package com.landray.kmss.sys.organization.service;

import java.util.List;
import java.util.Set;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.organization.forms.SysOrganizationVisibleListForm;
import com.landray.kmss.sys.organization.model.SysOrgConfig;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationVisible;

/**
 * 组织可见性业务对象接口
 * 
 * @author
 * @version 1.0 2015-06-16
 */
public interface ISysOrganizationVisibleService extends IBaseService {

	/**
	 * 获取人员的顶级可视组织ID
	 * 
	 * @param person
	 * @return
	 */
	public Set<String> getPersonVisibleOrgIds(SysOrgPerson person);

	/**
	 * 获取人员的顶级可视组织ID
	 * <p>
	 * 适用于生态组织权限计算
	 * 
	 * @param user
	 * @return
	 */
	public Set<String> getPersonVisibleOrgIds(KMSSUser user);

	/**
	 * 获取人员的顶级可视组织 加入角色过滤
	 * 
	 * @param person
	 * @return
	 */
	public Set<String> getPersonAuthVisibleOrgIds(KMSSUser user);

	/**
	 * 比如：“机构1”下有“机构11”，“机构11”下没有机构，而组织可见性设置的是“机构1”，那么“机构1”和“机构11”的ID都会返回
	 * 
	 * @param person
	 * @return
	 */
	public Set<String> getPersonVisibleAllOrgIds(SysOrgPerson person);

	public List<SysOrganizationVisible> findBySubordinates(String[] subordinates)
			throws Exception;

	public List<SysOrganizationVisible> findByPrincipals(String[] principalIds)
			throws Exception;

	public List<SysOrganizationVisible> getAllOrganizationVisible();

	// public void updateAll(List<SysOrganizationVisibleForm>
	// list,RequestContext requestContext) throws Exception;

	public void updateAll(
			SysOrganizationVisibleListForm sysOrganizationVisibleListForm,
			RequestContext requestContext) throws Exception;

	public boolean isOrgVisibleEnable() throws Exception;

	public boolean isOrgAeraEnable() throws Exception;

	public int getDefaultVisibleLevel() throws Exception;

	public Set<String> getDialogRootVisibleOrgIds();

	/**
	 * 获取用户可见的顶级组织数据（会过滤上下级组织类型的数据，在地址本中保留上下级组织的层级关系，如果出现断链情况则打平）
	 * 
	 * @param person
	 * @return
	 * @throws Exception
	 */
	public Set<String> getPersonRootVisibleOrgIds(SysOrgPerson person)
			throws Exception;

	/**
	 * 获取用户可见的顶级组织数据（会过滤上下级组织类型的数据，在地址本中保留上下级组织的层级关系，如果出现断链情况则打平）
	 * <p>
	 * 适用于生态组织权限计算
	 * 
	 * @param kmssUser
	 * @return
	 * @throws Exception
	 */
	public Set<String> getPersonRootVisibleOrgIds(KMSSUser kmssUser) throws Exception;

	/**
	 * 更新组织过滤的缓存数据
	 * 
	 * @throws Exception
	 */
	public void updateCacheLocal(SysOrgConfig orgConfig) throws Exception;

	public Set<String> getPersonVisibleIds(SysOrgPerson user) throws Exception;

	public Set<String> getPersonVisibleIds(KMSSUser kmssUser) throws Exception;

	void updateCacheAll(SysOrgConfig orgConfig) throws Exception;

}
