package com.landray.kmss.km.calendar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.sso.client.util.StringUtil;

/**
 * 日程设置农历重复时的工具类
 * 
 * @author huangwq
 * 
 */
public class LunarRecurrenceUtil {
    //liql. Sonar代码质量检测 :    "Calendars"和"DateFormats"不应被声明为static 有线程安全隐患,因此注释掉
	//private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

	private static String getNextLunar(boolean isLeap, String lunarStr,
			String freq, int interval) {
		if (KmCalendarConstant.RECURRENCE_FREQ_YEARLY.equals(freq)) {
			return getNextLunarYear(isLeap, lunarStr, interval);
		} else if (KmCalendarConstant.RECURRENCE_FREQ_MONTHLY.equals(freq)) {
			return getNextLunarMonth(lunarStr, interval);
		}
		return null;
	}

	private static String getNextLunarMonth(String lunarStr, int interval) {
		String[] ss = lunarStr.split("-");
		return getNextLunarMonth(Integer.parseInt(ss[0]), Integer
				.parseInt(ss[1]), Integer.parseInt(ss[2]), interval);
	}

	private static String getNextLunarYear(boolean isLeap, String lunarStr,
			int interval) {
		String[] ss = lunarStr.split("-");
		int year = Integer.parseInt(ss[0]);
		int month = Integer.parseInt(ss[1]);
		int day = Integer.parseInt(ss[2]);
		if (!isLeap) {
			year += interval;
			return year + "-" + month + "-" + day;
		}
		boolean isMonthLeap = false;
		do {
			year += interval;
			int leapMonth = Lunar.getLunarLeapMonth(year);
			if (leapMonth == month - 1) {
				isMonthLeap = true;
			}

		} while (!isMonthLeap);

		return year + "-" + month + "-" + day;
	}

	/**
	 * 获取下一个周期的农历时间
	 * 
	 * @param year
	 *            农历年
	 * @param month
	 *            农历月
	 * @param day
	 *            农历日
	 * @param interval
	 *            循环间隔
	 * @return 下一个周期的农历时间，格式为yyyy-MM-dd
	 */
	private static String getNextLunarMonth(int year, int month, int day,
			int interval) {

		boolean isLunarYear = Lunar.isLeapYear(year);
		int monthCount = 12;
		if (isLunarYear) {
			monthCount = 13;
		}
		int nextYear = year;
		int nextMonth = month + interval;
		if (nextMonth > monthCount) {
			nextYear += 1;
			nextMonth = nextMonth % monthCount;
		}
		return nextYear + "-" + nextMonth + "-" + day;
	}

	private static String getNextLunarMonth(Lunar lunar, int interval) {
		int year = lunar.getLunarYear();
		int month = lunar.getLunarMonth();
		int day = lunar.getLunarDay();
		return getNextLunarMonth(year, month, day, interval);

	}

	private static DateBean genDateBean(Date nextSolar, String nextLunarStr) {
		DateBean dateBean = new DateBean();
		if (nextSolar == null) {
			dateBean.setLunar(nextLunarStr);
			dateBean.setSolar(null);
			dateBean.setAvailable(false);

		} else {
			dateBean.setLunar(nextLunarStr);
			dateBean.setSolar(nextSolar);
			dateBean.setAvailable(true);
		}
		return dateBean;
	}

	/**
	 * 获取以后执行时间的列表，列表中存放的是新历日期
	 * 
	 * @param start
	 *            开始时间
	 * @param freq
	 *            周期类型，有两种：每月(MONTHLY)，每年(YEARLY)
	 * @param interval
	 *            频率
	 * @param count
	 *            执行多少次后结束
	 * @param until
	 *            到某个时间结束
	 * @return
	 */
	public static List<Date> getExecueSolarDateList(Date start, String freq,
			int interval, int count, Date until) {
		List<DateBean> dateBeans = getExecueDateBeanList(start, freq, interval,
				count, until);
		List<Date> dates = new ArrayList<Date>();
		for (DateBean dateBean : dateBeans) {
			dates.add(dateBean.getSolar());
		}
		return dates;
	}

	public static List<Date> getExecueSolarDateList(Date start,
			String recurrenceStr) {
		List<DateBean> dateBeans = getExecueDateBeanList(start, recurrenceStr);
		List<Date> dates = new ArrayList<Date>();
		for (DateBean dateBean : dateBeans) {
			dates.add(dateBean.getSolar());
		}
		return dates;
	}

	public static List<String> getExecueLunarDateList(Date start,
			String recurrenceStr) {
		List<DateBean> dateBeans = getExecueDateBeanList(start, recurrenceStr);
		List<String> dates = new ArrayList<String>();
		for (DateBean dateBean : dateBeans) {
			dates.add(dateBean.getLunar());
		}
		return dates;
	}

