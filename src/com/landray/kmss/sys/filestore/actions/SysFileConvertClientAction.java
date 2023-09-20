package com.landray.kmss.sys.filestore.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.filestore.forms.SysFileConvertClientForm;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.service.ISysFileConvertClientService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

public class SysFileConvertClientAction extends ExtendAction {

	protected ISysFileConvertClientService sysFileConvertClientService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysFileConvertClientService == null) {
			sysFileConvertClientService = (ISysFileConvertClientService) SpringBeanUtil
					.getBean("sysFileConvertClientService");
		}
		return sysFileConvertClientService;
	}

	@Override
	public ActionForward data(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			// changeFindPageHQLInfo(request, hqlInfo);
			Page page = ((ISysFileConvertClientService) getServiceImp(request)).findOKPage(hqlInfo);
			// 记录日志
			if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FINDALL,
					SysFileConvertClient.class.getName())) {
				UserOperContentHelper.putFinds(page.getList());
			}
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}

	public ActionForward operations(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-operations", true, getClass());
		String cmd = request.getParameter("cmd");
		String clientId = request.getParameter("clientId");
		((ISysFileConvertClientService) getServiceImp(request)).operateClients(clientId, cmd);
		TimeCounter.logCurrentTime("Action-operations", false, getClass());
		return getActionForward("success", mapping, form, request, response);
	}

	public ActionForward config(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return edit(mapping, form, request, response);
	}

	public ActionForward saveConfig(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysFileConvertClientForm configForm = (SysFileConvertClientForm) form;
		((ISysFileConvertClientService) getServiceImp(request)).operateClients(configForm.getFdId(), "config",
				configForm.getTaskCapacity(), configForm.getTaskTimeout(), configForm.getLogLevel());
		return getActionForward("success", mapping, form, request, response);
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysFileConvertClientForm clientForm = (SysFileConvertClientForm) form;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			SysFileConvertClient client = (SysFileConvertClient) getServiceImp(request).findByPrimaryKey(id, null,
					true);
			if (client != null) {
				JSONObject config = JSONObject.fromObject(client.getConverterConfig());
				clientForm.setTaskCapacity(config.getString("taskCapacity"));
				clientForm.setTaskTimeout(config.getString("taskTimeout"));
				clientForm.setLogLevel(config.getString("logLevel"));
			}
		}
		if (clientForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(getFormName(clientForm, request), clientForm);
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		hqlInfo.setWhereBlock("converterConfig like :converterConfig");
		hqlInfo.setParameter("converterConfig", "%" + "\"deleted\":\"false\"" + "%");// 非删除状态的转换服务
	}
}
