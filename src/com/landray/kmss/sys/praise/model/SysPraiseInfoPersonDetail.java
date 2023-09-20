package com.landray.kmss.sys.praise.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoPersonDetailForm;

public class SysPraiseInfoPersonDetail extends SysPraiseInfoDetailBase{

	@Override
	public Class getFormClass() {
		return SysPraiseInfoPersonDetailForm.class;
	}
	
	private Date docCreateTime;
	
	private SysOrgElement fdPerson;
	
	private String fdYearMonth;

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public SysOrgElement getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgElement fdPerson) {
		this.fdPerson = fdPerson;
	}

	public String getFdYearMonth() {
		return fdYearMonth;
	}

	public void setFdYearMonth(String fdYearMonth) {
		this.fdYearMonth = fdYearMonth;
	}
	
	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
			toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
		}
		return toFormPropertyMap;
	}
}
