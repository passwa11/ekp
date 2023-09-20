package com.landray.kmss.km.archives.service;

import java.util.List;

import com.landray.kmss.km.archives.model.KmArchivesTemplate;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IKmArchivesTemplateService extends IExtendDataService {

	public List<KmArchivesTemplate> getTemplateByMainDense(String fdMainId) throws Exception;

	public List<KmArchivesTemplate> getTemplateByDenses(List denseIds, Boolean getAll) throws Exception;
	
	/**
	 * 获取借阅流程的默认模板
	 * 
	 * @return
	 * @throws Exception
	 */
	public KmArchivesTemplate getDefaultTemplate() throws Exception;
}
