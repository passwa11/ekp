package com.landray.kmss.fssc.budgeting.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingOrgForm;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingOrg;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingOrgService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class FsscBudgetingOrgAction extends ExtendAction {

    private IFsscBudgetingOrgService fsscBudgetingOrgService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetingOrgService == null) {
            fsscBudgetingOrgService = (IFsscBudgetingOrgService) getBean("fsscBudgetingOrgService");
        }
        return fsscBudgetingOrgService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetingOrg.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscBudgetingOrgForm fsscBudgetingOrgForm = (FsscBudgetingOrgForm) super.createNewForm(mapping, form, request, response);
        ((IFsscBudgetingOrgService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscBudgetingOrgForm;
    }
    
    /**
	 * 将浏览器提交的表单数据添加到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，只显示返回按钮，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			super.save(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
			return getActionForward("success", mapping, form, request, response);
		}
	}
}
