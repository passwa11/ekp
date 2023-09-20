package com.landray.kmss.tic.jdbc.executor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.dbop.ds.DataSet;
import com.landray.kmss.component.dbop.model.CompDbcp;
import com.landray.kmss.component.dbop.service.ICompDbcpService;
import com.landray.kmss.sys.xform.base.service.controls.relation.RelationOuterSearchParams;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.core.common.service.ITicCoreFuncBaseService;
import com.landray.kmss.tic.core.log.model.TicCoreLogMain;
import com.landray.kmss.tic.core.middleware.executor.ITicDispatcherExecutor;
import com.landray.kmss.tic.jdbc.model.TicJdbcDataSet;
import com.landray.kmss.tic.jdbc.service.ITicJdbcDataSetService;
import com.landray.kmss.tic.jdbc.util.JdbcSqlUtil;
import com.landray.kmss.tic.jdbc.util.JdbcUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONNull;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.slf4j.Logger;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.util.encoders.Base64;

public class JdbcDispatcherExecutor implements ITicDispatcherExecutor {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(JdbcDispatcherExecutor.class);

	private static String handleLike(String conditionSql, JSONObject paramJo,
			List<JSONObject> valueList) {
		// 模糊查询条件语句处理
			JSONObject paramInfoJo = null;
			int a = conditionSql.indexOf("{");
			int b = conditionSql.indexOf("}");
			String substr = conditionSql.substring(a, b+1); 
			String fieldName = null;
			//模糊查询语句
			fieldName = substr.substring(1, substr.length()-1);
			if(fieldName.startsWith("?")){
				//如果带有?的的参数是搜索参数,需要特别处理
				String oldName = fieldName;
				fieldName = fieldName.substring(1);
				conditionSql = conditionSql.replace(oldName, fieldName);
			}
			//计算%的出现的个数
			int times = 0;
			String[] splitArray = conditionSql.split("%");
			//if(times.length)
			for(String s : splitArray)
			{
				if(StringUtil.isNotNull(s)) {
                    times++;
                }
			}
			times--;
			if(times >= 2 ){
				//需要判断%是否在参数的两边还是都是在其中一边
				int index1 = conditionSql.indexOf("%");
				int index2 = conditionSql.indexOf("{");
				int index3 = conditionSql.indexOf("}");
				int index4 = conditionSql.lastIndexOf("%");
				if(index2 > index1 && index3 < index4)
				{//%在参数两边
					conditionSql = conditionSql.replaceAll("%(.*?("+fieldName+").*?)%", "%?%");
				}else if(index2 > index1 && index2 > index4)
				{//%都在左边
					String subStr = conditionSql.substring(index4,index3+1);
					conditionSql = conditionSql.replace(subStr, "%?'");
				}else if(index3 < index1 && index3 < index4)
				{//%都在右边
					String subStr = conditionSql.substring(index2,index1);
					conditionSql = conditionSql.replace(subStr, "'?");
				}
			}else if(times == 1){//需要判断%在哪个位置
				//判断%在哪边
				int index = conditionSql.indexOf("%");
				String subStr = conditionSql.substring(index, conditionSql.length());
				if(subStr.contains(fieldName))
				{//%在左边
					int index2 = conditionSql.indexOf("}");
					String subStr2 = conditionSql.substring(index,index2+1);
					conditionSql = conditionSql.replace(subStr2, "%?'");
				}else
				{//%在右边
					int index2 = conditionSql.indexOf("{");
					String subStr2 = conditionSql.substring(index2,index);
					conditionSql = conditionSql.replace(subStr2, "'?");
				}
			}
			//paramInfoJo = realParamJo.getJSONObject(fieldName);
			paramInfoJo = paramJo.getJSONObject(fieldName);
			int indexOf = conditionSql.indexOf("'");
			int lastIndexOf = conditionSql.lastIndexOf("'");
			String subString = conditionSql.substring(indexOf, lastIndexOf+1);
			conditionSql = conditionSql.replace(subString, "?");
			String sub1 = subString.substring(1,subString.length()-1);
			paramInfoJo.put("value", sub1.replace("?", paramInfoJo.getString("value")));
			
		// Integer keyIndex = conditionMap.keySet().size()+1;
			
		valueList.add(paramInfoJo);
			//去除中括号
			conditionSql = conditionSql.substring(1,conditionSql.length()-1);
		// conditionMap.put(keyIndex, conditionSql);
		return conditionSql;
	}

