package com.landray.kmss.km.forum.dao.hibernate;

import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.km.forum.dao.IKmForumCategoryDao;
import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2006-Aug-31
 * 
 * @author 吴兵 版块设置数据访问接口实现
 */
public class KmForumCategoryDaoImp extends BaseTreeDaoImp implements
		IKmForumCategoryDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmForumCategory kmForumCategory = (KmForumCategory) modelObj;
		kmForumCategory.setDocCreator(UserUtil.getUser());
		kmForumCategory.setDocCreateTime(new Date());
		//为兼容旧数据新增时判断是否需要更新上级层级id
		if(kmForumCategory.getFdParent()!=null&&StringUtil.isNull(kmForumCategory.getFdParent().getFdHierarchyId()))
		{
			updateHierarchyId(kmForumCategory.getFdParent());
		}
		super.add(kmForumCategory);
		return kmForumCategory.getFdId();
	}
	
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		IBaseTreeModel treeModel = (IBaseTreeModel) modelObj;
		if(StringUtil.isNotNull(treeModel.getFdHierarchyId())){
			super.update(modelObj);
		}
		else {
            updateHierarchyId(modelObj);
        }
	}
	//为兼容旧数据需要更新父级的层级id，递归
	@Override
	public void updateHierarchyId(IBaseModel modelObj)throws Exception{
		IBaseTreeModel treeModel = (IBaseTreeModel) modelObj;
		if(treeModel.getFdParent()==null){
			treeModel.setFdHierarchyId(HIERARCHY_ID_SPLIT + treeModel.getFdId()
					+ HIERARCHY_ID_SPLIT);
			}
		
		else{
			if(StringUtil.isNull(treeModel.getFdParent().getFdHierarchyId())){
				updateHierarchyId(treeModel.getFdParent());	
		}
			treeModel.setFdHierarchyId(treeModel.getFdParent().getFdHierarchyId()+ treeModel.getFdId()
					+ HIERARCHY_ID_SPLIT);
             
		}
		modelObj.recalculateFields();
		getHibernateTemplate().saveOrUpdate(modelObj);
	}
	
	@Override
	public int updateForumDirectoy(String ids, String parentId) throws Exception {
		String hql = "update KmForumCategory set hbmParent='" + parentId
				+ "' where fdId in(" + HQLUtil.replaceToSQLString(ids) + ")";
		Query query = getHibernateSession().createQuery(hql);
		return query.executeUpdate();
	}

}
