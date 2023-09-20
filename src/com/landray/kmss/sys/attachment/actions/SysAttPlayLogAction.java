package com.landray.kmss.sys.attachment.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attachment.forms.SysAttPlayLogForm;
import com.landray.kmss.sys.attachment.model.SysAttPlayLog;
import com.landray.kmss.sys.attachment.service.ISysAttPlayLogService;
import com.landray.kmss.sys.attachment.service.spring.SysAttPlayLogTypeFactory;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class SysAttPlayLogAction extends ExtendAction {

	private ISysAttPlayLogService sysAttPlayLogService;

	@Override
	public ISysAttPlayLogService getServiceImp(HttpServletRequest request) {
		if (sysAttPlayLogService == null) {
			sysAttPlayLogService =
					(ISysAttPlayLogService) getBean("sysAttPlayLogService");
		}
		return sysAttPlayLogService;
	}

	/**
	 * 根据附件主键获取当前用户播放日志
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewByAttId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-viewByAttId", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdAttId = request.getParameter("fdAttId");
			String fdType = request.getParameter("fdType");

			if (StringUtil.isNull(fdAttId) || StringUtil.isNull(fdType)) {
				messages.addError(new NoRecordException());
			} else {

				JSONObject source = new JSONObject();

				Boolean isEnable = SysAttPlayLogTypeFactory.isEnable(fdType);

				if (isEnable) {
					SysAttPlayLog log =
							getServiceImp(request).viewByAttId(fdAttId);

					if (log != null) {
						source.element("fdId", log.getFdId());
						source.element("fdParam", log.getFdParam());
					}
				}

				source.element("fdEnable", isEnable);
				request.setAttribute("lui-source", source);

			}

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-viewByAttId", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("lui-source", mapping, form, request,
                    response);
        }
	}

	/**
	 * 根据主键更日志信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
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

			SysAttPlayLogForm logForm = (SysAttPlayLogForm) form;

			getServiceImp(request).updateParam(logForm.getFdId(),
					logForm.getFdParam());

			JSONObject source = new JSONObject();
			request.setAttribute("lui-source", source);

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("lui-source", mapping, form, request,
                    response);
        }
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }

			SysAttPlayLogForm logForm = (SysAttPlayLogForm) form;

			String fdId = getServiceImp(request).addParam(logForm.getFdAttId(),
					logForm.getFdParam(), logForm.getFdType());

			JSONObject source = new JSONObject();

			if (StringUtil.isNotNull(fdId)) {
				source.element("fdId", fdId);
			}

			request.setAttribute("lui-source", source);

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("lui-source", mapping, form, request,
                    response);
        }
	}

	@Override
	public void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo, SysAttPlayLog.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
	}

}
