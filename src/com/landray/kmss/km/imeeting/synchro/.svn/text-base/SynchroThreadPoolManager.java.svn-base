package com.landray.kmss.km.imeeting.synchro;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.util.thread.NamedThreadFactory;

/**
 * 线程池管理
 */
public class SynchroThreadPoolManager {

	private static SynchroThreadPoolManager instance = null;
	
	private ExecutorService executorService =null;
	
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	private static int THREAD_POOL_SIZE = 20;
	
	/**
	 * 返回实例
	 */
	public static SynchroThreadPoolManager getInstance(){
		if(instance ==null){
			instance = new SynchroThreadPoolManager();
		}
		return instance;
	}
	
	/**
	 * 启动
	 */
	public synchronized void start(){
		if (executorService != null && !executorService.isTerminated()) {
			if (logger.isDebugEnabled()) {
				logger.debug("线程池已经启动");
			}
			return;
		}
		ThreadFactory factory = new NamedThreadFactory(
				"ekp_imeeting_syncro_thread");
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
	 * 判断是否已经启动
	 */
	public boolean isStarted() {
		return executorService != null && !executorService.isShutdown();
	}
	
	/**
	 * 安排一个任务
	 */
	public void submit(Runnable task) {
		if (!isStarted()) {
			throw new RuntimeException(
					"the ThreadPool has not started or has been shutdown!");
		}
		executorService.submit(task);
	}
	
	
	
	
	
}
