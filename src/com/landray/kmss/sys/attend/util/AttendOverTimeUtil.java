package com.landray.kmss.sys.attend.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryDeduct;
import com.landray.kmss.util.ListSortUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 加班时长计算
 * @author 王京
 */
public class AttendOverTimeUtil {
    /**
     * 获取 实际时间排除标准时间的时间差
     * @param start1 实际开始时间
     * @param end1 实际结束时间
     * @param start2 标准开始时间
     * @param end2 标准结束时间
     * @return 分钟数
     */
    private static int getMixedMinites(Date start1, Date end1, Date start2,Date end2) {
        //标准开始时间的组合
        start2 = AttendUtil.joinYMDandHMS(AttendUtil.getDate(start1, 0),start2);
        //标准结束时间的组合
        end2 = AttendUtil.joinYMDandHMS(AttendUtil.getDate(start1, 0), end2);

        long startTime1 = start1.getTime();
        long endTime1 = end1.getTime();
        long startTime2 = start2.getTime();
        long endTime2 = end2.getTime();
        long mintes = 0;
        //只有这种情况加班才有效 打卡时间 >= 你应该的工作时间
        if(endTime1 >= startTime1){
            //这种情况才需要扣除工时 打卡时间 大于你填写的开始扣除工时的时间
            if(endTime1 >= startTime2){
                //打卡时间在你填写的扣除公时范围内 应该等于 实际扣除时间
                if(endTime1 <= endTime2) {
                    mintes = endTime1 - startTime2;
                } else {
                    //这种情况是打卡时间超过了应该扣工时的情况 应该只扣除这个时间段的公司就可以了
                    //mintes = endTime2-startTime2;
                    //打卡开始时间大于开始扣除工时时间，打卡开始时间小于结束扣除时间，打卡结束时间大于结束扣除时间
                    if (startTime2 <= startTime1 && endTime2 > startTime1
                            && endTime2 < endTime1) {
                        mintes = endTime2 - startTime1;
                    }
                    //打卡开始时间小于开始扣除时间，打卡结束时间大于结束扣除时间
                    if (startTime1 < startTime2 && endTime1 > endTime2) {
                        mintes = endTime2 - startTime2;
                    }
                    //打卡开始时间大于开始扣除时间，打卡结束时间小于结束扣除时间
                    if (startTime2 < startTime1 && endTime2 > endTime1) {
                        mintes = endTime1 - startTime1;
                    }
                    //打卡结束时间小于等于扣除结束时间，打卡开始时间小于开始扣除时间
                    if (endTime2 >= endTime1 && startTime2 > startTime1
                            && startTime2 < endTime1) {
                        mintes = endTime1 - startTime2;
                    }
                }
            }
        }
        return (int) Math.ceil(mintes / 60000d);
    }
    /**
     * 计算加班时间与扣减时间之间的差时
     * 返回单位是分钟
     * @param cate
     * @param periods
     * @return
     */
    public static int getDeductMins(SysAttendCategory cate,
                              List<JSONObject> periods) {
        List<SysAttendCategoryDeduct> list = cate.getOvertimeDeducts();
        int deductMins = 0;
        if (list != null && list.size() > 0) {
            for (SysAttendCategoryDeduct deduct : list) {
                for (JSONObject period : periods) {
                    deductMins = deductMins
                            + getMixedMinites(
                            new Date(period.getLong("fdStartTime")),
                            new Date(period.getLong("fdEndTime")),
                            deduct.getFdStartTime(),
                            deduct.getFdEndTime());
                }
            }
        }
        return deductMins;
    }


