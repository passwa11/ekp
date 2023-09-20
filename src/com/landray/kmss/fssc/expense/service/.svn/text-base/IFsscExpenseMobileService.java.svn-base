package com.landray.kmss.fssc.expense.service;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IFsscExpenseMobileService extends IExtendDataService {

	public JSONObject getExpenseCateList(HttpServletRequest request) throws Exception;

	public JSONObject getExpenseMainList(HttpServletRequest request)throws Exception;

	public JSONObject getParentId(String fdId) throws Exception;

	public JSONObject saveAtt(HttpServletRequest request)throws Exception;

	public JSONObject deleteAtt(HttpServletRequest request)throws Exception;

	public JSONObject updateRelation(Map<String, String> map)throws Exception;

	public void updateAttachmentRelation(String[] attId, String fdModelId)throws Exception;

	public JSONArray getFdInputTax(JSONObject param) throws Exception;


}
