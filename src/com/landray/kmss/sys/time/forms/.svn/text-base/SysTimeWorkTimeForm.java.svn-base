package com.landray.kmss.sys.time.forms;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.time.model.SysTimeWorkTime;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimeWorkTimeForm extends ExtendForm {
	/*
	 * 班次管理ID
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
	 * 班次设置
	 */
	private SysTimeWork sysTimeWork = null;

	/**
	 * @return 返回 班次管理ID
	 */
	public String getFdWorkId() {
		return fdWorkId;
	}

	/**
	 * @param fdWorkId
	 *            要设置的 班次管理ID
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
		if (sysTimeWork != null) {
			this.fdWorkId = String.valueOf(sysTimeWork.getFdId());
		}
	}

	/*
	 * 跨天 2 当天 1
	 */
	private String fdOverTimeType = null;

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

	public String getFdOverTimeType() {
		return fdOverTimeType;
	}

	public void setFdOverTimeType(String fdOverTimeType) {
		this.fdOverTimeType = fdOverTimeType;
	}

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
		return SysTimeWorkTime.class;
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