    /**
     * 获取加班排除时间段的 时间区间
     * @param category 考勤组
     * @param workData 计算日
     * @return
     */
    public static List<AttendComparableTime> getDeductTime(SysAttendCategory category, Date workData){
        List<AttendComparableTime> newList=new ArrayList<AttendComparableTime>();
        //是否扣除加班 时间
        boolean isOvertimeDeduct = category.getFdIsOvertimeDeduct() == null ? false : true;
        // 扣除类型 0按时间段
        int fdOvtDeductType = category.getFdOvtDeductType() == null ? 0 : category.getFdOvtDeductType();
        if (isOvertimeDeduct && Integer.valueOf(0).equals(fdOvtDeductType)) {
            List<SysAttendCategoryDeduct> list = category.getOvertimeDeducts();
            if (list != null && list.size() > 0) {
                for (SysAttendCategoryDeduct deduct : list) {
                    Date startSignTime = AttendUtil.joinYMDandHMS(workData, deduct.getFdStartTime());
                    Date endTime = AttendUtil.joinYMDandHMS(workData, deduct.getFdEndTime());
                    newList.add(new AttendComparableTime(startSignTime,endTime));
                }
            }
        }
        return newList;
    }

    /**
     * 加班扣除方法。只处理满减的场景
     * @param category 考勤组
     * @param overTime 当前加班总工时。分钟数
     * @return
     */
    public static int getTimeDeductMins(SysAttendCategory category,long overTime){
        int deductMins= 0;
        //是否扣除加班 时间
        boolean isOvertimeDeduct = category.getFdIsOvertimeDeduct() == null ? false : true;
        // 扣除类型 0按时间 1满减
        int fdOvtDeductType = category.getFdOvtDeductType() == null ? 0 : category.getFdOvtDeductType();
        if (isOvertimeDeduct && Integer.valueOf(1).equals(fdOvtDeductType)) {
            //满减
            deductMins = getDeductMinsForThreahold(category,overTime);
        }
        return deductMins;
    }

    /**
     * 加班时间 满减
     * @param cate
     * @param fdOverTime
     * @return
     */
    public static int getDeductMinsForThreahold(SysAttendCategory cate,  long fdOverTime) {
        List<SysAttendCategoryDeduct> list = cate
                .getOvertimeDeducts();
        int deductMins = 0;
        if (list != null && list.size() > 0) {
            SysAttendCategoryDeduct deduct = list.get(0);
            int fdThreahold = deduct.getFdThreshold();
            int fdDeductHours = deduct.getFdDeductHours();
            int hours = (int) fdOverTime / 60;
            if (hours / fdThreahold > 0) {
                deductMins = fdDeductHours * 60;
            }
        }
        return deductMins;
    }
    /**
     * 获取标准打卡时间的范围区间列表
     * @param signTimeList 班次时间信息
     * @param date 日期
     * @return
     * @throws Exception
     */
    public static List<AttendComparableTime> getStandardSignTime(List<Map<String, Object>> signTimeList,
                                                           Date date,Boolean fdIsCalculateOvertime,Boolean beforeWorkOverTIme) throws Exception {
        List<AttendComparableTime> comparableTimeList =new ArrayList<AttendComparableTime>();
        if(CollectionUtils.isNotEmpty(signTimeList)){
            //计算班次间隔时间。用每个班次的开始结束 时间为区间
            if(Boolean.TRUE.equals(fdIsCalculateOvertime)) {
                //每2条 为一个班次
                int workCount = signTimeList.size() / 2;
                for (int i = 0; i < workCount; i++) {
                    // 上班
                    Map<String, Object> startMap = signTimeList.get(2 * i);
                    // 下班
                    Map<String, Object> endMap = signTimeList.get(2 * i + 1);
                    //只有第班次1 才计算是否加班前
                    if(i==0){
                        comparableTimeList.add(setComparableTime(startMap, endMap, date, beforeWorkOverTIme));
                    }else {
                        comparableTimeList.add(setComparableTime(startMap, endMap, date, true));
                    }
                }
            } else {
                //不计算班次的间隔时间，就是所有班次的第1个班次和最后一个班次的。开始结束时间为区间 进行比较
                //最开始的上班
                Map<String, Object> startMap = signTimeList.get(0);
                //最晚的下班
                Map<String, Object> endMap = signTimeList.get(signTimeList.size() -1);
                comparableTimeList.add(setComparableTime(startMap,endMap,date,beforeWorkOverTIme));
            }
        }
        return comparableTimeList;
    }

