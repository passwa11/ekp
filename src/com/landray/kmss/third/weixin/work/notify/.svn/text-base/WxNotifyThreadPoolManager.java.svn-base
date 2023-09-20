package com.landray.kmss.third.weixin.work.notify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import com.landray.kmss.util.thread.NamedThreadFactory;

public class WxNotifyThreadPoolManager {

	private static WxNotifyThreadPoolManager instance = null;

	private ExecutorService executorService;

	private int THREAD_POOL_SIZE = 5;

	public int getTHREAD_POOL_SIZE() {
		return THREAD_POOL_SIZE;
	}

	public void setTHREAD_POOL_SIZE(int thread_pool_size) {
		THREAD_POOL_SIZE = thread_pool_size;
	}

	ThreadPoolExecutor getCMSThreadPoolExecutor() {
		return (ThreadPoolExecutor) executorService;
	}

	private WxNotifyThreadPoolManager() {
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static WxNotifyThreadPoolManager getInstance() {
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
			instance = new WxNotifyThreadPoolManager();
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
				"ekp_weixin_notify_syncro_thread");

		executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE,
				factory);

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		ExecutorService service = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 10; i++) {
			System.out.println("创建线程" + i);
			Runnable run = new Runnable() {
				@Override
                public void run() {
					System.out.println("启动线程");
				}
			};
			// 在未来某个时间执行给定的命令
			service.execute(run);
		}
		// 关闭启动线程
		// service.shutdown();
		// 等待子线程结束，再继续执行下面的代码
		// service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		System.out.println("all thread complete");

	}

}
