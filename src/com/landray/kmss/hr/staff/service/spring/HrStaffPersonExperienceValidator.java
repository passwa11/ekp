package com.landray.kmss.hr.staff.service.spring;

import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBase;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceBaseService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceContractService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 个人经历权限校验器
 * 
 * @author 潘永辉 2017-2-9
 * 
 */
public class HrStaffPersonExperienceValidator implements
		IAuthenticationValidator {
	private IHrStaffPersonExperienceBaseService experienceService;
	protected ISysPrintMainCoreService sysPrintMainCoreService;

	public ISysPrintMainCoreService getSysPrintMainCoreService() {
		if (sysPrintMainCoreService == null) {
            sysPrintMainCoreService = (ISysPrintMainCoreService) SpringBeanUtil
                    .getBean("sysPrintMainCoreService");
        }
		return sysPrintMainCoreService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		boolean result = false;
		String method = validatorContext.getParameter("method");

		if ("add".equals(method) || "edit".equals(method)) {
			String personInfoId = validatorContext.getParameter("personInfoId");
			result = UserUtil.checkUserId(personInfoId);
		}
		if ("save".equals(method) || "update".equals(method)) {
			String fdPersonInfoId = validatorContext
					.getParameter("fdPersonInfoId");
			result = UserUtil.checkUserId(fdPersonInfoId);
		}

		if ("delete".equals(method) || "deleteall".equals(method)) {
			String url = validatorContext.getRequestUrl();
			String str = url.substring(url.lastIndexOf("/") + 1, url
					.lastIndexOf(".do"));

			experienceService = (IHrStaffPersonExperienceBaseService) SpringBeanUtil
					.getBean(str + "Service");
			if ("delete".equals(method)) {
				String fdId = validatorContext.getParameter("fdId");
				result = _validate(fdId);
			}
			if ("deleteall".equals(method)) {
				String[] fdIds = validatorContext
						.getParameterValues("List_Selected");
				result = true;
				for (String fdId : fdIds) {
					if (!_validate(fdId)) {
						result = false;
						break;
					}
				}
			}
		}
		if ("print".equals(method)) {
			String url = validatorContext.getRequestUrl();
			String[] strs = url.split("/");
			String type = strs[strs.length - 2];
			if ("contract".equals(type)) {
				IHrStaffPersonExperienceContractService service = (IHrStaffPersonExperienceContractService) SpringBeanUtil
						.getBean("hrStaffPersonExperienceContractService");
				String fdId = validatorContext.getParameter("fdId");
				HrStaffPersonExperienceContract contract = (HrStaffPersonExperienceContract) service
						.findByPrimaryKey(fdId);
				HrStaffContractType contractType = contract
						.getFdStaffContType();
				if (contractType != null) {
					result = getSysPrintMainCoreService().isEnablePrintTemplate(
							contractType, null, Plugin.currentRequest());
				}
			}
		}
		return result;
	}

	private boolean _validate(String fdId) throws Exception {
		HrStaffPersonExperienceBase experienceBase = (HrStaffPersonExperienceBase) experienceService
				.findByPrimaryKey(fdId);
		if (experienceBase == null) {
			return false;
		}
		return UserUtil.checkUserId(experienceBase.getFdPersonInfo().getFdId());
	}

}
