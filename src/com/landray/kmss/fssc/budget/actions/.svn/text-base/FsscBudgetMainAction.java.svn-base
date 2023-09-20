package com.landray.kmss.fssc.budget.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataAuthUtil;
import com.landray.kmss.fssc.budget.forms.FsscBudgetMainForm;
import com.landray.kmss.fssc.budget.model.FsscBudgetMain;
import com.landray.kmss.fssc.budget.service.IFsscBudgetMainService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscBudgetMainAction extends ExtendAction {

    private IFsscBudgetMainService fsscBudgetMainService;

	@Override
    public IFsscBudgetMainService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetMainService == null) {
            fsscBudgetMainService = (IFsscBudgetMainService) getBean("fsscBudgetMainService");
        }
        return fsscBudgetMainService;
    }
	
	private IEopBasedataCompanyService eopBasedataCompanyService;
	
    public IEopBasedataCompanyService getEopBasedataCompanyService() {
    	 if (eopBasedataCompanyService == null) {
    		 eopBasedataCompanyService = (IEopBasedataCompanyService) getBean("eopBasedataCompanyService");
         }
		return eopBasedataCompanyService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String whereBlock=hqlInfo.getWhereBlock();
    	String fdSchemeId=request.getParameter("fdSchemeId");
    	if(StringUtil.isNotNull(fdSchemeId)&&!"null".equals(fdSchemeId)){
    		whereBlock=StringUtil.linkString(whereBlock, " and ", "scheme.fdId=:fdSchemeId");
    		hqlInfo.setParameter("fdSchemeId", fdSchemeId);
			hqlInfo.setJoinBlock(StringUtil.linkString(hqlInfo.getJoinBlock()," "," left join fsscBudgetMain.fdBudgetScheme scheme"));
    	}
    	/**********************由于预算没权限，所以在代码做控制************************/
    	SysOrgElement user=UserUtil.getUser();
    	if(!UserUtil.checkRole("ROLE_FSSCBUDGET_VIEW")){//没有查看所有权限
    		if(EopBasedataAuthUtil.isManagerOrStaff(null)){ //公司财务人员或者公司财务管理员
    			List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findCompanyByUserId(user.getFdId());
    			if(!ArrayUtil.isEmpty(companyList)){
					hqlInfo.setJoinBlock(StringUtil.linkString(hqlInfo.getJoinBlock()," ","  left join fsscBudgetMain.fdCompany company"));
    				whereBlock=StringUtil.linkString(whereBlock, " and ", HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";"))));
    			}
				whereBlock=StringUtil.linkString(whereBlock, " or ", "(fsscBudgetMain.docCreator.fdId=:docCreatorId) ");//创建者是公司财务时，自己创建无法查看
				hqlInfo.setParameter("docCreatorId", user.getFdId());
    		}else{
    			whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscBudgetMain.docCreator.fdId=:docCreatorId");
    			hqlInfo.setParameter("docCreatorId", user.getFdId());
    		}
    	}
    	/**********************由于预算没权限，所以在代码做控制************************/
    	String fdCompanyName = request.getParameter("q.fdCompanyName");
        if(StringUtil.isNotNull(fdCompanyName)){
			hqlInfo.setJoinBlock(StringUtil.linkString(hqlInfo.getJoinBlock()," ","  left join fsscBudgetMain.fdCompany com"));
        	whereBlock=StringUtil.linkString(whereBlock, " and ", "com.fdName like :fdCompanyName");
        	hqlInfo.setParameter("fdCompanyName","%"+fdCompanyName+"%");
        }
    	hqlInfo.setWhereBlock(whereBlock);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetMain.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscBudgetMainForm fsscBudgetMainForm = (FsscBudgetMainForm) super.createNewForm(mapping, form, request, response);
        ((IFsscBudgetMainService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscBudgetMainForm;
    }

	// 导出excel表数据
	public ActionForward downTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
				getServiceImp(request).downTemplate(request,response);
		} catch (Exception e) {
			messages.addError(e);
			return mapping.findForward("failure");
		}

		return null;

	}
	
	/**
	 * 保存导入预算明细数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveImport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();

		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			FsscBudgetMainForm mainForm = (FsscBudgetMainForm) form;
			JSONArray formList = getServiceImp(request).saveImport(mainForm,request);
			request.setAttribute("formList", formList);
		} catch (Exception e) {
			messages.addError(e);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(result.toString());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("result", mapping, form, request, response);
		}
	}
	
	/**
	 * 保存导入预算明细数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSaveImport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			FsscBudgetMainForm mainForm = (FsscBudgetMainForm) form;
			result = getServiceImp(request).initSaveImport(mainForm,request);
		} catch (Exception e) {
			messages.addError(e);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(result.toString());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			ActionForward forward=null;
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
			.save(request);
			if(result.containsKey("result")&&"success".equals(result.getString("result"))){
				request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
				forward=getActionForward("success", mapping, form, request, response);
			}else{
				forward=getActionForward("result", mapping, form, request, response);
			}
			return forward;
		}
	}
	
	/**
     * 
     * 预算结转
     */
    public ActionForward transferBudget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
        	String startMonth=request.getParameter("fdStartMonth");
    		if(StringUtil.isNotNull(startMonth)){
    			startMonth=startMonth.substring(1, 7);
    		}
    		String endMonth=request.getParameter("fdEndMonth");
    		if(StringUtil.isNotNull(endMonth)){
    			endMonth=endMonth.substring(1, 7);
    		}
    		String fdCompanyIds=request.getParameter("fdCompanyIds");
			getServiceImp(request).updateTransferBudget(startMonth, endMonth, fdCompanyIds);
		} catch (Exception e) {
			return getActionForward("failure", mapping, form, request, response);
		}
        request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
        return getActionForward("success", mapping, form, request, response);
    }
}
