package com.landray.kmss.tic.jdbc.service.spring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.component.dbop.ds.DataSet;
import com.landray.kmss.component.dbop.model.CompDbcp;
import com.landray.kmss.component.dbop.service.ICompDbcpService;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.core.common.service.ITicCoreFuncBaseService;
import com.landray.kmss.tic.jdbc.executor.JdbcDispatcherExecutor;
import com.landray.kmss.tic.jdbc.model.TicJdbcDataSet;
import com.landray.kmss.tic.jdbc.service.ITicJdbcDataSetService;
import com.landray.kmss.tic.jdbc.service.ITicJdbcMappManageService;
import com.landray.kmss.tic.jdbc.util.JdbcDB2Util;
import com.landray.kmss.tic.jdbc.util.JdbcMysqlUtil;
import com.landray.kmss.tic.jdbc.util.JdbcOracleUtil;
import com.landray.kmss.tic.jdbc.util.JdbcSqlServerUtil;
import com.landray.kmss.tic.jdbc.util.JdbcUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 映射配置业务接口实现
 * 
 * @author
 * @version 1.0 2013-07-24
 */
public class TicJdbcMappManageServiceImp extends BaseServiceImp implements
		ITicJdbcMappManageService {

	//private Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 预览数据
	 */
	@Override
    public Map<String,Object> getTableData(RequestContext requestInfo) throws Exception {
		String dbId = requestInfo.getParameter("dbId");
		String sqlExpression = requestInfo.getParameter("sourceSql");
		String inparam = requestInfo.getParameter("inparam");
		JSONObject jo = JSON.parseObject(inparam);
		//预编译时值的index映射
		// Map<Integer,JSONObject> valueMapping = new
		// HashMap<Integer,JSONObject>();
		List<JSONObject> valueList = new ArrayList<JSONObject>();
		
		//格式化入参数据带有type
		ITicCoreFuncBaseService ticCoreFuncBaseService = (ITicCoreFuncBaseService) SpringBeanUtil.getBean("ticCoreFuncBaseService");
		TicCoreFuncBase ticCoreFuncBase = (TicCoreFuncBase) ticCoreFuncBaseService
				.findFunc(dbId);
		JSONArray inParamArray = JSON.parseArray(ticCoreFuncBase.getFdParaIn());
		Map<String,String> nameAndTypeMap = new HashMap<String,String>();
		for(int i = 0 ; i < inParamArray.size() ; i++){
			JSONObject paramJo = inParamArray.getJSONObject(i);
			nameAndTypeMap.put(paramJo.getString("name"), paramJo.getString("type"));
		}
		JSONObject realParamJo = new JSONObject();
		Set<String> paramNames = jo.keySet();
		for(String paramName : paramNames){
			Object paramValue = jo.get(paramName);
			if(paramValue != null && StringUtil.isNotNull(paramValue.toString())){
				if(paramValue instanceof JSONObject){
					realParamJo.put(paramName,paramValue);
				}else{
					JSONObject realJo = new JSONObject();
					realJo.put("value", paramValue);
					String type;
					type = nameAndTypeMap.get(paramName);
					if(StringUtil.isNotNull(type)){
						realJo.put("type", type);
					}else{
						realJo.put("type", "string");
					}
					realParamJo.put(paramName, realJo);
				}
			}
		}
		
		//去除没有传参的查询条件并格式化成预编译sql语句
		sqlExpression = JdbcDispatcherExecutor.removeNoQueryConditionAndFrommat(
				sqlExpression, realParamJo, valueList);
		
		ITicJdbcDataSetService ticJdbcDataSetService = (ITicJdbcDataSetService) SpringBeanUtil.getBean("ticJdbcDataSetService");
		TicJdbcDataSet ticJdbcDataSet = (TicJdbcDataSet) ticJdbcDataSetService.findByPrimaryKey(dbId);
		String dataSource = ticJdbcDataSet.getFdDataSource();
		//jdbc数据库连接
		ICompDbcpService dbs = (ICompDbcpService) SpringBeanUtil
				.getBean("compDbcpService");
		CompDbcp compDbcp = (CompDbcp) dbs.findByPrimaryKey(dataSource);
		com.landray.kmss.util.ClassUtils.forName(compDbcp.getFdDriver());
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection connControl = null;
		PreparedStatement psControl = null;
		//返回数据集
		List<List<String>> resultList = new ArrayList<List<String>>();
		List<String> titleList = new ArrayList<String>();
		
		try {
			// 执行sql表达式取传出数据
			conn = DriverManager.getConnection(compDbcp.getFdUrl(), compDbcp
					.getFdUsername(), compDbcp.getFdPassword());
			ps = conn
					.prepareStatement(sqlExpression,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			//预编译设值
			if (valueList != null && valueList.size() > 0)
			{
				for (int i = 0; i < valueList.size(); i++)
				{
					//根据valueMapping的映射关系设值
					JSONObject valueJo = valueList.get(i);
					if("int".equals(valueJo.getString("type").toLowerCase())){
						ps.setInt(i+1, Integer.valueOf(valueJo.getString("value")));
					}else if("float".equals(valueJo.getString("type").toLowerCase())){
						ps.setFloat(i+1,Float.valueOf(valueJo.getString("value")));
					}else if("boolean".equals(valueJo.getString("type").toLowerCase())){
						ps.setBoolean(i+1, Boolean.valueOf(valueJo.getString("value")));
					}else{
						ps.setString(i+1, valueJo.getString("value"));
					}
				}
			}
			rs = ps.executeQuery();
			if(rs != null){
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				
				//获取表头信息
				for (int i = 1; i <= columnCount; i++) {
					titleList.add(metaData.getColumnLabel(i));
				}
				
				while (rs.next()) {
					List<String> dataList = new ArrayList<String>();
					for (int i = 1; i <= columnCount; i++) {
						Object obj = rs.getObject(i);
						if (obj == null) {
							obj = "";
						}
						dataList.add(String.valueOf(obj));
					}
					resultList.add(dataList);
				}
			}
		}finally
		{
			if (conn != null) {
				conn.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (connControl != null) {
				connControl.close();
			}
			if (psControl != null) {
				psControl.close();
			}
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("titleList", titleList);
		resultMap.put("resultList", resultList);
		return resultMap;
	}

	/**
	 * 截取掉输入sql中结尾处含有分号的情况
	 * 
	 * @param sourceSql
	 * @return
	 * @throws Exception
	 */
	public String checkSql(String sourceSql) throws Exception {
		if (sourceSql.indexOf(";") != -1) {
			int indexNum = sourceSql.indexOf(";");
			if (indexNum != sourceSql.length() - 1) {
				throw new Exception("sql syntax error：" + sourceSql);
			} else {
				sourceSql = sourceSql.substring(0, sourceSql.length() - 1);
			}
		}
		return sourceSql;
	}

	/**
	 * 为Sql语句添加取数据条数的限制条件
	 * 
	 * @param compDbcp
	 * @param sourceSql
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public String getSqlWithCondition(CompDbcp compDbcp, String sourceSql,
			int startRow, int endRow) {
		String executeSql = "";
		// 添加该限制条件，否则在数据量大时会出现内存溢出
		String dbType = compDbcp.getFdType().trim();
		if (dbType.equals(JdbcUtil.DB_TYPE_MYSQL)) {
			executeSql = "select * from (" + sourceSql + ") as temp limit "
					+ startRow + " , " + endRow;
		} else if (dbType.equals(JdbcUtil.DB_TYPE_MSSQLSERVER)) {
			executeSql = "select top " + endRow + " tempTab.* from ("
					+ sourceSql + ") as tempTab";
		} else if (dbType.equals(JdbcUtil.DB_TYPE_ORACLE)) {
			executeSql = "select * from (select rownum rn,temp.* from ("
					+ sourceSql + ") temp) temp2 where temp2.rn<=" + endRow;
		} else if (dbType.equals(JdbcUtil.DB_TYPE_DB2)) {
			executeSql = "select * from (" + sourceSql + ") TEMP fetch first "
					+ endRow + " rows only";
		}
		return executeSql;
	}

	/**
	 * 取数据源数据
	 */
	@Override
    public Map getDataSource() throws Exception {
		ICompDbcpService compDbcpService = (ICompDbcpService) SpringBeanUtil
				.getBean("compDbcpService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("compDbcp.fdId,compDbcp.fdName");
		List result = compDbcpService.findList(hqlInfo);
		Map<String, String> resultMap = new HashMap<String, String>();
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				resultMap.put((String) obj[0], (String) obj[1]);
			}
		}
		return resultMap;
	}

	/**
	 * 取出源表中是日期时间的字段
	 */
	@Override
    public List getTableFieldData(Map paraMap) throws Exception {
		String dbId = (String) paraMap.get("dbId");
		String sourceSql = (String) paraMap.get("sourceSql");
		List<String> field_List = new ArrayList<String>();

		ResultSet rs = null;
		CompDbcp compDbcp = JdbcUtil.getCompDbcp(dbId);
		DataSet ds = new DataSet(compDbcp.getFdName());

		if (StringUtils.isNotEmpty(sourceSql)) {
			sourceSql = sourceSql.replaceAll("&#13;&#10;", "").replaceAll(
					"\t|\r|\n", " ");

			if ((sourceSql.toUpperCase().indexOf("SELECT") == -1
					|| sourceSql.toUpperCase().indexOf("FROM") == -1)) {
				return field_List;
			}

			// 对sql语句结尾处含有分号的进行去掉
			sourceSql = checkSql(sourceSql);
			// 对sql添加上取数据条数的限制条件
			sourceSql = getSqlWithCondition(compDbcp, sourceSql, 1, 1);

			rs = ds.executeQuery(sourceSql);
			if (rs != null) {
				ResultSetMetaData metaData = rs.getMetaData();
				String columnName = "";
				String columnType = "";
				String optinVal = "";
				try {
					for (int i = 1, length = metaData.getColumnCount(); i <= length; i++) {
						columnName = metaData.getColumnLabel(i);
						columnType = metaData.getColumnTypeName(i);

						if (compDbcp.getFdType().equalsIgnoreCase(
								JdbcUtil.DB_TYPE_MYSQL)) {
							if (JdbcMysqlUtil.validateRQType4Mysql(columnType)) {
								optinVal = columnName;
								field_List.add(optinVal);
							}
							;
						} else if (compDbcp.getFdType().equalsIgnoreCase(
								JdbcUtil.DB_TYPE_ORACLE)) {
							if (JdbcOracleUtil
									.validateRQType4Oracle(columnType)) {
								optinVal = columnName;
								field_List.add(optinVal);
							}
							;
						} else if (compDbcp.getFdType().equalsIgnoreCase(
								JdbcUtil.DB_TYPE_MSSQLSERVER)) {
							if (JdbcSqlServerUtil
									.validateRQType4SqlServer(columnType)) {
								optinVal = columnName;
								field_List.add(optinVal);
							}
							;
						} else if (compDbcp.getFdType().equalsIgnoreCase(
								JdbcUtil.DB_TYPE_DB2)) {
							if (JdbcDB2Util.validateRQType4DB2(columnType)) {
								optinVal = columnName;
								field_List.add(optinVal);
							}
							;
						} else {
							throw new Exception("this database is not support.");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				} finally {
					try {
						if (rs != null) {
							rs.close();
						}
						if (ds != null) {
							ds.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return field_List;
	}

	/**
	 * 取源表数据中的所有经过映射的字段
	 */
	@Override
    public Set<String> getSourceTabFieldData(Map paraMap) throws Exception {
		Set<String> field_Set = new HashSet<String>();
		String fdMapConfigJson = (String) paraMap.get("fdMapConfigJson");
		JSONObject jsonObject = JSON.parseObject(fdMapConfigJson);
		Set<String> keySet = jsonObject.keySet();
		for(String key : keySet){
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for(int i = 0 ; i < jsonArray.size() ; i++){
				JSONObject columnObj = jsonArray.getJSONObject(i);
				String mappFieldName = columnObj.getString("mappFieldName");
				if (StringUtil.isNotNull(mappFieldName)) {
					field_Set.add(mappFieldName);
				}
			}
		}
		/*for (Iterator<String> it = jsonObject.keys(); it.hasNext();) {
			String key = it.next();
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (Iterator<JSONObject> it2 = jsonArray.iterator(); it2.hasNext();) {
				JSONObject columnObj = it2.next();
				String mappFieldName = columnObj.getString("mappFieldName");
				if (StringUtil.isNotNull(mappFieldName)) {
					field_Set.add(mappFieldName);
				}
			}
		}*/
		return field_Set;
	}

	/**
	 * 取源表数据中的所有字段
	 */
	// public List getSourceTabFieldData(Map paraMap)throws Exception{
	// String dbId = (String) paraMap.get("dbId");
	// String sourceSql = (String) paraMap.get("sourceSql");
	// List<String> field_List = new ArrayList<String>();
	// ResultSet rs = null;
	// CompDbcp compDbcp = JdbcUtil.getCompDbcp(dbId);
	// DataSet ds = new DataSet(compDbcp.getFdName());
	//
	// if (StringUtils.isNotEmpty(sourceSql)) {
	// sourceSql = sourceSql.replaceAll("&#13;&#10;", "").replaceAll(
	// "\t|\r|\n", " ").toUpperCase();
	// if ((sourceSql.indexOf("SELECT") == -1 || sourceSql.indexOf("FROM") ==
	// -1)) {
	// return field_List;
	// }
	//		
	// //对sql语句结尾处含有分号的进行去掉
	// sourceSql=checkSql(sourceSql);
	// //对sql添加上取数据条数的限制条件
	// String executeSql=getSqlWithCondition(compDbcp,sourceSql,1,1);
	//		
	// rs = ds.executeQuery(executeSql);
	//		  
	// if (rs != null) {
	// ResultSetMetaData metaData = rs.getMetaData();
	// String columnName = "";
	// try {
	// for (int i = 1, length = metaData.getColumnCount(); i <= length; i++) {
	// columnName = metaData.getColumnLabel(i).toLowerCase();
	// field_List.add(columnName);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw e;
	// } finally {
	// try {
	// if(rs!=null) rs.close();
	// if(ds != null) ds.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	// return field_List;
	// }
}
