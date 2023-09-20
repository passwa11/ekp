package com.landray.kmss.third.ding.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 共享timeout的可重入锁
 */
public class CommonReentrantLock extends ReentrantLock {

	private static final long serialVersionUID = 1L;

	private static long expiresTime = 0;

	@Override
	public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
		if (this.isHeldByCurrentThread()) {
			// 重入,直接调用
			// System.out.println(Thread.currentThread().getName() +
			// "重入,直接调用...");
			return super.tryLock(timeout, unit);
		}
		if (this.isLocked()) {
			// 重新计算新线程等待锁的timeout
			long newTimeout = expiresTime - System.currentTimeMillis();
			// System.out.println(Thread.currentThread().getName() + " timeout:"
			// + newTimeout);
			return super.tryLock(newTimeout, TimeUnit.MILLISECONDS);
		}
		// 获得锁的线程最长占用锁的时间点
		expiresTime = System.currentTimeMillis() + unit.toMillis(timeout);
		// System.out.println(Thread.currentThread().getName() + "释放时间点:" +
		// expiresTime);
		return super.tryLock(timeout, unit);
	}

}
