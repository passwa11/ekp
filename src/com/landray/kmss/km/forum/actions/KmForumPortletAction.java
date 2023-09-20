package com.landray.kmss.km.forum.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.forum.service.IKmForumCategoryService;
import com.landray.kmss.km.forum.service.IKmForumScoreService;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

/**
 * 论坛portlet
 * 
 */
public class KmForumPortletAction extends ExtendAction {
	protected IKmForumTopicService kmForumTopicService;
	protected IKmForumScoreService kmForumScoreService;
	protected IKmForumCategoryService kmForumCategoryService;

	@Override
	protected IKmForumTopicService getServiceImp(HttpServletRequest request) {
		if (kmForumTopicService == null) {
            kmForumTopicService = (IKmForumTopicService) getBean("kmForumTopicService");
        }
		return kmForumTopicService;
	}

	protected IKmForumScoreService getkmForumScoreService(
			HttpServletRequest request) {
		if (kmForumScoreService == null) {
            kmForumScoreService = (IKmForumScoreService) getBean("kmForumScoreService");
        }
		return kmForumScoreService;
	}

	protected IKmForumCategoryService getKmForumCategoryService(
			HttpServletRequest request) {
		if (kmForumCategoryService == null) {
            kmForumCategoryService = (IKmForumCategoryService) getBean("kmForumCategoryService");
        }
		return kmForumCategoryService;
	}

	/**
	 * 论坛主题portlet(我的、最新、精华、热门)
	 */
	public ActionForward getTopicList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-portlet", true, getClass());
		KmssMessages messages = new KmssMessages();
		RequestContext requestCtx = new RequestContext(request);
		Map<String, ?> rtnMap = null;
		try {
			rtnMap = getServiceImp(request).getTopicList(requestCtx);
			// 添加日志信息
			UserOperHelper.logFindAll(((Page) rtnMap.get("page")).getList(),
					getServiceImp(request).getModelName());
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-portlet", true, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-failure", mapping, form, request,
					response);
		} else {
			// 视图展现方式:classic(简单列表)、listtable(列表)
			String dataview = request.getParameter("dataview");
			if ("classic".equals(dataview)) {
				request.setAttribute("lui-source", rtnMap.get("datas"));
				return getActionForward("lui-source", mapping, form, request,
						response);
			} else {
				request.setAttribute("queryPage", rtnMap.get("page"));
				return getActionForward("kmForumTopicPortlet", mapping, form,
						request, response);
			}
		}
	}





	/**
	 * 论坛排名portlet(论坛积分排名(部门、公司)、论坛回帖数排名(部门、公司))
	 */
	public ActionForward getRankList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			RequestContext requestCtx = new RequestContext(request);
			Page page = getServiceImp(request).getRankList(requestCtx);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
			return getActionForward("kmForumRankPortlet", mapping, form,
					request, response);
		} catch (Exception e) {
			return getActionForward("lui-failure", mapping, form, request,
					response);
		}

	}
	
	/**
	 * 论坛主题  新版主题列表展现portlet(最新、热门)
	 */
	public ActionForward getTopicNewList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		try {
			RequestContext requestCtx = new RequestContext(request);
			Page page = getServiceImp(request).getTopicNewList(requestCtx);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
			return getActionForward("kmForumTopicNewPortlet", mapping, form,
					request, response);
		} catch (Exception e) {
			return getActionForward("lui-failure", mapping, form, request,
					response);
		}

	}
}
