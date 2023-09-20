package com.landray.kmss.third.ding.listener;

import java.util.ArrayList;
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
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/***
 * 加班审批通过节点结束事件，用于处理审批通过后将结果通知钉钉
 * 
 * @author 唐有炜
 *
 */
public class OvertimeApprovalListener implements IEventListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(OvertimeApprovalListener.class);

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

	public OvertimeApprovalListener() {
		super();
	}

	public OvertimeApprovalListener(IOmsRelationService omsRelationService,
			IThirdDingLeavelogService thirdDingLeavelogService) {
		super();
		this.omsRelationService = omsRelationService;
		this.thirdDingLeavelogService = thirdDingLeavelogService;
	}

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
	 * 处理审批通过事件
	 */
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		logger.warn("处理加班审批通过结束事件,parameter:" + parameter);
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

		// 获取加班人ekp用户id
		SysOrgPerson user = (SysOrgPerson) getFieldValue(baseModel, params,
				"fdOvertimeTargets");
		String ekpUserId = user.getFdId();

		// 参数组装
		Map<String, Object> dataMap = buildParams(baseModel, params, execution);
		logger.warn("传入参数dataMap=>" + JSON.toJSONString(dataMap));

		try {
			Map<String, Object> inParam = new HashMap<>();
			inParam.putAll(dataMap);

			// 通知钉钉
			JSONObject result = thirdDingLeavelogService
					.overtimeNotifyDing(dataMap, ekpUserId);
			logger.warn("result=>" + JSONUtils.valueToString(result));

			// 入参，出参
			dataMap.put("paramMap", params);
			dataMap.put("params", inParam);
			dataMap.put("result", result);

			if (result != null && result.getInt("errcode") == 0) {
				String duration = result.getJSONObject("result")
						.getString("duration");
				logger.warn("写入加班明细之前获取请假时长,duration=>" + duration);
				dataMap.put("duration", duration);

				// 成功，写入加班日志并标记为成功
				dataMap.put("fd_reason", "加班写入钉钉考勤操作成功");
				dataMap.put("ekpUserId", ekpUserId);
				dataMap.put("docSubject", docSubject);

				ThirdDingLeavelog docMain = writeOvertimeLog(dataMap,
						DING_SUCCESS);

				// 这里是记录加班明细具体逻辑
				JSONArray jsArr = result.getJSONObject("result")
						.getJSONArray("durationDetail");
				thirdDingLeavelogService.writeOvertimeDetail(docMain, jsArr);
			} else {
				logger.warn("钉钉返回错误");
				// 失败，写入加班日志并标记为失败
				String reason = result == null ? "钉钉返回结果为空"
						: JSONUtils.valueToString(result);
				dataMap.put("fd_reason",
						"钉钉返回错误:" + reason);
				dataMap.put("ekpUserId", ekpUserId);
				dataMap.put("docSubject", docSubject);
				writeOvertimeLog(dataMap, DING_ERROR);
			}

			logger.warn("全部处理完毕");
		} catch (Exception e) {
			// 失败，写入加班日志并标记为失败
			dataMap.put("fd_reason",
					"ekp系统异常:" + e.getMessage());
			dataMap.put("ekpUserId", ekpUserId);
			writeOvertimeLog(dataMap, EKP_ERRROR);
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
		// 加班人
		SysOrgPerson applyUser = (SysOrgPerson) getFieldValue(baseModel, params,
				"fdOvertimeTargets");
		String ekpApplyUserid = applyUser.getFdId();
		// 转换成钉钉的userid
		String applyUserid = omsRelationService
				.getDingUserIdByEkpUserId(ekpApplyUserid);
		if (applyUserid != null) {
			// dataMap.put("apply_userid", applyUserid);
			dataMap.put("userid", applyUserid);
		} else {
			logger.error("钉钉不存在该用户");
		}

		// 发起人
		String ekpUserId = UserUtil.getUser().getFdId();
		String userid = omsRelationService.getDingUserIdByEkpUserId(ekpUserId);
		// dataMap.put("userid", userid);
		dataMap.put("apply_userid", userid);

		// 1加班，2:外出类，3:请假类
		dataMap.put("biz_type", "1");

		// 加班按小时算
		dataMap.put("duration_unit", "hour");

		// biz_type为1时必传，默认用预计算的加班时长，用户也可以自己填写，所以加班时长字段要传入
		String fdDurationHour = "0";
		Object fdDurationHourObj = getFieldValue(baseModel, params,
				"fdDuration");
		if (null != fdDurationHourObj
				&& StringUtil.isNotNull(fdDurationHourObj.toString())) {
			if (fdDurationHourObj instanceof ArrayList) {
				fdDurationHourObj = ((ArrayList<?>) fdDurationHourObj).get(0);
				fdDurationHourObj = fdDurationHourObj.toString();
			} else if (fdDurationHourObj instanceof Double) {
				fdDurationHourObj = (Double) fdDurationHourObj;
				fdDurationHourObj = String.valueOf(fdDurationHourObj);
			} else {
				fdDurationHour = (String) fdDurationHourObj;
			}
		}
		dataMap.put("overtime_duration", fdDurationHour);

		// 开始时间
		Date fdStartDate = (Date) getFieldValue(baseModel, params,
				"fdStartDate");
		dataMap.put("from_time",
				DateUtil.convertDateToString(fdStartDate, "yyyy-MM-dd HH:mm"));

		// 结束时间
		Date fdEndDate = (Date) getFieldValue(baseModel, params,
				"fdEndDate");
		dataMap.put("to_time",
				DateUtil.convertDateToString(fdEndDate, "yyyy-MM-dd HH:mm"));

		dataMap.put("tag_name", "加班");
		dataMap.put("sub_type", "");
		dataMap.put("calculate_model", 1);

		// 审批单ID
		dataMap.put("approve_id", baseModel.getFdId());

		// 审批单跳转链接
		dataMap.put("jump_url",
				ResourceUtil.getKmssConfigString("kmss.urlPrefix") +
						ThirdDingUtil.getDictUrl(execution.getMainModel(), baseModel.getFdId()));

		// biz_type为1时必传，加班方式，1:转调休，2:转加班费
		dataMap.put("overtime_to_more", 1);
		return dataMap;
	}

	/**
	 * 写入加班日志
	 * 
	 * @throws Exception
	 */
	private ThirdDingLeavelog writeOvertimeLog(Map<String, Object> dataMap,
			Integer flag) throws Exception {
		logger.warn("写入加班日志...");

		ThirdDingLeavelog thirdDingLeaveLog = new ThirdDingLeavelog();
		thirdDingLeaveLog
				.setDocSubject(String.valueOf(dataMap.get("docSubject")));
		thirdDingLeaveLog.setFdUserid(String.valueOf(dataMap.get("userid")));
		thirdDingLeaveLog
				.setFdEkpUserid(String.valueOf(dataMap.get("ekpUserId")));
		thirdDingLeaveLog.setFdBizType(Integer
				.parseInt(String.valueOf(dataMap.get("biz_type"))));
		thirdDingLeaveLog.setFdTagName(String.valueOf(dataMap.get("tag_name")));
		thirdDingLeaveLog.setFdSubType(String.valueOf(dataMap.get("sub_type")));
		thirdDingLeaveLog.setFdDurationUnit(
				String.valueOf(dataMap.get("duration_unit")));
		thirdDingLeaveLog
				.setFdDuration(String.valueOf(dataMap.get("duration")));
		thirdDingLeaveLog
				.setFdFromTime(String.valueOf(dataMap.get("from_time")));
		thirdDingLeaveLog.setFdToTime(String.valueOf(dataMap.get("to_time")));
		thirdDingLeaveLog
				.setFdApproveId(String.valueOf(dataMap.get("approve_id")));
		thirdDingLeaveLog.setFdJumpUrl(String.valueOf(dataMap.get("jump_url")));
		thirdDingLeaveLog.setFdIstrue(String.valueOf(flag));
		thirdDingLeaveLog.setFdReason(String.valueOf(dataMap.get("fd_reason")));
		thirdDingLeaveLog
				.setFdParamMap(JSON.toJSONString(dataMap.get("paramMap")));
		Map<String, Object> inParamMap = new HashMap<>();
		inParamMap.put("url", "attendance/approve/finish");
		inParamMap.put("params",
				JSON.toJSONString(dataMap.get("params")));
		thirdDingLeaveLog.setFdParams(JSON.toJSONString(inParamMap));
		thirdDingLeaveLog.setDocCreateTime(new Date());
		thirdDingLeaveLog.setDocAlterTime(new Date());
		thirdDingLeaveLog.setFdResult(String.valueOf(dataMap.get("result")));
		thirdDingLeaveLog.setFdSendTime(1);

		thirdDingLeavelogService.add(thirdDingLeaveLog);

		return thirdDingLeaveLog;
	}
}
