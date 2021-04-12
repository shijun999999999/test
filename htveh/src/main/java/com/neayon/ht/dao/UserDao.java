package com.neayon.ht.dao;

import java.util.List;

import com.neayon.ht.bean.PageBean;
import com.neayon.ht.entity.User;

public interface UserDao {

	public User login(User user);
	
	public User fetchUserById(Integer userId);
	
	public List<User> fetchUsersByPage(PageBean pageBean);
	
	public Integer fetchUsersCountsByParam(PageBean pageBean);
	
	public Integer insert(User user);
	
	public Integer update(User user);
	
	public Integer delete(Integer userId);
	
	public User fetchUserByCard(String cardNo);
}
