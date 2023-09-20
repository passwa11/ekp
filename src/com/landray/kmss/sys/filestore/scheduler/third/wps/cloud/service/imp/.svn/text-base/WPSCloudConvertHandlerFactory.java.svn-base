package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service.imp;

import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service.WPSCloudConvertHandler;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 转换工厂
 */
public class WPSCloudConvertHandlerFactory {
    /**
     * 是否构建好了handler链条
     */
    private Boolean buildedHandlerChain = false;

    /**
     *  上传文件到WPS组件
     */
    private static WPSCloudConvertUpLoadFileHandler wpsCloudConvertUpLoadFileHandler = null;
    public  WPSCloudConvertUpLoadFileHandler getWpsCloudConvertUpLoadFileHandler() {
        if (wpsCloudConvertUpLoadFileHandler == null) {
            wpsCloudConvertUpLoadFileHandler = (WPSCloudConvertUpLoadFileHandler) SpringBeanUtil.getBean("wpsCloudConvertUpLoadFileHandler");
        }
        return wpsCloudConvertUpLoadFileHandler;
    }

    /**
     * 转换文件组件
     */
    private static WPSCloudConvertFileHandler wpsCloudConvertFileHandler = null;
    public WPSCloudConvertFileHandler getWpsCloudConvertFileHandler() {
        if(wpsCloudConvertFileHandler == null) {
            wpsCloudConvertFileHandler = (WPSCloudConvertFileHandler) SpringBeanUtil.getBean("wpsCloudConvertFileHandler");
        }

        return wpsCloudConvertFileHandler;
    }

    /**
     * 下载文件组件
     */
    private static WPSCloudDownloadConvertedFileHandler wpsCloudDownloadConvertedFileHandler;
    public WPSCloudDownloadConvertedFileHandler getWpsCloudDownloadConvertedFileHandler() {
        if(wpsCloudDownloadConvertedFileHandler == null ) {
            wpsCloudDownloadConvertedFileHandler = (WPSCloudDownloadConvertedFileHandler) SpringBeanUtil.getBean("wpsCloudDownloadConvertedFileHandler");
        }

        return wpsCloudDownloadConvertedFileHandler;
    }

    /**
     * 构造链条
     * @return
     */
    public WPSCloudConvertHandler getHandlerChain() {
        if(!buildedHandlerChain) {
            buildHandlerChain();
        }
        return getWpsCloudConvertUpLoadFileHandler();
    }

    public void buildHandlerChain() {
        getWpsCloudConvertUpLoadFileHandler().setSuccessor(getWpsCloudConvertFileHandler());
        getWpsCloudConvertFileHandler().setSuccessor(getWpsCloudDownloadConvertedFileHandler());
    }
}
