package com.landray.kmss.sys.attend.service;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

/**
 * 考勤记录导出记录表
 *@author 王京
 *@date 2021-10-13
 */
public interface ISysAttendReportLogService extends IExtendDataService {
    /**
     * 每日汇总的导出
     * @param hqlInfo
     * @param request
     */
    void addSyncStatDetailDownReport(HQLInfo hqlInfo, HttpServletRequest request) throws Exception;
    /**
     * 每月汇总的导出
     * @param request
     */
    void addSyncStatMonthDownReport(HQLInfo hqlsql,HttpServletRequest request) throws Exception;
    /**
     * 每月汇总的导出 按照时间区间
     * @param request
     */
    void addSynceMonthPeriodDownReport(HQLInfo hqlsql,HttpServletRequest request) throws Exception;

    /**
     *  考勤报表下载
     * @param request
     */
    void addSyncReportDown(HQLInfo hqlsql,HttpServletRequest request) throws Exception;

    /**
            * 获取单个用户今日导出次数
     * @param type 1是每日汇总，2是每月汇总
     * @return
             */
    Integer getExportNumber(String type) throws Exception;
}
