package com.landray.kmss.sys.handover.support.config.doc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.sys.ftsearch.apache.commons.collections4.CollectionUtils;
import com.landray.kmss.sys.lbpm.engine.builder.Instance;
import com.landray.kmss.sys.lbpm.engine.builder.ProcessInstance;
import com.landray.kmss.sys.lbpm.engine.builder.TaskInstance;
import com.landray.kmss.sys.lbpm.engine.exception.OperationCheckFailureExcepiton;
import com.landray.kmss.sys.lbpm.engine.integrate.notify.ILbpmNotifyService;
import com.landray.kmss.sys.lbpm.engine.integrate.notify.NotifyContext;
import com.landray.kmss.sys.lbpm.engine.integrate.notify.NotifyTextHolder;
import com.landray.kmss.sys.lbpm.engine.manager.ProcessServiceManager;
import com.landray.kmss.sys.lbpm.engine.manager.TempParameterKeys;
import com.landray.kmss.sys.lbpm.engine.manager.node.NodeTypeManager;
import com.landray.kmss.sys.lbpm.engine.manager.operation.ExecuteResult;
import com.landray.kmss.sys.lbpm.engine.manager.operation.OperationCheckerParameters;
import com.landray.kmss.sys.lbpm.engine.manager.operation.OperationParameters;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmExpecterLog;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmWorkitem;
import com.landray.kmss.sys.lbpm.engine.service.ProcessDefinitionService;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo;
import com.landray.kmss.sys.lbpmservice.operation.admin.LbpmAdminChangeCurHandlerBehaviour;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNote;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmSettingDefault;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmWorkingproxy;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 扩展的工作交接 系统修当前处理人behavior
 * 
 * @author nemo
 *
 */
public class HandLbpmAdminChangeCurHandlerBehaviour extends LbpmAdminChangeCurHandlerBehaviour{
	@Override
    protected String getActionNameForOpinion(TaskExecutionContext context,
                                             OperationParameters parameters) {
		return "{0}-{1}%%sys-lbpmservice-operation-admin:lbpmProcess.processor.identity.authority;"
				+ "sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.processor.modifyhandler";
	}

	private List<String> getSelectedTaskIds(OperationParameters parameters) {
		String taskIds = getParamStringValue(parameters, "taskIds");
		return Arrays.asList(taskIds.split(";"));
	}

	@Override
    public ExecuteResult execute(TaskExecutionContext context,
                                 OperationParameters parameters) throws Exception {

		List<LbpmExpecterLog> lbmpEpecterLogs= getExpecterLogs(context);

		if(CollectionUtils.isEmpty(lbmpEpecterLogs)) {
			return getExecuteResult(context, parameters);
		}

		//被交接人，必须要有，没有不做后面逻辑处理
		List<SysOrgElement> replaceElements=getReplaceElements(context, parameters);

		if(CollectionUtils.isEmpty(replaceElements)) {
			return getExecuteResult(context, parameters);
		}
		String repHandlerType = getParamStringValue(parameters,
				"repHandlerType");

		boolean isRepalce = StringUtil.isNull(repHandlerType)
				|| "replace".equals(repHandlerType);

		// 获取选中的工作项ID集
		List<String> selectedTaskIds = getSelectedTaskIds(parameters);

		// 将选择的工作项集和当前节点和当前流程查询出来的预计处理人日志比较，
		//taskId是一致，才去替换
		List<LbpmExpecterLog> epecterLogs=new ArrayList<LbpmExpecterLog>();
		//选择的工作项和预计处理人日志比较
		for (LbpmExpecterLog expecterLog : lbmpEpecterLogs) {
			if (selectedTaskIds.contains(expecterLog.getFdTaskId())) {
				epecterLogs.add(expecterLog);
			}
		}

		/*// 去替换的工作项集
		List<LbpmWorkitem> replaceWorkitems = new ArrayList<LbpmWorkitem>();
		// 执行流程定义替换处理人操作
		super.replaceNodeHandlersInInstance(context, selectedTaskIds, epecterLogs,
				parameters, replaceWorkitems);*/
		// 更新节点运行时处理人集
		ProcessDefinitionService processDefinitionService = (ProcessDefinitionService)SpringBeanUtil
				.getBean("lbpmProcessDefinitionService");
		processDefinitionService.replaceProcessInstanceNodeHandlers(context
				.getProcessInstance(), context.getNodeInstance()
				.getFdFactNodeId(), replaceElements);

		if(CollectionUtils.isEmpty(epecterLogs)) {
			return getExecuteResult(context, parameters);
		}

		// 通知选中的工作项集的相关处理人
		//根据开关是否要通知当前处理人
		String isNotifyCurrentHandler =  new LbpmSetting().getIsNotifyCurrentHandler();
		if ("true".equals(isNotifyCurrentHandler) && isRepalce) {
			//notifySelectedWorkitemHandlers(context, selectedTaskIds, epecterLogs);
		}

		for(int i=0;i<epecterLogs.size();i++) {
			LbpmExpecterLog log=epecterLogs.get(0);

			recordAuditNote(log, replaceElements.get(0), context);

			LbpmWorkitem lbpmWorkitem = context.getAccessManager().get(LbpmWorkitem.class,
					log.getFdTaskId());
			//addToProxy(lbpmWorkitem, log, replaceElements.get(0), context);

			log.setFdHandler(replaceElements.get(0));
			log.setFdHandlerType(
					LbpmExpecterLog.HANLDER_TYPE_HANDOVER);
			// 更新交接人
			log.setFdAuthId(lbpmWorkitem.getFdExpecter().getFdId());
			context.getAccessManager().update(log);

			SysOrgElement replaceElement=replaceElements.get(0);
			addToReaderRight(log, replaceElement, context);
		}

		return getExecuteResult(context, parameters);
	}


