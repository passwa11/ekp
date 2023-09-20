package com.landray.kmss.sys.time.service.scheduled;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmount;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 年假发放定时任务
 * @author liuyang
 * @date 2023/02/23
 */
public class SysTimeLeaveAmountYearService {
    private final  Logger logger = LoggerFactory.getLogger(SysTimeLeaveAmountYearService.class);

    private static final String YEAR_NO = "1";

    private static final String DATE_PATTEN = "yyyy-MM-dd";

    private ISysTimeLeaveAmountService sysTimeLeaveAmountService;

    private ISysTimeLeaveAmountService getSysTimeLeaveAmountService(){
        if(sysTimeLeaveAmountService == null){
            sysTimeLeaveAmountService = (ISysTimeLeaveAmountService)SpringBeanUtil.getBean("sysTimeLeaveAmountService");
        }
        return sysTimeLeaveAmountService;
    }

    private IHrStaffPersonInfoService hrStaffPersonInfoService;

    private IHrStaffPersonInfoService getHrStaffPersonInfoService(){
        if(hrStaffPersonInfoService == null){
            hrStaffPersonInfoService = (IHrStaffPersonInfoService)SpringBeanUtil.getBean("hrStaffPersonInfoService");
        }
        return hrStaffPersonInfoService;
    }

    private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

    private ISysTimeLeaveAmountItemService getSysTimeLeaveAmountItemService(){
        if(sysTimeLeaveAmountItemService == null){
            sysTimeLeaveAmountItemService = (ISysTimeLeaveAmountItemService)SpringBeanUtil.getBean("sysTimeLeaveAmountItemService");
        }
        return sysTimeLeaveAmountItemService;
    }

    private ISysTimeLeaveRuleService sysTimeLeaveRuleService;

    private ISysTimeLeaveRuleService getSysTimeLeaveRuleService(){
        if(sysTimeLeaveRuleService == null){
            sysTimeLeaveRuleService = (ISysTimeLeaveRuleService)SpringBeanUtil.getBean("sysTimeLeaveRuleService");
        }
        return sysTimeLeaveRuleService;
    }


    /**
     * 根据参加工作时间，发放满一周年年假假期
     * @param jobContext
     * @throws Exception
     */
    public void executeFullYearByEnterEnterpriseTime(SysQuartzJobContext jobContext) throws Exception {
        SysTimeLeaveRule rule = getSysTimeLeaveRuleService().getLeaveRuleByType(YEAR_NO);
        List<String> dates = getBeforeYearDate(1,1);
        if(!ArrayUtil.isEmpty(dates)){
            HQLInfo info = new HQLInfo();
            info.setWhereBlock("TIMESTAMPDIFF(year,fd_work_time,SYSDATE())>=1 and fdStatus in ('official','rehireAfterRetirement') and " + HQLUtil.buildLogicIN("fdWorkTime", dates));
            List<HrStaffPersonInfo> personInfos = getHrStaffPersonInfoService().findList(info);
            if(!ArrayUtil.isEmpty(personInfos) && rule != null){
                addOrUpdateLeaveAmomunt(rule,personInfos,"1");
            }
        }
    }

    /**
     * 根据转正时间，发放满一周年年假假期
     * @param jobContext
     * @throws Exception
     */
    public void executeFullYearByConvertTime(SysQuartzJobContext jobContext) throws Exception {
        SysTimeLeaveRule rule = getSysTimeLeaveRuleService().getLeaveRuleByType(YEAR_NO);
        List<String> dates = getBeforeYearDate(0,1);
        if(!ArrayUtil.isEmpty(dates)){
            HQLInfo info = new HQLInfo();
            info.setWhereBlock("TIMESTAMPDIFF(year,fd_work_time,SYSDATE())>=1 and fdStatus in ('official','rehireAfterRetirement') and " + HQLUtil.buildLogicIN("fdPositiveTime", dates));
            List<HrStaffPersonInfo> personInfos = getHrStaffPersonInfoService().findList(info);
            if(!ArrayUtil.isEmpty(personInfos) && rule != null){
                addOrUpdateLeaveAmomunt(rule,personInfos,"2");
            }
        }
    }

