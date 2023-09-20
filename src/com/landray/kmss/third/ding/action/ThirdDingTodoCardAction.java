package com.landray.kmss.third.ding.action;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.forms.ThirdDingTodoCardForm;
import com.landray.kmss.third.ding.model.ThirdDingTodoCard;
import com.landray.kmss.third.ding.service.IThirdDingTodoCardService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThirdDingTodoCardAction extends ExtendAction {

    private IThirdDingTodoCardService thirdDingTodoCardService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingTodoCardService == null) {
            thirdDingTodoCardService = (IThirdDingTodoCardService) getBean("thirdDingTodoCardService");
        }
        return thirdDingTodoCardService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingTodoCard.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, ThirdDingTodoCard.class);
        ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingTodoCardForm thirdDingTodoCardForm = (ThirdDingTodoCardForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingTodoCardService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingTodoCardForm;
    }
}
