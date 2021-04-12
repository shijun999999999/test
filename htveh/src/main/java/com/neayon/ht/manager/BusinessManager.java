package com.neayon.ht.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neayon.ht.bean.Message;
import com.neayon.ht.bean.PageBean;
import com.neayon.ht.common.Code;
import com.neayon.ht.common.DiscountDesc;
import com.neayon.ht.common.SocketKey;
import com.neayon.ht.dao.PayModelDao;
import com.neayon.ht.dao.PayorderDao;
import com.neayon.ht.dao.VehDao;
import com.neayon.ht.entity.PayModel;
import com.neayon.ht.entity.Payorder;
import com.neayon.ht.entity.User;
import com.neayon.ht.entity.Veh;
import com.neayon.ht.socket.WebSocketServer;
import com.neayon.ht.util.CharUtil;
import com.neayon.ht.util.SerialNumberUtil;

import net.sf.json.JSONObject;

@Service("BusinessManager")
@SuppressWarnings("unchecked")
public class BusinessManager extends BaseManager {
	public static final int TYPE_CONFIRM = 1;
	public static final int TYPE_AFTER_MARK = 2;
	public static final int TYPE_ISSUE = 3;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private VehDao vehDao;
	@Autowired
	private PayModelDao payModelDao;
	@Autowired
	private PayorderDao payorderDao;

	/**
	 * 拉取登记的车辆列表
	 * 
	 * @param page
	 * @param size
	 * @param startTime
	 * @param endTime
	 * @param veh
	 * @return
	 */
	public Message fetchVehList(Integer page, Integer size, String startTime, String endTime, Veh veh) {
		Message mes = new Message();
		if (CharUtil.isEmpty(veh)) {
			veh = new Veh();
		}
		Date start = null;
		try {
			start = sdf.parse(startTime);
		} catch (Exception e) {
		}
		Date end = null;
		try {
			end = sdf.parse(endTime);
		} catch (Exception e) {
		}
		PageBean pageBean = new PageBean(true, page, size, veh, start, end);
		List<Veh> vehs = vehDao.fetchVehsByPage(pageBean);
		Integer counts = vehDao.fetchVehsCountsByParam(pageBean);
//		Integer total = 1;
//		size = pageBean.getPageSize();
//		if (counts % size == 0) {
//			total = counts / size;
//		} else {
//			total = counts / size + 1;
//		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("vehs", vehs);
		map.put("total", counts);
		mes.setCode(Code.SUCCESS);
		mes.setMessage("fetch success");
		mes.setResult(map);
		return mes;
	}

