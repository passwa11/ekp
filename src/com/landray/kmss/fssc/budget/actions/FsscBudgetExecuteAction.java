package com.landray.kmss.fssc.budget.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budget.forms.FsscBudgetExecuteForm;
import com.landray.kmss.fssc.budget.model.FsscBudgetExecute;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class FsscBudgetExecuteAction extends ExtendAction {

    private IFsscBudgetExecuteService fsscBudgetExecuteService;

    @Override
    public IFsscBudgetExecuteService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetExecuteService == null) {
            fsscBudgetExecuteService = (IFsscBudgetExecuteService) getBean("fsscBudgetExecuteService");
        }
        return fsscBudgetExecuteService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String whereBlock=hqlInfo.getWhereBlock();
    	String fdType=request.getParameter("fdType");
    	if(StringUtil.isNotNull(fdType)){
    		whereBlock=StringUtil.linkString(" fsscBudgetExecute.fdType=:fdType", " and ", whereBlock);
    		hqlInfo.setParameter("fdType", fdType);
    	}
    	whereBlock=StringUtil.linkString(" fsscBudgetExecute.fdMoney<>0", " and ", whereBlock);
    	hqlInfo.setWhereBlock(whereBlock);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetExecute.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscBudgetExecuteForm fsscBudgetExecuteForm = (FsscBudgetExecuteForm) super.createNewForm(mapping, form, request, response);
        ((IFsscBudgetExecuteService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscBudgetExecuteForm;
    }
    
    /**
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward executeData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			super.data(mapping, form, request, response);
			Page page=(Page) request.getAttribute("queryPage");
			List<FsscBudgetExecute> dataList=page.getList();
			request.setAttribute("valMap", getServiceImp(request).getExtraExecute(dataList));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}

    /**
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward viewBillBudget(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			request.setAttribute("billBudget", getServiceImp(request).viewBillBudget(request));
			request.setAttribute("fdBudgetWarn", EopBasedataFsscUtil.getSwitchValue("fdBudgetWarn"));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("viewBillBudget", mapping, form, request, response);
		}
	}
}
