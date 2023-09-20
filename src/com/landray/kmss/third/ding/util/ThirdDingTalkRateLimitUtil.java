package com.landray.kmss.third.ding.util;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.landray.kmss.third.ding.model.DingConfig;

import com.landray.kmss.sys.cache.redis.RedisConfig;
import com.landray.kmss.third.ding.ratelimit.RateLimiter;
import com.landray.kmss.third.ding.ratelimit.RateLimiterAcquireException;
import com.landray.kmss.third.ding.ratelimit.RateLimiterFactory;
import com.landray.kmss.third.ding.ratelimit.RatelimitProtectedAction;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThirdDingTalkRateLimitUtil {

	private static Logger logger = LoggerFactory
			.getLogger(ThirdDingTalkRateLimitUtil.class);

	public static Boolean rateLimitEnable = null;

	public static Long rateLimitCount = ThirdDingTalkRateLimitConstant.DEFAULT_API_SED_RATE;

	private static RateLimiterFactory rateLimiterFactory = null;

	private static Set<String> interceptUrls = new HashSet();

	static {
		interceptUrls.add("/process/workrecord/task/");
		interceptUrls.add("/workrecord/");
		interceptUrls.add("/message/corpconversation/");
		interceptUrls.add("/call_back/get_call_back_failed_result");
		//interceptUrls.add("/user/get");
		interceptUrls.add("/todo/users/");
		interceptUrls.add("/department/list");
		interceptUrls.add("/calendar/users");
	}

	public static void setRateLimitEnable(String dingEnableRateLimit,
										  String dingEnableRateLimitCount) {
		if ("true".equals(dingEnableRateLimit)) {
			long rateLimitCount_new = ThirdDingTalkRateLimitConstant.DEFAULT_API_SED_RATE;
			if (StringUtil.isNotNull(dingEnableRateLimitCount)) {
				rateLimitCount_new = Long.parseLong(dingEnableRateLimitCount);
			}
			logger.debug("rateLimitCount_new:"+rateLimitCount_new);
			if(rateLimitCount_new!=rateLimitCount.longValue()) {
				logger.debug("更新rateLimitCount");
				rateLimitCount = rateLimitCount_new;
				getRateLimiterFactory().clearRateLimiterMap();
			}
			rateLimitEnable = true;
		} else {
			rateLimitEnable = false;
		}
	}

	public static boolean getRateLimitEnable() {
		if (rateLimitEnable == null) {
			setRateLimitEnable(
					DingConfig.newInstance().getDingEnableRateLimit(),
					DingConfig.newInstance().getDingEnableRateLimitCount());
		}
		return rateLimitEnable;
	}

	private static boolean isToIntercept(String url) {
		for (String interceptUrl : interceptUrls) {
			if (url.contains(interceptUrl)) {
				return true;
			}
		}
		return false;
	}

	public static Object executeRateLimit(
			String url, RatelimitProtectedAction action) throws Exception {
		boolean result = tryAcquire(url, null, 10, TimeUnit.SECONDS);
		if (result) {
			logger.debug("调用接口："+System.currentTimeMillis());
			return action.execute();
		}
		throw new RateLimiterAcquireException("获取令牌失败，url:"+url);
	}

	public static <T extends TaobaoResponse> T executeRateLimit(
			TaobaoRequest<T> request, String session,
			ThirdDingTalkClient client) throws Exception {
		String url = client.getRequestUrl();
		boolean result = tryAcquire(url, null, 10, TimeUnit.SECONDS);
		if (result) {
			logger.debug("调用接口："+System.currentTimeMillis());
			return client.doExecute(request, session);
		}
		throw new RateLimiterAcquireException("获取令牌失败，url:"+url);
	}

	public static boolean tryAcquire(String url, String httpMethod,
			long timeout, TimeUnit timeUnit) throws Exception {
		if (!RedisConfig.ENABLED) {
			logger.debug("没启用redis");
			return true;
		}
		if (!getRateLimitEnable()) {
			logger.debug("没启用限流");
			return true;
		}
		if (StringUtil.isNull(url)) {
			logger.warn("url为空，不进行限流拦截");
			return true;
		}
		if (!isToIntercept(url)) {
			logger.debug("url: "+url+"，不进行限流拦截");
			return true;
		}
		String key = url;
		if (StringUtil.isNotNull(httpMethod)) {
			key += "-" + httpMethod;
		}
		RateLimiter limiter = getRateLimiterFactory().build(key,
				rateLimitCount, 1);
		return limiter.tryAcquire(timeout, timeUnit);
	}

	public static RateLimiterFactory getRateLimiterFactory() {
		if (rateLimiterFactory == null) {
			rateLimiterFactory = (RateLimiterFactory) SpringBeanUtil
					.getBean("rateLimiterFactory");
		}
		return rateLimiterFactory;
	}

}
