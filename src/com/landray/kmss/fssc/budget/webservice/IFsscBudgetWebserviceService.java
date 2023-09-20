package com.landray.kmss.fssc.budget.webservice;

import javax.jws.WebService;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

@WebService
public interface IFsscBudgetWebserviceService extends ISysWebservice {
	
	/***************************************************
	 * 功能：匹配预算
	 * ************************************************/
	public String matchBudget(String messageJson);
	
	/***************************************************
	 * 功能：添加预算执行
	 * ************************************************/
	
	public String addBudgetExecute(String messageJson);
	
	/***************************************************
	 * 功能：删除对应的预算占用记录
	 * ************************************************/
	
	public String deleteBudgetExecue(String messageJson);
	
	/***************************************************
	 * 先删除对应维度的占用，重新插入新的占用或者使用等记录
	 * ***********************************************/
	public String updateBudgetExecue(String messageJson);
	
	/***************************************************
	 * 根据参数获取对应的执行信息
	 * ***********************************************/
	public String matchBudgetExecue(String dataJson);
	/***************************************************
	 * 根据参数增加预算信息
	 * ***********************************************/
	public String addBudgetData(String dataJson);
}
