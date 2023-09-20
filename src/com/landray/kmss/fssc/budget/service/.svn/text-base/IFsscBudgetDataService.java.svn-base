package com.landray.kmss.fssc.budget.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IFsscBudgetDataService extends IExtendDataService {

    public abstract List<FsscBudgetData> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<FsscBudgetData> findByFdCompanyGroup(EopBasedataCompanyGroup fdCompanyGroup) throws Exception;

    public abstract List<FsscBudgetData> findByFdCostCenter(EopBasedataCostCenter fdCostCenter) throws Exception;
    
    public abstract List<FsscBudgetData> findByFdDept(SysOrgElement element) throws Exception;

    public abstract List<FsscBudgetData> findByFdBudgetItem(EopBasedataBudgetItem fdBudgetItem) throws Exception;

    public abstract List<FsscBudgetData> findByFdProject(EopBasedataProject fdProject) throws Exception;

    public abstract List<FsscBudgetData> findByFdInnerOrder(EopBasedataInnerOrder fdInnerOrder) throws Exception;

    public abstract List<FsscBudgetData> findByFdWbs(EopBasedataWbs fdWbs) throws Exception;

    public abstract List<FsscBudgetData> findByFdBudgetScheme(EopBasedataBudgetScheme fdBudgetScheme) throws Exception;

    public abstract List<FsscBudgetData> findByFdCostCenterGroup(EopBasedataCostCenter fdCostCenterGroup) throws Exception;

    public abstract List<FsscBudgetData> findByFdCurrency(EopBasedataExchangeRate fdCurrency) throws Exception;
    
    public abstract JSONArray findBudget(JSONObject messageJson,String budgetPeriod,String fdYear,String fdPeriod) throws Exception;

	public abstract Map<String, String> getBudgetAcountInfo(String fdBudgetId,String dataType)  throws Exception;

	public abstract JSONObject updateBudgetStatus(HttpServletRequest request) throws Exception;

	public abstract void updateData(IExtendForm form,RequestContext requestContext)  throws Exception;
	
	public abstract boolean checkBudgetIsKnots(List<String> idList) throws Exception;

	public abstract Page getBudgetDataPage(HttpServletRequest request, JSONObject params) throws Exception;
	
	public abstract Page getBudgetCountDataPage(HttpServletRequest request, JSONObject params) throws Exception;


	public abstract HSSFWorkbook getExportDataList(HttpServletRequest request, JSONObject params) throws Exception;
	
	public abstract HSSFWorkbook getExportDataListNew(HttpServletRequest request, JSONObject params) throws Exception;

	
	public abstract HSSFWorkbook getExportCountDataList(HttpServletRequest request, JSONObject params) throws Exception;


	public abstract JSONObject checkBudgetExchangeRate(HttpServletRequest request) throws Exception;

}
