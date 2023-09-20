package com.landray.kmss.hr.ratify.validator;

import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.util.UserUtil;

/**
 * <P>效验是否有人事档案默认权限</P>
 * @author sunj
 * @version 1.0 2020年7月8日
 */
public class HrStaffDefaultAuthValidator implements IAuthenticationValidator {

	@Override
	public boolean validate(ValidatorRequestContext validatorContext) throws Exception {
		return UserUtil.checkRole("ROLE_HRSTAFF_DEFAULT");
	}

}
