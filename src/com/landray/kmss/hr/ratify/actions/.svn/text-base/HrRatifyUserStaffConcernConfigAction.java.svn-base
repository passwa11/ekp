package com.landray.kmss.hr.ratify.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.ratify.forms.HrRatifyStaffConcernConfigForm;
import com.landray.kmss.hr.ratify.forms.HrRatifyUserStaffConcernConfigForm;
import com.landray.kmss.hr.ratify.model.HrRatifyStaffConcernConfig;
import com.landray.kmss.hr.ratify.service.IHrRatifyStaffConcernConfigService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class HrRatifyUserStaffConcernConfigAction extends ExtendAction {

	private IHrRatifyStaffConcernConfigService hrRatifyStaffConcernConfigService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrRatifyStaffConcernConfigService == null) {
			hrRatifyStaffConcernConfigService = (IHrRatifyStaffConcernConfigService) getBean(
					"hrRatifyStaffConcernConfigService");
		}
		return hrRatifyStaffConcernConfigService;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		List<HrRatifyStaffConcernConfig> configs = getServiceImp(request)
				.findList(hqlInfo);
		List<HrRatifyStaffConcernConfigForm> configForms = new ArrayList<HrRatifyStaffConcernConfigForm>();
		for (HrRatifyStaffConcernConfig config : configs) {
			HrRatifyStaffConcernConfigForm configForm = new HrRatifyStaffConcernConfigForm();
			getServiceImp(request).convertModelToForm(configForm, config,
					new RequestContext(request));
			configForms.add(configForm);
		}
		HrRatifyUserStaffConcernConfigForm hrRatifyUserStaffConcernConfigForm = new HrRatifyUserStaffConcernConfigForm();
		hrRatifyUserStaffConcernConfigForm
				.setHrRatifyStaffConcernConfigFormList(configForms);
		request.setAttribute(
				getFormName(hrRatifyUserStaffConcernConfigForm, request),
				hrRatifyUserStaffConcernConfigForm);
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HrRatifyUserStaffConcernConfigForm hrRatifyUserStaffConcernConfigForm = (HrRatifyUserStaffConcernConfigForm) form;
			// 删除去除的配置
			String idStr = hrRatifyUserStaffConcernConfigForm.getDeleteIds();
			if (StringUtil.isNotNull(idStr)) {
				String[] ids = idStr.split(",");
				getServiceImp(request).delete(ids);
			}
			// 更新或新增
			List<HrRatifyStaffConcernConfigForm> list = (List<HrRatifyStaffConcernConfigForm>) hrRatifyUserStaffConcernConfigForm
					.getHrRatifyStaffConcernConfigFormList();
			for (HrRatifyStaffConcernConfigForm hrRatifyStaffConcernConfigForm : list) {
				String fdId = hrRatifyStaffConcernConfigForm.getFdId();
				String fdManagerIds = hrRatifyStaffConcernConfigForm
						.getFdManagerIds();
				if (StringUtil.isNull(fdManagerIds)) {
                    continue;
                }
				// 无fdId新增,有fdId更新
				if (StringUtil.isNull(fdId)) {
					HrRatifyStaffConcernConfig hrRatifyStaffConcernConfig = (HrRatifyStaffConcernConfig) getServiceImp(
							request).convertFormToModel(
									hrRatifyStaffConcernConfigForm, null,
									new RequestContext(request));
					hrRatifyStaffConcernConfig
							.setFdId(IDGenerator.generateID());
					getServiceImp(request).add(hrRatifyStaffConcernConfig);
				} else {
					getServiceImp(request).update(
							hrRatifyStaffConcernConfigForm,
							new RequestContext(request));
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

}
