package com.landray.kmss.sys.evaluation.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.evaluation.forms.SysEvaluationShareForm;
import com.landray.kmss.sys.evaluation.model.SysEvaluationShare;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationShareService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

public class SysEvaluationShareAction extends ExtendAction {

    private ISysEvaluationShareService sysEvaluationShareService;

    @Override
    public ISysEvaluationShareService getServiceImp(HttpServletRequest request) {
        if (sysEvaluationShareService == null) {
            sysEvaluationShareService = (ISysEvaluationShareService) getBean("sysEvaluationShareService");
        }
        return sysEvaluationShareService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, SysEvaluationShare.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.sys.evaluation.util.SysEvaluationUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.sys.evaluation.model.SysEvaluationShare.class);
        com.landray.kmss.sys.evaluation.util.SysEvaluationUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysEvaluationShareForm sysEvaluationShareForm = (SysEvaluationShareForm) super.createNewForm(mapping, form, request, response);
        ((ISysEvaluationShareService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return sysEvaluationShareForm;
    }

	/**
	 * 异步保存分享信息
	 */
	public ActionForward saveEvalShare(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-saveEvalShare", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }

			// 处理分享
			getServiceImp(request).saveEvalShare((IExtendForm) form, new RequestContext(request));

			JSONObject json = new JSONObject();
			json.element("flag", true);
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-saveEvalShare", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

    /**
     * 查看分享记录
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return 执行成功，返回list页面，否则返回failure页面
     * @throws Exception
     */
    public ActionForward viewAll(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            String shareMode = request.getParameter("fdShareMode");
            String modelId = request.getParameter("fdModelId");
            String modelName = request.getParameter("fdModelName");
            if (StringUtil.isNull(modelId) || StringUtil.isNull(modelName)) {
                throw new KmssException(
                        new KmssMessage(
                                "sys-evaluation:sysEvaluationShare.error.modelIdAndModelName"));
            }

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

            String where = "1=1";
			where += " and sysEvaluationShare.fdShareMode=:fdShareMode and sysEvaluationShare.fdModelId=:fdModelId and sysEvaluationShare.fdModelName=:fdModelName";
			hqlInfo.setParameter("fdShareMode", Integer.valueOf(shareMode));
            hqlInfo.setParameter("fdModelId", modelId);
            hqlInfo.setParameter("fdModelName", modelName);

            hqlInfo.setWhereBlock(where);
            hqlInfo.setOrderBy(orderby);
            hqlInfo.setPageNo(pageno);
            hqlInfo.setRowSize(rowsize);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
            UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
        } catch (Exception e) {
            messages.addError(e);
        }
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return getActionForward("listLog", mapping, form, request, response);
        }
    }
}
