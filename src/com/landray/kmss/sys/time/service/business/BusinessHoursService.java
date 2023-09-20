/**
 * 
 */
package com.landray.kmss.sys.time.service.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;

import com.landray.kmss.sys.time.interfaces.HoursField;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.DateUtil;

/**
 * 工时计算主服务
 * 
 * @author 龚健
 * @see
 */
public class BusinessHoursService implements BusinessHours, DayRuleCalculator,
		InitializingBean {
	private List<BusinessHoursCalculator> calculators = new ArrayList<BusinessHoursCalculator>();

	@Override
	public Date calculateFloatingHours(Date startDate, int numberOfDate,
									   HoursField field, List<RuleVisitor> visitors) {
		for (BusinessHoursCalculator calculator : calculators) {
			if (calculator.supports(field)) {
				return calculator.calculateBusinessHours(startDate,
						numberOfDate, visitors);
			}
		}
		throw new NoBusinessCalculatorException();
	}

	@Override
	public long calculateBusinessHours(Date startDate, Date endDate,
									   List<RuleVisitor> visitors) {
		Calendar peekCal = DateUtil.removeTime(startDate);

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		long endToUse = DateUtil.getTimeNubmer(endDate);

		long numberOfHours = 0;
		while (peekCal.before(endCal)) {
			peekCal.add(Calendar.DATE, 1);

			long endTime = peekCal.after(endCal) ? endToUse : -1;
			// 合并工作日和剔除假期的时间区间集
			List<TimeRange> ranges = intersectRanges(startCal.getTime(),
					endTime, visitors);
			for (TimeRange range : ranges) {
				numberOfHours += range.getCapacity();
			}

			startCal.setTime(peekCal.getTime());
		}

		return numberOfHours;
	}

	@Override
	public long calculateBusinessHours(long startDate, long endDate,
									   List<RuleVisitor> rules) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTimeInMillis(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTimeInMillis(endDate);

		return calculateBusinessHours(startCal.getTime(), endCal.getTime(),
				rules);
	}

	@Override
	public int calculateBusinessDays(long startDate, long endDate,
									 List<RuleVisitor> rules) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTimeInMillis(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTimeInMillis(endDate);

		return calculateBusinessDays(startCal.getTime(), endCal.getTime(),
				rules);
	}

	public int calculateBusinessDays(Date startDate, Date endDate,
			List<RuleVisitor> visitors) {
		Calendar peekCal = DateUtil.removeTime(startDate);

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		long endToUse = DateUtil.getTimeNubmer(endDate);

		//int numberOfDays = 0;
		Set<Date> dateSet=new HashSet<Date>();
		while (peekCal.before(endCal)) {
			peekCal.add(Calendar.DATE, 1);

			long endTime = peekCal.after(endCal) ? endToUse : -1;
			// 合并工作日和剔除假期的时间区间集
			List<TimeRange> ranges = intersectRanges(startCal.getTime(),
					endTime, visitors);
			for (TimeRange range : ranges) {
				if (range.getCapacity() > 0 && range.getDate()!=null) {
					dateSet.add(range.getDate());
				}
			}

			startCal.setTime(peekCal.getTime());
		}

		return dateSet.size();
	}

	@Override
	public List<TimeRange> intersectRanges(Date start,
										   List<RuleVisitor> visitors) {
		return intersectRanges(start, -1, visitors);
	}

	@Override
	public List<TimeRange> intersectRanges(Date start, long endTime,
										   List<RuleVisitor> visitors) {
		List<TimeRange> result = null;
		//防止跨天排班，从前一天开始计算。
		Calendar cal=Calendar.getInstance();
		cal.setTime(start);
		cal.add(Calendar.DATE, -1);
		for(int i=1;i>=0;i--) {
			//查前一天的排班，取前一天的排班时间与当天的交集，计算工时，13号排班 18:00~03:00(次日)，查询14 00:00~15 00:00的工时，（14 00:00~03:00）3+(14 18:00~14 23:59)6=9小时
			//跨天排班时：1、取前一天排班 ，前一天18~27 ，当天24~48区间，交集24~27共3小时
			//2、当天排班时间 18~24 与 0~24 交集 18~24共6小时 
			//3、共9小时
			TimeRange target = new TimeRange(DateUtil.getTimeNubmer(start)+i*DateUtil.DAY,endTime<0?(i+1)*DateUtil.DAY:endTime+i*DateUtil.DAY);
			for (RuleVisitor visitor : visitors) {
				BusinessDayRule rule = visitor.getDayRule(cal.getTime());
				if (rule != null) {
					if (rule.isBusinessDay(cal.getTime())) {
						List<TimeRange> ranges = getFloatingRanges(target, rule);
						if (result == null || result.isEmpty()) {
							result = ranges;
						} else {
							if (ranges != null && !ranges.isEmpty()) {
								result = mergerRanges(result, ranges);
							}
						}
					} else if (rule.isHolidayDay(cal.getTime()) && result != null) {
						BusinessDayRule holidayRule = visitor.getDayRule(start);
						//当前一天和当天都为节假日时，当天的工时为0.
						if(holidayRule!=null && holidayRule.isHolidayDay(start) && !cal.getTime().equals(start)) {
							result = new ArrayList<TimeRange>();
							continue;
						}
						// 假期计算获取时间交集需要日期信息。除去时需要除去日期信息
						long dateNum = DateUtil.getDateNumber(cal.getTime());
						TimeRange otherTarget = new TimeRange(dateNum
								+ target.getStartTime(), dateNum
								+ target.getEndTime());
						List<TimeRange> ranges = getFloatingRanges(otherTarget,
								rule);
						result = excludeRanges(result,
								getTimeRangesExceptDate(ranges, dateNum));
					}
				}
			}
			cal.setTime(start);
		}
		if(result!=null) {
			for (TimeRange range : result) {
				if(range.getEndTime()>DateUtil.DAY) {
					range.setDate(SysTimeUtil.getDate(start, -1));
				}else {
					range.setDate(SysTimeUtil.getDate(start, 0));
				}
			}
		}
		return result == null ? new ArrayList<TimeRange>() : result;
	}

	/**
	 * 假期的时间区间含有日期，所以进行某天时间区间计算时，需要除去日期信息，仅保留时间
	 * 
	 * @param ranges
	 * @param dateLog
	 * @return
	 */
	private List<TimeRange> getTimeRangesExceptDate(List<TimeRange> ranges,
			long dateLog) {
		List<TimeRange> rtnList = new ArrayList<TimeRange>();
		if (ranges == null || ranges.isEmpty()) {
			return rtnList;
		}
		for (int i = 0; i < ranges.size(); i++) {
			TimeRange tmpRange = ranges.get(i);
			long startTime = tmpRange.getStartTime() - dateLog;
			long endTime = tmpRange.getEndTime() - dateLog;
			rtnList.add(new TimeRange((startTime > DateUtil.DAY - 1) ? 0
					: startTime, (endTime > DateUtil.DAY - 1) ? -1 : endTime));
		}
		return rtnList;
	}

	/**
	 * 从目标区间集中剔除调整的区间集，返回剔除后的区间集
	 * 
	 * @param targetList
	 * @param adjustList
	 * @return
	 */
	private List<TimeRange> excludeRanges(List<TimeRange> targetList,
			List<TimeRange> adjustList) {
		List<TimeRange> result = new ArrayList<TimeRange>();
		for (TimeRange target : targetList) {
			TimeRange excludeToUse = target;
			for (TimeRange adjust : adjustList) {
				List<TimeRange> excludes = excludeToUse.exclude(adjust);
				if (excludes.size() == 2) {
					result.add(excludes.get(0));
					excludeToUse = excludes.get(1);
				} else {
					excludeToUse = excludes.get(0);
				}
			}
			result.add(excludeToUse);
		}
		return result;
	}

	/**
	 * 从目标区间集中合并调整的区间集，返回合并后的区间集
	 * 
	 * @param targetList
	 * @param adjustList
	 * @return
	 */
	private List<TimeRange> mergerRanges(List<TimeRange> targetList,
			List<TimeRange> adjustList) {
		List<TimeRange> result = new ArrayList<TimeRange>();
		result.addAll(targetList);
		result.addAll(adjustList);
		return mergerRanges(result);
	}

	private List<TimeRange> getFloatingRanges(TimeRange target,
			BusinessDayRule rule) {
		List<TimeRange> result = new ArrayList<TimeRange>();
		for (TimeRange range : rule.getTimeRangesOnAsc(target)) {
			TimeRange rangeToUse = range.intersect(target);
			if (rangeToUse != null) {
				result.add(rangeToUse);
			}
		}
		return mergerRanges(result);
	}

	private List<TimeRange> mergerRanges(List<TimeRange> ranges) {
		if (ranges == null || ranges.isEmpty()) {
			return ranges;
		}
		Collections.sort(ranges);

		List<TimeRange> result = new ArrayList<TimeRange>();

		TimeRange root = null;
		for (TimeRange range : ranges) {
			if (range.getCapacity() == 0) {
				continue;
			}
			if (root == null) {
				root = range;
			} else {
				TimeRange merger = root.merger(range);
				if (merger != null) {
					root = merger;
				} else {
					result.add(root);
					root = range;
				}
			}
		}
		if (root != null && !result.contains(root)) {
			result.add(root);
		}

		Collections.sort(result);

		return result;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		calculators.add(new BusinessDayCalculator());
		calculators.add(new BusinessHourCalculator(this));
		calculators.add(new BusinessMinuteCalculator(this));
	}
}
