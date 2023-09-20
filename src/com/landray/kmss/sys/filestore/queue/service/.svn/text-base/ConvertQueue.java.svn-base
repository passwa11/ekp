package com.landray.kmss.sys.filestore.queue.service;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

/**
 * 内存转换队列
 */
public interface ConvertQueue {

    /**
     * 将转换消息放入队列
     * @param sysFileConvertQueue
     * @throws Exception
     */
    void put(SysFileConvertQueue sysFileConvertQueue, String convertType) throws Exception;


    /**
     * 从队列中取出一条消息
     * @return
     * @throws Exception
     */
    SysFileConvertQueue take(String convertType) throws Exception;
    /**
     * 获取队列大小
     * @return
     * @throws Exception
     */
    Integer size(String convertType) throws Exception;
}
