package com.landray.kmss.hr.organization.dao.hibernate;

import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.dao.IHrOrganizationElementDao;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.util.StringUtil;

public class HrOrganizationElementDaoImp extends BaseDaoImp
		implements IHrOrganizationElementDao, HrOrgConstant, BaseTreeConstant {

	/**
	 * 获取层级ID
	 * 
	 * @param element
	 * @return
	 */
	private String getTreeHierarchyId(HrOrganizationElement element) {
		if (element.getFdOrgType().intValue() == HR_TYPE_ORG || element.getFdParent() == null) {
			return HIERARCHY_ID_SPLIT + element.getFdId() + HIERARCHY_ID_SPLIT;
		} else {
			return element.getFdParent().getFdHierarchyId() + element.getFdId() + HIERARCHY_ID_SPLIT;
		}
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		HrOrganizationElement element = (HrOrganizationElement) modelObj;
		if (element.getFdAlterTime() == null) {
			element.setFdAlterTime(new Date());
		}
		element.setFdHierarchyId(getTreeHierarchyId(element));
		return super.add(element);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrOrganizationElement element = (HrOrganizationElement) modelObj;
		if (element.getFdAlterTime() == null) {
			element.setFdAlterTime(new Date());
		}
		super.update(element);
		// 更新层级ID
		String hierarchyId = element.getFdHierarchyId();
		element.setFdHierarchyId(getTreeHierarchyId(element));
		super.update(modelObj);

		int orgType = element.getFdOrgType().intValue();
		if (orgType > HR_TYPE_DEPT) {
			return;
		}

		// 当组织架构为部门或机构时，判断层级ID是否有改动，若已经改动，则更新所有下级关系
		// 如将无效的部门变更为有效部门时，不存在下级关系，所以不更新层级架构
		if (!hierarchyId.equals(element.getFdHierarchyId()) && !"0".equals(hierarchyId)
				&& StringUtil.isNotNull(hierarchyId)) {
			HrOrganizationElement parentOrg = orgType == HR_TYPE_ORG ? element : element.getFdParentOrg();
			String hql = "update com.landray.kmss.hr.organization.model.HrOrganizationElement set fdAlterTime=:thisDay,";
			if (parentOrg == null) {
				hql += "hbmParentOrg=null,";
			} else {
				hql += "hbmParentOrg=:parentOrg,";
			}
			hql += "fdHierarchyId='" + element.getFdHierarchyId() + "' || substring(fdHierarchyId, "
					+ (hierarchyId.length() + 1) + ", length(fdHierarchyId)) ";
			hql += "where substring(fdHierarchyId,1," + hierarchyId.length() + ")='" + hierarchyId + "'";
			if (logger.isDebugEnabled()) {
				logger.debug("更新组织架构的所有下级关系，hql=" + hql);
			}
			Query q = getHibernateSession().createQuery(hql);
			if (parentOrg != null) {
				q.setParameter("parentOrg", orgType == HR_TYPE_ORG ? element : element.getFdParentOrg());
			}
			q.setParameter("thisDay", new Date());
			q.executeUpdate();
		}
	}

}
