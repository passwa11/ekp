package com.landray.kmss.third.ding.listener.attendance;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.response.OapiProcessWorkrecordCreateResponse;
import com.dingtalk.api.response.OapiProcessWorkrecordUpdateResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingDinstanceXform;
import com.landray.kmss.third.ding.model.ThirdDingDtemplateXform;
import com.landray.kmss.third.ding.model.ThirdDingLeavelog;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingDinstanceXformService;
import com.landray.kmss.third.ding.service.IThirdDingDtemplateXformService;
import com.landray.kmss.third.ding.service.IThirdDingLeavelogService;
import com.landray.kmss.third.ding.util.*;
import com.landray.kmss.third.ding.xform.util.ThirdDingXFormTemplateUtil;
import com.landray.kmss.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;
import java.util.*;

/***
 * 钉钉考勤套件审批通过节点结束事件，用于处理审批通过后将结果通知钉钉
 * 
 *
 */
public class AttendenceLeaveApprovalListener  implements IEventListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AttendenceLeaveApprovalListener.class);

	public AttendenceLeaveApprovalListener(IOmsRelationService omsRelationService,
			IThirdDingLeavelogService thirdDingLeavelogService) {
		super();
		this.omsRelationService = omsRelationService;
		this.thirdDingLeavelogService = thirdDingLeavelogService;
	}


	public AttendenceLeaveApprovalListener() {
		super();
	}

	private IThirdDingDinstanceXformService thirdDingDinstanceXformService;

	public IThirdDingDinstanceXformService getThirdDingDinstanceXformService() {
		if (thirdDingDinstanceXformService == null) {
			thirdDingDinstanceXformService = (IThirdDingDinstanceXformService) SpringBeanUtil
					.getBean("thirdDingDinstanceXformService");
		}
		return thirdDingDinstanceXformService;
	}

	protected ISysOrgPersonService sysOrgPersonService;

	protected ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	private IThirdDingDtemplateXformService thirdDingDtemplateXformService;

	public IThirdDingDtemplateXformService getThirdDingDtemplateXformService() {
		if (thirdDingDtemplateXformService == null) {
			thirdDingDtemplateXformService = (IThirdDingDtemplateXformService) SpringBeanUtil
					.getBean("thirdDingDtemplateXformService");
		}
		return thirdDingDtemplateXformService;
	}

	public void setThirdDingDtemplateXformService(
			IThirdDingDtemplateXformService thirdDingDtemplateXformService) {
		this.thirdDingDtemplateXformService = thirdDingDtemplateXformService;
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

	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
					.getBean("sysOrgElementService");
		}
		return sysOrgElementService;

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
		TransactionStatus transactionStatus =null;
		try {
			//Thread.sleep(5000);
			IBaseModel baseModel = execution.getMainModel();
			transactionStatus = TransactionUtils.beginNewTransaction();
			// 更新实例状态
			updateInstance(baseModel);
			TransactionUtils.getTransactionManager().commit(transactionStatus);
			logger.warn("处理审批通过结束事件,parameter:" + parameter);
			if (execution.getExecuteParameters() != null) {
				String docStatus = execution.getExecuteParameters()
						.getExpectMainModelStatus();
				if (SysDocConstant.DOC_STATUS_DISCARD.equals(docStatus)) {
					logger.error("流程为废弃状态不执行钉钉流程同步和日志写入");
					return;
				}
			}
			String type = ThirdDingXFormTemplateUtil
					.getXFormTemplateType(baseModel);
			logger.debug("type:" + type);
			if (StringUtil.isNull(type)) {
				return;
			}
			if ("attendance".equals(type)) {
				// 请假 (不用单独将数据写到钉钉，更新实例状态时会自动写入数据到钉钉考勤中)
			} else if ("workOverTime".equals(type)) {
				// 加班
				dealWithOverTime(baseModel);
			} else if ("goOut".equals(type)) {
				// 外出
			    dealWithGoOut(baseModel);
			} else if ("businessTrip".equals(type)) {
				// 出差
				dealWithBusinessTrip(baseModel);
			} else if ("changeOff".equals(type)) {
				// 换班
				dealWithChageOff(baseModel);
			} else if ("replacement".equals(type)) {
				// 补卡
				dealWithReplacement(baseModel);
			} else if ("destroyLeave".equals(type)) {// 销假套件
				logger.warn("---------------销假套件：创建销假实例等-------------");
				dealWithCancelLeave(baseModel);
			} else if ("batchCancel".equals(type)) {
				// 批量销假
				dealWithBatchCancelLeave(baseModel);
			}

		} catch (Exception e) {
			// 失败，写入请假日志并标记为失败
			logger.error(e.getMessage(), e);
			if (transactionStatus != null) {
				try {
					TransactionUtils.getTransactionManager().rollback(transactionStatus);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
		}

	}

	// 更新实例
	private void updateInstance(IBaseModel baseModel) {
		try {
			String type = ThirdDingXFormTemplateUtil
					.getXFormTemplateType(baseModel);
			String modelName = ModelUtil.getModelClassName(baseModel);
			DingConfig config = DingConfig.newInstance();
			logger.debug("modelName:" + modelName);
			if (("true"
					.equals(config.getAttendanceEnabled()) || "true"
							.equals(config.getDingSuitEnabled())
							&& StringUtil.isNotNull(type))
					&& "com.landray.kmss.km.review.model.KmReviewMain"
							.equals(modelName)) {
				logger.warn("-------开启钉钉审批高级版，审批结束，开始更新实例-------");
				// 找到对应的实例
				String docSubject = (String) DingUtil.getModelPropertyString(
						baseModel,
						"docSubject", "", null);
				logger.warn("流程标题=>" + docSubject);
				String reviewMainId = baseModel.getFdId();
				logger.warn("流程主文档fdId=>" + reviewMainId);
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"fdEkpInstanceId =:fdEkpInstanceId and fdStatus=:fdStatus");
				hqlInfo.setParameter("fdEkpInstanceId", reviewMainId);
				hqlInfo.setParameter("fdStatus", "20");
				List<ThirdDingDinstanceXform> list = getThirdDingDinstanceXformService()
						.findList(hqlInfo);
				if (list != null && list.size() > 0) {
					boolean hasError = false;
					for (int i = 0; i < list.size(); i++) {
						ThirdDingDinstanceXform dinstanceXform = list.get(i);
						OapiProcessWorkrecordUpdateResponse response = DingNotifyUtil
								.updateInstanceState(
								DingUtils.dingApiService.getAccessToken(),
										dinstanceXform.getFdInstanceId(),
								Long.valueOf(list.get(i).getFdTemplate()
										.getFdAgentId()),
										dinstanceXform.getFdEkpUser().getFdId(),
										true);

						if (response != null && response.getErrcode() == 0) {
							logger.debug("更新实例成功");
							dinstanceXform.setFdStatus("30");
							// 更新实例状态
							getThirdDingDinstanceXformService()
									.update(dinstanceXform);
							logger.debug("type:" + type);
							if ("attendance".equals(type)) {
								dealWithAttendance(baseModel, true);
							}
						} else {
							logger.warn("更新实例失败！" + docSubject
									+ "   reviewMainId:" + reviewMainId);

							hasError = true;
							if ("attendance".equals(type)) {
								// 单个请假
								dealWithAttendance(baseModel, false);
							} else if ("batchLeave".equals(type)) {
								// 批量请假
								dealWithBatchLeaveInfo(baseModel, false, "请假",
										"批量请假");
							} else if ("batchReplacement".equals(type)) {
								// 批量补卡
								dealWithBatchLeaveInfo(baseModel, false, "补卡",
										"批量补卡");
							} else if ("batchChange".equals(type)) {
								dealWithBatchLeaveInfo(baseModel, false, "换班",
										"批量换班");
							} else if ("batchWorkOverTime".equals(type)) {
								dealWithBatchLeaveInfo(baseModel, false, "加班",
										"批量加班");
							}
						}
					}

					if (!hasError) {
						// 批量请假，日志记录
						if ("batchLeave".equals(type)) {
							dealWithBatchLeaveInfo(baseModel, true, "请假",
									"批量请假");
						} else if ("batchReplacement".equals(type)) {
							dealWithBatchLeaveInfo(baseModel, true, "补卡",
									"批量补卡");
						} else if ("batchChange".equals(type)) {
							dealWithBatchLeaveInfo(baseModel, true, "换班",
									"批量换班");
						} else if ("batchWorkOverTime".equals(type)) {
							dealWithBatchLeaveInfo(baseModel, true, "加班",
									"批量加班");
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

	/**
	 * 批量套件，日志记录
	 * @param baseModel
	 * @param isSuccess
	 * @param fdTagName
	 * @param fdSubType
	 */
	private void dealWithBatchLeaveInfo(IBaseModel baseModel,
			boolean isSuccess, String fdTagName, String fdSubType) {
		try {
			logger.debug("钉钉请假套件：同步表单数据到钉钉");
			// 参数组装
			Map<String, Object> dataMap = new HashMap<String, Object>();
			// 获取流程主题
			String docSubject = (String) DingUtil.getModelPropertyString(
					baseModel,
					"docSubject", "", null);
			logger.warn("流程标题=>" + docSubject);
			String reviewMainId = baseModel.getFdId();
			logger.warn("流程主文档fdId=>" + reviewMainId);
			JSONObject input = new JSONObject();

			// 获取创建人信息
			IBaseService obj = (IBaseService) SpringBeanUtil.getBean("kmReviewMainService");
			Object kmReviewMainObject = obj.findByPrimaryKey(reviewMainId);
			Class clazz = kmReviewMainObject.getClass();
			Method method = clazz.getMethod("getDocCreator");
			SysOrgPerson docCreator = (SysOrgPerson) method.invoke(kmReviewMainObject);
			String ekpUserId = docCreator.getFdId();
			// 转换成钉钉的userid
			String userid = omsRelationService.getDingUserIdByEkpUserId(ekpUserId);
			logger.debug("创建者：" + docCreator.getFdName() + "  fdId:"+ ekpUserId + "  dindId:" + userid);

			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			logger.warn("map:" + map);

			// 当事人信息
			Map userMap = null;
			if ("请假".equals(fdTagName)) {
				userMap = (Map) map.get("fd_leave_user");
			} else if ("补卡".equals(fdTagName)) {
				userMap = (Map) map.get("fd_user");
			}
			String leave_userFdId = "";
			String leave_username = "";
			String leave_userDingId = "";
			if (userMap != null) {
				leave_userFdId = (String) userMap.get("id");
				leave_username = (String) userMap.get("name");
				leave_userDingId = omsRelationService.getDingUserIdByEkpUserId(leave_userFdId);
				logger.warn(fdTagName + "人信息【" + leave_username + "(fdId:"
						+ leave_userFdId
						+ "  userid:" + leave_userDingId + ")】");
			}

			// 入参，出参
			dataMap.put("params", "钉钉【" + fdTagName + "】套件在更新实例时，" + fdTagName
					+ "数据自动写入钉钉考勤。");
			dataMap.put("result", "");
			// 请假数据回写表单
			logger.info(fdTagName + "数据回写表单...");
			JSONArray leaveInfoArray = new JSONArray();
			if ("请假".equals(fdTagName)) {
				// 写入请假明细
				dataMap.put("duration", (String) map.get("fd_sum_duration"));

				// 具体明细
				try {
					List batchLeaveInfoList = (ArrayList) map
							.get("fd_batch_leave_table");
					if (batchLeaveInfoList != null
							&& !batchLeaveInfoList.isEmpty()) {
						for (int i = 0; i < batchLeaveInfoList.size(); i++) {
							JSONObject cur_param = JSONObject
									.fromObject(batchLeaveInfoList.get(i));
							JSONObject extend_value = JSONObject.fromObject(
									cur_param.get("fd_type_extend_value"));
							logger.warn("extend_value:" + extend_value);
							if (extend_value == null
									|| extend_value.isEmpty()) {
								logger.error("【钉钉批量请假】extend_value为空！");
								continue;
							}
							JSONArray detailList = extend_value
									.getJSONArray("detailList");
							String unit = "天";
							if ("hour".equalsIgnoreCase(
									cur_param.getString("type_unit"))) {
								unit = "小时";
							}
							for (int j = 0; j < detailList.size(); j++) {
								JSONObject data = new JSONObject();
								JSONObject item = detailList.getJSONObject(j);
								String workDate = DateUtil.convertDateToString(
										new Date(item.getLong("workDate")),
										"yyyy-MM-dd");
								data.put("date", workDate);
								boolean isRest = item.getBoolean("isRest");
								if (isRest) {
									data.put("duration", "0");
								} else {
									String dur = "0";
									if ("hour".equalsIgnoreCase(
											cur_param.getString("type_unit"))) {
										dur = item.getJSONObject("approveInfo")
												.getString("durationInHour");
									} else {
										dur = item.getJSONObject("approveInfo")
												.getString("durationInDay");
									}
									data.put("duration", dur + unit);
								}
								leaveInfoArray.add(data);

							}
						}
					}
				} catch (Exception e) {
					logger.error("构建请假明细异常：" + e.getMessage(), e);
				}
			}
			// 成功，写入请假日志并标记为成功
			dataMap.put("fd_reason", fdTagName + "写入钉钉考勤操作成功");
			dataMap.put("ekpUserId", ekpUserId);
			dataMap.put("docSubject", docSubject);

			logger.warn("---------批量" + fdTagName + "写入日志----------");
			ThirdDingLeavelog thirdDingLeaveLog = new ThirdDingLeavelog();
			thirdDingLeaveLog.setDocSubject(docSubject);
			thirdDingLeaveLog.setFdUserid(StringUtil.isNotNull(leave_userDingId)?leave_userDingId:userid);
			thirdDingLeaveLog.setFdEkpUserid(StringUtil.isNotNull(leave_userFdId)?leave_userFdId:ekpUserId);
			thirdDingLeaveLog.setFdTagName(fdTagName);
			thirdDingLeaveLog.setFdSubType(fdSubType);
			thirdDingLeaveLog.setFdApproveId(reviewMainId);
			thirdDingLeaveLog.setFdJumpUrl(getDomainName()
					+ ThirdDingUtil.getDictUrl(baseModel,
							baseModel.getFdId()));
			thirdDingLeaveLog.setFdIstrue(isSuccess ? "1" : "0");

			if ("请假".equals(fdTagName)) {
				thirdDingLeaveLog
						.setFdReason((String) map.get("fd_leave_remark"));
			}
			thirdDingLeaveLog.setFdParams(
					"钉钉" + fdTagName + "套件在更新实例时，" + fdTagName + "数据自动写入钉钉考勤!");
			thirdDingLeaveLog.setDocCreateTime(new Date());
			thirdDingLeaveLog.setDocAlterTime(new Date());
			thirdDingLeaveLog.setFdSendTime(1);
			thirdDingLeaveLog.setFdIsDingSuit("1");
			thirdDingLeaveLog.setFdIsBatch("1");
			thirdDingLeavelogService.add(thirdDingLeaveLog);
			thirdDingLeavelogService.writeLeaveDetail(thirdDingLeaveLog,
					leaveInfoArray);
			logger.warn(fdSubType + "数据全部处理完毕");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 数据同步异常通知管理员
			sengDingErrorNotify(baseModel);
		}
	}

	private void dealWithGoOut(IBaseModel baseModel) {
		logger.debug("------------外出套件处理--------------");
		try {
			// 参数组装
			Map<String, Object> dataMap = new HashMap<String, Object>();
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
			String ekpUserId = docCreator.getFdId();
			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			logger.warn("---map:" + map);

			// 转换成钉钉的userid
			String userid = omsRelationService
					.getDingUserIdByEkpUserId(ekpUserId);
			logger.debug("外出人：" + docCreator.getFdName() + "  fdId:"
					+ ekpUserId + "  dindId:" + userid);

			String overtime_duration = (String) map.get("duration");
			logger.debug("overtime_duration：" + overtime_duration);
			if (StringUtil.isNotNull(overtime_duration)
					&& overtime_duration.contains("小时")) {
				overtime_duration = overtime_duration.replace("小时", "");
				logger.debug(
						"overtime_duration去掉小时单位：" + overtime_duration);
			}

			// 开始时间
			Date from_date = (Date) map.get("from_time");
			String from_time = DateUtil.convertDateToString(from_date,
					"yyyy-MM-dd HH:mm");
			logger.debug("from_date:" + from_date + "  from_time:"
					+ from_time);

			// 结束时间
			Date to_date = (Date) map.get("to_time");
			logger.debug("from_date:" + from_date);
			String to_time = DateUtil.convertDateToString(to_date,
					"yyyy-MM-dd HH:mm");
			logger.debug("to_date:" + to_date + "  to_time:" + to_time);

			dataMap.put("userid", userid);
			dataMap.put("biz_type", 2);
			dataMap.put("calculate_model", 1); // 暂时用 1 工作日
			dataMap.put("duration_unit", "hour");
			dataMap.put("from_time", from_time);
			dataMap.put("to_time", to_time);
			dataMap.put("tag_name", "外出");
			dataMap.put("approve_id", reviewMainId);
			dataMap.put("jump_url",
					getDomainName()
							+ ThirdDingUtil.getDictUrl(baseModel,
									baseModel.getFdId()));

			logger.debug("外出 input:" + dataMap.toString());
			Map<String, Object> inParam = new HashMap<>();
			inParam.putAll(dataMap);
			// 通知钉钉
			JSONObject result = thirdDingLeavelogService
					.bussNotifyDing(dataMap, ekpUserId);
			logger.warn("result=>" + JSONUtils.valueToString(result));
			// 入参，出参
			dataMap.put("params", inParam);
			dataMap.put("result", result);
			// 请假数据回写表单
			logger.info("加班数据回写表单...");
			if (result != null && result.getInt("errcode") == 0) {
				// 这里防止钉钉成功后Ekp异常阻断流程
				try {
					// 钉钉数据回写Ekp
					// if (dataMap.get("biz_type").equals("3")) {
					// thirdDingLeavelogService.updateDingInfoToEkp(baseModel,
					// params, result);
					// 写入外出明细
					String duration = result.getJSONObject("result")
							.getString("duration");
					logger.warn("写入加班明细之前获取请假时长,duration=>" + duration);
					dataMap.put("duration", duration);
					// }
					// 成功，写入外出日志并标记为成功
					dataMap.put("fd_reason", "加班写入钉钉考勤操作成功");
					dataMap.put("ekpUserId", ekpUserId);
					dataMap.put("docSubject", docSubject);
					ThirdDingLeavelog docMain = writeLeaveLog(dataMap,
							DING_SUCCESS, null);

					// 这里是记录外出明细具体逻辑
					JSONArray jsArr = result.getJSONObject("result")
							.getJSONArray("durationDetail");
					thirdDingLeavelogService.writeLeaveDetail(docMain,
							jsArr);
				} catch (Exception e) {
					logger.error("通知完钉钉后EKP发生异常", e);
				}
			} else {
				logger.warn("钉钉返回错误");
				// 失败，写入外出日志并标记为失败
				String reason = result == null ? "钉钉返回结果为空"
						: JSONUtils.valueToString(result);
				dataMap.put("fd_reason",
						"钉钉返回错误:" + reason);
				dataMap.put("ekpUserId", ekpUserId);
				dataMap.put("docSubject", docSubject);
				writeLeaveLog(dataMap, DING_ERROR, null);
				// 数据同步异常通知管理员
				sengDingErrorNotify(dataMap, docSubject, docCreator);

			}
			logger.warn("外出数据全部处理完毕");

		} catch (Exception e) {
			logger.debug("外出数据同步到钉钉过程中发生异常");
			sengDingErrorNotify(baseModel);
		}

	}

	private void dealWithBusinessTrip(IBaseModel baseModel) {
		logger.debug("------------出差套件处理--------------");
		try {
			// 参数组装
			Map<String, Object> dataMap = new HashMap<String, Object>();
			// 获取流程主题
			String docSubject = (String) DingUtil.getModelPropertyString(
					baseModel,
					"docSubject", "", null);
			logger.warn("流程标题=>" + docSubject);
			String reviewMainId = baseModel.getFdId();
			logger.warn("流程主文档fdId=>" + reviewMainId);
			JSONObject input = new JSONObject();

			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			logger.debug("map:" + map);

			Map<String, String> tripUser = new HashMap<String, String>();

			// 获取创建人信息
			IBaseService obj = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
			Object kmReviewMainObject = obj
					.findByPrimaryKey(reviewMainId);
			Class clazz = kmReviewMainObject.getClass();
			Method method = clazz.getMethod("getDocCreator");
			SysOrgPerson docCreator = (SysOrgPerson) method
					.invoke(kmReviewMainObject);
			String ekpUserId = docCreator.getFdId();
			// 转换成钉钉的userid
			String userid = omsRelationService
					.getDingUserIdByEkpUserId(ekpUserId);
			if (StringUtil.isNull(userid)) {
				logger.warn("出差人ekpUserId：" + ekpUserId + " 在对照表无对应关系，不执行同步");
			} else {
				tripUser.put(ekpUserId, userid);
			}
			logger.debug("出差人：" + docCreator.getFdName() + "  fdId:"
					+ ekpUserId + "  dindId:" + userid);

			// 同行人
			Map userMap = (Map) map.get("trip_persons");
			logger.debug("同行人map:" + userMap);
			if (userMap != null && !userMap.isEmpty()) {
				String trip_userFdId = (String) userMap.get("id");
				String[] trip_users = trip_userFdId.split(";");
				for (int j = 0; j < trip_users.length; j++) {
					String fdId = trip_users[j];
					if (StringUtil.isNull(fdId)) {
                        continue;
                    }
					String did = omsRelationService
							.getDingUserIdByEkpUserId(fdId);
					if (StringUtil.isNotNull(did)) {
						// dealWithTripByUserid(baseModel, did, fdId);
						tripUser.put(fdId, did);
					} else {
						logger.warn("出差人：" + fdId + " 在对照表无对应关系，不执行同步");
					}
				}
			}


			// 获取行程信息
			ArrayList trips = (ArrayList) map.get("fd_trips_div");
			for (int i = 0; i < trips.size(); i++) {
				JSONObject trip = JSONObject.fromObject(trips.get(i));

				logger.debug("trip:" + trip);
				// 开始时间

				Date from_date = new Date(Long.valueOf(
						trip.getJSONObject("from_time").getString("time")));
				String from_time = DateUtil.convertDateToString(from_date,
						"yyyy-MM-dd");
				logger.debug("from_date:" + from_date + "  from_time:"
						+ from_time);
				// 结束时间
				Date to_date = new Date(Long.valueOf(
						trip.getJSONObject("to_time").getString("time")));
				;
				String to_time = DateUtil.convertDateToString(to_date,
						"yyyy-MM-dd");
				logger.debug("to_date:" + to_date + "  to_time:" + to_time);

				// 开始时间的上下午
				String fromDayType = trip.getString("from_day_type");
				from_time = from_time + " " + fromDayType;

				// 结束时间的上下午
				String toDayType = trip.getString("to_day_type");
				to_time = to_time + " " + toDayType;
				logger.debug(
						"from_time :" + from_time + "  to_time:" + to_time);

				for (String ekpId : tripUser.keySet()) {
					dealWithTripByUserid(baseModel, tripUser.get(ekpId), ekpId,
							from_time, to_time);
				}

			}

			logger.warn("出差数据全部处理完毕");

		} catch (Exception e) {
			logger.debug("出差数据同步到钉钉过程中发生异常", e);
			sengDingErrorNotify(baseModel);
		}
	}

	private void dealWithChageOff(IBaseModel baseModel) {
		logger.debug("------------换班套件处理--------------");
		try {
			// 参数组装
			Map<String, Object> dataMap = new HashMap<String, Object>();
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
			String ekpUserId = docCreator.getFdId();
			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			logger.warn("map:" + map);

			// 换班人
			Map proposer = (Map) map.get("proposer");
			String proposerId = (String) proposer.get("id");
			String userid_proposer = omsRelationService
					.getDingUserIdByEkpUserId(proposerId);
			logger.debug("换班人proposerId：" + proposerId
					+ "  userid_proposer:" + userid_proposer);

			// 替班人
			Map substitute = (Map) map.get("substitute");
			String substituteId = (String) substitute.get("id");
			String target_userid = omsRelationService
					.getDingUserIdByEkpUserId(substituteId);
			logger.debug("换班人substituteId：" + substituteId
					+ "  target_userid:" + target_userid);

			// 申请换班时间
			Date shift_date = (Date) map.get("shift_date");
			String switch_date = DateUtil
					.convertDateToString(shift_date, "yyyy-MM-dd");
			logger.debug("shift_date:" + shift_date + "  switch_date:"
					+ switch_date);

			// 换班时间
			Date return_date = (Date) map.get("return_date");
			String reback_date = DateUtil
					.convertDateToString(return_date, "yyyy-MM-dd");
			logger.debug("return_date:" + return_date + "  reback_date:"
					+ reback_date);

			dataMap.put("userid", userid_proposer);
			dataMap.put("switch_date", switch_date);
			dataMap.put("reback_date", reback_date);
			dataMap.put("apply_userid", userid_proposer);
			dataMap.put("target_userid", target_userid);
			dataMap.put("approve_id", reviewMainId);
			// dataMap.put("jump_url",
			// ResourceUtil.getKmssConfigString("kmss.urlPrefix")
			// + ThirdDingUtil.getDictUrl(
			// execution.getMainModel(),
			// baseModel.getFdId()));
			String jump_url = getDomainName()
					+ ThirdDingUtil.getDictUrl(baseModel,
							baseModel.getFdId());

			logger.debug("换班 input:" + dataMap.toString());
			Map<String, Object> inParam = new HashMap<>();
			inParam.putAll(dataMap);

			// 通知钉钉
			JSONObject result = thirdDingLeavelogService
					.switchNotifyDing(dataMap, proposerId);
			// 记录日志
			ThirdDingLeavelog thirdDingLeaveLog = new ThirdDingLeavelog();
			thirdDingLeaveLog.setDocSubject(docSubject);
			thirdDingLeaveLog.setFdEkpUserid(proposerId);
			thirdDingLeaveLog.setFdUserid(userid_proposer);
			thirdDingLeaveLog.setFdTagName("换班");
			thirdDingLeaveLog.setFdParamMap(map.toString());
			Map<String, Object> inParamMap = new HashMap<>();
			inParamMap.put("url", "approve/schedule/switch");
			inParamMap.put("params", dataMap);
			thirdDingLeaveLog
					.setFdParams(JSON.toJSONString(inParamMap));
			thirdDingLeaveLog.setDocCreateTime(new Date());
			thirdDingLeaveLog.setDocAlterTime(new Date());
			thirdDingLeaveLog
					.setFdResult(JSONUtils.valueToString(result));
			thirdDingLeaveLog.setFdApproveId(reviewMainId);
			thirdDingLeaveLog.setFdJumpUrl(jump_url);

			if (result != null && result.getInt("errcode") == 0) {
				logger.warn("换班处理成功");
				thirdDingLeaveLog
						.setFdIstrue(String.valueOf(DING_SUCCESS));
				thirdDingLeaveLog.setFdReason("换班写入钉钉考勤成功");
			} else {
				logger.error("换班钉钉处理失败");
				thirdDingLeaveLog
						.setFdIstrue(String.valueOf(DING_ERROR));
				thirdDingLeaveLog.setFdReason("换班钉钉处理失败");
				// 数据同步异常通知管理员
				sengDingErrorNotify(dataMap, docSubject, docCreator);
			}

			thirdDingLeavelogService.add(thirdDingLeaveLog);
			logger.warn("换班处理完毕");

		} catch (Exception e) {
			logger.debug("换班数据同步到钉钉过程中发生异常");
			// 数据同步异常通知管理员
			sengDingErrorNotify(baseModel);
		}
	}

	private void dealWithReplacement(IBaseModel baseModel) {
		logger.debug("------------补卡套件处理--------------");
		try {
			// 参数组装
			Map<String, Object> dataMap = new HashMap<String, Object>();
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
			String ekpUserId = docCreator.getFdId();
			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			logger.warn("map:" + map);
			// 转换成钉钉的userid
			String userid = omsRelationService
					.getDingUserIdByEkpUserId(ekpUserId);
			logger.debug("补卡人：" + docCreator.getFdName() + "  fdId:"
					+ ekpUserId + "  dindId:" + userid);

			String punch_id = (String) map.get("punchId");
			logger.debug("punch_id：" + punch_id);

			// 补卡时间
			Date data_time = (Date) map.get("data_time");
			String user_check_time = DateUtil.convertDateToString(
					data_time,
					"yyyy-MM-dd HH:mm");
			logger.debug("user_check_time:" + user_check_time
					+ "  data_time:"
					+ data_time);

			// 要补哪一天的卡
			// Date workDate = (Date) map.get("work_date");
			// String work_date = DateUtil.convertDateToString(workDate,
			// "yyyy-MM-dd");
			// logger.debug("workDate:" + workDate + " work_date:"
			// + work_date);
			Date workDate = DateUtil.convertStringToDate(
					(String) map.get("work_date"),
					"yyyy-MM-dd HH:mm:ss");
			String work_date = DateUtil.convertDateToString(workDate,
					"yyyy-MM-dd");
			logger.debug("workDate:" + workDate + "  work_date:"
					+ work_date);

			// 排班时间
			Date punchCheckTime = DateUtil.convertStringToDate(
					(String) map.get("punch_check_time"),
					"yyyy-MM-dd HH:mm:ss");
			String punch_check_time = DateUtil.convertDateToString(
					workDate,
					"yyyy-MM-dd HH:mm");
			logger.debug("punchCheckTime:" + punchCheckTime
					+ "  punch_check_time:"
					+ punch_check_time);

			dataMap.put("userid", userid);
			dataMap.put("work_date", work_date);
			dataMap.put("punch_id", punch_id);
			dataMap.put("punch_check_time", punch_check_time);
			dataMap.put("user_check_time", user_check_time);
			dataMap.put("tag_name", "补卡");
			dataMap.put("approve_id", reviewMainId);
			dataMap.put("jump_url",
					getDomainName()
							+ ThirdDingUtil.getDictUrl(baseModel,
									baseModel.getFdId()));

			logger.debug("补卡 input:" + dataMap.toString());
			Map<String, Object> inParam = new HashMap<>();
			inParam.putAll(dataMap);
			// 通知钉钉
			JSONObject result = thirdDingLeavelogService
					.checkNotifyDing(dataMap, ekpUserId);
			logger.warn("result=>" + JSONUtils.valueToString(result));
			// 记录日志
			IThirdDingLeavelogService thirdDingLeavelogService = (IThirdDingLeavelogService) SpringBeanUtil
					.getBean("thirdDingLeavelogService");
			ThirdDingLeavelog thirdDingLeaveLog = new ThirdDingLeavelog();
			thirdDingLeaveLog.setFdEkpUserid(ekpUserId);
			thirdDingLeaveLog.setDocSubject(docSubject);
			thirdDingLeaveLog.setFdJumpUrl(
					String.valueOf(dataMap.get("jump_url")));
			thirdDingLeaveLog
					.setFdUserid(String.valueOf(dataMap.get("userid")));
			thirdDingLeaveLog.setFdTagName("补卡");
			thirdDingLeaveLog.setFdParamMap(map.toString());
			Map<String, Object> inParamMap = new HashMap<>();
			inParamMap.put("url", "attendance/approve/cancel");
			inParamMap.put("params",
					JSON.toJSONString(dataMap));
			thirdDingLeaveLog
					.setFdParams(JSON.toJSONString(inParamMap));
			thirdDingLeaveLog.setDocCreateTime(new Date());
			thirdDingLeaveLog.setDocAlterTime(new Date());
			thirdDingLeaveLog
					.setFdResult(JSONUtils.valueToString(result));
			thirdDingLeaveLog.setFdSendTime(1);

			if (result != null && result.getInt("errcode") == 0) {
				// 成功，写入假期管理明细并标记为成功
				logger.info("补卡");
				thirdDingLeaveLog
						.setFdIstrue(String.valueOf(DING_SUCCESS));
				thirdDingLeaveLog.setFdReason("补卡写入钉钉考勤重试成功");
			} else {
				logger.warn("钉钉返回错误");
				// 失败，写入假期管理明细并标记为失败
				thirdDingLeaveLog
						.setFdIstrue(String.valueOf(DING_ERROR));
				thirdDingLeaveLog.setFdReason("补卡钉钉返回错误");
				// 数据同步异常通知管理员
				sengDingErrorNotify(dataMap, docSubject, docCreator);
			}

			thirdDingLeavelogService.add(thirdDingLeaveLog);
			logger.debug("补卡全部处理完毕");

		} catch (Exception e) {
			logger.error("补卡数据同步到钉钉过程中发生异常", e);
			// 数据同步异常通知管理员
			sengDingErrorNotify(baseModel);
		}
	}

	// 加班，多人加班多条日志
	private void dealWithOverTime(IBaseModel baseModel) {

		try {
			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			logger.warn("map:" + map);
			Map userMap = (Map) map.get("userid");
			String overTime_userFdId = (String) userMap.get("id");
			String[] overTime_users = overTime_userFdId.split(";");
			for (int j = 0; j < overTime_users.length; j++) {
				String fdId = overTime_users[j];
				if (StringUtil.isNull(fdId)) {
                    continue;
                }
				String did = omsRelationService
						.getDingUserIdByEkpUserId(fdId);
				if (StringUtil.isNotNull(did)) {
					dealWithOverTimeByUserid(baseModel, did, fdId);
				} else {
					logger.warn("加班人：" + fdId + " 在对照表无对应关系，不执行同步");
				}
			}
		} catch (Exception e) {
			logger.error("加班数据同步报错");
			sengDingErrorNotify(baseModel);
		}

	}

	// 出差（单人、单行程）
	private void dealWithTripByUserid(IBaseModel baseModel, String userid,
			String ekpUserId, String fromTime, String toTime) {
		try {
			// 参数组装
			Map<String, Object> dataMap = new HashMap<String, Object>();
			// 获取流程主题
			String docSubject = (String) DingUtil.getModelPropertyString(
					baseModel,
					"docSubject", "", null);
			logger.warn("流程标题=>" + docSubject);
			String reviewMainId = baseModel.getFdId();
			logger.warn("流程主文档fdId=>" + reviewMainId);
			JSONObject input = new JSONObject();

			dataMap.put("userid", userid);
			dataMap.put("biz_type", 2);
			dataMap.put("calculate_model", 0); // 暂时用 0自然天
			dataMap.put("duration_unit", "halfDay");
			dataMap.put("from_time", fromTime);
			dataMap.put("to_time", toTime);
			dataMap.put("tag_name", "出差");
			dataMap.put("approve_id", reviewMainId);
			dataMap.put("jump_url",
					getDomainName()
							+ ThirdDingUtil.getDictUrl(baseModel,
									baseModel.getFdId()));
			logger.debug("出差 input:" + dataMap.toString());
			Map<String, Object> inParam = new HashMap<>();
			inParam.putAll(dataMap);
			// 通知钉钉
			JSONObject result = thirdDingLeavelogService
					.bussNotifyDing(dataMap, ekpUserId);

			logger.warn("result=>" + JSONUtils.valueToString(result));
			// 入参，出参
			dataMap.put("params", inParam);
			dataMap.put("result", result);
			// 请假数据回写表单
			logger.info("出差数据回写表单...");
			if (result != null && result.getInt("errcode") == 0) {
				// 这里防止钉钉成功后Ekp异常阻断流程
				try {
					// 钉钉数据回写Ekp
					// if (dataMap.get("biz_type").equals("3")) {
					// thirdDingLeavelogService.updateDingInfoToEkp(baseModel,
					// params, result);
					// 写入外出明细
					String duration = result.getJSONObject("result")
							.getString("duration");
					logger.warn("写入出差明细之前获取请假时长,duration=>" + duration);
					dataMap.put("duration", duration);
					// }
					// 成功，写入外出日志并标记为成功
					dataMap.put("fd_reason", "出差写入钉钉考勤操作成功");
					dataMap.put("ekpUserId", ekpUserId);
					dataMap.put("docSubject", docSubject);
					ThirdDingLeavelog docMain = writeLeaveLog(dataMap,
							DING_SUCCESS, null);

					// 这里是记录外出明细具体逻辑
					JSONArray jsArr = result.getJSONObject("result")
							.getJSONArray("durationDetail");
					thirdDingLeavelogService.writeLeaveDetail(docMain,
							jsArr);
				} catch (Exception e1) {
					logger.error("出差日志写入发生异常");
				}
			}
		} catch (Exception e) {
			logger.error("处理出差数据同步到钉钉时发生异常");
			sengDingErrorNotify(baseModel);
		}

	}

	// 加班，多人加班多条日志
	private void dealWithOverTimeByUserid(IBaseModel baseModel, String userid,
			String fdId) {
		// 加班
		logger.debug("------------加班套件处理--------------");
		try {
			// 参数组装
			Map<String, Object> dataMap = new HashMap<String, Object>();
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
			String ekpUserId = docCreator.getFdId();
			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			logger.warn("map:" + map);

			String overtime_duration = (String) map.get("duration");
			logger.debug("overtime_duration：" + overtime_duration);

			// 开始时间
			Date from_date = (Date) map.get("from_time");
			String from_time = DateUtil.convertDateToString(from_date,
					"yyyy-MM-dd HH:mm");
			logger.debug("from_date:" + from_date + "  from_time:"
					+ from_time);

			// 结束时间
			Date to_date = (Date) map.get("to_time");
			logger.debug("from_date:" + from_date);
			String to_time = DateUtil.convertDateToString(to_date,
					"yyyy-MM-dd HH:mm");
			logger.debug("to_date:" + to_date + "  to_time:" + to_time);

			dataMap.put("userid", userid);
			dataMap.put("biz_type", 1);
			dataMap.put("calculate_model", 1); // 暂时用 1 工作日
			dataMap.put("duration_unit", "hour");
			dataMap.put("from_time", from_time);
			dataMap.put("to_time", to_time);
			dataMap.put("tag_name", "加班");
			dataMap.put("approve_id", reviewMainId);
			dataMap.put("overtime_duration", overtime_duration);
			dataMap.put("overtime_to_more", 1); // 暂时传1 1：加班转调休，2：加班转工资
			dataMap.put("jump_url",
					getDomainName()
							+ ThirdDingUtil.getDictUrl(baseModel,
									baseModel.getFdId()));

			logger.debug("加班 input:" + dataMap.toString());
			Map<String, Object> inParam = new HashMap<>();
			inParam.putAll(dataMap);
			// 通知钉钉
			JSONObject result = thirdDingLeavelogService
					.overtimeNotifyDing(dataMap, fdId);
			logger.warn("result=>" + JSONUtils.valueToString(result));
			// 入参，出参
			dataMap.put("params", inParam);
			dataMap.put("result", result);
			// 请假数据回写表单
			logger.info("加班数据回写表单...");
			if (result != null && result.getInt("errcode") == 0) {
				// 这里防止钉钉成功后Ekp异常阻断流程
				try {
					// 钉钉数据回写Ekp
					// if (dataMap.get("biz_type").equals("3")) {
					// thirdDingLeavelogService.updateDingInfoToEkp(baseModel,
					// params, result);
					// 写入加班明细
					String duration = result.getJSONObject("result")
							.getString("duration");
					logger.warn("写入加班明细之前获取请假时长,duration=>" + duration);
					dataMap.put("duration", duration);
					// }
					// 成功，写入请假日志并标记为成功
					dataMap.put("fd_reason", "加班写入钉钉考勤操作成功");
					dataMap.put("ekpUserId", fdId);
					dataMap.put("docSubject", docSubject);
					ThirdDingLeavelog docMain = writeLeaveLog(dataMap,
							DING_SUCCESS, null);

					// 这里是记录请假明细具体逻辑
					JSONArray jsArr = result.getJSONObject("result")
							.getJSONArray("durationDetail");
					thirdDingLeavelogService.writeLeaveDetail(docMain,
							jsArr);
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
				dataMap.put("ekpUserId", fdId);
				dataMap.put("docSubject", docSubject);
				writeLeaveLog(dataMap, DING_ERROR, null);

				// 数据同步异常通知管理员
				sengDingErrorNotify(dataMap, docSubject, docCreator);

			}

			logger.warn("加班数据全部处理完毕");

		} catch (Exception e) {
			logger.debug("加班数据同步到钉钉过程中发生异常");
			sengDingErrorNotify(baseModel);
		}

	}

	// 多人加班合并为一个日志（暂时分开为多人加班多条日志，此方案备份考虑）
	private void dealWithOverTime2(IBaseModel baseModel) {
		// 加班
		logger.debug("------------加班套件处理--------------");
		try {
			// 参数组装
			Map<String, Object> dataMap = new HashMap<String, Object>();
			// 获取流程主题
			String docSubject = (String) DingUtil.getModelPropertyString(
					baseModel,
					"docSubject", "", null);
			logger.warn("流程标题=>" + docSubject);
			String reviewMainId = baseModel.getFdId();
			logger.warn("流程主文档fdId=>" + reviewMainId);
			JSONObject input = new JSONObject();
			// 获取申请人信息
			IBaseService obj = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
			Object kmReviewMainObject = obj
					.findByPrimaryKey(reviewMainId);
			Class clazz = kmReviewMainObject.getClass();
			Method method = clazz.getMethod("getDocCreator");
			SysOrgPerson docCreator = (SysOrgPerson) method
					.invoke(kmReviewMainObject);
			String ekpUserId = docCreator.getFdId();
			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			logger.warn("map:" + map);

			Map userMap = (Map) map.get("userid");

			String overTime_userFdId = (String) userMap.get("id");
			String[] overTime_users = overTime_userFdId.split(";");
			String userid = "";
			for (int j = 0; j < overTime_users.length; j++) {
				String fdId = overTime_users[j];
				if (StringUtil.isNull(fdId)) {
                    continue;
                }
				String did = omsRelationService
						.getDingUserIdByEkpUserId(fdId);
				if (StringUtil.isNotNull(did)) {
					userid = userid + ";" + did;
				} else {
					logger.warn("加班人：" + fdId + " 在对照表无对应关系，不执行同步");
				}
			}
			// 转换成钉钉的userid
			logger.debug("加班人：" + docCreator.getFdName() + "  fdId:"
					+ overTime_userFdId + "  dindId:" + userid);
			logger.debug("加班人：" + overTime_userFdId);

			String overtime_duration = (String) map.get("duration");
			logger.debug("overtime_duration：" + overtime_duration);

			// 开始时间
			Date from_date = (Date) map.get("from_time");
			String from_time = DateUtil.convertDateToString(from_date,
					"yyyy-MM-dd HH:mm");
			logger.debug("from_date:" + from_date + "  from_time:"
					+ from_time);

			// 结束时间
			Date to_date = (Date) map.get("to_time");
			logger.debug("from_date:" + from_date);
			String to_time = DateUtil.convertDateToString(to_date,
					"yyyy-MM-dd HH:mm");
			logger.debug("to_date:" + to_date + "  to_time:" + to_time);

			dataMap.put("userid", userid);
			dataMap.put("biz_type", 1);
			dataMap.put("calculate_model", 1); // 暂时用 1 工作日
			dataMap.put("duration_unit", "hour");
			dataMap.put("from_time", from_time);
			dataMap.put("to_time", to_time);
			dataMap.put("tag_name", "加班");
			dataMap.put("approve_id", reviewMainId);
			dataMap.put("overtime_duration", overtime_duration);
			dataMap.put("overtime_to_more", 1); // 暂时传1 1：加班转调休，2：加班转工资
			dataMap.put("jump_url",
					getDomainName()
							+ ThirdDingUtil.getDictUrl(baseModel,
									baseModel.getFdId()));

			logger.debug("加班 input:" + dataMap.toString());
			Map<String, Object> inParam = new HashMap<>();
			inParam.putAll(dataMap);
			// 通知钉钉
			JSONObject result = thirdDingLeavelogService
					.attendenceOvertimeNotifyDing(dataMap, ekpUserId);
			logger.warn("result=>" + JSONUtils.valueToString(result));
			// 入参，出参
			dataMap.put("params", inParam);
			dataMap.put("result", result);
			// 请假数据回写表单
			logger.info("加班数据回写表单...");
			if (result != null && result.getInt("errcode") == 0) {
				// 这里防止钉钉成功后Ekp异常阻断流程
				try {
					// 钉钉数据回写Ekp
					// if (dataMap.get("biz_type").equals("3")) {
					// thirdDingLeavelogService.updateDingInfoToEkp(baseModel,
					// params, result);
					// 写入加班明细
					String duration = result.getJSONArray("data")
							.getJSONObject(0).getJSONObject("result")
							.getString("duration");
					logger.warn("写入加班明细之前获取请假时长,duration=>" + duration);
					dataMap.put("duration", duration);
					// }
					// 成功，写入请假日志并标记为成功
					dataMap.put("fd_reason", "加班写入钉钉考勤操作成功");
					dataMap.put("ekpUserId", ekpUserId);
					dataMap.put("docSubject", docSubject);
					ThirdDingLeavelog docMain = writeLeaveLog(dataMap,
							DING_SUCCESS, null);

					// 这里是记录请假明细具体逻辑
					JSONArray jsArr = result.getJSONArray("data")
							.getJSONObject(0).getJSONObject("result")
							.getJSONArray("durationDetail");
					thirdDingLeavelogService.writeLeaveDetail(docMain,
							jsArr);
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
				writeLeaveLog(dataMap, DING_ERROR, null);

				// 数据同步异常通知管理员
				sengDingErrorNotify(dataMap, docSubject, docCreator);

			}

			logger.warn("加班数据全部处理完毕");

		} catch (Exception e) {
			logger.debug("加班数据同步到钉钉过程中发生异常");
			sengDingErrorNotify(baseModel);
		}

	}

	// （单个）请假---更新实例时直接同步数据到钉钉考勤了，该步骤只是记录日志
	private void dealWithAttendance(IBaseModel baseModel, boolean isSuccess) {
		try {
			logger.debug("钉钉请假套件：同步表单数据到钉钉");
			// 参数组装
			Map<String, Object> dataMap = new HashMap<String, Object>();
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
			String ekpUserId = docCreator.getFdId();

			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			logger.warn("map:" + map);

			// 转换成钉钉的userid
			String userid = omsRelationService
					.getDingUserIdByEkpUserId(ekpUserId);
			logger.debug("创建者：" + docCreator.getFdName() + "  fdId:"
					+ ekpUserId + "  dindId:" + userid);

			// 请假单位
			String unit = "day";
			if (map.containsKey("unit")) {
				unit = (String) map.get("unit");
			}

			String extend_value = (String) map.get("extend_value");
			logger.debug("extend_value:" + extend_value);
			JSONObject ext = JSONObject.fromObject(extend_value);

			// 计算方式
			int calculate_model = 1;
			if (ext.containsKey("isNaturalDay")) {
				calculate_model = ext.getBoolean("isNaturalDay") ? 0 : 1;
			}

			// 开始时间
			String from_time = null;
			Date from_date = (Date) map.get("from_time");
			logger.debug("from_date:" + from_date);
			if ("day".equals(unit)) {
				from_time = DateUtil.convertDateToString(from_date,
						"yyyy-MM-dd");
			} else if ("halfDay".equals(unit)) {
				from_time = DateUtil.convertDateToString(from_date,
						"yyyy-MM-dd") + " "
						+ (String) map.get("from_half_day");
			} else if ("hour".equals(unit)) {
				from_time = DateUtil.convertDateToString(from_date,
						"yyyy-MM-dd HH:mm");
			}

			// 开始时间
			String to_time = null;
			Date to_date = (Date) map.get("to_time");
			logger.debug("from_date:" + from_date);
			if ("day".equals(unit)) {
				to_time = DateUtil.convertDateToString(to_date,
						"yyyy-MM-dd");
			} else if ("halfDay".equals(unit)) {
				to_time = DateUtil.convertDateToString(to_date,
						"yyyy-MM-dd") + " "
						+ (String) map.get("to_half_day");
			} else if ("hour".equals(unit)) {
				to_time = DateUtil.convertDateToString(to_date,
						"yyyy-MM-dd HH:mm");
			}

			dataMap.put("userid", userid);
			dataMap.put("biz_type", 3);
			dataMap.put("calculate_model", calculate_model);
			dataMap.put("duration_unit", unit);
			dataMap.put("from_time", from_time);
			dataMap.put("to_time", to_time);
			dataMap.put("tag_name", "请假");
			dataMap.put("sub_type", (String) map.get("leave_txt"));
			dataMap.put("approve_id", reviewMainId);
			dataMap.put("jump_url",
					getDomainName()
							+ ThirdDingUtil.getDictUrl(baseModel,
									baseModel.getFdId()));

			logger.debug("钉钉请假套件高级审批 input:" + dataMap.toString());
			Map<String, Object> inParam = new HashMap<>();
			inParam.putAll(dataMap);
			// 入参，出参
			dataMap.put("params", "钉钉请假套件在更新实例时，请假数据自动写入钉钉考勤。");
			dataMap.put("result", "");
			// 请假数据回写表单
			logger.info("请假数据回写表单...");
			
			// 写入请假明细
			dataMap.put("duration", (String) map.get("duration"));
			// 成功，写入请假日志并标记为成功
			dataMap.put("fd_reason", "请假写入钉钉考勤操作成功");
			dataMap.put("ekpUserId", ekpUserId);
			dataMap.put("docSubject", docSubject);

			// 具体时长
			JSONArray detailList = ext.getJSONArray("detailList");
			JSONArray jsArr = new JSONArray();
			for(int i=0;i<detailList.size();i++){
				JSONObject data = new JSONObject();
				JSONObject item = detailList.getJSONObject(i);
				String workDate = DateUtil.convertDateToString(new Date(item.getLong("workDate")), "yyyy-MM-dd");
				data.put("date", workDate);
				boolean isRest = item.getBoolean("isRest");
				if (isRest) {
					data.put("duration", "0");
				} else {
					String dur = "0";
					if ("hour".equalsIgnoreCase(unit)) {
						dur = item.getJSONObject("approveInfo")
								.getString("durationInHour");
					} else {
						dur = item.getJSONObject("approveInfo")
								.getString("durationInDay");
					}
					data.put("duration", dur);
				}
				jsArr.add(data);

			}

			if (isSuccess) {
				ThirdDingLeavelog docMain = writeLeaveLog(dataMap,
						DING_SUCCESS, "0");
				thirdDingLeavelogService.writeLeaveDetail(docMain,
						jsArr);
			} else {
				ThirdDingLeavelog docMain = writeLeaveLog(dataMap, DING_ERROR,
						"0");
				thirdDingLeavelogService.writeLeaveDetail(docMain,
						jsArr);
				// 数据同步异常通知管理员
				sengDingErrorNotify(dataMap, docSubject, docCreator);
			}
			logger.warn("请假数据全部处理完毕");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 数据同步异常通知管理员
			sengDingErrorNotify(baseModel);
		}
	}

	/**
	 * 钉钉考勤同步异常通知(runtime异常)
	 * @param baseModel
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

			String jumpUrl = getDomainName()
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

	/*
	 * 批量销假：创建销假实例并更新
	 */
	private void dealWithBatchCancelLeave(IBaseModel baseModel) {
		logger.warn("-------批量销假实例处理-------");
		try {
			// 参数组装
			Map<String, Object> dataMap = new HashMap<String, Object>();
			// 获取流程主题
			String docSubject = (String) DingUtil.getModelPropertyString(
					baseModel,
					"docSubject", "", null);
			logger.warn("流程标题=>" + docSubject);
			String reviewMainId = baseModel.getFdId();
			logger.warn("流程主文档fdId=>" + reviewMainId);

			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			// 销假人信息
			Map userMap = (Map) map.get("fd_cancel_user");
			String cancel_userFdId = (String) userMap.get("id");
			String cancel_username = (String) userMap
					.get("name");
			String userid = omsRelationService
					.getDingUserIdByEkpUserId(cancel_userFdId);
			SysOrgPerson leaveUser = (SysOrgPerson) getSysOrgPersonService()
					.findByPrimaryKey(cancel_userFdId);

			if (StringUtil.isNull(userid)) {
				logger.error("销假人userid为空，请检查人员对照表关系：" + cancel_username);
				return;
			}

			// 获取主文档id fd_leave_form
			// String fdLeaveFormId = (String) map.get("fd_leave_form");
			// 获取请假表单信息
			JSONObject formInfo = JSONObject
					.fromObject(map.get("fd_select_form"));
			logger.warn("请假表单信息:" + formInfo);

			// 原请假的模板code
			String process_code = formInfo.getString("fd_process_code");

			String url = getReviewUrl(reviewMainId);
			// 创建销假实例的请求参数
			JSONObject request = new JSONObject();
			JSONObject param = new JSONObject();
			param.put("process_code", process_code);
			param.put("originator_user_id", userid);
			param.put("title", docSubject);
			param.put("url", url);
			param.put("biz_action", "revoke");
			param.put("remark", "撤销请假");
			param.put("form_component_values", new JSONArray());
			// 请假实例(全部销假：把所有实例都更新了)
			String allInstance = formInfo.getString("fd_instance_id");
			String[] instanceIdArray = allInstance.split(";");
			logger.warn("instanceIdArray:" + instanceIdArray);

			// 请假的logId
			String logId = formInfo.getString("logId");
			String logResult = "";
			boolean hasError = false;
			for (int i = 0; i < instanceIdArray.length; i++) {
				// 根据请假实例去销假
				param.put("main_instance_id", instanceIdArray[i]);
				request.put("request", param);
				logger.warn("request:" + request);

				JSONObject distance = createXformCancelDistance(request);
				logger.warn("创建销假实例的结果:" + distance);
				String cancel_process_instance_id = "";

				if (distance.getInt("errcode") == 0) {
					cancel_process_instance_id = distance
							.getJSONObject("result")
							.getString("process_instance_id");
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setWhereBlock(
							"fdProcessCode=:fdProcessCode");
					hqlInfo.setParameter("fdProcessCode", process_code);
					ThirdDingDtemplateXform thirdDingDtemplateXform = (ThirdDingDtemplateXform) getThirdDingDtemplateXformService()
							.findFirstOne(hqlInfo);
					// 更新实例
					OapiProcessWorkrecordUpdateResponse response = DingNotifyUtil
							.updateInstanceState(
									DingUtils.dingApiService.getAccessToken(),
									cancel_process_instance_id,
									Long.valueOf(thirdDingDtemplateXform.getFdAgentId()),
									cancel_userFdId, true);

					if (response != null && response.getErrcode() == 0) {
						logger.debug("---更新销假实例成功----");
						// 创建实例数据
						ThirdDingDinstanceXform cancel_distance = new ThirdDingDinstanceXform();
						cancel_distance.setFdName(docSubject);
						cancel_distance.setDocCreateTime(new Date());
						cancel_distance.setFdInstanceId(baseModel.getFdId());
						cancel_distance.setFdDingUserId(userid);
						cancel_distance.setFdEkpInstanceId(reviewMainId);
						cancel_distance.setFdUrl(url);
						cancel_distance.setFdTemplate(thirdDingDtemplateXform);
						cancel_distance.setFdEkpUser(leaveUser);
						cancel_distance.setFdStatus("30");
						getThirdDingDinstanceXformService()
								.add(cancel_distance);

					} else {
						hasError = true;
						logResult += response == null ? "【更新实例返回为空】"
								: "【更新销假实例失败】" + response.getBody();
						logger.warn("更新销假实例失败！" + docSubject
								+ "   reviewMainId:" + reviewMainId);
						sengDingErrorNotify(baseModel);
					}
				} else {
					hasError = true;
					logResult += distance == null ? "【创建销假实例返回为空】"
							: "【创建销假实例失败】" + distance;
					logger.warn(distance == null ? "【创建销假实例返回为空】"
							: "【创建销假实例失败】" + distance);
				}
			}
			int isSuccess = 1;
			if (hasError) {
				isSuccess = 0;
				logger.error("销假过程中发生异常：" + logResult);
				logger.warn(map + "");
			} else {
				// 更新请假日志的状态 logId
				logger.warn("logId:" + logId);
				ThirdDingLeavelog thirdDingLeavelog = (ThirdDingLeavelog) thirdDingLeavelogService
						.findByPrimaryKey(logId, ThirdDingLeavelog.class,
								true);
				thirdDingLeavelog.setFdIstrue("2");
				thirdDingLeavelog.setDocAlterTime(new Date());
				thirdDingLeavelogService.update(thirdDingLeavelog);
			}

			// 重新提请假申请实例，并更新
			String cancelType = (String) map.get("cancelType");
			logger.debug("cancelType:" + cancelType);
			dataMap.put("docSubject", docSubject);
			dataMap.put("userid", userid);
			dataMap.put("ekpUserId", cancel_userFdId);
			dataMap.put("sub_type", "请假");
			dataMap.put("tag_name", "销假");
			dataMap.put("approve_id", baseModel.getFdId());
			dataMap.put("jump_url", logId); // 撤销单跳转地址 ，原log.fdId
			dataMap.put("fd_reason", "【钉钉批量销假套件】");
			dataMap.put("paramMap", "【钉钉批量销假套件】全部销假，每个请假单只能销假一次！");
			dataMap.put("result", logResult);
			dataMap.put("params", "请假的全部实例：" + allInstance + "  请求参数(之一)："
					+ request.toString()); // 入参
			// 销假日志写入third_ding_leave_log
			writeLeaveLog(dataMap, isSuccess, "1");
		} catch (Exception e) {
			logger.error("销假实例异常：" + e.getMessage(), e);
		}

	}


	private void dealWithCancelLeave(IBaseModel baseModel) {
		logger.debug("------------销假套件处理--------------");
		try {
			// 参数组装
			Map<String, Object> dataMap = new HashMap<String, Object>();
			// 获取流程主题
			String docSubject = (String) DingUtil.getModelPropertyString(
					baseModel,
					"docSubject", "", null);
			logger.warn("流程标题=>" + docSubject);
			String reviewMainId = baseModel.getFdId();
			logger.warn("流程主文档fdId=>" + reviewMainId);
			// 获取主文档创建者信息
			IBaseService obj = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
			Object kmReviewMainObject = obj
					.findByPrimaryKey(baseModel.getFdId());
			Class clazz = kmReviewMainObject.getClass();
			Method method = clazz.getMethod("getDocCreator");
			SysOrgPerson docCreator = (SysOrgPerson) method
					.invoke(kmReviewMainObject);
			
			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			logger.warn("map:" + map);
			String url = getReviewUrl(reviewMainId);
			String process_code = "";
			String main_instance_id = "";
			String ekpInstanceId = (String) map.get("level_form");
			String cancelLeave = (String) map.get("cancelLeave");
			String leaveText = "";
			String logId = "";
			String unit = "day";
			String leave_code = "";
			if (StringUtil.isNull(cancelLeave)) {
				logger.warn("---------cancelLeave为空！！！------");
				return;
			}
			com.alibaba.fastjson.JSONArray cancelInfo = com.alibaba.fastjson.JSONArray
					.parseArray(cancelLeave);
			com.alibaba.fastjson.JSONObject cur_info = new com.alibaba.fastjson.JSONObject();
			for (int i = 0; i < cancelInfo.size(); i++) {
				String id = cancelInfo.getJSONObject(i)
						.getString("fd_ekp_instance_id");
				logger.debug(
						"原请假单:" + ekpInstanceId + "  fd_ekp_instance_id:" + id);
				if (ekpInstanceId.equals(id)) {
					cur_info = cancelInfo.getJSONObject(i);
					leaveText = cancelInfo.getJSONObject(i)
							.getString("fd_name");
					main_instance_id = cancelInfo.getJSONObject(i)
							.getString("fd_instance_id");
					process_code = cancelInfo.getJSONObject(i)
							.getString("fd_process_code");
					logId = cancelInfo.getJSONObject(i)
							.getString("logId");
					unit = cancelInfo.getJSONObject(i)
							.getString("unit");
					leave_code = cancelInfo.getJSONObject(i)
							.getString("leave_code");
					break;
				}

			}

			String dingUserid = omsRelationService
					.getDingUserIdByEkpUserId(docCreator.getFdId());

			// 创建销假实例
			JSONObject request = new JSONObject();
			JSONObject param = new JSONObject();
			param.put("process_code", process_code);
			param.put("originator_user_id", dingUserid);
			param.put("title", docSubject);
			param.put("url", url);
			param.put("biz_action", "revoke");
			param.put("main_instance_id", main_instance_id);
			param.put("remark", "撤销请假");
			param.put("form_component_values", new JSONArray());
			request.put("request", param);
			logger.warn("request:" + request);
			JSONObject distance = createXformCancelDistance(request);

			logger.warn("distance:" + distance);
			String cancel_process_instance_id = "";
			if (distance.getInt("errcode") == 0) {
				cancel_process_instance_id = distance.getJSONObject("result")
						.getString("process_instance_id");
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"fdProcessCode=:fdProcessCode");
				hqlInfo.setParameter("fdProcessCode", process_code);
				ThirdDingDtemplateXform thirdDingDtemplateXform = (ThirdDingDtemplateXform) getThirdDingDtemplateXformService()
						.findFirstOne(hqlInfo);
				// 更新实例
				OapiProcessWorkrecordUpdateResponse response = DingNotifyUtil
						.updateInstanceState(
								DingUtils.dingApiService.getAccessToken(),
								cancel_process_instance_id,
								Long.valueOf(thirdDingDtemplateXform.getFdAgentId()),
								null, true);

				if (response != null && response.getErrcode() == 0) {
					logger.debug("---更新销假实例成功----");
					// 创建实例数据
					ThirdDingDinstanceXform cancel_distance = new ThirdDingDinstanceXform();
					cancel_distance.setFdName(docSubject);
					cancel_distance.setDocCreateTime(new Date());
					cancel_distance.setFdInstanceId(baseModel.getFdId());
					cancel_distance.setFdDingUserId(dingUserid);
					cancel_distance.setFdEkpInstanceId(reviewMainId);
					cancel_distance.setFdUrl(url);
					cancel_distance.setFdTemplate(thirdDingDtemplateXform);
					cancel_distance.setFdEkpUser(UserUtil.getUser());
					cancel_distance.setFdStatus("30");
					getThirdDingDinstanceXformService().add(cancel_distance);

					// 更新请假日志的状态 logId
					logger.warn("logId:" + logId);
					ThirdDingLeavelog thirdDingLeavelog = (ThirdDingLeavelog) thirdDingLeavelogService
							.findByPrimaryKey(logId, ThirdDingLeavelog.class,
									true);
					thirdDingLeavelog.setFdIstrue("2");
					thirdDingLeavelog.setDocAlterTime(new Date());
					thirdDingLeavelogService.update(thirdDingLeavelog);
					// 重新提请假申请实例，并更新
					String cancelType = (String) map.get("cancelType");
					logger.debug("cancelType:" + cancelType);
					dataMap.put("docSubject", docSubject);
					dataMap.put("userid", dingUserid);
					dataMap.put("ekpUserId", docCreator.getFdId());				
					dataMap.put("sub_type", "请假");
					dataMap.put("tag_name", "销假");
					dataMap.put("approve_id", baseModel.getFdId());
					dataMap.put("jump_url", logId); // 撤销单跳转地址 ，原log.fdId
					dataMap.put("fd_reason", "钉钉套件销假");
					dataMap.put("paramMap", "全部销假，每个请假单只能销假一次！");
					dataMap.put("result", response.getBody().toString());
					dataMap.put("params", request.toString()); // 入参

					if ("time".equals(cancelType)) {
						logger.debug("---按照时间销假-----");
						createCancelLeaveInstanceByTime(baseModel, map,
								cur_info, thirdDingDtemplateXform,
								dataMap);
					} else if ("detail".equals(cancelType)) {
						logger.debug("按照明细销假");
						createCancelLeaveInstance(baseModel, map,
								cur_info, thirdDingDtemplateXform,
								dataMap);
					}else{
						// 销假日志写入third_ding_leave_log
						writeLeaveLog(dataMap, 1, "0");
					}
					
					
				} else {
					logger.warn("更新销假实例失败！" + docSubject
							+ "   reviewMainId:" + reviewMainId);
					sengDingErrorNotify(baseModel);
				}

			} else {
				logger.error("创建实例失败:" + distance);
				sengDingErrorNotify(baseModel);
				return;
			}


		} catch (Exception e) {
			logger.error("创建销假实例发生异常:" + e.getMessage(), e);
			// 数据同步异常通知管理员
			sengDingErrorNotify(baseModel);
		}
	}


	private void createCancelLeaveInstanceByTime(IBaseModel baseModel, Map map,
			com.alibaba.fastjson.JSONObject cur_info,
			ThirdDingDtemplateXform temp, Map<String, Object> dataMap)
			throws Exception {
		logger.warn("------根据时间重新提请假流程实例，销部分假期-------");
		logger.warn("map:" + map);
		// 获取流程主题
		String docSubject = (String) DingUtil.getModelPropertyString(baseModel,
				"docSubject", "", null);
		logger.warn("流程标题=>" + docSubject);

		//原请假开始时间信息
		String ori_from_time=null;
		Long ori_startTime=0L;
		//原请假结束时间信息
		String ori_to_time=null;
		Long ori_endTime=0L;

		// JSONObject cardInfo = (JSONObject) map.get("cardInfo");
		Date from_date = (Date) map.get("cancelStartTime");
		String unit = cur_info.getString("unit");
		String from_time = "";
		Long _startTime = 0L; // 开始时间的时间戳
		Date ori_startDate = DateUtil.convertStringToDate(
				cur_info.getString("from_time_str"), "yyyy-MM-dd HH:mm:ss");

		String newToTime = null;
		if ("day".equalsIgnoreCase(unit)) {
			from_time = DateUtil.convertDateToString(from_date,
					"yyyy-MM-dd");
			ori_from_time = DateUtil.convertDateToString(ori_startDate,
					"yyyy-MM-dd");
			ori_startTime = ori_startDate.getTime();
			_startTime = DateUtil.convertStringToDate(from_time, "yyyy-MM-dd")
					.getTime();
			// 需要减去一天
			Date dd = new Date(from_date.getTime() - 1 * 24 * 60 * 60 * 1000L);
			newToTime = DateUtil.convertDateToString(dd, "yyyy-MM-dd");

		} else if ("halfDay".equalsIgnoreCase(unit)) {
			String fromHalfDay = (String) map
					.get("cancelFromDay");
			// 需要销假的开始时间上下午
			if ("AM".equalsIgnoreCase(fromHalfDay)) {
				fromHalfDay = "上午";
				_startTime = DateUtil.convertStringToDate(
						DateUtil.convertDateToString(from_date,
								"yyyy-MM-dd") + " 00:00",
						"yyyy-MM-dd HH:mm").getTime();

				// 需要减去一天
				Date dd = new Date(
						from_date.getTime() - 1 * 24 * 60 * 60 * 100L);
				newToTime = DateUtil.convertDateToString(dd, "yyyy-MM-dd")
						+ " 下午";

			} else if ("PM".equalsIgnoreCase(fromHalfDay)) {
				fromHalfDay = "下午";
				_startTime = DateUtil.convertStringToDate(
						DateUtil.convertDateToString(from_date,
								"yyyy-MM-dd") + " 12:00",
						"yyyy-MM-dd HH:mm").getTime();
				newToTime = DateUtil.convertDateToString(from_date,
						"yyyy-MM-dd") + " 上午";
			}
			from_time = DateUtil.convertDateToString(from_date,
					"yyyy-MM-dd") + " "
					+ fromHalfDay;

			String ori_fromHalfDay = cur_info.getString("from_half_day_str");
			// 原始开始时间上下午
			if ("AM".equalsIgnoreCase(ori_fromHalfDay)) {
				ori_fromHalfDay = "上午";
				ori_from_time = DateUtil.convertDateToString(ori_startDate,
						"yyyy-MM-dd") + " 00:00";
				ori_startTime = DateUtil
						.convertStringToDate(ori_from_time, "yyyy-MM-dd HH:mm")
						.getTime();

			} else if ("PM".equalsIgnoreCase(ori_fromHalfDay)) {
				ori_fromHalfDay = "下午";
				ori_from_time = DateUtil.convertDateToString(ori_startDate,
						"yyyy-MM-dd") + " 12:00";
				ori_startTime = DateUtil
						.convertStringToDate(ori_from_time, "yyyy-MM-dd HH:mm")
						.getTime();
			}

			ori_from_time = DateUtil.convertDateToString(ori_startDate,
					"yyyy-MM-dd") + " "
					+ ori_fromHalfDay;


		} else if ("hour".equalsIgnoreCase(unit)) {
			// 单位为小时，from_date丢失了时分信息，暂时取特殊字段
			// from_time = DateUtil.convertDateToString(from_date,
			// "yyyy-MM-dd HH:mm");
			from_time = (String) map.get("cancelLevelStartTime");
			logger.debug("小时 from_time:" + from_time);
			_startTime = DateUtil
					.convertStringToDate(from_time, "yyyy-MM-dd HH:mm")
					.getTime();
			ori_from_time = DateUtil.convertDateToString(ori_startDate,
					"yyyy-MM-dd HH:mm");
			ori_startTime = DateUtil
					.convertStringToDate(ori_from_time, "yyyy-MM-dd HH:mm")
					.getTime();

			newToTime = from_time;
		}
		logger.warn("_startTime:" + _startTime);

		// 结束时间
		Date ori_endDate = DateUtil.convertStringToDate(
				cur_info.getString("to_time_str"), "yyyy-MM-dd HH:mm:ss");
		String to_time = null;
		Long _endTime = 0L; // 销假结束时间的时间戳
		Date to_date = (Date) map.get("cancelEndTime");
		String newFromTime = null;
		if ("day".equalsIgnoreCase(unit)) {
			to_time = DateUtil.convertDateToString(to_date,
					"yyyy-MM-dd");
			_endTime = DateUtil.convertStringToDate(to_time, "yyyy-MM-dd")
					.getTime();

			ori_endTime = ori_endDate.getTime();

			// 需要增加一天
			Date dd = new Date(to_date.getTime() + 1 * 24 * 60 * 60 * 100L);
			newFromTime = DateUtil.convertDateToString(dd, "yyyy-MM-dd");
		} else if ("halfDay".equalsIgnoreCase(unit)) {
			String toHalfDay = (String) map.get("canceToDay");
			if ("AM".equalsIgnoreCase(toHalfDay)) {
				toHalfDay = "上午";
				_endTime = DateUtil.convertStringToDate(
						DateUtil.convertDateToString(to_date,
								"yyyy-MM-dd") + " 11:59",
						"yyyy-MM-dd HH:mm")
						.getTime();
				newFromTime = DateUtil.convertDateToString(to_date,
						"yyyy-MM-dd") + " 下午";
			} else if ("PM".equalsIgnoreCase(toHalfDay)) {
				toHalfDay = "下午";
				_endTime = DateUtil.convertStringToDate(
						DateUtil.convertDateToString(to_date,
								"yyyy-MM-dd") + " 23:59",
						"yyyy-MM-dd HH:mm")
						.getTime();
				// 需要增加一天
				Date dd = new Date(
						to_date.getTime() + 1 * 24 * 60 * 60 * 1000L);
				newFromTime = DateUtil.convertDateToString(dd, "yyyy-MM-dd")
						+ " 上午";
			}
			to_time = DateUtil.convertDateToString(to_date,
					"yyyy-MM-dd") + " " + toHalfDay;

			// 原始结束时间上下午
			String ori_toHalfDay = cur_info.getString("to_half_day_str");
			if ("AM".equalsIgnoreCase(ori_toHalfDay)) {
				ori_toHalfDay = "上午";
				ori_to_time = DateUtil.convertDateToString(ori_endDate,
						"yyyy-MM-dd") + " 11:59";
				ori_endTime = DateUtil
						.convertStringToDate(ori_to_time, "yyyy-MM-dd HH:mm")
						.getTime();

			} else if ("PM".equalsIgnoreCase(ori_toHalfDay)) {
				ori_toHalfDay = "下午";
				ori_to_time = DateUtil.convertDateToString(ori_endDate,
						"yyyy-MM-dd") + " 23:59";
				ori_endTime = DateUtil
						.convertStringToDate(ori_to_time, "yyyy-MM-dd HH:mm")
						.getTime();
			}
			ori_to_time = DateUtil.convertDateToString(ori_endDate,
					"yyyy-MM-dd") + " "
					+ ori_toHalfDay;
		} else if ("hour".equalsIgnoreCase(unit)) {
			// to_time = DateUtil.convertDateToString(to_date,
			// "yyyy-MM-dd HH:mm");
			to_time = (String) map.get("cancelLevelEndTime");
			logger.debug("结束时间，单位为小时：" + to_time);
			_endTime = DateUtil.convertStringToDate(to_time, "yyyy-MM-dd HH:mm")
					.getTime();

			ori_to_time = DateUtil.convertDateToString(ori_endDate,
					"yyyy-MM-dd HH:mm");
			ori_endTime = DateUtil
					.convertStringToDate(ori_to_time, "yyyy-MM-dd HH:mm")
					.getTime();
			newFromTime = to_time;
		}
		logger.warn("ori_startTime:" + ori_startTime);
		logger.warn("ori_from_time:" + ori_from_time);
		logger.warn("ori_endTime:" + ori_endTime);
		logger.warn("ori_to_time:" + ori_to_time);

		logger.warn("_startTime:" + _startTime);
		logger.warn("_from_time:" + from_time);
		logger.warn("_endTime:" + _endTime);
		logger.warn("_to_time:" + to_time);

		logger.warn("newToTime:" + newToTime);
		logger.warn("newFromTime:" + newFromTime);

		Map<String, String> leaveRecord = new HashMap<String, String>();
		if (_startTime > ori_startTime) {
			// 销假时间起点大于原请假起点
			logger.warn("销假时间起点大于原请假起点");
			leaveRecord.put(ori_from_time, newToTime);
		} else {
			logger.warn("销假时间起点==原请假起点");
		}

		if (_endTime < ori_endTime) {
			// 销假时间起点大于原请假起点
			logger.warn("销假结束点小于原请假结束点");
			leaveRecord.put(newFromTime, ori_to_time);
		} else {
			logger.warn("销假结束点==原请假结束点");
		}
		logger.warn("_endTime:" + _endTime);

		if (leaveRecord.size() == 0) {
			logger.warn("---全部销假---" + docSubject);
			return;
		}
		leaveMore(leaveRecord, cur_info, dataMap, baseModel, temp, map);

	}

	private void leaveMore(Map<String, String> leaveRecord,
			com.alibaba.fastjson.JSONObject cur_info,
			Map<String, Object> dataMap, IBaseModel baseModel,
			ThirdDingDtemplateXform temp, Map map) throws Exception {

		for (String from_time : leaveRecord.keySet()) {
			String to_time = leaveRecord.get(from_time);
			logger.warn(
					"准备请假：from_time->" + from_time + "  to_time ->" + to_time);
			leaveSingle(from_time, to_time, cur_info, dataMap, baseModel, temp,
					map);
		}
	}

	private void leaveSingle(String from_time, String to_time,
			com.alibaba.fastjson.JSONObject cur_info,
			Map<String, Object> dataMap, IBaseModel baseModel,
			ThirdDingDtemplateXform temp, Map map) throws Exception {

		// 获取主文档创建者信息
		IBaseService obj = (IBaseService) SpringBeanUtil
				.getBean("kmReviewMainService");
		Object kmReviewMainObject = obj
				.findByPrimaryKey(baseModel.getFdId());
		Class clazz = kmReviewMainObject.getClass();
		Method method = clazz.getMethod("getDocCreator");
		SysOrgPerson docCreator = (SysOrgPerson) method
				.invoke(kmReviewMainObject);
		String ekpUserId = docCreator.getFdId();

		// 获取流程主题
		String docSubject = (String) DingUtil.getModelPropertyString(baseModel,
				"docSubject", "", null);
		logger.warn("流程标题=>" + docSubject);

		JSONObject typeJson = new JSONObject();
		typeJson.put("leaveCode", cur_info.getString("leave_code"));
		String unit = cur_info.getString("unit");
		typeJson.put("unit", unit);
		String leave_name = cur_info.getString("leave_txt");
		JSONObject param = new JSONObject();
		param.put("请假类型", leave_name);
		param.put("type_extend_value", typeJson.toString());
		param.put("开始时间", from_time);
		param.put("结束时间", to_time);
		param.put("请假原因", (String) map.get("remark"));
		String rs = BizsuiteUtil
				.preCalculate(from_time, to_time,
						cur_info.getString("leave_code"), ekpUserId)
				.toString();
		logger.debug("rs:" + rs);
		JSONObject result = JSONObject.fromObject(rs);
		String extend_value = result.getJSONObject("result")
				.getJSONArray("form_data_list")
				.getJSONObject(0).getString("extend_value");
		param.put("extend_value", extend_value);
		param.put("type", "attendance");
		logger.debug("-----获取预计算的时长：");
		String duration = "0";
		JSONObject extend_value_json = JSONObject.fromObject(extend_value);
		if ("hour".equalsIgnoreCase(unit)) {
			duration = extend_value_json.getString("durationInDay");
		} else {
			duration = extend_value_json.getString("durationInHour");
		}
		param.put("时长", duration);
		param.put("url", getReviewUrl(baseModel.getFdId()));
		logger.debug("param:" + param);
		OapiProcessWorkrecordCreateResponse response = DingNotifyUtil
				.createXformDistance(DingUtils.getDingApiService()
						.getAccessToken(),
						cur_info.getString("fd_ding_user_id"), temp, docSubject,
						temp.getFdDetail(), param);
		// if (dataMap.containsKey("params")) {
		// String old = (String) dataMap.get("params");
		// dataMap.put("params", old + response.getMsg());
		// }
		dataMap.put("from_time", from_time);
		dataMap.put("to_time", to_time);
		dataMap.put("paramMap", "按时间部分销假");

		if (response!=null&&response.getErrcode() == 0) {
			String instanceId = response.getResult()
					.getProcessInstanceId();

			OapiProcessWorkrecordUpdateResponse update_response = DingNotifyUtil
					.updateInstanceState(
							DingUtils.dingApiService.getAccessToken(),
							instanceId,
							Long.valueOf(temp.getFdAgentId()), ekpUserId,
							true);
			if (update_response != null && update_response.getErrcode() == 0) {

				logger.warn("------更新实例成功-----");
				ThirdDingDinstanceXform distance = new ThirdDingDinstanceXform();
				distance.setFdName(docSubject);
				distance.setDocCreateTime(new Date());
				distance.setFdInstanceId(instanceId);
				distance.setFdDingUserId(cur_info.getString("fd_ding_user_id"));
				distance.setFdEkpInstanceId(baseModel.getFdId());
				distance.setFdUrl(getReviewUrl(baseModel.getFdId()));
				distance.setFdTemplate(temp);
				distance.setFdEkpUser(docCreator);
				distance.setFdStatus("30");
				getThirdDingDinstanceXformService().add(distance);

				dataMap.put("result", update_response.getBody());
				writeLeaveLog(dataMap, 1, "0");
			} else {
				logger.error("-------更新请假实例失败-------");
				logger.error(update_response == null ? "返回为null"
						: update_response.getBody());
				writeLeaveLog(dataMap, 0, "0");
			}

		}
	}

	private void createCancelLeaveInstance(IBaseModel baseModel, Map map,
			com.alibaba.fastjson.JSONObject cur_info,
			ThirdDingDtemplateXform temp, Map<String, Object> dataMap)
			throws Exception {
		logger.warn("------根据明细重新提请假流程实例，销部分假期-------");
		logger.warn("map:" + map);
		// 获取流程主题
		String docSubject = (String) DingUtil.getModelPropertyString(baseModel,
				"docSubject", "", null);
		logger.warn("流程标题=>" + docSubject);
		// 获取detailDate
		if (!map.containsKey("detailDate")) {
			return;
		}

		JSONArray detailDate_ja = JSONArray.fromObject(map.get("detailDate"));
		if (detailDate_ja == null || detailDate_ja.isEmpty()) {
            return;
        }
        
		//整理一下，按天整理
		Map<String,String> dateDetailMap = new HashMap<String,String>();
		for(int i=0;i<detailDate_ja.size();i++){
			JSONObject detail = detailDate_ja.getJSONObject(i);
			String date = detail.getString("date");
			if(dateDetailMap.containsKey(date)){
				dateDetailMap.put(date, dateDetailMap.get(date)+";"+detail.getString("time"));
			}else{
				dateDetailMap.put(date, detail.getString("time"));
			}
		}
		
		String unit = cur_info.getString("unit");
		Map<String, String> leaveRecord = new HashMap<String, String>();  //开始时间，结束时间
		logger.debug("unit:"+unit);
		if("hour".equals(unit)){
			for(String date:dateDetailMap.keySet()){
				String [] date_detailInfo = dateDetailMap.get(date).split(";");//11:02~12:00;14:00~15:00
				String start = null;
				String end=null;
				for(int j=0;j<date_detailInfo.length;j++){
					String [] info = dateDetailMap.get(date).split("~"); //11:02~12:00
					if(j==0){
						start=info[0];
						end = info[1];
					}else{
						if(end.equals(info[0])){
							end = info[1];
						}else{
							//不是连续的，记录一个请假时间段
							leaveRecord.put(date+" "+start, date+" "+end);
							start=info[0];
							end = info[1];
						}
					}
					// 最后一个
					if (j == date_detailInfo.length - 1) {
						leaveRecord.put(date + " " + start, date + " " + end);
					}
				}
			}
			
		} else if ("day".equals(unit)) {
			logger.warn("--------------单位为天的销假-------------------");
			String start = null;
			String end = null;
			Long _end = 0L;
			for (int j = 0; j < detailDate_ja.size(); j++) {
				String date = detailDate_ja.getJSONObject(j).getString("date");
				Long _date = DateUtil.convertStringToDate(date, "yyyy-MM-dd")
						.getTime();
				logger.warn("date:" + date);
				if (j == 0) {
					start = date;
					end = date;
					_end = _date;
				} else {
					if (_date - _end == 86400000L) {
						logger.warn("----连续------");
						_end = _date;
						end = date;
					} else {
						logger.warn("----不连续------");
						leaveRecord.put(start, end);
						start = date;
						end = date;
						_end = _date;
					}
				}
				// 最后一个
				if (j == detailDate_ja.size() - 1) {
					leaveRecord.put(start, end);
				}
			}

		} else if ("halfDay".equals(unit)) {
			logger.warn("--------------单位为半天的销假-------------------");
			String start = null; // 开始上下午
			String end = null; // 结束上下午
			String cur_start = null; // 当前日期开始上下午
			String cur_end = null;// 当前日期结束上下午
			Long _end = 0L; // 用来判断时间的连续性

			String startTime = null; // 开始时间+上下午
			String endTime = null; // 结束时间+上下午
			for (int j = 0; j < detailDate_ja.size(); j++) {
				String date = detailDate_ja.getJSONObject(j).getString("date");
				Long _date = DateUtil.convertStringToDate(date, "yyyy-MM-dd")
						.getTime();
				logger.warn("date:" + date);
				String halfDate = dateDetailMap.get(date);
				logger.warn("halfDate:" + halfDate);
				if (j == 0) {
					if (StringUtil.isNotNull(halfDate)
							&& halfDate.contains(";")) {
						start = dateDetailMap.get(date).split(";")[0];
						end = dateDetailMap.get(date).split(";")[1];
					} else {
						start = halfDate;
						end = halfDate;
					}
					startTime = date + " " + start;
					endTime = date + " " + end;
				} else {
					if (StringUtil.isNotNull(halfDate)
							&& halfDate.contains(";")) {
						cur_start = dateDetailMap.get(date).split(";")[0];
						cur_end = dateDetailMap.get(date).split(";")[1];
					} else {
						cur_start = halfDate;
						cur_end = halfDate;
					}
					if (_date - _end == 86400000L) {
						logger.warn("----日期连续------");
						if ("PM".equalsIgnoreCase(end)
								&& "AM".equalsIgnoreCase(cur_start)) {
							logger.warn("----时间连续------");
							end = cur_end;
							endTime = date + " " + cur_end;
						} else {
							logger.warn("----时间不连续------");
							leaveRecord.put(startTime, endTime);
							startTime = date + " " + cur_start;
							endTime = date + " " + cur_end;
							start = cur_start;
							end = cur_end;
						}
					} else {
						logger.warn("----日期不连续------");
						leaveRecord.put(startTime, endTime);
						startTime = date + " " + cur_start;
						endTime = date + " " + cur_end;
						start = cur_start;
						end = cur_end;
					}
				}
				_end = _date;
				// 最后一个
				if (j == detailDate_ja.size() - 1) {
					leaveRecord.put(startTime, endTime);
				}
			}

		}else{
			logger.warn("--------------单位异常-------------------" + unit);
			return;
		}
		logger.warn("leaveRecord:" + leaveRecord);
		leaveMore(leaveRecord, cur_info, dataMap, baseModel, temp, map);

	}

	private String getReviewUrl(String reviewMainId) {
		String url = "/km/review/km_review_main/kmReviewMain.do?method=view&fdId="
				+ reviewMainId
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		String dingDomain = DingConfig.newInstance()
				.getDingDomain();
		if (StringUtil.isNull(dingDomain)) {
			dingDomain = ResourceUtil
					.getKmssConfigString("kmss.urlPrefix");
		}
		if (dingDomain.trim().endsWith("/")) {
			dingDomain = dingDomain.trim().substring(0,
					dingDomain.length() - 1);
		}
		url = dingDomain + url.trim();
		return url;
	}

	// 创建销假实例
	public JSONObject createXformCancelDistance(JSONObject param)
			throws Exception {
		logger.debug(
				"---------------------高级审批：创建实例-------------------------：");
		String dingUrl = DingConstant.DING_PREFIX
				+ "/topapi/process/workrecord/create?access_token="
				+ DingUtils.getDingApiService()
						.getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		logger.debug("钉钉创建实例接口：" + dingUrl);
		return DingHttpClientUtil.httpPost(dingUrl, param, null,
				JSONObject.class);
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

	/**
	 * 写入请假日志
	 * 
	 * @throws Exception
	 */
	private ThirdDingLeavelog writeLeaveLog(Map<String, Object> dataMap,
			Integer flag, String fdIsBatch) throws Exception {
		logger.warn("写入请假日志...");
		ThirdDingLeavelog thirdDingLeaveLog = new ThirdDingLeavelog();
		try {

			thirdDingLeaveLog
					.setDocSubject(String.valueOf(dataMap.get("docSubject")));
			thirdDingLeaveLog
					.setFdUserid(String.valueOf(dataMap.get("userid")));
			thirdDingLeaveLog
					.setFdEkpUserid(String.valueOf(dataMap.get("ekpUserId")));
			if (dataMap.containsKey("biz_type")) {
				thirdDingLeaveLog.setFdBizType(Integer
						.parseInt(String.valueOf(dataMap.get("biz_type"))));
			}
			thirdDingLeaveLog
					.setFdTagName(String.valueOf(dataMap.get("tag_name")));
			thirdDingLeaveLog
					.setFdSubType(String.valueOf(dataMap.get("sub_type")));
			thirdDingLeaveLog.setFdDurationUnit(
					String.valueOf(dataMap.get("duration_unit")));
			thirdDingLeaveLog
					.setFdDuration(String.valueOf(dataMap.get("duration")));
			thirdDingLeaveLog
					.setFdFromTime(String.valueOf(dataMap.get("from_time")));
			thirdDingLeaveLog
					.setFdToTime(String.valueOf(dataMap.get("to_time")));
			thirdDingLeaveLog
					.setFdApproveId(String.valueOf(dataMap.get("approve_id")));
			thirdDingLeaveLog
					.setFdJumpUrl(String.valueOf(dataMap.get("jump_url")));
			thirdDingLeaveLog.setFdIstrue(String.valueOf(flag));
			thirdDingLeaveLog
					.setFdReason(String.valueOf(dataMap.get("fd_reason")));
			thirdDingLeaveLog
					.setFdParamMap(JSON.toJSONString(dataMap.get("paramMap")));
			thirdDingLeaveLog.setFdParams((String) dataMap.get("params"));
			thirdDingLeaveLog.setDocCreateTime(new Date());
			thirdDingLeaveLog.setDocAlterTime(new Date());
			thirdDingLeaveLog
					.setFdResult(String.valueOf(dataMap.get("result")));
			thirdDingLeaveLog.setFdSendTime(1);
			thirdDingLeaveLog.setFdIsDingSuit("1");
			if (StringUtil.isNotNull(fdIsBatch)) {
				thirdDingLeaveLog.setFdIsBatch(fdIsBatch);
			}
			thirdDingLeavelogService.add(thirdDingLeaveLog);
		} catch (Exception e) {
			logger.error("写入日志发生异常：", e);
		}

		return thirdDingLeaveLog;
	}

	public String getDomainName() {
		String domainName = DingConfig.newInstance().getDingDomain();
		if (StringUtil.isNull(domainName)) {
            domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
        }
		if (domainName.endsWith("/")) {
            domainName = domainName.substring(0, domainName.length() - 1);
        }
		return domainName;
	}
}
