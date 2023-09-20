package com.landray.kmss.util.breaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CircuitBreaker {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CircuitBreaker.class);

	private CircuitBreakerConfig config;

	private CircuitBreakerState state = CircuitBreakerState.Closed;

	private String name;

	/** 并发线程数 */
	private int threadCount = 0;

	/** 成功次数，半开状态使用 */
	private int successInHalfOpen = 0;

	/** 失败次数，关闭状态使用 */
	private int failureInClosed = 0;

	/** 状态置为Open的时间点，打开状态使用 */
	private long timeInOpen;

	public CircuitBreaker(String name, CircuitBreakerConfig config) {
		super();
		this.name = name;
		this.config = config;
	}

	public CircuitBreaker(String name) {
		this(name, new CircuitBreakerConfig());
	}

	/** 获取配置信息 */
	public CircuitBreakerConfig getConfig() {
		return config;
	}

	/** 获取当前状态 */
	public CircuitBreakerState getState() {
		return state;
	}

	/**
	 * 手工置为关闭状态
	 */
	public synchronized void toClosedState() {
		changeState(CircuitBreakerState.Closed);
	}

	/**
	 * 保护并执行代码
	 * 
	 * @param action
	 * @return action.execute()返回的结果
	 * @throws OpenCircuitException
	 *             当过载时抛出该错误
	 * @throws Exception
	 *             action.execute()抛出的错误
	 */
	public Object execute(ProtectedAction action) throws OpenCircuitException,
			Exception {
		doPrepare();
		try {
			Object result = action.execute();
			doSuccess();
			return result;
		} catch (Exception e) {
			if (action.isBreakException(e)) {
				doFailure();
			}
			throw e;
		} finally {
			doFinally();
		}
	}

	/** 准备阶段 */
	private synchronized void doPrepare() throws OpenCircuitException {
		switch (state) {
		case Closed:
			// 关闭状态，超出线程不执行
			if (threadCount >= config.maxThreadInClosed) {
				throw new OpenCircuitException();
			}
			break;
		case HalfOpen:
			// 半开状态，超出线程不执行
			if (threadCount >= config.maxThreadInHalfOpen) {
				throw new OpenCircuitException();
			}
			break;
		case Open:
			// 打开状态，未到期不执行，到期切换为半开状态
			if (threadCount >= config.maxThreadInHalfOpen
					|| System.currentTimeMillis() - timeInOpen < config.openTimeout) {
				throw new OpenCircuitException();
			}
			changeState(CircuitBreakerState.HalfOpen);
			break;
		}
		threadCount++;
	}

	/** 执行成功 */
	private synchronized void doSuccess() {
		switch (state) {
		case Closed:
			// 打开状态，重置失败次数
			failureInClosed = 0;
			break;
		case HalfOpen:
			// 半开状态，成功次数累计，达到阀值后置为关闭
			successInHalfOpen++;
			if (successInHalfOpen >= config.successCount2Closed) {
				changeState(CircuitBreakerState.Closed);
			}
			break;
		}
	}

	/** 执行失败 */
	private synchronized void doFailure() {
		switch (state) {
		case Closed:
			// 关闭状态，失败次数累计，达到阀值后置为打开
			failureInClosed++;
			if (failureInClosed >= config.failureCount2Open) {
				changeState(CircuitBreakerState.Open);
			}
			break;
		case HalfOpen:
			// 半开状态，置为打开
			changeState(CircuitBreakerState.Open);
			break;
		}
	}

	/** 执行结束 */
	private synchronized void doFinally() {
		threadCount--;
	}

	/** 改变状态 */
	private void changeState(CircuitBreakerState state) {
		if (this.state == state) {
			return;
		}
		this.successInHalfOpen = 0;
		this.failureInClosed = 0;
		if (state == CircuitBreakerState.Open) {
			this.timeInOpen = System.currentTimeMillis();
			if (this.state == CircuitBreakerState.Closed) {
				logger.warn("CircuitBreaker " + name + "'s State Changed : "
						+ this.state + " -> " + state);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("CircuitBreaker " + name
							+ "'s State Changed : " + this.state + " -> "
							+ state);
				}
			}
		} else {
			this.timeInOpen = 0;
			if (state == CircuitBreakerState.Closed) {
				logger.warn("CircuitBreaker '" + name + "' State Changed : "
						+ this.state + " -> " + state);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("CircuitBreaker '" + name
							+ "' State Changed : " + this.state + " -> "
							+ state);
				}
			}
		}
		this.state = state;
	}
}
