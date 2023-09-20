package com.landray.kmss.sys.attachment.service.spring;

import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.util.SysAttViewerUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.UserUtil;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * 福昕阅读需要参数构造器
 */
public class SysAttMainFoxitBuilder {
    private static final Logger logger = LoggerFactory.getLogger(SysAttMainFoxitBuilder.class);

    /**
     * 福昕的license信息
     *
     * @param request
     * @return
     */
    public SysAttMainFoxitBuilder creatFoxitAuthorization(HttpServletRequest request) {
        request.setAttribute("licenseSN", ResourceUtil.getKmssConfigString("sys.foxit.license.sn"));
        request.setAttribute("licenseKey", ResourceUtil.getKmssConfigString("sys.foxit.license.key"));
        return this;
    }

    /**
     * 附件是否可以打印
     * @param request
     * @param sysAttMain
     * @return
     */
    public SysAttMainFoxitBuilder createSysAuthorization(HttpServletRequest request, SysAttMain sysAttMain) {
        // 是否可打印
        boolean canPrint = UserUtil.checkAuthentication(
                "/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=" + sysAttMain.getFdId(), "GET");
        request.setAttribute("canPrint", canPrint);
        // 是否可下载
        boolean download = UserUtil.checkAuthentication(
                "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=" + sysAttMain.getFdId(), "GET");
        request.setAttribute("download", download);

        return this;
    }

    /**
     * 文件的相关信息
     *
     * @param request
     * @param sysAttMain
     * @return
     */
    public SysAttMainFoxitBuilder createSysFileInfo(HttpServletRequest request, SysAttMain sysAttMain,
                                                    String sign,  long expires) throws Exception{
        String urlPrefix = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
        if(urlPrefix.endsWith("/")) {
            urlPrefix = urlPrefix.substring(0, urlPrefix.lastIndexOf("/"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("系统地址:" + urlPrefix);
        }
        String downLoadUrl = urlPrefix + "/sys/attachment/sys_att_main/downloadFile.jsp?fdId=" + sysAttMain.getFdId() + "&Expires="
                + expires + "&Signature=" + sign + "&reqType=rest&filename=" + URLEncoder.encode(sysAttMain.getFdFileName(), "utf-8");

        if (logger.isDebugEnabled()) {
            logger.debug("下载地址:" + downLoadUrl);
        }

        request.setAttribute("downLoadUrl", downLoadUrl);
        request.setAttribute("filename", sysAttMain.getFdFileName());
        request.setAttribute("fdId", sysAttMain.getFdId());
        request.setAttribute("fileSize", SysAttViewerUtil.convertFileSize(sysAttMain.getFdSize()));

        return this;
    }



    /**
     * 水印信息
     * @return
     */
    public SysAttMainFoxitBuilder createWaterInfo(HttpServletRequest request){
        net.sf.json.JSONObject watermarkCfg = SysAttViewerUtil
                .getWaterMarkConfigInDB(true);
        /**
         * 是否显示文字水印
         */
        Boolean showWordWater = watermarkCfg.get("markWordVar") != null
                    && "true".equals(watermarkCfg.get("showWaterMark"))
                    && "word".equals(watermarkCfg.get("markType"));
        /**
         * 是否显示图片水印
         */
        Boolean showPictureWater = watermarkCfg.get("markWordVar") != null
                && "true".equals(watermarkCfg.get("showWaterMark"))
                && "pic".equals(watermarkCfg.get("markType"));

        SysAttMainWaterInfoBuilder sysAttMainWaterInfoBuilder = new SysAttMainWaterInfoBuilder();
        JSONObject waterMarkInfo =  sysAttMainWaterInfoBuilder.addBaseData(watermarkCfg)
                .addWordWaterData(request, watermarkCfg, showWordWater)
                .addPictureWaterData(request, watermarkCfg, showPictureWater)
                .build();

        request.setAttribute("markWordVar", waterMarkInfo.toString().replaceAll("\"", "'"));

        return this;
    }

    /**
     * 其它信息
     * @return
     */
    public SysAttMainFoxitBuilder createOthersInfo(HttpServletRequest request) {
        request.setAttribute("isMobile", isMobileRequest(request));

        return this;
    }

    /**
     * 判断终端请求类型
     *
     * @param request
     * @return 移动：true PC:false
     */
    public boolean isMobileRequest(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        if (userAgent.indexOf("Android") != -1) {
            return true;
        } else if (userAgent.indexOf("iPhone") != -1 || userAgent.indexOf("iPad") != -1) {
            return true;
        } else { // PC
            return false;
        }
    }
}
