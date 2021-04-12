package com.neayon.ht.bean;

import java.util.Date;

import com.neayon.ht.util.CharUtil;

/**
 * 
 * @author kamiyj
 */
public class PageBean {
	private boolean page = true;
	private Integer pageIndex = 1;
	private Integer pageSize = 20;
	private Integer pageStart;
	private Object param;
	private Date startTime;
	private Date endTime;
	private String orderBy;
	private boolean desc = false;

	public PageBean(boolean page, Integer pageIndex, Integer pageSize, Object param) {
		super();
		this.page = page;
		setPageBean(pageIndex, pageSize);
		this.param = param;
	}

	public PageBean(boolean page, Integer pageIndex, Integer pageSize, Object param, Date startTime, Date endTime) {
		super();
		this.page = page;
		setPageBean(pageIndex, pageSize);
		this.param = param;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public PageBean(Integer pageIndex, Integer pageSize, Object param, String orderBy, boolean desc) {
		super();
		this.page = true;
		setPageBean(pageIndex, pageSize);
		this.param = param;
		this.orderBy = orderBy;
		this.desc = desc;
	}

	public void setPageBean(Integer pageIndex, Integer pageSize) {
		if (!CharUtil.isEmpty(pageIndex) && pageIndex > 0) {
			setPageIndex(pageIndex);
		}
		if (!CharUtil.isEmpty(pageSize) && pageSize > 0) {
			setPageSize(pageSize);
		}
		setPageStart((getPageIndex() - 1) * getPageSize());
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getPageStart() {
		return pageStart;
	}

	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isDesc() {
		return desc;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
	}
}
