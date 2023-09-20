package com.landray.kmss.hr.staff.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.landray.kmss.util.StringUtil;

import java.util.concurrent.TimeUnit;

/**
 * @description: 人事档案处理事件缓存，主要是解决人员变动发送多次事件的处理
 * @author: wangjf
 * @time: 2022/1/18 5:30 下午
 * @version: 1.0
 */

public class HrStaffEventCache {

    private volatile static HrStaffEventCache singletonInstance;
    private Cache<String, String> locationCache;

    /**
     * @param
     * @description: 不允许外部实例化
     * @return:
     * @author: wangjf
     * @time: 2022/1/18 5:36 下午
     */
    private HrStaffEventCache() {
        init();
    }

    private void init() {
        if (locationCache == null) {
            // 缓存在5分钟内有效
            locationCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(5, TimeUnit.MINUTES).build();
        }
    }

    public static HrStaffEventCache getInstance() {
        if (singletonInstance == null) {
            synchronized (HrStaffEventCache.class) {
                if (singletonInstance == null) {
                    singletonInstance = new HrStaffEventCache();
                }
            }
        }
        return singletonInstance;
    }

    /**
     * @param fdId
     * @param type
     * @description: 是否存在
     * @return: boolean
     * @author: wangjf
     * @time: 2022/1/18 5:49 下午
     */
    public synchronized boolean exist(String fdId, String type) {
        String exist = locationCache.getIfPresent(fdId + "_" + type);
        if (StringUtil.isNotNull(exist)) {
            return true;
        } else {
            locationCache.put(fdId + "_" + type, fdId + "_" + type);
            return false;
        }
    }
}