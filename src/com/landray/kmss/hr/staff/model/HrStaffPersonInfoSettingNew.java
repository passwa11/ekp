package com.landray.kmss.hr.staff.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffPersonInfoSettingNewForm;

public class HrStaffPersonInfoSettingNew extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 排序号
	 */
	protected Integer fdOrder;
	/**
	 * 字段名称
	 */
	protected String fdName = null;

	protected String fdType = null;

	protected Boolean fdDefault;

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
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

	/**
	 * 是否系统默认
	 */
	public Boolean getFdDefault() {
		return fdDefault;
	}

	/**
	 * 是否系统默认
	 */
	public void setFdDefault(Boolean fdDefault) {
		this.fdDefault = fdDefault;
	}

	@Override
	public Class getFormClass() {
		return HrStaffPersonInfoSettingNewForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}

}
