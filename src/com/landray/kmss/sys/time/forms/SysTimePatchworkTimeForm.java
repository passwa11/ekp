package com.landray.kmss.sys.time.forms;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimePatchworkTime;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimePatchworkTimeForm extends ExtendForm {
	/*
	 * 补班管理ID
	 */
	private String fdWorkId = null;

	/*
	 * 上班时间
	 */
	private String fdWorkStartTime = null;

	/*
	 * 下班时间
	 */
	private String fdWorkEndTime = null;

	/*
	 * 补班设置
	 */
	private SysTimePatchwork sysTimePatchwork = null;

	/**
	 * @return 返回 补班管理ID
	 */
	public String getFdWorkId() {
		return fdWorkId;
	}

	/**
	 * @param fdWorkId
	 *            要设置的 补班管理ID
	 */
	public void setFdWorkId(String fdWorkId) {
		this.fdWorkId = fdWorkId;
	}

	/**
	 * @return 返回 上班时间
	 */
	public String getFdWorkStartTime() {
		return fdWorkStartTime;
	}

	/**
	 * @param fdWorkStartTime
	 *            要设置的 上班时间
	 */
	public void setFdWorkStartTime(String fdWorkStartTime) {
		this.fdWorkStartTime = fdWorkStartTime;
	}

	/**
	 * @return 返回 下班时间
	 */
	public String getFdWorkEndTime() {
		return fdWorkEndTime;
	}

	/**
	 * @param fdWorkEndTime
	 *            要设置的 下班时间
	 */
	public void setFdWorkEndTime(String fdWorkEndTime) {
		this.fdWorkEndTime = fdWorkEndTime;
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
		if (sysTimePatchwork != null) {
			this.fdWorkId = String.valueOf(sysTimePatchwork.getFdId());
		}

	}

	/**
	 * 跨天 2 当天 1
	 */
	private String fdOverTimeType = null;

	public String getFdOverTimeType() {
		return fdOverTimeType;
	}

	public void setFdOverTimeType(String fdOverTimeType) {
		this.fdOverTimeType = fdOverTimeType;
	}

	/**
	 * 最早上班时间
	 */
	private String fdStartTime;

	/**
	 * 最晚下班时间
	 */
	private String fdOverTime;

	/**
	 * 最晚下班时间的类型-跨天（今日还是次日）
	 */
	private Integer fdEndOverTimeType;

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.landray.kmss.web.action.ActionForm#reset(com.landray.kmss.web.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdWorkId = null;
		fdWorkStartTime = null;
		fdWorkEndTime = null;
		fdOverTimeType = null;

		fdStartTime = null;
		fdOverTime = null;
		fdEndOverTimeType = null;

		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return SysTimePatchworkTime.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdWorkStartTime",
					new FormConvertor_Common("fdWorkStartTime")
							.setDateTimeType(DateUtil.TYPE_TIME));
			toModelPropertyMap.put("fdWorkEndTime",
					new FormConvertor_Common("fdWorkEndTime")
							.setDateTimeType(DateUtil.TYPE_TIME));

			toModelPropertyMap.put("fdStartTime",
					new FormConvertor_Common("fdStartTime")
							.setDateTimeType(DateUtil.TYPE_TIME));

			toModelPropertyMap.put("fdOverTime",
					new FormConvertor_Common("fdOverTime")
							.setDateTimeType(DateUtil.TYPE_TIME));
		}
		return toModelPropertyMap;
	}


	public String getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(String fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	public String getFdOverTime() {
		return fdOverTime;
	}

	public void setFdOverTime(String fdOverTime) {
		this.fdOverTime = fdOverTime;
	}

	public Integer getFdEndOverTimeType() {
		return fdEndOverTimeType;
	}

	public void setFdEndOverTimeType(Integer fdEndOverTimeType) {
		this.fdEndOverTimeType = fdEndOverTimeType;
	}
}
