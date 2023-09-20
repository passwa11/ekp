package com.landray.kmss.eop.basedata.authvalidator;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

/*******************************************************************************
 * 校验当前登录人员是否有查看该公司权限
 * 
 * @author xiexx
 * @version 1.0 2019-4-26
 * 
 */
public class EopBasedataCompanyAuthValidator implements IAuthenticationValidator {
	  private IEopBasedataCompanyService eopBasedataCompanyService;
	
	  public IEopBasedataCompanyService getServiceImp() {
	        if (eopBasedataCompanyService == null) {
	        	eopBasedataCompanyService = (IEopBasedataCompanyService) SpringBeanUtil.getBean("eopBasedataCompanyService");
	        }
	        return eopBasedataCompanyService;
	    }

	@Override
    public boolean validate(ValidatorRequestContext validatorrequestcontext)
			throws Exception {
		boolean auth=false;  //默认无权限
		String fdId =validatorrequestcontext.getParameter("fdId");
		EopBasedataCompany company=(EopBasedataCompany) getServiceImp().findByPrimaryKey(fdId);
		List<EopBasedataCompany> companyList=getServiceImp().findCompanyByUserId(UserUtil.getUser().getFdId());
		if(companyList.contains(company)){
			auth=true;
		}
		return auth;
	}
}
