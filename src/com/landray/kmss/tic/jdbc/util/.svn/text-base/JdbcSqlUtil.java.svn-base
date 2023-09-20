package com.landray.kmss.tic.jdbc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.util.StringUtil;

public class JdbcSqlUtil {
	
	public static String sqlServerToPageSql(String sql, Integer pageCount,
			Integer currentPage, String idField) {
		/*Integer totalPage = null;
		if(totalCount % pageCount == 0){
			totalPage = totalCount / pageCount;
		}else{
			totalPage = totalCount / pageCount + 1;
		}
		int currentQueryRow = pageCount * currentPage;
		if ((currentPage == totalPage)) {
			pageCount = pageCount- (currentQueryRow - totalCount);
			currentQueryRow = totalCount;
		}
		sql = "SELECT TOP "+ currentQueryRow +" * FROM ("+ sql +
				") as t1 ORDER BY "+ "primaryKey" +" ASC";
		sql = "SELECT TOP "+ pageCount +" * FROM("+ sql +") AS t2 ORDER BY "+ "primaryKey" +" DESC";
		sql = "SELECT * FROM ("+ sql +") AS t3 ORDER BY "+ "primaryKey" +" ASC";
		return sql;*/
		String[] split ;
		split = sql.split(" from ");
		if(split.length != 2){
			split = sql.split(" FROM ");
		}
		Integer startPageNo = pageCount*(currentPage - 1);
		String pageSql = "SELECT TOP " + pageCount
				+ " * FROM( SELECT ROW_NUMBER() OVER (ORDER BY " + idField
				+ ") AS RowNumber"
				+ ","+split[0].substring(7, split[0].length())+" FROM "+split[1]+" )as A WHERE RowNumber > "+startPageNo;
		return pageSql;
	}
	
	/**
	 * SQLServer数据库的分页语句处理
	 * 
	 * @param sql
	 * @param pageCount
	 *            一页多少条数据
	 * @param currentPage
	 *            当前页
	 * @param totalCount
	 *            总条数量
	 * @return
	 * @author 严海星 2019年1月29日
	 */
	public static String sqlServerToPageSql(String sql, Integer pageCount,
			Integer currentPage) {
		return sqlServerToPageSql(sql, pageCount, currentPage, "fd_id");
	}

	public static void main(String[] args) {
		
		String sql = "select fd_id,fd_type,fd_name from ekpv15.sys_test where fd_id='1'";
		sqlServerToPageSql(sql, 10, 1);
		
		/*Map<String,Object> valueMapping = new HashMap<String,Object>();
		for(int i = 1 ; i < 3;i++){
			JSONObject jo = new JSONObject();
			jo.put("value","value"+i);
			jo.put("type", "string");
			valueMapping.put(i+"", jo);
		}
		JSONObject mapjo = new JSONObject(valueMapping);
		System.out.println(mapjo.toJSONString());
		
		Map<String,Object> tranmap = mapjo;
		Set<String> keySet = tranmap.keySet();
		for(String key : keySet){
			JSONObject object =  JSON.parseObject(tranmap.get(key).toString());
			System.out.print(Integer.valueOf(key));
			System.out.println(object.get("type"));
			//System.out.println(key+"--"+object.get("value")+";"+object.getByteValue("type"));
		}*/
	}
	
	/**
	 * DB2数据库的分页语句处理
	 * @param sql
	 * @param pageCount
	 * @param currentPage
	 * @return
	 * @author 严海星
	 * 2019年1月29日
	 */
	public static String db2ToPageSql(String sql , Integer pageCount , Integer currentPage)
	{
		sql = "select * from (select db2_t.*, ROW_NUMBER() OVER() AS ROWNUM from ("
			+ sql +") db2_t ) db2_ta where ROWNUM > "
			+ pageCount * (currentPage - 1) +" and ROWNUM <= "+ pageCount * currentPage;
		return sql;
	}
	
	/**
	 * MYSQL数据库的分页语句处理
	 * @param sql
	 * @param pageCount
	 * @param currentPage
	 * @return
	 * @author 严海星
	 * 2019年1月29日
	 */
	public static String mySqlToPageSql(String sql , Integer pageCount , Integer currentPage)
	{
		int startRow = (currentPage - 1) * pageCount; //起始行从0开始
		sql = sql + " limit " + startRow+","+pageCount;  //从第几行开始，取多少条数据
		return sql;
	}
	
	/**
	 * ORACLE数据库的分页语句处理
	 * @param sql
	 * @param pageCount
	 * @param currentPage
	 * @return
	 * @author 严海星
	 * 2019年1月29日
	 */
	public static String oracleToPageSql(String sql , Integer pageCount , Integer currentPage)
	{
		int startRow = (currentPage - 1) * pageCount; 
		int endRow=pageCount * currentPage;
		sql = "select * from ( select tic_jdbc01.*,rownum rn from (" + sql +") tic_jdbc01 where rownum <="+endRow +") tic_jdbc02 where tic_jdbc02.rn >="+startRow;  
		return sql;
	}
	
