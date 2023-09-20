package com.landray.kmss.km.archives.transfer;

import org.hibernate.query.Query;

import com.landray.kmss.km.archives.service.IKmArchivesBorrowService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.SpringBeanUtil;

public class KmArchivesTask extends KmArchivesChecker
		implements ISysAdminTransferTask {
	@Override
    public SysAdminTransferResult run(
			SysAdminTransferContext sysAdminTransferContext) {
		IKmArchivesMainService kmArchivesMainService = (IKmArchivesMainService) SpringBeanUtil
				.getBean("kmArchivesMainService");
		IKmArchivesBorrowService kmArchivesBorrowService = (IKmArchivesBorrowService) SpringBeanUtil
				.getBean("kmArchivesBorrowService");
		try {

			String hql1 = "update KmArchivesMain  set fd_last_modified_time=doc_create_time where fd_last_modified_time is null ";
			String hql2 = "update KmArchivesBorrow  set fd_last_modified_time=doc_create_time where fd_last_modified_time is null ";
			Query q1 = kmArchivesMainService.getBaseDao()
					.getHibernateSession().createQuery(hql1);
			q1.executeUpdate();
			Query q2 = kmArchivesMainService.getBaseDao()
					.getHibernateSession().createQuery(hql2);
				q2.executeUpdate();	
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);

		}
		
		return SysAdminTransferResult.OK;
	}

}
