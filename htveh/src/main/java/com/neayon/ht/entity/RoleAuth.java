package com.neayon.ht.entity;

public class RoleAuth {
	private Integer id;
	private String gp;
	private String gpdesc;
	private String functionPoint;
	private String funcdesc;
	private boolean state;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGp() {
		return gp;
	}

	public void setGp(String gp) {
		this.gp = gp;
	}

	public String getGpdesc() {
		return gpdesc;
	}

	public void setGpdesc(String gpdesc) {
		this.gpdesc = gpdesc;
	}

	public String getFunctionPoint() {
		return functionPoint;
	}

	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}

	public String getFuncdesc() {
		return funcdesc;
	}

	public void setFuncdesc(String funcdesc) {
		this.funcdesc = funcdesc;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

}
