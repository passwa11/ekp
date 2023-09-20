package com.landray.kmss.sys.attend.webservice;

import com.google.common.collect.Lists;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryLocation;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet;
import com.landray.kmss.sys.attend.model.SysAttendImportLog;
import com.landray.kmss.sys.attend.model.SysAttendSynDing;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendImportLogService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingService;
import com.landray.kmss.sys.attend.service.spring.SysAttendSynDingServiceImp;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendThreadPoolManager;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsOrgService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.annotation.RestApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author linxiuxian
 *
 */

@Controller
@RequestMapping(value = "/api/sys-attend/sysAttendRestservice", method = RequestMethod.POST)
@RestApi(docUrl = "/sys/attend/restservice/SysAttendRestServiceHelp.jsp", name = "sysAttendRestservice", resourceKey = "sys-attend:sysAttendRestservice.title")
public class SysAttendWebServiceImp
		implements ISysAttendWebService, SysAttendWebServiceConstant,
		SysOrgConstant,ApplicationListener<Event_Common> {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendWebServiceImp.class);
	private ISysWsOrgService sysWsOrgService;
	private ISysOrgCoreService sysOrgCoreService;
	private ISysAttendCategoryService sysAttendCategoryService;
	private ISysAttendMainService sysAttendMainService;
	private ISysAttendBusinessService sysAttendBusinessService;
	private ISysAttendImportLogService sysAttendImportLogService;
	
    public void setSysAttendImportLogService(ISysAttendImportLogService sysAttendImportLogService) {
        this.sysAttendImportLogService = sysAttendImportLogService;
    }

	public ISysAttendBusinessService getSysAttendBusinessService() {
		if (sysAttendBusinessService == null) {
			sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil
					.getBean("sysAttendBusinessService");
		}
		return sysAttendBusinessService;
	}
	public void setSysWsOrgService(ISysWsOrgService sysWsOrgService) {
		this.sysWsOrgService = sysWsOrgService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	public void
			setSysAttendMainService(
					ISysAttendMainService sysAttendMainService) {
		this.sysAttendMainService = sysAttendMainService;
	}
	private ISysAttendSynDingService sysAttendSynDingService=null;
	private ISysAttendSynDingService getISysAttendSynDingService(){
		if(sysAttendSynDingService ==null){
			sysAttendSynDingService= (ISysAttendSynDingService) SpringBeanUtil
					.getBean("sysAttendSynDingService");
		}
		return sysAttendSynDingService;
	}
	@ResponseBody
	@RequestMapping(value = "/addAttend")
	@Override
	public SysAttendResult
			addAttend(@RequestBody SysAttendAddContext addContext)
			throws Exception {
		SysAttendResult result = new SysAttendResult();
		 StackTraceElement[] stackElements = new Throwable().getStackTrace();
	        if(stackElements != null)
	        {
	            for(int i = 0; i < stackElements.length; i++)
	            {
	                logger.info(""+ stackElements[i]); 
	            }
	        }
		try {
			if (!checkNullIfNecessary(addContext,
					METHOD_CONSTANT_NAME_ADDATTEND,
					result)) {
				result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
				return result;
			}
			logger.debug("@webservice增加考勤记录接口被调用，信息为："
					+ JSONObject.fromObject(addContext).toString());
			String appName = addContext.getAppName();
			String dataType = addContext.getDataType();
			String datas = addContext.getDatas();
			JSONArray arrays = JSONArray.fromObject(datas);
			if (arrays == null || arrays.isEmpty()) {
				result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
				result.setMessage(ResourceUtil.getString(
						"sysAttendmain.webservice.warning.datas", "sys-attend",
						null,
						new Object[] { addContext.getDatas() }));
				logger.warn("参数信息中,考勤记录信息解析为空.传入信息为:" + addContext.getDatas());
				return result;
			}
			addAttendMain(appName, arrays,"webservice",null);
			result.setReturnState(RETURN_CONSTANT_STATUS_SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("@webservice增加考勤记录接口调用失败:" + e.getMessage(), e);
			result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
			result.setMessage(e.getMessage());
		}
		logger.debug("@webservice增加考勤记录接口被调用运行结束...");
		return result;
	}
	
	private void addAttendMain(String appName, JSONArray arrays,String operatorType,SysAttendImportLog sysAttendImportLog) throws Exception {
		logger.debug("增加考勤记录接口被调用运行开始...");
		if(arrays == null || arrays.isEmpty()) {
			logger.warn("考勤记录信息解析为空.传入信息为:" + arrays);
			if(sysAttendImportLog!=null) {
				sysAttendImportLog.setFdStatus(3);
				sysAttendImportLogService.update(sysAttendImportLog);
			}
			return;
		}
		 
		// 打卡记录按天分组成多个list
		List dateRecordList = groupByDate(arrays);
		Integer duplicateCount = 0;
		for (int k = 0; k < dateRecordList.size(); k++) {
			// 某天的打卡记录
			List<JSONObject> recordList = (List<JSONObject>) dateRecordList.get(k);
			// 考勤日期
			JSONObject json1 = recordList.get(0);
			//考勤日期
			Long workDate = json1.getLong("workDate");
			// 用户打卡记录
			Map<String, List<Date>> userSignDatas = convertUserData(recordList);
			// 用户与考勤组
			Map<String, List> userCateMap = getUserCategory(userSignDatas,recordList);

			for (String cateId : userCateMap.keySet()) {
				SysAttendCategory category = CategoryUtil.getCategoryById(cateId);
				// 某个考勤组下所有用户
				List users = userCateMap.get(cateId);
				List<List> groupLists = getUserGroup(users);
				for (List userList : groupLists) {
					//用户打卡记录 与 人员ID 组合MAP
					Map<String, List<Date>> newUserSignDatas = getUserAttendMainDataMap(userSignDatas,userList);
					// 用户打卡状态
					Map<String, List<JSONObject>> userStatusData = getUserSignedInfo(userSignDatas, category, userList,new Date(workDate));
					if(logger.isDebugEnabled()) {
						// 保存用户打卡记录
						logger.debug("getUserSignedInfo返回结果:" + userStatusData.toString());
					}
					getISysAttendSynDingService().addAttendMainBatch(userStatusData, appName, userList, new Date(workDate),"webservice接口同步置为无效打卡记录");
					/****  统一走上面的方法，为了统一入口，后期做优化的时候针对一个入口即可
					 *
					 * List<String> orgIdsList = new ArrayList(userStatusData.keySet());
					 * Map<String, String> areaMap = SysTimeUtil.getUserAuthAreaMap(orgIdsList);
					 * addBatch(userStatusData, appName, userList, new Date(workDate), areaMap);
					 */
					//每个用户本次删除的数量，记录LOG
					Map<String, Integer> duplicatesSignedList=new HashMap<String, Integer>();
					// 获取原始打卡记录
					Map<String, List<JSONObject>> userOriDataMap = getUserOriDatas(newUserSignDatas, cateId,  new Date(workDate), appName,duplicatesSignedList);
					if(logger.isDebugEnabled()) {
						logger.debug("userOriDataMap返回结果:" + userOriDataMap);
					}
					for(Integer count:duplicatesSignedList.values()) {
						if(count!=null) {
							duplicateCount+=count;
						}
					}
					// 保存原始打卡记录
					addOriBatch(userOriDataMap, appName, userList, new Date(workDate));
				}

			}
		}
		if(sysAttendImportLog!=null) {
			sysAttendImportLog.setFdStatus(2);
			String resultMsg=sysAttendImportLog.getFdResultMessage();
			if(StringUtil.isNotNull(resultMsg)) {
				JSONObject obj=JSONObject.fromObject(resultMsg);
				obj.put("duplicateCount", duplicateCount);
				if(obj.containsKey("successCount")) {
					Integer successCount = obj.getInt("successCount");
					obj.put("successCount", successCount-duplicateCount>0?successCount-duplicateCount:0);
				}
				sysAttendImportLog.setFdResultMessage(obj.toString());
			}
			sysAttendImportLogService.update(sysAttendImportLog);
		}
		logger.debug("增加考勤记录接口被调用运行结束...");
	}

	/**
	 * 获取原始打卡信息(去除重复)
	 * 原始记录增加更多字段值
	 * @param userSignDatas
	 * @param cateId 考勤组ID
	 * @param workDate
	 * @param fdAppName
	 * @param duplicatesSignedList
	 * @return
	 * @throws Exception
	 */
	private Map getUserOriDatas(Map<String, List<Date>> userSignDatas,
						 		String cateId, Date workDate, String fdAppName,Map<String, Integer> duplicatesSignedList) throws Exception {
		Map<String, List<JSONObject>> records = new HashMap<String, List<JSONObject>>();
		Set<String> userIdSet = userSignDatas.keySet();
		// 获取数据库中用户已打卡记录
		Map<String, List<com.alibaba.fastjson.JSONObject>> signedList = getUserOriList(userIdSet, workDate,
				"");
		for (String key : userIdSet) {
			List<Date> userRecord = userSignDatas.get(key);
			LinkedHashSet<Long> hashSet = new LinkedHashSet<>();
			if(CollectionUtils.isNotEmpty(userRecord)){
				for (Date date : userRecord) {
					hashSet.add(date.getTime());
				}
			}
			List<com.alibaba.fastjson.JSONObject> userSignedRecord = signedList.get(key);
			if (CollectionUtils.isNotEmpty(hashSet) && CollectionUtils.isNotEmpty(userSignedRecord)) {
				//本次打卡的日期列表，进行比较
		        List<Long> listWithoutDuplicates = new ArrayList<>(hashSet);
				for (com.alibaba.fastjson.JSONObject jsonInfo: userSignedRecord) {
					Date haveDate = jsonInfo.getDate("date");
					Long haveDateLong =haveDate.getTime();
					String fdCategoryId =jsonInfo.getString("fdCategoryId");
					if(listWithoutDuplicates.contains(haveDateLong) && cateId !=null && cateId.equals(fdCategoryId)){
						//如果存在，并且考勤组相同 则剔除，否则新增
						hashSet.remove(haveDateLong);
					}
				}
			}
			//总数量减去 成功的数量，就是剔除的数量
			duplicatesSignedList.put(key, userRecord.size() - hashSet.size());
			List<JSONObject> data = new ArrayList<JSONObject>();
			for (Long dateLong : hashSet) {
				JSONObject record = new JSONObject();
				record.put("docCreateTime", dateLong);
				record.put("fdCategoryId", cateId);
				data.add(record);
			}
			records.put(key, data);
		}
		return records;
	}

	/**
	 * 获取用户原始记录
	 * @param userSet 用户
	 * @param date 日期
	 * @param fdAppName 应用名称
	 * @return
	 * @throws Exception
	 */
	private Map<String, List<com.alibaba.fastjson.JSONObject>> getUserOriList(Set<String> userSet,
																			  Date date, String fdAppName) throws Exception {
		Map<String, List<com.alibaba.fastjson.JSONObject>> userDataMap = new HashMap<String, List<com.alibaba.fastjson.JSONObject>>();
		List<String> userList = new ArrayList<String>(userSet);
		StringBuffer whereBlock = new StringBuffer();
		HQLInfo hqlInfo = new HQLInfo();
		whereBlock.append(HQLUtil.buildLogicIN("fdPersonId", userList));
		if (StringUtil.isNotNull(fdAppName)) {
			whereBlock.append(" and fdAppName=:fdAppName");
			hqlInfo.setParameter("fdAppName", fdAppName);
		}
		whereBlock
				.append(" and fdWorkDate>=:beginDate and fdWorkDate<:endDate");
		hqlInfo.setParameter("beginDate", AttendUtil.getDate(date, 0));
		hqlInfo.setParameter("endDate", AttendUtil.getDate(date, 1));
		hqlInfo.setWhereBlock(whereBlock.toString());
		List<SysAttendSynDing> dingList = getISysAttendSynDingService()
				.findList(hqlInfo);
		if (dingList == null || dingList.isEmpty()) {
			return userDataMap;
		}
		for (SysAttendSynDing ding : dingList) {
			String fdPersonId = ding.getFdPersonId();
			Date docCreateTime = ding.getFdUserCheckTime();
			List<com.alibaba.fastjson.JSONObject> lists=userDataMap.get(fdPersonId);
			if(CollectionUtils.isEmpty(lists)){
				lists =new ArrayList<>();
			}
			com.alibaba.fastjson.JSONObject jsonObject=new com.alibaba.fastjson.JSONObject();
			jsonObject.put("date",docCreateTime);
			jsonObject.put("fdCategoryId",ding.getFdGroupId());
			lists.add(jsonObject);
			userDataMap.put(fdPersonId,lists);
		}
		return userDataMap;
	}

	// 用户组分割
	private List getUserGroup(List users) {
		int maxCount = 1000;
		List<List> groupLists = new ArrayList<List>();
		if (users.size() <= maxCount) {
			groupLists.add(users);
		} else {
			groupLists = AttendUtil.splitList(users, maxCount);
		}
		return groupLists;
	}

	/**
	 * 保存有效考勤记录
	 * 方法弃用，统一使用
	 * @see SysAttendSynDingServiceImp#addAttendMainBatch
	 * @param userStatusData
	 * @param fdAppName
	 * @param userList
	 * @param date
	 * @param areaMap
	 * @throws Exception
	 */
	@Deprecated
	private void addBatch(Map<String, List<JSONObject>> userStatusData,
			String fdAppName, List userList, Date date,
			Map<String, String> areaMap)
			throws Exception {
		if (userStatusData.isEmpty()) {
			logger.warn("用户考勤同步记录为空:" + userList);
			return;
		}
		 StackTraceElement[] stackElements = new Throwable().getStackTrace();
	        if(stackElements != null)
	        {
	            for(int i = 0; i < stackElements.length; i++)
	            {
	                logger.info(""+ stackElements[i]); 
	            }
	        }
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement statement = null;
		PreparedStatement insert = null;
		PreparedStatement update = null;
		PreparedStatement delete = null;
		ResultSet rs = null;
		try {
			boolean isInsert = false, isUpdate = false, isDelete = false;
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			// 获取用户已打卡记录fdid,且无效的记录
			List<String> userIdList = this.getUserAttendMainOfDel(conn,
					date, userStatusData);
			if (!userIdList.isEmpty()) {
				delete = conn
						.prepareStatement(
								"update sys_attend_main set doc_status=1,doc_alter_time=?,fd_alter_record='webservice接口同步置为无效打卡记录' where "
										+ HQLUtil.buildLogicIN("fd_id",
												userIdList));
				delete.setTimestamp(1,
						new Timestamp(new Date().getTime()));
				delete.addBatch();
				isDelete = true;
			}
			update = conn
					.prepareStatement(
							"update sys_attend_main set fd_status=?,doc_create_time=?,fd_location=?,fd_off_type=?,"
									+ "doc_alter_time=?,fd_app_name=?,fd_work_id=?,fd_work_type=?,fd_work_key=?,fd_category_his_id=?,fd_date_type=?,fd_base_work_time=?,fd_is_across=?,"
									+ "fd_business_id=? "
									+ "where fd_id =?");
			insert = conn
					.prepareStatement(
							"insert into sys_attend_main(fd_id,fd_status,doc_create_time,fd_location,fd_work_type,fd_outside,fd_category_his_id,"
									+ "doc_creator_id,fd_work_id,fd_date_type,doc_creator_hid,fd_app_name,doc_status,fd_work_key,fd_base_work_time,fd_is_across,"
									+ "fd_business_id,fd_off_type,auth_area_id) "
									+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			for (String key : userStatusData.keySet()) {
				List<JSONObject> records = userStatusData.get(key);

				for (JSONObject record : records) {
					String fdId = record.containsKey("fdId")
							? record.getString("fdId") : "";
					Integer fdStatus = record.getInt("fdStatus");
					Date docCreateTime = new Date(
							record.getLong("docCreateTime"));
					String fdCategoryId = record.getString("fdCategoryId");
					String fdWorkId = record.getString("fdWorkId");
					Integer fdWorkType = record.getInt("fdWorkType");
					String fdLocation = record.getString("fdLocation");
					String fdWorkKey = record.containsKey("fdWorkKey")
							? record.getString("fdWorkKey") : "";
					String fdDateType = record.containsKey("dateType")
							? record.getString("dateType")
							: AttendConstant.FD_DATE_TYPE[0];
					// 如果休息日设置为节假日，那么类型优先为节假日
					fdDateType = AttendConstant.FD_DATE_TYPE[2]
							.equals(fdDateType) ? fdDateType
									: fdDateType;
					if (!fdDateType.equals(AttendConstant.FD_DATE_TYPE[2])) {
					fdDateType = AttendConstant.FD_DATE_TYPE[1]
							.equals(fdDateType) ? fdDateType
									: AttendConstant.FD_DATE_TYPE[0];
					}
					Long fdBaseWorkTime = record.containsKey("fdBaseWorkTime")
							? record.getLong("fdBaseWorkTime") : null;
					Boolean fdIsAcross = (Boolean) record.get("fdIsAcross");
					String _fdAppName = (String) record.get("fdAppName");
					if ("__".equals(_fdAppName) || StringUtil.isNull(_fdAppName)) {
						_fdAppName = fdAppName;
					}
					Integer fdOffType = (Integer) record.get("fdOffType");
					String fdBusId = (String) record.get("fdBusId");

					if (StringUtil.isNotNull(fdId)) {
						update.setInt(1, fdStatus);
						update.setTimestamp(2,
								new Timestamp(docCreateTime.getTime()));
						update.setString(3, fdLocation);
						update.setObject(4, fdOffType);
						update.setTimestamp(5,
								new Timestamp(new Date().getTime()));
						update.setString(6, _fdAppName);
						update.setString(7, StringUtil.isNotNull(fdWorkKey)
								? null : fdWorkId);
						update.setInt(8, fdWorkType);
						update.setString(9, StringUtil.isNotNull(fdWorkKey)
								? fdWorkKey : null);
						update.setString(10, fdCategoryId);
						update.setInt(11, Integer.valueOf(fdDateType));
						update.setTimestamp(12, fdBaseWorkTime != null
								? new Timestamp(fdBaseWorkTime) : null);
						update.setBoolean(13, fdIsAcross);
						update.setString(14, fdBusId);

						update.setString(15, fdId);
						update.addBatch();
						isUpdate = true;
					} else {
						fdId = IDGenerator.generateID();
						insert.setString(1, fdId);
						insert.setInt(2, fdStatus);
						insert.setTimestamp(3,
								new Timestamp(docCreateTime.getTime()));
						insert.setString(4, fdLocation);
						insert.setInt(5, fdWorkType);
						insert.setBoolean(6, false);
						insert.setString(7, fdCategoryId);
						insert.setString(8, key);
						insert.setString(9, StringUtil.isNotNull(fdWorkKey)
								? null : fdWorkId);
						insert.setInt(10, Integer.valueOf(fdDateType));
						insert.setString(11, getUserHId(key, userList));
						insert.setString(12, _fdAppName);
						insert.setInt(13, 0);
						insert.setString(14, StringUtil.isNotNull(fdWorkKey)
								? fdWorkKey : null);
						insert.setTimestamp(15, fdBaseWorkTime != null
								? new Timestamp(fdBaseWorkTime) : null);
						insert.setBoolean(16, fdIsAcross);
						insert.setString(17, fdBusId);
						insert.setObject(18, fdOffType);
//						insert.setString(19, areaMap.get(key));
						insert.setString(19, "ccd");
						insert.addBatch();
						isInsert = true;
					}
				}
			}
			if (isUpdate) {
				update.executeBatch();
			}
			if (isInsert) {
				insert.executeBatch();
			}
			if (isDelete) {
				delete.executeBatch();
			}
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
			conn.rollback();
			throw ex;
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeStatement(update);
			JdbcUtils.closeStatement(delete);
			JdbcUtils.closeStatement(insert);
			JdbcUtils.closeConnection(conn);
		}
	}

	/**
	 * 保存导入数据到原始数据
	 * @param userOriDataMap 人员跟考勤数据的关联MAP
	 * @param fdAppName 数据来源名称
	 * @param userList 用户列表
	 * @param date 日期
	 * @throws Exception
	 */
	private void addOriBatch(Map<String, List<JSONObject>> userOriDataMap,
			String fdAppName, List<String> userList, Date date)
			throws Exception {
		if (userOriDataMap.isEmpty()) {
			logger.warn("用户考勤同步记录为空，人员ID:" + userList);
			return;
		}
		Map<String, SysAuthArea> userAuthMap = new HashMap<String, SysAuthArea>();
		for (String key : userOriDataMap.keySet()) {
			userAuthMap.put(key, SysTimeUtil.getUserAuthArea(key));
		}
		List<String> idList = new ArrayList<>();
		idList.addAll(userOriDataMap.keySet());
		List<SysOrgElement> orgList = AttendPersonUtil.getSysOrgElementById(idList);
		Map<String, SysOrgElement> userMap = new HashMap<String, SysOrgElement>();
		for (SysOrgElement sysOrgElement : orgList) {
			userMap.put(sysOrgElement.getFdId(), sysOrgElement);
		}

		TransactionStatus status = TransactionUtils.beginNewTransaction();
		try {
			//打卡
			for (String key : userOriDataMap.keySet()) {
				List<JSONObject> records = userOriDataMap.get(key);

				for (JSONObject record : records) {
					Date docCreateTime = new Date(record.getLong("docCreateTime"));
					SysAttendSynDing ding = new SysAttendSynDing();
					ding.setFdAppName(fdAppName);
					ding.setFdWorkDate(AttendUtil.getDate(date, 0));
					ding.setFdUserCheckTime(docCreateTime);
					ding.setFdPersonId(key);
					ding.setDocCreator(userMap.get(key));
					ding.setAuthArea(userAuthMap.get(key));
					ding.setDocCreateTime(new Date());
					ding.setFdGroupId(record.getString("fdCategoryId"));
					getISysAttendSynDingService().add(ding);
				}
			}
			TransactionUtils.getTransactionManager().commit(status);
		} catch (Exception ex) {
			logger.error("写入考勤原始数据异常：{},{}",ex.getMessage(), ex);
			TransactionUtils.getTransactionManager().rollback(status);
		}
	}

	/**
	 * 获取用户打卡记录中无效记录id
	 * 
	 * @param conn
	 * @param workDate
	 * @param userMaps
	 * @return
	 * @throws Exception
	 */
	public List getUserAttendMainOfDel(Connection conn, Date workDate,
			Map<String, List<JSONObject>> userMaps) throws Exception {
		Set<String> userIds = userMaps.keySet();
		List<String> userIdList = new ArrayList<String>(userIds);

		String where = HQLUtil.buildLogicIN("doc_creator_id", userIdList)
				+ " and (doc_status=0 or doc_status is null) and fd_work_type in(0,1)"
				+ " and (doc_create_time>=? and doc_create_time<? and (fd_is_across is null or fd_is_across=?) "
				+ " or fd_is_across=? and doc_create_time>=? and doc_create_time<?)";
		String orgSql = "select fd_id from sys_attend_main where "
				+ where;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> fdIdList = new ArrayList<String>();
		try {
			statement = conn.prepareStatement(orgSql);
			statement.setTimestamp(1,
					new Timestamp(AttendUtil.getDate(workDate, 0).getTime()));
			statement.setTimestamp(2,
					new Timestamp(AttendUtil.getDate(workDate, 1).getTime()));
			statement.setBoolean(3, false);
			statement.setBoolean(4, true);
			statement.setTimestamp(5,
					new Timestamp(AttendUtil.getDate(workDate, 1).getTime()));
			statement.setTimestamp(6,
					new Timestamp(AttendUtil.getDate(workDate, 2).getTime()));
			rs = statement.executeQuery();
			while (rs.next()) {
				fdIdList.add(rs.getString(1));
			}
			// 获取无效记录id
			for (String key : userMaps.keySet()) {
				List<JSONObject> records = userMaps.get(key);
				for (JSONObject record : records) {
					String fdId = record.containsKey("fdId")
							? record.getString("fdId") : "";
					if (StringUtil.isNull(fdId)) {
						continue;
					}
					if (fdIdList.contains(fdId)) {
						fdIdList.remove(fdId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
		}

		return fdIdList;
	}

	/**
	 * 获取用户层级Id
	 * 
	 * @param userId
	 * @param userList
	 * @return
	 */
	private String getUserHId(String userId, List userList) {
		for (int i = 0; i < userList.size(); i++) {
			Map<String, String> m = (Map<String, String>) userList.get(i);
			if (userId.equals(m.get("orgId").toString())) {
				return (String) m.get("orgHId");
			}
		}
		return "";
	}

	/**
	 * 过滤本次打卡时间 是否在排班规则范围内
	 * @param userSignDatas 所有的打卡人和时间对应关系
	 * @param ele 人员
	 * @param signTimeList 排班规则
	 * @return
	 */
	private List<Date> filterUserSignDate(
			Map<String, List<Date>> userSignDatas,
			SysOrgElement ele,
			List<Map<String, Object>> signTimeList) {
		List<Date> newDateList =new ArrayList<>();
		List<Date> newUserSignDatas = userSignDatas.get(ele.getFdId());
		Boolean isTimeAreNew =Boolean.FALSE;
		//过滤打卡时间是否在本次排班范围内
		for(Map<String, Object> workConfig:signTimeList){
			isTimeAreNew =(Boolean) workConfig.get("isTimeAreNew");
			if(isTimeAreNew !=null && Boolean.TRUE.equals(isTimeAreNew)) {
				//上班班次的最早打卡时间
				Date beginTime = (Date) workConfig.get("fdStartTime");
				//上班班次的最晚打卡时间
				Date overTime = (Date) workConfig.get("fdEndTime");
				if(beginTime ==null || overTime ==null){
					continue;
				}
				//下班的配置，是今日还是明日
				Integer fdEndOverTimeType = (Integer) workConfig.get("overTimeType");
				for (Date createTime : newUserSignDatas) {
					/** 2) 封装每个班次内的打卡时间 */
					Date workDate = AttendUtil.getDate(createTime, 0);
					if (beginTime != null) {
						beginTime = AttendUtil.joinYMDandHMS(workDate, beginTime);
					}
					if (overTime != null) {
						if (Integer.valueOf(2).equals(fdEndOverTimeType)) {
							//如果是次日，则加一
							overTime = AttendUtil.joinYMDandHMS(AttendUtil.getDate(workDate, 1), overTime);
						} else {
							overTime = AttendUtil.joinYMDandHMS(workDate, overTime);
						}
					}
					//当前打卡时间在此范围内。则表示是该班次
					if (beginTime.getTime() <= createTime.getTime() && createTime.getTime() <= overTime.getTime()
						&& !newDateList.contains(createTime)
					) {
						//在同一个最早最晚打卡时间，则认定为同一个班次
						newDateList.add(createTime);
						continue;
					}
				}
			} else {
				break;
			}
		}
		return newDateList;
	}

	/**
	 * 过滤用户有效的打卡时间。反正我是没看懂
	 * @param userSignDatas
	 * @param userList
	 * @return
	 */
	private Map<String, List<Date>> getEffectiveUserAttendMain(
			Map<String, List<Date>> userSignDatas,
			List userList) {
		Map<String, List<Date>> newUserSignDatas = new HashMap<String, List<Date>>();
		for (String userKey : userSignDatas.keySet()) {
			for (int i = 0; i < userList.size(); i++) {
				Map<String, String> m = (Map<String, String>) userList.get(i);
				if (userKey.equals(m.get("orgId").toString())) {
					newUserSignDatas.put(userKey, userSignDatas.get(userKey));
					break;
				}
			}
		}
		return newUserSignDatas;
	}

	/**
	 * 获取每个人员和打卡时间的对应关系
	 * @param userSignDatas
	 * @param userList
	 * @return key 是人员ID，value是打卡时间列表
	 */
	private Map<String, List<Date>> getUserAttendMainDataMap(
			Map<String, List<Date>> userSignDatas, List userList) {
		Map<String, List<Date>> newUserSignDatas = new HashMap<String, List<Date>>();
		for (String userKey : userSignDatas.keySet()) {
			for (int i = 0; i < userList.size(); i++) {
				Map<String, String> m = (Map<String, String>) userList.get(i);
				if (userKey.equals(m.get("orgId").toString())) {
					List<Date> dates=userSignDatas.get(userKey);
					List<Date> newDates=new ArrayList<Date>(dates);
					newUserSignDatas.put(userKey, newDates);
					break;
				}
			}
		}
		return newUserSignDatas;
	}

	/**
	 * 用户打卡状态相关信息
	 * @param userSignDatas 用户与打卡时间关系列表
	 * @param category 考勤组
	 * @param userList 用户列表
	 * @param workDate 打卡时间
	 * @return
	 * @throws Exception
	 */
	private Map getUserSignedInfo(Map<String, List<Date>> userSignDatas,
			SysAttendCategory category, List userList, Date workDate)
			throws Exception {
		//用户为kye。有效记录为value
		Map<String, List<JSONObject>> records = new HashMap<String, List<JSONObject>>();
		// 获取有效用户的打卡记录信息
		Map<String, List<Date>> newUserSignDatas = getEffectiveUserAttendMain(userSignDatas, userList);
		//用户列表为空不处理
		Set<String> userKeySet =newUserSignDatas.keySet();
		if(CollectionUtils.isEmpty(userKeySet)){
			return records;
		}
		// 区分考勤组排班制
		if (Integer.valueOf(1).equals(category.getFdShiftType())) {
			String[] ids = new String[] {};
			ids = userKeySet.toArray(ids);
			//获取用户并且用户Key跟对象封装到Map供后面使用
			List<SysOrgElement> orgList = sysOrgCoreService.findByPrimaryKeys(ids);
			Map<String,SysOrgElement> orgMap =new HashMap<>();
			for (SysOrgElement e:orgList ) {
				orgMap.put(e.getFdId(),e);
			}
			for (String userKey : newUserSignDatas.keySet()) {
				SysOrgElement ele = orgMap.get(userKey);
				// 构建有效打卡记录
				genTimeRecords(userSignDatas, category, workDate, ele, records,null);
			}
			return records;
		}
		// 获取数据库中用户已打卡记录
		Map<String, List<JSONObject>> signedList = getUserSignedList(userKeySet, workDate);
		// 获取数据库中用户出差/请假/外出相关时间区间
		Map<String, List<JSONObject>> bussList = this.getUserBussList(userKeySet, workDate);
		// 获取考勤组打卡时间点
		List works = sysAttendCategoryService
				.getAttendSignTimes(category, workDate);
		if (works.isEmpty()) {
			return records;
		}
		// 班次信息
		boolean isOneWork = works.size() > 2 ? false : true;
		Map map1 = (Map) works.get(0);
		Map map2 = (Map) works.get(1);
		Map map3 = null, map4 = null;
		if (!isOneWork) {
			map3 = (Map) works.get(2);
			map4 = (Map) works.get(3);
		}

		for (String userKey : newUserSignDatas.keySet()) {
			List<Date> dateList = newUserSignDatas.get(userKey);
			List<JSONObject> signedRecordList = signedList.get(userKey);
			// 已打卡时间列表
			List<Date> userSignedTimeList = getUserSignedList(signedRecordList);
			dateList.addAll(userSignedTimeList);

			List<JSONObject> mainList = new ArrayList<JSONObject>();
			// 构造用户最新打卡记录
			if (isOneWork) {
				genAttendMain(dateList, signedRecordList, map1, map2, mainList,
						workDate,category);
			} else {
				genAttendMain(dateList, signedRecordList, map1, map2, map3,	map4, mainList, workDate,category);
			}
			if (!mainList.isEmpty()) {
				records.put(userKey, mainList);
			}
		}
		// 重新计算该日期是否工作日打卡
		formatAttendMain(records, category, bussList, workDate);

		return records;
	}

	/**
	 * 生成排班类型的打卡记录
	 * @param userSignDatas 用户与打卡时间的对应Map
	 * @param category 考勤组
	 * @param workDate 工作日
	 * @param ele 用户对象
	 * @param records 打卡记录封装存储
	 * @throws Exception
	 */
	private List<JSONObject> genTimeRecords(Map<String, List<Date>> userSignDatas,
			SysAttendCategory category,Date workDate, SysOrgElement ele,
			Map<String, List<JSONObject>> records,List<Date> dateList) throws Exception {
		//考勤组排班时间列表
		List<Map<String, Object>> signTimeList = sysAttendCategoryService.getAttendSignTimes(ele,workDate);
		boolean isRest = false;
		if (signTimeList.isEmpty()) {
			isRest = true;
			// 休息日也允许同步数据
			signTimeList = sysAttendCategoryService.getAttendSignTimes(category, workDate, ele, true);
		}
		if (signTimeList.isEmpty()) {
			logger.warn("该用户当天没有排班信息,不同步考勤记录:用户名:" + ele.getFdName()
					+ ";时间:" + workDate);
			return null;
		}
		//用户主键
		String userKey =ele.getFdId();
		// 获取当前用户的打卡时间记录信息
		if(CollectionUtils.isEmpty(dateList)) {
			dateList = userSignDatas.get(userKey);
		}
		//filterUserSignDate(userSignDatas, ele,signTimeList);
		if(CollectionUtils.isEmpty(dateList)){
			//本次更新无打卡时间，则不继续执行
			return null;
		}

		Set<String> userKeySet=new HashSet<>();
		userKeySet.add(userKey);
		// 获取数据库中用户已打卡记录
		Map<String, List<JSONObject>> signedList = getUserSignedList(userKeySet, workDate);
		// 获取数据库中用户出差/请假/外出相关时间区间
		Map<String, List<JSONObject>> bussList = this.getUserBussList(userKeySet, workDate);
		// 系统中用户已打卡记录
		List<JSONObject> signedRecordList = signedList.get(userKey);
		// 已打卡时间列表
		List<Date> userSignedTimeList = getUserSignedList(signedRecordList);
		dateList.addAll(userSignedTimeList);
		// 构造用户最新打卡记录。多次记录合并在一起
		List<JSONObject> mainList = new ArrayList<JSONObject>();
		genTimeAttendMain(ele,dateList, signedRecordList, mainList,workDate, signTimeList,records);
		if (!mainList.isEmpty()) {
			formatAttendMain(mainList, isRest,bussList.get(ele.getFdId()), workDate, ele, signTimeList);
			records.put(userKey, mainList);
		}
		return mainList;
	}

	/**
	 * 获取用户已打卡的时间列表
	 * 
	 * @param signedRecordList
	 * @return
	 */
	private List<Date> getUserSignedList(List<JSONObject> signedRecordList) {
		List<Date> dateList = new ArrayList<Date>();
		if (signedRecordList == null || signedRecordList.isEmpty()) {
			return dateList;
		}
		for (JSONObject record : signedRecordList) {
			Long createTime = record.getLong("docCreateTime");
			Date docCreateTime = new Date(createTime);
			dateList.add(docCreateTime);
		}
		return dateList;
	}
	
	/**
	 * 获取用户缺卡的时间列表
	 * 
	 * @param signedRecordList
	 * @return
	 */
	private List<Date> getUserNoSignedList(List<JSONObject> signedRecordList) {
		List<Date> dateList = new ArrayList<Date>();
		if (signedRecordList == null || signedRecordList.isEmpty()) {
			return dateList;
		}
		for (JSONObject record : signedRecordList) {
			Long createTime = record.getLong("docCreateTime");
			Integer fdStatus =record.getInt("fdStatus");
			Integer fdState = (Integer) record.get("fdState");
			boolean isOk = (fdState != null && fdState.intValue() == 2)
					? true : false;// 异常审批通过为true
			if (fdStatus == null || fdStatus.intValue() == 0 && !isOk) {
				Date docCreateTime = new Date(createTime);
				dateList.add(docCreateTime);
			}
		}
		return dateList;
	}

	/**
	 * 移除dateList中 noSigDates的数据
	 * 
	 * @param dateList
	 * @param noSigDates
	 */
	private void formatDateList(List<Date> dateList,List<Date> noSigDates) {
		for (Date record : noSigDates) {
			int lastIndex=dateList.lastIndexOf(record);
			dateList.remove(lastIndex);
		}
	}

	private void formatAttendMain(Map<String, List<JSONObject>> records,
			SysAttendCategory category,
			Map<String, List<JSONObject>> bussListMap, Date workDate) {
		try {
			if (records.isEmpty()) {
				return;
			}
			// 判断该日期是否需要考勤打卡
			Boolean isAttendNeeded = sysAttendCategoryService
					.isAttendNeeded(category, workDate);
			// 打卡当天类型
			String dateType = Boolean.TRUE.equals(isAttendNeeded)
					? AttendConstant.FD_DATE_TYPE[0]
					: AttendConstant.FD_DATE_TYPE[1];
			if (AttendConstant.FD_DATE_TYPE[1].equals(dateType)) {
				// 区分休息日/节假日
				boolean isHoliday = sysAttendCategoryService
						.isHoliday(category.getFdId(), workDate);
				if (isHoliday) {
					dateType = AttendConstant.FD_DATE_TYPE[2];
				}
			}

			for (String userKey : records.keySet()) {
				List<JSONObject> mainList = records.get(userKey);
				for (Iterator<JSONObject> iter= mainList.iterator();iter.hasNext();) {
					JSONObject record=iter.next();
					// 出差/请假/外出状态
					Long workTime = record.containsKey("fdBaseWorkTime")
							? record.getLong("fdBaseWorkTime") : null;
					Date fdBaseWorkTime = workTime != null
							? AttendUtil.joinYMDandHMS(workDate,
									new Date(workTime))
							: null;
					// 跨天排班处理
					Integer fdOverTimeType = record
							.containsKey("overTimeType")
									? Integer.valueOf(
											record.getString("overTimeType"))
									: 1;
					if (fdBaseWorkTime != null && fdOverTimeType == 2) {
						fdBaseWorkTime = AttendUtil.addDate(fdBaseWorkTime, 1);
					}
					List<JSONObject> bussList = bussListMap != null
							? bussListMap.get(userKey) : null;
					if (fdBaseWorkTime != null && bussList != null) {
						long baseWorkTime = fdBaseWorkTime.getTime();
						for (JSONObject bus : bussList) {
							Long fdBusStartTime = (Long) bus
									.get("fdBusStartTime");
							Long fdBusEndTime = (Long) bus.get("fdBusEndTime");
							Integer fdBusType = (Integer) bus.get("fdBusType");// 业务类型
							String busId = (String) bus.get("fdBusId");
							Integer fdLeaveType = (Integer) bus
									.get("fdLeaveType");
							if (baseWorkTime >= fdBusStartTime
									&& baseWorkTime <= fdBusEndTime) {
								int fdStatus = fdBusType == 7 ? 6 : fdBusType;
								record.put("fdStatus", fdStatus);
								record.put("fdBusId", busId);
								if (fdStatus == 5) {
									// 请假
									record.put("fdOffType", fdLeaveType);
								}
								break;
							}
						}
					}
					record.put("dateType", dateType);
					if (!AttendConstant.FD_DATE_TYPE[0].equals(dateType)) {
						Integer fdStatus = record.getInt("fdStatus");
						if (AttendUtil.getAttendTrip() && fdStatus == 4) {// 出差按自然日计算
						} else if(fdStatus==0) {
							iter.remove();
						} else if (!AttendUtil.isAttendBuss(fdStatus + "")) {
							record.put("fdStatus", 1);// 非工作日时,为自由打卡
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("判断该日期是否需要考勤打卡报错,workDate:" + category.getFdId(), e);
		}

	}
	/**
	 * 重新计算排班制时休息日打卡、状态等信息
	 *
	 * @param mainList
	 * @param isRest
	 * @throws Exception
	 */
	private void formatAttendMain(List<JSONObject> mainList,
								  boolean isRest,
								  List<JSONObject> bussList,
								  Date workDate,
								  SysOrgElement ele,
								  List<Map<String, Object>> signTimeConfigurations)
			throws Exception {
		// 打卡当天类型
		String dateType = !isRest ? AttendConstant.FD_DATE_TYPE[0] : AttendConstant.FD_DATE_TYPE[1];
		if (AttendConstant.FD_DATE_TYPE[1].equals(dateType)) {
			// 区分休息日/节假日
			boolean isHoliday = sysAttendCategoryService
					.isHoliday(null, workDate, ele, true);
			if (isHoliday) {
				dateType = AttendConstant.FD_DATE_TYPE[2];
			}
		}
		//上班
		Map<String, Object> tempWorkTime = signTimeConfigurations.get(0);
		//下班
		Map<String, Object> tempOffWorkTime = signTimeConfigurations.get(signTimeConfigurations.size() -1);
		//班次的最早打卡时间
		Date workBeginTime = (Date) tempWorkTime.get("fdStartTime");
		//班次的最晚打卡时间
		Date workOverTime = (Date) tempOffWorkTime.get("fdEndTime");

		if (workBeginTime != null && workOverTime != null) {
			workBeginTime = AttendUtil.joinYMDandHMS(workDate, workBeginTime);

			//下班的最晚打卡时间配置，是今日还是明日
			Integer yesterFdEndOverTimeType = (Integer) tempOffWorkTime.get("endOverTimeType");
			//如果昨日是最晚打卡时间是允许次日
			workOverTime = AttendUtil.joinYMDandHMS(Integer.valueOf(2).equals(yesterFdEndOverTimeType) ? AttendUtil.getDate(workDate,1) : workDate, workOverTime);
		}
		for (Iterator<JSONObject> iter= mainList.iterator();iter.hasNext();) {
			JSONObject record=iter.next();
			//实际打卡时间
			Long fdUserCheckTime = record.containsKey("docCreateTime") ? record.getLong("docCreateTime") : null;
			//打卡时间，在标准的打卡时间范围内
			if (workBeginTime.getTime() <= fdUserCheckTime && fdUserCheckTime <= workOverTime.getTime()) {
				//标准的打卡日期
				Long workTime = record.containsKey("fdBaseWorkTime") ? record.getLong("fdBaseWorkTime") : null;
				Date fdBaseWorkTime = workTime != null ? new Date(workTime): null;
				if (fdBaseWorkTime != null && bussList != null) {
					long baseWorkTime = fdBaseWorkTime.getTime();
					for (JSONObject bus : bussList) {
						Long fdBusStartTime = (Long) bus.get("fdBusStartTime");
						Long fdBusEndTime = (Long) bus.get("fdBusEndTime");
						Integer fdBusType = (Integer) bus.get("fdBusType");// 业务类型
						String busId = (String) bus.get("fdBusId");
						Integer fdLeaveType = (Integer) bus.get("fdLeaveType");
						if (baseWorkTime >= fdBusStartTime && baseWorkTime <= fdBusEndTime) {
							int fdStatus = fdBusType == 7 ? 6 : fdBusType;
							record.put("fdStatus", fdStatus);
							record.put("fdBusId", busId);
							if (fdStatus == 5) {
								// 请假
								record.put("fdOffType", fdLeaveType);
							}
							break;
						}
					}
				}
			}
			if(!record.containsKey("dateType")) {
				record.put("dateType", dateType);
			}
			if (!AttendConstant.FD_DATE_TYPE[0].equals(dateType)) {
				Integer fdStatus = record.getInt("fdStatus");
				if (AttendUtil.getAttendTrip() && fdStatus == 4) {// 出差按自然日计算
				} else if (fdStatus == 0) {
					iter.remove();
				} else if (!AttendUtil.isAttendBuss(fdStatus + "")) {
					record.put("fdStatus", 1);// 非工作日时,为自由打卡
				}
			}
		}
	}
	/**
	 * 重新计算排班制时休息日打卡
	 * (老的逻辑，暂时保留)
	 * @param mainList
	 * @param isRest
	 * @throws Exception
	 */
	private void formatAttendMain(List<JSONObject> mainList, boolean isRest,
			List<JSONObject> bussList, Date workDate, SysOrgElement ele)
			throws Exception {
		// 打卡当天类型
		String dateType = !isRest ? AttendConstant.FD_DATE_TYPE[0]
				: AttendConstant.FD_DATE_TYPE[1];
		if (AttendConstant.FD_DATE_TYPE[1].equals(dateType)) {
			// 区分休息日/节假日
			boolean isHoliday = sysAttendCategoryService
					.isHoliday(null, workDate, ele, true);
			if (isHoliday) {
				dateType = AttendConstant.FD_DATE_TYPE[2];
			}
		}
		for (Iterator<JSONObject> iter= mainList.iterator();iter.hasNext();) {
			JSONObject record=iter.next();
			// 出差/请假/外出状态
			Long workTime = record.containsKey("fdBaseWorkTime")
					? record.getLong("fdBaseWorkTime") : null;
			Date fdBaseWorkTime = workTime != null
					? AttendUtil.joinYMDandHMS(workDate, new Date(workTime))
					: null;
			// 跨天排班处理
			Integer fdOverTimeType = record
					.containsKey("overTimeType")
							? Integer.valueOf(
									record.getString("overTimeType"))
							: 1;
			if (fdBaseWorkTime != null && fdOverTimeType == 2) {
				fdBaseWorkTime = AttendUtil.addDate(fdBaseWorkTime, 1);
			}

			if (fdBaseWorkTime != null && bussList != null) {
				long baseWorkTime = fdBaseWorkTime.getTime();
				for (JSONObject bus : bussList) {
					Long fdBusStartTime = (Long) bus.get("fdBusStartTime");
					Long fdBusEndTime = (Long) bus.get("fdBusEndTime");
					Integer fdBusType = (Integer) bus.get("fdBusType");// 业务类型
					String busId = (String) bus.get("fdBusId");
					Integer fdLeaveType = (Integer) bus
							.get("fdLeaveType");
					if (baseWorkTime >= fdBusStartTime
							&& baseWorkTime <= fdBusEndTime) {
						int fdStatus = fdBusType == 7 ? 6 : fdBusType;
						record.put("fdStatus", fdStatus);
						record.put("fdBusId", busId);
						if (fdStatus == 5) {
							// 请假
							record.put("fdOffType", fdLeaveType);
						}
						break;
					}
				}
			}
			record.put("dateType", dateType);
			if (!AttendConstant.FD_DATE_TYPE[0].equals(dateType)) {
				Integer fdStatus = record.getInt("fdStatus");
				if (AttendUtil.getAttendTrip() && fdStatus == 4) {// 出差按自然日计算
				} else if(fdStatus==0) {
					iter.remove();
				} else if (!AttendUtil.isAttendBuss(fdStatus + "")) {
					record.put("fdStatus", 1);// 非工作日时,为自由打卡
				}
			}
		}
	}

	/**
	 * 获取考勤日期,非用户实际打卡日期(支持跨天打卡)
	 * 
	 * @param date
	 *            打卡时间
	 * @return
	 */
	public Date getAttendDate(Date date, SysAttendCategory category,SysOrgElement ele) throws Exception {
		// 区分考勤组排班制
		if (Integer.valueOf(1).equals(category.getFdShiftType())) {
			//考勤组排班时间列表
			List<Map<String, Object>> signTimeList = sysAttendCategoryService.getAttendSignTimes(category, date, ele);
			if (signTimeList.isEmpty()) {
				// 休息日也允许同步数据
				signTimeList = sysAttendCategoryService.getAttendSignTimes(category, date, ele, true);
			}
			//判断时间是否在排班的最早最晚打卡时间范围内
			Boolean isTimeAreNew =false;
			for(Map<String, Object> workConfig:signTimeList) {
				isTimeAreNew = (Boolean) workConfig.get("isTimeAreNew");
				if (isTimeAreNew != null && Boolean.TRUE.equals(isTimeAreNew)) {

					//班次的最早打卡时间
					Date beginTime = (Date) workConfig.get("fdStartTime");
					//班次的最晚打卡时间
					Date overTime = (Date) workConfig.get("fdEndTime");
					if (beginTime == null || overTime == null) {
						continue;
					}
					//判断跨天
					//下班的最晚打卡时间配置，是今日还是明日
					Integer fdEndOverTimeType = (Integer) workConfig.get("endOverTimeType");
					//判断打卡时间是否在这个最早最晚的范围内
					beginTime = AttendUtil.joinYMDandHMS(date, beginTime);
					if (overTime != null) {
						if (Integer.valueOf(2).equals(fdEndOverTimeType)) {
							//如果是次日，则加一
							overTime = AttendUtil.joinYMDandHMS(AttendUtil.getDate(date, 1), overTime);
						} else {
							overTime = AttendUtil.joinYMDandHMS(date, overTime);
						}
					}
					//时间在某个排班时间区域内，则以其为打卡日归类
					if (beginTime.getTime() <= date.getTime() && date.getTime() <= overTime.getTime()) {
						date =AttendUtil.addDate(beginTime,0);
						break;
					}else{
						//是否在昨日的范围内
						beginTime = AttendUtil.joinYMDandHMS(AttendUtil.getDate(beginTime, -1), beginTime);
						overTime = AttendUtil.joinYMDandHMS(AttendUtil.getDate(overTime, -1), overTime);
						if (beginTime.getTime() <= date.getTime() && date.getTime() <= overTime.getTime()) {
							date =AttendUtil.addDate(beginTime,0);
							break;
						}
					}
				}
			}
			if(Boolean.TRUE.equals(isTimeAreNew)){
				return date;
			}
		}
		Integer fdEndDay = category.getFdEndDay();
		// 最早/晚打卡时间
		Date fdEndTime = category.getFdEndTime();
		if(Integer.valueOf(1).equals(category.getFdSameWorkTime())){
			// 一周不同上下班
			SysAttendCategoryTimesheet lastTimeSheet = getTimeSheet(category, date);
			if (lastTimeSheet != null) {
				fdEndDay = lastTimeSheet.getFdEndDay();
				fdEndTime =lastTimeSheet.getFdEndTime2();
			}
		}
		if (!Integer.valueOf(2).equals(fdEndDay)) {
			return date;
		}
		// 考勤组支持跨天打卡
		int fdEndTimeMin = AttendUtil.getHMinutes(fdEndTime);
		if (AttendUtil.getHMinutes(date) <= fdEndTimeMin) {
			return AttendUtil.getDate(date, -1);
		}
		return date;
	}
	private SysAttendCategoryTimesheet getTimeSheet(SysAttendCategory category,
													Date date) {
		if (category == null || date == null) {
			return null;
		}
		List<SysAttendCategoryTimesheet> tSheets = category
				.getFdTimeSheets();
		if(CollectionUtils.isNotEmpty(tSheets)) {
			for (SysAttendCategoryTimesheet tSheet : tSheets) {
				if (StringUtil.isNotNull(tSheet.getFdWeek())
						&& tSheet.getFdWeek()
						.indexOf(AttendUtil.getWeek(date)
								+ "") > -1) {
					return tSheet;
				}
			}
		}
		return null;
	}
	/**
	 * 删除打卡时间是缺卡生成的时间
	 * @param record1
	 * @param dateList
	 */
	private void removeDefaultTime(JSONObject record1,List<Date> dateList){
		if(record1 !=null && CollectionUtils.isNotEmpty(dateList)) {
			Number fdStatus = (Number) record1.get("fdStatus");
			Long createTime = record1.getLong("docCreateTime");
			Integer levelStatusFlage = 0;
			if (levelStatusFlage.equals(fdStatus)) {
				dateList.remove(new Date(createTime));
			}
		}
	}
	/**
	 * 删除打卡时间是请假生成的时间
	 * @param record1
	 * @param dateList
	 */
	private void removeLeaveTime(JSONObject record1,List<Date> dateList){
		if(record1 !=null && CollectionUtils.isNotEmpty(dateList)) {
			Number fdStatus = (Number) record1.get("fdStatus");
			Long createTime = record1.getLong("docCreateTime");
			Integer levelStatusFlage = 5;
			if (levelStatusFlage.equals(fdStatus)) {
				dateList.remove(new Date(createTime));
				//可能同一时间的打卡记录有多条，这里默认删除靠后第一条。因为添加的时候，是添加打卡记录 后添加流程中的记录时间。所以从后开始提除，只剔除一个
//				int ind = dateList.lastIndexOf(new Date(createTime));
//				if (ind > -1) {
//					dateList.remove(ind);
//				}
			}
		}
	}

	/**
	 * 生成有效记录
	 * @param dateList 用户所有打卡日期
	 * @param signedRecordList 本次打卡记录列表
	 * @param workTime1 上班时间的规则配置
	 * @param workTime2 下班时间的规则配置
	 * @param mainList 最终用户打卡记录信息
	 * @param date 工作时间
	 * @throws Exception
	 */
	private void genAttendMain(List<Date> dateList,
			List<JSONObject> signedRecordList, Map workTime1, Map workTime2,
			List<JSONObject> mainList, Date date, SysAttendCategory category) throws Exception {

		JSONObject record1 = getSignRecord(signedRecordList, workTime1);
		JSONObject record2 = getSignRecord(signedRecordList, workTime2);

		removeLeaveTime(record1,dateList);
		removeLeaveTime(record2,dateList);

		putAttendMain(record1, workTime1, mainList,category);
		putAttendMain(record2, workTime2, mainList,category);
		// 缺卡时间列表
		List<Date> userNoSignedTimeList = getUserNoSignedList(
				signedRecordList);
		formatDateList(dateList,userNoSignedTimeList);

		// 是否支持跨天
		boolean isAcrossDay = (Boolean) workTime1.get("isAcrossDay");
		// 最早/最晚打卡时间
		Date fdStartTime = (Date) workTime1.get("fdStartTime");
		Date fdEndTime = (Date) workTime1.get("fdEndTime");
		if (fdStartTime == null) {
			fdStartTime = AttendUtil.getDate(new Date(), 0);
		}
		if (fdEndTime == null) {
			fdEndTime = AttendUtil.getEndDate(new Date(), 0);
		}
		// 实际最早/最晚打卡时间戳
		fdStartTime = AttendUtil.joinYMDandHMS(date, fdStartTime);
		fdEndTime = AttendUtil.joinYMDandHMS(
				AttendUtil.getDate(date, isAcrossDay ? 1 : 0), fdEndTime);
		int fdStartTimeMin = fdStartTime.getHours() * 60
				+ fdStartTime.getMinutes();
		this.sortDateList(dateList);
		//缓存同一天上午打卡时间
		Date time_sign_am = null;

		for (Date createTime : dateList) {
			//去除秒
			Date tempCreateTime =AttendUtil.removeSecond(createTime);
			// 非打卡时间范围
			if (tempCreateTime.before(fdStartTime)
					|| tempCreateTime.after(fdEndTime)) {
				continue;
			}

			int _createTime = createTime.getHours() * 60
					+ createTime.getMinutes();
			// 跨天打卡 加上24小时
			if (isAcrossDay && !createTime.before(AttendUtil.getDate(date, 1))) {
				_createTime = _createTime + 24 * 60;
			}

			int onTempDate = getShouldOnWorkTime(workTime1);
			Date signedTime1 = getAttendMainSignTime(workTime1, mainList);
			int signTimeMin2 = getShouldOffWorkTime(signedTime1, workTime2);

			//不定时工作制 当天有打卡即可, 没有迟到 早退 加班 只有正常和旷工
			Integer fdShiftType = category.getFdShiftType();
			if(4 == fdShiftType){
				//不定时工作制
				addAttendMains(record1, 1, createTime, workTime1, mainList,category);
			}else{
				//综合工时 无迟到 只有早退和 未打卡下班卡
				//是综合工时 并且是上班 状态为1 正常
				if (_createTime <= onTempDate && _createTime >= fdStartTimeMin) {
					// 上班时间
					addAttendMains(record1, 1, createTime, workTime1, mainList,category);
					time_sign_am = createTime;
				} else if (_createTime >= signTimeMin2) {
					int fdStatus = 1;
					if(category.getFdShiftType() == 3){
						Float fdTotalTime = category.getFdTotalTime();
						if(signedTime1 !=null && ((createTime.getTime() -signedTime1.getTime())/1000/60/60) <fdTotalTime ){
							//早退
							fdStatus = 3;
						}
					}
					// 下班时间
					addAttendMains(record2, fdStatus, createTime, workTime2, mainList,category);
				} else {
					time_sign_am = createTime;
					// 上下班时间范围内
					// TODO 后续需要根据允许打卡时间区间判断
					int fdStatus = 2;
					if(category != null && category.getFdShiftType() == 3){
						//正常
						fdStatus = 1;
					}
					addAttendMains(record1, fdStatus, createTime, workTime1, mainList,category);

					//下班
					signedTime1 = getAttendMainSignTime(workTime1, mainList);
					int nowStatus2 = 3;
					if (signedTime1 != null && !createTime.equals(signedTime1)) {
						if(category != null && category.getFdShiftType() == 3){
							Float fdTotalTime = category.getFdTotalTime();
							if(signedTime1 !=null && ((createTime.getTime() -signedTime1.getTime())/1000/60/60) >=fdTotalTime ){
								//早退
								nowStatus2 = 1;
							}
						}
						addAttendMains(record2, nowStatus2, createTime, workTime2,	mainList,category);
					}
				}

			}

		}
		time_sign_am = null;
		Long createTime1 = getAttendMainCreateTime(workTime1, mainList);
		Long createTime2 = getAttendMainCreateTime(workTime2, mainList);

		// 打卡记录数据校验
		if (createTime1 != null && createTime2 != null
				&& createTime1.longValue() == createTime2.longValue()) {
			mainList.remove(getAttendMain(workTime2, mainList));
		}
	}

	/**
	 * 两班制打卡
	 * 
	 * @param dateList
	 * @param signedRecordList
	 * @param workTime1
	 *            一班上班
	 * @param workTime2
	 *            一班下班
	 * @param workTime3
	 *            二班上班
	 * @param workTime4
	 *            二班下班
	 * @param mainList
	 * @throws Exception
	 */
	private void genAttendMain(List<Date> dateList,
			List<JSONObject> signedRecordList, Map workTime1, Map workTime2,
			Map workTime3, Map workTime4, List<JSONObject> mainList,
			Date date,SysAttendCategory category) throws Exception {

		// 获取班次的有效打卡记录（不正常优先0 -> 2 -> 3 -> 其他）
		JSONObject record1 = getSignRecord(signedRecordList, workTime1);
		JSONObject record2 = getSignRecord(signedRecordList, workTime2);
		JSONObject record3 = getSignRecord(signedRecordList, workTime3);
		JSONObject record4 = getSignRecord(signedRecordList, workTime4);

		removeLeaveTime(record1,dateList);
		removeLeaveTime(record2,dateList);
		removeLeaveTime(record3,dateList);
		removeLeaveTime(record4,dateList);
		this.sortDateList(dateList);
		// 依据班次有效打卡信息 构建mainList
		putAttendMain(record1, workTime1, mainList,category);
		putAttendMain(record2, workTime2, mainList,category);
		putAttendMain(record3, workTime3, mainList,category);
		putAttendMain(record4, workTime4, mainList,category);
		
		// 缺卡时间列表
		List<Date> userNoSignedTimeList = getUserNoSignedList(
				signedRecordList);
		formatDateList(dateList,userNoSignedTimeList);

		Integer fdLateTime = (Integer) workTime1.get("fdLateTime");
		Integer fdLeftTime = (Integer) workTime1.get("fdLeftTime");
		Boolean fdIsFlex = (Boolean) workTime1.get("fdIsFlex");
		Integer fdFlexTime = (Integer) workTime1.get("fdFlexTime");
		fdLateTime = fdLateTime == null ? 0 : fdLateTime;
		fdLeftTime = fdLeftTime == null ? 0 : fdLeftTime;
		fdIsFlex = fdIsFlex == null ? false : fdIsFlex.booleanValue();
		fdFlexTime = fdFlexTime == null ? 0 : fdFlexTime;

		Date signTime1 = (Date) workTime1.get("signTime");
		int signTimeMin1 = signTime1.getHours() * 60
				+ signTime1.getMinutes();
		Date signTime2 = (Date) workTime2.get("signTime");
		int signTimeMin2 = signTime2.getHours() * 60
				+ signTime2.getMinutes();
		Date signTime3 = (Date) workTime3.get("signTime");
		int signTimeMin3 = signTime3.getHours() * 60
				+ signTime3.getMinutes();
		Date signTime4 = (Date) workTime4.get("signTime");

		int signTimeMin4 = signTime4.getHours() * 60
				+ signTime4.getMinutes();

		// 是否支持跨天
		boolean isAcrossDay = (Boolean) workTime1.get("isAcrossDay");
		// 一班次最早/最晚打卡时间
		Date fdStartTime = (Date) workTime1.get("fdStartTime");
		Date fdEndTime1 = (Date) workTime1.get("fdEndTime1");
		// 二班次最早/最晚打卡时间
		Date fdStartTime2 = (Date) workTime1.get("fdStartTime2");
		Date fdEndTime = (Date) workTime1.get("fdEndTime");

		if (fdStartTime == null) {
			fdStartTime = AttendUtil.getDate(new Date(), 0);
		}
		if (fdEndTime == null) {
			fdEndTime = AttendUtil.getEndDate(new Date(), 0);
		}
		if (fdStartTime2 == null) {
			fdStartTime2 = signTime2;
		}
		if (fdEndTime1 == null) {
			fdEndTime1 = signTime3;
		}
		// 实际最早/最晚打卡时间戳
		fdStartTime = AttendUtil.joinYMDandHMS(date, fdStartTime);
		fdEndTime = AttendUtil.joinYMDandHMS(
				AttendUtil.getDate(date, isAcrossDay ? 1 : 0), fdEndTime);
		//班次1的最早打卡时间
		int fdStartTimeMin = fdStartTime.getHours() * 60 + fdStartTime.getMinutes();
		//班次2的最晚打卡时间
		int fdEndTimeMin = fdEndTime.getHours() * 60 + fdEndTime.getMinutes();
		//班次2的最早打卡时间
		int fdStartTime2Min = fdStartTime2.getHours() * 60 + fdStartTime2.getMinutes();
		//班次1的最晚打卡时间
		int fdEndTime1Min = fdEndTime1.getHours() * 60 + fdEndTime1.getMinutes();

		for (Date createTime : dateList) {
			int _createTime = createTime.getHours() * 60
					+ createTime.getMinutes();
			//去除秒
			Date tempCreateTime =AttendUtil.removeSecond(createTime);
			if (tempCreateTime.before(fdStartTime)
					|| tempCreateTime.after(fdEndTime)) {
				continue;
			}

			// 跨天打卡 加上24小时
			if (isAcrossDay && !createTime.before(AttendUtil.getDate(date, 1))) {
				_createTime = _createTime + 24 * 60;
			}
			//弹性的开始时间
			int onTempDate = getShouldOnWorkTime(workTime1);
			Date onLastWorkTime = getAttendMainSignTime(workTime3, mainList);
			//弹性的下班时间
			int offLastWork = getShouldOffWorkTime(onLastWorkTime, workTime4);

			//打卡时间在弹性上班时间之前 并且 大于等于最早打卡时间
			if (_createTime <= onTempDate && _createTime >= fdStartTimeMin) {
				// 上班时间
				addAttendMains(record1, 1, createTime, workTime1, mainList,category);
			} else if (_createTime >= offLastWork) {
				// 下班时间
				addAttendMains(record4, 1, createTime, workTime4,mainList,category);
			} else if (_createTime > onTempDate && _createTime < signTimeMin2) {
				//打卡时间大于弹性的上班时间，并且小于班次2的打卡时间，则为1班次的 迟到打卡
				addAttendMains(record1, 2, createTime, workTime1, mainList,category);
				
				//获取1班次的上班打卡时间
				Date pSignTime = getAttendMainSignTime(workTime1, mainList);
				int nowStatus = 3;
				//打卡时间大于2班次的弹性下班时间 则把状态设置为正常
				if (_createTime >= getShouldOffWorkTime(pSignTime, workTime2)) {
					nowStatus = 1;
				}
				//打卡时间不等于1班次的上班打卡时间，则是1班次的下班打卡时间
				if (pSignTime != null && !pSignTime.equals(createTime)) {
					addAttendMains(record2, nowStatus, createTime, workTime2, mainList,category);
				}
			} else if (_createTime >= signTimeMin2 && _createTime < signTimeMin3) {

				// 休息时间区间
				Date pSignTime = getAttendMainSignTime(workTime1, mainList);
				int nowStatus = 3;
				if (_createTime >= getShouldOffWorkTime(pSignTime,
						workTime2)) {
					nowStatus = 1;
				}
				if (_createTime <= fdEndTime1Min) {
					Integer status2 = getAttendMainStatus(workTime2,
							mainList);
					Integer status3 = getAttendMainStatus(workTime3, mainList);
					// 当前班次未打卡或早退以及二班已打卡时
					if (status2 == null || status2 == 0 || (status2==3 && status3!=null && status3!=0)) {
						addAttendMains(record2, nowStatus, createTime,
								workTime2, mainList,category);
					}
				}
				//2班次的最早打卡时间
				if (_createTime >= fdStartTime2Min) {
					// 午班上班
					Date pSignTime2 = getAttendMainSignTime(workTime2, mainList);
					if (pSignTime2 ==null || (pSignTime2 != null && !pSignTime2.equals(createTime))) {
						addAttendMains(record3, 1, createTime, workTime3, mainList,category);
					}
				}
			} else if (_createTime >= signTimeMin3
					&& _createTime < offLastWork) {
				//晚班时间区间
				int nowStatus = 1;
				if (_createTime > getShouldOnWorkTime(workTime3)) {
					nowStatus = 2;
				}
				addAttendMains(record3, nowStatus, createTime, workTime3,
						mainList,category);

				// 下班打卡早退
				Date pSignTime3 = getAttendMainSignTime(workTime3, mainList);
				int nowStatus4 = 3;
				if (pSignTime3 != null && !pSignTime3.equals(createTime)) {
					addAttendMains(record4, nowStatus4, createTime, workTime4,
							mainList,category);
				}
			}
		}

		// 打卡数据校验
		Long createTime1 = getAttendMainCreateTime(workTime1, mainList);
		Long createTime2 = getAttendMainCreateTime(workTime2, mainList);
		Long createTime3 = getAttendMainCreateTime(workTime3, mainList);
		Long createTime4 = getAttendMainCreateTime(workTime4, mainList);
		
		Integer status1 = getAttendMainStatus(workTime1, mainList);
		Integer status2 = getAttendMainStatus(workTime2, mainList);
		Integer status3 = getAttendMainStatus(workTime3, mainList);
		//当两班制时，如果一班制未打卡或请假/出差/外出，同步时则将下午的第一次打卡默认为第二班制的上班卡，而不是一班的下班卡。
		if(createTime2 != null && (((status1==null || status1==0) && (status2!=null && status2==1)) || (AttendUtil.isAttendBuss(status1 + "") && AttendUtil.isAttendBuss(status2 + ""))) && (status3==null || status3==0)) {
			Date createTime =new Date(createTime2);
			int _createTime = createTime.getHours() * 60
					+ createTime.getMinutes();
			if (_createTime >= fdStartTime2Min
					&& _createTime <= signTimeMin3) {
				//晚班时间区间
				int nowStatus = 1;
				if (_createTime > getShouldOnWorkTime(workTime3)) {
					nowStatus = 2;
				}
				addAttendMains(record3, nowStatus, createTime, workTime3,
						mainList,category);
				JSONObject json2= getAttendMain(workTime2, mainList);
				if(json2!=null) {
					json2.put("docCreateTime",
							AttendUtil.joinYMDandHMS(date, signTime2).getTime());
					json2.put("fdStatus",
							AttendUtil.isAttendBuss(status2 + "")?status2:0);
				}
			}
		}
		createTime2 = getAttendMainCreateTime(workTime2, mainList);
		createTime3 = getAttendMainCreateTime(workTime3, mainList);
		// 上班和下班打卡时间相同时，移除下班时间
		if (createTime1 != null && createTime2 != null
				&& createTime1.longValue() == createTime2.longValue()) {
			mainList.remove(getAttendMain(workTime2, mainList));
		}
		if (createTime3 != null && createTime4 != null
				&& createTime4.longValue() == createTime3.longValue()) {
			mainList.remove(getAttendMain(workTime4, mainList));
		}

		// 一班下班打卡时间大于二班上班打卡时间，相互交换
		if (createTime2 != null && createTime3 != null
				&& createTime2.longValue() > createTime3.longValue()) {
			getAttendMain(workTime3, mainList).put("docCreateTime",
					createTime2);
			getAttendMain(workTime2, mainList).put("docCreateTime",
					createTime3);
		}
		// 一班下班和二班上班打卡时间相同，当第一班没有上班打卡数据时移除第一班下班数据当作第二班上班数据否则移除第二班上班当第一班下班
		if (createTime2 != null && createTime3 != null
				&& createTime2.longValue() == createTime3.longValue()) {
			if (status1 == null || status1 == 0) {
				mainList.remove(getAttendMain(workTime2, mainList));
				removePatchDate(workTime2, mainList, record2,
						new Date(createTime2),category);
			} else {
				mainList.remove(getAttendMain(workTime3, mainList));
			}
		}
	}

	private void putAttendMain(JSONObject record, Map workTime,
			List<JSONObject> mainList,SysAttendCategory category) {
		if (record == null) {
			return;
		}
		Number fdStatus = (Number) record.get("fdStatus");
		Long createTime = record.getLong("docCreateTime");
		Object _nextSignTime = (Object) workTime.get("nextSignTime");
		if (_nextSignTime != null
				&& StringUtil.isNotNull(_nextSignTime.toString())) {
			Date nextSignTime = (Date) _nextSignTime;
			Date docCreateTime = new Date(createTime);
			nextSignTime = AttendUtil.joinYMDandHMS(docCreateTime,
					nextSignTime);
			// 是否跨天排班处理
			Integer fdOverTimeType = (Integer) workTime.get("nextOverTimeType");
			if (AttendConstant.FD_OVERTIME_TYPE[2].equals(fdOverTimeType)) {
				nextSignTime = AttendUtil.addDate(nextSignTime, 1);
			}
			if (!docCreateTime.before(nextSignTime)) {
				return;
			}
		}
		addAttendMains(record, fdStatus.intValue(), null, workTime, mainList,category);
	}

	private Long getAttendMainCreateTime(Map workTime,
			List<JSONObject> mainList) {
		String fdWorkId = (String) workTime.get("fdWorkTimeId");
		Integer fdWorkType = (Integer) workTime.get("fdWorkType");
		for (JSONObject json : mainList) {
			String _fdWorkId = json.getString("fdWorkId");
			String _fdWorkKey = json.containsKey("fdWorkKey")
					? json.getString("fdWorkKey") : "";
			_fdWorkId = StringUtil.isNotNull(_fdWorkKey) ? _fdWorkKey
					: _fdWorkId;
			if (_fdWorkId.equals(fdWorkId)
					&& json.getInt("fdWorkType") == fdWorkType) {
				return json.getLong("docCreateTime");
			}
		}

		return null;
	}

	/**
	 * 获取对应打卡时间
	 * 
	 * @param workTime
	 * @param mainList
	 * @return
	 */
	private Date getAttendMainSignTime(Map workTime,
			List<JSONObject> mainList) {
		JSONObject json = getAttendMain(workTime, mainList);
		if (json == null) {
			return null;
		}
		Long docCreateTime = json.getLong("docCreateTime");
		if (docCreateTime != null) {
			return new Date(docCreateTime);
		}
		return null;
	}

	/**
	 * 获取对应打卡状态
	 * 
	 * @param workTime
	 * @param mainList
	 * @return 0:缺卡 1:正常 2:迟到 3:早退 4:出差 5:请假 6:外出
	 */
	private Integer getAttendMainStatus(Map workTime,
			List<JSONObject> mainList) {
		JSONObject json = getAttendMain(workTime, mainList);
		if (json == null) {
			return null;
		}
		Number fdStatus = (Number) json.get("fdStatus");
		if (fdStatus != null) {
			return fdStatus.intValue();
		}
		return null;
	}

	private JSONObject getAttendMain(Map workTime, List<JSONObject> mainList) {
		String fdWorkId = (String) workTime.get("fdWorkTimeId");
		Integer fdWorkType = (Integer) workTime.get("fdWorkType");
		for (JSONObject json : mainList) {
			String fdWorkKey = json.containsKey("fdWorkKey")
					? json.getString("fdWorkKey") : "";
			String _fdWorkId = json.getString("fdWorkId");
			_fdWorkId = StringUtil.isNotNull(fdWorkKey) ? fdWorkKey : _fdWorkId;

			if (_fdWorkId.equals(fdWorkId)
					&& json.getInt("fdWorkType") == fdWorkType) {
				return json;
			}
		}

		return null;
	}

	/**
	 * 过滤请假、出差、外出流程产生的有效考勤记录
	 * @param mainList 封装的打卡记录列表
	 * @param fdWorkId 班次ID
	 * @param fdWorkType 班次类型
	 * @param docCreateTime 打卡时间
	 * @param fdBaseWorkTime 标准打卡时间
	 * @param main 最新封装的有效考勤记录对象
	 */
	private void filterBusinessAttendMain(List<JSONObject> mainList,
										  String fdWorkId,
										  Integer fdWorkType,
										  Date docCreateTime,
										  Date fdBaseWorkTime,
										  Map<String,Object> main
	){
		// 相同班次数据过滤
		for (JSONObject json : mainList) {
			String _fdWorkId = json.getString("fdWorkId");
			String _fdWorkKey = json.containsKey("fdWorkKey") ? json.getString("fdWorkKey") : "";
			_fdWorkId = StringUtil.isNotNull(_fdWorkKey) ? _fdWorkKey : _fdWorkId;
			//已存在记录的状态
			int _fdStatus = getSignedStatus(json);
			//班次和上下班都相同的情况下
			if (_fdWorkId.equals(fdWorkId) && json.getInt("fdWorkType") == fdWorkType) {
				//已存在记录的打卡时间
				Date createTime = new Date(json.getLong("docCreateTime"));
				if (fdWorkType == 0) {
					//上班打卡 && 最新时间在创建时间之后 并且 状态 不是缺卡
					if (docCreateTime.after(createTime) && _fdStatus > 0) {
						//如果创建时间等于 配置的打卡时间，则清除
						if (AttendUtil.isAttendBuss(_fdStatus + "") && createTime.equals(fdBaseWorkTime)) {
							// 出差/请假/外出(第一次要同步数据)
							mainList.remove(json);
						} else {
							//本次不新增有效考勤
							if(main !=null){
								main.clear();
							}
						}
					} else {
						mainList.remove(json);
					}
				}
				else if (fdWorkType == 1) {
					if (docCreateTime.before(createTime) && _fdStatus > 0) {
						if (AttendUtil.isAttendBuss(_fdStatus + "")
								&& createTime.equals(fdBaseWorkTime)) {
							// 出差/请假/外出(第一次要同步数据)
							mainList.remove(json);
						} else {
							if(main !=null){
								main.clear();
							}
						}
					}  else {
						mainList.remove(json);
					}
				}
				break;
			}
		}
	}

	private void addAttendMains(JSONObject oldReocrd, int fdStatus,
			Date newCreateTime, Map workTime, List<JSONObject> mainList,SysAttendCategory category) {
		String fdId = "";
		String fdAppName = null;
		Date docCreateTime = null;
		String fdLocation = null;
		Integer fdState = null;

		if (oldReocrd != null) {
			fdId = oldReocrd.getString("fdId");
			int status = getSignedStatus(oldReocrd);
			Long createTime = oldReocrd.getLong("docCreateTime");
			docCreateTime = new Date(createTime);
			fdLocation = (String) oldReocrd.get("fdLocation");
			fdAppName = (String) oldReocrd.get("fdAppName");
			fdState = oldReocrd.get("fdState") == null ? null
					: (Integer) oldReocrd.get("fdState");
			if(createTime !=null && newCreateTime !=null && createTime.equals(newCreateTime.getTime()) && status ==0){
				//打卡时间跟原来老的一样，并且是缺卡的状态。则使用原来的打卡状态，
				fdStatus = status;
			}
			else if (AttendUtil.isAttendBuss(status + "") || Integer.valueOf(2).equals(fdState)) {
				fdStatus = status;
			}
		}
		//不定时工时 打卡状态为正常
		if(category!=null && category.getFdShiftType() == 4 && newCreateTime != null){
			fdStatus = 1;
		}

		String fdWorkId = (String) workTime.get("fdWorkTimeId");
		Integer fdWorkType = (Integer) workTime.get("fdWorkType");
		String fdCategoryId = (String) workTime.get("categoryId");
		String isTimeArea = (String) workTime.get("isTimeArea");
		Date signTime = (Date) workTime.get("signTime");

		// 是否支持跨天
		boolean isAcrossDay = (Boolean) workTime.get("isAcrossDay");
		// 是否跨天排班
		Integer fdOverTimeType = (Integer) workTime.get("overTimeType");
		long workDate = (Long) workTime.get("fdWorkDate");
		Date fdWorkDate = new Date(workDate);
		Date fdBaseWorkTime = AttendUtil.joinYMDandHMS(fdWorkDate, signTime);
		if (newCreateTime != null) {
			if (StringUtil.isNotNull(fdId)
					&& newCreateTime.equals(docCreateTime)&&StringUtil.isNotNull(fdLocation)) {
			} else {
				fdAppName = "__";// 特殊标识
				List<SysAttendCategoryLocation> fdLocations = (List<SysAttendCategoryLocation>) workTime
						.get("fdLocations");
				if (fdLocations != null && !fdLocations.isEmpty()) {
					fdLocation = fdLocations.get(0).getFdLocation();
				}
			}
			docCreateTime = newCreateTime;
		}

		fdLocation = StringUtil.isNotNull(fdLocation) ? fdLocation : "";
		fdAppName = StringUtil.isNotNull(fdAppName) ? fdAppName : "";
		JSONObject main = new JSONObject();
		if (StringUtil.isNotNull(fdId)) {
			main.put("fdId", fdId);
		}

		//下班的场景是跨天
		if (AttendConstant.FD_OVERTIME_TYPE[2].equals(fdOverTimeType) && Integer.valueOf(1).equals(fdWorkType)) {
			fdBaseWorkTime = AttendUtil.addDate(fdBaseWorkTime, 1);
		}
		boolean fdIsAcross = false;
		if (isAcrossDay) {
			if (!docCreateTime.before(AttendUtil.getDate(fdWorkDate, 1))) {
				fdIsAcross = true;
			}
		}

		main.put("fdStatus", fdStatus);
		main.put("docCreateTime", docCreateTime.getTime());
		main.put("fdWorkId", "true".equals(isTimeArea) ? "" : fdWorkId);
		main.put("fdWorkKey", "true".equals(isTimeArea) ? fdWorkId : "");
		main.put("fdWorkType", fdWorkType);
		main.put("fdCategoryId", fdCategoryId);
		main.put("fdLocation", fdLocation);
		main.put("fdBaseWorkTime", fdBaseWorkTime.getTime());
		main.put("fdIsAcross", fdIsAcross);
		main.put("overTimeType", fdOverTimeType);
		main.put("fdAppName", fdAppName);
		main.put("fdState", fdState);
		filterBusinessAttendMain(mainList,fdWorkId,fdWorkType,docCreateTime,fdBaseWorkTime,main);
		if (main != null && main.keys().hasNext()) {
			mainList.add(main);
		}
	}

	/**
	 * 正常上班最晚打卡时间
	 * 
	 * @param map
	 * @return
	 */
	private int getShouldOnWorkTime(Map map) {
		int shouldSignTime = sysAttendCategoryService.getShouldOnWorkTime(map);
		return shouldSignTime;
	}

	/**
	 * 正常下班打卡时间
	 * @param pSignTime 同个班次上班用户打卡时间
	 * @param map 班次相关信息
	 * @return
	 */
	private int getShouldOffWorkTime(Date pSignTime, Map map) {
		int shouldSignTime = sysAttendCategoryService
				.getShouldOffWorkTime(pSignTime, map);
		return shouldSignTime;
	}

	private int getSignedStatus(JSONObject record) {
		Integer fdStatus = (Integer) record.get("fdStatus");
		if (fdStatus != null) {
			return fdStatus.intValue();
		}
		return 0;
	}

	private Date getSignedTime(JSONObject record) {
		if (record == null) {
			return null;
		}
		Date createTime = new Date(record.getLong("docCreateTime"));
		return createTime;
	}

	/**
	 * 过滤某班次的打卡记录
	 * @param signedRecordList 打卡记录
	 * @param workTime 打卡时间
	 * @return
	 * @throws Exception
	 */
	private JSONObject getSignRecord(List<JSONObject> signedRecordList,
			Map workTime) throws Exception {
		if (signedRecordList == null || signedRecordList.isEmpty()) {
			return null;
		}
		List<JSONObject> signedList = new ArrayList<JSONObject>();
		Map<String, JSONObject> statusMap = new HashMap<String, JSONObject>();
		for (JSONObject json : signedRecordList) {
			if (sysAttendCategoryService.isSameWorkTime(workTime,
					(String) json.get("fdWorkId"),
					(Integer) json.get("fdWorkType"),
					(String) json.get("fdWorkKey"))) {
				signedList.add(json);
				Number fdStatus = (Number) json.get("fdStatus");
				if (fdStatus != null) {
					statusMap.put(fdStatus.toString(), json);
				}
			}
		}
		if (!signedList.isEmpty()) {
			// 同班次存在多条记录时,以状态不正常的优先
			if (statusMap.containsKey("0")) {
				return statusMap.get("0");
			} else if (statusMap.containsKey("2")) {
				return statusMap.get("2");
			} else if (statusMap.containsKey("3")) {
				return statusMap.get("3");
			} else {
				return signedList.get(0);
			}
		}
		return null;
	}

	/**
	 * 获取用户考勤记录
	 * @param userSet 用户列表
	 * @param date 日期
	 * @return
	 * @throws Exception
	 */
	private Map getUserSignedList(Set<String> userSet,
			Date date) throws Exception {


		Map<String, List<JSONObject>> records = new HashMap<String, List<JSONObject>>();
		if(CollectionUtils.isEmpty(userSet)){
			return records;
		}
		boolean isException =false;
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			List<String> userList = new ArrayList<String>(userSet);

			String signedSql = "select fd_id,fd_status,fd_work_type,fd_work_id,fd_state,doc_creator_id,doc_create_time,fd_location,fd_work_key,fd_app_name,fd_is_across from sys_attend_main where " +
					" doc_creator_id in (:docCreatorIds) and (doc_status=0 or doc_status is null) and fd_work_type in(0,1) ";
			String whereOne = " and doc_create_time>=:beginTime and doc_create_time<:endTime and (fd_is_across is null or fd_is_across=:fdIsAcross0) ";

			String whereTwo = " and fd_is_across=:fdIsAcross1 and doc_create_time>=:nextBegin and doc_create_time<:nextEnd";

			List signedList1 = this.sysAttendCategoryService.getBaseDao().getHibernateSession().createNativeQuery(signedSql + whereOne)
					.setBoolean("fdIsAcross0", false)
					.setDate("beginTime", AttendUtil.getDate(date, 0))
					.setDate("endTime", AttendUtil.getDate(date, 1))
					.setParameterList("docCreatorIds", userList).list();

			List signedList2 = this.sysAttendCategoryService.getBaseDao().getHibernateSession().createNativeQuery(signedSql + whereTwo)
					.setBoolean("fdIsAcross1", true)
					.setParameterList("docCreatorIds", userList)
					.setDate("nextBegin", AttendUtil.getDate(date, 1))
					.setDate("nextEnd", AttendUtil.getDate(date, 2)).list();

			List signedList = AttendUtil.unionList(signedList1, signedList2);


			for (int i = 0; i < signedList.size(); i++) {
				Object[] record = (Object[]) signedList.get(i);
				JSONObject ret = new JSONObject();
				ret.put("fdId", (String) record[0]);
				Number fdStatus = (Number) record[1];
				ret.put("fdStatus", fdStatus == null ? null : fdStatus.intValue());
				Number fdWorkType = (Number) record[2];
				ret.put("fdWorkType",
						fdWorkType == null ? null : fdWorkType.intValue());
				ret.put("fdWorkId", (String) record[3]);
				Number fdState = (Number) record[4];
				boolean isOk = (fdState != null && fdState.intValue() == 2)
						? true : false;// 异常审批通过为true
				ret.put("fdState", fdState == null ? null : fdState.intValue());
				ret.put("docCreatorId", (String) record[5]);
				Object docCreateTime = record[6];
				if(docCreateTime !=null) {
					if (docCreateTime instanceof Timestamp) {
						ret.put("docCreateTime", ((Timestamp) record[6]).getTime());
					}else{
						ret.put("docCreateTime", DateUtil.convertStringToDate(docCreateTime.toString()).getTime());
					}
				}
				ret.put("fdLocation", record[7] == null ? "" : (String) record[7]);
				ret.put("fdWorkKey", record[8] == null ? "" : (String) record[8]);
				ret.put("fdAppName", record[9] == null ? "" : (String) record[9]);
				ret.put("fdIsAcross", false);
				Object fdIsAcrossTemp = record[10];
				if(fdIsAcrossTemp !=null) {
					if (fdIsAcrossTemp instanceof Boolean) {
						ret.put("fdIsAcross", fdIsAcrossTemp);
					} else {
						//兼容不同数据库对boolean的支持
						ret.put("fdIsAcross", "1".equals(fdIsAcrossTemp.toString()));
					}
				}
				String docCreatorId = (String) record[5];
				if (!records.containsKey(docCreatorId)) {
					records.put(docCreatorId, new ArrayList<JSONObject>());
				}
				List<JSONObject> recordList = records.get(docCreatorId);
				recordList.add(ret);
			}
			TransactionUtils.commit(status);
		}catch (Exception e){
			isException =true;
			e.printStackTrace();
		}finally {
			if(status !=null && isException){
				TransactionUtils.rollback(status);
			}
		}
		return records;
	}

	/**
	 * 获取用户出差,请假,外出 的流程记录
	 * @param userSet 用户列表
	 * @param date 日期
	 * @return 用户id与打卡列表的对应关系
	 * @throws Exception
	 */
	private Map getUserBussList(Set<String> userSet ,
			Date date) throws Exception {
		Map<String, List<JSONObject>> records = new HashMap<String, List<JSONObject>>();
		if(CollectionUtils.isEmpty(userSet)){
			return records;
		}
		List<String> userList = new ArrayList<String>(userSet);
		List<Integer> fdTypes = new ArrayList<Integer>();
		fdTypes.add(4);
		fdTypes.add(5);
		fdTypes.add(7);
		// 出差/请假/外出记录 考虑跨天排班算两天
		List<SysAttendBusiness> allBusList = this.getSysAttendBusinessService()
				.findBussList(userList, AttendUtil.getDate(date, -1), AttendUtil.getDate(date, 2), fdTypes);

		// 筛选有效数据并且去重
		Set<SysAttendBusiness> tempBusSet = new HashSet<SysAttendBusiness>();
		for (String id : userList) {
			List<SysAttendBusiness> tempBusList = this.getSysAttendBusinessService().genUserBusiness(UserUtil.getUser(id), date, allBusList);
			for (SysAttendBusiness bus : tempBusList) {
				tempBusSet.add(bus);
			}
		}
		List<SysAttendBusiness> busList = new ArrayList<>();
		if (!tempBusSet.isEmpty()) {
			busList = new ArrayList<SysAttendBusiness>(tempBusSet);
		}

		for (SysAttendBusiness bus : busList) {
			List<SysOrgElement> targets = bus.getFdTargets();
			for (SysOrgElement ele : targets) {
				JSONObject ret = new JSONObject();
				String docCreatorId = ele.getFdId();
				Long fdBusStartTime = getUserBusTime(bus, true);
				Long fdBusEndTime = getUserBusTime(bus, false);
				if (fdBusStartTime == null || fdBusEndTime == null) {
					continue;
				}
				ret.put("fdBusStartTime", fdBusStartTime);
				ret.put("fdBusEndTime", fdBusEndTime);
				ret.put("fdBusType", bus.getFdType());
				ret.put("fdBusId", bus.getFdId());
				ret.put("fdLeaveType", bus.getFdBusType());// 假期编号
				if (!records.containsKey(docCreatorId)) {
					records.put(docCreatorId, new ArrayList<JSONObject>());
				}
				List<JSONObject> recordList = records.get(docCreatorId);
				recordList.add(ret);
			}
		}
		return records;
	}

	/**
	 * 获取用户出差/请假/外出的开始或结束时间
	 * 
	 * @param buss 流程业务数据
	 * @param isStartTime 是否是取开始日期
	 * @return
	 */
	private Long getUserBusTime(SysAttendBusiness buss, boolean isStartTime) {
		if (buss == null) {
			return null;
		}
		Date startTime = buss.getFdBusStartTime();
		if (isStartTime) {
			return startTime.getTime();
		}
		Date endTime = buss.getFdBusEndTime();
		if (Integer.valueOf(5).equals(buss.getFdType())) {
			// 请假
			if (buss.getFdStatType() != 3) {
				startTime = AttendUtil.getDate(startTime, 0);
				endTime = AttendUtil.getEndDate(endTime, 0); 
			}
			if (buss.getFdStatType() == 2) {
				Integer startNoon = buss.getFdStartNoon();
				Integer endNoon = buss.getFdEndNoon();
				Calendar cal = Calendar.getInstance();
				if (startNoon == 2) {
					cal.setTime(startTime);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					startTime = cal.getTime();
				}
			 	if (endNoon == 1) {
					cal.setTime(endTime);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					endTime = cal.getTime();
			 	}
			}
		}
		return endTime.getTime();
	}

	/**
	 * @param userDatas
	 *            某天用户与打卡记录映射
	 * @param recordList
	 *            某天的所有打卡记录
	 * @return
	 * @throws Exception
	 */
	private Map getUserCategory(Map<String, List<Date>> userDatas,
			List<JSONObject> recordList) throws Exception {
		Map<String, List> cateMap = new HashMap<String, List>();
		for (String orgId : userDatas.keySet()) {
			JSONObject record = getUserInfo(recordList, orgId);
			if (record == null) {
				continue;
			}
			String orgHId = (String) record.get("orgHId");
			String fdCategoryId = (String) record.get("fdCategoryId");

			List idList = new ArrayList();
			if (!cateMap.containsKey(fdCategoryId)) {
				cateMap.put(fdCategoryId, idList);
			}
			idList = cateMap.get(fdCategoryId);
			Map<String, String> orgInfo = new HashMap<String, String>();
			orgInfo.put("orgId", orgId);
			orgInfo.put("orgHId", orgHId);
			idList.add(orgInfo);
		}
		return cateMap;
	}

	private JSONObject getUserInfo(List<JSONObject> recordList, String orgId) {
		for (JSONObject record : recordList) {
			String docCreator = (String) record.get("docCreator");
			if (orgId.equals(docCreator)) {
				return record;
			}
		}
		return null;
	}

	/**
	 * 用户与打卡时间的映射
	 * 
	 * @param recordList
	 * @return
	 * @throws Exception
	 */
	private Map<String, List<Date>> convertUserData(List<JSONObject> recordList)
			throws Exception {
		Map<String, List<Date>> userDatas = new HashMap<String, List<Date>>();
		for (int i = 0; i < recordList.size(); i++) {
			JSONObject json = (JSONObject) recordList.get(i);
			String docCreator = json.getString("docCreator");
			String createTime = json.getString("createTime");
			Date dateTime = DateUtil.convertStringToDate(createTime,
					DATE_TIME_FORMAT_STRING);
			if (!userDatas.containsKey(docCreator)) {
				userDatas.put(docCreator, new ArrayList<Date>());
			}
			List<Date> datas = userDatas.get(docCreator);
			datas.add(dateTime);
		}
		for (String userKey : userDatas.keySet()) {
			List<Date> dateList = userDatas.get(userKey);
			Collections.sort(dateList,
					new Comparator<Date>() {
						@Override
						public int compare(Date o1, Date o2) {
							return o1.compareTo(o2);
						}
					});

		}
		return userDatas;
	}

	private void sortDateList(List<Date> dateList) {
		if (dateList == null || dateList.isEmpty()) {
			return;
		}
		Collections.sort(dateList,
				new Comparator<Date>() {
					@Override
					public int compare(Date o1, Date o2) {
						return o1.compareTo(o2);
					}
				});
	}

	/**
	 * 对打卡记录按天分组
	 * 
	 * @param arrays
	 * @return 返回每天的打卡
	 * @throws Exception
	 */
	private List groupByDate(JSONArray arrays) throws Exception {
		// 缓存考勤组信息
		Map<String, SysAttendCategory> catesMap = new HashMap<String, SysAttendCategory>();
		// 缓存考勤组与用户信息
		Map<String, List<String>> cateUserMap = new HashMap<String, List<String>>();

		List<List<JSONObject>> list = new ArrayList<List<JSONObject>>();
		Map<String, List<JSONObject>> datas = new HashMap<String, List<JSONObject>>();
		// 所有用户标识
		Set<String> orgIds = new HashSet<String>();
		for (int i = 0; i < arrays.size(); i++) {
			JSONObject json = (JSONObject) arrays.get(i);
			String docCreator = json.getString("docCreator");
			if(StringUtil.isNotNull(docCreator)){
				orgIds.add(docCreator);
			}
		}
		List<SysOrgElement> eleList = sysWsOrgService.findSysOrgList(orgIds.toString());
		eleList = formatPersons(eleList);

		for (int i = 0; i < arrays.size(); i++) {
			JSONObject json = (JSONObject) arrays.get(i);
			String docCreator = json.getString("docCreator");
			String createTime = json.getString("createTime");
			SysOrgElement orgEle = findOrgElementByWs(eleList,
					docCreator);
			if (orgEle == null) {
				logger.warn("组织架构不存在,用户id:" + docCreator);
				continue;
			}
			Date dateTime = DateUtil.convertStringToDate(createTime,
					DATE_TIME_FORMAT_STRING);

			String fdCategoryId = sysAttendCategoryService.getCategory(orgEle,dateTime);
			if (StringUtil.isNull(fdCategoryId)) {
				logger.warn("用户不存在考勤组,用户id:" + docCreator);
				continue;
			}
			SysAttendCategory sysAttendCategory = null;
			if (!catesMap.containsKey(fdCategoryId)) {
				sysAttendCategory = CategoryUtil.getCategoryById(fdCategoryId);
				catesMap.put(fdCategoryId, sysAttendCategory);
				List<String> orgList = sysAttendCategoryService.getAttendPersonIds(sysAttendCategory.getFdId(),dateTime);
				cateUserMap.put(fdCategoryId, orgList);
			}
			sysAttendCategory = catesMap.get(fdCategoryId);
			//考勤日期
			Date workDate = getAttendDate(dateTime,sysAttendCategory,orgEle);
			String createDate = DateUtil.convertDateToString(workDate, DateUtil.TYPE_DATE, null);
			if (!datas.containsKey(createDate)) {
				datas.put(createDate, new ArrayList<JSONObject>());
			}
			json.put("workDate", workDate.getTime());
			json.put("fdCategoryId", fdCategoryId);
			json.put("docCreator", orgEle.getFdId());
			json.put("orgHId", orgEle.getFdHierarchyId());
			List<JSONObject> records = datas.get(createDate);
			records.add(json);
		}
		for (String date : datas.keySet()) {
			List<JSONObject> tmpList = datas.get(date);
			if (tmpList != null && !tmpList.isEmpty()) {
				list.add(tmpList);
			}
		}
		return list;
	}

	private List formatPersons(List<SysOrgElement> orgList) throws Exception {
		boolean isPerson = true;
		if (orgList != null && !orgList.isEmpty()) {
			List expendList = new ArrayList();
			List personList = new ArrayList();
			for (int i = 0; i < orgList.size(); i++) {
				SysOrgElement org = (SysOrgElement) orgList.get(i);
				if (org.getFdOrgType() != ORG_TYPE_PERSON) {
					expendList.add(org);
					isPerson = false;
				} else {
					personList.add(sysOrgCoreService.format(org));
				}
			}
			if (!isPerson) {
				expendList = AttendPersonUtil.expandToPerson(expendList);
			}
			if (!expendList.isEmpty()) {
				personList.addAll(expendList);
			}
			orgList = personList;
		}
		return orgList;
	}

	public static SysOrgElement findOrgElementByWs(List<SysOrgElement> orgList,
			String personInfo) {
		com.alibaba.fastjson.JSONObject jsonObj = com.alibaba.fastjson.JSONArray.parseObject(personInfo);
		String id = jsonObj.containsKey("Id")?jsonObj.getString("Id"):null;
		String loginName =jsonObj.containsKey("LoginName")? jsonObj.getString("LoginName"):null;
		String personNo =jsonObj.containsKey("PersonNo")? jsonObj.getString("PersonNo"):null;
		for (SysOrgElement ele : orgList) {
			SysOrgPerson person = (SysOrgPerson) ele;
			if(id !=null && id.equals(person.getFdId())) {
				return ele;
			}
			if(loginName !=null && loginName.equals(person.getFdLoginName())) {
				return ele;
			}
			if(personNo !=null && personNo.equals(person.getFdNo())) {
				return ele;
			}
		}
		return null;
	}

	private SysOrgElement parseOrgToPerson(String jsonPerson)
			throws Exception {
		if (StringUtil.isNull(jsonPerson)) {
			return null;
		}
		SysOrgElement tmpOrg = sysWsOrgService
				.findSysOrgElement(jsonPerson);
		return tmpOrg;
	}

	private boolean checkNullIfNecessary(Object context, String methodKey,
			SysAttendResult result) throws Exception {
		if (null == context) {
			result.setMessage(ResourceUtil.getString(
					"sysNotifyTodo.webservice.warning.context", "sys-attend"));
			logger.debug("考勤服务上下文为空!");
			return false;
		}
		String fields = "";
		if (METHOD_CONSTANT_NAME_ADDATTEND.equalsIgnoreCase(methodKey)) {
			fields = "appName;dataType;datas";
		}
		String[] fileArr = fields.split(";");
		for (int i = 0; i < fileArr.length; i++) {
			if (isNullProperty(context, fileArr[i])) {
				String filedName = ResourceUtil.getString(
						"sysAttendmain.webservice." + fileArr[i], "sys-attend");
				result.setMessage(ResourceUtil.getString(
						"sysAttendmain.webservice.warning.property",
						"sys-notify", null,
						new Object[] { methodKey, filedName }));
				logger.debug("方法" + methodKey + "中,不允许考勤服务上下文中\"" + filedName
						+ "\"信息为空!");
				return false;
			}
		}
		return true;
	}

	private boolean isNullProperty(Object obj, String name) throws Exception {
		Object tmpObj = PropertyUtils.getProperty(obj, name);
		if (tmpObj instanceof String) {
			return StringUtil.isNull((String) tmpObj)
					|| "null".equalsIgnoreCase((String) tmpObj);
		} else if (tmpObj instanceof Integer) {
			return ((Integer) tmpObj) == 0;
		} else {
			return tmpObj == null;
		}
	}

	/**
	 * 生成排班打卡记录
	 * @param ele 人员
	 * @param dateList 打卡时间列表
	 * @param signedRecordList 打卡记录
	 * @param mainList 生成结果数据封装
	 * @param date 日期
	 * @param signTimeList 排班时间的配置列表
	 * @param records 用户的打卡记录对应Map key为用户id
	 * @throws Exception
	 */
	private void genTimeAttendMain(SysOrgElement ele,List<Date> dateList,
			List<JSONObject> signedRecordList, List<JSONObject> mainList,
			Date date, List<Map<String, Object>> signTimeList,Map<String, List<JSONObject>> records )
			throws Exception {
		//打卡时间的班次 封装
		this.sysAttendCategoryService.doWorkTimesRender(signTimeList,signedRecordList);
		//日期去重
		Set<Date> allTempDates =new HashSet<>();
		allTempDates.addAll(dateList);
		dateList = Lists.newArrayList(allTempDates);

		this.sortDateList(dateList);
		if (CollectionUtils.isNotEmpty(signTimeList)) {
			/**
			 * 根据排班班次的打卡配置 来封装成有效的打卡记录
			 */
			Boolean isTimeAreNew =Boolean.FALSE;
			/** 1) 根据所有的打卡记录遍历，在某个班次区间，则属于某个班次*/
			Map<String,List<Date>> workConfigAndSignDateMap=new HashMap<>(signTimeList.size());
			Map<String,List<Map<String, Object>>> workConfigMap=new HashMap<>();
			List<Date> yesterdays=new ArrayList<>();
			/** 2) 封装每个班次内的打卡时间 */
			Date workDate = AttendUtil.getDate(date, 0);
			SysAttendCategory category =null;
			Date yesterday = AttendUtil.getDate(workDate, -1);
			for(Map<String, Object> workConfig:signTimeList){
				isTimeAreNew =(Boolean) workConfig.get("isTimeAreNew");
				if(isTimeAreNew !=null && Boolean.TRUE.equals(isTimeAreNew)) {
					//班次的唯一标识
					String fdWorkTimeId = (String) workConfig.get("fdWorkTimeId");
					//同一个班次所有的打卡记录
					List<Date> workSignDateList =workConfigAndSignDateMap.get(fdWorkTimeId);
					if(workSignDateList ==null){
						workSignDateList = new ArrayList<>();
					}
					for (Date createTime : dateList) {
						Date checkDate = sysAttendCategoryService.getTimeAreaDateOfDate(createTime,workDate, workConfig);
						if(checkDate !=null) {
							//在同一个最早最晚打卡时间，则认定为同一个班次
							if(!workSignDateList.contains(createTime)) {
								workSignDateList.add(createTime);
							}
							continue;
						}
						//如果班次是跨天。则计算在昨日的考勤中
						if(!yesterdays.contains(createTime)){
							String categoryId =sysAttendCategoryService.getAttendCategory(ele,yesterday);
							if(StringUtil.isNotNull(categoryId)) {
								category = CategoryUtil.getCategoryById(categoryId);
								//查询人员昨日的排班情况
								List<Map<String, Object>> yesterSignTimeList = sysAttendCategoryService.getAttendSignTimes(category, yesterday, ele);
								if (yesterSignTimeList.isEmpty()) {
									continue;
								}
								Date yesterDay = sysAttendCategoryService.getTimeAreaDateOfDate(createTime, yesterday, yesterSignTimeList);
								if (yesterDay != null) {
									//在昨日跨天的打卡范围内
									yesterdays.add(createTime);
									continue;
								}
							}
						}
					}
					List<Map<String, Object>> workConfigListTemp =workConfigMap.get(fdWorkTimeId);
					if(workConfigListTemp ==null) {
						workConfigListTemp = new ArrayList<>();
					}
					workConfigListTemp.add(workConfig);
					//班次 的上下班配置
					workConfigMap.put(fdWorkTimeId,workConfigListTemp);
					//班次的所有打卡时间
					workConfigAndSignDateMap.put(fdWorkTimeId,workSignDateList);
				} else {
					break;
				}
			}
			if(CollectionUtils.isNotEmpty(yesterdays) && category !=null){
				//重新生成属于是昨日的考勤记录
				dateList.removeAll(yesterdays);
				Map<String, List<Date>> userSignDatas=new HashMap<>();
				userSignDatas.put(ele.getFdId(),yesterdays);
				List<JSONObject> yesterdaysMainList = genTimeRecords(userSignDatas,category , yesterday, ele ,records,yesterdays);
				if(CollectionUtils.isNotEmpty(yesterdaysMainList)){
					mainList.addAll(yesterdaysMainList);
				}
			}
			if(Boolean.TRUE.equals(isTimeAreNew)) {
				/** 3） 根据班次以及对应的打卡记录 封装成有效考勤记录 */
				for (Map.Entry<String, List<Map<String, Object>>> workConfigTemp: workConfigMap.entrySet()) {
					//一个班次 范围内的所有上下班时间处理、该时间在上面封装的时候去重了。重复时间不处理
					List<Date> dates = workConfigAndSignDateMap.get(workConfigTemp.getKey());
					if(CollectionUtils.isEmpty(dates)){
						continue;
					}
					this.sortDateList(dates);
					//每个班次 的上下班为一个列表
					List<Map<String, Object>> tempList = workConfigTemp.getValue();
					if(CollectionUtils.isNotEmpty(tempList)){

						//一个班次只有上下班两个
						Map<String, Object> goWorkTime =null;
						//下班
						Map<String, Object> outWorkTime =null;
						for (Map<String, Object> workTime:tempList) {
							//0是上班，1是下班
							Integer fdWorkType = (Integer) workTime.get("fdWorkType");
							if (Integer.valueOf(0).equals(fdWorkType)) {
								goWorkTime =workTime;
							} else if (Integer.valueOf(1).equals(fdWorkType)) {
								outWorkTime =workTime;
							}
						}
						if(goWorkTime !=null && outWorkTime !=null){
							//已经存在的打卡记录，打卡记录过滤班次信息 上班时间
							JSONObject goWorkRecord = getSignRecord(signedRecordList, goWorkTime);
							if(goWorkRecord !=null) {
								removeLeaveTime(goWorkRecord, dates);
								putAttendMain(goWorkRecord, goWorkTime, mainList,category);
								//删除缺卡
								removeDefaultTime(goWorkRecord,dates);
							}
							//已经存在的打卡记录，打卡记录过滤班次信息  下班时间
							JSONObject outWorkRecord = getSignRecord(signedRecordList, outWorkTime);
							if(outWorkRecord !=null) {
								removeLeaveTime(outWorkRecord, dates);
								putAttendMain(outWorkRecord, outWorkTime, mainList,category);
								//删除缺卡
								removeDefaultTime(outWorkRecord,dates);
							}
							if(CollectionUtils.isEmpty(dates)){
								continue;
							}
							/** 拿时间计算*/
							//标准的上班时间
							int goWorkTimeMis = getShouldOnWorkTime(goWorkTime);
							//上班的打卡时间
							Date pSignTime = (Date) goWorkTime.get("signTime");
							//标准的下班时间
							int outSignTimeMin = getShouldOffWorkTime(pSignTime, outWorkTime);
							//当前班次是否是支持跨天
							boolean isAcrossDay = (Boolean) outWorkTime.get("isAcrossDay");
							//打卡时间是否属于上班班次
							boolean isHaveGoWorkTime =false;

							//计算上班的打卡时间--取最早的打卡时间
							Date createTime = dates.get(0);
							int _createTime = createTime.getHours() * 60 + createTime.getMinutes();

							//最早打卡时间
							Date fdStartTime = (Date) goWorkTime.get("fdStartTime");
							int _beginSignTime = fdStartTime.getHours() * 60 + fdStartTime.getMinutes();

							//最晚的一次打卡时间
							Date overTime = dates.get(dates.size()-1);
							int status =0;

							if(_createTime >= _beginSignTime && _createTime <= goWorkTimeMis){
								//大于等于最早开始时间，小于等于标准 上班时间，则认为是正常打卡
								status =1;
								isHaveGoWorkTime =true;
							} else if(_createTime > goWorkTimeMis && _createTime < outSignTimeMin ){
								//最早一次的打卡时间 大于标准上班时间，小于标准下班时间 则认定是迟到
								status =2;
								isHaveGoWorkTime =true;
							}
							if(isHaveGoWorkTime) {
								//上班打卡的有效记录封装
								if(category!=null&&category.getFdShiftType() == 3){
									//正常
									status = 1;
								}

								addAttendMains(goWorkRecord, status, createTime, goWorkTime, mainList,category);
								if (createTime.getTime() == overTime.getTime()) {
									//只有一条打卡时间记录时，上下班时间相同。并且归属上班时间范围内。则不处理下班时间
									continue;
								}
							}
							boolean isNextDay =false;
							//下班的打卡时间 计算
							int _overTime = overTime.getHours() * 60 + overTime.getMinutes();
							// 下班计算 跨天打卡 加上24小时 是跨天，并且下班打卡时间是在第二天
							if (isAcrossDay && overTime.getTime() >= AttendUtil.getDate(date, 1).getTime()) {
								_overTime = _overTime + 24 * 60;
								isNextDay =true;
							}
							//下班时间必须在上班标准打卡时间之后
							if(_overTime > goWorkTimeMis){
								if(isHaveGoWorkTime) {
									//主要计算 弹性时间
									//获取上班打卡的时间
									Date goWorkOverTime = getAttendMainSignTime(goWorkTime, mainList);
									//下班标准打卡时间
									outSignTimeMin = getShouldOffWorkTime(goWorkOverTime, outWorkTime);
								}
								int outStatus = 0;
								//下班打卡时间在标准下班时间之后
								if (_overTime >= outSignTimeMin) {
									//正常下班
									outStatus = 1;
								}
								if ((isHaveGoWorkTime || isNextDay )&& _overTime < outSignTimeMin) {
									//早退下班
									outStatus = 3;
								}
								if (outStatus > 0) {
									addAttendMains(outWorkRecord, outStatus, overTime, outWorkTime, mainList,category);
								}
							}
						}
					}
				}
			} else {
				//非新配置的排班，则按照原来的逻辑处理。兼容历史逻辑 2021-12-01 王京。以下逻辑将要废弃，因为下面的逻辑不正确
				// 第一班上班时间 配置
				Map<String, Object> workTime1 = signTimeList.get(0);
				// 最后一般下班时间 配置
				Map<String, Object> lastWorkTime = signTimeList.get(signTimeList.size() - 1);
				// 最后一班上班时间 配置
				Map<String, Object> lastWorkTimeOn = signTimeList.get(signTimeList.size() - 2);

				Map<String, Object> workTime2 = signTimeList.get(1);
				JSONObject record2 = getSignRecord(signedRecordList, workTime2);
				putAttendMain(record2, workTime2, mainList,category);
				Map<String, Object> workTime3 = null;
				JSONObject record3 = null;
				Map<String, Object> workTime4 = null;
				JSONObject record4 = null;
				Map<String, Object> workTime5 = null;
				JSONObject record5 = null;
				Map<String, Object> workTime6 = null;
				JSONObject record6 = null;
				if (signTimeList.size() >= 4) {
					workTime3 = signTimeList.get(2);
					record3 = getSignRecord(signedRecordList,
							workTime3);
					removeLeaveTime(record3, dateList);
					putAttendMain(record3, workTime3, mainList,category);

					workTime4 = signTimeList.get(3);
					record4 = getSignRecord(signedRecordList,
							workTime4);
					removeLeaveTime(record4, dateList);
					putAttendMain(record4, workTime4, mainList,category);
				}

				if (signTimeList.size() >= 6) {
					// 三班次
					workTime5 = signTimeList.get(4);
					record5 = getSignRecord(signedRecordList,
							workTime5);
					removeLeaveTime(record5, dateList);
					putAttendMain(record5, workTime5, mainList,category);

					workTime6 = signTimeList.get(5);
					record6 = getSignRecord(signedRecordList,
							workTime6);
					removeLeaveTime(record6, dateList);
					putAttendMain(record6, workTime6, mainList,category);
				}

				// 已打卡记录中第一班上班有效打卡记录（不正常优先）
				JSONObject record1 = getSignRecord(signedRecordList, workTime1);
				// 已打卡记录中最后一班下班有效打卡记录（不正常优先）
				JSONObject lastRecord = getSignRecord(signedRecordList,
						lastWorkTime);
				removeLeaveTime(record1, dateList);
				removeLeaveTime(lastRecord, dateList);
				// 往mainList里面添加record信息
				putAttendMain(record1, workTime1, mainList,category);
				putAttendMain(lastRecord, lastWorkTime, mainList,category);
				// 缺卡时间列表
				List<Date> userNoSignedTimeList = getUserNoSignedList(
						signedRecordList);
				formatDateList(dateList, userNoSignedTimeList);
				// 迟到/早退规则
				Integer fdLateTime = (Integer) workTime1.get("fdLateTime");
				Integer fdLeftTime = (Integer) workTime1.get("fdLeftTime");

				// 第一班上班打卡时间
				Date signTime1 = (Date) workTime1.get("signTime");
				int signTimeMin1 = signTime1.getHours() * 60
						+ signTime1.getMinutes();

				// 最后一班下班打卡时间
				Date lastSignTime = (Date) lastWorkTime.get("signTime");
				int lastSignTimeMin = lastSignTime.getHours() * 60
						+ lastSignTime.getMinutes();


				// 是否支持跨天
				boolean isAcrossDay = (Boolean) workTime1.get("isAcrossDay");
				// 第一班上班最早/最晚打卡时间
				Date fdStartTime = (Date) workTime1.get("fdStartTime");
				Date fdEndTime = (Date) workTime1.get("fdEndTime");
				// 为空 最早打卡时间为00:00
				if (fdStartTime == null) {
					fdStartTime = AttendUtil.getDate(new Date(), 0);
				}
				// 为空则最晚打卡时间为23:00
				if (fdEndTime == null) {
					fdEndTime = AttendUtil.getEndDate(new Date(), 0);
				}
				// 实际最早/最晚打卡时间戳
				fdStartTime = AttendUtil.joinYMDandHMS(date, fdStartTime);
				fdEndTime = AttendUtil.joinYMDandHMS(
						AttendUtil.getDate(date, isAcrossDay ? 1 : 0), fdEndTime);
				int fdStartTimeMin = fdStartTime.getHours() * 60
						+ fdStartTime.getMinutes();
				int fdEndTimeMin = fdEndTime.getHours() * 60
						+ fdEndTime.getMinutes();

				for (Date createTime : dateList) {
					int _createTime = createTime.getHours() * 60
							+ createTime.getMinutes();
					if (createTime.before(fdStartTime)
							|| createTime.after(fdEndTime)) {
						continue;
					}
					// 跨天打卡 加上24小时
					if (isAcrossDay && !createTime.before(AttendUtil.getDate(date, 1))) {
						_createTime = _createTime + 24 * 60;
					}

					int onTempDate1 = getShouldOnWorkTime(workTime1);
					Date offLastTime = getAttendMainSignTime(lastWorkTimeOn, mainList);
					lastSignTimeMin = getShouldOffWorkTime(offLastTime,
							lastWorkTime);
					if (_createTime <= onTempDate1
							&& _createTime >= fdStartTimeMin) {// 上班时间
						addAttendMains(record1, 1, createTime, workTime1,
								mainList,category);
					} else if (_createTime >= lastSignTimeMin) {// 下班
						addAttendMains(lastRecord, 1, createTime,
								lastWorkTime, mainList,category);

					} else {
						if (signTimeList.size() == 2) {// 一班
							// 上下班时间范围内
							addAttendMains(record1, 2, createTime, workTime1,
									mainList,category);

							// 下班
							Date signedTime1 = getAttendMainSignTime(workTime1,
									mainList);
							int nowStatus = 3;
							int tempDate = getShouldOffWorkTime(signedTime1,
									lastWorkTime);
							if (_createTime >= tempDate) {
								nowStatus = 1;
							}
							if (signedTime1 != null
									&& !signedTime1.equals(createTime)) {
								addAttendMains(lastRecord, nowStatus, createTime,
										lastWorkTime, mainList,category);
							}
						}
						if (signTimeList.size() >= 4) {// 二班
							// 一班次最晚打卡时间
							Date fdEndTime1 = (Date) workTime1.get("fdEndTime1");
							// 二班次最早打卡时间
							Date fdStartTime2 = (Date) workTime1
									.get("fdStartTime2");

							Date signTime2 = (Date) workTime2.get("signTime");
							int signTimeMin2 = signTime2.getHours() * 60
									+ signTime2.getMinutes();


							Date signTime3 = (Date) workTime3.get("signTime");
							int signTimeMin3 = signTime3.getHours() * 60
									+ signTime3.getMinutes();


							Date signTime4 = (Date) workTime4.get("signTime");
							int signTimeMin4 = signTime4.getHours() * 60
									+ signTime4.getMinutes();

							Date signTime6 = signTimeList.size() >= 6
									? (Date) workTime6.get("signTime") : null;
							int signTimeMin6 = signTimeList.size() >= 6
									? (signTime6.getHours() * 60
									+ signTime6.getMinutes())
									: 0;

							if (_createTime > onTempDate1
									&& _createTime < signTimeMin2) {// 早班时间区间
								addAttendMains(record1, 2, createTime, workTime1,
										mainList,category);

								// 下班
								Date pSignTime = null;
								Long createTime1 = getAttendMainCreateTime(
										workTime1, mainList);
								if (createTime1 != null) {
									pSignTime = new Date(createTime1);
								}
								int nowStatus = 3;
								if (_createTime >= getShouldOffWorkTime(pSignTime,
										workTime2)) {
									nowStatus = 1;
								}
								if (pSignTime != null
										&& !pSignTime.equals(createTime)) {
									addAttendMains(record2, nowStatus, createTime,
											workTime2, mainList,category);
								}
							}
							if (_createTime >= signTimeMin2
									&& _createTime < signTimeMin3) {// 休息时间区间
								Date pSignTime = getAttendMainSignTime(workTime1,
										mainList);
								int nowStatus = 3;
								if (_createTime >= getShouldOffWorkTime(pSignTime,
										workTime2)) {
									nowStatus = 1;
								}


								Integer status2 = getAttendMainStatus(workTime2,
										mainList);
								Integer status3 = getAttendMainStatus(workTime3,
										mainList);
								// 当前班次未打卡或早退时或者下一班次打卡正常
								if (status2 == null || status2 == 0
										|| status2 == 3) {
									addAttendMains(record2, nowStatus,
											createTime,
											workTime2, mainList,category
									);
								} else if (status3 != null && status3 == 1) {
									Date pSignTime3 = getAttendMainSignTime(
											workTime3, mainList);
									if (pSignTime3 != null
											&& !pSignTime3.equals(createTime)) {
										addAttendMains(record2, nowStatus,
												createTime,
												workTime2, mainList,category);
									}
								}
								// 午班上班
								Date pSignTime2 = getAttendMainSignTime(workTime2,
										mainList);
								if (pSignTime2 != null
										&& !pSignTime2.equals(createTime)) {
									// 当前班次未打卡或迟到时
									if (status3 == null || status3 == 0
											|| status3 == 2) {
										addAttendMains(record3, 1, createTime,
												workTime3, mainList,category);
									}
								}
							}
							if (signTimeList.size() == 4) {
								signTimeMin4 = getShouldOffWorkTime(signTime3,
										workTime4);
							}
							if (_createTime >= signTimeMin3
									&& _createTime < signTimeMin4) {
								// 晚班时间区间
								int nowStatus = 1;
								if (_createTime > getShouldOnWorkTime(workTime3)) {
									nowStatus = 2;
								}
								addAttendMains(record3, nowStatus, createTime,
										workTime3, mainList,category);

								// 下班打卡
								Date pSignTime3 = getAttendMainSignTime(workTime3,
										mainList);
								int nowStatus4 = 3;
								int tempDate = getShouldOffWorkTime(pSignTime3,
										workTime4);
								if (_createTime >= tempDate) {
									nowStatus4 = 1;
								}
								if (pSignTime3 != null
										&& !pSignTime3.equals(createTime)) {
									addAttendMains(record4, nowStatus4, createTime,
											workTime4, mainList,category);
								}
							}
							Date signTime5 = signTimeList.size() >= 6
									? (Date) workTime5.get("signTime") : null;
							int signTimeMin5 = signTimeList.size() >= 6
									? (signTime5.getHours() * 60
									+ signTime5.getMinutes())
									: 0;
							if (signTimeList.size() == 6) {
								signTimeMin6 = getShouldOffWorkTime(signTime5,
										workTime6);
							}
							if (signTimeList.size() >= 6
									&& _createTime >= signTimeMin4
									&& _createTime < signTimeMin5) {// 三班次
								// 休息区间
								Date pSignTime = getSignedTime(record3);
								int nowStatus = 3;
								if (_createTime >= getShouldOffWorkTime(pSignTime,
										workTime4)) {
									nowStatus = 1;
								}

								Integer status4 = getAttendMainStatus(workTime4,
										mainList);
								Integer status5 = getAttendMainStatus(workTime5,
										mainList);
								// 当前班次未打卡或早退时或者下一班次打卡正常
								if (status4 == null || status4 == 0
										|| status4 == 3) {
									addAttendMains(record4, nowStatus,
											createTime,
											workTime4, mainList,category);
								} else if (status5 != null && status5 == 1) {
									Date pSignTime5 = getAttendMainSignTime(
											workTime5,
											mainList);
									if (pSignTime5 != null
											&& !pSignTime5.equals(createTime)) {
										addAttendMains(record4, nowStatus,
												createTime,
												workTime4, mainList,category);
									}
								}
								Date pSignTime4 = getAttendMainSignTime(workTime4,
										mainList);
								// 午班上班
								if (pSignTime4 != null
										&& !pSignTime4.equals(createTime)) {
									// 当前班次未打卡或迟到时
									if (status5 == null || status5 == 0
											|| status5 == 2) {
										addAttendMains(record5, 1, createTime,
												workTime5, mainList,category);
									}
								}
							}
							if (signTimeList.size() >= 6
									&& _createTime >= signTimeMin5
									&& _createTime < signTimeMin6) {
								// 晚班时间区间
								int nowStatus = 1;
								if (_createTime > getShouldOnWorkTime(workTime5)) {
									nowStatus = 2;
								}
								addAttendMains(record5, nowStatus, createTime,
										workTime5, mainList,category);

								// 下班打卡
								Date pSignTime5 = getSignedTime(record5);
								int nowStatus6 = 3;
								int tempDate = getShouldOffWorkTime(pSignTime5,
										workTime6);
								if (_createTime >= tempDate) {
									nowStatus6 = 1;
								}
								addAttendMains(record6, nowStatus6, createTime,
										workTime6, mainList,category);
							}
						}
					}
				}
				// 数据校验
				if (signTimeList.size() == 2) {
					Long createTime1 = getAttendMainCreateTime(workTime1, mainList);
					Long createTime2 = getAttendMainCreateTime(signTimeList.get(1),
							mainList);
					if (createTime1 != null && createTime2 != null
							&& createTime1.longValue() == createTime2.longValue()) {
						mainList.remove(
								getAttendMain(signTimeList.get(1), mainList));
					}
				}
				if (signTimeList.size() >= 4) {
					Long createTime1 = getAttendMainCreateTime(workTime1, mainList);
					Long createTime2 = getAttendMainCreateTime(signTimeList.get(1),
							mainList);
					Long createTime3 = getAttendMainCreateTime(signTimeList.get(2),
							mainList);
					Long createTime4 = getAttendMainCreateTime(signTimeList.get(3),
							mainList);

					// Map<String, Object> workTime2 = signTimeList.get(1);
					Date signTime2 = (Date) workTime2.get("signTime");
					int signTimeMin2 = signTime2.getHours() * 60
							+ signTime2.getMinutes();
					// Map<String, Object> workTime3 = signTimeList.get(2);
					// JSONObject record3 = getSignRecord(signedRecordList,
					// workTime3);
					Date signTime3 = (Date) workTime3.get("signTime");
					int signTimeMin3 = signTime3.getHours() * 60
							+ signTime3.getMinutes();

					// Map<String, Object> workTime4 = signTimeList.get(3);
					Date signTime4 = (Date) workTime4.get("signTime");
					int signTimeMin4 = signTime4.getHours() * 60
							+ signTime4.getMinutes();
					Integer status1 = getAttendMainStatus(workTime1, mainList);
					Integer status2 = getAttendMainStatus(workTime2, mainList);
					Integer status3 = getAttendMainStatus(workTime3, mainList);
					//当两班制时，如果一班制未打卡或请假/出差/外出，同步时则将下午的第一次打卡默认为第二班制的上班卡，而不是一班的下班卡。
					if (createTime2 != null && (((status1 == null || status1 == 0) && (status2 != null && status2 == 1)) || (AttendUtil.isAttendBuss(status1 + "") && AttendUtil.isAttendBuss(status2 + ""))) && (status3 == null || status3 == 0)) {
						Date createTime = new Date(createTime2);
						int _createTime = createTime.getHours() * 60
								+ createTime.getMinutes();
						if ((_createTime >= signTimeMin2
								&& _createTime <= signTimeMin3)) {
							//二班时间区间
							int nowStatus = 1;
							if (_createTime > getShouldOnWorkTime(workTime3)) {
								nowStatus = 2;
							}
							addAttendMains(record3, nowStatus, createTime, workTime3,
									mainList,category);
							JSONObject json2 = getAttendMain(workTime2, mainList);
							if (json2 != null) {
								json2.put("docCreateTime",
										AttendUtil.joinYMDandHMS(date, signTime2).getTime());
								json2.put("fdStatus",
										AttendUtil.isAttendBuss(status2 + "") ? status2 : 0);
							}
						}
					}
					createTime2 = getAttendMainCreateTime(workTime2, mainList);
					createTime3 = getAttendMainCreateTime(workTime3, mainList);

					if (createTime1 != null && createTime2 != null
							&& createTime1.longValue() == createTime2.longValue()) {
						mainList.remove(
								getAttendMain(signTimeList.get(1), mainList));
					}
					if (createTime3 != null && createTime4 != null
							&& createTime4.longValue() == createTime3.longValue()) {
						mainList.remove(
								getAttendMain(signTimeList.get(3), mainList));
					}

					if (createTime2 != null && createTime3 != null
							&& createTime2.longValue() > createTime3.longValue()) {
						getAttendMain(signTimeList.get(2), mainList).put(
								"docCreateTime",
								createTime2);
						getAttendMain(signTimeList.get(1), mainList).put(
								"docCreateTime",
								createTime3);
					}
					if (createTime2 != null && createTime3 != null
							&& createTime2.longValue() == createTime3.longValue()) {
						if (status1 == null || status1 == 0) {
							// JSONObject record2 = getSignRecord(signedRecordList,
							// workTime2);
							mainList.remove(getAttendMain(workTime2, mainList));
							removePatchDate(workTime2, mainList, record2,
									new Date(createTime2),category);
						} else {
							mainList.remove(getAttendMain(workTime3, mainList));
						}
					}
					//
					if (signTimeList.size() >= 6) {
						Long createTime5 = getAttendMainCreateTime(
								signTimeList.get(4), mainList);
						Long createTime6 = getAttendMainCreateTime(
								signTimeList.get(5), mainList);

						if (createTime5 != null && createTime6 != null
								&& createTime5.longValue() == createTime6
								.longValue()) {
							mainList.remove(
									getAttendMain(signTimeList.get(5), mainList));
						}
						if (createTime4 != null && createTime5 != null) {
							if (createTime4.longValue() > createTime5
									.longValue()) {
								getAttendMain(signTimeList.get(3), mainList).put(
										"docCreateTime",
										createTime5);
								getAttendMain(signTimeList.get(4), mainList).put(
										"docCreateTime",
										createTime4);
							}
							if (createTime4.longValue() == createTime5
									.longValue()) {
								if (status3 == null || status3 == 0) {
									mainList.remove(
											getAttendMain(signTimeList.get(3),
													mainList));
								} else {
									mainList.remove(
											getAttendMain(signTimeList.get(4),
													mainList));
								}
							}
						}

					}
				}
			}
		}
	}

	/**
	 * 移除一条最终结果的有效数据要添加一条数据库原有记录
	 * @param workTime
	 * @param mainList
	 * @param record
	 * @param date
	 */
	private void removePatchDate(Map workTime, List<JSONObject> mainList,
			JSONObject record, Date date,SysAttendCategory category) {
		if (record == null) {
			return;
		}
		Integer status = (Integer) record.get("fdStatus");
		addAttendMains(record, status, null, workTime, mainList,category);
		JSONObject json2 = getAttendMain(workTime, mainList);
		if (json2 != null) {
			Integer status2 = (Integer) json2.get("fdStatus");
			if (status2 == 0) {
				Date signTime = (Date) workTime.get("signTime");
				json2.put("docCreateTime",AttendUtil.joinYMDandHMS(date, signTime).getTime());
			}
		}
	}

	@Override
	public void onApplicationEvent(Event_Common event) {
		try {
			//导入原始记录
			if ("importOriginAttendMain".equals(event.getSource().toString())) {
				Map params = ((Event_Common) event).getParams();
				if (null == params || params.size() <= 0) {
					return;
				}
				JSONArray datas = null;
				if (params != null && params.containsKey("datas")) {
					datas = (JSONArray) params
							.get("datas");
				}
				SysAttendImportLog sysAttendImportLog = null;
				if (params != null && params.containsKey("sysAttendImportLog")) {
					sysAttendImportLog = (SysAttendImportLog) params
							.get("sysAttendImportLog");
				}
				String appName = null;
				if (params != null && params.containsKey("appName")) {
					appName = (String) params.get("appName");
				}
				String operatorType = null;
				if (params != null && params.containsKey("operatorType")) {
					operatorType = (String) params.get("operatorType");
				}
				RegenAttendThread task = new RegenAttendThread();
				task.setDatas(datas);
				task.setAppName(appName);
				task.setOperatorType(operatorType);
				task.setSysAttendImportLog(sysAttendImportLog);
				AttendThreadPoolManager manager = AttendThreadPoolManager
						.getInstance();
				if (!manager.isStarted()) {
					manager.start();
				}
				manager.submit(task);
				
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
	}
	
	class RegenAttendThread implements Runnable {
		// 用户打卡记录集合
		JSONArray datas = new JSONArray();
		SysAttendImportLog sysAttendImportLog=null;
		String appName;
		String operatorType;

		public SysAttendImportLog getSysAttendImportLog() {
			return sysAttendImportLog;
		}

		public void setSysAttendImportLog(SysAttendImportLog sysAttendImportLog) {
			this.sysAttendImportLog = sysAttendImportLog;
		}

		@Override
		public void run() {
			TransactionStatus status = null;
			try {
				status = TransactionUtils.beginNewTransaction();
				addAttendMain(appName,datas,operatorType,sysAttendImportLog);
				TransactionUtils.getTransactionManager().commit(status);
			} catch (Exception e) {
				e.printStackTrace();
				if (status != null) {
					TransactionUtils.getTransactionManager()
							.rollback(status);
				}
				try {
					if(sysAttendImportLog!=null) {
						sysAttendImportLog.setFdStatus(3);
						sysAttendImportLogService.update(sysAttendImportLog);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		public JSONArray getDatas() {
			return datas;
		}

		public void setDatas(JSONArray datas) {
			this.datas = datas;
		}
		public String getAppName() {
			return appName;
		}

		public void setAppName(String appName) {
			this.appName = appName;
		}

		public String getOperatorType() {
			return operatorType;
		}

		public void setOperatorType(String operatorType) {
			this.operatorType = operatorType;
		}
	}
}
