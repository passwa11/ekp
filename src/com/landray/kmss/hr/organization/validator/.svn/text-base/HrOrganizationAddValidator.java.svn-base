package com.landray.kmss.hr.organization.validator;

import com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;

/**
 * <P>是否能新建人事组织模块相关数据权限</P>
 * @author sunj
 * @version 1.0 2020年3月20日
 */
public class HrOrganizationAddValidator implements IAuthenticationValidator {

	@Override
	public boolean validate(ValidatorRequestContext validatorContext) throws Exception {
		HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
		return "true".equals(syncSetting.getHrToEkpEnable()) ? true : false;
	}

}
