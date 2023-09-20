package com.landray.kmss.fssc.budget.event;

import java.util.List;
import java.util.Map;

import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonFeeService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLoanService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonMobileService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProappService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProvisionService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.metadata.model.ExtendDataModelInfo;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 起草人提交事件，占用预算(流程管理-资金提交)
 * @author wangjinman
 *
 */
public class KmReviewZjSubmitEvent implements IEventListener{
	private IFsscCommonProappService fsscCommonProappService;
	
	public IFsscCommonProappService getFsscCommonProappService() {
		if (fsscCommonProappService == null) {
			fsscCommonProappService = (IFsscCommonProappService) SpringBeanUtil.getBean("fsscProappCommonService");
        }
		return fsscCommonProappService;
	}
	private IFsscCommonFeeService fsscCommonFeeService;
	
	public IFsscCommonFeeService getFsscCommonFeeService() {
		if (fsscCommonFeeService == null) {
			fsscCommonFeeService = (IFsscCommonFeeService) SpringBeanUtil.getBean("fsscCommonFeeService");
        }
		return fsscCommonFeeService;
	}
	private IFsscCommonBudgetOperatService fsscBudgetOperatService;
	
	public IFsscCommonBudgetOperatService getFsscBudgetOperatService() {
		if(fsscBudgetOperatService==null){
			fsscBudgetOperatService = (IFsscCommonBudgetOperatService) SpringBeanUtil.getBean("fsscBudgetOperatService");
		}
		return fsscBudgetOperatService;
	}
	private IFsscCommonLoanService fsscCommonLoanService;
	
	public IFsscCommonLoanService getFsscCommonLoanService() {
		if (fsscCommonLoanService == null) {
			fsscCommonLoanService = (IFsscCommonLoanService) SpringBeanUtil.getBean("fsscCommonLoanService");
        }
		return fsscCommonLoanService;
	}
	public IFsscCommonMobileService fsscCommonMobileService;

	public IFsscCommonMobileService getFsscCommonMobileService() {
		if(fsscCommonMobileService==null){
			fsscCommonMobileService = (IFsscCommonMobileService) SpringBeanUtil.getBean("fsscCommonMobileService");
		}
		return fsscCommonMobileService;
	}
	private IFsscCommonProvisionService fsscCommonProvisionService;
	
	public IFsscCommonProvisionService getFsscCommonProvisionService() {
		if(fsscCommonProvisionService==null){
			fsscCommonProvisionService = (IFsscCommonProvisionService) SpringBeanUtil.getBean("fsscProvisionCommonService");
		}
		return fsscCommonProvisionService;
	}
	
	private IFsscExpenseMainService fsscExpenseMainService;

	public IFsscExpenseMainService getFsscExpenseMainService() {
		if(fsscExpenseMainService==null){
			fsscExpenseMainService = (IFsscExpenseMainService) SpringBeanUtil.getBean("fsscExpenseMainService");
		}
		return fsscExpenseMainService;
	}

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		if (execution.getMainModel() instanceof KmReviewMain) {
			KmReviewMain main=(KmReviewMain)execution.getMainModel();
			ExtendDataModelInfo extendDataModelInfo = main.getExtendDataModelInfo();
			Map<String, Object> map=extendDataModelInfo.getModelData();
			List<Map<String, Object>> budgetDetail=null;
			for (String key: map.keySet()) {
				boolean needBreak=false;
				//明细数据
				Object obj=map.get(key);
				if(obj instanceof List){
					List<Map<String, Object>> list=(List)obj;
					for (Map<String, Object> detail : list) {
						for (String detailKey: detail.keySet()) {
							if(detailKey.contains("fdBudgetInfo")){//说明是需要匹配预算的明细
								budgetDetail=list;
								needBreak=true;
								break;
							}
						}
					}
					if(needBreak){
						break;
					}
				}
				
			}
			//如果有预提模块，需要生成预提占用台账
			if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
				for(Map<String, Object> detail:budgetDetail){
					String budgetInfoObj="";
					for (String key: detail.keySet()) {
						System.out.println(key);
						if(key.contains("fdBudgetInfo")){
							System.out.println(detail.get(key));
							budgetInfoObj=(String) detail.get(key);
							break;
						}
					}
					JSONArray data = JSONArray.fromObject(budgetInfoObj.replaceAll("'", "\""));
					JSONArray params = new JSONArray();
					for(int i=0;i<data.size();i++){
						JSONObject obj = data.getJSONObject(i);
						JSONObject row = new JSONObject();
						row.put("fdModelId", main.getFdId());
						row.put("fdModelName", KmReviewMain.class.getName());
						row.put("fdMoney", detail.get("fdMoney"));
						row.put("fdBudgetId", obj.get("fdBudgetId"));
						row.put("fdDetailId", detail.get("fdId"));
						row.put("fdCompanyId", map.get("fdCompany"));
						row.put("fdCostCenterId", detail.get("fdCostCenter"));
						row.put("fdExpenseItemId",detail.get("fdExpenseItem")); //由于结转情况下，需要重新匹配新的预算，所以需要传费用类型ID
						row.put("fdBudgetItemId", obj.get("fdBudgetItemId"));
						row.put("fdType", "2");
						row.put("fdPersonId", detail.get("fdPerson")!=null?((Map)detail.get("fdPerson")).get("id"):"");
						row.put("fdCurrency", detail.get("fdCurrency"));
						params.add(row);
					}
					System.out.println("占用预算请求参数："+params);
					getFsscBudgetOperatService().updateFsscBudgetExecute(params);
				}
			}
		}
	}
}
