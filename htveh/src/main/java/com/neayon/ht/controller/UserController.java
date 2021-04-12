package com.neayon.ht.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neayon.ht.bean.Message;
import com.neayon.ht.entity.Customer;
import com.neayon.ht.entity.User;
import com.neayon.ht.manager.UserManager;

@RestController
@RequestMapping("user")
public class UserController {
	@Resource(name = "UserManager")
	private UserManager um;

	@RequestMapping(value = "login.nc", name = "用户登录", method = RequestMethod.POST)
	public Message login(String username, String password) {
		Message mes = um.login(username, password);
		return mes;
	}

	@RequestMapping(value = "logout", name = "用户登出", method = RequestMethod.GET)
	public Message logout() {
		Message mes = um.logout();
		return mes;
	}

	@RequestMapping(value = "getUser", name = "获取用户信息", method = RequestMethod.GET)
	public Message getUser() {
		Message mes = um.getUser();
		return mes;
	}
	
	@RequestMapping(value = "customers", name = "获取客户列表", method = RequestMethod.GET)
	public Message customers(boolean isPage, int page, int size, Customer customer) {
		Message mes = um.fetchCustomers(isPage, page, size, customer);
		return mes;
	}

	public Message grayers() {
		Message mes = new Message();
		return mes;
	}

	@RequestMapping(value = "users", name = "获取全部职工", method = RequestMethod.GET)
	public Message users(int page, int size, String startTime, String endTime, User user) {
		Message mes = um.fetchUsers(page, size, startTime, endTime, user);
		return mes;
	}
	
	@RequestMapping(value = "addUser", name = "新增职工", method = RequestMethod.POST)
	public Message addUser(User user) {
		Message mes = um.addUser(user);
		return mes;
	}
	
	@RequestMapping(value = "updateUser", name = "更新职工信息", method = RequestMethod.POST)
	public Message updateUser(User user) {
		Message mes = um.updateUser(user);
		return mes;
	}
	
	@RequestMapping(value = "deleteUser", name = "删除职工", method = RequestMethod.POST)
	public Message deleteUser(Integer userId) {
		Message mes = um.deleteUser(userId);
		return mes;
	}
}