	private static String handleCondition(String conditionSql,
			JSONObject paramJo, List<JSONObject> valueList) {
		JSONObject paramInfoJo = null;
		int a = conditionSql.indexOf("{");
		int b = conditionSql.indexOf("}");
		String substr = conditionSql.substring(a, b + 1);
		String fieldName = null;
		if (substr.contains("startIndex")) {// 含有分页信息,需计算分页数据并设值

			// pagesize
		} else {
			fieldName = substr.substring(1, substr.length() - 1);
			conditionSql = conditionSql.replace("'" + substr + "'", "?");
			if (conditionSql.contains(substr))// 预防自写语句没带单引号
            {
                conditionSql = conditionSql.replace(substr, "?");
            }
		}
		if (paramInfoJo == null) {
            paramInfoJo = paramJo.getJSONObject(fieldName);
        }
		// paramInfoJo = realParamJo.getJSONObject(fieldName);

		// Integer keyIndex = conditionMap.keySet().size()+1;

		valueList.add(paramInfoJo);
		// 去除中括号
		conditionSql = conditionSql.substring(1, conditionSql.length() - 1);
		// conditionMap.put(keyIndex, conditionSql);
		return conditionSql;
	}

	/**
	 * 去除没有传参的查询条件并格式化为预编译语句
	 * 
	 * @param sqlExpression
	 * @param paramJo
	 * @param valueList
	 * @return
	 * @author 严海星 2018年11月30日
	 */
	public static String removeNoQueryConditionAndFrommat(String sqlExpression,
			JSONObject paramJo, List<JSONObject> valueList) {
		Set<String> paramNames = paramJo.keySet();
		/*
		 * //过滤入参json对象中value没有值的字段 JSONObject realParamJo = new JSONObject();
		 * for(String paramName : paramNames) { Object obj =
		 * paramJo.get(paramName); if(obj != null && obj instanceof JSONObject){
		 * JSONObject jo = paramJo.getJSONObject(paramName);
		 * if(StringUtil.isNotNull(jo.getString("value"))) {
		 * realParamJo.put(paramName, jo); } }else if(obj != null && obj
		 * instanceof String && StringUtil.isNotNull(obj.toString())){
		 * JSONObject jo = new JSONObject(); jo.put("value", obj);
		 * jo.put("type", "string"); realParamJo.put(paramName, jo); } }
		 * paramNames = realParamJo.keySet();
		 */
		Map<String, String> remap = removeNoQueryCondition(sqlExpression,
				paramNames);
		sqlExpression = remap.get("sqlExpression");
		Integer conditionCount = Integer.valueOf(remap.get("conditionCount"));
		// Map<String, String> conditionList = new HashMap<String, String>();
		// Map<String, String> likeConditionList = new HashMap<String,
		// String>();
		// 固定值条件语句
		// List<String> fixConditionList = new ArrayList<String>();
		for (int i = 0; i < conditionCount; i++)
		{
			int a = sqlExpression.indexOf("[");
			int b = sqlExpression.indexOf("]");
			String substr = sqlExpression.substring(a, b + 1);
			sqlExpression = sqlExpression.replace(substr, "#@" + i + "@#");

			// 判断是否是固定值判断语句
			if (!substr.contains("{")) {
				// 去中括号
				substr = substr.substring(1, substr.length() - 1);
				// fixConditionList.add(substr);
				sqlExpression = sqlExpression.replace("#@" + i + "@#", substr);
			} else {
				if (substr.contains("like")) {
					// likeConditionList.put("#@" + i + "@#", substr);
					String likeStr = handleLike(substr, paramJo, valueList);
					sqlExpression = sqlExpression.replace("#@" + i + "@#",
							likeStr);

				} else {
					// conditionList.put("#@" + i + "@#", substr);
					String conditionStr = handleCondition(substr, paramJo,
							valueList);
					sqlExpression = sqlExpression.replace("#@" + i + "@#",
							conditionStr);
				}
			}
		}
		Map<Integer, String> conditionMap = new HashMap<Integer, String>();

		//
		// for(int i = 0 ; i < conditionList.size() ; i++)
		// {
		//
		// }
		// StringBuilder sb = new StringBuilder(sqlExpression.trim());
		// for(int b = 1; b < conditionMap.keySet().size()+1; b++)
		// {
		// sb.append(" "+conditionMap.get(b));
		// }
		// for(String fixCondition : fixConditionList){
		// sb.append(" "+fixCondition);
		// }
		return sqlExpression;
	}
	
