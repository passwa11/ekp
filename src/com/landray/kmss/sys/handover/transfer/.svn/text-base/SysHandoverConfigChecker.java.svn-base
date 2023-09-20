package com.landray.kmss.sys.handover.transfer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigMainService;
import com.landray.kmss.util.SpringBeanUtil;

public class SysHandoverConfigChecker implements ISysAdminTransferChecker {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Override
	public SysAdminTransferCheckResult check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysHandoverConfigMainService sysHandoverConfigMainService = (ISysHandoverConfigMainService) SpringBeanUtil.getBean("sysHandoverConfigMainService");

			int count = 0;
			List<?> list = sysHandoverConfigMainService.findList("sysHandoverConfigMain.fdState is null", null);
			if (list != null) {
				count = list.size();
			}
			if (count > 0) {
				return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
			}
		} catch (Exception e) {
			logger.error("检查是否执行过旧数据迁移异常", e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
	}

}
