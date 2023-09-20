package com.landray.kmss.km.forum.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.km.forum.service.IKmForumCategoryService;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KmForumCategoryTeeService implements IXMLDataBean {

	private IKmForumCategoryService kmForumCategoryService;

	@Override
    public List getDataList(RequestContext xmlContext) throws Exception {
		List rtnList = new ArrayList();
		String whereBlock = null;
		String categoryId = xmlContext.getRequest().getParameter("categoryId");
		String isCategory = xmlContext.getRequest().getParameter("isCategory");
		String isManage = xmlContext.getRequest().getParameter("isManage");
		String fdPinked = xmlContext.getRequest().getParameter("fdPinked");
		String fromPrtlet = xmlContext.getRequest().getParameter("fromPrtlet");
		String fdName_lang = SysLangUtil.getLangFieldName("fdName");
		if (!StringUtil.isNull(categoryId)) {
			List kmForumCategorys = new ArrayList();
			String hql = "select kmForumCategory." + fdName_lang
					+ ",kmForumCategory.fdName";
			if(UserUtil.getKMSSUser().isAdmin()||UserUtil.checkRole("ROLE_KMFORUMCATE_ADMIN")||"true".equals(fromPrtlet)){
				hql += ",kmForumCategory.fdId from com.landray.kmss.km.forum.model.KmForumCategory kmForumCategory  where kmForumCategory.hbmParent.fdId = :categoryId  order by kmForumCategory.fdOrder asc";
                Query query = kmForumCategoryService.getBaseDao()
    					.getHibernateSession().createQuery(hql);
				query.setCacheable(true);
				query.setCacheMode(CacheMode.NORMAL);
				query.setCacheRegion("km-forum");
                query.setParameter("categoryId", categoryId);
                kmForumCategorys = query.list();
			}else{
				hql += ",kmForumCategory.fdId from com.landray.kmss.km.forum.model.KmForumCategory kmForumCategory  left join kmForumCategory"
						+ ".authAllEditors editors where kmForumCategory.hbmParent.fdId = :categoryId and (editors.fdId in (:orgIds))  order by kmForumCategory.fdOrder asc";
				Query query = kmForumCategoryService.getBaseDao()
						.getHibernateSession().createQuery(hql);
				query.setCacheable(true);
				query.setCacheMode(CacheMode.NORMAL);
				query.setCacheRegion("km-forum");
				query.setParameter("categoryId", categoryId);
				query.setParameterList("orgIds", UserUtil.getKMSSUser()
						.getUserAuthInfo().getAuthOrgIds());
				kmForumCategorys  = query.list();
			}
			
			for (int i = 0; i < kmForumCategorys.size(); i++) {
				Object[] info = (Object[]) kmForumCategorys.get(i);
				HashMap node = new HashMap();
				if (info[0] != null
						&& StringUtil.isNotNull(info[0].toString())) {
					node.put("text", info[0].toString());
				} else {
					node.put("text", info[1].toString());
				}
				node.put("value", info[2]);
				StringBuffer href = new StringBuffer();
				StringBuffer beanName = new StringBuffer();
				if (StringUtil.isNotNull(isManage) && ("true").equals(isManage)) {
					beanName.append("kmForumCategoryTeeService&categoryId="
							+ info[2] + "&isManage=" + isManage);
					href.append(xmlContext.getContextPath()).append(
							"/km/forum/km_forum_cate/kmForumCategory.do?method=manage&fdId="
									+ info[2]);
				} else if (StringUtil.isNotNull(fdPinked)) {
					beanName.append("kmForumCategoryTeeService&categoryId="
							+ info[2] + "&fdPinked=" + fdPinked);
					href.append(xmlContext.getContextPath()).append(
							"/km/forum/km_forum/kmForumTopic.do?method=list&fdForumId="
									+ info[2] + "&fdPinked=" + fdPinked);
				} else if (StringUtil.isNotNull(fromPrtlet)) {
					beanName.append("kmForumCategoryTeeService&categoryId="
							+ info[2] + "&fromPrtlet=true");
					href.append(xmlContext.getContextPath()).append(
							"/km/forum/km_forum/kmForumTopic.do?method=list&fdForumId="
									+ info[2]);
				}else {
					beanName.append("kmForumCategoryTeeService&categoryId="
							+ info[2]);
					href.append(xmlContext.getContextPath()).append(
							"/km/forum/km_forum/kmForumTopic.do?method=list&fdForumId="
									+ info[2]);
				}
				node.put("beanName", beanName);
				node.put("href", href);
				rtnList.add(node);
			}
		} else {
			whereBlock = "kmForumCategory.hbmParent is null ";
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setSelectBlock("kmForumCategory." + fdName_lang
					+ ",kmForumCategory.fdName,kmForumCategory.fdId");
			hqlInfo.setOrderBy("kmForumCategory.fdOrder asc");
			List kmForumCategorys = kmForumCategoryService.findValue(hqlInfo);
			for (int i = 0; i < kmForumCategorys.size(); i++) {
				Object[] info = (Object[]) kmForumCategorys.get(i);
				HashMap node = new HashMap();
				if (info[0] != null
						&& StringUtil.isNotNull(info[0].toString())) {
					node.put("text", info[0].toString());
				} else {
					node.put("text", info[1].toString());
				}
				StringBuffer beanName = new StringBuffer();
				if (StringUtil.isNotNull(isManage) && ("true").equals(isManage)) {
					beanName.append("kmForumCategoryTeeService&categoryId="
							+ info[2] + "&isManage=" + isManage);
				} else if (StringUtil.isNotNull(fdPinked)) {
					beanName.append("kmForumCategoryTeeService&categoryId="
							+ info[2] + "&fdPinked=" + fdPinked);
				} else if (StringUtil.isNotNull(fromPrtlet)) {
					beanName.append("kmForumCategoryTeeService&categoryId="
							+ info[2] + "&fromPrtlet=true");
				}else {
					beanName.append("kmForumCategoryTeeService&categoryId="
							+ info[2]);
				}
				node.put("beanName", beanName);
				
				int categoryParentSize = -1 ;
				
				if ("true".equals(fromPrtlet)){// 如果来自门户配置选择板块则  判断是否有孩子节点
					whereBlock = "kmForumCategory.hbmParent.fdId =:parentId"; 
					hqlInfo.setParameter("parentId", info[2]);
					hqlInfo.setWhereBlock(whereBlock);
					List categoryParentList = kmForumCategoryService.findValue(hqlInfo);
					categoryParentSize = categoryParentList.size();
				}
				if ((StringUtil.isNotNull(isCategory) && ("true").equals(isCategory)) ||
						(categoryParentSize>0) ) {//如果有孩子节点则需要显示isShowCheckBox
					node.put("value", info[2]);
					node.put("isShowCheckBox", true);
				}
				if (xmlContext.isCloud()) {
					// 能选中的版块才传到返回
					if ("true".equals(isCategory) || categoryParentSize > 0) {
						rtnList.add(node);
					}
				} else {
					rtnList.add(node);
				}
			}
		}
		return rtnList;
	}

	public void setKmForumCategoryService(
			IKmForumCategoryService kmForumCategoryService) {
		this.kmForumCategoryService = kmForumCategoryService;
	}

}
