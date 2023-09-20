package com.landray.kmss.sys.filestore.convert.cache;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 缓存队列信息用于沉淀使用
 */
public class ConvertQueueCache {
    private static CacheConfig config = CacheConfig.get(ConvertQueueCache.class);
    private static KmssCache cache = new KmssCache(ConvertQueueCache.class, config.setCacheType(CacheConfig.TYPE_REDIS));
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    static class Singleton {
        private static ConvertQueueCache instance = new ConvertQueueCache();
    }

    public static ConvertQueueCache getInstance() {
        return Singleton.instance;
    }

    public void put(String key, SysFileConvertQueue deliveryTaskQueue) {
        try {
            lock.writeLock().lock();
            Long expireTime = System.currentTimeMillis();
            deliveryTaskQueue.setExpireTime(expireTime);
            String object = JSONObject.toJSONString(deliveryTaskQueue);
            cache.put(key, object);
        } finally {
            lock.writeLock().unlock();
        }

    }

    public String get(String key) {
        return (String) cache.get(key);
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public List<String> getKeys() {
        return cache.getCacheKeys();
    }
}
