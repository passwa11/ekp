package com.landray.kmss.sys.filestore.cache;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;

/**
 * 转换配置缓存 （使用此缓存集群下数据无法同步）
 */
public class ConvertConfigCache extends BaseAppConfig {
    public ConvertConfigCache() throws Exception {
        super();
    }

    static class Singleton {
        private static ConvertConfigCache instance;

        static {
            try {
                instance = new ConvertConfigCache();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ConvertConfigCache getInstance() {
        return Singleton.instance;
    }

    public String getConfigValue(String key) {
        return super.getValue(key);
    }

    public void setConfigValue(String key, String value) {
        super.setValue(key, value);
    }



    public void updateConfigCache() throws Exception {
        getDataMap().clear();
        super.save();
    }

    @Override
    public String getJSPUrl() {
        return null;
    }
}
