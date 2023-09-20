package com.landray.kmss.km.archives.service;

import org.slf4j.Logger;

import com.landray.kmss.km.archives.model.KmArchivesSign;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IKmArchivesSignService extends IExtendDataService {
	
	public KmArchivesSign getKmArchivesSignByModelId(String fdTempModelId);

	/**
	 * 获取签名保存签名到数据库
	 * @param expires
	 * @param mainModelId
	 * @param mainModelDesc
	 * @return
	 */
	public String addSignForArchives(String mainModelId, String mainModelDesc);
	
	/**
	 * 校验签名
	 * @param expires
	 * @param fdId
	 * @param sign
	 * @param logger
	 * @return
	 * @throws Exception
	 */
	public boolean validateArchivesSignature(String expires, String fdId, String sign, Logger logger) throws Exception;
	
	/**
	 * 删除签名
	 * 
	 * @param fdModelId
	 * @throws Exception
	 */
	public void deleteSign(String fdModelId) throws Exception;
}
