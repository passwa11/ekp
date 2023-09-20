package com.landray.kmss.third.ding.ratelimit;

import com.landray.kmss.util.SpringBeanUtil;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 令牌桶限流器工厂
 * @author: Meng.Liu
 * @date: 2018/11/12 下午4:26
 */

public class RateLimiterFactory {

	private static Logger logger = LoggerFactory
			.getLogger(RateLimiterFactory.class);

	private DistributedLock distributedLock;

	private RedissonClient redissonClient;

	/**
	 * 本地持有对象
	 */
	private volatile Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

	private DistributedLock initDistributedLock() throws Exception {
//		RedissonProperties redissonProperties = new RedissonProperties();
//		Config config = new Config();
//		String address = redissonProperties.getAddress();
//		if(StringUtil.isNotNull(address) &&
//		   !address.startsWith("redis")){
//			address = "redis://"+address;
//		}
//		SingleServerConfig singleServerConfig = config.useSingleServer()
//				.setAddress(address)
//				.setTimeout(redissonProperties.getTimeout())
//				.setDatabase(RedisConfig.getDatabase())
//				.setConnectionPoolSize(
//						redissonProperties.getConnectionPoolSize())
//				.setConnectionMinimumIdleSize(
//						redissonProperties.getConnectionMinimumIdleSize());
//		if (StringUtil.isNotNull(redissonProperties.getPassword())) {
//			singleServerConfig.setPassword(redissonProperties.getPassword());
//		}
//		redissonClient = Redisson.create(config);

//		redissonClient = new RedissonClientFactoryBean().getObject();
		redissonClient = (RedissonClient) SpringBeanUtil.getBean("redissonClient");
		return new RedissonDistributedLocker(redissonClient);
	}

	public void close(){
		if(redissonClient!=null){
			redissonClient.shutdown();
		}
	}

	/**
	 * @param key              redis key
	 * @param permitsPerSecond 每秒产生的令牌数
	 * @param maxBurstSeconds  最大存储多少秒的令牌
	 * @return
	 */
	public RateLimiter build(String key, Long permitsPerSecond,
			Integer maxBurstSeconds) throws Exception {
		if (!rateLimiterMap.containsKey(key)) {
			synchronized (this) {
				if (!rateLimiterMap.containsKey(key)) {
					if (distributedLock == null) {
						distributedLock = initDistributedLock();
					}
					logger.debug("生成RateLimiter，permitsPerSecond："+permitsPerSecond+",maxBurstSeconds:"+maxBurstSeconds);
					rateLimiterMap.put(key,
							new RateLimiter(key, permitsPerSecond,
									maxBurstSeconds, distributedLock));
				}
			}
		}
		return rateLimiterMap.get(key);
	}

	public void clearRateLimiterMap(){
		for(String key:rateLimiterMap.keySet()){
			rateLimiterMap.get(key).clear();
		}
		rateLimiterMap.clear();
	}
}
