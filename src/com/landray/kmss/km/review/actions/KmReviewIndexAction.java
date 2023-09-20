/**
 *
 */
package com.landray.kmss.km.review.actions;

import com.landray.kmss.common.actions.DataAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.review.Constant;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.authorization.service.ISysAuthAreaService;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.lbpm.engine.builder.WorkitemInstance;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmUtil;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmSummaryApprovalService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.person.model.SysPersonFavoriteCategory;
import com.landray.kmss.sys.person.service.ISysPersonFavoriteCategoryService;
import com.landray.kmss.sys.person.service.util.FavoriteCategoryUtil;
import com.landray.kmss.sys.profile.model.ShowConfig;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.query.NativeQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 二级页面主页对应action
 *
 * @author 傅游翔
 *
 */
public class KmReviewIndexAction extends DataAction {

	private IKmReviewMainService kmReviewMainService;

	@Override
	protected IKmReviewMainService getServiceImp(HttpServletRequest request) {
		if (kmReviewMainService == null) {
			kmReviewMainService = (IKmReviewMainService) getBean("kmReviewMainService");
		}
		return kmReviewMainService;
	}
	private ISysPersonFavoriteCategoryService sysPersonFavoriteCategoryService;

	protected ISysPersonFavoriteCategoryService getSysPersonFavoriteCategoryServiceImp(
			HttpServletRequest request) {
		if (sysPersonFavoriteCategoryService == null) {
			sysPersonFavoriteCategoryService = (ISysPersonFavoriteCategoryService) getBean("sysPersonFavoriteCategoryService");
		}
		return sysPersonFavoriteCategoryService;
	}

	private ILbpmSummaryApprovalService lbpmSummaryApprovalService = (ILbpmSummaryApprovalService) SpringBeanUtil
			.getBean("lbpmSummaryApprovalService");

	@Override
	protected String getParentProperty() {
		return "fdTemplate";
	}

	private ISysCategoryMainService categoryMainService;

	@Override
	protected IBaseService getCategoryServiceImp(HttpServletRequest request) {
		if (categoryMainService == null) {
            categoryMainService = (ISysCategoryMainService) getBean("sysCategoryMainService");
        }
		return categoryMainService;
	}

	private ISysOrgElementService sysOrgElementService;

