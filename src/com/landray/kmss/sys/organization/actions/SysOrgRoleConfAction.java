package com.landray.kmss.sys.organization.actions;

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
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.forms.SysOrgRoleConfForm;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.model.SysOrgRoleConfCate;
import com.landray.kmss.sys.organization.service.ISysOrgRoleConfCateService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleConfService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 叶中奇
 */
public class SysOrgRoleConfAction extends ExtendAction {
	protected ISysOrgRoleConfService sysOrgRoleConfService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysOrgRoleConfService == null) {
            sysOrgRoleConfService = (ISysOrgRoleConfService) getBean("sysOrgRoleConfService");
        }
		return sysOrgRoleConfService;
	}

	protected ISysOrgRoleService sysOrgRoleService;

	protected ISysOrgRoleService getSysOrgRoleServiceImp(
			HttpServletRequest request) {
		if (sysOrgRoleService == null) {
            sysOrgRoleService = (ISysOrgRoleService) getBean("sysOrgRoleService");
        }
		return sysOrgRoleService;
	}
	
	protected ISysOrgRoleConfCateService sysOrgRoleConfCateService;

	protected ISysOrgRoleConfCateService getSysOrgRoleConfCateService() {
		if(sysOrgRoleConfCateService == null) {
            sysOrgRoleConfCateService = (ISysOrgRoleConfCateService) getBean("sysOrgRoleConfCateService");
        }
		return sysOrgRoleConfCateService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysOrgRoleConfForm mainForm = (SysOrgRoleConfForm) super.createNewForm(mapping, form, request, response);
		String categoryId = request.getParameter("categoryId");
		if (StringUtil.isNotNull(categoryId)) {
			SysOrgRoleConfCate sysOrgRoleConfCate = (SysOrgRoleConfCate) getSysOrgRoleConfCateService().findByPrimaryKey(categoryId);
			mainForm.setFdRoleConfCateId(categoryId);
			mainForm.setFdRoleConfCateName(sysOrgRoleConfCate.getFdName());
		}
		return mainForm;
	}
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		String parentCate = request.getParameter("parentCate");
		if (parentCate != null) {
			if ("".equals(parentCate)) {
				whereBlock += " and sysOrgRoleConf.fdRoleConfCate is null ";
			} else {
				whereBlock += " and sysOrgRoleConf.fdRoleConfCate.fdId =:fdId ";
				hqlInfo.setParameter("fdId", parentCate);
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_EDITOR);
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo, SysOrgRoleConf.class);
	}


	@Override
	@SuppressWarnings("unchecked")
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null) {
                rtnForm = getServiceImp(request).convertModelToForm(
                        (IExtendForm) form, model, new RequestContext(request));
            }
			if ("view".equals(request.getParameter("method"))) {
				if (UserOperHelper.allowLogOper("Action_Find",
						rtnForm.getModelClass().getName())) {
					UserOperContentHelper.putFind(model);
				}
				String whereBlock = "sysOrgRole.fdRoleConf.fdId='" + id + "'";
				String orderBy = "sysOrgRole.fdOrder";
				List list = getSysOrgRoleServiceImp(request).findList(
						whereBlock, orderBy);
				request.setAttribute("roles", list);
			}
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

	public ActionForward checkRepeatRole(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-checkRepeatRole", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ISysOrgRoleConfService sysOrgRoleConfService = (ISysOrgRoleConfService) getServiceImp(request);
			request.setAttribute("sysOrgRoleConfForm", sysOrgRoleConfService
					.loadRepeatRoleForm(request.getParameter("fdId")));
			// 检测无效的机构
			request.setAttribute("invalidElement", sysOrgRoleConfService
					.loadInvalidElement(request.getParameter("fdId")));
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-checkRepeatRole", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("check", mapping, form, request, response);
		}
	}

	public ActionForward updateRepeatRole(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysOrgRoleConfForm roleConfForm = (SysOrgRoleConfForm) form;
			ISysOrgRoleConfService sysOrgRoleConfService = (ISysOrgRoleConfService) getServiceImp(request);
			sysOrgRoleConfService.updateRepeatRoleForm(roleConfForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("check", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 批量置为无效
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward invalidatedAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			if (ids != null) {
                ((ISysOrgRoleConfService) getServiceImp(request))
                        .updateInvalidated(ids, new RequestContext(request));
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	/**
	 * 置为无效
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward invalidated(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                ((ISysOrgRoleConfService) getServiceImp(request))
                        .updateInvalidated(id, new RequestContext(request));
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	/**
	 * 角色线复制
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateCopy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateCopy", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			if (StringUtil.isNotNull(fdId)) {
				((ISysOrgRoleConfService) getServiceImp(request))
						.updateCopy(fdId);
			} else {
				messages.addError(new NoRecordException());
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-updateCopy", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
}
