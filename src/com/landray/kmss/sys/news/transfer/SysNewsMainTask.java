package com.landray.kmss.sys.news.transfer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.news.service.ISysNewsMainService;
import com.landray.kmss.util.SpringBeanUtil;

public class SysNewsMainTask extends SysNewsMainChecker implements
		ISysAdminTransferTask {
	@Override
    public SysAdminTransferResult run(
			SysAdminTransferContext sysAdminTransferContext) {
		String uuid=sysAdminTransferContext.getUUID();	
		ISysAdminTransferTaskService sysAdminTransferTaskService=(ISysAdminTransferTaskService)SpringBeanUtil.getBean("sysAdminTransferTaskService");
		ISysNewsMainService sysNewsMainService = (ISysNewsMainService) SpringBeanUtil
				.getBean("sysNewsMainService");
		ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService");
		try {
			List list=new ArrayList();
			list=sysAdminTransferTaskService.getBaseDao().findValue(null, "sysAdminTransferTask.fdUuid='"+uuid+"'", null);		
			SysAdminTransferTask sysAdminTransferTask=(SysAdminTransferTask)list.get(0);
			if (sysAdminTransferTask.getFdStatus() != 1) {
				HQLInfo hql = new HQLInfo();
				hql
						.setWhereBlock("sysNewsMain.fdModelId is not null and sysNewsMain.fdModelName = 'com.landray.kmss.km.imissive.model.KmImissiveSendMain' and sysNewsMain.fdIsLink is null");
				List<SysNewsMain> newsList = sysNewsMainService.findList(hql);
				if (newsList.size() > 0) {
					for (SysNewsMain sysNewsMain : newsList) {
						sysNewsMain.setFdContentType("word");
						
						List l = sysAttMainService.findByModelKey(
								"com.landray.kmss.sys.news.model.SysNewsMain",
								sysNewsMain.getFdId(), sysNewsMain
										.getFdModelId());
						for (int j = 0; j < l.size(); j++) {
							SysAttMain sysAttMain = (SysAttMain) l.get(j);
							sysAttMain.setFdKey("editonline");
							sysAttMainService.update(sysAttMain);
						}
						sysNewsMainService.update(sysNewsMain);
					}
				}
			}
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);
		}
		
		return SysAdminTransferResult.OK;
	}
}
