/**
 * 
 */
package com.landray.kmss.sys.time.service.rule;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.service.business.BusinessDayRule;
import com.landray.kmss.sys.time.service.business.TimeRange;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ListSortUtil;
import org.apache.commons.collections.CollectionUtils;

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
public class HolidayProvide implements BusinessRuleProvide {

	@Override
	public List<BusinessDayRule> getRules(SysTimeArea area,
										  SysOrgElement element) {
		List<BusinessDayRule> ranges = new ArrayList<BusinessDayRule>();
		SysTimeHoliday holiday = area.getFdHoliday();
		if (holiday == null) {
			return ranges;
		}
		List<SysTimeHolidayDetail> holidays = holiday.getFdHolidayDetailList();
		if(CollectionUtils.isNotEmpty(holidays)) {
			//开始时间倒序
			ListSortUtil.sort(holidays, "fdStartDay", true);
			for (SysTimeHolidayDetail hd : holidays) {
				ranges.add(new SysTimeHolidayRule(hd));
			}
		}
		return ranges;
	}

	@Override
	public int getPriority() {
		return 49;
	}

	private class SysTimeHolidayRule implements BusinessDayRule {
		private SysTimeHolidayDetail holiday;

		private SysTimeHolidayRule(SysTimeHolidayDetail holiday) {
			this.holiday = holiday;
		}

		@Override
		public boolean contains(Date dateToCheck) {
			long check = dateToCheck.getTime();
			long start = DateUtil.getDateNumber(holiday.getFdStartDay());

			long endt = DateUtil.getDateNumber(holiday.getFdEndDay());
			Calendar endCal = DateUtil.getCalendar(endt);
			endCal.add(Calendar.DATE, 1);
			long end = DateUtil.getDateNumber(endCal.getTime());
			return start <= check && check <= end;
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
			ranges.add(new TimeRange(holiday.getFdStartDay().getTime(),
					holiday.getFdEndDay().getTime() + 86400000L));
			return ranges;
		}
	}
}
