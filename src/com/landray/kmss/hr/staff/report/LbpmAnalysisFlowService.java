package com.landray.kmss.hr.staff.report;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 流程流转时间分析
 *
 * @author liuyang
 */
public class LbpmAnalysisFlowService {
    private static final Logger log = LoggerFactory.getLogger(LbpmAnalysisFlowService.class);

    //流程流转分析表数据长度
    private static final int DATA_LENGTH = 18;

    private IHrStaffPersonInfoService hrStaffPersonInfoService;

    protected IHrStaffPersonInfoService getHrStaffPersonInfoService() {
        if (hrStaffPersonInfoService == null) {
            hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil.getBean("hrStaffPersonInfoService");
        }
        return hrStaffPersonInfoService;
    }

    private IKmReviewTemplateService kmReviewTemplateService;

    protected IKmReviewTemplateService getKmReviewTemplateService() {
        if (kmReviewTemplateService == null) {
            kmReviewTemplateService = (IKmReviewTemplateService) SpringBeanUtil.getBean("kmReviewTemplateService");
        }
        return kmReviewTemplateService;
    }

    private ISysOrgElementService sysOrgElementService;

    protected ISysOrgElementService getSysOrgElementService() {
        if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

    /**
     * 根据时间段，流程模板id获取流程审批数据
     */
    public List<String[]> getApprovalDataByTempleteIds(Date start, Date end) throws Exception {
        List<String[]> result = new ArrayList<>();
        List<String> personIds = getApprovalPersonIds(start, end);
        for (String person : personIds) {
            String[] s = getPersonInfo(person);
            getPeopleApprovalTimeCountInfo(s, person, start, end);
            result.add(s);
        }
        Collections.sort(result, (o1, o2) -> {
            if(StringUtil.isNull(o1[17]) && StringUtil.isNull(o2[17])){
                if(Double.parseDouble(o1[17]) - Double.parseDouble(o2[17]) > 0){
                    return 1;
                }else{
                    return -1;
                }
            }else{
                return 0;
            }
        });
        return result;
    }

    /**
     * 根据person id 计算审批时间
     *
     * @param fdPersonId
     * @return
     */
    private void getPeopleApprovalTimeCountInfo(String[] data, String fdPersonId, Date start, Date end) throws Exception {
        String select = "";
        //差旅
        select += "sum(case when lbpmHistoryWorkitem.fdProcess.docSubject like '%差旅%' then 1 else 0 end), ";
        select += "sum(case when lbpmHistoryWorkitem.fdProcess.docSubject like '%差旅%' then lbpmHistoryWorkitem.fdCostTime else 0 end),";
        //假期
        select += "sum(case when lbpmHistoryWorkitem.fdProcess.docSubject like '%请假%' or lbpmHistoryWorkitem.fdProcess.docSubject like '%假期%' then 1 else 0 end),";
        select += "sum(case when lbpmHistoryWorkitem.fdProcess.docSubject like '%请假%' or lbpmHistoryWorkitem.fdProcess.docSubject like '%假期%' then lbpmHistoryWorkitem.fdCostTime else 0 end),";
        //签卡
        select += "sum(case when lbpmHistoryWorkitem.fdProcess.docSubject like '%签卡%' or lbpmHistoryWorkitem.fdProcess.docSubject like '%考勤异常%' then 1 else 0 end),";
        select += "sum(case when lbpmHistoryWorkitem.fdProcess.docSubject like '%签卡%' or lbpmHistoryWorkitem.fdProcess.docSubject like '%考勤异常%' then lbpmHistoryWorkitem.fdCostTime else 0 end) ,";
        //外出
        select += "sum(case when template.fdName in ('A12-外出申请','A33-saleforce外出申请') then 1 else 0 end),";
        select += "sum(case when template.fdName in ('A12-外出申请','A33-saleforce外出申请') then lbpmHistoryWorkitem.fdCostTime else 0 end),";
        //加班
        select += "sum(case when lbpmHistoryWorkitem.fdProcess.docSubject like '%加班%'  then 1 else 0 end),";
        select += "sum(case when lbpmHistoryWorkitem.fdProcess.docSubject like '%加班%'  then lbpmHistoryWorkitem.fdCostTime else 0 end),";

        select += "count(lbpmHistoryWorkitem.fdId), sum(lbpmHistoryWorkitem.fdCostTime)";
        HQLInfo info = new HQLInfo();
        info.setSelectBlock(select);
        info.setJoinBlock("left join KmReviewTemplate template on template.fdId=lbpmHistoryWorkitem.fdProcess.fdTemplateModelId");
        info.setWhereBlock("lbpmHistoryWorkitem.fdNode.fdNodeType='reviewNode' and lbpmHistoryWorkitem.fdNode.fdStatus='30' and lbpmHistoryWorkitem.fdFinishDate >:start and lbpmHistoryWorkitem.fdFinishDate <:end and lbpmHistoryWorkitem.fdHandler.fdId=:fdPersonId and " +
                "(lbpmHistoryWorkitem.fdProcess.docSubject like '%差旅%' or "+
                "(lbpmHistoryWorkitem.fdProcess.docSubject like '%请假%' or lbpmHistoryWorkitem.fdProcess.docSubject like '%假期%') or " +
                "(lbpmHistoryWorkitem.fdProcess.docSubject like '%签卡%' or lbpmHistoryWorkitem.fdProcess.docSubject like '%考勤异常%') or " +
                "template.fdName in ('A12-外出申请','A33-saleforce外出申请') or " +
                "lbpmHistoryWorkitem.fdProcess.docSubject like '%加班%')");
        info.setParameter("fdPersonId", fdPersonId);
        info.setParameter("start", start);
        info.setParameter("end", end);
        info.setModelName("com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmHistoryWorkitem");
        List<Object[]> objects = getKmReviewTemplateService().findList(info);
        if (!ArrayUtil.isEmpty(objects)) {
            Object[] object = objects.get(0);
            for (int i = 0; i < object.length; i++) {
                if (object[i] == null) {
                    data[i + 6] = "0";
                } else {
                    if (i % 2 == 1) {
                        int count = Integer.parseInt(object[i - 1].toString());
                        if (count > 0) {
                            DecimalFormat decimalFormat = new DecimalFormat("0.00");
                            data[i + 6] = decimalFormat.format(Long.parseLong(object[i].toString()) / (count * 1.0 * 1000 * 60));
                        }else{
                            data[i + 6] = object[i].toString();
                        }
                    } else {
                        data[i + 6] = object[i].toString();
                    }
                }
            }
        }
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
            //人员编号
            s[3] = personInfo.getFdStaffNo();
            //审批人
            s[4] = personInfo.getFdName();
            //岗位
            s[5] = personInfo.getFdOrgPost() != null ? personInfo.getFdOrgPost().getFdName() : "";
        } else {
            SysOrgElement element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(personId);
            if (element != null) {
                s[4] = element.getFdName();
            }
        }
        return s;
    }

    /**
     * 查询人员id
     */
    private List<String> getApprovalPersonIds(Date start, Date end) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setSelectBlock("distinct fdHandler.fdId");
        info.setWhereBlock("fdNode.fdNodeType ='reviewNode' and fdNode.fdStatus='30' and fdFinishDate >:start and fdFinishDate <:end and fdProcess.fdId in (" +
                            "select lbpmProcess.fdId from LbpmProcess lbpmProcess left join KmReviewTemplate template on lbpmProcess.fdTemplateModelId = template.fdId " +
                            "where lbpmProcess.docSubject like '%差旅%' or " +
                                  "lbpmProcess.docSubject like '%请假%' or " +
                                  "(lbpmProcess.docSubject like '%签卡%' or lbpmProcess.docSubject like '%考勤异常%') or " +
                                  "template.fdName in ('A12-外出申请','A33-saleforce外出申请') or " +
                                  "lbpmProcess.docSubject like '%加班%'" +
                             ")"
        );
        info.setParameter("start", start);
        info.setParameter("end", end);
        info.setModelName("com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmHistoryWorkitem");
        List<String> objects = getKmReviewTemplateService().findList(info);
        return objects;
    }
}
