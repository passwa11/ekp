package com.landray.kmss.eop.basedata.authvalidator;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataAuthUtil;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.util.SpringBeanUtil;


/*******************************************************************************
 * 校验当前人员是否是财务人员或者财务管理员，
 * 若有公司，校验当前人员是否是当前公司财务人员或者财务管理员
 * 
 * @author
 * @version 1.0 2019-4-22
 * 
 */
public class EopBasedataStaffAndManagerAuthValidator implements IAuthenticationValidator {
	
	private IEopBasedataCompanyService eopBasedataCompanyService;
	
	  public IEopBasedataCompanyService getServiceImp() {
	        if (eopBasedataCompanyService == null) {
	        	eopBasedataCompanyService = (IEopBasedataCompanyService) SpringBeanUtil.getBean("eopBasedataCompanyService");
	        }
	        return eopBasedataCompanyService;
	    }
	  /**
	   * 判断当前登录人是否为财务人员或者财务管理员
	   */
	  @Override
      public boolean validate(ValidatorRequestContext validatorrequestcontext)
				throws Exception {
			boolean auth=Boolean.TRUE;  //默认有权限
			String fdId =validatorrequestcontext.getParameter("fdId");
			EopBasedataCompany company=(EopBasedataCompany) getServiceImp().findByPrimaryKey(fdId,EopBasedataCompany.class,true);//该ID可能并不是公司ID
			if(company!=null){
				auth=EopBasedataAuthUtil.isManagerOrStaff(company.getFdId());
			}
			return auth;
		}
}
