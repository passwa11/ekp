/**
 * 
 */
package com.landray.kmss.sys.time.service.rule;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.time.model.SysTimeVacation;
import com.landray.kmss.sys.time.service.business.BusinessDayRule;
import com.landray.kmss.sys.time.service.business.TimeRange;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ListSortUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 假期设置规则提供器
 * 
 * @author 龚健
 * @see
 */
public class VacationProvide implements BusinessRuleProvide {
	@Override
	public List<BusinessDayRule> getRules(SysTimeArea area,
										  SysOrgElement element) {
		List<BusinessDayRule> ranges = new ArrayList<BusinessDayRule>();

		List<SysTimeVacation> vacations = null;
		boolean isBatch = area.getFdIsBatchSchedule();
		if (isBatch) {
			List<SysTimeOrgElementTime> orgElementTimes = area
					.getOrgElementTimeList();
			for (SysTimeOrgElementTime orgElementTime : orgElementTimes) {
				if (element.getFdId()
						.equals(orgElementTime.getSysOrgElement().getFdId())) {
					vacations = orgElementTime.getSysTimeVacationList();
					break;
				}
			}
		} else {
			vacations = area.getSysTimeVacationList();
		}
		if (vacations != null) {
			ListSortUtil.sort(vacations,"fdStartDate",true);
			for (SysTimeVacation vacation : vacations) {
				ranges.add(new VacationTimeRule(vacation, isBatch));
			}
		}

		return ranges;
	}

	@Override
	public int getPriority() {
		return 50;
	}

	private class VacationTimeRule implements BusinessDayRule {
		private SysTimeVacation vacation;
		private boolean isBatch;

		private VacationTimeRule(SysTimeVacation vacation, boolean isBatch) {
			this.vacation = vacation;
			this.isBatch = isBatch;
		}

		@Override
		public boolean contains(Date dateToCheck) {
			if (isBatch) {
				long check = DateUtil.getDateNumber(dateToCheck);
				long scheduleDate = DateUtil
						.getDateNumber(vacation.getFdScheduleDate());
				return check == scheduleDate;
			} else {
				long check = dateToCheck.getTime();
				long start = DateUtil.getDateNumber(new Date(vacation
						.getHbmStartTime()));
				Calendar endCal = DateUtil
						.getCalendar(vacation.getHbmEndTime());
				endCal.add(Calendar.DATE, 1);
				long end = DateUtil.getDateNumber(endCal.getTime());
				return start <= check && check <= end;
			}
		}

		@Override
		public boolean isBusinessDay(Date dateToCheck) {
			return false;
		}

		@Override
		public boolean isHolidayDay(Date dateToCheck) {
			return true;
		}

		@Override
		public List<TimeRange> getTimeRangesOnAsc(TimeRange target) {
			List<TimeRange> ranges = new ArrayList<TimeRange>();
			if (isBatch) {
				long start = vacation.getFdScheduleDate().getTime();
				long end = start + 86400000L;
				ranges.add(new TimeRange(start, end));
			} else {
				ranges.add(new TimeRange(vacation.getFdStartTime().getTime(),
						vacation.getFdEndTime().getTime()));
			}
			return ranges;
		}
	}
}
