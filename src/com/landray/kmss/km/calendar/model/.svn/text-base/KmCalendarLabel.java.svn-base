package com.landray.kmss.km.calendar.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.calendar.forms.KmCalendarLabelForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 标签
 * 
 * @author
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmCalendarLabel extends BaseModel {

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
	 * 创建者
	 */
	protected SysOrgElement fdCreator;

	/**
	 * @return 创建者
	 */
	public SysOrgElement getFdCreator() {
		return fdCreator;
	}

	/**
	 * @param fdCreator
	 *            创建者
	 */
	public void setFdCreator(SysOrgElement fdCreator) {
		this.fdCreator = fdCreator;
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

	private String fdModelName;

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	/**
	 * 是否选中:0未选中，1选中
	 */
	public Boolean fdSelectedFlag;

	/**
	 * 是否通用标签：0自定义，1|myEvent 通用固定标签
	 */
	public String fdCommonFlag;

	public Boolean getFdSelectedFlag() {
		return fdSelectedFlag;
	}

	public void setFdSelectedFlag(Boolean fdSelectedFlag) {
		this.fdSelectedFlag = fdSelectedFlag;
	}

	public String getFdCommonFlag() {
		return fdCommonFlag;
	}

	public void setFdCommonFlag(String fdCommonFlag) {
		this.fdCommonFlag = fdCommonFlag;
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
			toFormPropertyMap.put("fdCreator.fdId", "fdCreatorId");
			toFormPropertyMap.put("fdCreator.fdName", "fdCreatorName");
		}
		return toFormPropertyMap;
	}

	public KmCalendarLabel() {
		super();
	}

	public KmCalendarLabel(String fdName, String fdDescription, String fdColor,
			Integer fdOrder, SysOrgElement fdCreator) {
		this();
		this.fdName = fdName;
		this.fdDescription = fdDescription;
		this.fdColor = fdColor;
		this.fdOrder = fdOrder;
		this.fdCreator = fdCreator;
	}

	public KmCalendarLabel(String fdName, String fdDescription, String fdColor,
						   Integer fdOrder, SysOrgElement fdCreator,
						   Boolean selectedFlag,String commonFlag) {
		this();
		this.fdName = fdName;
		this.fdDescription = fdDescription;
		this.fdColor = fdColor;
		this.fdOrder = fdOrder;
		this.fdCreator = fdCreator;
		this.fdSelectedFlag = selectedFlag;
		this.fdCommonFlag = commonFlag;
	}
}
