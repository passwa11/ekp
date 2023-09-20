package com.landray.kmss.third.ding.util;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingDinstanceXform;
import com.landray.kmss.third.ding.model.ThirdDingIndanceXDetail;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingDinstanceXformService;
import com.landray.kmss.third.ding.service.IThirdDingLeavelogService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BizsuiteUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(BizsuiteUtil.class);

	private static IThirdDingDinstanceXformService thirdDingDinstanceXformService;

	public static IThirdDingDinstanceXformService
			getThirdDingDinstanceXformService() {
		if (thirdDingDinstanceXformService == null) {
			thirdDingDinstanceXformService = (IThirdDingDinstanceXformService) SpringBeanUtil
					.getBean("thirdDingDinstanceXformService");
		}
		return thirdDingDinstanceXformService;
	}

	/**
	 * 获取假期类型
	 * 
	 * @return
	 */
	public static JSONObject getBizsuiteTypes(String ekpUserId) {

		try {
			String userid = null;
			if (StringUtil.isNotNull(ekpUserId)) {
				userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(ekpUserId);
			} else {
				SysOrgPerson user = UserUtil.getUser();
				userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(user.getFdId());
			}
			if (StringUtil.isNull(userid)) {
				logger.warn("-----当前用户不在钉钉映射表中------fdId:" + ekpUserId);
				return new JSONObject();
			}
			String agentId = DingUtil.getAgentIdByCorpId(null);
			if (StringUtil.isNull(agentId)) {
				logger.warn("-----agentId为空------");
				agentId = DingConfig.newInstance().getDingAgentid();
			}

			JSONObject param = new JSONObject();
			param.put("biz_type", "attendance.leave");
			param.put("userid", userid);
			param.put("action_type", "getLeaveTypeWithBalance");
			param.put("agentid", agentId);

			return DingUtils.dingApiService.getBizsuite(param);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/*
	 * { "biz_type": "attendance.leave", 
	 *   "userid": "0913510944836576",
	 *   "action_type": "getLeaveTime",
	 *   "agentid":"869717408",
	 *   "form_data_list": [
	 *     { "label": "startTime", "extend_value": "", "value": "2020-10-29 09:00"},
	 *     { "label": "finishTime", "extend_value": "", "value":"2020-10-29 18:00" },
	 *     { "label": "type", "extend_value":"{\"leaveCode\":\"174dfc93-6cef-4ae1-be27-c3515fe39daf\"}",
	 *      "value": "" }
	 * ] }
	 */
	/**
	 * 预计算请假时长
	 * 
	 * @return
	 */
	public static JSONObject preCalculate(String startTime, String finishTime,
			String leaveCode, String ekpUserId) {

		try {
			String userid = null;
			if (StringUtil.isNotNull(ekpUserId)) {
				userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(ekpUserId);
			} else {
				SysOrgPerson user = UserUtil.getUser();
				userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(user.getFdId());
			}
			if (StringUtil.isNull(userid)) {
				logger.warn("-----当前用户不在钉钉映射表中------");
				return null;
			}
			String agentId = DingUtil.getAgentIdByCorpId(null);
			if (StringUtil.isNull(agentId)) {
				logger.warn("-----agentId为空------");
				agentId = DingConfig.newInstance().getDingAgentid();
			}

			JSONObject param = new JSONObject();
			param.put("biz_type", "attendance.leave");
			param.put("userid", userid);
			param.put("action_type", "getLeaveTime");
			param.put("agentid", agentId);
			
			JSONArray formDataList = new JSONArray();
			
			JSONObject startTimeItem = new JSONObject();
			startTimeItem.put("label", "startTime");
			startTimeItem.put("extend_value", "");
			startTimeItem.put("value", startTime);
			formDataList.add(startTimeItem);
			
			JSONObject endTimeItem = new JSONObject();
			endTimeItem.put("label", "finishTime");
			endTimeItem.put("extend_value", "");
			endTimeItem.put("value", finishTime);
			formDataList.add(endTimeItem);
			
			JSONObject typeItem = new JSONObject();
			typeItem.put("label", "type");
			JSONObject extendValue = new JSONObject();
			extendValue.put("leaveCode", leaveCode);
			typeItem.put("extend_value", extendValue);
			typeItem.put("value", "");
			formDataList.add(typeItem);
			
			param.put("form_data_list", formDataList);
			logger.debug("预计算请假时长:" + param);
			return DingUtils.dingApiService.getBizsuite(param);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/*
	 * 
	 * { "biz_type": "attendance.leave", "userid": "0913510944836576",
	 * "action_type": "canLeave", "agentid":"869717408", "form_data_list": [ {
	 * "label": "startTime", "extend_value": "", "value": "2020-10-29 09:00" },
	 * { "label": "finishTime", "extend_value": "", "value": "2020-10-29 18:00"
	 * }, { "label": "type", "extend_value":
	 * "{\"leaveCode\":\"174dfc93-6cef-4ae1-be27-c3515fe39daf\"}", "value": ""
	 * }, { "label": "leaveTimeInfo", "extend_value":
	 * "{\"compressedValue\":\"1f8b0800000000000000a550bb4ec33014fd973b87c869faf486a8502b51219574420c57894b2d5c3bf2b54155948189cf61e48b90f80ceca8a54162633c0fdf737c1a2851955ea1132b5309e05902957028d58d2407fcbe81ca5b74d2e8a59ee301384b6779d22317c65be09374c4faec4a6aef04011f8e58ca7e4977a234ba023e98b0286c053a6fa3b501ac6b75b8b6665fc87dec3266f92ccf078c75ce4e2d4c4f1be7d3a3562a240a97630475b585ae2e4b6b28a0a007f4e73b72685dcfd7e17e7a36ec9ced430292d6226eb245452281676129a401872c65d026b043ba8a357e1cb542bd0c3fbd08a3be18fb340f2b1fef32766a108518785e6cdaa5fd7bf633bfd132d486c5ed660da195a75d818f81f87afff87c7d83f61b3ccadcc405020000\",\"detailList\":[{\"approveInfo\":{\"durationInDay\":0.93,\"durationInHour\":7.50,\"fromAcross\":0,\"fromTime\":1603933200000,\"toAcross\":0,\"toTime\":1603963800000},\"classInfo\":{\"hasClass\":false,\"sections\":[{\"endAcross\":0,\"endTime\":1603963800000,\"startAcross\":0,\"startTime\":1603931400000}]},\"isRest\":false,\"workDate\":1603900800000,\"workTimeMinutes\":480}],\"durationInDay\":0.93,\"durationInHour\":7.50,\"extension\":\"{\\\"tag\\\":\\\"年假\\\"}\",\"isModifiable\":true,\"isNaturalDay\":false,\"pushTag\":\"请假\",\"unit\":\"HOUR\"}",
	 * "value": "" } ] }
	 * 
	 * 
	 */

	/**
	 * 判断是否能请假
	 * 
	 * @return
	 */
	public static JSONObject canLeave(String startTime, String finishTime,
			String leaveCode, String leaveTimeInfo, String ekpUserId) {

		try {
			String userid = null;
			if (StringUtil.isNotNull(ekpUserId)) {
				userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(ekpUserId);
			} else {
				SysOrgPerson user = UserUtil.getUser();
				userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(user.getFdId());
			}
			if (StringUtil.isNull(userid)) {
				logger.warn("-----当前用户不在钉钉映射表中------");
				return null;
			}
			String agentId = DingUtil.getAgentIdByCorpId(null);
			if (StringUtil.isNull(agentId)) {
				logger.warn("-----agentId为空------");
				agentId = DingConfig.newInstance().getDingAgentid();
			}

			JSONObject param = new JSONObject();
			param.put("biz_type", "attendance.leave");
			param.put("userid", userid);
			param.put("action_type", "canLeave");
			param.put("agentid", agentId);

			JSONArray formDataList = new JSONArray();

			JSONObject startTimeItem = new JSONObject();
			startTimeItem.put("label", "startTime");
			startTimeItem.put("extend_value", "");
			startTimeItem.put("value", startTime);
			formDataList.add(startTimeItem);

			JSONObject endTimeItem = new JSONObject();
			endTimeItem.put("label", "finishTime");
			endTimeItem.put("extend_value", "");
			endTimeItem.put("value", finishTime);
			formDataList.add(endTimeItem);

			JSONObject typeItem = new JSONObject();
			typeItem.put("label", "type");
			JSONObject extendValue = new JSONObject();
			extendValue.put("leaveCode", leaveCode);
			typeItem.put("extend_value", extendValue);
			typeItem.put("value", "");
			formDataList.add(typeItem);

			JSONObject leaveTimeInfoItem = new JSONObject();
			leaveTimeInfoItem.put("label", "leaveTimeInfo");
			if (StringUtil.isNull(leaveTimeInfo)) {
				leaveTimeInfo = getLeaveTimeInfo(startTime, finishTime,
						leaveCode, ekpUserId);
			}
			leaveTimeInfoItem.put("extend_value", leaveTimeInfo);
			leaveTimeInfoItem.put("value", "");
			formDataList.add(leaveTimeInfoItem);

			param.put("form_data_list", formDataList);
			logger.debug("判断是否能请假:" + param);
			JSONObject result = DingUtils.dingApiService.getBizsuite(param);
			logger.debug("-----result:" + result);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private static String getLeaveTimeInfo(String startTime, String finishTime,
			String leaveCode, String ekpUserId) {
		try {
			JSONObject rs = preCalculate(startTime, finishTime, leaveCode,
					ekpUserId);
			logger.debug(rs.toString());
			if (rs != null && rs.containsKey("errcode")
					&& rs.getInt("errcode") == 0) {
				return rs.getJSONObject("result").getJSONArray("form_data_list")
						.getJSONObject(0).getString("extend_value");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static JSONArray getCancelInfo(String ekpUserId,
										  String tagName) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setFromBlock("ThirdDingLeavelog thirdDingLeavelog");
			hqlInfo.setWhereBlock(
					"thirdDingLeavelog.fdApproveId=thirdDingDinstanceXform.fdEkpInstanceId and temp.fdId=thirdDingDinstanceXform.fdTemplate.fdId "
							+ "and thirdDingLeavelog.fdIstrue=1 and thirdDingLeavelog.fdTagName=:fdTagName"
							+ " and thirdDingLeavelog.fdIsDingSuit=1"
							+ " AND thirdDingLeavelog.fdEkpUserid=:fdEkpUserid "
							+ "AND thirdDingLeavelog.docCreateTime >=:docCreateTime "
							+ "AND thirdDingLeavelog.fdSubType !=:fdSubType");
			hqlInfo.setJoinBlock(
					" ,com.landray.kmss.third.ding.model.ThirdDingDinstanceXform thirdDingDinstanceXform ,com.landray.kmss.third.ding.model.ThirdDingDtemplateXform temp");
			hqlInfo.setParameter("fdTagName", tagName);
			hqlInfo.setParameter("fdEkpUserid", ekpUserId);
			hqlInfo.setParameter("docCreateTime", new Date(
					System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 30L));
			hqlInfo.setParameter("fdSubType", "销假");
			hqlInfo.setSelectBlock(
					" thirdDingLeavelog.fdId,thirdDingLeavelog.fdSubType,"
							+ "thirdDingLeavelog.docCreateTime,"
							+ "thirdDingDinstanceXform.fdEkpInstanceId ,"
							+ "thirdDingDinstanceXform.fdDingUserId,thirdDingDinstanceXform.fdInstanceId,"
							+ "thirdDingDinstanceXform.fdName,temp.fdProcessCode");

			IThirdDingLeavelogService thirdDingLeavelogService = (IThirdDingLeavelogService) SpringBeanUtil
					.getBean("thirdDingLeavelogService");
			List logList = thirdDingLeavelogService.findList(hqlInfo);
			if (logList == null) {
				return new JSONArray();
			}
			JSONArray rs = new JSONArray();
			if (logList.size() > 0) {
				// 获取主文档创建者信息
				IBaseService obj = (IBaseService) SpringBeanUtil
						.getBean("kmReviewMainService");
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				// 因为批量套件有多个实例，所以有多个记录
				Map<String, JSONObject> logInfo = new HashMap<String, JSONObject>();
				for (int i = 0; i < logList.size(); i++) {
					Object[] result = (Object[]) logList.get(i);
					String logId = (String) result[0];
					if (!logInfo.containsKey(logId)) {
						JSONObject json = new JSONObject();
						logger.warn("-----------fd_sub_type:"
								+ (String) result[1]);
						json.put("logId", (String) result[0]);
						json.put("leaveEndTime", DateUtil.convertDateToString((Date) result[2],"yyyy-MM-dd"));
						json.put("fd_ding_user_id", (String) result[4]);
						json.put("fd_ekp_instance_id", (String) result[3]);
						json.put("fd_instance_id", (String) result[5]);
						json.put("fd_name", (String) result[6]);
						json.put("fd_process_code", (String) result[7]);
						IBaseModel model = (IBaseModel) obj.findFirstOne(
								"fdId='" + (String) result[3]
										+ "'",
								null);
						if (model != null) {
							Map map = DingUtil
									.getExtendDataModelInfo(model);
							logger.debug("map:" + map);
							if ("批量请假".equals((String) result[1])) {
								json.put("batch", true);
								json.put("fd_sum_duration",
										(String) map.get("fd_sum_duration"));
								json.put("fd_leave_user",
										map.get("fd_leave_user"));
								json.put("batch_leave_table",
										map.get("fd_batch_leave_table"));
								// 把实例id塞到明细里面去
								pushInstanceId2Table((String) result[5], json);

								logInfo.put(logId, json);
							} else {
								json.put("extend_value",
										(String) map.get("extend_value"));
								json.put("leave_code",
										(String) map.get("leave_code"));
								json.put("unit", (String) map.get("unit"));
								json.put("from_half_day_str",
										(String) map.get("from_half_day"));
								json.put("leave_txt",
										(String) map.get("leave_txt"));
								json.put("to_half_day_str",
										(String) map.get("to_half_day"));
								json.put("from_time_str",
										sdf.format(map.get("from_time")));
								json.put("to_time_str",
										sdf.format(map.get("to_time")));
								logInfo.put(logId, json);
							}

						} else {
							logger.warn(
									"主文档找不到：" + (String) result[3]);
						}
					} else {
						// 批量套件，实例叠加
						JSONObject json = logInfo.get(logId);
						json.put("fd_instance_id",
								json.getString("fd_instance_id")
										+ ";" + (String) result[5]);
						// 把实例id塞到明细里面去
						pushInstanceId2Table((String) result[5], json);
						logInfo.put(logId, json);
					}

				}
				for (String id : logInfo.keySet()) {
					rs.add(logInfo.get(id));
				}
				return rs;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new JSONArray();
	}

	/*
	 * 把实例id放进批量套件的明细table中去
	 */
	private static void pushInstanceId2Table(String fdInstanceId,
			JSONObject json) {

		// 根据instanceId查出实例对象记录
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdInstanceId=:fdInstanceId");
			hqlInfo.setParameter("fdInstanceId", fdInstanceId);
			ThirdDingDinstanceXform xform = (ThirdDingDinstanceXform) getThirdDingDinstanceXformService()
					.findFirstOne(hqlInfo);
			String instanceStartTime = "";
			String instanceEndTime = "";
			if (xform != null) {
				List<ThirdDingIndanceXDetail> fdDetail = xform.getFdDetail();
				for (ThirdDingIndanceXDetail detail : fdDetail) {
					if ("开始时间".equals(detail.getFdName())) {
						instanceStartTime = detail.getFdValue();
					} else if ("结束时间".equals(detail.getFdName())) {
						instanceEndTime = detail.getFdValue();
					}
				}
			}
			JSONArray table = json.getJSONArray("batch_leave_table");
			for (int i = 0; i < table.size(); i++) {
				JSONObject item = table.getJSONObject(i);
				// 单位
				String unit = item.getString("type_unit");
				// 开始时间
				Long startTime = item.getJSONObject("fd_leave_start_time")
						.getLong("time");
				Date startDate = new Date(startTime);
				// 结束时间
				Long endTime = item.getJSONObject("fd_leave_end_time")
						.getLong("time");
				Date endDate = new Date(endTime);
				if ("hour".equals(unit)) {
					String _startTime = DateUtil.convertDateToString(startDate,
							"yyyy-MM-dd HH:mm");
					if (!instanceStartTime.equals(_startTime)) {
						continue;
					}
					String _endTime = DateUtil.convertDateToString(endDate,
							"yyyy-MM-dd HH:mm");
					if (!instanceEndTime.equals(_endTime)) {
						continue;
					}
					item.put("fdInstanceId", fdInstanceId);
					logger.debug("-------------匹配成功：" + fdInstanceId);
					break;
				} else if ("halfDay".equals(unit)) {
					String startHalfUnit = item.getString("fd_start_time_one");
					if ("AM".equalsIgnoreCase(startHalfUnit)) {
						startHalfUnit = "上午";
					} else if ("PM".equalsIgnoreCase(startHalfUnit)) {
						startHalfUnit = "下午";
					}
					String _startTime = DateUtil.convertDateToString(startDate,
							"yyyy-MM-dd");
					if (!instanceStartTime
							.equals(_startTime + " " + startHalfUnit)) {
						continue;
					}
					String endHalfUnit = item.getString("fd_end_time_one");
					if ("AM".equalsIgnoreCase(endHalfUnit)) {
						endHalfUnit = "上午";
					} else if ("PM".equalsIgnoreCase(endHalfUnit)) {
						endHalfUnit = "下午";
					}
					String _endTime = DateUtil.convertDateToString(endDate,
							"yyyy-MM-dd");
					if (!instanceEndTime.equals(_endTime + " " + endHalfUnit)) {
						continue;
					}
					item.put("fdInstanceId", fdInstanceId);
					logger.debug("-------------匹配成功：" + fdInstanceId);
					break;
				} else {
					String _startTime = DateUtil.convertDateToString(startDate,
							"yyyy-MM-dd");
					if (!instanceStartTime.equals(_startTime)) {
						continue;
					}
					String _endTime = DateUtil.convertDateToString(endDate,
							"yyyy-MM-dd");
					if (!instanceEndTime.equals(_endTime)) {
						continue;
					}
					item.put("fdInstanceId", fdInstanceId);
					logger.debug("-------------匹配成功：" + fdInstanceId);
					break;
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}


	}

	/**
	 * 获取个人补卡信息 { "errcode": 0, "errmsg": "ok", "result": { "form_data_list": [
	 * { "value": "本月已申请1次补卡， 剩余4次" } ], "seq_id": 0 }, "success": true,
	 * "request_id": "wgw9jewoe4d8" }
	 * 
	 * @param ekpUserId
	 * @return
	 */
	public static JSONObject getCheckInfo(String ekpUserId) {

		try {
			String userid = null;
			JSONObject param = new JSONObject();
			if (StringUtil.isNotNull(ekpUserId)) {
				userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(ekpUserId);
			} else {
				userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(
										UserUtil.getUser().getFdId());
			}
			if (StringUtil.isNull(userid)) {
				logger.warn("-----当前用户不在钉钉映射表中------fdId:" + ekpUserId);
				return param;
			}
			String agentId = DingUtil.getAgentIdByCorpId(null);
			if (StringUtil.isNull(agentId)) {
				logger.warn("-----agentId为空------");
				agentId = DingConfig.newInstance().getDingAgentid();
			}
			param.put("biz_type", "attendance.supply");
			param.put("userid", userid);
			param.put("action_type", "getSupplyTimesTips");
			param.put("agentid", agentId);
			return DingUtils.dingApiService.getBizsuite(param);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	// 获取考勤异常信息
	public static JSONObject getSupplyDates(String ekpUserId, String date) {

		try {
			String userid = null;
			if (StringUtil.isNotNull(ekpUserId)) {
				userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(ekpUserId);
			} else {
				userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(
										UserUtil.getUser().getFdId());
			}
			if (StringUtil.isNull(userid)) {
				logger.warn("-----当前用户不在钉钉映射表中------");
				return new JSONObject();
			}
			String agentId = DingUtil.getAgentIdByCorpId(null);
			if (StringUtil.isNull(agentId)) {
				logger.warn("-----agentId为空------");
				agentId = DingConfig.newInstance().getDingAgentid();
			}

			JSONObject param = new JSONObject();
			param.put("biz_type", "attendance.supply");
			param.put("userid", userid);
			param.put("action_type", "getSupplyDates");
			param.put("agentid", agentId);
			JSONArray formDataList = new JSONArray();
			JSONObject data = new JSONObject();
			data.put("label", "attendanceDate");
			data.put("extend_value", "");
			data.put("value", date); // 2020-11-23 12:00
			formDataList.add(data);
			param.put("form_data_list", formDataList);
			return DingUtils.dingApiService.getBizsuite(param);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	// 判断能否补卡
	public static JSONObject canSupply(String ekpUserId, String date,
			String extendValue) {
		try {
			String userid = null;
			if (StringUtil.isNotNull(ekpUserId)) {
				userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(ekpUserId);
			} else {
				userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(
										UserUtil.getUser().getFdId());
			}
			if (StringUtil.isNull(userid)) {
				logger.warn("-----当前用户不在钉钉映射表中------");
				return new JSONObject();
			}
			String agentId = DingConfig.newInstance().getDingAgentid();

			JSONObject param = new JSONObject();
			param.put("biz_type", "attendance.supply");
			param.put("userid", userid);
			param.put("action_type", "canSupply");
			param.put("agentid", agentId);
			JSONArray formDataList = new JSONArray();
			JSONObject data = new JSONObject();
			data.put("label", "userCheckTime");
			data.put("extend_value", extendValue);
			data.put("value", date); // 2020-11-23 12:00
			formDataList.add(data);
			param.put("form_data_list", formDataList);
			logger.warn("request:" + param);
			return DingUtils.dingApiService.getBizsuite(param);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	// 判断能否换班
	public static JSONObject canRelieveCheck(String applicantStaff_ekpid,
			String reliefStaff_ekpid, String relieveDatetime,
			String backDatetime) {
		try {
			JSONObject param = new JSONObject();
			String applicantStaff_userid = null;
			if (StringUtil.isNotNull(applicantStaff_ekpid)) {
				applicantStaff_userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(applicantStaff_ekpid);
			}
			if (StringUtil.isNull(applicantStaff_userid)) {
				logger.warn("-----换班人不在钉钉映射表中------");
				param.put("errcode", -1);
				param.put("errmsg", "换班人不在钉钉映射表中,请检查对照表信息");
				param.put("canRelieve", false);
				return param;
			}

			String reliefStaff_userid = null;
			if (StringUtil.isNotNull(reliefStaff_ekpid)) {
				reliefStaff_userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(reliefStaff_ekpid);
			}
			if (StringUtil.isNull(reliefStaff_userid)) {
				logger.warn("-----替班人不在钉钉映射表中------");
				param.put("errcode", -1);
				param.put("errmsg", "替班人不在钉钉映射表中,请检查对照表信息");
				param.put("canRelieve", false);
				return param;
			}

			String agentId = DingConfig.newInstance().getDingAgentid();

			param.put("biz_type", "attendance.relieve");
			param.put("userid", applicantStaff_userid);
			param.put("action_type", "canRelieveCheck");
			param.put("agentid", agentId);

			JSONArray formDataList = new JSONArray();
			// 替班人信息
			JSONObject data = new JSONObject();
			data.put("label", "reliefStaffId");
			data.put("extend_value", "");
			data.put("value", reliefStaff_userid);
			formDataList.add(data);

			// 换班日期信息
			JSONObject relieveDatetimeObj = new JSONObject();
			relieveDatetimeObj.put("label", "relieveDatetime");
			relieveDatetimeObj.put("extend_value", "");
			relieveDatetimeObj.put("value", relieveDatetime);
			formDataList.add(relieveDatetimeObj);

			// 换班信息，选填
			if (StringUtil.isNotNull(backDatetime)) {
				JSONObject backDatetimeObj = new JSONObject();
				backDatetimeObj.put("label", "backDatetime");
				backDatetimeObj.put("extend_value", "");
				backDatetimeObj.put("value", backDatetime);
				formDataList.add(backDatetimeObj);
			}
			param.put("form_data_list", formDataList);
			logger.debug("request:" + param);
			return DingUtils.dingApiService.getBizsuite(param);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	// 获取换班信息
	public static JSONObject getRelieveInfo(String applicantStaff_ekpid,
			String reliefStaff_ekpid, String relieveDatetime,
			String backDatetime) {
		try {
			JSONObject param = new JSONObject();
			String applicantStaff_userid = null;
			if (StringUtil.isNotNull(applicantStaff_ekpid)) {
				applicantStaff_userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(applicantStaff_ekpid);
			}
			if (StringUtil.isNull(applicantStaff_userid)) {
				logger.warn("-----换班人不在钉钉映射表中------");
				param.put("errcode", -1);
				param.put("errmsg", "换班人不在钉钉映射表中,请检查对照表信息");
				return param;
			}

			String reliefStaff_userid = null;
			if (StringUtil.isNotNull(reliefStaff_ekpid)) {
				reliefStaff_userid = ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService"))
								.getDingUserIdByEkpUserId(reliefStaff_ekpid);
			}
			if (StringUtil.isNull(reliefStaff_userid)) {
				logger.warn("-----替班人不在钉钉映射表中------");
				param.put("errcode", -1);
				param.put("errmsg", "替班人不在钉钉映射表中,请检查对照表信息");
				return param;
			}

			ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");

			SysOrgPerson applicantPer = (SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(applicantStaff_ekpid);

			SysOrgPerson reliefPer = (SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(reliefStaff_ekpid);

			String agentId = DingConfig.newInstance().getDingAgentid();

			param.put("biz_type", "attendance.relieve");
			param.put("userid", applicantStaff_userid);
			param.put("action_type", "getRelieveInfo");
			param.put("agentid", agentId);

			JSONArray formDataList = new JSONArray();

			// 换班人信息
			JSONObject applicantStaffInfo = new JSONObject();
			applicantStaffInfo.put("label", "applicantStaff");
			applicantStaffInfo.put("extend_value", "");
			JSONObject applicantStaff = new JSONObject();
			applicantStaff.put("emplId", applicantStaff_userid);
			applicantStaff.put("name", applicantPer.getFdName());
			applicantStaffInfo.put("value", applicantStaff.toString());
			formDataList.add(applicantStaffInfo);

			// 替班人信息
			JSONObject reliefStaffInfo = new JSONObject();
			reliefStaffInfo.put("label", "reliefStaff");
			reliefStaffInfo.put("extend_value", "");
			JSONObject reliefStaff = new JSONObject();
			reliefStaff.put("emplId", reliefStaff_userid);
			reliefStaff.put("name", reliefPer.getFdName());
			reliefStaffInfo.put("value", reliefStaff.toString());
			formDataList.add(reliefStaffInfo);

			// 换班日期信息
			JSONObject relieveDatetimeObj = new JSONObject();
			relieveDatetimeObj.put("label", "relieveDatetime");
			relieveDatetimeObj.put("extend_value", "");
			relieveDatetimeObj.put("value", relieveDatetime);
			formDataList.add(relieveDatetimeObj);

			// 换班信息，选填
			if (StringUtil.isNotNull(backDatetime)) {
				JSONObject backDatetimeObj = new JSONObject();
				backDatetimeObj.put("label", "backDatetime");
				backDatetimeObj.put("extend_value", "");
				backDatetimeObj.put("value", backDatetime);
				formDataList.add(backDatetimeObj);
			}
			param.put("form_data_list", formDataList);
			logger.debug("request:" + param);
			return DingUtils.dingApiService.getBizsuite(param);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/*
	 * 获取加班时长
	 */
	public static JSONObject getOvertimeDuration(String ekpUserIds,
			String startTime, String finishTime) {
		try {
			JSONObject param = new JSONObject();

			String overTime_userids = null;
			if (StringUtil.isNull(ekpUserIds)) {
				logger.warn("加班人不能为空");
			} else {
				String[] ids = ekpUserIds.split(";");
				IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService");
				String errorIds = null;
				for (int i = 0; i < ids.length; i++) {
					String id = omsRelationService
							.getDingUserIdByEkpUserId(ids[i]);
					if (StringUtil.isNull(id)) {
						errorIds = StringUtil.isNull(errorIds) ? id
								: (errorIds + ";" + id);
					} else {
						overTime_userids = StringUtil.isNull(overTime_userids)
								? id
								: (overTime_userids + "," + id);
					}
				}
				if (StringUtil.isNotNull(errorIds)) {
					param.put("errcode", -1);
					param.put("errmsg", "加班人不在钉钉人员映射表中，请先维护映射表");
					param.put("errIds", errorIds);
					return param;
				}

			}

			String agentId = DingConfig.newInstance().getDingAgentid();

			param.put("biz_type", "attendance.overtime");
			param.put("userid", overTime_userids.split(",")[0]);
			param.put("action_type", "getOvertimeDuration");
			param.put("agentid", agentId);

			JSONArray formDataList = new JSONArray();
			// 加班人信息
			JSONObject data = new JSONObject();
			data.put("label", "overtimeStaffIds");
			data.put("extend_value", "");
			data.put("value", overTime_userids);
			formDataList.add(data);

			// 开始时间
			JSONObject startTimeObj = new JSONObject();
			startTimeObj.put("label", "startTime");
			startTimeObj.put("extend_value", "");
			startTimeObj.put("value", startTime);
			formDataList.add(startTimeObj);

			// 结束时间
			JSONObject finishTimeObj = new JSONObject();
			finishTimeObj.put("label", "finishTime");
			finishTimeObj.put("extend_value", "");
			finishTimeObj.put("value", finishTime);
			formDataList.add(finishTimeObj);

			param.put("form_data_list", formDataList);
			logger.debug("request:" + param);
			return DingUtils.dingApiService.getBizsuite(param);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
