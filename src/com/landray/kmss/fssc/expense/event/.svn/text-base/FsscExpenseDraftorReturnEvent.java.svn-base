package com.landray.kmss.fssc.expense.event;

import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonFeeService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLedgerService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLoanService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProappService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProvisionService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 起草人撤回，释放预算
 * @author wangjinman
 *
 */
public class FsscExpenseDraftorReturnEvent implements IEventListener{
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
	
	private IFsscCommonLedgerService fsscCommonLedgerService;
	
	public IFsscCommonLedgerService getFsscCommonLedgerService() {
		if (fsscCommonLedgerService == null) {
			fsscCommonLedgerService = (IFsscCommonLedgerService) SpringBeanUtil.getBean("fsscCommonLedgerService");
        }
		return fsscCommonLedgerService;
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

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		FsscExpenseMain main = (FsscExpenseMain) execution.getMainModel();
		//重新设置核准金额、预算金额、本币金额
		for(FsscExpenseDetail detail:main.getFdDetailList()) {
			Double fdExchangeRate = detail.getFdExchangeRate();
			Double fdApplyMoney = detail.getFdApplyMoney();
			Double fdStandardMoney = FsscNumberUtil.getMultiplication(fdApplyMoney, fdExchangeRate,2);
			detail.setFdApprovedApplyMoney(fdApplyMoney);
			detail.setFdApprovedStandardMoney(fdStandardMoney);
			detail.setFdStandardMoney(fdStandardMoney);
		}
		getFsscExpenseMainService().getBaseDao().update(main);
		if(FsscCommonUtil.checkHasModule("/fssc/proapp/")&&StringUtil.isNotNull(main.getFdProappId())){
			getFsscCommonProappService().deleteProappLedger(main.getFdId(), FsscExpenseMain.class.getName());
		}
		if(FsscCommonUtil.checkHasModule("/fssc/provision/")){
			getFsscCommonProvisionService().deleteProvisionLedger(main.getFdId(), FsscExpenseMain.class.getName());
		}
		if(FsscCommonUtil.checkHasModule("/fssc/fee/")&&StringUtil.isNotNull(main.getFdFeeIds())){
			getFsscCommonFeeService().deleteFeeLedgerByModel(main.getFdId(), FsscExpenseMain.class.getName());
		}
		//没有预算模块，不进行操作
		if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
			if(getFsscBudgetOperatService()!=null){
				JSONObject object = new JSONObject();
				object.put("fdModelName", FsscExpenseMain.class.getName());
				object.put("fdModelId", main.getFdId());
				getFsscBudgetOperatService().deleteFsscBudgetExecute(object);
			}
		}
		//没有预算模块，不进行操作
		if(FsscCommonUtil.checkHasModule("/fssc/loan/")){
			if(getFsscCommonLoanService()!=null){
				JSONObject object = new JSONObject();
				object.put("fdModelName", FsscExpenseMain.class.getName());
				object.put("fdModelId", main.getFdId());
				getFsscCommonLoanService().delete(object);
			}
		}
		if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
			JSONObject valueJson=new JSONObject();
			valueJson.put("fdUseStatus", "0");   //撤回设置为未使用
			getFsscCommonLedgerService().updatePropertys( getFsscExpenseMainService().getInvoiceIds(main), valueJson);
			JSONObject object = new JSONObject();
			object.put("fdModelName", FsscExpenseMain.class.getName());
			object.put("fdModelId", main.getFdId());
			getFsscCommonLedgerService().clearInvoiceModel(object);
			String[] invoiceArr={};
			for(FsscExpenseDetail detail:main.getFdDetailList()) {
				String fdNoteId=detail.getFdNoteId();
				if(StringUtil.isNotNull(fdNoteId)){//随手记转报销，还原fdModelId为随手记ID
					valueJson=new JSONObject();
					valueJson.put("fdModelId", fdNoteId);   
					valueJson.put("fdModelName", "com.landray.kmss.fssc.mobile.model.FsscMobileNote");
					List<String> noteIdList=new ArrayList<String>();
					noteIdList.add(fdNoteId);
					List<String> invoiceIdList=getFsscCommonLedgerService().getInvoiceList(noteIdList);
					invoiceArr = new String[invoiceIdList.size()];
					if(!ArrayUtil.isEmpty(invoiceIdList)){
						invoiceIdList.toArray(invoiceArr);
					}
					getFsscCommonLedgerService().updatePropertys(invoiceArr, valueJson);
				}
			}
        }
		//存在招行商务卡模块，将交易数据状态变为未使用
		if(FsscCommonUtil.checkHasModule("/fssc/ccard/")){
			String hql = "update com.landray.kmss.fssc.ccard.model.FsscCcardTranData set fdState=:fdState where ";
			hql+="fdId in(select fdTranDataId from com.landray.kmss.fssc.expense.model.FsscExpenseTranData where docMain.fdId=:fdId)";
			getFsscExpenseMainService().getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdState","0").setParameter("fdId", main.getFdId()).executeUpdate();
		}
	}
}
