package com.landray.kmss.km.imeeting.synchro;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.landray.kmss.util.thread.NamedThreadFactory;

/**
 * <p>
 * 单例线程池，程序直接使用，拒绝策略采用的是默认策略，需要业务处理异常
 * </p>
 * 从无边界线程池换成了有界线程池 modify wangjf
 */
public class SynchroInThreadPoolManager {

    private volatile static SynchroInThreadPoolManager instance = null;
    //线程池启动的标志
    private volatile boolean init = false;

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroInThreadPoolManager.class);

    private ExecutorService executorService;

    private int THREAD_POOL_SIZE = 1;

    public int getTHREAD_POOL_SIZE() {
        return THREAD_POOL_SIZE;
    }

    /**
     * 不允许外部设置核心线程池的大小，方法弃用
     *
     * @param thread_pool_size
     * @return: void
     * @author: wangjf
     * @time: 2022/6/7 6:11 下午
     */
    @Deprecated
    public void setTHREAD_POOL_SIZE(int thread_pool_size) {
    }

    ThreadPoolExecutor getCMSThreadPoolExecutor() {
        return (ThreadPoolExecutor) executorService;
    }

    private SynchroInThreadPoolManager() {
        init();
    }

    /**
     * 初始化线程池
     *
     * @param
     * @return: void
     * @author: wangjf
     * @time: 2022/6/7 5:48 下午
     */
    private void init() {
        if (executorService == null) {
            ThreadFactory factory = new NamedThreadFactory("ekp_imeeting_synchro_thread");
            executorService = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE * 2,
                    0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(2000), factory);
            init = true;
            if (logger.isDebugEnabled()) {
                logger.debug("线程池ekp_imeeting_synchro_thread启动完成");
            }
        }
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static SynchroInThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (SynchroInThreadPoolManager.class) {
                if (instance == null) {
                    instance = new SynchroInThreadPoolManager();
                }
            }
        }
        return instance;
    }


    /**
     * 启动
     */
    public void start() {
        getInstance();
    }

    /**
     * 关闭
     */
    @Deprecated
    public void shutdown() {
    }

    /**
     * 提交一个任务
     *
     * @param task
     */
    public void submit(Runnable task) {
        if (!isStarted()) {
            throw new RuntimeException("the ThreadPool has not started or has been shutdown!");
        }
        executorService.submit(task);
    }

    /**
     * 判断线程池是否已经启动
     *
     * @return
     */
    public boolean isStarted() {
        return this.init;
    }

}
