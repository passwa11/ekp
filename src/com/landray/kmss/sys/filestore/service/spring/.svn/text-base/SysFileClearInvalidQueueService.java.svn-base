package com.landray.kmss.sys.filestore.service.spring;

import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;

public class SysFileClearInvalidQueueService {

	private ISysFileConvertDataService convertDataService = null;

	public void setConvertDataService(ISysFileConvertDataService convertDataService) {
		this.convertDataService = convertDataService;
	}

	public void clearInvalid() throws Exception {
		convertDataService.clearInvalidQueue();
	}
}
