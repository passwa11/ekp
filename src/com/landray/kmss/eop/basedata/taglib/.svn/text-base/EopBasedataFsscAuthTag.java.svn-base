package com.landray.kmss.eop.basedata.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.eop.basedata.util.EopBasedataAuthUtil;
import com.landray.kmss.util.StringUtil;

public class EopBasedataFsscAuthTag extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6109108270791060029L;
	/**
	 * 权限类型
	 */
	private String authType;
	
	public String getAuthType() {
		return authType;
	}


	public void setAuthType(String authType) {
		this.authType = authType;
	}

	/**
	 * 公司ID
	 */
	private String fdCompanyId;
	
	public String getFdCompanyId() {
		return fdCompanyId;
	}


	public void setFdCompanyId(String fdCompanyId) {
		this.fdCompanyId = fdCompanyId;
	}


	@Override
    public int doStartTag()throws JspException{
		int reVal=0;
		if (StringUtil.isNotNull(authType)) {
			try {
				if("staff".equals(authType)){
					reVal= EopBasedataAuthUtil.isStaff(fdCompanyId)?1:0;
				}else if("manager".equals(authType)){
					reVal= EopBasedataAuthUtil.isManager(fdCompanyId)?1:0;
				}else if("staffOrmanager".equals(authType)){
					reVal= EopBasedataAuthUtil.isManagerOrStaff(fdCompanyId)?1:0;
				}
			} catch (NumberFormatException e) {
				reVal=0;
			} catch (Exception e) {
				reVal=0;
			}
		}else {
			reVal=0;
		}
		return reVal;
	}

}
