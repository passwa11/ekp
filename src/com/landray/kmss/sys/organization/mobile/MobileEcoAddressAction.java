package com.landray.kmss.sys.organization.mobile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;

public class MobileEcoAddressAction extends BaseAction {
	
	protected IMobileAddressService mobileAddressService;

	public IMobileAddressService getMobileAddressService() {
		if (mobileAddressService == null) {
            mobileAddressService = (IMobileAddressService) SpringBeanUtil
                    .getBean("mobileAddressService");
        }
		return mobileAddressService;
	}
	
	public ActionForward addressList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			RequestContext requestContext = new RequestContext(request);
			requestContext.setParameter("fdIsExternal", "true");
			// 直接使用生态组织，为管理端操作
			requestContext.setParameter("sys_page", "true");
			List addressList = getMobileAddressService().mobileAddressList(
					requestContext);
			request.setAttribute("lui-source", JSONArray
					.fromObject(addressList));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}

	}
	
	public ActionForward searchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			RequestContext rc = new RequestContext(request);
			rc.setParameter("isExternal", "true");
			// 直接使用生态组织，为管理端操作
			rc.setParameter("sys_page", "true");
			String parentId = request.getParameter("parentId");
			if (StringUtil.isNotNull(parentId)) {
				rc.setParameter("parentId", parentId);
			}
			List searchList = getMobileAddressService().searchList(
					rc);
			request
					.setAttribute("lui-source", JSONArray
							.fromObject(searchList));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}

	}

}
