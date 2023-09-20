package com.landray.kmss.km.calendar.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.calendar.model.KmCalendarPersonGroup;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.BaseAuthForm;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 
 * 后台群组设置Form
 *
 */
@SuppressWarnings("serial")
public class KmCalendarPersonGroupForm extends BaseAuthForm {

	/**
	 * 序号
	 */
	protected String fdOrder;

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 群组名称
	 */
	protected String docSubject = null;

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 群组成员id
	 */
	protected String fdPersonGroupIds;

	public String getFdPersonGroupIds() {
		return fdPersonGroupIds;
	}

	public void setFdPersonGroupIds(String fdPersonGroupIds) {
		this.fdPersonGroupIds = fdPersonGroupIds;
	}

	/**
	 * 群组成员名称
	 */
	protected String fdPersonGroupNames;

	public String getFdPersonGroupNames() {
		return fdPersonGroupNames;
	}

	public void setFdPersonGroupNames(String fdPersonGroupNames) {
		this.fdPersonGroupNames = fdPersonGroupNames;
	}

	/**
	 * 描述
	 */
	protected String fdDescription;

	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdOrder = null;
		fdDescription = null;
		beforeChangePerson = null;
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return KmCalendarPersonGroup.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPersonGroupIds",
					new FormConvertor_IDsToModelList("fdPersonGroup",
							SysOrgPerson.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 记录变更前的人员(JSON)
	 */
	protected String beforeChangePerson = null;

	public String getBeforeChangePerson() {
		return beforeChangePerson;
	}

	public void setBeforeChangePerson(String beforeChangePerson) {
		this.beforeChangePerson = beforeChangePerson;
	}
}
