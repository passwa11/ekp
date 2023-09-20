package com.landray.kmss.tic.jdbc.control.sql;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.util.ClassUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.component.dbop.model.CompDbcp;
import com.landray.kmss.component.dbop.service.ICompDbcpService;
import com.landray.kmss.sys.hibernate.spi.ConnectionWrapper;
import com.landray.kmss.util.StringUtil;

public class TicJdbcFormTemplateSQLSelectDataBean extends HibernateDaoSupport
		implements IXMLDataBean {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(TicJdbcFormTemplateSQLSelectDataBean.class);
	private ICompDbcpService compDbcpService;
	CompDbcp compDbcps = null;

	public void setCompDbcpService(ICompDbcpService compDbcpService) {
		this.compDbcpService = compDbcpService;
	}

	@Override
    public List<Object> getDataList(RequestContext requestInfo)
			throws Exception {
		// 在搜索栏中输入的关键字
		String keyword = requestInfo.getParameter("keyword");
		String sqlValue = requestInfo.getParameter("sqlValue");
		String queryolumn = requestInfo.getParameter("column");
		String orderby = requestInfo.getParameter("orderby");
		String sqlresource = requestInfo.getParameter("sqlResource");// 获取选择的数据源
		String tic_index = requestInfo.getParameter("tic_index");// 控件行号

		/**
		 * 此方法不是很好 把数据连接id和sql语句加在一起穿过来的
		 */
		if (sqlValue.indexOf("@") != -1) {
			String[] strs = sqlValue.split("@");
			sqlresource = strs[0];
			sqlValue = strs[1];
		}
		if (StringUtil.isNull(orderby)) {
			orderby = "";
		} else {
			orderby = " ORDER BY ".concat(orderby);
		}
		// 是否打开选择框，默认加载列表数据
		boolean isLoadData= "false".equals(requestInfo.getParameter("isLoadData"))?false:true;
		List<Object> rtnList = new ArrayList<Object>();
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			if ((isLoadData && StringUtil.isNotNull(sqlValue))
					|| (StringUtil.isNotNull(keyword) && StringUtil
							.isNotNull(sqlValue))) {

				if (StringUtil.isNotNull(sqlresource)) {// 判断选择的数据源获取是否为空
					conn = this.getCreateConn(sqlresource);
				} else {
					conn = ConnectionWrapper.getInstance().getConnection(this.getSessionFactory().getCurrentSession());
				}
				String sql = sqlValue;
				if (StringUtil.isNotNull(keyword)) {
					sql = handlerUnionSql(sqlValue)
							+ getWhereSql(sqlValue, queryolumn, keyword)
							+ orderby;
				}
				if (log.isDebugEnabled()) {
					log.debug("sqlControl sql:" + sql);
				}
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs != null) {
					ResultSetMetaData metaData = rs.getMetaData();
					// 所有查询列
					Map<String, String> columnMap = new HashMap<String, String>();

					for (int i = 1, length = metaData.getColumnCount(); i <= length; i++) {
						columnMap.put(metaData.getColumnLabel(i), metaData
								.getColumnLabel(i));
					}
					Map<String, String> map1 = null;

					while (rs.next()) {
						map1 = new HashMap<String, String>();
						boolean firstFlag = true;
						for (Iterator<String> ite = columnMap.keySet()
								.iterator(); ite.hasNext();) {
							String column = ite.next();
							// column = column.toUpperCase();
							//用来绑定的数据
							map1.put(column, rs.getObject(column) == null ? ""
									: rs.getObject(column).toString());
							map1.put("tic_index", tic_index);
                            //用来设置dialog_list
							if (firstFlag) {
								map1.put("id", rs.getObject(1) == null ? ""
										: rs.getObject(1).toString());
								map1.put("name",rs.getObject(2).toString());
								//把值写出来，科密需求
								map1.put("info", rs.getObject(2) == null ? ""
										: map1.get("id")+" | "+rs.getObject(2).toString());
								firstFlag = false;
							}
						}
						firstFlag = true;
						rtnList.add(map1);
					}
					columnMap = null;
					map1 = null;
				}

			}
			return rtnList;
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger.debug(ex.getMessage());
			}
			ex.printStackTrace();
			return rtnList;
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

	/**
	 * 处理sql含有union的语句
	 * 
	 * @param sql
	 * @return
	 */
	private String handlerUnionSql(String sql) {
		if (StringUtil.isNotNull(sql)
				&& (sql.indexOf("UNION") != -1 || sql.indexOf("union") != -1)) {
			return " SELECT * FROM ( " + sql + " ) ";
		}
		return sql;
	}

	/**
	 * 获取查询的where SQL语句
	 * 
	 * @param paramColumn
	 * @param keyword
	 * @return
	 */
	private String getWhereSql(String paramSqlValue, String queryColumn,
			String keyword) {

		StringBuffer whereSql = new StringBuffer(" ");
		if (StringUtil.isNotNull(queryColumn)) {
			if ((paramSqlValue.indexOf("WHERE") != -1 || paramSqlValue
					.indexOf("where") != -1)
					&& (paramSqlValue.indexOf("union") == -1 || paramSqlValue
							.indexOf("UNION") == -1)) {
				whereSql.append(" AND (");
			} else {
				whereSql.append(" WHERE 1 = 1 AND (");

			}
			String[] columnNameArr = queryColumn.split(",");

			for (int i = 0; i < columnNameArr.length; i++) {
				// 判断是否查询中文如果是中文查询前要加N
				if (keyword.getBytes().length == keyword.length()) {
					whereSql.append(columnNameArr[i]).append(" LIKE ").append(
							"N\'%").append(keyword).append("%\'");
				} else {// 否则不需要加N
					whereSql.append(columnNameArr[i]).append(" LIKE ").append(
							"N\'%").append(keyword).append("%\'");//
				}
				if (i < columnNameArr.length - 1) {
					whereSql.append(" OR ");
				}
			}
			whereSql.append(" ) ");
		} else {
			if ((paramSqlValue.indexOf("WHERE") != -1 || paramSqlValue
					.indexOf("where") != -1)
					&& (paramSqlValue.indexOf("union") == -1 || paramSqlValue
							.indexOf("UNION") == -1)) {
			} else {
			}
		}
		return whereSql.toString();
	}

	/**
	 * 获取外部数据库德连接对象
	 * 
	 * @param sqlResource
	 * @return
	 * @throws Exception
	 */
	private Connection getCreateConn(String sqlResource) throws Exception {
		this.compDbcps = (CompDbcp) compDbcpService
				.findByPrimaryKey(sqlResource);
		com.landray.kmss.util.ClassUtils.forName(compDbcps.getFdDriver());

		return DriverManager.getConnection(compDbcps.getFdUrl(), compDbcps
				.getFdUsername(), compDbcps.getFdPassword());
	}
}
