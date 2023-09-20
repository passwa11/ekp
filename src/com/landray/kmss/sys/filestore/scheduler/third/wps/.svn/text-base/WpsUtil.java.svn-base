package com.landray.kmss.sys.filestore.scheduler.third.wps;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.appconfig.model.BaseAppconfigCache;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.model.ThirdWpsConvertOfdLog;
import com.landray.kmss.sys.filestore.service.IWpsConvertOfdLogService;
import com.landray.kmss.sys.filestore.util.HttpClientUtilManage;
import com.landray.kmss.sys.filestore.util.StaticParametersUtil;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerMainService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SignUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

/**
 *Wps文件校验工具类
 * @author zhengxin
 *
 */
public class WpsUtil {

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(WpsUtil.class);
	private static final String RESULT_ERROR_PARAMETER = "400";
	private static final String RESULT_NON_AUTHENTOR  = "403.3";
	private static final String RESULT_SERVER_ERRROR = "500";
	/*WPS服务返回的结果参数名*/
	private static final String STRING_CODE = "code";
	private static final String STRING_DATA = "data";
	private static final String STRING_ID = "id";
	private static final String STRING_DOWNLOAD = "download";
	private static final String STRING_PREVIEW = "preview";
	/*WPS服务返回的结果编码*/
	private static final String RESULT_SUCCESS = "200";


	public static Boolean isWpsOfdConvertEnable() {
		String url = null;
		url = configInfo("thirdWpsSetRedUrl");
		if(url.endsWith("/"))
		{
			url = url.substring(0, url.lastIndexOf("/"));
		}
		url = url+"/web-preview/";
		Boolean isEnable = false;
		URL urlPath;
		try {
			urlPath = new URL(url);
			// InputStream in = url.openStream();
			HttpURLConnection con = (HttpURLConnection) urlPath.openConnection();
			con.setConnectTimeout(1500);
			int state = con.getResponseCode();
			if (state == 200) {
				isEnable = true;
			}
		} catch (Exception e1) {
			isEnable = false;
			urlPath = null;
		}
		return isEnable;
	}

	/**
	 * wps服务地址：thirdWpsSetRedUrl
	 * @param name
	 * @return
	 */
	public static String configInfo(String name)
	{
		Map<String, String> dataMap = BaseAppconfigCache.getCacheData("com.landray.kmss.third.wps.model.ThirdWpsConfig");
		String config = "";
		if (!dataMap.isEmpty())
		{
			config = (String) dataMap.get(name);
		}

		return config;
	}


	/**
	 * 上传文件
	 * @throws Exception
	 */

