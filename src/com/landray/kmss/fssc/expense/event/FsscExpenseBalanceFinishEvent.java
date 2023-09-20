package com.landray.kmss.fssc.expense.event;

import java.util.Date;
import java.util.List;

import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetMatchService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.expense.constant.FsscExpenseConstant;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalance;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalanceDetail;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscExpenseBalanceFinishEvent implements IEventListener{
	private IFsscCommonBudgetOperatService fsscBudgetOperatService;
	
	public IFsscCommonBudgetOperatService getFsscBudgetOperatService() {
		if(fsscBudgetOperatService==null){
			fsscBudgetOperatService = (IFsscCommonBudgetOperatService) SpringBeanUtil.getBean("fsscBudgetOperatService");
		}
		return fsscBudgetOperatService;
	}
	private IFsscCommonBudgetMatchService fsscBudgetMatchService;
	public IFsscCommonBudgetMatchService getFsscBudgetMatchService() {
		if(fsscBudgetMatchService==null){
			fsscBudgetMatchService = (IFsscCommonBudgetMatchService) SpringBeanUtil.getBean("fsscBudgetMatchService");
		}
		return fsscBudgetMatchService;
	}
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		FsscExpenseBalance main = (FsscExpenseBalance) execution.getMainModel();
		main.setDocPublishTime(new Date());
		//是否启用了调账涉及预算变动
		if(!"true".equals(EopBasedataFsscUtil.getSwitchValue("fdRebudget"))){
			return;
		}
		//没有预算模块，不进行操作
		if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
			if(getFsscBudgetOperatService()!=null){
				List<FsscExpenseBalanceDetail> list = main.getFdDetailList();
				for(FsscExpenseBalanceDetail detail:list){
					String fdBudgetInfo = detail.getFdBudgetInfo();
					if(StringUtil.isNull(fdBudgetInfo)){
						continue;
					}
					JSONArray data = JSONArray.fromObject(fdBudgetInfo.replaceAll("'", "\""));
					JSONArray data1 = new JSONArray();
					for(int i=0;i<data.size();i++){
						JSONObject obj = data.getJSONObject(i);
						JSONObject row = new JSONObject();
						//如果是贷方，只能在流程发布时增加预算
						if(FsscExpenseConstant.FSSC_EXPENSE_VOUCHER_TYPE_PAY.equals(detail.getFdType())){
							row.put("fdMoney", detail.getFdBudgetMoney()*-1);
						}else{
							row.put("fdMoney", detail.getFdBudgetMoney());
						}
						row.put("fdModelId", main.getFdId());
						row.put("fdModelName", FsscExpenseBalance.class.getName());
						row.put("fdBudgetId", obj.get("fdBudgetId"));
						row.put("fdDetailId", detail.getFdId());
						row.put("fdCompanyId", main.getFdCompany().getFdId());
						row.put("fdCostCenterId", detail.getFdCostCenter()==null?"":detail.getFdCostCenter().getFdId());
						row.put("fdExpenseItemId", detail.getFdExpenseItem()==null?"":detail.getFdExpenseItem().getFdId()); //由于结转情况下，需要重新匹配新的预算，所以需要传费用类型ID
						row.put("fdBudgetItemId", obj.get("fdBudgetItemId"));
						row.put("fdType", "3");
						row.put("fdProjectId", detail.getFdProject()==null?"":detail.getFdProject().getFdId());
						row.put("fdPersonId", detail.getFdPerson()==null?"":detail.getFdPerson().getFdId());
						row.put("fdCurrency", main.getFdCurrency().getFdId());
						data1.add(row);
					}
					getFsscBudgetOperatService().updateFsscBudgetExecute(data1);
				}
			}
		}
	}

}
