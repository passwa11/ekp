package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.web.action.ActionMapping;

public class HrStaffPersonInfoSettingNewForm extends ExtendForm {

	private static final long serialVersionUID = 1L;

	/**
	 * 排序号
	 */
	protected String fdOrder = null;
	/**
	 * 字段名称
	 */
	protected String fdName = null;

	protected String fdType = null;

	protected String fdDefault = null;

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdDefault() {
		return fdDefault;
	}

	public void setFdDefault(String fdDefault) {
		this.fdDefault = fdDefault;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdOrder = null;
		fdName = null;
		fdType = null;
		fdDefault = null;
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return HrStaffPersonInfoSettingNew.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}
