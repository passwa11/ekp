package com.landray.kmss.sys.time.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.time.forms.SysTimeWorkDetailForm;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
 * 排班时间的配置
 * @author 王京
 */
public class SysTimeWorkDetail extends BaseModel implements Cloneable {
	/**
	 * 上班时间
	 */
	protected Date fdWorkStartTime;

	/**
	 * 下班时间
	 */
	protected Date fdWorkEndTime;

	protected SysTimeCommonTime sysTimeCommonTime = null;

	/**
	 * 跨天
	 */
	protected Integer fdOverTimeType;

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

	public Integer getFdOverTimeType() {
		return fdOverTimeType;
	}

	public void setFdOverTimeType(Integer fdOverTimeType) {
		this.fdOverTimeType = fdOverTimeType;
	}

	public java.util.Date getFdWorkStartTime() {
		return fdWorkStartTime;
	}

	public void setFdWorkStartTime(java.util.Date fdWorkStartTime) {
		this.fdWorkStartTime = fdWorkStartTime;
	}

	public java.util.Date getFdWorkEndTime() {
		return fdWorkEndTime;
	}

	public void setFdWorkEndTime(java.util.Date fdWorkEndTime) {
		this.fdWorkEndTime = fdWorkEndTime;
	}

	public SysTimeCommonTime getSysTimeCommonTime() {
		return sysTimeCommonTime;
	}

	public void setSysTimeCommonTime(SysTimeCommonTime sysTimeCommonTime) {
		this.sysTimeCommonTime = sysTimeCommonTime;
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

	@Override
	public Class getFormClass() {
		return SysTimeWorkDetailForm.class;
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

}
