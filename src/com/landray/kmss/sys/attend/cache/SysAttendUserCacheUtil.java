package com.landray.kmss.sys.attend.cache;

import com.landray.kmss.common.concurrent.KMSSCommonThreadUtil;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 考勤组人员关系缓存维护工具类
 * 处理内容全部异步处理
 * @author 王京
 * @date 2022-0613
 */
public class SysAttendUserCacheUtil {

    private static String USER_CATEGORY_KEY="sys_attend_user_category_";

    public static String getUserCategoryKey(String personId){
        return String.format("%s%s",USER_CATEGORY_KEY,personId);
    }
    /**
     * 更新组织下所有人某个时间区间的缓存
     * @param elements
     * @param date
     */
    public static void clearUserCache(List<String> elements, Date date) throws Exception {
        if(CollectionUtils.isEmpty(elements)){
            return;
        }
        SysAttendUserCacheRemoveTask task=new SysAttendUserCacheRemoveTask(elements,date);
        KMSSCommonThreadUtil.execute(task);
    }

    /**
     * 异步更新考勤组人员关系
     * 清理缓存关系
     * @date 2022-0613
     */
    static class SysAttendUserCacheRemoveTask implements Runnable{

        private  final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendUserCacheRemoveTask.class);

        /**
         * 需要处理的组织架构列表
         */
        private List<String> elements;

        /**
         * 需要处理的日期
         */
        private Date date;

        public SysAttendUserCacheRemoveTask(List<String> elements, Date date) {
            this.elements = elements;
            this.date = date;
        }

