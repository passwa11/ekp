package com.landray.kmss.hr.staff.transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class HrStaffPersonExperienceContractTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	public void doTransfer() throws SQLException {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = dataSource.getConnection();
		PreparedStatement ps = null;
		try {
			String sql = "update hr_staff_person_exp_cont cont set fd_staff_cont_type_id=(select fd_id from hr_staff_contract_type where fd_name=cont.fd_cont_type)";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("执行旧数据迁移为空异常", e);
			conn.rollback();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			JdbcUtils.closeStatement(ps);
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
