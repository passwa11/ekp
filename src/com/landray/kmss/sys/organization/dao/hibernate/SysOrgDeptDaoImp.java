package com.landray.kmss.sys.organization.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.landray.kmss.sys.organization.dao.ISysOrgDeptDao;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOrgDeptDaoImp extends SysOrgElementDaoImp implements ISysOrgDeptDao {

	/**
	 * 根据ID获取部门名称
	 */
	@Override
	public String getOriginalName(String id) throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		Connection conn = null;
		PreparedStatement ps = null;
		String name = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement("select fd_name from sys_org_element where fd_id = ?");
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
