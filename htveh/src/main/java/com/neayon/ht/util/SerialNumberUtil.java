package com.neayon.ht.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SerialNumberUtil {
	public static final String SN = "HTSN";
	public static final String ORDER_SN = "HTOS";
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String createSn(String header) {
		String sn = header + sdf.format(new Date()) + RandomUtils.getRandomNumbers(4);
		return sn;
	}
	
	public static void main(String[] args) {
		System.out.println(SerialNumberUtil.createSn(SN));
	}
}
