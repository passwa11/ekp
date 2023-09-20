package com.landray.kmss.km.comminfo.service.spring;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.comminfo.model.KmComminfoCategory;
import com.landray.kmss.km.comminfo.service.IKmComminfoCategoryService;
import com.landray.kmss.sys.authentication.intercept.AuthCategoryValidator;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class AuthValidatorImp implements IAuthenticationValidator {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(AuthCategoryValidator.class);
	protected IKmComminfoCategoryService kmComminfoCategoryService;

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {

		IBaseModel recModel = validatorContext.getRecModel();
		// 用于文档或模板
		String property = validatorContext.getValidatorPara("property");
		if (StringUtil.isNotNull(property)) {
			if (recModel != null) {
				if (logger.isDebugEnabled()) {
                    logger.debug("通过文档ID校验权限");
                }
				IBaseModel template = (IBaseModel) PropertyUtils.getProperty(
						recModel, property);
				if (template != null) {
					if (template instanceof KmComminfoCategory) {
                        return validateCategory(template.getFdId());
                    }
				} else if (logger.isDebugEnabled()) {
                    logger.debug("文档模板为空，校验失败");
                }
			}
		}

		return false;
	}

	private boolean validateCategory(String fdId) throws Exception {
		KmComminfoCategory categoryModel = (KmComminfoCategory) kmComminfoCategoryService
				.findByPrimaryKey(fdId, null, true);
		boolean flag = UserUtil.checkUserModels(categoryModel.getAuthEditors());
		return flag;
	}

	public IKmComminfoCategoryService getKmComminfoCategoryService() {
		return kmComminfoCategoryService;
	}

	public void setKmComminfoCategoryService(
			IKmComminfoCategoryService kmComminfoCategoryService) {
		this.kmComminfoCategoryService = kmComminfoCategoryService;
	}

}
