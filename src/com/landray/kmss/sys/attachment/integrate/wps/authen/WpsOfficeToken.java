package com.landray.kmss.sys.attachment.integrate.wps.authen;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

import com.landray.kmss.sys.authentication.ssoclient.SsoConfigUtil;
import com.landray.kmss.sys.authentication.ssoclient.redis.RedisPoolUtil;
import com.landray.kmss.sys.cache.redis.RedisConfig;

/**
 * Token信息
 * 
 * 
 */
public class WpsOfficeToken implements Serializable{

	private static final long serialVersionUID = -2615720574536845559L;

	private long createTime;

	private long expireTime;

	private String tokenString;

	private String username;

	protected WpsOfficeToken(long expireTime, String username, String tokenString) {
		this.expireTime = expireTime;
		this.username = username;
		this.tokenString = tokenString;
	}

	protected WpsOfficeToken(long createTime, long expireTime, String username,
			String tokenString) {
		this.createTime = createTime;
		this.expireTime = expireTime;
		this.username = username;
		this.tokenString = tokenString;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
            return false;
        }
		if (obj instanceof String) {
			return obj.equals(tokenString);
		}
		if (obj instanceof WpsOfficeToken) {
			return tokenString.equals(((WpsOfficeToken) obj).tokenString);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return tokenString.hashCode();
	}

	/**
	 * 用户名
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 是否过期
	 * 
	 * @return
	 */
	public boolean isExpired() {
		Integer maxAgeRedis = SsoConfigUtil.getCookieMaxageRedis();
		if (!RedisConfig.ENABLED || maxAgeRedis == null) {
			return isExpiredInCookie();
		} else {
			return isExpired(maxAgeRedis);
		}
	}

	public boolean isExpiredInCookie() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT0"));
		return cal.getTimeInMillis() > expireTime;
	}

	public boolean isExpired(Integer maxAgeRedis) {
		String expire_redis_str = RedisPoolUtil.get(tokenString);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT0"));
		if (expire_redis_str == null) {
			boolean isExpired = isExpiredInCookie();
			if (isExpired) {

			} else {
				Long expire = cal.getTimeInMillis() + (maxAgeRedis * 1000);
				RedisPoolUtil.setEx(tokenString, expire + "",
						SsoConfigUtil.getCookieMaxage() + 100);
			}
			return isExpired;
		} else {
			Long expire_redis = Long.parseLong(expire_redis_str);
			boolean isExpired = cal.getTimeInMillis() > expire_redis;
			if (!isExpired) {
				Long expire = cal.getTimeInMillis() + (maxAgeRedis * 1000);
				RedisPoolUtil.setEx(tokenString, expire + "",
						SsoConfigUtil.getCookieMaxage() + 100);
			}
			return isExpired;
		}

	}

	public long getExpireTime() {
		return this.expireTime;
	}

	public long getCreateTime() {
		return this.createTime;
	}

	@Override
	public String toString() {
		return tokenString;
	}
	public String getTokenString(){
        return tokenString;
    }
}
