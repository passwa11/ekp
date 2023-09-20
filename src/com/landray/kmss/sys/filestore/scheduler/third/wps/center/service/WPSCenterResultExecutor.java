package com.landray.kmss.sys.filestore.scheduler.third.wps.center.service;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;

public interface WPSCenterResultExecutor {
    Boolean doResult(ISysFileConvertDataService dataService,
                     SysFileConvertQueue deliveryTaskQueue, String taskId) throws Exception;
}
