package com.landray.kmss.km.calendar.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.ReadableDateTime;
import org.joda.time.tz.CachedDateTimeZone;

import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.km.calendar.forms.KmCalendarMainForm;
import com.landray.kmss.km.calendar.ical.compat.jodatime.DateTimeIteratorFactory;
import com.landray.kmss.km.calendar.ical.compat.jodatime.LocalDateIteratorFactory;
import com.landray.kmss.util.StringUtil;

public class Rfc2445Util {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		try {
			// test();
			// testGetNextExcuteDate();
			testGetLastExcuteDate();

		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param freq
	 *            周期
	 * @param interval
	 *            频率
	 * @param end_type
	 *            结束类型
	 * @param count
	 *            执行次数
	 * @param until
	 *            执行到某个时间截止
	 * @param byday
	 *            当周期为每月时，该值为一周的某天的信息，比如3SA；当周期为每周时，该值为星期的信息，比如：SU,MO,TU
	 * @return
	 */
	public static String buildRecurrenceStr(String freq, String interval,
			String end_type, String count, String until, String byday) {
		if (StringUtil.isNotNull(until)) {
            until = until.replaceAll("-", "");
        }
		// FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-1
		// RRULE:FREQ=DAILY;INTERVAL=4
		// RRULE:FREQ=DAILY;COUNT=3;INTERVAL=3
		// RRULE:FREQ=DAILY;UNTIL=20131129;INTERVAL=3
		// RRULE:FREQ=WEEKLY;INTERVAL=2;BYDAY=TU,TH,FR
		// RRULE:FREQ=WEEKLY;UNTIL=20131220;BYDAY=MO,WE,FR
		// RRULE:FREQ=WEEKLY;COUNT=2;BYDAY=TU,TH
		// RRULE:FREQ=MONTHLY;COUNT=2;INTERVAL=2
		// RRULE:FREQ=MONTHLY;UNTIL=20131116;INTERVAL=2;BYDAY=3SA 第3个星期六
		// RRULE:FREQ=YEARLY;INTERVAL=2
		// RRULE:FREQ=MONTHLY;UNTIL=20160816;INTERVAL=3
		// RRULE:FREQ=WEEKLY;INTERVAL=2;BYDAY=SU,MO,TU,WE,TH,FR,SA
		String recurrenceStr = null;
		if (KmCalendarConstant.RECURRENCE_FREQ_NO.equals(freq)) {
			return null;
		} else {
			recurrenceStr = "FREQ=" + freq;
		}
		if (KmCalendarConstant.RECURRENCE_END_TYPE_COUNT.equals(end_type)) {
			recurrenceStr += ";COUNT=" + count;

		} else if (KmCalendarConstant.RECURRENCE_END_TYPE_UNTIL
				.equals(end_type)) {
			recurrenceStr += ";UNTIL=" + until;

		}
		if (StringUtil.isNotNull(interval)) {
			recurrenceStr += ";INTERVAL=" + interval;
		}
		if (StringUtil.isNotNull(byday)) {
			recurrenceStr += ";BYDAY=" + byday;
		}

		return recurrenceStr;
	}

