package com.neayon.ht.network.driver;

import java.io.UnsupportedEncodingException;

import com.neayon.ht.bean.Message;
import com.neayon.ht.common.Code;
import com.neayon.ht.entity.Veh;
import com.neayon.ht.network.abs.AbstractCardDevice;
import com.neayon.ht.util.CharUtil;

public class CardDeviceOfNeayon extends AbstractCardDevice {
	private String cardPrefix = "040C0220000400";
	// 初始界面
	private String view1 = "A55A0480030000";
	// 车辆数量
	private String clsl = "A55A05820010${msg}";
	// 信息界面
	private String view2 = "A55A0480030001";
	// 检验员
	private String jyy = "A55A${len}820200${msg}FFFF";
	// 号牌号码
	private String hphm = "A55A${len}820100${msg}FFFF";
	// 提示界面
	private String view3 = "A55A0480030006";
	// 提示信息
	private String tsxx = "A55A${len}820400${msg}FFFF";

	@Override
	public void device2pc(byte[] datas) throws InterruptedException {
		String cardNo = CharUtil.byte2HexOfString(datas);
		System.out.println("读取到数据：" + cardNo);
		if (cardDevice.isBoub()) {
			// 绑定业务
			Message mes = cardDevice.bindVeh(cardNo.replace(cardPrefix, ""));
			if (mes.getCode() == Code.SUCCESS) {
				String jyymsg = jyy;
				String hphmmsg = hphm;
				String clslmsg = clsl;
				try {
					Veh veh = (Veh) mes.getResult();
					byte[] msgs = veh.getGuide().getBytes("GBK");
					String hex = CharUtil.byte2HexOfString(msgs);
					jyymsg = jyymsg.replace("${msg}", hex).replace("${len}", String.format("%02X", msgs.length + 5));
					msgs = veh.getPlateNumber().getBytes("GBK");
					hex = CharUtil.byte2HexOfString(msgs);
					hphmmsg = hphmmsg.replace("${msg}", hex).replace("${len}", String.format("%02X", msgs.length + 5));
					clslmsg = clslmsg.replace("${msg}", String.format("%04X", veh.getCountsWaiting()));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				cardDevice.sendMessage(jyymsg);
				cardDevice.sendMessage(hphmmsg);
				cardDevice.sendMessage(clslmsg);
				Thread.sleep(500);
				cardDevice.sendMessage(view2);
			} else {
				sendTips(mes.getError());
			}
		} else {
			// 解绑业务
			Message mes = cardDevice.unbindVeh(cardNo.replace(cardPrefix, ""));
			if (mes.getCode() == Code.SUCCESS) {
				sendTips("交车完成！");
			} else {
				sendTips(mes.getError());
			}
		}
		Thread.sleep(2000);
		cardDevice.sendMessage(view1);
	}
	
	/**
	 * 发送提示消息
	 * @param message
	 * @throws InterruptedException 
	 */
	private void sendTips(String message) throws InterruptedException {
		String tsxxmsg = tsxx;
		try {
			byte[] msgs = message.getBytes("GBK");
			String hex = CharUtil.byte2HexOfString(msgs);
			tsxxmsg = tsxxmsg.replace("${msg}", hex).replace("${len}", String.format("%02X", msgs.length + 5));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		cardDevice.sendMessage(tsxxmsg);
		Thread.sleep(500);
		cardDevice.sendMessage(view3);
	}
}
