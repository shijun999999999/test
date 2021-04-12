package com.neayon.ht.manager;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.neayon.ht.bean.Message;
import com.neayon.ht.common.Code;
import com.neayon.ht.common.Constant;
import com.neayon.ht.dao.BaseDao;
import com.neayon.ht.dao.LogDao;
import com.neayon.ht.entity.Base;
import com.neayon.ht.entity.Log;
import com.neayon.ht.entity.User;
import com.neayon.ht.util.RedisUtil;

public abstract class BaseManager {
	@Autowired
	protected HttpServletRequest request;
	@Resource
	protected RedisUtil redis;
	@Autowired
	private LogDao logDao;
	@Autowired
	private BaseDao baseDao;

	/**
	 * 获取登录缓存用户
	 * 
	 * @return
	 */
	public User getUserStored() {
		Object userID = request.getHeader(Constant.Key.REQ_HEADER);
		if (userID == null) {
			userID = request.getParameter(Constant.Key.REQ_HEADER);
		}
		if (userID == null) {
			return null;
		}
		User user = (User) redis.get(Constant.Key.USER_SESSION_KEY + userID.toString());
		return user;
	}

	/**
	 * 保存浏览操作日志记录
	 * 
	 * @param username
	 * @param authCode
	 * @param remark
	 */
	public void saveLog(String username, String authCode, String remark) {
		logDao.saveLog(new Log(username, authCode, remark));
	}
	
	/**
	 * 获取基础参数
	 * @param type
	 * @return
	 */
	public Message getBases(String type) {
		Message mes = new Message();
		List<Base> bases = baseDao.fetchBaseParams(type);
		mes.setCode(Code.SUCCESS);
		mes.setMessage("base");
		mes.setResult(bases);
		return mes;
	}
	
	public String getBaseParam(String type, String memo) {
		Base base = new Base();
		base.setType(type);
		base.setMemo(memo);
		Base result = baseDao.fetchBaseParam(base);
		if(result != null) {
			return result.getValue();
		} else {
			return "";
		}
	}
}
