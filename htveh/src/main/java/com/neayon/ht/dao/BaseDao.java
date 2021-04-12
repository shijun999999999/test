package com.neayon.ht.dao;

import java.util.List;

import com.neayon.ht.entity.Base;

public interface BaseDao {
	
	public List<Base> fetchBaseParams(String type);
	
	public Base fetchBaseParam(Base base);
}
