package com.landray.kmss.third.weixin.work.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinAuthLogForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinAuthLog;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinAuthLogService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdWeixinAuthLogAction extends ExtendAction {

    private IThirdWeixinAuthLogService thirdWeixinAuthLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinAuthLogService == null) {
            thirdWeixinAuthLogService = (IThirdWeixinAuthLogService) getBean("thirdWeixinAuthLogService");
        }
        return thirdWeixinAuthLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinAuthLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		com.landray.kmss.third.weixin.util.ThirdWeixinUtil
				.buildHqlInfoDate(hqlInfo, request, ThirdWeixinAuthLog.class);
        com.landray.kmss.third.weixin.util.ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinAuthLogForm thirdWeixinAuthLogForm = (ThirdWeixinAuthLogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinAuthLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinAuthLogForm;
    }
}
