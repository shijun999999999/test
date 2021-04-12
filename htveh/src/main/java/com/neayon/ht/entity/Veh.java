package com.neayon.ht.entity;

import java.util.Date;

public class Veh {
	// 车辆状态
	/** 已登记 */
	public static final int STATE_LOGED = 1;
	/** 全部项目完成/待支付 */
	public static final int STATE_UNPAY = 2;
	/** 已支付/待签证下发 */
	public static final int STATE_PAYED = 3;
	/** 待结算 */
	public static final int STATE_AFPAY = 4;
	/** 签证下发/车辆结束 */
	public static final int STATE_OVERD = 5;
	/** 退办 */
	public static final int STATE_WITHD = 6;
	// 车辆安检状态
	/** 已登记 */
	public static final int SF_STATE_LOGED = 1;
	/** 外观检测中 */
	public static final int SF_STATE_EXING = 2;
	/** 外观检测结束 */
	public static final int SF_STATE_EXOVR = 3;
	/** 底盘检测中 */
	public static final int SF_STATE_CHING = 4;
	/** 底盘检测结束 */
	public static final int SF_STATE_CHOVR = 5;
	/** 底盘动态检测中 */
	public static final int SF_STATE_DYING = 6;
	/** 底盘动态检测结束 */
	public static final int SF_STATE_DYOVR = 7;
	/** 上线检测中 */
	public static final int SF_STATE_ULING = 8;
	/** 下线结束 */
	public static final int SF_STATE_DLOVR = 9;
	/** 外廓检测中 */
	public static final int SF_STATE_WKING = 10;
	/** 外廓检测结束 */
	public static final int SF_STATE_WKOVR = 11;
	/** 整备检测中 */
	public static final int SF_STATE_ZBING = 12;
	/** 整备检测结束 */
	public static final int SF_STATE_ZBOVR = 13;
	/** 路试中 */
	public static final int SF_STATE_RDING = 14;
	/** 路试结束 */
	public static final int SF_STATE_RDOVR = 15;
	/** 安检流程结束 */
	public static final int SF_STATE_SFOVR = 16;
	// 车辆环检状态
	/** 已登记 */
	public static final int EV_STATE_LOGED = 1;
	/** 环检检测中 */
	public static final int EV_STATE_CKING = 2;
	/** 环检结束 */
	public static final int EV_STATE_CKOVR = 3;
	// 车辆其他状态
	/** 正常 */
	public static final int TIP_STATE_NORMAL = 1;
	/** 维修中 */
	public static final int TIP_STATE_REPAIR = 2;
	/** 预登记 */
	public static final int TIP_STATE_PREORD = 3;

	private int id;
	private String sn;
	private String sfsn;
	private String envsn;
	private String plateNumber;
	private String plateType;
	private String vehType;
	private String vin;
	private String insOrgNumber;
	private String owner;
	private String ownerContact;
	private String applicant;
	private String applicantContact;
	private String safetyInspects;
	private Date safetyStart;
	private String externalInspector;
	private Date externalStart;
	private Date externalEnd;
	private String chassisInspector;
	private Date chassisStart;
	private Date chassisEnd;
	private String dynamicInspector;
	private Date dynamicStart;
	private Date dynamicEnd;
	private String guide;
	private Date guideStart;
	private Date guideEnd;
	private Date safetyEnd;
	private int frequencySafety;
	private String conclusionSafety;
	private int stateSafety;
	private String envInspector;
	private String envInspects;
	private Date envStart;
	private Date envEnd;
	private int frequencyEnv;
	private String conclusionEnv;
	private int stateEnv;
	private int tipstate;
	private int state;
	private int createUser;
	private Date createTime;
	private int updateUser;
	private Date updateTime;
	private int countsWaiting;

	public Veh() {

	}

