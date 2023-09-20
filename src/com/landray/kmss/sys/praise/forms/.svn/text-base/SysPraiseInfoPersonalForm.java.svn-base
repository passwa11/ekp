package com.landray.kmss.sys.praise.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.model.SysPraiseInfoDetail;
import com.landray.kmss.sys.praise.model.SysPraiseInfoPersonal;

public class SysPraiseInfoPersonalForm extends ExtendForm {

	@Override
	public Class getModelClass() {
		return SysPraiseInfoPersonal.class;
	}

	private String fdPersonId;

	private String fdPersonName;

	private String fdUpdateTime = null;

	private String fdWeekId = null;

	private String fdWeekName = null;

	private String fdMonthId = null;

	private String fdMonthName = null;

	private String fdYearId = null;

	private String fdYearName = null;

	private String fdTotalId = null;

	private String fdTotalName = null;

	public String getFdPersonId() {
		return fdPersonId;
	}

	public void setFdPersonId(String fdPersonId) {
		this.fdPersonId = fdPersonId;
	}

	public String getFdPersonName() {
		return fdPersonName;
	}

	public void setFdPersonName(String fdPersonName) {
		this.fdPersonName = fdPersonName;
	}

	public String getFdTotalId() {
		return fdTotalId;
	}

	public void setFdTotalId(String fdTotalId) {
		this.fdTotalId = fdTotalId;
	}

	public String getFdTotalName() {
		return fdTotalName;
	}

	public void setFdTotalName(String fdTotalName) {
		this.fdTotalName = fdTotalName;
	}

	public String getFdWeekId() {
		return fdWeekId;
	}

	public void setFdWeekId(String fdWeekId) {
		this.fdWeekId = fdWeekId;
	}

	public String getFdWeekName() {
		return fdWeekName;
	}

	public void setFdWeekName(String fdWeekName) {
		this.fdWeekName = fdWeekName;
	}

	public String getFdMonthId() {
		return fdMonthId;
	}

	public void setFdMonthId(String fdMonthId) {
		this.fdMonthId = fdMonthId;
	}

	public String getFdMonthName() {
		return fdMonthName;
	}

	public void setFdMonthName(String fdMonthName) {
		this.fdMonthName = fdMonthName;
	}

	public String getFdYearId() {
		return fdYearId;
	}

	public void setFdYearId(String fdYearId) {
		this.fdYearId = fdYearId;
	}

	public String getFdYearName() {
		return fdYearName;
	}

	public void setFdYearName(String fdYearName) {
		this.fdYearName = fdYearName;
	}

	public String getFdUpdateTime() {
		return fdUpdateTime;
	}

	public void setFdUpdateTime(String fdUpdateTime) {
		this.fdUpdateTime = fdUpdateTime;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdUpdateTime = null;
		fdWeekId = null;
		fdWeekName = null;
		fdMonthId = null;
		fdMonthName = null;
		fdYearId = null;
		fdYearName = null;
		fdTotalId = null;
		fdTotalName = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdWeekId", new FormConvertor_IDToModel("fdWeek", SysPraiseInfoDetail.class));
			toModelPropertyMap.put("fdMonthId", new FormConvertor_IDToModel("fdMonth", SysPraiseInfoDetail.class));
			toModelPropertyMap.put("fdYearId", new FormConvertor_IDToModel("fdYear", SysPraiseInfoDetail.class));
			toModelPropertyMap.put("fdTotalId", new FormConvertor_IDToModel("fdTotal", SysPraiseInfoDetail.class));
			toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel("fdPerson", SysOrgElement.class));

		}
		return toModelPropertyMap;
	}

}
