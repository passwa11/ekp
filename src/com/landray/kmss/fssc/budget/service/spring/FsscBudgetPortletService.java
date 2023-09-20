package com.landray.kmss.fssc.budget.service.spring;

import com.landray.kmss.fssc.budget.constant.FsscBudgetConstant;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 实现不属于业务模块的portlet数据获取
 * @author xiexx
 *
 */

public class FsscBudgetPortletService extends ExtendDataServiceImp {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    private IFsscBudgetDataService fsscBudgetDataService;

    public void setFsscBudgetDataService(IFsscBudgetDataService fsscBudgetDataService) {
        this.fsscBudgetDataService = fsscBudgetDataService;
    }

    private IFsscBudgetExecuteService fsscBudgetExecuteService;

    public void setFsscBudgetExecuteService(IFsscBudgetExecuteService fsscBudgetExecuteService) {
        this.fsscBudgetExecuteService = fsscBudgetExecuteService;
    }

    /**
     * 部门预算总额及排名靠前8个费用类型及其他费用
     * @param request
     * @return
     */
    public JSONObject getCostCenterAcountInfo(HttpServletRequest request) throws Exception{
        String fdCostCenterId=request.getParameter("fdCostCenterId");
        JSONObject rtnData=new JSONObject();
        SysOrgPerson user= UserUtil.getUser();
        Calendar cal=Calendar.getInstance();
        StringBuilder hql=new StringBuilder();
        if(StringUtil.isNull(fdCostCenterId)){
            hql.append("select distinct t.fdId from EopBasedataCostCenter t left join t.fdFirstCharger fdFirst   ");
            hql.append("left join t.fdSecondCharger fdSecond left join t.fdManager fdManagers   ");
            hql.append(" left join t.fdBudgetManager fdBudgets   where fdFirst.fdId=:userId ");
            hql.append(" or fdSecond.fdId=:userId or fdManagers.fdId=:userId or fdBudgets.fdId=:userId");
            List<Object[]> costCenterInfos=fsscBudgetExecuteService.getBaseDao().getHibernateSession().createQuery(hql.toString())
                    .setParameter("userId",user.getFdId()).list();
            if(!ArrayUtil.isEmpty(costCenterInfos)){
                hql=new StringBuilder();
                hql.append("select distinct t.fdId,t.fdName from EopBasedataCostCenter t  left join t.hbmParent parents where t.fdIsGroup=:noGroup and ("+ HQLUtil.buildLogicIN("parents.fdId",costCenterInfos)
                    +" or "+ HQLUtil.buildLogicIN("t.fdId",costCenterInfos)+")");
                costCenterInfos=fsscBudgetExecuteService.getBaseDao().getHibernateSession().createQuery(hql.toString())
                        .setParameter("noGroup","1").list();  //查找成本中心
                JSONArray costCenterArr=new JSONArray();
                for(Object[] obj:costCenterInfos){
                    JSONObject info=new JSONObject();
                    info.put(obj[0],obj[1]);
                    costCenterArr.add(info);
                }
                if(costCenterInfos.get(0).length>0){
                    Object[] obj=costCenterInfos.get(0);
                    fdCostCenterId=String.valueOf(obj[0]);
                }
                rtnData.put("costCenterInfo",costCenterArr);
            }
        }
        if(StringUtil.isNotNull(fdCostCenterId)){
            //统计该成本中心fd_type为1和4的记录，初始化和调整金额，总和为全部金额
            hql=new StringBuilder();
            hql.append("select sum(budgetExecute.fd_money)  from fssc_budget_data datas left join fssc_budget_execute budgetExecute on datas.fd_id=budgetExecute.fd_budget_id  ");
            hql.append(" where  (budgetExecute.fd_type=:init or budgetExecute.fd_type=:adjust)  ");
            hql.append(" and  budgetExecute.fd_cost_center_id=:costCenterId and datas.fd_budget_status<>:close ");
            hql.append(" and ((datas.fd_period_type='5' and datas.fd_year=:currentYear) or datas.fd_period_type is null) ");
            List moneyList=fsscBudgetExecuteService.getBaseDao().getHibernateSession().createNativeQuery(hql.toString())
                    .setParameter("costCenterId",fdCostCenterId)
                    .setParameter("init","1").setParameter("adjust","4")
                    .setParameter("close",FsscBudgetConstant.FSSC_BUDGET_STATUS_CLOSE)
                    .setParameter("currentYear","5"+cal.get(Calendar.YEAR)+"0000").list();
            rtnData.put("totalAcount",!ArrayUtil.isEmpty(moneyList)&&moneyList.get(0)!=null?FsscNumberUtil.doubleToUp(Double.parseDouble(moneyList.get(0)+"")):0.0); //成本中心下所有预算金额
            //统计该成本中心fd_type为2,3的记录，使用金额，包括占用
            hql=new StringBuilder();
            hql.append("select sum(budgetExecute.fd_money)  from fssc_budget_execute budgetExecute   left join fssc_budget_data datas  on datas.fd_id=budgetExecute.fd_budget_id ");
            hql.append(" where  (budgetExecute.fd_type=:used or budgetExecute.fd_type=:occu)  ");
            hql.append(" and  budgetExecute.fd_cost_center_id=:costCenterId and datas.fd_budget_status<>:close");
            hql.append(" and ((datas.fd_period_type='5' and datas.fd_year=:currentYear) or datas.fd_period_type is null) ");
            moneyList=fsscBudgetExecuteService.getBaseDao().getHibernateSession().createNativeQuery(hql.toString())
                    .setParameter("costCenterId",fdCostCenterId)
                    .setParameter("occu","2").setParameter("used","3")
                    .setParameter("close",FsscBudgetConstant.FSSC_BUDGET_STATUS_CLOSE)
                    .setParameter("currentYear","5"+cal.get(Calendar.YEAR)+"0000").list();
            rtnData.put("usedAcount",!ArrayUtil.isEmpty(moneyList)&&moneyList.get(0)!=null?FsscNumberUtil.doubleToUp(Double.parseDouble( moneyList.get(0)+"")):0.0); //成本中心下所有预算金额
            rtnData.put("itemData",getExpenseItemsAccount(fdCostCenterId));
        }else{
            rtnData.put("totalAcount",0.0);
        }
        rtnData.put("canUse", FsscNumberUtil.getSubtraction(rtnData.optDouble("totalAcount",0.0),rtnData.optDouble("usedAcount",0.0),2));
        return rtnData;
    }

