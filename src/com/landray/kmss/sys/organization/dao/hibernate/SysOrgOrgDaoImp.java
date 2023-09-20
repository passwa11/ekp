package com.landray.kmss.sys.organization.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.sys.organization.dao.ISysOrgOrgDao;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class SysOrgOrgDaoImp extends SysOrgElementDaoImp implements
		ISysOrgOrgDao {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/**
	 * 将部门更改为机构
	 * 
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean setDeptToOrg(String deptId) throws Exception {
		String hql = "from SysOrgElement where fdId='" + deptId + "'";
		List deptList = getHibernateTemplate().find(hql);
		if (deptList.size() > 0) {
			SysOrgElement sysOrgElement = (SysOrgElement) deptList.get(0);
			if (sysOrgElement.getHbmParent() != null
					&& sysOrgElement.getHbmParent().getFdOrgType().intValue() != SysOrgElement.ORG_TYPE_ORG) {
				// 不能把部门下的部门更新为机构
				logger.error("找到ID为" + sysOrgElement.getFdId() + "的IT部门（"
						+ sysOrgElement.getFdName() + "）的上级不是机构，该部门不能设置为机构！");
				throw new KmssRuntimeException(new KmssMessage(
						ResourceUtil.getString("sysOrgOrg.updateDeptToOrg.error","sys-organization",
								UserUtil.getKMSSUser().getLocale(),sysOrgElement.getFdName())));
			}
			Connection conn = null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			try {
				conn = com.landray.kmss.sys.hibernate.spi.ConnectionWrapper.getInstance().getConnection(super.openSession());
				// 更改类型为机构
				String sql = "update sys_org_element set fd_org_type=1,fd_alter_time=? where fd_id=?";
				pstmt1 = conn.prepareStatement(sql);
				// 更新所有子的父机构
				// 多查一次数据库，以避免不同数据库的字符串连接函数不同所带来的问题
				// String sql = "select fd_hierarchy_id from " + tableName
				// + " where fd_id = ?";
				String fdHierarchyId = getFdHierarchyIdByDeptId(conn,
						"sys_org_element", sysOrgElement.getFdId());
				sql = "update sys_org_element set fd_parentorgid=? where fd_hierarchy_id like '"
						+ fdHierarchyId + "%'";
				pstmt2 = conn.prepareStatement(sql);
				// 更新机构（机构没有父机构）
				sql = "update sys_org_element set fd_parentorgid=null where fd_id=?";
				pstmt3 = conn.prepareStatement(sql);

				/* 开始执行SQL语句系列 */
				pstmt1.setTimestamp(1,new java.sql.Timestamp(System.currentTimeMillis()));//更新时间 便于同步
				pstmt1.setString(2, sysOrgElement.getFdId());
				pstmt2.setString(1, sysOrgElement.getFdId());
				// pstmt2.setString(2, element.getFdId());
				pstmt3.setString(1, sysOrgElement.getFdId());
				if(logger.isDebugEnabled()){
					logger.debug("准备执行pstmt1...:"+pstmt1.toString());
				}
				pstmt1.executeUpdate();
				if(logger.isDebugEnabled()){
					logger.debug("准备执行pstmt2...:"+pstmt2.toString());
				}
				pstmt2.executeUpdate();
				if(logger.isDebugEnabled()){
					logger.debug("准备执行pstmt3...:"+pstmt3.toString());
				}
				pstmt3.executeUpdate();
				if(logger.isDebugEnabled()){
					logger.debug("all done !");
				}
			} finally {
				// 关闭流
				JdbcUtils.closeStatement(pstmt1);
				JdbcUtils.closeStatement(pstmt2);
				JdbcUtils.closeStatement(pstmt3);
				JdbcUtils.closeConnection(conn);
			}
		}
		return true;
	}

	/**
	 * 多查一次数据库，以避免不同数据库的字符串连接函数不同所带来的问题
	 * 
	 * @param conn
	 * @param tableName
	 * @param fdId
	 * @return
	 * @throws SQLException
	 */
	private String getFdHierarchyIdByDeptId(Connection conn, String tableName,
			String fdId) throws SQLException {
		String fdHierarchyId = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select fd_hierarchy_id from " + tableName
					+ " where fd_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, fdId);
			rs = ps.executeQuery();
			if (rs.next()) {
				fdHierarchyId = rs.getString(1);
			}
		} catch (SQLException ex) {
			logger.error("查询父级id的时候发生异常：" + ex);
			throw new SQLException(ex);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
		}
		return fdHierarchyId;
	}

	@Override
	public String getOriginalName(String id) throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement ps = null;
		String name = null;
		try {
			conn = dataSource.getConnection();
			ps = conn
					.prepareStatement("select fd_name from sys_org_element where fd_id = ?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				name = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null) {
                ps.close();
            }
			if (conn != null) {
                conn.close();
            }
		}
		return name;
	}
}