	/**
	 * 构建rfc2445字符串
	 * 
	 * @param kmCalendarMainForm
	 *            form中需包含重复设置的信息，比如周期、频率等信息
	 * @return
	 */
	public static String buildRecurrenceStr(
			KmCalendarMainForm kmCalendarMainForm) {
		// FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-1
		// RRULE:FREQ=DAILY;INTERVAL=4
		// RRULE:FREQ=DAILY;COUNT=3;INTERVAL=3
		// RRULE:FREQ=DAILY;UNTIL=20131129;INTERVAL=3
		// RRULE:FREQ=WEEKLY;INTERVAL=2;BYDAY=TU,TH,FR
		// RRULE:FREQ=WEEKLY;UNTIL=20131220;BYDAY=MO,WE,FR
		// RRULE:FREQ=WEEKLY;COUNT=2;BYDAY=TU,TH
		// RRULE:FREQ=MONTHLY;COUNT=2;INTERVAL=2
		// RRULE:FREQ=MONTHLY;UNTIL=20131116;INTERVAL=2;BYDAY=3SA 第3个星期六
		// RRULE:FREQ=YEARLY;INTERVAL=2
		// RRULE:FREQ=MONTHLY;UNTIL=20160816;INTERVAL=3
		// RRULE:FREQ=WEEKLY;INTERVAL=2;BYDAY=SU,MO,TU,WE,TH,FR,SA
		String freq = kmCalendarMainForm.getRECURRENCE_FREQ();
		String recurrenceStr = null;
		if (KmCalendarConstant.RECURRENCE_FREQ_NO.equals(freq)) {
			return null;
		} else {
			recurrenceStr = "FREQ=" + freq;
		}
		String interval = kmCalendarMainForm.getRECURRENCE_INTERVAL();
		String end_type = kmCalendarMainForm.getRECURRENCE_END_TYPE();
		String byday = kmCalendarMainForm.getRECURRENCE_BYDAY();
		if (KmCalendarConstant.RECURRENCE_END_TYPE_COUNT.equals(end_type)) {
			recurrenceStr += ";COUNT="
					+ kmCalendarMainForm.getRECURRENCE_COUNT();

		} else if (KmCalendarConstant.RECURRENCE_END_TYPE_UNTIL
				.equals(end_type)) {
			recurrenceStr += ";UNTIL="
					+ kmCalendarMainForm.getRECURRENCE_UNTIL();

		}
		if (StringUtil.isNotNull(interval)) {
			recurrenceStr += ";INTERVAL=" + interval;
		}
		if (StringUtil.isNotNull(byday)) {
			recurrenceStr += ";BYDAY=" + byday;
		}

		return recurrenceStr;
	}

	public static void setRecurrenceValue(KmCalendarMainForm kmCalendarMainForm) {
		Map<String, String> paramsMap = getParamsMap(kmCalendarMainForm
				.getFdRecurrenceStr());

		String freq = paramsMap.get("FREQ");
		String interval = paramsMap.get("INTERVAL");
		String count = paramsMap.get("COUNT");
		String until = paramsMap.get("UNTIL");
		String byday = paramsMap.get("BYDAY");
		kmCalendarMainForm.setRECURRENCE_FREQ(freq);
		if (StringUtil.isNull(interval)) {
			kmCalendarMainForm.setRECURRENCE_INTERVAL("1");
		} else {
			kmCalendarMainForm.setRECURRENCE_INTERVAL(interval);
		}
		if (StringUtil.isNotNull(count)) {
			kmCalendarMainForm
					.setRECURRENCE_END_TYPE(KmCalendarConstant.RECURRENCE_END_TYPE_COUNT);
			kmCalendarMainForm.setRECURRENCE_COUNT(count);
		} else if (StringUtil.isNotNull(until)) {
			kmCalendarMainForm
					.setRECURRENCE_END_TYPE(KmCalendarConstant.RECURRENCE_END_TYPE_UNTIL);
			kmCalendarMainForm.setRECURRENCE_UNTIL(until);
		} else {
			kmCalendarMainForm
					.setRECURRENCE_END_TYPE(KmCalendarConstant.RECURRENCE_END_TYPE_NEVER);
		}
		if (StringUtil.isNotNull(byday)) {
			kmCalendarMainForm.setRECURRENCE_BYDAY(byday);
		}
		if (KmCalendarConstant.RECURRENCE_FREQ_MONTHLY.equals(freq)) {
			if (StringUtil.isNotNull(byday)) {
				kmCalendarMainForm
						.setRECURRENCE_MONTH_TYPE(KmCalendarConstant.RECURRENCE_FREQ_WEEKLY);
			} else {
				kmCalendarMainForm
						.setRECURRENCE_MONTH_TYPE(KmCalendarConstant.RECURRENCE_FREQ_MONTHLY);
			}
		} else if (KmCalendarConstant.RECURRENCE_FREQ_WEEKLY.equals(freq)) {
			kmCalendarMainForm.setRECURRENCE_WEEKS(byday);
		}
	}

