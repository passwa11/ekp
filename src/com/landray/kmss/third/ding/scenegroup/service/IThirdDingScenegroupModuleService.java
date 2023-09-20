package com.landray.kmss.third.ding.scenegroup.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupModule;

public interface IThirdDingScenegroupModuleService extends IExtendDataService {

	public ThirdDingScenegroupModule findByKey(String key) throws Exception;
}
