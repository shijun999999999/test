package com.neayon.ht.common;

public class SocketKey {
	public static final String IMPORTANT = "SOKEY$";
	public static final int PAY = 1;
	
	public static String toPay() {
		return IMPORTANT + PAY;
	}
}
