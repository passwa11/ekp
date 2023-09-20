package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.api;

import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.wps.WpsUtil;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.constant.WPSCloudApiConstant;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.constant.WPSCloudConstant;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain.WPSCloudConvertResult;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain.WPSCloudUpLoadResult;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.util.WPSCloudFileDowloadUtil;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.util.WPSCloudFileUtil;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.util.HttpClientUtilManage;
import com.landray.kmss.sys.filestore.util.StaticParametersUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求WPS接口实现
 */
public class WPSCloudApiImp implements WPSCloudApi{
    private static final Logger logger = LoggerFactory.getLogger(WPSCloudApiImp.class);

    /**
     * 上传文件到WPS
     * @param convertQueue 队列
     * @param wpsUrl WPS地址
     * @param url EKP地址
     * @return
     * @throws Exception
     */
    @Override
    public WPSCloudUpLoadResult uploadFileToWPSCloud(SysFileConvertQueue convertQueue,
                                                     String wpsUrl, String url){
        WPSCloudUpLoadResult wpsCloudUpLoadResult = new WPSCloudUpLoadResult();
        wpsCloudUpLoadResult.setResult(false);
        wpsCloudUpLoadResult.setConvertId("");
        try {
            String upUrl = wpsUrl + WPSCloudApiConstant.WPS_CLOUD_API_UP_FILE; //访问WPS地址

            Map<String, String> upHeader = new HashMap<String, String>(); //HTTP标题头
            upHeader.put("Content-Type", StaticParametersUtil.CONTENT_TYPE_FORM);
            Map<String, Object> upParameter = new HashMap<String, Object>(); //HTTP参数
            upParameter.put("url",url);
            upParameter.put("filename",convertQueue.getFdFileName());
            String result = HttpClientUtilManage.getInstance().doPost(upUrl,upParameter, upHeader);
            if(logger.isDebugEnabled()) {
                logger.debug("WPS转换:WPS访问地址：" + wpsUrl + ", EKP下载地址：" + url
                        +"上传文件到WPS服务器，结果:" + result);
            }
            if(result == null) {
                if(logger.isDebugEnabled()) {
                    logger.debug("WPS转换:上传文件到WPS服务器失败.队列ID:{}; 附件ID(attMainId):{};失败原因:{}",
                            convertQueue.getFdId(), convertQueue.getFdAttMainId(), result);
                }

                return new WPSCloudUpLoadResult(false, "", result);
            }


            //上传文件
            JSONObject resultJson = JSONObject.fromObject(result);
            String upCode = resultJson.getString(WPSCloudConstant.STRING_CODE); //请求返回编码 200成功

            WpsUtil.writeLog(upCode);

            if(StringUtil.isNotNull(upCode) && WPSCloudConstant.RESULT_SUCCESS.equals(upCode)) {
                Object upData = resultJson.get(WPSCloudConstant.STRING_DATA); //请求返回数据ID
                if(upData != null) {
                    JSONObject dataJson = JSONObject.fromObject(upData);
                    wpsCloudUpLoadResult.setConvertId(dataJson.getString(WPSCloudConstant.STRING_ID));
                    wpsCloudUpLoadResult.setResult(true);
                }
            }

            wpsCloudUpLoadResult.setWpsResult(result);

        } catch (Exception e) {
            logger.error("WPS转换:上传文件到WPS服务器失败.队列ID:{}; 附件ID(attMainId):{};异常信息:{}",
                        convertQueue.getFdId(), convertQueue.getFdAttMainId(), e);

            return new WPSCloudUpLoadResult(false, "", null);
        }

        return wpsCloudUpLoadResult;
    }

