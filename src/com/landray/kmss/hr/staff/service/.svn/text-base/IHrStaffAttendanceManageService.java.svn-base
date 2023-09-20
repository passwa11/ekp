package com.landray.kmss.hr.staff.service;

import java.util.List;

import com.landray.kmss.hr.staff.model.HrStaffAttendanceManage;

/**
 * 考勤管理
 * 
 * @author 潘永辉 2017-1-11
 * 
 */
public interface IHrStaffAttendanceManageService extends
		IHrStaffImportService {

	/**
	 * 根据员工ID获取有效的考勤管理
	 * 
	 * @param fdPersonInfoId
	 * @return
	 * @throws Exception
	 */
	public List<HrStaffAttendanceManage> findValidAttendanceManagesByPerson(
			String fdPersonInfoId) throws Exception;

	/**
	 * 获取有效的年假天数
	 * 
	 * @param fdPersonInfoId
	 * @return
	 * @throws Exception
	 */
	public Double getDaysOfAnnualLeaveByPerson(String fdPersonInfoId)
			throws Exception;

	/**
	 * 获取有效的调休天数
	 * 
	 * @param fdPersonInfoId
	 * @return
	 * @throws Exception
	 */
	public Double getDaysOfTakeWorkingByPerson(String fdPersonInfoId)
			throws Exception;

	/**
	 * 获取有效的带薪病假天数
	 * 
	 * @param fdPersonInfoId
	 * @return
	 * @throws Exception
	 */
	public Double getDaysOfSickLeaveByPerson(String fdPersonInfoId)
			throws Exception;

	/**
	 * 扣除有效的年假
	 * 
	 * @param fdPersonInfoId
	 * @param deductDays
	 * @throws Exception
	 */
	public void updateDaysOfAnnualLeaveByPerson(String fdPersonInfoId,
			Double deductDays) throws Exception;

	/**
	 * 扣除有效的调休假
	 * 
	 * @param fdPersonInfoId
	 * @param deductDays
	 * @throws Exception
	 */
	public void updateDaysOfTakeWorkingByPerson(String fdPersonInfoId,
			Double deductDays) throws Exception;

	/**
	 * 扣除有效的带薪病假
	 * 
	 * @param fdPersonInfoId
	 * @param deductDays
	 * @throws Exception
	 */
	public void updateDaysOfSickLeaveByPerson(String fdPersonInfoId,
			Double deductDays) throws Exception;
}
