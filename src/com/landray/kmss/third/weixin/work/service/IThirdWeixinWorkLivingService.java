package com.landray.kmss.third.weixin.work.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IThirdWeixinWorkLivingService extends IExtendDataService {

    public void deleteByLivingId(String livingId) throws Exception;
}
