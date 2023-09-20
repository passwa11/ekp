package com.landray.kmss.sys.fans.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.fans.service.ISysFansMainService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 关注机制 Action
 * 
 * @author
 * @version 1.0 2015-02-13
 */
public class SysFansMainAction extends ExtendAction {
	protected ISysFansMainService sysFansMainService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysFansMainService == null) {
            sysFansMainService =
                    (ISysFansMainService) getBean("sysFansMainService");
        }
		return sysFansMainService;
	}

	public ActionForward loadRlation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-loadRlation", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String userId = request.getParameter("userId");
			String attentModelName = request.getParameter("attentModelName");
			String fansModelName = request.getParameter("fansModelName");
			String isFollowPerson = request.getParameter("isFollowPerson");

			ISysFansMainService service =
					(ISysFansMainService) getServiceImp(request);
			Integer rela =
					service.getRelation(UserUtil.getUser().getFdId(), userId);

			json.put("relation", rela);
			json.put("userId", userId);
			json.put("fansModelName", fansModelName);
			if (StringUtil.isNotNull(isFollowPerson)
					&& "false".equals(isFollowPerson)) {
				json.put("attentModelName", attentModelName);
			} else {
				json.put("attentModelName", fansModelName);
			}
			json.put("isFollowPerson", isFollowPerson);
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-loadRlation", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	public ActionForward cancelAtt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject jsonObj = new JSONObject();
		try {

			String fdPersonId = request.getParameter("fdPersonId");
			if (StringUtil.isNotNull(fdPersonId)) {
				ISysFansMainService service =
						(ISysFansMainService) getServiceImp(request);
				HQLInfo hql = new HQLInfo();
				hql.setSelectBlock("fdId");
				hql.setWhereBlock("fdUserId=:fdUserId and fdFansId=:fdFansId");
				hql.setParameter("fdUserId", fdPersonId);
				hql.setParameter("fdFansId", UserUtil.getUser().getFdId());
				Object obj = service.findFirstOne(hql);
				if (obj != null) {
					service.delete(obj.toString());

					jsonObj.accumulate("flag", true);
				}
			} else {
				jsonObj.accumulate("flag", false);

			}
		} catch (Exception e) {
			messages.addError(e);

		}
		request.setAttribute("lui-source", jsonObj);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	public ActionForward addFollow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-addFollow", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		String fdPersonId = request.getParameter("fdPersonId");
		try {
			if (StringUtil.isNotNull(fdPersonId)) {
				ISysFansMainService service =
						(ISysFansMainService) getServiceImp(request);
				String result = service.updateFollow(request);

				json.put("relation", service
						.getRelation(UserUtil.getUser().getFdId(), fdPersonId));
				json.put("result", result);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		request.setAttribute("lui-source", json);
		TimeCounter.logCurrentTime("Action-addFollow", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	public ActionForward dataFollow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-dataFollow", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String id = request.getParameter("fdId");
			String type = request.getParameter("q.type");
			if (StringUtil.isNull(id) || StringUtil.isNull(type)) {
				throw new Exception("params:fdId, type must be not null!");
			}
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String fansModelName = request.getParameter("fansModelName");
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			if (isReserve) {
                orderby += " desc";
            }
			ISysFansMainService fansService =
					(ISysFansMainService) getServiceImp(request);
			Page page = fansService.findFollowPage(pageno, rowsize, orderby, id,
					type, fansModelName);

			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-dataFollow", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("followList", mapping, form, request,
					response);
		}
	}

	public ActionForward getRelationType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getRelationType", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		String personIdsStr = request.getParameter("personIdsStr");
		try {
			if (StringUtil.isNotNull(personIdsStr)) {
				ISysFansMainService service =
						(ISysFansMainService) getServiceImp(request);
				JSONArray relationType = service.getRelationByIds(personIdsStr);
				json.put("relation", relationType);
				json.put("success", true);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		request.setAttribute("lui-source", json);
		TimeCounter.logCurrentTime("Action-getRelationType", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}
}
