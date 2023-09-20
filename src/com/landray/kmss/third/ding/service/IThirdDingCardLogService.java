package com.landray.kmss.third.ding.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IThirdDingCardLogService extends IExtendDataService {

    public void clear(int days) throws Exception;
}
