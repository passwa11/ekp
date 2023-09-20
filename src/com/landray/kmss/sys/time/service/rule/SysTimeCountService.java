/**
 * 
 */
package com.landray.kmss.sys.time.service.rule;

import com.google.common.collect.Lists;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.time.interfaces.HoursField;
import com.landray.kmss.sys.time.interfaces.ISysTimeCountService;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.model.SysTimeHolidayPach;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimePatchworkTime;
import com.landray.kmss.sys.time.model.SysTimeVacation;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.time.model.SysTimeWorkDetail;
import com.landray.kmss.sys.time.model.SysTimeWorkTime;
import com.landray.kmss.sys.time.service.ISysTimeAreaService;
import com.landray.kmss.sys.time.service.ISysTimeHolidayPachService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.sys.time.service.business.BusinessHours;
import com.landray.kmss.sys.time.service.business.RuleVisitor;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计算工时和自然周期接口实现
 * 
 * @author 龚健
 * @see
 */
public class SysTimeCountService implements ISysTimeCountService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeCountService.class);
	/** 组织架构服务 */
	private ISysOrgElementService sysOrgElementService;
	/** 区域服务 */
	private ISysTimeAreaService sysTimeAreaService;
	/** 工时计算服务 */
	private BusinessHours businessHours;
	/** 工作日规则提供器集 */
	private List<BusinessRuleProvide> provides = new ArrayList<BusinessRuleProvide>();

	@Override
	public long getManHour(String id, long startTime, long endTime)
			throws Exception {
		return getManHour(getOrgElement(id), startTime, endTime);
	}

	@Override
	public long getManHour(SysOrgElement element, long startTime, long endTime)
			throws Exception {
		if (element != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("组织架构名称:" + element.getFdName() + " -- 组织架构名称:"
						+ element.getFdId() + " -- 开始时间：" + new Date(startTime)
						+ " -- 结束时间：" + new Date(endTime));
			}

			SysTimeArea area = getTimeArea(element);
			if (area != null) {
				return businessHours.calculateBusinessHours(startTime, endTime,
						getRules(area, element));
			} else {
				long attendManHour = SysTimeUtil.getAttendManHour(element, startTime, endTime);
				//-1代表计算错误，可能是模块不存在，也可能是考勤组不存在
				if(-1 != attendManHour){
					return attendManHour;
				}
			}
		}
		return getPeriod(startTime, endTime);
	}

	@Override
	public Date getEndTimeForWorkingHours(String id, Date startTime,
										  int numberOfDate, HoursField field) throws Exception {
		return getEndTimeForWorkingHours(getOrgElement(id), startTime,
				numberOfDate, field);
	}

	@Override
	public Date getEndTimeForWorkingHours(SysOrgElement element, Date startTime,
										  int numberOfDate, HoursField field) throws Exception {
		if (element != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("组织架构名称:" + element.getFdName() + " -- 组织架构名称:"
						+ element.getFdId() + " -- 开始时间：" + startTime);
			}

			SysTimeArea area = getTimeArea(element);
			if (area != null) {
				return businessHours.calculateFloatingHours(startTime,
						numberOfDate, field, getRules(area, element));
			} else {
				Date endTime = SysTimeUtil.getAttendEndTimeForWorkingHours(element, startTime, numberOfDate, field);
				//endTime 等于 startTime 代表计算错误，可能是模块不存在，也可能是考勤组不存在
				if(endTime.getTime() > startTime.getTime()){
					return endTime;
				}
			}
		}
		return null;
	}

	@Override
	public int getWorkingDays(String id, long startTime, long endTime)
			throws Exception {
		return getWorkingDays(getOrgElement(id), startTime, endTime);
	}

	@Override
	public int getWorkingDays(SysOrgElement element, long startTime,
							  long endTime) throws Exception {
		if (element != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("组织架构名称:" + element.getFdName() + " -- 组织架构名称:"
						+ element.getFdId() + " -- 开始时间：" + new Date(startTime)
						+ " -- 结束时间：" + new Date(endTime));
			}

			SysTimeArea area = getTimeArea(element);
			if (area != null) {
				return businessHours.calculateBusinessDays(startTime, endTime,
						getRules(area, element));
			} else {
				int attendWorkingDays = SysTimeUtil.getAttendWorkingDays(element, startTime, endTime);
				//-1代表计算错误，可能是模块不存在，也可能是考勤组不存在
				if(1 != attendWorkingDays){
					return attendWorkingDays;
				}
			}
		}
		return 0;
	}

	@Override
	public long getPeriod(long startTime, long endTime) throws Exception {
		if (startTime != 0 || endTime != 0) {
			return endTime - startTime;
		}
		return 0;
	}

	private List<RuleVisitor> getRules(SysTimeArea area,
			SysOrgElement element) {
		List<RuleVisitor> rules = new ArrayList<RuleVisitor>();
		for (BusinessRuleProvide provide : provides) {
			rules.add(new BusinessRuleVisitor(provide, area, element));
		}
		return rules;
	}

	private SysOrgElement getOrgElement(String id) throws Exception {
		SysOrgElement element = null;
		if (StringUtil.isNotNull(id)) {
			element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(id);
		}
		return element;
	}

	/**
	 * 获取区域组
	 * 
	 * @throws Exception
	 */
	@Override
	public SysTimeArea getTimeArea(SysOrgElement element) throws Exception {
		String areaId = null;
		Map<String, String> orgAreaMap = getParentGroupMap();
		if (orgAreaMap.get(element.getFdId()) != null) {
			areaId = orgAreaMap.get(element.getFdId());
		} else {
			String[] ids = element.getFdHierarchyId()
					.split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
			for (int i = ids.length - 2; i > 0; i--) {
				if (orgAreaMap.containsKey(ids[i])) {
					areaId = orgAreaMap.get(ids[i]);
					break;
				}
			}
		}
		logger.debug("加载区域组ID ： " + areaId);
		if (StringUtil.isNull(areaId)) {
			return null;
		} 
		return (SysTimeArea)sysTimeAreaService.findByPrimaryKey(areaId);
	}

	/**
	 * 获取所有的排班组中的组织架构ID 和 区域组的关系缓存
	 * @return ID映射表：组织架构ID->区域组ID
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> getParentGroupMap() throws Exception {
		Map<String, String> areaMap = (Map<String, String>) SysTimeUtil.SysTimeAreaOrgChache.get("orgAreaMap");
		if (areaMap == null || areaMap.size()==0) {
			logger.debug("重新加载组织架构和区域组关系");
			HashMap<String, String> result = new HashMap<String, String>();
			List<?> timeAreas = sysTimeAreaService.findList("",
					"sysTimeArea.docCreateTime asc");
			if (timeAreas != null) {
				for (int i = 0; i < timeAreas.size(); i++) {
					SysTimeArea area = (SysTimeArea) timeAreas.get(i);
					List<SysOrgElement> areaMembers = area.getAreaMembers();
					if (areaMembers != null) {
						for (SysOrgElement member : areaMembers) {
							result.put(member.getFdId(), area.getFdId());
						}
					}
				}
			}
			SysTimeUtil.SysTimeAreaOrgChache.put("orgAreaMap", result);
			areaMap = result;
		}
		return areaMap;
	}

	// ---------------------------通过Spring注入---------------------------

	public void setBusinessHours(BusinessHours businessHours) {
		this.businessHours = businessHours;
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public void setSysTimeAreaService(ISysTimeAreaService sysTimeAreaService) {
		this.sysTimeAreaService = sysTimeAreaService;
	}

	public void setProvides(List<BusinessRuleProvide> provides) {
		Collections.sort(provides, new Comparator<BusinessRuleProvide>() {
			@Override
			public int compare(BusinessRuleProvide o1, BusinessRuleProvide o2) {
				return o1.getPriority() < o2.getPriority() ? -1 : 1;
			}
		});
		this.provides = provides;
	}

	@Override
	public List<String> getWorkState(String id, Date time) throws Exception {
		SysOrgElement element = getOrgElement(id);
		List<String> rtnList = new ArrayList<String>();
		SysTimeArea area = getTimeArea(element);
		boolean isBatch = area.getFdIsBatchSchedule();
		List<SysTimeWork> works = null;
		List<SysTimePatchwork> patchworks = null;
		List<SysTimeVacation> vacations = null;
		if (isBatch) {
			List<SysTimeOrgElementTime> orgElementTimes = area
					.getOrgElementTimeList();
			for (SysTimeOrgElementTime orgElementTime : orgElementTimes) {
				if (element.getFdId()
						.equals(orgElementTime.getSysOrgElement().getFdId())) {
					patchworks = orgElementTime.getSysTimePatchworkList();
					vacations = orgElementTime.getSysTimeVacationList();
					works = orgElementTime.getSysTimeWorkList();

				}
			}
		} else {
			patchworks = area.getSysTimePatchworkList();
			vacations = area.getSysTimeVacationList();
			works = area.getSysTimeWorkList();
		}
		String wt = ResourceUtil.getString("calendar.data.wt", "sys-time");// 工作日0
		String vt = ResourceUtil.getString("calendar.data.vt", "sys-time");// 假期50
		String hds = ResourceUtil.getString("calendar.data.hd", "sys-time");// 节假日49
		String pwt = ResourceUtil.getString("calendar.data.pw", "sys-time");// 假期补班100
		String hdp = ResourceUtil.getString("calendar.data.hdp", "sys-time");// 节假日补班99
		String hdn = ResourceUtil.getString("calendar.data.normal", "sys-time");// 休息日

		// 假期补班
		if (patchworks != null && patchworks.size() > 0) {
			if (isBatch) {
				for (SysTimePatchwork pw : patchworks) {
					long check = DateUtil.getDateNumber(time);
					long scheduleDate = DateUtil
							.getDateNumber(pw.getFdScheduleDate());
					if (check == scheduleDate) {
						rtnList.add(pwt);
					}
				}
			} else {
				for (SysTimePatchwork pw : patchworks) {
					if (pw.getFdStartTime() != null
							&& pw.getFdEndTime() != null) {
						if (time.getTime() >= pw.getFdStartTime().getTime()
								&& time.getTime() <= pw.getFdEndTime()
										.getTime()) {
							rtnList.add(pwt);
							break;
						}
					}
				}
			}
		}
		SysTimeHoliday holiday = area.getFdHoliday();
		// 节假日补班
		if (holiday != null) {
			// 节假日
			List<SysTimeHolidayDetail> holidays = holiday
					.getFdHolidayDetailList();
			if (holidays != null && holidays.size() > 0) {
				List<SysTimeHolidayPach> holidayPachs = null;
				for (SysTimeHolidayDetail hd : holidays) {
					holidayPachs = getSysTimeHolidayPachService().findList(
							"fdDetail.fdId='" + hd.getFdId() + "'", null);
					if (holidayPachs != null && !holidayPachs.isEmpty()) {
						for (SysTimeHolidayPach hp : holidayPachs) {
							if (hp.getFdPachTime().getTime() == time
									.getTime()) {
								if (!(rtnList.contains(pwt))) {
									rtnList.add(hdp);
									break;
								}
							}
						}
					}
				}
			}
		}
		// 假期
		if (vacations != null && vacations.size() > 0) {
			if(isBatch){
				for (SysTimeVacation vc : vacations) {
					long check = DateUtil.getDateNumber(time);
					long scheduleDate = DateUtil
							.getDateNumber(vc.getFdScheduleDate());
					if (check == scheduleDate) {
						rtnList.add(vt);
					}
				}
			}else{
				for (SysTimeVacation vc : vacations) {
					if (vc.getFdStartTime() != null && vc.getFdEndTime() != null) {
						if (time.getTime() >= vc.getFdStartTime().getTime()
								&& time.getTime() <= vc.getFdEndTime().getTime()) {
							if (!(rtnList.contains(pwt) || rtnList.contains(hds))) {
								rtnList.add(vt);
								break;
							}
						}
					}
				}
			}
		}
		if (holiday != null) {
			// 节假日
			List<SysTimeHolidayDetail> holidays = holiday
					.getFdHolidayDetailList();
			if (holidays != null && holidays.size() > 0) {
				List<SysTimeHolidayPach> holidayPachs = null;
				for (SysTimeHolidayDetail hd : holidays) {
					if (hd.getFdStartDay() != null
							&& hd.getFdEndDay() != null) {
						if (time.getTime() >= hd.getFdStartDay().getTime()
								&& time.getTime() <= hd.getFdEndDay()
										.getTime()) {
							if (!(rtnList.contains(vt) || rtnList.contains(pwt)
									|| rtnList.contains(hdp))) {
								rtnList.add(hds);
								break;
							}
						}
					}
				}
			}
		}

		// 上班
		if (works != null && works.size() > 0) {
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.setTime(time);
			List<Long> rangts = new ArrayList<Long>();
			if (isBatch) {
				for (SysTimeWork wk : works) {
					long check = DateUtil.getDateNumber(time);
					long scheduleDate = DateUtil
							.getDateNumber(wk.getFdScheduleDate());
					if (check == scheduleDate) {
						rtnList.add(wt);
					}
				}
			} else {
				for (SysTimeWork wk : works) {
					if (wk.getFdStartTime() != null
							&& wk.getFdEndTime() != null) {
						if (time.getTime() >= wk.getFdStartTime().getTime()
								&& time.getTime() <= wk.getFdEndTime()
										.getTime()) {
							rangts.add(wk.getDocCreateTime().getTime());
						}
					} else if (wk.getFdStartTime() != null
							&& wk.getFdEndTime() == null) {
						if (time.getTime() >= wk.getFdStartTime().getTime()) {
							rangts.add(wk.getDocCreateTime().getTime());
						}
					}
				}
				Collections.sort(rangts);
				if (!rangts.isEmpty()) {
					long ct = rangts.get(rangts.size() - 1);
					for (SysTimeWork wk : works) {
						if (ct == wk.getDocCreateTime().getTime()) {
							if (cal.get(Calendar.DAY_OF_WEEK) >= wk
									.getFdWeekStartTime()
									&& cal.get(Calendar.DAY_OF_WEEK) <= wk
											.getFdWeekEndTime()) {
								if (!(rtnList.contains(vt)
										|| rtnList.contains(pwt)
										|| rtnList.contains(hds)
										|| rtnList.contains(hdp))) {
									rtnList.add(wt);
									break;
								}
							}
						}
					}
				}
			}
		}
		if (rtnList.isEmpty()) {
			rtnList.add(hdn);
		}
		return rtnList;
	}

	private ISysTimeHolidayPachService sysTimeHolidayPachService = null;

	public ISysTimeHolidayPachService getSysTimeHolidayPachService() {
		if (sysTimeHolidayPachService == null) {
			sysTimeHolidayPachService = (ISysTimeHolidayPachService) SpringBeanUtil
					.getBean("sysTimeHolidayPachService");
		}
		return sysTimeHolidayPachService;
	}

	@Override
	public JSONArray getHolidayPachDay(String id) throws Exception {
		SysOrgElement element = getOrgElement(id);
		JSONArray ja = new JSONArray();
		SysTimeArea area = getTimeArea(element);
		if (area == null) {
			return ja;
		}
		Map<String, String> wmap = new HashMap<String, String>();
		Map<String, String> vmap = new HashMap<String, String>();
		Map<String, String> pmap = new HashMap<String, String>();
		Map<String, String> hvmap = new HashMap<String, String>();// 节假日假期日期
		Map<String, String> hpmap = new HashMap<String, String>();// 节假日补班日期
		String st, et;
		// 工作日
		List<SysTimeWork> works = null;
		// 假期
		List<SysTimeVacation> vacations = null;
		// 假期补班
		List<SysTimePatchwork> patchworks = null;
		boolean isBatch = area.getFdIsBatchSchedule();
		if (isBatch) {
			List<SysTimeOrgElementTime> orgElementTimes = area
					.getOrgElementTimeList();
			for (SysTimeOrgElementTime orgElementTime : orgElementTimes) {
				SysOrgElement sysOrgElement = orgElementTime.getSysOrgElement();
				if (sysOrgElement.getFdId().equals(id)) {
					works = orgElementTime.getSysTimeWorkList();
					vacations = orgElementTime.getSysTimeVacationList();
					patchworks = orgElementTime.getSysTimePatchworkList();
				}
			}
		} else {
			works = area.getSysTimeWorkList();
			vacations = area.getSysTimeVacationList();
			patchworks = area.getSysTimePatchworkList();
		}
		if (works != null && works.size() > 0) {
			for (SysTimeWork work : works) {
				if (isBatch) {
					String date = DateUtil.convertDateToString(
							work.getFdScheduleDate(),
							DateUtil.TYPE_DATE, null);
					wmap.put(date, date);
				} else {
					st = DateUtil.convertDateToString(work.getFdStartTime(),
							DateUtil.TYPE_DATE, null);
					et = DateUtil.convertDateToString(work.getFdEndTime(),
							DateUtil.TYPE_DATE, null);
					getDay(st, et, wmap);
				}
			}
		}

		if (vacations != null && vacations.size() > 0) {
			for (SysTimeVacation vc : vacations) {
				if (isBatch) {
					String date = DateUtil.convertDateToString(
							vc.getFdScheduleDate(),
							DateUtil.TYPE_DATE, null);
					vmap.put(date, date);
				} else {
					st = DateUtil.convertDateToString(vc.getFdStartDate(),
							DateUtil.TYPE_DATE, null);
					et = DateUtil.convertDateToString(vc.getFdEndDate(),
							DateUtil.TYPE_DATE, null);
					getDay(st, et, vmap);
				}
			}
		}

		if (patchworks != null && patchworks.size() > 0) {
			for (SysTimePatchwork pw : patchworks) {
				if (isBatch) {
					String date = DateUtil.convertDateToString(
							pw.getFdScheduleDate(),
							DateUtil.TYPE_DATE, null);
					pmap.put(date, date);
				} else {
					st = DateUtil.convertDateToString(pw.getFdStartTime(),
							DateUtil.TYPE_DATE, null);
					et = DateUtil.convertDateToString(pw.getFdEndTime(),
							DateUtil.TYPE_DATE, null);
					getDay(st, et, pmap);
				}
			}
		}
		SysTimeHoliday holiday = area.getFdHoliday();
		if (holiday != null) {
			// 节假日
			List<SysTimeHolidayDetail> holidays = holiday
					.getFdHolidayDetailList();
			if (holidays != null && holidays.size() > 0) {
				List<SysTimeHolidayPach> holidayPachs = null;
				for (SysTimeHolidayDetail hd : holidays) {
					st = DateUtil.convertDateToString(hd.getFdStartDay(),
							DateUtil.TYPE_DATE, null);
					et = DateUtil.convertDateToString(hd.getFdEndDay(),
							DateUtil.TYPE_DATE, null);
					getDay(st, et, hvmap);
					// 节假日补班
					holidayPachs = getSysTimeHolidayPachService().findList(
							"fdDetail.fdId='" + hd.getFdId() + "'", null);
					if (holidayPachs != null && !holidayPachs.isEmpty()) {
						for (SysTimeHolidayPach hp : holidayPachs) {
							st = DateUtil.convertDateToString(
									hp.getFdPachTime(), DateUtil.TYPE_DATE,
									null);
							hpmap.put(st, st);
						}
					}
				}
			}
		}
		List<String> keys = null;
		JSONObject jo = null;
		if (vmap.size() > 0) {
			keys = new ArrayList(vmap.keySet());
			for (int i = 0; i < keys.size(); i++) {
				boolean isHoliday = true;
				String date = keys.get(i);
				if (isBatch) {
				} else {
					isHoliday = !pmap.containsKey(date)
							&& !hpmap.containsKey(date);
				}
				if (isHoliday) {
					jo = new JSONObject();
					jo.put("date", keys.get(i));
					jo.put("type", "1");
					ja.add(jo);
				}
			}
		}
		if (pmap.size() > 0) {
			keys = new ArrayList(pmap.keySet());
			for (int i = 0; i < keys.size(); i++) {
				jo = new JSONObject();
				jo.put("date", keys.get(i));
				jo.put("type", "2");
				ja.add(jo);
			}
		}
		if (hvmap.size() > 0) {
			keys = new ArrayList(hvmap.keySet());
			for (int i = 0; i < keys.size(); i++) {
				boolean isHoliday = true;
				String date = keys.get(i);
				if(isBatch){
					isHoliday = !wmap.containsKey(date)
							&& !pmap.containsKey(date)
							&& !vmap.containsKey(date);
				}else{
					isHoliday = !pmap.containsKey(date)
							&& !hpmap.containsKey(date);
				}
				if (isHoliday) {
					jo = new JSONObject();
					jo.put("date", keys.get(i));
					jo.put("type", "1");
					ja.add(jo);
				}
			}
		}
		if (hpmap.size() > 0) {
			keys = new ArrayList(hpmap.keySet());
			for (int i = 0; i < keys.size(); i++) {
				boolean isPatch = true;
				String date = keys.get(i);
				if (isBatch) {
					isPatch = !wmap.containsKey(date)
							&& !vmap.containsKey(date)
							&& !pmap.containsKey(date);
				}
				if (isPatch) {
					jo = new JSONObject();
					jo.put("date", keys.get(i));
					jo.put("type", "2");
					ja.add(jo);
				}
			}
		}
		vmap = null;
		pmap = null;
		hvmap = null;
		hpmap = null;
		return ja;
	}

	private void getDay(String startDate, String endDate, Map dayMap) {
		if (StringUtil.isNull(startDate) || StringUtil.isNull(endDate)) {
			return;
		}
		startDate = startDate.split(" ")[0];
		endDate = endDate.split(" ")[0];
		dayMap.put(startDate, startDate);
		Date sdate = DateUtil.convertStringToDate(startDate, DateUtil.TYPE_DATE,
				null);
		Date edate = DateUtil.convertStringToDate(endDate, DateUtil.TYPE_DATE,
				null);
		if (sdate.getTime() > edate.getTime()) {
			return;
		}
		int count = 0;
		if (!startDate.equals(endDate)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateUtil.convertStringToDate(startDate,
					DateUtil.TYPE_DATE, null));
			do {
				count++;
				cal.add(Calendar.DAY_OF_MONTH, 1);
				startDate = DateUtil.convertDateToString(cal.getTime(),
						DateUtil.TYPE_DATE, null);
				startDate = startDate.split(" ")[0];
				dayMap.put(startDate, startDate);
				if (count > 365 * 10) {
					break;// 异常处理，强制退出
				}
			} while (!startDate.equals(endDate));
		}
		return;
	}

	@Override
	public com.alibaba.fastjson.JSONObject getWorkTimes(SysOrgElement element, Date date)
			throws Exception {
		long time = System.currentTimeMillis();
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		String dateStr = null;
		if (element == null || date == null) {
			logger.debug("params is null(person or date)");
			json = getEmptyWorkTimes(json, false);
		} else {
			SysTimeArea area = getTimeArea(element);
			if (area != null) {

				// KmssCache cache = new KmssCache(SysTimeWork.class);
				date = new Date(DateUtil.getDateNumber(date));
				dateStr = DateUtil.convertDateToString(date,
						DateUtil.PATTERN_DATE);
				Map<String, String> map = getHolidayVacationDayMap(element.getFdId(), area);
				if (map.containsKey(dateStr)) {
					json = getEmptyWorkTimes(json, true);
				} else {
					json.put("workTimes", getWT(area, date, element));
					json.put("vacations", getV(area, date, element));
					json.put("isHoliday", false);
				}
			} else {
				json = getEmptyWorkTimes(json, false);
			}
		}
		logger.debug("获取(" + dateStr + ")班次耗时(毫秒)："
				+ (System.currentTimeMillis() - time));
		return json;
	}

	/**
	 * @param json
	 * @return 不在区域组直接返回空的结果
	 */
	private com.alibaba.fastjson.JSONObject getEmptyWorkTimes(com.alibaba.fastjson.JSONObject json, boolean flag) {
		json.put("workTimes", new com.alibaba.fastjson.JSONArray());
		json.put("vacations", new com.alibaba.fastjson.JSONArray());
		json.put("isHoliday", flag);
		return json;
	}

	/**
	 * @param area
	 * @param date
	 * @param element
	 * @return 获取假期和法定假期
	 */
	private com.alibaba.fastjson.JSONArray getV(SysTimeArea area, Date date, SysOrgElement element) {
		com.alibaba.fastjson.JSONArray ja = new com.alibaba.fastjson.JSONArray();
		List<SysTimeVacation> vacations = new ArrayList<SysTimeVacation>();
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
		com.alibaba.fastjson.JSONObject json = null;
		if (isBatch) {
			for (SysTimeVacation vacation : vacations) {
				long check = DateUtil.getDateNumber(date);
				long scheduleDate = DateUtil
						.getDateNumber(vacation.getFdScheduleDate());
				if (check == scheduleDate) {
					json = new com.alibaba.fastjson.JSONObject();
					json.put("fdStartTime",
							DateUtil.getTimeNubmer(
									vacation.getFdScheduleDate()));
					json.put("fdEndTime", 86399000L);
					ja.add(json);
				}
			}
		} else {
			for (SysTimeVacation vacation : vacations) {
				if (vacation.getFdStartDate().getTime() > date.getTime()
						|| date.getTime() > vacation.getFdEndDate().getTime()) {
					continue;
				}
				if (vacation.getFdStartDate().getTime() == date.getTime()
						&& date.getTime() != vacation.getFdEndDate()
								.getTime()) {
					json = new com.alibaba.fastjson.JSONObject();
					json.put("fdStartTime",
							DateUtil.getTimeNubmer(vacation.getFdStartTime()));
					json.put("fdEndTime", 86399000L);
					ja.add(json);
				} else if (vacation.getFdStartDate().getTime() != date.getTime()
						&& date.getTime() == vacation.getFdEndDate()
								.getTime()) {
					json = new com.alibaba.fastjson.JSONObject();
					json.put("fdStartTime", 0);
					json.put("fdEndTime",
							DateUtil.getTimeNubmer(vacation.getFdEndTime()));
					ja.add(json);
				} else if (vacation.getFdStartDate().getTime() == date.getTime()
						&& date.getTime() == vacation.getFdEndDate()
								.getTime()) {
					json = new com.alibaba.fastjson.JSONObject();
					json.put("fdStartTime",
							DateUtil.getTimeNubmer(vacation.getFdStartTime()));
					json.put("fdEndTime",
							DateUtil.getTimeNubmer(vacation.getFdEndTime()));
					ja.add(json);
				} else {
					json = new com.alibaba.fastjson.JSONObject();
					json.put("fdStartTime", 0);
					json.put("fdEndTime", 86399000L);
					ja.add(json);
				}
			}
		}
		return ja;
	}

	/**
	 * @param area
	 * @param date
	 * @param element
	 * @return 获取工作班次（自定义补班》节假日补班》工作日）
	 */
	private com.alibaba.fastjson.JSONArray getWT(SysTimeArea area, Date date,
			SysOrgElement element) throws Exception {
		com.alibaba.fastjson.JSONArray ja = new com.alibaba.fastjson.JSONArray();
		ja = getPatchWorkTimes(area, date, element);
		if (ja.size() == 0) {
			ja = getHolidayPatchWorkTimes(area, date, element);
		}
		if (ja.size() == 0) {
			ja = getWorkTimes(area, date, element);
		}
		return ja;
	}

	// 工作日，剔除周六周日
	private com.alibaba.fastjson.JSONArray getWorkTimes(SysTimeArea area, Date date,
			SysOrgElement element) throws Exception {
		com.alibaba.fastjson.JSONArray ja = new com.alibaba.fastjson.JSONArray();
		if (date == null || area == null) {
			return ja;
		}
		List<SysTimeWork> works = new ArrayList<SysTimeWork>();
		boolean isBatch = area.getFdIsBatchSchedule();
		if (isBatch) {
			List<SysTimeOrgElementTime> orgElementTimes = sysTimeAreaService.getOrgElementTimes(Lists.newArrayList(element.getFdId()));
			for (SysTimeOrgElementTime orgElementTime : orgElementTimes) {
				if (element.getFdId().equals(orgElementTime.getSysOrgElement().getFdId())) {
					works = orgElementTime.getSysTimeWorkList();
					break;
				}
			}
		} else {
			works = area.getSysTimeWorkList();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dweek = cal.get(Calendar.DAY_OF_WEEK);
		com.alibaba.fastjson.JSONObject json = null;
		if (isBatch) {
			for (SysTimeWork work : works) {
				long check = DateUtil.getDateNumber(date);
				long scheduleDate = DateUtil
						.getDateNumber(work.getFdScheduleDate());
				if (check == scheduleDate) {
					SysTimeCommonTime common = work.getSysTimeCommonTime();
					for (SysTimeWorkDetail detail : common.getSysTimeWorkDetails()) {
						json = new com.alibaba.fastjson.JSONObject();
						json.put("fdTotalDay",common.getFdTotalDay());
						json.put("fdStartTime", detail.getHbmWorkStartTime());
						json.put("fdEndTime", detail.getHbmWorkEndTime());
						json.put("fdOverTimeType", detail.getFdOverTimeType());

						json.put("fdBeginTime", detail.getHbmStartTime());
						json.put("fdOverTime", detail.getHbmFdOverTime());
						json.put("fdEndOverTimeType", detail.getFdEndOverTimeType());

						Long restStartTime = common.getHbmRestStartTime();
						Long restEndTime = common.getHbmRestEndTime();
						if (restStartTime != null && restEndTime != null) {
							json.put("fdRestStartTime", restStartTime);
							json.put("fdRestEndTime", restEndTime);
							json.put("fdRestStartType", common.getFdRestStartType());
							json.put("fdRestEndType", common.getFdRestEndType());
						}
						json.put("fdCommonWorkId", common.getFdId());
						ja.add(json);
					}
				}
			}
		} else {
			for (SysTimeWork work : works) {
				Long fdEndTime = work.getHbmEndTime();
				if (fdEndTime == null) {
					Date d = new Date();
					d.setDate(d.getDate() + 365);
					fdEndTime = new Long(
							DateUtil.getDateNumber(d) + DateUtil.DAY
									- 1);
				}
				if (work.getHbmStartTime() <= date.getTime()
						&& date.getTime() < fdEndTime) {
					if (work.getFdWeekStartTime() <= dweek
							&& dweek <= work.getFdWeekEndTime()) {
						SysTimeCommonTime common = work.getSysTimeCommonTime();
						if (common != null) {
							for (SysTimeWorkDetail detail : common
									.getSysTimeWorkDetails()) {
								json = new com.alibaba.fastjson.JSONObject();
								json.put("fdTotalDay",common.getFdTotalDay());
								json.put("fdStartTime", detail.getHbmWorkStartTime());
								json.put("fdEndTime", detail.getHbmWorkEndTime());
								json.put("fdOverTimeType", detail.getFdOverTimeType());
								json.put("fdBeginTime", detail.getHbmStartTime());
								json.put("fdOverTime", detail.getHbmFdOverTime());
								json.put("fdEndOverTimeType", detail.getFdEndOverTimeType());

								Long restStartTime = common.getHbmRestStartTime();
								Long restEndTime = common.getHbmRestEndTime();
								if (restStartTime != null && restEndTime != null) {
									json.put("fdRestStartTime", restStartTime);
									json.put("fdRestEndTime", restEndTime);
									json.put("fdRestStartType", common.getFdRestStartType());
									json.put("fdRestEndType", common.getFdRestEndType());
								}
								ja.add(json);
							}
						} else {
							List<SysTimeWorkTime> times = work
									.getSysTimeWorkTimeList();
							for (SysTimeWorkTime time : times) {
								json = new com.alibaba.fastjson.JSONObject();
								json.put("fdStartTime", time.getHbmWorkStartTime());
								json.put("fdEndTime", time.getHbmWorkEndTime());
								ja.add(json);
							}
						}
						break;
					}
				}
			}
		}
		return ja;
	}

	/**
	 * 获取补班的班次详情
	 * @param area
	 * @param date
	 * @param element
	 * @return
	 */
	private com.alibaba.fastjson.JSONArray getPatchWorkTimes(SysTimeArea area, Date date,
			SysOrgElement element) throws Exception {
		com.alibaba.fastjson.JSONArray ja = new com.alibaba.fastjson.JSONArray();
		List<SysTimePatchwork> patchWorks = new ArrayList<SysTimePatchwork>();
		boolean isBatch = area.getFdIsBatchSchedule();
		if (isBatch) {
			//如果是个人排班，则使用匹配原则查找
			List<SysTimeOrgElementTime> orgElementTimes = sysTimeAreaService.getOrgElementTimes(Lists.newArrayList(element.getFdId()));
			if(CollectionUtils.isNotEmpty(orgElementTimes)) {
				for (SysTimeOrgElementTime orgElementTime : orgElementTimes) {
					if (element.getFdId().equals(orgElementTime.getSysOrgElement().getFdId())) {
						patchWorks = orgElementTime.getSysTimePatchworkList();
						break;
					}
				}
			}
		} else {
			patchWorks = area.getSysTimePatchworkList();
		}
		List<SysTimePatchworkTime> patchworkTimes = null;
		com.alibaba.fastjson.JSONObject json = null;
		if (isBatch) {
			for (SysTimePatchwork patchWork : patchWorks) {
				long check = DateUtil.getDateNumber(date);
				long scheduleDate = DateUtil.getDateNumber(patchWork.getFdScheduleDate());
				if (check == scheduleDate) {
					SysTimeCommonTime common = patchWork.getSysTimeCommonTime();
					List<SysTimeWorkDetail> workDetails = common.getSysTimeWorkDetails();
					for (SysTimeWorkDetail workDetail : workDetails) {
						json = new com.alibaba.fastjson.JSONObject();
						json.put("fdTotalDay",common.getFdTotalDay());
						json.put("fdStartTime", workDetail.getHbmWorkStartTime());
						json.put("fdEndTime", workDetail.getHbmWorkEndTime());

						json.put("fdBeginTime", workDetail.getHbmStartTime());
						json.put("fdOverTime", workDetail.getHbmFdOverTime());
						json.put("fdEndOverTimeType", workDetail.getFdEndOverTimeType());
						json.put("fdOverTimeType", workDetail.getFdOverTimeType());
						Long restStartTime = common.getHbmRestStartTime();
						Long restEndTime = common.getHbmRestEndTime();
						if (restStartTime != null
								&& restEndTime != null) {
							json.put("fdRestStartTime", restStartTime);
							json.put("fdRestEndTime", restEndTime);
							json.put("fdRestStartType", common.getFdRestStartType());
							json.put("fdRestEndType", common.getFdRestEndType());
						}
						json.put("fdCommonWorkId", common.getFdId());
						ja.add(json);
					}
				}
			}
		} else {
			for (SysTimePatchwork patchWork : patchWorks) {
				if (patchWork.getHbmStartTime() <= date.getTime()
						&& date.getTime() < patchWork.getHbmEndTime()) {
					SysTimeCommonTime common = patchWork.getSysTimeCommonTime();
					if (common != null) {
						for (SysTimeWorkDetail detail : common
								.getSysTimeWorkDetails()) {
							json = new com.alibaba.fastjson.JSONObject();
							json.put("fdTotalDay",common.getFdTotalDay());
							json.put("fdStartTime", detail.getHbmWorkStartTime());
							json.put("fdEndTime", detail.getHbmWorkEndTime());
							json.put("fdOverTimeType", detail.getFdOverTimeType());
							json.put("fdBeginTime", detail.getHbmStartTime());
							json.put("fdOverTime", detail.getHbmFdOverTime());
							json.put("fdEndOverTimeType", detail.getFdEndOverTimeType());

							Long restStartTime = common.getHbmRestStartTime();
							Long restEndTime = common.getHbmRestEndTime();
							if (restStartTime != null
									&& restEndTime != null) {
								json.put("fdRestStartTime", restStartTime);
								json.put("fdRestEndTime", restEndTime);
								json.put("fdRestStartType", common.getFdRestStartType());
								json.put("fdRestEndType", common.getFdRestEndType());
							}
							ja.add(json);
						}
					} else {
						patchworkTimes = patchWork
								.getSysTimePatchworkTimeList();
						if (patchworkTimes != null
								&& !patchworkTimes.isEmpty()) {
							for (SysTimePatchworkTime time : patchworkTimes) {
								json = new com.alibaba.fastjson.JSONObject();
								json.put("fdTotalDay",common.getFdTotalDay());
								json.put("fdStartTime",
										time.getHbmWorkStartTime());
								json.put("fdEndTime", time.getHbmWorkEndTime());
								json.put("fdOverTimeType",
										time.getFdOverTimeType());
								json.put("fdBeginTime", time.getHbmStartTime());
								json.put("fdOverTime", time.getHbmFdOverTime());
								json.put("fdEndOverTimeType", time.getFdEndOverTimeType());
								Long restStartTime = common
										.getHbmRestStartTime();
								Long restEndTime = common.getHbmRestEndTime();
								if (restStartTime != null
										&& restEndTime != null) {
									json.put("fdRestStartTime",restStartTime);
									json.put("fdRestEndTime",restEndTime);
									json.put("fdRestStartType", common.getFdRestStartType());
									json.put("fdRestEndType", common.getFdRestEndType());
								}
								ja.add(json);
							}
						}
					}
				}
			}
		}
		return ja;
	}

	// 节假日补班(前后15天找工作的班次)
	private Map<String, String> getHolidayPatchDayMap(SysTimeArea area) {
		Map<String, String> map = new HashMap<>();
		SysTimeHoliday holiday = area.getFdHoliday();
		if (holiday == null) {
			return map;
		}
		List<SysTimeHolidayDetail> holidayWorks = holiday.getFdHolidayDetailList();
		if (holiday != null) {
			String[] patchDays = null;
			for (SysTimeHolidayDetail detail : holidayWorks) {
				if (StringUtil.isNull(detail.getFdPatchDay())) {
					continue;
				}
				patchDays = detail.getFdPatchDay().split("[,;]");
				for (String day : patchDays) {
					if (StringUtil.isNull(day)) {
						continue;
					}
					map.put(day, day);
				}
			}
		}
		return map;
	}

	private com.alibaba.fastjson.JSONArray getHolidayPatchWorkTimes(SysTimeArea area, Date date,
			SysOrgElement element) throws Exception {
		com.alibaba.fastjson.JSONArray ja = new com.alibaba.fastjson.JSONArray();
		Map<String, String> map = getHolidayPatchDayMap(area);
		String dateStr = DateUtil.convertDateToString(date, DateUtil.PATTERN_DATE);
		if (map.containsKey(dateStr)) {
			Calendar cal = Calendar.getInstance();
			for (int i = 0; i <= 15; i++) {
				cal.setTime(date);
				cal.add(Calendar.DAY_OF_MONTH, -i);
				int dweek = cal.get(Calendar.DAY_OF_WEEK);
				if (i > 0 && (dweek == 1 || dweek == 7)) {
					continue;
				}
				ja = getWorkTimes(area, cal.getTime(), element);
				if (ja.size() > 0) {
					break;
				}
			}
			if (ja.size() == 0) {
				for (int i = 0; i <= 5; i++) {
					cal.setTime(date);
					cal.add(Calendar.DAY_OF_MONTH, i);
					int dweek = cal.get(Calendar.DAY_OF_WEEK);
					if (i > 0 && (dweek == 1 || dweek == 7)) {
						continue;
					}
					ja = getWorkTimes(area, cal.getTime(), element);
					if (ja.size() > 0) {
						break;
					}
				}
			}
		}
		return ja;
	}

	private Map<String, String> getHolidayVacationDayMap(String orgId, SysTimeArea area) {

		Map<String, String> cache = (Map<String, String>) SysTimeUtil.SysTimeAreaOrgHolidayChache.get(orgId);
		if(cache != null){
			return cache;
		}
		Map<String, String> map = new HashMap<>();
		SysTimeHoliday holiday = area.getFdHoliday();
		if (holiday == null) {
			return map;
		}
		String dateStr = null;
		List<SysTimeHolidayDetail> holidayWorks = holiday.getFdHolidayDetailList();
		if (holidayWorks != null) {
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.PATTERN_DATE);
			for (SysTimeHolidayDetail detail : holidayWorks) {
				startDate.setTime(detail.getFdStartDay());
				endDate.setTime(detail.getFdEndDay());
				long fdStartDayMillis = DateUtil.removeTime(startDate).getTimeInMillis();
				long fdEndDayMillis = DateUtil.removeTime(endDate).getTimeInMillis();
				if (fdEndDayMillis == fdStartDayMillis) {
					dateStr = dateFormat.format(detail.getFdStartDay());
					map.put(dateStr, dateStr);
				} else {
					startDate.setTime(detail.getFdStartDay());
					for (int i = 0; i < i + 1; i++) {
						dateStr = dateFormat.format(startDate.getTime());
						map.put(dateStr, dateStr);
						startDate.add(Calendar.DATE, 1);
						if (fdEndDayMillis < startDate.getTimeInMillis()) {
							break;
						}
					}
				}
			}
		}
		SysTimeUtil.SysTimeAreaOrgHolidayChache.put(orgId, map);
		return map;
	}
	
	@Override
	public double getUserLeaveAmount(SysOrgElement element, String fdLeaveType)
			throws Exception {
		if (element != null) {
			Calendar ca = Calendar.getInstance();
			int year = ca.get(Calendar.YEAR);
			SysTimeLeaveRule rule = getSysTimeLeaveRuleService()
					.getLeaveRuleByType(fdLeaveType);
			if (rule == null) {
				logger.warn(
						"用户获取假期剩余额度出错,无法找到当前指定的假期类型!fdLeaveType:"
								+ fdLeaveType);
				return 0;
			}
			if(!Boolean.TRUE.equals(rule.getFdIsAmount())){
				//没有开启额度管理
				return -1;
			}
			SysTimeLeaveAmountItem amount = getSysTimeLeaveAmountService()
					.getLeaveAmountItemByType(year, element.getFdId(),
							fdLeaveType);
			if (amount == null) {
				// 当前年没有额度信息,则取去年的额度
				amount = getSysTimeLeaveAmountService()
						.getLeaveAmountItemByType(year-1, element.getFdId(),
								fdLeaveType);
			}
			double days = 0;
			if (amount != null && amount.getFdIsAvail()) {
				// 先判断上周期剩余假期是否存在
				if (Boolean.TRUE.equals(amount.getFdIsLastAvail())
						&& amount.getFdLastRestDay() != null
						&& amount.getFdLastRestDay() > 0) {
					days += amount.getFdLastRestDay();
				}
				// 当前周期
				if (amount.getFdRestDay() != null
						&& amount.getFdRestDay() > 0) {
					days += amount.getFdRestDay();
				}
				return days;
			}
		}
		return 0;
	}

	private ISysTimeLeaveAmountService sysTimeLeaveAmountService = null;
	public ISysTimeLeaveAmountService getSysTimeLeaveAmountService() {
		if (sysTimeLeaveAmountService == null) {
			sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil
					.getBean("sysTimeLeaveAmountService");
		}
		return sysTimeLeaveAmountService;
	}

	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;
	protected ISysTimeLeaveRuleService getSysTimeLeaveRuleService() {
		if (sysTimeLeaveRuleService == null) {
			sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil
					.getBean(
							"sysTimeLeaveRuleService");
		}
		return sysTimeLeaveRuleService;
	}
}