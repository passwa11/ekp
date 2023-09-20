package com.landray.kmss.third.ding.function;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.slf4j.Logger;

import java.util.*;

public class DingFunctions {
	private static DingApiService dingApiService = DingUtils
			.getDingApiService();
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingFunctions.class);

	/**
	 * 根据开始时间和结束时间计算时长
	 *
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 时长
	 */
	public static double calcuateTime(SysOrgPerson person, Date fdStartDate,
			Date fdStartTime, String fdStartTimeHalf,
			Date fdEndDate, Date fdEndTime, String fdEndTimeHalf,
			Object durationUnit2, Object fdOffType2)
			throws Exception {
		
		String fdOffType = String.valueOf(fdOffType2);
		if (null == person) {
			return 0d;
		}
		String durationUnit = String.valueOf(durationUnit2);
		if (StringUtil.isNotNull(durationUnit) && durationUnit.contains(".0")) {
			durationUnit = durationUnit.substring(0,
					durationUnit.indexOf(".0"));
		}
		if (StringUtil.isNull(durationUnit)) {
			return 0d;
		}
		JSONObject params = new JSONObject();
		params.put("biz_type", 3L);
		params.put("userid", getDingUserIdByEkpUserId(person.getFdId()));
		int type = Integer.parseInt(durationUnit);
		if (0 == type) {
			return 0d;
		}
		
		switch (type) {
		case 1: {// 天
			params.put("duration_unit", "day");
			params.put("from_time",
					DateUtil.convertDateToString(fdStartDate, "yyyy-MM-dd"));
			params.put("to_time",
					DateUtil.convertDateToString(fdEndDate, "yyyy-MM-dd"));
			break;
		}
		case 2: {// 半天
			params.put("duration_unit", "halfDay");
			params.put("from_time",
					DateUtil.convertDateToString(fdStartDate, "yyyy-MM-dd")
							+ " " + fdStartTimeHalf);
			params.put("to_time",
					DateUtil.convertDateToString(fdEndDate, "yyyy-MM-dd") + " "
							+ fdEndTimeHalf);
			break;
		}
		case 3: {// 小时
			params.put("duration_unit", "hour");
			params.put("from_time",
					DateUtil.convertDateToString(fdStartDate, "yyyy-MM-dd")
							+ " "
							+ DateUtil.convertDateToString(fdStartTime,
									"HH:mm"));
			params.put("to_time",
					DateUtil.convertDateToString(fdEndDate, "yyyy-MM-dd") + " "
							+ DateUtil.convertDateToString(fdEndTime, "HH:mm"));
			break;
		}
		}

		// 计算类型，从假期管理配置读取
		ISysTimeLeaveRuleService sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil
				.getBean("sysTimeLeaveRuleService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdStatDayType");
		hqlInfo.setWhereBlock(
				"sysTimeLeaveRule.fdIsAvailable=true and fdSerialNo=:fdOffType");
		hqlInfo.setParameter("fdOffType", fdOffType);
		Integer fdStatDayType = (Integer) sysTimeLeaveRuleService.findFirstOne(hqlInfo);
		if (fdStatDayType != null) {
			params.put("calculate_model", fdStatDayType == 2 ? 0 : 1);
		} else {
			String unit = params.getString("duration_unit");
			if ("halfDay".equals(unit)) {
				params.put("calculate_model", 0);
			} else {
				params.put("calculate_model", 1);
			}
		}
		logger.warn("请假预计算时长params=>" + JSONUtils.valueToString(params));
		JSONObject result = dingApiService.preCalcuateDate(params,
				person.getFdId());
		logger.warn("result=>" + JSONUtils.valueToString(result));

		double duration = 0d;
		if (result != null && result.getInt("errcode") == 0) {
			duration = Double.parseDouble(
					result.getJSONObject("result").getString("duration"));
			logger.info("请假预计算时长操作成功");
		} else if (result != null && result.getInt("errcode") == 850012) {
			logger.debug("计算自然天不能超过一个月（钉钉规定单次不允许超过一个月）");
			Date fromDate = new Date();
			Date toDate = new Date();

			if ("day".equals(params.getString("duration_unit"))) {
				fromDate = DateUtil.convertStringToDate(
						params.getString("from_time"), "yyyy-MM-dd");
				toDate = DateUtil.convertStringToDate(
						params.getString("to_time"), "yyyy-MM-dd");
			} else if ("halfDay".equals(params.getString("duration_unit"))) {
				fromDate = DateUtil.convertStringToDate(
						DateUtil.convertDateToString(fdStartDate, "yyyy-MM-dd"),
						"yyyy-MM-dd");
				toDate = DateUtil.convertStringToDate(
						DateUtil.convertDateToString(fdEndDate, "yyyy-MM-dd"),
						"yyyy-MM-dd");

			} else {
				// 小时
				fromDate = DateUtil.convertStringToDate(
						params.getString("from_time"), "yyyy-MM-dd HH:mm");
				toDate = DateUtil.convertStringToDate(
						params.getString("to_time"), "yyyy-MM-dd HH:mm");
			}

			Date tempStartDate = fromDate;
			Date tempEndDate = new Date();
			Boolean isEnd = true;
			String tempStartTimeHalf = fdStartTimeHalf;
			String tenpEndTimeHalf = fdEndTimeHalf;
			do {

				tempEndDate = DateUtil.getNextDay(tempStartDate, 28);

				if (toDate.before(tempEndDate) || toDate.equals(tempEndDate)) {
					tempEndDate = toDate;
					isEnd = false;
				}
				if ("day".equals(params.getString("duration_unit"))) {
					params.element("from_time",
							DateUtil.convertDateToString(tempStartDate,
									"yyyy-MM-dd"));
					params.element("to_time",
							DateUtil.convertDateToString(tempEndDate,
									"yyyy-MM-dd"));
				} else if ("halfDay"
						.equals(params.getString("duration_unit"))) {
					params.element("from_time",
							DateUtil.convertDateToString(tempStartDate,
									"yyyy-MM-dd") + " " + tempStartTimeHalf);
					params.element("to_time",
							DateUtil.convertDateToString(tempEndDate,
									"yyyy-MM-dd") + " " + fdEndTimeHalf);

				} else {
					params.element("from_time",
							DateUtil.convertDateToString(tempStartDate,
									"yyyy-MM-dd HH:mm"));
					params.element("to_time",
							DateUtil.convertDateToString(tempEndDate,
									"yyyy-MM-dd HH:mm"));
				}

				logger.warn(
						"请假预计算时长params=>" + JSONUtils.valueToString(params));
				result = dingApiService.preCalcuateDate(params,
						person.getFdId());
				if (result != null && result.getInt("errcode") == 0) {
					duration += Double.parseDouble(
							result.getJSONObject("result")
									.getString("duration"));
					logger.info("请假预计算时长操作成功");
				}
				tempStartDate = tempEndDate;
				if ("day".equals(params.getString("duration_unit"))) {
					tempStartDate = DateUtil.getNextDay(tempStartDate, 1);
				} else if ("halfDay"
						.equals(params.getString("duration_unit"))) {
					if ("PM".equals(tenpEndTimeHalf)) {
						tempStartDate = DateUtil.getNextDay(tempStartDate, 1);
					}
				}
				tempStartTimeHalf = "AM".equals(tenpEndTimeHalf) ? "PM" : "AM"; // 上下午交替

			} while (isEnd);

		} else {
			logger.warn("请假预计算时长钉钉返回错误");
		}

		return duration;
	}

	/**
	 * 预计算外出时长
	 * 
	 * @param person
	 * @param fdStartDate
	 * @param fdStartTime
	 * @param fdEndDate
	 * @param fdEndTime
	 * @return
	 * @throws Exception
	 */
	public static double calcuateBusinessTime(Object person_obj,
			Date fdStartDate, Date fdStartTime, Date fdEndDate, Date fdEndTime)
			throws Exception {

		logger.debug("预计算外出时长      person_obj：" + person_obj);
		if (null == person_obj) {
			return 0d;
		}

		SysOrgPerson person = new SysOrgPerson();
		String ding_userId = "";

		try {
			if (person_obj instanceof ArrayList) {
				logger.debug("多人申请外出预计算入参");
				ArrayList<SysOrgPerson> personList = (ArrayList<SysOrgPerson>) person_obj;
				for (SysOrgPerson per : personList) {
					ding_userId = getDingUserIdByEkpUserId(per.getFdId());
					person = per;
					if (StringUtil.isNotNull(ding_userId)) {
						break;
					}
				}

			} else if (person_obj instanceof SysOrgPerson) {
				logger.debug("单人申请外出预计算入参");
				person = (SysOrgPerson) person_obj;
				ding_userId = getDingUserIdByEkpUserId(person.getFdId());

			}
		} catch (Exception e) {
			logger.error("预计算外出时长时，申请人获取过程中发生异常");
			logger.error(e.getMessage(), e);
		}

		logger.debug("获取外出预计算数据人员为：" + person.getFdName() + "  ding_userId:"
				+ ding_userId);

		JSONObject params = new JSONObject();
		params.put("biz_type", 2L);
		params.put("userid", getDingUserIdByEkpUserId(person.getFdId()));

		params.put("duration_unit", "hour");
		// 开始时间
		params.put("from_time",
				DateUtil.convertDateToString(fdStartDate, "yyyy-MM-dd")
						+ " " + DateUtil.convertDateToString(fdStartTime,
								"HH:mm"));

		// 结束时间
		params.put("to_time",
				DateUtil.convertDateToString(fdEndDate, "yyyy-MM-dd") + " "
						+ DateUtil.convertDateToString(fdEndTime, "HH:mm"));

		params.put("calculate_model", 1);
		logger.warn("外出预计算出差时长params=>" + JSONUtils.valueToString(params));
		JSONObject result = dingApiService.preCalcuateDate(params,
				person.getFdId());
		logger.warn("result=>" + JSONUtils.valueToString(result));

		double duration = 0d;
		if (result != null && result.getInt("errcode") == 0) {
			duration = Double.parseDouble(
					result.getJSONObject("result").getString("duration"));
			logger.info("外出预计算时长操作成功");
		} else {
			logger.warn("外出预计算时长钉钉返回错误");
		}

		return duration;
	}

	/**
	 * 预计算出差时长
	 * 
	 * @param person
	 * @param fdStartDate
	 * @param fdEndDate
	 * @return
	 * @throws Exception
	 */
	public static double calcuateTripTime(Object person_obj,
			Date fdStartDate, Date fdEndDate)
			throws Exception {
		if (null == person_obj) {
			return 0d;
		}

		SysOrgPerson person = new SysOrgPerson();
		String ding_userId = "";

		try {
			if (person_obj instanceof ArrayList) {
				logger.debug("多人申请出差预计算入参");
				ArrayList<SysOrgPerson> personList = (ArrayList<SysOrgPerson>) person_obj;
				for (SysOrgPerson per : personList) {
					ding_userId = getDingUserIdByEkpUserId(per.getFdId());
					if (StringUtil.isNotNull(ding_userId)) {
						person = per;
						break;
					}
				}

			} else if (person_obj instanceof SysOrgPerson) {
				logger.debug("单人申请出差预计算入参");
				person = (SysOrgPerson) person_obj;
				ding_userId = getDingUserIdByEkpUserId(person.getFdId());

			}
		} catch (Exception e) {
			logger.error("预计算出差时长时，申请人获取过程中发生异常");
			logger.error(e.getMessage(), e);
		}

		logger.debug("获取出差预计算数据人员为：" + person.getFdName() + "  ding_userId:"
				+ ding_userId);

		JSONObject params = new JSONObject();
		params.put("biz_type", 2L);
		params.put("userid", getDingUserIdByEkpUserId(person.getFdId()));

		// 外出时长单位

		params.put("duration_unit", "day");
		params.put("from_time",
				DateUtil.convertDateToString(fdStartDate, "yyyy-MM-dd"));
		params.put("to_time",
				DateUtil.convertDateToString(fdEndDate, "yyyy-MM-dd"));

		params.put("calculate_model", 1);
		logger.warn("出差预计算出差时长params=>" + JSONUtils.valueToString(params));
		JSONObject result = dingApiService.preCalcuateDate(params,
				person.getFdId());
		logger.warn("result=>" + JSONUtils.valueToString(result));

		double duration = 0d;
		if (result != null && result.getInt("errcode") == 0) {
			duration = Double.parseDouble(
					result.getJSONObject("result").getString("duration"));
			logger.info("出差预计算时长操作成功");
		} else {
			logger.warn("出差预计算时长钉钉返回错误");
		}

		return duration;
	}

	/**
	 * 预计算加班时长
	 * 
	 * @param person
	 *            加班人
	 * @param fdStartDate
	 *            加班开始时间
	 * @param fdEndDate
	 *            加班结束时间
	 * @return
	 * @throws Exception
	 */
	public static double calcuateOverTime(SysOrgPerson person, Date fdStartDate,
			Date fdEndDate)
			throws Exception {
		if (null == person) {
			return 0d;
		}

		JSONObject params = new JSONObject();
		params.put("biz_type", 1L);
		params.put("userid", getDingUserIdByEkpUserId(person.getFdId()));
		params.put("from_time",
				DateUtil.convertDateToString(fdStartDate, "yyyy-MM-dd HH:mm"));
		params.put("to_time",
				DateUtil.convertDateToString(fdEndDate, "yyyy-MM-dd HH:mm"));
		params.put("duration_unit", "hour");
		params.put("calculate_model", 1);

		logger.warn("加班预计算时长params=>" + JSONUtils.valueToString(params));
		JSONObject result = dingApiService.preCalcuateDate(params,
				person.getFdId());
		logger.warn("result=>" + JSONUtils.valueToString(result));

		double duration = 0d;
		if (result != null && result.getInt("errcode") == 0) {
			duration = Double.parseDouble(
					result.getJSONObject("result").getString("duration"));
			logger.info("加班预计算时长操作成功");
		} else {
			logger.warn("加班预计算时长钉钉返回错误");
		}

		return duration;
	}

	/**
	 * 查询排班列表,使用业务函数代替
	 * 
	 * @param person
	 *            操作人
	 * @param person
	 *            用户
	 * @param fdDateTime
	 *            排班时间
	 * @return
	 * @throws Exception
	 */
	public static List queryScheduleList(SysOrgPerson opPerson,
			SysOrgPerson person, Date fdDateTime)
			throws Exception {
		List resultList = new ArrayList<>();

		try {
			JSONObject params = new JSONObject();
			params.put("op_user_id",
					getDingUserIdByEkpUserId(opPerson.getFdId()));
			params.put("user_id", getDingUserIdByEkpUserId(person.getFdId()));
			params.put("date_time", fdDateTime.getTime());
			logger.warn("查询排班列表params=>" + JSONUtils.valueToString(params));
			JSONObject result = dingApiService.scheduleByDay(params, null);
			logger.warn("result=>" + JSONUtils.valueToString(result));
			if (result != null && result.getInt("errcode") == 0) {
				JSONArray ja = result.getJSONArray("result");
				logger.info("排班结果=>" + JSONUtils.valueToString(ja));

				for (Object jo : ja) {
					JSONObject jobj = (JSONObject) jo;
					Map<Long, String> punchMap = new HashMap<>();
					// 班次ID
					Long class_id = jobj.getLong("class_id");
					// 打卡时间
					String check_date_time = jobj.getString("check_date_time");
					punchMap.put(class_id, check_date_time);

					resultList.add(punchMap);
				}
			}
			logger.info("转换后的排班结果=>" + JSONUtils.valueToString(resultList));
		} catch (Exception e) {
			logger.error("公式加载错误", e);
			return null;
		}
		return resultList;
	}

	/**
	 * 根据ekUserId查找钉钉userid
	 *
	 * @param ekpUserId
	 * @return
	 * @throws Exception
	 */
	private static String getDingUserIdByEkpUserId(String ekpUserId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock("fdEkpId=:fdEkpId");
		hqlInfo.setParameter("fdEkpId", ekpUserId);
		IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
				.getBean("omsRelationService");
		return (String) omsRelationService.findFirstOne(hqlInfo);
	}
}
