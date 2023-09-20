/**
 * 
 */
package com.landray.kmss.hr.staff.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author 陈经纬
 * @creation 2017-1-1
 */
public class HrStaffDateUtil {

	// 获得本周一0点时间
	public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal
				.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	// 获得本周日24点时间
	public static Date getTimesWeeknight() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesWeekmorning());
		cal.add(Calendar.DAY_OF_WEEK, Calendar.MONDAY + 4);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	// 获得本月第一天0点时间
	public static Date getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal
				.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal
				.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	// 获得本月最后一天24点时间
	public static Date getTimesMonthnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal
				.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	// 下月第一天
	public static Date getTimeLastMonthFirst() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY) + 1, cal
				.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal
				.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	// 下月最后一天
	public static Date getTimeLastMonthLast() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY) + 1, cal
				.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	// 季的第一天
	public static Date getFirstDayOfQuarter() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int month = getQuarterInMonth(calendar.get(Calendar.MONDAY) + 1, true);
		calendar.set(calendar.get(Calendar.YEAR), month - 1, calendar
				.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	// 季度末
	public static Date getLastDayOfQuarter() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int month = getQuarterInMonth(calendar.get(Calendar.MONDAY) + 1, false);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	// 季度一年四季， 第一季度：1月-3月， 第二季度：4月-6月， 第三季度：7月-9月， 第四季度：10月-12月
	private static int getQuarterInMonth(int month, boolean isQuarterStart) {
		int months[] = { 1, 4, 7, 10 };
		if (!isQuarterStart) {
			months = new int[] { 3, 6, 9, 12 };
		}
		if (month >= 1 && month <= 3) {
            return months[0];
        } else if (month >= 4 && month <= 6) {
            return months[1];
        } else if (month >= 7 && month <= 9) {
            return months[2];
        } else {
            return months[3];
        }
	}

	public static int dateToFdBirthdayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int fdBirthdayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		return fdBirthdayOfYear;
	}

	public static Date getToday() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String first = formatter.format(getFirstDayOfQuarter());
		String last = formatter.format(getLastDayOfQuarter());

		System.out.println(first);
		System.out.println(last);
		// System.out.println(judgeDay());
		// System.out.println(formatter.format(getTimesWeekmorning()));
		// System.out.println(formatter.format(getTimesWeeknight()));
		// System.out.println(formatter.format(getTimesMonthmorning()));
		// System.out.println(formatter.format(getTimesMonthnight()));
		// System.out.println(formatter.format(getTimesJanuaryfirst()));
		// System.out.println(formatter.format(getTimesJanuaryLast()));
		// System.out.println(formatter.format(getTimesFebruaryFirst()));
		// System.out.println(formatter.format(getTimesFebruaryLast()));
		// System.out.println(formatter.format(getFirstDayOfQuarter()));
		// System.out.println(formatter.format(getLastDayOfQuarter()));

	}

	/**
	 * 取两个时间相差的年月日
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int[] getNeturalAge(Date beginDate, Date endDate) {
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);

		int diffYears = 0, diffMonths, diffDays;
		int dayOfBirth = begin.get(Calendar.DAY_OF_MONTH);
		int dayOfNow = end.get(Calendar.DAY_OF_MONTH);
		if (dayOfBirth <= dayOfNow) {
			diffMonths = getMonthsOfAge(begin, end);
			diffDays = dayOfNow - dayOfBirth;
			if (diffMonths == 0) {
                diffDays++;
            }
		} else {
			if (isEndOfMonth(begin)) {
				if (isEndOfMonth(end)) {
					diffMonths = getMonthsOfAge(begin, end);
					diffDays = 0;
				} else {
					end.add(Calendar.MONTH, -1);
					diffMonths = getMonthsOfAge(begin, end);
					diffDays = dayOfNow + 1;
				}
			} else {
				if (isEndOfMonth(end)) {
					diffMonths = getMonthsOfAge(begin, end);
					diffDays = 0;
				} else {
					end.add(Calendar.MONTH, -1);// 上个月
					diffMonths = getMonthsOfAge(begin, end);
					// 获取上个月最大的一天
					int maxDayOfLastMonth = end
							.getActualMaximum(Calendar.DAY_OF_MONTH);
					if (maxDayOfLastMonth > dayOfBirth) {
						diffDays = maxDayOfLastMonth - dayOfBirth + dayOfNow;
					} else {
						diffDays = dayOfNow;
					}
				}
			}
		}
		// 计算月份时，没有考虑年
		diffYears = diffMonths / 12;
		diffMonths = diffMonths % 12;
		return new int[] { diffYears, diffMonths, diffDays };
	}

	/**
	 * 结束时间减去subMonth月，取两个时间相差的年月日
	 * @param beginDate
	 * @param endDate
	 * @param subMonth
	 * @return
	 */
	public static int[] getNeturalAge(Date beginDate, Date endDate,Integer subMonth) {
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		if(subMonth!=null) {
			end.add(Calendar.MONTH, subMonth);
		}
		int[] _time = getNeturalAge(beginDate,end.getTime());
		_time[0] = _time[0]>0?_time[0]:0;
		_time[1] = _time[1]>0?_time[1]:0;
		return _time;
	}
	
	/**
	 * 获取两个日历的月份之差
	 * 
	 * @param calendarBirth
	 * @param calendarNow
	 * @return
	 */
	public static int getMonthsOfAge(Calendar begin, Calendar end) {
		return (end.get(Calendar.YEAR) - begin.get(Calendar.YEAR)) * 12
				+ end.get(Calendar.MONTH) - begin.get(Calendar.MONTH);
	}

	/**
	 * 判断这一天是否是月底
	 * 
	 * @param calendar
	 * @return
	 */
	public static boolean isEndOfMonth(Calendar calendar) {
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		if (dayOfMonth == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            return true;
        }
		return false;
	}

	public static String getCronExpression(Calendar calendar) {
		StringBuffer sb = new StringBuffer();
		sb.append("0 ");
		sb.append(calendar.get(Calendar.MINUTE) + " ");
		sb.append(calendar.get(Calendar.HOUR_OF_DAY) + " ");
		sb.append(calendar.get(Calendar.DAY_OF_MONTH) + " ");
		sb.append((calendar.get(Calendar.MONTH) + 1) + " ");
		sb.append("? ");
		sb.append(calendar.get(Calendar.YEAR));
		return sb.toString();
	}

	/**
	 * 判断日期类型 YYYY-MM-DD
	 * @param date
	 * @return
	 */
	public static boolean isDate(String date){
		Pattern p = Pattern.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)");
		return p.matcher(date).matches();
	}
}
