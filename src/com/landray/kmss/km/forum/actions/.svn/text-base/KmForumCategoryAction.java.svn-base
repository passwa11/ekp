package com.landray.kmss.km.forum.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.forum.forms.KmForumCategoryForm;
import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.km.forum.model.KmForumPost;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.km.forum.service.IKmForumCategoryService;
import com.landray.kmss.km.forum.service.IKmForumPostService;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.category.interfaces.ConfigUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessageWriter;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 创建日期 2006-Aug-31
 * 
 * @author 吴兵
 */
public class KmForumCategoryAction extends ExtendAction

{
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmssMessageWriter.class);
	protected IKmForumCategoryService kmForumCategoryService;
	protected IKmForumTopicService kmForumTopicService;
	protected IKmForumPostService kmForumPostService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmForumCategoryService == null) {
			kmForumCategoryService = (IKmForumCategoryService) getBean("kmForumCategoryService");
		}
		return kmForumCategoryService;
	}

	protected IBaseService getForumTopicServiceImp(HttpServletRequest request) {
		if (kmForumTopicService == null) {
			kmForumTopicService = (IKmForumTopicService) getBean("kmForumTopicService");
		}
		return kmForumTopicService;
	}

	protected IKmForumPostService getKmForumPostServiceImp(
			HttpServletRequest request) {
		if (kmForumPostService == null) {
			kmForumPostService = (IKmForumPostService) getBean("kmForumPostService");
		}
		return kmForumPostService;
	}

	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
										String curOrderBy) throws Exception {
		if (StringUtil.isNull(curOrderBy)) {
			return "kmForumCategory.fdOrder";
		}
		return curOrderBy;
	}

	/**
	 * 论坛版主管理页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward manage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("manage");
		}
	}

	/**
	 * 保存管理内容且转向论坛版主管理页面。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回manage页面
	 * @throws Exception
	 */
	public ActionForward manageUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return mapping.findForward("manage");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("success");
		}
	}

	protected ActionForm createNewForumForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmForumCategoryForm kmForumCategoryForm = (KmForumCategoryForm) form;
		kmForumCategoryForm.reset(mapping, request);
		kmForumCategoryForm.setFdParentId("");

		return kmForumCategoryForm;
	}

	public ActionForward addDirectory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForumForm(mapping, form, request,
					response);
			if (newForm != form) {
				request.setAttribute(getFormName(newForm, request), newForm);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("editDirectory", mapping, form, request,
					response);
		}
	}

	public ActionForward viewDirectory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("viewDirectory", mapping, form, request,
					response);
		}
	}

	public ActionForward editDirectory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("editDirectory", mapping, form, request,
					response);
		}
	}

	public ActionForward updateDirectory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			KmForumCategoryForm kmForumCategoryForm = (KmForumCategoryForm) form;
			String fdId = kmForumCategoryForm.getFdId();
			if (StringUtil.isNotNull(fdId)) {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton("button.back",
								"/km/forum/km_forum_cate/kmForumCategory.do?method=viewDirectory&fdId="
										+ fdId,
								false)
						.save(request);
			}
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	public ActionForward categoryDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String categoryId = request.getParameter("categoryId");
			boolean fetchBaseInfo = false;
			if(StringUtil.isNotNull(request.getParameter("base"))){
				fetchBaseInfo = true;
			}
			KmForumCategory kmForumCate = (KmForumCategory) getServiceImp(
					request).findByPrimaryKey(categoryId);
			UserOperHelper.logFind(kmForumCate);// 添加日志信息
			if(!fetchBaseInfo){
				loadForumCategoryTopicInfo(request, categoryId);
			}
			JSONObject json = new JSONObject();
			json.accumulate("fdId", kmForumCate.getFdId());
			json.accumulate("name", kmForumCate.getFdName());
			json.accumulate("description", kmForumCate.getFdDescription());
			json.accumulate("subCateCount", getSubCount(request,kmForumCate.getFdId()));
			if (kmForumCate.getFdParent() != null) {
				KmForumCategory parent = (KmForumCategory) kmForumCate
						.getFdParent();
				json.accumulate("parentName", parent.getFdName());
				json.accumulate("parentId", parent.getFdId());
			}
			if(!fetchBaseInfo){
				json.accumulate("topicCount", request.getAttribute("topicCount"));
				json.accumulate("postCount", request.getAttribute("replyCount"));
			}
			request.setAttribute("lui-source", json.toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	private long getSubCount(HttpServletRequest request, String categoryId)throws Exception{
		HQLInfo info = new HQLInfo();
		String whereBlock = "hbmParent.fdId = :fdId";
		info.setParameter("fdId", categoryId);
		if (ConfigUtil.auth(KmForumCategory.class.getName())) {//全局分类权限配置
			info.setJoinBlock(" left join kmForumCategory.authAllReaders authAllReaders");
			KMSSUser user = UserUtil.getKMSSUser();
			List authOrgIds = user.getUserAuthInfo().getAuthOrgIds();
			whereBlock += " and ((kmForumCategory.authReaderFlag =:authReaderFlag) or (authAllReaders.fdId in(:orgs)))";
			info.setParameter("authReaderFlag", true);
			info.setParameter("orgs", authOrgIds);
		}
		info.setJoinBlock(
				StringUtil.linkString(info.getJoinBlock(), " ", "left join kmForumCategory.hbmParent hbmParent"));
		info.setWhereBlock(whereBlock);
		info.setSelectBlock("count(*)");
		List resList = getServiceImp(request).findList(info);
		if(!resList.isEmpty()){
			Object res = resList.get(0);
			if(res!=null){
				return (Long)res;
			}
		}
		
		return 0L;
	}
	public ActionForward subCate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
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
			String categoryId = request.getParameter("categoryId");
			HQLInfo info = new HQLInfo();
			String whereBlock = "hbmParent.fdId =:fdId";
			info.setParameter("fdId", categoryId);
			if (ConfigUtil.auth(KmForumCategory.class.getName())) {//全局分类权限配置
				info.setJoinBlock(" left join kmForumCategory.authAllReaders authAllReaders");
				KMSSUser user = UserUtil.getKMSSUser();
				List authOrgIds = user.getUserAuthInfo().getAuthOrgIds();
				whereBlock += " and ((kmForumCategory.authReaderFlag =:authReaderFlag) or (authAllReaders.fdId in(:orgs)))";
				info.setParameter("authReaderFlag", true);
				info.setParameter("orgs", authOrgIds);
			}
			info.setJoinBlock(
					StringUtil.linkString(info.getJoinBlock(), " ", "left join kmForumCategory.hbmParent hbmParent"));
			info.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(info);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
			List<KmForumCategory> list = page.getList();
			JSONObject countJson = new JSONObject();
			for (KmForumCategory kmForumCategory : list) {
				loadForumCategoryTopicInfo(request, kmForumCategory.getFdId());
				countJson.element("topicCount" + kmForumCategory.getFdId(),
						request.getAttribute("topicCount"));
				countJson.element("replyCount" + kmForumCategory.getFdId(),
						request.getAttribute("replyCount"));
			}
			request.setAttribute("countJson", countJson);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("subCate");
		}
	}
	/**
	 * 论坛首面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，论坛首面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward main(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String categoryId = request.getParameter("categoryId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy("kmForumCategory.fdOrder asc");

			String whereBlock;
			if (StringUtil.isNotNull(categoryId)) {
				whereBlock = "kmForumCategory.fdId = :categoryId";
				hqlInfo.setParameter("categoryId", categoryId);
			} else {
				hqlInfo.setJoinBlock(" left join kmForumCategory.hbmParent hbmParent");
				whereBlock = "hbmParent is null";
			}
			hqlInfo.setWhereBlock(whereBlock);

			List kmForumCategorys = getServiceImp(request).findList(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(kmForumCategorys,
					getServiceImp(request).getModelName());
			String type = "";
			String isShowAll = "";
			if (StringUtil.isNotNull(request.getParameter("isShowAll"))) {
				isShowAll = request.getParameter("isShowAll");
			}
			if (StringUtil.isNotNull(request.getParameter("type"))) {
				type = request.getParameter("type");
			}
			JSONArray jsonArr = new JSONArray();
			for (int i = 0; i < kmForumCategorys.size(); i++) {
				KmForumCategory kmForumCategory = (KmForumCategory) kmForumCategorys.get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("fdId", kmForumCategory.getFdId());
				jsonObj.put("fdName", kmForumCategory.getFdName());
				jsonObj.put("isShowAll", isShowAll);
				jsonObj.put("fdDescription", kmForumCategory.getFdDescription());
				// 获取今日发帖数
				getTopicToday(request);

				if (StringUtil.isNotNull(type) && "criteria".equals(type)) {
					loadForumCategoryTopicInfo(request, categoryId);
					if (StringUtil.isNull(categoryId)) {
						jsonObj.put("rowSize", kmForumCategorys.size());
					} else {
						jsonObj.put("rowSize", ((KmForumCategory) kmForumCategorys.get(0)).getFdChildren().size());
					}
					jsonObj.put("topicCountToday", request.getAttribute("topicCountToday"));
					jsonObj.put("topicCount", request.getAttribute("topicCount"));
					jsonObj.put("replyCount", request.getAttribute("replyCount"));
				} else {
					jsonObj.put("rowSize", Integer.MAX_VALUE);
				}
				jsonObj.put("type", type);
				JSONArray authAllEditors = new JSONArray();
				List authAllEditorList = kmForumCategory.getAuthAllEditors();
				for (int j = 0; j < authAllEditorList.size(); j++) {
					SysOrgElement editor = (SysOrgElement)authAllEditorList.get(j);
					JSONObject obj1 = new JSONObject();
					obj1.put("personId", editor.getFdId());
					obj1.put("personName", editor.getFdName());
					authAllEditors.add(obj1);
				}
				jsonObj.put("authAllEditors", authAllEditors);
				
				JSONArray children = new JSONArray();
				List<KmForumCategory> childrenCate = kmForumCategory.getFdChildren();
				for (KmForumCategory k : childrenCate) {
					// 开启生态组织，对外部人员进行版块过滤
					if (SysOrgEcoUtil.IS_ENABLED_ECO && !UserUtil.getKMSSUser().isAdmin()
							&& SysOrgEcoUtil.isExternal()) {
						List<SysOrgElement> readers = k.getAuthReaders();
						ArrayUtil.concatTwoList(k.getAuthAllReaders(), readers);
						readers.addAll(k.getAuthAllReaders());
						List<String> ids = new ArrayList<String>();
						for (SysOrgElement elem : readers) {
							ids.add(elem.getFdId());
						}
						if (!UserUtil.checkUserIds(ids)) {
							continue;
						}
					}
					JSONObject obj2 = new JSONObject();
					obj2.put("fdId", k.getFdId());
					obj2.put("fdName", k.getFdName());

					Map<String, String> sumCateTopicInfoMap = (Map<String, String>) request
							.getAttribute("sumCateTopicInfoMap");
					if (sumCateTopicInfoMap != null) {
						obj2.put("todayCount", sumCateTopicInfoMap.get(k) == null ? 0 : sumCateTopicInfoMap.get(k));
					}
					obj2.put("fdDescription", k.getFdDescription());
					JSONArray authAllEditors_child = new JSONArray();
					List authAllEditorList_child = k.getAuthAllEditors();
					for (int m = 0; m < authAllEditorList_child.size(); m++) {
						SysOrgElement editor_child = (SysOrgElement) authAllEditorList_child.get(m);
						JSONObject obj3 = new JSONObject();
						obj3.put("personId", editor_child.getFdId());
						obj3.put("personName", editor_child.getFdName());
						authAllEditors_child.add(obj3);
					}
					obj2.put("authAllEditors", authAllEditors_child);
					Integer[] countArr = ((IKmForumTopicService) getForumTopicServiceImp(request))
							.getForumTopicAndReplyCount(k.getFdId());
					String local = "";
					if (request.getLocale() != null) {
						local = request.getLocale().toString().toLowerCase();
					}
					int fdTopicCount = countArr[0];
					String fdTopicCountVal = "";
					// if (fdTopicCount > 10000) {
					// if (StringUtil.isNotNull(local)) {
					// if (local.equals("zh_cn") || local.equals("zh_hk")) {
					// fdTopicCountVal = String.valueOf(fdTopicCount / 10000) +
					// "万";
					// } else if (local.equals("en_us")) {
					// fdTopicCountVal = String.valueOf(fdTopicCount / 1000) +
					// "THS";
					// }else {
					// fdTopicCountVal = "999+";
					// }
					// } else {
					// fdTopicCountVal = "999+";
					// }
					// } else {
						fdTopicCountVal = String.valueOf(fdTopicCount);
					// }


					int fdPostCount = countArr[1];
					String fdPostCountVal = "";
					// if (fdPostCount > 10000) {
					// if (StringUtil.isNotNull(local)) {
					// if (local.equals("zh_cn") || local.equals("zh_hk")) {
					// fdPostCountVal = String.valueOf(fdPostCount / 10000) +
					// "万";
					// } else if (local.equals("en_us")) {
					// fdPostCountVal = String.valueOf(fdPostCount / 1000) +
					// "THS";
					// }else {
					// fdPostCountVal = "999+";
					// }
					// } else {
					// fdPostCountVal = "999+";
					// }
					// } else
					if (fdPostCount < 0) {
						fdPostCountVal = "0";
						fdPostCount = 0;
					} else {
						fdPostCountVal = String.valueOf(fdPostCount);
					}
					obj2.put("fdTopicCountVal", fdTopicCountVal);
					obj2.put("fdPostCountVal", fdPostCountVal);
					Object filterDraft = request.getParameter("filterDraft");
					KmForumTopic kmForumTopic = ((IKmForumTopicService) getForumTopicServiceImp(
							request)).getRecentTopicByCategoryId(k.getFdId(), "true".equals(filterDraft));
					if (kmForumTopic != null) {
						if (kmForumTopic.getForumPosts().size() > 0) {
							KmForumPost kmForumPost = (KmForumPost) kmForumTopic.getForumPosts().get(0);
							obj2.put("fdForumId", kmForumPost.getKmForumTopic().getKmForumCategory().getFdId());
							obj2.put("fdTopicId", kmForumPost.getKmForumTopic().getFdId());
							obj2.put("docSubject", kmForumPost.getDocSubject());
							if (kmForumPost.getFdPoster() != null) {
								obj2.put("fdPosterId", kmForumPost.getFdPoster().getFdId());
								obj2.put("fdPosterName", kmForumPost.getFdPoster().getFdName());
								obj2.put("fdPosterHeadurl",
										PersonInfoServiceGetter
												.getPersonHeadimageUrl(
														kmForumPost
																.getFdPoster()
																.getFdId(),
														null));
							}
							Date d = kmForumPost.getDocAlterTime();
							if(d == null){
								d = kmForumPost.getDocCreateTime();
							}
							obj2.put("docAlterTime",
									DateUtil.convertDateToString(d, ResourceUtil.getString("date.format.datetime")));
						}
					}
					children.add(obj2);
				}
				jsonObj.put("children", children);
				jsonArr.add(jsonObj);
			}
			//System.out.println(jsonArr);
			
			request.setAttribute("lui-source", jsonArr);

		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-index", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	/**
	 * 获取主题信息
	 * 
	 * @param request
	 * @param categoryId
	 * @throws Exception
	 */
	private void loadForumCategoryTopicInfo(HttpServletRequest request,
			String categoryId) throws Exception {
		
		Integer [] countArr = ((IKmForumTopicService)getForumTopicServiceImp(request)).getForumTopicAndReplyCount(categoryId);
		int topicCount = countArr[0];
		int replyCount = countArr[1];
		// 今日主题
		Map<String, String> sumCateTopicInfoMap = (Map<String, String>) request.getAttribute("sumCateTopicInfoMap");
				
		if (sumCateTopicInfoMap != null) {
			if (StringUtil.isNull(categoryId)) {
				int count = 0;
				List values = (List) sumCateTopicInfoMap.values();
				for (int i = 0; i < values.size(); i++) {
					count = count + Integer.parseInt(values.get(i).toString());
				}
				request.setAttribute("topicCountToday", count);
			} else {
				request.setAttribute("topicCountToday", sumCateTopicInfoMap
						.get(categoryId) == null ? 0 : sumCateTopicInfoMap
						.get(categoryId));
			}
		}
		// 发帖数
		request.setAttribute("topicCount", topicCount);
		// 回帖数
		request.setAttribute("replyCount", replyCount);
	}

	/**
	 * 获取今日主题数
	 * 
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	private void getTopicToday(HttpServletRequest request) throws Exception {
		String categoryId = request.getParameter("categoryId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName("com.landray.kmss.km.forum.model.KmForumTopic");
		hqlInfo.setSelectBlock("count(*),kmForumCategory.fdId");
	    String whereBlock = " kmForumTopic.docCreateTime <:endTime and kmForumTopic.docCreateTime >:startTime and kmForumTopic.fdStatus!=:fdStatus";
	    if (StringUtil.isNotNull(categoryId)) {
			whereBlock += " and kmForumCategory.fdHierarchyId like :categoryId group by kmForumCategory.fdId";
			hqlInfo.setParameter("categoryId","%" + categoryId + "x%");
		} else {
			whereBlock += " and kmForumCategory.hbmParent !=null group by kmForumCategory.fdId";
		}
		hqlInfo.setJoinBlock(" left join kmForumTopic.kmForumCategory kmForumCategory");
	    hqlInfo.setParameter("fdStatus",SysDocConstant.DOC_STATUS_DRAFT);
	    hqlInfo.setWhereBlock(whereBlock);
	    //获取今日时间段
	    Calendar c1 = new GregorianCalendar();
	    c1.set(Calendar.HOUR_OF_DAY, 0);
	    c1.set(Calendar.MINUTE, 0);
	    c1.set(Calendar.SECOND, 0);
	    
	    Calendar c2 = new GregorianCalendar();
	    c2.set(Calendar.HOUR_OF_DAY, 23);
	    c2.set(Calendar.MINUTE, 59);
	    c2.set(Calendar.SECOND, 59);
	    
	    Date startTime = c1.getTime();
	    Date endTime = c2.getTime();
		hqlInfo.setParameter("startTime", startTime);
		hqlInfo.setParameter("endTime", endTime);
		List list = getForumTopicServiceImp(request).findValue(hqlInfo);
		Map<String,String> sumCateTopicInfoMap = new HashMap<String,String>();
		int sum = 0;
		for(int i=0;i<list.size();i++){
			Object[] result = (Object[])list.get(i);
			sumCateTopicInfoMap.put(result[1].toString(),result[0].toString());
			 if (StringUtil.isNotNull(categoryId)) {
				 sum += Integer.parseInt(result[0].toString());
			 }
		}
		 if (StringUtil.isNotNull(categoryId)) {
			 sumCateTopicInfoMap.put(categoryId,String.valueOf(sum));
		 }
		request.setAttribute("sumCateTopicInfoMap", sumCateTopicInfoMap);
	}

	/**
	 * 根据http请求，返回执行list操作需要用到的where语句。
	 * 
	 * type=directory:主目录，type=forum:版块
	 * 
	 * @param form
	 * @param request
	 * @return where语句字符串（不包含where关键字）
	 * @throws Exception
	 */
	@Override
	protected String getFindPageWhereBlock(HttpServletRequest request)
			throws Exception {
		String type = request.getParameter("type");
		String whereBlock = "";
		if (StringUtil.isNotNull(type)) {
			if ("directory".equals(type)) {
				whereBlock = "kmForumCategory.hbmParent is  null";
			} else {
				whereBlock = "kmForumCategory.hbmParent is not null";
			}
		}
		return whereBlock;
	}

	public ActionForward getForumTopicCategoryAll(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy("kmForumCategory.fdId");
		List<?> result = getServiceImp(request).findList(hqlInfo);
		// 添加日志信息
		UserOperHelper.logFindAll(result,
				getServiceImp(request).getModelName());
		JSONArray jsonArr = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		for (int i = 0; i < result.size(); i++) {
			KmForumCategory kmForumCategory = (KmForumCategory) result.get(i);
			jsonObj.put("text", kmForumCategory.getFdName());
			jsonObj.put("value", kmForumCategory.getFdId());
			jsonArr.add(jsonObj);

		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonArr.toString());
		return null;
	}

	/**
	 * 转移论坛版块
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeDirectory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String parentId = ((KmForumCategoryForm) form).getFdParentId();
		String ids = request.getParameter("values");
		try {
			((IKmForumCategoryService) getServiceImp(request))
					.updateForumDirectoy(ids, parentId);
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("success", mapping, form, request, response);

	}
	
	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();		
		String forward = "failure";
		String[] ids = null;
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			ids = request.getParameterValues("List_Selected");
			if (ids != null) {
				getServiceImp(request).delete(ids);
			}
		} catch (Exception e) {
			String key = "errors.unknown";
			if (e instanceof DataIntegrityViolationException || e instanceof ConstraintViolationException) {
				key = "error.constraintViolationException";
			}
			KmssMessage msg = new KmssMessage(key);
			messages.addError(msg);
		}
		
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
			return getActionForward(forward, mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, KmForumCategory.class);
	}
	
	

}
