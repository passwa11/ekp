package com.landray.kmss.eop.basedata.actions;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataSwitchForm;
import com.landray.kmss.eop.basedata.model.EopBasedataSwitch;
import com.landray.kmss.eop.basedata.service.IEopBasedataSwitchService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class EopBasedataSwitchAction extends EopBasedataBusinessAction {

    private IEopBasedataSwitchService eopBasedataSwitchService;

    @Override
    public IEopBasedataSwitchService getServiceImp(HttpServletRequest request) {
        if (eopBasedataSwitchService == null) {
            eopBasedataSwitchService = (IEopBasedataSwitchService) getBean("eopBasedataSwitchService");
        }
        return eopBasedataSwitchService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataSwitch.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataSwitchForm eopBasedataSwitchForm = (EopBasedataSwitchForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataSwitchService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataSwitchForm;
    }
    
	public ActionForward modifySwitch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		ActionForward forward = new ActionForward();
		try {
			EopBasedataSwitchForm eopBasedataSwitchForm = (EopBasedataSwitchForm) form;
			// 查找是否已经存在开关设置数据，存在则编辑，不存在则新增
			List<EopBasedataSwitch> switchList = getServiceImp(request).findList(new HQLInfo());
			if (ArrayUtil.isEmpty(switchList)) {
				eopBasedataSwitchForm.setMethod_GET("add");
				forward = super.add(mapping, form, request, response);
			} else {
				eopBasedataSwitchForm.setMethod_GET("edit");
				request.setAttribute("fromName", EopBasedataFsscUtil.getAllSwitchValue());
				forward.setPath("/eop/basedata/eop_basedata_switch/eopBasedataSwitch.do?method=edit&fdId="
								+ switchList.get(0).getFdId());
			}
			// 根据admin.do配置显示对应的信息
			request.setAttribute("todo", ResourceUtil.getKmssConfigString("kmss.notify.type.todo.enabled"));// 待办
			request.setAttribute("email", ResourceUtil.getKmssConfigString("kmss.notify.type.email.enabled"));// 邮件
			request.setAttribute("mobile", ResourceUtil.getKmssConfigString("kmss.notify.type.mobile.enabled"));// 短消息
			request.setAttribute("fromList", getServiceImp(request).viewOpenOrClose()); //开关账设置
			request.setAttribute("budgetRuleList", getServiceImp(request).budgetRuleDetail()); //预算导入规则
			request.setAttribute("deduBudgetList", getServiceImp(request).deduBdgetDetail()); //预算扣减逻辑
			request.setAttribute("deduProvisionList", getServiceImp(request).deduProvisionDetail()); //预提扣减逻辑
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return forward;
		}
	}

	/**
	 * 将浏览器提交的表单开关数据更新到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	public ActionForward updateSwitch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).updateSwitch((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		PrintWriter out = response.getWriter();
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			out.print("false");
		} else {
			out.print("true");
		}
		return null;
	}
	
	
	public ActionForward fdModule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            int pageno = 0;
            int rowsize = SysConfigParameters.getRowSize();
            if (s_pageno != null && s_pageno.length() > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setPageNo(pageno);
            hqlInfo.setRowSize(rowsize);
            Page page = new Page();
            page.setList(EopBasedataFsscUtil.getFsscModule(request));
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("selectModule");
            
        }
    }

}
