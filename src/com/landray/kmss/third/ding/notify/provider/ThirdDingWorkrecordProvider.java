package com.landray.kmss.third.ding.notify.provider;

import com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest;
import com.dingtalk.api.request.OapiWorkrecordAddRequest;
import com.dingtalk.api.request.OapiWorkrecordAddRequest.FormItemVo;
import com.dingtalk.api.request.OapiWorkrecordUpdateRequest;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskUpdateResponse;
import com.dingtalk.api.response.OapiWorkrecordAddResponse;
import com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse;
import com.dingtalk.api.response.OapiWorkrecordUpdateResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProviderExtend;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.*;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyLog;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyWorkrecord;
import com.landray.kmss.third.ding.notify.queue.constant.ThirdDingNotifyQueueErrorConstants;
import com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError;
import com.landray.kmss.third.ding.notify.queue.service.IThirdDingNotifyQueueErrorService;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyLogService;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyWorkrecordService;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.provider.DingXformNotifyProvider;
import com.landray.kmss.third.ding.service.*;
import com.landray.kmss.third.ding.util.ObjectUtil;
import com.landray.kmss.third.ding.util.*;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.enums.ValueLabel;
import com.taobao.api.ApiException;
import com.taobao.api.BaseTaobaoRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.orm.hibernate5.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;
import java.util.*;