	protected ISysOrgElementService getSysOrgElementService(HttpServletRequest request) {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	/**
	 * 获取所有状态文档数量
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward countAllStatus(ActionMapping mapping, ActionForm form,
										HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TimeCounter.logCurrentTime("Action-countAllStatus", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			this.changeFindPageHQLInfo(request, hqlInfo);
			hqlInfo.setSelectBlock("count(kmReviewMain.fdId), kmReviewMain.docStatus");
			hqlInfo.setOrderBy(null);
			String whereBlock = hqlInfo.getWhereBlock();
			hqlInfo.setWhereBlock(whereBlock);
			List<Object> list = getServiceImp(request).countAllStatus(hqlInfo);
			request.setAttribute("lui-source", list);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-countAllStatus", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	/**
	 * 获取nav多标签文档数量
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward count(ActionMapping mapping, ActionForm form,
							   HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TimeCounter.logCurrentTime("Action-count", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("count(*)");
			this.changeFindPageHQLInfo(request, hqlInfo);
			hqlInfo.setOrderBy(null);
			@SuppressWarnings("unchecked")
			Object obj = getServiceImp(request).findFirstOne(hqlInfo);
			String count = "0";
			if (null != obj) {
				count = obj.toString();
			}
			request.setAttribute("lui-source", new JSONObject().element(
					"count", count));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-count", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
										String curOrderBy) throws Exception {
		String orderBy = request.getParameter("orderby");
		String ordertype = request.getParameter("ordertype");
		if("arrivalTime".equals(orderBy) || "resolveTime".equals(orderBy)){
			orderBy = "docCreateTime";
		}
		boolean isReserve = false;
		if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
			isReserve = true;
		}
		String orderById = ",kmReviewMain.fdId";
		if (StringUtil.isNotNull(orderBy)) {
			orderBy = "kmReviewMain." + orderBy ;
			String orderbyDesc = orderBy + " desc" + orderById + " desc";
			String orderbyAll = isReserve ? orderbyDesc : orderBy + orderById;
			return orderbyAll;
		}
		return " kmReviewMain.docCreateTime desc" + orderById + " desc ";
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		CriteriaValue cv = new CriteriaValue(request);
		String categoryId = cv.poll("fdTemplate");
		String mydoc = cv.poll("mydoc");
		String doctype = cv.poll("doctype");
		String[] docStatusVal = cv.polls("docStatus");
		String whereStr = hqlInfo.getWhereBlock();
		StringBuilder hql = new StringBuilder(StringUtil.isNull(whereStr) ? "1=1 " : whereStr);
		String[] fdIsFiles = cv.polls("fdIsFile");
		if (fdIsFiles != null && fdIsFiles.length == 1) {
			String fdIsFile = fdIsFiles[0];
			if ("1".equals(fdIsFile)) {
				hql.append(" and kmReviewMain.fdIsFiling =:fdIsFiling");
				hqlInfo.setParameter("fdIsFiling", true);
			} else if ("0".equals(fdIsFile)) {
				hql.append(
						" and (kmReviewMain.fdIsFiling =:fdIsFiling or kmReviewMain.fdIsFiling is null)");
				hqlInfo.setParameter("fdIsFiling", false);
			}
		}
		String docStatus = "";
		if (docStatusVal != null) {
			if (docStatusVal.length > 1) {
				List<String> __list = new ArrayList<String>();
				for (int i = 0; i < docStatusVal.length; i++) {
					__list.add(docStatusVal[i]);
				}
				hql.append(" and " + HQLUtil.buildLogicIN("kmReviewMain.docStatus", __list));
			} else {
				docStatus = docStatusVal[0];
				if (!("41".equals(docStatus))) {
					hql.append(" and kmReviewMain.docStatus =:docStatus");
					hqlInfo.setParameter("docStatus", docStatus);
					if ("10".equals(docStatus) && "true".equals(request.getParameter("owner"))) {
						hql.append(" and kmReviewMain.docCreator.fdId=:docCreator");
						hqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());
					}
				}
			}
		}

		if (StringUtil.isNotNull(mydoc)) {
			mydoc = mydoc.trim().toLowerCase();
			if ("create".equals(mydoc)) { // 我启动的流程,不包含废弃和归档
				hql.append(
						" and kmReviewMain.docCreator.fdId=:docCreator ");
				hqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			}
		}
		if (StringUtil.isNotNull(doctype)) {
			if ("examine".equals(doctype)) { // 流程审核
				if (StringUtil.isNotNull(docStatus)) {
					hql.append(" and kmReviewMain.docStatus =:docStatus");
					hqlInfo.setParameter("docStatus", docStatus);
				} else {
					hql.append(" and (kmReviewMain.docStatus = '20' or kmReviewMain.docStatus = '11')");
				}
			} else if ("follow".equals(doctype)) { // 流程跟踪
				StringBuffer buff = new StringBuffer();
				if (StringUtil.isNotNull(hqlInfo.getJoinBlock())) {
					buff.append(hqlInfo.getJoinBlock());
				}
				buff.append(", com.landray.kmss.sys.lbpmservice.support.model.LbpmFollow lbpmFollow ");
				hqlInfo.setJoinBlock(buff.toString());
				hql.append(" and lbpmFollow.fdProcessId = kmReviewMain.fdId and lbpmFollow.fdWatcher.fdId=:fdWatcher");
				hqlInfo.setParameter("fdWatcher", UserUtil.getUser().getFdId());
			} else if ("feedback".equals(doctype)) { // 流程反馈
				if (StringUtil.isNull(docStatus)) {
					hql.append(" and (kmReviewMain.docStatus = '30' or kmReviewMain.docStatus = '31')");
				} else {
					if ("41".equals(docStatus)) { // 未反馈
						hql.append(" and kmReviewMain.docStatus = '30'");
					}
				}
			}
		}
		if (StringUtil.isNull(categoryId)) {
			categoryId = request.getParameter("categoryId");
		}

		if (StringUtil.isNotNull(categoryId)) {
			// 默认 show all
			SysCategoryMain category = (SysCategoryMain) getCategoryServiceImp(
					request).findByPrimaryKey(categoryId, null, true);
			if (category != null) {
				hql
						.append(" and kmReviewMain.fdTemplate.docCategory.fdHierarchyId like :category");
				hqlInfo.setParameter("category", category.getFdHierarchyId()
						+ "%");
			} else {
				hql.append(" and kmReviewMain.fdTemplate.fdId = :template");
				hqlInfo.setParameter("template", categoryId);
			}
		}
		String[] docCreator = cv.polls("docCreator");
		if(docCreator!=null){
			if (docCreator.length > 1) {
				HQLWrapper hqlW = HQLUtil.buildPreparedLogicIN("kmReviewMain.docCreator.fdId","kmReviewMain"+ "0_", Arrays.asList(docCreator));
				hql.append( " and "+hqlW.getHql());
				hqlInfo.setParameter(hqlW.getParameterList());
			}else{
				hql.append(" and kmReviewMain.docCreator.fdId = :creator");
				hqlInfo.setParameter("creator", docCreator[0]);
			}
		}
		String docProperties = cv.poll("docProperties");
		if (StringUtil.isNotNull(docProperties)) {
			hql.append(" and docProperties.fdId = :docProperties");
			if(StringUtil.isNotNull(hqlInfo.getJoinBlock())){
				hqlInfo.setJoinBlock(hqlInfo.getJoinBlock()+" inner join kmReviewMain.docProperties docProperties");
			}else{
				hqlInfo.setJoinBlock(" inner join kmReviewMain.docProperties docProperties");
			}
			hqlInfo.setParameter("docProperties", docProperties);
		}
		CriteriaUtil.buildHql(cv, hqlInfo, KmReviewMain.class);
		if (StringUtil.isNotNull(hqlInfo.getWhereBlock())) {
			hql.append(" and (").append(hqlInfo.getWhereBlock());
		}
		//ai判断逻辑
		String ai = cv.poll("ai");
		if(StringUtil.isNotNull(ai)&&"true".equals(ai)){
			String docSubject = cv.poll("docSubject");
			if(StringUtil.isNotNull(docSubject)){
				if(docSubject.startsWith(";")) {
                    docSubject = docSubject.replaceFirst(";", "");
                }
				if(docSubject.indexOf(";")>=0){
					String[] ds = docSubject.split("[,;]");
					StringBuffer aiw = new StringBuffer();
					for(int i=0;i<ds.length;i++){
						//if(StringUtil.isNotNull(ds[i])&&ds.length>1&&i<2){限制2个关键字
						if(StringUtil.isNotNull(ds[i])){
							if(i==0){
								aiw.append(" or (kmReviewMain.docSubject like '%"+ds[i]+"%' or kmReviewMain.fdTemplate.fdName like '%"+ds[i]+"%'");
							}else if(i==ds.length-1){
								aiw.append(" or kmReviewMain.docSubject like '%"+ds[i]+"%' or kmReviewMain.fdTemplate.fdName like '%"+ds[i]+"%'))");
							}else{
								aiw.append(" or kmReviewMain.docSubject like '%"+ds[i]+"%' or kmReviewMain.fdTemplate.fdName like '%"+ds[i]+"%'");
							}
						}
					}
					hql.append(aiw.toString());
				}else{
					hql.append(" or kmReviewMain.fdTemplate.fdName like :tname)");
					hqlInfo.setParameter("tname", "%"+docSubject.trim()+"%");
				}
			}
		}else{
			if (StringUtil.isNotNull(hqlInfo.getWhereBlock())) {
                hql.append(")");
            }
		}
		hqlInfo.setWhereBlock(hql.toString());
		//当前处理人
		String[] fdCurrentHandler = cv.polls("fdCurrentHandler");
		if(fdCurrentHandler!=null){
			LbpmUtil.buildLimitBlockForMyApproval("kmReviewMain", hqlInfo,
					getServiceImp(request).getOrgAndPost(request, fdCurrentHandler));
			hqlInfo.setAuthCheckType(null);
		}
		//已处理人
		String fdAlreadyHandler = cv.poll("fdAlreadyHandler");
		if(StringUtil.isNotNull(fdAlreadyHandler)){
			LbpmUtil.buildLimitBlockForMyApproved("kmReviewMain", hqlInfo,fdAlreadyHandler);
			hqlInfo.setAuthCheckType(null);
		}
		buildHomeZoneHql(cv, hqlInfo, request);
	}

	/**
	 * @Author admin
	 * @Version  1.0
	 * @Description 统计流程各种状态的数量
	 * @param request
	 * @Return java.lang.String
	 * @Exception
	 * @Date 2020-12-14 16:12
	 */
	public String getCount(HttpServletRequest request) throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		String count = "0";
		hqlInfo.setGettingCount(true);
		changeFindPageHQLInfo(request, hqlInfo);
		List<Long> list = getServiceImp(request).findValue(hqlInfo);
		if (list.size() > 0) {
			count = list.get(0).toString();
		}
		return count;
	}


