package com.landray.kmss.sys.simplecategory.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryPreviewModel;

/**
 * 分类概览service
 * 
 * @author Administrator
 * 
 */
public interface ISysSimpleCategoryPreviewService extends IBaseService {

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
	
    /**
     * 创建概览
     * @param previewContent 内容
     * @param authAreaId  区域ID
     * @param isEnableIsSolation  是否开启分级授权
     * @throws Exception
     */
	public void addCategoryPreviewBySomething(String previewContent,String categoryId,
			String authAreaId, String isEnableIsSolation) throws Exception;
	
	
	/**
	 * 根据分类获取文档数量
	 */
	public Integer getDocAmount(SysSimpleCategoryAuthTmpModel sysSimpleCategoryAuthTmpModel,
			String authAreaId) throws Exception;
	
	/**
	 * 获取子级分类
	 * 
	 * @param categoryId
	 * @param authAreaId
	 * @return
	 * @throws Exception
	 */
	public List<SysSimpleCategoryAuthTmpModel> getCategoryList(String categoryId,
			String authAreaId) throws Exception;
}
