package com.landray.kmss.fssc.budget.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONObject;

public interface IFsscBudgetAdjustMainService extends IExtendDataService {

    public abstract List<FsscBudgetAdjustMain> findByDocTemplate(FsscBudgetAdjustCategory docTemplate) throws Exception;

    public abstract List<FsscBudgetAdjustMain> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<FsscBudgetAdjustMain> findByFdCurrency(EopBasedataExchangeRate fdCurrency) throws Exception;

	public abstract JSONObject checkLendMoney(HttpServletRequest request)  throws Exception;
	
	public abstract void operationBudget(FsscBudgetAdjustMain fsscBudgetAdjust,String method) throws Exception;

	public abstract JSONObject checkBorrowMoney(HttpServletRequest request) throws Exception;
	

	public abstract boolean checkAdjust(HttpServletRequest request)  throws Exception;
}
