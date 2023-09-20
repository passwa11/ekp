package com.landray.kmss.third.weixin.action;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataTemp;
import com.landray.kmss.third.weixin.forms.ThirdWeixinChatDataTempForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.service.IThirdWeixinChatDataTempService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdWeixinChatDataTempAction extends ExtendAction {

    private IThirdWeixinChatDataTempService thirdWeixinChatDataTempService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWeixinChatDataTempService == null) {
            thirdWeixinChatDataTempService = (IThirdWeixinChatDataTempService) getBean("thirdWeixinChatDataTempService");
        }
        return thirdWeixinChatDataTempService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWeixinChatDataTemp.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdWeixinUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.weixin.model.ThirdWeixinChatDataTemp.class);
        ThirdWeixinUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWeixinChatDataTempForm thirdWeixinChatDataTempForm = (ThirdWeixinChatDataTempForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWeixinChatDataTempService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWeixinChatDataTempForm;
    }
}
