package com.landray.kmss.sys.zone.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;

import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.util.SpringBeanUtil;

public class SysZoneTransferChecker implements ISysAdminTransferChecker{
	
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	private ISysZonePersonInfoService sysZonePersonInfoService = null;
	
	public ISysZonePersonInfoService getSysZonePersonInfoService() {
		if(sysZonePersonInfoService == null) {
			sysZonePersonInfoService = (ISysZonePersonInfoService)SpringBeanUtil.getBean("sysZonePersonInfoService");
		}
		return sysZonePersonInfoService;
	}
	@Override
	public SysAdminTransferCheckResult check(
			SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		Session session = null;
		try {
			session = getSysZonePersonInfoService().getBaseDao().getHibernateSession();
			
			String hql = "select count(*)  from com.landray.kmss.sys.zone.model.SysZonePersonInfo sysZonePersonInfo "  + 
						"where sysZonePersonInfo.fdAttentionNum is null or sysZonePersonInfo.fdFansNum  is null";
			Object  count = session.createQuery(hql).uniqueResult();
			if(Long.valueOf(count.toString()).longValue() > 0 ) {
				return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
			} else {
				return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
			}
		}catch(Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
	}

}
