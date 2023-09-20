package com.landray.kmss.sys.zone.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;

import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.util.SpringBeanUtil;

public class SysZoneTransferTask implements ISysAdminTransferTask {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	private ISysZonePersonInfoService sysZonePersonInfoService = null;
	
	public ISysZonePersonInfoService getSysZonePersonInfoService() {
		if(sysZonePersonInfoService == null) {
			sysZonePersonInfoService = (ISysZonePersonInfoService)SpringBeanUtil.getBean("sysZonePersonInfoService");
		}
		return sysZonePersonInfoService;
	}
	@Override
	public SysAdminTransferResult run(
			SysAdminTransferContext sysAdminTransferContext) {
		Session session = null;
		try {
			session = getSysZonePersonInfoService().getBaseDao().getHibernateSession();
			String hql = "update com.landray.kmss.sys.zone.model.SysZonePersonInfo sysZonePersonInfo set sysZonePersonInfo.fdAttentionNum = "  + 
						" (select count(*) from com.landray.kmss.sys.fans.model.SysFansMain sysFansMain " + 
							"where sysFansMain.fdFansId = sysZonePersonInfo.fdId) " + 
						 "where sysZonePersonInfo.fdAttentionNum is null";
			session.createQuery(hql).executeUpdate();
			//session.createQuery(hql).setParameter("fdAttentionNum", new Integer(0)).executeUpdate();
			String hql2 = "update com.landray.kmss.sys.zone.model.SysZonePersonInfo sysZonePersonInfo set sysZonePersonInfo.fdFansNum = "  + 
							" (select count(*) from com.landray.kmss.sys.fans.model.SysFansMain sysFansMain " + 
							"where sysFansMain.fdUserId = sysZonePersonInfo.fdId) " + 
			 				"where sysZonePersonInfo.fdFansNum is null";
			session.createQuery(hql2).executeUpdate();
			//session.createQuery(hql2).setParameter("fdFansNum", new Integer(0)).executeUpdate();
		}catch(Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return 	new SysAdminTransferResult(
						ISysAdminTransferConstant.TASK_RESULT_ERROR, "");
		}
		return SysAdminTransferResult.OK;
	}

}
