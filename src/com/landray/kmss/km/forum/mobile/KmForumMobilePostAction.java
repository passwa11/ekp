package com.landray.kmss.km.forum.mobile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.forum.model.KmForumPost;
import com.landray.kmss.km.forum.service.IKmForumPostService;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.mobile.annotation.Separater;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;


@Separater
public class KmForumMobilePostAction extends ExtendAction {

	protected IKmForumPostService kmForumPostService;

	protected IKmForumTopicService kmForumTopicService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmForumPostService == null) {
            kmForumPostService = (IKmForumPostService) getBean("kmForumPostService");
        }
		return kmForumPostService;
	}

	protected IKmForumTopicService getTopicServiceImp(HttpServletRequest request) {
		if (kmForumTopicService == null) {
            kmForumTopicService = (IKmForumTopicService) getBean("kmForumTopicService");
        }
		return kmForumTopicService;
	}
	
	
	/**
	 * 回复请求
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			IExtendForm rtnForm = null;
			String postId = request.getParameter("fdPostId");
			if (StringUtil.isNotNull(postId)) {
				IBaseModel model = getServiceImp(request).findByPrimaryKey(postId, null, true);
				if (model != null) {
					rtnForm = getServiceImp(request).convertModelToForm((IExtendForm) form, model,
							new RequestContext(request));
				}
				if (rtnForm == null) {
                    throw new NoRecordException();
                }
				request.setAttribute(getFormName(rtnForm, request), rtnForm);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("viewPost");
		}
	}
	

	@Override
	@Separater("/km/forum/mobile/view.jsp")
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			form.reset(mapping, request);
			String fdTopicId = request.getParameter("fdTopicId");
			if (!StringUtil.isNull(fdTopicId)) {
				if (MobileUtil.getClientType(new RequestContext(request)) == MobileUtil.PC) {
					return new ActionForward("/km/forum/km_forum/kmForumPost.do?method=view&fdTopicId=" + fdTopicId,
							true);
				}
				getTopicServiceImp(request).hitCount(fdTopicId);
				IBaseModel model = getTopicServiceImp(request).findByPrimaryKey(fdTopicId, null, true);
				request.setAttribute("forumTopic", model);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("viewTopic");
		}
	}

	public ActionForward listReplays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdTopicId = request.getParameter("fdTopicId");
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String sortby = request.getParameter("sortby");
			String isMore = request.getParameter("isMore");
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (StringUtil.isNotNull(fdTopicId)) {
				if (s_pageno != null && s_pageno.length() > 0) {
					pageno = Integer.parseInt(s_pageno);
				}
				if (s_rowsize != null && s_rowsize.length() > 0) {
					rowsize = Integer.parseInt(s_rowsize);
				}
				List topicList = getServiceImp(request).findList(
						"kmForumPost.kmForumTopic.fdId='" + fdTopicId
								+ "' and kmForumPost.fdFloor =1",
						"kmForumPost.fdFloor asc");
				KmForumPost topicPoster = (KmForumPost) topicList.get(0);
				HQLInfo hqlInfo = new HQLInfo();
				String whereBlock = "kmForumPost.kmForumTopic.fdId=:fdId and kmForumPost.fdFloor !=1 ";//
				hqlInfo.setParameter("fdId", fdTopicId);
				// 移动端回帖作者可见数据过滤
				if (topicPoster.getFdIsOnlyView() != null
						&& topicPoster.getFdIsOnlyView()) {
					// 匿名全部只能自己可见,作者可以看到全部，非作者只能看到自己
					if (topicPoster.getFdIsAnonymous()
							|| !topicPoster.getFdPoster().getFdId().equals(
									UserUtil.getUser(request).getFdId())) {
						whereBlock += " and (kmForumPost.fdFloor =1 or kmForumPost.fdPoster.fdId=:posterId)";
						hqlInfo.setParameter("posterId", UserUtil.getUser(
								request).getFdId());
					}
				}
				hqlInfo.setWhereBlock(whereBlock);
				String orderBy=StringUtil.isNotNull(sortby)? sortby: "kmForumPost.fdFloor asc";
				hqlInfo.setOrderBy(orderBy);
				hqlInfo.setPageNo(pageno);
				hqlInfo.setRowSize(rowsize);
				Page page = getServiceImp(request).findPage(hqlInfo);		
				if (!"true".equals(isMore)){//非加载更多记录的情况需要把楼主插入到第一个的位置，让它排在第一位
					page.getList().add(0, topicPoster);
				}
				request.setAttribute("queryPage", page);
				request.setAttribute("forumTopic", getTopicServiceImp(request).findByPrimaryKey(fdTopicId));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("listPost");
		}
	}

}
