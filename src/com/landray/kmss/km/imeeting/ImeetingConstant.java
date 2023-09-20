package com.landray.kmss.km.imeeting;

import com.landray.kmss.constant.SysDocConstant;

public interface ImeetingConstant extends SysDocConstant {

	public static final String FEEDBACK_TYPE_HOST = "01"; // 主持

	public static final String FEEDBACK_TYPE_ATTEND = "02"; // 参加人员

	public static final String FEEDBACK_TYPE_PARTICIPANT = "03"; // 列席人员

	public static final String FEEDBACK_TYPE_INVITE = "04"; // 邀请人员

	public static final String FEEDBACK_TYPE_TOPIC_REPORTER = "05"; // 议题汇报人

	public static final String FEEDBACK_TYPE_TOPIC_ATTENDUNITLIAISON = "06"; // 议题出席单位联络员

	public static final String FEEDBACK_TYPE_TOPIC_LISTENUNITLIAISON = "07"; // 议题旁听单位联络员

	public static final String FEEDBACK_TYPE_TOPIC_ATTEND = "08"; // 议题参会人

	public static final String UNIT_TOPIC_ATTEND = "topic_attend"; // 列席单位

	public static final String UNIT_TOPIC_LISTEN = "topic_listen"; // 旁听单位

	public static final String ROLE_ISSUE_ATTEND = "issue-attend"; // 列席单位

	public static final String ROLE_ISSUE_NONVOTOR = "issue-nonvotor"; // 旁听单位

	// *********************会议主文档状态 *********************

	public static final String FEEDBACK_NOTIFY_PROXY = "proxy";
	public static final String FEEDBACK_NOTIFY_INVITE = "invite";

	/**
	 * 取消
	 */
	public static final String STATUS_CANCEL = "41";

	/**
	 * --------------------是否催办纪要-------------------------
	 */
	public static final String HURRY_SUMMARY_YES = "true";

	public static final String HURRY_SUMMARY_NO = "false";

	/**
	 * --------------------是否会议变更-------------------------
	 */
	public static final String IS_CHANGEMEETING_YES = "true";

	public static final String IS_CHANGEMEETING_NO = "false";

	/**
	 * --------------------是否发送会议通知-------------------------
	 */
	public static final String IS_NOTIFY_YES = "true";

	public static final String IS_NOTIFY_NO = "false";

	/**
	 * ----------------------是否包含会议纪要---------------------
	 */
	public static final String MAIN_CONTAINS_SUMMARY_YES = "true";

	public static final String MAIN_CONTAINS_SUMMARY_NO = "false";

	/**
	 * ----------------------是否取消会议---------------------
	 */
	public static final String IS_CANCELMEETING_YES = "true";

	public static final String IS_CANCELMEETING_NO = "false";

	// *********************会议操作*********************
	/**
	 * 创建会议
	 */
	public static final String MEETING_MAIN_ADD = "01";

	/**
	 * 发送会议通知
	 */
	public static final String MEETING_MAIN_NOTIFY = "02";

	/**
	 * 上会材料
	 */
	public static final String MEETING_MAIN_UPLOAD = "03";

	/**
	 * 取消会议
	 */
	public static final String MEETING_MAIN_CANCEL = "04";

	/**
	 * 会议变更
	 */
	public static final String MEETING_MAIN_CHANGE = "05";

	/**
	 * 纪要录入
	 */
	public static final String MEETING_MAIN_SUMMARY = "06";

	/**
	 * 其它人员回复情况
	 */
	public static final String MEETING_OTHER_REMARKDETAILS = "05";

	/**
	 * 历史记录
	 */
	public static final String MEETING_HISTORY = "06";

	/**
	 * 会议变更后与会人员操作状态重置
	 */
	public static final String MEETING_RESET = "07";

	/**
	 * 召开时间不定期
	 */
	public static final int MEETING_PERIOD_INDEFINITE = 5;

	public static Long FLOW_NUMBER_START = new Long(1);

	// *********************会议操作*********************

	/**
	 * 全部资源
	 */
	public static final String MEETING_RES_ALL = "all";

	/**
	 * 空闲资源
	 */
	public static final String MEETING_RES_REFF = "free";

	// *********************会议通知选项*********************

	/**
	 * 发布后自动通知
	 */
	public static final String MEETING_NOTIFY_AUTO = "1";

	/**
	 * 手动通知
	 */
	public static final String MEETING_NOTIFY_SELF = "2";

	// *********************会议回执类型*********************

	/**
	 * 已参加
	 */
	public static final String MEETING_FEEDBACK_OPT_ATTEND = "01";

