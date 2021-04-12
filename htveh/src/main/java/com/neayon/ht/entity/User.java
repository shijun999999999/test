package com.neayon.ht.entity;

import java.util.Date;

public class User {
	/** 初始密码 */
	public static final String INTIPWD = "111111";

	private Integer id;
	private String username;
	private String password;
	private Integer role;
	private String validate;
	private String cardNo;
	private String drivingLicense;
	private String drivingModel;
	private String drivingStart;
	private String drivingEnd;
	private Date createTime;
	private Date updateTime;
	private String functionPoint;

	public User() {

	}

	public User(String username, String password) {
		setUsername(username);
		setPassword(password);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getDrivingLicense() {
		return drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public String getDrivingModel() {
		return drivingModel;
	}

	public void setDrivingModel(String drivingModel) {
		this.drivingModel = drivingModel;
	}

	public String getDrivingStart() {
		return drivingStart;
	}

	public void setDrivingStart(String drivingStart) {
		this.drivingStart = drivingStart;
	}

	public String getDrivingEnd() {
		return drivingEnd;
	}

	public void setDrivingEnd(String drivingEnd) {
		this.drivingEnd = drivingEnd;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFunctionPoint() {
		return functionPoint;
	}

	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}

}
