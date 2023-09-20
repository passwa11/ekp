package com.landray.kmss.hr.staff.transfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.model.HrStaffAttendanceManage;
import com.landray.kmss.hr.staff.model.HrStaffAttendanceManageDetailed;
import com.landray.kmss.hr.staff.service.IHrStaffAttendanceManageDetailedService;
import com.landray.kmss.hr.staff.service.IHrStaffAttendanceManageService;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmount;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;

/**
 * 数据迁移，人事档案 -> 排版管理
 * 
 * HrStaffAttendanceManage -> SysTimeLeaveAmount <br>
 * HrStaffAttendanceManageDetailed -> SysTimeLeaveDetail
 *
 * @author cuiwj
 * @version 1.0 2019-02-27
 */
public class HrStaffAttendanceTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(HrStaffAttendanceTransferTask.class);

	IHrStaffAttendanceManageService hrStaffAttendanceManageService;
	IHrStaffAttendanceManageDetailedService hrStaffAttendanceManageDetailedService;
	ISysTimeLeaveAmountService sysTimeLeaveAmountService;
	ISysTimeLeaveDetailService sysTimeLeaveDetailService;
	ISysTimeLeaveRuleService sysTimeLeaveRuleService;

	public IHrStaffAttendanceManageService getHrStaffAttendanceManageService() {
		if (hrStaffAttendanceManageService == null) {
			hrStaffAttendanceManageService = (IHrStaffAttendanceManageService) SpringBeanUtil
					.getBean("hrStaffAttendanceManageService");
		}
		return hrStaffAttendanceManageService;
	}

	public IHrStaffAttendanceManageDetailedService
			getHrStaffAttendanceManageDetailedService() {
		if (hrStaffAttendanceManageDetailedService == null) {
			hrStaffAttendanceManageDetailedService = (IHrStaffAttendanceManageDetailedService) SpringBeanUtil
					.getBean("hrStaffAttendanceManageDetailedService");
		}
		return hrStaffAttendanceManageDetailedService;
	}

	public ISysTimeLeaveAmountService getSysTimeLeaveAmountService() {
		if (sysTimeLeaveAmountService == null) {
			sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil
					.getBean("sysTimeLeaveAmountService");
		}
		return sysTimeLeaveAmountService;
	}

	public ISysTimeLeaveDetailService getSysTimeLeaveDetailService() {
		if (sysTimeLeaveDetailService == null) {
			sysTimeLeaveDetailService = (ISysTimeLeaveDetailService) SpringBeanUtil
					.getBean("sysTimeLeaveDetailService");
		}
		return sysTimeLeaveDetailService;
	}

	public ISysTimeLeaveRuleService getSysTimeLeaveRuleService() {
		if (sysTimeLeaveRuleService == null) {
			sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil
					.getBean("sysTimeLeaveRuleService");
		}
		return sysTimeLeaveRuleService;
	}

	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");
			String uuid = sysAdminTransferCheckContext.getUUID();
			List list = new ArrayList();
			list = sysAdminTransferTaskService.getBaseDao().findValue(null,
					"sysAdminTransferTask.fdUuid='" + uuid + "'", null);

			if (list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) list
						.get(0);
				if (sysAdminTransferTask.getFdStatus() == 1) {
					return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
				}
			}
			HQLInfo hqlInfo1 = new HQLInfo();
			hqlInfo1.setGettingCount(true);
			List managetList = getHrStaffAttendanceManageService()
					.findValue(hqlInfo1);
			HQLInfo hqlInfo2 = new HQLInfo();
			hqlInfo2.setGettingCount(true);
			List detailList = getHrStaffAttendanceManageDetailedService()
					.findValue(hqlInfo2);
			// 两张表都为空，则不执行
			if (managetList.isEmpty() && detailList.isEmpty()) {
				return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}

	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		try {
			// -------假期类型-------
			List<SysTimeLeaveRule> leaveRuleList = getSysTimeLeaveRuleService()
					.findList("fdIsAvailable=" + HibernateUtil.toBooleanValueString(true), "");
			Integer maxSerial = getMaxLeaveRuleSerial(leaveRuleList);

			// 年假
			String annnualLeaveName = ResourceUtil.getString(
					"hr-staff:hrStaff.robot.optionValue.overtime.annualLeave");
			if (StringUtil.isNotNull(annnualLeaveName)) {
				SysTimeLeaveRule annualLeave = getLeaveRule(leaveRuleList,
						annnualLeaveName);
				if (annualLeave == null) {
					maxSerial++;
					addLeaveRule(annnualLeaveName, maxSerial);
				} else {
					if (!Boolean.TRUE.equals(annualLeave.getFdIsAmount())) {
						annnualLeaveName = annnualLeaveName + "_hr";
						SysTimeLeaveRule annualLeaveNew = getLeaveRule(
								leaveRuleList, annnualLeaveName);
						if (annualLeaveNew != null) {
							maxSerial++;
							addLeaveRule(annnualLeaveName, maxSerial);
						}

					}
				}
			}
			// 调休假
			String takeWorkingName = ResourceUtil.getString(
					"hr-staff:hrStaff.robot.optionValue.overtime.takeWorking");
			if (StringUtil.isNotNull(takeWorkingName)) {
				SysTimeLeaveRule takeWorking = getLeaveRule(leaveRuleList,
						takeWorkingName);
				if (takeWorking == null) {
					maxSerial++;
					addLeaveRule(takeWorkingName, maxSerial);
				} else {
					if (!Boolean.TRUE.equals(takeWorking.getFdIsAmount())) {
						takeWorkingName = takeWorkingName + "_hr";
						SysTimeLeaveRule takeWorkingNew = getLeaveRule(
								leaveRuleList, takeWorkingName);
						if (takeWorkingNew != null) {
							maxSerial++;
							addLeaveRule(takeWorkingName, maxSerial);
						}
						
					}
				}
			}
			// 病假
			String sickLeaveName = ResourceUtil.getString(
					"hr-staff:hrStaff.robot.optionValue.overtime.sickLeave");
			if (StringUtil.isNotNull(sickLeaveName)) {
				SysTimeLeaveRule sickLeave = getLeaveRule(leaveRuleList,
						sickLeaveName);
				if (sickLeave == null) {
					maxSerial++;
					addLeaveRule(sickLeaveName, maxSerial);
				} else {
					if (!Boolean.TRUE.equals(sickLeave.getFdIsAmount())) {
						sickLeaveName = sickLeaveName + "_hr";
						SysTimeLeaveRule sickLeaveNew = getLeaveRule(
								leaveRuleList, sickLeaveName);
						if (sickLeaveNew != null) {
							maxSerial++;
							addLeaveRule(sickLeaveName, maxSerial);
						}
					}
				}
			}
			List<SysTimeLeaveRule> ruleList = new ArrayList<SysTimeLeaveRule>();
			SysTimeLeaveRule annualLeave = getSysTimeLeaveRuleService()
					.getLeaveRuleByName(annnualLeaveName);
			ruleList.add(annualLeave);
			SysTimeLeaveRule takeWorking = getSysTimeLeaveRuleService()
					.getLeaveRuleByName(takeWorkingName);
			ruleList.add(takeWorking);
			SysTimeLeaveRule sickLeave = getSysTimeLeaveRuleService()
					.getLeaveRuleByName(sickLeaveName);
			ruleList.add(sickLeave);
			// -------假期额度-------
			String sql=" select b.fd_id from hr_staff_attendance_manage a inner join sys_org_person b on a.fd_person_info_id =b.fd_id group by b.fd_id ";

			List<String> personList = getHrStaffAttendanceManageService().getBaseDao().getHibernateSession().createNativeQuery(sql).list();

		/*	HQLInfo hql1 = new HQLInfo();
			hql1.setSelectBlock("fdPersonInfo.fdOrgPerson.fdId");

			hql1.setWhereBlock("fdPersonInfo.fdOrgPerson.fdId is not null");
			List<String> personList = getHrStaffAttendanceManageService()
					.findList(hql1);*/
			List<List> groupList = this.splitList(personList, 1000);

			for (List valueList : groupList) {
				List<HrStaffAttendanceManage> managetList = getHrStaffAttendanceManageService()
						.findList(HQLUtil.buildLogicIN(
								"fdPersonInfo.fdId", valueList),
								"");
				List<SysTimeLeaveAmount> updateList = new ArrayList<SysTimeLeaveAmount>();
				List<SysTimeLeaveAmount> addList = new ArrayList<SysTimeLeaveAmount>();
				TransactionStatus status = TransactionUtils
						.beginNewTransaction();
				for (HrStaffAttendanceManage manage : managetList) {
					if (!ruleList.isEmpty() && manage != null) {
						updateOrAddLeaveAmomunt(manage, ruleList, addList,
								updateList);
					}
				}
				try {
					for (int j = 0; j < updateList.size(); j++) {
						getSysTimeLeaveAmountService()
								.update(updateList.get(j));
					}
					for (int j = 0; j < addList.size(); j++) {
						getSysTimeLeaveAmountService().add(addList.get(j));
					}
					TransactionUtils.getTransactionManager().commit(status);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("批量迁移假期额度数据失败:" + e.getMessage(), e);
					TransactionUtils.getTransactionManager().rollback(status);
				}
			}
			logger.info("用户假期额度数据迁移完成!");
			logger.info("用户请假明细数据迁移准备开始...");
			// -------假期明细-------
			String sql2="select b.fd_id from hr_staff_atte_manage_detailed a inner join sys_org_person b on a.fd_person_info_id =b.fd_id where a.fd_type=:fdType group by b.fd_id";
			List<String> orgList =getHrStaffAttendanceManageDetailedService().getBaseDao().getHibernateSession().createNativeQuery(sql2)
					.setParameter("fdType",HrStaffAttendanceManageDetailed.TYPE_LEAVE).list();
			/*HQLInfo hql2 = new HQLInfo();
			hql2.setSelectBlock("distinct fdPersonInfo.fdOrgPerson.fdId");
			hql2.setWhereBlock(
					"fdPersonInfo.fdOrgPerson.fdId is not null and (fdType is null or fdType=:type)");
			hql2.setParameter("type",
					HrStaffAttendanceManageDetailed.TYPE_LEAVE);
			List<String> orgList = getHrStaffAttendanceManageDetailedService()
					.findList(hql2);*/
			List<List> groupList2 = this.splitList(orgList, 1000);
			for (List valueList : groupList2) {
				//HR的Fd_id跟sys_org_person的Fd_id相同。所以这里直接用id查询
				HQLInfo hql = new HQLInfo();
				hql.setWhereBlock(
						"fdPersonInfo.fdId is not null and (fdType is null or fdType=:type)");
				hql.setWhereBlock(
						hql.getWhereBlock() + " and " + HQLUtil.buildLogicIN(
								"fdPersonInfo.fdId", valueList));
				hql.setParameter("type",
						HrStaffAttendanceManageDetailed.TYPE_LEAVE);
				List<HrStaffAttendanceManageDetailed> detailList = getHrStaffAttendanceManageDetailedService()
						.findList(hql);
				// 排班请假明细
				HQLInfo leaveDetailHql = new HQLInfo();
				StringBuffer leaveWhere = new StringBuffer();
				leaveWhere.append(
						HQLUtil.buildLogicIN("fdPerson.fdId", valueList));
				leaveWhere.append(" and (fdType is null or fdType=:fdType)");
				leaveDetailHql.setParameter("fdType", 1);
				leaveDetailHql.setWhereBlock(leaveWhere.toString());
				List<SysTimeLeaveDetail> leaveDetailList = getSysTimeLeaveDetailService()
						.findList(leaveDetailHql);
				TransactionStatus status = TransactionUtils
						.beginNewTransaction();
				try {
					for (HrStaffAttendanceManageDetailed detail : detailList) {
						if (!ruleList.isEmpty() && detail != null) {
							addLeaveDetail(detail, annnualLeaveName,
									takeWorkingName, sickLeaveName,
									leaveDetailList);
						}
					}
					TransactionUtils.getTransactionManager().commit(status);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("批量用户请假明细数据迁移失败:" + e.getMessage(), e);
					TransactionUtils.getTransactionManager().rollback(status);
				}

			}

			logger.info("用户请假明细数据迁移准备完成!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("人事档案迁移数据到排班出错：" + e.getMessage(), e);
		}
		return SysAdminTransferResult.OK;
	}

	private void addLeaveDetail(HrStaffAttendanceManageDetailed detail,
			String annnualLeaveName, String takeWorkingName,
			String sickLeaveName, List<SysTimeLeaveDetail> recordList)
			throws Exception {
		SysOrgPerson person = detail.getFdPersonInfo().getFdOrgPerson();
		SysTimeLeaveRule annualLeave = getSysTimeLeaveRuleService()
				.getLeaveRuleByName(annnualLeaveName);
		SysTimeLeaveRule takeWorking = getSysTimeLeaveRuleService()
				.getLeaveRuleByName(takeWorkingName);
		SysTimeLeaveRule sickLeave = getSysTimeLeaveRuleService()
				.getLeaveRuleByName(sickLeaveName);
		String leaveType = detail.getFdLeaveType();

		String leaveName = detail.getFdLeaveType();
		if (StringUtil.isNull(leaveName)) {
			logger.warn("请假明细数据迁移时,用户(" + person.getFdName()
					+ ")假期类型为空,忽略处理!fdId:" + detail.getFdId());
			return;
		}

		if (HrStaffAttendanceManageDetailed.LEAVE_TYPE_TAKEWORKING
				.equals(leaveName) || takeWorkingName.equals(leaveName)
				|| HrStaffAttendanceManageDetailed.LEAVE_TYPE_ANNUALLEAVE
						.equals(leaveName)
				|| annnualLeaveName.equals(leaveName)
				|| HrStaffAttendanceManageDetailed.LEAVE_TYPE_SICKLEAVE
						.equals(leaveName)
				|| sickLeaveName.equals(leaveName)) {
			if (detail.getFdBeginDate() == null
					|| detail.getFdEndDate() == null) {
				logger.warn("请假明细数据迁移时,用户(" + person.getFdName()
						+ ")请假时间为空,忽略处理!fdId:" + detail.getFdId());
				return;
			}
			if (StringUtil.isNull(detail.getFdLeaveType())) {
				logger.warn("请假明细数据迁移时,用户(" + person.getFdName()
						+ ")假期类型为空,忽略处理!" + detail.getFdId());
				return;
			}
			SysTimeLeaveDetail leaveDetail = new SysTimeLeaveDetail();
			leaveDetail.setFdPerson(person);
			leaveDetail.setFdStartTime(detail.getFdBeginDate());
			leaveDetail.setFdEndTime(detail.getFdEndDate());
			leaveDetail.setFdLeaveTime(detail.getFdLeaveDays() != null
					? detail.getFdLeaveDays().floatValue() : 0f);
			SysTimeLeaveRule rule = null;
			if (HrStaffAttendanceManageDetailed.LEAVE_TYPE_TAKEWORKING
					.equals(leaveType)
					|| takeWorkingName.indexOf(leaveName) > -1) {
				rule = takeWorking;
			}
			if (HrStaffAttendanceManageDetailed.LEAVE_TYPE_ANNUALLEAVE
					.equals(leaveType)
					|| annnualLeaveName.indexOf(leaveName) > -1) {
				rule = annualLeave;
			}
			if (HrStaffAttendanceManageDetailed.LEAVE_TYPE_SICKLEAVE
					.equals(leaveType)
					|| sickLeaveName.indexOf(leaveName) > -1) {
				rule = sickLeave;
			}
			if (rule == null) {
				logger.warn("请假明细数据迁移时,用户(" + person.getFdName()
						+ ")假期规则为空,忽略处理!leaveType:" + leaveType
						+ ";fdId:" + detail.getFdId());
				return;
			}
			leaveDetail.setFdLeaveName(rule.getFdName());
			leaveDetail.setFdLeaveType(rule.getFdSerialNo() + "");
			leaveDetail.setFdStatType(rule.getFdStatType());
			leaveDetail.setFdOprType(1);
			leaveDetail.setFdOprStatus(
					detail.getFdException().equals(Boolean.TRUE) ? 2 : 1);
			String processLink = detail.getFdRelatedProcess();
			if (StringUtil.isNotNull(processLink)) {
				String prefix = "/km/review/km_review_main/kmReviewMain.do?method=view&fdId=";
				if (processLink.indexOf(prefix) > -1) {
					leaveDetail
							.setFdReviewId(
									processLink.substring(prefix.length()));
				}
			}
			String processName = detail.getFdSubject();
			if (StringUtil.isNotNull(processName)) {
				leaveDetail.setFdReviewName(processName);
			} else {
				leaveDetail.setFdReviewName(ResourceUtil.getString(
						"hrStaffAttendanceManageDetailed.relatedProcess",
						"hr-staff", null, new String[] { person.getFdName(),
								detail.getLeaveType() }));
			}
			leaveDetail.setDocCreateTime(detail.getFdCreateTime());
			leaveDetail.setDocCreator(detail.getFdCreator());
			if (existRecord(leaveDetail, recordList)) {
				return;
			}
			getSysTimeLeaveDetailService().add(leaveDetail);
		}
	}

	private boolean existRecord(SysTimeLeaveDetail leaveDetail,
			List<SysTimeLeaveDetail> recordList) {
		if (recordList == null || recordList.isEmpty()) {
			return false;
		}
		for (SysTimeLeaveDetail detail : recordList) {
			if (leaveDetail.getFdPerson().getFdId()
					.equals(detail.getFdPerson().getFdId()) &&
					leaveDetail.getFdLeaveType().equals(detail.getFdLeaveType())
					&& leaveDetail.getFdStartTime()
							.equals(detail.getFdStartTime())) {
				return true;
			}
		}
		return false;
	}

	private SysTimeLeaveAmount getLeaveAmount(String personId, Integer year) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysTimeLeaveAmount.fdPerson.fdId=:personId and sysTimeLeaveAmount.fdYear=:year");
			hqlInfo.setParameter("personId", personId);
			hqlInfo.setParameter("year", year);
			List<SysTimeLeaveAmount> list = getSysTimeLeaveAmountService()
					.findList(hqlInfo);
			if (!list.isEmpty()) {
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private SysTimeLeaveAmountItem getLeaveAmountItem(
			List<SysTimeLeaveAmountItem> itemList, String leaveType) {
		for (SysTimeLeaveAmountItem item : itemList) {
			if (leaveType.equals(item.getFdLeaveType())) {
				return item;
			}
		}
		return null;
	}

	public void updateLeaveAmountItem(SysTimeLeaveAmountItem item,
			SysTimeLeaveAmount amount, SysTimeLeaveRule leaveRule,
			HrStaffAttendanceManage manage,
			List<SysTimeLeaveRule> leaveRuleList)
			throws Exception {
		Float days = 0f;
		String fdSourceType = item.getFdSourceType();
		if ("hr".equals(fdSourceType)) {
			// 已迁移过数据,不重复执行
			logger.debug("假期类型(" + leaveRule.getFdName()
					+ ")已迁移过数据,不重复执行.若需要重新执行,需清理当前已迁移的数据信息!");
			return;
		}
		Date validDate = manage.getFdExpirationDate();
		if (leaveRule.getFdName().equals(leaveRuleList.get(0).getFdName())) {
			days = manage.getFdDaysOfAnnualLeave().floatValue();
		} else if (leaveRule.getFdName()
				.equals(leaveRuleList.get(1).getFdName())) {
			days = manage.getFdDaysOfTakeWorking().floatValue();
		} else if (leaveRule.getFdName()
				.equals(leaveRuleList.get(2).getFdName())) {
			days = manage.getFdDaysOfSickLeave().floatValue();
		}
		Float totalDay = item.getFdTotalDay() != null ? item.getFdTotalDay()
				: 0f;
		Float restDay = item.getFdRestDay() != null ? item.getFdRestDay() : 0f;
		item.setFdTotalDay(totalDay + days);
		item.setFdRestDay(restDay + days);
		item.setFdValidDate(validDate);
		item.setFdIsAvail(
				validDate != null ? IsAfterToday(validDate) : true);
		item.setFdSourceType("hr");
	}

	private Boolean IsAfterToday(Date date) {
		if (date == null) {
			return true;
		}
		Date today = SysTimeUtil.getDate(new Date(), 0);
		return SysTimeUtil.getDate(date, 0).compareTo(today) >= 0;
	}

	public SysTimeLeaveAmountItem getLeaveAmountItem(Integer year,
			String personId, String leaveType) {
		SysTimeLeaveAmount amount = getLeaveAmount(personId, year);
		if (amount != null) {
			return getLeaveAmountItem(amount.getFdAmountItems(),
					leaveType);
		}
		return null;
	}

	public SysTimeLeaveAmountItem createLeaveAmountItem(
			SysTimeLeaveAmount amount, SysTimeLeaveRule leaveRule,
			HrStaffAttendanceManage manage)
			throws Exception {
		Integer year = amount.getFdYear();
		String personId = amount.getFdPerson().getFdId();
		SysTimeLeaveAmountItem item = new SysTimeLeaveAmountItem();
		item.setFdId(IDGenerator.generateID());
		item.setFdAmount(amount);
		item.setFdLeaveName(leaveRule.getFdName());
		item.setFdLeaveType(leaveRule.getFdSerialNo());
		item.setFdSourceType("hr");
		if (Boolean.TRUE.equals(leaveRule.getFdIsAmount())
				&& Boolean.TRUE.equals(leaveRule.getFdIsAvailable())) {
			Float days = 0f;
			Date validDate = manage.getFdExpirationDate();
			String leaveName = leaveRule.getFdName().replace("_hr", "");
			if (leaveName.equals(ResourceUtil.getString(
					"hr-staff:hrStaff.robot.optionValue.overtime.annualLeave"))) {
				days = manage.getFdDaysOfAnnualLeave().floatValue();
			} else if (leaveName.equals(ResourceUtil.getString(
					"hr-staff:hrStaff.robot.optionValue.overtime.takeWorking"))) {
				days = manage.getFdDaysOfTakeWorking().floatValue();
			} else if (leaveName.equals(ResourceUtil.getString(
					"hr-staff:hrStaff.robot.optionValue.overtime.sickLeave"))) {
				days = manage.getFdDaysOfSickLeave().floatValue();
			}
			item.setFdIsAuto(false);
			item.setFdIsAccumulate(false);
			item.setFdTotalDay(days);
			item.setFdRestDay(days);
			item.setFdUsedDay(0f);
			item.setFdValidDate(validDate);
			item.setFdIsAvail(
					validDate != null ? IsAfterToday(validDate) : true);
			// 上一年的数据
			if (StringUtil.isNotNull(personId)) {
				SysTimeLeaveAmountItem lastYearItem = getLeaveAmountItem(
						year - 1, personId, leaveRule.getFdSerialNo());
				if (lastYearItem != null) {// 有上一年的数据
					if (Boolean.TRUE.equals(lastYearItem.getFdIsAccumulate())) {// 是否累加
						Float restDay = lastYearItem.getFdRestDay() == null
								? 0 : lastYearItem.getFdRestDay();
						Float lastRestDay = lastYearItem
								.getFdLastRestDay() == null ? 0
										: lastYearItem.getFdLastRestDay();
						item.setFdLastTotalDay(restDay + lastRestDay);
						item.setFdLastRestDay(restDay + lastRestDay);
						item.setFdLastUsedDay(lastYearItem.getFdLastUsedDay());
						item.setFdIsLastAvail(true);
					} else if (lastYearItem.getFdValidDate() != null) {
						Float restDay = lastYearItem.getFdRestDay() == null
								? 0 : lastYearItem.getFdRestDay();
						item.setFdLastTotalDay(restDay);
						item.setFdLastRestDay(restDay);
						item.setFdLastUsedDay(lastYearItem.getFdLastUsedDay());
						item.setFdIsLastAvail(
								IsAfterToday(lastYearItem.getFdValidDate()));
						item.setFdLastValidDate(lastYearItem.getFdValidDate());
					}
				}
			}
			return item;
		} else {
			return null;
		}
	}

	private void updateOrAddLeaveAmomunt(HrStaffAttendanceManage manage,
			List<SysTimeLeaveRule> leaveRuleList,
			List<SysTimeLeaveAmount> addList,
			List<SysTimeLeaveAmount> updateList) throws Exception {
		SysOrgPerson person = manage.getFdPersonInfo().getFdOrgPerson();
		Integer year = manage.getFdYear();
		if (person == null && year == null) {
			return;
		}
		SysTimeLeaveAmount amount = getLeaveAmount(person.getFdId(), year);
		if (amount != null) {// 已存在额度数据
			List<SysTimeLeaveAmountItem> itemList = amount
					.getFdAmountItems();
			List<SysTimeLeaveAmountItem> tmpList = new ArrayList<SysTimeLeaveAmountItem>();
			tmpList.addAll(itemList);
			for (SysTimeLeaveRule leaveRule : leaveRuleList) {
				SysTimeLeaveAmountItem item = getLeaveAmountItem(
						tmpList, leaveRule.getFdSerialNo());
				if (item != null) {
					// 已存在该假期的额度信息
					updateLeaveAmountItem(item, item.getFdAmount(),
							leaveRule, manage, leaveRuleList);
				} else {
					// 没有该假期的额度信息
					SysTimeLeaveAmountItem newItem = createLeaveAmountItem(
							amount, leaveRule, manage);
					tmpList.add(newItem);
				}
			}
			itemList.clear();
			itemList.addAll(tmpList);
			amount.setDocAlteror(UserUtil.getUser());
			amount.setDocAlterTime(new Date());
			// getSysTimeLeaveAmountService().update(amount);
			updateList.add(amount);
		} else {// 不存在额度数据
			SysTimeLeaveAmount newAmount = new SysTimeLeaveAmount();
			newAmount.setFdYear(year);
			newAmount.setFdPerson(person);
			newAmount.setDocCreator(UserUtil.getUser());
			newAmount.setDocCreateTime(new Date());
			List<SysTimeLeaveAmountItem> itemList = new ArrayList<SysTimeLeaveAmountItem>();
			newAmount.setFdAmountItems(itemList);
			for (SysTimeLeaveRule leaveRule : leaveRuleList) {
				SysTimeLeaveAmountItem newItem = createLeaveAmountItem(
						newAmount, leaveRule, manage);
				itemList.add(newItem);
			}
			// getSysTimeLeaveAmountService().add(newAmount);
			addList.add(newAmount);
		}
	}

	private void addLeaveRule(String leaveName, Integer serial)
			throws Exception {
		SysTimeLeaveRule leaveRule = new SysTimeLeaveRule();
		leaveRule.setFdName(leaveName);
		leaveRule.setFdSerialNo(serial + "");
		leaveRule.setFdStatType(1);// 按天请假
		leaveRule.setFdIsAvailable(true);
		leaveRule.setFdStatDayType(2);// 自然日计算请假
		leaveRule.setFdDayConvertTime(8);// 工时转换
		leaveRule.setDocCreateTime(new Date());
		leaveRule.setDocCreator(UserUtil.getUser());
		leaveRule.setFdIsAmount(true);
		leaveRule.setFdAmountType(1);// 手动发放
		getSysTimeLeaveRuleService().add(leaveRule);
	}

	private SysTimeLeaveRule getLeaveRule(List<SysTimeLeaveRule> leaveRuleList,
			String leaveName) {
		for (SysTimeLeaveRule rule : leaveRuleList) {
			if (leaveName.equals(rule.getFdName())) {
				return rule;
			}
		}
		return null;
	}

	private Integer
			getMaxLeaveRuleSerial(List<SysTimeLeaveRule> leaveRuleList) {
		Integer maxSerial = 1;
		for (SysTimeLeaveRule rule : leaveRuleList) {
			try {
				Integer serialNo = Integer.parseInt(rule.getFdSerialNo());
				if (maxSerial < serialNo) {
					maxSerial = serialNo;
				}
			} catch (Exception e) {
			}
		}
		return maxSerial;
	}

	private List<List> splitList(List list, int pageSize) {
		int listSize = list.size(); // list的大小
		int page = (listSize + (pageSize - 1)) / pageSize; // 页数
		List<List> listArray = new ArrayList<List>(); // 创建list数组,用来保存分割后的list
		for (int i = 0; i < page; i++) {
			List subList = new ArrayList();
			for (int j = 0; j < listSize; j++) {
				int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
				if (pageIndex == (i + 1)) {
					subList.add(list.get(j));
				}

				if ((j + 1) == ((j + 1) * pageSize)) {
					break;
				}
			}
			listArray.add(subList); // 将分割后的list放入对应的数组的位中
		}
		return listArray;
	}
}
