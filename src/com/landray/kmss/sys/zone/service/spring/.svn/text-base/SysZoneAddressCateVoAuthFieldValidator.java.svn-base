package com.landray.kmss.sys.zone.service.spring;

import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.zone.model.SysZoneAddressCateVo;
import com.landray.kmss.sys.zone.service.ISysZoneAddressCateVoService;

public class SysZoneAddressCateVoAuthFieldValidator
		implements IAuthenticationValidator {
	private ISysZoneAddressCateVoService sysZoneAddressCateVoService;

	public void setSysZoneAddressCateVoService(
			ISysZoneAddressCateVoService sysZoneAddressCateVoService) {
		this.sysZoneAddressCateVoService = sysZoneAddressCateVoService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String cateId = validatorContext.getValidatorParaValue("recid");
		if (StringUtil.isNotNull(cateId)) {
			SysZoneAddressCateVo cateVo = sysZoneAddressCateVoService
					.getCateVoByCateId(cateId);
			if (cateVo != null) {
				String itemType = cateVo.getFdItemType();
				if ("private".equals(itemType)) {
					return true;
				}
			}
		}
		return false;
	}

}
