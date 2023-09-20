package com.landray.kmss.sys.attachment.integrate.wps.interfaces;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import net.sf.json.JSONObject;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 调用WPS中台接口方法
 *
 * @date 2021-05-12
 *
 */
public interface ISysAttachmentWpsCenterOfficeProvider extends IBaseService {

	/**
	 * 查询任务
	 *
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public String queryTask(String taskId) throws Exception;
	
	/**
	 * 下载文件
	 *
	 * @param downloadId
	 * @param downloadPath
	 * @throws Exception
	 */
	public void download(String downloadId, String downloadPath) throws Exception;
	
	/**
	 * 签名设置
	 * @param header
	 * @param shaUrl
	 * @param parameter
	 * @throws Exception
	 */
	public void setSingure(Map<String, String> header, String shaUrl, Map parameter) throws Exception;
	
	/**
	 * 调用Http接口 doPut
	 * 
	 * @return
	 */
	 public String doPut(String url, Map<String,Object> parameter, Map<String, String> header) throws Exception;
	 
	 /**
		 * 调用Http接口 doPost
		 * 
		 * @return
		 */
	 public String doPost(String url, Map<String,Object> parameter, Map<String, String> header) throws Exception;
	 
	 /**
     * 发送HttpGet请求
     * @param url
     * @return
     */
    public byte[] doGet(String url,Map<String,String> parameter ,Map<String,String> header) throws Exception;

	/**
	 * 保存wpstoken
	 * @param wpsToken
	 * @return
	 * @throws Exception
	 */
	public void saveWpsToken(String wpsToken) throws Exception;

	/**
	 * 获取保存好的wpstoken
	 * @return
	 */
	public String getWpsToken() throws Exception;

	/**
	 * 获取长时间的token，用于多次交互，例如编辑
	 * @param userName 登录名
	 * @param wpsToken
	 * @return
	 * @throws Exception
	 */
	public String getLongCallBackToken(String userName, String wpsToken, Boolean isLongTime) throws Exception;


	/**
	 * 获取一次有效的token，用于单词交互，例如下载
	 * @param userName 登录名
	 * @param key 例如文件id
	 * @param wpsToken
	 * @return
	 * @throws Exception
	 */
	public String getOnceCallBackToken(String userName, String key, String wpsToken) throws Exception;

	/**
	 * 验证回调token是否有效
	 * @param callbackToken 回调token
	 * @return
	 * @throws Exception
	 */
	public Boolean validateCallBackToken(String callbackToken) throws Exception;

	/**
	 * 获取在线编辑地址
	 *
	 * @return
	 * @throws Exception
	 */
	public JSONObject getWpsCenterViewAndEditUrl(String fdAttMainId, String fdMode) throws Exception;

	/**
	 * 获取在线编辑地址
	 *
	 * @return
	 * @throws Exception
	 */
	public JSONObject getWpsCenterEditUrl(HttpServletRequest request) throws Exception;

	/**
	 * 持久到数据库
	 * @param fdModelId
	 * @param fdModelName
	 * @param type
	 * @param taskID
	 * @param fdAttMainId
	 * @param downloadUrl
	 * @param status
	 * @throws Exception
	 */
	public void persistThirdWpsConvert(String fdModelId, String fdModelName, String type,
									   String taskID, String fdAttMainId, String fdFileId, String downloadUrl, String status) throws Exception;

	/**
	 * 下载已经转换的文件
	 * @param taskId
	 * @param downloadId
	 * @throws Exception
	 */
	public void dealWpsOperateFile(String taskId, String downloadId) throws Exception ;

	/**
	 * WPS转换文件
	 * @param taskId
	 * @param fileDownloadUrl
	 * @param fdAttMainId
	 * @param fdFileName
	 * @return
	 * @throws Exception
	 */
	public String wpsCenterConvertFile(String taskId, String fileDownloadUrl, String fdAttMainId, String fdFileName, String convertKey) throws Exception;

	/**
	 * 调用wps文档中台的智能公文能力，异步任务，返回wps的任务id，可以用此id在third_wps_convert转换表中查询处理进度，
	 * 处理成功后可以用download_url下载智能公文处理后的文件流
	 * @param fdAttMainId 附件主ID
	 * @param preInstallTemplate 智能公文预设模板，如果值为空，默认模板为正文；更多模板见SmartOfficialConstant.java
	 * @return  任务ID,taskId
	 * @throws Exception
	 */
	public String requestSmartOfficial(String fdAttMainId, String preInstallTemplate) throws Exception;

	/**
	 * 获取WPS中台在线预览地址
	 *
	 * @return
	 * @throws Exception
	 */

	public JSONObject getWpsCenterPreviewUrl(String fdAttMainId, String fdMode) throws Exception;

	/**
	 * 清稿
	 *
	 * @return
	 * @throws Exception
	 */
	String wpsCenterOperateClean(SysAttMain sysAttMain, String userId, String type);

	/**
	 *

	 * @return
	 * @throws Exception
	 */
   String wpsCenterWrapHeader(List<Map<String, Object>> fileInfos, List<Map<String, Object>> fillDatas,
									  String fdModelId, String fdModelName, String fdAttMainId, String type, String userId);

	/**
	 * 获取WPS中台操作回调后的信息
	 *
	 * @return
	 * @throws Exception
	 */
	public JSONObject getCovertDownload(String taskID) throws Exception;

	/**
	 * 获取WPS中台操作回调后的信息
	 *
	 * @return
	 * @throws Exception
	 */
	JSONObject getCovertDownload(String fdModelId, String fdModelName, String type) throws Exception;

	/**
	 * 根据下载地址获取下载文件流
	 * @param downloadUrl 下载地址
	 * @return  结果流
	 * @throws Exception
	 */
	InputStream downloadWithUrl(String downloadUrl) throws Exception;

	/**
	 * 根据任务ID获取下载文件流
	 * @param taskId 任务ID
	 * @return 结果流
	 * @throws Exception
	 */
	InputStream downloadByTaskId(String taskId) throws Exception;

	/**
	 * 根据任务ID获取下载url
	 * @param taskId 任务ID
	 * @return 结果流
	 * @throws Exception
	 */
	String downloadUrlByTaskId(String taskId) throws Exception;

	/**
	 * 构造下载地址
	 * @param fdAttMainId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	String generateDownloadUrl(String fdAttMainId, String userId) throws Exception;

	void saveWpsCenterConfig(Map<String,String> params) throws Exception;

	/**
	 * 持久到数据库
	 * @param fdModelId
	 * @param fdModelName
	 * @param type
	 * @param taskID
	 * @param fdAttMainId
	 * @param fdFileId
	 * @param downloadUrl
	 * @param status
	 * @param convertKey
	 * @throws Exception
	 */
	void persistThirdWpsConvert(String fdModelId, String fdModelName, String type,
									   String taskID, String fdAttMainId,
									   String fdFileId,String downloadUrl,
									   String status, String convertKey) throws Exception;

	/**
	 * 获取转换用的WPS地址
	 * @return
	 */
	String getWpsUrl2Converter();

	/**
	 * 使用同步方式实现转换
	 */
	Boolean wpsCenterSyncConvertFile(Object jsonParameters) throws Exception;
}
