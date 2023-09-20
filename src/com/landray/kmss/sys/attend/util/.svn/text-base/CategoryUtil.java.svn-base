package com.landray.kmss.sys.attend.util;

import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.sys.attend.cache.SysAttendUserCategoryDto;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTargetNew;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 考勤组的工具类
 *
 * @author wj
 */
public class CategoryUtil {
    /**
     * 工作日补班的KEY
     */
    public static String HOLIDAY_CACHE_MAP_KEY = "timeHolidayPatchMap_%s";
    /**
     * 节假日的Key
     */
    public static String HOLIDAY_DAY_CACHE_MAP_KEY = "timeHolidayDayMap_%s";
    /**
     * 立即生效
     */
    public static String ENABLE_FLAG = "1";
    /**
     * 默认50个 手动清理，永不失效
     * 缓存考勤组配置信息。数据内容有变更，需要手动清理缓存。
     * key 为考勤组Id
     */
    public static KmssCache CATEGORY_CACHE_MAP = new KmssCache(SysAttendCategory.class, getConfig(SysAttendCategory.class));
    /**
     * 默认50个 手动清理，永不失效
     * 缓存历史考勤组配置信息。数据内容有变更，需要手动清理缓存。
     * key 为考勤组Id
     */
    public static KmssCache HIS_CATEGORY_CACHE_MAP = new KmssCache(SysAttendHisCategory.class, getConfig(SysAttendHisCategory.class));

    /**
     * 本机缓存配置 永不失效
     *
     * @return
     */
    private static CacheConfig getConfig(Class<?> cl) {
        CacheConfig config = CacheConfig.get(cl);
        config.maxElementsInMemory = 50;
        config.overflowToDisk = true;
//        config.setCacheType(CacheConfig.TYPE_LOCAL);
        return config;
    }

    /**
     * 默认500个 手动清理，永不失效
     * 缓存 人员跟考勤组的关系。数据内容有变更，需要手动清理缓存。
     * key 为人员id,value 为考勤组封装对象，SysAttendUserCategoryListDto 类
     */
    public static KmssCache USER_CATEGORY_CACHE_MAP = new KmssCache(SysAttendUserCategoryDto.class, getSysAttenUserCategoryConfig());

    /**
     * 默认50个 手动清理，永不失效
     * 考勤组下有多少人员
     * key 为考勤组id,value 人员的简易对象List
     */
    public static KmssCache CATEGORY_USERS_CACHE_MAP = new KmssCache(SysOrgElement.class, getConfig(SysOrgElement.class));
    /**
     * 默认50个 手动清理，永不失效
     * 考勤组下有多少人员
     * key 为考勤组id,value 人员Str列表
     */
    public static KmssCache CATEGORY_USERIDS_CACHE_MAP = new KmssCache(SysAttendCategoryTargetNew.class, getConfig(SysAttendCategoryTargetNew.class));

    /**
     * 人员跟考勤组关系的缓存配置
     * 存储在redis中
     *
     * @return
     */
    private static CacheConfig getSysAttenUserCategoryConfig() {
        CacheConfig config = CacheConfig.get(SysAttendUserCategoryDto.class);
        config.maxElementsInMemory = 500;
        config.overflowToDisk = true;

//        long liveSeconds = 60 * 60 * 24 * 4;
//        config.overflowToDisk = true;
//        config.timeToLiveSeconds = liveSeconds;
//        config.timeToIdleSeconds = liveSeconds;
//        config.eternal = false;

//        config.setCacheType(CacheConfig.TYPE_REDIS);
        return config;
    }

    /**
     * 人员跟排班的缓存
     * 默认200存储于内存，有效期20小时
     * 数据有变更，需要清除。
     */
    public static KmssCache USER_WORKTIME_CACHE_MAP = new KmssCache(SysAttendCategoryWorktime.class, getSysAttenUserWorkConfig());

