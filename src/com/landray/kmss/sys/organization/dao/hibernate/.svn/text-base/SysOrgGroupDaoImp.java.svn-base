package com.landray.kmss.sys.organization.dao.hibernate;

import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.organization.dao.ISysOrgGroupDao;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.util.HQLUtil;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysOrgGroupDaoImp extends SysOrgElementDaoImp implements
		ISysOrgGroupDao {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgGroupDaoImp.class);

	@Override
    protected void clearRelation(SysOrgElement element) {
		super.clearRelation(element);
		SysOrgGroup group = (SysOrgGroup) element;
		group.setFdGroupCate(null);
		group.setFdMembers(null);
	}

	/**
	 * 获取群组->成员的嵌套关系
	 * 
	 * @return ID映射表：群组ID->群组成员IDList
	 * @throws Exception
	 */
	private Map getChildGroupMap() {
		KmssCache cache = new KmssCache(SysOrgElement.class);
		Map childGroupMap = (Map) cache.get("childGroupMap");
		if (childGroupMap == null) {
			logger.debug("重新加载群组关系");
			Map[] result = HQLUtil
					.buildManyToManyIDBidirectionalMap(
							getHibernateSession(),
							"select fd_groupid,fd_elementid from sys_org_group_element left join sys_org_element on fd_elementid=fd_id where fd_org_type="
									+ ORG_TYPE_GROUP);
			cache.put("childGroupMap", result[0]);
			cache.put("parentGroupMap", result[1]);
			childGroupMap = result[0];
		}
		return childGroupMap;
	}

	/**
	 * 获取群组->父群组嵌套关系
	 * 
	 * @return ID映射表：群组ID->父群组IDList
	 * @throws Exception
	 */
	private Map getParentGroupMap() {
		KmssCache cache = new KmssCache(SysOrgElement.class);
		Map parentGroupMap = (Map) cache.get("parentGroupMap");
		if (parentGroupMap == null) {
			logger.debug("重新加载群组关系");
			Map[] result = HQLUtil
					.buildManyToManyIDBidirectionalMap(
							getHibernateSession(),
							"select fd_groupid,fd_elementid from sys_org_group_element left join sys_org_element on fd_elementid=fd_id where fd_org_type="
									+ ORG_TYPE_GROUP);
			cache.put("childGroupMap", result[0]);
			cache.put("parentGroupMap", result[1]);
			parentGroupMap = result[1];
		}
		return parentGroupMap;
	}

	@Override
    public List fetchChildGroupIds(List groupIds) {
		return HQLUtil.fetchManyToManyIDList(groupIds, getChildGroupMap());
	}

	@Override
    public List fetchParentGroupIds(List groupIds) {
		return HQLUtil.fetchManyToManyIDList(groupIds, getParentGroupMap());
	}

	@Override
	public List fetchChildGroupIds(Set groupIds) throws Exception {
		return HQLUtil.fetchManyToManyIDList(new ArrayList(groupIds), getChildGroupMap());
	}
}
