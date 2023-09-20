package com.landray.kmss.km.calendar.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.query.NativeQuery;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.calendar.model.KmCalendarPersonGroup;
import com.landray.kmss.sys.organization.model.SysOrgElement;

public interface IKmCalendarPersonGroupService extends IBaseService {

	/**
	 * 获取层级人员（已性能优化）
	 * @param hierarchyIds
	 * @param searchParent
	 * @throws Exception
	 */
	public List<SysOrgElement> getSysOrgElements(Set<String> hierarchyIds, boolean searchParent) throws Exception;

	/**
	 * 查找指定人员的成员群组
	 * 
	 * @param personId
	 * @return
	 */
	public List<KmCalendarPersonGroup> getUserPersonGroup(String personId)
			throws Exception;

	/**
	 * 查找指定成员分组的人员
	 * 
	 * @param requestContext
	 * @param loadAll
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<SysOrgElement>> getFdPersonGroup(
			RequestContext requestContext,
			Boolean loadAll) throws Exception;

	public Map<String, List<SysOrgElement>>
			getFdPersonGroup(RequestContext requestContext)
			throws Exception;
	
	public void deleteMainGroup(String fdId) throws Exception;
	
	
	public  void deleteMainGroup(String[] ids) throws Exception;
}
