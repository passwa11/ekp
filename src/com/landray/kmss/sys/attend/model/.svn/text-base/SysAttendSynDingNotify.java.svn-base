package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendSynDingNotifyForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

public class SysAttendSynDingNotify extends BaseModel {

	private SysOrgPerson docCreator;
	private Date docCreateTime;
	private Integer fdStatus = 0;
	private String fdLink;
	private String docSubject;
	private String fdSysAttendSynDingId;
	private SysOrgElement fdReceiver;

	@Override
	public Class<SysAttendSynDingNotifyForm> getFormClass() {
		return SysAttendSynDingNotifyForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdReceiver.fdId", "fdOutPersonId");
			toFormPropertyMap.put("fdReceiver.fdName", "fdOutPersonName");
		}
		return toFormPropertyMap;
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

	public Integer getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(Integer fdStatus) {
		this.fdStatus = fdStatus;
	}

	public String getFdLink() {
		return fdLink;
	}

	public void setFdLink(String fdLink) {
		this.fdLink = fdLink;
	}

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	public String getFdSysAttendSynDingId() {
		return fdSysAttendSynDingId;
	}

	public void setFdSysAttendSynDingId(String fdSysAttendSynDingId) {
		this.fdSysAttendSynDingId = fdSysAttendSynDingId;
	}

	public SysOrgElement getFdReceiver() {
		return fdReceiver;
	}

	public void setFdReceiver(SysOrgElement fdPerson) {
		this.fdReceiver = fdPerson;
	}
}
