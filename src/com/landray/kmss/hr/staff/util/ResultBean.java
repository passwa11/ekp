package com.landray.kmss.hr.staff.util;

import java.util.HashMap;
import java.util.Map;

public class ResultBean {
	private int failCount = 0;// 失败个数
	private int successCount = 0;// 成功个数
	private Map<Integer, String> errorMsgs = new HashMap<Integer, String>();// 错误信息

	public void addFailCount() {
		setFailCount(getFailCount() + 1);
	}

	public void addSuccessCount() {
		setSuccessCount(getSuccessCount() + 1);
	}

	/**
	 * @return failCount
	 */
	public int getFailCount() {
		return failCount;
	}

	/**
	 * @param failCount
	 *            要设置的 failCount
	 */
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	/**
	 * @return successCount
	 */
	public int getSuccessCount() {
		return successCount;
	}

	/**
	 * @param successCount
	 *            要设置的 successCount
	 */
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	/**
	 * @return errorMsgs
	 */
	public Map<Integer, String> getErrorMsgs() {
		return errorMsgs;
	}

	/**
	 * @param errorMsgs
	 *            要设置的 errorMsgs
	 */
	public void setErrorMsgs(Map<Integer, String> errorMsgs) {
		this.errorMsgs = errorMsgs;
	}
	
}
