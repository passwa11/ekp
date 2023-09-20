package com.landray.kmss.km.review.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import com.landray.kmss.util.thread.NamedThreadFactory;

/**
 * 线程池管理
 * 
 * 
 */
public class ThreadPoolManager {
	private static final int THREAD_POOL_SIZE = 10;

	private static ThreadPoolManager instance = null;

	private ExecutorService executorService;

	ThreadPoolExecutor getThreadPoolExecutor() {
		return (ThreadPoolExecutor) executorService;
	}

	private ThreadPoolManager() {
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static ThreadPoolManager getInstance() {
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
			instance = new ThreadPoolManager();
		}
	}

	/**
	 * 启动
	 */
	public synchronized void start() {
		if (executorService != null) {
			return;
		}
		ThreadFactory factory = new NamedThreadFactory("ekp_km_review_thread");
		switch (THREAD_POOL_SIZE) {
		// 单个线程
		case 1:
			executorService = Executors.newFixedThreadPool(1, factory);
			break;
		// 无限制线程
		case Integer.MAX_VALUE:
			executorService = Executors.newCachedThreadPool(factory);
			break;
		// 确定线程数
		default:
			executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE,
					factory);
			break;
		}
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
	 * 判断线程池是否已经启动
	 * 
	 * @return
	 */
	public boolean isStarted() {
		return executorService != null && !executorService.isShutdown();
	}
}
