package com.neayon.ht.util;

/**
 * 字符处理工具
 */
public class CharUtil {
	private static String regex = "(.{2})";
	private static String pl = "$1-";
	
	/**
	 * 判断对象是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if(obj != null && obj instanceof String){
			obj = obj.toString().trim();
		}
		if (obj != null && !"".equals(obj) && !"null".equals(obj)) {
			return false;
		}
		return true;
	}

	/**
	 * 对象转字符串
	 * @param obj 对象
	 * @return 字符串
	 */
	public static String objectToString(Object obj) {
		if (isEmpty(obj)) {
			return "";
		} else {
			return obj.toString();
		}
	}

	/**
	 * 对象null转空字符串
	 * @param obj
	 * @return
	 */
	public static Object nullToString(Object obj) {
		if (isEmpty(obj)) {
			return "";
		} else {
			return obj;
		}
	}
	
	/**
	 * 将数组用指定的连接符拼接成字符串
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String concatString(Object[] array, String separator) {
		String result = "";
		for (Object obj : array) {
			if (isEmpty(result)) {
				result = obj.toString();
			} else {
				result += "," + obj.toString();
			}
		}
		return result;
	}

	/**
	 * 判断制定字符串是否存在于另一个字符串内
	 * @param s
	 * @param array
	 * @return
	 */
	public static boolean isInString(String s, String array) {
		boolean flag = false;
		if (isEmpty(array) || isEmpty(s)) {
			flag = false;
		} else {
			String[] data = array.split(",");
			for (String temp : data) {
				if (s.trim().equals(temp.trim())) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}


	/**
	 * 判断最多连续字个数
	 * @param str 字符串
	 * @return 连续字个数
	 */
	public static int continuousCount(String str) {
		int count = 1;
		int temCount = 1;
		for (int i = 0; i < str.length(); i++) {
			for (int j = i + 1; j < str.length(); j++) {
				if (str.charAt(j) == str.charAt(i)) {
					temCount++;
				} else {
					if (temCount > count) {
						count = temCount;
						temCount = 0;
					}
					i = j - 1;
					break;
				}
			}
		}
		return count;
	}

	/**
	 * 格式化字符串为多行
	 * @param str 需格式化字符串
	 * @param len 单行字符长度
	 * @return
	 */
	public static String formatStrToRows(String str, int len) {
		String res = "";
		if (!isEmpty(str)) {
			char[] cs = str.replaceAll("\\s{2,}", " ").toCharArray();// 合并空格并转化为字符数组
			for (int k = 0; k < cs.length; k++) {
				if (k != 0 && k != (cs.length - 1) && k % len == 0) {
					res += "\n" + cs[k];
				} else {
					res += cs[k];
				}
			}
		}
		return res;
	}
	
	public static Float MathRound(Float f) {
		return (float) (Math.round(f * 100)) / 100;
	}
	
	public static Float MathRound1(Float f) {
		return (float) (Math.round(f * 10)) / 10;
	}
	
	public static String toMyStr(String source) {
		String res = source.toUpperCase();
		res = res.replaceAll(regex, pl);
		res = res.substring(0, res.length() - 1);
		return res;
	}
	
	public static String fromMyStr(String md5) {
		String res = md5.replace("-", "");
		res = res.toLowerCase();
		return res;
	}
	
	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
	
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}
	
	public static String byte2HexOfString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
}