	/**
	 * 修改指定参数的重复信息
	 * 
	 * @param recurrenceStr
	 *            重复信息
	 * @param param
	 *            重复参数
	 * @param value
	 *            修改值
	 * @return
	 */
	public static String setRecurrenceParam(String recurrenceStr, String param,
			String value) {
		Pattern p = Pattern.compile("(;?" + param + "=)[^;]*");
		Matcher m = p.matcher(recurrenceStr);
		if (m.find() && m.group(0) != null && m.group(1) != null) {
			return recurrenceStr.replace(m.group(0), m.group(1) + value);
		} else {
			return recurrenceStr;
		}
	}

	public static Map<String, String> parseRecurrenceStr(String recurrenceStr) {
		// FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-1
		// RRULE:FREQ=DAILY;INTERVAL=4
		// RRULE:FREQ=DAILY;COUNT=3;INTERVAL=3
		// RRULE:FREQ=DAILY;UNTIL=20131129;INTERVAL=3
		// RRULE:FREQ=WEEKLY;INTERVAL=2;BYDAY=TU,TH,FR
		// RRULE:FREQ=WEEKLY;UNTIL=20131220;BYDAY=MO,WE,FR
		// RRULE:FREQ=WEEKLY;COUNT=2;BYDAY=TU,TH
		// RRULE:FREQ=MONTHLY;COUNT=2;INTERVAL=2
		// RRULE:FREQ=MONTHLY;UNTIL=20131116;INTERVAL=2;BYDAY=3SA 第3个星期六
		// RRULE:FREQ=YEARLY;INTERVAL=2
		// RRULE:FREQ=MONTHLY;UNTIL=20160816;INTERVAL=3
		// RRULE:FREQ=WEEKLY;INTERVAL=2;BYDAY=SU,MO,TU,WE,TH,FR,SA

		Map<String, String> result = new HashMap<String, String>();
		Map<String, String> paramsMap = getParamsMap(recurrenceStr);

		String freq = paramsMap.get("FREQ");
		String interval = paramsMap.get("INTERVAL");
		String count = paramsMap.get("COUNT");
		String until = paramsMap.get("UNTIL");
		String byday = paramsMap.get("BYDAY");
		result.put(KmCalendarConstant.RECURRENCE_FREQ, freq);
		if (StringUtil.isNull(interval)) {
			result.put(KmCalendarConstant.RECURRENCE_INTERVAL, "1");
		} else {
			result.put(KmCalendarConstant.RECURRENCE_INTERVAL, interval);
		}
		if (StringUtil.isNotNull(count)) {
			result.put(KmCalendarConstant.RECURRENCE_END_TYPE,
					KmCalendarConstant.RECURRENCE_END_TYPE_COUNT);
			result.put(KmCalendarConstant.RECURRENCE_COUNT, count);
		} else if (StringUtil.isNotNull(until)) {
			result.put(KmCalendarConstant.RECURRENCE_END_TYPE,
					KmCalendarConstant.RECURRENCE_END_TYPE_UNTIL);
			result.put(KmCalendarConstant.RECURRENCE_UNTIL, until);
		} else {
			result.put(KmCalendarConstant.RECURRENCE_END_TYPE,
					KmCalendarConstant.RECURRENCE_END_TYPE_NEVER);
		}
		if (StringUtil.isNotNull(byday)) {
			result.put(KmCalendarConstant.RECURRENCE_BYDAY, byday);
		}
		if (KmCalendarConstant.RECURRENCE_FREQ_MONTHLY.equals(freq)) {
			if (StringUtil.isNotNull(byday)) {
				result.put(KmCalendarConstant.RECURRENCE_MONTH_TYPE,
						KmCalendarConstant.RECURRENCE_MONTH_TYPE_WEEK);
			} else {
				result.put(KmCalendarConstant.RECURRENCE_MONTH_TYPE,
						KmCalendarConstant.RECURRENCE_MONTH_TYPE_MONTH);
			}
		} else if (KmCalendarConstant.RECURRENCE_FREQ_WEEKLY.equals(freq)) {
			result.put(KmCalendarConstant.RECURRENCE_WEEKS, byday);
		}
		return result;

	}

