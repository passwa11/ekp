package com.landray.kmss.sys.news.transfer;

import org.hibernate.CacheMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.query.Query;

import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.util.SpringBeanUtil;

public class SysNewsAttTask extends SysNewsAttChecker implements
		ISysAdminTransferTask {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(SysNewsAttTask.class);
	@Override
    public SysAdminTransferResult run(
			SysAdminTransferContext sysAdminTransferContext) {
		ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService");
		String updateSql = "update com.landray.kmss.sys.attachment.model.SysAttMain att_main set att_main.fdKey='editonline' where ((att_main.fdModelName='com.landray.kmss.sys.news.model.SysNewsMain' and att_main.fdKey='mainOnline')  or  (att_main.fdModelName='com.landray.kmss.sys.news.model.SysNewsTemplate' and att_main.fdKey='mainContent'))";

		try {
			Query query = sysAttMainService.getBaseDao().getHibernateSession().createQuery(updateSql);
			// 启用二级缓存
			query.setCacheable(true);
			// 设置缓存模式
			query.setCacheMode(CacheMode.NORMAL);
			// 设置缓存区域
			query.setCacheRegion("sys-news");
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("",e);
		}
		return SysAdminTransferResult.OK;
	}
}
