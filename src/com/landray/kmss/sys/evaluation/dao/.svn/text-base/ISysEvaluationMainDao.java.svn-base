package com.landray.kmss.sys.evaluation.dao;
import java.util.List;

import com.landray.kmss.common.dao.IBaseCoreDao;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationModel;


/**
 * 创建日期 2006-九月-01
 * @author 叶中奇
 * 点评机制数据访问接口
 */
public interface ISysEvaluationMainDao extends IBaseCoreDao {
	/*
	 * 获得点评的数量
	 */
	public int getRecordCountByModel(ISysEvaluationModel sysEvaluationModel);
	
	/*
	 * 获取点评平均分数
	 */
	public double score(String modelName,String modelId);
	
	
	/**
	 * 获取各个点评级别的人数
	 */
	
	public List getEvalStarDetail(String modelName, String modelId);
	
	public void deleteByParentId(String fdParentId) throws Exception;
}
