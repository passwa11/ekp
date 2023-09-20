package com.landray.kmss.third.ding.util;

public class ThirdDingTalkRateLimitConstant {

	/**
	 * 处理接口频率问题
	 * <pre>
	 * <h3>钉钉官方说明</h3>
	 * <b>企业和ISV共享</b>
	 * 每个接口每秒能被调用的次数都有一个上限，触发上限的话接口会返回错误码90002。遇到此错误码，需要你在服务端代码里等待1秒钟再继续运行。
	 * 企业和ISV的每个IP，调用所有开放平台接口总量，不能超过20秒3000次，如触发限流，会禁止调用5分钟。该限流不会返回对应的错误码，返回的是一个html页面，需要开发者自行处理。处理方法为：增加IP的数量，控制调用量。
	 * <b>企业独享</b>
	 * 每个企业的每个appkey调用单个接口的频率不可超过40次/秒，否则返回错误码90018。
	 * 每个企业的每个appkey调用单个接口的频率不可超过1500次/分，否则返回错误码90006。
	 * 每个企业调用单个接口的频率不可超过1500次/分，否则返回错误码90005。
	 * </pre>
	 * @throws ApiException 
	 */

	/**
	 * 默认的IP每秒限流频率
	 */
	public final static long DEFAULT_IP_SED_RATE = 150L;

	/**
	 * 默认的接口每秒限流频率，钉钉的限制是40。本地限流先设成30，防止因为网络等一些原因导致瞬时并发超过40
	 */
	public final static long DEFAULT_API_SED_RATE = 30L;

	/**
	 * 默认的接口每分钟限流频率
	 */
	public final static long DEFAULT_API_MIN_RATE = 1500L;

	/**
	 * 默认的IP限流等待时间
	 */
	public final static long DEFAULT_WAITING_IP_SEDTIMEOUT = 5L;

	/**
	 * 默认的接口每秒限流等待时间
	 */
	public final static long DEFAULT_WAITING_API_SEDTIMEOUT = 3L;

	/**
	 * 默认的接口每分钟限流等待时间
	 */
	public final static long DEFAULT_WAITING_API_MINTIMEOUT = 30L;

	/**
	 * 全局Ip地址
	 */
	public final static String DEFAULT_IP = "99.99.99.99";

}
