package com.longteng.framework.config;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	private static InputStream in;
	private static Properties properties;
	private static Logger logger = Logger.getLogger(Config.class);

	static {
		try {
			in = new FileInputStream(Constants.config);
		} catch (FileNotFoundException e) {
			logger.error("加载config配置文件异常", e);
		}
		properties = new Properties();
		load();

	}

	/**
	 * 载入配置文件config.properties，从输入流中读取属性列表
	 * 
	 * @param path
	 */
	public Config(String path) {

	}

	/**
	 * 将输入流加载到属性文件中
	 */
	private static void load() {
		try {
			properties.load(in);
		} catch (Exception e) {
			logger.error("载入配置文件异常", e);
		}
	}

	/**
	 * 根据属性文件中的key，获取属性文件中相应的value
	 * 
	 * @param strName
	 * @return
	 */
	public static String get(String strName) {
		String values = "";
		if (properties.getProperty(strName) != null) {
			values = properties.getProperty(strName).trim();
		}
		return values;
	}
}
