package com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache;

import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.KmssCache;

import java.util.List;

/**
 * 用于回调后处理转换的状态
 */
public class ConvertCallbackCache {
    private static CacheConfig configConvertCallback = CacheConfig.get(ConvertCallbackCache.class);
    private static KmssCache cacheConvertCallback = new KmssCache(ConvertCallbackCache.class,
            configConvertCallback.setCacheType(CacheConfig.TYPE_REDIS));
    static class Singleton {
        private static ConvertCallbackCache instance = new ConvertCallbackCache();
    }

    public static ConvertCallbackCache getInstance() {
        return Singleton.instance;
    }

    public void put(String key, String value) {
        cacheConvertCallback.put(key, value);
    }

    public String get(String key) {
        return (String) cacheConvertCallback.get(key);
    }

    public void remove(String key) {
        cacheConvertCallback.remove(key);
    }

    public List<String> getKeys() {
        return cacheConvertCallback.getCacheKeys();
    }


}
