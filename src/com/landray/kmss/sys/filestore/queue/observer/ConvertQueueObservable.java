package com.landray.kmss.sys.filestore.queue.observer;

import com.landray.kmss.sys.filestore.queue.dto.RequestParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;

/**
 * 通知观察才执行查询数据库中未分配的队列
 */
public class ConvertQueueObservable extends Observable {

    private static final Logger logger = LoggerFactory.getLogger(ConvertQueueObservable.class);
    /**
     * 查询数据
     * @param requestParameter
     */
    public void queryConvertInfo(RequestParameter requestParameter){
        if(logger.isDebugEnabled()) {
            logger.debug("通知查询未分配转换信息，可观察者接收到信息:{}", requestParameter.toString());
        }
        this.setChanged();
        this.notifyObservers(requestParameter);
    }
}
