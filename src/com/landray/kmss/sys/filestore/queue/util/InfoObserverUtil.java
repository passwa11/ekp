package com.landray.kmss.sys.filestore.queue.util;

import com.landray.kmss.sys.filestore.queue.dto.RequestParameter;
import com.landray.kmss.sys.filestore.queue.observer.ConvertQueueObservable;
import com.landray.kmss.sys.filestore.queue.observer.ConvertQueueObserver;

/**
 * 观察者
 */
public class InfoObserverUtil {
    // 观察者
    private  static ConvertQueueObservable convertQueueObservable = new ConvertQueueObservable();
    private  static ConvertQueueObserver convertQueueObserver = new ConvertQueueObserver();

    static class Singleton {

       public static InfoObserverUtil instance = new InfoObserverUtil();
    }
    public static InfoObserverUtil getInstance() {
        return Singleton.instance;
    }

    static {
        convertQueueObservable.addObserver(convertQueueObserver);
    }

    private InfoObserverUtil() {

    }
    public void infoObserver(String[] convertType, String serverName) {
        RequestParameter requestParameter = new RequestParameter(convertType, serverName);
        convertQueueObservable.queryConvertInfo(requestParameter);
    }
}
