package com.landray.kmss.sys.attend.service.spring;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.attend.cache.SysAttendUserCacheUtil;
import com.landray.kmss.sys.attend.cache.SysAttendUserCategoryListDto;
import com.landray.kmss.sys.attend.dao.ISysAttendCategoryContentDao;
import com.landray.kmss.sys.attend.dao.ISysAttendCategoryExcNewDao;
import com.landray.kmss.sys.attend.dao.ISysAttendCategoryTargetChangeDao;
import com.landray.kmss.sys.attend.dao.ISysAttendCategoryTargetNewDao;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryForm;
import com.landray.kmss.sys.attend.forms.SysAttendHisCategoryForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryExcNew;
import com.landray.kmss.sys.attend.model.SysAttendCategoryLocation;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTargetChange;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTargetNew;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendHisCategoryContent;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.sys.attend.util.AttendThreadPoolManager;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.event.SysOrgElementChangeEvent;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.sunbor.web.tag.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 考勤组的变更记录
 * @author wj
 */
public class SysAttendHisCategoryServiceImp extends ExtendDataServiceImp implements ISysAttendHisCategoryService, ApplicationListener<SysOrgElementChangeEvent>, IEventMulticasterAware {
    private final Logger logger = LoggerFactory.getLogger(SysAttendHisCategoryServiceImp.class);
    private ISysAttendCategoryTargetNewDao sysAttendCategoryTargetNewDao;
    private ISysAttendCategoryTargetChangeDao sysAttendCategoryTargetChangeDao;
    private IEventMulticaster multicaster;
    @Override
    public void setEventMulticaster(IEventMulticaster multicaster) {
        this.multicaster = multicaster;
    }
    private ISysAttendCategoryContentDao sysAttendCategoryContentDao;
    private ISysAttendCategoryContentDao getSysAttendCategoryContentDao(){
        if(sysAttendCategoryContentDao ==null){
            sysAttendCategoryContentDao = (ISysAttendCategoryContentDao) SpringBeanUtil.getBean("sysAttendCategoryContentDao");
        }
        return sysAttendCategoryContentDao;
    }

    private ISysAttendCategoryExcNewDao sysAttendCategoryExcNewDao;
    private ISysAttendCategoryService sysAttendCategoryService;
    private ISysOrgElementService sysOrgElementService;

