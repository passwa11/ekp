package com.landray.kmss.sys.attend.service;

import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.organization.model.SysOrgElement;

import java.util.Date;
import java.util.List;

/**
 * 考勤流程事件中的公共方法接口类
 * @author 王京
 */
public interface ISysAttendListenerCommonService {
    /**
     * 重新生成有效考勤记录时，先删除当天人员所有的有效考勤记录。重新生成
     * @param userIdList 人员列表
     * @param date 日期
     * @throws Exception
     */
    void deleteMainBatch(List<String> userIdList, Date date) throws Exception;
    /**
     * 异步清除待办
     * @param mainIds 有效考勤记录主文档ID
     */
    void removeUnSignNotify(List<String> mainIds);
    /**
     * 根据请假流程记录生成有效考勤记录
     * @param business
     * @throws Exception
     */
    void updateSysAttendMainByLeaveBis(SysAttendBusiness business)  throws Exception;
    /**
     * 根据请假流程记录生成有效考勤记录
     * @param business
     * @throws Exception
     */
    void updateSysAttendMainByLeaveBis(SysAttendBusiness business,Date statBeginDate,Date statEndDate)  throws Exception;

    /**
     * 根据人员时间查询对应的有效考勤记录
     * @param person
     * @param startTime
     * @param endTime
     * @param searchDay 是否查询跨天的打卡数据( true是查询,false是不查询)
     * @return
     * @throws Exception
     */
    List<SysAttendMain> getUserAttendMainByDay(SysOrgElement person, Date startTime, Date endTime,boolean searchDay)
            throws Exception;

    /**
     * 更新外出的有效考勤记录
     * @param business
     * @throws Exception
     */
    void updateSysAttendMainByOutgoing(SysAttendBusiness business)
            throws Exception;
    /**
     * 更新出差的有效考勤记录
     * @param business
     * @throws Exception
     */
    void updateSysAttendMainByBusiness(SysAttendBusiness business,Date statBeginDate,Date statEndDate)
            throws Exception;
    /**
     * 更新出差的有效考勤记录
     * @param business
     * @throws Exception
     */
    void updateSysAttendMainByBusiness(SysAttendBusiness business)
            throws Exception;
    /**
     * 查询流程是否在考勤模块存在
     * @param processId
     * @return
     * @throws Exception
     */
    boolean checkProcessIsHave(String processId,Integer ...processType)
            throws Exception;
}
