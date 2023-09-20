package com.landray.kmss.sys.time.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.forms.SysTimeLeaveDetailForm;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmount;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.sys.time.util.SysTimeLeaveTimeDto;
import com.landray.kmss.sys.time.util.SysTimePersonUtil;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-24
 */
public class SysTimeLeaveDetailAction extends SysTimeImportAction {

	private ISysTimeLeaveDetailService sysTimeLeaveDetailService;
	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;
	@Override
	protected ISysTimeLeaveDetailService
			getServiceImp(HttpServletRequest request) {
		if (sysTimeLeaveDetailService == null) {
			sysTimeLeaveDetailService = (ISysTimeLeaveDetailService) getBean(
					"sysTimeLeaveDetailService");
		}
		return sysTimeLeaveDetailService;
	}

	public ISysTimeLeaveRuleService getSysTimeLeaveRuleService() {
		if (sysTimeLeaveRuleService == null) {
			sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) getBean(
					"sysTimeLeaveRuleService");
		}
		return sysTimeLeaveRuleService;
	}
	private ISysTimeLeaveAmountService sysTimeLeaveAmountService;

	public ISysTimeLeaveAmountService getSysTimeLeaveAmountService() {
		if (sysTimeLeaveAmountService == null) {
			sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) getBean(
					"sysTimeLeaveAmountService");
		}
		return sysTimeLeaveAmountService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		SysTimeLeaveDetailForm detailForm = (SysTimeLeaveDetailForm) form;
		detailForm.setFdOprType("2");
		detailForm.setFdOprStatus("0");
		detailForm.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		detailForm.setDocCreatorId(UserUtil.getUser().getFdId());
		detailForm.setDocCreatorName(UserUtil.getUser().getFdName());
		detailForm.setFdIsUpdateAttend("false");
		List<SysTimeLeaveRule> leaveRuleList = getSysTimeLeaveRuleService()
				.getLeaveRuleList("");
		JSONArray jsonArr = new JSONArray();
		for (SysTimeLeaveRule leaveRule : leaveRuleList) {
			JSONObject json = new JSONObject();
			json.put("leaveName", leaveRule.getFdName());
			json.put("statType", leaveRule.getFdStatType());
			json.put("leaveType", leaveRule.getFdSerialNo());
			jsonArr.add(json);
		}
		request.setAttribute("leaveRuleList", leaveRuleList);
		request.setAttribute("leaveRules", jsonArr.toString().replace("\"", "\\\"").replace("'", "\\\'"));
		if (!leaveRuleList.isEmpty()) {
			SysTimeLeaveRule leaveRule = leaveRuleList.get(0);
			detailForm.setFdLeaveName(leaveRule.getFdName());
			detailForm.setFdStatType(leaveRule.getFdStatType() + "");
			detailForm.setFdLeaveType(leaveRule.getFdSerialNo());
		}
		// 设置场所
		if (form instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}
		return detailForm;
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
			SysTimeLeaveDetailForm leaveForm = (SysTimeLeaveDetailForm) form;
			if(StringUtil.isNull(leaveForm.getFdLeaveTime())) {
				leaveForm.setFdLeaveTime(
						SysTimeUtil.getLeaveDays(
								Integer.valueOf(leaveForm.getFdTotalTime()),
								Integer.valueOf(leaveForm.getFdStatType())));
			}
			String fdId = getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			if (StringUtil.isNotNull(fdId)) {
				// 扣减额度
				getServiceImp(request).updateDeduct(fdId);
				// 更新考勤
				if ("true".equals(leaveForm.getFdIsUpdateAttend())) {
					getServiceImp(request).updateAttend(fdId);
				}
			}
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
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			SysTimeLeaveDetailForm leaveForm = (SysTimeLeaveDetailForm) form;
			leaveForm.setFdLeaveTime(
					SysTimeUtil.getLeaveDays(
							Integer.valueOf(leaveForm.getFdTotalTime()),
							Integer.valueOf(leaveForm.getFdStatType())));
			String fdId = getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			if (StringUtil.isNotNull(fdId)) {
				// 扣减额度
				getServiceImp(request).updateDeduct(fdId);
				// 更新考勤
				if ("true".equals(leaveForm.getFdIsUpdateAttend())) {
					getServiceImp(request).updateAttend(fdId);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return add(mapping, form, request, response);
		}
	}

	public ActionForward deduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deduct", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				getServiceImp(request).updateDeduct(fdId);
				SysTimeLeaveDetail leaveDetail = (SysTimeLeaveDetail) getServiceImp(
						request).findByPrimaryKey(fdId);
				json.accumulate("status", leaveDetail.getFdOprStatus());
				json.accumulate("reason", leaveDetail.getFdOprDesc());
				request.setAttribute("lui-source", json);
			} else {
				throw new NoRecordException();
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deduct", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	public ActionForward deductAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deduct", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String[] ids = request.getParameterValues("List_Selected");
			if (ids != null) {
				getServiceImp(request).updateDeduct(ids);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deduct", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
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

	public ActionForward updateAttendAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateAttendAll", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String[] ids = request.getParameterValues("List_Selected");
			if (ids != null) {
				getServiceImp(request).updateAttend(ids);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-updateAttendAll", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
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
		CriteriaValue cv = new CriteriaValue(request);
		String personId = request.getParameter("personId");
		if (StringUtil.isNotNull(personId)) {
			whereBlock
					.append(" and sysTimeLeaveDetail.fdPerson.fdId=:personId");
			hqlInfo.setParameter("personId", personId);
		}
		String year = request.getParameter("year");
		if (StringUtil.isNotNull(year)) {
			List<String> itemIds =new ArrayList<String>();
			List<String> nextItemIds =new ArrayList<String>();
			if(StringUtil.isNotNull(personId)) {
				itemIds = getLeaveAmountItems(personId, Integer.valueOf(year));
				nextItemIds = getLeaveAmountItems(personId, Integer.valueOf(year)+1);
			}
			whereBlock.append(!itemIds.isEmpty() ? " and ((" : " and ");
			whereBlock.append(
					" ((sysTimeLeaveDetail.docCreateTime>=:startTime and sysTimeLeaveDetail.docCreateTime<:endTime)");
			whereBlock.append(
					"or (sysTimeLeaveDetail.docCreateTime>=:startTime and sysTimeLeaveDetail.docCreateTime<:endTime))");
			if(!itemIds.isEmpty()) {
				if(!nextItemIds.isEmpty()) {
					whereBlock.append(" and ").append(SysTimeUtil.buildLogicNotIN("sysTimeLeaveDetail.sysTimeLeaveAmountItemId", nextItemIds));
				}
				whereBlock.append(")");
				whereBlock.append(" or ").append(HQLUtil.buildLogicIN("sysTimeLeaveDetail.sysTimeLeaveAmountItemId", itemIds));
				whereBlock.append(")");
			}
			//根据请假创建时间进行数据的过滤
			Map<String, Date> sysTimeLeaveAmountCreateAndEndTime = getSysTimeLeaveAmountService().getSysTimeLeaveAmountCreateAndEndTime(Integer.parseInt(year), personId);
			hqlInfo.setParameter("startTime", sysTimeLeaveAmountCreateAndEndTime.get("startTime"));
			hqlInfo.setParameter("endTime", sysTimeLeaveAmountCreateAndEndTime.get("endTime"));
		}
		// 以下筛选属性需要手工定义筛选范围
		String _fdKey = cv.poll("_fdKey");
		String[] _fdDept = cv.polls("_fdDept");
		// 姓名、登录名、手机号或邮箱
		if (StringUtil.isNotNull(_fdKey)) {
			whereBlock
					.append(" and (sysTimeLeaveDetail.fdPerson.fdName like :fdKey");
			whereBlock
					.append(" or sysTimeLeaveDetail.fdPerson.fdLoginName like :fdKey");
			whereBlock
					.append(" or sysTimeLeaveDetail.fdPerson.fdMobileNo like :fdKey");
			whereBlock
					.append(" or sysTimeLeaveDetail.fdPerson.fdEmail like :fdKey)");
			hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
		}
		// 部门
		if (_fdDept != null) {
			List<String> tmpIds = Arrays.asList(_fdDept);
			List orgIds = SysTimePersonUtil.expandToPersonIds(tmpIds);
			if (orgIds != null && !orgIds.isEmpty()) {
				whereBlock.append(" and " + HQLUtil.buildLogicIN(
						"sysTimeLeaveDetail.fdPerson.fdId", orgIds));
			} else {
				whereBlock.append(" and 1=2");
			}
		}
		// 员工状态
		String[] _fdStatus = cv.polls("_fdStatus");
		String fromModule = request.getParameter("fromModule");
		// 加班,请假操作类型(2:加班)
		String fdType = request.getParameter("fdType");
		if (_fdStatus != null && _fdStatus.length > 0 && "hr".equals(fromModule)
				&& SysTimeUtil.moduleExist("/hr/staff")) {
			List<String> fdStatus = new ArrayList<String>();
			boolean isNull = false;
			for (String _fdStatu : _fdStatus) {
				if ("official".equals(_fdStatu)) {
					isNull = true;
				}
				fdStatus.add(_fdStatu);
			}
			String where = "";
			if (isNull) {
				where += " and (hrStaffPersonInfo.fdStatus in (:hrStatus) or hrStaffPersonInfo.fdStatus is null)";
			} else {
				where += " and hrStaffPersonInfo.fdStatus in (:hrStatus)";
			}

			hqlInfo.setFromBlock(
					"com.landray.kmss.sys.time.model.SysTimeLeaveDetail sysTimeLeaveDetail"
							+ ",com.landray.kmss.hr.staff.model.HrStaffPersonInfo as hrStaffPersonInfo");
			whereBlock.append(
					" and hrStaffPersonInfo.fdOrgPerson.fdId=sysTimeLeaveDetail.fdPerson.fdId"
							+ where);

			hqlInfo.setParameter("hrStatus", fdStatus);
		}
		if ("2".equals(fdType)) {
			// 加班明细
			whereBlock.append(" and sysTimeLeaveDetail.fdType=:fdType");
			hqlInfo.setParameter("fdType", 2);
		} else {
			whereBlock.append(
					" and (sysTimeLeaveDetail.fdType is null or sysTimeLeaveDetail.fdType=:fdType)");
			hqlInfo.setParameter("fdType", 1);
		}
		// 人员
		String _fdPerson = cv.poll("_fdPerson");
		if (StringUtil.isNotNull(_fdPerson)) {
			whereBlock
					.append(" and sysTimeLeaveDetail.fdPerson.fdId=:fdPersonId");
			hqlInfo.setParameter("fdPersonId", _fdPerson);
		}
		// 请假类型
		String _fdLeaveName = cv.poll("_fdLeaveName");
		if (StringUtil.isNotNull(_fdLeaveName)) {
			whereBlock.append(" and sysTimeLeaveDetail.fdLeaveName=:leaveName");
			hqlInfo.setParameter("leaveName", _fdLeaveName);
		}
		// 请假开始时间
		String[] _fdStartTime = cv.polls("_fdStartTime");
		if (_fdStartTime != null && _fdStartTime.length > 1) {
			if (StringUtil.isNotNull(_fdStartTime[0])) {
				Date date1 = DateUtil.convertStringToDate(_fdStartTime[0],
						DateUtil.TYPE_DATE, request.getLocale());
				whereBlock.append(
						" and sysTimeLeaveDetail.fdStartTime>=:startFirst");
				hqlInfo.setParameter("startFirst",
						SysTimeUtil.getDate(date1, 0));
			}
			if (StringUtil.isNotNull(_fdStartTime[1])) {
				Date date2 = DateUtil.convertStringToDate(_fdStartTime[1],
						DateUtil.TYPE_DATE, request.getLocale());
				whereBlock.append(
						" and sysTimeLeaveDetail.fdStartTime<:startNext");
				hqlInfo.setParameter("startNext",
						SysTimeUtil.getDate(date2, 1));
			}
		}
		// 请假结束时间
		String[] _fdEndTime = cv.polls("_fdEndTime");
		if (_fdEndTime != null && _fdEndTime.length > 1) {
			if (StringUtil.isNotNull(_fdEndTime[0])) {
				Date date1 = DateUtil.convertStringToDate(_fdEndTime[0],
						DateUtil.TYPE_DATE, request.getLocale());
				whereBlock
						.append(" and sysTimeLeaveDetail.fdEndTime>=:endFirst");
				hqlInfo.setParameter("endFirst",
						SysTimeUtil.getDate(date1, 0));
			}
			if (StringUtil.isNotNull(_fdEndTime[1])) {
				Date date2 = DateUtil.convertStringToDate(_fdEndTime[1],
						DateUtil.TYPE_DATE, request.getLocale());
				whereBlock.append(" and sysTimeLeaveDetail.fdEndTime<:endNext");
				hqlInfo.setParameter("endNext",
						SysTimeUtil.getDate(date2, 1));
			}
		}
		// 扣除方式
		String _fdOprType = cv.poll("_fdOprType");
		if (StringUtil.isNotNull(_fdOprType)) {
			whereBlock.append(" and sysTimeLeaveDetail.fdOprType=:oprType");
			hqlInfo.setParameter("oprType", Integer.parseInt(_fdOprType));
		}
		// 扣除情况
		String _fdOprStatus = cv.poll("_fdOprStatus");
		if (StringUtil.isNotNull(_fdOprStatus)) {
			if ("2".equals(_fdOprStatus)) {
				whereBlock.append(
						" and sysTimeLeaveDetail.fdOprStatus=:oprStatus and sysTimeLeaveDetail.fdCanUpdateAttend!=:canUpdate");
				hqlInfo.setParameter("oprStatus", 2);
				hqlInfo.setParameter("canUpdate", true);
			} else if ("0".equals(_fdOprStatus)) {
				whereBlock.append(
						" and (sysTimeLeaveDetail.fdOprStatus=:oprStatus or sysTimeLeaveDetail.fdCanUpdateAttend=:canUpdate and sysTimeLeaveDetail.fdOprStatus=2)");
				hqlInfo.setParameter("oprStatus", 0);
				hqlInfo.setParameter("canUpdate", true);
			} else {
				whereBlock.append(
						" and sysTimeLeaveDetail.fdOprStatus=:oprStatus");
				hqlInfo.setParameter("oprStatus",
						Integer.parseInt(_fdOprStatus));
			}
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
	}
	
	/**
	 * 获取假期额度的明细idlist
	 * @param personId
	 * @param year
	 * @return
	 */
	private List<String> getLeaveAmountItems(String personId, Integer year) {
		List<String> itemIds =new ArrayList<String>();
		SysTimeLeaveAmount sysTimeLeaveAmount = getSysTimeLeaveAmountService().getLeaveAmount(year, personId);
		if(sysTimeLeaveAmount!=null) {
			List<SysTimeLeaveAmountItem> itemList =  sysTimeLeaveAmount.getFdAmountItems();
			if(itemList!=null && !itemList.isEmpty()) {
				for(SysTimeLeaveAmountItem sysTimeLeaveAmountItem :itemList) {
					itemIds.add(sysTimeLeaveAmountItem.getFdId());
				}
			}
		}
		return itemIds;
	}

	public ActionForward getLeaveCriterion(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getLeaveCriterion", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray jsonArr = new JSONArray();
			List<SysTimeLeaveRule> leaveRuleList = getSysTimeLeaveAmountService()
					.getAllLeaveRule();
			for (SysTimeLeaveRule leaveRule : leaveRuleList) {
				JSONObject json = new JSONObject();
				json.put("text", leaveRule.getFdName());
				json.put("value", leaveRule.getFdName());
				jsonArr.add(json);
			}
			request.setAttribute("lui-source", jsonArr);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getLeaveCriterion", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
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
			String fdLeaveType = request.getParameter("fdLeaveType");

			SysOrgElement docCreator = getSysOrgCoreService()
					.findByPrimaryKey(docCreatorId);
			SysTimeLeaveRule rule = getSysTimeLeaveRuleService()
					.getLeaveRuleByType(Integer.valueOf(fdLeaveType));
			Integer statDayType = rule.getFdStatDayType();

			String pattern = "3".equals(statType) ? DateUtil.TYPE_DATETIME
					: DateUtil.TYPE_DATE;
			Date startTime = DateUtil.convertStringToDate(fdStartTime, pattern,
					request.getLocale());
			Date endTime = DateUtil.convertStringToDate(fdEndTime, pattern,
					request.getLocale());
			Integer startNoon = "2".equals(statType)
					? Integer.valueOf(fdStartNoon) : null;
			Integer endNoon = "2".equals(statType) ? Integer.valueOf(fdEndNoon)
					: null;

			SysTimeLeaveTimeDto dto= SysTimeUtil.getLeaveTimes(docCreator,
					startTime,
					endTime, startNoon, endNoon, statDayType,
					Integer.valueOf(statType),fdLeaveType);
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

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("sys-time:sysTimeLeaveDetail.import.template");
	}

}
