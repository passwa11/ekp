package com.landray.kmss.sys.filestore.scheduler.third.dianju.util;

import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.service.IDianjuUtilService;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerMainService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SignUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 请求第三文系统应用工具类
 */
public class FireThirdApplicatonUtil {

    private static final Logger logger = LoggerFactory.getLogger(FireThirdApplicatonUtil.class);

    private static ISysAttMainCoreInnerService getSysAttMainCoreInnerServiceImp() {
        return (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
    }

    private static IDianjuUtilService dianjuUtilService;
    private static IDianjuUtilService getDianjuUtilService(){
        if(dianjuUtilService == null){
            dianjuUtilService = (IDianjuUtilService) SpringBeanUtil.getBean("thirdDianjuUtilService");
        }
        return dianjuUtilService;
    }
    /**
     * 根据转换Key获取下载文件名
     * @param convertKey  转换Key
     * @return
     */
    public static String getFileDownloadName(String convertKey) {
        if(logger.isDebugEnabled()) {
            logger.debug("根据转换Key获取下载文件名：{}" , convertKey);
        }

        if("toPDF".equals(convertKey)) {
            return "toPDF-Dianju_pdf";
        }
        else  {
            return  "toOFD-Dianju_ofd";
        }
    }


    /**
     * 获取下载文件夹
     * @param basePath 基础路径
     * @param filePath 文件夹路径
     * @return
     */
    public static String getDownloadDocumnet(String basePath, String filePath) {
        if(logger.isDebugEnabled()) {
            logger.debug("获取下载文件夹路径:basePath:{}**filePath:{}", basePath , filePath);
        }

        if (basePath.endsWith("/")) {
            basePath = basePath.substring(0, basePath.lastIndexOf("/"));
        }

        String fdPath = basePath + filePath + "_convert";

        if(logger.isDebugEnabled()) {
            logger.debug("文件下载到文件夹路径fdPath：{}", fdPath);
        }
        return fdPath;
    }

    /**
     * 第三方到本系统下载附件的地址
     * @return
     * @throws Exception
     */
    public static String getFilePath(String fileName, String attmainId) throws Exception{
        return getDianjuUtilService().getFilePath(attmainId);
    }


    /**
     * 为下载链接签名
     *
     * @param expires
     * @param attMainId
     * @return
     * @throws Exception
     */
    private static String getRestSign(String attMainId, long expires) throws Exception {
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
    public static String getSystemUrl(){
        String localUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix"); //系统地址的前部分
        if(localUrl.endsWith("/"))
        {
            localUrl = localUrl.substring(0, localUrl.lastIndexOf("/"));
        }
        return localUrl;
    }
}
