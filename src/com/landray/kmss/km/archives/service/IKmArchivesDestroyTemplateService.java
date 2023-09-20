package com.landray.kmss.km.archives.service;

import com.landray.kmss.km.archives.model.KmArchivesDestroyTemplate;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IKmArchivesDestroyTemplateService extends IExtendDataService {
	
	/**
	 * 获取销毁默认模板
	 * 
	 * @return
	 * @throws Exception
	 */
	public KmArchivesDestroyTemplate getDefaultDestroyTemplate() throws Exception;

}
