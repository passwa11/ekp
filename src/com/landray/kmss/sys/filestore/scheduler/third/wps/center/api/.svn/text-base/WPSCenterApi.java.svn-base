package com.landray.kmss.sys.filestore.scheduler.third.wps.center.api;

import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

/**
 * 请求第三方接口
 */
public interface WPSCenterApi {

    /**
     * 文件转换
     * @param taskId
     * @param filePath
     * @param deliveryTaskQueue
     * @return
     * @throws Exception
     */
    String doConvertFile(String taskId, String filePath, SysFileConvertQueue deliveryTaskQueue) throws Exception ;

    /**
     * 同步转换接口
     * @return
     * @throws Exception
     */
    Boolean syncConvertFile(SysAttMain sysAttMain, String json) throws Exception;

    /**
     * 异步转换接口
     * @return
     * @throws Exception
     */
    String asyncConvertFile(SysAttMain sysAttMain, String json, WPSCenterCallBusiness callBusiness) throws Exception;
}
