package com.landray.kmss.fssc.budget.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.fssc.budget.service.spring.FsscBudgetPortletService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FsscBudgetPortletAction extends ExtendAction  {
	
	private static final Log logger = LogFactory.getLog(FsscBudgetPortletAction.class);

	public FsscBudgetPortletService fsscBudgetPortletService;

	@Override
    protected FsscBudgetPortletService getServiceImp(HttpServletRequest request) {
		if (fsscBudgetPortletService == null) {
			fsscBudgetPortletService = (FsscBudgetPortletService) getBean("fsscBudgetPortletService");
		}
		return fsscBudgetPortletService;
	}

	/**
     * 部门预算总额及排名靠前8个费用类型及其他费用
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getCostCenterAcountInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            JSONObject jsonObject = getServiceImp(request).getCostCenterAcountInfo(request);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
            messages.addError(e);
        }
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return null;
        }
    }
	/**
     * 统计9个项目预算
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getProjectAcountInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            JSONObject jsonObject = getServiceImp(request).getProjectAcountInfo(request);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
            messages.addError(e);
        }
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return null;
        }
    }
}
