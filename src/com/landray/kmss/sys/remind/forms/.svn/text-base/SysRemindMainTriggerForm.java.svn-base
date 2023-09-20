package com.landray.kmss.sys.remind.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.remind.model.SysRemindMain;
import com.landray.kmss.sys.remind.model.SysRemindMainTrigger;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 触发时间
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindMainTriggerForm extends ExtendForm {

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
	private String fdDay;

	/**
	 * 小时
	 */
	private String fdHour;

	/**
	 * 分钟
	 */
	private String fdMinute;

	/**
	 * 时间
	 */
	private String fdTime;

	/**
	 * 排序号
	 */
	private String fdOrder;

	/**
	 * 所属提醒
	 */
	private String fdRemindId;
	private String fdRemindName;

	@Override
	public Class getModelClass() {
		return SysRemindMainTrigger.class;
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

	public String getFdDay() {
		return fdDay;
	}

	public void setFdDay(String fdDay) {
		this.fdDay = fdDay;
	}

	public String getFdHour() {
		return fdHour;
	}

	public void setFdHour(String fdHour) {
		this.fdHour = fdHour;
	}

	public String getFdMinute() {
		return fdMinute;
	}

	public void setFdMinute(String fdMinute) {
		this.fdMinute = fdMinute;
	}

	public String getFdTime() {
		return fdTime;
	}

	public void setFdTime(String fdTime) {
		this.fdTime = fdTime;
	}

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getFdRemindId() {
		return fdRemindId;
	}

	public void setFdRemindId(String fdRemindId) {
		this.fdRemindId = fdRemindId;
	}

	public String getFdRemindName() {
		return fdRemindName;
	}

	public void setFdRemindName(String fdRemindName) {
		this.fdRemindName = fdRemindName;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.fdFieldId = null;
		this.fdFieldName = null;
		this.fdMode = null;
		this.fdDay = null;
		this.fdHour = null;
		this.fdMinute = null;
		this.fdTime = null;
		this.fdOrder = null;
		this.fdRemindId = null;
		this.fdRemindName = null;
		super.reset(mapping, request);
	}

	private FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdRemindId", new FormConvertor_IDToModel("fdRemind", SysRemindMain.class));
		}
		return toModelPropertyMap;
	}

}
