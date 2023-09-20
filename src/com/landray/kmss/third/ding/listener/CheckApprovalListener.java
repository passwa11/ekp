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

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/***
 * 补卡节点结束事件
 * 
 * @author 唐有炜
 *
 */
public class CheckApprovalListener implements IEventListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CheckApprovalListener.class);
			
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
	/**
	 * 处理补卡事件
	 */
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		logger.warn("处理补卡节点结束事件,parameter:" + parameter);
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

		// 获取ekp用户id
		SysOrgPerson user = (SysOrgPerson) getFieldValue(baseModel, params,
				"fdCheckTargets");
		String ekpUserId = user.getFdId();

		// 审批单跳转链接
		String jump_url = ResourceUtil.getKmssConfigString("kmss.urlPrefix")
				+ "/ding.do?method=view&fdId="
				+ baseModel.getFdId();

		// 参数组装
		Map<String, Object> dataMap = buildParams(baseModel, params, execution);
		logger.info("传入参数dataMap=>" + JSON.toJSONString(dataMap));

		try {
			// 通知钉钉
			JSONObject result = thirdDingLeavelogService
					.checkNotifyDing(dataMap, ekpUserId);

			// 记录日志
			IThirdDingLeavelogService thirdDingLeavelogService = (IThirdDingLeavelogService) SpringBeanUtil
					.getBean("thirdDingLeavelogService");
			ThirdDingLeavelog thirdDingLeaveLog = new ThirdDingLeavelog();
			thirdDingLeaveLog.setFdEkpUserid(ekpUserId);
			thirdDingLeaveLog.setDocSubject(docSubject);
			thirdDingLeaveLog.setFdJumpUrl(jump_url);
			thirdDingLeaveLog
					.setFdUserid(String.valueOf(dataMap.get("userid")));
			thirdDingLeaveLog.setFdTagName("补卡");
			thirdDingLeaveLog.setFdParamMap(JSON.toJSONString(params));
			Map<String, Object> inParamMap = new HashMap<>();
			inParamMap.put("url", "attendance/approve/cancel");
			inParamMap.put("params",
					JSON.toJSONString(dataMap));
			thirdDingLeaveLog.setFdParams(JSON.toJSONString(inParamMap));
			thirdDingLeaveLog.setDocCreateTime(new Date());
			thirdDingLeaveLog.setDocAlterTime(new Date());
			thirdDingLeaveLog.setFdResult(JSONUtils.valueToString(result));
			thirdDingLeaveLog.setFdSendTime(1);

			if (result != null && result.getInt("errcode") == 0) {
				// 成功，写入假期管理明细并标记为成功
				logger.info("补卡");
				thirdDingLeaveLog.setFdIstrue(String.valueOf(DING_SUCCESS));
				thirdDingLeaveLog.setFdReason("补卡写入钉钉考勤重试成功");
			} else {
				logger.warn("钉钉返回错误");
				// 失败，写入假期管理明细并标记为失败
				thirdDingLeaveLog.setFdIstrue(String.valueOf(DING_ERROR));
				thirdDingLeaveLog.setFdReason("补卡钉钉返回错误");
			}

			thirdDingLeavelogService.add(thirdDingLeaveLog);
			logger.warn("全部处理完毕");
		} catch (Exception e) {
			// 失败
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

	/**
	 * 组装参数
	 * 
	 * @param baseModel
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> buildParams(IBaseModel baseModel,
			JSONObject params, EventExecutionContext execution) throws Exception {
		Map<String, Object> dataMap = new HashMap<>();
		// 补卡人
		SysOrgPerson user = (SysOrgPerson) getFieldValue(baseModel, params,
				"fdCheckTargets");
		String ekpUserId = user.getFdId();
		// 转换成钉钉的userid
		String userid = omsRelationService.getDingUserIdByEkpUserId(ekpUserId);
		if (userid != null) {
			dataMap.put("userid", userid);
		} else {
			logger.error("钉钉不存在该用户");
		}

		// 班次日期
		Date fdStartDate = (Date) getFieldValue(baseModel, params,
				"fdWorkDate");
		dataMap.put("work_date",
				DateUtil.convertDateToString(fdStartDate, "yyyy-MM-dd"));

		// 排班ID
		String tempId = String.valueOf(getFieldValue(baseModel, params,
				"fdPunchId"));
		if (tempId.contains("_")) {
			tempId = tempId.substring(0, tempId.indexOf("_"));
		}
		Long fdPunchId = Long.parseLong(tempId);
		dataMap.put("punch_id", fdPunchId);

		// 排班时间
		Date fdPunchCheckTime = DateUtil.convertStringToDate(
				String.valueOf(getFieldValue(baseModel, params,
						"fdPunchCheckTime")));
		dataMap.put("punch_check_time", DateUtil
				.convertDateToString(fdPunchCheckTime, "yyyy-MM-dd HH:mm"));
		
		// 用户打卡时间
		Date fdUserCheckTime = (Date) getFieldValue(baseModel, params,
				"fdUserCheckTime");
		dataMap.put("user_check_time", DateUtil
				.convertDateToString(fdUserCheckTime, "yyyy-MM-dd HH:mm"));

		// 审批单名称
		dataMap.put("tag_name", "补卡");

		// 审批单ID
		dataMap.put("approve_id", baseModel.getFdId());

		// 审批单跳转链接
		dataMap.put("jump_url",
				ResourceUtil.getKmssConfigString("kmss.urlPrefix")
						+ ThirdDingUtil.getDictUrl(execution.getMainModel(), baseModel.getFdId()));
		return dataMap;
	}
}
