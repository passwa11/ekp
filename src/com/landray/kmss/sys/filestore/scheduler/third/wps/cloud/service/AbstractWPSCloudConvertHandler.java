package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.wps.WpsUtil;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain.WPSCloudConvertHandlerResult;

/**
 * WPS云转换模板
 */
public abstract class AbstractWPSCloudConvertHandler implements WPSCloudConvertHandler {

    protected  WPSCloudConvertHandler successor;

    /**
     * 执行
     * @param convertQueue
     * @param wPSCloudConvertHandlerResult
     * @return
     * @throws Exception
     */
    @Override
    public Boolean execute(SysFileConvertQueue convertQueue, WPSCloudConvertHandlerResult wPSCloudConvertHandlerResult) throws Exception {
        WPSCloudConvertHandlerResult wpsCloudConvertHandlerResult = doExecute(convertQueue, wPSCloudConvertHandlerResult);
        if (successor != null && wpsCloudConvertHandlerResult.getDoNext()) {
           return successor.execute(convertQueue, wpsCloudConvertHandlerResult);
        } else {
            return wpsCloudConvertHandlerResult.getResult();
        }
    }

    protected  abstract WPSCloudConvertHandlerResult doExecute(SysFileConvertQueue convertQueue, WPSCloudConvertHandlerResult wpsCloudConvertHandlerResult) throws Exception;

    public void setSuccessor(WPSCloudConvertHandler successor) {
        this.successor = successor;
    }

    public WPSCloudConvertHandler getSuccessor() {
        return successor;
    }

    /**
     * 第三文件访问地址
     * @return
     */
    public String getWpsConfiUrl() {
        String url =  WpsUtil.configInfo("thirdWpsSetRedUrl");
        if (url.endsWith("/"))
        {
            url = url.substring(0, url.lastIndexOf("/"));
        }
        return url;
    }
}
