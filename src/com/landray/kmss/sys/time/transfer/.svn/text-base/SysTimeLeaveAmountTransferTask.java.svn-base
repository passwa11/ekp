package com.landray.kmss.sys.time.transfer;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.util.SpringBeanUtil;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户假期额度表增加假期类型编号信息
 *
 */
public class SysTimeLeaveAmountTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeLeaveAmountTransferTask.class);

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
			ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService = (ISysTimeLeaveAmountItemService) SpringBeanUtil
					.getBean("sysTimeLeaveAmountItemService");
			ISysTimeLeaveRuleService sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil
					.getBean("sysTimeLeaveRuleService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysTimeLeaveAmountItem.fdLeaveType is null");
			hqlInfo.setGettingCount(true);
			List<Object> resultList = sysTimeLeaveAmountItemService
					.findValue(hqlInfo);
			Object result = resultList.get(0);
			int count = Integer
					.valueOf(result != null ? result.toString() : "0");
			if (count == 0) {
				return SysAdminTransferResult.OK;
			}
			logger.debug("假期额度数据量为:" + count + "条");
			List<SysTimeLeaveRule> rules = sysTimeLeaveRuleService
					.getLeaveRuleList("");
			if (rules == null || rules.isEmpty()) {
				return SysAdminTransferResult.OK;
			}

			for (SysTimeLeaveRule rule : rules) {
				String ruleName = rule.getFdName();
				String sql = "update sys_time_leave_aitem set fd_leave_type=:fdleaveType where fd_leave_name=:fdLeaveName";
				NativeQuery query = sysTimeLeaveAmountItemService.getBaseDao().getHibernateSession().createNativeQuery(sql);
				query.setParameter("fdleaveType", rule.getFdSerialNo());
				query.setParameter("fdLeaveName", ruleName);
				query.addSynchronizedQuerySpace("sys_time_leave_aitem");
				query.executeUpdate();
			}
			logger.debug("用户假期额度数据迁移结束...");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SysAdminTransferResult.OK;
	}

}