	private void buildHomeZoneHql(CriteriaValue cv, HQLInfo hqlInfo,
								  HttpServletRequest request) throws Exception {
		String whereStr = hqlInfo.getWhereBlock();
		StringBuilder where = new StringBuilder(
				StringUtil.isNull(whereStr) ? "1=1 " : whereStr);
		String self = cv.poll("selfdoc");
		String tadoc = cv.poll("tadoc");
		boolean isSelfDoc = StringUtil.isNotNull(self);
		String mydoc = isSelfDoc ? self : tadoc;
		String userId = isSelfDoc ? UserUtil.getUser().getFdId() : request
				.getParameter("userid");

		if (StringUtil.isNull(userId) || StringUtil.isNull(mydoc)) {
			return;
		}

		if (StringUtil.isNotNull(mydoc)) {
			// 我启动的流程
			mydoc = mydoc.trim().toLowerCase();
			if ("create".equals(mydoc)) {
				where.append(" and kmReviewMain.docCreator.fdId=:docCreator");
				hqlInfo.setParameter("docCreator", userId);
				hqlInfo.setWhereBlock(where.toString());
			} else if ("approval".equals(mydoc)) {
				List<String> userIds = new ArrayList<String>();
				// #55126 修复 待办列表显示有，在“我的流程---待我审的”一栏中，却显示为空。
				if (isSelfDoc) {
					userIds = getRelationOrgIds(UserUtil.getUser());
				} else {
					SysOrgElement element = (SysOrgElement) getSysOrgElementService(
							request).findByPrimaryKey(userId);
					userIds = getRelationOrgIds(element);
					// userIds.add(userId);
				}
				LbpmUtil.buildLimitBlockForMyApproval("kmReviewMain", hqlInfo,userIds);
				hqlInfo.setAuthCheckType(null);
			} else if ("approved".equals(mydoc)) {
				LbpmUtil.buildLimitBlockForMyApproved("kmReviewMain", hqlInfo, userId);
				hqlInfo.setAuthCheckType(null);
			}
		}
	}