    /**
     * 设置班次的比较时间
     * @param startMap
     * @param endMap
     * @param date
     * @param beforeWorkOverTIme 上班前是否计算加班的标识
     * @return
     */
    private static AttendComparableTime setComparableTime(Map<String, Object> startMap ,Map<String, Object> endMap,Date date,Boolean beforeWorkOverTIme){
        Date startSignTime = (Date) startMap.get("signTime");

        if(Boolean.FALSE.equals(beforeWorkOverTIme)){
            //如果不计算上班前的加班工时，则使用班次的最早打卡时间为计算时间
            startSignTime = (Date) startMap.get("fdStartTime");
        }
        startSignTime = AttendUtil.joinYMDandHMS(date, startSignTime);
        Date endSignTime = (Date) endMap.get("signTime");
        endSignTime = AttendUtil.joinYMDandHMS(date, endSignTime);
        //下班计算标准时间是否跨天
        Integer overTimeType = (Integer) endMap.get("overTimeType");
        if (Integer.valueOf(2).equals(overTimeType)) {
            endSignTime = AttendUtil.addDate(endSignTime, 1);
        }
        return new AttendComparableTime(startSignTime, endSignTime);
    }
    /**
     * 获取用户当天 每个班次的上班 下班打卡时间
     * @param sysAttendMainList 打卡记录
     * @param docCreatorId 人员id
     * @return
     */
    public static Map<String,Map<String,Date>> getSignTime(List<Object[]> sysAttendMainList,String docCreatorId){

        //取每个班次的上班打卡时间、下班打卡时间
        Map<String,Map<String,Date>> workSignTimeMap=new HashMap<String,Map<String,Date>>(2);
        Map<String,List<Object[]>> workSignListMap=new HashMap<String,List<Object[]>>(2);
        for (Object obj : sysAttendMainList) {
            Object[] record = (Object[]) obj;
            //班次ID
            String fdWorkId = (String) record[6];
            //班次ID.排班类型 使用fdWorkKey
            String fdWorkKey = (String) record[12];
            if(StringUtil.isNull(fdWorkId)){
                fdWorkId = fdWorkKey;
            }
            String fdAppName = (String) record[15];
            //补卡状态
            Number fdState = (Number) record[7];
            boolean isOk = fdState != null && fdState.intValue() == 2;
            //打卡状态
            Number fdStatus = (Number) record[1];
            //出差并且有打卡记录，不是系统产生的
            boolean isBusinessSign = fdStatus != null && fdStatus.intValue() == 4;
            //外出
            boolean isOutSign = fdStatus != null && fdStatus.intValue() == 6;
            if (!(fdAppName !=null || isOk || isBusinessSign || isOutSign)) {
                //出差 状态、正常状态 或异常通过的打卡时间才计算加班
                continue;
            }
            //该班次对应的打卡时间
            List<Object[]> signItemList  =workSignListMap.get(fdWorkId);
            if(signItemList ==null){
                signItemList =new ArrayList();
            }
            signItemList.add(record);
            workSignListMap.put(fdWorkId,signItemList);
        }
        for (Map.Entry<String, List<Object[]>> workTimeInfo : workSignListMap.entrySet()) {
            //每个班次的打卡时间列表
            List<Object[]> signMainList = workTimeInfo.getValue();
            //最早打卡开始时间
            Date startOvertime = null;
            //最晚打卡结束时间
            Date endOvertime = null;
            //该班次对应的打卡时间
            Map<String,Date> signTimeMap  =workSignTimeMap.get(workTimeInfo.getKey());
            for (Object[] record : signMainList) {
                //人员id
                String statCreatorId = (String) record[0];
                //上下班标识
                Number fdWorkType = (Number) record[3];
                if(signTimeMap ==null){
                    signTimeMap =new HashMap<String,Date>(2);
                }
                if (statCreatorId != null && statCreatorId.equals(docCreatorId)) {
                    //打卡时间
                    Timestamp createTime = (Timestamp) record[2];
                    if(fdWorkType.intValue() ==0 ) {
                        //上班
                        if (startOvertime == null || createTime.getTime() < startOvertime.getTime()) {
                            startOvertime = createTime;
                        }
                    }
                    if(fdWorkType.intValue() ==1) {
                        //下班
                        if (endOvertime == null || createTime.getTime() > endOvertime.getTime()) {
                            endOvertime = createTime;
                        }
                    }
                }
            }
            if(startOvertime !=null && endOvertime !=null) {
                //上班
                signTimeMap.put("startTime",AttendUtil.removeSecond(startOvertime));
                //下班
                signTimeMap.put("endTime", AttendUtil.removeSecond(endOvertime));
                workSignTimeMap.put(workTimeInfo.getKey(),signTimeMap);
            }
        }
        return workSignTimeMap;
    }
    /**
     * 根据区间返回过滤 范围内排除交集的分钟数
     * 特别说明：传进来的区间范围，不要间隔太大。这里用于场景，2天内的处理。因为加班只有按天提交，加上跨天。
     * @param attendComparableTime 日期对象
     * @param list       排除日期列表
     * @return 返回差集的分钟数
     */
    public static int differencesMis(AttendComparableTime attendComparableTime, List<AttendComparableTime> list) {
        int mis=0;
        if(attendComparableTime ==null) {
            return mis;
        }
        //以每分钟为key 来判断是否重复
        Map<Integer,Integer> baseDataMap = attendComparableTime.getBaseMap();
        if(CollectionUtils.isNotEmpty(list)) {
            ListSortUtil.sort(list,"startDate",false);

            for (int i = 0; i < list.size(); i++) {
                AttendComparableTime next = list.get(i);
                //过滤列表 大于 范围区间。直接返回空
                if(next.getStartDate().getTime() <= attendComparableTime.getStartDate().getTime()
                        && next.getEndDate().getTime() >= attendComparableTime.getEndDate().getTime() ){
                    //完全排除
                    for (Map.Entry<Integer,Integer> checkMap:baseDataMap.entrySet()) {
                        baseDataMap.put(checkMap.getKey(),1);
                    }
                    continue;
                }
                //有重叠， 计算重叠次数
                Map<Integer,Integer> nextDataMap = next.getBaseMap();
                for (Map.Entry<Integer,Integer> checkMap:nextDataMap.entrySet()) {
                    Integer baseValue = baseDataMap.get(checkMap.getKey());
                    if(baseValue !=null){
                        baseDataMap.put(checkMap.getKey(),baseValue+1);
                    }
                }
            }
            //取value为0的个数。代表多少分钟
            for (Map.Entry<Integer, Integer> resutInfo : baseDataMap.entrySet()) {
                if (Integer.valueOf(0).equals(resutInfo.getValue())) {
                    mis++;
                }
            }
            return mis;
        } else {
            //如果打卡记录和排除时间段都是空的。直接返回开始到结束的时长
            mis = getOverTime(Lists.newArrayList(attendComparableTime));
        }
        return mis;
    }


