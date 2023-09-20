package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffAttendanceManageDetailedForm;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;

/**
 * 个人假期明细
 * 
 * @author 潘永辉 2017-1-11
 * 
 */
public class HrStaffAttendanceManageDetailed extends HrStaffBaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 操作类型：请假
	 */
	public static final Integer TYPE_LEAVE = 1;
	/**
	 * 操作类型：加班
	 */
	public static final Integer TYPE_OVERTIME = 2;

	/**
	 * 假期类型：调休
	 */
	public static final String LEAVE_TYPE_TAKEWORKING = "takeWorking";
	/**
	 * 假期类型：年假
	 */
	public static final String LEAVE_TYPE_ANNUALLEAVE = "annualLeave";
	/**
	 * 假期类型：病假
	 */
	public static final String LEAVE_TYPE_SICKLEAVE = "sickLeave";

	// 请假（加班）天数
	private Double fdLeaveDays = 0.0;
	// 相关流程
	private String fdRelatedProcess;
	// 开始日期
	private Date fdBeginDate;
	// 结束日期
	private Date fdEndDate;
	// 请假（加班）类型
	private String fdLeaveType;
	// 有异常，当假期扣除失败时标记为异常
	private Boolean fdException;
	// 操作类型
	private Integer fdType;
	//流程标题
	private String fdSubject;

	@Override
	public Class getFormClass() {
		return HrStaffAttendanceManageDetailedForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}

	public Double getFdLeaveDays() {
		return fdLeaveDays;
	}

	public void setFdLeaveDays(Double fdLeaveDays) {
		this.fdLeaveDays = fdLeaveDays;
	}

	public String getFdRelatedProcess() {
		return fdRelatedProcess;
	}

	public void setFdRelatedProcess(String fdRelatedProcess) {
		this.fdRelatedProcess = fdRelatedProcess;
	}

	public Date getFdBeginDate() {
		return fdBeginDate;
	}

	public void setFdBeginDate(Date fdBeginDate) {
		this.fdBeginDate = fdBeginDate;
	}

	public Date getFdEndDate() {
		return fdEndDate;
	}

	public void setFdEndDate(Date fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	public String getFdLeaveType() {
		return fdLeaveType;
	}

	public void setFdLeaveType(String fdLeaveType) {
		this.fdLeaveType = fdLeaveType;
	}

	public Boolean getFdException() {
		if (fdException == null) {
            return false;
        }
		return fdException;
	}

	public void setFdException(Boolean fdException) {
		if (fdException == null) {
            fdException = false;
        }
		this.fdException = fdException;
	}

	public Integer getFdType() {
		if (fdType == null) {
			// 兼容历史数据，为空时默认请假
			return TYPE_LEAVE;
		}
		return fdType;
	}

	public void setFdType(Integer fdType) {
		this.fdType = fdType;
	}

	/**
	 * 获取假期类型
	 * 
	 * @return
	 */
	public String getLeaveType() {
		if (fdLeaveType != null) {
			if (LEAVE_TYPE_TAKEWORKING.equals(fdLeaveType)
					|| LEAVE_TYPE_ANNUALLEAVE.equals(fdLeaveType)
					|| LEAVE_TYPE_SICKLEAVE.equals(fdLeaveType)) {
				return ResourceUtil.getString("hr-staff:hrStaff.robot.optionValue.overtime." + fdLeaveType);
			} else {
				return fdLeaveType;
			}
		}
		return "";
	}

	public String getBeginDateForString() {
		return getStringDate(fdBeginDate);
	}

	public String getEndDateForString() {
		return getStringDate(fdEndDate);
	}

	private String getStringDate(Date date) {
		if (date == null) {
            return "";
        }
		// 判断一下是否有时间，有则显示日期 + 时间，没有则只显示日期
		String s_time = DateUtil.convertDateToString(date, "HH:mm:ss");
		Date d = DateUtil.convertStringToDate(s_time, "HH:mm:ss");
		if (d.getTime() == -28800000) { // 没有时间
			return DateUtil.convertDateToString(date, ResourceUtil.getString("date.format.date"));
		} else { // 有时间
			return DateUtil.convertDateToString(date, ResourceUtil.getString("date.format.datetime"));
		}
	}

	public String getFdSubject() {
		return fdSubject;
	}

	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}
}