	/**
	 * @param person
	 * @return 当前用户ID和所属岗位ID
	 */
	@SuppressWarnings("unchecked")
	private List<String> getRelationOrgIds(SysOrgElement element) {
		List<String> results = new ArrayList<String>();
		results.add(element.getFdId());

		List<SysOrgElement> posts = element.getFdPosts();
		for (SysOrgElement post : posts) {
			results.add(post.getFdId());
		}
		return results;
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
			keydataIdStr = " and kmKeydataUsed.keydataId = :keydataId";
		}
		// 从kmKeydataUsed表中查找使用了‘keydataId’数据的对应主文档ID（这里指流程管理主文档ID），“kmReviewMainForm”指的是模块的form名称
		whereBlock += "kmReviewMain.fdId in (select kmKeydataUsed.modelId from com.landray.kmss.km.keydata.base.model.KmKeydataUsed kmKeydataUsed"
				+ " where kmKeydataUsed.formName='kmReviewMainForm'"
				+ keydataIdStr + ")";

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
			if (StringUtil.isNotNull(keydataId)) {
				hqlInfo.setParameter("keydataId", keydataId);
			}
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
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
			return getActionForward("list", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		String forwardStr = request.getParameter("forwardStr");
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String s_pagingType = request.getParameter("pagingtype");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String pagingSetting = request.getParameter("pagingSetting");
			String mydoc = request.getParameter("q.mydoc");
			if(StringUtil.isNotNull(mydoc) && "approval".equals(mydoc)){
				Page page = getServiceImp(request).listArrival(new RequestContext(request));
				getDetailInfo(page,request);
			}else if(StringUtil.isNotNull(mydoc) && "approved".equals(mydoc)){
				Page page = getServiceImp(request).listApproved(new RequestContext(request));
				getDetailInfo(page,request);
			}else{
				boolean isReserve = false;
				if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
					isReserve = true;
				}
				int pageno = 0;
				int rowsize = SysConfigParameters.getRowSize();
				if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
					pageno = Integer.parseInt(s_pageno);
				}
				if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
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
				if ("simple".equals(s_pagingType)
						|| ShowConfig.PAGING_SETTING_SIMPLE.equals(pagingSetting)) {
					hqlInfo.setPagingType(HQLInfo.PAGING_TYPE_SIMPLE);
				}
				Page page = getServiceImp(request).findPage(hqlInfo);
				// 添加日志信息
				UserOperHelper.logFindAll(page.getList(),
						getServiceImp(request).getModelName());
				request.setAttribute("queryPage", page);
			}

		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			if (StringUtil.isNotNull(forwardStr)) {
				return getActionForward(forwardStr, mapping, form, request, response);
			}else{
				return getActionForward("list", mapping, form, request, response);
			}
		}

	}

	//封装参数返回页面
	private void getDetailInfo(Page page, HttpServletRequest request)
			throws Exception {
		IKmReviewTemplateService kmReviewTemplateService = (IKmReviewTemplateService) SpringBeanUtil.getBean("kmReviewTemplateService");
		ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
		ISysAuthAreaService sysAuthAreaService=(ISysAuthAreaService)SpringBeanUtil.getBean("sysAuthAreaService");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List kmReviewMains = page.getList();
		List<KmReviewMain> listMap = new ArrayList<KmReviewMain>();
		for (Object kmReviewMain : kmReviewMains) {
			KmReviewMain reviewMain = new KmReviewMain();
			Map<String, Object> newMap = new HashMap<String, Object>();
			Object [] km = (Object [])kmReviewMain;
			reviewMain.setFdId(km[0].toString());
			reviewMain.setDocSubject(km[1].toString());
			if(km[2] != null){
				reviewMain.setFdNumber(km[2].toString());
			}
			KmReviewTemplate kmReviewTemplate = null;
			if(km[3] != null){
				kmReviewTemplate = (KmReviewTemplate) kmReviewTemplateService.findByPrimaryKey(km[3].toString());
			}
			SysOrgPerson sysOrgPerson = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(km[5].toString());
			if(km[7] != null){
				SysAuthArea sysAuthArea = (SysAuthArea) sysAuthAreaService.findByPrimaryKey(km[7].toString());
				reviewMain.setAuthArea(sysAuthArea);
			}
			if(km[9] != null){
				reviewMain.setDocPublishTime(format.parse(km[9].toString()));
			}
			reviewMain.setFdTemplate(kmReviewTemplate);
			reviewMain.setDocCreator(sysOrgPerson);
			if(km[4] != null){
				reviewMain.setFdUseWord("1".equals(km[4].toString()));
			}
			if(km[8] != null){
				reviewMain.setDocCreateTime(format.parse(km[8].toString()));
			}
			if(km[10] != null){
				reviewMain.setFdIsFiling("1".equals(km[10].toString()));
			}
			reviewMain.setDocStatus(km[11]==null?"":km[11].toString());
			listMap.add(reviewMain);
		}
		page.setList(listMap);
		request.setAttribute("queryPage", page);
	}

	/**
	 *实现功能描述:流程首页action
	 *@return com.landray.kmss.web.action.ActionForward
	 */
	public ActionForward homeInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String modeType = "";
		try {
			/*新增参数判断调用方法的来源：
			 * 如果获取的参数是（modeType=isPortlet），则证明是部件处调用，
			 * 那么就条到部件显示的页面，不应该原有的逻辑跳转*/
			modeType = request.getParameter("modeType");
			String isHome = request.getParameter("isHome");
			String isFast = request.getParameter("isFast");
			String count = null;
			if ("true".equals(isHome)) {
				JSONObject json = new JSONObject();
				//统计待我审核数量
				JSONObject countJson = new JSONObject();
				countJson.put("weekCount",waitReview(Constant.THIS_WEEK,request));
				countJson.put("monthCount",waitReview(Constant.THIS_MONTH,request));
				json.put("review", countJson);

				//统计我已审核数量
				countJson.clear();
				countJson.put("monthCount",finishReview(Constant.THIS_MONTH,request));
				countJson.put("yearCount",finishReview(Constant.THIS_YEAR,request));
				json.put("reviewedInfo", countJson);

				//统计我起草的
				countJson.clear();
				countJson.put("monthCount",draftReview(Constant.THIS_MONTH,request));
				countJson.put("yearCount",draftReview(Constant.THIS_YEAR,request));
				json.put("draft",countJson);

				//统计全部
				countJson.clear();
				countJson.put("monthCount",allReview(Constant.THIS_MONTH,request));
				countJson.put("yearCount",allReview(Constant.THIS_YEAR,request));
				json.put("all",countJson);

				/*如果是部件参数，则跳转到部件页面，否则按当前的逻辑跳转*/
				if (!"".equals(modeType) && "isPortlet".equals(modeType)) {
					//部件页面
					request.setAttribute("statisticsHomeInfo", json);
					modeType = "statisticsHomeInfo";
				} else {
					request.setAttribute("homeInfo", json);
					modeType = "homeInfo";
				}
			}
		}catch (Exception e){
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward(modeType, mapping, form, request, response);
		}
	}
	//待我审核汇总(本周/本月)
	private String waitReview(String dateFlag,HttpServletRequest request) throws Exception{
		RequestContext requestContext = new RequestContext(request);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		requestContext.setParameter("approvalStartDate",format.format(LbpmUtil.getDateForTime(dateFlag)));
		requestContext.setParameter("approvalEndDate", format.format(new Date()));
		int count = getServiceImp(request).getArrivalListUseTotal(requestContext);
		return String.valueOf(count);
	}

	//我已审核汇总(本月/本年)
	private String finishReview(String dateFlag,HttpServletRequest request) throws Exception{
		RequestContext requestContext = new RequestContext(request);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		requestContext.setParameter("approvedStartDate",format.format(LbpmUtil.getDateForTime(dateFlag)));
		requestContext.setParameter("approvedEndDate", format.format(new Date()));
		int count = getServiceImp(request).getApprovedTotal(requestContext);
		return String.valueOf(count);
	}

	//全部汇总(本年，本月)
	private String allReview(String dateFlag,HttpServletRequest request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		hqlInfo.setModelName(KmReviewMain.class.getName());
		String where = " kmReviewMain.docCreateTime between :fdStartDate and :fdNowDate";
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("fdNowDate", new Date());
		hqlInfo.setParameter("fdStartDate", LbpmUtil.getDateForTime(dateFlag));
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		String count = getServiceImp(request).getCount(hqlInfo);
		return count;
	}
	//我起草汇总(本周，本月)
	private String draftReview(String dateFlag,HttpServletRequest request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		hqlInfo.setModelName(KmReviewMain.class.getName());
		String where = " kmReviewMain.docCreator.fdId=:docCreator and kmReviewMain.docCreateTime between :fdStartDate and :fdNowDate";
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());
		hqlInfo.setParameter("fdNowDate", new Date());
		hqlInfo.setParameter("fdStartDate", LbpmUtil.getDateForTime(dateFlag));
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		String count = getServiceImp(request).getCount(hqlInfo);
		return count;
	}

	/**
	 *实现功能描述:常用流程封装
	 *@return com.landray.kmss.web.action.ActionForward
	 */
	public ActionForward offenUseList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try{
			//获取到在部件中配置的是“常用流程”的文档显示数
			String portletRowSize = request.getParameter("rowSize");
			JSONObject json = new JSONObject();
			JSONArray offenUseArray = new JSONArray();
			offenUseArray = listOffenUseProcess("com.landray.kmss.km.review.model.KmReviewTemplate", null, request);
			//取前10条数据展示
			JSONArray newjsonArray = new JSONArray();
			/*添加新的逻辑走向，如果是“常用流程”的部件配置的文档条数，
			 *那么就按照配置的文档条数来显示
			 *否则就按照之前的逻辑来显示
			 */
			if (portletRowSize != null && (offenUseArray != null && offenUseArray.size() > 0)) {
				int size = Integer.parseInt(portletRowSize);
				//如果用户设置的显示数大于查询到的所有数据量，那就最多就只能显示所有数据
				if (size > offenUseArray.size()) {
					size = offenUseArray.size();
				}
				for (int i = 0; i < size; i++) {
					newjsonArray.add(offenUseArray.get(i));
				}
				json.put("offenUseProcess", newjsonArray);
			} else {
				if (offenUseArray != null && offenUseArray.size() > 10) {
					for (int i = 0; i <= 9; i++) {
						newjsonArray.add(offenUseArray.get(i));
					}
					json.put("offenUseProcess", newjsonArray);
				} else {
					json.put("offenUseProcess", offenUseArray);
				}
			}
			request.setAttribute("offenUseList", json);
		}catch (Exception e){
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("offenUseList", mapping, form, request, response);
		}
	}

	/**
	 *实现功能描述:待我审的流程含摘要信息
	 *@return com.landray.kmss.web.action.ActionForward
	 */
	public ActionForward reviewProcessList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String rowsize = request.getParameter("rowsize");
		try{
			JSONObject json = new JSONObject();
			String orderby = "docCreateTime";
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
			LbpmUtil.buildLimitBlockForMyApproval("kmReviewMain",hqlInfo);
			hqlInfo.setPageNo(1);
			if("".equals(rowsize) || null==rowsize){
				hqlInfo.setRowSize(6);
			}else{
				hqlInfo.setRowSize(Integer.parseInt(rowsize));
			}
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			List processData =  getServiceImp(request).findPage(hqlInfo).getList();
			JSONObject datas = new JSONObject();
			JSONArray array = lbpmSummaryApprovalService.getProcessReviewInfo(processData, datas,new RequestContext(request));
			//循环遍历获取表单摘要，然后做集合分割，
			// 重新封装摘要数据满足前端table 一行三列的结构
			resizeKmReviewSummaryInfo(array);
			//取前6条数据
			json.put("processReviewInfo", array);
			request.setAttribute("reviewProcessList", json);
		}catch (Exception e){
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("reviewProcessList", mapping, form, request, response);
		}
	}

	/*
	 * List集合分割
	 */
	private static List<List<Map<String,Object>>> groupList(List<Map<String,Object>> list) {
		List<List<Map<String,Object>>> listGroup = new ArrayList<List<Map<String,Object>>>();
		int listSize = list.size();
		//子集合的长度
		int toIndex = 3;
		for (int i = 0; i < list.size(); i += 3) {
			if (i + 3 > listSize) {
				toIndex = listSize - i;
			}
			List<Map<String,Object>> newList = list.subList(i, i + toIndex);
			listGroup.add(newList);
		}
		return listGroup;
	}
	//摘要信息重新整合
	private void resizeKmReviewSummaryInfo(JSONArray array){
		for(int i=0;i<array.size();i++){
			JSONObject obj = (JSONObject) array.get(i);
			if(null != obj.getJSONObject("summary")){
				JSONObject arr = obj.getJSONObject("summary");
				List<Map<String,Object>> ob = null;
				if(arr.containsKey("properties")){
					ob = (List<Map<String,Object>>) arr.get("properties");
				}
				if(null == ob){
					obj.put("summaryInfo","");
					continue;
				}
				List<List<Map<String,Object>>> lists = groupList(ob);
				obj.put("summaryInfo",lists);
			}
		}
	}

	/**
	 *实现功能描述:我起草的流程含摘要信息
	 *@return json
	 */
	public ActionForward draftProcessList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		String orderby = "docCreateTime";
		String rowsize = request.getParameter("rowsize");
		try{
			HQLFragment hqlFragment = new HQLFragment();
			HQLInfo hql = new HQLInfo();
			hql.setOrderBy(orderby);
			hql.setOrderBy(getFindPageOrderBy(request, hql.getOrderBy()));
			String whereStr = hql.getWhereBlock();
			StringBuilder buff = new StringBuilder(StringUtil.isNull(whereStr) ? "1=1 " : whereStr);
			buff.append(
					" and kmReviewMain.docCreator.fdId=:docCreator ");
			hqlFragment.setParameter(new HQLParameter("docCreator", UserUtil.getUser().getFdId()));
			hqlFragment.setWhereBlock(buff.toString());
			LbpmUtil.getHqlByFragments(hqlFragment, hql);
			hql.setPageNo(1);
			if("".equals(rowsize) || null == rowsize){
				hql.setRowSize(6);
			}else{
				hql.setRowSize(Integer.parseInt(rowsize));
			}
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			List processData = getServiceImp(request).findPage(hql).getList();
			JSONObject datas = new JSONObject();
			JSONArray array = lbpmSummaryApprovalService.getProcessReviewInfo(processData, datas,new RequestContext(request));
			//循环遍历获取表单摘要，然后做集合分割，
			// 重新封装摘要数据满足前端table 一行三列的结构
			resizeKmReviewSummaryInfo(array);
			json.put("draftInfo", array);
			request.setAttribute("draftProcessList", json);
		}catch (Exception e){
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("draftProcessList", mapping, form, request, response);
		}
	}

	/**
	 *实现功能描述:我已审核的含摘要信息
	 *@return json
	 */
	public ActionForward reviewedProcessList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											 HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		String rowsize = request.getParameter("rowsize");
		try{
			String orderby = "docCreateTime";
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
			hqlInfo.setModelName(KmReviewMain.class.getName());
			String where = "kmReviewMain.fdId in (select t.fdProcess.fdId from com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmHistoryWorkitem as t " +
					"where t.fdActivityType <> 'draftWorkitem' and t.fdHandler.fdId = '"+UserUtil.getUser().getFdId()+"')";
			hqlInfo.setWhereBlock(where);
			hqlInfo.setPageNo(1);
			if("".equals(rowsize) || null == rowsize){
				hqlInfo.setRowSize(6);
			}else{
				hqlInfo.setRowSize(Integer.parseInt(rowsize));
			}
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			List processData = getServiceImp(request).findPage(hqlInfo).getList();
			JSONObject datas = new JSONObject();
			JSONArray array = lbpmSummaryApprovalService.getProcessReviewInfo(processData, datas,new RequestContext(request));
			//循环遍历获取表单摘要，然后做集合分割，
			// 重新封装摘要数据满足前端table 一行三列的结构
			resizeKmReviewSummaryInfo(array);
			json.put("reviewedInfo", array);
			request.setAttribute("reviewedProcessList", json);
		}catch (Exception e){
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("reviewedProcessList", mapping, form, request, response);
		}
	}

	/**
	 *实现功能描述:我跟踪的含摘要信息
	 *@return com.landray.kmss.web.action.ActionForward
	 */
	public ActionForward followProcessList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		String rowsize = request.getParameter("rowsize");
		try{
			HQLFragment hqlFragment = new HQLFragment();
			HQLInfo hql = new HQLInfo();
			StringBuffer buf = new StringBuffer();
			if (StringUtil.isNotNull(hql.getJoinBlock())) {
				buf.append(hql.getJoinBlock());
			}
			buf.append(", com.landray.kmss.sys.lbpmservice.support.model.LbpmFollow lbpmFollow ");
			hql.setJoinBlock(buf.toString());
			String whereStr = hql.getWhereBlock();
			StringBuilder buff = new StringBuilder(StringUtil.isNull(whereStr) ? "1=1 " : whereStr);
			buff.append(
					" and lbpmFollow.fdProcessId = kmReviewMain.fdId and lbpmFollow.fdWatcher.fdId=:fdWatcher ");
			hqlFragment.setParameter(new HQLParameter("fdWatcher", UserUtil.getUser().getFdId()));
			hqlFragment.setWhereBlock(buff.toString());
			LbpmUtil.getHqlByFragments(hqlFragment, hql);
			hql.setPageNo(1);
			if("".equals(rowsize) || null == rowsize){
				hql.setRowSize(6);
			}else{
				hql.setRowSize(Integer.parseInt(rowsize));
			}
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			List processData = getServiceImp(request).findPage(hql).getList();
			JSONObject datas = new JSONObject();
			JSONArray array = lbpmSummaryApprovalService.getProcessReviewInfo(processData, datas,new RequestContext(request));
			//循环遍历获取表单摘要，然后做集合分割，
			// 重新封装摘要数据满足前端table 一行三列的结构
			resizeKmReviewSummaryInfo(array);
			json.put("followInfo", array);
			request.setAttribute("followProcessList", json);
		}catch (Exception e){
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("followProcessList", mapping, form, request, response);
		}
	}

	/**
	 *实现功能描述:报表统计部件我审批的数据
	 *@return com.landray.kmss.web.action.ActionForward
	 */
	public ActionForward reviewedProcessTable(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hql = new HQLInfo();
			String orderby = "docCreateTime";
			String reviewed = "reviewed";
			String thisYear = "thisYear";
			hql.setOrderBy(orderby);
			hql.setOrderBy(getFindPageOrderBy(request, hql.getOrderBy()));
			LbpmUtil.buildLimitBlockForApproved("kmReviewMain", hql,
					UserUtil.getUser().getFdId(), reviewed, thisYear);
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			List<KmReviewMain> list = getServiceImp(request).findList(hql);
			List <Integer> arrayList  = getChartsData(list);
			request.setAttribute("reviewedProcessTable", arrayList);
		}catch (Exception e){
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("reviewedProcessTable", mapping, form, request, response);
		}
	}

	/**
	 *实现功能描述:按月封装待我已审的本年的数据
	 *@return com.landray.kmss.web.action.ActionForward
	 */
	public ActionForward reviewedProcessTab(ActionMapping mapping,
											  ActionForm form, HttpServletRequest request,
											  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		List <Integer> arrayList = new ArrayList<>();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			String orderby = "docCreateTime";
			String reviewed = "reviewed";
			String thisYear = "thisYear";
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
			LbpmUtil.buildLimitBlockForApproved("kmReviewMain", hqlInfo,
					UserUtil.getUser().getFdId(), reviewed, thisYear);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			List<KmReviewMain> list = getServiceImp(request).findList(hqlInfo);
			arrayList  = getChartsData(list);
		}catch (Exception e){
			messages.addError(e);
		}
		response.getWriter().write(arrayList.toString());
		return null;
	}

	/**
	 *实现功能描述:按要求封装图表数据
	 *@param [list]
	 *@return java.util.List<java.lang.Integer>
	 */
	private List<Integer> getChartsData(List<KmReviewMain> list){
		List <Integer> arrayList = new ArrayList<Integer>();
		int num1 = 0;
		int num2 = 0;
		int num3 = 0;
		int num4 = 0;
		int num5 = 0;
		int num6 = 0;
		int num7 = 0;
		int num8 = 0;
		int num9 = 0;
		int num10 = 0;
		int num11 = 0;
		int num12 = 0;
		if (list.size() > 0) {
			for (KmReviewMain reviewMain : list) {
				if (reviewMain.getDocCreateTime().getMonth() == 0) {
					num1++;
				}
				if (reviewMain.getDocCreateTime().getMonth() == 1) {
					num2++;
				}
				if (reviewMain.getDocCreateTime().getMonth() == 2) {
					num3++;
				}
				if (reviewMain.getDocCreateTime().getMonth() == 3) {
					num4++;
				}
				if (reviewMain.getDocCreateTime().getMonth() == 4) {
					num5++;
				}
				if (reviewMain.getDocCreateTime().getMonth() == 5) {
					num6++;
				}
				if (reviewMain.getDocCreateTime().getMonth() == 6) {
					num7++;
				}
				if (reviewMain.getDocCreateTime().getMonth() == 7) {
					num8++;
				}
				if (reviewMain.getDocCreateTime().getMonth() == 8) {
					num9++;
				}
				if (reviewMain.getDocCreateTime().getMonth() == 9) {
					num10++;
				}
				if (reviewMain.getDocCreateTime().getMonth() == 10) {
					num11++;
				}
				if (reviewMain.getDocCreateTime().getMonth() == 11) {
					num12++;
				}
			}
		}
		if(new Date().getMonth() == 0){
			arrayList.add(num1);
		}
		if(new Date().getMonth() == 1){
			arrayList.add(num1);
			arrayList.add(num2);
		}
		if(new Date().getMonth() == 2){
			arrayList.add(num1);
			arrayList.add(num2);
			arrayList.add(num3);
		}
		if(new Date().getMonth() == 3){
			arrayList.add(num1);
			arrayList.add(num2);
			arrayList.add(num3);
			arrayList.add(num4);
		}
		if(new Date().getMonth() == 4){
			arrayList.add(num1);
			arrayList.add(num2);
			arrayList.add(num3);
			arrayList.add(num4);
			arrayList.add(num5);
		}
		if(new Date().getMonth() == 5){
			arrayList.add(num1);
			arrayList.add(num2);
			arrayList.add(num3);
			arrayList.add(num4);
			arrayList.add(num5);
			arrayList.add(num6);
		}
		if(new Date().getMonth() == 6){
			arrayList.add(num1);
			arrayList.add(num2);
			arrayList.add(num3);
			arrayList.add(num4);
			arrayList.add(num5);
			arrayList.add(num6);
			arrayList.add(num7);
		}
		if(new Date().getMonth() == 7){
			arrayList.add(num1);
			arrayList.add(num2);
			arrayList.add(num3);
			arrayList.add(num4);
			arrayList.add(num5);
			arrayList.add(num6);
			arrayList.add(num7);
			arrayList.add(num8);
		}
		if(new Date().getMonth() == 8){
			arrayList.add(num1);
			arrayList.add(num2);
			arrayList.add(num3);
			arrayList.add(num4);
			arrayList.add(num5);
			arrayList.add(num6);
			arrayList.add(num7);
			arrayList.add(num8);
			arrayList.add(num9);
		}
		if(new Date().getMonth() == 9){
			arrayList.add(num1);
			arrayList.add(num2);
			arrayList.add(num3);
			arrayList.add(num4);
			arrayList.add(num5);
			arrayList.add(num6);
			arrayList.add(num7);
			arrayList.add(num8);
			arrayList.add(num9);
			arrayList.add(num10);
		}
		if(new Date().getMonth() == 10){
			arrayList.add(num1);
			arrayList.add(num2);
			arrayList.add(num3);
			arrayList.add(num4);
			arrayList.add(num5);
			arrayList.add(num6);
			arrayList.add(num7);
			arrayList.add(num8);
			arrayList.add(num9);
			arrayList.add(num10);
			arrayList.add(num11);
		}
		if(new Date().getMonth() == 11){
			arrayList.add(num1);
			arrayList.add(num2);
			arrayList.add(num3);
			arrayList.add(num4);
			arrayList.add(num5);
			arrayList.add(num6);
			arrayList.add(num7);
			arrayList.add(num8);
			arrayList.add(num9);
			arrayList.add(num10);
			arrayList.add(num11);
			arrayList.add(num12);
		}
		return arrayList;
	}

	//报表部件我起草的数据统计方法
	public ActionForward draftProcessTable (ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HQLFragment hqlFragment = new HQLFragment();
			StringBuilder buff = null;
			HQLInfo hql = new HQLInfo();
			String orderby = "docCreateTime";
			hql.setOrderBy(orderby);
			hql.setOrderBy(getFindPageOrderBy(request, hql.getOrderBy()));

			String whereStr = hql.getWhereBlock();
			buff = new StringBuilder(StringUtil.isNull(whereStr) ? "1=1 " : whereStr);

			buff.append(
					" and kmReviewMain.docCreator.fdId=:docCreator ");
			buff.append(" and kmReviewMain.docCreateTime between :fdStartDate and :fdNowDate ");
			hqlFragment.setParameter(new HQLParameter("docCreator", UserUtil.getUser().getFdId()));
			hqlFragment.setParameter(new HQLParameter("fdNowDate", new Date()));
			hqlFragment.setParameter(new HQLParameter("fdStartDate", LbpmUtil.getDateForTime("year")));
			hqlFragment.setWhereBlock(buff.toString());
			LbpmUtil.getHqlByFragments(hqlFragment, hql);
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			List<KmReviewMain> list = getServiceImp(request).findList(hql);
			List <Integer> arrayList  = getChartsData(list);
			request.setAttribute("draftProcessTable",arrayList);
		}catch (Exception e){
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("draftProcessTable", mapping, form, request, response);
		}
	}

	//按月封装我起草的本年数据
	public ActionForward draftProcessTab (ActionMapping mapping,
											 ActionForm form, HttpServletRequest request,
											 HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		List <Integer> arrayList = new ArrayList<>();
		try {
			HQLFragment hqlFragment = new HQLFragment();
			StringBuilder buff = null;
			HQLInfo hql = new HQLInfo();
			String orderby = "docCreateTime";
			hql.setOrderBy(orderby);
			hql.setOrderBy(getFindPageOrderBy(request, hql.getOrderBy()));

			String whereStr = hql.getWhereBlock();
			buff = new StringBuilder(StringUtil.isNull(whereStr) ? "1=1 " : whereStr);

			buff.append(
					" and kmReviewMain.docCreator.fdId=:docCreator ");
			buff.append(" and kmReviewMain.docCreateTime between :fdStartDate and :fdNowDate ");
			hqlFragment.setParameter(new HQLParameter("docCreator", UserUtil.getUser().getFdId()));
			hqlFragment.setParameter(new HQLParameter("fdNowDate", new Date()));
			hqlFragment.setParameter(new HQLParameter("fdStartDate", LbpmUtil.getDateForTime("year")));
			hqlFragment.setWhereBlock(buff.toString());
			LbpmUtil.getHqlByFragments(hqlFragment, hql);
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			List<KmReviewMain> list = getServiceImp(request).findList(hql);
			arrayList  = getChartsData(list);
		}catch (Exception e){
			messages.addError(e);
		}
		response.getWriter().write(arrayList.toString());
		return null;
	}
	/**
	 *实现功能描述:常用流程查询
	 *@return net.sf.json.JSONArray
	 */
	public JSONArray listOffenUseProcess (String modelName, String key, HttpServletRequest request){
		KmssMessages messages = new KmssMessages();
		JSONArray cates = new JSONArray();
		try {
			List<String> templateModelNames = new ArrayList<String>();
			templateModelNames.add(
					"com.landray.kmss.km.asset.model.KmAssetApplyTemplate");
			templateModelNames
					.add("com.landray.kmss.hr.recruit.model.HrRecruitTemplate");
			templateModelNames
					.add("com.landray.kmss.hr.ratify.model.HrRatifyTemplate");
			SysPersonFavoriteCategory category = getSysPersonFavoriteCategoryServiceImp(request).getFavoriteCategory(modelName);
			if (category != null) {
				String ids = category.getFdCategoryIds();
				String names = category.getFdCategoryNames();
				if (StringUtil.isNotNull(ids)) {
					String[] idArray = ids.split(";");
					String[] nameArray = names.split(";");
					if (idArray.length > 0) {
						Map<String, String> nameMap = new HashMap<>();
						for (int i = 0; i < idArray.length; i++) {
							nameMap.put(idArray[i], nameArray[i]);
						}
						SysDictModel dict = SysDataDict.getInstance()
								.getModel(modelName);
						IBaseService bean = (IBaseService) SpringBeanUtil
								.getBean(dict.getServiceBean());
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setCheckParam(
								SysAuthConstant.CheckType.AllCheck,
								SysAuthConstant.AllCheck.DEFAULT);
						hqlInfo.setWhereBlock(
								ModelUtil.getModelTableName(modelName)
										+ ".fdId in(:ids)");
						hqlInfo.setParameter("ids", java.util.Arrays.asList(idArray));
						List<IBaseModel> list = bean.findList(hqlInfo);
						list = FavoriteCategoryUtil.sortedModelsByIds(idArray,list);
						for (IBaseModel templateModel : list) {
							StringBuffer buffer = new StringBuffer("/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId=");
							buffer.append(templateModel.getFdId());
							buffer.append("&.fdTemplate=");
							buffer.append(templateModel.getFdId());
							buffer.append("&i.docTemplate=");
							buffer.append(templateModel.getFdId());
							JSONObject cate = new JSONObject();
							cate.put("value", templateModel.getFdId());
							cate.put("text", nameMap.get(templateModel.getFdId()));
							cate.put("url",buffer.toString());

							if(templateModel instanceof KmReviewTemplate){
								cate.put("icon", ((KmReviewTemplate) templateModel).getFdIcon());
							}
							if (templateModelNames.contains(modelName)) {
								String table = dict.getTable();
								String sql = "select count(fd_id) from " + table
										+ " where fd_id=? and fd_tempkey=?";
								NativeQuery query = getServiceImp(request).getBaseDao()
										.getHibernateSession().createNativeQuery(sql);
								query.setString(0, templateModel.getFdId());
								query.setString(1, key);
								List<Number> value = query.list();
								if (value.isEmpty() || value.get(0).intValue() < 1) {
                                    continue;
                                }
							}

							// 禁用的模板不显示
							if (PropertyUtils.isReadable(templateModel,
									"fdIsAvailable")) {
								Object fdIsAvailable = PropertyUtils
										.getProperty(templateModel,
												"fdIsAvailable");
								if (fdIsAvailable != null
										&& "false"
										.equals(fdIsAvailable.toString())) {
									continue;
								}
							}

							if(templateModel != null){
								if( templateModel instanceof IBaseTemplateModel){
									IBaseTemplateModel baseTemplateModel = (IBaseTemplateModel)templateModel;
									SysCategoryMain categoryMain = baseTemplateModel.getDocCategory();
									if(categoryMain !=null){
										cate.put("cateName", categoryMain.getFdName());
									}

								} else if (templateModel instanceof ISysSimpleCategoryModel) {
									ISysSimpleCategoryModel baseTemplateModel = (ISysSimpleCategoryModel) templateModel;
									if (baseTemplateModel != null) {
										IBaseModel fdParent = baseTemplateModel.getFdParent();
										if (fdParent != null) {

											Object templateModelNameObj = null;
											try {
												templateModelNameObj = PropertyUtils.getProperty(fdParent, "fdName");
											} catch (Exception e) {
												templateModelNameObj = PropertyUtils.getProperty(fdParent, "docSubject");
											}

											if (templateModelNameObj == null) {
												continue;
											} else {
												cate.put("cateName", templateModelNameObj.toString());
											}
										}
									}
								}
								// String addUrl = getAddURL(id, dict);
								// cate.put("addUrl", addUrl);
							}
							cates.add(cate);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		return cates;
	}
	/**
	 *实现功能描述:流程首页报表部件
	 *@param [mapping, form, request, response]
	 *@return com.landray.kmss.web.action.ActionForward
	 */
	public ActionForward listProcessHomePageCharts(ActionMapping mapping,
												   ActionForm form,
												   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String summary = request.getParameter("summary");
			if ("listCreateCharts".equals(summary)) {
				request.setAttribute("summary", "draftProcessTable");
			} else {
				request.setAttribute("summary", "reviewedProcessTable");
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		if (messages.hasError()) {
			request.setAttribute(
					"lui-source",
					new JSONObject().element("msg",
							ResourceUtil.getString("return.optFailure")));
			return getActionForward("lui-failure", mapping, form, request,
					response);
		} else {
			return getActionForward("portletProcessSummaryCharts", mapping, form,
					request,
					response);
		}

	}

	/**
	 *实现功能描述:流程列表（含摘要）门户部件Action
	 *@param [mapping, form, request, response]
	 *@return com.landray.kmss.web.action.ActionForward
	 */
	public ActionForward listProcessSummaryPortlet(ActionMapping mapping,
												   ActionForm form,
												   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String rowsize = request.getParameter("rowsize");
		String processType = request.getParameter("processType");
		try {
			if (null != rowsize) {
				if ("reviewProcessInfo".equals(processType)) {
					request.setAttribute("processType", "reviewProcessList");
					request.setAttribute("rowsize",rowsize);
				} else if ("reviewedProcessInfo".equals(processType)) {
					request.setAttribute("processType", "reviewedProcessList");
					request.setAttribute("rowsize",rowsize);
				} else if ("draftProcessInfo".equals(processType)) {
					request.setAttribute("processType", "draftProcessList");
					request.setAttribute("rowsize",rowsize);
				} else {
					request.setAttribute("processType", "followProcessList");
					request.setAttribute("rowsize",rowsize);
				}
			}
		}catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		if (messages.hasError()) {
			request.setAttribute(
					"lui-source",
					new JSONObject().element("msg",
							ResourceUtil.getString("return.optFailure")));
			return getActionForward("lui-failure", mapping, form, request,
					response);
		}else{
			return getActionForward("portletProcessSummary", mapping, form,
					request,
					response);
		}
	}

	/**
	 * 流程首页-常用流程和最近使用流程部件
	 * 跳转到"常用流程/最近使用流“汇总页面
	 * 根据配置分别请求不同路径
	 */
	public ActionForward toOftenOrRecent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										 HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			//流程类型参数-常用流程/最近使用流
			String oftenRecentFlow = request.getParameter("oftenRecentFlow");
			//显示的文档条数
			String rowSize = request.getParameter("rowsize");
			/*如果参数“oftenRecentFlow”和“rowsize”同时为空的情况，则设置一个默认值
			 * 防止在“页面配置”-“部件配置”时点击预览出现空白的情况
			 * */
			if ("".equals(oftenRecentFlow) && "".equals(rowSize)) {
				oftenRecentFlow = "offen";
				rowSize = "6";
			}
			json.put("oftenRecentFlow", oftenRecentFlow);
			json.put("rowSize", rowSize);
			request.setAttribute("commonRecently", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("commonRecently", mapping, form, request, response);
		}
	}

	/*#153388-流程发起页面增加统计数据-开始*/

	/**
	 * 统计km/review流程发起页面的分类数据，统计分类分别为：
	 * 全部（我发起的）、我已审、待我审、驳回（[我的发起]驳回页签）、结束（[我的发起] 结束页签）、废弃（[我的发起] 废弃页签）
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getListAllCountSummaryOfKmReview(ActionMapping mapping,
														  ActionForm form,
														  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		KmssMessages messages = new KmssMessages();
		//定义返回的数据集合
		Map resultMap = new HashMap();
		//定义查询的语句对象
		HQLInfo hqlInfo = new HQLInfo();
		//拼接sql字符串
		StringBuilder whereBlock = new StringBuilder();
		try {
			/****************开始查询****************/
			// 全部（我发起的）
			//设置from语句
			hqlInfo.setGettingCount(true);
			whereBlock.append(" kmReviewMain.docCreator.fdId=:createorId ");
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("createorId",
					UserUtil.getUser().getFdId());
			String draft = getServiceImp(request).getCount(hqlInfo);
			resultMap.put("draft", draft);

			//我已审
			hqlInfo = new HQLInfo();
			//设置from语句
			hqlInfo.setGettingCount(true);
			LbpmUtil.buildLimitBlockForMyApproved("kmReviewMain", hqlInfo,
					UserUtil.getUser().getFdId());
			//设置权限过滤参数
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			String approved = getServiceImp(request).getCount(hqlInfo);
			resultMap.put("approved", approved);

			//待我审
			hqlInfo = new HQLInfo();
			//设置from语句
			hqlInfo.setGettingCount(true);
			List<String> userList = new ArrayList<String>();
			//因为这里参数类型定义的是list,所以需要传递对应的类型
			userList.addAll(UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
			LbpmUtil.buildLimitBlockForMyApproval("kmReviewMain", hqlInfo,
					userList);
			//设置权限过滤参数
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			String approval = getServiceImp(request).getCount(hqlInfo);
			resultMap.put("approval", approval);

			//驳回
			hqlInfo = new HQLInfo();
			//设置from语句
			hqlInfo.setGettingCount(true);
			whereBlock = new StringBuilder();
			whereBlock.append(" kmReviewMain.docCreator.fdId=:createorId and kmReviewMain.docStatus =:docStatus");
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("createorId",
					UserUtil.getUser().getFdId());
			hqlInfo.setParameter("docStatus", "11");
			String reject = getServiceImp(request).getCount(hqlInfo);
			resultMap.put("reject", reject);

			//结束
			hqlInfo = new HQLInfo();
			//设置from语句
			hqlInfo.setGettingCount(true);
			whereBlock = new StringBuilder();
			whereBlock.append(" kmReviewMain.docCreator.fdId=:createorId and kmReviewMain.docStatus =:docStatus");
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("createorId",
					UserUtil.getUser().getFdId());
			hqlInfo.setParameter("docStatus", "30");
			String end = getServiceImp(request).getCount(hqlInfo);
			resultMap.put("end", end);

			//废弃
			hqlInfo = new HQLInfo();
			//设置from语句
			hqlInfo.setGettingCount(true);
			whereBlock = new StringBuilder();
			whereBlock.append(" kmReviewMain.docCreator.fdId=:createorId and kmReviewMain.docStatus =:docStatus");
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("createorId",
					UserUtil.getUser().getFdId());
			hqlInfo.setParameter("docStatus", "00");
			String abandoned = getServiceImp(request).getCount(hqlInfo);
			resultMap.put("abandoned", abandoned);
			/****************结束查询****************/
		} catch (Exception e) {
			messages.addError(e);
		}
		//输出数据
		response.getWriter().print(JSONObject.fromObject(resultMap).toString());
		return null;
	}
	/*#153388-流程发起页面增加统计数据-结束*/
}
