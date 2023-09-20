package com.landray.kmss.km.forum.dao.hibernate;

import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.forum.dao.IKmForumPostDao;
import com.landray.kmss.km.forum.dao.IKmForumTopicDao;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.util.HQLUtil;


/**
 * 创建日期 2006-Sep-05
 * @author 吴兵
 * 论坛话题数据访问接口实现
 */
public class KmForumTopicDaoImp extends BaseDaoImp implements IKmForumTopicDao {

	IKmForumPostDao kmForumPostDao ;
	public void setKmForumPostDao(IKmForumPostDao kmForumPostDao){
		this.kmForumPostDao =kmForumPostDao;
	}

	@Override
    public void delete(IBaseModel modelObj) throws Exception {
		KmForumTopic topic = (KmForumTopic)modelObj;
		for(int i=0;i<topic.getForumPosts().size();i++ ){
			BaseModel model = (BaseModel)topic.getForumPosts().get(i);
			topic.getForumPosts().remove(model);
			i--;
			kmForumPostDao.delete(model);
		}
		getHibernateTemplate().delete(modelObj);
	}
	
	@Override
    public int updateDucmentCategory(String ids, String forumId) throws Exception {
		String hql = "update KmForumTopic set kmForumCategory='" + forumId
				+ "' where fdId in(" + HQLUtil.replaceToSQLString(ids) + ")";
		Query query = getHibernateSession().createQuery(hql);
		return query.executeUpdate();
	}
	
	@Override
    public void updateTopAgent() throws Exception {
		String hql = "update KmForumTopic set fdTopTime=null, fdTopEndTime=null, fdSticked=:fdSticked where fdTopEndTime<:now";
		Query query = getHibernateSession().createQuery(hql);
		query.setParameter("now", new Date());
		query.setParameter("fdSticked", false);
		query.executeUpdate();
	}
	
}
