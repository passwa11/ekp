package com.landray.kmss.sys.restservice.server.actions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.restservice.server.forms.SysRestserviceServerPolicyForm;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerPolicyService;
import com.landray.kmss.sys.restservice.server.util.SysRsUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.sso.client.util.StringUtil;

/**
 * RestService用户帐号设置 Action
 * 
 * @author  
 */
public class SysRestserviceServerPolicyAction extends ExtendAction {
	protected ISysRestserviceServerPolicyService sysRestserviceServerPolicyService;

	@Override
	protected ISysRestserviceServerPolicyService getServiceImp(HttpServletRequest request) {
		if (sysRestserviceServerPolicyService == null) {
			sysRestserviceServerPolicyService = (ISysRestserviceServerPolicyService) getBean("sysRestserviceServerPolicyService");
		}
		return sysRestserviceServerPolicyService;
	}
	
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysRestserviceServerPolicy.class);
	}

	/**
	 * 修改密码。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward editPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String fdId = request.getParameter("fdId");
		if (StringUtil.isNotNull(fdId)) {
			request.setAttribute("fdId", fdId);
		}

		try {
			if (oldPassword != null && newPassword != null) {
				if (!editPassword(request, fdId, oldPassword, newPassword)) {
					return mapping.findForward("editPassword");
				}
			} else {
				return mapping.findForward("editPassword");
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * @throws IOException
	 */
	private Boolean editPassword(HttpServletRequest request, String fdId,
			String oldPassword, String newPassword) throws Exception {
		Boolean isEdit = false;

		ISysRestserviceServerPolicyService service = (ISysRestserviceServerPolicyService) getServiceImp(request);
		SysRestserviceServerPolicy user = (SysRestserviceServerPolicy) service
				.findByPrimaryKey(fdId);
		oldPassword = SysRsUtil.encryptPwd(oldPassword);

		if (oldPassword.equals(user.getFdPassword())) {
			newPassword = SysRsUtil.encryptPwd(newPassword);
			user.setFdPassword(newPassword);
			service.update(user);
			isEdit = true;
		} else {
			request.setAttribute("alertPassword", "原密码输入错误，请重新输入！");
			isEdit = false;
		}
		return isEdit;
	}

	/**
	 * 将浏览器提交的表单数据添加到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		ISysRestserviceServerPolicyService service = getServiceImp(request);
		SysRestserviceServerPolicyForm policyForm = (SysRestserviceServerPolicyForm) form;

		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}

			// 判断用户名或登录ID是否重复
			SysRestserviceServerPolicy user = service.findUser(policyForm.getFdName().trim(),
					policyForm.getFdLoginId().trim());
			if (user != null) {
				request.setAttribute("alertPassword", "策略名称或登录账号已经存在，请重新输入！");
				return mapping.findForward("edit");
			}

			service.add((IExtendForm) form, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
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

}
