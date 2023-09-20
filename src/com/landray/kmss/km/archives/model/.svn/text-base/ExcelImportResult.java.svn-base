package com.landray.kmss.km.archives.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.util.ArrayUtil;

import net.sf.json.JSONObject;

public class ExcelImportResult {

	private List<String> titles = new ArrayList<String>(); // 标题头

	private List<ErrorRowInfo> errorRows = new ArrayList<ErrorRowInfo>(); // 每个错误行（包含错误行号，错误列号，行的错误信息）

	private List<String> otherErrors = new ArrayList<String>(); // 其他错误

	private int rowIndex; // 行号
	// 导入详情
	private String importMsg;

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<ErrorRowInfo> getErrorRows() {
		return errorRows;
	}

	public void setErrorRows(List<ErrorRowInfo> errorRows) {
		this.errorRows = errorRows;
	}

	public List<String> getOtherErrors() {
		return otherErrors;
	}

	public void setOtherErrors(List<String> otherErrors) {
		this.otherErrors = otherErrors;
	}

	public String getImportMsg() {
		return importMsg;
	}

	public void setImportMsg(String importMsg) {
		this.importMsg = importMsg;
	}

	public boolean isError() {
		return !ArrayUtil.isEmpty(errorRows) || !ArrayUtil.isEmpty(otherErrors);
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}
