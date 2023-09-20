package com.landray.kmss.km.calendar.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.calendar.forms.KmCalendarLabelForm;

/**
 * 标签
 * 
 * @author
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmCalendarAgendaLabel extends BaseModel {

	/**
	 * 名称
	 */
	protected String fdName;

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
	protected String fdDescription;

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
	protected String fdColor;

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
	protected Integer fdOrder = new Integer(0);

	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	private String fdAgendaModelName;

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

	@Override
    @SuppressWarnings("unchecked")
	public Class getFormClass() {
		return KmCalendarLabelForm.class;
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

	public KmCalendarAgendaLabel() {
		super();
	}

	public void setFdAgendaModelName(String fdAgendaModelName) {
		this.fdAgendaModelName = fdAgendaModelName;
	}

	public String getFdAgendaModelName() {
		return fdAgendaModelName;
	}

}
