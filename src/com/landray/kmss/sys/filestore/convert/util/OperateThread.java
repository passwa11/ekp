package com.landray.kmss.sys.filestore.convert.util;

import com.landray.kmss.sys.filestore.convert.thread.CleanResourcesThread;
import com.landray.kmss.sys.filestore.convert.thread.RequestHandleThread;
import com.landray.kmss.sys.filestore.convert.thread.ResponseHandleThread;
import com.landray.kmss.sys.filestore.thread.ConvertThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程操作
 */
public class OperateThread implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(OperateThread.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.warn("开始初始化线程池和线程......");
		ThreadPoolExecutor threadPoolExecutor =
				ConvertThreadPool.getInstance().getThreadPoolExecutor();
		initConvertTread(threadPoolExecutor);
		initHandleThread(threadPoolExecutor);
		initCleanResourceThread(threadPoolExecutor);
	}

	public void initConvertTread(ThreadPoolExecutor threadPoolExecutor) {
		RequestHandleThread convertTread = RequestHandleThread.getInstance();
		convertTread.setConvertRunning(true);
		convertTread.setDaemon(true);
		threadPoolExecutor.submit(convertTread);
		logger.warn("开启转换线程");
		//convertTread.start();
	}

	public void initHandleThread(ThreadPoolExecutor threadPoolExecutor) {
		ResponseHandleThread handleThread = ResponseHandleThread.getInstance();
		handleThread.setHandleRunning(true);
		handleThread.setDaemon(true);
		//handleThread.start();
		threadPoolExecutor.submit(handleThread);
		logger.warn("开启转换处理线程");
	}

	public void initCleanResourceThread(ThreadPoolExecutor threadPoolExecutor) {
		CleanResourcesThread cleanResourceThread =  CleanResourcesThread.getInstance();
		cleanResourceThread.setCleanRunning(true);
		cleanResourceThread.setDaemon(true);
		//cleanResourceThread.start();
		threadPoolExecutor.submit(cleanResourceThread);
		logger.warn("开启转换未返回回调的清除线程");
	}
}
