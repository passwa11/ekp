package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service.imp;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.api.WPSCloudApiImp;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain.WPSCloudConvertHandlerResult;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain.WPSCloudUpLoadResult;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service.AbstractWPSCloudConvertHandler;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 上传文件
 */
public class WPSCloudConvertUpLoadFileHandler extends AbstractWPSCloudConvertHandler {
    private static final Logger logger = LoggerFactory.getLogger(WPSCloudConvertUpLoadFileHandler.class);

    private static WPSCloudApiImp wpsCloudApiImp = null;
    public WPSCloudApiImp getWpsCloudApiImp() {
        if(wpsCloudApiImp == null) {
            wpsCloudApiImp = (WPSCloudApiImp) SpringBeanUtil.getBean("wpsCloudApiImp");
        }

        return wpsCloudApiImp;
    }
    @Override
    public WPSCloudConvertHandlerResult doExecute(SysFileConvertQueue convertQueue, WPSCloudConvertHandlerResult wpsCloudConvertHandlerResult) throws Exception {

        String selfUrl = convertQueue.getFdFileDownUrl(); //EKP下载地址
        String url = getWpsConfiUrl();

        if(StringUtil.isNull(selfUrl)) {
            return new WPSCloudConvertHandlerResult(false, false, "", "");
        }

        WPSCloudUpLoadResult wpsCloudUpLoadResult = getWpsCloudApiImp().
                uploadFileToWPSCloud(convertQueue, url, selfUrl);

        if(!wpsCloudUpLoadResult.getResult()) {
            if (logger.isDebugEnabled()) {
                logger.error("WPS转换:上传文件到WPS服务器失败.队列ID:{}; attMainId{};失败原因:{}",
                        convertQueue.getFdId(), convertQueue.getFdAttMainId(), wpsCloudUpLoadResult.getWpsResult());
            }

            return new WPSCloudConvertHandlerResult(false, false, "", "");

        } else {
            if (logger.isDebugEnabled()) {
                logger.error("WPS转换:上传文件到WPS服务器成功.队列ID:{}; attMainId{};失败原因:{}",
                        convertQueue.getFdId(), convertQueue.getFdAttMainId(), wpsCloudUpLoadResult.getWpsResult());
            }

            return new WPSCloudConvertHandlerResult(true, true,
                    wpsCloudUpLoadResult.getConvertId(), "");
        }


    }
}
