package com.landray.kmss.sys.organization.filter;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixDataCateService;
import com.landray.kmss.util.StringUtil;

/**
 * 矩阵数据分组维护者校验器
 * 
 * @author panyh
 *
 */
public class AuthOrgMatrixDataCateValidator implements IAuthenticationValidator {

	private ISysOrgMatrixDataCateService sysOrgMatrixDataCateService;

	public void setSysOrgMatrixDataCateService(ISysOrgMatrixDataCateService sysOrgMatrixDataCateService) {
		this.sysOrgMatrixDataCateService = sysOrgMatrixDataCateService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext) throws Exception {
		String fdId = validatorContext.getParameter("fdId");
		if (StringUtil.isNotNull(fdId)) {
			List list = sysOrgMatrixDataCateService.getDataCates(fdId);
			return CollectionUtils.isNotEmpty(list);
		}
		return false;
	}

}
