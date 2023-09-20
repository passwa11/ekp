package com.landray.kmss.util.breaker;

public class CircuitBreakerConfig {
	/**
	 * 关闭状态最大线程数
	 */
	public int maxThreadInClosed = 100;

	/**
	 * 半开状态最大线程数
	 */
	public int maxThreadInHalfOpen = 5;

	/**
	 * 打开状态多少毫秒后，自动转为半关闭状态
	 */
	public long openTimeout = 2000;

	/**
	 * 半开状态下，当连续成功了多少次以后，置为关闭，不建议调整
	 */
	public int successCount2Closed = 5;

	/**
	 * 关闭状态下，当连续失败了多少次以后，置为打开，不建议调整
	 */
	public int failureCount2Open = 5;
}