	private static Map<String, String> getParamsMap(String recurrenceStr) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		String[] params = recurrenceStr.split(";");
		for (String param : params) {
			String[] arrays = param.split("=");
			paramsMap.put(arrays[0], arrays[1]);

		}
		return paramsMap;
	}

	public static List<Date> getExcuteDateList(String recurrenceStr,
			Date start, int count) throws ParseException {
		List<Date> excuteDateList = new ArrayList<Date>();
		if(StringUtil.isNotNull(recurrenceStr)) { //判断是否是重复时间
			if (!recurrenceStr.startsWith("RRULE:")) {
				recurrenceStr = "RRULE:" + recurrenceStr;
				recurrenceStr = replaceUntil(recurrenceStr);
				// DateTimeZone zone = CachedDateTimeZone.getDefault();
				DateTimeZone zone = CachedDateTimeZone.forTimeZone(TimeZone
						.getDefault());
				ReadableDateTime dateTime = new DateTime(start);
				for (DateTime date : DateTimeIteratorFactory.createDateTimeIterable(
						recurrenceStr, dateTime, zone, true)) {
					// System.out.println(date.getMillis());
					// System.out.println(new Date(date.getMillis()));
					excuteDateList.add(new Date(date.getMillis()));
					count--;
					if (count == 0) {
						break;
					}
				}
			}
		}
		return excuteDateList;
	}

	public static List<Date> getExcuteDateList(String recurrenceStr,
			Date start, Long eventDelta, Date rangeStart, Date rangeEnd)
			throws ParseException {
		List<Date> excuteDateList = new ArrayList<Date>();
		if(StringUtil.isNotNull(recurrenceStr)) {
			if (!recurrenceStr.startsWith("RRULE:")) {
				recurrenceStr = "RRULE:" + recurrenceStr;
			}
		recurrenceStr = replaceUntil(recurrenceStr);
		// DateTimeZone zone = CachedDateTimeZone.getDefault();
		DateTimeZone zone = CachedDateTimeZone.forTimeZone(TimeZone
				.getDefault());

		ReadableDateTime dateTime = new DateTime(start);

		for (DateTime date : DateTimeIteratorFactory.createDateTimeIterable(
				recurrenceStr, dateTime, zone, true)) {
			// System.out.println(date.getMillis());
			// System.out.println(new Date(date.getMillis()));

			long docStartTime = new Date(date.getMillis()).getTime();
			long docFinishTime = docStartTime + eventDelta;
			if (docStartTime < rangeEnd.getTime()
					&& docFinishTime >= rangeStart.getTime()) {
				excuteDateList.add(new Date(date.getMillis()));
			}
			if (docStartTime >= rangeEnd.getTime()) {
				break;
			}
		}
		}
		return excuteDateList;
	}

	public static List<Date> getExcuteDateList(String recurrenceStr, Date start)
			throws ParseException {
		if (!recurrenceStr.startsWith("RRULE:")) {
			recurrenceStr = "RRULE:" + recurrenceStr;
		}
		recurrenceStr = replaceUntil(recurrenceStr);
		List<Date> excuteDateList = new ArrayList<Date>();
		ReadableDateTime dateTime = new DateTime(start);
		// DateTimeZone zone = CachedDateTimeZone.getDefault();
		DateTimeZone zone = CachedDateTimeZone.UTC;
		int count = 0;
		for (DateTime date : DateTimeIteratorFactory.createDateTimeIterable(
				recurrenceStr, dateTime, zone, true)) {
			// System.out.println(date.getMillis());
			// System.out.println(new Date(date.getMillis()));
			excuteDateList.add(new Date(date.getMillis()));
			count++;
		}
		return excuteDateList;
	}

