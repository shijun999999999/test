package com.neayon.ht.dao;

import com.neayon.ht.entity.Payorder;

public interface PayorderDao {
	
	public Integer savePayorder(Payorder payorder);
	
	public Payorder fetchPayorder(String sn);
	
	public Integer updatePayorder(Payorder payorder);
}
