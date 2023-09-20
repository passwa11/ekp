/**
 * 
 */
package com.landray.kmss.sys.time.service.rule;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.time.model.SysTimeWorkDetail;
import com.landray.kmss.sys.time.model.SysTimeWorkTime;
import com.landray.kmss.sys.time.service.business.BusinessDayRule;
import com.landray.kmss.sys.time.service.business.TimeRange;
import com.landray.kmss.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 工作时间设置规则提供器
 * 
 * @author 龚健
 * @see
 */
public class WorkTimeProvide implements BusinessRuleProvide {
	@Override
	public List<BusinessDayRule> getRules(SysTimeArea area,
										  SysOrgElement element) {
		List<BusinessDayRule> ranges = new ArrayList<BusinessDayRule>();

		List<SysTimeWork> works = null;
		boolean isBatch = area.getFdIsBatchSchedule();
		if (isBatch) {
			List<SysTimeOrgElementTime> orgElementTimes = area
					.getOrgElementTimeList();
			for (SysTimeOrgElementTime orgElementTime : orgElementTimes) {
				if (element.getFdId()
						.equals(orgElementTime.getSysOrgElement().getFdId())) {
					works = orgElementTime.getSysTimeWorkList();
					break;
				}
			}
		} else {
			works = area.getSysTimeWorkList();
		}
		if (works != null) {
			for (SysTimeWork work : works) {
				ranges.add(new WorkTimeRange(work, isBatch));
			}
		}
		return ranges;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	private class WorkTimeRange implements BusinessDayRule {
		private SysTimeWork work;
		private List<TimeRange> ranges = new ArrayList<TimeRange>();
		private boolean isBatch;

		private WorkTimeRange(SysTimeWork work, boolean isBatch) {
			this.work = work;
			this.isBatch = isBatch;
		}

		@Override
		public boolean contains(Date dateToCheck) {
			return checkDay(dateToCheck);
		}

		private boolean checkDay(Date dateToCheck) {
			long check = DateUtil.getDateNumber(dateToCheck);
			if (isBatch) {
				long scheduleDate = DateUtil
						.getDateNumber(work.getFdScheduleDate());
				return check == scheduleDate;
			} else {
				long start = work.getHbmStartTime().longValue();

				Long endTime = work.getHbmEndTime();
				return start <= check
						&& (endTime == null || check <= endTime.longValue());
			}
		}

		private boolean checkWeek(Date dateToCheck) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateToCheck);

			int workWeek = cal.get(Calendar.DAY_OF_WEEK);
			return work.getFdWeekStartTime().longValue() <= workWeek
					&& workWeek <= work.getFdWeekEndTime().longValue();
		}

		@Override
		public boolean isBusinessDay(Date dateToCheck) {
			if (isBatch) {
				return true;
			}
			return checkWeek(dateToCheck);
		}

		@Override
		public boolean isHolidayDay(Date dateToCheck) {
			return false;
		}

		@Override
		public List<TimeRange> getTimeRangesOnAsc(TimeRange target) {
			if (!ranges.isEmpty()) {
				ranges.clear();
			}
			if (ranges.isEmpty()) {
				SysTimeCommonTime commonTime = work.getSysTimeCommonTime();
				if (commonTime == null) {// 兼容历史数据
					List<SysTimeWorkTime> workTimes = work
							.getSysTimeWorkTimeList();
					for (SysTimeWorkTime workTime : workTimes) {
						ranges.add(new TimeRange(workTime.getHbmWorkStartTime(),
								workTime.getHbmWorkEndTime()));
					}
				} else {
					List<SysTimeWorkDetail> workDetails = commonTime
							.getSysTimeWorkDetails();
					for (SysTimeWorkDetail workDetail : workDetails) {
						Long hbmWorkEndTime=workDetail.getHbmWorkEndTime();
						if(Integer.valueOf(2).equals(workDetail.getFdOverTimeType())) {
							hbmWorkEndTime+=DateUtil.DAY;
						}
						List<TimeRange> range = getWorkTimeRange(
								workDetail.getHbmWorkStartTime(),
								hbmWorkEndTime, commonTime,
								target);
						ranges.addAll(range);
					}
				}
				Collections.sort(ranges);
			}
			return ranges;
		}

		private List<TimeRange> getWorkTimeRange(Long hbmWorkStartTime,
				Long hbmWorkEndTime, SysTimeCommonTime commonTime,
				TimeRange target) {

			Long restStartTime = commonTime.getHbmRestStartTime();
			Long restEndTime = commonTime.getHbmRestEndTime();
			List<TimeRange> rangeList =new ArrayList<TimeRange>();
			if (restStartTime != null && restEndTime != null) {
				//午休时间的扣除算法，如果午休类型是次日的情况下。该值增加24小时的long
				Integer fdRestStartType = commonTime.getFdRestStartType();
				Integer fdRestEndType = commonTime.getFdRestEndType();
				if(Integer.valueOf(2).equals(fdRestStartType)){
					restStartTime +=DateUtil.DAY;
				}
				if(Integer.valueOf(2).equals(fdRestEndType)){
					restEndTime +=DateUtil.DAY;
				}
				TimeRange range = new TimeRange(hbmWorkStartTime,
						hbmWorkEndTime);
				TimeRange rangeToUse = range.intersect(target);
				if (rangeToUse != null) {
					if (rangeToUse.getEndTime() >= restEndTime) {
						if (rangeToUse.getStartTime() < restEndTime) {
							if (rangeToUse.getStartTime() >= restStartTime) {
								rangeList.add(new TimeRange(restEndTime,(Long)rangeToUse.getEndTime()));
							} else {
								rangeList.add(new TimeRange((Long)rangeToUse.getStartTime(),restStartTime));
								rangeList.add(new TimeRange(restEndTime,(Long)rangeToUse.getEndTime()));
							}
						}
					} else if (rangeToUse.getEndTime() > restStartTime) {
						rangeList.add(new TimeRange((Long)rangeToUse.getStartTime(),restStartTime));
					}
					if(rangeList.isEmpty()) {
						rangeList.add(new TimeRange((Long) rangeToUse.getStartTime(),
								(Long) (rangeToUse.getEndTime())));
					}
					return rangeList;
				}
			}
			rangeList.add(new TimeRange(hbmWorkStartTime, hbmWorkEndTime));
			return rangeList;
		}
	}
}
