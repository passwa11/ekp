package com.landray.kmss.sys.time.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.time.forms.SysTimePatchworkTimeForm;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 补班班次
 */
public class SysTimePatchworkTime extends BaseModel implements Cloneable {

	/*
	 * 上班时间
	 */
	protected java.util.Date fdWorkStartTime;

	/*
	 * 下班时间
	 */
	protected java.util.Date fdWorkEndTime;

	/*
	 * 补班设置
	 */
	protected SysTimePatchwork sysTimePatchwork = null;

	public SysTimePatchworkTime() {
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
	 * @return 返回 补班设置
	 */
	public SysTimePatchwork getSysTimePatchwork() {
		return sysTimePatchwork;
	}

	/**
	 * @param sysTimePatchwork
	 *            要设置的 补班设置
	 */
	public void setSysTimePatchwork(SysTimePatchwork sysTimePatchwork) {
		this.sysTimePatchwork = sysTimePatchwork;
	}

	@Override
	public Class getFormClass() {
		return SysTimePatchworkTimeForm.class;
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

			toFormPropertyMap.put("fdStartTime", new ModelConvertor_Common(
					"fdStartTime").setDateTimeType(DateUtil.TYPE_TIME));

			toFormPropertyMap.put("fdOverTime", new ModelConvertor_Common(
					"fdOverTime").setDateTimeType(DateUtil.TYPE_TIME));
		}
		return toFormPropertyMap;
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


	/**
	 * 最早上班时间
	 */
	protected Date fdStartTime;

	/**
	 * 最晚下班时间
	 */
	protected Date fdOverTime;

	/**
	 * 最晚下班时间的类型-跨天（今日还是次日）
	 */
	protected Integer fdEndOverTimeType;


	public Date getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(Date fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	public Date getFdOverTime() {
		return fdOverTime;
	}

	public void setFdOverTime(Date fdOverTime) {
		this.fdOverTime = fdOverTime;
	}

	public Integer getFdEndOverTimeType() {
		return fdEndOverTimeType;
	}

	public void setFdEndOverTimeType(Integer fdEndOverTimeType) {
		this.fdEndOverTimeType = fdEndOverTimeType;
	}
	/**
	 * 最早打卡时间
	 */
	public Long getHbmStartTime() {
		if (fdStartTime == null) {
			return null;
		}
		return new Long(DateUtil.getTimeNubmer(fdStartTime));
	}
	/**
	 * 最早打卡时间
	 */
	public void setHbmStartTime(Long hbmStartTime) {
		if (hbmStartTime == null) {
			fdStartTime = null;
		} else {
			fdStartTime = DateUtil.getTimeByNubmer(hbmStartTime.longValue());
		}
	}
	/**
	 * 最晚打卡时间-结束时间
	 */
	public Long getHbmFdOverTime() {
		if (fdOverTime == null) {
			return null;
		}
		return new Long(DateUtil.getTimeNubmer(fdOverTime));
	}

	/**
	 * 最晚打卡时间-结束时间
	 * @param hbmFdOverTime
	 */
	public void setHbmFdOverTime(Long hbmFdOverTime) {
		if (hbmFdOverTime == null) {
			fdOverTime = null;
		} else {
			fdOverTime = DateUtil.getTimeByNubmer(hbmFdOverTime.longValue());
		}
	}
}
