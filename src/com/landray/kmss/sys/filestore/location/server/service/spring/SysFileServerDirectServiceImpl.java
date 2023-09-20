package com.landray.kmss.sys.filestore.location.server.service.spring;

import com.landray.kmss.sys.filestore.location.model.SysFileSignature;
import com.landray.kmss.sys.filestore.location.model.SysFileSignatureRequest;
import com.landray.kmss.sys.filestore.location.service.AbstractSysFileLocationDirectService;

public class SysFileServerDirectServiceImpl
		extends AbstractSysFileLocationDirectService {

	/**
	 * isSupportDirect返回false时，此方法不会执行
	 */
	@Override
	public String getUploadUrl() throws Exception {
		return null;
	}
	
	@Override
	public String getDownloadUrl(String fdPath, String fileName) throws Exception {
		return null;
	}

	/**
	 * isSupportDirect返回false时，此方法不会执行
	 */
	@Override
	public SysFileSignature createSignature(
			SysFileSignatureRequest signatureRequest) throws Exception {
		return null;
	}
	
	@Override
	public Boolean isSupportDirect() {
		return false;
	}

}
