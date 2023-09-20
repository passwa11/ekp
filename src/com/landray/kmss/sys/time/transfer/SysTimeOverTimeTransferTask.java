package com.landray.kmss.sys.time.transfer;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.SpringBeanUtil;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SysTimeOverTimeTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeAmountTransferTask.class);

	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		try {
			String sql = "update sys_time_work_detail  set fd_over_time_type=:overTimeType where fd_over_time_type is null";
			String sql2 = "update sys_time_patchwork_time  set fd_over_time_type=:overTimeType  where fd_over_time_type is null";
			String sql3 = "update sys_time_work_time  set fd_over_time_type=:overTimeType  where fd_over_time_type is null";
			NativeQuery query = baseDao.getHibernateSession().createNativeQuery(sql);
			NativeQuery query2 = baseDao.getHibernateSession().createNativeQuery(sql2);
			NativeQuery query3 = baseDao.getHibernateSession().createNativeQuery(sql3);
			query.addSynchronizedQuerySpace("sys_time_work_detail");
			query2.addSynchronizedQuerySpace("sys_time_patchwork_time");
			query3.addSynchronizedQuerySpace("sys_time_work_time");

			query.setParameter("overTimeType", 1);
			query2.setParameter("overTimeType", 1);
			query3.setParameter("overTimeType", 1);
			query.executeUpdate();
			query2.executeUpdate();
			query3.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return SysAdminTransferResult.OK;
	}

	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");
			String uuid = sysAdminTransferCheckContext.getUUID();
			List list = new ArrayList();
			list = sysAdminTransferTaskService.getBaseDao().findValue(null,
					"sysAdminTransferTask.fdUuid='" + uuid + "'", null);
			if (list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) list
						.get(0);
				if (sysAdminTransferTask.getFdStatus() == 1) {
					return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}

}
