package com.landray.kmss.km.archives.model;

import java.util.ArrayList;
import java.util.List;

public class ErrorRowInfo {
	/**
	 * 错误行号
	 */
	private int errRowNumber;
	/**
	 * 错误列号集合
	 */
	private List<Integer> errColNumbers = new ArrayList<Integer>();
	/**
	 * 错误信息集合
	 */
	private List<String> errInfos = new ArrayList<String>();
	/**
	 * 原数据行
	 */
	private List<String> contents = new ArrayList<String>();

	private boolean otherError = false;

	private boolean error = false;

	public int getErrRowNumber() {
		return errRowNumber;
	}

	public void setErrRowNumber(int errRowNumber) {
		this.errRowNumber = errRowNumber;
	}

	/**
	 * 请勿调用，仅作为json输出
	 * 
	 * @return
	 */
	public List<Integer> getErrColNumbers() {
		return errColNumbers;
	}

	public void setErrColNumbers(List<Integer> errColNumbers) {
		this.errColNumbers = errColNumbers;
	}

	/**
	 * 请勿调用，仅作为json输出
	 * 
	 * @return
	 */
	public List<String> getErrInfos() {
		return errInfos;
	}

	public void setErrInfos(List<String> errInfos) {
		this.errInfos = errInfos;
	}

	public List<String> getContents() {
		return contents;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	/**
	 * 添加错误--必须用addError
	 * 
	 * @param rowIndex
	 *            行号
	 * @param colIndex
	 *            列号
	 * @param msg
	 *            错误信息
	 */
	public void addError(int rowIndex, int colIndex, String msg) {
		this.error = true;
		this.errRowNumber = rowIndex;
		this.errColNumbers.add(colIndex);
		this.errInfos.add(msg);
	}

	public void setOtherError(boolean otherError) {
		this.otherError = otherError;
	}

	public boolean hasOtherError() {
		return otherError;
	}
	
	public boolean isError() {
		return error;
	}
}
