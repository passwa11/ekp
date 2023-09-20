package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.model.BaseModel;

/**
 * 考勤记录备份日志
 *
 * @author cuiwj
 * @version 1.0 2019-02-21
 */
public class SysAttendMainBakLog extends BaseModel {

	private String fdYear;

	private String fdCreateTime;

	private String fdOprTime;

	private String fdTableName;

	public String getFdYear() {
		return fdYear;
	}

	public void setFdYear(String fdYear) {
		this.fdYear = fdYear;
	}

	public String getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public String getFdOprTime() {
		return fdOprTime;
	}

	public void setFdOprTime(String fdOprTime) {
		this.fdOprTime = fdOprTime;
	}

	public String getFdTableName() {
		return fdTableName;
	}

	public void setFdTableName(String fdTableName) {
		this.fdTableName = fdTableName;
	}

	@Override
	public Class getFormClass() {
		return null;
	}

}
