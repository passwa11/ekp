package com.landray.kmss.third.ding.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.ding.model.ThirdDingCardConfig;
import net.sf.json.JSONObject;

public interface IThirdDingCardConfigService extends IExtendDataService {

    public void addInteractivecard(JSONObject request,String title,String receiver,String modelName,String modelId,String from,String outTrackId) throws Exception;

    public void updateInteractivecard(JSONObject request,String title,String modelName,String modelId) throws Exception;

    ThirdDingCardConfig getCardByModel(String modelName, String mainId);
}
