package com.landray.kmss.sys.attend.service;

import com.landray.kmss.sys.attend.forms.SysAttendHisCategoryForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.organization.model.SysOrgElement;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 考勤组的变更记录
 * @author wj
 */
public interface ISysAttendHisCategoryService extends IExtendDataService {
    /**
     * 根据原始考勤组ID，和负责人。查询其所有的历史版本的ID
     * @param oldCategoryId 原始考勤组ID
     * @param element 负责人
     * @return
     * @throws Exception
     */
    Set<String> getAllCategorys(List<String> oldCategoryId,SysOrgElement element) throws Exception;
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
    Set<String> getCategoryUsers(String oldCategoryId,Date beginDate,Date endDate) throws Exception;
    /**
     * 根据历史考勤组ID查询所有的有变化的组织对象
     * @param hisId
     * @return
     * @throws Exception
     */
    Set<SysOrgElement> getChangeOrgByHisId(String hisId) throws Exception;
    /**
     * 根据历史考勤组ID查询所有的组织对象
     * @param hisId
     * @return
     * @throws Exception
     */
    Set<SysOrgElement> getTargetOrgByHisId(String hisId) throws Exception;

    /**
     * 根据历史考勤组ID查询所有的排除组织对象
     * @param hisId
     * @return
     * @throws Exception
     */
    Set<SysOrgElement> getExcOrgByHisId(String hisId) throws Exception;
    /**
     * 根据组织和日期获取对应的考勤组配置信息
     * @param orgElement
     * @param date
     * @param fdType
     * @param isExc
     * @return
     * @throws Exception
     */
    SysAttendCategory getCategoryByUserAndDate(SysOrgElement orgElement,Date date,Integer fdType,Boolean isExc) throws Exception;
    /**
     * 将历史打卡记录中的 所属考勤组ID修改为最新的
     * @param categoryId 原来的考勤组ID
     * @param newCategoryId 当前最新的考勤组ID
     * @param isConvert 是否是数据库兼容转换
     */
    void updateHisAttendMain(String categoryId,String newCategoryId,boolean isConvert)throws Exception;

    /**
     * 编辑历史考勤组
     * @param sysAttendHisCategoryForm
     */
    void updateHisCategory(SysAttendHisCategoryForm sysAttendHisCategoryForm) throws Exception;
    /**
     * 验证考勤组中的考勤对象是否在其他考勤组中存在
     * @param category
     * @return
     */
    String checkCategoryTarget(SysAttendCategory category) throws Exception;
    /**
     * 根据原始考勤组ID和对应日期 获取在这之后所有的历史版本的考勤组ID
     * @param oldCategoryId 原始考勤组ID
     * @return
     * @throws Exception
     */
    Set<String> getAllCategorys(List<String> oldCategoryId) throws Exception;
    /**
     * 根据原始考勤组ID和对应日期 获取在这之后所有的历史版本的考勤组ID
     * @param oldCategoryId 原始考勤组ID
     * @return
     * @throws Exception
     */
    Set<String> getAllCategorys(List<String> oldCategoryId ,boolean fdIsAvailable) throws Exception;


    /**
     * 获取某个日期所有正在进行的考勤组列表
     * @param fdType 考勤组还是签到组
     * @param date 日期
     * @return
     * @throws Exception
     */
    Set<String> getAllCategorys(Date date, Integer fdType) throws Exception;
    /**
     * 转换考勤组信息
     * @param hisCategory
     * @return
     * @throws Exception
     */
    SysAttendCategory convertCategory(SysAttendHisCategory hisCategory) throws Exception;
    /**
     * 根本考勤组历史版本的ID获取对应的考勤组信息
     * @param categoryId
     * @return
     * @throws Exception
     */
    SysAttendCategory getCategoryById(String categoryId) throws Exception;
    /**
     * 根据组织和日期获取对应的考勤组配置信息
     * @param orgElement 组织对象
     * @param date 日期
     * @return
     * @throws Exception
     */
    SysAttendCategory getCategoryByUserAndDate(SysOrgElement orgElement, Date date,Integer fdType) throws Exception;

    /**
     * 保存考勤组为历史版本
     * @param category
     */
    SysAttendHisCategory addHisCategory(SysAttendCategory category,String contentJson) throws Exception;
    /**
     * 根据原始考勤组ID和日期 或者相关的考勤组的id
     * @param categoryId
     * @return
     */
    SysAttendHisCategory getLastVersionFdId(String categoryId,Date fdBeginTime) throws Exception;
    /**
     * 获取考勤组最后时间的历史版本FdId
     * @param categoryId
     * @return
     */
    SysAttendHisCategory getLastVersionFdId(String categoryId) throws Exception;
    /**
     * 根据某个考勤组配置ID以及时间
     * 获取该考勤对象列表
     * @param date 日期
     * @param oldCategoryId 原始考勤组ID
     * @param fdType 考勤组类型，【1是考勤组、2是签到组】
     * @return 排除组织对象列表
     * @throws Exception
     */
    List<SysOrgElement> getOldCategoryTargetOrg(String oldCategoryId, Date date,Integer fdType) throws Exception;
    /**
     * 根据某个考勤组配置ID以及时间
     * 获取该考勤对象列表
     * @param date 日期
     * @param hisCategoryId 考勤组配置ID
     * @param fdType 考勤组类型，【1是考勤组、2是签到组】
     * @return 排除组织对象列表
     * @throws Exception
     */
    List<SysOrgElement> getCategoryTargetOrg(String hisCategoryId, Date date,Integer fdType) throws Exception;
    /**
     * 根据某个考勤组配置ID以及时间
     * 获取该考勤对象是否在排除范围内
     * @param date 日期
     * @param hisCategoryId 考勤组配置ID
     * @param fdType 考勤组类型，【1是考勤组、2是签到组】
     * @return 排除组织对象列表
     * @throws Exception
     */
    List<SysOrgElement> getCategoryExcOrg(String hisCategoryId,Date date,Integer fdType) throws Exception;
    /**
     * 根据时间和
     * 获取该考勤对象列表
     * @param date 日期
     * @param fdType 考勤组类型，【1是考勤组、2是签到组】
     * @return 排除组织对象列表
     * @throws Exception
     */
    List<SysOrgElement> getCategoryTargetOrg( Date date,Integer fdType) throws Exception;
    /**
     * 根据某个考勤组配置ID以及时间
     * 获取该考勤对象是否在排除范围内
     * @param date 日期
     * @param fdType 考勤组类型，【1是考勤组、2是签到组】
     * @return 排除组织对象列表
     * @throws Exception
     */
    List<SysOrgElement> getCategoryExcOrg(Date date,Integer fdType) throws Exception;
    /**
     * 根据某个考勤组配置ID以及时间
     * 获取该考勤排除对象列表
     * @param date 日期
     * @param oldCategoryId 原始考勤组ID
     * @param fdType 考勤组类型，【1是考勤组、2是签到组】
     * @return 排除组织对象列表
     * @throws Exception
     */
    List<SysOrgElement> getOldCategoryExcOrg(String oldCategoryId, Date date,Integer fdType) throws Exception;
}
