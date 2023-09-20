package com.landray.kmss.tic.rest.connector.service.spring;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.dbop.ds.DataSet;
import com.landray.kmss.component.dbop.model.CompDbcp;
import com.landray.kmss.sys.formula.parser.FastjsonProvider;
import com.landray.kmss.sys.formula.parser.FormulaParserByJS;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.core.common.util.TicCommonUtil;
import com.landray.kmss.tic.core.log.constant.TicCoreLogConstant;
import com.landray.kmss.tic.core.log.model.TicCoreLogMain;
import com.landray.kmss.tic.core.log.service.ITicCoreLogMainService;
import com.landray.kmss.tic.core.log.service.ITicCoreLogManageService;
import com.landray.kmss.tic.core.sync.model.TicCoreSyncJob;
import com.landray.kmss.tic.core.sync.model.TicCoreSyncTempFunc;
import com.landray.kmss.tic.core.sync.service.ITicCoreSyncUniteQuartzService;
import com.landray.kmss.tic.core.util.RecursionUtil;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;
import com.landray.kmss.tic.rest.executor.IRestDataExecutor;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.httpclient.util.DateParseException;
import org.slf4j.Logger;
import org.springframework.web.util.HtmlUtils;
import org.w3c.dom.NodeList;

import java.sql.*;
import java.text.ParseException;
import java.util.Date;
import java.util.*;

/**
 * Rest定时任务统一服务
 * 
 * @author 何建华
 * 
 * @version 2019-07-24
 */

