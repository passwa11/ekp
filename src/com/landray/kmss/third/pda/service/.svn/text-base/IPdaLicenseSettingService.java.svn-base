package com.landray.kmss.third.pda.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;

public interface IPdaLicenseSettingService extends IBaseService {

	public List getAccessorList(String sort) throws Exception;

	public void addAccessorList(Object[] orgIds, boolean isReplace)
			throws Exception;

	public Object[] praseOrgInfo(String[] orgIds) throws Exception;
	
	public void deleteOrgs(String[] orgIds) throws Exception;

	public boolean existed(String orgId) throws Exception;

	public int deleteExecuteQuery(String hql) throws Exception;

}
