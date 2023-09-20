package com.landray.kmss.sys.praise.service;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.service.IBaseService;

public interface ISysPraiseInfoPersonMonthService extends IBaseService{

	void executeDetail(Date lastTime, Date nowTime, List<String> orgIds) throws Exception;

}
