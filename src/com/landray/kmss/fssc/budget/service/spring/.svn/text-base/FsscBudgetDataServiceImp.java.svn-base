package com.landray.kmss.fssc.budget.service.spring;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.eop.basedata.service.IEopBasedataExchangeRateService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budget.constant.FsscBudgetConstant;
import com.landray.kmss.fssc.budget.forms.FsscBudgetDataForm;
import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.fssc.budget.service.IFsscBudgetAdjustLogService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.fssc.budget.util.FsscBudgetUtil;
import com.landray.kmss.fssc.common.util.ExcelFileGenerator;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscBudgetDataServiceImp extends ExtendDataServiceImp implements IFsscBudgetDataService {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(FsscBudgetDataServiceImp.class);
	
	protected IFsscBudgetExecuteService fsscBudgetExecuteService;
	
	public void setFsscBudgetExecuteService(IFsscBudgetExecuteService fsscBudgetExecuteService) {
		if(fsscBudgetExecuteService==null){
			fsscBudgetExecuteService=(IFsscBudgetExecuteService) SpringBeanUtil.getBean("fsscBudgetExecuteService");
		}
		this.fsscBudgetExecuteService = fsscBudgetExecuteService;
	}
	
	protected IFsscBudgetAdjustLogService fsscBudgetAdjustLogService;
	
	public void setFsscBudgetAdjustLogService(
			IFsscBudgetAdjustLogService fsscBudgetAdjustLogService) {
		this.fsscBudgetAdjustLogService = fsscBudgetAdjustLogService;
	}
	
	protected IEopBasedataExchangeRateService eopBasedataExchangeRateService;
	
	public void setEopBasedataExchangeRateService(IEopBasedataExchangeRateService eopBasedataExchangeRateService) {
		this.eopBasedataExchangeRateService = eopBasedataExchangeRateService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscBudgetData) {
            FsscBudgetData fsscBudgetData = (FsscBudgetData) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscBudgetData fsscBudgetData = new FsscBudgetData();
        FsscBudgetUtil.initModelFromRequest(fsscBudgetData, requestContext);
        return fsscBudgetData;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscBudgetData fsscBudgetData = (FsscBudgetData) model;
    }

    @Override
    public List<FsscBudgetData> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetData.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetData> findByFdCompanyGroup(EopBasedataCompanyGroup fdCompanyGroup) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetData.fdCompanyGroup.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompanyGroup.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetData> findByFdCostCenter(EopBasedataCostCenter fdCostCenter) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetData.fdCostCenter.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCostCenter.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetData> findByFdBudgetItem(EopBasedataBudgetItem fdBudgetItem) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetData.fdBudgetItem.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdBudgetItem.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetData> findByFdProject(EopBasedataProject fdProject) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetData.fdProject.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdProject.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetData> findByFdInnerOrder(EopBasedataInnerOrder fdInnerOrder) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetData.fdInnerOrder.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdInnerOrder.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetData> findByFdWbs(EopBasedataWbs fdWbs) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetData.fdWbs.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdWbs.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetData> findByFdBudgetScheme(EopBasedataBudgetScheme fdBudgetScheme) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetData.fdBudgetScheme.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdBudgetScheme.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetData> findByFdCostCenterGroup(EopBasedataCostCenter fdCostCenterGroup) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetData.fdCostCenterGroup.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCostCenterGroup.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetData> findByFdCurrency(EopBasedataExchangeRate fdCurrency) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetData.fdCurrency.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCurrency.getFdId());
        return this.findList(hqlInfo);
    }
    /*************************************************
     * @pram messageJson  查询预算所需的维度信息
     * @pram budgetPeriod  查询预算期间，年：5，季度：3，月度：1
     * @pram fdYear  查询预算年份，如：2018
     * @pram fdPeriod  查询预算年份，结合budgetPeriod，对应的是第几个月或者第几个季度
     * **********************************************/
    @Override
    public JSONArray findBudget(JSONObject messageJson, String budgetPeriod, String fdYear, String fdPeriod) throws Exception{
    	JSONArray budgetArray=new JSONArray();
    	//成本中心
		String fdCostCenterIdOrCode=messageJson.containsKey("fdCostCenterId")?messageJson.getString("fdCostCenterId"):"";
		if(StringUtil.isNull(fdCostCenterIdOrCode)){
			//若是Id为空，则获取编码
			fdCostCenterIdOrCode=messageJson.containsKey("fdCostCenterCode")?messageJson.getString("fdCostCenterCode"):"";
		}
		EopBasedataCostCenter costCenter=null;
		if(StringUtil.isNotNull(fdCostCenterIdOrCode)){
			List<EopBasedataCostCenter> centerList=this.getBaseDao().getHibernateSession().createQuery("select eopBasedataCostCenter from EopBasedataCostCenter eopBasedataCostCenter where eopBasedataCostCenter.fdCode = :fdCostCenterIdOrCode or eopBasedataCostCenter.fdId = :fdCostCenterIdOrCode")
					.setParameter("fdCostCenterIdOrCode", fdCostCenterIdOrCode).list();
			if(!ArrayUtil.isEmpty(centerList)){
				//若是Id为空，则获取编码
				costCenter=centerList.get(0);
				//有成本中心默认查找成本中心组
				EopBasedataCostCenter parent=(EopBasedataCostCenter) costCenter.getFdParent();
				if(parent!=null){
					//若是参数未传自动获取，若传了已传输的为准
					if(!messageJson.containsKey("fdCostCenterGroupId")||messageJson.get("fdCostCenterGroupId")==null){
						messageJson.put("fdCostCenterGroupId", parent.getFdId());
					}
					if(!messageJson.containsKey("fdCostCenterGroupCode")||messageJson.get("fdCostCenterGroupCode")==null){
						messageJson.put("fdCostCenterGroupCode", parent.getFdId());
					}
				}
			}
		}
		//记账公司
		String fdCompanyIdOrCode=messageJson.containsKey("fdCompanyId")?messageJson.getString("fdCompanyId"):"";
		if(StringUtil.isNull(fdCompanyIdOrCode)){
			//若是Id为空，则获取编码
			fdCompanyIdOrCode=messageJson.containsKey("fdCompanyCode")?messageJson.getString("fdCompanyCode"):"";
		}
		EopBasedataCompany company=null;
		if(StringUtil.isNotNull(fdCompanyIdOrCode)){
			List<EopBasedataCompany> companyList=this.getBaseDao().getHibernateSession().createQuery("select eopBasedataCompany from EopBasedataCompany eopBasedataCompany where eopBasedataCompany.fdCode = :fdCompanyIdOrCode or eopBasedataCompany.fdId = :fdCompanyIdOrCode")
					.setParameter("fdCompanyIdOrCode", fdCompanyIdOrCode).list();
			if(!ArrayUtil.isEmpty(companyList)){
				//若是Id为空，则获取编码
				company=companyList.get(0);
				//有公司默认查找公司组组
				EopBasedataCompanyGroup group=company.getFdGroup();
				if(group!=null){
					//若是参数未传自动获取，若传了已传输的为准
					if(!messageJson.containsKey("fdCompanyGroupId")||messageJson.get("fdCompanyGroupId")==null){
						messageJson.put("fdCompanyGroupId", group.getFdId());
					}
					if(!messageJson.containsKey("fdCompanyGroupCode")||messageJson.get("fdCompanyGroupCode")==null){
						messageJson.put("fdCompanyGroupCode", group.getFdCode());
					}
				}
			}
		}
    	//match为匹配查询，execute为执行查询。预算滚动且不启用结转，匹配只需返回当前月、季、年可使用额，执行需返回所有本年预算
    	String queryType=messageJson.containsKey("queryType")?messageJson.getString("queryType"):""; 
    	String fdSchemeIdOrCode=messageJson.containsKey("fdBudgetSchemeId")?messageJson.get("fdBudgetSchemeId").toString():"";
    	if(StringUtil.isNull(fdSchemeIdOrCode)){
    		fdSchemeIdOrCode=messageJson.containsKey("fdBudgetSchemeCode")?messageJson.get("fdBudgetSchemeCode").toString():"";
    	}
    	EopBasedataBudgetScheme eopBasedataBudgetScheme=null;
    	if(StringUtil.isNotNull(fdSchemeIdOrCode)){
			List<EopBasedataBudgetScheme> schemeList=this.getBaseDao().getHibernateSession().createQuery("select t from EopBasedataBudgetScheme t where t.fdCode = :fdSchemeIdOrCode or t.fdId = :fdSchemeIdOrCode")
					.setParameter("fdSchemeIdOrCode", fdSchemeIdOrCode).list();
			if(!ArrayUtil.isEmpty(schemeList)){
				eopBasedataBudgetScheme=schemeList.get(0);
			}
		}
    	SysDataDict dataDict = SysDataDict.getInstance();
    	Map<String, SysDictCommonProperty> propMap = dataDict.getModel(FsscBudgetData.class.getName()).getPropertyMap();
    	if(eopBasedataBudgetScheme!=null){
    		Map<String,List<String>>  propertyMap=EopBasedataFsscUtil.getPropertyByScheme(eopBasedataBudgetScheme.getFdId());
        	List<String> inPropertyList=propertyMap.get("inPropertyList");
        	List<String> notInPropertyList=propertyMap.get("notInPropertyList");
        	int n=1;  //除了成本中心组包含成本中心情况下，其他情况都是n=1；
        	if(EopBasedataConstant.FSSC_IS_CONTAIN_YES.equals(EopBasedataFsscUtil.getSwitchValue("fdIsContain"))
        			&&FsscCommonUtil.isContain(eopBasedataBudgetScheme.getFdDimension(), "4;", ";")){
        		//启用了成本中心组包含成本中心开关且方案中包含成本中心维度
        		n=2;
        	}
        	for(int k=0;k<n;k++){
        		Calendar cal=Calendar.getInstance();
    			if(StringUtil.isNull(fdYear)){
    				fdYear=String.valueOf(cal.get(Calendar.YEAR));
    			}
    			if(k==1){
    				//启用了成本中心组包含成本中心开关,则第二次查询成本中心组维度，将原有成本中心维度替换
    				Collections.replaceAll(inPropertyList, "fdCostCenter", "fdCostCenterGroup");  //将成本中心维度替换为成本中心组维度
    				Collections.replaceAll(notInPropertyList, "fdCostCenterGroup", "fdCostCenter");  //将成本中心组维度替换为成本中心维度
    			}
        		HQLInfo hqlInfo=new HQLInfo();
        		StringBuilder whereBlock=new StringBuilder("fsscBudgetData.fdBudgetStatus=:fdBudgetStatus and fsscBudgetData.fdBudgetScheme.fdId=:fdBudgetSchemeId");
    			String period=eopBasedataBudgetScheme.getFdPeriod();
    			if(!FsscCommonUtil.isContain(period, "1;", ";")){//说明期间为不限，则不需要拼接期间条件
    				whereBlock.append(" and fsscBudgetData.fdYear=:fdYear");
    				hqlInfo.setParameter("fdYear", "5"+fdYear+"0000");
    				if(!FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_ALL.equals(budgetPeriod)){//若不是查全部则传对应的type，1：月度，3：季度，5年度
    	    			whereBlock.append(" and fsscBudgetData.fdPeriodType=:fdPeriodType");
    	    			hqlInfo.setParameter("fdPeriodType", budgetPeriod);
    	    			if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_MONTH.equals(budgetPeriod)){
    	    				whereBlock.append(" and fsscBudgetData.fdPeriod=:fdPeriod");
    	        			hqlInfo.setParameter("fdPeriod", fdPeriod);
    	    			}else if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_QUARTER.equals(budgetPeriod)){
    	    				whereBlock.append(" and fsscBudgetData.fdPeriod=:fdPeriod");
    	    				hqlInfo.setParameter("fdPeriod", fdPeriod);
    	    			}
    	    		}else if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_ALL.equals(budgetPeriod)&&StringUtil.isNotNull(fdPeriod)){
    	    			StringBuilder block=new StringBuilder(" and (fsscBudgetData.fdPeriod is null");
    	    			block.append(" or (fsscBudgetData.fdPeriodType=:fdQuarterPeriodType and  fsscBudgetData.fdPeriod=:fdQuarterPeriod)");
    	    			block.append(" or (fsscBudgetData.fdPeriodType=:fdMonthPeriodType and  fsscBudgetData.fdPeriod=:fdMonthPeriod)");
    	    			block.append(")");
    	    			whereBlock.append(block);
    	    			hqlInfo.setParameter("fdQuarterPeriodType", "3");
    	    			hqlInfo.setParameter("fdMonthPeriodType", "1");
    	    			hqlInfo.setParameter("fdQuarterPeriod", "0"+String.valueOf(Integer.parseInt(fdPeriod)/3));
    	    			hqlInfo.setParameter("fdMonthPeriod", fdPeriod);
    	    		}else{//是查全部的话拼接当前年度、当前季度、当前月度(若是不接转的，直接查找所有，从近往远扣，不指定对应的月度)
    	    			StringBuilder block=new StringBuilder(" and (fsscBudgetData.fdPeriod is null");
    	    			block.append(" or (fsscBudgetData.fdPeriodType=:fdQuarterPeriodType and  fsscBudgetData.fdPeriod=:fdQuarterPeriod)");
    	    			block.append(" or (fsscBudgetData.fdPeriodType=:fdMonthPeriodType and  fsscBudgetData.fdPeriod=:fdMonthPeriod)");
    	    			block.append(")");
    	    			whereBlock.append(block);
    	    			hqlInfo.setParameter("fdQuarterPeriodType", "3");
    	    			hqlInfo.setParameter("fdMonthPeriodType", "1");
    	    			hqlInfo.setParameter("fdQuarterPeriod", "0"+String.valueOf(cal.get(Calendar.MONTH)/3));
    	    			hqlInfo.setParameter("fdMonthPeriod", cal.get(Calendar.MONTH)>9?String.valueOf(cal.get(Calendar.MONTH)):("0"+cal.get(Calendar.MONTH)));
    	    		}
    			}
        		hqlInfo.setParameter("fdBudgetStatus", FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE);//查询启用的预算
        		hqlInfo.setParameter("fdBudgetSchemeId", eopBasedataBudgetScheme.getFdId());//查询启用的预算
        		for (String property : inPropertyList) {
        			if(propMap.containsKey(property)){
        				SysDictCommonProperty dict=propMap.get(property);
        				if(dict.getType().startsWith("com.landray.kmss.")){
            				if(messageJson.containsKey(property+"Id")){
            					whereBlock.append(" and fsscBudgetData."+property+".fdId=:"+property);
             	    			hqlInfo.setParameter(property, messageJson.get(property+"Id"));
            				}else if(messageJson.containsKey(property+"Code")){
            					whereBlock.append(" and fsscBudgetData."+property+".fdCode=:"+property);
             	    			hqlInfo.setParameter(property, messageJson.get(property+"Code"));
            				}else{//需要的维度未传值，直接设置null
            					whereBlock.append(" and fsscBudgetData."+property+" is null");
            				}
        				}else{
        					if(messageJson.containsKey(property)){
        						whereBlock.append(" and fsscBudgetData."+property+"=:"+property);
        	 	    			hqlInfo.setParameter(property, messageJson.get(property));
        					}else{//需要的维度未传值，直接设置null
        						whereBlock.append(" and fsscBudgetData."+property+" is null");
        					}
        				}
        			}
    			}
        		for (String property : notInPropertyList) {
        			if(propMap.containsKey(property)){
        				whereBlock.append(" and fsscBudgetData."+property+" is null");
        			}
        		}
        		hqlInfo.setWhereBlock(whereBlock.toString());
        		List<FsscBudgetData> budgetList=this.findList(hqlInfo);
        		if(StringUtil.isNull(queryType)){
        			queryType="match";
        		}
        		for(FsscBudgetData data:budgetList){
        			JSONObject budgetJson=new JSONObject();
        			StringBuilder subject=new StringBuilder();
        			String fdYearStr=data.getFdYear();
        			if(StringUtil.isNotNull(fdYearStr)){
        				subject.append(fdYearStr.substring(1, 5)+ResourceUtil.getString("enums.budget.period.type.year","fssc-budget")+"/");
        			}
        			if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_QUARTER.equals(data.getFdPeriodType())){
        				subject.append(Integer.parseInt(data.getFdPeriod())+1).append(ResourceUtil.getString("enums.budget.period.type.quarter","fssc-budget")).append("/");
        			}
        			if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_MONTH.equals(data.getFdPeriodType())){
        				subject.append(Integer.parseInt(data.getFdPeriod())+1).append(ResourceUtil.getString("enums.budget.period.type.month","fssc-budget")).append("/");
        			}
        			for (String property : inPropertyList) {
        				Object obj=PropertyUtils.getProperty(data, property);
        				if(obj!=null){
        					subject.append(PropertyUtils.getProperty(obj, "fdName")).append("/");
        				}
        			}
        			// 得到预算的可使用金额
        			Map<String, Double> budgetExecuteData = fsscBudgetExecuteService.getExecuteData(data.getFdId(),messageJson.optString("fdModelId"),messageJson.optString("fdDetailId"),queryType);
        			budgetJson.put("fdSubject", subject.toString());
        			budgetJson.put("fdBudgetId", data.getFdId());
        			budgetJson.put("fdBudgetItemId", data.getFdBudgetItem()!=null?data.getFdBudgetItem().getFdId():"");
        			budgetJson.put("fdRule", data.getFdRule());  //1为刚控，2为柔控
        			budgetJson.put("fdApply", data.getFdApply());  //1为固定，2为滚动
        			budgetJson.put("fdIsKnots", data.getFdIsKnots());  //0或者nul未结转，1：已结转
        			budgetJson.put("fdElasticPercent", data.getFdElasticPercent());  //弹性比例
        			if(!FsscCommonUtil.isContain(eopBasedataBudgetScheme.getFdDimension(), "2;", ";")) {  //不包含公司
						Double rate=1.0;
						if(company!=null) {
							rate=FsscBudgetUtil.getBudgetToBillRate(eopBasedataBudgetScheme,data.getFdCurrency()!=null?data.getFdCurrency().getFdId():"", company.getFdId());
						}
						budgetJson.put("fdCanUseAmount", FsscNumberUtil.getMultiplication(budgetExecuteData.get("canUseAmount"), rate, 2)); //预算数据币种转为单据公司对应预算币种的可使用，用于前端判断
        			}else {
        				budgetJson.put("fdCanUseAmount", budgetExecuteData.get("canUseAmount")); //预算数据币种转为单据公司对应预算币种的可使用，用于前端判断
        			}
        			budgetJson.put("canUseAmountDisplay", budgetExecuteData.get("canUseAmount")); //预算数据本身币种下的可使用，用于数字显示
        			budgetJson.put("fdAlreadyUsedAmount",  budgetExecuteData.get("alreadyUsedAmount"));  //已使用
        			budgetJson.put("fdOccupyAmount", budgetExecuteData.get("occupyAmount")); //占用
        			budgetJson.put("fdInitAmount", budgetExecuteData.get("initAmount"));  //初始化金额
        			budgetJson.put("fdTotalAmount", budgetExecuteData.get("totalAmount"));  //预算总金额
        			budgetJson.put("fdAdjustAmount", budgetExecuteData.get("adjustAmount")); //预算调整额
        			budgetJson.put("fdCurrenyId", data.getFdCurrency().getFdId()); //币种ID
        			budgetJson.put("fdSymbol", data.getFdCurrency().getFdAbbreviation());//币种符号
        			budgetJson.put("fdPeriodType", data.getFdPeriodType());
        			budgetJson.put("fdPeriod", data.getFdPeriod());
        			budgetArray.add(budgetJson);
        		}
        	}
    	}
    	return budgetArray;
    }

	/****************************************************
	 * 根据预算ID获取预算金额信息
	 * @param fdBudgetId  预算ID
	 * @param dataType，数据格式，string表示获取千分位格式，double表示获取数字格式
	 * *************************************************/
    @Override
    public Map getBudgetAcountInfo(String fdBudgetId, String dataType) throws Exception{
    	Map rtnMap=new ConcurrentHashMap<>();
    	// 得到预算的可使用金额
		Map<String, Double> budgetExecuteData = fsscBudgetExecuteService.getExecuteData(fdBudgetId,null,null,"match");
		rtnMap.put("fdCanUseAmount", "string".equals(dataType)
				?FsscNumberUtil.formatThousandth(budgetExecuteData.get("canUseAmount")):budgetExecuteData.get("canUseAmount")); //可使用
		rtnMap.put("fdAlreadyUsedAmount",  "string".equals(dataType)
				?FsscNumberUtil.formatThousandth(budgetExecuteData.get("alreadyUsedAmount")):budgetExecuteData.get("alreadyUsedAmount"));  //已使用
		rtnMap.put("fdOccupyAmount", "string".equals(dataType)
				?FsscNumberUtil.formatThousandth(budgetExecuteData.get("occupyAmount")):budgetExecuteData.get("occupyAmount")); //占用
		rtnMap.put("fdInitAmount", "string".equals(dataType)
				?FsscNumberUtil.formatThousandth(budgetExecuteData.get("initAmount")):budgetExecuteData.get("initAmount"));  //初始化金额
		rtnMap.put("fdTotalAmount", "string".equals(dataType)
				?FsscNumberUtil.formatThousandth(budgetExecuteData.get("totalAmount")):budgetExecuteData.get("totalAmount"));  //预算总金额
		rtnMap.put("fdAdjustAmount", "string".equals(dataType)
				?FsscNumberUtil.formatThousandth(budgetExecuteData.get("adjustAmount")):budgetExecuteData.get("adjustAmount")); //预算调整额
		rtnMap.put("rollMoney", "string".equals(dataType)
				?FsscNumberUtil.formatThousandth(budgetExecuteData.get("rollMoney")):budgetExecuteData.get("rollMoney")); //滚动金额
    	return rtnMap;
    }

	@Override
	public JSONObject updateBudgetStatus(HttpServletRequest request) throws Exception {
		JSONObject rtnData=new JSONObject();
		String operation=request.getParameter("operation");
		String fdBudgetStatus="";
		String value="1";  //无预算占用
		try {
			switch(operation){
				case "stop":
				fdBudgetStatus=FsscBudgetConstant.FSSC_BUDGET_STATUS_PAUSE;
				break;
				case "restart":
				fdBudgetStatus=FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE;
				break;
				case "close":
				fdBudgetStatus=FsscBudgetConstant.FSSC_BUDGET_STATUS_CLOSE;
				break;
			}
			String[] ids = request.getParameterValues("List_Selected");
			if (ids != null && ids.length > 0) {
				List<FsscBudgetData> dataList=this.findByPrimaryKeys(ids);
				if(!"restart".equals(operation)){
					Map<String,Integer> occuMap=findOccuBudget(ids);
					Iterator it=occuMap.entrySet().iterator();
					while(it.hasNext()){
						Entry entry=(Entry) it.next();
						if(((Integer)entry.getValue())>0){
							value="0";  //有预算占用直接跳出循环
							break;
						}
					}
				}
				rtnData.put("value", value);
				if ("1".equals(value)) {
					for(FsscBudgetData data:dataList){
						if(!FsscBudgetConstant.FSSC_BUDGET_STATUS_CLOSE.equals(data.getFdBudgetStatus())){
							data.setFdBudgetStatus(fdBudgetStatus);
							this.update(data);
						}
					}
				}
			}
		} catch (Exception e) {
			rtnData.put("value", "2");
			rtnData.put("message", "e");
			logger.error("", e);
		}
		return rtnData;
	}
	
	public Map<String,Integer> findOccuBudget(String[] ids) throws Exception{
		Map<String,Integer> rtnMap=new ConcurrentHashMap<>();
		StringBuilder sql=new StringBuilder();
		sql.append("select fd_budget_id,count(fd_id) from fssc_budget_execute where fd_type=:type and ");
		sql.append(HQLUtil.buildLogicIN("fd_budget_id", ArrayUtil.convertArrayToList(ids)));
		sql.append("  group by fd_budget_id");
		List<Object[]> objList=this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString())
				.setParameter("type", FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU).list();
		for(Object[] obj:objList){
			rtnMap.put(obj[0].toString(), obj[1]!=null?(Integer.parseInt(String.valueOf(obj[1]))):0);
		}
		return rtnMap;
	}

	@Override
	public void updateData(IExtendForm form, RequestContext requestContext)
			throws Exception {
		String fdId=requestContext.getParameter("fdId");
		FsscBudgetDataForm mainForm=(FsscBudgetDataForm) form;
		FsscBudgetData data=(FsscBudgetData) this.findByPrimaryKey(fdId, null, true);
		Double fdMoney=StringUtil.isNotNull(mainForm.getFdMoney())?Double.parseDouble(mainForm.getFdMoney()):0.0;
		JSONObject dataJson=new JSONObject();
		dataJson.put("fdModelId", fdId); //预算调整ID,查看页面是它本身ID
		dataJson.put("fdModelName", FsscBudgetData.class.getName()); //预算数据modelName
		dataJson.put("fdBudgetId", fdId); //预算ID
		dataJson.put("fdMoney", fdMoney);
		dataJson.put("fdPersonId", UserUtil.getUser().getFdId());
		dataJson.put("fdDesc", "");
		//create删除是没数据的没正常情况下，update先删除历史占用的，插入新的占用计算，publish和update类似
		fsscBudgetAdjustLogService.addAdjust(dataJson);
		//由于调整参数，预算执行也要用，故不再重新初始化
		dataJson=new JSONObject();
		dataJson.put("fdModelId", fdId); //预算调整ID,查看页面是它本身ID
		dataJson.put("fdModelName", FsscBudgetData.class.getName()); //预算数据modelName
		dataJson.put("fdBudgetId", fdId); //预算ID
		dataJson.put("fdMoney", fdMoney);
		dataJson.put("noCompany","true");
		dataJson.put("fdType", FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST);
		Map<String,List<String>> propertyMap=EopBasedataFsscUtil.getPropertyByScheme(data.getFdBudgetScheme().getFdId());
		for(String property:propertyMap.get("inPropertyList")){
			Object obj=PropertyUtils.getProperty(data, property);
			if(obj!=null){
				dataJson.put(property+"Id", PropertyUtils.getProperty(obj, "fdId"));
			}
		}
		fsscBudgetExecuteService.addFsscBudgetExecute(dataJson);
	}
	
	/**
	 * 校验传进来的预算ID是否全部都已结转，是则返回true，存在未结转的返回false
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	@Override
    public boolean checkBudgetIsKnots(List<String> idList) throws Exception{
		boolean rtn=Boolean.TRUE;
		if(!ArrayUtil.isEmpty(idList)){
			List<FsscBudgetData> dataList=this.findList(HQLUtil.buildLogicIN("fsscBudgetData.fdId", idList), null);
			for(FsscBudgetData data:dataList){
				if(!FsscBudgetConstant.FSSC_BUDGET_TRANSFER_YES.equals(data.getFdIsKnots())){
					rtn=Boolean.FALSE;
					break;
				}
			}
		}
		return rtn;
	}
	
	@Override
	public Page getBudgetDataPage(HttpServletRequest request, JSONObject params) throws Exception {
		String fdDimension=request.getParameter("fdDimension");
		if(StringUtil.isNotNull(fdDimension)){
			request.setAttribute("ledgerTitle", getLedgerTitle(fdDimension));
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setPageNo((Integer)params.get("pageno"));
		hqlInfo.setRowSize((Integer)params.get("rowsize"));
		String where="";
		/**预算方案**/
		String fdSchemeId=request.getParameter("fdSchemeId");
		where=buildWhereBlock(hqlInfo,where,"fdBudgetScheme",fdSchemeId);
		String[] dimensionArr=new String[]{"fdCompanyGroup","fdCompany","fdCostCenterGroup","fdCostCenter","fdProject","fdWbs","fdInnerOrder",
				"fdBudgetItem","fdDept","fdPerson"};
		for(String pro:dimensionArr){
			String value=request.getParameter(pro+"Id");
			if(StringUtil.isNotNull(value)){
				where=buildWhereBlock(hqlInfo,where,pro,value);
			}
		}
		/**年份**/
		String fdYear=request.getParameter("fdYear");
		if(StringUtil.isNotNull(fdYear)){
			where=StringUtil.linkString(where, " and ", "fsscBudgetData.fdYear=:fdYear");
			hqlInfo.setParameter("fdYear", "5"+fdYear+"0000");
		}
		/**期间类型**/
		String fdPeriodType=request.getParameter("fdPeriodType");
		if(StringUtil.isNotNull(fdPeriodType)&&!"0".equals(fdPeriodType)){
			where=StringUtil.linkString(where, " and ", "fsscBudgetData.fdPeriodType=:fdPeriodType");
			hqlInfo.setParameter("fdPeriodType", fdPeriodType);
			/**期间**/
			String fdPeriod=request.getParameter("fdPeriod");
			if(StringUtil.isNotNull(fdPeriod)){
				where=StringUtil.linkString(where, " and ", "fsscBudgetData.fdPeriod=:fdPeriod");
				hqlInfo.setParameter("fdPeriod", fdPeriod);
			}
		}
		/**预算状态**/
		String fdBudgetStatus=request.getParameter("fdBudgetStatus");
		if(StringUtil.isNotNull(fdBudgetStatus)){
			where=StringUtil.linkString(where, " and ", HQLUtil.buildLogicIN("fsscBudgetData.fdBudgetStatus",ArrayUtil.convertArrayToList(fdBudgetStatus.split(";"))));
		}else{
			where=StringUtil.linkString(where, " and ", "fsscBudgetData.fdBudgetStatus=:fdBudgetStatus");
			hqlInfo.setParameter("fdBudgetStatus", FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE);
		}
		
		String fdCostCenterParentId=request.getParameter("fdCostCenterParentId");
		hqlInfo.setJoinBlock(" left join fsscBudgetData.fdCostCenter.hbmParent parent ");
		if(StringUtil.isNotNull(fdCostCenterParentId)){
			where=StringUtil.linkString(where, " and ", " parent.fdId=:fdCostCenterParentId");
			hqlInfo.setParameter("fdCostCenterParentId", fdCostCenterParentId);

		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		hqlInfo.setOrderBy(" fsscBudgetData.fdCompany,parent.fdName,"
				+ "fsscBudgetData.fdCostCenter.fdName,fsscBudgetData.fdBudgetItem.fdCode,fsscBudgetData.fdBudgetItem.fdName,"
				+ "fsscBudgetData.fdYear,fsscBudgetData.fdPeriod nulls last");////按排序号排序，空值在后

		Page page=this.findPage(hqlInfo);
		List<FsscBudgetData> dataList=page.getList();
		List<String> budgetIdList=new ArrayList<String>();
		for(FsscBudgetData data:dataList){
			budgetIdList.add(data.getFdId());
		}
		Map<String,Map> dataMap=fsscBudgetExecuteService.getExecuteDataList(budgetIdList,null,"match");
		List resultList=new ArrayList<>();
		Map<String, SysDictCommonProperty> dictMap=SysDataDict.getInstance().getModel(FsscBudgetData.class.getName()).getPropertyMap();
		for(FsscBudgetData data:dataList){
			List result=new ArrayList<>();
			result.add(data.getFdId());
			List<String> propertyList=getLedgerProperty(fdDimension);
			//如果预算里面包含成本中心，那么需要带出关联的成本中心组·
			if(propertyList.contains("fdCostCenter")){
				propertyList.add(propertyList.indexOf("fdCostCenter"),"fdCostCenterParent");
			}
			//如果预算里面包含预算科目，那么需要带出关联的上级科目·
			if(propertyList.contains("fdBudgetItem")){
				propertyList.add(propertyList.indexOf("fdBudgetItem"),"fdBudgetItemParent");
			}
			for(String property:propertyList){
				if("fdCostCenterParent".equals(property)){
					if(data.getFdCostCenter()!=null&&data.getFdCostCenter().getFdParent()!=null){
						EopBasedataCostCenter parent=(EopBasedataCostCenter) data.getFdCostCenter().getFdParent();
						result.add(parent.getFdName());
					}else{
						result.add("");
					}
					continue;
				}
				if("fdBudgetItemParent".equals(property)){
					if(data.getFdBudgetItem()!=null&&data.getFdBudgetItem().getFdParent()!=null){
						EopBasedataBudgetItem parent=(EopBasedataBudgetItem) data.getFdBudgetItem().getFdParent();
						result.add(parent.getFdName());
					}else{
						result.add("");
					}
					continue;
				}
				if(dictMap.containsKey(property)){
					SysDictCommonProperty dict=dictMap.get(property);
					if(dict.getType().startsWith("com.landray.kmss.")){
						Object obj=PropertyUtils.getProperty(data, property);
						if(obj!=null){
							result.add(PropertyUtils.getProperty(obj, "fdName"));
						}else{
							result.add(null);
						}
					}else{
						result.add(PropertyUtils.getProperty(data, property));
					}
				}
			}
			result.add(StringUtil.isNotNull(data.getFdYear())?data.getFdYear().substring(1, 5):null);  //年度
			if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_QUARTER.equals(data.getFdPeriodType())){
				result.add(Integer.parseInt(data.getFdPeriod())+1);  //季度
			}else{
				result.add(null);
			}
			if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_MONTH.equals(data.getFdPeriodType())){
				result.add(Integer.parseInt(data.getFdPeriod())+1);  //月度
			}else{
				result.add(null);
			}
			if(dataMap.containsKey(data.getFdId())){
				// 得到预算的可使用金额
				Map<String, Double> budgetExecuteData = dataMap.get(data.getFdId());
				double initAmount=budgetExecuteData.containsKey("initAmount")?budgetExecuteData.get("initAmount"):0.0;
				double adjustAmount=budgetExecuteData.containsKey("adjustAmount")?budgetExecuteData.get("adjustAmount"):0.0;
				result.add(FsscNumberUtil.getAddition(initAmount, adjustAmount, 2));//预算总额
				result.add(initAmount);  //初始化金额
				result.add(budgetExecuteData.containsKey("alreadyUsedAmount")?budgetExecuteData.get("alreadyUsedAmount"):0.0);//已使用额度
				result.add(budgetExecuteData.containsKey("occupyAmount")?budgetExecuteData.get("occupyAmount"):0.0);//占用额度

				result.add(adjustAmount);//调整金额
				result.add(budgetExecuteData.containsKey("canUseAmount")?budgetExecuteData.get("canUseAmount"):0.0);//可使用计划额度
				result.add(data.getFdElasticPercent());//弹性比例
				result.add(data.getFdBudgetStatus());   //预算状态
				resultList.add(result);
			}
		}
		page.setList(resultList);
		return page;
	}

	
	@Override
	public Page getBudgetCountDataPage(HttpServletRequest request, JSONObject params) throws Exception {
		Integer pageno=(Integer)params.get("pageno");
		pageno=pageno==0?1:pageno;
		Integer rowsize=(Integer)params.get("rowsize");
		String where=" where main.fd_period is not null ";
		/**预算方案**/
		String fdSchemeId=request.getParameter("fdSchemeId");
		if(StringUtil.isNotNull(fdSchemeId)){
			where=StringUtil.linkString(where, " and ", " main.fd_budget_scheme_id=:fdSchemeId");
		}
		/**年份**/
		String fdYear=request.getParameter("fdYear");
		if(StringUtil.isNotNull(fdYear)){
			where=StringUtil.linkString(where, " and ", " main.fd_year=:fdYear");
		}
		String fdPeriod=request.getParameter("fdPeriod");
		if(StringUtil.isNotNull(fdPeriod)){
			where=StringUtil.linkString(where, " and ", " main.fd_period=:fdPeriod");
		}
		if(StringUtil.isNotNull(fdYear)){
			where=StringUtil.linkString(where, " and ", " main.fd_year=:fdYear");
		}
		String fdCostCenterGroupId=request.getParameter("fdCostCenterGroupId");
		if(StringUtil.isNotNull(fdCostCenterGroupId)){
			where=StringUtil.linkString(where, " and ", " centerGroup.fd_id=:fdCostCenterGroupId");
		}
		String fdBudgetItemId=request.getParameter("fdBudgetItemId");
		if(StringUtil.isNotNull(fdBudgetItemId)){
			where=StringUtil.linkString(where, " and ", " main.fd_budget_item_id=:fdBudgetItemId");
		}
		
		String sqlCount="select count(*) from (SELECT centerGroup.fd_id as groupid,main.fd_budget_item_id,main.fd_period,	GROUP_CONCAT( main.fd_id ) AS budgets  "
				+ " from fssc_budget_data main "
				+ "LEFT JOIN eop_basedata_cost_center center ON main.fd_cost_center_id = center.fd_id "
				+" LEFT JOIN (SELECT * from eop_basedata_cost_center WHERE fd_is_group='2') centerGroup "
				+ " ON centerGroup.fd_id = center.fd_parent_id "+where
				+ " GROUP BY centerGroup.fd_id,main.fd_budget_item_id,main.fd_period)  main ";
		Query queryCount=this.getBaseDao().getHibernateSession().createSQLQuery(sqlCount);

		String sql=" SELECT center.fd_name AS centerName,parent.fd_name as fd_parent_name,budgetItem.fd_name,main.fd_period,main.budgets from "
				+ " (SELECT centerGroup.fd_id as groupid,main.fd_budget_item_id,main.fd_period,	GROUP_CONCAT( main.fd_id ) AS budgets  "
				+ " from fssc_budget_data main"
				+ " LEFT JOIN eop_basedata_cost_center center ON main.fd_cost_center_id = center.fd_id "
				+" LEFT JOIN (SELECT * from eop_basedata_cost_center WHERE fd_is_group='2') centerGroup "
				+ " ON centerGroup.fd_id = center.fd_parent_id "+where
				+ " GROUP BY centerGroup.fd_id,main.fd_budget_item_id,main.fd_period) main"
				+ " LEFT JOIN eop_basedata_cost_center center ON main.groupid = center.fd_id "
				+ " LEFT JOIN eop_basedata_budget_item budgetItem ON main.fd_budget_item_id = budgetItem.fd_id "
				+ " LEFT JOIN eop_basedata_budget_item parent ON parent.fd_id = budgetItem.fd_parent_id "
				+ " ORDER BY center.fd_name,budgetItem.fd_code,budgetItem.fd_name,fd_period	limit " +((pageno-1)*rowsize)+","+rowsize;
		Query query=this.getBaseDao().getHibernateSession().createSQLQuery(sql);
		System.out.println(sql);
		if(StringUtil.isNotNull(fdSchemeId)){
			query.setParameter("fdSchemeId", fdSchemeId);
			queryCount.setParameter("fdSchemeId", fdSchemeId);

		}
		if(StringUtil.isNotNull(fdYear)){
			query.setParameter("fdYear", "5"+fdYear+"0000");
			queryCount.setParameter("fdYear", "5"+fdYear+"0000");

		}
		if(StringUtil.isNotNull(fdCostCenterGroupId)){
			query.setParameter("fdCostCenterGroupId", fdCostCenterGroupId);
			queryCount.setParameter("fdCostCenterGroupId", fdCostCenterGroupId);

		}
		if(StringUtil.isNotNull(fdBudgetItemId)){
			query.setParameter("fdBudgetItemId", fdBudgetItemId);
			queryCount.setParameter("fdBudgetItemId", fdBudgetItemId);

		}
		if(StringUtil.isNotNull(fdPeriod)){
			query.setParameter("fdPeriod", fdPeriod);
			queryCount.setParameter("fdPeriod", fdPeriod);

		}
		List<BigInteger> count=queryCount.list();

		List<Object[]> list = query.list();
		Map<String, SysDictCommonProperty> dictMap=SysDataDict.getInstance().getModel(FsscBudgetData.class.getName()).getPropertyMap();
		List resultList=new ArrayList<>();
		for (Object[] objs : list) {
			String budgetIds=(String) objs[4];
			if(StringUtil.isNotNull(budgetIds)){
				List result=new ArrayList<>();
				result.add((String)objs[0]);  //成本中心
				result.add((String)objs[1]);  //上级科目
				result.add((String)objs[2]);  //月度科目
				result.add(Integer.parseInt((String)objs[3])+1);  //月度
					String[] budgetIdsArr=budgetIds.split(",");
					List<String> budgetIdList=Arrays.asList(budgetIdsArr);
					Map<String,Map> dataMap=fsscBudgetExecuteService.getExecuteDataList(budgetIdList,null,"match");
					double canUseAmountTotal=0;
					double initAmountTotal=0;
					double adjustAmountTotal=0;
					double alreadyUsedAmountTotal=0;
					double occupyAmountTotal=0;
					double fdTotalMoneyTotal=0;

					
					for(String key:dataMap.keySet()){
						Map<String, Double> budgetExecuteData = dataMap.get(key);
						double initAmount=budgetExecuteData.containsKey("initAmount")?budgetExecuteData.get("initAmount"):0.0;
						initAmountTotal+=initAmount;
						double adjustAmount=budgetExecuteData.containsKey("adjustAmount")?budgetExecuteData.get("adjustAmount"):0.0;
						adjustAmountTotal+=adjustAmount;
						double alreadyUsedAmount=budgetExecuteData.containsKey("alreadyUsedAmount")?budgetExecuteData.get("alreadyUsedAmount"):0.0;//已使用额度
						double occupyAmount=budgetExecuteData.containsKey("occupyAmount")?budgetExecuteData.get("occupyAmount"):0.0;//占用额度
						double canUseAmount=budgetExecuteData.containsKey("canUseAmount")?budgetExecuteData.get("canUseAmount"):0.0;//可使用计划额度
						double fdTotalMoney=budgetExecuteData.containsKey("fdTotalMoney")?budgetExecuteData.get("fdTotalMoney"):0.0;//当前预算

						alreadyUsedAmountTotal+=alreadyUsedAmount;
						occupyAmountTotal+=occupyAmount;
						canUseAmountTotal+=canUseAmount;
						fdTotalMoneyTotal+=fdTotalMoney;
					}
					result.add(fdTotalMoneyTotal);//当前预算
					result.add(initAmountTotal);  //初始化金额
					result.add(alreadyUsedAmountTotal);//已使用额度
					result.add(occupyAmountTotal);//审批中的费用
					result.add(adjustAmountTotal);//调整金额
					result.add(canUseAmountTotal);//可使用计划额度
//					result.add(FsscNumberUtil.getAddition(initAmountTotal, adjustAmountTotal, 2));//预算总额
					resultList.add(result);
			}
			
		}
		Page page=new Page();
		int totalRow=count==null?0:count.get(0).intValue();
		page.setTotalrows(totalRow);
		page.setPageno(pageno);
		page.setRowsize(rowsize);
		
		page.setTotal((int)Math.ceil(Double.valueOf(totalRow+"") /rowsize));
		page.setList(resultList);
		return page;
	}
	
	
	
	
	
	
	/**
	 * 根据前端条件获取所有执行台账信息和对应执行数据，导出
	 * @param request
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<FsscBudgetData> getBudgetDataList(HttpServletRequest request, JSONObject params) throws Exception {
		String fdDimension=request.getParameter("fdDimension");
		if(StringUtil.isNotNull(fdDimension)){
			request.setAttribute("ledgerTitle", getLedgerTitle(fdDimension));
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setPageNo((Integer)params.get("pageno"));
		hqlInfo.setRowSize((Integer)params.get("rowsize"));
		String where="";
		/**预算方案**/
		String fdSchemeId=request.getParameter("fdSchemeId");
		where=buildWhereBlock(hqlInfo,where,"fdBudgetScheme",fdSchemeId);
		String[] dimensionArr=new String[]{"fdCompanyGroup","fdCompany","fdCostCenterGroup","fdCostCenter","fdProject","fdWbs","fdInnerOrder",
			"fdBudgetItem","fdDept","fdPerson"};
		for(String pro:dimensionArr){
			String value=request.getParameter(pro+"Id");
			if(StringUtil.isNotNull(value)){
				where=buildWhereBlock(hqlInfo,where,pro,value);
			}
		}
		/**年份**/
		String fdYear=request.getParameter("fdYear");
		if(StringUtil.isNotNull(fdYear)){
			where=StringUtil.linkString(where, " and ", "fsscBudgetData.fdYear=:fdYear");
			hqlInfo.setParameter("fdYear", "5"+fdYear+"0000");
		}
		/**期间类型**/
		String fdPeriodType=request.getParameter("fdPeriodType");
		if(StringUtil.isNotNull(fdPeriodType)&&!"0".equals(fdPeriodType)){
			where=StringUtil.linkString(where, " and ", "fsscBudgetData.fdPeriodType=:fdPeriodType");
			hqlInfo.setParameter("fdPeriodType", fdPeriodType);
			/**期间**/
			String fdPeriod=request.getParameter("fdPeriod");
			if(StringUtil.isNotNull(fdPeriod)){
				where=StringUtil.linkString(where, " and ", "fsscBudgetData.fdPeriod=:fdPeriod");
				hqlInfo.setParameter("fdPeriod", fdPeriod);
			}
		}
		/**预算状态**/
		String fdBudgetStatus=request.getParameter("fdBudgetStatus");
		if(StringUtil.isNotNull(fdBudgetStatus)){
			where=StringUtil.linkString(where, " and ", HQLUtil.buildLogicIN("fsscBudgetData.fdBudgetStatus",ArrayUtil.convertArrayToList(fdBudgetStatus.split(";"))));
		}else{
			where=StringUtil.linkString(where, " and ", "fsscBudgetData.fdBudgetStatus=:fdBudgetStatus");
			hqlInfo.setParameter("fdBudgetStatus", FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE);
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		return this.findList(hqlInfo);
	}

	/**
	 * 统计预算初始化金额、调整金额、占用、使用、可使用金额（加上滚动金额）
	 * @param budgetList
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object[]> getExecuteAmount(List<String> budgetList) throws Exception{
		Map<String, Object[]> acountMap=new HashMap<String, Object[]>();
		if(!ArrayUtil.isEmpty(budgetList)) {
			StringBuilder sql=new StringBuilder();
			sql.append(" select fd_budget_id,sum(case when fd_type='1'  then fd_money else 0 end) init, ");
			sql.append(" sum(case when fd_type='4'  then fd_money else 0 end) adjust, ");
			sql.append(" sum(case when (fd_type='1' or fd_type='4')  then fd_money else 0 end) total, ");
			sql.append(" sum(case when fd_type='3'  then fd_money else 0 end) used, ");
			sql.append(" sum(case when fd_type='2'  then fd_money else 0 end) occu ");
			sql.append(" from fssc_budget_execute ");
			sql.append(" where "+HQLUtil.buildLogicIN("fd_budget_id",budgetList));
			sql.append(" group by fd_budget_id ");
			List<Object[]> resultList=this.getBaseDao().getHibernateSession().createSQLQuery(sql.toString()).list();
			for(Object[] obj:resultList){
				acountMap.put(String.valueOf(obj[0]),obj);
			}
		}
		return acountMap;
	}

	public String buildWhereBlock(HQLInfo hqlInfo,String where,String property,String value) throws Exception{
		where=StringUtil.linkString(where, " and ", "fsscBudgetData."+property+".fdId=:"+property);
		hqlInfo.setParameter(property, value);
		return where;
	}
	
	//获取需要展现的维度title
	public List<String> getLedgerTitle(String fdDimension) {
		List<String> titleList=new ArrayList<>();
		String[] propertys = {"fdCompanyGroup","fdCompany","fdCostCenterGroup","fdCostCenter","fdProject","fdWbs","fdInnerOrder","fdBudgetItem","fdAsset","fdPerson","fdDept"};
		for(int i=1,size=propertys.length;i<=size;i++){
			if(FsscCommonUtil.isContain(fdDimension, i+";", ";")){
				String propertyName =propertys[i-1];
				if("fdCostCenter".equals(propertyName)){
					titleList.add(ResourceUtil.getString("fsscBudgetData.fdCostCenterParentName", "fssc-budget"));
				}
				if("fdBudgetItem".equals(propertyName)){
					titleList.add(ResourceUtil.getString("fsscBudgetData.fdBudgetItemParentName", "fssc-budget"));
				}
				titleList.add(ResourceUtil.getString("fsscBudgetData."+propertyName, "fssc-budget"));
			}
		}
		return titleList;
	}
	//获取需要展现的维度值
	public List<String> getLedgerProperty(String fdDimension) {
		List<String> propertyList=new ArrayList<>();
		String[] propertys = {"fdCompanyGroup","fdCompany","fdCostCenterGroup","fdCostCenter","fdProject","fdWbs","fdInnerOrder","fdBudgetItem","fdAsset","fdPerson","fdDept"};
		for(int i=1,size=propertys.length;i<=size;i++){
			if(FsscCommonUtil.isContain(fdDimension, i+";", ";")){
				propertyList.add(propertys[i-1]);
			}
		}
		return propertyList;
	}

	@Override
	public HSSFWorkbook getExportDataList(HttpServletRequest request, JSONObject params) throws Exception {request.getSession().removeAttribute("endFlag");//每次导入前，清除结束标记
	List<String> fieldNameList =new ArrayList<>();
	String fdDimension=request.getParameter("fdDimension");
	if(StringUtil.isNotNull(fdDimension)){
		fieldNameList.addAll(getLedgerTitle(fdDimension));  //维度字段
	}
	String[] property={"fdYear","fdQuarter","fdMonth","fdMoney","fdAdjustMoney","fdTotalMoney","fdAlreadyUsedMoney","fdOccupyMoney","fdCanUseMoney","fdBudgetStatus"};

	for(int n=0,size=property.length;n<size;n++){
		fieldNameList.add(ResourceUtil.getString("fsscBudgetData."+property[n], "fssc-budget"));
	}
	List<FsscBudgetData> modelList= getBudgetDataList(request, params);
	List<Object[]> newModelList = new ArrayList<Object[]>();
	DecimalFormat df = new DecimalFormat("#0.00"); // Double类型，
	String fdSchemeId=request.getParameter("fdSchemeId");
	HQLInfo hqlInfo=new HQLInfo();
	StringBuilder whereBlock=new StringBuilder();
	whereBlock.append(" fsscBudgetData.fdBudgetScheme.fdId=:fdSchemeId and fsscBudgetData.fdBudgetStatus=:fdBudgetStatus");
	hqlInfo.setSelectBlock("fsscBudgetData.fdId");
	hqlInfo.setParameter("fdSchemeId",fdSchemeId);
	hqlInfo.setParameter("fdBudgetStatus",FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE);
	Map<String,List<String>>  propertyMap=EopBasedataFsscUtil.getPropertyByScheme(fdSchemeId);
	List<String> inPropertyList=propertyMap.get("inPropertyList");
	SysDataDict dataDict = SysDataDict.getInstance();
	Map<String, SysDictCommonProperty> propMap = dataDict.getModel(FsscBudgetData.class.getName()).getPropertyMap();
	for (String pro : inPropertyList) {
		if(propMap.containsKey(pro)){
			SysDictCommonProperty dict=propMap.get(pro);
			if(dict.getType().startsWith("com.landray.kmss.")){
				String value=request.getParameter(pro+"Id");
				if(StringUtil.isNotNull(value)){
					whereBlock.append(" and fsscBudgetData."+pro+".fdId=:"+pro);
					hqlInfo.setParameter(pro, value);
				}
			}
		}
	}
	hqlInfo.setWhereBlock(whereBlock.toString());
	List<String> budgetList=this.findList(hqlInfo);
	Map<String,Object[]> accountMap=getExecuteAmount(budgetList);//查询该方案下的所有预算对应的金额
	Map<String,List<String>> colMap=getColMapList(fdSchemeId);
	List<String> ledgerPro=getLedgerProperty( fdDimension);
	// 截取两位小数
	if (!ArrayUtil.isEmpty(modelList)) {
		for (int i = 0; i < modelList.size(); i++) {
			FsscBudgetData data = modelList.get(i);
			Double rollAmount=getRollAmount(data,colMap,accountMap);
			Object[] values = new Object[fieldNameList.size()];
			//第一个位id，从1开始,长度相应-1
			for(int n=0,size=values.length;n<size;n++){
				int y=0;
				for(String pro:ledgerPro){//设置维度值
					if(PropertyUtils.isWriteable(data,pro)&&PropertyUtils.getProperty(data,pro)!=null){
						Object obj=PropertyUtils.getProperty(data,pro);
						if(PropertyUtils.isWriteable(obj,"fdName")&&PropertyUtils.getProperty(obj,"fdName")!=null){
							values[y]=PropertyUtils.getProperty(obj,"fdName");
						}else{
							values[y]=PropertyUtils.getProperty(data,pro);
						}
					}else{
						values[y]="";
					}
					y++;
				}
				//年份
				String fdYear=data.getFdYear();
				if(StringUtil.isNotNull(fdYear)){
					values[ledgerPro.size()]=fdYear.substring(1,5)+ResourceUtil.getString("enums.budget.period.type.year", "fssc-budget");
				}else{
					values[ledgerPro.size()]="";
				}
				String fdPeriodType =data.getFdPeriodType();
				//季度
				if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_QUARTER.equals(fdPeriodType)){
					values[ledgerPro.size()+1]=(Integer.parseInt(data.getFdPeriod())+1)+ResourceUtil.getString("enums.budget.period.type.quarter", "fssc-budget");
				}else{
					values[ledgerPro.size()+1]="";
				}
				//月度
				if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_MONTH.equals(fdPeriodType)){
					values[ledgerPro.size()+2]=(Integer.parseInt(data.getFdPeriod())+1)+ResourceUtil.getString("enums.budget.period.type.month", "fssc-budget");
				}else{
					values[ledgerPro.size()+2]="";
				}
				Double canUse=0.0;
				if(accountMap.containsKey(data.getFdId())){
					Object[] obj=accountMap.get(data.getFdId());
					for(int x=1;x<obj.length;x++){
						if(obj[x]!=null&&FsscNumberUtil.isNumber(obj[x])){//只格式化后面6个金额
							values[ledgerPro.size()+x+2]=df.format(obj[x]);
						}else{
							values[ledgerPro.size()+x+2]="";
						}
					}
					canUse=FsscNumberUtil.getAddition((obj[3]!=null?Double.parseDouble(String.valueOf(obj[3])):0.0)-(obj[4]!=null?Double.parseDouble(String.valueOf(obj[4])):0.0)
							-(obj[5]!=null?Double.parseDouble(String.valueOf(obj[5])):0.0),rollAmount);
				}
				values[values.length-2]=df.format(canUse);
				values[values.length-1]=EnumerationTypeUtil.getColumnEnumsLabel("fssc_budget_status", data.getFdBudgetStatus());;
			}
			newModelList.add(values);
		}
	}
	ExcelFileGenerator fileGenerator = new ExcelFileGenerator(fieldNameList,newModelList);
	request.getSession().setAttribute("endFlag", "1");//设置结束标记
	return fileGenerator.createWorkbook();
	}
	
	@Override
	public HSSFWorkbook getExportDataListNew(HttpServletRequest request, JSONObject params) throws Exception {
		//所属公司	成本中心	预算科目	年度	季度	月度	成本中心所属组	当前预算	预算初始额	已发生费用	审批中的费用	调整金额	可使用额度	弹性比例	预算状态
		String[] modeArr={"所属公司","成本中心所属组","成本中心","上级科目","预算科目","年度","季度","月度","当前预算","预算初始额","已发生费用","审批中的费用","调整金额","可使用额度","弹性比例","预算状态"};
		List<String> fieldNameList = Arrays.asList(modeArr);
		Page page=getBudgetDataPage(request, params);
		List<Object[]> objList=new ArrayList<>();
		List<List> list=page.getList();
		for (List list2 : list) {
			list2.remove(0);
			String txbl=(String) list2.get(list2.size()-2);//弹性比例
			if(StringUtil.isNull(txbl)){
				txbl="0%";
			}else{
				txbl+="%";
			}
			String yszt=(String) list2.get(list2.size()-1);//预算状态
			if("1".equals(yszt)){
				yszt="启用";
			}else if("2".equals(yszt)){
				yszt="暂停";
			}else{
				yszt="关闭";
			}
			list2.set(list2.size()-2, txbl);
			list2.set(list2.size()-1, yszt);

			Object[] obj=list2.toArray();
			objList.add(obj);
		}
		ExcelFileGenerator fileGenerator = new ExcelFileGenerator(fieldNameList,objList);
		request.getSession().setAttribute("endFlag", "1");//设置结束标记
		return fileGenerator.createWorkbook();
	
	}

	@Override
	public HSSFWorkbook getExportCountDataList(HttpServletRequest request, JSONObject params) throws Exception {
		//成本中心组  上级科目	预算科目	月度	当前预算	预算初始额	已发生费用	审批中的费用	调整金额	可使用额度
		String[] modeArr={"成本中心组","上级科目","预算科目","月度","当前预算","预算初始额","已发生费用","审批中的费用","调整金额","可使用额度"};
		List<String> fieldNameList = Arrays.asList(modeArr);
		Page page=getBudgetCountDataPage(request, params);
		List<Object[]> objList=new ArrayList<>();
		List<List> list=page.getList();
		for (List list2 : list) {
			Object[] obj=list2.toArray();
			objList.add(obj);
		}
		ExcelFileGenerator fileGenerator = new ExcelFileGenerator(fieldNameList,objList);
		request.getSession().setAttribute("endFlag", "1");//设置结束标记
		return fileGenerator.createWorkbook();
	
	}

	public  Double getRollAmount(FsscBudgetData data, Map<String, List<String>> colMap, Map<String, Object[]> accountMap) throws Exception{
		StringBuilder sql=new StringBuilder();
		sql.append(" select fd_id from fssc_budget_data ");
		sql.append(" where fd_budget_status=:fdBudgetStatus and fd_year=:fdYear ");
		sql.append("  and fd_period_type=:fdPeriodType and fd_period<:fdPeriod");
		sql.append("  and fd_apply=:fdApply");
		Map<String, SysDictCommonProperty> propMap = SysDataDict.getInstance().getModel(FsscBudgetData.class.getName()).getPropertyMap();
		for (String pro : colMap.get("inCol")) {
			Object obj=PropertyUtils.getProperty(data, pro);
			if(propMap.get(pro)!=null&&propMap.get(pro).getType().startsWith("com.landray.kmss")){
				if(obj!=null){
					sql.append(" and "+propMap.get(pro).getColumn()+"=:"+pro);
				}else{
					sql.append(" and "+propMap.get(pro).getColumn()+" is null");
				}
			}else{
				if(propMap.get(pro)!=null){
					sql.append(" and "+propMap.get(pro).getColumn()+"=:"+pro);
				}
			}
		}
		for (String pro : colMap.get("notInCol")) {
			if(propMap.get(pro)!=null){
				sql.append(" and "+propMap.get(pro).getColumn()+" is null");
			}
		}
		Query query=this.getBaseDao().getHibernateSession().createSQLQuery(sql.toString());
		query.setParameter("fdBudgetStatus", FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE);
		query.setParameter("fdYear", data.getFdYear());
		query.setParameter("fdPeriodType", data.getFdPeriodType());
		query.setParameter("fdPeriod", data.getFdPeriod());
		query.setParameter("fdApply", FsscBudgetConstant.FSSC_BUDGET_RULE_ROLL);
		for (String pro : colMap.get("inCol")) {
			Object obj=PropertyUtils.getProperty(data, pro);
			if(propMap.get(pro).getType().startsWith("com.landray.kmss")){
				if(obj!=null){
					query.setParameter(pro,PropertyUtils.getProperty(obj, "fdId"));
				}
			}else{
				query.setParameter(pro,obj);
			}
		}
		List<String> budgetIdList=query.list();
		Double rollAmount=0.0;
		for(String  budgetId:budgetIdList){
			Object [] obj=accountMap.get(budgetId);
			if(obj==null){
				continue;
			}
			//可使用金额=（初始化+调整金额）-（占用+使用）
			Double canUse=FsscNumberUtil.getSubtraction(FsscNumberUtil.getAddition(obj[1]!=null?Double.parseDouble(String.valueOf(obj[1])):0.0,obj[2]!=null?Double.parseDouble(String.valueOf(obj[2])):0.0),
					FsscNumberUtil.getAddition(obj[4]!=null?Double.parseDouble(String.valueOf(obj[4])):0.0,obj[5]!=null?Double.parseDouble(String.valueOf(obj[5])):0.0));
			rollAmount=FsscNumberUtil.getAddition(rollAmount,canUse);
		}
		return rollAmount;
	}

	/**
	 * 获取数据库表字段
	 * @param fdSchemeId
	 * @return
	 * @throws Exception
	 */
	public  Map<String,List<String>> getColMapList(String fdSchemeId) throws Exception{
		EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) this.findByPrimaryKey(fdSchemeId,EopBasedataBudgetScheme.class,true);
		List<String> inColList=new ArrayList<>();
		List<String> notInColList=new ArrayList<>();
		String[] propertys = {"fdCompanyGroup","fdCompany","fdCostCenterGroup","fdCostCenter","fdProject","fdWbs","fdInnerOrder","fdBudgetItem","fdAsset","fdPerson","fdDept"};
		for(int i=1,size=propertys.length;i<=size;i++){
			if(FsscCommonUtil.isContain(scheme.getFdDimension(), i+";", ";")){
				inColList.add(propertys[i-1]);
			}else{
				notInColList.add(propertys[i-1]);
			}
		}
		Map<String,List<String>> map=new HashMap<String,List<String>>();
		map.put("inCol",inColList);
		map.put("notInCol",notInColList);
		return map;
	}

	/**
	 * 校验公司预算币和预算数据币种汇率是否配置，未配置提示终止提交，不然无法占用预算
	 */
	@Override
	public JSONObject checkBudgetExchangeRate(HttpServletRequest request) throws Exception {
		JSONObject rtnObj=new JSONObject();
		StringBuilder msg=new StringBuilder();
		if(FsscCommonUtil.checkHasModule("/fssc/budget/")) {
			String fdCompanyId=request.getParameter("fdCompanyId");
			String params=request.getParameter("params");
			//params=params.replaceAll("'", "\"");
			if(StringUtil.isNotNull(params)&&StringUtil.isNotNull(fdCompanyId)) {
				EopBasedataCompany company=(EopBasedataCompany) this.findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
				String comBudgetCurrency=company!=null?(company.getFdBudgetCurrency()!=null?company.getFdBudgetCurrency().getFdName():""):"";
				JSONArray budgetArr=JSONArray.fromObject(params);
				List<String> fdBudgetIdList=new ArrayList<String>();
				for(int i=0,size=budgetArr.size();i<size;i++) {
					JSONObject obj=budgetArr.getJSONObject(i);
					if(obj.containsKey("fdBudgetInfo")) {
						String  fdBudgetInfo=(String) obj.get("fdBudgetInfo");
						if(StringUtil.isNull(fdBudgetInfo)) {
							continue;
						}
						JSONArray arr=JSONArray.fromObject(fdBudgetInfo);
						for(int j=0;j<arr.size();j++) {
							JSONObject json=arr.getJSONObject(j);
							String budgetId=json.optString("fdBudgetId", "");
							if(!fdBudgetIdList.contains(budgetId)) {
								fdBudgetIdList.add(budgetId);
							}
						}
					}
				}
				if(!ArrayUtil.isEmpty(fdBudgetIdList)) {
					String[] ids =new String[fdBudgetIdList.size()];
					fdBudgetIdList.toArray(ids);
					List<FsscBudgetData> dataList=this.findByPrimaryKeys(ids);
					List<String> fdCurrencyIdList=new ArrayList<String>();
					Map<String,String> cueeencyMap=new HashMap<String,String>();
					for(FsscBudgetData data:dataList) {
						String fdCurrencyId=data.getFdCurrency()!=null?data.getFdCurrency().getFdId():"";
						if(!fdCurrencyIdList.contains(fdCurrencyId)) {
							fdCurrencyIdList.add(fdCurrencyId);
							cueeencyMap.put(fdCurrencyId, data.getFdCurrency()!=null?data.getFdCurrency().getFdName():"");
						}
					}
					if(!ArrayUtil.isEmpty(fdCurrencyIdList)) {
						for(String fdCurrencyId:fdCurrencyIdList) {
							//获取对应公司预算币种换为预算数据币种的汇率
							Double rate=eopBasedataExchangeRateService.getBudgetToRate(fdCurrencyId, fdCompanyId);
							if(rate==null) {
								msg.append(comBudgetCurrency).append(ResourceUtil.getString("budget.rate.and", "fssc-budget"))
								.append(cueeencyMap.get(fdCurrencyId)).append("、");
							}
							//获取预算数据币种换为对应公司预算币种的汇率
							rate=eopBasedataExchangeRateService.getBudgetRate(fdCurrencyId, fdCompanyId);
							if(rate==null) {
								msg.append(cueeencyMap.get(fdCurrencyId)).append(ResourceUtil.getString("budget.rate.and", "fssc-budget"))
								.append(comBudgetCurrency).append("、");
							}
						}
						if(StringUtil.isNotNull(msg.toString())) {
							msg=new StringBuilder(msg.toString().substring(0, msg.toString().length()-1));
							msg.append(ResourceUtil.getString("budget.rate.tips", "fssc-budget"));
						}
					}
				}
			}
		}
		rtnObj.put("msg", msg.toString());
		return rtnObj;
	}

	@Override
	public List<FsscBudgetData> findByFdDept(SysOrgElement element) throws Exception {
		 	HQLInfo hqlInfo = new HQLInfo();
	        hqlInfo.setWhereBlock("fsscBudgetData.fdCostCenter.fdEkpOrg.fdId=:fdId");
	        hqlInfo.setParameter("fdId", element.getFdId());
	        return this.findList(hqlInfo);
	}
}
