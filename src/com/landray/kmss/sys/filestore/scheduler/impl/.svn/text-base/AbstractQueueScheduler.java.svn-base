package com.landray.kmss.sys.filestore.scheduler.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.filestore.scheduler.IQueueScheduler;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public abstract class AbstractQueueScheduler implements IQueueScheduler, Runnable {

	public AbstractQueueScheduler() {
	}

	protected abstract String getThreadName();

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractQueueScheduler.class);

	protected boolean running = false;
	protected boolean enable = true;
	protected ISysFileConvertDataService dataService = null;
	protected Thread runThread = null;

	protected ReentrantLock reentrantLock = new ReentrantLock();
	protected Condition waitCondition = reentrantLock.newCondition();

	protected void waitingTime(long timeout, TimeUnit timeUnit) {
		reentrantLock.lock();
		try {
			waitCondition.await(timeout, timeUnit);
		} catch (InterruptedException e) {
		} finally {
			reentrantLock.unlock();
		}
	}

	protected void signalAll() {
		reentrantLock.lock();
		try {
			waitCondition.signalAll();
		} finally {
			reentrantLock.unlock();
		}
	}

	protected void logDebug(String message) {
		if (logger != null && logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}

	protected void logDebug(String message, Throwable throwable) {
		if (logger != null && logger.isDebugEnabled()) {
			logger.debug(message, throwable);
		}
	}

	@Override
	public void startScheduler() {
		String enableString = ResourceUtil.getKmssConfigString("kmss.filestore.thread.enable");
		if(StringUtils.isNoneBlank(enableString)) {
			enable = Boolean.valueOf(enableString);
		}
		
		if(enable) {
			if (!running) {
				running = true;
				runThread = new Thread(this, getThreadName());
				runThread.setDaemon(true);
				runThread.start();
			}
		}
		
		 
	}

	@Override
	public void stopScheduler() {
		running = false;
		runThread.interrupt();
	}

	@Override
	public void reDistribute() {
		signalAll();
	}

	@Override
	public void run() {
		String encryptionMode = ResourceUtil.getKmssConfigString("sys.att.encryption.mode");

		if (StringUtil.isNull(encryptionMode)) {
			encryptionMode = "0";
		}

		logger.warn("加密模式:" + encryptionMode);
		while (running) {
			try {
				doDistributeConvertQueue(encryptionMode);
				int sleepTime = getSleepTime();
				if (sleepTime > 0) {
					waitingTime(sleepTime, TimeUnit.SECONDS);
				}
			} catch (Throwable t) {
				logger.warn("未处理的异常", t);
				waitingTime(1, TimeUnit.MINUTES);
			}
		}
	}

	protected int getSleepTime() {
		// 实现类可以实现此方法更改自己的休眠时间
		return Integer.valueOf(dataService.getConfigService().getGlobalConfigForm().getDistributeThreadSleepTime());
	}

	protected abstract void doDistributeConvertQueue(String encryptionMode);
}
