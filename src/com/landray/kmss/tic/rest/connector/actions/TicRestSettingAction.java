package com.landray.kmss.tic.rest.connector.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.tic.rest.connector.forms.TicRestSettingForm;
import com.landray.kmss.tic.rest.connector.model.TicRestSettCategory;
import com.landray.kmss.tic.rest.connector.model.TicRestSetting;
import com.landray.kmss.tic.rest.connector.service.ITicRestSettCategoryService;
import com.landray.kmss.tic.rest.connector.service.ITicRestSettingService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * REST服务配置 Action
 */
public class TicRestSettingAction extends ExtendAction {

	protected ITicRestSettingService TicRestSettingService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (TicRestSettingService == null) {
			TicRestSettingService = (ITicRestSettingService) getBean("ticRestSettingService");
		}
		return TicRestSettingService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TicRestSettingForm ticRestSettingForm = (TicRestSettingForm) form;
		String categoryId = request.getParameter("categoryId");
		ticRestSettingForm.setDocCreatorId(UserUtil.getUser().getFdId());
		ticRestSettingForm.setDocCreatorName(UserUtil.getUser().getFdName());
		ticRestSettingForm.setDocCreateTime(DateUtil.convertDateToString(new Date(), null, null));
		ITicRestSettCategoryService ticRestSettCategoryService = (ITicRestSettCategoryService) SpringBeanUtil
				.getBean("ticRestSettCategoryService");
		if (StringUtil.isNotNull(categoryId)) {
			TicRestSettCategory ticRestSettCategory = (TicRestSettCategory) ticRestSettCategoryService.findByPrimaryKey(categoryId);
			ticRestSettingForm.setSettCategoryId(categoryId);
			ticRestSettingForm.setSettCategoryName(ticRestSettCategory.getFdName());
		}
		return ticRestSettingForm;
	}

	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			TicRestSettingForm mainForm = (TicRestSettingForm) form;
			mainForm.setDocAlterTime(DateUtil.convertDateToString(new Date(), null, null));
			TicRestSetting TicRestSetting = new TicRestSetting();
			TicRestSetting = (TicRestSetting) getServiceImp(request).convertFormToModel(mainForm, TicRestSetting,
					new RequestContext());
			getServiceImp(request).add((IExtendForm) mainForm, new RequestContext(request));

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			TicRestSettingForm mainForm = (TicRestSettingForm) form;
			mainForm.setDocAlterTime(DateUtil.convertDateToString(new Date(), null, null));
			// 添加操作日志
			getServiceImp(request).update((IExtendForm) mainForm, new RequestContext(request));
			TicRestSetting TicRestSetting = new TicRestSetting();
			TicRestSetting = (TicRestSetting) getServiceImp(request).convertFormToModel(mainForm, TicRestSetting,
					new RequestContext());

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 删除方法，添加操作日志
	 */
	@Override
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
		Object result = super.delete(mapping, form, request, response);
		String id = request.getParameter("fdId");
		return (ActionForward) result;
	}

	/**
	 * 
	 * 删除方法，添加操作日志
	 */
	@Override
    public ActionForward deleteall(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
		Object result = super.deleteall(mapping, form, request, response);
		String[] ids = request.getParameterValues("List_Selected");
		return (ActionForward) result;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String categoryId = request.getParameter("categoryId");
		String hql = hqlInfo.getWhereBlock();
		// hql=StringUtil.linkString(hql, " and ",
		// "ticRestSetting.docIsNewVersion = :docIsNewVersion");
		// hqlInfo.setParameter("docIsNewVersion", true);
		if (!StringUtil.isNull(categoryId)) {
			hql = StringUtil.linkString(hql, " and ", "ticRestSetting.settCategory.fdHierarchyId like :fdHierarchyId ");
			hqlInfo.setParameter("fdHierarchyId", "%" + categoryId + "%");
		}
		hqlInfo.setWhereBlock(hql);

	}

	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			TicRestSetting model = (TicRestSetting) getServiceImp(request).findByPrimaryKey(id, null, true);
			if (model != null) {
				rtnForm = getServiceImp(request).convertModelToForm((IExtendForm) form, model,
						new RequestContext(request));
			}
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}
}