	/**
	 * 获取车辆信息
	 * 
	 * @param sn
	 * @return
	 */
	public Message getVeh(String sn) {
		Message mes = new Message();
		if (CharUtil.isEmpty(sn)) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError("sn");
			return mes;
		}
		if (!sn.startsWith(SerialNumberUtil.SN)) {
			mes.setCode(Code.PARAM_REGEX_ERROR);
			mes.setError("sn");
			return mes;
		}
		Veh veh = vehDao.fetchVehBySn(sn);
		if (veh == null) {
			veh = new Veh();
		}
		mes.setCode(Code.SUCCESS);
		mes.setMessage("veh");
		mes.setResult(veh);
		return mes;
	}

	/**
	 * 查询订单信息
	 * 
	 * @param sn
	 * @return
	 */
	public Message fetchPayorder(String sn) {
		Message mes = new Message();
		if (CharUtil.isEmpty(sn)) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError("sn");
			return mes;
		}
		if (!sn.startsWith(SerialNumberUtil.SN)) {
			mes.setCode(Code.PARAM_REGEX_ERROR);
			mes.setError("sn");
			return mes;
		}
		Payorder payorder = getPayorder(sn);
		if (payorder == null) {
			payorder = new Payorder();
		}
		mes.setCode(Code.SUCCESS);
		mes.setMessage("payorder");
		mes.setResult(payorder);
		return mes;
	}

	/**
	 * 创建支付订单
	 * 
	 * @param sn
	 * @param inspections
	 * @return
	 */
	public Message doPayorder(String sn, String inspections) {
		Message mes = new Message();
		if (CharUtil.isEmpty(sn)) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError("sn");
			return mes;
		}
		// 检查是否创建过支付订单
		if (!CharUtil.isEmpty(getPayorder(sn))) {
			mes.setCode(Code.OPTION_DUBLICATE);
			mes.setError("payorder");
			return mes;
		}
		if (CharUtil.isEmpty(inspections)) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError("inspections");
			return mes;
		}
		Veh veh = vehDao.fetchVehBySn(sn);
		if (CharUtil.isEmpty(veh)) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError("veh");
			return mes;
		}
		Payorder payorder = new Payorder();
		payorder.setSn(veh.getSn());
		payorder.setOrderSn(SerialNumberUtil.createSn(SerialNumberUtil.ORDER_SN));
		calcPay(payorder, veh, inspections);
		Integer res = payorderDao.savePayorder(payorder);
		if (res > 0) {
			// 更新车辆状态
			veh.setState(Veh.STATE_UNPAY);
			veh.setUpdateUser(getUserStored().getId());
			vehDao.updateVeh(veh);
			JSONObject msg = new JSONObject();
			msg.put("from", getUserStored().getId());
			msg.put("to", "ALL");
			msg.put("message", SocketKey.toPay() + ";" + String.format("%.2f", payorder.getFeeActual() / 100.0));
			WebSocketServer.sendMessageToAll(msg.toString());
			mes.setCode(Code.SUCCESS);
			mes.setMessage("create success");
			mes.setResult(payorder);
		} else {
			mes.setCode(Code.OPTION_FAILED);
			mes.setError("payorder");
		}
		return mes;
	}

	/**
	 * 获取支付订单
	 * 
	 * @param sn
	 * @return
	 */
	public Payorder getPayorder(String sn) {
		Payorder payorder = payorderDao.fetchPayorder(sn);
		return payorder;
	}

	/**
	 * 计算支付费用
	 * 
	 * @param veh
	 * @param inspectsActual
	 * @return
	 */
	private void calcPay(Payorder payorder, Veh veh, String inspectsActual) {
		List<PayModel> models = payModelDao.fetchModels();
		JSONObject insOriginal = new JSONObject();
		String safetyInspects = veh.getSafetyInspects();
		String envInspects = veh.getEnvInspects();
		String vehType = veh.getVehType();
		String operator = getUserStored().getUsername();
		List<String> remarks = new ArrayList<String>();
		if (!CharUtil.isEmpty(inspectsActual)) {
			try {
				JSONObject insact = JSONObject.fromObject(inspectsActual);
				if (!CharUtil.isEmpty(safetyInspects)) {
					try {
						JSONObject inssaf = JSONObject.fromObject(safetyInspects);
						insOriginal.put("safe", inssaf);
						JSONObject actsaf = insact.getJSONObject("safe");
						if (!actsaf.equals(inssaf)) {
							remarks.add(DiscountDesc.SAF_XMKC.replace(DiscountDesc.RP, operator));
						}
						dealOriginal(payorder, vehType, inssaf, models);
						dealActual(payorder, vehType, actsaf, models, remarks);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (!CharUtil.isEmpty(envInspects)) {
					try {
						JSONObject insenv = JSONObject.fromObject(envInspects);
						insOriginal.put("env", insenv);
						JSONObject actenv = insact.getJSONObject("env");
						if (!actenv.equals(insenv)) {
							remarks.add(DiscountDesc.ENV_XMKC.replace(DiscountDesc.RP, operator));
						}
						dealOriginal(payorder, vehType, insenv, models);
						dealActual(payorder, vehType, actenv, models, remarks);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		payorder.setInspectsOriginal(insOriginal.toString());
		payorder.setInspectsActual(inspectsActual);
		payorder.setDiscount(payorder.getFeeOriginal() - payorder.getFeeActual());
		payorder.setDiscountRemark(StringUtils.join(remarks, ";"));
		payorder.setState(Payorder.STATE_UNPAY);
		payorder.setCreateUser(operator);
	}

	private void dealOriginal(Payorder payorder, String vehType, JSONObject inspects, List<PayModel> models) {
		Integer feeOriginal = payorder.getFeeOriginal();
		Set<String> keys = inspects.keySet();
		for (String key : keys) {
			String[] xms = inspects.getString(key).split(",");
			xmf: for (String xm : xms) {
				for (PayModel model : models) {
					if (model.getInspection().equals(xm) && model.getVehType().contains(vehType)) {
						feeOriginal += model.getCostOriginal();
						continue xmf;
					}
				}
				feeOriginal += PayModel.DEFAULT_PAY;
			}
		}
		payorder.setFeeOriginal(feeOriginal);
	}

	public void dealActual(Payorder payorder, String vehType, JSONObject inspects, List<PayModel> models,
			List<String> remarks) {
		Integer feeActual = payorder.getFeeActual();
		Set<String> keys = inspects.keySet();
		for (String key : keys) {
			String[] xms = inspects.getString(key).split(",");
			xmf: for (String xm : xms) {
				for (PayModel model : models) {
					if (model.getInspection().equals(xm) && model.getVehType().contains(vehType)) {
						feeActual += model.getCostActual();
						if (!model.getCostOriginal().equals(model.getCostActual())) {
							remarks.add(DiscountDesc.DPZK.replace(DiscountDesc.RP, model.getTitle()));
						}
						continue xmf;
					}
				}
				feeActual += PayModel.DEFAULT_PAY;
			}
		}
		payorder.setFeeActual(feeActual);
	}

	/**
	 * 更新订单
	 * 
	 * @param sn
	 * @param type
	 * @return
	 */
	public Message updatePayorder(String sn, Integer type) {
		Message mes = new Message();
		if (CharUtil.isEmpty(sn)) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError("sn");
			return mes;
		}
		Veh veh = vehDao.fetchVehBySn(sn);
		if (veh == null) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError("veh");
			return mes;
		}
		Payorder payorder = payorderDao.fetchPayorder(sn);
		if (payorder == null) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError("payorder");
			return mes;
		}
		User user = getUserStored();
		switch (type) {
		case TYPE_CONFIRM:
			payorder.setConfirmUser(user.getUsername());
			payorder.setState(Payorder.STATE_PAYED);
			veh.setState(Veh.STATE_PAYED);
			veh.setUpdateUser(user.getId());
			break;
		case TYPE_AFTER_MARK:
			payorder.setConfirmUser(user.getUsername());
			payorder.setState(Payorder.STATE_AFPAY);
			veh.setState(Veh.STATE_AFPAY);
			veh.setUpdateUser(user.getId());
			break;
		case TYPE_ISSUE:
			payorder.setIssueUser(user.getUsername());
			payorder.setState(Payorder.STATE_ISSUE);
			veh.setState(Veh.STATE_OVERD);
			veh.setUpdateUser(user.getId());
			break;
		default:
			break;
		}
		Integer orderres = payorderDao.updatePayorder(payorder);
		Integer vehres = vehDao.updateVeh(veh);
		if (orderres > 0 && vehres > 0) {
			mes.setCode(Code.SUCCESS);
			mes.setMessage("payorder");
			mes.setResult("confirm OK");
		} else {
			mes.setCode(Code.OPTION_FAILED);
			mes.setError("payorder");
		}
		return mes;
	}
}
