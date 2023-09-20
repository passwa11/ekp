/**
 * 
 */
package com.landray.kmss.sys.time.service.business;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import com.landray.kmss.util.DateUtil;

/**
 * 工时按毫秒计算
 * 
 * @author miaogr
 * @see
 */
public abstract class BusinessMillisecondCalculator implements
		BusinessHoursCalculator {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	private DayRuleCalculator ruleCalc;

	public BusinessMillisecondCalculator(DayRuleCalculator ruleCalc) {
		this.ruleCalc = ruleCalc;
	}

	@Override
	public Date calculateBusinessHours(Date startDate,
									   long numberOfMillisecond, List<RuleVisitor> visitors) {

		if (logger.isDebugEnabled()) {
			logger.debug("开始计算时间："
					+ DateUtil.convertDateToString(startDate,
							"yyyy-MM-dd HH:mm:ss"));
		}

		Calendar peekCal = DateUtil.getCalendar(startDate);

		long millisecond = 0;
		int count = 0;
		while (millisecond <= numberOfMillisecond) {
			List<TimeRange> ranges = ruleCalc.intersectRanges(
					peekCal.getTime(), visitors);
			if (ranges.isEmpty()) {
				logger.debug("ranges is emtpy");
				count += 1;
			}
			// 30天内未匹配到，为避免死循环，直接返回开始时间
			if (count >= 30) {
				return startDate;
			}
			Collections.sort(ranges,new Comparator<TimeRange>() {
				@Override
				public int compare(TimeRange o1, TimeRange o2) {
					long s1 = o1.getEndTime() > DateUtil.DAY ? 0L : o1.getStartTime();
					long s2 = o2.getEndTime() > DateUtil.DAY ? 0L : o2.getStartTime();
					if(s1 == s2) {
						return 0;
					}
					return s1 > s2 ? 1 : -1;
				}
			});
			for (TimeRange range : ranges) {
				//跨天场景下，需要重新构建timerange
				if(range.getEndTime() > DateUtil.DAY && millisecond > 0) {
					range = new TimeRange(0,range.getEndTime() - DateUtil.DAY);
				}
				long rangeMillisecond = range.getCapacity();
				logger.debug("rangeMillisecond:" + range.getCapacity()
						+ ";rang:" + range.getStartTime() + "~"
						+ range.getEndTime() + ";millisecond:" + millisecond);
				if ((rangeMillisecond + millisecond) >= numberOfMillisecond) {
					long date = DateUtil.getDateNumber(peekCal.getTime());
					peekCal.setTimeInMillis(date + range.getStartTime());
					peekCal.add(Calendar.MILLISECOND,
							(int) (numberOfMillisecond - millisecond));

					if (logger.isDebugEnabled()) {
						logger.debug("结束计算时间："
								+ numberOfMillisecond
								+ " 毫秒后："
								+ DateUtil.convertDateToString(
										peekCal.getTime(),
										"yyyy-MM-dd HH:mm:ss"));
					}
					return peekCal.getTime();
				}

				millisecond += rangeMillisecond;
			}

			peekCal.set(Calendar.HOUR_OF_DAY, 0);
			peekCal.set(Calendar.MINUTE, 0);
			peekCal.set(Calendar.SECOND, 0);
			peekCal.set(Calendar.MILLISECOND, 0);

			peekCal.add(Calendar.DATE, 1);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("结束计算时间："
					+ numberOfMillisecond
					+ " 毫秒后："
					+ DateUtil.convertDateToString(peekCal.getTime(),
							"yyyy-MM-dd HH:mm:ss"));
		}
		return peekCal.getTime();
	}

}
