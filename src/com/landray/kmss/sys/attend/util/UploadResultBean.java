package com.landray.kmss.sys.attend.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Excel表格处理结果Bean
 * 
 */
public class UploadResultBean {

	private int failCount = 0;// 失败个数
	private int successCount = 0;// 成功个数
	private int ignoreCount = 0;// 忽略个数
	private int duplicateCount = 0;// 重复个数
	private Map<Integer, String> errorMsgs = new HashMap<Integer, String>();// 错误信息
	
	public void addFailCount()
	{
		setFailCount(getFailCount()+1);
	}
	
	public void addSuccessCount()
	{
		setSuccessCount(getSuccessCount()+1);
	}
	
	public void addIgoreCount()
	{
		setIgnoreCount(getIgnoreCount()+1);
	}
	
	public void addErrorMsg(String msg)
	{
		errorMsgs.put(errorMsgs.size(), msg);
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
	 * @return ignoreCount
	 */
	public int getIgnoreCount() {
		return ignoreCount;
	}

	/**
	 * @param ignoreCount
	 *            要设置的 ignoreCount
	 */
	public void setIgnoreCount(int ignoreCount) {
		this.ignoreCount = ignoreCount;
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

	@Override
	public String toString() {
		return "{\"successCount\":"+getSuccessCount()+",\"ignoreCount\":"+getIgnoreCount()+",\"failCount\":"+getFailCount()+",\"duplicateCount\":"+getDuplicateCount()+"}";
	}

	public int getDuplicateCount() {
		return duplicateCount;
	}

	public void setDuplicateCount(int duplicateCount) {
		this.duplicateCount = duplicateCount;
	}
}
