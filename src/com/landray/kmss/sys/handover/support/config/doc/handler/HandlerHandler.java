package com.landray.kmss.sys.handover.support.config.doc.handler;

import java.util.Arrays;
import java.util.List;

import com.landray.kmss.sys.handover.support.util.ListSplitUtil;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmHistoryWorkitem;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmWorkitem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLogDetail;
import com.landray.kmss.sys.handover.support.config.doc.AbstractDocHandler;
import com.landray.kmss.sys.handover.support.util.HandModelUtil;
import com.landray.kmss.sys.handover.support.util.ProcessNodeUtil;
import com.landray.kmss.sys.lbpm.engine.integrate.expecterlog.ILbpmExpecterLogService;
import com.landray.kmss.sys.lbpm.engine.manager.operation.OperationParameters;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmExpecterLog;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmNode;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.service.ExecuteParameters;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpmservice.constant.LbpmConstants;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmSettingDefault;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.lbpmservice.support.util.LbpmTemplateUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

/**
 * 当前处理人执行器
 * 思路：1、查询：以流程实例为单位
 * 		2、处理：需要注意调用执行接口，对待办需要重新发送
 * 		3、处理的时候会执行操作权限校验：AbstractOperationBehaviour checkUser，因此需要写一个system操作进行跳过权限校验
 * @author tanyh
 *
 */
