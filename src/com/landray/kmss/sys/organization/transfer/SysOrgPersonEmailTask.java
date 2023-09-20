package com.landray.kmss.sys.organization.transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOrgPersonEmailTask implements ISysAdminTransferTask {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		String uuid = sysAdminTransferContext.getUUID();
		ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil.getBean("sysAdminTransferTaskService");

		try {
			List<SysAdminTransferTask> list = sysAdminTransferTaskService.getBaseDao().findValue(null, "sysAdminTransferTask.fdUuid='" + uuid + "'", null);
			if (list != null && list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = list.get(0);
				if (sysAdminTransferTask.getFdStatus() != 1) {
					String filter = "{密文}";
					DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
					Connection conn = null;
					PreparedStatement psselect = null;
					PreparedStatement psupdate = null;
					ResultSet rs = null;
					try {
						conn = dataSource.getConnection();
						psselect = conn.prepareStatement("select fd_id, fd_email from sys_org_person where fd_email like ?");
						psupdate = conn.prepareStatement("update sys_org_person set fd_email = ? where fd_id = ?");
						psselect.setString(1, filter + "%");
						rs = psselect.executeQuery();
						while (rs.next()) {
							String id = rs.getString(1);
							String email = rs.getString(2);
							email = email.replace(filter, "");
							psupdate.setString(1, email);
							psupdate.setString(2, id);
							psupdate.addBatch();
						}
						psupdate.executeBatch();
					} catch (Exception e) {
						logger.error("检查是否执行过旧数据迁移为空异常", e);
					} finally {
						JdbcUtils.closeResultSet(rs);
						JdbcUtils.closeStatement(psselect);
						JdbcUtils.closeStatement(psupdate);
						JdbcUtils.closeConnection(conn);
					}
				}
			}
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);
		}

		return SysAdminTransferResult.OK;
	}

}
