package com.landray.kmss.third.ding.service.spring;

import com.dingtalk.api.request.OapiProcessinstanceExecuteRequest;
import com.dingtalk.api.request.OapiProcessinstanceGetRequest;
import com.dingtalk.api.response.OapiProcessinstanceExecuteResponse;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse.TaskTopVo;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.lbpm.engine.integrate.expecterlog.ILbpmExpecterLogService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.manager.operation.OperationParameters;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmExpecterLog;
import com.landray.kmss.sys.lbpm.engine.service.ExecuteParameters;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.ThirdDingFinstance;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingFinstanceService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdDingFlowEventHandlerService implements IEventListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingFlowEventHandlerService.class);

	private IThirdDingFinstanceService thirdDingFinstanceService;

	private IOmsRelationService omsRelationService;

	public void setThirdDingFinstanceService(IThirdDingFinstanceService thirdDingFinstanceService) {
		this.thirdDingFinstanceService = thirdDingFinstanceService;
	}

	public void setOmsRelationService(IOmsRelationService omsRelationService) {
		this.omsRelationService = omsRelationService;
	}

	private ProcessExecuteService executeService;

	public ProcessExecuteService getExecuteService() {
		if (executeService == null) {
			executeService = (ProcessExecuteService) SpringBeanUtil
					.getBean("lbpmProcessExecuteService");
		}
		return executeService;
	}

	private IBaseService kmReviewMainService = null;

	public IBaseService getKmReviewMainService() {
		if (kmReviewMainService == null) {
			kmReviewMainService = (IBaseService) SpringBeanUtil.getBean("kmReviewMainService");
		}
		return kmReviewMainService;
	}

	private IBackgroundAuthService backgroundAuthService = null;

	public IBackgroundAuthService getBackgroundAuthService() {
		if (backgroundAuthService == null) {
			backgroundAuthService = (IBackgroundAuthService) SpringBeanUtil
					.getBean("backgroundAuthService");
		}
		return backgroundAuthService;
	}

	private ILbpmExpecterLogService lbpmExpecterLogService = null;

	public ILbpmExpecterLogService getLbpmExpecterLogService() {
		if (lbpmExpecterLogService == null) {
			lbpmExpecterLogService = (ILbpmExpecterLogService) SpringBeanUtil
					.getBean("lbpmExpecterLogService");
		}
		return lbpmExpecterLogService;
	}
	
	private ISysOrgPersonService sysOrgPersonService = null;
	
	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		logger.warn("---------------------流程节点，结束钉钉流程事件------------------------");
		try {
			// 钉钉补卡申请
			IBaseModel baseModel = execution.getMainModel();
			updateDing(baseModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateDing(IBaseModel baseModel) throws Exception {
		String fdTemplateId = DingUtil.getModelTemplateProperyId(baseModel, "fdTemplate", null);
		if (StringUtil.isNull(fdTemplateId)) {
			return;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdInstanceId");
		hqlInfo.setWhereBlock("fdTemplateId='" + fdTemplateId + "' and fdModelId='" + baseModel.getFdId() + "'");

		String fdInstanceId = (String) thirdDingFinstanceService.findFirstOne(hqlInfo);
		if (StringUtils.isNotBlank(fdInstanceId)) {
			// 根据流程实例获取审批单信息
			DingApiService dingService = DingUtils.getDingApiService();
			String url = DingConstant.DING_PREFIX
					+ "/topapi/processinstance/get"
					+ DingUtil.getDingAppKeyByEKPUserId("?", null);
			logger.debug("钉钉接口：" + url);
			ThirdDingTalkClient client = new ThirdDingTalkClient(url);
			OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
			request.setProcessInstanceId(fdInstanceId);
			OapiProcessinstanceGetResponse response = client.execute(request, dingService.getAccessToken());
			if (response.getErrcode()==0&&response.getProcessInstance().getFinishTime()==null) {
				List<TaskTopVo> tasks = response.getProcessInstance().getTasks();
				OapiProcessinstanceExecuteResponse rsp = null;
				for(TaskTopVo task:tasks){
					if("RUNNING".equals(task.getTaskStatus())){
						url = DingConstant.DING_PREFIX
								+ "/topapi/processinstance/execute"
								+ DingUtil.getDingAppKeyByEKPUserId("?", null);
						logger.debug("钉钉接口：" + url);
						client = new ThirdDingTalkClient(url);
						OapiProcessinstanceExecuteRequest req = new OapiProcessinstanceExecuteRequest();
						req.setActionerUserid(task.getUserid());
						req.setProcessInstanceId(fdInstanceId);
						req.setTaskId(Long.parseLong(task.getTaskid()));
						req.setResult("agree");
						req.setRemark("同意");
						rsp = client.execute(req, dingService.getAccessToken());
						logger.debug("审批结果：" + rsp.getBody());
						if (!rsp.getResult()) {
							return;
						}
					}
				}
				if (null != rsp && rsp.getResult()) {
					updateDing(baseModel);
				}
			}else{
				logger.info("流程已经结束或者异常，不再处理");
				return;
			}
		}
	}
	
	public String flowAudit(String pid,String time) throws Exception {
		logger.debug("-----------------------------钉钉结束流程同步通知EKP--------------------------------------------");
		JSONObject retjson = new JSONObject();
		try {
			ThirdDingFinstance thirdDingFinstance = (ThirdDingFinstance) thirdDingFinstanceService.findFirstOne("fdCreateTime='"+time+"' or fdInstanceId='"+pid+"'", null);
			if(thirdDingFinstance == null){
				retjson.put("returnState", "1");
				retjson.put("message", "失败！找不到对应的主文档");
				return retjson.toString();
			}
			IBaseModel main = getKmReviewMainService().findByPrimaryKey(thirdDingFinstance.getFdModelId(),
					thirdDingFinstance.getFdModelName(), true);
			// 根据主文档查找下一节点的处理人
			List<LbpmExpecterLog> listlog = getLbpmExpecterLogService()
					.findByProcessId(main.getFdId());
			if (listlog == null || listlog.size() == 0) {
				/*logger.error("节点处理人为空...");
				retjson.put("returnState", "1");
				retjson.put("message", "失败！节点处理人为空...");
				return retjson.toString();*/
			}
			// 后台流程的操作（通过）
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("log", listlog.get(0));
			map.put("main", main);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("fdId");
			hqlInfo.setWhereBlock("fdLoginName='admin'");
			String personId = (String) getSysOrgPersonService().findFirstOne(hqlInfo);
			if(StringUtils.isNotBlank(personId)){
				map.put("adminId", personId);
				getBackgroundAuthService().switchUserById(
						personId, new Runner() {
							@Override
                            public Object run(Object parameter) throws Exception {
								Map<String, Object> map = (Map<String, Object>) parameter;
								LbpmExpecterLog log = (LbpmExpecterLog) map
										.get("log");
								IBaseModel model = (IBaseModel) map.get("main");
								OperationParameters parameters = new OperationParameters();
								parameters.setFdProcessInstanceId(log
										.getFdProcessId());
								parameters.setFdActivityType(log.getFdTaskType());
								parameters.setFdTaskId(log.getFdTaskId());
								parameters.setOperationType("admin_pass");
								Map<String, Object> flowMap = new HashMap<String, Object>();
								flowMap.put("operationName", "通过");
								flowMap.put("notifyType", "todo");
								flowMap.put("notifyOnFinish", true);
								flowMap.put("auditNote", "系统终审通过(钉钉)");
								parameters.setParameter(JSONObject.fromObject(
										flowMap).toString());
								ExecuteParameters executeParameters = new ExecuteParameters(
										model);
								getExecuteService().execute(parameters,
										executeParameters, null);
								return parameter;
							}
						}, map);
			}
			retjson.put("returnState", "2");
			retjson.put("message", "成功");
		} catch (Exception e) {
			retjson.put("returnState", "1");
			retjson.put("message", e.getMessage());
			logger.error("", e);
			e.printStackTrace();
		}
		return retjson.toString();
	}
}
