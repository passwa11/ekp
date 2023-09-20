package com.landray.kmss.sys.attend.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryLocation;
import com.landray.kmss.sys.attend.model.SysAttendCategoryRule;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTime;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendOutPerson;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendOutPersonService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.DbUtils;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 可匿名访问的Action
 *
 * @author cuiwj
 * @version 1.0 2018-08-21
 */
public class SysAttendAnonymousAction extends ExtendAction {
	protected ISysAttendMainService sysAttendMainService;
	private ISysAttendCategoryService sysAttendCategoryService;
	private ISysAttendOutPersonService sysAttendOutPersonService;
	private ISysOrgCoreService sysOrgCoreService;

	@Override
	protected ISysAttendMainService getServiceImp(HttpServletRequest request) {
		if (sysAttendMainService == null) {
			sysAttendMainService = (ISysAttendMainService) getBean(
					"sysAttendMainService");
		}
		return sysAttendMainService;
	}

	public ISysAttendCategoryService getSysAttendCategoryService() {
		if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) getBean(
					"sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}

	public ISysAttendOutPersonService getSysAttendOutPersonService() {
		if (sysAttendOutPersonService == null) {
			sysAttendOutPersonService = (ISysAttendOutPersonService) getBean(
					"sysAttendOutPersonService");
		}
		return sysAttendOutPersonService;
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	/**
	 * 会议签到扫二维码，可匿名访问
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward scanToSign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-scanToSign", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmssMessage message = new KmssMessage("");
		boolean fail = false;
		try {
			String categoryId = request.getParameter("categoryId");
			String qrCodeTime = request.getParameter("qrCodeTime");
			String timestamp = request.getParameter("t");

			if (StringUtil.isNotNull(qrCodeTime) && !"0".equals(qrCodeTime)) {
				long ts = Long.valueOf(
						StringUtil.isNull(timestamp) ? "0" : timestamp);
				if (DbUtils.getDbTimeMillis() - ts > Long.valueOf(qrCodeTime)
						* 1L * 1000) {
					fail = true;
					message = new KmssMessage(
							"sys-attend:sysAttendMain.scan.qrcode.overdue");
				}
			}
			if (!fail) {
				if (StringUtil.isNotNull(categoryId)) {
					if (UserUtil.getUser().isAnonymous()) {
						return getActionForward("signChoose", mapping, form,
								request, response);
					} else {
						String url = "/sys/attend/mobile/import/sign_inner.jsp";
						url += "?categoryId=" + categoryId;
						int clientType = MobileUtil.getClientType(request);
						if (clientType == MobileUtil.DING_ANDRIOD) {
							url += "&oauth=ekp";
							response.sendRedirect(StringUtil.formatUrl(url));
						} else if (clientType == MobileUtil.THIRD_WXWORK
								|| clientType == MobileUtil.THIRD_WEIXIN) {
							url += "&oauth=ekp2wx";
							response.sendRedirect(StringUtil.formatUrl(url));
						} else {
							response.sendRedirect(StringUtil.formatUrl(url));
						}
						return null;
					}
				} else {
					fail = true;
					message = new KmssMessage(
							"sys-attend:sysAttendMain.scan.noCategory.tip");
				}
			}
		} catch (Exception e) {
			fail = true;
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-scanToSign", false, getClass());
		if (fail) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).setTitle(message)
					.save(request);
			return getActionForward("scanError", mapping, form, request,
					response);
		} else {
			return getActionForward("signChoose", mapping, form, request,
					response);
		}
	}

	/**
	 * 移动端会议签到，获取某个考勤组的信息，可匿名访问
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-viewCategory", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String categoryId = request.getParameter("categoryId");
			JSONObject result = new JSONObject();
			if (StringUtil.isNotNull(categoryId)) {
				SysAttendCategory category = (SysAttendCategory) getSysAttendCategoryService()
						.findByPrimaryKey(categoryId);
				UserOperHelper.logFind(category);// 添加日志信息
				if (category != null) {
					result.accumulate("fdId", category.getFdId());
					result.accumulate("fdName", category.getFdName());
					List<SysAttendCategoryTime> sysAttendCategoryTimes = category
							.getFdTimes();
					List<SysAttendCategoryLocation> fdLocations = category
							.getFdLocations();
					List<SysAttendCategoryRule> fdRuleList = category
							.getFdRule();

					if (sysAttendCategoryTimes != null
							&& sysAttendCategoryTimes.size() > 0) {
						JSONArray times = new JSONArray();
						Date fdInTime = null;
						// fdInTime签到时间
						if (fdRuleList != null && !fdRuleList.isEmpty()) {
							fdInTime = fdRuleList.get(0).getFdInTime();
						}
						// fdTime签到日期
						for (SysAttendCategoryTime time : sysAttendCategoryTimes) {
							Date fdTime = time.getFdTime();
							times.add(DateUtil.convertDateToString(fdTime,
									DateUtil.TYPE_DATE, null));
						}
						result.accumulate("fdTimes", times);
						result.accumulate("fdInTime",
								DateUtil.convertDateToString(fdInTime,
										DateUtil.TYPE_TIME, null));
					}
					// 地理位置信息
					if (fdLocations != null && !fdLocations.isEmpty()) {
						JSONArray locations = new JSONArray();
						for (SysAttendCategoryLocation fdLocation : fdLocations) {
							JSONObject locObj = new JSONObject();
							locObj.accumulate("address",
									fdLocation.getFdLocation());
							locObj.accumulate("coord",
									fdLocation.getFdLatLng());
							locations.add(locObj);
						}
						result.accumulate("fdLocations", locations);
					}
					// 是否限制地理位置范围外打卡
					if (fdRuleList != null && !fdRuleList.isEmpty()) {
						result.accumulate("fdLimit",
								fdRuleList.get(0).getFdLimit());
					}
					// 是否参与者
					boolean isfdAttender = UserUtil
							.checkUserModels(category.getFdTargets());
					if (category.getFdUnlimitTarget()) {
						isfdAttender = true;
					}
					result.accumulate("isfdAttender", isfdAttender);
					// 是否负责人
					result.accumulate("isfdManager",
							UserUtil.checkUserId(
									category.getFdManager().getFdId()));
					// 开始时间
					result.accumulate("fdStartTime",
							DateUtil.convertDateToString(
									category.getFdStartTime(),
									DateUtil.TYPE_TIME, null));
					// 结束时间
					result.accumulate("fdEndTime",
							DateUtil.convertDateToString(
									category.getFdEndTime(), DateUtil.TYPE_TIME,
									null));
					// 签到组状态
					result.accumulate("fdStatus", category.getFdStatus());
					// 查看会议签到的链接
					String fdAppUrl = category.getFdAppUrl();
					if (StringUtil.isNotNull(fdAppUrl)) {
						if (fdAppUrl.indexOf("?") > -1) {
							fdAppUrl += "&fromSysAttend=true";
						} else {
							fdAppUrl += "?fromSysAttend=true";
						}
						result.accumulate("isUrlAccess",
								UserUtil.checkAuthentication(fdAppUrl, null));
						result.accumulate("fdAppUrl", fdAppUrl);
					}
					result.accumulate("clientType",
							MobileUtil.getClientType(request));
					request.setAttribute("lui-source", result);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-viewCategory", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return mapping.findForward("lui-source");
		}
	}

	/**
	 * 会议签到：获取签到记录，可匿名访问
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listMain", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setPageNo(0);
			hqlInfo.setRowSize(100);
			StringBuffer whereBlock = new StringBuffer("1=1");
			String me = request.getParameter("me");
			String outer = request.getParameter("outer");
			String userId = request.getParameter("userId");
			if (UserUtil.getUser().isAnonymous() || "true".equals(outer)) {// 匿名用户
				if (StringUtil.isNotNull(userId)) {
					whereBlock.append(
							" and sysAttendMain.fdOutPerson.fdId=:userId");
					hqlInfo.setParameter("userId", userId);
				} else {
					throw new NoRecordException();
				}
				// 防止因为数据过滤获取不了数据
				hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.NO);
			} else {
				if ("true".equals(me)) {
					whereBlock.append(" and sysAttendMain.docCreator.fdId=:me");
					hqlInfo.setParameter("me", UserUtil.getUser().getFdId());
				}
			}
			String categoryId = request.getParameter("categoryId");
			if (StringUtil.isNotNull(categoryId)) {
				whereBlock.append(
						" and sysAttendMain.fdCategory.fdId =:categoryId");
				hqlInfo.setParameter("categoryId", categoryId);
			}
			hqlInfo.setWhereBlock(whereBlock.toString());
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listMain", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("listMain", mapping, form, request,
					response);
		}
	}

	/**
	 * 会议签到，更新打卡记录，可匿名访问
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateByExt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean fail = false;
		SysAttendCategory category = null;
		KmssMessage message = new KmssMessage("");
		try {
			String categoryId = request.getParameter("categoryId");
			String fdLocation = request.getParameter("fdLocation");
			String fdLatLng = request.getParameter("fdLatLng");
			if (StringUtil.isNotNull(categoryId)) {
				category = (SysAttendCategory) getSysAttendCategoryService()
						.findByPrimaryKey(categoryId);
				List<SysOrgElement> fdExcTargets = getSysOrgCoreService()
						.expandToPerson(category.getFdExcTargets());
				Boolean fdUnlimitGTarget = category.getFdUnlimitTarget();
				List<SysAttendCategoryTime> timeList = category.getFdTimes();
				Date signTime = timeList.get(0).getFdTime();
				Date fdStartTime = category.getFdStartTime();
				Date fdEndTime = category.getFdEndTime();

				// 签到是否已结束
				if (category.getFdStatus() == 2) {
					message = new KmssMessage(
							"sys-attend:sysAttendMain.scan.status.tip");
					fail = true;
				}
				// 签到时间范围内
				Date now = new Date();
				if (!fail && fdStartTime != null && fdEndTime != null) {
					Date starTime = AttendUtil.getDate(signTime, 0);
					Date endTime = AttendUtil.getDate(signTime, 0);
					starTime.setHours(fdStartTime.getHours());
					starTime.setMinutes(fdStartTime.getMinutes());
					endTime.setHours(fdEndTime.getHours());
					endTime.setMinutes(fdEndTime.getMinutes());
					if (now.before(starTime) || now.after(endTime)) {
						message = new KmssMessage(
								"sys-attend:sysAttendMain.scan.time.tip");
						fail = true;
					}
				}
				// 排除人员
				if (!fail && !fdExcTargets.isEmpty()) {
					if (fdExcTargets.contains(UserUtil.getUser())) {
						message = new KmssMessage(
								"sys-attend:sysAttendMain.scan.tip");
						fail = true;
					}
				}

				// 更新打卡记录
				if (!fail) {
					if (StringUtil.isNotNull(category.getFdAppId())) {
						if (!UserUtil.getUser().isAnonymous()) {
							HQLInfo hqlInfo = new HQLInfo();
							hqlInfo.setWhereBlock(
									"sysAttendMain.fdCategory.fdId=:categoryId and sysAttendMain.docCreator.fdId=:userId and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
							hqlInfo.setParameter("categoryId", categoryId);
							hqlInfo.setParameter("userId",
									UserUtil.getUser().getFdId());
							List<SysAttendMain> sysAttendMains = getServiceImp(
									request).findList(hqlInfo);
							if (sysAttendMains != null
									&& sysAttendMains.size() > 0) {
								SysAttendMain sysAttendMain = sysAttendMains
										.get(0);
								if (sysAttendMain.getFdStatus() != 1) {
									sysAttendMain.setFdStatus(
											getSignStatusByScan(category));
								}
								if (StringUtil.isNotNull(fdLocation)) {
									sysAttendMain.setFdLocation(fdLocation);
								}
								if (StringUtil.isNotNull(fdLatLng)
										&& fdLatLng.contains(",")) {
									String[] coord = fdLatLng
											.replace("bd09:", "")
											.replace("gcj02:", "").split(",");
									sysAttendMain.setFdLat(coord[0]);
									sysAttendMain.setFdLng(coord[1]);
									sysAttendMain.setFdLatLng(fdLatLng);
								}
								sysAttendMain.setFdClientInfo(AttendUtil
										.getClientType(
												new RequestContext(request)));
								sysAttendMain.setFdDeviceInfo(AttendUtil
										.getOperatingSystem(
												new RequestContext(request)));
								// 添加日志信息
								if (UserOperHelper.allowLogOper("updateByExt",
										getServiceImp(request).getModelName())) {
									UserOperHelper.setEventType(ResourceUtil
											.getMessage("button.update"));
									UserOperContentHelper.putUpdate(sysAttendMain)
										.putSimple("fdStatus", null, sysAttendMain.getFdStatus())
										.putSimple("fdLocation", null, fdLocation)
										.putSimple("fdLat", null, sysAttendMain.getFdLat())
										.putSimple("fdLng", null, sysAttendMain.getFdLng())
										.putSimple("fdLatLng", null, sysAttendMain.getFdLatLng());
								}
								getServiceImp(request).update(sysAttendMain);
							} else {
								// 是否允许范围外人员签到
								if (fdUnlimitGTarget != null
										&& fdUnlimitGTarget.booleanValue()) {
									SysAttendMain sysAttendMain = new SysAttendMain();
									sysAttendMain.setFdCategory(category);
									sysAttendMain.setFdHisCategory(CategoryUtil.getHisCategoryById(categoryId));
									sysAttendMain.setFdStatus(
											getSignStatusByScan(category));
									sysAttendMain.setFdOutTarget(true);
									if (StringUtil.isNotNull(fdLocation)) {
										sysAttendMain.setFdLocation(fdLocation);
									}
									if (StringUtil.isNotNull(fdLatLng)
											&& fdLatLng.contains(",")) {
										String[] coord = fdLatLng
												.replace("bd09:", "")
												.replace("gcj02:", "")
												.split(",");
										sysAttendMain.setFdLat(coord[0]);
										sysAttendMain.setFdLng(coord[1]);
										sysAttendMain.setFdLatLng(fdLatLng);
									}
									sysAttendMain.setFdClientInfo(
											AttendUtil.getClientType(
													new RequestContext(
															request)));
									sysAttendMain.setFdDeviceInfo(
											AttendUtil.getOperatingSystem(
													new RequestContext(
															request)));
									// 添加日志信息
									if (UserOperHelper.allowLogOper("updateByExt"
											,getServiceImp(request).getModelName())) {
										UserOperHelper.setEventType(ResourceUtil.getMessage("button.add"));
										UserOperContentHelper.putAdd(sysAttendMain,
												"fdCategory", "fdStatus",
												"fdLocation", "fdLat", "fdLng",
												"fdLatLng");
									}
									getServiceImp(request).add(sysAttendMain,
											new Date());
								} else {
									message = new KmssMessage(
											"sys-attend:sysAttendMain.scan.limit.tip");
									fail = true;
								}
							}
						} else {
							// 是否允许EKP外部人员签到
							if (Boolean.TRUE.equals(category.getFdUnlimitOuter())) {
								String userId = request.getParameter("userId");
								if (StringUtil.isNotNull(userId)) {
									SysAttendOutPerson outPerson = (SysAttendOutPerson) getSysAttendOutPersonService()
											.findByPrimaryKey(userId);
									if (outPerson != null) {
										SysAttendMain sysAttendMain = new SysAttendMain();
										sysAttendMain.setFdCategory(category);
										sysAttendMain.setFdHisCategory(CategoryUtil.getHisCategoryById(categoryId));
										sysAttendMain.setFdStatus(
												getSignStatusByScan(category));
										if (StringUtil.isNotNull(fdLocation)) {
											sysAttendMain
													.setFdLocation(fdLocation);
										}
										if (StringUtil.isNotNull(fdLatLng)
												&& fdLatLng.contains(",")) {
											String[] coord = fdLatLng
													.replace("bd09:", "")
													.replace("gcj02:", "")
													.split(",");
											sysAttendMain.setFdLat(coord[0]);
											sysAttendMain.setFdLng(coord[1]);
											sysAttendMain.setFdLatLng(fdLatLng);
										}
										sysAttendMain.setFdOutPerson(outPerson);
										sysAttendMain.setFdClientInfo(AttendUtil
												.getClientType(
														new RequestContext(
																request)));
										sysAttendMain.setFdDeviceInfo(AttendUtil
												.getOperatingSystem(
														new RequestContext(
																request)));
										getServiceImp(request)
												.add(sysAttendMain, new Date());
									} else {
										message = new KmssMessage(
												"sys-attend:sysAttendMain.scan.noOuter.tip");
										fail = true;
									}
								}
							} else {
								message = new KmssMessage(
										"sys-attend:sysAttendMain.scan.limit.tip");
								fail = true;
							}
						}
					} else {
						message = new KmssMessage(
								"sys-attend:sysAttendMain.scan.support.tip");
						fail = true;
					}
				}
			}
		} catch (Exception e) {
			request.setAttribute("lui-source", "error");
			fail = true;
		}

		String forwardUrl = "";
		if (category != null && StringUtil.isNotNull(category.getFdAppUrl())) {
			forwardUrl = category.getFdAppUrl();
			if (forwardUrl.indexOf("?") > -1) {
				forwardUrl += "&fromSysAttend=true";
			} else {
				forwardUrl += "?fromSysAttend=true";
			}
		}

		JSONObject json = new JSONObject();
		json.accumulate("forwardUrl", forwardUrl);
		json.put("status", 1);
		if (fail) {
			json.put("status", 0);
			json.put("message",
					ResourceUtil.getString(message.getMessageKey()));
			request.setAttribute("lui-source", json);
			return getActionForward("lui-source", mapping, form, request,
					response);
		} else {
			request.setAttribute("lui-source", json);
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	/**
	 * 获取扫码签到的状态，正常或迟到
	 * 
	 * @param category
	 * @return
	 */
	private int getSignStatusByScan(SysAttendCategory category) {
		int status = 1;
		List<SysAttendCategoryRule> fdRuleList = category.getFdRule();
		if (fdRuleList != null && !fdRuleList.isEmpty()) {
			SysAttendCategoryRule rule = fdRuleList.get(0);
			Date fdInTime = rule.getFdInTime();
			Integer fdLateTime = rule.getFdLateTime() == null ? 0 : rule.getFdLateTime();
			if (fdInTime != null && fdLateTime != null) {
				int signTime = fdInTime.getHours() * 60 + fdInTime.getMinutes()
						+ fdLateTime;
				Date now = new Date();
				int nowTime = now.getHours() * 60 + now.getMinutes();
				if (nowTime > signTime) {
					status = 2;
				}
			}
		}
		return status;
	}

	/**
	 * 跳转到外部人员注册页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward register(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-register", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-register", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("register", mapping, form, request,
					response);
		}
	}

	/**
	 * 跳转到外部人员签到页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward signOuter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-signOuter", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (StringUtil.isNull(request.getParameter("categoryId"))
					|| StringUtil.isNull(request.getParameter("userId"))) {
				throw new Exception();
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-signOuter", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("signOuter", mapping, form, request,
					response);
		}
	}
}
