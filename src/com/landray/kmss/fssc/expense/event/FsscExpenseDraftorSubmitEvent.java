package com.landray.kmss.fssc.expense.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonFeeService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLoanService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonMobileService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProappService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProvisionService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.model.FsscExpenseOffsetLoan;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 起草人提交事件，占用预算
 * @author wangjinman
 *
 */
public class FsscExpenseDraftorSubmitEvent implements IEventListener{
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
		FsscExpenseMain main = (FsscExpenseMain) execution.getMainModel();
		//只处理驳回状态
		if(!SysDocConstant.DOC_STATUS_REFUSE.equals(main.getDocStatus())){
			return;
		}
		Map<String,Double> budgetMap = new HashMap<String,Double>();
		Map<String,Double> detailMap = new HashMap<String,Double>();
		Map<String,Double> feeMap = new HashMap<String,Double>();
		if(FsscCommonUtil.checkHasModule("/fssc/proapp/")&&StringUtil.isNotNull(main.getFdProappId())){
			List<FsscExpenseDetail> list = main.getFdDetailList();
			EopBasedataProject project = main.getFdProject();
			JSONArray datas = new JSONArray();
			for(FsscExpenseDetail detail:list){
				if(StringUtil.isNull(detail.getFdProappInfo())){
					continue;
				}
				if(main.getDocTemplate().getFdIsProjectShare()!=null&&main.getDocTemplate().getFdIsProjectShare()){
					project = detail.getFdProject();
				}
				JSONObject row = new JSONObject();
				row.put("fdModelId", main.getFdId());
				row.put("fdModelName",FsscExpenseMain.class.getName());
				row.put("fdModelSubject", main.getDocSubject());
				row.put("fdDetailId", detail.getFdId());
				row.put("fdType", "2");
				row.put("fdProjectApprovalId", main.getFdProappId());
				row.put("fdProjectApprovalSubject", main.getFdProappName());
				EopBasedataExpenseItem item = detail.getFdExpenseItem();
				if(item!=null){
					row.put("fdExpenseItemId", item.getFdId());
					row.put("fdExpenseItemName", item.getFdName());
					row.put("fdExpenseItemCode", item.getFdCode());
				}
				EopBasedataCompany comp = detail.getFdCompany();
				if(comp!=null){
					row.put("fdCompanyId", comp.getFdId());
					row.put("fdCompanyName", comp.getFdName());
					row.put("fdCompanyCode", comp.getFdCode());
					EopBasedataCompanyGroup group = comp.getFdGroup();
					if(group!=null){
						row.put("fdCompanyGroupId", group.getFdId());
						row.put("fdCompanyGroupName", group.getFdName());
						row.put("fdCopanyGroupCode", group.getFdCode());
					}
				}
				EopBasedataCostCenter cost = detail.getFdCostCenter();
				if(cost!=null){
					row.put("fdCostCenterId", cost.getFdId());
					row.put("fdCostCenterName", cost.getFdName());
					row.put("fdCostCenterCode", cost.getFdCode());
				}
				if(project!=null){
					row.put("fdProjectId", project.getFdId());
					row.put("fdProjectName", project.getFdName());
					row.put("fdProjectCode", project.getFdCode());
				}
				EopBasedataWbs wbs = detail.getFdWbs();
				if(wbs!=null){
					row.put("fdWbsId", wbs.getFdId());
					row.put("fdWbsName", wbs.getFdName());
					row.put("fdWbsCode", wbs.getFdCode());
				}
				EopBasedataInnerOrder order = detail.getFdInnerOrder();
				if(order!=null){
					row.put("fdInnerOrderId", order.getFdId());
					row.put("fdInnerOrderName", order.getFdName());
					row.put("fdInnerOrderCode", order.getFdCode());
				}
				SysOrgPerson person = detail.getFdRealUser();
				if(person!=null){
					row.put("fdPersonId", person.getFdId());
					row.put("fdPersonName", person.getFdName());
					row.put("fdPersonCode", person.getFdNo());
				}
				row.put("fdCurrency", detail.getFdCurrency()!=null?"":detail.getFdCurrency().getFdName());
				row.put("fdMoney", detail.getFdBudgetMoney());
				if(cost!=null&&item!=null){
					List<EopBasedataBudgetItem> items = item.getFdBudgetItems();
					EopBasedataBudgetItem it = null;
					String fdCode = cost.getFdType().getFdCode();
					if(items.size()==1){
						it = items.get(0);
					}
					for(EopBasedataBudgetItem i:items){
						if(i.getFdCode().indexOf(fdCode)>-1){
							it = i;
							break;
						}
					}
					if(it!=null){
						row.put("fdBudgetItemId", it.getFdId());
						row.put("fdBudgetItemName", it.getFdName());
						row.put("fdBudgetItemCode", it.getFdCode());
					}
				}
				datas.add(row);
			}
			getFsscCommonProappService().udpateProappLedger(datas);
		}
		//如果有预提模块，需要生成预提占用台账
		if(FsscCommonUtil.checkHasModule("/fssc/provision/")){
			//冲抵逻辑，1含税，2不含税
			String  fdProvisionRule=EopBasedataFsscUtil.getDetailPropertyValue(main.getFdCompany().getFdId(),"fdProvisionRule");
			JSONArray datas = new JSONArray();
			getFsscCommonProvisionService().deleteProvisionLedger(main.getFdId(), FsscExpenseMain.class.getName());
			List<FsscExpenseDetail> list = main.getFdDetailList();
			Map<String,Double> ledgerMap = new HashMap<String,Double>();
			for(FsscExpenseDetail detail:list) {
				JSONObject obj = new JSONObject();
				if(detail.getFdExpenseItem()!=null) {
					obj.put("fdExpenseItemId", detail.getFdExpenseItem().getFdId());
				}
				if(detail.getFdCostCenter()!=null) {
					obj.put("fdCostCenterId", detail.getFdCostCenter().getFdId());
				}
				if(detail.getFdProject()!=null) {
					obj.put("fdProjectId", detail.getFdProject().getFdId());
				}
				if(detail.getFdInnerOrder()!=null) {
					obj.put("fdInnerOrderId", detail.getFdInnerOrder().getFdId());
				}
				if(detail.getFdWbs()!=null) {
					obj.put("fdWbsId", detail.getFdWbs().getFdId());
				}
				obj.put("fdProappId", main.getFdProappId());
				obj.put("fdDetailId", detail.getFdId());
				JSONObject rtn = getFsscCommonProvisionService().getProvisionLedgerData(obj);
				if("success".equals(rtn.optString("result"))) {
					JSONArray data = rtn.getJSONArray("data");
					detail.setFdProvisionInfo(data.toString());
					Double detailMoney = detail.getFdStandardMoney();
					if("2".equals(fdProvisionRule)&&detail.getFdNoTaxMoney()!=null) {//如果是不含税，则需要扣除税额
						detailMoney = detail.getFdNoTaxMoney();
					}
					Double useMoney = 0d;
					if(data.size()>0) {
						for(int i=0;i<data.size();i++) {
							JSONObject row = new JSONObject();
							row.put("fdDetailId", detail.getFdId());
							row.put("fdModelName", FsscExpenseMain.class.getName());
							row.put("fdModelId", main.getFdId());
							row.put("fdType", "2");
							row.putAll(data.getJSONObject(i));
							Double fdMoney = ledgerMap.containsKey(row.getString("fdLedgerId"))?ledgerMap.get(row.getString("fdLedgerId")):row.getDouble("fdMoney");
							if(FsscNumberUtil.isEqual(fdMoney, 0d)||FsscNumberUtil.isEqual(detailMoney, 0d)) {//如果当前可用为0，跳过
								continue;
							}
							if(fdMoney>detailMoney) {//如果当前预提可用金额大于报销金额，直接全额冲抵 
								row.put("fdMoney", detailMoney);
								fdMoney = FsscNumberUtil.getSubtraction(fdMoney, detailMoney);
								ledgerMap.put(row.getString("fdLedgerId"), fdMoney);
								useMoney = FsscNumberUtil.getAddition(useMoney, detailMoney);
								detailMoney = 0d;
							}else if(FsscNumberUtil.isEqual(detailMoney, fdMoney)){//如果当前预提可用金额等于报销金额
								row.put("fdMoney", detailMoney);
								useMoney = FsscNumberUtil.getAddition(useMoney, fdMoney);
								ledgerMap.put(row.getString("fdLedgerId"), 0d);
								detailMoney = 0d;
							}else {
								useMoney = FsscNumberUtil.getAddition(useMoney, fdMoney);
								detailMoney = FsscNumberUtil.getSubtraction(detailMoney, fdMoney);
								ledgerMap.put(row.getString("fdLedgerId"), 0d);
								row.put("fdMoney", fdMoney);
							}
							datas.add(row);
						}
					}
					detail.setFdProvisionMoney(useMoney);
				}
			}
			if(datas.size()>0) {
				getFsscCommonProvisionService().addProvisionLedger(datas);
			}
			getFsscExpenseMainService().getBaseDao().update(main);
		}
		if(FsscCommonUtil.checkHasModule("/fssc/fee/")&&StringUtil.isNotNull(main.getFdFeeIds())){
			List<FsscExpenseDetail> list = main.getFdDetailList();
			Double detailMoney = 0d;
			for(FsscExpenseDetail detail:list){
				JSONArray data = JSONArray.fromObject(detail.getFdFeeInfo().replaceAll("'", "\""));
				JSONArray params = new JSONArray();
				Double feeMoney = 0d;
				for(int i=0;i<data.size();i++){
					JSONObject obj = data.getJSONObject(i);
					//前面的明细已经占用了
					if(feeMap.containsKey(obj.get("fdLedgerId"))){
						feeMoney = feeMap.get(obj.get("fdLedgerId"));
					}else{
						feeMoney = obj.getDouble("fdUsableMoney");
					}
					if(detailMap.containsKey(detail.getFdId())){
						detailMoney = detailMap.get(detail.getFdId());
					}else{
						detailMoney = detail.getFdBudgetMoney();
					}

					if(FsscNumberUtil.isEqual(detailMoney, 0d)||FsscNumberUtil.isEqual(feeMoney, 0d)){
						continue;
					}
					JSONObject row = new JSONObject();
					row.put("fdModelId", main.getFdId());
					row.put("fdModelName", FsscExpenseMain.class.getName());
					row.put("fdType", "2");
					row.put("fdDetailId", detail.getFdId());
					row.putAll(obj);
					//如果事前的可用额大于当前明细的金额，直接全额占用
					if(feeMoney>detailMoney){
						row.put("fdBudgetMoney", detailMoney);
						feeMap.put(obj.getString("fdLedgerId"), FsscNumberUtil.getSubtraction(feeMoney,  detailMoney));
						detailMap.put(detail.getFdId(), 0d);
						//如果当前事前台账占用了预算，则报销占预算时需要排除掉这部分金额
						if(obj.containsKey("fdIsUseBudget")&&"true".equals(obj.getString("fdIsUseBudget"))){
							budgetMap.put(detail.getFdId(), 0d);
						}
					}else if(feeMoney<detailMoney){//如果事前的可用额小于当前明细的金额，全额占用该事前，并占用下一个事前
						feeMap.put(obj.getString("fdLedgerId"), 0d);
						row.put("fdBudgetMoney", feeMoney);
						detailMap.put(detail.getFdId(), FsscNumberUtil.getSubtraction(detailMoney, feeMoney));
						//如果当前事前台账占用了预算，则报销占预算时需要排除掉这部分金额
						if(obj.containsKey("fdIsUseBudget")&&"true".equals(obj.getString("fdIsUseBudget"))){
							if(budgetMap.containsKey(detail.getFdId())){
								Double mon = budgetMap.get(detail.getFdId());
								budgetMap.put(detail.getFdId(), FsscNumberUtil.getSubtraction(mon, feeMoney));
							}else{
								budgetMap.put(detail.getFdId(), FsscNumberUtil.getSubtraction(detail.getFdBudgetMoney(), feeMoney));
							}
						}
					}else{
						row.put("fdBudgetMoney", detailMoney);
						feeMap.put(obj.getString("fdLedgerId"), 0d);
						detailMap.put(detail.getFdId(), 0d);
						//如果当前事前台账占用了预算，则报销占预算时需要排除掉这部分金额
						if(obj.containsKey("fdIsUseBudget")&&"true".equals(obj.getString("fdIsUseBudget"))){
							budgetMap.put(detail.getFdId(), 0d);
						}
					}
					params.add(row);
				}
				getFsscCommonFeeService().updateFsscFeeLedger(params);
			}
		}
		if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
			List<FsscExpenseDetail> list = main.getFdDetailList();
			for(FsscExpenseDetail detail:list){
				JSONArray data = JSONArray.fromObject(detail.getFdBudgetInfo().replaceAll("'", "\""));
				JSONArray params = new JSONArray();
				for(int i=0;i<data.size();i++){
					EopBasedataProject project = main.getFdProject();
					if(main.getDocTemplate().getFdIsProjectShare()!=null&&main.getDocTemplate().getFdIsProjectShare()){
						project = detail.getFdProject();
					}
					JSONObject obj = data.getJSONObject(i);
					JSONObject row = new JSONObject();
					row.put("fdModelId", main.getFdId());
					row.put("fdModelName", FsscExpenseMain.class.getName());
					if(budgetMap.containsKey(detail.getFdId())){
						row.put("fdMoney", budgetMap.get(detail.getFdId()));
					}else{
						row.put("fdMoney", detail.getFdBudgetMoney());
					}
					row.put("fdBudgetId", obj.get("fdBudgetId"));
					row.put("fdDetailId", detail.getFdId());
					row.put("fdCompanyId", detail.getFdCompany().getFdId());
					row.put("fdCostCenterId", detail.getFdCostCenter().getFdId());
					row.put("fdExpenseItemId", detail.getFdExpenseItem()==null?"":detail.getFdExpenseItem().getFdId()); //由于结转情况下，需要重新匹配新的预算，所以需要传费用类型ID
					row.put("fdBudgetItemId", obj.get("fdBudgetItemId"));
					row.put("fdType", "2");
					row.put("fdProjectId", project==null?"":project.getFdId());
					row.put("fdInnerOrderId", detail.getFdInnerOrder()==null?"":detail.getFdInnerOrder().getFdId());
					row.put("fdWbsId", detail.getFdWbs()==null?"":detail.getFdWbs().getFdId());
					row.put("fdPersonId", detail.getFdRealUser().getFdId());
					row.put("fdDeptId", detail.getFdRealUser().getFdParent().getFdId());
					row.put("fdCurrency", detail.getFdCurrency().getFdId());
					params.add(row);
				}
				getFsscBudgetOperatService().updateFsscBudgetExecute(params);
			}
		}
		//没有预算模块，不进行操作
		if(FsscCommonUtil.checkHasModule("/fssc/loan/")){
			if(getFsscCommonLoanService()!=null){
				List<FsscExpenseOffsetLoan> list = main.getFdOffsetList();
				JSONObject data = new JSONObject();
				data.put("fdModelId", main.getFdId());
				data.put("fdModelName", FsscExpenseMain.class.getName());
				JSONArray params = new JSONArray();
				for(FsscExpenseOffsetLoan detail:list){
					JSONObject obj = new JSONObject();
					obj.put("fdPersonId", main.getFdClaimant().getFdId());
					obj.put("fdLoanId", detail.getFdLoanId());
					obj.put("fdModelId", main.getFdId());
					obj.put("fdType", "2");
					obj.put("fdModelName", FsscExpenseMain.class.getName());
					obj.put("fdMoney", detail.getFdOffsetMoney());
					obj.put("fdMultiplier", "-1");//标识报销冲抵
					params.add(obj);
				}
				data.put("jsonArray", params);
				getFsscCommonLoanService().deleteAdd(data);
			}
		}
		//选择未报费用，需要更新关联，标识为已使用
		if(FsscCommonUtil.checkHasModule("/fssc/mobile/")){
			List<FsscExpenseDetail> list = main.getFdDetailList();
			Map<String,String> rel = new HashMap<String,String>();
			for(FsscExpenseDetail d:list){
				if(StringUtil.isNotNull(d.getFdNoteId())){
					rel.put(d.getFdNoteId(), d.getFdId());
				}
			}
			getFsscCommonMobileService().updateRelationNote(rel);
		}
	}
}
