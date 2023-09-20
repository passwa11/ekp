package com.landray.kmss.sys.time.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.forms.SysTimeLeaveResumeForm;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveResumeService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.sys.time.util.SysTimeLeaveTimeDto;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 *
 * @author cuiwj
 * @version 1.0 2019-01-15
 */
public class SysTimeLeaveResumeAction extends ExtendAction {

	private ISysTimeLeaveResumeService sysTimeLeaveResumeService;
	private ISysOrgCoreService sysOrgCoreService;
	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;
	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	public ISysTimeLeaveRuleService getSysTimeLeaveRuleService() {
		if (sysTimeLeaveRuleService == null) {
			sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) getBean(
					"sysTimeLeaveRuleService");
		}
		return sysTimeLeaveRuleService;
	}

	@Override
	protected ISysTimeLeaveResumeService
			getServiceImp(HttpServletRequest request) {
		if (sysTimeLeaveResumeService == null) {
			sysTimeLeaveResumeService = (ISysTimeLeaveResumeService) getBean(
					"sysTimeLeaveResumeService");
		}
		return sysTimeLeaveResumeService;
	}

	private ISysTimeLeaveDetailService sysTimeLeaveDetailService;

	public ISysTimeLeaveDetailService getSysTimeLeaveDetailService() {
		if (sysTimeLeaveDetailService == null) {
			sysTimeLeaveDetailService = (ISysTimeLeaveDetailService) getBean(
					"sysTimeLeaveDetailService");
		}
		return sysTimeLeaveDetailService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		} else {
			whereBlock = new StringBuffer("1 = 1");
		}
		String detailId = request.getParameter("detailId");
		if (StringUtil.isNotNull(detailId)) {
			whereBlock.append(
					" and sysTimeLeaveResume.fdLeaveDetail.fdId=:detailId");
			hqlInfo.setParameter("detailId", detailId);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		SysTimeLeaveResumeForm resumeForm = (SysTimeLeaveResumeForm) form;
		String leaveDetailId = request.getParameter("leaveDetailId");
		if (StringUtil.isNull(leaveDetailId)) {
			throw new UnexpectedRequestException();
		}
		SysTimeLeaveDetail leaveDetail = (SysTimeLeaveDetail) getSysTimeLeaveDetailService()
				.findByPrimaryKey(leaveDetailId);
		resumeForm.setFdPersonId(leaveDetail.getFdPerson().getFdId());
		resumeForm.setFdPersonName(leaveDetail.getFdPerson().getFdName());
		resumeForm.setFdDetailId(leaveDetail.getFdId());
		resumeForm.setFdDetailName(leaveDetail.getFdLeaveName());
		resumeForm.setFdDetailStartTime(DateUtil.convertDateToString(
				leaveDetail.getFdStartTime(), DateUtil.TYPE_DATETIME, null));
		resumeForm.setFdDetailEndTime(DateUtil.convertDateToString(
				leaveDetail.getFdEndTime(), DateUtil.TYPE_DATETIME, null));
		resumeForm.setFdDetailStatType(leaveDetail.getFdStatType() + "");
		resumeForm.setFdDetailStartNoon(leaveDetail.getFdStartNoon() + "");
		resumeForm.setFdDetailEndNoon(leaveDetail.getFdEndNoon() + "");
		resumeForm.setFdOprType("2");
		resumeForm.setFdOprStatus("0");
		resumeForm.setDocCreatorId(UserUtil.getUser().getFdId());
		resumeForm.setDocCreatorName(UserUtil.getUser().getFdName());
		resumeForm.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, null));
		resumeForm.setFdIsUpdateAttend("false");
		request.setAttribute("resumeType", leaveDetail.getFdLeaveType());
		return resumeForm;
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String fdId = getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	@Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		String leaveDetailId = "";
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			SysTimeLeaveResumeForm resumeForm = (SysTimeLeaveResumeForm) form;
			leaveDetailId = resumeForm.getFdDetailId();
			String fdId = getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
			return getActionForward("edit", mapping, form, request, response);
		} else {
			request.setAttribute("redirectto", mapping.getPath()
					+ ".do?method=add&leaveDetailId=" + leaveDetailId);
		}
			return new ActionForward("/resource/jsp/redirect.jsp");
	}

	public ActionForward getLeaveTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getLeaveTime", true, getClass());
		JSONObject result = new JSONObject();
		try {
			String fdStartTime = request.getParameter("fdStartTime");
			String fdEndTime = request.getParameter("fdEndTime");
			String fdStartNoon = request.getParameter("fdStartNoon");
			String fdEndNoon = request.getParameter("fdEndNoon");
			String docCreatorId = request.getParameter("docCreatorId");
			String statType = request.getParameter("fdStatType");
			String fdResumeType = request.getParameter("fdResumeType");

			SysOrgElement docCreator = getSysOrgCoreService().findByPrimaryKey(docCreatorId);
			SysTimeLeaveRule rule = getSysTimeLeaveRuleService()
					.getLeaveRuleByType(Integer.valueOf(fdResumeType));
			Integer statDayType = rule.getFdStatDayType();
			
			String pattern = "3".equals(statType) ? DateUtil.TYPE_DATETIME
					: DateUtil.TYPE_DATE;
			Date startTime = DateUtil.convertStringToDate(fdStartTime, pattern,
					request.getLocale());
			Date endTime = DateUtil.convertStringToDate(fdEndTime, pattern,
					request.getLocale());
			Integer startNoon = "2".equals(statType) ? Integer.valueOf(fdStartNoon):null;
			Integer endNoon = "2".equals(statType) ? Integer.valueOf(fdEndNoon):null;
			SysTimeLeaveTimeDto dto =SysTimeUtil.getLeaveTimes(docCreator,
					startTime,
					endTime, startNoon, endNoon, statDayType,
					Integer.valueOf(statType),fdResumeType);

			Integer leaveTimes =dto.getLeaveTimeMins();
			Float leaveHour = leaveTimes.floatValue() / 60;
			String leaveTimesStr = null;
			if ("3".equals(statType) && dto.getLeaveTimeDays() < 1 ) {
				//不满足一天的情况下用小时显示
				leaveTimesStr = SysTimeUtil.formatHourTimeStr(leaveHour);
			} else {
				//大于1天。
				leaveTimesStr = NumberUtil.roundDecimal(dto.getLeaveTimeDays(),3) + ResourceUtil.getString("date.interval.day");
			}
			result.put("leaveDays", dto.getLeaveTimeDays());
			result.put("leaveTimes", leaveTimes);
			result.put("leaveTimesStr", leaveTimesStr);
			result.put("status", 1);

			request.setAttribute("lui-source", result);
		} catch (Exception e) {
			result.put("status", 0);
			e.printStackTrace();
			request.setAttribute("lui-source", result);
		}


		return getActionForward("lui-source", mapping, form, request,
				response);
	}

	public ActionForward updateAttend(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateAttend", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				getServiceImp(request).updateAttend(fdId);
			} else {
				throw new NoRecordException();
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-updateAttend", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

}