public class HandlerHandler extends AbstractDocHandler {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HandlerHandler.class);
	/** 流程处理人日志服务 */
	ILbpmExpecterLogService lbpmExpecterLogService = null;

	public ILbpmExpecterLogService getLbpmExpecterLogService() {
		if (lbpmExpecterLogService == null) {
			lbpmExpecterLogService = (ILbpmExpecterLogService) SpringBeanUtil.getBean("lbpmExpecterLogService");
		}
		return lbpmExpecterLogService;
	}

	ILbpmProcessService lbpmProcessService = null;

	public ILbpmProcessService getLbpmProcessService() {
		if (lbpmProcessService == null) {
			lbpmProcessService = (ILbpmProcessService) SpringBeanUtil.getBean("lbpmProcessService");
		}
		return lbpmProcessService;
	}

	/** 流程运行服务 */
	private ProcessExecuteService lbpmProcessExecuteService = null;

	public ProcessExecuteService getLbpmProcessExecuteService() {
		if (lbpmProcessExecuteService == null) {
			lbpmProcessExecuteService = (ProcessExecuteService) SpringBeanUtil.getBean("lbpmProcessExecuteService");
		}
		return lbpmProcessExecuteService;
	}

	IBaseDao baseDao = null;

	@Override
	public IBaseDao getBaseDao() {
		if (baseDao == null) {
			baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		}
		return baseDao;
	}
	
	/**
	 * 返回“查询，明细，执行”查询条件
	 * @return
	 */
	private String getWhereBlock(String modelTable) {
		return "lbpmExpecterLog.fdProcessId = lbpmProcess.fdId "
				+ " and lbpmExpecterLog.fdIsActive = :isActive "
				+ " and lbpmExpecterLog.fdHandler.fdId = :orgId "
				+ " and lbpmProcess.fdModelName = :fdModelName "
				+ " and lbpmProcess.fdModelId = " + modelTable + ".fdId "
				+ " and lbpmExpecterLog.fdTaskType != 'draftWorkitem' "
				+ " and lbpmExpecterLog.fdTaskType != 'communicateWorkitem' "
				+ " and lbpmExpecterLog.fdTaskType != 'assignWorkitem' ";

	}

	@Override
	public void search(HandoverSearchContext context) throws Exception {
		String orgId = context.getHandoverOrg().getFdId();
		String modelName = context.getModule();
		String modelTable = ModelUtil.getModelTableName(modelName);

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(modelName);
		hqlInfo.setJoinBlock(",LbpmProcess lbpmProcess, " + modelName + " " + modelTable);
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setFromBlock("LbpmExpecterLog lbpmExpecterLog");
		hqlInfo.setWhereBlock(getWhereBlock(modelTable));
		hqlInfo.setParameter("isActive", Boolean.TRUE);
		hqlInfo.setParameter("orgId", orgId);
		hqlInfo.setParameter("fdModelName", modelName);
		List<?> result = getBaseDao().findValue(hqlInfo);
		context.getHandoverSearchResult().setTotal(Long.parseLong(result.get(0).toString()));
	}
	
	/**
	 * 获取所有当前处理人的流程实例ID<br>
	 * 使用场景：交接的时候不勾选具体ID
	 * 
	 * @param modelName
	 *            模块名
	 * @param orgId
	 *            当前处理人
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<String> getAllProcessIds(String modelName, String orgId) throws Exception {
		List<String> result = getBaseDao().findValue(getAllProcessIdsHQLInfo(modelName, orgId));
		return result;
	}
	
	public HQLInfo getProcessIdsHQLInfo(String fdModelName, String orgId, String expecterLogIds) {
		String modelTable = ModelUtil.getModelTableName(fdModelName);
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock(",LbpmProcess lbpmProcess, " + fdModelName + " " + modelTable);
		hqlInfo.setSelectBlock("lbpmExpecterLog.fdId");
		hqlInfo.setFromBlock("LbpmExpecterLog lbpmExpecterLog");
		hqlInfo.setWhereBlock(getWhereBlock(modelTable));
		hqlInfo.setParameter("isActive", Boolean.TRUE);
		hqlInfo.setParameter("orgId", orgId);
		hqlInfo.setParameter("fdModelName",fdModelName);
		if(StringUtil.isNotNull(expecterLogIds)){
			List<String> allList = Arrays.asList(expecterLogIds.split(","));
			//大于1000条时拆分list #110385
			if(allList.size() >= 1000) {
				List<List<String>> newList = ListSplitUtil.splitList(allList, ListSplitUtil.MAX_LENGTH);
				StringBuffer whereBlock = new StringBuffer(hqlInfo.getWhereBlock());
				for(int i = 0;i < newList.size();i++) {
					if(i == 0) {
						whereBlock.append(" and (lbpmExpecterLog.fdId in (:expecterLogIds").append("_").append(i).append(")");
					} else {
						whereBlock.append(" or lbpmExpecterLog.fdId in (:expecterLogIds").append("_").append(i).append(")");
					}

					String param = "expecterLogIds_"+i;
					hqlInfo.setParameter(param, newList.get(i));
				}
				whereBlock.append(")");
				hqlInfo.setWhereBlock(whereBlock.toString());
			}else {
				hqlInfo.setWhereBlock(hqlInfo.getWhereBlock() + " and lbpmExpecterLog.fdId in (:expecterLogIds)");
				hqlInfo.setParameter("expecterLogIds", allList);
			}
		}
		return hqlInfo;
	}
	
	public HQLInfo getAllProcessIdsHQLInfo(String modelName, String orgId) {
		return getProcessIdsHQLInfo(modelName, orgId, null);
	}

	@Override
	public void execute(HandoverExecuteContext context) throws Exception {
		List<String> ids = context.getSelectedRecordIds();
		if (ids == null) { // 如果没有特定的记录，则更新所有
			ids = getAllProcessIds(context.getModule(), context.getFrom().getFdId());
		}
		updateHandler(ids, context.getTo().getFdId(), true, context);
	}

	@Override
	public Page detail(HQLInfo hqlInfo, SysOrgElement org, String modelName, String item, RequestContext requestContext) throws Exception {
		String modelTable = ModelUtil.getModelTableName(modelName);
		if (StringUtil.isNull(hqlInfo.getModelName())) {
			hqlInfo.setModelName(modelName);
		}
		StringBuffer buff = new StringBuffer();
		if (StringUtil.isNotNull(hqlInfo.getJoinBlock())) {
			buff.append(hqlInfo.getJoinBlock());
		}
		buff.append(", LbpmProcess lbpmProcess, " + modelName + " " + modelTable);
		hqlInfo.setJoinBlock(buff.toString());
		hqlInfo.setModelName("com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmExpecterLog");
		String whereBlock = StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", getWhereBlock(modelTable));
		hqlInfo.setWhereBlock(whereBlock);
		super.buildSearchFilterWhereBlock(hqlInfo, modelTable, modelName, requestContext);
		hqlInfo.setParameter("isActive", Boolean.TRUE);
		hqlInfo.setParameter("orgId", org.getFdId());
		hqlInfo.setParameter("fdModelName", modelName);
		hqlInfo.setOrderBy(" " + modelTable + ".fdId desc, lbpmExpecterLog.fdFactId");

		return getBaseDao().findPage(hqlInfo);
	}
	
	/**
	 * 批量修改当前处理人
	 * 
	 * @throws Exception
	 * 
	 */
	private void updateHandler(List<String> expecterLogIds, String targetOrgElemIds, boolean onlyModifyInValidHandler,
			HandoverExecuteContext context) throws Exception {
		long successCount = 0L;
		long ignoreCount = 0L;
		Assert.notNull(expecterLogIds, "流程节点Id不能为空");
		Assert.notNull(targetOrgElemIds, "新处理人不能为空");

		LbpmSettingDefault defaultInfo = new LbpmSettingDefault();
		String notifyType = defaultInfo.getDefaultNotifyType();
		List<String> processIds = context.getSelectedProcessIds();
		List<String> factIds = context.getSelectedFactIds();
				for (int i = 0; i < expecterLogIds.size(); i++) {
			String expecterLogId = expecterLogIds.get(i);
			LbpmExpecterLog expecterLog = (LbpmExpecterLog) getBaseDao().findByPrimaryKey(expecterLogId, "com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmExpecterLog", true);
			String lbpmProcessId = processIds.get(i);
			LbpmProcess lbpmProcess = (LbpmProcess) getLbpmProcessService().findByPrimaryKey(lbpmProcessId);
			if (expecterLog != null) {
				List<LbpmNode> nodes = lbpmProcess.getFdNodes();

				// 操作参数
				JSONObject operationParam = new JSONObject();
				operationParam.put("operationType", "sys_changeCurHandler");

				JSONObject params = new JSONObject();
				params.put("repHandlerIds", targetOrgElemIds);
				params.put("auditNote", "");
				params.put("notifyType", notifyType);
				for (LbpmNode node : nodes) {
					String taskIds = getReplaceTasks(expecterLog, node.getFdFactNodeId());
					if (StringUtil.isNotNull(taskIds)) {
						operationParam.put("activityType", node.getFdNodeType());
						operationParam.put("taskId", node.getFdId());
						params.put("taskIds", taskIds);
						operationParam.put("param", params);

						// 执行修改当前处理人的操作
						doProcessExecute(lbpmProcessId, lbpmProcess.getFdModelName(), operationParam);
                        // 更新工作项
						if (getBaseDao().isExist(LbpmWorkitem.class.getName(), expecterLog.getFdTaskId())) {
							LbpmWorkitem item = (LbpmWorkitem) getBaseDao().findByPrimaryKey(expecterLog.getFdTaskId(),
									LbpmWorkitem.class.getName(), false);
							LbpmHistoryWorkitem historyWorkitem = (LbpmHistoryWorkitem) getBaseDao().findByPrimaryKey(expecterLog.getFdTaskId(),
									LbpmHistoryWorkitem.class.getName(), false);
							item.setFdExpecter(context.getTo());
							historyWorkitem.setFdExpecter(context.getTo());
							getBaseDao().update(item);
							getBaseDao().update(historyWorkitem);
						}

					}
				}
				String factName = ProcessNodeUtil.getProcessNodeName(lbpmProcessId, expecterLog.getFdFactId());
				successCount += addLog(context, lbpmProcess, factName, SysHandoverConfigLogDetail.STATE_SUCC);
			} else {
				// 如果节点为空，有可能是当前节点已经结束了
				String factId = factIds.get(i);
				String factName = ProcessNodeUtil.getProcessNodeName(lbpmProcessId, factId);
				ignoreCount += addLog(context, lbpmProcess, factName, SysHandoverConfigLogDetail.STATE_IGNORE);
			}

		}
		context.setSuccTotal(successCount);
		context.setIgnoreTotal(ignoreCount);
	}
	
	private int addLog(HandoverExecuteContext context, LbpmProcess lbpmProcess, String factName, Integer state) {
		int count = 0;
		// 记录日志
		try {
			IBaseModel model = getBaseDao().findByPrimaryKey(lbpmProcess.getFdId(), lbpmProcess.getFdModelName(), true);
			String docSubject = LbpmTemplateUtil.getDocSubject(model);
			String url = HandModelUtil.getUrl(model);
			String desc = docSubject;
			context.log(lbpmProcess.getFdId(), context.getModule(), desc, url, factName, state);
			count++;
		} catch (Exception e) {
			// 出错，跳过不计数
			logger.error("LbpmExpecterLog:" + lbpmProcess.getFdId() + "交接当前处理人出错", e);
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 获取要替换的工作项Id集
	 * 
	 * @param logs
	 * @param fdFactId
	 * @return
	 */
	protected String getReplaceTasks(LbpmExpecterLog expecterLog, String fdFactId) {
		if (expecterLog.getFdFactId().equals(fdFactId)) {
			if (LbpmConstants.DRAFT_WORKITEM.equals(expecterLog.getFdTaskType())) {
				logger.debug("处于起草人节点的流程不作‘修改处理人’的操作,忽略,流程fdId:" + expecterLog.getFdProcessId());
				return "";
			}
			if (LbpmConstants.COMMUNICATE_WORKITEM.equals(expecterLog.getFdTaskType())) {
				logger.debug("忽略被沟通人工作项,流程fdId:" + expecterLog.getFdProcessId());
				return "";
			}
			if ("2".equals(expecterLog.getFdHandlerType())) {
				logger.debug("忽略被授权人无效的流程fdId:" + expecterLog.getFdProcessId());
				return "";
			}
			return expecterLog.getFdTaskId();
		}
		return "";
	}
	
	/**
	 * 流程执行
	 * 
	 */
	private void doProcessExecute(String fdModelId, String fdModelName,
			JSONObject operationParamJson) throws Exception {
		// 操作参数
		OperationParameters operParameters = new OperationParameters(fdModelId,
				operationParamJson.getString("operationType"),
				operationParamJson.getString("activityType"),
				operationParamJson.getString("taskId"),
				operationParamJson.getString("param"));
		// 运行参数
		ExecuteParameters exeParameters = new ExecuteParameters(fdModelName,
				fdModelId);

		getLbpmProcessExecuteService().systemExecute(operParameters, exeParameters);
	}
	
	@Override
	public String getFdAttribute(){
		return HANDLER;
	}

}
