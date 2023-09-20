package com.landray.kmss.sys.attend.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.hr.staff.report.HrAttendJiTiQianKaReport;
import com.landray.kmss.hr.staff.util.Excel02Util;
import com.landray.kmss.sys.attend.forms.SysAttendMainExcForm;
import com.landray.kmss.sys.attend.forms.SysAttendMainForm;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryLocation;
import com.landray.kmss.sys.attend.model.SysAttendCategoryRule;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTime;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendDeviceExc;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendMainExc;
import com.landray.kmss.sys.attend.model.SysAttendSignBak;
import com.landray.kmss.sys.attend.model.SysAttendSignLog;
import com.landray.kmss.sys.attend.model.SysAttendStat;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendDeviceExcService;
import com.landray.kmss.sys.attend.service.ISysAttendDeviceService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainExcService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendOrgService;
import com.landray.kmss.sys.attend.service.ISysAttendSignBakService;
import com.landray.kmss.sys.attend.service.ISysAttendSignLogService;
import com.landray.kmss.sys.attend.service.ISysAttendStatDetailService;
import com.landray.kmss.sys.attend.service.ISysAttendStatJobService;
import com.landray.kmss.sys.attend.service.ISysAttendStatService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.DbUtils;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到表 Action
 *
 * @author
 * @version 1.0 2017-05-24
 */