    /**
     * 获取成本中心下费用类型
     * @param fdCostCenterId
     * @return
     * @throws Exception
     */
    public List<Object[]> getExpenseItemsAccount(String fdCostCenterId) throws Exception{
        List<Object[]> moneyList=new ArrayList<Object[]>();
        String sql="";
        StringBuilder hql=new StringBuilder();
        //只统计今年的预算，期间不限的不统计在内
        Calendar ca=Calendar.getInstance();
        int year=ca.get(Calendar.YEAR);  //当前年度
        //事前明细没有单独保存，从台账中获取信息
        if(FsscCommonUtil.checkHasModule("/fssc/fee/")){
            hql.append(" select item.fd_name,sum(executes.fd_money) money from fssc_fee_ledger detail,fssc_budget_execute executes,eop_basedata_expense_item item,fssc_budget_data datas ");
            hql.append(" where executes.fd_detail_id=detail.fd_detail_id and item.fd_id=detail.fd_expense_item_id and datas.fd_id=executes.fd_budget_id ");
            hql.append(" and datas.fd_year=:fdYear and datas.fd_period_type=:fdPeriodType and (executes.fd_type=:occu or executes.fd_type=:used) and detail.fd_cost_center_id=:fdCostCenterId group by item.fd_name ");
            sql=StringUtil.linkString(sql," union all ",hql.toString());
        }
        //成本中心在明细，直接关联明细表
        String[] detailModelNames= {"com.landray.kmss.fssc.expense.model.FsscExpenseDetail","com.landray.kmss.fssc.payment.model.FsscPaymentDetail",
                "com.landray.kmss.fssc.proapp.model.FsscProappDetail","com.landray.kmss.fssc.proapp.model.FsscProappChangeDetail"};
        SysDataDict dataDict=SysDataDict.getInstance();
        for(String detailModelName:detailModelNames){
            SysDictModel dictModel= dataDict.getModel(detailModelName);
            if(dictModel!=null){
                hql=new StringBuilder();
                hql.append("select item.fd_name,sum(executes.fd_money) money from "+dictModel.getTable()+" detail,fssc_budget_execute executes,eop_basedata_expense_item item ,fssc_budget_data datas ");
                hql.append(" where executes.fd_detail_id=detail.fd_id and item.fd_id=detail.fd_expense_item_id and datas.fd_id=executes.fd_budget_id ");
                hql.append(" and datas.fd_year=:fdYear and datas.fd_period_type=:fdPeriodType and (executes.fd_type=:occu or executes.fd_type=:used) and detail.fd_cost_center_id=:fdCostCenterId group by item.fd_name ");
                sql=StringUtil.linkString(sql," union all ",hql.toString());
            }
        }
        //成本中心在主表，要关联主表
        String[] mainModelArr= {"com.landray.kmss.fssc.purch.model.FsscPurchDemand","com.landray.kmss.fssc.purch.model.FsscPurchUse"};
        String[] detailModelArr= {"com.landray.kmss.fssc.purch.model.FsscPurchDemandDetail","com.landray.kmss.fssc.purch.model.FsscPurchUseDetail"};
        for(String detailModelName:detailModelNames){
            SysDictModel detailDictModel= dataDict.getModel(detailModelName);
            SysDictModel mainDictModel= dataDict.getModel(detailModelName);
            if(mainDictModel!=null&&detailDictModel!=null){
                hql=new StringBuilder();
                hql.append("select item.fd_name,sum(executes.fd_money) money from "+detailDictModel.getTable()+" detail,"+mainDictModel.getTable()+" main,fssc_budget_execute executes,eop_basedata_expense_item item ,fssc_budget_data datas ");
                hql.append(" where detail.doc_main_id=main.fd_id and executes.fd_detail_id=detail.fd_id and item.fd_id=detail.fd_expense_item_id and datas.fd_id=executes.fd_budget_id ");
                hql.append(" and datas.fd_year=:fdYear and datas.fd_period_type=:fdPeriodType and (executes.fd_type=:occu or executes.fd_type=:used) and detail.fd_cost_center_id=:fdCostCenterId group by item.fd_name ");
                sql=StringUtil.linkString(sql," union all ",hql.toString());
            }
        }
        if(StringUtil.isNotNull(sql)){
            sql="select t.fd_name,sum(t.money) money from ("+sql+") t group by t.fd_name order by sum(t.money) desc";
            moneyList=fsscBudgetExecuteService.getBaseDao().getHibernateSession().createNativeQuery(sql)
                    .setParameter("fdYear","5"+year+"0000").setParameter("fdCostCenterId",fdCostCenterId)
                    .setParameter("fdPeriodType",FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_YEAR)
                    .setParameter("occu",FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU)
                    .setParameter("used",FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_INUSE).list();
            if(moneyList.size()>8){
                List<Object[]> subList=moneyList.subList(7, moneyList.size());
                Double otherMoney=0.0;
                for(int n=0,size=moneyList.size();n<size;n++){
                    otherMoney=otherMoney+(moneyList.get(n)[1]!=null?((Double) moneyList.get(n)[1]):0.0);
                }
                moneyList=moneyList.subList(0,7);
                Object[] obj=new Object[2];
                obj[0]= ResourceUtil.getString("other.items.fee","fssc-budget");
                obj[1]=otherMoney;
                moneyList.add(obj);
            }
        }
        return moneyList;
    }

