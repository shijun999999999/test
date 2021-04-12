package com.neayon.ht.network.device;

import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.neayon.ht.bean.Message;
import com.neayon.ht.common.Code;
import com.neayon.ht.entity.Veh;
import com.neayon.ht.exception.MyException;
import com.neayon.ht.manager.DeviceManager;
import com.neayon.ht.network.DCPReader;
import com.neayon.ht.network.abs.AbstractCardDevice;
import com.neayon.ht.util.CharUtil;

import net.sf.json.JSONObject;

@Service("CardDevice")
public class CardDevice extends DCPReader {
	private Logger log = LoggerFactory.getLogger(CardDevice.class);
	private AbstractCardDevice abs;
	private boolean boub;
	private String insType;
	@Resource(name = "DeviceManager")
	private DeviceManager dm;

	public boolean isBoub() {
		return boub;
	}

	public void setBoub(boolean boub) {
		this.boub = boub;
	}

	public String getInsType() {
		return insType;
	}

	public void setInsType(String insType) {
		this.insType = insType;
	}

	@Override
	public void init() throws InstantiationException, IllegalAccessException, ClassNotFoundException, MyException {
		abs = (AbstractCardDevice) Class.forName(super.getDevice().getDecoder()).newInstance();
		abs.init(this);
		String tmp = getDevice().getOthers();
		if (!CharUtil.isEmpty(tmp)) {
			JSONObject others = JSONObject.fromObject(tmp);
			this.setBoub(others.getBoolean("boub"));
			this.setInsType(others.getString("insType"));
		} else {
			throw new MyException("配置不全");
		}
	}

	@Override
	public void run() {
		while (true) {
			byte[] r = new byte[1024 * 128];
			int length = 0;
			int lengthTemp = 0;
			try {
				while (in.available() > 0) {
					lengthTemp = in.read(r);
					length += lengthTemp;
					if (length >= 1024 * 128) {
						break;
					}
				}
				if (length > 0) {
					byte[] endodedData = new byte[length];
					System.arraycopy(r, 0, endodedData, 0, length);
					abs.device2pc(endodedData);
				}
			} catch (IOException | InterruptedException e) {
				log.error("读卡数据异常", e);
			}
		}
	}

	/**
	 * 绑定车辆
	 * 
	 * @return
	 */
	public Message bindVeh(String cardNo) {
		Message result = new Message();
		try {
			Veh veh = dm.doBind(cardNo, insType);
			if (veh != null) {
				result.setCode(Code.SUCCESS);
				result.setResult(veh);
			} else {
				result.setCode(Code.NO_VEH_BIND);
				result.setError("当前没有可分配给你的车辆！");
			}
		} catch (MyException e) {
			result.setCode(e.getCode());
			result.setError(e.getE());
		} catch (Exception e) {
			result.setCode(Code.RUNTIME_EXCEPTION);
			result.setError(e.getMessage());
		}
		return result;
	}

	/**
	 * 解绑车辆
	 * 
	 * @return
	 */
	public Message unbindVeh(String cardNo) {
		Message result = new Message();
		try {
			if (dm.doUnbind(cardNo, insType)) {
				result.setCode(Code.SUCCESS);
			}
		} catch (MyException e) {
			result.setCode(e.getCode());
			result.setError(e.getE());
		} catch (Exception e) {
			result.setCode(Code.RUNTIME_EXCEPTION);
			result.setError(e.getMessage());
		}
		return result;
	}

}
