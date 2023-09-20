package com.landray.kmss.sys.organization.intercept;

import java.util.List;

import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.util.StringUtil;

/**
 * 组织类型管理员校验器
 * @author zby
 *
 */
public class ExternalOrgAdminValidator implements IAuthenticationValidator,
	SysOrgConstant, BaseTreeConstant{
	
	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext) throws Exception {
		String elemId = validatorContext.getValidatorPara("elemId");
		
		if (StringUtil.isNull(elemId)) {
			elemId = validatorContext.getValidatorParaValue("recid");
		} else {
			elemId = validatorContext.getValidatorParaValue("elemId");
		}
		
		List<String> admindIds = sysOrgCoreService.findAdminIdsByElemId(elemId);
		// 将岗位展开为人员id
		List<String> personIds = sysOrgCoreService.expandToPersonIds(admindIds);
		String userId = validatorContext.getUser().getPerson().getFdId();
		return personIds.contains(userId);
	}

}
