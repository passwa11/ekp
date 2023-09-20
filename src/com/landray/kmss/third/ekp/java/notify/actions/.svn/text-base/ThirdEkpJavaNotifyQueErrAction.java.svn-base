package com.landray.kmss.third.ekp.java.notify.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.ekp.java.notify.forms.ThirdEkpJavaNotifyQueErrForm;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyQueErr;
import com.landray.kmss.third.ekp.java.notify.service.IThirdEkpJavaNotifyQueErrService;
import com.landray.kmss.third.ekp.java.notify.util.ThirdEkpJavaNotifyUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class ThirdEkpJavaNotifyQueErrAction extends ExtendAction {

	private IThirdEkpJavaNotifyQueErrService thirdEkpJavaNotifyQueErrService;

	@Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdEkpJavaNotifyQueErrService == null) {
			thirdEkpJavaNotifyQueErrService = (IThirdEkpJavaNotifyQueErrService) getBean(
					"thirdEkpJavaNotifyQueErrService");
		}
		return thirdEkpJavaNotifyQueErrService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request,
                                      HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo,
				ThirdEkpJavaNotifyQueErr.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		ThirdEkpJavaNotifyUtil.buildHqlInfoDate(hqlInfo, request,
				ThirdEkpJavaNotifyQueErr.class);
		ThirdEkpJavaNotifyUtil.buildHqlInfoModel(hqlInfo, request);
		String flag = request.getParameter("q.fdFlag");
		if (StringUtil.isNull(flag)) {
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				hqlInfo.setWhereBlock("thirdEkpJavaNotifyQueErr.fdFlag=1");
			} else {
				hqlInfo.setWhereBlock(
						whereBlock + " and thirdEkpJavaNotifyQueErr.fdFlag=1");
			}
		}
	}

	@Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ThirdEkpJavaNotifyQueErrForm thirdEkpJavaNotifyQueErrForm = (ThirdEkpJavaNotifyQueErrForm) super.createNewForm(
				mapping, form, request, response);
		((IThirdEkpJavaNotifyQueErrService) getServiceImp(request))
				.initFormSetting((IExtendForm) form,
						new RequestContext(request));
		return thirdEkpJavaNotifyQueErrForm;
	}

	public ActionForward resend(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-resend", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			((IThirdEkpJavaNotifyQueErrService) getServiceImp(request))
					.updateResend(ids);

		} catch (Exception e) {
			messages.addError(new KmssMessage(null), e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-clear", true, getClass());
		JSONObject jsonObject = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String ekpUserId = request.getParameter("userId");
			((IThirdEkpJavaNotifyQueErrService) getServiceImp(
					request)).clear(ekpUserId);
			jsonObject.put("success", true);
			jsonObject.put("message", "成功");

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage() == null
					? "NullPointerException异常" : e.getMessage());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonObject.toString());
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward resendAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-resendAll", true, getClass());
		JSONObject jsonObject = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String ekpUserId = request.getParameter("userId");
			((IThirdEkpJavaNotifyQueErrService) getServiceImp(
					request)).resend(ekpUserId);
			jsonObject.put("success", true);
			jsonObject.put("message", "成功");

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage() == null
					? "NullPointerException异常" : e.getMessage());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonObject.toString());
			e.printStackTrace();
		}
		return null;
	}

}
