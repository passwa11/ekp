package com.landray.kmss.sys.attend.service.spring;

import java.util.List;

import com.landray.kmss.sys.attend.service.ISysAttendOrgService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 判断当前用户是不是机构，部门，岗位领导
 * 
 * @author admin
 *
 */
public class SysAttendOrgValidator implements IAuthenticationValidator {

	private ISysAttendOrgService sysAttendOrgService;

	public void
			setSysAttendOrgService(ISysAttendOrgService sysAttendOrgService) {
		this.sysAttendOrgService = sysAttendOrgService;
	}

	@Override
    public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		SysOrgPerson user = validatorContext.getUser().getPerson();
		if (validatorContext.getUser().getPerson() != null) {
			List orgIds = sysAttendOrgService.findOrgIdsByLeader(user);
			if (orgIds != null && !orgIds.isEmpty()) {
				return true;
			}
		}
		return false;
	}

}
