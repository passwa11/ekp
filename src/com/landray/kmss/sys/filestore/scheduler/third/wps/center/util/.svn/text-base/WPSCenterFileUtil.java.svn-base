package com.landray.kmss.sys.filestore.scheduler.third.wps.center.util;


import com.landray.kmss.sys.filestore.scheduler.third.wps.WpsUtil;
import com.landray.kmss.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;

/**
 * 文件处理工具类
 */
public class WPSCenterFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(WPSCenterFileUtil.class);

    /**
     * 处理空下载地址
     * @param fileName
     * @param attmainId
     * @return
     * @throws Exception
     */
    public static String getFilePath(String fileName, String attmainId) throws Exception{
        String queueUrl = getSystemUrl() + "/sys/attachment/sys_att_main/downloadFile.jsp?" +
                "fdId=%s&reqType=rest&Expires=%s&Signature=%s&filename=%s";
        long expires = System.currentTimeMillis() + (3 * 60 * 1000);// 下载链接3分钟有效
        String signature = WpsUtil.getRestSign(attmainId, expires);
       String  filePath = String.format(queueUrl, attmainId, expires, signature, URLEncoder.encode(fileName, "utf-8"));

       if(logger.isDebugEnabled()) {
           logger.debug("下载的地址为空， 生意构造的下载地址是：{}", filePath);
       }
       return filePath;
    }

    /**
     * 获取自己系统的访问地址
     *
     * @return
     */
    public static String getSystemUrl(){
        String localUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix"); //系统地址的前部分

        if(localUrl.endsWith("/"))
        {
            localUrl = localUrl.substring(0, localUrl.lastIndexOf("/"));
        }

        return localUrl;
    }

}
