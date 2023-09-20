package com.landray.kmss.third.weixin.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayOrder;

public interface IThirdWeixinPayOrderService extends IExtendDataService {

    public ThirdWeixinPayOrder findOrder(String modelName, String modelId, String fdKey) throws Exception;
}
