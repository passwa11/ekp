package com.landray.kmss.sys.evaluation.service;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseCoreInnerService;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationModel;

/**
 * 创建日期 2006-九月-01
 * 
 * @author 叶中奇 点评机制业务对象接口
 */
public interface ISysEvaluationMainService extends IBaseCoreInnerService {
	/*
	 * 获得点评的数量
	 */
	public int getRecordCountByModel(ISysEvaluationModel sysEvaluationModel);
	
	/*
	 * 获取点评平均分数
	 */
	public double score(String modelName,String modelId);
	
	/*
	 * 获取全文点评的所有模块名
	 */
	public JSONArray listEvalModels(RequestContext requestInfo) throws Exception;
	
	/*
	 * 列出被点评的文档名称
	 */
	public String getEvalDocSubject(String docModelName,String docModelId) throws Exception;
	/*
	 * 获取被点评文档url
	 */
	public String getDocUrl(String fdModelId,String fdModelName,RequestContext requestInfo) throws Exception;
	
	/**
	 *  获取某个文档的每个点评级别的人数(五个级别)
	 *  @return jsonArray对象 [{score:分数,times:次数},.....]，最多五个对象
	 */
	public JSONArray getEvalStarDetail(String fdModelName, String fdModelId) throws Exception;
	
	public  String getTopEvaluationIdByUserId(String modelName, String modelId, String userId) throws Exception;
	
	/**
	 * 获取点评列表的附件
	 * @param list
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public JSONObject getListAtt(String[] list, String modelName) throws Exception;
	
	/**
	 * 获取点评列表
	 * @param fdModelId
	 * @param fdModelName
	 * @param fdKey
	 * @return
	 * @throws Exception
	 */
	public List findEvaluationMainList(String fdModelId,String fdModelName,String fdKey) throws Exception;
	
}
