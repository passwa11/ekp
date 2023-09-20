package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryWorktimeForm;

import java.util.Date;



/**
 * 考勤班次
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryWorktime  extends BaseModel {

	/**
	 * 上班时间
	 */
	private Date fdStartTime;
	
	/**
	 * 下班时间
	 */
	private Date fdEndTime;

	public Date getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(Date fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	public Date getFdEndTime() {
		return fdEndTime;
	}

	public void setFdEndTime(Date fdEndTime) {
		this.fdEndTime = fdEndTime;
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

	/**
	 * 是否有效
	 */
	private Boolean fdIsAvailable;

	public Boolean getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}
	
	/**
	 * 跨天标识 ，1：当天，2：次日
	 */
	private Integer fdOverTimeType;
	

	public Integer getFdOverTimeType() {
		return fdOverTimeType;
	}

	public void setFdOverTimeType(Integer fdOverTimeType) {
		this.fdOverTimeType = fdOverTimeType;
	}

	@Override
    public Class<SysAttendCategoryWorktimeForm> getFormClass() {
		return SysAttendCategoryWorktimeForm.class;
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

	/**
	 * 非数据库字段START
	 */
	/**
	 * 最早上班时间
	 */
	protected Date fdBeginTime;

	/**
	 * 最晚下班时间
	 */
	protected Date fdOverTime;

	/**
	 * 最晚下班时间的类型-跨天（今日还是次日）
	 */
	protected Integer fdEndOverTimeType;

	public Date getFdBeginTime() {
		return fdBeginTime;
	}

	public void setFdBeginTime(Date fdBeginTime) {
		this.fdBeginTime = fdBeginTime;
	}

	public Date getFdOverTime() {
		return fdOverTime;
	}

	public void setFdOverTime(Date fdOverTime) {
		this.fdOverTime = fdOverTime;
	}

	/**
	 * 非数据库字段END
	 */


	public Integer getFdEndOverTimeType() {
		return fdEndOverTimeType;
	}

	public void setFdEndOverTimeType(Integer fdEndOverTimeType) {
		this.fdEndOverTimeType = fdEndOverTimeType;
	}
}
