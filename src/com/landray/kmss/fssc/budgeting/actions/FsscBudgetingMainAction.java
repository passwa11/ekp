package com.landray.kmss.fssc.budgeting.actions;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budgeting.constant.FsscBudgetingConstant;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingMainForm;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingMain;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingMainService;
import com.landray.kmss.sys.edition.forms.SysEditionMainForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class FsscBudgetingMainAction extends ExtendAction {

    private IFsscBudgetingMainService fsscBudgetingMainService;

    @Override
    public IFsscBudgetingMainService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetingMainService == null) {
            fsscBudgetingMainService = (IFsscBudgetingMainService) getBean("fsscBudgetingMainService");
        }
        return fsscBudgetingMainService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetingMain.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscBudgetingMainForm fsscBudgetingMainForm = (FsscBudgetingMainForm) super.createNewForm(mapping, form, request, response);
        Map<String,Boolean> authMap=getServiceImp(request).getBudgetingViewAuth(request, request.getParameter("fdOrgId"));
        request.setAttribute("authMap", authMap);
        ((IFsscBudgetingMainService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscBudgetingMainForm;
    }
    /************************************************
     * 自下而上 预算编制跳转页面,若是存已经存在预算编制，则跳转查看页面，若是不存在，
     * 则跳转新建页面（非机构/公司）
     ***********************************************/
    public ActionForward budgeting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-budgeting", true, getClass());
		KmssMessages messages = new KmssMessages();
		String defaultForward="edit";  //默认跳转编辑页面
		String fdId="";
		try {
			ActionForm newForm =null;
			//判断是否已经存在预算编制，存在则跳转查看页面
			boolean isView=getServiceImp(request).isView(request);
			if(isView){
				defaultForward="view";
				loadActionForm(mapping, form, request, response);
				FsscBudgetingMainForm mainForm=(FsscBudgetingMainForm) form;
				fdId=mainForm.getFdId();
				request.setAttribute("isView", true);
			}else{
				//校验当前人员是否有当前机构的预算编制权限
				if(!getServiceImp(request).checkBudgetingAuth(((FsscBudgetingMainForm) form).getFdOrgId())){
					defaultForward="e403";//无权限
				}else{
					newForm = createNewForm(mapping, form, request, response);
					FsscBudgetingMainForm mainForm=(FsscBudgetingMainForm) newForm;
					fdId=mainForm.getFdId();
					mainForm.setMethod_GET("add");
					if (newForm != form) {
                        request.setAttribute(getFormName(newForm, request), newForm);
                    }
					super.add(mapping, mainForm, request, response);  //默认初始化为add
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-budgeting", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			ActionForward forward=null;
			String rtnMsg=(String) request.getAttribute("rtnMsg");
			if(StringUtil.isNotNull(rtnMsg)){
				defaultForward="warning";
			}
			if("view".equals(defaultForward)){
				forward=new ActionForward("/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=view&orgType="
			+request.getParameter("orgType")+"&fdOrgId="+request.getParameter("fdOrgId")+"&fdSchemeId="
			+request.getParameter("fdSchemeId")+"&fdId="+fdId,true);
			}else{
				forward=getActionForward(defaultForward, mapping, form, request, response);
			}
			return forward;
		}
	}
    /**
     * 自下而上  展现预算汇总（公司/机构）
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward budgetingUp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 

		TimeCounter.logCurrentTime("Action-budgetingUp", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			request.setAttribute("tempList", getServiceImp(request).gatherOrg(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-budgetingUp", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("budgetingUp", mapping, form, request, response);
		}
    }
    /************************************************
     * 自上而下 预算编制跳转页面,若是存已经存在预算编制，则跳转查看页面，若是不存在，
     * 则跳转新建页面（非机构/公司）
     ***********************************************/
    public ActionForward budgetingChild(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    				throws Exception {
    	TimeCounter.logCurrentTime("Action-budgeting", true, getClass());
    	KmssMessages messages = new KmssMessages();
    	String defaultForward="childEdit";  //默认跳转编辑页面
    	String fdId="";
    	try {
    		ActionForm newForm =null;
    		//判断是否已经存在预算编制，存在则跳转查看页面
    		boolean isChildView=getServiceImp(request).isInitView(request);
    		if(isChildView){
    			defaultForward="childView";
    			fdId=(String) request.getAttribute("fdId");
    		}else{
    			//校验当前人员是否有当前机构的预算编制权限
				if(!getServiceImp(request).checkBudgetingAuth(((FsscBudgetingMainForm) form).getFdOrgId())){
					defaultForward="e403";//无权限
				}else{
					defaultForward="childEdit";
	    			newForm = createNewForm(mapping, form, request, response);
	    			FsscBudgetingMainForm mainForm=(FsscBudgetingMainForm) newForm;
	    			fdId=mainForm.getFdId();
	    			mainForm.setMethod_GET("add");
	    			if (newForm != form) {
                        request.setAttribute(getFormName(newForm, request), newForm);
                    }
	    			super.add(mapping, mainForm, request, response);  //默认初始化为add
				}
    		}
    	} catch (Exception e) {
    		messages.addError(e);
    	}
    	
    	TimeCounter.logCurrentTime("Action-budgeting", false, getClass());
    	if (messages.hasError()) {
    		KmssReturnPage.getInstance(request).addMessages(messages)
    		.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
    		return getActionForward("failure", mapping, form, request, response);
    	} else {
    		ActionForward forward=null;
    		String rtnMsg=(String) request.getAttribute("rtnMsg");
			if(StringUtil.isNotNull(rtnMsg)){
				defaultForward="warning";
			}
    		if("childView".equals(defaultForward)){
    			forward=new ActionForward("/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=childView&orgType="
    					+request.getParameter("orgType")+"&fdOrgId="+request.getParameter("fdOrgId")+"&fdSchemeId="
    					+request.getParameter("fdSchemeId")+"&fdId="+fdId+"&fdCompanyId="+request.getParameter("fdCompanyId"),true);
    		}else{
    			forward=getActionForward(defaultForward, mapping, form, request, response);
    		}
    		return forward;
    	}
    }
    /**
     * 非机构/公司预算编制
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward budgetingEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			IExtendForm rtnForm = null;
			FsscBudgetingMainForm mainForm=(FsscBudgetingMainForm) form;
			mainForm.setMethod("edit");
			mainForm.setMethod_GET("edit");
			String fdId=mainForm.getFdId();
			if(StringUtil.isNotNull(fdId)){
				Map<String,Boolean> authMap=getServiceImp(request).getBudgetingViewAuth(request, mainForm.getFdOrgId());
				FsscBudgetingMain main=(FsscBudgetingMain) getServiceImp(request).findByPrimaryKey(fdId, null, true);
				getServiceImp(request).completeDetail(request,main,"childView",authMap);
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) mainForm, main, new RequestContext(request));
			}
			if (rtnForm == null) {
                throw new NoRecordException();
            }
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("childEdit", mapping, form, request, response);
		}
	}
    /**
     * 自上而下  编制最上级预算（公司/机构）
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward budgetingInit(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    				throws Exception {
    	TimeCounter.logCurrentTime("Action-budgetingInit", true, getClass());
    	KmssMessages messages = new KmssMessages();
    	String defaultForward="edit";  //默认跳转编辑页面
    	String fdId="";
    	try {
    		ActionForm newForm =null;
    		//判断是否已经存在预算编制，存在则跳转查看页面
    		boolean isInitView=getServiceImp(request).isInitView(request);
    		if(isInitView){
    			defaultForward="initView";
    			fdId=(String) request.getAttribute("fdId");
    		}else{
    			//校验当前人员是否有当前机构的预算编制权限
				if(!getServiceImp(request).checkBudgetingAuth(((FsscBudgetingMainForm) form).getFdOrgId())){
					defaultForward="e403";//无权限
				}else{
					defaultForward="initEdit";
	    			newForm = createNewForm(mapping, form, request, response);
	    			FsscBudgetingMainForm mainForm=(FsscBudgetingMainForm) newForm;
	    			mainForm.setFdTotalMoney("0.00");
	    			mainForm.setFdChildTotalMoney("0.00");
	    			mainForm.setMethod_GET("add");
	    			fdId=mainForm.getFdId();
	    			if (newForm != form) {
                        request.setAttribute(getFormName(newForm, request), newForm);
                    }
	    			super.add(mapping, mainForm, request, response);  //默认初始化为add
				}
    		}
    	} catch (Exception e) {
    		messages.addError(e);
    	}
    	
    	TimeCounter.logCurrentTime("Action-budgetingInit", false, getClass());
    	if (messages.hasError()) {
    		KmssReturnPage.getInstance(request).addMessages(messages)
    		.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
    		return getActionForward("failure", mapping, form, request, response);
    	} else {
    		ActionForward forward=null;
			if("initView".equals(defaultForward)){
				forward=new ActionForward("/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=initView&orgType="
			+request.getParameter("orgType")+"&fdOrgId="+request.getParameter("fdOrgId")+"&fdSchemeId="
			+request.getParameter("fdSchemeId")+"&fdId="+fdId,true);
			}else{
				forward=getActionForward(defaultForward, mapping, form, request, response);
			}
    		return forward;
    	}
    }
    /**
     * 机构/公司编辑预算编制
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			IExtendForm rtnForm = null;
			IBaseModel model=null;
			FsscBudgetingMainForm mainForm=(FsscBudgetingMainForm) form;
			mainForm.setMethod("edit");
			mainForm.setMethod_GET("edit");
			String fdId=mainForm.getFdId();
			if(StringUtil.isNotNull(fdId)){
				String id = (String) request.getAttribute("fdId");
	    		if(StringUtil.isNull(id)){
	    			id=request.getParameter("fdId");
	    		}
	    		if (!StringUtil.isNull(id)) {
	    			model = getServiceImp(request).findByPrimaryKey(id,
	    					null, true);
	    			if (model != null){
	    				rtnForm = getServiceImp(request).convertModelToForm(
	    						(IExtendForm) form, model, new RequestContext(request));
	    			}
	    		}
	    		if (rtnForm == null) {
                    throw new NoRecordException();
                }
	    		request.setAttribute(getFormName(rtnForm, request), rtnForm);
				Map<String,Boolean> authMap=getServiceImp(request).getBudgetingViewAuth(request, mainForm.getFdOrgId());
				FsscBudgetingMain main=(FsscBudgetingMain) getServiceImp(request).findByPrimaryKey(fdId, null, true);
				getServiceImp(request).completeDetail(request,main,"initView",authMap);
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) mainForm, main, new RequestContext(request));
			}
			if (rtnForm == null) {
                throw new NoRecordException();
            }
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("initEdit", mapping, form, request, response);
		}
	}
    
    /**
     * 自上而下预算编制查看页面  机构/公司
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initView(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    				throws Exception {
    	TimeCounter.logCurrentTime("Action-initView", true, getClass());
    	KmssMessages messages = new KmssMessages();
    	form.reset(mapping, request);
		IExtendForm rtnForm = null;
		IBaseModel model=null;
		boolean authFlag=false; //默认不能查看预算编制
    	try {
			String id = (String) request.getAttribute("fdId");
    		if(StringUtil.isNull(id)){
    			id=request.getParameter("fdId");
    		}
    		if (!StringUtil.isNull(id)) {
    			model = getServiceImp(request).findByPrimaryKey(id,
    					null, true);
    			if (model != null){
    				FsscBudgetingMain main=(FsscBudgetingMain) model;
    				Map<String,Boolean> authMap=getServiceImp(request).getBudgetingViewAuth(request,main.getFdOrgId());
    				request.setAttribute("authMap", authMap);
    				authFlag=authMap.get("authFlag");
    				getServiceImp(request).completeDetail(request,main,"initView",authMap);
    				rtnForm = getServiceImp(request).convertModelToForm(
    						(IExtendForm) form, main, new RequestContext(request));
    			}
    		}
    		if (rtnForm == null) {
                throw new NoRecordException();
            }
    		request.setAttribute(getFormName(rtnForm, request), rtnForm);
    		FsscBudgetingMainForm mainForm=(FsscBudgetingMainForm) form;
    		
    		SysEditionMainForm editionForm = mainForm.getEditionForm();
    		if(FsscBudgetingConstant.FD_STATUS_REFUSE.equals(mainForm.getFdStatus())
    				&&"true".equals(mainForm.getDocIsNewVersion())
    				&&Boolean.TRUE.equals(request.getAttribute("budgetAuth"))
    				&&StringUtil.isNull(request.getParameter("viewVersion"))){//有编制权限才能做新版本
    			editionForm.setEnabledNewVersion("true");
    		}
    		mainForm.setEditionForm(editionForm);
    		List historyOptionList=getServiceImp(request).getHistoryOptionList(mainForm.getFdId());
			if(!ArrayUtil.isEmpty(historyOptionList)) {
				request.setAttribute("historyOptionList", historyOptionList);
			}
    	} catch (Exception e) {
    		messages.addError(e);
    	}
    	
    	TimeCounter.logCurrentTime("Action-initView", false, getClass());
    	if (messages.hasError()) {
    		KmssReturnPage.getInstance(request).addMessages(messages)
    		.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
    		return getActionForward("failure", mapping, form, request, response);
    	}else if(!authFlag){
    		return getActionForward("e403", mapping, form, request, response);
    	} else {
    		return getActionForward("initView", mapping, form, request, response);
    	}
    }
    /**
     * 独立编制预算
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward budgetingIndependent(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    				throws Exception {
    	TimeCounter.logCurrentTime("Action-budgetingIndependent", true, getClass());
    	KmssMessages messages = new KmssMessages();
    	String defaultForward="edit";  //默认跳转编辑页面
    	String fdId="";
    	try {
    		ActionForm newForm =null;
    		//判断是否已经存在预算编制，存在则跳转查看页面
    		boolean isInitView=getServiceImp(request).isInitView(request);
    		if(isInitView){
    			defaultForward="independentView";
    			fdId=(String) request.getAttribute("fdId");
    		}else{
    			//校验当前人员是否有当前机构的预算编制权限
				if(!getServiceImp(request).checkBudgetingAuth(((FsscBudgetingMainForm) form).getFdOrgId())){
					defaultForward="e403";//无权限
				}else{
					defaultForward="independentEdit";
	    			newForm = createNewForm(mapping, form, request, response);
	    			FsscBudgetingMainForm mainForm=(FsscBudgetingMainForm) newForm;
	    			fdId=mainForm.getFdId();
	    			mainForm.setFdTotalMoney("0.00");
	    			mainForm.setFdChildTotalMoney("0.00");
	    			mainForm.setMethod_GET("add");
	    			if (newForm != form) {
                        request.setAttribute(getFormName(newForm, request), newForm);
                    }
	    			super.add(mapping, mainForm, request, response);  //默认初始化为add
				}
    		}
    	} catch (Exception e) {
    		messages.addError(e);
    	}
    	
    	TimeCounter.logCurrentTime("Action-budgetingIndependent", false, getClass());
    	if (messages.hasError()) {
    		KmssReturnPage.getInstance(request).addMessages(messages)
    		.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
    		return getActionForward("failure", mapping, form, request, response);
    	} else {
    		ActionForward forward=null;
			if("independentView".equals(defaultForward)){
				forward=new ActionForward("/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=independentView&orgType="
			+request.getParameter("orgType")+"&fdOrgId="+request.getParameter("fdOrgId")+"&fdSchemeId="
			+request.getParameter("fdSchemeId")+"&fdId="+fdId,true);
			}else{
				forward=getActionForward(defaultForward, mapping, form, request, response);
			}
    		return forward;
    	}
    }
    /**
     * 独立预算编制编辑
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward independentEdit(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    				throws Exception {
    	TimeCounter.logCurrentTime("Action-edit", true, getClass());
    	KmssMessages messages = new KmssMessages();
    	try {
    		IExtendForm rtnForm = null;
    		FsscBudgetingMainForm mainForm=(FsscBudgetingMainForm) form;
    		mainForm.setMethod("edit");
    		mainForm.setMethod_GET("edit");
    		String fdId=mainForm.getFdId();
    		if(StringUtil.isNotNull(fdId)){
				Map<String,Boolean> authMap=getServiceImp(request).getBudgetingViewAuth(request, mainForm.getFdOrgId());
    			FsscBudgetingMain main=(FsscBudgetingMain) getServiceImp(request).findByPrimaryKey(fdId, null, true);
    			getServiceImp(request).completeDetail(request,main,"initView",authMap);
    			rtnForm = getServiceImp(request).convertModelToForm(
    					(IExtendForm) mainForm, main, new RequestContext(request));
    		}
    		if (rtnForm == null) {
                throw new NoRecordException();
            }
    		request.setAttribute(getFormName(rtnForm, request), rtnForm);
    	} catch (Exception e) {
    		messages.addError(e);
    	}
    	
    	TimeCounter.logCurrentTime("Action-edit", false, getClass());
    	if (messages.hasError()) {
    		KmssReturnPage.getInstance(request).addMessages(messages)
    		.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
    		return getActionForward("failure", mapping, form, request, response);
    	} else {
    		return getActionForward("initEdit", mapping, form, request, response);
    	}
    }
    /**
     * 自下而上预算编制查看方法
     */
    @Override
    public ActionForward view(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		ActionForward forward=null;
		boolean authFlag=true;
		try {
			loadActionForm(mapping, form, request, response);
			FsscBudgetingMainForm mainForm=(FsscBudgetingMainForm) form;
			//校验当前人员是否有当前机构的预算编制审核/编制权限
			Map<String,Boolean> authMap=(Map<String, Boolean>) request.getAttribute("authMap");
			authFlag=authMap.get("authFlag");
			if("edition".equals(request.getParameter("viewPattern"))||"redirect".equals(request.getParameter("viewType"))){
				//查看历史版本时，所有默认方法都会走view,故重新定向页面
				forward=getServiceImp(request).getViewForward(mainForm);
			}
			SysEditionMainForm editionForm = mainForm.getEditionForm();
			if(FsscBudgetingConstant.FD_STATUS_REFUSE.equals(mainForm.getFdStatus())
					&&"true".equals(mainForm.getDocIsNewVersion())
					&&Boolean.TRUE.equals(request.getAttribute("budgetAuth"))
					&&StringUtil.isNull(request.getParameter("viewVersion"))){
				editionForm.setEnabledNewVersion("true");
			}
			mainForm.setEditionForm(editionForm);
			List historyOptionList=getServiceImp(request).getHistoryOptionList(mainForm.getFdId());
			if(!ArrayUtil.isEmpty(historyOptionList)) {
				request.setAttribute("historyOptionList", historyOptionList);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}else if(!authFlag){
			return getActionForward("e403", mapping, form, request, response);
		} else {
			if(forward==null){
				return getActionForward("view", mapping, form, request, response);
			}else{
				return forward;
			}
		}
	}
    /**
     * 独立预算编制查看页面
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward independentView(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    				throws Exception {
    	TimeCounter.logCurrentTime("Action-independentView", true, getClass());
    	KmssMessages messages = new KmssMessages();
    	boolean authFlag=true;
    	request.setAttribute("approvalAuth", Boolean.FALSE);  //默认允许审批
    	request.setAttribute("effectAuth", Boolean.FALSE);  //默认允许生效
    	try {
			loadActionForm(mapping, form, request, response);
    		FsscBudgetingMainForm mainForm=(FsscBudgetingMainForm) form;
    		//校验当前人员是否有当前机构的预算编制审核/编制权限
    		Map<String,Boolean> authMap=(Map<String, Boolean>) request.getAttribute("authMap");
    		authFlag=authMap.get("authFlag");
    		SysEditionMainForm editionForm = mainForm.getEditionForm();
    		if(FsscBudgetingConstant.FD_STATUS_REFUSE.equals(mainForm.getFdStatus())
    				&&"true".equals(mainForm.getDocIsNewVersion())
    				&&Boolean.TRUE.equals(request.getAttribute("budgetAuth"))
    				&&StringUtil.isNull(request.getParameter("viewVersion"))){
    			editionForm.setEnabledNewVersion("true");
    		}
    		mainForm.setEditionForm(editionForm);
    		List historyOptionList=getServiceImp(request).getHistoryOptionList(mainForm.getFdId());
			if(!ArrayUtil.isEmpty(historyOptionList)) {
				request.setAttribute("historyOptionList", historyOptionList);
			}
    	} catch (Exception e) {
    		messages.addError(e);
    	}
    	
    	TimeCounter.logCurrentTime("Action-independentView", false, getClass());
    	if (messages.hasError()) {
    		KmssReturnPage.getInstance(request).addMessages(messages)
    		.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
    		return getActionForward("failure", mapping, form, request, response);
    	} else if(!authFlag){
    		return getActionForward("e403", mapping, form, request, response);
    	}else {
    		return getActionForward("independentView", mapping, form, request, response);
    	}
    }
    /**
     * 自上而下非公司/机构查看页面
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward childView(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    				throws Exception {
    	TimeCounter.logCurrentTime("Action-view", true, getClass());
    	KmssMessages messages = new KmssMessages();
    	boolean authFlag=true;
    	request.setAttribute("approvalAuth", Boolean.FALSE);  //默认允许审批
    	request.setAttribute("effectAuth", Boolean.FALSE);  //默认允许生效
    	try {
			form.reset(mapping, request);
    		IExtendForm rtnForm = null;
    		IBaseModel model =null;
    		String id = (String) request.getAttribute("fdId");
    		if(StringUtil.isNull(id)){
    			id=request.getParameter("fdId");
    		}
    		if (!StringUtil.isNull(id)) {
    			model = getServiceImp(request).findByPrimaryKey(id,null, true);
    			if (model != null){
    				FsscBudgetingMain main=(FsscBudgetingMain) model;
    				//校验当前人员是否有当前机构的预算编制审核/编制权限
    				Map<String,Boolean> authMap=getServiceImp(request).getBudgetingViewAuth(request,main.getFdOrgId());
    				request.setAttribute("authMap", authMap);
    				authFlag=authMap.get("authFlag");
    				getServiceImp(request).completeDetail(request,main,"childView",authMap);
    				rtnForm = getServiceImp(request).convertModelToForm(
    						(IExtendForm) form, main, new RequestContext(request));
    			}
    		}
    		if (rtnForm == null) {
                throw new NoRecordException();
            }
    		request.setAttribute(getFormName(rtnForm, request), rtnForm);
    		FsscBudgetingMainForm mainForm=(FsscBudgetingMainForm) form;
    		SysEditionMainForm editionForm = mainForm.getEditionForm();
    		if(FsscBudgetingConstant.FD_STATUS_REFUSE.equals(mainForm.getFdStatus())
    				&&"true".equals(mainForm.getDocIsNewVersion())
    				&&Boolean.TRUE.equals(request.getAttribute("budgetAuth"))
    				&&StringUtil.isNull(request.getParameter("viewVersion"))){
    			editionForm.setEnabledNewVersion("true");
    		}
    		mainForm.setEditionForm(editionForm);
    	} catch (Exception e) {
    		messages.addError(e);
    	}
    	
    	TimeCounter.logCurrentTime("Action-view", false, getClass());
    	if (messages.hasError()) {
    		KmssReturnPage.getInstance(request).addMessages(messages)
    		.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
    		return getActionForward("failure", mapping, form, request, response);
    	}else if(!authFlag){
    		return getActionForward("e403", mapping, form, request, response);
    	} else {
    		return getActionForward("childView", mapping, form, request, response);
    	}
    }
    
    /**
	 * 根据http请求，获取model，将model转化为form并返回。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 若获取model不成功，则抛出errors.norecord的错误信息。
	 * 
	 * @param form
	 * @param request
	 * @return form对象
	 * @throws Exception
	 */
	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		IBaseModel model =null;
		String id = (String) request.getAttribute("fdId");
		if(StringUtil.isNull(id)){
			id=request.getParameter("fdId");
		}
		if (!StringUtil.isNull(id)) {
			model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null){
				FsscBudgetingMain main=(FsscBudgetingMain) model;
				Map<String,Boolean> authMap=getServiceImp(request).getBudgetingViewAuth(request,main.getFdOrgId());
				request.setAttribute("authMap", authMap);
				getServiceImp(request).completeDetail(request,main,"view",authMap);
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, main, new RequestContext(request));
				
			}
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}
    /**
	 * 将浏览器提交的表单数据添加到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，只显示返回按钮，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			super.save(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
			return getActionForward("success", mapping, form, request, response);
		}
	}
	/**
	 * 将浏览器提交的表单数据添加到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，只显示返回按钮，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			super.update(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	/**
	 * 生成新版本
	 * 
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward newEdition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdBudgetingType=EopBasedataFsscUtil.getSwitchValue("fdBudgetingType");//获取预算编制方式
		String fdOrgType="";//机构类型
		String fdOrgId=""; //机构ID
		try {
			String fdMainId = request.getParameter("originId");
			if (StringUtil.isNull(fdMainId)) {
                throw new NoRecordException();
            }
			FsscBudgetingMainForm mainForm = (FsscBudgetingMainForm) form;
			FsscBudgetingMain main = (FsscBudgetingMain) getServiceImp(request)
					.findByPrimaryKey(fdMainId);
			fdOrgType=main.getFdOrgType();
			fdOrgId=main.getFdOrgId();
			mainForm = (FsscBudgetingMainForm) getServiceImp(request)
					.cloneModelToForm(mainForm, main,
							new RequestContext(request));
			Map<String,Boolean> authMap=getServiceImp(request).getBudgetingViewAuth(request, fdOrgId);
			String version=request.getParameter("version");
			if(StringUtil.isNotNull(version)){
				String[] versionArr=version.split("\\.");
				SysEditionMainForm editionForm = mainForm.getEditionForm();
				editionForm.setMainVersion(versionArr[0]);
				editionForm.setAuxiVersion(versionArr[1]);
				editionForm.setIsNewVersion("true");
				editionForm.setEnabledNewVersion("true");
				mainForm.setEditionForm(editionForm);
			}
			getServiceImp(request).createNewEdition(request,main,authMap);
			mainForm.setFdChildTotalMoney(String.valueOf(main.getFdChildTotalMoney()));
			mainForm.setMethod("add");
			mainForm.setMethod_GET("add");
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			String forward="";
			if(FsscBudgetingConstant.FD_BUDTING_TYPE_DOWN.equals(fdBudgetingType)){
				if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(fdOrgType)){//记账公司
					forward="initEdit";
				}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(fdOrgType)){//成本中心
					forward="childEdit";
				}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)){//组织结构
					SysOrgElement org=(SysOrgElement) getServiceImp(request).findByPrimaryKey(fdOrgId, SysOrgElement.class, true);
					if(org.getFdOrgType()==1){//机构
						forward="initEdit";
					}else{//部门
						forward="childEdit";
					}
				}
			}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_INDEPENDENT.equals(fdBudgetingType)){
				forward="independentEdit";
			}else{
				forward="edit";
			}
			return mapping.findForward(forward);
		}
	}
	/**
	 * 通过/驳回预算：1、勾选部分，点击驳回，则勾选的被驳回，未勾选的被审核通过；2、勾选部分，点击通过，则勾选的被通过，未勾选的被驳回；
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approvalDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-approvalDoc", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).updateApprovalDoc(request);

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-approvalDoc", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
	
	/**
	 * 预算生效
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward effectDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeCounter.logCurrentTime("Action-effectDoc", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).addBudgeting(request);
		} catch (Exception e) {
			messages.addError(e);
		}
		
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-effectDoc", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
	
	/**
	 * 将目前期间的预算编制置为废弃
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	public ActionForward updateBudgetingStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).updateBudgetingStatus();
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

}
