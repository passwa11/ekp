package com.landray.kmss.hr.staff.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffRatifyLog;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * @Author HermiteZhang
 * @Email zhangw9409@gmail.com
 * @Date create in 18:07 2020/2/19
 * @Desc:
 */
public class HrStaffPortletUtil {

    // 转正
    public final static String TYPE_POSITIVE = "positive";
    // 入职
    public final static String TYPE_ENTRY = "entry";
    // 在职
    public final static String TYPE_ONTHEJOB = "onTheJob";
    // 离职
    public final static String TYPE_LEAVE = "leave";
    // 生日
    public final static String TYPE_BIRTHDAY = "birthday";
    //合同
    public final static String TYPE_CONTRACT = "contract";
    //调岗
    public final static String TYPE_TRANSFER = "transfer";
    //周年
    public final static String TYPE_ANNUAL = "annual";

    /**
     * 获取当前日期当月的第一天和最后一天
     *
     * @return
     */
    public static Date[] getStartAndEndDayOfMonth() {
        return getStartAndEndDayOfMonth(new Date());
    }

    /**
     * 获取指定日期当月的第一天和最后一天
     *
     * @param date
     * @return
     */
    public static Date[] getStartAndEndDayOfMonth(Date date) {
        date = date == null ? new Date() : date;
        // 日期-筛选
        // 获取当月第一天和最后一天
        Calendar cale = null;
        cale = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String ymd = format.format(date);
        Long time = 0L;
        try {
            time = format.parse(ymd).getTime();
        } catch (ParseException e) {
            time = date.getTime();
        }
        cale.setTime(new Date(time));
        Date firstday, lastday;
        // 获取前月的第一天
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = cale.getTime();
        // 获取前月的最后一天
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        cale.set(Calendar.HOUR_OF_DAY, 23);
        cale.set(Calendar.MINUTE, 59);
        cale.set(Calendar.SECOND, 59);

        cale.set(Calendar.MILLISECOND, 999);
        lastday = cale.getTime();

        Date[] darry = {firstday, lastday};
        return darry;
    }

    /**
     * 获取当前日期的年月日星期等。。
     *
     * @param startDateOfMonth
     * @return
     */
    public static Map<String, Object> getCalendYMWD(Date startDateOfMonth) {
        Map<String, Object> calendFormat = new HashMap<>();
        Calendar cale = null;
        cale = Calendar.getInstance();
        cale.setTime(new Date());
        calendFormat.put("day", cale.get(Calendar.DAY_OF_MONTH));
        cale.setTime(startDateOfMonth);
        calendFormat.put("week", cale.get(Calendar.DAY_OF_WEEK) - 1);
        calendFormat.put("year", cale.get(Calendar.YEAR));
        calendFormat.put("month", cale.get(Calendar.MONTH) + 1);
        return calendFormat;
    }


    /**
     * 判断状态(入职，在职，离职，转正，生日，周年)
     *
     * @param personInfo
     * @param period
     * @return
     */
    public static boolean judge(HrStaffPersonInfo personInfo, Date[] period, String type) {
        switch (type) {
            case TYPE_ONTHEJOB:
                return judgeOnTheJob(personInfo);
            case TYPE_ENTRY:
                return judgeEntry(personInfo, period);
            case TYPE_POSITIVE:
                return judgePositive(personInfo, period);
            case TYPE_LEAVE:
                return judgeLeave(personInfo, period);
            case TYPE_BIRTHDAY:
                return judgeBirthday(personInfo, period);
            case TYPE_ANNUAL:
                return judgeAnnual(personInfo, period);
        }
        if (personInfo.getFdStatus() == null) {
            return false;
        }
        String status = personInfo.getFdStatus();
        Boolean leave = "dismissal".equals(status) || "leave".equals(status)
                || "retire".equals(status);
        return !leave;
    }

