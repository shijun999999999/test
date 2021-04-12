package com.neayon.ht.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neayon.ht.bean.Message;
import com.neayon.ht.entity.Veh;
import com.neayon.ht.manager.BusinessManager;

@RestController
@RequestMapping("business")
public class BusinessController {
	@Resource(name = "BusinessManager")
	private BusinessManager bm;

	@RequestMapping(value = "vehList", name = "获取登记车辆列表", method = RequestMethod.GET)
	public Message vehList(Integer page, Integer size, String startTime, String endTime, Veh veh) {
		Message mes = bm.fetchVehList(page, size, startTime, endTime, veh);
		return mes;
	}
	
	@RequestMapping(value = "getVeh", name = "获取车辆信息", method = RequestMethod.GET)
	public Message getVeh(String sn) {
		Message mes = bm.getVeh(sn);
		return mes;
	}
	
	@RequestMapping(value = "getPayorder", name = "获取订单信息", method = RequestMethod.GET)
	public Message getPayorder(String sn) {
		Message mes = bm.fetchPayorder(sn);
		return mes;
	}
	
	@RequestMapping(value = "doPayorder", name = "创建支付订单", method = RequestMethod.POST)
	public Message doPayorder(String sn, String inspections) {
		Message mes = bm.doPayorder(sn, inspections);
		return mes;
	}
	
	@RequestMapping(value = "confirmPay", name = "订单确认", method = RequestMethod.POST)
	public Message confirmPay(String sn) {
		Message mes = bm.updatePayorder(sn, BusinessManager.TYPE_CONFIRM);
		return mes;
	}
	
	@RequestMapping(value = "afPay", name = "后结付标记", method = RequestMethod.POST)
	public Message afay(String sn) {
		Message mes = bm.updatePayorder(sn, BusinessManager.TYPE_AFTER_MARK);
		return mes;
	}
	
	@RequestMapping(value = "issue", name = "签证签发", method = RequestMethod.POST)
	public Message issue(String sn) {
		Message mes = bm.updatePayorder(sn, BusinessManager.TYPE_ISSUE);
		return mes;
	}
}
