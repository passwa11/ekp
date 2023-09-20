/**
 * 
 */
package com.landray.kmss.tic.jdbc.service.bean;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.util.ClassUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.component.dbop.model.CompDbcp;
import com.landray.kmss.sys.hibernate.spi.ConnectionWrapper;
import com.landray.kmss.util.StringUtil;

/**
 * @author qiujh
 * @version 1.0 2014-4-15
 */
public class TicJdbcDataSetParamBean extends HibernateDaoSupport implements
		IXMLDataBean {

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String fdDataSource = requestInfo.getParameter("fdDataSource");
		String fdSqlExpression = requestInfo
				.getParameter("fdSqlExpressionTest");
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if (StringUtil.isNotNull(fdSqlExpression)) {
			// 剔除掉回车和换行
			// fdSqlExpression = fdSqlExpression.replaceAll("&#13;&#10;", "")
			// .replaceAll("\t|\r|\n", " ").toUpperCase();
			fdSqlExpression = new String(Base64.decode(fdSqlExpression.getBytes("UTF-8")),"UTF-8");
			fdSqlExpression = fdSqlExpression.replaceAll("\\+", "ticPLUStic");
			fdSqlExpression = URLDecoder.decode(fdSqlExpression,"UTF-8");
			fdSqlExpression = fdSqlExpression.replaceAll("ticPLUStic", "\\+");
			ResultSet rs = null;
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				if (StringUtil.isNotNull(fdDataSource)) {
					conn = this.getCreateConn(fdDataSource);
				} else {
					conn = ConnectionWrapper.getInstance().getConnection(this.getSessionFactory().getCurrentSession());
				}

				// 求全部列
				pstmt = conn.prepareStatement(fdSqlExpression);
				rs = pstmt.executeQuery();
				if (rs != null) {
					ResultSetMetaData metaData = rs.getMetaData();
					// 输出数据集的字段集
					for (int i = 1, length = metaData.getColumnCount(); i <= length; i++) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("tagName", metaData.getColumnLabel(i));
						map.put("ctype", metaData.getColumnTypeName(i));
						map.put("length", String.valueOf(metaData
								.getColumnDisplaySize(i)));
						// 默认不是传出参数
						map.put("isOut", "true");
						result.add(map);
					}
					rs.close();
				}
				pstmt.close();
				// 查询列
				// pstmt = conn.prepareStatement(fdSqlExpression);
				// rs = pstmt.executeQuery();
				// if (rs != null) {
				// ResultSetMetaData metaData = rs.getMetaData();
				// // 输出数据集的字段集
				// for (int i = 1, length = metaData.getColumnCount(); i <=
				// length; i++) {
				// String tagName = metaData.getColumnLabel(i)
				// ;
				// // 遍历集合找列
				// for (Iterator<Map<String, String>> it = result
				// .iterator(); it.hasNext();) {
				// Map<String, String> map = it.next();
				// if (map.get("tagName")
				// .equalsIgnoreCase(tagName)) {
				// map.put("isOut", "true");
				// }
				// }
				// }
				// }
			} catch (Exception e) {
				e.printStackTrace();
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

	public List getDataList2(RequestContext requestInfo) throws Exception {
		String fdDataSource = requestInfo.getParameter("fdDataSource");
		String fdSqlExpression = requestInfo.getParameter("fdSqlExpression");
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if (StringUtil.isNotNull(fdSqlExpression)) {
			// 剔除掉回车和换行
			fdSqlExpression = fdSqlExpression.replaceAll("&#13;&#10;", "")
					.replaceAll("\t|\r|\n", " ");
			ResultSet rs = null;
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				int selectIndex = fdSqlExpression.toUpperCase()
						.indexOf("SELECT");
				int fromIndex = fdSqlExpression.toUpperCase().indexOf("FROM");
				if ((selectIndex == -1 || fromIndex == -1)) {
					return result;
				}
				if (fdSqlExpression.toUpperCase().indexOf("WHERE") != -1) {
					fdSqlExpression = fdSqlExpression.substring(0,
							fdSqlExpression.toUpperCase().indexOf("WHERE"));
				}
				String fdSqlAllColumnExpression = fdSqlExpression.substring(0,
						selectIndex + 6)
						+ " * " + fdSqlExpression.substring(fromIndex);
				if (StringUtil.isNotNull(fdDataSource)) {
					conn = this.getCreateConn(fdDataSource);
				} else {
					conn = ConnectionWrapper.getInstance().getConnection(this.getSessionFactory().getCurrentSession());
				}
				// 求全部列
				pstmt = conn.prepareStatement(fdSqlAllColumnExpression);
				rs = pstmt.executeQuery();
				if (rs != null) {
					ResultSetMetaData metaData = rs.getMetaData();
					// 输出数据集的字段集
					for (int i = 1, length = metaData.getColumnCount(); i <= length; i++) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("tagName", metaData.getColumnLabel(i));
						map.put("ctype", metaData.getColumnTypeName(i));
						map.put("length", String.valueOf(metaData
								.getColumnDisplaySize(i)));
						// 默认不是传出参数
						map.put("isOut", "false");
						result.add(map);
					}
					rs.close();
				}
				pstmt.close();
				// 查询列
				pstmt = conn.prepareStatement(fdSqlExpression);
				rs = pstmt.executeQuery();
				if (rs != null) {
					ResultSetMetaData metaData = rs.getMetaData();
					// 输出数据集的字段集
					for (int i = 1, length = metaData.getColumnCount(); i <= length; i++) {
						String tagName = metaData.getColumnLabel(i);
						// 遍历集合找列
						for (Iterator<Map<String, String>> it = result
								.iterator(); it.hasNext();) {
							Map<String, String> map = it.next();
							if (map.get("tagName")
									.equalsIgnoreCase(tagName)) {
								map.put("isOut", "true");
							}
						}
					}
				}
			} catch (Exception e) {
				Map<String, String> node = new HashMap<String, String>();
				node.put("error", e.getMessage());
				result.add(node);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (conn != null) {
					try {
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}

	/**
	 * 获取外部数据库德连接对象
	 * 
	 * @param fdDataSource
	 * @return
	 * @throws Exception
	 */
	private Connection getCreateConn(String fdDataSource) throws Exception {
		CompDbcp compDbcps = (CompDbcp) com.landray.kmss.sys.hibernate.spi.HibernateWrapper.getSession(this).get(CompDbcp.class,
				fdDataSource);
		com.landray.kmss.util.ClassUtils.forName(compDbcps.getFdDriver());
		return DriverManager.getConnection(compDbcps.getFdUrl(), compDbcps
				.getFdUsername(), compDbcps.getFdPassword());
	}

}