	/**
	 * 去除没有传入参数的查询条件
	 * @param sql
	 * @param paramKey
	 * @return
	 * @author 严海星
	 * 2018年11月29日
	 */
	public static Map<String,String> removeNoQueryCondition(String sql , Set<String> paramKey)
	{
		Integer conditionCount = 0;
		Map<String,String> result = new HashMap<String,String>();
		Pattern p = Pattern.compile("\\[[^\\[\\]]+\\]");
		Matcher m = p.matcher(sql);
		StringBuffer sbreplace = new StringBuffer();
		// 提取参数信息
		while (m.find()) {
			Pattern p2 = Pattern.compile("'?\\{\\??(\\w+)\\}'?");
			Matcher m2 = p2.matcher(m.group());
			String condition = m.group();
			boolean hasValue = false;
			if(!condition.contains("{")){//如果没有花括号就是该条件为固定值
				hasValue = true;
			}
			while (m2.find()) {
				if (paramKey.contains(m2.group(1))) {
					hasValue = true;
					break;
				}
			}
			// 有值 需要保留
			if (!hasValue) {
				m.appendReplacement(sbreplace, " ");
			}else{
				conditionCount+=1;
			}
		}
		sql = m.appendTail(sbreplace).toString();
		result.put("sqlExpression", sql);
		result.put("conditionCount", conditionCount.toString());
		return result;
	}
	
	/**
	 * 去除没有传参的sql条件语句以及中括号
	 * @param sql
	 * @param paramKey
	 * @return
	 * @author 严海星
	 * 2018年11月29日
	 */
	public static String removeNoQueryConditionAndSquareBrackets(String sql , Set<String> paramKey)
	{
		Pattern p = Pattern.compile("\\[[^\\[\\]]+\\]");
		Matcher m = p.matcher(sql);
		StringBuffer sbreplace = new StringBuffer();
		// 提取参数信息
		while (m.find()) {
			Pattern p2 = Pattern.compile("'?\\{\\??(\\w+)\\}'?");
			Matcher m2 = p2.matcher(m.group());
			boolean hasValue = false;
			while (m2.find()) {
				if (paramKey.contains(m2.group(1))) {
					hasValue = true;
					break;
				}
			}
			// 有值 需要保留
			if (hasValue) {
				m.appendReplacement(sbreplace, m.group().replace("[", "")
						.replace("]", ""));
			} else {
				m.appendReplacement(sbreplace, " ");
			}
		}
		sql = m.appendTail(sbreplace).toString();
		return sql;
	}
	
