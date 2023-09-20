package com.landray.kmss.sys.organization.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.service.ISysLogOrganizationService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

/**
 * @version 1.0
 * @author
 */
public class SysOrgLogAction extends ExtendAction implements SysOrgConstant {
	protected ISysOrgElementService sysOrgElementService = null;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
        }
		return sysOrgElementService;
	}
	
	private ISysLogOrganizationService sysLogOrganizationService;

	public ISysLogOrganizationService getSysLogOrganizationService() {
		if (sysLogOrganizationService == null) {
			sysLogOrganizationService = (ISysLogOrganizationService) SpringBeanUtil
					.getBean("sysLogOrganizationService");
		}
		return sysLogOrganizationService;
	}
	
	/**
	 * 查询变更日志
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward logList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
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
			
			if (StringUtil.isNull(orderby)) {
                orderby = "sysLogOrganization.fdCreateTime desc";
            }
			
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			
			String targetId = request.getParameter("targetId");
			// 判断请求来源，如果是组织机构查询变更记录时，跳转到特定的路径
			if (StringUtil.isNotNull(targetId)) {
				String whereBlock = hqlInfo.getWhereBlock();
				if (StringUtil.isNotNull(whereBlock)) {
                    hqlInfo.setWhereBlock(whereBlock + " and sysLogOrganization.fdTargetId = :targetId");
                } else {
                    hqlInfo.setWhereBlock("sysLogOrganization.fdTargetId = :targetId");
                }
				hqlInfo.setParameter("targetId", targetId);
			}
			
			Page page = getSysLogOrganizationService().findPage(hqlInfo);
			if(UserOperHelper.allowLogOper("Action_FindAll", getSysLogOrganizationService().getModelName())){
				UserOperContentHelper.putFinds(page.getList());
			}
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
			return getActionForward("logList", mapping, form, request, response);
		}
	}

}
