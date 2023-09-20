package com.landray.kmss.tic.jdbc.service.spring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.ClassUtils;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.component.dbop.model.CompDbcp;
import com.landray.kmss.sys.hibernate.spi.ConnectionWrapper;
import com.landray.kmss.tic.jdbc.service.ITicJdbcDataSetService;
import com.landray.kmss.util.StringUtil;

import bsh.This;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 数据集管理业务接口实现
 * 
 * @author 
 * @version 1.0 2014-04-15
 */
public class TicJdbcDataSetServiceImp extends BaseServiceImp implements ITicJdbcDataSetService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(This.class);

	@Override
    public void loadDbtableDatas(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String fdDataSource = request.getParameter("fdDataSource");
		String fdDbtable = request.getParameter("fdDbtable");
		JSONObject result = new JSONObject();
		if (StringUtil.isNotNull(fdDataSource)
				&& StringUtil.isNotNull(fdDbtable)) {
			String fdSqlExpression = "select * from " + fdDbtable;
			Connection conn = null;
			PreparedStatement pstmtAllCol = null;
			ResultSet rsAllCol = null;

			PreparedStatement pstmtCol = null;
			ResultSet rsCol = null;
			try {
				if (StringUtil.isNotNull(fdDataSource)) {
					conn = this.getCreateConn(fdDataSource);
				} else {
					conn = ConnectionWrapper.getInstance().getConnection(this.getBaseDao().getHibernateSession());
				}

				// 求全部列
				pstmtAllCol = conn.prepareStatement(fdSqlExpression);
				rsAllCol = pstmtAllCol.executeQuery();
				if (rsAllCol != null) {
					JSONArray datas = new JSONArray();
					ResultSetMetaData metaData = rsAllCol.getMetaData();
					// 输出数据集的字段集
					for (int i = 1, length = metaData
							.getColumnCount(); i <= length; i++) {
						JSONObject data = new JSONObject();
						data.put("columnName", metaData.getColumnLabel(i));
						data.put("columnType", metaData.getColumnTypeName(i));
						data.put("length", String
								.valueOf(metaData.getColumnDisplaySize(i)));
						datas.add(data);
					}
					result.put("status", "00");
					result.put("datas", datas);
					result.put("dbtype", getCompDbcpType(fdDataSource));
				} else {
					result.put("status", "01");
					result.put("errlog", "找不到" + fdDbtable + "的列表！");
				}

			} catch (Exception e) {
				logger.error("方法loadDbtableDatas获取数量列表发生异常：" + e);
				result.put("status", "01");
				result.put("errlog", "方法loadDbtableDatas获取数量列表发生异常：" + e);
			} finally {
				JdbcUtils.closeResultSet(rsAllCol);
				JdbcUtils.closeStatement(pstmtAllCol);
				JdbcUtils.closeResultSet(rsCol);
				JdbcUtils.closeStatement(pstmtCol);
				JdbcUtils.closeConnection(conn);
			}
		} else {
			result.put("status", "01");
			result.put("errlog", "数据源或者表名不能为空!");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result.toString());
	}

	private Session getSession() {
		return this.getBaseDao().getHibernateSession();
	}

	/**
	 * 获取外部数据库的连接对象
	 * 
	 * @param fdDataSource
	 * @return
	 * @throws Exception
	 */
	private Connection getCreateConn(String fdDataSource) throws Exception {
		CompDbcp compDbcps = getCompDbcp(fdDataSource);
		com.landray.kmss.util.ClassUtils.forName(compDbcps.getFdDriver());
		return DriverManager.getConnection(compDbcps.getFdUrl(), compDbcps
				.getFdUsername(), compDbcps.getFdPassword());
	}

	private String getCompDbcpType(String fdDataSource) {
		CompDbcp compDbcps = getCompDbcp(fdDataSource);
		return compDbcps.getFdType();
	}

	private CompDbcp getCompDbcp(String fdDataSource) {
		CompDbcp compDbcps = (CompDbcp) getSession().get(CompDbcp.class,
				fdDataSource);
		return compDbcps;
	}
}