    /**
     * 每满10年，计算年假假期
     *
     * @param jobContext
     * @throws Exception
     */
    public void executeYearItem(SysQuartzJobContext jobContext) throws Exception {
        SysTimeLeaveRule rule = getSysTimeLeaveRuleService().getLeaveRuleByType(YEAR_NO);
        List<String> dates = getBeforeYearDate(10,5);
        if(!ArrayUtil.isEmpty(dates)){
            HQLInfo info = new HQLInfo();
            info.setWhereBlock("TIMESTAMPDIFF(year,fd_work_time,SYSDATE())>=1  and fdStatus in ('official','rehireAfterRetirement') and " + HQLUtil.buildLogicIN("fdWorkTime", dates));
            List<HrStaffPersonInfo> personInfos = getHrStaffPersonInfoService().findList(info);
            if(!ArrayUtil.isEmpty(personInfos) && rule != null){
                addOrUpdateLeaveAmomunt(rule,personInfos,"3");
            }
        }
    }

    /**
     * 根据假期类型列表和人员列表更新返聘假期额度信息
     * @param leaveRule
     * @param personList
     * @throws Exception
     */
    public void addOrUpdateLeaveAmomunt(SysTimeLeaveRule leaveRule, List<HrStaffPersonInfo> personList,String fdType) throws Exception {
        if (ArrayUtil.isEmpty(personList)) {
            logger.info("人员列表为空，不需要更新假期");
            return;
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        for (HrStaffPersonInfo personInfo : personList) {
            if (null == personInfo.getFdOrgPerson()) {
                logger.info("人事档案名称：{} 找不到对应的组织架构id", personInfo.getFdName());
                continue;
            }
            SysTimeLeaveAmount amount = getSysTimeLeaveAmountService().getLeaveAmount(year, personInfo.getFdOrgPerson().getFdId());
            if (null == amount) {
                amount = new SysTimeLeaveAmount();
                amount.setFdId(IDGenerator.generateID());
                amount.setFdYear(year);
                amount.setFdPerson(personInfo.getFdOrgPerson());
                amount.setDocCreator(UserUtil.getUser());
                amount.setDocCreateTime(new Date());
                SysTimeLeaveAmountItem newItem = createLeaveAmountItem(amount, leaveRule, personInfo,fdType);
                if (newItem == null) {
                    continue;
                } else {
                    List<SysTimeLeaveAmountItem> items = new ArrayList<>();
                    items.add(newItem);
                    amount.setFdAmountItems(items);
                }
            } else {
                SysTimeLeaveAmountItem item = null;
                for(SysTimeLeaveAmountItem sysTimeLeaveAmountItem : amount.getFdAmountItems()){
                    if(YEAR_NO.equals(sysTimeLeaveAmountItem.getFdLeaveType())){
                        item = sysTimeLeaveAmountItem;
                        break;
                    }
                }
                if (item != null) {
                    // 已存在该假期的额度信息
                    updateLeaveAmountItem(item, item.getFdAmount(), leaveRule, personInfo,fdType);
                } else {
                    // 没有该假期的额度信息
                    SysTimeLeaveAmountItem newItem = createLeaveAmountItem(amount, leaveRule, personInfo,fdType);
                    if (newItem == null) {
                        continue;
                    }
                    amount.getFdAmountItems().add(newItem);
                }
            }
            getSysTimeLeaveAmountService().update(amount);
        }
    }

    private void updateLeaveAmountItem(SysTimeLeaveAmountItem item, SysTimeLeaveAmount amount, SysTimeLeaveRule leaveRule, HrStaffPersonInfo personInfo,String fdType) throws Exception {
        if (Boolean.TRUE.equals(leaveRule.getFdIsAmount()) && Boolean.TRUE.equals(leaveRule.getFdIsAvailable())) {
            // 发放方式
            Integer fdAmountType = leaveRule.getFdAmountType();
            //更新时，更新名称
            item.setFdLeaveName(leaveRule.getFdName());
            String createTime = DateUtil.convertDateToString(personInfo.getFdWorkTime(), DATE_PATTEN);

            if (StringUtil.isNull(createTime)) {
                logger.debug("createLeaveAmountItem方法，用户名:{},用户id:{}，组织架构人员获取创建日期为空忽略该人员操作...",personInfo.getFdName(),personInfo.getFdId());
                return;
            }
            if (fdAmountType == 3) {
                item.setFdIsAuto(true);
                //获取总天数
                Float fdTotalDay = 0f;
                //以使用天数
                Float fdUsedDay = item.getFdUsedDay() == null ? 0f : item.getFdUsedDay();
                if (null != createTime) {
                    fdTotalDay = getLeaveQuota(personInfo,leaveRule, createTime,fdType);
                }
                item.setFdTotalDay(fdTotalDay);
                item.setFdRestDay(fdTotalDay - fdUsedDay);
            }
            Integer calType = leaveRule.getFdAmountCalType();
            if (calType == null) {
                throw new Exception("发放方式数据有误");
            }
            Integer year = amount.getFdYear();
            Date fdValidDate = item.getFdValidDate();
            if (calType == 1) {
                // 到期清零
                item.setFdIsAccumulate(false);
                Date validDate = getValidDate(year, 0);
                //如果失效日期 > 计算得到的日期
                if (fdValidDate != null && fdValidDate.getTime() > validDate.getTime()) {
                    item.setFdIsAvail(IsAfterToday(fdValidDate));
                } else {
                    item.setFdValidDate(validDate);
                    item.setFdIsAvail(IsAfterToday(validDate));
                }
            } else if (calType == 2) {
                // 到期不清零
                item.setFdIsAccumulate(true);
                item.setFdValidDate(null);
                item.setFdIsAvail(true);
            } else if (calType == 3) {
                // 到期清零，延长有效期
                item.setFdIsAccumulate(false);
                Date validDate = getValidDate(year, leaveRule.getFdValidDays());
                //如果失效日期 > 计算得到的日期
                if (fdValidDate != null && fdValidDate.getTime() > validDate.getTime()) {
                    item.setFdIsAvail(IsAfterToday(fdValidDate));
                } else {
                    item.setFdValidDate(validDate);
                    item.setFdIsAvail(IsAfterToday(validDate));
                }
            }
        }
    }

    /**
     * 创建年假
     * @param amount
     * @param leaveRule
     * @param personInfo
     * @return
     * @throws Exception
     */
    private SysTimeLeaveAmountItem createLeaveAmountItem(SysTimeLeaveAmount amount, SysTimeLeaveRule leaveRule, HrStaffPersonInfo personInfo,String fdType) throws Exception {
        Integer year = amount.getFdYear();
        Date today = SysTimeUtil.getDate(new Date(), 0);
        String personId = amount.getFdPerson().getFdId();
        SysTimeLeaveAmountItem item = new SysTimeLeaveAmountItem();
        item.setFdId(IDGenerator.generateID());
        item.setFdAmount(amount);
        item.setFdLeaveName(leaveRule.getFdName());
        item.setFdLeaveType(leaveRule.getFdSerialNo());
        if (Boolean.TRUE.equals(leaveRule.getFdIsAmount()) && Boolean.TRUE.equals(leaveRule.getFdIsAvailable())) {
            //上一年年假
            SysTimeLeaveAmountItem lastItem = getSysTimeLeaveAmountService().getLeaveAmountItem(year - 1, personId, leaveRule.getFdSerialNo());
            //发放方式
            Integer fdAmountType = leaveRule.getFdAmountType();
            //结算方式
            Integer fdAmountCalType = leaveRule.getFdAmountCalType();
            if (fdAmountType == null) {
                throw new UnexpectedRequestException();
            }
            //规则发放
            if (fdAmountType == 3) {
                //是否自动发放
                item.setFdIsAuto(true);
                //以使用天数
                item.setFdUsedDay(0f);
                //总天数计算
                item.setFdTotalDay(0f);
                //剩余天数
                item.setFdRestDay(0f);
                //开始时间
                String createTime = DateUtil.convertDateToString(personInfo.getFdWorkTime(), DATE_PATTEN);
                if (StringUtil.isNull(createTime)) {
                    logger.debug("createLeaveAmountItem方法，用户名:{},用户id:{}，组织架构人员获取创建日期为空忽略该人员操作...",personInfo.getFdName(),personInfo.getFdId());
                } else {
                    float totalDay = getLeaveQuota(personInfo,leaveRule, createTime,fdType);
                    //总天数计算
                    item.setFdTotalDay(totalDay);
                    //剩余天数
                    item.setFdRestDay(totalDay);
                    // 到期清零
                    if (fdAmountCalType == 1) {
                        item.setFdIsAccumulate(false);
                        Date validDate = getValidDate(year, 0);
                        item.setFdValidDate(validDate);
                        item.setFdIsAvail(IsAfterToday(validDate));
                    }
                    // 不清零，累加
                    if (fdAmountCalType == 2) {
                        item.setFdIsAccumulate(true);
                        item.setFdValidDate(null);
                        item.setFdIsAvail(true);
                    }
                    // 到期清零，延长有效期
                    if (fdAmountCalType == 3 && leaveRule.getFdValidDays() != null) {
                        item.setFdIsAccumulate(false);
                        Date validDate = getValidDate(year, 0);
                        item.setFdValidDate(validDate);
                        item.setFdIsAvail(IsAfterToday(validDate));
                    }
                }
            }

            if (lastItem != null) {
                // 有上一年的数据
                // 是否累加
                if (Boolean.TRUE.equals(lastItem.getFdIsAccumulate()) && fdAmountCalType == 2) {
                    Float restDay = lastItem.getFdRestDay() == null ? 0 : lastItem.getFdRestDay();
                    Float lastRestDay = lastItem.getFdLastRestDay() == null ? 0 : lastItem.getFdLastRestDay();
                    item.setFdLastTotalDay(restDay + lastRestDay);
                    item.setFdLastRestDay(restDay + lastRestDay);
                    item.setFdIsLastAvail(true);
                } else if (lastItem.getFdValidDate() != null) {
                    Float restDay = lastItem.getFdRestDay() == null ? 0 : lastItem.getFdRestDay();
                    if (lastItem.getFdValidDate().compareTo(today) >= 0) {
                        // 未过期
                        item.setFdLastTotalDay(restDay);
                        item.setFdLastRestDay(restDay);
                        item.setFdIsLastAvail(true);
                    } else {
                        // 已过期
                        item.setFdLastTotalDay(restDay);
                        item.setFdLastRestDay(restDay);
                        item.setFdIsLastAvail(false);
                    }
                    item.setFdLastValidDate(lastItem.getFdValidDate());
                    item.setFdLastUsedDay(0f);
                }
            }
            return item;
        }
        return null;
    }

    /**
     * 计算假期额度
     * @param leaveRule
     * @param fdBeginTime：参加工作日期
     * @return
     * @throws Exception
     */
    public Float getLeaveQuota(HrStaffPersonInfo personInfo,SysTimeLeaveRule leaveRule, String fdBeginTime,String fdType) throws Exception {
        //当前时间
        Date current = new Date();
        //如果是转正计算年假，则年假开始时间为入职时间，计算入职时间到当年年底的天数
        if("2".equals(fdType)){
            current = personInfo.getFdEntryTime();
        }
        //年初时间
        Date beginDayOfYear = DateUtil.getBeginDayOfYear();
        //年底时间
        Date endDayOfYear = DateUtil.getEndDayOfYear();
        //明年年初
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDayOfYear);
        calendar.add(Calendar.YEAR,1);
        Date fdNextBeginDayOfYear = calendar.getTime();
        String endTime = DateUtil.convertDateToString(fdNextBeginDayOfYear, DATE_PATTEN);
        Float days = getSysTimeLeaveAmountService().getTotalDay(leaveRule, fdBeginTime, endTime);
        long poor = DateUtil.getDatePoor(endDayOfYear,current);
        Float fdTotalDay = (poor / 365f) * days;
        //如果是计算工龄满10年
        if("3".equals(fdType)){
            String fdBeginDayOfYearStr = DateUtil.convertDateToString(beginDayOfYear,DATE_PATTEN);
            Float dayBefore = getSysTimeLeaveAmountService().getTotalDay(leaveRule, fdBeginTime, fdBeginDayOfYearStr);
            long poorBefore = DateUtil.getDatePoor(current,beginDayOfYear);
            Float totalDayBefore = (poorBefore / 365f) * dayBefore;
            fdTotalDay = fdTotalDay + totalDayBefore;
        }
        Double totalDay = Math.floor(fdTotalDay * 2) / 2.0;
        return totalDay.floatValue();
    }

    private List<String> getBeforeYearDate(int year, int count) {
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < count; i++) {
            calendar.add(Calendar.YEAR, -year);
            String d = DateUtil.convertDateToString(calendar.getTime(),DATE_PATTEN);
            dates.add(d);
        }
        return dates;
    }

    /**
     * 获取有效日期
     *
     * @param year
     *            年份
     * @param delta
     *            延长天数
     * @return
     */
    private Date getValidDate(Integer year, Integer delta) {
        if (year == null || delta == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.roll(Calendar.DAY_OF_YEAR, -1);
        cal.add(Calendar.DATE, delta);
        return cal.getTime();
    }

    /**
     * 是否今天或今天后
     *
     * @param date
     * @return
     */
    private Boolean IsAfterToday(Date date) {
        Date today = SysTimeUtil.getDate(new Date(), 0);
        return SysTimeUtil.getDate(date, 0).compareTo(today) >= 0;
    }
}
