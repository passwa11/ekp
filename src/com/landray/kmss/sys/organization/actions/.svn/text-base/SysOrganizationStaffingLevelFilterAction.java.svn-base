package com.landray.kmss.sys.organization.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.organization.forms.SysOrganizationStaffingLevelFilterForm;
import com.landray.kmss.sys.organization.model.SysOrgConfig;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.util.SysOrgStaffingLevelFilterConstant;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 职级配置 Action
 * 
 * @author
 * @version 1.0 2015-07-23
 */
public class SysOrganizationStaffingLevelFilterAction extends ExtendAction {
	protected ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysOrganizationStaffingLevelService == null) {
            sysOrganizationStaffingLevelService = (ISysOrganizationStaffingLevelService) getBean("sysOrganizationStaffingLevelService");
        }
		return sysOrganizationStaffingLevelService;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		SysOrganizationStaffingLevelFilterForm sysOrganizationStaffingLevelFilterForm = (SysOrganizationStaffingLevelFilterForm) form;

		try {
			((ISysOrganizationStaffingLevelService) getServiceImp(request))
					.updateFilterSetting(
							sysOrganizationStaffingLevelFilterForm,
							new RequestContext(request));
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

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SysOrganizationStaffingLevelFilterForm sysOrganizationStaffingLevelFilterForm = new SysOrganizationStaffingLevelFilterForm();
		SysOrgConfig orgConfig = new SysOrgConfig();
		orgConfig.setFdType(2);
		String isOrgStaffingLevelFilterEnable = orgConfig
				.getOrgStaffingLevelFilterEnable();
		if (StringUtil.isNull(isOrgStaffingLevelFilterEnable)) {
			orgConfig.setOrgStaffingLevelFilterEnable("false");
			orgConfig.save();
		} else {
			sysOrganizationStaffingLevelFilterForm
					.setIsOrgStaffingLevelFilterEnable(isOrgStaffingLevelFilterEnable);
		}
		String orgStaffingLevelFilterSub = orgConfig
				.getOrgStaffingLevelFilterSub();
		if (StringUtil.isNull(orgStaffingLevelFilterSub)) {
			orgConfig.setOrgStaffingLevelFilterSub("0");
			orgConfig.save();
		} else {
			sysOrganizationStaffingLevelFilterForm
					.setOrgStaffingLevelFilterSub(orgStaffingLevelFilterSub);
		}
		String orgStaffingLevelFilterDirection = orgConfig
				.getOrgStaffingLevelFilterDirection();
		if (StringUtil.isNull(orgStaffingLevelFilterDirection)) {
			orgConfig
					.setOrgStaffingLevelFilterDirection(SysOrgStaffingLevelFilterConstant.ORG_STAFFING_LEVEL_FILTER_DIRECTION_POSITIVE);
			orgConfig.save();
		} else {
			sysOrganizationStaffingLevelFilterForm
					.setOrgStaffingLevelFilterDirection(orgStaffingLevelFilterDirection);
		}
		if (sysOrganizationStaffingLevelFilterForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(getFormName(
				sysOrganizationStaffingLevelFilterForm, request),
				sysOrganizationStaffingLevelFilterForm);
	}
}
