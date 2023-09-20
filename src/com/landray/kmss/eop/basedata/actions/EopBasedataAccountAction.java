package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataAccountForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAccount;
import com.landray.kmss.eop.basedata.service.IEopBasedataAccountService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class EopBasedataAccountAction extends EopBasedataBusinessAction {

    private IEopBasedataAccountService eopBasedataAccountService;

    @Override
	public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataAccountService == null) {
            eopBasedataAccountService = (IEopBasedataAccountService) getBean("eopBasedataAccountService");
        }
        return eopBasedataAccountService;
    }

    @Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String whereBlock=hqlInfo.getWhereBlock();
    	if(!UserUtil.checkRole("ROLE_EOPBASEDATA_ACCOUNT")){
    		//若是无员工账号设置权限，只能查看自己的账户
    		whereBlock=StringUtil.linkString(whereBlock, " and ", "eopBasedataAccount.fdPerson.fdId=:fdUserId");
    		hqlInfo.setParameter("fdUserId", UserUtil.getUser().getFdId());
    	}
    	hqlInfo.setWhereBlock(whereBlock);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataAccount.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataAccountForm eopBasedataAccountForm = (EopBasedataAccountForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataAccountService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataAccountForm;
    }
    
	public ActionForward addMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EopBasedataAccountForm acForm = (EopBasedataAccountForm) createNewForm(mapping, form, request, response);
		request.setAttribute("eopBasedataAccountForm", acForm);
		return mapping.findForward("editMobile");
	}
    
	public ActionForward editMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		return mapping.findForward("editMobile");
	}

	public ActionForward listMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HQLInfo hqlInfo =new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataAccount.fdPerson.fdId=:userId");
		hqlInfo.setParameter("userId",UserUtil.getUser().getFdId());
		request.setAttribute("queryPage", getServiceImp(request).findPage(hqlInfo));
		return mapping.findForward("listMobile");
	}
	
	public ActionForward updateByMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String method = request.getParameter("submitType");
		if("addMobile".equals(method)) {
			super.save(mapping, form, request, response);
		}else {
			super.update(mapping, form, request, response);
		}
		return new ActionForward("/eop/basedata/eop_basedata_account/eopBasedataAccount.do?method=listMobile",true);
	}
}
