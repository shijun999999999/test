package com.neayon.ht.entity;

import java.util.Date;

public class Log {
	private String user;
	private String access;
	private String remark;
	private Date accessTime;
	
	public Log() {
		
	}
	
	public Log(String user, String access, String remark) {
		setUser(user);
		setAccess(access);
		setRemark(remark);
		setAccessTime(new Date());
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
}
