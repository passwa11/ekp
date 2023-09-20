package com.landray.kmss.sys.oms.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.sys.oms.model.SysOmsPerson;
import com.landray.kmss.sys.oms.forms.SysOmsPersonForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.oms.service.ISysOmsPersonService;
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

public class SysOmsPersonAction extends ExtendAction {

    private ISysOmsPersonService sysOmsPersonService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (sysOmsPersonService == null) {
            sysOmsPersonService = (ISysOmsPersonService) getBean("sysOmsPersonService");
        }
        return sysOmsPersonService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, SysOmsPerson.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.sys.oms.util.SysOmsUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.sys.oms.model.SysOmsPerson.class);
        com.landray.kmss.sys.oms.util.SysOmsUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysOmsPersonForm sysOmsPersonForm = (SysOmsPersonForm) super.createNewForm(mapping, form, request, response);
        ((ISysOmsPersonService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return sysOmsPersonForm;
    }
}
