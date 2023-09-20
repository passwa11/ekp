package com.landray.kmss.hr.organization.validator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.util.HrOrgAuthorityUtil;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class HrOrgCompileValidator implements IAuthenticationValidator {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	private static IHrOrganizationElementService hrOrganizationElementService;

	private static IHrOrganizationElementService getHrOrganizationElementServiceImp() {
		if (hrOrganizationElementService == null) {
			hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil
					.getBean("hrOrganizationElementService");
		}
		return hrOrganizationElementService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext) throws Exception {
		String id = validatorContext.getParameter("fdId");
		StringBuffer whereBlock = new StringBuffer();
		HQLInfo hqlInfo = new HQLInfo();

		List<String> orgIds = HrOrgAuthorityUtil.getOrgIds();
		if (ArrayUtil.isEmpty(orgIds)) {
			return false;
		}
		if (!UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_COMPILE_SCOPE")) {
			return false;
		}
		for (int i = 0; i < orgIds.size(); i++) {
			String orgId = orgIds.get(i);
			if ((orgIds.size() - 1) == i) {
				whereBlock.append("(hrOrganizationElement.fdHierarchyId like :orgid_" + i + ")");
			} else {
				whereBlock.append("(hrOrganizationElement.fdHierarchyId like :orgid_" + i + ") or ");
			}
			hqlInfo.setParameter("orgid_" + i, "%" + orgId + "%");
			hqlInfo.setSelectBlock("fdId");
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		List list = getHrOrganizationElementServiceImp().findList(hqlInfo);
		return list.contains(id);
	}

	public static boolean validateCompileRole(String deptId) throws Exception {
		Boolean isAdmin = UserUtil.getKMSSUser().isAdmin();
		if (isAdmin || UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_ADMIN")) {//系统管理员或者拥有阅读人事组织机构管理员直接放行
			return true;
		}

		StringBuffer whereBlock = new StringBuffer();
		HQLInfo hqlInfo = new HQLInfo();
		if (UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_COMPILE_SCOPE")) {
			return false;
		}
		if (UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_COMPILE")) {
			return true;
		}
		List<String> orgIds = HrOrgAuthorityUtil.getOrgIds();
		if (ArrayUtil.isEmpty(orgIds)) {
			return false;
		}

		for (int i = 0; i < orgIds.size(); i++) {
			String orgId = orgIds.get(i);
			if ((orgIds.size() - 1) == i) {
				whereBlock.append("(hrOrganizationElement.fdHierarchyId like :orgid_" + i + ")");
			} else {
				whereBlock.append("(hrOrganizationElement.fdHierarchyId like :orgid_" + i + ") or ");
			}
			hqlInfo.setParameter("orgid_" + i, "%" + orgId + "%");
			hqlInfo.setSelectBlock("fdId");
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		List list = getHrOrganizationElementServiceImp().findList(hqlInfo);
		return list.contains(deptId);
	}
}
