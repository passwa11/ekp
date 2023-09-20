package com.landray.kmss.km.forum.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;

/**
 * 创建日期 2006-Aug-31
 * 
 * @author 吴兵 版块设置业务对象接口
 */
public interface IKmForumCategoryService extends IBaseService {
	public void updateHierarchyId(IBaseModel model)throws Exception;
	
	/**
	 * 批量转移版块
	 * 
	 * @param ids
	 * @param templateId
	 * @throws Exception
	 */
	public int updateForumDirectoy(String ids, String templateId)
			throws Exception;
	
	/**
	 * 把分类的IDS转换成板块IDS
	 * 
	 * @param request
	 * @param fdForumIds
	 * @return
	 * @throws Exception
	 */
	public List<String> expentCategoryToModuleIds(HttpServletRequest request,
			String fdForumIds) throws Exception;
}
