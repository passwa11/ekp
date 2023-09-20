package com.landray.kmss.hr.organization.service.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.util.HrOrgDialogUtil;
import com.landray.kmss.sys.authentication.intercept.RoleValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.service.spring.OrgDialogUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * <P>人事组织架构地址本数据源</P>
 * @version 1.0 2020年1月2日
 */
public class HrOrgDialogList implements IXMLDataBean, HrOrgConstant {

	private IHrOrganizationElementService hrOrganizationElementService;

	public IHrOrganizationElementService getHrOrganizationElementService() {
		return hrOrganizationElementService;
	}

	public void setHrOrganizationElementService(IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	private RoleValidator roleValidator;

	public void setRoleValidator(RoleValidator roleValidator) {
		this.roleValidator = roleValidator;
	}

	public RoleValidator getRoleValidator() {
		return roleValidator;
	}

	/**
	 * 不做组织过滤
	 * 
	 * @param xmlContext
	 * @return
	 * @throws Exception
	 */
	public List getDataListAll(RequestContext xmlContext) throws Exception {

		// 查找组织架构列表数据
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock;
		int orgType = HR_TYPE_DEFAULT;
		String deptLimit = xmlContext.getParameter("deptLimit");
		String para = xmlContext.getParameter("parent");

		String fdParentId = para;
		if (StringUtil.isNull(para)) {
			if (StringUtil.isNotNull(deptLimit) && !"undefined".equals(deptLimit)) {
				Set<String> elementIds = OrgDialogUtil.getRootOrgIds(deptLimit);
				if (elementIds == null || elementIds.isEmpty()) {
					return new ArrayList();
				} else {
					whereBlock = "hrOrganizationElement.fdId in (" + SysOrgUtil.buildInBlock(elementIds) + ")";
				}
			} else {
				whereBlock = "hrOrganizationElement.hbmParent=null";
			}
		} else {
			whereBlock = "hrOrganizationElement.hbmParent.fdId=:hbmParentId";
			hqlInfo.setParameter("hbmParentId", para);
		}
		para = xmlContext.getParameter("orgType");
		if (para != null && !"".equals(para)) {
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {
			}
		}
		orgType = orgType & (HR_TYPE_ALLORG | HR_FLAG_AVAILABLEALL | HR_FLAG_BUSINESSALL);
		whereBlock = SysOrgHQLUtil.buildWhereBlock(orgType, whereBlock, "hrOrganizationElement");
		hqlInfo.setWhereBlock(whereBlock);
		// 多语言
		hqlInfo.setOrderBy("hrOrganizationElement.fdOrgType desc, hrOrganizationElement.fdOrder, hrOrganizationElement."
				+ SysLangUtil.getLangFieldName("fdName"));

		hqlInfo.setRowSize(SysOrgUtil.LIMIT_RESULT_SIZE + 1);
		hqlInfo.setGetCount(false);
		hqlInfo.setAuthCheckType("DIALOG_READER");
		List<HrOrganizationElement> elemList = hrOrganizationElementService.findPage(hqlInfo).getList();

		//elemList = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult(elemList);

		if (elemList.size() > SysOrgUtil.LIMIT_RESULT_SIZE) {
			return HrOrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath());
		}

		// 点击部门或机构时，把这部门或机构下的所有岗位中的人员，加到列表中去
		if ((orgType & HR_TYPE_PERSON) == HR_TYPE_PERSON && StringUtil.isNotNull(fdParentId)) {
			HQLInfo info = new HQLInfo();
			whereBlock = SysOrgHQLUtil.buildWhereBlock(HR_TYPE_POST,
					"hrOrganizationElement.hbmParent.fdId=:hbmParentId", "hrOrganizationElement");

			info.setParameter("hbmParentId", fdParentId);
			info.setWhereBlock(whereBlock);
			info.setOrderBy("hrOrganizationElement.fdOrder");
			List<HrOrganizationElement> postList = hrOrganizationElementService.findList(info);
			for (HrOrganizationElement post : postList) {
				List<?> ss = post.getFdPersons();
				//ss = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult((List<SysOrgElement>) ss);
				for (int i = 0; i < ss.size(); i++) {
					HrOrganizationElement soeOri = ((HrOrganizationElement) ss.get(i));
					HrOrganizationElement soe = new HrOrganizationElement();
					cloneOrg(soe, soeOri);
					if (soe.getFdIsAvailable() && soe.getFdIsBusiness() && elemList != null
							&& !elemList.contains(soe)) {
						soe.setFdOrder(post.getFdOrder());
						elemList.add(soe);
						if (elemList.size() > SysOrgUtil.LIMIT_RESULT_SIZE) {
							return HrOrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath());
						}
					}
				}
			}

			Collections.sort(elemList, new Comparator() {

				@Override
				public int compare(Object o1, Object o2) {
					HrOrganizationElement org1 = (HrOrganizationElement) o1;
					HrOrganizationElement org2 = (HrOrganizationElement) o2;
					Integer i1 = org1.getFdOrder() == null ? Integer.MAX_VALUE : org1.getFdOrder();
					Integer i2 = org2.getFdOrder() == null ? Integer.MAX_VALUE : org2.getFdOrder();
					if (org1.getFdOrgType().equals(org2.getFdOrgType())) {
						if (i1.equals(i2)) {
							return 0;
						} else if (i1 > i2) {
							return 1;
						} else {
							return -1;
						}
					} else if (org1.getFdOrgType() > org2.getFdOrgType()) {
						return -1;
					} else {
						return 1;
					}
				}
			});
		}
		return HrOrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath());
	}

	/**
	 * 克隆所需的属性
	 * 
	 * @param newOrg
	 * @param oldOrg
	 */
	private void cloneOrg(HrOrganizationElement newOrg, HrOrganizationElement oldOrg) {
		newOrg.setFdId(oldOrg.getFdId());
		newOrg.setFdName(oldOrg.getFdName());
		newOrg.setFdOrgType(oldOrg.getFdOrgType());
		newOrg.setFdIsAvailable(oldOrg.getFdIsAvailable());
		newOrg.setFdHierarchyId(oldOrg.getFdHierarchyId());
		newOrg.setFdParent(oldOrg.getFdParent());
		newOrg.setFdIsBusiness(oldOrg.getFdIsBusiness());
		newOrg.setFdPersons(oldOrg.getFdPersons());
		newOrg.setFdMemo(oldOrg.getFdMemo());
		newOrg.setFdOrder(oldOrg.getFdOrder());
	}

	@Override
	public List getDataList(RequestContext xmlContext) throws Exception {
		String deptLimit = xmlContext.getParameter("deptLimit");

		KMSSUser user = UserUtil.getKMSSUser();
		if (user != null && user.isAdmin()) {
			return getDataListAll(xmlContext);
		}

		String sys_page = xmlContext.getParameter("sys_page");
		// 如果是管理页面
		String validatorParas = "role=ROLE_SYSORG_DIALOG_USER";
		if ("true".equals(sys_page)) {
			validatorParas = "role=ROLE_SYSORG_ORG_ADMIN";
		}
		ValidatorRequestContext context = new ValidatorRequestContext();
		context.setUser(UserUtil.getKMSSUser());
		context.addValidatorParas(validatorParas);
		if (roleValidator.validate(context)) {
			return getDataListAll(xmlContext);
		}

		/*	Set<String> elementIds = sysOrganizationVisibleService.getPersonVisibleOrgIds(UserUtil.getUser());
		
			if (elementIds == null || elementIds.size() == 0) {
				return getDataListAll(xmlContext);
			}
		
			if (StringUtil.isNotNull(deptLimit) && !"undefined".equals(deptLimit)) {
				elementIds = OrgDialogUtil.getIntersection(deptLimit, elementIds);
				if (elementIds == null || elementIds.size() == 0) {
					return new ArrayList();
				}
			}*/

		// 查找组织架构列表数据
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock;
		int orgType = HR_TYPE_DEFAULT;
		String para = xmlContext.getParameter("parent");
		String fdParentId = para;
		if (StringUtil.isNull(para)) {
			whereBlock = "hrOrganizationElement.hbmParent=null";
		}
		//whereBlock = "hrOrganizationElement.fdId in (" + SysOrgUtil.buildInBlock(elementIds) + ")";
		else {
			whereBlock = "hrOrganizationElement.hbmParent.fdId=:hbmParentId and hrOrganizationElement.fdOrgType!=1";
			hqlInfo.setParameter("hbmParentId", para);
		}
		para = xmlContext.getParameter("orgType");
		if (para != null && !"".equals(para)) {
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {
			}
		}

		orgType = orgType & (HR_TYPE_ALLORG | HR_FLAG_AVAILABLEALL | HR_FLAG_BUSINESSALL);
		whereBlock = SysOrgHQLUtil.buildWhereBlock(orgType, whereBlock, "hrOrganizationElement");

		hqlInfo.setWhereBlock(whereBlock);
		// 多语言
		hqlInfo.setOrderBy("hrOrganizationElement.fdOrgType desc, hrOrganizationElement.fdOrder, hrOrganizationElement."
				+ SysLangUtil.getLangFieldName("fdName"));
		hqlInfo.setAuthCheckType("DIALOG_READER");
		hqlInfo.setRowSize(SysOrgUtil.LIMIT_RESULT_SIZE + 1);
		hqlInfo.setGetCount(false);
		List<HrOrganizationElement> elemList = hrOrganizationElementService.findPage(hqlInfo).getList();
		//elemList = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult(elemList);
		if (elemList.size() > SysOrgUtil.LIMIT_RESULT_SIZE) {
			return HrOrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath());
		}

		// 点击部门或机构时，把这部门或机构下的所有岗位中的人员，加到列表中去
		if ((orgType & HR_TYPE_PERSON) == HR_TYPE_PERSON && StringUtil.isNotNull(fdParentId)) {
			HQLInfo info = new HQLInfo();
			whereBlock = SysOrgHQLUtil.buildWhereBlock(HR_TYPE_POST,
					"hrOrganizationElement.hbmParent.fdId=:hbmParentId", "hrOrganizationElement");

			info.setParameter("hbmParentId", fdParentId);
			info.setWhereBlock(whereBlock);
			info.setOrderBy("hrOrganizationElement.fdOrder");
			List<HrOrganizationElement> postList = hrOrganizationElementService.findList(info);

			//boolean visible_role = checkVisibleRole();

			for (HrOrganizationElement post : postList) {
				List<?> ss = post.getFdPersons();
				//ss = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult((List<SysOrgElement>) ss);
				for (int i = 0; i < ss.size(); i++) {
					HrOrganizationElement soeOri = ((HrOrganizationElement) ss.get(i));

					HrOrganizationElement soe = new HrOrganizationElement();

					cloneOrg(soe, soeOri);

					if (soe.getFdIsAvailable() && soe.getFdIsBusiness() && elemList != null && !elemList.contains(soe)) {
						soe.setFdOrder(post.getFdOrder());
						elemList.add(soe);
						if (elemList.size() > SysOrgUtil.LIMIT_RESULT_SIZE) {
							return HrOrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath());
						}
					}
				}
			}

			Collections.sort(elemList, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					HrOrganizationElement org1 = (HrOrganizationElement) o1;
					HrOrganizationElement org2 = (HrOrganizationElement) o2;
					Integer i1 = org1.getFdOrder() == null ? Integer.MAX_VALUE : org1.getFdOrder();
					Integer i2 = org2.getFdOrder() == null ? Integer.MAX_VALUE : org2.getFdOrder();
					if (org1.getFdOrgType().equals(org2.getFdOrgType())) {
						if (i1.equals(i2)) {
							return 0;
						} else if (i1 > i2) {
							return 1;
						} else {
							return -1;
						}
					} else if (org1.getFdOrgType() > org2.getFdOrgType()) {
						return -1;
					} else {
						return 1;
					}
				}
			});
		}
		return HrOrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath());
	}

}
