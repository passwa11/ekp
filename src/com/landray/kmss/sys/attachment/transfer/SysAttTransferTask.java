package com.landray.kmss.sys.attachment.transfer;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysAttTransferTask extends SysAttTransferChecker implements ISysAdminTransferTask {

	@SuppressWarnings("rawtypes")
	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		String uuid = sysAdminTransferContext.getUUID();
		ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
				.getBean("sysAdminTransferTaskService");
		try {
			List list = new ArrayList();
			HQLInfo info = new HQLInfo();
			info.setWhereBlock("sysAdminTransferTask.fdUuid=:uuid");
			info.setParameter("uuid", uuid);
			list = sysAdminTransferTaskService.getBaseDao().findValue(info);
			SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) list.get(0);
			if (!sysAdminTransferTask.getFdStatus().equals(ISysAdminTransferConstant.TASK_STATUS_RUNED)) {
				SysFileStoreUtil.resetConvertInfo();
			}
			return SysAdminTransferResult.OK;
		} catch (Exception e) {
			logger.info("附件迁移任务出错", e);
			return new SysAdminTransferResult(ISysAdminTransferConstant.TASK_STATUS_NOT_RUNED, e.getLocalizedMessage(),
					e);

		}
	}
}
