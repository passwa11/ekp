package com.landray.kmss.km.calendar.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.calendar.model.KmCalendarAgendaLabel;

/**
 * 标签 Form
 * 
 * @author
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmCalendarAgendaLabelForm extends ExtendForm {

	/**
	 * 名称
	 */
	protected String fdName = null;

	/**
	 * @return 名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 描述
	 */
	protected String fdDescription = null;

	/**
	 * @return 描述
	 */
	public String getFdDescription() {
		return fdDescription;
	}

	/**
	 * @param fdDescription
	 *            描述
	 */
	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	/**
	 * 颜色
	 */
	protected String fdColor = null;

	/**
	 * @return 颜色
	 */
	public String getFdColor() {
		return fdColor;
	}

	/**
	 * @param fdColor
	 *            颜色
	 */
	public void setFdColor(String fdColor) {
		this.fdColor = fdColor;
	}

	/**
	 * 排序号
	 */
	protected String fdOrder = null;

	/**
	 * @return 排序号
	 */
	public String getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	private Boolean isAgendaLabel;

	public Boolean getIsAgendaLabel() {
		return isAgendaLabel;
	}

	public void setIsAgendaLabel(Boolean isAgendaLabel) {
		this.isAgendaLabel = isAgendaLabel;
	}

	private Boolean fdIsAvailable;

	public Boolean getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	private String fdAgendaModelName;

	public String getFdAgendaModelName() {
		return fdAgendaModelName;
	}

	public void setFdAgendaModelName(String fdAgendaModelName) {
		this.fdAgendaModelName = fdAgendaModelName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdDescription = null;
		fdColor = null;
		fdOrder = null;
		isAgendaLabel = false;
		fdIsAvailable = true;
		fdAgendaModelName = null;

		super.reset(mapping, request);
	}

	@Override
    @SuppressWarnings("unchecked")
	public Class getModelClass() {
		return KmCalendarAgendaLabel.class;
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
