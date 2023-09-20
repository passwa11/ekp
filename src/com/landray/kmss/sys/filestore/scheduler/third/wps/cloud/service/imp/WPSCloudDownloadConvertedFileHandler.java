package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service.imp;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.api.WPSCloudApiImp;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain.WPSCloudConvertHandlerResult;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service.AbstractWPSCloudConvertHandler;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 下载文件
 */
public class WPSCloudDownloadConvertedFileHandler extends AbstractWPSCloudConvertHandler {
    private static final Logger logger = LoggerFactory.getLogger(WPSCloudDownloadConvertedFileHandler.class);
    private static WPSCloudApiImp wpsCloudApiImp = null;
    public WPSCloudApiImp getWpsCloudApiImp() {
        if(wpsCloudApiImp == null) {
            wpsCloudApiImp = (WPSCloudApiImp) SpringBeanUtil.getBean("wpsCloudApiImp");
        }

        return wpsCloudApiImp;
    }
    @Override
    public WPSCloudConvertHandlerResult doExecute(SysFileConvertQueue convertQueue, WPSCloudConvertHandlerResult wpsCloudConvertHandlerResult) throws Exception {

        Boolean isSuccessed = getWpsCloudApiImp().downloadConvertedFileFromWPSCloud(convertQueue,
                wpsCloudConvertHandlerResult.getDownloadId(),getWpsConfiUrl());
        if(isSuccessed) {
            return new WPSCloudConvertHandlerResult(true, false, "",
                    wpsCloudConvertHandlerResult.getDownloadId());
        } else {
            return new WPSCloudConvertHandlerResult(false, false, "",
                    wpsCloudConvertHandlerResult.getDownloadId());
        }
    }

}
