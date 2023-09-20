package com.landray.kmss.hr.organization.actions;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.organization.service.IHrOrgFileAuthorService;
import com.landray.kmss.hr.organization.service.IHrOrganizationConPostService;
import com.landray.kmss.hr.organization.util.HrOrgAuthorityUtil;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.hr.organization.validator.HrOrgConPostValidator;
import com.landray.kmss.hr.staff.forms.HrStaffTrackRecordForm;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.hr.staff.service.IHrStaffFileAuthorService;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.WorkBook;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrOrganizationConPostAction extends ExtendAction {
	private IHrStaffFileAuthorService hrStaffFileAuthorService;

	private static IHrOrgFileAuthorService hrOrgFileAuthorService;

	protected static IHrOrgFileAuthorService getHrOrgFileAuthorServiceImp() {
		if (hrOrgFileAuthorService == null) {
			hrOrgFileAuthorService = (IHrOrgFileAuthorService) SpringBeanUtil
					.getBean("hrOrgFileAuthorService");
		}
		return hrOrgFileAuthorService;
	}

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private IHrOrganizationConPostService hrOrganizationConPostService;

	@Override
	public IHrOrganizationConPostService getServiceImp(HttpServletRequest request) {
		if (hrOrganizationConPostService == null) {
			hrOrganizationConPostService = (IHrOrganizationConPostService) getBean("hrOrganizationConPostService");
		}
		return hrOrganizationConPostService;
	}


	private IHrStaffTrackRecordService hrStaffTrackRecordService;


	public IHrStaffTrackRecordService getHrStaffTrackRecordService() {
		if (hrStaffTrackRecordService == null) {
			hrStaffTrackRecordService = (IHrStaffTrackRecordService) getBean(
					"hrStaffTrackRecordService");
		}
		return hrStaffTrackRecordService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {

		CriteriaValue cv = new CriteriaValue(request);
		// 以下筛选属性需要手工定义筛选范围
		String _fdKey = cv.poll("_fdKey");
		String _fdDept = cv.poll("_fdDept");
		String fdStatu = cv.poll("fdStatus");

		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		} else {
			whereBlock = new StringBuffer("1 = 1");
		}
		// 姓名、登录名、手机号或邮箱
		if (StringUtil.isNotNull(_fdKey)) {
			// 查询组织机构人员信息
			HQLInfo _hqlInfo = new HQLInfo();
			_hqlInfo.setSelectBlock("sysOrgPerson.fdId");
			_hqlInfo.setWhereBlock(
					"sysOrgPerson.fdName like :fdKey or sysOrgPerson.fdLoginName like :fdKey or sysOrgPerson.fdMobileNo like :fdKey or sysOrgPerson.fdEmail like :fdKey");
			_hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
			List<String> ids = getSysOrgPersonService().findValue(_hqlInfo);
			whereBlock.append(
					" and ((fdPersonInfo.fdName like :fdKey or fdPersonInfo.fdMobileNo like :fdKey or fdPersonInfo.fdEmail like :fdKey)");
			if (!ids.isEmpty()) {
				whereBlock.append(" or fdPersonInfo.fdId in (:ids)");
				hqlInfo.setParameter("ids", ids);
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
		}

		if (StringUtil.isNotNull(_fdDept)) {
			whereBlock.append(" and fdHrOrgDept.fdId = :deptId");
			hqlInfo.setParameter("deptId", _fdDept);
		}

		if (StringUtil.isNotNull(fdStatu)) {
			whereBlock.append(" and fdStatus = :fdStatus");
			hqlInfo.setParameter("fdStatus", fdStatu);
		}

		// 员工状态
		String[] _fdStatus = cv.polls("_fdStatus");
		if (_fdStatus != null && _fdStatus.length > 0) {
			List<String> fdStatus = new ArrayList<String>();
			boolean isNull = false;
			for (String _fdStatu : _fdStatus) {
				if ("official".equals(_fdStatu)) {
					isNull = true;
				}
				fdStatus.add(_fdStatu);
			}
			whereBlock.append(" and (fdPersonInfo.fdStatus in (:fdStatus)");
			if (isNull) {
				whereBlock.append(" or fdPersonInfo.fdStatus is null");
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdStatus", fdStatus);
		}

		//任职记录类型
		String fdType = cv.poll("fdType");
		if (StringUtil.isNotNull(fdType)) {
			whereBlock.append(" and fdType = :fdType");
			hqlInfo.setParameter("fdType", fdType);
		}
		//员工
		String fdPerson = cv.poll("fdPerson");
		if (StringUtil.isNotNull(fdPerson)) {
			whereBlock.append(" and fdPersonInfo.fdId = :fdPerson");
			hqlInfo.setParameter("fdPerson", fdPerson);
		}
		// 如果是管理员或者机构管理员，直接放行
		if (!(UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_ADMIN")
				|| UserUtil.getKMSSUser().isAdmin())) {
			whereBlock = HrOrgAuthorityUtil.builtWhereBlock(whereBlock,
					"hrStaffTrackRecord", hqlInfo);
			}
		hqlInfo.setWhereBlock(whereBlock.toString());
		request.setAttribute("fdType", fdType);
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
				orderby = " fdCreateTime desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getHrStaffTrackRecordService().findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
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

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return super.createNewForm(mapping, form, request, response);
    }

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getHrStaffTrackRecordService().add((IExtendForm) form, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
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
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getHrStaffTrackRecordService().update((IExtendForm) form, new RequestContext(request));
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
			//返回按钮
			IExtendForm mainForm = (IExtendForm) form;
			String fdModelId = mainForm.getFdId();
			String fdModelName = mainForm.getModelClass().getName();
			SysDictModel model = SysDataDict.getInstance().getModel(fdModelName);
			if (model != null && !StringUtil.isNull(model.getUrl())) {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton("button.back", formatModelUrl(fdModelId, model.getUrl()), false).save(request);
			}

			return getActionForward("success", mapping, form, request, response);
		}
	}

	private String formatModelUrl(String value, String url) {
		if (StringUtil.isNull(url)) {
			return null;
		}
		Pattern p = Pattern.compile("\\$\\{([^}]+)\\}");
		Matcher m = p.matcher(url);
		while (m.find()) {
			String property = m.group(1);
			try {
				url = StringUtil.replace(url, "${" + property + "}", value);
			} catch (Exception e) {
			}
		}
		return url;
	}

	public ActionForward updateConPostPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				IExtendForm rtnForm = null;
				IBaseModel model = getHrStaffTrackRecordService().findByPrimaryKey(fdId, null, true);
				if (model != null) {
					rtnForm = getHrStaffTrackRecordService().convertModelToForm((IExtendForm) form, model,
							new RequestContext(request));
					HrStaffTrackRecordForm hrStaffTrackRecordForm = (HrStaffTrackRecordForm) rtnForm;
					hrStaffTrackRecordForm.setMethod_GET("edit");
				}
				if (rtnForm == null) {
					throw new NoRecordException();
				}
				request.setAttribute(getFormName(rtnForm, request), rtnForm);

			} else {
				ActionForm newForm = createNewForm(mapping, form, request, response);
				HrStaffTrackRecordForm hrStaffTrackRecordForm = (HrStaffTrackRecordForm) newForm;
				hrStaffTrackRecordForm.setMethod_GET("add");
				if (newForm != form) {
					request.setAttribute(getFormName(newForm, request), newForm);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("updateConPostPage", mapping, form, request, response);
		}
	}

	public ActionForward delConPostPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("fdIds");
			List<HrStaffTrackRecord> list = getHrStaffTrackRecordService().findByPrimaryKeys(ids.split(";"));
			List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			for (HrStaffTrackRecord conPost : list) {
				map = new HashMap<String, String>();
				map.put("fdId", conPost.getFdId());
				String fdName = null;
				if (conPost.getFdPersonInfo() != null) {
					fdName = conPost.getFdPersonInfo().getFdName();
				}
				map.put("fdName", fdName);
				listMap.add(map);
			}
			request.setAttribute("list", listMap);

		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("delConPostPage", mapping, form, request, response);
		}
	}

	/**
	 * <p>下载导入模板</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward downloadTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			WorkBook wb = getServiceImp(request).buildTemplateWorkbook(request);
			ExcelOutput output = new ExcelOutputImp();
			output.output(wb, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
		return null;
	}

	/**
	 * <p>导入</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward importConPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject importResult = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			importResult.put("otherErrors", new JSONArray());
			HrStaffTrackRecordForm mainForm = (HrStaffTrackRecordForm) form;
			FormFile file = mainForm.getFile();
			importResult = getServiceImp(request).addImportData(file.getInputStream(), request.getLocale());
		} catch (KmssRuntimeException e) {
			e.printStackTrace();
			String msg = "未知异常！";
			if (e.getCause() instanceof ParseException) {
				msg = "类型转换失败！";
			}
			importResult.put("hasError", 1);
			importResult.getJSONArray("otherErrors").add(msg);
		} catch (Exception e) {
			e.printStackTrace();
			importResult.put("hasError", 1);
			importResult.put("importMsg", ResourceUtil.getString("hr-organization:hr.organization.import.fail"));
			importResult.getJSONArray("otherErrors").add(e.getMessage());
		}
		String result = HrOrgUtil.replaceCharacter(importResult.toString());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("<script>parent.callback(" + result + ");</script>");
		return null;
	}

	public ActionForward finishConPostPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("fdIds");
			List<HrStaffTrackRecord> list = getHrStaffTrackRecordService().findByPrimaryKeys(ids.split(";"));
			List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			for (HrStaffTrackRecord conPost : list) {
				map = new HashMap<String, String>();
				map.put("fdId", conPost.getFdId());
				map.put("fdName", conPost.getFdPersonInfo().getFdName());
				listMap.add(map);
			}
			request.setAttribute("list", listMap);

		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("finishConPostPage", mapping, form, request, response);
		}
	}

	public ActionForward finishConPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-finishConPost", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				String[] ids = id.split(";");
				getServiceImp(request).updateFinishConPostByIds(ids);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-finishConPost", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * <p>效验是否有当前部门新建兼岗权限</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void validateConPostRole(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			boolean result = true;
			String fdHrOrgDeptId = request.getParameter("fdHrOrgDeptId");

			result = HrOrgConPostValidator.validateConPostRole(fdHrOrgDeptId);
			json.put("result", result);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
	}

	/**
	 * <p>检查同部门同岗位兼岗唯一</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void checkUnique(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			boolean result = true;
			String fdHrOrgDeptId = request.getParameter("fdHrOrgDeptId");
			String fdHrOrgPostId = request.getParameter("fdHrOrgPostId");
			String fdPersonInfoId = request.getParameter("fdPersonInfoId");
			String fdId = request.getParameter("fdId");

			result = getHrStaffTrackRecordService().checkUnique(fdId, fdPersonInfoId, fdHrOrgDeptId, fdHrOrgPostId,
					null, "2");
			json.put("result", result);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
	}
}
