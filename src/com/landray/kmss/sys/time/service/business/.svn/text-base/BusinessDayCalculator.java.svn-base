/**
 * 
 */
package com.landray.kmss.sys.time.service.business;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.landray.kmss.sys.time.interfaces.HoursField;

/**
 * 工作日计算
 * 
 * @author 龚健
 * @see
 */
public class BusinessDayCalculator implements BusinessHoursCalculator {
	@Override
	public Date calculateBusinessHours(Date startDate, long numberOfDay,
									   List<RuleVisitor> visitors) {
		if (numberOfDay <= 0) {
			return startDate;
		}
		Calendar peekCal = Calendar.getInstance();
		peekCal.setTime(startDate);

		boolean isFirstHoliday = false;
		int days = 0;
		HashMap<String, Boolean> checkResult = checkDay(peekCal, visitors);
		if ((checkResult.get("isBusinessDay").booleanValue() && !checkResult
				.get("isHolidayDay").booleanValue()) != true) {
			isFirstHoliday = true;
		}

		while (days < numberOfDay) {
			peekCal.add(Calendar.DATE, 1);

			checkResult.clear();
			checkResult = checkDay(peekCal, visitors);
			if (checkResult.get("isBusinessDay").booleanValue()
					&& !checkResult.get("isHolidayDay").booleanValue()) {
				days++;
			}
		}

		if (isFirstHoliday) {
			// 非工作日启动的节点，设置节点超时跳过时间为计算结果的隔天凌晨
			peekCal.set(Calendar.HOUR_OF_DAY, 23);
			peekCal.set(Calendar.MINUTE, 59);
			peekCal.set(Calendar.SECOND, 59);
			peekCal.set(Calendar.MILLISECOND, 999);
		}
		return peekCal.getTime();
	}

	public HashMap<String, Boolean> checkDay(Calendar peekCal,
			List<RuleVisitor> visitors) {
		boolean isBusinessDay = false;
		boolean isHolidayDay = false;
		HashMap<String, Boolean> result = new HashMap<String, Boolean>();

		for (RuleVisitor visitor : visitors) {
			BusinessDayRule rule = visitor.getDayRule(peekCal.getTime());
			if (rule != null) {
				if (rule.isBusinessDay(peekCal.getTime())) {
					isBusinessDay = true;
				}
				if (rule.isHolidayDay(peekCal.getTime())) {
					isHolidayDay = true;
					break;
				}
			}
		}
		result.put("isBusinessDay", isBusinessDay);
		result.put("isHolidayDay", isHolidayDay);

		return result;
	}

	@Override
	public boolean supports(HoursField field) {
		return HoursField.DAY.equals(field);
	}
}
