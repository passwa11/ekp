package com.landray.kmss.sys.attend.transfer;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.attend.service.ISysAttendStatService;
import com.landray.kmss.util.SpringBeanUtil;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 考勤统计相关排序数据迁移(外出工时等)
 * 
 * @author linxiuxian
 *
 */
public class SysAttendStatSortTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendStatSortTransferTask.class);

	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");
			ISysAttendStatService sysAttendStatService = (ISysAttendStatService) SpringBeanUtil
					.getBean("sysAttendStatService");

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
	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		transferSysAttendStat();
		transferSysAttendStatMonth();
		return SysAdminTransferResult.OK;
	}

	// 日统计表数据迁移
	private void transferSysAttendStat() {
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		Session session = baseDao.openSession();
		Connection conn = null;
		try {
			conn = com.landray.kmss.sys.hibernate.spi.ConnectionWrapper.getInstance().getConnection(session);
			String props = "fd_over_time,fd_missed_exc_count,fd_late_exc_count,fd_left_exc_count,fd_absent_days,fd_trip_days,fd_off_days,fd_outgoing_time";
			String[] fdPropArr = props.split(",");
			for (String fdProp : fdPropArr) {
				String uSql = "update sys_attend_stat set " + fdProp
						+ "=0 where " + fdProp + " is null";
				Statement stat = conn.createStatement();
				try {
					conn.setAutoCommit(false);
					stat.execute(uSql);
					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					logger.error(ex.getMessage(), ex);
				} finally {
					JdbcUtils.closeStatement(stat);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		session.flush();
		session.clear();
	}

	// 月统计表数据迁移
	private void transferSysAttendStatMonth() {
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		Session session = baseDao.openSession();
		Connection conn = null;
		try {
			conn = com.landray.kmss.sys.hibernate.spi.ConnectionWrapper.getInstance().getConnection(session);
			String props = "fd_over_time,fd_work_over_time,fd_off_over_time,fd_holiday_over_time,fd_holiday_over_time,fd_missed_exc_count,fd_late_exc_count,"
					+ "fd_left_exc_count,fd_absent_days_count,fd_outgoing_time";
			String[] fdPropArr = props.split(",");
			for (String fdProp : fdPropArr) {
				String uSql = "update sys_attend_stat_month set " + fdProp
						+ "=0 where " + fdProp + " is null";
				Statement stat = conn.createStatement();
				try {
					conn.setAutoCommit(false);
					stat.execute(uSql);
					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					logger.error(ex.getMessage(), ex);
				} finally {
					JdbcUtils.closeStatement(stat);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		session.flush();
		session.clear();
	}

	private List getDatas(Connection conn, String sql)
			throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> idList = new ArrayList<String>();
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();
			while (rs.next()) {
				idList.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
		}

		return idList;
	}
}
