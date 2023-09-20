package com.landray.kmss.third.ding.service.spring;

import com.dingtalk.api.request.OapiProcessWorkrecordTaskCreateRequest;
import com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskCreateResponse;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskCreateResponse.TaskTopVo;
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
import com.landray.kmss.third.ding.model.*;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.*;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;

public class ThirdDingDtaskServiceImp extends ExtendDataServiceImp implements IThirdDingDtaskService {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingDtaskServiceImp.class);

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof ThirdDingDtask) {
			ThirdDingDtask thirdDingDtask = (ThirdDingDtask) model;
		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		ThirdDingDtask thirdDingDtask = new ThirdDingDtask();
		ThirdDingUtil.initModelFromRequest(thirdDingDtask, requestContext);
		return thirdDingDtask;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		ThirdDingDtask thirdDingDtask = (ThirdDingDtask) model;
	}

	@Override
    public List<ThirdDingDtask> findByFdInstance(ThirdDingDinstance fdInstance) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("thirdDingDtask.fdInstance.fdId=:fdId");
		hqlInfo.setParameter("fdId", fdInstance.getFdId());
		return this.findList(hqlInfo);
	}

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	private IThirdDingNotifylogService thirdDingNotifylogService;

	public IThirdDingNotifylogService getThirdDingNotifylogService() {
		if (thirdDingNotifylogService == null) {
			thirdDingNotifylogService = (IThirdDingNotifylogService) SpringBeanUtil
					.getBean("thirdDingNotifylogService");
		}
		return thirdDingNotifylogService;
	}

	@Override
	public String addTask(ThirdDingDtask dtask, SysNotifyTodo todo, Long agentId) throws Exception {
		String instanceid = dtask.getFdInstance().getFdInstanceId();
		String token = DingUtils.getDingApiService().getAccessToken();
		OapiProcessWorkrecordTaskCreateRequest req = new OapiProcessWorkrecordTaskCreateRequest();
		try {
			OapiProcessWorkrecordTaskCreateResponse response = DingNotifyUtil
					.createTask(req, token, instanceid,
							dtask.getFdDingUserId(), dtask.getFdUrl(), agentId,
							dtask.getFdEkpUser() != null
									? dtask.getFdEkpUser().getFdId() : null);
			if (response.getErrcode() == 0) {
				List<TaskTopVo> tasks = response.getTasks();
				String taskid = null;
				for (TaskTopVo task : tasks) {
					taskid = task.getTaskId().toString();
				}
				dtask.setFdTaskId(taskid);
				dtask.setFdStatus("12");
			} else {
				dtask.setFdStatus("11");
				logger.error("发送待办失败，详细错误：" + response.getBody());
			}
			dtask.setFdDesc(response.getBody());
			dtask.setDocCreateTime(new Date());
			JSONObject jo = JSONObject.fromObject(response.getBody());
			addNotifyLog(todo, jo, JSONObject.fromObject(req).toString());
			return super.add(dtask);
		} catch (Exception e) {
			logger.error("发送待办异常：" + e.getMessage(), e);
			JSONObject jo = new JSONObject();
			jo.put("待办异常", e.getMessage());
			addNotifyLog(todo, jo, JSONObject.fromObject(req).toString());
			dtask.setFdStatus("11");
			dtask.setFdDesc(e.getMessage());
			dtask.setDocCreateTime(new Date());
			return super.add(dtask);
		}
	}

	private void addNotifyLog(SysNotifyTodo todo, JSONObject jo, String reqData) {
		try {
			ThirdDingNotifylog notifylog = new ThirdDingNotifylog();
			notifylog.setDocSubject(todo.getFdSubject());
			notifylog.setFdNotifyData(reqData);
			notifylog.setFdSendTime(new Date());
			notifylog.setFdRtnMsg(jo.toString());
			notifylog.setFdNotifyId(todo.getFdId());
			notifylog.setFdRtnTime(new Date());
			getThirdDingNotifylogService().add(notifylog);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private ISysNotifyTodoService sysNotifyTodoService = null;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}

	private IThirdDingDinstanceService thirdDingDinstanceService = null;

	public IThirdDingDinstanceService getThirdDingDinstanceService() {
		if (thirdDingDinstanceService == null) {
			thirdDingDinstanceService = (IThirdDingDinstanceService) SpringBeanUtil
					.getBean("thirdDingDinstanceService");
		}
		return thirdDingDinstanceService;
	}

	private IThirdDingDtemplateService thirdDingDtemplateService = null;

	public IThirdDingDtemplateService getThirdDingDtemplateService() {
		if (thirdDingDtemplateService == null) {
			thirdDingDtemplateService = (IThirdDingDtemplateService) SpringBeanUtil
					.getBean("thirdDingDtemplateService");
		}
		return thirdDingDtemplateService;
	}

	private IOmsRelationService omsRelationService = null;

	public IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
		}
		return omsRelationService;
	}

	@Override
	public void updateSendTask(String fdId) throws Exception {
		logger.debug("处理待办任务：" + fdId);
		ThirdDingDtask dtask = (ThirdDingDtask) super.findByPrimaryKey(fdId, null, true);
		if (dtask != null) {
			logger.debug("处理待办任务：" + dtask.getFdName());
			SysNotifyTodo todo = (SysNotifyTodo) getSysNotifyTodoService().findByPrimaryKey(dtask.getFdEkpTaskId(),
					null, true);
			List<SysOrgElement> todoTargets = null;
			if (todo != null) {
				logger.debug("找到待办，" + todo.getFdSubject());
				ThirdDingDinstance dinstance = dtask.getFdInstance();
				if (dtask.getFdInstance() == null) {
					logger.debug("所属实例为空");
					String dingId = instanceCreator(dtask.getFdEkpUser().getFdId());
					if (StringUtil.isNull(dingId)) {
						logger.debug("找不到接收人dingId");
						return;
					}
					ThirdDingDtemplate template = getThirdDingDtemplateService()
							.updateCommonTemplate(null);
					if (template == null) {
						logger.debug("模块为空");
						getThirdDingDtemplateService().addCommonTemplate(null,
								null);
						template = getThirdDingDtemplateService()
								.updateCommonTemplate(null);
					}
					logger.debug("获取实例");
					dinstance = getThirdDingDinstanceService()
							.findCommonInstance(todo, template.getFdId());
					if (dinstance == null) {
						logger.debug("实例为空，创建实例");
						String dinstanceid = getThirdDingDinstanceService().addCommonInstance(dingId, todo, template,template.getFdDetail());
						dinstance = (ThirdDingDinstance) getThirdDingDinstanceService().findByPrimaryKey(dinstanceid);
						logger.debug("实例ID，" + dinstance.getFdId() + ","
								+ dinstance.getFdInstanceId());
					}
				}
				DingConfig config = new DingConfig();
				dtask.setFdInstance(dinstance);
				String instanceid = dinstance.getFdInstanceId();
				String token = DingUtils.getDingApiService().getAccessToken();
				todoTargets = todo.getHbmTodoTargets();
				// 未推送或者推送失败
				if (StringUtil.isNull(dtask.getFdTaskId())) {
					logger.debug("任务ID为空," + dtask.getFdEkpUser().getFdId());
					boolean flag = false;
					for (SysOrgElement ele : todoTargets) {
						if (ele.getFdId().equals(dtask.getFdEkpUser().getFdId())) {
							logger.debug("待办处理人：" + todo.getFdId() + "---"
									+ ele.getFdId());
							flag = true;
						}
					}
					if (flag) {
						logger.debug("人员还在待办里面");
						OapiProcessWorkrecordTaskCreateRequest req = new OapiProcessWorkrecordTaskCreateRequest();
						OapiProcessWorkrecordTaskCreateResponse res = DingNotifyUtil.createTask(req, token, instanceid,
										dtask.getFdDingUserId(),
										dtask.getFdUrl(),
										Long.parseLong(config.getDingAgentid()),
										dtask.getFdEkpUser() != null
												? dtask.getFdEkpUser().getFdId()
												: null);
						if (res.getErrcode() == 0) {
							logger.debug("重发待办成功");
							List<TaskTopVo> tasks = res.getTasks();
							String taskid = null;
							for (TaskTopVo task : tasks) {
								taskid = task.getTaskId().toString();
							}
							logger.debug("taskid:" + taskid);
							dtask.setFdTaskId(taskid);
							dtask.setFdStatus("12");
						} else {
							logger.debug("重发待办失败，" + res.getErrorCode() + ","
									+ res.getErrmsg());
							if (res.getErrcode() == 820012) {
								logger.debug("重新生成实例");
								String dingId = instanceCreator(
										dtask.getFdEkpUser().getFdId());
								logger.debug("dingId:" + dingId);
								ThirdDingDtemplate template = getThirdDingDtemplateService()
										.updateCommonTemplate(null);
								if (template == null) {
									logger.debug("重新生成模板");
									getThirdDingDtemplateService()
											.addCommonTemplate(null,
													null);
									template = getThirdDingDtemplateService()
											.updateCommonTemplate(null);
								}
								logger.debug("重新生成实例");
								String dinstanceid = getThirdDingDinstanceService()
										.addCommonInstance(dingId, todo,
												template,
												template.getFdDetail());
								dinstance = (ThirdDingDinstance) getThirdDingDinstanceService()
										.findByPrimaryKey(dinstanceid);
								logger.debug("生成实例成功，实例ID：" + dinstanceid
										+ "---" + dinstance.getFdInstanceId());
								dtask.setFdInstance(dinstance);
								logger.debug("重新生成任务");
								OapiProcessWorkrecordTaskCreateRequest req1 = new OapiProcessWorkrecordTaskCreateRequest();
								OapiProcessWorkrecordTaskCreateResponse res1 = DingNotifyUtil
										.createTask(req1, token,
												dinstance.getFdInstanceId(),
												dtask.getFdDingUserId(),
												dtask.getFdUrl(),
												Long.parseLong(config
														.getDingAgentid()),
												dtask.getFdEkpUser() != null
														? dtask.getFdEkpUser()
																.getFdId()
														: null);
								if (res1.getErrcode() == 0) {
									logger.debug("重新生成任务成功");
									List<TaskTopVo> tasks = res1.getTasks();
									String taskid = null;
									for (TaskTopVo task : tasks) {
										taskid = task.getTaskId().toString();
									}
									logger.debug("taskid:" + taskid);
									dtask.setFdTaskId(taskid);
									dtask.setFdStatus("12");
								} else {
									dtask.setFdStatus("11");
									logger.debug(
											"重新生成任务失败，" + res1.getErrorCode()
													+ "," + res1.getErrmsg());
								}
							} else {
								logger.debug("其它错误," + res.getErrorCode() + ","
										+ res.getErrmsg());
								dtask.setFdStatus("11");
							}
						}
						dtask.setFdDesc(res.getBody());
						dtask.setDocCreateTime(new Date());
						super.update(dtask);
					} else {
						logger.debug("人员不在待办里面");
						dtask.setFdStatus("22");
						dtask.setFdDesc("待办已经处理无须推送");
						super.update(dtask);
					}
				} else {
					logger.debug("任务ID不为空");
					boolean flag = true;
					for (SysOrgElement ele : todoTargets) {
						if (ele.getFdId().equals(dtask.getFdEkpUser().getFdId())) {
							flag = false;
							break;
						}
					}
					if (flag) {
						logger.debug(
								"人员不在待办里面，" + dtask.getFdEkpUser().getFdId());
						OapiProcessWorkrecordTaskUpdateRequest req = new OapiProcessWorkrecordTaskUpdateRequest();
						OapiProcessWorkrecordTaskUpdateResponse res = DingNotifyUtil.updateTask(req, token, instanceid,
										Long.parseLong(dtask.getFdTaskId()),
										Long.parseLong(config.getDingAgentid()),
										dtask.getFdEkpUser() != null
												? dtask.getFdEkpUser().getFdId()
												: null);
						if (res.getErrcode() == 0) {
							logger.debug("置为已办成功");
							dtask.setFdStatus("22");
						} else {
							logger.debug("置为已办失败，" + res.getErrorCode() + ","
									+ res.getErrmsg());
							dtask.setFdStatus("21");
						}
						dtask.setFdDesc(res.getBody());
						dtask.setDocCreateTime(new Date());
						super.update(dtask);
					} else {
						logger.debug("人员在待办里面，但之前已推送成功");
						dtask.setFdStatus("22");
						dtask.setFdDesc("待办已经处理无须推送");
						super.update(dtask);
					}
				}
			} else {
				logger.debug("找不到待办，" + dtask.getFdEkpTaskId());
				dtask.setFdStatus("22");
				dtask.setFdDesc("待办已经处理无须推送");
				super.update(dtask);
				logger.debug("待办已经处理无须推送");
			}
		} else {
			logger.debug("文档不存在");
		}
	}

	private String instanceCreator(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock("fdEkpId='" + fdId + "'");
		String dinguserid = (String) getOmsRelationService().findFirstOne(hqlInfo);
		if (StringUtils.isBlank(dinguserid)) {
			logger.debug("消息接收人在映射表中找不到，无法发送消息");
		}
		return dinguserid;
	}

	@Override
	public ThirdDingDtask findByTaskId(String instanceId, String taskId)
			throws Exception {
		if (StringUtil.isNull(instanceId) || StringUtil.isNull(taskId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"fdInstance.fdInstanceId=:instanceId and fdTaskId=:taskId");
		info.setParameter("instanceId", instanceId);
		info.setParameter("taskId", taskId);
		return (ThirdDingDtask) this.findFirstOne(info);
	}

	@Override
    public ThirdDingDtask findByNotifyId(String notifyId, String dingUserId)
			throws Exception {
		if (StringUtil.isNull(notifyId) || StringUtil.isNull(dingUserId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"fdDingUserId=:fdDingUserId and fdEkpTaskId=:fdEkpTaskId");
		info.setParameter("fdDingUserId", dingUserId);
		info.setParameter("fdEkpTaskId", notifyId);
		return (ThirdDingDtask) this.findFirstOne(info);
	}
}
