package com.landray.kmss.sys.transport.util;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.transport.service.extendsion.ISysTransportPlugin;
import com.landray.kmss.sys.transport.service.extendsion.impl.SystransportFilterItem;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @Author lifangmin
 * @Date 2021/1/28 13:59
 * @VERSION 1.0.0
 * @Description 导入导出扩展点工具类
 */
public class PluginUtil {
    private static String extensionPointId = "com.landray.kmss.sys.transport";
    private static boolean isload = false;

    /**
     * 获取指定类型的扩展解析后的信息
     *
     * @param pluginEnum 对应扩展点的item
     * @param <T>        扩展点item 解析后的结果
     * @return 解析后的结果
     */
    public static <T> T get(SysTransPluginEnum pluginEnum) {
        if (!isload) {
            synchronized (PluginUtil.class) {
                if (!isload) {
                    loadItem();
                    isload = true;
                }
            }
        }
        return pluginEnum.getSysTransportPlugin().result();
    }

    private static void loadItem() {
        IExtensionPoint point = Plugin.getExtensionPoint(extensionPointId);
        if (null == point) {
            return;
        }
        IExtension[] allModule = point.getExtensions();
        if (ArrayUtils.isEmpty(allModule)) {
            return;
        }
        String itemName;
        for (IExtension extension : allModule) {
            itemName = extension.getAttribute("name");
            for (SysTransPluginEnum pluginEnum : SysTransPluginEnum.values()) {
                if (pluginEnum.getItem().equals(itemName)) {
                    pluginEnum.getSysTransportPlugin().init(extension);
                    break;
                }
            }
        }
    }

    public enum SysTransPluginEnum {
        TRANSPORT_FILTER("transportFilter", new SystransportFilterItem());
        private String item;
        private ISysTransportPlugin sysTransportPlugin;

        SysTransPluginEnum(String item, ISysTransportPlugin sysTransportPlugin) {
            this.item = item;
            this.sysTransportPlugin = sysTransportPlugin;
        }

        public String getItem() {
            return item;
        }

        public ISysTransportPlugin getSysTransportPlugin() {
            return sysTransportPlugin;
        }
    }
}
