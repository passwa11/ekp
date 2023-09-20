package com.landray.kmss.sys.ui.transfer;

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
 * 参数配置统一管理
 * 
 * @author 潘永辉 2016-9-2
 *
 */
public class SysUiConfigChecker implements ISysAdminTransferChecker {
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
			ps = conn.prepareStatement("select count(1) from sys_app_config where fd_key = ?");
			ps.setString(1, "com.landray.kmss.sys.person.model.SysPersonConfig");
			rs = ps.executeQuery();
			int count = 0;
			if (rs.next()) {
                count = rs.getInt(1);
            }
			
			if (count > 0) {
				isRuned = false;
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
