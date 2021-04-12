package com.neayon.ht.dao;

import java.util.List;

import com.neayon.ht.bean.PageBean;
import com.neayon.ht.entity.Customer;

public interface CustomerDao {
	
	public List<Customer> fetchCustomers(PageBean pageBean);
}
