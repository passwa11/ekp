package com.landray.kmss.sys.organization.transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 参数配置统一管理
 * 
 * @author 潘永辉 2016-08-22
 * 
 */
public class SysOrganizationInitPasswordTask implements ISysAdminTransferTask {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {

		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement psselect = null;
		PreparedStatement psupdate = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			psselect = conn
					.prepareStatement(
							"select fd_id,fd_init_password from sys_org_person where fd_init_password is not null and fd_init_password!=''");
			psupdate = conn
					.prepareStatement(
							"update sys_org_person set fd_init_password=? where fd_id=?");

			rs = psselect.executeQuery();
			while (rs.next()) {
				String fd_id = rs.getString(1);
				String password = rs.getString(2);
				password = PasswordUtil.desEncrypt(password);
				psupdate.setString(1, password);
				psupdate.setString(2, fd_id);
				psupdate.addBatch();
			}
			psupdate.executeBatch();
			conn.commit();
		} catch (Exception e) {
			logger.error(e.toString());
			return new SysAdminTransferResult(
					ISysAdminTransferConstant.TASK_RESULT_ERROR, e);
		} finally {
			// 关闭流
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					logger.error("关闭流出错", ex);
				} catch (Throwable ex) {
					logger.error("关闭流出错", ex);
				}
			}
			JdbcUtils.closeStatement(psselect);
			JdbcUtils.closeStatement(psupdate);
			JdbcUtils.closeConnection(conn);
		}

		return SysAdminTransferResult.OK;
	}

}
