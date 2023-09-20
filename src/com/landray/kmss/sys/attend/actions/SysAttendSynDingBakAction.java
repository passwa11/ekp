package com.landray.kmss.sys.attend.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.dao.SQLInfo;
import com.landray.kmss.sys.attend.forms.SysAttendSynDingBakForm;
import com.landray.kmss.sys.attend.model.SysAttendSynDingBak;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingBakService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;

public class SysAttendSynDingBakAction extends ExtendAction {
	private ISysAttendSynDingBakService sysAttendSynDingBakService;

	@Override
	public IBaseService getServiceImp(HttpServletRequest request) {
		if (sysAttendSynDingBakService == null) {
			sysAttendSynDingBakService = (ISysAttendSynDingBakService) getBean(
					"sysAttendSynDingBakService");
		}
		return sysAttendSynDingBakService;
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
	public ActionForward list(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
				orderby += " desc";
			}
			SQLInfo sqlInfo = new SQLInfo();
			sqlInfo.setOrderBy(orderby);
			sqlInfo.setPageNo(pageno);
			sqlInfo.setRowSize(rowsize);
			changeFindPageSQLInfo(request, sqlInfo);
			Page page = ((ISysAttendSynDingBakService) getServiceImp(request))
					.findPage(sqlInfo);
			/*
			 * request.setAttribute("categoryMap",
			 * getCategoryMap(page.getList()));
			 * request.setAttribute("personMap", getPersonMap(page.getList()));
			 */
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("list", mapping, form, request, response);
		}
	}


	private void changeFindPageSQLInfo(HttpServletRequest request,
			SQLInfo sqlInfo) throws Exception {
		String year = request.getParameter("year");
		if (StringUtil.isNotNull(year)) {
			sqlInfo.setTableName("sys_attend_syn_ding_bak_" + year);
			sqlInfo.setEntityClass(SysAttendSynDingBak.class);
		} else {
			throw new NoRecordException();
		}
		CriteriaValue cv = new CriteriaValue(request);
		StringBuffer sb = new StringBuffer(" 1=1 ");
		// 筛选项，时间
		String[] fdUserCheckTime = cv.polls("fdUserCheckTime");
		if (fdUserCheckTime != null && fdUserCheckTime.length > 1) {
			if (StringUtil.isNotNull(fdUserCheckTime[0])) {
				Date startTime = DateUtil.convertStringToDate(
						fdUserCheckTime[0],
						DateUtil.TYPE_DATE, request.getLocale());
				sb.append(
						" and fd_user_check_time>=:startTime");
				sqlInfo.setParameter("startTime",
						SysTimeUtil.getDate(startTime, 0));
			}
			if (StringUtil.isNotNull(fdUserCheckTime[1])) {
				Date endTime = DateUtil.convertStringToDate(fdUserCheckTime[1],
						DateUtil.TYPE_DATE, request.getLocale());
				sb.append(" and fd_user_check_time<:endTime");
				sqlInfo.setParameter("endTime",
						SysTimeUtil.getDate(endTime, 1));
			}
		}

		String[] fdStatusArr = cv.polls("fdStatus");
		if (fdStatusArr != null && fdStatusArr.length > 0) {
			List<String> fdStatusList = ArrayUtil
					.convertArrayToList(fdStatusArr);
			if (!fdStatusList.isEmpty()) {
				sb.append(" and (1=2");
				if (fdStatusList.contains("1")) {
					sb.append(
							" or fd_time_result='Normal' ");
				}
				if (fdStatusList.contains("11")) {
					sb.append(
							" or (fd_time_result='Normal' and fd_location_result='Outside') ");
				}
				if (fdStatusList.contains("2")) {
					sb.append(
							" or fd_time_result='Late' ");
				}
				if (fdStatusList.contains("3")) {
					sb.append(
							" or fd_time_result='Early' ");
				}
				if (fdStatusList.contains("4")) {
					sb.append(
							" or fd_time_result='Trip' ");
				}
				if (fdStatusList.contains("5")) {
					sb.append(
							" or fd_time_result='Leave' ");
				}
				if (fdStatusList.contains("6")) {
					sb.append(
							" or fd_time_result='Outgoing' ");
				}
				if (fdStatusList.contains("7")) {
					sb.append(
							" or (fd_invalid_record_type='Other'  or fd_invalid_record_type='Security') ");
				}
				sb.append(")");
			}
		}
		// 筛选项，人员
		String docCreator = cv.poll("fdPersonId");
		if (StringUtil.isNotNull(docCreator)) {
			sb.append(" and fd_person_id=:docCreatorId");
			sqlInfo.setParameter("docCreatorId", docCreator);
		}
		String docCreatorDept = cv.poll("fdPersonDept");
		if (StringUtil.isNotNull(docCreatorDept)) {
			List<String> deptIds = new ArrayList<String>();
			deptIds.add(docCreatorDept);
			List personIds = getSysOrgCoreService()
					.expandToPostPersonIds(deptIds);
			if (!personIds.isEmpty()) {
				sb.append(" and " + HQLUtil.buildLogicIN(
						"fd_person_id", personIds));
			} else {
				sb.append(" and 1=2");
			}
		}
		// 排序
		String orderBy = sqlInfo.getOrderBy();
		if (StringUtil.isNotNull(orderBy)) {
			if (orderBy.contains("fdUserCheckTime")) {
				if (orderBy.contains("desc")) {
					sqlInfo.setOrderBy("fd_user_check_time desc");
				} else {
					sqlInfo.setOrderBy("fd_user_check_time");
				}
			} else {
				sqlInfo.setOrderBy(null);
			}
		}
		// 因 历史原始考勤记录是分表的，无法直接过滤，在后台直接判断权限条件过滤
		if (!UserUtil.getKMSSUser().isAdmin()
				|| !UserUtil
						.checkRole("ROLE_SYSATTEND_HISTORY_ORIGINAL_READER")) {
			sb.append(
					" and fd_person_id='" + UserUtil.getUser().getFdId() + "'");
		}
		sqlInfo.setWhereBlock(sb.toString());
	}

	@Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		SysAttendSynDingBakForm sysAttendSynDingBakForm = (SysAttendSynDingBakForm) super.createNewForm(
				mapping, form, request,
				response);
		((ISysAttendSynDingBakService) getServiceImp(request)).initFormSetting(
				(IExtendForm) form,
				new RequestContext(request));
		return sysAttendSynDingBakForm;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			String year = request.getParameter("year");
			if (StringUtil.isNotNull(year)) {
				Map<String, Object> params = new HashMap<>();
				params.put("entity", SysAttendSynDingBak.class);
				params.put("tableName", "sys_attend_syn_ding_bak_" + year);
				SysAttendSynDingBak model = ((ISysAttendSynDingBakService) getServiceImp(
						request))
						.findByPrimaryKey(id, params);
				if (model != null) {
					rtnForm = getServiceImp(request).convertModelToForm(
							(IExtendForm) form, model,
							new RequestContext(request));
				}
			} else {
				throw new NoRecordException();
			}
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

	/**
	 * 获取历史记录年份
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRemainYears(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getRemainYears", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONArray jsonArr = new JSONArray();
		try {
			List<String> recordList = ((ISysAttendSynDingBakService) getServiceImp(
					request)).getRemainYears();
			for (String year : recordList) {
				jsonArr.add(year);
			}
			request.setAttribute("lui-source", jsonArr);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getRemainYears", false, getClass());
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

}
