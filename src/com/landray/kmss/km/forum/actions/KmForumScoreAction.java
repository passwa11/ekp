package com.landray.kmss.km.forum.actions;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.forum.forms.KmForumScoreForm;
import com.landray.kmss.km.forum.model.KmForumScore;
import com.landray.kmss.km.forum.service.IKmForumScoreService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2006-Sep-07
 * 
 * @author 吴兵
 */
public class KmForumScoreAction extends ExtendAction

{
	protected IKmForumScoreService kmForumScoreService;

	protected ISysOrgCoreService sysOrgCoreService;

	@Override
	protected IKmForumScoreService getServiceImp(HttpServletRequest request) {
		if (kmForumScoreService == null) {
            kmForumScoreService = (IKmForumScoreService) getBean("kmForumScoreService");
        }
		return kmForumScoreService;
	}

	protected ISysOrgCoreService getSysOrgCoreServiceImp(
			HttpServletRequest request) {
		if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
        }
		return sysOrgCoreService;
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String type=request.getParameter("type");
		try {
			form.reset(mapping, request);
			getServiceImp(request).convertModelToForm((IExtendForm) form,
					getKmForumScore(request), new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			if(StringUtil.isNotNull(type)&& "person".equals(type)){
				return mapping.findForward("editForPerson");
			}else{
				return mapping.findForward("edit");
			}
		}
	}
	

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			form.reset(mapping, request);
			getServiceImp(request).convertModelToForm((IExtendForm) form,
					getKmForumScore(request), new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("view");
		}
	}

	private KmForumScore getKmForumScore(HttpServletRequest request)
			throws Exception {
		String fdId = request.getParameter("fdId");

		String owner = request.getParameter("owner");
		if (!StringUtil.isNull(owner)) {
			fdId = UserUtil.getUser().getFdId();
			request.setAttribute("userId", fdId);
		}
		if ("0".equals(fdId)) {
			fdId = UserUtil.getUser().getFdId();
		}
		KmForumScore model = (KmForumScore) getServiceImp(request)
				.findByPrimaryKey(fdId, null, true);
		if (model == null) {
			model = new KmForumScore();
			SysOrgPerson person = (SysOrgPerson) getSysOrgCoreServiceImp(
					request).findByPrimaryKey(fdId, SysOrgPerson.class);
			model.setPerson(person);
			model.setFdId(fdId);
		}
		UserOperHelper.logFind(model);// 添加日志信息
		return model;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdId = request.getParameter("fdId");
			String owner = request.getParameter("owner");
			if (!StringUtil.isNull(owner)) {
				fdId = UserUtil.getUser().getFdId();
			}
			if ("0".equals(fdId)) {
				fdId = UserUtil.getUser().getFdId();
			}
			KmForumScoreForm kmForumScoreForm = (KmForumScoreForm) form;
			kmForumScoreForm.setFdId(fdId);
			if (getServiceImp(request).findByPrimaryKey(fdId, null, true) != null) {
				getServiceImp(request).update(kmForumScoreForm,
						new RequestContext(request));
			} else {
				getServiceImp(request).add(kmForumScoreForm,
						new RequestContext(request));
			}
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
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	public ActionForward updateScore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			KmForumScoreForm kmForumScoreForm = (KmForumScoreForm) form;
		    String fdId=UserUtil.getUser().getFdId();
		    kmForumScoreForm.setFdId(fdId);
			if (getServiceImp(request).findByPrimaryKey(fdId, null, true) != null) {
				getServiceImp(request).update(kmForumScoreForm,
						new RequestContext(request));
			} else {
				getServiceImp(request).add(kmForumScoreForm,
						new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		PrintWriter out=response.getWriter();
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			out.print("false");
		} else {
			out.print("true");
		}
		return null;
	}


	/**
	 * 更新压缩所有用户头像。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateAllUserAvatar(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateAllUserPic", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			getServiceImp(request).updateAllUserAvatar(
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter
				.logCurrentTime("Action-updateAllUserPic", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}
}
