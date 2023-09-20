package com.landray.kmss.km.calendar.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarNotifyJobParamBean {

	public CalendarNotifyJobParamBean() {
		super();
	}

	public CalendarNotifyJobParamBean(String remindMainId, boolean isLunar,
			String startDate) {
		this.remindMainId = remindMainId;
		this.isLunar = isLunar;
		this.startDate = startDate;
	}

	/*
	 * 提醒设置记录的ID
	 */
	private String remindMainId;

	public String getRemindMainId() {
		return remindMainId;
	}

	public void setRemindMainId(String remindMainId) {
		this.remindMainId = remindMainId;
	}

	/*
	 * 是否农历
	 */
	private boolean isLunar = false;

	public boolean isLunar() {
		return isLunar;
	}

	public void setLunar(boolean isLunar) {
		this.isLunar = isLunar;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRecurrenceStr() {
		return recurrenceStr;
	}

	public void setRecurrenceStr(String recurrenceStr) {
		this.recurrenceStr = recurrenceStr;
	}

	public List<String> getExcuteDates() {
		return excuteDates;
	}

	public void setExcuteDates(List<String> excuteDates) {
		this.excuteDates = excuteDates;
	}

	/*
	 * 开始时间，格式为：yyyyMMddHHmmss
	 */
	private String startDate;
	/*
	 * 结束时间，格式为：yyyyMMddHHmmss
	 */
	private String endDate;
	/*
	 * 重复规则
	 */
	private String recurrenceStr;
	/*
	 * 执行时间列表
	 */
	private List<String> excuteDates = new ArrayList<String>();

	/*
	 * 上次执行时间，主要针对农历重复的情况
	 */
	private String executedDate;

	public String getExecutedDate() {
		return executedDate;
	}

	public void setExecutedDate(String executedDate) {
		this.executedDate = executedDate;
	}

	private String lunarFreq;

	public String getLunarFreq() {
		return lunarFreq;
	}

	public void setLunarFreq(String lunarFreq) {
		this.lunarFreq = lunarFreq;
	}

	public String getLunarInterval() {
		return lunarInterval;
	}

	public void setLunarInterval(String lunarInterval) {
		this.lunarInterval = lunarInterval;
	}

	private String lunarInterval;

	@Override
	public String toString() {
		// ({"endDate":"2014-02-05 00:00","excuteDates":[],"executedDate":"","lunar":false,"lunarFreq":"","lunarInterval":"","recurrenceStr":"FREQ=MONTHLY;COUNT=3;INTERVAL=1","remindMainId":"14308b0d432860fff0937aa49ecb7b72","startDate":"2013-12-05 00:00"})
		return "remindMainId=" + remindMainId + "&" + "isLunar=" + isLunar
				+ "&" + "startDate=" + startDate + "&" + "endDate=" + endDate
				+ "&" + "recurrenceStr=" + recurrenceStr + "&" + "lunarFreq="
				+ lunarFreq + "&" + "lunarInterval=" + lunarInterval + "&"
				+ "executedDate=" + executedDate + "&" + "excuteDates="
				+ getExcuteDatesStr();
	}

	private String getExcuteDatesStr() {
		String str = "";
		for (String excuteDate : excuteDates) {
			str += excuteDate + "#";
		}
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	private static List<String> getExcuteDatesList(String str) {
		List<String> excuteDates = new ArrayList<String>();
		String[] dates = str.split("#");
		for (String date : dates) {
			excuteDates.add(date);
		}
		return excuteDates;
	}

	public static CalendarNotifyJobParamBean toBean(String str) {
		Map<String, String> result = parseStr(str);
		String isLunarStr = result.get("isLunar");
		boolean isLunar = false;
		if ("true".equals(isLunarStr)) {
			isLunar = true;
		}
		CalendarNotifyJobParamBean bean = new CalendarNotifyJobParamBean(result
				.get("remindMainId"), isLunar, result.get("startDate"));
		bean.setEndDate(result.get("endDate"));
		bean.setExecutedDate(result.get("executedDate"));
		bean.setLunarFreq(result.get("lunarFreq"));
		bean.setLunarInterval(result.get("lunarInterval"));
		bean.setRecurrenceStr(result.get("recurrenceStr"));
		bean.setExcuteDates(getExcuteDatesList(result.get("excuteDates")));
		return null;
	}

	private static Map<String, String> parseStr(String str) {
		Map<String, String> result = new HashMap<String, String>();

		String[] params = str.split("&");
		for (String param : params) {
			String[] pair = param.split("=");
			result.put(pair[0], pair[1]);
		}

		return result;

	}

}
