package com.landray.kmss.sys.organization.transfer;

import java.sql.Connection;
import java.sql.Date;
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
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 矩阵组织版本标识
 * 
 * @author 潘永辉 2020-02-22
 * 
 */
public class SysOrgMatrixVersionTask implements ISysAdminTransferTask {
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
					ResultSet rs = null;
					try {
						conn = dataSource.getConnection();
						ps = conn.prepareStatement("SELECT fd_id, fd_sub_table FROM sys_org_matrix");
						rs = ps.executeQuery();
						while (rs.next()) {
							String matrixId = rs.getString(1);
							String tableName = rs.getString(2);
							PreparedStatement sub_ps = null;
							boolean hasVersion = false;
							try {
								sub_ps = conn.prepareStatement("SELECT fd_version FROM " + tableName);
								sub_ps.executeQuery();
								// 如果SQL执行成功，说明有fd_version字段
								hasVersion = true;
							} catch (Exception e) {
								logger.info("查询矩阵数据表[" + tableName + "]失败，缺少字段，需要做迁移：" + e.getMessage());
							} finally {
								JdbcUtils.closeStatement(sub_ps);
							}
							if (!hasVersion) {
								// 没有版本号字段，需要增加
								PreparedStatement add_ps = null;
								PreparedStatement update_ps = null;
								PreparedStatement add_version_ps = null;
								try {
									// 增加版本字段
									add_ps = conn.prepareStatement("ALTER TABLE " + tableName + " ADD fd_version VARCHAR(5) NULL");
									add_ps.executeUpdate();
									// 更新版本号
									update_ps = conn.prepareStatement("UPDATE " + tableName + " SET fd_version = 'V1'");
									update_ps.executeUpdate();
									// 增加版本信息
									add_version_ps  = conn.prepareStatement("INSERT INTO sys_org_matrix_version (fd_id, fd_name, fd_version, fd_create_time, fd_matrix_id) VALUES (?, ?, ?, ?, ?)");
									add_version_ps.setString(1, IDGenerator.generateID());
									add_version_ps.setString(2, "V1");
									add_version_ps.setInt(3, 1);
									add_version_ps.setDate(4, new Date(System.currentTimeMillis()));
									add_version_ps.setString(5, matrixId);
									add_version_ps.executeUpdate();
								} catch (Exception e) {
									logger.error("矩阵数据表[" + tableName + "]增加版本字段失败", e);
								} finally {
									JdbcUtils.closeStatement(add_ps);
									JdbcUtils.closeStatement(update_ps);
									JdbcUtils.closeStatement(add_version_ps);
								}
							}
						}
					} catch (Exception e) {
						logger.error("检查是否执行过旧数据迁移为空异常", e);
					} finally {
						JdbcUtils.closeResultSet(rs);
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
