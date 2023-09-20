package com.landray.kmss.sys.filestore.scheduler.third.foxit.service;

/**
 * 转换调度
 */
public interface FoxitConvertFile {
    /**
     * 转换处理
     * @param serviceName
     */
    void doDistributeConvertQueue(String serviceName);
}
