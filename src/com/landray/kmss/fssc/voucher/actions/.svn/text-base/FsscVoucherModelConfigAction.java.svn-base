package com.landray.kmss.fssc.voucher.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.fssc.voucher.forms.FsscVoucherModelConfigForm;
import com.landray.kmss.fssc.voucher.model.FsscVoucherModelConfig;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherModelConfigService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FsscVoucherModelConfigAction extends ExtendAction {

    private IFsscVoucherModelConfigService fsscVoucherModelConfigService;

    @Override
    public IFsscVoucherModelConfigService getServiceImp(HttpServletRequest request) {
        if (fsscVoucherModelConfigService == null) {
            fsscVoucherModelConfigService = (IFsscVoucherModelConfigService) getBean("fsscVoucherModelConfigService");
        }
        return fsscVoucherModelConfigService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscVoucherModelConfig.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscVoucherModelConfigForm fsscVoucherModelConfigForm = (FsscVoucherModelConfigForm) super.createNewForm(mapping, form, request, response);
        ((IFsscVoucherModelConfigService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscVoucherModelConfigForm;
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
            getServiceImp(request).updateInit();
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
