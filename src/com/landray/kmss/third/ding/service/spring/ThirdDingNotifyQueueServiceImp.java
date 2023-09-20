package com.landray.kmss.third.ding.service.spring;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.landray.kmss.third.ding.notify.provider.ThirdDingTodoProvider;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.messageType.DingOfficeBody;
import com.landray.kmss.third.ding.messageType.DingOfficeHead;
import com.landray.kmss.third.ding.messageType.DingOfficeMessage;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingNotifyQueue;
import com.landray.kmss.third.ding.model.ThirdDingWork;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyLog;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyLogService;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IThirdDingNotifyQueueService;
import com.landray.kmss.third.ding.service.IThirdDingWorkService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SecureUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

public class ThirdDingNotifyQueueServiceImp extends ExtendDataServiceImp implements IThirdDingNotifyQueueService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingNotifyQueueServiceImp.class);

	private static boolean runQueueLocked = false;

	private SysQuartzJobContext jobContext = null;

	private ISysNotifyTodoService sysNotifyTodoService = null;

	private static int count = 0; // 接口频率计数

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil
					.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}

	private IThirdDingNotifyLogService thirdDingNotifyLogService;

	public IThirdDingNotifyLogService getThirdDingNotifyLogService() {
		if (thirdDingNotifyLogService == null) {
			thirdDingNotifyLogService = (IThirdDingNotifyLogService) SpringBeanUtil
					.getBean("thirdDingNotifyLogService");
		}
		return thirdDingNotifyLogService;
	}

	private IThirdDingWorkService thirdDingWorkService;

	public IThirdDingWorkService getThirdDingWorkService() {
		if (thirdDingWorkService == null) {
			return (IThirdDingWorkService) SpringBeanUtil
					.getBean("thirdDingWorkService");
		}
		return thirdDingWorkService;
	}

	private ThirdDingTodoProvider thirdDingTodoProvider;

	private ThirdDingTodoProvider getThirdDingTodoProvider(){
		if(thirdDingTodoProvider==null){
			thirdDingTodoProvider = (ThirdDingTodoProvider)SpringBeanUtil.getBean("thirdDingTodoProvider");
		}
		return thirdDingTodoProvider;
	}

    @Override
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingNotifyQueue) {
            ThirdDingNotifyQueue thirdDingNotifyQueue = (ThirdDingNotifyQueue) model;
        }
        return model;
    }

    @Override
	public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingNotifyQueue thirdDingNotifyQueue = new ThirdDingNotifyQueue();
        ThirdDingUtil.initModelFromRequest(thirdDingNotifyQueue, requestContext);
        return thirdDingNotifyQueue;
    }

    @Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingNotifyQueue thirdDingNotifyQueue = (ThirdDingNotifyQueue) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	public void log(String info) {
		if (jobContext != null) {
			jobContext.logMessage(info);
		}
	}

	@Override
	public void addMessage(SysQuartzJobContext context) {

		if (context != null) {
			this.jobContext = context;
		}
		if (runQueueLocked) {
			log("当前有定时任务在跑！");
			return;
		}

		runQueueLocked = true;
		count = 0;
		logger.debug("消息队列推送！");
		try {
			List<ThirdDingNotifyQueue> list = this.findList("1=1", null);
			logger.debug("推送的条数为：" + list.size());
			log("推送的条数为：" + list.size());
			if (list != null && list.size() > 0) {
				for (ThirdDingNotifyQueue queue : list) {
					logger.warn("正在推送：" + queue.getDocSubject());
					log("正在推送：" + queue.getDocSubject());
					SysNotifyTodo todo = (SysNotifyTodo) getSysNotifyTodoService()
							.findByPrimaryKey(queue.getFdTodoId(), null,
									true);
					if(todo==null){
						logger.warn("待办已删除：{}",queue.getDocSubject());
						delete(queue);
						continue;
					}

					DingOfficeMessage dingOfficeMessage = createNotifyToDoDTO(
							todo);
					String[] dingUserIds = queue.getFdUserids().split(",");
					StringBuffer userIds = new StringBuffer();
					boolean isEnd = false;
					//boolean hasErro = false;
					String erroUserids = null;
					for (int i = 0; i < dingUserIds.length; i++) {
						userIds.append(dingUserIds[i] + ",");
						if ((i + 1) % 100 == 0) {
							if (userIds.length() == 0) {
								continue;
							}
							Long endTime = queue.getFdEndTime();
							logger.debug("endTime:" + endTime);
							if (endTime != null && endTime != 0) {
								if (System.currentTimeMillis() > endTime) {
									logger.warn("当前时间已经超过了消息的截止时间，不再推送消息...");
									logger.warn("endTime:" + endTime);
									this.delete(queue);
									isEnd = true;
									break;
								}
							}
							// send
							dingOfficeMessage.setTouser(userIds.toString());
							getThirdDingTodoProvider().sendNotify(todo,userIds.toString(),null,false,dingOfficeMessage,null);
							userIds.setLength(0);
						}
					}
					if (isEnd) {
						continue;
					}
					if (StringUtils.isNotEmpty(userIds.toString())) {
						Long endTime = queue.getFdEndTime();
						logger.debug("endTime:" + endTime);
						if (endTime != null && endTime != 0) {
							if (System.currentTimeMillis() > endTime) {
								logger.warn("当前时间已经超过了消息的截止时间，不再推送消息...");
								logger.warn("endTime:" + endTime);
								this.delete(queue);
								continue;
							}
						}
						// send
						dingOfficeMessage.setTouser(userIds.toString());
						getThirdDingTodoProvider().sendNotify(todo,userIds.toString(),null,false,dingOfficeMessage,null);
						userIds.setLength(0);
					}
					this.delete(queue);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			runQueueLocked = false;
		}
	}

	@Deprecated
	private String sendNotifyOld(SysNotifyTodo todo,
			String userid, String deptid,
			boolean flag, DingOfficeMessage dingOfficeMessage)
			throws Exception {
		count++;
		if (count % 25 == 0) {
			logger.warn("连续发待办超过25次，睡眠0.4秒，避免接口调用过度");
			Thread.sleep(400);
		}
		long time = System.currentTimeMillis();
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setFdSendTime(start);
		String result = null;
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
			logger.debug("工作通知待办title:" + title);
			content.put("title", title);
			content.put("pc_message_url", (String) map.get("pc_message_url"));
			content.put("message_url", (String) map.get("message_url"));
			logger.debug("发送数据参数，基本信息:" + content + ",用户信息：" + userid + ",部门信息"
					+ deptid + ",是否全员发送：" + flag);
			Long agentid = 0L;
			if (StringUtil
					.isNotNull(DingConfig.newInstance().getDingAgentid())) {
				agentid = Long
						.parseLong(DingConfig.newInstance().getDingAgentid());

				// 查找钉钉应用
				if ("true".equals(DingConfig.newInstance().getSendAsModel())) {
					String app_agentid = getDingWorkByModel(todo);
					logger.debug("app_agentid:" + app_agentid);
					if (StringUtil.isNotNull(app_agentid)) {
						agentid = Long.valueOf(app_agentid);
					}
				}

			}

			log.setFdNotifyData("发送数据参数，基本信息:" + content + ",用户信息：" + userid
					+ ",部门信息" + deptid + ",是否全员发送：" + flag);
			result = dingService.messageSend(content, userid, deptid,
					flag, agentid, todo.getDocCreator().getFdId());
			logger.debug("消息发送返回消息：" + result);
			boolean valid = JSONObject.isValid(result);
			if (valid) {
				JSONObject jo = JSONObject.parseObject(result);
				if (jo.containsKey("errcode")
						&& jo.getInteger("errcode") == 0) {
					log.setFdResult(true);
				} else {
					log.setFdResult(false);
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

		logger.debug("发送钉钉消息耗时：" + (System.currentTimeMillis() - time) + "毫秒");
		return result;
	}

	private String getDingWorkByModel(SysNotifyTodo todo) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		List<ThirdDingWork> dingWorkList = getThirdDingWorkService()
				.findList(hqlInfo);
		if (null == dingWorkList || dingWorkList.size() <= 0) {
			return null;
		}
		for (ThirdDingWork dingWork : dingWorkList) {
			if (StringUtil.isNotNull(dingWork.getFdUrlPrefix())) {
				String[] fdUrlPrefix = dingWork.getFdUrlPrefix().split(";");
				for (int i = 0; i < fdUrlPrefix.length; i++) {
					if (todo.getFdLink().indexOf(fdUrlPrefix[i]) > -1) {
						return dingWork.getFdAgentid();
					}
				}
			}
		}
		return null;
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
		String fdId = todo.getFdId();
		String modelName = "";
		String creatorName = null;
		if (todo.getDocCreator() != null) {
			creatorName = todo.getDocCreator().getFdName(fdLang);
		}
		String createDate = com.sunbor.util.DateUtil
				.convertDateToString(todo.getFdCreateTime());
		SysDictModel sysDict = SysDataDict.getInstance()
				.getModel(todo.getFdModelName());
		if (sysDict != null) {
			modelName = ResourceUtil.getStringValue(sysDict.getMessageKey(),
					null, locale);
		} else {
			modelName = todo.getFdAppName();
		}

		dingOfficeMessage.setAgentid(DingConfig.newInstance().getDingAgentid());
		dingOfficeMessage.setMsgtype("oa");

		Map<String, Object> map = new HashMap<String, Object>();

		DingOfficeHead dingOfficeHead = new DingOfficeHead();
		String titleColor = DingConfig.newInstance().getDingTitleColor();
		if (StringUtils.isEmpty(titleColor)) {
			titleColor = "FF9A89B9";
		}

		dingOfficeHead.setBgcolor(titleColor);
		DingOfficeBody dingOfficeBody = new DingOfficeBody();
		dingOfficeBody.setTitle(sub);
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

		dingOfficeBody.setContent(bodyContent);
		String domainName = DingConfig.newInstance().getDingDomain();
		String viewUrl = null;
		if (StringUtil.isNotNull(todo.getFdId())) {
			viewUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
					+ todo.getFdId() + "&oauth=ekp&dingOut=true";
			if (StringUtils.isNotEmpty(domainName)) {
				viewUrl = domainName + viewUrl;
			} else {
				viewUrl = StringUtil.formatUrl(viewUrl);
			}
		} else {
			if (StringUtil.isNull(domainName)) {
                domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
            }
			if (domainName.endsWith("/")) {
                domainName = domainName.substring(0, domainName.length() - 1);
            }
			viewUrl = StringUtil.formatUrl(todo.getFdLink(), domainName);
			if (viewUrl.indexOf("?") == -1) {
				viewUrl = viewUrl + "?oauth=ekp&dingOut=true";
			} else {
				viewUrl = viewUrl + "&oauth=ekp&dingOut=true";
			}
		}
		map.put("message_url", viewUrl);
		String pcViewUrl = DingUtil.getPcViewUrl(todo);

		if ("true".equals(DingConfig.newInstance().getDingTodoSsoEnabled())) {

			pcViewUrl = StringUtil
					.formatUrl("/resource/jsp/sso_redirect.jsp?url="
							+ SecureUtil.BASE64Encoder(viewUrl));
		}
		map.put("pc_message_url", pcViewUrl);
		map.put("head", dingOfficeHead);
		map.put("body", dingOfficeBody);
		dingOfficeMessage.setOa(map);

		String msg = "钉钉应用ID:" + DingConfig.newInstance().getDingAgentid()
				+ "访问url:" + viewUrl;
		logger.debug(msg);
		return dingOfficeMessage;
	}
}
