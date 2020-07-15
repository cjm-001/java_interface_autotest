package com.longteng.main;

import com.longteng.framework.config.Config;
import com.longteng.framework.config.Constants;
import com.longteng.framework.dao.DBUtil;
import com.longteng.framework.domain.Report;
import com.longteng.framework.domain.ReportSuite;
import com.longteng.framework.listener.MyTestListener;
import com.longteng.framework.mail.EmailClient;
import com.longteng.framework.report.template.ReportHtml;
import com.longteng.framework.suite.LoadSuite;
import com.longteng.framework.util.DataUtil;
import com.longteng.framework.util.FileUtil;
import com.longteng.framework.util.Log;
import org.apache.log4j.Logger;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.uncommons.reportng.HTMLReporter;

import java.util.*;

public class RunTest {
	private static DataUtil data = DataUtil.getInstance();
	private static Report report = Report.getInstance();
	private static Logger logger = Log.getLogger(RunTest.class);
	public static Config config = new Config(Constants.config);

	private static List<XmlSuite> xmlSuiteList = loadSuite();

	public void run() {

		TestNG testng = new TestNG();
		testng.addListener(new MyTestListener()); // 加入监听
		testng.setUseDefaultListeners(false); // 判断是否生成testNg自带报告
		// 设置reportNG
		if ("1".equals(Config.get("REPORT_PRIORITY"))) {
			List<Class> listenerClass = new ArrayList<Class>();
			listenerClass.add(HTMLReporter.class);
			testng.setListenerClasses(listenerClass);
		}
		testng.setOutputDirectory(Constants.Out_Dir); // 设置报告输出路径
		testng.setXmlSuites(xmlSuiteList); // 设置运行套件
		try {
			long startTime = System.currentTimeMillis();
			report.setStartTime(startTime);
			logger.info("开始所有运行测试!");

			this.initDB();//初始化数据库
			testng.run();
			logger.info("所有测试运行结束!");
			long entTime = System.currentTimeMillis();
			report.setStartTime(startTime);
			long runTime = entTime - startTime;
			report.setRunTime((runTime / 1000) + "秒");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		FileUtil.DeleteFolder("report", false);
		List<ReportSuite> reportSuiteList = report.getReportSuiteList();
		ReportHtml.writeReport(reportSuiteList);
		try {
			String sendTo = Config.get("sendTo");
			if (!"".equalsIgnoreCase(sendTo)) {
				EmailClient email = new EmailClient();
				logger.info("开始发送邮件!");
				// SendEmail.sendEmail();
				email.doSendAffixEmail("测试报告", "测试结果", "56562370@qq.com", "report\\report.html");
				logger.info("发送邮件完毕!");
			} else {
				logger.info("没有收件人信息不发送邮件!");
			}
		} catch (Exception e) {
			logger.error("发送邮件异常:" + e);
			e.printStackTrace();
		}

		logger.info("工程执行结束!");
	}

	public static void main(String[] args) {
		RunTest runTest = new RunTest();
		runTest.run();
	}

	/**
	 * 加载测试套件
	 *
	 * @return
	 */
	private static List<XmlSuite> loadSuite() {
		logger.info("开始加载测试场景!");
		xmlSuiteList = LoadSuite.getXmlSuites();
		logger.info("加载测试场景结束!");
		return xmlSuiteList;
	}

	/**
	 * 获取config.properties属性配置
	 *
	 * @return
	 */
	public static Config getConfig() {
		return config;
	}

	private void initDB() {
		String initSql = "update cardinfo set cardbalance = 0,cardstatus=null,userid=null where cardNumber = '22223333'";
		DBUtil.excuteUpdate(initSql);
		
		

	}

}
