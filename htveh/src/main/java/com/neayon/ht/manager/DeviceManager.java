package com.neayon.ht.manager;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neayon.ht.common.Code;
import com.neayon.ht.common.Constant;
import com.neayon.ht.common.LKEnvCons;
import com.neayon.ht.common.LKSafeCons;
import com.neayon.ht.dao.UserDao;
import com.neayon.ht.dao.VehDao;
import com.neayon.ht.entity.User;
import com.neayon.ht.entity.Veh;
import com.neayon.ht.exception.MyException;
import com.neayon.ht.util.CharUtil;
import com.neayon.ht.util.EnvWebserviceUtil;
import com.neayon.ht.util.SafeWebserviceUtil;
import com.neayon.ht.util.SerialNumberUtil;
import com.neayon.ht.util.XMParser;

import net.sf.json.JSONObject;

@Service("DeviceManager")
public class DeviceManager extends BaseManager {
	@Autowired
	private UserDao userDao;
	@Autowired
	private VehDao vehDao;

	/**
	 * 绑定检验员与车辆
	 * 
	 * @param cardNo
	 * @param insType
	 * @return
	 * @throws MyException
	 */
	public Veh doBind(String cardNo, String insType) throws MyException {
		User jyy = userDao.fetchUserByCard(cardNo);
		if (jyy == null) {
			throw new MyException(Code.PARAM_NULL_ERROR, "该卡号未绑定检验员！");
		} else {
			if ("safe".equals(insType)) {
				return dealSafe(cardNo, jyy);
			} else if ("env".equals(insType)) {
				return dealEnvironment(cardNo, jyy);
			} else {
				throw new MyException(Code.INSTYPE_ERROR, "错误的刷卡机设置");
			}
		}
	}

	/**
	 * 检验员解绑车辆/交车
	 * 
	 * @param cardNo
	 * @param insType
	 * @return
	 */
	public boolean doUnbind(String cardNo, String insType) throws MyException {
		Date now = new Date();
		boolean safe = false, env = false;
		// 安检交车解绑
		String sfsn = (String) redis.get(getVtgSafeKey(cardNo));
		if (!CharUtil.isEmpty(sfsn)) {
			Veh veh = vehDao.fetchVehBySfsn(sfsn);
			if (veh != null) {
				veh.setStateSafety(Veh.SF_STATE_SFOVR);
				veh.setGuideEnd(now);
				veh.setSafetyEnd(now);
				if (veh.getStateEnv() == Veh.EV_STATE_CKOVR) {
					veh.setState(Veh.STATE_UNPAY);
				}
				vehDao.updateVeh(veh);
			}
			safe = redis.del(getVtgSafeKey(cardNo)) && redis.del(getVehSafeKey(sfsn));
		}
		// 环检交车解绑
		String envsn = (String) redis.get(getVtgEnvKey(cardNo));
		if (!CharUtil.isEmpty(envsn)) {
			Veh veh = vehDao.fetchVehByEnvsn(envsn);
			if (veh != null) {
				veh.setStateEnv(Veh.EV_STATE_CKOVR);
				veh.setEnvEnd(now);
				if (veh.getStateSafety() == Veh.SF_STATE_SFOVR) {
					veh.setState(Veh.STATE_UNPAY);
				}
				vehDao.updateVeh(veh);
			}
			env = redis.del(getVtgEnvKey(cardNo)) && redis.del(getVehEnvKey(envsn));
		}
		if (safe || env) {
			return true;
		} else {
			throw new MyException(Code.GUIDE_HAS_NO_VEH, "你的名下没有车辆");
		}
	}

