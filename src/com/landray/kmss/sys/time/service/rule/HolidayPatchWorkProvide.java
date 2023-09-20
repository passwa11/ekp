/**
 * 
 */
package com.landray.kmss.sys.time.service.rule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeHolidayPach;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimePatchworkTime;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.time.model.SysTimeWorkDetail;
import com.landray.kmss.sys.time.model.SysTimeWorkTime;
import com.landray.kmss.sys.time.service.ISysTimeHolidayPachService;
import com.landray.kmss.sys.time.service.business.BusinessDayRule;
import com.landray.kmss.sys.time.service.business.TimeRange;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 补班时间设置规则提供器
 * 
 * @author 龚健
 * @see
 */
public class HolidayPatchWorkProvide implements BusinessRuleProvide {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HolidayPatchWorkProvide.class);

	@Override
	public List<BusinessDayRule> getRules(SysTimeArea area,
										  SysOrgElement element) {
		List<BusinessDayRule> ranges = new ArrayList<BusinessDayRule>();
		List<SysTimeHolidayPach> pachs;
		try {
			pachs = getWorkTime(area,element);
			for (SysTimeHolidayPach pach : pachs) {
				ranges.add(new WorkTimeRange(pach));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ranges;
	}

	@Override
	public int getPriority() {
		return 99;
	}

	private class WorkTimeRange implements BusinessDayRule {
		private SysTimeHolidayPach pach;
		private List<TimeRange> ranges = new ArrayList<TimeRange>();

		private WorkTimeRange(SysTimeHolidayPach pach) {
			this.pach = pach;
		}

		@Override
		public boolean contains(Date dateToCheck) {
			long check = dateToCheck.getTime();
			long start = pach.getFdPachTime().getTime();

			Long end = pach.getFdPachTime().getTime() + DateUtil.DAY-1;
			return start <= check && check <= end;
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
			if (ranges.isEmpty()) {
				List<SysTimePatchworkTime> workTimes = pach.getWorkTimes();
				List<SysTimeWorkDetail> workDetailList = pach
						.getWorkDetailList();
				if (!workDetailList.isEmpty()) {
					for (SysTimeWorkDetail workDetail : workDetailList) {
						Long hbmWorkEndTime=workDetail.getHbmWorkEndTime();
						if(Integer.valueOf(2).equals(workDetail.getFdOverTimeType())) {
							hbmWorkEndTime+=DateUtil.DAY;
						}
						List<TimeRange> range = getWorkTimeRange(
								workDetail.getHbmWorkStartTime(),
								hbmWorkEndTime,
								workDetail.getSysTimeCommonTime(),
								target);
						ranges.addAll(range);
					}
				}

				if (workDetailList.isEmpty()) {
					for (SysTimePatchworkTime workTime : workTimes) {
						Long hbmWorkEndTime=workTime.getHbmWorkEndTime();
						if(Integer.valueOf(2).equals(workTime.getFdOverTimeType())) {
							hbmWorkEndTime+=DateUtil.DAY;
						}
						ranges.add(new TimeRange(workTime.getHbmWorkStartTime(),
								hbmWorkEndTime));
					}
				}

				Collections.sort(ranges);
			}
			return ranges;
		}
	}

	private void getHolidayPach(SysTimeHolidayPach pach, SysTimeWorkTime wt) {
		SysTimePatchworkTime pwt = new SysTimePatchworkTime();
		pwt.setHbmWorkStartTime(wt.getHbmWorkStartTime());
		pwt.setHbmWorkEndTime(wt.getHbmWorkEndTime());
		pwt.setFdOverTimeType(wt.getFdOverTimeType());
		pach.getWorkTimes().add(pwt);
	}
	private void getHolidayPach(SysTimeHolidayPach pach, SysTimeWorkDetail wt) {
		pach.getWorkDetailList().add(wt);
	}

	private List<SysTimeHolidayPach> getWorkTime(SysTimeArea area,SysOrgElement element)
			throws Exception {
		List<SysTimeWork> works = null;
		List<SysTimePatchwork> patchworks = null;
		boolean isBatch = area.getFdIsBatchSchedule();
		if (isBatch) {
			List<SysTimeOrgElementTime> orgElementTimes = area
					.getOrgElementTimeList();
			for (SysTimeOrgElementTime orgElementTime : orgElementTimes) {
				if (element.getFdId()
						.equals(orgElementTime.getSysOrgElement().getFdId())) {
					patchworks = orgElementTime.getSysTimePatchworkList();
					works=orgElementTime.getSysTimeWorkList();
					break;
				}
			}
		} else {
			patchworks = area.getSysTimePatchworkList();
			works = area.getSysTimeWorkList();
		}
		List<SysTimeHolidayPach> hps = getList(area);
		List<SysTimeWorkTime> wtd = null;
		List<SysTimeWorkDetail> workDetails = null;
		SysTimePatchworkTime pwt = null;
		if ((works == null || works.size() == 0)&&(patchworks==null || patchworks.size()==0)) {
			// 无工作日设置则补班时间为24个小时
			if (hps != null && !hps.isEmpty()) {
				for (SysTimeHolidayPach pach : hps) {
					pwt = new SysTimePatchworkTime();
					pwt.setHbmWorkStartTime(0L);
					pwt.setHbmWorkEndTime(DateUtil.DAY - 1);
					pach.getWorkTimes().add(pwt);
				}
			}
		} else {
			if (hps != null && !hps.isEmpty()) {
				long pt = 0;
				long end=0;
				for (SysTimeHolidayPach pach : hps) {
					if (pach.getFdPachTime() == null) {
						continue;
					}
					boolean flag = true;
					pt = pach.getFdPachTime().getTime();
					end = pt + DateUtil.DAY-1;
					// 区间内判断
					for (SysTimeWork work : works) {
						if (work.getFdStartTime() != null
								&& work.getFdEndTime() != null) {
							if (pt >= work.getFdStartTime().getTime()
									&& pt <= work.getFdEndTime().getTime()) {
								wtd = work.getSysTimeWorkTimeList();
								for (SysTimeWorkTime wt : wtd) {
									if(wt.getFdWorkStartTime() !=null && wt.getFdWorkEndTime() !=null){
										flag = false;
										getHolidayPach(pach, wt);
									}
								}
								SysTimeCommonTime commonTime =  work.getSysTimeCommonTime();
								if(commonTime !=null){
									workDetails = commonTime.getSysTimeWorkDetails();
									for(SysTimeWorkDetail detail : workDetails){
										if(detail.getFdWorkStartTime() !=null && detail.getFdWorkEndTime()!=null){
											flag = false;
											getHolidayPach(pach, detail);
										}
									}
								}
								if(!flag) {
									break;
								}
							}
						} else if (work.getFdStartTime() != null
								&& work.getFdEndTime() == null) {
							if (pt >= work.getFdStartTime().getTime()) {
								wtd = work.getSysTimeWorkTimeList();
								for (SysTimeWorkTime wt : wtd) {
									if(wt.getFdWorkStartTime() !=null && wt.getFdWorkEndTime() !=null){
										flag = false;
										getHolidayPach(pach, wt);
									}
								}
								SysTimeCommonTime commonTime =  work.getSysTimeCommonTime();
								if(commonTime !=null){
									workDetails = commonTime.getSysTimeWorkDetails();
									for(SysTimeWorkDetail detail : workDetails){
										if(detail.getFdWorkStartTime() !=null && detail.getFdWorkEndTime()!=null){
											flag = false;
											getHolidayPach(pach, detail);
										}
									}
								}
								if(!flag) {
									break;
								}
							}
						}else if (isBatch && work.getFdScheduleDate()!=null&&pt <= work.getFdScheduleDate().getTime() && work.getFdScheduleDate().getTime() <= end) {
							SysTimeCommonTime commonTime =  work.getSysTimeCommonTime();
							if(commonTime !=null){
								workDetails = commonTime.getSysTimeWorkDetails();
								for(SysTimeWorkDetail detail : workDetails){
									if(detail.getFdWorkStartTime() !=null && detail.getFdWorkEndTime()!=null){
										flag = false;
										getHolidayPach(pach, detail);
									}
								}
							}
							if(!flag) {
								break;
							}
						}
					}
					if(flag) {
						for (SysTimePatchwork sysTimePatchwork : patchworks) {
							if(isBatch && sysTimePatchwork.getFdScheduleDate()!=null&&pt <= sysTimePatchwork.getFdScheduleDate().getTime() && sysTimePatchwork.getFdScheduleDate().getTime() <= end) {
								SysTimeCommonTime commonTime =  sysTimePatchwork.getSysTimeCommonTime();
								if(commonTime !=null){
									workDetails = commonTime.getSysTimeWorkDetails();
									for(SysTimeWorkDetail detail : workDetails){
										if(detail.getFdWorkStartTime() !=null && detail.getFdWorkEndTime()!=null){
											flag = false;
											getHolidayPach(pach, detail);
										}
									}
								}
								if(!flag) {
									break;
								}
							}
						}
					}
					// 前后一周工作日区间判断
					if (flag) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(pach.getFdPachTime());
						cal.add(Calendar.DAY_OF_MONTH, -7);
						boolean fg = false;
						for (int j = 1; j <= 14; j++) {
							pt = cal.getTimeInMillis();
							end = pt + DateUtil.DAY-1;
							for (SysTimeWork work : works) {
								if (work.getFdStartTime() != null
										&& work.getFdEndTime() != null) {
									if (pt >= work.getFdStartTime().getTime()
											&& pt <= work.getFdEndTime()
													.getTime()) {
										wtd = work.getSysTimeWorkTimeList();
										for (SysTimeWorkTime wt : wtd) {
											if (wt.getFdWorkStartTime() != null
													&& wt.getFdWorkEndTime() != null) {
												flag = false;
												fg = true;
												getHolidayPach(pach, wt);
											}
										}
										// 兼容处理
										SysTimeCommonTime commonTime = work
												.getSysTimeCommonTime();
										if (commonTime != null) {
											workDetails = commonTime
													.getSysTimeWorkDetails();
											for (SysTimeWorkDetail detail : workDetails) {
												if (detail
														.getFdWorkStartTime() != null
														&& detail
																.getFdWorkEndTime() != null) {
													flag = false;
													fg = true;
													getHolidayPach(pach,
															detail);
												}
											}
										}
									}
								} else if (work.getFdStartTime() != null
										&& work.getFdEndTime() == null) {
									if (pt >= work.getFdStartTime().getTime()) {
										wtd = work.getSysTimeWorkTimeList();
										for (SysTimeWorkTime wt : wtd) {
											if (wt.getFdWorkStartTime() != null
													&& wt.getFdWorkEndTime() != null) {
												flag = false;
												fg = true;
												getHolidayPach(pach, wt);
											}
										}
										// 兼容处理
										SysTimeCommonTime commonTime = work
												.getSysTimeCommonTime();
										if (commonTime != null) {
											workDetails = commonTime
													.getSysTimeWorkDetails();
											for (SysTimeWorkDetail detail : workDetails) {
												if (detail
														.getFdWorkStartTime() != null
														&& detail
																.getFdWorkEndTime() != null) {
													flag = false;
													fg = true;
													getHolidayPach(pach,
															detail);
												}
											}
										}
									}
								}else if (isBatch && work.getFdScheduleDate()!=null&&pt <= work.getFdScheduleDate().getTime() && work.getFdScheduleDate().getTime() <= end) {
									SysTimeCommonTime commonTime =  work.getSysTimeCommonTime();
									if(commonTime !=null){
										workDetails = commonTime.getSysTimeWorkDetails();
										for(SysTimeWorkDetail detail : workDetails){
											if(detail.getFdWorkStartTime() !=null && detail.getFdWorkEndTime()!=null){
												flag = false;
												fg = true;
												getHolidayPach(pach, detail);
											}
										}
									}
								}
								if (fg) {
									break;
								}
							}
							if (fg) {
								break;
							}
							cal.add(Calendar.DAY_OF_MONTH, 1);
						}
					}
					// 结束时间为空的区间判断
					if (flag) {
						long st = 0L;
						for (SysTimeWork work : works) {
							if (work.getFdStartTime() != null
									&& work.getFdEndTime() == null) {
								if (st == 0) {
									st = work.getDocCreateTime().getTime();
								} else if (st < work.getDocCreateTime()
										.getTime()) {
									st = work.getDocCreateTime().getTime();
								}
							}
						}
						for (SysTimeWork work : works) {
							if (work.getFdStartTime() != null
									&& work.getFdEndTime() == null && st == work
											.getDocCreateTime().getTime()) {
								wtd = work.getSysTimeWorkTimeList();
								for (SysTimeWorkTime wt : wtd) {
									if (wt.getFdWorkStartTime() != null
											&& wt.getFdWorkEndTime() != null) {
										flag = false;
										getHolidayPach(pach, wt);
									}
								}
								// 兼容处理
								SysTimeCommonTime commonTime = work
										.getSysTimeCommonTime();
								if (commonTime != null) {
									workDetails = commonTime
											.getSysTimeWorkDetails();
									for (SysTimeWorkDetail detail : workDetails) {
										if (detail.getFdWorkStartTime() != null
												&& detail
														.getFdWorkEndTime() != null) {
											flag = false;
											getHolidayPach(pach, detail);
										}
									}
								}
								if(!flag) {
									break;
								}
							}
						}
					}
					// 都找不到则默认无工作日，补班时间为24小时
					if (flag) {
						pwt = new SysTimePatchworkTime();
						pwt.setHbmWorkStartTime(0L);
						pwt.setHbmWorkEndTime(DateUtil.DAY - 1);
						pach.getWorkTimes().add(pwt);
					}
				}
			}
		}
		return hps;
	}

	private List<SysTimeHolidayPach> getList(SysTimeArea area)
			throws Exception {
		List<SysTimeHolidayPach> rtnList = new ArrayList<SysTimeHolidayPach>();
		SysTimeHoliday holiday = area.getFdHoliday();
		if (holiday == null) {
			return rtnList;
		}
		List<SysTimeHolidayPach> pls = getSysTimeHolidayPachService()
				.findList("fdHoliday.fdId='" + holiday.getFdId() + "'", null);
		List<SysTimePatchwork> works = area.getSysTimePatchworkList();
		if (works == null || works.isEmpty()) {
			rtnList = pls;
		} else {
			if (pls != null && pls.size() > 0) {
				long pt = 0L;
				for (SysTimeHolidayPach pach : pls) {
					pt = pach.getFdPachTime().getTime();
					boolean flag = true;
					for (SysTimePatchwork work : works) {
						if (work.getFdStartTime() != null
								&& work.getFdEndTime() != null) {
							if (pt >= work.getFdStartTime().getTime()
									&& pt <= work.getFdEndTime().getTime()) {
								flag = false;
								break;
							}
						}
					}
					if (flag) {
						rtnList.add(pach);
					}
				}
			}
		}
		return rtnList;
	}

	private List<TimeRange> getWorkTimeRange(Long hbmWorkStartTime,
			Long hbmWorkEndTime, SysTimeCommonTime commonTime,
			TimeRange target) {
		Long restStartTime = commonTime.getHbmRestStartTime();
		Long restEndTime = commonTime.getHbmRestEndTime();
		List<TimeRange> rangeList =new ArrayList<TimeRange>();
		if (restStartTime != null && restEndTime != null) {
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
			} else {
				// 出现场景:在非上班时间区间发起流程会调用计算工时接口,此时该时间区间不在上班区间内,故交集为空
				if (logger.isDebugEnabled()) {
					logger.debug("获取当前范围与指定范围之间的交集为空!hbmWorkStartTime:"
							+ hbmWorkStartTime + ";hbmWorkEndTime:"
							+ hbmWorkEndTime
							+ ";target.startTime:" + target.getStartTime()
							+ ";target.endTime:" + target.getEndTime()
							+ ";range.startTime:" + range.getStartTime()
							+ ";range.endTime:" + range.getEndTime());
				}
			}
		}
		rangeList.add(new TimeRange(hbmWorkStartTime, hbmWorkEndTime));
		return rangeList;
	}

	private ISysTimeHolidayPachService sysTimeHolidayPachService = null;

	public ISysTimeHolidayPachService getSysTimeHolidayPachService() {
		if (sysTimeHolidayPachService == null) {
			sysTimeHolidayPachService = (ISysTimeHolidayPachService) SpringBeanUtil
					.getBean("sysTimeHolidayPachService");
		}
		return sysTimeHolidayPachService;
	}
}
