package com.longteng.framework.util;

import com.longteng.framework.config.Constants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

public class Log {

	static {
		File directory = new File(Constants.LogProperty);
		String configPath = directory.getPath();

		System.out.println("Load Config fileName:" + directory);
		PropertyConfigurator.configure(configPath);
	}

	public static Logger getLogger(Class clazz) {
		return Logger.getLogger(clazz);
	}

	public static void main(String[] args) {
		Logger logger = Log.getLogger(Log.class);
		logger.info("longtengTest");
		System.out.println("longtengTest");
		logger.error("异常");
	}

}
