package com.landray.kmss.sys.attend.service.business;

import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * @description: <p>考勤时间是每周都相同，规则如下：
 * 1、考勤时间每天都是相同
 * 2、存在周末
 * 3、追加打卡日期
 * 4、排除打卡日期
 * 5、存在假期（假期中存在补卡日期）
 * </p>
 * @author: wangjf
 * @time: 2022/3/29 5:01 下午
 * @version: 1.0
 */

public class WeekSameBusinessCalculatorProvide extends AbstractBusinessCalculatorProvide {

    public WeekSameBusinessCalculatorProvide(SysAttendCategory sysAttendCategory) {
        super(sysAttendCategory);
    }

    /**
     * @param
     * @description: 自定义个性化初始化，在父类的init最后
     * @return: void
     * @author: wangjf
     * @time: 2022/3/31 3:28 下午
     */
    @Override
    protected void initialization() {
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
        String[] fdWeeks = this.sysAttendCategory.getFdWeek().split(";");
        String dayOfWeek = String.valueOf(AttendUtil.getWeek(date));
        for (String fdWeek : fdWeeks) {
            if (dayOfWeek.equals(fdWeek)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected TimeRange getTimeRange(Date date) {

        TimeRange timeRange = new TimeRange();
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
            //班次
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