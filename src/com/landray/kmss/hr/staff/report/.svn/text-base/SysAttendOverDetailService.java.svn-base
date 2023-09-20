package com.landray.kmss.hr.staff.report;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.config.service.IHrConfigOvertimeConfigService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 加班明细
 *
 * @author liuyang
 */
public class SysAttendOverDetailService {
    private static final int DATA_LENGTH = 16;

    private static String DATE_TIME = "HH:mm:ss";

    private static String DATE = "yyyy-MM-dd";

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

    private ISysAttendBusinessService sysAttendBusinessService;

    protected ISysAttendBusinessService getSysAttendBusinessService() {
        if (sysAttendBusinessService == null) {
            sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil.getBean("sysAttendBusinessService");
        }
        return sysAttendBusinessService;
    }

    private IHrConfigOvertimeConfigService hrConfigOvertimeConfigService;

    protected IHrConfigOvertimeConfigService getHrConfigOvertimeConfigService() {
        if (hrConfigOvertimeConfigService == null) {
            hrConfigOvertimeConfigService = (IHrConfigOvertimeConfigService) SpringBeanUtil.getBean("hrConfigOvertimeConfigService");
        }
        return hrConfigOvertimeConfigService;
    }

    private IKmReviewMainService kmReviewMainService;

    protected IKmReviewMainService getKmReviewMainService() {
        if (kmReviewMainService == null) {
            kmReviewMainService = (IKmReviewMainService) SpringBeanUtil.getBean("kmReviewMainService");
        }
        return kmReviewMainService;
    }

    private void setPersonInfo(String personId, String[] s) throws Exception {
        HrStaffPersonInfo personInfo = getHrStaffPersonInfoService().findByOrgPersonId(personId);
        if (personInfo != null) {
            //人员编号
            s[0] = personInfo.getFdStaffNo() != null ? personInfo.getFdStaffNo() : "";
            //姓名
            s[1] = personInfo.getFdName() != null ? personInfo.getFdName() : "";
            //一级部门
            s[2] = personInfo.getFdFirstLevelDepartment() != null ? personInfo.getFdFirstLevelDepartment().getFdName() : "";
            //二级部门
            s[3] = personInfo.getFdSecondLevelDepartment() != null ? personInfo.getFdSecondLevelDepartment().getFdName() : "";
            //三级部门
            s[4] = personInfo.getFdThirdLevelDepartment() != null ? personInfo.getFdThirdLevelDepartment().getFdName() : "";
        } else {
            SysOrgElement element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(personId);
            if (element != null) {
                s[1] = element.getFdName();
            }
        }
    }

    /**
     * 根据时间段，流程模板id获取流程审批数据
     */
    public List<String[]> getAttendDataByTempleteIds(Date start, Date end, List<String> targetIds) throws Exception {
        List<String[]> result = new ArrayList<>();
        List<String> personIds = AttendPersonUtil.expandToPersonIds(targetIds);
        for (String personId : personIds) {
            List<String[]> attendList = getPeopleAttendInfo(personId, start, end);
            if(!ArrayUtil.isEmpty(attendList)){
                result.addAll(attendList);
            }
        }
        return result;
    }

