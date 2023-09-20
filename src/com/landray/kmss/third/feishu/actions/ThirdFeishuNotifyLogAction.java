package com.landray.kmss.third.feishu.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyLog;
import com.landray.kmss.third.feishu.forms.ThirdFeishuNotifyLogForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.feishu.service.IThirdFeishuNotifyLogService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.feishu.util.ThirdFeishuUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdFeishuNotifyLogAction extends ExtendAction {

    private IThirdFeishuNotifyLogService thirdFeishuNotifyLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdFeishuNotifyLogService == null) {
            thirdFeishuNotifyLogService = (IThirdFeishuNotifyLogService) getBean("thirdFeishuNotifyLogService");
        }
        return thirdFeishuNotifyLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdFeishuNotifyLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.feishu.util.ThirdFeishuUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.feishu.model.ThirdFeishuNotifyLog.class);
        com.landray.kmss.third.feishu.util.ThirdFeishuUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdFeishuNotifyLogForm thirdFeishuNotifyLogForm = (ThirdFeishuNotifyLogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdFeishuNotifyLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdFeishuNotifyLogForm;
    }
}
