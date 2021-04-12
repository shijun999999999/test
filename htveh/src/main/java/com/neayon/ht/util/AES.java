package com.neayon.ht.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	/*
	 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
	 * 此处使用AES-128-CBC加密模式，key需要为16位。
	 */
	private static final String SKEY = "shly@~_^&&176_78";

	// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
	private static final String IVSTR = "0201080306050704";

	/**
	 * 加密
	 * 
	 * @Title: Encrypt
	 * @param sSrc
	 * @return
	 * @return: String
	 */
	public static String Encrypt(String sSrc) {
		try {
			if (sSrc == null || sSrc.length() < 2) {
				return null;
			}
			// 判断Key是否为16位
			if (SKEY.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = SKEY.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
			IvParameterSpec iv = new IvParameterSpec(IVSTR.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes());

			return encodeBytes(encrypted);
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * 解密
	 * 
	 * @Title: Decrypt
	 * @param sSrc
	 * @return
	 * @return: String
	 */
	public static String Decrypt(String sSrc) {
		try {
			// 判断Key是否正确
			if (sSrc == null || sSrc.length() < 16) {
				return null;
			}
			// 判断Key是否为16位
			if (SKEY.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = SKEY.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(IVSTR.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] encrypted1 = decodeBytes(sSrc);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "UTF-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}

	public static String encodeBytes(byte[] bytes) {
		StringBuffer strBuf = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ('a')));
			strBuf.append((char) (((bytes[i]) & 0xF) + ('a')));
		}

		return strBuf.toString();
	}

	public static byte[] decodeBytes(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length(); i += 2) {
			char c = str.charAt(i);
			bytes[i / 2] = (byte) ((c - 'a') << 4);
			c = str.charAt(i + 1);
			bytes[i / 2] += (c - 'a');
		}
		return bytes;
	}

}
