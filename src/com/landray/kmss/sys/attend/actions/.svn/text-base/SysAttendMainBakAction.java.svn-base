package com.landray.kmss.sys.attend.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.dao.SQLInfo;
import com.landray.kmss.sys.attend.forms.SysAttendMainBakForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendMainBak;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainBakService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-01
 */
public class SysAttendMainBakAction extends ExtendAction {

	private ISysAttendMainBakService sysAttendMainBakService;
	private ISysOrgCoreService sysOrgCoreService;
	private ISysAttendCategoryService sysAttendCategoryService;
	private ISysAttendBusinessService sysAttendBusinessService;

	@Override
	protected ISysAttendMainBakService
			getServiceImp(HttpServletRequest request) {
		if (sysAttendMainBakService == null) {
			sysAttendMainBakService = (ISysAttendMainBakService) getBean(
					"sysAttendMainBakService");
		}
		return sysAttendMainBakService;
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	public ISysAttendCategoryService getSysAttendCategoryService() {
		if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) getBean(
					"sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}

	public ISysAttendBusinessService getSysAttendBusinessService() {
		if (sysAttendBusinessService == null) {
			sysAttendBusinessService = (ISysAttendBusinessService) getBean(
					"sysAttendBusinessService");
		}
		return sysAttendBusinessService;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			String year = request.getParameter("year");
			if (StringUtil.isNotNull(year)) {
				Map<String, Object> params = new HashMap<>();
				params.put("entity", SysAttendMainBak.class);
				params.put("tableName", "sys_attend_main_bak_" + year);
				SysAttendMainBak model = getServiceImp(request)
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
		SysAttendMainBakForm bakForm = (SysAttendMainBakForm) rtnForm;
		if (StringUtil.isNotNull(bakForm.getFdCategoryId())) {
			request.setAttribute("sysAttendCategory",
					getSysAttendCategoryService()
							.findByPrimaryKey(bakForm.getFdCategoryId()));
		}
		if (StringUtil.isNotNull(bakForm.getDocCreatorId())) {
			request.setAttribute("docCreator", getSysOrgCoreService()
					.findByPrimaryKey(bakForm.getDocCreatorId()));
		}
		if (StringUtil.isNotNull(bakForm.getDocAlterorId())) {
			request.setAttribute("docAlteror", getSysOrgCoreService()
					.findByPrimaryKey(bakForm.getDocAlterorId()));
		}
		if (StringUtil.isNotNull(bakForm.getFdBusinessId())) {
			request.setAttribute("fdBussiness", getSysAttendBusinessService()
					.findByPrimaryKey(bakForm.getFdBusinessId()));
		}
		if ("5".equals(bakForm.getFdStatus())
				&& StringUtil.isNotNull(bakForm.getFdOffType())) {
			request.setAttribute("fdOffTypeText", AttendUtil.getLeaveTypeText(
					Integer.parseInt(bakForm.getFdOffType())));
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
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
			Page page = getServiceImp(request).findPage(sqlInfo);
			request.setAttribute("categoryMap", getCategoryMap(page.getList()));
			request.setAttribute("personMap", getPersonMap(page.getList()));
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

	private Map<String, Object> getCategoryMap(List list) {
		Map<String, Object> cateMap = new HashMap<String, Object>();
		try {
			List<String> cateIds = new ArrayList<String>();
			for (Object obj : list) {
				String fdCategoryId = null;
				if (obj instanceof String) {
					fdCategoryId = (String) obj;
				} else {
					SysAttendMainBak mainBak = (SysAttendMainBak) obj;
					fdCategoryId = mainBak.getFdCategoryId();
				}
				if (StringUtil.isNotNull(fdCategoryId)) {
					cateIds.add(fdCategoryId);
				}
			}
			List<SysAttendCategory> cateList = new ArrayList<SysAttendCategory>();
			if (!cateIds.isEmpty()) {
				cateList = getSysAttendCategoryService().findList(
						HQLUtil.buildLogicIN("sysAttendCategory.fdId", cateIds),
						"");
			}
			if (!cateList.isEmpty()) {
				for (SysAttendCategory cate : cateList) {
					cateMap.put(cate.getFdId(), cate);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cateMap;
	}

	private Map<String, Object> getPersonMap(List list) {
		Map<String, Object> personMap = new HashMap<String, Object>();
		try {
			List<String> personIds = new ArrayList<String>();
			for (Object obj : list) {
				String docCreatorId = null;
				if (obj instanceof String) {
					docCreatorId = (String) obj;
				} else {
					SysAttendMainBak mainBak = (SysAttendMainBak) obj;
					docCreatorId = mainBak.getDocCreatorId();
				}
				if (StringUtil.isNotNull(docCreatorId)) {
					personIds.add(docCreatorId);
				}
			}
			List<SysOrgElement> personList = new ArrayList<SysOrgElement>();
			if (!personIds.isEmpty()) {
				personList = getSysOrgCoreService().findByPrimaryKeys(
						personIds.toArray(new String[personIds.size()]));
			}
			if (!personList.isEmpty()) {
				for (SysOrgElement person : personList) {
					personMap.put(person.getFdId(), person);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personMap;
	}

	private void changeFindPageSQLInfo(HttpServletRequest request,
			SQLInfo sqlInfo) throws Exception {
		String year = request.getParameter("year");
		if (StringUtil.isNotNull(year)) {
			sqlInfo.setTableName("sys_attend_main_bak_" + year);
			sqlInfo.setEntityClass(SysAttendMainBak.class);
		} else {
			throw new NoRecordException();
		}
		CriteriaValue cv = new CriteriaValue(request);
		StringBuffer sb = new StringBuffer("1=1 ");

		// 筛选项，时间
		String[] docCreateTime = cv.polls("docCreateTime");
		if (docCreateTime != null && docCreateTime.length > 1) {
			if (StringUtil.isNotNull(docCreateTime[0])) {
				Date startTime = DateUtil.convertStringToDate(docCreateTime[0],
						DateUtil.TYPE_DATE, request.getLocale());
				sb.append(
						" and doc_create_time>=:startTime");
				sqlInfo.setParameter("startTime",
						SysTimeUtil.getDate(startTime, 0));
			}
			if (StringUtil.isNotNull(docCreateTime[1])) {
				Date endTime = DateUtil.convertStringToDate(docCreateTime[1],
						DateUtil.TYPE_DATE, request.getLocale());
				sb.append(" and doc_create_time<:endTime");
				sqlInfo.setParameter("endTime",
						SysTimeUtil.getDate(endTime, 1));
			}
		}

		// 筛选项，人员
		String docCreator = cv.poll("docCreator");
		if (StringUtil.isNotNull(docCreator)) {
			sb.append(" and doc_creator_id=:docCreatorId");
			sqlInfo.setParameter("docCreatorId", docCreator);
		}

		// 筛选项，部门
		String docCreatorDept = cv.poll("docCreatorDept");
		if (StringUtil.isNotNull(docCreatorDept)) {
			List orgIds = getSysOrgCoreService().expandToPersonIds(
					Arrays.asList(new String[] { docCreatorDept }));
			if (!orgIds.isEmpty()) {
				sb.append(" and " + HQLUtil
						.buildLogicIN("doc_creator_id", orgIds));
			} else {
				sb.append(" and 1=2");
			}
		}

		// 筛选项，考勤组名
		String categoryName = cv.poll("fdCategoryName");
		if (StringUtil.isNotNull(categoryName)) {
			List cateIds = getSysAttendCategoryService().findValue(
					"sysAttendCategory.fdId",
					"sysAttendCategory.fdName like '%" + categoryName + "%'",
					"");
			if (!cateIds.isEmpty()) {
				sb.append(" and " + HQLUtil.buildLogicIN(
						"fd_category_id", cateIds));
			} else {
				sb.append(" and 1=2");
			}
		}

		// 筛选项，处理状态
		String[] fdState = cv.polls("fdState");
		if (fdState != null && fdState.length > 0) {
			List<String> fdStateList = ArrayUtil.convertArrayToList(fdState);
			if (!fdStateList.isEmpty()) {
				sb.append(" and (1=2");
				if (fdStateList.contains("0")) {
					sb.append(" or fd_state is null");
				}
				sb.append(
						" or " + HQLUtil.buildLogicIN("fd_state", fdStateList));
				sb.append(" )");
			}
		}

		// 筛选项，签到状态
		String[] fdStatusArr = cv.polls("fdStatus");
		if (fdStatusArr != null && fdStatusArr.length > 0) {
			List<String> fdStatusList = ArrayUtil
					.convertArrayToList(fdStatusArr);
			if (!fdStatusList.isEmpty()) {
				sb.append(" and (1=2");
				// 正常
				if (fdStatusList.contains("1")) {
					sb.append(
							" or fd_status=1 and (fd_outside=0 or fd_outside is null)"
									+ " or fd_state=2 and (fd_status=0 or fd_status=2 or fd_status=3)");
				}
				// 外勤
				if (fdStatusList.contains("11")) {
					sb.append(
							" or fd_status=1 and fd_outside=1");
				}
				// 缺卡
				if (fdStatusList.contains("0")) {
					sb.append(
							" or fd_status=0 and (fd_state is null or fd_state!=2)");
				}
				// 迟到
				if (fdStatusList.contains("2")) {
					sb.append(
							" or fd_status=2 and (fd_state is null or fd_state!=2)");
				}
				// 早退
				if (fdStatusList.contains("3")) {
					sb.append(
							" or fd_status=3 and (fd_state is null or fd_state!=2)");
				}
				// 出差
				if (fdStatusList.contains("4")) {
					sb.append(
							" or fd_status=4");
				}
				// 请假
				if (fdStatusList.contains("5")) {
					sb.append(
							" or fd_status=5");
				}
				// 外出
				if (fdStatusList.contains("6")) {
					sb.append(
							" or fd_status=6");
				}
				sb.append(" )");
			}
		}

		// 只显示今天前的记录
		sb.append(" and doc_create_time <= :docCreateTime");
		sqlInfo.setParameter("docCreateTime",
				AttendUtil.getDate(new Date(), 1));

		// 不显示缺卡记录
		String noMissed = request.getParameter("noMissed");
		if ("true".equals(noMissed)) {
			sb.append(" and fd_status != 0");
		}
		sb.append(" and (doc_status=0 or doc_status is null)");
		// 排序
		String orderBy = sqlInfo.getOrderBy();
		if (StringUtil.isNotNull(orderBy)) {
			if (orderBy.contains("docCreateTime")) {
				if (orderBy.contains("desc")) {
					sqlInfo.setOrderBy("doc_create_time desc");
				} else {
					sqlInfo.setOrderBy("doc_create_time");
				}
			} else {
				sqlInfo.setOrderBy(null);
			}
		}
		sqlInfo.setWhereBlock(sb.toString());
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		StringBuffer sb = new StringBuffer("1=1 ");

		// 筛选项，人员
		String docCreator = cv.poll("docCreator");
		if (StringUtil.isNotNull(docCreator)) {
			sb.append(" and sysAttendMainBak.docCreatorId=:docCreatorId");
			hqlInfo.setParameter("docCreatorId", docCreator);
		}

		// 筛选项，部门
		String docCreatorDept = cv.poll("docCreatorDept");
		if (StringUtil.isNotNull(docCreatorDept)) {
			List orgIds = getSysOrgCoreService().expandToPersonIds(
					Arrays.asList(new String[] { docCreatorDept }));
			if (!orgIds.isEmpty()) {
				sb.append(" and " + HQLUtil
						.buildLogicIN("sysAttendMainBak.docCreatorId", orgIds));
			} else {
				sb.append(" and 1=2");
			}
		}

		// 筛选项，考勤组名
		String categoryName = cv.poll("fdCategoryName");
		if (StringUtil.isNotNull(categoryName)) {
			List cateIds = getSysAttendCategoryService().findValue(
					"sysAttendCategory.fdId",
					"sysAttendCategory.fdName like '%" + categoryName + "%'",
					"");
			if (!cateIds.isEmpty()) {
				sb.append(" and " + HQLUtil.buildLogicIN(
						"sysAttendMainBak.fdCategoryId", cateIds));
			} else {
				sb.append(" and 1=2");
			}
		}

		// 筛选项，处理状态
		String[] fdState = cv.polls("fdState");
		if (fdState != null && fdState.length > 0) {
			List<String> fdStateList = ArrayUtil.convertArrayToList(fdState);
			if (!fdStateList.isEmpty()) {
				sb.append(" and (1=2");
				if (fdStateList.contains("0")) {
					sb.append(" or sysAttendMainBak.fdState is null");
				}
				sb.append(" or "
						+ HQLUtil.buildLogicIN("sysAttendMainBak.fdState",
								fdStateList));
				sb.append(" )");
			}
		}

		// 筛选项，签到状态
		String[] fdStatusArr = cv.polls("fdStatus");
		if (fdStatusArr != null && fdStatusArr.length > 0) {
			List<String> fdStatusList = ArrayUtil
					.convertArrayToList(fdStatusArr);
			if (!fdStatusList.isEmpty()) {
				sb.append(" and (1=2");
				// 正常
				if (fdStatusList.contains("1")) {
					sb.append(
							" or sysAttendMainBak.fdStatus=1 and sysAttendMainBak.fdOutside=false"
									+ " or sysAttendMainBak.fdState=2 and (sysAttendMainBak.fdStatus=0 or sysAttendMainBak.fdStatus=2 or sysAttendMainBak.fdStatus=3)");
				}
				// 外勤
				if (fdStatusList.contains("11")) {
					sb.append(
							" or sysAttendMainBak.fdStatus=1 and sysAttendMainBak.fdOutside=true");
				}
				// 缺卡
				if (fdStatusList.contains("0")) {
					sb.append(
							" or sysAttendMainBak.fdStatus=0 and (sysAttendMainBak.fdState is null or sysAttendMainBak.fdState!=2)");
				}
				// 迟到
				if (fdStatusList.contains("2")) {
					sb.append(
							" or sysAttendMainBak.fdStatus=2 and (sysAttendMainBak.fdState is null or sysAttendMainBak.fdState!=2)");
				}
				// 早退
				if (fdStatusList.contains("3")) {
					sb.append(
							" or sysAttendMainBak.fdStatus=3 and (sysAttendMainBak.fdState is null or sysAttendMainBak.fdState!=2)");
				}
				// 出差
				if (fdStatusList.contains("4")) {
					sb.append(
							" or sysAttendMainBak.fdStatus=4");
				}
				// 请假
				if (fdStatusList.contains("5")) {
					sb.append(
							" or sysAttendMainBak.fdStatus=5");
				}
				// 外出
				if (fdStatusList.contains("6")) {
					sb.append(
							" or sysAttendMainBak.fdStatus=6");
				}
				sb.append(" )");
			}
		}
		// 只显示今天前的记录
		sb.append(" and sysAttendMainBak.docCreateTime <= :docCreateTime");
		hqlInfo.setParameter("docCreateTime",
				AttendUtil.getDate(new Date(), 1));

		// 不显示缺卡记录
		String noMissed = request.getParameter("noMissed");
		if ("true".equals(noMissed)) {
			sb.append(" and sysAttendMainBak.fdStatus != 0");
		}
		sb.append(
				" and (sysAttendMainBak.docStatus=0 or sysAttendMainBak.docStatus is null)");
		hqlInfo.setWhereBlock(sb.toString());
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendMainBak.class);
	}

	public ActionForward getRemainYears(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getRemainYears", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONArray jsonArr = new JSONArray();
		try {
			IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
			String recordSql = "select distinct(fd_year) from sys_attend_main_baklog order by fd_year";
			List<String> recordList = baseDao.getHibernateSession().createNativeQuery(recordSql).list();
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
