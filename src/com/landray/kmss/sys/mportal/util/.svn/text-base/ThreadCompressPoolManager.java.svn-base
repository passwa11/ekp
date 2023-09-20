package com.landray.kmss.sys.mportal.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import com.landray.kmss.util.thread.NamedThreadFactory;

/**
 * 线程池管理(压缩门户部件单独使用)
 * 
 */
public class ThreadCompressPoolManager {
	private static final int THREAD_POOL_SIZE = 5;

	private static ThreadCompressPoolManager instance = null;

	private ExecutorService executorService;

	ThreadPoolExecutor getThreadPoolExecutor() {
		return (ThreadPoolExecutor) executorService;
	}

	private ThreadCompressPoolManager() {
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static ThreadCompressPoolManager getInstance() {
		if (instance == null) {
			synchronized (ThreadCompressPoolManager.class) {
				if (instance == null) {
					instance = new ThreadCompressPoolManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 启动
	 */
	public synchronized void start() {
		if (executorService != null) {
			return;
		}
		ThreadFactory factory = new NamedThreadFactory("ekp_sys_mportal_thread");
		executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE,
				factory);
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
		executorService.submit(task);
	}

	/**
	 * 关闭
	 */
	public synchronized void shutdown() {
		if (executorService == null) {
			return;
		}
		executorService.shutdown();
		executorService = null;
		instance = null;
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
