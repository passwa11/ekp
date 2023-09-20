package com.landray.kmss.third.pda.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;

public class PdaMessagePushInfo extends BaseModel {

	/**
	 * 待办id
	 */
	private String fdNotifyId;

	/**
	 * 是否有效
	 */
	private String fdAvailable;

	/**
	 * 是否已推送
	 */
	private String fdHasPushed;

	/**
	 * 创建时间
	 */
	private Date fdCreateTime;

	/**
	 * 需要推送的人员
	 */
	private SysOrgElement fdPerson;

	public String getFdNotifyId() {
		return fdNotifyId;
	}

	public void setFdNotifyId(String fdNotifyId) {
		this.fdNotifyId = fdNotifyId;
	}

	public String getFdAvailable() {
		return fdAvailable;
	}

	public void setFdAvailable(String fdAvailable) {
		this.fdAvailable = fdAvailable;
	}

	public String getFdHasPushed() {
		return fdHasPushed;
	}

	public void setFdHasPushed(String fdHasPushed) {
		this.fdHasPushed = fdHasPushed;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public SysOrgElement getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgElement fdPerson) {
		this.fdPerson = fdPerson;
	}

	@Override
    public Class getFormClass() {
		return PdaMessagePushInfo.class;
	}

}
