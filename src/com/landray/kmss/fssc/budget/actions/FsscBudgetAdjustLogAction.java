package com.landray.kmss.fssc.budget.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.budget.forms.FsscBudgetAdjustLogForm;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustLog;
import com.landray.kmss.fssc.budget.service.IFsscBudgetAdjustLogService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class FsscBudgetAdjustLogAction extends ExtendAction {

    private IFsscBudgetAdjustLogService fsscBudgetAdjustLogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetAdjustLogService == null) {
            fsscBudgetAdjustLogService = (IFsscBudgetAdjustLogService) getBean("fsscBudgetAdjustLogService");
        }
        return fsscBudgetAdjustLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String whereBlock=hqlInfo.getWhereBlock();
    	String fdBudgetId=request.getParameter("fdBudgetId");
        if(StringUtil.isNotNull(fdBudgetId)){
      	  whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscBudgetAdjustLog.fdBudgetId=:fdBudgetId");
      	  hqlInfo.setParameter("fdBudgetId", fdBudgetId);
        }
        hqlInfo.setWhereBlock(whereBlock);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetAdjustLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscBudgetAdjustLogForm fsscBudgetAdjustLogForm = (FsscBudgetAdjustLogForm) super.createNewForm(mapping, form, request, response);
        ((IFsscBudgetAdjustLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscBudgetAdjustLogForm;
    }
}
