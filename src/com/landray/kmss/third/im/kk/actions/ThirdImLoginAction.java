package com.landray.kmss.third.im.kk.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.third.im.kk.model.ThirdImLogin;
import com.landray.kmss.third.im.kk.forms.ThirdImLoginForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.im.kk.service.IThirdImLoginService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.im.kk.util.ThirdImUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdImLoginAction extends ExtendAction {

    private IThirdImLoginService thirdImLoginService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdImLoginService == null) {
            thirdImLoginService = (IThirdImLoginService) getBean("thirdImLoginService");
        }
        return thirdImLoginService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdImLogin.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.im.kk.util.ThirdImUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.im.kk.model.ThirdImLogin.class);
        com.landray.kmss.third.im.kk.util.ThirdImUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdImLoginForm thirdImLoginForm = (ThirdImLoginForm) super.createNewForm(mapping, form, request, response);
        ((IThirdImLoginService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdImLoginForm;
    }
}
