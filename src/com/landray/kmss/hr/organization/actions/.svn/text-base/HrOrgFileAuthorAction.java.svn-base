package com.landray.kmss.hr.organization.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.organization.forms.HrOrgFileAuthorForm;
import com.landray.kmss.hr.organization.model.HrOrgFileAuthor;
import com.landray.kmss.hr.organization.service.IHrOrgFileAuthorService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;
 
/**
 * 人事组织授权 Action
 * 
 * @author 
 */
public class HrOrgFileAuthorAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrOrgFileAuthorAction.class);

	protected IHrOrgFileAuthorService hrOrgFileAuthorService;

	@Override
	protected IHrOrgFileAuthorService getServiceImp(HttpServletRequest request) {
		if (hrOrgFileAuthorService == null) {
			hrOrgFileAuthorService = (IHrOrgFileAuthorService) getBean("hrOrgFileAuthorService");
		}
		return hrOrgFileAuthorService;
	}

	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementServiceImp() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}
	
	public ActionForward config(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-config", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String orgId = request.getParameter("parentId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("hrOrgFileAuthor.fdName=:orgId");
			hqlInfo.setParameter("orgId", orgId);
			List<HrOrgFileAuthor> authorList = getServiceImp(request).findList(hqlInfo);

			if(ArrayUtil.isEmpty(authorList)){
				ActionForm newForm = createNewForm(mapping, form, request, response);
				HrOrgFileAuthorForm hrOrgFileAuthorForm = (HrOrgFileAuthorForm) newForm;
				hrOrgFileAuthorForm.setFdName(orgId);
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
					HrOrgFileAuthorForm hrOrgFileAuthorForm = (HrOrgFileAuthorForm) rtnForm;
					hrOrgFileAuthorForm.setFdName(orgId);
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
		HrOrgFileAuthorForm authorForm = (HrOrgFileAuthorForm) form;
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

	public void checkStaffFileAuthor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		boolean result = true;
		try {
			String fdId = request.getParameter("fdId");
			IBaseModel model = getSysOrgElementServiceImp().findByPrimaryKey(fdId, null, true);
			result = (null != model) ? true : false;
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		json.put("result", result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}
}

