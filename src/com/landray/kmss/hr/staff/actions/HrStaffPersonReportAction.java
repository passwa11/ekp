package com.landray.kmss.hr.staff.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.staff.forms.HrStaffPersonReportForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonReport;
import com.landray.kmss.hr.staff.service.IHrStaffPersonReportService;
import com.landray.kmss.hr.staff.util.ReportResult;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 统计报表
 * 
 * @author 潘永辉 2017-1-17
 * 
 */
public class HrStaffPersonReportAction extends ExtendAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrStaffPersonReportAction.class);

	private IHrStaffPersonReportService hrStaffPersonReportService;

	@Override
	protected IHrStaffPersonReportService getServiceImp(
			HttpServletRequest request) {
		if (hrStaffPersonReportService == null) {
			hrStaffPersonReportService = (IHrStaffPersonReportService) getBean("hrStaffPersonReportService");
		}
		return hrStaffPersonReportService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForm _form = super
				.createNewForm(mapping, form, request, response);
		HrStaffPersonReportForm reportForm = (HrStaffPersonReportForm) _form;
		if (reportForm.getFdAgeRange() == null) {
			if ("reportAge".equals(request.getParameter("fdReportType"))) {
				reportForm.setFdAgeRange("10"); // 年龄的区间默认为10年
			} else {
				reportForm.setFdAgeRange("3"); // 司龄的区间默认为3年
			}
		}
		return _form;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo,
				HrStaffPersonReport.class);

		String fdReportType = request.getParameter("fdReportType");
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1 = 1";
		}
		if (StringUtil.isNotNull(fdReportType)) {
			whereBlock += " and hrStaffPersonReport.fdReportType = :fdReportType";
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("fdReportType", fdReportType);
		}
		
		//ai判断逻辑
		CriteriaValue cv = new CriteriaValue(request);
		String ai = cv.poll("ai");
		if(StringUtil.isNotNull(ai)&&"true".equals(ai)){
			String fdName = cv.poll("fdName");
			if(StringUtil.isNotNull(fdName)){
				if(fdName.startsWith(";")) {
                    fdName = fdName.replaceFirst(";", "");
                }
				if(fdName.indexOf(";")>=0){
					String[] ds = fdName.split("[,;]");
					for(int i=0;i<ds.length;i++){
						if(StringUtil.isNotNull(ds[i])){
							if(i==0){
								whereBlock += " and (hrStaffPersonReport.fdName like '%"+ds[i]+"%'";
							}else if(i==ds.length-1){
								whereBlock += " or hrStaffPersonReport.fdName like '%"+ds[i]+"%')";
							}else{
								whereBlock += " or hrStaffPersonReport.fdName like '%"+ds[i]+"%'";
							}
						}
					}
					hqlInfo.setWhereBlock(whereBlock);
				}
			}
		}
	}

	/**
	 * 统计(图表)
	 */
	public final ActionForward statChart(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-statChart", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ReportResult result = getServiceImp(request).statChart(
					(IExtendForm) form, new RequestContext(request));
			request.setAttribute("result", result);
		} catch (Exception e) {
			messages.addError(e);
			logger.error("", e);
		}

		TimeCounter.logCurrentTime("Action-statChart", false, getClass());
		if (messages.hasError()) {
			request.setAttribute("lui-source", new JSONObject().element("msg",
					ResourceUtil.getString("return.optFailure")));
			return getActionForward("lui-failure", mapping, form, request,
					response);
		} else {
			return getActionForward("barLine", mapping, form, request, response);
		}

	}

	/**
	 * 统计(列表)
	 */
	public final ActionForward statList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-statList", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSON result = getServiceImp(request).statList((IExtendForm) form,
					new RequestContext(request));
			request.setAttribute("lui-source", result);
		} catch (Exception e) {
			messages.addError(e);
			logger.error("", e);
		}
		TimeCounter.logCurrentTime("Action-statList", false, getClass());
		if (messages.hasError()) {
			request.setAttribute("lui-source", new JSONObject().element("msg",
					ResourceUtil.getString("return.optFailure")));
			return getActionForward("lui-failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		String id = request.getParameter("fdId");
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String method = request.getParameter("method_GET");
			String[] _fdStatus = request.getParameterValues("_fdStatus");
			if (_fdStatus != null) {
				HrStaffPersonReportForm reportForm = (HrStaffPersonReportForm) form;
				reportForm.setFdStatus(StringUtil.join(_fdStatus, ","));
			}
			if ("add".equalsIgnoreCase(method)) {
				// 添加日志信息
				if (UserOperHelper.allowLogOper("Service_Add",
						getServiceImp(request).getModelName())) {
					UserOperHelper.setEventType(
							ResourceUtil.getString("button.save"));
				}
				id = getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));
			} else {
				// 添加日志信息
				if (UserOperHelper.allowLogOper("Service_Add",
						getServiceImp(request).getModelName())) {
					UserOperHelper.setEventType(
							ResourceUtil.getString("button.update"));
				}
				getServiceImp(request).update((IExtendForm) form,
						new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			request.setAttribute("redirectto", mapping.getPath()
					+ ".do?method=view&fdId=" + id);
			return new ActionForward("/resource/jsp/redirect.jsp");
		}
	}

	public JSONObject getMobileStat(HttpServletRequest request)
			throws Exception {
		JSONObject json = new JSONObject();
		ReportResult workTimeResult = getServiceImp(request)
				.statOverviewChart("workTime");
		json.accumulate("workTime", workTimeResult.getStatistics());
		int workTimeCount = 0;
		for (Object obj : workTimeResult.getStatistics()) {
			JSONObject jsonObj = (JSONObject) obj;
			workTimeCount+=(int)jsonObj.get("value");
		}
		json.accumulate("workTimeCount", workTimeCount);
		ReportResult staffSex = getServiceImp(request)
				.statOverviewChart("staffSex");
		JSONArray staffSexStatis = staffSex.getStatistics();
		JSONObject staffSexObj = (JSONObject) staffSex.getStatistics().get(0);
		int staffSexCount = (int) staffSexObj.get("unknown")
				+ (int) staffSexObj.get("female")
				+ (int) staffSexObj.get("male");
		staffSexObj.accumulate("staffSexCount", staffSexCount);
		json.accumulate("staffSex", staffSexObj);
		ReportResult staffType = getServiceImp(request)
				.statOverviewChart("staffType");
		json.accumulate("staffType", staffType.getStatistics());
		ReportResult education = getServiceImp(request)
				.statOverviewChart("education");
		json.accumulate("education", education.getStatistics());
		int educationCount = 0;
		for (Object obj : education.getStatistics()) {
			JSONObject jsonObj = (JSONObject) obj;
			educationCount += (int) jsonObj.get("value");
		}
		json.accumulate("educationCount", educationCount);
		return json;
	}
	/**
	 * 概况图表（饼图）
	 */
	public ActionForward overviewChart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-statChart", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ReportResult result = getServiceImp(request).statOverviewChart(
					request.getParameter("type"));
			request.setAttribute("result", result);
			if (StringUtil.isNotNull(request.getParameter("mobile"))) {
				request.setAttribute("statistics", getMobileStat(request));
			}
		} catch (Exception e) {
			messages.addError(e);
			logger.error("", e);
		}
		TimeCounter.logCurrentTime("Action-statChart", false, getClass());
		if (messages.hasError()) {
			request.setAttribute("lui-source", new JSONObject().element("msg",
					ResourceUtil.getString("return.optFailure")));
			return getActionForward("lui-failure", mapping, form, request,
					response);
		} else {
			return getActionForward("overviewChart", mapping, form, request,
					response);
		}

	}

	public ActionForward getHrStaffMobileStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getHrStaffMobileStat", true,
				getClass());
		String ja = "";
		String orgId = request.getParameter("fdOrgId");
		try {
			ja = getServiceImp(request).getStaffMobileStat(orgId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		TimeCounter.logCurrentTime("Action-getHrStaffMobileIndex", false,
				getClass());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(ja.toString());
		return null;
	}
}
