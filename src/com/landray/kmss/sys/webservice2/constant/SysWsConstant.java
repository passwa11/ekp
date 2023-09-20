package com.landray.kmss.sys.webservice2.constant;

/**
 * 常量类
 * 
 * @author Jeff
 * 
 */
public interface SysWsConstant {

	// 服务状态：已停止
	public static final int SERVICE_STATUS_STOP = 0;

	// 服务状态：已启动
	public static final int SERVICE_STATUS_START = 1;

	// 启动类型：自动
	public static final String STARTUP_TYPE_AUTO = "0";

	// 启动类型：手动
	public static final String STARTUP_TYPE_MANUAL = "1";

	// 启动类型：禁用
	public static final String STARTUP_TYPE_DISABLE = "2";

	// 执行成功
	public static final String EXEC_SUCCESS = "0";

	// 服务运行时发生异常
	public static final String SERVICE_EXCEPTION = "1";

	// 用户名或密码错误
	public static final String UNAUTHORIZED_USER = "2";

	// 非法的客户端IP地址
	public static final String ILLEGAL_IP = "3";

	// 用户帐号已被锁定
	public static final String LOCKED_USER = "4";

	// 消息体大小超出限制
	public static final String BODY_SIZE_EXPIRE = "5";

	// 运行日志的字段
	public static final String LOG_FIELDS = "fdId, fdServiceName, fdServiceBean, fdUserName, fdClientIp, fdStartTime, fdEndTime, fdExecResult, fdErrorMsg, fdRunTime, fdRunTimeMillis, fdServiceMethod, fdRequestMsg, fdResponseMsg";

}
