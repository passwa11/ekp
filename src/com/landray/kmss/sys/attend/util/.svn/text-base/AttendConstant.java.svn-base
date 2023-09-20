package com.landray.kmss.sys.attend.util;

import com.landray.kmss.util.ResourceUtil;

public interface AttendConstant {

	public static final String MONDAY = "1";

	public static final String TUESDAY = "2";

	public static final String WENDSDAY = "3";

	public static final String THURSDAY = "4";

	public static final String FRIDAY = "5";

	public static final String SATURDAY = "6";

	public static final String SUNDAY = "7";

	// 签到组状态，1：进行中，2：已结束，3：已删除
	public static final String UNSTART = "0";
	public static final String DOING = "1";
	public static final String FINISHED = "2";
	public static final String DEL = "3";
	// 签到类型
	public static final int FDTYPE_ATTEND = 1;
	public static final int FDTYPE_CUST = 2;

	// 签到周期类型
	public static final String FDPERIODTYPE_WEEK = "1";
	public static final String FDPERIODTYPE_CUST = "2";

	// 签到班次
	public static final String FDWORK_ONCE = "1";
	public static final String FDWORK_TWICE = "2";

	public interface SysAttendMain {
		public static final Integer[] FD_WORK_TYPE = new Integer[] { 0, 1 };

	}

	/**
	 * 流程状态，0是标识 新流程事件开始，1 是结束或者默认
	 */
	public static final Integer[] ATTEND_PROCESS_STATUS = new Integer[] { 0 , 1 };
	/**
	 * 流程删除标识.1是删除
	 */
	public static final Integer[] ATTEND_PROCESS_BUSINESS_DEL_FLAG = new Integer[] { 0 , 1 };

	/**
	 * 流程状态。0，1，2，3是空，为了引用地方用具体数字来区分。这里没意义。
	 * 4 出差
	 * 5 是请假
	 * 6 是加班
	 * 7 是外出
	 * 9 是销出差
	 * 8 是销假
	 */
	public static final Integer[] ATTEND_PROCESS_TYPE = new Integer[] { 0 , 1 ,2,3,4,5,6,7,8,9};


	// 会议签到组 默认迟到时间
	public static final String FD_SIGN_LATETIME = "5";
	// 默认加班最小时长
	public static final String FD_MIN_HOUR = "3";
	// 签到日期类型（工作日、休息日、节假日）
	public static final String[] FD_DATE_TYPE = new String[] { "0", "1", "2" };
	// 打卡状态
	public static final Integer[] ATTENDMAIN_FDSTATUS = new Integer[] { 0, 1, 2,
			3, 4, 5, 6 };
	// 班制类型：0固定班制，1排班，2自定义
	public static final Integer[] FD_SHIFT_TYPE = new Integer[] { 0, 1, 2 };
	// 一周不同工作时间：0相同，1不同
	public static final Integer[] FD_SAMEWTIME_TYPE = new Integer[] { 0, 1 };

	// 最晚打卡结束时间类型：1当天，2次日
	public static final Integer[] FD_ENDDAY_TYPE = new Integer[] { 0, 1, 2 };

	// 跨天排班类型 :1不夸天，2次日
	public static final Integer[] FD_OVERTIME_TYPE = new Integer[] { 0, 1, 2 };

	// 流程计算类型:1 按天，2 按半天，3 按小时（仅请假用到）
	public static final Integer[] FD_STAT_TYPE = new Integer[] { 0, 1, 2, 3 };

	// 排班流程业务类型 4 出差,5 请假,6 加班 ,7 外出，8 销假,9 销出差
	public static final Integer[] FD_ATTENDBUS_TYPE = new Integer[] { 0, 1, 2,
			3, 4, 5, 6, 7, 8, 9 };

	// 流程按半天算类型 1上午 2下午
	public static final Integer[] FD_NOON_TYPE = new Integer[] { 0, 1, 2 };

	public static final String[] FD_SECURITY_MODE = new String[] { "camera",
			"face" };
	public interface SysAttendConfig {
		String[] FD_CLIENT = new String[] { "kk", "ding" };

	}
	
	public static final Integer DING_SYN_ERROR_REPEAT = 3;
    public static final String DING_SYN_ERROR_FDFLAG_SEND = "0";
    public static final String DING_SYN_ERROR_FDFLAG_ERROR = "1";

	// 系统来源
	public static final String SYS_ATTEND_MAIN_FDAPPNAME = "dingding";
	public static final String SYS_ATTEND_MAIN_QIYEWEIXIN = "qywx";
	
	/**
	 * 时间格式要求
	 */
	public static final String DATE_TIME_FORMAT_STRING = ResourceUtil
			.getString("sysAttend.date.format.datetime","sys-attend");
}
