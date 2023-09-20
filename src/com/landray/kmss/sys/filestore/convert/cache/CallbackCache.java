package com.landray.kmss.sys.filestore.convert.cache;


import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 基于redis封装回调使用的缓存
 */
public class CallbackCache {
    private static final Logger logger = LoggerFactory.getLogger(CallbackCache.class);
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private static CacheConfig config = CacheConfig.get(SysFileConvertQueue.class);
    private static KmssCache cache = new KmssCache(SysFileConvertQueue.class,
            config.setCacheType(CacheConfig.TYPE_REDIS));

    static class Singleton {
        private static CallbackCache instance = new CallbackCache();
    }

    public static CallbackCache getInstance() {
        return Singleton.instance;
    }

    /**
     * 入值
     * @param taskId
     */
    public void put(String taskId, SysFileConvertQueue sysFileConvertQueue) {
        try {
            lock.writeLock().lock();
            sysFileConvertQueue.setExpireTime(System.currentTimeMillis());
            String json = JSONObject.toJSONString(sysFileConvertQueue);
            cache.put(taskId, json);
        } finally {
            lock.writeLock().unlock();
        }

    }

    /**
     * 取值
     * @param taskId
     * @return
     */
    public SysFileConvertQueue get(String taskId) {
        try {
            lock.readLock().lock();
            String json = (String) cache.get(taskId);
            SysFileConvertQueue object = JSONObject.parseObject(json, SysFileConvertQueue.class);
            return object;
        } finally {
            lock.readLock().unlock();
        }

    }

    public List<String> getKeys() {
        try {
            lock.readLock().lock();
            return cache.getCacheKeys();
        } finally {
            lock.readLock().unlock();
        }

    }

    public void remove(String taskId) {
        try{
            lock.readLock().lock();
            cache.remove(taskId);
        } finally {
            lock.readLock().unlock(); }

    }
}
