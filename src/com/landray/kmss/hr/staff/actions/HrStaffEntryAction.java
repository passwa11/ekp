package com.landray.kmss.hr.staff.actions;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.staff.forms.HrStaffEntryForm;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.service.IHrStaffEntryService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.WorkBook;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

public class HrStaffEntryAction extends ExtendAction {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private IHrStaffEntryService hrStaffEntryService;

	@Override
	protected IHrStaffEntryService getServiceImp(HttpServletRequest request) {
		if (hrStaffEntryService == null) {
			hrStaffEntryService = (IHrStaffEntryService) getBean(
					"hrStaffEntryService");
		}
		return hrStaffEntryService;
	}

	public ActionForward newList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 修复 普通用户修改人事流程的时候报错403
		return super.list(mapping, form, request, response);

	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		// 新UED查询方式
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, HrStaffEntry.class);
		String whereBlock = hqlInfo.getWhereBlock();
		String searchKey = cv.poll("searchKey");
		if (StringUtil.isNotNull(searchKey)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(hrStaffEntry.fdName like :searchKey or hrStaffEntry.fdMobileNo like :searchKey)");
			hqlInfo.setParameter("searchKey", "%" + searchKey + "%");
		}
		String fdStatus = request.getParameter("fdStatus");
		if (StringUtil.isNotNull(fdStatus)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"hrStaffEntry.fdStatus=:fdStatus");
			hqlInfo.setParameter("fdStatus", fdStatus);
		}
		String fdName = request.getParameter("fdName");
		if (StringUtil.isNotNull(fdName)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"hrStaffEntry.fdName like :fdName");
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
		}
		String fdMobileNo = request.getParameter("fdMobileNo");
		if (StringUtil.isNotNull(fdMobileNo)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"hrStaffEntry.fdMobileNo=:fdMobileNo");
			hqlInfo.setParameter("fdMobileNo", fdMobileNo);
		}
		// 移动端搜索
		String keyword = request.getParameter("keyword");
		if (StringUtil.isNotNull(keyword)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(hrStaffEntry.fdName like :_fdName or hrStaffEntry.fdMobileNo like:_fdMobileNo)");
			hqlInfo.setParameter("_fdName", "%" + keyword + "%");
			hqlInfo.setParameter("_fdMobileNo", "%" + keyword + "%");
		}
		String fdPlanEntryDeptId = request.getParameter("fdPlanEntryDeptId");
		if (StringUtil.isNotNull(fdPlanEntryDeptId)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"hrStaffEntry.fdPlanEntryDept.fdId=:fdPlanEntryDeptId");
			hqlInfo.setParameter("fdPlanEntryDeptId", fdPlanEntryDeptId);
		}
		String fdOrgPostIds = request.getParameter("fdOrgPostIds");
		if (StringUtil.isNotNull(fdOrgPostIds)) {
			hqlInfo.setJoinBlock(
					"left join hrStaffEntry.fdOrgPosts fdOrgPosts");
			whereBlock = StringUtil
					.linkString(whereBlock,
							" and ", "("
									+ HQLUtil.buildLogicIN("fdOrgPosts.fdId",
											ArrayUtil.asList(
													fdOrgPostIds.split(";")))
									+ ")");
		}
		StringBuffer sb = new StringBuffer(whereBlock);
		sb = HrStaffAuthorityUtil.builtWhereBlock(sb, "hrStaffEntry", hqlInfo);
		hqlInfo.setWhereBlock(sb.toString());
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HrStaffEntryForm entryForm = (HrStaffEntryForm) form;
		super.createNewForm(mapping, entryForm, request, response);
		entryForm.setFdStatus("1");
		return entryForm;
	}

	public ActionForward check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			HrStaffEntryForm entryForm = (HrStaffEntryForm) form;
			getServiceImp(request).updateCheck(entryForm);
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

	/**
	 * <p>通过手机号获取未扫码的待入职员工信息</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward findByMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("update.4m", mapping, form, request, response);
		}
	}

	public ActionForward addEntryMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-addEntryMobile", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForm(mapping, form, request, response);
			if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-addEntryMobile", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("addEntryMobile", mapping, form, request, response);
		}
	}

	/**
	 * <p>匿名保存待入职员工信息</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward saveAnonymousByEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).update((IExtendForm) form, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 入职管理列表页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward entryManageList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-entryManageList", true, getClass());
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
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-entryManageList", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("entryManage", mapping, form, request,
					response);
		}
	}

	public ActionForward editCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-editCheck", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-editCheck", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("editCheck", mapping, form, request,
					response);
		}
	}

	public ActionForward editAbandon(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-editAbandon", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("ids");
			List<HrStaffEntry> list = getServiceImp(request)
					.findByPrimaryKeys(ids.split(";"));
			request.setAttribute("list", list);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-editAbandon", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("editAbandon", mapping, form, request,
					response);
		}
	}

	public ActionForward abandonEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-abandonEntry", true, getClass());
		KmssMessages messages = new KmssMessages();
		boolean status = true;// 执行结果
		try {
			String ids = request.getParameter("ids");
			String reason = request.getParameter("reason");
			String remark = request.getParameter("remark");
			List<HrStaffEntry> list = getServiceImp(request)
					.findByPrimaryKeys(ids.split(";"));
			for (HrStaffEntry entry : list) {
				entry.setFdStatus("3");
				entry.setFdAbandonReason(reason);
				entry.setFdAbandonRemark(remark);
				getServiceImp(request).update(entry);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-abandonEntry", false, getClass());
		if (messages.hasError()) {
			status = false;
		}
		JSONObject json = new JSONObject();
		json.put("status", status);// 执行结果
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 下载模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param responses
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadTemplet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			// 模板名称
			String templetName = ResourceUtil
					.getString("hr-staff:hrStaffEntry.templetName");
			// 构建模板文件
			HSSFWorkbook workbook = getServiceImp(request)
					.buildTempletWorkBook();
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ HrStaffImportUtil.encodeFileName(request, templetName));
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			return null;
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	/**
	 * 导入员工
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fileUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-fileUpload", true, getClass());
		KmssMessage message = null;
		HrStaffEntryForm entryForm = (HrStaffEntryForm) form;
		FormFile file = entryForm.getFile();
		String resultMsg = null;
		boolean state = false;
		if (file == null || file.getFileSize() < 1) {
			resultMsg = ResourceUtil.getString("hrStaff.import.noFile",
					"hr-staff");
		} else {
			try {
				message = getServiceImp(request).saveImportData(entryForm);
				state = message.getMessageType() == KmssMessage.MESSAGE_COMMON;
			} catch (Exception e) {
				message = new KmssMessage(e.getMessage());
				logger.error("", e);
			}
			resultMsg = message.getMessageKey();
		}
		// 保存导入信息
		request.setAttribute("resultMsg", resultMsg);
		// 状态
		request.setAttribute("state", state);
		// 保存导入的类型
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("uploadActionUrl",
				request.getParameter("uploadActionUrl"));
		request.setAttribute("downLoadUrl",
				request.getParameter("downLoadUrl"));
		return getActionForward("hrStaffFileUpload2", mapping, form, request,
				response);
	}

	/**
	 * <p>
	 * PC端新增待入职的保存并新增
	 * </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAddMobile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
            return getActionForward("edit", mapping, form, request, response);
        } else {
            return addEntryMobile(mapping, form, request, response);
        }
	}

	public ActionForward export(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-export", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameter("List_Selected").split(";");
			String fdStatus = request.getParameter("fdStatus");
			WorkBook wb = getServiceImp(request)
					.export(ArrayUtil.convertArrayToList(ids), fdStatus);
			ExcelOutput output = new ExcelOutputImp();
			output.output(wb, response);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-export", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        }
		return null;
	}

	/*
	 * 选择系统账户带出手机号
	 */
	public ActionForward getPersonInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getPersonInfo", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String fdOrgPersonId = request.getParameter("fdOrgPersonId");
			String mobileNo = getServiceImp(request)
					.getPersonInfo(fdOrgPersonId);
				json.put("status", 1);
				json.put("mobileNo", mobileNo);
		} catch (Exception e) {
			json.put("status", 0);
			messages.addError(e);
		}
		response.getWriter().print(json);
		return null;
	}
}
