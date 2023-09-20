package com.landray.kmss.km.archives.service;

import com.landray.kmss.km.archives.model.KmArchivesPeriod;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IKmArchivesPeriodService extends IExtendDataService {

	/**
	 * 根据保存年限查询记录
	 * 
	 * @param saveLife
	 * @return
	 * @throws Exception
	 */
	public KmArchivesPeriod findByValue(Integer saveLife) throws Exception;

}
