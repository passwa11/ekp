package com.landray.kmss.sys.praise.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.landray.kmss.util.StringUtil;

public class SysPraiseInfoCommonUtil {

	/**
	 * 赞赏方式：点赞
	 */
	public static final Integer TYPE_PRAISE = 1;

	/**
	 * 赞赏方式：打赏
	 */
	public static final Integer TYPE_RICH = 2;

	/**
	 * 赞赏方式：点踩
	 */
	public static final Integer TYPE_OPPOSE = 3;

	/**
	 * 人员类型：操作人员
	 */
	public static final String PERSONTYPE_OPERATOR = "operator";

	/**
	 * 人员类型： 目标人员
	 */
	public static final String PERSONTYPE_TARGET = "target";

	public static final String WEEK = "week";
	public static final String MONTH = "month";
	public static final String YEAR = "year";
	public static final String TOTAL = "total";

	/**
	 * 根据时间获取年月信息
	 * 
	 * @param docCreateTime
	 * @return
	 */
	public static String getYearMonth(Date docCreateTime) {

		Calendar calendar = Calendar.getInstance();
		if (docCreateTime != null) {
			calendar.setTime(docCreateTime);
		}
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;

		return month > 9 ? String.valueOf(year + "" + month) : String.valueOf(year + "0" + month);
	}

	/**
	 * 获取时间区间内的所有年月信息
	 * 
	 * @param lastTime
	 * @param nowTime
	 * @return
	 */
	public static List getYearMonthList(Date lastTime, Date nowTime) {
		List<String> ymList = new ArrayList<String>();
		if (lastTime == null || nowTime == null) {
			return ymList;
		}

		Calendar lastCal = Calendar.getInstance(), nowCal = Calendar.getInstance();
		lastCal.setTime(lastTime);
		nowCal.setTime(nowTime);
		while (lastCal.get(Calendar.MONTH) <= nowCal.get(Calendar.MONTH) && lastCal.get(Calendar.YEAR) <= nowCal.get(Calendar.YEAR)) {
			ymList.add(getYearMonth(lastCal.getTime()));
			lastCal.add(Calendar.MONTH, 1);
		}
		return ymList;
	}

	/**
	 * 判断是否同年（月、周）
	 * 
	 * @param fdNow
	 * @param fdLastUpdateTime
	 * @param timeType
	 * @return
	 */
	public static Boolean timeCheck(Date fdNow, Date fdLastUpdateTime, String timeType) {
		Boolean isSame = false;
		if (StringUtil.isNotNull(timeType)) {
			Calendar fdLastTime = Calendar.getInstance();
			fdLastTime.setTime(fdLastUpdateTime);
			Calendar fdNowTime = Calendar.getInstance();
			fdNowTime.setTime(fdNow);
			if (YEAR.equals(timeType)) {
				if (fdLastTime.get(Calendar.YEAR) == fdNowTime.get(Calendar.YEAR)) {
					isSame = true;
				}
			} else if (MONTH.equals(timeType)) {
				if (fdLastTime.get(Calendar.YEAR) == fdNowTime.get(Calendar.YEAR)
						&& fdLastTime.get(Calendar.MONTH) == fdNowTime.get(Calendar.MONTH)) {
					isSame = true;
				}
			} else if (WEEK.equals(timeType)) {
				if (fdLastTime.get(Calendar.YEAR) == fdNowTime.get(Calendar.YEAR)
						&& fdLastTime.get(Calendar.WEEK_OF_YEAR) == fdNowTime.get(Calendar.WEEK_OF_YEAR)) {
					isSame = true;
				}
			}
		}
		return isSame;
	}

	/**
	 * 获取选定时间所在的周天的日期
	 */
	public static Date getWeekTime(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new Timestamp(sdf.parse(sdf.format(cal.getTime())).getTime());
	}

	/**
	 * 获取XXXXX年第一天的日期
	 * 
	 * @param startDate
	 * @return
	 */
	public static Date getNowYearDate(Date startDate) {
		Calendar calendar = Calendar.getInstance();
		if (startDate != null) {
			calendar.setTime(startDate);
		}
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}
	
	/**
	 * 获取当月第一天
	 */
	public static Date getMonthTime(Date startDate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		if (startDate != null) {
			cal.setTime(startDate);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		cal.set(Calendar.DATE, 1);
		return new Timestamp(sdf.parse(sdf.format(cal.getTime())).getTime());
	}

}
