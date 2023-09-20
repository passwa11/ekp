package com.landray.kmss.fssc.budget.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budget.constant.FsscBudgetConstant;
import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.fssc.budget.model.FsscBudgetExecute;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.fssc.budget.util.FsscBudgetParseXmlUtil;
import com.landray.kmss.fssc.budget.util.FsscBudgetUtil;
import com.landray.kmss.fssc.common.constant.FsscCommonConstant;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import jodd.util.ArraysUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class FsscBudgetExecuteServiceImp extends ExtendDataServiceImp implements IFsscBudgetExecuteService {
	
	protected IFsscBudgetDataService fsscBudgetDataService;
	
	public IFsscBudgetDataService getFsscBudgetDataService() {
		if(fsscBudgetDataService==null){
			fsscBudgetDataService=(IFsscBudgetDataService) SpringBeanUtil.getBean("fsscBudgetDataService");
		}
		return fsscBudgetDataService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscBudgetExecute) {
            FsscBudgetExecute fsscBudgetExecute = (FsscBudgetExecute) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscBudgetExecute fsscBudgetExecute = new FsscBudgetExecute();
        FsscBudgetUtil.initModelFromRequest(fsscBudgetExecute, requestContext);
        return fsscBudgetExecute;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscBudgetExecute fsscBudgetExecute = (FsscBudgetExecute) model;
    }
    /********************************************************
     * @param executeJson 增加预算执行信息
     * return JSONObject result:success 成功，failure，失败，message：失败信息 
     * ******************************************************/
    @Override
    public JSONObject addFsscBudgetExecute(JSONObject executeJson) throws Exception{
    	JSONObject rtnObj=new JSONObject();
    	try {
    		if(!executeJson.isEmpty()){
    			//设置传参必填字段
    			String[] validateProperty={"fdCompanyId","fdModelId","fdModelName","fdBudgetId","fdType","fdMoney"};
    			for(String property:validateProperty){
    				if("true".equals(executeJson.optString("noCompany"))&&"fdCompanyId".equals(property)) {//不需要校验公司，比如，预算调整
    					continue;
    				}
    				Object val=executeJson.containsKey(property)?executeJson.get(property):null;
    				if(val==null){
    					KmssMessage msg = new KmssMessage(ResourceUtil.getString("message.fsscBudgetExecute.setParameterError", "fssc-budget"));
    					throw new KmssRuntimeException(msg);
    				}
    			}
    			String fdBudgetId=executeJson.optString("fdBudgetId", "");
    			FsscBudgetData data=(FsscBudgetData) getFsscBudgetDataService().findByPrimaryKey(fdBudgetId, FsscBudgetData.class, true);
    			if(data!=null) {
    				if("true".equals(executeJson.optString("noCompany"))) {//不要转为公司，比如预算调整，直接是预算金额
    					executeJson.put("fdMoney", executeJson.optDouble("fdMoney", 0.0));
    				}else {
    					String fdCompanyId=executeJson.optString("fdCompanyId", "");
            			Double fdMoney=executeJson.optDouble("fdMoney", 0.0);
            			executeJson.put("fdMoney", FsscNumberUtil.getMultiplication(fdMoney, FsscBudgetUtil.getBillToBudgetRate(data.getFdBudgetScheme(), data.getFdCurrency()!=null?data.getFdCurrency().getFdId():"", fdCompanyId), 2));
    				}
    			}
    			Map<String, SysDictCommonProperty> dictMap=SysDataDict.getInstance().getModel(FsscBudgetExecute.class.getName()).getPropertyMap();
    			FsscBudgetExecute execute=new FsscBudgetExecute();
    	    	  //迭代器迭代 map集合所有的keys  
    	        Iterator it = executeJson.keys();  
    	        while(it.hasNext()){  
    	            String key = (String) it.next();  //获取map的key  
    	            Object value = executeJson.get(key);  //得到value的值  
    	            if(dictMap.containsKey(key)){
    	            	PropertyUtils.setProperty(execute, key, value);  
    	            }
    	        }  
    	        this.add(execute);
    	        rtnObj.put("result", "success");
        	}
		} catch (Exception e) {
			rtnObj.put("result", "failure");
			rtnObj.put("message", e.getMessage());
		}
    	return rtnObj;
    }
    
    /********************************************************
     * @param executeJson 删除预算执行信息
     * ******************************************************/
    @Override
    public JSONObject deleteFsscBudgetExecute(JSONObject executeJson) throws Exception{
    	JSONObject rtnObj=new JSONObject();
    	try {
    		HQLInfo hqlInfo=new HQLInfo();
        	StringBuilder whereBlock=new StringBuilder("fsscBudgetExecute.fdModelId=:fdModelId");
        	hqlInfo.setParameter("fdModelId", executeJson.containsKey("fdModelId")?executeJson.get("fdModelId"):"");
        	whereBlock.append(" and fsscBudgetExecute.fdType!=:fdType");
        	hqlInfo.setParameter("fdType", FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_INUSE); //已使用的预算信息不允许删除
        	if(executeJson.containsKey("fdDetailId")&&StringUtil.isNotNull(executeJson.getString("fdDetailId"))){
        		whereBlock.append(" and fsscBudgetExecute.fdDetailId=:fdDetailId");
            	hqlInfo.setParameter("fdDetailId", executeJson.get("fdDetailId")); //对应预算执行记录的明细ID
        	}
        	if(executeJson.containsKey("fdModelName")){
        		whereBlock.append(" and fsscBudgetExecute.fdModelName=:fdModelName");
        		hqlInfo.setParameter("fdModelName", executeJson.get("fdModelName")); //对应预算执行记录的主表ID
        	}
        	if(executeJson.containsKey("fdBudgetId")&&StringUtil.isNotNull(executeJson.getString("fdBudgetId"))){//对应预算ID，不传则删除整个明细匹配的预算
        		whereBlock.append(" and fsscBudgetExecute.fdBudgetId=:fdBudgetId");
        		hqlInfo.setParameter("fdBudgetId", executeJson.get("fdBudgetId")); //对应预算ID
        	}
        	hqlInfo.setWhereBlock(whereBlock.toString());
        	List<FsscBudgetExecute> executeList=this.findList(hqlInfo);
        	for(FsscBudgetExecute execute:executeList){
        		this.delete(execute);
        	}
        	rtnObj.put("result", "success");
		} catch (Exception e) {
			rtnObj.put("result", "failure");
			rtnObj.put("message", e.toString());
		}
    	return rtnObj;
    }

	@Override
	public Map<String, Double> getExecuteData(String fdBudgetId,String fdModelId,String fdDetailId,String queryType)
			throws Exception {
		Map<String, Double> rtnMap = new ConcurrentHashMap<String, Double>();
		if (StringUtil.isNull(fdBudgetId)) {
			return rtnMap;
		}
		FsscBudgetData fsscBudgetData = (FsscBudgetData) this.findByPrimaryKey(fdBudgetId,FsscBudgetData.class,true);
    	String fdKnots=EopBasedataFsscUtil.getSwitchValue("fdKnots");
    	if(StringUtil.isNull(fdKnots)){
    		fdKnots=EopBasedataConstant.FSSC_FDKNOTS_NO; //未设置结转，默认不接转
    	}
		double rollMoney = 0.0;  //滚动预算金额
		if (FsscBudgetConstant.FSSC_BUDGET_RULE_ROLL.equals(fsscBudgetData.getFdApply())&&"match".equals(queryType)) {//滚动预算且未启用结转，需查找前面所有的季度或者月度的可使用额
			//若是滚动查找所有的预算，不可直接调用fsscBudgetDataService.findBudget方法，不然造成死循环
			JSONArray budgetArray=getBudgets(fsscBudgetData, fdModelId, fdDetailId);  //增加单据ID参数
			for(int j=0;j<budgetArray.size();j++){
				JSONObject budgetObj=budgetArray.getJSONObject(j);
				List<String> idList=new ArrayList<String>();
				if(budgetObj.containsKey("fdBudgetId")&&budgetObj.get("fdBudgetId")!=null){
					idList.add(budgetObj.getString("fdBudgetId"));
				}
				if(!getFsscBudgetDataService().checkBudgetIsKnots(idList)){
					if(budgetObj.containsKey("canUseAmount")&&budgetObj.get("canUseAmount")!=null){
						rollMoney=FsscNumberUtil.getAddition(rollMoney, budgetObj.getDouble("canUseAmount"), 2);
					}
				}
			}
		}
		// 初始计划额度
		double initAmount = fsscBudgetData.getFdMoney();
		// 计算已使用计划额度
		double alreadyUsedAmount = getExceuteByType(fdBudgetId,null,null,FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_INUSE);
		// 计算占用额度
		double occupyAmount =  getExceuteByType(fdBudgetId,fdModelId,fdDetailId,FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU);
		// 调整额度
		double adjustAmount = getExceuteByType(fdBudgetId,null,null,FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST);
		// 调整后计划额度 = 初始化额度 + 调整额度
		double totalAmount = FsscNumberUtil.getAddition(initAmount, adjustAmount);
		double canUseAmount = FsscNumberUtil.getSubtraction(FsscNumberUtil.getSubtraction(totalAmount, alreadyUsedAmount),occupyAmount);  //当前的预算可使用额
		canUseAmount = FsscNumberUtil.getAddition(canUseAmount, rollMoney);  //加上滚动过来的金额
		rtnMap.put("rollMoney", rollMoney);  //滚动预算额
		rtnMap.put("alreadyUsedAmount", FsscNumberUtil.doubleToUp(alreadyUsedAmount));  //已使用
		rtnMap.put("occupyAmount",  FsscNumberUtil.doubleToUp(occupyAmount)); //占用
		rtnMap.put("canUseAmount",  FsscNumberUtil.doubleToUp(canUseAmount));  //可使用
		rtnMap.put("initAmount",  FsscNumberUtil.doubleToUp(initAmount));  //初始化金额
		rtnMap.put("totalAmount",  FsscNumberUtil.doubleToUp(totalAmount));  //预算总金额
		rtnMap.put("adjustAmount",  FsscNumberUtil.doubleToUp(adjustAmount)); //预算调整额
		return rtnMap;
	}

	//批量获取预算可使用金额、占用金额等信息
	@Override
    public Map<String,Map> getExecuteDataList(List<String> fdBudgetIdList, String fdModelId, String queryType)
			throws Exception {
		Map<String,Map> rtnMap = new HashMap<String,Map>();
		if (ArrayUtil.isEmpty(fdBudgetIdList)) {
			return rtnMap;
		}
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("fsscBudgetData.fdId",fdBudgetIdList));
		List<FsscBudgetData> budgetDataList=getFsscBudgetDataService().findList(hqlInfo);
    	String fdKnots=EopBasedataFsscUtil.getSwitchValue("fdKnots");
    	if(StringUtil.isNull(fdKnots)){
    		fdKnots=EopBasedataConstant.FSSC_FDKNOTS_NO; //未设置结转，默认不接转
    	}
    	Map<String,Double> budgetDataMap=getExceuteMapAllType(fdBudgetIdList);
    	Map<String,Double> budgetDataByModelMap=new HashMap<String,Double>();
    	if(StringUtil.isNotNull(fdModelId)){
			budgetDataByModelMap=getExceuteMapAllTypeByMoldeId(fdBudgetIdList,fdModelId);
		}
    	for(FsscBudgetData fsscBudgetData:budgetDataList){
			double rollMoney = 0.0;  //滚动预算金额
			if (FsscBudgetConstant.FSSC_BUDGET_RULE_ROLL.equals(fsscBudgetData.getFdApply())&&"match".equals(queryType)) {//滚动预算且未启用结转，需查找前面所有的季度或者月度的可使用额
				//若是滚动查找所有的预算，不可直接调用fsscBudgetDataService.findBudget方法，不然造成死循环
				JSONArray budgetArray=getBudgets(fsscBudgetData,null,null);
				for(int j=0;j<budgetArray.size();j++){
					JSONObject budgetObj=budgetArray.getJSONObject(j);
					List<String> idList=new ArrayList<String>();
					if(budgetObj.containsKey("fdBudgetId")&&budgetObj.get("fdBudgetId")!=null){
						idList.add(budgetObj.getString("fdBudgetId"));
					}
					if(!getFsscBudgetDataService().checkBudgetIsKnots(idList)){
						if(budgetObj.containsKey("canUseAmount")&&budgetObj.get("canUseAmount")!=null){
							rollMoney=FsscNumberUtil.getAddition(rollMoney, budgetObj.getDouble("canUseAmount"), 2);
						}
					}
				}
			}
			// 初始计划额度
			double initAmount = fsscBudgetData.getFdMoney();
			// 计算已使用计划额度
			double alreadyUsedAmount =0.0;
			if(budgetDataMap.containsKey(fsscBudgetData.getFdId()+FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_INUSE)){
				alreadyUsedAmount=budgetDataMap.get(fsscBudgetData.getFdId()+FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_INUSE);
			}
			// 计算占用额度
			double occupyAmount =0.0;
			if(budgetDataMap.containsKey(fsscBudgetData.getFdId()+FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU)){
				occupyAmount=budgetDataMap.get(fsscBudgetData.getFdId()+FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU);
			}
			// 本单据占用额度
			double thisOccupyAmount =0.0;
			if(budgetDataByModelMap.containsKey(fsscBudgetData.getFdId()+FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU)){
				thisOccupyAmount=budgetDataByModelMap.get(fsscBudgetData.getFdId()+FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU);
			}
			// 调整额度
			double adjustAmount = 0.0;
			if(budgetDataMap.containsKey(fsscBudgetData.getFdId()+FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST)){
				adjustAmount=budgetDataMap.get(fsscBudgetData.getFdId()+FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST);
			}
			// 调整后计划额度 = 初始化额度 + 调整额度
			double totalAmount = FsscNumberUtil.getAddition(initAmount, adjustAmount);
			double canUseAmount = FsscNumberUtil.getSubtraction(FsscNumberUtil.getSubtraction(totalAmount, alreadyUsedAmount),occupyAmount);  //当前的预算可使用额
			canUseAmount = FsscNumberUtil.getAddition(canUseAmount, rollMoney);  //加上滚动过来的金额
			Map<String,Object> dataMap=new HashMap<String,Object>();
			Map<String,List<String>> propertyMap=EopBasedataFsscUtil.getPropertyByScheme(fsscBudgetData.getFdBudgetScheme()!=null?fsscBudgetData.getFdBudgetScheme().getFdId():"");
			if(propertyMap.containsKey("inPropertyList")){
				String subject="";
				if(StringUtil.isNotNull(fsscBudgetData.getFdPeriodType())){
					String year=fsscBudgetData.getFdYear();
					if(StringUtil.isNotNull(year)){
						subject=StringUtil.linkString(subject,"/",year.substring(1,5)+ResourceUtil.getString("enums.budget.period.type.year","fssc-budget"));
					}
					String fdPeriod=fsscBudgetData.getFdPeriod();
					if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_QUARTER.equals(fsscBudgetData.getFdPeriodType())){
						subject=StringUtil.linkString(subject,"/",("0"+(Integer.parseInt(fdPeriod)+1))+ResourceUtil.getString("enums.budget.period.type.quarter","fssc-budget"));
					}
					if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_MONTH.equals(fsscBudgetData.getFdPeriodType())){
						subject=StringUtil.linkString(subject,"/",((Integer.parseInt(fdPeriod)+1)<10?("0"+(Integer.parseInt(fdPeriod)+1)):(Integer.parseInt(fdPeriod)+1))+ResourceUtil.getString("enums.budget.period.type.month","fssc-budget"));
					}
				}
				List<String> inPropertyList=propertyMap.get("inPropertyList");
				for(String inProperty:inPropertyList){
					if(PropertyUtils.isWriteable(fsscBudgetData,inProperty)){
						Object obj=PropertyUtils.getProperty(fsscBudgetData,inProperty);
						if(obj!=null) {
							if (PropertyUtils.isWriteable(obj, "fdName")) {
								if (PropertyUtils.getProperty(obj, "fdName") != null) {
									subject = StringUtil.linkString(subject, "/", (String) PropertyUtils.getProperty(obj, "fdName"));
								}
							} else {
								if (obj != null) {
									subject = StringUtil.linkString(subject, "/", (String) obj);
								}
							}
						}
					}
				}
				dataMap.put("subject", subject);  //滚动预算额
			}
			double fdTotalMoney=rollMoney+FsscNumberUtil.doubleToUp(initAmount);//当前预算是预算初始值+预算滚动值
			dataMap.put("rollMoney", rollMoney);  //滚动预算额
			dataMap.put("alreadyUsedAmount", FsscNumberUtil.doubleToUp(alreadyUsedAmount));  //已使用
			dataMap.put("occupyAmount",  FsscNumberUtil.doubleToUp(occupyAmount)); //占用
			dataMap.put("thisOccupyAmount",  FsscNumberUtil.doubleToUp(thisOccupyAmount)); //本单据占用
			dataMap.put("canUseAmount",  FsscNumberUtil.doubleToUp(canUseAmount));  //可使用
			dataMap.put("initAmount",  FsscNumberUtil.doubleToUp(initAmount));  //初始化金额
			dataMap.put("totalAmount",  FsscNumberUtil.doubleToUp(totalAmount));  //预算总金额
			dataMap.put("adjustAmount",  FsscNumberUtil.doubleToUp(adjustAmount)); //预算调整额
			dataMap.put("fdTotalMoney",  FsscNumberUtil.doubleToUp(fdTotalMoney)); //当前预算

			
			rtnMap.put(fsscBudgetData.getFdId(),dataMap);
		}
		return rtnMap;
	}

	/***************************************************
	 * 返回不同的type的值，1：初始额度，2：占用额度，3：已使用额度，4：调整额度
	 * ***********************************************/
	
	@Override
    public Double getExceuteByType(String fdBudgetId, String fdModelId, String fdDetailId, String fdType) throws Exception{
		Double fdGoalMoney=0.0;
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setSelectBlock("sum(fsscBudgetExecute.fdMoney)");
		String whereBlock="fsscBudgetExecute.fdBudgetId=:fdBudgetId and fsscBudgetExecute.fdType=:fdType";
		if(StringUtil.isNotNull(fdModelId)) {//排除fdModelId对应的记录，目前排除占用，其他的都是传null
			whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscBudgetExecute.fdModelId <>:fdModelId");
			hqlInfo.setParameter("fdModelId", fdModelId);
		}
		if(StringUtil.isNotNull(fdDetailId)) {//排除fdDetailId对应的记录，目前排除占用，其他的都是传null
			whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscBudgetExecute.fdDetailId <>:fdDetailId");
			hqlInfo.setParameter("fdDetailId", fdDetailId);
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdBudgetId", fdBudgetId);
		hqlInfo.setParameter("fdType", fdType);
		List result=this.findList(hqlInfo);
		if(!ArrayUtil.isEmpty(result)&&result.get(0)!=null){
			fdGoalMoney=(Double) result.get(0);
		}
		return fdGoalMoney;
	} 
	/***************************************************
	 * 返回不同的type的值，1：初始额度，2：占用额度，3：已使用额度，4：调整额度,多个预算id
	 * ***********************************************/
	@Override
    public Map<String,Double> getExceuteMapByType(List<String> fdBudgetIdList, String fdType) throws Exception{
		Map<String,Double> rtnMap=new HashMap<>();
		StringBuilder hql=new StringBuilder();
		hql.append("select fsscBudgetExecute.fdBudgetId,sum(fsscBudgetExecute.fdMoney)");
		hql.append(" from FsscBudgetExecute fsscBudgetExecute");
		hql.append(" where "+HQLUtil.buildLogicIN("fsscBudgetExecute.fdBudgetId", fdBudgetIdList)+" and fsscBudgetExecute.fdType=:fdType");
		hql.append(" group by fsscBudgetExecute.fdBudgetId");
		List<Object[]> result=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
				.setParameter("fdType", fdType).list();
		if(!ArrayUtil.isEmpty(result)){
			for(Object[] obj:result){
				if(obj.length>1){
					rtnMap.put(String.valueOf(obj[0]), obj[1]!=null?Double.parseDouble(String.valueOf(obj[1])):0.0);
				}
			}
		}
		return rtnMap;
	}

	/***************************************************
	 * 返回不同的type的值，1：初始额度，2：占用额度，3：已使用额度，4：调整额度,多个预算id
	 * ***********************************************/
	public Map<String,Double> getExceuteMapAllType(List<String> fdBudgetIdList) throws Exception{
		Map<String,Double> rtnMap=new HashMap<>();
		StringBuilder hql=new StringBuilder();
		hql.append("select fsscBudgetExecute.fdBudgetId,fsscBudgetExecute.fdType,sum(fsscBudgetExecute.fdMoney)");
		hql.append(" from FsscBudgetExecute fsscBudgetExecute");
		hql.append(" where "+HQLUtil.buildLogicIN("fsscBudgetExecute.fdBudgetId", fdBudgetIdList));
		hql.append(" group by fsscBudgetExecute.fdBudgetId,fsscBudgetExecute.fdType");
		List<Object[]> result=this.getBaseDao().getHibernateSession().createQuery(hql.toString()).list();
		if(!ArrayUtil.isEmpty(result)){
			for(Object[] obj:result){
				if(obj.length>1){
					rtnMap.put(String.valueOf(obj[0])+String.valueOf(obj[1]), obj[2]!=null?Double.parseDouble(String.valueOf(obj[2])):0.0);
				}
			}
		}
		return rtnMap;
	}

	/***************************************************
	 * 返回不同的type的值，1：初始额度，2：占用额度，3：已使用额度，4：调整额度,多个预算id
	 * ***********************************************/
	public Map<String,Double> getExceuteMapAllTypeByMoldeId(List<String> fdBudgetIdList,String fdModelId) throws Exception{
		Map<String,Double> rtnMap=new HashMap<>();
		StringBuilder hql=new StringBuilder();
		hql.append("select fsscBudgetExecute.fdBudgetId,fsscBudgetExecute.fdType,sum(fsscBudgetExecute.fdMoney)");
		hql.append(" from FsscBudgetExecute fsscBudgetExecute");
		hql.append(" where "+HQLUtil.buildLogicIN("fsscBudgetExecute.fdBudgetId", fdBudgetIdList));
		hql.append(" and fsscBudgetExecute.fdModelId=:fdModelId");
		hql.append(" group by fsscBudgetExecute.fdBudgetId,fsscBudgetExecute.fdType");
		List<Object[]> result=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
				.setParameter("fdModelId",fdModelId).list();
		if(!ArrayUtil.isEmpty(result)){
			for(Object[] obj:result){
				if(obj.length>1){
					rtnMap.put(String.valueOf(obj[0])+String.valueOf(obj[1]), obj[1]!=null?Double.parseDouble(String.valueOf(obj[2])):0.0);
				}
			}
		}
		return rtnMap;
	}

	//根据预算获取其对应需滚动的总可使用额的预算
	@Override
    public JSONArray getBudgets(FsscBudgetData fsscBudgetData, String fdModelId, String fdDetailId) throws Exception{
		JSONArray rtnArray=new JSONArray();
		EopBasedataBudgetScheme scheme=fsscBudgetData.getFdBudgetScheme();
		if(scheme==null&&StringUtil.isNotNull(fsscBudgetData.getFdBudgetSchemeCode())){
			List<EopBasedataBudgetScheme> schemeList=this.getBaseDao().getHibernateSession()
					.createQuery("select scheme from EopBasedataBudgetScheme scheme where scheme.fdCode=:fdCode")
					.setParameter("fdCode", fsscBudgetData.getFdBudgetSchemeCode()).list();
			if(!ArrayUtil.isEmpty(schemeList)){
				scheme=schemeList.get(0);
			}
		}
		if(scheme==null){
			return rtnArray;
		}
		String fdDimensions=scheme.getFdDimension()+";";  //预算维度
		List<Map<String,String>>  propertyList=FsscBudgetParseXmlUtil.getImportProperty();
    	List<Map<String,String>> inPropertyList=new ArrayList<Map<String,String>>();
    	List<Map<String,String>> notInPropertyList=new ArrayList<Map<String,String>>();
    	for(Map<String,String> map:propertyList){
			if(map.containsKey("dimension")){//维度和期间处理
				if(FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";")){
					inPropertyList.add(map);
				}else{
					notInPropertyList.add(map);
				}
			}
		}
    	SysDictModel dict=SysDataDict.getInstance().getModel(FsscBudgetData.class.getName());
		Map<String, SysDictCommonProperty> propertyMap=dict.getPropertyMap();
		HQLInfo hqlInfo=new HQLInfo();
		StringBuilder whereBlock=new StringBuilder("fsscBudgetData.fdBudgetStatus=:fdBudgetStatus");
		whereBlock.append(" and fsscBudgetData.fdYear=:fdYear and fsscBudgetData.fdPeriodType=:fdPeriodType");
		whereBlock.append(" and fsscBudgetData.fdPeriod<:fdPeriod");
		whereBlock.append(" and fsscBudgetData.fdApply=:fdApply");
		hqlInfo.setParameter("fdBudgetStatus", FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE);
		hqlInfo.setParameter("fdYear", fsscBudgetData.getFdYear());
		hqlInfo.setParameter("fdPeriodType", fsscBudgetData.getFdPeriodType());
		hqlInfo.setParameter("fdPeriod", fsscBudgetData.getFdPeriod());
		hqlInfo.setParameter("fdApply", FsscBudgetConstant.FSSC_BUDGET_RULE_ROLL);
		for (Map<String,String> map : inPropertyList) {
			String property=map.get("property");
			if(propertyMap.get(property).getType().startsWith("com.landray.kmss")){
				Object obj=PropertyUtils.getProperty(fsscBudgetData, property);
				if(obj!=null){
					whereBlock.append(" and fsscBudgetData."+property+".fdId=:"+property);
					hqlInfo.setParameter(property,PropertyUtils.getProperty(obj, "fdId"));
				}else{
					whereBlock.append(" and fsscBudgetData."+property+".fdId is null");
				}
			}else{
				whereBlock.append(" and fsscBudgetData."+property+".fdId=:"+property);
	 			hqlInfo.setParameter(property,PropertyUtils.getProperty(fsscBudgetData, property));
			}
		}
		for (Map<String,String> map : notInPropertyList) {
			String property=map.get("property");
			whereBlock.append(" and fsscBudgetData."+property+" is null");
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setOrderBy("fsscBudgetData.fdPeriod desc");
		List<FsscBudgetData> dataList=getFsscBudgetDataService().findList(hqlInfo);
		for(FsscBudgetData data:dataList){
			JSONObject obj=new JSONObject();
			// 初始计划额度
			double initAmount = data.getFdMoney();
			// 计算已使用计划额度
			double alreadyUsedAmount = getExceuteByType(data.getFdId(), fdModelId, fdDetailId,FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_INUSE);
			// 计算占用额度
			double occupyAmount =  getExceuteByType(data.getFdId(),fdModelId, fdDetailId,FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU);
			// 调整额度
			double adjustAmount = getExceuteByType(data.getFdId(),null, null,FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST);
			// 调整后计划额度 = 初始化额度 + 调整额度
			double totalAmount = initAmount + adjustAmount;
			double canUseAmount = totalAmount - alreadyUsedAmount - occupyAmount;  //当前的预算可使用额
			obj.put("fdBudgetId", data.getFdId());
			obj.put("canUseAmount", canUseAmount);
			rtnArray.add(obj);
		}
		return rtnArray;
	}

	/***************************************************
	 * 先删除对应维度的占用，重新插入新的占用或者使用等记录
	 * ***********************************************/
	@Override
	public JSONObject updateFsscBudgetExecute(JSONObject executeJson)
			throws Exception {
		JSONObject jsonObject=new JSONObject();
		try {
			JSONObject rtnObj=addFsscBudgetExecute(executeJson);
			if(FsscCommonConstant.FSSC_COMMON_RESUT_SUCCESS.equals(rtnObj.get("result"))){
				jsonObject.put("result","success");
			}else{
				jsonObject.put("result","failure");
				jsonObject.put("message", rtnObj.get("message"));
			}
		} catch (Exception e) {
			jsonObject.put("result","failure");
			jsonObject.put("message", e.toString());
		}
		return jsonObject;
	}
	
	/***************************************************
	 * 根据参数查询预算执行信息
	 * ***********************************************/
	@Override
	public JSONObject matchFsscBudgetExecute(JSONObject dataJson) {
		JSONObject jsonObject=new JSONObject();
		try {
			JSONArray jsonArray=new JSONArray();
			JSONObject obj=new JSONObject();
			String fdModelId=dataJson.containsKey("fdModelId")?dataJson.getString("fdModelId"):null;
			String fdModelName=dataJson.containsKey("fdModelName")?dataJson.getString("fdModelName"):null;
			String fdDetailId=dataJson.containsKey("fdDetailId")?dataJson.getString("fdDetailId"):null;
			String fdType=dataJson.containsKey("fdType")?dataJson.getString("fdType"):null;
			StringBuilder whereBlock=new StringBuilder();
			HQLInfo hqlInfo=new HQLInfo();
			whereBlock.append("fsscBudgetExecute.fdModelId=:fdModelId");
			hqlInfo.setParameter("fdModelId", fdModelId);
			whereBlock.append(" and fsscBudgetExecute.fdModelName=:fdModelName");
			hqlInfo.setParameter("fdModelName", fdModelName);
			if(StringUtil.isNotNull(fdDetailId)){
				whereBlock.append(" and fsscBudgetExecute.fdDetailId=:fdDetailId");
				hqlInfo.setParameter("fdDetailId", fdDetailId);
			}
			if(StringUtil.isNotNull(fdType)){
				whereBlock.append(" and fsscBudgetExecute.fdType=:fdType");
				hqlInfo.setParameter("fdType", fdType);
			}
			hqlInfo.setWhereBlock(whereBlock.toString());
			List<FsscBudgetExecute> executeList=this.findList(hqlInfo);
			Map<String, SysDictCommonProperty> propMap = SysDataDict.getInstance().getModel(FsscBudgetExecute.class.getName()).getPropertyMap();
			for (FsscBudgetExecute execute : executeList) {
				obj=new JSONObject();
				Iterator its=propMap.keySet().iterator();
				while(its.hasNext()){
					String property=(String) its.next();
					SysDictCommonProperty dict=propMap.get(property);
					String type=dict.getType();
					if(type.startsWith("com.landray.kmss")){//对象树形
						Object object=PropertyUtils.getProperty(execute, property);
						if(object!=null){
							obj.put(property+"Id", PropertyUtils.getProperty(object, "fdId"));	
						}
					}else{//简单属性
						if("fdMoney".equals(property)) {
							Double fdMoney=execute.getFdMoney();
							FsscBudgetData data=(FsscBudgetData) getFsscBudgetDataService().findByPrimaryKey(execute.getFdBudgetId(), null, true);
							String fdCompanyId=dataJson.optString("fdCompanyId", "");
							if(fdMoney!=null&&data!=null&&StringUtil.isNotNull(fdCompanyId)) {
								obj.put("fdMoney", FsscNumberUtil.getMultiplication(fdMoney, FsscBudgetUtil.getBudgetToBillRate(data.getFdBudgetScheme(), data.getFdCurrency()!=null?data.getFdCurrency().getFdId():"", fdCompanyId), 2));
							}else{
								obj.put("fdMoney", fdMoney);
							}
						}else {
							obj.put(property, PropertyUtils.getProperty(execute, property));	
						}
					}
				}
				jsonArray.add(obj);
			}
			jsonObject.put("result","success");
			jsonObject.put("data", jsonArray);
		} catch (Exception e) {
			jsonObject.put("result","failure");
			jsonObject.put("message", e.toString());
		}
		return jsonObject;
	}
	
	/***************************************************
	 * 获取执行数据关联的单据信息，用于列表显示
	 * ***********************************************/
	@Override
	public Map<String, Map<String,String>> getExtraExecute(List<FsscBudgetExecute> dataList)
			throws Exception {
		Map<String, Map<String,String>> rtnMap=new HashMap<String, Map<String,String>>();
		StringBuilder hql=new StringBuilder();
		Map<String,String> valMap=new HashMap<String,String>();
		for (FsscBudgetExecute execute : dataList) {
			valMap=new HashMap<String,String>();
			String fdModelName=execute.getFdModelName();
			if(StringUtil.isNotNull(fdModelName)&&fdModelName.indexOf("$")>-1){
				fdModelName=fdModelName.substring(0,fdModelName.indexOf("$"));
			}
			hql=new StringBuilder();
			String number="docNumber";
			if(fdModelName.contains("KmReviewMain")){
				number="fdNumber";
			}
			hql.append(" select t."+number+",t.docSubject,t.fdId from "+fdModelName);
			hql.append(" t where t.fdId=:fdModelId");
			List<Object[]> result=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
					.setParameter("fdModelId", execute.getFdModelId()).list();
			List<String> ids = new ArrayList<>();

			for(int i=0,len=result.size();i<len;i++){
				Object[] obj=result.get(i);
				valMap.put("docNumber", String.valueOf(obj[0]));
				valMap.put("docSubject", String.valueOf(obj[1]));
				if(StringUtil.isNotNull(fdModelName)){
					SysDictModel model=SysDataDict.getInstance().getModel(fdModelName);
					String messageKey=model.getMessageKey();
					if(StringUtil.isNotNull(messageKey)){
						valMap.put("fdModelName", ResourceUtil.getString(messageKey.split(":")[1], messageKey.split(":")[0]));
					}
					valMap.put("fdUrl", model.getUrl().replace("${fdId}", String.valueOf(obj[2])));
					ids.add(String.valueOf(obj[2]));
				}
			}
			if("com.landray.kmss.fssc.expense.model.FsscExpenseMain".equals(fdModelName)){
				List<FsscExpenseMain> primaryKeys = getFsscExpenseMainService().findByPrimaryKeys(ids.toArray(new String[ids.size()]));
				if(primaryKeys!=null && primaryKeys.size()>0){
					for (FsscExpenseMain primaryKey : primaryKeys) {
						SysOrgPerson docCreator = primaryKey.getDocCreator();
						if(docCreator!=null){
							valMap.put("docCreateName", docCreator.getFdName());
						}
						List<FsscExpenseDetail> fdDetailList = primaryKey.getFdDetailList();
						String fdContent=primaryKey.getFdContent();
						if(fdDetailList!=null && fdDetailList.size()>0){
							String realUser = "";
							String realDept ="";							
							FsscExpenseDetail fsscExpenseDetail=fdDetailList.get(0);
								SysOrgPerson fdRealUser = fsscExpenseDetail.getFdRealUser();
								if(fdRealUser!=null){
									realUser=fdRealUser.getFdName();
								}
								if(fsscExpenseDetail.getFdDept()!=null){
									realDept=fsscExpenseDetail.getFdDept().getFdName();
								}
							valMap.put("realUser", realUser);
							valMap.put("realDept", realDept);
							valMap.put("fdContent", fdContent);
						}
					}
				}
			}

			rtnMap.put(execute.getFdId(), valMap);
		}



		return rtnMap;
	}

	@Override
    public Map<String,Map> viewBillBudget(HttpServletRequest request) throws Exception{
		List rtnList=new ArrayList();
		String fdModelId=request.getParameter("fdModelId");   //单据ID
		//查询当前单据占用的预算记录
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock("fsscBudgetExecute.fdModelId=:fdModelId");
		hqlInfo.setParameter("fdModelId",fdModelId);
		hqlInfo.setSelectBlock("fsscBudgetExecute.fdBudgetId");
		List<String> budgetIdList=this.findList(hqlInfo);
		Map<String,Map> dataMap=getExecuteDataList(budgetIdList,fdModelId,"match");
		return dataMap;
	}
	private IFsscExpenseMainService fsscExpenseMainService;
	public IFsscExpenseMainService getFsscExpenseMainService() {
		if (fsscExpenseMainService == null) {
			fsscExpenseMainService = (IFsscExpenseMainService) SpringBeanUtil.getBean("fsscExpenseMainService");
		}
		return fsscExpenseMainService;
	}
}
