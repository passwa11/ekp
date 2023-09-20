package com.landray.kmss.km.forum.dao;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.IBaseDao;

/**
 * 创建日期 2006-Sep-05
 * 
 * @author 吴兵 帖子数据访问接口
 */
public interface IKmForumPostDao extends IBaseDao {
	/**
	 * @return 当前楼号
	 */
	public abstract Integer getCurrentFloor(String fdTopicId) throws Exception;

	/**
	 * @return 根据修改时间间隔取帖子集合
	 */
	public abstract List getLastPosts(Date begin, Date end, int start, int count)
			throws Exception;

}
