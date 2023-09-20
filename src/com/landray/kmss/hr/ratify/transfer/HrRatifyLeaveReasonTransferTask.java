package com.landray.kmss.hr.ratify.transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.SpringBeanUtil;

public class HrRatifyLeaveReasonTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	public void doTransfer() throws SQLException {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = dataSource.getConnection();
		ResultSet rs_select = null;
		PreparedStatement ps_insert = null;
		PreparedStatement ps_select = null;
		try {
			ps_insert = conn.prepareStatement(
					"insert into hr_staff_person_info_set_new values(?,?,?,?)");
			ps_select = conn.prepareStatement(
					"select * from hr_ratify_leave_reason where fd_type is null or fd_type = ?");
			ps_select.setString(1, "");
			rs_select = ps_select.executeQuery();
			while (rs_select.next()) {
				ps_insert.setString(1, rs_select.getString("fd_id"));
				ps_insert.setString(2, rs_select.getString("fd_name"));
				ps_insert.setInt(3, rs_select.getInt("fd_order"));
				ps_insert.setString(4, "fdLeaveReason");
				ps_insert.addBatch();
			}
			ps_insert.executeBatch();
		} catch (SQLException e) {
			logger.error("执行旧数据迁移为空异常", e);
			conn.rollback();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			JdbcUtils.closeStatement(ps_select);
			JdbcUtils.closeStatement(ps_insert);
			JdbcUtils.closeResultSet(rs_select);
			JdbcUtils.closeConnection(conn);
		}
	}

	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		try {
			String uuid = sysAdminTransferContext.getUUID();
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");
			List sysAdminTransferList = new ArrayList();
			sysAdminTransferList = sysAdminTransferTaskService.getBaseDao()
					.findValue(null,
							"sysAdminTransferTask.fdUuid='" + uuid + "'", null);
			SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) sysAdminTransferList
					.get(0);
			if (sysAdminTransferTask.getFdStatus() != 1) {
				doTransfer();
			}
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);
			return new SysAdminTransferResult(
					ISysAdminTransferConstant.TASK_STATUS_NOT_RUNED,
					e.getLocalizedMessage(),
					e);
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
			logger.error("", e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}

}
