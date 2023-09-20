package com.landray.kmss.third.ding.listener.attendance;

import com.dingtalk.api.response.OapiProcessWorkrecordUpdateResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpmservice.operation.handler.LbpmHandlerRefuseEvent;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingDinstanceXform;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingDinstanceXformService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.third.ding.xform.util.ThirdDingXFormTemplateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 钉钉考勤套件审批通过节点结束事件，用于处理审批通过后将结果通知钉钉
 * 
 *
 */
public class AttendenceAbandonListener implements IEventListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AttendenceAbandonListener.class);

	public AttendenceAbandonListener(IOmsRelationService omsRelationService) {
		super();
		this.omsRelationService = omsRelationService;
	}

	public AttendenceAbandonListener() {
		super();
	}

	/**
	 * ekp异常，不重试
	 */
	static final int EKP_ERRROR = -1;
	/**
	 * 钉钉同步成功
	 */
	static final int DING_SUCCESS = 1;
	/**
	 * 钉钉返回失败
	 */
	static final int DING_ERROR = 0;

	private IOmsRelationService omsRelationService;



	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
					.getBean("sysOrgElementService");
		}
		return sysOrgElementService;

	}

	private IThirdDingDinstanceXformService thirdDingDinstanceXformService;

	public IThirdDingDinstanceXformService getThirdDingDinstanceXformService() {
		if (thirdDingDinstanceXformService == null) {
			thirdDingDinstanceXformService = (IThirdDingDinstanceXformService) SpringBeanUtil
					.getBean("thirdDingDinstanceXformService");
		}
		return thirdDingDinstanceXformService;
	}

	protected ISysOrgPostService sysOrgPostService;

	protected ISysOrgPostService
			getSysOrgPostServiceService() {
		if (sysOrgPostService == null) {
			sysOrgPostService = (ISysOrgPostService) SpringBeanUtil
					.getBean("sysOrgPostService");
		}
		return sysOrgPostService;
	}

	public void setOmsRelationService(IOmsRelationService omsRelationService) {
		this.omsRelationService = omsRelationService;
	}



	/**
	 * 处理审批通过事件
	 */
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		try {
			logger.info("-----------处理起草人废弃和撤回以及驳回事件----------" );
			IBaseModel baseModel = execution.getMainModel();
			String type = ThirdDingXFormTemplateUtil
					.getXFormTemplateType(baseModel);
			logger.debug("type:" + type);
			if (StringUtil.isNull(type)||"common".equals(type)) {
				// 更新实例
				if (!(execution.getEvent() instanceof LbpmHandlerRefuseEvent)) {
                    //普通模板的流程，只有撤回和废弃才更新实例。驳回不更新
					updateInstance(execution);
				}else{
					logger.debug("-------普通流程驳回，不更新实例-------");
				}
			}else{
				//套件，支持驳回编辑
				if (execution.getEvent() instanceof LbpmHandlerRefuseEvent) {
					LbpmHandlerRefuseEvent lbpmHandlerRefuseEvent = (LbpmHandlerRefuseEvent) execution
							.getEvent();
					String node = lbpmHandlerRefuseEvent.getJumpToNodeId();
					if (!"N2".equals(node)) {
						logger.debug("------非起草节点，不更新实例--------" + node);
						return;
					}
				}
				updateInstance(execution);
			}

		} catch (Exception e) {
			// 失败，写入请假日志并标记为失败
			logger.error(e.getMessage(), e);
		}

	}

	// 更新实例
	private void updateInstance(EventExecutionContext execution) {
		try {
			IBaseModel baseModel = execution.getMainModel();
			String modelName = ModelUtil.getModelClassName(baseModel);
			String reviewMainId = baseModel.getFdId();
			logger.debug("modelName:" + modelName);
			if ("com.landray.kmss.km.review.model.KmReviewMain"
					.equals(modelName)) {
				logger.warn("-------开启钉钉审批高级版，审批结束，开始更新实例-------");
				// 找到对应的实例
				String docSubject = (String) DingUtil.getModelPropertyString(
						baseModel,
						"docSubject", "", null);
				logger.warn("流程标题=>" + docSubject);

				logger.warn("流程主文档fdId=>" + reviewMainId);
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"fdEkpInstanceId =:fdEkpInstanceId and fdStatus=:fdStatus");
				hqlInfo.setParameter("fdEkpInstanceId", reviewMainId);
				hqlInfo.setParameter("fdStatus", "20");
				List<ThirdDingDinstanceXform> list = getThirdDingDinstanceXformService()
						.findList(hqlInfo);
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						ThirdDingDinstanceXform dinstanceXform = list.get(i);
						OapiProcessWorkrecordUpdateResponse response = DingNotifyUtil
								.updateInstanceState(
										DingUtils.dingApiService
												.getAccessToken(),
										dinstanceXform.getFdInstanceId(),
										Long.valueOf(list.get(i).getFdTemplate()
												.getFdAgentId()),
										dinstanceXform.getFdEkpUser().getFdId(),
										false);

						if (response != null && response.getErrcode() == 0) {
							logger.debug("更新实例成功");
							dinstanceXform.setFdStatus("00");
							getThirdDingDinstanceXformService()
									.update(dinstanceXform);
						} else {
							logger.warn("更新实例失败！" + docSubject
									+ "   reviewMainId:" + reviewMainId);
						}
					}

				} else {
					logger.warn("没有找到需要更新的实例！    主题：" + docSubject
							+ "   reviewMainId:" + reviewMainId);
				}

				return;
			}

		} catch (Exception e) {
			logger.error("更新实例状态失败", e);
		}
	}

	public boolean hasInstanceInXform(String reviewMainId) {
		if (getInstanceXform(reviewMainId) == null) {
			return false;
		} else {
			return true;
		}
	}

	private ThirdDingDinstanceXform getInstanceXform(String reviewMainId) {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock(
				"fdEkpInstanceId=:fdEkpInstanceId and fdStatus=:fdStatus");
		hql.setParameter("fdEkpInstanceId", reviewMainId);
		hql.setParameter("fdStatus", "20");
		try {
			return (ThirdDingDinstanceXform) getThirdDingDinstanceXformService()
					.findFirstOne(hql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 钉钉考勤同步异常通知(runtime异常)
	 * 
	 * @param dataMap
	 */
	private void sengDingErrorNotify(IBaseModel baseModel) {
		try {
			String notifyIds = DingConfig.newInstance()
					.getAttendanceErrorNotifyOrgId();
			if (StringUtil.isNull(notifyIds)) {
				logger.warn("钉钉异常通知人为空，因此不发送异常通知");
				return;
			}

			String[] id_array = notifyIds.split(";");
			String dingDeptIds = "";
			String dingUserIds = "";
			String ekpUserId = null;

			for (int i = 0; i < id_array.length; i++) {
				if (StringUtil.isNull(id_array[i])) {
                    continue;
                }
				logger.debug("通知对象id:" + id_array[i]);
				SysOrgElement org = (SysOrgElement) getSysOrgElementService()
						.findByPrimaryKey(id_array[i]);
				if (org != null) {
					if (org.getFdOrgType() == SysOrgElement.ORG_TYPE_ORG || org
							.getFdOrgType() == SysOrgElement.ORG_TYPE_DEPT) {
						logger.debug("部门Id:" + id_array[i]);
						String dingDeptId = omsRelationService
								.getDingUserIdByEkpUserId(id_array[i]);
						if (StringUtil.isNotNull(dingDeptId)) {
							ekpUserId = id_array[i];
							dingDeptIds = dingDeptIds + "," + dingDeptId;
						}
					} else if (org
							.getFdOrgType() == SysOrgElement.ORG_TYPE_POST) {
						logger.debug("岗位Id:" + id_array[i]);
						SysOrgPost post = (SysOrgPost) getSysOrgPostServiceService()
								.findByPrimaryKey(id_array[i]);
						if (post != null) {
							List<SysOrgPerson> persons = post.getFdPersons();
							for (SysOrgPerson person : persons) {
								String userid = omsRelationService
										.getDingUserIdByEkpUserId(
												person.getFdId());
								if (StringUtil.isNotNull(userid)) {
									ekpUserId = person.getFdId();
									dingUserIds = dingUserIds + "," + userid;
								}
							}
						}
					} else if (org
							.getFdOrgType() == SysOrgElement.ORG_TYPE_PERSON) {
						logger.debug("人员Id:" + id_array[i]);
						String userId = omsRelationService
								.getDingUserIdByEkpUserId(id_array[i]);
						if (StringUtil.isNotNull(userId)) {
							ekpUserId = id_array[i];
							dingUserIds = dingUserIds + "," + userId;
						}
					}

				}

			}
			// 获取流程主题
			String docSubject = (String) DingUtil.getModelPropertyString(
					baseModel,
					"docSubject", "", null);
			logger.warn("流程标题=>" + docSubject);
			String reviewMainId = baseModel.getFdId();
			logger.warn("流程主文档fdId=>" + reviewMainId);
			JSONObject input = new JSONObject();
			// 获取请假人信息
			IBaseService obj = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
			Object kmReviewMainObject = obj
					.findByPrimaryKey(reviewMainId);
			Class clazz = kmReviewMainObject.getClass();
			Method method = clazz.getMethod("getDocCreator");
			SysOrgPerson docCreator = (SysOrgPerson) method
					.invoke(kmReviewMainObject);

			Map<String, String> content = new HashMap<String, String>();
			content.put("title", "ekp考勤数据同步到钉钉过程异常");
			content.put("content", "【异常】请处理" + docCreator.getFdName()
					+ " 提交的申请：" + docSubject + " \n");

			String jumpUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix")
					+ ThirdDingUtil.getDictUrl(baseModel,
							baseModel.getFdId());
			content.put("message_url", jumpUrl);
			content.put("pc_message_url", jumpUrl);
			content.put("color", "FF9A89B9");

			logger.debug("content:" + content.toString());

			DingUtils.getDingApiService().messageSend(content, dingUserIds,
					dingUserIds, false,
					Long.valueOf(DingConfig.newInstance().getDingAgentid()),
					ekpUserId);
		} catch (Exception e) {
			logger.error("发送异常通知失败");
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 钉钉考勤同步异常通知(同步异常)
	 * 
	 * @param dataMap
	 */
	private void sengDingErrorNotify(Map<String, Object> dataMap,
			String docSubject, SysOrgPerson docCreator) {
		
		try {
			String notifyIds = DingConfig.newInstance().getAttendanceErrorNotifyOrgId();
			if(StringUtil.isNull(notifyIds)){
				logger.warn("钉钉异常通知人为空，因此不发送异常通知");
				return;
			}
			
			String[] id_array = notifyIds.split(";");
			String dingDeptIds="";
			String dingUserIds="";
			String ekpUserId = null;
			
			for(int i=0;i<id_array.length;i++){
				if(StringUtil.isNull(id_array[i])) {
                    continue;
                }
				logger.debug("通知对象id:" + id_array[i]);
				SysOrgElement org=(SysOrgElement) getSysOrgElementService().findByPrimaryKey(id_array[i]);
				if(org !=null){
					if(org.getFdOrgType()== SysOrgElement.ORG_TYPE_ORG||org.getFdOrgType()==SysOrgElement.ORG_TYPE_DEPT){
						logger.debug("部门Id:"+id_array[i]);
						String dingDeptId = omsRelationService.getDingUserIdByEkpUserId(id_array[i]);
						if(StringUtil.isNotNull(dingDeptId)){
							ekpUserId=id_array[i];
							dingDeptIds=dingDeptIds+","+dingDeptId;
						}
					}else if(org.getFdOrgType()== SysOrgElement.ORG_TYPE_POST){
						logger.debug("岗位Id:"+id_array[i]);
						SysOrgPost post = (SysOrgPost) getSysOrgPostServiceService().findByPrimaryKey(id_array[i]);
						if(post != null ){
							List<SysOrgPerson> persons = post.getFdPersons();
							for(SysOrgPerson person:persons){
								String userid = omsRelationService.getDingUserIdByEkpUserId(person.getFdId());
								if(StringUtil.isNotNull(userid)){
									ekpUserId=person.getFdId();
									dingUserIds=dingUserIds+","+userid;
								}
							}
						}
					} else if (org
							.getFdOrgType() == SysOrgElement.ORG_TYPE_PERSON) {
						logger.debug("人员Id:" + id_array[i]);
						String userId = omsRelationService
								.getDingUserIdByEkpUserId(id_array[i]);
						if (StringUtil.isNotNull(userId)) {
							ekpUserId = id_array[i];
							dingUserIds = dingUserIds + "," + userId;
						}
					}
					
				}
				
			}
			Map<String, String> content = new HashMap<String, String>();
			content.put("title", "ekp考勤数据同步到钉钉异常");
			content.put("content", "【异常】请处理" + docCreator.getFdName()
					+ " 提交的申请：" + docSubject + " \n");
			content.put("message_url", (String) dataMap.get("jump_url"));
			content.put("pc_message_url", (String) dataMap.get("jump_url"));
			content.put("color", "FF9A89B9");
			
			logger.debug("content:" + content.toString());

			DingUtils.getDingApiService().messageSend(content, dingUserIds,
					dingUserIds, false,
					Long.valueOf(DingConfig.newInstance().getDingAgentid()),
					ekpUserId);
		} catch (Exception e) {
			logger.error("发送异常通知失败");
			logger.error(e.getMessage(),e);;
		}
		
	}
}
