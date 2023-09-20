package com.landray.kmss.sys.filestore.scheduler.third.foxit.util;

import com.landray.kmss.sys.appconfig.model.BaseAppconfigCache;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.scheduler.third.wps.WpsUtil;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

/**
 *  工具类
 */
public class FoxitUtil {
    private static final Logger logger = LoggerFactory.getLogger(FoxitUtil.class);
    private static ISysAttUploadService sysAttUploadService;
    public static ISysAttUploadService getSysAttUploadService() {
        if(sysAttUploadService == null) {
            sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
        }

        return sysAttUploadService;
    }

    /**
     * 获取文件所在根路径
     * @return
     */
    public static String getResourcePath(String fileRelativePath) {
        String cfgPath =  ResourceUtil.getKmssConfigString("kmss.resource.path");

        cfgPath = cfgPath.replaceAll("\\\\", "/");
        if (!cfgPath.endsWith("/")) {
            cfgPath += "/";
        }
        fileRelativePath = fileRelativePath.replaceAll("\\\\", "/");
        if (fileRelativePath.startsWith("/")) {
            fileRelativePath = fileRelativePath.substring(1);
        }
        String rsourcePath = cfgPath + fileRelativePath;

        if(logger.isDebugEnabled()) {
            logger.debug("文件所在的相对路径：{}", rsourcePath);
        }
        return rsourcePath;
    }

    /**
     * 删除文件

     * @throws Exception
     */
    public static void deleteFile(String filePath) throws Exception {
        SysFileLocationUtil.getProxyService().deleteFile(filePath);
    }

    public static String fileAbsolutePath(String fdFileId, String fdFilePath,
                                          String extendName, String toConvertType) {

        String filePath = fdFilePath + "_convert" + File.separator + fdFileId + "_"
                + toConvertType +"." + extendName;
        return filePath;
    }

    public static String configValue(String name) {
        Map<String, String> dataMap = BaseAppconfigCache.getCacheData("com.landray.kmss.third.foxit.model.ThirdFoxitConfig");
        String config = "";
        if (!dataMap.isEmpty()) {
            config = (String) dataMap.get(name);
        }

        if(logger.isDebugEnabled()) {
            logger.debug("获取配置属性名:{},属性值:{}", name , config);
        }

        return config;
    }

    /**
     * 处理地址最后不带‘/’
     * @param url
     * @return
     */
    public static String dealUrl(String url) {
        if(StringUtil.isNull(url)) {
            return url;
        }
        if(url.endsWith("/")) {
            url = url.substring(0, url.lastIndexOf("/"));
        }

        return url.trim();
    }

    /**
     * 服务地址
     * @param name
     * @return
     */
    public static String serverUrl(String name) {
        return dealUrl(configValue(name));
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
     * 根据转换Key获取下载文件名
     * @param convertKey  转换Key
     * @return
     */
    public  static String getFileDownloadName(String convertKey) {
        if(logger.isDebugEnabled()) {
            logger.debug("根据转换Key获取下载文件名：{}" , convertKey);
        }

        if("toPDF".equals(convertKey)) {
            return "toPDF-Foxit_pdf";
        }
        else  {
            return  "toOFD-Foxit_ofd";
        }
    }


    /**
     * 转换类型
     * @param type
     * @return
     */
    public static String convertType(String type) {
        return "toOFD".equals(type) ? "ofd" : "pdf";
    }
}
