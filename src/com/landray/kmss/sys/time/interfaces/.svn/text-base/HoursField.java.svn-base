/**
 * 
 */
package com.landray.kmss.sys.time.interfaces;

import java.util.Calendar;

/**
 * 计算工作时模式。
 * <p>
 * Day ：工作日，按天计算<br>
 * Hour ：工作时间，按小时计算
 * 
 * @author 龚健
 * @see
 */
public enum HoursField {
	DAY(Calendar.DATE), HOUR(Calendar.HOUR), MINUTE(Calendar.MINUTE);

	private final int calendarField;

	private HoursField(int calendarField) {
		this.calendarField = calendarField;
	}

	public int getCalendarField() {
		return calendarField;
	}
}
