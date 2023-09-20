package com.landray.kmss.sys.remind.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.remind.forms.SysRemindMainTriggerForm;

/**
 * 触发时间
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindMainTrigger extends BaseModel {

	/**
	 * 触发字段
	 */
	private String fdFieldId;
	private String fdFieldName;

	/**
	 * 触发模式
	 */
	private String fdMode;

	/**
	 * 天
	 */
	private Integer fdDay;

	/**
	 * 小时
	 */
	private Integer fdHour;

	/**
	 * 分钟
	 */
	private Integer fdMinute;

	/**
	 * 时间
	 */
	private String fdTime;

	/**
	 * 排序号
	 */
	private Integer fdOrder;

	/**
	 * 所属提醒
	 */
	private SysRemindMain fdRemind;

	@Override
	public Class getFormClass() {
		return SysRemindMainTriggerForm.class;
	}

	public String getFdFieldId() {
		return fdFieldId;
	}

	public void setFdFieldId(String fdFieldId) {
		this.fdFieldId = fdFieldId;
	}

	public String getFdFieldName() {
		return fdFieldName;
	}

	public void setFdFieldName(String fdFieldName) {
		this.fdFieldName = fdFieldName;
	}


	public String getFdMode() {
		return fdMode;
	}

	public void setFdMode(String fdMode) {
		this.fdMode = fdMode;
	}

	public Integer getFdDay() {
		return fdDay;
	}

	public void setFdDay(Integer fdDay) {
		this.fdDay = fdDay;
	}

	public Integer getFdHour() {
		return fdHour;
	}

	public void setFdHour(Integer fdHour) {
		this.fdHour = fdHour;
	}

	public Integer getFdMinute() {
		return fdMinute;
	}

	public void setFdMinute(Integer fdMinute) {
		this.fdMinute = fdMinute;
	}

	public String getFdTime() {
		return fdTime;
	}

	public void setFdTime(String fdTime) {
		this.fdTime = fdTime;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public SysRemindMain getFdRemind() {
		return fdRemind;
	}

	public void setFdRemind(SysRemindMain fdRemind) {
		this.fdRemind = fdRemind;
	}

	private ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdRemind.fdId", "fdRemindId");
			toFormPropertyMap.put("fdRemind.fdName", "fdRemindName");
		}

		return toFormPropertyMap;
	}

}
