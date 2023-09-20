package com.landray.kmss.third.ding.service.spring;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.third.ding.model.ThirdDingBuss;
import com.landray.kmss.third.ding.model.ThirdDingLeave;
import com.landray.kmss.third.ding.model.ThirdDingLeavelog;
import com.landray.kmss.third.ding.model.ThirdDingOvertime;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IThirdDingBussService;
import com.landray.kmss.third.ding.service.IThirdDingLeaveService;
import com.landray.kmss.third.ding.service.IThirdDingLeavelogService;
import com.landray.kmss.third.ding.service.IThirdDingOvertimeService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.sso.client.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.Date;
import java.util.Map;

public class ThirdDingLeavelogServiceImp extends ExtendDataServiceImp implements IThirdDingLeavelogService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingLeavelogServiceImp.class);
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
	/**
	 * 最多重试次数
	 */
	static final int MAX_RETRY_TIMES = 5;

	// =======================
	// 依赖注入开始
	// =======================
	private DingApiService dingApiService = DingUtils.getDingApiService();
	private IBaseService kmReviewMainService;
	private IThirdDingLeaveService thirdDingLeaveService;
	private IThirdDingBussService thirdDingBussService;
	private IThirdDingOvertimeService thirdDingOvertimeService;

	public IBaseService getKmReviewMainServiceImp() {
		if (kmReviewMainService == null) {
			kmReviewMainService = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
		}
		return kmReviewMainService;
	}

	public void setThirdDingLeaveService(
			IThirdDingLeaveService thirdDingLeaveService) {
		this.thirdDingLeaveService = thirdDingLeaveService;
	}

	public void setThirdDingBussService(
			IThirdDingBussService thirdDingBussService) {
		this.thirdDingBussService = thirdDingBussService;
	}

	public void setThirdDingOvertimeService(
			IThirdDingOvertimeService thirdDingOvertimeService) {
		this.thirdDingOvertimeService = thirdDingOvertimeService;
	}

	// =======================
	// 依赖注入结束
	// =======================

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model,
                                            ConvertorContext context) throws Exception {
		return super.convertBizFormToModel(form, model, context);
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingLeavelog thirdDingLeavelog = new ThirdDingLeavelog();
        thirdDingLeavelog.setDocCreateTime(new Date());
		thirdDingLeavelog.setDocAlterTime(new Date());
        thirdDingLeavelog.setDocCreator(UserUtil.getUser());
        ThirdDingUtil.initModelFromRequest(thirdDingLeavelog, requestContext);
        return thirdDingLeavelog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
		super.initCoreServiceFormSetting(form, model, requestContext);
    }

	// =====================================
	// 请假流程开始
	// =====================================

	@Override
	public boolean updateLeaveSync(String fdId) throws Exception {
		boolean flag = false;
		ThirdDingLeavelog log = null;
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" fdId=:fdId");
			hqlInfo.setParameter("fdId", fdId);
			log = (ThirdDingLeavelog) this.getBaseDao().findFirstOne(hqlInfo);
			if (log != null) {
				// 最多重试次数限制
				if (null != log.getFdSendTime()) {
					if (log.getFdSendTime().equals(MAX_RETRY_TIMES)) {
						return false;
					}
				} else {
					// 兼容之前没有该字段的情况
					log.setFdSendTime(1);
				}

				com.alibaba.fastjson.JSONObject paramObj = JSON
						.parseObject(log.getFdParams());
				Map<String, Object> dingParams = com.alibaba.fastjson.JSONObject
						.parseObject(paramObj.getString("params"), Map.class);

				JSONObject result = leaveNotifyDing(dingParams,
						log.getDocCreator() == null ? null
								: log.getDocCreator().getFdId());
				if (result != null && result.getInt("errcode") == 0) {
					flag = true;
					// 修改同步状态为成功
					log.setFdIstrue("1");
					log.setFdResult(JSON.toJSONString(result));
					log.setFdReason("请假写入钉钉考勤重试成功");

					// 回写表单
					// 需要保存参数映射才能实现回写
					IBaseModel baseModel = getKmReviewMainServiceImp()
							.findByPrimaryKey(log.getFdApproveId());
					String paramMap = log.getFdParamMap();
					// 兼任以前没保存参数映射的情况
					if (StringUtils.isNotEmpty(paramMap)) {
						JSONObject params = JSONObject.fromObject(paramMap);
						updateDingInfoToEkp(baseModel, params, result);
					}
					logger.info("重试时长回写表单=>" + baseModel);

					// 这里是记录请假明细具体逻辑
					JSONArray jsArr = result.getJSONObject("result")
							.getJSONArray("durationDetail");
					// 记录明细
					writeLeaveDetail(log, jsArr);
					logger.info("写入请假明细完成");
				} else {
					logger.error("请假同步重试钉钉异常");
				}
			}
		} catch (Exception e) {
			log.setFdIstrue("0");
			logger.error("请假同步重试失败", e);
		} finally {
			// 更新重试次数
			if (null != log) {
				Integer fdSendTime = log.getFdSendTime();
				if (fdSendTime > 0 && fdSendTime < MAX_RETRY_TIMES) {
					log.setFdSendTime(++fdSendTime);
					log.setDocAlterTime(new Date());
					this.update(log);
				}
			}
		}
		return flag;
	}

	/**
	 * 通知钉钉
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	@Override
	public JSONObject leaveNotifyDing(Map<String, Object> paramMap,
			String ekpUserId)
			throws Exception {
		logger.warn("审批通过，开始通知钉钉...");
		// 调用钉钉通知接口
		JSONObject param = new JSONObject();
		param.putAll(paramMap);
		logger.warn("params=>" + JSONUtils.valueToString(param));
		JSONObject result = dingApiService.approveFinish(param, ekpUserId);
		logger.warn("result=>" + JSONUtils.valueToString(result));
		// 这里需要把钉钉返回的数据回写到表单里面
		logger.info("审批通过通知钉钉结束");
		return result;
	}

	/**
	 * 钉钉数据回写到Ekp
	 * 
	 * @param baseModel
	 * @param params
	 *            入参映射
	 * @param result
	 *            钉钉返回的数据
	 * @throws Exception
	 */
	@Override
	public void updateDingInfoToEkp(IBaseModel baseModel, JSONObject params,
			JSONObject result) throws Exception {
		JSONObject data = result.getJSONObject("result");
		String duration = data.getString("duration");
		logger.warn("钉钉返回的实际时长=>" + duration);
		if (baseModel instanceof IExtendDataModel) {
			IExtendDataModel kmReviewMain = (IExtendDataModel) baseModel;
			Map<String, Object> extendData = kmReviewMain
					.getExtendDataModelInfo().getModelData();
			logger.warn("extendData=>" + extendData);

			// 设置时长为钉钉返回的实际时长
			String fdDurationKey = params
					.getJSONObject("fdDuration")
					.getString("value");
			kmReviewMain
					.getExtendDataModelInfo().getModelData()
					.put(fdDurationKey, duration);

			// 更新操作
			SysDataDict dict = SysDataDict.getInstance();
			SysDictModel dictModel = dict
					.getModel(ModelUtil.getModelClassName(baseModel));
			IBaseService kmReviewMainService = (IBaseService) SpringBeanUtil
					.getBean(dictModel.getServiceBean());
			kmReviewMainService.update(baseModel);
		}
	}

	/**
	 * 写入请假明细
	 * 
	 * @param jsArr
	 *            请假明细
	 * @throws Exception
	 */
	@Override
	public void writeLeaveDetail(ThirdDingLeavelog docMain, JSONArray jsArr)
			throws Exception {
		for (int i = 0; i < jsArr.size(); i++) {
			JSONObject jsObj = (JSONObject) jsArr.get(i);
			ThirdDingLeave thirdDingLeave = new ThirdDingLeave();
			thirdDingLeave.setFdDate(jsObj.getString("date"));
			thirdDingLeave.setFdDuration(jsObj.getString("duration"));
			thirdDingLeave.setDocMain(docMain);
			thirdDingLeave.setDocIndex(i);
			thirdDingLeaveService.add(thirdDingLeave);
		}
		logger.info("写入请假明细完成");
	}
	// =====================================
	// 请假流程结束
	// =====================================

	// =====================================
	// 外出流程开始
	// =====================================
	@Override
	public boolean updateBussSync(String fdId) throws Exception {
		boolean flag = false;
		ThirdDingLeavelog log = null;
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" fdId=:fdId");
			hqlInfo.setParameter("fdId", fdId);
			log = (ThirdDingLeavelog) this.getBaseDao().findFirstOne(hqlInfo);
			if (log != null) {
				// 最多重试次数限制
				if (null != log.getFdSendTime()) {
					if (log.getFdSendTime().equals(MAX_RETRY_TIMES)) {
						return false;
					}
				} else {
					// 兼容之前没有该字段的情况
					log.setFdSendTime(1);
				}

				com.alibaba.fastjson.JSONObject paramObj = JSON
						.parseObject(log.getFdParams());
				Map<String, Object> dingParams = com.alibaba.fastjson.JSONObject
						.parseObject(paramObj.getString("params"), Map.class);

				JSONObject result = bussNotifyDing(dingParams,
						log.getDocCreator() == null ? null
								: log.getDocCreator().getFdId());
				if (result != null && result.getInt("errcode") == 0) {
					flag = true;
					// 修改同步状态为成功
					log.setFdIstrue("1");
					log.setFdResult(JSON.toJSONString(result));
					log.setFdReason("外出写入钉钉考勤重试成功");

					// 这里是记录外出明细具体逻辑
					JSONArray jsArr = result.getJSONObject("result")
							.getJSONArray("durationDetail");
					// 记录明细
					writeBussDetail(log, jsArr);
					logger.info("写入外出明细完成");
				} else {
					logger.error("外出同步重试钉钉异常");
				}
			}
		} catch (Exception e) {
			log.setFdIstrue("0");
			logger.error("外出同步重试失败", e);
		} finally {
			// 更新重试次数
			if (null != log) {
				Integer fdSendTime = log.getFdSendTime();
				if (fdSendTime > 0 && fdSendTime < MAX_RETRY_TIMES) {
					log.setFdSendTime(++fdSendTime);
					log.setDocAlterTime(new Date());
					this.update(log);
				}
			}
		}
		return flag;
	}

	/**
	 * 通知钉钉
	 * 
	 * @throws Exception
	 */
	@Override
	public JSONObject bussNotifyDing(Map<String, Object> dataMap,
			String ekpUserId)
			throws Exception {
		logger.warn("审批通过，开始通知钉钉...");
		// 调用钉钉通知接口
		JSONObject param = new JSONObject();
		param.putAll(dataMap);
		JSONObject result = dingApiService.approveFinish(param, ekpUserId);
		logger.warn("result=>" + JSONUtils.valueToString(result));
		// 这里需要把钉钉返回的数据回写到表单里面
		logger.info("审批通过通知钉钉结束");
		return result;
	}

	/**
	 * 写入外出明细
	 * 
	 * @param jsArr
	 *            外出明细
	 * @throws Exception
	 */
	@Override
	public void writeBussDetail(ThirdDingLeavelog docMain, JSONArray jsArr)
			throws Exception {
		for (int i = 0; i < jsArr.size(); i++) {
			JSONObject jsObj = (JSONObject) jsArr.get(i);
			ThirdDingBuss thirdDingBuss = new ThirdDingBuss();
			thirdDingBuss.setFdDate(jsObj.getString("date"));
			thirdDingBuss.setFdDuration(jsObj.getString("duration"));
			thirdDingBuss.setDocMain(docMain);
			thirdDingBuss.setDocIndex(i);
			thirdDingBussService.add(thirdDingBuss);
		}
		logger.info("写入外出明细完成");
	}
	// =====================================
	// 外出流程结束
	// =====================================

	// =====================================
	// 补卡流程开始
	// =====================================
	@Override
	public boolean updateCheckSync(String fdId) throws Exception {
		boolean flag = false;
		ThirdDingLeavelog log = null;
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" fdId=:fdId");
			hqlInfo.setParameter("fdId", fdId);
			log = (ThirdDingLeavelog) this.getBaseDao().findFirstOne(hqlInfo);
			if (log != null) {
				// 最多重试次数限制
				if (null != log.getFdSendTime()) {
					if (log.getFdSendTime().equals(MAX_RETRY_TIMES)) {
						return false;
					}
				} else {
					// 兼容之前没有该字段的情况
					log.setFdSendTime(1);
				}

				com.alibaba.fastjson.JSONObject paramObj = JSON
						.parseObject(log.getFdParams());
				Map<String, Object> dingParams = com.alibaba.fastjson.JSONObject
						.parseObject(paramObj.getString("params"), Map.class);

				JSONObject result = checkNotifyDing(dingParams,
						log.getDocCreator() == null ? null
								: log.getDocCreator().getFdId());
				if (result != null && result.getInt("errcode") == 0) {
					flag = true;
					// 修改同步状态为成功
					log.setFdIstrue("1");
					log.setFdResult(JSON.toJSONString(result));
					log.setFdReason("补卡写入钉钉考勤重试成功");
				} else {
					logger.error("补卡同步重试钉钉异常");
				}
			}
		} catch (Exception e) {
			log.setFdIstrue("0");
			logger.error("补卡同步重试失败", e);
		} finally {
			// 更新重试次数
			if (null != log) {
				Integer fdSendTime = log.getFdSendTime();
				if (fdSendTime > 0 && fdSendTime < MAX_RETRY_TIMES) {
					log.setFdSendTime(++fdSendTime);
					log.setDocAlterTime(new Date());
					this.update(log);
				}
			}
		}
		return flag;
	}
	/**
	 * 通知钉钉补卡
	 * 
	 * @throws Exception
	 */
	@Override
    public JSONObject checkNotifyDing(Map<String, Object> dataMap,
                                      String ekpUserId)
			throws Exception {
		logger.warn("审批通过，开始通知钉钉补卡...");
		// 调用钉钉通知接口
		JSONObject param = new JSONObject();
		param.putAll(dataMap);
		JSONObject result = dingApiService.approveCheck(param, ekpUserId);
		logger.warn("result=>" + JSONUtils.valueToString(result));
		// 这里需要把钉钉返回的数据回写到表单里面
		logger.info("审批通过通知钉钉补卡结束");
		return result;
	}
	// =====================================
	// 补卡流程结束
	// =====================================

	// =====================================
	// 换班流程开始
	// =====================================

	/**
	 * 换班流通知钉钉
	 * 
	 * @throws Exception
	 */
	@Override
	public boolean updateSwitchSync(String fdId) throws Exception {
		boolean flag = false;
		ThirdDingLeavelog log = null;
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" fdId=:fdId");
			hqlInfo.setParameter("fdId", fdId);
			log = (ThirdDingLeavelog) this.getBaseDao().findFirstOne(hqlInfo);
			if (log != null) {
				// 最多重试次数限制
				if (null != log.getFdSendTime()) {
					if (log.getFdSendTime().equals(MAX_RETRY_TIMES)) {
						return false;
					}
				} else {
					// 兼容之前没有该字段的情况
					log.setFdSendTime(1);
				}

				com.alibaba.fastjson.JSONObject paramObj = JSON
						.parseObject(log.getFdParams());
				Map<String, Object> dingParams = com.alibaba.fastjson.JSONObject
						.parseObject(paramObj.getString("params"), Map.class);

				JSONObject result = switchNotifyDing(dingParams,
						log.getDocCreator() == null ? null
								: log.getDocCreator().getFdId());
				if (result != null && result.getInt("errcode") == 0) {
					flag = true;
					// 修改同步状态为成功
					log.setFdIstrue("1");
					log.setFdResult(JSON.toJSONString(result));
					log.setFdReason("换班写入钉钉考勤重试成功");
				} else {
					logger.error("换班同步重试钉钉异常");
				}
			}
		} catch (Exception e) {
			log.setFdIstrue("0");
			logger.error("换班同步重试失败", e);
		} finally {
			// 更新重试次数
			if (null != log) {
				Integer fdSendTime = log.getFdSendTime();
				if (fdSendTime > 0 && fdSendTime < MAX_RETRY_TIMES) {
					log.setFdSendTime(++fdSendTime);
					log.setDocAlterTime(new Date());
					this.update(log);
				}
			}
		}
		return flag;
	}

	@Override
	public JSONObject switchNotifyDing(Map<String, Object> dataMap,
			String ekpUserId)
			throws Exception {
		logger.warn("审批通过，开始通知钉钉...");
		// 调用钉钉通知接口
		JSONObject param = new JSONObject();
		param.putAll(dataMap);
		logger.warn("调用钉钉换班接口,入参=>" + JSON.toJSONString(dataMap));
		JSONObject result = dingApiService.approveSwitch(param, null);
		logger.warn("result=>" + JSONUtils.valueToString(result));
		return result;
	}
	// =====================================
	// 换班流程结束
	// =====================================

	// =====================================
	// 加班流程结束
	// =====================================
	@Override
	public boolean updateOvertimeSync(String fdId) throws Exception {
		boolean flag = false;
		ThirdDingLeavelog log = null;
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" fdId=:fdId");
			hqlInfo.setParameter("fdId", fdId);
			log = (ThirdDingLeavelog) this.getBaseDao().findFirstOne(hqlInfo);
			if (log != null) {
				// 最多重试次数限制
				if (null != log.getFdSendTime()) {
					if (log.getFdSendTime().equals(MAX_RETRY_TIMES)) {
						return false;
					}
				} else {
					// 兼容之前没有该字段的情况
					log.setFdSendTime(1);
				}

				com.alibaba.fastjson.JSONObject paramObj = JSON
						.parseObject(log.getFdParams());
				Map<String, Object> dingParams = com.alibaba.fastjson.JSONObject
						.parseObject(paramObj.getString("params"), Map.class);

				JSONObject result = overtimeNotifyDing(dingParams,
						log.getDocCreator() == null ? null
								: log.getDocCreator().getFdId());
				if (result != null && result.getInt("errcode") == 0) {
					flag = true;
					// 修改同步状态为成功
					log.setFdIstrue("1");
					log.setFdResult(JSON.toJSONString(result));
					log.setFdReason("加班写入钉钉考勤重试成功");

					// 这里是记录外出明细具体逻辑
					JSONArray jsArr = result.getJSONObject("result")
							.getJSONArray("durationDetail");
					// 记录明细
					writeOvertimeDetail(log, jsArr);
					logger.info("写入加班明细完成");
				} else {
					logger.error("加班同步重试钉钉异常");
				}
			}
		} catch (Exception e) {
			log.setFdIstrue("0");
			logger.error("加班同步重试失败", e);
		} finally {
			// 更新重试次数
			if (null != log) {
				Integer fdSendTime = log.getFdSendTime();
				if (fdSendTime > 0 && fdSendTime < MAX_RETRY_TIMES) {
					log.setFdSendTime(++fdSendTime);
					log.setDocAlterTime(new Date());
					this.update(log);
				}
			}
		}
		return flag;
	}

	@Override
	public JSONObject overtimeNotifyDing(Map<String, Object> dataMap,
			String ekpUserId)
			throws Exception {
		logger.warn("审批通过，开始通知钉钉...");
		// 调用钉钉通知接口
		JSONObject param = new JSONObject();
		param.putAll(dataMap);
		logger.warn("调用钉钉加班接口,入参=>" + JSON.toJSONString(dataMap));
		JSONObject result = dingApiService.approveFinish(param, ekpUserId);
		logger.warn("result=>" + JSONUtils.valueToString(result));
		return result;
	}

	@Override
	public JSONObject attendenceOvertimeNotifyDing(Map<String, Object> dataMap,
			String ekpUserId)
			throws Exception {
		logger.warn("钉钉加班套件审批通过，开始通知钉钉...");
		// 调用钉钉通知接口
		JSONObject param = new JSONObject();
		String userid = (String) dataMap.get("userid");
		logger.debug("userid:" + userid);
		String[] userid_array = userid.split(";");
		JSONObject result = new JSONObject();
		JSONArray data = new JSONArray();
		JSONArray errorData = new JSONArray();
		boolean errcodeFlag = true;
		int errcode = -1;
		for (int i = 0; i < userid_array.length; i++) {
			if (StringUtil.isNull(userid_array[i])) {
				continue;
			}
			logger.debug("同步" + userid + "的数据");
			dataMap.put("userid", userid_array[i]);
			param.putAll(dataMap);
			logger.warn("调用钉钉加班接口,入参=>" + JSON.toJSONString(dataMap));
			JSONObject rs = dingApiService.approveFinish(param, ekpUserId);
			logger.warn("result=>" + JSONUtils.valueToString(rs));
			if (rs != null && rs.getInt("errcode") == 0) {
				rs.put("userid", userid_array[i]);
				data.add(rs);
			} else {
				errcodeFlag = false;
				if (rs == null) {
					rs = new JSONObject();
					rs.put("errMsg", "钉钉接口返回null");
				} else {
					errcode = rs.getInt("errcode");
				}
				rs.put("userid", userid_array[i]);
				errorData.add(rs);
			}
		}
		if (!errcodeFlag) {
			result.put("errcode", errcode);
		} else {
			result.put("errcode", 0);
		}
		result.put("data", data);
		result.put("errorData", errorData);
		return result;
	}

	@Override
	public void writeOvertimeDetail(ThirdDingLeavelog docMain, JSONArray jsArr)
			throws Exception {
		for (int i = 0; i < jsArr.size(); i++) {
			JSONObject jsObj = (JSONObject) jsArr.get(i);
			ThirdDingOvertime thirdDingOvertime = new ThirdDingOvertime();
			thirdDingOvertime.setFdDate(jsObj.getString("date"));
			thirdDingOvertime.setFdDuration(jsObj.getString("duration"));
			thirdDingOvertime.setDocMain(docMain);
			thirdDingOvertime.setDocIndex(i);
			thirdDingOvertimeService.add(thirdDingOvertime);
		}
		logger.info("写入外出明细完成");
	}
	// =====================================
	// 加班流程结束
	// =====================================

}
