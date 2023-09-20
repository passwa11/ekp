package com.landray.kmss.fssc.budgeting.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingAuthForm;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingAuth;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingAuthService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class FsscBudgetingAuthAction extends ExtendAction {

    private IFsscBudgetingAuthService fsscBudgetingAuthService;

    @Override
    public IFsscBudgetingAuthService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetingAuthService == null) {
            fsscBudgetingAuthService = (IFsscBudgetingAuthService) getBean("fsscBudgetingAuthService");
        }
        return fsscBudgetingAuthService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetingAuth.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscBudgetingAuthForm fsscBudgetingAuthForm = (FsscBudgetingAuthForm) super.createNewForm(mapping, form, request, response);
        ((IFsscBudgetingAuthService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscBudgetingAuthForm;
    }
    /**
	 * 保存导入预算编制权限数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveImport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();

		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			FsscBudgetingAuthForm mainForm = (FsscBudgetingAuthForm) form;
			getServiceImp(request).saveImport(mainForm,request);
		} catch (Exception e) {
			messages.addError(e);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(result.toString());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("result", mapping, form, request, response);
		}
	}
}
