package com.landray.kmss.hr.staff.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.model.HrStaffAttendanceManage;
import com.landray.kmss.hr.staff.model.HrStaffImport;
import com.landray.kmss.hr.staff.service.IHrStaffAttendanceManageService;
import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;

/**
 * 考勤管理
 * 
 * @author 潘永辉 2017-1-11
 * 
 */
public class HrStaffAttendanceManageServiceImp extends
		HrStaffImportServiceImp implements IHrStaffAttendanceManageService {

	/**
	 * 根据员工ID获取有效的考勤管理
	 * 
	 * @param fdPersonInfoId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HrStaffAttendanceManage> findValidAttendanceManagesByPerson(
			String fdPersonInfoId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setRowSize(Integer.MAX_VALUE);
		hqlInfo.setOrderBy("hrStaffAttendanceManage.fdExpirationDate, hrStaffAttendanceManage.fdYear");
		buildCondition(hqlInfo, fdPersonInfoId);
		return findList(hqlInfo);
	}

	/**
	 * 获取有效的年假天数
	 * 
	 * @param fdPersonInfoId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Double getDaysOfAnnualLeaveByPerson(String fdPersonInfoId)
			throws Exception {
		return getDays(fdPersonInfoId, HrStaffPersonUtil.LEAVETYPE_ANNUALLEAVE);
	}

	/**
	 * 获取有效的调休天数
	 * 
	 * @param fdPersonInfoId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Double getDaysOfTakeWorkingByPerson(String fdPersonInfoId)
			throws Exception {
		return getDays(fdPersonInfoId, HrStaffPersonUtil.LEAVETYPE_TAKEWORKING);
	}

	/**
	 * 获取有效的带薪病假天数
	 * 
	 * @param fdPersonInfoId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Double getDaysOfSickLeaveByPerson(String fdPersonInfoId)
			throws Exception {
		return getDays(fdPersonInfoId, HrStaffPersonUtil.LEAVETYPE_SICKLEAVE);
	}
	
	private void buildCondition(HQLInfo hqlInfo, String fdPersonInfoId) {
		hqlInfo.setWhereBlock("hrStaffAttendanceManage.fdPersonInfo.fdId = :fdPersonInfoId and (hrStaffAttendanceManage.fdExpirationDate is null or hrStaffAttendanceManage.fdExpirationDate >= :fdExpirationDate)");
		hqlInfo.setParameter("fdPersonInfoId", fdPersonInfoId);
		hqlInfo.setParameter("fdExpirationDate", new Date());
	}

	@SuppressWarnings("unchecked")
	private Double getDays(String fdPersonInfoId, int leaveType)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		switch (leaveType) {
		case HrStaffPersonUtil.LEAVETYPE_TAKEWORKING: { // 调休
			hqlInfo.setSelectBlock("sum(hrStaffAttendanceManage.fdDaysOfTakeWorking)");
			break;
		}
		case HrStaffPersonUtil.LEAVETYPE_ANNUALLEAVE: { // 年假
			hqlInfo.setSelectBlock("sum(hrStaffAttendanceManage.fdDaysOfAnnualLeave)");
			break;
		}
		case HrStaffPersonUtil.LEAVETYPE_SICKLEAVE: { // 病假
			hqlInfo.setSelectBlock("sum(hrStaffAttendanceManage.fdDaysOfSickLeave)");
			break;
		}
		}
		buildCondition(hqlInfo, fdPersonInfoId);
		List<Double> list = findValue(hqlInfo);
		Double day = 0.0;
		if (list != null && !list.isEmpty()) {
			Double d = list.get(0);
			if (d != null) {
                day = d;
            }
		}
		return day;
	}

	/**
	 * 扣除有效的年假
	 * 
	 * @param fdPersonInfoId
	 * @param deductDays
	 * @throws Exception
	 */
	@Override
	public void updateDaysOfAnnualLeaveByPerson(String fdPersonInfoId,
			Double deductDays) throws Exception {
		updateDays(fdPersonInfoId, HrStaffPersonUtil.LEAVETYPE_ANNUALLEAVE,
				deductDays);
	}

	/**
	 * 扣除有效的调休假
	 * 
	 * @param fdPersonInfoId
	 * @param deductDays
	 * @throws Exception
	 */
	@Override
	public void updateDaysOfTakeWorkingByPerson(String fdPersonInfoId,
			Double deductDays) throws Exception {
		updateDays(fdPersonInfoId, HrStaffPersonUtil.LEAVETYPE_TAKEWORKING,
				deductDays);
	}

	/**
	 * 扣除有效的带薪病假
	 * 
	 * @param fdPersonInfoId
	 * @param deductDays
	 * @throws Exception
	 */
	@Override
	public void updateDaysOfSickLeaveByPerson(String fdPersonInfoId,
			Double deductDays) throws Exception {
		updateDays(fdPersonInfoId, HrStaffPersonUtil.LEAVETYPE_SICKLEAVE,
				deductDays);
	}

	private synchronized void updateDays(String fdPersonInfoId, int leaveType,
			Double deductDays) throws Exception {
		// 判断扣除的天数是否大于0
		if (deductDays.doubleValue() <= 0) {
			return;
		}

		// 获取所有的有效假期(按有效期排序)
		List<HrStaffAttendanceManage> attendanceManages = findValidAttendanceManagesByPerson(fdPersonInfoId);
		// 这里需要注意：扣除一条记录后，还有未扣除的假期天数（比如本次请假5天，而某条记录的可用天数只有3天，则需求扣除下一条记录的可用天数）
		double _deductDays = deductDays.doubleValue();
		for (HrStaffAttendanceManage attendanceManage : attendanceManages) {
			// 判断此年份的有效假期数据是否够用
			double days = 0.0;
			switch (leaveType) {
			case HrStaffPersonUtil.LEAVETYPE_TAKEWORKING: { // 调休
				days = attendanceManage.getFdDaysOfTakeWorking();
				break;
			}
			case HrStaffPersonUtil.LEAVETYPE_ANNUALLEAVE: { // 年假
				days = attendanceManage.getFdDaysOfAnnualLeave();
				break;
			}
			case HrStaffPersonUtil.LEAVETYPE_SICKLEAVE: { // 病假
				days = attendanceManage.getFdDaysOfSickLeave();
				break;
			}
			}
			// 本次要扣除的天数
			double deductDay = 0.0;
			// 判断本次记录是否够用
			if (days >= _deductDays) {
				// 够用
				deductDay = _deductDays;
				_deductDays = 0.0;
			} else {
				// 将未扣完的天数保存起来，下次循环再扣除
				deductDay = days;
				_deductDays = _deductDays - days;
			}

			switch (leaveType) {
			case HrStaffPersonUtil.LEAVETYPE_TAKEWORKING: { // 调休
				attendanceManage.setFdDaysOfTakeWorking(days - deductDay);
				break;
			}
			case HrStaffPersonUtil.LEAVETYPE_ANNUALLEAVE: { // 年假
				attendanceManage.setFdDaysOfAnnualLeave(days - deductDay);
				break;
			}
			case HrStaffPersonUtil.LEAVETYPE_SICKLEAVE: { // 病假
				attendanceManage.setFdDaysOfSickLeave(days - deductDay);
				break;
			}
			}
			update(attendanceManage); // 更新记录

			if (_deductDays <= 0) {
				break; // 没有可扣除天数了，返回
			}
		}
	}

	@Override
	public String[] getImportFields() {
		// 年份， 失效日期， 剩余年假天数， 剩余可调休天数， 当前剩余带薪病假天数
		return new String[] { "fdYear", "fdExpirationDate",
				"fdDaysOfAnnualLeave", "fdDaysOfTakeWorking",
				"fdDaysOfSickLeave" };
	}
	
	@Override
	public String getTypeString() {
		return "考勤管理";
	}
	
	@Override
	protected void saveData(HrStaffImport baseModel, boolean isNew)
			throws Exception {
		// 根据年份查询数据，如果有就更新，如果没有就新增
		HrStaffAttendanceManage model = (HrStaffAttendanceManage) baseModel;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("hrStaffAttendanceManage.fdYear = :fdYear and hrStaffAttendanceManage.fdPersonInfo = :fdPersonInfo");
		hqlInfo.setParameter("fdYear", model.getFdYear());
		hqlInfo.setParameter("fdPersonInfo", model.getFdPersonInfo());
		List<HrStaffAttendanceManage> list = findList(hqlInfo);
		if (list != null && !list.isEmpty()) {
			HrStaffAttendanceManage attendanceManage = list.get(0);
			attendanceManage.setFdExpirationDate(model.getFdExpirationDate());
			attendanceManage.setFdDaysOfAnnualLeave(model.getFdDaysOfAnnualLeave());
			attendanceManage.setFdDaysOfTakeWorking(model.getFdDaysOfTakeWorking());
			attendanceManage.setFdDaysOfSickLeave(model.getFdDaysOfSickLeave());
			update(attendanceManage);
		} else {
			add(baseModel);
		}
	}

}