	public Veh(String guide, String plateNumber, int countsWaiting) {
		setGuide(guide);
		setPlateNumber(plateNumber);
		setCountsWaiting(countsWaiting);
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

	public String getSfsn() {
		return sfsn;
	}

	public void setSfsn(String sfsn) {
		this.sfsn = sfsn;
	}

	public String getEnvsn() {
		return envsn;
	}

	public void setEnvsn(String envsn) {
		this.envsn = envsn;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getPlateType() {
		return plateType;
	}

	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}

	public String getVehType() {
		return vehType;
	}

	public void setVehType(String vehType) {
		this.vehType = vehType;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getInsOrgNumber() {
		return insOrgNumber;
	}

	public void setInsOrgNumber(String insOrgNumber) {
		this.insOrgNumber = insOrgNumber;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwnerContact() {
		return ownerContact;
	}

	public void setOwnerContact(String ownerContact) {
		this.ownerContact = ownerContact;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getApplicantContact() {
		return applicantContact;
	}

	public void setApplicantContact(String applicantContact) {
		this.applicantContact = applicantContact;
	}

	public String getSafetyInspects() {
		return safetyInspects;
	}

	public void setSafetyInspects(String safetyInspects) {
		this.safetyInspects = safetyInspects;
	}

	public Date getSafetyStart() {
		return safetyStart;
	}

	public void setSafetyStart(Date safetyStart) {
		this.safetyStart = safetyStart;
	}

	public String getExternalInspector() {
		return externalInspector;
	}

	public void setExternalInspector(String externalInspector) {
		this.externalInspector = externalInspector;
	}

	public Date getExternalStart() {
		return externalStart;
	}

	public void setExternalStart(Date externalStart) {
		this.externalStart = externalStart;
	}

	public Date getExternalEnd() {
		return externalEnd;
	}

	public void setExternalEnd(Date externalEnd) {
		this.externalEnd = externalEnd;
	}

	public String getChassisInspector() {
		return chassisInspector;
	}

	public void setChassisInspector(String chassisInspector) {
		this.chassisInspector = chassisInspector;
	}

	public Date getChassisStart() {
		return chassisStart;
	}

	public void setChassisStart(Date chassisStart) {
		this.chassisStart = chassisStart;
	}

	public Date getChassisEnd() {
		return chassisEnd;
	}

	public void setChassisEnd(Date chassisEnd) {
		this.chassisEnd = chassisEnd;
	}

	public String getDynamicInspector() {
		return dynamicInspector;
	}

	public void setDynamicInspector(String dynamicInspector) {
		this.dynamicInspector = dynamicInspector;
	}

	public Date getDynamicStart() {
		return dynamicStart;
	}

	public void setDynamicStart(Date dynamicStart) {
		this.dynamicStart = dynamicStart;
	}

	public Date getDynamicEnd() {
		return dynamicEnd;
	}

	public void setDynamicEnd(Date dynamicEnd) {
		this.dynamicEnd = dynamicEnd;
	}

	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	public Date getGuideStart() {
		return guideStart;
	}

	public void setGuideStart(Date guideStart) {
		this.guideStart = guideStart;
	}

	public Date getGuideEnd() {
		return guideEnd;
	}

	public void setGuideEnd(Date guideEnd) {
		this.guideEnd = guideEnd;
	}

	public Date getSafetyEnd() {
		return safetyEnd;
	}

	public void setSafetyEnd(Date safetyEnd) {
		this.safetyEnd = safetyEnd;
	}

	public int getFrequencySafety() {
		return frequencySafety;
	}

	public void setFrequencySafety(int frequencySafety) {
		this.frequencySafety = frequencySafety;
	}

	public String getConclusionSafety() {
		return conclusionSafety;
	}

	public void setConclusionSafety(String conclusionSafety) {
		this.conclusionSafety = conclusionSafety;
	}

	public int getStateSafety() {
		return stateSafety;
	}

	public void setStateSafety(int stateSafety) {
		this.stateSafety = stateSafety;
	}

	public String getEnvInspector() {
		return envInspector;
	}

	public void setEnvInspector(String envInspector) {
		this.envInspector = envInspector;
	}

	public String getEnvInspects() {
		return envInspects;
	}

	public void setEnvInspects(String envInspects) {
		this.envInspects = envInspects;
	}

	public Date getEnvStart() {
		return envStart;
	}

	public void setEnvStart(Date envStart) {
		this.envStart = envStart;
	}

	public Date getEnvEnd() {
		return envEnd;
	}

	public void setEnvEnd(Date envEnd) {
		this.envEnd = envEnd;
	}

	public int getFrequencyEnv() {
		return frequencyEnv;
	}

	public void setFrequencyEnv(int frequencyEnv) {
		this.frequencyEnv = frequencyEnv;
	}

	public String getConclusionEnv() {
		return conclusionEnv;
	}

	public void setConclusionEnv(String conclusionEnv) {
		this.conclusionEnv = conclusionEnv;
	}

	public int getStateEnv() {
		return stateEnv;
	}

	public void setStateEnv(int stateEnv) {
		this.stateEnv = stateEnv;
	}

	public int getTipstate() {
		return tipstate;
	}

	public void setTipstate(int tipstate) {
		this.tipstate = tipstate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(int updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getCountsWaiting() {
		return countsWaiting;
	}

	public void setCountsWaiting(int countsWaiting) {
		this.countsWaiting = countsWaiting;
	}
}
