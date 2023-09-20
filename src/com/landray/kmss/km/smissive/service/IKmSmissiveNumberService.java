package com.landray.kmss.km.smissive.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;


public interface IKmSmissiveNumberService extends IBaseService {

	public String getTempNumFromDb(String fdNumberId) throws Exception;

	public void deleteTempNumFromDb(String fdNumberId, String docBufferNum) throws Exception;

	public void deleteExpiredNum(SysQuartzJobContext context) throws Exception;
}
