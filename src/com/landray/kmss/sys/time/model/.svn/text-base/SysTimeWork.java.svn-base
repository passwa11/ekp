package com.landray.kmss.sys.time.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.forms.SysTimeWorkForm;
import com.landray.kmss.util.DateUtil;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 班次设置
 */
@SuppressWarnings("serial")
public class SysTimeWork extends BaseModel implements Cloneable {
	/** 从星期几开始 */
	protected java.lang.Long fdWeekStartTime;

	/** 到星期几结束 */
	protected java.lang.Long fdWeekEndTime;

	/** 工作的有效开始时间 */
	protected java.util.Date fdStartTime;

	/** 工作的有效结束时间 */
	protected java.util.Date fdEndTime;
	protected String fdName;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/** 创建时间 */
	protected java.util.Date docCreateTime = new Date();

	/** 区域组设置 */
	protected SysTimeArea sysTimeArea = null;

	/** 组织架构元素 */
	protected SysOrgElement docCreator = null;
	/*
	 * 类型 1.通用 2.自定义
	 */
	protected java.lang.String timeType;
	/** 工作时间展示颜色 */
	protected String fdTimeWorkColor;

	/** 班次列表 */
	protected SysTimeCommonTime sysTimeCommonTime = null;
	protected List<SysTimeWorkTime> sysTimeWorkTimeList = new ArrayList<SysTimeWorkTime>();

	/**
	 * 个人排班时使用的关联字段
	 */
	protected SysTimeOrgElementTime sysTimeOrgElementTime = null;

	public SysTimeOrgElementTime getSysTimeOrgElementTime() {
		return sysTimeOrgElementTime;
	}

	public void
			setSysTimeOrgElementTime(
					SysTimeOrgElementTime sysTimeOrgElementTime) {
		this.sysTimeOrgElementTime = sysTimeOrgElementTime;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public SysTimeWork() {
		super();
	}

	public java.lang.String getTimeType() {
		return timeType;
	}

	public void setTimeType(java.lang.String timeType) {
		this.timeType = timeType;
	}


	public List<SysTimeWorkTime> getSysTimeWorkTimeList() {
		return sysTimeWorkTimeList;
	}

	public void
			setSysTimeWorkTimeList(List<SysTimeWorkTime> sysTimeWorkTimeList) {
		this.sysTimeWorkTimeList = sysTimeWorkTimeList;
	}

	/**
	 * @return 返回 从星期几开始
	 */
	public java.lang.Long getFdWeekStartTime() {
		return fdWeekStartTime;
	}

	/**
	 * @param fdWeekStartTime
	 *            要设置的 从星期几开始
	 */
	public void setFdWeekStartTime(java.lang.Long fdWeekStartTime) {
		this.fdWeekStartTime = fdWeekStartTime;
	}
	/*
	 * 
	 * @param fdTimeWorkColor 工作时间展示颜色
	 */

	public String getFdTimeWorkColor() {
		return fdTimeWorkColor;
	}

	public void setFdTimeWorkColor(String fdTimeWorkColor) {
		this.fdTimeWorkColor = fdTimeWorkColor;
	}
	/**
	 * @return 返回 到星期几结束
	 */
	public java.lang.Long getFdWeekEndTime() {
		return fdWeekEndTime;
	}

	/**
	 * @param fdWeekEndTime
	 *            要设置的 到星期几结束
	 */
	public void setFdWeekEndTime(java.lang.Long fdWeekEndTime) {
		this.fdWeekEndTime = fdWeekEndTime;
	}

	/**
	 * @return 返回 工作的有效开始时间
	 */
	public java.util.Date getFdStartTime() {
		return fdStartTime;
	}

	/**
	 * @param fdStartTime
	 *            要设置的 工作的有效开始时间
	 */
	public void setFdStartTime(java.util.Date fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	/**
	 * @return 返回 工作的有效结束时间
	 */
	public java.util.Date getFdEndTime() {
		return fdEndTime;
	}

	/**
	 * @param fdEndTime
	 *            要设置的 工作的有效结束时间
	 */
	public void setFdEndTime(java.util.Date fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	public Long getHbmStartTime() {
		if (fdStartTime == null) {
			return null;
		}
		return new Long(DateUtil.getDateNumber(fdStartTime));
	}

	public void setHbmStartTime(Long hbmStartTime) {
		if (hbmStartTime == null) {
			fdStartTime = null;
		} else {
			fdStartTime = new Date(hbmStartTime.longValue());
		}
	}

	public Long getHbmEndTime() {
		if (fdEndTime == null) {
			return null;
		}
		return new Long(DateUtil.getDateNumber(fdEndTime) + DateUtil.DAY - 1);
	}

	public void setHbmEndTime(Long hbmEndTime) {
		if (hbmEndTime == null) {
			fdEndTime = null;
		} else {
			fdEndTime = new Date(hbmEndTime.longValue());
		}
	}

	/**
	 * @return 返回 创建时间
	 */
	public java.util.Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	public void setDocCreateTime(java.util.Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * @return 返回 区域组设置
	 */
	public SysTimeArea getSysTimeArea() {
		return sysTimeArea;
	}

	/**
	 * @param sysTimeArea
	 *            要设置的 区域组设置
	 */
	public void setSysTimeArea(SysTimeArea sysTimeArea) {
		this.sysTimeArea = sysTimeArea;
	}

	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	public SysTimeCommonTime getSysTimeCommonTime() {
		return sysTimeCommonTime;
	}

	public void setSysTimeCommonTime(SysTimeCommonTime sysTimeCommonTime) {
		this.sysTimeCommonTime = sysTimeCommonTime;
	}

	/**
	 * 批量排班时使用，用于记录某一天的排班信息
	 */
	protected Date fdScheduleDate;

	public Date getFdScheduleDate() {
		return fdScheduleDate;
	}

	public void setFdScheduleDate(Date fdScheduleDate) {
		this.fdScheduleDate = fdScheduleDate;
	}

	@Override
	public Class<?> getFormClass() {
		return SysTimeWorkForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("sysTimeCommonTime.fdId",
					"sysTimeCommonId");
			toFormPropertyMap.put("sysTimeArea.fdId", "sysTimeAreaId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("fdStartTime", new ModelConvertor_Common(
					"fdStartTime").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdEndTime", new ModelConvertor_Common(
					"fdEndTime").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("sysTimeWorkTimeList",
					new ModelConvertor_ModelListToFormList(
							"sysTimeWorkTimeFormList"));
		}
		return toFormPropertyMap;
	}

}
