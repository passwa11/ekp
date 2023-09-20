package com.landray.kmss.sys.filestore.convert.observer;


import com.landray.kmss.sys.filestore.convert.service.CallbackHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;
import java.util.Observer;

/**
 * 回调后处理观察者
 */
public class ConvertObserver implements Observer {
    private static final Logger logger = LoggerFactory.getLogger(ConvertObserver.class);
    private CallbackHandle callbackHandle;
     public ConvertObserver(CallbackHandle callbackHandle) {
         this.callbackHandle = callbackHandle;
     }
    @Override
    public void update(Observable o, Object arg) {
         if(logger.isDebugEnabled()) {
             logger.debug("观察者开始分发操作");
         }

         try {
            callbackHandle.doCallbackHandel(arg);
         } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
