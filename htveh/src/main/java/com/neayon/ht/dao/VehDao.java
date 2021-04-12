package com.neayon.ht.dao;

import java.util.List;

import com.neayon.ht.bean.PageBean;
import com.neayon.ht.entity.Veh;

public interface VehDao {
	
	public List<Veh> fetchVehsByPage(PageBean pageBean);
	
	public Integer fetchVehsCountsByParam(PageBean pageBean);
	
	public Veh fetchVehBySn(String sn);
	
	public Integer updateVeh(Veh veh);
	
	public Veh fetchVehBySfsn(String sfsn);
	
	public Veh fetchVehByEnvsn(String envsn);
	
	public Veh fetchUniqueVeh(Veh veh);
	
	public Integer saveNewVeh(Veh veh);
}
