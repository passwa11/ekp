package com.landray.kmss.third.ding.xform.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSimpleProperty;
import com.landray.kmss.sys.xform.interfaces.ISysFormTemplateModel;
import com.landray.kmss.sys.xform.service.DictLoadService;
import com.landray.kmss.sys.xform.service.SysFormDictTreeVarService;
import com.landray.kmss.third.ding.xform.service.spring.ThirdDingXFormTemplateService;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdDingXFormTemplateUtil {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingXFormTemplateUtil.class);

	public static IBaseService kmReviewTemplateService = null;

	public static IBaseService getKmReviewTemplateService() {
		if (kmReviewTemplateService == null) {
			kmReviewTemplateService = (IBaseService) SpringBeanUtil
					.getBean("kmReviewTemplateService");
		}
		return kmReviewTemplateService;
	}

	public static final String getXFormTemplateType(String templateId) {
		try {
			return getXFormTemplateType(
					getKmReviewTemplateService().findByPrimaryKey(templateId));
		} catch (Exception e) {
			return null;
		}
	}

	public static final String getXFormTemplateTypeByMainId(String mainId) {
		try {
			IBaseService obj = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
			return ThirdDingXFormTemplateUtil
					.getXFormTemplateType(obj.findByPrimaryKey(mainId));
		} catch (Exception e) {
			return null;
		}
	}

	public static final String getXFormTemplateType(IBaseModel baseModel)
			throws Exception {
		if (baseModel == null) {
			logger.info("baseModel 为空 ,无法获取钉钉套件类型");
			return null;
		}
		DictLoadService loadService = (DictLoadService) SpringBeanUtil
				.getBean("sysFormDictLoadService");
		SysDictModel sysDictModel = null;
		if (baseModel instanceof ISysFormTemplateModel) {
			SysFormDictTreeVarService sysFormDictTreeVarService = (SysFormDictTreeVarService) SpringBeanUtil
					.getBean("sysFormDictVarTree");
			sysDictModel = sysFormDictTreeVarService
					.loadDictByTemplateId(baseModel.getFdId());
		} else {

			sysDictModel = loadService.loadDict(baseModel);
		}
		if (sysDictModel == null) {
			logger.error("数据字典为空,无法获取钉钉套件类型 modelClassName : "
					+ ModelUtil.getModelClassName(baseModel));
		}
		List<SysDictCommonProperty> propertyList = sysDictModel
				.getPropertyList();
		for (SysDictCommonProperty commonProperty : propertyList) {
			if (commonProperty instanceof SysDictExtendSimpleProperty) {
				SysDictExtendSimpleProperty simpleProperty = (SysDictExtendSimpleProperty) commonProperty;
				String businessType = simpleProperty.getBusinessType();
				String name = simpleProperty.getName();
				if ("mask".equalsIgnoreCase(businessType)) {
					return name;
				}
			}
		}
		return "";
	}

	@SuppressWarnings("rawtypes")
	public static final Map getXFormTemplateConfig(IBaseModel baseModel)
			throws Exception {
		if (baseModel == null) {
			logger.info("baseModel 为空 ,无法获取钉钉套件类型");
			return null;
		}
		DictLoadService loadService = (DictLoadService) SpringBeanUtil
				.getBean("sysFormDictLoadService");
		SysDictModel sysDictModel = null;
		if (baseModel instanceof ISysFormTemplateModel) {
			SysFormDictTreeVarService sysFormDictTreeVarService = (SysFormDictTreeVarService) SpringBeanUtil
					.getBean("sysFormDictVarTree");
			sysDictModel = sysFormDictTreeVarService
					.loadDictByTemplateId(baseModel.getFdId());
		} else {

			sysDictModel = loadService.loadDict(baseModel);
		}
		if (sysDictModel == null) {
			logger.error("数据字典为空,无法获取钉钉套件类型 modelClassName : "
					+ ModelUtil.getModelClassName(baseModel));
		}
		ThirdDingXFormTemplateService templateService = (ThirdDingXFormTemplateService) SpringBeanUtil
				.getBean("thirdDingXFormTemplateService");
		List<SysDictCommonProperty> propertyList = sysDictModel
				.getPropertyList();
		for (SysDictCommonProperty commonProperty : propertyList) {
			if (commonProperty instanceof SysDictExtendSimpleProperty) {
				SysDictExtendSimpleProperty simpleProperty = (SysDictExtendSimpleProperty) commonProperty;
				String businessType = simpleProperty.getBusinessType();
				String name = simpleProperty.getName();
				if ("mask".equalsIgnoreCase(businessType)) {
					return templateService.getTemplateConfig(name);
				}
			}
		}
		return null;
	}

	/**
	 * 是否显示钉钉套件
	 * 
	 * @return
	 */

	public static String isShowDingSuit() {
		ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil
				.getBean("sysAppConfigService");
		Map orgMap;
		try {
			orgMap = sysAppConfigService
					.findByKey(
							"com.landray.kmss.third.ding.model.DingConfig");
			if (orgMap != null) {
				String dingEnabled = orgMap.get("dingEnabled") + "";
				if ("true".equals(dingEnabled)) {
					String attendanceEnabled = orgMap.get("attendanceEnabled")
							+ "";
					String dingSuitEnabled = orgMap.get("dingSuitEnabled") + "";
					if ("true".equalsIgnoreCase(attendanceEnabled)
							|| "true".equalsIgnoreCase(dingSuitEnabled)) {
						return "true";
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取钉钉开关配置dingEnabled异常", e);
		}
		return "false";
	}

	/**
	 * 获取套件明细字段的值
	 *
	 * @param kmReviewMainObject
	 * @param key
	 * @return
	 */
	public static String getSuiteValue(IBaseModel kmReviewMainObject,
									   String key) {
		try {
			String type = getXFormTemplateType(kmReviewMainObject);
			logger.warn("套件type:" + type);
			Map map = DingUtil.getExtendDataModelInfo(kmReviewMainObject);
			logger.warn("map:" + map);
			return getValueByKey(map, key, type);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		return null;
	}

	private static String getValueByKey(Map map, String key, String type) {
		if (StringUtil.isNotNull(key) && key.indexOf("$suiteTable$_") > -1) {
			key = key.replace("$suiteTable$_", ""); // 去掉key的前缀
			if ("batchLeave".equals(type)) {
				if ("leaveRemark".equals(key)) {
					return (String) map.get("fd_leave_remark");
				} else if ("fdSumDuration".equals(key)) {
					return (String) map.get("fd_sum_duration");
				} else if ("leaveTime".equals(key)) {
					List batchLeaveInfoList = (ArrayList) map
							.get("fd_batch_leave_table");
					if (batchLeaveInfoList == null
							|| batchLeaveInfoList.isEmpty()) {
						logger.warn("------请假明细为空--------");
						return null;
					}
					String leaveTime = "";
					JSONObject cur_param;
					for (int i = 0; i < batchLeaveInfoList.size(); i++) {
						cur_param = JSONObject
								.fromObject(batchLeaveInfoList.get(i));
						// 请假单位
						String unit = cur_param.getString("fd_item_unit");

						// 开始时间
						String from_time = null;
						Long fromTime = cur_param
								.getJSONObject("fd_leave_start_time")
								.getLong("time");
						Date from_date = new Date(fromTime);
						logger.debug("from_date:" + from_date);
						if ("day".equalsIgnoreCase(unit)) {
							from_time = DateUtil.convertDateToString(from_date,
									"yyyy-MM-dd");
						} else if ("halfDay".equalsIgnoreCase(unit)) {
							String fromHalfDay = cur_param
									.getString("fd_start_time_one");
							if ("AM".equalsIgnoreCase(fromHalfDay)) {
								fromHalfDay = "上午";
							} else if ("PM".equalsIgnoreCase(fromHalfDay)) {
								fromHalfDay = "下午";
							}
							from_time = DateUtil.convertDateToString(from_date,
									"yyyy-MM-dd") + " "
									+ fromHalfDay;
						} else if ("hour".equalsIgnoreCase(unit)) {
							from_time = DateUtil.convertDateToString(from_date,
									"yyyy-MM-dd HH:mm");
						}
						// 结束时间
						String to_time = null;
						Long toDate = cur_param
								.getJSONObject("fd_leave_end_time")
								.getLong("time");
						Date to_date = new Date(toDate);
						logger.debug("to_date:" + to_date);
						if ("day".equalsIgnoreCase(unit)) {
							to_time = DateUtil.convertDateToString(to_date,
									"yyyy-MM-dd");
						} else if ("halfDay".equalsIgnoreCase(unit)) {
							String toHalfDay = cur_param
									.getString("fd_end_time_one");
							if ("AM".equalsIgnoreCase(toHalfDay)) {
								toHalfDay = "上午";
							} else if ("PM".equalsIgnoreCase(toHalfDay)) {
								toHalfDay = "下午";
							}
							to_time = DateUtil.convertDateToString(to_date,
									"yyyy-MM-dd") + " " + toHalfDay;
						} else if ("hour".equalsIgnoreCase(unit)) {
							to_time = DateUtil.convertDateToString(to_date,
									"yyyy-MM-dd HH:mm");
						}
						leaveTime += from_time + " - " + to_time + ";";
					}
					if (leaveTime.endsWith(";")) {
                        leaveTime = leaveTime.substring(0,
                                leaveTime.length() - 1);
                    }
					return leaveTime;
				} else if ("leaveType".equals(key)) {
					List batchLeaveInfoList = (ArrayList) map
							.get("fd_batch_leave_table");
					if (batchLeaveInfoList == null
							|| batchLeaveInfoList.isEmpty()) {
						logger.warn("------请假明细为空--------");
						return null;
					}
					String leaveType = "";
					JSONObject cur_param;
					for (int i = 0; i < batchLeaveInfoList.size(); i++) {
						cur_param = JSONObject
								.fromObject(batchLeaveInfoList.get(i));
						String name = cur_param.getString("fd_type_name");
						if (StringUtil.isNotNull(name)) {
							// 去掉重复的假期
							if (leaveType.contains("name")) {
								continue;
							}
							leaveType += name + ";";
						}
					}
					if (leaveType.endsWith(";")) {
                        leaveType = leaveType.substring(0,
                                leaveType.length() - 1);
                    }
					return leaveType;
				}

			} else if ("batchCancel".equals(type)) {
				// 销假
				if ("fd_cancel_user".equals(key)) {
					return (String) ((Map) map.get("fd_cancel_user"))
							.get("name");
				} else if ("fd_form_name".equals(key)
						|| "fd_cancel_remark".equals(key)
						|| "fd_cancel_sum_time".equals(key)) {
					return (String) map.get(key);
				}
			} else if ("batchWorkOverTime".equals(type)) {
				List batchInfoList = (ArrayList) map.get("fd_batch_work_table");
				if (batchInfoList == null || batchInfoList.isEmpty()) {
					logger.error("------批量加班明细为空--------");
					return "";
				}
				String result = "";
				JSONObject cur_param;
				// 加班
				if ("users".equals(key)) {
					for (int i = 0; i < batchInfoList.size(); i++) {
						cur_param = JSONObject.fromObject(batchInfoList.get(i));
						// 加班人（可多人）
						String applicant_name = cur_param
								.getJSONObject("fd_work_user")
								.getString("name");
						logger.debug("加班人：name:" + applicant_name);
						result += applicant_name + ";";
					}
				} else if ("workOverTime".equals(key)) {
					// 加班时间
					for (int i = 0; i < batchInfoList.size(); i++) {
						cur_param = JSONObject.fromObject(batchInfoList.get(i));
						// 开始时间
						Long start_date = cur_param
								.getJSONObject("fd_work_start_time")
								.getLong("time");
						String startTime = DateUtil.convertDateToString(
								new Date(start_date),
								"yyyy-MM-dd HH:mm");
						logger.debug("开始时间：" + startTime);

						// 结束时间
						Long fd_work_end_time = cur_param
								.getJSONObject("fd_work_end_time")
								.getLong("time");
						String endTime = DateUtil.convertDateToString(
								new Date(fd_work_end_time),
								"HH:mm");
						logger.debug("结束时间：" + endTime);
						result += startTime + " - " + endTime + ";";
					}
				} else if ("compensation".equals(key)) {
					// 加班补偿
					for (int i = 0; i < batchInfoList.size(); i++) {
						cur_param = JSONObject.fromObject(batchInfoList.get(i));
						// 暂时不传
					}
					result = "转调休/加班费";
				} else if ("duration".equals(key)) {
					// 加班时长
					for (int i = 0; i < batchInfoList.size(); i++) {
						cur_param = JSONObject.fromObject(batchInfoList.get(i));
						String fd_duration = cur_param.getString("fd_duration");
						logger.debug("时长：" + fd_duration);
						result += fd_duration + "+";
					}
					if (result.endsWith("+")) {
                        result = result.substring(0, result.length() - 1)
                                + " 小时";
                    }
				}
				if (result.endsWith(";")) {
                    result = result.substring(0, result.length() - 1);
                }
				return result;

			} else if ("batchChange".equals(type)) {
				// 换班
				List batchInfoList = (ArrayList) map.get("fd_change_table");
				if (batchInfoList == null || batchInfoList.isEmpty()) {
					logger.error("------批量加班明细为空--------");
					return "";
				}
				String result = "";
				JSONObject cur_param;
				if ("fdChangeApplyUser".equals(key)) { // 换班人
					for (int i = 0; i < batchInfoList.size(); i++) {
						cur_param = JSONObject.fromObject(batchInfoList.get(i));
						String applicant_name = cur_param
								.getJSONObject("fd_change_apply_user")
								.getString("name");
						result += applicant_name + ";";
					}
				} else if ("fdChangeSwapUser".equals(key)) { // 替班人
					for (int i = 0; i < batchInfoList.size(); i++) {
						cur_param = JSONObject.fromObject(batchInfoList.get(i));
						String applicant_name = cur_param
								.getJSONObject("fd_change_swap_user")
								.getString("name");
						result += applicant_name + ";";
					}
				} else if ("fdChangeDate".equals(key)) { // 换班日期
					for (int i = 0; i < batchInfoList.size(); i++) {
						cur_param = JSONObject.fromObject(batchInfoList.get(i));
						// 换班日期
						Long fd_change_date = cur_param
								.getJSONObject("fd_change_date")
								.getLong("time");
						Date chaneDate = new Date(fd_change_date);
						String chaneDateTime = DateUtil.convertDateToString(
								chaneDate,
								"yyyy-MM-dd");
						logger.debug("换班日期：" + chaneDateTime);
						result += chaneDateTime + ";";
					}
				} else if ("fdReturnDate".equals(key)) { // 还班日期
					for (int i = 0; i < batchInfoList.size(); i++) {
						cur_param = JSONObject.fromObject(batchInfoList.get(i));
						// 还班日期（选填）
						JSONObject returnObj = cur_param
								.getJSONObject("fd_return_date");
						if (returnObj != null && !returnObj.isEmpty()) {
							Long fd_return_date = cur_param
									.getJSONObject("fd_return_date")
									.getLong("time");
							Date returnDate = new Date(fd_return_date);
							String returnDateTime = DateUtil
									.convertDateToString(returnDate,
											"yyyy-MM-dd");
							logger.debug("还班日期：" + returnDateTime);
							result += returnDateTime + ";";
						}
					}
				} else if ("fdChangeRemark".equals(key)) {
					// 换班理由
					return (String) map.get("fd_change_remark");
				}
				if (result.endsWith(";")) {
                    result = result.substring(0, result.length() - 1);
                }
				return result;

			} else if ("batchReplacement".equals(type)) {
				// 补卡
				if ("fdUser".equals(key)) {
					return (String) ((Map) map.get("fd_user"))
							.get("name");
				} else if ("fdReplacementCount".equals(key)) {
					return (String) map.get("fd_replacement_count");
				} else if ("fdReplacementTime".equals(key)) {

					List batchReplacementList = (ArrayList) map
							.get("fd_batch_replacement_table");
					if (batchReplacementList == null
							|| batchReplacementList.isEmpty()) {
						logger.warn("------补卡明细为空--------");
						return null;
					}
					String rTime = "";
					JSONObject cur_param;
					for (int i = 0; i < batchReplacementList.size(); i++) {
						cur_param = JSONObject
								.fromObject(batchReplacementList.get(i));
						// 补卡时间
						Long time = cur_param
								.getJSONObject("fd_replacement_time")
								.getLong("time");
						Date replacementTime = new Date(time);
						String userCheckTime = DateUtil.convertDateToString(
								replacementTime,
								"yyyy-MM-dd HH:mm");
						logger.warn("userCheckTime:" + userCheckTime);

						rTime += userCheckTime + ";";
					}
					if (rTime.endsWith(";")) {
                        rTime = rTime.substring(0, rTime.length() - 1);
                    }
					return rTime;
				} else if ("fdReplacementReason".equals(key)) {

					List batchReplacementList = (ArrayList) map
							.get("fd_batch_replacement_table");
					if (batchReplacementList == null
							|| batchReplacementList.isEmpty()) {
						logger.warn("------补卡明细为空--------");
						return null;
					}
					String rReason = "";
					JSONObject cur_param;
					for (int i = 0; i < batchReplacementList.size(); i++) {
						cur_param = JSONObject
								.fromObject(batchReplacementList.get(i));
						// 补卡原因
						String fdReason = cur_param
								.getString("fd_replacement_reason");
						logger.warn("fdReason:" + fdReason);
						// 去掉相同的理由
						if (rReason.contains("fdReason")) {
                            continue;
                        }
						rReason += fdReason + ";";
					}
					if (rReason.endsWith(";")) {
                        rReason = rReason.substring(0, rReason.length() - 1);
                    }
					return rReason;
				}

			}
		}
		return null;
	}

}
