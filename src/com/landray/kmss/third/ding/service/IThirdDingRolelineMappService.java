package com.landray.kmss.third.ding.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.ding.model.ThirdDingRolelineMapp;

public interface IThirdDingRolelineMappService extends IExtendDataService {

	public ThirdDingRolelineMapp findByDingRole(String dingRoleId)
			throws Exception;

}
