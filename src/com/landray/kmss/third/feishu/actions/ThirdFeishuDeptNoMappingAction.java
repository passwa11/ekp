package com.landray.kmss.third.feishu.actions;

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
import com.landray.kmss.third.feishu.forms.ThirdFeishuDeptNoMappingForm;
import com.landray.kmss.third.feishu.model.ThirdFeishuDeptNoMapping;
import com.landray.kmss.third.feishu.service.IThirdFeishuDeptNoMappingService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class ThirdFeishuDeptNoMappingAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuDeptNoMappingAction.class);

    private IThirdFeishuDeptNoMappingService thirdFeishuDeptNoMappingService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdFeishuDeptNoMappingService == null) {
            thirdFeishuDeptNoMappingService = (IThirdFeishuDeptNoMappingService) getBean("thirdFeishuDeptNoMappingService");
        }
        return thirdFeishuDeptNoMappingService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdFeishuDeptNoMapping.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.feishu.util.ThirdFeishuUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.feishu.model.ThirdFeishuDeptNoMapping.class);
        com.landray.kmss.third.feishu.util.ThirdFeishuUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdFeishuDeptNoMappingForm thirdFeishuDeptNoMappingForm = (ThirdFeishuDeptNoMappingForm) super.createNewForm(mapping, form, request, response);
        ((IThirdFeishuDeptNoMappingService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdFeishuDeptNoMappingForm;
    }

	public ActionForward feishuDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-feishuDel", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String feishuId = request.getParameter("feishuId");
			((IThirdFeishuDeptNoMappingService) getServiceImp(request))
					.deleteFeishu(fdId,
							feishuId);
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
		TimeCounter.logCurrentTime("Action-feishuDel", false, getClass());
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
			String fdEKPId = request.getParameter("fdEKPId");
			boolean flag = ((IThirdFeishuDeptNoMappingService) getServiceImp(
					request))
					.updateEKP(fdId,
									fdEKPId);
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
