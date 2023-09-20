package com.landray.kmss.third.pda.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;

public class PdaMessagePushMember extends BaseModel {

	/**
	 * 手机设备deviceToken信息
	 */
	private String fdDeviceToken;

	/**
	 * 状态，是否启用
	 */
	private String fdStatus;

	/**
	 * 设备对应人
	 */
	private SysOrgElement fdPerson;
	
	/**
	 * 设备类型
	 */
	private String fdDeviceType;

	/**
	 * 创建时间
	 */
	private Date fdCreateTime;

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	public SysOrgElement getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgElement fdPerson) {
		this.fdPerson = fdPerson;
	}

	public String getFdDeviceToken() {
		return fdDeviceToken;
	}

	public void setFdDeviceToken(String fdDeviceToken) {
		this.fdDeviceToken = fdDeviceToken;
	}

	public String getFdDeviceType() {
		return fdDeviceType;
	}

	public void setFdDeviceType(String fdDeviceType) {
		this.fdDeviceType = fdDeviceType;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	@Override
    public Class getFormClass() {
		return PdaMessagePushMember.class;
	}

}
