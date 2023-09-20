package com.landray.kmss.hr.organization.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.organization.forms.HrOrganizationLogForm;
import com.landray.kmss.hr.organization.model.HrOrganizationLog;
import com.landray.kmss.hr.organization.service.IHrOrganizationLogService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class HrOrganizationLogAction extends ExtendAction {

    private IHrOrganizationLogService hrOrganizationLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrOrganizationLogService == null) {
            hrOrganizationLogService = (IHrOrganizationLogService) getBean("hrOrganizationLogService");
        }
        return hrOrganizationLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrOrganizationLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.organization.util.HrOrganizationUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.organization.model.HrOrganizationLog.class);
        com.landray.kmss.hr.organization.util.HrOrganizationUtil.buildHqlInfoModel(hqlInfo, request);
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		String ownId = request.getParameter("ownId");
		if (StringUtil.isNotNull(ownId)) {
			whereBlock += "and fdTargetId =:ownId";
			hqlInfo.setParameter("ownId", ownId);
		} else {
			whereBlock += "and 1=0";
		}
		hqlInfo.setWhereBlock(whereBlock);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrOrganizationLogForm hrOrganizationLogForm = (HrOrganizationLogForm) super.createNewForm(mapping, form, request, response);
        ((IHrOrganizationLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return hrOrganizationLogForm;
    }
}
