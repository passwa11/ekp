package com.landray.kmss.fssc.fee.service.spring;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataItemBudget;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.fee.constant.FsscFeeConstant;
import com.landray.kmss.fssc.fee.model.FsscFeeLedger;
import com.landray.kmss.fssc.fee.model.FsscFeeMain;
import com.landray.kmss.fssc.fee.model.FsscFeeMapp;
import com.landray.kmss.fssc.fee.service.IFsscFeeLedgerService;
import com.landray.kmss.fssc.fee.service.IFsscFeeMappService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataEvent;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;

public class FsscFeeFormEvent implements IExtendDataEvent{
	private final static String[] mapField = {"fdCompany","fdCostCenter","fdExpenseItem","fdProject","fdInnerOrder","fdWbs","fdDept","fdPerson"};
	private final static String[] mapClass = {
			"com.landray.kmss.eop.basedata.model.EopBasedataCompany",
			"com.landray.kmss.eop.basedata.model.EopBasedataCostCenter",
			"com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem",
			"com.landray.kmss.eop.basedata.model.EopBasedataProject",
			"com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder",
			"com.landray.kmss.eop.basedata.model.EopBasedataWbs",
			"com.landray.kmss.sys.organization.model.SysOrgElement",
			"com.landray.kmss.sys.organization.model.SysOrgElement"
	};
	private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	private IFsscFeeMappService fsscFeeMappService;

	public void setFsscFeeMappService(IFsscFeeMappService fsscFeeMappService) {
		this.fsscFeeMappService = fsscFeeMappService;
	}
	 
	private IFsscFeeLedgerService fsscFeeLedgerService;

	public void setFsscFeeLedgerService(IFsscFeeLedgerService fsscFeeLedgerService) {
		this.fsscFeeLedgerService = fsscFeeLedgerService;
	}
	
	@Override
	public void onInit(RequestContext request, IExtendDataModel model, ISysMetadataParser dataParser) throws Exception {
 		
	}

	@Override
	public void onAdd(IExtendDataModel model, ISysMetadataParser dataParser) throws Exception {
		FsscFeeMain main = (FsscFeeMain) model;
		addData(main);
	}

	@Override
	public void onUpdate(IExtendDataModel model, ISysMetadataParser dataParser) throws Exception {
		FsscFeeMain main = (FsscFeeMain) model;
		deleteData(main);
		addData(main);
	}

	@Override
	public void onDelete(IExtendDataModel model, ISysMetadataParser dataParser) throws Exception {
		FsscFeeMain main = (FsscFeeMain) model;
		deleteData(main);
	}
	