    /**
     * 统计9个项目预算使用情况
     * @param request
     * @return
     */
    public JSONObject getProjectAcountInfo(HttpServletRequest request) throws Exception{
        JSONObject rtnData=new JSONObject();
        Calendar cal=Calendar.getInstance();
        StringBuilder hql=new StringBuilder();
        //统计成本中心下的项目fd_type为1和4的记录，初始化和调整金额，总和为全部金额
        hql=new StringBuilder();
        hql.append("select project.fd_name,sum(case when budgetExecute.fd_type=:init or budgetExecute.fd_type=:adjust then budgetExecute.fd_money else 0 end) total, ");
        hql.append(" sum(case when budgetExecute.fd_type=:used or budgetExecute.fd_type=:occu then budgetExecute.fd_money else 0 end) used  ");
        hql.append(" from fssc_budget_data datas,fssc_budget_execute budgetExecute,eop_basedata_project project ");
        hql.append(" where datas.fd_id=budgetExecute.fd_budget_id and datas.fd_project_id=project.fd_id and datas.fd_budget_status<>:close ");
        hql.append(" and ((datas.fd_period_type='5' and datas.fd_year=:currentYear) or datas.fd_period_type is null  or datas.fd_period_type='' ) ");
        hql.append(" group by project.fd_name  order by sum( case when budgetExecute.fd_type=:used or budgetExecute.fd_type=:occu then budgetExecute.fd_money else 0 end) desc ");
        Query query=fsscBudgetExecuteService.getBaseDao().getHibernateSession().createNativeQuery(hql.toString())
                .setParameter("init","1").setParameter("adjust","4")
                .setParameter("used","3").setParameter("occu","2")
                .setParameter("close",FsscBudgetConstant.FSSC_BUDGET_STATUS_CLOSE)
                .setParameter("currentYear","5"+cal.get(Calendar.YEAR)+"0000");
        List<Object[]> projectList=query.list();
        if(!ArrayUtil.isEmpty(projectList)&&projectList.size()>20){
            projectList=projectList.subList(0,20);
        }
        rtnData.put("projectArr",JSONArray.fromObject(projectList)); //成本中心下项目所有预算金额
        return rtnData;
    }
}
