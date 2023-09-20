package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.util;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerMainService;
import com.landray.kmss.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.List;

public class WPSCloudFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(WPSCloudFileUtil.class);

    /**
     * 目标文件转换类型
     * @param convertKey
     * @return
     */
    public static String getConvertFileFormat(String convertKey) {
        String targetFileFormat = "";
        if("toOFD".equals(convertKey)) {
            targetFileFormat = "ofd";
        } else if("toPDF".equals(convertKey)) {
            targetFileFormat = "pdf";
        }

        if(logger.isDebugEnabled()) {
            logger.debug("目标转换文件类型：{}" , targetFileFormat);
        }

        return targetFileFormat;
    }

    /**
     * 下载文件名称
     * @return
     */
    public static String getDownloadFileName(String convertKey) {
        String convertFileName = "";

        if("toPDF".equals(convertKey)) {
            convertFileName = "toPDF-WPS_pdf";
        } else if("toOFD".equals(convertKey)) {
            convertFileName = "toOFD-WPS_ofd";
        }

        if (logger.isDebugEnabled()) {
            logger.debug("转换后下载的文件名为：{}" , convertFileName);
        }

        return convertFileName;
    }

    public static String getDownloadUrl(SysFileConvertQueue deliveryTaskQueue) throws Exception{
        String fileName = deliveryTaskQueue.getFdFileName();
        String attmainId = 	deliveryTaskQueue.getFdAttMainId();
        String queueUrl = getSystemUrl() + "/sys/attachment/sys_att_main/downloadFile.jsp?" +
                "fdId=%s&reqType=rest&Expires=%s&Signature=%s&filename=%s";
        long expires = System.currentTimeMillis() + (3 * 60 * 1000);// 下载链接3分钟有效
        String signature = getRestSign(attmainId, expires);
        String  filePath = String.format(queueUrl, attmainId, expires, signature, URLEncoder.encode(fileName, "utf-8"));

        //logger.error("下载的地址为：" + filePath);

        if(logger.isDebugEnabled()) {
            logger.debug("下载的地址为：" + filePath);
        }
        return filePath;
    }

    /**
     * 为下载链接签名
     *
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
    /**
     * 获取自己系统的访问地址
     *
     * @return
     */
    public static String getSystemUrl(){
        String localUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix"); //系统地址的前部分

        if(localUrl.endsWith("/")) {
            localUrl = localUrl.substring(0, localUrl.lastIndexOf("/"));
        }

        return localUrl;
    }
}
