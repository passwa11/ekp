package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryRule;



/**
 * 签到规则 Form
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryRuleForm  extends ExtendForm  {

	/**
	 * 签到方式
	 */
	private String fdMode;
	
	/**
	 * @return 签到方式
	 */
	public String getFdMode() {
		return this.fdMode;
	}
	
	/**
	 * @param fdMode 签到方式
	 */
	public void setFdMode(String fdMode) {
		this.fdMode = fdMode;
	}
	
	/**
	 * 签到范围
	 */
	private String fdLimit;
	
	/**
	 * @return 签到范围
	 */
	public String getFdLimit() {
		return this.fdLimit;
	}
	
	/**
	 * @param fdLimit 签到范围
	 */
	public void setFdLimit(String fdLimit) {
		this.fdLimit = fdLimit;
	}
	
	/**
	 * 签到时间
	 */
	private String fdInTime;
	
	/**
	 * @return 签到时间
	 */
	public String getFdInTime() {
		return this.fdInTime;
	}
	
	/**
	 * @param fdInTime 签到时间
	 */
	public void setFdInTime(String fdInTime) {
		this.fdInTime = fdInTime;
	}
	
	/**
	 * 签到提醒
	 */
	private String fdInRemind;
	
	/**
	 * @return 签到提醒
	 */
	public String getFdInRemind() {
		return this.fdInRemind;
	}
	
	/**
	 * @param fdInRemind 签到提醒
	 */
	public void setFdInRemind(String fdInRemind) {
		this.fdInRemind = fdInRemind;
	}
	
	/**
	 * 签到提醒时间
	 */
	private String fdInRemindTime;
	
	/**
	 * @return 签到提醒时间
	 */
	public String getFdInRemindTime() {
		return this.fdInRemindTime;
	}
	
	/**
	 * @param fdInRemindTime 签到提醒时间
	 */
	public void setFdInRemindTime(String fdInRemindTime) {
		this.fdInRemindTime = fdInRemindTime;
	}
	
	/**
	 * 签退提醒
	 */
	private String fdOutRemind;
	
	/**
	 * @return 签退提醒
	 */
	public String getFdOutRemind() {
		return this.fdOutRemind;
	}
	
	/**
	 * @param fdOutRemind 签退提醒
	 */
	public void setFdOutRemind(String fdOutRemind) {
		this.fdOutRemind = fdOutRemind;
	}
	
	/**
	 * 签退提醒时间
	 */
	private String fdOutRemindTime;
	
	/**
	 * @return 签退提醒时间
	 */
	public String getFdOutRemindTime() {
		return this.fdOutRemindTime;
	}
	
	/**
	 * @param fdOutRemindTime 签退提醒时间
	 */
	public void setFdOutRemindTime(String fdOutRemindTime) {
		this.fdOutRemindTime = fdOutRemindTime;
	}
	
	/**
	 * 早退时间
	 */
	private String fdLeftTime;
	
	/**
	 * @return 早退时间
	 */
	public String getFdLeftTime() {
		return this.fdLeftTime;
	}
	
	/**
	 * @param fdLeftTime 早退时间
	 */
	public void setFdLeftTime(String fdLeftTime) {
		this.fdLeftTime = fdLeftTime;
	}
	
	/**
	 * 迟到时间
	 */
	private String fdLateTime;
	
	/**
	 * @return 迟到时间
	 */
	public String getFdLateTime() {
		return this.fdLateTime;
	}
	
	/**
	 * @param fdLateTime 迟到时间
	 */
	public void setFdLateTime(String fdLateTime) {
		this.fdLateTime = fdLateTime;
	}
	
	/**
	 * 是否外勤
	 */
	private String fdOutside;
	
	/**
	 * @return 是否外勤
	 */
	public String getFdOutside() {
		return this.fdOutside;
	}
	
	/**
	 * @param fdOutside 是否外勤
	 */
	public void setFdOutside(String fdOutside) {
		this.fdOutside = fdOutside;
	}
	
	/**
	 * 签到事项的ID
	 */
	private String fdCategoryId;

	/**
	 * @return 签到事项的ID
	 */
	public String getFdCategoryId() {
		return this.fdCategoryId;
	}

	/**
	 * @param fdCategoryId
	 *            签到事项的ID
	 */
	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}

	/**
	 * 签到事项的名称
	 */
	private String fdCategoryName;

	/**
	 * @return 签到事项的名称
	 */
	public String getFdCategoryName() {
		return this.fdCategoryName;
	}

	/**
	 * @param fdCategoryName
	 *            签到事项的名称
	 */
	public void setFdCategoryName(String fdCategoryName) {
		this.fdCategoryName = fdCategoryName;
	}

	//机制开始 
	//机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdMode = null;
		fdLimit = null;
		fdInTime = null;
		fdInRemind = null;
		fdInRemindTime = null;
		fdOutRemind = null;
		fdOutRemindTime = null;
		fdLeftTime = null;
		fdLateTime = null;
		fdOutside = null;
		fdCategoryId = null;
		fdCategoryName = null;
		
 
		super.reset(mapping, request);
	}

	@Override
    public Class<SysAttendCategoryRule> getModelClass() {
		return SysAttendCategoryRule.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdCategoryId",
					new FormConvertor_IDToModel("fdCategory",
							SysAttendCategory.class));
		}
		return toModelPropertyMap;
	}
}
