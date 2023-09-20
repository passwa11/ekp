package com.landray.kmss.hr.organization.transfer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * <P>人事档案数据迁移到人事组织架构</P>
 * @author sunj
 * @version 1.0 2019年12月5日
 */
public class HrOrganizationPersonTask extends HrOrganizationPersonChecker implements
		ISysAdminTransferTask {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		String uuid = sysAdminTransferContext.getUUID();
		ISysAdminTransferTaskService sysAdminTransferTaskService=(ISysAdminTransferTaskService)SpringBeanUtil.getBean("sysAdminTransferTaskService");
		IHrOrganizationElementService hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil
				.getBean("hrOrganizationElementService");
		try {
			List sysAdminTransferList=new ArrayList();
			sysAdminTransferList=sysAdminTransferTaskService.getBaseDao().findValue(null, "sysAdminTransferTask.fdUuid='"+uuid+"'", null);		
			SysAdminTransferTask sysAdminTransferTask=(SysAdminTransferTask)sysAdminTransferList.get(0);
			/*if (sysAdminTransferTask.getFdStatus() != 1) {*/
				hrOrganizationElementService.initStaffPerson();
			/*}*/
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);
		}
		
		return SysAdminTransferResult.OK;
	}


}
