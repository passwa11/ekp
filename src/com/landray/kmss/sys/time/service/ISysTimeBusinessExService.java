package com.landray.kmss.sys.time.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeBusinessEx;

import java.util.List;

public interface ISysTimeBusinessExService extends IExtendDataService {
	
	public List<SysTimeBusinessEx> findByFlowId(String processId)
			throws Exception;

	/**
	 * 组织ID，转换成人员ID
	 * 不取部门下岗位
	 * @param orgList
	 * @return
	 * @throws Exception
	 */
	public List<String> expandToPersonIds(List orgList) throws Exception;

	/**
	 * 获取人员对象
	 * @param orgList
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgPerson> expandToPerson(List orgList) throws Exception;
}
