package com.landray.kmss.third.welink.notify;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProviderExtend;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.welink.constant.ThirdWelinkConstant;
import com.landray.kmss.third.welink.model.ThirdWelinkConfig;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyLog;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyQueueErr;
import com.landray.kmss.third.welink.service.IThirdWelinkNotifyLogService;
import com.landray.kmss.third.welink.service.IThirdWelinkNotifyQueueErrService;
import com.landray.kmss.third.welink.service.IThirdWelinkPersonMappingService;
import com.landray.kmss.third.welink.service.IThirdWelinkService;
import com.landray.kmss.third.welink.service.IThirdWelinkTodoTaskMappService;
import com.landray.kmss.third.welink.util.ThirdWelinkApiUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

import net.sf.json.JSONObject;

public class ThirdWelinkTodoTaskProvider extends BaseSysNotifyProviderExtend {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWelinkTodoTaskProvider.class);
	
	public IThirdWelinkNotifyQueueErrService
			getThirdWelinkNotifyQueueErrService() {
		return thirdWelinkNotifyQueueErrService;
	}

	public void setThirdWelinkNotifyQueueErrService(
			IThirdWelinkNotifyQueueErrService thirdWelinkNotifyQueueErrService) {
		this.thirdWelinkNotifyQueueErrService = thirdWelinkNotifyQueueErrService;
	}

	public IThirdWelinkService getThirdWelinkService() {
		return thirdWelinkService;
	}

	public void setThirdWelinkService(IThirdWelinkService thirdWelinkService) {
		this.thirdWelinkService = thirdWelinkService;
	}

	public ISysOrgPersonService getSysOrgPersonService() {
		return sysOrgPersonService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	private IThirdWelinkNotifyLogService thirdWelinkNotifyLogService;

	private IThirdWelinkNotifyQueueErrService thirdWelinkNotifyQueueErrService;

	private IThirdWelinkService thirdWelinkService;

	private IThirdWelinkTodoTaskMappService thirdWelinkTodoTaskMappService = null;


	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}
	

	public void setSysNotifyTodoService(ISysNotifyTodoService sysNotifyTodoService) {
		this.sysNotifyTodoService = sysNotifyTodoService;
	}

	private boolean checkNeedNotify(SysNotifyTodo todo, NotifyContext context) {
		if (!"true"
				.equals(ThirdWelinkConfig.newInstance().getWelinkEnabled())) {
			logger.debug("钉钉未开启集成，不推送消息...");
			return false;
		}
		boolean notifyflag = false;
		logger.debug("开始向钉钉推送消息...");
		String appType = null;
		if (context != null) {
			appType = context.getFdAppType();
		}
		if (StringUtil.isNotNull(appType) ) {
			if (appType.contains("all") || appType.contains("welink")) {
				notifyflag = true;
				logger.debug("已经设置参数fdAppType=" + appType + ",故强制推送消息...");
			}
		}else if(StringUtil.isNull(appType)){
			if (todo.getFdType() == 1 || todo.getFdType() == 3) {
				if ("true".equals(ThirdWelinkConfig.newInstance()
						.getWelinkTodoTaskEnabled())) {
					notifyflag = true;
				}
			}
		}
		if(!notifyflag){
			logger.debug("未开启welink待办任务推送,参数fdAppType=" + appType + ",待办Id="
					+ todo.getFdId() + ",待办主题：" + todo.getFdSubject());
		}
		return notifyflag;
	}

	@Override
    public void add(SysNotifyTodo todo, NotifyContext context)
			throws Exception {
		logger.debug("add ：" + todo.getFdId() + "," + todo.getFdSubject());
		long atime = System.currentTimeMillis();
		if (!checkNeedNotify(todo, context)) {
			return;
		}
		if (!"true"
				.equals(ThirdWelinkConfig.newInstance()
						.getWelinkNotifyEnabled())) {
			logger.debug("待办待阅推送未启用，不推送消息...");
			return;
		}
		NotifyContextImp ctx = (NotifyContextImp) context;
		List notifyTargetList = ((NotifyContextImp) context).getNotifyPersons();
		if (notifyTargetList == null || notifyTargetList.isEmpty()) {
			logger.debug("通知人员为空不执行消息发送，通知标题为："+todo.getFdSubject()+"("+todo.getFdId()+")");
			return;
		}else{
			Iterator<?> it = ctx.getNotifyPersons().iterator();
			while (it.hasNext()) {
				SysOrgPerson sysOrgPerson = (SysOrgPerson) it.next();
				addTodoTask(todo, sysOrgPerson);
			}
		}
		logger.debug("发送钉钉消息总耗时："+(System.currentTimeMillis()-atime)+"毫秒");
	}


	@Override
    public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
		logger.debug("clearTodoPersons ：" + todo.getFdId() + ","
				+ todo.getFdSubject());
		if (!checkNeedNotify(todo, null)) {
			return;
		}
		delTodoTask(todo, ThirdWelinkConstant.NOTIFY_METHD_CLEARTODOPERSONS);
	}

	@Override
    public void remove(SysNotifyTodo todo) throws Exception {
		logger.debug("remove ：" + todo.getFdId() + "," + todo.getFdSubject());
		if (!checkNeedNotify(todo, null)) {
			return;
		}
		delTodoTask(todo, ThirdWelinkConstant.NOTIFY_METHD_REMOVE);
	}
	
	@Override
    public void setTodoDone(SysNotifyTodo todo) throws Exception {
		logger.debug(
				"setTodoDone ：" + todo.getFdId() + "," + todo.getFdSubject());
		if (!checkNeedNotify(todo, null)) {
			return;
		}
		List<SysOrgElement> notifyTargetList = todo.getHbmTodoTargets();
		if (notifyTargetList == null || notifyTargetList.isEmpty()) {
			logger.debug("通知人员为空不执行消息发送，通知标题为：" + todo.getFdSubject() + "("
					+ todo.getFdId() + ")");
			return;
		} else {
			for (SysOrgElement ele : notifyTargetList) {
				if (ele.getFdOrgType() != 8) {
					logger.error("待办接收人不是用户，组织ID：" + ele.getFdId() + "，组织名称："
							+ ele.getFdName());
					continue;
				}
				deleteTodoTask(todo, (SysOrgPerson) ele,
						ThirdWelinkConstant.NOTIFY_METHD_SETTODODONE);
			}
		}
	}

	@Override
    public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
		logger.debug("setTodoDone ：" + todo.getFdId() + ","
				+ todo.getFdSubject() + "," + person.getFdLoginName());
		if (!checkNeedNotify(todo, null)) {
			return;
		}
		deleteTodoTask(todo, person,
				ThirdWelinkConstant.NOTIFY_METHD_REMOVEDONEPERSON);
	}

	@Override
    public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
		if (logger.isDebugEnabled()) {
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
		if (!checkNeedNotify(todo, null)) {
			return;
		}
		if (persons == null || persons.isEmpty()) {
			logger.debug("通知人员为空不执行消息发送，通知标题为：" + todo.getFdSubject() + "("
					+ todo.getFdId() + ")");
			return;
		} else {
			for (SysOrgElement ele : (List<SysOrgElement>) persons) {
				if (ele.getFdOrgType() != 8) {
					logger.error("待办接收人不是用户，组织ID：" + ele.getFdId() + "，组织名称："
							+ ele.getFdName());
					continue;
				}
				deleteTodoTask(todo, (SysOrgPerson) ele,
						ThirdWelinkConstant.NOTIFY_METHD_SETPERSONDONE);
			}
		}
	}
	


	@Override
    public void updateTodo(SysNotifyTodo todo) throws Exception {
		logger.debug("updateTodo ：" + todo.getFdId() + ","
				+ todo.getFdSubject());
		if (!checkNeedNotify(todo, null)) {
			return;
		}
		// 先从映射表中删除待办（删除成功后删除映射表），然后再新增待办
		remove(todo);

		List notifyTargetList = todo.getHbmTodoTargets();
		List<String> ekpIds = new ArrayList<String>();
		if (notifyTargetList != null && notifyTargetList.size() > 0) {
			Iterator<?> it = notifyTargetList.iterator();
			while (it.hasNext()) {
				SysOrgPerson sysOrgPerson = (SysOrgPerson) it.next();
				ekpIds.add(sysOrgPerson.getFdId());
			}
		}


	}

	
	private void addTodoTask(SysNotifyTodo todo,
			SysOrgPerson toUser)
	{
		ThirdWelinkNotifyLog log = new ThirdWelinkNotifyLog();
		log.setFdSendType(2);
		log.setFdMethod(ThirdWelinkConstant.NOTIFY_METHD_ADD);
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		log.setFdUser(toUser);
		Date start = new Date();
		log.setDocCreateTime(start);
		String content = todo.getFdSubject();
		switch (todo.getFdLevel()) {
		case 1:
			content = "【"
					+ ThirdWelinkApiUtil.getValueByLang(
							"sysNotifyTodo.level.taglib.1",
							"sys-notify", todo.getFdLang())
					+ "】" + content;
			break;
		case 2:
			content = "【"
					+ ThirdWelinkApiUtil.getValueByLang(
							"sysNotifyTodo.level.taglib.2",
							"sys-notify", todo.getFdLang())
					+ "】" + content;
			break;
		}
		try {
			thirdWelinkService.addTodoTask(todo.getFdId(), todo.getFdSubject(),
					toUser, todo.getDocCreator(),
					ThirdWelinkApiUtil.getViewUrl(todo),
					ThirdWelinkApiUtil.getModelName(todo), log);
			if (log.getFdResult() == null) {
				log.setFdResult(1);
			}
		} catch (Exception e) {
			logger.error("", e);
			log.setFdErrMsg(e.getMessage());
			log.setFdResult(2);
			String reqData = log.getFdReqData();
			if (StringUtil.isNotNull(reqData)) {
				addErrorQueue(todo,
						buildTodoReqData(todo, toUser.getFdId(), log.getFdUrl(),
								reqData),
						toUser, ThirdWelinkConstant.NOTIFY_TASK_ADD,
						e.getMessage());
			}

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
	}
	
	private String buildTodoReqData(SysNotifyTodo todo, String ekpUserId,
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
		todoReqJson.put("toUser", ekpUserId);
		return todoReqJson.toString();
	}

	private void addErrorQueue(SysNotifyTodo todo, String reqData,
			SysOrgPerson toUser, int methodType,
			String errorMsg) {
		ThirdWelinkNotifyQueueErr error = new ThirdWelinkNotifyQueueErr();
		error.setFdSubject(todo.getFdSubject());
		error.setFdMd5(todo.getFdMD5());
		error.setFdSendType(2);
		error.setFdNotifyId(todo.getFdId());
		error.setFdMethod(methodType);
		error.setFdErrMsg(errorMsg);
		error.setFdData(reqData);
		error.setFdFromUser(todo.getDocCreator());
		error.setFdToUser(toUser);
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
			logger.error("", e);
		}
	}

	private void delTodoTask(SysNotifyTodo todo, int methodType)
			throws Exception {
		List<String> personIds = thirdWelinkTodoTaskMappService.findValue(
				"thirdWelinkTodoTaskMapp.fdPersonId",
				"thirdWelinkTodoTaskMapp.fdTodoId='" + todo.getFdId() + "'",
				null);
		for (String personId : personIds) {
			SysOrgPerson person = (SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(personId, null, true);
			deleteTodoTask(todo, person,
					methodType);
		}
	}

	private void deleteTodoTask(SysNotifyTodo todo,
			SysOrgPerson toUser, int methodType) throws Exception {
		ThirdWelinkNotifyLog log = new ThirdWelinkNotifyLog();
		log.setFdSendType(2);
		log.setFdMethod(methodType);
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		log.setFdUser(toUser);
		Date start = new Date();
		log.setDocCreateTime(start);

		try {
			/*
			 * ThirdWelinkPersonMapping toUser_mapping =
			 * thirdWelinkPersonMappingService .findByEkpId(toUser.getFdId());
			 * if (toUser_mapping == null) { logger.error( "待办接收人（ID:" +
			 * toUser.getFdId() + "名称:" + toUser.getFdName() + ",登录名:" +
			 * toUser.getFdLoginName() +
			 * "）找不到对应的映射关系，可能是该用户没有同步到welink中。不进行待办同步，待办标题：" +
			 * todo.getFdSubject() + ",待办ID：" + todo.getFdId());
			 * log.setFdResult(2); log.setFdErrMsg("待办接收人（ID:" +
			 * toUser.getFdId() + "名称:" + toUser.getFdName() + ",登录名:" +
			 * toUser.getFdLoginName() +
			 * "）找不到对应的映射关系，可能是该用户没有同步到welink中。不进行待办同步，待办标题：" +
			 * todo.getFdSubject() + ",待办ID：" + todo.getFdId()); return; }
			 * String welinkUserId = toUser_mapping.getFdWelinkUserId(); if
			 * (StringUtil.isNull(welinkUserId)) { logger.error( "待办发送人（ID:" +
			 * toUser.getFdId() + "名称:" + toUser.getFdName() + ",登录名:" +
			 * toUser.getFdLoginName() +
			 * "）映射关系中的welinkUserId为空，可能是该用户同步到welink失败。不进行待办同步，待办标题：" +
			 * todo.getFdSubject() + ",待办ID：" + todo.getFdId());
			 * log.setFdResult(2); log.setFdErrMsg( "待办发送人（ID:" +
			 * toUser.getFdId() + "名称:" + toUser.getFdName() + ",登录名:" +
			 * toUser.getFdLoginName() +
			 * "）映射关系中的welinkUserId为空，可能是该用户同步到welink失败。不进行待办同步，待办标题：" +
			 * todo.getFdSubject() + ",待办ID：" + todo.getFdId()); return; }
			 */
			thirdWelinkService
					.delTodoTask(todo.getFdId() + "X" + toUser.getFdId(), log);
			log.setFdResult(1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			log.setFdErrMsg(e.getMessage());
			log.setFdResult(2);
			String url = log.getFdUrl();
			if (StringUtil.isNotNull(url)) {
				addErrorQueue(todo,
						buildTodoReqData(todo, toUser.getFdId(), url, null),
						toUser, ThirdWelinkConstant.NOTIFY_TASK_DEL,
						e.getMessage());
			}
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
	}

	private void delTodoTask(SysNotifyTodo todo, String ekpUserId)
			throws Exception {
		ThirdWelinkNotifyLog log = new ThirdWelinkNotifyLog();
		log.setFdSendType(2);
		log.setFdMethod(ThirdWelinkConstant.NOTIFY_TASK_DEL);
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setDocCreateTime(start);

		try {
			thirdWelinkService.delTodoTask(todo.getFdId() + "X" + ekpUserId,
					log);
			log.setFdResult(1);
		} catch (Exception e) {
			logger.error("", e);
			log.setFdErrMsg(e.getMessage());
			log.setFdResult(2);
			String url = log.getFdUrl();
			if (StringUtil.isNotNull(url)) {
				addErrorQueue(todo,
						buildTodoReqData(todo, ekpUserId, url, null),
						null, ThirdWelinkConstant.NOTIFY_TASK_DEL,
						e.getMessage());
			}
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
	}


	private ISysNotifyTodoService sysNotifyTodoService = null;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}


	public IThirdWelinkNotifyLogService getThirdWelinkNotifyLogService() {
		return thirdWelinkNotifyLogService;
	}

	public void
			setThirdWelinkNotifyLogService(
					IThirdWelinkNotifyLogService thirdWelinkNotifyLogService) {
		this.thirdWelinkNotifyLogService = thirdWelinkNotifyLogService;
	}

	
	public void addTodoTask(JSONObject o) throws Exception {
		ThirdWelinkNotifyLog log = new ThirdWelinkNotifyLog();
		log.setFdNotifyId(o.getJSONObject("todo").getString("id"));
		log.setDocSubject(o.getJSONObject("todo").getString("subject"));
		log.setFdSendType(2);
		log.setFdMethod(ThirdWelinkConstant.NOTIFY_METHD_RESEND);
		if (o.containsKey("toUser")) {
			String userId = o.getString("toUser");
			log.setFdUser((SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(userId));
		}
		Date start = new Date();
		log.setDocCreateTime(start);
		try {
			thirdWelinkService.addTodoTask(o, log);
			log.setFdResult(1);
		} catch (Exception e) {
			logger.error("", e);
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
	}

	@Deprecated
	public void updateTodoTask(JSONObject o) throws Exception {
		ThirdWelinkNotifyLog log = new ThirdWelinkNotifyLog();
		log.setFdNotifyId(o.getJSONObject("todo").getString("id"));
		log.setDocSubject(o.getJSONObject("todo").getString("subject"));
		log.setFdSendType(2);
		log.setFdMethod(ThirdWelinkConstant.NOTIFY_METHD_RESEND);
		if (o.containsKey("toUser")) {
			String userId = o.getString("toUser");
			log.setFdUser((SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(userId));
		}
		Date start = new Date();
		log.setDocCreateTime(start);
		try {
			thirdWelinkService.delTodoTask(o, log);
			log.setFdResult(1);
		} catch (Exception e) {
			logger.error("", e);
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
	}

	public void deleteTodoTask(JSONObject o) throws Exception {
		ThirdWelinkNotifyLog log = new ThirdWelinkNotifyLog();
		log.setFdNotifyId(o.getJSONObject("todo").getString("id"));
		log.setDocSubject(o.getJSONObject("todo").getString("subject"));
		log.setFdSendType(2);
		log.setFdMethod(ThirdWelinkConstant.NOTIFY_METHD_RESEND);
		Date start = new Date();
		log.setDocCreateTime(start);
		try {
			thirdWelinkService.delTodoTask(o, log);
			log.setFdResult(1);
		} catch (Exception e) {
			logger.error("", e);
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
	}

	IThirdWelinkPersonMappingService thirdWelinkPersonMappingService = null;

	public IThirdWelinkPersonMappingService
			getThirdWelinkPersonMappingService() {
		return thirdWelinkPersonMappingService;
	}

	public void setThirdWelinkPersonMappingService(
			IThirdWelinkPersonMappingService thirdWelinkPersonMappingService) {
		this.thirdWelinkPersonMappingService = thirdWelinkPersonMappingService;
	}

	public IThirdWelinkTodoTaskMappService getThirdWelinkTodoTaskMappService() {
		return thirdWelinkTodoTaskMappService;
	}

	public void setThirdWelinkTodoTaskMappService(
			IThirdWelinkTodoTaskMappService thirdWelinkTodoTaskMappService) {
		this.thirdWelinkTodoTaskMappService = thirdWelinkTodoTaskMappService;
	}
}
