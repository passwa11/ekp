package com.landray.kmss.sys.filestore.queue.observer;

import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueue;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueueService;
import com.landray.kmss.sys.filestore.queue.dto.QueryParameter;
import com.landray.kmss.sys.filestore.queue.dto.RequestParameter;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static com.landray.kmss.sys.filestore.queue.constant.ConvertQueueConstant.*;

/**
 * 观察者： 查询队列表
 */
public class ConvertQueueObserver implements Observer {
    private static final Logger logger = LoggerFactory.getLogger(ConvertQueueObserver.class);
    private static FailConvertQueueObservable failConvertQueueObservable =
            new FailConvertQueueObservable();
    private static FailConvertQueueObserver failConvertQueueObserver =
            new FailConvertQueueObserver();

    /**
     * 转换队列服务
     */
    private static ConvertQueueService convertQueueService = null;
    private static ConvertQueueService getConvertQueueService() {
        if(convertQueueService == null) {
            convertQueueService = (ConvertQueueService) SpringBeanUtil.getBean("convertQueueServiceImpl");
        }

        return convertQueueService;
    }

    static {
        failConvertQueueObservable.addObserver(failConvertQueueObserver);
    }


    /**
     * 队列管理组件
     */
    private static ConvertQueue convertQueue = null;
    private static ConvertQueue getConvertQueue() {
        if(convertQueue == null) {
            convertQueue = (ConvertQueue) SpringBeanUtil.getBean("convertQueueImpl");
        }

        return convertQueue;
    }

    @Override
    public void update(Observable o, Object arg) {

        try {

            RequestParameter requestParameter = (RequestParameter) arg;
            String[] convertKeys = requestParameter.getConvertKeys();
            String convertType = requestParameter.getConvertType();
            if(logger.isDebugEnabled()) {
                logger.debug("观察者接到通知查询未分配任,接收到信息：{}", requestParameter.toString());
            }

            ConvertQueue queue = getConvertQueue();
            // 如果此时队列中有数据了，则不需要去查询数据库
            if(queue.size(convertType) > 0) {
                return;
            }

            // 请求和响应队列还有数据在进行，不需要查询数据库
            // （防止数据状态未更新又重复查询转换）
//            if(!RequestHandleThread.getInstance().isEmpty() ||
//                    !RequestHandleThread.getInstance().isEmpty()) {
//                return;
//            }
            // 存在未分配任务
            if (hasNextData(convertKeys, convertType)) {
                // 添加到转换队列中
                addQueue(convertKeys, convertType);
            } else {
                //  通知执行查询数据库转换不成功队列或执行转换不成功队列
                failConvertQueueObservable.executeFailQueue(requestParameter);
            }

            if(logger.isDebugEnabled()) {
                logger.debug("查询数据库是否含有未转换队列,已查询完成,当前转换队列数：{}",
                        getConvertQueue().size(convertType));
            }


        } catch (Exception e) {
          logger.error("查询离线时的未分配任务异常：{}", e);
        } finally {

        }

    }

    /**
     * 查询看看是否存在未转换任务
     * @param convertKeys
     * @return
     * @throws Exception
     */
    public Boolean hasNextData(String[] convertKeys, String convertType) throws Exception{

        QueryParameter queryParameter = new QueryParameter();
        queryParameter.setConverterKeys(convertKeys);
        queryParameter.setConvertType(convertType);
        queryParameter.setStatus(SysFileConvertConstant.UNASSIGNED);
        queryParameter.setPageNo(QUERY_PAGE_NUMBER);
        queryParameter.setRowSize(QUERY_ROW_NUMBER_ONE);

        return getConvertQueueService().getCount(queryParameter).equals(0) ? false : true;
    }

    /**
     * 查询未转换任务，一次中只查询50条
     * @param convertKeys
     * @param convertType
     * @throws Exception
     */
    public void addQueue(String[] convertKeys, String convertType) throws Exception{

        if(logger.isDebugEnabled()) {
            logger.debug("查询未分配队列");
        }

        QueryParameter queryParameter = new QueryParameter();
        queryParameter.setConverterKeys(convertKeys);
        queryParameter.setConvertType(convertType);
        queryParameter.setStatus(SysFileConvertConstant.UNASSIGNED);
        queryParameter.setPageNo(QUERY_PAGE_NUMBER);
        queryParameter.setRowSize(QUERY_ROW_NUMBER);
        List<SysFileConvertQueue> sysFileConvertQueues =getConvertQueueService().getUnassignedTasks(queryParameter);

        for(SysFileConvertQueue sysFileConvertQueue : sysFileConvertQueues) {
            getConvertQueue().put(sysFileConvertQueue, convertType);
        }
    }

}
