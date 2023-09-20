package com.landray.kmss.hr.organization.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.organization.constant.HrOrganizationRecentContactConstant;
import com.landray.kmss.hr.organization.dao.IHrOrganizationRecentContactDao;
import com.landray.kmss.util.StringUtil;

/**
 * 最近联系人数据访问接口实现
 * 
 * @author
 * @version 1.0 2015-08-03
 */
public class HrOrganizationRecentContactDaoImp extends BaseDaoImp implements
		IHrOrganizationRecentContactDao {

	private final static int[] orgTypes = { 1, 2, 4, 8 };

	@Override
	public void clearOldContacts(String personId) throws Exception {
		// TODO 自动生成的方法存根
		// String hql =
		// "SELECT recentContact1.fdContact.fdId FROM sysOrganizationRecentContact AS recentContact1 INNER JOIN (SELECT A.fdContact.fdOrgType,A.docCreateTime FROM sysOrganizationRecentContact AS A LEFT JOIN sysOrganizationRecentContact AS B ON A.fdContact.fdOrgType = B.fdContact.fdOrgType AND A.docCreateTime <= B.docCreateTime GROUP BY A.fdContact.fdOrgType HAVING COUNT(B.docCreateTime) <= 10) AS B1  ON A1.fdContact.fdOrgType = B1.fdContact.fdOrgType AND A1.docCreateTime = B1.docCreateTime ORDER BY A1.fdContact.fdOrgType,A1.docCreateTime DESC";
		List<String> contactIds = new ArrayList<String>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("hrOrganizationRecentContact.fdId");
		hqlInfo
				.setWhereBlock(
						"hrOrganizationRecentContact.fdUser.fdId = :personId and hrOrganizationRecentContact.fdContact.fdOrgType = :fdOrgType");
		hqlInfo.setOrderBy("hrOrganizationRecentContact.docCreateTime desc");
		hqlInfo
				.setRowSize(HrOrganizationRecentContactConstant.ORG_RECENT_CONTACT_ROW_SIZE);
		hqlInfo.setPageNo(1);
		hqlInfo.setParameter("personId", personId);
		for (int orgType : orgTypes) {
			hqlInfo.setParameter("fdOrgType", orgType);
			List<String> ids = findPage(hqlInfo).getList();
			contactIds.addAll(ids);
		}

		String hql_delete = "delete from com.landray.kmss.hr.organization.model.HrOrganizationRecentContact hrOrganizationRecentContact where hrOrganizationRecentContact.fdUser.fdId = :userId";
		if (!contactIds.isEmpty()) {
			hql_delete += " and hrOrganizationRecentContact.fdId not in "
					+ buildInStr(contactIds);
		}
		Query query = super.getSession().createQuery(hql_delete);
		query.setParameter("userId", personId);
		query.executeUpdate();
	}

	@Override
    public void delContacts(String personId, String[] contactIds)
			throws Exception {

		if (StringUtil.isNull(personId)) {
			return;
		}
		if (contactIds == null || contactIds.length == 0) {
			String hql_delete = "delete from com.landray.kmss.hr.organization.model.HrOrganizationRecentContact hrOrganizationRecentContact where hrOrganizationRecentContact.fdUser.fdId = :userId";

			Query query = super.getSession().createQuery(hql_delete);
			query.setParameter("userId", personId);
			query.executeUpdate();
			return;
		}
		List<String> contactIdList = Arrays.asList(contactIds);

		String hql_delete = "delete from com.landray.kmss.hr.organization.model.HrOrganizationRecentContact hrOrganizationRecentContact where hrOrganizationRecentContact.fdUser.fdId = :userId";
		hql_delete += " and hrOrganizationRecentContact.fdContact.fdId in "
				+ buildInStr(contactIdList);
		Query query = super.getSession().createQuery(hql_delete);
		query.setParameter("userId", personId);
		query.executeUpdate();

	}
	

	private String buildInStr(List<String> ids) {
		String ids_str = "";
		for (String id : ids) {
			ids_str += "'" + id + "',";
		}
		ids_str = ids_str.substring(0, ids_str.length() - 1);
		ids_str = "(" + ids_str + ")";
		return ids_str;
	}

}
