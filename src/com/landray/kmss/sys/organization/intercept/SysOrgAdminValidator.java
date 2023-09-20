package com.landray.kmss.sys.organization.intercept;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 部门/机构管理员校验器
 * 
 * @author yirf
 * 
 */
public class SysOrgAdminValidator implements IAuthenticationValidator,
		SysOrgConstant, BaseTreeConstant {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgAdminValidator.class);

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	/**
	 * 校验当前用户是否指定机构/部门的管理员
	 * 
	 * @param validatorContext
	 * @param orgelem
	 * @return
	 * @throws Exception
	 */
	private boolean checkUseOrgElement(
			ValidatorRequestContext validatorContext, SysOrgElement orgelem)
			throws Exception {
		if (orgelem == null) {
			if (logger.isDebugEnabled()) {
                logger.debug("获取组织机构信息失败，校验失败");
            }
			return false;
		}
		return sysOrgCoreService.checkPersonIsOrgAdmin(UserUtil.getUser(),
				orgelem, validatorContext.getParameter("method"));
	}

	/**
	 * 校验主函数
	 */
	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String[] roles = validatorContext.getValidatorPara("role").split(
				"\\s*;\\s*");
		if (!ArrayUtil.isListIntersect(validatorContext.getUser()
				.getUserAuthInfo().getAuthRoleAliases(), Arrays.asList(roles))) {
            return false;
        }
		String orgid = validatorContext.getValidatorPara("orgid");
		if (StringUtil.isNull(orgid)) {
            return validateModel(validatorContext);
        } else {
            return validateRequest(validatorContext);
        }
	}

	/**
	 * 采用域模型的方式进行校验
	 * 
	 * @param validatorContext
	 * @return
	 * @throws Exception
	 */
	private boolean validateModel(ValidatorRequestContext validatorContext)
			throws Exception {
		SysOrgElement model = (SysOrgElement) validatorContext.getRecModel();
		//alter by zhanglz,非管理员用户执行校验时 可能为空，抛出异常后“|”后续的校验无法进行，故屏蔽此异常，返回false
		if (model == null)
		{
			if(logger.isDebugEnabled())
			{
				logger.debug("(SysOrgElement) validatorContext.getRecModel() is null! Warning!");
			}
			return false;
		}
		return checkUseOrgElement(validatorContext, model);
	}

	/**
	 * 采用请求的方式进行校验
	 * 
	 * @param validatorContext
	 * @return
	 * @throws Exception
	 */
	private boolean validateRequest(ValidatorRequestContext validatorContext)
			throws Exception {
		SysOrgElement model = (SysOrgElement) validatorContext.getRecModel();
		String orgid = validatorContext.getValidatorParaValue("orgid");
		if (StringUtil.isNull(orgid)) {
			/*
			if (logger.isDebugEnabled()) {
                logger.debug("从请求中获取组织机构信息为空，校验失败");
            }
			return false;
			*/
			/* 无父级部门信息-权限同域模型方式校验 */
			return checkUseOrgElement(validatorContext, model);
		}
		if (logger.isDebugEnabled()) {
            logger.debug("从请求中获取组织机构信息进行校验");
        }
		orgid = orgid.trim();
		SysOrgElement orgelem = sysOrgCoreService.findByPrimaryKey(orgid);
		if (model != null && model.getFdParent() != null) {
			String fdParentId = model.getFdParent().getFdId();
			// 原本（高级权限人员）已设置的上级部门 - 无须判断父级部门权限 -权限同域模型方式校验
			if (fdParentId.equals(orgid)) {
				return checkUseOrgElement(validatorContext, model);
			}
		}

		//维护管理范围下的部门 - 判断父级部门下自己是否有权限
		return checkUseOrgElement(validatorContext, orgelem);
	}
}
