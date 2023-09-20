package com.landray.kmss.sys.attend.service.spring;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendStatLog;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendListenerCommonService;
import com.landray.kmss.sys.attend.service.ISysAttendMainJobService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendStatJobService;
import com.landray.kmss.sys.attend.service.ISysAttendStatLogService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionStatus;
import java.sql.ResultSet;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 生成缺卡数据(以用户当前考勤组班次生成缺卡记录)
 *
 * @author linxiuxian
 *
 * v2 生成缺卡记录改造
 * @author wj
 */
public class SysAttendMainJobServiceImp implements ISysAttendMainJobService {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendMainJobServiceImp.class);
    private ISysAttendListenerCommonService sysAttendListenerCommonService;
    private ISysAttendBusinessService sysAttendBusinessService;
    private IBaseDao baseDao;
    public IBaseDao getBaseDao() {
        if (baseDao == null) {
            baseDao = (IBaseDao) SpringBeanUtil
                    .getBean("KmssBaseDao");
        }
        return baseDao;
    }
    private ISysQuartzCoreService sysQuartzCoreService;
    private ISysQuartzCoreService getSysQuartzCoreService() {
        if (sysQuartzCoreService == null) {
            sysQuartzCoreService = (ISysQuartzCoreService) SpringBeanUtil.getBean("sysQuartzCoreService");
        }
        return sysQuartzCoreService;
    }
    ISysAttendStatJobService statJobService;

    private ISysAttendStatJobService getSysAttendStatJobService() {
        if (statJobService == null) {
            statJobService = (ISysAttendStatJobService) SpringBeanUtil.getBean("sysAttendStatJobService");
        }
        return statJobService;
    }
    public ISysAttendListenerCommonService getSysAttendListenerCommonService() {
        if (sysAttendListenerCommonService == null) {
            sysAttendListenerCommonService = (ISysAttendListenerCommonService) SpringBeanUtil
                    .getBean("sysAttendListenerCommonService");
        }
        return sysAttendListenerCommonService;
    }
    private ISysAttendCategoryService sysAttendCategoryService;

    protected ISysAttendCategoryService getSysAttendCategoryService() {
        if (sysAttendCategoryService == null) {
            sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
        }
        return sysAttendCategoryService;
    }
    public ISysAttendBusinessService getSysAttendBusinessService() {
        if (sysAttendBusinessService == null) {
            sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil
                    .getBean("sysAttendBusinessService");
        }
        return sysAttendBusinessService;
    }
    private ISysAttendMainService sysAttendMainService = (ISysAttendMainService) SpringBeanUtil
            .getBean("sysAttendMainService");
 ;
    private ISysOrgCoreService orgService = (ISysOrgCoreService) SpringBeanUtil
            .getBean("sysOrgCoreService");

    private ISysQuartzJobService sysQuartzJobService = (ISysQuartzJobService) SpringBeanUtil
            .getBean(
                    "sysQuartzJobService");

    private final static String DATE_FORMAT = "yyyy-MM-dd-HH-mm:ss";

    /**
     * 产生缺卡记录日志的Log
     */
    private String STAT_LOG_KEY_ACROSS="across";
    /**
     * 产生缺卡记录日志的Log
     */
    private String STAT_LOG_KEY_CATEGORY="every_category";

    /**
     * 每个考勤组生成缺卡记录
     * 各个考勤组的普通定时任务调用。
     * 非排班类型
     * @throws Exception
     */
    public void executeCategory(SysQuartzJobContext jobContext) throws Exception {
        JSONObject param = JSONObject.fromObject(jobContext.getParameter());
        logger.debug("考勤组缺卡定时任务启动:" + param.toString());
        String fdCategoryId = param.getString("fdCategoryId");
        Boolean isAcross = param.getBoolean("isAcross");
        Long fdEndTime = param.getLong("fdEndTime");
        Long deleteQuartzDate =param.has("deleteQuartzDate")? param.getLong("deleteQuartzDate"):0L;

        if (StringUtil.isNull(fdCategoryId) ) {
            logger.warn("考勤组缺卡定时任务退出,考勤组fdCategoryId为空!");
            return;
        }
        if(deleteQuartzDate !=null && deleteQuartzDate > 0){
            String quartKey = param.getString("quartKey");
            //指定删除日期。则不运行
            Date nowDate  = DateUtil.getDate(0);
            if(nowDate.getTime() >= deleteQuartzDate){
                //删除该考勤组的定时任务
                SysAttendCategory category = (SysAttendCategory) getSysAttendCategoryService().findByPrimaryKey(fdCategoryId);
                getSysQuartzCoreService().delete(category, quartKey);
                return;
            }
        }
        String weeks =  param.has("weeks")? param.getString("weeks"):null;
        //当前星期跟需要运行的星期不相等。则不运行
        if (StringUtil.isNotNull(weeks)) {
            String[] weeksArr =weeks.split(";");
            //当前时间所属的星期几
            int currentWeekNumber = AttendUtil.getWeek(new Date());
            boolean isReturn =true;
            for (String week : weeksArr) {
                if (StringUtil.isNotNull(week)) {
                    int weekNumber = Integer.valueOf(week) % 7;
                    if(weekNumber==0){
                        weekNumber=7;
                    }
                    if(currentWeekNumber ==weekNumber){
                        isReturn =false;
                        break;
                    }
                }
            }
            if(isReturn){
                //运行的星期跟当前星期不匹配，则结束
                jobContext.logMessage("非运行周期，任务结束！" );
                return;
            }
        }

        //最晚打卡时间
        Date endTime =new Date(fdEndTime);
        //查询该考勤组缺卡任务
        Set<Date> dateList = getStatDateList(STAT_LOG_KEY_CATEGORY,fdCategoryId,isAcross,endTime);
        Date lastStatDate =executeCommon(dateList,Lists.newArrayList(fdCategoryId),jobContext);
        if(lastStatDate !=null) {
            // 记录当前统计时间戳
            saveStatLog(STAT_LOG_KEY_CATEGORY, fdCategoryId,lastStatDate);
        } else {
            jobContext.logMessage("执行考勤组缺卡定时任务结束，当前没有产生缺卡信息！" );
        }
    }

    /**
     * 执行缺卡的公用方法
     * @param dateList 日期列表
     * @param fdCategoryIds 考勤组列表
     * @param jobContext 定时任务上下午
     * @throws Exception
     */
    private Date executeCommon(Set<Date> paramDateList,List<String> fdCategoryIds,SysQuartzJobContext jobContext) throws Exception {
        Set<String> allUsers = new HashSet<>();
        List<Date> dateList =Lists.newArrayList(paramDateList);
        //排序
        Collections.sort(dateList,
                new Comparator<Date>() {
                    @Override
                    public int compare(Date o1, Date o2) {
                        return o1.compareTo(o2);
                    }
                });
        //正常情况下只有一天，如果服务停了可能有多天
        List<Date> newDate = new ArrayList<>();
        Date lastStatDate = null;
        if(CollectionUtils.isNotEmpty(dateList)) {
            //时间按照顺序排序，这里取最后一个时间
            lastStatDate = dateList.get(dateList.size()-1);
            for (Date statDate : dateList) {
                //统计日期 需要根据最好的执行成功时间来判断。
                jobContext.logMessage("执行考勤组缺卡定时任务开始,当前执行日期:" + DateUtil.convertDateToString(statDate, null));
                //生成缺卡记录。只统计生成了缺卡的人员。其他的不统计
                List<String> userIds = this.statCategorys(statDate, fdCategoryIds, true, jobContext);
                if (CollectionUtils.isNotEmpty(userIds)) {
                    allUsers.addAll(userIds);
                    newDate.add(statDate);
                }
            }
            List<String> tempStatUser = Lists.newArrayList(allUsers);
            List<Date> tempStatDate = Lists.newArrayList(newDate);
            //重新统计
            if (CollectionUtils.isNotEmpty(tempStatUser) && CollectionUtils.isNotEmpty(tempStatDate)) {
                getSysAttendStatJobService().stat(tempStatUser, tempStatDate);
            }
        }
        return lastStatDate;
    }
    /**
     * 考勤组为排班类型的缺卡定时任务
     * 定时任务未执行导致遗漏的日期进行补全。
     * 每日执行只会处理昨日，跟今日 最晚时间打卡时间之后的缺卡产生。
     * @param jobContext
     * @throws Exception
     */
    @Override
    public void executeAnother(SysQuartzJobContext jobContext) throws Exception {
        try {
            /**
             * 获取所有排班类型的考勤组
             * 排班类型-2天之前的缺卡记录是否执行
             * 历史数据补全的操作。
             */
            jobContext.logMessage("考勤缺卡记录补全定时任务执行开始");
            List<String> cateIds = getCategoryIds();
            jobContext.logMessage("获取排班类型考勤组id:" + cateIds);
            //本定时任务，默认处理昨天跟今天的考勤，防止在这处理之前定时任务没成功。
            //这里兼容处理 定时任务最后执行时间到2天前的缺卡记录。如果没有则不处理
            Set<Date> dateList = getStatList(STAT_LOG_KEY_ACROSS, -2);
            if (CollectionUtils.isNotEmpty(dateList)) {
                jobContext.logMessage("补全考勤组遗漏日期开始");
                Date lastStatDate = executeCommon(dateList, cateIds, jobContext);
                //更新最后执行时间为今日
                saveStatLog(STAT_LOG_KEY_ACROSS, null, lastStatDate);
                jobContext.logMessage("补全考勤组遗漏日期结束");
            }
            Date now=new Date();
            /**
             * 处理昨天和今天的缺卡记录
             * 获取该定时任务上次执行的日期
             * 根据每个考勤组获取对应的所有人员
             * 根据日期、人员 获取对应的排班详情
             * 判断排班中的最晚打卡时间(如果是跨天，则不处理今日的缺卡产生)
             * 过滤：上个执行的日期到当前所间隔是时间区间需要产生缺卡记录的人员
             */
            //拿昨日的日期去过滤
            Date statDate = DateUtil.getDate(-1);
            boolean isResult = schedulingCommon(statDate, cateIds, jobContext);
            //今日的缺卡产生。结束时间在今日
            statDate = DateUtil.getDate(0);
            boolean isTodayResult = schedulingCommon(statDate, cateIds, jobContext);
            jobContext.logMessage("考勤缺卡记录补全定时任务执行结束");
            //执行缺卡记录完成以后 增加日志
            //更新最后执行时间为当前时间，下次任务，取时间与允许时间之间 的时间来产生缺卡
            if(isResult || isTodayResult) {
                saveStatLog(STAT_LOG_KEY_ACROSS, null,now);
            }
        }catch (Exception e){
            logger.error("排班类型考勤组生成缺卡异常：{}",e);
            jobContext.logMessage(e.getMessage());
        }finally {
            AttendPersonUtil.release();
        }
    }

    private boolean schedulingCommon(Date statDate,List<String> cateIds,SysQuartzJobContext jobContext) throws Exception {
        // 写入缺卡记录
        List<String> tempStatUser =  statSchedulingCategorys(statDate,cateIds,true,jobContext);
        List<Date> tempStatDate = Lists.newArrayList(statDate);
        //重新统计
        if (CollectionUtils.isNotEmpty(tempStatUser) && CollectionUtils.isNotEmpty(tempStatDate)) {
            getSysAttendStatJobService().stat(tempStatUser, tempStatDate);
            return true;
        }
        return false;
    }

    /**
     * 根据考勤组生成缺卡(排班类型)，
     * 返回产生缺卡的人员：只统计考勤组的缺卡人员
     * @param date       日期
     * @param cateIds    考勤组ID
     * @param isOld      是否属于原始考勤组
     * @param jobContext 定时任务上下文，主要用于日志的记录
     * @throws Exception
     */
    private List<String> statSchedulingCategorys(Date date,
                                       List<String> cateIds,
                                       boolean isOld,
                                       SysQuartzJobContext jobContext) throws Exception {
        if (jobContext != null) {
            jobContext.logMessage("缺卡统计开始,日期" + DateUtil.convertDateToString(date, DATE_FORMAT) + ",cateIds:" + cateIds);
        }
        List<String> orgList = null;
        String dateTemp =DateUtil.convertDateToString(date, DATE_FORMAT);
        if ((cateIds == null || cateIds.isEmpty())) {
            jobContext.logMessage("执行日期:" + dateTemp + ",考勤组为空:");
            return null;
        }
        orgList = new ArrayList<String>();
        long current = System.currentTimeMillis();
        if(logger.isDebugEnabled()) {
            logger.debug("获取考勤组人员开始,耗时毫秒{}", current);
        }
        if (cateIds != null && !cateIds.isEmpty()) {
            //根据考勤组和时间获取考勤对象，具体到个人
            orgList = getSysAttendCategoryService().getAttendPersonIds(cateIds, date, isOld);
        }
        if(logger.isDebugEnabled()) {
            long end = System.currentTimeMillis();
            logger.debug("获取考勤组人员结束,{},耗时毫秒{}", end,end-current);
        }
        if (CollectionUtils.isNotEmpty(orgList)) {
            if (jobContext != null) {
                jobContext.logMessage("执行缺卡任务，统计的人员："+ Joiner.on(";").join(orgList));
            }
            if (jobContext != null) {
                jobContext.logMessage("执行日期:" + dateTemp + ",考勤组包括:" + cateIds);
            }
            /**
             * 考勤对象展开到的所有人员
             */
            List<SysOrgElement> orgPersonLists = AttendPersonUtil.expandToPersonSimple(orgList);
            //获取该人员昨天的排班情况
            //排班最晚打卡时间、最晚打卡时间标识是否跨天
            //如果是跨天，当前时间 减去25分钟
            Date now=new Date();
            //最后运行的时间
            Date lastUpdateDate = getMaxStatDate(STAT_LOG_KEY_ACROSS,null);

            Set<String> personIds=new HashSet<>();
            for (SysOrgElement person:orgPersonLists) {
                // 获取用户的排班信息
                List<Map<String, Object>> signTimeList = this.getSysAttendCategoryService().getAttendSignTimes( person,date);
                if (CollectionUtils.isEmpty(signTimeList)) {
                     continue;
                }
                //最后一个排班的最晚打卡时间标识
                Map<String, Object> signTimeMap = signTimeList.get(signTimeList.size()-1);
                //最晚打卡时间
                Date fdEndTime = (Date) signTimeMap.get("fdEndTime");
                //最晚打卡时间标识
                Integer endOverTimeType = (Integer) signTimeMap.get("endOverTimeType");

                //如果最晚打卡时间属于当日，并且定时任务最后执行成功时间，在昨日之前。则生成昨日的缺卡记录。
                fdEndTime = Integer.valueOf(2).equals(endOverTimeType)?
                        AttendUtil.addDate(AttendUtil.joinYMDandHMS(date,fdEndTime),1):
                        AttendUtil.joinYMDandHMS(date,fdEndTime);
                //最晚打卡时间 大于上次允许的时间，并且小于当前时间内的
                if(fdEndTime.getTime() > lastUpdateDate.getTime() &&  fdEndTime.getTime() < now.getTime()){
                    personIds.add(person.getFdId());
                }
            }
            if(CollectionUtils.isNotEmpty(personIds)){
                //进行缺卡判断并且补卡
                orgList = statMissCommon(Lists.newArrayList(personIds), date, jobContext,cateIds,isOld);
            }
        } else {
            if (jobContext != null) {
                jobContext.logMessage("执行缺卡任务，统计人员为空");
            }
        }
        if (jobContext != null) {
            jobContext.logMessage("缺卡统计结束,日期" + DateUtil.convertDateToString(date, DATE_FORMAT) + ",cateIds:" + cateIds);
        }
        return orgList;
    }

    /**
     * 统计昨天的数据
     * 定时任务执行
     * @param jobContext
     * @throws Exception
     */
    @Override
    public void statYesterday(SysQuartzJobContext jobContext)
            throws Exception {

        deleteStatLog();
        Date statDate = DateUtil.getDate(-1);
        jobContext.logMessage("开始执行昨日全系统人员的统计，日期: " + DateUtil.convertDateToString(statDate,DateUtil.PATTERN_DATE));
        getSysAttendStatJobService().stat(statDate);
    }
    /**
     * 根据考勤组生成缺卡，
     * 返回产生缺卡的人员：只统计考勤组的缺卡人员
     * @param date       日期
     * @param cateIds    考勤组ID
     * @param isOld      是否属于原始考勤组
     * @param jobContext 定时任务上下文，主要用于日志的记录
     * @throws Exception
     */
    private List<String> statCategorys(Date date,
                                       List<String> cateIds,
                                       boolean isOld,
                                       SysQuartzJobContext jobContext
                                       ) throws Exception {
        if (jobContext != null) {
            jobContext.logMessage("缺卡统计开始,日期" + DateUtil.convertDateToString(date, DATE_FORMAT) + ",cateIds:" + cateIds);
        }
        List<String> orgList = null;
        String dateTemp =DateUtil.convertDateToString(date, DATE_FORMAT);
        if ((cateIds == null || cateIds.isEmpty())) {
            jobContext.logMessage("执行日期:" + dateTemp + ",考勤组为空:");
            return null;
        }
        orgList = new ArrayList<String>();
        long current = System.currentTimeMillis();
        if(logger.isDebugEnabled()) {
            logger.debug("获取考勤组人员开始,耗时毫秒{}", current);
        }
        if (cateIds != null && !cateIds.isEmpty()) {
            //根据考勤组和时间获取考勤对象，具体到个人
            orgList = getSysAttendCategoryService().getAttendPersonIds(cateIds, date, isOld);
        }
        if(logger.isDebugEnabled()) {
            long end = System.currentTimeMillis();
            logger.debug("获取考勤组人员结束,{},耗时毫秒{}", end,end-current);
        }
        if (CollectionUtils.isNotEmpty(orgList)) {
            if (jobContext != null) {
                jobContext.logMessage("执行缺卡任务，统计的人员："+ Joiner.on(";").join(orgList));
            }
            if (jobContext != null) {
                jobContext.logMessage("执行日期:" + dateTemp + ",考勤组包括:" + cateIds);
            }
            //进行缺卡判断并且补卡
            orgList = statMissCommon(orgList, date, jobContext,cateIds,isOld);
        }else{
            if (jobContext != null) {
                jobContext.logMessage("执行缺卡任务，统计人员为空");
            }
        }
        if (jobContext != null) {
            jobContext.logMessage("缺卡统计结束,日期" + DateUtil.convertDateToString(date, DATE_FORMAT) + ",cateIds:" + cateIds);
        }
        return orgList;
    }

    /**
     * 根据人员、日期生成缺卡记录
     * 排除相关考勤组
     * @param orgList
     * @param date
     * @param oldCategoryIds 本次统计的考勤组范围。如果不是在该范围的人员。则不处理。
     * @param isOld 考勤组范围值的类型，是历史考勤组id还是原始考勤组id
     * @throws Exception
     */
    private void statMissCommon(List<String> orgList, Date date,List<String> oldCategoryIds,boolean isOld) throws Exception {
        statMissCommon(orgList, date, null,oldCategoryIds,isOld);
    }

    /**
     * 根据人员和日期统计考勤缺卡
     * @param orgList    人员列表
     * @param date       缺卡的日期
     * @param jobContext 定时任务的上下文
     * @throws Exception
     */
    private List<String>  statMissCommon(List<String> orgList, Date date, SysQuartzJobContext jobContext,List<String> oldCategoryIds,boolean isOld) throws Exception {
        if (CollectionUtils.isEmpty(orgList)) {
            return orgList;
        }
        StackTraceElement[] stackElements = new Throwable().getStackTrace();
        if(stackElements != null)
        {
            for(int i = 0; i < stackElements.length; i++)
            {
                logger.info(""+ stackElements[i]); 
            }
        }
        logger.info("abcdeeeee");
        try{
        for(String personId : orgList)
        	if("1821e4edcc2acd76e56b28548469edfd".equals(personId))
        		logger.info("叶秋怡");
        	else if("1821e4ee5666e9bd896126040e881fc2".equals(personId))
        		logger.info("陈伊玲");
        	else if("1821e4ee5889706da22472a41218d04e".equals(personId))
        		logger.info("梁仁");
        	else if("1821e4f6476e4c1f0c6d4ae4c9096e05".equals(personId))
        		logger.info("林川飞");
        	else if("1821a311ce1ed48a17416e247af950bb".equals(personId))
        		logger.info("李晏");
        	else if("1821e4ee919377b2f96fed548908bf52".equals(personId))
        		logger.info("林春凤");
        	else if("1821e4f6d68a57490c8d24c47a1b4fb8".equals(personId))
        		logger.info("吕登文");
        	else if("1821e4f6b3c1a08a9ee5652497dbaad6".equals(personId))
        		logger.info("王瑜杨");
        	else if("1821e4ede12ec00b236990844b68f595".equals(personId))
        		logger.info("张蕾");
        	else if("1821e4f6b50f179a4aae86e45598a456".equals(personId))
        		logger.info("袁正明");
        	else if("1821e4ef05b3abf57f2839345e1b63f8".equals(personId))
        		logger.info("张廷东");
        	else if("1847f0d3965bb1727902cb041f59625c".equals(personId))
        		logger.info("港口运输营运中心");
    }catch(Exception e){
    	e.printStackTrace();
    }
        //List去重处理
        orgList = new ArrayList(new HashSet(orgList));

        boolean isException =false;
        /**
         * 生成缺卡之前。先把流程同步到考勤的数据，重新计算下当天的。
         * 主要是避免流程先填写，后面考勤组有变动。导致班次不一致，产生没必要的缺卡
         */
        TransactionStatus status =null;
        try {
            if (jobContext != null) {
                jobContext.logMessage("执行缺卡任务，开始重新统计今日的流程考勤数据"+date);
            }
            //重新生成有效考勤记录
            status = TransactionUtils.beginNewTransaction();
            List<Integer> fdTypes = new ArrayList<Integer>();
            fdTypes.add(4);
            fdTypes.add(5);
            fdTypes.add(7);
            Date nextDate = AttendUtil.getEndDate(date, 0);
            // 出差/请假/外出记录
            List<SysAttendBusiness> busList = this.getSysAttendBusinessService().findBussList(orgList, date, nextDate, fdTypes);
    		busList.sort(new Comparator<SysAttendBusiness>(){
				@Override
				public int compare(SysAttendBusiness d1,SysAttendBusiness d2){
					return (int) (d1.getFdBusStartTime().getTime()-d2.getFdBusStartTime().getTime());
					
				}
			});
    		if(busList.size()>1)
			for (int j=0;j<busList.size();j++) {
				for (int k=j;k<busList.size()-1;k++) {
//					try{
//						boolean s = (busList.get(j).getFdBusType()==1||busList.get(j).getFdBusType()==13)&&(busList.get(k+1).getFdBusType()==1||busList.get(k+1).getFdBusType()==13)&&busList.get(j).getFdBusEndTime()!=null&&busList.get(k+1).getFdBusStartTime()!=null&&busList.get(j).getFdBusEndTime().getTime()<=busList.get(k+1).getFdBusStartTime().getTime();
//					}catch(Exception e){
//						e.printStackTrace();
//					}
					if((busList.get(j).getFdBusType()!=null&&busList.get(j).getFdBusType()==1||busList.get(j).getFdBusType()!=null&&busList.get(j).getFdBusType()==13)&&(busList.get(k+1).getFdBusType()!=null&&busList.get(k+1).getFdBusType()==1||busList.get(k+1).getFdBusType()!=null&&busList.get(k+1).getFdBusType()==13)&&busList.get(j).getFdBusEndTime()!=null&&busList.get(k+1).getFdBusStartTime()!=null&&busList.get(j).getFdBusEndTime().getTime()<=busList.get(k+1).getFdBusStartTime().getTime()){
						busList.get(j).setFdBusEndTime(busList.get(k+1).getFdBusEndTime());
					}
				}
			}

			busList.sort(new Comparator<SysAttendBusiness>(){
				@Override
				public int compare(SysAttendBusiness d1,SysAttendBusiness d2){
					return (int) (d1.getFdBusEndTime().getTime()-d2.getFdBusEndTime().getTime());
					
				}
			});
			for (int j=busList.size()-1;j>=0;j--) {
				for (int k=busList.size()-1;k>0;k--) {
					if((busList.get(j).getFdBusType()!=null&&busList.get(j).getFdBusType()==1||busList.get(j).getFdBusType()!=null&&busList.get(j).getFdBusType()==13)&&(busList.get(k-1).getFdBusType()!=null&&busList.get(k-1).getFdBusType()==1||busList.get(k-1).getFdBusType()!=null&&busList.get(k-1).getFdBusType()==13)&&busList.get(j).getFdBusStartTime()!=null&&busList.get(k-1).getFdBusEndTime()!=null&&busList.get(j).getFdBusStartTime().getTime()<=busList.get(k-1).getFdBusEndTime().getTime()){
						busList.get(j).setFdBusStartTime(busList.get(k-1).getFdBusStartTime());
					}
				}
			}
            if (CollectionUtils.isNotEmpty(busList)) {
                //根据流程（流程分类）来生成对应的有效考勤记录
                for (SysAttendBusiness business : busList) {
                    if (Integer.valueOf(5).equals(business.getFdType())) {
                        getSysAttendListenerCommonService().updateSysAttendMainByLeaveBis(business, date, nextDate);
                    } else if (Integer.valueOf(7).equals(business.getFdType())) {
                        getSysAttendListenerCommonService().updateSysAttendMainByOutgoing(business);
                    } else if (Integer.valueOf(4).equals(business.getFdType())) {
                        getSysAttendListenerCommonService().updateSysAttendMainByBusiness(business, date, nextDate);
                    }
                }
            }
        } catch (Exception e) {
            isException = true;
            logger.error("只统计考勤组的缺卡人员", e);
            throw new Exception(e);
//            e.printStackTrace();
        } finally {
            if (isException && status != null) {
                TransactionUtils.getTransactionManager().rollback(status);
            } else if (status != null) {
                TransactionUtils.getTransactionManager().commit(status);
            }
            if (jobContext != null) {
                jobContext.logMessage("执行缺卡任务，统计流程考勤数据完成！"+date);
            }
            isException = false;
            status =null;
        }
        //本次生成的缺卡人员
        Set<String> missPersons=new HashSet<>();
        if(!isException) {
            try {
                status = TransactionUtils.beginNewTransaction();
                // 用户组分割
                int maxCount = 1000;
                List<List> groupLists = new ArrayList<List>();
                if (orgList.size() <= maxCount) {
                    groupLists.add(orgList);
                } else {
                    groupLists = AttendUtil.splitList(orgList, maxCount);
                }
                if (jobContext != null) {
                    jobContext.logMessage("根据人数进行切分，切分后总共有" + groupLists.size() + "组");
                }
                Date now = new Date();
                Date newDate = AttendUtil.getDate(date, 0);

                for (int i = 0; i < groupLists.size(); i++) {
                    List<String> _tmpList = groupLists.get(i);
                    String[] ids = new String[_tmpList.size()];
                    _tmpList.toArray(ids);
                    //大于今天的日期不产生缺卡记录
                    if (newDate.getTime() > now.getTime()) {
                        continue;
                    }
                    //根据考勤组来分类处理
                    Map<String, List> cateMap = getUserCategory(ids, date,oldCategoryIds,isOld);
                    for (String cateId : cateMap.keySet()) {
                        List<HashMap<String, String>> _orgInfoList = cateMap.get(cateId);
                        List<String> _orgIdList = new ArrayList<String>();
                        for (HashMap<String, String> map : _orgInfoList) {
                            String orgId = map.get("orgId");
                            _orgIdList.add(orgId);
                        }
                        if (jobContext != null) {
                            jobContext.logMessage("统计日期：" + DateUtil.convertDateToString(date, DATE_FORMAT) + ",考勤组id:" + cateId);
                        }
                        SysAttendCategory category = CategoryUtil.getCategoryById(cateId);

                        Set<String> missPersonIds = null;
                        if (Integer.valueOf(1).equals(category.getFdShiftType())) {
                            missPersonIds = genAttendMainOfTimeArea(date, _orgIdList, jobContext);
                        } else {
                            //固定班次和自定义班次
                            missPersonIds = genAttendMain(category, date, _orgIdList, _orgInfoList, jobContext);
                        }
                        if(CollectionUtils.isNotEmpty(missPersonIds)){
                            missPersons.addAll(missPersonIds);
                        }
                    }
                }
                if (jobContext != null) {
                    jobContext.logMessage("日期："+DateUtil.convertDateToString(date, DATE_FORMAT)+"产生缺卡人员总数："+missPersons.size());
                    jobContext.logMessage("产生缺卡人员ID：" + Joiner.on(";").join(missPersons));
                }
            } catch (Exception e) {
                isException = true;
                logger.error("只统计考勤组的缺卡人员", e);
                if (jobContext != null) {
                    jobContext.logMessage("统计考勤组缺卡人员出错" + e.getMessage());
                }
                throw new Exception(e);
            } finally {
                if (isException && status != null) {
                    TransactionUtils.rollback(status);
                }else if(status !=null){
                    TransactionUtils.commit(status);
                }
            }
        }
        return Lists.newArrayList(missPersons);
    }

    /**
     * 外部生成缺卡以及统计
     * @param date 统计的日期 不允许为空
     * @param categoryId
     *            考勤组ID
     * @param isCalMissed
     *            是否写入缺卡记录
     * @param isStatMonth
     *            是否需要重新统计月度数据
     * @param orgList
     *            用户id列表(categoryId与orgList至少一项不允许为空)
     * @throws Exception
     */
    @Override
    public void stat(Date date, String categoryId, boolean isCalMissed,
			Boolean isStatMonth, List<String> orgList,Map<String, com.alibaba.fastjson.JSONObject> monthDataMap) throws Exception {
        Date stateDate = AttendUtil.getDate(date, 0);
        if (StringUtil.isNull(categoryId) && CollectionUtils.isEmpty(orgList)) {
            return;
        }
        SysAttendCategory category = null;
        if (StringUtil.isNotNull(categoryId)) {
            category = CategoryUtil.getCategoryById(categoryId);
            if (category == null && (orgList == null || orgList.isEmpty())) {
                return;
            }
        }
        //组织统计人员列表
        List orgListNew = new ArrayList<>();
        Set<String> orgSet = new HashSet<>();
        //避免人员重复计算，转成set处理
        if (CollectionUtils.isNotEmpty(orgList)) {
            orgSet.addAll(orgList);
        }
        if (StringUtil.isNotNull(categoryId)) {
            orgSet.addAll(getSysAttendCategoryService().getAttendPersonIds(Lists.newArrayList(categoryId), stateDate, false));
        }
        orgListNew.addAll(orgSet);
        if (isCalMissed && CollectionUtils.isNotEmpty(orgListNew)) {
            statMissCommon(orgListNew, stateDate ,StringUtil.isNotNull(categoryId) ?Lists.newArrayList(categoryId):null,true);
        }
        // 统计每日数据
        getSysAttendStatJobService().stat(stateDate, isStatMonth, orgListNew,monthDataMap);
    }


    /**
     * 是否休息日
     *
     * @param date
     * @param category
     * @param org
     * @return
     * @throws Exception
     */
    private boolean isRestDay(Date date, SysAttendCategory category,
                              SysOrgElement org) throws Exception {
        List<SysAttendCategory> list = new ArrayList<SysAttendCategory>();
        list.add(category);
        com.alibaba.fastjson.JSONArray datas = getSysAttendCategoryService().filterAttendCategory(list,
                date, true, org);
        return datas == null || datas.isEmpty();
    }

    /**
     * 删除缺卡的记录
     *
     * @param date
     * @param category
     * @throws Exception
     */
    private void deleteMissed(Date date, SysAttendCategory category,
                              List<String> personList)
            throws Exception {
        DataSource dataSource = (DataSource) SpringBeanUtil
                .getBean("dataSource");
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            if (personList == null) {
                personList = new ArrayList<String>();
            }
            if (category != null) {
                List<String> orgList = this.getSysAttendCategoryService().getAttendPersonIds(category.getFdId(), date);
                personList.addAll(orgList);
            }
            if (personList.isEmpty()) {
                return;
            }
            Date startDate = AttendUtil.getDate(date, 0);
            Date endDate = AttendUtil.getDate(date, 1);
            if (null == category) {
                HQLInfo hqlInfo = new HQLInfo();
                StringBuffer whereBlock = new StringBuffer();
                whereBlock.append("1=1");
                whereBlock.append(" and " + HQLUtil.buildLogicIN("sysAttendMain.docCreator.fdId", personList));
                whereBlock.append(" and sysAttendMain.docCreateTime >=:docCreateTime");
                whereBlock.append(" and sysAttendMain.docCreateTime <:docCreateTimes");
                whereBlock.append(" and sysAttendMain.fdStatus =0 and sysAttendMain.fdState is null");
                hqlInfo.setWhereBlock(whereBlock.toString());
                hqlInfo.setParameter("docCreateTime", startDate);
                hqlInfo.setParameter("docCreateTimes", endDate);
                List<SysAttendMain> list = sysAttendMainService.findList(hqlInfo);
                if (null != list && list.size() > 0) {
                    category = CategoryUtil.getFdCategoryInfo(list.get(0));
                }
            }
            String sqlStr = "";
            if (null != category) {
                //处理127002
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                String formatStart = sd.format(startDate);
                String formatEnd = sd.format(endDate);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String startTime = sdf.format(category.getFdStartTime());
                String endTime = sdf.format(category.getFdEndTime());
                formatStart = formatStart + " " + startTime;
                formatEnd = formatEnd + " " + endTime;
                sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startDate = sd.parse(formatStart);
                endDate = sd.parse(formatEnd);
            } else {
                //处理126932
                sqlStr = " and fd_is_across <> ? ";
            }


            String sql = "update sys_attend_main set doc_status=1,fd_alter_record='缺卡定时任务删除无效缺卡记录',doc_alter_time=? where "
                    + HQLUtil.buildLogicIN("doc_creator_id", personList)
                    + " and doc_create_time>=? and doc_create_time<? and fd_status=0 and fd_state is null" + sqlStr;
            conn = dataSource.getConnection();
            statement = conn.prepareStatement(sql);
            statement.setTimestamp(1,
                    new Timestamp(new Date().getTime()));
            statement.setTimestamp(2,
                    new Timestamp(startDate.getTime()));
            statement.setTimestamp(3,
                    new Timestamp(endDate.getTime()));
            if (StringUtils.isNoneBlank(sqlStr)) {
                statement.setBoolean(4, true);
            }
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(conn);
        }
    }

    /**
     * 更新打卡记录为正常
     *
     * @param date
     * @param category
     * @throws Exception
     */
    private void updateToNormal(Date date, SysAttendCategory category,
                                List<String> personList) throws Exception {
        DataSource dataSource = (DataSource) SpringBeanUtil
                .getBean("dataSource");
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            if (personList == null) {
                personList = new ArrayList<String>();
            }
            if (category != null) {
                List<String> orgList = this.getSysAttendCategoryService().getAttendPersonIds(category.getFdId(), date);
                personList.addAll(orgList);
            }
            if (personList.isEmpty()) {
                return;
            }
            String sql = "update sys_attend_main set fd_status=1 where "
                    + HQLUtil.buildLogicIN("doc_creator_id", personList)
                    + " and doc_create_time>=? and doc_create_time<? and (fd_status=2 or fd_status=3 or fd_state is not null)";
            conn = dataSource.getConnection();
            statement = conn.prepareStatement(sql);
            statement.setTimestamp(1,
                    new Timestamp(AttendUtil.getDate(date, 0).getTime()));
            statement.setTimestamp(2,
                    new Timestamp(AttendUtil.getDate(date, 1).getTime()));
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(conn);
        }
    }
    /**
     * 获取需要统计哪些天的列表
     * 默认情况是当前日期的前一天。
     * @return
     */
    private Set<Date> getStatDateList(String operType,String fdCategoryId,Boolean isArcoss,Date endTime) {
        Set<Date> list = new HashSet<>();
        int defaultDayNums=0;
        if(Boolean.TRUE.equals(isArcoss)){
            //考勤组的最晚打卡标识是次日
            defaultDayNums =defaultDayNums-1;
        }
        //当前时间
        Date now=new Date();
        //计算缺卡的日期 2022-06-24
        Date endDate = DateUtil.getDate(defaultDayNums);
        //考勤组最晚打卡时间的拼接
        endTime =AttendUtil.joinYMDandHMS(endDate,endTime);
        //当前类型的最后一次计算的日期 2022-06-23
        Date statDate = getMaxStatDate(operType,fdCategoryId);
        if (statDate == null) {
            statDate = getMaxStatDate("sysattendMainJob",null);
        }
        //统计昨日
        if (statDate == null) {
            list.add(DateUtil.getDate(-1));
        }else if(statDate.compareTo(endDate)>=0){
            //统计日期跟最后一次成功的日期相同。则直接返回
            return list;
        }

        Date beginDate = null;
        boolean isEquitToday = true;
        //当前时间 小于 考勤组最晚打卡时间
        if(now.getTime() < endTime.getTime()){
            if(statDate == null){
                return list;
            }
            //当前时间，在最晚打卡时间之前。
            beginDate = AttendUtil.getDate(statDate, 1);
            if(beginDate.getTime() >= endDate.getTime()){
                //统计日期跟最后一次成功的日期相同。则直接返回
                //重复执行，允许之前前1天的缺卡统计
                list.add(DateUtil.getDate(-1));
                return list;
            }
            isEquitToday =false;
        } else {
            beginDate = AttendUtil.getDate(statDate, 1);
        }
        while (beginDate != null) {
            list.add(beginDate);
            beginDate = AttendUtil.getDate(beginDate, 1);
            //如果当前执行时间大于应该执行的日期
            if(isEquitToday){
                if (beginDate.getTime() > endDate.getTime()) {
                    beginDate = null;
                    break;
                }
            }else {
                if (beginDate.getTime() >= endDate.getTime()) {
                    beginDate = null;
                }
            }
        }
        return list;
    }

    /**
     * 获取需要统计哪些天的列表
     * 默认情况是当前日期的前一天。
     * @return
     */
    private Set getStatList(String operType,int defaultDayNums) {
        Set<Date> list = new HashSet<>();
        Date endDate = DateUtil.getDate(defaultDayNums);
        //当前类型的最后一次执行成功时间
        Date statDate = getMaxStatDate(operType,null);
        if(statDate == null){
            list.add(DateUtil.getDate(defaultDayNums));
            return list;
        }
        //最后执行时间在需要计算的时间之后。则不处理
        if (AttendUtil.getDate(statDate, 1).after(endDate)) {
            return list;
        }
        list.add(statDate);
        Date startDate = AttendUtil.getDate(statDate, 1);
        while (startDate != null) {
            list.add(startDate);
            startDate = AttendUtil.getDate(startDate, 1);
            if (startDate.after(endDate)) {
                startDate = null;
            }
        }

        return list;
    }


    /**
     * 获取所有排班类型的考勤组
     * @return
     */
    private List<String> getCategoryIds() {
        List<String> cateIds = new ArrayList<String>();
        try {
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock("sysAttendCategory.fdStatus=1 and sysAttendCategory.fdType=1 and sysAttendCategory.fdShiftType=1 ");
            hqlInfo.setSelectBlock("sysAttendCategory.fdId");
            return getSysAttendCategoryService().findList(hqlInfo);
        } catch (Exception e) {
            logger.error("排班类型的考勤组ID获取失败:{}", e);
        }
        return cateIds;
    }


    /**
     * 非跨天考勤组的缺卡，每天2点执行一次
     * @param jobContext
     * @throws Exception
     */
    @Override
    public void execute(SysQuartzJobContext jobContext) throws Exception {
        jobContext.logMessage("该定时任务已废弃，请禁用" );
       /* List<Date> dateList = getStatList("sysattendMainJob");
        Set<String> allUsers = new HashSet<>();
        long current = System.currentTimeMillis();
        if(logger.isDebugEnabled()) {
            logger.debug("执行非跨天缺卡记录的定时任务，开始{}", System.currentTimeMillis());
        }
        //正常情况下只有一天，如果服务停了可能有多天
        List<Date> newDate = new ArrayList<>();
        for (Date statDate : dateList) {
            jobContext.logMessage("执行非跨天考勤组缺卡定时任务开始,当前执行日期:" + DateUtil.convertDateToString(statDate, null));
            // 写入缺卡记录
            // 获取非跨天的考勤组id
            List cateIds = getCategoryIds(statDate, false);
            jobContext.logMessage("获取非跨天考勤组ID信息有" + cateIds);
            //生成缺卡记录。只统计生成了缺卡的人员。其他的不统计
            List<String> userIds = this.statCategorys(statDate, cateIds, true, jobContext);
            if (CollectionUtils.isNotEmpty(userIds)) {
                allUsers.addAll(userIds);
                newDate.add(statDate);
            }
            jobContext.logMessage("执行非跨天考勤组缺卡定时任务结束,当前执行日期:" + DateUtil.convertDateToString(statDate, null));
        }
        if(logger.isDebugEnabled()) {
            long end = System.currentTimeMillis();
            logger.debug("执行非跨天缺卡记录的定时任务，生成缺卡记录结束{},耗时毫秒{}", end,end-current);
        }
        List<String> tempStatUser = Lists.newArrayList(allUsers);
        List<Date> tempStatDate = Lists.newArrayList(newDate);
        //重新统计
        if (CollectionUtils.isNotEmpty(tempStatUser) && CollectionUtils.isNotEmpty(tempStatDate)) {
            getSysAttendStatJobService().stat(tempStatUser, tempStatDate);
        }
        // 记录当前统计时间戳
        saveStatLog("sysattendMainJob");
        if(logger.isDebugEnabled()) {
            long end = System.currentTimeMillis();
            logger.debug("执行非跨天缺卡记录的定时任务，全部结束{},耗时毫秒", end,end-current);
        }*/
    }

    ISysAttendStatLogService sysAttendStatLogService;
    private ISysAttendStatLogService getSysAttendStatLogService(){
        if(sysAttendStatLogService ==null) {
            sysAttendStatLogService = (ISysAttendStatLogService) SpringBeanUtil.getBean("sysAttendStatLogService");
        }
        return sysAttendStatLogService;
    }

    /**
     * 生成缺卡记录的日志记录
     * @param operType 类型
     * @param fdCategoryId 考勤组ID
     * @param statDate 生成的日期
     */
    private void saveStatLog(String operType,
                             String fdCategoryId,
                             Date statDate
    ) {
        try {
            SysAttendStatLog log = new SysAttendStatLog();
            log.setFdCreateTime(statDate);
            log.setFdCategoryId(fdCategoryId);
            log.setFdOperType(operType);
            log.setDocCreateTime(new Date());
            getSysAttendStatLogService().add(log);
        } catch (Exception e) {
        }
    }
    /**
     * 清理90天之前的生成缺卡执行日志 数据。
     */
    private void deleteStatLog(){
        boolean isException =false;
        TransactionStatus status = TransactionUtils.beginNewTransaction();
        try {
            Query query = getBaseDao().getHibernateSession().createQuery("delete from SysAttendStatLog where fdCreateTime <:endTime");
            query.setParameter("endTime", AttendUtil.getDate(new Date(), -90));
            query.executeUpdate();
        }catch (Exception e){
            isException =true;
        } finally {
            if(isException){
                TransactionUtils.rollback(status);
            }else {
                TransactionUtils.commit(status);
            }
        }
    }

    /**
     * 获取考勤组与用户间的映射关系(考勤组id -> 用户List)
     *
     * @param ids
     * @param stateDate
     * @param oldCategoryIds 本次需要统计的考勤组ID
     * @param isOld 原始考勤组id 还是历史考勤组id
     * @return
     * @throws Exception
     */
    private Map getUserCategory(String[] ids, Date stateDate,List<String> oldCategoryIds,boolean isOld) throws Exception {
        List<SysOrgElement> eleList = orgService.findByPrimaryKeys(ids);
        Map<String, List> cateMap = new HashMap<String, List>();
        Map<String, SysAttendCategory> tmpCategoryMap = new HashMap<String, SysAttendCategory>();

        for (SysOrgElement orgEle : eleList) {
            String categoryId = getSysAttendCategoryService().getAttendCategory(orgEle, stateDate);
            if (StringUtil.isNull(categoryId)) {
                continue;
            }
            if(CollectionUtils.isNotEmpty(oldCategoryIds)) {
                if (isOld) {
                    //原始考勤组id，不在本次统计 考勤组。则不处理
                    SysAttendHisCategory hisCategory = CategoryUtil.getHisCategoryById(categoryId);
                    if (hisCategory == null || !oldCategoryIds.contains(hisCategory.getFdCategoryId())) {
                    	logger.info(orgEle.getFdName());
                        continue;
                    }
                } else {
                    if (!oldCategoryIds.contains(categoryId)) {
                        //历史考勤组id，不在本次考勤组统计结果内。则不处理
                    	logger.info(orgEle.getFdName());
                        continue;
                    }
                }
            }
            SysAttendCategory sysAttendCategory = null;
            // 缓存考勤组信息
            if (!tmpCategoryMap.containsKey(categoryId)) {
                sysAttendCategory = CategoryUtil.getCategoryById(categoryId);
                if (sysAttendCategory == null) {
                	logger.info(orgEle.getFdName());
                    continue;
                }
                tmpCategoryMap.put(categoryId, sysAttendCategory);
            } else {
                sysAttendCategory = tmpCategoryMap.get(categoryId);
            }
            if (sysAttendCategory == null) {
            	logger.info(orgEle.getFdName());
                continue;
            }
            //休息日不生成缺卡
            if (isRestDay(stateDate, sysAttendCategory, orgEle)) {
            	logger.info(orgEle.getFdName());
                continue;
            }
            //考勤组生效日期
            Date effectTime = sysAttendCategory.getFdEffectTime() == null ? sysAttendCategory.getDocCreateTime() : sysAttendCategory.getFdEffectTime();
            SysOrgPerson person = null;
            if (orgEle instanceof SysOrgPerson) {
                person = (SysOrgPerson) orgEle;
            } else {
                person = (SysOrgPerson) orgService.format(orgEle);
            }
            //人员的入职时间为空则取创建时间
            Date personCreateTime = person.getFdHiredate() == null ? orgEle.getFdCreateTime() : person.getFdHiredate();
            //人员离职日期。失效状态下取人员离职日期
            Date personAlterTime = null;
            if (Boolean.FALSE.equals(person.getFdIsAvailable())) {
                //如果是无效状态，并且离职日期不为空，则取离职日期.否则取人员最后修改时间
                personAlterTime = person.getFdLeaveDate() == null ? person.getFdAlterTime() : person.getFdLeaveDate();
            }
            // 根据用户入职时间判断是否写入缺卡记录
            if (personCreateTime != null && AttendUtil.getDate(personCreateTime, 0).getTime() > AttendUtil.getDate(effectTime, 0).getTime()) {
                effectTime = personCreateTime;
            }
            //计算日期，在考勤启用时间之前 或者 人员的离职日期之前
            if (AttendUtil.getDate(stateDate, 0).before(AttendUtil.getDate(effectTime, 0))
                    || (personAlterTime != null && AttendUtil.getDate(personAlterTime, 0).before(AttendUtil.getDate(stateDate, 0)))) {
            	logger.info(orgEle.getFdName());
                continue;
            }

            //人员信息
            Map<String, String> orgInfo = new HashMap<String, String>();
            orgInfo.put("orgId", orgEle.getFdId());
            orgInfo.put("orgHId", orgEle.getFdHierarchyId());

            List idList = cateMap.get(categoryId);
            if (CollectionUtils.isEmpty(idList)) {
                idList = new ArrayList<>();
            }
            idList.add(orgInfo);
            cateMap.put(categoryId, idList);
        }
        return cateMap;
    }

    /**
     * 获取某一天的时间设置
     *
     * @param category
     * @param date
     * @return
     */
    private SysAttendCategoryTimesheet getTimeSheet(SysAttendCategory category, Date date) {
        List<SysAttendCategoryTimesheet> tSheets = category.getFdTimeSheets();
        if (tSheets != null && !tSheets.isEmpty()) {
            for (SysAttendCategoryTimesheet tSheet : tSheets) {
                if (StringUtil.isNotNull(tSheet.getFdWeek()) && tSheet.getFdWeek().indexOf(AttendUtil.getWeek(date) + "") > -1) {
                    return tSheet;
                }
            }
        }
        return null;
    }

    /**
     * 获取班次
     *
     * @param category 考勤组
     * @param date     日期
     * @description:
     * @return: net.sf.json.JSONArray
     * @author: wangjf
     * @time: 2022/3/1 5:05 下午
     */
    private JSONArray getWorkTime(SysAttendCategory category, Date date) {
        JSONArray works = new JSONArray();
        List<SysAttendCategoryWorktime> workTimes = null;

        if ((Integer.valueOf(0).equals(category.getFdShiftType()) || Integer.valueOf(3).equals(category.getFdShiftType()))
                && Integer.valueOf(1).equals(category.getFdSameWorkTime())) {
            List<SysAttendCategoryTimesheet> tSheets = category.getFdTimeSheets();
            if (tSheets != null && !tSheets.isEmpty()) {
                for (SysAttendCategoryTimesheet tSheet : tSheets) {
                    if (StringUtil.isNotNull(tSheet.getFdWeek()) && tSheet.getFdWeek().indexOf(AttendUtil.getWeek(date) + "") > -1) {
                        workTimes = tSheet.getAvailWorkTime();
                        break;
                    }
                }
            }
        } else {
            workTimes = category.getAvailWorkTime();
        }

        if (workTimes == null || workTimes.isEmpty()) {
            if(category !=null && category.getFdShiftType() == 3 ){
                List<SysAttendCategoryWorktime> fdWorkTime = category.getFdWorkTime();
                if(fdWorkTime!=null && fdWorkTime.size()>0){
                    SysAttendCategoryWorktime workTime = fdWorkTime.get(0);
                    JSONObject json = new JSONObject();
                    json.put("fdWorkId", workTime.getFdId());
                    json.put("fdWorkType", 0);
                    if(workTime.getFdStartTime() != null){
                        json.put("signTime", workTime.getFdStartTime().getTime());
                    }else{
                        json.put("signTime", (1000 * 60 * 60 * 9));
                    }
                    works.add(json);
                    json = new JSONObject();
                    json.put("fdWorkId", workTime.getFdId());
                    json.put("fdWorkType", 1);
                    if(workTime.getFdEndTime()!=null){
                        json.put("signTime", workTime.getFdEndTime().getTime());
                    }else{
                        json.put("signTime", (1000 * 60 * 60 * 18));
                    }
                    works.add(json);
                }
            }

            return works;
        }
        if(category.getFdShiftType() == 4 && workTimes.size() > 0){
            SysAttendCategoryWorktime workTime = workTimes.get(0);
            JSONObject json = new JSONObject();
            json.put("fdWorkId", workTime.getFdId());
            json.put("fdWorkType", 0);
            if(workTime.getFdStartTime() != null){
                json.put("signTime", workTime.getFdStartTime().getTime());
            }else{
                json.put("signTime", 3600000L);
            }
            works.add(json);
            json = new JSONObject();
            json.put("fdWorkId", workTime.getFdId());
            json.put("fdWorkType", 1);
            if(workTime.getFdEndTime()!=null){
                json.put("signTime", workTime.getFdEndTime().getTime());
            }else{
                json.put("signTime", 36000000L);
            }
            works.add(json);
            //3600000
            //36000000
        }else{
            for (SysAttendCategoryWorktime workTime : workTimes) {
                JSONObject json = new JSONObject();
                json.put("fdWorkId", workTime.getFdId());
                json.put("fdWorkType", 0);
                json.put("signTime", workTime.getFdStartTime().getTime());
                works.add(json);
                json = new JSONObject();
                json.put("fdWorkId", workTime.getFdId());
                json.put("fdWorkType", 1);
                json.put("signTime", workTime.getFdEndTime().getTime());
                works.add(json);
            }
        }

        return works;
    }

    /**
     * 插入缺卡信息
     *
     * @param categoryId 考勤组id
     * @param fdWorkId   班次id
     * @param fdWorkType 班次类型
     * @param list       需要补卡的人员
     * @param signTime   打卡时间
     * @param statDate   统计日期
     * @param areaMap    集团分级场所信息
     * @param jobContext 定时任务上下文
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2022/3/2 9:00 上午
     */
    private List<String> addBatch(String categoryId, String fdWorkId, int fdWorkType,
                          List<HashMap<String, String>> list, Long signTime, Date statDate,
                          Map<String, String> areaMap, SysQuartzJobContext jobContext)
            throws Exception {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(statDate);
        calendar.add(Calendar.DATE, 1);
        Date nextDate = calendar.getTime();
        DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
        Connection conn = null;
        PreparedStatement insert = null;
        String fdOverTimeType = "no";
        //记录缺卡人员插入信息
        List<String> insertOrgIdList = new ArrayList<>();
        try {
            SysAttendCategoryWorktime sysAttendCategoryWorktime = null;
            SysAttendCategory category = CategoryUtil.getCategoryById(categoryId);
            if (null != category && null != category.getFdWorkTime() && category.getFdWorkTime().size() > 0) {
                for (SysAttendCategoryWorktime worktime : category.getFdWorkTime()) {
                    if (null != worktime && fdWorkId.equals(worktime.getFdId())) {
                        sysAttendCategoryWorktime = worktime;
                        break;
                    }
                }
            }
            if (null != sysAttendCategoryWorktime && Integer.valueOf(AttendConstant.FD_OVERTIME_TYPE[2]).equals(sysAttendCategoryWorktime.getFdOverTimeType())) {
                if (fdWorkType == 1) {
                    //跨天的记录
                    fdOverTimeType = "yes";
                }
            }
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            StackTraceElement[] stackElements = new Throwable().getStackTrace();
            if(stackElements != null)
            {
                for(int i = 0; i < stackElements.length; i++)
                {
                    logger.info(""+ stackElements[i]); 
                }
            }
            int loop = 0;
            insert = conn.prepareStatement("insert into sys_attend_main(fd_id,fd_status,doc_create_time,fd_work_type,fd_category_his_id," +
                    "doc_creator_id,fd_work_id,fd_date_type,doc_creator_hid,doc_status,fd_base_work_time,fd_is_across,auth_area_id," +
                    "doc_alter_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            for (HashMap<String, String> map : list) {
                if (loop > 0 && (loop % 2000 == 0)) {
                    insert.executeBatch();
                    conn.commit();
                }
                insertOrgIdList.add(map.get("orgId"));
                String fdId = IDGenerator.generateID();
                insert.setString(1, fdId);
                insert.setInt(2, 0);
                if ("yes".equals(fdOverTimeType)) {
                    insert.setTimestamp(3, getSignDate(signTime, nextDate));
                } else {
                    insert.setTimestamp(3, getSignDate(signTime, statDate));
                }
                insert.setInt(4, fdWorkType);
                insert.setString(5, categoryId);
                insert.setString(6, map.get("orgId"));
                insert.setString(7, fdWorkId);
                insert.setInt(8, 0);
                insert.setString(9, map.get("orgHId"));
                insert.setInt(10, 0);
                if ("yes".equals(fdOverTimeType)) {
                    insert.setTimestamp(11, getSignDate(signTime, nextDate));
                    insert.setInt(12, 1);
                } else {
                    insert.setTimestamp(11, getSignDate(signTime, statDate));
                    insert.setInt(12, 0);
                }
                insert.setString(13, "orgId");
//                insert.setString(13, areaMap.get(map.get("orgId")));
                insert.setTimestamp(14, new Timestamp(System.currentTimeMillis()));
                insert.addBatch();
                loop++;
            }
            insert.executeBatch();
            conn.commit();
            if (jobContext != null) {
                jobContext.logMessage("考勤组Id:" + categoryId + ",statDate统计时间" + DateUtil.convertDateToString(statDate, null) + ",缺卡插入成功用户Id:" + insertOrgIdList);
            }
        } catch (Exception ex) {
            logger.error("考勤组Id:{},statDate统计时间:{},发生错误", categoryId, DateUtil.convertDateToString(statDate, null), ex);
            conn.rollback();
            if (jobContext != null) {
                jobContext.logMessage("考勤组Id:" + categoryId + ",statDate统计时间" + DateUtil.convertDateToString(statDate, null) + ",错误信息：" + ex.getMessage());
                jobContext.logMessage("考勤组Id:" + categoryId + ",statDate统计时间" + DateUtil.convertDateToString(statDate, null) + ",缺卡插入失败用户Id:" + insertOrgIdList);
            }
            throw ex;
        } finally {
            JdbcUtils.closeStatement(insert);
            JdbcUtils.closeConnection(conn);
        }
        return insertOrgIdList;
    }

    /**
     * 插入考勤缺卡状态
     *
     * @param userList
     * @param areaMap
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2022/3/1 4:50 下午
     */
    private void addBatch(List<Map<String, Object>> userList, Map<String, String> areaMap, SysQuartzJobContext jobContext) throws Exception {
        DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
        Connection conn = null;
        PreparedStatement insert = null;
        List<String> userIdList = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            int loop = 0;
            insert = conn.prepareStatement(
                    "insert into sys_attend_main(fd_id,fd_status,doc_create_time,fd_work_type,fd_category_his_id," +
                            "doc_creator_id,fd_date_type,doc_creator_hid,doc_status,fd_work_key,fd_base_work_time,fd_is_across,auth_area_id) " +
                            "values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

            for (Map<String, Object> map : userList) {
                if (loop > 0 && (loop % 2000 == 0)) {
                    insert.executeBatch();
                    conn.commit();
                }
                String fdWorkTimeId = (String) map.get("fdWorkTimeId");
                String categoryId = (String) map.get("categoryId");
                Integer fdWorkType = (Integer) map.get("fdWorkType");
                String orgId = (String) map.get("orgId");
                String orgHId = (String) map.get("orgHId");
                Date signTime = (Date) map.get("signTime");
                Date signDate = (Date) map.get("signDate");
                Integer nextOverTimeType = (Integer) map.get("overTimeType");

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(signDate);
                calendar.add(Calendar.DATE, 1);
                Date nextDate = calendar.getTime();

                insert.setString(1, IDGenerator.generateID());
                insert.setInt(2, 0);
                if (Integer.valueOf(AttendConstant.FD_OVERTIME_TYPE[2])
                        .equals(nextOverTimeType)) {
                    insert.setTimestamp(3, getSignDate(signTime.getTime(), nextDate));
                } else {
                    insert.setTimestamp(3, getSignDate(signTime.getTime(), signDate));
                }
                insert.setInt(4, fdWorkType);
                insert.setString(5, categoryId);
                insert.setString(6, orgId);
                insert.setInt(7, 0);
                insert.setString(8, orgHId);
                insert.setInt(9, 0);
                insert.setString(10, fdWorkTimeId);
                if (Integer.valueOf(AttendConstant.FD_OVERTIME_TYPE[2])
                        .equals(nextOverTimeType)) {
                    insert.setTimestamp(11, getSignDate(signTime.getTime(), nextDate));
                    insert.setInt(12, 1);
                } else {
                    insert.setTimestamp(11, getSignDate(signTime.getTime(), signDate));
                    insert.setInt(12, 0);
                }
                insert.setString(13, "abcd");
//                insert.setString(13, areaMap.get(orgId));
                insert.addBatch();
                loop++;
                userIdList.add(orgId);
            }
            StackTraceElement[] stackElements = new Throwable().getStackTrace();
            if(stackElements != null)
            {
                for(int i = 0; i < stackElements.length; i++)
                {
                    logger.info(""+ stackElements[i]); 
                }
            }
            insert.executeBatch();
            conn.commit();
            if (jobContext != null) {
                jobContext.logMessage("补卡成功插入缺卡信息，ids:" + userIdList);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage(), ex);
            conn.rollback();
            if (jobContext != null) {
                jobContext.logMessage("补卡失败插入缺卡信息，ids:" + userIdList);
            }
            throw ex;
        } finally {
            JdbcUtils.closeStatement(insert);
            JdbcUtils.closeConnection(conn);
        }
    }

    /**
     * @param operType
     * @description: 查询考勤统计日志，根据类型获取最后的统计时间
     * @return: java.util.Date
     * @author: wangjf
     * @time: 2022/3/2 11:25 上午
     */
    private Date getMaxStatDate(String operType,String fdCategoryId) {

        try {
            if(StringUtil.isNull(fdCategoryId)) {
                Query query = getBaseDao().getHibernateSession()
                        .createQuery(
                                "select max(log.fdCreateTime) from com.landray.kmss.sys.attend.model.SysAttendStatLog log where log.fdOperType=:fdOperType")
                        .setParameter("fdOperType", operType);
                Date result = (Date) query.uniqueResult();
                if (result != null) {
                    return result;
                }
            }else{
                Query query = getBaseDao().getHibernateSession()
                        .createQuery(
                                "select max(log.fdCreateTime) from com.landray.kmss.sys.attend.model.SysAttendStatLog log where log.fdOperType=:fdOperType and log.fdCategoryId =:fdCategoryId")
                        .setParameter("fdOperType", operType)
                        .setParameter("fdCategoryId",fdCategoryId);
                Date result = (Date) query.uniqueResult();
                if (result != null) {
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否为跨天的考勤组，最重要的判定条件最晚打卡-次日，只有签到服务中的最晚打卡时间是次日才会判定是跨天考勤
     *
     * @param category
     * @param date
     * @return
     */
    private boolean isAcrossCate(SysAttendCategory category, Date date) {
        //班制类型。0固定班制，1排版，2自定义
        Integer fdShiftType = category.getFdShiftType();
        //一周内相同工作时间。0相同，1不相同
        Integer fdSameWorktime = category.getFdSameWorkTime();
        //固定周期和自定义班制
        if (fdShiftType == null || fdShiftType == 0 || fdShiftType == 2) {
            if (fdSameWorktime == null || fdSameWorktime == 0) {
                // 判定最晚打卡时间是否是次日
                if (Integer.valueOf(2).equals(category.getFdEndDay())) {
                    return true;
                }
            } else if (fdSameWorktime == 1) {
                SysAttendCategoryTimesheet tSheet = getTimeSheet(category, date);
                // 判定最晚打卡时间是否是次日
                if (tSheet != null && Integer.valueOf(2).equals(tSheet.getFdEndDay())) {
                    return true;
                }
            }
        }
        // 排班制
        else if (Integer.valueOf(1).equals(fdShiftType)) {
            // 判定最晚打卡时间是否是次日
            if (Integer.valueOf(2).equals(category.getFdEndDay())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 固定和自定义的人员生成缺卡
     * @param category     考勤分组
     * @param date         时间
     * @param _orgIdList   用户id列表
     * @param _orgInfoList 用户信息列表
     * @param jobContext   定时任务上下文
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2022/3/1 5:03 下午
     */
    private Set<String> genAttendMain(SysAttendCategory category, Date date, List<String> _orgIdList,
                               List<HashMap<String, String>> _orgInfoList, SysQuartzJobContext jobContext) throws Exception {
        Set<String> missPersonIds=new HashSet<>();
        // 班次
        JSONArray works = getWorkTime(category, date);
        if(category.getFdShiftType() == 3 || category.getFdShiftType() == 4){
        //补偿综合工时数据异常没有 工作班次情况
        Map workTime1 = (Map) works.get(0);
        Map workTime2 = (Map) works.get(1);
        Map<String, List<JSONObject>> originalDingClockInRecords = getAttendDingSignedList(
                _orgIdList, AttendUtil.getDate(date, 0),"resetAll");
        Set<Map.Entry<String, List<JSONObject>>> entries = originalDingClockInRecords.entrySet();
        for (Map.Entry<String, List<JSONObject>> entry : entries) {
            List<JSONObject> dingList = entry.getValue();
            if(dingList!=null && dingList.size() >0){
                Float fdTotalTime = category.getFdTotalTime();
                JSONObject objDing = dingList.get(0);
                long fdUserCheckTime = objDing.getLong("fdUserCheckTime");
                Date tempCreateTime =AttendUtil.removeSecond(new Date(fdUserCheckTime));
                workTime1.put("signTime",tempCreateTime.getTime() - (1000 * 60 * 1));
                long totle = (long)(fdTotalTime * 1000 * 60 * 60);
                Date tempCreateTime2 =AttendUtil.removeSecond(new Date(fdUserCheckTime+totle));
                //判断是否跨天
                int Time1 = tempCreateTime.getHours() * 60 + tempCreateTime.getMinutes();
                int Time2 = tempCreateTime2.getHours() * 60 + tempCreateTime2.getMinutes();
                if(Time1>Time2){
                    workTime2.put("signTime",category.getFdEndTime().getTime());
                }else{
                    workTime2.put("signTime",tempCreateTime2.getTime());
                }
                break;
            }
        }
        }
        if (works.isEmpty()) {
            if (jobContext != null) {
                jobContext.logMessage("考勤组id:" + category.getFdId() + ",日期:" + DateUtil.convertDateToString(date, DATE_FORMAT) + " 无法找到班次信息");
            }
            return null;
        }
        //是否是跨天考勤组
        boolean isAcrossDay = isAcrossCate(category, date);
        // 哪些人员打卡
        String where = " doc_create_time>=:beginTime and doc_create_time<:endTime and fd_work_id=:fdWorkId and fd_work_type=:fdWorkType and "
                + HQLUtil.buildLogicIN("doc_creator_id", _orgIdList);
        if (isAcrossDay) {
            where = " fd_work_id=:fdWorkId and fd_work_type=:fdWorkType and "
                    + HQLUtil.buildLogicIN("doc_creator_id", _orgIdList)
                    + " and ((doc_create_time>=:beginTime and doc_create_time<:endTime and (fd_is_across is null or fd_is_across=:fdIsAcross0))"
                    + " or fd_is_across=:fdIsAcross1 and doc_create_time>=:nextBegin and doc_create_time<:nextEnd)";
        }
        where = where + " and (doc_status=0 or doc_status is null) ";
        String signedSql = "select distinct doc_creator_id from sys_attend_main where " + where;

        for (int k = 0; k < works.size(); k++) {
            JSONObject json = (JSONObject) works.get(k);
            String fdWorkId = json.getString("fdWorkId");
            Integer fdWorkType = json.getInt("fdWorkType");
            Long signTime = json.getLong("signTime");
            // 当前班次未打卡人员
            List<HashMap<String, String>> _tmpOrgInfoList = new ArrayList<HashMap<String, String>>();
            _tmpOrgInfoList.addAll(_orgInfoList);

            // 查询班次已打卡人员
            List signedList = new ArrayList();
            if (isAcrossDay) {
                signedList = getBaseDao().getHibernateSession().createNativeQuery(signedSql).setBoolean("fdIsAcross0", false)
                        .setBoolean("fdIsAcross1", true)
                        //.setString("fdCategoryId", category.getFdId())
                        .setDate("beginTime", AttendUtil.getDate(date, 0)).setDate("endTime", AttendUtil.getDate(date, 1))
                        .setDate("nextBegin", AttendUtil.getDate(date, 1)).setDate("nextEnd", AttendUtil.getDate(date, 2))
                        .setString("fdWorkId", fdWorkId).setInteger("fdWorkType", fdWorkType).list();
            } else {
                signedList = getBaseDao().getHibernateSession().createNativeQuery(signedSql)
                        //.setString("fdCategoryId", category.getFdId())
                        .setDate("beginTime", AttendUtil.getDate(date, 0)).setDate("endTime", AttendUtil.getDate(date, 1))
                        .setString("fdWorkId", fdWorkId).setInteger("fdWorkType", fdWorkType).list();
            }
            if (!signedList.isEmpty()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("SysAttendMainJob 已打卡人员：" + signedList);
                }
                for (Object obj : signedList) {
                    for (HashMap<String, String> m : _orgInfoList) {
                        if (m.get("orgId").equals((String) obj)) {
                            _tmpOrgInfoList.remove(m);
                        }
                    }
                }
            }
            if (_tmpOrgInfoList.isEmpty()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("SysAttendMainJob 未打卡人员为空 signTime:" + signTime);
                }
                continue;
            }
            //未打卡人员ID
            List<String> orgIdsList = getUserIdList2(_tmpOrgInfoList);
            if (logger.isDebugEnabled()) {
                logger.debug("SysAttendMainJob 未打卡人员:" + orgIdsList);
            }
            if (jobContext != null) {
                String str=Integer.valueOf(0).equals(fdWorkType)?"上班":"下班";
                jobContext.logMessage("考勤组id：" + category.getFdId() + "时间：" + DateUtil.convertDateToString(date, DATE_FORMAT) + ",班次id:" + fdWorkId +"("+str+"）,未打卡人员:" + orgIdsList);
            }
            List<String> insertOrgList = addBatch(category.getFdId(), fdWorkId, fdWorkType, _tmpOrgInfoList, signTime, date, SysTimeUtil.getUserAuthAreaMap(orgIdsList), jobContext);
            missPersonIds.addAll(insertOrgList);
        }
        return missPersonIds;
    }

    /**
     * @param list
     * @description: 获取用户IDList
     * @return: java.util.List<java.lang.String>
     * @author: wangjf
     * @time: 2022/3/2 11:26 上午
     */
    private List<String> getUserIdList(List<Map<String, Object>> list) {
        List<String> resultList = new ArrayList<String>();
        for (Map<String, Object> m : list) {
            resultList.add((String) m.get("orgId"));
        }
        return resultList;
    }

    /**
     * @param list
     * @description: 获取用户IDList
     * @return: java.util.List<java.lang.String>
     * @author: wangjf
     * @time: 2022/3/2 11:26 上午
     */
    private List<String> getUserIdList2(List<HashMap<String, String>> list) {
        List<String> resultList = new ArrayList<String>();
        for (HashMap<String, String> m : list) {
            resultList.add(m.get("orgId"));
        }
        return resultList;
    }

    /**
     * 根据人员的班次，生成人员对应班次信息的缺卡
     * @param date     时间
     * @param orgIds   对应的用户
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2022/3/1 3:59 下午
     */
    private Set<String> genAttendMainOfTimeArea(Date date, List<String> orgIds, SysQuartzJobContext jobContext) throws Exception {
        Set<String> missPersonIds=new HashSet<>();
        // 用户打卡记录
        Map<String, List<Map<String, Object>>> recordList = findAttendList(orgIds, date);
        List<SysOrgElement> orgList = AttendPersonUtil.getSysOrgElementById(orgIds);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (SysOrgElement ele : orgList) {
            // 获取指定用户某天的打卡时间点
            List<Map<String, Object>> signTimeList = this.getSysAttendCategoryService().getAttendSignTimes(ele,date);
            if (!signTimeList.isEmpty()) {
                Map<String, Object> tmpMap = signTimeList.get(0);
                if (!tmpMap.containsKey("fdWorkTimeId")) {
                    if (jobContext != null) {
                        jobContext.logMessage("用户id:" + ele.getFdId() + ",无法找到相应排班信息,忽略补卡操作,日期：" + DateUtil.convertDateToString(date, DATE_FORMAT));
                    }
                    logger.warn("该用户{}无法找到相应排班信息,忽略补卡操作!日期:{}", ele.getFdId(), DateUtil.convertDateToString(date, DATE_FORMAT));
                    continue;
                }
            }
            // 用户的打卡信息
            List<Map<String, Object>> userRecordList = recordList.get(ele.getFdId());
            if (userRecordList == null) {
                userRecordList = new ArrayList<Map<String, Object>>();
            }
            //转换用户的有效数据跟班次对应关系
            this.getSysAttendCategoryService().doWorkTimesRender(signTimeList, userRecordList);
            for (Map<String, Object> signTime : signTimeList) {
                boolean signed = false;
                for (Map<String, Object> record : userRecordList) {
                    //判断数据是否符合signTime打卡规则，如果考勤数据是符合规则则不需要补卡，否则需要补卡
                    if (isSameWorkTime(signTime, (String) record.get("fdWorkId"), (Integer) record.get("fdWorkType"), (String) record.get("fdWorkKey"))) {
                        signed = true;
                        break;
                    }
                }
                if (!signed) {
                    // 需补卡
                    signTime.put("orgHId", ele.getFdHierarchyId());
                    signTime.put("orgId", ele.getFdId());
                    signTime.put("signDate", date);
                    resultList.add(signTime);
                }
            }
        }
        List<String> orgIdsList = getUserIdList(resultList);
        if (jobContext != null) {
            jobContext.logMessage("进入补卡入库阶段开始，补卡日期" + DateUtil.convertDateToString(date, DATE_FORMAT) + "补卡人员id:" + orgIdsList);
        }
        if(CollectionUtils.isNotEmpty(resultList)) {
            addBatch(resultList, SysTimeUtil.getUserAuthAreaMap(orgIdsList), jobContext);
            missPersonIds.addAll(orgIdsList);
        }else{
            if(jobContext !=null) {
                jobContext.logMessage("没有需要产生的补卡记录!");
            }
        }
        if (jobContext != null) {
            jobContext.logMessage("进入补卡入库阶段结束，补卡日期" + DateUtil.convertDateToString(date, DATE_FORMAT));
        }
        return missPersonIds;
    }

    /**
     * 判断是否是通一个班次
     *
     * @param workTimeMap 排班打卡信息
     * @param fdWorkId
     * @param fdWorkType
     * @param fdWorkKey
     * @description:
     * @return: boolean
     * @author: wangjf
     * @time: 2022/3/1 4:23 下午
     */
    private boolean isSameWorkTime(Map<String, Object> workTimeMap, String fdWorkId, Integer fdWorkType, String fdWorkKey) {
        String workTimeId = (String) workTimeMap.get("fdWorkTimeId");
        Integer workType = (Integer) workTimeMap.get("fdWorkType");
        Integer shiftType = (Integer) workTimeMap.get("fdShiftType");

        if (StringUtil.isNull(workTimeId) || workType == null) {
            return false;
        }
        if (Integer.valueOf(1).equals(shiftType)) {
//            if (workTimeId.equals(fdWorkKey) && workType.equals(fdWorkType)) {
                if (workType.equals(fdWorkType)) {
                return true;
            }
            return false;
        }
        if (workTimeId.equals(fdWorkId) && workType.equals(fdWorkType)) {
            return true;
        }
        return false;
    }

    /**
     * 获取用户某天的考勤打卡记录
     *
     * @param orgIds
     * @param date
     * @return userMap <用户id，用户打卡信息Map： fdWorkId：班次id， fdWorkType：班次type，
     * fdWorkKey：班次key， docCreateTime：打卡时间， fdIsAcross:是否跨天打卡>
     * @throws Exception
     */
    private Map findAttendList(List orgIds, Date date)
            throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select fd_work_id,fd_work_type,doc_creator_id,doc_create_time,fd_work_key,fd_is_across")
                .append(" from sys_attend_main m ")
                .append(" where (doc_status=0 or doc_status is null) and (fd_work_id is not null or fd_work_key is not null) "
                        + " and (doc_create_time>=:beginTime and doc_create_time<:endTime and (fd_is_across is null or fd_is_across= 0)"
                        + " or fd_is_across=1 and doc_create_time>=:nextBegin and doc_create_time<:nextEnd)");
        sql.append(" and " + HQLUtil.buildLogicIN("doc_creator_id", orgIds));
        Query nativeQuery = getBaseDao().getHibernateSession().createSQLQuery(sql.toString());
        nativeQuery.setParameter("beginTime", AttendUtil.getDate(date, 0));
        nativeQuery.setParameter("endTime", AttendUtil.getDate(date, 1));
        nativeQuery.setParameter("nextBegin", AttendUtil.getDate(date, 1));// 跨天加班的数据
        nativeQuery.setParameter("nextEnd", AttendUtil.getDate(date, 2));
       // nativeQuery.setParameter("fdIsAcross0", false);
        //nativeQuery.setParameter("fdIsAcross1", true);
        List list = nativeQuery.list();

        Map<String, List<Map<String, Object>>> userMap = new HashMap<String, List<Map<String, Object>>>();
        for (int i = 0; i < list.size(); i++) {
            Object[] record = (Object[]) list.get(i);
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("fdWorkId", (String) record[0]);
            Number fdWorkType = (Number) record[1];
            String docCreatorId = (String) record[2];
            if (!userMap.containsKey(docCreatorId)) {
                userMap.put(docCreatorId, new ArrayList<Map<String, Object>>());
            }
            m.put("fdWorkType", fdWorkType == null ? null : fdWorkType.intValue());
            Object docCreateTime =record[3];
            if(docCreateTime ==null){
                m.put("docCreateTime",null);
            }else {
                if (docCreateTime instanceof Timestamp) {
                    m.put("docCreateTime", (Timestamp) record[3]);
                } else {
                    m.put("docCreateTime", new Timestamp(DateUtil.convertStringToDate(String.valueOf(record[3])).getTime()));
                }
            }
            m.put("fdWorkKey", (String) record[4]);
            m.put("fdIsAcross", AttendUtil.getBooleanField(record[5]));
            List<Map<String, Object>> userRecords = userMap.get(docCreatorId);
            userRecords.add(m);
        }
        return userMap;
    }

    /**
     * @param signTime
     * @param statDate
     * @description: 获取打卡时间年月日与统计时间分钟和小时
     * @return: java.sql.Timestamp
     * @author: wangjf
     * @time: 2022/3/2 11:28 上午
     */
    private Timestamp getSignDate(long signTime, Date statDate) {
        Date _signTime = new Date(signTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(statDate);
        cal.set(Calendar.HOUR_OF_DAY, _signTime.getHours());
        cal.set(Calendar.MINUTE, _signTime.getMinutes());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new Timestamp(cal.getTime().getTime());
    }
    /**
     * 	获取用户某天钉钉打卡原始记录
     *
     * @param orgIds
     * 	用户Ids
     * @param workedDate
     * 	某天
     * @return Map
     * 	钉钉、微信原始打卡数据
     * @throws Exception
     */
    private Map getAttendDingSignedList(List<String> orgIds, Date workedDate,String typeName)
            throws Exception {
        DataSource dataSource = (DataSource) SpringBeanUtil
                .getBean("dataSource");

        StringBuilder signedSql =new StringBuilder("select fd_work_date,fd_user_id,fd_check_type,fd_source_type,fd_time_result,fd_user_address,fd_base_check_time,"
                + "fd_user_check_time,fd_person_id,fd_location_result,fd_invalid_record_type,fd_id,fd_wifi_name,fd_user_mac_addr,fd_outside_remark,fd_app_name from sys_attend_syn_ding where "
                +  " fd_user_check_time>=? and fd_user_check_time<? ");
        //排除 请假\出差\外出流程 的原始记录，或者是打卡的来源非空
        if("exchange".equals(typeName)) {
            signedSql.append(" and (fd_time_result not in ('Trip','Leave','Outgoing') or fd_time_result is null )");
        } else if("resetAll".equals(typeName)) {
            signedSql.append(" and fd_app_name is not null ");
        } else {
            signedSql.append(" and fd_ding_id is not null ");
        }
        Map<String, List<JSONObject>> records = new HashMap<String, List<JSONObject>>();
        if(CollectionUtils.isNotEmpty(orgIds)) {
            signedSql.append(" and ").append(HQLUtil.buildLogicIN("fd_person_id", orgIds));
        }else{
            //没有人员的情况下，不查询，避免数据量过大，系统宕机
            return records;
        }
        signedSql.append("  order by fd_user_check_time asc ");
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<String> invalidUserList = new ArrayList<String>();
        try {
            conn = dataSource.getConnection();
            statement = conn.prepareStatement(signedSql.toString());
            statement.setTimestamp(1, new Timestamp(AttendUtil.getDate(workedDate, 0).getTime()));
            statement.setTimestamp(2, new Timestamp(AttendUtil.getDate(workedDate, 2).getTime()));
            rs = statement.executeQuery();
            while (rs.next()) {
                JSONObject ret = new JSONObject();
                Timestamp fdWorkDate = rs.getTimestamp(1);
                ret.put("fdWorkDate",
                        fdWorkDate != null ? fdWorkDate.getTime() : 0);
                ret.put("fdUserId", rs.getString(2));
                ret.put("fdCheckType", rs.getString(3));
                ret.put("fdSourceType", rs.getString(4));
                ret.put("fdTimeResult", rs.getString(5));
                ret.put("fdUserAddress", rs.getString(6));
                Timestamp fdBaseCheckTime = rs.getTimestamp(7);
                Timestamp fdUserCheckTime = rs.getTimestamp(8);
                ret.put("fdBaseCheckTime",
                        fdBaseCheckTime != null ? fdBaseCheckTime.getTime()
                                : 0);
                ret.put("fdUserCheckTime",
                        fdUserCheckTime != null ? fdUserCheckTime.getTime()
                                : 0);
                String fdPersonId = rs.getString(9);
                ret.put("fdPersonId", fdPersonId);
                ret.put("fdLocationResult", rs.getString(10));
                if (!records.containsKey(fdPersonId)) {
                    records.put(fdPersonId, new ArrayList<JSONObject>());
                }
                // 打卡记录是否有效
                String fdInvalidRecordType = rs.getString(11);
                if ("Security".equals(fdInvalidRecordType)
                        || "Other".equals(fdInvalidRecordType)) {
                    invalidUserList.add(fdPersonId);
                    continue;
                }
                ret.put("fdWifiName", rs.getString(13));
                ret.put("fdUserMacAddr", rs.getString(14));
                ret.put("fdOutsideRemark", rs.getString(15));
                ret.put("fdAppName", rs.getString(16));
                List<JSONObject> recordList = records.get(fdPersonId);
                recordList.add(ret);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(conn);
        }
        if (!invalidUserList.isEmpty()) {
            logger.warn("考勤打卡记录存在安全问题导致打卡数据无效,包括如下用户ID:" + invalidUserList);
        }
        return records;
    }

}
