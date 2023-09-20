package com.landray.kmss.sys.simplecategory.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.simplecategory.forms.CateChgForm;
import com.landray.kmss.sys.simplecategory.service.ICateChgService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;

public class CateChgAction extends BaseAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(CateChgAction.class);

	public ActionForward cateChgEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			form.reset(mapping, request);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("cateChgEdit", mapping, form, request,
					response);
		}
	}

	public ActionForward cateChgUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }

			CateChgForm cateChgForm = (CateChgForm) form;
			String[] ids = cateChgForm.getFdIds().split("\\s*[;,]\\s*");
			String modelName = request.getParameter("modelName");

			if (SysAuthAreaUtils.isAreaEnabled(modelName)) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						modelName, "method=edit&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
					String formIds = ArrayUtil.concat(authIds, ';');
					cateChgForm.setFdIds(formIds);

					ICateChgService cateChgService = (ICateChgService) getBean("cateChgService");
					cateChgService.updateChgCate(cateChgForm,
							new RequestContext(request));
				}
			} else {
				ICateChgService cateChgService = (ICateChgService) getBean("cateChgService");
				cateChgService.updateChgCate(cateChgForm, new RequestContext(
						request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	protected ActionForward getActionForward(String defaultForward,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String para = request.getParameter("forward");
		if (!StringUtil.isNull(para)) {
            defaultForward = para;
        }
		return mapping.findForward(defaultForward);
	}
}
