package com.landray.kmss.km.forum.transfer;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.util.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.praise.service.ISysPraiseMainService;
import com.landray.kmss.util.SpringBeanUtil;

public class KmForumPraiseTransferChecker implements ISysAdminTransferChecker {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Override
	public SysAdminTransferCheckResult check(
			SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		
		ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
				.getBean("sysAdminTransferTaskService");
		ISysPraiseMainService sysPraiseMainService = (ISysPraiseMainService) SpringBeanUtil
				.getBean("sysPraiseMainService");
		try {
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
			
			String selectSql = "select count(*) from km_forum_topic";
			List supportList = sysPraiseMainService.getBaseDao().getHibernateSession().createNativeQuery(selectSql).list();
			if (Long.parseLong(supportList.get(0).toString()) == 0L) {
				return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
			}
			//判断km_forum_support表是否存在，如果存在——待处理，不存在已处理
			if(!DbUtils.isExitTable("km_forum_support")){
				return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
			}
		} catch (Exception e) {
			logger.error("检查是否执行过旧数据迁移为空异常", e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}

}
