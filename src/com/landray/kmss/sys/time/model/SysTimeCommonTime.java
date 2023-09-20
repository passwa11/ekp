package com.landray.kmss.sys.time.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.time.forms.SysTimeCommonTimeForm;
import com.landray.kmss.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/***
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 班次时间
 */
public class SysTimeCommonTime extends BaseModel {
	/*** 名称 */
	protected java.lang.String fdName;
	/**
	 * 简称
	 */
	protected java.lang.String simpleName;
	/**
	 * 状态
	 */
	protected java.lang.String status;
	protected java.lang.String isSchedule;
	protected Integer fdOrder;
	/**
	 * 类型 1.通用 2.自定义
	 */
	protected java.lang.String type = "1";
	/**
	 * 午休开始时间
	 */
	protected java.util.Date fdRestStartTime;
	/**
	 * 午休结束时间
	 */
	protected java.util.Date fdRestEndTime;

	/**
	 * 总工时
	 */
	protected java.lang.String fdWorkHour;

	/***
	 * 总工时 半天还是一天。用于统计
	 */
	protected Float fdTotalDay;
	/*** 班次展示颜色 */
	protected String fdWorkTimeColor;
	/*** 工作时间列表 */
	protected List<SysTimeWork> sysTimeWorkList = new ArrayList<SysTimeWork>();
	/*** 补班时间列表 */
	protected List<SysTimePatchwork> sysTimePatchwork = new ArrayList<SysTimePatchwork>();
	/*** 班次明细 */
	protected List<SysTimeWorkDetail> sysTimeWorkDetails = new ArrayList<SysTimeWorkDetail>();

	public List<SysTimeWorkDetail> getSysTimeWorkDetails() {
		return sysTimeWorkDetails;
	}

	public void
			setSysTimeWorkDetails(List<SysTimeWorkDetail> sysTimeWorkDetails) {
		this.sysTimeWorkDetails = sysTimeWorkDetails;
	}

	public List<SysTimeWork> getSysTimeWorkList() {
		return sysTimeWorkList;
	}

	public void setSysTimeWorkList(List<SysTimeWork> sysTimeWorkList) {
		this.sysTimeWorkList = sysTimeWorkList;
	}

	public SysTimeCommonTime() {
		super();
	}

	public Long getHbmRestStartTime() {
		if (fdRestStartTime == null) {
			return null;
		}
		return new Long(DateUtil.getTimeNubmer(fdRestStartTime));
	}

	public void setHbmRestStartTime(Long hbmRestStartTime) {
		if (hbmRestStartTime == null) {
			fdRestStartTime = null;
		} else {
			fdRestStartTime = DateUtil.getTimeByNubmer(hbmRestStartTime
					.longValue());
		}
	}

	public Long getHbmRestEndTime() {
		if (fdRestEndTime == null) {
			return null;
		}
		return new Long(DateUtil.getTimeNubmer(fdRestEndTime));
	}

	public void setHbmRestEndTime(Long hbmRestEndTime) {
		if (hbmRestEndTime == null) {
			fdRestEndTime = null;
		} else {
			fdRestEndTime = DateUtil
					.getTimeByNubmer(hbmRestEndTime.longValue());
		}
	}

	public java.lang.String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(java.lang.String simpleName) {
		this.simpleName = simpleName;
	}

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getIsSchedule() {
		return isSchedule;
	}

	public void setIsSchedule(java.lang.String isSchedule) {
		this.isSchedule = isSchedule;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.util.Date getFdRestStartTime() {
		return fdRestStartTime;
	}

	public void setFdRestStartTime(java.util.Date fdRestStartTime) {
		this.fdRestStartTime = fdRestStartTime;
	}

	public java.util.Date getFdRestEndTime() {
		return fdRestEndTime;
	}

	public void setFdRestEndTime(java.util.Date fdRestEndTime) {
		this.fdRestEndTime = fdRestEndTime;
	}

	public java.lang.String getFdWorkHour() {
		return fdWorkHour;
	}

	public void setFdWorkHour(java.lang.String fdWorkHour) {
		this.fdWorkHour = fdWorkHour;
	}

	public String getFdWorkTimeColor() {
		return fdWorkTimeColor;
	}

	public void setFdWorkTimeColor(String fdWorkTimeColor) {
		this.fdWorkTimeColor = fdWorkTimeColor;
	}

	public java.lang.String getFdName() {
		return fdName;
	}

	public void setFdName(java.lang.String fdName) {
		this.fdName = fdName;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	@Override
	public Class getFormClass() {
		return SysTimeCommonTimeForm.class;
	}

	public List<SysTimePatchwork> getSysTimePatchwork() {
		return sysTimePatchwork;
	}

	public void setSysTimePatchwork(List<SysTimePatchwork> sysTimePatchwork) {
		this.sysTimePatchwork = sysTimePatchwork;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdRestStartTime", new ModelConvertor_Common(
					"fdRestStartTime").setDateTimeType(DateUtil.TYPE_TIME));
			toFormPropertyMap.put("fdRestEndTime", new ModelConvertor_Common(
					"fdRestEndTime").setDateTimeType(DateUtil.TYPE_TIME));
			toFormPropertyMap.put("sysTimeWorkDetails",
					new ModelConvertor_ModelListToFormList(
							"sysTimeWorkDetails"));
			toFormPropertyMap.put("authArea.fdId", "authAreaId");
			toFormPropertyMap.put("authArea.fdName", "authAreaName");
		}
		return toFormPropertyMap;
	}

	/**
	 * 所属场所
	 */
	protected SysAuthArea authArea;

	public SysAuthArea getAuthArea() {
		return authArea;
	}

	public void setAuthArea(SysAuthArea authArea) {
		this.authArea = authArea;
	}

	public Float getFdTotalDay() {
		if(fdTotalDay ==null){
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
	/***
	 * 午休结束时间的标识，
	 * 2次日，1当日
	 */
	private Integer fdRestEndType;

	public Integer getFdRestEndType() {
		if(fdRestEndType ==null){
			fdRestEndType =1;
		}
		return fdRestEndType;
	}

	public void setFdRestEndType(Integer fdRestEndType) {
		this.fdRestEndType = fdRestEndType;
	}
}
