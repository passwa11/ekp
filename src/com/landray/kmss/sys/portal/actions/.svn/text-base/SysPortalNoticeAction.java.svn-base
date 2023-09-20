package com.landray.kmss.sys.portal.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.sys.portal.model.SysPortalNotice;
import com.landray.kmss.sys.portal.service.ISysPortalNoticeService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

/**
 * 门户公告
 * 
 * @author linxiuxian
 *
 */
public class SysPortalNoticeAction extends ExtendAction {
	private ISysPortalNoticeService sysPortalNoticeService;

	@Override
	protected ISysPortalNoticeService
			getServiceImp(HttpServletRequest request) {
		if (sysPortalNoticeService == null) {
			sysPortalNoticeService = (ISysPortalNoticeService) getBean(
					"sysPortalNoticeService");
		}
		return sysPortalNoticeService;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalNotice.class.getName());
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(false);
		hqlInfo.setOrderBy("docCreateTime desc,fdId desc");
		Page page = getServiceImp(request).findPage(hqlInfo);
		SysPortalNotice model = new SysPortalNotice();
		if (page.getList() != null && !page.getList().isEmpty()) {
			model = (SysPortalNotice) page.getList().get(0);
			if (model != null && model.getFdState().booleanValue()) {
				UserOperHelper.logFind(model);
				long endTime = model.getDocEndTime().getTime();
				long now = new Date().getTime();
				if (now > endTime) {
					model.setFdState(false);
				}
			}
		}
		rtnForm = getServiceImp(request).convertModelToForm(
				(IExtendForm) form, model, new RequestContext(request));
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	public ActionForward getPortalNotice(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String __portalNoticeShow__ = (String) request.getSession()
				.getAttribute("__portalNoticeShow__");
		JSONObject result = new JSONObject();
		result.put("isShow", 0);
		try {
			SysPortalNotice model = getServiceImp(request).getPortalNotice();
			if (model != null && StringUtil.isNull(__portalNoticeShow__)) {
				if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FIND,
						SysPortalNotice.class.getName())) {
					UserOperContentHelper.putFind(model);
				}

				result.put("isShow", 1);
				result.put("docContent", model.getDocContent());
				request.getSession().setAttribute("__portalNoticeShow__",
						"true");
			}

		} catch (Exception e) {
		}

		request.setAttribute("lui-source", result);
		return mapping.findForward("lui-source");
	}
}
