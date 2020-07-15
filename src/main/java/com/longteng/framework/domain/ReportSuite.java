package com.longteng.framework.domain;

import java.util.ArrayList;
import java.util.List;

public class ReportSuite {

	private String suiteName;
	private String suiteType;
	private List<ReportCase> reportCaseList = new ArrayList<ReportCase>();

	public String getSuiteName() {
		return suiteName;
	}

	public String getSuiteType() {
		return suiteType;
	}

	public void setSuiteType(String suiteType) {
		this.suiteType = suiteType;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}

	public void addReportCase(ReportCase reportCase) {
		reportCaseList.add(reportCase);
	}

	public List<ReportCase> getReportCaseList() {
		return reportCaseList;
	}

	public void setReportCaseList(List<ReportCase> reportCaseList) {
		this.reportCaseList = reportCaseList;
	}
}
