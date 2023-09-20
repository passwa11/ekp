package com.landray.kmss.third.ding.ratelimit;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.redisson.api.RLock;

/**
 * 基于Redisson的分布式锁接口
 * @author liumeng
 */
public interface DistributedLock {

	/**
	 * 获取锁
	 * @param lockKey
	 * @return
	 */
	//RLock lock(String lockKey);

	/**
	 * 获取锁，设置锁超时时长
	 * @param lockKey
	 * @param leaseTime
	 * @return
	 */
	//RLock lock(String lockKey, long leaseTime);

	/**
	 * 获取锁，设置锁超时时长
	 * @param lockKey
	 * @param leaseTime
	 * @param timeUnit
	 * @return
	 */
	//RLock lock(String lockKey, long leaseTime, TimeUnit timeUnit);

	/**
	 * 尝试获取锁
	 * @param lockKey
	 * @param waitTime
	 * @param leaseTime
	 * @param timeUnit
	 * @return
	 */
	//boolean tryLock(String lockKey, long waitTime, long leaseTime,
	//		TimeUnit timeUnit);

	/**
	 * 释放锁
	 * @param lockKey
	 */
	void unLock(String lockKey);

	/**
	 * 释放锁
	 * @param rLock
	 */
	void unLock(RLock rLock);

	RLock getRLock(String lockKey);
}
