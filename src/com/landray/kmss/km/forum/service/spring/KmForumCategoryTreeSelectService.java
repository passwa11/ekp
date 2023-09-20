package com.landray.kmss.km.forum.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.km.forum.service.IKmForumCategoryService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class KmForumCategoryTreeSelectService implements IXMLDataBean {

	private IKmForumCategoryService kmForumCategoryService;

	@Override
    public List getDataList(RequestContext xmlContext) throws Exception {
		List rtnList = new ArrayList();
		String whereBlock = null;
		String categoryId = xmlContext.getRequest().getParameter("categoryId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy("kmForumCategory.fdOrder asc");

		if (!StringUtil.isNull(categoryId)){
			whereBlock = "kmForumCategory.hbmParent.fdId = :categoryId";
			hqlInfo.setParameter("categoryId", categoryId);
		}else{
			whereBlock = "kmForumCategory.hbmParent is null ";
		}
		hqlInfo.setWhereBlock(whereBlock);
		List kmForumCategorys = kmForumCategoryService.findValue(hqlInfo);
		KMSSUser curUser = UserUtil.getKMSSUser(xmlContext.getRequest());
		List curOrgs = curUser.getUserAuthInfo().getAuthOrgIds();
		for (int i = 0; i < kmForumCategorys.size(); i++) {
			KmForumCategory info = (KmForumCategory) kmForumCategorys.get(i);
			HashMap node = new HashMap();
			node.put("text", info.getFdName());
			node.put("value", info.getFdId());
			if (StringUtil.isNotNull(categoryId)) {
				if (curUser.isAdmin()) {
					node.put("isShowCheckBox", true);
				} else {
					boolean isShowCheckBox=true;
					List tmpList = info.getAuthAllReaders();
					if (tmpList != null && !tmpList.isEmpty()) {
						tmpList.addAll(info.getAuthAllEditors());
						if (tmpList != null && !tmpList.isEmpty()) {
							String[] tmpOrgs = ArrayUtil.joinProperty(tmpList,
									"fdId", ";");
							if (StringUtil.isNotNull(tmpOrgs[0])) {
								tmpOrgs = tmpOrgs[0].split(";");
								if (!ArrayUtil.isListIntersect(curOrgs,
										ArrayUtil.convertArrayToList(tmpOrgs))) {
									isShowCheckBox=false;
								} 
							}
						}
					} 
					node.put("isShowCheckBox", isShowCheckBox);
				}
			} else {
				node.put("isShowCheckBox", false);
			}
			node.put("beanName", "kmForumCategoryTreeSelectService&categoryId="
					+ info.getFdId());
			node.put("nodeType", "CATEGORY");
			rtnList.add(node);
		}
		return rtnList;
	}

	public void setKmForumCategoryService(
			IKmForumCategoryService kmForumCategoryService) {
		this.kmForumCategoryService = kmForumCategoryService;
	}

}
