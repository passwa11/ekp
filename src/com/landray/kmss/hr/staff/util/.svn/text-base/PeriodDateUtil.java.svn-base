package com.landray.kmss.hr.staff.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 统计报表入职期间工具
 * 
 * @author 潘永辉 2017-1-18
 * 
 */
public class PeriodDateUtil {

	/**
	 * 本月，开始与结束时间
	 * 
	 * @return
	 */
	public static Date[] getCurrentMonth() {
		Date[] dates = new Date[2];
		Calendar c1 = Calendar.getInstance();
		c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONDAY), c1
				.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		c1.set(Calendar.DAY_OF_MONTH, c1
				.getActualMinimum(Calendar.DAY_OF_MONTH));
		dates[0] = c1.getTime();

		Calendar c2 = Calendar.getInstance();
		c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONDAY), c2
				.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		c2.set(Calendar.DAY_OF_MONTH, c2
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		dates[1] = c2.getTime();
		return dates;
	}

	/**
	 * 上一月，开始与结束时间
	 * 
	 * @return
	 */
	public static Date[] getLastMonth() {
		Date[] dates = getCurrentMonth();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(dates[0]);
		c1.add(Calendar.MONDAY, -1);
		c2.setTime(dates[1]);
		c2.add(Calendar.MONDAY, -1);
		// 这里要重新设置当前月的最后一天
		c2.set(Calendar.DAY_OF_MONTH, c2
				.getActualMaximum(Calendar.DAY_OF_MONTH));

		dates[0] = c1.getTime();
		dates[1] = c2.getTime();
		return dates;
	}

	/**
	 * 本季，开始与结束时间
	 * 
	 * @return
	 */
	public static Date[] getCurrentSeason() {
		Date[] dates = getCurrentMonth();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		int currentMonth = c1.get(Calendar.MONTH) + 1;
		if (currentMonth >= 1 && currentMonth <= 3) {
            currentMonth = 0;
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            currentMonth = 3;
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            currentMonth = 4;
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            currentMonth = 9;
        }

		c1.setTime(dates[0]);
		c1.set(Calendar.MONDAY, currentMonth);
		c2.setTime(dates[1]);
		c2.set(Calendar.MONDAY, currentMonth + 2);
		// 这里要重新设置当前月的最后一天
		c2.set(Calendar.DAY_OF_MONTH, c2
				.getActualMaximum(Calendar.DAY_OF_MONTH));

		dates[0] = c1.getTime();
		dates[1] = c2.getTime();
		return dates;
	}

	/**
	 * 上一季，开始与结束时间
	 * 
	 * @return
	 */
	public static Date[] getLastSeason() {
		Date[] dates = getCurrentSeason();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(dates[0]);
		c1.add(Calendar.MONTH, -3);
		c2.setTime(dates[1]);
		c2.add(Calendar.MONTH, -3);
		// 这里要重新设置当前月的最后一天
		c2.set(Calendar.DAY_OF_MONTH, c2
				.getActualMaximum(Calendar.DAY_OF_MONTH));

		dates[0] = c1.getTime();
		dates[1] = c2.getTime();
		return dates;
	}

	/**
	 * 本年，开始与结束时间
	 * 
	 * @return
	 */
	public static Date[] getCurrentYear() {
		Date[] dates = getCurrentMonth();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(dates[0]);
		c1.set(Calendar.MONDAY, 0);
		c2.setTime(dates[1]);
		c2.set(Calendar.MONDAY, 11);
		// 这里要重新设置当前月的最后一天
		c2.set(Calendar.DAY_OF_MONTH, c2
				.getActualMaximum(Calendar.DAY_OF_MONTH));

		dates[0] = c1.getTime();
		dates[1] = c2.getTime();
		return dates;
	}

	/**
	 * 上一年，开始与结束时间
	 * 
	 * @return
	 */
	public static Date[] getLastYear() {
		Date[] dates = getCurrentYear();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(dates[0]);
		c1.add(Calendar.YEAR, -1);
		c2.setTime(dates[1]);
		c2.add(Calendar.YEAR, -1);
		// 这里要重新设置当前月的最后一天
		c2.set(Calendar.DAY_OF_MONTH, c2
				.getActualMaximum(Calendar.DAY_OF_MONTH));

		dates[0] = c1.getTime();
		dates[1] = c2.getTime();
		return dates;
	}
}
