package com.landray.kmss.third.welink.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.welink.forms.ThirdWelinkDeptNoMappingForm;
import com.landray.kmss.third.welink.model.ThirdWelinkDeptNoMapping;
import com.landray.kmss.third.welink.service.IThirdWelinkDeptNoMappingService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class ThirdWelinkDeptNoMappingAction extends ExtendAction {

    private IThirdWelinkDeptNoMappingService thirdWelinkDeptNoMappingService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWelinkDeptNoMappingService == null) {
            thirdWelinkDeptNoMappingService = (IThirdWelinkDeptNoMappingService) getBean("thirdWelinkDeptNoMappingService");
        }
        return thirdWelinkDeptNoMappingService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWelinkDeptNoMapping.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.welink.util.ThirdWelinkUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.welink.model.ThirdWelinkDeptNoMapping.class);
        com.landray.kmss.third.welink.util.ThirdWelinkUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWelinkDeptNoMappingForm thirdWelinkDeptNoMappingForm = (ThirdWelinkDeptNoMappingForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWelinkDeptNoMappingService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWelinkDeptNoMappingForm;
    }

	public ActionForward welinkDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-welinkDel", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String type = request.getParameter("type");
			String parentId = request.getParameter("parentId");

			json.put("status", 1);
			json.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-welinkDel", false, getClass());
		return null;
	}

	public ActionForward ekpUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-ekpUpdate", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String type = request.getParameter("type");
			String fdEKPId = request.getParameter("fdEKPId");
			boolean flag = false;
			if (flag) {
				json.put("status", 1);
				json.put("msg", "成功");
			} else {
				json.put("status", 0);
				json.put("msg", "请不要重复映射");
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-ekpUpdate", false, getClass());
		return null;
	}
}
