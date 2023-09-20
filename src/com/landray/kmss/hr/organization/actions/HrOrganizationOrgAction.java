package com.landray.kmss.hr.organization.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.hr.organization.forms.HrOrganizationOrgForm;
import com.landray.kmss.hr.organization.model.HrOrganizationOrg;
import com.landray.kmss.hr.organization.service.IHrOrganizationOrgService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrOrganizationOrgAction extends ExtendAction {

    private IHrOrganizationOrgService hrOrganizationOrgService;

	@Override
	public IHrOrganizationOrgService getServiceImp(HttpServletRequest request) {
        if (hrOrganizationOrgService == null) {
            hrOrganizationOrgService = (IHrOrganizationOrgService) getBean("hrOrganizationOrgService");
        }
        return hrOrganizationOrgService;
    }

    @Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrOrganizationOrg.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.organization.util.HrOrganizationUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.organization.model.HrOrganizationOrg.class);
        com.landray.kmss.hr.organization.util.HrOrganizationUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrOrganizationOrgForm hrOrganizationOrgForm = (HrOrganizationOrgForm) super.createNewForm(mapping, form, request, response);
        ((IHrOrganizationOrgService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
		hrOrganizationOrgForm.setFdCreateTime(DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME));
        return hrOrganizationOrgForm;
    }

	/**
	 * <p>禁用组织</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward changeDisabled(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			String fdId = request.getParameter("fdId");
			boolean flag = getServiceImp(request).updateInvalidated(fdId, new RequestContext(request));
			json.put("flag", flag);
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	/**
	 * <p>
	 * 批量禁用组织
	 * </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward changeDisabledList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {

			JSONArray jsonarr = new JSONArray();
			String fdId = request.getParameter("fdId");
			String[] fdIdArr = fdId.split(";");
			for (int i = 0; i < fdIdArr.length; i++) {
				JSONObject json = new JSONObject();
				boolean flag = getServiceImp(request).updateInvalidated(
						fdIdArr[i],
						new RequestContext(request));
				json.put("flag", flag);
				jsonarr.add(json);
			}

			request.setAttribute("lui-source", jsonarr);
		} catch (Exception e) {
			messages.addError(e);
		}
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
