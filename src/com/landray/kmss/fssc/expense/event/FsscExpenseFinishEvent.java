package com.landray.kmss.fssc.expense.event;

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
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProappService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProvisionService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonWxcardService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseInvoiceDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.model.FsscExpenseOffsetLoan;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FsscExpenseFinishEvent implements IEventListener{
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
	private IFsscExpenseMainService fsscExpenseMainService;	
	public IFsscExpenseMainService getFsscExpenseMainService() {
		if (fsscExpenseMainService == null) {
			fsscExpenseMainService = (IFsscExpenseMainService) SpringBeanUtil.getBean("fsscExpenseMainService");
        }
		return fsscExpenseMainService;
	}
	private IFsscCommonProvisionService fsscCommonProvisionService;
	
	public IFsscCommonProvisionService getFsscCommonProvisionService() {
		if(fsscCommonProvisionService==null){
			fsscCommonProvisionService = (IFsscCommonProvisionService) SpringBeanUtil.getBean("fsscProvisionCommonService");
		}
		return fsscCommonProvisionService;
	}
	
	private IFsscCommonWxcardService fsscCommonWxcardService;
	
	public IFsscCommonWxcardService getFsscCommonWxcardService() {
		if(fsscCommonWxcardService==null){
			fsscCommonWxcardService = (IFsscCommonWxcardService) SpringBeanUtil.getBean("fsscWxcardCommonService");
		}
		return fsscCommonWxcardService;
	}
	
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		FsscExpenseMain main = (FsscExpenseMain) execution.getMainModel();
		main.setDocPublishTime(new Date());
		if(FsscCommonUtil.checkHasModule("/fssc/proapp/")&&StringUtil.isNotNull(main.getFdProappId())){
			List<FsscExpenseDetail> list = main.getFdDetailList();
			EopBasedataProject project = main.getFdProject();
			JSONArray datas = new JSONArray();
			for(FsscExpenseDetail detail:list){
				if(StringUtil.isNull(detail.getFdProappInfo())||detail.getFdApprovedApplyMoney()<0){
					continue;
				}
				if(main.getDocTemplate().getFdIsProjectShare()!=null&&main.getDocTemplate().getFdIsProjectShare()){
					project = detail.getFdProject();
				}
				JSONObject row = new JSONObject();
				row.put("fdModelId", main.getFdId());
				row.put("fdHappenDate", DateUtil.convertDateToString(detail.getFdHappenDate(), DateUtil.PATTERN_DATE));
				row.put("fdModelName",FsscExpenseMain.class.getName());
				row.put("fdModelSubject", main.getDocSubject());
				row.put("fdDetailId", detail.getFdId());
				row.put("fdType", "3");
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
				if(detail.getFdApprovedApplyMoney()<0){
					continue;
				}
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
				obj.put("fdCompanyId", detail.getFdCompany().getFdId());
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
							row.put("fdType", "3");
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
		Map<String,Double> map  = getBudgetMap(main);
		if(FsscCommonUtil.checkHasModule("/fssc/fee/")&&StringUtil.isNotNull(main.getFdFeeIds())){
			getFsscCommonFeeService().updateFsscFeeBudget(main.getFdId(), FsscExpenseMain.class.getName(),main.getFdCompany()!=null?main.getFdCompany().getFdId():"");
			if(main.getFdIsCloseFee()){//勾选了关闭事前
				String[] fdFeeIds=main.getFdFeeIds().split(";");
				for(int i=0,len=fdFeeIds.length;i<len;i++){
					getFsscCommonFeeService().updateCloseFee(fdFeeIds[i]);
				}
			}
		}
		//没有预算模块，不进行操作
		if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
			if(getFsscBudgetOperatService()!=null){
				List<FsscExpenseDetail> list = main.getFdDetailList();
				for(FsscExpenseDetail detail:list){
					if(StringUtil.isNull(detail.getFdBudgetInfo())||FsscNumberUtil.isEqual(map.get(detail.getFdId()), 0d)||detail.getFdApprovedApplyMoney()<0){
						continue;
					}
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
						row.put("fdMoney", map.get(detail.getFdId()));
						row.put("fdBudgetId", obj.get("fdBudgetId"));
						row.put("fdDetailId", detail.getFdId());
						row.put("fdCompanyId", detail.getFdCompany().getFdId());
						row.put("fdCostCenterId", detail.getFdCostCenter().getFdId());
						row.put("fdExpenseItemId", detail.getFdExpenseItem()==null?"":detail.getFdExpenseItem().getFdId()); //由于结转情况下，需要重新匹配新的预算，所以需要传费用类型ID
						row.put("fdBudgetItemId", obj.get("fdBudgetItemId"));
						row.put("fdType", "3");
						row.put("fdProjectId", project==null?"":project.getFdId());
						row.put("fdInnerOrderId", detail.getFdInnerOrder()==null?"":detail.getFdInnerOrder().getFdId());
						row.put("fdWbsId", detail.getFdWbs()==null?"":detail.getFdWbs().getFdId());
						row.put("fdPersonId", detail.getFdRealUser().getFdId());
						if(detail.getFdDept()!=null){
							row.put("fdDeptId", detail.getFdDept().getFdId());
						}else{
							if(detail.getFdRealUser().getFdParent()!=null){
								row.put("fdDeptId", detail.getFdRealUser().getFdParent().getFdId());
							}
						}
						row.put("fdCurrency", detail.getFdCurrency().getFdId());
						params.add(row);					
					}
					if(params.size()>0){
						getFsscBudgetOperatService().updateFsscBudgetExecute(params);
					}
				}
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
					if(detail.getFdOffsetMoney()==null||FsscNumberUtil.isEqual(detail.getFdOffsetMoney(), 0d)||detail.getFdOffsetMoney()<0){
						continue;
					}
					JSONObject obj = new JSONObject();
					obj.put("fdPersonId", main.getFdClaimant().getFdId());
					obj.put("fdLoanId", detail.getFdLoanId());
					obj.put("fdModelId", main.getFdId());
					obj.put("fdModelName", FsscExpenseMain.class.getName());
					obj.put("fdModelNumber", main.getDocNumber());
					obj.put("fdModelSubject", main.getDocSubject());
					obj.put("fdType", "3");
					obj.put("fdMoney", detail.getFdOffsetMoney());
					obj.put("fdMultiplier", "-1");//标识报销冲抵
					params.add(obj);
				}
				data.put("jsonArray", params);
				getFsscCommonLoanService().deleteAdd(data);
			}
		}
		
		if(main!=null&&FsscCommonUtil.checkHasModule("/fssc/wxcard/")){
			List<FsscExpenseInvoiceDetail> invoiceDetailList=main.getFdInvoiceList();
			for(FsscExpenseInvoiceDetail invoiceDetail:invoiceDetailList){
				JSONObject param=new JSONObject();
				param.put("invoice_code", invoiceDetail.getFdInvoiceCode());
				param.put("invoice_no", invoiceDetail.getFdInvoiceNumber());
				JSONObject obj= getFsscCommonWxcardService().findInvoice(param.toString());
				if(!obj.isEmpty()){
					obj.put("status", 3);	//核销
					if(!"1".equals(obj.optString("is_block_chain", "0"))) {//非区块链发票
						getFsscCommonWxcardService().synStatusByInvoice(obj);	//更新发票报销状态为核销
					}
					param.put("invoiceStatus", "INVOICE_REIMBURSE_CLOSURE");	//发票状态核销
					getFsscCommonWxcardService().updateInvoiceStatus(param);	//更新微信卡包的发票报销状态为核销
				}
			}
		}
	}
	/**
	 * 计算明细需要扣减预算的金额
	 * @param main
	 * @return
	 */
	private Map<String, Double> getBudgetMap(FsscExpenseMain main) throws Exception{
		Map<String, Double> rtn = new HashMap<String, Double>();
		List<FsscExpenseDetail> list = main.getFdDetailList();
		for(FsscExpenseDetail detail:list){
			rtn.put(detail.getFdId(), detail.getFdBudgetMoney()!=null?detail.getFdBudgetMoney():0.0);
		}
		//如果关联了事前，需要扣除占事前的金额
		if(FsscCommonUtil.checkHasModule("/fssc/fee/")&&StringUtil.isNotNull(main.getFdFeeIds())){
			for(FsscExpenseDetail detail:list){
				String fdFeeInfo = detail.getFdFeeInfo();
				if(StringUtil.isNull(fdFeeInfo)){
					continue;
				}
				JSONArray feeInfo = JSONArray.fromObject(fdFeeInfo.replaceAll("'", "\""));
				Double fdBudgetMoney = detail.getFdBudgetMoney();
				for(int i=0;i<feeInfo.size();i++){
					JSONObject fee = feeInfo.getJSONObject(i);
					if(!fee.containsKey("fdIsUseBudget")||!(Boolean)fee.get("fdIsUseBudget")){//事前占了预算的话要排除掉
						continue;
					}
					Double fdOffsetMoney = Double.parseDouble(fee.optString("fdOffsetMoney","0"));//当前占用的金额
					fdBudgetMoney = FsscNumberUtil.getSubtraction(fdBudgetMoney, fdOffsetMoney);
				}
				rtn.put(detail.getFdId(),fdBudgetMoney<0?0d:fdBudgetMoney);
			}
		}
		return rtn;
	}

}
