package com.landray.kmss.sys.attend.service.business;

import java.util.Date;

/**
 * @description: 时间范围，支持排序
 * @author: wangjf
 * @time: 2022/3/29 10:15 上午
 * @version: 1.0
 */

public class TimeRange implements Comparable<TimeRange> {
    //工作开始时间班次1
    private long startTime = 0;
    //工作结束时间班次1
    private long endTime = 0;
    //工作开始时间班次2
    private long startTime2 = 0;
    //工作结束时间班次2
    private long endTime2 = 0;
    //休息开始时间
    private long startRestTime = 0;
    //休息结束时间
    private long endRestTime = 0;
    //1代表一个班次、2代表两个班次，当存在两个班次的时间说明不存在午休时间startRestTime 和endRestTime不需要赋值
    private int work = 1;
    //日期
    private Date date = null;

    /**
     * @param
     * @description: 清空时间
     * @return: void
     * @author: wangjf
     * @time: 2022/3/31 2:00 上午
     */
    public void clear() {
        this.startTime = 0;
        this.endTime = 0;
        this.startTime2 = 0;
        this.endTime2 = 0;
        this.startRestTime = 0;
        this.endRestTime = 0;
    }

    @Override
    public int compareTo(TimeRange other) {
        return this.date.getTime() < other.date.getTime() ? -1 : 1;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @param
     * @description: 计算工时
     * @return: long
     * @author: wangjf
     * @time: 2022/3/29 8:22 下午
     */
    public long getCapacity() {
        //如果是两个班次
        if (this.work == 2) {
            return (this.endTime - this.startTime) + (this.endTime2 - this.startTime2);
        }
        return (this.endTime - this.startTime) - (this.endRestTime - this.startRestTime);
    }

    /**
     * 返回上午工时
     * @param
     * @return: long
     * @author: wangjf
     * @time: 2022/4/2 2:45 下午
     */
    public long getAmCapacity(){
        return this.startRestTime - this.startTime;
    }

    /**
     * 返回下午工时
     * @param
     * @return: long
     * @author: wangjf
     * @time: 2022/4/2 2:47 下午
     */
    public long getPmCapacity(){
        return this.endTime - this.endRestTime;
    }
    /**
     * 获取第一班工时
     * @param
     * @return: long
     * @author: wangjf
     * @time: 2022/4/2 2:49 下午
     */
    public long getOneWorkCapacity(){
        return this.endTime - this.startTime;
    }

    /**
     * 获取第二班工时
     * @param
     * @return: long
     * @author: wangjf
     * @time: 2022/4/2 2:50 下午
     */
    public long getTwoWorkCapacity(){
        return this.endTime2 - this.startTime2;
    }

    public long getStartRestTime() {
        return this.startRestTime;
    }

    public void setStartRestTime(long startRestTime) {
        this.startRestTime = startRestTime;
    }

    public long getEndRestTime() {
        return this.endRestTime;
    }

    public void setEndRestTime(long endRestTime) {
        this.endRestTime = endRestTime;
    }

    public long getStartTime2() {
        return this.startTime2;
    }

    public void setStartTime2(long startTime2) {
        this.startTime2 = startTime2;
    }

    public long getEndTime2() {
        return this.endTime2;
    }

    public void setEndTime2(long endTime2) {
        this.endTime2 = endTime2;
    }

    public int getWork() {
        return this.work;
    }

    public void setWork(int work) {
        this.work = work;
    }
}