public class TicRestSyncUniteQuartzService implements  ITicCoreSyncUniteQuartzService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TicRestSyncUniteQuartzService.class);
	
	private ITicCoreLogMainService ticCoreLogMainService;

	public ITicCoreLogMainService getTicCoreLogMainService() {
		if (ticCoreLogMainService == null) {
			ticCoreLogMainService = (ITicCoreLogMainService) SpringBeanUtil
					.getBean("ticCoreLogMainService");
		}
		return ticCoreLogMainService;
	}

	FastjsonProvider jsonProvider=new FastjsonProvider();//
	
	/**
	 * script中表示变量或函数的前缀字符串
	 */
	private static final String SCRIPT_VARFLAG_LEFT = "$";

	/**
	 * script中表示变量或函数的后缀字符串
	 */
	private static final String SCRIPT_VARFLAG_RIGHT = "$";

	/**
	 * script中表示函数的前缀字符串
	 */
	private static final char SCRIPT_FUNFLAG_LEFT= '(';

	/**
	 * script中表示函数的后缀字符串
	 */
	private static final char SCRIPT_FUNFLAG_RIGHT =')';

	/**
	 * script中函数标志
	 */
	private static final String SCRIPT_FUNFLAG="$(";

	/**
	 * 执行映射任务
	 * 
	 * @param ticRestSyncId
	 *            rest定时任务类id
	 * @throws Exception
	 */
	@Override
    public void executeFuncByTask(TicCoreSyncJob ticCoreSyncJob, TicCoreSyncTempFunc tempFunc, TicCoreFuncBase ticBase) throws Exception {

		Date startDate = new Date();
		Date lastTime = tempFunc.getFdLastDate();

		//查询Rest函数
		String funcId=tempFunc.getFdFuncBaseId();
		if(StringUtil.isNull(funcId)){
			return;
		}
		if(ticBase==null){
			return;
		}
		TicRestMain ticRestMain = (TicRestMain)ticBase;
		String fdRestStr = tempFunc.getFdMappConfig();
		if (StringUtil.isNull(fdRestStr)) {
			return;
		}
		JSONObject jsonMapping = JSONObject.parseObject(fdRestStr, Feature.OrderedField);
		JSONObject jsonParam=new JSONObject(true);
		JSONObject header=new JSONObject(true);
		JSONObject url=new JSONObject(true);
		JSONObject body=new JSONObject(true);

		//生成URL
		RecursionUtil.generaJSONData((JSONArray)jsonMapping.get("url"),url);

		//获取header
		//RecursionUtil.generaJSONData((JSONArray)jsonMapping.get("url"),url);
		JSONObject req = null;
		if(StringUtil.isNull(ticRestMain.getFdReqParam())){
			return;
		}
		req =  JSONObject.parseObject(ticRestMain.getFdReqParam(),Feature.OrderedField);
		//生成header
		RecursionUtil.generaJSONData((JSONArray)req.get("header"),header);
		//生成Body
		RecursionUtil.generaJSONData((JSONArray)jsonMapping.get("body"),body);
		
		//生成返回映射信息
		Map <String,JSONObject> sysncMap=new HashMap<String,JSONObject>(); 
		RecursionUtil.generaReturn((JSONArray)jsonMapping.get("return"),sysncMap,"","");
		
		if(sysncMap.size()==0){
			return;
		}
		
		jsonParam.put("url", url);
		jsonParam.put("body", body);
		jsonParam.put("header", header);
		// 数据源
		CompDbcp compDbcp = tempFunc.getFdCompDbcp();
		DataSet dataSet = null;
		ResultSet rs = null;
		TicCoreLogMain ticLog = new TicCoreLogMain();
		try {
			ticLog.setFdAppType(ticRestMain.getFdAppType());
			ITicCoreLogManageService ticCoreLogManageService = (ITicCoreLogManageService) SpringBeanUtil
					.getBean("ticCoreLogManageService");
			HQLInfo selectLogTypeHql = new HQLInfo();
			selectLogTypeHql.setSelectBlock("fdLogType");
			Integer logType = 2;
			Integer result = (Integer) ticCoreLogManageService.findFirstOne(selectLogTypeHql);
			if (result != null) {
				logType = result;
			}

			// 日志记录
			ticLog.setFdLogType(ticRestMain.getFdFuncType());
			ticLog.setFuncName(ticRestMain.getFdName());
			ticLog.setFdType(logType);
			ticLog.setFdStartTime(new Date());
			ticLog.setFuncId(ticRestMain.getFdId());
			ticLog.setFdUrl(ticRestMain.getFdReqURL());
			ticLog.setFdImportParOri(JSONObject.toJSONString(jsonParam));
			ticLog.setFdPoolName(ticCoreSyncJob.getFdSubject()+ "[任务同步]");
			ticLog.setFdExecSource("5");//同步任务
			IRestDataExecutor rde = (IRestDataExecutor) SpringBeanUtil
					.getBean("restDispatcherExecutor");
			String returnStr = rde.doSyncRest(
					JSONObject.toJSONString(jsonParam), ticRestMain, ticLog);
			if(logger.isDebugEnabled()){
				logger.debug("返回数据"+returnStr);
			}
			if(StringUtil.isNull(returnStr)){
				return;
			}
			//返回的数据
			JSONObject returnData=JSONObject.parseObject(returnStr,Feature.OrderedField);
			if(returnData.containsKey("out") && returnData.get("out")!=null && returnData.get("out") instanceof JSONObject){
				returnData=(JSONObject)returnData.get("out");
			}
			ticLog.setFdExportParOri(JSONObject.toJSONString(returnData)); 
			dataSet = new DataSet(compDbcp.getFdName());
			Connection conn = dataSet.getConnection();
			// 批量操作开始事务
			conn.setAutoCommit(false);
			PreparedStatement ps = null;
			Statement stmt = null;
			//根据同步方式来配置
			//根据同步的几张表来同步数据
			for(String nearParentArrayNode:sysncMap.keySet()){
				//获取同步信息,一个对象数组为维度，对应一张表
	        	JSONObject sysTableLine =sysncMap.get(nearParentArrayNode);
	        	JSONObject sysncJson=sysTableLine.getJSONObject("sysncJson");
	            //改表对应的数据库字段
				Map<String, String> sqlMap = new HashMap<String, String>();
				sqlMap.put("syncType", sysncJson.getString("syncType"));
				List<Map<String, Object>> columnValueList = new ArrayList<Map<String, Object>>();
				//获取当前对象数组的的数据
				Object objData=jsonProvider.getValue(returnData, nearParentArrayNode);
				if(objData==null || !(objData instanceof JSONArray)){
					continue;
				}
				JSONArray returnLineData=(JSONArray)objData;
				if(returnLineData.isEmpty()){//数据为空，则不同步
					continue;
				}
				//组装要插入的值，columnValueList的一个元素存放一条数据库记录
				getDetailExecute(nearParentArrayNode,returnLineData,sysTableLine, sqlMap,columnValueList);
				String columnKey =sysncJson.getString("key");//同步的key
				// 如果是空数据，那么跳过
				if (sqlMap.containsKey("continue")) {
					return;
				}
				if (!sqlMap.containsKey("syncType")) {
					return;
				}
				short syncType = Short.parseShort(sqlMap.get("syncType"));
				if (sqlMap.containsKey("deleteSql")) {
					ps = conn.prepareStatement(sqlMap.get("deleteSql"));
					ps.execute();
				}
				/*
				 * [if] 增量(插入前删除) [else] 不是全量的话，那么就是增量，增量(时间戳)，增量(条件删除)中的一种
				 */
				if (syncType == SYNC_INCR_BEFORE_DEL) {
					// 增量（插入前删除）
					String selectSql = sqlMap.get("selectSql");
					if (StringUtil.isNotNull(selectSql)) {
						// 预编译并执行查询语句，查出相同的用来修改（增量方式）
						stmt = conn.createStatement(
								ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
						rs = stmt.executeQuery(selectSql);
						while (rs.next()) {
							String keyValue = String.valueOf(rs
									.getObject(columnKey));
							for (Iterator<Map<String, Object>> it = columnValueList
									.iterator(); it.hasNext();) {
								Map<String, Object> tempMap = it.next();
								// 比较KEY列值，相同的进行删除
								if (keyValue.equals(tempMap.get(columnKey))) {
									rs.deleteRow();
									break;
								}
							}
						}
					}
				} else if (syncType != SYNC_FULL) {
					// 增量，增量(时间戳)，增量(条件删除)
					String selectSql = sqlMap.get("selectSql");
					if (StringUtil.isNotNull(selectSql)) {
						// 执行查询语句，查出相同的用来修改（增量方式）
						stmt = conn.createStatement(
								ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
						rs = stmt.executeQuery(selectSql);
						while (rs.next()) {
							String keyValue = String.valueOf(rs
									.getObject(columnKey));
							for (Iterator<Map<String, Object>> it = columnValueList
									.iterator(); it.hasNext();) {
								Map<String, Object> tempMap = it.next();
								// 比较KEY列值，修改语句执行（此Map为TreeMap，有序的），并移除原List数据
								if (keyValue.equals(tempMap.get(columnKey))) {
									// 移除原Lisst数据
									it.remove();
									for (String key : tempMap.keySet()) {
										// KEY则跳过，因为修改语句是根据KEY来的
										if (key.equals(columnKey)) {
											continue;
										}
										if (rs.findColumn(key) > 0) {
											rs.updateObject(key, tempMap
													.get(key));
										}
									}
									rs.updateRow();
									break;
								}
							}
						}
					}
				}

				// 插入操作，所有同步方式都可能需要做的
				if (columnValueList.size() > 0) {
					String addSql = sqlMap.get("addSql");
					ps = conn.prepareStatement(addSql);
					int len=columnValueList.size();
					for(int j=1;j<=len;j++){
						Map<String, Object> columnMap=columnValueList.get(j-1);
						int i = 1;
						for (Object value : columnMap.values()) {
							ps.setObject(i, value);
							i++;
						}
						ps.addBatch(); //插入代码打包，等一定量后再一起插入
						if((j%1000==0 && j>1) || (j==len)){//每1000次提交一次或者最后提交一次 
							ps.executeBatch();
							conn.commit(); 
							ps.clearBatch();
							if(j<len){
								conn.setAutoCommit(false);// 开始事务 
							}  
						}
					}
					ps.executeBatch();
					conn.commit(); 
				}
			}
			dataSet.commit();
			ticLog.setFdIsErr(TicCoreLogConstant.TIC_CORE_LOG_TYPE_SUCCESS);
			ticLog.setFdMessages("成功日志：ticSoapSyncUniteQuartzService.method();执行");
		} catch (Exception e) {
			ticLog.setFdIsErr(TicCoreLogConstant.TIC_CORE_LOG_TYPE_ERROR);
			ticLog.setFdMessages("异常日志：ticSoapSyncUniteQuartzService.method();执行错误为："+TicCommonUtil.getExceptionToString(e));
		} finally {
			if (dataSet != null) {
				dataSet.close();
			}
			if (rs != null) {
				rs.close();
			}
			
		}
		ticLog.setFdEndTime(new Date());
		// 修改最后执行时间
		tempFunc.setFdLastDate(new Date());
		//ticSoapSyncTempFuncService.update(tempFunc);
		getTicCoreLogMainService().saveTicCoreLogMain(ticLog);

	}

	/**
	 * 组装数据库执行语句
	 * 
	 * @param nodeList
	 * @throws SQLException
	 * @throws DateParseException
	 */
	private String getDetailExecute(String nearParentArrayNode,JSONArray returnLineData,JSONObject sysTableLine,
			Map<String, String> sqlMap,
			List<Map<String, Object>> columnValueList) throws Exception {
		        JSONObject  sysncJson=sysTableLine.getJSONObject("sysncJson");
				if (sysncJson.containsKey("fdSyncTable")) {
					// 明细表操作开始
					String syncTable = sysncJson.getString("fdSyncTable");
					String syncType = sysncJson.getString("syncType");
					String columnKey = sysncJson.getString("key");
					String syncDateTimeColumn = sysncJson
							.getString("syncType_date");
					String fdDelConditionName = sysncJson.getString("fdDelConditionName");
					// 非全量同步，必须选择KEY
					if (Short.parseShort(syncType) != SYNC_FULL
							&& StringUtil.isNull(columnKey)) {
						new Exception("同步失败，表" + syncTable + "的非全量方式同步的Key为必选");
					}
					//TreeMap<String,Object> columnMap = getColumnMap(modelColunToDbColumn, 
						//	syncDateTimeColumn);
					// 为数据库表中列的容器装值
					setColumnValueList(nearParentArrayNode,returnLineData,sysTableLine,columnValueList,
							syncDateTimeColumn);
					if (columnValueList.isEmpty()) {
						sqlMap.put("continue", "continue");
						return null;
					}
					// 设置插入语句
					String addSql = setAddSql(syncTable, columnValueList.get(0));
					String deleteSql = "";
					String selectSql = "";
					switch (Short.parseShort(syncType)) {
					case SYNC_INCR:
						// 增量
						selectSql = setSelectSql(syncTable, columnKey,
								columnValueList);
						sqlMap.put("selectSql", selectSql);
						break;
					case SYNC_FULL:
						// 全量
						deleteSql = "delete from " + syncTable;
						if (StringUtil.isNotNull(addSql)) {
							sqlMap.put("deleteSql", deleteSql);
						}
						break;
					case SYNC_INCR_DATE:
						// 增量(时间戳)
						if (StringUtil.isNotNull(syncDateTimeColumn)) {
							selectSql = setSelectSql(syncTable, columnKey,
									columnValueList);
							// 返回时间戳列
							sqlMap.put("selectSql", selectSql);
						} else {
							new Exception("同步失败，增量(时间戳)方式未选择时间戳");
						}
						break;
					case SYNC_INCR_BEFORE_DEL:
						// 增量(插入前删除)
						selectSql = setSelectSql(syncTable, columnKey,
								columnValueList);
						if (StringUtil.isNotNull(selectSql)) {
							sqlMap.put("selectSql", selectSql);
						}
						break;
					case SYNC_INCR_CONDITION_DEL:
						// 增量(条件删除)
						deleteSql = "delete from " + syncTable;
						selectSql = setSelectSql(syncTable, columnKey,
								columnValueList);
						if (StringUtil.isNotNull(selectSql)) {
							String fdDeleteExpression="";
							if (StringUtil.isNotNull(fdDelConditionName)) {
								fdDeleteExpression= HtmlUtils
										.htmlUnescape(fdDelConditionName);
								// 设置删除表达式，解析替换fdDeleteExpression字段
								setFdDeleteExpression(null,fdDeleteExpression);
								deleteSql += " where " + fdDeleteExpression;
								sqlMap.put("deleteSql", deleteSql);
							}
							sqlMap.put("selectSql", selectSql);
						}
						break;
					}
					sqlMap.put("syncType", syncType);
					sqlMap.put("addSql", addSql);
					return columnKey;
				}
		return null;
	}

	/**
	 * 解析删除条件表达式
	 * 
	 * @param nodeList
	 * @param syncTypeDateTime
	 * @return
	 */
	private void setFdDeleteExpression(NodeList nodeList,String fdDeleteExpression) {/*
		for (int i = 0, len = nodeList.getLength(); i < len; i++) {
			Node curNode = nodeList.item(i);
			if (Node.ELEMENT_NODE != curNode.getNodeType()) {
				continue;
			}
			// 获取对应注释节点
			Node commentNode = findCommentNode(curNode);
			if (null == commentNode) {
				// 还有子节点，那么递归
				if (curNode.hasChildNodes()) {
					setFdDeleteExpression(curNode.getChildNodes(),fdDeleteExpression);
				}
				continue;
			}
			String commentStr = commentNode.getTextContent();
			String splitStr = "|erp_web=";
			if (commentStr.lastIndexOf(splitStr) > 0) {
				int start = commentStr.lastIndexOf(splitStr)
						+ splitStr.length();
				String result = commentStr.substring(start);
				JSONObject commentJsonObj = JSONObject.parseObject(result);
				if (commentJsonObj.containsKey("fdSyncTable")) {
					// 明细表包含明细表那么跳过
					continue;
				} else if (commentJsonObj.containsKey("mappingValue")) {
					// 证明有映射，那么开始数据库sql语句拼串
					if (fdDeleteExpression.contains("$" + curNode.getNodeName()
					+ "$")) {
						String mappingValue = commentJsonObj
								.getString("mappingValue");
						fdDeleteExpression = fdDeleteExpression.replaceAll(
								"\\$" + curNode.getNodeName() + "\\$",
								mappingValue);
					}
				}
			}
			// 还有子节点，那么递归
			if (curNode.hasChildNodes()) {
				setFdDeleteExpression(curNode.getChildNodes(),fdDeleteExpression);
			}
		}
		return null;
	*/}

	private String setAddSql(String syncTable, Map<String, Object> map) {
		String addSql = "";
		String setSql = "";
		for (String column : map.keySet()) {
			addSql += column + ",";
			setSql += "?,";
		}
		if (StringUtil.isNotNull(setSql)) {
			// 截取最后一个逗号，再补上括号
			addSql = addSql.substring(0, addSql.length() - 1);
			setSql = setSql.substring(0, setSql.length() - 1);
			return "insert into " + syncTable + "(" + addSql + ")" + "values("
			+ setSql + ")";
		} else {
			return null;
		}
	}

	/**
	 * @deprecated
	 * @param syncTable
	 * @param columnKey
	 * @param map
	 * @return
	 */
	private String setUpdateSql(String syncTable, String columnKey,
			Map<String, String> map) {
		String setSql = "";
		for (String column : map.keySet()) {
			if (columnKey.equals(column)) {
				continue;
			}
			setSql += column + "=?,";
		}
		if (StringUtil.isNotNull(setSql)) {
			// 截取最后一个逗号，再补上括号
			setSql = setSql.substring(0, setSql.length() - 1);
			return "update " + syncTable + " set " + setSql + " where "
			+ columnKey + "=?";
		} else {
			return null;
		}
	}

	/**
	 * 拼串查询是否存在记录的sql语句
	 * 
	 * @param syncTable
	 * @param columnKey
	 * @param columnValueList
	 * @return
	 */
	private String setSelectSql(String syncTable, String columnKey,
			List<Map<String, Object>> columnValueList) {
		String selectBlock = "";
		StringBuffer whereBlockBuf = new StringBuffer("");
		for (int i = 0, len = columnValueList.size(); i < len; i++) {
			Map<String, Object> map = columnValueList.get(i);
			if (i == 0) {
				// 查询的字段
				for (String key : map.keySet()) {
					selectBlock += key + ",";
				}
			}
			Object keyValue = map.get(columnKey);
			if (i == len - 1) {
				whereBlockBuf.append(columnKey + "='" + keyValue + "' ");
			} else {
				whereBlockBuf.append(columnKey + "='" + keyValue + "' or ");
			}
		}
		if (StringUtil.isNotNull(whereBlockBuf.toString())) {
			selectBlock = selectBlock.substring(0, selectBlock.length() - 1);
			return "select " + selectBlock + " from " + syncTable + " where "
			+ whereBlockBuf.toString();
		} else {
			return null;
		}
	}

	/**
	 * 遍历返回数据，组装sql语句
	 * 
	 * @param nodeList
	 * @param columnValueList
	 * @throws SQLException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	private void setColumnValueList(String nearParentArrayNode,JSONArray returnLineData,JSONObject sysTableLine,
			List<Map<String, Object>> columnValueList, String syncDateTimeColumn)
					throws SQLException, ParseException {
		// 遍历多行明细表第一层子节点（多少个子节点决定着多少条记录）
		Map<String,JSONObject> modelColunToDbColumn =(Map<String,JSONObject>)sysTableLine.get("modelColunToDbColumn");
		JSONObject  sysncJson=sysTableLine.getJSONObject("sysncJson");
		String generateFdId=sysncJson.getString("generateFdId");
		// 遍历多行明细表第一层子节点（多少个子节点决定着多少条记录）
		for (int i = 0; i < returnLineData.size(); i++) {
			JSONObject json=returnLineData.getJSONObject(i);
			//根据数据库组装值
			TreeMap<String, Object> temp = new TreeMap<String, Object>();
			for (String xPath:modelColunToDbColumn.keySet()) {
				// 遍历明细表的一行记录进行设置(递归遍历)
				JSONObject dataMap=modelColunToDbColumn.get(xPath);
				//数据库的值
				Object value=null;
				try {
					if(dataMap.containsKey("formulaValue")&& StringUtil.isNotNull(dataMap.getString("formulaValue"))){
						FormulaParserByJS parserByJS=FormulaParserByJS.getInstance(json, new FastjsonProvider(),null);
						String formulaValue=dataMap.getString("formulaValue");
						value = parserByJS.parseValueScript(dealFormulaValue(formulaValue,nearParentArrayNode));
					}else{
						String name=xPath.substring(xPath.indexOf(nearParentArrayNode)+nearParentArrayNode.length()+1,xPath.length());
						value = jsonProvider.getValue(json, name);
				    }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String commentvalue=dataMap.getString("commentvalue");
				temp.put(commentvalue,value);

		    }
			if (StringUtil.isNotNull(generateFdId)) {
				temp.put(generateFdId, IDGenerator
							.generateID());
			}
			columnValueList.add(temp);
		}
	}

	//公式中过滤掉数据对象的跟路径nearParentArrayNode,获得相对路径
	public String dealFormulaValue(String formulaValue,String nearParentArrayNode){
		StringBuffer leftScript = new StringBuffer();
        if(StringUtil.isNull(formulaValue) ||StringUtil.isNull(nearParentArrayNode)){
        	return formulaValue;
        }
		try {
			// 右边未解释的部分
			String rightScript = formulaValue.trim();
			// 下面代码将解释formulaValue代码
			int k=0;
			for (int index = rightScript.indexOf(SCRIPT_VARFLAG_LEFT); index > -1; index = rightScript
					.indexOf(SCRIPT_VARFLAG_LEFT)) {
				int nxtIndex = rightScript.indexOf(SCRIPT_VARFLAG_RIGHT,
						index + 1);
				// index为开始点，nxtIndex为结束点，无结束点则退出循环
				if (nxtIndex == -1) {
					break;
				}
				String varName = rightScript.substring(index + 1, nxtIndex);
				leftScript.append(rightScript.substring(0, index));
				rightScript = rightScript.substring(nxtIndex + 1);
				Object obj=null;
				if (rightScript.length() > 0 && rightScript.charAt(0) == SCRIPT_FUNFLAG_LEFT) {//是函数方式
					// 若变量接着“(”，则认为是函数
					//拿到类名
					//拿到方法名字
					int next=rightScript.indexOf(SCRIPT_FUNFLAG_RIGHT);
					//拿到参数
					String paramStr=rightScript.substring(1,next);
					leftScript.append(SCRIPT_FUNFLAG)
					.append(dealFormulaValue(paramStr,nearParentArrayNode))
					.append(SCRIPT_FUNFLAG_RIGHT);
					rightScript=rightScript.substring(next+1);
				} else {
					// 不接“(”，说明是一个变量
					String name=varName.substring(varName.indexOf(nearParentArrayNode)+nearParentArrayNode.length()+1,varName.length());
					leftScript.append(SCRIPT_VARFLAG_LEFT).append(name)
					.append(SCRIPT_VARFLAG_RIGHT);
				}
			}
			return leftScript.append(rightScript).toString();
		} catch (Exception e) {
			return "";
		} finally {
		}
	}

}