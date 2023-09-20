package com.landray.kmss.hr.organization.actions;

import java.io.IOException;
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
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.forms.SysOrganizationStaffingLevelForm;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

public class HrOrganizationStaffingLevelAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	protected ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	@Override
	protected ISysOrganizationStaffingLevelService getServiceImp(HttpServletRequest request) {
		if (sysOrganizationStaffingLevelService == null) {
			sysOrganizationStaffingLevelService = (ISysOrganizationStaffingLevelService) getBean(
					"sysOrganizationStaffingLevelService");
		}
		return sysOrganizationStaffingLevelService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo, SysOrganizationStaffingLevel.class);
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
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
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

	public ActionForward updateStaffingLevelPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				IExtendForm rtnForm = null;
				IBaseModel model = getServiceImp(request).findByPrimaryKey(fdId, null, true);
				if (model != null) {
					rtnForm = getServiceImp(request).convertModelToForm((IExtendForm) form, model,
							new RequestContext(request));
					SysOrganizationStaffingLevelForm levelForm = (SysOrganizationStaffingLevelForm) rtnForm;
					levelForm.setMethod_GET("edit");
				}
				if (rtnForm == null) {
					throw new NoRecordException();
				}
				request.setAttribute(getFormName(rtnForm, request), rtnForm);

			} else {
				ActionForm newForm = createNewForm(mapping, form, request, response);
				SysOrganizationStaffingLevelForm levelForm = (SysOrganizationStaffingLevelForm) newForm;
				levelForm.setMethod_GET("add");
				if (newForm != form) {
					request.setAttribute(getFormName(newForm, request), newForm);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("editStaffingLevelPage", mapping, form, request, response);
		}
	}

	public ActionForward deleteStaffingLevelPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("fdIds");
			List<SysOrganizationStaffingLevel> list = getServiceImp(request).findByPrimaryKeys(ids.split(";"));
			request.setAttribute("list", list);

		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("deleteStaffingLevelPage", mapping, form, request, response);
		}
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
	public ActionForward downloadTemplet(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			// 模板名称
			String templetName = ResourceUtil.getString("sys-organization:sysOrganizationStaffingLevel.templetName");
			// 构建模板文件
			HSSFWorkbook workbook = ((ISysOrganizationStaffingLevelService) getServiceImp(request))
					.buildTempletWorkBook();

			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + SysOrgUtil.encodeFileName(request, templetName));
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			return null;
		} catch (IOException e) {
			messages.addError(e);
		}

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
	 * 导入
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fileUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-fileUpload", true, getClass());
		KmssMessage message = null;

		SysOrganizationStaffingLevelForm staffingLevelForm = (SysOrganizationStaffingLevelForm) form;
		FormFile file = staffingLevelForm.getFile();
		String resultMsg = null;
		boolean state = false;
		if (file == null || file.getFileSize() < 1) {
			resultMsg = ResourceUtil.getString("sysOrganizationStaffingLevel.import.noFile", "sys-organization");
		} else {
			try {
				message = ((ISysOrganizationStaffingLevelService) getServiceImp(request))
						.saveImportData(staffingLevelForm);
				state = message.getMessageType() == KmssMessage.MESSAGE_COMMON;
			} catch (Exception e) {
				message = new KmssMessage(e.getMessage());
				logger.error("", e);
			}
			resultMsg = message.getMessageKey();
		}
		// 保存导入信息
		request.setAttribute("resultMsg", resultMsg);
		request.setAttribute("state", state);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("<script>parent.callback('" + resultMsg + "');</script>");
		return null;
	}

	public ActionForward dialogData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String keyWord = request.getParameter("q._keyword");
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			if (StringUtil.isNotNull(keyWord)) {
				String where = "";
				where += "(sysOrganizationStaffingLevel.fdName like :fdName";
				hqlInfo.setParameter("fdName", "%" + keyWord + "%");
				where += ")";
				hqlInfo.setWhereBlock(where);
			}
			HQLHelper.by(request).buildHQLInfo(hqlInfo, SysOrganizationStaffingLevel.class);
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("dialogData");
		}
	}

}
