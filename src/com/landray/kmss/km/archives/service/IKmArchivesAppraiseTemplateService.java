package com.landray.kmss.km.archives.service;

import com.landray.kmss.km.archives.model.KmArchivesAppraiseTemplate;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IKmArchivesAppraiseTemplateService extends IExtendDataService {
	
	/**
	 * 查默认的鉴定模板
	 * 
	 * @return 默认模板
	 */
	public KmArchivesAppraiseTemplate getDefaultAppraiseTemplate() throws Exception;

}
