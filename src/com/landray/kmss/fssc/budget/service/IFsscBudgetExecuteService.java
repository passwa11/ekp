package com.landray.kmss.fssc.budget.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.fssc.budget.model.FsscBudgetExecute;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface IFsscBudgetExecuteService extends IExtendDataService {

	public Map<String, Double> getExecuteData(String fdBudgetId, String fdModelId, String fdDetailId, String queryType) throws Exception;
	
	public JSONObject addFsscBudgetExecute(JSONObject executeJson) throws Exception;
	
	public JSONObject deleteFsscBudgetExecute(JSONObject executeJson) throws Exception;

	public JSONObject updateFsscBudgetExecute(JSONObject executeJson)  throws Exception;
	
	public abstract JSONArray getBudgets(FsscBudgetData data,String fdModelId,String fdDetailId) throws Exception;

	public Map<String,Map<String,String>> getExtraExecute(List<FsscBudgetExecute> dataList)  throws Exception;

	public JSONObject matchFsscBudgetExecute(JSONObject dataJson);

	public Double getExceuteByType(String fdBudgetId,String fdModelId,String fdDetailId,String fdType)  throws Exception;

	public Map<String,Double> getExceuteMapByType(List<String> fdBudgetIdList,String fdType) throws Exception;


    public Map<String,Map> viewBillBudget(HttpServletRequest request) throws Exception;

    public Map<String,Map> getExecuteDataList(List<String> budgetIdList, String fdModelId, String queryType) throws Exception;
}
