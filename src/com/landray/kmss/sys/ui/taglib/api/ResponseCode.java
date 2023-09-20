package com.landray.kmss.sys.ui.taglib.api;

public interface ResponseCode {

	/**
	 * 成功
	 */
	public static final String SUCCESS = "0";

	/** 服务端错误 10+ */
	public static final String StringERNAL_ERROR = "10";
	
	/** 服务端错误:用户访问错误：登录及权限等错误 20+ */

	public static final String USER_ACCESS_NOT_LOGIN = "20";

	public static final String USER_ACCESS_LOCKED = "21";

	public static final String USER_ACCESS_DISABLED = "22";

	public static final String USER_ACCESS_PASSWORD_WRONG = "23";

	public static final String USER_ACCESS_KICKED_BY_LOGIN = "24";

	public static final String USER_ACCESS_EXPIRED = "25";

	

	/** 客户端错误 700+ */
	/** 客户端参数错误 */
	public static final String CLIENT_PRAMETER_ERROR = "700";

	/** 客户端无效参数错误 */
	public static final String CLIENT_INVALID_REQUEST = "701";

	/** 客户端访问过于频繁 */
	public static final String CLIENT_REQUEST_FREQUENCE_LIMIT = "702";

	/** API模块错误 1200 ~1300 */
	public static final String SERVER_SYS_ERROR = "1200";
	
	public static final String API_FORMAT_ERROR = "1201";
	
	public static final String API_UNSUPPORT_REQUEST = "1202";
	
	public static final String API_UNKNOWN_TOKEN = "1203";
	
	public static final String API_INVALID_TOKEN = "1204";
	
	public static final String API_USER_NOT_FOUND = "1205";
	
	public static final String API_AUTH_ERROR = "1206";
}
