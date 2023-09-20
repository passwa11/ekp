package com.landray.kmss.third.ding.action;

import com.landray.kmss.third.ding.model.ThirdDingCardMapping;
import com.landray.kmss.third.ding.forms.ThirdDingCardMappingForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.ding.service.IThirdDingCardMappingService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingCardMappingAction extends ExtendAction {

    private IThirdDingCardMappingService thirdDingCardMappingService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingCardMappingService == null) {
            thirdDingCardMappingService = (IThirdDingCardMappingService) getBean("thirdDingCardMappingService");
        }
        return thirdDingCardMappingService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingCardMapping.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingCardMapping.class);
        ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingCardMappingForm thirdDingCardMappingForm = (ThirdDingCardMappingForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingCardMappingService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingCardMappingForm;
    }
}
