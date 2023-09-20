/**
 * 
 */
package com.landray.kmss.sys.time.service.rule;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimePatchworkTime;
import com.landray.kmss.sys.time.model.SysTimeWorkDetail;
import com.landray.kmss.sys.time.service.business.BusinessDayRule;
import com.landray.kmss.sys.time.service.business.TimeRange;
import com.landray.kmss.util.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 补班时间设置规则提供器
 * 
 * @author 龚健
 * @see
 */
public class PatchWorkProvide implements BusinessRuleProvide {
	@Override
	public List<BusinessDayRule> getRules(SysTimeArea area,
										  SysOrgElement element) {
		List<BusinessDayRule> ranges = new ArrayList<BusinessDayRule>();

		List<SysTimePatchwork> works = null;
		boolean isBatch = area.getFdIsBatchSchedule();
		if (isBatch) {
			List<SysTimeOrgElementTime> orgElementTimes = area
					.getOrgElementTimeList();
			for (SysTimeOrgElementTime orgElementTime : orgElementTimes) {
				if (element.getFdId()
						.equals(orgElementTime.getSysOrgElement().getFdId())) {
					works = orgElementTime.getSysTimePatchworkList();
					break;
				}
			}
		} else {
			works = area.getSysTimePatchworkList();
		}
		if (works != null) {
			for (SysTimePatchwork work : works) {
				ranges.add(new PatchWorkTimeRange(work, isBatch));
			}
		}

		return ranges;
	}

	@Override
	public int getPriority() {
		return 100;
	}

	private class PatchWorkTimeRange implements BusinessDayRule {
		private SysTimePatchwork work;
		private List<TimeRange> ranges = new ArrayList<TimeRange>();
		private boolean isBatch;

		private PatchWorkTimeRange(SysTimePatchwork work, boolean isBatch) {
			this.work = work;
			this.isBatch = isBatch;
		}

		@Override
		public boolean contains(Date dateToCheck) {
			if (isBatch) {
				long check = DateUtil.getDateNumber(dateToCheck);
				long scheduleDate = DateUtil
						.getDateNumber(work.getFdScheduleDate());
				return check == scheduleDate;
			} else {
				long check = dateToCheck.getTime();
				return work.getHbmStartTime().longValue() <= check
						&& check <= work.getHbmEndTime().longValue();
			}
		}

		@Override
		public boolean isBusinessDay(Date dateToCheck) {
			return true;
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
			SysTimeCommonTime commonTime = work.getSysTimeCommonTime();
			if (commonTime == null) {// 兼容历史数据
				List<SysTimePatchworkTime> workTimes = work.getSysTimePatchworkTimeList();
				for (SysTimePatchworkTime workTime : workTimes) {
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
							hbmWorkEndTime, commonTime, target);
					ranges.addAll(range);
				}
			}
			Collections.sort(ranges);
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
				TimeRange range = new TimeRange(hbmWorkStartTime,hbmWorkEndTime);
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
