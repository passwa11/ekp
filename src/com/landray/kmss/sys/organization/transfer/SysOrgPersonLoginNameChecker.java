package com.landray.kmss.sys.organization.transfer;

import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 登录名转小写
 * 
 * @author 潘永辉 2021-07-29
 * 
 */
public class SysOrgPersonLoginNameChecker implements ISysAdminTransferChecker {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

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
			ps = conn.prepareStatement("SELECT COUNT(fd_id) FROM sys_org_person WHERE fd_login_name_lower IS NULL");
			rs = ps.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
					isRuned = false;
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
