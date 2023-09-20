package com.landray.kmss.sys.attachment.service;

import com.landray.kmss.common.service.IBaseCoreOuterService;

public interface ISysAttRtfCoreService extends IBaseCoreOuterService {
	public String saveSysAttRtfDataByfilePath(String fdApplicantId,String fdModelName,String filePath) throws Exception; 
}
