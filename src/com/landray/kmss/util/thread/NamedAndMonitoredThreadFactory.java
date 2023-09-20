/**
 * 
 */
package com.landray.kmss.util.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可命名、可输出线程堆栈的线程工厂
 * <p>
 * <b>该工厂每创建一个新线程就会创建一个Monitor线程去监控执行堆栈，并计算统计执行的时间，方便排查性能问题</b>
 * <br/>
 * <b>因为监控使用的是轮询方式，所以对系统整体性能影响较大，仅限开发时使用，不可用于生产环境</b>
 * <br/>
 * 可在模块内的的ThreadPoolManager里使用（替换原来的NamedThreadFactory）
 * @author 陈进科
 * 
 */
public class NamedAndMonitoredThreadFactory implements ThreadFactory {

	protected static final AtomicInteger poolNumber = new AtomicInteger(1);

	protected final AtomicInteger threadNumber = new AtomicInteger(1);

	protected final String namePrefix;

	/**
	 * @param name
	 *            - 线程限定名
	 */
	public NamedAndMonitoredThreadFactory(String name) {
		namePrefix = name + "-pool-" + poolNumber.getAndIncrement()
				+ "-thread-";
	}

	@Override
    public Thread newThread(Runnable r) {

		Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
		if (t.isDaemon()){
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		addMonitor(t);
		return t;
	}

	private void addMonitor(Thread targetThread){
		AutoShutdownThreadMonitor monitor = new AutoShutdownThreadMonitor(targetThread.getName(),targetThread);
		monitor.begin();
	}
}
