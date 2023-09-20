package com.landray.kmss.hr.organization.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant.AuthCheck;
import com.landray.kmss.constant.SysAuthConstant.CheckType;
import com.landray.kmss.hr.organization.forms.HrOrganizationGradeForm;
import com.landray.kmss.hr.organization.model.HrOrganizationGrade;
import com.landray.kmss.hr.organization.service.IHrOrganizationGradeService;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.util.HQLHelper;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrOrganizationGradeAction extends ExtendAction {

    private IHrOrganizationGradeService hrOrganizationGradeService;

	@Override
	public IHrOrganizationGradeService getServiceImp(HttpServletRequest request) {
        if (hrOrganizationGradeService == null) {
            hrOrganizationGradeService = (IHrOrganizationGradeService) getBean("hrOrganizationGradeService");
        }
        return hrOrganizationGradeService;
    }

    @Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrOrganizationGrade.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.organization.util.HrOrganizationUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.organization.model.HrOrganizationGrade.class);
        com.landray.kmss.hr.organization.util.HrOrganizationUtil.buildHqlInfoModel(hqlInfo, request);
		hqlInfo.setCheckParam(CheckType.AuthCheck, AuthCheck.SYS_READER);
    }

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrOrganizationGradeForm hrOrganizationGradeForm = (HrOrganizationGradeForm) super.createNewForm(mapping, form, request, response);
        ((IHrOrganizationGradeService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return hrOrganizationGradeForm;
    }

	public ActionForward updateGradePage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
					HrOrganizationGradeForm gradeForm = (HrOrganizationGradeForm) rtnForm;
					gradeForm.setMethod_GET("edit");
				}
				if (rtnForm == null) {
					throw new NoRecordException();
				}
				request.setAttribute(getFormName(rtnForm, request), rtnForm);

			} else {
				ActionForm newForm = createNewForm(mapping, form, request, response);
				HrOrganizationGradeForm gradeForm = (HrOrganizationGradeForm) newForm;
				gradeForm.setMethod_GET("add");
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
			return getActionForward("updateGradePage", mapping, form, request, response);
		}
	}

	public void checkCodeUnique(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			String fdCode = request.getParameter("fdCode");
			String fdId = request.getParameter("fdId");
			boolean result = getServiceImp(request).checkCodeUnique(fdCode, fdId);
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

	public void checkNameUnique(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			String fdName = request.getParameter("fdName");
			String fdId = request.getParameter("fdId");
			boolean result = getServiceImp(request).checkNameUnique(fdName, fdId);
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


	public ActionForward deleteGradePage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("fdIds");
			List<HrOrganizationGrade> list = getServiceImp(request).findByPrimaryKeys(ids.split(";"));
			request.setAttribute("list", list);

		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("deleteGradePage", mapping, form, request, response);
		}
	}

	/**
	 * <p>导入职等</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward importGrade(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject importResult = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			importResult.put("otherErrors", new JSONArray());
			HrOrganizationGradeForm mainForm = (HrOrganizationGradeForm) form;
			FormFile file = mainForm.getFile();
			importResult = getServiceImp(request).addImportData(file.getInputStream(), request.getLocale());
		} catch (Exception e) {
			e.printStackTrace();
			importResult.put("hasError", 1);
			importResult.put("importMsg", ResourceUtil.getString("hr-organization:hr.organization.import.fail"));
			importResult.getJSONArray("otherErrors").add(ResourceUtil.getString("hr-organization:hr.organization.import.failErrormsg")+e.getMessage());
		}
		String result = HrOrgUtil.replaceCharacter(importResult.toString());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("<script>parent.callback(" + result + ");</script>");
		return null;
	}

	/**
	 * <p>导出</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			WorkBook wb = getServiceImp(request).export(hqlInfo, request);
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
			}
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				messages.setHasError();
				KmssMessage message = new KmssMessage("hr-organization:hr.organization.delete.message");
				message.setMessageType(KmssMessage.MESSAGE_ERROR);
				messages.addError(message, e);
			} else {
				messages.addError(e);
			}
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}
}
