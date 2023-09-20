package com.landray.kmss.sys.evaluation.dao;

import com.landray.kmss.common.dao.IBaseCoreDao;
import com.landray.kmss.common.model.IBaseModel;

public interface ISysEvaluationNotesDao extends IBaseCoreDao {
	/*
	 * 获得段落点评的数量
	 */
	public int getNotesCountByModel(IBaseModel model);
}
