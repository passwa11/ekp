package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryRuleForm;



/**
 * 签到规则
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryRule  extends BaseModel {

	/**
	 * 签到方式
	 */
	private Integer fdMode;
	
	/**
	 * @return 签到方式
	 */
	public Integer getFdMode() {
		return this.fdMode;
	}
	
	/**
	 * @param fdMode 签到方式
	 */
	public void setFdMode(Integer fdMode) {
		this.fdMode = fdMode;
	}
	
	/**
	 * 签到范围
	 */
	private Integer fdLimit;
	
	/**
	 * @return 签到范围
	 */
	public Integer getFdLimit() {
		return this.fdLimit;
	}
	
	/**
	 * @param fdLimit 签到范围
	 */
	public void setFdLimit(Integer fdLimit) {
		this.fdLimit = fdLimit;
	}
	
	/**
	 * 签到时间
	 */
	private Date fdInTime;
	
	/**
	 * @return 签到时间
	 */
	public Date getFdInTime() {
		return this.fdInTime;
	}
	
	/**
	 * @param fdInTime 签到时间
	 */
	public void setFdInTime(Date fdInTime) {
		this.fdInTime = fdInTime;
	}
	
	/**
	 * 签到提醒
	 */
	private Boolean fdInRemind;
	
	/**
	 * @return 签到提醒
	 */
	public Boolean getFdInRemind() {
		return this.fdInRemind;
	}
	
	/**
	 * @param fdInRemind 签到提醒
	 */
	public void setFdInRemind(Boolean fdInRemind) {
		this.fdInRemind = fdInRemind;
	}
	
	/**
	 * 签到提醒时间
	 */
	private Integer fdInRemindTime;
	
	/**
	 * @return 签到提醒时间
	 */
	public Integer getFdInRemindTime() {
		return this.fdInRemindTime;
	}
	
	/**
	 * @param fdInRemindTime 签到提醒时间
	 */
	public void setFdInRemindTime(Integer fdInRemindTime) {
		this.fdInRemindTime = fdInRemindTime;
	}
	
	/**
	 * 签退提醒
	 */
	private Boolean fdOutRemind;
	
	/**
	 * @return 签退提醒
	 */
	public Boolean getFdOutRemind() {
		return this.fdOutRemind;
	}
	
	/**
	 * @param fdOutRemind 签退提醒
	 */
	public void setFdOutRemind(Boolean fdOutRemind) {
		this.fdOutRemind = fdOutRemind;
	}
	
	/**
	 * 签退提醒时间
	 */
	private Integer fdOutRemindTime;
	
	/**
	 * @return 签退提醒时间
	 */
	public Integer getFdOutRemindTime() {
		return this.fdOutRemindTime;
	}
	
	/**
	 * @param fdOutRemindTime 签退提醒时间
	 */
	public void setFdOutRemindTime(Integer fdOutRemindTime) {
		this.fdOutRemindTime = fdOutRemindTime;
	}
	
	/**
	 * 早退时间
	 */
	private Integer fdLeftTime;
	
	/**
	 * @return 早退时间
	 */
	public Integer getFdLeftTime() {
		return this.fdLeftTime;
	}
	
	/**
	 * @param fdLeftTime 早退时间
	 */
	public void setFdLeftTime(Integer fdLeftTime) {
		this.fdLeftTime = fdLeftTime;
	}
	
	/**
	 * 迟到时间
	 */
	private Integer fdLateTime;
	
	/**
	 * @return 迟到时间
	 */
	public Integer getFdLateTime() {
		return this.fdLateTime;
	}
	
	/**
	 * @param fdLateTime 迟到时间
	 */
	public void setFdLateTime(Integer fdLateTime) {
		this.fdLateTime = fdLateTime;
	}
	
	/**
	 * 是否外勤
	 */
	private Boolean fdOutside;
	
	/**
	 * @return 是否外勤
	 */
	public Boolean getFdOutside() {
		return this.fdOutside;
	}
	
	/**
	 * @param fdOutside 是否外勤
	 */
	public void setFdOutside(Boolean fdOutside) {
		this.fdOutside = fdOutside;
	}
	
	/**
	 * 签到事项
	 */
	private SysAttendCategory fdCategory;

	/**
	 * @return 签到事项
	 */
	public SysAttendCategory getFdCategory() {
		return this.fdCategory;
	}

	/**
	 * @param fdCategory
	 *            签到事项
	 */
	public void setFdCategory(SysAttendCategory fdCategory) {
		this.fdCategory = fdCategory;
	}

	//机制开始
	//机制结束

	@Override
    public Class<SysAttendCategoryRuleForm> getFormClass() {
		return SysAttendCategoryRuleForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdCategory.fdId", "fdCategoryId");
			toFormPropertyMap.put("fdCategory.fdId", "fdCategoryName");
		}
		return toFormPropertyMap;
	}
}
