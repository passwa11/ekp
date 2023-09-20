package com.landray.kmss.sys.zone.intercept;

import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

public class SysZoneIsSelfValidator implements IAuthenticationValidator {

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String id = validatorContext.getValidatorParaValue("recid");
		if (StringUtil.isNull(id)) {
            throw new UnexpectedRequestException();
        }
		// 判断修改的目标
		HttpServletRequest request = validatorContext.getRequest();
		if (request != null) {
			// 获取要修改的目标，是否自己
			String fdModelId = request.getParameter("sysTagMainForm.fdModelId");
			if (StringUtil.isNotNull(fdModelId) && !fdModelId.equals(id)) {
				return false;
			}
		}
		if(id.equals(validatorContext.getUser().getUserId())) {
			return true;
		} else {
			return false;
		}
	}
}
