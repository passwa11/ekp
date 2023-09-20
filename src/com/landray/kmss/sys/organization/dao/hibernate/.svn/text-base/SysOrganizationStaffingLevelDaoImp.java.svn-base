package com.landray.kmss.sys.organization.dao.hibernate;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import org.slf4j.Logger;

import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.dao.ISysOrganizationStaffingLevelDao;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;

/**
 * 职级配置数据访问接口实现
 * 
 * @author
 * @version 1.0 2015-07-23
 */
public class SysOrganizationStaffingLevelDaoImp extends BaseDaoImp implements
		ISysOrganizationStaffingLevelDao {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrganizationStaffingLevelDaoImp.class);

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysOrganizationStaffingLevel staffingLevel = (SysOrganizationStaffingLevel) modelObj;
		Date current = new Date();
		staffingLevel.setDocCreateTime(current);
		staffingLevel.setDocAlterTime(current);
		if (staffingLevel.getDocCreator() == null) {
            staffingLevel.setDocCreator(UserUtil.getUser());
        }
		String fdId = null;
		TransactionStatus addStatus = TransactionUtils.beginNewTransaction();
		try {
			fdId = super.add(modelObj);
			TransactionUtils.getTransactionManager().commit(addStatus);
		} catch (Exception ex) {
			logger.error("", ex);
			TransactionUtils.getTransactionManager().rollback(addStatus);
			throw ex;
		}
		// super.flushHibernateSession();
		updatePersonStaffingLevel(staffingLevel);
		return fdId;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysOrganizationStaffingLevel staffingLevel = (SysOrganizationStaffingLevel) modelObj;
		staffingLevel.setDocAlterTime(new Date());
		super.update(modelObj);
		updatePersonStaffingLevel(staffingLevel);
	}

	@Deprecated
	private void updatePersonStaffingLevelBak(
			SysOrganizationStaffingLevel staffingLevel) throws Exception {

		String sql = "update SysOrgPerson sysOrgPerson set sysOrgPerson.fdStaffingLevel.fdId = null where sysOrgPerson.fdStaffingLevel.fdId = '"
				+ staffingLevel.getFdId() + "'";
		getHibernateSession().createQuery(sql).executeUpdate();

		List<SysOrgPerson> persons = staffingLevel.getFdPersons();
		if (persons == null || persons.size() == 0) {
			return;
		}
		for (String inBlock : buildInBlocks(persons)) {
			inBlock = inBlock.substring(1);
			StringBuffer sb = new StringBuffer(50000);
			sb
					.append("update SysOrgPerson sysOrgPerson set sysOrgPerson.fdStaffingLevel.fdId = '"
							+ staffingLevel.getFdId() + "'");
			sb.append(" where sysOrgPerson.fdId in (" + inBlock + ")");
			getHibernateSession().createQuery(sb.toString()).executeUpdate();
		}

	}

	public void updatePersonStaffingLevel(
			SysOrganizationStaffingLevel staffingLevel) throws Exception {

		// 清空所有人员的职务引用
		String sql = "update sys_org_person set fd_staffing_level_id = null where fd_staffing_level_id = '" + staffingLevel.getFdId() + "'";
		getHibernateSession().createNativeQuery(sql).executeUpdate();

		// 更新现有人员的职务引用
		List<SysOrgPerson> persons = staffingLevel.getFdPersons();
		for (String inBlock : buildInBlocks(persons)) {
			inBlock = inBlock.substring(1);
			sql = "update sys_org_person set fd_staffing_level_id = '" + staffingLevel.getFdId() + "' where fd_id in (" + inBlock + ")";
			getHibernateSession().createNativeQuery(sql).executeUpdate();
		}
	}

	private List<String> buildInBlocks(List<SysOrgPerson> list) {
		List<String> result = new ArrayList<String>();
		StringBuffer sb = new StringBuffer(50000);
		int i = 1;
		for (SysOrgPerson person : list) {
			i++;
			sb.append(",'").append(person.getFdId()).append("'");
			if (i >= 1000) {
				result.add(sb.toString());
				i = 1;
				sb = new StringBuffer(50000);
			}
		}
		if (sb.length() > 0) {
			result.add(sb.toString());
		}
		return result;
	}

	@Override
	public void clearDefaultFlag(
			SysOrganizationStaffingLevel sysOrganizationStaffingLevel) {
		if (!sysOrganizationStaffingLevel.getFdIsDefault().booleanValue()) {
			return;
		}
		StringBuffer sb = new StringBuffer(50);
		sb
				.append("update SysOrganizationStaffingLevel set fdIsDefault = false");
		sb.append(" where fdId != :fdId");
		sb.append(" and fdIsDefault = true");
		getHibernateSession().createQuery(sb.toString()).setString("fdId",
				sysOrganizationStaffingLevel.getFdId()).executeUpdate();
	}

}
