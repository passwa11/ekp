package com.landray.kmss.sys.portal.transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysPortalFdAnonymousTransferTask extends SysPortalFdAnonymousTransferChecker implements ISysAdminTransferTask{
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysPortalFdAnonymousTransferTask.class);
	private static final String POTAL_MAIN = "sys_portal_main";
	private static final String POTAL_PAGE = "sys_portal_page";
	private static final String POTAL_PAGE_INDEX = "IDX108C48106473262C";
	private static final String POTAL_MAIN_INDEX = "IDX108AEB3A6473262C";
	
	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		try {
			//只对SQLServer数据库进行迁移
			if(!"net.sourceforge.jtds.jdbc.Driver".equals(ResourceUtil.getKmssConfigString("hibernate.connection.driverClass"))) {
                return SysAdminTransferResult.OK;
            }
			execute(POTAL_PAGE,POTAL_PAGE_INDEX);
			execute(POTAL_MAIN,POTAL_MAIN_INDEX);
		}catch (Exception e) {
			return new SysAdminTransferResult(ISysAdminTransferConstant.TASK_RESULT_ERROR, e);
		}
		return SysAdminTransferResult.OK;
	}
	
	/**
	 * 去除sys_portal_main和sys_portal_page字段fd_anonymous上的默认值约束
	 * @param tableName 表名
	 * @param sqlIndex 
	 */
	private void execute(String tableName, String sqlIndex) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
			conn = dataSource.getConnection();
			String str = "select b.name from sysobjects b join syscolumns a on b.id = a.cdefault where a.id = object_id('"+tableName+"') and a.name = 'fd_anonymous'";
			String constraint = "";
			try {
				ps = conn.prepareStatement(str);
				rs = ps.executeQuery();
				if(rs.next()) {
					constraint =rs.getString("name");
				}
			}finally {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(ps);
			}
			
			//存在约则删除
			if(StringUtil.isNotNull(constraint)) {
				String dropSql = "ALTER TABLE sys_portal_main  DROP constraint "+constraint;
				try {
					ps = conn.prepareStatement(dropSql);
					ps.executeUpdate();
				}finally {
					JdbcUtils.closeStatement(ps);
				}
			}
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT 1 FROM sys.indexes WHERE object_id=OBJECT_ID('"+tableName+"', N'U') and NAME='"+sqlIndex+"'");
			try {
				try {
					ps = conn.prepareStatement(sb.toString());
					rs = ps.executeQuery();
					if(rs.next()) {
						String dropIndexSql = "DROP INDEX ["+sqlIndex+"] ON [dbo].["+tableName+"]";
						try {
							ps = conn.prepareStatement(dropIndexSql);
							ps.executeUpdate();
						}finally {
							JdbcUtils.closeResultSet(rs);
							JdbcUtils.closeStatement(ps);
						}
						
					}
				}finally {
					JdbcUtils.closeResultSet(rs);
					JdbcUtils.closeStatement(ps);
				}
				//存在索引则删除
				
			}finally {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(ps);
			}
			//修改字段类型
			String sql = "ALTER TABLE "+tableName+" alter column fd_anonymous tinyint NULL";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.error("-----匿名门户字段类型变更SQL执行错误-------"+e);
		}finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
	}
}
