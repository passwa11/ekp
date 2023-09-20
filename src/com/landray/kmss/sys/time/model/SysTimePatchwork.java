package com.landray.kmss.sys.time.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.forms.SysTimePatchworkForm;
import com.landray.kmss.util.DateUtil;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 补班设置
 */
@SuppressWarnings("serial")
public class SysTimePatchwork extends BaseModel implements Cloneable {
	/*
	 * 名称
	 */
	protected java.lang.String fdName;

	/*
	 * 开始时间
	 */
	protected java.util.Date fdStartTime;

	/*
	 * 结束时间
	 */
	protected java.util.Date fdEndTime;

	/*
	 * 创建时间
	 */
	protected java.util.Date docCreateTime = new Date();
	/*
	 * 类型 1.通用 2.自定义
	 */
	protected java.lang.String timeType;

	/*
	 * 区域组设置
	 */
	protected SysTimeArea sysTimeArea = null;

	/*
	 * 组织架构元素
	 */
	protected SysOrgElement docCreator = null;
	/*
	 * 补班时间展示颜色
	 */
	protected String fdPatchWorkColor = null;

	/*
	 * 班次列表
	 */
	protected SysTimeCommonTime sysTimeCommonTime = null;
	protected List<SysTimePatchworkTime> sysTimePatchworkTimeList = new ArrayList<SysTimePatchworkTime>();

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

	public SysTimePatchwork() {
		super();
	}

	/**
	 * @return 返回 补班时间展示颜色
	 */
	public String getFdPatchWorkColor() {
		return fdPatchWorkColor;
	}

	public void setFdPatchWorkColor(String fdPatchWorkColor) {
		this.fdPatchWorkColor = fdPatchWorkColor;
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

	public java.lang.String getTimeType() {
		return timeType;
	}

	public void setTimeType(java.lang.String timeType) {
		this.timeType = timeType;
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

	/**
	 * @return 返回 组织架构元素
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	/**
	 * @param sysOrgElement
	 *            要设置的 组织架构元素
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	public List<SysTimePatchworkTime> getSysTimePatchworkTimeList() {
		return sysTimePatchworkTimeList;
	}

	public void setSysTimePatchworkTimeList(
			List<SysTimePatchworkTime> sysTimePatchworkTimeList) {
		this.sysTimePatchworkTimeList = sysTimePatchworkTimeList;
	}

	public SysTimeCommonTime getSysTimeCommonTime() {
		return sysTimeCommonTime;
	}

	public void setSysTimeCommonTime(SysTimeCommonTime sysTimeCommonTime) {
		this.sysTimeCommonTime = sysTimeCommonTime;
	}

	@Override
	public Class<?> getFormClass() {
		return SysTimePatchworkForm.class;
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
			toFormPropertyMap.put("sysTimeCommonTime.fdId",
					"sysTimeCommonId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("fdStartTime", new ModelConvertor_Common(
					"fdStartTime").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdEndTime", new ModelConvertor_Common(
					"fdEndTime").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("sysTimePatchworkTimeList",
					new ModelConvertor_ModelListToFormList(
							"sysTimePatchworkTimeFormList"));
		}
		return toFormPropertyMap;
	}
}