	public static Date getLastedExecuteDate(String recurrenceStr, Date start)
			throws ParseException {
		Map<String, String> map = parseRecurrenceStr(recurrenceStr);
		String endType = map.get(KmCalendarConstant.RECURRENCE_END_TYPE);
		if (KmCalendarConstant.RECURRENCE_END_TYPE_NEVER.equals(endType)) {
			Calendar c = Calendar.getInstance();
			c.set(2500, 11, 1);
			return c.getTime();
		}
		if (!recurrenceStr.startsWith("RRULE:")) {
			recurrenceStr = "RRULE:" + recurrenceStr;
		}
		recurrenceStr = replaceUntil(recurrenceStr);
		ReadableDateTime dateTime = new DateTime(start);
		// DateTimeZone zone = CachedDateTimeZone.getDefault();
		DateTimeZone zone = CachedDateTimeZone.UTC;
		DateTime result = null;
		for (DateTime date : DateTimeIteratorFactory.createDateTimeIterable(
				recurrenceStr, dateTime, zone, true)) {
			result = date;
		}
		if (result == null) {
			return null;
		} else {
			return new Date(result.getMillis());
		}

	}

	public static Date getNextExcuteDate(String recurrenceStr, Date start)
			throws ParseException {
		ReadableDateTime dateTime = new DateTime(start);
		// DateTimeZone zone = CachedDateTimeZone.getDefault();
		DateTimeZone zone = CachedDateTimeZone.UTC;
		if (!recurrenceStr.startsWith("RRULE:")) {
			recurrenceStr = "RRULE:" + recurrenceStr;
		}
		recurrenceStr = replaceUntil(recurrenceStr);
		int i = 0;
		for (DateTime date : DateTimeIteratorFactory.createDateTimeIterable(
				recurrenceStr, dateTime, zone, true)) {
			i++;
			if (i == 2) {
				// System.out.println(new Date(date.getMillis()));
				return new Date(date.getMillis());
			}
			// System.out.println(date.getMillis());
			// System.out.println(new Date(date.getMillis()));

		}
		return null;
	}

