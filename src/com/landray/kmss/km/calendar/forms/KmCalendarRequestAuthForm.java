package com.landray.kmss.km.calendar.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.calendar.convertor.KmCalendar_FormConvertor_IDToModel;
import com.landray.kmss.km.calendar.convertor.KmCalendar_FormConvertor_IDsToModelList;
import com.landray.kmss.km.calendar.model.KmCalendarRequestAuth;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

public class KmCalendarRequestAuthForm extends ExtendForm {

	/*
	 * 创建人Id
	 */
	private String docCreateId = null;

	public String getDocCreateId() {
		return docCreateId;
	}

	public void setDocCreateId(String docCreateId) {
		this.docCreateId = docCreateId;
	}

	/*
	 * 创建人名称
	 */
	private String docCreateName = null;

	public String getDocCreateName() {
		return docCreateName;
	}

	public void setDocCreateName(String docCreateName) {
		this.docCreateName = docCreateName;
	}

	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	private String fdRequestPersonIds;

	private String fdRequestPersonNames;

	public String getFdRequestPersonIds() {
		return fdRequestPersonIds;
	}

	public void setFdRequestPersonIds(String fdRequestPersonIds) {
		this.fdRequestPersonIds = fdRequestPersonIds;
	}

	public String getFdRequestPersonNames() {
		return fdRequestPersonNames;
	}

	public void setFdRequestPersonNames(String fdRequestPersonNames) {
		this.fdRequestPersonNames = fdRequestPersonNames;
	}

	private String fdRequestAuth;

	public String getFdRequestAuth() {
		return fdRequestAuth;
	}

	public void setFdRequestAuth(String fdRequestAuth) {
		this.fdRequestAuth = fdRequestAuth;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.docCreateId = null;
		this.docCreateTime = null;
		this.fdRequestPersonIds = null;
		this.fdRequestPersonNames = null;
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return KmCalendarRequestAuth.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreateId",
					new KmCalendar_FormConvertor_IDToModel(
							"docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("fdRequestPersonIds",
					new KmCalendar_FormConvertor_IDsToModelList(
							"fdRequestPerson",
							SysOrgPerson.class));
		}
		return toModelPropertyMap;
	}

}
