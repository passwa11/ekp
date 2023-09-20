package com.landray.kmss.km.archives.transfer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.km.archives.service.IKmArchivesBorrowService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 检测迁移任务是否需要执行
 * 
 * @author 黄泽凯
 * @version 1.0 2018-7-11
 */
public class KmArchivesChecker implements ISysAdminTransferChecker {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Override
    @SuppressWarnings("unchecked")
	public SysAdminTransferCheckResult check(
			SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
				.getBean("sysAdminTransferTaskService");

		IKmArchivesMainService kmArchivesMainService = (IKmArchivesMainService) SpringBeanUtil
				.getBean("kmArchivesMainService");
		IKmArchivesBorrowService kmArchivesBorrowService = (IKmArchivesBorrowService) SpringBeanUtil
				.getBean("kmArchivesBorrowService");

		try {
			List<Long> mainCount = kmArchivesMainService.getBaseDao().findValue(
					"count(*)", "kmArchivesMain.fdLastModifiedTime is null",
					null);
			List<Long> borrowCount = kmArchivesBorrowService.getBaseDao()
					.findValue("count(*)",
							"kmArchivesBorrow.fdLastModifiedTime is null",
							null);
			String uuid = sysAdminTransferCheckContext.getUUID();
			List list = sysAdminTransferTaskService.getBaseDao().findValue(null,
					"sysAdminTransferTask.fdUuid='" + uuid + "'", null);
			if (list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) list
						.get(0);
				if (sysAdminTransferTask.getFdStatus() == 1
						&& mainCount.get(0) == 0
						&& borrowCount.get(0) == 0) {
					return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
				}
			}
		} catch (Exception e) {
			logger.error("检查档案管理是否为空异常", e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}
}
