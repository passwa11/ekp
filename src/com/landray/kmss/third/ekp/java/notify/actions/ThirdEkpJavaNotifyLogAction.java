package com.landray.kmss.third.ekp.java.notify.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ekp.java.notify.forms.ThirdEkpJavaNotifyLogForm;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyLog;
import com.landray.kmss.third.ekp.java.notify.service.IThirdEkpJavaNotifyLogService;
import com.landray.kmss.third.ekp.java.notify.util.ThirdEkpJavaNotifyUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdEkpJavaNotifyLogAction extends ExtendAction {

    private IThirdEkpJavaNotifyLogService thirdEkpJavaNotifyLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdEkpJavaNotifyLogService == null) {
            thirdEkpJavaNotifyLogService = (IThirdEkpJavaNotifyLogService) getBean("thirdEkpJavaNotifyLogService");
        }
        return thirdEkpJavaNotifyLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdEkpJavaNotifyLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		ThirdEkpJavaNotifyUtil.buildHqlInfoDate(hqlInfo, request,
				ThirdEkpJavaNotifyLog.class);
		ThirdEkpJavaNotifyUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdEkpJavaNotifyLogForm thirdEkpJavaNotifyLogForm = (ThirdEkpJavaNotifyLogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdEkpJavaNotifyLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdEkpJavaNotifyLogForm;
    }
}
