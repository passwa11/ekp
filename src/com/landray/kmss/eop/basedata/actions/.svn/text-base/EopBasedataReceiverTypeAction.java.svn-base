package com.landray.kmss.eop.basedata.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.eop.basedata.model.EopBasedataReceiverType;
import com.landray.kmss.eop.basedata.forms.EopBasedataReceiverTypeForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.eop.basedata.service.IEopBasedataReceiverTypeService;
import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

public class EopBasedataReceiverTypeAction extends EopBasedataBusinessAction {

    private IEopBasedataReceiverTypeService eopBasedataReceiverTypeService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataReceiverTypeService == null) {
            eopBasedataReceiverTypeService = (IEopBasedataReceiverTypeService) getBean("eopBasedataReceiverTypeService");
        }
        return eopBasedataReceiverTypeService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataReceiverType.class);
        String fdCompanyName=request.getParameter("q.fdCompanyName");
        if(StringUtil.isNotNull(fdCompanyName)) {
            hqlInfo.setJoinBlock(" left join eopBasedataReceiverType.fdCompanyList company ");
            hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "company.fdName like :fdCompanyName"));
            hqlInfo.setParameter("fdCompanyName", "%"+fdCompanyName+"%");
        }
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, EopBasedataReceiverType.class);
        EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataReceiverTypeForm eopBasedataReceiverTypeForm = (EopBasedataReceiverTypeForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataReceiverTypeService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataReceiverTypeForm;
    }
}
