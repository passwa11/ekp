package com.landray.kmss.sys.filestore.scheduler.third.dianju.util;

import com.landray.kmss.sys.appconfig.model.BaseAppconfigCache;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 获取配置属性工具类
 */
public class ConfigUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

    public static String configValue(String name) {
        Map<String, String> dataMap = BaseAppconfigCache.getCacheData("com.landray.kmss.third.dianju.model.ThirdDianjuConfig");
        String config = "";
        if (!dataMap.isEmpty()) {
            config = (String) dataMap.get(name);
        }

        if(logger.isDebugEnabled()) {
            logger.debug("获取配置属性名:{},属性值:{}", name , config);
        }

        return config;
    }

    public static String dealUrl(String url) {
        if(StringUtil.isNull(url)) {
            return url;
        }
        if(url.endsWith("/")) {
            url = url.substring(0, url.lastIndexOf("/"));
        }

        return url;
    }
}
