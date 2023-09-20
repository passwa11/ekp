package com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache;

import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.domain.ConvertGoing;

/**
 * 基于redis实现缓存
 */
public class ConvertRedisCache {
    private static CacheConfig configConvertGoing = CacheConfig.get(ConvertGoing.class);
    private static KmssCache cacheConvertGoing = new KmssCache(ConvertGoing.class,configConvertGoing.setCacheType(CacheConfig.TYPE_REDIS));

    static class Singleton {
        private static ConvertRedisCache instance = new ConvertRedisCache();
    }

    public static ConvertRedisCache getInstance() {
        return Singleton.instance;
    }

    public void put(String key, String value) {
        cacheConvertGoing.put(key, value);
    }

    public String get(String key) {
        return (String) cacheConvertGoing.get(key);
    }

    public void remove(String key) {
        cacheConvertGoing.remove(key);
    }

    public Boolean exist(String key) {
        return cacheConvertGoing.get(key) == null ? false : true;
    }
}