	/**
	 * 不参加
	 */
	public static final String MEETING_FEEDBACK_OPT_UNATTEND = "02";

	/**
	 * 找人代理
	 */
	public static final String MEETING_FEEDBACK_OPT_PROXY = "03";

	/**
	 * 未回执
	 */
	public static final String MEETING_FEEDBACK_OPT_NOT = "04";

	/**
	 * 邀请他人参加
	 */
	public static final String MEETING_FEEDBACK_OPT_ATTENDOTHER = "05";

	/**
	 * 系统发送
	 */
	public static final String MEETING_FEEDBACK_FROMTYPE_SYSTEM = "0";
	/**
	 * 他人邀请
	 */
	public static final String MEETING_FEEDBACK_FROMTYPE_INVITE = "1";
	/**
	 * 他人代理邀请
	 */
	public static final String MEETING_FEEDBACK_FROMTYPE_PROXY = "2";

	// *********************编辑方式*********************
	/**
	 * 编辑方式 -RTF
	 */
	public static final String FDCONTENTTYPE_RTF = "rtf";

	/**
	 * 编辑方式 - Word文档
	 */
	public static final String FDCONTENTTYPE_WORD = "word";

	// *********************日程同步方式*********************

	/**
	 * 不同步
	 */
	public static final String MEETING_SYNC_NOSYNC = "noSync";

	/**
	 * @deprecated 流程提交后同步
	 */
	public static final String MEETING_SYNC_FLOWSUBMIT = "flowSubmit";

	/**
	 * 发送会议通知后同步
	 */
	public static final String MEETING_SYNC_SENDNOTIFY = "sendNotify";

	/**
	 * 点击提交后同步
	 */
	public static final String MEETING_SYNC_PERSONATTEND = "personAttend";

	// *********************检测结果*********************

	/**
	 * 不检测
	 */
	public static final String MEETING_CHECKFREE_NOTCHECK = "01";

	/**
	 * 没有冲突
	 */
	public static final String MEETING_CHECKFREE_NOCONFLICT = "02";

	/**
	 * 存在冲突
	 */
	public static final String MEETING_CHECKFREE_CONFLICT = "03";

	/**
	 * 会议提前结束
	 */
	public static final String MEETING_MAIN_EARLYEND = "08";

	// *********************纪要通知人********************

	/**
	 * 会议组织人
	 */
	public static final String MEETING_SUMMARY_NOTITY_EMCEE = "1";

	/**
	 * 会议主持人
	 */
	public static final String MEETING_SUMMARY_NOTITY_HOST = "2";

	/**
	 * 实际与会人员
	 */
	public static final String MEETING_SUMMARY_NOTITY_ACTUAL_ATTEND = "3";

	/**
	 * 抄送人
	 */
	public static final String MEETING_SUMMARY_NOTITY_COPY = "4";

	/**
	 * 参加/列席人
	 */
	public static final String MEETING_SUMMARY_NOTITY_PLAN = "5";

	public static final String RECURRENCE_FREQ = "FREQ";

	public static final String RECURRENCE_INTERVAL = "INTERVAL";

	public static final String RECURRENCE_COUNT = "COUNT";

	public static final String RECURRENCE_UNTIL = "UNTIL";

	public static final String RECURRENCE_BYDAY = "BYDAY";
	/*
	 * 日历重复周期设置，包含：不重复、按天、按周、按月、按年
	 */
	public static final String RECURRENCE_FREQ_NO = "NO";

	public static final String RECURRENCE_FREQ_DAILY = "DAILY";

	public static final String RECURRENCE_FREQ_WEEKLY = "WEEKLY";

	public static final String RECURRENCE_FREQ_MONTHLY = "MONTHLY";

	public static final String RECURRENCE_FREQ_YEARLY = "YEARLY";

	/*
	 * 循环结束方式
	 */
	public static final String RECURRENCE_END_TYPE = "ENDTYPE";

	public static final String RECURRENCE_END_TYPE_NEVER = "NEVER";

	public static final String RECURRENCE_END_TYPE_COUNT = "COUNT";

	public static final String RECURRENCE_END_TYPE_UNTIL = "UNTIL";

	/*
	 * 频率为每月时，重复类型：一月的某天、一周的某天
	 */
	public static final String RECURRENCE_MONTH_TYPE = "RECURRENCE_MONTH_TYPE";

	public static final String RECURRENCE_MONTH_TYPE_MONTH = "month";

	public static final String RECURRENCE_MONTH_TYPE_WEEK = "week";

	public static final String RECURRENCE_WEEKS = "RECURRENCE_WEEKS";
}
