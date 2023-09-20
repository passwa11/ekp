package com.landray.kmss.third.ding.listener;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.third.ding.model.ThirdDingLeavelog;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingLeavelogService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.slf4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/***
 * 请假审批通过节点结束事件，用于处理审批通过后将结果通知钉钉
 * 
 * @author 唐有炜
 *
 */
public class LeaveApprovalListener implements IEventListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LeaveApprovalListener.class);

	public LeaveApprovalListener(IOmsRelationService omsRelationService,
			IThirdDingLeavelogService thirdDingLeavelogService) {
		super();
		this.omsRelationService = omsRelationService;
		this.thirdDingLeavelogService = thirdDingLeavelogService;
	}

	public LeaveApprovalListener() {
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
		logger.warn("处理审批通过结束事件,parameter:" + parameter);
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
				"fdLeaveTargets");
		String ekpUserId = user.getFdId();

		// 参数组装
		Map<String, Object> dataMap = buildParams(baseModel, params, execution);
		logger.info("传入参数dataMap=>" + JSON.toJSONString(dataMap));

		try {
			Map<String, Object> inParam = new HashMap<>();
			inParam.putAll(dataMap);

			// 通知钉钉
			JSONObject result = thirdDingLeavelogService
					.leaveNotifyDing(dataMap, ekpUserId);
			logger.warn("result=>" + JSONUtils.valueToString(result));
			if (result != null && result.getInt("errcode") == 850012) {
				// params=>{"duration_unit":"day","biz_type":"3",
				// "jump_url":"http://chenhw1.myekp.com/km/review/km_review_main/kmReviewMain.do?method=view&fdId=1706d4ddabf2501d57ca7ab4520bb01e",
				// "from_time":"2020-04-01","calculate_model":1,"userid":"040056415837728275",
				// "sub_type":"年假","tag_name":"请假","approve_id":"1706d4ddabf2501d57ca7ab4520bb01e","to_time":"2020-06-01"}
				logger.debug("请假时长超过钉钉时长限制，将分拆日期请求到钉钉，以绕开该限制");

				Date fromDate = new Date();
				Date toDate = new Date();

				String tempStartTimeHalf = "AM";
				String tenpEndTimeHalf = "AM";
				if ("day".equals(dataMap.get("duration_unit"))) {
					fromDate = DateUtil.convertStringToDate(
							(String) dataMap.get("from_time"), "yyyy-MM-dd");
					toDate = DateUtil.convertStringToDate(
							(String) dataMap.get("to_time"), "yyyy-MM-dd");
				} else if ("halfDay".equals(dataMap.get("duration_unit"))) {

					String fromTime = (String) dataMap.get("from_time");
					String toTime = (String) dataMap.get("to_time");
					logger.debug("fromTime:" + fromTime);
					logger.debug("toTime:" + toTime);
					if (StringUtil.isNotNull(fromTime)
							&& fromTime.contains("AM")) {
						tempStartTimeHalf = "AM";
						fromTime = fromTime.substring(0,
								fromTime.indexOf(" AM"));
					} else if (StringUtil.isNotNull(fromTime)
							&& fromTime.contains("PM")) {
						tempStartTimeHalf = "PM";
						fromTime = fromTime.substring(0,
								fromTime.indexOf(" PM"));
					}
					if (StringUtil.isNotNull(toTime)
							&& toTime.contains("AM")) {
						tenpEndTimeHalf = "AM";
						toTime = toTime.substring(0, toTime.indexOf(" AM"));
					} else if (StringUtil.isNotNull(toTime)
							&& toTime.contains("PM")) {
						tenpEndTimeHalf = "PM";
						toTime = toTime.substring(0, toTime.indexOf(" PM"));
					}
					fromDate = DateUtil.convertStringToDate(
							fromTime, "yyyy-MM-dd");
					toDate = DateUtil.convertStringToDate(
							toTime, "yyyy-MM-dd");

				} else {
					// 小时
					fromDate = DateUtil.convertStringToDate(
							(String) dataMap.get("from_time"),
							"yyyy-MM-dd HH:mm");
					toDate = DateUtil.convertStringToDate(
							(String) dataMap.get("to_time"),
							"yyyy-MM-dd HH:mm");
				}

				Date tempStartDate = fromDate;
				Date tempEndDate = new Date();
				Boolean isEnd = true;
				// String tempStartTimeHalf = fdStartTimeHalf;
				// String tenpEndTimeHalf = fdEndTimeHalf;
				do {

					tempEndDate = DateUtil.getNextDay(tempStartDate, 28);

					if (toDate.before(tempEndDate)) {
						tempEndDate = toDate;
						isEnd = false;
					}
					if ("day".equals(dataMap.get("duration_unit"))) {
						dataMap.put("from_time",
								DateUtil.convertDateToString(tempStartDate,
										"yyyy-MM-dd"));
						dataMap.put("to_time",
								DateUtil.convertDateToString(tempEndDate,
										"yyyy-MM-dd"));
					} else if ("halfDay"
							.equals(dataMap.get("duration_unit"))) {

						dataMap.put("from_time",
								DateUtil.convertDateToString(tempStartDate,
										"yyyy-MM-dd") + " "
										+ tempStartTimeHalf);
						dataMap.put("to_time",
								DateUtil.convertDateToString(tempEndDate,
										"yyyy-MM-dd") + " " + tenpEndTimeHalf);

					} else {
						dataMap.put("from_time",
								DateUtil.convertDateToString(tempStartDate,
										"yyyy-MM-dd HH:mm"));
						dataMap.put("to_time",
								DateUtil.convertDateToString(tempEndDate,
										"yyyy-MM-dd HH:mm"));
					}

					logger.warn(
							"dataMap：" + dataMap.toString());
					result = thirdDingLeavelogService.leaveNotifyDing(dataMap,
							ekpUserId);
					// 入参，出参
					dataMap.put("paramMap", params);
					dataMap.put("params", inParam);
					dataMap.put("result", result);
					if (result != null && result.getInt("errcode") == 0) {
						if (result != null && result.getInt("errcode") == 0) {
							// 这里防止钉钉成功后Ekp异常阻断流程
							try {
								// 钉钉数据回写Ekp
								if ("3".equals(dataMap.get("biz_type"))) {
									thirdDingLeavelogService
											.updateDingInfoToEkp(baseModel,
													params, result);

									// 写入请假明细
									String duration = result
											.getJSONObject("result")
											.getString("duration");
									logger.warn("写入请假明细之前获取请假时长,duration=>"
											+ duration);
									dataMap.put("duration", duration);
								}
								// 成功，写入请假日志并标记为成功
								dataMap.put("fd_reason", "请假写入钉钉考勤操作成功");
								dataMap.put("ekpUserId", ekpUserId);
								dataMap.put("docSubject", docSubject);
								ThirdDingLeavelog docMain = writeLeaveLog(
										dataMap,
										DING_SUCCESS);

								// 这里是记录请假明细具体逻辑
								JSONArray jsArr = result.getJSONObject("result")
										.getJSONArray("durationDetail");
								thirdDingLeavelogService
										.writeLeaveDetail(docMain, jsArr);
							} catch (Exception e) {
								logger.error("通知完钉钉后EKP发生异常", e);
							}
						} else {
							logger.warn("钉钉返回错误");
							// 失败，写入请假日志并标记为失败
							String reason = result == null ? "钉钉返回结果为空"
									: JSONUtils.valueToString(result);
							dataMap.put("fd_reason",
									"钉钉返回错误:" + reason);
							dataMap.put("ekpUserId", ekpUserId);
							dataMap.put("docSubject", docSubject);
							writeLeaveLog(dataMap, DING_ERROR);
						}
					}
					tempStartDate = tempEndDate;
					if ("day".equals(dataMap.get("duration_unit"))) {
						tempStartDate = DateUtil.getNextDay(tempStartDate, 1);
					} else if ("halfDay"
							.equals(dataMap.get("duration_unit"))) {
						if ("PM".equals(tenpEndTimeHalf)) {
							tempStartDate = DateUtil.getNextDay(tempStartDate,
									1);
						}
					}
					tempStartTimeHalf = "AM".equals(tenpEndTimeHalf) ? "PM"
							: "AM";

				} while (isEnd);

				return;
			}

			// 入参，出参
			dataMap.put("paramMap", params);
			dataMap.put("params", inParam);
			dataMap.put("result", result);

			// 请假数据回写表单
			logger.info("请假数据回写表单...");
			if (result != null && result.getInt("errcode") == 0) {
				// 这里防止钉钉成功后Ekp异常阻断流程
				try {
					// 钉钉数据回写Ekp
					if ("3".equals(dataMap.get("biz_type"))) {
						thirdDingLeavelogService.updateDingInfoToEkp(baseModel,
								params, result);

						// 写入请假明细
						String duration = result.getJSONObject("result")
								.getString("duration");
						logger.warn("写入请假明细之前获取请假时长,duration=>" + duration);
						dataMap.put("duration", duration);
					}
					// 成功，写入请假日志并标记为成功
					dataMap.put("fd_reason", "请假写入钉钉考勤操作成功");
					dataMap.put("ekpUserId", ekpUserId);
					dataMap.put("docSubject", docSubject);
					ThirdDingLeavelog docMain = writeLeaveLog(dataMap,
							DING_SUCCESS);

					// 这里是记录请假明细具体逻辑
					JSONArray jsArr = result.getJSONObject("result")
							.getJSONArray("durationDetail");
					thirdDingLeavelogService.writeLeaveDetail(docMain, jsArr);
				} catch (Exception e) {
					logger.error("通知完钉钉后EKP发生异常", e);
				}
			} else {
				logger.warn("钉钉返回错误");
				// 失败，写入请假日志并标记为失败
				String reason = result == null ? "钉钉返回结果为空"
						: JSONUtils.valueToString(result);
				dataMap.put("fd_reason",
						"钉钉返回错误:" + reason);
				dataMap.put("ekpUserId", ekpUserId);
				dataMap.put("docSubject", docSubject);
				writeLeaveLog(dataMap, DING_ERROR);
			}

			logger.warn("全部处理完毕");

		} catch (Exception e) {
			// 失败，写入请假日志并标记为失败
			dataMap.put("fd_reason",
					"exp系统异常:" + e.getMessage());
			dataMap.put("ekpUserId", ekpUserId);
			writeLeaveLog(dataMap, EKP_ERRROR);
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
	 * @param execution
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> buildParams(IBaseModel baseModel,
			JSONObject params, EventExecutionContext execution) throws Exception {
		Map<String, Object> dataMap = new HashMap<>();
		// 申请人
		SysOrgPerson user = (SysOrgPerson) getFieldValue(baseModel, params,
				"fdLeaveTargets");
		String ekpUserId = user.getFdId();
		// 转换成钉钉的userid
		String userid = omsRelationService.getDingUserIdByEkpUserId(ekpUserId);
		if (userid != null) {
			dataMap.put("userid", userid);
		} else {
			logger.error("钉钉不存在该用户");
		}

		// 2:外出类，3:请假类
		dataMap.put("biz_type", "3");

		// 时长单位
		String durationUnit = String.valueOf(getFieldValue(baseModel, params,
				"fdDurationUnit"));
		if (StringUtil.isNotNull(durationUnit) && durationUnit.contains(".0")) {
			durationUnit = durationUnit.substring(0,
					durationUnit.indexOf(".0"));
		}
		Integer type = Integer.parseInt(durationUnit);
		switch (type) {
		case 1: {// 天
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
			break;
		}
		case 2: {// 半天
			dataMap.put("duration_unit", "halfDay");
			// 开始时间
			Date fdStartDate = (Date) getFieldValue(baseModel, params,
					"fdStartDate");
			String fdStartTimeHalf = (String) getFieldValue(baseModel, params,
					"fdStartTimeHalf");
			dataMap.put("from_time",
					DateUtil.convertDateToString(fdStartDate, "yyyy-MM-dd")
							+ " "
							+ fdStartTimeHalf);

			// 结束时间
			Date fdEndDate = (Date) getFieldValue(baseModel, params,
					"fdEndDate");
			String fdEndTimeHalf = (String) getFieldValue(baseModel, params,
					"fdEndTimeHalf");
			dataMap.put("to_time",
					DateUtil.convertDateToString(fdEndDate, "yyyy-MM-dd") + " "
							+ fdEndTimeHalf);
			break;
		}
		case 3: {// 小时
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
			break;
		}
		default:
			break;
		}

		// 审批单类型名称
		dataMap.put("tag_name", "请假");

		// 审批单子类型ID，获取的是请假编码
		Object fdOffType2 = getFieldValue(baseModel, params,
				"fdOffType");
		String fdOffType = String.valueOf(fdOffType2);
		
		if (StringUtil.isNotNull(fdOffType) && fdOffType.contains(".0")) {
			fdOffType = fdOffType.substring(0, fdOffType.indexOf(".0"));
		}
	
		// 计算类型，从假期管理配置读取
		ISysTimeLeaveRuleService sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil
				.getBean("sysTimeLeaveRuleService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysTimeLeaveRule.fdIsAvailable=true and fdSerialNo=:fdOffType");
		hqlInfo.setParameter("fdOffType", fdOffType);
		SysTimeLeaveRule sysTimeLeaveRule = (SysTimeLeaveRule) sysTimeLeaveRuleService.findFirstOne(hqlInfo);
		if (sysTimeLeaveRule != null) {
			dataMap.put("calculate_model",
					sysTimeLeaveRule.getFdStatDayType() == 2 ? 0 : 1);

			// 审批单子类型名称
			dataMap.put("sub_type", sysTimeLeaveRule.getFdName());
		} else {
			logger.debug("假期管理配置读取不到请假类型，将取请假类型显示值作为请假类型同步到钉钉");
			ISysMetadataParser parse = (ISysMetadataParser) SpringBeanUtil
					.getBean("sysMetadataParser");
			SysDictModel dictModel = parse.getDictModel(baseModel);
			String fdKey = params.getJSONObject("fdOffType")
					.getString("value");
			SysDictCommonProperty property = dictModel.getPropertyMap()
					.get(fdKey);
			String vals = property.getEnumValues(); // 事假2|101;产假2|102;病假2|103;丧假2|104
			logger.debug("下拉菜单的值：" + vals);
			String[] arrs = vals.split(";");
			for (int i = 0; i < arrs.length; i++) {
				if (!arrs[i].contains(fdOffType)) {
                    continue;
                }
				String[] temp = arrs[i].split("\\|");
				if (temp.length > 0 && temp[1].equals(fdOffType)) {
					dataMap.put("sub_type", temp[0]);
					break;
				}
			}
			String unit = (String) dataMap.get("duration_unit");
			if ("halfDay".equals(unit)) {
				dataMap.put("calculate_model", 0);
			} else {
				dataMap.put("calculate_model", 1);
			}
		}

		// 审批单ID
		dataMap.put("approve_id", baseModel.getFdId());

		// 审批单跳转链接
		dataMap.put("jump_url",
				ResourceUtil.getKmssConfigString("kmss.urlPrefix")
						+ ThirdDingUtil.getDictUrl(execution.getMainModel(), baseModel.getFdId()));
		return dataMap;
	}

	/**
	 * 写入请假日志
	 * 
	 * @throws Exception
	 */
	private ThirdDingLeavelog writeLeaveLog(Map<String, Object> dataMap,
			Integer flag) throws Exception {
		logger.warn("写入请假日志...");

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
		String res = String.valueOf(dataMap.get("result"));
		if (StringUtil.isNotNull(res) && res.length() >= 1900) {
			logger.warn("【钉钉考勤--请假】" + res);
			res = res.substring(0, 1900) + "...";
		}
		thirdDingLeaveLog.setFdResult(res);
		thirdDingLeaveLog.setFdSendTime(1);

		thirdDingLeavelogService.add(thirdDingLeaveLog);

		return thirdDingLeaveLog;
	}
}
