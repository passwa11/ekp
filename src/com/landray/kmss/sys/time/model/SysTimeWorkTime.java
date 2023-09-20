package com.landray.kmss.sys.time.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.time.forms.SysTimeWorkTimeForm;
import com.landray.kmss.util.DateUtil;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 班次时间
 */
public class SysTimeWorkTime extends BaseModel implements Cloneable {

	/*
	 * 上班时间
	 */
	protected java.util.Date fdWorkStartTime;

	/*
	 * 下班时间
	 */
	protected java.util.Date fdWorkEndTime;

	/*
	 * 班次设置
	 */
	protected SysTimeWork sysTimeWork = null;

	public SysTimeWorkTime() {
		super();
	}

	/**
	 * @return 返回 上班时间
	 */
	public java.util.Date getFdWorkStartTime() {
		return fdWorkStartTime;
	}

	/**
	 * @param fdWorkStartTime
	 *            要设置的 上班时间
	 */
	public void setFdWorkStartTime(java.util.Date fdWorkStartTime) {
		this.fdWorkStartTime = fdWorkStartTime;
	}

	public Long getHbmWorkStartTime() {
		if (fdWorkStartTime == null) {
			return null;
		}
		return new Long(DateUtil.getTimeNubmer(fdWorkStartTime));
	}

	public void setHbmWorkStartTime(Long hbmWorkStartTime) {
		if (hbmWorkStartTime == null) {
			fdWorkStartTime = null;
		} else {
			fdWorkStartTime = DateUtil.getTimeByNubmer(hbmWorkStartTime
					.longValue());
		}
	}

	/**
	 * @return 返回 下班时间
	 */
	public java.util.Date getFdWorkEndTime() {
		return fdWorkEndTime;
	}

	/**
	 * @param fdWorkEndTime
	 *            要设置的 下班时间
	 */
	public void setFdWorkEndTime(java.util.Date fdWorkEndTime) {
		this.fdWorkEndTime = fdWorkEndTime;
	}

	public Long getHbmWorkEndTime() {
		if (fdWorkEndTime == null) {
			return null;
		}
		return new Long(DateUtil.getTimeNubmer(fdWorkEndTime));
	}

	public void setHbmWorkEndTime(Long hbmWorkEndTime) {
		if (hbmWorkEndTime == null) {
			fdWorkEndTime = null;
		} else {
			fdWorkEndTime = DateUtil
					.getTimeByNubmer(hbmWorkEndTime.longValue());
		}
	}

	/**
	 * @return 返回 班次设置
	 */
	public SysTimeWork getSysTimeWork() {
		return sysTimeWork;
	}

	/**
	 * @param sysTimeWork
	 *            要设置的 班次设置
	 */
	public void setSysTimeWork(SysTimeWork sysTimeWork) {
		this.sysTimeWork = sysTimeWork;
	}

	@Override
	public Class getFormClass() {
		return SysTimeWorkTimeForm.class;
	}

	/*
	 * 跨天
	 */
	protected Integer fdOverTimeType;

	public Integer getFdOverTimeType() {
		return fdOverTimeType;
	}

	public void setFdOverTimeType(Integer fdOverTimeType) {
		this.fdOverTimeType = fdOverTimeType;
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
			toFormPropertyMap.put("fdWorkStartTime", new ModelConvertor_Common(
					"fdWorkStartTime").setDateTimeType(DateUtil.TYPE_TIME));
			toFormPropertyMap.put("fdWorkEndTime", new ModelConvertor_Common(
					"fdWorkEndTime").setDateTimeType(DateUtil.TYPE_TIME));
		}
		return toFormPropertyMap;
	}
}
