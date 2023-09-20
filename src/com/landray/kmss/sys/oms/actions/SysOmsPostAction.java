package com.landray.kmss.sys.oms.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.sys.oms.model.SysOmsPost;
import com.landray.kmss.sys.oms.forms.SysOmsPostForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.oms.service.ISysOmsPostService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class SysOmsPostAction extends ExtendAction {

    private ISysOmsPostService sysOmsPostService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (sysOmsPostService == null) {
            sysOmsPostService = (ISysOmsPostService) getBean("sysOmsPostService");
        }
        return sysOmsPostService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, SysOmsPost.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.sys.oms.util.SysOmsUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.sys.oms.model.SysOmsPost.class);
        com.landray.kmss.sys.oms.util.SysOmsUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysOmsPostForm sysOmsPostForm = (SysOmsPostForm) super.createNewForm(mapping, form, request, response);
        ((ISysOmsPostService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return sysOmsPostForm;
    }
}
