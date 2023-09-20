package com.landray.kmss.fssc.voucher.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.fssc.voucher.forms.FsscVoucherRuleConfigForm;
import com.landray.kmss.fssc.voucher.model.FsscVoucherRuleConfig;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherRuleConfigService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FsscVoucherRuleConfigAction extends ExtendAction {

    private IFsscVoucherRuleConfigService fsscVoucherRuleConfigService;

    @Override
    public IFsscVoucherRuleConfigService getServiceImp(HttpServletRequest request) {
        if (fsscVoucherRuleConfigService == null) {
            fsscVoucherRuleConfigService = (IFsscVoucherRuleConfigService) getBean("fsscVoucherRuleConfigService");
        }
        return fsscVoucherRuleConfigService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscVoucherRuleConfig.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscVoucherRuleConfigForm fsscVoucherRuleConfigForm = (FsscVoucherRuleConfigForm) super.createNewForm(mapping, form, request, response);
        ((IFsscVoucherRuleConfigService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscVoucherRuleConfigForm;
    }

    /**
     * 初始化
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward updateInit(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
        JSONObject json = new JSONObject();
        try {
            String fileName = request.getParameter("fileName");
            getServiceImp(request).updateInit(fileName);
            json.put("fdIsBoolean", "true");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("fdIsBoolean", "false");
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        return null;
    }
    
    
    /**
     * 复制
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward copyDoc(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		try {
			getServiceImp(request).updateCopyDoc(request,response);
			json.put("fdIsBoolean", "true");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("fdIsBoolean", "false");
		}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
		return null;
		}
}
