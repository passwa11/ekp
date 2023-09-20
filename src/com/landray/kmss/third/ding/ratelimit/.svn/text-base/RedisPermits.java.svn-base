package com.landray.kmss.third.ding.ratelimit;

import com.landray.kmss.sys.authentication.ssoclient.Logger;
import com.landray.kmss.sys.authentication.ssoclient.redis.RedisPool;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisPermits {

	/**
	 * maxPermits 最大存储令牌数
	 */
	private Long maxPermits;

	public Long getMaxPermits() {
		return maxPermits;
	}

	public void setMaxPermits(Long maxPermits) {
		this.maxPermits = maxPermits;
	}

	public Long getStoredPermits() {
		return storedPermits;
	}

	public void setStoredPermits(Long storedPermits) {
		this.storedPermits = storedPermits;
	}

	public Long getIntervalMillis() {
		return intervalMillis;
	}

	public void setIntervalMillis(Long intervalMillis) {
		this.intervalMillis = intervalMillis;
	}

	public Long getNextFreeTicketMillis() {
		return nextFreeTicketMillis;
	}

	public void setNextFreeTicketMillis(Long nextFreeTicketMillis) {
		this.nextFreeTicketMillis = nextFreeTicketMillis;
	}

	/**
	 * storedPermits 当前存储令牌数
	 */
	private Long storedPermits;
	/**
	 * intervalMillis 添加令牌时间间隔
	 */
	private Long intervalMillis;
	/**
	 * nextFreeTicketMillis 下次请求可以获取令牌的起始时间，默认当前系统时间
	 */
	private Long nextFreeTicketMillis;

	/**
	 * 最大存储令牌的时间（秒）
	 */
	private Integer maxBurstSeconds = 1;

	public RedisPermits(){

	}

	public Long redisNow(){
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
			RedisPool.returnBrokenResource(jedis);
		}
		RedisPool.returnResource(jedis);
		return time;
	}

	/**
	 * @param permitsPerSecond 每秒放入的令牌数
	 * @param maxBurstSeconds  maxPermits由此字段计算，最大存储maxBurstSeconds秒生成的令牌
	 */
	public RedisPermits(Long permitsPerSecond, Integer maxBurstSeconds,
						Long maxPermits) {
		if (null == maxBurstSeconds) {
			maxBurstSeconds = 60;
		}
		this.maxBurstSeconds = maxBurstSeconds;
		if (maxPermits != null) {
			this.maxPermits = maxPermits;
		} else {
			this.maxPermits = (long) (permitsPerSecond * maxBurstSeconds);
		}
		//huangwq 初始令牌环改为1
		//this.storedPermits = permitsPerSecond.longValue();
		this.storedPermits = 1L;
		this.intervalMillis = (long) (TimeUnit.SECONDS.toMillis(1)
				/ permitsPerSecond);
		Long time = redisNow();
		long current = null == time ? System.currentTimeMillis() : time;
		this.nextFreeTicketMillis = current;

		//System.out.println("nextFreeTicketMillis："+nextFreeTicketMillis+"---"+this);
		//Thread.dumpStack();
	}


	/**
	 * redis的过期时长
	 * @return
	 */
	public Long expires() {
//		long now = System.currentTimeMillis();
//		return 2 * TimeUnit.MINUTES.toSeconds(1)
//				+ TimeUnit.MILLISECONDS
//						.toSeconds(Math.max(nextFreeTicketMillis, now) - now);
		return (long) maxBurstSeconds;
	}

	/**
	 * 异步更新当前持有的令牌数
	 * 若当前时间晚于nextFreeTicketMicros，则计算该段时间内可以生成多少令牌，将生成的令牌加入令牌桶中并更新数据
	 * @param now
	 * @return
	 */
	public boolean reSync(long now) {
		if (now > nextFreeTicketMillis) {
			//System.out.println("当前令牌数："+storedPermits+","+System.currentTimeMillis()+","+now+","+nextFreeTicketMillis+","+intervalMillis);
			storedPermits = Math.min(maxPermits, storedPermits
					+ (now - nextFreeTicketMillis) / intervalMillis);
			//System.out.println("当前令牌数："+storedPermits+","+System.currentTimeMillis());
			nextFreeTicketMillis = now;
			return true;
		}
		return false;
	}
}
