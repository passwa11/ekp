package com.landray.kmss.hr.organization.model;

import java.io.Serializable;

import com.landray.kmss.sys.log.model.BaseSysLogApp;

/**
  * 组织变更记录
  */
public class HrOrganizationLog extends BaseSysLogApp implements Serializable {

	private static final long serialVersionUID = 1455327374750559277L;

	/*
	 * 详细信息
	 */
	private String fdDetails;

	public String getFdDetails() {
		return fdDetails;
	}

	public void setFdDetails(String fdDetails) {
		this.fdDetails = fdDetails;
	}

	/**
	 * 变动描述
	 */
	private String fdDesc;

	public String getFdDesc() {
		return fdDesc;
	}

	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

	/*
	 * 浏览器
	 */
	private String fdBrowser;

	public String getFdBrowser() {
		return fdBrowser;
	}

	public void setFdBrowser(String fdBrowser) {
		this.fdBrowser = fdBrowser;
	}

	/*
	 * 设备
	 */
	private String fdEquipment;

	public String getFdEquipment() {
		return fdEquipment;
	}

	public void setFdEquipment(String fdEquipment) {
		this.fdEquipment = fdEquipment;
	}
}
