package com.landray.kmss.third.ding.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.ding.model.ThirdDingRoleCateMapp;

public interface IThirdDingRoleCateMappService extends IExtendDataService {

	public void deleteByCateId(String cateId) throws Exception;

	public ThirdDingRoleCateMapp findByGroupId(String groupId) throws Exception;
}
