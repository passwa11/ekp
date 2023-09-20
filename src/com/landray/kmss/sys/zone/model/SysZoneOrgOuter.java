package com.landray.kmss.sys.zone.model;

import com.landray.kmss.common.model.BaseModel;

public class SysZoneOrgOuter extends BaseModel {

	private static final long serialVersionUID = 2189166165047082322L;

	@SuppressWarnings("rawtypes")
	@Override
	public Class getFormClass() {
		return null;
	}

	private String fdName;
	private String fdPostDesc;
	private String fdMobileNo;
	private String fdWorkPhone;
	private String fdEmail;
	private String fdMemo;
	private String fdOtherInfo;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdPostDesc() {
		return fdPostDesc;
	}

	public void setFdPostDesc(String fdPostDesc) {
		this.fdPostDesc = fdPostDesc;
	}

	public String getFdMobileNo() {
		return fdMobileNo;
	}

	public void setFdMobileNo(String fdMobileNo) {
		this.fdMobileNo = fdMobileNo;
	}

	public String getFdWorkPhone() {
		return fdWorkPhone;
	}

	public void setFdWorkPhone(String fdWorkPhone) {
		this.fdWorkPhone = fdWorkPhone;
	}

	public String getFdEmail() {
		return fdEmail;
	}

	public void setFdEmail(String fdEmail) {
		this.fdEmail = fdEmail;
	}

	public String getFdMemo() {
		return fdMemo;
	}

	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
	}

	public String getFdOtherInfo() {
		return fdOtherInfo;
	}

	public void setFdOtherInfo(String fdOtherInfo) {
		this.fdOtherInfo = fdOtherInfo;
	}

}
