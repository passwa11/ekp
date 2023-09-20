package com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache;

import com.landray.kmss.sys.filestore.scheduler.third.wps.center.domain.ConvertGoing;


import java.util.HashMap;
import java.util.Map;

/**
 * 转换过程信息缓存
 */
public class ConvertGoingCache {

    private static final Map<String, ConvertGoing> convertGoingCache = new HashMap<>();
    private ConvertGoingCache(){

    }

    /**
     * 添加缓存
     * @param taskId
     * @param convertGoing
     */
    public static void addCache(String taskId, ConvertGoing convertGoing) {
        convertGoingCache.put(taskId, convertGoing);
    }

    public static ConvertGoing getCache(String taskId) {
        return convertGoingCache.get(taskId);
    }
    /**
     * 清楚缓存的对应KEY
     * @param taskId
     */
    public static void removeCache(String taskId) {
        convertGoingCache.remove(taskId);
    }

    /**
     * 清空缓存
     */
    public static void cleanCache() {
        convertGoingCache.clear();
    }

    public static Integer getCacheSize() {
        return convertGoingCache.size();
    }
}