        @Override
        public void run() {
            TransactionStatus status=null;
            boolean isException =false;
            try {
                status =TransactionUtils.beginNewReadTransaction();
                //解析组织下对应的所有人员
                List<String> personIds = AttendPersonUtil.expandToPersonIds(elements);
                if(CollectionUtils.isEmpty(personIds)){
                    return;
                }
                //删除所有人员的某个日期所在区间的 考勤组缓存
                //如果date为null 则删除该人员所有的日期区间缓存;
                for (String personId:personIds) {
                    String key =SysAttendUserCacheUtil.getUserCategoryKey(personId);
                    SysAttendUserCategoryListDto userCategoryListCatche = (SysAttendUserCategoryListDto) CategoryUtil.USER_CATEGORY_CACHE_MAP.get(key);
                    if(userCategoryListCatche !=null){
                        userCategoryListCatche.remove(date);
                        //如果存在考勤组区间 则重新赋值，不存在则清理该缓存
                        if(CollectionUtils.isNotEmpty(userCategoryListCatche.getCategoryDtoList())) {
                            CategoryUtil.USER_CATEGORY_CACHE_MAP.put(key, userCategoryListCatche);
                        }else{
                            CategoryUtil.USER_CATEGORY_CACHE_MAP.remove(key);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("考勤组人员关系缓存维护工具类，清理缓存信息异常；{}",e.getMessage());
                isException =true;
            }finally {
                if(status !=null && isException){
                    TransactionUtils.rollback(status);
                }
                else if(status !=null){
                    TransactionUtils.commit(status);
                }
            }
        }
    }

    /**
     * 删除不在当前考勤组中的人员
     */
    public static void removeNotInCurrentCategoryId(String categoryId, List<SysOrgElement> categoryAllElement ,Date date) throws Exception {
        //多线程去获取人员的考勤组。获取人员所在考勤组的时候，会把人员考勤组关系存储于缓存中。后续从缓存中读取
        CountDownLatch latch=new CountDownLatch(categoryAllElement.size());
        for (SysOrgElement element: categoryAllElement ) {
            SysAttendGetUserCategoryTask task=new SysAttendGetUserCategoryTask(element,date,latch);
            KMSSCommonThreadUtil.execute(task);
        }
        latch.await(30, TimeUnit.MINUTES);
        //匹配不在缓存中的人员。从当前列表移除
        Iterator it = categoryAllElement.iterator();
        while (it.hasNext()){
            SysOrgElement ele = (SysOrgElement) it.next(); 
            SysAttendUserCategoryListDto userCategoryListCatche = (SysAttendUserCategoryListDto) CategoryUtil.USER_CATEGORY_CACHE_MAP.get(getUserCategoryKey(ele.getFdId()));
            if(userCategoryListCatche !=null){
                String tempCategoryId = userCategoryListCatche.get(date);
                if(StringUtil.isNotNull(tempCategoryId) && !tempCategoryId.equals(categoryId)) {
                    it.remove();
                }
            }
        }

    }

    /**
     * 获取根据日期和用户获取对应的考勤组ID
     */
    static class SysAttendGetUserCategoryTask implements Runnable{
        private  final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendGetUserCategoryTask.class);
        /**
         * 需要处理的人员对象
         */
        private SysOrgElement elements;

        /**
         * 需要处理的日期
         */
        private Date date;

        private CountDownLatch latch;

        public SysAttendGetUserCategoryTask(SysOrgElement elements, Date date,CountDownLatch latch) {
            this.elements = elements;
            this.date = date;
            this.latch=latch;
        }

        @Override
        public void run() {
            TransactionStatus status=null;
            boolean isException =false;
            try {
                status =TransactionUtils.beginNewReadTransaction();
                //因为获取人员的所在考勤组以后 会存储到缓存中。后面要使用人员所在考勤组ID，只需要读取缓存
                getSysAttendCategoryService().getCategory(elements,date);
            } catch (Exception e) {
                logger.error("获取人员所在考勤组异常；{}",e.getMessage());
                isException =true;
            }finally {
                if(latch !=null){
                    latch.countDown();
                }
                if(status !=null && isException){
                    TransactionUtils.rollback(status);
                }
                else if(status !=null){
                    TransactionUtils.commit(status);
                }
            }
        }
    }


    /**
     * 更新组织下所有人某个时间区间的缓存
     * @param elements
     * @param date
     */
    public static void updateUserCache(List<String> elements, Date date) throws Exception {
        if(CollectionUtils.isEmpty(elements)){
            return;
        }
        SysAttendUserCacheUpdateTask task=new SysAttendUserCacheUpdateTask(elements,date);
        KMSSCommonThreadUtil.execute(task);
    }

    /**
     * 异步更新考勤组人员关系
     * 清理缓存关系
     * @date 2022-0613
     */
    static class SysAttendUserCacheUpdateTask implements Runnable{

        private  final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendUserCacheRemoveTask.class);

        /**
         * 需要处理的组织架构列表
         */
        private List<String> elements;

        /**
         * 需要处理的日期
         */
        private Date date;

        public SysAttendUserCacheUpdateTask(List<String> elements, Date date) {
            this.elements = elements;
            this.date = date;
        }

        @Override
        public void run() {
            TransactionStatus status=null;
            boolean isException =false;
            try {
                status =TransactionUtils.beginNewReadTransaction();
                //解析组织下对应的所有人员
                List<SysOrgElement> categoryAllElement = AttendPersonUtil.expandToPerson(elements);
                if(CollectionUtils.isEmpty(categoryAllElement)){
                    return;
                }
                CountDownLatch latch=new CountDownLatch(categoryAllElement.size());
                for (SysOrgElement element: categoryAllElement ) {
                    SysAttendGetUserCategoryTask task=new SysAttendGetUserCategoryTask(element,date,latch);
                    KMSSCommonThreadUtil.execute(task);
                }
                latch.await(30, TimeUnit.MINUTES);
            } catch (Exception e) {
                logger.error("考勤组人员关系缓存维护工具类，更新缓存信息异常；{}",e.getMessage());
                isException =true;
            }finally {
                if(status !=null && isException){
                    TransactionUtils.rollback(status);
                }
                else if(status !=null){
                    TransactionUtils.commit(status);
                }
            }
        }
    }


    private static ISysAttendCategoryService sysAttendCategoryService;

    private static ISysAttendCategoryService getSysAttendCategoryService() {
        if (sysAttendCategoryService == null) {
            sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
        }
        return sysAttendCategoryService;
    }
}