    /**
     * 根据时间范围列表。去重叠时间
     * @param timeList
     * @return
     */
    public static int getOverTime(List<AttendComparableTime> timeList) {
        if(CollectionUtils.isEmpty(timeList)){
            return 0;
        }
        Collections.sort(timeList);
        Stack startPoint = new Stack();
        Stack endPoint = new Stack();
        startPoint.push(0);
        endPoint.push(0);
        for (AttendComparableTime time : timeList)
        {
            if (time.getStart() > time.getEnd()){
                //时间不规范
                continue;
            }
            //没有重叠 开始结束点位赋值
            if (time.getStart() > (int)endPoint.peek()){
                startPoint.push(time.getStart());
                endPoint.push(time.getEnd());
            } else if (time.getEnd() > (int)endPoint.peek())  {
                //有部分重叠，取并集
                endPoint.pop();
                endPoint.push(time.getEnd());
            } else {
                //完全重叠 则不处理
            }
        }
        int totalSecond = 0;
        while (!startPoint.empty()) {
            totalSecond += (int)endPoint.pop() - (int)startPoint.pop();
        }
        return  (int) Math.ceil(totalSecond /60);
    }

    /**
     * 求差集
     *
     * @param comparableTime 需要拆分的日期对象
     * @param list 排除的日期列表
     * @return 差集
     */
    public static List<AttendComparableTime> differences(AttendComparableTime comparableTime, List<AttendComparableTime> list) {
        List<AttendComparableTime> result = new ArrayList<>();
        AttendComparableTime first = list.get(0);
        if (list.size() == 1) {
            //开始时间比第1个时间小。开始时间为比较时间
            if (comparableTime.getStartDate().getTime() < first.getStartDate().getTime()) {
                result.add(new AttendComparableTime(comparableTime.getStartDate(), first.getStartDate()));
            }
            if (comparableTime.getEndDate().getTime() - first.getEndDate().getTime() > 1000) {
                result.add(new AttendComparableTime(first.getEndDate(), comparableTime.getEndDate()));
            }
        } else {
            for (int i = 1; i < list.size(); i++) {
                AttendComparableTime next = list.get(i);
                result.add(new AttendComparableTime(first.getEndDate(), next.getStartDate()));
                first = next;
            }
            AttendComparableTime last = list.get(list.size() - 1);
            if (comparableTime.getEndDate().getTime() > last.getEndDate().getTime()) {
                result.add(new AttendComparableTime(last.getEndDate(), comparableTime.getEndDate()));
            }
        }
        return result;
    }

