package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service.imp;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.api.WPSCloudApiImp;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain.WPSCloudConvertHandlerResult;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain.WPSCloudConvertResult;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service.AbstractWPSCloudConvertHandler;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 转换文件
 */
public class WPSCloudConvertFileHandler extends AbstractWPSCloudConvertHandler {
    private static final Logger logger = LoggerFactory.getLogger(WPSCloudConvertFileHandler.class);

    private static WPSCloudApiImp wpsCloudApiImp = null;
    public WPSCloudApiImp getWpsCloudApiImp() {
        if(wpsCloudApiImp == null) {
            wpsCloudApiImp = (WPSCloudApiImp) SpringBeanUtil.getBean("wpsCloudApiImp");
        }

        return wpsCloudApiImp;
    }
    @Override
    public  WPSCloudConvertHandlerResult doExecute(SysFileConvertQueue convertQueue, WPSCloudConvertHandlerResult wpsCloudConvertHandlerResult) throws Exception {

        WPSCloudConvertResult wpsCloudConvertResult =  getWpsCloudApiImp().convertFileFromWPSCloud(convertQueue,
                wpsCloudConvertHandlerResult.getConvertId(), getWpsConfiUrl());

        if (!wpsCloudConvertResult.getResult()) {
            if(logger.isDebugEnabled()) {
                logger.debug("WPS转换:要求WPS服务器对上传的文件转换失败:{}", wpsCloudConvertResult.getWpsResult());
            }

            return new WPSCloudConvertHandlerResult(false, false, "", "");
        } else {
            if(logger.isDebugEnabled()) {
                logger.debug("WPS转换:要求WPS服务器对上传的文件转换成功:{}", wpsCloudConvertResult.getWpsResult());
            }

            return new WPSCloudConvertHandlerResult(true, true, "", wpsCloudConvertResult.getDownloadId());
        }

    }
}
