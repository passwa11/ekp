package com.landray.kmss.sys.restservice.server.constant;

/**
 * 常量类
 * 
 * @author  
 * 
 */
public interface SysRsConstant {

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
	public static final String EXCEPTION_SERVICE = "1";

	// 用户名或密码错误
	public static final String UNAUTHORIZED_USER = "2";

	// 非法的客户端IP地址
	public static final String ILLEGAL_IP = "3";

	// 用户帐号已被锁定
	public static final String LOCKED_USER = "4";

	// 消息体大小超出限制
	public static final String BODY_SIZE_EXPIRE = "5";
	
	// 未找到请求的REST服务
	public static final String EXCEPTION_REQUEST = "6";

	// 运行日志的字段
	public static final String LOG_FIELDS = "fdId, fdName, fdServiceName, fdUserName, fdClientIp, fdStartTime, fdEndTime, fdExecResult, fdErrorMsg, fdRunTime, fdRunTimeMillis, fdOriginUri, fdRequestMsg, fdResponseMsg";
	
	/**
	 * 账号认证方式
	 */
	public static final String AUTH_TYPE_BASIC = "BASIC";
	public static final String AUTH_TYPE_OAUTH2 = "OAUTH2";
	
	//账号认证
	public static final String POLICY_MODEL_USER = "0";
	//匿名
	public static final String POLICY_MODEL_ANONYMOUS = "1";
	//固定密钥(EKP Cloud)
	public static final String POLICY_MODEL_SECRETKEY = "2";
}
