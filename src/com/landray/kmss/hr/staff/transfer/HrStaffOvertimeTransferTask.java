package com.landray.kmss.hr.staff.transfer;

import com.landray.kmss.common.dao.HQLInfo;
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
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * 机器人节点加班明细数据迁移到排班管理
 * 
 * @author linxiuxian
 * 人事档案的加班功能已不存在
 * 作废该兼容性监测任务。
 * 扩展点中的配置已删除
 * 2021-12-29 王京
 *
 */
public class HrStaffOvertimeTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(HrStaffOvertimeTransferTask.class);

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
			hqlInfo2.setWhereBlock(
					"fdPersonInfo.fdOrgPerson.fdId is not null and (fdType is null or fdType=:type)");
			hqlInfo2.setParameter("type",
					HrStaffAttendanceManageDetailed.TYPE_OVERTIME);
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
				SysTimeLeaveRule rule = getLeaveRule(leaveRuleList,
						annnualLeaveName);
				annnualLeaveName = rule != null ? rule.getFdName() : null;
			}
			// 调休假
			String takeWorkingName = ResourceUtil.getString(
					"hr-staff:hrStaff.robot.optionValue.overtime.takeWorking");
			if (StringUtil.isNotNull(takeWorkingName)) {
				SysTimeLeaveRule rule = getLeaveRule(leaveRuleList,
						takeWorkingName);
				takeWorkingName = rule != null ? rule.getFdName() : null;
			}
			// 病假
			String sickLeaveName = ResourceUtil.getString(
					"hr-staff:hrStaff.robot.optionValue.overtime.sickLeave");
			if (StringUtil.isNotNull(sickLeaveName)) {
				SysTimeLeaveRule rule = getLeaveRule(leaveRuleList,
						sickLeaveName);
				sickLeaveName = rule != null ? rule.getFdName() : null;
			}

			// -------加班明细-------
			logger.info("用户加班明细数据迁移准备开始...");
			HQLInfo hql2 = new HQLInfo();
			hql2.setSelectBlock("fdPersonInfo.fdOrgPerson.fdId");
			hql2.setWhereBlock(
					"fdPersonInfo.fdOrgPerson.fdId is not null and (fdType is null or fdType=:type)");
			hql2.setParameter("type",
					HrStaffAttendanceManageDetailed.TYPE_OVERTIME);
			List<String> orgList = getHrStaffAttendanceManageDetailedService()
					.findList(hql2);
			List<List> groupList2 = this.splitList(orgList, 1000);
			for (List valueList : groupList2) {
				HQLInfo hql = new HQLInfo();
				hql.setWhereBlock(
						"fdPersonInfo.fdOrgPerson.fdId is not null and (fdType is null or fdType=:type)");
				hql.setWhereBlock(
						hql.getWhereBlock() + " and " + HQLUtil.buildLogicIN(
								"fdPersonInfo.fdOrgPerson.fdId", valueList));
				hql.setParameter("type",
						HrStaffAttendanceManageDetailed.TYPE_OVERTIME);
				List<HrStaffAttendanceManageDetailed> detailList = getHrStaffAttendanceManageDetailedService()
						.findList(hql);
				// 排班请假明细
				HQLInfo overHql = new HQLInfo();
				StringBuffer overWhere = new StringBuffer();
				overWhere.append(
						HQLUtil.buildLogicIN("fdPerson.fdId", valueList));
				overWhere.append(" and fdType=:fdType");
				overHql.setParameter("fdType", 2);
				overHql.setWhereBlock(overWhere.toString());
				List<SysTimeLeaveDetail> leaveDetailList = getSysTimeLeaveDetailService().findList(overHql);
				TransactionStatus status = TransactionUtils.beginNewTransaction();
				Boolean isException =Boolean.FALSE;
				try {
					for (HrStaffAttendanceManageDetailed detail : detailList) {
						if (detail != null) {
							addOvertimeDetail(detail, annnualLeaveName,
									takeWorkingName, sickLeaveName,
									leaveDetailList);
						}
					}
					TransactionUtils.getTransactionManager().commit(status);
				} catch (Exception e) {
					isException =Boolean.TRUE;
					e.printStackTrace();
					logger.error("批量用户加班明细数据迁移失败:" + e.getMessage(), e);
				}finally {
					if(Boolean.TRUE.equals(isException)) {
						TransactionUtils.getTransactionManager().rollback(status);
					}
				}

			}


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("人事档案加班明细迁移数据到排班出错：" + e.getMessage(), e);
		}
		return SysAdminTransferResult.OK;
	}

	private void addOvertimeDetail(HrStaffAttendanceManageDetailed detail,
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
		if (StringUtil.isNull(leaveType)) {
			logger.warn("请假明细数据迁移时,用户(" + person.getFdName()
					+ ")假期类型为空,忽略处理!fdId:" + detail.getFdId());
			return;
		}
		if (HrStaffAttendanceManageDetailed.LEAVE_TYPE_TAKEWORKING
				.equals(leaveType)
				|| HrStaffAttendanceManageDetailed.LEAVE_TYPE_ANNUALLEAVE
						.equals(leaveType)
				|| HrStaffAttendanceManageDetailed.LEAVE_TYPE_SICKLEAVE
						.equals(leaveType)) {
			if (detail.getFdBeginDate() == null
					|| detail.getFdEndDate() == null) {
				logger.warn("加班明细数据迁移时,用户(" + person.getFdName()
						+ ")加班时间为空,忽略处理!fdId:" + detail.getFdId());
				return;
			}
			if (StringUtil.isNull(detail.getFdLeaveType())) {
				logger.warn("加班明细数据迁移时,用户(" + person.getFdName()
						+ ")假期类型为空,忽略处理!" + detail.getFdId());
				return;
			}
			SysTimeLeaveDetail leaveDetail = new SysTimeLeaveDetail();

			leaveDetail.setFdPerson(person);
			leaveDetail.setFdType(2);
			leaveDetail.setFdStartTime(detail.getFdBeginDate());
			leaveDetail.setFdEndTime(detail.getFdEndDate());
			leaveDetail.setFdLeaveTime(detail.getFdLeaveDays() != null
					? detail.getFdLeaveDays().floatValue() : 0f);
			SysTimeLeaveRule rule = null;
			if (HrStaffAttendanceManageDetailed.LEAVE_TYPE_TAKEWORKING
					.equals(leaveType)) {
				rule = takeWorking;
			}
			if (HrStaffAttendanceManageDetailed.LEAVE_TYPE_ANNUALLEAVE
					.equals(leaveType)) {
				rule = annualLeave;
			}
			if (HrStaffAttendanceManageDetailed.LEAVE_TYPE_SICKLEAVE
					.equals(leaveType)) {
				rule = sickLeave;
			}
			if (rule == null) {
				logger.warn("加班明细数据迁移时,用户(" + person.getFdName()
						+ ")假期规则为空,忽略处理!leaveType:" + leaveType
						+ ";fdId:" + detail.getFdId());
				return;
			}
			leaveDetail.setFdLeaveName(rule.getFdName());
			leaveDetail.setFdLeaveType(rule.getFdSerialNo() + "");
			leaveDetail.setFdStatType(rule.getFdStatType());
			leaveDetail.setFdOprType(1);
			leaveDetail.setFdOprStatus(
					Boolean.TRUE.equals(detail.getFdException()) ? 2 : 1);
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

	private SysTimeLeaveRule getLeaveRule(List<SysTimeLeaveRule> leaveRuleList,
			String leaveName) {
		leaveName = leaveName.replace("_hr", "");
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
