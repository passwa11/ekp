package com.landray.kmss.sys.filestore.location.util;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileResourceBuzPathProvider;
import com.landray.kmss.sys.filestore.location.service.AbstractSysFileResourceProxyService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SysFileResourceUtil {
    private static List<ResourceExtension> resourceExtensions = new ArrayList<>();

    private static List<BuzPathExtension> buzPathExtensions = new ArrayList<>();

    public static void write(String buzKey, String buzRelativePath, InputStream in) throws Exception {
        getProxyService().write(getPath(buzKey, buzRelativePath), in);
    }

    public static void write(String buzKey, String buzRelativePath, InputStream in, boolean isRelative) throws Exception {
        getProxyService().write(getPath(buzKey, buzRelativePath), isRelative, in);
    }

    public static InputStream read(String buzKey, String buzRelativePath) throws Exception {
        return getProxyService().read(getPath(buzKey, buzRelativePath));
    }

    public static InputStream read(String buzKey, String buzRelativePath, boolean isRelative) throws Exception {
        return getProxyService().read(getPath(buzKey, buzRelativePath), isRelative);
    }

    public static void delete(String buzKey, String buzRelativePath) throws Exception {
        getProxyService().delete(getPath(buzKey, buzRelativePath));
    }

    public static void delete(String buzKey, String buzRelativePath, boolean isRelative) throws Exception {
        getProxyService().delete(getPath(buzKey, buzRelativePath),isRelative);
    }

    private static List<ResourceExtension> getResourceExtensions() {
        if (resourceExtensions.isEmpty()) {
            synchronized (resourceExtensions) {
                if (resourceExtensions.isEmpty()) {
                    IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.sys.filestore.resource");
                    IExtension[] extensions = point.getExtensions();
                    for (IExtension extension : extensions) {
                        if ("config".equals(extension.getAttribute("name"))) {
                            String key = Plugin.getParamValueString(extension, "key");
                            String name = Plugin.getParamValueString(extension, "name");
                            String serviceName = Plugin.getParamValueString(extension, "service");
                            AbstractSysFileResourceProxyService service = (AbstractSysFileResourceProxyService) SpringBeanUtil.getBean(serviceName);
                            resourceExtensions.add(new ResourceExtension(key, name, service));
                        }
                    }
                }
            }
        }
        return resourceExtensions;
    }

    private static List<BuzPathExtension> getBuzPathExtensions() {
        if (buzPathExtensions.isEmpty()) {
            synchronized (buzPathExtensions) {
                if (buzPathExtensions.isEmpty()) {
                    IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.sys.filestore.resource.buzPath");
                    IExtension[] extensions = point.getExtensions();
                    for (IExtension extension : extensions) {
                        if ("config".equals(extension.getAttribute("name"))) {
                            String key = Plugin.getParamValueString(extension, "key");
                            String path = Plugin.getParamValueString(extension, "path");
                            String providerName = Plugin.getParamValueString(extension, "provider");
                            ISysFileResourceBuzPathProvider provider = (ISysFileResourceBuzPathProvider) SpringBeanUtil.getBean(providerName);
                            buzPathExtensions.add(new BuzPathExtension(key, path, provider));
                        }
                    }
                }
            }
        }
        return buzPathExtensions;
    }

    private static AbstractSysFileResourceProxyService getProxyService() {
        String key = ResourceUtil.getKmssConfigString("sys.other.location");
        if (StringUtils.isEmpty(key)) {
            key = SysAttBase.ATTACHMENT_LOCATION_SERVER;
        }
        List<ResourceExtension> extensions = getResourceExtensions();
        for (ResourceExtension extension : extensions) {
            if (StringUtils.equals(extension.getKey(), key)) {
                return extension.getService();
            }
        }
        return null;
    }

    private static String getPath(String key,String relativePath) {
        List<BuzPathExtension> extensions = getBuzPathExtensions();
        for (BuzPathExtension extension : extensions) {
            if (StringUtils.equals(extension.getKey(), key)) {
                return extension.getProvider().getBuzPath(extension.getPath(), relativePath);
            }
        }
        return null;
    }

    static class ResourceExtension{
        private String key;
        private String name;
        private AbstractSysFileResourceProxyService service;

        ResourceExtension(String key, String name, AbstractSysFileResourceProxyService service) {
            this.key = key;
            this.name = name;
            this.service = service;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public AbstractSysFileResourceProxyService getService() {
            return service;
        }
    }

    static class BuzPathExtension{
        private String key;
        private String path;
        private ISysFileResourceBuzPathProvider provider;

        BuzPathExtension(String key, String path, ISysFileResourceBuzPathProvider provider) {
            this.key = key;
            this.path = path;
            this.provider = provider;
        }

        public String getKey() {
            return key;
        }

        public String getPath() {
            return path;
        }

        public ISysFileResourceBuzPathProvider getProvider() {
            return provider;
        }
    }
}
