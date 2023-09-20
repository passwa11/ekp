package com.landray.kmss.sys.attend.cache;


import com.landray.kmss.sys.attend.util.AttendUtil;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 考勤组人员跟考勤组关系的存储对象
 * 人员会存在多日期对应的考勤组，这里存储1对多的关系
 * 用于缓存中
 * @author wj
 * @date 2022-06-10
 */
public class SysAttendUserCategoryListDto implements Serializable {

    private List<SysAttendUserCategoryDto> categoryDtoList;

    /**
     * 将人员考勤组的信息存储进
     * @param fdCategoryId 历史考勤组ID
     * @param fdBeginDate 历史考勤组的开始时间
     * @param fdEndDate 历史考勤组的结束时间
     * @param fdOldCategoryId 原始考勤组id
     */
    public void put(String fdCategoryId,Date fdBeginDate,Date fdEndDate,String fdOldCategoryId,Integer level){
        SysAttendUserCategoryDto dto=new SysAttendUserCategoryDto();
        dto.setFdCategoryId(fdCategoryId);
        //去除时分秒只保留日期
        fdBeginDate = AttendUtil.getDate(fdBeginDate,0);
        dto.setFdBeginDate(fdBeginDate);
        //目前还是以每天来进行缓存。
        dto.setFdEndDate(fdBeginDate);
        dto.setFdOldCategoryId(fdOldCategoryId);
        dto.setLevel(level==null?0:level);
        if(CollectionUtils.isEmpty(categoryDtoList)){
            categoryDtoList = Collections.synchronizedList(new ArrayList<SysAttendUserCategoryDto>());
        }
        categoryDtoList.add(dto);
        if(categoryDtoList.size() > 1) {
            //删除180天之前的缓存
            Date tempDate = AttendUtil.getDate(fdBeginDate,-180);
            Iterator<SysAttendUserCategoryDto> it = categoryDtoList.iterator();
            while (it.hasNext()){
                SysAttendUserCategoryDto info = it.next();
                if(info !=null && info.getFdBeginDate().getTime() < tempDate.getTime()){
                    it.remove();
                }
            }

        }
        /*if(categoryDtoList.size() > 1) {
            //根据开始时间排序存储，最晚的开始时间存在最前面。get的时候从前往后匹配
            ListSortUtil.sort(categoryDtoList, "fdBeginDate", true);
        }*/
    }
    /**
     * 从缓存中获取对应日期范围内的考勤组ID
     * @return
     */
    public String get(Date date){
        if(CollectionUtils.isNotEmpty(categoryDtoList)){
            //去除时分秒只保留日期
            date = AttendUtil.getDate(date,0);
            for (SysAttendUserCategoryDto dto: categoryDtoList) {
                //判断日期是否在其范围内
                //大于等于开始时间，小于结束时间
                if(dto.getFdBeginDate() !=null && dto.getFdEndDate() !=null){
                    if(dto.getFdBeginDate().getTime() == date.getTime() ){
                        return dto.getFdCategoryId();
                    }
                }
            }
        }
        return null;
    }
    /**
     * 从缓存中获取对应日期范围内的考勤组ID
     * @return
     */
    //TODO 按时间区间的缓存，有缺陷，暂时按天来存储。后续在考虑
    /*public String get(Date date){
        if(CollectionUtils.isNotEmpty(categoryDtoList)){
            //存在于缓存中中的日期结束存在多个2099,但是开始时间不一样，所以这里根据顺序从后往前查找
            //categoryDtoList 一定是根据fdBeginDate进行倒序。最晚的在最前面。。
            List<SysAttendUserCategoryDto> tempList= Lists.newArrayList();
            // get次数比put次数多。为了进一步增加性能 ，该排序操作放在存放的时候已经排序好。不在此处排序
            for (SysAttendUserCategoryDto dto: categoryDtoList) {
                //判断日期是否在其范围内
                //大于等于开始时间，小于结束时间
                if(dto.getFdBeginDate() !=null && dto.getFdEndDate() !=null){
                    if(dto.getFdBeginDate().getTime() <= date.getTime() && date.getTime() < dto.getFdEndDate().getTime()){
                        tempList.add(dto);
                    }
                }
            }
            //只有一条，直接返回
            if(CollectionUtils.isNotEmpty(tempList)) {
                if(tempList.size()==1){
                    return tempList.get(0).getFdCategoryId();
                }
                String returnId = null;
                //就近原则算其所属
                Integer level=-1;
                for (SysAttendUserCategoryDto dto : tempList) {
                    //返回时间匹配 以后 等级越高的考勤组
                    Integer tempLevel = dto.getLevel()==null?-1:dto.getLevel();
                    if(tempLevel > level ){
                        level= tempLevel;
                        returnId = dto.getFdCategoryId();
                    }
                }
                //兼容没有level字段的数据，进行按日期就近匹配。
                if(StringUtil.isNull(returnId)) {
                    //tempList得到所有匹配的考勤组信息
                    int countDay = -1;
                    for (SysAttendUserCategoryDto dto : tempList) {
                        //开始时间到计算时间间隔天
                        int beginTime = getBetweenDays(date, dto.getFdBeginDate());
                        int endTime = getBetweenDays(date, dto.getFdEndDate());
                        int tempCountDay = beginTime + endTime;
                        if (countDay == -1 || tempCountDay < countDay) {
                            countDay = tempCountDay;
                            returnId = dto.getFdCategoryId();
                        }
                    }
                }
                return returnId;
            }
        }
        return null;
    }*/

    /**
     * 获取两个日期间隔的天
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getBetweenDays(Date startTime, Date endTime) {
        long start = startTime.getTime();
        long end = endTime.getTime();
        int betweenDays = (int) (Math.abs(end - start)/(24*3600*1000));
        return betweenDays + 1;
    }

    /**
     * 从缓存中删除该人员的所有缓存对象
     * @return
     */
    public void remove(){
        if(CollectionUtils.isNotEmpty(categoryDtoList)){
            categoryDtoList.clear();
        }
    }
    /**
     * 从缓存中删除某个时间段的缓存
     * @return
     */
    public void remove(Date date){
        if(date ==null){
            remove();
            return;
        }
        if(CollectionUtils.isNotEmpty(categoryDtoList)){
            date = AttendUtil.getDate(date,0);
            for (Iterator<SysAttendUserCategoryDto> it = categoryDtoList.iterator(); it.hasNext(); ) {
                SysAttendUserCategoryDto dto =it.next();
                //大于某天的缓存清楚
                if(dto.getFdBeginDate() !=null && dto.getFdEndDate() !=null){
                    if(dto.getFdBeginDate().getTime() >= date.getTime()){
                        it.remove();
                    }
                }
            }
        }
    }

    public List<com.landray.kmss.sys.attend.cache.SysAttendUserCategoryDto> getCategoryDtoList() {
        return categoryDtoList;
    }

    public void setCategoryDtoList(List<com.landray.kmss.sys.attend.cache.SysAttendUserCategoryDto> categoryDtoList) {
        this.categoryDtoList = categoryDtoList;
    }
}
