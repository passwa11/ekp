package com.landray.kmss.sys.organization.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

public class SysOrgRetrievePasswordLog extends BaseModel  {

	@Override
	public Class getFormClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	public SysOrgPerson getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgPerson fdPerson) {
		this.fdPerson = fdPerson;
	}

	public String getFdMobileCode() {
		return fdMobileCode;
	}

	public void setFdMobileCode(String fdMobileCode) {
		this.fdMobileCode = fdMobileCode;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	private SysOrgPerson fdPerson;
	
	private String fdMobileCode;
	
	private Date fdCreateTime;

}
