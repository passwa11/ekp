package com.landray.kmss.sys.portal.transfer;

import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;

public class SysPortalTopicFdNameTransferChecker implements ISysAdminTransferChecker {
	@Override
    public SysAdminTransferCheckResult check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}
}
