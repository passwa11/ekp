package com.landray.kmss.sys.organization.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.organization.forms.SysOrganizationVisibleForm;
import com.landray.kmss.sys.organization.forms.SysOrganizationVisibleListForm;
import com.landray.kmss.sys.organization.model.SysOrgConfig;
import com.landray.kmss.sys.organization.model.SysOrganizationVisible;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.sso.client.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysOrganizationVisibleListAction extends ExtendAction {

	protected ISysOrganizationVisibleService sysOrganizationVisibleService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		// TODO 自动生成的方法存根
		if (sysOrganizationVisibleService == null) {
			sysOrganizationVisibleService = (ISysOrganizationVisibleService) SpringBeanUtil
					.getBean("sysOrganizationVisibleService");
		}
		return sysOrganizationVisibleService;
	}

	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		SysOrganizationVisibleListForm sysOrganizationVisibleListForm = (SysOrganizationVisibleListForm) form;

		try {
			((ISysOrganizationVisibleService) getServiceImp(request))
					.updateAll(sysOrganizationVisibleListForm,
							new RequestContext(request));

			publishEvent();
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("error", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward updateJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		SysOrganizationVisibleListForm sysOrganizationVisibleListForm = (SysOrganizationVisibleListForm) form;

		try {
			((ISysOrganizationVisibleService) getServiceImp(request))
					.updateAll(sysOrganizationVisibleListForm,
							new RequestContext(request));

		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("success", "1");
			jsonArray.add(jsonObject);
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(jsonArray.toString());
		} else {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("success", "0");
			jsonArray.add(jsonObject);
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(jsonArray.toString());
		}
		return null;
	}

	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<SysOrganizationVisible> sysOrganizationVisibles = getServiceImp(
				request).findList(new HQLInfo());

		List<SysOrganizationVisibleForm> sysOrganizationVisibleForms = new ArrayList<SysOrganizationVisibleForm>();
		for (SysOrganizationVisible sysOrganizationVisible : sysOrganizationVisibles) {
			SysOrganizationVisibleForm sysOrganizationVisibleForm = new SysOrganizationVisibleForm();
			getServiceImp(request).convertModelToForm(
					(IExtendForm) sysOrganizationVisibleForm,
					sysOrganizationVisible, new RequestContext(request));
			sysOrganizationVisibleForms.add(sysOrganizationVisibleForm);
		}
		SysOrganizationVisibleListForm sysOrganizationVisibleListForm = new SysOrganizationVisibleListForm();
		sysOrganizationVisibleListForm
				.setSysOrganizationVisibleFormList(sysOrganizationVisibleForms);
		SysOrgConfig orgConfig = new SysOrgConfig();
		orgConfig.setFdType(1);
		String isOrgAeraEnable = orgConfig.getOrgAeraEnable();
		if (StringUtil.isNull(isOrgAeraEnable)) {
			orgConfig.setOrgAeraEnable("false");
			orgConfig.save();
		} else {
			sysOrganizationVisibleListForm
					.setIsOrgAeraEnable(isOrgAeraEnable);
		}

		String isOrgVisibleEnable = orgConfig.getOrgVisibleEnable();
		if (StringUtil.isNull(isOrgVisibleEnable)) {
			orgConfig.setOrgVisibleEnable("false");
			orgConfig.save();
		} else {
			sysOrganizationVisibleListForm
					.setIsOrgVisibleEnable(isOrgVisibleEnable);
		}
		String defaultVisibleLevel = orgConfig.getDefaultVisibleLevel();
		if (StringUtil.isNull(defaultVisibleLevel)) {
			// orgConfig.setDefaultVisibleLevel("0");
			// orgConfig.save();
			sysOrganizationVisibleListForm.setDefaultVisibleLevel("");
		} else {
			sysOrganizationVisibleListForm
					.setDefaultVisibleLevel(defaultVisibleLevel);
		}
		request.setAttribute(getFormName(sysOrganizationVisibleListForm,
				request), sysOrganizationVisibleListForm);
	}

	private void publishEvent() {
		//修改可见性事件
		WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext())
				.publishEvent(new Event_Common("orgVisible"));
	}

}
