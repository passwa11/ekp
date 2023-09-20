package com.landray.kmss.km.comminfo.service;

import com.landray.kmss.common.service.IBaseService;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 徐乃瑞 常用资料类别业务对象接口
 */
public interface IKmComminfoCategoryService extends IBaseService {
	public boolean updateDataFromCategorysTo(final String[] cateIds,
			final String cateId) throws Exception;
}
