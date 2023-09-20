package com.landray.kmss.sys.restservice.client.util;

import com.landray.kmss.sys.config.action.SysConfigAdminUtil;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author 苏运彬
 * @date 2021/7/1
 */
public class ConfigUtil {
    /**
     * 读取配置文件到PropertiesConfiguration
     * @param fileName
     * @return
     * @throws Exception
     */
    public static PropertiesConfiguration getConfig(String fileName) throws Exception{
        Properties properties = getProperties(fileName);
        PropertiesConfiguration p = SysConfigAdminUtil.convertProperties(properties);
        return p;
    }

    /**
     * 读取配置文件到Properties对象
     * @param fileName
     * @return
     * @throws Exception
     */
    public static Properties getProperties(String fileName) throws Exception{
        Properties properties = new Properties();
        PropertiesConfiguration p = null;
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(
                    ConfigLocationsUtil.getWebContentPath()
                            + "/WEB-INF/KmssConfig/" + fileName+".properties");
            SysConfigAdminUtil.loadProperties(properties, fis, null);
        }finally{
            IOUtils.closeQuietly(fis);
        }

        return properties;
    }

    /**
     * 获取kmssConfig的配置
     * @param key
     * @return
     */
    public static String getKmssConfigString(String key){
        return getKmssConfigString(key, null);
    }

    /**
     * 从kmssconfig配置文件中取key对应的值，如果对应值为空，则返回默认值
     */
    public static String getKmssConfigString(String key, String defaultVal){
        String val = ResourceUtil.getKmssConfigString(key);
        return StringUtil.isNotNull(val) ? val : defaultVal;
    }
}