    /**
     * 求时间区间与时间区间列表的所有交集
     * @param comparableTime 需要拆分的日期对象
     * @param list 合并的日期列表
     * @return 日期交集
     */
    public static List<AttendComparableTime> intersection(AttendComparableTime comparableTime, List<AttendComparableTime> list) {
        List<AttendComparableTime> result = new ArrayList<>();
        for (AttendComparableTime date : list) {
            // 时间区间全部在开放时间区间内：ad.startDate <= open.startDate < open.endDate <= ad.endDate
            // 则[open.startDate, open.endDate] 均不开放
            if (date.getStartDate().getTime() <= comparableTime.getStartDate().getTime()
                    && date.getEndDate().getTime() >= comparableTime.getEndDate().getTime()) {
                // 开放时间区间已全部占用
                break;
            }
            // 时间区间全部在开放时间区间内：open.startDate <= ad.startDate < ad.endDate <= open.endDate
            else if (date.getStartDate().getTime() >= comparableTime.getStartDate().getTime()
                    && date.getEndDate().getTime() <= comparableTime.getEndDate().getTime()) {
                result.add(new AttendComparableTime(date.getStartDate(), date.getEndDate()));
            }
            // 结束时间在开放时间区间内：ad.startDate <= open.startDate <= ad.endDate <= open.endDate
            else if (date.getStartDate().getTime() <= comparableTime.getStartDate().getTime()
                    && date.getEndDate().getTime() >= comparableTime.getStartDate().getTime()
                    && date.getEndDate().getTime() <= comparableTime.getEndDate().getTime()) {
                result.add(new AttendComparableTime(comparableTime.getStartDate(), date.getEndDate()));
            }
            // 开始时间在开放时间区间内：open.startDate <= ad.startDate <= open.endDate <= ad.endDate
            else if (date.getEndDate().getTime() > comparableTime.getEndDate().getTime()) {
                result.add(new AttendComparableTime(date.getStartDate(), comparableTime.getEndDate()));
            }
        }
        return result;
    }


    /**
     * 根据区间列表。获取交集的日期
     * @param timeList
     * @return
     */
    public static AttendComparableTime getIntersectionDate(List<AttendComparableTime> timeList) {
        if(CollectionUtils.isEmpty(timeList)){
            return null;
        }

//        Map<Integer,Integer> commonDataMap =new HashMap<>(1);
//        for ( AttendComparableTime next :timeList) {
//            Map<Integer,Integer> nextDataMap = next.getBaseMap();
//            for (Map.Entry<Integer,Integer> checkMap:nextDataMap.entrySet()) {
//                Integer baseValue = nextDataMap.get(checkMap.getKey());
//                if(baseValue !=null){
//                    nextDataMap.put(checkMap.getKey(),baseValue+1);
//                }
//            }
//        }
        return null;
    }
}
