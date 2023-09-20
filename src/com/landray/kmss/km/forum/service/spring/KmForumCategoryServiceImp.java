package com.landray.kmss.km.forum.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.forum.dao.IKmForumCategoryDao;
import com.landray.kmss.km.forum.forms.KmForumCategoryForm;
import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.km.forum.service.IKmForumCategoryService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.simplecategory.service.SysSimpleCategoryServiceImp;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2006-Aug-31
 * 
 * @author 吴兵 版块设置业务接口实现
 */
public class KmForumCategoryServiceImp extends SysSimpleCategoryServiceImp implements
		IKmForumCategoryService {

	@Override
    public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
                                         RequestContext requestContext) throws Exception {
		KmForumCategoryForm kmForumCategoryForm = (KmForumCategoryForm)form;
		if ("00".equals(kmForumCategoryForm.getFdParentId())) {
			kmForumCategoryForm.setFdParentId("");
		}
		KmForumCategory kmForumCategory = (KmForumCategory) super
				.convertFormToModel(form, model, requestContext);
		kmForumCategory.setDocAlter(UserUtil.getUser());
		kmForumCategory.setDocAlterTime(new Date());
		return kmForumCategory;
	}

	@Override
    public void updateHierarchyId(IBaseModel model) throws Exception {
		
		((IKmForumCategoryDao)getBaseDao()).updateHierarchyId(model);
	}
	
	@Override
    public int updateForumDirectoy(String ids, String parentId)
	throws Exception {
		// 添加日志信息
		if (UserOperHelper.allowLogOper("updateForumDirectoy",
				getModelName())) {
			UserOperContentHelper.putUpdate(ids).putSimple("parentId", null,
					parentId);
		}
		return ((IKmForumCategoryDao) this.getBaseDao()).updateForumDirectoy(
				ids, parentId);
	}

	@Override
	public List<String> expentCategoryToModuleIds(HttpServletRequest request,
			String fdForumIds) throws Exception {
		List<String> ids = new ArrayList<String>();
		String[] fdForumIdStrs = fdForumIds.split(";");
		for (String id : fdForumIdStrs) {
			KmForumCategory category = (KmForumCategory)findByPrimaryKey(id);
			if (!ids.contains(id)) {
				ids.add(id);
			}
			expendAllChildren(category, ids);
		}
		return ids;
	}
   
	private void expendAllChildren(KmForumCategory category, List<String> ids) {
		List<KmForumCategory> chilidCategory = category.getFdChildren();
		if (chilidCategory.size() > 0) {
			for (KmForumCategory kmForumCategory : chilidCategory) {
				if (!ids.contains(kmForumCategory.getFdId())) {
					ids.add(kmForumCategory.getFdId());
				}
				expendAllChildren(kmForumCategory, ids);
			}
		} else {
			if (!ids.contains(category.getFdId())) {
				ids.add(category.getFdId());
			}
		}
	}
}
