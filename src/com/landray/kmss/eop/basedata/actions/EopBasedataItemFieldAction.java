package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataItemFieldForm;
import com.landray.kmss.eop.basedata.model.EopBasedataItemField;
import com.landray.kmss.eop.basedata.service.IEopBasedataItemFieldService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class EopBasedataItemFieldAction extends EopBasedataBusinessAction {

    private IEopBasedataItemFieldService eopBasedataItemFieldService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataItemFieldService == null) {
            eopBasedataItemFieldService = (IEopBasedataItemFieldService) getBean("eopBasedataItemFieldService");
        }
        return eopBasedataItemFieldService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String where = hqlInfo.getWhereBlock();
    	String fdCompanyId = request.getParameter("fdCompanyId");
    	if(StringUtil.isNotNull(fdCompanyId)){
    		where = StringUtil.linkString(where, " and ", "eopBasedataItemField.fdCompany.fdId=:fdCompanyId");
    		hqlInfo.setParameter("fdCompanyId",fdCompanyId);
    	}
    	hqlInfo.setWhereBlock(where);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataItemField.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataItemFieldForm eopBasedataItemFieldForm = (EopBasedataItemFieldForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataItemFieldService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataItemFieldForm;
    }
}
