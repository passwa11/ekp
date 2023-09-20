package com.landray.kmss.third.feishu.notify;

import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProviderExtend;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.feishu.constant.ThirdFeishuConstant;
import com.landray.kmss.third.feishu.model.ThirdFeishuConfig;
import com.landray.kmss.third.feishu.model.ThirdFeishuMsgMapp;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyLog;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyQueueErr;
import com.landray.kmss.third.feishu.service.IThirdFeishuMsgMappService;
import com.landray.kmss.third.feishu.service.IThirdFeishuNotifyLogService;
import com.landray.kmss.third.feishu.service.IThirdFeishuNotifyQueueErrService;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.third.feishu.util.ThirdFeishuApiUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.*;

public class ThirdFeishuTodoMessageProviderV2 extends BaseSysNotifyProviderExtend implements IThirdFeishuTodoResendProvider {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuTodoMessageProviderV2.class);
	
	private IThirdFeishuService thirdFeishuService;

	@Override
    public void add(SysNotifyTodo todo, NotifyContext context) throws Exception {
		long atime = System.currentTimeMillis();
		if (!"true"
				.equals(ThirdFeishuConfig.newInstance().getFeishuEnabled())) {
			logger.debug("飞书未开启集成，不推送消息...");
			return;
		}
		boolean notifyflag = false;
		logger.debug("开始向飞书推送消息...");
		String appType = context.getFdAppType();
		if (StringUtil.isNotNull(appType) ) {
			if (appType.contains("all")) {
				notifyflag = true;
				logger.debug("已经设置参数fdAppType=" + appType + ",故强制推送消息...");
			}
		}else if(StringUtil.isNull(appType)){
			
			if (todo.getFdType() == 2) {
				if (!"true".equals(
						ThirdFeishuConfig.newInstance()
								.getFeishuToreadMsgEnabled())) {
					// 待阅关闭,默认关闭，开启需要勾上
					logger.debug("未开启待阅消息推送到飞书的消息中心...");
				}else{
					notifyflag = true;
				}
			}
			if (todo.getFdType() == 1 || todo.getFdType() == 3) {
				if (!"true".equals(
						ThirdFeishuConfig.newInstance()
								.getFeishuTodoMsgEnabled())) {
					// 待阅关闭,默认关闭，开启需要勾上
					logger.debug("未开启待办消息推送到飞书的消息中心...");
				}else{
					notifyflag = true;
				}
			}
		}
		if(!notifyflag){
			logger.debug("都未开启待阅、待办消息推送,参数fdAppType=" + appType + ",待办Id="
					+ todo.getFdId() + ",待办主题：" + todo.getFdSubject());
			return;
		}
		
		NotifyContextImp ctx = (NotifyContextImp) context;
		List notifyTargetList = ((NotifyContextImp) context).getNotifyPersons();
		if (notifyTargetList == null || notifyTargetList.isEmpty()) {
			logger.warn("通知人员为空不执行消息发送，通知标题为：" + todo.getFdSubject() + "("
					+ todo.getFdId() + ")");
			return;
		}else{

			List<String> ekpIds = new ArrayList<String>();
			if (notifyTargetList != null && notifyTargetList.size() > 0) {
				Iterator<?> it = ctx.getNotifyPersons().iterator();
				while (it.hasNext()) {
					SysOrgPerson sysOrgPerson = (SysOrgPerson) it.next();
					ekpIds.add(sysOrgPerson.getFdId());
				}
			}
			Map<String, String> userIdMap = ThirdFeishuApiUtil
					.getFeishuUserIdMap(ekpIds);
			if (userIdMap == null || userIdMap.size() == 0) {
				logger.error("通过EKP的fdId查找人员映射表发现找不到对应的飞书人员(" + ekpIds
						+ ")，请先检查组织同步是否正常");
				return;
			}

			for (String userId : userIdMap.keySet()) {
				sendNotify(todo, userId, userIdMap.get(userId));
			}
		}
		logger.debug(
				"发送飞书消息总耗时：" + (System.currentTimeMillis() - atime) + "毫秒");
	}
	
	/**
	 * 发送卡片消息
	 */
	private void sendNotify(SysNotifyTodo todo,
			String userId, String ekpId)
			throws Exception {
		long time = System.currentTimeMillis();
		ThirdFeishuNotifyLog log = new ThirdFeishuNotifyLog();
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setDocCreateTime(start);
		try {
			String title = getTodoTitle(todo);
			String fdLang = todo.getFdLang();
			Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
			if (locale == null) {
				locale = ResourceUtil.getLocaleByUser();
			}

			thirdFeishuService.sendCardMessageV1(title, getDocSubject(todo),
					ThirdFeishuApiUtil.getViewUrl(todo),
					ThirdFeishuApiUtil.getModelName(todo),
					getCreatorName(todo.getDocCreator(), locale),
					getCreatorTime(todo.getFdCreateTime(), locale), userId,
					locale, todo.getFdType(), log);
			if (log.getFdResult() == null) {
				log.setFdResult(1);
			}
			Date end = new Date();
			log.setFdRtnTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			String reqData = log.getFdReqData();
			JSONObject o = JSONObject.fromObject(reqData);
			addMsgMapp(todo, userId, ekpId, log.getFdMessageId(),
					o.getString("content"), fdLang);
		} catch (Exception e) {
			logger.error("发送飞书消息异常：", e);
			log.setFdErrMsg(e.getMessage());
			log.setFdResult(2);
			Date end = new Date();
			log.setFdRtnTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			String reqData = log.getFdReqData();
			addErrorQueue(todo, buildTodoReqData(todo, log.getFdUrl(), reqData,
					ekpId, userId, null, false),
					e.getMessage(), "sendCardMessage");
		} finally {
			TransactionStatus addStatus = null;
			try {
				addStatus = TransactionUtils
						.beginNewTransaction();
				thirdFeishuNotifyLogService.add(log);
				TransactionUtils.getTransactionManager().commit(addStatus);
			} catch (Exception e) {
				logger.error("", e);
				TransactionUtils.getTransactionManager().rollback(addStatus);
			}
		}
		logger.debug(
				"发送飞书消息耗时：" + (System.currentTimeMillis() - time) + "毫秒");
	}
	
	private String buildTodoReqData(SysNotifyTodo todo,
			String url,
			String reqData, String ekpUserId, String feishuUserId,
			String messageId,
			boolean delMapping) {
		JSONObject todoJson = new JSONObject();
		todoJson.put("id", todo.getFdId());
		todoJson.put("subject", todo.getFdSubject());
		todoJson.put("lang", todo.getFdLang());
		JSONObject todoReqJson = new JSONObject();
		todoReqJson.put("todo", todoJson);
		todoReqJson.put("url", url);
		todoReqJson.put("ekpUserId", ekpUserId);
		todoReqJson.put("feishuUserId", feishuUserId);
		todoReqJson.put("messageId", messageId);
		todoReqJson.put("delMapping", delMapping);
		if (StringUtil.isNotNull(reqData)) {
			todoReqJson.put("reqData", JSONObject.fromObject(reqData));
		}
		return todoReqJson.toString();
	}

	private void addErrorQueue(SysNotifyTodo todo, String reqData,
			String errorMsg, String method) {
		ThirdFeishuNotifyQueueErr error = new ThirdFeishuNotifyQueueErr();
		error.setFdSubject(todo.getFdSubject());
		error.setFdMd5(todo.getFdMD5());
		error.setFdNotifyId(todo.getFdId());
		error.setFdErrMsg(errorMsg);
		error.setFdData(reqData);
		error.setFdPerson(todo.getDocCreator());
		error.setDocCreateTime(new Date());
		error.setFdRepeatHandle(0);
		error.setFdFlag(
				ThirdFeishuConstant.NOTIFY_ERROR_FDFLAG_ERROR);
		error.setFdMethod(method);
		TransactionStatus addStatus = null;
		try {
			addStatus = TransactionUtils
					.beginNewTransaction();
			thirdFeishuNotifyQueueErrService.add(error);
			TransactionUtils.getTransactionManager().commit(addStatus);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (addStatus != null) {
				TransactionUtils.getTransactionManager().rollback(addStatus);
			}
		}
	}

	private void addMsgMapp(SysNotifyTodo todo, String userId, String ekpId,
			String messageId, String content,
			String fdLang) {
		ThirdFeishuMsgMapp mapp = new ThirdFeishuMsgMapp();
		mapp.setFdTodoSubject(todo.getFdSubject());
		mapp.setFdNotifyId(todo.getFdId());
		mapp.setFdContent(content);
		mapp.setFdMessageId(messageId);
		mapp.setFdUserId(userId);
		mapp.setFdPersonId(ekpId);
		mapp.setFdLang(fdLang);
		TransactionStatus addStatus = null;
		try {
			addStatus = TransactionUtils
					.beginNewTransaction();
			thirdFeishuMsgMappService.add(mapp);
			TransactionUtils.getTransactionManager().commit(addStatus);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (addStatus != null) {
				TransactionUtils.getTransactionManager().rollback(addStatus);
			}
		}
	}

	private void addMsgMapp(JSONObject o, String messageId) {

		ThirdFeishuMsgMapp mapp = new ThirdFeishuMsgMapp();
		mapp.setFdTodoSubject(o.getJSONObject("todo").getString("subject"));
		mapp.setFdNotifyId(o.getJSONObject("todo").getString("id"));
		mapp.setFdContent(o.getJSONObject("reqData").getString("content"));
		mapp.setFdMessageId(messageId);
		mapp.setFdUserId(o.getString("feishuUserId"));
		mapp.setFdPersonId(o.getString("ekpUserId"));
		mapp.setFdLang(o.getJSONObject("todo").getString("lang"));
		TransactionStatus addStatus = null;
		try {
			addStatus = TransactionUtils
					.beginNewTransaction();
			thirdFeishuMsgMappService.add(mapp);
			TransactionUtils.getTransactionManager().commit(addStatus);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (addStatus != null) {
				TransactionUtils.getTransactionManager().rollback(addStatus);
			}
		}
	}

	private IThirdFeishuMsgMappService thirdFeishuMsgMappService;

	public IThirdFeishuNotifyQueueErrService
			getThirdFeishuNotifyQueueErrService() {
		return thirdFeishuNotifyQueueErrService;
	}

	public void setThirdFeishuNotifyQueueErrService(
			IThirdFeishuNotifyQueueErrService thirdFeishuNotifyQueueErrService) {
		this.thirdFeishuNotifyQueueErrService = thirdFeishuNotifyQueueErrService;
	}

	private IThirdFeishuNotifyQueueErrService thirdFeishuNotifyQueueErrService;

	@Override
    public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
		updateNotify(todo,"clearTodoPersons");
	}

	@Override
    public void remove(SysNotifyTodo todo) throws Exception {
		updateNotify(todo,"remove");
	}
	
	@Override
    public void setTodoDone(SysNotifyTodo todo) throws Exception {
		updateNotify(todo,"setTodoDone");
	}

	@Override
    public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
		List persons = new ArrayList<SysOrgPerson>();
		persons.add(person);
		updateNotify(todo, persons,"removeDonePerson");
	}

	@Override
    public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
		updateNotify(todo, persons,"setPersonsDone");
	}
	
	@Override
    public void updateTodo(SysNotifyTodo todo) throws Exception {
		updateNotify(todo,"updateTodo");
	}


	public IThirdFeishuService getThirdFeishuService() {
		return thirdFeishuService;
	}

	public void setThirdFeishuService(IThirdFeishuService thirdFeishuService) {
		this.thirdFeishuService = thirdFeishuService;
	}
	
	private IThirdFeishuNotifyLogService thirdFeishuNotifyLogService;

	public IThirdFeishuNotifyLogService getThirdFeishuNotifyLogService() {
		return thirdFeishuNotifyLogService;
	}

	public void setThirdFeishuNotifyLogService(
			IThirdFeishuNotifyLogService thirdFeishuNotifyLogService) {
		this.thirdFeishuNotifyLogService = thirdFeishuNotifyLogService;
	}
	
	@Override
    public void resend(JSONObject o, String method)
			throws Exception {
		long time = System.currentTimeMillis();
		ThirdFeishuNotifyLog log = new ThirdFeishuNotifyLog();
		log.setFdNotifyId(o.getJSONObject("todo").getString("id"));
		log.setDocSubject(o.getJSONObject("todo").getString("subject"));
		Date start = new Date();
		log.setDocCreateTime(start);
		try {
			if ("sendMessage".equals(method)) {
				thirdFeishuService.sendMessage(o.getJSONObject("reqData"), log);
				addMsgMapp(o, log.getFdMessageId());
			} else if ("sendCardMessage".equals(method)) {
				thirdFeishuService.sendCardMessageV1(o.getJSONObject("reqData"),
						log);
				addMsgMapp(o, log.getFdMessageId());
			} else if ("updateCardMessage".equals(method)) {
				thirdFeishuService.updateCardMessageV1(o.getString("url"),
						o.getJSONObject("reqData"),
						log);
				deleteMapping(o);
			} else {
				thirdFeishuService.sendMessage(o.getJSONObject("reqData"), log);
				addMsgMapp(o, log.getFdMessageId());
			}
			log.setFdResult(1);
		} catch (Exception e) {
			logger.error("发送飞书消息异常：", e);
			log.setFdErrMsg(e.getMessage());
			log.setFdResult(2);
			throw e;
		} finally {
			Date end = new Date();
			log.setFdRtnTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			TransactionStatus addStatus = null;
			try {
				addStatus = TransactionUtils
						.beginNewTransaction();
				thirdFeishuNotifyLogService.add(log);
				TransactionUtils.getTransactionManager().commit(addStatus);
			} catch (Exception e) {
				logger.error("", e);
				TransactionUtils.getTransactionManager().rollback(addStatus);
			}
		}
		logger.debug(
				"发送飞书消息耗时：" + (System.currentTimeMillis() - time) + "毫秒");
	}

	private String getCreatorName(SysOrgPerson creator, Locale locale) {
		if (creator == null) {
			return null;
		}
		return creator.getFdName(locale);
	}

	private String getCreatorTime(Date createTime, Locale locale) {
		if (createTime == null) {
			return null;
		}
		return DateUtil.convertDateToString(createTime, null, locale);
	}

	private String getDocSubject(SysNotifyTodo todo) {
		String subject = todo.getFdSubject();
		if (StringUtil.isNotNull(todo.getFdAppName())
				|| todo.getFdLink().startsWith("http")) {
			return subject;
		} else {
			try {
				String modelId = todo.getFdModelId();
				String modelName = todo.getFdModelName();
				if (StringUtil.isNotNull(modelId)
						&& StringUtil.isNotNull(modelName)) {
					Object object = thirdFeishuNotifyLogService
							.findByPrimaryKey(modelId, modelName, true);
					if (object != null) {
						Map map = BeanUtils.describe(object);
						if (map.containsKey("fdName")) {
							subject = BeanUtils.getSimpleProperty(object,
									"fdName");
						} else if (map.containsKey("docSubject")) {
							subject = BeanUtils.getSimpleProperty(object,
									"docSubject");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("", e);
			}
		}
		return subject;
	}

	private String getTodoTitle(SysNotifyTodo todo) {
		String title = todo.getFdSubject();
		switch (todo.getFdLevel()) {
		case 1:
			title = "【"
					+ ThirdFeishuApiUtil.getValueByLang(
							"sysNotifyTodo.level.taglib.1",
							"sys-notify", todo.getFdLang())
					+ "】" + title;
			break;
		case 2:
			title = "【"
					+ ThirdFeishuApiUtil.getValueByLang(
							"sysNotifyTodo.level.taglib.2",
							"sys-notify", todo.getFdLang())
					+ "】" + title;
			break;
		}
		return title;
	}

	private void updateNotify(SysNotifyTodo todo, List<SysOrgElement> persons, String method)
			throws Exception {
		if (persons == null || persons.isEmpty()) {
			updateNotify(todo,method);
		}
		for (SysOrgElement person : persons) {
			ThirdFeishuMsgMapp mapp = thirdFeishuMsgMappService
					.findByNotifyAndPerson(todo.getFdId(), person.getFdId());
			if (mapp != null) {
				updateNotify(todo, mapp, method);
			}
		}
	}

	private void updateNotify(SysNotifyTodo todo, String method) throws Exception {
		List<ThirdFeishuMsgMapp> list = thirdFeishuMsgMappService
				.findByNotifyId(todo.getFdId());
		if (list == null || list.isEmpty()) {
			return;
		}
		for (ThirdFeishuMsgMapp mapp : list) {
			updateNotify(todo, mapp, method);
		}
	}

	private String getStatus(SysNotifyTodo todo, String method){
		String fdLang = todo.getFdLang();
		Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
		if (locale == null) {
			locale = ResourceUtil.getLocaleByUser();
		}
		String type = "todo";
		if("remove".equals(method)){
			type = "remove";
		}else if("setPersonsDone".equals(method)){
			type = todo.getFdType()==2?"hasread":"hasdone";
		}else if("clearTodoPersons".equals(method)){
			type = "remove";
		}else if("setTodoDone".equals(method)){
			type = todo.getFdType()==2?"hasread":"hasdone";
		}else if("removeDonePerson".equals(method)){
			type = "remove";
		}else if("updateTodo".equals(method)){
			type = "todo";
			switch (todo.getFdType()){
				case 2:
					type = "toread";
					break;
				case 3:
					type = "suspend";
					break;
			}
		}
		return ResourceUtil.getStringValue("msg.status",
				"third-feishu", locale)
				+ ResourceUtil.getStringValue("msg.status." + type,
				"third-feishu", locale);
	}

	private void updateNotify(SysNotifyTodo todo,
			ThirdFeishuMsgMapp mapp, String method)
			throws Exception {
		long time = System.currentTimeMillis();
		ThirdFeishuNotifyLog log = new ThirdFeishuNotifyLog();
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setDocCreateTime(start);
		try {
			String fdLang = mapp.getFdLang();
			Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
			if (locale == null) {
				locale = ResourceUtil.getLocaleByUser();
			}
			thirdFeishuService.updateCardMessageV1(mapp.getFdMessageId(),
					mapp.getFdContent(), todo.getFdType(),
					locale,
					log,getStatus(todo,method));
			if(!"updateTodo".equals(method)) {
				thirdFeishuMsgMappService.delete(mapp);
			}
			if (log.getFdResult() == null) {
				log.setFdResult(1);
			}
			Date end = new Date();
			log.setFdRtnTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());

		} catch (Exception e) {
			logger.error("更新飞书消息异常：", e);
			log.setFdErrMsg(e.getMessage());
			log.setFdResult(2);
			Date end = new Date();
			log.setFdRtnTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			String reqData = log.getFdReqData();
			addErrorQueue(todo, buildTodoReqData(todo, log.getFdUrl(), reqData,
					mapp.getFdPersonId(), mapp.getFdUserId(),
					mapp.getFdMessageId(),
					!"updateTodo".equals(method)),
					e.getMessage(), "updateCardMessage");
		} finally {
			TransactionStatus addStatus = null;
			try {
				addStatus = TransactionUtils
						.beginNewTransaction();
				thirdFeishuNotifyLogService.add(log);
				TransactionUtils.getTransactionManager().commit(addStatus);
			} catch (Exception e) {
				logger.error("", e);
				TransactionUtils.getTransactionManager().rollback(addStatus);
			}
		}
		logger.debug(
				"发送飞书消息耗时：" + (System.currentTimeMillis() - time) + "毫秒");
	}

	public IThirdFeishuMsgMappService getThirdFeishuMsgMappService() {
		return thirdFeishuMsgMappService;
	}

	public void
			setThirdFeishuMsgMappService(
					IThirdFeishuMsgMappService thirdFeishuMsgMappService) {
		this.thirdFeishuMsgMappService = thirdFeishuMsgMappService;
	}

	private void deleteMapping(JSONObject o) {
		if(!o.containsKey("delMapping")){
			return;
		}
		if(!o.getBoolean("delMapping")){
			return;
		}
		try {
			String messageId = o.getString("messageId");
			ThirdFeishuMsgMapp mapp = thirdFeishuMsgMappService
					.findByMessageId(messageId);
			if (mapp == null) {
				logger.warn("找不到对应的映射关系，messageId:" + messageId);
				return;
			}
			thirdFeishuMsgMappService.delete(mapp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