	/**
	 * 获取以后执行时间的列表，列表中存放的是农历日期
	 * 
	 * @param start
	 *            开始时间
	 * @param freq
	 *            周期类型，有两种：每月(MONTHLY)，每年(YEARLY)
	 * @param interval
	 *            频率
	 * @param count
	 *            执行多少次后结束
	 * @param until
	 *            到某个时间结束
	 * @return
	 */
	public static List<String> getExecueLunarDateList(Date start, String freq,
			int interval, int count, Date until) {
		List<DateBean> dateBeans = getExecueDateBeanList(start, freq, interval,
				count, until);
		List<String> dates = new ArrayList<String>();
		for (DateBean dateBean : dateBeans) {
			dates.add(dateBean.getLunar());
		}
		return dates;
	}

	private static List<Date> getExecueSolarDateList2(Date start, String freq,
			int interval, int count, Date until) {
		Lunar lunar = new Lunar(start);
		String temp_lunar_str = lunar.getLunarStr();
		boolean isLeap = lunar.isLeap();
		List<Date> dates = new ArrayList<Date>();
		if (count == 0 && until == null) {
			count = 36;
		}
		if (count != 0) {
			for (int i = 0; i < count; i++) {
				String nextLunarStr = getNextLunar(isLeap, temp_lunar_str,
						freq, interval);
				Date nextSolar = Lunar.getSolarCalendar(nextLunarStr);
				nextSolar.setHours(start.getHours());
				nextSolar.setMinutes(start.getMinutes());
				nextSolar.setSeconds(start.getSeconds());
				if (nextSolar == null) {
					count--;
				} else {
					dates.add(nextSolar);
				}
			}
		} else {
			if (start.after(until)) {
				return null;
			} else {
				String nextLunarStr;
				Date nextSolar;
				while (true) {
					do {
						nextLunarStr = getNextLunar(isLeap, temp_lunar_str,
								freq, interval);
						nextSolar = Lunar.getSolarCalendar(nextLunarStr);
						temp_lunar_str = nextLunarStr;
					} while (nextSolar == null);
					if (!nextSolar.after(until)) {
						dates.add(nextSolar);
					} else {
						break;
					}
				}
			}
		}
		return dates;
	}

	/**
	 * 获取下次执行时间
	 * 
	 * @param start
	 *            当前执行时间
	 * @param freq
	 *            周期
	 * @param interval
	 *            频率
	 * @return 下次执行时间（新历时间）
	 */
	public static Date getNextExecueSolarDate(Date start, String freq,
			int interval) {
		DateBean dateBean = getNextExecueDateBean(start, freq, interval);
		return dateBean.getSolar();
	}

	/**
	 * 获取下次执行时间
	 * 
	 * @param start
	 *            当前执行时间
	 * @param freq
	 *            周期
	 * @param interval
	 *            频率
	 * @return 下次执行时间（农历时间）
	 */
	public static String getNextExecueLunarDate(Date start, String freq,
			int interval) {
		DateBean dateBean = getNextExecueDateBean(start, freq, interval);
		return dateBean.getLunar();
	}

	/**
	 * 获取下次执行时间
	 * 
	 * @param start
	 *            当前执行时间
	 * @param freq
	 *            周期
	 * @param interval
	 *            频率
	 * @return
	 */
	public static DateBean getNextExecueDateBean(Date start, String freq,
			int interval) {
		Lunar lunar = new Lunar(start);
		String temp_lunar_str = lunar.getLunarStr();
		String nextLunarStr;
		Date nextSolar;
		do {
			nextLunarStr = getNextLunar(lunar.isLeap(), temp_lunar_str, freq,
					interval);
			nextSolar = Lunar.getSolarCalendar(nextLunarStr);
			temp_lunar_str = nextLunarStr;
		} while (nextSolar == null);

		DateBean dateBean = genDateBean(nextSolar, nextLunarStr);

		return dateBean;
	}

	public static String getLastLunarDate(Date start, String freq,
			int interval, int count, Date until) {
		DateBean last = getLastDateBeanList(start, freq, interval, count, until);
		if (last == null) {
			return null;
		} else {
			return last.getLunar();
		}
	}

	public static Date getLastSolarDate(Date start, String freq, int interval,
			int count, Date until) {
		DateBean last = getLastDateBeanList(start, freq, interval, count, until);
		if (last == null) {
			return null;
		} else {
			return last.getSolar();
		}
	}

	public static String getLastLunarDate(Date start, String recurrenceStr) {
		DateBean last = getLastDateBeanList(start, recurrenceStr);
		if (last == null) {
			return null;
		} else {
			return last.getLunar();
		}
	}

