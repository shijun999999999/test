package com.neayon.ht.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neayon.ht.bean.Message;
import com.neayon.ht.common.Code;
import com.neayon.ht.dao.RoleDao;
import com.neayon.ht.entity.Role;
import com.neayon.ht.entity.RoleAuth;
import com.neayon.ht.exception.MyException;
import com.neayon.ht.util.CharUtil;

@Service("RoleManager")
public class RoleManager extends BaseManager {
	@Autowired
	private RoleDao roleDao;

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	public Message getRoles() {
		Message mes = new Message();
		List<Role> roles = roleDao.getRoles();
		mes.setCode(Code.SUCCESS);
		mes.setMessage("roles");
		mes.setResult(roles);
		return mes;
	}

	/**
	 * 校验角色是否正确
	 * 
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkRole(Integer roleId) {
		List<Role> roles = (List<Role>) getRoles().getResult();
		for (Role role : roles) {
			if (role.getId().intValue() == roleId.intValue()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取权限字段
	 * 
	 * @return
	 */
	public Message roleAuths() {
		Message mes = new Message();
		List<RoleAuth> auths = roleDao.getRoleAuths();
		mes.setCode(Code.SUCCESS);
		mes.setMessage("roleAuths");
		mes.setResult(auths);
		return mes;
	}

	/**
	 * 新增角色
	 * 
	 * @param role
	 * @return
	 */
	public Message addRole(Role role) {
		Message mes = new Message();
		try {
			checkRole(role, false);
		} catch (MyException e) {
			mes.setCode(e.getCode());
			mes.setError(e.getE());
			return mes;
		}
		role.setCreateUser(getUserStored().getId());
		Integer res = roleDao.addRole(role);
		if (res > 0) {
			mes.setCode(Code.SUCCESS);
			mes.setMessage("add role");
			mes.setResult(res);
		} else {
			mes.setCode(Code.OPTION_FAILED);
			mes.setError("add role");
		}
		return mes;
	}

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return
	 */
	public Message updateRole(Role role) {
		Message mes = new Message();
		try {
			checkRole(role, true);
		} catch (MyException e) {
			mes.setCode(e.getCode());
			mes.setError(e.getE());
			return mes;
		}
		role.setUpdateUser(getUserStored().getId());
		Integer res = roleDao.updateRole(role);
		if (res > 0) {
			mes.setCode(Code.SUCCESS);
			mes.setMessage("update role");
			mes.setResult(res);
		} else {
			mes.setCode(Code.OPTION_FAILED);
			mes.setError("update role");
		}
		return mes;
	}

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 * @return
	 */
	public Message delRole(Integer roleId) {
		Message mes = new Message();
		if (roleId == null || roleId <= 0) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError("roleId");
			return mes;
		}
		Integer res = roleDao.delRole(roleId);
		if (res > 0) {
			mes.setCode(Code.SUCCESS);
			mes.setMessage("delete role");
			mes.setResult(res);
		} else {
			mes.setCode(Code.OPTION_FAILED);
			mes.setError("delete role");
		}
		return mes;
	}

	private void checkRole(Role role, boolean update) throws MyException {
		if (role == null) {
			throw new MyException(Code.PARAM_NULL_ERROR, "role");
		}
		if (CharUtil.isEmpty(role.getRoleName())) {
			throw new MyException(Code.PARAM_NULL_ERROR, "roleName");
		}
		if (CharUtil.isEmpty(role.isState())) {
			role.setState(false);
		}
		if (update) {
			if (role.getId() == null || role.getId() <= 0) {
				throw new MyException(Code.PARAM_NULL_ERROR, "roleId");
			}
		}
	}
}
