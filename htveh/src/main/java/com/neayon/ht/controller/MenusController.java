package com.neayon.ht.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neayon.ht.bean.Message;
import com.neayon.ht.manager.MenuManager;

@RestController
@RequestMapping("menu")
public class MenusController {
	@Resource(name = "MenuManager")
	private MenuManager mm;

	@RequestMapping(value = "getMenus", name = "获取菜单", method = RequestMethod.GET)
	public Message getMenus() {
		Message mes = mm.getMenus();
		return mes;
	}

	@RequestMapping(value = "getBases", name = "获取基础参数", method = RequestMethod.GET)
	public Message getBases(String type) {
		Message mes = mm.getBases(type);
		return mes;
	}
	
	@RequestMapping(value = "getPayModels", name = "获取付费模板", method = RequestMethod.GET)
	public Message getPayModels(String vehType) {
		Message mes = mm.getPayModels(vehType);
		return mes;
	}
}
