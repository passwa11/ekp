package com.landray.kmss.sys.simplecategory.dao.hibernate;

import java.util.List;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryPreviewModel;

/**
 * 分类概览dao
 * 
 * @author Administrator
 * 
 */
public interface ISysSimpleCategoryPreviewDao extends IBaseDao {

	/**
	 * 获取分类概览列表
	 * 
	 * @return List
	 * @throws Exception
	 */
	public List<SysSimpleCategoryPreviewModel> getCategoryPreviewList()
			throws Exception;

	/**
	 * 根据区域ID查询
	 * 
	 * @param authAreaId
	 * @return KmKmapCategoryPerModel
	 * @throws Exception
	 */
	public SysSimpleCategoryPreviewModel getCategoryPreview(
			String authAreaId,String categoryId) throws Exception;

}
