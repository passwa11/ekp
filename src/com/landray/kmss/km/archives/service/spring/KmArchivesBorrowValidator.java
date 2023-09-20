package com.landray.kmss.km.archives.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.km.archives.model.KmArchivesDense;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.model.KmArchivesTemplate;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.km.archives.service.IKmArchivesTemplateService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.sso.client.util.StringUtil;

/**
 * 档案借阅校验器
 */
public class KmArchivesBorrowValidator implements IAuthenticationValidator {
	private IKmArchivesMainService kmArchivesMainService;
	public void setKmArchivesMainService(
			IKmArchivesMainService kmArchivesMainService) {
		this.kmArchivesMainService = kmArchivesMainService;
	}

	private IKmArchivesTemplateService kmArchivesTemplateService;

	public void setKmArchivesTemplateService(IKmArchivesTemplateService kmArchivesTemplateService) {
		this.kmArchivesTemplateService = kmArchivesTemplateService;
	}

	@Override
	/**
	 * 校验
	 */
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String fdId = validatorContext.getValidatorParaValue("recid");
		boolean result = false;
		if (StringUtil.isNotNull(fdId)) {
			KmArchivesMain kmArchivesMain = (KmArchivesMain) kmArchivesMainService.findByPrimaryKey(fdId);
			if (kmArchivesMain != null) {
				List<KmArchivesTemplate> kmArchivesTemplateList = null;
				KmArchivesDense kmArchivesDense = kmArchivesMain.getFdDense();
				if (kmArchivesDense != null) {
					List fdDenseIds = new ArrayList();
					fdDenseIds.add(kmArchivesDense.getFdId());
					kmArchivesTemplateList = kmArchivesTemplateService
							.getTemplateByDenses(fdDenseIds, false);
				} else {
					kmArchivesTemplateList = kmArchivesTemplateService
							.getTemplateByDenses(null, false);
				}
				if (kmArchivesTemplateList != null && !kmArchivesTemplateList.isEmpty()) {
					result = true;
				}
			}
		}
		return result;
	}
}
