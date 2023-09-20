package com.landray.kmss.sys.filestore.location.interfaces;

import com.landray.kmss.sys.filestore.location.model.SysFileSignature;
import com.landray.kmss.sys.filestore.location.model.SysFileSignatureRequest;

/**
 * 直连相关配置
 */
public interface ISysFileLocationDirectService {

	/**
	 * 下载链接
	 * 
	 * @param fdPath
	 * @return
	 * @throws Exception
	 */
	public String getDownloadUrl(String fdPath, String fileName)
			throws Exception;

	/**
	 * 删除链接
	 * 
	 * @param fdPath
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getDeleteUrl(String fdPath) throws Exception;

	/**
	 * 上传链接
	 * @param userAgent 浏览器标识
	 * @return
	 * @throws Exception
	 */
	public String getUploadUrl(String userAgent) throws Exception;

	/**
	 * 上传method类型，默认post
	 * 
	 * @return
	 */
	public String getMethodName();

	/**
	 * 上传signature<br>
	 * 
	 * 
	 * @param signatureRequest
	 * @return
	 * @throws Exception
	 */
	public SysFileSignature getSignature(
			SysFileSignatureRequest signatureRequest) throws Exception;

	/**
	 * 文件上传file域的name 例如阿里云使用的是“file”<br>
	 * 此值为空代表传输实体部分是一个无结构二级制数据，适合PUT方法
	 * 
	 * @return
	 */
	public String getFileVal();
	
	/**
	 * 是否支持直连<br>
	 * @param userAgent 浏览器标识
	 * @return
	 */
	public Boolean isSupportDirect(String userAgent);

	/**
	 * 是否支持服务直连<br/>
	 * @return
	 */
	public Boolean isSupportServiceDirect();
}
