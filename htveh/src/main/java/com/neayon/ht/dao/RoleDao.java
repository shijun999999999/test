package com.neayon.ht.dao;

import java.util.List;

import com.neayon.ht.entity.Role;
import com.neayon.ht.entity.RoleAuth;

public interface RoleDao {

	public Role getRoleById(Integer id);

	public List<Role> getRoles();

	public List<RoleAuth> getRoleAuths();
	
	public Integer addRole(Role role);
	
	public Integer updateRole(Role role);
	
	public Integer delRole(Integer roleId);
}
