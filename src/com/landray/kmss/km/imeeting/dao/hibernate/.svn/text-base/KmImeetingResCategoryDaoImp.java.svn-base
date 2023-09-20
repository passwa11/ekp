package com.landray.kmss.km.imeeting.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.km.imeeting.dao.IKmImeetingResCategoryDao;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingResCategory;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 会议室分类数据访问接口实现
 */
public class KmImeetingResCategoryDaoImp extends BaseTreeDaoImp implements
		IKmImeetingResCategoryDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingResCategory kmImeetingResCategory = (KmImeetingResCategory) modelObj;
		kmImeetingResCategory.setDocCreateTime(new Date());
		kmImeetingResCategory.setDocCreator(UserUtil.getUser());
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmImeetingResCategory kmImeetingResCategory = (KmImeetingResCategory) modelObj;
		kmImeetingResCategory.setDocAlterTime(new Date());
		kmImeetingResCategory.setDocAlteror(UserUtil.getUser());
		IBaseTreeModel treeModel = (IBaseTreeModel) modelObj;
		if (StringUtil.isNotNull(treeModel.getFdHierarchyId())) {
			super.update(modelObj);
		} else {
			updateHierarchyId(modelObj);
		}
	}

	public void updateHierarchyId(IBaseModel modelObj) throws Exception {
		IBaseTreeModel treeModel = (IBaseTreeModel) modelObj;
		if (treeModel.getFdParent() == null) {
			treeModel.setFdHierarchyId(HIERARCHY_ID_SPLIT + treeModel.getFdId()
					+ HIERARCHY_ID_SPLIT);
		} else {
			if (StringUtil.isNull(treeModel.getFdParent().getFdHierarchyId())) {
				updateHierarchyId(treeModel.getFdParent());
			}
			treeModel.setFdHierarchyId(treeModel.getFdParent()
					.getFdHierarchyId()
					+ treeModel.getFdId() + HIERARCHY_ID_SPLIT);
		}
		modelObj.recalculateFields();
		getHibernateTemplate().saveOrUpdate(modelObj);
	}

}
