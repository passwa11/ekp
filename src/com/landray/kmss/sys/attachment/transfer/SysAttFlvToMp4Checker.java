package com.landray.kmss.sys.attachment.transfer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysAttFlvToMp4Checker implements ISysAdminTransferChecker {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Override
	@SuppressWarnings("unchecked")
	public SysAdminTransferCheckResult check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");
			String uuid = sysAdminTransferCheckContext.getUUID();
			List<SysAdminTransferTask> list = new ArrayList<SysAdminTransferTask>();
			HQLInfo info = new HQLInfo();
			info.setWhereBlock("sysAdminTransferTask.fdUuid=:uuid");
			info.setParameter("uuid", uuid);
			list = sysAdminTransferTaskService.getBaseDao().findValue(info);
			if (!ArrayUtil.isEmpty(list)) {
				SysAdminTransferTask sysAdminTransferTask = list.get(0);
				if (sysAdminTransferTask.getFdStatus() == 1) {
					return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}
}
