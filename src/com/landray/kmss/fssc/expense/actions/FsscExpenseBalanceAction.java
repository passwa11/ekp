package com.landray.kmss.fssc.expense.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetMatchService;
import com.landray.kmss.fssc.common.util.FsscCommonProcessUtil;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.expense.forms.FsscExpenseBalanceForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalance;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory;
import com.landray.kmss.fssc.expense.service.IFsscExpenseBalanceService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscExpenseBalanceAction extends ExtendAction {

    private IFsscExpenseBalanceService fsscExpenseBalanceService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscExpenseBalanceService == null) {
            fsscExpenseBalanceService = (IFsscExpenseBalanceService) getBean("fsscExpenseBalanceService");
        }
        return fsscExpenseBalanceService;
    }

	private IFsscCommonBudgetMatchService fsscBudgetMatchService;

	public IFsscCommonBudgetMatchService getFsscBudgetMatchService() {
		if (fsscBudgetMatchService == null) {
			fsscBudgetMatchService = (IFsscCommonBudgetMatchService) SpringBeanUtil.getBean("fsscBudgetMatchService");
		}
		return fsscBudgetMatchService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscExpenseBalance.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		FsscCommonProcessUtil.buildLbpmHanderHql(hqlInfo,request,"fsscExpenseBalance");
		FsscCommonUtil.getCommonWhereBlock(request,hqlInfo,"fsscExpenseBalance.fdCompany.fdName","fdCompanyName","like"," and ");		
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscExpenseBalanceForm fsscExpenseBalanceForm = (FsscExpenseBalanceForm) super.createNewForm(mapping, form, request, response);
        ((IFsscExpenseBalanceService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        String docTemplateName = fsscExpenseBalanceForm.getDocTemplateName();
        FsscExpenseBalanceCategory tem = (FsscExpenseBalanceCategory) getServiceImp(request).findByPrimaryKey(fsscExpenseBalanceForm.getDocTemplateId(),FsscExpenseBalanceCategory.class,true);
		while (tem.getFdParent()!= null) {
			tem=(FsscExpenseBalanceCategory)tem.getFdParent();
			docTemplateName = tem.getFdName() + "  >  " + docTemplateName;
		}
		request.setAttribute("docTemplateName", docTemplateName);
        return fsscExpenseBalanceForm;
    }
    
    @Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		String fdId = request.getParameter("fdId");
		FsscExpenseBalance main = (FsscExpenseBalance) getServiceImp(request).findByPrimaryKey(fdId, null, true);
		FsscExpenseBalanceCategory tem = main.getDocTemplate();
		String docTemplateName = tem.getFdName();
		while (tem.getFdParent() != null) {
			tem = (FsscExpenseBalanceCategory) tem.getFdParent();
			docTemplateName = tem.getFdName() + "  >  " + docTemplateName;
		}
		request.setAttribute("docTemplateName", docTemplateName);
		request.setAttribute("docTemplate", main.getDocTemplate());
	}

	public ActionForward getBudgetData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data");
		JSONObject data1=new JSONObject();
		JSONArray data0 = JSONArray.fromObject(data);
		if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
			for(int i=0,j=data0.size();i<j;i++){
				JSONObject obj = (JSONObject) data0.get(i);
				String index = obj.getString("index");
				JSONObject rtn = getFsscBudgetMatchService().matchFsscBudget(obj);
				if("2".equals(rtn.get("result"))){
					data1.put(index, rtn.get("data"));
				}else{
					data1.put(index, new JSONArray());
				}
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(data1.toString());
		return null;
	}
	
	/**
	 * 打印单据。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回print页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			FsscExpenseBalanceForm rtnForm = null;
			String id = request.getParameter("fdId");
			if (!StringUtil.isNull(id)) {
				FsscExpenseBalance model = (FsscExpenseBalance) getServiceImp(request)
						.findByPrimaryKey(id, null, true);
				if (model != null) {
					rtnForm = (FsscExpenseBalanceForm) getServiceImp(request)
							.convertModelToForm((IExtendForm) form, model,
									new RequestContext(request));
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("print", mapping, form, request,
					response);
		}
	}
	
	/**
	 * 校验是否选中待审、发布单据
	 */
	public ActionForward checkDeleteAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		if(!EopBasedataFsscUtil.checkStatus(ids,FsscExpenseBalance.class.getName(),"docStatus","20;30")){
			messages.addError(new KmssMessage(ResourceUtil.getString("fssc.common.examine.or.publish.delete.tips", "fssc-common")));	
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}
    
}