public class SysAttendMainAction extends ExtendAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendMainAction.class);

	protected ISysAttendMainService sysAttendMainService;
	private ISysAttendCategoryService sysAttendCategoryService;
	private ISysAttendMainExcService sysAttendMainExcService;
	private ISysAttendOrgService sysAttendOrgService;
	private ISysOrgCoreService sysOrgCoreService;
	private ISysAttendConfigService sysAttendConfigService;
	private ISysAttendDeviceService sysAttendDeviceService;
	private ISysAttendDeviceExcService sysAttendDeviceExcService;
	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;
	private ISysAttendBusinessService sysAttendBusinessService;
	private ISysAttendSynDingService sysAttendSynDingService;
	private ISysAttendStatDetailService sysAttendStatDetailService;
	private ISysAttendStatService sysAttendStatService;
	private ISysAttendStatJobService sysAttendStatJobService;

	private ISysAttendHisCategoryService sysAttendHisCategoryService;

	private ISysAttendHisCategoryService getSysAttendHisCategoryService() {
		if (sysAttendHisCategoryService == null) {
			sysAttendHisCategoryService = (ISysAttendHisCategoryService) SpringBeanUtil
					.getBean("sysAttendHisCategoryService");
		}
		return sysAttendHisCategoryService;
	}

	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForm(mapping, form, request, response);
			if (newForm != form) {
				request.setAttribute(getFormName(newForm, request), newForm);
			}
			// 获取当前班次打卡记录,防止重复打卡
			String fdCategoryId = request.getParameter("fdCategoryId");
			String fdWorkType = request.getParameter("fdWorkType");
			String fdWorkTimeId = request.getParameter("fdWorkTimeId");
			Map<String, Object> recordMap = getAttendMain(fdCategoryId, fdWorkTimeId, fdWorkType);
			if (recordMap != null) {
				Integer fdStatus = (Integer) recordMap.get("fdStatus");
				String fdId = (String) recordMap.get("fdId");
				if (!AttendUtil.isAttendBuss(fdStatus.toString())) {
					String redirectUrl = "/sys/attend/mobile/index.jsp";
					request.setAttribute("redirectto", redirectUrl);
					return mapping.findForward("redirect");
				}
				// 出差/请假/外出
				request.setAttribute("fdAttendMainId", fdId);
				request.setAttribute("fdAttendBussStatus", fdStatus);
			}
		} catch (Exception e) {
			messages.addError(e);
			logger.error("外勤打卡页面报错:" + e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
				orderby += " desc";
			}
			if (StringUtil.isNull(orderby)) {
				orderby = "sysAttendMain.docCreateTime desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			List<SysAttendMain> mainList = new ArrayList<SysAttendMain>();
			mainList.addAll(page.getList());
			if (!mainList.isEmpty()) {
				List<String> mainIds = new ArrayList<String>();
				for (SysAttendMain main : mainList) {
					mainIds.add(main.getFdId());
				}
				List<SysAttendMainExc> excList = getAttendMainExcServiceImp()
						.findList(HQLUtil.buildLogicIN("sysAttendMainExc.fdAttendMain.fdId", mainIds), "");
				Map<String, Object> excMap = new HashMap<String, Object>();
				for (SysAttendMainExc exc : excList) {
					excMap.put(exc.getFdAttendMain().getFdId(), exc);
				}
				if (!excMap.isEmpty()) {
					request.setAttribute("excMap", excMap);
				}
			}
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("list", mapping, form, request, response);
		}
	}

	private Map<String, Object> getAttendMain(String fdCategoryId, String fdWorkTimeId, String fdWorkType)
			throws Exception {
		if (StringUtil.isNull(fdCategoryId) || StringUtil.isNull(fdWorkType) || StringUtil.isNull(fdWorkTimeId)) {
			return null;
		}
		List<Map<String, Object>> recordList = getServiceImp(null).findList(fdCategoryId, new Date());
		if (!recordList.isEmpty()) {
			for (Map<String, Object> record : recordList) {
				Integer _fdWorkType = (Integer) record.get("fdWorkType");
				String _fdWordId = (String) record.get("fdWorkId");
				String fdWorkKey = (String) record.get("fdWorkKey");
				_fdWordId = StringUtil.isNotNull(fdWorkKey) ? fdWorkKey : _fdWordId;

				if (fdWorkTimeId.equals(_fdWordId) && Integer.valueOf(fdWorkType).equals(_fdWorkType)) {
					return record;
				}
			}
		}
		return null;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		form = super.createNewForm(mapping, form, request, response);
		SysAttendMainForm mainForm = (SysAttendMainForm) form;
		String fdCategoryId = request.getParameter("fdCategoryId");
		String fdWorkType = request.getParameter("fdWorkType");
		String fdIsAcross = request.getParameter("fdIsAcross");

		if (StringUtil.isNotNull(fdCategoryId)) {
			String __userCateId = null;
			// 考勤组
			SysAttendCategory category = CategoryUtil.getCategoryById(fdCategoryId);
			if (category == null) {
				// 签到组
				category = (SysAttendCategory) getCategoryServiceImp().findByPrimaryKey(fdCategoryId,
						SysAttendCategory.class, false);
			} else {
				__userCateId = getCategoryServiceImp().getAttendCategory(UserUtil.getUser(), new Date());
			}
			List<SysAttendCategoryRule> ruleList = category.getFdRule();
			request.setAttribute("_fdOsdReviewIsUpload",
					null == category.getFdOsdReviewIsUpload() ? "0" : category.getFdOsdReviewIsUpload());
			request.setAttribute("_fdType", category.getFdType());
			request.setAttribute("_fdSecurityMode", AttendUtil.isEnableKKConfig() ? category.getFdSecurityMode() : "");
			request.setAttribute("isRestDay", StringUtil.isNull(__userCateId) ? "true" : "false");
			Date signDate = AttendUtil.getDate(new Date(), 0);
			if (StringUtil.isNotNull(fdIsAcross) && "true".equals(fdIsAcross)) {
				signDate = AttendUtil.getDate(new Date(), -1);
			}
			request.setAttribute("_fdWorkDate", signDate.getTime());
			List<SysAttendCategoryLocation> fdLocations = category.getFdLocations();
			Date signTime = new Date();

			String fdWorkTimeId = request.getParameter("fdWorkTimeId");
			// 打卡时间点
			String _signTime = request.getParameter("_signTime");
			// 获取班次信息
			SysAttendCategoryWorktime workTime = getCategoryServiceImp().getCurrentWorkTime(category, fdWorkTimeId,
					fdWorkType, _signTime);
			if (workTime != null) {
				Integer workTimeMins = workTime.getFdEndTime().getHours() * 60 + workTime.getFdEndTime().getMinutes()
						- workTime.getFdStartTime().getHours() * 60 - workTime.getFdStartTime().getMinutes();
				int _goWorkTimeMins = workTime.getFdStartTime().getHours() * 60
						+ workTime.getFdStartTime().getMinutes();
				if (Integer.valueOf(2).equals(workTime.getFdOverTimeType())) {
					Date startTime = AttendUtil.getDateTime(workTime.getFdStartTime(), 1);
					Date endTime = AttendUtil.getDateTime(workTime.getFdEndTime(), 2);
					workTimeMins = (int) ((endTime.getTime() - startTime.getTime()) / (60 * 1000));
				}
				request.setAttribute("_workTimeMins", workTimeMins);
				request.setAttribute("_goWorkTimeMins", _goWorkTimeMins);

				// 当前所打卡的标准 打卡时间
				Integer overTimeType = 1;
				if ("0".equals(fdWorkType)) {
					signTime = workTime.getFdStartTime();
				} else {
					signTime = workTime.getFdEndTime();
					overTimeType = workTime.getFdOverTimeType() == null ? 1 : workTime.getFdOverTimeType();
				}
				int mins = signTime.getHours() * 60 + signTime.getMinutes();
				if ("1".equals(fdWorkType) && Integer.valueOf(2).equals(workTime.getFdOverTimeType())) {
					mins += 1440;// 24*60
				}
				request.setAttribute("_signTime", mins);
				request.setAttribute("_overTimeType", overTimeType);
			}
			if (ruleList != null && !ruleList.isEmpty()) {
				SysAttendCategoryRule rule = ruleList.get(0);
				request.setAttribute("_fdLateTime", rule.getFdLateTime());
				request.setAttribute("_fdLeftTime", rule.getFdLeftTime());
				request.setAttribute("_fdLimit", rule.getFdLimit());
				request.setAttribute("_outside", Boolean.TRUE.equals(rule.getFdOutside()));
			}
			if (fdLocations != null && !fdLocations.isEmpty()) {
				JSONArray array = new JSONArray();
				for (SysAttendCategoryLocation loc : fdLocations) {
					JSONObject json = new JSONObject();
					json.put("coord", loc.getFdLatLng());
					json.put("address", loc.getFdLocation());
					json.put("distance", loc.getFdLimit());
					array.add(json);
				}
				request.setAttribute("_fdLocations", array);
			} else {
				request.setAttribute("_fdLocations", new JSONArray());
			}

			request.setAttribute("_fdIsFlex", category.getFdIsFlex());
			request.setAttribute("_fdFlexTime", category.getFdFlexTime());
			request.setAttribute("_fdNotifyResult", category.getFdNotifyResult());
			request.setAttribute("_fdManagerId", category.getFdManager().getFdId());

		}

		return mainForm;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		// 默认为考勤标识
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		StringBuffer sb = new StringBuffer("1=1 ");
		boolean hisCategoryJoin = false;
		String categoryType = request.getParameter("categoryType");
		if (StringUtil.isNotNull(categoryType)) {
			if ("custom".equals(categoryType)) {
				sb.append(" and sysAttendMain.fdCategory.fdType=:fdCategoryType");
				hqlInfo.setParameter("fdCategoryType", 2);
			} else {
				// 考勤数据
				sb.append(" and sysAttendMain.fdWorkType is not null ");
				sb.append(" and (sysAttendMain.fdWorkKey is not null or sysAttendMain.workTime is not null)");
			}

		}
		String appId = request.getParameter("appId");
		if (StringUtil.isNotNull(appId)) {
			// 会议签到记录不做权限过滤
			// hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			sb.append(" and sysAttendMain.fdCategory.fdAppId=:appId");
			hqlInfo.setParameter("appId", appId);
			String operType = request.getParameter("operType");
			if ("1".equals(operType)) {
				// 已签到
				sb.append(" and sysAttendMain.fdStatus > 0");
			} else if ("0".equals(operType)) {
				// 未签到
				sb.append(" and sysAttendMain.fdStatus = 0");
			}
		}
		String fdCategoryId = request.getParameter("fdCategoryId");
		if (StringUtil.isNotNull(fdCategoryId)) {
			SysAttendHisCategory hisCategory = CategoryUtil.getHisCategoryById(fdCategoryId);
			if (hisCategory != null) {
				sb.append(" and fdHisCategory.fdCategoryId =:fdCategoryId ");
				hisCategoryJoin = true;
			} else {
				sb.append(" and sysAttendMain.fdCategory.fdId =:fdCategoryId ");
			}
			hqlInfo.setParameter("fdCategoryId", fdCategoryId);
		}

		/*
		 * String appName = request.getParameter("appName"); String appKey =
		 * request.getParameter("appKey"); if ("default".equals(appKey)) {
		 * hqlInfo.setAuthCheckType("SIGN_READER"); sb.append(
		 * " and (sysAttendMain.fdCategory.fdAppId='' or sysAttendMain.fdCategory.fdAppId is null)"
		 * ); } else if (StringUtil.isNotNull(appKey)) { sb.append(
		 * " and (sysAttendMain.fdCategory.fdAppName =:fdAppName or sysAttendMain.fdCategory.fdAppKey =:fdAppKey)"
		 * ); hqlInfo.setParameter("fdAppName", appName);
		 * hqlInfo.setParameter("fdAppKey", appKey); }
		 */

		String fdStatus = request.getParameter("fdStatus");
		if (StringUtil.isNotNull(fdStatus)) {
			List fdStatusArr = ArrayUtil.convertArrayToList(fdStatus.split(";"));
			sb.append(" and " + HQLUtil.buildLogicIN("sysAttendMain.fdStatus", fdStatusArr));
		}

		// 搜索考勤组名筛选项
		String categoryName = cv.poll("fdCategoryName");
		if (StringUtil.isNotNull(categoryName)) {
			if (StringUtil.isNotNull(categoryType)) {
				if ("custom".equals(categoryType)) {
					sb.append(" and sysAttendMain.fdCategory.fdName like :categoryName ");
				} else {
					sb.append(" and fdHisCategory.fdName like :categoryName ");
					hisCategoryJoin = true;
				}
			}
			hqlInfo.setParameter("categoryName", "%" + categoryName + "%");
		}

		// 处理状态筛选项
		String[] fdState = cv.polls("fdState");
		if (fdState != null && fdState.length > 0) {
			List<String> fdStateList = ArrayUtil.convertArrayToList(fdState);
			if (!fdStateList.isEmpty()) {
				sb.append(" and (1=2");
				if (fdStateList.contains("0")) {
					sb.append(" or sysAttendMain.fdState is null");
				}
				sb.append(" or " + HQLUtil.buildLogicIN("sysAttendMain.fdState", fdStateList));
				sb.append(" )");
			}
		}

		// 签到状态筛选项
		String[] fdStatusArr = cv.polls("fdStatus");
		if (fdStatusArr != null && fdStatusArr.length > 0) {
			List<String> fdStatusList = ArrayUtil.convertArrayToList(fdStatusArr);
			if (!fdStatusList.isEmpty()) {
				sb.append(" and (1=2");
				// 正常
				if (fdStatusList.contains("1")) {
					sb.append(
							" or sysAttendMain.fdStatus=1 and (sysAttendMain.fdOutside is null or sysAttendMain.fdOutside=:fdOutside1)"
									+ " or sysAttendMain.fdState=2 and (sysAttendMain.fdStatus=0 or sysAttendMain.fdStatus=2 or sysAttendMain.fdStatus=3)");
					hqlInfo.setParameter("fdOutside1", false);
				}
				// 外勤
				if (fdStatusList.contains("11")) {
					sb.append(" or sysAttendMain.fdStatus=1 and sysAttendMain.fdOutside=:fdOutside2");
					hqlInfo.setParameter("fdOutside2", true);
				}
				// 缺卡
				if (fdStatusList.contains("0")) {
					sb.append(
							" or sysAttendMain.fdStatus=0 and (sysAttendMain.fdState is null or sysAttendMain.fdState!=2)");
				}
				// 迟到
				if (fdStatusList.contains("2")) {
					sb.append(
							" or sysAttendMain.fdStatus=2 and (sysAttendMain.fdState is null or sysAttendMain.fdState!=2)");
				}
				// 早退
				if (fdStatusList.contains("3")) {
					sb.append(
							" or sysAttendMain.fdStatus=3 and (sysAttendMain.fdState is null or sysAttendMain.fdState!=2)");
				}
				// 出差
				if (fdStatusList.contains("4")) {
					sb.append(" or sysAttendMain.fdStatus=4");
				}
				// 请假
				if (fdStatusList.contains("5")) {
					sb.append(" or sysAttendMain.fdStatus=5");
				}
				// 外出
				if (fdStatusList.contains("6")) {
					sb.append(" or sysAttendMain.fdStatus=6");
				}
				sb.append(" )");
			}
		}

		String mydoc = request.getParameter("mydoc");
		// 考勤异常
		if ("myexc".equals(mydoc)) {
			String[] excTypes = cv.polls("excType");
			if (excTypes == null || excTypes.length <= 0) {
				sb.append(" and (sysAttendMain.fdStatus=0 or sysAttendMain.fdStatus=2 or sysAttendMain.fdStatus=3"
						+ " or (fdHisCategory.fdOsdReviewType=1 and sysAttendMain.fdStatus=1 and sysAttendMain.fdOutside=:fdOutside3))");
				hqlInfo.setParameter("fdOutside3", true);
				hisCategoryJoin = true;
			} else {
				List<String> excTypesList = ArrayUtil.convertArrayToList(excTypes);
				sb.append(" and (1=2");
				if (excTypesList.contains("11")) {// 外勤
					sb.append(
							" or fdHisCategory.fdOsdReviewType=1 and sysAttendMain.fdStatus=1 and sysAttendMain.fdOutside=:fdOutside4");
					hqlInfo.setParameter("fdOutside4", true);
					excTypesList.remove("11");
					hisCategoryJoin = true;
				}
				if (!excTypesList.isEmpty()) {
					sb.append(" or " + HQLUtil.buildLogicIN("sysAttendMain.fdStatus", excTypesList));
				}
				sb.append(")");
			}
		}

		String me = request.getParameter("me");
		if ("true".equals(me)) {
			sb.append(" and sysAttendMain.docCreator.fdId=:me");
			hqlInfo.setParameter("me", UserUtil.getUser().getFdId());
		}
		// 部门筛选项
		String docCreatorDept = cv.poll("docCreatorDept");
		if (StringUtil.isNotNull(docCreatorDept)) {
			List<String> deptIds = new ArrayList<String>();
			deptIds.add(docCreatorDept);
			List personIds = getSysOrgCoreService().expandToPostPersonIds(deptIds);
			if (!personIds.isEmpty()) {
				sb.append(" and " + HQLUtil.buildLogicIN("sysAttendMain.docCreator.fdId", personIds));
			} else {
				sb.append(" and 1=2");
			}
		}
		// 显示今天前的记录
		/*
		 * sb.append(" and sysAttendMain.docCreateTime <= :docCreateTime");
		 * hqlInfo.setParameter("docCreateTime", AttendUtil.getDate(new Date(),
		 * 1));
		 */

		// 不显示缺卡记录
		String noMissed = request.getParameter("noMissed");
		if ("true".equals(noMissed)) {
			sb.append(" and (sysAttendMain.fdStatus != 0 or sysAttendMain.fdState is not null)");
		}
		// 考勤 业务相关
		// if (attend) {
		// sb.append(" and syOrgElement.fdIsBusiness=:isBusiness");
		// hqlInfo.setParameter("isBusiness", true);
		// }
		sb.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
		hqlInfo.setWhereBlock(sb.toString());
		if (hisCategoryJoin) {
			hqlInfo.setJoinBlock(" left join sysAttendMain.fdHisCategory fdHisCategory ");
		}
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendMain.class);
	}

	/**
	 * 移动端签到
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listAttend(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-listAttend", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			Page page = new Page();
			String signDate = request.getParameter("signDate");
			// 根据人员和日期。查找所属日期的考勤组
			String categoryId = request.getParameter("categoryId");

			if (StringUtil.isNotNull(categoryId)) {
				Date date = AttendUtil.getDate(new Date(), 0);
				if (StringUtil.isNotNull(signDate)) {
					date.setTime(Long.parseLong(signDate));
				}
				List recordList = null;
				// 区分考勤组和签到组
				// 2021-09-30冲制修改
				SysAttendCategory category = getSysAttendHisCategoryService().getCategoryById(categoryId);
				// 兼容历史数据
				if (category == null) {
					if (getCategoryServiceImp().getBaseDao().isExist(SysAttendCategory.class.getName(), categoryId)) {
						// 签到组
						category = (SysAttendCategory) getCategoryServiceImp().findByPrimaryKey(categoryId, null, true);
						recordList = getServiceImp(request).findCustList(categoryId, date);
					}
				} else {
					String currentCategoryId = getCategoryServiceImp().getCategory(UserUtil.getUser(), date);
					if (currentCategoryId == null || !currentCategoryId.equals(categoryId)) {
						throw new Exception("考勤组跟当前用户不一致，请求失败");
					}
					// 考勤组
					recordList = getServiceImp(request).findList(categoryId, date);
				}
				List list = getServiceImp(request).format(recordList, category, date);
				page.setList(list);
				page.setPageno(1);
				page.setTotalrows(list.size());
				page.setRowsize(SysConfigParameters.getRowSize());
			} else {
				page.excecute();
			}
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-listAttend", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listAttend", mapping, form, request, response);
		}
	}

	public ActionForward getDbTimeMillis(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getDbTimeMillis", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			json.accumulate("nowTime", DbUtils.getDbTimeMillis());
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			messages.addError(e);
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return mapping.findForward("lui-source");
		}
	}

	public ActionForward getCateAlertTime(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getCateAlertTime", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdCategoryId = request.getParameter("fdCategoryId");
			JSONObject json = new JSONObject();
			if (StringUtil.isNotNull(fdCategoryId)) {
				// 如果找不到其考勤组，直接就设置2099.不允许打卡
				SysAttendCategory sysAttendCategory = CategoryUtil.getCategoryById(fdCategoryId);
				if (null != sysAttendCategory) {
					json.accumulate("cateAlertTime",
							sysAttendCategory.getDocAlterTime() == null ? sysAttendCategory.getDocCreateTime().getTime()
									: sysAttendCategory.getDocAlterTime().getTime());
				} else {
					json.accumulate("cateAlertTime", CategoryUtil.getMaxDate());
				}
			}
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			messages.addError(e);
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return mapping.findForward("lui-source");
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", false);
		SysAttendMainForm mainForm = null;
		try {
			mainForm = (SysAttendMainForm) form;
			logger.warn("考勤打卡开始,用户名:" + UserUtil.getUser().getFdName() + ";fdCategoryId:" + mainForm.getFdCategoryId());
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			Date workDay = new Date();
			// long类型
			String fdWordkDate = request.getParameter("fdWorkDateLong");
			if (StringUtil.isNotNull(fdWordkDate)) {
				workDay = new Date(Long.valueOf(fdWordkDate));
			}
			boolean isAttend = true;
			SysAttendCategory category = CategoryUtil.getCategoryById(mainForm.getFdCategoryId());
			// 兼容历史数据
			if (category == null) {
				if (getCategoryServiceImp().getBaseDao().isExist(SysAttendCategory.class.getName(),
						mainForm.getFdCategoryId())) {
					// 签到组
					category = (SysAttendCategory) getCategoryServiceImp().findByPrimaryKey(mainForm.getFdCategoryId());
					if (category.getFdType() == 2) {
						isAttend = false;
					}
				}
			}
			if (isAttend) {
				String currentCategoryId = getCategoryServiceImp().getCategory(UserUtil.getUser(), workDay);
				if (currentCategoryId == null || !currentCategoryId.equals(mainForm.getFdCategoryId())) {
					throw new Exception("考勤组跟当前用户不一致，请求失败");
				}
			}
			// 打卡校验,防止重复打卡
			String fdWorkTimeId = StringUtil.isNotNull(mainForm.getFdWorkTimeId()) ? mainForm.getFdWorkTimeId()
					: mainForm.getFdWorkKey();
			Map<String, Object> recordMap = getAttendMain(mainForm.getFdCategoryId(), fdWorkTimeId,
					mainForm.getFdWorkType());
			if (recordMap != null) {
				Integer fdStatus = (Integer) recordMap.get("fdStatus");
				boolean isAttendBus = AttendUtil.isAttendBuss(fdStatus.toString());
				// 防止重复插入打卡记录
				if (isAttendBus) {
					mainForm.setFdId((String) recordMap.get("fdId"));
				} else {
					String msg = "用户重复打卡,忽略:userName:" + UserUtil.getUserName(request) + ";fdCategoryId:"
							+ mainForm.getFdCategoryId() + ";fdWorkId:" + fdWorkTimeId + ";" + mainForm.getFdWorkType();
					logger.warn(msg);
					jsonObj.put("code", "02");
					throw new Exception(msg);
				}
			}
			// 保存或更新用户打卡设备号
			this.getSysAttendDeviceService().saveOrUpdateUserDevice(request, jsonObj);
			// 保存打卡记录信息
			mainForm.setFdAppName(ResourceUtil.getString("sysAttendMain.fdAppName.ekp", "sys-attend"));
			// 处理标准打卡时间 根据signTime转换成时间。
			setFdBaseWorkTime(request, mainForm);
			// 保存打卡记录
			String fdId = getServiceImp(request).add((IExtendForm) form, new RequestContext(request));
			jsonObj.put("status", true);
			// 打卡设备异常时增加异常记录
			this.saveUserDeviceExc(request, fdId);

		} catch (Exception e) {
			logger.error("考勤打卡失败,用户名:" + UserUtil.getUser().getFdName() + ";" + e.getMessage(), e);
			messages.addError(e);
			jsonObj.put("status", false);
		} finally {
			if (mainForm != null) {
				// 每次请求不管成功都记录打卡记录
				getServiceImp(request).addSignLog(mainForm);
			}
		}
		request.setAttribute("lui-source", jsonObj);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	private void setFdBaseWorkTime(HttpServletRequest request, SysAttendMainForm mainForm) {
		if (mainForm == null) {
			return;
		}
		// 分钟数
		String signTime = request.getParameter("signTime");
		// long类型
		String fdWordkDate = request.getParameter("fdWorkDateLong");
		if (StringUtil.isNotNull(signTime) && StringUtil.isNotNull(fdWordkDate)) {
			Integer signMins = Integer.valueOf(signTime);
			Date workDate = new Date(Long.valueOf(fdWordkDate));
			// 小时
			int hours = signMins / 60;
			if (hours > 24) {
				// 超过24小时说明是跨天
				hours = hours - 24;
				workDate = AttendUtil.getDate(workDate, 1);
			}
			// 分钟
			int mins = signMins % 60;
			Calendar cal = Calendar.getInstance();
			cal.setTime(workDate);
			cal.set(Calendar.HOUR_OF_DAY, hours);
			cal.set(Calendar.MINUTE, mins);
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.SECOND, 0);
			mainForm.setFdBaseWorkTime(cal.getTime());
			mainForm.setFdWorkDate(workDate);
		}
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject jsonObj = new JSONObject();
		SysAttendMainForm mainForm = null;
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			mainForm = (SysAttendMainForm) form;
			SysAttendCategory sysAttendCategory = CategoryUtil.getCategoryById(mainForm.getFdCategoryId());
			mainForm.setFdAppName(ResourceUtil.getString("sysAttendMain.fdAppName.ekp", "sys-attend"));
			mainForm.setFdSourceType("");
			Date fdEndTime = sysAttendCategory.getFdEndTime();
			Date fdStartTime = sysAttendCategory.getFdStartTime();
			Integer fdEndDay = sysAttendCategory.getFdEndDay();
			Date now = new Date();
			int nowMins = now.getHours() * 60 + now.getMinutes();
			String fdIsAcross = request.getParameter("fdIsAcross");
			if ("true".equals(fdIsAcross)) {
				// 跨天打卡加上24小时的分钟数
				nowMins += 24 * 60;
			}
			// 标准打卡时间
			setFdBaseWorkTime(request, mainForm);
			boolean isUpdate = true;
			if (fdEndTime != null && fdStartTime != null) {
				int fdEndMins = fdEndTime.getHours() * 60 + fdEndTime.getMinutes();
				int fdStartMins = fdStartTime.getHours() * 60 + fdStartTime.getMinutes();
				if (fdEndDay == null || fdEndDay.intValue() == 1) {
					if (nowMins > fdEndMins) {
						isUpdate = false;
					}
				} else if (fdEndDay.intValue() == 2) {
					if (nowMins > fdEndMins && nowMins < fdStartMins) {
						isUpdate = false;
					}
				}
			}
			boolean result = false;
			if (isUpdate) {
				this.getSysAttendDeviceService().saveOrUpdateUserDevice(request, jsonObj);
				getServiceImp(request).update((IExtendForm) form, new RequestContext(request));
				result = true;
				// 打卡设备异常时增加异常记录
				this.saveUserDeviceExc(request, mainForm.getFdId());
				jsonObj.put("status", result);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			messages.addError(e);
			jsonObj.put("status", false);
		} finally {
			if (mainForm != null) {
				// 每次请求不管成功都记录打卡记录
				getServiceImp(request).addSignLog(mainForm);
			}
		}
		request.setAttribute("lui-source", jsonObj);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	/**
	 * 会议签到，更新打卡记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateByExt(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean fail = false;
		SysAttendCategory category = null;
		KmssMessage message = new KmssMessage("");
		try {
			String categoryId = request.getParameter("categoryId");
			String fdLocation = request.getParameter("fdLocation");
			String fdLatLng = request.getParameter("fdLatLng");
			if (StringUtil.isNotNull(categoryId)) {
				// 考勤组配置信息
				category = (SysAttendCategory) getCategoryServiceImp().findByPrimaryKey(categoryId);
				if (category == null) {
					throw new Exception("考勤组不存在");
				}
				// 考勤组版本信息
				// SysAttendHisCategory hisCategory
				// =CategoryUtil.getHisCategoryById(categoryId);
				// List<SysOrgElement> tempTargets =
				// getSysAttendHisCategoryService().getCategoryExcOrg(categoryId,new
				// Date(),CategoryUtil.CATEGORY_FD_TYPE_SIGN);
				// 考勤排除对象对象
				// List<SysOrgElement> fdExcTargets =
				// getSysOrgCoreService().expandToPerson(tempTargets);
				List<SysOrgElement> fdExcTargets = getSysOrgCoreService().expandToPerson(category.getFdExcTargets());
				Boolean fdUnlimitGTarget = category.getFdUnlimitTarget();
				List<SysAttendCategoryTime> timeList = category.getFdTimes();
				Date signTime = timeList.get(0).getFdTime();
				Date fdStartTime = category.getFdStartTime();
				Date fdEndTime = category.getFdEndTime();

				// 签到是否已结束
				if (category.getFdStatus() == 2) {
					message = new KmssMessage("sys-attend:sysAttendMain.scan.status.tip");
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
						message = new KmssMessage("sys-attend:sysAttendMain.scan.time.tip");
						fail = true;
					}
				}
				// 排除人员
				if (!fail && !fdExcTargets.isEmpty()) {
					if (fdExcTargets.contains(UserUtil.getUser())) {
						message = new KmssMessage("sys-attend:sysAttendMain.scan.tip");
						fail = true;
					}
				}

				// 更新打卡记录
				if (!fail) {
					if (StringUtil.isNotNull(category.getFdAppId())) {
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setWhereBlock(
								"sysAttendMain.fdCategory.fdId=:categoryId and sysAttendMain.docCreator.fdId=:userId and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
						hqlInfo.setParameter("categoryId", categoryId);
						hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
						SysAttendMain sysAttendMains = (SysAttendMain) getServiceImp(request).findFirstOne(hqlInfo);
						if (sysAttendMains != null) {
							SysAttendMain sysAttendMain = sysAttendMains;
							if (sysAttendMain.getFdStatus() != 1) {
								sysAttendMain.setFdStatus(getSignStatusByScan(category));
							}
							if (StringUtil.isNotNull(fdLocation)) {
								sysAttendMain.setFdLocation(fdLocation);
							}
							if (StringUtil.isNotNull(fdLatLng) && fdLatLng.contains(",")) {
								String[] coord = fdLatLng.replace("bd09:", "").replace("gcj02:", "").split(",");
								sysAttendMain.setFdLat(coord[0]);
								sysAttendMain.setFdLng(coord[1]);
								sysAttendMain.setFdLatLng(fdLatLng);
							}
							sysAttendMain.setFdClientInfo(AttendUtil.getClientType(new RequestContext(request)));
							sysAttendMain.setFdDeviceInfo(AttendUtil.getOperatingSystem(new RequestContext(request)));
							// 添加日志信息
							if (UserOperHelper.allowLogOper("updateByExt", getServiceImp(request).getModelName())) {
								UserOperHelper.setEventType(ResourceUtil.getMessage("button.update"));
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
							if (fdUnlimitGTarget != null && fdUnlimitGTarget.booleanValue()) {
								SysAttendMain sysAttendMain = new SysAttendMain();
								sysAttendMain.setFdCategory(category);
								sysAttendMain.setFdStatus(getSignStatusByScan(category));
								sysAttendMain.setFdOutTarget(true);
								if (StringUtil.isNotNull(fdLocation)) {
									sysAttendMain.setFdLocation(fdLocation);
								}
								if (StringUtil.isNotNull(fdLatLng) && fdLatLng.contains(",")) {
									String[] coord = fdLatLng.replace("bd09:", "").replace("gcj02:", "").split(",");
									sysAttendMain.setFdLat(coord[0]);
									sysAttendMain.setFdLng(coord[1]);
									sysAttendMain.setFdLatLng(fdLatLng);
								}
								sysAttendMain.setFdClientInfo(AttendUtil.getClientType(new RequestContext(request)));
								sysAttendMain
										.setFdDeviceInfo(AttendUtil.getOperatingSystem(new RequestContext(request)));
								// 添加日志信息
								if (UserOperHelper.allowLogOper("updateByExt", getServiceImp(request).getModelName())) {
									UserOperHelper.setEventType(ResourceUtil.getMessage("button.add"));
									UserOperContentHelper.putAdd(sysAttendMain, "fdCategory", "fdStatus", "fdLocation",
											"fdLat", "fdLng", "fdLatLng");
								}
								getServiceImp(request).add(sysAttendMain, new Date());
							} else {
								message = new KmssMessage("sys-attend:sysAttendMain.scan.limit.tip");
								fail = true;
							}
						}
					} else {
						message = new KmssMessage("sys-attend:sysAttendMain.scan.support.tip");
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
			json.put("message", ResourceUtil.getString(message.getMessageKey()));
			request.setAttribute("lui-source", json);
			return getActionForward("lui-source", mapping, form, request, response);
		} else {
			request.setAttribute("lui-source", json);
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	/**
	 * 会议扫码签到，更新打卡记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward updateByScan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean fail = false;
		SysAttendCategory category = null;
		KmssMessage message = new KmssMessage("");
		try {
			String categoryId = request.getParameter("categoryId");
			if (StringUtil.isNotNull(categoryId)) {
				category = (SysAttendCategory) getCategoryServiceImp().findByPrimaryKey(categoryId);
				List<SysOrgElement> tempTargets = null;
				// 签到对象
				if (category == null) {
					throw new Exception(" category is null");
				} else {
					tempTargets = category.getFdTargets();
				}
				if (CollectionUtils.isNotEmpty(tempTargets)) {
					throw new Exception(" targets is null");
				}
				// 考勤对象
				List<SysOrgElement> fdExcTargets = getSysOrgCoreService().expandToPerson(tempTargets);
				Boolean fdUnlimitGTarget = category.getFdUnlimitTarget();
				List<SysAttendCategoryTime> timeList = category.getFdTimes();
				Date signTime = timeList.get(0).getFdTime();
				Date fdStartTime = category.getFdStartTime();
				Date fdEndTime = category.getFdEndTime();
				// 签到是否已结束
				if (category.getFdStatus() == 2) {
					request.setAttribute("lui-source",
							ResourceUtil.getString("sysAttendMain.scan.status.tip", "sys-attend"));
					message = new KmssMessage("sys-attend:sysAttendMain.scan.status.tip");
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
						request.setAttribute("lui-source",
								ResourceUtil.getString("sysAttendMain.scan.time.tip", "sys-attend"));
						message = new KmssMessage("sys-attend:sysAttendMain.scan.time.tip");
						fail = true;
					}
				}
				// 排除人员
				if (!fdExcTargets.isEmpty() && !fail) {
					if (fdExcTargets.contains(UserUtil.getUser())) {
						request.setAttribute("lui-source",
								ResourceUtil.getString("sysAttendMain.scan.tip", "sys-attend"));
						message = new KmssMessage("sys-attend:sysAttendMain.scan.tip");
						fail = true;
					}
				}
				// 二维码有效期
				if (!fail) {
					String t = request.getParameter("t");
					long timestamp = Long.valueOf(StringUtil.isNull(t) ? "0" : t);
					Integer fdQRCodeTime = category.getFdQRCodeTime();
					if (fdQRCodeTime != null) {
						// 请求时间戳超过限定时间
						if (DbUtils.getDbTimeMillis() - timestamp > fdQRCodeTime * 1L * 1000) {
							fail = true;
							request.setAttribute("lui-source",
									ResourceUtil.getString("sysAttendMain.scan.time.tip", "sys-attend"));
							message = new KmssMessage("sys-attend:sysAttendMain.scan.time.tip");
						}
					}
				}
				// 更新打卡记录
				if (!fail) {
					if (StringUtil.isNotNull(category.getFdAppId())) {
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setWhereBlock(
								"sysAttendMain.fdHisCategory.fdId=:categoryId and sysAttendMain.docCreator.fdId=:userId and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
						hqlInfo.setParameter("categoryId", categoryId);
						hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
						SysAttendMain sysAttendMains = (SysAttendMain) getServiceImp(request).findFirstOne(hqlInfo);
						if (sysAttendMains != null) {
							SysAttendMain sysAttendMain = sysAttendMains;
							if (sysAttendMain.getFdStatus() != 1) {
								sysAttendMain.setFdStatus(getSignStatusByScan(category));
							}
							// 添加日志信息
							if (UserOperHelper.allowLogOper("updateByScan", getServiceImp(request).getModelName())) {
								UserOperHelper.setEventType(ResourceUtil.getMessage("button.update"));
								UserOperContentHelper.putUpdate(sysAttendMain).putSimple("fdStatus", null,
										sysAttendMain.getFdStatus());
							}
							getServiceImp(request).update(sysAttendMain);
						} else {
							// 是否允许范围外人员签到
							if (fdUnlimitGTarget != null && fdUnlimitGTarget.booleanValue()) {
								SysAttendMain sysAttendMain = new SysAttendMain();
								sysAttendMain.setFdCategory(category);
								sysAttendMain.setFdStatus(getSignStatusByScan(category));
								sysAttendMain.setFdOutTarget(true);
								if (UserOperHelper.allowLogOper("updateByScan",
										getServiceImp(request).getModelName())) {
									UserOperHelper.setEventType(ResourceUtil.getMessage("button.add"));
									UserOperContentHelper.putAdd(sysAttendMain, "fdCategory", "fdStatus");
								}
								getServiceImp(request).add(sysAttendMain, new Date());
							} else {
								request.setAttribute("lui-source",
										ResourceUtil.getString("sysAttendMain.scan.limit.tip", "sys-attend"));
								message = new KmssMessage("sys-attend:sysAttendMain.scan.limit.tip");
								fail = true;
							}
						}
					} else {
						request.setAttribute("lui-source",
								ResourceUtil.getString("sysAttendMain.scan.support.tip", "sys-attend"));
						message = new KmssMessage("sys-attend:sysAttendMain.scan.support.tip");
						fail = true;
					}
				}

			}
		} catch (Exception e) {
			request.setAttribute("lui-source", "error");
			fail = true;
		}
		// forward
		String forwardUrl = "";
		if (category != null && StringUtil.isNotNull(category.getFdAppUrl())) {
			forwardUrl = category.getFdAppUrl();
			if (forwardUrl.indexOf("?") > -1) {
				forwardUrl += "&fromSysAttend=true";
			} else {
				forwardUrl += "?fromSysAttend=true";
			}
		}

		if (fail) {
			KmssMessages messages = new KmssMessages();
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN)
					.setTitle(message).save(request);
			request.setAttribute("forwardUrl", forwardUrl);
			return getActionForward("scanError", mapping, form, request, response);
		} else {
			if (StringUtil.isNotNull(forwardUrl)) {
				ActionForward actionForward = new ActionForward();
				actionForward.setPath(forwardUrl);
				return actionForward;
			}
			return getActionForward("success", mapping, form, request, response);
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
				int signTime = fdInTime.getHours() * 60 + fdInTime.getMinutes() + fdLateTime;
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
	 * PC端考勤日历和签到日历，获取数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward mycalendar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			String fdStart = request.getParameter("fdStart");
			String fdEnd = request.getParameter("fdEnd");
			Date docStartTime = null;
			Date docEndTime = null;
			if (StringUtil.isNotNull(fdStart) && StringUtil.isNotNull(fdEnd)) {
				docStartTime = DateUtil.convertStringToDate(fdStart, DateUtil.TYPE_DATETIME, request.getLocale());
				docEndTime = DateUtil.convertStringToDate(fdEnd, DateUtil.TYPE_DATETIME, request.getLocale());
			} else {
				docStartTime = AttendUtil.getMonth(new Date(), 0);
				docEndTime = AttendUtil.getMonth(new Date(), 1);
			}
			docEndTime = AttendUtil.addDate(docEndTime, 1);// 跨天加班数据
			String categoryType = request.getParameter("categoryType");
			if (StringUtil.isNull(categoryType)) {
				categoryType = "attend";
			}
			Integer fdType = null;
			if ("attend".equals(categoryType)) {
				fdType = AttendConstant.FDTYPE_ATTEND;
			} else {
				fdType = AttendConstant.FDTYPE_CUST;
			}
			HQLInfo hqlInfo = new HQLInfo();
			StringBuffer whereBlock = new StringBuffer();
			whereBlock
					.append("sysAttendMain.docCreateTime>=:docStartTime and sysAttendMain.docCreateTime<=:docEndTime");
			whereBlock.append(" and sysAttendMain.docCreator.fdId=:userid");
			if (AttendConstant.FDTYPE_ATTEND == fdType) {
				// 去掉考勤历史表关联，加快查询速度
				whereBlock.append(" and sysAttendMain.fdWorkType is not null ");
				whereBlock.append(" and (sysAttendMain.fdWorkKey is not null or sysAttendMain.workTime is not null)");
			} else if (AttendConstant.FDTYPE_CUST == fdType) {
				whereBlock.append(" and sysAttendMain.fdCategory.fdType=:fdType ");
				hqlInfo.setParameter("fdType", fdType);
			}
			hqlInfo.setParameter("docStartTime", docStartTime);
			hqlInfo.setParameter("docEndTime", docEndTime);
			hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
			}
			whereBlock.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setOrderBy("sysAttendMain.docCreateTime asc");
			List<SysAttendMain> resultList = getServiceImp(request).findList(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(resultList, getServiceImp(request).getModelName());
			if ("attend".equals(categoryType)) {
				array = genMyCalAttendJson(resultList, request);

			} else {
				array = genMyCalCusJson(resultList, request);
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	/**
	 * #65608 去重
	 * 
	 * @param list
	 * @return
	 */
	private List<SysAttendMain> distinctMainList(List<SysAttendMain> list) throws Exception {
		List<SysAttendMain> newList = new ArrayList<SysAttendMain>();
		if (list.isEmpty()) {
			return list;
		}
		if (CategoryUtil.CATEGORY_FD_TYPE_SIGN.equals(list.get(0).getFdHisCategory().getFdType())) {
			return list;
		}
		for (SysAttendMain main : list) {
			SysAttendCategory category = CategoryUtil.getCategoryById(main.getFdHisCategory().getFdId());
			if (category == null || CategoryUtil.CATEGORY_FD_TYPE_SIGN.equals(category.getFdType())) {
				continue;
			}
			if(category.getFdShiftType() == 4 && main.getFdWorkType() == 1){
				continue;
			}

			String workId = main.getWorkTime() == null ? main.getFdWorkKey() : main.getWorkTime().getFdId();
			Integer workType = main.getFdWorkType();
			if (StringUtil.isNull(workId) || workType == null) {
				continue;
			}
			Date date = Boolean.TRUE.equals(main.getFdIsAcross()) ? AttendUtil.getDate(main.getDocCreateTime(), -1)
					: AttendUtil.getDate(main.getDocCreateTime(), 0);
			boolean isFind = false;
			for (SysAttendMain _main : newList) {
				String _workId = _main.getWorkTime() == null ? _main.getFdWorkKey() : _main.getWorkTime().getFdId();
				Integer _workType = _main.getFdWorkType();
				Date _date = Boolean.TRUE.equals(_main.getFdIsAcross())
						? AttendUtil.getDate(_main.getDocCreateTime(), -1)
						: AttendUtil.getDate(_main.getDocCreateTime(), 0);
				if (workId.equals(_workId) && workType.equals(_workType) && AttendUtil.isSameDate(date, _date)) {

					isFind = true;

					if (logger.isDebugEnabled()) {
						logger.debug("distinctMainList:mainid_" + main.getFdId() + " ,_mainid" + _main.getFdId());
					}
					break;
				}
			}
			if (!isFind) {
				newList.add(main);
			}
		}
		return newList;
	}

	/**
	 * PC端考勤日历，格式化数据
	 * 
	 * @param mainList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private JSONArray genMyCalAttendJson(List<SysAttendMain> mainList, HttpServletRequest request) throws Exception {
		JSONArray array = new JSONArray();
		// #65608 去重
		mainList = distinctMainList(mainList);
		// 出差/请假/外出相关信息
		List<String> bussIds = new ArrayList<String>();
		for (SysAttendMain sysAttendMain : mainList) {

			JSONObject data = new JSONObject();
			data.put("fdId", sysAttendMain.getFdId());
			if (Boolean.TRUE.equals(sysAttendMain.getFdIsAcross())) {
				// 把跨天打卡的数据移动到日历的前一天
				Calendar cal = Calendar.getInstance();
				cal.setTime(sysAttendMain.getDocCreateTime());
				cal.add(Calendar.DATE, -1);
				if (sysAttendMain.getFdWorkType() != null && sysAttendMain.getFdWorkType() == 1) {
					cal.set(Calendar.HOUR_OF_DAY, 23);
				}
				if (sysAttendMain.getFdWorkType() != null && sysAttendMain.getFdWorkType() == 0) {
					cal.set(Calendar.HOUR_OF_DAY, 22);
				}
				data.put("start",
						DateUtil.convertDateToString(cal.getTime(), DateUtil.TYPE_DATETIME, request.getLocale()));
				data.put("end",
						DateUtil.convertDateToString(cal.getTime(), DateUtil.TYPE_DATETIME, request.getLocale()));
				data.put("attendTime",
						DateUtil.convertDateToString(sysAttendMain.getDocCreateTime(), DateUtil.TYPE_TIME,
								request.getLocale()) + "("
								+ ResourceUtil.getString("sysAttendMain.fdIsAcross.nextday", "sys-attend") + ")");
			} else {
				Date baseTime = sysAttendMain.getFdBaseWorkTime() == null || (sysAttendMain.getDocCreateTime() != null
						&& AttendUtil.getDate(sysAttendMain.getDocCreateTime(), 0)
								.compareTo(AttendUtil.getDate(sysAttendMain.getFdBaseWorkTime(), 0)) != 0)
										? sysAttendMain.getDocCreateTime() : sysAttendMain.getFdBaseWorkTime();
				data.put("start", DateUtil.convertDateToString(baseTime, DateUtil.TYPE_DATETIME, request.getLocale()));
				data.put("end", DateUtil.convertDateToString(baseTime, DateUtil.TYPE_DATETIME, request.getLocale()));
				data.put("attendTime", DateUtil.convertDateToString(sysAttendMain.getDocCreateTime(),
						DateUtil.TYPE_TIME, request.getLocale()));
			}
			data.put("fdStatus", sysAttendMain.getFdStatus());
			data.put("fdState", sysAttendMain.getFdState());
			data.put("fdOutside", sysAttendMain.getFdOutside());
			data.put("fdWorkType", sysAttendMain.getFdWorkType());
			if (sysAttendMain.getFdHisCategory() != null) {
				SysAttendCategory category = CategoryUtil.getFdCategoryInfo(sysAttendMain);
				data.put("fdCategoryName", category.getFdName());
				data.put("fdCategoryId", category.getFdId());
				data.put("fdOsdReviewType", category.getFdOsdReviewType() == null ? 0 : category.getFdOsdReviewType());
			}
			if (sysAttendMain.getDocCreator() != null) {
				data.put("creator", sysAttendMain.getDocCreator().getFdName());
				data.put("creatorId", sysAttendMain.getDocCreator().getFdId());
			}
			data.put("href",
					"/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId=" + sysAttendMain.getFdId());
			if (sysAttendMain.getFdOffType() != null && Integer.valueOf(5).equals(sysAttendMain.getFdStatus())) {
				data.put("fdOffTypeText", AttendUtil.getLeaveTypeText(sysAttendMain.getFdOffType()));
			}
			// 关联流程信息
			if (sysAttendMain.getFdStatus() != null
					&& AttendUtil.isAttendBuss(sysAttendMain.getFdStatus().toString())) {
				String fdBussSigned = "false";
				if (StringUtil.isNotNull(sysAttendMain.getFdLocation())
						|| StringUtil.isNotNull(sysAttendMain.getFdWifiName())
						|| StringUtil.isNotNull(sysAttendMain.getFdAppName())) {
					fdBussSigned = "true";
				}
				data.put("fdBussSigned", fdBussSigned);
			}
			array.add(data);
			// 关联流程信息格式化
			genBussJson(array, data, bussIds, request);
		}
		return array;
	}

	private void genBussJson(JSONArray array, JSONObject record, List<String> bussIds, HttpServletRequest request)
			throws Exception {
		List<String> orgList = new ArrayList<String>();
		String userId = UserUtil.getKMSSUser().getUserId();
		orgList.add(userId);
		List<String> fdTypes = new ArrayList<String>();
		fdTypes.add("4");
		fdTypes.add("5");
		fdTypes.add("7");// 注意与打卡状态外出值不一致
		Date start = DateUtil.convertStringToDate((String) record.get("start"), DateUtil.TYPE_DATETIME, null);
		Date startTime = AttendUtil.getDate(start, 0);
		Date endTime = AttendUtil.getDate(start, 2);
		List<SysAttendBusiness> businessAllList = getSysAttendBusinessService().findBussList(orgList, startTime,
				endTime, fdTypes);
		if (!businessAllList.isEmpty()) {
			List<SysAttendBusiness> businessList = getSysAttendBusinessService()
					.genUserBusiness(UserUtil.getUser(userId), startTime, businessAllList);
			for (SysAttendBusiness business : businessList) {
				if (bussIds.contains(business.getFdId())) {
					continue;
				}
				JSONObject json = new JSONObject();
				json.put("fdId", record.get("fdId"));
				json.put("fdStatus", business.getFdType() == 7 ? 6 : business.getFdType());
				json.put("start", DateUtil.convertDateToString(AttendUtil.getDate(start, 0), DateUtil.TYPE_DATETIME,
						request.getLocale()));
				json.put("end", record.get("end"));
				json.put("href", business.getDocUrl());
				json.put("fdBussTitle", business.getFdProcessName());
				json.put("fdOutside", record.get("fdOutside"));
				json.put("fdBussHead", "true");
				json.put("fdBussSigned", record.get("fdBussSigned"));
				// 判断日期类型
				String pattent = DateUtil.TYPE_DATE;
				if (business.getFdType() == 5 && business.getFdStatType() == 3) {
					pattent = DateUtil.PATTERN_DATETIME;
				}
				if (business.getFdType() == 4 && (!AttendUtil.isDay(business.getFdBusStartTime())
						|| !AttendUtil.isDay(business.getFdBusEndTime()))) {
					pattent = DateUtil.PATTERN_DATETIME;
				}
				if (business.getFdType() == 7) {
					pattent = DateUtil.TYPE_TIME;
				}
				String fdStartTime = business.getFdBusStartTime() == null ? ""
						: DateUtil.convertDateToString(business.getFdBusStartTime(), pattent, null);

				String fdEndTime = business.getFdBusEndTime() == null ? ""
						: DateUtil.convertDateToString(business.getFdBusEndTime(), pattent, null);
				if (business.getFdType() == 5 && business.getFdStatType() == 2 && StringUtil.isNotNull(fdStartTime)) {
					String am = ResourceUtil.getString("sysAttendMain.buss.am", "sys-attend");
					String pm = ResourceUtil.getString("sysAttendMain.buss.pm", "sys-attend");
					fdStartTime = fdStartTime + " " + (business.getFdStartNoon() == 1 ? am : pm);
					fdEndTime = fdEndTime + " " + (business.getFdEndNoon() == 1 ? am : pm);
				}
				json.put("fdBussStartTime", fdStartTime);
				json.put("fdBussEndTime", fdEndTime);
				array.add(json);
				bussIds.add(business.getFdId());
			}
		}
	}

	/**
	 * PC端签到日历，格式化数据
	 *
	 * @param mainList
	 * @param request
	 * @return
	 */
	private JSONArray genMyCalCusJson(List<SysAttendMain> mainList, HttpServletRequest request) {
		JSONArray array = new JSONArray();
		for (SysAttendMain sysAttendMain : mainList) {
			JSONObject data = new JSONObject();
			data.put("fdId", sysAttendMain.getFdId());
			data.put("start", DateUtil.convertDateToString(sysAttendMain.getDocCreateTime(), DateUtil.TYPE_DATETIME,
					request.getLocale()));
			data.put("end", DateUtil.convertDateToString(sysAttendMain.getDocCreateTime(), DateUtil.TYPE_DATETIME,
					request.getLocale()));
			data.put("attendTime", DateUtil.convertDateToString(sysAttendMain.getDocCreateTime(), DateUtil.TYPE_TIME,
					request.getLocale()));
			data.put("fdStatus", sysAttendMain.getFdStatus());
			if (sysAttendMain.getFdHisCategory() != null) {
				data.put("fdCategoryName", sysAttendMain.getFdHisCategory().getFdName());
				data.put("fdCategoryId", sysAttendMain.getFdHisCategory().getFdId());
			}
			if (sysAttendMain.getDocCreator() != null) {
				data.put("creator", sysAttendMain.getDocCreator().getFdName());
				data.put("creatorId", sysAttendMain.getDocCreator().getFdId());
			}
			data.put("href",
					"/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId=" + sysAttendMain.getFdId());
			array.add(data);
		}
		return array;
	}

	/**
	 * PC端导出原始打卡记录
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportRecordExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-exportRecordExcel", true, getClass());
		KmssMessages messages = new KmssMessages();

		HQLInfo hqlInfo = new HQLInfo();
		changeFindPageHQLInfo(request, hqlInfo);
		// hqlInfo.setOrderBy(
		// "sysAttendMain.docCreator.fdId asc, sysAttendMain.docCreateTime
		// asc");
		hqlInfo.setOrderBy("sysAttendMain.docCreateTime desc");
		// findList不做权限过滤，这里手动处理
		if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
		}
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(500);
		List list = new ArrayList();
		Page page = getServiceImp(request).findPage(hqlInfo);
		list.addAll(page.getList());
		if (page.getTotal() > 1) {
			for (int i = 2; i <= page.getTotal(); i++) {
				Page p = getServiceImp(request).findPage(hqlInfo);
				List dataList = p.getList();
				if (list.size() + dataList.size() > 10000) {
					break;
				}
				list.addAll(dataList);

			}
		}
		// 添加日志信息
		UserOperHelper.logFindAll(list, getServiceImp(request).getModelName());
		String cateType = request.getParameter("cateType");
		String filename = "";
		if ("attend".equals(cateType)) {
			filename = ResourceUtil.getString("sysAttendMain.export.filename.attend", "sys-attend");
		} else {
			filename = ResourceUtil.getString("sysAttendMain.export.filename.custom", "sys-attend");
		}
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition",
				"attachment;filename=\"" + new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls\"");
		ServletOutputStream out = response.getOutputStream();
		try {
			HSSFWorkbook workbook = null;
			if ("attend".equals(cateType)) {
				workbook = getServiceImp(request).buildAttendWorkBook(list);
			} else {
				workbook = getServiceImp(request).buildCustomWorkBook(list);
			}
			workbook.write(out);
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

		TimeCounter.logCurrentTime("Action-exportRecordExcel", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return null;
		}
	}

	/**
	 * 移动端个人日历，获取数据
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward listGroupCalendar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String categoryId = request.getParameter("categoryId");
		if (StringUtil.isNull(categoryId)) {
			logger.error("categoryId不能为空！");
			return null;
		}
		try {
			String startStr = request.getParameter("fdStart");
			String endStr = request.getParameter("fdEnd");
			Date rangeStart = null;
			Date rangeEnd = null;
			if (StringUtil.isNotNull(startStr)) {
				rangeStart = DateUtil.convertStringToDate(startStr, DateUtil.TYPE_DATETIME,
						UserUtil.getKMSSUser().getLocale());
				rangeEnd = DateUtil.convertStringToDate(endStr, DateUtil.TYPE_DATETIME,
						UserUtil.getKMSSUser().getLocale());
			} else {
				rangeStart = AttendUtil.getMonth(new Date(), 0);
				rangeEnd = AttendUtil.getMonth(new Date(), 1);
			}
			rangeEnd = AttendUtil.addDate(rangeEnd, 2);// 跨天加班数据,移动端需再+1
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy("sysAttendMain.docCreateTime");
			hqlInfo.setPageNo(1);
			hqlInfo.setRowSize(500);
			String userId = UserUtil.getKMSSUser().getUserId();
			StringBuffer sb = new StringBuffer();
			SysAttendCategory category = CategoryUtil.getCategoryById(categoryId);
			sb.append("sysOrgElement.fdId=:docCreatorId");
			hqlInfo.setParameter("docCreatorId", userId);
			sb.append(" and sysAttendMain.docCreateTime>=:beginTime and sysAttendMain.docCreateTime<:endTime");
			hqlInfo.setParameter("beginTime", rangeStart);
			hqlInfo.setParameter("endTime", rangeEnd);
			StringBuilder joinBlock = new StringBuilder();
			joinBlock.append(" left join sysAttendMain.docCreator sysOrgElement ");
			// 考勤组时不需根据当前所属考勤组过滤
			if (category == null) {
				sb.append(" and sysAttendMain.fdCategory.fdId=:categoryId");
				hqlInfo.setParameter("categoryId", categoryId);
				category = (SysAttendCategory) getCategoryServiceImp().findByPrimaryKey(categoryId);
			} else {
				sb.append(" and sysAttendMain.fdWorkType is not null ");
				sb.append(" and (sysAttendMain.fdWorkKey is not null or sysAttendMain.workTime is not null)");
			}
			hqlInfo.setJoinBlock(joinBlock.toString());
			sb.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
			hqlInfo.setWhereBlock(sb.toString());
			// 因为已经根据人员过滤了。所以这里不需要在权限过滤
			hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			if (logger.isDebugEnabled()) {
				logger.debug("listGroupCalendar.pageList :" + page.getList());
			}
			// #65608 去重
			List mainList = distinctMainList(page.getList());
			if (logger.isDebugEnabled()) {
				logger.debug("listGroupCalendar.mainList :" + mainList);
			}
			JSONObject result = getServiceImp(request).formatCalendarData(mainList, category);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		SysAttendMainForm mainForm = (SysAttendMainForm) form;
		SysAttendCategory sysAttendCategory = CategoryUtil.getCategoryById(mainForm.getFdCategoryId());
		SysAttendMain sysAttendMain = (SysAttendMain) getServiceImp(request).convertFormToModel(mainForm, null,
				new RequestContext(request));
		request.setAttribute("sysAttendMain", sysAttendMain);
		request.setAttribute("sysAttendCategory", sysAttendCategory);
		if (mainForm.getFdState() != null) {
			// 异常处理数据
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy("sysAttendMainExc.docCreateTime desc");
			hqlInfo.setPageNo(1);
			hqlInfo.setRowSize(SysConfigParameters.getRowSize());
			hqlInfo.setWhereBlock("sysAttendMainExc.fdAttendMain.fdId=:fdAttendMainId");
			hqlInfo.setParameter("fdAttendMainId", mainForm.getFdId());
			SysAttendMainExc excList = (SysAttendMainExc) this.getAttendMainExcServiceImp().findFirstOne(hqlInfo);
			if (excList != null) {
				SysAttendMainExc mainExc = excList;
				SysAttendMainExcForm excForm = new SysAttendMainExcForm();
				excForm = (SysAttendMainExcForm) getAttendMainExcServiceImp().convertModelToForm(excForm, mainExc,
						new RequestContext(request));
				request.setAttribute("sysAttendMainExcForm", excForm);
			}
		}
		if ("5".equals(mainForm.getFdStatus()) && StringUtil.isNotNull(mainForm.getFdOffType())) {
			request.setAttribute("fdOffTypeText",
					AttendUtil.getLeaveTypeText(Integer.parseInt(mainForm.getFdOffType())));
		}
		// 出差/请假/外出相关申请流程信息
		if (AttendUtil.isAttendBuss(mainForm.getFdStatus())) {
			SysAttendMain main = (SysAttendMain) getServiceImp(request).findByPrimaryKey(mainForm.getFdId());
			SysAttendBusiness business = main.getFdBusiness();
			if (business != null) {
				// 判断日期类型
				String pattent = DateUtil.TYPE_DATE;
				if (business.getFdType() == 5 && business.getFdStatType() == 3) {
					pattent = DateUtil.PATTERN_DATETIME;
				}
				String fdStartTime = business.getFdBusStartTime() == null ? ""
						: DateUtil.convertDateToString(business.getFdBusStartTime(), pattent, null);

				String fdEndTime = business.getFdBusEndTime() == null ? ""
						: DateUtil.convertDateToString(business.getFdBusEndTime(), pattent, null);
				if (business.getFdType() == 5 && business.getFdStatType() == 2 && StringUtil.isNotNull(fdStartTime)) {
					String am = ResourceUtil.getString("sysAttendMain.buss.am", "sys-attend");
					String pm = ResourceUtil.getString("sysAttendMain.buss.pm", "sys-attend");
					// 兼容如果不传上下午标志给默认值
					Integer startNoon = (business.getFdStartNoon() == null ? 1 : business.getFdStartNoon());
					Integer endNoon = (business.getFdEndNoon() == null ? 2 : business.getFdEndNoon());

					fdStartTime = fdStartTime + " " + (startNoon == 1 ? am : pm);
					fdEndTime = fdEndTime + " " + (endNoon == 1 ? am : pm);
				}
				request.setAttribute("sysAttendBusiness", business);
				request.setAttribute("fdBusStartTime", fdStartTime);
				request.setAttribute("fdBusEndTime", fdEndTime);
			}
		}
	}

	/**
	 * 用于打卡性能测试 ??? 页面上有调用。把页面注释了
	 * 
	 * @param request
	 * @param response
	 * @param categoryId
	 * @return
	 */
	public List test(HttpServletRequest request, HttpServletResponse response, String categoryId) {
		ActionForward f = null;
		try {
			KmssMessages messages = new KmssMessages();
			try {
				Page page = new Page();
				if (StringUtil.isNotNull(categoryId)) {
					List recordList = getServiceImp(request).findList(categoryId, new Date());
					// 添加日志信息
					UserOperHelper.logFindAll(recordList, getServiceImp(request).getModelName());

					SysAttendCategory category = getSysAttendHisCategoryService().getCategoryById(categoryId);
					// 兼容历史数据
					if (category == null) {
						category = (SysAttendCategory) getCategoryServiceImp().findByPrimaryKey(categoryId, null, true);
					}
					List list = getServiceImp(request).format(recordList, category, new Date());
					page.setList(list);
					page.setPageno(1);
					page.setTotalrows(list.size());
					page.setRowsize(15);
					return page.getList();
				} else {
					return null;
				}

			} catch (Exception e) {
				messages.addError(e);
				logger.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 移动端个人考勤轨迹，获取数据
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewTrail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();

		try {
			String categoryId = request.getParameter("categoryId");
			if (StringUtil.isNull(categoryId)) {
				logger.error("categoryId不能为空！");
			}
			String trailDate = request.getParameter("trailDate");
			if (StringUtil.isNull(trailDate)) {
				logger.error("trailDate不能为空");
			}
			String docCreatorId = request.getParameter("docCreatorId");
			if (StringUtil.isNull(docCreatorId)) {
				docCreatorId = UserUtil.getKMSSUser().getUserId();
			}
			Date trailStartTime = null;
			Date trailEndTime = null;
			if (StringUtil.isNotNull(trailDate)) {
				trailStartTime = DateUtil.convertStringToDate(trailDate, DateUtil.TYPE_DATE,
						UserUtil.getKMSSUser().getLocale());
				Calendar cal = Calendar.getInstance();
				cal.setTime(trailStartTime);
				cal.add(Calendar.DATE, 1);
				trailEndTime = cal.getTime();
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy("sysAttendMain.docCreateTime");
			StringBuffer sb = new StringBuffer();
			sb.append("sysAttendMain.docCreator.fdId=:docCreatorId");
			hqlInfo.setParameter("docCreatorId", docCreatorId);
			sb.append(" and sysAttendMain.docCreateTime>=:beginTime and sysAttendMain.docCreateTime<:endTime");
			hqlInfo.setParameter("beginTime", trailStartTime);
			hqlInfo.setParameter("endTime", trailEndTime);

			SysAttendCategory category = CategoryUtil.getCategoryById(categoryId);
			if (category == null) {
				sb.append(" and sysAttendMain.fdCategory.fdId=:categoryId ");
			} else {
				sb.append(" and sysAttendMain.fdHisCategory.fdId=:categoryId ");
			}
			hqlInfo.setParameter("categoryId", categoryId);
			sb.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
			hqlInfo.setWhereBlock(sb.toString());
			List<SysAttendMain> attendlist = getServiceImp(request).findList(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(attendlist, getServiceImp(request).getModelName());
			JSONArray result = getServiceImp(request).formatTrailData(attendlist, new RequestContext(request));
			request.setAttribute("traildatas", result);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("viewTrail", mapping, form, request, response);
		}

	}

	/**
	 * 移动端团队考勤轨迹，获取数据
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward statTrail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-statTrail", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();
		Number count = 0;
		List<SysAttendMain> recordList = new ArrayList<SysAttendMain>();
		try {
			String fdCategoryId = request.getParameter("fdCategoryId");
			if (StringUtil.isNull(fdCategoryId)) {
				logger.error("fdCategoryId不能为空！");
			}
			String fdDate = request.getParameter("fdDate");
			if (StringUtil.isNull(fdDate)) {
				fdDate = new Date().getTime() + "";
			}
			Date startTime = null;
			Date endTime = null;
			if (StringUtil.isNotNull(fdDate)) {
				startTime = AttendUtil.getDate(new Date(Long.valueOf(fdDate)), 0);
				endTime = AttendUtil.getDate(new Date(Long.valueOf(fdDate)), 1);
			}
			String mydoc = request.getParameter("mydoc");
			SysAttendCategory category = CategoryUtil.getCategoryById(fdCategoryId);
			String docCreatorId = request.getParameter("docCreatorId");
			if (StringUtil.isNull(docCreatorId)) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setSelectBlock("sysAttendMain.docCreator.fdId");
				hqlInfo.setOrderBy("sysAttendMain.docCreateTime asc");
				StringBuffer sb = new StringBuffer();
				sb.append(" sysAttendMain.docCreateTime>=:beginTime and sysAttendMain.docCreateTime<:endTime");
				hqlInfo.setParameter("beginTime", startTime);
				hqlInfo.setParameter("endTime", endTime);
				if (category == null) {
					sb.append(" and sysAttendMain.fdCategory.fdId=:categoryId ");
				} else {
					sb.append(" and sysAttendMain.fdHisCategory.fdId=:categoryId ");
				}
				hqlInfo.setParameter("categoryId", fdCategoryId);
				sb.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
				hqlInfo.setWhereBlock(sb.toString());
				hqlInfo.setRowSize(1);
				Object orgList = getServiceImp(request).findFirstOne(hqlInfo);
				if (orgList != null) {
					docCreatorId = (String) orgList;
				}

			}

			if (StringUtil.isNotNull(docCreatorId)) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setOrderBy("sysAttendMain.docCreateTime asc");
				StringBuffer sb = new StringBuffer();
				sb.append(" sysAttendMain.docCreateTime>=:beginTime and sysAttendMain.docCreateTime<:endTime");
				hqlInfo.setParameter("beginTime", startTime);
				hqlInfo.setParameter("endTime", endTime);
				if (category == null) {
					sb.append(" and sysAttendMain.fdCategory.fdId=:categoryId ");
				} else {
					sb.append(" and sysAttendMain.fdHisCategory.fdId=:categoryId ");
				}
				hqlInfo.setParameter("categoryId", fdCategoryId);
				sb.append(" and sysAttendMain.docCreator.fdId=:docCreatorId");
				hqlInfo.setParameter("docCreatorId", docCreatorId);
				sb.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
				hqlInfo.setWhereBlock(sb.toString());
				hqlInfo.setRowSize(100);
				recordList = getServiceImp(request).findList(hqlInfo);
				// 添加日志信息
				UserOperHelper.logFindAll(recordList, getServiceImp(request).getModelName());
				// 已签到人数
				if ("true".equals(mydoc)) {
					count = 1;
				} else {
					HQLInfo hqlInfo1 = new HQLInfo();
					hqlInfo1.setSelectBlock("count(distinct sysAttendMain.docCreator.fdId)");
					StringBuffer tempWhere = new StringBuffer(
							"sysAttendMain.docCreateTime>=:beginTime and sysAttendMain.docCreateTime<:endTime  and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
					if (category == null) {
						tempWhere.append(" and sysAttendMain.fdCategory.fdId=:categoryId ");
					} else {
						tempWhere.append(" and sysAttendMain.fdHisCategory.fdId=:categoryId ");
					}
					hqlInfo1.setWhereBlock(tempWhere.toString());

					hqlInfo1.setParameter("categoryId", fdCategoryId);
					hqlInfo1.setParameter("beginTime", startTime);
					hqlInfo1.setParameter("endTime", endTime);
					List signedList = getServiceImp(request).findValue(hqlInfo1);

					count = (Number) signedList.get(0);
				}
			}
			request.setAttribute("queryPage", recordList);
			request.setAttribute("fdSignCount", (int) count.longValue());
			request.setAttribute("mydoc", "true".equals(mydoc));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-statTrail", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("statTrail", mapping, form, request, response);
		}

	}

	public ActionForward viewMain(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		List<SysAttendMain> recordList = new ArrayList<SysAttendMain>();
		try {
			JSONArray array = new JSONArray();
			String fdAttendType = request.getParameter("fdAttendType");
			String fdAttendMainId = request.getParameter("fdAttendMainId");
			String fdAttendFdType = request.getParameter("fdAttendFdType");
			String fdcreatorId = request.getParameter("fdcreatorId");
			String fdtime = request.getParameter("fdtime");
			// 2：日统计，3：月统计
			if ("2".equals(fdAttendType) || "3".equals(fdAttendType)) {
				Date StartTime = null;
				Date EndTime = null;
				if (StringUtil.isNotNull(fdtime)) {
					if ("2".equals(fdAttendType)) {
						StartTime = DateUtil.convertStringToDate(fdtime, DateUtil.TYPE_DATE,
								UserUtil.getKMSSUser().getLocale());
						Calendar cal = Calendar.getInstance();
						cal.setTime(StartTime);
						cal.add(Calendar.DATE, 1);
						EndTime = cal.getTime();
					} else {
						Date fdData = DateUtil.convertStringToDate(fdtime, DateUtil.TYPE_DATE,
								UserUtil.getKMSSUser().getLocale());
						EndTime = AttendUtil.getMonth(fdData, 1);
						StartTime = AttendUtil.getMonth(fdData, 0);
					}
				}
				// 根据statid找出作者跟时间
				HQLInfo hqlInfo = new HQLInfo();
				StringBuffer sb = new StringBuffer();
				sb.append("sysAttendMain.docCreator.fdId=:userid");
				if ("2".equals(fdAttendFdType) || "3".equals(fdAttendFdType)) {
					sb.append(" and sysAttendMain.fdStatus=:fdStatus");
					hqlInfo.setParameter("fdStatus", Integer.parseInt(fdAttendFdType));
				}
				if ("4".equals(fdAttendFdType)) {
					sb.append(" and sysAttendMain.fdOutside=:fdOutside");
					hqlInfo.setParameter("fdOutside", true);
				}
				sb.append(" and sysAttendMain.docCreateTime>=:beginTime and sysAttendMain.docCreateTime<:endTime");
				// 去掉考勤历史表关联，加快查询速度
				sb.append(" and sysAttendMain.fdWorkType is not null ");
				sb.append(" and (sysAttendMain.fdWorkKey is not null or sysAttendMain.workTime is not null)");
				hqlInfo.setWhereBlock(sb.toString());
				hqlInfo.setParameter("userid", fdcreatorId);
				hqlInfo.setParameter("beginTime", StartTime);
				hqlInfo.setParameter("endTime", EndTime);
				List<SysAttendMain> resultList = getServiceImp(request).findList(hqlInfo);
				for (Object o : resultList) {
					recordList.add((SysAttendMain) o);
				}
			} else {
				if (StringUtil.isNotNull(fdAttendMainId)) {
					SysAttendMain main = (SysAttendMain) getServiceImp(request).findByPrimaryKey(fdAttendMainId);
					recordList.add(main);
					// 添加日志信息
					UserOperHelper.logFindAll(recordList, getServiceImp(request).getModelName());
				}
			}
			request.setAttribute("queryPage", recordList);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return getActionForward("listMain", mapping, form, request, response);
		}
	}

	/**
	 * 管理员修改考勤记录
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editByAdmin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-editByAdmin", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			SysAttendMainForm mainForm = (SysAttendMainForm) form;
			String fdStatus = mainForm.getFdStatus();
			Integer fdState = mainForm.getFdState();
			if (("0".equals(fdStatus) || "2".equals(fdStatus) || "3".equals(fdStatus)) && fdState != null
					&& fdState == 2) {
				// 异常处理通过显示为正常
				request.setAttribute("__fdStatus", "1");
			} else if ("1".equals(fdStatus) && "true".equals(mainForm.getFdOutside())) {
				// 外勤
				request.setAttribute("__fdStatus", "11");
			} else {
				request.setAttribute("__fdStatus", fdStatus);
			}
			// 请假类型配置
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("sysTimeLeaveRule.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", true);
			List leaveRuleList = getSysTimeLeaveRuleService().findList(hqlInfo);
			if (!leaveRuleList.isEmpty()) {
				request.setAttribute("leaveRuleList", leaveRuleList);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-editByAdmin", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("editByAdmin", mapping, form, request, response);
		}
	}

	/**
	 * 管理员更新考勤记录
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateByAdmin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateByAdmin", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).updateByAdmin((IExtendForm) form, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-updateByAdmin", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			request.setAttribute("redirectto", mapping.getPath() + ".do?method=view&fdId=" + fdId);
			return new ActionForward("/resource/jsp/redirect.jsp");
		}
	}

	public ActionForward isExistRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-isExistRecord", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();
		result.put("count", 0);
		try {
			// 考勤范围
			String targets = request.getParameter("targets");
			if (StringUtil.isNotNull(targets)) {
				String[] targetIds = targets.split(";");
				List idList = Arrays.asList(targetIds);
				List<String> userIds = getSysOrgCoreService().expandToPersonIds(idList);
				if (userIds.isEmpty()) {
					throw new Exception();
				}
				long count = getServiceImp(null).isExistRecord(userIds);
				result.put("count", count);
			}

		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}
		request.setAttribute("lui-source", result);
		TimeCounter.logCurrentTime("Action-isExistRecord", false, getClass());
		return mapping.findForward("lui-source");
	}

	public ActionForward getHPDay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getHPDay", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONArray jsonArr = new JSONArray();
		try {
			String categoryId = getCategoryServiceImp().getAttendCategory(new Date());
			jsonArr = getCategoryServiceImp().getHolidayPatchDay(categoryId);
			request.setAttribute("lui-source", jsonArr);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getHPDay", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	/**
	 * 增加异常设备记录
	 *
	 * @param request
	 * @param fdMainId
	 *            打卡记录Id
	 * @throws Exception
	 */
	private void saveUserDeviceExc(HttpServletRequest request, String fdMainId) throws Exception {
		try {
			String deviceExcFlag = request.getParameter("deviceExcFlag");
			String deviceExcMode = request.getParameter("deviceExcMode");
			if (StringUtil.isNull(deviceExcFlag) || StringUtil.isNull(deviceExcMode)) {
				return;
			}
			SysAttendDeviceExc deviceExc = new SysAttendDeviceExc();
			deviceExc.setDocCreateTime(new Date());
			deviceExc.setDocCreator(UserUtil.getUser());
			deviceExc.setFdClientType(AttendUtil.getDeviceClientType(request));
			deviceExc.setFdDeviceExcMode(deviceExcMode);
			deviceExc.setFdMainId(fdMainId);
			getSysAttendDeviceExcService().add(deviceExc);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

	}

	public ActionForward exportExtendExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-exportExtendExcel", true, getClass());
		KmssMessages messages = new KmssMessages();
		String categoryId = request.getParameter("categoryId");
		String categoryName = request.getParameter("categoryName");
		String filename = "";
		if (StringUtil.isNotNull(categoryName)) {
			filename += categoryName;
		}
		filename += ResourceUtil.getString("sys-attend:sysAttendMain.signData");
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition",
				"attachment;filename=\"" + new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls\"");
		ServletOutputStream out = response.getOutputStream();
		try {
			HSSFWorkbook workbook = getServiceImp(request).buildExtendWorkBook(new RequestContext(request));
			workbook.write(out);
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		TimeCounter.logCurrentTime("Action-exportExtendExcel", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return null;
		}
	}

	/**
	 * 获取出差/请假等信息
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward listAttendBuss(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		JSONArray array = new JSONArray();
		JSONObject result = new JSONObject();
		result.put("status", 0);
		try {
			String categoryId = request.getParameter("fdCategoryId");
			String statDate = request.getParameter("statDate");
			if (StringUtil.isNull(statDate)) {
				statDate = new Date().getTime() + "";
			}
			if (StringUtil.isNotNull(categoryId)) {
				array = getServiceImp(request).findAttendBussinessList(new Date(Long.valueOf(statDate)));
			}
		} catch (Exception e) {
			messages.addError(e);
			result.put("status", 1);
		}
		result.put("datas", array);
		request.setAttribute("lui-source", result);
		return mapping.findForward("lui-source");
	}

	public ActionForward validListDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-validListDetail", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
				orderby += " desc";
			}
			if (StringUtil.isNull(orderby)) {
				orderby = "sysAttendMain.docCreateTime desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			StringBuilder sb = new StringBuilder(" 1=1 ");
			String fdTargetId = request.getParameter("fdTargetId");
			if (StringUtil.isNotNull(fdTargetId)) {
				List<String> targetIds = ArrayUtil.convertArrayToList(fdTargetId.split(";"));
				List<String> personIds = AttendPersonUtil.expandToPersonIds(targetIds);
				if (!personIds.isEmpty()) {
					sb.append(" and " + HQLUtil.buildLogicIN("sysAttendMain.docCreator.fdId", personIds));
					// sb.append(" and
					// sysAttendMain.docCreator.fdIsAvailable=0))");//
					// 某个部门下的离职人员
				} else {
					sb.append(" and sysAttendMain.docCreator.fdId='-1' ");// 不存在记录
				}
			}
			String fdEndTime = request.getParameter("fdEndTime");
			if (StringUtil.isNotNull(fdEndTime)) {
				Date endTime = DateUtil.convertStringToDate(fdEndTime, DateUtil.TYPE_DATE, request.getLocale());
				sb.append(" and ("
						+ "(sysAttendMain.docCreateTime>=:fdStartTime and sysAttendMain.docCreateTime<:fdEndTime and (sysAttendMain.fdIsAcross is null or sysAttendMain.fdIsAcross =0))"
						+ " or (sysAttendMain.docCreateTime>=:fdNextStartTime and sysAttendMain.docCreateTime<:fdNextEndTime and sysAttendMain.fdIsAcross = 1))");
				hqlInfo.setParameter("fdStartTime", AttendUtil.getDate(endTime, 0));
				hqlInfo.setParameter("fdEndTime", AttendUtil.getEndDate(endTime, 0));
				hqlInfo.setParameter("fdNextStartTime", AttendUtil.getDate(endTime, 1));
				hqlInfo.setParameter("fdNextEndTime", AttendUtil.getEndDate(endTime, 1));
			}
			sb.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
			hqlInfo.setWhereBlock(sb.toString());
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-validListDetail", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("validListDetail", mapping, form, request, response);
		}
	}

	public ActionForward originListDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-originListDetail", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
				orderby += " desc";
			}
			if (StringUtil.isNull(orderby)) {
				orderby = "sysAttendSynDing.docCreateTime desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			StringBuilder sb = new StringBuilder(" 1=1 ");
			String fdTargetId = request.getParameter("fdTargetId");
			if (StringUtil.isNotNull(fdTargetId)) {
				List<String> targetIds = ArrayUtil.convertArrayToList(fdTargetId.split(";"));
				// ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService)
				// getBean("sysOrgCoreService");
				List<String> personIds = AttendPersonUtil.expandToPersonIds(targetIds);
				if (!personIds.isEmpty()) {

					sb.append(" and " + HQLUtil.buildLogicIN("sysAttendSynDing.docCreator.fdId", personIds));
				} else {
					sb.append(" and sysAttendSynDing.docCreator.fdId='-1' ");// 不存在记录
				}
			}
			String fdEndTime = request.getParameter("fdEndTime");
			if (StringUtil.isNotNull(fdEndTime)) {
				Date endTime = DateUtil.convertStringToDate(fdEndTime, DateUtil.TYPE_DATE, request.getLocale());
				sb.append(" and sysAttendSynDing.fdWorkDate>=:fdStartTime and sysAttendSynDing.fdWorkDate<:fdEndTime");
				hqlInfo.setParameter("fdStartTime", AttendUtil.getDate(endTime, 0));
				hqlInfo.setParameter("fdEndTime", AttendUtil.getDate(endTime, 1));
			}
			hqlInfo.setWhereBlock(sb.toString());
			Page page = getSysAttendSynDingService().findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-originListDetail", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("originListDetail", mapping, form, request, response);
		}
	}

	public ActionForward statListDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-statListDetail", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			// 结束时间
			String fdEndTime = request.getParameter("fdEndTime");
			// 查询对象
			String fdTargetId = request.getParameter("fdTargetId");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			if (isReserve) {
				orderby += " desc";
			}
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			Page page = new Page();
			if (StringUtil.isNotNull(fdEndTime) && StringUtil.isNotNull(fdTargetId)) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setOrderBy(orderby);
				hqlInfo.setPageNo(pageno);
				hqlInfo.setRowSize(rowsize);
				StringBuilder sb = new StringBuilder(" 1=1 ");
				List<String> targetIds = ArrayUtil.convertArrayToList(fdTargetId.split(";"));
				// ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService)
				// getBean("sysOrgCoreService");
				List<String> personIds = AttendPersonUtil.expandToPersonIds(targetIds);
				if (!personIds.isEmpty()) {
					sb.append(" and " + HQLUtil.buildLogicIN("sysAttendStat.docCreator.fdId", personIds));
				} else {
					sb.append(" and sysAttendStat.docCreator.fdId='-1' ");// 不存在记录
				}
				Date endTime = DateUtil.convertStringToDate(fdEndTime, DateUtil.TYPE_DATE, request.getLocale());
				sb.append(" and sysAttendStat.fdDate>=:fdStartTime and sysAttendStat.fdDate<:fdEndTime");
				hqlInfo.setParameter("fdStartTime", AttendUtil.getDate(endTime, 0));
				hqlInfo.setParameter("fdEndTime", AttendUtil.getEndDate(endTime, 0));
				hqlInfo.setWhereBlock(sb.toString());
				page = getSysAttendStatService().findPage(hqlInfo);
				Map<String, Object> resultMap = getSysAttendStatDetailService().formatStatDetail(page.getList());
				List list = (List) resultMap.get("list");
				request.setAttribute("worksMap", resultMap.get("worksMap"));
				// 添加日志信息
				UserOperHelper.logFindAll(list, getServiceImp(request).getModelName());
				// 班制数
				request.setAttribute("workTimeCount", this.getWorkTimeCount(request));
				// 用于展示考勤组名
				// request.setAttribute("categoryMap",
				// getCategoryServiceImp().getCategoryMap());
				// 有权限操作的考勤组id
				List authCateIds = new ArrayList();
				authCateIds.addAll(getCategoryServiceImp().findCategorysByLeader(UserUtil.getUser(), 1));
				authCateIds.addAll(getCategoryServiceImp().findCateIdsByEditorId(UserUtil.getUser().getFdId(), 1));
				request.setAttribute("authCateIds", authCateIds);
				request.setAttribute("isAdmin", UserUtil.getKMSSUser().isAdmin());
			}
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-statListDetail", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("statListDetail", mapping, form, request, response);
		}

	}

	public ActionForward viewCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		String viewType = request.getParameter("viewType");
		try {
			String fdEndTime = request.getParameter("fdEndTime");
			if (StringUtil.isNull(fdEndTime)) {
				logger.error("fdEndTime不能为空！");
			}
			String fdTargetId = request.getParameter("fdTargetId");
			if (StringUtil.isNull(fdTargetId)) {
				logger.error("fdTargetId不能为空");
			}
			if (StringUtil.isNull(viewType)) {
				throw new UnexpectedRequestException();
			}
			request.setAttribute("fdEndTime", fdEndTime);
			request.setAttribute("fdTargetId", fdTargetId);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward(viewType, mapping, form, request, response);
		}

	}

	private SysAttendStat getSysAttendStat(JSONObject json) {
		String fdCategoryId = (String) json.get("fdCategoryId");
		String fdCategoryName = json.getString("fdCategoryName");
		int fdTotalTime = json.getInt("fdTotalTime");
		int fdLateTime = json.getInt("fdLateTime");
		int fdLeftTime = json.getInt("fdLeftTime");
		boolean fdStatus = json.getInt("fdStatus") == 1 ? true : false;
		boolean fdOutside = json.getInt("fdOutside") == 1 ? true : false;
		boolean fdLate = json.getInt("fdLate") == 1 ? true : false;
		boolean fdLeft = json.getInt("fdLeft") == 1 ? true : false;
		boolean fdMissed = json.getInt("fdMissed") == 1 ? true : false;
		boolean fdAbsent = json.getInt("fdAbsent") == 1 ? true : false;
		boolean fdTrip = json.getInt("fdTrip") == 1 ? true : false;
		boolean fdOff = json.getInt("fdOff") == 1 ? true : false;
		int fdMissedCount = json.getInt("fdMissedCount");
		int fdOutsideCount = json.getInt("fdOutsideCount");
		int fdLateCount = json.getInt("fdLateCount");
		int fdLeftCount = json.getInt("fdLeftCount");
		float fdTripDays = 0;
		if (json.has("fdTripDays") && !StringUtil.isNull(json.getString("fdTripDays"))) {
			fdTripDays = (float) json.getDouble("fdTripDays");
		}
		float fdOffDays = (float) json.getDouble("fdOffDays");
		float fdOffTime = (float) json.getDouble("fdOffTime");
		int fdOverTime = json.getInt("fdOverTime");
		int fdDateType = json.getInt("fdDateType");
		int fdMissedExcCount = json.getInt("fdMissedExcCount");
		int fdLateExcCount = json.getInt("fdLateExcCount");
		int fdLeftExcCount = json.getInt("fdLeftExcCount");
		JSONObject offDaysDetail = json.getJSONObject("fdOffCountDetail");
		String fdOffCountDetail = offDaysDetail.isEmpty() ? null : offDaysDetail.toString();
		String docCreatorHId = json.containsKey("docCreatorHId") ? json.getString("docCreatorHId") : null;
		float fdAbsentDays = (float) json.getDouble("fdAbsentDays");
		float fdOutgoingTime = (float) json.getDouble("fdOutgoingTime");
		fdOutgoingTime = fdOutgoingTime > 24f ? 24f : fdOutgoingTime;
		boolean fdIsNoRecord = json.containsKey("fdIsNoRecord") ? json.getBoolean("fdIsNoRecord") : false;
		Long fdDateLong = json.getLong("fdDate");
		Date fdDate = new Date(fdDateLong);
		SysAttendStat sysAttendStat = new SysAttendStat();
		String fdId = IDGenerator.generateID();
		sysAttendStat.setFdId(fdId);
		sysAttendStat.setFdTotalTime((long) fdTotalTime);
		sysAttendStat.setFdLateTime(fdLateTime);
		sysAttendStat.setFdLeftTime(fdLeftTime);
		sysAttendStat.setFdStatus(fdStatus);
		sysAttendStat.setFdOutside(fdOutside);
		sysAttendStat.setFdCategoryId(fdCategoryId);
		sysAttendStat.setFdCategoryName(fdCategoryName);
		sysAttendStat.setFdLate(fdLate);
		sysAttendStat.setFdLeft(fdLeft);
		sysAttendStat.setFdMissed(fdMissed);
		sysAttendStat.setFdAbsent(fdAbsent);
		sysAttendStat.setFdMissedCount(fdMissedCount);
		sysAttendStat.setFdOutsideCount(fdOutsideCount);
		sysAttendStat.setFdLateCount(fdLateCount);
		sysAttendStat.setFdLeftCount(fdLeftCount);
		sysAttendStat.setFdTrip(fdTrip);
		sysAttendStat.setFdOff(fdOff);
		sysAttendStat.setFdTripDays(fdTripDays);
		sysAttendStat.setFdOffDays(fdOffDays);
		sysAttendStat.setFdOverTime((long) fdOverTime);
		sysAttendStat.setFdDateType(fdDateType);
		sysAttendStat.setFdMissedExcCount(fdMissedExcCount);
		sysAttendStat.setFdLateExcCount(fdLateExcCount);
		sysAttendStat.setFdLeftExcCount(fdLeftExcCount);
		sysAttendStat.setFdOffCountDetail(fdOffCountDetail);
		sysAttendStat.setDocCreatorHId(docCreatorHId);
		sysAttendStat.setFdAbsentDays(fdAbsentDays);
		sysAttendStat.setFdOutgoingTime(fdOutgoingTime);
		sysAttendStat.setFdOffTime((int) fdOffTime);
		sysAttendStat.setFdIsNoRecord(fdIsNoRecord);
		sysAttendStat.setFdDate(fdDate);
		return sysAttendStat;
	}

	// 班制数
	private int getWorkTimeCount(HttpServletRequest request) throws Exception {
		Map<String, List<List<JSONObject>>> userWorksMap = (Map<String, List<List<JSONObject>>>) request
				.getAttribute("worksMap");
		int maxCount = 1;
		for (String key : userWorksMap.keySet()) {
			List<List<JSONObject>> works = userWorksMap.get(key);
			if (works.size() > maxCount) {
				maxCount = works.size();
			}
		}
		return maxCount;
	}

	@Override
	protected ISysAttendMainService getServiceImp(HttpServletRequest request) {
		if (sysAttendMainService == null) {
			sysAttendMainService = (ISysAttendMainService) getBean("sysAttendMainService");
		}
		return sysAttendMainService;
	}

	protected ISysAttendCategoryService getCategoryServiceImp() {
		if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) getBean("sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}

	protected ISysAttendMainExcService getAttendMainExcServiceImp() {
		if (sysAttendMainExcService == null) {
			sysAttendMainExcService = (ISysAttendMainExcService) getBean("sysAttendMainExcService");
		}
		return sysAttendMainExcService;
	}

	public ISysAttendOrgService getSysAttendOrgService() {
		if (sysAttendOrgService == null) {
			sysAttendOrgService = (ISysAttendOrgService) getBean("sysAttendOrgService");
		}
		return sysAttendOrgService;
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	public ISysAttendConfigService getSysAttendConfigService() {
		if (sysAttendConfigService == null) {
			sysAttendConfigService = (ISysAttendConfigService) getBean("sysAttendConfigService");
		}
		return sysAttendConfigService;
	}

	public ISysAttendDeviceService getSysAttendDeviceService() {
		if (sysAttendDeviceService == null) {
			sysAttendDeviceService = (ISysAttendDeviceService) getBean("sysAttendDeviceService");
		}
		return sysAttendDeviceService;
	}

	public ISysAttendDeviceExcService getSysAttendDeviceExcService() {
		if (sysAttendDeviceExcService == null) {
			sysAttendDeviceExcService = (ISysAttendDeviceExcService) getBean("sysAttendDeviceExcService");
		}
		return sysAttendDeviceExcService;
	}

	public ISysTimeLeaveRuleService getSysTimeLeaveRuleService() {
		if (sysTimeLeaveRuleService == null) {
			sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) getBean("sysTimeLeaveRuleService");
		}
		return sysTimeLeaveRuleService;
	}

	public ISysAttendBusinessService getSysAttendBusinessService() {
		if (sysAttendBusinessService == null) {
			sysAttendBusinessService = (ISysAttendBusinessService) getBean("sysAttendBusinessService");
		}
		return sysAttendBusinessService;
	}

	public ISysAttendSynDingService getSysAttendSynDingService() {
		if (sysAttendSynDingService == null) {
			sysAttendSynDingService = (ISysAttendSynDingService) getBean("sysAttendSynDingService");
		}
		return sysAttendSynDingService;
	}

	protected ISysAttendStatDetailService getSysAttendStatDetailService() {
		if (sysAttendStatDetailService == null) {
			sysAttendStatDetailService = (ISysAttendStatDetailService) getBean("sysAttendStatDetailService");
		}
		return sysAttendStatDetailService;
	}

	public ISysAttendStatService getSysAttendStatService() {
		if (sysAttendStatService == null) {
			sysAttendStatService = (ISysAttendStatService) getBean("sysAttendStatService");
		}
		return sysAttendStatService;
	}

	public ISysAttendStatJobService getSysAttendStatJobService() {
		if (sysAttendStatJobService == null) {
			sysAttendStatJobService = (ISysAttendStatJobService) getBean("sysAttendStatJobService");
		}
		return sysAttendStatJobService;
	}

	private ISysAttendSignLogService sysAttendSignLogService;

	public ISysAttendSignLogService getSysAttendSignLogService() {
		if (sysAttendSignLogService == null) {
			sysAttendSignLogService = (ISysAttendSignLogService) getBean("sysAttendSignLogService");
		}
		return sysAttendSignLogService;
	}

	private ISysAttendSignBakService sysAttendSignBakService;

	public ISysAttendSignBakService getSysAttendSignBakService() {
		if (sysAttendSignBakService == null) {
			sysAttendSignBakService = (ISysAttendSignBakService) getBean("sysAttendSignBakService");
		}
		return sysAttendSignBakService;
	}

	/**
	 * 获取打卡记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMySignLog(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String currentDate = request.getParameter("currentDate");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysAttendSignLog.docCreator.fdId=:fdOperatorId2 and sysAttendSignLog.fdBaseDate=:fdBaseDate");
		// 先查询日志记录表，如果日志记录表 查日志备份表
		hqlInfo.setParameter("fdOperatorId2", UserUtil.getUser().getFdId());
		hqlInfo.setOrderBy("sysAttendSignLog.docCreateTime desc");
		Date searchDate = new Date();
		if (StringUtil.isNotNull(currentDate)) {
			searchDate = DateUtil.convertStringToDate(currentDate);
		}
		hqlInfo.setParameter("fdBaseDate", AttendUtil.getDate(searchDate, 0));
		List<SysAttendSignLog> signLogList = this.getSysAttendSignLogService().findList(hqlInfo);
		com.alibaba.fastjson.JSONArray array = new com.alibaba.fastjson.JSONArray();
		if (CollectionUtils.isNotEmpty(signLogList)) {
			for (SysAttendSignLog signLog : signLogList) {
				com.alibaba.fastjson.JSONObject info = new com.alibaba.fastjson.JSONObject();
				info.put("created",
						DateUtil.convertDateToString(signLog.getDocCreateTime(), DateUtil.PATTERN_DATETIME));
				info.put("location",
						"2".equals(signLog.getFdType()) ? signLog.getFdWifiName() : signLog.getFdAddress());
				info.put("type", signLog.getFdType());
				array.add(info);
			}
		} else {
			// 查询历史备份表
			HQLInfo hqlInfoBak = new HQLInfo();
			hqlInfoBak.setWhereBlock(
					"sysAttendSignBak.docCreator.fdId=:fdOperatorId2 and sysAttendSignBak.fdBaseDate=:fdBaseDate");
			// 先查询日志记录表，如果日志记录表 查日志备份表
			hqlInfoBak.setParameter("fdOperatorId2", UserUtil.getUser().getFdId());
			hqlInfoBak.setOrderBy("sysAttendSignBak.docCreateTime desc");
			if (StringUtil.isNotNull(currentDate)) {
				searchDate = DateUtil.convertStringToDate(currentDate);
			}
			hqlInfoBak.setParameter("fdBaseDate", AttendUtil.getDate(searchDate, 0));
			List<SysAttendSignBak> signLogBakList = this.getSysAttendSignBakService().findList(hqlInfoBak);
			if (CollectionUtils.isNotEmpty(signLogBakList)) {
				for (SysAttendSignBak signLog : signLogBakList) {
					com.alibaba.fastjson.JSONObject info = new com.alibaba.fastjson.JSONObject();
					info.put("created",
							DateUtil.convertDateToString(signLog.getDocCreateTime(), DateUtil.PATTERN_DATETIME));
					info.put("location",
							"2".equals(signLog.getFdType()) ? signLog.getFdWifiName() : signLog.getFdAddress());
					info.put("type", signLog.getFdType());
					array.add(info);
				}
			}
		}
		response.getWriter().print(array);
		return null;
	}

	/**
	 * 导出人事考勤集体加班（未用到）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportHrAttendJiTiJiaBanReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-report", false, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String y = request.getParameter("y");
			String m = request.getParameter("m");
			if (y.equals("") || m.equals("")) {
				Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
				ca.setTime(new Date()); // 设置时间为当前时间

				y = ca.get(Calendar.YEAR) + "";
				m = ca.get(Calendar.MONTH) + "";
			}
			List<Map<String, Object>> list = HrAttendJiTiQianKaReport.monthReport(y, m);
			String value = "人事考勤集体加班";
			String filename = value + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet();
			XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
			XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);

			String[] title = { "序号", "人员编号", "姓名", "	加班名称", "平日加班", "周末加班", "法定加班", "是否转加班费"};
			XSSFRow row1 = sheet.createRow(0);
			row1.setHeight((short) (100 * 6));
			for (int i = 0; i < title.length; i++) {
				sheet.setColumnWidth(i, 3000);
				Excel02Util.setCell(row1, titleStyle, i, title[i]);
			}

			if (list != null) {

				for (int j = 0; j < list.size(); j++) {
					XSSFRow row = sheet.createRow(j + 1);
					row.setHeight((short) (100 * 6));
					Map<String, Object> map = list.get(j);
					Excel02Util.setCell(row, bodyStyle, j, j);
				}
			}
			response.setContentType("application/vnd.ms-excel; charset=UTF-8");
			response.setContentType("application/ms-excel");
			filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
			response.setHeader("Cache-Control", "max-age=0");
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
			wb.write(response.getOutputStream());
			return null;
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
	}

	/**
	 * 导出人事 集体假期（未用到）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportHrAttendJiTiJiaQiReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-report", false, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String y = request.getParameter("y");
			String m = request.getParameter("m");
			if (y.equals("") || m.equals("")) {
				Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
				ca.setTime(new Date()); // 设置时间为当前时间

				y = ca.get(Calendar.YEAR) + "";
				m = ca.get(Calendar.MONTH) + "";
			}
			List<Map<String, Object>> list = HrAttendJiTiQianKaReport.monthReport(y, m);
			
			String value = "人事 集体假期";
			String filename = value + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet();
			XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
			XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
										
			String[] title = { "序号", "人员编号", "姓名", "请假名称", "请假天数", "备注"};
			XSSFRow row1 = sheet.createRow(0);
			row1.setHeight((short) (100 * 6));
			for (int i = 0; i < title.length; i++) {
				sheet.setColumnWidth(i, 3000);
				Excel02Util.setCell(row1, titleStyle, i, title[i]);
			}

			if (list != null) {

				for (int j = 0; j < list.size(); j++) {
					XSSFRow row = sheet.createRow(j + 1);
					row.setHeight((short) (100 * 6));
					Map<String, Object> map = list.get(j);
					Excel02Util.setCell(row, bodyStyle, j, j);
//					String[] str = list.get(j);
//					for (int k = 0; k < str.length; k++) {
//						Excel02Util.setCell(row, bodyStyle, k, str[k]);
//					}
				}
			}
			response.setContentType("application/vnd.ms-excel; charset=UTF-8");
			response.setContentType("application/ms-excel");
			filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
			response.setHeader("Cache-Control", "max-age=0");
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
			wb.write(response.getOutputStream());
			return null;
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
	}

	/**
	 * 导出人事 集体签卡（未用到）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportHrAttendJiTiQianKaReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-report", false, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String y = request.getParameter("y");
			String m = request.getParameter("m");
			if (y.equals("") || m.equals("")) {
				Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
				ca.setTime(new Date()); // 设置时间为当前时间

				y = ca.get(Calendar.YEAR) + "";
				m = ca.get(Calendar.MONTH) + "";
			}
			List<Map<String, Object>> list = HrAttendJiTiQianKaReport.monthReport(y, m);
			String value = "人事 集体签卡";
			String filename = value + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet();
			XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
			XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);

			String[] title = { "序号", "人员编号", "姓名", "上班卡", "下班卡", "签卡次数", "签卡原因"};
			XSSFRow row1 = sheet.createRow(0);
			row1.setHeight((short) (100 * 6));
			for (int i = 0; i < title.length; i++) {
				sheet.setColumnWidth(i, 3000);
				Excel02Util.setCell(row1, titleStyle, i, title[i]);
			}

			if (list != null) {

				for (int j = 0; j < list.size(); j++) {
					XSSFRow row = sheet.createRow(j + 1);
					row.setHeight((short) (100 * 6));
					Map<String, Object> map = list.get(j);
					Excel02Util.setCell(row, bodyStyle, j, j);
				}
			}
			response.setContentType("application/vnd.ms-excel; charset=UTF-8");
			response.setContentType("application/ms-excel");
			filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
			response.setHeader("Cache-Control", "max-age=0");
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
			wb.write(response.getOutputStream());
			return null;
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
	}
}
