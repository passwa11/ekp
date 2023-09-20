package com.landray.kmss.third.im.kk.constant;

/**
 * <P>KK二维码登录常量</P>
 * @author 孙佳
 */
public class KkQrcodeConstants {

	/**
	 * 3.5 用户信息查询接口（用于EKP服务器调用）
	 */
	public static final String KK_QRCODE_QUERYBIND = "serverj/qrcode/queryBind.ajax";
	
	/**
	 * 3.1 二维码显示界面（用于浏览器调用）
	 */
	public static final String KK_QRCODE_INDEX = "serverj/qrcode/getcode";

	/**
	 * 3.3 二维码查询接口（用于浏览器调用）
	 */
	public static final String KK_QRCODE_QUERY = "serverj/qrcode/query.ajax";

	/**
	 * 5.1 EKP登录服务（用于KK页面跳转EKP使用）
	 */
	public static final String EKP_USER_LOGIN = "/third/im/kk/user.do?method=login";

	/**
	 *	6.1 获取用户在线状态
	 */
	public static final String KK_USER_STATE = "serverj/user/getUserPresence.ajax";

}
