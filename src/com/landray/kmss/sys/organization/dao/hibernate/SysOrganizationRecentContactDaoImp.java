package com.landray.kmss.sys.organization.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.dao.ISysOrganizationRecentContactDao;
import com.landray.kmss.sys.organization.util.SysOrganizationRecentContactConstant;
import com.landray.kmss.util.StringUtil;

/**
 * 最近联系人数据访问接口实现
 * 
 * @author
 * @version 1.0 2015-08-03
 */
public class SysOrganizationRecentContactDaoImp extends BaseDaoImp implements
		ISysOrganizationRecentContactDao {

	private final static int[] orgTypes = { 1, 2, 4, 8 };

	@Override
	public void clearOldContacts(String personId) throws Exception {
		// TODO 自动生成的方法存根
		// String hql =
		// "SELECT recentContact1.fdContact.fdId FROM sysOrganizationRecentContact AS recentContact1 INNER JOIN (SELECT A.fdContact.fdOrgType,A.docCreateTime FROM sysOrganizationRecentContact AS A LEFT JOIN sysOrganizationRecentContact AS B ON A.fdContact.fdOrgType = B.fdContact.fdOrgType AND A.docCreateTime <= B.docCreateTime GROUP BY A.fdContact.fdOrgType HAVING COUNT(B.docCreateTime) <= 10) AS B1  ON A1.fdContact.fdOrgType = B1.fdContact.fdOrgType AND A1.docCreateTime = B1.docCreateTime ORDER BY A1.fdContact.fdOrgType,A1.docCreateTime DESC";
		List<String> contactIds = new ArrayList<String>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("sysOrganizationRecentContact.fdId");
		hqlInfo
				.setWhereBlock("sysOrganizationRecentContact.fdUser.fdId = :personId and sysOrganizationRecentContact.fdContact.fdOrgType = :fdOrgType");
		hqlInfo.setOrderBy("sysOrganizationRecentContact.docCreateTime desc");
		hqlInfo
				.setRowSize(SysOrganizationRecentContactConstant.ORG_RECENT_CONTACT_ROW_SIZE);
		hqlInfo.setPageNo(1);
		hqlInfo.setParameter("personId", personId);
		for (int orgType : orgTypes) {
			hqlInfo.setParameter("fdOrgType", orgType);
			List<String> ids = findPage(hqlInfo).getList();
			contactIds.addAll(ids);
		}

		String hql_delete = "delete from com.landray.kmss.sys.organization.model.SysOrganizationRecentContact sysOrganizationRecentContact where sysOrganizationRecentContact.fdUser.fdId = :userId";
		if (!contactIds.isEmpty()) {
			hql_delete += " and sysOrganizationRecentContact.fdId not in "
					+ buildInStr(contactIds);
		}
		// hql_delete += buildInStr(contactIds);
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
			String hql_delete = "delete from com.landray.kmss.sys.organization.model.SysOrganizationRecentContact sysOrganizationRecentContact where sysOrganizationRecentContact.fdUser.fdId = :userId";

			Query query = super.getSession().createQuery(hql_delete);
			query.setParameter("userId", personId);
			query.executeUpdate();
			return;
		}
		List<String> contactIdList = Arrays.asList(contactIds);

		String hql_delete = "delete from com.landray.kmss.sys.organization.model.SysOrganizationRecentContact sysOrganizationRecentContact where sysOrganizationRecentContact.fdUser.fdId = :userId";
		hql_delete += " and sysOrganizationRecentContact.fdContact.fdId in "
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
