package com.landray.kmss.fssc.fee.listener;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.fee.model.FsscFeeMain;
import com.landray.kmss.fssc.fee.model.FsscFeeMapp;
import com.landray.kmss.fssc.fee.service.IFsscFeeMappService;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscFeeFinishEvent implements IEventListener{
	private IFsscFeeMappService fsscFeeMappService;
	public void setFsscFeeMappService(IFsscFeeMappService fsscFeeMappService) {
		this.fsscFeeMappService = fsscFeeMappService;
	}
	private IFsscCommonBudgetOperatService fsscBudgetOperatService;
	
	public IFsscCommonBudgetOperatService getFsscBudgetOperatService() {
		if(fsscBudgetOperatService==null){
			fsscBudgetOperatService = (IFsscCommonBudgetOperatService) SpringBeanUtil.getBean("fsscBudgetOperatService");
		}
		return fsscBudgetOperatService;
	}
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		FsscFeeMain main = (FsscFeeMain) execution.getMainModel();
		main.setDocPublishTime(new Date());
		//没有预算模块，不进行操作
		if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
			if(getFsscBudgetOperatService()!=null){
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("fsscFeeMapp.fdTemplate.fdId=:fdTemplateId");
				hqlInfo.setParameter("fdTemplateId",main.getDocTemplate().getFdId());
				List<FsscFeeMapp> list = fsscFeeMappService.findList(hqlInfo);
				//必须要配置了台账映射
				if(!ArrayUtil.isEmpty(list)){
					FsscFeeMapp mapp = list.get(0);
					//必须要配置预算规则
					if(StringUtil.isNotNull(mapp.getFdRuleId())){
						String fdRuleId = mapp.getFdRuleId().replaceAll("\\$", "").split("_budget_status")[0];
						//配置了明细表，说明预算在明细中
						if(StringUtil.isNotNull(mapp.getFdTableId())){
							String fdTableId = mapp.getFdTableId().replaceAll("\\$", "");
							fdRuleId = fdRuleId.split("\\.")[1];
							List<Map> tableList = (List<Map>) main.getExtendDataModelInfo().getModelData().get(fdTableId);
							for(Map detail:tableList){
								String fdBudgetInfo = (String) detail.get(fdRuleId+"_budget_info");
								JSONArray data = JSONArray.fromObject(fdBudgetInfo.replaceAll("'", "\""));
								JSONArray params = new JSONArray();
								for(int i=0;i<data.size();i++){
									JSONObject obj = data.getJSONObject(i);
									JSONObject row = new JSONObject();
									row.put("fdModelId", main.getFdId());
									row.put("fdModelName", FsscFeeMain.class.getName());
									Number fdMoney = (Number) detail.get(mapp.getFdMoneyId().replaceAll("\\$", "").split("\\.")[1]);
									Double money = fdMoney==null?0d:fdMoney.doubleValue();
									Object rate = detail.get(mapp.getFdCurrencyId().replaceAll("\\$", "").split("\\.")[1]+"_budget_rate");
									Double rr = 1d;
									try{
										rr = Double.parseDouble(rate.toString());
									}catch(Exception e){
									}
									money = FsscNumberUtil.getMultiplication(money, rr);
									row.put("fdMoney", money);
									row.put("fdBudgetId", obj.get("fdBudgetId"));
									row.put("fdDetailId", detail.get("fdId"));
									row.put("fdBudgetItemId", obj.get("fdBudgetItemId"));
									row.put("fdType", "3");
									row.put("fdCompanyId", main.getExtendDataModelInfo().getModelData().get(mapp.getFdCompanyId().replaceAll("\\$", "")));
									if(StringUtil.isNotNull(mapp.getFdCostCenterId())){
										row.put("fdCostCenterId", detail.get(mapp.getFdCostCenterId().replaceAll("\\$", "").split("\\.")[1]));
									}
									if(StringUtil.isNotNull(mapp.getFdProjectId())){
										row.put("fdProjectId", detail.get(mapp.getFdProjectId().replaceAll("\\$", "").split("\\.")[1]));
									}
									if(StringUtil.isNotNull(mapp.getFdWbs())){
										row.put("fdWbsId", detail.get(mapp.getFdWbs().replaceAll("\\$", "").split("\\.")[1]));
									}
									if(StringUtil.isNotNull(mapp.getFdInnerOrderId())){
										row.put("fdInnerOrderId", detail.get(mapp.getFdInnerOrderId().replaceAll("\\$", "").split("\\.")[1]));
									}
									if(StringUtil.isNotNull(mapp.getFdPersonId())){
										Map person = (Map) detail.get(mapp.getFdPersonId().replaceAll("\\$", "").split("\\.")[1]);
										row.put("fdPersonId", person==null?"":person.get("id"));
									}
									//由于结转情况下，需要重新匹配新的预算，所以需要传费用类型ID
									if(StringUtil.isNotNull(mapp.getFdExpenseItemId())){
										row.put("fdExpenseItemId", detail.get(mapp.getFdExpenseItemId().replaceAll("\\$", "").split("\\.")[1]));
									}
									params.add(row);
								}
								getFsscBudgetOperatService().updateFsscBudgetExecute(params);
							}
						}else{
							String fdBudgetInfo = (String) main.getExtendDataModelInfo().getModelData().get(fdRuleId+"_budget_info");
							JSONArray data = JSONArray.fromObject(fdBudgetInfo.replaceAll("'", "\""));
							JSONArray params = new JSONArray();
							for(int i=0;i<data.size();i++){
								JSONObject obj = data.getJSONObject(i);
								JSONObject row = new JSONObject();
								row.put("fdModelId", main.getFdId());
								row.put("fdModelName", FsscFeeMain.class.getName());
								Number fdMoney = (Number) main.getExtendDataModelInfo().getModelData().get(mapp.getFdMoneyId().replaceAll("\\$", ""));
								Double money = fdMoney==null?0d:fdMoney.doubleValue();
								Object rate = main.getExtendDataModelInfo().getModelData().get(mapp.getFdCurrencyId().replaceAll("\\$", "")+"_budget_rate");
								Double rr = 1d;
								try{
									rr = Double.parseDouble(rate.toString());
								}catch(Exception e){
								}
								money = FsscNumberUtil.getMultiplication(money, rr);
								row.put("fdMoney", money);
								row.put("fdBudgetId", obj.get("fdBudgetId"));
								row.put("fdDetailId", main.getExtendDataModelInfo().getModelData().get("fdId"));
								row.put("fdBudgetItemId", obj.get("fdBudgetItemId"));
								row.put("fdType", "3");
								row.put("fdCompanyId", main.getExtendDataModelInfo().getModelData().get(mapp.getFdCompanyId().replaceAll("\\$", "")));
								if(StringUtil.isNotNull(mapp.getFdCostCenterId())){
									row.put("fdCostCenterId", main.getExtendDataModelInfo().getModelData().get(mapp.getFdCostCenterId().replaceAll("\\$", "")));
								}
								if(StringUtil.isNotNull(mapp.getFdProjectId())){
									row.put("fdProjectId", main.getExtendDataModelInfo().getModelData().get(mapp.getFdProjectId().replaceAll("\\$", "")));
								}
								if(StringUtil.isNotNull(mapp.getFdWbs())){
									row.put("fdWbsId", main.getExtendDataModelInfo().getModelData().get(mapp.getFdWbs().replaceAll("\\$", "")));
								}
								if(StringUtil.isNotNull(mapp.getFdInnerOrderId())){
									row.put("fdInnerOrderId", main.getExtendDataModelInfo().getModelData().get(mapp.getFdInnerOrderId().replaceAll("\\$", "")));
								}
								if(StringUtil.isNotNull(mapp.getFdPersonId())){
									Map person = (Map) main.getExtendDataModelInfo().getModelData().get(mapp.getFdPersonId().replaceAll("\\$", ""));
									row.put("fdPersonId", person==null?"":person.get("id"));
								}
								params.add(row);
							}
							getFsscBudgetOperatService().updateFsscBudgetExecute(params);
						}
					}
				}
			}
		}
	}

}
