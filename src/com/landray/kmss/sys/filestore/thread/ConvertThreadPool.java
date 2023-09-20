package com.landray.kmss.sys.filestore.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 */
public class ConvertThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(ConvertThreadPool.class);
    private static ThreadPoolExecutor threadPoolExecutor = null;

    // 核心线程数量
    private static final int THREAD_CORE_POOL_SIZE = 3;
    // 最大线程数量
    private static final int THREAD_MAX_MUN_POOL_SIZE = 6;
    // 非核心线程保留时间
    private static final long THREAD_KEEP_ALIVE_TIME = 30;
    // 线程队列大小
    private static final int THREAD_QUEUE_SIZE = 100;

    public ConvertThreadPool() {

        threadPoolExecutor = new ThreadPoolExecutor(THREAD_CORE_POOL_SIZE,
                THREAD_MAX_MUN_POOL_SIZE, THREAD_KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(THREAD_QUEUE_SIZE),
                new ConvertThreadFactory(), new ConvertThreadRejectPolicy());
        logger.warn("完成初始化转换线程池...");
    }

    static class Singleton {
        private static ConvertThreadPool instance = new ConvertThreadPool();
    }

    public static ConvertThreadPool getInstance() {
        return Singleton.instance;
    }

    public ThreadPoolExecutor getThreadPoolExecutor(){
        return threadPoolExecutor;
    }
}
