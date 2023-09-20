package com.landray.kmss.util.breaker;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.conn.ConnectTimeoutException;

public class CircuitBreakerTest {
	// 在单例模式，可以不定义为静态变量，使用时，请为熔断器命名
	private static CircuitBreaker breaker = new CircuitBreaker(
			"CircuitBreakerTest");

	/**
	 * 本方法为样例代码，仅供开发人员理解接口使用，请勿直接搬过去
	 */
	public void doDemo() {
		try {
			final String param = "hello world!";
			Object result = breaker.execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					// 该方法为受熔断器保护的方法
					// 声明为final的变量，在这里可以直接获取
					System.out.println(param);
					return "result";
				}

				@Override
				public boolean isBreakException(Exception e) {
					// 当execute方法触发，并且有错误抛出时，该方法将被触发
					// 根据错误类型，return
					// true表示需要触发熔断器的熔断功能，一般自己程序的错误不需要触发熔断，远程服务无法连接的时候才触发熔断
					return e instanceof ConnectTimeoutException;
				}
			});
			// 这里的result值，就是action.execute的返回值
			System.out.println(result);
		} catch (OpenCircuitException e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// 当熔断器生效时抛出该错误，action.execute方法不会被触发
		} catch (Exception e) {
			// 要么在这里做错误处理，要么不捕获该错误
			// action.execute方法抛出错误的时候，在这里可以捕获
		}
	}

	/**
	 * 本方法为测试代码，测试之前，请在log4j开启<br>
	 * log4j.logger.com.landray.kmss.util.breaker = DEBUG
	 */
	public void doTest() {
		CircuitBreakerConfig config = new CircuitBreakerConfig();
		// config.maxThreadInClosed = 20;
		// config.maxThreadInHalfOpen = 5;
		// config.openTimeout = 2000;
		// config.successCount2Closed = 5;
		// config.failureCount2Open = 5;

		final CircuitBreaker breaker = new CircuitBreaker("CircuitBreakerTest",
				config);
		final long beginTime = System.currentTimeMillis(); // 开始时间
		final long totalTime = 300000; // 测试时长
		final AtomicInteger activeCount = new AtomicInteger(0); // 当前运行线程数
		final Map<String, Boolean> exeThreads = new ConcurrentHashMap<String, Boolean>(); // 每个统计周期运行过的线程
		// 执行线程
		for (int i = 0; i < 110; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// 执行3分钟
					while (System.currentTimeMillis() - beginTime < totalTime) {
						final long dt = (System.currentTimeMillis() - beginTime) / 1000; // 时间差（秒）
						try {
							ProtectedAction action = new ProtectedAction() {
								@Override
								public Object execute() throws Exception {
									activeCount.addAndGet(1);
									try {
										exeThreads.put(Thread.currentThread()
												.getName(), Boolean.TRUE);
										Thread.sleep(100);
										// 随机异常
										if (new SecureRandom().nextDouble() < getRate(dt)) {
											throw new Exception();
										}
										return null;
									} finally {
										activeCount.addAndGet(-1);
									}
								}

								@Override
								public boolean isBreakException(Exception e) {
									return true;
								}
							};
							breaker.execute(action);
						} catch (OpenCircuitException e) {
						} catch (Exception e) {
						}
					}
				}
			}).start();
		}
		// 监控线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (System.currentTimeMillis() - beginTime < totalTime) {
					long dt = (System.currentTimeMillis() - beginTime) / 1000;
					if (dt % 50 == 0) {
						System.out.println("时间\t\t错误率\t\t当前线程数\t执行过的线程数");
					}
					int exeThreadCount = exeThreads.size();
					exeThreads.clear();
					System.out.println(dt + "\t\t"
							+ Math.round(getRate(dt) * 100) + "%\t\t"
							+ activeCount.get() + "\t\t" + exeThreadCount);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
	}

	private double getRate(long dt) {
		// 0~25秒，每秒+2%，25~50秒，每秒-2%
		double r = (dt % 50) * 0.02;
		if (r > 0.5) {
			r = 1 - r;
		}
		return r;
	}

	public static void main(String[] args) {
		new CircuitBreakerTest().doTest();
	}
}
