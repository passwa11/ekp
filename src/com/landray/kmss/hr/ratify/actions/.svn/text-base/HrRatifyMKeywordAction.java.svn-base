package com.landray.kmss.hr.ratify.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.forms.HrRatifyMKeywordForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.hr.ratify.service.IHrRatifyMKeywordService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class HrRatifyMKeywordAction extends ExtendAction {

    private IHrRatifyMKeywordService hrRatifyMKeywordService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrRatifyMKeywordService == null) {
            hrRatifyMKeywordService = (IHrRatifyMKeywordService) getBean("hrRatifyMKeywordService");
        }
        return hrRatifyMKeywordService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrRatifyMKeyword.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.ratify.model.HrRatifyMKeyword.class);
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrRatifyMKeywordForm hrRatifyMKeywordForm = (HrRatifyMKeywordForm) super.createNewForm(mapping, form, request, response);
        ((IHrRatifyMKeywordService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return hrRatifyMKeywordForm;
    }
}
