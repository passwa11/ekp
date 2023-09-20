package com.landray.kmss.sys.organization.transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 矩阵组织版本标识
 * 
 * @author 潘永辉 2020-02-22
 * 
 */
public class SysOrgMatrixVersionChecker implements ISysAdminTransferChecker {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Override
	public SysAdminTransferCheckResult check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		if(isRuned()) {
            return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
        } else {
            return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
        }
	}
	
	/**
	 * 判断系统是否需要做迁移
	 * @return
	 */
	public boolean isRuned() {
		boolean isRuned = true;
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
				boolean hasVersion = false;
				try {
					sub_ps = conn.prepareStatement("SELECT fd_version FROM " + tableName);
					sub_ps.executeQuery();
					// 如果SQL执行成功，说明有fd_version字段
					hasVersion = true;
				} catch (Exception e) {
					logger.error("查询矩阵数据表[" + tableName + "]失败，需要做迁移：" + e.getMessage());
				} finally {
					JdbcUtils.closeStatement(sub_ps);
				}
				if (!hasVersion) {
					isRuned = false;
					break;
				}
			}
		} catch (Exception e) {
			logger.error("检查是否执行过旧数据迁移为空异常", e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
		return isRuned;
	}

}
