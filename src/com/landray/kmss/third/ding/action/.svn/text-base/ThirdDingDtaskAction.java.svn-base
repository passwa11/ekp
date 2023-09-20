package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.ding.forms.ThirdDingDtaskForm;
import com.landray.kmss.third.ding.model.ThirdDingDtask;
import com.landray.kmss.third.ding.service.IThirdDingDtaskService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;
public class ThirdDingDtaskAction extends ExtendAction {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingDtaskAction.class);

	private IThirdDingDtaskService thirdDingDtaskService;

	@Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdDingDtaskService == null) {
			thirdDingDtaskService = (IThirdDingDtaskService) getBean("thirdDingDtaskService");
		}
		return thirdDingDtaskService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingDtask.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request,
				com.landray.kmss.third.ding.model.ThirdDingDtask.class);
		com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
	}

	@Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
		ThirdDingDtaskForm thirdDingDtaskForm = (ThirdDingDtaskForm) super.createNewForm(mapping, form, request,
				response);
		((IThirdDingDtaskService) getServiceImp(request)).initFormSetting((IExtendForm) form,
				new RequestContext(request));
		return thirdDingDtaskForm;
	}


	public ActionForward updateSend(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateSend", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			if(StringUtil.isNotNull(fdId)){
				((IThirdDingDtaskService)getServiceImp(request)).updateSendTask(fdId);
				result.put("error", "0");
				result.put("msg", "notify is done or send sucessfully");
			}
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
			result.put("error", "1");
			result.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result.toString());
		TimeCounter.logCurrentTime("Action-updateSend", true, getClass());
		return null;
	}
}
