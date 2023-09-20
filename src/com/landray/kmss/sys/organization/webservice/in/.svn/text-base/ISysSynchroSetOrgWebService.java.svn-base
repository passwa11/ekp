package com.landray.kmss.sys.organization.webservice.in;

import javax.jws.WebService;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

@WebService
public interface ISysSynchroSetOrgWebService extends ISysWebservice {
	/**
	 * 同步所有组织组织基本信息
	 * 
	 * @param setOrgContext
	 * @return
	 * @throws Exception
	 */
	public SysSynchroSetResult syncOrgElementsBaseInfo(
			SysSynchroSetOrgContext setOrgContext) throws Exception;

	/**
	 * 同步更新的组织的机构数据(可多次执行)
	 */
	public SysSynchroSetResult syncOrgElements(
			SysSynchroSetOrgContext setOrgContext) throws Exception;

	public SysSynchroSetResult updateOrgStaffingLevels(
			SysSynchroSetOrgContext setOrgContext) throws Exception;

	public SysSynchroSetResult delOrgStaffingLevels(
			SysSynchroSetOrgContext setOrgContext) throws Exception;

	public SysSynchroSetResult updateOrgElement(
			SysSynchroSetOrgContext setOrgContext) throws Exception;

}