public class ThirdDingWorkrecordProvider extends BaseSysNotifyProviderExtend implements
		DingConstant {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingWorkrecordProvider.class);
	
	private ISysOrgPersonService sysOrgPersonService;

	private IThirdDingNotifyLogService thirdDingNotifyLogService;

	private IThirdDingNotifyQueueErrorService thirdDingNotifyQueueErrorService;

	private IThirdDingNotifyWorkrecordService thirdDingNotifyWorkrecordService;

	public IThirdDingNotifyWorkrecordService
			getThirdDingNotifyWorkrecordService() {
		return thirdDingNotifyWorkrecordService;
	}

	public void setThirdDingNotifyWorkrecordService(
			IThirdDingNotifyWorkrecordService thirdDingNotifyWorkrecordService) {
		this.thirdDingNotifyWorkrecordService = thirdDingNotifyWorkrecordService;
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}
	

	public void setSysNotifyTodoService(ISysNotifyTodoService sysNotifyTodoService) {
		this.sysNotifyTodoService = sysNotifyTodoService;
	}

	private boolean checkNeedNotify(SysNotifyTodo todo, NotifyContext context) {
		if (!DingUtil.checkNotifyApiType("WR")) {
			return false;
		}
		if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
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
			if (appType.contains("all") || appType.contains("ding")) {
				notifyflag = true;
				logger.debug("已经设置参数fdAppType=" + appType + ",故强制推送消息...");
			}
		}else if(StringUtil.isNull(appType)){
			if (todo.getFdType() == 1 || todo.getFdType() == 3) {
				if ("true".equals(DingConfig.newInstance()
						.getDingWorkRecordEnabled())) {
					notifyflag = true;
				}
			}
		}
		if(!notifyflag){
			logger.debug("未开启待办消息推送,参数fdAppType=" + appType + ",待办Id="
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

		NotifyContextImp ctx = (NotifyContextImp) context;
		List notifyTargetList = ((NotifyContextImp) context).getNotifyPersons();
		if (notifyTargetList == null || notifyTargetList.isEmpty()) {
			logger.debug("通知人员为空不执行消息发送，通知标题为："+todo.getFdSubject()+"("+todo.getFdId()+")");
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
			
			Map<String, String> dingIdMap = DingUtil.getDingIdMap(
					ekpIds);
			// Set<String> dingIds = ThirdDingUtil.getDingIdSet(org, ekpIds);
			if (dingIdMap == null || dingIdMap.size() == 0) {
				logger.error("通过EKP的fdId查找人员映射表发现找不到对应的钉钉人员(" + ekpIds
						+ ")，请先检查组织同步是否正常");
				return;
			}
			// List<String> dingIdList = new ArrayList<String>(dingIds);
			// DingOfficeMessage dingOfficeMessage = createNotifyToDoDTO(todo);

			if (dingIdMap != null && dingIdMap.size() > 0) {
				// 工作待办推送
				if ("true".equals(
						DingConfig.newInstance().getDingWorkRecordEnabled())
						&& (todo.getFdType() == 1 || todo.getFdType() == 3)) {
					if (dingIdMap.size() > 0) {
						String wrurl = DingUtil.getViewUrl(todo);
						for (String dingId : dingIdMap.keySet()) {
							Long endTime = context.getFdEndTime();
							logger.debug("endTime:" + endTime);
							if (endTime != null && endTime != 0) {
								if (System.currentTimeMillis() > endTime) {
									logger.warn("当前时间已经超过了消息的截止时间，不再推送消息...");
									logger.warn("endTime:" + endTime);
									return;
								}
							}
							addWorkrecord(dingId, dingIdMap.get(dingId),
									todo, wrurl);
							}
					}
				}
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
		if (DingUtil.hasInstanceInXform(todo)) {
			logger.warn("-------开启钉钉审批高级版/套件，接口走钉钉待办旧接口更新-------");
			DingXformNotifyProvider.newInstance().clearTodoPersons(todo);
		}
		updateWorkrecord(todo, null);
	}

	@Override
	public void remove(SysNotifyTodo todo) throws Exception {
		logger.debug("remove ：" + todo.getFdId() + "," + todo.getFdSubject());
		if (!checkNeedNotify(todo, null)) {
			return;
		}
		if (DingUtil.hasInstanceInXform(todo)) {
			logger.warn("-------开启钉钉审批高级版/套件，接口走钉钉待办旧接口更新    remove-------");
			DingXformNotifyProvider.newInstance().remove(todo);
		}
		Thread.sleep(1000);
		updateWorkrecord(todo, null);
	}
	
	@Override
	public void setTodoDone(SysNotifyTodo todo) throws Exception {
		logger.debug(
				"setTodoDone ：" + todo.getFdId() + "," + todo.getFdSubject());
		if (!checkNeedNotify(todo, null)) {
			return;
		}
		if (DingUtil.hasInstanceInXform(todo)) {
			DingXformNotifyProvider.newInstance().setTodoDone(todo);
			logger.warn("-------开启钉钉审批高级版/套件，接口走钉钉待办旧接口更新-------");
		}
		updateWorkrecord(todo, null);
	}

	//场景二：ekp把待办中部分人员设置为已办
	@Override
	public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
		logger.debug("setTodoDone ：" + todo.getFdId() + ","
				+ todo.getFdSubject() + "," + person.getFdLoginName());
		if (!checkNeedNotify(todo, null)) {
			return;
		}
		if (DingUtil.hasInstanceInXform(todo)) {
			logger.warn("-------开启钉钉审批高级版/套件，接口走钉钉待办旧接口更新-------");
			DingXformNotifyProvider.newInstance().removeDonePerson(todo,person);
		}
		List list = new ArrayList();
		list.add(person);
		List<SysOrgElement> persons = new ArrayList<SysOrgElement>();
		persons.add(person);
		updateWorkrecord(todo, persons);
	}

	@Override
	public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
		logger.info("setPersonsDone ：" + todo.getFdId() + ","
				+ todo.getFdSubject());
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
		if (DingUtil.hasInstanceInXform(todo)) {
			logger.warn("-------开启钉钉审批高级版/套件，接口走钉钉待办旧接口更新-------");
			DingXformNotifyProvider.newInstance().setPersonsDone(todo,
					persons);
		}
		updateWorkrecord(todo, (List<SysOrgElement>) persons);
	}
	
	@Override
	public void updateTodo(SysNotifyTodo todo) throws Exception {
		logger.debug("updateTodo ：" + todo.getFdId() + ","
				+ todo.getFdSubject());
	}

	public void updateTodo3(SysNotifyTodo todo) throws Exception {
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

		Map<String, String> dingIdMap = DingUtil.getDingIdMap(
				ekpIds);
		if (dingIdMap == null || dingIdMap.size() == 0) {
			logger.error("通过EKP的fdId查找人员映射表发现找不到对应的钉钉人员(" + ekpIds
					+ ")，请先检查组织同步是否正常");
			return;
		}
		if (dingIdMap != null && dingIdMap.size() > 0) {
			// 工作待办推送
			if ("true".equals(
					DingConfig.newInstance().getDingWorkRecordEnabled())
					&& (todo.getFdType() == 1 || todo.getFdType() == 3)) {
				if (dingIdMap.size() > 0) {
					String wrurl = DingUtil.getViewUrl(todo);
					for (String dingId : dingIdMap.keySet()) {
						addWorkrecord(dingId, dingIdMap.get(dingId),
								todo, wrurl);
					}
				}
			}
		}
	}

	private String getSubject(SysNotifyTodo todo) {
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
					Object object = thirdDingNotifylogService
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
						if(StringUtil.isNull(subject)){
							//避免模块的字段值为空，督办模块fdName为空
							subject = todo.getFdSubject();
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return subject;
	}
	
	private String addWorkrecord(String dingUserId,
			String ekpUserId,
			SysNotifyTodo todo, String url)
	{
		ThirdDingWorkrecordAddRequest req = new ThirdDingWorkrecordAddRequest();
		req.setUserid(dingUserId);
		req.setCreateTime((new Date()).getTime());
		// SysDictModel sysDictModel =
		// SysDataDict.getInstance().getModel(todo.getFdModelName());
		// String title = null;
		// if (null == sysDictModel) {
		// title = todo.getFdModelName();
		// } else {
		// title = ResourceUtil.getString(sysDictModel.getMessageKey());
		// }
		String title = getSubject(todo);
		Locale local = null;
		if (StringUtil.isNotNull(todo.getFdLang())
				&& todo.getFdLang().contains("-")) {
			local = new Locale(todo.getFdLang().split("-")[0],
					todo.getFdLang().split("-")[1]);
		}
		switch (todo.getFdLevel()) {
		case 1:
			title = "【"
					+ ResourceUtil.getStringValue(
							"sysNotifyTodo.level.taglib.1", "sys-notify", local)
					+ "】" + title;
			break;
		case 2:
			title = "【"
					+ ResourceUtil.getStringValue(
							"sysNotifyTodo.level.taglib.2", "sys-notify", local)
					+ "】" + title;
			break;
		}
		if (todo.getFdType() == 3) {
			title = "【"
					+ ResourceUtil.getStringValue(
							"sysNotifyTodo.type.suspend", "sys-notify", local)
					+ "】" + title;
		}
		logger.debug("待办的主题：" + title + ",用户：" + dingUserId);
		if (StringUtil.isNotNull(title)
				&& title.length() >= 50) {
			logger.debug("待办主题长度超过了50");
			req.setTitle(title.substring(0, 45) + "...");
		} else {
			req.setTitle(title);
		}
		// f4的dingAppKey参数
		String addAppKey = ResourceUtil
				.getKmssConfigString("kmss.ding.addAppKey");
		if (StringUtil.isNotNull(addAppKey)
				&& "true".equalsIgnoreCase(addAppKey)
				&& !url.contains("dingAppKey")) {
			url = url + DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		}
		logger.debug("新待办接口url：" + url);
		req.setUrl(url);

		// 自定义待办内容
		List<FormItemVo> itemVos = getItemVos(todo);
		for (FormItemVo fv : itemVos) {
			logger.debug("【钉钉待办】" + fv.getTitle() + " " + fv.getContent());
		}
		req.setFormItemList(itemVos);
		req.setBizId(todo.getFdId());
		String pcurl = DingUtil.getPcViewUrl(todo);
		req.setPcUrl(pcurl);

		//2：在PC端打开 4：在浏览器打开
		//统一使用PC端方式打开，浏览器打开会有两个问题：1.直接跳到外部浏览器，没有单点  2.url可加上ddtab=true参数，会在工作台打开，但是无法自动关闭（切换成外部打开方式时）
		req.setPcOpenType(2L);
//		String dingTodoPcOpenType = DingConfig.newInstance().getDingTodoPcOpenType();
//		if ("in".equals(dingTodoPcOpenType)) {
//			req.setPcOpenType(4L);
//		} else {
//			req.setPcOpenType(2L);
//		}
		req.setSourceName("EKP");

		return callDingdingAdd(
				DingConstant.DING_PREFIX + "/topapi/workrecord/add",
				todo,
				req, ekpUserId);

	}

	private List<FormItemVo> getItemVos(SysNotifyTodo todo) {

		String fdModelName = todo.getFdModelName();
		String fdModelId = todo.getFdModelId();
		logger.debug("fdModelName:" + fdModelName + " fdModelId:" + fdModelId);
		List<FormItemVo> itemVos = new ArrayList<FormItemVo>();
		if (StringUtil.isNull(fdModelName) || StringUtil.isNull(fdModelId)) {
			return getDefaultItemVos(todo);
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
			ThirdDingTodoTemplate thirdDingTodoTemplate = getTemplateModel(
					list);
			if (thirdDingTodoTemplate != null) {
				// 使用自定义待办模版
				logger.debug("使用自定义待办模版");
				// ThirdDingTodoTemplate thirdDingTodoTemplate = list.get(0);

				if ("com.landray.kmss.km.review.model.KmReviewMain"
						.equals(fdModelName)) {
					// 流程管理有自定义表单，特殊处理
					logger.debug("流程表单待办处理");
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
					logger.warn("---------------------templateId:"
							+ templateId.toString());

					String todoTemplateId = templateId.toString();
					ThirdDingTodoTemplate thirdDingTodoTemplateTemp = null;
					ThirdDingTodoTemplate kmReviewDefault = null;
					for (ThirdDingTodoTemplate template : list) {
						if (todoTemplateId.equals(template.getFdTemplateId())
								&& (StringUtil.isNull(template.getFdType())
										|| template.getFdType()
												.contains("1"))) {
							logger.debug("匹配上了流程模版:" + template.getFdName());
							thirdDingTodoTemplateTemp = template;
							break;
						}
						if (StringUtil.isNull(template.getFdTemplateId())
								&& (StringUtil.isNull(template.getFdType())
										|| template.getFdType()
												.contains("1"))) {
							logger.debug("流程模版默认待办:" + template.getFdName());
							kmReviewDefault = template;
						}
					}
					if (thirdDingTodoTemplateTemp != null) {
						// 表单模版
						try {
							thirdDingTodoTemplate = thirdDingTodoTemplateTemp;

						} catch (Exception e) {
							logger.error("表单模版创建待办出错：" + e);
							return getDefaultModelItemVos(todo);
						}

					} else if (kmReviewDefault != null) {
						// 流程模版
						try {
							thirdDingTodoTemplate = kmReviewDefault;

						} catch (Exception e) {
							logger.error(" 流程模版创建待办出错：" + e);
							return getDefaultModelItemVos(todo);
						}

					} else {
						// 默认模版
						return getDefaultModelItemVos(todo);
					}

				}
				// else {
					SysDictModel newsModel = SysDataDict.getInstance()
							.getModel(fdModelName);
					String beanName = newsModel.getServiceBean();
					// String beanName = "sysTaskMainService";
					
					logger.debug("-----------beanName:" + beanName);
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
						logger.debug("fdDetail:" + fdDetail);
						JSONObject fdDetailJSON = JSONObject
								.fromObject(fdDetail);
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
							// name = ja.getJSONObject(i).getString("name");
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
							singleKey.add(
									ja.getJSONObject(i).getString("key"));
							nameList.add(name);
							if (!"true".equals(fromForm)) {
								singleFormItemVo = getModelItemVos(todo,
										singleKey, nameList, false);
							} else {
								// 表单字段 单个处理
								singleFormItemVo = getFormItemVos(todo,
										thirdDingTodoTemplate, key, name);
							}
							if (null != singleFormItemVo) {
								itemVos.addAll(singleFormItemVo);
							}

						}

					} else {
						List nameList = new ArrayList();
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
							logger.debug(
									"key:" + key + " fromForm:" + fromForm
											+ " name:" + name);
							keyList.add(ja.getJSONObject(i).getString("key"));
							nameList.add(name);

						}

						itemVos = getModelItemVos(todo, keyList, nameList,
								false);
						}
						

					} else {
						logger.warn("获取模块信息失败,将以默认待办模版推送！" + fdModelName + " "
								+ fdModelId);
						itemVos = getDefaultModelItemVos(todo);
					}

				// }

			} else {
				logger.debug("使用默认待办模版！");
				itemVos = getDefaultModelItemVos(todo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return getDefaultItemVos(todo);
		}

		return itemVos;
	}

	/*
	 * 根据推送方式返回待办模板
	 */
	private ThirdDingTodoTemplate
			getTemplateModel(List<ThirdDingTodoTemplate> list) {
		if (list == null || list.isEmpty()) {
            return null;
        }

		for (ThirdDingTodoTemplate temp : list) {
			if (StringUtil.isNull(temp.getFdType())
					|| temp.getFdType().contains("1")) {
				return temp;
			}
		}
		return null;
	}

	// 获取表单字段数据
	private List<FormItemVo> getFormItemVos(SysNotifyTodo todo,
			ThirdDingTodoTemplate thirdDingTodoTemplate, String key,
			String name) {
		List<FormItemVo> list = new ArrayList<FormItemVo>();

		logger.debug("表单key:" + key + " title:" + name);

		FormItemVo formItemVo = new FormItemVo();
		formItemVo.setTitle(name);

		String content = ObjectUtil.getFormValue(todo, thirdDingTodoTemplate,
				key);
		logger.debug("表单 Content:" + content);
		formItemVo.setContent(content);
		if (StringUtil.isNotNull(content)) {
			list.add(formItemVo);
		}
		return list;
	}

	/**
	 * 模块待办模版
	 * 
	 * @param todo
	 * @param keyList
	 * @param nameList
	 * @param isDefalut
	 *            是否是默认模板
	 * @return
	 */
	private static List<FormItemVo> getModelItemVos(SysNotifyTodo todo,
			List<String> keyList, List<String> nameList, boolean isDefalut) {
		// 根据待办模版配置的默认模版进行推送
		List<FormItemVo> itemVos = new ArrayList<FormItemVo>();
		FormItemVo vo = new FormItemVo();
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
					return getDefaultModelItemVos(todo);
				}
			}

			Map<String, SysDictCommonProperty> map = model
					.getPropertyMap();

			for (int i = 0; i < keyList.size(); i++) {
				String key = keyList.get(i);
				logger.debug(
						"key:" + key + " title:" + nameList.get(i));
				vo = new FormItemVo();
				vo.setTitle(nameList.get(i).toString());
				String enumType = null;

				try {
					// 枚举类型的判断 判断key的最后一个字段 docCreator.fdName
					if (key.contains(".")) {
						logger.debug("key为对象类型：" + key);
						String[] keyArray = key.split("\\.");
						if (keyArray != null && keyArray.length > 0) {
							SysDictCommonProperty name = map.get(keyArray[0]);
							String type = name.getType();
							logger.debug("type：" + type);
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
						logger.debug("key为简单类型：" + keyList.get(i));
						SysDictCommonProperty name = map.get(keyList.get(i));
						enumType = name.getEnumType();
					}

				} catch (Exception e) {
					logger.error(
							"判断字段" + keyList.get(i) + "是否为枚举类型过程中发生异常！" + e);

				}

				String content = ObjectUtil.getValue2(main,
						keyList.get(i).toString());

				if (StringUtil.isNull(content)) {
					logger.warn("内容为空(key:" + keyList.get(i) + ")");
					// content = " ";
					continue;
				}
				logger.debug("enumType:" + enumType);
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
							logger.debug("枚举转换：" + content + "("
									+ enumType + ")");
						}
					}
				}
				// 默认待办模块，值特殊处理
				if (isDefalut) {
					if ("fdModelName".equals(keyList.get(i))
							&& content.startsWith("com.landray.kmss.")) {
						logger.debug("将modelName转为模块名称：" + content);
						SysDictModel m = SysDataDict.getInstance()
								.getModel(content);
						String model_name = m.getMessageKey();
						if (StringUtil.isNotNull(model_name)
								&& model_name.contains(":")) {
							content = DingUtil.getValueByLang(
									model_name.split(":")[1],
									model_name.split(":")[0], todo.getFdLang());
							logger.debug("转化后的模块名称为：" + content);
						}

					} else if ("fdLevel".equals(keyList.get(i))
							&& StringUtil.isNotNull(content)) {
						// 优先级转换 待办优先级按紧急、急、一般,紧急:1, 急:2,一般:3 fdLevel
						logger.debug("优先级转换：" + content);
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

				logger.debug("content:" + content);
				vo.setContent(content);
				itemVos.add(vo);


				}

				return itemVos;

		} catch (Exception e) {
			logger.error("创建模块待办发生异常！,将以默认待办发送");
			logger.error(e.getMessage(), e);
			return getDefaultModelItemVos(todo);
		}
		
	}

	private static List<FormItemVo> getDefaultItemVos(SysNotifyTodo todo) {
		List<FormItemVo> itemVos = new ArrayList<FormItemVo>();
		FormItemVo vo = new FormItemVo();
		vo.setTitle(DingUtil.getValueByLang("third.ding.notify.title",
				"third-ding-notify", todo.getFdLang()));
		String content = todo.getFdSubject();
		switch (todo.getFdLevel()) {
		case 1:
			content = "【"
					+ DingUtil.getValueByLang("sysNotifyTodo.level.taglib.1",
							"sys-notify", todo.getFdLang())
					+ "】" + content;
			break;
		case 2:
			content = "【"
					+ DingUtil.getValueByLang("sysNotifyTodo.level.taglib.2",
							"sys-notify", todo.getFdLang())
					+ "】" + content;
			break;
		}
		vo.setContent(content);
		itemVos.add(vo);
		vo = new FormItemVo();
		vo.setTitle(DingUtil.getValueByLang("third.ding.notify.createtime",
				"third-ding-notify", todo.getFdLang()));
		vo.setContent(DateUtil.convertDateToString(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		itemVos.add(vo);
		return itemVos;
	}

	private static List<FormItemVo> getDefaultModelItemVos(SysNotifyTodo todo) {

		// 根据待办模版配置的默认模版进行推送
		List<FormItemVo> itemVos = new ArrayList<FormItemVo>();
		FormItemVo vo = new FormItemVo();
		try {
			// 获取待办模版
			IThirdDingTodoTemplateService thirdDingTodoTemplateService = (IThirdDingTodoTemplateService) SpringBeanUtil
					.getBean("thirdDingTodoTemplateService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"thirdDingTodoTemplate.fdIsdefault=:fdIsdefault");
			hqlInfo.setParameter("fdIsdefault", "1");
			List<ThirdDingTodoTemplate> list = thirdDingTodoTemplateService
					.findList(hqlInfo);
			if (list.size() > 0) {
				// {"data":[{"key":"fdSubject","name":"主题"},{"key":"docCreateTime","name":"创建时间"},{"key":"docCreatorId","name":"创建人"}]}
				ThirdDingTodoTemplate thirdDingTodoTemplate = list.get(0);
				String fdDetail = thirdDingTodoTemplate.getFdDetail();
				logger.debug("fdDetail:" + fdDetail);
				JSONObject fdDetailJSON = JSONObject.fromObject(fdDetail);
				JSONArray ja = fdDetailJSON.getJSONArray("data");
				String key;
				String fromForm;
				String name;
				JSONArray titleJA = new JSONArray();
				for (int i = 0; i < ja.size(); i++) {
					name = "";
					vo = new FormItemVo();
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

					logger.debug("key:" + key + " fromForm:" + fromForm
							+ " name:" + name);

					if ("fdSubject".equals(key)) {
						// vo.setTitle(DingUtil.getValueByLang(
						// "third.ding.notify.title",
						// "third-ding-notify", todo.getFdLang()));

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
						vo.setTitle(name);
						vo.setContent(content);
					} else if ("docCreateTime".equals(key)
							|| "fdCreateTime".equals(key)) {
						// vo.setTitle(DingUtil.getValueByLang(
						// "third.ding.notify.createtime",
						// "third-ding-notify", todo.getFdLang()));
						logger.debug("todo.getFdCreateTime: "
								+ todo.getFdCreateTime());
						vo.setTitle(name);
						vo.setContent(DateUtil.convertDateToString(
								todo.getFdCreateTime(),
								"yyyy-MM-dd HH:mm:ss"));
					} else {
						// vo.setTitle(DingUtil.getValueByLang(
						// "third.ding.notify.creator",
						// "third-ding-notify", todo.getFdLang()));
						List<String> keyList = new ArrayList<String>();
						keyList.add(key);
						List<String> nameList = new ArrayList<String>();
						nameList.add(name);
						// 创建人 属于对象类型
						List<FormItemVo> rsList = getModelItemVos(todo, keyList,
								nameList, true);
						if (rsList != null && rsList.size() > 0) {
							vo = rsList.get(0);
						} else {
							logger.error("name:" + name + "    key:" + key
									+ "  的值为空，该行不推送");
							continue;
						}

					}
					itemVos.add(vo);

				}

			} else {
				logger.warn("没有设置默认待办模版，使用系统默认待办！");
				return getDefaultItemVos(todo);
			}

		} catch (Exception e) {
			logger.error("创建默认待办发生异常！");
			logger.error(e.getMessage(), e);
			return getDefaultItemVos(todo);
		}
		return itemVos;
	}

	private JSONObject buildAddRequestJson(ThirdDingWorkrecordAddRequest req,
			String ekpUserId, SysNotifyTodo todo) {
		JSONObject data = new JSONObject();
		data.put("ekpUserId", ekpUserId);
		data.put("todoId", todo.getFdId());
		data.put("subject", todo.getFdSubject());
		data.put("userId", req.getUserid());
		data.put("createTime", req.getCreateTime());
		data.put("title", req.getTitle());
		data.put("url", req.getUrl());
		data.put("pcUrl", req.getPcUrl());
		String itemsStr = req.getFormItemList();
		JSONArray items = JSONArray.fromObject(itemsStr);
		data.put("itemList", items);
		data.put("bizId", req.getBizId());
		return data;
	}

	private JSONObject buildUpdateRequestJson(OapiWorkrecordUpdateRequest req,
			String ekpUserId, SysNotifyTodo todo) {
		JSONObject data = new JSONObject();
		data.put("ekpUserId", ekpUserId);
		data.put("todoId", todo.getFdId());
		data.put("subject", todo.getFdSubject());
		data.put("userId", req.getUserid());
		data.put("recordId", req.getRecordId());
		return data;
	}

	private OapiWorkrecordUpdateRequest buildUpdateRequest(JSONObject o) {
		OapiWorkrecordUpdateRequest req = new OapiWorkrecordUpdateRequest();
		req.setUserid(o.getString("userId"));
		req.setRecordId(o.getString("recordId"));
		return req;
	}

	private ThirdDingWorkrecordAddRequest buildAddRequest(JSONObject o) {
		ThirdDingWorkrecordAddRequest req = new ThirdDingWorkrecordAddRequest();
		req.setUserid(o.getString("userId"));
		req.setUrl(o.getString("url"));
		req.setPcUrl(o.getString("pcUrl"));
		req.setCreateTime(o.getLong("createTime"));
		String subject = o.getString("title");
		if (subject != null && subject.length() >= 50) {
			subject = subject.substring(0, 45) + "...";
		}
		req.setTitle(subject);
		JSONArray array = o.getJSONArray("itemList");
		List<FormItemVo> itemVos = new ArrayList<FormItemVo>();
		for(int i=0;i<array.size();i++){
			JSONObject obj = array.getJSONObject(i);
			String title = obj.getString("title");
			String content = obj.getString("content");
			FormItemVo vo = new FormItemVo();
			vo.setTitle(title);
			vo.setContent(content);
			itemVos.add(vo);
		}
		req.setFormItemList(itemVos);
		req.setBizId(o.getString("bizId"));
		
		return req;
	}
	
	private String callDingdingAdd(String url, SysNotifyTodo todo,
			OapiWorkrecordAddRequest req,
			String ekpUserId) {
		String rs = null;
		String dingUrl = url
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		logger.debug("调钉钉接口：" + dingUrl);
		DingApiService dingService = DingUtils.getDingApiService();
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdUrl(client.getRequestUrl());
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setFdSendTime(start);
		log.setFdNotifyData(req.toString());
		logger.debug("*******************req.toString():" + req.toString());
		OapiWorkrecordAddResponse rsp;
		try {
			rsp = client.execute(req,
					dingService.getAccessToken());
			rs = rsp.getBody();
			log.setFdRtnMsg(rsp.getBody());
			JSONObject jo = JSONObject.fromObject(rsp.getBody());
			String requestId = null;
			if (jo.containsKey("request_id")) {
				requestId = jo.getString("request_id");
			}
			log.setFdRequestId(requestId);
			if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
				log.setFdResult(true);
				logger.info("待办发送到钉钉详细：" + jo.toString());
				String recordId = jo.getString("record_id");
				addWorkrecord(todo.getFdId(), todo.getFdSubject(),
						(OapiWorkrecordAddRequest) req, ekpUserId,
						recordId);
			} else {
				log.setFdResult(false);
				logger.warn("待办发送到钉钉创建失败。详细：" + jo.toString());
				addErrorQueue(todo, req, ekpUserId, "add", jo.toString());
			}
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			log.setFdResult(false);
			log.setFdRtnMsg(e.getMessage());
			addErrorQueue(todo, req, ekpUserId, "add", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			log.setFdResult(false);
			log.setFdRtnMsg(e.getMessage());
			addErrorQueue(todo, req, ekpUserId, "add", e.getMessage());
		} finally {
			Date end = new Date();
			log.setFdRtnTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			TransactionStatus addStatus = null;
			try {
				addStatus = TransactionUtils
						.beginNewTransaction();
				thirdDingNotifyLogService.add(log);
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
		return rs;
	}

	private void callDingdingUpdate(String url, SysNotifyTodo todo,
			OapiWorkrecordUpdateRequest req,
			ThirdDingNotifyWorkrecord record, String ekpUserId) {
		//Thread.dumpStack();
		String dingUrl = url
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		logger.debug("调钉钉接口：" + dingUrl);
		DingApiService dingService = DingUtils.getDingApiService();
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdUrl(client.getRequestUrl());
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setFdSendTime(start);
		log.setFdNotifyData(
				buildUpdateRequestJson(req, null, todo).toString());
		OapiWorkrecordUpdateResponse rsp;
		TransactionStatus deleteStatus = null;
		try {
			rsp = client.execute(req,
					dingService.getAccessToken());
			deleteStatus = TransactionUtils
					.beginNewTransaction();
			log.setFdRtnMsg(rsp.getBody());
			JSONObject jo = JSONObject.fromObject(rsp.getBody());
			String requestId = null;
			if (jo.containsKey("request_id")) {
				requestId = jo.getString("request_id");
			}
			log.setFdRequestId(requestId);
			if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
				log.setFdResult(true);
				logger.info("待办更新到钉钉详细：" + jo.toString());
				try {
					thirdDingNotifyWorkrecordService.delete(record.getFdId());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw e;
				}
			} else {
				log.setFdResult(false);
				logger.warn("待办更新到钉钉创建失败。详细：" + jo.toString());
				record.setFdEKPDel(true);
				thirdDingNotifyWorkrecordService.update(record);
				addErrorQueue(todo, req, record.getFdUser().getFdId(),
						"update", jo.toString());
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
				record.setFdEKPDel(true);
				try {
					thirdDingNotifyWorkrecordService.update(record);
				} catch (Exception e1) {
					logger.error(e.getMessage(), e);
				}
				addErrorQueue(todo, req, record.getFdUser().getFdId(),
						"update", e.getMessage());
			} else if (e instanceof HibernateObjectRetrievalFailureException) {
				logger.error(e.getMessage(), e);
				log.setFdResult(false);
				log.setFdRtnMsg(e.getMessage());
			} else {
				logger.error(e.getMessage(), e);
				log.setFdResult(false);
				log.setFdRtnMsg(e.getMessage());
				addErrorQueue(todo, req, record.getFdUser().getFdId(),
						"update", e.getMessage());
			}

		} finally {
			Date end = new Date();
			log.setFdRtnTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			try {
				thirdDingNotifyLogService.add(log);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void addWorkrecord(String todoId, String subject,
			OapiWorkrecordAddRequest req,
			String ekpUserId, String recordId) {
		TransactionStatus addStatus = null;
		try {
			ThirdDingNotifyWorkrecord record = new ThirdDingNotifyWorkrecord();
			record.setFdDingUserId(req.getUserid());
			record.setFdEKPDel(false);
			record.setFdUser((SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(ekpUserId));
			record.setFdNotifyId(todoId);
			record.setFdRecordId(recordId);
			record.setFdSubject(subject);
			addStatus = TransactionUtils
					.beginNewTransaction();
			thirdDingNotifyWorkrecordService.add(record);
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

	private void addErrorQueue(SysNotifyTodo todo,
			BaseTaobaoRequest req,
			String ekpUserId, String method, String errorMsg) {

		// 添加之前判断是否有重复的，如果有重复，则只是更新errorMsg
		String userid = "";
		boolean updateFlag = false;
		if (req instanceof ThirdDingWorkrecordAddRequest) {
			ThirdDingWorkrecordAddRequest addRequest = (ThirdDingWorkrecordAddRequest) req;
			userid = addRequest.getUserid();
		} else if (req instanceof OapiWorkrecordUpdateRequest) {
			OapiWorkrecordUpdateRequest updaeRequest = (OapiWorkrecordUpdateRequest) req;
			userid = updaeRequest.getUserid();
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"fdTodoId=:fdTodoId and fdDingUserId=:fdDingUserId and fdMethod=:fdMethod");
		hqlInfo.setParameter("fdTodoId", todo.getFdId());
		hqlInfo.setParameter("fdDingUserId", userid);
		hqlInfo.setParameter("fdMethod", method);
		ThirdDingNotifyQueueError error = null;
		try {
			error = (ThirdDingNotifyQueueError) thirdDingNotifyQueueErrorService
					.findFirstOne(hqlInfo);
			if (error != null) {
				logger.warn("------更新错误队列的数据-------");
				updateFlag = true;
			} else {
				error = new ThirdDingNotifyQueueError();
			}
		} catch (Exception e1) {
			error = new ThirdDingNotifyQueueError();
			logger.error(e1.getMessage(), e1);
		}
		error.setFdSubject(todo.getFdSubject());
		error.setFdAppName(todo.getFdAppName());
		error.setFdTodoId(todo.getFdId());
		error.setFdErrorMsg(errorMsg);
		if (req instanceof ThirdDingWorkrecordAddRequest) {
			ThirdDingWorkrecordAddRequest addRequest = (ThirdDingWorkrecordAddRequest) req;
			error.setFdDingUserId(addRequest.getUserid());
			error.setFdJson(
					buildAddRequestJson(addRequest, ekpUserId, todo)
							.toString());
			error.setFdMD5(
					generateMD5("add", todo.getFdId(), addRequest.getUserid()));
		} else if (req instanceof OapiWorkrecordUpdateRequest) {
			OapiWorkrecordUpdateRequest updaeRequest = (OapiWorkrecordUpdateRequest) req;
			error.setFdDingUserId(updaeRequest.getUserid());
			error.setFdJson(
					buildUpdateRequestJson(updaeRequest, ekpUserId, todo)
							.toString());
			error.setFdMD5(generateMD5(method, todo.getFdId(),
					updaeRequest.getUserid()));
		}
		error.setFdCreateTime(new Date());
		error.setFdMethod(method);

		error.setFdRepeatHandle(
				ThirdDingNotifyQueueErrorConstants.NOTIFY_ERROR_REPEAT);
		error.setFdFlag(
				ThirdDingNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_ERROR);
		TransactionStatus addStatus = null;
		try {
			error.setFdUser(
					(SysOrgPerson) sysOrgPersonService
							.findByPrimaryKey(ekpUserId));
			addStatus = TransactionUtils
					.beginNewTransaction();
			if (updateFlag) {
				thirdDingNotifyQueueErrorService.update(error);
			} else {
				thirdDingNotifyQueueErrorService.add(error);
			}

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
	
	public String generateMD5(String opt, String todoId, String dingUserId) {
		return MD5Util.getMD5String(opt + todoId + dingUserId);
	}

	private List<ThirdDingNotifyWorkrecord> findWorkrecord(SysNotifyTodo todo,
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
			List<ThirdDingNotifyWorkrecord> notifies = thirdDingNotifyWorkrecordService
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

	public void updateWorkrecord(SysNotifyTodo todo,
			List<SysOrgElement> persons) throws ApiException, Exception {
		List<ThirdDingNotifyWorkrecord> notifies = findWorkrecord(todo,persons);
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

		JSONObject jo = null;
		if (notifies != null && notifies.size() > 0) {
			OapiWorkrecordUpdateRequest req = null;
			for (ThirdDingNotifyWorkrecord notify : notifies) {
				req = new OapiWorkrecordUpdateRequest();
				req.setUserid(notify.getFdDingUserId());
				req.setRecordId(notify.getFdRecordId());
				callDingdingUpdate(
						DingConstant.DING_PREFIX + "/topapi/workrecord/update",
						todo,
						req, notify, ekpUserId);
			}
		}else{
			updateWorkrecordOld(todo, persons);
		}
	}

	private void updateWorkrecordOld(SysNotifyTodo todo,
			List<SysOrgElement> persons) throws ApiException, Exception {
		long time = System.currentTimeMillis();
		String ekpUserId = null;
		try {
			/*
			 * if (todo.getFdType() == 3 && type == 3) {
			 * logger.debug("当前待办(待办名称：" + todo.getFdSubject() +
			 * ")为暂挂，直接跳过不更新钉钉的待办"); return; }
			 */
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
					if (StringUtil.isNull(ekpUserId)) {
						String dind_userId = ((IOmsRelationService) SpringBeanUtil
								.getBean("omsRelationService"))
										.getDingUserIdByEkpUserId(
												target.getFdId());
						if (StringUtil.isNotNull(dind_userId)) {
							ekpUserId = target.getFdId();
						}
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

			List<ThirdDingDtask> notifies = thirdDingDtaskService
					.findList(where, null);
			if (notifies != null && notifies.size() > 0) {
				for (ThirdDingDtask task : notifies) {
					logger.debug("更新处理人(名称:" + task.getFdEkpUser().getFdName()
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
									ekpUserId);
					if (response.getErrcode() == 0) {
						task.setFdStatus("22");
					} else {
						task.setFdStatus("21");
						logger.error("更新钉钉待办状态异常，错误信息：" + response.getBody());
					}
					task.setFdDesc(response.getBody());
					thirdDingDtaskService.update(task);
					JSONObject jo = JSONObject.fromObject(response.getBody());
					addNotifyLogOld(todo, jo,
							JSONObject.fromObject(req).toString());
				}
			} else {
				logger.debug("更新待办(待办名称：" + todo.getFdSubject() + ",主键："
						+ todo.getFdId() + ")在钉钉待办任务中找不到对应的数据");
			}
			// 老接口历史数据的更新
			// updateWorkrecordOld(todo, persons);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新钉钉待办状态异常，错误信息：", e);
		}
		logger.debug("更新钉钉待办耗时：" + (System.currentTimeMillis() - time) + "毫秒");
	}

	private void addNotifyLogOld(SysNotifyTodo todo, JSONObject jo,
			String reqData) {
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
			logger.error("添加日志发生异常：", e);
		}
	}

	private ISysNotifyTodoService sysNotifyTodoService = null;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}


	public IThirdDingNotifyLogService getThirdDingNotifyLogService() {
		return thirdDingNotifyLogService;
	}

	public void
			setThirdDingNotifyLogService(
					IThirdDingNotifyLogService thirdDingNotifyLogService) {
		this.thirdDingNotifyLogService = thirdDingNotifyLogService;
	}

	public IThirdDingNotifyQueueErrorService
			getThirdDingNotifyQueueErrorService() {
		return thirdDingNotifyQueueErrorService;
	}

	public void setThirdDingNotifyQueueErrorService(
			IThirdDingNotifyQueueErrorService thirdDingNotifyQueueErrorService) {
		this.thirdDingNotifyQueueErrorService = thirdDingNotifyQueueErrorService;
	}
	
	public void add(JSONObject o, String userFdId) throws Exception {
		ThirdDingWorkrecordAddRequest req = buildAddRequest(o);

		String url = DingConstant.DING_PREFIX + "/topapi/workrecord/add"
				+ DingUtil.getDingAppKeyByEKPUserId("?", userFdId);
		logger.debug("钉钉接口：" + url);
		DingApiService dingService = DingUtils.getDingApiService();
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdUrl(client.getRequestUrl());
		log.setFdNotifyId(o.getString("todoId"));
		log.setDocSubject(o.getString("subject"));
		Date start = new Date();
		log.setFdSendTime(start);
		log.setFdNotifyData(req.toString());
		OapiWorkrecordAddResponse rsp;
		try {
			rsp = client.execute(req,
					dingService.getAccessToken());
			log.setFdRtnMsg(rsp.getBody());
			JSONObject jo = JSONObject.fromObject(rsp.getBody());
			String requestId = null;
			if (jo.containsKey("request_id")) {
				requestId = jo.getString("request_id");
			}
			log.setFdRequestId(requestId);
			if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
				log.setFdResult(true);
				logger.info("待办发送到钉钉详细：" + jo.toString());
				String recordId = jo.getString("record_id");
				String todoId = o.getString("todoId");
				String subject = o.getString("subject");
				String ekpUserId = o.getString("ekpUserId");
				addWorkrecord(todoId, subject, (OapiWorkrecordAddRequest) req,
						ekpUserId,
						recordId);
			}else if(jo.containsKey("errcode") && jo.getInt("errcode") == 854001){
				logger.info("待办重复，获取待办列表进行匹配。" + jo.toString());
				String userId = req.getUserid();
				String todoId = o.getString("todoId");
				String subject = o.getString("subject");
				String ekpUserId = o.getString("ekpUserId");
				mappingNotify(userId, todoId, ekpUserId, subject);
			} else {
				log.setFdResult(false);
				logger.warn("待办发送到钉钉创建失败。详细：" + jo.toString());
				throw new ApiException(jo.toString());
			}
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			log.setFdResult(false);
			log.setFdRtnMsg(e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			log.setFdResult(false);
			log.setFdRtnMsg(e.getMessage());
			// addErrorQueue(todo, req, ekpUserId, org,"add");
			throw e;
		} finally {
			Date end = new Date();
			log.setFdRtnTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			try {
				thirdDingNotifyLogService.add(log);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void update(JSONObject o, String userFdId) throws Exception {
		OapiWorkrecordUpdateRequest req = buildUpdateRequest(o);

		String url = DingConstant.DING_PREFIX + "/topapi/workrecord/update"
				+ DingUtil.getDingAppKeyByEKPUserId("?", userFdId);
		logger.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdUrl(client.getRequestUrl());
		log.setFdNotifyId(o.getString("todoId"));
		log.setDocSubject(o.getString("subject"));
		Date start = new Date();
		log.setFdSendTime(start);
		log.setFdNotifyData(req.toString());
		OapiWorkrecordUpdateResponse rsp;
		try {
			DingApiService dingService = DingUtils.getDingApiService();
			rsp = client.execute(req,
					dingService.getAccessToken());
			log.setFdRtnMsg(rsp.getBody());
			JSONObject jo = JSONObject.fromObject(rsp.getBody());
			String requestId = null;
			if (jo.containsKey("request_id")) {
				requestId = jo.getString("request_id");
			}
			log.setFdRequestId(requestId);
			if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
				log.setFdResult(true);
				logger.info("待办更新到钉钉详细：" + jo.toString());
				thirdDingNotifyWorkrecordService.delete(o.getString("recordId"),
						o.getString("todoId"));
			} else {
				log.setFdResult(false);
				logger.warn("待办更新到钉钉创建失败。详细：" + jo.toString());
				throw new ApiException(jo.toString());
			}
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			log.setFdResult(false);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			log.setFdResult(false);
			log.setFdRtnMsg(e.getMessage());
			throw e;
		} finally {
			Date end = new Date();
			log.setFdRtnTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			try {
				thirdDingNotifyLogService.add(log);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private IThirdDingDtaskService thirdDingDtaskService;

	public void setThirdDingDtaskService(
			IThirdDingDtaskService thirdDingDtaskService) {
		this.thirdDingDtaskService = thirdDingDtaskService;
	}

	private IThirdDingNotifylogService thirdDingNotifylogService;

	public void setThirdDingNotifylogService(
			IThirdDingNotifylogService thirdDingNotifylogService) {
		this.thirdDingNotifylogService = thirdDingNotifylogService;
	}

	private IThirdDingDinstanceXformService thirdDingDinstanceXformService;

	public IThirdDingDinstanceXformService getThirdDingDinstanceXformService() {
		if (thirdDingDinstanceXformService == null) {
			thirdDingDinstanceXformService = (IThirdDingDinstanceXformService) SpringBeanUtil
					.getBean("thirdDingDinstanceXformService");
		}
		return thirdDingDinstanceXformService;
	}


	public boolean addTodoByTool(JSONObject curData) throws Exception {

		logger.warn("手动推送单个待办：" + curData);
		String todoId = curData.getString("notifyFdId");
		SysNotifyTodo todo = (SysNotifyTodo) getSysNotifyTodoService()
				.findByPrimaryKey(todoId);
		String ids = curData.getString("ids"); // 格式【钉钉id;ekp的fdId】
		if (StringUtil.isNull(ids) || !ids.contains(";")) {
			logger.warn("ids的格式有误！【钉钉id;ekp的fdId】");
			return false;
		}
		if ("true".equals(
				DingConfig.newInstance().getDingWorkRecordEnabled())
				&& (todo.getFdType() == 1 || todo.getFdType() == 3)) {
			String wrurl = DingUtil.getViewUrl(todo);
			String rs = addWorkrecord(ids.split(";")[0], ids.split(";")[1],
					todo, wrurl);
			logger.warn("【待办工具-待办重发】重推的结果：" + rs);
			if (StringUtil.isNotNull(rs)) {
				JSONObject jo = JSONObject.fromObject(rs);
				if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}

		} else {
			logger.warn("推送待办到钉钉的开关已关闭或该待办的的类型不是1（待办）");
		}

		return false;
	}


	/**
	 * 待办重发如果遇到待办重复的错误，则获取该用户在钉钉的待办列表进行匹配
	 * @param dingUserId
	 * @param notifyId
	 * @param ekpUserId
	 * @param subject
	 * @throws Exception
	 */
	private void mappingNotify(String dingUserId, String notifyId,
							   String ekpUserId, String subject)
			throws Exception {
		StringBuffer errorBuffer = new StringBuffer();
		Map<String, OapiWorkrecordGetbyuseridResponse.WorkRecordVo> voWRMap = new HashMap<String, OapiWorkrecordGetbyuseridResponse.WorkRecordVo>();
		CleaningToolUtil.handleNotifyWR(dingUserId, 0L, 0L, voWRMap, ekpUserId,
				errorBuffer);
		if (errorBuffer.length() > 0) {
			logger.error("获取待办列表失败,用户：" + dingUserId + ",错误信息：" + errorBuffer);
			return;
		}
		String workrecordId = null;
		for (String key : voWRMap.keySet()) {
			OapiWorkrecordGetbyuseridResponse.WorkRecordVo vo = voWRMap.get(key);
			String url = vo.getUrl();
			if (url.contains(notifyId)) {
				workrecordId = key;
				break;
			}
		}
		if (workrecordId == null) {
			logger.error("匹配不到对应的钉钉待办：" + dingUserId + ",ekp待办ID：" + notifyId);
			return;
		}
		TransactionStatus addStatus = null;
		try {
			ThirdDingNotifyWorkrecord record = new ThirdDingNotifyWorkrecord();
			record.setFdDingUserId(dingUserId);
			record.setFdEKPDel(false);
			record.setFdUser((SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(ekpUserId));
			record.setFdNotifyId(notifyId);
			record.setFdRecordId(workrecordId);
			record.setFdSubject(subject);
			addStatus = TransactionUtils
					.beginNewTransaction();
			thirdDingNotifyWorkrecordService.add(record);
			TransactionUtils.getTransactionManager().commit(addStatus);
			logger.debug("待办匹配成功，对应的钉钉待办ID为：" + workrecordId);
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
