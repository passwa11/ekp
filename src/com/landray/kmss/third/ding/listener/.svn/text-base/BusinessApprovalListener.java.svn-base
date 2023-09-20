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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/***
 * 外出审批通过节点结束事件，用于处理审批通过后将结果通知钉钉
 * 
 * @author 唐有炜
 *
 */
public class BusinessApprovalListener implements IEventListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(BusinessApprovalListener.class);

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

	public BusinessApprovalListener() {
		super();
	}

	public BusinessApprovalListener(IOmsRelationService omsRelationService,
			IThirdDingLeavelogService thirdDingLeavelogService) {
		super();
		this.omsRelationService = omsRelationService;
		this.thirdDingLeavelogService = thirdDingLeavelogService;
	}

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
		logger.warn("处理外出审批通过结束事件,parameter:" + parameter);
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

		// 参数组装
		HashMap<String, Object> _dataMap = buildParams(baseModel, params,
				execution);
		// 添加申请人信息，申请人有可能是数组
		SysOrgPerson[] arr2;
		try {
			Object persons = getFieldValue(baseModel, params,
					"fdLeaveTargets");
			if (persons instanceof ArrayList) {
				logger.debug("多人申请出差或外出");
				ArrayList<SysOrgPerson> personList = (ArrayList<SysOrgPerson>) persons;

				for (SysOrgPerson person : personList) {
					HashMap<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.putAll(_dataMap);
					// 申请人
					String apply_ekpUserId = person.getFdId();
					// 审批单ID
					dataMap.put("approve_id",
							baseModel.getFdId() + "-" + apply_ekpUserId);

					// 转换成钉钉的userid
					String userid = omsRelationService
							.getDingUserIdByEkpUserId(apply_ekpUserId);
					if (userid != null) {
						dataMap.put("userid", userid);
						ProcessingApplication(dataMap, params, apply_ekpUserId,
								docSubject);

					} else {
						logger.error("钉钉不存在该用户 fdId:" + apply_ekpUserId);
					}

				}

			} else if (persons instanceof SysOrgPerson) {
				logger.debug("单人申请出差或外出");
				// 申请人
				String apply_ekpUserId = ((SysOrgPerson) persons).getFdId();
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.putAll(_dataMap);
				// 审批单ID
				dataMap.put("approve_id", baseModel.getFdId());
				// 转换成钉钉的userid
				String userid = omsRelationService
						.getDingUserIdByEkpUserId(apply_ekpUserId);
				if (userid != null) {
					dataMap.put("userid", userid);
					ProcessingApplication(dataMap, params, apply_ekpUserId,
							docSubject);

				} else {
					logger.error("钉钉不存在该用户");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	private void ProcessingApplication(Map<String, Object> dataMap,
			JSONObject params, String ekpUserId, String docSubject) {

		logger.info("传入参数dataMap=>" + JSON.toJSONString(dataMap));
		try {

			// 通知钉钉
			JSONObject result = thirdDingLeavelogService
					.bussNotifyDing(dataMap, ekpUserId);

			Map<String, Object> inParam = new HashMap<>();
			inParam.putAll(dataMap);
			dataMap.put("paramMap", params);
			dataMap.put("params", inParam);
			// 入参，出参
			dataMap.put("result", result);

			// 请假数据回写表单
			logger.info("请假数据回写表单...");
			if (result != null && result.getInt("errcode") == 0) {
				String duration = result.getJSONObject("result")
						.getString("duration");
				logger.warn("写入请假明细之前获取请假时长,duration=>" + duration);
				dataMap.put("duration", duration);

				// 成功，写入外出日志并标记为成功
				String bussType = "day"
						.equals(String.valueOf(inParam.get("duration_unit"))) ? "出差" : "外出";
				dataMap.put("fd_reason", bussType + "外出写入钉钉考勤操作成功");
				dataMap.put("ekpUserId", ekpUserId);
				dataMap.put("docSubject", docSubject);

				ThirdDingLeavelog docMain = writeBussLog(dataMap, DING_SUCCESS);

				// 这里是记录请假明细具体逻辑
				JSONArray jsArr = result.getJSONObject("result")
						.getJSONArray("durationDetail");
				thirdDingLeavelogService.writeBussDetail(docMain, jsArr);
			} else {
				logger.warn("钉钉返回错误");
				// 失败，写入外出日志并标记为失败
				String reason = result == null ? "钉钉返回结果为空"
						: JSONUtils.valueToString(result);
				dataMap.put("fd_reason",
						"钉钉返回错误:" + reason);
				dataMap.put("ekpUserId", ekpUserId);
				dataMap.put("docSubject", docSubject);
				writeBussLog(dataMap, DING_ERROR);
			}

			logger.warn("全部处理完毕");
		} catch (Exception e) {
			// 失败，写入外出日志并标记为失败
			logger.error(e.getMessage(), e);
			dataMap.put("fd_reason",
					"ekp系统异常:" + e.getMessage());
			dataMap.put("ekpUserId", ekpUserId);
			try {
				writeBussLog(dataMap, EKP_ERRROR);
			} catch (Exception e1) {
				logger.error(e1.getMessage(), e1);
				e1.printStackTrace();
			}

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
	private HashMap<String, Object> buildParams(IBaseModel baseModel,
			JSONObject params, EventExecutionContext execution) throws Exception {
		HashMap<String, Object> dataMap = new HashMap<>();


		// 2:外出类，3:请假类
		dataMap.put("biz_type", "2");

		// 外出时长单位
		Integer type = params.getInt("fdDurationUnit");

		if (type == 1) {
			dataMap.put("duration_unit", "day");
			// 开始时间
			Date fdStartDate = (Date) getFieldValue(baseModel, params,
					"fdStartDate");
			dataMap.put("from_time",
					DateUtil.convertDateToString(fdStartDate, "yyyy-MM-dd"));

			// 结束时间
			Date fdEndDate = (Date) getFieldValue(baseModel, params,
					"fdEndDate");
			dataMap.put("to_time",
					DateUtil.convertDateToString(fdEndDate, "yyyy-MM-dd"));

			dataMap.put("tag_name", "出差");
		} else {
			dataMap.put("duration_unit", "hour");
			// 开始时间
			Date fdStartDate = (Date) getFieldValue(baseModel, params,
					"fdStartDate");
			Date fdStartTime = (Date) getFieldValue(baseModel, params,
					"fdStartTime");
			dataMap.put("from_time",
					DateUtil.convertDateToString(fdStartDate, "yyyy-MM-dd")
							+ " " + DateUtil.convertDateToString(fdStartTime,
									"HH:mm"));

			// 结束时间
			Date fdEndDate = (Date) getFieldValue(baseModel, params,
					"fdEndDate");
			Date fdEndTime = (Date) getFieldValue(baseModel, params,
					"fdEndTime");
			dataMap.put("to_time",
					DateUtil.convertDateToString(fdEndDate, "yyyy-MM-dd") + " "
							+ DateUtil.convertDateToString(fdEndTime, "HH:mm"));

			dataMap.put("tag_name", "外出");
		}

		dataMap.put("sub_type", "");
		dataMap.put("calculate_model", 1);



		// 审批单跳转链接
		dataMap.put("jump_url",
				ResourceUtil.getKmssConfigString("kmss.urlPrefix")
						+ ThirdDingUtil.getDictUrl(execution.getMainModel(), baseModel.getFdId()));
		return dataMap;
	}

	/**
	 * 写入外出日志
	 * 
	 * @throws Exception
	 */
	private ThirdDingLeavelog writeBussLog(Map<String, Object> dataMap,
			Integer flag) throws Exception {
		logger.warn("写入外出日志...");

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
