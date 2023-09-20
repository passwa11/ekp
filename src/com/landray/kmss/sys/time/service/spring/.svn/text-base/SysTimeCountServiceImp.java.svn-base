package com.landray.kmss.sys.time.service.spring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.time.interfaces.HoursField;
import com.landray.kmss.sys.time.interfaces.ISysTimeCountService;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimePatchworkTime;
import com.landray.kmss.sys.time.model.SysTimeVacation;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.time.model.SysTimeWorkTime;
import com.landray.kmss.sys.time.service.ISysTimeAreaService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;

/**
 * 计算工时和自然周期接口实现
 * 
 * 创建日期 2008-一月-21
 * 
 * @author 易荣烽
 */

public class SysTimeCountServiceImp implements ISysTimeCountService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeCountServiceImp.class);
	/** 组织架构服务 */
	private ISysOrgElementService sysOrgElementService;
	/** 区域服务 */
	private ISysTimeAreaService sysTimeAreaService;

	/**
	 * 根据开始时间和结束时间计算自然周期
	 * 
	 * @param starTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 一串數字
	 * @throws Exception
	 */
	@Override
	public long getPeriod(long starTime, long endTime) throws Exception {
		long time;
		if (starTime != 0 || endTime != 0) {
			time = endTime - starTime;
		} else {
			time = 0;
		}
		return time;
	}

	/**
	 * 根据组织架构id查找所属区域组，根据开始时间和结束时间匹配班次等来计算工时
	 * 
	 * @param id
	 *            组织架构id
	 * @param starTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 一串數字
	 * @throws Exception
	 */
	@Override
	public long getManHour(String id, long startDateTime, long endDateTime)
			throws Exception {
		long time = 0;
		if (StringUtil.isNotNull(id)) {
			SysOrgElement sysOrgElement = (SysOrgElement) sysOrgElementService
					.findByPrimaryKey(id);
			time = getManHour(sysOrgElement, startDateTime, endDateTime);

		} else if (endDateTime != 0 || startDateTime != 0) {
			time = endDateTime - startDateTime;
		} else {
			time = 0;
		}
		return time;
	}

	/**
	 * 根据组织架构对象查找所属区域组，根据开始时间和结束时间匹配班次等来计算工时
	 * 
	 * @param element
	 *            组织架构元素
	 * @param starTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 一串數字
	 * @throws Exception
	 */
	@Override
	public long getManHour(SysOrgElement element, long startDateTime,
						   long endDateTime) throws Exception {
		long time = 0;

		if (element != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("组织架构名称:" + element.getFdName() + " -- 组织架构名称:"
						+ element.getFdId() + " -- 开始时间："
						+ new Date(startDateTime) + " -- 结束时间："
						+ new Date(endDateTime));
			}

			String areaId = null;
			SysTimeArea area = getTimeArea(element);
			if(area!=null) {
				areaId = area.getFdId();
			}

			if (StringUtil.isNotNull(areaId)) {
				SysTimeArea sysTimeArea = (SysTimeArea) sysTimeAreaService
						.findByPrimaryKey(areaId);
				List workTimeRangeList = getWorkTimeRange(sysTimeArea,
						startDateTime, endDateTime);
				if (workTimeRangeList != null) {
					List excludeVacationRangeList = getExcludeVacationRange(
							sysTimeArea, workTimeRangeList);

					if (excludeVacationRangeList != null) {
						List containPatchworkTimeRangeList = getContainPatchworkTimeRange(
								sysTimeArea, excludeVacationRangeList,
								startDateTime, endDateTime);
						if (containPatchworkTimeRangeList != null) {
							time = getFinallyManhour(containPatchworkTimeRangeList);
						}
					}

				}

			} else if (endDateTime != 0 || startDateTime != 0) {
				time = endDateTime - startDateTime;
			} else {
				time = 0;
			}

		} else if (endDateTime != 0 || startDateTime != 0) {
			time = endDateTime - startDateTime;
		} else {
			time = 0;
		}
		return time;
	}

	@Override
	public Date getEndTimeForWorkingHours(String id, Date startTime,
										  int dateDistance, HoursField field) throws Exception {
		SysOrgElement element = null;
		if (StringUtil.isNotNull(id)) {
			element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(id);
		}
		return getEndTimeForWorkingHours(element, startTime, dateDistance,
				field);
	}

	@Override
	public Date getEndTimeForWorkingHours(SysOrgElement element,
										  Date startTime, int dateDistance, HoursField field)
			throws Exception {
		return null;
	}

	/**
	 * 获取区域组
	 * 
	 * @throws Exception
	 */
	@Override
	public SysTimeArea getTimeArea(SysOrgElement element) throws Exception {
		String areaId = null;
		Map orgAreaMap = getParentGroupMap();
		if (orgAreaMap.get(element.getFdId()) != null) {
			areaId = orgAreaMap.get(element.getFdId()).toString();
		} else {
			String[] ids = element.getFdHierarchyId().split(
					BaseTreeConstant.HIERARCHY_ID_SPLIT);
			for (int i = ids.length - 2; i > 0; i--) {
				if (orgAreaMap.containsKey(ids[i])) {
					areaId = orgAreaMap.get(ids[i]).toString();
					break;
				}
			}
		}
		logger.debug("加载区域组ID ： " + areaId);

		if (StringUtil.isNull(areaId)) {
			return null;
		}
		return (SysTimeArea) sysTimeAreaService.findByPrimaryKey(areaId);
	}

	/**
	 * 获取班次的时间段列表
	 */
	private List getWorkTimeRange(SysTimeArea sysTimeArea, long startDateTime,
			long endDateTime) throws Exception {
		if (sysTimeArea == null) {
			return null;
		}
		// 获取班次设置列表
		List timeWorkList = sysTimeArea.getSysTimeWorkList();
		List workTimeRangeList = new ArrayList();

		long startDate = DateUtil.getDateNumber(new Date(startDateTime));
		long startTime = DateUtil.getTimeNubmer(new Date(startDateTime));
		long endDate = DateUtil.getDateNumber(new Date(endDateTime));
		long endTime = DateUtil.getTimeNubmer(new Date(endDateTime));

		// 将传入的开始日期和结束日期拆分成天
		for (long calDate = startDate; calDate < endDateTime; calDate += DateUtil.DAY) {
			for (int i = 0; i < timeWorkList.size(); i++) {
				SysTimeWork sysTimeWork = (SysTimeWork) timeWorkList.get(i);

				boolean isAvailable = false;
				if (sysTimeWork.getHbmEndTime() == null) {
					isAvailable = sysTimeWork.getHbmStartTime().longValue() <= calDate;
				} else {
					isAvailable = isTimeBetween(sysTimeWork.getHbmStartTime(),
							calDate, sysTimeWork.getHbmEndTime());
				}
				// 判断当前日期是否在班次设置范围中
				if (isAvailable) {
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(calDate);
					int workWeek = cal.get(Calendar.DAY_OF_WEEK);
					// 判断当前日期是否在周次中
					if (isTimeBetween(sysTimeWork.getFdWeekStartTime(),
							workWeek, sysTimeWork.getFdWeekEndTime())) {
						// 获取班次列表
						List sysTimeWorkTimeList = sysTimeWork
								.getSysTimeWorkTimeList();
						for (int j = 0; j < sysTimeWorkTimeList.size(); j++) {

							SysTimeWorkTime sysTimeWorkTime = (SysTimeWorkTime) sysTimeWorkTimeList
									.get(j);
							Long workStartTime = sysTimeWorkTime
									.getHbmWorkStartTime();
							Long workEndTime = sysTimeWorkTime
									.getHbmWorkEndTime();
							// 判断如果开始日期等于当前日期
							if (startDate == calDate) {
								// 如果传入的开始时间大于工作结束时间，继续循环
								if (startTime > workEndTime.longValue()) {
									continue;
								}
								// 如果传入的开始时间在工作开始时间和结束时间之间，工作的开始时间就等于传入的开始时间
								if (isTimeBetween(workStartTime, startTime,
										workEndTime)) {
									workStartTime = new Long(startTime);
								}
							}
							// 判断如果传入的结束日期等于当前日期
							if (endDate == calDate) {
								// 如果传入的结束时间小于工作开始时间，继续循环
								if (endTime < workStartTime.longValue()) {
									continue;
								}
								// 如果传入的结束时间在工作开始时间和结束时间之间，工作的结束时间等于传入结束时间
								if (isTimeBetween(workStartTime, endTime,
										workEndTime)) {
									workEndTime = new Long(endTime);
								}
							}
							workTimeRangeList.add(new TimeRange(calDate,
									workStartTime.longValue(), workEndTime
											.longValue()));
						}
					}
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("班次设置时间段：" + workTimeRangeList);
		}
		return workTimeRangeList;
	}

	/**
	 * 获取排除假期的时间段列表
	 */
	private List getExcludeVacationRange(SysTimeArea sysTimeArea,
			List workTimeRangeList) {
		if (sysTimeArea == null) {
			return null;
		}

		// 获取休假设置列表
		List vacationList = sysTimeArea.getSysTimeVacationList();

		for (int i = 0; i < vacationList.size(); i++) {

			SysTimeVacation sysTimeVacation = (SysTimeVacation) vacationList
					.get(i);
			List excludeVacationRangeList = new ArrayList();

			// 将假期的开始时间和结束时间拆分为日期和时间
			long vacationStartDate = DateUtil.getDateNumber(new Date(
					sysTimeVacation.getHbmStartTime().longValue()));
			long vacationStartTime = DateUtil.getTimeNubmer(new Date(
					sysTimeVacation.getHbmStartTime().longValue()));
			long vacationEndDate = DateUtil.getDateNumber(new Date(
					sysTimeVacation.getHbmEndTime().longValue()));
			long vacationEndTime = DateUtil.getTimeNubmer(new Date(
					sysTimeVacation.getHbmEndTime().longValue()));

			for (int j = 0; j < workTimeRangeList.size(); j++) {

				TimeRange timeRange = (TimeRange) workTimeRangeList.get(j);
				long timeRangeDate = timeRange.getDate();
				long timeRangeStartTime = timeRange.getStartTime();
				long timeRangeEndTime = timeRange.getEndTime();

				// 判断当前日期是否在假期日期范围内
				if (isTimeBetween(vacationStartDate, timeRangeDate,
						vacationEndDate)) {
					if (timeRangeDate == vacationStartDate
							&& timeRangeDate != vacationEndDate) {
						// 如果工作开始时间大于等于假期开始时间，继续循环
						if (timeRangeStartTime >= vacationStartTime) {
							continue;
						}

						if (timeRangeEndTime <= vacationEndTime) {
							timeRangeEndTime = vacationStartTime;
							excludeVacationRangeList.add(new TimeRange(
									timeRangeDate, timeRangeStartTime,
									timeRangeEndTime));
						} else {
							excludeVacationRangeList.add(new TimeRange(
									timeRangeDate, timeRangeStartTime,
									timeRangeEndTime));
						}
					} else if (timeRangeDate == vacationEndDate
							&& timeRangeDate != vacationStartDate) {
						// 如果工作开始时间大于等于假期开始时间，工作结束时间小于等于假期结束时间，继续循环
						if (timeRangeEndTime <= vacationEndTime) {
							continue;
						}

						if (timeRangeEndTime >= vacationEndTime) {
							timeRangeStartTime = vacationEndTime;
							excludeVacationRangeList.add(new TimeRange(
									timeRangeDate, timeRangeStartTime,
									timeRangeEndTime));
						} else {
							excludeVacationRangeList.add(new TimeRange(
									timeRangeDate, timeRangeStartTime,
									timeRangeEndTime));
						}

					} else if (timeRangeDate == vacationStartDate
							&& timeRangeDate == vacationEndDate) {
						// 如果工作结束时间小于等于假期结束时间，继续循环
						if (timeRangeStartTime >= vacationStartTime
								&& timeRangeEndTime <= vacationEndTime) {
							continue;
						} else if (isTimeBetween(timeRangeStartTime,
								vacationStartTime, timeRangeEndTime)
								&& timeRangeEndTime < vacationEndTime) {
							timeRangeEndTime = vacationStartTime;
							excludeVacationRangeList.add(new TimeRange(
									timeRangeDate, timeRangeStartTime,
									timeRangeEndTime));
						} else if (isTimeBetween(timeRangeStartTime,
								vacationEndTime, timeRangeEndTime)
								&& timeRangeStartTime > vacationStartTime) {
							timeRangeStartTime = vacationEndTime;
							excludeVacationRangeList.add(new TimeRange(
									timeRangeDate, timeRangeStartTime,
									timeRangeEndTime));
						} else if (isTimeBetween(timeRangeStartTime,
								vacationStartTime, timeRangeEndTime)
								&& isTimeBetween(timeRangeStartTime,
										vacationEndTime, timeRangeEndTime)) {
							excludeVacationRangeList.add(new TimeRange(
									timeRangeDate, timeRangeStartTime,
									vacationStartTime));
							excludeVacationRangeList.add(new TimeRange(
									timeRangeDate, vacationEndTime,
									timeRangeEndTime));

						} else {
							excludeVacationRangeList.add(new TimeRange(
									timeRangeDate, timeRangeStartTime,
									timeRangeEndTime));
						}
					}
				} else {
					excludeVacationRangeList.add(new TimeRange(timeRangeDate,
							timeRangeStartTime, timeRangeEndTime));
				}
			}
			workTimeRangeList = excludeVacationRangeList;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("排除假期设置时间段：" + workTimeRangeList);
		}
		return workTimeRangeList;
	}

	/**
	 * 获取包含补班的时间段列表
	 */
	private List getContainPatchworkTimeRange(SysTimeArea sysTimeArea,
			List excludeVacationRangeList, long startDateTime, long endDateTime) {
		if (sysTimeArea == null) {
			return null;
		}
		// 获取补班设置列表
		List patchworkList = sysTimeArea.getSysTimePatchworkList();

		long startDate = DateUtil.getDateNumber(new Date(startDateTime));
		long startTime = DateUtil.getTimeNubmer(new Date(startDateTime));
		long endDate = DateUtil.getDateNumber(new Date(endDateTime));
		long endTime = DateUtil.getTimeNubmer(new Date(endDateTime));

		// 将传入的开始日期和结束日期拆分成天
		for (long calDate = startDate; calDate < endDateTime; calDate += DateUtil.DAY) {
			for (int i = 0; i < patchworkList.size(); i++) {
				SysTimePatchwork sysTimePatchwork = (SysTimePatchwork) patchworkList
						.get(i);
				// 判断当前日期是否在补班设置范围中
				if (isTimeBetween(sysTimePatchwork.getHbmStartTime(), calDate,
						sysTimePatchwork.getHbmEndTime())) {

					// 获取班次列表
					List sysTimePatchworkTimeList = sysTimePatchwork
							.getSysTimePatchworkTimeList();
					for (int j = 0; j < sysTimePatchworkTimeList.size(); j++) {

						SysTimePatchworkTime sysTimePatchworkTime = (SysTimePatchworkTime) sysTimePatchworkTimeList
								.get(j);
						Long workStartTime = sysTimePatchworkTime
								.getHbmWorkStartTime();
						Long workEndTime = sysTimePatchworkTime
								.getHbmWorkEndTime();
						// 判断如果开始日期等于当前日期
						if (startDate == calDate) {
							// 如果传入的开始时间大于工作结束时间，继续循环
							if (startTime > workEndTime.longValue()) {
								continue;
							}
							// 如果传入的开始时间在工作开始时间和结束时间之间，工作的开始时间就等于传入的开始时间
							if (isTimeBetween(workStartTime, startTime,
									workEndTime)) {
								workStartTime = new Long(startTime);
							}
						}
						// 判断如果传入的结束日期等于当前日期
						if (endDate == calDate) {
							// 如果传入的结束时间小于工作开始时间，继续循环
							if (endTime < workStartTime.longValue()) {
								continue;
							}
							// 如果传入的结束时间在工作开始时间和结束时间之间，工作的结束时间等于传入结束时间
							if (isTimeBetween(workStartTime, endTime,
									workEndTime)) {
								workEndTime = new Long(endTime);
							}
						}
						// 将补班时间段加入
						excludeVacationRangeList.add(new TimeRange(calDate,
								workStartTime.longValue(), workEndTime
										.longValue()));
					}
				}
			}
		}

		Comparator comparator = new Comparator() {
			@Override
			public int compare(Object object1, Object Object2) {
				TimeRange timeRange1 = (TimeRange) object1;
				TimeRange timeRange2 = (TimeRange) Object2;
				long cmp = 0;
				int rtn;
				if (timeRange1.getDate() == timeRange2.getDate()) {
					cmp = timeRange1.getStartTime() - timeRange2.getStartTime();
				} else if (timeRange1.getDate() != timeRange2.getDate()) {
					cmp = timeRange1.getDate() - timeRange2.getDate();
				}

				if (cmp > 0) {
					rtn = 1;
				} else if (cmp < 0) {
					rtn = -1;
				} else {
					rtn = 0;
				}
				return rtn;
			}
		};
		// 对时间段排序
		Collections.sort(excludeVacationRangeList, comparator);

		if (logger.isDebugEnabled()) {
			logger.debug("补班设置时间和排除休假时间段：" + excludeVacationRangeList);
		}
		// 截取时间段
		for (int i = excludeVacationRangeList.size() - 1; i > 0; i--) {
			TimeRange timeRange1 = (TimeRange) excludeVacationRangeList
					.get(i - 1);
			TimeRange timeRange2 = (TimeRange) excludeVacationRangeList.get(i);
			if (timeRange1.getDate() == timeRange2.getDate()) {
				if (timeRange2.getStartTime() > timeRange1.getEndTime()) {
					continue;
				}
				if (timeRange2.getStartTime() <= timeRange1.getEndTime()
						&& timeRange2.getEndTime() > timeRange1.getEndTime()) {
					timeRange1.setEndTime(timeRange2.getEndTime());
					excludeVacationRangeList.remove(i);
				} else if (timeRange2.getStartTime() <= timeRange1.getEndTime()
						&& timeRange2.getEndTime() <= timeRange1.getEndTime()) {
					excludeVacationRangeList.remove(i);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("加上补班时间和排除休假时间段：" + excludeVacationRangeList);
		}
		return excludeVacationRangeList;
	}

	/**
	 * 根据时间段列表计算总工时
	 */
	private long getFinallyManhour(List containPatchworkTimeRangeList) {
		long time = 0;
		for (int i = 0; i < containPatchworkTimeRangeList.size(); i++) {
			TimeRange timeRange = (TimeRange) containPatchworkTimeRangeList
					.get(i);
			time += timeRange.getEndTime() - timeRange.getStartTime();

		}
		return time;
	}

	/**
	 * 获取组织架构与区域组对应表
	 * 
	 * @return ID映射表：组织架构ID->区域组ID
	 * @throws Exception
	 */
	private Map getParentGroupMap() throws Exception {
		KmssCache cache = new KmssCache(SysTimeArea.class);
		Map orgAreaMap = (Map) cache.get("orgAreaMap");
		if (orgAreaMap == null) {
			logger.debug("重新加载组织架构和区域组关系");
			HashMap result = new HashMap();
			List timeAreas = sysTimeAreaService.findList("", "");
			if (timeAreas != null) {
				for (int i = 0; i < timeAreas.size(); i++) {
					SysTimeArea sysTimeArea = (SysTimeArea) timeAreas.get(i);
					List areaMembers = sysTimeArea.getAreaMembers();
					if (areaMembers != null) {
						for (int j = 0; j < areaMembers.size(); j++) {
							SysOrgElement sysOrgElement = (SysOrgElement) areaMembers
									.get(j);
							result.put(sysOrgElement.getFdId(), sysTimeArea
									.getFdId());
						}
					}
				}
			}
			cache.put("orgAreaMap", result);
			orgAreaMap = result;
		}
		return orgAreaMap;
	}

	/**
	 * 时间段对象
	 * 
	 */
	public class TimeRange {

		/*
		 * 日期
		 */
		protected long date;

		/*
		 * 开始时间
		 */
		protected long startTime;

		/*
		 * 结束时间
		 */
		protected long endTime;

		public long getDate() {
			return date;
		}

		public void setDate(long date) {
			this.date = date;
		}

		public long getEndTime() {
			return endTime;
		}

		public void setEndTime(long endTime) {
			this.endTime = endTime;
		}

		public long getStartTime() {
			return startTime;
		}

		public void setStartTime(long startTime) {
			this.startTime = startTime;
		}

		public TimeRange(long date, long startTime, long endTime) {
			super();
			this.date = date;
			this.startTime = startTime;
			this.endTime = endTime;
		}

		@Override
		public String toString() {
			return DateUtil.convertDateToString(new Date(date), ResourceUtil
					.getString("date.format.date"))
					+ " "
					+ DateUtil.convertDateToString(DateUtil
							.getTimeByNubmer(startTime), "HH:mm:ss")
					+ " - "
					+ DateUtil.convertDateToString(DateUtil
							.getTimeByNubmer(endTime), "HH:mm:ss");
		}
	}

	/**
	 * 判断时间是否在某段时间内
	 * 
	 * @param startTime
	 * @param time
	 * @param endTime
	 * @return
	 */
	private boolean isTimeBetween(Long startTime, long time, Long endTime) {
		return startTime.longValue() <= time && time <= endTime.longValue();
	}

	/**
	 * 判断时间是否在某段时间内
	 * 
	 * @param startTime
	 * @param time
	 * @param endTime
	 * @return
	 */
	private boolean isTimeBetween(long startTime, long time, long endTime) {
		return startTime <= time && time <= endTime;
	}

	// ---------------------------通过Spring注入---------------------------

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public void setSysTimeAreaService(ISysTimeAreaService sysTimeAreaService) {
		this.sysTimeAreaService = sysTimeAreaService;
	}

	@Override
	public int getWorkingDays(String id, long startTime, long endTime)
			throws Exception {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int getWorkingDays(SysOrgElement element, long startTime,
			long endTime) throws Exception {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public List<String> getWorkState(String id, Date time)
			throws Exception {
		return null;
	}

	@Override
	public JSONArray getHolidayPachDay(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.alibaba.fastjson.JSONObject getWorkTimes(SysOrgElement element, Date date)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getUserLeaveAmount(SysOrgElement element, String fdLeaveTime)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
