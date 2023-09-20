package com.landray.kmss.fssc.budget.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.fssc.budget.forms.FsscBudgetMainForm;
import com.landray.kmss.fssc.budget.model.FsscBudgetMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IFsscBudgetMainService extends IExtendDataService {

    public abstract List<FsscBudgetMain> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<FsscBudgetMain> findByFdBudgetScheme(EopBasedataBudgetScheme fdBudgetScheme) throws Exception;

    public abstract List<FsscBudgetMain> findByFdCompanyGroup(EopBasedataCompanyGroup fdCompanyGroup) throws Exception;

    public abstract List<FsscBudgetMain> findByFdCurrency(EopBasedataExchangeRate fdCurrency) throws Exception;

	public abstract void downTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public abstract JSONArray saveImport(FsscBudgetMainForm mainForm, HttpServletRequest request)  throws Exception;
	
	public abstract void addBudgetData(FsscBudgetMain main) throws Exception;

	public abstract void updateTransferBudget(String startMonth,String endMonth,String fdCompanyIds)  throws Exception;

	public abstract void addFsscBudgetData(JSONArray dataJson)  throws Exception;

	public abstract JSONObject initSaveImport(FsscBudgetMainForm mainForm, HttpServletRequest request)  throws Exception;
}
