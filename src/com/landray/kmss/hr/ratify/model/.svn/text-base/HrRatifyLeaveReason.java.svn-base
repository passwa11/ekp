package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.ratify.forms.HrRatifyLeaveReasonForm;

public class HrRatifyLeaveReason extends BaseModel {

	private static ModelToFormPropertyMap toFormPropertyMap;

	/**
	 * 离职原因名称
	 */
	protected String fdName;

	protected String fdType;
	/**
	 * 排序号
	 */
	protected Integer fdOrder;

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

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return HrRatifyLeaveReasonForm.class;
	}

}