	private String getActionName() {
		StringBuffer result = new StringBuffer("{0}-{1}%%");
		result.append("sys-lbpmservice:lbpmProcess.processor.identity.system;");
		result.append("sys-lbpmservice:lbpmNode.opInfo.handover");
		return result.toString();
	}

	private String getActionDescription(String authorizeDesc,
										String authorizedDesc) {
		StringBuffer result = new StringBuffer();
		result.append("sys-lbpmservice-operation-admin:lbpmNode.opInfo.authorize.authority");
		result.append("%%").append(authorizeDesc);
		result.append("##").append(authorizedDesc);
		return result.toString();
	}

	private String[] getNotifyParamtersByTask(Instance task) {
		String[] values = new String[3];
		if (task instanceof ProcessInstance) {
			values[0] = task.getFdId();
			values[1] = null;
			values[2] = null;
		} else if (task instanceof TaskInstance) {
			TaskInstance taskInstance = (TaskInstance) task;
			values[0] = taskInstance.getFdProcessInstanceId();
			values[1] = taskInstance.getFdNode().getFdId();
			// 判断如果是工作项
			if (!taskInstance.getFdNode().getFdId().equals(task.getFdId())) {
				values[2] = task.getFdId();
			}
		}
		return values;
	}

	private void recordAuditNote(
			LbpmExpecterLog log, SysOrgElement expecter,TaskExecutionContext context) {

		ProcessExecuteService processExecuteService = (ProcessExecuteService) SpringBeanUtil
				.getBean("lbpmProcessExecuteService");

		ILbpmNotifyService lbpmNotifyService=(ILbpmNotifyService) SpringBeanUtil
				.getBean("lbpmNotifyService");

		ISysNotifyTodoService sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil
				.getBean("sysNotifyTodoService");


		ProcessInstanceInfo load = processExecuteService
				.load(log.getFdProcessId());
		ProcessInstance processInstance = load.getProcessInstance();

		LbpmWorkitem lbpmWorkitem = context.getAccessManager().get(LbpmWorkitem.class,
				log.getFdTaskId());

		String authorizeDesc = log.getFdHandler().getFdName();
		String authorizedDesc=expecter.getFdName();

		// 增加审批记录
		LbpmAuditNote note = new LbpmAuditNote();
		note.setFdFactNodeId(lbpmWorkitem.getFdNode().getFdActivityId());
		note.setFdFactNodeName(lbpmWorkitem.getFdNode().getFdFactNodeName());
		note.setFdCreateTime(new Date());
		note.setFdProcess((LbpmProcess) processInstance);
		note.setFdActionName(getActionName());
		note.setFdActionInfo(
				getActionDescription(authorizeDesc, authorizedDesc));

		context.getAccessManager().save(note);


		// 发送待办
		try {
			IBaseModel mainModel = context.getMainModel();

			String[] params = getNotifyParamtersByTask(lbpmWorkitem);
			List<SysNotifyTodo> todos = sysNotifyTodoService.getCoreModels(
					mainModel,
					params[0], params[1], params[2]);

			for (SysNotifyTodo notifyTodo : todos) {
				if (!"1".equals(notifyTodo.getFdDeleteFlag()) && (notifyTodo
						.getFdType() == SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL || notifyTodo
						.getFdType() == SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL_SUSPEND)) {//添加暂挂流程工作交接发送代办问题处理 #165372
					List<SysOrgElement> hbmTodoTargets = notifyTodo
							.getHbmTodoTargets();



					List<SysOrgElement> list = new ArrayList<>();
					list.add(log.getFdHandler());
					lbpmNotifyService.setPersonsDone(mainModel, list,
							lbpmWorkitem);
					LbpmSettingDefault defaultInfo = new LbpmSettingDefault();

					List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
					targets.add(expecter);
					NotifyContext notifyContext = new NotifyContext(
							mainModel, targets,
							"sys-lbpmservice-operation-admin:lbpmAuthorize.authorize.authority.notify",
							defaultInfo.getDefaultNotifyType(),
							SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL,
							lbpmWorkitem,
							""
									+ notifyTodo.getFdLevel());
					notifyContext.putReplaceText("authorizer",
							new NotifyTextHolder(
									log.getFdHandler()
											.getFdName(),
									"$org.fdName$",
									log.getFdHandler()));
					lbpmNotifyService.send(notifyContext);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addToProxy(LbpmWorkitem lbpmWorkitem,
							LbpmExpecterLog log, SysOrgElement expecter, TaskExecutionContext context) {
		LbpmWorkingproxy proxy = new LbpmWorkingproxy();
		proxy.setFdProcess(lbpmWorkitem.getFdProcess());
		proxy.setFdFactNodeId(lbpmWorkitem.getFdNode().getFdFactNodeId());
		proxy.setFdByproxy(expecter);
		proxy.setFdProxy(log.getFdHandler());
		proxy.setFdExpecter(expecter);
		proxy.setFdTaskId(lbpmWorkitem.getFdId());
		context.getAccessManager().save(proxy);
	}


	private void addToReaderRight(LbpmExpecterLog log,
								  SysOrgElement replaceElement,TaskExecutionContext context) throws Exception {
		ProcessServiceManager serviceManager = (ProcessServiceManager) SpringBeanUtil
				.getBean("lbpmProcessServiceManager");
		IBaseModel mainModel = context.getMainModel();
		List<SysOrgElement> readers = new ArrayList<>();
		readers.add(replaceElement);
		serviceManager.getMainModelPerstenceService()
				.addMainModelReadRight(mainModel, readers);
	}

	@Override
    public void checkData(OperationCheckerParameters checkParam,
                          OperationParameters operParam)
			throws OperationCheckFailureExcepiton {
		String param = operParam.getParameter();
		String repHandlerType = getParamStringValue(param, "repHandlerType");
		if(StringUtil.isNull(repHandlerType) || "replace".equals(repHandlerType)){
			if (StringUtil.isNull(getParamStringValue(param, "taskIds"))) {
				throw new OperationCheckFailureExcepiton("操作参数没有被修改处理人");
			}

			if (StringUtil.isNull(getParamStringValue(param, "repHandlerIds"))) {
				throw new OperationCheckFailureExcepiton("操作参数没有被替换处理人工作项ID数组");
			}
		} else {
			if (StringUtil.isNull(getParamStringValue(param, "repHandlerIds"))) {
				throw new OperationCheckFailureExcepiton("操作参数没有追加处理人工作项ID数组");
			}
		}
	}


	/**
	 * @param context
	 * @param parameters
	 * @return 去替换的人员集
	 */
	private List<SysOrgElement> getReplaceElements(
			TaskExecutionContext context, OperationParameters parameters) {
		String replaceIds = getParamStringValue(parameters, "repHandlerIds");
		return context.getServiceManager().getOrgResolverService()
				.getOrgs(replaceIds);
	}

	private String getWorkitemType(TaskExecutionContext context) {
		String type = context.getNodeInstance().getFdActivityType();
		String modelName = context.getExecuteParameters().getModelName();
		NodeTypeManager manager = NodeTypeManager.getInstance();
		return manager.getType(type, modelName).getSubTaskType();
	}

	private List<LbpmExpecterLog> getExpecterLogs(TaskExecutionContext context) {
		Map<String, Object> paramInfo = new HashMap<String, Object>();
		paramInfo.put("fdProcessId", context.getProcessInstance().getFdId());
		paramInfo.put("fdFactId", context.getNodeInstance().getFdActivityId());

		// 查询...
		List<LbpmExpecterLog> expecterLogs = context.getAccessManager().find(
				"LbpmExpecterLog.findLogByNodeFactId", paramInfo);
		if (expecterLogs == null) {
			return new ArrayList<LbpmExpecterLog>();
		}
		return expecterLogs;
	}


	/**
	 * 通知选中的工作项集的相关处理人
	 * 
	 * @param context
	 * @param selectedWorkitemElements
	 */
	private void notifySelectedWorkitemHandlers(TaskExecutionContext context,
			List<String> selectedTaskIds, List<LbpmExpecterLog> expecterLogs) {

		List<SysOrgElement> selectedWorkitemElements = getSelectedWorkitemHandlers(
				expecterLogs, selectedTaskIds);

		String msgKey = "sys-lbpmservice-operation-admin:lbpmProcess.privileger.modify.notify.handover";
		// 创建代办上下文并发送
		NotifyContext notifyContext = new NotifyContext(context.getMainModel(),
				selectedWorkitemElements, msgKey, context.getNotifyType(),
				SysNotifyConstant.NOTIFY_TODOTYPE_ONCE, context.getTask(),
				context.getNotifyLevel());

		context.getProcessParameters().setInstanceParamValue(
				context.getProcessInstance(), TempParameterKeys.NOTIFY_LEVEL,
				context.getNotifyLevel());

		context.getServiceManager().getNotifyService().send(notifyContext);
	}
	
	private List<SysOrgElement> getSelectedWorkitemHandlers(
			List<LbpmExpecterLog> expecterLogs, List<String> selectedTaskIds) {
		List<SysOrgElement> handlers = new ArrayList<SysOrgElement>();
		for (LbpmExpecterLog expecterLog : expecterLogs) {
			if (selectedTaskIds.contains(expecterLog.getFdTaskId())) {
				handlers.add(expecterLog.getFdHandler());
			}
		}
		return handlers;
	}

}