    /**
     * WPS转换文件
     * @param convertQueue 队列
     * @param convertId 转换ID
     * @param wpsUrl WPS请求地址
     * @return
     * @throws Exception
     */
    @Override
    public WPSCloudConvertResult convertFileFromWPSCloud(SysFileConvertQueue convertQueue,
                                                         String convertId, String wpsUrl){
        WPSCloudConvertResult wpsCloudConvertResult = new WPSCloudConvertResult();
        wpsCloudConvertResult.setWpsResult(null);
        wpsCloudConvertResult.setResult(false);
        wpsCloudConvertResult.setDownloadId("");

        try {
            String convertKey = convertQueue.getFdConverterKey();

            Map<String, String> convertHeader = new HashMap<String, String>(); //HTTP请求头
            convertHeader.put("Content-Type", StaticParametersUtil.CONTENT_TYPE_JSON);
            Map<String, Object> convertParameter = new HashMap<String, Object>(); //HTTP请求参数
            convertParameter.put("id", convertId);
            convertParameter.put("targetFileFormat", WPSCloudFileUtil.getConvertFileFormat(convertKey)); //需要转换类型
            String convertUrl = wpsUrl + WPSCloudApiConstant.WPS_CLOUD_API_CONVERT; //请求WPS地址
            String convertResult = HttpClientUtilManage.getInstance().doPost(convertUrl,
                    convertParameter, convertHeader);

            if (logger.isDebugEnabled()) {
                logger.debug("WPS转换,上传文件要求转换返回信息:" + convertResult);
            }

            JSONObject convertUrlResultJson = JSONObject.fromObject(convertResult);

            if(convertUrlResultJson != null) {
                String converCode = convertUrlResultJson.getString(WPSCloudConstant.STRING_CODE);
                WpsUtil.writeLog(converCode);

                if(StringUtil.isNotNull(converCode) && WPSCloudConstant.RESULT_SUCCESS.equals(converCode)) {
                    Object convertData = convertUrlResultJson.get(WPSCloudConstant.STRING_DATA); //请求返回数据ID
                    JSONObject dataJson = JSONObject.fromObject(convertData);
                    String downloadId = dataJson.getString(WPSCloudConstant.STRING_DOWNLOAD);

                    //如果下载或预览的ID为空，视为没有转换成功
                    if(StringUtil.isNotNull(downloadId)) {
                        wpsCloudConvertResult.setWpsResult(convertResult);
                        wpsCloudConvertResult.setResult(true);
                        wpsCloudConvertResult.setDownloadId(downloadId);
                    }
                }

            }
        } catch (Exception e) {
            logger.error("WPS转换失败,队列ID:{}; 附件ID(attMainId):{}; 异常信息:{}",
                    convertQueue.getFdId(), convertQueue.getFdAttMainId(), e);
            return wpsCloudConvertResult;
        }


        return wpsCloudConvertResult;
    }

    /**
     * 从WPS下载已经转换的文件
     * @param convertQueue 队列
     * @param downloadId 下载地址
     * @param serverUrl WPS请求地址
     * @return
     * @throws Exception
     */
    @Override
    public Boolean downloadConvertedFileFromWPSCloud(SysFileConvertQueue convertQueue, String downloadId,
                                                     String serverUrl){

        String downpUrl = serverUrl + WPSCloudApiConstant.WPS_CLOUD_API_DOWNLOAD;
        boolean isSuccessed = false;
        if (logger.isDebugEnabled()) {
            logger.debug("WPS转换,下载ID:" + downloadId);
        }
        //下载文件
        if(StringUtil.isNotNull(downloadId)) {
            InputStream is = null;

            try {
                String url = String.format(downpUrl, downloadId);
                is = WPSCloudFileDowloadUtil.getFileInputStream(url);
                if(is == null) {
                    logger.error("WPS转换下载文件为空");
                    return false;
                }
                String convertKey = convertQueue.getFdConverterKey();

                ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
                SysAttFile sysAsttFile = sysAttUploadService.getFileById(convertQueue.getFdFileId());
                if(sysAsttFile != null) {
              
                    String fdPath = sysAsttFile.getFdFilePath() + "_convert" + File.separator
                            + WPSCloudFileUtil.getDownloadFileName(convertKey);
                    if (logger.isDebugEnabled()) {
                        logger.debug("WPS转换,输出路径:" + fdPath);
                    }

                    SysFileLocationUtil.getProxyService().writeFile(is,fdPath);
                }

            } catch (Exception e) {
                isSuccessed = false;
                logger.error("WPS转换:下载WPS转换后的文件出现异常.队列ID:{},异常信息：{}", convertQueue.getFdId(), e);
            } finally {
                try{
                    if(is != null) {
                        is.close();
                    }
                } catch (Exception e) {
                    logger.error("error:", e);
                }
            }
            isSuccessed = true;
        }

        return isSuccessed;
    }
}
