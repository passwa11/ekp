package com.landray.kmss.sys.time.transfer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmount;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 修复用户假期额度表中已用天数大于总天数问题
 *
 */
public class SysTimeLeaveAmountItemTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeLeaveAmountItemTransferTask.class);
	
	private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;
	private ISysTimeLeaveAmountItemService getSysTimeLeaveAmountItemService() {
		if(sysTimeLeaveAmountItemService == null) {
			sysTimeLeaveAmountItemService = (ISysTimeLeaveAmountItemService) SpringBeanUtil
					.getBean("sysTimeLeaveAmountItemService");
		}
		return sysTimeLeaveAmountItemService;
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
			logger.debug("用户假期额度数据迁移开始...");
			Calendar cal = Calendar.getInstance();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysTimeLeaveAmountItem.fdLastUsedDay > sysTimeLeaveAmountItem.fdLastTotalDay and sysTimeLeaveAmountItem.fdAmount.fdYear=:fdYear");
			hqlInfo.setParameter("fdYear", cal.get(Calendar.YEAR));
			hqlInfo.setGettingCount(true);
			List<Object> resultList = getSysTimeLeaveAmountItemService()
					.findValue(hqlInfo);
			Object result = resultList.get(0);
			int count = Integer
					.valueOf(result != null ? result.toString() : "0");
			if (count == 0) {
				return SysAdminTransferResult.OK;
			}
			logger.debug("需修复假期额度数据量为:" + count + "条");
			HQLInfo curhqlInfo = new HQLInfo();
			curhqlInfo.setWhereBlock(
					"sysTimeLeaveAmountItem.fdLastUsedDay > sysTimeLeaveAmountItem.fdLastTotalDay and sysTimeLeaveAmountItem.fdAmount.fdYear=:fdYear");
			curhqlInfo.setParameter("fdYear", cal.get(Calendar.YEAR));
			List<SysTimeLeaveAmountItem> curItems = getSysTimeLeaveAmountItemService().findList(curhqlInfo);
			if(curItems!=null && !curItems.isEmpty()) {
				for(SysTimeLeaveAmountItem curItem : curItems) {
					SysTimeLeaveAmount amount = curItem.getFdAmount();
					if(amount!=null) {
						SysTimeLeaveAmountItem lastItem = getAmountItem(amount.getFdPerson().getFdId(),amount.getFdYear()-1,curItem.getFdLeaveType());
						if(lastItem!=null) {
							//前一年额度的已用额度
							Float usedDay = lastItem.getFdUsedDay(); 
							usedDay = usedDay != null && usedDay > 0 ? usedDay:0f;
							//前一年额度的剩余天数
							Float restDay = lastItem.getFdRestDay();
							restDay = restDay != null && restDay > 0 ? restDay:0f;
							//上周期已用额度
							Float lastUsedDay = curItem.getFdLastUsedDay();
							lastUsedDay = lastUsedDay != null && lastUsedDay > 0 ? lastUsedDay:0f;
							//上周期已用额度大于前一年额度的剩余天数，并且上周期已用额度大于前一年额度的已用额度时，
							//将上周期已用额度改为上周期已用额度-前一年额度的已用额度
							lastUsedDay = lastUsedDay > restDay && lastUsedDay >= usedDay ? lastUsedDay - usedDay : lastUsedDay;
							curItem.setFdLastTotalDay(restDay);
							curItem.setFdLastUsedDay(lastUsedDay);
							curItem.setFdLastRestDay(restDay - lastUsedDay);
							getSysTimeLeaveAmountItemService().update(curItem);
						}
					}
					
				}
			}
			logger.debug("用户假期额度数据迁移结束...");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SysAdminTransferResult.OK;
	}
	
	private SysTimeLeaveAmountItem getAmountItem(String personId, Integer year,
			String leaveType) throws Exception {
		if (year != null && StringUtil.isNotNull(leaveType)
				&& StringUtil.isNotNull(personId)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysTimeLeaveAmountItem.fdAmount.fdPerson.fdId = :personId "
							+ "and sysTimeLeaveAmountItem.fdAmount.fdYear = :year "
							+ "and sysTimeLeaveAmountItem.fdLeaveType = :fdLeaveType");
			hqlInfo.setParameter("personId", personId);
			hqlInfo.setParameter("year", year);
			hqlInfo.setParameter("fdLeaveType", leaveType);
			SysTimeLeaveAmountItem sysTimeLeaveAmountItem = (SysTimeLeaveAmountItem) getSysTimeLeaveAmountItemService().findFirstOne(hqlInfo);
			if (sysTimeLeaveAmountItem != null) {
				return sysTimeLeaveAmountItem;
			}
		}
		return null;
	}

}
