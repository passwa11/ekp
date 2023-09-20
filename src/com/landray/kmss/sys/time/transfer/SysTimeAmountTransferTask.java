package com.landray.kmss.sys.time.transfer;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.util.SpringBeanUtil;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户额度场所数据迁移
 * 
 * @author linxiuxian
 *
 */
public class SysTimeAmountTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeAmountTransferTask.class);

	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		Session session = baseDao.getHibernateSession();
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = com.landray.kmss.sys.hibernate.spi.ConnectionWrapper.getInstance().getConnection(session);
			String sql = "update sys_time_leave_amount set fd_operator_id=doc_creator_id,doc_creator_id=fd_person_id where fd_operator_id is null";
			stat = conn.prepareStatement(sql);
			stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeStatement(stat);
			session.flush();
			session.clear();
		}
		return SysAdminTransferResult.OK;
	}

	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");
			ISysTimeLeaveAmountService sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil
					.getBean("sysTimeLeaveAmountService");

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
			// 全新数据库不需要数据迁移
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setRowSize(15);
			List countList = sysTimeLeaveAmountService.findValue(hqlInfo);
			if (countList.isEmpty()) {
				return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}

		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}
}
