package com.landray.kmss.sys.mportal.transfer;

import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.util.SpringBeanUtil;

public class SysMportalTransferChecker implements ISysAdminTransferChecker {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Override
	public SysAdminTransferCheckResult check(
			SysAdminTransferCheckContext sysAdminTransferCheckContext) {

		int count = 0;

		try {

			IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
			Session session = baseDao.getHibernateSession();

			String sql = "select count(c.fd_id) from sys_mportal_card c where c.fd_portlet_config is null";
			//#168392 优化 更新数据时声明影响范围，避免二级缓存重建
			//count = ((Number) session.createNativeQuery(sql).list().get(0)).intValue();
			NativeQuery nativeQuery = session.createNativeQuery(sql);
			// 启用二级缓存
			nativeQuery.setCacheable(true);
			// 设置缓存模式
			nativeQuery.setCacheMode(CacheMode.NORMAL);
			// 设置缓存区域
			nativeQuery.setCacheRegion("sys-mportal");
			count = ((Number) nativeQuery.list().get(0)).intValue();
			if (count > 0) {
                return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
            }

		} catch (Exception e) {

			logger.error(e.toString());

		}

		return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
	}
}
