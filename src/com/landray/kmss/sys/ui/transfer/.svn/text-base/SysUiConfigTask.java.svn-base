package com.landray.kmss.sys.ui.transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

/**
 * 参数配置统一管理
 * 
 * @author 潘永辉 2016-9-2
 *
 */
public class SysUiConfigTask implements ISysAdminTransferTask {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		String uuid = sysAdminTransferContext.getUUID();
		ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil.getBean("sysAdminTransferTaskService");

		try {
			List<SysAdminTransferTask> list = sysAdminTransferTaskService.getBaseDao().findValue(null, "sysAdminTransferTask.fdUuid='" + uuid + "'", null);
			if (list != null && list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = list.get(0);
				if (sysAdminTransferTask.getFdStatus() != 1) {
					DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
					Connection conn = null;
					PreparedStatement ps = null;
					try {
						conn = dataSource.getConnection();
						ps = conn.prepareStatement("update sys_app_config set fd_key = ? where fd_key = ?");
						ps.setString(1, "com.landray.kmss.sys.ui.model.SysUiConfig");
						ps.setString(2, "com.landray.kmss.sys.person.model.SysPersonConfig");
						ps.execute();
					} catch (Exception e) {
						logger.error("检查是否执行过旧数据迁移为空异常", e);
					} finally {
						JdbcUtils.closeStatement(ps);
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
