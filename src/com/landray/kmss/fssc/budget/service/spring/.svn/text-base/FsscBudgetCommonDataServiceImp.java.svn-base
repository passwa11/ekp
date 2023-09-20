package com.landray.kmss.fssc.budget.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetSchemeService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budget.constant.FsscBudgetConstant;
import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDetailService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.StringUtil;

public class FsscBudgetCommonDataServiceImp implements IXMLDataBean{
	
	protected IFsscBudgetDetailService fsscBudgetDetailService;
	
	public void setFsscBudgetDetailService(IFsscBudgetDetailService fsscBudgetDetailService) {
		this.fsscBudgetDetailService = fsscBudgetDetailService;
	}
	
	protected IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService;
	
    public void setEopBasedataBudgetSchemeService(
			IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService) {
		this.eopBasedataBudgetSchemeService = eopBasedataBudgetSchemeService;
	}
    
    protected IFsscBudgetDataService fsscBudgetDataService;

	public void setFsscBudgetDataService(IFsscBudgetDataService fsscBudgetDataService) {
		this.fsscBudgetDataService = fsscBudgetDataService;
	}
	
	protected IFsscBudgetExecuteService fsscBudgetExecuteService;

	public void setFsscBudgetExecuteService(IFsscBudgetExecuteService fsscBudgetExecuteService) {
		this.fsscBudgetExecuteService = fsscBudgetExecuteService;
	}


	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnList=new ArrayList();
		String source=requestInfo.getParameter("source");
		if("detailMoney".equals(source)){
			//预算新建页面，根据页面信息获取期间的金额
			String fdSchemeId=requestInfo.getParameter("fdSchemeId");
			String fdYear=requestInfo.getParameter("fdYear");
			Map<String,List<String>> propertyMap=EopBasedataFsscUtil.getPropertyByScheme(fdSchemeId);
			List<String> inPropertyList=propertyMap.get("inPropertyList");
			List<String> notInPropertyList=propertyMap.get("notInPropertyList");
			HQLInfo hqlInfo=new HQLInfo();
			StringBuilder where=new StringBuilder(" fsscBudgetData.fdYear=:fdYear");
			hqlInfo.setParameter("fdYear", fdYear);
			where.append(" and fsscBudgetData.fdBudgetStatus=:fdBudgetStatus");
			hqlInfo.setParameter("fdBudgetStatus", FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE);
			for(String property:inPropertyList){
				String value=requestInfo.getParameter(property+"Id");
				where.append(" and fsscBudgetData."+property+".fdId=:"+property+"Id");
				hqlInfo.setParameter(property+"Id", value);
			}
			for(String property:notInPropertyList){
				where.append(" and fsscBudgetData."+property+" is null");
			}
			hqlInfo.setWhereBlock(where.toString());
			List<FsscBudgetData> budgetList=fsscBudgetDataService.findList(hqlInfo);
			Map rtnMap=new HashMap();
			Map<String,Double> rtnVal=fsscBudgetExecuteService.getExceuteMapByType(ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(budgetList, "fdId", ";")[0].split(";")), FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST);
			for(FsscBudgetData data:budgetList){
				rtnMap.put("money"+data.getFdPeriodType()+(StringUtil.isNotNull(data.getFdPeriod())?data.getFdPeriod():""), NumberUtil.roundDecimal(FsscNumberUtil.getAddition(rtnVal.containsKey(data.getFdId())?rtnVal.get(data.getFdId()):0.0, data.getFdMoney()), "##0.00"));
				if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_YEAR.equals(data.getFdPeriodType())
						&&!rtnMap.containsKey("fdYearRule")){
					rtnMap.put("fdYearRule", data.getFdRule());
					rtnMap.put("fdYearApply", data.getFdApply());
				}else if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_QUARTER.equals(data.getFdPeriodType())
						&&!rtnMap.containsKey("fdQuarterRule")){
					rtnMap.put("fdQuarterRule", data.getFdRule());
					rtnMap.put("fdQuarterApply", data.getFdApply());
				}else if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_MONTH.equals(data.getFdPeriodType())
						&&!rtnMap.containsKey("fdMonthRule")){
					rtnMap.put("fdMonthRule", data.getFdRule());
					rtnMap.put("fdMonthApply", data.getFdApply());
				}
			}
			rtnList.add(rtnMap);
		}
		return rtnList;
	}

}
