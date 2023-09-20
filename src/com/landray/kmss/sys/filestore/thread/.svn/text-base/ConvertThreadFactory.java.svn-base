package com.landray.kmss.sys.filestore.thread;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 转换线程池使用的线程工厂
 */
public class ConvertThreadFactory implements ThreadFactory {

    private static final Log logger = LogFactory.getLog(ConvertThreadFactory.class);

    private final AtomicInteger threadNum = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "factory-convert-thread-" + threadNum.getAndIncrement());
        if(logger.isDebugEnabled()) {
            logger.debug(t.getName() + " has been created");
        }

        return t;
    }
}
