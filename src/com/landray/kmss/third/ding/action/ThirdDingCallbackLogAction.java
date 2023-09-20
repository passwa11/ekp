package com.landray.kmss.third.ding.action;

import java.io.PrintWriter;

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
import com.landray.kmss.third.ding.forms.ThirdDingCallbackLogForm;
import com.landray.kmss.third.ding.model.ThirdDingCallbackLog;
import com.landray.kmss.third.ding.service.IThirdDingCallbackLogService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class ThirdDingCallbackLogAction extends ExtendAction {

    private IThirdDingCallbackLogService thirdDingCallbackLogService;

    @Override
    public IThirdDingCallbackLogService getServiceImp(HttpServletRequest request) {
        if (thirdDingCallbackLogService == null) {
            thirdDingCallbackLogService = (IThirdDingCallbackLogService) getBean("thirdDingCallbackLogService");
        }
        return thirdDingCallbackLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingCallbackLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingCallbackLog.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingCallbackLogForm thirdDingCallbackLogForm = (ThirdDingCallbackLogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingCallbackLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingCallbackLogForm;
    }
    
    
	public ActionForward callbackAgain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-callbackAgain", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject jsonObject = new JSONObject();
		PrintWriter printWriter = response.getWriter();
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				getServiceImp(request).saveOrUpdateCallbackAgain(fdId);
			}
			jsonObject.put("success", true);
	   	    jsonObject.put("message", "成功");
	   	    printWriter.print(jsonObject);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage());  
			printWriter.print(jsonObject);
			e.printStackTrace();
		}

		return null;
	}
}
