package com.landray.kmss.km.forum.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.km.forum.dao.IKmForumPostDao;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

/**
 * 创建日期 2006-Sep-05
 * 
 * @author 吴兵 帖子数据访问接口实现
 */
public class KmForumPostDaoImp extends BaseDaoImp implements IKmForumPostDao {

	private static final String selectPostByTime = "from KmForumPost post where post.docAlterTime between :begin and :end order by post.fdId desc";

	/**
	 * @return 当前楼号
	 */
	@Override
	public Integer getCurrentFloor(String fdTopicId) throws Exception {
		List list = findValue("max(kmForumPost.fdFloor)",
				"kmForumPost.kmForumTopic.fdId='" + fdTopicId+"'", null);
		if (list.isEmpty()) {
            return new Integer(0);
        }
		Object o = (Object) list.get(0);
		if (o == null) {
            return new Integer(0);
        }
		return (Integer) o;
	}

	/**
	 * @return 根据修改时间间隔取帖子集合
	 */
	@Override
	public List getLastPosts(Date begin, Date end, int start, int count)
			throws Exception {
		Query query = super.getSession().createQuery(
				new StringBuffer(selectPostByTime).toString());
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("km-forum");
		query.setTimestamp("begin", begin);
		query.setTimestamp("end", end);
		query.setFirstResult(start);
		query.setMaxResults(count);

		// 获取话题信息
		return query.list();
	}
}
