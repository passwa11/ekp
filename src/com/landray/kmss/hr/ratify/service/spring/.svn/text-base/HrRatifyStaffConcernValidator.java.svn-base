package com.landray.kmss.hr.ratify.service.spring;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.ratify.model.HrRatifyStaffConcernConfig;
import com.landray.kmss.hr.ratify.service.IHrRatifyStaffConcernConfigService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;

public class HrRatifyStaffConcernValidator implements IAuthenticationValidator {

	private IHrRatifyStaffConcernConfigService hrRatifyStaffConcernConfigService;

	public void setHrRatifyStaffConcernConfigService(
			IHrRatifyStaffConcernConfigService hrRatifyStaffConcernConfigService) {
		this.hrRatifyStaffConcernConfigService = hrRatifyStaffConcernConfigService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String fdType = validatorContext.getValidatorPara("fdType");
		KMSSUser kmssUser = UserUtil.getKMSSUser();
		boolean flag = kmssUser.isAdmin();
		if (!flag) {
			List<String> authOrgIds = kmssUser.getUserAuthInfo()
					.getAuthOrgIds();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"hrRatifyStaffConcernConfig." + fdType + "=:fdType");
			hqlInfo.setParameter("fdType", new Boolean(true));
			List<HrRatifyStaffConcernConfig> list = hrRatifyStaffConcernConfigService
					.findList(hqlInfo);
			Set<String> set = new HashSet<String>();
			for (HrRatifyStaffConcernConfig config : list) {
				List<SysOrgElement> managers = config.getFdManagers();
				for (SysOrgElement org : managers) {
					set.add(org.getFdId());
				}
			}
			flag = ArrayUtil.isArrayIntersect(authOrgIds.toArray(),
					set.toArray());
		}
		return flag;
	}

}
