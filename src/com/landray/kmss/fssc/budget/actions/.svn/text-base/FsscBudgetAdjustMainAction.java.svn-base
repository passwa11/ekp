package com.landray.kmss.fssc.budget.actions;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budget.forms.FsscBudgetAdjustMainForm;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain;
import com.landray.kmss.fssc.budget.service.IFsscBudgetAdjustMainService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FsscBudgetAdjustMainAction extends ExtendAction {

    private IFsscBudgetAdjustMainService fsscBudgetAdjustMainService;

    @Override
    public IFsscBudgetAdjustMainService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetAdjustMainService == null) {
            fsscBudgetAdjustMainService = (IFsscBudgetAdjustMainService) getBean("fsscBudgetAdjustMainService");
        }
        return fsscBudgetAdjustMainService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String whereBlock=hqlInfo.getWhereBlock();
    	String fdSchemeId=request.getParameter("fdSchemeId");
    	if(StringUtil.isNotNull(fdSchemeId)){
    		whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscBudgetAdjustMain.fdBudgetScheme.fdId=:fdSchemeId");
    		hqlInfo.setParameter("fdSchemeId", fdSchemeId);
    	}
    	String fdCompanyName = request.getParameter("q.fdCompanyName");
        if(StringUtil.isNotNull(fdCompanyName)){
        	whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscBudgetAdjustMain.fdCompany.fdName like :fdCompanyName");
        	hqlInfo.setParameter("fdCompanyName","%"+fdCompanyName+"%");
        }
    	hqlInfo.setWhereBlock(whereBlock);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetAdjustMain.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscBudgetAdjustMainForm fsscBudgetAdjustMainForm = (FsscBudgetAdjustMainForm) super.createNewForm(mapping, form, request, response);
        ((IFsscBudgetAdjustMainService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscBudgetAdjustMainForm;
    }
    
    @Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			FsscBudgetAdjustMain main = (FsscBudgetAdjustMain) getServiceImp(request).findByPrimaryKey(id,
					null, true);
			request.setAttribute("docTemplate", main.getDocTemplate());
			request.setAttribute("adjustType", main.getDocTemplate().getFdAdjustType());
			String fdBudgetSchemeId=main.getFdBudgetScheme().getFdId();
			EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) getServiceImp(request).findByPrimaryKey(fdBudgetSchemeId,EopBasedataBudgetScheme.class, true);
			if(scheme!=null){
				request.setAttribute("dimensions", scheme.getFdDimension());
				String fdPeriod=scheme.getFdPeriod();
	        	String fdSchemePeriod="";
	        	if(FsscCommonUtil.isContain(fdPeriod, "4;", ";")) {//包含月度
	        		fdSchemePeriod="1";
	        	}else if(FsscCommonUtil.isContain(fdPeriod, "3;", ";")) {//包含季度
	        		fdSchemePeriod="3";
	        	}else if(FsscCommonUtil.isContain(fdPeriod, "2;", ";")) {//包含年度
	        		fdSchemePeriod="5";
	        	}
	        	request.setAttribute("fdSchemePeriod", fdSchemePeriod);
			}
		}
	}
    
    /**
	 * 预算调整校验当前行借出金额是否足够
	 */
	public ActionForward checkLendMoney(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = getServiceImp(request).checkLendMoney(request);
		if(UserOperHelper.allowLogOper("checkLendMoney", FsscBudgetAdjustMain.class.getName())){
			UserOperContentHelper.putFind(JSON.parseObject(json.toString()));
		}
		response.setCharacterEncoding("UTF-8");
		if(!json.isEmpty()){
			response.getWriter().write(json.toString());
		}else{
			response.getWriter().write("");
		}
		return null;
	}
	/**
	 * 预算调整/追加校验当前行借入成本中心对应预算是否存在
	 */
	public ActionForward checkBorrowMoney(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject result = getServiceImp(request).checkBorrowMoney(request);
		if(UserOperHelper.allowLogOper("checkLendMoney", FsscBudgetAdjustMain.class.getName())){
			String params=request.getParameter("hashMapArray");
			if(StringUtil.isNotNull(params)){
				UserOperContentHelper.putFind(JSONObject.fromObject(params));
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(String.valueOf(result));
		return null;
	}
	/**
	 * 查看页面预算调整，校验调减金额不能多余可使用额，防止预算调为负数
	 */
	public ActionForward checkAdjust(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean result = getServiceImp(request).checkAdjust(request);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(String.valueOf(result));
		return null;
	}
	
	/**
	 * 校验是否选中待审、发布单据
	 */
	public ActionForward checkDeleteAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		if(!EopBasedataFsscUtil.checkStatus(ids,FsscBudgetAdjustMain.class.getName(),"docStatus","20;30")){
			messages.addError(new KmssMessage(ResourceUtil.getString("fssc.common.examine.or.publish.delete.tips", "fssc-common")));	
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}
}
