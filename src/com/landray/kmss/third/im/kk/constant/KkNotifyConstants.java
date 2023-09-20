package com.landray.kmss.third.im.kk.constant;

/**
 * kk通讯中的常量
 * @author 
 *
 */
public class KkNotifyConstants {
	
	/**
	 * 模块名称 msg
	 */
	public static final String KK_MSG_TYPE_SMART="smart-msg";
	/**
	 * 模块名称 org
	 */
	public static final String KK_MSG_TYPE_ORG="organization";
	/**
	 * 模块名称sso
	 */
	public static final String KK_MSG_TYPE_SSO="Sso";
	//======================
	/**
	 * 发送im消息
	 */
	public static final String KK_MSG_CMD_MSG="send-im-msg";
	/**
	 * 发送广播
	 */
	public static final String KK_MSG_CMD_BROADCAS="send-broadcas";
	/**
	 * 待办
	 */	
	//	public static final String KK_MSG_CMD_DO="send-do-list";
	public static final String KK_MSG_CMD_DO="notice-msg";
	/**
	 * 待阅
	 */
	public static final String KK_MSG_CMD_NOTICE="notice-msg";
	/**
	 * 获取待办待阅的条数
	 */
	public static final String KK_MSG_CMD_NOTICECOUNT="notice-count";
	/**
	 * 发送短信
	 */
	public static final String KK_MSG_CMD_SMS="send-sms";
	/**
	 * 邮件推送
	 */
	public static final String KK_MSG_CMD_MAIL="push-email";
	//======================
	/**
	 * 获取用户状态
	 */
	public static final String KK_ORG_CMD_STATUS="get-user-status";
	/**
	 * 获取用户信息
	 */
	public static final String KK_ORG_CMD_INFO="get-user-info";
	/**
	 * 获取sso令牌
	 */
	public static final String KK_SSO_CMD_GET="get-sso-token";
	/**
	 * 验证sso令牌
	 */
	public static final String KK_SSO_CMD_CHECK="check-sso-token";
	
	//=================================
	/**
	 * 系统账号名
	 */
	public static final String KK_SYS_ACCOUNT="";
	
	/**
	 * 系统名字
	 */
	public static final String KK_SYS_NAME="EKP";
	
//	==============消息机制中待办 跟待阅区分
	/**
	 * 待办
	 */
	public static final Integer KK_NOTIFY_DO=1;
	/**
	 * 待阅
	 */
	public static final Integer KK_NOTIFY_READ=2;
	/**
	 * 暂挂
	 */
	public static final Integer KK_NOTIFY_SUSPEND = 3;
	
	/**
	 * 重要级别：高
	 */
	public static final String KK_IMPORT_LV_H="high";
	/**
	 * 重要级别：一般
	 */
	public static final String KK_IMPORT_LV_N="normal";
	/**
	 * 紧急级别：高
	 */
	public static final String KK_EMERGENT_LV_H="high";
	/**
	 * 紧急级别：一般
	 */
	public static final String KK_EMERGENT_LV_N="normal";
	
	public static final String KK5_CROP = "0001";

	public static final String KK5_RECEIVER = "KK_AppMessage_Receiver1";
	public static final String KK5_RECEIVER2 = "KK_AppMessage_Receiver2";

	//消息队列表 常量

	/**
	 * 向kk发送待办的线程池维护线程的最小数量
	 */
	public static final String KK_COREPOOLSIZE = "kmss.ims.notify.kk.corePoolSize";
	/**
	 * 向kk发送待办的线程池维护线程的最大数量
	 */
	public static final String KK_MAXPOOLSIZE = "kmss.ims.notify.kk.maxPoolSize";
	/**
	 * 向kk发送待办的线程池线程队列容量
	 */
	public static final String KK_QUEUECAPACITY = "kmss.ims.notify.kk.queueCapacity";
	/**
	 * 向kk发送待办的线程池线程队列容量
	 */
	public static final String KK_KEEPALIVESECONDS = "kmss.ims.notify.kk.keepAliveSeconds";


	/**
	 *  推送应用消息通知地址
	 */
	public static final String PUSH_USER_URL = "serverj/push/pushUser.ajax";

	/**
	 * 推送应用角标(批量，5265)地址
	 */
	public static final String PUSH_BADGEBATCH2_URL = "serverj/push/pushBadgeBatch2.ajax";

	/**
	 * 推送公众号消息
	 */
	public static final String KK5_PUSH_TO_SERVICEUSER_API = "push/pushServiceUser.ajax";

	/**
	 *  获取所有可用的公众号地址
	 */
	public static final String KK5_GETGZH_API = "info/listService.ajax";

	/**
	 * 获取用户icon
	 */
	public static final String KK5_GETUSERIMAGE_API = "readUserIcon";
	/**
	 * 更新用户icon
	 */
	public static final String KK5_CHANGEUSERIMAGE_API = "updateUserIcon";

	/**
	 * 消息队列状态  0-待推送     1-推送中     2-推送成功
	 */

	public static final Integer NOTIFY_STATUS_WAIT = 0;

	public static final Integer NOTIFY_STATUS_USE = 1;

	public static final Integer NOTIFY_STATUS_SUCCESS = 2;

	/**
	 * 发送新的待办
	 */
	public static final Integer SNED_NOTIFY__TODO = 1;

	/**
	 * 	更新待办数
	 */
	public static final Integer UPDATE_NOTIFY__TODO_NUM = 2;

	/**
	 * 更新待阅数
	 */
	public static final Integer UPDATE_NOTIFY__TOREAD_NUM = 3;

}