	public static String frommatSqlRemoveLimitCondition(String sql) {
		List<String> conditions = new ArrayList<String>();
		StringBuffer sbreplace = new StringBuffer();
		Pattern p = Pattern.compile("\\[[^\\[\\]]+\\]");
		Matcher m = p.matcher(sql);
		int tag = 0;
		while (m.find()) {
			String condition = m.group();
			if (!(condition.contains("startIndex"))) {
				conditions.add(condition);
			}
			tag++;
			m.appendReplacement(sbreplace, "");
		}
		sbreplace = new StringBuffer(sbreplace.subSequence(0, sbreplace.length() - tag));
		for (String condition : conditions) {
			sbreplace.append(" " + condition);
		}
		sql = m.appendTail(sbreplace).toString();
		return sql;
	}
	
	/**
	 * 去除没有传参的查询条件并格式化为预编译语句
	 * @param sqlExpression
	 * @param paramJo
	 * @param valueMapping
	 * @return
	 * @author 严海星
	 * 2018年11月30日
	 */
	public static String removeNoQueryConditionAndFrommat(String sqlExpression,JSONObject paramJo,Map<String,Object> valueMapping)
	{
		Set<String> paramNames = paramJo.keySet();
		//过滤入参json对象中value没有值的字段
		JSONObject realParamJo = new JSONObject();
		for(String paramName : paramNames)
		{
			Object obj = paramJo.get(paramName);
			if(obj != null && obj instanceof JSONObject){
				JSONObject jo = paramJo.getJSONObject(paramName);
				if(StringUtil.isNotNull(jo.getString("value")))
				{
					realParamJo.put(paramName, jo);
				}
			}else if(obj != null && obj instanceof String && StringUtil.isNotNull(obj.toString())){
				JSONObject jo = new JSONObject();
				jo.put("value", obj);
				jo.put("type", "string");
				realParamJo.put(paramName, jo);
			}
		}
		paramNames = realParamJo.keySet();
		
		sqlExpression = removeNoQueryCondition(sqlExpression,paramNames);
		List<String> conditionList = new ArrayList<String>();
		List<String> likeConditionList = new ArrayList<String>();
		for(int i = 0 ; i < paramNames.size() ; i++)
		{
			int a = sqlExpression.indexOf("[");
			int b = sqlExpression.indexOf("]");
			String substr = sqlExpression.substring(a, b+1); 
			sqlExpression = sqlExpression.replace(substr, "");
			if(substr.contains("like"))
			{
				likeConditionList.add(substr);
			}else
			{
				conditionList.add(substr);
			}
		}
		Map<Integer,String> conditionMap = new HashMap<Integer,String>();
		for(int i = 0 ; i < likeConditionList.size() ; i++)
		{//模糊查询条件语句处理
			JSONObject paramInfoJo = null;
			String conditionSql = likeConditionList.get(i);
			int a = conditionSql.indexOf("{");
			int b = conditionSql.indexOf("}");
			String substr = conditionSql.substring(a, b+1); 
			String fieldName = null;
			//模糊查询语句
			fieldName = substr.substring(1, substr.length()-1);
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
			paramInfoJo = realParamJo.getJSONObject(fieldName);
			int indexOf = conditionSql.indexOf("'");
			int lastIndexOf = conditionSql.lastIndexOf("'");
			String subString = conditionSql.substring(indexOf, lastIndexOf+1);
			conditionSql = conditionSql.replace(subString, "?");
			String sub1 = subString.substring(1,subString.length()-1);
			paramInfoJo.put("value", sub1.replace("?", paramInfoJo.getString("value")));
			
			Integer keyIndex = conditionMap.keySet().size()+1;
			
			valueMapping.put(keyIndex.toString(), paramInfoJo);
			//去除中括号
			conditionSql = conditionSql.substring(1,conditionSql.length()-1);
			conditionMap.put(keyIndex, conditionSql);
		}
		for(int i = 0 ; i < conditionList.size() ; i++)
		{
			JSONObject paramInfoJo = null;
			String conditionSql = conditionList.get(i);
			int a = conditionSql.indexOf("{");
			int b = conditionSql.indexOf("}");
			String substr = conditionSql.substring(a, b+1); 
			String fieldName = null;
			if(substr.contains("startIndex"))
			{//含有分页信息,需计算分页数据并设值
				
			}else{
				fieldName = substr.substring(1, substr.length()-1);
				conditionSql = conditionSql.replace("'"+substr+"'", "?");
				if(conditionSql.contains(substr))//预防自写语句没带单引号
                {
                    conditionSql = conditionSql.replace(substr, "?");
                }
			}
			if(paramInfoJo == null) {
                paramInfoJo = realParamJo.getJSONObject(fieldName);
            }
			
			Integer keyIndex = conditionMap.keySet().size()+1;
			
			valueMapping.put(keyIndex.toString(), paramInfoJo);
			//去除中括号
			conditionSql = conditionSql.substring(1,conditionSql.length()-1);
			conditionMap.put(keyIndex, conditionSql);
		}
		StringBuilder sb = new StringBuilder(sqlExpression.trim());
		for(int b = 1; b < conditionMap.keySet().size()+1; b++)
		{
			sb.append(" "+conditionMap.get(b));
		}
		return sb.toString();
	}
	
	/**
	 * 去除没有传入参数的查询条件
	 * @param sql
	 * @param paramKey
	 * @return
	 * @author 严海星
	 * 2018年11月29日
	 */
	public static String removeNoQueryCondition(String sql , Set<String> paramKey)
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
			if (!hasValue) {
				m.appendReplacement(sbreplace, " ");
			}
		}
		sql = m.appendTail(sbreplace).toString();
		return sql;
	}
}
