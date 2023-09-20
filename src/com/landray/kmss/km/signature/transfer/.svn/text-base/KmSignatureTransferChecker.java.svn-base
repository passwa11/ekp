package com.landray.kmss.km.signature.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.query.Query;

import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 检测签章管理迁移任务是否需要执行
 * 
 * @author 魏本源
 * @version 1.0 2015-09-30
 */
public class KmSignatureTransferChecker implements ISysAdminTransferChecker {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Override
    public SysAdminTransferCheckResult check(
			SysAdminTransferCheckContext sysAdminTransferCheckContext) {

		IKmSignatureMainService kmSignatureMainService = (IKmSignatureMainService) SpringBeanUtil
				.getBean("kmSignatureMainService");

		String hql = "select count(*) from com.landray.kmss.km.signature.model.KmSignatureMain kmSignatureMain where 1 = 1";

		try {
			Query q = kmSignatureMainService.getBaseDao().getHibernateSession()
					.createQuery(hql);
			Long count = (Long) q.uniqueResult();
			if (count == 0) {
				return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
			}

		} catch (Exception e) {
			logger.error("签章管理数据迁移异常", e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}
}
