package com.landray.kmss.sys.filestore.service;

import java.util.Map;

import com.landray.kmss.common.service.IBaseService;

public interface ISysFileConvertCallbackService extends IBaseService {

	/**
	 * convertFinish成功结果回调
	 * @param receiveInfos
	 * @throws Exception 
	 */
	public void setSuccessCallback(Map<String, String> receiveInfos) throws Exception;
	
	/**
	 * 保存转化后的附件到沉淀的文档
	 * @param receiveInfos
	 * @throws Exception
	 */
	public void saveMultidocFile(Map<String, String> receiveInfos) throws Exception;
}