    private List<String[]> getPeopleAttendInfo(String fdPersonId, Date start, Date end) throws Exception {
        List<String[]> result = new ArrayList<>();
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysAttendBusiness.fdTargets.fdId=:fdId and sysAttendBusiness.fdBusStartTime>=:fdStartTime and sysAttendBusiness.fdBusEndTime <=:fdEndTime and sysAttendBusiness.fdType='6'");
        hqlInfo.setParameter("fdId", fdPersonId);
        hqlInfo.setParameter("fdStartTime", AttendUtil.getDate(start, 0));
        hqlInfo.setParameter("fdEndTime", AttendUtil.getEndDate(end, 0));
        List<SysAttendBusiness> stats = getSysAttendBusinessService().findList(hqlInfo);
        HQLInfo hqlInfo1 = new HQLInfo();
        hqlInfo1.setWhereBlock("sysAttendBusiness.fdTargets.fdId=:fdId and sysAttendBusiness.fdBusStartTime>=:fdStartTime and sysAttendBusiness.fdBusEndTime <=:fdEndTime and sysAttendBusiness.fdType='7'");
        hqlInfo1.setParameter("fdId", fdPersonId);
        hqlInfo1.setParameter("fdStartTime", AttendUtil.getDate(start, 0));
        hqlInfo1.setParameter("fdEndTime", AttendUtil.getEndDate(end, 0));
        List<SysAttendBusiness> stats1 = getSysAttendBusinessService().findList(hqlInfo1);
        for (SysAttendBusiness sysAttendBusiness : stats) {
            if(sysAttendBusiness.getFdBusStartTime() == null || sysAttendBusiness.getFdBusEndTime() == null) {
                continue;
            }
            String[] data = new String[DATA_LENGTH];
            //设置人员基本信息
            setPersonInfo(fdPersonId,data);
            String fdOverDate = DateUtil.convertDateToString(sysAttendBusiness.getFdBusStartTime(), "yyyy-MM-dd hh:mm");
            //加班类型
            JSONObject dataType = getHrConfigOvertimeConfigService().getOvertimeType(fdPersonId, fdOverDate);
            if (dataType.containsKey("type")) {
                String type = dataType.getString("type");
                switch (type){
                    case "1":  data[5] = "工作日";break;
                    case "2":  data[5] = "周末";break;
                    case "3":  data[5] = "节假日";break;
                    default: data[5] = "";
                }
            }
            //加班日期
            data[6] = DateUtil.convertDateToString(sysAttendBusiness.getFdBusStartTime(), DATE);
            //申请加班开始时间
            data[7] = DateUtil.convertDateToString(sysAttendBusiness.getFdBusStartTime(), DATE_TIME);
            //实际开始时间
            //申请加班结束时间
            data[9] = DateUtil.convertDateToString(sysAttendBusiness.getFdBusEndTime(), DATE_TIME);
            //实际结束时间
            int minu1 = (int)((sysAttendBusiness.getFdBusEndTime().getTime()-sysAttendBusiness.getFdBusStartTime().getTime())/60000);
            int minu2 = minu1-Integer.parseInt(sysAttendBusiness.getFdMealTimes());
            if(minu2<60)
            	data[11]="0";
            else
            	data[11]=String.valueOf(((minu2-minu2%30)/30)*0.5);
            //加班计划时长
//            data[11] = sysAttendBusiness.getFdOverApplyTimes() == null ? "" : String.valueOf(sysAttendBusiness.getFdOverApplyTimes());
            //加班实际时长
//            data[12] = sysAttendBusiness.getFdOverTimes() == null ? "" : String.valueOf(sysAttendBusiness.getFdOverTimes());
            long actualOverEndTime = 0;
            long actualOverBeginTime = 0;
            try{
            	actualOverEndTime = sysAttendBusiness.getFdActualOverEndTime().getTime();
            	actualOverBeginTime = sysAttendBusiness.getFdActualOverBeginTime().getTime();
            	
            }catch(Exception e){
            	for (SysAttendBusiness sysAttendBusiness1 : stats1) {
            		actualOverEndTime=sysAttendBusiness1.getFdBusEndTime().getTime();
            		actualOverBeginTime=sysAttendBusiness1.getFdBusStartTime().getTime();
            	}
            	e.printStackTrace();
            }finally{
                data[8] = DateUtil.convertDateToString(new Date(actualOverBeginTime), DATE_TIME);
                data[10] = DateUtil.convertDateToString(new Date(actualOverEndTime), DATE_TIME);
            	long busStartTime = sysAttendBusiness.getFdBusStartTime().getTime();
            	long busEndTime = sysAttendBusiness.getFdBusEndTime().getTime();
            	long unionTime = 0;
            	if(actualOverEndTime<=busEndTime&&actualOverEndTime>busStartTime&&actualOverBeginTime<busStartTime)
            		unionTime=actualOverEndTime-busStartTime;
            	if(actualOverEndTime<=busEndTime&&actualOverEndTime>busStartTime&&actualOverBeginTime>=busStartTime)
            		unionTime=actualOverEndTime-actualOverBeginTime;
            	if(actualOverBeginTime<busEndTime&&actualOverEndTime>=busEndTime&&actualOverBeginTime>=busStartTime)
            		unionTime=busEndTime-actualOverBeginTime;

            	if(busEndTime<=actualOverEndTime&&busEndTime>actualOverBeginTime&&busStartTime<=actualOverBeginTime)
            		unionTime=busEndTime-actualOverBeginTime;
            	if(busEndTime<=actualOverEndTime&&busEndTime>actualOverBeginTime&&busStartTime>=actualOverBeginTime)
            		unionTime=busEndTime-busStartTime;
            	if(busStartTime<actualOverEndTime&&busEndTime>=actualOverEndTime&&busStartTime>=actualOverBeginTime)
            		unionTime=actualOverEndTime-busStartTime;
            int minu = (int) (unionTime/60000-Integer.parseInt(sysAttendBusiness.getFdMealTimes()));
            if(minu<60)
            	data[12]="0";
            else
            	data[12]=String.valueOf(((minu-minu%30)/30)*0.5);
            }
//            if(Integer.parseInt(data[12])>1);
//            data[12]=String.valueOf(1+(int)((Integer.parseInt(data[12])-1)/0.5)*0.5);
            //加班原因
            String reason = getOverReasonByProcess(sysAttendBusiness.getFdProcessId());
            data[13] = StringUtil.isNull(reason) ? "" : reason;
            //用餐时间(分钟)
            data[14] = sysAttendBusiness.getFdMealTimes() == null ? "" : String.valueOf(sysAttendBusiness.getFdMealTimes());
            //是否转加班费
            data[15] = "2".equals(sysAttendBusiness.getFdOverHandle()) ? "是" : "否";
            result.add(data);
        }
        return result;
    }

    /**
     * 获取加班原因
     * @param fdProcessId
     * @return
     * @throws Exception
     */
    private String getOverReasonByProcess(String fdProcessId) throws Exception {
        if (StringUtil.isNull(fdProcessId)) {
            return null;
        }
        String str = "";
        KmReviewMain kmReviewMain = (KmReviewMain) getKmReviewMainService().findByPrimaryKey(fdProcessId,KmReviewMain.class, true);
        if(null == kmReviewMain){
            return null;
        }
        Map<String, Object> map = kmReviewMain.getExtendDataModelInfo().getModelData();
        if (map.containsKey("fd_3af436a2d6dc40")) {
            str = (String) map.get("fd_3af436a2d6dc40");
        }

        if (map.containsKey("fd_3b1422d2ce3810")) {
            str = (String) map.get("fd_3b1422d2ce3810");
        }
        return str;
    }
}
