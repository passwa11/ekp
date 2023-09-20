package com.landray.kmss.sys.attend.service.business;

import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.util.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: <p>考勤时间是每周相同，但是每天的时间可以不一样，规则如下：
 * 1、签到日期规则存储在sys_attend_category_tsheet中
 * 2、存在假期（存在补卡日期）
 * 3、存在排除日期
 * 4、可能存在周末（需要根据字段进行判断）
 * </p>
 * @author: wangjf
 * @time: 2022/3/29 5:01 下午
 * @version: 1.0
 */

public class WeekDiffBusinessCalculatorProvide extends AbstractBusinessCalculatorProvide {

    /**
     * 每周周规律上班的周期,根据周的第几天进行数据的获取
     */
    private Map<String, SysAttendCategoryTimesheet> weekWorkDayMap;

    public WeekDiffBusinessCalculatorProvide(SysAttendCategory sysAttendCategory) {
        super(sysAttendCategory);
    }

    /**
     * @param
     * @description: 个性化初始化
     * @return: void
     * @author: wangjf
     * @time: 2022/3/31 10:52 上午
     */
    @Override
    protected void initialization() {
        this.weekWorkDayMap = new HashMap<>();
        //每周相同，但是每天的时间可以不一样
        List<SysAttendCategoryTimesheet> fdTimeSheets = this.sysAttendCategory.getFdTimeSheets();
        for (SysAttendCategoryTimesheet fdTimeSheet : fdTimeSheets) {
            //把周按照每天进行拆解
            String[] fdWeeks = fdTimeSheet.getFdWeek().split(";");
            for (String fdWeek : fdWeeks) {
                this.weekWorkDayMap.put(fdWeek, fdTimeSheet);
            }
        }

    }

    /**
     * @param date
     * @description: 检查是否是工作日
     * @return: boolean
     * @author: wangjf
     * @time: 2022/3/30 2:55 下午
     */
    @Override
    protected boolean checkBusinessDay(Date date) {
        String dayOfWeek = String.valueOf(AttendUtil.getWeek(date));
        if (this.weekWorkDayMap.get(dayOfWeek) != null) {
            return true;
        }
        return false;
    }

    /**
     * 获取打卡时间规则
     *
     * @param date
     * @return: com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet
     * @author: wangjf
     * @time: 2022/4/28 11:04 上午
     */
    private SysAttendCategoryTimesheet getSysAttendCategoryTimesheet(Date date) {

        //有正常的打卡规则
        SysAttendCategoryTimesheet sysAttendCategoryTimesheet = this.weekWorkDayMap.get(String.valueOf(AttendUtil.getWeek(date)));
        if (sysAttendCategoryTimesheet != null) {
            return sysAttendCategoryTimesheet;
        }
        //说明可能存在补班或者存在没安排打卡日期但是需要打卡的日期，补班获取的时间为每周的开始第一天的时间，每周开始时间为周一
        for (int i = 0; i < 8; i++) {
            sysAttendCategoryTimesheet = weekWorkDayMap.get(String.valueOf(i));
            if (sysAttendCategoryTimesheet != null) {
                break;
            }
        }
        return sysAttendCategoryTimesheet;
    }

    @Override
    protected TimeRange getTimeRange(Date date) {
        TimeRange timeRange = new TimeRange();
        timeRange.setDate(AttendUtil.getDate(date, 0));
        //补班一定要上班，优先级最高；工作日和假期，假期的优先级比工作日优先级高，是工作日并且不是假期才上班
        if (checkPatchDay(date) || (checkBusinessDay(date) && !checkHoliday(date))) {
            SysAttendCategoryTimesheet sysAttendCategoryTimesheet = getSysAttendCategoryTimesheet(date);
            //班次1
            SysAttendCategoryWorktime workTimeOne = sysAttendCategoryTimesheet.getAvailWorkTime().get(0);
            //班次2
            SysAttendCategoryWorktime workTimeTwo = null;
            //存在两个班次
            if (2 == sysAttendCategoryTimesheet.getFdWork()) {
                workTimeTwo = sysAttendCategoryTimesheet.getAvailWorkTime().get(1);
            }
            //班次
            timeRange.setWork(sysAttendCategoryTimesheet.getFdWork());
            timeRange.setStartTime(DateUtil.getTimeNubmer(workTimeOne.getFdStartTime()));
            //如果工作结束时间是跨天
            if (2 == workTimeOne.getFdOverTimeType()) {
                //结束时间+1天毫秒数-开始时间=结余时间毫秒数+开始毫秒数=结束毫秒数
                timeRange.setEndTime(DateUtil.getTimeNubmer(workTimeOne.getFdEndTime()) + DAY_TIME_MILLISECONDS - DateUtil.getTimeNubmer(workTimeOne.getFdStartTime()) + timeRange.getStartTime());
            } else {
                timeRange.setEndTime(DateUtil.getTimeNubmer(workTimeOne.getFdEndTime()));
            }
            //如果是两个班次
            if (2 == sysAttendCategoryTimesheet.getFdWork()) {
                timeRange.setStartTime2(DateUtil.getTimeNubmer(workTimeTwo.getFdStartTime()));
                //如果工作结束时间是跨天
                if (2 == workTimeTwo.getFdOverTimeType()) {
                    timeRange.setEndTime2(DateUtil.getTimeNubmer(workTimeTwo.getFdEndTime()) + DAY_TIME_MILLISECONDS - DateUtil.getTimeNubmer(workTimeTwo.getFdStartTime()) + timeRange.getStartTime2());
                } else {
                    timeRange.setEndTime2(DateUtil.getTimeNubmer(workTimeTwo.getFdEndTime()));
                }

            } else {
                timeRange.setStartRestTime(DateUtil.getTimeNubmer(sysAttendCategoryTimesheet.getFdRestStartTime()));
                timeRange.setEndRestTime(DateUtil.getTimeNubmer(sysAttendCategoryTimesheet.getFdRestEndTime()));
            }
        }
        return timeRange;
    }

}