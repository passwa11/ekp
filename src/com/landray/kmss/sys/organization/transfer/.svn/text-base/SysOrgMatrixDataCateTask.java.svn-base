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

/**
 * 矩阵组织分组标识
 * 
 * @author 潘永辉 2020-02-22
 * 
 */
public class SysOrgMatrixDataCateTask implements ISysAdminTransferTask {
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
						ps = conn.prepareStatement("SELECT fd_sub_table FROM sys_org_matrix");
						rs = ps.executeQuery();
						while (rs.next()) {
							String tableName = rs.getString(1);
							PreparedStatement sub_ps = null;
							boolean hasType = false;
							try {
								sub_ps = conn.prepareStatement("SELECT fd_cate_id FROM " + tableName);
								sub_ps.executeQuery();
								// 如果SQL执行成功，说明有fd_cate_id字段
								hasType = true;
							} catch (Exception e) {
								logger.info("查询矩阵数据表[" + tableName + "]失败，缺少字段，需要做迁移：" + e.getMessage());
							} finally {
								JdbcUtils.closeStatement(sub_ps);
							}
							if (!hasType) {
								// 没有分组字段，需要增加
								PreparedStatement add_ps = null;
								try {
									// 增加分组字段
									add_ps = conn.prepareStatement("ALTER TABLE " + tableName + " ADD fd_cate_id VARCHAR(36) NULL");
									add_ps.executeUpdate();
								} catch (Exception e) {
									logger.error("矩阵数据表[" + tableName + "]增加版本字段失败", e);
								} finally {
									JdbcUtils.closeStatement(add_ps);
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
