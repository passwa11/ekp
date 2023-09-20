package com.landray.kmss.util;

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
import org.joda.time.ReadableDateTime;
import org.joda.time.tz.CachedDateTimeZone;

import com.landray.kmss.util.ical.compat.jodatime.DateTimeIteratorFactory;

/**
 * 重复信息工具(可用于日程重复、周期性会议等场景..)
 */
public class RecurrenceUtil {

	public static Date lastedDate = null;

	static {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2050, 11, 1);
		lastedDate = calendar.getTime();
	}

	/**
	 * 重复类型: 不重复、按天重复、按周重复、按月重复、按年重复
	 */
	public enum RecurrenceFreq {
		NO, DAILY, WEEKLY, MONTHLY, YEARLY
	}
	
	/**
	 * 结束条件: 从不、按次数、直到某天
	 */
	public enum RecurrenceEndType {
		NEVER, COUNT, UNTIL
	}

	public enum RecurrenceMonthType {
		WEEK, MONTH
	}

	/**
	 * 生成重复信息字符串
	 * @param params:{ FREQ:重复类型， INTERVAL:重复频率, ENDTYPE:结束条件, COUNT:执行次数, UNTIL:直到某天结束, BYDAY:周、月重复方式 }
	 */
	public static String buildRecurrenceStr(Map<String, String> params) {
		String recurrenceStr = null;
		if (StringUtil.isNull(params.get("FREQ")) 
				|| RecurrenceFreq.NO.name().equals(params.get("FREQ"))) {
			return recurrenceStr;
		}else{
			recurrenceStr = "FREQ=" + params.get("FREQ");
		}
		if (RecurrenceEndType.COUNT.name().equals(params.get("ENDTYPE"))) {
			recurrenceStr += ";COUNT=" + params.get("COUNT");
		} else if (RecurrenceEndType.UNTIL.name()
				.equals(params.get("ENDTYPE"))) {
			recurrenceStr += ";UNTIL="
					+ params.get("UNTIL").replaceAll("-", "");
		}
		if (StringUtil.isNotNull(params.get("INTERVAL"))) {
			recurrenceStr += ";INTERVAL=" + params.get("INTERVAL");
		}
		if (StringUtil.isNotNull(params.get("BYDAY"))) {
			recurrenceStr += ";BYDAY=" + params.get("BYDAY");
		}
		return recurrenceStr;
	}
	
	/**
	 * 解析重复信息字符串
	 * @param recurrenceStr 重复信息字符串
	 */
	public static Map<String, String> parseRecurrenceStr(String recurrenceStr) {
		Map<String, String> result = new HashMap<String, String>();
		Map<String, String> paramsMap = getParamsMap(recurrenceStr);
		String freq = paramsMap.get("FREQ");
		result.put("FREQ", paramsMap.get("FREQ"));
		if (StringUtil.isNotNull(paramsMap.get("INTERVAL"))) {
			result.put("INTERVAL", paramsMap.get("INTERVAL"));
		} else {
			result.put("INTERVAL", "1");
		}
		if (StringUtil.isNotNull(paramsMap.get("COUNT"))) {
			result.put("ENDTYPE", RecurrenceEndType.COUNT.name());
			result.put("COUNT", paramsMap.get("COUNT"));
		} else if (StringUtil.isNotNull(paramsMap.get("UNTIL"))) {
			result.put("ENDTYPE", RecurrenceEndType.UNTIL.name());
			result.put("UNTIL", paramsMap.get("UNTIL"));
		} else {
			result.put("ENDTYPE", RecurrenceEndType.NEVER.name());
		}
		if(StringUtil.isNotNull(paramsMap.get("BYDAY"))){
			result.put("BYDAY", paramsMap.get("BYDAY"));
		}
		if (RecurrenceFreq.MONTHLY.name().equals(freq)) {
			if (StringUtil.isNotNull(paramsMap.get("BYDAY"))) {
				result.put("MONTHTYPE", RecurrenceMonthType.WEEK.name());
			} else {
				result.put("MONTHTYPE", RecurrenceMonthType.MONTH.name());
			}
		}
		return result;
	}



	/**
	 * 根据重复信息获取指定范围内所有符合条件的日期
	 * 
	 * @param recurrenceStr
	 *            重复信息字符串
	 * @param baseDate
	 *            基准时间
	 * @param rangeStart
	 *            范围开始时间
	 * @param rangeEnd
	 *            范围结束时间
	 */
	public static List<Date> getExcuteDateList(String recurrenceStr,
			Date baseDate, Date rangeStart, Date rangeEnd)
			throws ParseException {
		List<Date> excuteDateList = new ArrayList<Date>();
		recurrenceStr = ensureRecurrenceStr(recurrenceStr);
		DateTimeZone zone = CachedDateTimeZone.forTimeZone(TimeZone
				.getDefault());
		ReadableDateTime dateTime = new DateTime(baseDate);
		for (DateTime date : DateTimeIteratorFactory.createDateTimeIterable(
				recurrenceStr, dateTime, zone, true)) {
			long curTime = new Date(date.getMillis()).getTime();
			if (curTime < rangeEnd.getTime()
					&& curTime >= rangeStart.getTime()) {
				excuteDateList.add(new Date(date.getMillis()));
			}
			if (curTime >= rangeEnd.getTime()
					|| curTime > lastedDate.getTime()) {
				break;
			}
		}
		return excuteDateList;
	}

	/**
	 * 获取重复日程的最后日期
	 * 
	 * @param recurrenceStr
	 *            重复信息字符串
	 * @param start
	 *            开始时间
	 * @throws ParseException
	 */
	public static Date getLastedExecuteDate(String recurrenceStr, Date start)
			throws ParseException {
		Map<String, String> paramsMap = parseRecurrenceStr(recurrenceStr);
		String endType = paramsMap.get("ENDTYPE");
		if (RecurrenceEndType.NEVER.name().equals(endType)) {
			return (Date) lastedDate.clone();
		}
		recurrenceStr = ensureRecurrenceStr(recurrenceStr);
		ReadableDateTime dateTime = new DateTime(start);
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

	/**
	 * 获取下一个开始时间
	 * 
	 * @param recurrenceStr : 重复信息字符串
	 * @param start  : 开始时间
	 */
	public static Date getNextExecuteDate(String recurrenceStr, Date start)
			throws ParseException {
		Date baseDate = start;
		return getNextExecuteDate(recurrenceStr, start, baseDate);
	}

	/**
	 * 获取下一个开始时间
	 * 
	 * @param recurrenceStr : 重复信息字符串
	 * @param start  : 开始时间
	 * @param baseDate  : 基准时间（取哪一天的下一个开始时间）
	 */
	public static Date getNextExecuteDate(String recurrenceStr, Date start,
			Date baseDate)
			throws ParseException {
		recurrenceStr = ensureRecurrenceStr(recurrenceStr);
		ReadableDateTime dateTime = new DateTime(start);
		DateTimeZone zone = CachedDateTimeZone.forTimeZone(TimeZone
				.getDefault());
		DateTime result = null;
		int curIndex = -1;
		for (DateTime date : DateTimeIteratorFactory.createDateTimeIterable(
				recurrenceStr, dateTime, zone, true)) {
			if (date.getMillis() >= baseDate.getTime()) {
				curIndex++;
			}
			result = date;
			if (curIndex == 1) {
				break;
			}
		}
		if (result == null || curIndex < 1) {
			return null;
		} else {
			return new Date(result.getMillis());
		}
	}

	/**
	 * 获取指定范围内重复日程执行次数
	 * 
	 * @param recurrenceStr
	 *            重复信息字符串
	 * @param baseDate
	 *            基准时间
	 * @param rangeStart
	 *            范围开始时间
	 * @param rangeEnd
	 *            范围结束时间
	 */
	public static int getExecuteCount(String recurrenceStr, Date baseDate,
			Date rangeStart, Date rangeEnd) throws ParseException {
		Map<String, String> paramsMap = parseRecurrenceStr(recurrenceStr);
		String endType = paramsMap.get("ENDTYPE");
		if (RecurrenceEndType.NEVER.name().equals(endType)) {
			return Integer.MAX_VALUE;
		}
		recurrenceStr = ensureRecurrenceStr(recurrenceStr);
		ReadableDateTime dateTime = new DateTime(baseDate);
		DateTimeZone zone = CachedDateTimeZone.UTC;
		int count = 0;
		for (DateTime date : DateTimeIteratorFactory.createDateTimeIterable(
				recurrenceStr, dateTime, zone, true)) {
			long curTime = new Date(date.getMillis()).getTime();
			if (curTime <= rangeEnd.getTime()
					&& curTime >= rangeStart.getTime()) {
				count++;
			}
			if (curTime >= rangeEnd.getTime()) {
				break;
			}
		}
		return count;
	}

	/**
	 * 根据重复规则获得文字信息
	 * 
	 * @param recurrenceStr
	 *            重复规则
	 * @param startTime
	 *            规则开始日期
	 * @return
	 */
	public static Map<String, String> getRepeatInfo(String recurrenceStr,
			Date startTime) {
		Calendar calendar = null;
		if(startTime !=null) {
			calendar = Calendar.getInstance();
			calendar.setTime(startTime);
		}
		Map<String, String> infos = new HashMap<>();
		Map<String, String> params = RecurrenceUtil
				.parseRecurrenceStr(recurrenceStr);
		String freq = null;
		String interval = null;
		String byDay = null;
		String count = null;
		String until = null;
		if (StringUtil.isNotNull(params.get("FREQ"))) {
			freq = params.get("FREQ");
		}
		if (StringUtil.isNotNull(params.get("INTERVAL"))) {
			interval = params.get("INTERVAL");
		}
		if (StringUtil.isNotNull(params.get("BYDAY"))) {
			byDay = params.get("BYDAY");
		}
		if (RecurrenceEndType.COUNT.name().equals(params.get("ENDTYPE"))) {
			count = params.get("COUNT");
		} else if (RecurrenceEndType.UNTIL.name()
				.equals(params.get("ENDTYPE"))) {
			until = params.get("UNTIL").replaceAll("-", "");
		}
		if (StringUtil.isNotNull(freq)) {
			if (RecurrenceFreq.DAILY.name().equals(freq)) {
				infos.put("FREQ", "按天");
				infos.put("INTERVAL", "每" + interval + "天 ");
			} else if (RecurrenceFreq.WEEKLY.name().equals(freq)) {
				infos.put("FREQ", "按周");
				infos.put("INTERVAL", "每" + interval + "周 ");
			} else if (RecurrenceFreq.MONTHLY.name().equals(freq)) {
				infos.put("FREQ", "按月");
				if (StringUtil.isNull(byDay)  && null !=calendar) {
					int day = calendar.get(Calendar.DAY_OF_MONTH);
					infos.put("INTERVAL", "每" + interval + "月 " + day + "号");
				} else {
					infos.put("INTERVAL", "每" + interval + "月 ");
				}
			} else if (RecurrenceFreq.YEARLY.name().equals(freq)) {
				infos.put("FREQ", "按年");
				infos.put("INTERVAL", "每" + interval + "年 ");
			}
			if (StringUtil.isNotNull(count)) {
				infos.put("COUNT", "发生" + count + "次");
			} else if (StringUtil.isNotNull(until)) {
				infos.put("UNTIL", "直到" + until);
			}
			if (StringUtil.isNotNull(byDay)) {
				String[] weekDays = { "SU", "MO", "TU", "WE", "TH", "FR",
						"SA" };
				String[] days = byDay.split(",");
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < days.length; i++) {
					for (int j = 0; j < weekDays.length; j++) {
						int index = days[i].indexOf(weekDays[j]);
						if (index != -1) {
							if (index != 0) {
								String weekNumber = days[i].substring(0, index);
								sb.append("第").append(weekNumber)
										.append("个周的");
							}
							if (j == 0) {
								sb.append("周日 ");
							} else if (j == 1) {
								sb.append("周一 ");
							} else if (j == 2) {
								sb.append("周二 ");
							} else if (j == 3) {
								sb.append("周三 ");
							} else if (j == 4) {
								sb.append("周四 ");
							} else if (j == 5) {
								sb.append("周五 ");
							} else if (j == 6) {
								sb.append("周六 ");
							}
						}
					}
				}
				infos.put("BYDAY", sb.toString());
			}
		}
		return infos;
	}

	// 转换重复信息参数
	private static Map<String, String> getParamsMap(String recurrenceStr) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		String[] params = recurrenceStr.split(";");
		for (String param : params) {
			String[] arrays = param.split("=");
			paramsMap.put(arrays[0], arrays[1]);
		}
		return paramsMap;
	}

	// 格式化重复信息字符串
	private static String ensureRecurrenceStr(String recurrenceStr) {
		if (!recurrenceStr.startsWith("RRULE:")) {
			recurrenceStr = "RRULE:" + recurrenceStr;
		}
		Pattern pattern = Pattern.compile("UNTIL=(\\d+)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(recurrenceStr);
		if (matcher.find()) {
			recurrenceStr = matcher.replaceFirst(matcher.group() + "T160000Z");
		}
		return recurrenceStr;
	}

	public static void main(String[] args) {
	}

}
