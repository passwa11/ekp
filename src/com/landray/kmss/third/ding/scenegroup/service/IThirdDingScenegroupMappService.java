package com.landray.kmss.third.ding.scenegroup.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupMapp;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupModule;

import net.sf.json.JSONObject;

public interface IThirdDingScenegroupMappService extends IExtendDataService {

    public abstract List<ThirdDingScenegroupMapp> findByFdModuleId(ThirdDingScenegroupModule fdModuleId) throws Exception;

	public ThirdDingScenegroupMapp findByModel(String modelName,
			String modelId, String fdKey) throws Exception;

	public void updateByCallback(JSONObject plainTextJson) throws Exception;

	public ThirdDingScenegroupMapp findByChatId(String chatId) throws Exception;

}
