package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 原始记录导入日志表
 * @author tancx
 *
 */
public class SysAttendImportLog extends BaseModel {
	private Integer fdStatus = 0;
	private Date docCreateTime;
	private String fdResultMessage;
	private SysOrgPerson docCreator;

	@Override
	public Class getFormClass() {
		return null;
	}

	public Integer getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(Integer fdStatus) {
		this.fdStatus = fdStatus;
	}

	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}
	
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdResultMessage() {
		return fdResultMessage;
	}

	public void setFdResultMessage(String fdResultMessage) {
		this.fdResultMessage = fdResultMessage;
	}
	
	/**
	 * 修改时间
	 */
	private Date docAlterTime;

	/**
	 * @return 修改时间
	 */
	public Date getDocAlterTime() {
		return this.docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}
}
