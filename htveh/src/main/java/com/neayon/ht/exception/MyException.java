package com.neayon.ht.exception;

public class MyException extends Exception {
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String e;
	
	public MyException(String e) {
		this.setE(e);
	}
	
	public MyException(Integer code, String e) {
		this.setCode(code);
		this.setE(e);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}
}
