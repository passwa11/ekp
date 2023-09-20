package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryTimesheetForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工作时间设置弹框
 *
 * @author
 * @version 1.0 2018-06-12
 */
public class SysAttendCategoryTimesheet extends BaseModel {

	/**
	 * 星期，以;分隔
	 */
	private String fdWeek;

	/**
	 * 一班制：1，两班制：2
	 */
	private Integer fdWork;

	/**
	 * 班次
	 */
	private List<SysAttendCategoryWorktime> fdWorkTime = new ArrayList<SysAttendCategoryWorktime>();

	/**
	 * 最早打卡1
	 */
	private Date fdStartTime1;

	/**
	 * 最早打卡2
	 */
	private Date fdStartTime2;

	/**
	 * 最晚打卡1
	 */
	private Date fdEndTime1;

	/**
	 * 最晚打卡2
	 */
	private Date fdEndTime2;

	/**
	 * 当天：1，次日：2
	 */
	private Integer fdEndDay;

	/**
	 * 午休开始时间
	 */
	private Date fdRestStartTime;

	/**
	 * 午休结束时间
	 */
	private Date fdRestEndTime;

	/**
	 * 总工时
	 */
	private Float fdTotalTime;
	/**
	 * 统计时 按照多少天统计
	 */
	private Float fdTotalDay;

	/**
	 * 考勤组
	 */
	private SysAttendCategory fdCategory;

	public String getFdWeek() {
		return fdWeek;
	}

	public void setFdWeek(String fdWeek) {
		this.fdWeek = fdWeek;
	}

	public Integer getFdWork() {
		return fdWork;
	}

	public void setFdWork(Integer fdWork) {
		this.fdWork = fdWork;
	}

	/**
	 * 可能包含无效的班制，不建议使用
	 * 
	 * @return
	 */
	@Deprecated
	public List<SysAttendCategoryWorktime> getFdWorkTime() {
		return fdWorkTime;
	}

	/**
	 * 去除无效的班制，建议使用
	 * 
	 * @return
	 */
	public List<SysAttendCategoryWorktime> getAvailWorkTime() {
		List<SysAttendCategoryWorktime> tmpWorkTime = new ArrayList<SysAttendCategoryWorktime>();
		tmpWorkTime.addAll(fdWorkTime);
		for (int i = 0; i < fdWorkTime.size(); i++) {
			SysAttendCategoryWorktime work = fdWorkTime.get(i);
			if (Boolean.FALSE.equals(work.getFdIsAvailable())) {
				tmpWorkTime.remove(work);
			}
		}
		return tmpWorkTime;
	}

	public void setFdWorkTime(List<SysAttendCategoryWorktime> fdWorkTime) {
		this.fdWorkTime = fdWorkTime;
	}

	public Date getFdStartTime1() {
		return fdStartTime1;
	}

	public void setFdStartTime1(Date fdStartTime1) {
		this.fdStartTime1 = fdStartTime1;
	}

	public Date getFdStartTime2() {
		return fdStartTime2;
	}

	public void setFdStartTime2(Date fdStartTime2) {
		this.fdStartTime2 = fdStartTime2;
	}

	public Date getFdEndTime1() {
		return fdEndTime1;
	}

	public void setFdEndTime1(Date fdEndTime1) {
		this.fdEndTime1 = fdEndTime1;
	}

	public Date getFdEndTime2() {
		return fdEndTime2;
	}

	public void setFdEndTime2(Date fdEndTime2) {
		this.fdEndTime2 = fdEndTime2;
	}

	public Integer getFdEndDay() {
		return fdEndDay;
	}

	public void setFdEndDay(Integer fdEndDay) {
		this.fdEndDay = fdEndDay;
	}

	public Date getFdRestStartTime() {
		return fdRestStartTime;
	}

	public void setFdRestStartTime(Date fdRestStartTime) {
		this.fdRestStartTime = fdRestStartTime;
	}

	public Date getFdRestEndTime() {
		return fdRestEndTime;
	}

	public void setFdRestEndTime(Date fdRestEndTime) {
		this.fdRestEndTime = fdRestEndTime;
	}

	public Float getFdTotalTime() {
		return fdTotalTime;
	}

	public void setFdTotalTime(Float fdTotalTime) {
		this.fdTotalTime = fdTotalTime;
	}

	public SysAttendCategory getFdCategory() {
		return fdCategory;
	}

	public void setFdCategory(SysAttendCategory fdCategory) {
		this.fdCategory = fdCategory;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdWorkTime",
					new ModelConvertor_ModelListToFormList("fdWorkTime"));
			toFormPropertyMap.put("fdCategory.fdId", "fdCategoryId");
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysAttendCategoryTimesheetForm.class;
	}

	public Float getFdTotalDay() {
		//因为历史数据原因，如果没有按照1天来计算
		if(fdTotalDay==null){
			return 1F;
		}
		return fdTotalDay;
	}

	public void setFdTotalDay(Float fdTotalDay) {
		this.fdTotalDay = fdTotalDay;
	}

	/**
	 * 午休开始时间类型
	 * 1,当日
	 * 2,次日
	 */
	private Integer fdRestStartType;

	public Integer getFdRestStartType() {
		//兼容历史没设置过的，默认是当日
		if(fdRestStartType ==null){
			fdRestStartType =1;
		}
		return fdRestStartType;
	}
	public void setFdRestStartType(Integer fdRestStartType) {
		this.fdRestStartType = fdRestStartType;
	}
	/**
	 * 午休结束时间类型
	 * 1,当日
	 * 2,次日
	 */
	private Integer fdRestEndType;

	public Integer getFdRestEndType() {
		//兼容历史没设置过的，默认是当日
		if(fdRestEndType ==null){
			fdRestEndType =1;
		}
		return fdRestEndType;
	}

	public void setFdRestEndType(Integer fdRestEndType) {
		this.fdRestEndType = fdRestEndType;
	}
}
