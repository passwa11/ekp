package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.ratify.model.HrRatifyLeaveReason;
import com.landray.kmss.web.action.ActionMapping;

public class HrRatifyLeaveReasonForm extends ExtendForm {

	private static FormToModelPropertyMap toModelPropertyMap;

	/**
	 * 离职原因名称
	 */
	protected String fdName = null;

	protected String fdType = null;

	/**
	 * 排序号
	 */
	protected String fdOrder = null;

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

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdType = null;
		fdOrder = null;
		super.reset(mapping, request);
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}

	@Override
	public Class getModelClass() {
		return HrRatifyLeaveReason.class;
	}

}
