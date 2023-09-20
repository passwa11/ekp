package com.landray.kmss.sys.filestore.scheduler.third.foxit.api;

import com.landray.kmss.sys.attachment.integrate.wps.util.FileDowloadUtil;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertRequestDto;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertResponseDto;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.util.FoxitUtil;
import com.landray.kmss.sys.filestore.util.HttpClientUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.landray.kmss.sys.filestore.scheduler.third.foxit.constant.FoxitApi.*;
import static com.landray.kmss.sys.filestore.scheduler.third.foxit.constant.FoxitConstant.*;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 实现请求第三方
 */
public class FireFoxitApplicationApiImpl implements FireFoxitApplicationApi {
    private static final Logger logger = LoggerFactory.getLogger(FireFoxitApplicationApiImpl.class);
    /**
     * 转换文件
     * @throws Exception
     */
    @Override
   public String  doConvertFile(Map<String, List<File>>  files, Map<String, String> filesInfos,  Map<String, String> headers)
            throws Exception {
        String convertUrl = FoxitUtil.serverUrl(FOXIT_SERVER_URL) + FOXIT_API_CONVERT;  // 转换地址
       String result = HttpClientUtil.getInstance()
               .doMultipartFormDataPost(convertUrl, files, filesInfos, headers);

       return result;
   }

    /**
     * 根据URL文件转换文件
     * @throws Exception
     */
    @Override
    public String doConvertFileByUrl(Map<String, Object> parameter, Map<String, String> header)
            throws Exception {
        String convertUrl = FoxitUtil.serverUrl(FOXIT_SERVER_URL) + FOXIT_API_CONVERT_BY_URL;  // 转换地址
        return HttpClientUtil.getInstance().doPost(convertUrl, parameter,  header);
    }

    /**
     * 下载文件
     * @return
     */
    @Override
    public  ConvertResponseDto downloadConvertedFile(ConvertRequestDto convertRequest) throws Exception{
        boolean isSuccessed = false;
        InputStream in = null;
        ConvertResponseDto crd = new ConvertResponseDto();
        crd.setStatus(FOXIT_FAILURE);
        crd.setDeliveryTaskQueue(convertRequest.getDeliveryTaskQueue());
        try {
            String url = convertRequest.getDownloadConvertUrl();
            if(StringUtil.isNull(url)) {
                return crd;
            }

            in = FileDowloadUtil.getFileInputStream(url);
            crd.setBytes(IOUtils.toByteArray(in));
            crd.setStatus(FOXIT_SUCCESS);
            isSuccessed = true;

        } catch (Exception e) {
            logger.error("error:", e);
            throw e;
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                   logger.error("error:", e);
                }

            }
        }

        if(!isSuccessed) {
            crd.setStatus(FOXIT_FAILURE);
        }

        return crd;
    }
}
