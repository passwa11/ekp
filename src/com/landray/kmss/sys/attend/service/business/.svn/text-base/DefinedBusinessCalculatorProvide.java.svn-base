package com.landray.kmss.sys.attend.service.business;

import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTime;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.util.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: <p>
 * 考勤组时间是自定义有如下规则：
 * 1、考勤组时间是自定义，存储sys_attend_category_time中
 * 2、无排除日期
 * 3、签到开始和结束时间从SysAttendCategory中获取
 *
 * </p>
 * @author: wangjf
 * @time: 2022/3/29 5:01 下午
 * @version: 1.0
 */

public class DefinedBusinessCalculatorProvide extends AbstractBusinessCalculatorProvide {

    /**
     * 工作日
     */
    private Map<String, Date> workDayMap;

    /**
     * @param sysAttendCategory
     * @description: 构造器不允许空构造
     * @time: 2022/3/29 10:00 下午
     */
    public DefinedBusinessCalculatorProvide(SysAttendCategory sysAttendCategory) {
        super(sysAttendCategory);
    }

    /**
     * @param
     * @description: 初始化上班日期的list
     * @return: void
     * @author: wangjf
     * @time: 2022/3/31 1:43 下午
     */
    @Override
    protected void initialization() {
        this.workDayMap = new HashMap<>();
        List<SysAttendCategoryTime> fdTimes = this.sysAttendCategory.getFdTimes();
        for (SysAttendCategoryTime fdTime : fdTimes) {
            String dateStr = DateUtil.convertDateToString(fdTime.getFdTime(), "yyyy-MM-dd");
            this.workDayMap.put(dateStr, fdTime.getFdTime());
        }
    }

    @Override
    protected boolean checkBusinessDay(Date date) {
        String dateStr = DateUtil.convertDateToString(date, "yyyy-MM-dd");
        if (this.workDayMap.get(dateStr) != null) {
            return true;
        }
        return false;
    }

    /**
     * @param
     * @description: 非周期性签到
     * @return: boolean
     * @author: wangjf
     * @time: 2022/4/1 2:07 下午
     */
    @Override
    protected boolean isCycleWorkDay() {
        return false;
    }

    @Override
    protected TimeRange getTimeRange(Date date) {

        TimeRange timeRange = new TimeRange();
        //去除时间
        timeRange.setDate(AttendUtil.getDate(date, 0));
        //补班一定要上班，优先级最高；工作日和假期，假期的优先级比工作日优先级高，是工作日并且不是假期才上班
        if (checkPatchDay(date) || (checkBusinessDay(date) && !checkHoliday(date))) {

            List<SysAttendCategoryWorktime> availWorkTimeList = this.sysAttendCategory.getAvailWorkTime();
            //班次1
            SysAttendCategoryWorktime workTimeOne = availWorkTimeList.get(0);
            //班次2
            SysAttendCategoryWorktime workTimeTwo = null;
            //存在两个班次
            if (2 == this.sysAttendCategory.getFdWork()) {
                workTimeTwo = availWorkTimeList.get(1);
            }
            timeRange.setWork(this.sysAttendCategory.getFdWork());
            timeRange.setStartTime(DateUtil.getTimeNubmer(workTimeOne.getFdStartTime()));
            //如果工作结束时间是跨天
            if (2 == workTimeOne.getFdOverTimeType()) {
                timeRange.setEndTime(DateUtil.getTimeNubmer(workTimeOne.getFdEndTime()) + DAY_TIME_MILLISECONDS - DateUtil.getTimeNubmer(workTimeOne.getFdStartTime()) + timeRange.getStartTime());
            } else {
                timeRange.setEndTime(DateUtil.getTimeNubmer(workTimeOne.getFdEndTime()));
            }
            //如果是两个班次
            if (2 == this.sysAttendCategory.getFdWork()) {
                timeRange.setStartTime2(DateUtil.getTimeNubmer(workTimeTwo.getFdStartTime()));
                //如果工作结束时间是跨天
                if (2 == workTimeTwo.getFdOverTimeType()) {
                    timeRange.setEndTime2(DateUtil.getTimeNubmer(workTimeTwo.getFdEndTime()) + DAY_TIME_MILLISECONDS - DateUtil.getTimeNubmer(workTimeTwo.getFdStartTime()) + timeRange.getStartTime2());
                } else {
                    timeRange.setEndTime2(DateUtil.getTimeNubmer(workTimeTwo.getFdEndTime()));
                }

            } else {
                timeRange.setStartRestTime(DateUtil.getTimeNubmer(this.sysAttendCategory.getFdRestStartTime()));
                timeRange.setEndRestTime(DateUtil.getTimeNubmer(this.sysAttendCategory.getFdRestEndTime()));
            }
        }
        return timeRange;
    }
}