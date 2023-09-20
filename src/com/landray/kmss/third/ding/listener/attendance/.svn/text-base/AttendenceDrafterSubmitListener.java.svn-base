package com.landray.kmss.third.ding.listener.attendance;

import com.dingtalk.api.response.OapiProcessWorkrecordCreateResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.third.ding.model.*;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingDinstanceXformService;
import com.landray.kmss.third.ding.service.IThirdDingDtemplateXformService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.third.ding.xform.util.ThirdDingXFormTemplateUtil;
import com.landray.kmss.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;

/***
 * 套件表单提交时监听，用于创建套件实例
 *
 */
public class AttendenceDrafterSubmitListener implements IEventListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AttendenceDrafterSubmitListener.class);

	private IOmsRelationService omsRelationService;

	public void setOmsRelationService(IOmsRelationService omsRelationService) {
		this.omsRelationService = omsRelationService;
	}

	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	private IThirdDingDinstanceXformService thirdDingDinstanceXformService;

	public IThirdDingDinstanceXformService getThirdDingDinstanceXformService() {
		if (thirdDingDinstanceXformService == null) {
			thirdDingDinstanceXformService = (IThirdDingDinstanceXformService) SpringBeanUtil.getBean("thirdDingDinstanceXformService");
		}
		return thirdDingDinstanceXformService;
	}

	private IThirdDingDtemplateXformService thirdDingDtemplateXformService;

	public IThirdDingDtemplateXformService getThirdDingDtemplateXformService() {
		if (thirdDingDtemplateXformService == null) {
			thirdDingDtemplateXformService = (IThirdDingDtemplateXformService) SpringBeanUtil.getBean("thirdDingDtemplateXformService");
		}
		return thirdDingDtemplateXformService;
	}

	protected ISysOrgPostService sysOrgPostService;

	protected ISysOrgPostService getSysOrgPostServiceService() {
		if (sysOrgPostService == null) {
			sysOrgPostService = (ISysOrgPostService) SpringBeanUtil.getBean("sysOrgPostService");
		}
		return sysOrgPostService;
	}


	/**
	 * 处理起草人提交事件
	 */
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		try {
			logger.info("处理起草人提交事件,parameter:" + parameter);
			IBaseModel baseModel = execution.getMainModel();
			String modelName = ModelUtil.getModelClassName(baseModel);
			logger.debug("modelName:" + modelName);
			if (!"com.landray.kmss.km.review.model.KmReviewMain".equals(modelName)) {
				return;
			}
			//获取套件类型
			String type = ThirdDingXFormTemplateUtil.getXFormTemplateType(baseModel);
			logger.debug("type:" + type);
			DingConfig config = DingConfig.newInstance();
			
			if ("true".equals(config.getDingEnabled())
					&& ("true".equals(config.getAttendanceEnabled())
							|| "true".equals(config.getDingSuitEnabled())
									&& StringUtil.isNotNull(type))) {
				//审批高级版的非套件模板类型
				if (StringUtil.isNull(type)) {
					type = "common";
				}
				// 创建实例
				createXformDisdance(baseModel, type);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 创建套件实例
	 * @param baseModel
	 * @param type 套件类型
	 */
	public void createXformDisdance(IBaseModel baseModel, String type) {
		try {
			// 获取流程主题
			String docSubject = (String) DingUtil.getModelPropertyString(
					baseModel,"docSubject", "", null);
			logger.info("流程标题=>" + docSubject);
			String reviewMainId = baseModel.getFdId();
			logger.info("流程主文档fdId=>" + reviewMainId);
			// 获取主文档创建者信息
			Object kmReviewMainObject = ((IBaseService) SpringBeanUtil.getBean("kmReviewMainService")).findByPrimaryKey(reviewMainId);
			Method method = kmReviewMainObject.getClass().getMethod("getDocCreator");
			SysOrgPerson docCreator = (SysOrgPerson) method.invoke(kmReviewMainObject);
			String ekpUserId = docCreator.getFdId();
			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			logger.info("map:" + map);

			// 转换成钉钉的userid
			String userid = omsRelationService
					.getDingUserIdByEkpUserId(ekpUserId);
			logger.debug("创建者：" + docCreator.getFdName() + "  fdId:"
					+ ekpUserId + "  dindId:" + userid);


			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"fdEkpInstanceId =:fdEkpInstanceId and fdStatus=:fdStatus");
			hqlInfo.setParameter("fdEkpInstanceId", reviewMainId);
			hqlInfo.setParameter("fdStatus", "20");
			ThirdDingDinstanceXform instance = (ThirdDingDinstanceXform) getThirdDingDinstanceXformService()
					.findFirstOne(hqlInfo);
			if (instance != null) {
				logger.debug(docSubject + "------已存在实例模板，不再创建实例---------");
			} else {
				logger.debug(docSubject + "--------不存在可使用实例-----------");
				// 获取主文档的模板Id
				IBaseModel tempModel = (IBaseModel) kmReviewMainObject.getClass()
						.getMethod("getFdTemplate")
						.invoke(kmReviewMainObject);
				logger.debug("-----模板ID-------" + tempModel.getFdId());

				// 文档创建时间
				Date date = (Date) kmReviewMainObject.getClass().getMethod("getDocCreateTime")
						.invoke(kmReviewMainObject);
				String createTime = "";
				if (date != null) {
					createTime = DateUtil.convertDateToString(date,
							"yyyy-MM-dd HH:mm");
				}
				logger.debug("--------createTime-------" + createTime);
				hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"fdTemplateId=:fdTemplateId and fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdTemplateId", tempModel.getFdId());
				hqlInfo.setParameter("fdIsAvailable", true);
				//获取可用的套件模板
				ThirdDingDtemplateXform xform= (ThirdDingDtemplateXform)getThirdDingDtemplateXformService().findFirstOne(hqlInfo);
				if (xform != null) {
					logger.debug("钉钉模板名称：" + xform.getFdName());
					JSONObject param = new JSONObject();
					param.put("ekpUserId", ekpUserId);
					param.put("creater", docCreator.getFdName());
					param.put("createTime", createTime);
					String url ="/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+reviewMainId+DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
					//获取域名
					String dingDomain = DingUtil.getDingDomin();
					// 独立窗口打开
					url = dingDomain + "/third/ding/pc/web_wnd.jsp?url="
							+ URLEncoder.encode(url,"UTF-8") + "&ddtab=true";

					logger.debug("url:{},长度：{}", url,url.length());
					param.put("url", url);
					if ("attendance".equals(type)) {
						// 请假
						String extend_value = map.get("extend_value")
								.toString();
						logger.warn("extend_value:" + extend_value);
						if (StringUtil.isNull(extend_value)) {
							return;
						}

						// 请假单位
						String unit = "day";
						if (map.containsKey("unit")) {
							unit = (String) map.get("unit");
						} else {
							JSONObject extendObj = JSONObject
									.fromObject(extend_value);
							unit = extendObj.getString("unit");
						}

						// 开始时间
						String from_time = null;
						Date from_date = (Date) map.get("from_time");
						logger.debug("from_date:" + from_date);
						if ("day".equalsIgnoreCase(unit)) {
							from_time = DateUtil.convertDateToString(from_date,
									"yyyy-MM-dd");
						} else if ("halfDay".equalsIgnoreCase(unit)) {
							String fromHalfDay = (String) map
									.get("from_half_day");
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
						Date to_date = (Date) map.get("to_time");
						logger.debug("from_date:" + from_date);
						if ("day".equalsIgnoreCase(unit)) {
							to_time = DateUtil.convertDateToString(to_date,
									"yyyy-MM-dd");
						} else if ("halfDay".equalsIgnoreCase(unit)) {
							String toHalfDay = (String) map.get("to_half_day");
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
						JSONObject typeJson = new JSONObject();
						typeJson.put("leaveCode",
								(String) map.get("leave_code"));
						typeJson.put("unit", unit);
						String leave_name = (String) map.get("leave_txt");
						param.put("请假类型", leave_name);
						param.put("type_extend_value", typeJson.toString());
						param.put("开始时间", from_time);
						param.put("结束时间", to_time);
						param.put("时长", (String) map.get("duration"));
						param.put("请假原因", (String) map.get("reason"));
						param.put("extend_value", extend_value);
						param.put("type", type);

					} else if ("batchLeave".equals(type)) {
						// 批量请假
						param.put("type", type);
						param.put("userid", userid);
						dealWithBatchLeave(param, map, xform, docSubject,
								reviewMainId, docCreator, baseModel);
						return;

					} else if ("batchReplacement".equals(type)) {
						// 批量补卡
						param.put("type", type);
						param.put("userid", userid);
						dealWithBatchReplacement(param, map, xform, docSubject,
								reviewMainId, docCreator, baseModel);
						return;
					} else if ("workOverTime".equals(type)) {
						// 加班
						logger.warn("map:" + map);
						Map userMap = (Map) map.get("userid");
						String overTime_userFdId = (String) userMap.get("id");
						String names = (String) userMap.get("name");
						String[] overTime_users = overTime_userFdId.split(";");

						String overtime_duration = (String) map.get("duration");
						logger.debug("overtime_duration：" + overtime_duration);

						// 开始时间
						Date from_date = (Date) map.get("from_time");
						String from_time = DateUtil.convertDateToString(
								from_date,
								"yyyy-MM-dd HH:mm");
						logger.debug("from_date:" + from_date + "  from_time:"
								+ from_time);

						// 结束时间
						Date to_date = (Date) map.get("to_time");
						logger.debug("from_date:" + from_date);
						String to_time = DateUtil.convertDateToString(to_date,
								"yyyy-MM-dd HH:mm");
						logger.debug(
								"to_date:" + to_date + "  to_time:" + to_time);

						param.put("加班人", names);
						param.put("开始时间", from_date);
						param.put("结束时间", to_time);
					} else if ("goOut".equals(type)) {
						// 外出
						String overtime_duration = (String) map.get("duration");
						logger.debug("overtime_duration：" + overtime_duration);
						if (StringUtil.isNotNull(overtime_duration)
								&& overtime_duration.contains("小时")) {
							overtime_duration = overtime_duration.replace("小时",
									"");
							logger.debug(
									"overtime_duration去掉小时单位："
											+ overtime_duration);
						}

						// 开始时间
						Date from_date = (Date) map.get("from_time");
						String from_time = DateUtil.convertDateToString(
								from_date,
								"yyyy-MM-dd HH:mm");
						logger.debug("from_date:" + from_date + "  from_time:"
								+ from_time);

						// 结束时间
						Date to_date = (Date) map.get("to_time");
						logger.debug("from_date:" + from_date);
						String to_time = DateUtil.convertDateToString(to_date,
								"yyyy-MM-dd HH:mm");
						logger.debug(
								"to_date:" + to_date + "  to_time:" + to_time);

						param.put("开始时间", from_date);
						param.put("结束时间", to_time);
						param.put("时长", to_time);
					} else if ("businessTrip".equals(type)) {
						// 出差套件
						// 获取行程信息
						ArrayList trips = (ArrayList) map.get("fd_trips_div");
						JSONObject trip = JSONObject.fromObject(trips.get(0));
						param.put("出差事由", (String) map.get("reason"));
						String traffic_tool = (String) trip.get("traffic_tool");
						if ("aircraft,".equals(traffic_tool)) {
							traffic_tool = "飞机";
						} else if ("train,".equals(traffic_tool)) {
							traffic_tool = "火车";
						} else if ("car,".equals(traffic_tool)) {
							traffic_tool = "汽车";
						} else if ("other".equals(traffic_tool)) {
							traffic_tool = "其他";
						}
						param.put("交通工具", traffic_tool);
						String route_type = (String) trip.get("route_type");
						if ("oneWay".equals(route_type)) {
							route_type = "单程";
						} else if ("return".equals(route_type)) {
							route_type = "往返";
						}
						param.put("单程往返", route_type);
					} else if ("changeOff".equals(type)) {
						// 换班套件
						// 换班人
						Map proposer = (Map) map.get("proposer");
						String proposerId = (String) proposer.get("id");
						String userid_proposer = omsRelationService
								.getDingUserIdByEkpUserId(proposerId);
						logger.debug("换班人proposerId：" + proposerId
								+ "  userid_proposer:" + userid_proposer);

						SysOrgElement proposer_user = (SysOrgElement) getSysOrgElementService()
								.findByPrimaryKey(proposerId);

						// 替班人
						Map substitute = (Map) map.get("substitute");
						String substituteId = (String) substitute.get("id");
						String target_userid = omsRelationService
								.getDingUserIdByEkpUserId(substituteId);
						logger.debug("换班人substituteId：" + substituteId
								+ "  target_userid:" + target_userid);
						SysOrgElement substitute_user = (SysOrgElement) getSysOrgElementService()
								.findByPrimaryKey(substituteId);

						// 申请换班时间
						Date shift_date = (Date) map.get("shift_date");
						String switch_date = DateUtil
								.convertDateToString(shift_date, "yyyy-MM-dd");
						logger.debug(
								"shift_date:" + shift_date + "  switch_date:"
										+ switch_date);

						param.put("申请人", proposer_user.getFdName());
						param.put("替班人", substitute_user.getFdName());
						param.put("换班日期", switch_date);
					} else if ("replacement".equals(type)) {// 补卡套件

						// 补卡理由
						String reason = null;
						if (map.containsKey("fd_38c8cba60e6e70")) {
							reason = (String) map.get("fd_38c8cba60e6e70");
						} else if (map.containsKey("fd_reseaon")) {
							reason = (String) map.get("fd_reseaon");
						}

						// 补卡时间
						Date data_time = (Date) map.get("data_time");
						String user_check_time = DateUtil.convertDateToString(
								data_time,
								"yyyy-MM-dd HH:mm");
						logger.debug("user_check_time:" + user_check_time
								+ "  data_time:"
								+ data_time);

						// 要补哪一天的卡
						Date workDate = DateUtil.convertStringToDate(
								(String) map.get("work_date"),
								"yyyy-MM-dd HH:mm:ss");
						String work_date = DateUtil.convertDateToString(
								workDate,
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

						param.put("补卡时间", user_check_time);
						param.put("补卡理由", reason);
						param.put("补卡班次", punch_check_time);
					} else if ("destroyLeave".equals(type)) {// 销假套件
						logger.warn("---------------销假套件-------------");

						String ekpInstanceId = (String) map.get("level_form");
						String cancelLeave = (String) map.get("cancelLeave");
						String leaveText = "";
						if (StringUtil.isNull(cancelLeave)) {
							logger.warn("---------cancelLeave为空！！！------");
							return;
						}
						JSONArray cancelInfo = JSONArray
								.fromObject(cancelLeave);
						// .parseArray(cancelLeave);
						for (int i = 0; i < cancelInfo.size(); i++) {
							String id = cancelInfo.getJSONObject(i)
									.getString("fd_ekp_instance_id");
							if (ekpInstanceId.equals(id)) {
								leaveText = cancelInfo.getJSONObject(i)
										.getString("fd_name");
								break;
							}

						}
						param.put("请假单", leaveText);
						param.put("销假时长", (String) map.get("duration"));
						param.put("剩余请假时长", (String) map.get("haveLeaveTime"));

					} else if ("batchCancel".equals(type)) {
						// 批量销假，暂时是全部销假，全部销假的只需要一个审批实例就可以了，部分销假的话，下面就需要另外创建实例了
						// String leaveTarget = String
						// .valueOf(map.get("fd_form_name")) + "-" +
						// String.valueOf(map.get("fd_leave_form"));
						String leaveTarget = String
								.valueOf(map.get("fd_form_name"));
						param.put("请假单", leaveTarget);
						param.put("销假时长",
								String.valueOf(map.get("fd_cancel_sum_time")));
						param.put("剩余请假时长", String
								.valueOf(map.get("fd_cancel_surplus_time")));
						// 如果创建人userid为空，则以销假人userid去创建
						if (StringUtil.isNull(userid)) {
							Map userMap = (Map) map.get("fd_cancel_user");
							String cancel_userFdId = (String) userMap.get("id");
							String cancel_username = (String) userMap
									.get("name");
							userid = omsRelationService
									.getDingUserIdByEkpUserId(cancel_userFdId);
						}

					} else if ("batchChange".equals(type)) {

						// 批量换班
						param.put("type", type);
						dealWithBatchChange(param, map, xform, docSubject,
								reviewMainId, docCreator, baseModel);
						return;

					} else if ("batchWorkOverTime".equals(type)) {
						// 批量加班
						param.put("type", type);
						dealWithBatchOverTime(param, map, xform, docSubject,
								reviewMainId, docCreator, baseModel);
						return;

					}

					// 批量请假和批量补卡等发起人不一定是操作人，其他需要登录人本人作为实例的发起人
					if (StringUtil.isNull(userid)) {
						logger.warn("-----在钉钉对照表无法找到文档创建者对应的关系，不创建实例！-----");
						return;
					}
					OapiProcessWorkrecordCreateResponse response = DingNotifyUtil
							.createXformDistance(DingUtils.getDingApiService()
									.getAccessToken(),
									userid, xform, docSubject,
									xform.getFdDetail(), param);
					if (response!=null&&response.getErrcode() == 0) {

						String instanceId = response.getResult()
								.getProcessInstanceId();

						ThirdDingDinstanceXform distance = new ThirdDingDinstanceXform();
						distance.setFdName(docSubject);
						distance.setDocCreateTime(new Date());
						distance.setFdInstanceId(instanceId);
						distance.setFdDingUserId(userid);
						distance.setFdEkpInstanceId(reviewMainId);
						distance.setFdUrl(url);
						distance.setFdTemplate(xform);
						distance.setFdEkpUser(docCreator);
						distance.setFdStatus("20");

						List<ThirdDingIndanceXDetail> fdDetail = new ArrayList<ThirdDingIndanceXDetail>();
						ThirdDingIndanceXDetail detail = null;
						for (ThirdDingTemplateXDetail det : xform
								.getFdDetail()) {
							detail = new ThirdDingIndanceXDetail();
							detail.setFdName(det.getFdName());
							detail.setFdType("TextField");
							if (StringUtil.isNotNull(det.getFdName())
									&& param.containsKey(det.getFdName())) {
								detail.setFdValue(det.getFdName());
							} else if ("标题".equals(detail.getFdName())) {
								detail.setFdValue(docSubject);
							} else if ("创建者".equals(detail.getFdName())) {
								if (!param.containsKey("creater")) {
									detail.setFdValue("");
								} else {
									detail.setFdValue(
											param.getString("creater"));
								}
							} else if ("创建时间".equals(detail.getFdName())) {
								if (!param.containsKey("createTime")) {
									detail.setFdValue("");
								} else {
									detail.setFdValue(
											param.getString("createTime"));
								}
							} else {
								detail.setFdValue("");
							}
							fdDetail.add(detail);
						}
						distance.setFdDetail(fdDetail);

						//独立开启一个事务，避免流程的报错影响到实例的保存
						TransactionStatus status = null;
						long alltime = System.currentTimeMillis();
						try {
							status = TransactionUtils.beginNewTransaction();
							getThirdDingDinstanceXformService().add(distance);
							TransactionUtils.getTransactionManager().commit(status);
						} catch (Exception e) {
							if (status != null) {
								try {
									TransactionUtils.getTransactionManager().rollback(status);
								} catch (Exception ex) {
									logger.error("---事务回滚出错---", ex);
								}
							}
						}
						
					}else{
						logger.warn("创建实例失败：" + response == null ? "返回结果为null"
								: response.getBody());
						sengDingErrorNotify(baseModel, "创建实例失败,请及时处理");
					}
					
				} else {
                   logger.warn("主文档  "+docSubject+" 对应的模板没有同步到钉钉，请检查模板情况!templateId:"+tempModel.getFdId());
				}
				
			}

		} catch (Exception e) {
			logger.error("创建实例失败", e);
			sengDingErrorNotify(baseModel, "创建实例失败,请及时处理");
		}
	}

	/*
	 * 批量换班
	 */
	private void dealWithBatchChange(JSONObject param, Map map,
			ThirdDingDtemplateXform temp, String docSubject,
			String reviewMainId, SysOrgPerson docCreator,
			IBaseModel baseModel) throws Exception {

		logger.debug("换班Map：" + map);
		// 构建请假明细信息
		net.sf.json.JSONArray changeInfo = new net.sf.json.JSONArray();
		buildBatchChangeInfo(changeInfo, param, map);
		logger.debug(changeInfo.toString());
		for (int i = 0; i < changeInfo.size(); i++) {
			JSONObject _param = changeInfo.getJSONObject(i);
			String userid = _param.getString("applicant_userid");
			JSONObject response = DingNotifyUtil
					.createXformDistance_batch(DingUtils.getDingApiService()
							.getAccessToken(),
							userid, temp, docSubject,
							temp.getFdDetail(), _param);
			// 创建实例成功，保存实例到数据库
			saveInstanceLog2DB(response, docSubject, userid, reviewMainId,
					_param, temp, docCreator, baseModel);

		}

	}

	private void saveInstanceLog2DB(
			JSONObject response, String docSubject,
			String userid, String reviewMainId, JSONObject _param,
			ThirdDingDtemplateXform temp, SysOrgPerson docCreator,
			IBaseModel baseModel) throws Exception {
		if (response != null && response.getInt("errcode") == 0) {
			String instanceId = response.getJSONObject("result")
					.getString("process_instance_id");
			ThirdDingDinstanceXform distance = new ThirdDingDinstanceXform();
			distance.setFdName(docSubject);
			distance.setDocCreateTime(new Date());
			distance.setFdInstanceId(instanceId);
			distance.setFdDingUserId(userid);
			distance.setFdEkpInstanceId(reviewMainId);
			distance.setFdUrl(_param.getString("url"));
			distance.setFdTemplate(temp);
			distance.setFdEkpUser(docCreator);
			distance.setFdStatus("20");

			List<ThirdDingIndanceXDetail> fdDetail = new ArrayList<ThirdDingIndanceXDetail>();
			ThirdDingIndanceXDetail detail = null;
			for (ThirdDingTemplateXDetail det : temp
					.getFdDetail()) {
				detail = new ThirdDingIndanceXDetail();
				detail.setFdName(det.getFdName());
				detail.setFdType("TextField");
				if (StringUtil.isNotNull(det.getFdName())
						&& _param.containsKey(det.getFdName())) {
					detail.setFdValue(_param.getString(det.getFdName()));
				} else {
					detail.setFdValue("");
				}
				fdDetail.add(detail);
			}
			distance.setFdDetail(fdDetail);
			//独立开启一个事务，避免流程的报错影响到实例的保存
			TransactionStatus status = null;
			long alltime = System.currentTimeMillis();
			try {
				status = TransactionUtils.beginNewTransaction();
				getThirdDingDinstanceXformService().add(distance);
				TransactionUtils.getTransactionManager().commit(status);
			} catch (Exception e) {
				if (status != null) {
					try {
						TransactionUtils.getTransactionManager().rollback(status);
					} catch (Exception ex) {
						logger.error("---事务回滚出错---", ex);
					}
				}
			}
		} else {
			logger.warn("创建实例失败：" + response == null ? "返回结果为null"
					: response.toString());
			sengDingErrorNotify(baseModel, "创建实例失败,请及时处理");
		}
	}

	// 构建批量换班信息
	private void buildBatchChangeInfo(JSONArray changeInfo, JSONObject param,
			Map map) throws Exception {
		/*
		 * { "name":"申请人", "value":"星星", "extValue":
		 * "[{\"emplId\":\"0913510944836576\",\"name\":\"星星\"}]",
		 * "bizAlias":"applicant" } ,{ "name":"替班人", "value":"xiexie22",
		 * "extValue":
		 * "[{\"emplId\":\"010248466665388042176\",\"name\":\"xiexie22\"}]",
		 * "bizAlias":"relief" },{ "name":"换班日期", "value":"2020-12-28",
		 * "bizAlias":"relieveDate" },{ "name":"还班日期", "value":"2020-12-29",
		 * "bizAlias":"backDate" },{ "name":"换班说明",
		 * "value":"星星从\"休息\"换为\"正常班次\"", "bizAlias":"relieveInfo" },{
		 * "name":"还班说明", "value":"星星从\"正常班次\"换为\"休息\"", "bizAlias":"backInfo" }
		 */
		List batchInfoList = (ArrayList) map.get("fd_change_table");
		if (batchInfoList == null || batchInfoList.isEmpty()) {
			logger.error("------批量换班明细为空--------");
			return;
		}
		JSONObject temp_param;
		JSONObject cur_param;
		String paramString = param.toString();
		for (int i = 0; i < batchInfoList.size(); i++) {
			cur_param = JSONObject.fromObject(batchInfoList.get(i));
			temp_param = JSONObject.fromObject(paramString);

			// 申请人
			String applicant_ekpId = cur_param
					.getJSONObject("fd_change_apply_user").getString("id");
			String applicant_name = cur_param
					.getJSONObject("fd_change_apply_user").getString("name");
			String applicant_userid = omsRelationService
					.getDingUserIdByEkpUserId(applicant_ekpId);
			JSONObject applicat = new JSONObject();
			applicat.put("emplId", applicant_userid);
			applicat.put("name", applicant_name);
			JSONArray applicat_extValue = new JSONArray();
			applicat_extValue.add(applicat);

			// 替班人
			String relief_ekpId = cur_param.getJSONObject("fd_change_swap_user")
					.getString("id");
			String relief_name = cur_param.getJSONObject("fd_change_swap_user")
					.getString("name");
			String relief_userid = omsRelationService
					.getDingUserIdByEkpUserId(relief_ekpId);
			JSONObject relief = new JSONObject();
			relief.put("emplId", relief_userid);
			relief.put("name", relief_name);
			JSONArray relief_extValue = new JSONArray();
			relief_extValue.add(relief);

			// 换班日期
			Long fd_change_date = cur_param.getJSONObject("fd_change_date")
					.getLong("time");
			Date chaneDate = new Date(fd_change_date);
			String chaneDateTime = DateUtil.convertDateToString(chaneDate,
					"yyyy-MM-dd");
			logger.debug("换班日期：" + chaneDateTime);

			// 还班日期（选填）
			JSONObject returnObj = cur_param.getJSONObject("fd_return_date");
			if (returnObj != null && !returnObj.isEmpty()) {
				Long fd_return_date = cur_param.getJSONObject("fd_return_date")
						.getLong("time");
				Date returnDate = new Date(fd_return_date);
				String returnDateTime = DateUtil.convertDateToString(returnDate,
						"yyyy-MM-dd");
				logger.debug("还班日期：" + returnDateTime);
				temp_param.put("还班日期", returnDateTime);
			} else {
				logger.warn("还班日期为空");
			}

			// 换班说明
			String change_value = cur_param.getString("change_value");
			// change_value =
			// "{\"backInfo\":\"\",\"success\":\"true\",\"relieveInfo\":\"火旺小号从\\\"chw班次\\\"换为\\\"晚班考勤\\\",熊浩淋从\\\"晚班考勤\\\"换为\\\"chw班次\\\"\"}";
			logger.debug("换班说明：" + change_value);
			if (StringUtil.isNotNull(change_value)) {
				// "{\"backInfo\":\"火旺从\\\"chw班次\\\"换为\\\"晚班考勤\\\",熊浩淋从\\\"晚班考勤\\\"换为\\\"chw班次\\\"\",\"success\":\"true\",\"relieveInfo\":\"火旺从\\\"chw班次\\\"换为\\\"晚班考勤\\\",熊浩淋从\\\"晚班考勤\\\"换为\\\"chw班次\\\"\"}"
				JSONObject changeValue = JSONObject.fromObject(change_value);
				if (changeValue.containsKey("relieveInfo")) {
					temp_param.put("换班说明",
							changeValue.getString("relieveInfo"));
				} else {
					logger.warn("----------换班说明为空----------");
				}
				if (changeValue.containsKey("backInfo")) {
					temp_param.put("还班说明", changeValue.getString("backInfo"));
				} else {
					logger.warn("----------还班说明为空----------");
				}
			} else {
				logger.warn("----------换、还班说明为空----------");
			}
			temp_param.put("申请人", applicant_name);
			temp_param.put("applicat_extValue", applicat_extValue.toString());
			temp_param.put("applicant_userid", applicant_userid);
			temp_param.put("替班人", relief_name);
			temp_param.put("relief_extValue", relief_extValue.toString());
			temp_param.put("换班日期", chaneDateTime);
			changeInfo.add(temp_param);
		}

	}


	/*
	 * 处理批量补卡
	 */
	/*
	 * 处理批量补卡
	 */
	private void dealWithBatchReplacement(JSONObject param, Map map,
			ThirdDingDtemplateXform temp, String docSubject,
			String reviewMainId, SysOrgPerson docCreator, IBaseModel baseModel)
			throws Exception {

		logger.warn("map:" + map);
		// 补卡人
		Map userMap = (Map) map.get("fd_user");
		String replacement_userFdId = (String) userMap.get("id");
		String replacement_username = (String) userMap.get("name");
		String replacement_userDingId = omsRelationService
				.getDingUserIdByEkpUserId(replacement_userFdId);

		param.put("leave_userFdId", replacement_userFdId);
		param.put("leave_userDingId", replacement_userDingId);
		param.put("leave_username", replacement_username);
		if (StringUtil.isNull(replacement_userDingId)) {
			logger.warn("【创建请假实例异常】请假人的钉钉userid为空:" + map);
			return;
		}
		// 构建补卡明细信息
		net.sf.json.JSONArray checkInfo = new net.sf.json.JSONArray();
		buildReplacementInfo(checkInfo, param, map);

		for (int i = 0; i < checkInfo.size(); i++) {
			JSONObject _param = checkInfo.getJSONObject(i);
			String userid = replacement_userDingId;

			JSONObject response = DingNotifyUtil
					.createXformDistance_batch(
							DingUtils.getDingApiService()
									.getAccessToken(),
							userid, temp, docSubject,
							temp.getFdDetail(), _param);

			// 创建实例成功，保存实例到数据库
			saveInstanceLog2DB(response, docSubject, userid, reviewMainId,
					_param, temp, docCreator, baseModel);

		}
	}

	// 构建补卡明细
	private void buildReplacementInfo(net.sf.json.JSONArray checkInfo,
			JSONObject param,
			Map map) {

		List batchReplacementInfoList = (ArrayList) map
				.get("fd_batch_replacement_table");
		if (batchReplacementInfoList == null
				|| batchReplacementInfoList.isEmpty()) {
			logger.error("------补卡明细为空！--------");
			return;
		}
		JSONObject temp_param;
		JSONObject cur_param;
		String paramString = param.toString();
		for (int i = 0; i < batchReplacementInfoList.size(); i++) {
			cur_param = JSONObject.fromObject(batchReplacementInfoList.get(i));
			temp_param = JSONObject.fromObject(paramString);
			String extend_value = cur_param
					.getString("singer_replacement_info");
			logger.warn("extend_value:" + extend_value);
			// 构建实例的extend_value
			JSONObject _extndValue = new JSONObject();
			JSONObject extndValue = JSONObject.fromObject(extend_value);
			_extndValue.put("chat", false);
			String punchId = extndValue.getString("punchId");
			if (StringUtil.isNotNull(punchId) && !punchId.contains("_")) {
				_extndValue.put("planId", Long.valueOf(punchId));
			}
			_extndValue.put("planText", extndValue.getString("planText"));
			_extndValue.put("timeStamp", extndValue.getLong("work_date"));
			_extndValue.put("workDate", extndValue.getLong("work_date"));
			_extndValue.put("userCheckTime",
					extndValue.getLong("check_date_time"));
			_extndValue.put("planTip", extndValue.getString("text"));
			_extndValue.put("bizAlias", "userCheckTime");

			// 补卡原因
			String fdReason = cur_param
					.getString("fd_replacement_reason");
			logger.warn("fdReason:" + fdReason);

			// 补卡时间
			Long time = cur_param.getJSONObject("fd_replacement_time")
					.getLong("time");
			Date replacementTime = new Date(time);
			String userCheckTime = DateUtil.convertDateToString(replacementTime,
					"yyyy-MM-dd HH:mm");
			logger.warn("from_date:" + userCheckTime);

			temp_param.put("补卡原因", fdReason);
			temp_param.put("补卡时间", userCheckTime);
			temp_param.put("extend_value", _extndValue.toString());
			checkInfo.add(temp_param);
		}
		logger.warn("checkInfo:" + checkInfo);

	}

	/*
	 * 处理批量请假
	 */
	private void dealWithBatchLeave(JSONObject param, Map map,
			ThirdDingDtemplateXform temp, String docSubject,
			String reviewMainId, SysOrgPerson docCreator, IBaseModel baseModel)
			throws Exception {

		// 总时长
		String fdSumDuration = (String) map.get("fd_sum_duration");
		logger.warn("总时长：" + fdSumDuration);

		// 请假人
		logger.warn("map:" + map);
		Map userMap = (Map) map.get("fd_leave_user");
		String leave_userFdId = (String) userMap.get("id");
		String leave_username = (String) userMap.get("name");
		String leave_userDingId = omsRelationService
				.getDingUserIdByEkpUserId(leave_userFdId);

		param.put("leave_userFdId", leave_userFdId);
		param.put("leave_userDingId", leave_userDingId);
		param.put("leave_username", leave_username);
        if (StringUtil.isNull(leave_userDingId)) {
			logger.error("【创建请假实例异常】请假人的钉钉userid为空");
			return;
	    }
		// 构建请假明细信息
		net.sf.json.JSONArray leaveInfo = new net.sf.json.JSONArray();
		buildLeaveInfo(leaveInfo, param, map);
		for (int i = 0; i < leaveInfo.size(); i++) {
			JSONObject _param = leaveInfo.getJSONObject(i);
			String userid = leave_userDingId;
			
			OapiProcessWorkrecordCreateResponse response = DingNotifyUtil
					.createXformDistance(DingUtils.getDingApiService()
							.getAccessToken(),
							userid, temp, docSubject,
							temp.getFdDetail(), _param);
			if (response != null && response.getErrcode() == 0) {
				String instanceId = response.getResult()
						.getProcessInstanceId();

				ThirdDingDinstanceXform distance = new ThirdDingDinstanceXform();
				distance.setFdName(docSubject);
				distance.setDocCreateTime(new Date());
				distance.setFdInstanceId(instanceId);
				distance.setFdDingUserId(userid);
				distance.setFdEkpInstanceId(reviewMainId);
				distance.setFdUrl(_param.getString("url"));
				distance.setFdTemplate(temp);
				distance.setFdEkpUser(docCreator);
				distance.setFdStatus("20");

				List<ThirdDingIndanceXDetail> fdDetail = new ArrayList<ThirdDingIndanceXDetail>();
				ThirdDingIndanceXDetail detail = null;
				for (ThirdDingTemplateXDetail det : temp
						.getFdDetail()) {
					detail = new ThirdDingIndanceXDetail();
					detail.setFdName(det.getFdName());
					detail.setFdType("TextField");
					if (StringUtil.isNotNull(det.getFdName())
							&& _param.containsKey(det.getFdName())) {
						detail.setFdValue(_param.getString(det.getFdName()));
					} else {
						detail.setFdValue("");
					}
					fdDetail.add(detail);
				}
				distance.setFdDetail(fdDetail);
				//独立开启一个事务，避免流程的报错影响到实例的保存
				TransactionStatus status = null;
				long alltime = System.currentTimeMillis();
				try {
					status = TransactionUtils.beginNewTransaction();
					getThirdDingDinstanceXformService().add(distance);
					TransactionUtils.getTransactionManager().commit(status);
				} catch (Exception e) {
					if (status != null) {
						try {
							TransactionUtils.getTransactionManager().rollback(status);
						} catch (Exception ex) {
							logger.error("---事务回滚出错---", ex);
						}
					}
				}

			} else {
				logger.warn("创建实例失败：" + response == null ? "返回结果为null"
						: response.getBody());
				sengDingErrorNotify(baseModel, "创建实例失败,请及时处理");
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void buildLeaveInfo(net.sf.json.JSONArray leaveInfo,
			JSONObject param,
			Map map) {

		List batchLeaveInfoList = (ArrayList) map.get("fd_batch_leave_table");
		if (batchLeaveInfoList == null || batchLeaveInfoList.isEmpty()) {
			logger.error("------请假明细为空--------");
			return;
		}
		JSONObject temp_param;
		JSONObject cur_param;
		String paramString = param.toString();
		for (int i = 0; i < batchLeaveInfoList.size(); i++) {
			cur_param = JSONObject.fromObject(batchLeaveInfoList.get(i));
			temp_param = JSONObject.fromObject(paramString);
			String extend_value = cur_param.getString("fd_type_extend_value");
			logger.warn("extend_value:" + extend_value);
			if (StringUtil.isNull(extend_value)) {
				logger.error("【钉钉批量请假】extend_value为空！");
				return;
			}
			// 请假单位
			String unit = cur_param.getString("fd_item_unit");

			// 开始时间
			String from_time = null;
			Long fromTime = cur_param.getJSONObject("fd_leave_start_time")
					.getLong("time");
			Date from_date = new Date(fromTime);
			logger.debug("from_date:" + from_date);
			if ("day".equalsIgnoreCase(unit)) {
				from_time = DateUtil.convertDateToString(from_date,
						"yyyy-MM-dd");
			} else if ("halfDay".equalsIgnoreCase(unit)) {
				String fromHalfDay = cur_param.getString("fd_start_time_one");
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
			Long toDate = cur_param.getJSONObject("fd_leave_end_time")
					.getLong("time");
			Date to_date = new Date(toDate);
			logger.debug("to_date:" + to_date);
			if ("day".equalsIgnoreCase(unit)) {
				to_time = DateUtil.convertDateToString(to_date,
						"yyyy-MM-dd");
			} else if ("halfDay".equalsIgnoreCase(unit)) {
				String toHalfDay = cur_param.getString("fd_end_time_one");
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
			JSONObject typeJson = new JSONObject();
			typeJson.put("leaveCode", cur_param.getString("fd_leave_type"));
			typeJson.put("unit", unit);
			String leave_name = cur_param.getString("fd_type_name");
			logger.warn("leave_name:" + leave_name);
			if (StringUtil.isNotNull(leave_name)) {
				if (leave_name.contains("(")) {
					leave_name = leave_name.substring(0,
							leave_name.indexOf("("));
				} else if (leave_name.contains("（")) {
					leave_name = leave_name.substring(0,
							leave_name.indexOf("（"));
				}
			}
			temp_param.put("请假类型", leave_name);
			temp_param.put("type_extend_value", typeJson.toString());
			temp_param.put("开始时间", from_time);
			temp_param.put("结束时间", to_time);
			temp_param.put("总时长", (String) map.get("fd_sum_duration"));
			String itemTime = cur_param.getString("fd_item_duration");
			if (itemTime.contains("天")) {
				itemTime = itemTime.replace("天", "").trim();
			} else if (itemTime.contains("小时")) {
				itemTime = itemTime.replace("小时", "").trim();
			}
			temp_param.put("时长", itemTime);
			temp_param.put("请假原因", (String) map.get("fd_leave_remark"));
			temp_param.put("extend_value", extend_value);
			leaveInfo.add(temp_param);
		}
		logger.warn("leaveInfo:" + leaveInfo);

	}

	/**
	 * 钉钉考勤同步异常通知(runtime异常)
	 * 
	 * @param
	 */
	private void sengDingErrorNotify(IBaseModel baseModel, String title) {
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
			content.put("title", title);
			content.put("content", "【异常】请处理" + docCreator.getFdName()
					+ " 提交的申请：" + docSubject + " \n");

			String jumpUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix")
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

	/*
	 * 批量加班
	 */
	private void dealWithBatchOverTime(JSONObject param, Map map,
			ThirdDingDtemplateXform temp, String docSubject,
			String reviewMainId, SysOrgPerson docCreator,
			IBaseModel baseModel) throws Exception {
		logger.debug("批量加班Map：" + map);
		// 构建加班明细信息
		net.sf.json.JSONArray overTimeInfo = new net.sf.json.JSONArray();
		buildBatchOverTimeInfo(overTimeInfo, param, map);
		logger.debug(overTimeInfo.toString());
		for (int i = 0; i < overTimeInfo.size(); i++) {
			JSONObject _param = overTimeInfo.getJSONObject(i);
			// 发起人
			String userid = _param.getString("userids").split(",")[0];
			JSONObject response = DingNotifyUtil
					.createXformDistance_batch(DingUtils.getDingApiService()
							.getAccessToken(),
							userid, temp, docSubject,
							temp.getFdDetail(), _param);
			// 创建实例成功，保存实例到数据库
			saveInstanceLog2DB(response, docSubject, userid, reviewMainId,
					_param, temp, docCreator, baseModel);

		}

	}

	/*
	 * 构建批量加班明细
	 */
	private void buildBatchOverTimeInfo(JSONArray overTimeInfo,
			JSONObject param, Map map) throws Exception {
		List batchInfoList = (ArrayList) map.get("fd_batch_work_table");
		if (batchInfoList == null || batchInfoList.isEmpty()) {
			logger.error("------批量加班明细为空--------");
			return;
		}
		JSONObject temp_param;
		JSONObject cur_param;
		String paramString = param.toString();
		for (int i = 0; i < batchInfoList.size(); i++) {
			cur_param = JSONObject.fromObject(batchInfoList.get(i));
			temp_param = JSONObject.fromObject(paramString);

			// 加班人（可多人）
			String applicant_ekpId = cur_param
					.getJSONObject("fd_work_user").getString("id");
			String applicant_name = cur_param
					.getJSONObject("fd_work_user").getString("name");
			logger.debug("加班人：" + applicant_ekpId + "  name:" + applicant_name);

			String userids = "";
			String[] fdIds = applicant_ekpId.split(";");
			String[] names = applicant_name.split(";");
			JSONArray partner_extValue = new JSONArray();
			JSONObject partner;
			for (int j = 0; j < fdIds.length; j++) {
				String applicant_userid = omsRelationService
						.getDingUserIdByEkpUserId(fdIds[j]);
				partner = new JSONObject();
				partner.put("emplId", applicant_userid);
				partner.put("name", names[j]);
				partner.put("read", false);
				partner.put("readTime", 0);
				partner_extValue.add(partner);
				userids += applicant_userid + ",";
			}
			if (userids.endsWith(",")) {
				userids = userids.substring(0, userids.length() - 1);
			}
			logger.debug("userids:" + userids);
			logger.debug("partner_extValue:" + partner_extValue);

			// 开始时间
			Long start_date = cur_param.getJSONObject("fd_work_start_time")
					.getLong("time");
			String startTime = DateUtil.convertDateToString(
					new Date(start_date),
					"yyyy-MM-dd HH:mm");
			logger.debug("开始时间：" + startTime);

			// 结束时间
			Long fd_work_end_time = cur_param.getJSONObject("fd_work_end_time")
					.getLong("time");
			String endTime = DateUtil.convertDateToString(
					new Date(fd_work_end_time),
					"yyyy-MM-dd HH:mm");
			logger.debug("结束时间：" + endTime);

			// 时长
			String fd_duration = cur_param.getString("fd_duration");
			logger.debug("时长：" + fd_duration);

			// 时长extendValue
			String duration_extValue = cur_param.getString("fd_extend_value");
			// 设置测试数据
			// if (StringUtil.isNull(duration_extValue)) {
			// duration_extValue =
			// "{\"calculateMode\":1,\"compressedValue\":\"1f8b0800000000000000a550416ac33010fccb9e85916dc534ba9586924243214d4ea587c55688a82205addc128cff5ec9ae13936b75db99d1ceec7450a3a95b83416d5ca340e60c1a15509b574d01e447074deb3168675fec0a2f5190e5259b816bd77a90cb8cf339bad1b60d8a402e04bfa3de55ed6c03b22c44220e0a43eb93b4033c9fcde5d9bbd34e9f52962aaf0a5ef0e1b191ddb919570af1c7d50689e2e66441436c659bc7da3ba2e1a638cdfe2daa692705f461d24df34d9957cb51da7f32d0b455a993031a520cbe95a7e80612f28c43cfe088f494625c153fce7fad62b377bb4622995c5b120f83c3bfabbee17bab635458bfedb7d0ff0263b1fe93e6010000\",\"detailList\":[{\"durationInDay\":1.13,\"durationInHour\":9.00,\"durationInMinutes\":540.00,\"durationInSecond\":32400,\"features\":{\"applyFromTime\":1616202000000,\"applyToTime\":1616234400000,\"classSections\":[{\"endAcross\":1,\"endTime\":1616256000000,\"startAcross\":0,\"startTime\":1616169600000}],\"isRest\":false,\"version\":\"1.0\"},\"hasClass\":false,\"workDate\":1616169600000,\"workTimeMinutes\":480}],\"durationInDay\":1.13,\"durationInHour\":9.00,\"durationInMinutes\":540,\"durationUnit\":\"HOUR\",\"featureMap\":{\"overtimeUrl\":\"https://attend.dingtalk.com/attend/index.html?corpId=ding35a7fd308d38a9ee35c2f4657eb6378f&showmenu=false&dd_share=false&overtimeId=230815239#admin/overtimeRuleDetail\",\"remark\":\"
			// 加班时长以审批单为准；\",\"overtimeSettingId\":\"230815239\"}}";
			// }

			logger.debug("时长duration_extValue：" + duration_extValue);
			temp_param.put("加班人", applicant_name);
			temp_param.put("partner_extValue", partner_extValue.toString());
			temp_param.put("userids", userids);

			temp_param.put("开始时间", startTime);
			temp_param.put("结束时间", endTime);

			temp_param.put("时长", fd_duration);
			temp_param.put("duration_extValue", duration_extValue);

			//加班补偿
			String fd_compensation = "charge";
			if(cur_param.containsKey("fd_compensation")){
				fd_compensation = cur_param.getString("fd_compensation");
			}
			logger.info("加班补偿---：" + fd_compensation);
			temp_param.put("fd_compensation", fd_compensation);

			overTimeInfo.add(temp_param);
		}
	}
}