	/**
	 * 安检部分处理流程
	 * 
	 * @param cardNo
	 * @param jyy
	 * @return
	 * @throws MyException
	 */
	private Veh dealSafe(String cardNo, User jyy) throws MyException {
		String sfsn = (String) redis.get(getVtgSafeKey(cardNo));
		if (sfsn != null) {
			Veh veh = vehDao.fetchVehBySfsn(sfsn);
			throw new MyException(Code.GUIDE_HAS_VEH, "安检已绑车 " + veh.getPlateNumber());
		}
		// 林克取安检待检车列表
		Map<String, String> param = new HashMap<String, String>();
		param.put("jczbh", getBaseParam("baseInfo", "jyjgbh"));
		Document doc = SafeWebserviceUtil.queryws(LKSafeCons.VD02, param);
		try {
			List<Veh> vehs = parseVehs(doc.getRootElement().element("body").elements("info"), "safe");
			String cllxs = getBaseParam("zjcx", jyy.getDrivingModel());
			for (Veh veh : vehs) {
				Veh vehLocal = vehDao.fetchUniqueVeh(veh);
				if (vehLocal != null) {
					// 复检，分配给初检检验员
					if (!jyy.getUsername().equals(vehLocal.getGuide())) {
						continue;
					}
					// 校验是否可绑定
					if (vehLocal.getTipstate() != Veh.TIP_STATE_NORMAL) {
						continue;
					}
				}
				// 下载车辆信息
				param.put("hphm", veh.getPlateNumber());
				param.put("hpzl", veh.getPlateType());
				Document vehDoc = SafeWebserviceUtil.queryws(LKSafeCons.VD01, param);
				Element vehEl = vehDoc.getRootElement().element("body").element("info");
				Veh vehTmp = parseSfVeh(vehEl);
				// 引车员能力过滤
				if (cllxs.equals("")) {
					throw new MyException(Code.GUIDE_DRIVING_BANED, "你没有驾驶资格");
				} else {
					if (!cllxs.contains(vehTmp.getVehType())) {
						continue;
					}
				}
				// 外观是否完成（判断是否可绑定）
				if (!judgeCouldUplineSafe(veh)) {
					continue;
				}
				// 信息本地存储
				int res = 0;
				if (vehLocal != null) {
					doSfRecheck(vehEl, vehLocal);
					vehLocal.setUpdateUser(jyy.getId());
					res = vehDao.updateVeh(vehLocal);
				} else {
					vehLocal = vehTmp;
					vehLocal.setGuide(jyy.getUsername());
					vehLocal.setCreateUser(jyy.getId());
					res = vehDao.saveNewVeh(vehLocal);
				}
				if (res > 0) {
					// 绑定车辆和引车员
					redis.set(getVtgSafeKey(cardNo), veh.getSfsn(), Constant.EXPIRETIME);
					redis.set(getVehSafeKey(veh.getSfsn()), cardNo, Constant.EXPIRETIME);
					// TODO 发车上线Q01模拟信号

					return new Veh(jyy.getUsername(), veh.getPlateNumber(), vehs.size());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(Code.RUNTIME_EXCEPTION, e.getMessage());
		}
		return null;
	}

	/**
	 * 环检部分处理流程
	 * 
	 * @param cardNo
	 * @param jyy
	 * @return
	 * @throws MyException
	 */
	private Veh dealEnvironment(String cardNo, User jyy) throws MyException {
		String envsn = (String) redis.get(getVtgEnvKey(cardNo));
		if (envsn != null) {
			Veh veh = vehDao.fetchVehByEnvsn(envsn);
			throw new MyException(Code.GUIDE_HAS_VEH, "环检已绑车 " + veh.getPlateNumber());
		}
		// 林克取环检待检车辆列表
		Map<String, String> param = new HashMap<String, String>();
		param.put("jczbh", getBaseParam("baseInfo", "jyjgbh"));
		param.put("jcfs", "A,B,C,Q");
		Document doc = EnvWebserviceUtil.queryws(LKEnvCons.VD01, param);
		try {
			List<Veh> vehs = parseVehs(doc.getRootElement().element("body").elements("info"), "env");
			String cllxs = getBaseParam("zjcx", jyy.getDrivingModel());
			for (Veh veh : vehs) {
				Veh vehLocal = vehDao.fetchUniqueVeh(veh);
				if (vehLocal != null) {
					// 复检，分配给初检检验员
					if (!jyy.getUsername().equals(vehLocal.getEnvInspector())) {
						continue;
					}
					// 校验是否可绑定
					if (vehLocal.getTipstate() != Veh.TIP_STATE_NORMAL) {
						continue;
					}
				}
				// 下载车辆信息
				param.put("lsh", veh.getEnvsn());
				Document vehDoc = EnvWebserviceUtil.queryws(LKEnvCons.VD02, param);
				Element vehEl = vehDoc.getRootElement().element("body").element("info");
				Veh vehTmp = parseEnvVeh(vehEl);
				// 引车员能力过滤
				if (cllxs.equals("")) {
					throw new MyException(Code.GUIDE_DRIVING_BANED, "你没有驾驶资格");
				} else {
					if (!cllxs.contains(vehTmp.getVehType())) {
						continue;
					}
				}
				// 信息本地存储
				int res = 0;
				if (vehLocal != null) {
					doEnvRecheck(vehEl, vehLocal);
					vehLocal.setUpdateUser(jyy.getId());
					res = vehDao.updateVeh(vehLocal);
				} else {
					vehLocal = vehTmp;
					vehLocal.setEnvInspector(jyy.getUsername());
					vehLocal.setCreateUser(jyy.getId());
					res = vehDao.saveNewVeh(vehLocal);
				}
				if (res > 0) {
					// 绑定车辆和引车员
					redis.set(getVtgEnvKey(cardNo), veh.getEnvsn(), Constant.EXPIRETIME);
					redis.set(getVehEnvKey(veh.getSfsn()), cardNo, Constant.EXPIRETIME);
					return new Veh(jyy.getUsername(), veh.getPlateNumber(), vehs.size());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(Code.RUNTIME_EXCEPTION, e.getMessage());
		}
		return null;
	}

	private String getVtgSafeKey(String cardNo) {
		return "vtg-safe-" + cardNo;
	}

	private String getVtgEnvKey(String cardNo) {
		return "vtg-env-" + cardNo;
	}

	private String getVehSafeKey(String sfsn) {
		return "veh-safe-" + sfsn;
	}

	private String getVehEnvKey(String sfsn) {
		return "veh-env-" + sfsn;
	}

	/**
	 * 车辆待检列表转换
	 * 
	 * @param els
	 * @param insType
	 * @return
	 */
	private List<Veh> parseVehs(List<Element> els, String insType) {
		List<Veh> vehs = new ArrayList<Veh>();
		for (Element el : els) {
			Veh veh = new Veh();
			String lsh = el.elementText("lsh");
			// 过滤被绑定的车
			if ("safe".equals(insType)) {
				if (redis.get(getVehSafeKey(lsh)) != null) {
					continue;
				}
				veh.setSfsn(lsh);
			} else if ("env".equals(insType)) {
				if (redis.get(getVehEnvKey(lsh)) != null) {
					continue;
				}
				veh.setEnvsn(lsh);
			}
			veh.setPlateNumber(el.elementText("hphm"));
			veh.setPlateType(el.elementText("hpzl"));
			vehs.add(veh);
		}
		System.out.println(vehs.size());
		vehs.sort(new Comparator<Veh>() {
			@Override
			public int compare(Veh o1, Veh o2) {
				if (Integer.parseInt(o1.getPlateType()) > Integer.parseInt(o2.getPlateType())) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		return vehs;
	}

	/**
	 * 安检车辆详细信息转换
	 * 
	 * @param el
	 * @return
	 */
	private Veh parseSfVeh(Element el) {
		Veh veh = new Veh();
		veh.setSn(SerialNumberUtil.createSn(SerialNumberUtil.SN));
		veh.setSfsn(el.elementText("lsh"));
		veh.setPlateNumber(el.elementText("hphm"));
		veh.setPlateType(el.elementText("hpzl"));
		veh.setVehType(el.elementText("cllx"));
		veh.setOwner(el.elementText("syr"));
		veh.setVin(el.elementText("clsbdh"));
		veh.setInsOrgNumber(el.elementText("jczbh"));
		veh.setFrequencySafety(Integer.parseInt(el.elementText("jycs")));
		veh.setSafetyInspects(transInspections(null, veh.getFrequencySafety(), el.elementText("bcjyxm")));
		veh.setGuideStart(new Date());
		veh.setStateSafety(Veh.SF_STATE_ULING);
		return veh;
	}

	/**
	 * 安检复检车辆信息更新
	 * 
	 * @param el
	 * @param veh
	 */
	private void doSfRecheck(Element el, Veh veh) {
		veh.setFrequencySafety(Integer.parseInt(el.elementText("jycs")));
		veh.setSafetyInspects(transInspections(JSONObject.fromObject(veh.getSafetyInspects()), veh.getFrequencySafety(),
				el.elementText("bcjyxm")));
		veh.setGuideStart(new Date());
		veh.setStateSafety(Veh.SF_STATE_ULING);
		veh.setState(Veh.STATE_LOGED);
	}

	/**
	 * 环检车辆详细信息转换
	 * 
	 * @param el
	 * @return
	 */
	private Veh parseEnvVeh(Element el) {
		Veh veh = new Veh();
		veh.setSn(SerialNumberUtil.createSn(SerialNumberUtil.SN));
		veh.setSfsn(el.elementText("lsh"));
		veh.setPlateNumber(el.elementText("hphm"));
		veh.setPlateType(el.elementText("hpzl"));
		veh.setVehType(el.elementText("cllx"));
		veh.setOwner(el.elementText("syr"));
		veh.setOwnerContact(el.elementText("lxdh"));
		veh.setVin(el.elementText("clsbdh"));
		veh.setInsOrgNumber(el.elementText("jczbh"));
		veh.setFrequencyEnv(Integer.parseInt(el.elementText("jccs")));
		String jyxm = el.elementText("jcfs");
		if (judgeCouldUplineEnv(veh)) {
			jyxm += ",HF";
		}
		if ("1".equals(el.elementText("obd"))) {
			jyxm += ",OBD";
		}
		veh.setEnvInspects(transInspections(JSONObject.fromObject(veh.getEnvInspects()), veh.getFrequencyEnv(), jyxm));
		veh.setEnvStart(new Date());
		veh.setStateEnv(Veh.EV_STATE_CKING);
		return veh;
	}

	/**
	 * 环检复检车辆信息更新
	 * 
	 * @param el
	 * @param veh
	 */
	private void doEnvRecheck(Element el, Veh veh) {
		veh.setFrequencySafety(Integer.parseInt(el.elementText("jccs")));
		String jyxm = el.elementText("jcfs");
		if (judgeCouldUplineEnv(veh)) {
			jyxm += ",HF";
		}
		if ("1".equals(el.elementText("obd"))) {
			jyxm += ",OBD";
		}
		veh.setEnvInspects(transInspections(JSONObject.fromObject(veh.getEnvInspects()), veh.getFrequencyEnv(), jyxm));
		veh.setStateEnv(Veh.EV_STATE_CKING);
		veh.setState(Veh.STATE_LOGED);
	}

	/**
	 * 检验项目组装
	 * 
	 * @param inspectsOrigin
	 * @param frequency
	 * @param inspections
	 * @return
	 */
	private String transInspections(JSONObject inspectsOrigin, Integer frequency, String inspections) {
		if (inspectsOrigin == null) {
			inspectsOrigin = new JSONObject();
		}
		inspectsOrigin.put("t" + frequency, XMParser.parseLinkJyxmToGBStr(inspections, ","));
		return inspectsOrigin.toString();
	}

	/**
	 * 校验车辆是否做完安检外观
	 * 
	 * @param veh
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private boolean judgeCouldUplineSafe(Veh veh) {
		String lsh = veh.getSfsn();
		boolean wj = false;
		Map<String, String> param = new HashMap<String, String>();
		param.put("lsh", lsh);
		Document doc = SafeWebserviceUtil.queryws(LKSafeCons.VD06, param);
		if (doc != null) {
			Element root = doc.getRootElement();
			if (root != null) {
				Element head = root.element("head");
				Element body = root.element("body");
				if (head != null && "1".equals(head.elementText("code")) && body != null) {
					List<Element> infos = body.elements("info");
					if (infos != null && infos.size() > 0) {
						for (Element info : infos) {
							if ("外观".equals(URLDecoder.decode(info.elementText("rgjyfs")))) {
								wj = true;
								break;
							}
						}
					}
				}
			}
		}
		return wj;
	}

	/**
	 * 校验车辆是否做完环检外观
	 * 
	 * @param veh
	 * @return
	 */
	private boolean judgeCouldUplineEnv(Veh veh) {
		String lsh = veh.getEnvsn();
		boolean wj = false;
		Map<String, String> param = new HashMap<String, String>();
		param.put("jczbh", veh.getInsOrgNumber());
		param.put("lsh", lsh);
		param.put("jccs", String.valueOf(veh.getFrequencyEnv()));
		Document doc = EnvWebserviceUtil.queryws(LKEnvCons.VD07, param);
		if (doc != null) {
			Element root = doc.getRootElement();
			if (root != null) {
				Element head = root.element("head");
				Element body = root.element("body");
				if (head != null && "1".equals(head.elementText("code")) && body != null) {
					Element info = body.element("info");
					if (info != null) {
						wj = true;
					}
				}
			}
		}
		return wj;
	}
}
