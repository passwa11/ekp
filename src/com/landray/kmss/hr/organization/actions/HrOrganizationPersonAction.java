package com.landray.kmss.hr.organization.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.organization.service.IHrOrganizationPersonService;

public class HrOrganizationPersonAction extends ExtendAction {

    private IHrOrganizationPersonService hrOrganizationPersonService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrOrganizationPersonService == null) {
            hrOrganizationPersonService = (IHrOrganizationPersonService) getBean("hrOrganizationPersonService");
        }
        return hrOrganizationPersonService;
    }


}
