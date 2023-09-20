package com.landray.kmss.fssc.fee.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.fee.constant.FsscFeeConstant;
import com.landray.kmss.fssc.fee.forms.FsscFeeLedgerForm;
import com.landray.kmss.fssc.fee.model.FsscFeeLedger;
import com.landray.kmss.fssc.fee.service.IFsscFeeLedgerService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class FsscFeeLedgerAction extends ExtendAction {

    private IFsscFeeLedgerService fsscFeeLedgerService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscFeeLedgerService == null) {
            fsscFeeLedgerService = (IFsscFeeLedgerService) getBean("fsscFeeLedgerService");
        }
        return fsscFeeLedgerService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscFeeLedger.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        String where = hqlInfo.getWhereBlock();
        String fdFeeId = request.getParameter("fdFeeId");
        if(StringUtil.isNotNull(fdFeeId)){
        	where = StringUtil.linkString(where, " and ", "fsscFeeLedger.fdLedgerId in(select fdId from FsscFeeLedger where fdModelId =:fdFeeId and fdType=:type1)");
        	hqlInfo.setParameter("fdFeeId",fdFeeId);
        	hqlInfo.setParameter("type1",FsscFeeConstant.FSSC_FEE_LEDGER_TYPE_INIT);
        }
        hqlInfo.setWhereBlock(where);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscFeeLedgerForm fsscFeeLedgerForm = (FsscFeeLedgerForm) super.createNewForm(mapping, form, request, response);
        ((IFsscFeeLedgerService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscFeeLedgerForm;
    }
    
    public ActionForward executeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	KmssMessages messages = new KmssMessages();
		try {
	    	super.data(mapping, form, request, response);
			Page page=(Page) request.getAttribute("queryPage");
			List<FsscFeeLedger> list = page.getList();
			request.setAttribute("valMap", ((IFsscFeeLedgerService)getServiceImp(request)).processListData(list));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
    }
}
