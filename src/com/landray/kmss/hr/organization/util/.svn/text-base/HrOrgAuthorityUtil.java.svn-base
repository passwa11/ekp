package com.landray.kmss.hr.organization.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.service.IHrOrgFileAuthorService;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * <P>人事组织数据权限过滤</P>
 * @version 1.0 2020年3月19日
 */
public class HrOrgAuthorityUtil {

	private static IHrOrgFileAuthorService hrOrgFileAuthorService;

	protected static IHrOrgFileAuthorService getHrOrgFileAuthorServiceImp() {
		if (hrOrgFileAuthorService == null) {
			hrOrgFileAuthorService = (IHrOrgFileAuthorService) SpringBeanUtil.getBean("hrOrgFileAuthorService");
		}
		return hrOrgFileAuthorService;
	}

	private static IHrOrganizationElementService hrOrganizationElementService;

	public static IHrOrganizationElementService
			getHrOrganizationElementService() {
		if (hrOrganizationElementService == null) {
			hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil
					.getBean("hrOrganizationElementService");
		}
		return hrOrganizationElementService;
	}

	//兼岗
	private static final String HR_STAFF_TRACK_RECORD = "hrStaffTrackRecord";
	//岗位
	private static final String HR_ORGANIZATION_POST = "hrOrganizationPost";
	//组织
	private static final String HR_ORGANIZATION_ELEMENT = "hrOrganizationElement";

	public static StringBuffer builtWhereBlock(StringBuffer whereBlock, String tableName, HQLInfo hqlInfo)
			throws Exception {
		// 获取有权限的部门id
		List<String> oldorgIds = getOrgIds();
		// 获取所有子部门数据
		List<String> orgIds = getDeptIds(oldorgIds);
		Boolean isAdmin = UserUtil.getKMSSUser().isAdmin();
		if (isAdmin || UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_ADMIN")) {//系统管理员或者拥有阅读人事组织机构管理员直接放行
			return whereBlock;
		}
		if (HR_STAFF_TRACK_RECORD.equals(tableName)
				&& UserUtil.checkRole("ROLE_HRORGANIZATION_TRACK_RECORD")) {
			return whereBlock;
		}
		if (HR_STAFF_TRACK_RECORD.equals(tableName) && UserUtil
				.checkRole("ROLE_HRORGANIZATION_TRACK_RECORD_SCOPE")) {
			return whereBlock;
		}
		if (HR_STAFF_TRACK_RECORD.equals(tableName) && !UserUtil
				.checkRole("ROLE_HRORGANIZATION_TRACK_RECORD_SCOPE")) {
				whereBlock.append(" and 1 = 2");
			return whereBlock;
		}
		if (orgIds.isEmpty()) {
			whereBlock.append(" and 1 = 2");// 当前人员能查询的机构数据为空则直接过滤
			return whereBlock;
		}
		whereBlock.append(" and (");
		for (int i = 0; i < orgIds.size(); i++) {
			String orgId = orgIds.get(i);
			if ((orgIds.size() - 1) == i) {
				if (HR_STAFF_TRACK_RECORD.equals(tableName)) {
					whereBlock.append(tableName + ".fdPersonInfo.fdHierarchyId like :orgid_" + i);
				} else {
					whereBlock.append("(" + tableName + ".fdHierarchyId like :orgid_" + i + ")");
				}
			} else {
				if (HR_STAFF_TRACK_RECORD.equals(tableName)) {
					whereBlock.append(tableName + ".fdPersonInfo.fdHierarchyId like :orgid_" + i + " or ");
				} else {
					whereBlock.append(
							"(" + tableName + ".fdHierarchyId like :orgid_" + i + ") or ");
				}
			}
			hqlInfo.setParameter("orgid_" + i, "%" + orgId + "%");
		}
		whereBlock.append(" )");
		return whereBlock;
	}

	public static List<String> getOrgIds() throws Exception {
		String[] postId = UserUtil.getKMSSUser().getPostIds();
		String sql = "select a.fd_id,a.fd_name,d.fd_org_id from hr_org_file_author_detail d left join hr_org_file_author a on a.fd_id = d.fd_author_id where fd_org_id =:personId";
		if (postId != null && postId.length > 0) {
			List list = Arrays.asList(postId);
			sql = sql + " or " + HQLUtil.buildLogicIN("fd_org_id", list);
		}
		List<Object[]> authorDetails = getHrOrgFileAuthorServiceImp().getBaseDao().getHibernateSession().createNativeQuery(sql).setString("personId", UserUtil.getKMSSUser().getPerson().getFdId()).list();
		List<String> orgIds = new ArrayList<String>();

		//处理DB2会多出一列的情况
		if (authorDetails.size() > 0) {
			int j = 1;
			if (authorDetails.get(0)[0] instanceof BigInteger) {
				j++;
			}

			for (Object[] obj : authorDetails) {
				if (StringUtil.isNotNull((String) obj[j])) {
					orgIds.add((String) obj[j]);
				}
			}
		}

		return orgIds;
	}

	/**
	 * 获取部门的所有子部门
	 * 
	 * @param oldorgIds
	 * @return
	 * @throws Exception
	 */
	private static List<String> getDeptIds(List<String> oldorgIds)
			throws Exception {
		List<String> newDeptIds = new ArrayList<String>();
		newDeptIds.addAll(oldorgIds);
		for (String oldorgId : oldorgIds) {
			HrOrganizationElement element = (HrOrganizationElement) getHrOrganizationElementService()
					.findByPrimaryKey(oldorgId);
			if (!ArrayUtil.isEmpty(element.getFdChildren())) {
				List<HrOrganizationElement> childs = element
						.getFdChildren();
				List<String> childIds = new ArrayList<String>();
				for (HrOrganizationElement child : childs) {
					// 子级如果是部门或者机构
					if (child.getFdOrgType() == 1
							|| child.getFdOrgType() == 2) {
						childIds.add(child.getFdId());
					} else {
						continue;
					}
				}
				if (!ArrayUtil.isEmpty(childIds)) {
					newDeptIds.addAll(getDeptIds(childIds));
				}
			}
		}
		return newDeptIds;
	}
}
