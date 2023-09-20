package com.landray.kmss.km.calendar.cms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.util.thread.NamedThreadFactory;

public class CMSThreadPoolManager {

    private static CMSThreadPoolManager instance = null;

    private ExecutorService executorService;

    private static final Logger logger = LoggerFactory.getLogger(CMSThreadPoolManager.class);

    private int THREAD_POOL_SIZE = 10;

    public int getTHREAD_POOL_SIZE() {
        return THREAD_POOL_SIZE;
    }

    public void setTHREAD_POOL_SIZE(int thread_pool_size) {
        THREAD_POOL_SIZE = thread_pool_size;
    }

    ThreadPoolExecutor getCMSThreadPoolExecutor() {
        return (ThreadPoolExecutor) executorService;
    }

    private CMSThreadPoolManager() {
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static CMSThreadPoolManager getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    /**
     * 创建一个实例
     */
    private synchronized static void createInstance() {
        if (instance == null) {
            instance = new CMSThreadPoolManager();
        }
    }

    /**
     * 启动
     */
    public synchronized void start() {
        if (executorService != null) {
            return;
        }
        ThreadFactory factory = new NamedThreadFactory(
                "ekp_calendar_syncro_thread");

        executorService = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE * 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(2000),
                factory);
    }

    /**
     * 关闭
     */
    public synchronized void shutdown() {
        if (executorService == null) {
            return;
        }
        try {
            executorService.shutdown();
            int retryCount = 0;
            while (!executorService.awaitTermination(1, TimeUnit.SECONDS))
            {
                ++retryCount;
                if(retryCount > 60)
                {
                    executorService.shutdownNow();
                    break;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.error("线程池关闭失败!!!!");
            logger.error(e.getMessage());
        }
        finally {
            executorService = null;
            instance = null;
        }
    }

    /**
     * 安排一个任务
     *
     * @param task
     */
    public void submit(Runnable task) {
        if (!isStarted()) {
            throw new RuntimeException(
                    "the ThreadPool has not started or has been shutdown!");
        }
        executorService.execute(task);
    }

    /**
     * 判断线程池是否已经启动
     *
     * @return
     */
    public boolean isStarted() {
        return executorService != null && !executorService.isShutdown();
    }
}
