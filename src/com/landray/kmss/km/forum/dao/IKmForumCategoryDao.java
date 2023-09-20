package com.landray.kmss.km.forum.dao;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;


/**
 * 创建日期 2006-Aug-31
 * @author 吴兵
 * 版块设置数据访问接口
 */
public interface IKmForumCategoryDao extends IBaseDao {

	public void updateHierarchyId(IBaseModel modelObj)throws Exception;
	
	/**
	 * 批量转移版块
	 * 
	 * @param ids
	 * @param templateId
	 * @throws Exception
	 */
	public int updateForumDirectoy(String ids, String parentId)
			throws Exception;
}
