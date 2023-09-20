package com.landray.kmss.fssc.budgeting.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingMainForm;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.web.action.ActionForward;

public interface IFsscBudgetingMainService extends IExtendDataService {

	public boolean isView(HttpServletRequest request) throws Exception;

	public void completeDetail(HttpServletRequest request, FsscBudgetingMain main,String method, Map<String, Boolean> authMap) throws Exception;

	public void createNewEdition(HttpServletRequest request,FsscBudgetingMain main, Map<String, Boolean> authMap)  throws Exception;

	public List<Map> gatherOrg(HttpServletRequest request)  throws Exception;

	public boolean isInitView(HttpServletRequest request) throws Exception;
	
	public void updateApprovalDoc(HttpServletRequest request) throws Exception;

	public void addBudgeting(HttpServletRequest request)  throws Exception;

	public boolean checkBudgetingAuth(String fdOrgId)  throws Exception;

	public boolean checkBudgetingApprovalAuth(String fdOrgId)  throws Exception;

	public boolean checkBudgetingEffectAuth()  throws Exception;

	public ActionForward getViewForward(FsscBudgetingMainForm mainForm)  throws Exception;

	public void updateBudgetingStatus()  throws Exception;
	
	public Map<String, Boolean> getBudgetingViewAuth(HttpServletRequest request,
			String fdOrgId) throws Exception;
	
	public List getHistoryOptionList(String fdId) throws Exception;

}
