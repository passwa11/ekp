package com.landray.kmss.sys.handover.transfer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.handover.constant.SysHandoverConstant;
import com.landray.kmss.sys.handover.model.SysHandoverConfigMain;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigMainService;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 工作交接模块类数据迁移
 * 
 * @author 潘永辉
 * 
 */
public class SysHandoverConfigTask implements ISysAdminTransferTask {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		String uuid = sysAdminTransferContext.getUUID();
		try {
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil.getBean("sysAdminTransferTaskService");
			List<SysAdminTransferTask> list = sysAdminTransferTaskService.getBaseDao().findValue(null, "sysAdminTransferTask.fdUuid='" + uuid + "'", null);
			if (list != null && list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = list.get(0);
				if (sysAdminTransferTask.getFdStatus() != 1) {
					ISysHandoverConfigMainService sysHandoverConfigMainService = (ISysHandoverConfigMainService) SpringBeanUtil.getBean("sysHandoverConfigMainService");
					List<SysHandoverConfigMain> sysHandoverConfigMains = sysHandoverConfigMainService.findList("sysHandoverConfigMain.fdState is null", null);
					if (sysHandoverConfigMains != null) {
						for (SysHandoverConfigMain sysHandoverConfigMain : sysHandoverConfigMains) {
							sysHandoverConfigMain.setFdState(SysHandoverConstant.HANDOVER_STATE_SUCC);
							sysHandoverConfigMainService.update(sysHandoverConfigMain);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("执行数据迁移异常", e);
		}

		return SysAdminTransferResult.OK;
	}
}