    /**
     * 获取日历索引
     *
     * @param dateArray
     * @param calendMap
     * @return
     */
    public static List<Map> getIdxOfCalendMap(Date[] dateArray, Map<String, Map> calendMap) {
        List<Map> dateIdx = new ArrayList<>();
        Date date = dateArray[0];
        while (true) {
            Map<String, Object> dateIdxMap = new HashMap<>();
            String dateFormat = DateUtil.convertDateToString(date,
                    DateUtil.PATTERN_DATE);
            boolean isToDo = false;
            if (calendMap.get(dateFormat) != null) {
                isToDo = true;
            }
            dateIdxMap.put("date", dateFormat);
            dateIdxMap.put("isToDo", isToDo);
            dateIdx.add(dateIdxMap);
            date = new Date(date.getTime() + 86400000L);
            if (date.after(dateArray[1])) {
                break;
            }
        }
        return dateIdx;
    }

    /**
     * 数据组装，时间戳为key
     *
     * @param key
     * @param typeKey
     * @param calendMap
     */
    public static void formatCalendMap(String key, String typeKey,
                                       Map<String, Map> calendMap) {

        Map<String, Integer> dayInfo = calendMap.get(key);
        if (dayInfo == null) {
            dayInfo = new HashMap<String, Integer>();
            dayInfo.put(typeKey, 1);
        } else {
            Integer i = dayInfo.get(typeKey);
            if (i == null) {
                i = 1;
            } else {
                i += 1;
            }
            dayInfo.put(typeKey, i);
        }
        calendMap.put(key, dayInfo);
    }
    //--------------------------------------判断了----------------------------------------------------------//

    /**
     * 功能 - 判断当月在职,不判断过去或将来的时间的在职
     *
     * @param personInfo
     * @return
     */
    private static boolean judgeOnTheJob(HrStaffPersonInfo personInfo) {
        if (personInfo.getFdStatus() == null) {
            return false;
        }
        String status = personInfo.getFdStatus();
        Boolean leave = "dismissal".equals(status) || "leave".equals(status)
                || "retire".equals(status);
        return !leave;
    }

    /**
     * 功能 - 判断当月入职(已入职)
     *
     * @param personInfo
     * @param period
     * @return
     */
    private static boolean judgeEntry(HrStaffPersonInfo personInfo, Date[] period) {
        if (personInfo.getFdEntryTime() == null) {
            return false;
        }
        return !personInfo.getFdEntryTime().before(period[0])
                && personInfo.getFdEntryTime().before(period[1]);
    }

    /**
     * 功能 - 判断当月转正
     *
     * @param personInfo
     * @param period
     * @return
     */
    private static boolean judgePositive(HrStaffPersonInfo personInfo, Date[] period) {
        if (personInfo.getFdPositiveTime() == null) {
            return false;
        }
        return !personInfo.getFdPositiveTime().before(period[0])
                && personInfo.getFdPositiveTime().before(period[1]);
    }

    /**
     * 功能 - 判断当月离职
     *
     * @param personInfo
     * @param period
     * @return
     */
    private static boolean judgeLeave(HrStaffPersonInfo personInfo,
                                      Date[] period) {
        if (personInfo.getFdLeaveTime() == null) {
            return false;
        }
        return !personInfo.getFdLeaveTime().before(period[0])
                && personInfo.getFdLeaveTime().before(period[1]);
    }

    /**
     * 功能 - 判断当月生日
     *
     * @param personInfo
     * @param period
     * @return
     */
    private static boolean judgeBirthday(HrStaffPersonInfo personInfo,
                                         Date[] period) {
		if (personInfo == null || personInfo.getFdDateOfBirth() == null) {
            return false;
        }
		// 获取生日时间
		Date birthDate = personInfo.getFdDateOfBirth();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(birthDate);
		int birthMonth = calendar.get(Calendar.MONTH) + 1; // 获取生日月份
		int birthDay = calendar.get(Calendar.DATE); // 获取生日日期
		// 当前月份
		Calendar cale = null;
		cale = Calendar.getInstance();
		cale.setTime(period[0]);
		int curMonth = cale.get(Calendar.MONTH) + 1;
		int startDate = cale.get(Calendar.DATE);
		cale.setTime(period[1]);
		int endDate = cale.get(Calendar.DATE);
		if (curMonth == birthMonth && startDate <= birthDay
				&& birthDay <= endDate) {
			return true;
		} else {
			return false;
		}

    }

