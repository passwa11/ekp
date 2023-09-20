package com.landray.kmss.sys.filestore.circuitbreak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 拒绝策略
 *
 */
public class RejectPolicy implements RejectedExecutionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RejectPolicy.class);
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        doLog(r, e);
    }

    private void doLog(Runnable r, ThreadPoolExecutor e) {
        // 日志记录
        logger.debug("{} rejected", r.toString());
       }
    }