package com.landray.kmss.sys.attend.service.spring;

import java.util.List;

import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;

public class SysAttendCateReaderValidator implements IAuthenticationValidator {

	private ISysAttendCategoryService sysAttendCategoryService;

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String field = validatorContext.getValidatorPara("cateType");
		SysOrgElement user = validatorContext.getUser().getPerson();
		if ("1".equals(field) || "2".equals(field)) {
			List cateIds = sysAttendCategoryService
					.findCateIdsByReaderId(user.getFdId(),
							Integer.parseInt(field));
			if (cateIds != null && !cateIds.isEmpty()) {
				return true;
			}
		}
		return false;
	}

}
