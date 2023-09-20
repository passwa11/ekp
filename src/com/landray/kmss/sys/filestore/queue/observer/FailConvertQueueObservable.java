package com.landray.kmss.sys.filestore.queue.observer;

import com.landray.kmss.sys.filestore.queue.dto.RequestParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;

/**
 * 失败转换通知观察者
 */
public class FailConvertQueueObservable extends Observable {

    private static final Logger logger = LoggerFactory.getLogger(FailConvertQueueObservable.class);
    /**
     * 失败队列执行转换
     * @param requestParameter
     */
    public void executeFailQueue(RequestParameter requestParameter){
        if(logger.isDebugEnabled()) {
            logger.debug("接收到通知查询数据库中转换失败的队列或执行转换失败的队列，信息：{}",
                    requestParameter.toString());
        }
        this.setChanged();
        this.notifyObservers(requestParameter);
    }
}
