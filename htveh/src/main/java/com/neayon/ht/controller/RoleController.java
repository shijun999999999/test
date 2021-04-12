package com.neayon.ht.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neayon.ht.bean.Message;
import com.neayon.ht.entity.Role;
import com.neayon.ht.manager.RoleManager;

@RestController
@RequestMapping("role")
public class RoleController {
	@Resource(name = "RoleManager")
	private RoleManager rm;
	
	@RequestMapping(value = "roles", name = "获取所有角色列表", method = RequestMethod.GET)
	public Message roles() {
		Message mes = rm.getRoles();
		return mes;
	}
	
	@RequestMapping(value = "roleAuths", name = "获取权限字段", method = RequestMethod.GET)
	public Message roleAuths() {
		Message mes = rm.roleAuths();
		return mes;
	}
	
	@RequestMapping(value = "addRole", name = "新增角色", method = RequestMethod.POST)
	public Message addRole(Role role) {
		Message mes = rm.addRole(role);
		return mes;
	}
	
	@RequestMapping(value = "updateRole", name = "更新角色", method = RequestMethod.POST)
	public Message updateRole(Role role) {
		Message mes = rm.updateRole(role);
		return mes;
	}
	
	@RequestMapping(value = "delRole", name = "删除角色", method = RequestMethod.POST)
	public Message delRole(Integer roleId) {
		Message mes = rm.delRole(roleId);
		return mes;
	}
}
