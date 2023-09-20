package com.landray.kmss.tic.soap.sync.service.spring;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.httpclient.util.DateParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.landray.kmss.component.dbop.ds.DataSet;
import com.landray.kmss.component.dbop.model.CompDbcp;
import com.landray.kmss.sys.formula.parser.FormulaParserByJS;
import com.landray.kmss.sys.metadata.exception.KmssUnExpectTypeException;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.core.common.service.ITicCoreFuncBaseService;
import com.landray.kmss.tic.core.log.constant.TicCoreLogConstant;
import com.landray.kmss.tic.core.log.interfaces.ITicCoreLogInterface;
import com.landray.kmss.tic.core.log.service.ITicCoreLogMainService;
import com.landray.kmss.tic.core.mapping.constant.Constant;
import com.landray.kmss.tic.core.sync.model.TicCoreSyncJob;
import com.landray.kmss.tic.core.sync.model.TicCoreSyncTempFunc;
import com.landray.kmss.tic.core.sync.service.ITicCoreSyncUniteQuartzService;
import com.landray.kmss.tic.core.util.DomUtil;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.util.header.SoapInfo;
import com.landray.kmss.tic.soap.sync.service.ITicSoapSyncJobService;
import com.landray.kmss.tic.soap.sync.service.ITicSoapSyncTempFuncService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;


/**
 * soap定时任务统一服务
 * 
 * @author zhangtian
 * 
 * @version 2012-2-15
 */

