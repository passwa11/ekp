package com.landray.kmss.third.ekp.java.tag;

import java.util.List;

import com.landray.kmss.third.ekp.java.tag.client.TagAddContext;
import com.landray.kmss.third.ekp.java.tag.client.TagGetResult;
import com.landray.kmss.third.ekp.java.tag.client.TagGetTagsContext;
import com.landray.kmss.third.ekp.java.tag.client.TagResult;

public interface ISysTagWebServiceClient {

	/**
	 * 查询分类接口
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public TagGetResult getCategories(String type) throws Exception;

	/**
	 * 查询标签接口
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public TagGetResult getTags(TagGetTagsContext context) throws Exception;

	/**
	 * 新增接口
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public TagResult addTags(TagAddContext context) throws Exception;

	/**
	 * 获取配置组
	 * 
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public TagResult getGroups(String modelName) throws Exception;
	
	
	/**
	 * 获取是否是特殊标签
	 */
	public TagResult getIsSpecialByTags(List<String>tags) throws Exception;

}
