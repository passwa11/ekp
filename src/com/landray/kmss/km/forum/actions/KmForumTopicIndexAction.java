package com.landray.kmss.km.forum.actions;

import com.landray.kmss.common.actions.DataAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.dao.IHQLBuilder;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.forum.model.KmForumConfig;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.km.forum.service.IKmForumCategoryService;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.km.forum.service.IkmForumIndexDataService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建日期 2006-Sep-07
 * 
 * @author 吴兵
 */
public class KmForumTopicIndexAction extends DataAction {
	protected IKmForumTopicService kmForumTopicService;
	protected IKmForumCategoryService kmForumCategoryService;
	private IHQLBuilder hqlBuilder;
	protected IkmForumIndexDataService kmForumIndexDataService; 
	
	protected IkmForumIndexDataService getIndexDataServiceImp(HttpServletRequest request) {
		if (kmForumIndexDataService == null) {
            kmForumIndexDataService = (IkmForumIndexDataService) getBean("kmForumIndexDataService");
        }
		return kmForumIndexDataService;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmForumTopicService == null) {
            kmForumTopicService = (IKmForumTopicService) getBean("kmForumTopicService");
        }
		return kmForumTopicService;
	}

	@Override
	protected IKmForumCategoryService getCategoryServiceImp(HttpServletRequest request) {
		if (kmForumCategoryService == null) {
            kmForumCategoryService = (IKmForumCategoryService) getBean("kmForumCategoryService");
        }
		return kmForumCategoryService;
	}

	public IHQLBuilder getHqlBuilder() {
		if (hqlBuilder == null) {
            hqlBuilder = (IHQLBuilder) getBean("kmssAuthHQLBuilder");
        }
		return hqlBuilder;
	}

	public void setHqlBuilder(IHQLBuilder hqlBuilder) {
		this.hqlBuilder = hqlBuilder;
	}

	@Override
	protected String getParentProperty() {
		return "kmForumCategory";
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		// 查询器
		String whereBlock = StringUtil.isNull(hqlInfo.getWhereBlock()) ? "1=1"
				: hqlInfo.getWhereBlock();
		CriteriaValue criteriaValue = new CriteriaValue(request);
		String topic = criteriaValue.poll("myTopic");
		String category = criteriaValue.poll("docCategory");
		boolean joinPoster = false;
		boolean joinCategory = false;
		if (StringUtil.isNotNull(topic)) {
			// 我发表的
			if ("create".equals(topic)) {
				joinPoster = true;
				whereBlock += " and fdPoster.fdId=:userId";
				hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
			}
			// 我回帖的
			else if ("attend".equals(topic)) {
				joinPoster = true;
				StringBuffer sb = new StringBuffer();
				sb
						.append(" and kmForumTopic.fdId in (select kmForumTopic from KmForumTopic as kmForumTopic ");
				sb
						.append(" inner join kmForumTopic.forumPosts as kmForumPost ");
				sb.append(" where fdPoster.fdId=:fdMyPosterId ");
				sb.append(" and kmForumPost.fdFloor>1) ");
				whereBlock += sb.toString();
				hqlInfo.setParameter("fdMyPosterId", UserUtil.getUser()
						.getFdId());
			}
		}
		// 分类
		if (StringUtil.isNotNull(category)) {
			joinCategory = true;
			whereBlock += " and kmForumCategory.fdHierarchyId like :categoryId";
			hqlInfo.setParameter("categoryId", "%" + category + "%");
		}
		// 帖子状态
		if (StringUtil.isNotNull(topic) && !"create".equals(topic)) {
			whereBlock += " and kmForumTopic.fdStatus!=:status";
			hqlInfo.setParameter("status", "10");
		} else {
			String status = criteriaValue.poll("fdStatus");
			if(StringUtil.isNull(topic) && StringUtil.isNull(status)) {
				joinPoster = true;
				whereBlock += " and ((fdPoster.fdId=:userId and kmForumTopic.fdStatus =:status) or kmForumTopic.fdStatus!=:status)";
				hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
				hqlInfo.setParameter("status", "10");
			}
			if (StringUtil.isNotNull(status) && "10".equals(status)) {
				if(StringUtil.isNull(topic)) {
					joinPoster = true;
					whereBlock += " and fdPoster.fdId=:userId";
					hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
				}
				whereBlock += " and kmForumTopic.fdStatus =:status";
				hqlInfo.setParameter("status", "10");
			} else if (StringUtil.isNotNull(status) && "30".equals(status)) {
				whereBlock += " and kmForumTopic.fdStatus!=:status";
				hqlInfo.setParameter("status", "10");
			}
		}

		// 版块
		String categoryId = criteriaValue.poll("categoryId");
		if (StringUtil.isNotNull(categoryId)) {
			// 为兼容老数据看是否需要更新所在树链的层级id
			((IKmForumTopicService) getServiceImp(request))
					.updateHierarchyId(categoryId);
			
			List<String> cateIds = getCategoryServiceImp(request).expentCategoryToModuleIds(request,categoryId);
			cateIds.add(categoryId);
			joinCategory = true;
			String hqlFrame = "kmForumCategory.fdId in(:categoryIds)";
			hqlInfo.setParameter("categoryIds",cateIds);
			whereBlock += " and " + hqlFrame;
		}

		// 其他查询
		String fdOther = criteriaValue.poll("fdOther");
		if (StringUtil.isNotNull(fdOther)) {
			if ("top".equals(fdOther)) {// 置顶
				whereBlock += " and kmForumTopic.fdSticked =:fdSticked";
				hqlInfo.setParameter("fdSticked", true);
			} else if ("end".equals(fdOther)) {// 结贴
				whereBlock += " and kmForumTopic.fdStatus =:fdStatus";
				hqlInfo.setParameter("fdStatus",
						SysDocConstant.DOC_STATUS_EXPIRE);
			} else if ("hot".equals(fdOther)) {// 热帖
				whereBlock += " and kmForumTopic.fdReplyCount >=:fdReplyCount";
				hqlInfo.setParameter("fdReplyCount", Integer
						.parseInt(new KmForumConfig().getHotReplyCount()));
			} else if ("pink".equals(fdOther)) {// 精华
				whereBlock += " and kmForumTopic.fdPinked =:fdPinked";
				hqlInfo.setParameter("fdPinked", true);
			}
		}
		String orderByPara = request.getParameter("orderby");
		String orderBy = " ";
		boolean isReserve = false;
		String ordertype = request.getParameter("ordertype");
		if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
			isReserve = true;
		}
		if (StringUtil.isNull(orderByPara)) {
			orderBy = "kmForumTopic.fdSticked desc,kmForumTopic.fdPinked desc,kmForumTopic.fdLastPostTime desc";
		} else if ("fdReplyCount;fdHitCount".equals(orderByPara)) {
			if (isReserve) {
				orderBy += "kmForumTopic.fdReplyCount desc,kmForumTopic.fdHitCount desc";
			} else {
				orderBy += "kmForumTopic.fdReplyCount,kmForumTopic.fdHitCount";
			}
			orderBy = "kmForumTopic.fdSticked desc," + orderBy;
		} else {
			if(!orderByPara.startsWith("kmForumTopic")) {
				orderByPara = "kmForumTopic." + orderByPara;
			}
			if (isReserve) {
				orderBy += orderByPara + " desc";
			} else {
				orderBy += orderByPara;
			}
			orderBy = "kmForumTopic.fdSticked desc," + orderBy;
		}
		hqlInfo.setOrderBy(orderBy);
		hqlInfo.setWhereBlock(whereBlock);
		CriteriaUtil.buildHql(criteriaValue, hqlInfo, KmForumTopic.class);
		
		//生态组织--外部人员权限控制 
		if (SysOrgEcoUtil.IS_ENABLED_ECO && SysOrgEcoUtil.isExternal()) {
			joinCategory = true;
			hqlInfo.setJoinBlock(" left join kmForumCategory.authAllReaders allReads left join kmForumCategory.authReaders readers  left join kmForumCategory.authAllEditors editors ");
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),  " and ", "(allReads.fdId in (:orgIds) or readers.fdId in (:orgIds) or editors.fdId in (:orgIds))"));
			hqlInfo.setParameter("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		}

		// 处理JOIN
		String joinBlock = hqlInfo.getJoinBlock();
		if(joinPoster) {
			joinBlock = StringUtil.linkString(joinBlock, " " , "left join kmForumTopic.fdPoster fdPoster");
		}
		if(joinCategory) {
			joinBlock = StringUtil.linkString(joinBlock, " " , "left join kmForumTopic.kmForumCategory kmForumCategory");
		}
		hqlInfo.setJoinBlock(joinBlock);
	}

	public ActionForward checkAuth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("content-type", "application/json;charset=utf-8");
		PrintWriter out = response.getWriter();

		JSONObject resultJson = new JSONObject();
		boolean isAdmin = false;
		boolean isRoleDelete = false;
		boolean isRoleMove = false;
		// 是否版主
		boolean isCategoryRule = false;
		if (UserUtil.getKMSSUser().isAdmin()) {
			isAdmin = true;
		}
		if (ArrayUtil.isListIntersect(UserUtil.getKMSSUser().getUserAuthInfo()
				.getAuthRoleAliases(), Arrays
				.asList(new String[] { "ROLE_KMFORUMTOPIC_TOPICDELETE" }))) {
			isRoleDelete = true;
		}
		if (ArrayUtil.isListIntersect(UserUtil.getKMSSUser().getUserAuthInfo()
				.getAuthRoleAliases(), Arrays
				.asList(new String[] { "ROLE_KMFORUMTOPIC_TOPICMOVE" }))) {
			isRoleMove = true;
		}
		String forumId = request.getParameter("forumId");
		if (StringUtil.isNotNull(forumId)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo
					.setModelName("com.landray.kmss.km.forum.model.KmForumCategory");
			hqlInfo.setAuthCheckType("cateManager");
			String tableName = "kmForumCategory";
			hqlInfo.setSelectBlock(tableName + ".fdId");
			hqlInfo.setWhereBlock(tableName + ".fdId=:fdId");
			hqlInfo.setParameter("fdId", forumId);
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_NO);
			HQLWrapper hqlWrapper = getHqlBuilder().buildQueryHQL(hqlInfo);
			Query query = getServiceImp(request).getBaseDao()
					.getHibernateSession().createQuery(hqlWrapper.getHql());
			query.setCacheable(true);
			query.setCacheMode(CacheMode.NORMAL);
			query.setCacheRegion("km-forum");
			HQLUtil.setParameters(query, hqlWrapper.getParameterList());
			boolean rtnVal = query.list().size() > 0;
			if (!rtnVal) {
				if (!getServiceImp(request).getBaseDao().isExist(
						hqlInfo.getModelName(), forumId)) {
					throw new NoRecordException();
				}
			}
			if (rtnVal) {
				isCategoryRule = true;
			}
		}
		// 板块下发帖权限
		if (StringUtil.isNotNull(forumId)) {
			String addUrl = "/km/forum/km_forum/kmForumPost.do?method=add&fdForumId="
					+ forumId;
			if (UserUtil.checkAuthentication(addUrl, "")) {
				resultJson.put("canAdd", true);
			} else {
				resultJson.put("canAdd", false);
			}
		}
		if (isAdmin || isRoleDelete || isCategoryRule) {
			resultJson.put("canDelete", true);
		} else {
			resultJson.put("canDelete", false);
		}
		if (isAdmin || isRoleMove || isCategoryRule) {
			resultJson.put("canMove", true);
		} else {
			resultJson.put("canMove", false);
		}
		if (isAdmin || isCategoryRule) {
			resultJson.put("canBatchConclude", true);
		} else {
			resultJson.put("canBatchConclude", false);
		}
		out.print(resultJson);
		return null;
	}

	public ActionForward showKeydataUsed(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();

		String whereBlock = "";
		String keydataIdStr = "";
		String keydataId = request.getParameter("keydataId");
		if (StringUtil.isNotNull(keydataId)) {
			keydataIdStr = " and kmKeydataUsed.keydataId = '" + keydataId + "'";
		}
		whereBlock += "kmForumTopic.fdId in (select kmForumPost.kmForumTopic.fdId from com.landray.kmss.km.forum.model.KmForumPost kmForumPost where kmForumPost.fdId in (select kmKeydataUsed.modelId from com.landray.kmss.km.keydata.base.model.KmKeydataUsed kmKeydataUsed"
				+ " where kmKeydataUsed.formName='kmForumPostForm'"
				+ keydataIdStr + "))";

		// 以下部分可直接参考list中的逻辑代码
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			String whereBlockOri = hqlInfo.getWhereBlock();
			if (StringUtil.isNotNull(whereBlockOri)) {
				whereBlock = whereBlockOri + " and (" + whereBlock + ")";
			}
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listChildren", mapping, form, request,
					response);
		}
	}
	
	
	public ActionForward getData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		List list=getIndexDataServiceImp(request).getUserInfo();
		JSONArray jsonArr = new JSONArray();
		JSONObject obj = new JSONObject();
		Map map = (HashMap) list.get(0);
		obj.accumulate("name", map.get("name"));
		obj.accumulate("headurl", map.get("headurl"));
		obj.accumulate("pcount", map.get("pcount"));
		obj.accumulate("rcount", map.get("rcount"));
		obj.accumulate("score", map.get("score"));
		obj.accumulate("level", map.get("level"));
		jsonArr.add(obj);
		// 添加日志信息
		String modelName = getServiceImp(request).getModelName();
		if (UserOperHelper.allowLogOper("getData", modelName)) {
			UserOperContentHelper.putFind((String) map.get("id"),
					(String) map.get("name"), modelName);
			UserOperContentHelper.putFind((String) map.get("id"),
					(String) map.get("headurl"), modelName);
			UserOperContentHelper.putFind((String) map.get("id"),
					(String) map.get("pcount"), modelName);
			UserOperContentHelper.putFind((String) map.get("id"),
					(String) map.get("rcount"), modelName);
			UserOperContentHelper.putFind((String) map.get("id"),
					(String) map.get("score"), modelName);
			UserOperContentHelper.putFind((String) map.get("id"),
					(String) map.get("level"), modelName);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonArr.toString());// 结果
		return null;
	}
}
