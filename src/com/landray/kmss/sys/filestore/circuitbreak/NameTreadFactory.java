package com.landray.kmss.sys.filestore.circuitbreak;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 线程名称
 *
 */
public class NameTreadFactory implements ThreadFactory {
	private static final Log logger = LogFactory.getLog(NameTreadFactory.class);

        private final AtomicInteger threadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "convert-thread-" + threadNum.getAndIncrement());
            if(logger.isDebugEnabled()) {
            	logger.debug(t.getName() + " has been created");
            }
            
            return t;
        }
    }