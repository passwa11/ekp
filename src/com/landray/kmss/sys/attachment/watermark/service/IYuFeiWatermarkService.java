package com.landray.kmss.sys.attachment.watermark.service;

import java.io.InputStream;
import java.util.Map;

/**
 * 宇飞水印对外接口
 *
 */
public interface IYuFeiWatermarkService {
	/**
	 * 关键字
	 * @return
	 * @throws Exception
	 */
	public String getFdKey() throws Exception;
	
	/**
	 * 添加水印
	 * @param inputStream 待加水印的文件流
	 * @param fileName 文件名称
	 * @return
	 * @throws Exception
	 */
	public InputStream addWatermark(InputStream inputStream,String fileName) throws Exception;
	
	/**
	 * 添加水印
	 * @param inputStream 待加水印的文件流
	 * @param fileName 文件名称
	 * @param params 自定义配置参数
	 * @return
	 * @throws Exception
	 */
	public InputStream addWatermark(InputStream inputStream,String fileName,Map<String, Object> params) throws Exception;
}
