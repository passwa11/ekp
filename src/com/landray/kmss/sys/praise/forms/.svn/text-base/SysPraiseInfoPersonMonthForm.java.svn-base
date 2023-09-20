package com.landray.kmss.sys.praise.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.model.SysPraiseInfoPersonMonth;

public class SysPraiseInfoPersonMonthForm extends SysPraiseInfoDetailBaseForm {

	@Override
	public Class getModelClass() {
		return SysPraiseInfoPersonMonth.class;
	}

	private String fdPersonId;

	private String fdPersonName;

	private String fdYearMonth;

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

	public String getFdYearMonth() {
		return fdYearMonth;
	}

	public void setFdYearMonth(String fdYearMonth) {
		this.fdYearMonth = fdYearMonth;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdYearMonth = null;
		fdPersonId = null;
		fdPersonName = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel("fdPerson", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
