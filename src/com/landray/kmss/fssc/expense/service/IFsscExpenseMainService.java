package com.landray.kmss.fssc.expense.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

public interface IFsscExpenseMainService extends IExtendDataService {

    public abstract List<FsscExpenseMain> findByDocTemplate(FsscExpenseCategory docTemplate) throws Exception;

    public abstract List<FsscExpenseMain> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<FsscExpenseMain> findByFdCostCenter(EopBasedataCostCenter fdCostCenter) throws Exception;

    public abstract List<FsscExpenseMain> findByFdProject(EopBasedataProject fdProject) throws Exception;
    
    public abstract void downloadBankFile(HttpServletRequest request,HttpServletResponse response)throws Exception ;
    
    public abstract JSONObject checkFeeRelation(HttpServletRequest request) throws Exception ;
    
    public abstract String[] getInvoiceIds(FsscExpenseMain main) throws Exception ;

	public abstract JSONObject checkInvoiceDetail(HttpServletRequest request)  throws Exception ;

	public abstract JSONObject checkInvoice(HttpServletRequest request)  throws Exception;

	public abstract void addOrUpdateInvoiceInfo(FsscExpenseMain main)throws Exception;

	public abstract void  updateBudgetInfo(FsscExpenseMain main) throws Exception;

	public abstract Page listPortlet(HttpServletRequest request) throws Exception;
	
	public abstract void updateLederInvice(String fdId)throws Exception;
}
