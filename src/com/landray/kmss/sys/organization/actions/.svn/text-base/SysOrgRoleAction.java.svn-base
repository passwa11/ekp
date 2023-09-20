package com.landray.kmss.sys.organization.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.exception.ExistChildrenException;
import com.landray.kmss.sys.organization.forms.SysOrgRoleForm;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.service.ISysOrgRoleConfService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 叶中奇
 */
public class SysOrgRoleAction extends ExtendAction implements SysOrgConstant {
	protected ISysOrgRoleService sysOrgRoleService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysOrgRoleService == null) {
            sysOrgRoleService = (ISysOrgRoleService) getBean("sysOrgRoleService");
        }
		return sysOrgRoleService;
	}

	protected ISysOrgRoleConfService sysOrgRoleConfService;

	protected ISysOrgRoleConfService getSysOrgRoleConfService(
			HttpServletRequest request) {
		if (sysOrgRoleConfService == null) {
            sysOrgRoleConfService = (ISysOrgRoleConfService) getBean("sysOrgRoleConfService");
        }
		return sysOrgRoleConfService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		SysOrgRoleForm roleForm = (SysOrgRoleForm) form;
		String fdConfId = request.getParameter("fdConfId");
		if (StringUtil.isNotNull(fdConfId)) {
			SysOrgRoleConf orgRoleConf = (SysOrgRoleConf) getSysOrgRoleConfService(
					request).findByPrimaryKey(fdConfId);
			roleForm.setFdConfId(orgRoleConf.getFdId());
			roleForm.setFdConfName(orgRoleConf.getFdName());
			roleForm.setFdPlugin("sysOrgRolePluginService");
		} else {
			roleForm.setFdPlugin("sysOrgPlugin_Leader");
		}
		roleForm.setFdParameter("type=0&level=0");
		return form;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		hqlInfo.setWhereBlock("sysOrgRole.fdRoleConf is null ");
		
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo, SysOrgRole.class);

		String orderby = request.getParameter("orderby");
		String ordertype = request.getParameter("ordertype");

		if ("fdName".equals(orderby)) {
			String currentLocaleCountry = null;
			if (SysLangUtil.isLangEnabled()) {
				currentLocaleCountry = SysLangUtil
						.getCurrentLocaleCountry();
			if(StringUtil.isNotNull(currentLocaleCountry)&&currentLocaleCountry.equals(SysLangUtil.getOfficialLang())){
				currentLocaleCountry = null;
			}
			}
			if (StringUtil.isNotNull(currentLocaleCountry)) {
				orderby = "fdName" + currentLocaleCountry;
				boolean isReserve = false;
				if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
					isReserve = true;
				}
				if (isReserve) {
					orderby += " desc";
				}
				hqlInfo.setOrderBy(orderby);
			}
		}
	}

	/**
	 * 修改状态
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward chgenabled(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			if (ids != null) {
                ((ISysOrgRoleService) getServiceImp(request))
                        .updateInvalidated(ids, new RequestContext(request));
            }
		} catch (ExistChildrenException existChildren) {
			messages
					.addError(
							new KmssMessage(
									"global.message",
									"<a href='"
											+ request.getContextPath()
											+ "/sys/organization/sys_org_element/sysOrgElement.do?method=findChildrens&fdIds="
											+ StringUtil.escape(ArrayUtil
													.concat(ids, ','))
											+ "' target='_blank'>"
											+ ResourceUtil
													.getString(
															"sysOrgDept.error.existChildren",
															"sys-organization")
											+ ResourceUtil
													.getString(
															"sysOrgDept.error.existChildren.msg",
															"sys-organization")
											+ "<a>"), existChildren);
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
}
