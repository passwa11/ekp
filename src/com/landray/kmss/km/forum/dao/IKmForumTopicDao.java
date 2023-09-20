package com.landray.kmss.km.forum.dao;
import com.landray.kmss.common.dao.IBaseDao;


/**
 * 创建日期 2006-Sep-05
 * @author 吴兵
 * 论坛话题数据访问接口
 */
public interface IKmForumTopicDao extends IBaseDao {
	
	/**
	 * 批量转移帖子
	 * 
	 * @param ids
	 * @param templateId
	 * @throws Exception
	 */
	public int updateDucmentCategory(String ids, String forumId)
			throws Exception;
	
	/**
	 * 清除定时任务
	 * 
	 * @throws Exception
	 */
	public void updateTopAgent() throws Exception;

}
