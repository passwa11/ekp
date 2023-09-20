package com.landray.kmss.common.dto;

import java.util.List;

/**
 * 设计信息打包，用于批量更新<br>
 * saveList中的appName将会被这里的appName覆盖
 * 
 * @author 叶中奇
 */
public class DesignElementGroup {
	private String fdAppName;

	private List<DesignElementDetail> saveList;

	private List<String> deleteList;

	public String getFdAppName() {
		return fdAppName;
	}

	public void setFdAppName(String fdAppName) {
		this.fdAppName = fdAppName;
	}

	public List<DesignElementDetail> getSaveList() {
		return saveList;
	}

	public void setSaveList(List<DesignElementDetail> saveList) {
		this.saveList = saveList;
	}

	public List<String> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<String> deleteList) {
		this.deleteList = deleteList;
	}
}