    /**
     * 功能 - 判断当月周年
     *
     * @param personInfo
     * @param period
     * @return
     */
    private static boolean judgeAnnual(HrStaffPersonInfo personInfo,
                                       Date[] period) {
        if (personInfo == null || personInfo.getFdEntryTime() == null) {
            return false;
        }
        //到本公司的日期
        Calendar cale = Calendar.getInstance();
        cale.setTime(personInfo.getFdEntryTime());  
        //比较的结束时间，减去一年 > 到本公司的日期
        Calendar currentEndDate = null;
        currentEndDate = Calendar.getInstance();
        currentEndDate.setTime(period[1]);
        
        Calendar endDate = null;
        endDate = Calendar.getInstance();
        endDate.setTime(period[1]);
        endDate.add(Calendar.MONTH, -11); 
        if(cale.getTimeInMillis() <= endDate.getTimeInMillis()) {
        	//到本公司的时间，跟当前结束日期比较 大于1年,考虑到2-29号这里减去11个月再来判断月份相等 
        	if(cale.get(Calendar.MONTH) ==currentEndDate.get(Calendar.MONTH)) {
        	    //并且是同一个月
        		return true;
        	}
        } 
        return false; 
    }

    //--------------------------------------判断-需要搜索其他service----------------------------------------------------------//


