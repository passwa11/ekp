package com.landray.kmss.sys.zone.service.spring;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.zone.model.SysZoneOrgOuter;
import com.landray.kmss.sys.zone.service.ISysZoneOrgOuterService;

public class SysZoneOrgOuterServiceImp extends BaseServiceImp implements ISysZoneOrgOuterService {

	@Override
	public void deleteOuter(String orgId) throws Exception {
		SysZoneOrgOuter sysZoneOrgOuter = (SysZoneOrgOuter) findByPrimaryKey(
				orgId);
		delete(sysZoneOrgOuter);

	}

}
