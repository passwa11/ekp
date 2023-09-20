package com.landray.kmss.sys.praise.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoPersonalForm;

public class SysPraiseInfoPersonal extends BaseModel{

	@Override
	public Class getFormClass() {
		return SysPraiseInfoPersonalForm.class;
	}
	
	private Date fdUpdateTime;
	
	private SysOrgElement fdPerson;

	private SysPraiseInfoDetail fdWeek;
	
	private SysPraiseInfoDetail fdMonth;
	
	private SysPraiseInfoDetail fdYear;
	
	private SysPraiseInfoDetail fdTotal;
	
	public SysOrgElement getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgElement fdPerson) {
		this.fdPerson = fdPerson;
	}
	
	public Date getFdUpdateTime() {
		return fdUpdateTime;
	}

	public void setFdUpdateTime(Date fdUpdateTime) {
		this.fdUpdateTime = fdUpdateTime;
	}

	public SysPraiseInfoDetail getFdWeek() {
		return fdWeek;
	}

	public void setFdWeek(SysPraiseInfoDetail fdWeek) {
		this.fdWeek = fdWeek;
	}

	public SysPraiseInfoDetail getFdMonth() {
		return fdMonth;
	}

	public void setFdMonth(SysPraiseInfoDetail fdMonth) {
		this.fdMonth = fdMonth;
	}

	public SysPraiseInfoDetail getFdYear() {
		return fdYear;
	}

	public void setFdYear(SysPraiseInfoDetail fdYear) {
		this.fdYear = fdYear;
	}

	public SysPraiseInfoDetail getFdTotal() {
		return fdTotal;
	}

	public void setFdTotal(SysPraiseInfoDetail fdTotal) {
		this.fdTotal = fdTotal;
	}
	
	
	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdWeek.fdId", "fdWeekId");
			toFormPropertyMap.put("fdWeek.fdId", "fdWeekName");
			toFormPropertyMap.put("fdMonth.fdId", "fdMonthId");
			toFormPropertyMap.put("fdMonth.fdId", "fdMonthName");
			toFormPropertyMap.put("fdYear.fdId", "fdYearId");
			toFormPropertyMap.put("fdYear.fdId", "fdYearName");
			toFormPropertyMap.put("fdTotal.fdId", "fdTotalId");
			toFormPropertyMap.put("fdTotal.fdId", "fdTotalName");
			toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
			toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
		}
		return toFormPropertyMap;
	}
}
