package com.landray.kmss.sys.filestore.scheduler.third.wps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.util.WPSCloudFileDowloadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.sys.attachment.io.IOUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.impl.AbstractQueueScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.wps.interfaces.IWpsConvertScheduler;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.util.HttpClientUtilManage;
import com.landray.kmss.sys.filestore.util.StaticParametersUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

import net.sf.json.JSONObject;

/**
 * WPS转换实现(最初的WPS云转换功能，现在不使用。
 * 当前使用的是{@link com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service.imp.WPSConvertSchedulerImp})
 * @author
 * @date 2020-09-09
 *
 */
public class WpsConvertSchedulerImp extends AbstractQueueScheduler
		implements IWpsConvertScheduler {
	public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded; charset=UTF-8";
	public static final String CONVERT_CONTENT_TYPE_FORM = "application/json; charset=UTF-8";
	
	protected static final Logger logger = LoggerFactory.getLogger(WpsConvertSchedulerImp.class);
	/*WPS服务返回的结果参数名*/
	private static final String STRING_CODE = "code";
	private static final String STRING_DATA = "data";
	private static final String STRING_ID = "id";
	private static final String STRING_DOWNLOAD = "download";
	private static final String STRING_PREVIEW = "preview";
	
	/*WPS服务返回的结果编码*/
	private static final String RESULT_SUCCESS = "200";

	
	private static final int CONVERT_NUMBER = 3;
	
	
	@Override
	protected String getThreadName() {
		return "wpsConvertScheduler";
	}

	public void setDataService(ISysFileConvertDataService dataService) {
		this.dataService = dataService;
	}

	@Override
	protected void doDistributeConvertQueue(String encryptionMode) {
		TransactionStatus status = null;
		Throwable throwable = null;
		try {
				status = TransactionUtils.beginNewTransaction();
			    //TODO 需要写个新的接口来查找队列的信息
				List<SysFileConvertQueue> unsignedTasks = new ArrayList<SysFileConvertQueue>();
			List<SysFileConvertQueue> tasks = dataService.getUnassignedTasksByconverterKeys("remote", new String[]{"toOFD", "toPDF"});
				
				for (SysFileConvertQueue deliveryTaskQueue : tasks) {

					if(isEnableExecute(deliveryTaskQueue) ) {
						String filePath = deliveryTaskQueue.getFdFileDownUrl();
						String queueFileName = deliveryTaskQueue.getFdFileName();

						//没有文件名，只有后缀的视为无效文件
						if (".doc".equalsIgnoreCase(queueFileName) || ".docx".equalsIgnoreCase(queueFileName)) {
							dataService.setRemoteConvertQueue(null, "taskInvalid",
									deliveryTaskQueue.getFdId(), "",
									"文件名：" + queueFileName + ",转换失败，因为文件名为【.doc】或【.docx】:"
											+ WpsUtil.configInfo("thirdWpsCenterUrl"));
							continue;
						}

						//如果下载地址为空处理
						if (StringUtil.isNull(filePath)) {
							String queueUrl = "/sys/attachment/sys_att_main/downloadFile.jsp?" +
									"fdId=%s&reqType=rest&filename=%s";
							filePath = String.format(queueUrl, deliveryTaskQueue.getFdAttMainId(), URLEncoder.encode(queueFileName, "utf-8"));
							logger.info("队列文件下载地址为空，构造文件下载地址是：" + filePath);
						}

						confirmTransaction( "begin", false, deliveryTaskQueue);
						boolean result = distributeConvertFile(deliveryTaskQueue, encryptionMode);
						if(!result) //如果为false ,则查看下是否是地址为空，为空说明不为下载地址，则直接关闭转换队列
						{
							 String selfUrl = WpsUtil.systemHttp(filePath, deliveryTaskQueue.getFdAttMainId()); //EKP下载地址
							 if(StringUtil.isNull(selfUrl))
						      {
						    	  dataService.setRemoteConvertQueue(null, "taskInvalid",
						    			  deliveryTaskQueue.getFdId(), "",
											"转换队列下载地址无效,将此条记录变更为无效记录。下载地址为:" + deliveryTaskQueue.getFdFileDownUrl());
						    	  TransactionUtils.commit(status);
						    	  continue;
						      }
						}
						confirmTransaction("end", result,  deliveryTaskQueue);
					}
					
				}
			TransactionUtils.commit(status);

		} catch (Exception e) {
			throwable = e;
			logger.error("文件存储加密线程执行出错", e);
		}finally
		{
			if (throwable != null && status != null) {
				TransactionUtils.rollback(status);
			}

			
		}
	}

	public Boolean isEnableExecute(SysFileConvertQueue deliveryTaskQueue) {
		//1.【集成管理】->【其它应用集成】->【WPS集成】中的【在线预览配置】中需要开启【启用在线预览服务】
		//2.【附件转换】->【转换配置】->【开启转换服务】是WPS
		//3.【集成管理】->【其它应用集成】->【WPS集成】中的【在线预览配置】中需要是Linux环境
		return StringUtil.isNotNull(WpsUtil.configInfo("thirdWpsPreviewEnabled"))
				&& "true".equals(WpsUtil.configInfo("thirdWpsPreviewEnabled"))
				&& StringUtil.isNotNull(deliveryTaskQueue.getFdConverterType())
				&& "wps".equals(deliveryTaskQueue.getFdConverterType())
				&& StringUtil.isNotNull(WpsUtil.configInfo("thirdWpsOS"))
				&& "linux".equals(WpsUtil.configInfo("thirdWpsOS"));
	}

	public boolean distributeConvertFile(SysFileConvertQueue deliveryTaskQueue, String encryptionMode) throws Exception
	{
		//上传文件
		String resultID =  upFileToRemote(deliveryTaskQueue);
		String downloadID = "";
		boolean result = false;
		//转换文件
		if(StringUtil.isNotNull(resultID))
		{
			downloadID = convertFile(deliveryTaskQueue, resultID); 
		}
		//下载文件
		if(StringUtil.isNotNull(downloadID))
		{
			result = downloadFile(deliveryTaskQueue, downloadID);
		}
		
		return result;
	}
	
	
	/**
	 * 上传文件
	 * @throws Exception 
	 */

	public String upFileToRemote(SysFileConvertQueue convertQueue) throws Exception {
		  String convertId = ""; //上传后的ID
	      String selfUrl = WpsUtil.systemHttp(convertQueue.getFdFileDownUrl(), convertQueue.getFdAttMainId()); //EKP下载地址
	      String url =  WpsUtil.configInfo("thirdWpsSetRedUrl");

	      if(StringUtil.isNull(selfUrl))
	      {
	    	  return "";
	      }
	      
	      if (url.endsWith("/")) 
		  {
			 url = url.substring(0, url.lastIndexOf("/"));
		  }
	      
	       String upUrl = url + "/web-preview/api/httpFile"; //访问WPS地址
		
	       Map<String, String> upHeader = new HashMap<String, String>(); //HTTP标题头
		   upHeader.put("Content-Type", StaticParametersUtil.CONTENT_TYPE_FORM);
		   Map<String, Object> upParameter = new HashMap<String, Object>(); //HTTP参数
		   upParameter.put("url",selfUrl);
		   upParameter.put("filename",convertQueue.getFdFileName());
		   boolean success = false;

			try 
			{
				String upUrlResult = HttpClientUtilManage.getInstance().doPost(upUrl,upParameter, upHeader);
				if(upUrlResult == null)
				{
					logger.error(">>>>>>WPS转换:上传文件到WPS服务器失败.队列ID:" + convertQueue.getFdId());
					WpsUtil.saveResultLogs(convertQueue.getFdAttMainId(),"1", "0", "WPS转换:上传文件到WPS服务器失败,失败原因：connection time out");
					return convertId;
				}
				//上传文件
					JSONObject hitUrlResultJson = JSONObject.fromObject(upUrlResult);
					
					if(hitUrlResultJson != null)
					{
						String upCode = hitUrlResultJson.getString(STRING_CODE); //请求返回编码 200成功
						WpsUtil.writeLog(upCode);
						
						if(StringUtil.isNotNull(upCode) && RESULT_SUCCESS.equals(upCode))
						{
							//saveResultLogs(convertQueue.getFdAttMainId(),"1", "1", upUrlResult);
							Object upData = hitUrlResultJson.get(STRING_DATA); //请求返回数据ID
							
							if(upData != null)
							{
								JSONObject dataJson = JSONObject.fromObject(upData);
								convertId = dataJson.getString(STRING_ID);
								success = true;
							}
						}
					}

				
				
				if(!success)
				{
					logger.error("WPS转换:上传文件到WPS服务器失败.队列ID:" + convertQueue.getFdId());
					WpsUtil.saveResultLogs(convertQueue.getFdAttMainId(),"1", "0", "WPS转换:上传文件到WPS服务器失败:" + upUrlResult);
				//	confirmTransaction(false, convertQueue);
				}
				else
				{
					WpsUtil.saveResultLogs(convertQueue.getFdAttMainId(),"1", "1", "WPS转换:上传文件到WPS服务器成功:" +upUrlResult);
				}
			} 
			catch (Exception e) 
			{
				logger.error("WPS转换:上传文件到WPS服务器出现异常.队列ID:" + convertQueue.getFdId());
			//	confirmTransaction(false, convertQueue);
				logger.error(e.getMessage(), e);
			}
		return convertId;
	}

	/**
	 * 转换文件
	 * @throws Exception 
	 */

	public String convertFile(SysFileConvertQueue convertQueue, String convertId) throws Exception {
		String downloadId = "";
		String previewId  = "";
		boolean success = false;
		String convertKey = convertQueue.getFdConverterKey();
		String targetFileFormat = "";
		
		if("toOFD".equals(convertKey))
		{
			targetFileFormat = "ofd";
		}
		else if("toPDF".equals(convertKey))
		{
			targetFileFormat = "pdf";
		}
		//上传文件后转换
		if(StringUtil.isNotNull(convertId) && StringUtil.isNotNull(targetFileFormat))
		{
			Map<String, String> convertHeader = new HashMap<String, String>(); //HTTP请求头
			convertHeader.put("Content-Type", StaticParametersUtil.CONTENT_TYPE_JSON);
			Map<String, Object> convertParameter = new HashMap<String, Object>(); //HTTP请求参数
			convertParameter.put("id", convertId);
			convertParameter.put("targetFileFormat", targetFileFormat); //需要转换类型
			String url =  WpsUtil.configInfo("thirdWpsSetRedUrl");
			if (url.endsWith("/")) 
			{
			   url = url.substring(0, url.lastIndexOf("/"));
			}
			
			String convertUrl = url + "/web-preview/api/convert"; //请求WPS地址
			
			try
			{
				String convertResult = HttpClientUtilManage.getInstance().doPost(convertUrl,
						convertParameter, convertHeader);
				logger.debug("WPS转换,上传文件要求转换返回信息:" + convertResult);
				
					JSONObject convertUrlResultJson = JSONObject.fromObject(convertResult);
					
					if(convertUrlResultJson != null)
					{
						String converCode = convertUrlResultJson.getString(STRING_CODE);
						WpsUtil.writeLog(converCode);
						
						if(StringUtil.isNotNull(converCode) && RESULT_SUCCESS.equals(converCode))
						{
							Object convertData = convertUrlResultJson.get(STRING_DATA); //请求返回数据ID
							WpsUtil.saveResultLogs(convertQueue.getFdAttMainId(),"2", "1", convertResult);
							if(convertData != null)
							{
								JSONObject dataJson = JSONObject.fromObject(convertData);
								downloadId = dataJson.getString(STRING_DOWNLOAD);
							//	previewId = dataJson.getString(STRING_PREVIEW);
								
								//如果下载或预览的ID为空，视为没有转换成功
								if(StringUtil.isNotNull(downloadId))
								{
								//	saveResults(convertQueue.getFdAttMainId(), convertId, downloadId, previewId);
									success = true;
								}
							}
						}
						
					}
				
				logger.debug("WPS转换:要求WPS服务器对上传的文件转换成功与否:" + success);
			//	confirmTransaction(success, convertQueue);
				if(success)
				{
					WpsUtil.saveResultLogs(convertQueue.getFdAttMainId(),"2", "1", "WPS转换:要求WPS服务器对上传的文件转换成功:" + convertResult);
				}
				else
				{
					WpsUtil.saveResultLogs(convertQueue.getFdAttMainId(),"2", "0", "WPS转换:要求WPS服务器对上传的文件转换失败:" + convertResult);
				}
			} 
			catch (Exception e)
			{
				logger.error("WPS转换:要求WPS服务器对上传的文件转换出现异常.队列ID:" + convertQueue.getFdId());
			//	confirmTransaction(false, convertQueue);
				logger.error(e.getMessage(), e);
			}
		
		}
		else
		{
			logger.error("WPS转换:要求WPS服务器对上传的文件转换出现异常.可能为空参数:convertId" + convertId + ",targetFileFormat:"+targetFileFormat);
		}
		return downloadId;
	}

	
	/**
	 * 下载文件
	 */

	public boolean downloadFile(SysFileConvertQueue convertQueue, String downloadId) 
	{
		String serverUrl =  WpsUtil.configInfo("thirdWpsSetRedUrl");
		if (serverUrl.endsWith("/")) 
		{
		 serverUrl = serverUrl.substring(0, serverUrl.lastIndexOf("/"));
		}
		 
        String downpUrl = serverUrl + "/web-preview/api/download/%s";
        boolean isSuccessed = false;
		
		//下载文件
		if(StringUtil.isNotNull(downloadId))
		{
			FileOutputStream fos = null;
			String url = String.format(downpUrl, downloadId);
			try (InputStream is = WPSCloudFileDowloadUtil.getFileInputStream(url))
			{
				String convertKey = convertQueue.getFdConverterKey();

				String convertFileName = "";
                
                if("toPDF".equals(convertKey))
                {
                	convertFileName = "toPDF-WPS_pdf";
                }
                else if("toOFD".equals(convertKey))
                {
                	convertFileName = "toOFD-WPS_ofd";
                }
                ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
        		SysAttFile sysAsttFile = sysAttUploadService.getFileById(convertQueue.getFdFileId());
        		if(sysAsttFile != null)
        		{
        			String basePath = ResourceUtil.getKmssConfigString("kmss.resource.path");
    				if (basePath.endsWith("/")) {
    					basePath = basePath.substring(0, basePath.lastIndexOf("/"));
    				}
        			String fdPath = basePath + sysAsttFile.getFdFilePath() + "_convert" + File.separator
        					+ convertFileName;
        			File filePath = new File( basePath + sysAsttFile.getFdFilePath() + "_convert");
        			if(!filePath.exists())
        			{
        				filePath.mkdirs();
        			}
        			 File file = new File(fdPath);
     				fos = new FileOutputStream(file);
     				IOUtil.write(is, fos);  
        		}
			} catch (Exception e) {
				logger.error("WPS转换:下载WPS转换后的文件出现异常.队列ID:" + convertQueue.getFdId());
				logger.error(e.getMessage(), e);
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (IOException e) {
					logger.error("WPS转换:下载WPS转换后的文件出现异常[fos.close()].队列ID:" + convertQueue.getFdId());
					logger.error(e.getMessage(), e);
				}
			}
			isSuccessed = true;
		}
		return isSuccessed;
	}
	
	/**
	 * 添加结果--此处不需要再保存了，由于返回的信息不使用
	 * 
	 * 注意：表和代码保留，可以后期使用
	 * @param attMainId  附件表ID
	 * @param fdConvertOfdId 转换OFD的ID
	 * @param fdOfdDownId   下载ID
	 * @param fdOfdPreviewId  预览ID 使用时，前端需要编码：encodeURIComponent
	 * @throws Exception
	 */
	/*
	public void saveResults(String attMainId, String fdConvertOfdId, String fdOfdDownId, String fdOfdPreviewId) throws Exception
	{
	
		IWpsConvertOfdService wpsConvertOfdService = (IWpsConvertOfdService) SpringBeanUtil.getBean("wpsConvertOfdService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("thirdWpsConvertOfd.fdAttMainId=:fdAttMainId");
		hqlInfo.setParameter("fdAttMainId", attMainId);
		List<ThirdWpsConvertOfd> list = wpsConvertOfdService.findList(hqlInfo);
		
		if(ArrayUtil.isEmpty(list))
		{
			ThirdWpsConvertOfd twco = new ThirdWpsConvertOfd();
			twco.setFdAttMainId(attMainId);
			twco.setResultId(fdConvertOfdId);
			twco.setDownloadId(fdOfdDownId);
			twco.setPreviewId(fdOfdPreviewId);
			wpsConvertOfdService.add(twco);
		}
		else
		{
			ThirdWpsConvertOfd twco = list.get(0);
			twco.setFdAttMainId(attMainId);
			twco.setResultId(fdConvertOfdId);
			twco.setDownloadId(fdOfdDownId);
			twco.setPreviewId(fdOfdPreviewId);
			wpsConvertOfdService.update(twco);
		}
		
	}
	
	*/
	
	
	/**
	 * 事务处理
	 * @param success 转换成功与否
	 * @param convertQueue
	 * @param status begin:开始进入转换  end 结束转换
	 */
	
	public void confirmTransaction(String status, boolean success, SysFileConvertQueue convertQueue)
	{
		try
		{
			if("begin".equals(status))
			{
				dataService.setRemoteConvertQueue(null, "taskAssigned", convertQueue.getFdId(), "",
						"分配给转换服务【WPS】:" + WpsUtil.configInfo("thirdWpsSetRedUrl"));
			}
			else
			{
				if(success)
				{
					dataService.setRemoteConvertQueue(null, "otherConvertFinish", convertQueue.getFdId(), "",
							"转换服务转换成功【WPS】:" + WpsUtil.configInfo("thirdWpsSetRedUrl"));
				}
				else
				{
					dataService.setRemoteConvertQueue(null, "taskUnAssigned",
							convertQueue.getFdId(), "",
							"分配任务的时候发送消息到转换服务不成功，请检查转换服务【WPS】:" + WpsUtil.configInfo("thirdWpsSetRedUrl"));
					
					logger.debug("WPS转换:WPS转换失败.队列ID:" + convertQueue.getFdId());
				}
			}
			
		}
		catch (Exception e)
		{
			//convertQueue.setFdConvertStatus(SysFileConvertConstant.FAILURE);
			logger.error("WPS转换更新表sys_File_Convert_Queue异常：" + e.getStackTrace());
			logger.error(e.getMessage(), e);
		}
		
	}
	

}
