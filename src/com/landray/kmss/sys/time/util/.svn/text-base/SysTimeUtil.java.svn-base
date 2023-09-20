package com.landray.kmss.sys.time.util;

import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.authentication.user.UserAuthInfo;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.authorization.model.SysAuthDefaultArea;
import com.landray.kmss.sys.authorization.service.ISysAuthAreaService;
import com.landray.kmss.sys.authorization.service.ISysAuthDefaultAreaService;
import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.interfaces.HoursField;
import com.landray.kmss.sys.time.interfaces.ISysTimeCountService;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeLeaveConfig;
import com.landray.kmss.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-25
 */
public class SysTimeUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeUtil.class);
	private static final Map<String, Boolean> modules = new ConcurrentHashMap<String, Boolean>();
	private static Map<String, String> authAreaMap = new HashMap<String, String>();

	private static ISysOrgCoreService sysOrgCoreService;
	private static ISysAuthAreaService sysAuthAreaService;
	private static ISysAuthDefaultAreaService sysAuthDefaultAreaService;
	private static ISysTimeCountService sysTimeCountService;
	  private static ISysAttendCategoryService sysAttendCategoryService;

	    protected static ISysAttendCategoryService getSysAttendCategoryService() {
	        if (sysAttendCategoryService == null) {
	            sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
	        }
	        return sysAttendCategoryService;
	    }
	public static ISysTimeCountService getSysTimeCountService() {
		if (sysTimeCountService == null) {
			sysTimeCountService = (ISysTimeCountService) SpringBeanUtil.getBean(
					"sysTimeCountService");
		}
		return sysTimeCountService;
	}

	public static ISysAuthDefaultAreaService getSysAuthDefaultAreaService() {
		if (sysAuthDefaultAreaService == null) {
			sysAuthDefaultAreaService = (ISysAuthDefaultAreaService) SpringBeanUtil
					.getBean("sysAuthDefaultAreaService");
		}
		return sysAuthDefaultAreaService;
	}
	public static ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	public static ISysAuthAreaService getSysAuthAreaService() {
		if (sysAuthAreaService == null) {
			sysAuthAreaService = (ISysAuthAreaService) SpringBeanUtil
					.getBean("sysAuthAreaService");
		}
		return sysAuthAreaService;
	}
	/**
	 * 返回一天的凌晨
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDate(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getEndDate(Date date, int day) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * 分隔List
	 * 
	 * @param list
	 * @param pageSize
	 * @return
	 */
	public static List<List> splitList(List list, int pageSize) {
		int listSize = list.size(); // list的大小
		int page = (listSize + (pageSize - 1)) / pageSize; // 页数
		List<List> listArray = new ArrayList<List>(); // 创建list数组,用来保存分割后的list
		for (int i = 0; i < page; i++) {
			List subList = new ArrayList();
			for (int j = 0; j < listSize; j++) {
				int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
				if (pageIndex == (i + 1)) {
					subList.add(list.get(j));
				}

				if ((j + 1) == ((j + 1) * pageSize)) {
					break;
				}
			}
			listArray.add(subList); // 将分割后的list放入对应的数组的位中
		}
		return listArray;
	}

	/**
	 * 根据模块路径判断模块是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean moduleExist(String path) {
		Boolean exist = modules.get(path);
		if (exist == null) {
			exist = new File(PluginConfigLocationsUtil.getKmssConfigPath()
					+ path).exists();
			modules.put(path, exist);
		}
		return exist;
	}

	/**
	 * 不建议使用 格式化时间显示 格式统一为:xx天xx小时xx分钟
	 * 
	 * @param leaveDay
	 * @param leaveHour
	 *            不会累计到天
	 * @return
	 */
	public static String formatLeaveTimeStr(Integer leaveDay,
			Float leaveHour) {
		leaveDay = leaveDay==null ? 0:leaveDay;
		leaveHour = leaveHour==null ? 0f:leaveHour;
		int _leaveHour = leaveHour.intValue();
		Float mins = (leaveHour-leaveHour.intValue())*60;
		int _mins = Math.round(mins);
		if (_mins >= 60) {
			_leaveHour += _mins / 60;
			_mins = Math.round(_mins % 60);
		}
		String leaveTxt = "";
		if (leaveDay > 0) {
			leaveTxt = leaveDay + ResourceUtil.getString("date.interval.day");
			if (_leaveHour == 0 && _mins == 0) {
				leaveTxt = leaveDay + "";
			}
		}
		if(_leaveHour>0){
			leaveTxt += _leaveHour
					+ ResourceUtil.getString("date.interval.hour");
		}
		if(_mins>0){
			leaveTxt += _mins + ResourceUtil.getString("date.interval.minute");
		}
		if (leaveDay == 0 && _leaveHour == 0 && _mins == 0) {
			leaveTxt = "0";
		}
		return leaveTxt;
	}

	/**
	 * 格式化时间显示 格式统一为:xx天xx小时xx分钟
	 * @param convertTime 转换的标准小时数
	 * @param leaveDay
	 *            注意:天数若不是整数,则小数部分会累计到leaveHour
	 * @param leaveHour
	 *            注意:小时数会与leaveDay中的小数部分累计
	 * @return
	 */
	public static String formatLeaveTimeStr(Float convertTime,Float leaveDay, Float leaveHour) {
		leaveDay = leaveDay == null ? 0 : leaveDay;
		leaveHour = leaveHour == null ? 0f : leaveHour;
		if(convertTime==null){
			convertTime = SysTimeUtil.getConvertTime();
		}
		int _leaveDay = leaveDay.intValue();
		Float tmpLeaveDayHour = (leaveDay - _leaveDay) * convertTime;
		leaveHour = leaveHour + tmpLeaveDayHour;

		Float leaveHourDay = leaveHour / convertTime;
		_leaveDay += leaveHourDay.intValue();
		String leaveTxt = "";

		Float tmpLeaveHour = leaveHour % convertTime;
		int _leaveHour = tmpLeaveHour.intValue();
		Float mins = (tmpLeaveHour - tmpLeaveHour.intValue()) * 60;
		int _mins = Math.round(mins);
		if (_mins >= 60) {
			_leaveHour += _mins / 60;
			_mins = Math.round(_mins % 60);
		}
		if (_leaveDay > 0) {
			leaveTxt = _leaveDay + ResourceUtil.getString("date.interval.day");
		}
		if (_leaveHour > 0) {
			leaveTxt += _leaveHour
					+ ResourceUtil.getString("date.interval.hour");
		}
		if (_mins > 0) {
			leaveTxt += _mins
					+ ResourceUtil.getString("date.interval.minute");
		}
		if (_leaveDay == 0 && _leaveHour == 0 && _mins == 0) {
			leaveTxt = "0";
		}
		return leaveTxt;
	}

	/**
	 * 小时单位的值转成成小时分钟
	 * 格式化时间显示 格式统一为:xx小时xx分钟
	 * @param leaveHour 需要转换的小时
	 * @return xx小时xx分钟
	 */
	public static String formatHourTimeStr(Float leaveHour) {
		leaveHour = leaveHour == null ? 0f : leaveHour;
		int _leaveHour = leaveHour.intValue();
		Float mins = (leaveHour - leaveHour.intValue()) * 60;
		int _mins = Math.round(mins);
		if (_mins >= 60) {
			_leaveHour += _mins / 60;
			_mins = Math.round(_mins % 60);
		}
		if (_leaveHour == 0 && _mins == 0) {
			return "0";
		}
		//只显示小时和分钟数
		String leaveTxt="";
		if (_leaveHour > 0) {
			leaveTxt += _leaveHour
					+ ResourceUtil.getString("date.interval.hour");
		}
		if (_mins > 0) {
			leaveTxt += _mins
					+ ResourceUtil.getString("date.interval.minute");
		}
		return leaveTxt;
	}
	/**
	 * 格式化时间显示 格式统一为:xx天xx小时xx分钟
	 * 
	 * @param leaveDay
	 *            注意:天数若不是整数,则小数部分会累计到leaveHour
	 * @param leaveHour
	 *            注意:小时数会与leaveDay中的小数部分累计
	 * @return
	 */
	public static String formatLeaveTimeStr(Float leaveDay, Float leaveHour) {
		return formatLeaveTimeStr(SysTimeUtil.getConvertTime(),leaveDay,leaveHour);
	}

	/**
	 * 天数加小时相加成天数
	 * @param leaveDay
	 * @param leaveHour
	 * @return
	 */
	public static Float formatLeaveTimeToDay(Float leaveDay, Float leaveHour) throws Exception {
		leaveDay = leaveDay == null ? 0 : leaveDay;
		leaveHour = leaveHour == null ? 0f : leaveHour;
		double _leaveDay = leaveDay.doubleValue();
		//小时转成分钟
		int min =Float.valueOf(leaveHour * 60F).intValue();
		//分钟转成天
		Float hourDay = Float.valueOf(SysTimeUtil.getLeaveDays(min,3));
		//合计天
		return (float) (_leaveDay + hourDay.doubleValue());
	}

	public static String formatDecimal(Float value, int pos) {
		if (value == null) {
			return "0";
		}
		return NumberUtil.roundDecimal(value, 1);
	}

	public static String formatFloat(Float ff) {
		DecimalFormat df = new DecimalFormat("#.#");
		return df.format(ff);
	}

	public static int compareDecimal(Float value1, Float value2) {
		if (value1 == null || value2 == null) {
			return 0;
		}
		BigDecimal v1 = BigDecimal.valueOf(value1);
		BigDecimal v2 = BigDecimal.valueOf(value2);
		return v1.compareTo(v2);
	}


	/**
	 * 获取对应天数
	 * @param convertTime 转换时长 不存在则用系统的配置
	 * @param fdTotalTime
	 *            分钟数(注意按天时为24小时)
	 * @param fdStatType
	 *            类型:天,半天,小时(1,2,3)
	 * @return
	 * @throws Exception
	 */
	public static String getLeaveDays(Float convertTime,int fdTotalTime, Integer fdStatType)
			throws Exception {
		String fdLeaveTime = "0";
		fdLeaveTime = NumberUtil
				.roundDecimal((float) fdTotalTime / (60 * 24), 1);
		if (fdStatType != null && fdStatType == 3) {
			// 兼容处理
			if(convertTime ==null || Float.valueOf(0F).equals(convertTime)) {
				convertTime = getConvertTime();
			}
			// 转换为天
			fdLeaveTime = NumberUtil
					.roundDecimal((float) fdTotalTime / (60 * convertTime), 3);
		}
		return fdLeaveTime;
	}

	/**
	 * 获取对应天数
	 * 
	 * @param fdTotalTime
	 *            分钟数(注意按天时为24小时)
	 * @param fdStatType
	 *            类型:天,半天,小时(1,2,3)
	 * @return
	 * @throws Exception
	 */
	public static String getLeaveDays(int fdTotalTime, Integer fdStatType)
			throws Exception {
		String fdLeaveTime = "0";
		fdLeaveTime = NumberUtil
				.roundDecimal((float) fdTotalTime / (60 * 24), 1);
		if (fdStatType != null && fdStatType == 3) {// 兼容处理
			Float convertTime = getConvertTime();
			// 转换为天
			fdLeaveTime = NumberUtil
					.roundDecimal((float) fdTotalTime / (60 * convertTime), 3);
		}
		return fdLeaveTime;
	}

	/**
	 * 根据天数获取对应分钟数
	 * 
	 * @param days
	 * @param fdStatType
	 * @return
	 */
	public static int getLeaveMins(Float days, Integer fdStatType) {
		int mins = 0;
		mins = (int) (days * 24 * 60);
		if (fdStatType != null && fdStatType == 3) {
			Float convertTime = getConvertTime();
			mins = Math.round(convertTime * days * 60);
		}
		return mins;
	}

	/**
	 * 获取工时转换参数
	 * 
	 * @return
	 */
	public static Float getConvertTime() {
		Float convertTime = Float.valueOf(8);// 工时换算默认8小时
		try {
			SysTimeLeaveConfig leaveConfig = new SysTimeLeaveConfig();
			if (leaveConfig != null
					&& StringUtil.isNotNull(leaveConfig.getDayConvertTime())) {
				convertTime = Float.parseFloat(leaveConfig.getDayConvertTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return convertTime;
	}

	/**
	 * 计算请假时间
	 * @param person
	 * @param startTime
	 * @param endTime
	 * @param startNoon
	 * @param endNoon
	 * @param fdStatDayType  假期类型对应的工作日计算方式(1:工作日,2:自然日)
	 * @param statType
	 * @return 请假的分钟数/按照标准工时转换的天数
	 * @throws Exception
	 */
	public static SysTimeLeaveTimeDto getLeaveTimes(SysOrgElement person, Date startTime,
			Date endTime,
			Integer startNoon, Integer endNoon, Integer fdStatDayType,
			Integer statType,String fdLeaveType)
			throws Exception {
		SysTimeLeaveTimeDto leaveTimeDto =new SysTimeLeaveTimeDto();
		logger.debug("userName:" + person.getFdName() + ",startTime:"
				+ startTime + ",endTime:" + endTime + ",startNoon:" + startNoon
				+ ",endNoon:" + endNoon
				+ ",fdStatDayType:" + fdStatDayType + ",statType:" + statType);
		if (startTime == null || endTime == null) {
			return leaveTimeDto;
		}
		int leaveMins = 0;
		Float leaveDays = 0F;
		List<Date> dateList = getDateList(statType, startTime, endTime,
				startNoon, endNoon);
		if (dateList.size() < 2) {
			return leaveTimeDto;
		}
		for (int i = 0; i < dateList.size() - 1; i++) {
			Date leaveStart = dateList.get(i);
			Date leaveEnd = dateList.get(i + 1);
			Date date = getDate(leaveStart, 0);
			int daysMins = 0;
			Float everyDays = 0F;
			if (leaveStart == null || leaveEnd == null
					|| leaveStart.getTime() > leaveEnd.getTime()) {
				continue;
			}
			fdStatDayType = fdStatDayType == null ? Integer.valueOf(2)
					: fdStatDayType;// 默认自然日
			//如果是自然日。只算一天，半天
			long totalMin = getTotalMins(leaveStart, leaveEnd);
			if (statType == 1) {
				// 按天
				if (totalMin >= 24 * 60) {
					daysMins = 24 * 60;
					everyDays =1F;
				}
			} else if (statType == 2) {
				// 按半天
				if (totalMin > 0 && totalMin <= 12 * 60) {
					daysMins = 12 * 60;
					everyDays =0.5F;
				} else if (totalMin >= 24 * 60) {
					daysMins = 24 * 60;
					everyDays =1F;
				}
			} else {
				daysMins =0;
				everyDays =0F;
			}
			//自然日
			if(Integer.valueOf(2).equals(fdStatDayType)){
				leaveMins += daysMins;
				leaveDays += everyDays;
				continue;
			}
			//非休息日，计算按小时排班的请假
			if (isInAttendGroup(date, person)  && totalMin > 0) {
				//在考勤组中，判断当日是否有班次
				// 获取用户打卡时间
				List<Map<String, Object>> signTimeList = getAttendSignTimeList(person, date, 1);
				// 小时请假跨天处理
				boolean isOverType = false;
				if(CollectionUtils.isEmpty(signTimeList)){
					//没有排班，则认为是休息日
					daysMins =0;
					everyDays =0F;
				}
				//按小时
				if (statType == 3) {
					// 获取前一天的排班信息
					List<Map<String, Object>> lastSignTimeList = getAttendSignTimeList(
							person, getDate(date, -1),
							fdStatDayType);
					if (CollectionUtils.isNotEmpty(lastSignTimeList) && lastSignTimeList.size() >= 2) {
						Map<String, Object> offWorkMap = lastSignTimeList.get(lastSignTimeList.size() - 1);
						Integer overTimeType = (Integer) offWorkMap.get("overTimeType");
						isOverType = Integer.valueOf(2).equals(overTimeType);
					}
					if (CollectionUtils.isNotEmpty(signTimeList)) {
						String categoryId = getSysAttendCategoryService().getAttendCategory(person, leaveStart);
			            
			            SysAttendCategory sysAttendCategory = null;
			            // 缓存考勤组信息
			        
			            sysAttendCategory = CategoryUtil.getCategoryById(categoryId);
			            if(sysAttendCategory!=null && sysAttendCategory.getFdShiftType()==3){
			            	long ms = leaveEnd.getTime()-leaveStart.getTime();
			            	daysMins = (int) Math.ceil(ms / (1000 * 60));	
			            }else
						daysMins = getLeaveMins(leaveStart, leaveEnd, date,
								signTimeList);
						//定制开始，修改小时计算逻辑，不足一小时，按一小时计算，超过一个小时以半小时为最小单位计算,Add by liuyang
						if(!"10".equals(fdLeaveType)){
							if (daysMins < 60 && daysMins > 0) {
								daysMins = 60;
							} else {
								daysMins = (int) (Math.ceil(daysMins / 30.0) * 30);
							}
						}
						//定制开始，修改小时计算逻辑，不足一小时，按一小时计算，超过一个小时以半小时为最小单位计算,Add by liuyang
						Float fdWorkTimeHour = (Float) signTimeList.get(0).get("fdWorkTimeHour");
						if (fdWorkTimeHour != null && fdWorkTimeHour > 0) {
							everyDays = daysMins / (fdWorkTimeHour * 60);
						} else {
							//modify by liuyang
							//everyDays = daysMins / getConvertTime();
							everyDays = daysMins / (getConvertTime() * 60);
						}
					}
					/*** 跨天排班处理*/
					if (isOverType) {
						int tempDaysMins = getLeaveMins(leaveStart, leaveEnd,
								getDate(date, -1),
								lastSignTimeList);
						if (tempDaysMins > 0) {
							daysMins += tempDaysMins;
							Float fdWorkTimeHour = (Float) lastSignTimeList.get(0).get("fdWorkTimeHour");
							Float tempEveryDays = 0F;
							if (fdWorkTimeHour > 0) {
								tempEveryDays = tempDaysMins / (fdWorkTimeHour * 60);
							} else {
								//modify by liuyang
								//tempEveryDays = tempDaysMins / getConvertTime();
								tempEveryDays = tempDaysMins / (getConvertTime() * 60);
							}
							everyDays += tempEveryDays;
						}
					}
				}
			}else{
				daysMins =0;
				everyDays =0F;
			}
			leaveMins += daysMins;
			leaveDays +=everyDays;
		}
		leaveTimeDto.setLeaveTimeDays(roundString(leaveDays, 3));
		leaveTimeDto.setLeaveTimeMins(leaveMins);
		return leaveTimeDto;
	}

	public static List<Date> getDateList(Integer fdStatType, Date startTime,
			Date endTime, Integer fdStartNoon, Integer fdEndNoon) {
		List<Date> dateList = new ArrayList<Date>();
		if (fdStatType == null) {// 兼容以前数据
			dateList = getDateListByTime(startTime,
					endTime);
		} else if (fdStatType == 1) {// 按天
			dateList = getDateListByDay(startTime,
					endTime);
		} else if (fdStatType == 2) {// 按半天
			dateList = getDateListByHalfDay(startTime,
					endTime, fdStartNoon, fdEndNoon);
		} else if (fdStatType == 3) {// 按小时
			dateList = getDateListByTime(startTime,
					endTime);
		}
		return dateList;
	}

	/**
	 * 获取时间区间的分钟数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long getTotalMins(Date startTime, Date endTime) {
		if (startTime == null || endTime == null
				|| startTime.getTime() > endTime.getTime()) {
			return 0;
		}
		return (endTime.getTime() - startTime.getTime() )/1000/60;
	}

	public static int getLeaveMins1(Date startTime, Date endTime,
			Date date, List<Map<String, Object>> signTimeList) {
		try {
		    
			if (signTimeList != null && !signTimeList.isEmpty()) {
				long ms = 0L;
				for (int i = 0; i < signTimeList.size() / 2; i++) {
					// 上班
					Map<String, Object> startMap = signTimeList.get(2 * i);
					// 下班
					Map<String, Object> endMap = signTimeList.get(2 * i + 1);
					//标准的上班打卡时间
					Date startSignTime = (Date) startMap.get("signTime");
					//标准的下班打卡时间
					Date endSignTime = (Date) endMap.get("signTime");
					Boolean isFlex = (Boolean)startMap.get("fdIsFlex");
					long startTimeSecond = startTime.getTime();
					// 跨天排班 下班时间要加一天
					Integer overTimeType = (Integer) endMap.get("overTimeType");
					Date endTempDate = getDate(date, 0);
					if (Integer.valueOf(2).equals(overTimeType)) {
						endTempDate = getDate(date, 1);
					}
					startSignTime = joinYMDandHMS(date,
							startSignTime);
					endSignTime = joinYMDandHMS(endTempDate, endSignTime);
					if (startTime.compareTo(endSignTime) < 0
							&& endTime.compareTo(startSignTime) > 0) {
						if (startTime.compareTo(startSignTime) >= 0) {
							if (endTime.compareTo(endSignTime) <= 0) {
								ms += endTime.getTime() - startTime.getTime();
							} else {
								if(endTime.getTime()>AttendUtil.getDayLastTime(startTime).getTime()){
									if(startSignTime.getTime()>AttendUtil.getDayLastTime(startTime).getTime())
										if(endTime.getTime()>AttendUtil.getDayLastTime(startSignTime).getTime())
											ms += endSignTime.getTime()
													- startSignTime.getTime();
										else
											ms += endTime.getTime()
												- startSignTime.getTime();
									else
										if(isFlex&&startTime.getTime()-startSignTime.getTime()<=30*60*1000)
											ms += endSignTime.getTime()
											- startSignTime.getTime();
										else
											ms += endSignTime.getTime()
													- startTime.getTime();
								}
								else if(isFlex&&startTime.getTime()-startSignTime.getTime()<=30*60*1000)
									
									ms += endSignTime.getTime()
											- startSignTime.getTime();
									else
								ms += endTime.getTime()
										- startTime.getTime();
							}
						} else {
							if (endTime.compareTo(endSignTime) <= 0) {
								ms += endTime.getTime()
										- startSignTime.getTime();
							} else {
								ms += endSignTime.getTime()
										- startSignTime.getTime();
							}
						}
					}
				}
				// 去除午休时间
				if (signTimeList.size() == 2) {
					// 只有一个班次时，才有午休时间
					Map<String, Object> map = signTimeList.get(0);
					Date restStart = null;
					Date restEnd = null;
					if (map.containsKey("fdRestStartTime")
							&& map.containsKey("fdRestEndTime")) {
						restStart = (Date) map.get("fdRestStartTime");
						restEnd = (Date) map.get("fdRestEndTime");
					}
					if (restStart != null && restEnd != null) {
						//请假的开始时间小于午休结束时间，并且，结束时间大于午休开始时间
						if(startTime.getTime() < restEnd.getTime() && endTime.getTime() > restStart.getTime()){
							//请假开始时间 大于等于 午休时间
							if (startTime.compareTo(restStart) >= 0) {
								//请假结束时间 小于 等于 午休结束时间
								if (endTime.compareTo(restEnd) <= 0) {
									ms -= (endTime.getTime() - startTime.getTime());
								} else {
									ms -= (restEnd.getTime() - startTime.getTime());
								}
							} else {
								if (endTime.compareTo(restEnd) <= 0) {
									ms -= (endTime.getTime() - restStart.getTime());
								} else {
									ms -= (restEnd.getTime() - restStart.getTime());
								}
							}
						}
					}
				}
				return (int) Math.ceil(ms / (1000 * 60));
			}
		} catch (Exception e) {
			logger.error("获取请假时长异常{}",e);
		}
		return 0;
	}
	public static int getLeaveMins(Date startTime, Date endTime,
			Date date, List<Map<String, Object>> signTimeList) {
		try {
		    
			if (signTimeList != null && !signTimeList.isEmpty()) {
				long ms = 0L;
				for (int i = 0; i < signTimeList.size() / 2; i++) {
					// 上班
					Map<String, Object> startMap = signTimeList.get(2 * i);
					// 下班
					Map<String, Object> endMap = signTimeList.get(2 * i + 1);
					//标准的上班打卡时间
					Date startSignTime = (Date) startMap.get("signTime");
					//标准的下班打卡时间
					Date endSignTime = (Date) endMap.get("signTime");
					Boolean isFlex = (Boolean)startMap.get("fdIsFlex");
					long startTimeSecond = startTime.getTime();
					// 跨天排班 下班时间要加一天
					Integer overTimeType = (Integer) endMap.get("overTimeType");
					Date endTempDate = getDate(date, 0);
					if (Integer.valueOf(2).equals(overTimeType)) {
						endTempDate = getDate(date, 1);
					}
					startSignTime = joinYMDandHMS(date,
							startSignTime);
					endSignTime = joinYMDandHMS(endTempDate, endSignTime);
					if (startTime.compareTo(endSignTime) < 0
							&& endTime.compareTo(startSignTime) > 0) {
						if (startTime.compareTo(startSignTime) >= 0) {
							if (endTime.compareTo(endSignTime) <= 0) {
								ms += endTime.getTime() - startTime.getTime();
							} else {
								if(isFlex&&startTime.getTime()-startSignTime.getTime()<=30*60*1000)
									
									ms += endSignTime.getTime()
											- startSignTime.getTime();
								else
								ms += endSignTime.getTime()
										- startTime.getTime();
							}
						} else {
							if (endTime.compareTo(endSignTime) <= 0) {
								ms += endTime.getTime()
										- startSignTime.getTime();
							} else {
								ms += endSignTime.getTime()
										- startSignTime.getTime();
							}
						}
					}
				}
				// 去除午休时间
				if (signTimeList.size() == 2) {
					// 只有一个班次时，才有午休时间
					Map<String, Object> map = signTimeList.get(0);
					Date restStart = null;
					Date restEnd = null;
					if (map.containsKey("fdRestStartTime")
							&& map.containsKey("fdRestEndTime")) {
						restStart = (Date) map.get("fdRestStartTime");
						restEnd = (Date) map.get("fdRestEndTime");
					}
					if (restStart != null && restEnd != null) {
						//请假的开始时间小于午休结束时间，并且，结束时间大于午休开始时间
						if(startTime.getTime() < restEnd.getTime() && endTime.getTime() > restStart.getTime()){
							//请假开始时间 大于等于 午休时间
							if (startTime.compareTo(restStart) >= 0) {
								//请假结束时间 小于 等于 午休结束时间
								if (endTime.compareTo(restEnd) <= 0) {
									ms -= (endTime.getTime() - startTime.getTime());
								} else {
									ms -= (restEnd.getTime() - startTime.getTime());
								}
							} else {
								if (endTime.compareTo(restEnd) <= 0) {
									ms -= (endTime.getTime() - restStart.getTime());
								} else {
									ms -= (restEnd.getTime() - restStart.getTime());
								}
							}
						}
					}
				}
				return (int) Math.ceil(ms / (1000 * 60));
			}
		} catch (Exception e) {
			logger.error("获取请假时长异常{}",e);
		}
		return 0;
	}
	public static Date addDate(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}
	/**
	 * startTime和endTime为日期时间类型，返回分割好的日期列表 <br>
	 * 例子：startTime为2018-01-01 11:11，endTime为2018-01-03 00:00<br>
	 * 返回[2018-01-01 11:11, 2018-01-02 00:00, 2018-01-03 00:00]
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<Date> getDateListByTime(Date startTime, Date endTime) {
		List<Date> dateList = new ArrayList<Date>();
		dateList.add(startTime);
		Calendar cal = Calendar.getInstance();
		for (cal.setTime(getDate(startTime, 1)); cal.getTime()
				.compareTo(endTime) < 0; cal.add(Calendar.DATE, 1)) {
			dateList.add(cal.getTime());
		}
		dateList.add(endTime);
		return dateList;
	}

	/**
	 * startDate和endDate为日期类型，startNoon开始上下午标识，endNoon结束上下午标识
	 * 
	 * @param startDate
	 * @param endDate
	 * @param startNoon
	 * @param endNoon
	 * @return
	 */
	public static List<Date> getDateListByHalfDay(Date startDate, Date endDate,
			Integer startNoon, Integer endNoon) {
		if (startNoon == null || endNoon == null) {
			return getDateListByDay(startDate, endDate);
		}
		List<Date> dateList = new ArrayList<Date>();
		Date startTime = null;
		Date endTime = null;
		Calendar cal = Calendar.getInstance();
		if (startNoon == 1) {
			startTime = getDate(startDate, 0);
		} else if (startNoon == 2) {
			startTime = getDate(startDate, 0);
			cal.setTime(startTime);
			cal.set(Calendar.HOUR_OF_DAY, 12);
			startTime = cal.getTime();
		}
		if (endNoon == 1) {
			endTime = getDate(endDate, 0);
			cal.setTime(endTime);
			cal.set(Calendar.HOUR_OF_DAY, 12);
			endTime = cal.getTime();
		} else if (endNoon == 2) {
			endTime = getDate(endDate, 1);
		}
		if (startTime != null && endTime != null) {
			dateList = getDateListByTime(startTime, endTime);
		}
		return dateList;
	}

	/**
	 * startDate和endDate为日期类型
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getDateListByDay(Date startDate, Date endDate) {
		List<Date> dateList = new ArrayList<Date>();
		Date startTime = getDate(startDate, 0);
		Date endTime = getDate(endDate, 1);
		Calendar cal = Calendar.getInstance();
		for (cal.setTime(startTime); cal.getTime().compareTo(endTime) <= 0; cal
				.add(Calendar.DATE, 1)) {
			dateList.add(cal.getTime());
		}
		return dateList;
	}

	/**
	 * 是否休息日（包含节假日）
	 * 
	 * @param date
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private static boolean isRestDay(Date date, SysOrgElement org)
			throws Exception {
		Object service = SpringBeanUtil
				.getBean("sysAttendCategoryService");
		Class<?> clz = service.getClass();
		Method method = clz.getMethod("getAttendCategory",
				new Class[] { SysOrgElement.class, Date.class });
		String fdCategoryId = (String) method.invoke(service, org, date);
		return StringUtil.isNotNull(fdCategoryId) ? false : true;
	}

	/**
	 * 是否在考勤组
	 * @param date
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private static boolean isInAttendGroup(Date date, SysOrgElement org)
			throws Exception {
		Object service = SpringBeanUtil
				.getBean("sysAttendCategoryService");
		Class<?> clz = service.getClass();
		Method method = clz.getMethod("getCategory",
				new Class[] { SysOrgElement.class, Date.class, Boolean.class});
		String fdCategoryId = (String) method.invoke(service, org, date,Boolean.FALSE);
		return StringUtil.isNotNull(fdCategoryId) ? true : false;
	}
	/**
	 * 用户是否存在考勤组
	 * 
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private static boolean isExistCategory(SysOrgElement org) throws Exception {
		Object service = SpringBeanUtil
				.getBean("sysAttendCategoryService");
		Class<?> clz = service.getClass();
		Method method = clz.getMethod("getAttendCategory",
				new Class[] { SysOrgElement.class });
		String fdCategoryId = (String) method.invoke(service, org);
		return StringUtil.isNotNull(fdCategoryId) ? true : false;
	}

	/**
	 *
	 * @description:  获取考勤打卡时间
	 * @param org
	 * @param date
	 * @param workType
	 * @return: java.util.List
	 * @author: wangjf
	 * @time: 2022/3/7 5:18 下午
	 */
	public static List getAttendSignTimeList(SysOrgElement org, Date date,
			Integer workType)
			throws Exception {
		try {
			Object service = SpringBeanUtil
					.getBean("sysAttendCategoryService");
			Class<?> clz = service.getClass();
			boolean need = Integer.valueOf(2).equals(workType);
			Method method = clz.getMethod("getAttendSignTimesOfTime",
					new Class[]{SysOrgElement.class, Date.class, boolean.class});
			List<Map<String, Object>> signTimeList = (List<Map<String, Object>>) method
					.invoke(service, org, date, need);
			return signTimeList;
		} catch (Exception e) {
			logger.debug("执行方法sysAttendCategoryService.getAttendSignTimesOfTime，方法不存在",e);
		}
		return null;
	}

	/**
	 * 清空考勤模块的缓存
	 * @throws Exception
	 */
	public static void updateSignTimesCatch() {
		try {
				Object service = SpringBeanUtil.getBean("sysAttendCategoryService");
				Class<?> clz = service.getClass();
				Method method = clz.getMethod("clearSignTimesCache",
						new Class[]{});
				method.invoke(service);
		} catch (Exception e) {
			logger.debug("执行方法sysAttendCategoryService.updateSignTimesCatch，方法不存在",e);
		}
	}

	/**
	 *
	 * @description: 获取签到模块 签到工时(毫秒)
	 * @param element
	 * @param startTime
	 * @param endTime
	 * @return: long
	 * @author: wangjf
	 * @time: 2022/4/1 5:05 下午
	 */
	public static long getAttendManHour(SysOrgElement element, long startTime, long endTime){
		long manHour = -1;
		try {
			Object service = SpringBeanUtil.getBean("sysAttendCountService");
			Class<?> clz = service.getClass();
			Method method = clz.getMethod("getManHour",new Class[] { SysOrgElement.class, long.class, long.class });
			manHour = (long) method.invoke(service, element, startTime, endTime);
		}catch (RuntimeException e){
			logger.debug("执行方法sysAttendCountService.getAttendManHour时错误，无法获取sysAttendCountService可能是模块不存在，方法不存在",e);
		}catch (NoSuchMethodException e) {
			logger.debug("执行方法sysAttendCountService.getAttendManHour时错误，方法不存在",e);
		}catch (IllegalAccessException e) {
			logger.debug("执行方法sysAttendCountService.getAttendManHour时错误，没有访问权限的异常",e);
		}catch (InvocationTargetException e) {
			logger.error("执行方法sysAttendCountService.getAttendManHour时错误，反射执行异常",e);
		}
		return manHour;
	}

	/**
	 *
	 * @description: 获取签到模块 签到工时(天)
	 * @param element
	 * @param startTime
	 * @param endTime
	 * @return: long
	 * @author: wangjf
	 * @time: 2022/4/1 5:08 下午
	 */
	public static int getAttendWorkingDays(SysOrgElement element, long startTime, long endTime){
		int manDay = -1;
		try {
			Object service = SpringBeanUtil.getBean("sysAttendCountService");
			Class<?> clz = service.getClass();
			Method method = clz.getMethod("getWorkingDays",new Class[] { SysOrgElement.class, long.class, long.class });
			manDay = (int) method.invoke(service, element, startTime, endTime);
		}catch (RuntimeException e){
			logger.debug("执行方法sysAttendCountService.getWorkingDays时错误，无法获取sysAttendCountService可能是模块不存在",e);
		}catch (NoSuchMethodException e) {
			logger.debug("执行方法sysAttendCountService.getWorkingDays时错误，方法不存在",e);
		}catch (IllegalAccessException e) {
			logger.debug("执行方法sysAttendCountService.getWorkingDays时错误，没有访问权限的异常",e);
		}catch (InvocationTargetException e) {
			logger.error("执行方法sysAttendCountService.getWorkingDays时错误，反射执行异常",e);
		}
		return manDay;
	}

	/**
	 *
	 * @description: 根据组织架构id，开始时间和需要跳过的工时获取结束时间
	 * @param element
	 * @param startTime
	 * @param numberOfDate
	 * @param field
	 * @return: Date
	 * @author: wangjf
	 * @time: 2022/4/1 5:10 下午
	 */
	public static Date getAttendEndTimeForWorkingHours(SysOrgElement element, Date startTime, int numberOfDate, HoursField field){
		Date endTime = startTime;
		try {
			Object service = SpringBeanUtil.getBean("sysAttendCountService");
			Class<?> clz = service.getClass();
			Method method = clz.getMethod("getEndTimeForWorkingHours",new Class[] { SysOrgElement.class, Date.class, int.class, HoursField.class });
			endTime = (Date) method.invoke(service, element, startTime, numberOfDate, field);
		}catch (RuntimeException e){
			logger.debug("执行方法sysAttendCountService.getEndTimeForWorkingHours时错误，无法获取sysAttendCountService可能是模块不存在",e);
		}catch (NoSuchMethodException e) {
			logger.debug("执行方法sysAttendCountService.getEndTimeForWorkingHours时错误，方法不存在",e);
		}catch (IllegalAccessException e) {
			logger.debug("执行方法sysAttendCountService.getEndTimeForWorkingHours时错误，没有访问权限的异常",e);
		}catch (InvocationTargetException e) {
			logger.error("执行方法sysAttendCountService.getEndTimeForWorkingHours时错误，反射执行异常",e);
		}
		return endTime;
	}


	public static Date joinYMDandHMS(Date ymd, Date hms) {
		if (ymd == null || hms == null) {
			return null;
		}
		Date rtnDate = new Date(ymd.getTime());
		rtnDate.setHours(hms.getHours());
		rtnDate.setMinutes(hms.getMinutes());
		rtnDate.setSeconds(hms.getSeconds());
		return rtnDate;
	}

	public static Map<String, Date> getStartAndEndTime(Date startTime,
			Date endTime, Integer statType, Integer startNoon,
			Integer endNoon) {
		Map<String, Date> timeMap = new HashMap<String, Date>();
		Date leaveStart = null;
		Date leaveEnd = null;
		if (statType == null || statType == 3) {
			leaveStart = startTime;
			leaveEnd = endTime;
		} else if (statType == 1) {
			leaveStart = SysTimeUtil.getDate(startTime, 0);
			leaveEnd = SysTimeUtil.getDate(endTime, 1);
		} else if (statType == 2) {
			Calendar cal = Calendar.getInstance();
			if (startNoon != null && endNoon != null) {
				leaveStart = SysTimeUtil.getDate(startTime, 0);
				if (startNoon == 2) {
					cal.setTime(leaveStart);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					leaveStart = cal.getTime();
				}
				leaveEnd = SysTimeUtil.getDate(endTime, 0);
				if (endNoon == 1) {
					cal.setTime(leaveEnd);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					leaveEnd = cal.getTime();
				} else {
					cal.setTime(leaveEnd);
					cal.add(Calendar.DATE, 1);
					leaveEnd = cal.getTime();
				}
			} else {
				leaveStart = SysTimeUtil.getDate(startTime, 0);
				leaveEnd = SysTimeUtil.getDate(endTime, 1);
			}
		}
		timeMap.put("startTime", leaveStart);
		timeMap.put("endTime", leaveEnd);
		return timeMap;
	}

	/**
	 * 批量初始化用户场所缓存信息
	 * 
	 * @param orgList
	 *            用户id列表
	 * @throws Exception
	 */
	public static void initUserAuthAreaMap(List<String> orgList)
			throws Exception {
		if (!ISysAuthConstant.IS_AREA_ENABLED) {
			return;
		}
		if (orgList == null || orgList.isEmpty()) {
			return;
		}
		List<SysOrgPerson> eleList = getSysOrgCoreService().expandToPerson(orgList);
		for (SysOrgPerson person : eleList) {
			// authAreaMap缓存之后是否设置过默认场所，所以此处应该先判断是否有默认场所，而不是从缓存取
			SysAuthDefaultArea authDefArea = getSysAuthDefaultAreaService()
					.findValue(person.getFdId());
			if (authDefArea != null) {
				SysAuthArea sysAuthArea = authDefArea.getAuthArea();
				if (sysAuthArea != null) {
					authAreaMap.put(person.getFdId(),
							sysAuthArea.getFdId());
					continue;
				}
			}
			String fdAuthAreaId = authAreaMap.get(person.getFdId());
			if (StringUtil.isNotNull(fdAuthAreaId)) {
				continue;
			}
			UserAuthInfo authInfo = getSysOrgCoreService()
					.getOrgsUserAuthInfo(person);
			SysAuthArea sysAuthArea = getSysAuthAreaService()
					.getLoginArea(person, authInfo.getAuthOrgIds());
			if (sysAuthArea != null) {
				authAreaMap.put(person.getFdId(), sysAuthArea.getFdId());
			}
		}
	}

	/**
	 * 获取用户对应场所(若是当前登录用户,则获取当前运行环境的场所)
	 * 
	 * @param orgId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	public static String getUserAuthAreaId(String orgId) throws Exception {
		if (!ISysAuthConstant.IS_AREA_ENABLED) {
			return null;
		}
		if (StringUtil.isNull(orgId)) {
			return null;
		}
		String fdAuthAreaId = authAreaMap.get(orgId);
		if (orgId.equals(UserUtil.getKMSSUser().getUserId())) {
			fdAuthAreaId = UserUtil.getKMSSUser().getAuthAreaId();
		}
		if (StringUtil.isNotNull(fdAuthAreaId)) {
			return fdAuthAreaId;
		}
		List<String> orgList = new ArrayList<String>();
		orgList.add(orgId);
		initUserAuthAreaMap(orgList);
		return authAreaMap.get(orgId);
	}

	/**
	 * 获取用户对应场所
	 * 
	 * @param orgList 用户id列表
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getUserAuthAreaMap(List<String> orgList)
			throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		if (!ISysAuthConstant.IS_AREA_ENABLED) {
			return resultMap;
		}
		if (orgList == null || orgList.isEmpty()) {
			return resultMap;
		}
		initUserAuthAreaMap(orgList);
		for (String orgId : orgList) {
			String fdAuthAreaId = authAreaMap.get(orgId);
			resultMap.put(orgId, fdAuthAreaId);
		}
		return resultMap;
	}

	/**
	 * 获取用户对应场所(若是当前登录用户,则获取当前运行环境的场所)
	 * 
	 * @param orgId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	public static SysAuthArea getUserAuthArea(String orgId) throws Exception {
		if (!ISysAuthConstant.IS_AREA_ENABLED) {
			return null;
		}
		if (StringUtil.isNull(orgId)) {
			return null;
		}
		SysAuthArea authArea = null;
		String fdAuthAreaId = authAreaMap.get(orgId);
		if (orgId.equals(UserUtil.getKMSSUser().getUserId())) {
			fdAuthAreaId = UserUtil.getKMSSUser().getAuthAreaId();
		}
		if (StringUtil.isNull(fdAuthAreaId)) {
			List<String> orgList = new ArrayList<String>();
			orgList.add(orgId);
			initUserAuthAreaMap(orgList);
			fdAuthAreaId = authAreaMap.get(orgId);
		}
		if (StringUtil.isNotNull(fdAuthAreaId)) {
			authArea = (SysAuthArea) getSysAuthAreaService()
					.findByPrimaryKey(fdAuthAreaId);
		}
		return authArea;
	}
	
	/**
	 * 构造not in语句，若valueList超过1000时，该函数会自动拆分成多个not in语句
	 * 
	 * @param item
	 * @param valueList
	 * @return item in (valueList)
	 */
	public static String buildLogicNotIN(String item, List valueList) {
		if(null==valueList || valueList.isEmpty()) {
			return " (1=1) ";
		}
		int n = (valueList.size() - 1) / 1000;
		StringBuffer rtnStr = new StringBuffer();
		Object obj = valueList.get(0);
		boolean isString = false;
		if (obj instanceof Character || obj instanceof String) {
			isString = true;
		}
		String tmpStr;
		for (int i = 0; i <= n; i++) {
			int size = i == n ? valueList.size() : (i + 1) * 1000;
			if (i > 0) {
				rtnStr.append(" or ");
			}
			rtnStr.append(item + " not in (");
			if (isString) {
				StringBuffer tmpBuf = new StringBuffer();
				for (int j = i * 1000; j < size; j++) {
					tmpStr = valueList.get(j).toString().replaceAll("'", "''");
					tmpBuf.append(",'").append(tmpStr).append("'");
				}
				tmpStr = tmpBuf.substring(1);
			} else {
				tmpStr = valueList.subList(i * 1000, size).toString();
				tmpStr = tmpStr.substring(1, tmpStr.length() - 1);
			}
			rtnStr.append(tmpStr);
			rtnStr.append(")");
		}
		if (n > 0) {
			return "(" + rtnStr.toString() + ")";
		} else {
			return rtnStr.toString();
		}
	}

	/**
	 * 计算加班天数
	 * @param mins
	 * @return
	 */
	public static  Float getOvertimeDays(Float mins) {
		float days = 0f;
		Float convertTime = SysTimeUtil.getConvertTime();
		days = (float) mins / (60f * convertTime);
		return roundString(days, 3);
	}
	public static  Float getOvertimeDays(Float mins,Float convertTime) {
		float days = 0f;
		if(convertTime ==null || convertTime ==0){
			convertTime = SysTimeUtil.getConvertTime();
		}
		days = (float) mins / (60f * convertTime);
		return roundString(days, 3);
	}

	public static Float getOverDays(Float mins) {
		float days = 0f;
		Float convertTime = SysTimeUtil.getConvertTime();
		if (mins!=null) {
			days=mins/convertTime;
		}
		return days;
	}

	public static Float getOverDays(Float mins,Float convertTime) {
		float days = 0f;
		if(convertTime ==null || convertTime ==0){
			convertTime = SysTimeUtil.getConvertTime();
		}
		if (mins!=null) {
			days=mins/convertTime;
		}
		return days;
	}

	/**
	 * 获取某个人某一天的标准工作时长
	 * @param userId
	 * @param date
	 * @param signTimeList
	 * @return 分钟数
	 */
	public static int getStandWorkTime(String userId,Date date, List<Map<String, Object>> signTimeList) {
		try {
			if (signTimeList != null && !signTimeList.isEmpty()) {
				//最早的打卡班次配置
				Map<String, Object> startMap = signTimeList.get(0);
				//最晚的打卡班次配置
				Map<String, Object> endMap = signTimeList.get(signTimeList.size() -1 );
				//标准的上班打卡时间
				Date startSignTime = (Date) startMap.get("signTime");
				//标准的下班打卡时间
				Date endSignTime = (Date) endMap.get("signTime");
				// 跨天排班 下班时间要加一天
				Integer overTimeType = (Integer) endMap.get("overTimeType");
				Date endTempDate = getDate(date, 0);
				if (Integer.valueOf(2).equals(overTimeType)) {
					endTempDate = getDate(date, 1);
				}
				startSignTime = joinYMDandHMS(date,
						startSignTime);
				endSignTime = joinYMDandHMS(endTempDate, endSignTime);
				//获取当天应该工作的时长。
				long workTimeLong = getSysTimeCountService().getManHour(userId,startSignTime.getTime(), endSignTime.getTime());
				//分钟数
				return (int) (workTimeLong / 60  /1000);
			}
		} catch (Exception e) {
			logger.error("排班管理，计算标准上班工时错误{}",e.getMessage());
		}
		return 0;
	}

	public static Float roundString(Float num, int scale) {
		BigDecimal a = new BigDecimal(1);
		BigDecimal b = new BigDecimal(num);
		return b.divide(a, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}
	/**
	 * 缓存人员跟排班组的假期关系
	 */
	public static KmssCache SysTimeAreaOrgHolidayChache = new KmssCache(SysTimeArea.class,getConfig());
	/**
	 * 缓存人员跟排班组的关系
	 */
	public static KmssCache SysTimeAreaOrgChache = new KmssCache(SysTimeArea.class,getConfig());
	/**
	 * 排班组的缓存
	 */
	public static KmssCache SysTimeAreaChache = new KmssCache(SysTimeArea.class,getConfig());

	private static CacheConfig getConfig() {
		CacheConfig config = CacheConfig.get(SysTimeArea.class);
		config.maxElementsInMemory = 1000;
		config.overflowToDisk = true;
		config.timeToLiveSeconds = 3600;
		config.timeToIdleSeconds = 60;
		//默认是永久有效，这时候上面的两个参数timeToLiveSeconds和timeToIdleSeconds就没用了，这里设为非永久有效
		config.eternal = true;
		return config;
	}

	public static List<Date> convertDateList(String s){
		if(StringUtil.isNull(s)){
			return null;
		}
		List<Date> dateList = new ArrayList<>();
		String[] strings = s.split(",");
		for(String s1 : strings){
			Date date = DateUtil.convertStringToDate(s1, null, null);
			dateList.add(date);
		}
		return dateList;
	}
}
