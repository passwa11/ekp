package com.landray.kmss.sys.filestore.cache;

import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.KmssCache;

/**
 * 转换配置信息缓存  使用redis
 */
public class FileConvertConfigCache {
    private static CacheConfig config = CacheConfig.get(FileConvertConfigCache.class);
    private static KmssCache cache = new KmssCache(FileConvertConfigCache.class, config.setCacheType(CacheConfig.TYPE_REDIS));


    static class Singleton {
        private static FileConvertConfigCache instance = new FileConvertConfigCache();
    }

    public static FileConvertConfigCache getInstance() {
        return Singleton.instance;
    }

    public void put(String key, String value) {
        cache.put(key, value);

    }

    public String get(String key) {
        return (String) cache.get(key);
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

}