    private static CacheConfig getSysAttenUserWorkConfig() {
        //人员的排班打卡信息、缓存配置。有效期20小时
        long liveSeconds = 60 * 60 * 20;
        CacheConfig config = CacheConfig.get(SysAttendCategoryWorktime.class);
        config.maxElementsInMemory = 200;
        config.overflowToDisk = true;
        config.timeToLiveSeconds = liveSeconds;
        config.timeToIdleSeconds = liveSeconds;
        config.eternal = false;
//        config.setCacheType(CacheConfig.TYPE_REDIS);
        return config;
    }


    /**
     * 考勤组类型
     */
    public static Integer CATEGORY_FD_TYPE_TRUE = 1;
    /**
     * 签到组
     */
    public static Integer CATEGORY_FD_TYPE_SIGN = 2;
    /**
     * 考勤组进行中状态
     */
    public static Integer CATEGORY_FD_STATUS_TRUE = 1;


    /**
     * 排除对象
     */
    @Deprecated
    public static String[] EXCLUDE_PROPERTIES = {
            "fdRange", "fdHideRange", "fdExternal",
            "customPropList", "customPropMap", "dynamicMap", "mechanismMap", "toFormPropertyMap",
            "authAllEditors", "authAllReaders", "authArea", "authAttCopys", "authAttDownloads", "authAttNocopy", "authAttNodownload",
            "authAttNoprint", "authAttPrints", "authChangeAtt", "authEditors", "authOtherEditors", "fdATemplate", "authOtherReaders", "authReaders"
            , "hbmChildren", "hbmGroups", "hbmParent", "hbmParent", "hbmPersons", "hbmPosts", "hbmSuperLeader", "hbmSuperLeaderChildren", "hbmThisLeader", "hbmThisLeaderChildren"
            , "sysDictModel", "fdChildren", "authElementAdmins", "allMyLeader", "allLeader", "fdParent", "fdParentOrg",
            "fdPersons", "fdPosts", "hbmParentOrg", "fdViews", "fdGroups", "fdCategory", "docAlteror", "docCreator", "fdTemplate", "fdHolidayDetailList",
            "fdStaffingLevel", "addressTypeList", "fdThisLeader", "fdSuperLeader", "hbmRange", "fdHiderange", "fdElement", "fdOthers",
            "fdManager", "fdTargets", "fdExcTargets"
    };

    /**
     * 考勤组有效期最大时间
     *
     * @return 默认返回2099年。反正我是活不到这么久。
     */
    public static Date getMaxDate() {
        Calendar date = Calendar.getInstance();
        date.set(2099, 11, 31, 0, 0, 0);
        return date.getTime();
    }

    /**
     * 获取考勤组信息
     *
     * @param main
     * @return
     * @throws Exception
     */
    public static SysAttendCategory getFdCategoryInfo(SysAttendMain main) throws Exception {
        SysAttendCategory cate = getSysAttendHisCategoryService().convertCategory(main.getFdHisCategory());
        if (cate == null) {
            //暂时默认是考勤组、后续签到组在考虑
            cate = getSysAttendHisCategoryService().getCategoryByUserAndDate(main.getDocCreator(), main.getDocCreateTime(), CATEGORY_FD_TYPE_TRUE);
        }
        return cate;
    }

    /**
     * 根本考勤组历史版本的ID获取对应的考勤组信息
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    public static SysAttendCategory getCategoryById(String categoryId) throws Exception {

        return getSysAttendHisCategoryService().getCategoryById(categoryId);
    }

    /**
     * 根据原始考勤组的ID获取对应日期的历史的考勤组
     *
     * @param categoryId 原始考勤组
     * @return
     * @throws Exception
     */
    public static SysAttendCategory getLastVersionCategoryFdId(String categoryId, Date date) throws Exception {

        String hisCategoryId = getLastVersionFdId(categoryId, date);
        if (StringUtil.isNotNull(hisCategoryId)) {
            return getCategoryById(hisCategoryId);
        }
        return null;
    }

    /**
     * 根据原始考勤组ID和日期 或者相关的考勤组的id
     *
     * @param categoryId
     * @return
     */
    public static String getLastVersionFdId(String categoryId, Date fdBeginTime) throws Exception {
        SysAttendHisCategory hisCategory = getSysAttendHisCategoryService().getLastVersionFdId(categoryId, fdBeginTime);
        if (hisCategory != null) {
            return hisCategory.getFdId();
        }
        return null;
    }