    /**
     * 功能 合同到期
     *
     * @param list
     * @return
     */
    public static JSONObject getContract(List<HrStaffPersonExperienceContract> list) {
        JSONObject contract = new JSONObject();
        Map<String, Map> calendMap = null;
        int count = 0;
        try {
            calendMap = new HashMap<>();
            for (HrStaffPersonExperienceContract ec : list) {
                String key = DateUtil.convertDateToString(
                        ec.getFdEndDate(), DateUtil.PATTERN_DATE);
                formatCalendMap(key, "contract", calendMap);

                count++;
            }
            if (count > 0) {
                contract.put("map", calendMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        contract.put("count", count);
        return contract;
    }

    /**
     * 功能 - 判断当月入职(拟入职)
     *
     * @param
     * @param list
     * @return
     */
    public static JSONObject getEntry(List<HrStaffEntry> list) {
        JSONObject transfer = new JSONObject();
        Map<String, Map> transferMap = null;
        int count = 0;
        try {
            transferMap = new HashMap<>();
            for (HrStaffEntry tr : list) {
                String key = DateUtil.convertDateToString(
                        tr.getFdPlanEntryTime(), DateUtil.PATTERN_DATE);
                formatCalendMap(key, "entry", transferMap);
                count++;
            }
            if (count > 0) {
                transfer.put("map", transferMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        transfer.put("count", count);
        return transfer;
    }

    /**
     * 功能 调岗
     *
     * @param
     * @param list
     * @return
     */

    public static JSONObject getTransfer(List<HrStaffRatifyLog> list) {
        JSONObject transfer = new JSONObject();
        Map<String, Map> transferMap = null;
        int count = 0;
        try {
            transferMap = new HashMap<>();
            for (HrStaffRatifyLog tr : list) {
                String key = DateUtil.convertDateToString(
                        tr.getFdRatifyDate(), DateUtil.PATTERN_DATE);
                formatCalendMap(key, "transfer", transferMap);
                count++;
            }
            if (count > 0) {
                transfer.put("map", transferMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        transfer.put("count", count);
        return transfer;
    }

    /**
     * 功能 面试（暂时移除）
     *
     * @param personInfo
     * @param period
     * @return
     */
    /*
     * private JSONObject getViewPlan(Date[] period) { JSONObject viewplan = new
     * JSONObject(); Map<String, Map> viewplanMap = null; int count = 0;
     *
     * try { List<HrRecruitViewPlan> list = getHrRecruitViewPlanServiceImp()
     * .findList( createTimeHql("hrRecruitViewPlan.fdViewerTime", period));
     * viewplanMap = new HashMap<>(); for (HrRecruitViewPlan ec : list) { String
     * key = DateUtil.convertDateToString( ec.getFdViewerTime(),
     * DateUtil.PATTERN_DATE); formatCalendMap(key, "viewplan", viewplanMap);
     *
     * count++; } if (count > 0) { viewplan.put("map", viewplanMap); } } catch
     * (Exception e) { e.printStackTrace(); } viewplan.put("count", count);
     * return viewplan; }
     */


    //-----------------------sql build ------------------------------------//

    /**
     * where *** and ( hrStaffPersonInfo.fdEntryTime>= :startTime and hrStaffPersonInfo.fdEntryTime <= :endTime  )
     *
     * @param hqlInfo
     * @param period
     */
    public static void buildPersonListSql(HQLInfo hqlInfo, Date[] period) {
        StringBuffer whereblock = new StringBuffer();
		Calendar satrtCal = Calendar.getInstance();
		satrtCal.setTime(period[0]);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(period[1]);
		// 获取period 的起始月日，和结束月日
		int startMonth = satrtCal.get(Calendar.MONTH) + 1;
		int startDay = satrtCal.get(Calendar.DATE);
		int endMonth = endCal.get(Calendar.MONTH) + 1;
		int endDay = endCal.get(Calendar.DATE);
        if (StringUtil.isNotNull(hqlInfo.getWhereBlock())) {
            whereblock.append(hqlInfo.getWhereBlock());
        }
        else {
            whereblock.append(" 1=1 ");
        }
        whereblock.append(" and ( ");
        whereblock.append(" (hrStaffPersonInfo.fdEntryTime>= :startTime and hrStaffPersonInfo.fdEntryTime <= :endTime ) or");
        whereblock.append(" (hrStaffPersonInfo.fdLeaveTime>= :startTime and hrStaffPersonInfo.fdLeaveTime <= :endTime ) or");
        whereblock.append(" (hrStaffPersonInfo.fdPositiveTime>= :startTime and hrStaffPersonInfo.fdPositiveTime <= :endTime) or");
		// 判断起始月<=月（生日）<=结束月 ，起始日<=日（生日）<=结束日
		whereblock.append(
				" (Month(hrStaffPersonInfo.fdDateOfBirth)>= :startMonth and Month(hrStaffPersonInfo.fdDateOfBirth)<=:endMonth and Day(hrStaffPersonInfo.fdDateOfBirth)>=:startDay and  Day(hrStaffPersonInfo.fdDateOfBirth)<=:endDay and hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice')) or");
        //月份 用于周年
        whereblock.append(" (MONTH(hrStaffPersonInfo.fdEntryTime) =:currentMonth)"); 
        whereblock.append(" )"); 
        hqlInfo.setWhereBlock(whereblock.toString());
        hqlInfo.setParameter("startTime", period[0]);
        hqlInfo.setParameter("endTime", period[1]);
        Calendar cal = Calendar.getInstance();
		hqlInfo.setParameter("startMonth", startMonth);
		hqlInfo.setParameter("endMonth", endMonth);
		hqlInfo.setParameter("startDay", startDay);
		hqlInfo.setParameter("endDay", endDay);
        //按月查下，获取周年
        hqlInfo.setParameter("currentMonth", cal.get(Calendar.MONTH)+1);
    }

    /**
     * 构建时间区间hql
     *
     * @param key
     * @param period
     * @return
     */
    public static HQLInfo createTimeHql(String key, Date[] period) {
        HQLInfo hqlInfo = new HQLInfo();
        String whereBlock = key + " >= :startTime and  " + key + " <= :endTime ";
        hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setParameter("startTime", period[0]);
        hqlInfo.setParameter("endTime", period[1]);
        return hqlInfo;
    }

    public  static HQLInfo createOnJobHql(){
         /*
        SELECT COUNT(*) FROM hr_staff_person_info
        WHERE fd_leave_time >=dateadd(month, datediff(month, 0, getdate()), 0)
        AND fd_leave_time<=dateadd(month, datediff(month, 0, dateadd(month, 1, getdate())), -1)
        AND fd_status IN('dismissal','leave','retire');
         */
        HQLInfo hqlInfo = new HQLInfo();
        String whereBlock = " hrStaffPersonInfo.fdStatus IN('dismissal','leave','retire') ";
        hqlInfo.setWhereBlock(whereBlock);
        return hqlInfo;
    }
}
