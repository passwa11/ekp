package com.landray.kmss.sys.portal.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.portal.forms.SysPortalMaterialTagForm;
import com.landray.kmss.sys.portal.model.SysPortalNav;
import com.landray.kmss.sys.portal.service.ISysPortalMaterialTagService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class SysPortalMaterialTagAction extends ExtendAction {

    private ISysPortalMaterialTagService sysPortalMaterialTagService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (sysPortalMaterialTagService == null) {
            sysPortalMaterialTagService = (ISysPortalMaterialTagService) getBean("sysPortalMaterialTagService");
        }
        return sysPortalMaterialTagService;
    }

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		String where = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(where)) {
			where = " 1=1 ";
		}
		hqlInfo.setWhereBlock(where);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysPortalNav.class);
		if (StringUtil.isNotNull(request.getParameter("config"))) {
			hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_EDITOR);
		}
	}

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysPortalMaterialTagForm sysPortalMaterialTagForm = (SysPortalMaterialTagForm) super.createNewForm(mapping, form, request, response);
        ((ISysPortalMaterialTagService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return sysPortalMaterialTagForm;
    }
}
