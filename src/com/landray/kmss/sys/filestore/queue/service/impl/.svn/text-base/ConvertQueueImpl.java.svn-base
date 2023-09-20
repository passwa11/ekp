package com.landray.kmss.sys.filestore.queue.service.impl;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueue;
import com.landray.kmss.sys.filestore.queue.constant.ConvertQueueConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 转换队列方法实现
 */
public class ConvertQueueImpl implements ConvertQueue {

    private static final Logger logger = LoggerFactory.getLogger(ConvertQueueImpl.class);
    private ConcurrentHashMap<String, ArrayBlockingQueue<SysFileConvertQueue>>
                               mapQueue = new ConcurrentHashMap<>();
    /**
     * 队列唯一性
     */

    private ConcurrentHashMap<String, CopyOnWriteArrayList<String>>  uniqueQueue = new ConcurrentHashMap<>();
    /**
     * 将转换消息放入队列
     * @param sysFileConvertQueue
     * @throws Exception
     */
    @Override
    public void put(SysFileConvertQueue sysFileConvertQueue, String convertType) throws Exception {

        if (getUniqueQueue(convertType).contains(sysFileConvertQueue.getFdId())) {
            logger.warn("转换信息已经存在于内存队列中，无须重复添加。");
            return;
        }

        ArrayBlockingQueue<SysFileConvertQueue> queue = getQueue(convertType);
        Integer queueSize = queue.size();


        // 队列是否已经满了
        if(queueSize >= ConvertQueueConstant.QUEUE_MAX_SIZE) {
            logger.warn("转换内存队列已满,开启不添加策略,当前队列数量:{}",queueSize);
            return;
        }

        queue.put(sysFileConvertQueue);
        getUniqueQueue(convertType).add(sysFileConvertQueue.getFdId());

    }


    /**
     * 从队列中取出一条消息
     * @return
     * @throws Exception
     */
    @Override
    public SysFileConvertQueue take(String convertType) throws Exception {
        SysFileConvertQueue sysFileConvertQueue = getQueue(convertType).take();
        getUniqueQueue(convertType).remove(sysFileConvertQueue.getFdId());
        return sysFileConvertQueue;
    }
    /**
     * 获取队列大小
     * @return
     * @throws Exception
     */
    @Override
    public Integer size(String convertType) throws Exception {
        return getQueue(convertType).size();
    }

    /**
     * 获取队列
     * @param convertType
     * @return
     */
    public ArrayBlockingQueue<SysFileConvertQueue> getQueue(String convertType) {
        ArrayBlockingQueue<SysFileConvertQueue> queue = mapQueue.get(convertType);
        if(queue == null) {
            logger.warn("获取【{}】的内存中队列为空，创建内存队列.", convertType);
            queue = new ArrayBlockingQueue<SysFileConvertQueue>(ConvertQueueConstant.QUEUE_MAX_SIZE);
            mapQueue.put(convertType, queue);
        }

        return queue;
    }

    /**
     * 唯一队列
     * @param convertType
     * @return
     */
    public  CopyOnWriteArrayList<String> getUniqueQueue(String convertType) {
        CopyOnWriteArrayList<String> uniqueIds = uniqueQueue.get(convertType);
        if(uniqueIds == null) {
            logger.warn("获取【{}】的唯一队列记录对象为空，开启创建新对象", convertType);
            uniqueIds = new CopyOnWriteArrayList();
            uniqueQueue.put(convertType, uniqueIds);
        }

        return uniqueIds;
    }
}
