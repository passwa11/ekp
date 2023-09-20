package com.landray.kmss.third.ding.service.spring;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.dingtalk.api.response.OapiProcessWorkrecordCreateResponse;
import com.dingtalk.api.response.OapiProcessWorkrecordUpdateResponse;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowDiscard;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingDinstance;
import com.landray.kmss.third.ding.model.ThirdDingDtemplate;
import com.landray.kmss.third.ding.model.ThirdDingInstanceDetail;
import com.landray.kmss.third.ding.model.ThirdDingTemplateDetail;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingDinstanceService;
import com.landray.kmss.third.ding.service.IThirdDingDtaskService;
import com.landray.kmss.third.ding.service.IThirdDingDtemplateService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class ThirdDingDinstanceServiceImp extends ExtendDataServiceImp
		implements IThirdDingDinstanceService, ApplicationListener {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingDinstanceServiceImp.class);

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof ThirdDingDinstance) {
			ThirdDingDinstance thirdDingDinstance = (ThirdDingDinstance) model;
		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		ThirdDingDinstance thirdDingDinstance = new ThirdDingDinstance();
		ThirdDingUtil.initModelFromRequest(thirdDingDinstance, requestContext);
		return thirdDingDinstance;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		ThirdDingDinstance thirdDingDinstance = (ThirdDingDinstance) model;
	}

	@Override
    public List<ThirdDingDinstance> findByFdTemplate(ThirdDingDtemplate fdTemplate) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("thirdDingDinstance.fdTemplate.fdId=:fdId");
		hqlInfo.setParameter("fdId", fdTemplate.getFdId());
		return this.findList(hqlInfo);
	}

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	private IThirdDingDtemplateService thirdDingDtemplateService = null;

	public IThirdDingDtemplateService getThirdDingDtemplateService() {
		if (thirdDingDtemplateService == null) {
			thirdDingDtemplateService = (IThirdDingDtemplateService) SpringBeanUtil
					.getBean("thirdDingDtemplateService");
		}
		return thirdDingDtemplateService;
	}

	private IThirdDingDtaskService thirdDingDtaskService = null;

	public IThirdDingDtaskService getThirdDingDtaskService() {
		if (thirdDingDtaskService == null) {
			thirdDingDtaskService = (IThirdDingDtaskService) SpringBeanUtil.getBean("thirdDingDtaskService");
		}
		return thirdDingDtaskService;
	}

	private IOmsRelationService omsRelationService = null;

	public IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
		}
		return omsRelationService;
	}

	private String getSubject(SysNotifyTodo todo) {
		String subject = todo.getFdSubject();
		if (StringUtil.isNotNull(todo.getFdAppName()) || todo.getFdLink().startsWith("http")) {
			return subject;
		}else{
			try {
				String modelId = todo.getFdModelId();
				String modelName = todo.getFdModelName();
				if (StringUtil.isNotNull(modelId) && StringUtil.isNotNull(modelName)) {
					Object object = getOmsRelationService().findByPrimaryKey(modelId, modelName, true);
					if (object != null) {
						Map map = BeanUtils.describe(object);
						if(map.containsKey("fdName")){
							subject = BeanUtils.getSimpleProperty(object, "fdName");
						}else if(map.containsKey("docSubject")){
							subject = BeanUtils.getSimpleProperty(object, "docSubject");
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
	
	private String getNameByLang(String name, Locale locale) {
		if ("标题".equals(name)) {
			return ResourceUtil.getStringValue(
					"sysNotifyTodo.fdSubject", "sys-notify",
					locale);
		} else if ("创建者".equals(name)) {
			return ResourceUtil.getStringValue(
					"sysNotifyTodo.docCreator", "sys-notify",
					locale);
		} else if ("创建时间".equals(name)) {
			return ResourceUtil.getStringValue(
					"sysNotifyTodo.fdCreateTime", "sys-notify",
					locale);
		}
		return name;
	}

	@Override
	public String addCommonInstance(String duserid, SysNotifyTodo todo,ThirdDingDtemplate template,List<ThirdDingTemplateDetail> details) throws Exception {

		String fdLang = todo.getFdLang();
		if (StringUtil.isNull(fdLang)) {
			fdLang = SysLangUtil.getOfficialLang();
		}
		if (StringUtil.isNull(fdLang)) {
			fdLang = "zh-CN";
		}
		Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
		if (locale == null) {
			locale = ResourceUtil.getLocale("zh-CN");
		}

		String fdId = null;
		String token = DingUtils.getDingApiService().getAccessToken();
		if (StringUtil.isNotNull(duserid)) {
			String subject = getSubject(todo);
			OapiProcessWorkrecordCreateResponse response = DingNotifyUtil.createExample(token, duserid, todo,
					template, subject, details);
			if (response.getErrcode() == 0) {
				String instanceId = response.getResult().getProcessInstanceId();
				ThirdDingDinstance dinstance = new ThirdDingDinstance();
				dinstance.setFdCreator(todo.getDocCreator());
				dinstance.setFdDingUserId(duserid);
				dinstance.setFdEkpInstanceId(todo.getFdModelId());
				dinstance.setFdInstanceId(instanceId);
				dinstance.setFdName(todo.getFdSubject());
				dinstance.setFdTemplate(template);
				dinstance.setDocCreateTime(new Date());
				String domainName = DingConfig.newInstance().getDingDomain();
		    	String viewUrl = null;
				if(StringUtil.isNotNull(todo.getFdId())){
					viewUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
							+ todo.getFdId() + "&oauth=ekp&dingOut=true";
					if (StringUtils.isNotEmpty(domainName)) {
						viewUrl = domainName + viewUrl;
					} else {
						viewUrl = StringUtil.formatUrl(viewUrl);
					}
				}else{
					if(StringUtil.isNull(domainName)) {
                        domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
                    }
					if(domainName.endsWith("/")) {
                        domainName = domainName.substring(0, domainName.length()-1);
                    }
					viewUrl = StringUtil.formatUrl(todo.getFdLink(), domainName);		
					if(viewUrl.indexOf("?")==-1){
						viewUrl = viewUrl + "?oauth=ekp&dingOut=true";
					}else{
						viewUrl = viewUrl + "&oauth=ekp&dingOut=true";
					}
				}
				dinstance.setFdUrl(viewUrl);
				List<ThirdDingInstanceDetail> didetails = new ArrayList<ThirdDingInstanceDetail>();
				ThirdDingInstanceDetail didetail = null;
				for (ThirdDingTemplateDetail detail : details) {
					didetail = new ThirdDingInstanceDetail();
					didetail.setFdName(
							getNameByLang(detail.getFdName(), locale));
					didetail.setFdType(detail.getFdType());
					if ("标题".equals(detail.getFdName())) {
						String title = todo.getFdSubject();
						switch (todo.getFdLevel()) {
						case 1:
							title = ResourceUtil.getString(
									"enums.notify.level.1", "third-ding",
									locale) + title;
							break;
						case 2:
							title = ResourceUtil.getString(
									"enums.notify.level.2", "third-ding",
									locale) + title;
							break;
						default:
							title = ResourceUtil.getString(
									"enums.notify.level.3", "third-ding",
									locale) + title;
							break;
						}
						didetail.setFdValue(title);
					} else if ("创建者".equals(detail.getFdName())) {
						didetail.setFdValue(todo.getDocCreatorName());
					} else if ("创建时间".equals(detail.getFdName())) {
						if (todo.getFdCreateTime() == null) {
							didetail.setFdValue("");
						} else {
							didetail.setFdValue(
									DateUtil.convertDateToString(todo.getFdCreateTime(), "yyyy-MM-dd HH:mm"));
						}
					}
					didetails.add(didetail);
				}
				dinstance.setFdDetail(didetails);
				fdId = super.add(dinstance);
			} else {
				logger.error("创建通用待办模板的实例异常，详细错误：" + response.getBody());
			}
		} else {
			logger.debug("通过待办无法找到文档创建人信息");
		}
		return fdId;
	}

	@Override
	public ThirdDingDinstance findCommonInstance(SysNotifyTodo todo,
			String templateId) throws Exception {
		ThirdDingDinstance dinstance = null;
		List<ThirdDingDinstance> dinstances = findList("fdEkpInstanceId='" + todo.getFdModelId() + "'", null);
		if (dinstances != null && dinstances.size() > 0) {
			for (ThirdDingDinstance thirdDingDinstance : dinstances) {
				if (thirdDingDinstance.getFdTemplate().getFdId()
						.equals(templateId)) {
					return thirdDingDinstance;
				}
			}
			// dinstance = dinstances.get(0);
		}
		return dinstance;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
            return;
        }
		Object obj = event.getSource();
		if (event instanceof Event_SysFlowFinish || event instanceof Event_SysFlowDiscard) {
			try {
				String fdId = ((BaseModel) obj).getFdId();
				delInstance(fdId, null);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("", e);
			}
		}
	}

	@Override
    public void delInstance(String fdId, String ekpUserId) throws Exception {
		List<ThirdDingDinstance> dlist = findList("fdEkpInstanceId='"+fdId+"'", null);
		if (dlist != null && dlist.size()>0) {
			String token = DingUtils.getDingApiService().getAccessToken();
			for(ThirdDingDinstance dinstance:dlist){
				String modelName = null;
				if (StringUtil.isNull(ekpUserId)) {
					SysOrgElement person = dinstance.getFdCreator();
					if (person != null) {
						ekpUserId = getOmsRelationService()
								.getDingUserIdByEkpUserId(person.getFdId());
					}
				}
				if (StringUtil.isNotNull(dinstance.getFdInstanceId())) {
					// 主文档状态
					boolean isAgree = true;
					try {
						String todoUrl = dinstance.getFdUrl();

						if (StringUtil.isNotNull(todoUrl) && todoUrl.contains(
								"sys/notify/sys_notify_todo/sysNotifyTodo.do")) {
							String todoId = StringUtil.getParameter(todoUrl,
									"fdId");
							logger.debug("todoId:" + todoId);
							if (StringUtil.isNotNull(todoId)) {
								SysNotifyTodo todo = (SysNotifyTodo) ((ISysNotifyTodoService) SpringBeanUtil
										.getBean("sysNotifyTodoService"))
												.findByPrimaryKey(todoId);
								modelName = todo.getFdModelName();
								logger.debug("modelName:" + modelName);
								// #129786考虑会议管理中会议审批结束后，发送会议邀请，会议纪要推送到钉钉失败的问题，会议实例暂时不更新处理
								if ("com.landray.kmss.km.imeeting.model.KmImeetingMain"
										.equals(modelName)) {
									logger.warn(
											"----考虑会议管理中会议审批结束后，发送会议邀请，会议纪要推送到钉钉失败的问题，会议实例暂时不更新处理----");
									break;
								}
								if (StringUtil.isNotNull(modelName)) {
									SysDictModel sysDict = SysDataDict
											.getInstance().getModel(modelName);
									String beanName = sysDict.getServiceBean();
									logger.debug(
											"-----------beanName:" + beanName);
									IBaseService mainService = (IBaseService) SpringBeanUtil
											.getBean(beanName);
									Object main = mainService.findByPrimaryKey(
											todo.getFdModelId());
									if (main != null
											&& main instanceof IBaseModel) {
											Method method = main.getClass()
													.getMethod("getDocStatus");
											String docStatus = (String) method
													.invoke(main);
											logger.debug("------docStatus:"
													+ docStatus);
											if (SysDocConstant.DOC_STATUS_DISCARD
													.equals(docStatus)) {
												logger.warn(
														"-----------废弃--------------");
												isAgree = false;
											}

									}

								}
							}
						}

					} catch (Exception e) {
						logger.warn(
								"获取主文档状态失败,可能原因：主文档被删除或者主文档模块不含有getDocStatus方法（docStatus）,默认钉钉待办状态为 【已通过】");
					}

					// 车辆管理在流程结束后会发一条待办出来,这时候如果先删除实例会导致这条待办推送失败
					if ("com.landray.kmss.km.carmng.model.KmCarmngApplication"
							.equals(modelName)) {
						// ThirdDingDinstanceThread thirdDingDinstanceThread =
						// new ThirdDingDinstanceThread(
						// dinstance.getFdId(), isAgree);
						// Thread t = new Thread(thirdDingDinstanceThread);
						// t.start();
						return;
					}

					OapiProcessWorkrecordUpdateResponse response = DingNotifyUtil.updateInstanceState(token,
									dinstance.getFdInstanceId(),
									dinstance.getFdTemplate().getFdAgentId(),
									ekpUserId, isAgree);
					if (response.getErrcode() == 0) {
						logger.debug("更新实例成功！");
					} else {
						logger.error("更新所有的钉钉流程实例待办异常，详细错误：" + response.getBody());
					}
				}
			}
		}
	}
}
