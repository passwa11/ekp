/**
 * 
 */
package com.landray.kmss.sys.time.service.business;

import java.util.Date;
import java.util.List;

/**
 * 工作日规则，一天内的设置规则。比如设置时间段为工作时间。此工作时间以一天之内为前提。
 * 
 * @author 龚健
 * @see
 */
public interface BusinessDayRule {
	/**
	 * @return 所在日期是否是工作日
	 */
	boolean isBusinessDay(Date dateToCheck);

	/**
	 * @return 所在日期是否是假日
	 */
	boolean isHolidayDay(Date dateToCheck);

	/**
	 * 
	 * @param target
	 *            选择的区间
	 * @return 升序排序的工作时间范围集
	 */
	List<TimeRange> getTimeRangesOnAsc(TimeRange target);

	/**
	 * @param dateToCheck
	 * @return 指定日期是否包含在此规则中
	 */
	boolean contains(Date dateToCheck);
}
