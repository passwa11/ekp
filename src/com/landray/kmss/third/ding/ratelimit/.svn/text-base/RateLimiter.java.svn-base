package com.landray.kmss.third.ding.ratelimit;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.authentication.ssoclient.redis.RedisPool;
import com.landray.kmss.sys.authentication.ssoclient.redis.RedisPoolUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * 令牌桶限流器
 * @author: Meng.Liu
 * @date: 2018/11/12 下午4:31
 */
public class RateLimiter {

	private static Logger logger = LoggerFactory
			.getLogger(RateLimiter.class);

	/**
	 * redis key
	 */
	private String key;
	/**
	 * redis分布式锁的key
	 * @return
	 */
	private String lockKey;
	/**
	 * 每秒存入的令牌数
	 */
	private Long permitsPerSecond;
	/**
	 * 最大存储maxBurstSeconds秒生成的令牌
	 */
	private Integer maxBurstSeconds;
	/**
	 * 分布式同步锁
	 */
	private DistributedLock syncLock;

	public RateLimiter(String key, Long permitsPerSecond,
			Integer maxBurstSeconds, DistributedLock syncLock) {
		this.key = key;
		this.lockKey = "DISTRIBUTED_LOCK:" + key;
		this.permitsPerSecond = permitsPerSecond;
		this.maxBurstSeconds = maxBurstSeconds;
		this.syncLock = syncLock;
	}

	/**
	 * 生成并存储默认令牌桶
	 * @return
	 */
	private RedisPermits putDefaultPermits() {
		//this.lock();
		Lock rLock = null;
		try {
			rLock = syncLock.getRLock(lockKey);
			rLock.lock();
			String obj = RedisPoolUtil.get(key);
			if (null == obj) {
				RedisPermits permits = new RedisPermits(permitsPerSecond,
						maxBurstSeconds, 2L);
				logger.debug("设置 "+key+" 的缓存，"+JSONObject.toJSONString(permits));
				RedisPoolUtil.setEx(key, JSONObject.toJSONString(permits),
						permits.expires().intValue());
				return permits;
			} else {
				JSONObject RedisPermitsJson = JSONObject.parseObject(obj);
				return JSON.toJavaObject(RedisPermitsJson, RedisPermits.class);
			}
		} finally {
			//this.unlock();
			if(rLock!=null){
				rLock.unlock();
			}
		}

	}

	/**
	 * 加锁
	 */
//	private void lock() {
//		syncLock.lock(lockKey);
//	}

	/**
	 * 解锁
	 */
//	private void unlock() {
//		syncLock.unLock(lockKey);
//	}

	/**
	 * 获取令牌桶
	 * @return
	 */
	public RedisPermits getPermits() {
		String obj = RedisPoolUtil.get(key);
		if (null == obj) {
			return putDefaultPermits();
		}
		JSONObject RedisPermitsJson = JSONObject.parseObject(obj);
		return JSON.toJavaObject(RedisPermitsJson, RedisPermits.class);
	}

	/**
	 * 更新令牌桶
	 * @param permits
	 */
	public void setPermits(RedisPermits permits) {
		logger.debug("更新 "+key+" 的缓存，"+JSONObject.toJSONString(permits));
		RedisPoolUtil.set(key, JSONObject.toJSONString(permits));
	}

	/**
	 * 等待直到获取指定数量的令牌
	 * @param tokens
	 * @return
	 * @throws InterruptedException
	 */
	public Long acquire(Long tokens) throws InterruptedException {
		long milliToWait = this.reserve(tokens);
		logger.debug("acquire for " + milliToWait + "ms " +
				Thread.currentThread().getName());
		Thread.sleep(milliToWait);
		return milliToWait;
	}

	/**
	 * 获取1一个令牌
	 * @return
	 * @throws InterruptedException
	 */
	private long acquire() throws InterruptedException {
		return acquire(1L);
	}

