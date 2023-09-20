package com.landray.kmss.hr.staff.report;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.attend.model.SysAttendStat;
import com.landray.kmss.sys.attend.service.ISysAttendStatService;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.time.model.SysTimeLeaveConfig;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 统计考勤异常信息
 * @author liuyang
 */
public class SysAttendPersonReportService {
    private static final Logger log = LoggerFactory.getLogger(SysAttendPersonReportService.class);
    private static final int DATA_LENGTH = 9;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private IHrStaffPersonInfoService hrStaffPersonInfoService;

    protected IHrStaffPersonInfoService getHrStaffPersonInfoService() {
        if (hrStaffPersonInfoService == null) {
            hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil.getBean("hrStaffPersonInfoService");
        }
        return hrStaffPersonInfoService;
    }

    private ISysOrgElementService sysOrgElementService;

    protected ISysOrgElementService getSysOrgElementService() {
        if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

    private ISysAttendStatService sysAttendStatService;

    public ISysAttendStatService getSysAttendStatService() {
        if (sysAttendStatService == null) {
            sysAttendStatService = (ISysAttendStatService) SpringBeanUtil.getBean("sysAttendStatService");
        }
        return sysAttendStatService;
    }

    private String[] getPersonInfo(String personId) throws Exception {
        String[] s = new String[DATA_LENGTH];
        HrStaffPersonInfo personInfo = getHrStaffPersonInfoService().findByOrgPersonId(personId);
        if (personInfo != null) {
            //一级部门
            s[0] = personInfo.getFdFirstLevelDepartment() != null ? personInfo.getFdFirstLevelDepartment().getFdName() : "";
            //二级部门
            s[1] = personInfo.getFdSecondLevelDepartment() != null ? personInfo.getFdSecondLevelDepartment().getFdName() : "";
            //三级部门
            s[2] = personInfo.getFdThirdLevelDepartment() != null ? personInfo.getFdThirdLevelDepartment().getFdName() : "";
            //姓名
            s[3] = personInfo.getFdName();
            //人员编号
            s[4] = personInfo.getFdStaffNo();
        } else {
            SysOrgElement element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(personId);
            if (element != null) {
                s[3] = element.getFdName();
            }
        }
        return s;
    }

    /**
     * 根据时间段，流程模板id获取流程审批数据
     */
    public List<String[]> getAttendDataByTempleteIds(Date start, Date end, List<String> targetIds) throws Exception {
        List<String[]> result = new ArrayList<>();
        List<String> personIds = AttendPersonUtil.expandToPersonIds(targetIds);
        for (String person : personIds) {
            String[] s = getPersonInfo(person);
            setPeopleAttendInfo(s, person, start, end);
            //缺卡次数大于0，才显示
            if(Integer.parseInt(s[6]) > 0){
                result.add(s);
            }
        }
        return result;
    }

    private void setPeopleAttendInfo(String[] data, String fdPersonId, Date start, Date end) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysAttendStat.docCreator.fdId=:fdId and sysAttendStat.fdDate>=:fdStartTime and sysAttendStat.fdDate<:fdEndTime");
        hqlInfo.setParameter("fdId", fdPersonId);
        hqlInfo.setParameter("fdStartTime", AttendUtil.getDate(start, 0));
        hqlInfo.setParameter("fdEndTime", AttendUtil.getEndDate(end, 0));
        List<SysAttendStat> stats = getSysAttendStatService().findList(hqlInfo);
        for (SysAttendStat sysAttendStat : stats) {
            //无刷卡天数
            if (sysAttendStat.getFdMissed()) {
                data[5] = data[5] == null ? "1" : String.valueOf(Integer.parseInt(data[5]) + 1);
            }
            //缺卡次数
            if (StringUtil.isNull(data[6])) {
                data[6] = String.valueOf(sysAttendStat.getFdMissedCount());
            } else {
                data[6] = String.valueOf(sysAttendStat.getFdMissedCount() + Integer.parseInt(data[6]));
            }
            String fdOffCountDetail = sysAttendStat.getFdOffCountDetail();
            if (StringUtil.isNotNull(fdOffCountDetail)) {
                Float fdPersonLeaveDays = getLeaveHoursByOffType(fdOffCountDetail, "2");
                if (StringUtil.isNotNull(data[7])) {
                    data[7] = decimalFormat.format(Float.parseFloat(data[7]) + fdPersonLeaveDays);
                } else {
                    data[7] = decimalFormat.format(fdPersonLeaveDays);
                }

                Float fdSickLeaveDays = getLeaveHoursByOffType(fdOffCountDetail, "3");
                if (StringUtil.isNotNull(data[8])) {
                    data[8] = decimalFormat.format(Float.parseFloat(data[8]) + fdSickLeaveDays);
                } else {
                    data[8] = decimalFormat.format(fdSickLeaveDays);
                }
            }
        }
    }

    private Float getLeaveHoursByOffType(String fdOffDaysDetail, String offType) throws Exception {
        Float count = 0f;
        if (StringUtil.isNull(fdOffDaysDetail) || StringUtil.isNull(offType)) {
            return count;
        }
        SysTimeLeaveConfig rule = new SysTimeLeaveConfig();
        JSONObject statMonthJson = JSONObject.fromObject(fdOffDaysDetail);
        Object countObj = statMonthJson.get(offType);
        if (countObj instanceof JSONObject) {
            JSONObject json = (JSONObject) countObj;
            if (json.getInt("statType") == 3) {
                Number _count = (Number) json.get("count");
                count = _count.floatValue() / Float.parseFloat(rule.getDayConvertTime());
            } else {
                count = (float) json.getDouble("count");
            }
        } else if (countObj instanceof Number) {
            count = (float) countObj;
        }
        return count;
    }
}