	/**
	 * 调用该方法的json结构
	 * {
	 * 	"fieldName":{
	 * 					type:"string",
	 * 					value:"value"
	 * 				}
	 * }
	 * 
	 * @param jo
	 * @param funcId
	 * @return
	 * @throws Exception
	 * @author 严海星
	 * 2018年10月21日
	 */
	@Override
	public String execute(JSONObject jo, String funcId, TicCoreLogMain log) throws Exception {
		//tic_jdbc_data_set    16690574ab93401cfb58b76486681fa9
		log.setFdImportParOri(jo.toString());
		//获取分页信息
		Object controlPageSize = jo.remove("controlPageSize");
		Object controlPageNum = jo.remove("controlPageNum");
		
		ITicJdbcDataSetService ticJdbcDataSetService = (ITicJdbcDataSetService) SpringBeanUtil.getBean("ticJdbcDataSetService");
		TicJdbcDataSet ticJdbcDataSet = (TicJdbcDataSet) ticJdbcDataSetService.findByPrimaryKey(funcId);
		String dataSource = ticJdbcDataSet.getFdDataSource();
		String sqlExpression = ticJdbcDataSet.getFdSqlExpression();
		//预编译时值的index映射
		// Map<Integer,JSONObject> valueMapping = new
		// HashMap<Integer,JSONObject>();
		List<JSONObject> valueList = new ArrayList<JSONObject>();
		
		//格式化入参数据带有type
		ITicCoreFuncBaseService ticCoreFuncBaseService = (ITicCoreFuncBaseService) SpringBeanUtil.getBean("ticCoreFuncBaseService");
		TicCoreFuncBase ticCoreFuncBase =  (TicCoreFuncBase) ticCoreFuncBaseService.findByPrimaryKey(funcId);
		JSONArray inParamArray = JSON.parseArray(ticCoreFuncBase.getFdParaIn());
		Map<String,String> nameAndTypeMap = new HashMap<String,String>();
		for(int i = 0 ; i < inParamArray.size() ; i++){
			JSONObject paramJo = inParamArray.getJSONObject(i);
			nameAndTypeMap.put(paramJo.getString("name"), paramJo.getString("type"));
		}
		JSONObject realParamJo = new JSONObject();
		Set<String> paramNames = jo.keySet();
		for(String paramName : paramNames){
			JSONObject realJo = new JSONObject();
			Object paramValue = jo.get(paramName);
			if(paramValue != null && StringUtil.isNotNull(paramValue.toString())){
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
		
		//去除没有传参的查询条件并格式化成预编译sql语句
		sqlExpression = removeNoQueryConditionAndFrommat(sqlExpression,
				realParamJo, valueList);

		String sql_count = "select count(*) from ( "+sqlExpression.replaceAll("\r\n"," ") + " ) aaa";
		
		//jdbc数据库连接
		ICompDbcpService dbs = (ICompDbcpService) SpringBeanUtil
				.getBean("compDbcpService");
		CompDbcp compDbcp = (CompDbcp) dbs.findByPrimaryKey(dataSource);
		com.landray.kmss.util.ClassUtils.forName(compDbcp.getFdDriver());
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps_count = null;
		PreparedStatement getCountPS = null;//用于sqlserver需要分页时另外查询数据总数
		DataSet ds = null;//用于获取主键,用于sqlserver数据库分页使用
		ResultSet rs = null;
		ResultSet rs_count = null;
		ResultSet countRs = null;//用于sqlserver需要分页时另外查询数据总数
		ResultSet pkRs = null;//用于sqlserver需要分页时获取主键
		Connection connControl = null;
		Integer totalCount = null;
		//返回数据集
		JSONArray rtn_array = new JSONArray();
		try {
			long start = System.currentTimeMillis();
			//建立连接
//			conn = DriverManager.getConnection(compDbcp.getFdUrl(), compDbcp
//					.getFdUsername(), compDbcp.getFdPassword());
			DataSet dataSet = new DataSet(compDbcp.getFdName());
			conn = dataSet.getConnection();
			logger.debug("建立数据库连接耗时："+(System.currentTimeMillis()-start)+"ms");
			//判断是否需要分页处理
			if(controlPageNum != null && controlPageSize != null && ((Integer)controlPageSize != 0)){
				//分页处理
				Integer pageCount = (Integer) controlPageSize;//每页数量
				Integer currentPage = (Integer) controlPageNum;//当前页
				String dbType = compDbcp.getFdType();
				//sqlserver数据库的分页处理
				if (JdbcUtil.DB_TYPE_MSSQLSERVER.equals(dbType)) {
					//sqlserver的分页需要获取数据总数信息
					/*String countSql = "select count(0) from (" + sqlExpression + ") tempTab";
					getCountPS = conn.prepareStatement(countSql);
					//预编译设值
					if(valueMapping != null && valueMapping.size() > 0)
					{
						for(int i = 0 ;i < valueMapping.size(); i++)
						{
							//根据valueMapping的映射关系设值
							JSONObject valueJo = valueMapping.get(i+1);
							if("int".equals(valueJo.getString("type").toLowerCase())){
								getCountPS.setInt(i+1, Integer.valueOf(valueJo.getString("value")));
							}else if("float".equals(valueJo.getString("type").toLowerCase())){
								getCountPS.setFloat(i+1,Float.valueOf(valueJo.getString("value")));
							}else if("boolean".equals(valueJo.getString("type").toLowerCase())){
								getCountPS.setBoolean(i+1, Boolean.valueOf(valueJo.getString("value")));
							}else{
								getCountPS.setString(i+1, valueJo.getString("value"));
							}
						}
					}
					Integer totalCount = null;
					countRs = getCountPS.executeQuery();
					if (countRs.next()) {
						totalCount = countRs.getInt(1);
					}*/
					
					/*//获取需要查询表的主键sourcePK
					//String primaryKey = "";
					String getSourcePkSql = sqlExpression.substring(0, sqlExpression.indexOf("["));
					ds = new DataSet(compDbcp.getFdName());
					pkRs = ds.executeQuery(getSourcePkSql);
					if(pkRs != null){
						ResultSetMetaData metaData = pkRs.getMetaData();
						//DatabaseMetaData databaseMetaData = ds.getConnection().getMetaData();
						int columnCount = metaData.getColumnCount();

						if (columnCount > 0) {
							List<String> tabNameList = getTabNameList(getSourcePkSql);
							if (tabNameList != null && tabNameList.size() > 0) {
								for (int i = 0; i < tabNameList.size(); i++) {
									String tableName = tabNameList.get(i);// metaData.getTableName(1);
									tableName = tableName.trim();

									if (tableName.contains(" ")) {
										return metaData.getColumnName(columnCount);
									}
									ResultSet pkRSet = databaseMetaData.getPrimaryKeys(
											null, null, tableName.trim());
									if (pkRSet.next()) {
										primaryKey = (String) pkRSet.getObject(4);
										if (StringUtils.isNotEmpty(primaryKey)) {
											break;
										}
									}
								}
								if (!StringUtils.isNotEmpty(primaryKey)) {
									logger.warn("源表SQL结果字段中没有主键，SqlServer下全量同步必须要主键");
									return metaData.getColumnName(columnCount);
								}
							} else {
								throw new Exception("源表SQL无法解析出表名或主键");
							}
						}
					}*/
					String idField = "fd_id";
					String paraOut = ticJdbcDataSet.getFdParaOut();
					if (StringUtil.isNotNull(paraOut)) {
						JSONArray array = JSONArray.parseArray(paraOut);
						if (array != null && array.size() > 0) {
							JSONObject obj = array.getJSONObject(0);
							if (obj != null && obj.containsKey("children")) {
								JSONArray children = obj
										.getJSONArray("children");
								if (children != null && children.size() > 0) {
									JSONObject fieldObj = children
											.getJSONObject(0);
									idField = fieldObj.getString("name");
								}
							}
						}
					}
					 sqlExpression = JdbcSqlUtil.sqlServerToPageSql(
					 sqlExpression, pageCount, currentPage, idField);
							
				} else if (JdbcUtil.DB_TYPE_DB2.equals(dbType)) {//DB2数据库的分页处理
					sqlExpression = JdbcSqlUtil.db2ToPageSql(sqlExpression, pageCount, currentPage);
				} else if (JdbcUtil.DB_TYPE_MYSQL.equals(dbType)) {//mysql数据库的分页处理
					sqlExpression = JdbcSqlUtil.mySqlToPageSql(sqlExpression, pageCount, currentPage);
				} else if (JdbcUtil.DB_TYPE_ORACLE.equals(dbType)) {//oracle数据库的分页处理
					sqlExpression = JdbcSqlUtil.oracleToPageSql(sqlExpression, pageCount, currentPage);
				}
			}
			
			ps = conn.prepareStatement(sqlExpression, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps_count = conn.prepareStatement(sql_count);
			//预编译设值
			if (valueList != null && valueList.size() > 0)
			{
				for (int i = 0; i < valueList.size(); i++)
				{
					//根据valueMapping的映射关系设值
					JSONObject valueJo = valueList.get(i);
					if("int".equals(valueJo.getString("type").toLowerCase())){
						ps.setInt(i+1, Integer.valueOf(valueJo.getString("value")));
						ps_count.setInt(i+1, Integer.valueOf(valueJo.getString("value")));
					}else if("float".equals(valueJo.getString("type").toLowerCase())){
						ps.setFloat(i+1,Float.valueOf(valueJo.getString("value")));
						ps_count.setFloat(i+1,Float.valueOf(valueJo.getString("value")));
					}else if("boolean".equals(valueJo.getString("type").toLowerCase())){
						ps.setBoolean(i+1, Boolean.valueOf(valueJo.getString("value")));
						ps_count.setBoolean(i+1, Boolean.valueOf(valueJo.getString("value")));
					}else{
						ps.setString(i+1, valueJo.getString("value"));
						ps_count.setString(i+1, valueJo.getString("value"));
					}
				}
			}
			long startTime=System.currentTimeMillis();
			rs = ps.executeQuery();
			long endTime = System.currentTimeMillis();
			long fdTimeConsuming=((endTime - startTime));
			log.setFdTimeConsuming(String.valueOf(fdTimeConsuming));
			logger.info("sql执行耗时:"+fdTimeConsuming+ " ms");
			ResultSetMetaData metaData = rs.getMetaData();
			int length = metaData.getColumnCount();
			
			while (rs.next()) {
				JSONObject rowData = new JSONObject();
				for (int i = 1; i <= length; i++) {
					Object obj = rs.getObject(i);
					if (obj == null) {
						obj = "";
					}
					String columnName = metaData.getColumnLabel(i);
					String value = String.valueOf(obj);
					rowData.put(columnName, value);
				}
				rtn_array.add(rowData);
			}

			try {
				long start2 = System.currentTimeMillis();
				rs_count = ps_count.executeQuery();
				if (rs_count.next()) {
					totalCount = rs_count.getInt(1);
				}
				logger.debug("获取总数耗时："+(System.currentTimeMillis()-start2)+"ms");
			}catch (Exception e){
				logger.error("获取总数失败，"+sql_count,e);
			}
		}finally
		{
			if (conn != null) {
				conn.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (ps_count != null) {
				ps_count.close();
			}
			if (getCountPS != null) {
				getCountPS.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (rs_count != null) {
				rs_count.close();
			}
			if (countRs != null) {
				countRs.close();
			}
			if (pkRs != null) {
				pkRs.close();
			}
			if (ds != null) {
				ds.close();
			}
			if (connControl != null) {
				connControl.close();
			}
		}
		
		JSONObject export = new JSONObject();
		export.put("out", rtn_array);
		if(totalCount!=null){
			export.put("controlTotalCount",totalCount+"");
		}
		log.setFdExportParOri(export.toString());
		return export.toJSONString();
	}
	
	/**
	 * 取出sql语句中的表名称
	 * @param sql
	 */
	public static List<String> getTabNameList(String sql) {
		sql = sql.replaceAll("\\[", "").replaceAll("\\]", "");
		List<String> tabNameList = new ArrayList<String>();
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		try {
			Select select = (Select) parserManager.parse(new StringReader(sql));
			PlainSelect plain = (PlainSelect) select.getSelectBody();
			FromItem fromItem = plain.getFromItem();
			tabNameList.add(fromItem.toString());
			List<Join> joins = plain.getJoins();
			if(joins!=null){
				for (Join join : joins) {
					tabNameList.add(join.toString());
				}
			}
		} catch (JSQLParserException e) {
			// TODO 自动生成 catch 块
			logger.error(e.toString());
		}

		return tabNameList;
	}

	@Override
	public TicCoreFuncBase findFunc(String fdId) throws Exception {
		// TODO Auto-generated method stub
		return (TicCoreFuncBase) getTicJdbcDataSetService()
				.findByPrimaryKey(fdId);
	}

	@Override
	public TicCoreFuncBase findFuncByKey(String fdKey) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		//hqlInfo.setSelectBlock("fdFuncType");
		hqlInfo.setWhereBlock("fdKey=:fdKey");
		hqlInfo.setParameter("fdKey", fdKey);
		return (TicCoreFuncBase) getTicJdbcDataSetService().findFirstOne(hqlInfo);
	}

	public ITicJdbcDataSetService getTicJdbcDataSetService() {
		if (ticJdbcDataSetService == null) {
			ticJdbcDataSetService = (ITicJdbcDataSetService) SpringBeanUtil
					.getBean("ticJdbcDataSetService");
		}
		return ticJdbcDataSetService;
	}

	private ITicJdbcDataSetService ticJdbcDataSetService;

	@Override
	public List<RelationOuterSearchParams> getOutSearchParams(String funcId) {
		List<RelationOuterSearchParams> outerSearchs = null;
		ITicCoreFuncBaseService ticCoreFuncBaseService = (ITicCoreFuncBaseService) SpringBeanUtil
				.getBean("ticCoreFuncBaseService");
		TicJdbcDataSet ticJdbcDataSet;
		try {
			ticJdbcDataSet = (TicJdbcDataSet) ticCoreFuncBaseService
					.findFunc(funcId);
			JSONObject jsonObject = JSONObject
					.parseObject(ticJdbcDataSet.getFdData());
			if (jsonObject.containsKey("search")
					&& !(jsonObject.get("search") instanceof JSONNull)) {
				outerSearchs = new ArrayList<RelationOuterSearchParams>();
				// 搜索
				JSONArray searchJsonArr = jsonObject.getJSONArray("search");
				for (Iterator<Object> it = searchJsonArr.iterator(); it
						.hasNext();) {
					JSONObject columnObj = (JSONObject) it.next();
					String tagName = columnObj.getString("tagName");
					RelationOuterSearchParams childField = new RelationOuterSearchParams();
					childField.setTagName(tagName);
					childField.setUuid(new String(Base64.encode(tagName.getBytes())));
					childField.setColumnName(columnObj.getString("columnName"));
					childField.setSdefault(columnObj.getString("sdefault"));
					childField.setStype(columnObj.getString("stype"));
					childField.setStypeData(columnObj.getString("stypeData"));
					String sdesc = columnObj.getString("sdesc");
					childField.setSdesc(StringUtil.isNull(sdesc)?tagName:sdesc);
					outerSearchs.add(childField);
				}
			}
			return outerSearchs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
		}
		return null;

	}

	@Override
	public String executeRest(JSONObject jo, String funcId, TicCoreLogMain log)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
