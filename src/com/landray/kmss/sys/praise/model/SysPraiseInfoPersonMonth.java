package com.landray.kmss.sys.praise.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoPersonMonthForm;

public class SysPraiseInfoPersonMonth extends SysPraiseInfoDetailBase {

	@Override
	public Class getFormClass() {
		return SysPraiseInfoPersonMonthForm.class;
	}

	private SysOrgElement fdPerson;

	private String fdYearMonth;

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
