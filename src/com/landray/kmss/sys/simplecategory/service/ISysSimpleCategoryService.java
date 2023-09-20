package com.landray.kmss.sys.simplecategory.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;

public interface ISysSimpleCategoryService extends IBaseService {
	public List getAllChildCategory(ISysSimpleCategoryModel category)
			throws Exception;

}
