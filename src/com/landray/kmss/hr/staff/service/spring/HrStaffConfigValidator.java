package com.landray.kmss.hr.staff.service.spring;

import com.landray.kmss.hr.staff.model.HrStaffConfig;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class HrStaffConfigValidator implements IAuthenticationValidator {

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		HrStaffConfig hrStaffConfig = new HrStaffConfig();
		boolean flag = "true".equals(hrStaffConfig.getFdSelfView());
		String recid = validatorContext.getValidatorParaValue("recid");
		if (StringUtil.isNotNull(recid)) {
			String userId = UserUtil.getKMSSUser().getUserId();
			flag = flag && recid.equals(userId);
		}
		return flag;
	}

}
