package com.landray.kmss.sys.attend.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AttendComparableTime implements Comparable<AttendComparableTime>{
    private Date startDate;
    private Date endDate;

    /**
     * 开始时间（时间戳截止到秒）
     */
    private int start;
    /**
     * 结束时间（时间戳截止到秒）
     */
    private int end;

    /**
     * 以日期形式
     * @param startDate
     * @param endDate
     */
    public AttendComparableTime(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        if(this.startDate !=null) {
            start = (int) (this.startDate.getTime() / 1000);
        }
        if(this.endDate !=null) {
            end = (int) (this.endDate.getTime() / 1000);
        }
    }

    /**
     * 以时间戳形式。
     * @param startDate 秒为单位的时间戳
     * @param endDate 秒为单位的时间戳
     */
    public AttendComparableTime(int startDate, int endDate) {
        this.start = startDate;
        this.end = endDate;
        if(this.start > 0 ) {
            this.startDate = new Date(this.start * 1000);
        }
        if(this.endDate !=null) {
            this.endDate = new Date(this.end * 1000);
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean contains(Date d){
        return d.after(startDate) && d.before(endDate);
    }

    /**
     * 将区间内以每分计算
     * @return
     */
    public Map<Integer,Integer> getBaseMap(){
        Map<Integer,Integer> baseDataMap =new HashMap<Integer,Integer>();
        if(startDate !=null && endDate !=null){
            //以分的区间
            long startLongMis= startDate.getTime()/1000/60;
            long endLongMis= endDate.getTime()/1000/60;
            if(startLongMis > endLongMis){
                return baseDataMap;
            }
            for (long i=startLongMis;i < endLongMis ;i++) {
                baseDataMap.put((int)i,0);
            }
        }
        return baseDataMap;
    }

    @Override
    public String toString() {
        return "DateObject{startDate=" + startDate + ", endDate=" + endDate +  "}";
    }
    @Override
    public int compareTo(AttendComparableTime other)
    {
        if (start == other.start)
        {
            return other.end - end;
        }
        return start - other.start;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
