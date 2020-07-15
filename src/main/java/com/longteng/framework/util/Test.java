package com.longteng.framework.util;

import java.io.FileNotFoundException;

import com.longteng.framework.param.ExcelDataProvider;

public class Test {
	public static void main(String[] args) {
		DataUtil d = DataUtil.getInstance();
		System.out.println(d);
		DataUtil d2 = DataUtil.getInstance();

		System.out.println(d2);
		
		try {
			ExcelDataProvider e = new ExcelDataProvider(null);
			System.out.println(e);
			ExcelDataProvider e2 = new ExcelDataProvider(null);
			System.out.println(e2);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
