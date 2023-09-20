package com.landray.kmss.sys.organization.interfaces;

import javax.servlet.http.HttpServletRequest;

public interface ISysOrgPassUpdate {
	
	public boolean isPassUpdateEnable() throws Exception;
	
	public void changePassword(String loginName, String newPassword) throws Exception;
	
	public boolean validatePassword(HttpServletRequest request,
			String username, String password) throws Exception;

}
