package com.landray.kmss.sys.zone.transfer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.zone.constant.SysZoneConstant;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.util.SpringBeanUtil;

public class SysZoneTransferPicChecker implements ISysAdminTransferChecker {
	
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	private ISysAttMainCoreInnerService sysAttMainService = null;

	public ISysAttMainCoreInnerService getSysZonePersonInfoService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SysAdminTransferCheckResult check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			List<SysAttMain> atts = (List<SysAttMain>) getSysZonePersonInfoService().findPage(new HQLInfo(),
					SysZonePersonInfo.class.getName(), SysZoneConstant.BIG_PHOTO_KEY).getList();
			if (!atts.isEmpty()) {
				return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
	}

}
