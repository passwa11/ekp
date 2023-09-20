package com.landray.kmss.sys.attachment.plugin.wpsAddons.util;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.plugin.wpsAddons.service.ISysAttachmentWpsAddonsExt;
import org.slf4j.Logger;

import java.util.Map;

public class SysAttachmentWpsAddonsLockUtils {
    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttachmentWpsAddonsLockUtils.class);

    public static boolean handle(Map<String,String> params) throws Exception {
        IExtension[] extensions = Plugin.getExtensions("com.landray.kmss.sys.attachment.wpsAddons.ext", "*", "lock");
        IExtension extension = Plugin.getExtension(extensions, "key", params.get("type"));
        if (extension == null) {
            throw new RuntimeException("加载项扩展点类型[" + params.get("type") + "]不存在");
        }
        ISysAttachmentWpsAddonsExt ext = Plugin.getParamValue(extension, "provider");
        return ext.execute(params);
    }
}
