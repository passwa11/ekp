package com.landray.kmss.third.ding.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.ding.model.ThirdDingEvent;

import net.sf.json.JSONObject;

public interface IThirdDingEventService extends IExtendDataService {

	public JSONObject updateCallBack(ThirdDingEvent event) throws Exception;

	public void deleteAll();
}