	/**
	 *
	 * @param tokens 要获取的令牌数
	 * @param timeout 获取令牌等待的时间，负数被视为0
	 * @param unit
	 * @return
	 * @throws InterruptedException
	 */
	public Boolean tryAcquire(Long tokens, Long timeout, TimeUnit unit)
			throws InterruptedException {
		long timeoutMicros = Math.max(unit.toMillis(timeout), 0);
		checkTokens(tokens);
		Long milliToWait;
		long start = System.currentTimeMillis();
		Lock rLock = null;
		try {
			logger.debug(Thread.currentThread().getName()+"  加锁");
			//this.lock();
			rLock = syncLock.getRLock(lockKey);
			rLock.lock();
			if (!this.canAcquire(tokens, timeoutMicros)) {
				return false;
			} else {
				milliToWait = this.reserveAndGetWaitLength(tokens);
			}
		} finally {
			logger.debug(
					"占用锁耗时：" + (System.currentTimeMillis() - start) + "，  解锁");
			//this.unlock();
			if(rLock!=null){
				rLock.unlock();
			}
		}
		logger.debug(Thread.currentThread().getName()+"  等待 "+milliToWait);
		Thread.sleep(milliToWait);
		return true;
	}

	/**
	 * 获取一个令牌
	 * @param timeout
	 * @param unit
	 * @return
	 * @throws InterruptedException
	 */
	public Boolean tryAcquire(Long timeout, TimeUnit unit)
			throws InterruptedException {
		return tryAcquire(1L, timeout, unit);
	}

	private long redisNow() {
		Long time = null;
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedis();
			List<String> times = jedis.time();
			String currUnixTime = times.get(0);
			String currMicrosecond = times.get(1);
			time = Long.parseLong(currUnixTime)*1000L + (Long.parseLong(currMicrosecond)/1000L);
			//log.debug("对比："+(time-System.currentTimeMillis()));
		} catch (Exception e) {
			logger.error("expire key:{" + key + "} error", e);
			RedisPool.returnBrokenResource(jedis);
		}
		RedisPool.returnResource(jedis);
		return null == time ? System.currentTimeMillis() : time;
	}

	/**
	 * 获取令牌n个需要等待的时间
	 * @param tokens
	 * @return
	 */
	private long reserve(Long tokens) {
		this.checkTokens(tokens);
		Lock rLock = null;
		try {
			//this.lock();
			rLock = syncLock.getRLock(lockKey);
			rLock.lock();
			return this.reserveAndGetWaitLength(tokens);
		} finally {
			//this.unlock();
			if(rLock!=null){
				rLock.unlock();
			}
		}
	}

	/**
	 * 校验token值
	 * @param tokens
	 */
	private void checkTokens(Long tokens) {
		if (tokens < 0) {
			throw new IllegalArgumentException(
					"Requested tokens " + tokens + " must be positive");
		}
	}

	/**
	 * 在等待的时间内是否可以获取到令牌
	 * @param tokens
	 * @param timeoutMillis
	 * @return
	 */
	private Boolean canAcquire(Long tokens, Long timeoutMillis) {
		return queryEarliestAvailable(tokens) - timeoutMillis <= 0;
	}

	/**
	 * 返回获取{tokens}个令牌最早可用的时间
	 * @param tokens
	 * @return
	 */
	private Long queryEarliestAvailable(Long tokens) {
		long n = redisNow();
		RedisPermits permit = this.getPermits();
		permit.reSync(n);
		// 可以消耗的令牌数
		long storedPermitsToSpend = Math.min(tokens, permit.getStoredPermits());
		// 需要等待的令牌数
		long freshPermits = tokens - storedPermitsToSpend;
		// 需要等待的时间
		long waitMillis = freshPermits * permit.getIntervalMillis();
		return permit.getNextFreeTicketMillis() - n +
				waitMillis;
	}

	/**
	 * 预定@{tokens}个令牌并返回所需要等待的时间
	 * @param tokens
	 * @return
	 */
	private Long reserveAndGetWaitLength(Long tokens) {
		long n = redisNow();
		RedisPermits permit = this.getPermits();
		permit.reSync(n);
		// 可以消耗的令牌数
		long storedPermitsToSpend = Math.min(tokens, permit.getStoredPermits());
		// 需要等待的令牌数
		long freshPermits = tokens - storedPermitsToSpend;
		// 需要等待的时间
		long waitMillis = freshPermits * permit.getIntervalMillis();
		permit.setNextFreeTicketMillis(
				permit.getNextFreeTicketMillis() + waitMillis);
		permit.setStoredPermits(
				permit.getStoredPermits() - storedPermitsToSpend);
		this.setPermits(permit);
		return permit.getNextFreeTicketMillis() - n;
	}

	public void clear(){
		RedisPoolUtil.del(key);
	}
}