	public static Date getLastSolarDate(Date start, String recurrenceStr) {
		DateBean last = getLastDateBeanList(start, recurrenceStr);
		if (last == null) {
			return start;
		} else {
			return last.getSolar();
		}
	}

	public static DateBean getLastDateBeanList(Date start, String recurrenceStr) {
		List<DateBean> dateBeans = getExecueDateBeanList(start, recurrenceStr);
		DateBean last = null;
		for (DateBean dateBean : dateBeans) {
			last = dateBean;
		}
		return last;
	}

	public static DateBean getLastDateBeanList(Date start, String freq,
			int interval, int count, Date until) {
		List<DateBean> dateBeans = getExecueDateBeanList(start, freq, interval,
				count, until);
		DateBean last = null;
		for (DateBean dateBean : dateBeans) {
			last = dateBean;
		}
		return last;
	}

	public static List<DateBean> getExecueDateBeanList(Date start,
			String recurrenceStr) {
		Map<String, String> result = Rfc2445Util
				.parseRecurrenceStr(recurrenceStr);
		String freq = result.get(KmCalendarConstant.RECURRENCE_FREQ);
		String intervalStr = result.get(KmCalendarConstant.RECURRENCE_INTERVAL);
		String countStr = result.get(KmCalendarConstant.RECURRENCE_COUNT);
		String untilStr = result.get(KmCalendarConstant.RECURRENCE_UNTIL);
		int interval = 1;
		int count = 0;
		if (StringUtil.isNotNull(intervalStr)) {
			interval = Integer.parseInt(intervalStr);
		}
		if (StringUtil.isNotNull(countStr)) {
			count = Integer.parseInt(countStr);
		}
		Date until = null;
		if (StringUtil.isNotNull(untilStr)) {
			// until = DateUtil.convertStringToDate(untilStr,
			// DateUtil.PATTERN_DATE);
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				until = format.parse(untilStr);
			} catch (ParseException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}

		}
		return getExecueDateBeanList(start, freq, interval, count, until);
	}

	public static List<DateBean> getExecueDateBeanList(Date start, String freq,
			int interval, int count, Date until) {
		Lunar lunar = new Lunar(start);
		String temp_lunar_str = lunar.getLunarStr();
		List<DateBean> dateBeans = new ArrayList<DateBean>();
		if (count == 0 && until == null) {
			if (freq.equals(KmCalendarConstant.RECURRENCE_FREQ_MONTHLY)) {
				count = 120;
			} else if (freq.equals(KmCalendarConstant.RECURRENCE_FREQ_YEARLY)) {
				count = 10;
			}
		}
		if (count != 0) {
			Calendar c = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			for (int i = 0; i < count - 1; i++) {
				String nextLunarStr = getNextLunar(lunar.isLeap(),
						temp_lunar_str, freq, interval);
				Date nextSolar = Lunar.getSolarCalendar(nextLunarStr);
				c.setTime(start);
				c2.setTime(nextSolar);
				c.set(Calendar.YEAR, c2.get(Calendar.YEAR));
				c.set(Calendar.MONTH, c2.get(Calendar.MONTH));
				c.set(Calendar.DAY_OF_MONTH, c2.get(Calendar.DAY_OF_MONTH));
				DateBean dateBean = genDateBean(c.getTime(), nextLunarStr);

				temp_lunar_str = nextLunarStr;
				if (!dateBean.isAvailable()) {
					i--;
				} else {
					if (start.getTime() != c.getTime().getTime()) {
                        dateBeans.add(dateBean);
                    }
				}
			}
		} else {
			if (start.after(until)) {
				return null;
			} else {
				String nextLunarStr;
				Date nextSolar;
				while (true) {
					do {
						nextLunarStr = getNextLunar(lunar.isLeap(),
								temp_lunar_str, freq, interval);
						int year = Integer.parseInt(nextLunarStr
								.substring(0, 4));
						if (year > 2028) {
							nextSolar = null;
							break;
						}
						nextSolar = Lunar.getSolarCalendar(nextLunarStr);
						temp_lunar_str = nextLunarStr;

					} while (nextSolar == null);
					if (nextSolar != null && !nextSolar.after(until)) {
						DateBean dateBean = genDateBean(nextSolar, nextLunarStr);
						dateBeans.add(dateBean);

					} else {
						break;
					}
				}
			}
		}
		return dateBeans;
	}

	// private static List<DateBean> Lunar(Date start, String freq, int
	// interval,
	// int count, Date until) {
	// Lunar lunar = new Lunar(start);
	// String temp_lunar_str = lunar.getLunarStr();
	//
	// List<DateBean> dateBeans = new ArrayList<DateBean>();
	// if (freq.equals(KmCalendarConstant.RECURRENCE_FREQ_MONTHLY)) {
	// if (count == 0 && until == null) {
	// count = 120;
	// }
	// if (count != 0) {
	// for (int i = 0; i < count; i++) {
	// String nextLunarStr = getNextLunarMonth(temp_lunar_str,
	// interval);
	// Date nextSolar = LunarSolarConveter
	// .getSolarCalendar(nextLunarStr);
	// DateBean dateBean = genDateBean(nextSolar, nextLunarStr);
	// dateBeans.add(dateBean);
	// temp_lunar_str = nextLunarStr;
	// if (!dateBean.isAvailable()) {
	// count--;
	// }
	//
	// }
	// } else {
	// if (start.after(until)) {
	// return null;
	// } else {
	// Date temp = start;
	// while (!temp.after(until)) {
	// String nextLunarStr = getNextLunarMonth(temp_lunar_str,
	// interval);
	// Date nextSolar = LunarSolarConveter
	// .getSolarCalendar(nextLunarStr);
	// DateBean dateBean = genDateBean(nextSolar, nextLunarStr);
	// dateBeans.add(dateBean);
	// temp = nextSolar;
	// }
	//
	// }
	// }
	// } else if (freq.equals(KmCalendarConstant.RECURRENCE_FREQ_YEARLY)) {
	// if (count == 0 && until == null) {
	// count = 20;
	// }
	// if (count != 0) {
	// for (int i = 0; i < count; i++) {
	// String nextLunarStr = getNextLunarYear(temp_lunar_str,
	// interval);
	// Date nextSolar = LunarSolarConveter
	// .getSolarCalendar(nextLunarStr);
	// DateBean dateBean = genDateBean(nextSolar, nextLunarStr);
	// dateBeans.add(dateBean);
	// temp_lunar_str = nextLunarStr;
	// if (!dateBean.isAvailable()) {
	// count--;
	// }
	//
	// }
	// } else {
	// if (start.after(until)) {
	// return null;
	// } else {
	// Date temp = start;
	// while (!temp.after(until)) {
	// String nextLunarStr = getNextLunarYear(temp_lunar_str,
	// interval);
	// Date nextSolar = LunarSolarConveter
	// .getSolarCalendar(nextLunarStr);
	// DateBean dateBean = genDateBean(nextSolar, nextLunarStr);
	// dateBeans.add(dateBean);
	// temp = nextSolar;
	// }
	// }
	// }
	// }
	// return dateBeans;
	// }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		testMonthNext();
		System.out.println("-------------------------");
		testYearNext();
		System.out.println("-------------------------");
		testMonth();
		System.out.println("-------------------------");
		testYear();
		System.out.println("-------------------------");
	}

	public static void testYearNext() {
		Calendar c = Calendar.getInstance();
		c.set(2017, 6, 27);

		Calendar c2 = Calendar.getInstance();
		c2.set(2026, 6, 2);
		DateBean bean = LunarRecurrenceUtil.getNextExecueDateBean(c.getTime(),
				"YEARLY", 1);

		System.out.println(bean.getLunar());
		System.out.println(bean.getSolar());
		System.out.println();

	}

	public static void testMonthNext() {
		Calendar c = Calendar.getInstance();
		c.set(2017, 6, 27);

		Calendar c2 = Calendar.getInstance();
		c2.set(2026, 6, 2);
		DateBean bean = LunarRecurrenceUtil.getNextExecueDateBean(c.getTime(),
				"MONTHLY", 1);

		System.out.println(bean.getLunar());
		System.out.println(bean.getSolar());
		System.out.println();

	}

	public static void testMonth() {
		Calendar c = Calendar.getInstance();
		c.set(2017, 6, 27);

		Calendar c2 = Calendar.getInstance();
		c2.set(2018, 6, 2);
		List<DateBean> list = LunarRecurrenceUtil.getExecueDateBeanList(c
				.getTime(), "MONTHLY", 1, 0, c2.getTime());
		for (DateBean bean : list) {
			System.out.println(bean.getLunar());
			System.out.println(bean.getSolar());
			System.out.println();
		}
	}

	public static void testYear() {
		Calendar c = Calendar.getInstance();
		c.set(2017, 6, 27);

		Calendar c2 = Calendar.getInstance();
		c2.set(2026, 6, 2);
		List<DateBean> list = LunarRecurrenceUtil.getExecueDateBeanList(c
				.getTime(), "YEARLY", 1, 0, c2.getTime());
		for (DateBean bean : list) {
			System.out.println(bean.getLunar());
			System.out.println(bean.getSolar());
			System.out.println();
		}
	}

}
