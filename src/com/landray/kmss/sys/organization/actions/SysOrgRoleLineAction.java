package com.landray.kmss.sys.organization.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.model.SysOrgRoleLine;
import com.landray.kmss.sys.organization.service.ISysOrgRoleConfService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleLineService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 叶中奇
 */
public class SysOrgRoleLineAction extends ExtendAction {
	protected ISysOrgRoleLineService sysOrgRoleLineService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysOrgRoleLineService == null) {
            sysOrgRoleLineService = (ISysOrgRoleLineService) getBean("sysOrgRoleLineService");
        }
		return sysOrgRoleLineService;
	}

	protected ISysOrgRoleConfService sysOrgRoleConfService;

	protected ISysOrgRoleConfService getSysOrgRoleConfServiceImp(
			HttpServletRequest request) {
		if (sysOrgRoleConfService == null) {
            sysOrgRoleConfService = (ISysOrgRoleConfService) getBean("sysOrgRoleConfService");
        }
		return sysOrgRoleConfService;
	}

	/**
	 * 编辑角色线中的成员
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward roleTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdConfId = request.getParameter("fdConfId");
			if (StringUtil.isNull(fdConfId)) {
                throw new UnexpectedRequestException();
            }
			SysOrgRoleConf roleConf = (SysOrgRoleConf) getSysOrgRoleConfServiceImp(
					request).findByPrimaryKey(fdConfId, null, true);
			if (UserOperHelper.allowLogOper("roleTree",
					"com.landray.kmss.sys.organization.model.SysOrgRoleLine")) {
				UserOperContentHelper.putFind(roleConf);
			}
			request.setAttribute("fdConfId", fdConfId);
			request.setAttribute("fdConfName", roleConf.getFdName());
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("roleTree", mapping, form, request,
					response);
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			returnMessage(request, response);
			return null;
		}
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
			returnMessage(request, response);
			return null;
		}
	}

	private void returnMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysOrgRoleLine model = (SysOrgRoleLine) getServiceImp(request)
				.findByPrimaryKey(request.getParameter("fdId"));
		StringBuffer script = new StringBuffer();
		String name = model.getDisName();
		name = StringUtil.replace(name, "\"", "\\\"");
		SysOrgElement sysOrgRoleMember = model.getSysOrgRoleMember();
		String fdName = model.getFdName();
		String isExternal = "false";
		if (sysOrgRoleMember != null) {
			if(SysOrgConstant.ORG_TYPE_POST== model.getSysOrgRoleMember().getFdOrgType()){
				String disName = sysOrgRoleMember.getFdName()+"<"+ sysOrgRoleMember.getFdParentsName() +">";
				if (sysOrgRoleMember.getFdIsAvailable() != null && !sysOrgRoleMember.getFdIsAvailable()) // 无效
                {
                    disName += "(" + ResourceUtil.getString("sysOrg.address.info.disable", "sys-organization") + ")";
                }
				if (StringUtil.isNotNull(fdName)) {
                    disName = fdName + "(" + disName + ")";
                }

				name = disName;
			}
			isExternal = sysOrgRoleMember.getFdIsExternal().toString();
		}
		
		script.append("<script>parent.returnMessge(\"").append(model.getFdId())
				.append("\", \"").append(name).append("\", \"").append(isExternal).append("\"");
		if (model.getSysOrgRoleMember() != null) {
			script.append(',').append(
					model.getSysOrgRoleMember().getFdOrgType());
		}
		script.append(");</script>");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(script.toString());
	}
}
