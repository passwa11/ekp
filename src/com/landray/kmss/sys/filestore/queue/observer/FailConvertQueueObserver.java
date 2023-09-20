package com.landray.kmss.sys.filestore.queue.observer;


import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.dto.QueryParameter;
import com.landray.kmss.sys.filestore.queue.dto.RequestParameter;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueue;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueueService;
import com.landray.kmss.sys.filestore.scheduler.third.ConvertServerMediator;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import static com.landray.kmss.sys.filestore.queue.constant.ConvertQueueConstant.*;

/**
 * 查询数据库失败队列或执行失败队列的观察者
 */
public class FailConvertQueueObserver implements Observer {
    private static final Logger logger = LoggerFactory.getLogger(FailConvertQueueObserver.class);

    /**
     * 队列管理组件
     */
    private static ConvertQueue failConvertQueue = null;
    private static ConvertQueue getFailConvertQueue() {
        if(failConvertQueue == null) {
            failConvertQueue = (ConvertQueue) SpringBeanUtil.getBean("failConvertQueueImpl");
        }

        return failConvertQueue;
    }
    /**
     * 转换队列信息
     */
    private static ConvertQueue convertQueue = null;
    private static ConvertQueue getConvertQueue() {
        if(convertQueue == null) {
            convertQueue = (ConvertQueue) SpringBeanUtil.getBean("convertQueueImpl");
        }

        return convertQueue;
    }

    /**
     * 队列管理组件
     */
    private static ConvertServerMediator convertServerMediator = null;
    private static ConvertServerMediator getConvertServerMediator() {
        if(convertServerMediator == null) {
            convertServerMediator = (ConvertServerMediator) SpringBeanUtil.getBean("convertServerMediator");
        }

        return convertServerMediator;
    }

    private static ConvertQueueService convertQueueService = null;
    private static ConvertQueueService getConvertQueueService() {
        if(convertQueueService == null) {
            convertQueueService = (ConvertQueueService) SpringBeanUtil.getBean("convertQueueServiceImpl");
        }

        return convertQueueService;
    }
    @Override
    public void update(Observable o, Object arg) {

        try {
            RequestParameter requestParameter = (RequestParameter) arg;
            String[] convertKeys = requestParameter.getConvertKeys();
            String convertType = requestParameter.getConvertType();
            if(logger.isDebugEnabled()) {
                logger.debug("接收到通知查询数据库中转换失败的队列或执行转换失败的队列，信息：{}",
                        requestParameter.toString());
            }

            ConvertQueue failureQueue = getFailConvertQueue();
            Integer queueSize = failureQueue.size(convertType);

            if(logger.isDebugEnabled()) {
                logger.debug("(未查询数据库前)转换失败队列中存在的数量是：{}", queueSize);
            }

            ConvertQueue queue = getConvertQueue();
            // 如果转换队列 或 异常队列有数据，则无须查询数据库
            if(queue.size(convertType) > 0 || queueSize > 0) {
                return;
            }

            // 请求和响应队列还有数据在进行，不需要查询数据库
            // （防止数据状态未更新又重复查询转换）
//            if(!RequestHandleThread.getInstance().isEmpty() ||
//                    !RequestHandleThread.getInstance().isEmpty()) {
//                return;
//            }

            // 异常转换队列为空，则查询数据库，每次50条
            // 从数据库中查询异常转换队列, 存在转换失败队列，则加入ConvertQueue中
            if (hasNextData(convertKeys, convertType,
                    SysFileConvertConstant.FAILURE, QUERY_CONVERT_NUMBER)) {
                addQueue(convertKeys, convertType,
                        SysFileConvertConstant.FAILURE, QUERY_CONVERT_NUMBER);
            }


            // 从数据库中查询已经分配任务超过10分钟都没有转换的，变为失败队列继续转换
            if (hasNextData(convertKeys, convertType,
                    SysFileConvertConstant.ASSIGNED, -99)) {
                addQueue(convertKeys, convertType,
                        SysFileConvertConstant.ASSIGNED, -99);
            }

            if(logger.isDebugEnabled()) {
                logger.debug("(查询数据库后)转换失败队列中存在的数量是：{}",
                        getFailConvertQueue().size(convertType));
            }

        } catch (Exception e) {
          logger.error("执行内存异常转换队列出问题：{}", e);
        } finally {

        }

    }

    /**
     * 是否的数据
     * @param convertKeys
     * @param convertType
     * @param status
     * @param convertNum
     * @return
     * @throws Exception
     */
    public Boolean hasNextData(String[] convertKeys, String convertType,
                               int status, int convertNum) throws Exception {
        QueryParameter queryParameter = new QueryParameter();
        queryParameter.setConverterKeys(convertKeys);
        queryParameter.setConvertType(convertType);
        queryParameter.setStatus(status);
        queryParameter.setPageNo(QUERY_PAGE_NUMBER);
        queryParameter.setRowSize(QUERY_ROW_NUMBER_ONE);
        queryParameter.setConvertNumber(convertNum);
        Boolean hasNext = getConvertQueueService().getCount(queryParameter).equals(0) ? false : true;

        if(logger.isDebugEnabled()) {
            logger.debug("从数据库中查询异常转换队列，是否有数据：{}", hasNext);
        }

        return hasNext;
    }

    /**
     * 查询失败队列，每次查询50条
     * @param convertKeys
     * @param convertType
     * @throws Exception
     */
    public void addQueue(String[] convertKeys, String convertType,
                         int status, int convertNum) throws Exception {
        if(logger.isDebugEnabled()) {
            logger.debug("查询失败队列");
        }
        QueryParameter queryParameter = new QueryParameter();
        queryParameter.setConverterKeys(convertKeys);
        queryParameter.setConvertType(convertType);
        queryParameter.setStatus(status);
        queryParameter.setPageNo(QUERY_PAGE_NUMBER);
        queryParameter.setRowSize(QUERY_ROW_NUMBER);
        queryParameter.setConvertNumber(convertNum);
        List<SysFileConvertQueue> sysFileConvertQueues =getConvertQueueService().getUnassignedTasks(queryParameter);


        if(status == SysFileConvertConstant.ASSIGNED) {  // 已分配的
            for (SysFileConvertQueue sysFileConvertQueue : sysFileConvertQueues) {
                Date date = sysFileConvertQueue.getFdCreateTime();
                long dateTime = date.getTime();
                long current = new Date().getTime();
                // 入队超过10分钟的
                if((current - dateTime) > 10 * 60 * 1000) {
                    getFailConvertQueue().put(sysFileConvertQueue, convertType);
                }

            }
        } else {  // 失败的
            for (SysFileConvertQueue sysFileConvertQueue : sysFileConvertQueues) {
                getFailConvertQueue().put(sysFileConvertQueue, convertType);
            }
        }

    }

}
