package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain.WPSCloudConvertHandlerResult;

/**
 * 接口
 */
public interface WPSCloudConvertHandler {

    Boolean execute(SysFileConvertQueue convertQueue, WPSCloudConvertHandlerResult wpsCloudConvertHandlerResult) throws Exception;
}