	public static String replaceUntil(String recurrenceStr) {
		Pattern pattern = Pattern.compile("UNTIL=(\\d+)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(recurrenceStr);
		if (matcher.find()) {
			// matcher.appendReplacement(matcher.group(1),
			// matcher.group(1)+"T160000Z");
			return matcher.replaceFirst(matcher.group() + "T160000Z");
		}
		return recurrenceStr;

	}

	public static void testGetLastExcuteDate() throws ParseException {
		String s = "FREQ=DAILY;UNTIL=20150722;INTERVAL=1";
		// s = "UNTIL=20150722;";

		Pattern pattern = Pattern.compile("UNTIL=(\\d+)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(s);
		// 替换第一个符合正则的数据
		StringBuffer buffer = new StringBuffer();
		buffer.append("T160000Z");
		String tmp = "";
		while (matcher.find()) {
			System.out.println(matcher.group(1));
			// matcher.appendReplacement(matcher.group(1),
			// matcher.group(1)+"T160000Z");
			tmp = matcher.replaceFirst(matcher.group() + "T160000Z");
			// matcher.appendReplacement(sbr, "Java");
		}
		System.out.println(tmp);

		Pattern pattern2 = Pattern.compile("\\d{2}");
		Matcher matcher2 = pattern.matcher("11 Hello World,正则表达式 Hello World"); // 替换第一个符合正则的数据
		System.out.println(matcher2.replaceAll("Java"));

		Pattern p = Pattern.compile("(\\d{3,5})([a-z]{2})");
		String s2 = "123aa-34345bb-234cc-00";
		Matcher m = p.matcher(s2);
		System.out.println(m.groupCount());// 2组
		while (m.find()) {
			System.out.println(m.group(1));// 数字字母都有
			// p(m.group(1));//只有数字
			// p(m.group(2));//只有字母
		}

		Calendar c = Calendar.getInstance();
		c.set(2015, 6, 13, 16, 1, 0);

		System.out.println(getLastedExecuteDate(s, c.getTime()));

	}

	public static void testGetNextExcuteDate() throws ParseException {
		Calendar c = Calendar.getInstance();
		c.set(2014, 4, 13);
		// LocalDate start = new LocalDate(2014, 4, 13);
		LocalDate start = new LocalDate(c.getTime());
		// start = new LocalDate(new Date().getTime());
		// Every friday the thirteenth.
		String ical = "RRULE:FREQ=MONTHLY"
		// + ";BYDAY=FR" // every Friday
				// + ";BYMONTHDAY=2" // that occurs on the 13th of the month
				// + ";COUNT=3"; // stop after 13 occurences
				// + ";UNTIL=20130101";
				+ ";";
		Date date = getNextExcuteDate(ical, c.getTime());
		if (date != null) {
			System.out.println(date);
		}
	}

	public static void test() throws ParseException {
		// TODO 自动生成的方法存根
		Calendar c = Calendar.getInstance();
		c.set(2014, 4, 13);
		// LocalDate start = new LocalDate(2014, 4, 13);
		LocalDate start = new LocalDate(c.getTime());
		// start = new LocalDate(new Date().getTime());
		// Every friday the thirteenth.
		String ical = "RRULE:FREQ=MONTHLY"
		// + ";BYDAY=FR" // every Friday
				// + ";BYMONTHDAY=2" // that occurs on the 13th of the month
				// + ";COUNT=3"; // stop after 13 occurences
				// + ";UNTIL=20130101";
				+ ";";
		ReadableDateTime dateTime = new DateTime(c.getTimeInMillis());
		DateTimeZone zone = CachedDateTimeZone.getDefault();
		// DateTimeIteratorFactory.createDateTimeIterable(ical, dateTime,
		// CachedDateTimeZone.UTC, true);

		for (DateTime date : DateTimeIteratorFactory.createDateTimeIterable(
				ical, dateTime, zone, true)) {
			System.out.println(date.getMillis());
			System.out.println(new Date(date.getMillis()));
		}
		// Print out each date in the series.
		for (LocalDate date : LocalDateIteratorFactory.createLocalDateIterable(
				ical, start, true)) {
			System.out.println(date);
		}
	}

	/**
	 * 把rfc2445的字符串转换为quartz的cronExpression
	 * 
	 * @param date
	 *            开始时间
	 * @param recurrenceStr
	 * @return
	 */
	public static String getCronExpression(Date date, String recurrenceStr) {
		Map<String, String> result = parseRecurrenceStr(recurrenceStr);
		String freq = result.get(KmCalendarConstant.RECURRENCE_FREQ);
		String interval_str = result
				.get(KmCalendarConstant.RECURRENCE_INTERVAL);
		int interval = 0;
		if (StringUtil.isNotNull(interval_str)) {
			interval = Integer.parseInt(interval_str);
		}
		String byDay = result.get(KmCalendarConstant.RECURRENCE_BYDAY);
		return getCronExpression(date, freq, interval, byDay);
	}

	/**
	 * 构建cron表达式
	 * 
	 * @param date
	 *            开始时间
	 * @param freq
	 *            周期
	 * @param interval
	 *            频率
	 * @param byDay
	 * @return
	 */
	public static String getCronExpression(Date date, String freq,
			int interval, String byDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String year = c.get(Calendar.YEAR) + "";
		String month = (c.get(Calendar.MONTH) + 1) + "";
		String day = c.get(Calendar.DAY_OF_MONTH) + "";
		String hour = c.get(Calendar.HOUR_OF_DAY) + "";
		String minute = c.get(Calendar.MINUTE) + "";
		String second = c.get(Calendar.SECOND) + "";

		String timeExpression = second + " " + minute + " " + hour + " ";
		String expression = timeExpression;
		// 每天：2013-11-20 12:20:58 每隔8天
		// 58 20 12 20/8 * *
		if (freq.equals(KmCalendarConstant.RECURRENCE_FREQ_DAILY)) {
			// 每天：2013-11-20 12:20:58 每隔8天
			// 58 20 12 20/8 * *
			String dayExpression = "";
			if (interval > 0) {
				dayExpression = day + "/" + interval;
			} else {
				dayExpression = day;
			}
			expression += dayExpression + " * ?";
		} else if (freq.equals(KmCalendarConstant.RECURRENCE_FREQ_WEEKLY)) {
			// 每周：2013-11-20 12:20:58 每隔2周
			// 58 20 12 ? * MON,FRI/14
			String weekExpression = buildQuartzWeeksStr(byDay);
			if (interval > 0) {
				weekExpression += "/" + interval * 7;
			}
			expression += "? * " + weekExpression;
		} else if (freq.equals(KmCalendarConstant.RECURRENCE_FREQ_MONTHLY)) {
			// 每月：2013-11-20 12:20:58 每隔2月
			// 58 20 12 20 11/2 *
			// 每月：2013-11-20 12:20:58 每隔2月 每月第三个周五
			// 58 20 12 ? 11/2 6#3
			String monthExpression = month;
			if (interval > 0) {
				monthExpression += "/" + interval;
			}
			if (StringUtil.isNull(byDay)) {
				expression += day + " " + monthExpression + " ?";
			} else {
				expression += "? " + monthExpression + " "
						+ buildQuartzMonthWeekStr(byDay);
			}

		} else if (freq.equals(KmCalendarConstant.RECURRENCE_FREQ_YEARLY)) {
			// 每年：2013-11-20 12:20:58 每隔2年
			// 58 20 12 20 11 ? 2013/2
			String yearExpression = "*";
			if (interval > 0) {
				yearExpression += "/" + interval;
			}
			expression += day + " " + month + " ? " + yearExpression;
		}

		return expression;
	}

	private static String buildQuartzMonthWeekStr(String byDay) {
		// 每月第三个周五， 3FR 转化为 6#3
		byDay = byDay.trim();
		if (byDay.length() != 3) {
			System.out.println("数据格式不正确，无法转换为一周的某天：" + byDay);
			return null;
		}
		String weekOfMonth = byDay.substring(0, 1);
		int numberOfWeek = 0;
		String week = byDay.substring(1);
		// SU,MO,TU,WE,TH,FR,SA
		if ("SU".equals(week)) {
			numberOfWeek = 1;
		} else if ("MO".equals(week)) {
			numberOfWeek = 2;
		} else if ("TU".equals(week)) {
			numberOfWeek = 3;
		} else if ("WE".equals(week)) {
			numberOfWeek = 4;
		} else if ("TH".equals(week)) {
			numberOfWeek = 5;
		} else if ("FR".equals(week)) {
			numberOfWeek = 6;
		} else if ("SA".equals(week)) {
			numberOfWeek = 7;
		}
		return numberOfWeek + "#" + weekOfMonth;
	}

	private static String buildQuartzWeeksStr(String byDay) {
		// SU,MO,TU,WE,TH,FR,SA
		// SUN,MON,TUE,WED,THU,FRI,SAT
		String result = byDay.replaceAll("SU", "SUN").replaceAll("MO", "MON")
				.replaceAll("TU", "TUE").replaceAll("WE", "WED").replaceAll(
						"TH", "THU").replaceAll("FR", "FRI").replaceAll("SA",
						"SAT");
		return result;
	}
}
