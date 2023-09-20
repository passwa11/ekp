package com.landray.kmss.third.welink.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.welink.model.ThirdWelinkDeptMapping;

import net.sf.json.JSONObject;

public interface IThirdWelinkDeptMappingService extends IExtendDataService {

	public ThirdWelinkDeptMapping findByEkpId(String ekpId) throws Exception;

	public ThirdWelinkDeptMapping findByWelinkId(String welinkId)
			throws Exception;

	public void addMapping(SysOrgElement dept) throws Exception;

	public void addMapping(SysOrgElement dept, String welinkId) throws Exception;

	/**
	 * @throws Exception
	 *             部门映射初始化
	 */
	public void updateDept(JSONObject json) throws Exception;
}