public class TicSoapSyncUniteQuartzService implements  ITicCoreSyncUniteQuartzService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TicSoapSyncUniteQuartzService.class);
	private Date lastTime = null;
	private String fdDeleteExpression = null;
	private ITicSoapSyncJobService ticSoapSyncJobService;
	private ITicSoap ticSoap;
	private ITicSoapSyncTempFuncService ticSoapSyncTempFuncService;
	private ITicCoreLogInterface ticCoreLogInterface;
	
	private ITicCoreFuncBaseService ticCoreFuncBaseService=(ITicCoreFuncBaseService)SpringBeanUtil
			.getBean("ticCoreFuncBaseService");
	
	private ITicCoreLogMainService ticCoreLogMainService = (ITicCoreLogMainService) SpringBeanUtil
			.getBean("ticCoreLogMainService");
		
	/**
	  * script中表示变量或函数的前缀字符串
	  */
	private static final String SCRIPT_VARFLAG_LEFT = "$";

	/**
	 * script中表示变量或函数的后缀字符串
	 */
	private static final String SCRIPT_VARFLAG_RIGHT = "$";


	public void setTicSoapSyncJobService(
			ITicSoapSyncJobService ticSoapSyncJobService) {
		this.ticSoapSyncJobService = ticSoapSyncJobService;
	}

	public void setTicSoap(ITicSoap ticSoap) {
		this.ticSoap = ticSoap;
	}

	public void setTicSoapSyncTempFuncService(
			ITicSoapSyncTempFuncService ticSoapSyncTempFuncService) {
		this.ticSoapSyncTempFuncService = ticSoapSyncTempFuncService;
	}

	public void setTicCoreLogInterface(
			ITicCoreLogInterface ticCoreLogInterface) {
		this.ticCoreLogInterface = ticCoreLogInterface;
	}
	
	public static void main(String[] args) {
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <web ID=\"14ca1604e7bda66d72ddf2f4cb9ad127\"> 	<Input> 		<!--{\"nodeEnable\":\"checked\"}|erp_web={\"nodeEnable\":\"checked\",\"ctype\":\"\",\"dataType\":\"\"}--> 		<soapenv:Envelope xmlns:out=\"http://out.webservice.organization.sys.kmss.landray.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"> 			<!--{\"nodeEnable\":\"checked\"}|erp_web={\"nodeEnable\":\"checked\",\"ctype\":\"\",\"title\":\"\",\"dataType\":\"\"}--><soapenv:Header/> 			<!--{\"nodeEnable\":\"checked\"}|erp_web={\"nodeEnable\":\"checked\",\"ctype\":\"\",\"dataType\":\"\"}--><soapenv:Body> 				<!--{\"nodeEnable\":\"checked\"}|erp_web={\"nodeEnable\":\"checked\",\"ctype\":\"\",\"dataType\":\"\"}--><out:getRoleInfo> 					<!--Optional:|erp_web={\"nodeEnable\":\"checked\",\"ctype\":\"\",\"dataType\":\"\"}--> 					<arg0> 						<!--Optional:|erp_web={\"nodeEnable\":\"checked\",\"ctype\":\"\",\"title\":\"\",\"dataType\":\"\",\"inputSelect\":\"1\",\"inputText\":\"\",\"mark\":\"\"}--> 						<returnOrgType>?</returnOrgType> 						<!--Optional:|erp_web={\"nodeEnable\":\"checked\",\"ctype\":\"\",\"title\":\"\",\"dataType\":\"\",\"inputSelect\":\"1\",\"inputText\":\"\",\"mark\":\"\"}--> 						<beginTimeStamp>?</beginTimeStamp> 						<!--{\"nodeEnable\":\"checked\"}|erp_web={\"nodeEnable\":\"checked\",\"ctype\":\"\",\"title\":\"\",\"dataType\":\"\",\"inputSelect\":\"1\",\"inputText\":\"\",\"mark\":\"\"}--><count>?</count> 					</arg0> 				</out:getRoleInfo> 			</soapenv:Body> 		</soapenv:Envelope> 	</Input> 	<Output> 		<!--{\"ctype\":\"\"}--> 		 	<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">    <!--{\"ctype\":\"\"}--><soap:Body>       <!--{\"ctype\":\"\"}--><ns1:getRoleInfoResponse xmlns:ns1=\"http://out.webservice.organization.sys.kmss.landray.com/\">          <!--Optional:|erp_web={\"ctype\":\"明细表\",\"syncType\":\"2\",\"syncType_date\":\"\",\"fdDelConditionName\":\"\",\"fdSyncTable\":\"test\",\"key\":\"id\",\"generateFdId\":\"id\"}--><return>             <!--{\"disp\":\"\"}|erp_web={\"disp\":\"\",\"ctype\":\"\",\"title\":\"数目\",\"isSuccess\":\"\",\"isFail\":\"\",\"mappingValue\":\"field1\",\"mark\":\"\"}--><count>3</count>             <!--Optional:|erp_web={\"disp\":\"\",\"ctype\":\"\",\"title\":\"\",\"isSuccess\":\"\",\"isFail\":\"\",\"mappingValue\":\"\",\"mark\":\"\"}--><message>[{\"id\":\"14e99a2fb275f76d7f269234daf9f085\",\"lunid\":\"14e99a2fb275f76d7f269234daf9f085\",\"name\":\"通用岗位1\",\"type\":null,\"isAvailable\":true,\"hierarchyId\":\"x14e99a2fb275f76d7f269234daf9f085x\",\"alterTime\":\"2015-07-17 09:43:01.680\",\"plugin\":\"sysOrgPlugin_Leader\",\"parameter\":\"type=excludeme&amp;level=0\",\"isMultiple\":false,\"rtnValue\":\"12\"},{\"id\":\"14e9abd679f441d278260ab442ca23cb\",\"lunid\":\"14e9abd679f441d278260ab442ca23cb\",\"name\":\"角色关系1\",\"type\":null,\"isAvailable\":true,\"hierarchyId\":\"x14e9abd679f441d278260ab442ca23cbx\",\"alterTime\":\"2015-07-17 14:47:07.627\",\"plugin\":\"sysOrgRolePluginService\",\"parameter\":\"type=0&amp;level=-1&amp;location=0\",\"isMultiple\":false,\"rtnValue\":\"12\",\"roleConf\":\"14de1bdfbbd3298dbf47c594f17b57c9\"},{\"id\":\"14ed8bc59e5995916e012f74606bb1c7\",\"lunid\":\"14ed8bc59e5995916e012f74606bb1c7\",\"name\":\"角色关系2\",\"type\":null,\"isAvailable\":true,\"hierarchyId\":\"x14ed8bc59e5995916e012f74606bb1c7x\",\"alterTime\":\"2015-07-29 15:38:51.903\",\"plugin\":\"organizationDialogList\",\"parameter\":\"orgType=63\",\"isMultiple\":true,\"rtnValue\":\"15\",\"roleConf\":\"14de1bdfbbd3298dbf47c594f17b57c9\"}]</message>             <!--{\"disp\":\"\"}|erp_web={\"disp\":\"\",\"ctype\":\"\",\"title\":\"\",\"isSuccess\":\"\",\"isFail\":\"\",\"mappingValue\":\"\",\"mark\":\"\"}--><returnState>2</returnState>          </return>       </ns1:getRoleInfoResponse>    </soap:Body> </soap:Envelope> </Output> 	 </web> ";
		String xpath = "//Envelope/Body/getRoleInfoResponse/return";
		try {
			NodeList list = DomUtil.selectNode(xpath, DomUtil.stringToDoc(s));
			System.out.println(list.getLength());
		} catch (XPathExpressionException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行映射任务
	 * 
	 * @throws Exception
	 */
	@Override
    public void executeFuncByTask(TicCoreSyncJob ticCoreSyncJob, TicCoreSyncTempFunc tempFunc, TicCoreFuncBase ticBase) throws Exception {

			Date startDate = new Date();
			lastTime = tempFunc.getFdLastDate();
			
			//查询Soap函数
			String funcId=tempFunc.getFdFuncBaseId();
			if(StringUtil.isNull(funcId)){
				return;
			}
	        if(ticBase==null){
	        	return;
	        }
			TicSoapMain ticSoapMain = (TicSoapMain)ticBase;
			/*TicSoapMain ticSoapMain=(TicSoapMain)ticSoapMainService.findByPrimaryKey(funcId);*/
			String fdSoapXml = tempFunc.getFdMappConfig();
			if (StringUtil.isNull(fdSoapXml)) {
				return;
			}

			// 数据源
			CompDbcp compDbcp = tempFunc.getFdCompDbcp();
			DataSet dataSet = null;
			ResultSet rs = null;
		Document doc = null;
		Document backDoc = null;
			try {
			doc = DomUtil.stringToDoc(fdSoapXml);
			NodeList InNodeList = DomUtil.selectNode("/web/Input", doc);
			// 设置节点值的方法
			setNodeValue(InNodeList, lastTime);
			SoapInfo soapInfo = new SoapInfo();
			soapInfo.setRequestDocument(doc);
			soapInfo.setTicSoapMain(ticSoapMain);
			backDoc = ticSoap.inputToOutputDocument(soapInfo);

				dataSet = new DataSet(compDbcp.getFdName());
				Connection conn = dataSet.getConnection();
				// 批量操作开始事务
				conn.setAutoCommit(false);
				String fdSyncTableXpath = tempFunc.getFdSyncTableXpath();
				if("取会计科目".equals(ticCoreSyncJob.getFdSubject())){
					fdSyncTableXpath ="//Envelope/Body/getAccountResponse/getAccountReturn/getAccountReturn";					
			    }else if("取组织机构详细信息(含成本中心、利润中心等)".equals(ticCoreSyncJob.getFdSubject())){
				   fdSyncTableXpath="//Envelope/Body/getOrgResponse/getOrgReturn/getOrgReturn";;
				}
				String[] fdSyncTableXpaths = fdSyncTableXpath.split(",");
				for (String xpath : fdSyncTableXpaths) {
					if (StringUtil.isNull(xpath)) {
						continue;
					}
					PreparedStatement ps = null;
					Statement stmt = null;
					NodeList outNodeList = DomUtil.selectNode(xpath, backDoc);
					Map<String, String> sqlMap = new HashMap<String, String>();
					List<Map<String, Object>> columnValueList = new ArrayList<Map<String, Object>>();
					String columnKey =null;
					if("取会计科目".equals(ticCoreSyncJob.getFdSubject())){
						columnKey=dealAccount(outNodeList,sqlMap,columnValueList);
					}else if("取组织机构详细信息(含成本中心、利润中心等)".equals(ticCoreSyncJob.getFdSubject())){
						columnKey=dealOrg(outNodeList,sqlMap,columnValueList);
					}else{
						NodeList outNodeList2 = DomUtil.selectNode(xpath, DomUtil.stringToDoc(fdSoapXml));
						columnKey = getDetailExecute(outNodeList,outNodeList2, sqlMap,
									columnValueList);
					}
					// 如果是空数据，那么跳过
					if (sqlMap.containsKey("continue")) {
						continue;
					}
					if (!sqlMap.containsKey("syncType")) {
						continue;
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
			// 修改最后执行时间
			// tempFunc.setFdLastDate(new Date());
			ticSoapSyncTempFuncService.update(tempFunc);
			ticCoreLogInterface.saveLogMain(ticCoreSyncJob.getFdSubject()
					+ "[任务同步]", Constant.FD_TYPE_SOAP, startDate,
					doc == null ? "" : DomUtil.DocToString(doc),
					backDoc == null ? "" : DomUtil.DocToString(backDoc),
					"成功日志：ticSoapSyncUniteQuartzService.method();执行",
					TicCoreLogConstant.TIC_CORE_LOG_TYPE_SUCCESS,
					ticSoapMain.getFdAppType());
			} catch (Exception e) {
				ticCoreLogInterface.saveLogMain(ticCoreSyncJob.getFdSubject()
					+ "[任务同步]", Constant.FD_TYPE_SOAP, startDate,
					doc == null ? "" : DomUtil.DocToString(doc),
					backDoc == null ? "" : DomUtil.DocToString(backDoc),
						"异常日志：ticSoapSyncUniteQuartzService.method();执行错误为："
								+ e.toString(),
						TicCoreLogConstant.TIC_CORE_LOG_TYPE_ERROR,ticSoapMain.getFdAppType());//函数的fdAppType
				throw e;
			} finally {
				if (dataSet != null) {
					dataSet.close();
				}
				if (rs != null) {
					rs.close();
				}
			}

	}

	/**
	 * 组装数据库执行语句
	 * 
	 * @param nodeList
	 * @throws SQLException
	 * @throws DateParseException
	 */
	private String getDetailExecute(NodeList nodeList,NodeList nodeList2,
			Map<String, String> sqlMap,
			List<Map<String, Object>> columnValueList) throws Exception {
		for (int i = 0, len = nodeList.getLength(); i < len; i++) {
			Node curNode = nodeList.item(i);
			if (Node.ELEMENT_NODE != curNode.getNodeType()) {
				continue;
			}
			Node commentNode = findCommentNode(curNode);
			if (null == commentNode) {
				continue;
			}
			String commentStr = commentNode.getTextContent();
			String splitStr = "|erp_web=";
			if (commentStr.lastIndexOf(splitStr) > 0) {
				int start = commentStr.lastIndexOf(splitStr)
						+ splitStr.length();
				String result = commentStr.substring(start);
				JSONObject commentJsonObj = JSONObject.fromObject(result);
				if (commentJsonObj.containsKey("fdSyncTable")) {
					// 明细表操作开始
					String syncTable = commentJsonObj.getString("fdSyncTable");
					String syncType = commentJsonObj.getString("syncType");
					String columnKey = commentJsonObj.getString("key");
					String syncDateTimeColumn = commentJsonObj
							.getString("syncType_date");
					String fdDelConditionName = commentJsonObj
							.getString("fdDelConditionName");
					// 非全量同步，必须选择KEY
					if (Short.parseShort(syncType) != SYNC_FULL
							&& StringUtil.isNull(columnKey)) {
						new Exception("同步失败，表" + syncTable + "的非全量方式同步的Key为必选");
					}
					TreeMap<String,Object> columnMap = getColumnMap(nodeList2, 
							syncDateTimeColumn);
					// 为数据库表中列的容器装值
					setColumnValueList(nodeList, columnValueList,
							syncDateTimeColumn,columnMap);
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
							if (StringUtil.isNotNull(fdDelConditionName)) {
								fdDeleteExpression = HtmlUtils
										.htmlUnescape(fdDelConditionName);
								// 设置删除表达式，解析替换fdDeleteExpression字段
								setFdDeleteExpression(curNode.getChildNodes());
								deleteSql += " where " + fdDeleteExpression;
								sqlMap.put("deleteSql", deleteSql);
							} else {
								fdDeleteExpression = "";
							}
							sqlMap.put("selectSql", selectSql);
						}
						break;
					}
					sqlMap.put("syncType", syncType);
					sqlMap.put("addSql", addSql);
					return columnKey;
				}
			}
		}
		return null;
	}

	/**
	 * 解析删除条件表达式
	 * 
	 * @param nodeList
	 * @return
	 */
	private String setFdDeleteExpression(NodeList nodeList) {
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
					setFdDeleteExpression(curNode.getChildNodes());
				}
				continue;
			}
			String commentStr = commentNode.getTextContent();
			String splitStr = "|erp_web=";
			if (commentStr.lastIndexOf(splitStr) > 0) {
				int start = commentStr.lastIndexOf(splitStr)
						+ splitStr.length();
				String result = commentStr.substring(start);
				JSONObject commentJsonObj = JSONObject.fromObject(result);
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
				setFdDeleteExpression(curNode.getChildNodes());
			}
		}
		return null;
	}

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
	 * 获取原始的值映射
	 * @param nodeList
	 * @param syncDateTimeColumn
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	private TreeMap<String, Object> getColumnMap(NodeList nodeList, String syncDateTimeColumn)
			throws SQLException, ParseException {
		// 遍历多行明细表第一层子节点（多少个子节点决定着多少条记录）
		for (int i = 0, len = nodeList.getLength(); i < len; i++) {
			Node curNode = nodeList.item(i);
			if (Node.ELEMENT_NODE != curNode.getNodeType()) {
				continue;
			}
			Node commentNode = findCommentNode(curNode);
			if (null == commentNode) {
				continue;
			}
			String commentStr = commentNode.getTextContent();
			String splitStr = "|erp_web=";
			if (commentStr.lastIndexOf(splitStr) > 0) {
				int start = commentStr.lastIndexOf(splitStr)
						+ splitStr.length();
				String result = commentStr.substring(start);
				JSONObject commentJsonObj = JSONObject.fromObject(result);
				if (commentJsonObj.containsKey("fdSyncTable")) {
					TreeMap<String, Object> columnValueMap = new TreeMap<String, Object>();
					// 遍历明细表的一行记录进行设置(递归遍历)
					setColumnValueMap(curNode.getChildNodes(), columnValueMap,
							syncDateTimeColumn,true);
					for(String key:columnValueMap.keySet()){
						columnValueMap.put(key, null);
					}
					return columnValueMap;
				}
			}
		}
		return null;
	}
	
	
	private TreeMap<String,Object> cloneMap(TreeMap<String,Object> columnMap){
		TreeMap<String,Object> temp = new TreeMap<String,Object>();
		for(String key:columnMap.keySet()){
			temp.put(key, columnMap.get(key));
		}
		return temp;
	}

	/**
	 * 遍历多行明细表第一层节点
	 * 
	 * @param nodeList
	 * @param columnValueList
	 * @throws SQLException
	 * @throws ParseException
	 */
	private void setColumnValueList(NodeList nodeList,
			List<Map<String, Object>> columnValueList, String syncDateTimeColumn, TreeMap<String,Object> columnMap)
			throws SQLException, ParseException {
		// 遍历多行明细表第一层子节点（多少个子节点决定着多少条记录）
		for (int i = 0, len = nodeList.getLength(); i < len; i++) {
			Node curNode = nodeList.item(i);
			if (Node.ELEMENT_NODE != curNode.getNodeType()) {
				continue;
			}
			Node commentNode = findCommentNode(curNode);
			if (null == commentNode) {
				continue;
			}
			String commentStr = commentNode.getTextContent();
			String splitStr = "|erp_web=";
			if (commentStr.lastIndexOf(splitStr) > 0) {
				int start = commentStr.lastIndexOf(splitStr)
						+ splitStr.length();
				String result = commentStr.substring(start);
				JSONObject commentJsonObj = JSONObject.fromObject(result);
				if (commentJsonObj.containsKey("fdSyncTable")) {
					TreeMap<String, Object> columnValueMap = new TreeMap<String, Object>();
					// 遍历明细表的一行记录进行设置(递归遍历)
					setColumnValueMap(curNode.getChildNodes(), columnValueMap,
							syncDateTimeColumn,false);
					
					if (!columnValueMap.isEmpty()) {
						TreeMap<String, Object> temp = cloneMap(columnMap);
						for(String key:temp.keySet()){
							if(columnValueMap.containsKey(key)){
								temp.put(key, columnValueMap.get(key));
							}
						}
						String generateFdId = commentJsonObj
								.getString("generateFdId");
						if (StringUtil.isNotNull(generateFdId)) {
							temp.put(generateFdId, IDGenerator
									.generateID());
						}
						// 把一条记录装入List容器
						columnValueList.add(temp);
					}
				}
			}
		}
	}

	/**
	 * 设置列值的Map容器 （递归遍历）
	 * 
	 * @param nodeList
	 * @param columnValueMap
	 * @throws SQLException
	 * @throws ParseException
	 */
	private void setColumnValueMap(NodeList nodeList,
			Map<String, Object> columnValueMap, String syncDateTimeColumn,boolean flag)
			throws SQLException, ParseException {
		JSONObject contextData=null;
		String parentPath=null;
		FormulaParserByJS parser=null;
		if(!flag){
			//获取node节点构造的json对象
			parentPath=replace(nodeList.item(0).getParentNode().getNodeName()+".");//Node curNode
			contextData=buildJsonByNode(nodeList);//hejianhua d
			contextData.put("parentPath", parentPath);
			parser=FormulaParserByJS.getInstance(contextData);			
		}
		for (int i = 0, len = nodeList.getLength(); i < len; i++) {//hejianhua
			Node curNode = nodeList.item(i);
			if (Node.ELEMENT_NODE != curNode.getNodeType()) {
				continue;
			}
			Node commentNode = findCommentNode(curNode);
			if (null == commentNode) {
				// 有子节点继续递归
				if (curNode.hasChildNodes()) {
					setColumnValueMap(curNode.getChildNodes(), columnValueMap,
							syncDateTimeColumn,flag);
				}
				continue;
			}
			String commentStr = commentNode.getTextContent();
			String splitStr = "|erp_web=";
			if (commentStr.lastIndexOf(splitStr) > 0) {
				int start = commentStr.lastIndexOf(splitStr)
						+ splitStr.length();
				String result = commentStr.substring(start);
				JSONObject commentJsonObj = JSONObject.fromObject(result);
				if (commentJsonObj.containsKey("fdSyncTable")) {
					// 明细表包含明细表那么跳过
					continue;
				} else if (commentJsonObj.containsKey("mappingValue")) {
					// 证明有映射，那么开始数据库sql语句拼串
					Object value ="";
					String script=commentJsonObj.optString("ekpid");
                    String type=commentJsonObj.optString("ctype");
                    if(!flag && StringUtil.isNotNull(script)){
                    	value=parser.parseValueScript(replaceParent(script,parentPath));
                    }else{//没配置公式，后续考虑用type来转value
                    	if(StringUtil.isNotNull(type) && curNode.getTextContent()!=null
                    			&& !"dateTime".equals(type)){
                    		value =formatValue(curNode.getTextContent(), type);
                    	}else{
                    		value = curNode.getTextContent();
                    	}
                    }
					if (commentJsonObj.containsKey("ctype")
							&& "dateTime".equals(commentJsonObj
									.getString("ctype"))) {
						Date date = DateUtil.convertStringToDate((String)value,
								"yyyy-MM-dd'T'HH:mm:ss");
						// 比较时间戳
						if (lastTime != null
								&& syncDateTimeColumn.equals(curNode
										.getNodeName())
								&& date.compareTo(lastTime) <= 0) {
							columnValueMap.clear();
							return;
						}
						value = DateUtil.convertDateToString(date,
								"yyyy-MM-dd HH:mm:ss");
					}
					String mappingColumn = commentJsonObj
							.getString("mappingValue");
					if (StringUtil.isNotNull(mappingColumn)) {
						columnValueMap.put(mappingColumn, value);
					}
				}
			}
			// 有子节点继续递归
			if (curNode.hasChildNodes()) {
				setColumnValueMap(curNode.getChildNodes(), columnValueMap,
						syncDateTimeColumn,flag);
			}
		}
	}

	/**
	 * 从注释中拿出节点的值进行设置 (可递归方法)
	 * 
	 * @param nodeList
	 */
	private void setNodeValue(NodeList nodeList, Date lastTime)
			throws Exception {
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
					setNodeValue(curNode.getChildNodes(), lastTime);
				}
				continue;
			}
			String commentStr = commentNode.getTextContent();
			String splitStr = "|erp_web=";
			if (commentStr.lastIndexOf(splitStr) > 0) {
				int start = commentStr.lastIndexOf(splitStr)
						+ splitStr.length();
				String result = commentStr.substring(start);
				JSONObject commentJsonObj = JSONObject.fromObject(result);
				Document doc = curNode.getOwnerDocument();
				String textStr = "";
				if (commentJsonObj.containsKey("inputSelect")) {
					String inputSelect = commentJsonObj
							.getString("inputSelect");
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					if ("1".equals(inputSelect)) {
						textStr = commentJsonObj.getString("inputText");
					} else if ("2".equals(inputSelect)) {
						// 最后更新时间
						textStr = sdf.format(new Date());
					} else if ("3".equals(inputSelect)) {
						// 最后执行时间
						if (null != lastTime) {
							textStr = sdf.format(lastTime);
						} else {
							textStr = "";
						}
					}
					Text nodeText = doc.createTextNode(textStr);
					curNode.appendChild(nodeText);
				}
			}
			// 还有子节点，那么递归
			if (curNode.hasChildNodes()) {
				setNodeValue(curNode.getChildNodes(), lastTime);
			}
		}
	}

	/**
	 * 找上一个为注释节点
	 * 
	 * @param curNode
	 * @return
	 */
	private Node findCommentNode(Node curNode) {
		if (curNode != null) {
			Node preNode = curNode.getPreviousSibling();
			// 上一个节点就是尽头
			if (preNode == null) {
				return null;
			} else if (preNode.getNodeType() == Node.ELEMENT_NODE) {
				return null;
			} else if (preNode.getNodeType() == Node.COMMENT_NODE) {
				return preNode;
			} else {
				return findCommentNode(preNode);
			}
		}
		return null;
	}
	
	/**
	 * 设置列值的Map容器 （递归遍历）
	 * 
	 * @param nodeList
	 * @throws SQLException
	 * @throws ParseException
	 */
	private JSONObject buildJsonByNode(NodeList nodeList)
			throws SQLException, ParseException {
		JSONObject obj=new JSONObject();
		for(int i = 0, len = nodeList.getLength(); i < len; i++){
			Node curNode = nodeList.item(i);
			if (Node.ELEMENT_NODE != curNode.getNodeType()) {
				continue;
			}
			Node commentNode = findCommentNode(curNode);
			if (null == commentNode) {
				// 有子节点继续递归
				if (curNode.hasChildNodes()) {
					obj.put(replace(curNode.getNodeName()), buildJsonByNode(curNode.getChildNodes()));
				}
				continue;
			}
			obj.put(replace(curNode.getNodeName()),curNode.getTextContent());
		}
		return obj;
	}
	
	/*替换变量中的':'*/
	private  String replace(String str){
		if(str!=null){
			str=str.substring(str.indexOf(":")+1);

		}
		return str;
	}
	
	/*替换变量中的父路径*/
	private static String replaceParent(String varName,String parentPath){
		
		if(StringUtil.isNotNull(varName) && StringUtil.isNotNull(parentPath)){
			StringBuffer leftScript = new StringBuffer();
			// 右边未解释的部分
			String rightScript = varName.trim();
			// 下面代码将解释script代码
			for (int index = rightScript.indexOf(SCRIPT_VARFLAG_LEFT); index > -1; index = rightScript
								.indexOf(SCRIPT_VARFLAG_LEFT)) {
							int nxtIndex = rightScript.indexOf(SCRIPT_VARFLAG_RIGHT,
									index + 1);
							// index为开始点，nxtIndex为结束点，无结束点则退出循环
							if (nxtIndex == -1) {
								break;
							}
							String var = rightScript.substring(index + 1, nxtIndex);
							leftScript.append(rightScript.substring(0, index));
							rightScript = rightScript.substring(nxtIndex + 1);
                            leftScript.append(SCRIPT_VARFLAG_LEFT).append(toStr(var,parentPath))
							.append(SCRIPT_VARFLAG_RIGHT);
		   }
		   varName=(leftScript.append(rightScript)).toString();
		}
		return varName;
	}
	
	private static String toStr(String varName,String parentPath){
		if(StringUtil.isNotNull(varName)&& StringUtil.isNotNull(parentPath)){
			if(varName.indexOf(parentPath)>0){
				varName=varName.substring(varName.indexOf(parentPath)+parentPath.length());
			}
		}
		return varName;
	}
	
	public Object formatValue(Object value, String type)
			throws KmssUnExpectTypeException {
		try {
			if (value == null) {
				return null;
			}
			// 处理数组情况
			boolean isArray = type.endsWith("[]");
			if (isArray) {
				String m_type = type.substring(0, type.length() - 2);
				List rtnVal = null;
				if (value instanceof Object[]) {
					rtnVal = Arrays.asList(value);
				} else if (value instanceof List) {
					rtnVal = new ArrayList((Collection) value);
				} else {
					rtnVal = new ArrayList();
					rtnVal.add(value);
				}
				for (int i = 0; i < rtnVal.size(); i++) {
					rtnVal.set(i, formatValue(rtnVal.get(i), m_type));
				}
				return rtnVal;
			}
			// 注意：数组的处理不会进入到这里
			// 处理Clob类型的数据
			if (value instanceof Clob) {
				Clob m_value = (Clob) value;
				try {
					int length = (int) m_value.length();
					char[] buffer = new char[length];
					m_value.getCharacterStream().read(buffer);
					value = new String(buffer);
				} catch (Exception e) {
					return null;
				}
			}
			if ("Object".equalsIgnoreCase(type)) {
				return value;
			}
			if ("String".equalsIgnoreCase(type) || "RTF".equalsIgnoreCase(type)) {
				return value.toString();
			}
			if (value instanceof String) {
				String m_value = ((String) value).trim();
				if ("".equals(m_value)) {
					return null;
				}
				value = m_value;
			}
			if ("Date".equalsIgnoreCase(type) || "Time".equalsIgnoreCase(type)
					|| "DateTime".equalsIgnoreCase(type)) {
				
				if (value instanceof Date) {
					// 注意：SQL查询中，可能会返回java.sql.Date类型，该类型无法持久化，需要转换 #32394
					return new Date(((Date) value).getTime());
				}
				if (value instanceof String) {
					try{
						return DateUtil.convertStringToDate((String) value, type
								.toLowerCase(), null);
					}catch(Exception ex){
						//作者 曹映辉 #日期 2016年10月28日  兼容中英文环境相互切换默认日期数据无法解析问题
						if(((String) value).indexOf("/")>=0){
							if("Date".equals(type)){
								return DateUtil.convertStringToDate((String) value, "MM/dd/yyyy"); 
							}else if( "DateTime".equalsIgnoreCase(type)){
								return DateUtil.convertStringToDate((String) value, "MM/dd/yyyy HH:mm"); 
							}
						}
						else if(((String) value).indexOf("-")>=0){
							if("Date".equals(type)){
								return DateUtil.convertStringToDate((String) value, "yyyy-MM-dd"); 
							}else if( "DateTime".equalsIgnoreCase(type)){
								return DateUtil.convertStringToDate((String) value, "yyyy-MM-dd HH:mm"); 
							}
						}
					}
					
				}
				if (value instanceof Number) {
					return new Date(new Long(value.toString()));
				}
				//Oracle获取的日期类型，依赖了Oralc的驱动包，没找到好的解决方案 解决缺陷##25597 作者 曹映辉 #日期 2016年3月23日
				if ("oracle.sql.TIMESTAMP".equals(value.getClass().getName())) {
					Class TIMESTAMP_class = Class.forName("oracle.sql.TIMESTAMP");
					Method m = TIMESTAMP_class.getDeclaredMethod("timestampValue");
					return m.invoke(value);
					//obj = ((TIMESTAMP) obj).timestampValue();
				}
			} else if ("Integer".equalsIgnoreCase(type)) {
				if (value instanceof Number) {
					return ((Number) value).intValue();
				}
				if (value instanceof String) {
					return new Double(value.toString()).intValue();
				}
			} else if ("Long".equalsIgnoreCase(type)) {
				if (value instanceof Number) {
					return ((Number) value).longValue();
				}
				if (value instanceof String) {
					return new Double(value.toString()).longValue();
				}
			} else if ("BigDecimal".equalsIgnoreCase(type)) {
				if (value instanceof BigDecimal) {
					return value;
				}
				if (value instanceof BigInteger) {
					return new BigDecimal((BigInteger) value);
				}
				if (value instanceof Number) {
					double _value_ = ((Number) value).doubleValue();
					if (Double.isInfinite(_value_) || Double.isNaN(_value_)) {
						return null;
					}
					return new BigDecimal(_value_);
				}
				if (value instanceof String) {
					return new BigDecimal((String) value);
				}
			} else if ("Double".equalsIgnoreCase(type)) {
				if (value instanceof Number) {
					double _value_ = ((Number) value).doubleValue();
					if (Double.isInfinite(_value_) || Double.isNaN(_value_)) {
						return null;
					}
					return NumberUtil.correctDouble(_value_);
				}
				if (value instanceof String) {
					return NumberUtil
							.correctDouble(new Double(value.toString()));
				}
			} else if ("Boolean".equalsIgnoreCase(type)) {
				if (value instanceof Boolean) {
					return ((Boolean) value).booleanValue();
				}
				String m_value = value.toString();
				if ("true".equalsIgnoreCase(m_value)
						|| "t".equalsIgnoreCase(m_value)
						|| "yes".equalsIgnoreCase(m_value)
						|| "y".equalsIgnoreCase(m_value)
						|| "1".equalsIgnoreCase(m_value)) {
					return new Boolean(true);
				}
				if ("false".equalsIgnoreCase(m_value)
						|| "f".equalsIgnoreCase(m_value)
						|| "no".equalsIgnoreCase(m_value)
						|| "n".equalsIgnoreCase(m_value)
						|| "0".equalsIgnoreCase(m_value)) {
					return new Boolean(false);
				}
			}
		} catch (Exception e) {
			throw new KmssUnExpectTypeException(type, e);
		}
		return value;
	}
	
	
	//处理EAS会计科目
	private String dealAccount(NodeList nodeList,//节点数据
			                  Map<String, String> sqlMap, 
			                  List<Map<String, Object>> columnValueList){
		try {
			if(nodeList!=null && nodeList.getLength()>0){
				for(int i=1;i<nodeList.getLength();i++){//处理行数据,i=0为头数据，i>=1为行数据
					Node curNode=nodeList.item(i);
					NodeList childs=curNode.getChildNodes();
					if(childs!=null && childs.getLength()>0){
						TreeMap<String, Object> temp=new TreeMap<String,Object>();
						int k=1;
						for(int j=0;(j<childs.getLength()&& k<=AccountEnum.values().length);j++){
							Node node=childs.item(j);
							if (Node.ELEMENT_NODE == node.getNodeType() && "getAccountReturn".equals(node.getNodeName())) {
								temp.put(AccountEnum.getEnumValueByKey(k), node.getTextContent());
								k++;
							}
						}
						if(!temp.isEmpty()){
							temp.put("fd_id", IDGenerator
									.generateID());
							columnValueList.add(temp);
						}	
					}
					
				}
			}
			if (columnValueList.isEmpty()) {
				sqlMap.put("continue", "continue");
				return null;
			}
			// 明细表操作开始
			String syncTable = "tic_eas_account";//tic_k3_account
			String syncType ="1";
			String columnKey ="fd_number";
			// 设置插入语句
			String addSql = setAddSql(syncTable,columnValueList.get(0));
			String selectSql = "";
			// 增量
			selectSql = setSelectSql(syncTable, columnKey,
					columnValueList);
			sqlMap.put("selectSql", selectSql);
			sqlMap.put("syncType", syncType);
			sqlMap.put("addSql", addSql);
			return columnKey;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	//处理EAS账套
	private String dealOrg(NodeList nodeList,//节点数据
			             Map<String, String> sqlMap, 
			             List<Map<String, Object>> columnValueList) throws Exception{
		

		try {
			if(nodeList!=null && nodeList.getLength()>0){
				for(int i=1;i<nodeList.getLength();i++){//处理行数据,i=0为头数据，i>=1为行数据
					Node curNode=nodeList.item(i);
					NodeList childs=curNode.getChildNodes();
					if(childs!=null && childs.getLength()>0){
						TreeMap<String, Object> temp=new TreeMap<String,Object>();
						int k=1;
						for(int j=0;(j<childs.getLength() && k<=OrgEnum.values().length);j++){
							Node node=childs.item(j);
							if (Node.ELEMENT_NODE == node.getNodeType() && "getOrgReturn".equals(node.getNodeName())) {
								temp.put(OrgEnum.getEnumValueByKey(k), node.getTextContent());
								k++;
							}
						}
						if(!temp.isEmpty()){
							temp.put("fd_id", IDGenerator
									.generateID());
							columnValueList.add(temp);
						}	
					}
					
				}
			}
			if (columnValueList.isEmpty()) {
				sqlMap.put("continue", "continue");
				return null;
			}
			// 明细表操作开始
			String syncTable = "tic_eas_org";
			String syncType ="1";
			String columnKey ="fd_name";
			// 设置插入语句
			String addSql = setAddSql(syncTable,columnValueList.get(0));
			String selectSql = "";
			// 增量
			selectSql = setSelectSql(syncTable, columnKey,
					columnValueList);
			sqlMap.put("selectSql", selectSql);
			sqlMap.put("syncType", syncType);
			sqlMap.put("addSql", addSql);
			return columnKey;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	
	}
	
	

	
}
