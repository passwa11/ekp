package com.landray.kmss.third.welink.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IThirdWelinkDeptNoMappingService extends IExtendDataService {

	public void addNoMapping(String welinkId, String welinkName,
			String welinkPath) throws Exception;
}
