package com.landray.kmss.sys.attend.service.business;

import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.time.interfaces.HoursField;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

import java.util.Date;

/**
 * @description: 签到时间计算服务
 * @author: wangjf
 * @time: 2022/4/1 2:43 下午
 * @version: 1.0
 */

public class SysAttendCountServiceImp implements ISysAttendCountService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendCountServiceImp.class);

    /**
     * 组织架构服务
     */
    private ISysOrgElementService getSysOrgElementService() {
        ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        return sysOrgElementService;
    }

    /**
     * 签到服务
     */
    private ISysAttendCategoryService getSysAttendCategoryService() {
        ISysAttendCategoryService sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
        return sysAttendCategoryService;
    }


    @Override
    public long getManHour(String id, long startTime, long endTime) throws Exception {
        return getManHour(getOrgElement(id), startTime, endTime);
    }

    @Override
    public long getManHour(SysOrgElement element, long startTime, long endTime) throws Exception {
        if (element != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("组织架构名称:{},组织架构id:{},开始时间:{},结束时间:{}", element.getFdName(), element.getFdId(), new Date(startTime), new Date(endTime));
            }
            SysAttendCategory sysAttendCategory = getSysAttendCategory(element);
            if (sysAttendCategory == null) {
                logger.warn("考勤组缺失无法计算,组织架构名称:{},组织架构ID:{},开始时间:{},结束时间:{}", element.getFdName(), element.getFdId(), new Date(startTime), new Date(endTime));
                return -1;
            }
            //获取具体的计算提供者
            AbstractBusinessCalculatorProvide businessCalculatorProvide = BusinessCalculatorProvideFactory.getBusinessCalculatorProvide(sysAttendCategory);
            if (businessCalculatorProvide != null) {
                return businessCalculatorProvide.getManHour(startTime, endTime);
            } else {
                logger.warn("考勤组信息缺失无法计算,组织架构名称:{},组织架构ID:{},开始时间:{},结束时间:{}", element.getFdName(), element.getFdId(), new Date(startTime), new Date(endTime));
            }
        } else {
            logger.warn("组织架构为空无法计算,开始时间:{},结束时间:{}", element.getFdName(), element.getFdId(), new Date(startTime), new Date(endTime));
        }
        return -1;
    }

    @Override
    public Date getEndTimeForWorkingHours(String id, Date startTime, int numberOfDate, HoursField field) throws Exception {
        return getEndTimeForWorkingHours(getOrgElement(id), startTime, numberOfDate, field);
    }

    @Override
    public Date getEndTimeForWorkingHours(SysOrgElement element, Date startTime, int numberOfDate, HoursField field) throws Exception {
        if (element != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("组织架构名称:{},组织架构id:{},开始时间:{}", element.getFdName(), element.getFdId(), startTime);
            }
            SysAttendCategory sysAttendCategory = getSysAttendCategory(element);
            if (sysAttendCategory == null) {
                logger.warn("考勤组缺失无法计算,组织架构名称:{},组织架构ID:{},开始时间:{},结束时间:{}", element.getFdName(), element.getFdId(), startTime);
                return startTime;
            }
            //获取具体的计算提供者
            AbstractBusinessCalculatorProvide businessCalculatorProvide = BusinessCalculatorProvideFactory.getBusinessCalculatorProvide(sysAttendCategory);
            if (businessCalculatorProvide != null) {
                return businessCalculatorProvide.getEndTimeForWorkingHours(startTime, numberOfDate, field);
            } else {
                logger.warn("考勤组信息缺失无法计算,组织架构名称:{},组织架构ID:{},开始时间:{},结束时间:{}", element.getFdName(), element.getFdId(), startTime);
            }
        } else {
            logger.warn("组织架构为空无法计算,开始时间:{},结束时间:{}", element.getFdName(), element.getFdId(), startTime);
        }
        return startTime;
    }

    @Override
    public int getWorkingDays(String id, long startTime, long endTime) throws Exception {
        return getWorkingDays(getOrgElement(id), startTime, endTime);
    }

    @Override
    public int getWorkingDays(SysOrgElement element, long startTime, long endTime) throws Exception {
        if (element != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("组织架构名称:{},组织架构id:{},开始时间:{},结束时间:{}", element.getFdName(), element.getFdId(), new Date(startTime), new Date(endTime));
            }
            SysAttendCategory sysAttendCategory = getSysAttendCategory(element);
            if (sysAttendCategory == null) {
                logger.warn("考勤组缺失无法计算,组织架构名称:{},组织架构ID:{},开始时间:{},结束时间:{}", element.getFdName(), element.getFdId(), new Date(startTime), new Date(endTime));
                return -1;
            }
            //获取具体的计算提供者
            AbstractBusinessCalculatorProvide businessCalculatorProvide = BusinessCalculatorProvideFactory.getBusinessCalculatorProvide(sysAttendCategory);
            if (businessCalculatorProvide != null) {
                return businessCalculatorProvide.getWorkingDays(startTime, endTime);
            } else {
                logger.warn("考勤组信息缺失无法计算,组织架构名称:{},组织架构ID:{},开始时间:{},结束时间:{}", element.getFdName(), element.getFdId(), new Date(startTime), new Date(endTime));
            }
        } else {
            logger.warn("组织架构为空无法计算,开始时间:{},结束时间:{}", element.getFdName(), element.getFdId(), new Date(startTime), new Date(endTime));
        }
        return -1;
    }

    /**
     * @param id
     * @description: 获取组织架构元素
     * @return: com.landray.kmss.sys.organization.model.SysOrgElement
     * @author: wangjf
     * @time: 2022/4/1 3:05 下午
     */
    private SysOrgElement getOrgElement(String id) throws Exception {
        SysOrgElement element = null;
        if (StringUtil.isNotNull(id)) {
            element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(id, SysOrgElement.class, true);
        }
        return element;
    }

    /**
     * @param element
     * @description: 获取签到组
     * @return: com.landray.kmss.sys.attend.model.SysAttendCategory
     * @author: wangjf
     * @time: 2022/4/1 3:15 下午
     */
    private SysAttendCategory getSysAttendCategory(SysOrgElement element) throws Exception {
        String categoryId = getSysAttendCategoryService().getAttendCategory(element);
        SysAttendCategory sysAttendCategory = CategoryUtil.getCategoryById(categoryId);
        if (sysAttendCategory != null) {
            return sysAttendCategory;
        }
        return null;
    }


}