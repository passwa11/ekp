package com.landray.kmss.third.ding.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.ding.model.ThirdDingCardMapping;
import net.sf.json.JSONObject;

public interface IThirdDingCardMappingService extends IExtendDataService {

   public ThirdDingCardMapping getMappingByModel(String modelName, String mainId);

   public void updateDealWithCardCallback(String outTrackId, String fdId, JSONObject action);
}
