package com.neayon.ht.entity;

import java.util.Date;

public class PayModel {
	/** 默认付费模板金额，单位：分 */
	public static int DEFAULT_PAY = 1000;
	
	private Integer id;
	private String title;
	private String vehType;
	private String inspection;
	private Integer costOriginal;
	private Integer costActual;
	private boolean state;
	private Integer createUser;
	private Date createTime;
	private Integer updateUser;
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVehType() {
		return vehType;
	}

	public void setVehType(String vehType) {
		this.vehType = vehType;
	}

	public String getInspection() {
		return inspection;
	}

	public void setInspection(String inspection) {
		this.inspection = inspection;
	}

	public Integer getCostOriginal() {
		return costOriginal;
	}

	public void setCostOriginal(Integer costOriginal) {
		this.costOriginal = costOriginal;
	}

	public Integer getCostActual() {
		return costActual;
	}

	public void setCostActual(Integer costActual) {
		this.costActual = costActual;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
