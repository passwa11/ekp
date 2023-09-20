package com.landray.kmss.third.ding.provider;

import com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest;
import com.dingtalk.api.request.OapiWorkrecordAddRequest;
import com.dingtalk.api.request.OapiWorkrecordAddRequest.FormItemVo;
import com.dingtalk.api.request.OapiWorkrecordUpdateRequest;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskUpdateResponse;
import com.dingtalk.api.response.OapiWorkrecordAddResponse;
import com.dingtalk.api.response.OapiWorkrecordUpdateResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.sys.config.dict.SysDataDict;
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
import com.landray.kmss.third.ding.dto.CorMode;
import com.landray.kmss.third.ding.messageType.DingOfficeBody;
import com.landray.kmss.third.ding.messageType.DingOfficeHead;
import com.landray.kmss.third.ding.messageType.DingOfficeMessage;
import com.landray.kmss.third.ding.model.*;
import com.landray.kmss.third.ding.notify.util.NotifyUtil;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.*;
import com.landray.kmss.third.ding.util.*;
import com.landray.kmss.util.*;
import com.taobao.api.ApiException;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import java.util.*;

public class DingNotifyProvider extends BaseSysNotifyProviderExtend implements
		IEventMulticasterAware,DingConstant,IThirdDingErrorHandlerService {
	
	private static final Logger logger = LoggerFactory.getLogger(DingNotifyProvider.class);
	
	private static final String EXTENSION_POINT_SUBFLOW = "com.landray.kmss.sys.workflow.support.oa.subprocess";

	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}
	
	private IOmsRelationService omsRelationService;

	public void setOmsRelationService(IOmsRelationService omsRelationService) {
		this.omsRelationService = omsRelationService;
	}
	
	private IThirdDingNotifyService thirdDingNotifyService;

	public void setThirdDingNotifyService(IThirdDingNotifyService thirdDingNotifyService) {
		this.thirdDingNotifyService = thirdDingNotifyService;
	}

	private IThirdDingNotifyQueueService thirdDingNotifyQueueService;

	public IThirdDingNotifyQueueService getThirdDingNotifyQueueService() {
		if (thirdDingNotifyQueueService == null) {
			return (IThirdDingNotifyQueueService) SpringBeanUtil
					.getBean("thirdDingNotifyQueueService");
		}
		return thirdDingNotifyQueueService;
	}

	private IThirdDingNotifylogService thirdDingNotifylogService;

	public void setThirdDingNotifylogService(IThirdDingNotifylogService thirdDingNotifylogService) {
		this.thirdDingNotifylogService = thirdDingNotifylogService;
	}
	
	private IThirdDingDinstanceService thirdDingDinstanceService;

	public void setThirdDingDinstanceService(IThirdDingDinstanceService thirdDingDinstanceService) {
		this.thirdDingDinstanceService = thirdDingDinstanceService;
	}
	
	private IThirdDingDtaskService thirdDingDtaskService;

	public void setThirdDingDtaskService(IThirdDingDtaskService thirdDingDtaskService) {
		this.thirdDingDtaskService = thirdDingDtaskService;
	}

	public void setSysNotifyTodoService(ISysNotifyTodoService sysNotifyTodoService) {
		this.sysNotifyTodoService = sysNotifyTodoService;
	}


	@Override
    public void add(SysNotifyTodo todo, NotifyContext context) throws Exception {
		if (!DingUtil.checkNotifyApiType("WF")) {
			return;
		}
		long atime = System.currentTimeMillis();
		if(!"true".equals(DingConfig.newInstance().getDingEnabled())){
			logger.debug("钉钉未开启集成，不推送消息...");
			return;
		}
		boolean notifyflag = false;
		logger.debug("开始向钉钉推送消息...");
		String appType = context.getFdAppType();
		if (StringUtil.isNotNull(appType) ) {
			if (appType.contains("all") || appType.contains("ding")) {
				notifyflag = true;
				logger.debug("已经设置参数fdAppType=" + appType + ",故强制推送消息...");
			}
		}else if(StringUtil.isNull(appType)){
			// if
			// (!"true".equals(DingConfig.newInstance().getDingTodoEnabled())) {
			// logger.debug("钉钉消息推送未开启...");
			// return;
			// }
			
			if (todo.getFdType() == 2) {
				if (!"true".equals(DingConfig.newInstance().getDingTodotype2Enabled())) {
					// 待阅关闭,默认关闭，开启需要勾上
					logger.debug("未开启待阅消息推送到钉钉的消息中心...");
				}else{
					notifyflag = true;
				}
			}
			if (todo.getFdType() == 1) {
				if (!"true".equals(DingConfig.newInstance().getDingTodotype1Enabled())) {
					// 待阅关闭,默认关闭，开启需要勾上
					logger.debug("未开启待办消息推送到钉钉的消息中心...");
				}else{
					notifyflag = true;
				}
				if (!"true".equals(DingConfig.newInstance().getDingWorkRecordEnabled())) {
					// 待阅关闭,默认关闭，开启需要勾上
					logger.debug("未开启待办消息推送到钉钉的待办中心...");
				}else{
					notifyflag = true;
				}
			}
		}
		if(!notifyflag){
			logger.debug("都未开启待阅、待办、工作待办消息推送,参数fdAppType=" + appType + ",待办Id="+todo.getFdId()+",待办主题："+todo.getFdSubject());
			return;
		}else{
			// 获取模板
			init();
		}
		
		NotifyContextImp ctx = (NotifyContextImp) context;
		List notifyTargetList = ((NotifyContextImp) context).getNotifyPersons();
		if (notifyTargetList == null || notifyTargetList.isEmpty()) {
			logger.debug("通知人员为空不执行消息发送，通知标题为："+todo.getFdSubject()+"("+todo.getFdId()+")");
			return;
		}else{
			DingOfficeMessage dingOfficeMessage = createNotifyToDoDTO(todo);
			List<String> ekpIds = new ArrayList<String>();
			if (notifyTargetList != null && notifyTargetList.size() > 0) {
				Iterator<?> it = ctx.getNotifyPersons().iterator();
				while (it.hasNext()) {
					SysOrgPerson sysOrgPerson = (SysOrgPerson) it.next();
					ekpIds.add(sysOrgPerson.getFdId());
				}
			}
			
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock(
					" omsRelationModel.fdEkpId, omsRelationModel.fdAppPkId ");
			hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("omsRelationModel.fdEkpId", ekpIds)
					+ " GROUP BY omsRelationModel.fdEkpId, omsRelationModel.fdAppPkId");
			List<Object[]> models = omsRelationService.findList(hqlInfo);
			String ekpUserId = null;
			if (models == null || models.size() == 0) {
				logger.debug("通过EKP的fdId查找中间映射表发现找不到对应的钉钉人员("+ekpIds+")，请先维护中间映射表数据");
			}
			// 同一个待办过滤重复的数据(钉钉)
			Map<String,Object[]> dingMap = new HashMap<String,Object[]>();
			if (models != null) {
				for (int i = 0; i < models.size(); i++) {
					Object[] info = (Object[]) models.get(i);
					if (info[1]!=null&&StringUtil.isNotNull(info[1].toString())
							&& !"null".equalsIgnoreCase(info[1].toString())) {
                        ekpUserId = info[0].toString();
                    }
					dingMap.put(info[1].toString(), info);
				}
			}
			logger.debug("待办对象中代表人员fdid:" + ekpUserId);
			List<Object[]> omsModels = new ArrayList<Object[]>(dingMap.values());
			boolean isMoreThen500 = false;
			if (omsModels.size() >= 500) {
				logger.warn("=======加到通知队列通知=========");
				isMoreThen500 = true;
				String _userIds = StringUtils.join(dingMap.keySet().toArray(),
						",");
				logger.debug("_userIds:" + _userIds);
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
			StringBuffer userIds = new StringBuffer();

			if(omsModels!=null && omsModels.size() > 0){
				ThirdDingDtemplate template = getTemplate(todo.getFdLang());
				//待办待阅推送
				if (!isMoreThen500 && (("true".equals(
						DingConfig.newInstance().getDingTodotype1Enabled())
						&& todo.getFdType() == 1)
						|| ("true".equals(DingConfig.newInstance().getDingTodotype2Enabled())&&todo.getFdType() == 2)
						|| (StringUtil.isNotNull(appType)
								&& (appType.contains("all")
										|| appType.contains("ding"))))) {
					for (int i = 0; i < omsModels.size(); i++) {
						Object[] info = (Object[]) omsModels.get(i);
						userIds.append(info[1].toString() + ",");
						if ((i + 1) % 100 == 0) {
							if (userIds.length() == 0) {
								continue;
							}
							String touser = userIds.toString().substring(0, userIds.toString().length() - 1);
							dingOfficeMessage.setTouser(touser);
							Long endTime = context.getFdEndTime();
							logger.debug("endTime:" + endTime);
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
						logger.debug("endTime:" + endTime);
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
				// 工作待办推送
				if ("true".equals(DingConfig.newInstance().getDingWorkRecordEnabled())&&todo.getFdType() == 1){
					if(omsModels.size()>0){
						// 获取流程实例提交人
						String dingId = instanceCreator(todo);
						if(StringUtil.isNull(dingId)){
							dingId = (String) ((Object[])omsModels.get(0))[1];
						}
						String wrurl = (String) dingOfficeMessage.getOa().get("message_url");
						String wrurl_pc = (String) dingOfficeMessage.getOa()
								.get("pc_message_url");
						TransactionStatus add = null;
						try {
							add = TransactionUtils
									.beginNewTransaction();
							ThirdDingDinstance dinstance = thirdDingDinstanceService
									.findCommonInstance(todo,
											template.getFdId());
							if (dinstance == null) {
								String fdId = thirdDingDinstanceService
										.addCommonInstance(dingId, todo,
												template, detailsMap.get(
														template.getFdId()));
								dinstance = (ThirdDingDinstance) thirdDingDinstanceService
										.findByPrimaryKey(fdId);
							}
							if (dinstance != null) {
								for (int i = 0; i < omsModels.size(); i++) {
									Long endTime = context.getFdEndTime();
									logger.debug("endTime:" + endTime);
									if (endTime != null && endTime != 0) {
										if (System
												.currentTimeMillis() > endTime) {
											logger.warn(
													"当前时间已经超过了消息的截止时间，不再推送消息...");
											logger.warn("endTime:" + endTime);
											return;
										}
									}
									addWorkrecord((Object[]) omsModels.get(i),
											todo,
											wrurl_pc, dinstance,
											template.getFdAgentId());
								}
							}
							TransactionUtils.commit(add);
						} catch (Exception e) {
							if (add != null) {
								try {
									TransactionUtils.getTransactionManager()
											.rollback(add);
								} catch (Exception ex) {
									logger.error("---事务回滚出错---", ex);
								}
							}
						}
					}
				}
			}
		}
		logger.debug("发送钉钉消息总耗时："+(System.currentTimeMillis()-atime)+"毫秒");
	}
	
	private IThirdDingDtemplateService thirdDingDtemplateService = null;

	public IThirdDingDtemplateService getThirdDingDtemplateService() {
		if (thirdDingDtemplateService == null) {
			thirdDingDtemplateService = (IThirdDingDtemplateService) SpringBeanUtil
					.getBean("thirdDingDtemplateService");
		}
		return thirdDingDtemplateService;
	}
	
	private Map<String, ThirdDingDtemplate> templatesMap = null;

	private Map<String, List<ThirdDingTemplateDetail>> detailsMap = null;

	public Map<String, ThirdDingDtemplate> getTemplatesMap() {
		return templatesMap;
	}

	public void setTemplatesMap(Map<String, ThirdDingDtemplate> templatesMap) {
		this.templatesMap = templatesMap;
	}

	private ThirdDingDtemplate defaultTemplate = null;
	
	// private Map<String, List<ThirdDingTemplateDetail>> detailsMap = null;
	
	public ThirdDingDtemplate getDefaultTemplate() {
		return defaultTemplate;
	}

	public void setDefaultTemplate(ThirdDingDtemplate defaultTemplate) {
		this.defaultTemplate = defaultTemplate;
	}

	private List<ThirdDingTemplateDetail>
			buildDetais(ThirdDingDtemplate template) {
		List<ThirdDingTemplateDetail> result = new ArrayList<ThirdDingTemplateDetail>();
		List<ThirdDingTemplateDetail> details = template.getFdDetail();
		for (ThirdDingTemplateDetail detail : details) {
			ThirdDingTemplateDetail dt = new ThirdDingTemplateDetail();
			dt.setFdName(detail.getFdName());
			dt.setFdType(detail.getFdType());
			dt.setDocIndex(detail.getDocIndex());
			result.add(dt);
		}
		return result;
	}

	private void init() throws Exception {

		if (templatesMap == null) {
			TransactionStatus addStatus = null;
			try {
				addStatus = TransactionUtils
						.beginNewTransaction();
				templatesMap = new HashMap<String, ThirdDingDtemplate>();
				detailsMap = new HashMap<String, List<ThirdDingTemplateDetail>>();
				List<String> langs = null;
				String officialLang = "zh-CN";
				if (SysLangUtil.isLangEnabled()) {
					langs = SysLangUtil.getSupportedLangList();
					officialLang = ResourceUtil
							.getKmssConfigString("kmss.lang.official");
					officialLang = officialLang
							.substring(officialLang.indexOf("\\|") + 1);
				}
				if (langs == null || langs.isEmpty()) {
					ThirdDingDtemplate template = getThirdDingDtemplateService()
							.updateCommonTemplate(null);
					if (template == null) {
						getThirdDingDtemplateService().addCommonTemplate(null,
								null);
						template = getThirdDingDtemplateService()
								.updateCommonTemplate(null);
					}
					// if(details==null){
					// details = new ArrayList(template.getFdDetail());
					// }
					templatesMap.put("default", template);
					defaultTemplate = template;
					// List<ThirdDingTemplateDetail> details =
					// template.getFdDetail();
					if (template != null) {
                        detailsMap.put(template.getFdId(),
                                buildDetais(template));
                    }
				} else {
					ThirdDingDtemplate template = getThirdDingDtemplateService()
							.updateCommonTemplate(officialLang);
					if (template == null) {
						getThirdDingDtemplateService().addCommonTemplate(null,
								officialLang);
						template = getThirdDingDtemplateService()
								.updateCommonTemplate(officialLang);
					}
					templatesMap.put("default", template);
					defaultTemplate = template;
					if (template != null) {
						detailsMap.put(template.getFdId(),
								buildDetais(template));
					} else {
						logger.error("钉钉待办模板为空，可能是该钉钉账号模板数超过了100！");
					}
					for (String lang : langs) {
						template = getThirdDingDtemplateService()
								.updateCommonTemplate(lang);
						if (template == null) {
							getThirdDingDtemplateService().addCommonTemplate(
									null,
									lang);
							template = getThirdDingDtemplateService()
									.updateCommonTemplate(lang);
						}
						templatesMap.put(lang, template);
						if (template != null) {
							detailsMap.put(template.getFdId(),
									buildDetais(template));
						} else {
							logger.error("钉钉待办模板为空，可能是该钉钉账号模板数超过了100！");
						}

					}
				}
				TransactionUtils.commit(addStatus);
			} catch (Exception e) {
				if (addStatus != null) {
					try {
						TransactionUtils.getTransactionManager()
								.rollback(addStatus);
					} catch (Exception ex) {
						logger.error("---事务回滚出错---", ex);
					}
				}
				throw e;
			}
		}
	}
	
	private ThirdDingDtemplate getTemplate(String fdLang) {
		ThirdDingDtemplate template = null;
		if (StringUtil.isNull(fdLang)) {
			template = defaultTemplate;
		} else {
			template = templatesMap.get(fdLang);
		}
		if (template == null) {
			template = defaultTemplate;
		}
		return template;
	}
	

	private String instanceCreator(SysNotifyTodo todo) throws Exception{
		String dinguserid = null;
		if (todo.getDocCreator() != null) {
			String ekpuserid = todo.getDocCreator().getFdId();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("fdAppPkId");
			hqlInfo.setWhereBlock("fdEkpId='" + ekpuserid + "'");
			String  fdAppPkId = (String) omsRelationService.findFirstOne(hqlInfo);
			if (StringUtils.isNotBlank(fdAppPkId)) {
				dinguserid = fdAppPkId;
			} else {
				logger.debug("文档创建人信息在钉钉集成模块的人员对照表里面找不到对应的钉钉人员,默认使用接收人替代");
			}
		}
		return dinguserid;
	}

	private void sendNotify(SysNotifyTodo todo, String userid, String deptid,
			boolean flag, DingOfficeMessage dingOfficeMessage, String ekpUserId)
			throws Exception {
		long time = System.currentTimeMillis();
		try{
			DingApiService dingService = DingUtils.getDingApiService();
			Map<String,String> content = new HashMap<String, String>();
			Map<String,Object> map = dingOfficeMessage.getOa();
			DingOfficeHead head = (DingOfficeHead) map.get("head");
			content.put("color", head.getBgcolor());
			DingOfficeBody body = (DingOfficeBody) map.get("body");
			content.put("content", body.getContent());
			content.put("title", body.getTitle());
			String dingAppKey = DingUtil.getDingAppKeyByEKPUserId("&",
					ekpUserId);
			logger.debug("-----工作通知pc_message_url："
					+ (String) map.get("pc_message_url") + dingAppKey);
			logger.debug("-----工作通知message_url："
					+ (String) map.get("message_url") + dingAppKey);
			content.put("pc_message_url",
					(String) map.get("pc_message_url") + dingAppKey);
			content.put("message_url",
					(String) map.get("message_url") + dingAppKey);
			logger.debug("发送数据参数，基本信息:"+ content+",用户信息："+userid+",部门信息"+deptid+",是否全员发送："+flag);
			String result = dingService.messageSend(content, userid, deptid,
					flag, NotifyUtil.getAgendId(todo), ekpUserId);
			logger.debug("消息发送返回消息："+result);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("发送钉钉消息异常：",e);
		}
		logger.debug("发送钉钉消息耗时："+(System.currentTimeMillis()-time)+"毫秒");
	}
	
	private void sendNotifyOld(SysNotifyTodo todo,DingOfficeMessage dingOfficeMessage) throws Exception {
		try{
			CorMode corMode = new CorMode();
			corMode.setCorpid(DingConfig.newInstance().getDingCorpid());
			String secret = DingConfig.newInstance().getDingCorpSecret();
			if(StringUtil.isNotNull(secret)){
				corMode.setCorpsecret(DingConfig.newInstance().getDingCorpSecret());
			}else{
				corMode.setCorpsecret(DingConfig.newInstance().getAppSecret());
			}
			String tokenId = TokenUtils.getToken(corMode);
			if (StringUtils.isNotEmpty(tokenId)) {
				long time = System.currentTimeMillis();
				String send_message_url = DingConstant.DING_PREFIX + "/message/send?access_token=ACCESS_TOKEN";
				String url = send_message_url.replace("ACCESS_TOKEN", tokenId);
				logger.debug("请求地址:" + url+",发送数据:"+ JSONObject.fromObject(dingOfficeMessage).toString());
				String result = DingHttpClientUtil.httpPost(url, dingOfficeMessage,
						"errcode", String.class);
				if(!"0".equals(result)){
					logger.error("消息发送失败..."+result);
					addError(todo,JSONObject.fromObject(dingOfficeMessage).toString(), result, "message");
				}else{
					logger.debug("消息发送成功...");
				}
				logger.debug("发送钉钉消息耗时："+(System.currentTimeMillis()-time)+"毫秒");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("发送钉钉消息异常：",e);
		}
	}

	public DingOfficeMessage createNotifyToDoDTO(SysNotifyTodo todo)
			throws IllegalAccessException {

		String fdLang = todo.getFdLang();
		Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
		if (locale == null) {
			locale = ResourceUtil.getLocaleByUser();
		}

		DingOfficeMessage dingOfficeMessage = new DingOfficeMessage();
		String sub = todo.getFdSubject();
		String modelName= getSysNotifyTodoService().getModelNameText(todo);
		String creatorName = null;
		if(todo.getDocCreator()!=null){
			creatorName = todo.getDocCreator().getFdName(fdLang);
		}
		String createDate = com.sunbor.util.DateUtil.convertDateToString(todo.getFdCreateTime());

		dingOfficeMessage.setAgentid(DingConfig.newInstance().getDingAgentid());
		dingOfficeMessage.setMsgtype("oa");

    	Map<String,Object>map = new HashMap<String,Object>();
    	 
    	DingOfficeHead dingOfficeHead = new DingOfficeHead();
		String titleColor = DingConfig.newInstance().getDingTitleColor();
    	if(StringUtils.isEmpty(titleColor)){
    		titleColor="FF9A89B9";
    	}
    	
    	dingOfficeHead.setBgcolor(titleColor);
    	DingOfficeBody dingOfficeBody = new DingOfficeBody();
    	dingOfficeBody.setTitle(sub);
    	String bodyContent=null;
    	if(StringUtil.isNotNull(creatorName)){
    		bodyContent = "${fdCreatorName}:      " + creatorName + "\r\n" + "${docCreateTime}:  " + createDate
    				+ "\r\n" + "${module}:  " + modelName + "";
    		bodyContent = bodyContent
    				.replace("${fdCreatorName}",
    						ResourceUtil.getStringValue("sysNotifyCategory.fdCreatorName", "sys-notify", locale));
    	}else{
    		bodyContent = "${docCreateTime}:  " + createDate + "\r\n" + "${module}:  " + modelName + "";
    	}
    	
		bodyContent = bodyContent
				.replace("${docCreateTime}",
						ResourceUtil.getStringValue("sysNotifyCategory.docCreateTime", "sys-notify", locale))
				.replace("${module}",
						ResourceUtil.getStringValue("sysNotifyCategory.type.module", "sys-notify", locale));
    	
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

		// 钉钉转屏参数
		// viewUrl += "&dd_orientation=auto"; 
		map.put("message_url", viewUrl);
		String pcViewUrl = DingUtil.getPcViewUrl(todo);
		// String pcViewUrl = domainName + "/third/ding/pc/pcopen.jsp?fdTodoId="
		// + fdId
		// + "&appId=" + DingConfig.newInstance().getDingAgentid()
		// + "&oauth=ekp&url="
		// + SecureUtil.BASE64Encoder(todo.getFdLink());
		if ("true".equals(DingConfig.newInstance().getDingTodoSsoEnabled())) {
			// pcViewUrl = viewUrl;
			// String ssoRedirectUrl = ResourceUtil
			// .getKmssConfigString("kmss.ssoclient.redirect.loginURL");
			// if (StringUtil.isNotNull(ssoRedirectUrl)) {
			// ssoRedirectUrl = ssoRedirectUrl
			// .replace("${url}", URLEncoder.encode(pcViewUrl))
			// .replace("${URL}", URLEncoder.encode(pcViewUrl));
			// ssoRedirectUrl = ssoRedirectUrl + "&outopen=true";
			// pcViewUrl = ssoRedirectUrl;
			// }
			pcViewUrl = StringUtil
					.formatUrl("/resource/jsp/sso_redirect.jsp?url="
							+ SecureUtil.BASE64Encoder(viewUrl));
		}
		// System.out.println(pcViewUrl);
		map.put("pc_message_url", pcViewUrl);
    	map.put("head", dingOfficeHead);
    	map.put("body", dingOfficeBody);
    	dingOfficeMessage.setOa(map);

		String msg = "钉钉应用ID:" + DingConfig.newInstance().getDingAgentid()
				+ "访问url:" + viewUrl;
		logger.debug(msg);
		return dingOfficeMessage;
	}

	//场景一：ekp把待办删除,待办接收人应该全部更新为完成
	@Override
	public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
		if (!DingUtil.checkNotifyApiType("WF")) {
			return;
		}
		if (DingUtil.hasInstanceInXform(todo)) {
			logger.warn("-------钉钉审批高级版/套件，接口走钉钉审批接口-------");
			DingXformNotifyProvider.newInstance().clearTodoPersons(todo);
		}
		updateWorkrecord(todo, null, 1);
	}

	@Override
	public void remove(SysNotifyTodo todo) throws Exception {
		if (!DingUtil.checkNotifyApiType("WF")) {
			return;
		}
		if (DingUtil.hasInstanceInXform(todo)) {
			logger.warn("-------钉钉审批高级版/套件，接口走钉钉审批接口-------");
			DingXformNotifyProvider.newInstance().remove(todo);
		}
		logger.debug(" remove ：" + todo.getFdId() + "," + todo.getFdSubject());
		Thread.sleep(1000);
		updateWorkrecord(todo, null, 1);
	}
	
	@Override
	public void setTodoDone(SysNotifyTodo todo) throws Exception {
		if (!DingUtil.checkNotifyApiType("WF")) {
			return;
		}
		if (DingUtil.hasInstanceInXform(todo)) {
			logger.warn("-------钉钉审批高级版/套件，接口走钉钉审批接口-------");
			DingXformNotifyProvider.newInstance().setTodoDone(todo);
		}
		updateWorkrecord(todo, null, 1);
	}

	//场景二：ekp把待办中部分人员设置为已办
	@Override
	public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
		if (!DingUtil.checkNotifyApiType("WF")) {
			return;
		}
		if (DingUtil.hasInstanceInXform(todo)) {
			logger.warn("-------钉钉审批高级版/套件，接口走钉钉审批接口-------");
			DingXformNotifyProvider.newInstance().removeDonePerson(todo,
					person);
		}
		List list = new ArrayList();
		list.add(person);
		updateWorkrecord(todo, list, 2);
	}

	@Override
	public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
		if (!DingUtil.checkNotifyApiType("WF")) {
			return;
		}
		if (DingUtil.hasInstanceInXform(todo)) {
			logger.warn("-------钉钉审批高级版/套件，接口走钉钉审批接口-------");
			DingXformNotifyProvider.newInstance().setPersonsDone(todo,
					persons);
		}
		updateWorkrecord(todo, persons, 2);
	}
	
	//场景三：ekp更新待办
	@Override
	public void updateTodo(SysNotifyTodo todo) throws Exception {
		if (!DingUtil.checkNotifyApiType("WF")) {
			return;
		}
		if (DingUtil.hasInstanceInXform(todo)) {
			logger.warn("-------钉钉审批高级版/套件，接口走钉钉审批接口-------");
			DingXformNotifyProvider.newInstance().updateTodo(todo);
		}
//		updateWorkrecord(todo, null, 3);
		// try {
		// delWorkrecord(todo);
		// add(todo);
		// } catch (Exception e) {
		// logger.error(e.getMessage(), e);
		// }
	}

	private void delWorkrecord(SysNotifyTodo todo)
			throws ApiException, Exception {
		long time = System.currentTimeMillis();
		try {
			String where = "fdStatus!='22'";
			where += " and fdEkpTaskId='" + todo.getFdId() + "'";

			List<ThirdDingDtask> notifies = thirdDingDtaskService
					.findList(where, null);
			if (notifies != null && notifies.size() > 0) {
				for (ThirdDingDtask task : notifies) {
					logger.warn("更新处理人(名称:" + task.getFdEkpUser().getFdName()
							+ ")的钉钉待办(待办名称：" + todo.getFdSubject()
							+ ",主键：" + todo.getFdId() + ")");
					if (StringUtil.isNull(task.getFdTaskId())) {
                        continue;
                    }
					String token = DingUtils.getDingApiService()
							.getAccessToken();
					OapiProcessWorkrecordTaskUpdateRequest req = new OapiProcessWorkrecordTaskUpdateRequest();
					OapiProcessWorkrecordTaskUpdateResponse response = DingNotifyUtil
							.updateTask(req, token,
									task.getFdInstance().getFdInstanceId(),
									Long.parseLong(task.getFdTaskId()),
									Long.parseLong(DingConfig.newInstance()
											.getDingAgentid()),
									task.getFdEkpUser() == null ? null
											: task.getFdEkpUser().getFdId());
					thirdDingDtaskService.delete(task);
					JSONObject jo = JSONObject.fromObject(response.getBody());
					addNotifyLog(todo, jo,
							JSONObject.fromObject(req).toString());
				}
				ThirdDingDinstance dinstance = (ThirdDingDinstance) thirdDingDinstanceService
						.findFirstOne("fdEkpInstanceId='" + todo.getFdModelId()
								+ "' and fdUrl like '%" + todo.getFdId() + "%'",
								null);
				if (dinstance != null) {
					logger.warn("删除实例，fdId:" + dinstance.getFdId()
							+ ",dinstanceId:" + dinstance.getFdInstanceId()
							+ ",fdName:" + dinstance.getFdName() + ",notifyId:"
							+ dinstance.getFdUrl());
					thirdDingDinstanceService.delete(dinstance);

				}
			} else {
				logger.debug("更新待办(待办名称：" + todo.getFdSubject() + ",主键："
						+ todo.getFdId() + ")在钉钉待办任务中找不到对应的数据");
			}
		} catch (Exception e) {
			logger.error("更新钉钉待办状态异常，错误信息：", e);
			throw e;
		}
		logger.debug("更新钉钉待办耗时：" + (System.currentTimeMillis() - time) + "毫秒");
	}

	public void add(SysNotifyTodo todo) throws Exception {
		long atime = System.currentTimeMillis();
		if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
			logger.debug("钉钉未开启集成，不推送消息...");
			return;
		}
		boolean notifyflag = false;
		logger.debug("开始向钉钉推送消息...");

		// if (!"true".equals(DingConfig.newInstance().getDingTodoEnabled())) {
		// logger.debug("钉钉消息推送未开启...");
		// return;
		// }
		if (todo.getFdType() == 1 || todo.getFdType() == 3) {
			if (!"true".equals(
					DingConfig.newInstance().getDingWorkRecordEnabled())) {
				// 待阅关闭,默认关闭，开启需要勾上
				logger.debug("未开启待办消息推送到钉钉的待办中心...");
			} else {
				notifyflag = true;
			}
		}

		if (!notifyflag) {
			logger.debug("都未开启待阅、待办、工作待办消息推送,待办Id=" + todo.getFdId() + ",待办主题："
					+ todo.getFdSubject());
			return;
		} else {
			// 获取模板
			init();
		}

		List notifyTargetList = todo.getHbmTodoTargets();
		if (notifyTargetList == null || notifyTargetList.isEmpty()) {
			logger.debug("通知人员为空不执行消息发送，通知标题为：" + todo.getFdSubject() + "("
					+ todo.getFdId() + ")");
			return;
		} else {
			DingOfficeMessage dingOfficeMessage = createNotifyToDoDTO(todo);
			List<String> ekpIds = new ArrayList<String>();
			if (notifyTargetList != null && notifyTargetList.size() > 0) {
				Iterator<?> it = notifyTargetList.iterator();
				while (it.hasNext()) {
					SysOrgPerson sysOrgPerson = (SysOrgPerson) it.next();
					ekpIds.add(sysOrgPerson.getFdId());
				}
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock(
					" omsRelationModel.fdEkpId, omsRelationModel.fdAppPkId ");
			hqlInfo.setWhereBlock(
					HQLUtil.buildLogicIN("omsRelationModel.fdEkpId", ekpIds)
							+ " GROUP BY omsRelationModel.fdEkpId, omsRelationModel.fdAppPkId");
			List<Object[]> models = omsRelationService.findList(hqlInfo);
			if (models == null || models.size() == 0) {
				logger.debug("通过EKP的fdId查找中间映射表发现找不到对应的钉钉人员(" + ekpIds
						+ ")，请先维护中间映射表数据");
			}
			// 同一个待办过滤重复的数据(钉钉)
			Map<String, Object[]> dingMap = new HashMap<String, Object[]>();
			if (models != null) {
				for (int i = 0; i < models.size(); i++) {
					Object[] info = (Object[]) models.get(i);
					if (info[1] != null
							&& StringUtil.isNotNull(info[1].toString())
							&& !"null".equalsIgnoreCase(info[1].toString())) {
                        dingMap.put(info[1].toString(), info);
                    }
				}
			}
			List<Object[]> omsModels = new ArrayList<Object[]>(
					dingMap.values());
			if (omsModels != null && omsModels.size() > 0) {
				// 工作待办推送
				ThirdDingDtemplate template = getTemplate(todo.getFdLang());
					if (omsModels.size() > 0) {
						// 获取流程实例提交人
						String dingId = instanceCreator(todo);
						if (StringUtil.isNull(dingId)) {
							dingId = (String) ((Object[]) omsModels.get(0))[1];
						}
						String wrurl = (String) dingOfficeMessage.getOa()
								.get("message_url");
					String wrurl_pc = (String) dingOfficeMessage.getOa()
							.get("pc_message_url");
						ThirdDingDinstance dinstance = thirdDingDinstanceService
							.findCommonInstance(todo, template.getFdId());
					if (dinstance == null) {

							String fdId = thirdDingDinstanceService
									.addCommonInstance(dingId, todo, template,detailsMap.get(template.getFdId())
						);
							dinstance = (ThirdDingDinstance) thirdDingDinstanceService
									.findByPrimaryKey(fdId);
						}
						if (dinstance != null) {
							for (int i = 0; i < omsModels.size(); i++) {
								addWorkrecord((Object[]) omsModels.get(i), todo,
									wrurl_pc, dinstance,
										template.getFdAgentId());
							}
						}
					}
			}
		}
		logger.debug(
				"发送钉钉消息总耗时：" + (System.currentTimeMillis() - atime) + "毫秒");
	}

	private void addWorkrecord(Object[] model, SysNotifyTodo todo, String url,ThirdDingDinstance dinstance,Long agentId) throws Exception {
		long time = System.currentTimeMillis();
		ThirdDingDtask dtask = new ThirdDingDtask();
		SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(model[0].toString());
		dtask.setFdDingUserId(model[1].toString());
		dtask.setFdEkpTaskId(todo.getFdId());
		dtask.setFdInstance(dinstance);
		dtask.setFdName(todo.getFdSubject());
		dtask.setFdUrl(url);
		dtask.setFdEkpUser(person);
		thirdDingDtaskService.addTask(dtask,todo,agentId);
		logger.debug("发送钉钉待办耗时："+(System.currentTimeMillis()-time)+"毫秒");
	}
	
	private void addWorkrecordOld(Object[] model, SysNotifyTodo todo, String url) throws Exception {
		DingApiService dingService = DingUtils.getDingApiService();
		ThirdDingTalkClient client = new ThirdDingTalkClient(DingConstant.DING_PREFIX + "/topapi/workrecord/add");
		OapiWorkrecordAddRequest req = new OapiWorkrecordAddRequest();
		req.setUserid(model[1].toString());
		req.setCreateTime((new Date()).getTime());
		SysDictModel sysDictModel = SysDataDict.getInstance().getModel(todo.getFdModelName());
		String title = null;
		if (null == sysDictModel) {
			title = todo.getFdModelName();
		} else {
			title = ResourceUtil.getString(sysDictModel.getMessageKey());
		}
		req.setTitle(title);
		req.setUrl(url);
		List<FormItemVo> itemVos = new ArrayList<FormItemVo>();
		FormItemVo vo = new FormItemVo();
		vo.setTitle("标题");
		String content = todo.getFdSubject();
		switch (todo.getFdLevel()) {
		case 1:
			content = "【紧急】" + content;
			break;
		case 2:
			content = "【急】" + content;
			break;
		default:
			content = "【一般】" + content;
			break;
		}
		vo.setContent(content);
		itemVos.add(vo);
		vo = new FormItemVo();
		vo.setTitle("创建时间");
		vo.setContent(DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		itemVos.add(vo);
		req.setFormItemList(itemVos);
		OapiWorkrecordAddResponse rsp = client.execute(req, dingService.getAccessToken());
		JSONObject jo = JSONObject.fromObject(rsp.getBody());
		if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
			ThirdDingNotify dingNotify = new ThirdDingNotify();
			dingNotify.setFdDingUserId(model[1].toString());
			dingNotify.setFdEkpUserId(model[0].toString());
			dingNotify.setFdModelId(todo.getFdId());
			dingNotify.setFdModelName(todo.getFdModelName());
			dingNotify.setFdParam1(todo.getFdParameter1());
			dingNotify.setFdParam2(todo.getFdParameter2());
			dingNotify.setFdRecordId(jo.getString("record_id"));
			dingNotify.setDocCreateTime(new Date());
			dingNotify.setFdEKPDel("0");
			thirdDingNotifyService.add(dingNotify);
			logger.info("待办发送到钉钉详细：" + jo.toString());
		} else {
			logger.info("待办发送到钉钉创建失败。详细：" + jo.toString());
			JSONObject ejo = new JSONObject();
			ejo.put("userid", model[1].toString());
			ejo.put("ekpid", model[0].toString());
			ejo.put("modelid", todo.getFdId());
			ejo.put("modelname", todo.getFdModelName());
			ejo.put("title", title);
			ejo.put("subject", todo.getFdSubject());
			ejo.put("url", url);
			addError(todo, ejo.toString(), jo.toString(), "notify");
		}
		//保存待办发送日志
		addNotifyLog(todo, jo, JSONObject.fromObject(req).toString());
	}
	
	private void updateWorkrecord(SysNotifyTodo todo, List<SysOrgElement> persons, int type) throws ApiException, Exception {
		long time = System.currentTimeMillis();
		try {
			logger.debug("------更新待办----  type：" + type);
			/*if (todo.getFdType() == 3 && type == 3) {
				logger.debug("当前待办(待办名称：" + todo.getFdSubject() + ")为暂挂，直接跳过不更新钉钉的待办");
				return;
			}*/
			String where = "fdStatus!='22'";
			if (persons == null || persons.size() == 0) {
				where += " and fdEkpTaskId='" + todo.getFdId() + "'";
			} else {
				where += " and fdEkpTaskId='" + todo.getFdId() + "'";
				StringBuffer ids = new StringBuffer();
				for (SysOrgElement target : persons) {
					if (target == null) {
                        continue;
                    }
					ids.append("'" + target.getFdId() + "',");
				}
				String fdIds = null;
				if (ids.length() > 0 && ids.toString().endsWith(",")) {
                    fdIds = ids.toString().substring(0, ids.length() - 1);
                }
				if (StringUtil.isNotNull(fdIds)) {
					where += " and fdEkpUser.fdId in (" + fdIds + ")";
				}
			}
			Map<String, String> tpidMap = new HashMap<String, String>();
			if(type == 3){
				SysNotifyTodo rtodo = (SysNotifyTodo) getSysNotifyTodoService().findByPrimaryKey(todo.getFdId(), null, true);
				if (rtodo != null){
					List<SysOrgElement> targets = rtodo.getHbmTodoTargets();
					for (SysOrgElement ele : targets) {
						tpidMap.put(ele.getFdId(), ele.getFdId());
					}
				}
			}
			TransactionStatus status = null;
			try {
				status = TransactionUtils
						.beginNewTransaction();
				List<ThirdDingDtask> notifies = thirdDingDtaskService
						.findList(where, null);
				if (notifies != null && notifies.size() > 0) {
					for (ThirdDingDtask task : notifies) {
						logger.debug("更新处理人(名称:" + task.getFdEkpUser().getFdId()
								+ ")的钉钉待办(待办名称：" + todo.getFdSubject()
								+ ",主键：" + todo.getFdId() + ")");
						if (StringUtil.isNull(task.getFdTaskId())) {
                            continue;
                        }
						// 当前人还在待办中直接跳过
						if (tpidMap.containsKey(task.getFdEkpUser().getFdId())
								&& type == 3) {
                            continue;
                        }
						String token = DingUtils.getDingApiService()
								.getAccessToken();
						OapiProcessWorkrecordTaskUpdateRequest req = new OapiProcessWorkrecordTaskUpdateRequest();
						OapiProcessWorkrecordTaskUpdateResponse response = DingNotifyUtil
								.updateTask(req, token,
										task.getFdInstance().getFdInstanceId(),
										Long.parseLong(task.getFdTaskId()),
										Long.parseLong(DingConfig.newInstance()
												.getDingAgentid()),
										task.getFdEkpUser() == null ? null
												: task.getFdEkpUser()
														.getFdId());
						if (response.getErrcode() == 0) {
							task.setFdStatus("22");
							logger.debug("更新待办状态成功！");

							/*
							 * 此代码暂时屏蔽，后续更新实例状态需要修改 （#122575
							 * 工作沟通功个人回复时，会把其他人钉钉中的沟通置为已办）
							 *  注意：无表单流程的待办实例状态无法更新
							 */
							// try {
							// String modelName = todo.getFdModelName();
							// if (StringUtil.isNotNull(modelName)) {
							// // 父子流程
							// IExtension[] subExtensions = Plugin
							// .getExtensions(
							// EXTENSION_POINT_SUBFLOW,
							// "*");
							// if (Plugin.getExtension(subExtensions,
							// "modelName", modelName) == null) {
							// // 无表单流程
							// logger.debug("无表单待办直接更新实例状态");
							//
							// thirdDingDinstanceService.delInstance(
							// task.getFdInstance()
							// .getFdEkpInstanceId(),
							// task.getFdEkpUser() == null
							// ? null
							// : task.getFdEkpUser()
							// .getFdId());
							//
							// }
							// } else {
							// logger.debug("无表单待办直接更新实例状态");
							//
							// thirdDingDinstanceService.delInstance(
							// task.getFdInstance()
							// .getFdEkpInstanceId(),
							// task.getFdEkpUser() == null
							// ? null
							// : task.getFdEkpUser()
							// .getFdId());
							// }
							//
							// } catch (Exception e) {
							// logger.error("更新实例状态失败！" + e.getMessage(), e);
							// }

						} else {
							task.setFdStatus("21");
							logger.error(
									"更新钉钉待办状态异常，错误信息：" + response.getBody());
						}
						task.setFdDesc(response.getBody());
						thirdDingDtaskService.update(task);
						JSONObject jo = JSONObject
								.fromObject(response.getBody());
						addNotifyLog(todo, jo,
								JSONObject.fromObject(req).toString());
					}
				} else {
					logger.debug("更新待办(待办名称：" + todo.getFdSubject() + ",主键："
							+ todo.getFdId() + ")在钉钉待办任务中找不到对应的数据");
				}
				TransactionUtils.commit(status);
			} catch (Exception e1) {
				if (status != null) {
					try {
						TransactionUtils.getTransactionManager()
								.rollback(status);
					} catch (Exception ex) {
						logger.error("---事务回滚出错---", ex);
					}
				}
			}
			// 老接口历史数据的更新
			updateWorkrecordOld(todo, persons);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新钉钉待办状态异常，错误信息：", e);
		}
		logger.debug("更新钉钉待办耗时：" + (System.currentTimeMillis() - time) + "毫秒");
	}
	
	private void updateWorkrecordOld(SysNotifyTodo todo, List<SysOrgElement> persons) throws ApiException, Exception{
		String where = null;
		if (persons == null || persons.size() == 0) {
			where = "fdModelId='" + todo.getFdId() + "' and fdModelName='" + todo.getFdModelName()	+ "'";
		}else{
			where = "fdModelId='" + todo.getFdId() + "' and fdModelName='" + todo.getFdModelName()	+ "'";
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
				where += " and fdEkpUserId in (" + fdIds + ")";
			}
		}
		List<ThirdDingNotify> notifies = thirdDingNotifyService.findList(where, null);
		JSONObject jo = null;
		if (notifies != null && notifies.size() > 0) {
			OapiWorkrecordUpdateRequest req = null;
			for (ThirdDingNotify notify : notifies) {
				try {
					DingApiService dingService = DingUtils.getDingApiService();
					String url = DingConstant.DING_PREFIX
							+ "/topapi/workrecord/update";
					logger.debug("钉钉接口  url:" + url);
					ThirdDingTalkClient client = new ThirdDingTalkClient(url);
					req = new OapiWorkrecordUpdateRequest();
					req.setUserid(notify.getFdDingUserId());
					req.setRecordId(notify.getFdRecordId());
					OapiWorkrecordUpdateResponse rsp = client.execute(req, dingService.getAccessToken());
					logger.info("钉钉待办删除详细："+rsp.getBody());
					jo = JSONObject.fromObject(rsp.getBody());
					if(jo.containsKey("errcode")&&jo.getInt("errcode")==0){
						thirdDingNotifyService.delete(notify);
					}else{
						notify.setFdEKPDel("1");
						thirdDingNotifyService.update(notify);
						addError(notify, JSONObject.fromObject(notify).toString(),rsp.getBody(), "notifyUpdate");
					}
				} catch (Exception e) {
					notify.setFdEKPDel("1");
					thirdDingNotifyService.update(notify);
					e.printStackTrace();
					logger.error("更新待办发生异常：",e);
				}finally {
					//保存待办发送日志
					if (req != null) {
                        addNotifyLog(todo, jo, JSONObject.fromObject(req).toString());
                    }
				}
			}
		}
	}
	
	private void addNotifyLog(SysNotifyTodo todo, JSONObject jo, String reqData){
		try {
			ThirdDingNotifylog notifylog = new ThirdDingNotifylog();
			notifylog.setDocSubject(todo.getFdSubject());
			notifylog.setFdNotifyData(reqData);
			notifylog.setFdSendTime(new Date());
			notifylog.setFdRtnMsg(jo.toString());
			notifylog.setFdNotifyId(todo.getFdId());
			notifylog.setFdRtnTime(new Date());
			thirdDingNotifylogService.add(notifylog);
		} catch (Exception e) {
			logger.error("添加日志发生异常：",e);
		}
	}
	
	private String getDingUser(String fdId) throws Exception{
		IOmsRelationService omsRelationService= (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock("fdEkpId='"+fdId+"'");
		return (String) omsRelationService.findFirstOne(hqlInfo);
	}
	
	private IEventMulticaster multicaster;

	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;
	}
	
	private IThirdDingErrorService thirdDingErrorService;

	public void setThirdDingErrorService(IThirdDingErrorService thirdDingErrorService) {
		this.thirdDingErrorService = thirdDingErrorService;
	}
	
	private void addError(BaseModel model,String content,String errmsg,String type) throws Exception{
		String fdName = "钉钉消息发送";
		if("notify".equals(type)){
			fdName = "钉钉工作待办发送";
		}else if("notifyUpdate".equals(type)){
			fdName = "删除钉钉工作待办";
		}
		ThirdDingError error = new ThirdDingError();
		error.setFdName(fdName);
		error.setFdContent(content);
		error.setFdCreateTime(new Date());
		error.setFdModelId(model.getFdId());
		error.setFdModelName(ModelUtil.getModelClassName(model));
		error.setFdErrorMsg(errmsg);
		error.setFdServiceName("dingNotifyProvider");
		error.setFdServiceMethod("handleError");
		JSONObject param = new JSONObject();
		param.put("type", type);
		error.setFdMethodParam(param.toString());
		error.setFdCount(0);
		thirdDingErrorService.add(error);
	}

	@Override
	public boolean handleError(ThirdDingError error) throws Exception {
		boolean flag = true;
		JSONObject json = JSONObject.fromObject(error.getFdMethodParam());
		try {
			String type = json.getString("type");
			if("notifyUpdate".equals(type)){
				flag = sendUpdateErrorNotify(error.getFdContent());
			}else if("message".equals(type)){
				flag = sendErrorMesssage(error.getFdContent());
			}else if("notify".equals(type)){
				flag = sendErrorNotify(error.getFdContent());
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error("处理数据异常：", e);
		}
		return flag;
	}
	
	private boolean sendErrorMesssage(String content) throws Exception {
		boolean flag = true;
		if (StringUtil.isNull(content)) {
			logger.debug("通知内容为空，不处理...");
			return flag;
		}
		JSONObject json = JSONObject.fromObject(content);
		// 待办通知已经处理则不进行重复发送
		if (existNotify(json)) {
            return flag;
        }
		CorMode corMode = new CorMode();
		corMode.setCorpid(DingUtil.getCorpId());
		String secret = DingConfig.newInstance().getDingCorpSecret();
		if (StringUtil.isNotNull(secret)) {
			corMode.setCorpsecret(DingConfig.newInstance().getDingCorpSecret());
		} else {
			corMode.setCorpsecret(DingConfig.newInstance().getAppSecret());
		}
		String tokenId = TokenUtils.getToken(corMode);
		if (StringUtils.isNotEmpty(tokenId)) {
			String send_message_url = DingConstant.DING_PREFIX
					+ "/message/send?access_token=ACCESS_TOKEN"
					+ DingUtil.getDingAppKeyByEKPUserId("&", null);
			String url = send_message_url.replace("ACCESS_TOKEN", tokenId);
			logger.debug("钉钉接口：" + url);
			String result = DingHttpClientUtil.httpPost(url, JSONObject.fromObject(content), "errcode", String.class);
			if (!"0".equals(result)) {
				logger.error("消息发送失败...");
				flag = false;
			} else {
				logger.debug("消息发送成功...");
				flag = true;
			}
		}
		return flag;
	}
	
	private boolean sendUpdateErrorNotify(String content) throws Exception {
		boolean flag = true;
		if (StringUtil.isNull(content)) {
			logger.debug("待办通知内容为空，不处理...");
			return flag;
		}
		JSONObject json = JSONObject.fromObject(content);
		// 待办通知已经处理则不进行重复发送
		if (existNotify(json)) {
            return flag;
        }
		ThirdDingNotify notify = (ThirdDingNotify) JSONObject.toBean(JSONObject.fromObject(content),
				ThirdDingNotify.class);
		DingApiService dingService = DingUtils.getDingApiService();
		String url = DingConstant.DING_PREFIX + "/topapi/workrecord/update"
				+ DingUtil.getDingAppKeyByEKPUserId("?",
						notify.getFdEkpUserId());
		logger.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiWorkrecordUpdateRequest req = new OapiWorkrecordUpdateRequest();
		req.setUserid(notify.getFdDingUserId());
		req.setRecordId(notify.getFdRecordId());
		OapiWorkrecordUpdateResponse rsp = client.execute(req, dingService.getAccessToken());
		logger.debug("钉钉待办删除详细：" + rsp.getBody());
		JSONObject jo = JSONObject.fromObject(rsp.getBody());
		if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
			thirdDingNotifyService.delete(notify);
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
	
	private boolean sendErrorNotify(String content) throws Exception {
		boolean flag = true;
		if (StringUtil.isNull(content)) {
			logger.debug("钉钉工作待办内容为空，不处理...");
			return flag;
		}
		JSONObject json = JSONObject.fromObject(content);
		// 待办已经处理则不进行重复发送
		if (existNotify(json)) {
            return flag;
        }
		String userid = json.getString("userid");
		String ekpid = json.getString("ekpid");
		String modelid = json.getString("modelid");
		String modelname = json.getString("modelname");
		String title = json.getString("title");
		String subject = json.getString("subject");
		String url = json.getString("url");
		DingApiService dingService = DingUtils.getDingApiService();
		String ding_url = DingConstant.DING_PREFIX + "/topapi/workrecord/add"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpid);
		logger.debug("钉钉接口：" + ding_url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(ding_url);
		OapiWorkrecordAddRequest req = new OapiWorkrecordAddRequest();
		req.setUserid(userid);
		req.setCreateTime((new Date()).getTime());
		req.setTitle(title);
		req.setUrl(url);
		List<FormItemVo> itemVos = new ArrayList<FormItemVo>();
		FormItemVo vo = new FormItemVo();
		vo.setTitle("标题");
		vo.setContent(subject);
		itemVos.add(vo);
		vo = new FormItemVo();
		vo.setTitle("创建时间");
		vo.setContent(DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		itemVos.add(vo);
		req.setFormItemList(itemVos);
		OapiWorkrecordAddResponse rsp = client.execute(req, dingService.getAccessToken());
		JSONObject jo = JSONObject.fromObject(rsp.getBody());
		if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
			ThirdDingNotify dingNotify = new ThirdDingNotify();
			dingNotify.setFdDingUserId(userid);
			dingNotify.setFdEkpUserId(ekpid);
			dingNotify.setFdModelId(modelid);
			dingNotify.setFdModelName(modelname);
			dingNotify.setFdRecordId(jo.getString("record_id"));
			dingNotify.setDocCreateTime(new Date());
			dingNotify.setFdEKPDel("0");
			thirdDingNotifyService.add(dingNotify);
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
	
	private ISysNotifyTodoService sysNotifyTodoService = null;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}

	private boolean existNotify(JSONObject json) throws Exception {
		boolean flag = true;
		try{
			if(json.containsKey("modelid") && json.containsKey("ekpid")){
				String modelid = json.getString("modelid");
				String ekpid = json.getString("ekpid");
				SysNotifyTodo todo = (SysNotifyTodo) getSysNotifyTodoService().findByPrimaryKey(modelid, null, true);
				if(todo==null) {
                    return true;
                }
				if (todo != null && todo.getHbmTodoTargets() != null && todo.getHbmTodoTargets().size() > 0) {
					List<SysOrgElement> todoTargets = todo.getHbmTodoTargets();
					for (SysOrgElement ele : todoTargets) {
						if (ekpid.equals(ele.getFdId())) {
							flag = false;
							break;
						}
					}
				}
			}else{
				flag = false;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("判断待办是否存在发送异常：",e);
		}
		return flag;
	}

	public Map<String, List<ThirdDingTemplateDetail>> getDetailsMap() {
		return detailsMap;
	}

	public void resetTemplate() throws Exception {
		templatesMap = null;
		detailsMap = null;
		defaultTemplate = null;
		init();
	}
	
}
