package com.landray.kmss.sys.filestore.convert.observer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;

/**
 * 回调使用的观察
 */
public class ConvertObservable<T> extends Observable {

    private static final Logger logger = LoggerFactory.getLogger(ConvertObservable.class);

    public void handleConvertCallback(T object) {

        if(logger.isDebugEnabled()) {
            logger.debug("转换厂商返回的回调通知到可观察者");
        }

        this.setChanged();
        this.notifyObservers(object);
    }
}
