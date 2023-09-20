package com.landray.kmss.third.ding.ratelimit;

import java.util.concurrent.locks.Lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedissonDistributedLocker implements DistributedLock {

	private static final Logger logger = LoggerFactory
			.getLogger(RedissonDistributedLocker.class);

	private RedissonClient redissonClient;

	public RedissonDistributedLocker(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}

//	@Override
//	public RLock lock(String lockKey) {
//		RLock rLock = this.getRLock(lockKey);
//		rLock.lock();
//		return rLock;
//	}

//	@Override
//	public RLock lock(String lockKey, long leaseTime) {
//		return this.lock(lockKey, leaseTime, TimeUnit.SECONDS);
//	}
//
//	@Override
//	public RLock lock(String lockKey, long leaseTime, TimeUnit timeUnit) {
//		RLock rLock = this.getRLock(lockKey);
//		rLock.lock(leaseTime, timeUnit);
//		return rLock;
//	}

//	@Override
//	public boolean tryLock(String lockKey, long waitTime, long leaseTime,
//			TimeUnit timeUnit) {
//		RLock rLock = this.getRLock(lockKey);
//		try {
//			return rLock.tryLock(waitTime, leaseTime, timeUnit);
//		} catch (InterruptedException e) {
//			logger.error("", e);
//		}
//		return false;
//	}

	@Override
	public void unLock(String lockKey) {
		Lock rLock = this.getRLock(lockKey);
		rLock.unlock();
	}

	@Override
	public void unLock(RLock rLock) {
		if (null == rLock) {
			throw new NullPointerException("rLock cannot be null.");
		}
		rLock.unlock();
	}

	@Override
    public RLock getRLock(String lockKey) {
		if (null == redissonClient) {
			throw new NullPointerException("redisson client is null.");
		}
		return redissonClient.getLock(lockKey);
	}
}
