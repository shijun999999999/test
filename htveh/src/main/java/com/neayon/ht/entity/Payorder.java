package com.neayon.ht.entity;

import java.util.Date;

public class Payorder {
	/** 已创建/待支付 */
	public static final int STATE_UNPAY = 1;
	/** 已支付/待签证 */
	public static final int STATE_PAYED = 2;
	/** 赊账/待签证 */
	public static final int STATE_AFPAY = 3;
	/** 已签发/完成 */
	public static final int STATE_ISSUE = 4;

	private int id;
	private String sn;
	private String orderSn;
	// {"safe":{"t1":"F1,UQ,NC,...","t2":"...",...},"env":{"t1":"...",...}}
	private String inspectsOriginal;
	private int feeOriginal;
	private String inspectsActual;
	private int feeActual;
	private int discount;
	private String discountRemark;
	private String createUser;
	private String confirmUser;
	private String issueUser;
	private int viper;
	private int state;
	private Date createTime;
	private Date updateTime;

	public Payorder() {
		setFeeOriginal(0);
		setFeeActual(0);
		setDiscount(0);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getInspectsOriginal() {
		return inspectsOriginal;
	}

	public void setInspectsOriginal(String inspectsOriginal) {
		this.inspectsOriginal = inspectsOriginal;
	}

	public int getFeeOriginal() {
		return feeOriginal;
	}

	public void setFeeOriginal(int feeOriginal) {
		this.feeOriginal = feeOriginal;
	}

	public String getInspectsActual() {
		return inspectsActual;
	}

	public void setInspectsActual(String inspectsActual) {
		this.inspectsActual = inspectsActual;
	}

	public int getFeeActual() {
		return feeActual;
	}

	public void setFeeActual(int feeActual) {
		this.feeActual = feeActual;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getDiscountRemark() {
		return discountRemark;
	}

	public void setDiscountRemark(String discountRemark) {
		this.discountRemark = discountRemark;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	public String getIssueUser() {
		return issueUser;
	}

	public void setIssueUser(String issueUser) {
		this.issueUser = issueUser;
	}

	public int getViper() {
		return viper;
	}

	public void setViper(int viper) {
		this.viper = viper;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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
}
