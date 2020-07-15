package com.longteng.testcase;

import com.longteng.framework.asserts.AssertUtil;
import com.longteng.framework.dao.DBUtil;
import com.longteng.framework.util.HttpClientUtil;
import com.longteng.testcase.TestCase;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class InterfaceTest extends TestCase {
	// 方法内的入参是什么样的类型 取决于ExcelDataProvider返回的什么类型
	@Test(dataProvider = "data")
	public void runInterfaceTest(Map<String, String> stringStringMap) {
		String caseName = stringStringMap.get("用例名称");
		String url = stringStringMap.get("接口地址(名称)");
		String methodName = stringStringMap.get("方法");
		String param = stringStringMap.get("入参");
		String assertType = stringStringMap.get("断言类型");
		String expected = stringStringMap.get("预期结果");
		String isEnable = stringStringMap.get("是否执行");
		String sql = stringStringMap.get("SQL语句");
		String sqlExpected = stringStringMap.get("SQL预期结果");

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("url", url);
		paramMap.put("methodName", methodName);
		paramMap.put("paramBody", param);
		paramMap.put("contentType", "application/json");


		
		Map<String, String> resultMap = null;
		if ("是".equals(isEnable)) {
			resultMap = HttpClientUtil.request(paramMap, null);

			String actual = resultMap.get("returnBody");
			if (null == assertType || assertType.equalsIgnoreCase("全部比对")) {
				AssertUtil.assertEquals(actual, expected, caseName);
			} else if (assertType.equalsIgnoreCase("包含")) {
				AssertUtil.assertContains(actual, expected, caseName);
			}

			if (null != sql && !"".equals(sql)) {
				String sqlActual = null;
				try {
					sqlActual = DBUtil.excuteQeury(sql);
				} catch (Exception e) {
					logger.error("", e);
				}
				AssertUtil.assertEquals(sqlActual, sqlExpected, caseName);
			}
		}
	}
}
