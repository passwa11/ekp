package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataAuthorizeForm;
import com.landray.kmss.eop.basedata.service.IEopBasedataAuthorizeService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class EopBasedataAuthorizeMobileAction extends ExtendAction {

    private IEopBasedataAuthorizeService eopBasedataAuthorizeService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataAuthorizeService == null) {
            eopBasedataAuthorizeService = (IEopBasedataAuthorizeService) getBean("eopBasedataAuthorizeService");
        }
        return eopBasedataAuthorizeService;
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataAuthorizeForm eopBasedataAuthorizeForm = (EopBasedataAuthorizeForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataAuthorizeService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataAuthorizeForm;
    }
    
    /**
	 * 授权列表
	 */
	@Override
    public ActionForward data(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			request.setAttribute("authorizeList", ((IEopBasedataAuthorizeService) getServiceImp(request)).getAuthorizeList(request));//授权列表
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request,response);
		}
	}
	
	/**
	 * 将浏览器提交的表单数据添加到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
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
			return new ActionForward("/eop/basedata/eop_basedata_authorize/eopMobileAuthorize.do?method=data",true);
		}
	}
	
	/**
	 * 将浏览器提交的表单数据更新到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回edit页面
	 * @throws Exception
	 */
	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			super.update(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return new ActionForward("/eop/basedata/eop_basedata_authorize/eopMobileAuthorize.do?method=data",true);
		}
	}
    
}
