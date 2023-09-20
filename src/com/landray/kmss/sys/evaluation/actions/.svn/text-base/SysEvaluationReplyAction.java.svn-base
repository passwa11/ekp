package com.landray.kmss.sys.evaluation.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.evaluation.model.SysEvaluationReply;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationReplyService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
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

public class SysEvaluationReplyAction extends BaseAction {
	
	protected ActionForward getActionForward(String defaultForward,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String para = request.getParameter("forward");
		if (!StringUtil.isNull(para) && !"failure".equals(defaultForward)) {
            defaultForward = para;
        }
		return mapping.findForward(defaultForward);
	}
	
	protected ISysEvaluationReplyService sysEvaluationReplyServiceImp;

	protected ISysEvaluationReplyService getServiceImp(
			HttpServletRequest request) {
		if (sysEvaluationReplyServiceImp == null) {
            sysEvaluationReplyServiceImp = (ISysEvaluationReplyService) getBean("sysEvaluationReplyService");
        }
		return sysEvaluationReplyServiceImp;
	}

	/**
	 * 添加回复
	 * 
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String replyId = getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			JSONObject rtnJson = new JSONObject();
			rtnJson.put("replyId", replyId);
			request.setAttribute("lui-source", rtnJson);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("lui-source", mapping, form, request,
                    response);
        }
	}

	public ActionForward listReplyCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-listReplyCount", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("ids");
			List<Object[]> counts = getServiceImp(request).getReplyCount(ids);
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < counts.size(); i++) {
				JSONObject count = new JSONObject();
				Object[] _count = counts.get(i);
				count.element("value", _count[0]);
				count.element("text", _count[1]);
				jsonArray.add(count);
			}
			request.setAttribute("lui-source", jsonArray);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-listReplyCount", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("lui-source", mapping, form, request,
                    response);
        }
	}

	@SuppressWarnings("unchecked")
	public ActionForward listReplyInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listReplyInfo", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String fdEvaluationId = request.getParameter("fdEvaluationId");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}

			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			if (s_rowsize != null && s_rowsize.length() > 0) {
				int rowsize = Integer.parseInt(s_rowsize);
				hqlInfo.setRowSize(rowsize);
			}

			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				whereBlock = "1=1 ";
			}
			if (!StringUtil.isNull(fdEvaluationId)) {
				whereBlock += " and sysEvaluationReply.fdModelId=:fdModelId ";
				hqlInfo.setParameter("fdModelId", fdEvaluationId);
			} else {
				throw new KmssException(
						new KmssMessage(
								"sys-evaluation:sysEvaluationReply.error.fdEvaluationId"));
			}
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setOrderBy("sysEvaluationReply.fdReplyTime");
			
			List<SysEvaluationReply> evalReplyList = (List<SysEvaluationReply>) getServiceImp(
					request).findList(hqlInfo);
			
			UserOperHelper.logFindAll(evalReplyList,
					getServiceImp(request).getModelName());
			JSONArray jArray = new JSONArray();
			for (SysEvaluationReply evalReply : evalReplyList) {
				JSONObject jObject = new JSONObject();
				String replyTime = DateUtil.convertDateToString(
						evalReply.getFdReplyTime(), DateUtil.TYPE_DATETIME,
						request.getLocale());
				// 被回复者
				if (evalReply.getFdParentReplyer() != null) {
					String parentReplyerName = evalReply.getFdParentReplyer()
							.getFdName();
					String parentReplyerId = evalReply.getFdParentReplyer()
							.getFdId();
					jObject.put("parentReplyerName", parentReplyerName);
					jObject.put("parentReplyerId", parentReplyerId);
				} else {
					jObject.put("parentReplayerName", "");
				}
				String docContent = evalReply.getDocContent ();
				if(StringUtil.isNotNull(docContent)) {
					// 防止表情被替换
					if(docContent.indexOf("img") > -1 || docContent.contains("<br>")) {
						docContent = evalReply.getDocContent();
					} else {
						docContent = docContent.replaceAll("<","&lt;").replaceAll (">", "&gt;");
					}
				}
				jObject.put("replyContent", docContent);
				jObject.put("replyTime", replyTime);
				jObject.put("replyId", evalReply.getFdId());
				String replyerId = evalReply.getFdReplyer().getFdId();
				jObject.put("replyerId", replyerId);
				String path = PersonInfoServiceGetter.getPersonHeadimageUrl(replyerId, "m");
				if (!PersonInfoServiceGetter.isFullPath(path)) {
					path = request.getContextPath() + path;
				}
				jObject.put("imgUrl", path);
				jObject.put("replyerName", evalReply.getFdReplyer().getFdName());
				jObject.put("currentUserId", UserUtil.getUser().getFdId());
				jArray.add(jObject);
			}

			JSONObject rtnJson = new JSONObject();
			rtnJson.put("replyInfo", jArray);
			request.setAttribute("lui-source", rtnJson);

		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-listReplyInfo", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("lui-source", mapping, form, request,
                    response);
        }
	}

	// 获取对话信息
	public ActionForward getReplyDoalogs(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getReplyDoalogs", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			String replyId = request.getParameter("replyId");
			String evalId = request.getParameter("evalId");
			String evalModelName = request.getParameter("evalModelName");// 全文点评或段落点评modelName
			ISysEvaluationReplyService service = (ISysEvaluationReplyService) getServiceImp(request);

			JSONArray replyDialogs = new JSONArray();
			replyDialogs = service.getParentReplyInfo(replyDialogs, replyId,
					new RequestContext(request));

			JSONObject json = new JSONObject();
			json.accumulate("evalId", evalId);
			json.accumulate("evalModelName", evalModelName);
			json.accumulate("replyDialogsInfo", replyDialogs);
			json.accumulate("currUserId", UserUtil.getUser().getFdId());
			request.setAttribute("replyDialogs", json);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-getReplyDoalogs", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("replyDialogs", mapping, form, request,
                    response);
        }
	}

	/**
	 * 分页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findReplyInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-findReplyInfo", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String fdEvaluationId = request.getParameter("fdEvaluationId");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}

			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			int rowsize = SysConfigParameters.getRowSize();
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			hqlInfo.setRowSize(rowsize);

			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				whereBlock = "1=1 ";
			}
			if (!StringUtil.isNull(fdEvaluationId)) {
				whereBlock += " and sysEvaluationReply.fdModelId=:fdModelId ";
				hqlInfo.setParameter("fdModelId", fdEvaluationId);
			} else {
				throw new KmssException(
						new KmssMessage(
								"sys-evaluation:sysEvaluationReply.error.fdEvaluationId"));
			}
			hqlInfo.setWhereBlock(whereBlock);
			Page page = (Page) getServiceImp(
					request).findPage(hqlInfo);

			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);

		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-findReplyInfo", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("lui-source", mapping, form, request,
                    response);
        }
	}
}
