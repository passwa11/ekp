package com.landray.kmss.sys.restservice.server.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.IKmssSystemInitBean;
import com.landray.kmss.util.KmssMessages;

public interface ISysRestserviceServerInitService extends IBaseService, IKmssSystemInitBean {

	@Override
    public KmssMessages initializeData();
}
