package com.landray.kmss.third.ding.service.spring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.request.OapiProcessWorkrecordTaskCreateRequest;
import com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskCreateResponse;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskUpdateResponse;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingDtask;
import com.landray.kmss.third.ding.model.ThirdDingError;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.IThirdDingDtaskService;
import com.landray.kmss.third.ding.service.IThirdDingDtemplateService;
import com.landray.kmss.third.ding.service.IThirdDingErrorHandlerService;
import com.landray.kmss.third.ding.service.IThirdDingErrorService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class ThirdDingErrorServiceImp extends ExtendDataServiceImp implements IThirdDingErrorService {

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof ThirdDingError) {
			ThirdDingError thirdDingError = (ThirdDingError) model;
		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		ThirdDingError thirdDingError = new ThirdDingError();
		ThirdDingUtil.initModelFromRequest(thirdDingError, requestContext);
		return thirdDingError;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		ThirdDingError thirdDingError = (ThirdDingError) model;
	}

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(IThirdDingErrorService.class);

	private static boolean locked = false;

	@Override
    public void synchroError(SysQuartzJobContext context) {
		String temp = "";
		if (locked) {
			temp = "异常任务同步已经在运行，当前任务中断...";
			logger.info(temp);
			context.logMessage(temp);
			return;
		}
		try {
			locked = true;
			long alltime = System.currentTimeMillis();
			updateHandle();
			updateHandleNotify();
			logger.debug(temp);
			context.logMessage(temp);
			temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000;
			logger.debug(temp);
			context.logMessage(temp);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("", ex);
		} finally {
			locked = false;
		}
	}

	private void updateHandle() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ThirdDingError> errors = findList("fdCount<=1", null);
		String service = null;
		Object object = null;
		boolean flag = true;
		String errormsg = null;
		for (ThirdDingError error : errors) {
			flag = true;
			errormsg = null;
			service = error.getFdServiceName();
			if (StringUtil.isNotNull(service)) {
				try {
					if (map.containsKey(service)) {
						object = map.get(service);
					} else {
						object = SpringBeanUtil.getBean(service);
						map.put(service, object);
					}
					if (object != null && object instanceof IThirdDingErrorHandlerService) {
						flag = ((IThirdDingErrorHandlerService) object).handleError(error);
					} else {
						flag = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					errormsg = e.getMessage();
					flag = false;
				}
				if (flag) {
					delete(error);
				} else {
					error.setFdCount(error.getFdCount() + 1);
					error.setFdHandleError(errormsg);
					update(error);
				}
			}
		}
		object = null;
	}
	
	private IThirdDingDtaskService thirdDingDtaskService;
	
	public IThirdDingDtaskService getThirdDingDtaskService() {
		if(thirdDingDtaskService==null){
			thirdDingDtaskService = (IThirdDingDtaskService) SpringBeanUtil.getBean("thirdDingDtaskService");
		}
		return thirdDingDtaskService;
	}

	private ISysNotifyTodoService sysNotifyTodoService = null;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}
	
	private IThirdDingDtemplateService thirdDingDtemplateService = null;

	public IThirdDingDtemplateService getThirdDingDtemplateService() {
		if (thirdDingDtemplateService == null) {
			thirdDingDtemplateService = (IThirdDingDtemplateService) SpringBeanUtil
					.getBean("thirdDingDtemplateService");
		}
		return thirdDingDtemplateService;
	}
	
	private void updateHandleNotify() throws Exception {
		// ThirdDingDtemplate template =
		// getThirdDingDtemplateService().updateCommonTemplate();
		String agentId = DingConfig.newInstance().getDingAgentid();
		List<ThirdDingDtask> updateTasks = new ArrayList<ThirdDingDtask>();
		Calendar ucal = Calendar.getInstance();
		ucal.add(Calendar.DATE, -15);
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdStatus in ('10','11') and docCreateTime>=:date");
		info.setParameter("date", ucal.getTime());
		// 重新发送未发送待办和失败待办
		List<ThirdDingDtask> dtasks = getThirdDingDtaskService().findList(info);
		SysNotifyTodo todo = null;
		List<SysOrgElement> todoTargets = null;
		String token = DingUtils.getDingApiService().getAccessToken();
		for(ThirdDingDtask task:dtasks){
			//如果待办找不到或者结束则跳过
			boolean flag = false;
			todo = (SysNotifyTodo) getSysNotifyTodoService().findByPrimaryKey(task.getFdEkpTaskId(), null, true);
			if (todo == null || todo.getHbmTodoTargets() == null || todo.getHbmTodoTargets().size()==0) {
				logger.debug("兼容处理：EKP中待办已经删除或者未办人员为空，则不重新发送待办");
			} else {
				todoTargets = todo.getHbmTodoTargets();
				for (SysOrgElement ele : todoTargets) {
					if (task.getFdEkpUser().getFdId().equals(ele.getFdId())) {
						flag = true;
						break;
					}
				}
			}
			if(flag){
				OapiProcessWorkrecordTaskCreateRequest req = new OapiProcessWorkrecordTaskCreateRequest();
				OapiProcessWorkrecordTaskCreateResponse response = DingNotifyUtil.createTask(req, token, task.getFdInstance().getFdInstanceId(),
								task.getFdDingUserId(), task.getFdUrl(),
								Long.parseLong(agentId),
								task.getFdEkpUser() == null ? null
										: task.getFdEkpUser().getFdId());
				if (response.getErrcode() == 0) {
					task.setFdStatus("12");
					updateTasks.add(task);
				} else {
					logger.error("重新发送未发送待办和失败待办失败，详情："+response.getBody());
				}
				
			}
		}
		// 重新发送未更新待办和更新失败待办
		info = new HQLInfo();
		info.setWhereBlock("fdStatus in ('20','21') and docCreateTime>=:date");
		info.setParameter("date", ucal.getTime());
		dtasks = getThirdDingDtaskService().findList(info);
		for(ThirdDingDtask task:dtasks){
			OapiProcessWorkrecordTaskUpdateRequest req = new OapiProcessWorkrecordTaskUpdateRequest();
			OapiProcessWorkrecordTaskUpdateResponse res = DingNotifyUtil.updateTask(req, token,
							task.getFdInstance().getFdInstanceId(),
							Long.parseLong(task.getFdTaskId()),
							Long.parseLong(agentId), task.getFdEkpUser() == null
									? null : task.getFdEkpUser().getFdId());
			if (res.getErrcode() == 0) {
				task.setFdStatus("22");
				updateTasks.add(task);
			}else{
				logger.error("重新发送未更新待办和更新失败的待办再次失败，详情："+res.getBody());
			}
		}
		logger.debug("共更新异常待办(条)："+updateTasks.size());
		for(ThirdDingDtask task:updateTasks){
			getThirdDingDtaskService().update(task);
		}
	}
	
}
