package com.landray.kmss.sys.attend.service.business;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.interfaces.HoursField;

import java.util.Date;

/**
 * @description: 签到时间计算服务
 * @author: wangjf
 * @time: 2022/4/1 2:50 下午
 * @version: 1.0
 */

public interface ISysAttendCountService {

    /**
     * @param id
     * @param startTime
     * @param endTime
     * @description: 根据组织架构id、开始时间和结束时间获取耗费的工时长（毫秒）
     * @return: long
     * @author: wangjf
     * @time: 2022/4/1 2:53 下午
     */
    long getManHour(String id, long startTime, long endTime) throws Exception;

    /**
     * @param element
     * @param startTime
     * @param endTime
     * @description: 根据组织架构、开始时间和结束时间获取耗费的工时长（毫秒）
     * @return: long
     * @author: wangjf
     * @time: 2022/4/1 2:54 下午
     */
    long getManHour(SysOrgElement element, long startTime, long endTime) throws Exception;

    /**
     * @param id
     * @param startTime
     * @param numberOfDate
     * @param field
     * @description: 根据组织架构id，开始时间和需要跳过的工时获取结束时间
     * @return: java.util.Date
     * @author: wangjf
     * @time: 2022/4/1 2:55 下午
     */
    Date getEndTimeForWorkingHours(String id, Date startTime, int numberOfDate, HoursField field) throws Exception;

    /**
     * @param element
     * @param startTime
     * @param numberOfDate
     * @param field
     * @description: 根据组织架构，开始时间和需要跳过的工时获取结束时间
     * @return: java.util.Date
     * @author: wangjf
     * @time: 2022/4/1 2:55 下午
     */
    Date getEndTimeForWorkingHours(SysOrgElement element, Date startTime, int numberOfDate, HoursField field) throws Exception;

    /**
     * @param id
     * @param startTime
     * @param endTime
     * @description: 根据组织架构id，开始时间和结束时间获取耗费多少个工作日
     * @return: int
     * @author: wangjf
     * @time: 2022/4/1 2:55 下午
     */
    int getWorkingDays(String id, long startTime, long endTime) throws Exception;

    /**
     * @param element
     * @param startTime
     * @param endTime
     * @description: 根据组织架构，开始时间和结束时间获取耗费多少个工作日
     * @return: int
     * @author: wangjf
     * @time: 2022/4/1 2:56 下午
     */
    int getWorkingDays(SysOrgElement element, long startTime, long endTime) throws Exception;
}
