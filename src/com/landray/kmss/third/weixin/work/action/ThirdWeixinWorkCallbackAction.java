package com.landray.kmss.third.weixin.work.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinWorkCallbackForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkCallback;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkCallbackService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdWeixinWorkCallbackAction extends ExtendAction {

    private IThirdWeixinWorkCallbackService thirdWeixinWorkCallbackService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinWorkCallbackService == null) {
            thirdWeixinWorkCallbackService = (IThirdWeixinWorkCallbackService) getBean("thirdWeixinWorkCallbackService");
        }
        return thirdWeixinWorkCallbackService;
    }

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request,
                                      HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo,
				ThirdWeixinWorkCallback.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil
				.buildHqlInfoDate(hqlInfo, request,
						com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkCallback.class);
		com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil
				.buildHqlInfoModel(hqlInfo, request);
	}

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinWorkCallbackForm thirdWeixinWorkCallbackForm = (ThirdWeixinWorkCallbackForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinWorkCallbackService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinWorkCallbackForm;
    }

}
