package com.landray.kmss.sys.attend.service.business;

import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryExctime;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTime;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.time.interfaces.HoursField;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.service.ISysTimeHolidayService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 计算规则抽象类
 * @author: wangjf
 * @time: 2022/3/29 9:54 下午
 * @version: 1.0
 */

public abstract class AbstractBusinessCalculatorProvide {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractBusinessCalculatorProvide.class);

    protected SysAttendCategory sysAttendCategory;
    //一个班次
    private final static int ONE_WORK = 1;
    //两个班次
    private final static int TWO_WORK = 2;
    // 每天时间毫秒数
    protected final static int DAY_TIME_MILLISECONDS = 24 * 60 * 60 * 1000;

    //假期（包括假期表和排除打卡日期）
    protected List<Date> holidayList = new ArrayList<>();
    //补班（包括假期表中补卡和追加打卡日期）
    protected List<Date> patchDayList = new ArrayList<>();

    /**
     * @param sysAttendCategory
     * @description: 构造器不允许空构造
     * @time: 2022/3/29 10:00 下午
     */
    public AbstractBusinessCalculatorProvide(SysAttendCategory sysAttendCategory) {
        this.sysAttendCategory = sysAttendCategory;
        this.init();
    }

    /**
     * @param
     * @description: 初始化假期和补卡日期
     * @return: void
     * @author: wangjf
     * @time: 2022/3/30 5:25 下午
     */
    private void init() {
        this.handleExcWorkDay();
        this.handleWorkDay();
        this.handleHoliday();
        this.initialization();
    }

    /**
     * @param
     * @description: 提供需要个性化初始化的方法，子类只需要重写该方法即可
     * @return: void
     * @author: wangjf
     * @time: 2022/3/31 10:50 上午
     */
    protected abstract void initialization();

    /**
     * @param startTime
     * @param endTime
     * @description: 计算startTime到endTime相差的工时
     * @return: long 返回毫秒
     * @author: wangjf
     * @time: 2022/3/30 5:10 下午
     */
    public long getManHour(long startTime, long endTime) {
        return this.calculateBusinessHour(startTime, endTime);
    }

    /**
     * @param startTime
     * @param numberOfDate
     * @param field(时间单位，天、小时、分钟)
     * @description: 计算开始时间开始+numberOfDate后是哪个日期
     * @return: java.util.Date
     * @author: wangjf
     * @time: 2022/3/30 5:12 下午
     */
    public Date getEndTimeForWorkingHours(Date startTime, int numberOfDate, HoursField field) {

        //天的需要特殊处理-因为周相同中每天的工时可以不一样，小时和分钟的不需要特殊处理
        if (HoursField.DAY.equals(field)) {
            return calculateBusinessDate(startTime, numberOfDate);
        } else if (HoursField.HOUR.equals(field)) {
            long milliseconds = numberOfDate * 60 * 60 * 1000;
            return calculateBusinessDate(startTime, milliseconds);
        } else if (HoursField.MINUTE.equals(field)) {
            long milliseconds = numberOfDate * 60 * 1000;
            return calculateBusinessDate(startTime, milliseconds);
        }
        //如果不符合以上情况则直接返回
        return startTime;
    }

    /**
     * @param startTime
     * @param endTime
     * @description: 两个时间相差的天数
     * @return: int
     * @author: wangjf
     * @time: 2022/3/30 5:16 下午
     */
    public int getWorkingDays(long startTime, long endTime) {
        return this.calculateBusinessDays(startTime, endTime);
    }

    /**
     * 检查是否是下班
     *
     * @param startTime
     * @return: boolean
     * @author: wangjf
     * @time: 2022/5/10 5:03 下午
     */
    private boolean checkOffWork(Date startTime) {

        TimeRange timeRange = getTimeRange(startTime);
        //不上班时间
        if (timeRange.getCapacity() == 0) {
            return true;
        }
        long lastTime = timeRange.getDate().getTime();
        if (ONE_WORK == timeRange.getWork()) {
            lastTime += timeRange.getEndTime();
        } else if (TWO_WORK == timeRange.getWork()) {
            lastTime += timeRange.getEndTime2();
        }
        //下班时间
        if (startTime.getTime() > lastTime) {
            return true;
        }
        return false;

    }

    /**
     * @param startTime
     * @param numberOfDay
     * @description: 根据天来计算结束日期
     * @return: java.util.Date
     * @author: wangjf
     * @time: 2022/4/1 11:50 上午
     */
    private Date calculateBusinessDate(Date startTime, int numberOfDay) {

        //保存开始时间
        long startToUse = DateUtil.getTimeNubmer(startTime);
        //默认会执行一次
        int executeCount = 0;
        //最多执行40次 如果后面不存在工作日了，则需要另外处理（针对自定义考勤日期的情况），防止死循环
        int executeMax = 40;
        //执行日期
        Calendar startCal = DateUtil.getCalendar(startTime);
        //保留最后一天进行日期计算
        TimeRange endRange = null;
        //startTime当前时间是否是工作时间内，如果是工作的话，那就不需要设置开始的endRange了
        boolean offWork = false;
        if (checkOffWork(startTime)) {
            offWork = true;
        }
        do {
            //执行一次加一天
            startCal.add(Calendar.DATE, 1);
            TimeRange timeRange = getTimeRange(startCal.getTime());
            //说明是工作日
            if (timeRange.getCapacity() > 0) {
                endRange = timeRange;
                executeCount++;
            }
            //如果不是周期性签到，则最多执行40次
            if (!isCycleWorkDay()) {
                executeMax--;
            }
        } while ((numberOfDay > executeCount) && executeMax > 0);
        //如果是非工作时间发起的流程，则直接返回工作日的上班时间
        if (offWork) {
            //获取下一个工作日的上班时间
            TimeRange nextBusinessDay = getNextBusinessDay(endRange.getDate());
            return new Date(nextBusinessDay.getDate().getTime() + nextBusinessDay.getStartTime());
        }
        //在正常的工作日中的结束时间
        return getBusinessDate(endRange, startToUse);
    }

    /**
     * @param endRange
     * @param startToUse
     * @description: 获取时间
     * @return: java.util.Date
     * @author: wangjf
     * @time: 2022/4/2 1:35 下午
     */
    private Date getBusinessDate(TimeRange endRange, long startToUse) {
        //如果只有一个班次
        if (ONE_WORK == endRange.getWork()) {
            if (endRange.getStartTime() >= startToUse) {
                //比上班时间早，则返回上班时间
                return new Date(endRange.getDate().getTime() + endRange.getStartTime());
            } else if (endRange.getStartTime() < startToUse && startToUse <= endRange.getStartRestTime()) {
                //大于上班时间，小于午休开始时间，则返回实际时间
                return new Date(endRange.getDate().getTime() + startToUse);
            } else if (endRange.getStartRestTime() < startToUse && startToUse <= endRange.getEndRestTime()) {
                //大于午休开始时间，小于午休结束时间，则返回午休结束时间
                return new Date(endRange.getDate().getTime() + endRange.getEndRestTime());
            } else if (endRange.getEndRestTime() < startToUse && startToUse <= endRange.getEndTime()) {
                //大于午休结束时间，小于下班时间，则返回实际时间
                return new Date(endRange.getDate().getTime() + startToUse);
            } else if (startToUse > endRange.getEndTime()) {
                //大于下班结束时间，则日期加一天，并且设置时间为上班时间
                TimeRange nextBusinessDay = getNextBusinessDay(endRange.getDate());
                return new Date(nextBusinessDay.getDate().getTime() + nextBusinessDay.getStartTime());
            }

        } else if (TWO_WORK == endRange.getWork()) {
            //如果存在两个班次
            if (endRange.getStartTime() >= startToUse) {
                //如果小于第一班上班时间，则返回第一班上班时间
                return new Date(endRange.getDate().getTime() + endRange.getStartTime());
            } else if (endRange.getStartTime() < startToUse && startToUse <= endRange.getEndTime()) {
                //如果大于第一班上班时间，并且小于第一班下班时间，则返回实际时间
                return new Date(endRange.getDate().getTime() + startToUse);
            } else if (endRange.getEndTime() < startToUse && startToUse <= endRange.getStartTime2()) {
                //如果大于第一班下班时间，并且小于第二班上班时间，则设置为第二班上班开始时间
                return new Date(endRange.getDate().getTime() + endRange.getStartTime2());
            } else if (endRange.getStartTime2() < startToUse && startToUse <= endRange.getEndTime2()) {
                //如果大于第二班开始时间，则设置为实际上班时间
                return new Date(endRange.getDate().getTime() + startToUse);
            } else if (startToUse > endRange.getEndTime2()) {
                //如果大于第二班下班时间，则设置为第二天的第一班上班时间
                TimeRange nextBusinessDay = getNextBusinessDay(endRange.getDate());
                return new Date(nextBusinessDay.getDate().getTime() + nextBusinessDay.getStartTime());
            }
        }
        //最后的情况不知道什么时候发生
        return new Date(endRange.getDate().getTime() + startToUse);

    }

    /**
     * 计算跨天考勤的情况是否存在工时，如果存在则需要判定给的时间是否足够
     *
     * @param startTime
     * @param milliseconds
     * @return: long
     * @author: wangjf
     * @time: 2022/5/10 10:05 上午
     */
    private CrossDayResult calculateCrossDay(Date startTime, long milliseconds) {

        CrossDayResult crossDay = new CrossDayResult();
        //日期+跳过时间得出的时间戳
        long endTime = startTime.getTime() + milliseconds;
        //上一天的时间范围
        TimeRange timeRange = getTimeRange(AttendUtil.addDate(startTime, -1));
        //如果不是工作日，直接返回
        if (timeRange.getCapacity() == 0) {
            return crossDay;
        }
        // 昨天上班时间最后时间戳
        long yesterdayLastTime = timeRange.getDate().getTime();
        if (ONE_WORK == timeRange.getWork()) {
            yesterdayLastTime += timeRange.getEndTime();
        } else if (TWO_WORK == timeRange.getWork()) {
            yesterdayLastTime += timeRange.getEndTime2();
        }
        //说明开始时间没在跨天中，则直接跳出，不需要计算
        if (startTime.getTime() > yesterdayLastTime) {
            return crossDay;
        }
        //如果结束时间小于昨天跨天工作的结束时间，则需要计算
        if (endTime < yesterdayLastTime) {
            crossDay.setResult(2);
            crossDay.setDate(new Date(endTime));
        } else {
            crossDay.setResult(3);
            crossDay.setMilliseconds(endTime - yesterdayLastTime);
            crossDay.setDate(new Date(yesterdayLastTime));
        }
        return crossDay;
    }

    class CrossDayResult {

        //1:不需要计算(默认),2:已经计算过了,3:需要再次计算
        private int result = 1;
        private Date date;
        private long milliseconds;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public long getMilliseconds() {
            return milliseconds;
        }

        public void setMilliseconds(long milliseconds) {
            this.milliseconds = milliseconds;
        }
    }

    /**
     * @param startTime
     * @param milliseconds
     * @description: 计算开始时间到milliseconds 是在哪个工作日
     * @return: java.util.Date
     * @author: wangjf
     * @time: 2022/4/1 11:12 上午
     */
    private Date calculateBusinessDate(Date startTime, long milliseconds) {

        //判定startTime与跨天工时是否需要计算
        CrossDayResult crossDay = calculateCrossDay(startTime, milliseconds);
        if (2 == crossDay.getResult()) {
            return crossDay.getDate();
        } else if (3 == crossDay.getResult()) {
            startTime = crossDay.getDate();
            milliseconds = crossDay.getMilliseconds();
        }

        //保存开始时间
        long startToUse = DateUtil.getTimeNubmer(startTime);
        //最多执行40次 如果后面不存在工作日了，则需要另外处理（针对自定义考勤日期的情况），防止死循环
        int executeMax = 40;
        //执行日期
        Calendar startCal = DateUtil.getCalendar(startTime);
        //保留最后一天进行日期计算
        TimeRange endRange = null;
        //第一次获取到的时间
        int once = 1;
        //startTime当天不是假期，如果是假期的话，那就不需要设置开始的timeRange了，因为无论是上班还是下班时间在下一天都当做没发生的时间
        boolean startHoliday = false;
        do {
            TimeRange timeRange = getTimeRange(startCal.getTime());
            //说明是工作日
            if (timeRange.getCapacity() > 0) {
                //第一次获取时需要把开始时间设置进去，并且startTime当天不是节假日，如果是节假日则时间不需要设置
                if (once == 1 && !startHoliday) {
                    //设置时间
                    setFirstTimeRange(timeRange, startToUse);
                }
                once++;
                endRange = timeRange;
                milliseconds = milliseconds - timeRange.getCapacity();
            }
            //如果不是周期性签到，则最多执行40次
            if (!isCycleWorkDay()) {
                executeMax--;
            }
            //执行一次加一天
            startCal.add(Calendar.DATE, 1);
            startHoliday = true;
        } while (milliseconds >= 0 && executeMax > 0);

        //还原还剩余毫秒数
        long overMilliseconds = milliseconds + endRange.getCapacity();

        if (ONE_WORK == endRange.getWork()) {
            //说明扣减完了上午的工时，并且还扣减了下午了工时
            if (overMilliseconds <= endRange.getAmCapacity()) {
                return new Date(endRange.getDate().getTime() + endRange.getStartTime() + overMilliseconds);
            } else {
                return new Date(endRange.getDate().getTime() + endRange.getEndRestTime() + (overMilliseconds - endRange.getAmCapacity()));
            }
        } else if (TWO_WORK == endRange.getWork()) {
            //说明扣减完了第一班的工时，并且还扣减了第二班了工时
            if (overMilliseconds <= endRange.getOneWorkCapacity()) {
                return new Date(endRange.getDate().getTime() + endRange.getStartTime() + overMilliseconds);
            } else {
                return new Date(endRange.getDate().getTime() + endRange.getStartTime2() + (overMilliseconds - endRange.getOneWorkCapacity()));
            }
        }
        return startTime;
    }

    /**
     * @param startTime
     * @param endTime
     * @description: 计算工时
     * @return: long
     * @author: wangjf
     * @time: 2022/3/31 4:05 下午
     */
    private long calculateBusinessHour(long startTime, long endTime) {
        Calendar startCal = DateUtil.getCalendar(startTime);
        Calendar endCal = DateUtil.getCalendar(endTime);
        //保留开始时间 用于工时时间计算
        long startToUse = DateUtil.getTimeNubmer(startCal.getTime());
        //保存结束时间 用于工时时间计算
        long endToUse = DateUtil.getTimeNubmer(endCal.getTime());

        List<TimeRange> timeRangeList = getTimeRangeList(startCal.getTime(), endCal.getTime());

        //设置开始时间范围
        TimeRange firstTimeRange = timeRangeList.get(0);
        setFirstTimeRange(firstTimeRange, startToUse);

        //结束时间
        TimeRange endTimeRange = timeRangeList.get(timeRangeList.size() - 1);
        //设置结束时间范围
        setEndTimeRange(endTimeRange, endToUse);
        //工作时间
        long numberOfHours = 0L;
        for (TimeRange range : timeRangeList) {
            numberOfHours += range.getCapacity();
        }
        return numberOfHours;
    }

    /**
     * @param timeRange
     * @param startToUse
     * @description: 设置开始时间范围
     * @return: void
     * @author: wangjf
     * @time: 2022/4/1 11:06 上午
     */
    private void setFirstTimeRange(TimeRange timeRange, long startToUse) {
        //说明需要签到
        if (timeRange.getCapacity() > 0) {
            //说明是一班制
            if (ONE_WORK == timeRange.getWork()) {
                //如果是在午休之前
                if (timeRange.getStartTime() < startToUse && startToUse <= timeRange.getStartRestTime()) {
                    timeRange.setStartTime(startToUse);
                } else if (timeRange.getStartRestTime() < startToUse && startToUse <= timeRange.getEndRestTime()) {
                    //说明在午休中
                    timeRange.setStartTime(timeRange.getEndRestTime());
                    //置空休息时间
                    timeRange.setStartRestTime(0);
                    timeRange.setEndRestTime(0);
                } else if (timeRange.getEndRestTime() < startToUse && startToUse <= timeRange.getEndTime()) {
                    //说明在午休后
                    timeRange.setStartTime(startToUse);
                    //置空休息时间
                    timeRange.setStartRestTime(0);
                    timeRange.setEndRestTime(0);
                } else if (timeRange.getEndTime() < startToUse) {
                    //说明在下班时间,清空当天时间
                    timeRange.clear();
                }
            } else if (TWO_WORK == timeRange.getWork()) {
                //如果是两班制
                //如果是在第一班之中
                if (timeRange.getStartTime() < startToUse && startToUse <= timeRange.getEndTime()) {
                    timeRange.setStartTime(startToUse);
                } else if (timeRange.getEndTime() < startToUse && startToUse <= timeRange.getStartTime2()) {
                    //置空第一班的数据，说明在两班中间
                    timeRange.setStartTime(0);
                    timeRange.setEndTime(0);
                } else if (timeRange.getStartTime2() < startToUse && startToUse <= timeRange.getEndTime2()) {
                    //置空第一班的数据
                    timeRange.setStartTime(0);
                    timeRange.setEndTime(0);
                    //说明在第二班中间
                    timeRange.setStartTime2(startToUse);
                } else if (timeRange.getEndTime2() < startToUse) {
                    //说明在下班时间,清空当天时间
                    timeRange.clear();
                }
            }
        }
    }

    /**
     * @param endTimeRange
     * @param endToUse
     * @description: 设置结束时间范围
     * @return: com.landray.kmss.sys.attend.service.business.TimeRange
     * @author: wangjf
     * @time: 2022/4/1 11:03 上午
     */
    private void setEndTimeRange(TimeRange endTimeRange, long endToUse) {
        //说明非工作日,不需要签到
        if (endTimeRange.getCapacity() <= 0) {
            return;
        }
        //说明是一班制
        if (ONE_WORK == endTimeRange.getWork()) {
            //如果是在午休之前
            if (endTimeRange.getStartTime() < endToUse && endToUse <= endTimeRange.getStartRestTime()) {
                endTimeRange.setEndTime(endToUse);
                //置空休息时间
                endTimeRange.setStartRestTime(0);
                endTimeRange.setEndRestTime(0);
            } else if (endTimeRange.getStartRestTime() < endToUse && endToUse <= endTimeRange.getEndRestTime()) {
                //说明在午休中，置为午休开始时间
                endTimeRange.setEndTime(endTimeRange.getStartRestTime());
                //置空休息时间
                endTimeRange.setStartRestTime(0);
                endTimeRange.setEndRestTime(0);
            } else if (endTimeRange.getEndRestTime() < endToUse && endToUse <= endTimeRange.getEndTime()) {
                //说明在午休后
                endTimeRange.setEndTime(endToUse);
            } else if (endTimeRange.getStartTime() > endToUse) {
                //说明还没上班,清空当天时间
                endTimeRange.clear();
            }
        } else if (TWO_WORK == endTimeRange.getWork()) {
            //如果是两班制
            //如果是在第一班当中
            if (endTimeRange.getStartTime() < endToUse && endToUse <= endTimeRange.getEndTime()) {
                endTimeRange.setEndTime(endToUse);
                //置空第二班的数据
                endTimeRange.setStartTime2(0);
                endTimeRange.setEndTime2(0);
            } else if (endTimeRange.getEndTime() < endToUse && endToUse <= endTimeRange.getStartTime2()) {
                //说明在两班中间
                //置空第二班的数据
                endTimeRange.setStartTime2(0);
                endTimeRange.setEndTime2(0);
            } else if (endTimeRange.getStartTime2() < endToUse && endToUse <= endTimeRange.getEndTime2()) {
                //说明在第二班中间
                endTimeRange.setEndTime2(endToUse);
            } else if (endTimeRange.getEndTime2() < endToUse) {
                //说明在下班时间,清空当天时间
                endTimeRange.clear();
            }
        }
    }

    /**
     * @param startTime
     * @param endTime
     * @description: 计算天数
     * @return: int
     * @author: wangjf
     * @time: 2022/3/31 4:59 下午
     */
    private int calculateBusinessDays(long startTime, long endTime) {
        Calendar startCal = DateUtil.getCalendar(startTime);
        Calendar endCal = DateUtil.getCalendar(endTime);
        Set<Date> dateSet = new HashSet<Date>();
        List<TimeRange> timeRangeList = getTimeRangeList(startCal.getTime(), endCal.getTime());
        for (TimeRange timeRange : timeRangeList) {
            if (timeRange.getCapacity() > 0) {
                dateSet.add(timeRange.getDate());
            }
        }
        return dateSet.size();
    }


    /**
     * @param date
     * @description: 验证是否是工作日
     * @return: boolean
     * @author: wangjf
     * @time: 2022/3/29 9:58 下午
     */
    protected abstract boolean checkBusinessDay(Date date);

    /**
     * @param
     * @description: 是否是周期性签到规则
     * @return: boolean
     * @author: wangjf
     * @time: 2022/4/1 2:05 下午
     */
    protected boolean isCycleWorkDay() {
        return true;
    }

    /**
     * @param date
     * @description: 获取TimeRange由子类具体实现
     * @return: com.landray.kmss.sys.attend.service.business.TimeRange
     * @author: wangjf
     * @time: 2022/3/31 2:46 下午
     */
    protected abstract TimeRange getTimeRange(Date date);

    /**
     * @param
     * @description: 处理排除日期，当做假期处理
     * @return: void
     * @author: wangjf
     * @time: 2022/3/30 4:54 下午
     */
    private void handleExcWorkDay() {
        if (CollectionUtils.isEmpty(this.sysAttendCategory.getFdExcTimes())) {
            return;
        }
        List<SysAttendCategoryExctime> fdExcTimes = this.sysAttendCategory.getFdExcTimes();
        for (SysAttendCategoryExctime fdExcTime : fdExcTimes) {
            this.holidayList.add(fdExcTime.getFdExcTime());
        }
    }

    /**
     * @param
     * @description: 处理追加打卡日期
     * @return: void
     * @author: wangjf
     * @time: 2022/3/30 4:50 下午
     */
    private void handleWorkDay() {
        if (CollectionUtils.isEmpty(this.sysAttendCategory.getFdTimes())) {
            return;
        }
        List<SysAttendCategoryTime> fdTimes = this.sysAttendCategory.getFdTimes();
        for (SysAttendCategoryTime fdTime : fdTimes) {
            this.patchDayList.add(fdTime.getFdTime());
        }
    }

    /**
     * @description: 处理假期，对假期和补班进行合并
     * @return: boolean
     * @author: wangjf
     * @time: 2022/3/30 2:56 下午
     */
    private void handleHoliday() {
        //不存在假期则直接返回
        if (this.sysAttendCategory.getFdHoliday() == null) {
            return;
        }
        ISysTimeHolidayService sysTimeHolidayService = (ISysTimeHolidayService) SpringBeanUtil.getBean("sysTimeHolidayService");
        SysTimeHoliday sysTimeHoliday = null;
        try {
            sysTimeHoliday = (SysTimeHoliday) sysTimeHolidayService.findByPrimaryKey(this.sysAttendCategory.getFdHoliday().getFdId(), SysTimeHoliday.class, true);
        } catch (Exception e) {
            logger.error("获取假期失败,id:", this.sysAttendCategory.getFdHoliday().getFdId(), e);
        }
        if (sysTimeHoliday == null || CollectionUtils.isEmpty(sysTimeHoliday.getFdHolidayDetailList())) {
            //没有假期数据
            return;
        }
        //所有假期
        List<SysTimeHolidayDetail> fdHolidayDetailList = sysTimeHoliday.getFdHolidayDetailList();
        for (SysTimeHolidayDetail sysTimeHolidayDetail : fdHolidayDetailList) {
            //假期分割
            List<Date> holidays = getDateListByDay(sysTimeHolidayDetail.getFdStartDay(), sysTimeHolidayDetail.getFdEndDay());
            this.holidayList.addAll(holidays);
            if (StringUtil.isNotNull(sysTimeHolidayDetail.getFdPatchDay())) {
                //分离补班数据
                List<Date> dateList = getDateList(sysTimeHolidayDetail.getFdPatchDay());
                if (!CollectionUtils.isEmpty(dateList)) {
                    this.patchDayList.addAll(dateList);
                }
            }
        }
    }

    /**
     * 给定日期判断是否是补班时间
     *
     * @param date
     * @return: boolean
     * @author: wangjf
     * @time: 2022/4/27 4:37 下午
     */
    protected boolean checkPatchDay(Date date) {
        //查询补班中是否存在补班
        if (!CollectionUtils.isEmpty(this.patchDayList)) {
            //如果日期存在补班中
            for (Date patchDay : this.patchDayList) {
                if (AttendUtil.isSameDate(patchDay, date)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param date
     * @description: 判断是否为假期
     * @return: boolean
     * @author: wangjf
     * @time: 2022/3/30 4:58 下午
     */
    protected boolean checkHoliday(Date date) {
        //查询假期
        if (!CollectionUtils.isEmpty(this.holidayList)) {
            //如果日期存在假期中，说明需要是假期，则直接返回true
            for (Date holiday : this.holidayList) {
                if (AttendUtil.isSameDate(holiday, date)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param dateStrings eg:2022-04-02,2022-04-16
     * @description: 根据字符串获取时间List
     * @return: java.util.List<java.util.Date>
     * @author: wangjf
     * @time: 2022/3/30 3:48 下午
     */
    private List<Date> getDateList(String dateStrings) {
        if (StringUtil.isNull(dateStrings)) {
            return null;
        }
        List<Date> resultList = new ArrayList<>();
        String[] dates = dateStrings.split(",");
        for (String dateStr : dates) {
            Date date = DateUtil.convertStringToDate(dateStr, DateUtil.PATTERN_DATE, null);
            resultList.add(date);
        }
        return resultList;
    }

    /**
     * @param startDate
     * @param endDate
     * @description: 返回时间跨度的时间范围列表
     * @return: java.util.List<com.landray.kmss.sys.attend.service.business.TimeRange>
     * @author: wangjf
     * @time: 2022/3/31 3:20 下午
     */
    protected List<TimeRange> getTimeRangeList(Date startDate, Date endDate) {
        //结果返回
        List<TimeRange> timeRangeList = new ArrayList<>();
        List<Date> dateListByDay = getDateListByDay(startDate, endDate);
        for (int i = 0; i < dateListByDay.size(); i++) {
            Date date = dateListByDay.get(i);
            timeRangeList.add(getTimeRange(date));
        }
        return timeRangeList;
    }

    /**
     * 获取两个时间的中间日期
     *
     * @param startDate
     * @param endDate
     * @return: java.util.List<java.util.Date>
     * @author: wangjf
     * @time: 2022/4/27 5:28 下午
     */
    private List<Date> getDateListByDay(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList<Date>();
        Date startTime = AttendUtil.getDate(startDate, 0);
        Date endTime = AttendUtil.getDate(endDate, 0);
        Calendar cal = Calendar.getInstance();
        for (cal.setTime(startTime); cal.getTime().compareTo(endTime) <= 0; cal.add(Calendar.DATE, 1)) {
            dateList.add(cal.getTime());
        }
        return dateList;
    }

    /**
     * 获取下一个最近的工作日时间范围
     *
     * @param date
     * @return: java.util.Date
     * @author: wangjf
     * @time: 2022/5/6 4:02 下午
     */
    private TimeRange getNextBusinessDay(Date date) {
        for (int i = 1; i < 40; i++) {
            TimeRange timeRange = getTimeRange(AttendUtil.addDate(date, i));
            if (timeRange.getCapacity() > 0) {
                return timeRange;
            }
        }
        //查找了40天后还是没结果，返回第40天的时间回去
        return getTimeRange(AttendUtil.addDate(date, 1));
    }

}