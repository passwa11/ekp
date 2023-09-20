package com.landray.kmss.third.weixin.work.notify;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.third.weixin.work.api.CorpGroupAppShareInfo;
import com.landray.kmss.third.weixin.work.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProviderExtend;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyLog;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyQueErr;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyTaskcard;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWork;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.model.api.WxArticle;
import com.landray.kmss.third.weixin.work.model.api.WxMessage;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SecureUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;


public class WxNotifyProvider extends BaseSysNotifyProviderExtend
		implements
		WxworkConstant {

	private static final Logger logger = LoggerFactory
			.getLogger(WxNotifyProvider.class);

	private WxworkApiService wxworkApiService = null;

	private void init() {
		wxworkApiService = WxworkUtils.getWxworkApiService();
	}

	private IWxworkOmsRelationService wxworkOmsRelationService = null;

	public void setWxworkOmsRelationService(
			IWxworkOmsRelationService wxworkOmsRelationService) {
		this.wxworkOmsRelationService = wxworkOmsRelationService;
	}

	private IThirdWeixinWorkService thirdWeixinWorkService;

	public void setThirdWeixinWorkService(IThirdWeixinWorkService thirdWeixinWorkService) {
		this.thirdWeixinWorkService = thirdWeixinWorkService;
	}

	private IThirdWeixinCgUserMappService thirdWeixinCgUserMappService;
	public void setThirdWeixinCgUserMappService(IThirdWeixinCgUserMappService thirdWeixinCgUserMappService) {
		this.thirdWeixinCgUserMappService = thirdWeixinCgUserMappService;
	}

	private boolean needSend(SysNotifyTodo todo, NotifyContext context) {
		if (!"true".equals(WeixinWorkConfig.newInstance().getWxEnabled())) {
			logger.debug("企业微信未开启集成，不推送消息...");
			return false;
		}
		boolean notifyflag = false;
		String appType = "";
		if (context != null) {
			appType = context.getFdAppType();
		}
		if (StringUtil.isNotNull(appType)) {
			if (appType.contains("all") || appType.contains("wxwork")) {
				notifyflag = true;
				logger.debug("已经设置参数fdAppType=" + appType + ",故强制推送消息...");
			}
		} else {
			if (!"true".equals(
					WeixinWorkConfig.newInstance().getWxTodoEnabled())) {
				return false;
			}
			if (todo.getFdType() == 2) {
				if (!"true".equals(WeixinWorkConfig.newInstance()
						.getWxTodoType2Enabled())) {
					// 待阅关闭,默认关闭，开启需要勾上
					return false;
				}
			}
			notifyflag = true;
		}

		if (!notifyflag) {
			logger.debug("企业微信不进行消息推送，消息参数fdAppType=" + appType + ",待办Id="
					+ todo.getFdId() + ",待办主题：" + todo.getFdSubject());
			return false;
		}
		return notifyflag;
	}

	@Override
    public void add(SysNotifyTodo todo, NotifyContext context)
			throws Exception {

		// WxNotifyThreadPoolManager manager = null;
		try {
			// manager = WxNotifyThreadPoolManager.getInstance();
			// manager.start();
			if (!needSend(todo, context)) {
				return;
			}

			long atime = System.currentTimeMillis();

			NotifyContextImp ctx = (NotifyContextImp) context;
			List notifyTargetList = ((NotifyContextImp) context).getNotifyPersons();
			if (notifyTargetList == null || notifyTargetList.isEmpty()) {
				logger.debug("通知人员为空不执行消息发送，通知标题为："+todo.getFdSubject()+"("+todo.getFdId()+")");
				return;
			} else {
				init();
				if (notifyTargetList.size() > 0) {
					WxMessage message = createWxMessage(todo);
					logger.debug("推送应用ID：" + message.getAgentId());
					List<String> ekpIds = new ArrayList<String>();
					if (notifyTargetList != null && notifyTargetList.size() > 0) {
						Iterator<?> it = ctx.getNotifyPersons().iterator();
						while (it.hasNext()) {
							SysOrgPerson sysOrgPerson = (SysOrgPerson) it.next();
							ekpIds.add(sysOrgPerson.getFdId());
						}
					}
					sendMessageInner(todo,context,message,ekpIds);
					sendMessageCorpGroup(todo,context,message,ekpIds);
				}
			}
			logger.debug("发送企业微信消息总耗时："+(System.currentTimeMillis()-atime)+"毫秒");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		} finally {
			// if (manager != null) {
			// manager.shutdown();
			// }
		}
	}

	/**
	 * 发送消息给内部用户
	 * @param todo
	 * @param context
	 * @param message
	 * @param ekpIds
	 * @throws Exception
	 */
	private void sendMessageInner(SysNotifyTodo todo, NotifyContext context, WxMessage message, List<String> ekpIds) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("fdEkpId", ekpIds));
		List<String> fdAppPkIds = wxworkOmsRelationService
				.findValue(hqlInfo);
		if (fdAppPkIds == null || fdAppPkIds.size() == 0) {
			logger.debug("通过EKP的fdId查找中间映射表发现找不到对应的微信人员("+ekpIds+")，请先维护中间映射表数据");
		}
		sendMessageByLoop(todo,context,message,fdAppPkIds,WeixinWorkConfig.newInstance().getWxCorpid());
	}

	/**
	 * 发送消息给下游组织用户
	 * @param todo
	 * @param context
	 * @param message
	 * @param ekpIds
	 * @throws Exception
	 */
	private void sendMessageCorpGroup(SysNotifyTodo todo, NotifyContext context, WxMessage message, List<String> ekpIds) throws Exception {
		String corpGroupIntegrateEnable = WeixinWorkConfig.newInstance().getCorpGroupIntegrateEnable();
		if(!"true".equals(corpGroupIntegrateEnable)){
			return;
		}
		Map<String, CorpGroupAppShareInfo> appShareInfoMap = wxworkApiService.getAppShareInfoMap();
		if(appShareInfoMap==null || appShareInfoMap.isEmpty()){
			logger.warn("获取应用共享信息失败，请检查上下游配置以及后台日志");
			return;
		}
		for(String corpId:appShareInfoMap.keySet()){
			List<String> fdUserIds = getWxUserIds(corpId,ekpIds);
			if (fdUserIds == null || fdUserIds.size() == 0) {
				logger.debug("通过EKP的fdId查找中间映射表发现找不到对应的微信人员("+ekpIds+")，所属组织："+corpId+"，请先维护中间映射表数据");
				continue;
			}
			sendMessageByLoop(todo,context,message,fdUserIds,corpId);
		}
	}

	private void sendMessageByLoop(SysNotifyTodo todo, NotifyContext context, WxMessage message, List<String> fdAppPkIds, String corpId){
		int loop = 0;
		StringBuffer usersloginName = new StringBuffer();
		for (int i = 0; i < fdAppPkIds.size(); i++) {
			usersloginName
					.append(fdAppPkIds.get(i) + "|");
			if ((i + 1) % 1000 == 0) {
				if (StringUtils
						.isNotEmpty(usersloginName.toString())) {
					message.setToUser(usersloginName.substring(0,
							usersloginName.length() - 1));
					logger.debug("发送微信消息的人员列表:" + usersloginName);
					try {
						// Thread thread = new Thread(
						// new WxNotifySendThread(
						// wxworkApiService,
						// message));
						// manager.submit(thread);

						// wxworkApiService.messageSend(message);
						loop++;
						sendMessage(message, todo, context,
								loop, corpId);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
				usersloginName.setLength(0);
			}
		}
		if (StringUtils.isNotEmpty(usersloginName.toString())) {
			message.setToUser(usersloginName.substring(0,
					usersloginName.length() - 1));
			logger.debug("发送微信消息的人员列表:" + usersloginName);
			try {
				// Thread thread = new Thread(new
				// WxNotifySendThread(
				// wxworkApiService, message));
				// manager.submit(thread);
				// wxworkApiService.messageSendTaskcard(
				// message, todo.getFdId());
				loop++;
				sendMessage(message, todo, context, loop, corpId);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	private JSONObject buildNewsReqObj(WxMessage msg) {
		WxMessage paramObj = null;
		List<WxArticle> ars = msg.getArticles();
		if (CollectionUtils.isNotEmpty(ars)) {
			WxMessage newsMsg = new WxMessage();
			Map<String, Object> newsMap = new HashMap<String, Object>();
			newsMap.put("articles", ars);

			newsMsg.setAgentId(msg.getAgentId());
			newsMsg.setToUser(msg.getToUser());
			newsMsg.setToTag(msg.getToTag());
			newsMsg.setToParty(msg.getToParty());
			newsMsg.setMsgType(WxworkConstant.CUSTOM_MSG_NEWS);
			newsMsg.setNews(newsMap);

			paramObj = newsMsg;
		}
		if (paramObj == null) {
			return null;
		}
		return (JSONObject) JSON.toJSON(paramObj);
	}

	private JSONObject buildTaskcardReqObj(WxMessage msg,
										   SysNotifyTodo todo, int loop) {
		JSONObject msgObj = new JSONObject();
		msgObj.put("touser", msg.getToUser());
		msgObj.put("msgtype", "taskcard");
		msgObj.put("agentid", msg.getAgentId());
		JSONObject taskcard = new JSONObject();
		taskcard.put("title", msg.getArticles().get(0).getTitle());
		taskcard.put("description", msg.getArticles().get(0).getDescription());
		taskcard.put("url", msg.getArticles().get(0).getUrl());
		taskcard.put("task_id", todo.getFdId() + "_" + loop);
		JSONArray btns = new JSONArray();
		JSONObject btn = new JSONObject();
		btn.put("key", "handle");
		Locale locale = SysLangUtil.getLocaleByShortName(todo.getFdLang());
		if (locale == null) {
			locale = ResourceUtil.getLocaleByUser();
		}
		if (todo.getFdType() == 2) {
			btn.put("name",
					ResourceUtil.getStringValue(
							"third.weixin.work.notify.taskcard.read",
							"third-weixin-work", locale));
			btn.put("replace_name", ResourceUtil.getStringValue(
					"third.weixin.work.notify.taskcard.hasRead",
					"third-weixin-work", locale));
		} else {
			btn.put("name", ResourceUtil.getStringValue(
					"third.weixin.work.notify.taskcard.todo",
					"third-weixin-work", locale));
			btn.put("replace_name", ResourceUtil.getStringValue(
					"third.weixin.work.notify.taskcard.hasDone",
					"third-weixin-work", locale));
		}
		btns.add(btn);
		taskcard.put("btn", btns);
		msgObj.put("taskcard", taskcard);
		return msgObj;
	}

	private JSONObject buildUpdateTaskcardReqObj(String touser, String agentId,
												 String task_id, Set<String> userIdSet) {
		JSONObject msgObj = new JSONObject();
		JSONArray userids = new JSONArray();
		String[] userIds = touser.split("\\|");
		if (userIdSet == null) {
			for (String userid : userIds) {
				userids.add(userid);
			}
		} else {
			for (String userid : userIds) {
				if (userIdSet.contains(userid)) {
					userids.add(userid);
				}
			}
		}
		if (userids.isEmpty()) {
			return null;
		}
		msgObj.put("userids", userids);
		msgObj.put("agentid", agentId);
		msgObj.put("task_id", task_id);
		msgObj.put("clicked_key", "handle");
		return msgObj;
	}

	private void sendMessage(WxMessage message, SysNotifyTodo todo,
							 NotifyContext context, int loop, String corpId) {
		Long endTime = context.getFdEndTime();
		logger.debug("endTime:" + endTime);
		if (endTime != null && endTime != 0) {
			if (System.currentTimeMillis() > endTime) {
				logger.warn("当前时间已经超过了消息的截止时间，不再推送消息...");
				logger.warn("endTime:" + endTime);
				return;
			}
		}
		String sendType = null;
		String errMsg = null;
		JSONObject result = null;
		try {
			WeixinWorkConfig config = new WeixinWorkConfig();
			sendType = config.getWxNotifySendType();
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}

		// todo的fdId为空，则强制用图文方式
		if(StringUtil.isNull(todo.getFdId())){
			sendType="news";
		}

		Date start = new Date();
		JSONObject msgObj = null;
		try {
			if ("taskcard".equals(sendType)) {
				msgObj = buildTaskcardReqObj(message, todo, loop);
			} else {
				msgObj = buildNewsReqObj(message);
			}
			result = wxworkApiService.messageSend(message.getAgentId(),
					msgObj, corpId);
			if (result == null || result.getIntValue("errcode") != 0) {
				throw new Exception(result.toString());
			} else {
				if ("taskcard".equals(sendType)) {
					try {
						addTaskcard(todo, message.getToUser(), msgObj
								.getJSONObject("taskcard").getString("task_id"), corpId);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
			try {
				addNotifyQueErr(todo, msgObj.toString(), "send",
						errMsg, corpId);
			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
		} finally {
			try {
				addLog("send", start, msgObj,
						result, errMsg, todo.getFdId(), todo.getFdSubject(), corpId);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private WxMessage createWxMessage(SysNotifyTodo todo) throws Exception {
		return createWxNewsMessage(todo);
	}

	private WxMessage createWxNewsMessage(SysNotifyTodo todo)
			throws Exception {
		String fdLang = todo.getFdLang();
		Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
		if (locale == null) {
			locale = ResourceUtil.getLocaleByUser();
		}
		String wxagentId = getNotifyPushAgentId(todo);
		WxMessage message = new WxMessage();
		message.setAgentId(wxagentId);
		message.setMsgType(WxworkConstant.CUSTOM_MSG_NEWS);
		String domainName = WeixinWorkConfig.newInstance().getWxDomain();
		if(StringUtil.isNull(domainName)) {
            domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
        }
		if(domainName.endsWith("/")) {
            domainName = domainName.substring(0, domainName.length()-1);
        }
		String viewUrl = null;
		String purl = "/third/weixin/work/sso/pc_message.jsp?oauth="+OAUTH_EKP_FLAG;
		if(StringUtil.isNotNull(todo.getFdId())){
			viewUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
					+ todo.getFdId() + "&oauth=" + OAUTH_EKP_FLAG;
			if (StringUtils.isNotEmpty(domainName)) {
				viewUrl = domainName + viewUrl;
			} else {
				viewUrl = StringUtil.formatUrl(viewUrl);
			}
			purl = purl + "&fdTodoId=" + todo.getFdId();
		}else{
			if(StringUtil.isNull(domainName)) {
                domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
            }
			if(domainName.endsWith("/")) {
                domainName = domainName.substring(0, domainName.length()-1);
            }
			viewUrl = StringUtil.formatUrl(todo.getFdLink(), domainName);
			if(viewUrl.indexOf("?")==-1){
				viewUrl = viewUrl + "?oauth=" + OAUTH_EKP_FLAG;
			}else{
				viewUrl = viewUrl + "&oauth=" + OAUTH_EKP_FLAG;
			}
			purl = purl + "&url=" + SecureUtil.BASE64Encoder(StringUtil.formatUrl(todo.getFdLink(), domainName));
		}
		WxArticle article = new WxArticle();
//		viewUrl = wxCpService.oauth2buildAuthorizationUrl(domainName + purl, null);
		article.setUrl(domainName + purl);

		StringBuffer sb = new StringBuffer();
		if (todo.getDocCreator() != null) {
			sb.append("${fdCreatorName}:      " + todo.getDocCreator().getFdName(fdLang));
			sb.append("\r\n");
		}

		sb.append("${docCreateTime}:  "
				+ DateUtil.convertDateToString(todo.getFdCreateTime(),
				"yyyy-MM-dd"));
		sb.append("\r\n");
		SysDictModel sysDict = SysDataDict.getInstance()
				.getModel(todo.getFdModelName());
		String modelName = "";
		if (sysDict != null) {
			modelName = ResourceUtil.getStringValue(sysDict.getMessageKey(), null, locale);
		}
		if (StringUtils.isNotBlank(modelName)) {
			sb.append("${module}:  " + modelName);
		}

		String description = sb.toString()
				.replace("${fdCreatorName}",
						ResourceUtil.getStringValue("sysNotifyCategory.fdCreatorName", "sys-notify", locale))
				.replace("${docCreateTime}",
						ResourceUtil.getStringValue("sysNotifyCategory.docCreateTime", "sys-notify", locale))
				.replace("${module}",
						ResourceUtil.getStringValue("sysNotifyCategory.type.module", "sys-notify", locale));
		article.setDescription(description);
		String title = todo.getFdSubject();
		Locale local = null;
		if (StringUtil.isNotNull(todo.getFdLang())
				&& todo.getFdLang().split("-").length == 2) {
			local = new Locale(todo.getFdLang().split("-")[0],
					todo.getFdLang().split("-")[1]);
		} else {
			local = locale;
		}
		switch (todo.getFdLevel()) {
			case 1:
				title = "【"+ResourceUtil.getStringValue("sysNotifyTodo.level.taglib.1", "sys-notify", local)+"】" + title;
				break;
			case 2:
				title = "【"+ResourceUtil.getStringValue("sysNotifyTodo.level.taglib.2", "sys-notify", local)+"】" + title;
				break;
			// default:
			// title =
			// "【"+ResourceUtil.getStringValue("sysNotifyTodo.level.taglib.3",
			// "sys-notify", local)+"】" + title;
			// break;
		}
		article.setTitle(title);
		message.getArticles().add(article);

		if (logger.isDebugEnabled()) {
			logger.debug("wxMessage::" + JSON.toJSONString(message));
		}
		return message;
	}

	private WxMessage createWxTextMessage(SysNotifyTodo todo)
			throws Exception {
		String fdLang = todo.getFdLang();
		Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
		if (locale == null) {
			locale = ResourceUtil.getLocaleByUser();
		}
		WxMessage message = new WxMessage();
		String wxagentId = getNotifyPushAgentId(todo);
		message.setAgentId(wxagentId);
		message.setMsgType(WxworkConstant.CUSTOM_MSG_TEXT);
		String domainName = WeixinWorkConfig.newInstance().getWxDomain();
		if(StringUtil.isNull(domainName)) {
            domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
        }
		if(domainName.endsWith("/")) {
            domainName = domainName.substring(0, domainName.length()-1);
        }
		String viewUrl = null;
		String purl = "/third/weixin/work/sso/pc_message.jsp?oauth="+OAUTH_EKP_FLAG;
		if(StringUtil.isNotNull(todo.getFdId())){
			viewUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
					+ todo.getFdId() + "&oauth=" + OAUTH_EKP_FLAG;
			if (StringUtils.isNotEmpty(domainName)) {
				viewUrl = domainName + viewUrl;
			} else {
				viewUrl = StringUtil.formatUrl(viewUrl);
			}
			purl = purl + "&fdTodoId=" + todo.getFdId();
		}else{
			if(StringUtil.isNull(domainName)) {
                domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
            }
			if(domainName.endsWith("/")) {
                domainName = domainName.substring(0, domainName.length()-1);
            }
			viewUrl = StringUtil.formatUrl(todo.getFdLink(), domainName);
			if(viewUrl.indexOf("?")==-1){
				viewUrl = viewUrl + "?oauth=" + OAUTH_EKP_FLAG;
			}else{
				viewUrl = viewUrl + "&oauth=" + OAUTH_EKP_FLAG;
			}
			purl = purl + "&url=" + SecureUtil.BASE64Encoder(StringUtil.formatUrl(todo.getFdLink(), domainName));
		}
		viewUrl = wxworkApiService
				.oauth2buildAuthorizationUrl(domainName + purl, null);
		// viewUrl = wxCpService.oauth2buildAuthorizationUrl(viewUrl, null);
		StringBuffer content = new StringBuffer();
		String title = todo.getFdSubject();
		switch (todo.getFdLevel()) {
			case 1:
				title = ResourceUtil.getString("enums.notify.level.1", "third-weixin") + title;
				break;
			case 2:
				title = ResourceUtil.getString("enums.notify.level.2", "third-weixin") + title;
				break;
			default:
				title = ResourceUtil.getString("enums.notify.level.3", "third-weixin") + title;
				break;
		}
		content.append(title).append("\r\n");
		if (todo.getDocCreator() != null) {
			content.append("${fdCreatorName}：").append(todo.getDocCreator().getFdName(fdLang));
			content.append("\n");
		}
		content.append("${docCreateTime}:  "
				+ DateUtil.convertDateToString(todo.getFdCreateTime(),
				"yyyy-MM-dd"));
		content.append("\n");
		SysDictModel sysDict = SysDataDict.getInstance()
				.getModel(todo.getFdModelName());
		String modelName = "";
		if (sysDict != null) {
			modelName = ResourceUtil.getStringValue(sysDict.getMessageKey(), null, locale);
		}
		if (StringUtils.isNotBlank(modelName)) {
			content.append("${module}:  " + modelName);
			content.append("\r\n");
		}

		content.append("<a href='" + viewUrl + "'>"
				+ ResourceUtil.getStringValue("third.wx.notify.viewall", "third-weixin", locale) + "</a>");
		String c = content.toString()
				.replace("${fdCreatorName}",
						ResourceUtil.getStringValue("sysNotifyCategory.fdCreatorName", "sys-notify", locale))
				.replace("${docCreateTime}",
						ResourceUtil.getStringValue("sysNotifyCategory.docCreateTime", "sys-notify", locale))
				.replace("${module}",
						ResourceUtil.getStringValue("sysNotifyCategory.type.module", "sys-notify", locale));
		message.setContent(c);
		if (logger.isDebugEnabled()) {
			logger.debug("wxMessage::" + JSON.toJSONString(message));
		}
		return message;
	}

	/**
	 * <p>获取待阅推送的应用id</p>
	 * @return
	 * @author 孙佳
	 * @throws Exception
	 */
	private String getNotifyPushAgentId(SysNotifyTodo todo) throws Exception {
		String wxAgentId = WeixinWorkConfig.newInstance().getWxAgentid();
		if (todo == null) {
			return wxAgentId;
		}
		if (todo.getFdType() == 2) {
			//待阅
			wxAgentId = WeixinWorkConfig.newInstance().getWxToReadAgentid();
			String wxToReadPre = WeixinWorkConfig.newInstance().getWxToReadPre();
			if(StringUtil.isNull(wxToReadPre) || !"true".equals(wxToReadPre)){
				return wxAgentId;
			}
			String workAgentId = getWxWorkByModel(todo);
			if(StringUtil.isNotNull(workAgentId)){
				return workAgentId;
			}
		}
		return wxAgentId;
	}

	private String getWxWorkByModel(SysNotifyTodo todo) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		List<ThirdWeixinWork> wxWorkList = thirdWeixinWorkService.findList(hqlInfo);
		if (null == wxWorkList || wxWorkList.size() <= 0) {
			return null;
		}
		for (ThirdWeixinWork weixinWork : wxWorkList) {
			if (StringUtil.isNotNull(weixinWork.getFdUrlPrefix())) {
				String[] fdUrlPrefix = weixinWork.getFdUrlPrefix().split(";");
				for (int i = 0; i < fdUrlPrefix.length; i++) {
					if (todo.getFdLink().indexOf(fdUrlPrefix[i]) > -1) {
						return weixinWork.getFdAgentid();
					}
				}
			}
		}
		return null;
	}

	@Override
    public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
		logger.debug("clearTodoPersons " + todo.getFdId() + ","
				+ todo.getFdSubject());
		updateTaskcard(todo, null);
	}

	@Override
    public void remove(SysNotifyTodo todo) throws Exception {
		logger.debug("remove " + todo.getFdId() + "," + todo.getFdSubject());
		updateTaskcard(todo, null);
	}

	private void updateTaskcard(SysNotifyTodo todo, Set<String> userIds)
			throws Exception {
		if (!needSend(todo, null)) {
			return;
		}
		String sendType = null;
		try {
			WeixinWorkConfig config = new WeixinWorkConfig();
			sendType = config.getWxNotifySendType();
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
		if (!"taskcard".equals(sendType) || StringUtil.isNull(todo.getFdId())) {
			return;
		}
		if (userIds != null) {
			for (String userId : userIds) {
				ThirdWeixinNotifyTaskcard taskcard = thirdWeixinNotifyTaskcardService
						.findByNotifyIdAndUserid(todo.getFdId(), userId);
				if (taskcard != null) {
					Set<String> userIds_tmp = new HashSet<>();
					userIds_tmp.add(userId);
					updateTaskcard(todo, taskcard, userIds_tmp);
				}
			}
		} else {
			List<ThirdWeixinNotifyTaskcard> list = thirdWeixinNotifyTaskcardService
					.findByNotifyId(todo.getFdId());
			if (list != null && !list.isEmpty()) {
				init();
				for (ThirdWeixinNotifyTaskcard taskcard : list) {
					updateTaskcard(todo, taskcard, null);
				}
			}
		}
	}

	private void updateTaskcard(SysNotifyTodo todo,
								ThirdWeixinNotifyTaskcard taskcard, Set<String> userIds) {
		Date start = new Date();
		JSONObject result = null;
		JSONObject reqData = null;
		String errMsg = null;
		try {
			String task_id = taskcard.getFdTaskcardId();
			String touser = taskcard.getFdTouser();
			String agentId = getNotifyPushAgentId(todo);
			reqData = buildUpdateTaskcardReqObj(touser, agentId, task_id,
					userIds);
			if (reqData == null) {
				return;
			}
			result = wxworkApiService.updateTaskcard(agentId, reqData,taskcard.getFdCorpId());
			if (result == null || result.getIntValue("errcode") != 0) {
				throw new Exception(result==null?"":result.toString());
			} else {
				if(userIds==null) {
					try {
						thirdWeixinNotifyTaskcardService.delete(taskcard);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
			try {
				addNotifyQueErr(todo, reqData.toString(), "update_taskcard",
						errMsg, taskcard.getFdCorpId());
			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
		} finally {
			try {
				if (reqData != null) {
					addLog("update_taskcard", start, reqData, result,
							errMsg, todo.getFdId(), todo.getFdSubject(),taskcard.getFdCorpId());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
    public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
		logger.debug(
				"removeDonePerson " + todo.getFdId() + "," + todo.getFdSubject());
		List<SysOrgPerson> persons = new ArrayList<SysOrgPerson>();
		persons.add(person);
		updateTaskcard(todo, getWxUserIds(persons));
		updateTaskcardCorpGroup(todo,persons);
	}

	@Override
    public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
		logger.debug(
				"setPersonsDone " + todo.getFdId() + "," + todo.getFdSubject());
		updateTaskcard(todo, getWxUserIds(persons));
		updateTaskcardCorpGroup(todo,persons);
	}

	@Override
    public void setTodoDone(SysNotifyTodo todo) throws Exception {
		logger.debug(
				"setTodoDone " + todo.getFdId() + "," + todo.getFdSubject());
		updateTaskcard(todo, null);
	}

	/**
	 * 获取下游组织的userid列表
	 * @param corpId 下游组织企业id
	 * @param ekpIds ekp用户ID列表
	 * @return
	 */
	private List<String> getWxUserIds(String corpId, List<String> ekpIds) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdUserId");
		hqlInfo.setWhereBlock("fdCorpId=:corpId and "+ HQLUtil.buildLogicIN("fdEkpId", ekpIds));
		hqlInfo.setParameter("corpId",corpId);
		List<String> fdUserIds = thirdWeixinCgUserMappService
				.findValue(hqlInfo);
		return fdUserIds;
	}

	/**
	 * 更新下游组织的消息状态
	 * @param todo 待办消息
	 * @param persons 需要更新的用户列表
	 * @throws Exception
	 */
	private void updateTaskcardCorpGroup(SysNotifyTodo todo, List persons) throws Exception {
		String corpGroupIntegrateEnable = WeixinWorkConfig.newInstance().getCorpGroupIntegrateEnable();
		if(!"true".equals(corpGroupIntegrateEnable)){
			return;
		}
		Map<String, CorpGroupAppShareInfo> appShareInfoMap = wxworkApiService.getAppShareInfoMap();
		if(appShareInfoMap==null || appShareInfoMap.isEmpty()){
			logger.warn("获取应用共享信息失败，请检查上下游配置以及后台日志");
			return;
		}
		for(String corpId:appShareInfoMap.keySet()){
			List<String> ekpIds = new ArrayList<String>();
			for (SysOrgPerson person : (List<SysOrgPerson>)persons) {
				ekpIds.add(person.getFdId());
			}
			List<String> fdUserIds = getWxUserIds(corpId,ekpIds);
			if (fdUserIds == null || fdUserIds.size() == 0) {
				logger.debug("通过EKP的fdId查找中间映射表发现找不到对应的微信人员("+ekpIds+")，所属组织："+corpId+"，请先维护中间映射表数据");
				continue;
			}
			updateTaskcard(todo, new HashSet<>(fdUserIds));
		}
	}

	private void addNotifyQueErr(SysNotifyTodo todo, String reqData,
								 String method,
								 String errorMsg, String corpId) {

		ThirdWeixinNotifyQueErr error = new ThirdWeixinNotifyQueErr();
		error.setFdSubject(todo.getFdSubject());
		error.setFdNotifyId(todo.getFdId());
		error.setFdErrMsg(errorMsg);
		error.setFdData(reqData);
		error.setFdMethod(method);
		error.setFdApiType("message");
		error.setDocCreateTime(new Date());
		error.setFdRepeatHandle(0);
		error.setFdFlag("1");
		error.setFdCorpId(corpId);
		TransactionStatus addStatus = null;
		try {
			addStatus = TransactionUtils.beginNewTransaction();
			thirdWeixinNotifyQueErrService.add(error);
			TransactionUtils.getTransactionManager().commit(addStatus);
		} catch (Exception e) {
			if(addStatus != null){
				TransactionUtils.rollback(addStatus);
			}
			logger.error(e.getMessage(), e);
		}
	}

	private void addLog(String method, Date start, JSONObject reqObj,
						JSONObject resultObj, String errMsg, String todoId, String subject, String corpId)
			throws Exception {

		ThirdWeixinNotifyLog log = new ThirdWeixinNotifyLog();
		log.setFdReqDate(start);
		log.setFdResDate(new Date());
		log.setFdSubject(subject);
		log.setFdExpireTime(System.currentTimeMillis() - start.getTime());
		log.setFdMethod(method);
		log.setFdCorpId(corpId);
		if ("send".equals(method)) {
			log.setFdUrl("/cgi-bin/message/send");
		} else if ("update_taskcard".equals(method)) {
			log.setFdUrl("/cgi-bin/message/update_taskcard");
		}
		log.setFdApiType("message");
		log.setFdNotifyId(todoId);
		log.setFdReqData(reqObj.toString());
		log.setFdResData(resultObj == null ? null : resultObj.toString());
		log.setFdErrMsg(errMsg);
		if (StringUtil.isNotNull(errMsg)) {
			log.setFdResult(2);
		} else {
			log.setFdResult(1);
		}
		thirdWeixinNotifyLogService.add(log);
	}

	private void addTaskcard(SysNotifyTodo todo, String toUser,
							 String taskcardId, String corpId)
			throws Exception {
		ThirdWeixinNotifyTaskcard taskcard = new ThirdWeixinNotifyTaskcard();
		taskcard.setFdNotifyId(todo.getFdId());
		taskcard.setFdSubject(todo.getFdSubject());
		taskcard.setFdTaskcardId(taskcardId);
		taskcard.setDocCreateTime(new Date());
		taskcard.setFdTouser(toUser);
		taskcard.setFdCorpId(corpId);
		thirdWeixinNotifyTaskcardService.add(taskcard);
	}

	private IThirdWeixinNotifyQueErrService thirdWeixinNotifyQueErrService;

	private IThirdWeixinNotifyLogService thirdWeixinNotifyLogService;

	private IThirdWeixinNotifyTaskcardService thirdWeixinNotifyTaskcardService;

	public IThirdWeixinNotifyQueErrService getThirdWeixinNotifyQueErrService() {
		return thirdWeixinNotifyQueErrService;
	}

	public void setThirdWeixinNotifyQueErrService(
			IThirdWeixinNotifyQueErrService thirdWeixinNotifyQueErrService) {
		this.thirdWeixinNotifyQueErrService = thirdWeixinNotifyQueErrService;
	}

	public IThirdWeixinNotifyLogService getThirdWeixinNotifyLogService() {
		return thirdWeixinNotifyLogService;
	}

	public void setThirdWeixinNotifyLogService(
			IThirdWeixinNotifyLogService thirdWeixinNotifyLogService) {
		this.thirdWeixinNotifyLogService = thirdWeixinNotifyLogService;
	}

	public IThirdWeixinNotifyTaskcardService
	getThirdWeixinNotifyTaskcardService() {
		return thirdWeixinNotifyTaskcardService;
	}

	public void setThirdWeixinNotifyTaskcardService(
			IThirdWeixinNotifyTaskcardService thirdWeixinNotifyTaskcardService) {
		this.thirdWeixinNotifyTaskcardService = thirdWeixinNotifyTaskcardService;
	}

	private Set<String> getWxUserIds(List<SysOrgPerson> persons)
			throws Exception {
		List<String> ekpIds = new ArrayList<String>();
		for (SysOrgPerson person : persons) {
			ekpIds.add(person.getFdId());
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("fdEkpId", ekpIds));
		List<WxworkOmsRelationModel> omsModels = wxworkOmsRelationService
				.findList(hqlInfo);
		Set<String> wxUserIds = new HashSet<String>();
		if (omsModels == null || omsModels.size() == 0) {
			return wxUserIds;
		}
		for (int i = 0; i < omsModels.size(); i++) {
			wxUserIds.add(omsModels.get(i).getFdAppPkId());
		}
		return wxUserIds;
	}

	public void sendMessage(String notifyId, String subject,
							JSONObject msgObj, String corpId) throws Exception {
		String sendType = null;
		String errMsg = null;
		JSONObject result = null;

		Date start = new Date();
		try {
			init();
			SysNotifyTodo todo = (SysNotifyTodo) sysNotifyTodoService
					.findByPrimaryKey(notifyId, null, true);
			String agentId = getNotifyPushAgentId(todo);
			result = wxworkApiService.messageSend(agentId,
					msgObj, corpId);
			if (result == null || result.getIntValue("errcode") != 0) {
				throw new Exception(result.toString());
			} else {
				if ("taskcard".equals(sendType)) {
					try {
						addTaskcard(todo, msgObj.getString("touser"), msgObj
								.getJSONObject("taskcard")
								.getString("task_id"),corpId);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
			throw e;
		} finally {
			try {
				addLog("send", start, msgObj,
						result, errMsg, notifyId, subject, corpId);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void updateTaskcard(String notifyId, String subject,
							   JSONObject msgObj, String corpId) throws Exception {
		Date start = new Date();
		JSONObject result = null;
		String errMsg = null;
		try {
			init();
			SysNotifyTodo todo = (SysNotifyTodo) sysNotifyTodoService
					.findByPrimaryKey(notifyId, null, true);
			String agentId = getNotifyPushAgentId(todo);
			result = wxworkApiService.updateTaskcard(agentId, msgObj, corpId);
			if (result == null || result.getIntValue("errcode") != 0) {
				throw new Exception(result.toString());
			}else{
				try {
					String taskcardId = msgObj.getString("task_id");
					ThirdWeixinNotifyTaskcard taskcard = thirdWeixinNotifyTaskcardService.findByTaskcardId(corpId,taskcardId);
					if(taskcard!=null) {
						thirdWeixinNotifyTaskcardService.delete(taskcard);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
			throw e;
		} finally {
			try {
				addLog("update_taskcard", start, msgObj, result,
						errMsg, notifyId, subject, corpId);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public ISysNotifyTodoService getSysNotifyTodoService() {
		return sysNotifyTodoService;
	}

	public void setSysNotifyTodoService(
			ISysNotifyTodoService sysNotifyTodoService) {
		this.sysNotifyTodoService = sysNotifyTodoService;
	}

	private ISysNotifyTodoService sysNotifyTodoService;

}
