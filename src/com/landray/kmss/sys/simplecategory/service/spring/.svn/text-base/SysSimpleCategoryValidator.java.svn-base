package com.landray.kmss.sys.simplecategory.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryAuthService;
import com.landray.kmss.sys.simplecategory.service.SysSimpleCategoryAuthServiceImp;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 简单分类权限校验器,验证用户是否是分类的可维护者
 * 
 * @author wubing
 * 
 */
public class SysSimpleCategoryValidator implements IAuthenticationValidator {
	private String fieldType;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		IBaseModel recModel = validatorContext.getRecModel();
		if (recModel != null
				&& SysAuthAreaUtils
						.isAreaEnabled(recModel.getClass().getName())
				&& "editors".equalsIgnoreCase(fieldType)) {
			boolean isAvailable = SysAuthAreaUtils.isAvailableModel(recModel,
					AreaIsolation.CHILD);
			if (!isAvailable) {
				if (logger.isDebugEnabled()) {
                    logger.debug("当前用户无权操作上级场所的数据，校验失败");
                }
				return false;
			}
		}

		String model = validatorContext.getValidatorPara("model");
		String id = validatorContext.getValidatorParaValue("recid");
		String adminRoleName = validatorContext
				.getValidatorPara("adminRoleName");
		if (logger.isDebugEnabled()) {
			logger.debug("adminRoleName::" + adminRoleName);
		}
		SysDictModel dict = SysDataDict.getInstance().getModel(model);
		ISysSimpleCategoryAuthService sysSimpleCategoryAuthService = new SysSimpleCategoryAuthServiceImp();
		sysSimpleCategoryAuthService
				.setBaseService((IBaseService) SpringBeanUtil.getBean(dict
						.getServiceBean()));
		sysSimpleCategoryAuthService.setAdminRoleName(adminRoleName);

		if (model.equals(sysSimpleCategoryAuthService.getModelName())) {
			if (logger.isDebugEnabled()) {
				logger.debug("类别权限校验，校验角色访问权限"
						+ sysSimpleCategoryAuthService.getAdminRoleName());
			}
			// 类别权限判断
			if (sysSimpleCategoryAuthService.isAdmin()) {
				return true;
			}
			if (StringUtil.isNull(id)) {
				if (logger.isDebugEnabled()) {
                    logger.debug("获取不到类别树Id，校验失败");
                }
				return false;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("类别权限校验，校验类别访问权限" + id);
			}
			return validateService(id, fieldType, sysSimpleCategoryAuthService);
		}
		return false;
	}

	private boolean validateService(String fdId, String fieldType,
			ISysSimpleCategoryAuthService sysSimpleCategoryAuthService)
			throws Exception {
		if ("editors".equalsIgnoreCase(fieldType)) {
			return sysSimpleCategoryAuthService.isEditors(fdId);
		} else {
			return sysSimpleCategoryAuthService.isReaders(fdId);
		}
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
}
