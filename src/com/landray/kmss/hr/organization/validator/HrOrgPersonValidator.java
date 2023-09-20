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

/**
 * <P>是否有当前部门新建员工权限</P>
 * @author sunj
 * @version 1.0 2020年4月1日
 */
public class HrOrgPersonValidator implements IAuthenticationValidator {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());


	private IHrOrganizationElementService hrOrganizationElementService;

	public IHrOrganizationElementService getHrOrganizationElementServiceImp() {
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
		if (!UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_PERSON_SCOPE")) {
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


}
