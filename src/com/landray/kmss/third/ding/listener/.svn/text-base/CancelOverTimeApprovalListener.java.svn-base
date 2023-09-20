package com.landray.kmss.third.ding.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.model.ThirdDingLeavelog;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingLeavelogService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.oracle.StringUtil;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

public class CancelOverTimeApprovalListener implements IEventListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CancelOverTimeApprovalListener.class);

	/**
	 * ekp异常，不重试
	 */
	static final int EKP_ERRROR = -1;
	/**
	 * 钉钉同步成功
	 */
	static final int DING_SUCCESS = 1;
	/**
	 * 重新提表单
	 */
	static final int DING_RESUBMIT = 1;

	/**
	 * 仅销假，不重新提表单申请
	 */
	static final int DING_NOTSUBMIT = 0;
	/**
	 * 钉钉返回失败
	 */
	static final int DING_ERROR = 0;

	/**
	 * 钉钉销假类型 请假 外出 出差 加班
	 */
	static final String DING_LEAVE = "请假";
	static final String DING_BUSINESS = "外出";
	static final String DING_TRIP = "出差";
	static final String DING_OVERTIME = "加班";

	private DingApiService dingApiService = DingUtils.getDingApiService();
	private IOmsRelationService omsRelationService;
	private IThirdDingLeavelogService thirdDingLeavelogService;

	public void setThirdDingLeavelogService(
			IThirdDingLeavelogService thirdDingLeavelogService) {
		this.thirdDingLeavelogService = thirdDingLeavelogService;
	}

	public void setOmsRelationService(IOmsRelationService omsRelationService) {
		this.omsRelationService = omsRelationService;
	}


	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		try {

			logger.warn("处理销假节点结束事件...");
			logger.debug("parameter:" + parameter);
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

			Map<String, Object> dataMap = new HashMap<>();

			// 销假种类：加班 请假 出差 外出
			// String fdCancelType = (String) getFieldValue(baseModel, params,
			// "fdCancelType");

			String fdCancelType = DING_OVERTIME;

			logger.debug("======fdCancelType:" + fdCancelType);
			if (StringUtil.isNull(fdCancelType)) {
				logger.warn("销假种类名称fdCancelType为空，默认只是销假，不重新提考勤");
				// logger.warn("默认只是销假，不重新提考勤！");
			} else {
				fdCancelType = fdCancelType.trim();
			}

			// 申请人(加班要特殊处理一下，加班人字段 fdOvertimeTargets )
			SysOrgPerson user = null;
			if (DING_OVERTIME.equals(fdCancelType)) {
				user = (SysOrgPerson) getFieldValue(baseModel, params,
						"fdOvertimeTargets");
			} else {
				user = (SysOrgPerson) getFieldValue(baseModel, params,
						"fdLeaveTargets");
			}
			String ekpUserId = null;

			// 转换成钉钉的userid
			String userid = null;
			if (null != user) {
				ekpUserId = user.getFdId();
				userid = omsRelationService
						.getDingUserIdByEkpUserId(ekpUserId);
			} else {
				logger.error("获取申请人信息为null");
			}
			logger.debug("钉钉userid：" + userid);
			if (userid != null) {
				dataMap.put("userid", userid);
			} else {
				logger.error("钉钉不存在该用户");
			}

			// 获取流程主题
			String docSubject = (String) DingUtil.getModelPropertyString(
					baseModel,
					"docSubject", "", null);

			logger.debug("流程标题=>" + docSubject);

			String fdApproveId = (String) getFieldValue(baseModel, params,
					"fdApproveId");
			System.out.println("======fdApproveId:" + fdApproveId);

			if (StringUtil.isNull(fdApproveId)) {
				logger.error("fdApproveId为空");
			} else {
				dataMap.put("approve_id", fdApproveId);
			}



			// Object rsubmint = getFieldValue(baseModel, params, "fdResubmit");
			// if (null == rsubmint) {
			// logger.warn("默认只是销假，不重新提考勤！");
			// rsubmint = "0";
			// }
			// String fdResubmit = String.valueOf(rsubmint);
			// int resubmit = Double.valueOf(fdResubmit).intValue(); //1表示重新提表单
			// 0表示只是销假
			int resubmit = 0;
			if (StringUtil.isNotNull(fdCancelType)) {
				// 根据开始时间判断是否需要重新提表单 fdStartDate
				// Date fdStartDate = (Date) getFieldValue(baseModel, params,
				// "fdStartDate");
				String fdStartDate = params.getString("fdStartDate");
				logger.error("*****************fdStartDate:" + fdStartDate);
				if (StringUtil.isNotNull(fdStartDate)) {

					Date sDate = (Date) getFieldValue(baseModel, params,
							"fdStartDate");
					logger.debug("sDate：" + sDate);
					Date end_Date = (Date) getFieldValue(baseModel, params,
							"fdEndDate");
					logger.debug("end_Date：" + end_Date);

					if (sDate != null && end_Date != null) {
						logger.debug(
								"根据fdStartDate和fdEndDate判断该销加班需要重新提交表单："
										+ fdStartDate);
						resubmit = 1;
					} else {
						logger.warn(
								"根据fdStartDate和fdEndDate至少有一个为null判断该销加班不需要重新提交表单！");
					}

				}
			}

			logger.debug("======resubmit:" + resubmit);
			logger.debug("【" + fdCancelType + "销假】dataMap:" + dataMap);

			Map<String, Object> inParam = new HashMap<>();
			inParam.putAll(dataMap);
			// 开始通知钉钉
			JSONObject result = cancelNotifyDing(dataMap);

			dataMap.put("paramMap", params);
			dataMap.put("params", inParam);
			dataMap.put("result", result);
			dataMap.put("docSubject", docSubject);
			dataMap.put("ekpUserId", ekpUserId);
			dataMap.put("sub_type", fdCancelType);
			dataMap.put("tag_name", "销假");
			dataMap.put("biz_type", 0);
			// 将approve_id 改为文档的id以便查看页面跳转
			logger.debug("将approve_id 改为文档的id以便查看页面跳转：" + fdApproveId + " -> "
					+ baseModel.getFdId());
			dataMap.put("approve_id", baseModel.getFdId());

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"fdEkpUserid=:fdEkpUserid and fdApproveId=:fdApproveId and fdIstrue=:fdIstrue and fdTagName != :fdTagName");
			hqlInfo.setParameter("fdEkpUserid", ekpUserId);
			hqlInfo.setParameter("fdApproveId", fdApproveId);
			hqlInfo.setParameter("fdTagName", "销假");
			hqlInfo.setParameter("fdIstrue", "1");

			List<ThirdDingLeavelog> oldLeavelog = thirdDingLeavelogService
					.findList(hqlInfo);
			String fdReason = "";
			if (oldLeavelog != null && oldLeavelog.size() > 0) {
				String oldDOCSubject = oldLeavelog.get(0)
						.getDocSubject();
				logger.debug(" 撤销单号的标题：" + oldDOCSubject);
				fdReason += " 撤销单号的标题：" + oldDOCSubject;
				dataMap.put("jump_url", oldLeavelog.get(0).getFdId());
			}

			// 销假数据回写表单
			if (result != null && result.getInt("errcode") == 0) {
				fdReason = "【撤销" + fdCancelType + "操作成功】撤销单号：" + fdApproveId
						+ fdReason;

				try {
					logger.debug("开始修改原请假记录状态！");
					// 将已销假的请假记录的状态改为 0 fdIsrrue
					for (ThirdDingLeavelog thirdDingLeaveLog : oldLeavelog) {
						if ("销假".equals(thirdDingLeaveLog.getFdTagName())) {
							continue;
						}
						logger.warn("修改记录状态：（1 -> 2）:"
								+ thirdDingLeaveLog.getDocSubject());
						thirdDingLeaveLog.setFdIstrue("2");
						// thirdDingLeaveLog.setFdSubType(
						// thirdDingLeaveLog.getFdSubType() + "(已销假)");
						thirdDingLeaveLog.setFdReason(
								"该考勤单已成功从钉钉端撤回！");
						thirdDingLeavelogService.update(thirdDingLeaveLog);
					}

					dataMap.put("fd_reason", fdReason);
					try {
						ThirdDingLeavelog docMain = writeCancelLog(dataMap,
								DING_SUCCESS);
					} catch (Exception e) {
						logger.error("写入销假日志发生异常！" + e);
					}

					// 销假成功，下面处理重新提交请假申请逻辑
					if (DING_RESUBMIT == resubmit
							&& StringUtil.isNotNull(fdCancelType)) {
						// 重新提交请假申请
						logger.debug("重新提交" + fdCancelType + "申请：" + parameter);
						try {
							switch (fdCancelType) {
							case DING_LEAVE:
								new LeaveApprovalListener(omsRelationService,
										thirdDingLeavelogService).handleEvent(
												execution,
												parameter);
								break;
							case DING_BUSINESS:
								new BusinessApprovalListener(omsRelationService,
										thirdDingLeavelogService).handleEvent(
												execution,
												parameter);
								break;
							case DING_TRIP:
								new BusinessApprovalListener(omsRelationService,
										thirdDingLeavelogService).handleEvent(
												execution,
												parameter);
								break;
							case DING_OVERTIME:
								new OvertimeApprovalListener(omsRelationService,
										thirdDingLeavelogService).handleEvent(
												execution,
												parameter);
								break;
							}


						} catch (Exception e) {
							logger.error("重新提交流程中发生异常：" + e);
						}

					} else {
						logger.warn("只是销假，不重新提交请假申请：" + parameter);
					}

				} catch (Exception e2) {
					logger.error("写入钉钉考勤中发生了异常");
					logger.error("", e2);
				}

			} else {

				try {
					logger.warn("钉钉返回错误");
					// 失败，写入请假日志并标记为失败
					String reason = result == null ? "钉钉返回结果为空"
							: JSONUtils.valueToString(result);
					dataMap.put("fd_reason",
							"钉钉返回错误:" + reason);
					writeCancelLog(dataMap, DING_ERROR);
				} catch (Exception e2) {
					logger.error("写入钉钉考勤中发生了异常");
					logger.error("", e2);
				}

			}

		} catch (Exception e) {
			logger.error("", e);
		}

	}

	/**
	 * 销假同步写入日志
	 * 
	 * @param dataMap
	 * @param dingSuccess
	 * @return
	 * @throws Exception
	 */
	private ThirdDingLeavelog writeCancelLog(Map<String, Object> dataMap,
			int flag) throws Exception {

		logger.warn("写入销假日志...");

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
		thirdDingLeaveLog
				.setFdApproveId(String.valueOf(dataMap.get("approve_id")));
		thirdDingLeaveLog.setFdIstrue(String.valueOf(flag));
		thirdDingLeaveLog.setFdReason(String.valueOf(dataMap.get("fd_reason")));
		thirdDingLeaveLog.setFdJumpUrl(String.valueOf(dataMap.get("jump_url")));
		thirdDingLeaveLog
				.setFdParamMap(JSON.toJSONString(dataMap.get("paramMap")));
		Map<String, Object> inParamMap = new HashMap<>();
		inParamMap.put("url", "attendance/approve/cancel");
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

	/**
	 * 获取字段值
	 * 
	 * @param baseModel
	 * @param parameter
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
	 * 通知钉钉
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	private JSONObject cancelNotifyDing(Map<String, Object> dataMap)
			throws Exception {
		logger.warn("销假审批通过，开始通知钉钉...");
		// 调用钉钉通知接口
		JSONObject param = new JSONObject();
		param.putAll(dataMap);
		JSONObject result = dingApiService.approveCanel(param, null);
		logger.warn("result=>" + JSONUtils.valueToString(result));
		// 这里需要把钉钉返回的数据回写到表单里面
		logger.info("审批通过通知钉钉结束");
		return result;
	}
}
