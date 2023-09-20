package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataBudgetSchemeForm;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetSchemeService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class EopBasedataBudgetSchemeAction extends EopBasedataBusinessAction {

    private IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataBudgetSchemeService == null) {
            eopBasedataBudgetSchemeService = (IEopBasedataBudgetSchemeService) getBean("eopBasedataBudgetSchemeService");
        }
        return eopBasedataBudgetSchemeService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataBudgetScheme.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataBudgetSchemeForm eopBasedataBudgetSchemeForm = (EopBasedataBudgetSchemeForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataBudgetSchemeService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        request.setAttribute("version", EopBasedataFsscUtil.checkVersion("true"));
        return eopBasedataBudgetSchemeForm;
    }
    
    /**
   	 * 重写loadActionForm，编辑时获取是否启用公司组
   	 * 
   	 * @param form
   	 * @param request
   	 * @return form对象
   	 * @throws Exception
   	 */
   	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
   			throws Exception {
   		super.loadActionForm(mapping, form, request, response);
   		request.setAttribute("fdCompanyGroup", EopBasedataFsscUtil.getSwitchValue("fdCompanyGroup"));
   		request.setAttribute("version", EopBasedataFsscUtil.checkVersion("true"));
   	}
}
