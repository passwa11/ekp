package com.landray.kmss.sys.filestore.queue.util;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueueCallbackExtension;
import com.landray.kmss.util.SpringBeanUtil;

public class ConvertQueueCallbackExtensionUtil {
    public static void execute(SysFileConvertQueue queue){
        IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.sys.filestore.convert.queueCallback");
        IExtension[] extensions = point.getExtensions();
        for (IExtension extension : extensions) {
            String serviceName = Plugin.getParamValueString(extension, "service");
            ConvertQueueCallbackExtension service = (ConvertQueueCallbackExtension) SpringBeanUtil.getBean(serviceName);
            service.handle(queue);
        }
    }
}