    /**
     * 根据原始考勤组ID获取所有的历史版本ID
     *
     * @param oldCategoryId 原始考勤组ID
     * @return
     * @throws Exception
     */
    public static Set<String> getAllCategorys(List<String> oldCategoryId) throws Exception {
        return getSysAttendHisCategoryService().getAllCategorys(oldCategoryId);
    }

    /**
     * 根据原始考勤组ID 和 对应日期 获取相关的组织架构所有人员。
     * 不过滤排除人员。
     * 该方法主要用于查询的参数中
     *
     * @param oldCategoryId
     * @param beginDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static Set<String> getOldCategoryUsers(String oldCategoryId, Date beginDate, Date endDate) throws Exception {
        return getSysAttendHisCategoryService().getCategoryUsers(oldCategoryId, beginDate, endDate);
    }


    /**
     * 根本考勤组历史版本的ID获取对应的考勤组信息
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    public static SysAttendHisCategory getHisCategoryById(String categoryId) throws Exception {
        SysAttendHisCategory hisCategory = (SysAttendHisCategory) HIS_CATEGORY_CACHE_MAP.get(categoryId);
        if (hisCategory == null) {
            if (getSysAttendHisCategoryService().getBaseDao().isExist(SysAttendHisCategory.class.getName(), categoryId)) {
                hisCategory = (SysAttendHisCategory) getSysAttendHisCategoryService().findByPrimaryKey(categoryId);
                HIS_CATEGORY_CACHE_MAP.put(categoryId, hisCategory);
            }
        }
        return hisCategory;
    }

    /**
     * 判断人员是否属于全局例外人员
     * @param orgMap
     * @param element
     * @return
     */
    public static boolean isGlobalExcTarget(Map orgMap, SysOrgElement element) {
        if (orgMap.isEmpty()) {
            return false;
        }
        if (orgMap.get(element.getFdId()) != null) {
            return true;
        } else {
            String[] ids = element.getFdHierarchyId().split(
                    BaseTreeConstant.HIERARCHY_ID_SPLIT);
            for (int i = ids.length - 2; i > 0; i--) {
                if (orgMap.containsKey(ids[i])) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 获取全局排除人员
     * @return
     * @throws Exception
     */
    public static Map<String,String> getGlobalExcTargetMap() throws Exception {
        KmssCache cache = new KmssCache(SysAttendConfig.class);
        Map orgGlobalMap = (Map) cache.get("orgGlobalExcTargetMap");
        if (orgGlobalMap == null) {
            HashMap result = new HashMap();
            List paramList = getSysAttendConfigService().findList("", "");
            if (paramList != null && !paramList.isEmpty()) {
                SysAttendConfig config = (SysAttendConfig) paramList.get(0);
                if (StringUtil.isNotNull(config.getFdExcTargetIds())) {
                    String[] ids = config.getFdExcTargetIds().split(";");
                    List<String> idList = Arrays.asList(ids);
                    List<String> orgList = AttendPersonUtil.expandToPersonIds(idList);
                    for (int j = 0; j < orgList.size(); j++) {
                        result.put(orgList.get(j), orgList.get(j));
                    }
                }
            }
            cache.put("orgGlobalExcTargetMap", result);
            orgGlobalMap = result;
        }
        return orgGlobalMap;
    }

    private static ISysAttendHisCategoryService sysAttendHisCategoryService;

    /**
     * 历史考勤组服务对象
     * @return
     */
    private static ISysAttendHisCategoryService getSysAttendHisCategoryService() {
        if (sysAttendHisCategoryService == null) {
            sysAttendHisCategoryService = (ISysAttendHisCategoryService) SpringBeanUtil.getBean("sysAttendHisCategoryService");
        }
        return sysAttendHisCategoryService;
    }

    private static ISysAttendConfigService sysAttendConfigService;
    private static ISysAttendConfigService getSysAttendConfigService() {
        if (sysAttendConfigService == null) {
            sysAttendConfigService = (ISysAttendConfigService) SpringBeanUtil.getBean("sysAttendConfigService");
        }
        return sysAttendConfigService;
    }
}