	private void addData(FsscFeeMain main) throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fsscFeeMapp.fdTemplate.fdId=:fdTemplateId");
		hqlInfo.setParameter("fdTemplateId",main.getDocTemplate().getFdId());
		List<FsscFeeMapp> list = fsscFeeMappService.findList(hqlInfo);
		Map<String,Object> cache = new HashMap<String,Object>();
		if(!ArrayUtil.isEmpty(list)){
			FsscFeeMapp mapp = list.get(0);
			FormulaParser parser = FormulaParser.getInstance(main);
			List<Object> detailList = getDetailList(mapp,parser);
			String fdRuleId = mapp.getFdRuleId();
			if(StringUtil.isNotNull(fdRuleId)){
				fdRuleId = fdRuleId.replaceAll("\\$", "");
				fdRuleId = fdRuleId.split("_budget_status")[0];
				if(fdRuleId.indexOf(".")>-1){
					fdRuleId = fdRuleId.split("\\.")[1];
				}
			}
			//存在明细表，按明细生成
			if(!ArrayUtil.isEmpty(detailList)){
				for(int i=0;i<detailList.size();i++){
					FsscFeeLedger le = getLedger(main, mapp, parser, cache, i);
					if(le==null){
						continue;
					}
					Map<String,Object> detailMap = (Map<String, Object>) getValue(mapp, parser, "fdTableId", i);
					le.setFdDetailId((String) detailMap.get("fdId"));
					if(StringUtil.isNotNull(fdRuleId)){
						String fdBudgetInfo = (String) detailMap.get(fdRuleId+"_budget_info");
						le.setFdIsUseBudget(StringUtil.isNotNull(fdBudgetInfo)&&!"[]".equals(fdBudgetInfo));
					}else{
						le.setFdIsUseBudget(false);
					}
					setMoney(le,main,mapp,parser,true,i);
					fsscFeeLedgerService.add(le);
				}
			}else{
				FsscFeeLedger le = getLedger(main, mapp, parser, cache, 0);
				if(le==null){
					return;
				}
				le.setFdDetailId(main.getFdId());
				setMoney(le,main,mapp,parser,false,0);
				if(StringUtil.isNotNull(fdRuleId)){
					String fdBudgetInfo = (String) main.getExtendDataModelInfo().getModelData().get(fdRuleId+"_budget_info");
					le.setFdIsUseBudget(StringUtil.isNotNull(fdBudgetInfo)&&!"[]".equals(fdBudgetInfo));
				}else{
					le.setFdIsUseBudget(false);
				}
				fsscFeeLedgerService.add(le);
			}
		}
	}
	
	private void setMoney(FsscFeeLedger le, FsscFeeMain main,FsscFeeMapp mapp,FormulaParser parser,Boolean isDetail,int i)throws Exception {
		Object money = getValue(mapp, parser, "fdMoneyId", i);
		Double fdApplyMoney = 0d;
		try{
			fdApplyMoney = ((Number)money).doubleValue();
		}catch(Exception e){
		}
		le.setFdApplyMoney(fdApplyMoney);
		if(FsscCommonUtil.checkVersion("true")){
			String fdCurrencyId = mapp.getFdCurrencyId();
			fdCurrencyId = fdCurrencyId.replaceAll("\\$", "");
			if(isDetail){
				fdCurrencyId = fdCurrencyId.split("\\.")[1];
				List<Map<String,Object>> detail = (List<Map<String, Object>>) main.getExtendDataModelInfo().getModelData().get(mapp.getFdTableId().replaceAll("\\$", ""));
				String key_cost = fdCurrencyId+"_cost_rate",key_budget = fdCurrencyId+"_budget_rate";
				Double fdBudgetRate = 1d,fdExchangeRate = 1d;
				if(detail.get(i).containsKey(key_budget)&&detail.get(i).get(key_budget)!=null){
					fdBudgetRate = Double.valueOf(detail.get(i).get(key_budget).toString());
				}
				if(detail.get(i).containsKey(key_cost)&&detail.get(i).get(key_cost)!=null){
					fdExchangeRate = Double.valueOf(detail.get(i).get(key_cost).toString());
				}
				fdBudgetRate = fdBudgetRate==null?1d:fdBudgetRate;
				fdExchangeRate = fdExchangeRate==null?1d:fdExchangeRate;
				le.setFdBudgetMoney(FsscNumberUtil.getMultiplication(fdApplyMoney, fdBudgetRate, 2));
				le.setFdStandardMoney(FsscNumberUtil.getMultiplication(fdApplyMoney, fdExchangeRate, 2));
			}else{
				Double fdBudgetRate = Double.valueOf(main.getExtendDataModelInfo().getModelData().get(fdCurrencyId+"_budget_rate").toString());
				Double fdExchangeRate = Double.valueOf(main.getExtendDataModelInfo().getModelData().get(fdCurrencyId+"_cost_rate").toString());
				le.setFdBudgetMoney(FsscNumberUtil.getMultiplication(fdApplyMoney, fdBudgetRate, 2));
				le.setFdStandardMoney(FsscNumberUtil.getMultiplication(fdApplyMoney, fdExchangeRate, 2));
			}
		}else{
			le.setFdBudgetMoney(fdApplyMoney);
			le.setFdStandardMoney(fdApplyMoney);
		}
		
	}

	private FsscFeeLedger getLedger(FsscFeeMain main,FsscFeeMapp mapp,FormulaParser parser,Map<String,Object> cache,int i) throws Exception{
		FsscFeeLedger le = new FsscFeeLedger();
		le.setFdLedgerId(le.getFdId());
		le.setFdModelId(main.getFdId());
		le.setFdModelName(FsscFeeMain.class.getName());
		le.setFdType(FsscFeeConstant.FSSC_FEE_LEDGER_TYPE_INIT);
		le.setDocCreateTime(new Date());
		String fdExpenseItemId = (String) getValue(mapp,parser,"fdExpenseItemId",i);
		if(StringUtil.isNotNull(fdExpenseItemId)) {
			le.setFdExpenseItemId(fdExpenseItemId);
			EopBasedataExpenseItem exp = (EopBasedataExpenseItem) fsscFeeLedgerService.findByPrimaryKey(fdExpenseItemId, EopBasedataExpenseItem.class, true);
			le.setFdExpenseItemCode(exp.getFdCode());
			le.setFdExpenseItemName(exp.getFdName());
		}
		String fdCompanyId = (String) getValue(mapp,parser,"fdCompanyId",i);
		String fdSchemeId = getSchemeId(fdCompanyId,fdExpenseItemId,cache);
		if(StringUtil.isNotNull(fdSchemeId)){
			Map<String,List<String>> propertyList = EopBasedataFsscUtil.getPropertyByScheme(fdSchemeId);
			if(propertyList!=null&&propertyList.containsKey("inPropertyList")){
				List<String> inPropertyList = propertyList.get("inPropertyList");
				for(String property:inPropertyList){
					//资产暂时不用
					if("fdAssert".equals(property)){
						continue;
					}
					//公司组和成本中心组需要特殊处理
					if(property.indexOf("Group")>-1){
						IBaseModel parent = getParentValue(mapp, parser, property.replace("Group", "")+"Id", i);
						if(parent==null){
							return null;
						}
						PropertyUtils.setProperty(le, property+"Id", parent.getFdId());
						try {
							PropertyUtils.setProperty(le, property+"Name", PropertyUtils.getProperty(parent, "fdName"));
							PropertyUtils.setProperty(le, property+"Code", PropertyUtils.getProperty(parent, "fdCode"));
						}catch(Exception e) {}
					}else if("fdBudgetItem".equals(property)){//预算科目特殊处理，从费用类型中取
						EopBasedataExpenseItem item = null;
						if(cache.containsKey(fdExpenseItemId+"-model")){
							item = (EopBasedataExpenseItem) cache.get(fdExpenseItemId+"-model");
						}else{
							item = (EopBasedataExpenseItem) fsscFeeLedgerService.findByPrimaryKey(fdExpenseItemId, EopBasedataExpenseItem.class, true);
							cache.put(fdExpenseItemId+"-model", item);
						}
						List<EopBasedataBudgetItem> list = item.getFdBudgetItems();
						EopBasedataBudgetItem itemCom = null;
						if(!ArrayUtil.isEmpty(list)){
							//只配了一个科目直接取
							if(list.size()==1){
								itemCom = list.get(0);
							}else{
								String fdCostCenterId = (String) getValue(mapp, parser, "fdCostCenterId", i);
								if(StringUtil.isNotNull(fdCostCenterId)){
									EopBasedataCostCenter cost = (EopBasedataCostCenter) fsscFeeLedgerService.findByPrimaryKey(fdCostCenterId, EopBasedataCostCenter.class, true);
									String typeCode = cost.getFdType()==null?"":cost.getFdType().getFdCode();
									if(StringUtil.isNotNull(typeCode)){
										for(EopBasedataBudgetItem it:list){
											if(it.getFdCode().startsWith(typeCode)){
												itemCom = it;
												break;
											}
										}
									}
								}
							}
						}
						if(itemCom!=null){
							le.setFdBudgetItemId(itemCom.getFdId());
						}
					}else{
						Object value =  getValue(mapp, parser, property+"Id", i);
						if(value instanceof IBaseModel){
							IBaseModel model = (IBaseModel) value;
							PropertyUtils.setProperty(le, property+"Id", model.getFdId());
							try {
								PropertyUtils.setProperty(le, property+"Name", PropertyUtils.getProperty(model, "fdName"));
								if(PropertyUtils.isWriteable(model,"fdCode")){
									PropertyUtils.setProperty(le, property+"Code", PropertyUtils.getProperty(model, "fdCode"));
								}
								if(PropertyUtils.isWriteable(model,"fdNo")){
									PropertyUtils.setProperty(le, property+"Code", PropertyUtils.getProperty(model, "fdNo"));
								}
							}catch(Exception e) {}
						}else{
							if(value==null||StringUtil.isNull(value.toString())) {
								continue;
							}
							PropertyUtils.setProperty(le, property+"Id", value);
							String clazz = "";
							for(int k=0;k<mapField.length;k++) {
								if(property.contains(mapField[k])) {
									clazz = mapClass[k];
									break;
								}
							}
							IBaseModel m = fsscFeeMappService.findByPrimaryKey(value.toString(), clazz, true);
							if(m!=null) {
								PropertyUtils.setProperty(le, property+"Name", PropertyUtils.getProperty(m, "fdName"));
								try {
									PropertyUtils.setProperty(le, property+"Code", PropertyUtils.getProperty(m, "fdCode"));
								}catch(Exception e) {}
							}
						}
					}
				}
			}
		}
		return le;
	}
	
	private IBaseModel getParentValue(FsscFeeMapp mapp,FormulaParser parser,String property,int i)throws Exception{
		String value = (String) getValue(mapp, parser, property, i);
		if(StringUtil.isNotNull(value)){
			Class clazz = property.indexOf("Company")>-1?EopBasedataCompany.class:EopBasedataCostCenter.class;
			IBaseModel model = fsscFeeMappService.findByPrimaryKey(value, clazz, true);
			if(property.indexOf("Company")>-1){
				return (IBaseModel) PropertyUtils.getProperty(model, "fdGroup");
			}
			return (IBaseModel) PropertyUtils.getProperty(model, "fdParent");
		}
		return null;
	}
	
	private String getSchemeId(String fdCompanyId,String fdExpenseItemId, Map<String, Object> cache)  throws Exception{
		if(cache.containsKey(fdExpenseItemId)){
			return (String) cache.get(fdExpenseItemId);
		}
		String hql = "select item.fdCategory.fdId from "+EopBasedataItemBudget.class.getName()+" item left join item.fdCompanyList comp left join item.fdItems items where items.fdId=:fdExpenseItemId and item.fdIsAvailable=:fdIsAvailable and (comp.fdId=:fdCompanyId or comp.fdId is null)";
		Query query = fsscFeeMappService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdCompanyId", fdCompanyId);
		query.setParameter("fdExpenseItemId", fdExpenseItemId);
		query.setParameter("fdIsAvailable", true);
		query.setMaxResults(1);
		String fdSchemeId = (String) query.uniqueResult();
		cache.put(fdExpenseItemId, fdSchemeId);
		return fdSchemeId;
	}

	private List<Object> getDetailList(FsscFeeMapp mapp,FormulaParser parser) throws Exception{
		SysDictModel dict = SysDataDict.getInstance().getModel(FsscFeeMapp.class.getName());
		for(SysDictCommonProperty property:dict.getPropertyList()){
			if("fdId".equals(property.getName())||property.getName().indexOf("Id")==-1){
				continue;
			}
			Object value = parser.parseValueScript((String) PropertyUtils.getProperty(mapp, property.getName()));
			if(value instanceof List){
				return (List<Object>) value;
			}
		}
		return null;
	}
	
	private Object getValue(FsscFeeMapp mapp,FormulaParser parser, String property, int i)throws Exception{
		Object value = parser.parseValueScript((String) PropertyUtils.getProperty(mapp, property));
		if(value instanceof List){
			List<Object> data = (List<Object>) value;
			return data.get(i);
		}
		return value;
	}
	
	private void deleteData(FsscFeeMain main) throws HibernateException, Exception{
		String hql = "delete from "+FsscFeeLedger.class.getName()+" where fdModelId=:fdModelId and fdModelName=:fdModelName";
		Query query = fsscFeeLedgerService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdModelId", main.getFdId()); 
		query.setParameter("fdModelName", FsscFeeMain.class.getName());
		query.executeUpdate();
	}

}