    public ISysOrgElementService getSysOrgElementService() {
        if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

    /**
     * 验证考勤组中的考勤对象是否在其他考勤组中存在
     * @param category
     * @return
     */
    @Override
    public String checkCategoryTarget(SysAttendCategory category) throws Exception {
        List<SysOrgElement> targets = category.getFdTargets();

        if (CollectionUtils.isNotEmpty(targets)) {
            List<String> tipList=new ArrayList<>();
            checkSysOrg(targets,tipList,category);
            if(CollectionUtils.isNotEmpty(tipList)){
               return Joiner.on(";").join(tipList);
            }
        }
        return null;
    }

    /**
     * 根据组织对象列表 获取其是否在其他的考勤组中存在
     * @param targets 考勤组对象
     * @param tipList 提示列表
     * @param category 考勤组
     * @throws Exception
     */
    private void checkSysOrg(List<SysOrgElement> targets,List<String> tipList,SysAttendCategory category) throws Exception {
        for (SysOrgElement element : targets) {
            String id=element.getFdId();
            //排除对象
            List<SysOrgElement> excTargets =category.getFdExcTargets();
            if(CollectionUtils.isNotEmpty(excTargets)) {
                boolean isHave= false;
                //先过滤是否在本次排除的范围内，如果不是再进行数据库中排查
                for (SysOrgElement extTarget:excTargets) {
                    if(id.equals(extTarget.getFdId())){
                        isHave =true;
                        break;
                    }
                }
                if(isHave){
                    continue;
                }
            }
            Date now=new Date();
            //当前时间的0时0分0秒
            Date beginDate= AttendUtil.getDate(now,1);
            if(CategoryUtil.ENABLE_FLAG.equals(category.getFdStatusFlag()) || category.getFdStatusFlag() ==null){
                //立即生效
                beginDate= AttendUtil.getDate(now,0);
            }
            //判断是否在其他考勤组范围内
            SysAttendHisCategory hisCategory = getCategoryTarget(element.getFdId(),beginDate,CategoryUtil.CATEGORY_FD_TYPE_TRUE);
            if(hisCategory !=null && !category.getFdId().equals(hisCategory.getFdCategoryId())){
                //判断是否在该考勤组的排除范围内
                SysAttendHisCategory excHisCategory = getCategoryExc(id, hisCategory.getFdId(), beginDate,CategoryUtil.CATEGORY_FD_TYPE_TRUE);
                if(excHisCategory ==null) {
                    //存在
                    String tip = ResourceUtil.getString("sys-attend:sysAttendHisCategory.tip");
                    if (tip != null) {
                        tip = tip.replace("%sys-attend:sysAttendHisCategory.userName%", element.getFdName());
                        tip = tip.replace("%sys-attend:sysAttendHisCategory.categoryName%", hisCategory.getFdName());
                        tipList.add(tip);
                    }
                }
            }
        }
    }
    /**
     * 根据原始考勤组ID查询其所有的历史版本的ID
     * @param oldCategoryId 原始考勤组ID
     * @return
     * @throws Exception
     */
    @Override
    public Set<String> getAllCategorys(List<String> oldCategoryId) throws Exception {
        return getAllCategorys(oldCategoryId,true);
    }
    /**
     * 根据原始考勤组ID查询其所有的历史版本的ID
     * @param oldCategoryId 原始考勤组ID
     * @return
     * @throws Exception
     */
    @Override
    public Set<String> getAllCategorys(List<String> oldCategoryId,boolean fdIsAvailable) throws Exception {
        //TODO 可以考勤缓存，如果有新增的历史版本，则更新缓存
        Set<String> resultList =new HashSet<>();
        StringBuffer whereSql=new StringBuffer();
        HQLInfo hqlInfo=new HQLInfo();
        whereSql.append( HQLUtil.buildLogicIN("sysAttendHisCategory.fdCategoryId", oldCategoryId));
        if(fdIsAvailable) {
            whereSql.append(" and sysAttendHisCategory.fdIsAvailable=:fdIsAvailable");
            hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
            hqlInfo.setWhereBlock(whereSql.toString());
        }
        hqlInfo.setSelectBlock("sysAttendHisCategory.fdId");
        List<Object> hisCategoryIds = this.findList(hqlInfo);
        if(CollectionUtils.isNotEmpty(hisCategoryIds)){
            for (Object obj: hisCategoryIds) {
                resultList.add(obj.toString());
            }
        }
        return resultList;
    }

    /**
     * 根据原始考勤组ID，和负责人。查询其所有的历史版本的ID
     * @param oldCategoryId 原始考勤组ID
     * @param element 负责人
     * @return
     * @throws Exception
     */
    @Override
    public Set<String> getAllCategorys(List<String> oldCategoryId,SysOrgElement element) throws Exception {
        //TODO 可以考勤缓存，如果有新增的历史版本，则更新缓存
        Set<String> resultList =new HashSet<>();
        StringBuffer whereSql=new StringBuffer();
        HQLInfo hqlInfo=new HQLInfo();
        whereSql.append( HQLUtil.buildLogicIN("sysAttendHisCategory.fdCategoryId", oldCategoryId));
        whereSql.append(" and sysAttendHisCategory.fdIsAvailable=:fdIsAvailable");
        whereSql.append(" and sysAttendHisCategory.fdManager.fdId=:elementId");
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
        hqlInfo.setParameter("elementId",element.getFdId());
        hqlInfo.setWhereBlock(whereSql.toString());
        hqlInfo.setSelectBlock("sysAttendHisCategory.fdId");
        List<Object> hisCategoryIds = this.findList(hqlInfo);
        if(CollectionUtils.isNotEmpty(hisCategoryIds)){
            for (Object obj: hisCategoryIds) {
                resultList.add(obj.toString());
            }
        }
        return resultList;
    }

    /**
     * 根据原始考勤组ID 和 对应日期 获取相关的组织架构所有人员。
     * 不过滤排除人员。
     * 该方法主要用于查询的参数中
     * @param oldCategoryId
     * @param beginDate
     * @param endDate
     * @return
     * @throws Exception
     */
    @Override
    public Set<String> getCategoryUsers(String oldCategoryId,Date beginDate,Date endDate) throws Exception {
        //查询原始考勤组对应日期范围内的所有历史考勤组ID。
        StringBuffer whereSql=new StringBuffer();
        HQLInfo hqlInfo=new HQLInfo();
        whereSql.append( "sysAttendHisCategory.fdCategoryId =:oldCategoryId ");
        whereSql.append(" and sysAttendHisCategory.fdIsAvailable=:fdIsAvailable");
        //开始时间在范围内，或者结束时间大于开始时间
        whereSql.append(" and ( (sysAttendHisCategory.fdBeginTime >=:fdBeginTime and sysAttendHisCategory.fdBeginTime <=:fdEndTime )");
        whereSql.append(" or (sysAttendHisCategory.fdBeginTime <=:fdEndTime and  sysAttendHisCategory.fdEndTime > :fdBeginTime))");

        hqlInfo.setParameter("oldCategoryId",oldCategoryId);
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
        hqlInfo.setParameter("fdBeginTime",beginDate);
        hqlInfo.setParameter("fdEndTime",endDate);
        hqlInfo.setSelectBlock("sysAttendHisCategory.fdId");
        hqlInfo.setWhereBlock(whereSql.toString());
        List<String> list= this.findValue(hqlInfo);
        Set<String> orgIds=new HashSet<String>();
        if(CollectionUtils.isNotEmpty(list)){
            orgIds.addAll(getCategoryTargetOrgChangeByHisCategoryId(list));
            orgIds.addAll(getCategoryTargetByHisCategoryId(list));
        }
        return orgIds;
    }

    /**
     * 根据原始考勤组ID查询其所有的历史版本的ID
     * @param oldCategoryId 原始考勤组ID
     * @return
     * @throws Exception
     */
    private List<SysAttendHisCategory> getAllCategorys(String oldCategoryId) throws Exception {
        StringBuffer whereSql=new StringBuffer();
        HQLInfo hqlInfo=new HQLInfo();
        whereSql.append( "sysAttendHisCategory.fdCategoryId =:oldCategoryId ");
        whereSql.append(" and sysAttendHisCategory.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("oldCategoryId",oldCategoryId);
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
        hqlInfo.setWhereBlock(whereSql.toString());
        return this.findList(hqlInfo);
    }

    /**
     * 根本考勤组历史版本的ID获取对应的考勤组信息
     * @param categoryId
     * @return
     * @throws Exception
     */
    @Override
    public SysAttendCategory getCategoryById(String categoryId) throws Exception {
        if(StringUtil.isNull(categoryId)){
            return null;
        }
        SysAttendCategory sysAttendCategory = (SysAttendCategory) CategoryUtil.CATEGORY_CACHE_MAP.get(categoryId);
        if(sysAttendCategory !=null){
            return sysAttendCategory;
        }
        if(getBaseDao().isExist(SysAttendHisCategory.class.getName(),categoryId)) {
            SysAttendHisCategory hisCategory = (SysAttendHisCategory) this.findByPrimaryKey(categoryId, SysAttendHisCategory.class, false);
            return convertCategory(hisCategory);
        }
        return null;
    }
    /**
     * 根据组织和日期获取对应的考勤组配置信息
     * @param orgElement 组织对象
     * @param date 日期
     * @return
     * @throws Exception
     */
    @Override
    public SysAttendCategory getCategoryByUserAndDate(SysOrgElement orgElement,Date date,Integer fdType) throws Exception {
        if(orgElement ==null){
            return null;
        }
        //人员跟日期在考勤组中存在的情况 则从缓存中获取
        String key =SysAttendUserCacheUtil.getUserCategoryKey(orgElement.getFdId());
        SysAttendUserCategoryListDto userCategoryListCatche = (SysAttendUserCategoryListDto) CategoryUtil.USER_CATEGORY_CACHE_MAP.get(key);
        if(userCategoryListCatche !=null){
            String categoryId = userCategoryListCatche.get(date);
            if(StringUtil.isNotNull(categoryId)) {
                return CategoryUtil.getCategoryById(categoryId);
            }
        }
        SysAttendHisCategory hisCategory = getHisCategoryByUserAndDate(orgElement,null,date,fdType,true);
        return convertCategoryAndCache(key,hisCategory,date);
    }

    /**
     * 根据人员查找到的考勤组结果。进行缓存并且转换考勤组
     * @param key 缓存key
     * @param hisCategory 历史考勤组信息
     * @return
     * @throws Exception
     */
    private SysAttendCategory convertCategoryAndCache(String key,SysAttendHisCategory hisCategory,Date date) throws Exception {
        if(hisCategory !=null){
            SysAttendUserCategoryListDto cacheInfo = (SysAttendUserCategoryListDto) CategoryUtil.USER_CATEGORY_CACHE_MAP.get(key);
            if(cacheInfo ==null) {
                cacheInfo = new SysAttendUserCategoryListDto();
            }
            cacheInfo.put(hisCategory.getFdId(),date,null,hisCategory.getFdCategoryId(),hisCategory.getLevel());
            CategoryUtil.USER_CATEGORY_CACHE_MAP.put(key,cacheInfo);
            return convertCategory(hisCategory);
        }
        return null;
    }
    /**
     * 根据组织和日期获取对应的考勤组配置信息
     * @param orgElement 组织对象
     * @param date 日期
     * @deprecated 该方法未过滤全局排除人员，获取人员所在考勤组 建议调用sysAttendCategoryService中的getCategoryInfo,不可直接调用
     * @return
     * @throws Exception
     */
    @Override
    public SysAttendCategory getCategoryByUserAndDate(SysOrgElement orgElement,Date date,Integer fdType,Boolean isExc) throws Exception {
        //人员跟日期在考勤组中存在的情况 则从缓存中获取
        if(orgElement ==null){
            return null;
        }
        String key =SysAttendUserCacheUtil.getUserCategoryKey(orgElement.getFdId());
        SysAttendUserCategoryListDto userCategoryListCatche = (SysAttendUserCategoryListDto) CategoryUtil.USER_CATEGORY_CACHE_MAP.get(key);
        if(userCategoryListCatche !=null){
            String categoryId = userCategoryListCatche.get(date);
            if(StringUtil.isNotNull(categoryId)) {
                return CategoryUtil.getCategoryById(categoryId);
            }
        }
        SysAttendHisCategory hisCategory = getHisCategoryByUserAndDate(orgElement,null,date,fdType,isExc);
        return convertCategoryAndCache(key,hisCategory,date);
    }
    /**
     * 根据组织和日期获取对应的考勤组历史配置信息
     * @param orgElement 组织对象
     * @param date 日期
     * @param excTargets 排除人员
     * @param fdType 考勤组类型：1考勤组，2签到组。
     * @return
     * @throws Exception
     */
    private SysAttendHisCategory getHisCategoryByUserAndDate(SysOrgElement orgElement,
                                                             List<SysOrgElement> excTargets,
                                                             Date date,
                                                             Integer fdType,Boolean isExc) throws Exception {
        String leveId =orgElement.getFdHierarchyId();
        //离职人员。重新组装其层级id
        if("0".equals(leveId) || !Boolean.TRUE.equals(orgElement.getFdIsAvailable())){
            String predDeptId = orgElement.getFdPreDeptId();
            //离职人员 默认以自己的id为层级
            leveId = String.format("x%sx", orgElement.getFdId());
            if(StringUtil.isNull(predDeptId)){
                //防止外部参数缺失。在查一次。
                orgElement = getSysOrgCoreService().findByPrimaryKey(orgElement.getFdId());
                if(orgElement !=null) {
                    predDeptId = orgElement.getFdPreDeptId();
                }
            }
            if(StringUtil.isNotNull(predDeptId) && !"null".equalsIgnoreCase(predDeptId)){
                if(getSysOrgElementService().getBaseDao().isExist(SysOrgElement.class.getName(),predDeptId)) {
                    SysOrgElement predDept = getSysOrgCoreService().findByPrimaryKey(predDeptId);
                    if (predDept != null) {
                        leveId = String.format("%sx%sx", predDept.getFdHierarchyId(), orgElement.getFdId());
                    }
                }
            }
        }
        return getHisCategoryByUserLeveAndDate(leveId,excTargets,date,fdType,isExc);
    }

    /**
     * 根据组织层级ID获取对应所在的历史考勤组
     * @param hierarchyId 组织层级ID
     * @param excTargets 排除人员
     * @param date 日期
     * @param fdType 类型默认是考勤组
     * @param isExc 是否过滤排除人员
     * @return
     * @throws Exception
     */
    private SysAttendHisCategory getHisCategoryByUserLeveAndDate(String hierarchyId,List<SysOrgElement> excTargets,
                                                                 Date date,
                                                                 Integer fdType,
                                                                 Boolean isExc) throws Exception {
        if(StringUtil.isNull(hierarchyId)){
            return null;
        }
        if(fdType==null){
            //默认签到组
            fdType =CategoryUtil.CATEGORY_FD_TYPE_TRUE;
        }
        // 判断层级关系
        String[] ids =hierarchyId.split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
        //全局排除人员，在调用方处理
        SysAttendCategoryTargetNew categoryTargetNew=null;
        for (int i = ids.length-1; i >= 0; i--) {
            //倒序查找
            String id = ids[i];
            if (StringUtil.isNotNull(id)) {
                if (CollectionUtils.isNotEmpty(excTargets)) {
                    //先过滤是否在本次排除的范围内，如果不是再进行数据库中排查
                    for (SysOrgElement extTarget : excTargets) {
                        if (id.equals(extTarget.getFdId())) {
                            return null;
                        }
                    }
                }
                SysAttendHisCategory hisCategory = getCategoryTarget(id, date, fdType);
                if(hisCategory ==null){
                    //如果在原考勤组中查找不到，查找组织架构变更
                    hisCategory =getCategoryTargetChange(id,date);
                }
                if(isExc) {
                    if (hisCategory != null) {
                        //过滤在该考勤组中属于排除人员
                        String[] haveIds = hierarchyId.split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
                        for (int j = haveIds.length - 1; j >= 0; j--) {
                            //倒序查找
                            String idNew = haveIds[j];
                            if (StringUtil.isNotNull(idNew)) {
                                if ( getCategoryExc(idNew, hisCategory.getFdId(), date, fdType) != null) {
                                    return null;
                                }
                            }
                        }
                        hisCategory.setLevel(i);
                        return hisCategory;
                    }
                }else{
                    if(hisCategory !=null) {
                        hisCategory.setLevel(i);
                        return hisCategory;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 转换考勤组信息
     * @param hisCategory
     * @return
     * @throws Exception
     */
    @Override
    public SysAttendCategory convertCategory(SysAttendHisCategory hisCategory) throws Exception {
        if(hisCategory ==null){
            return null;
        }
        if(Boolean.TRUE.equals(hisCategory.getFdIsAvailable())) {
            //这里进行缓存处理，同一周期的重复查询直接从缓存中获取，外部调用需要手动清理释放内存
            SysAttendCategory sysAttendCategory = (SysAttendCategory) CategoryUtil.CATEGORY_CACHE_MAP.get(hisCategory.getFdId());
            if (sysAttendCategory != null) {
                return sysAttendCategory;
            }
            //配置信息因为使用场景少，这里使用外键关联
            SysAttendHisCategoryContent categoryContent=hisCategory.getFdCategoryContentNew();
            if(categoryContent !=null) {
                String content = categoryContent.getFdCategoryContent();
                //JSON对象存储的form 这里需要将form转出model返回
                if (StringUtil.isNotNull(content)) {
                    sysAttendCategory = JSONObject.parseObject(content, SysAttendCategory.class);
                    //把历史版本的主键赋值给 分类。用于后续的计算
                    sysAttendCategory.setFdId(hisCategory.getFdId());
                    sysAttendCategory.setDocCreator(hisCategory.getDocAlteror());
                    sysAttendCategory.setDocAlteror(hisCategory.getDocAlteror());
                    sysAttendCategory.setFdManager(hisCategory.getFdManager());
                    CategoryUtil.CATEGORY_CACHE_MAP.put(hisCategory.getFdId(), sysAttendCategory);
                    return sysAttendCategory;
                }
            }
        }
        return null;
    }
    /**
     * 获取某个日期所有正在进行的考勤组列表
     * @param fdType 考勤组还是签到组
     * @param date 日期
     * @return
     * @throws Exception
     */
    @Override
    public Set<String> getAllCategorys(Date date,Integer fdType) throws Exception {
        Set<String> resultList =new HashSet<>();
        StringBuffer whereSql=new StringBuffer();
        whereSql.append("sysAttendCategoryTargetNew.fdBeginTime <= :date and sysAttendCategoryTargetNew.fdEndTime > :date ");
        HQLInfo hqlInfo=new HQLInfo();
        whereSql.append(" and sysAttendCategoryTargetNew.hisCategoryId.fdType=:fdType");
        hqlInfo.setParameter("fdType",fdType);

        whereSql.append(" and sysAttendCategoryTargetNew.hisCategoryId.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);

        hqlInfo.setWhereBlock(whereSql.toString());
        hqlInfo.setParameter("date",date);
        hqlInfo.setSelectBlock("sysAttendCategoryTargetNew.hisCategoryId.fdId");
        List<Object> hisCategoryIds = getSysAttendCategoryTargetNewDao().findList(hqlInfo);
        if(CollectionUtils.isNotEmpty(hisCategoryIds)){
            for (Object obj: hisCategoryIds) {
                resultList.add(obj.toString());
            }
        }
        return resultList;
    }

    /**
     * 根据历史考勤组ID查询所有的有变化的组织对象
     * @param hisId
     * @return
     * @throws Exception
     */
    @Override
    public Set<SysOrgElement> getChangeOrgByHisId(String hisId) throws Exception {
        Set<SysOrgElement> resultList =new HashSet<>();
        StringBuffer whereSql=new StringBuffer();
        HQLInfo hqlInfo=new HQLInfo();
        whereSql.append(" sysAttendCategoryTargetChange.fdHisCategory.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);

        whereSql.append(" and  sysAttendCategoryTargetChange.fdHisCategory.fdId=:hisId");
        hqlInfo.setParameter("hisId",hisId);

        hqlInfo.setWhereBlock(whereSql.toString());
        hqlInfo.setSelectBlock("sysAttendCategoryTargetChange.fdOrg");
        List<SysOrgElement> hisCategoryIds = getSysAttendCategoryTargetChangeDao().findValue(hqlInfo);
        if(CollectionUtils.isNotEmpty(hisCategoryIds)){
            for (SysOrgElement obj: hisCategoryIds) {
                resultList.add(obj);
            }
        }
        return resultList;
    }
    /**
     * 根据历史考勤组ID查询所有的组织对象
     * @param hisId
     * @return
     * @throws Exception
     */
    @Override
    public Set<SysOrgElement> getTargetOrgByHisId(String hisId) throws Exception {
        Set<SysOrgElement> resultList =new HashSet<>();
        StringBuffer whereSql=new StringBuffer();
        HQLInfo hqlInfo=new HQLInfo();
        whereSql.append(" sysAttendCategoryTargetNew.hisCategoryId.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);

        whereSql.append(" and sysAttendCategoryTargetNew.hisCategoryId.fdId=:hisId");
        hqlInfo.setParameter("hisId",hisId);

        hqlInfo.setWhereBlock(whereSql.toString());
        hqlInfo.setSelectBlock("sysAttendCategoryTargetNew.fdOrg");
        List<SysOrgElement> hisCategoryIds = getSysAttendCategoryTargetNewDao().findValue(hqlInfo);
        if(CollectionUtils.isNotEmpty(hisCategoryIds)){
            for (SysOrgElement obj: hisCategoryIds) {
                resultList.add(obj);
            }
        }
        return resultList;
    }
    /**
     * 根据历史考勤组ID查询所有的组织对象
     * @param hisId
     * @return
     * @throws Exception
     */
    @Override
    public Set<SysOrgElement> getExcOrgByHisId(String hisId) throws Exception {
        Set<SysOrgElement> resultList =new HashSet<>();
        StringBuffer whereSql=new StringBuffer();
        HQLInfo hqlInfo=new HQLInfo();
        whereSql.append(" sysAttendCategoryExcNew.hisCategoryId.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);

        whereSql.append(" and sysAttendCategoryExcNew.hisCategoryId.fdId=:hisId");
        hqlInfo.setParameter("hisId",hisId);

        hqlInfo.setWhereBlock(whereSql.toString());
        hqlInfo.setSelectBlock("sysAttendCategoryExcNew.fdOrg");
        List<SysOrgElement> hisCategoryIds = getSysAttendCategoryExcNewDao().findValue(hqlInfo);
        if(CollectionUtils.isNotEmpty(hisCategoryIds)){
            for (SysOrgElement obj: hisCategoryIds) {
                resultList.add(obj);
            }
        }
        return resultList;
    }

    /**
     * 根据某个考勤对象
     * 获取组织某个时间段所对应的考勤组配置信息
     * @param orgId 组织id
     * @param date 日期
     * @return
     * @throws Exception
     */
    private SysAttendHisCategory getCategoryTarget(String orgId,Date date,Integer fdType) throws Exception {
        StringBuffer whereSql=new StringBuffer();
        whereSql.append("sysAttendCategoryTargetNew.fdOrg.fdId=:orgId and sysAttendCategoryTargetNew.fdBeginTime <= :date and sysAttendCategoryTargetNew.fdEndTime > :date ");
        whereSql.append(" and sysAttendCategoryTargetNew.hisCategoryId.fdType=:fdType");
        whereSql.append(" and sysAttendCategoryTargetNew.hisCategoryId.fdIsAvailable=:fdIsAvailable");
        HQLInfo hqlInfo=new HQLInfo();
        hqlInfo.setWhereBlock(whereSql.toString());
        hqlInfo.setParameter("fdType",fdType);
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
        hqlInfo.setParameter("orgId",orgId);
        hqlInfo.setParameter("date",date);
        hqlInfo.setSelectBlock("sysAttendCategoryTargetNew.hisCategoryId.fdId");
        //先查ID是否存在。存在的情况，在查询对象
        List<String> hisIds = getSysAttendCategoryTargetNewDao().findValue(hqlInfo);
        if(CollectionUtils.isNotEmpty(hisIds)) {
            List<SysAttendHisCategory> list =this.findByPrimaryKeys(hisIds.toArray(new String[hisIds.size()]));
            if(CollectionUtils.isNotEmpty(list)) {
                return list.get(0);
            }

//            HQLInfo hqlInfo2=new HQLInfo();
//            hqlInfo2.setWhereBlock(whereSql.toString());
//            hqlInfo2.setParameter("fdType",fdType);
//            hqlInfo2.setParameter("fdIsAvailable",Boolean.TRUE);
//            hqlInfo2.setParameter("orgId",orgId);
//            hqlInfo2.setParameter("date",date);
//            hqlInfo2.setOrderBy("sysAttendCategoryTargetNew.fdId desc");
//            hqlInfo2.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
//            hqlInfo2.setRowSize(1);
//            hqlInfo2.setGetCount(Boolean.FALSE);
//            Page page = getSysAttendCategoryTargetNewDao().findPage(hqlInfo2);
//            if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
//                return (SysAttendCategoryTargetNew) page.getList().get(0);
//            }
        }
        return null;
    }

    /**
     * 根据某个考勤对象
     * 获取组织某个时间段所对应的考勤组配置信息
     * @param orgId 组织id
     * @param date 日期
     * @return
     * @throws Exception
     */
    private SysAttendHisCategory getCategoryTargetChange(String orgId,Date date) throws Exception {
        StringBuffer whereSql=new StringBuffer();
        whereSql.append("sysAttendCategoryTargetChange.fdOrg.fdId=:orgId and sysAttendCategoryTargetChange.fdBeginTime <= :date and sysAttendCategoryTargetChange.fdEndTime > :date ");
        HQLInfo hqlInfoTemp = new HQLInfo();
        hqlInfoTemp.setWhereBlock(whereSql.toString());
        hqlInfoTemp.setSelectBlock("sysAttendCategoryTargetChange.fdHisCategory.fdId");
        hqlInfoTemp.setParameter("orgId", orgId);
        hqlInfoTemp.setParameter("date", date);
        //先查ID是否存在。存在的情况，在查询对象
        List<String> hisIds = getSysAttendCategoryTargetChangeDao().findValue(hqlInfoTemp);
        if(CollectionUtils.isNotEmpty(hisIds)) {
            List<SysAttendHisCategory> list =this.findByPrimaryKeys(hisIds.toArray(new String[hisIds.size()]));
            if(CollectionUtils.isNotEmpty(list)) {
                return list.get(0);
            }
//            HQLInfo hqlInfo = new HQLInfo();
//            hqlInfo.setWhereBlock(whereSql.toString());
//            hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
//            hqlInfo.setParameter("orgId", orgId);
//            hqlInfo.setParameter("date", date);
//            hqlInfo.setOrderBy("sysAttendCategoryTargetChange.fdId desc");
//            hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
//            hqlInfo.setRowSize(1);
//            hqlInfo.setGetCount(Boolean.FALSE);
//            Page page = getSysAttendCategoryTargetChangeDao().findPage(hqlInfo);
//            if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
//                return (SysAttendCategoryTargetChange) page.getList().get(0);
//            }
        }
        return null;
    }

    /**
     * 根据某个考勤组配置ID以及时间
     * 获取该考勤对象列表
     * @param date 日期
     * @param fdType 考勤组类型，【1是考勤组、2是签到组】
     * @return 排除组织对象列表
     * @throws Exception
     */
    @Override
    public List<SysOrgElement> getCategoryTargetOrg(Date date,Integer fdType) throws Exception {
        if( date ==null || fdType==null){
            throw new Exception(" params is null");
        }
        return this.getCategoryTargetOrgCommon(null,date,fdType,null,null);
    }
    /**
     * 根据某个考勤组配置ID以及时间
     * 获取该考勤对象列表
     * @param date 日期
     * @param hisCategoryId 考勤组配置ID,如果为空则查询所有考勤组的人员列表
     * @param fdType 考勤组类型，【1是考勤组、2是签到组】
     * @return 排除组织对象列表
     * @throws Exception
     */
    @Override
    public List<SysOrgElement> getCategoryTargetOrg(String hisCategoryId, Date date,Integer fdType) throws Exception {
        if(StringUtil.isNull(hisCategoryId) || date ==null || fdType==null){
            throw new Exception(" params is null");
        }
        return this.getCategoryTargetOrgCommon(hisCategoryId,date,fdType,null,null);
    }
    /**
     * 根据某个考勤组配置ID以及时间
     * 获取该考勤对象列表
     * @param date 日期
     * @param oldCategoryId 原始考勤组ID
     * @param fdType 考勤组类型，【1是考勤组、2是签到组】
     * @return 排除组织对象列表
     * @throws Exception
     */
    @Override
    public List<SysOrgElement> getOldCategoryTargetOrg(String oldCategoryId, Date date,Integer fdType) throws Exception {
        if(StringUtil.isNull(oldCategoryId) || date ==null || fdType==null){
            throw new Exception(" params is null");
        }
        return this.getCategoryTargetOrgCommon(null,date,fdType,oldCategoryId,null);
    }

    /**
     * 根据某个考勤组配置ID以及时间
     * 获取该考勤对象列表
     * @param date 日期
     * @param hisCategoryId 考勤组配置ID,如果为空则查询所有考勤组的人员列表
     * @param fdType 考勤组类型，【1是考勤组、2是签到组】
     * @param oldCategoryId 原始考勤组
     * @return 考勤对象列表
     * @throws Exception
     */
    private List<SysOrgElement> getCategoryTargetOrgCommon(String hisCategoryId, Date date,Integer fdType,String oldCategoryId,String userId) throws Exception {
        HQLInfo hqlInfo=new HQLInfo();
        Set<SysOrgElement> resultList =new HashSet<>();

        StringBuffer whereSql=new StringBuffer();
        whereSql.append(" sysAttendCategoryTargetNew.fdBeginTime <=:date and sysAttendCategoryTargetNew.fdEndTime > :date ");
        whereSql.append(" and sysAttendCategoryTargetNew.hisCategoryId.fdType=:fdType");
        hqlInfo.setParameter("fdType",fdType);

        whereSql.append(" and sysAttendCategoryTargetNew.hisCategoryId.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);

        if(StringUtil.isNotNull(hisCategoryId)) {
            whereSql.append(" and sysAttendCategoryTargetNew.hisCategoryId.fdId=:hisCategoryId");
            hqlInfo.setParameter("hisCategoryId",hisCategoryId);
        }
        if(StringUtil.isNotNull(oldCategoryId)) {
            //原始考勤组
            whereSql.append(" and sysAttendCategoryTargetNew.hisCategoryId.fdCategoryId=:oldCategoryId");
            hqlInfo.setParameter("oldCategoryId",oldCategoryId);
        }
        if(StringUtil.isNotNull(userId)) {
            whereSql.append(" and sysAttendCategoryTargetNew.fdOrg.fdId=:orgFdId");
            hqlInfo.setParameter("orgFdId",userId);
        }
        hqlInfo.setWhereBlock(whereSql.toString());
        hqlInfo.setParameter("date",date);
        List<SysAttendCategoryTargetNew> lists= getSysAttendCategoryTargetNewDao().findList(hqlInfo);
        if(CollectionUtils.isNotEmpty(lists)){
            for (SysAttendCategoryTargetNew bean:lists) {
                resultList.add(bean.getFdOrg());
            }
        }
        //查找人员组织架构变更后所属的考勤组
        List<SysOrgElement> changeList  = getCategoryTargetOrgChangeCommon(hisCategoryId,date,oldCategoryId,userId);
        if(CollectionUtils.isNotEmpty(changeList)){
            resultList.addAll(changeList);
        }
        return Lists.newArrayList(resultList);
    }

    /**
     * 查询某个历史考勤组中所有变更组织架构。组织架构ID
     * @param hisCategoryId 历史考勤组
     * @return
     * @throws Exception
     */
    private List<String> getCategoryTargetOrgChangeByHisCategoryId(List<String> hisCategoryId) throws Exception {
        HQLInfo hqlInfo=new HQLInfo();
        StringBuffer whereSql=new StringBuffer();
        whereSql.append(" sysAttendCategoryTargetChange.fdIsAvailable=:fdIsAvailable");
        whereSql.append(" and sysAttendCategoryTargetChange.fdHisCategory.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
        whereSql.append(" and ").append(HQLUtil.buildLogicIN(" sysAttendCategoryTargetChange.fdHisCategory.fdId",hisCategoryId));
        hqlInfo.setSelectBlock("sysAttendCategoryTargetChange.fdOrg.fdId");
        hqlInfo.setWhereBlock(whereSql.toString());
        return getSysAttendCategoryTargetChangeDao().findValue(hqlInfo);
    }
    /**
     * 查询某个历史考勤组中所有组织架构ID
     * @param hisCategoryId 历史考勤组
     * @return
     * @throws Exception
     */
    private List<String> getCategoryTargetByHisCategoryId(List<String> hisCategoryId) throws Exception {
        HQLInfo hqlInfo=new HQLInfo();
        StringBuffer whereSql=new StringBuffer();
        whereSql.append(" sysAttendCategoryTargetNew.hisCategoryId.fdIsAvailable=:fdIsAvailable");
        whereSql.append(" and ").append(HQLUtil.buildLogicIN(" sysAttendCategoryTargetNew.hisCategoryId.fdId",hisCategoryId));
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
        hqlInfo.setSelectBlock("sysAttendCategoryTargetNew.fdOrg.fdId");
        hqlInfo.setWhereBlock(whereSql.toString());
        return getSysAttendCategoryTargetNewDao().findValue(hqlInfo);
    }
    /**
     * 获取某个时间段 内 人员变更组织架构 所属的考勤组
     * @param date 日期
     * @param hisCategoryId 考勤组配置ID,如果为空则查询所有考勤组的人员列表
     * @param oldCategoryId 原始考勤组
     * @return 考勤对象列表
     * @throws Exception
     */
    private List<SysOrgElement> getCategoryTargetOrgChangeCommon(String hisCategoryId, Date date,String oldCategoryId,String userId) throws Exception {
        HQLInfo hqlInfo=new HQLInfo();
        Set<SysOrgElement> resultList =new HashSet<>();

        StringBuffer whereSql=new StringBuffer();
        whereSql.append(" sysAttendCategoryTargetChange.fdBeginTime <=:date and sysAttendCategoryTargetChange.fdEndTime > :date ");
        whereSql.append(" and sysAttendCategoryTargetChange.fdIsAvailable=:fdIsAvailable");
        whereSql.append(" and sysAttendCategoryTargetChange.fdHisCategory.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);

        if(StringUtil.isNotNull(hisCategoryId)) {
            whereSql.append(" and sysAttendCategoryTargetChange.fdHisCategory.fdId=:hisCategoryId");
            hqlInfo.setParameter("hisCategoryId",hisCategoryId);
        }
        if(StringUtil.isNotNull(oldCategoryId)) {
            //原始考勤组
            whereSql.append(" and sysAttendCategoryTargetChange.fdHisCategory.fdCategoryId=:oldCategoryId");
            hqlInfo.setParameter("oldCategoryId",oldCategoryId);
        }
        if(StringUtil.isNotNull(userId)) {
            whereSql.append(" and sysAttendCategoryTargetChange.fdOrg.fdId=:orgFdId");
            hqlInfo.setParameter("orgFdId",userId);
        }
        hqlInfo.setWhereBlock(whereSql.toString());
        hqlInfo.setParameter("date",date);
        List<SysAttendCategoryTargetChange> lists= getSysAttendCategoryTargetChangeDao().findList(hqlInfo);
        if(CollectionUtils.isNotEmpty(lists)){
            for (SysAttendCategoryTargetChange bean:lists) {
                resultList.add(bean.getFdOrg());
            }
        }
        return Lists.newArrayList(resultList);
    }


    /**
     * 根据组织ID 获取 组织ID变更的所有历史记录
     * @param orgId 组织架构iD
     * @return 考勤对象列表
     * @throws Exception
     */
    private List<SysAttendCategoryTargetChange> getCategoryTargetChangeInfo(String orgId) throws Exception {
        HQLInfo hqlInfo=new HQLInfo();
        StringBuffer whereSql=new StringBuffer();
        whereSql.append(" sysAttendCategoryTargetChange.fdIsAvailable=:fdIsAvailable");
        whereSql.append(" and sysAttendCategoryTargetChange.fdHisCategory.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
        if(StringUtil.isNotNull(orgId)) {
            whereSql.append(" and sysAttendCategoryTargetChange.fdOrg.fdId=:orgFdId");
            hqlInfo.setParameter("orgFdId",orgId);
        }
        hqlInfo.setWhereBlock(whereSql.toString());
        hqlInfo.setOrderBy("sysAttendCategoryTargetChange.fdEndTime desc");
        List<SysAttendCategoryTargetChange> lists= getSysAttendCategoryTargetChangeDao().findList(hqlInfo);

        return lists;
    }

    /**
     * 根据某个考勤组配置ID以及时间
     * 获取该考勤对象是否在排除范围内
     * @param date 日期
     * @param fdType 考勤组配置ID
     * @return 排除组织对象列表
     * @throws Exception
     */
    @Override
    public List<SysOrgElement> getCategoryExcOrg(Date date,Integer fdType) throws Exception {
        if(date ==null || fdType==null){
            throw new Exception(" params is null");
        }
        return this.getCategoryExcOrgCommon(null,date,fdType,null);
    }
    /**
     * 根据某个考勤组配置ID以及时间
     * 获取该考勤对象是否在排除范围内
     * @param date 日期
     * @param hisCategoryId 考勤组配置ID
     * @return 排除组织对象列表
     * @throws Exception
     */
    @Override
    public List<SysOrgElement> getCategoryExcOrg(String hisCategoryId, Date date,Integer fdType) throws Exception {
        if(StringUtil.isNull(hisCategoryId) || date ==null || fdType==null){
            throw new Exception(" params is null");
        }
        return this.getCategoryExcOrgCommon(hisCategoryId,date,fdType,null);
    }
    /**
     * 根据某个考勤组配置ID以及时间
     * 获取该考勤排除对象列表
     * @param date 日期
     * @param oldCategoryId 原始考勤组ID
     * @param fdType 考勤组类型，【1是考勤组、2是签到组】
     * @return 排除组织对象列表
     * @throws Exception
     */
    @Override
    public List<SysOrgElement> getOldCategoryExcOrg(String oldCategoryId, Date date,Integer fdType) throws Exception {
        if(StringUtil.isNull(oldCategoryId) || date ==null || fdType==null){
            throw new Exception(" params is null");
        }
        return this.getCategoryExcOrgCommon(null,date,fdType,oldCategoryId);
    }
    /**
     * 获取考勤排除人员
     * @param hisCategoryId 考勤组
     * @param date 日期
     * @param fdType 考勤组类型
     * @return 考勤组为空，则查询所有的考勤组
     * @param oldCategoryId 原始考勤组
     * @throws Exception
     */
    private List<SysOrgElement> getCategoryExcOrgCommon(String hisCategoryId, Date date,Integer fdType,String oldCategoryId) throws Exception {
        Set<SysOrgElement> resultList =new HashSet<>();
        HQLInfo hqlInfo=new HQLInfo();

        StringBuffer whereSql=new StringBuffer();
        whereSql.append(" sysAttendCategoryExcNew.fdBeginTime <=:date and sysAttendCategoryExcNew.fdEndTime > :date ");
        whereSql.append(" and sysAttendCategoryExcNew.hisCategoryId.fdType=:fdType");

        whereSql.append(" and sysAttendCategoryExcNew.hisCategoryId.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);

        hqlInfo.setParameter("fdType",fdType);
        if(StringUtil.isNotNull(hisCategoryId)) {
            whereSql.append(" and sysAttendCategoryExcNew.hisCategoryId.fdId=:hisCategoryId");
            hqlInfo.setParameter("hisCategoryId", hisCategoryId);
        }
        if(StringUtil.isNotNull(oldCategoryId)) {
            //原始考勤组
            whereSql.append(" and sysAttendCategoryExcNew.hisCategoryId.fdCategoryId=:oldCategoryId");
            hqlInfo.setParameter("oldCategoryId",oldCategoryId);
        }
        hqlInfo.setWhereBlock(whereSql.toString());
        hqlInfo.setParameter("date",date);
        List<SysAttendCategoryExcNew> sysAttendCategoryExcNews= getSysAttendCategoryExcNewDao().findList(hqlInfo);
        if(CollectionUtils.isNotEmpty(sysAttendCategoryExcNews)){
            for (SysAttendCategoryExcNew bean:sysAttendCategoryExcNews) {
                resultList.add(bean.getFdOrg());
            }
        }
        return Lists.newArrayList(resultList);
    }

    /**
     * 根据某个考勤对象和考勤组配置ID以及时间
     * 获取该考勤对象是否在排除范围内
     * @param orgId 组织id
     * @param date 日期
     * @param hisCategoryId 考勤组配置ID
     * @return 考勤组排除对象
     * @throws Exception
     */
    private SysAttendHisCategory getCategoryExc(String orgId,String hisCategoryId,Date date,Integer fdType) throws Exception {
        StringBuffer whereSql=new StringBuffer();

        whereSql.append("sysAttendCategoryExcNew.fdOrg.fdId=:orgId and sysAttendCategoryExcNew.fdBeginTime <=:date and sysAttendCategoryExcNew.fdEndTime > :date ");
        whereSql.append(" and sysAttendCategoryExcNew.hisCategoryId.fdId=:hisCategoryId");
        whereSql.append(" and sysAttendCategoryExcNew.hisCategoryId.fdIsAvailable=:fdIsAvailable");
        HQLInfo hqlInfoTemp = new HQLInfo();
        hqlInfoTemp.setWhereBlock(whereSql.toString());
        hqlInfoTemp.setSelectBlock("sysAttendCategoryExcNew.hisCategoryId.fdId");
        hqlInfoTemp.setParameter("fdIsAvailable", Boolean.TRUE);
        hqlInfoTemp.setParameter("orgId", orgId);
        hqlInfoTemp.setParameter("date", date);
        hqlInfoTemp.setParameter("hisCategoryId",hisCategoryId);
        //先查ID是否存在。存在的情况，在查询对象
        List<String> hisIds = getSysAttendCategoryExcNewDao().findValue(hqlInfoTemp);
        if(CollectionUtils.isNotEmpty(hisIds)) {
            List<SysAttendHisCategory> list =this.findByPrimaryKeys(hisIds.toArray(new String[hisIds.size()]));
            if(CollectionUtils.isNotEmpty(list)) {
                return list.get(0);
            }
        }
        return null;
    }
    /**
     * 获取考勤组最后时间的历史版本FdId
     * @param categoryId
     * @return
     */
    @Override
    public SysAttendHisCategory getLastVersionFdId(String categoryId) throws Exception {
        HQLInfo hqlInfo=new HQLInfo();
        hqlInfo.setWhereBlock("sysAttendHisCategory.fdCategoryId =:categoryId and sysAttendHisCategory.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("categoryId",categoryId);
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
        hqlInfo.setOrderBy(" sysAttendHisCategory.fdEndTime desc ");
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,  SysAuthConstant.AllCheck.NO);
        hqlInfo.setRowSize(1);
        hqlInfo.setGetCount(Boolean.FALSE);
        Page page =this.findPage(hqlInfo);
        if(page !=null && CollectionUtils.isNotEmpty(page.getList())){
            return (SysAttendHisCategory) page.getList().get(0);
        }
        return null;
    }
    /**
     * 根据原始考勤组ID和日期 或者相关的考勤组的id
     * @param categoryId
     * @return
     */
    @Override
    public SysAttendHisCategory getLastVersionFdId(String categoryId,Date fdBeginTime) throws Exception {
        HQLInfo hqlInfo=new HQLInfo();
        hqlInfo.setWhereBlock("sysAttendHisCategory.fdCategoryId =:categoryId and sysAttendHisCategory.fdBeginTime <=:fdBeginTime and fdEndTime > :fdBeginTime and sysAttendHisCategory.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("categoryId",categoryId);
        hqlInfo.setParameter("fdBeginTime",fdBeginTime);
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
        hqlInfo.setOrderBy("sysAttendHisCategory.fdEndTime desc");
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
                SysAuthConstant.AllCheck.NO);
        hqlInfo.setRowSize(1);
        hqlInfo.setGetCount(Boolean.FALSE);
        Page page =this.findPage(hqlInfo);
        if(page !=null && CollectionUtils.isNotEmpty(page.getList())){
            return (SysAttendHisCategory) page.getList().get(0);
        }
        return null;
    }


    /**
     * 编辑历史考勤组
     * @param sysAttendHisCategoryForm
     */
    @Override
    public void updateHisCategory(SysAttendHisCategoryForm sysAttendHisCategoryForm) throws Exception {
        SysAttendCategoryForm categoryForm = sysAttendHisCategoryForm.getSysAttendCategoryForm();
        SysAttendCategory category = new SysAttendCategory();
        if(categoryForm !=null) {
            //转换单独的属性类型
            boolean isException =false;
            TransactionStatus status =null;
            try {
                //使用只读事务进行转换
                status =TransactionUtils.beginNewReadTransaction();
                getSysAttendCategoryService().convertFormToModel(categoryForm, category, new RequestContext());
            }catch (Exception e){
                isException =true;
            }finally {
                if(isException && status !=null) {
                    TransactionUtils.rollback(status);
                }else if(status !=null){
                    TransactionUtils.commit(status);
                }
            }
        }
        if(Boolean.TRUE.equals(category.getFdCanMap())){
            List<SysAttendCategoryLocation>  locations =category.getFdLocations();
            //如果开启的是地图验证，则判断地图配置
            if(CollectionUtils.isEmpty(locations)){
                throw new RuntimeException(ResourceUtil.getString("sys-attend:sysAttendMainLog.fdAddress.error"));
            }
            //如果地址存在，地址名称为空。则认为数据丢失
            for (SysAttendCategoryLocation location:locations) {
                if(StringUtil.isNull(location.getFdLocation()) || StringUtil.isNull(location.getFdLatLng())){
                    throw new RuntimeException(ResourceUtil.getString("sys-attend:sysAttendMainLog.fdAddress.error"));
                }
            }
        }
        category.setFdId(sysAttendHisCategoryForm.getFdCategoryId());
        //验证考勤组中的对象是否在其他考勤组
        String tip = this.checkCategoryTarget(category);
        if(StringUtil.isNotNull(tip)){
            throw new RuntimeException(tip);
        }
        // 1)仅修改考勤组的Content/外勤是否需要审批
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(CategoryUtil.EXCLUDE_PROPERTIES);
        String contentJson = com.alibaba.fastjson.JSONObject.toJSONString(category, excludefilter,
                SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect
        );
        //先把原来的设置为无效。新创建一个。用于历史考勤组的编辑历史记录
        //重新生成一份新的历史记录。（影响点，考勤统计中的考勤组可能是关联原来的，需要重新统计以后才会更新为最新的）
        SysAttendHisCategory hisCategory = (SysAttendHisCategory) this.findByPrimaryKey(sysAttendHisCategoryForm.getFdId());
        Set<String> orgIds=new HashSet<>();
        if(hisCategory !=null){
            //历史考勤组变更之前所有考勤对象
            List<String> hisCategoryIds =Lists.newArrayList(hisCategory.getFdId());
            orgIds.addAll(getCategoryTargetOrgChangeByHisCategoryId(hisCategoryIds));
            orgIds.addAll(getCategoryTargetByHisCategoryId(hisCategoryIds));

            //清除原考勤组的考勤对象
            deleteCategoryTarget(hisCategory.getFdId());
            hisCategory.setFdManager(category.getFdManager());
            //配置信息因为使用场景少，这里使用外键关联
            SysAttendHisCategoryContent categoryContent=hisCategory.getFdCategoryContentNew();
            categoryContent.setFdCategoryContent(contentJson);

            hisCategory.setFdCategoryContentNew(categoryContent);
            hisCategory.setFdIsAvailable(Boolean.TRUE);
            //外勤状态
            hisCategory.setFdOsdReviewType(category.getFdOsdReviewType());
            this.update(hisCategory);
            // 2)处理考勤组的考勤对象
            this.addTarget(hisCategory,category,hisCategory.getFdBeginTime(),hisCategory.getFdEndTime());

            //历史考勤组变更之后所有考勤对象
            orgIds.addAll(getCategoryTargetOrgChangeByHisCategoryId(hisCategoryIds));
            orgIds.addAll(getCategoryTargetByHisCategoryId(hisCategoryIds));
        }
        // 3)清空相关缓存
        //清空假日的缓存
        KmssCache cache = new KmssCache(SysTimeHoliday.class);
        String timeHolidayMapKey = String.format(CategoryUtil.HOLIDAY_CACHE_MAP_KEY, sysAttendHisCategoryForm.getFdId());
        cache.put(timeHolidayMapKey, null);

        String timeHolidayDayMapKey = String.format(CategoryUtil.HOLIDAY_DAY_CACHE_MAP_KEY, sysAttendHisCategoryForm.getFdId());
        cache.put(timeHolidayDayMapKey, null);
        //清空历史考勤组的缓存
        //清理考勤组与人员的关系缓存；只清除该考勤组时间区间内的缓存
        SysAttendUserCacheUtil.clearUserCache(Lists.newArrayList(orgIds.iterator()),AttendUtil.getDate(hisCategory.getFdBeginTime(),-1));
        //清理历史考勤组 缓存
        CategoryUtil.HIS_CATEGORY_CACHE_MAP.remove(hisCategory.getFdId());
        //清理考勤组的缓存 缓存
        CategoryUtil.CATEGORY_CACHE_MAP.remove(hisCategory.getFdId());
        //清理人员的排班情况 缓存
        CategoryUtil.USER_WORKTIME_CACHE_MAP.clear();
        CategoryUtil.CATEGORY_USERIDS_CACHE_MAP.clear();
        CategoryUtil.CATEGORY_USERS_CACHE_MAP.clear();
    }
    private String [] updateTable={"sys_attend_stat","sys_attend_stat_detail","sys_attend_stat_month","sys_attend_report_month","sys_attend_stat_period"
    };

    /**
     * 将历史打卡记录中的 所属考勤组ID修改为最新的
     * @param categoryId 原来的考勤组ID
     * @param newCategoryId 当前最新的考勤组ID
     */
    @Override
    public void updateHisAttendMain(String categoryId,String newCategoryId,boolean isConvert)throws Exception{
        if(StringUtil.isNull(categoryId) || StringUtil.isNull(newCategoryId)){
            logger.error("修改历史考勤数据所属考勤组 错误，参数为空");
            return;
        }
        String sql ="";
        Query query = null;
        if(isConvert) {
            sql = "update sys_attend_main  set fd_category_his_id=:hisId,fd_category_id=null where fd_category_id =:cateId";
            query = this.getBaseDao().getHibernateSession().createSQLQuery(sql);
            query.setParameter("hisId", newCategoryId);
            query.setParameter("cateId", categoryId);
            query.executeUpdate();
        }else {
            sql = "update sys_attend_main  set fd_category_his_id=:hisId where fd_category_his_id =:cateId";
            query = this.getBaseDao().getHibernateSession().createSQLQuery(sql);
            query.setParameter("hisId", newCategoryId);
            query.setParameter("cateId", categoryId);
            query.executeUpdate();
        }
        //更新表结构中的考勤组ID
        for (String tableName : updateTable) {
            sql = "update " + tableName + "  set fd_category_id=:hisId where fd_category_id =:cateId";
            query = this.getBaseDao().getHibernateSession().createSQLQuery(sql);
            query.setParameter("hisId", newCategoryId);
            query.setParameter("cateId", categoryId);
            query.executeUpdate();
        }
        sql = "update sys_attend_syn_ding  set fd_group_id=:hisId where fd_group_id =:fdId";
        query = this.getBaseDao().getHibernateSession().createSQLQuery(sql);
        query.setParameter("hisId", newCategoryId);
        query.setParameter("fdId", categoryId);
        query.executeUpdate();

        //报表需要单独处理
        String reportSql = "select fd_id,fd_category_ids from sys_attend_report where fd_category_ids like '%" + categoryId + "%'";
        Query reportQuery = this.getBaseDao().getHibernateSession().createSQLQuery(reportSql);
        List<Object[]> list = reportQuery.list();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Object[] info : list) {
                String fdId = (String) info[0];
                String fdCategroyIds = (String) info[1];
                if (StringUtil.isNotNull(fdCategroyIds)) {
                    fdCategroyIds = fdCategroyIds.replaceAll(categoryId, newCategoryId);
                    sql = "update  sys_attend_report set fd_category_ids=:hisId where fd_id =:fdId";
                    query = this.getBaseDao().getHibernateSession().createSQLQuery(sql);
                    query.setParameter("hisId", fdCategroyIds);
                    query.setParameter("fdId", fdId);
                    query.executeUpdate();
                 }
            }
        }
    }

    /**
     * 保存考勤组为历史版本
     * @param category
     */
    @Override
    public SysAttendHisCategory addHisCategory(SysAttendCategory category,String contentJson) throws Exception {
        Date now=new Date();
        //1、考勤组信息转化成json存储
        //判断考勤组保存新版本、老版本
        SysAttendHisCategory oldCategory =getLastVersionFdId(category.getFdId());
        //当前时间的0时0分0秒
        Date beginDate= AttendUtil.getDate(now,1);
        if(CategoryUtil.ENABLE_FLAG.equals(category.getFdStatusFlag())){
            //立即生效
            beginDate= AttendUtil.getDate(now,0);
        }

        if(oldCategory ==null){
            //第一次取考勤组的生效时间为开始时间
            beginDate = category.getFdEffectTime();
        } else {
            //如果原考勤组是有效状态情况。才更新。否则不更新。插入新的历史考勤组
            //如果历史考勤组的开始时间 不是今日，则更新，并且重新同步历史考勤记录
            if( (category.getFdOldStatusFlag() ==null ||
                    Integer.valueOf(1).equals(category.getFdOldStatusFlag()) )
                    && oldCategory.getFdBeginTime().before(beginDate) && oldCategory.getFdEndTime().after(beginDate)) {
                oldCategory.setFdEndTime(beginDate);
                oldCategory.setDocAlterTime(now);
                super.update(oldCategory);

            }
            if(oldCategory !=null){
                //删除同一天编辑的考勤组记录
                deleteCategoryHis(category.getFdId(),beginDate);
                //这里如果开始日期是今日的，则删除后重新覆盖
                this.deleteCategoryTarget(category.getFdId(),beginDate);
            }
        }
        List<String> clearUserList =category.getFdTargets().stream().map(e -> e.getFdId()).collect(Collectors.toList());
        //清空人员跟考勤组的缓存关系
        SysAttendUserCacheUtil.clearUserCache(clearUserList, beginDate);
        SysAttendHisCategory hisCategory = null;
        //进行中状态才 产生历史记录，其他的将考勤对象有效时间设置为今日截止
        if(CategoryUtil.CATEGORY_FD_STATUS_TRUE.equals(category.getFdStatus())) {
            hisCategory = new SysAttendHisCategory();
            hisCategory.setFdName(category.getFdName());
            hisCategory.setFdType(category.getFdType());
            hisCategory.setFdCategoryId(category.getFdId());

            SysAttendHisCategoryContent categoryContent=new SysAttendHisCategoryContent();
            categoryContent.setFdCategoryContent(contentJson);
            getSysAttendCategoryContentDao().add(categoryContent);
            hisCategory.setFdCategoryContentNew(categoryContent);
            hisCategory.setFdOsdReviewType(category.getFdOsdReviewType());
            hisCategory.setFdBeginTime(beginDate);
            hisCategory.setFdEndTime(CategoryUtil.getMaxDate());
            hisCategory.setFdIsAvailable(Boolean.TRUE);
            hisCategory.setFdManager(category.getFdManager());
            super.add(hisCategory);
        }
        if(oldCategory !=null) {
            //将现有的考勤对象的结束时间修改为本次的开始时间。
            this.updateCategoryTarget(oldCategory.getFdId(), beginDate);
        }
        //2、考勤对象的存储
        addTarget(hisCategory,category,beginDate,null);

        return hisCategory;
    }

    /**
     * 重新统计考勤信息
     * 根据历史考勤组重新统计打卡时间
     * @param oldCategory 考勤组信息
     * @param orgId 组织ID
     * @param dateList 日期
     */
    private void restat(SysAttendHisCategory oldCategory,String orgId, List<Date> dateList) {
        try {
            if(dateList.isEmpty()){
                return;
            }
            Set<String> orgList = new HashSet<>();
            if(StringUtil.isNull(orgId)) {
                //处理的考勤组ID
                List<String> categoryIds = Lists.newArrayList(oldCategory.getFdId());
                for (Date startDate : dateList) {
                    orgList.addAll(getSysAttendCategoryService().getAttendPersonIds(categoryIds, startDate, false));
                }

            }else{
                orgList.add(orgId);
            }
            if (orgList.isEmpty()) {
                return;
            }
            //事务提交以后执行
            multicaster.attatchEvent(
                    new EventOfTransactionCommit(StringUtils.EMPTY),
                    new IEventCallBack() {
                        @Override
                        public void execute(ApplicationEvent arg0)
                                throws Throwable {
                            AttendStatThread task = new AttendStatThread();
                            task.setDateList(dateList);
                            task.setOrgList(new ArrayList<>(orgList));
                            task.setFdMethod("restat");
                            task.setFdIsCalMissed("false");
                            AttendThreadPoolManager manager = AttendThreadPoolManager
                                    .getInstance();
                            if (!manager.isStarted()) {
                                manager.start();
                            }
                            manager.submit(task);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新考勤对象、排除对象的实效时间为当前考勤组失效时间
     * @param hisId 考勤对象的ID
     * @param newEndTime 失效时间
     */
    private void updateCategoryTarget(String hisId,Date newEndTime){

        //将原有的考勤对象的失效结束时间修改为本次的开始时间
        String updateTargetSql=" update SysAttendCategoryTargetNew set fdEndTime = :fdEndTimeNew "
                + " where hisCategoryId.fdId=:hisCategoryId and fdEndTime > :fdBeginTime and fdBeginTime < :fdEndTimeNew ";

        this.getSysAttendCategoryTargetNewDao().getHibernateSession().createQuery(updateTargetSql)
                .setParameter("fdEndTimeNew",newEndTime)
                .setParameter("hisCategoryId",hisId)
                .setParameter("fdBeginTime",new Date()).executeUpdate();

        //将原有的排除对象的失效结束时间修改为本次的开始时间
        String updateExcSql=" update SysAttendCategoryExcNew set fdEndTime = :fdEndTimeNew "
                + " where hisCategoryId.fdId=:hisCategoryId  and fdEndTime > :fdBeginTime and fdBeginTime < :fdEndTimeNew ";

        this.getSysAttendCategoryExcNewDao().getHibernateSession().createQuery(updateExcSql)
                .setParameter("fdEndTimeNew",newEndTime)
                .setParameter("hisCategoryId",hisId)
                .setParameter("fdBeginTime",new Date()).executeUpdate();
    }
    /**
     * 删除开始时间为具体的某一天之后的考勤对象信息
     * 历史考勤组如果是删除状态，则删除对应的签到对象
     * 大概意思是指还未发生的考勤 可以删除
     * @param fdHisCategoryId 历史考勤组ID
     */
    private void deleteCategoryTarget(String fdHisCategoryId){
        //考勤对象
        String updateTargetSql=" delete from sys_attend_category_target_new  where his_category_id_id=:fdHisCategoryId ";
        this.getSysAttendCategoryTargetNewDao().getHibernateSession().createSQLQuery(updateTargetSql)
                .setParameter("fdHisCategoryId",fdHisCategoryId).executeUpdate();
        //排除对象
        String updateExcSql=" delete from sys_attend_category_exc_new  where his_category_id_id=:fdHisCategoryId ";
        this.getSysAttendCategoryExcNewDao().getHibernateSession().createSQLQuery(updateExcSql)
                .setParameter("fdHisCategoryId",fdHisCategoryId) .executeUpdate();
    }
    /**
     * 删除开始时间为具体的某一天之后的考勤对象信息
     * 历史考勤组如果是删除状态，则删除对应的签到对象
     * 大概意思是指还未发生的考勤 可以删除
     * @param fdCategoryId 原始考勤组ID
     * @param newEndTime
     */
    private void deleteCategoryTarget(String fdCategoryId,Date newEndTime){
        //考勤对象
        String updateTargetSql=" delete from sys_attend_category_target_new  where fd_begin_time >= :fdBeginTime and his_category_id_id in (select a.fd_id from sys_attend_his_category a where a.fd_category_id=:fdCategoryId and a.fd_is_available=:fdIsAvailable) ";
        this.getSysAttendCategoryTargetNewDao().getHibernateSession().createSQLQuery(updateTargetSql)
                .setParameter("fdIsAvailable",false)
                .setParameter("fdCategoryId",fdCategoryId)
                .setParameter("fdBeginTime",newEndTime).executeUpdate();
        //排除对象
        String updateExcSql=" delete from sys_attend_category_exc_new  where fd_begin_time >= :fdBeginTime and his_category_id_id in (select a.fd_id from sys_attend_his_category a where a.fd_category_id=:fdCategoryId and a.fd_is_available=:fdIsAvailable) ";
        this.getSysAttendCategoryExcNewDao().getHibernateSession().createSQLQuery(updateExcSql)
                .setParameter("fdIsAvailable",false)
                .setParameter("fdCategoryId",fdCategoryId)
                .setParameter("fdBeginTime",newEndTime).executeUpdate();
    }
    /**
     * 删除开始时间为具体的某一天之后的考勤组信息
     * 大概意思是指还未发生的考勤 可以删除
     * @param fdCategoryId 原始考勤组
     * @param newEndTime 操作日期
     */
    private void deleteCategoryHis(String fdCategoryId,Date newEndTime){
        //将原有的考勤对象的失效结束时间修改为本次的开始时间
        String updateTargetSql=" update SysAttendHisCategory set fdIsAvailable=:fdIsAvailable,docAlterTime=:docAlterTime  where fdCategoryId=:fdCategoryId and fdBeginTime >= :fdBeginTime ";
        this.getBaseDao().getHibernateSession().createQuery(updateTargetSql)
                .setParameter("fdIsAvailable",Boolean.FALSE)
                .setParameter("fdCategoryId",fdCategoryId)
                .setParameter("docAlterTime",new Date())
                .setParameter("fdBeginTime",newEndTime).executeUpdate();

    }
    /**
     * 保存考勤组的考勤对象
     * @param newCategory 考勤组历史版本信息
     * @param category
     * @param beginDate 考勤组生效时间
     */
    private void addTarget(
            SysAttendHisCategory newCategory,
                          SysAttendCategory category,
                          Date beginDate,
                            Date endDate
    ) throws Exception {

        if(newCategory !=null) {
            //考勤组的考勤对象
            List<SysOrgElement> targets = category.getFdTargets();
            List<SysAttendCategoryExcNew> excNewList =new ArrayList<>();
            if (CollectionUtils.isNotEmpty(targets)) {
                for (SysOrgElement element : targets) {
                    //保存新的考勤对象信息
                    SysAttendCategoryTargetNew targetNew = new SysAttendCategoryTargetNew();
                    targetNew.setHisCategoryId(newCategory);
                    targetNew.setFdBeginTime(beginDate);
                    targetNew.setFdEndTime(endDate ==null?CategoryUtil.getMaxDate():endDate);
                    targetNew.setFdOrg(element);
                    this.getSysAttendCategoryTargetNewDao().add(targetNew);
                }
            }
            List<SysOrgElement> exTargets = category.getFdExcTargets();
            if (CollectionUtils.isNotEmpty(exTargets)) {
                for (SysOrgElement element : exTargets) {
                    //保存新的考勤排除对象信息
                    SysAttendCategoryExcNew targetNew = new SysAttendCategoryExcNew();
                    targetNew.setHisCategoryId(newCategory);
                    targetNew.setFdBeginTime(beginDate);
                    targetNew.setFdEndTime(endDate ==null?CategoryUtil.getMaxDate():endDate);
                    targetNew.setFdOrg(element);
                    excNewList.add(targetNew);
                }
            }
            //因为目前没有addBatch方法，循环添加吧
            if (CollectionUtils.isNotEmpty(excNewList)) {
                for (SysAttendCategoryExcNew targetNew: excNewList) {
                    this.getSysAttendCategoryExcNewDao().add(targetNew);
                }
            }
        }
    }
    private ISysOrgCoreService sysOrgCoreService;


    private ISysOrgCoreService getSysOrgCoreService() {
        if(sysOrgCoreService ==null){
            sysOrgCoreService= (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
        }
        return sysOrgCoreService;
    }

    private ISysAttendCategoryService getSysAttendCategoryService() {
        if(sysAttendCategoryService ==null){
            sysAttendCategoryService= (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
        }
        return sysAttendCategoryService;
    }
    private ISysAttendCategoryTargetNewDao getSysAttendCategoryTargetNewDao() {
        if(sysAttendCategoryTargetNewDao ==null){
            sysAttendCategoryTargetNewDao= (ISysAttendCategoryTargetNewDao) SpringBeanUtil.getBean("sysAttendCategoryTargetNewDao");
        }
        return sysAttendCategoryTargetNewDao;
    }
    private ISysAttendCategoryTargetChangeDao getSysAttendCategoryTargetChangeDao() {
        if(sysAttendCategoryTargetChangeDao ==null){
            sysAttendCategoryTargetChangeDao= (ISysAttendCategoryTargetChangeDao) SpringBeanUtil.getBean("sysAttendCategoryTargetChangeDao");
        }
        return sysAttendCategoryTargetChangeDao;
    }

    private ISysAttendCategoryExcNewDao getSysAttendCategoryExcNewDao() {
        if(sysAttendCategoryExcNewDao ==null){
            sysAttendCategoryExcNewDao= (ISysAttendCategoryExcNewDao) SpringBeanUtil.getBean("sysAttendCategoryExcNewDao");
        }
        return sysAttendCategoryExcNewDao;
    }

    @Override
    public void onApplicationEvent(SysOrgElementChangeEvent changeEvent) {
        if (changeEvent  !=null) {
            String orgId = changeEvent.getSysOrgElement().getFdId();
            Date now=new Date();
            //人员组织架构变更，去找其相关的考勤组。
            Date day=AttendUtil.getDate(now,0);
            try {
                if(StringUtil.isNotNull(changeEvent.getBeforeHierarchyId())){
                    //清理所有的考勤组下人员列表
                    CategoryUtil.CATEGORY_USERIDS_CACHE_MAP.clear();
                    CategoryUtil.CATEGORY_USERS_CACHE_MAP.clear();
                    //组织架构下所有人的考勤组所属缓存清除。不管该对象是何组织类型，其下面解析的人员不会变化；
                    SysAttendUserCacheUtil.clearUserCache(Lists.newArrayList(orgId),now);
                    return;
                }
                //昨天的历史考勤组
                SysAttendHisCategory hisCategory =getHisCategoryByUserLeveAndDate(changeEvent.getBeforeHierarchyId(),null,day,null,true);
                //变更后的历史考勤组
                SysAttendHisCategory hisCategoryTwo =getHisCategoryByUserLeveAndDate(changeEvent.getAfterHierarchyId(),null,day,null,true);
                if(hisCategory ==null || hisCategoryTwo ==null){
                    //组织架构下所有人的考勤组所属缓存清除。不管该对象是何组织类型，其下面解析的人员不会变化；
                    SysAttendUserCacheUtil.clearUserCache(Lists.newArrayList(orgId),now);
                    return;
                }
                if(hisCategory.getFdId().equals(hisCategoryTwo.getFdId())){
                    //相同的情况下，不清理人员-考勤组缓存
                    return;
                }
                //组织架构下所有人的考勤组所属缓存清除。不管该对象是何组织类型，其下面解析的人员不会变化；
                SysAttendUserCacheUtil.clearUserCache(Lists.newArrayList(orgId),now);

                SysOrgElement element = getSysOrgCoreService().findByPrimaryKey(orgId);
                //查询人员原来所在考勤组的所有历史版本，
                List<SysAttendHisCategory> hisCategories = getAllCategorys(hisCategory.getFdCategoryId());
                for (SysAttendHisCategory hisBean:hisCategories) {
                    if(hisBean.getFdId().equals(hisCategory.getFdId())){
                        continue;
                    }
                    //如果在原来的考勤组的版本内存在。
                    SysAttendHisCategory inHisCategory = getHisCategoryByUserLeveAndDate(changeEvent.getBeforeHierarchyId(),null,hisBean.getFdBeginTime(),null,true);
                    if(inHisCategory !=null && inHisCategory.getFdId().equals(hisBean.getFdId())){
                        //新增一条历史的
                        SysAttendCategoryTargetChange targetNew = new SysAttendCategoryTargetChange();
                        targetNew.setFdHisCategory(hisCategory);
                        targetNew.setFdBeginTime(hisBean.getFdBeginTime());
                        targetNew.setFdEndTime(hisBean.getFdEndTime());
                        targetNew.setFdOrg(element);
                        targetNew.setFdIsAvailable(Boolean.TRUE);
                        this.getSysAttendCategoryTargetChangeDao().add(targetNew);
                    }
                }
                boolean add =true;
                Date maxEndTime =null;

                List<SysAttendCategoryTargetChange>    hisTargetInfos = this.getCategoryTargetChangeInfo(orgId);
                if(CollectionUtils.isNotEmpty(hisTargetInfos)) {
                    for (SysAttendCategoryTargetChange targetInfo : hisTargetInfos) {
                        Date endTime = targetInfo.getFdEndTime();
                        if(endTime.getTime() == day.getTime()){
                            //今天变更多次 则不做任何处理
                            add =false;
                            if(hisCategoryTwo.getFdId().equals(targetInfo.getFdHisCategory().getFdId())){
                                //如果结束时间是今天，变更后，又变更回来。则设置为无效
                                targetInfo.setFdIsAvailable(Boolean.FALSE);
                                this.getSysAttendCategoryTargetChangeDao().update(targetInfo);
                            }
                            continue;
                        }
                        if(maxEndTime ==null){
                            maxEndTime =endTime;
                        } else if(endTime.getTime() > maxEndTime.getTime()){
                            maxEndTime = endTime;
                        }
                    }
                }
                if(add) {
                    Date newBeginDate =maxEndTime == null ? hisCategory.getFdBeginTime() : maxEndTime;
                    //记录变更之前的考勤组信息
                    SysAttendCategoryTargetChange targetNew = new SysAttendCategoryTargetChange();
                    targetNew.setFdHisCategory(hisCategory);
                    targetNew.setFdBeginTime(newBeginDate);
                    targetNew.setFdEndTime(day);
                    targetNew.setFdOrg(element);
                    targetNew.setFdIsAvailable(Boolean.TRUE);
                    this.getSysAttendCategoryTargetChangeDao().add(targetNew);
                    //组织架构下所有人的考勤组所属缓存清除。不管该对象是何组织类型，其下面解析的人员不会变化；
                    SysAttendUserCacheUtil.clearUserCache(Lists.newArrayList(orgId),AttendUtil.getDate(day,-1));
                }
                //清理所有的考勤组下人员列表
                CategoryUtil.CATEGORY_USERIDS_CACHE_MAP.clear();
                CategoryUtil.CATEGORY_USERS_CACHE_MAP.clear();
                //更新有效考勤记录到新的考勤组
                getSysAttendCategoryService().updateAttendMainRecord(hisCategoryTwo.getFdId(),Lists.newArrayList(orgId),null);
                //统计该组织对象下今天的考勤数据
                restat(hisCategoryTwo,orgId,Lists.newArrayList(day));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
