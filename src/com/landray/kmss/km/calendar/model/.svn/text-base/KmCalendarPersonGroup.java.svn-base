package com.landray.kmss.km.calendar.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.calendar.forms.KmCalendarPersonGroupForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;

/**
 * 
 * 后台群组设置model
 *
 */
@SuppressWarnings("serial")
public class KmCalendarPersonGroup extends BaseAuthModel {

	/**
	 * 序号
	 */
	protected Integer fdOrder;

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 群组名称
	 */
	protected String docSubject;

	@Override
    public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 群组成员
	 */
	protected List<SysOrgElement> fdPersonGroup;

	public List<SysOrgElement> getFdPersonGroup() {
		return fdPersonGroup;
	}

	public void setFdPersonGroup(List<SysOrgElement> fdPersonGroup) {
		this.fdPersonGroup = fdPersonGroup;
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
	public Class getFormClass() {
		return KmCalendarPersonGroupForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPersonGroup",
					new ModelConvertor_ModelListToString(
							"fdPersonGroupIds:fdPersonGroupNames",
							"fdId:deptLevelNames"));
		}
		return toFormPropertyMap;
	}

	/**
	 * 记录变更前的人员(JSON)
	 */
	protected String beforeChangePerson;

	public String getBeforeChangePerson() {
		return (String) readLazyField("beforeChangePerson",
				beforeChangePerson);
	}

	public void setBeforeChangePerson(String beforeChangePerson) {
		this.beforeChangePerson = (String) writeLazyField(
				"beforeChangeContent", this.beforeChangePerson,
				beforeChangePerson);
	}
}