	public static String upFileToRemote(String ekpUrl, String attMainId, boolean addPrefix) throws Exception {
		String convertId = ""; // 上传后的ID
		String selfUrl = "";
		if (addPrefix) {
			selfUrl = systemHttp(ekpUrl, attMainId); // EKP下载地址
		} else {
			selfUrl = ekpUrl;
		}

		String url = configInfo("thirdWpsSetRedUrl");
		if (url.endsWith("/")) {
			url = url.substring(0, url.lastIndexOf("/"));
		}

		String upUrl = url + "/web-preview/api/httpFile"; // 访问WPS地址

		Map<String, String> upHeader = new HashMap<String, String>(); // HTTP标题头
		upHeader.put("Content-Type", StaticParametersUtil.CONTENT_TYPE_FORM);
		Map<String, Object> upParameter = new HashMap<String, Object>(); // HTTP参数
		upParameter.put("url", selfUrl);
		try {
			String upUrlResult = HttpClientUtilManage.getInstance().doPost(upUrl, upParameter, upHeader);

			// 上传文件
			JSONObject hitUrlResultJson = JSONObject.fromObject(upUrlResult);

			if (hitUrlResultJson != null) {
				String upCode = hitUrlResultJson.getString(STRING_CODE); // 请求返回编码 200成功
				writeLog(upCode);

				if (StringUtil.isNotNull(upCode) && RESULT_SUCCESS.equals(upCode)) {
					Object upData = hitUrlResultJson.get(STRING_DATA); // 请求返回数据ID

					if (upData != null) {
						JSONObject dataJson = JSONObject.fromObject(upData);
						convertId = dataJson.getString(STRING_ID);
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return convertId;
	}
	public static String upFileToRemote(String ekpUrl, String attMainId, String fileName, boolean addPrefix) throws Exception {
		String convertId = ""; // 上传后的ID
		String selfUrl = "";
		if (addPrefix) {
			selfUrl = systemHttp(ekpUrl, attMainId); // EKP下载地址
		} else {
			selfUrl = ekpUrl;
		}

		String url = configInfo("thirdWpsSetRedUrl");
		if (url.endsWith("/")) {
			url = url.substring(0, url.lastIndexOf("/"));
		}

		String upUrl = url + "/web-preview/api/httpFile"; // 访问WPS地址

		Map<String, String> upHeader = new HashMap<String, String>(); // HTTP标题头
		upHeader.put("Content-Type", StaticParametersUtil.CONTENT_TYPE_FORM);
		Map<String, Object> upParameter = new HashMap<String, Object>(); // HTTP参数
		upParameter.put("url", selfUrl);
		upParameter.put("filename", fileName);
		try {
			String upUrlResult = HttpClientUtilManage.getInstance().doPost(upUrl, upParameter, upHeader);

			// 上传文件
			JSONObject hitUrlResultJson = JSONObject.fromObject(upUrlResult);

			if (hitUrlResultJson != null) {
				String upCode = hitUrlResultJson.getString(STRING_CODE); // 请求返回编码 200成功
				writeLog(upCode);

				if (StringUtil.isNotNull(upCode) && RESULT_SUCCESS.equals(upCode)) {
					Object upData = hitUrlResultJson.get(STRING_DATA); // 请求返回数据ID

					if (upData != null) {
						JSONObject dataJson = JSONObject.fromObject(upData);
						convertId = dataJson.getString(STRING_ID);
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return convertId;
	}
	/**
	 * 转换文件
	 * @throws Exception
	 */

	public static String convertFile(String convertId, String targetFileFormat) throws Exception {
		//String downloadId = "";
		String previewId  = "";
		boolean success = false;

		//上传文件后转换
		if(StringUtil.isNotNull(convertId))
		{
			Map<String, String> convertHeader = new HashMap<String, String>(); //HTTP请求头
			convertHeader.put("Content-Type", StaticParametersUtil.CONTENT_TYPE_JSON);
			Map<String, Object> convertParameter = new HashMap<String, Object>(); //HTTP请求参数
			convertParameter.put("id", convertId);
			convertParameter.put("targetFileFormat", targetFileFormat);
			String url =  configInfo("thirdWpsSetRedUrl");

			if (url.endsWith("/"))
			{
				url = url.substring(0, url.lastIndexOf("/"));
			}

			String convertUrl = url + "/doc-convert/api/convert"; //请求WPS地址

			try
			{
				String convertResult = HttpClientUtilManage.getInstance().doPost(convertUrl,
						convertParameter, convertHeader);
				logger.debug("WPS转OFD上传文件要求转换返回信息:" + convertResult);

				JSONObject convertUrlResultJson = JSONObject.fromObject(convertResult);

				if(convertUrlResultJson != null)
				{
					String converCode = convertUrlResultJson.getString(STRING_CODE);
					writeLog(converCode);

					if(StringUtil.isNotNull(converCode) && RESULT_SUCCESS.equals(converCode))
					{
						Object convertData = convertUrlResultJson.get(STRING_DATA); //请求返回数据ID

						if(convertData != null)
						{
							JSONObject dataJson = JSONObject.fromObject(convertData);
							previewId = dataJson.getString(STRING_PREVIEW);

						}
					}

				}

				logger.debug("WPS转换:要求WPS服务器对上传的文件转换成功与否:" + success);

			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}

		}
		return previewId;
	}


	/**
	 * 添加结果日志信息
	 *
	 * 转换过程中如果有失败后成功，则失败一条，成功一条记录
	 * @param attMainId  附件表ID
	 * @param type 类型：1-上传, 2-转换, 3-清稿， 4-套红
	 * @param status   状态：1-成功；0-失败
	 * @param result  结果信息
	 * @throws Exception
	 */
	public static void saveResultLogs(String attMainId, String type, String status, String result) throws Exception
	{

		IWpsConvertOfdLogService wpsConvertOfdLogService = (IWpsConvertOfdLogService) SpringBeanUtil.getBean("wpsConvertOfdLogService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("thirdWpsConvertOfdLog.fdAttMainId=:fdAttMainId and thirdWpsConvertOfdLog.fdType=:fdType"
				+ " and thirdWpsConvertOfdLog.fdStatus=:fdStatus");
		hqlInfo.setParameter("fdAttMainId", attMainId);
		hqlInfo.setParameter("fdType", type);
		hqlInfo.setParameter("fdStatus", status);

		Object obj = wpsConvertOfdLogService.findFirstOne(hqlInfo);


		if(obj == null)
		{
			ThirdWpsConvertOfdLog twcol = new ThirdWpsConvertOfdLog();
			twcol.setFdAttMainId(attMainId);
			twcol.setFdResult(result);
			twcol.setFdType(type);
			twcol.setFdStatus(status);
			twcol.setFdcreateTime(new Date());
			wpsConvertOfdLogService.add(twcol);
		}
		else
		{
			ThirdWpsConvertOfdLog twcol = (ThirdWpsConvertOfdLog)obj;
			twcol.setFdResult(result);
			twcol.setFdStatus(status);
			twcol.setFdcreateTime(new Date());
			wpsConvertOfdLogService.update(twcol);
		}

	}


	/**
	 * 请求WPS返回的异常结果记录
	 *
	 * @param code
	 */
	public static void writeLog(String code)
	{
		switch(code)
		{
			case RESULT_ERROR_PARAMETER:
				logger.debug("WPS转换参数错误");
				break;
			case RESULT_NON_AUTHENTOR:
				logger.debug("WPS转换未授权");
				break;
			case RESULT_SERVER_ERRROR:
				logger.debug("WPS转换服务器处理失败");
				break;
			default:
				logger.debug("WPS转换返回的code:" + code);
		}
	}

	/**
	 * 第三方到本系统下载附件的地址
	 * @param url
	 * @param attMainId
	 * @return
	 * @throws Exception
	 */
	public static String systemHttp(String url, String attMainId) throws Exception
	{
		if(StringUtil.isNull(url))
		{
			logger.error("下载地址为空，请检查。");
			return "";
		}

		if(!url.contains("/sys/attachment/sys_att_main/downloadFile.jsp"))
		{
			logger.error("不是附件下载地址：" + url);
			return "";
		}

		long expires = System.currentTimeMillis() + (3 * 60 * 1000);// 下载链接3分钟有效
		String signature = getRestSign(attMainId, expires);
		String localUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix"); //系统地址的前部分

		if(localUrl.endsWith("/"))
		{
			localUrl = localUrl.substring(0, localUrl.lastIndexOf("/"));
		}


		if(url.contains("Expires") && url.contains("Signature")) //兼容旧数据
		{
			String[] parameters = url.split("&");
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < parameters.length; i++)
			{
				if(parameters[i].contains("Expires"))
				{
					sb.append("Expires=").append(expires).append("&");
				}
				else if(parameters[i].contains("Signature"))
				{
					sb.append("Signature=").append(signature).append("&");
				}else
				{
					sb.append(parameters[i]).append("&");
				}
			}

			String resultUrl = localUrl + sb.toString().substring(0, sb.lastIndexOf("&"));

			return resultUrl;
		}
		else
		{
			String[] parameters = url.split("&");
			String addParaUrl = parameters[0] + "&Expires="+expires+"&Signature=" + signature + "&" + parameters[1] + "&" +  parameters[2];
			String resultUrl = localUrl + addParaUrl;

			return resultUrl;
		}

	}

	/**
	 * 为下载链接签名
	 *
	 * @param method
	 * @param expires
	 * @param attMainId
	 * @return
	 * @throws Exception
	 */
	public static String getRestSign(String attMainId, long expires) throws Exception {
		String signStr = expires + ":" + attMainId;
		ISysRestserviceServerMainService sysRestMainService = (ISysRestserviceServerMainService) SpringBeanUtil
				.getBean("sysRestserviceServerMainService");
		SysRestserviceServerMain sysRestserviceServerMain = sysRestMainService
				.findByServiceBean("sysAttachmentRestService");
		if(sysRestserviceServerMain == null){
			return "";
		}
		List<SysRestserviceServerPolicy> webPolicys = sysRestserviceServerMain.getFdPolicy();
		if (ArrayUtil.isEmpty(webPolicys)) {
			return "";
		}
		SysRestserviceServerPolicy webPolicy = webPolicys.get(0);
		String sign = SignUtil.getHMAC(signStr + ":" + webPolicy.getFdLoginId(),
				StringUtil.isNotNull(webPolicy.getFdPassword()) ? webPolicy.getFdPassword() : webPolicy.getFdId());
		return sign;
	}
}
