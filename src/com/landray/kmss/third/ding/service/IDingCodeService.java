package com.landray.kmss.third.ding.service;

import com.landray.kmss.common.service.IBaseService;

public interface IDingCodeService extends IBaseService {
	
	public String getUseridByCode(String fdCode)throws Exception;
	
	public void deleteByCode(String fdCode)throws Exception;

}
