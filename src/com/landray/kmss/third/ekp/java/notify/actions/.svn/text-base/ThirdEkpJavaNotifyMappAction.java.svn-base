package com.landray.kmss.third.ekp.java.notify.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ekp.java.notify.forms.ThirdEkpJavaNotifyMappForm;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyMapp;
import com.landray.kmss.third.ekp.java.notify.service.IThirdEkpJavaNotifyMappService;
import com.landray.kmss.third.ekp.java.notify.util.ThirdEkpJavaNotifyUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdEkpJavaNotifyMappAction extends ExtendAction {

    private IThirdEkpJavaNotifyMappService thirdEkpJavaNotifyMappService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdEkpJavaNotifyMappService == null) {
            thirdEkpJavaNotifyMappService = (IThirdEkpJavaNotifyMappService) getBean("thirdEkpJavaNotifyMappService");
        }
        return thirdEkpJavaNotifyMappService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdEkpJavaNotifyMapp.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		ThirdEkpJavaNotifyUtil.buildHqlInfoDate(hqlInfo, request,
				ThirdEkpJavaNotifyMapp.class);
		ThirdEkpJavaNotifyUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdEkpJavaNotifyMappForm thirdEkpJavaNotifyMappForm = (ThirdEkpJavaNotifyMappForm) super.createNewForm(mapping, form, request, response);
        ((IThirdEkpJavaNotifyMappService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdEkpJavaNotifyMappForm;
    }
}
