package com.landray.kmss.third.welink.notify;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProviderExtend;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.welink.constant.ThirdWelinkConstant;
import com.landray.kmss.third.welink.model.ThirdWelinkConfig;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyLog;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyQueueErr;
import com.landray.kmss.third.welink.service.IThirdWelinkNotifyLogService;
import com.landray.kmss.third.welink.service.IThirdWelinkNotifyQueueErrService;
import com.landray.kmss.third.welink.service.IThirdWelinkService;
import com.landray.kmss.third.welink.util.ThirdWelinkApiUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdWelinkTodoMessageProvider extends BaseSysNotifyProviderExtend {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWelinkTodoMessageProvider.class);
	
	private IThirdWelinkService thirdWelinkService;

	@Override
    public void add(SysNotifyTodo todo, NotifyContext context) throws Exception {
		long atime = System.currentTimeMillis();
		if (!"true"
				.equals(ThirdWelinkConfig.newInstance().getWelinkEnabled())) {
			logger.debug("welink未开启集成，不推送消息...");
			return;
		}
		if (!"true"
				.equals(ThirdWelinkConfig.newInstance()
						.getWelinkNotifyEnabled())) {
			logger.debug("待办待阅推送未启用，不推送消息...");
			return;
		}
		boolean notifyflag = false;
		logger.debug("开始向welink推送消息...");
		String appType = context.getFdAppType();
		if (StringUtil.isNotNull(appType) ) {
			if (appType.contains("all")) {
				notifyflag = true;
				logger.debug("已经设置参数fdAppType=" + appType + ",故强制推送消息...");
			}
		}else if(StringUtil.isNull(appType)){
			
			if (todo.getFdType() == 2) {
				if (!"true".equals(
						ThirdWelinkConfig.newInstance()
								.getWelinkToreadMsgEnabled())) {
					// 待阅关闭,默认关闭，开启需要勾上
					logger.debug("未开启待阅消息推送到welink的消息中心...");
				}else{
					notifyflag = true;
				}
			}
			if (todo.getFdType() == 1) {
				if (!"true".equals(
						ThirdWelinkConfig.newInstance()
								.getWelinkTodoMsgEnabled())) {
					// 待阅关闭,默认关闭，开启需要勾上
					logger.debug("未开启待办消息推送到welink的消息中心...");
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

			List<String> welinkUserIds = ThirdWelinkApiUtil
					.getWelinkUserIdList(ekpIds);
			if (welinkUserIds == null || welinkUserIds.size() == 0) {
				logger.error("通过EKP的fdId查找人员映射表发现找不到对应的welink人员(" + ekpIds
						+ ")，请先检查组织同步是否正常");
				return;
			}

			for (int i = 0; i < Math.ceil(welinkUserIds.size() / 1000f); i++) {

				JSONArray userIds = new JSONArray();
				int toIndex = (i + 1) * 1000;
				if (toIndex > welinkUserIds.size()) {
					toIndex = welinkUserIds.size();
				}
				userIds.addAll(welinkUserIds.subList(i * 1000, toIndex));
				sendNotify(todo, userIds);
			}
		}

		logger.debug(
				"发送welink消息总耗时：" + (System.currentTimeMillis() - atime) + "毫秒");
	}
	
	

	private void sendNotify(SysNotifyTodo todo,
			JSONArray userIds)
			throws Exception {
		long time = System.currentTimeMillis();
		ThirdWelinkNotifyLog log = new ThirdWelinkNotifyLog();
		log.setFdSendType(1);
		log.setFdMethod(1);
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setDocCreateTime(start);
		try{
			String title = todo.getFdSubject();
			switch (todo.getFdLevel()) {
			case 1:
				title = "【"
						+ ThirdWelinkApiUtil.getValueByLang(
								"sysNotifyTodo.level.taglib.1",
								"sys-notify", todo.getFdLang())
						+ "】" + title;
				break;
			case 2:
				title = "【"
						+ ThirdWelinkApiUtil.getValueByLang(
								"sysNotifyTodo.level.taglib.2",
								"sys-notify", todo.getFdLang())
						+ "】" + title;
				break;
			}
			String fdLang = todo.getFdLang();
			Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
			if (locale == null) {
				locale = ResourceUtil.getLocaleByUser();
			}
			String content = ResourceUtil.getStringValue(
					"sysNotifyCategory.type.module", "sys-notify", locale) + "："
					+ ThirdWelinkApiUtil.getModelName(todo)
					+ "\r\n"
					+ ResourceUtil.getStringValue(
							"sysNotifyCategory.docCreateTime", "sys-notify",
							locale)
					+ "：" + DateUtil.convertDateToString(todo.getFdCreateTime(),
							"yyyyMMdd HH:mm");

			thirdWelinkService.sendMessage(title, content,
					ThirdWelinkApiUtil.getViewUrl(todo),
					ThirdWelinkApiUtil.getModelName(todo), userIds, log);
			if (log.getFdResult() == null) {
				log.setFdResult(1);
			}
			Date end = new Date();
			log.setFdResTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());

		}catch(Exception e){
			logger.error("发送welink消息异常：", e);
			log.setFdErrMsg(e.getMessage());
			log.setFdResult(2);
			Date end = new Date();
			log.setFdResTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			String reqData = log.getFdReqData();
			addErrorQueue(todo, buildTodoReqData(todo, log.getFdUrl(), reqData),
					e.getMessage());
		} finally {
			TransactionStatus addStatus = null;
			try {
				addStatus = TransactionUtils.beginNewTransaction();
				thirdWelinkNotifyLogService.add(log);
				TransactionUtils.getTransactionManager().commit(addStatus);
			} catch (Exception e) {
				if(addStatus != null){
					TransactionUtils.rollback(addStatus);
				}
				logger.error("", e);
			}
		}
		logger.debug(
				"发送welink消息耗时：" + (System.currentTimeMillis() - time) + "毫秒");
	}
	
	private String buildTodoReqData(SysNotifyTodo todo,
			String url,
			String reqData) {
		JSONObject todoJson = new JSONObject();
		todoJson.put("id", todo.getFdId());
		todoJson.put("subject", todo.getFdSubject());
		JSONObject todoReqJson = new JSONObject();
		todoReqJson.put("todo", todoJson);
		todoReqJson.put("url", url);
		if (StringUtil.isNotNull(reqData)) {
			todoReqJson.put("reqData", JSONObject.fromObject(reqData));
		}
		return todoReqJson.toString();
	}

	private void addErrorQueue(SysNotifyTodo todo, String reqData,
			String errorMsg) {
		ThirdWelinkNotifyQueueErr error = new ThirdWelinkNotifyQueueErr();
		error.setFdSubject(todo.getFdSubject());
		error.setFdMd5(todo.getFdMD5());
		error.setFdSendType(1);
		error.setFdNotifyId(todo.getFdId());
		error.setFdMethod(1);
		error.setFdErrMsg(errorMsg);
		error.setFdData(reqData);
		error.setFdFromUser(todo.getDocCreator());
		error.setDocCreateTime(new Date());
		error.setFdRepeatHandle(0);
		error.setFdFlag(
				ThirdWelinkConstant.NOTIFY_ERROR_FDFLAG_ERROR);
		TransactionStatus addStatus = null;
		try {
			addStatus = TransactionUtils.beginNewTransaction();
			thirdWelinkNotifyQueueErrService.add(error);
			TransactionUtils.getTransactionManager().commit(addStatus);
		} catch (Exception e) {
			if(addStatus != null){
				TransactionUtils.rollback(addStatus);
			}
			logger.error(e.getMessage(), e);
		}
	}

	private IThirdWelinkNotifyQueueErrService thirdWelinkNotifyQueueErrService;

	public IThirdWelinkNotifyQueueErrService
			getThirdWelinkNotifyQueueErrService() {
		return thirdWelinkNotifyQueueErrService;
	}

	public void setThirdWelinkNotifyQueueErrService(
			IThirdWelinkNotifyQueueErrService thirdWelinkNotifyQueueErrService) {
		this.thirdWelinkNotifyQueueErrService = thirdWelinkNotifyQueueErrService;
	}



	@Override
    public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
	}

	@Override
    public void remove(SysNotifyTodo todo) throws Exception {
	}
	
	@Override
    public void setTodoDone(SysNotifyTodo todo) throws Exception {
	}

	@Override
    public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
	}

	@Override
    public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
	}
	
	@Override
    public void updateTodo(SysNotifyTodo todo) throws Exception {

	}


	public IThirdWelinkService getThirdWelinkService() {
		return thirdWelinkService;
	}

	public void setThirdWelinkService(IThirdWelinkService thirdWelinkService) {
		this.thirdWelinkService = thirdWelinkService;
	}
	
	private IThirdWelinkNotifyLogService thirdWelinkNotifyLogService;

	public IThirdWelinkNotifyLogService getThirdWelinkNotifyLogService() {
		return thirdWelinkNotifyLogService;
	}

	public void setThirdWelinkNotifyLogService(
			IThirdWelinkNotifyLogService thirdWelinkNotifyLogService) {
		this.thirdWelinkNotifyLogService = thirdWelinkNotifyLogService;
	}
	
	public void sendNotify(JSONObject o)
			throws Exception {
		long time = System.currentTimeMillis();
		ThirdWelinkNotifyLog log = new ThirdWelinkNotifyLog();
		log.setFdSendType(1);
		log.setFdMethod(1);
		log.setFdNotifyId(o.getJSONObject("todo").getString("id"));
		log.setDocSubject(o.getJSONObject("todo").getString("subject"));
		Date start = new Date();
		log.setDocCreateTime(start);
		try {
			thirdWelinkService.sendMessage(o.getJSONObject("reqData"), log);
			log.setFdResult(1);
		} catch (Exception e) {
			logger.error("发送welink消息异常：", e);
			log.setFdErrMsg(e.getMessage());
			log.setFdResult(2);
			throw e;
		} finally {
			Date end = new Date();
			log.setFdResTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			TransactionStatus addStatus = null;
			try {
				addStatus = TransactionUtils.beginNewTransaction();
				thirdWelinkNotifyLogService.add(log);
				TransactionUtils.getTransactionManager().commit(addStatus);
			} catch (Exception e) {
				if(addStatus != null){
					TransactionUtils.rollback(addStatus);
				}
				logger.error("", e);
			}
		}
		logger.debug(
				"发送welink消息耗时：" + (System.currentTimeMillis() - time) + "毫秒");
	}

}
