package com.landray.kmss.sys.filestore.scheduler.third.dianju.api;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.attachment.integrate.wps.util.FileDowloadUtil;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.ConstantParameter;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.DianjuApi;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.RequestResponse;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.ConvertRequestResultDTO;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.DianjuConvertRequest;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.ConfigUtil;
import com.landray.kmss.sys.filestore.util.HttpClientUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 访问第三方应用接口实现
 */
public class FireThirdApplicationApiImp implements  FireThirdApplicationApi{
     private static final Logger logger = LoggerFactory.getLogger(FireThirdApplicationApiImp.class);
    /**
     * 请求文件转换
     * @param convertRequestParameter 请求参数
     * @param convertRequestHeader   请求头
     * @return
     * @throws Exception
     */
    @Override
    public ConvertRequestResultDTO convertFile(Map<String, Object> convertRequestParameter, Map<String, String> convertRequestHeader)
            throws Exception{
        try {
            String url =  ConfigUtil.dealUrl(ConfigUtil.configValue(ConstantParameter.CONVERT_DIANJU_THIRD_URL))
                    + DianjuApi.DIANJU_CONVERT_API;
            String result = HttpClientUtil.getInstance().doPost(url, convertRequestParameter, convertRequestHeader);
            if (logger.isDebugEnabled()) {
                logger.debug("请求第三文件系统返回的结果是：{}", result);
            }

            ConvertRequestResultDTO requestResultDTO = new ConvertRequestResultDTO();
            JSONObject json = JSONObject.parseObject(result);
            String retMsg  = json.getString(RequestResponse.DIANJN_CONVERT_REST_MSG);
            requestResultDTO.setStatusCode(retMsg);

            if(logger.isDebugEnabled()) {
                logger.debug("请求结果对象RequestResultDTO：{}" , requestResultDTO.toString());
            }

            return requestResultDTO;
        } catch (Exception e) {
            logger.error("请求文件转换失败:{}", e);
            throw e;
        }

    }

    /**
     * 请求获取文件转换后信息，用于下载
     *
     * @param convertRequestParameter 请求参数
     * @param convertRequestHeader    请求头
     * @return
     * @throws Exception
     */
    @Override
    public ConvertRequestResultDTO searchConvertInfo(Map<String, Object> convertRequestParameter, Map<String, String> convertRequestHeader)
              throws Exception {
       try {
           String url = ConfigUtil.dealUrl(ConfigUtil.configValue(ConstantParameter.CONVERT_DIANJU_THIRD_URL))
                   + DianjuApi.DIANJU_CONVERT_SEARCH_API;
           String result = HttpClientUtil.getInstance().doPost(url, convertRequestParameter, convertRequestHeader);
           if (logger.isDebugEnabled()) {
               logger.debug("请求第三文件系统返回的结果是：{}", result);
           }

           JSONObject json = JSONObject.parseObject(result);
           // 转换任务ID
           String taskId  = json.getString(RequestResponse.DIANJU_SEARCH_SERIAL_NUMBER);
           //下载的地址
           String downloadUrl = json.getString(RequestResponse.DIANJU_SEARCH_FILE_URL);

           ConvertRequestResultDTO requestResultDTO = new ConvertRequestResultDTO();
           requestResultDTO.setTaskId(taskId);
           requestResultDTO.setDownloadUrl(downloadUrl);

           if(logger.isDebugEnabled()) {
               logger.debug("请求结果对象RequestResultDTO：{}" , requestResultDTO.toString());
           }

           return requestResultDTO;
       } catch (Exception e) {
           logger.error("请求下载文件信息异常:{}", e);
           throw e;
       }

    }

    /**
     * 下载文件
     * @return
     */
    @Override
    public ConvertRequestResultDTO downloadConvertedFile(DianjuConvertRequest convertRequest) throws Exception{

        boolean isSuccessed = false;

        InputStream in = null;
        ConvertRequestResultDTO requestResultDTO = new ConvertRequestResultDTO();
        requestResultDTO.setStatusCode(RequestResponse.DIANJU_FAILURE);
        try {
            String url = convertRequest.getDownloadUrl();
            if(StringUtil.isNull(url)) {
                return requestResultDTO;
            }

            in = FileDowloadUtil.getFileInputStream(url);
            requestResultDTO.setBytes(IOUtils.toByteArray(in));
            requestResultDTO.setStatusCode(RequestResponse.DIANJU_SUCCESS);
            isSuccessed = true;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.error("error:{}", e);
                }

            }
        }

        if(!isSuccessed) {
            requestResultDTO.setStatusCode(RequestResponse.DIANJU_FAILURE);
        }

       return requestResultDTO;

    }

    /**
     * 关闭流
     * @param fos
     * @param is
     */
    public void closeStream( FileOutputStream fos, InputStream is) {
        try {
            if (fos != null) {
                fos.close();
            }

            if(is != null) {
                is.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
