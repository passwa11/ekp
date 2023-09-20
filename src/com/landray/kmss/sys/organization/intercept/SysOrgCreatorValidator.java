package com.landray.kmss.sys.organization.intercept;

import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 外部组织可使用人员校验器
 * 
 * 校验条件：
 * 1.当前人在当前组织所属组织类型的可使用人员中
 * 2.该组织为当前人创建的组织或子组织
 * @author zby
 *
 */
public class SysOrgCreatorValidator implements IAuthenticationValidator,
	SysOrgConstant, BaseTreeConstant{
	
	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext) throws Exception {
		String fdId = validatorContext.getValidatorParaValue("recid");
		if (StringUtil.isNull(fdId)) {
			return false;
		}
		
		SysOrgElement element = sysOrgCoreService.findByPrimaryKey(fdId);
		if (element == null) {
			return false;
		}
		
		String method = validatorContext.getParameter("method");
		
		return sysOrgCoreService.isAvailablePerson(element, UserUtil.getUser(), method);
	}

}
