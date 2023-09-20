package com.landray.kmss.tic.jdbc.control.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.util.ClassUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.component.dbop.model.CompDbcp;
import com.landray.kmss.sys.hibernate.spi.ConnectionWrapper;
import com.landray.kmss.util.StringUtil;

public class TicJdbcFormSQLSelectDBServiceImp extends HibernateDaoSupport implements
		IXMLDataBean {

	@Override
    @SuppressWarnings("unchecked")
	public List getDataList(RequestContext requestInfo) throws Exception {
		List result = new ArrayList();

		// 获得属性框中配置的sql语句
		String sqlvalue = requestInfo.getParameter("sqlvalue");
		String sqlResource = requestInfo.getParameter("sqlResource");

		if (StringUtil.isNotNull(sqlvalue)) {
			// 剔除掉回车和换行
			sqlvalue = sqlvalue.replaceAll("&#13;&#10;", "").replaceAll(
					"\t|\r|\n", " ");

			ResultSet rs = null;
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				if ((sqlvalue.toUpperCase().indexOf("SELECT") == -1
						|| sqlvalue.toUpperCase()
						.indexOf("FROM") == -1)) {
					return result;
				}
				if (sqlvalue.indexOf("WHERE") != -1) {
					sqlvalue = sqlvalue.substring(0, sqlvalue.indexOf("WHERE"));
				}
				if (sqlvalue.indexOf("where") != -1) {
					sqlvalue = sqlvalue.substring(0, sqlvalue.indexOf("where"));
				}
				Map<String, String> map = null;
				if (StringUtil.isNotNull(sqlResource)) {
					conn = this.getCreateConn(sqlResource);
				} else {
					conn = ConnectionWrapper.getInstance().getConnection(this.getSessionFactory().getCurrentSession());
				}
				pstmt = conn.prepareStatement(sqlvalue);
				rs = pstmt.executeQuery();

				if (rs != null) {
					ResultSetMetaData metaData = rs.getMetaData();
					// 输出数据集的字段集
					for (int i = 1, length = metaData.getColumnCount(); i <= length; i++) {
						map = new HashMap<String, String>();
						map.put("column", metaData.getColumnLabel(i));
						result.add(map);
					}
					map = null;
				}

			} catch (Exception e) {
				Map<String, String> node = new HashMap<String, String>();
				node.put("error", e.getMessage());
				result.add(node);
			} finally {
				if (rs != null) {
					try {
						rs.close();
						rs = null;
					} catch (Exception e) {
						logger.error("", e);
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
						pstmt = null;
					} catch (Exception e) {
						logger.error("", e);
					}
				}
				if (conn != null) {
					try {
						conn.close();
						conn = null;
					} catch (Exception e) {
						logger.error("", e);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 获取外部数据库德连接对象
	 * 
	 * @param sqlResource
	 * @return
	 * @throws Exception
	 */
	private Connection getCreateConn(String sqlResource) {
		try {
			CompDbcp compDbcps = (CompDbcp) com.landray.kmss.sys.hibernate.spi.HibernateWrapper.getSession(this).get(
					CompDbcp.class, sqlResource);
			com.landray.kmss.util.ClassUtils.forName(compDbcps.getFdDriver());
			return DriverManager.getConnection(compDbcps.getFdUrl(), compDbcps
					.getFdUsername(), compDbcps.getFdPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ConnectionWrapper.getInstance().getConnection(this.getSessionFactory().getCurrentSession());
	}
}
