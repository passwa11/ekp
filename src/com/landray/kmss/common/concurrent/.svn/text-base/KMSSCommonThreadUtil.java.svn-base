package com.landray.kmss.common.concurrent;

import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.core.task.TaskRejectedException;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 系统级线程池
 *
 * @author 严明镜
 * @version 1.0 2021年04月08日
 */
public class KMSSCommonThreadUtil {

	private static final Logger logger = LoggerFactory.getLogger(KMSSCommonThreadUtil.class);

	private static ThreadPoolTaskExecutor taskExecutor;

	private static ThreadPoolTaskExecutor getTaskExecutor() {
		if (taskExecutor == null) {
			taskExecutor = (ThreadPoolTaskExecutor) SpringBeanUtil.getBean("kmssCommonThreadTaskExecutor");
		}
		Assert.isTrue(taskExecutor != null, "未找到Bean：kmssCommonThreadTaskExecutor");
		return taskExecutor;
	}

	/**
	 * 获取线程队列剩余空间，大于0则表示还有空余。
	 * <b>注意</b>该值不一定准确，因为可能空闲位置随后立即被其它线程占用，
	 * 所以在后续execute时依然可能抛出TaskRejectedException异常。
	 */
	public static int remainingCapacity() {
		return getTaskExecutor().getThreadPoolExecutor().getQueue().remainingCapacity();
	}

	/**
	 * 启动新线程执行
	 * <pre>{@code
	 *     KMSSCommonThreadUtil.execute(() -> System.out.println("execute start"));
	 * }</pre>
	 * @param task 可执行任务
	 * @throws TaskRejectedException 当任务无法执行时抛出
	 */
	public static void execute(Runnable task) {
		getTaskExecutor().execute(task);
	}

	/**
	 * 以可获取返回值的方式启动新线程执行
	 * <pre>{@code
	 *  System.out.println("submit Callable get=" + KMSSCommonThreadUtil.submit((Callable<Object>) () -> {
	 *    System.out.println("submit Callable start");
	 *    return "call result";
	 *  }).get());
	 * }</pre>
	 * @param task 可执行任务
	 * @return 可获取结果值的Future对象
	 * @throws TaskRejectedException 当任务无法执行时抛出
	 */
	public static <T> Future<T> submit(Callable<T> task) {
		return getTaskExecutor().submit(task);
	}
}
