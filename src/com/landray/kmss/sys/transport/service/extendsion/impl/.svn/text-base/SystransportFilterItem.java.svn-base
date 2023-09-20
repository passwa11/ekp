package com.landray.kmss.sys.transport.service.extendsion.impl;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.transport.service.extendsion.ISysTransportFilter;
import com.landray.kmss.sys.transport.service.extendsion.ISysTransportPlugin;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author lifangmin
 * @Date 2021/1/28 13:48
 * @VERSION 1.0.0
 * @Description 扩展带你SystransportFilter 解析
 */
public class SystransportFilterItem implements ISysTransportPlugin {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String moduleNames;
    private final String serviceName;
    private Map<String, ISysTransportFilter> result;

    public SystransportFilterItem() {
        this.moduleNames = "moduleNames";
        this.serviceName = "serviceName";
        this.result = new HashMap<>();
    }

    @Override
    public void init(IExtension extension) {
        if (null == extension) {
            return;
        }
        String paramModuleNames = Plugin.getParamValueString(extension, this.moduleNames);
        String paramServiceName = Plugin.getParamValueString(extension, this.serviceName);
        if (StringUtils.isBlank(paramModuleNames) || StringUtils.isBlank(paramServiceName)) {
            return;
        }
        String[] moduleNames = paramModuleNames.split("[,;]");
        if (ArrayUtils.isEmpty(moduleNames)) {
            return;
        }
        ISysTransportFilter sysTransportFilter = null;
        try {
            sysTransportFilter = (ISysTransportFilter) SpringBeanUtil.getBean(paramServiceName);
            if (null == sysTransportFilter) {
                return;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        for (String moduleName : moduleNames) {
            this.result.put(moduleName, sysTransportFilter);
        }
    }

    @Override
    public <T> T result() {
        return (T) this.result;
    }
}
