package com.landray.kmss.sys.time.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.forms.SysTimeVacationForm;
import com.landray.kmss.util.DateUtil;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 休假设置
 */
@SuppressWarnings("serial")
public class SysTimeVacation extends BaseModel implements Cloneable {
	/*
	 * 名称
	 */
	protected java.lang.String fdName;

	/*
	 * 开始日期
	 */
	protected java.util.Date fdStartDate;

	/*
	 * 结束日期
	 */
	protected java.util.Date fdEndDate;

	/*
	 * 开始时间
	 */
	protected java.util.Date fdStartTime = DateUtil.convertStringToDate(
			"00:00:00", "HH:MM:SS");

	/*
	 * 结束时间
	 */
	protected java.util.Date fdEndTime = DateUtil.convertStringToDate(
			"00:00:00", "HH:MM:SS");

	/*
	 * 创建时间
	 */
	protected java.util.Date docCreateTime = new Date();

	/*
	 * 区域组设置
	 */
	protected SysTimeArea sysTimeArea = null;

	/*
	 * 组织架构元素
	 */
	protected SysOrgElement docCreator = null;

	/**
	 * 批量排班时使用的关联字段
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

	public SysTimeVacation() {
		super();
	}

	/**
	 * @return 返回 名称
	 */
	public java.lang.String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            要设置的 名称
	 */
	public void setFdName(java.lang.String fdName) {
		this.fdName = fdName;
	}

	public java.util.Date getFdEndDate() {
		return fdEndDate;
	}

	public void setFdEndDate(java.util.Date fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	public java.util.Date getFdStartDate() {
		return fdStartDate;
	}

	public void setFdStartDate(java.util.Date fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	/**
	 * @return 返回 开始时间
	 */
	public java.util.Date getFdStartTime() {
		return fdStartTime;
	}

	/**
	 * @param fdStartTime
	 *            要设置的 开始时间
	 */
	public void setFdStartTime(java.util.Date fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	/**
	 * @return 返回 结束时间
	 */
	public java.util.Date getFdEndTime() {
		return fdEndTime;
	}

	/**
	 * @param fdEndTime
	 *            要设置的 结束时间
	 */
	public void setFdEndTime(java.util.Date fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	public Long getHbmStartTime() {
		if (fdStartDate == null || fdStartTime == null) {
			return null;
		} else {
			return new Long(DateUtil
					.getDateTimeNumber(fdStartDate, fdStartTime));
		}
	}

	public void setHbmStartTime(Long hbmStartTime) {
		if (hbmStartTime == null) {
			fdStartDate = null;
			fdStartTime = null;
		} else {
			fdStartDate = new Date(hbmStartTime.longValue());
			fdStartTime = new Date(hbmStartTime.longValue());
		}
	}

	public Long getHbmEndTime() {
		if (fdEndDate == null || fdEndTime == null) {
			return null;
		}
 	
		return new Long(DateUtil.getDateTimeNumber(fdEndDate, fdEndTime));
	}

	public void setHbmEndTime(Long hbmEndTime) {
		if (hbmEndTime == null) {
			fdEndDate = null;
			fdEndTime = null;
		} else {
			fdEndDate = new Date(hbmEndTime.longValue());
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

	@Override
	public Class<?> getFormClass() {
		return SysTimeVacationForm.class;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("sysTimeArea.fdId", "sysTimeAreaId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("fdStartTime", new ModelConvertor_Common(
					"fdStartTime").setDateTimeType(DateUtil.TYPE_TIME));
			toFormPropertyMap.put("fdEndTime", new ModelConvertor_Common(
					"fdEndTime").setDateTimeType(DateUtil.TYPE_TIME));
			toFormPropertyMap.put("fdStartDate", new ModelConvertor_Common(
					"fdStartDate").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdEndDate", new ModelConvertor_Common(
					"fdEndDate").setDateTimeType(DateUtil.TYPE_DATE));
		}
		return toFormPropertyMap;
	}

}
