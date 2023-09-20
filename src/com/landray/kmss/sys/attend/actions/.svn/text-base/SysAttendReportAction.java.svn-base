package com.landray.kmss.sys.attend.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.forms.SysAttendReportForm;
import com.landray.kmss.sys.attend.model.SysAttendReport;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendReportLogService;
import com.landray.kmss.sys.attend.service.ISysAttendReportService;
import com.landray.kmss.sys.attend.service.ISysAttendStatMonthJobService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
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
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 月统计报表 Action
 * 
 * @author
 * @version 1.0 2017-07-27
 */
public class SysAttendReportAction extends ExtendAction {
	protected ISysAttendReportService sysAttendReportService;
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendReportAction.class);

	@Override
	protected ISysAttendReportService getServiceImp(HttpServletRequest request) {
		if (sysAttendReportService == null) {
			sysAttendReportService = (ISysAttendReportService) getBean("sysAttendReportService");
		}
		return sysAttendReportService;
	}

	private ISysAttendConfigService sysAttendConfigService;

	public ISysAttendConfigService getSysAttendConfigServiceImp() {
		if (sysAttendConfigService == null) {
			sysAttendConfigService = (ISysAttendConfigService) getBean("sysAttendConfigService");
		}
		return sysAttendConfigService;
	}

	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;

	public ISysTimeLeaveRuleService getSysTimeLeaveRuleService() {
		if (sysTimeLeaveRuleService == null) {
			sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil.getBean("sysTimeLeaveRuleService");
		}
		return sysTimeLeaveRuleService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendReport.class);
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.createNewForm(mapping, form, request, response);
		SysAttendReportForm sysAttendReportForm = (SysAttendReportForm) form;
		// 设置场所
		if (form instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}

		// 月份
		String fdMonth = request.getParameter("fdMonth");
		if (StringUtil.isNotNull(fdMonth)) {
			sysAttendReportForm.setFdMonth(fdMonth);
		} else {
			sysAttendReportForm.setFdMonth(DateUtil.convertDateToString(AttendUtil.getMonth(new Date(), 0),
					DateUtil.TYPE_DATETIME, request.getLocale()));
		}
		// 部门
		String fdDeptIds = request.getParameter("fdDeptIds");
		String fdDeptNames = request.getParameter("fdDeptNames");
		if (StringUtil.isNotNull(fdMonth)) {
			sysAttendReportForm.setAuthReaderIds(fdDeptIds);
			sysAttendReportForm.setAuthReaderNames(fdDeptNames);
			sysAttendReportForm.setFdDeptIds(fdDeptIds);
			sysAttendReportForm.setFdDeptNames(fdDeptNames);
		} else {
			if (UserUtil.getUser().getFdParent() != null) {
				String orgId = UserUtil.getUser().getFdParent().getFdId();
				String orgName = UserUtil.getUser().getFdParent().getFdName();
				sysAttendReportForm.setAuthReaderIds(orgId);
				sysAttendReportForm.setAuthReaderNames(orgName);
				sysAttendReportForm.setFdDeptIds(orgId);
				sysAttendReportForm.setFdDeptNames(orgName);
			}
		}
		// 是否离职
		String fdIsQuit = request.getParameter("fdIsQuit");
		if (StringUtil.isNotNull(fdMonth)) {
			sysAttendReportForm.setFdIsQuit(fdIsQuit);
		} else {
			sysAttendReportForm.setFdIsQuit("false");
		}
		// 报表名称
		String fdName = request.getParameter("fdName");
		if (StringUtil.isNotNull(fdName)) {
			sysAttendReportForm.setFdName(fdName);
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			sysAttendReportForm.setFdName(cal.get(Calendar.YEAR)
					+ ResourceUtil.getString(request, "sysAttendReport.fdName.fdYearPart", "sys-attend")
					+ (cal.get(Calendar.MONTH) + 1)
					+ ResourceUtil.getString(request, "sysAttendReport.fdName.fdMonthPartNew", "sys-attend")
					+ ResourceUtil.getString(request, "sysAttendReport.report", "sys-attend"));
		}
		// 考勤组
		String fdCategoryIds = request.getParameter("fdCategoryIds");
		String fdCategoryNames = request.getParameter("fdCategoryNames");
		if (StringUtil.isNotNull(fdCategoryIds) && StringUtil.isNotNull(fdCategoryNames)) {
			sysAttendReportForm.setFdCategoryIds(fdCategoryIds);
			sysAttendReportForm.setFdCategoryNames(fdCategoryNames);
		}
		String fdTargetType = request.getParameter("fdTargetType");
		if (StringUtil.isNotNull(fdTargetType)) {
			sysAttendReportForm.setFdTargetType(fdTargetType);
		}
		sysAttendReportForm.setDocCreatorId(UserUtil.getUser().getFdId());
		sysAttendReportForm.setDocCreateTime(
				DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));

		return sysAttendReportForm;
	}

	/**
	 * 查询实时月统计数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("listReportStatus", "processing");
		TimeCounter.logCurrentTime("Action-listReport", true, getClass());
		KmssMessages messages = new KmssMessages();

		try {
			Page page = getServiceImp(request).statList(new RequestContext(request));
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
			String fdShowCols = request.getParameter("fdShowCols");
			if (StringUtil.isNotNull(fdShowCols)) {
				// AI接口新增
				String ai = request.getParameter("ai");
				if (StringUtil.isNotNull(ai) && "true".equals(ai)) {
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setWhereBlock("sysTimeLeaveRule.fdIsAvailable=:fdIsAvailable");
					hqlInfo.setParameter("fdIsAvailable", true);
					List leaveRuleList = getSysTimeLeaveRuleService().findList(hqlInfo);
					if (!leaveRuleList.isEmpty()) {
						String offKeys = "";
						for (int i = 0; i < leaveRuleList.size(); i++) {
							SysTimeLeaveRule sysTimeLeaveRule = (SysTimeLeaveRule) leaveRuleList.get(i);
							offKeys += ";" + sysTimeLeaveRule.getFdName();
						}
						fdShowCols += offKeys;
					}
				}
				fdShowCols=StringEscapeUtils.unescapeHtml(fdShowCols);
				request.setAttribute("fdShowCols", fdShowCols);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		request.getSession().removeAttribute("listReportStatus");
		TimeCounter.logCurrentTime("Action-listReport", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listReport", mapping, form, request, response);
		}
	}
	public void syncAttendDatabase(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/text;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(getServiceImp(request).syncAttendDatabase(new RequestContext(request)).toString());
	}
	public void checkDuplicatedRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/text;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String reportType = request.getParameter("reportType");
		
		JSONObject resp = new JSONObject();
		if(StringUtils.isNotBlank(reportType)){
			if("listReport".equals(reportType)) {
				String listReportStatus = (String) request.getSession().getAttribute("listReportStatus") ;
				if(StringUtils.isNotBlank(listReportStatus)){
					resp.put("status", listReportStatus);
					resp.put("message", ResourceUtil.getString("sysAttend.report.duplicated.request", "sys-attend"));
					response.getWriter().write(resp.toString());
					return;
				}
			}
			else if("statPeriod".equals(reportType)){
				String statPeriodStatus = (String) request.getSession().getAttribute("statPeriodStatus") ;
				if(StringUtils.isNotBlank(statPeriodStatus)){
					resp.put("status", statPeriodStatus);
					resp.put("message", ResourceUtil.getString("sysAttend.report.duplicated.request", "sys-attend"));
					response.getWriter().write(resp.toString());
					return;
				}
			}
			else if("listDetail".equals(reportType)) {
				String listDetailStatus = (String) request.getSession().getAttribute("listDetailStatus") ;
				if(StringUtils.isNotBlank(listDetailStatus)){
					resp.put("status", listDetailStatus);
					resp.put("message", ResourceUtil.getString("sysAttend.report.duplicated.request", "sys-attend"));
					response.getWriter().write(resp.toString());
					return;
				}
			}
		}
		resp.put("status", "done");
		resp.put("message", "");
		response.getWriter().write(resp.toString());
	}

	/**
	 * 生成某个日期区间的统计数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("statPeriodStatus", "processing") ;
		TimeCounter.logCurrentTime("Action-statPeriod", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			ISysAttendStatMonthJobService sysAttendStatMonthJobService = (ISysAttendStatMonthJobService) SpringBeanUtil
					.getBean("sysAttendStatMonthJobService");
			String fdStartTime = request.getParameter("fdStartTime");
			String fdEndTime = request.getParameter("fdEndTime");
			if (StringUtil.isNotNull(fdStartTime) && StringUtil.isNotNull(fdEndTime)) {
				Date fdStartDate = DateUtil.convertStringToDate(fdStartTime, DateUtil.TYPE_DATETIME,
						request.getLocale());
				Date fdEndDate = DateUtil.convertStringToDate(fdEndTime, DateUtil.TYPE_DATETIME, request.getLocale());
				String fdTargetType = request.getParameter("fdTargetType");
				String fdCategoryIds = request.getParameter("fdCategoryIds");
				String fdDeptIds = request.getParameter("fdDeptIds");
				// 按部门统计
				if (StringUtil.isNull(fdTargetType) || "1".equals(fdTargetType)) {
					if (StringUtil.isNotNull(fdDeptIds)) {
						for (String deptId : fdDeptIds.split("[,;]")) {
							ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
							SysOrgElement orgElement = sysOrgCoreService.findByPrimaryKey(deptId);
							if (orgElement != null) {
								sysAttendStatMonthJobService.stat(orgElement, AttendUtil.getDate(fdStartDate, 0),
										AttendUtil.getDate(fdEndDate, 0));
							}
						}
					}
					// 按考勤组统计
				} else if ("2".equals(fdTargetType)) {
					if (StringUtil.isNotNull(fdCategoryIds)) {
						for (String categoryId : fdCategoryIds.split("[,;]")) {
							if (StringUtil.isNotNull(categoryId)) {
								sysAttendStatMonthJobService.stat(categoryId, AttendUtil.getDate(fdStartDate, 0),
										AttendUtil.getDate(fdEndDate, 0));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		request.getSession().removeAttribute("statPeriodStatus");
		TimeCounter.logCurrentTime("Action-statPeriod", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			json.accumulate("result", true);
			request.setAttribute("lui-source", json);
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	/**
	 * 查询某个日期区间的统计数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-listPeriod", true, getClass());
		KmssMessages messages = new KmssMessages();

		try {
			Page page = getServiceImp(request).listPeriod(new RequestContext(request));
			request.setAttribute("queryPage", page);
			String fdShowCols = request.getParameter("fdShowCols");
			if (StringUtil.isNotNull(fdShowCols)) {
				request.setAttribute("fdShowCols", fdShowCols);
			}
			String fdDateType = request.getParameter("fdDateType");
			if (StringUtil.isNotNull(fdDateType)) {
				request.setAttribute("fdDateType", fdDateType);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-listPeriod", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listReport", mapping, form, request, response);
		}
	}

	public ActionForward exportPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-exportPeriod", true, getClass());
		KmssMessages messages = new KmssMessages();

		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		//因为用户没法从线程中获取，封装的查询语句放在外部处理
		HQLInfo hqlInfo = getServiceImp(request).getExportPeriodHqlInfo(new RequestContext(request));

		getSysAttendReportLogService().addSynceMonthPeriodDownReport(hqlInfo,request);
		TimeCounter.logCurrentTime("Action-exportPeriod", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			json.put("data", "error");
		} else {
			json.put("data", "success");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
		/*
		String filename = ResourceUtil.getString("table.sysAttendReport", "sys-attend");
		String fdName = request.getParameter("fdName");
		if (StringUtil.isNotNull(fdName)) {
			filename = fdName;
		}
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition",
				"attachment;filename=\"" + new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls\"");
		ServletOutputStream out = response.getOutputStream();
		try {
			HSSFWorkbook workbook = getServiceImp(request).exportPeriod(new RequestContext(request));
			workbook.write(out);
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);

		} finally {
			out.flush();
			out.close();
		}

		TimeCounter.logCurrentTime("Action-exportPeriod", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return null;
		}*/
	}

	private ISysAttendReportLogService sysAttendReportLogService;
	public ISysAttendReportLogService getSysAttendReportLogService() {
		if (sysAttendReportLogService == null) {
			sysAttendReportLogService = (ISysAttendReportLogService) getBean("sysAttendReportLogService");
		}
		return sysAttendReportLogService;
	}
	/**
	 * 查询实时月统计数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-exportReport", true, getClass());
		KmssMessages messages = new KmssMessages();

//		String filename = ResourceUtil.getString("table.sysAttendReport", "sys-attend");
//		String fdName = request.getParameter("fdName");
//		if (StringUtil.isNotNull(fdName)) {
//			filename = fdName;
//		}
//		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
//		response.addHeader("Content-Disposition",
//				"attachment;filename=\"" + new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls\"");
//		ServletOutputStream out = response.getOutputStream();
//		try {
//			HSSFWorkbook workbook = getServiceImp(request).exportExcel(new RequestContext(request));
//			workbook.write(out);
//		} catch (Exception e) {
//			messages.addError(e);
//			logger.error(e.getMessage(), e);
//		} finally {
//			out.flush();
//			out.close();
//		}
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		//因为用户没法从线程中获取，封装的查询语句放在外部处理
		HQLInfo hqlInfo = getServiceImp(request).getExportHqlInfo(new RequestContext(request));
		getSysAttendReportLogService().addSyncStatMonthDownReport(hqlInfo,request);
		TimeCounter.logCurrentTime("Action-exportReport", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			json.put("data", "error");
		} else {
			json.put("data", "success");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * 保存查询条件，保存为报表数据，最后跳转view页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAndView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-saveAndView", true, getClass());
		KmssMessages messages = new KmssMessages();
		String id = request.getParameter("fdId");
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String method = request.getParameter("method_GET");
			if ("add".equalsIgnoreCase(method)) {
				id = getServiceImp(request).add((IExtendForm) form, new RequestContext(request));
				// 把查询到实时数据保存为报表数据
				getServiceImp(request).saveReportMonth(new RequestContext(request), id);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-saveAndView", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			request.setAttribute("redirectto", mapping.getPath() + ".do?method=view&fdId=" + id);
			return new ActionForward("/resource/jsp/redirect.jsp");
		}
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			SysAttendReportForm reportForm = (SysAttendReportForm) form;
			getServiceImp(request).update((IExtendForm) reportForm, new RequestContext(request));
			// 把查询到实时数据保存为报表数据
			getServiceImp(request).saveReportMonth(new RequestContext(request), reportForm.getFdId());
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).delete(id);
				getServiceImp(request).deleteReportMonth(new RequestContext(request), id);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");

			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids, request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage("sys-authorization:area.batch.operation.info", noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
					getServiceImp(request).delete(authIds);
				}
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
				getServiceImp(request).deleteReportMonth(new RequestContext(request), ids);
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 查询月统计报表数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listReportMonth(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-listReportMonth", true, getClass());
		KmssMessages messages = new KmssMessages();

		try {
			Page page = getServiceImp(request).monthList(new RequestContext(request));
			String fdShowCols = request.getParameter("fdShowCols");
			if (StringUtil.isNotNull(fdShowCols)) {
				request.setAttribute("fdShowCols", fdShowCols);
			}
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-listReportMonth", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listReport", mapping, form, request, response);
		}
	}

	/**
	 * 导出月统计报表数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportMonthReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-exportMonthReport", true, getClass());
		KmssMessages messages = new KmssMessages();
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		//因为用户没法从线程中获取，封装的查询语句放在外部处理
		HQLInfo hqlInfo = getServiceImp(request).getExportHqlInfo(new RequestContext(request));

		getSysAttendReportLogService().addSyncReportDown(hqlInfo,request);
		TimeCounter.logCurrentTime("Action-exportReport", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			json.put("data", "error");
		} else {
			json.put("data", "success");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
		/*String filename = ResourceUtil.getString("table.sysAttendReport", "sys-attend");
		String fdName = request.getParameter("fdName");
		if (StringUtil.isNotNull(fdName)) {
			filename = fdName;
		}
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition",
				"attachment;filename=\"" + new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls\"");
		ServletOutputStream out = response.getOutputStream();
		try {
			RequestContext requestContext=new RequestContext(request);
			HQLInfo hqlInfo =getServiceImp(request).getExportHqlInfo(requestContext);
			HSSFWorkbook workbook = getServiceImp(request).exportExcel(hqlInfo,requestContext);
			workbook.write(out);
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		} finally {
			out.flush();
			out.close();
		}

		TimeCounter.logCurrentTime("Action-exportMonthReport", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return null;
		}*/
	}
}
