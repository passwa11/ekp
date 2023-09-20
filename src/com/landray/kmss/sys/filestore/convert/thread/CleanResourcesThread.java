package com.landray.kmss.sys.filestore.convert.thread;

import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.convert.cache.CallbackCache;
import com.landray.kmss.sys.filestore.convert.constant.ThreadTime;
import com.landray.kmss.sys.filestore.convert.domain.ConvertResponseBase;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.state.ConvertQueueInfo;
import com.landray.kmss.sys.filestore.state.ConvertQueueState;
import com.landray.kmss.sys.filestore.state.ConvertedStateFactory;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import java.util.*;

/**
 * 清空--转换成功但是没有回调的缓存，设置为失败
 */
public class CleanResourcesThread<T extends ConvertResponseBase> extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(CleanResourcesThread.class);
    private volatile  Boolean cleanRunning = false;

    static class Singleton {
       private static CleanResourcesThread instance = new CleanResourcesThread();
    }
    public static CleanResourcesThread getInstance() {
        return Singleton.instance;
    }

    private CleanResourcesThread() {
    }
    /**
     * 记录状态组件
     */
    private static ConvertedStateFactory convertedStateFactory = null;
    private ConvertedStateFactory getConvertedStateFactory() {
        if (convertedStateFactory == null) {
            convertedStateFactory = (ConvertedStateFactory) SpringBeanUtil
                    .getBean("convertedStateFactory");
        }

        return convertedStateFactory;
    }

    @Override
    public void run() {
       while(cleanRunning) {
           try {

               List<String> keys = CallbackCache.getInstance().getKeys();
               if(!keys.isEmpty()) {
                   if(logger.isDebugEnabled()) {
                       logger.debug("执行清除转换成功但是没有回调的缓存数据......");
                   }

                   List<SysFileConvertQueue> queues = new ArrayList<>();

                   for(String taskId : keys) {
                       SysFileConvertQueue deliveryTaskQueue = CallbackCache.getInstance().get(taskId);
                       Long expireTime = deliveryTaskQueue.getExpireTime();
                       if(expireTime == null) {
                           continue;
                       }
                       Long currentTime = System.currentTimeMillis();
                       // 进过一小时没有回调，则设置失败
                       if((currentTime - expireTime) > ThreadTime.AN_HOUR) {
                           queues.add(deliveryTaskQueue);
                           // 删除redis中的缓存数据
                           CallbackCache.getInstance().remove(taskId);
                       }
                   }

                   if(logger.isDebugEnabled()) {
                       logger.debug("30分钟查询过去一小时存在转换成功但是没有回调的缓存数量：{}",
                               queues.size());
                   }

                   TransactionStatus status = null;
                   Exception throwException = null;
                   boolean success = false;
                   try {
                       status = TransactionUtils.beginNewTransaction();
                       // 队列设置为失败转换
                       for (SysFileConvertQueue queue : queues) {
                           dealFailure(queue);
                       }

                       TransactionUtils.commit(status);
                       success = true;
                   } catch (Exception e) {
                       success = false;
                       throwException  = e;
                       logger.error("清除转换成功但是没有回调的缓存数据出错", e);
                   } finally {
                       if (throwException != null && status != null) {
                           TransactionUtils.rollback(status);
                       }
                   }
               }

           } catch (Exception e) {
                e.printStackTrace();
           }

           try {
               // 半小时调用一下
               Thread.sleep(ThreadTime.HALF_AN_HOUR);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    /**
     * 队列设置为失败
     * @param deliveryTaskQueue
     * @throws Exception
     */
    public void dealFailure(SysFileConvertQueue deliveryTaskQueue) throws Exception {
        ConvertQueueState convertQueueState = getConvertedStateFactory()
                .getConvertedState(ConvertState.CONVERT_OTHER_FINISH_FAILURE);
        convertQueueState.updateConvertQueue(null, deliveryTaskQueue,
                new ConvertQueueInfo("", "任务已经分配转换，但是没有回调！"));
    }

    public Boolean getCleanRunning() {
        return cleanRunning;
    }

    public void setCleanRunning(Boolean cleanRunning) {
        this.cleanRunning = cleanRunning;
    }
}
