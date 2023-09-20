package com.landray.kmss.sys.evaluation.service;


import net.sf.json.JSONArray;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseCoreInnerService;

public interface ISysEvaluationNotesService extends IBaseCoreInnerService {
	public JSONArray listEvalNotesModels(RequestContext requestInfo) throws Exception;
	
	/*
	 * 获得段落点评的数量
	 */
	public int getNotesCountByModel(IBaseModel model);
	/*
	 * 获取全文点评的所有模块名
	 */
	public JSONArray listEvalModels(RequestContext requestInfo) throws Exception;
	
	/*
	 * 获取被点评文档url
	 */
	public String getDocUrl(String fdModelId,String fdModelName,RequestContext requestInfo) throws Exception;
}	
