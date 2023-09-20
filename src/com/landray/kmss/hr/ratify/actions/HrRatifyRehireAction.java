package com.landray.kmss.hr.ratify.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.ratify.forms.HrRatifyRehireForm;
import com.landray.kmss.hr.ratify.model.HrRatifyRehire;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyRehireService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogCoreService;
import com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class HrRatifyRehireAction extends HrRatifyMainAction {

    private IHrRatifyRehireService hrRatifyRehireService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrRatifyRehireService == null) {
            hrRatifyRehireService = (IHrRatifyRehireService) getBean("hrRatifyRehireService");
        }
        return hrRatifyRehireService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrRatifyRehire.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.ratify.model.HrRatifyRehire.class);
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		((IHrRatifyRehireService) getServiceImp(request)).initFormSetting(
				(IExtendForm) form, new RequestContext(request));
        HrRatifyRehireForm hrRatifyRehireForm = (HrRatifyRehireForm) super.createNewForm(mapping, form, request, response);
		hrRatifyRehireForm.setDocTemplateName(
				getTemplatePath(hrRatifyRehireForm.getDocTemplateId()));
		String fdStaffId = request.getParameter("fdStaffId");
		if (StringUtil.isNotNull(fdStaffId)) {
			SysOrgElement fdStaff = getSysOrgCoreService()
					.findByPrimaryKey(fdStaffId);
			hrRatifyRehireForm.setFdRehireStaffId(fdStaffId);
			hrRatifyRehireForm.setFdRehireStaffName(fdStaff.getFdName());
		}
        return hrRatifyRehireForm;
    }

	/**
	 * 打印
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
    public ActionForward print(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-print", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HtmlToMht.setLocaleWhenExport(request);
			loadActionForm(mapping, form, request, response);
			// 引入打印机制
			HrRatifyRehireForm ratifyForm = (HrRatifyRehireForm) form;
			HrRatifyTemplate template = (HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(ratifyForm.getDocTemplateId());
			boolean enable = getSysPrintMainCoreService()
					.isEnablePrintTemplate(template, null, request);

			HrRatifyRehire main = (HrRatifyRehire) getServiceImp(
					null)
							.convertFormToModel(ratifyForm, null,
									new RequestContext(request));
			getSysPrintMainCoreService().initPrintData(main, ratifyForm,
					request);
			if (enable) {
				request.setAttribute("isShowSwitchBtn", "true");
			}
			// 打印日志
			getSysPrintLogCoreService().addPrintLog(main,
					new RequestContext(request));
			String printPageType = request.getParameter("_ptype");
			if (enable && !"old".equals(printPageType)) {
				return mapping.findForward("sysprint");
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-print", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("print", mapping, form, request, response);
		}
	}

	protected ISysPrintMainCoreService sysPrintMainCoreService;

	@Override
    public ISysPrintMainCoreService getSysPrintMainCoreService() {
		if (sysPrintMainCoreService == null) {
			sysPrintMainCoreService = (ISysPrintMainCoreService) getBean(
					"sysPrintMainCoreService");
		}
		return sysPrintMainCoreService;
	}

	protected ISysPrintLogCoreService sysPrintLogCoreService;

	@Override
    public ISysPrintLogCoreService getSysPrintLogCoreService() {
		if (sysPrintLogCoreService == null) {
			sysPrintLogCoreService = (ISysPrintLogCoreService) getBean(
					"sysPrintLogCoreService");
		}
		return sysPrintLogCoreService;
	}
}
