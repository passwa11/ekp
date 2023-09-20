package com.landray.kmss.third.ding.notify.provider;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.request.OapiWorkrecordAddRequest.FormItemVo;
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
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingDinstanceXform;
import com.landray.kmss.third.ding.model.ThirdDingTodoCard;
import com.landray.kmss.third.ding.model.ThirdDingTodoTemplate;
import com.landray.kmss.third.ding.model.api.TodoCard;
import com.landray.kmss.third.ding.model.api.TodoTask;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyLog;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyWorkrecord;
import com.landray.kmss.third.ding.notify.queue.constant.ThirdDingNotifyQueueErrorConstants;
import com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError;
import com.landray.kmss.third.ding.notify.queue.service.IThirdDingNotifyQueueErrorService;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyLogService;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyWorkrecordService;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.provider.DingXformNotifyProvider;
import com.landray.kmss.third.ding.service.*;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ObjectUtil;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.enums.ValueLabel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;
import java.util.*;

public class ThirdDingTodoTaskProvider extends BaseSysNotifyProviderExtend implements
		DingConstant {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingTodoTaskProvider.class);
	
	private ISysOrgPersonService sysOrgPersonService;

	private IThirdDingNotifyLogService thirdDingNotifyLogService;

	private IThirdDingNotifyQueueErrorService thirdDingNotifyQueueErrorService;

	private IThirdDingNotifyWorkrecordService thirdDingNotifyWorkrecordService;

	private IThirdDingTodoCardService thirdDingTodoCardService;

	public static final String PREFIX_TODO_V2 ="$TODOV2$";

	public static final String API_TIP="[待办接口V2.0]";

	public static final String PREFIX_API="V2.0_";

	private ThirdDingTodoTaskProvider dingTodoTaskProvider;

	public ThirdDingTodoTaskProvider getDingTodoTaskProvider() {
		if (dingTodoTaskProvider == null) {
			dingTodoTaskProvider = (ThirdDingTodoTaskProvider) SpringBeanUtil
					.getBean("thirdDingWorkrecordProvider");
		}
		return dingTodoTaskProvider;
	}

	public IThirdDingTodoCardService getThirdDingTodoCardService() {
		if (thirdDingTodoCardService == null) {
			thirdDingTodoCardService = (IThirdDingTodoCardService) SpringBeanUtil
					.getBean("thirdDingTodoCardService");
		}
		return thirdDingTodoCardService;
	}

	private IThirdDingTodoTemplateService thirdDingTodoTemplateService;

	public IThirdDingTodoTemplateService getThirdDingTodoTemplateService() {
		if (thirdDingTodoTemplateService == null) {
			thirdDingTodoTemplateService = (IThirdDingTodoTemplateService) SpringBeanUtil
					.getBean("thirdDingTodoTemplateService");
		}
		return thirdDingTodoTemplateService;
	}

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
		if (!DingUtil.checkNotifyApiType("TODO")) {
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
			
			Map<String, String> dingIdMap = DingUtil.getDingUnionIdMap(
					ekpIds);

			if (dingIdMap == null || dingIdMap.size() == 0) {
				logger.error("通过EKP的fdId查找人员映射表发现找不到对应的钉钉人员(" + ekpIds
						+ ")，请先检查组织同步是否正常");
				return;
			}

			//发起人
			SysOrgPerson creator = context.getDocCreator();
			String creatorUnionId = null;
            if(creator!=null){
				logger.debug("待办的发起人"+creator.getFdName());
				 creatorUnionId = DingUtil.getUnionIdByEkpId(creator.getFdId());
			}else {
            	logger.warn("-----------待办发起人为空-----------");
			}
			if (dingIdMap != null && dingIdMap.size() > 0) {
				// 工作待办推送
				if ("true".equals(
						DingConfig.newInstance().getDingWorkRecordEnabled())
						&& (todo.getFdType() == 1 || todo.getFdType() == 3)) {
					if (dingIdMap.size() > 0) {
						addTask(creatorUnionId,dingIdMap.keySet().toArray(new String[0]),
								todo, DingUtil.getViewUrl(todo));
					}
				}
			}
		}
		logger.debug("发送钉钉消息总耗时："+(System.currentTimeMillis()-atime)+"毫秒");
	}

	private Boolean addTask(String creatorUnionId, String[] targetUnionIds, SysNotifyTodo todo, String url) {
		//日志记录
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		TodoTask todoTask = new TodoTask();
		//记录原始的创建者unionId
		if(StringUtil.isNull(creatorUnionId)){
			logger.warn("创建人unionId为空，优先取管理员union作为创建人");
			creatorUnionId=DingUtil.getDingAdminUinionId();
			if(StringUtil.isNull(creatorUnionId)){
				logger.warn("获取管理员unionId失败，取第一个待办执行人作为创建人："+targetUnionIds[0]);
				creatorUnionId=targetUnionIds[0];
			}
			todoTask.setOnlyShowExecutor(true);
		}

		//是否只是展示执行人列表
		String notifyIsOnlyShowExecutor = DingConfig.newInstance().getNotifyIsOnlyShowExecutor();
		if("true".equals(notifyIsOnlyShowExecutor)){
			todoTask.setOnlyShowExecutor(true);
		}

		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setFdSendTime(start);
		try {
			//发起人
			todoTask.setSourceId(todo.getFdId());
			todoTask.setCreatorId(creatorUnionId);
			//执行人
			todoTask.setExecutorIds(Arrays.asList(targetUnionIds.clone()));
			log.setFdUrl("[POST]"+DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+(StringUtil.isNull(creatorUnionId)?targetUnionIds[0]:creatorUnionId)+"/tasks");

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
			logger.debug("待办的主题：" + title);
			todoTask.setSubject(title);

			//待办跳转地址
			logger.debug("新待办移动端url：" + url);
			String pcurl = DingUtil.getPcViewUrl(todo);
			logger.debug("pc端地址："+pcurl);
			todoTask.setDetailUrl(new TodoTask.DetailUrl(url,pcurl));

			//获取卡片Id
			logger.debug("待办的语言："+todo.getFdLang());
			String cardId = getCardId(todo,creatorUnionId,todo.getFdLang());
			// 自定义待办卡片内容
			if(StringUtil.isNotNull(cardId)){
				try {
					ThirdDingTodoCard card= (ThirdDingTodoCard) getThirdDingTodoCardService().findFirstOne("fdCardId='"+cardId+"'",null);
					if(card != null){
						logger.debug("***根据cardId:"+cardId+" 找到记录了***");
						List<TodoTask.Filed> list = new ArrayList<TodoTask.Filed>();
						setTaslFiled(list,card,todo);
						todoTask.setContentFieldList(list);
					}else{
						logger.warn("***根据cardId:"+cardId+" 无法找到记录***");
					}
					todoTask.setCardTypeId(cardId);
					log.setFdNotifyData(JSON.toJSONString(todoTask));
					JSONObject rs = DingUtils.getDingApiService().addTask(creatorUnionId,todoTask);
					log.setFdRtnMsg(rs==null?null:rs.toString());
					logger.debug("创建新待办rs:"+rs);
					//记录日志
					if(rs!=null&&rs.containsKey("id")){
						log.setFdResult(true);
						//成功
						String recordId = rs.getString("id");
						recordId=PREFIX_TODO_V2+recordId;
						addWorkrecord(todo.getFdId(),todo.getFdSubject(),creatorUnionId,(todo.getDocCreator()==null?null:todo.getDocCreator().getFdId()),recordId);
					}else{
						//待办失败
						log.setFdResult(false);
						String reqParam = JSON.toJSONString(todoTask);
						addErrorQueue(todo,reqParam,(todo.getDocCreator()==null?null:todo.getDocCreator().getFdId()),"add",rs==null?"null":rs.toString(),creatorUnionId);
					}
				} catch (Exception e) {
					logger.warn(e.getMessage(),e);
					log.setFdResult(false);
					addErrorQueue(todo,JSON.toJSONString(todoTask),(todo.getDocCreator()==null?null:todo.getDocCreator().getFdId()),"add","程序异常:"+e.getMessage(),creatorUnionId);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return false;
		}finally {
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


	}


	//添加卡片的值
	private void setTaslFiled(List<TodoTask.Filed> list, ThirdDingTodoCard thirdDingTodoCard, SysNotifyTodo todo) {

		try {
			ThirdDingTodoTemplate thirdDingTodoTemplate = (ThirdDingTodoTemplate) getThirdDingTodoTemplateService().findByPrimaryKey(thirdDingTodoCard.getFdTemplateId());
			String fdDetail = thirdDingTodoCard.getFdCardMsg();
			JSONArray detailJSONArry = JSONObject.fromObject(fdDetail).getJSONArray("contentFieldList");
			if(detailJSONArry == null || detailJSONArry.isEmpty()) {
                return;
            }

			SysDictModel model = null;
			Object main = new Object();
			Map<String,String> keyIsFromForm = new HashMap<String,String> (); //判断表单字段是否来自流程主文档
			if("1".equals(thirdDingTodoTemplate.getFdIsdefault())){
				model = SysDataDict.getInstance()
						.getModel("com.landray.kmss.sys.notify.model.SysNotifyTodo");
				main=todo;

			}else{
				//通用模块模板
				model = SysDataDict.getInstance().getModel(todo.getFdModelName());
				String beanName = model.getServiceBean();
				IBaseService obj = (IBaseService) SpringBeanUtil.getBean(beanName);
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("fdId=:fdId");
				hqlInfo.setParameter("fdId", todo.getFdModelId());
				main = obj.findFirstOne(hqlInfo);
				if (main == null) {
					logger.error("*****获取模块主文档失败！****");
					return;
				}
			}

			if(StringUtil.isNotNull(thirdDingTodoTemplate.getFdTemplateId())){
				//表单模板
				keyIsFromForm = getReviewKeyMap(thirdDingTodoTemplate);
			}
			Map<String, SysDictCommonProperty> map = model.getPropertyMap();

			//默认模板
			for(int i=0;i<detailJSONArry.size();i++){
				JSONObject field = detailJSONArry.getJSONObject(i);
				String key = field.getString("fieldKey");
				Object value = "";
				String enumType = null;
				String type = null;
				Class clazz = null;

				try {
					if(keyIsFromForm!=null&&keyIsFromForm.containsKey(key)&&"true".equalsIgnoreCase(keyIsFromForm.get(key))){
						//流程表单字段
						logger.warn("流程表单字段："+key);
						String content = ObjectUtil.getFormValue(todo, thirdDingTodoTemplate,key);
						logger.warn("流程表单字段值："+content);
						if(StringUtil.isNull(content)) {
                            content="--";
                        }
						list.add(new TodoTask.Filed(key,content));
						continue;
					}

					if(key.contains(".")){
						String[] keyArray = key.split("\\.");
						Map<String, SysDictCommonProperty> map2 = null;
						SysDictCommonProperty property = null;
						SysDictModel model2 = null;
						SysDictCommonProperty property2=null;
						property2 = map.get(keyArray[0]);
						type = property2.getType();
						clazz = main.getClass();
						value=main;
						for(int j=0;j<keyArray.length;j++){
							// value=com.opensymphony.util.BeanUtils.getValue(main, keyArray[j]);
							if(value!=null) {
								if (j != 0) {
									clazz = value.getClass();
								}
								String temp_key = keyArray[j];
								temp_key = "get" + keyArray[j].substring(0, 1).toUpperCase()
										+ keyArray[j].substring(1);
								logger.debug("======_key=======:" + temp_key);

								Method method = clazz.getMethod(temp_key.trim());
								value = method.invoke(value);
							}

							//对象
							if(j<keyArray.length-1){
								model2 = SysDataDict.getInstance().getModel(type);
								map2 = model2.getPropertyMap();
								property2 = map2.get(keyArray[j+1]);
								type = property2.getType();
							}
						}
						enumType = property2.getEnumType();

					}else{
						value = com.opensymphony.util.BeanUtils.getValue(main, key);
						logger.debug("key为简单类型：" +key);
						SysDictCommonProperty property = map.get(key);
						enumType = property.getEnumType();
						type = property.getType();
					}
					logger.debug("是否枚举："+enumType);
					String finalValue = "";

					if(value==null || "".equals(value)){
						list.add(new TodoTask.Filed(key,"--"));
						continue;
					}

					// 枚举进行转换
					if (StringUtil.isNotNull(enumType)) {
						finalValue = (String) value;
						List enumList = EnumerationTypeUtil.getColumnEnumsByType(enumType);
						for (int k = 0; k < enumList.size(); k++) {
							ValueLabel valueLabel = (ValueLabel) enumList.get(k);
							if (finalValue.equals(valueLabel.getValue())) {
								finalValue = DingUtil.getValueByLang(
										valueLabel.getLabelKey(),
										valueLabel.getBundle(),
										todo.getFdLang());
								logger.debug("枚举转换：" + finalValue + "("
										+ enumType + ")");
							}
						}
					}else{
						//类型转换
						if("DateTime".equals(type)){
							finalValue = DateUtil.convertDateToString((Date) value,"yyyy-MM-dd HH:mm");
						}else if("Date".equals(type)){
							finalValue = DateUtil.convertDateToString((Date) value,"yyyy-MM-dd");
						}else if("Time".equals(type)){
							finalValue = DateUtil.convertDateToString((Date) value,"HH:mm");
						}else if("Integer".equals(type)){
							finalValue = String.valueOf(value);
						}else{
							finalValue = (String) value;
						}
					}
					list.add(new TodoTask.Filed(key,finalValue));
				} catch (Exception e) {
					logger.warn("***获取key:"+key+" 对应的值异常");
					logger.error(e.getMessage(),e);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	private Map<String, String> getReviewKeyMap(ThirdDingTodoTemplate thirdDingTodoTemplate) {
		Map<String, String> map = new HashMap<String, String> ();

		String fdDetail = thirdDingTodoTemplate.getFdDetail();
		JSONObject fdDetailJSON = JSONObject.fromObject(fdDetail);
		JSONArray ja = fdDetailJSON.getJSONArray("data");
		for (int i = 0; i < ja.size(); i++) {
			map.put(ja.getJSONObject(i).getString("key"),ja.getJSONObject(i).getString("fromForm"));
		}
		return map;
	}


	private String getCardId(SysNotifyTodo todo,String creatorUnionId,String lang) {
		String fdModelName = todo.getFdModelName();
		String fdModelId = todo.getFdModelId();
		if(StringUtil.isNull(lang)) {
            lang= DingUtil.getTodoDefaultLang();
        }
		logger.debug("fdModelName:" + fdModelName + " fdModelId:" + fdModelId+" lang:"+lang);
		List<FormItemVo> itemVos = new ArrayList<FormItemVo>();
		if (StringUtil.isNull(fdModelName) || StringUtil.isNull(fdModelId)) {
			return getDefaultCardId(todo,creatorUnionId,lang);
		}
		// 获取待办模版
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"thirdDingTodoTemplate.fdModelName=:fdModelName and (fdType is null or fdType='' or fdType like '%1%')");
		hqlInfo.setParameter("fdModelName", fdModelName);
		List<ThirdDingTodoTemplate> list;
		try {
			list = getThirdDingTodoTemplateService().findList(hqlInfo);
			ThirdDingTodoTemplate thirdDingTodoTemplate = getTemplateModel(list);
			if (thirdDingTodoTemplate != null) {
				// 使用自定义待办模版
				logger.warn("------使用自定义待办模版推送待办-----");
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
					String todoTemplateId = templateId.toString();  //ekp表单模板ID
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
						thirdDingTodoTemplate = thirdDingTodoTemplateTemp;

					} else if (kmReviewDefault != null) {
						// 流程模版
						thirdDingTodoTemplate = kmReviewDefault;
					} else {
						// 默认模版
						return getDefaultCardId(todo,creatorUnionId,lang);
					}

				}
				return getModelCardId(thirdDingTodoTemplate,todo,creatorUnionId,lang);

			} else {
				logger.debug("使用默认待办模版！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return getDefaultCardId(todo,creatorUnionId,lang);
	}

	private String getModelCardId(ThirdDingTodoTemplate thirdDingTodoTemplate, SysNotifyTodo todo, String creatorUnionId, String lang) {
		return getCommonCardId(thirdDingTodoTemplate, lang, creatorUnionId);
	}

	//获取默认模板对应的卡片信息
	private Object[] getDefaultCardId(String lang){

		String sql = "SELECT card.fd_id,card.fd_card_id FROM third_ding_todo_card card,third_ding_todo_template temp WHERE card.fd_template_id=temp.fd_id AND temp.fd_isdefault='1' and fd_lang='"+lang+"'";
		try {
			List<Object[]> list= thirdDingNotifyWorkrecordService.getBaseDao().getHibernateSession().createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
				return list.get(0);
			}else{
				logger.warn("------默认模板没有对应的钉钉卡片-----lang:"+lang);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}


	private String getDefaultCardId(SysNotifyTodo todo,String creatorUnionId,String todoLang) {
		if (StringUtil.isNotNull(todoLang) && todoLang.contains("|")) {
			todoLang = todoLang.substring(todoLang.indexOf("|") + 1);
		}
		// 获取待办模版
		Object[] cardInfo = getDefaultCardId(todoLang);
		if (cardInfo != null) {
			return (String) cardInfo[1];
		} else {
			//没有卡片，则新建钉钉卡片
			// 获取待办模版
			try {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"thirdDingTodoTemplate.fdIsdefault=:fdIsdefault");
				hqlInfo.setParameter("fdIsdefault", "1");
				ThirdDingTodoTemplate thirdDingTodoTemplate = (ThirdDingTodoTemplate) getThirdDingTodoTemplateService().findFirstOne(hqlInfo);
				if (thirdDingTodoTemplate != null) {
					return getCommonCardId(thirdDingTodoTemplate, todoLang, creatorUnionId);
				}
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}

		}
		return null;
	}

	private String getCardIdByTemplateAndLang(String thirdDingTodoTemplateId, String todoLang){
		//先查询，找不到记录则新建
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdCardId");
		hqlInfo.setWhereBlock("fdTemplateId =:fdTemplateId and fdLang=:fdLang");
		hqlInfo.setParameter("fdTemplateId",thirdDingTodoTemplateId);
		hqlInfo.setParameter("fdLang",todoLang);
		try {
			return (String) getThirdDingTodoCardService().findFirstOne(hqlInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	private String getCommonCardId(ThirdDingTodoTemplate thirdDingTodoTemplate, String todoLang, String creatorUnionId) {

		logger.debug("*****默认语言："+todoLang);
		String id = getCardIdByTemplateAndLang(thirdDingTodoTemplate.getFdId(),todoLang);
		if(StringUtil.isNotNull(id)) {
            return id;
        }

		String fdDetail = thirdDingTodoTemplate.getFdDetail();
		logger.debug("fdDetail:" + fdDetail);
		JSONObject fdDetailJSON = JSONObject.fromObject(fdDetail);
		JSONArray ja = fdDetailJSON.getJSONArray("data");
		String key;
		String fromForm;
		String name;
		JSONArray titleJA = new JSONArray();

		//根据语言解析出来
		Map<String, List> langMap = new HashMap<String, List>();
		Map<String, String> langMapping = new HashMap<String, String>();
		for (int i = 0; i < ja.size(); i++) {
			name = "";
			key = ja.getJSONObject(i).getString("key");
			titleJA = ja.getJSONObject(i).getJSONArray("title");
			for (int j = 0; j < titleJA.size(); j++) {
				String lang = todoLang;
				if(titleJA.getJSONObject(j).containsKey("lang")){
					lang= titleJA.getJSONObject(j)
							.getString("lang");
				}
				//"lang": "中文|zh-CN"
				if (StringUtil.isNotNull(lang) && lang.contains("|")) {
					lang = lang.substring(lang.indexOf("|") + 1);
				}
				logger.warn("语言：" + lang);
				name = titleJA.getJSONObject(j).getString("value");
				if (StringUtil.isNull(name)) {
                    continue;
                }
				List tempList = null;
				if (langMap.containsKey(lang)) {
					tempList = langMap.get(lang);
				} else {
					tempList = new ArrayList<String>();
				}
				String[] tempTitle = new String[2];
				tempTitle[0] = key;
				tempTitle[1] = name;
				tempList.add(tempTitle);
				langMap.put(lang, tempList);
			}

		}
		//根据语言去创建卡片
		List<String[]> fieldArray = null;
		if(langMap.containsKey(todoLang)){
			fieldArray = langMap.get(todoLang);
		}else{
			//不包含待办语言，在模板里，则以待办语言创建
			for(String lang:langMap.keySet()){
				fieldArray= langMap.get(lang);
				break;
			}
		}

		TodoCard card = new TodoCard();
		card.setCardType(TodoCard.CARDTYPE_CUSTOM);
		card.setIcon("https://img.alicdn.com/imgextra/i4/O1CN01DL6m7D22fNN3EY3RK_!!6000000007147-2-tps-24-24.png");
		card.setPcDetailUrlOpenMode("PC_SLIDE");
		List<TodoCard.CardField> cardList = new ArrayList<TodoCard.CardField>();
		for (int k = 0; k < fieldArray.size(); k++) {
			cardList.add(new TodoCard.CardField(fieldArray.get(k)[0], new TodoCard.NameI18n(fieldArray.get(k)[1])));
		}
		card.setContentFieldList(cardList);

		JSONObject addResult = null;
		try {
			addResult = DingUtils.getDingApiService().addCard(creatorUnionId, card);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		logger.warn("新建卡片结果：" + addResult);

		//记录卡片日志
		if(addResult!=null&&addResult.containsKey("id")) {
			addDingCard(addResult, thirdDingTodoTemplate, todoLang);
			return addResult.getString("id");
		}else{
			logger.warn("新建卡片识别："+addResult);
		}
		return null;
	}


	private void addDingCard(JSONObject result, ThirdDingTodoTemplate temp,String lang) {

		try {
			ThirdDingTodoCard card = new ThirdDingTodoCard();
			card.setFdCardId(result.getString("id"));
			card.setFdName(temp.getFdName());
			card.setFdTemplateId(temp.getFdId());
			card.setFdLang(lang);
			card.setDocCreateTime(new Date());
			card.setFdCardMsg(result.toString());
			getThirdDingTodoCardService().add(card);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

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
			//return;
		}
		updateTodoTask(todo,"clearTodoPersons",false,null);
	}

	@Override
    public void remove(SysNotifyTodo todo) throws Exception {
		logger.warn("remove ：" + todo.getFdId() + "," + todo.getFdSubject());
		if (!checkNeedNotify(todo, null)) {
			return;
		}
		if (DingUtil.hasInstanceInXform(todo)) {
			logger.warn("-------开启钉钉审批高级版/套件，接口走钉钉待办旧接口更新    remove-------");
			DingXformNotifyProvider.newInstance().remove(todo);
			//return;
		}
		//删除待办
		DingApiService dingService = DingUtils.getDingApiService();
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setFdSendTime(start);
		boolean needAddLog = true;
		try {
			//根据待办id,查询出recordId
			ThirdDingNotifyWorkrecord workrecord = null;
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdNotifyId=:fdNotifyId");
			hqlInfo.setParameter("fdNotifyId",todo.getFdId());
			workrecord = (ThirdDingNotifyWorkrecord) thirdDingNotifyWorkrecordService.findFirstOne(hqlInfo);
			if(workrecord != null){
				String recordId =workrecord.getFdRecordId();
				if(recordId.startsWith(PREFIX_TODO_V2)){
					recordId = recordId.replace(PREFIX_TODO_V2,"").trim();
					//创建人
					String creatorUnionId = workrecord.getFdDingUserId();
					log.setFdUrl("[DELETE]"+DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+creatorUnionId+"/tasks/"+recordId);

					JSONObject rs = dingService.deleteTask(creatorUnionId,recordId);
					log.setFdRtnMsg(rs==null?"null":rs.toString());
					if(rs!=null&&rs.containsKey("result")&&rs.getBoolean("result")){
						log.setFdResult(true);
						//删除成功，将待办映射表记录删除
						thirdDingNotifyWorkrecordService.delete(workrecord.getFdId());
					}else{
						log.setFdResult(false);
						addErrorQueue(todo,"",(todo.getDocCreator()==null?null:todo.getDocCreator().getFdId()),"delete",(rs==null?"返回为空":rs.toString()),creatorUnionId);
					}
				}else{
					ThirdDingWorkrecordProvider provider= (ThirdDingWorkrecordProvider) SpringBeanUtil.getBean("thirdDingWorkrecordProvider");
					provider.updateWorkrecord(todo,null);
					needAddLog = false;
					return;
				}
			}else {
				log.setFdResult(false);
				log.setFdRtnMsg("***待办映射表找不到对应的记录，remove失败***");
				logger.warn("映射表找不到对应的日志，更新失败："+todo.getFdSubject());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			log.setFdResult(false);
			addErrorQueue(todo,"",(todo.getDocCreator()==null?null:todo.getDocCreator().getFdId()),"delete","程序异常："+e.getMessage(),null);
			log.setFdRtnMsg("程序异常："+e.getMessage());
		}finally {
			if(needAddLog==true) {
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
			//return;
		}
		updateTodoTask(todo,"setTodoDone",true,null);
	}

	/**
	 * 更新待办,updateTotal为false，局部更新待办，即更新部分人员
	 * @param todo
	 * @param
	 */
	private void updateTodoTask(SysNotifyTodo todo, String method,boolean updateTotal,List<SysOrgElement> persons) {

		logger.info("****更新的方法："+method);
		DingApiService dingService = DingUtils.getDingApiService();
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setFdSendTime(start);
		boolean needAddLog = true;
		try {
			//根据待办id,查询出recordId
			ThirdDingNotifyWorkrecord workrecord = null;
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdNotifyId=:fdNotifyId");
			hqlInfo.setParameter("fdNotifyId",todo.getFdId());
			workrecord = (ThirdDingNotifyWorkrecord) thirdDingNotifyWorkrecordService.findFirstOne(hqlInfo);
			if(workrecord != null){
				String recordId = workrecord.getFdRecordId();
				if(recordId.startsWith(PREFIX_TODO_V2)){
					recordId = recordId.replace(PREFIX_TODO_V2,"").trim();
					//新待办接口
					logger.warn("***新数据："+todo.getFdSubject()+"***");
					//创建人
					String creatorUnionId = workrecord.getFdDingUserId();
					//钉钉待办id
					if(updateTotal){
						JSONObject req  = new JSONObject();
						req.put("done",true);
						log.setFdUrl("[PUT]"+DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+creatorUnionId+"/tasks/"+recordId);
						log.setFdNotifyData(req.toString());
						JSONObject rs = dingService.updateTask(creatorUnionId,recordId,req);
						log.setFdRtnMsg(rs==null?"返回为空":rs.toString());
						//同步状态
						if(rs!=null&&!rs.isEmpty()&&rs.containsKey("result")&&rs.getBoolean("result")){
							log.setFdResult(true);
							//删除映射表记录
							thirdDingNotifyWorkrecordService.delete(workrecord.getFdId());
						}else{
							log.setFdResult(false);
							addErrorQueue(todo,req.toString(),(todo.getDocCreator()==null?null:todo.getDocCreator().getFdId()),"update",(rs==null?"返回为空":rs.toString()),creatorUnionId);
						}
					}else{
						//局部更新
						logger.warn("----------局部更新--------");
						if(persons!=null&&persons.size()>0){
							JSONObject req  = new JSONObject();
							JSONArray executorStatusList = new JSONArray();
							String ekpUserId = "";
							for(SysOrgElement person:persons){
								ekpUserId+=person.getFdId()+";";
								String unionId = DingUtil.getUnionIdByEkpId(person.getFdId());
								if(StringUtil.isNull(unionId)){
									logger.warn("***待办："+todo.getFdSubject()+" 中的更新人员："+person.getFdName()+" 在对照表中找不到unionId,请先维护对照表关系");
									continue;
								}
								JSONObject user  = new JSONObject();
                                user.put("id",unionId);
								user.put("isDone",true);
								executorStatusList.add(user);
							}
							req.put("executorStatusList",executorStatusList);
							log.setFdNotifyData(req.toString());

							if (executorStatusList.isEmpty()){
								logger.warn("***需要更新的人为空*** ekp用户ids:"+ekpUserId);
								log.setFdResult(false);

								//暂时不考虑更新映射表异常的数据
								//req.put("ekpUserIds",ekpUserId);  //把原ekpUserId放在数据里
								//addErrorQueue(todo,req.toString(),todo.getDocCreator().getFdId(),"update","***需要更新的人为空***");
							}else{
								log.setFdUrl(DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+creatorUnionId+"/tasks/"+recordId+"/executorStatus");

								JSONObject result = dingService.updateExecutorStatus(creatorUnionId,recordId,req);
								log.setFdRtnMsg(result==null?"返回为空":result.toString());
								if(result!=null&&result.containsKey("result")&&result.getBoolean("result")){
									logger.warn("更新成功："+result);
									log.setFdResult(true);
								}else{
									logger.warn("更新失败："+result);
									log.setFdResult(false);
									addErrorQueue(todo,req.toString(),(todo.getDocCreator()==null?null:todo.getDocCreator().getFdId()),"update_executorStatus",(result==null?"返回为空":result.toString()),creatorUnionId);
								}
							}
						}else{
							logger.warn("________清空待办处理人员_________"+todo.getFdSubject());
							JSONObject req = new JSONObject();
							log.setFdUrl("[PUT]"+DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+creatorUnionId+"/tasks/"+recordId);
							req.put("executorIds",new JSONArray());
							log.setFdNotifyData(req.toString());
							JSONObject rs = dingService.updateTask(creatorUnionId,recordId,req);
							log.setFdRtnMsg(rs==null?"null":rs.toString());
							if(rs!=null&&rs.containsKey("result")&&rs.getBoolean("result")){
								logger.warn("待办："+todo.getFdSubject()+"  清理所有执行人完成："+(rs==null?"null":rs.toString()));
								log.setFdResult(true);
							}else{
								logger.warn("待办："+todo.getFdSubject()+"  处理异常："+(rs==null?"null":rs.toString()));
								log.setFdResult(false);
								addErrorQueue(todo,req.toString(),(todo.getDocCreator()==null?null:todo.getDocCreator().getFdId()),"update",(rs==null?"返回为空":rs.toString()),creatorUnionId);
							}
						}
					}
				}else{
					logger.warn("***旧数据："+todo.getFdSubject()+"***"+method);
					ThirdDingWorkrecordProvider provider= (ThirdDingWorkrecordProvider) SpringBeanUtil.getBean("thirdDingWorkrecordProvider");
					provider.updateWorkrecord(todo,persons);
					needAddLog = false;
					return;
				}
			}else {
				log.setFdResult(false);
				log.setFdRtnMsg("***待办映射表找不到对应的记录，更新失败***");
				logger.warn("映射表找不到对应的日志，更新失败："+todo.getFdSubject());
			}

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			log.setFdResult(false);
			log.setFdRtnMsg("程序异常："+e.getMessage());
		}finally {
			if(needAddLog==true) {
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
			DingXformNotifyProvider.newInstance().removeDonePerson(todo,
					person);
			//return;
		}
		List<SysOrgElement> persons = new ArrayList<SysOrgElement>();
		persons.add(person);
		updateTodoTask(todo,"removeDonePerson",false,persons);
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
			DingXformNotifyProvider.newInstance().setPersonsDone(todo,persons);
			//return;
		}

		updateTodoTask(todo,"setPersonsDone",false,persons);
	}
	
	@Override
    public void updateTodo(SysNotifyTodo todo) throws Exception {
		logger.warn("更新待办updateTodo ：" + todo.getFdId() + ","+ todo.getFdSubject());
		//目前只更新标题，比如暂挂
		DingApiService dingService = DingUtils.getDingApiService();
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdNotifyId(todo.getFdId());
		log.setDocSubject(todo.getFdSubject());
		Date start = new Date();
		log.setFdSendTime(start);
		boolean needAddLog = true;
		try {
			//根据待办id,查询出recordId
			ThirdDingNotifyWorkrecord workrecord = null;
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdNotifyId=:fdNotifyId");
			hqlInfo.setParameter("fdNotifyId",todo.getFdId());
			workrecord = (ThirdDingNotifyWorkrecord) thirdDingNotifyWorkrecordService.findFirstOne(hqlInfo);
			if(workrecord != null){
				String recordId = workrecord.getFdRecordId();
				if(recordId.startsWith(PREFIX_TODO_V2)){
					recordId = recordId.replace(PREFIX_TODO_V2,"").trim();
					//创建人
					String creatorUnionId = workrecord.getFdDingUserId();
					//钉钉待办id
					JSONObject req = new JSONObject();
					String subject = getSubject(todo);
					//暂挂+紧急程度
					Locale local = null;
					if (StringUtil.isNotNull(todo.getFdLang())
							&& todo.getFdLang().contains("-")) {
						local = new Locale(todo.getFdLang().split("-")[0],
								todo.getFdLang().split("-")[1]);
					}

					switch (todo.getFdLevel()) {
						case 1:
							subject = "【"
									+ ResourceUtil.getStringValue(
									"sysNotifyTodo.level.taglib.1", "sys-notify", local)
									+ "】" + subject;
							break;
						case 2:
							subject = "【"
									+ ResourceUtil.getStringValue(
									"sysNotifyTodo.level.taglib.2", "sys-notify", local)
									+ "】" + subject;
							break;
					}
					if (todo.getFdType() == 3) {
						subject = "【"
								+ ResourceUtil.getStringValue(
								"sysNotifyTodo.type.suspend", "sys-notify", local)
								+ "】" + subject;
					}
					req.put("subject",subject);
					log.setFdUrl("[PUT]"+DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+creatorUnionId+"/tasks/"+recordId);
					log.setFdNotifyData(req.toString());
					JSONObject rs = dingService.updateTask(creatorUnionId,recordId,req);
					log.setFdRtnMsg(rs==null?"null":rs.toString());
					if(rs!=null&&rs.containsKey("result")&&rs.getBoolean("result")){
						log.setFdResult(true);
					}else{
						log.setFdResult(false);
					}
				}else{
					ThirdDingWorkrecordProvider provider= (ThirdDingWorkrecordProvider) SpringBeanUtil.getBean("thirdDingWorkrecordProvider");
					provider.updateTodo(todo);
					needAddLog = false;
					return;
				}
			}else {
				log.setFdResult(false);
				log.setFdRtnMsg("***待办映射表找不到对应的记录，更新失败***");
				logger.warn("映射表找不到对应的日志，更新失败："+todo.getFdSubject());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			log.setFdResult(false);
			log.setFdRtnMsg("程序异常："+e.getMessage());
		}finally {
			if(needAddLog==true) {
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
	private void addWorkrecord(String todoId, String subject,
			String creatorUnionId,
			String ekpUserId, String recordId) {
		TransactionStatus addStatus = null;
		try {
			ThirdDingNotifyWorkrecord record = new ThirdDingNotifyWorkrecord();
			record.setFdDingUserId(creatorUnionId);
			record.setFdEKPDel(false);
			record.setFdUser((SysOrgPerson) sysOrgPersonService.findByPrimaryKey(ekpUserId));
			record.setFdNotifyId(todoId);
			record.setFdRecordId(recordId);
			record.setFdSubject(subject);
			addStatus = TransactionUtils.beginNewTransaction();
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
			String req,
			String ekpUserId, String method, String errorMsg,String unionId) {

		if(StringUtil.isNull(method)) {
            return;
        }
		method = PREFIX_API+method;

		// 添加之前判断是否有重复的，如果有重复，则只是更新errorMsg
		boolean updateFlag = false;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"fdTodoId=:fdTodoId and fdMethod=:fdMethod");
		hqlInfo.setParameter("fdTodoId", todo.getFdId());
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
		error.setFdDingUserId(unionId);
		error.setFdSubject(todo.getFdSubject());
		error.setFdAppName(todo.getFdAppName());
		error.setFdTodoId(todo.getFdId());
		error.setFdSendTime(new Date());
		error.setFdErrorMsg(API_TIP+errorMsg); //根据错误信息的开头来判断是否是新接口
		error.setFdMD5(generateMD5(method, todo.getFdId(),ekpUserId));
		error.setFdCreateTime(new Date());
		error.setFdMethod(method);
		error.setFdJson(req);
		error.setFdRepeatHandle(ThirdDingNotifyQueueErrorConstants.NOTIFY_ERROR_REPEAT);
		error.setFdFlag(ThirdDingNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_ERROR);
		TransactionStatus addStatus = null;
		try {
			if(StringUtil.isNotNull(ekpUserId)){
				error.setFdUser((SysOrgPerson) sysOrgPersonService.findByPrimaryKey(ekpUserId));
			}

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

	private ThirdDingDinstanceXform getInstanceXform(SysNotifyTodo todo) {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock(
				"fdEkpInstanceId=:fdEkpInstanceId and fdStatus=:fdStatus");
		hql.setParameter("fdEkpInstanceId", todo.getFdModelId());
		hql.setParameter("fdStatus", "20");
		try {
			return (ThirdDingDinstanceXform) getThirdDingDinstanceXformService()
					.findFirstOne(hql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/*
	 *  手动重发
	 */
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
			String[] data ={DingUtil.getUnionIdByEkpId(ids.split(";")[1])};

			return addTask(null, data, todo, wrurl);

		} else {
			logger.warn("推送待办到钉钉的开关已关闭或该待办的的类型不是1（待办）");
		}

		return false;
	}

	//------------------消息重发 start----------------

	public void add(JSONObject data, String unionId,String todoId,String ekpUserId,String subject) throws Exception {
		logger.debug("【新增】消息重发--"+data);
		logger.debug("【更新部分】消息重发--"+data);
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdNotifyId(todoId);
		log.setDocSubject(subject);
		Date start = new Date();
		log.setFdSendTime(start);
		log.setFdNotifyData(data.toString());
		log.setFdUrl("[POST]"+DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/tasks");
		try {
			TodoTask task = JSON.parseObject(data.toString(), TodoTask.class);
			JSONObject rs = DingUtils.getDingApiService().addTask(unionId, task);
			log.setFdRtnMsg(rs==null?null:rs.toString());
			if (rs != null && rs.containsKey("id")) {
				logger.debug("---新增待办成功-----" + subject);
				log.setFdResult(true);
				//添加映射表
				addWorkrecord(todoId, subject, unionId, ekpUserId, PREFIX_TODO_V2 + rs.getString("id"));
			} else {
				throw new RuntimeException("重发失败：" + rs);
			}
		}catch (Exception e){
			log.setFdResult(false);
			if(log.getFdRtnMsg()==null){
				log.setFdRtnMsg(e.getMessage());
			}
			throw e;
		}finally {
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

	public void update(JSONObject data, String unionId,String taskId,String ekpUserId,String subject) throws Exception {
		logger.debug("【更新】消息重发--"+data);
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdNotifyId(taskId);
		log.setDocSubject(subject);
		Date start = new Date();
		log.setFdSendTime(start);
		log.setFdNotifyData(data.toString());
		log.setFdUrl("[PUT]"+DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/tasks/"+taskId);
		try {
			TodoTask task = JSON.parseObject(data.toString(), TodoTask.class);
			String dingTaskId = getRecordIdByTodoId(taskId);
			String tempTaskId = dingTaskId.replace(PREFIX_TODO_V2, "").trim();
			JSONObject rs = DingUtils.getDingApiService().updateTask(unionId, tempTaskId, data);
			log.setFdRtnMsg(rs.toString());
			if (rs != null && rs.containsKey("result") && rs.getBoolean("result")) {
				//判断是否需要删除待办映射表
				if (data.containsKey("done") && data.getBoolean("done")) {
					logger.debug("---更新状态成功-----" + subject);
					log.setFdResult(true);
					//找到对照表
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setSelectBlock("fdId");
					hqlInfo.setWhereBlock("fdRecordId='" + dingTaskId + "'");
					String fdId = (String) thirdDingNotifyWorkrecordService.findFirstOne(hqlInfo);
					if (StringUtils.isNotBlank(fdId)) {
						thirdDingNotifyWorkrecordService.delete(fdId);
					}
				}else{
					log.setFdResult(false);
				}
			} else {
				throw new RuntimeException("重发失败：" + rs);
			}
		}catch (Exception e){
			log.setFdResult(false);
			if(log.getFdRtnMsg()==null){
				log.setFdRtnMsg(e.getMessage());
			}
			throw e;
		}finally {
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
	public void updateExecutorStatus(JSONObject data, String unionId,String taskId,String ekpUserId,String subject) throws Exception {
		logger.debug("【更新部分】消息重发--"+data);
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdNotifyId(taskId);
		log.setDocSubject(subject);
		Date start = new Date();
		log.setFdSendTime(start);
		log.setFdNotifyData(data.toString());
		log.setFdUrl("[PUT]"+DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/tasks/"+taskId+"/executorStatus");
		try {
			String dingTaskId = getRecordIdByTodoId(taskId);
			String tempTaskId = dingTaskId.replace(PREFIX_TODO_V2,"").trim();
			TodoTask task = JSON.parseObject(data.toString(),TodoTask.class);
			JSONObject rs = DingUtils.getDingApiService().updateExecutorStatus(unionId,tempTaskId,data);
			log.setFdRtnMsg(rs.toString());
			if(rs!=null&&rs.containsKey("result")&&rs.getBoolean("result")){
				log.setFdResult(true);
				logger.debug("---更新成功---"+subject);
			}else{
				throw new RuntimeException("重发失败："+rs);
			}
		}catch (Exception e){
			log.setFdResult(false);
			if(log.getFdRtnMsg()==null){
				log.setFdRtnMsg(e.getMessage());
			}
			throw e;
		}finally {
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

	public void deleteTask(String unionId,String taskId) throws Exception {
		logger.debug("【删除消息】消息重发--"+taskId);
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdNotifyId(taskId);
		Date start = new Date();
		log.setFdSendTime(start);
		log.setFdUrl("[DELETE]"+DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/tasks/"+taskId);
		try {
			ThirdDingNotifyWorkrecord thirdDingNotifyWorkrecord = getRecordByTodoId(taskId);
			log.setDocSubject(thirdDingNotifyWorkrecord.getFdSubject());
			String dingTaskId = thirdDingNotifyWorkrecord.getFdRecordId();
			String tempTaskId = dingTaskId.replace(PREFIX_TODO_V2,"").trim();
			JSONObject rs = DingUtils.getDingApiService().deleteTask(unionId,tempTaskId);
			if(rs!=null&&rs.containsKey("result")&&rs.getBoolean("result")){
				logger.debug("---删除待办成功-----");
				log.setFdResult(true);
				//删除映射表
				thirdDingNotifyWorkrecordService.delete(thirdDingNotifyWorkrecord);
			}else{
				throw new RuntimeException("重发失败："+rs);
			}
		}catch (Exception e){
			log.setFdResult(false);
			if(log.getFdRtnMsg()==null){
				log.setFdRtnMsg(e.getMessage());
			}
			throw e;
		}finally {
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

	/**
	 * 根据ekp的待办id查询钉钉的待办id
	 * @param notifyId
	 * @return
	 */
	private String getRecordIdByTodoId(String notifyId)throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdRecordId");
		hqlInfo.setWhereBlock("fdNotifyId='"+notifyId+"'");
		String recordId = (String) thirdDingNotifyWorkrecordService.findFirstOne(hqlInfo);
		if(StringUtils.isNotBlank(recordId)){
			return recordId;
		}else{
			throw new RuntimeException("根据ekp待办id:"+notifyId+" 找不到钉钉待办id");
		}
	}

	/**
	 * 根据ekp的待办id查询钉钉的待办id
	 * @param notifyId
	 * @return
	 */
	private ThirdDingNotifyWorkrecord getRecordByTodoId(String notifyId)throws Exception{
		ThirdDingNotifyWorkrecord record = (ThirdDingNotifyWorkrecord) thirdDingNotifyWorkrecordService.findFirstOne("fdNotifyId='"+notifyId+"'",null);
		if(record != null){
			return record;
		}else{
			throw new RuntimeException("根据ekp待办id:"+notifyId+" 找不到钉钉待办id");
		}
	}

	//------------------消息重发 end----------------

}


