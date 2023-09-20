package com.landray.kmss.sys.filestore.scheduler.third.wps.center.util;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.api.WPSCenterCallBusiness;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * WPS 异步转换回调业务模块接口
 */
public class WPSCenterCallBusinessUtil {

    /**
     * 回调回调接口的方法
     * @param result  结果
     * @param taskId  任务ID
     */
    public static void callBack(Boolean result, String taskId) {
        IExtensionPoint point = Plugin.getExtensionPoint("asyncConverter");
        IExtension[] extensions = point.getExtensions();
        for (IExtension extension : extensions) {
            String serviceName = Plugin.getParamValueString(extension, "service");
            WPSCenterCallBusiness wpsCenterCallBusiness = (WPSCenterCallBusiness) SpringBeanUtil.getBean(serviceName);

            if(result) {
                wpsCenterCallBusiness.successCallback(taskId);
            } else {
                wpsCenterCallBusiness.failureCallback(taskId);
            }
        }
    }
}
