package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffAttendanceManageForm;
import com.landray.kmss.util.DateUtil;

/**
 * 考勤管理
 * 
 * @author 潘永辉 2017-1-11
 * 
 */
public class HrStaffAttendanceManage extends HrStaffBaseModel {
	private static final long serialVersionUID = 1L;

	// 年份
	private Integer fdYear = 0;
	// 失效日期
	private Date fdExpirationDate;
	// 剩余年假天数
	private Double fdDaysOfAnnualLeave;
	// 剩余调休天数
	private Double fdDaysOfTakeWorking;
	// 剩余带薪病假天数
	private Double fdDaysOfSickLeave;

	@Override
	public Class getFormClass() {
		return HrStaffAttendanceManageForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());

			// 由于界面没有datetime选择，只使用date
			toFormPropertyMap.put("fdExpirationDate",
					new ModelConvertor_Common("fdExpirationDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
		}
		return toFormPropertyMap;
	}

	public Integer getFdYear() {
		return fdYear;
	}

	public void setFdYear(Integer fdYear) {
		this.fdYear = fdYear;
	}

	public Date getFdExpirationDate() {
		return fdExpirationDate;
	}

	public void setFdExpirationDate(Date fdExpirationDate) {
		this.fdExpirationDate = fdExpirationDate;
	}

	public Double getFdDaysOfAnnualLeave() {
		if (fdDaysOfAnnualLeave == null) {
            return 0.0;
        }
		return fdDaysOfAnnualLeave;
	}

	public void setFdDaysOfAnnualLeave(Double fdDaysOfAnnualLeave) {
		this.fdDaysOfAnnualLeave = fdDaysOfAnnualLeave;
	}

	public Double getFdDaysOfTakeWorking() {
		if (fdDaysOfTakeWorking == null) {
            return 0.0;
        }
		return fdDaysOfTakeWorking;
	}

	public void setFdDaysOfTakeWorking(Double fdDaysOfTakeWorking) {
		this.fdDaysOfTakeWorking = fdDaysOfTakeWorking;
	}

	public Double getFdDaysOfSickLeave() {
		if (fdDaysOfSickLeave == null) {
            return 0.0;
        }
		return fdDaysOfSickLeave;
	}

	public void setFdDaysOfSickLeave(Double fdDaysOfSickLeave) {
		this.fdDaysOfSickLeave = fdDaysOfSickLeave;
	}

	/****************** 以下方法目的是在页面上显示的整数不带小数点（如：不显示2.0，而是显示2） *********************/
	public String getDaysOfAnnualLeave() {
		return convertDoubleToString(fdDaysOfAnnualLeave);
	}

	public String getDaysOfTakeWorking() {
		return convertDoubleToString(fdDaysOfTakeWorking);
	}

	public String getDaysOfSickLeave() {
		return convertDoubleToString(fdDaysOfSickLeave);
	}

	private String convertDoubleToString(Double value) {
		if (value != null) {
			if (equalDouble(value.doubleValue() % 1.0, 0)) {
				return String.valueOf(value.longValue());
			}
			return String.valueOf(value);
		}
		return "";
	}
	
	public boolean equalDouble(double a, double b) {
		if ((a - b > -0.000001) && (a - b) < 0.000001) {
            return true;
        } else {
            return false;
        }
	}

}
