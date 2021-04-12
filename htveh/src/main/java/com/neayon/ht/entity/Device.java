package com.neayon.ht.entity;

import java.util.Date;

public class Device {
	/** 制动设备 */
	public static final int DEVICE_BRAKE = 1;
	/** 灯光设备 */
	public static final int DEVICE_LAMP = 2;
	/** 速度设备 */
	public static final int DEVICE_SPEED = 3;
	/** 侧滑设备 */
	public static final int DEVICE_SIDES = 4;
	/** 称重设备 */
	public static final int DEVICE_WEIGHT = 5;
	/** 平板设备 */
	public static final int DEVICE_FLAT = 6;
	/** 外廓仪 */
	public static final int DEVICE_OVERSIZE = 7;
	/** 光电开关 */
	public static final int DEVICE_SWITCH = 90;
	/** 点阵屏 */
	public static final int DEVICE_DOTSCREEN = 91;
	/** 遥控器 */
	public static final int DEVICE_REMOTE = 92;
	/** 刷卡器 */
	public static final int DEVICE_CARD = 93;

	public static final int CONNECT_TCP = 1;
	public static final int CONNECT_COM = 2;

	private int id;
	private String name;
	private String manufacturer;
	private String info;
	private String directions;
	private String shots;
	private Date repairDate;
	private String repairShot;
	private Date standrDate;
	private String standrShot;
	private int deviceType;
	private String decoder;
	private int connectType;
	private String com;
	private int rate;
	private int databits;
	private int checkbits;
	private int stopbits;
	private String host;
	private int dataport;
	private int orderport;
	private String others;
	private boolean state;
	private Date createTime;
	private Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	public String getShots() {
		return shots;
	}

	public void setShots(String shots) {
		this.shots = shots;
	}

	public Date getRepairDate() {
		return repairDate;
	}

	public void setRepairDate(Date repairDate) {
		this.repairDate = repairDate;
	}

	public String getRepairShot() {
		return repairShot;
	}

	public void setRepairShot(String repairShot) {
		this.repairShot = repairShot;
	}

	public Date getStandrDate() {
		return standrDate;
	}

	public void setStandrDate(Date standrDate) {
		this.standrDate = standrDate;
	}

	public String getStandrShot() {
		return standrShot;
	}

	public void setStandrShot(String standrShot) {
		this.standrShot = standrShot;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public String getDecoder() {
		return decoder;
	}

	public void setDecoder(String decoder) {
		this.decoder = decoder;
	}

	public int getConnectType() {
		return connectType;
	}

	public void setConnectType(int connectType) {
		this.connectType = connectType;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getDatabits() {
		return databits;
	}

	public void setDatabits(int databits) {
		this.databits = databits;
	}

	public int getCheckbits() {
		return checkbits;
	}

	public void setCheckbits(int checkbits) {
		this.checkbits = checkbits;
	}

	public int getStopbits() {
		return stopbits;
	}

	public void setStopbits(int stopbits) {
		this.stopbits = stopbits;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getDataport() {
		return dataport;
	}

	public void setDataport(int dataport) {
		this.dataport = dataport;
	}

	public int getOrderport() {
		return orderport;
	}

	public void setOrderport(int orderport) {
		this.orderport = orderport;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
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
