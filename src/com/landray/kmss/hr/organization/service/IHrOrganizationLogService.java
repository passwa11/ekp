package com.landray.kmss.hr.organization.service;

import java.util.List;

import com.landray.kmss.hr.organization.model.HrOrganizationLog;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IHrOrganizationLogService extends IExtendDataService {

	public List<HrOrganizationLog> findLogByOrgId(String fdId) throws Exception;
}
