package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataPassportForm;
import com.landray.kmss.eop.basedata.model.EopBasedataPassport;
import com.landray.kmss.eop.basedata.service.IEopBasedataPassportService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EopBasedataPassportAction extends EopBasedataBusinessAction {

    private IEopBasedataPassportService eopBasedataPassportService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataPassportService == null) {
            eopBasedataPassportService = (IEopBasedataPassportService) getBean("eopBasedataPassportService");
        }
        return eopBasedataPassportService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String whereBlock=hqlInfo.getWhereBlock();
    	if(!UserUtil.checkRole("ROLE_EOPBASEDATA_PASSPORT")){
    		//若是无员工账号设置权限，只能查看自己的账户
    		whereBlock=StringUtil.linkString(whereBlock, " and ", "eopBasedataPassport.fdPerson.fdId=:fdUserId");
    		hqlInfo.setParameter("fdUserId", UserUtil.getUser().getFdId());
    	}
    	hqlInfo.setWhereBlock(whereBlock);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataPassport.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataPassportForm eopBasedataPassportForm = (EopBasedataPassportForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataPassportService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataPassportForm;
    }
}
