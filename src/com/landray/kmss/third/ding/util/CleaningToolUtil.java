package com.landray.kmss.third.ding.util;

import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.util.SpringBeanUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.slf4j.Logger;

import com.dingtalk.api.request.OapiProcessWorkrecordTaskQueryRequest;
import com.dingtalk.api.request.OapiWorkrecordGetbyuseridRequest;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse;
import com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.util.SecureUtil;
import com.landray.kmss.util.StringUtil;

public class CleaningToolUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CleaningToolUtil.class);

	private static ISysNotifyTodoService sysNotifyTodoService = null;

	private static final String TODO_TYPE_TODO = "待处理";
	private static final String TODO_TYPE_DELETE = "已移除";

	private static final String WORK_TODO = "01"; //工作流待办
	private static final String WORK_DELETE= "02"; //工作流移除的待办
	private static final String WR_TODO= "11"; //待办任务1.0待办
	private static final String WR_DELETE= "12"; //待办任务1.0移除的待办
	private static final String TASK_TODO= "21"; //待办任务2.0待办

	public static final String API_WORK= "工作流接口"; //工作流待办接口
	public static final String API_TODO_1= "待办任务接口V1.0"; //1.0待办接口
	public static final String API_TODO_2= "待办任务接口V2.0"; //2.0待办接口


	private static ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}

	/**
	 * 获取工作流待办列表
	 */
	public static void getQueryWork(String userid, long pg, long status,
									Map<String, com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo> voMap,
									String ekpUserId, StringBuffer errBuffer)
			throws Exception {

		ThirdDingTalkClient client = new ThirdDingTalkClient(
				DingConstant.DING_PREFIX
						+ "/topapi/process/workrecord/task/query"
						+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId));
		OapiProcessWorkrecordTaskQueryRequest req = new OapiProcessWorkrecordTaskQueryRequest();
		req.setUserid(userid);
		req.setOffset(pg * 20L);
		req.setCount(20L);
		req.setStatus(status);
		OapiProcessWorkrecordTaskQueryResponse response = client.execute(req,
				DingUtils.dingApiService.getAccessToken());
		if (response.getErrcode() == 0) {
			com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.PageResult result = response
					.getResult();
			List<com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo> vos = result
					.getList();
			if (vos != null) {
				for (com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo vo : vos) {
					voMap.put(vo.getTaskId(), vo);
				}
				if (result.getHasMore()) {
					getQueryWork(userid, pg + 1,status, voMap, ekpUserId, errBuffer);
				}
			}
		} else {
			errBuffer.append(response.getBody());
			logger.error(response.getBody());
		}
	}

	/**
	 * 获取待办任务接口的待办列表(v1.0,钉钉已不维护)
	 */
	public static void handleNotifyWR(String userid, long page,
									  long status,
									  Map<String, com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse.WorkRecordVo> voMap,
									  String ekpUserId, StringBuffer errBuffer)
			throws Exception {
		ThirdDingTalkClient client = new ThirdDingTalkClient(
				DingConstant.DING_PREFIX + "/topapi/workrecord/getbyuserid"
						+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId));
		OapiWorkrecordGetbyuseridRequest req = new OapiWorkrecordGetbyuseridRequest();
		req.setUserid(userid);
		req.setOffset(page * 50);
		req.setLimit(50L);
		req.setStatus(status);
		OapiWorkrecordGetbyuseridResponse rsp = client.execute(req,
				DingUtils.dingApiService.getAccessToken());
		if (rsp.getErrcode() == 0) {
			com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse.PageResult result = rsp
					.getRecords();
			List<com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse.WorkRecordVo> vos = result
					.getList();
			if (vos != null) {
				for (com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse.WorkRecordVo vo : vos) {
					voMap.put(vo.getRecordId(), vo);
				}
				if (result.getHasMore()) {
					handleNotifyWR(userid, page + 1, status, voMap, ekpUserId,errBuffer);
				}
			}
		} else {
			errBuffer.append(rsp.getBody());
			logger.error(rsp.getBody());
		}
	}

	/**
	 * 获取待办的fdId
	 *
	 * @return
	 */
	public static String getNotifyId(String url) {
		String fdId = null;
		try {
			if (StringUtil.isNull(url)) {
				return fdId;
			}
			url = url.replace("?", "&");
			// /sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=xxx
			fdId = StringUtil.getParameter(url, "fdId");
			logger.debug("fdId：" + fdId);
			// 跳出外部浏览器 /third/ding/pc/pcopen.jsp?fdTodoId=
			if (StringUtil.isNull(fdId)) {
				fdId = StringUtil.getParameter(url, "fdTodoId");
				logger.debug("fdTodoId：" + fdId);
			}
			// http://xxx.myekp.com/resource/jsp/sso_redirect.jsp?url=aHR0cDovL2NoZW5ody5teWVrcC5jb20vc3lzL25vdGlmeS9zeXNfbm90aWZ5X3RvZG8vc3lzTm90aWZ5VG9kby5kbz9tZXRob2Q9dmlldyZmZElkPTE3M2JlNzM0MDc1ZTgwYWNmYzc3NGI0NDk2NGIyMTQyJm9hdXRoPWVrcCZkaW5nT3V0PXRydWU=
			if (StringUtil.isNull(fdId) && StringUtil.isNotNull(url)
					&& url.contains("/sso_redirect.jsp")) {
				String base64_url = StringUtil.getParameter(url, "url");
				logger.debug("base64_url：" + base64_url);
				fdId = StringUtil.getParameter(
						SecureUtil.BASE64Decoder(base64_url)
								.replace("?", "&"),
						"fdId");
				logger.debug("sso_redirect  fdId:" + fdId);
			}
			// 独立窗口打开/third/ding/pc/web_wnd.jsp?url=
			if (StringUtil.isNull(fdId) && StringUtil.isNotNull(url)
					&& url.contains("/third/ding/pc/web_wnd.jsp")) {
				logger.debug("独立窗口打开 url：" + url);
				url = StringUtil.getParameter(url, "url");
				if (StringUtil.isNotNull(url)) {
					url = URLDecoder.decode(url);
					logger.debug("待办 url：" + url);
					fdId = StringUtil.getParameter(url, "fdId");
				}
			}
		} catch (Exception e) {
			logger.error("提取待办fdId异常：" + e.getMessage(), e);
		}
		return fdId;
	}

	/**
	 * 查询2.0待办
	 */
	public static void getQueryDingOrgTasks(String unionId,String nextToken,boolean isDone,HashMap toDoMap,StringBuffer errBuffer) throws Exception {
		String url =DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/org/tasks/query";
		JSONObject req = new JSONObject();
		req.put("nextToken",nextToken);
		req.put("isDone",isDone);
		JSONObject todoDatas = DingApiHttpClientUtil.httpPost("/v1.0/todo/users/{unionId}/org/tasks/query",url, DingUtils.dingApiService.getAccessToken(), req,null, JSONObject.class);
		if (todoDatas != null && !todoDatas.isEmpty()) {
			if(todoDatas.containsKey("todoCards")){
				JSONArray todoCards = todoDatas.getJSONArray("todoCards");
				for (int i = 0; i < todoCards.size(); i++) {
					JSONObject item = todoCards.getJSONObject(i);
					toDoMap.put(item.getString("taskId"),item);
				}
				if (todoDatas.containsKey("nextToken") && !"0".equals(todoDatas.getString("nextToken"))) {
					getQueryDingOrgTasks(unionId,todoDatas.getString("nextToken"),false,toDoMap,errBuffer);
				}
			}else if(todoDatas.containsKey("code")){
				errBuffer.append(todoDatas);
				logger.error(todoDatas.toString());
			}
		}
	}

	/**
	 * 对比单个用户的待办信息
	 */
	public static void setUserTodoInfo(OmsRelationModel relationModel, SysOrgPerson user, JSONArray hadSendArray, JSONArray notSendArray, JSONArray checkErrorArray, JSONArray updateFailArray) throws Exception {

		String userInfo = user.getFdName() + "("+ user.getFdLoginName()+ ")";
		//获取用户的ekp待办列表
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysNotifyTodo.hbmTodoTargets.fdId = :userId and fdType=:type");
		hqlInfo.setParameter("userId", user.getFdId());
		hqlInfo.setParameter("type", 1);
		List<SysNotifyTodo> todoList = getSysNotifyTodoService().findList(hqlInfo);

		StringBuffer errorBuffer = new StringBuffer();
		// 获取钉钉工作流待办列表
		Map<String, com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo> voMap = new HashMap<String, com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo>();
		//待办数据存于voMap，错误信息存于errorBuffer
		getQueryWork(relationModel.getFdAppPkId(), 0L, 0L, voMap, user.getFdId(),errorBuffer);
		logger.debug("获取钉钉工作流待办信息:" + voMap);
		if (errorBuffer.length() > 0) {
			JSONObject error = JSONObject.fromObject(errorBuffer.toString());
			if (error.getInt("errcode") == 88 && !"WF".equals(DingConfig.newInstance().getNotifyApiType())){
				logger.info("非工作流接口推送，忽略工作流待办接口没有权限~~~");
			}else {
				logger.error("【钉钉接口异常】" + errorBuffer.toString());
				throw new RuntimeException(errorBuffer.toString());
			}
		}

		// 获取（待办接口V1.0)待办信息,现在钉钉已不再维护，清理仅兼容旧应用
		errorBuffer.setLength(0);
		Map<String, com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse.WorkRecordVo> voWRMap = new HashMap<>();
		//待办数据存于voMap，错误信息存于errorBuffer
		handleNotifyWR(relationModel.getFdAppPkId(), 0L, 0L, voWRMap, user.getFdId(),errorBuffer);
		logger.debug("获取（待办接口V1.0的)待办信息:" + voWRMap);
		if (errorBuffer.length()>0) {
			JSONObject error = JSONObject.fromObject(errorBuffer.toString());
			if(error.getInt("errcode")==88 && !"WR".equals(DingConfig.newInstance().getNotifyApiType())){
				//权限问题暂时放行
				logger.debug("待办1.0接口无权限，不中断");
			}else{
				logger.error("【钉钉待办v1.0接口异常】" + errorBuffer.toString());
				throw new RuntimeException(errorBuffer.toString());
			}
		}

		// 获取（待办V2.0接口的)待办信息
		errorBuffer.setLength(0);
		HashMap<String, JSONObject> toDoMap = new HashMap<>();
		getQueryDingOrgTasks(relationModel.getFdUnionId(),"0",false,toDoMap,errorBuffer);
		if (errorBuffer.length()>0) {
			JSONObject error = JSONObject.fromObject(errorBuffer.toString());
			if ("Forbidden.AccessDenied.AccessTokenPermissionDenied".equals(error.getString("code")) && !"TODO".equals(DingConfig.newInstance().getNotifyApiType())){
				logger.info("非待办v2.0接口推送，忽略待办v2.0接口没有权限~~~");
			}else {
				logger.error("【钉钉待办v2.0接口异常】" + errorBuffer.toString());
				throw new RuntimeException(errorBuffer.toString());
			}
		}

		// 获取钉钉工作流移除的待办信息
		errorBuffer.setLength(0);
		Map<String, com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo> voRemoveMap = new HashMap<String, com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo>();
		getQueryWork(relationModel.getFdAppPkId(), 0L, -1L, voRemoveMap,user.getFdId(), errorBuffer);
		logger.debug("获取钉钉工作流移除的待办信息:" + voRemoveMap);
		if (errorBuffer.length()>0) {
			JSONObject error = JSONObject.fromObject(errorBuffer.toString());
			if (error.getInt("errcode") == 88 && !"WF".equals(DingConfig.newInstance().getNotifyApiType())){
				logger.info("非工作流接口推送，忽略工作流待办接口没有权限~~~");
			}else {
				logger.error("【钉钉接口异常】" + errorBuffer.toString());
				throw new RuntimeException(errorBuffer.toString());
			}
		}

		// 获取（待办接口的)移除的待办信息
		Map<String, com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse.WorkRecordVo> voWRRemoveMap = new HashMap<String, com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse.WorkRecordVo>();
		handleNotifyWR(relationModel.getFdAppPkId(), 0L, -1L,voWRRemoveMap, user.getFdId(), errorBuffer);
		logger.debug("获取（待办接口的)移除的待办信息:" + voWRRemoveMap);
		if (errorBuffer.length()>0) {
			JSONObject error = JSONObject.fromObject(errorBuffer.toString());
			if(error.getInt("errcode")==88){
				//权限问题暂时放行
				logger.debug("待办1.0接口无权限，不中断");
			}else{
				logger.error("【钉钉待办v1.0接口异常】" + errorBuffer.toString());
				throw new RuntimeException(errorBuffer.toString());
			}
		}

		String ids = relationModel.getFdAppPkId() + ";" + user.getFdId();

		//从ekp待办的维度，记录钉钉待办数据（已匹配的数据）: key->fdId,val->0(0/1/2)-taskId
		Map<String,String> notifyIdMappings = new HashMap<>();

		// 根据待办列表--构建已推送的待办信息
		buildHadSendInfo(voMap, voWRMap,toDoMap, hadSendArray, userInfo,TODO_TYPE_TODO,notifyIdMappings,checkErrorArray); // 待办列表
		// 根据已移除的待办列表--构建已推送的待办信息
		buildHadSendInfo(voRemoveMap, voWRRemoveMap,new HashMap<String,JSONObject>(), hadSendArray,userInfo, TODO_TYPE_DELETE,notifyIdMappings,checkErrorArray); // 移除列表

		// 构建未推送的待办信息
		buildNotSendInfo(todoList,notifyIdMappings, notSendArray, checkErrorArray,userInfo, ids);

		//钉钉待办列表有，而eKp待办列表没有则归类为 处理了没有更新
		List<String> ekpIds = todoList.stream().map(SysNotifyTodo::getFdId).collect(Collectors.toList());
		List<String> failEkpIds = notifyIdMappings.keySet().stream().filter(id -> ekpIds.contains(id) ? false : true).collect(Collectors.toList());
		if (failEkpIds !=null && !failEkpIds.isEmpty()){
			//有更新失败的数据
			failEkpIds.forEach(id->{
				String dingNotifyId = notifyIdMappings.get(id);
				String type = dingNotifyId.substring(0,2);
				String dingId = dingNotifyId.substring(3);
				JSONObject obj = null;
				switch (type){
					case WORK_TODO:
					case WORK_DELETE:
						OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo vo = type.equals(WORK_TODO)?voMap.get(dingId):voRemoveMap.get(dingId);
						obj = buildBaseTodoInfo(vo.getTitle(),id, vo.getTaskId(),userInfo,API_WORK,type.equals(WORK_TODO)?TODO_TYPE_TODO:TODO_TYPE_DELETE);
						obj.put("dingInstanceId", vo.getInstanceId());
						break;
					case WR_TODO:
					case WR_DELETE:
						OapiWorkrecordGetbyuseridResponse.WorkRecordVo workRecordVo = type.equals(WR_TODO)?voWRMap.get(dingId):voWRRemoveMap.get(dingId);
						obj = buildBaseTodoInfo(workRecordVo.getTitle(),id, dingId,userInfo,API_TODO_1,type.equals(WR_TODO)?TODO_TYPE_TODO:TODO_TYPE_DELETE);
						break;
					case TASK_TODO:
						JSONObject taskObject = toDoMap.get(dingId);
						obj= buildBaseTodoInfo(taskObject.getString("subject"),id, dingId,userInfo,API_TODO_2,TODO_TYPE_TODO);
						obj.put("unionId", relationModel.getFdUnionId());
						break;
				}
				obj.put("ids", ids);
				obj.put("userid", relationModel.getFdAppPkId());
				updateFailArray.add(obj);
			});

		}
	}

	/**
	 * 构建未推送的待办信息
	 * @param todoList ekp的待办列表
	 * @param notifyIdMappings  提取的钉钉待办列表
	 * @param notSendArray  存放未推送的数据
	 * @param userInfo 用户信息
	 * @param ids 用户组合id
	 */
	private static void buildNotSendInfo(List<SysNotifyTodo> todoList, Map<String, String> notifyIdMappings, JSONArray notSendArray,JSONArray checkErrorArray, String userInfo, String ids) {
		if (todoList != null && !todoList.isEmpty()) {
			todoList.stream().forEach(todo -> {
				if(!notifyIdMappings.containsKey(todo.getFdId())){
					//ekp待办不在钉钉待办列表中，但是有可能是用户手动删除了，所以需要先判断待办映射表中是否存在记录
					JSONObject notifyData = findNotifyById(todo.getFdId());
					if(notifyData != null && !notifyData.isEmpty()){
						JSONObject obj =  buildBaseTodoInfo(todo.getFdSubject(),todo.getFdId(), null,userInfo,null,null);
						obj.putAll(notifyData);
						obj.put("errorType","钉钉端已删除");
						checkErrorArray.add(obj);
					}else{
						//未推送
						JSONObject obj = new JSONObject();
						obj.put("title", todo.getFdSubject());
						obj.put("notifyFdId", todo.getFdId());
						obj.put("user", userInfo); // 暂时传空
						obj.put("ids", ids);
						notSendArray.add(obj);
					}
				}
			});
		}
	}

	/**
	 * 查询待办映射表，看是否有推送记录（过滤钉钉端删除待办的情况）
	 * @param fdId
	 * @return
	 */
	private static JSONObject findNotifyById(String fdId)  {
		try {
			String wr_sql = "SELECT fd_record_id FROM third_ding_notify_wr WHERE fd_notify_id='"+fdId+"'";
			String work_sql = "SELECT fd_task_id FROM third_ding_dtask WHERE fd_ekp_task_id='"+fdId+"'";
			String work_xform_sql = "SELECT fd_task_id FROM third_ding_dtask_xform WHERE fd_ekp_task_id='"+fdId+"'";

			JSONObject finalData = new JSONObject();
			//待办任务（v1.0和v2.0）
			Session session = getSysNotifyTodoService().getBaseDao().getHibernateSession();
			List<Object> list = session.createNativeQuery(wr_sql).list();
			if(list!=null && !list.isEmpty()){
				list.forEach(id->{
					finalData.accumulate("taskId",id);
					if(((String) id).startsWith("$TODOV2$")){
						finalData.accumulate("type",API_TODO_2);
					}else{
						finalData.accumulate("type",API_TODO_1);
					}
				});
			}
			//工作流接口
			list = session.createNativeQuery(work_sql).list();
			if(list!=null && !list.isEmpty()){
				list.forEach(id->{
					finalData.accumulate("taskId",id);
					finalData.accumulate("type",API_WORK);
				});
			}
			//审批高级版工作流接口
			list = session.createNativeQuery(work_xform_sql).list();
			if(list!=null && !list.isEmpty()){
				list.forEach(id->{
					finalData.accumulate("taskId",id);
					finalData.accumulate("type","审批高级版");
				});
			}
			return finalData;
		} catch (Exception e) {
			logger.warn(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 内容：待办标题，待办id,钉钉待办id,待办人员，接口方式
	 * @param voMap
	 * @param voWRMap
	 * @param toDoMap
	 * @param hadSendArray 已推送的待办数据
	 * @param userInfo
	 * @param status
	 * @param notifyIdMappings 记录匹配的数据
	 * @param checkErrorArray
	 */
	private static void buildHadSendInfo(Map<String, OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo> voMap,
										 Map<String, OapiWorkrecordGetbyuseridResponse.WorkRecordVo> voWRMap,
										 HashMap<String, JSONObject> toDoMap,
										 JSONArray hadSendArray, String userInfo, String status, Map<String, String> notifyIdMappings, JSONArray checkErrorArray) {
		//工作流接口
		if (voMap != null && !voMap.isEmpty()) {
			voMap.values().forEach(vo->{
				String notifyFdId = getNotifyId(vo.getUrl());
				if (StringUtil.isNotNull(notifyFdId)) {
					notifyIdMappings.put(notifyFdId,(TODO_TYPE_TODO.equals(status)?WORK_TODO:WORK_DELETE)+"-"+vo.getTaskId());
					hadSendArray.add(buildBaseTodoInfo(vo.getTitle(),notifyFdId,vo.getTaskId(),userInfo,API_WORK,status));
				}else{
					//无法提取ekp待办fdId参数
					addMatchError(vo,checkErrorArray,userInfo,status, vo.getUrl());
				}
			});
		}
		//待办1.0接口（钉钉已不维护）
		if (voWRMap != null && !voWRMap.isEmpty()) {
			voWRMap.values().forEach(vo->{
				String notifyFdId = getNotifyId(vo.getUrl());
				if (StringUtil.isNotNull(notifyFdId)) {
					notifyIdMappings.put(notifyFdId,(TODO_TYPE_TODO.equals(status)?WR_TODO:WR_DELETE)+"-"+vo.getRecordId());
					hadSendArray.add(buildBaseTodoInfo(vo.getTitle(),notifyFdId,vo.getRecordId(),userInfo,API_TODO_1,status));
				}else{
					//无法提取ekp待办fdId参数
					addMatchError(vo,checkErrorArray,userInfo,status, vo.getUrl());
				}
			});
		}
		//待办2.0
		if (toDoMap != null && !toDoMap.isEmpty()) {
			toDoMap.values().forEach(itemObject->{
				JSONObject detailUrl = itemObject.getJSONObject("detailUrl");
				String pcUrl = null;
				if (detailUrl.has("pcUrl")) {
					//pc端url地址
					pcUrl = detailUrl.getString("pcUrl");
				}
				String notifyFdId = getNotifyId(pcUrl);
				if (StringUtil.isNotNull(notifyFdId)) {
					notifyIdMappings.put(notifyFdId,TASK_TODO+"-"+itemObject.getString("taskId"));
					hadSendArray.add(buildBaseTodoInfo(itemObject.getString("subject"),notifyFdId,itemObject.getString("taskId"),userInfo,API_TODO_2,status));
				}else{
					//无法提取ekp待办fdId参数
					addMatchError(itemObject,checkErrorArray,userInfo,status,pcUrl);
				}
			});
		}
	}

	/**
	 * 记录提取不到ekp待办id的异常记录
	 */
	private static void addMatchError(Object dingNotifyVO, JSONArray checkErrorArray, String userInfo, String status, String url) {
		if(dingNotifyVO instanceof OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo){
			//工作流接口
			OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo vo = (OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo)dingNotifyVO;
			JSONObject obj =  buildBaseTodoInfo(vo.getTitle(),null, vo.getTaskId(),userInfo,API_WORK,status);
			obj.put("url", vo.getUrl());
			obj.put("errorType","URL无法提取ekp待办的fdId");
			checkErrorArray.add(obj);
		}else if (dingNotifyVO instanceof OapiWorkrecordGetbyuseridResponse.WorkRecordVo){
			OapiWorkrecordGetbyuseridResponse.WorkRecordVo vo = (OapiWorkrecordGetbyuseridResponse.WorkRecordVo)dingNotifyVO;
			JSONObject obj =  buildBaseTodoInfo(vo.getTitle(),null, vo.getRecordId(),userInfo,"待办任务接口",status);
			obj.put("url", vo.getUrl());
			obj.put("errorType","URL无法提取ekp待办的fdId");
			checkErrorArray.add(obj);
		}else if (dingNotifyVO instanceof JSONObject){
			JSONObject vo = (JSONObject)dingNotifyVO;
			JSONObject obj =  buildBaseTodoInfo(vo.getString("subject"),null, vo.getString("taskId"),userInfo,API_TODO_2,status);
			obj.put("url", url);
			obj.put("errorType","URL无法提取ekp待办的fdId");
			checkErrorArray.add(obj);
		}
	}

	/**
	 * 构建待办结果信息
	 */
	private static JSONObject buildBaseTodoInfo(String subject, String notifyFdId, String taskId, String userInfo, String type, String status) {
		JSONObject obj = new JSONObject();
		obj.put("title", subject);
		obj.put("notifyFdId", notifyFdId);
		obj.put("dingNotifyId", taskId);
		obj.put("user", userInfo);
		obj.put("type", type);
		obj.put("status", status);
		return obj;
	}

}
