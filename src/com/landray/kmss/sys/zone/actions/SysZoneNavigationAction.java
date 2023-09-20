/**
 * 
 */
package com.landray.kmss.sys.zone.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.person.actions.BaseCategoryAction;
import com.landray.kmss.sys.zone.forms.SysZoneNavigationForm;
import com.landray.kmss.sys.zone.model.SysZoneNavigation;
import com.landray.kmss.sys.zone.service.ISysZoneNavigationService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.UserUtil;

/**
 * @author 傅游翔
 * 
 */
public class SysZoneNavigationAction extends BaseCategoryAction {

	private ISysZoneNavigationService sysZoneNavigationService;

	@Override
	protected ISysZoneNavigationService getServiceImp(HttpServletRequest request) {
		if (sysZoneNavigationService == null) {
			sysZoneNavigationService = (ISysZoneNavigationService) getBean("sysZoneNavigationService");
		}
		return sysZoneNavigationService;
	}

	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
			String curOrderBy) throws Exception {
		return "fdStatus desc, fdOrder asc";
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		ActionForward forward = super.list(mapping, form, request, response);
		if (!"failure".equals(forward.getName()) ) {
			return getActionForward("data", mapping, form, request, response);
		}
		return forward;
	}

	@SuppressWarnings("unchecked")
	public ActionForward portlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-portlet", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdStatus > 1");
			hqlInfo.setOrderBy("fdOrder asc");
			List<SysZoneNavigation> navs = getServiceImp(request).findList(
					hqlInfo);
			request.setAttribute("navs", navs);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-portlet", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("portlet", mapping, form, request, response);
		}
	}
	
	@Override
	public ActionForward updateStatus(ActionMapping mapping, ActionForm form,
									  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateStatus", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			String status = request.getParameter("status");
			String showType = request.getParameter("changeShowType");
			if (!ArrayUtils.isEmpty(ids)) {
                getServiceImp(request).updateStatus(ids, status, showType);
            }

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-updateStatus", false, getClass());
		return updateResult(messages, mapping, form, request, response);
	}
	
	
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		SysZoneNavigationForm pForm = (SysZoneNavigationForm) form;
		KMSSUser user = UserUtil.getKMSSUser();
		pForm.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		pForm.setDocCreatorName(user.getUserName());
		return form;
	}
}
