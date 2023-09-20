package com.landray.kmss.sys.remind.service.spring;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction;
import com.landray.kmss.sys.lbpm.engine.integrate.rules.IRuleProvider;
import com.landray.kmss.sys.lbpm.engine.integrate.rules.RuleFact;
import com.landray.kmss.sys.lbpm.engine.manager.NoExecutionEnvironment;
import com.landray.kmss.sys.lbpm.engine.manager.ProcessServiceManager;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.sys.remind.constant.SysRemindConstant;
import com.landray.kmss.sys.remind.model.*;
import com.landray.kmss.sys.remind.service.ISysRemindMainService;
import com.landray.kmss.sys.remind.service.ISysRemindMainTaskLogService;
import com.landray.kmss.sys.remind.service.ISysRemindMainTaskService;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.util.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.*;

/**
 * 提醒中心
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindMainServiceImp extends BaseServiceImp
		implements ISysRemindMainService, ApplicationListener, SysRemindConstant {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private ISysRemindMainTaskService sysRemindMainTaskService;

	private ISysRemindMainTaskLogService sysRemindMainTaskLogService;

	private ISysQuartzCoreService sysQuartzCoreService;

	private ProcessServiceManager processServiceManager;

	private ISysOrgPersonService sysOrgPersonService;

	private ISysOrgCoreService sysOrgCoreService;

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysRemindMainTaskService(ISysRemindMainTaskService sysRemindMainTaskService) {
		this.sysRemindMainTaskService = sysRemindMainTaskService;
	}

	public void setSysRemindMainTaskLogService(ISysRemindMainTaskLogService sysRemindMainTaskLogService) {
		this.sysRemindMainTaskLogService = sysRemindMainTaskLogService;
	}

	public void setSysQuartzCoreService(ISysQuartzCoreService sysQuartzCoreService) {
		this.sysQuartzCoreService = sysQuartzCoreService;
	}

	public void setProcessServiceManager(ProcessServiceManager processServiceManager) {
		this.processServiceManager = processServiceManager;
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setSysNotifyMainCoreService(ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		return super.convertFormToModel(form, model, requestContext);
	}

	@Override
	public void createRemindTask(SysRemindMain main, IBaseModel model) throws Exception {
		try {
			// 规则提供器
			IRuleProvider ruleProvider = getRuleProvider(model);
			// 条件过滤
			if (BooleanUtils.isTrue(main.getFdIsFilter())) {
				if (!filter(ruleProvider, model, main.getFdConditionId())) {
					// 条件不匹配，无需提醒
					return;
				}
			}
			// 解析标题
			RuleFact title = new RuleFact(model);
			String subject = main.getFdSubjectId();
			if (StringUtil.isNull(subject)) {
				// 无标题，不提醒
				logger.warn("没有找到待办标题，不提醒。");
				return;
			}
			if (!subject.contains("$")) {
				if (!subject.startsWith("\"")) {
					subject = "\"" + subject;
				}
				if (!subject.endsWith("\"")) {
					subject = subject + "\"";
				}
			}
			title.setScript(subject);
			title.setReturnType("String");
			Object value = ruleProvider.executeRules(title);
			// 保存任务
			saveTask(main, model, value.toString());
		} catch (Exception e) {
			logger.error("公式解析处理人出错", e);
			throw e;
		}
	}

	/**
	 * 获取规则提供器
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private IRuleProvider getRuleProvider(IBaseModel model) throws Exception {
		// 规则提供器
		IRuleProvider ruleProvider = processServiceManager.getRuleService().getRuleProvider(new NoExecutionEnvironment(model));
		// 追加解析器
		ruleProvider.addRuleParser(LbpmFunction.class.getName());
		return ruleProvider;
	}

	/**
	 * 条件过滤
	 * 
	 * @param ruleProvider
	 * @param main
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private boolean filter(IRuleProvider ruleProvider, IBaseModel model, String condition) throws Exception {
		boolean isFilter = false;
		// 规则事实参数
		RuleFact filter = new RuleFact(model);
		filter.setScript(condition);
		filter.setReturnType("Boolean");
		Object value = ruleProvider.executeRules(filter);
		if ("true".equals(value.toString())) {
			// 条件过滤
			isFilter = true;
		}
		return isFilter;
	}

	/**
	 * 保存定时任务
	 * 
	 * @param main
	 * @throws Exception
	 */
	private void saveTask(SysRemindMain main, IBaseModel model, String subject) throws Exception {
		List<SysRemindMainTrigger> triggers = main.getFdTriggers();
		if (CollectionUtils.isNotEmpty(triggers)) {
			// 获取发送人
			SysOrgPerson sender = getSender(main, model);
			if (sender == null) {
				return;
			}
			// 获取接收人
			List<SysOrgElement> receivers = getReceivers(main.getFdReceivers(), model);
			if (CollectionUtils.isEmpty(receivers)) {
				return;
			}
			for (SysRemindMainTrigger trigger : triggers) {
				/**
				 * 每个触发时间都会生成一个提醒任务，该任务只记录：提醒时间，待办标题，业务主文档，提醒配置
				 * <p>
				 * 注意：定时任务在执行时，会获取当前提醒配置信息，根据最新的提醒配置进行过滤和取值，当业务主文档或提醒配置有更新时，可能会出现与最初的提醒配置不匹配
				 * <p>
				 * 后续如果需要做更详细的提醒，需要在提醒任务中记录当前的提醒配置
				 */
				SysRemindMainTask task = new SysRemindMainTask();
				task.setFdId(IDGenerator.generateID());
				task.setFdRemindId(main.getFdId());
				task.setFdSubject(subject);
				task.setFdModelId(model.getFdId());
				task.setFdModelName(ModelUtil.getModelClassName(model));

				Date date = null;
				boolean isNow = false;
				if (FIELD_TYPE_AFTER_PUBLISH.equals(trigger.getFdFieldId())
						|| FIELD_PUBLISH_TIME.equals(trigger.getFdFieldId())) {
					// 流程发布后提醒，这里不设置提醒时间
					task.setFdTriggerId(trigger.getFdId());
					task.setFdKey(FIELD_TYPE_AFTER_PUBLISH);
				} else {
					if (FIELD_TYPE_AFTER_SUBMIT.equals(trigger.getFdFieldId())
							|| FIELD_CREATE_TIME.equals(trigger.getFdFieldId())) {
						// 流程提交后提醒
						if (TRIGGER_MODE_TIME.equals(trigger.getFdMode())) {
							isNow = true;
						}
						date = new Date();
					}
					// 获取触发时间
					Date runTime = getTrigger(trigger, model, date);
					if (runTime == null) {
						continue;
					} else {
						// 判断运行时间，如果已过时就不处理
						if (!isNow && new Date().after(runTime)) {
							logger.warn("【提醒中心】运行时间[" + DateUtil.convertDateToString(runTime, DateUtil.PATTERN_DATETIME)
									+ "]已经过时了，本次不提醒，标题：" + subject);
							continue;
						}
					}
					task.setFdRunTime(runTime);
				}

				// 保存提醒任务
				sysRemindMainTaskService.add(task);
				if (task.getFdRunTime() != null) {
					saveJob(task, task.getFdRunTime(), subject, model);
				}
			}
		}
	}

	/**
	 * 保存定时任务
	 * 
	 * @param task
	 * @param runTime
	 * @param subject
	 * @throws Exception
	 */
	private void saveJob(SysRemindMainTask task, Date runTime, String subject, IBaseModel model) throws Exception {
		SysQuartzModelContext quartzContext = new SysQuartzModelContext();
		quartzContext.setQuartzJobMethod("remindJob");
		quartzContext.setQuartzJobService("sysRemindMainService");
		quartzContext.setQuartzKey("remindJob");
		JSONObject parameter = new JSONObject();
		parameter.put("taskId", task.getFdId());
		quartzContext.setQuartzParameter(parameter.toString());
		// 定时任务标题
		quartzContext.setQuartzSubject("【" + ResourceUtil.getString("sys-remind:module.sys.remind") + "】" + subject);
		// 定时任务执行时间
		quartzContext.setQuartzCronExpression(getCronExpression(runTime));
		quartzContext.setQuartzRequired(true); // 必须执行
		String url = ModelUtil.getModelUrl(model, ModelUtil.getModelClassName(model));
		if (StringUtil.isNotNull(url)) {
			quartzContext.setQuartzLink(url);
		}

		// 生成定时任务
		sysQuartzCoreService.saveScheduler(quartzContext, task);
	}

	/**
	 * 解析触发时间
	 * 
	 * @param trigger
	 * @param model
	 * @param date
	 * @return
	 * @throws Exception
	 */
	private Date getTrigger(SysRemindMainTrigger trigger, IBaseModel model, Date date) throws Exception {
		// 日期时间 + “之前/之后”： XX天，XX时，XX分
		// 日期时间 + “当天”：时间
		// 日期 + “当天”：时间
		// 日期 + “之前/之后”：XX天，时间
		// 日期时间 + “当时”：
		String field = trigger.getFdFieldId();
		// model属性
		if (PropertyUtils.isReadable(model, field)) {
			Object object = PropertyUtils.getProperty(model, field);
			if (object instanceof Date) {
				date = (Date) object;
			}
		}
		if (date == null) {
			if (model instanceof IExtendDataModel) {
				Map<String, Object> modelData = ((IExtendDataModel) model).getExtendDataModelInfo().getModelData();
				Object object = modelData.get(field);
				if (object instanceof Date) {
					date = (Date) object;
				}
			}
		}
		if (date == null) {
			return null;
		}
		if (TRIGGER_MODE_TIME.equals(trigger.getFdMode())) {
			// 当时
			return date;
		} else if (TRIGGER_MODE_DAY.equals(trigger.getFdMode())) {
			// 当天，需要加上时间
			String time = trigger.getFdTime();
			if (StringUtil.isNull(time)) {
				time = "00:00";
			}
			String newDate = DateUtil.convertDateToString(date, DATE_PATTERN) + " " + time;
			return DateUtil.convertStringToDate(newDate, DATETIME_PATTERN);
		} else if (TRIGGER_MODE_BEFORE.equals(trigger.getFdMode()) || TRIGGER_MODE_AFTER.equals(trigger.getFdMode())) {
			boolean isAfter = true;
			if (TRIGGER_MODE_BEFORE.equals(trigger.getFdMode())) {
				isAfter = false;
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			// 之前、之后
			Integer day = trigger.getFdDay();
			if (isAfter) {
				cal.add(Calendar.DAY_OF_MONTH, day);
			} else {
				cal.add(Calendar.DAY_OF_MONTH, -day);
			}
			Integer hour = trigger.getFdHour();
			Integer minute = trigger.getFdMinute();
			String time = trigger.getFdTime();
			if (StringUtil.isNotNull(time)) {
				String newDate = DateUtil.convertDateToString(cal.getTime(), DATE_PATTERN) + " " + time;
				return DateUtil.convertStringToDate(newDate, DATETIME_PATTERN);
			} else {
				if (isAfter) {
					cal.add(Calendar.HOUR, hour);
					cal.add(Calendar.MINUTE, minute);
				} else {
					cal.add(Calendar.HOUR, -hour);
					cal.add(Calendar.MINUTE, -minute);
				}
			}
			return cal.getTime();
		}
		return null;
	}

	/**
	 * 解析发送人
	 * 
	 * @param main
	 * @return
	 * @throws Exception
	 */
	private SysOrgPerson getSender(SysRemindMain main, IBaseModel model) throws Exception {
		try {
			if (FIELD_TYPE_XFORM.equals(main.getFdSenderType())) {
				List<SysOrgElement> list = getByXform(model, main.getFdSenderId());
				if (CollectionUtils.isNotEmpty(list)) {
					SysOrgElement element = list.get(0);
					if (element.getFdOrgType().equals(SysOrgConstant.ORG_TYPE_PERSON)) {
						return (SysOrgPerson) sysOrgCoreService.format(element);
					}
				}
			} else {
				// 地址本字段
				return (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(main.getFdSenderId());
			}
		} catch (Exception e) {
			logger.warn("无法解析发送人：", e);
		}
		return null;
	}

	/**
	 * 获取接收者
	 * 
	 * @param receivers
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private List<SysOrgElement> getReceivers(List<SysRemindMainReceiver> receivers, IBaseModel model) throws Exception {
		List<SysOrgElement> elements = new ArrayList<SysOrgElement>();
		for (SysRemindMainReceiver receiver : receivers) {
			if (FIELD_TYPE_XFORM.equals(receiver.getFdType())) {
				// 表单字段
				List<SysOrgElement> list = getByXform(model, receiver.getFdReceiverId());
				if (CollectionUtils.isNotEmpty(list)) {
					elements.addAll(list);
				}
			} else {
				// 地址本字段
				elements.addAll(receiver.getFdReceiverOrgs());
			}
		}
		return elements;
	}

	/**
	 * 获取表单字段的人员信息
	 * 
	 * @param model
	 * @param field
	 * @return
	 * @throws Exception
	 */
	private List<SysOrgElement> getByXform(IBaseModel model, String field) throws Exception {
		List<SysOrgElement> list = new ArrayList<SysOrgElement>();
		// model属性
		if (PropertyUtils.isReadable(model, field)) {
			Object object = PropertyUtils.getProperty(model, field);
			if (object instanceof SysOrgElement) {
				list.add((SysOrgElement) object);
			}
		}
		// 表单字段
		if (model instanceof IExtendDataModel) {
			Map<String, Object> modelData = ((IExtendDataModel) model).getExtendDataModelInfo().getModelData();
			Object object = modelData.get(field);
			if (object instanceof SysOrgElement) {
				list.add((SysOrgElement) object);
			} else if (object instanceof List) {
				List temp = (List) object;
				for (Object obj : temp) {
					if (obj instanceof SysOrgElement) {
						list.add((SysOrgElement) obj);
					}
				}
			} else if (object instanceof Map) {
				Map<String, String> temp = (Map<String, String>) object;
				if (temp.containsKey("id")) {
					String[] ids = temp.get("id").split(";");
					List elems = sysOrgCoreService.findByPrimaryKeys(ids);
					if (CollectionUtils.isNotEmpty(elems)) {
						list.addAll(elems);
					}
				}
			}
		}
		return list;
	}

	/**
	 * cronExpression表达式
	 */
	private String getCronExpression(Date date) throws Exception {
		StringBuffer cronExpression = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		cronExpression.append("0 ").append(calendar.get(Calendar.MINUTE) + " ")
				.append(calendar.get(Calendar.HOUR_OF_DAY) + " ").append(calendar.get(Calendar.DAY_OF_MONTH) + " ")
				.append((calendar.get(Calendar.MONTH) + 1) + " ").append("? " + calendar.get(Calendar.YEAR));
		return cronExpression.toString();
	}

	@Override
	public void remindJob(SysQuartzJobContext context) throws Exception {
		// 发送待办通知
		JSONObject param = JSONObject.parseObject(context.getParameter());
		String taskId = (String) param.get("taskId");
		SysRemindMainTask task = (SysRemindMainTask) sysRemindMainTaskService.findByPrimaryKey(taskId);
		if (task != null) {
			String mainId = task.getFdRemindId();
			String subject = task.getFdSubject();
			String modelId = task.getFdModelId();
			String modelName = task.getFdModelName();

			String errorMsg = null;
			// 获取提醒配置
			SysRemindMain remindMan = (SysRemindMain) findByPrimaryKey(mainId);
			if (remindMan == null) {
				errorMsg = "提醒配置不存在[fdId=" + mainId + "]";
			} else if (!BooleanUtils.isTrue(remindMan.getFdIsEnable())) {
				errorMsg = "提醒配置已禁用[" + remindMan.getFdName() + "]";
			}
			// 获取业务主文档
			IBaseModel model = getBaseDao().findByPrimaryKey(modelId, modelName, true);
			if (model == null) {
				errorMsg = "业务主文档不存在[modelId=" + modelId + ",modelName=" + modelName + "]";
			}
			// 提醒过滤
			if (StringUtil.isNull(errorMsg)) {
				// 条件过滤
				if (BooleanUtils.isTrue(remindMan.getFdIsFilter())) {
					// 规则提供器
					IRuleProvider ruleProvider = getRuleProvider(model);
					if (!filter(ruleProvider, model, remindMan.getFdConditionId())) {
						// 条件不匹配，无需提醒
						errorMsg = "提醒条件不满足，当前条件[" + remindMan.getFdConditionName() + "]，业务主文档[modelId=" + modelId
								+ ",modelName=" + modelName + "]";
					}
				}
			}
			SysOrgPerson sender = null;
			List<SysOrgElement> receivers = null;
			if (StringUtil.isNull(errorMsg)) {
				sender = getSender(remindMan, model);
				if (sender == null) {
					errorMsg = "无法获取发送人";
				}
				receivers = getReceivers(remindMan.getFdReceivers(), model);
				if (CollectionUtils.isEmpty(receivers)) {
					errorMsg = "无法获取接收人";
				}
			}

			if (StringUtil.isNull(errorMsg)) {
				// 获取主文档查看链接
				String url = ModelUtil.getModelUrl(model, modelName);
				String[] notifyTypes = remindMan.getFdNotifyType().split(";");
				List<SysOrgPerson> receiverPersons = sysOrgCoreService.expandToPerson(receivers);
				// 按接收人分别发送
				for (SysOrgPerson receiver : receiverPersons) {
					// 按待办类型分别发送
					for (String notifyType : notifyTypes) {
						logger.info("提醒中心发送消息 -> 消息类型：" + notifyType + "，发送人：" + sender.getFdName() + "，接收人："
								+ receiver.getFdName() + "，发送标题：" + subject);
						// 获取待办上下文
						NotifyContext notifyContext = sysNotifyMainCoreService.getContext("sys-remind:module.sys.remind");
						// 获取通知方式
						notifyContext.setNotifyType(notifyType);
						notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
						List targets = new ArrayList();
						targets.add(receiver);
						notifyContext.setNotifyTarget(targets);
						notifyContext.setSubject(subject);
						notifyContext.setLink(url);
						notifyContext.setDocCreator(sender);
						// 记录任务日志
						SysRemindMainTaskLog taskLog = new SysRemindMainTaskLog();
						taskLog.setFdTask(task);
						taskLog.setFdCreateTime(new Date());
						taskLog.setFdNotifyType(notifyType);
						taskLog.setFdSender(sender);
						taskLog.setFdReceiver(receiver);
						try {
							sysNotifyMainCoreService.sendNotify(task, notifyContext, null);
							taskLog.setFdIsSuccess(true);
						} catch (Exception e) {
							logger.warn("提醒中心发送待办失败：", e);
							taskLog.setFdIsSuccess(false);
							taskLog.setFdMessage(e.getMessage());
						}
						sysRemindMainTaskLogService.add(taskLog);
					}
				}
				// 增加接收者阅读权限
				addAuth(model, receivers);
			} else {
				// 记录任务错误日志
				SysRemindMainTaskLog taskLog = new SysRemindMainTaskLog();
				taskLog.setFdTask(task);
				taskLog.setFdCreateTime(new Date());
				taskLog.setFdIsSuccess(false);
				taskLog.setFdMessage(errorMsg);
				sysRemindMainTaskLogService.add(taskLog);
			}
		} else {
			logger.warn("【提醒中心】提醒任务不存在，本次任务结束。[fdId=" + taskId + "]");
		}
	}

	/**
	 * 给接收者增加阅读权限
	 * 
	 * @param model
	 * @param receivers
	 * @throws Exception
	 */
	private void addAuth(IBaseModel model, List<SysOrgElement> receivers) throws Exception {
		if (model instanceof BaseAuthModel) {
			// 增加可阅读者权限
			BaseAuthModel authModel = (BaseAuthModel) model;
			ArrayUtil.concatTwoList(receivers, authModel.getAuthOtherReaders());
			// 获取业务服务类进行更新
			String modelName = ModelUtil.getModelClassName(model);
			String serviceName = SysDataDict.getInstance().getModel(modelName).getServiceBean();
			IBaseService service = (IBaseService) SpringBeanUtil.getBean(serviceName);
			if (service != null) {
				service.getBaseDao().update(authModel);
			} else {
				logger.warn("无法获取主文档Service，modelName=" + modelName);
			}
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
            return;
        }
		if (event instanceof Event_SysFlowFinish) {
			Object obj = event.getSource();
			if(obj instanceof IBaseModel) {
				try {
					IBaseModel model = (IBaseModel) obj;
					// 获取流程发布的提醒任务
					List<SysRemindMainTask> tasks = sysRemindMainTaskService.getByModel(model.getFdId(),
							ModelUtil.getModelClassName(model), FIELD_TYPE_AFTER_PUBLISH);
					if (CollectionUtils.isNotEmpty(tasks)) {
						for (SysRemindMainTask task : tasks) {
							SysRemindMainTrigger trigger = (SysRemindMainTrigger) sysRemindMainTaskService
									.findByPrimaryKey(task.getFdTriggerId(), SysRemindMainTrigger.class.getName(), true);
							// 获取触发时间
							Date runTime = getTrigger(trigger, model, new Date());
							if (runTime == null) {
								continue;
							}
							task.setFdRunTime(runTime);
							sysRemindMainTaskService.update(task);
							saveJob(task, task.getFdRunTime(), task.getFdSubject(), model);
						}
					}
				} catch (Exception e) {
					logger.warn("触发提醒任务失败：", e);
				}
			}
		}
	}

}
