package com.landray.kmss.hr.staff.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.staff.forms.HrStaffFileAuthorForm;
import com.landray.kmss.hr.staff.model.HrStaffFileAuthor;
import com.landray.kmss.hr.staff.service.IHrStaffFileAuthorService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

 
/**
 * 档案授权 Action
 * 
 * @author 
 * @version 1.0 2017-11-10
 */
public class HrStaffFileAuthorAction extends ExtendAction {
	protected IHrStaffFileAuthorService hrStaffFileAuthorService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(hrStaffFileAuthorService == null){
			hrStaffFileAuthorService = (IHrStaffFileAuthorService)getBean("hrStaffFileAuthorService");
		}
		return hrStaffFileAuthorService;
	}
	
	public ActionForward config(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-config", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String orgId = request.getParameter("parentId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("hrStaffFileAuthor.fdName=:orgId");
			hqlInfo.setParameter("orgId", orgId);
			List<HrStaffFileAuthor> authorList = getServiceImp(request).findList(hqlInfo);

			if(ArrayUtil.isEmpty(authorList)){
				ActionForm newForm = createNewForm(mapping, form, request, response);
				if (newForm != form) {
                    request.setAttribute(getFormName(newForm, request), newForm);
                }
			}else{
				IBaseModel model = authorList.get(0);
				UserOperHelper.logFind(model);// 添加日志信息
				IExtendForm rtnForm = null;
				if (model != null){
					 rtnForm = getServiceImp(request).convertModelToForm(
							(IExtendForm) form, model, new RequestContext(request));
				}
				if (rtnForm == null){
					throw new NoRecordException();
				}
				request.setAttribute(getFormName(rtnForm, request), rtnForm);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-config", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}
	
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		String orgId = request.getParameter("parent");
		// 设置场所
		if (form instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}
		HrStaffFileAuthorForm authorForm = (HrStaffFileAuthorForm) form;
		authorForm.setFdName(orgId);
		return authorForm;
	}
	
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
			HrStaffFileAuthorForm hsForm= (HrStaffFileAuthorForm)form;
			hsForm.setFdName(request.getParameter("parentId"));
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}
}

