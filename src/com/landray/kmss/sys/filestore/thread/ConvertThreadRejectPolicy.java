package com.landray.kmss.sys.filestore.thread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 转换线程池使用的拒绝策略
 */
public class ConvertThreadRejectPolicy implements RejectedExecutionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ConvertThreadRejectPolicy.class);
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        doLog(r, e);
    }

    private void doLog(Runnable r, ThreadPoolExecutor e) {
        // 日志记录
        logger.debug("{} rejected by convertThreadRejectPolicy", r.toString());
    }
}
