package com.landray.kmss.third.ding.notify.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationStatusBarUpdateRequest;
import com.dingtalk.api.request.OapiWorkrecordAddRequest.FormItemVo;
import com.dingtalk.api.response.OapiMessageCorpconversationStatusBarUpdateResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProviderExtend;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.messageType.DingOfficeBody;
import com.landray.kmss.third.ding.messageType.DingOfficeHead;
import com.landray.kmss.third.ding.messageType.DingOfficeMessage;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingNotifyQueue;
import com.landray.kmss.third.ding.model.ThirdDingTodoTemplate;
import com.landray.kmss.third.ding.notify.interfaces.MessageSendException;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyLog;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyMessage;
import com.landray.kmss.third.ding.notify.queue.constant.ThirdDingNotifyQueueErrorConstants;
import com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError;
import com.landray.kmss.third.ding.notify.queue.service.IThirdDingNotifyQueueErrorService;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyLogService;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyMessageService;
import com.landray.kmss.third.ding.notify.util.NotifyUtil;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingNotifyQueueService;
import com.landray.kmss.third.ding.service.IThirdDingTodoTemplateService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ObjectUtil;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.enums.ValueLabel;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.orm.hibernate5.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class ThirdDingTodoProvider extends BaseSysNotifyProviderExtend {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingTodoProvider.class);

	private IThirdDingNotifyQueueService thirdDingNotifyQueueService = null;

	public IThirdDingNotifyQueueService getThirdDingNotifyQueueService() {
		if (thirdDingNotifyQueueService == null) {
			return (IThirdDingNotifyQueueService) SpringBeanUtil
					.getBean("thirdDingNotifyQueueService");
		}
		return thirdDingNotifyQueueService;
	}

	private IThirdDingNotifyMessageService thirdDingNotifyMessageService;

	public IThirdDingNotifyMessageService getThirdDingNotifyMessageService() {
		if (thirdDingNotifyMessageService == null) {
			return (IThirdDingNotifyMessageService) SpringBeanUtil
					.getBean("thirdDingNotifyMessageService");
		}
		return thirdDingNotifyMessageService;
	}

	@Override
	public void add(SysNotifyTodo todo, NotifyContext context) throws Exception {
		if (!(DingUtil.checkNotifyApiType("WR") ||DingUtil.checkNotifyApiType("TODO")
				|| DingUtil.isXformTemplate(todo))) {
			return;
		}
		long atime = System.currentTimeMillis();
		if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
			if (logger.isDebugEnabled()) {
				logger.debug("钉钉未开启集成，不推送消息...");
			}
			return;
		}

		String ekpUserId = null;
		boolean notifyflag = false;
		if (logger.isDebugEnabled()) {
			logger.debug("开始向钉钉推送消息...");
		}
		String appType = context.getFdAppType();
		if (StringUtil.isNotNull(appType) ) {
			if (appType.contains("all") || appType.contains("ding")) {
				notifyflag = true;
				if (logger.isDebugEnabled()) {
					logger.debug("已经设置参数fdAppType=" + appType + ",故强制推送消息...");
				}
			}
		}else if(StringUtil.isNull(appType)){
			if (todo.getFdType() == 2) {
				if (!"true".equals(
						DingConfig.newInstance().getDingTodotype2Enabled())) {
					// 待阅关闭,默认关闭，开启需要勾上
					if (logger.isDebugEnabled()) {
						logger.debug("未开启待阅消息推送到钉钉的消息中心...");
					}
				}else{
					notifyflag = true;
				}
			}
			if (todo.getFdType() == 1 || todo.getFdType() == 3) {
				if (!"true".equals(
						DingConfig.newInstance().getDingTodotype1Enabled())) {
					// 待阅关闭,默认关闭，开启需要勾上
					if (logger.isDebugEnabled()) {
						logger.debug("未开启待办消息推送到钉钉的消息中心...");
					}
				}else{
					notifyflag = true;
				}
			}
		}
		if(!notifyflag){
			if (logger.isDebugEnabled()) {
				logger.debug("都未开启待阅、待办消息推送,参数fdAppType=" + appType + ",待办Id="
						+ todo.getFdId() + ",待办主题：" + todo.getFdSubject());
			}
			return;
		}
		
		NotifyContextImp ctx = (NotifyContextImp) context;
		List notifyTargetList = ((NotifyContextImp) context).getNotifyPersons();
		if (notifyTargetList == null || notifyTargetList.isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.warn("通知人员为空不执行消息发送，通知标题为：" + todo.getFdSubject() + "("
						+ todo.getFdId() + ")");
			}
			return;
		}else{
			DingOfficeMessage dingOfficeMessage = createNotifyToDoDTO(todo);
			List<String> ekpIds = new ArrayList<String>();
			if (notifyTargetList != null && notifyTargetList.size() > 0) {
				Iterator<?> it = ctx.getNotifyPersons().iterator();
				while (it.hasNext()) {
					SysOrgPerson sysOrgPerson = (SysOrgPerson) it.next();
					if (StringUtil.isNull(ekpUserId)) {
						String dind_userId = ((IOmsRelationService) SpringBeanUtil
								.getBean("omsRelationService"))
										.getDingUserIdByEkpUserId(
												sysOrgPerson.getFdId());
						if (StringUtil.isNotNull(dind_userId)) {
							ekpUserId = sysOrgPerson.getFdId();
						}
					}
					ekpIds.add(sysOrgPerson.getFdId());
				}
			}

			Map<String, String> dingIdMap = DingUtil.getDingIdMap(
					ekpIds);
			Set<String> dingIds = dingIdMap.keySet();
			if (dingIds == null || dingIds.size() == 0) {
				if (logger.isDebugEnabled()) {
					logger.error("通过EKP的fdId查找人员映射表发现找不到对应的钉钉人员(" + ekpIds
							+ ")，请先检查组织同步是否正常");
				}
				return;
			}

			boolean isMoreThen500 = false;
			String _userIds = StringUtils.join(dingIds.toArray(), ",");
			logger.debug("_userIds:" + _userIds);
			if (dingIds.size() >= 500) {
				logger.warn("=======加到通知队列通知=========");
				isMoreThen500 = true;
				ThirdDingNotifyQueue queue = new ThirdDingNotifyQueue();
				queue.setDocSubject(todo.getFdSubject());
				queue.setFdAppType(ctx.getFdAppType());
				queue.setFdUserids(_userIds);
				queue.setFdEndTime(ctx.getFdEndTime());
				queue.setFdTodoId(todo.getFdId());
				getThirdDingNotifyQueueService().add(queue);
			}
			// 避免定时任务没有开启，自动执行一下
			getThirdDingNotifyQueueService().addMessage(null);
			List<String> dingIdList = new ArrayList<String>(dingIds);
			StringBuffer userIds = new StringBuffer();
			if (dingIdList != null && dingIdList.size() > 0) {
				//待办待阅推送
				if (!isMoreThen500 && (("true".equals(
						DingConfig.newInstance().getDingTodotype1Enabled())
						&& (todo.getFdType() == 1 || todo.getFdType() == 3))
						|| ("true"
								.equals(DingConfig.newInstance()
										.getDingTodotype2Enabled())
								&& todo.getFdType() == 2)
						|| (StringUtil.isNotNull(appType)
								&& (appType.contains("all")
										|| appType.contains("ding"))))) {
					for (int i = 0; i < dingIdList.size(); i++) {
						userIds.append(dingIdList.get(i) + ",");
						if ((i + 1) % 100 == 0) {
							if (userIds.length() == 0) {
								continue;
							}
							String touser = userIds.toString().substring(0, userIds.toString().length() - 1);
							dingOfficeMessage.setTouser(touser);
							Long endTime = context.getFdEndTime();
							if (logger.isDebugEnabled()) {
								logger.debug("endTime:" + endTime);
							}
							if (endTime != null && endTime != 0) {
								if (System.currentTimeMillis() > endTime) {
									logger.warn("当前时间已经超过了消息的截止时间，不再推送消息...");
									logger.warn("endTime:" + endTime);
									return;
								}
							}
							sendNotify(todo, touser, null, false,
									dingOfficeMessage, ekpUserId);
							userIds.setLength(0);
						}
					}
					if (StringUtils.isNotEmpty(userIds.toString())) {
						String touser = userIds.toString().substring(0, userIds.toString().length() - 1);
						dingOfficeMessage.setTouser(touser);
						Long endTime = context.getFdEndTime();
						if (logger.isDebugEnabled()) {
							logger.debug("endTime:" + endTime);
						}
						if (endTime != null && endTime != 0) {
							if (System.currentTimeMillis() > endTime) {
								logger.warn("当前时间已经超过了消息的截止时间，不再推送消息...");
								logger.warn("endTime:" + endTime);
								return;
							}
						}
						sendNotify(todo, touser, null, false,
								dingOfficeMessage, ekpUserId);
					}
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("发送钉钉消息总耗时："+(System.currentTimeMillis()-atime)+"毫秒");
		}
	}

	public void sendNotifyBatch(SysNotifyTodo todo,
						   String userid, String deptid,
						   boolean flag, DingOfficeMessage dingOfficeMessage, String ekpUserId){
		long time = System.currentTimeMillis();
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setFdSendTime(start);
		OapiMessageCorpconversationAsyncsendV2Request request = null;
		try{
			log.setFdUrl("/topapi/message/corpconversation/asyncsend_v2");
			request = buildMessageSendRequest(todo,userid,deptid,flag, dingOfficeMessage,ekpUserId);

			log.setFdNotifyData(buildSendRequestJson(request).toJSONString());
			DingApiService dingService = DingUtils.getDingApiService();
			String result = dingService.messageSend(NotifyUtil.getAgendId(todo), ekpUserId,
					request);
			if (logger.isDebugEnabled()) {
				logger.debug("消息发送返回消息："+result);
			}
			JSONObject jo = JSONObject.parseObject(result);
			log.setFdRequestId(jo.getString("request_id"));
			if (jo.containsKey("errcode")
					&& jo.getInteger("errcode") == 0) {
				log.setFdResult(true);
			} else {
				log.setFdResult(false);
			}
			log.setFdRtnMsg(result);
			Date end = new Date();
			log.setFdSendTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());

		}catch(Exception e){
			log.setFdResult(false);
			logger.error("发送钉钉消息异常：",e);
			log.setFdRtnMsg(e.getMessage());
			log.setFdUrl("/topapi/message/corpconversation/asyncsend_v2");
			Date end = new Date();
			log.setFdSendTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			if (!(e instanceof MessageSendException)) {
				addErrorQueue(todo, request, ekpUserId, "asyncsend_v2",
						e.getMessage(),false);
			}
		} finally {
			TransactionStatus addStatus = null;
			try {
				addStatus = TransactionUtils
						.beginNewTransaction();
				getThirdDingNotifyLogService().add(log);
				TransactionUtils.getTransactionManager().commit(addStatus);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				if (addStatus != null) {
					try {
						TransactionUtils.getTransactionManager()
								.rollback(addStatus);
					} catch (Exception ex) {
						logger.error("---事务回滚出错---", ex);
					}
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("发送钉钉消息耗时："+(System.currentTimeMillis()-time)+"毫秒");
		}
	}

	public void sendNotifySingle(SysNotifyTodo todo,
								String userid, String deptid,
								boolean flag, DingOfficeMessage dingOfficeMessage, String ekpUserId){
		long time = System.currentTimeMillis();
		OapiMessageCorpconversationAsyncsendV2Request request = null;
		try{
			String[] useridArray = userid.split(",");
			request = buildMessageSendRequest(todo,userid,deptid,flag, dingOfficeMessage,ekpUserId);
			int i = 0;
			for(String dingId:useridArray){
				request.setUseridList(dingId);
				callDingdingSend("/topapi/message/corpconversation/asyncsend_v2",todo,request,ekpUserId);
				if((i++)%20==0){
					Thread.sleep(200);
				}
			}
		}catch(Exception e){
			logger.error("发送钉钉消息异常：",e);
		}
	}

	private JSONObject buildSendRequestJson(OapiMessageCorpconversationAsyncsendV2Request req) {
		String[] keys = {"agent_id","userid_list","dept_id_list","to_all_user","msg"};
		List<String> keysList = Arrays.asList(keys);
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(req);
		JSONObject resultObj = new JSONObject();
		for(String key:jsonObject.keySet()){
			if(keysList.contains(key)){
				resultObj.put(key,jsonObject.get(key));
			}
		}
		return resultObj;
	}

	private JSONObject buildUpdateRequestJson(OapiMessageCorpconversationStatusBarUpdateRequest req) {
		String[] keys = {"agent_id","task_id","status_value","status_bg"};
		List<String> keysList = Arrays.asList(keys);
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(req);
		JSONObject resultObj = new JSONObject();
		for(String key:jsonObject.keySet()){
			if(keysList.contains(key)){
				resultObj.put(key,jsonObject.get(key));
			}
		}
		return resultObj;
	}

	private void callDingdingSend(String url, SysNotifyTodo todo,
									OapiMessageCorpconversationAsyncsendV2Request req,
								   String ekpUserId) {
		String dingUrl = url+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		if (logger.isDebugEnabled()) {
			logger.debug("调钉钉接口：" + dingUrl);
		}
		DingApiService dingService = DingUtils.getDingApiService();
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdUrl(client.getRequestUrl());
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setFdSendTime(start);
		log.setFdNotifyData(((JSONObject)JSONObject.toJSON(req.getTextParams())).toJSONString());
		if (logger.isDebugEnabled()) {
			logger.debug("*******************req.toString():" + req.toString());
		}
		try {
			Long agentId =NotifyUtil.getAgendId(todo);
			String result = dingService.messageSend(agentId, ekpUserId,
					req);
			log.setFdRtnMsg(result);
			net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(result);
			String requestId = null;
			if (jo.containsKey("request_id")) {
				requestId = jo.getString("request_id");
			}
			log.setFdRequestId(requestId);
			if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
				log.setFdResult(true);
				logger.info("待办发送到钉钉详细：" + jo.toString());
				String task_id = jo.getString("task_id");
				addMessageRecord(todo.getFdId(), todo.getFdSubject(),
						req, ekpUserId,
						task_id, agentId+"");
			} else {
				log.setFdResult(false);
				logger.warn("待办发送到钉钉创建失败。详细：" + jo.toString());
				addErrorQueue(todo, req, ekpUserId, "asyncsend_v2_status", jo.toString(),false);
			}
		}  catch (Exception e) {
			logger.error(e.getMessage(), e);
			log.setFdResult(false);
			log.setFdRtnMsg(e.getMessage());
			addErrorQueue(todo, req, ekpUserId, "asyncsend_v2_status", e.getMessage(),false);
		} finally {
			Date end = new Date();
			log.setFdRtnTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			TransactionStatus addStatus = null;
			try {
				addStatus = TransactionUtils
						.beginNewTransaction();
				getThirdDingNotifyLogService().add(log);
				TransactionUtils.getTransactionManager().commit(addStatus);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				if (addStatus != null) {
					try {
						TransactionUtils.getTransactionManager()
								.rollback(addStatus);
					} catch (Exception ex) {
						logger.error("---事务回滚出错---", ex);
					}
				}
			}
		}
	}

	private void addMessageRecord(String todoId, String subject,
							OapiMessageCorpconversationAsyncsendV2Request req,
							   String ekpUserId, String task_id, String agentId) {
		TransactionStatus addStatus = null;
		try {
			ThirdDingNotifyMessage record = new ThirdDingNotifyMessage();
			record.setFdDingUserId(req.getUseridList());
			record.setFdUser((SysOrgPerson) getSysOrgPersonService()
					.findByPrimaryKey(ekpUserId));
			record.setFdNotifyId(todoId);
			record.setFdDingTaskId(task_id);
			record.setFdSubject(subject);
			record.setFdAgentId(agentId);
			addStatus = TransactionUtils
					.beginNewTransaction();
			getThirdDingNotifyMessageService().add(record);
			TransactionUtils.getTransactionManager().commit(addStatus);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (addStatus != null) {
				try {
					TransactionUtils.getTransactionManager()
							.rollback(addStatus);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
		}
	}

	private OapiMessageCorpconversationAsyncsendV2Request buildMessageSendRequest(SysNotifyTodo todo, String userid, String deptid, boolean flag, DingOfficeMessage dingOfficeMessage, String ekpUserId) throws Exception {
		try {
			DingApiService dingService = DingUtils.getDingApiService();
			Map<String, String> content = new HashMap<String, String>();
			Map<String, Object> map = dingOfficeMessage.getOa();
			DingOfficeHead head = (DingOfficeHead) map.get("head");
			content.put("color", head.getBgcolor());
			DingOfficeBody body = (DingOfficeBody) map.get("body");
			content.put("content", body.getContent());
			String title = body.getTitle();
			switch (todo.getFdLevel()) {
				case 1:
					title = "【"
							+ DingUtil.getValueByLang(
							"sysNotifyTodo.level.taglib.1",
							"sys-notify", todo.getFdLang())
							+ "】" + title;
					break;
				case 2:
					title = "【"
							+ DingUtil.getValueByLang(
							"sysNotifyTodo.level.taglib.2",
							"sys-notify", todo.getFdLang())
							+ "】" + title;
					break;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("工作通知待办title:" + title);
			}
			content.put("title", title);
			content.put("pc_message_url", (String) map.get("pc_message_url"));
			content.put("message_url", (String) map.get("message_url"));
			if(!flag && (todo.getFdType()==1 || todo.getFdType()==3) && "true".equals(DingConfig.newInstance().getUpdateMessageStatus())) {
				content.put("status_bar",getNotifyStatus(todo));
			}
			if (logger.isDebugEnabled()) {
				logger.debug("发送数据参数，基本信息:" + content + ",用户信息：" + userid + ",部门信息" + deptid + ",是否全员发送：" + flag);
			}
			return dingService
					.buildMessageSendRequest(content, userid, deptid,
							flag, NotifyUtil.getAgendId(todo), ekpUserId);
		}catch (Exception e){
			throw e;
		}
	}

	public void sendNotify(SysNotifyTodo todo,
			String userid, String deptid,
			boolean flag, DingOfficeMessage dingOfficeMessage, String ekpUserId)
	{
		if(StringUtil.isNull(ekpUserId)&&todo.getDocCreator()!=null){
			ekpUserId = todo.getDocCreator().getFdId();
		}
		if(!flag && (todo.getFdType()==1||todo.getFdType()==3) && "true".equals(DingConfig.newInstance().getUpdateMessageStatus())){
			sendNotifySingle(todo,userid,deptid,flag,dingOfficeMessage,ekpUserId);
		}else{
			sendNotifyBatch(todo,userid,deptid,flag,dingOfficeMessage,ekpUserId);
		}
	}
	
	public DingOfficeMessage createNotifyToDoDTO(SysNotifyTodo todo)
			throws IllegalAccessException, InvocationTargetException {

		String fdLang = todo.getFdLang();
		Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
		if (locale == null) {
			locale = ResourceUtil.getLocaleByUser();
		}

		DingOfficeMessage dingOfficeMessage = new DingOfficeMessage();
		String sub = todo.getFdSubject();
		dingOfficeMessage.setAgentid(DingConfig.newInstance().getDingAgentid());
		dingOfficeMessage.setMsgtype("oa");
    	Map<String,Object>map = new HashMap<String,Object>();
    	 
    	DingOfficeHead dingOfficeHead = new DingOfficeHead();
		String titleColor = DingConfig.newInstance().getDingTitleColor();
		if (StringUtils.isEmpty(titleColor)) {
			titleColor = "FF9A89B9";
		}
    	dingOfficeHead.setBgcolor(titleColor);
    	DingOfficeBody dingOfficeBody = new DingOfficeBody();
    	dingOfficeBody.setTitle(sub);
		String bodyContent = getBobyContent(todo, locale);
    	
    	dingOfficeBody.setContent(bodyContent);

		// 钉钉转屏参数
		// viewUrl += "&dd_orientation=auto";
		String viewUrl = DingUtil.getViewUrl(todo);
		String pcViewUrl = DingUtil.getPcViewUrl(todo);

		map.put("message_url", viewUrl);
		map.put("pc_message_url", pcViewUrl);
    	map.put("head", dingOfficeHead);
    	map.put("body", dingOfficeBody);

    	dingOfficeMessage.setOa(map);

		String msg = "钉钉应用ID:" + DingConfig.newInstance().getDingAgentid()
				+ "访问url:" + viewUrl;
		if (logger.isDebugEnabled()) {
			logger.debug(msg);
		}
		return dingOfficeMessage;
	}

	/*
	 * 获取钉钉工作通知推送内容
	 */
	private String getBobyContent(SysNotifyTodo todo, Locale locale) {

		String content = "";
		String fdModelName = todo.getFdModelName();
		String fdModelId = todo.getFdModelId();
		if (logger.isDebugEnabled()) {
			logger.debug("fdModelName:" + fdModelName + " fdModelId:" + fdModelId);
		}
		if (StringUtil.isNull(fdModelName) || StringUtil.isNull(fdModelId)) {
			return getDefalutBobyContent(todo, locale);
		}
		// 获取待办模版
		IThirdDingTodoTemplateService thirdDingTodoTemplateService = (IThirdDingTodoTemplateService) SpringBeanUtil
				.getBean("thirdDingTodoTemplateService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"thirdDingTodoTemplate.fdModelName=:fdModelName");
		hqlInfo.setParameter("fdModelName", fdModelName);
		List<ThirdDingTodoTemplate> list;
		try {
			list = thirdDingTodoTemplateService.findList(hqlInfo);
			ThirdDingTodoTemplate thirdDingTodoTemplate = getTemplateModel(list,
					todo);
			if (thirdDingTodoTemplate != null) {
				// 使用自定义工作通知模版
				if (logger.isDebugEnabled()) {
					logger.debug("----使用工作通知模块自定义的模版---");
				}

				SysDictModel newsModel = SysDataDict.getInstance()
						.getModel(fdModelName);
				String beanName = newsModel.getServiceBean();
				if (logger.isDebugEnabled()) {
					logger.debug("-----------beanName:" + beanName);
				}
				IBaseService obj = (IBaseService) SpringBeanUtil
						.getBean(beanName);
				hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"fdId=:fdId");
				hqlInfo.setParameter("fdId", fdModelId);
				List mainList = obj.findList(hqlInfo);
				if (mainList.size() > 0) {
					// 构建待办信息
					String fdDetail = thirdDingTodoTemplate.getFdDetail();
					if (logger.isDebugEnabled()) {
						logger.debug("fdDetail:" + fdDetail);
					}
					JSONObject fdDetailJSON = JSONObject.parseObject(fdDetail);
					JSONArray ja = fdDetailJSON.getJSONArray("data");
					List keyList = new ArrayList();
					String key;
					String fromForm;
					String name = "";
					JSONArray titleJA = new JSONArray();
					List<FormItemVo> singleFormItemVo = new ArrayList<FormItemVo>();
					if ("com.landray.kmss.km.review.model.KmReviewMain"
							.equals(fdModelName)) {
						// 流程
						for (int i = 0; i < ja.size(); i++) {
							name = "";
							singleFormItemVo = null;
							List singleKey = new ArrayList();
							List nameList = new ArrayList();
							key = ja.getJSONObject(i).getString("key");
							titleJA = ja.getJSONObject(i).getJSONArray("title");
							// 异构系统的fdLang可能为空
							if (StringUtil.isNotNull(todo.getFdLang())) {
								for (int j = 0; j < titleJA.size(); j++) {
									String lang = titleJA.getJSONObject(j)
											.getString("lang");
									if (lang.contains(todo.getFdLang())) {
										name = titleJA.getJSONObject(j)
												.getString("value");
										break;
									}
								}

							}
							if (StringUtil.isNull(name)) {
								name = titleJA.getJSONObject(0)
										.getString("value");// 取默认语言
							}

							fromForm = ja.getJSONObject(i)
									.getString("fromForm");
							logger.debug(
									"key:" + key + " fromForm:" + fromForm
											+ " name:" + name);
							String val = "";
							if (!"true".equals(fromForm)) {
								val = getModelItemValue(todo,
										ja.getJSONObject(i).getString("key"),
										false);
							} else {
								// 表单字段 单个处理
								val = ObjectUtil.getFormValue(todo,
										thirdDingTodoTemplate,
										key);
							}
							content += name + ":   " + val + "\r\n";

						}

					} else {
						for (int i = 0; i < ja.size(); i++) {
							key = ja.getJSONObject(i).getString("key");
							fromForm = ja.getJSONObject(i)
									.getString("fromForm");
							name = "";
							titleJA = ja.getJSONObject(i).getJSONArray("title");

							// 异构系统的fdLang可能为空
							if (StringUtil.isNotNull(todo.getFdLang())) {

								for (int j = 0; j < titleJA.size(); j++) {
									String lang = titleJA.getJSONObject(j)
											.getString("lang");
									if (lang.contains(todo.getFdLang())) {
										name = titleJA.getJSONObject(j)
												.getString("value");
										break;
									}
								}

							}
							if (StringUtil.isNull(name)) {
								name = titleJA.getJSONObject(0)
										.getString("value");// 取默认语言
							}
							if (logger.isDebugEnabled()) {
								logger.debug(
										"key:" + key + " fromForm:" + fromForm
												+ " name:" + name);
							}
							String val = getModelItemValue(todo,
									ja.getJSONObject(i).getString("key"),
									false);

							content += name + ":   " + val + "\r\n";
						}
					}

				} else {
					logger.warn("获取模块信息失败,将以默认待办模版推送！" + fdModelName + " "
							+ fdModelId);
					return getDefalutBobyContent(todo, locale);
				}

			} else {
				logger.warn("使用默认待办模版！");
				return getDefalutBobyContent(todo, locale);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getDefalutBobyContent(todo, locale);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("【工作通知事项内容】" + content);
		}
		return content;

	}


	/*
	 * 根据推送方式返回待办模板
	 */
	private ThirdDingTodoTemplate
			getTemplateModel(List<ThirdDingTodoTemplate> list,
					SysNotifyTodo todo) throws Exception {
		if (list == null || list.isEmpty()) {
			return null;
		}

		if ("com.landray.kmss.km.review.model.KmReviewMain"
				.equals(todo.getFdModelName())) {
			// 流程管理有自定义表单，特殊处理
			logger.debug("-----流程表单工作通知处理-----");
			String reviewMainId = todo.getFdModelId();
			IBaseService obj = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
			Object kmReviewMainObject = obj
					.findByPrimaryKey(reviewMainId);
			Class clazz = kmReviewMainObject.getClass();
			Method method = clazz.getMethod("getFdTemplate");
			Object templateObj = method.invoke(kmReviewMainObject);
			clazz = templateObj.getClass();
			Object templateId = clazz.getMethod("getFdId")
					.invoke(templateObj);
			logger.debug("---------------------templateId:" + templateId.toString());

			String todoTemplateId = templateId.toString();
			ThirdDingTodoTemplate thirdDingTodoTemplateTemp = null;
			ThirdDingTodoTemplate kmReviewDefault = null;
			for (ThirdDingTodoTemplate template : list) {
				if (todoTemplateId.equals(template.getFdTemplateId())
						&& (StringUtil.isNull(template.getFdType())
								|| template.getFdType()
										.contains("2"))) {
					logger.debug("匹配上了流程模版:" + template.getFdName());
					thirdDingTodoTemplateTemp = template;
					break;
				}
				if (StringUtil.isNull(template.getFdTemplateId())
						&& (StringUtil.isNull(template.getFdType())
								|| template.getFdType()
										.contains("2"))) {
					if (logger.isDebugEnabled()) {
						logger.debug("流程模版默认待办:" + template.getFdName());
					}
					kmReviewDefault = template;
				}
			}

			if (thirdDingTodoTemplateTemp != null) {
				return thirdDingTodoTemplateTemp;
			} else if (kmReviewDefault != null) {
				return kmReviewDefault;
			}
			return null;
		}

		for (ThirdDingTodoTemplate temp : list) {
			if (StringUtil.isNotNull(temp.getFdType())
					&& temp.getFdType().contains("2")) {
				return temp;
			}
		}
		return null;
	}

	private  String getModelItemValue(SysNotifyTodo todo,String key, boolean isDefalut) {
		// 根据待办模版配置的默认模版进行推送
		String fdModelName = todo.getFdModelName();
		String fdModelId = todo.getFdModelId();
		SysDictModel model = null;
		Object main = new Object();
		try {
			if (isDefalut) {
				// 默认待办 从com.landray.kmss.sys.notify.model.SysNotifyTodo取数据
				model = SysDataDict.getInstance()
						.getModel(
								"com.landray.kmss.sys.notify.model.SysNotifyTodo");

				main = todo;
			} else {
				model = SysDataDict.getInstance()
						.getModel(fdModelName);
				String beanName = model.getServiceBean();

				IBaseService obj = (IBaseService) SpringBeanUtil
						.getBean(beanName);
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"fdId=:fdId");
				hqlInfo.setParameter("fdId", fdModelId);
				main = obj.findFirstOne(hqlInfo);

				if (main == null) {
					logger.error("获取模块主文档失败！将以默认待办模版推送！");
					return getDefalutBobyContent(todo, null);
				}
			}

			Map<String, SysDictCommonProperty> map = model
					.getPropertyMap();
			String enumType = null;
			try {
				// 枚举类型的判断 判断key的最后一个字段 docCreator.fdName
				if (key.contains(".")) {
					if (logger.isDebugEnabled()) {
						logger.debug("key为对象类型：" + key);
					}
					String[] keyArray = key.split("\\.");
					if (keyArray != null && keyArray.length > 0) {
						SysDictCommonProperty name = map.get(keyArray[0]);
						String type = name.getType();
						if (logger.isDebugEnabled()) {
							logger.debug("type：" + type);
						}
						SysDictModel model2;
						Map<String, SysDictCommonProperty> map2;
						for (int k = 1; k < keyArray.length; k++) {
							model2 = SysDataDict.getInstance()
									.getModel(type);
							map2 = model2.getPropertyMap();
							name = map2.get(keyArray[k]);
							type = name.getType();
						}
						enumType = name.getEnumType();

					}

				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("key为简单类型：" + key);
					}
					SysDictCommonProperty name = map.get(key);
					enumType = name.getEnumType();
				}

			} catch (Exception e) {
				logger.error(
						"判断字段" + key + "是否为枚举类型过程中发生异常！"+e.getMessage() ,e);

			}

			String content = ObjectUtil.getValue2(main, key);

			if (StringUtil.isNull(content)) {
				logger.warn("内容为空(key:" + key + ")");
				return "";
			}
			if (logger.isDebugEnabled()) {
				logger.debug("enumType:" + enumType);
			}
			// 枚举进行转换
			if (StringUtil.isNotNull(enumType)) {
				List enumList = EnumerationTypeUtil
						.getColumnEnumsByType(enumType);
				for (int k = 0; k < enumList.size(); k++) {
					ValueLabel valueLabel = (ValueLabel) enumList.get(k);
					if (content.equals(valueLabel.getValue())) {
						content = DingUtil.getValueByLang(
								valueLabel.getLabelKey(),
								valueLabel.getBundle(),
								todo.getFdLang());
						if (logger.isDebugEnabled()) {
							logger.debug("枚举转换：" + content + "("
									+ enumType + ")");
						}
					}
				}
			}
			// 默认待办模块，值特殊处理
			if (isDefalut) {
				if ("fdModelName".equals(key)) {
					content = getSysNotifyTodoService().getModelNameText(todo);
				} else if ("fdLevel".equals(key)
						&& StringUtil.isNotNull(content)) {
					// 优先级转换 待办优先级按紧急、急、一般,紧急:1, 急:2,一般:3 fdLevel
					if (logger.isDebugEnabled()) {
						logger.debug("优先级转换：" + content);
					}
					switch (todo.getFdLevel()) {
					case 1:
						content = DingUtil.getValueByLang(
								"sysNotifyTodo.level.taglib.1",
								"sys-notify", todo.getFdLang());
						break;
					case 2:
						content = DingUtil.getValueByLang(
								"sysNotifyTodo.level.taglib.2",
								"sys-notify", todo.getFdLang());
						break;
					case 3:
						content = DingUtil.getValueByLang(
								"sysNotifyTodo.level.taglib.3",
								"sys-notify", todo.getFdLang());
						break;
					}

				}

			}
			if (logger.isDebugEnabled()) {
				logger.debug("content:" + content);
			}
			return content;

		} catch (Exception e) {
			logger.error("创建模块待办发生异常！,将以默认待办发送" + e.getMessage(), e);
			return getDefalutBobyContent(todo, null);
		}

	}

	/*
	 * 获取默认的工作通知推送内容,优先推容配置的默认模板，其次是原始模板(创建人、创建时间、模块)
	 */
	private String getDefalutBobyContent(SysNotifyTodo todo, Locale locale) {

		if (locale == null) {
			locale = SysLangUtil.getLocaleByShortName(todo.getFdLang());
			if (locale == null) {
				locale = ResourceUtil.getLocaleByUser();
			}
		}
		// 判断默认模板是否有工作通知推送方式
		try {
			IThirdDingTodoTemplateService thirdDingTodoTemplateService = (IThirdDingTodoTemplateService) SpringBeanUtil
					.getBean("thirdDingTodoTemplateService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"thirdDingTodoTemplate.fdIsdefault=:fdIsdefault and thirdDingTodoTemplate.fdType like :fdType");
			hqlInfo.setParameter("fdIsdefault", "1");
			hqlInfo.setParameter("fdType", "%2%");
			List<ThirdDingTodoTemplate> list = thirdDingTodoTemplateService
					.findList(hqlInfo);
			if (list.size() > 0) {
				return getDefalutModelBobyContent(todo, locale, list.get(0));
			}
		} catch (Exception e) {
			logger.error("获取默认工作通知模板失败！" + e.getMessage(), e);
		}

		String modelName = getSysNotifyTodoService().getModelNameText(todo);
		String creatorName = null;
		if (todo.getDocCreator() != null) {
			creatorName = todo.getDocCreator().getFdName(todo.getFdLang());
		}
		String createDate = com.sunbor.util.DateUtil
				.convertDateToString(todo.getFdCreateTime());

		String bodyContent = null;
		if (StringUtil.isNotNull(creatorName)) {
			bodyContent = "${fdCreatorName}:      " + creatorName + "\r\n"
					+ "${docCreateTime}:  " + createDate
					+ "\r\n" + "${module}:  " + modelName + "";
			bodyContent = bodyContent
					.replace("${fdCreatorName}",
							ResourceUtil.getStringValue(
									"sysNotifyCategory.fdCreatorName",
									"sys-notify", locale));
		} else {
			bodyContent = "${docCreateTime}:  " + createDate + "\r\n"
					+ "${module}:  " + modelName + "";
		}

		bodyContent = bodyContent
				.replace("${docCreateTime}",
						ResourceUtil.getStringValue(
								"sysNotifyCategory.docCreateTime", "sys-notify",
								locale))
				.replace("${module}",
						ResourceUtil.getStringValue(
								"sysNotifyCategory.type.module", "sys-notify",
								locale));

		return bodyContent;
	}

	private String getDefalutModelBobyContent(SysNotifyTodo todo, Locale locale,
			ThirdDingTodoTemplate thirdDingTodoTemplate) throws Exception {

		String fdDetail = thirdDingTodoTemplate.getFdDetail();
		if (logger.isDebugEnabled()) {
			logger.debug("fdDetail:" + fdDetail);
		}
		JSONObject fdDetailJSON = JSONObject.parseObject(fdDetail);
		JSONArray ja = fdDetailJSON.getJSONArray("data");
		String key;
		String fromForm;
		String name;
		JSONArray titleJA = new JSONArray();
		String result = "";
		for (int i = 0; i < ja.size(); i++) {
			name = "";
			key = ja.getJSONObject(i).getString("key");
			fromForm = ja.getJSONObject(i).getString("fromForm");

			titleJA = ja.getJSONObject(i).getJSONArray("title");
			// 异构系统的fdLang可能为空
			if (StringUtil.isNotNull(todo.getFdLang())) {
				for (int j = 0; j < titleJA.size(); j++) {
					String lang = titleJA.getJSONObject(j)
							.getString("lang");
					if (lang.contains(todo.getFdLang())) {
						name = titleJA.getJSONObject(j)
								.getString("value");
						break;
					}
				}

			}
			if (StringUtil.isNull(name)) {
				name = titleJA.getJSONObject(0)
						.getString("value");// 取默认语言
			}
			if (logger.isDebugEnabled()) {
				logger.debug("key:" + key + " fromForm:" + fromForm
						+ " name:" + name);
			}

			if ("fdSubject".equals(key)) {
				String content = todo.getFdSubject();
				switch (todo.getFdLevel()) {
				case 1:
					content = "【"
							+ DingUtil.getValueByLang(
									"sysNotifyTodo.level.taglib.1",
									"sys-notify", todo.getFdLang())
							+ "】" + content;
					break;
				case 2:
					content = "【"
							+ DingUtil.getValueByLang(
									"sysNotifyTodo.level.taglib.2",
									"sys-notify", todo.getFdLang())
							+ "】" + content;
					break;
				}
				result += name + "     :" + content + "\r\n";
			} else if ("docCreateTime".equals(key)
					|| "fdCreateTime".equals(key)) {
				if (logger.isDebugEnabled()) {
					logger.debug("todo.getFdCreateTime: "
							+ todo.getFdCreateTime());
				}
				result += name + "     :" + DateUtil.convertDateToString(
						todo.getFdCreateTime(),
						"yyyy-MM-dd HH:mm:ss") + "\r\n";
			} else {

				List<String> keyList = new ArrayList<String>();
				keyList.add(key);
				List<String> nameList = new ArrayList<String>();
				nameList.add(name);
				// 创建人 属于对象类型
				String rs = getModelItemValue(todo, key, true);
				result += name + "     :" + rs + "\r\n";
			}
		}
		return result;
	}

	private boolean checkNeedNotify(SysNotifyTodo todo){
		if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
			if (logger.isDebugEnabled()) {
				logger.debug("钉钉未开启集成，不推送消息...");
			}
			return false;
		}
		if (!"true".equals(DingConfig.newInstance().getUpdateMessageStatus())) {
			if (logger.isDebugEnabled()) {
				logger.debug("未开启更新工作通知功能，无需处理...");
			}
			return false;
		}
		//待阅无需更新待办
		if(todo.getFdType()==2){
			return false;
		}
		boolean notifyflag = false;
		if (todo.getFdType() == 2) {
			if (!"true".equals(
					DingConfig.newInstance().getDingTodotype2Enabled())) {
				// 待阅关闭,默认关闭，开启需要勾上
				if (logger.isDebugEnabled()) {
					logger.debug("未开启待阅消息推送到钉钉的消息中心...");
				}
			}else{
				notifyflag = true;
			}
		}
		if (todo.getFdType() == 1 || todo.getFdType() == 3) {
			if (!"true".equals(
					DingConfig.newInstance().getDingTodotype1Enabled())) {
				if (logger.isDebugEnabled()) {
					logger.debug("未开启待办消息推送到钉钉的消息中心...");
				}
			}else{
				notifyflag = true;
			}
		}
		return notifyflag;
	}

	@Override
    public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("clearTodoPersons ：" + todo.getFdId() + ","
					+ todo.getFdSubject());
		}
		if (!checkNeedNotify(todo)) {
			return;
		}
		updateMessageStatus(todo, null, ResourceUtil.getString("thirdDingNotifyMessage.status.done","third-ding-notify",getTodoLocale(todo)),true);
	}

	@Override
    public void remove(SysNotifyTodo todo) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("remove ：" + todo.getFdId() + "," + todo.getFdSubject());
		}
		if (!checkNeedNotify(todo)) {
			return;
		}

		updateMessageStatus(todo, null, ResourceUtil.getString("thirdDingNotifyMessage.status.remove", "third-ding-notify",getTodoLocale(todo)),true);

	}

	@Override
    public void setTodoDone(SysNotifyTodo todo) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"setTodoDone ：" + todo.getFdId() + "," + todo.getFdSubject());
		}
		if (!checkNeedNotify(todo)) {
			return;
		}
		updateMessageStatus(todo, null, ResourceUtil.getString("thirdDingNotifyMessage.status.done","third-ding-notify",getTodoLocale(todo)),true);
	}

	//场景二：ekp把待办中部分人员设置为已办
	@Override
    public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("setTodoDone ：" + todo.getFdId() + ","
					+ todo.getFdSubject() + "," + person.getFdLoginName());
		}
		if (!checkNeedNotify(todo)) {
			return;
		}
		List list = new ArrayList();
		list.add(person);
		List<SysOrgElement> persons = new ArrayList<SysOrgElement>();
		persons.add(person);
		updateMessageStatus(todo, persons, ResourceUtil.getString("thirdDingNotifyMessage.status.remove","third-ding-notify",getTodoLocale(todo)),true);
	}

	@Override
    public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
		if (!checkNeedNotify(todo)) {
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("setPersonsDone ：" + todo.getFdId() + ","
					+ todo.getFdSubject());
			String personsStr = "";
			if (persons != null && persons.size() > 0) {
				for (Object o : persons) {
					SysOrgPerson person = (SysOrgPerson) o;
					personsStr += person.getFdLoginName() + ",";
				}
			}
			logger.debug("setTodoDone ：" + todo.getFdId() + ","
					+ todo.getFdSubject() + "," + personsStr);
		}
		updateMessageStatus(todo, (List<SysOrgElement>) persons, ResourceUtil.getString("thirdDingNotifyMessage.status.done","third-ding-notify",getTodoLocale(todo)),true);
	}

	@Override
    public void updateTodo(SysNotifyTodo todo) throws Exception {
		if (!checkNeedNotify(todo)) {
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug(
					"updateTodo ：" + todo.getFdId() + "," + todo.getFdSubject());
		}
		updateMessageStatus(todo, null, getNotifyStatus(todo), false);
	}

	public void updateMessageStatus(SysNotifyTodo todo,
								 List<SysOrgElement> persons, String status_bar, boolean deleteMapping) throws ApiException, Exception {
		List<ThirdDingNotifyMessage> notifies = findMessage(todo,persons);
		String ekpUserId = null;
		if (persons != null && persons.size() > 0) {
			for (SysOrgElement user : persons) {
				String dind_userId = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
						.getDingUserIdByEkpUserId(
								user.getFdId());
				if (StringUtil.isNotNull(dind_userId)) {
					ekpUserId = user.getFdId();
					break;
				}
			}
		}

		net.sf.json.JSONObject jo = null;
		if (notifies != null && notifies.size() > 0) {
			OapiMessageCorpconversationStatusBarUpdateRequest req = null;
			int i = 0;
			for (ThirdDingNotifyMessage notify : notifies) {
				req = new OapiMessageCorpconversationStatusBarUpdateRequest();
				req.setAgentId(Long.parseLong(notify.getFdAgentId()));
				req.setTaskId(Long.parseLong(notify.getFdDingTaskId()));
				req.setStatusValue(status_bar);
				req.setStatusBg("0xFF78C06E");
				callDingdingUpdate(
						DingConstant.DING_PREFIX + "/topapi/message/corpconversation/status_bar/update",
						todo,
						req, notify, ekpUserId, deleteMapping);
				if((i++)%20==0){
					Thread.sleep(100);
				}
			}
		}
	}

	private void callDingdingUpdate(String url, SysNotifyTodo todo,
									OapiMessageCorpconversationStatusBarUpdateRequest req,
									ThirdDingNotifyMessage record, String ekpUserId, boolean deleteMapping) {
		String dingUrl = url
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		if (logger.isDebugEnabled()) {
			logger.debug("调钉钉接口：" + dingUrl);
		}
		DingApiService dingService = DingUtils.getDingApiService();
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdUrl(client.getRequestUrl());
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setFdSendTime(start);
		log.setFdNotifyData(((JSONObject)JSONObject.toJSON(req.getTextParams())).toJSONString());
		OapiMessageCorpconversationStatusBarUpdateResponse rsp;
		TransactionStatus deleteStatus = null;
		try {
			rsp = client.execute(req,
					dingService.getAccessToken(req.getAgentId()+""));
			deleteStatus = TransactionUtils
					.beginNewTransaction();
			log.setFdRtnMsg(rsp.getBody());
			net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(rsp.getBody());
			String requestId = null;
			if (jo.containsKey("request_id")) {
				requestId = jo.getString("request_id");
			}
			log.setFdRequestId(requestId);
			if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
				log.setFdResult(true);
				//暂挂的场景下不能删除映射
				if(deleteMapping) {
					try {
						getThirdDingNotifyMessageService().delete(record.getFdId());
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw e;
					}
				}
			} else {
				log.setFdResult(false);
				logger.warn("待办更新到钉钉创建失败。详细：" + jo.toString());
				addErrorQueue(todo, req, record.getFdUser().getFdId(),
						"messageUpdate", jo.toString(),deleteMapping);
			}
			TransactionUtils.getTransactionManager().commit(deleteStatus);
		} catch (Exception e) {
			if (deleteStatus != null) {
				try {
					TransactionUtils.getTransactionManager()
							.rollback(deleteStatus);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
			if (e instanceof ApiException) {
				logger.error(e.getMessage(), e);
				log.setFdResult(false);
				addErrorQueue(todo, req, record.getFdUser().getFdId(),
						"messageUpdate", e.getMessage(),deleteMapping);
			} else if (e instanceof HibernateObjectRetrievalFailureException) {
				logger.error(e.getMessage(), e);
				log.setFdResult(false);
				log.setFdRtnMsg(e.getMessage());
			} else {
				logger.error(e.getMessage(), e);
				log.setFdResult(false);
				log.setFdRtnMsg(e.getMessage());
				addErrorQueue(todo, req, record.getFdUser().getFdId(),
						"messageUpdate", e.getMessage(),deleteMapping);
			}

		} finally {
			Date end = new Date();
			log.setFdRtnTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			try {
				getThirdDingNotifyLogService().add(log);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private List<ThirdDingNotifyMessage> findMessage(SysNotifyTodo todo,
														   List<SysOrgElement> persons) throws Exception {
		String where = null;
		if (persons == null || persons.size() == 0) {
			where = "fdNotifyId='" + todo.getFdId() + "'";
		}else{
			where = "fdNotifyId='" + todo.getFdId() + "'";
			StringBuffer ids = new StringBuffer();
			for (SysOrgElement target : persons) {
				if(target==null) {
                    continue;
                }
				ids.append("'"+target.getFdId()+"',");
			}
			String fdIds = null;
			if(ids.length()>0 && ids.toString().endsWith(",")) {
                fdIds = ids.toString().substring(0, ids.length()-1);
            }
			if(StringUtil.isNotNull(fdIds)){
				where += " and fdUser.fdId in (" + fdIds + ")";
			}
		}
		TransactionStatus find = null;
		try {
			find = TransactionUtils
					.beginNewTransaction();
			List<ThirdDingNotifyMessage> notifies = getThirdDingNotifyMessageService()
					.findList(where, null);
			TransactionUtils.commit(find);
			return notifies;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (find != null) {
				try {
					TransactionUtils.getTransactionManager()
							.rollback(find);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
			throw e;
		}

	}


	private IThirdDingNotifyLogService thirdDingNotifyLogService;

	public IThirdDingNotifyLogService getThirdDingNotifyLogService() {
		if (thirdDingNotifyLogService == null) {
			thirdDingNotifyLogService = (IThirdDingNotifyLogService) SpringBeanUtil
					.getBean("thirdDingNotifyLogService");
		}
		return thirdDingNotifyLogService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	private IThirdDingNotifyQueueErrorService thirdDingNotifyQueueErrorService;

	public IThirdDingNotifyQueueErrorService
			getThirdDingNotifyQueueErrorService() {
		if (thirdDingNotifyQueueErrorService == null) {
			thirdDingNotifyQueueErrorService = (IThirdDingNotifyQueueErrorService) SpringBeanUtil
					.getBean("thirdDingNotifyQueueErrorService");
		}
		return thirdDingNotifyQueueErrorService;
	}

	private ISysNotifyTodoService sysNotifyTodoService = null;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil
					.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}



	private JSONObject buildRequestJson(
			TaobaoRequest req,
			String ekpUserId, SysNotifyTodo todo) {
		JSONObject data = new JSONObject();
		data.put("ekpUserId", ekpUserId);
		data.put("todoId", todo.getFdId());
		data.put("subject", todo.getFdSubject());
		data.put("req", JSON.toJSON(req).toString());
		return data;
	}

	private void addErrorQueue(SysNotifyTodo todo,
							   TaobaoRequest req,
			String ekpUserId, String method, String errorMsg, Boolean deleteMapping) {
		ThirdDingNotifyQueueError error = new ThirdDingNotifyQueueError();
		error.setFdSubject(todo.getFdSubject());
		error.setFdAppName(todo.getFdAppName());
		error.setFdTodoId(todo.getFdId());
		error.setFdErrorMsg(errorMsg);
		error.setFdDeleteMapping(deleteMapping);
		// error.setFdDingUserId(req.getUserid());
		error.setFdJson(buildRequestJson(req, ekpUserId, todo).toString());
		error.setFdCreateTime(new Date());
		error.setFdMethod(method);

		error.setFdRepeatHandle(
				ThirdDingNotifyQueueErrorConstants.NOTIFY_ERROR_REPEAT);
		error.setFdFlag(
				ThirdDingNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_ERROR);
		TransactionStatus addStatus = null;
		try {
			error.setFdUser(
					(SysOrgPerson) getSysOrgPersonService()
							.findByPrimaryKey(ekpUserId));
			addStatus = TransactionUtils
					.beginNewTransaction();
			getThirdDingNotifyQueueErrorService().add(error);
			TransactionUtils.getTransactionManager().commit(addStatus);

		} catch (Exception e) {
			logger.error("", e);
			if (addStatus != null) {
				try {
					TransactionUtils.getTransactionManager()
							.rollback(addStatus);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
		}
	}

	public void sendNotifyStatus(JSONObject o) throws Exception {
		sendNotify(o,true);
	}

	public void sendNotify(JSONObject o) throws Exception {
		sendNotify(o,false);
	}

	public void sendNotify(JSONObject o, boolean sendStatus) throws Exception {
		long time = System.currentTimeMillis();
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		String todoId = o.getString("todoId");
		log.setFdNotifyId(todoId);
		log.setDocSubject(o.getString("subject"));
		Date start = new Date();
		log.setFdSendTime(start);
		String reqStr = o.getString("req");
		// JSON jsonObject = (JSON) JSON.parse(reqStr);
		OapiMessageCorpconversationAsyncsendV2Request request = JSONObject
				.parseObject(reqStr,
						new TypeReference<OapiMessageCorpconversationAsyncsendV2Request>() {
						});
		try {
			SysNotifyTodo todo = (SysNotifyTodo) getSysNotifyTodoService()
					.findByPrimaryKey(todoId, null, true);
			if (todo == null) {
				throw new MessageSendException("待办已被删除，不进行重发");
			}
			DingApiService dingService = DingUtils.getDingApiService();
			Long agentId = NotifyUtil.getAgendId(todo);
			String result = dingService.messageSend(agentId,
					o.getString("ekpUserId"),
					request);
			if (logger.isDebugEnabled()) {
				logger.debug("消息发送返回消息：" + result);
			}
			JSONObject jo = JSONObject.parseObject(result);
			if (jo.containsKey("errcode")
					&& jo.getInteger("errcode") == 0) {
				log.setFdResult(true);
				if(sendStatus){
					addMessageRecord(todoId,log.getDocSubject(),request,o.getString("ekpUserId"),jo.getString("task_id"),agentId+"");
				}
			} else {
				log.setFdResult(false);
			}
			log.setFdRtnMsg(result);
			log.setFdUrl("/topapi/message/corpconversation/asyncsend_v2");
			Date end = new Date();
			log.setFdSendTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());

		} catch (Exception e) {
			logger.error("发送钉钉消息异常：", e);
			log.setFdRtnMsg(e.getMessage());
			log.setFdUrl("/topapi/message/corpconversation/asyncsend_v2");
			Date end = new Date();
			log.setFdSendTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			if (!(e instanceof MessageSendException)) {
				throw e;
			}
		} finally {
			TransactionStatus addStatus = null;
			try {
				addStatus = TransactionUtils
						.beginNewTransaction();
				getThirdDingNotifyLogService().add(log);
				TransactionUtils.getTransactionManager().commit(addStatus);
			} catch (Exception e) {
				logger.error("", e);
				if (addStatus != null) {
					try {
						TransactionUtils.getTransactionManager()
								.rollback(addStatus);
					} catch (Exception ex) {
						logger.error("---事务回滚出错---", ex);
					}
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("发送钉钉消息耗时：" + (System.currentTimeMillis() - time) + "毫秒");
		}
	}

	public void updateMessage(JSONObject o, Boolean deleteMapping) throws Exception {
		long time = System.currentTimeMillis();
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		String todoId = o.getString("todoId");
		log.setFdNotifyId(todoId);
		log.setDocSubject(o.getString("subject"));
		Date start = new Date();
		log.setFdSendTime(start);
		String reqStr = o.getString("req");
		OapiMessageCorpconversationStatusBarUpdateRequest request = JSONObject
				.parseObject(reqStr,
						new TypeReference<OapiMessageCorpconversationStatusBarUpdateRequest>() {
						});
		try {
			String dingUrl = DingConstant.DING_PREFIX + "/topapi/message/corpconversation/status_bar/update"
					+ DingUtil.getDingAppKeyByEKPUserId("?", o.getString("ekpUserId"));
			if (logger.isDebugEnabled()) {
				logger.debug("调钉钉接口：" + dingUrl);
			}
			DingApiService dingService = DingUtils.getDingApiService();
			ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
			OapiMessageCorpconversationStatusBarUpdateResponse response = client.execute(request, dingService.getAccessToken(request.getAgentId()+""));
			String result = response.getBody();
			if (logger.isDebugEnabled()) {
				logger.debug("消息发送返回消息：" + result);
			}
			boolean valid = JSONObject.isValid(result);
			if (valid) {
				JSONObject jo = JSONObject.parseObject(result);
				if (jo.containsKey("errcode")
						&& jo.getInteger("errcode") == 0) {
					log.setFdResult(true);
					if(deleteMapping!=null && deleteMapping==true) {
						try {
							getThirdDingNotifyMessageService().delete(request.getTaskId() + "", todoId);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
							throw e;
						}
					}
				} else {
					log.setFdResult(false);
				}
			} else {
				log.setFdResult(false);
			}
			log.setFdRtnMsg(result);
			log.setFdUrl("/topapi/message/corpconversation/status_bar/update");
			Date end = new Date();
			log.setFdSendTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());

		} catch (Exception e) {
			logger.error("发送钉钉消息异常：", e);
			log.setFdRtnMsg(e.getMessage());
			log.setFdUrl("/topapi/message/corpconversation/status_bar/update");
			Date end = new Date();
			log.setFdSendTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			if (!(e instanceof MessageSendException)) {
				throw e;
			}
		} finally {
			TransactionStatus addStatus = null;
			try {
				addStatus = TransactionUtils
						.beginNewTransaction();
				getThirdDingNotifyLogService().add(log);
				TransactionUtils.getTransactionManager().commit(addStatus);
			} catch (Exception e) {
				logger.error("", e);
				if (addStatus != null) {
					try {
						TransactionUtils.getTransactionManager()
								.rollback(addStatus);
					} catch (Exception ex) {
						logger.error("---事务回滚出错---", ex);
					}
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("发送钉钉消息耗时：" + (System.currentTimeMillis() - time) + "毫秒");
		}
	}

	private String getNotifyStatus(SysNotifyTodo todo){
		Locale locale = getTodoLocale(todo);
		String messageStatus = ResourceUtil.getStringValue("thirdDingNotifyMessage.status.todo","third-ding-notify",locale);
		switch (todo.getFdType()){
			case 2:
				messageStatus = ResourceUtil.getStringValue("thirdDingNotifyMessage.status.toread","third-ding-notify",locale);
				break;
			case 3:
				messageStatus = ResourceUtil.getStringValue("thirdDingNotifyMessage.status.suspend","third-ding-notify",locale);
				break;
		}
		return messageStatus;
	}


	private Locale getTodoLocale(SysNotifyTodo todo){
		String fdLang = todo.getFdLang();
		Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
		if (locale == null) {
			locale = ResourceUtil.getLocaleByUser();
		}
		return locale;
	}
}
