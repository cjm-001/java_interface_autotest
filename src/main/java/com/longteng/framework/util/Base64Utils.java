package com.longteng.framework.util;

import java.io.IOException;
import java.lang.reflect.Method;

public class Base64Utils {
	/***
	 * encode by Base64
	 */
	public static String encodeBase64(byte[] input) {
		try {
			Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
			Method mainMethod = clazz.getMethod("encode", byte[].class);
			mainMethod.setAccessible(true);
			Object retObj = mainMethod.invoke(null, new Object[] { input });
			return (String) retObj;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/***
	 * decode by Base64
	 */
	public static byte[] decodeBase64(String input) {
		try {
			Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
			Method mainMethod = clazz.getMethod("decode", String.class);
			mainMethod.setAccessible(true);
			Object retObj = mainMethod.invoke(null, input);
			return (byte[]) retObj;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(Base64Utils.encodeBase64("longtengtest".getBytes()));

		System.out.println(new String(Base64Utils.decodeBase64("bG9uZ3Rlbmd0ZXN0")));
		

	}
}
