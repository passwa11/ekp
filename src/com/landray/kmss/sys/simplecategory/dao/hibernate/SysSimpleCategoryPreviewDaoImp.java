package com.landray.kmss.sys.simplecategory.dao.hibernate;

import java.util.List;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryPreviewModel;

/**
 * 分类概览Dao实现
 * 
 * @author Administrator
 * 
 */
public abstract class SysSimpleCategoryPreviewDaoImp extends BaseDaoImp
		implements ISysSimpleCategoryPreviewDao {

	@Override
    public List<SysSimpleCategoryPreviewModel> getCategoryPreviewList()
			throws Exception {
		return super.findList(null);
	}

}
