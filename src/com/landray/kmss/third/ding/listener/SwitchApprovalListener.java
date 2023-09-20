package com.landray.kmss.third.ding.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.model.ThirdDingLeavelog;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingLeavelogService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

public class SwitchApprovalListener implements IEventListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SwitchApprovalListener.class);

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
	private IThirdDingLeavelogService thirdDingLeavelogService;

	public void setOmsRelationService(IOmsRelationService omsRelationService) {
		this.omsRelationService = omsRelationService;
	}

	public void setThirdDingLeavelogService(
			IThirdDingLeavelogService thirdDingLeavelogService) {
		this.thirdDingLeavelogService = thirdDingLeavelogService;
	}

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		logger.warn("处理换班节点结束事件...");
		if (execution.getExecuteParameters() != null) {
			String docStatus = execution.getExecuteParameters()
					.getExpectMainModelStatus();
			if (SysDocConstant.DOC_STATUS_DISCARD.equals(docStatus)) {
				logger.error("流程为废弃状态不执行钉钉流程同步和日志写入");
				return;
			}
		}
		IBaseModel baseModel = execution.getMainModel();
		JSONObject params = JSONObject.fromObject(parameter);

		// 获取流程主题
		String docSubject = (String) DingUtil.getModelPropertyString(baseModel,
				"docSubject", "", null);
		logger.warn("流程标题=>" + docSubject);

		Map<String, Object> dataMap = new HashMap<>();

		// 发起人
		String ekpUserId = UserUtil.getUser().getFdId();
		String userid = omsRelationService.getDingUserIdByEkpUserId(ekpUserId);
		dataMap.put("userid", userid);

		// 换班人
		SysOrgPerson applyUser = (SysOrgPerson) getFieldValue(baseModel, params,
				"fdApplyUserid");
		String ekpApplyUserId = applyUser.getFdId();
		// 转换成钉钉的userid
		String applyUserid = omsRelationService
				.getDingUserIdByEkpUserId(ekpApplyUserId);
		if (applyUserid != null) {
			dataMap.put("apply_userid", applyUserid);
		} else {
			logger.error("钉钉不存在换班人");
		}

		// 被换班人
		SysOrgPerson targetUser = (SysOrgPerson) getFieldValue(baseModel,
				params,
				"fdTargetUserid");
		String ekpTargetUserid = targetUser.getFdId();
		// 转换成钉钉的userid
		String targetUserid = omsRelationService
				.getDingUserIdByEkpUserId(ekpTargetUserid);
		if (targetUserid != null) {
			dataMap.put("target_userid", targetUserid);
		} else {
			logger.error("钉钉不存在被换班人");
		}

		Date fdSwitchDate = (Date) getFieldValue(baseModel, params,
				"fdSwitchDate");
		dataMap.put("switch_date",
				DateUtil.convertDateToString(fdSwitchDate, "yyyy-MM-dd"));

		Date fdRebackDate = (Date) getFieldValue(baseModel, params,
				"fdRebackDate");
		dataMap.put("reback_date",
				DateUtil.convertDateToString(fdRebackDate, "yyyy-MM-dd"));

		dataMap.put("tag_name", "换班");

		String fdAapproveId = baseModel.getFdId();
		// 审批单ID
		dataMap.put("approve_id", baseModel.getFdId());

		// 审批单跳转链接
		String fdJumpUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix")
				+ ThirdDingUtil.getDictUrl(execution.getMainModel(), baseModel.getFdId());

		try {
			// 通知钉钉
			JSONObject result = thirdDingLeavelogService
					.switchNotifyDing(dataMap, applyUserid);

			// 记录日志
			ThirdDingLeavelog thirdDingLeaveLog = new ThirdDingLeavelog();
			thirdDingLeaveLog.setDocSubject(docSubject);
			thirdDingLeaveLog.setFdEkpUserid(ekpUserId);
			thirdDingLeaveLog.setFdUserid(userid);
			thirdDingLeaveLog.setFdTagName("换班");
			thirdDingLeaveLog.setFdParamMap(JSON.toJSONString(params));
			Map<String, Object> inParamMap = new HashMap<>();
			inParamMap.put("url", "approve/schedule/switch");
			inParamMap.put("params", dataMap);
			thirdDingLeaveLog.setFdParams(JSON.toJSONString(inParamMap));
			thirdDingLeaveLog.setDocCreateTime(new Date());
			thirdDingLeaveLog.setDocAlterTime(new Date());
			thirdDingLeaveLog.setFdResult(JSONUtils.valueToString(result));
			thirdDingLeaveLog.setFdApproveId(fdAapproveId);
			thirdDingLeaveLog.setFdJumpUrl(fdJumpUrl);

			if (result != null && result.getInt("errcode") == 0) {
				logger.warn("换班处理成功");
				thirdDingLeaveLog.setFdIstrue(String.valueOf(DING_SUCCESS));
				thirdDingLeaveLog.setFdReason("换班写入钉钉考勤成功");
			} else {
				logger.error("换班钉钉处理失败");
				thirdDingLeaveLog.setFdIstrue(String.valueOf(DING_ERROR));
				thirdDingLeaveLog.setFdReason("换班钉钉处理失败");
			}

			thirdDingLeavelogService.add(thirdDingLeaveLog);
			logger.warn("换班处理完毕");
		} catch (Exception e) {
			logger.error("系统异常", e);
			e.printStackTrace();
		}
	}

	/**
	 * 获取字段值
	 * 
	 * @param baseModel
	 * @param params
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private Object getFieldValue(IBaseModel baseModel, JSONObject params,
			String key) throws Exception {
		ISysMetadataParser parse = (ISysMetadataParser) SpringBeanUtil
				.getBean("sysMetadataParser");
		Object result = null;
		if (params.containsKey(key)) {
			String fdKey = params.getJSONObject(key)
					.getString("value");
			result = parse.getFieldValue(baseModel, fdKey, false);

		}
		return result;
	}
}
