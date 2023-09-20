package com.landray.kmss.fssc.fee.actions;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.query.Query;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysAuthConstant.AuthCheck;
import com.landray.kmss.constant.SysAuthConstant.CheckType;
import com.landray.kmss.eop.basedata.imp.EopBasedataImportUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataStandardService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetMatchService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonExpenseService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonPaymentService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.common.util.PdaFlagUtil;
import com.landray.kmss.fssc.fee.forms.FsscFeeMainForm;
import com.landray.kmss.fssc.fee.model.FsscFeeMain;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.fssc.fee.service.IFsscFeeLedgerService;
import com.landray.kmss.fssc.fee.service.IFsscFeeMainService;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscFeeMainAction extends ExtendAction {
	
	private IFsscCommonExpenseService fsscExpenseCommonService;

    public IFsscCommonExpenseService getFsscExpenseCommonService() {
    	if(fsscExpenseCommonService==null){
    		fsscExpenseCommonService = (IFsscCommonExpenseService) getBean("fsscExpenseCommonService");
    	}
		return fsscExpenseCommonService;
	}

	private IFsscFeeMainService fsscFeeMainService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscFeeMainService == null) {
            fsscFeeMainService = (IFsscFeeMainService) getBean("fsscFeeMainService");
        }
        return fsscFeeMainService;
    }
    
    private IFsscCommonBudgetMatchService fsscCommonBudgetService;

	public IFsscCommonBudgetMatchService getFsscCommonBudgetService() {
		if (fsscCommonBudgetService == null) {
			fsscCommonBudgetService = (IFsscCommonBudgetMatchService) getBean("fsscBudgetMatchService");
        }
		return fsscCommonBudgetService;
	}
	
    private IEopBasedataStandardService eopBasedataStandardService;

    public IEopBasedataStandardService getEopBasedataStandardService() {
    	if (eopBasedataStandardService == null) {
    		eopBasedataStandardService = (IEopBasedataStandardService) getBean("eopBasedataStandardService");
        }
		return eopBasedataStandardService;
	}
    
    private IEopBasedataCompanyService eopBasedataCompanyService;

    public IEopBasedataCompanyService getEopBasedataCompanyService() {
    	if (eopBasedataCompanyService == null) {
    		eopBasedataCompanyService = (IEopBasedataCompanyService) getBean("eopBasedataCompanyService");
        }
		return eopBasedataCompanyService;
	}
    
    private IFsscFeeLedgerService fsscFeeLedgerService;

    public IFsscFeeLedgerService getFsscFeeLedgerService() {
    	if (fsscFeeLedgerService == null) {
    		fsscFeeLedgerService = (IFsscFeeLedgerService) getBean("fsscFeeLedgerService");
        }
		return fsscFeeLedgerService;
	}
    
    private IFsscCommonPaymentService fsscPaymentCommonService;

    public IFsscCommonPaymentService getFsscPaymentCommonService() {
    	if(fsscPaymentCommonService==null){
    		fsscPaymentCommonService = (IFsscCommonPaymentService) getBean("fsscCommonPaymentService");
    	}
		return fsscPaymentCommonService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscFeeMain.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscFeeMainForm fsscFeeMainForm = (FsscFeeMainForm) super.createNewForm(mapping, form, request, response);
        ((IFsscFeeMainService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        String docTemplateId = request.getParameter("i.docTemplate");
        FsscFeeTemplate temp = (FsscFeeTemplate) getServiceImp(request).findByPrimaryKey(docTemplateId, FsscFeeTemplate.class, true);
        String docTemplateName =temp.getFdName();
        request.setAttribute("docTemplate", temp);
        SysCategoryMain cate=temp.getDocCategory();
        docTemplateName=cate.getFdName()+ "  >  " + docTemplateName;
		while (cate.getFdParent()!= null) {
			cate =(SysCategoryMain)cate.getFdParent();
			docTemplateName=cate.getFdName()+ "  >  " + docTemplateName;
		}
		request.setAttribute("docTemplateName", docTemplateName);
        return fsscFeeMainForm;
    }
    
    @Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		String fdId = request.getParameter("fdId");
		FsscFeeMain main = (FsscFeeMain) getServiceImp(request).findByPrimaryKey(fdId, null, true);
		FsscFeeTemplate temp = main.getDocTemplate();
		String docTemplateName = temp.getFdName();
		SysCategoryMain cate=temp.getDocCategory();
	    docTemplateName=cate.getFdName()+ "  >  " + docTemplateName;
		while (cate.getFdParent()!= null) {
			cate =(SysCategoryMain)cate.getFdParent();
			docTemplateName=cate.getFdName()+ "  >  " + docTemplateName;
		}
		request.setAttribute("docTemplateName", docTemplateName);
		request.setAttribute("docTemplate", main.getDocTemplate());
	}

    /**
	 * 预算匹配
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward matchBudget(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject rtn = new JSONObject();
		String param = request.getParameter("data");
		rtn.put("result", "success");
		try {
			if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
				JSONObject data = JSONObject.fromObject(param);
				JSONObject budget = getFsscCommonBudgetService().matchFsscBudget(data);
				EopBasedataCompany comp = (EopBasedataCompany) getServiceImp(request).findByPrimaryKey(data.getString("fdCompanyId"), EopBasedataCompany.class, true);
				String hql = "select rate from com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate rate left join rate.fdCompanyList comp where rate.fdIsAvailable=:fdIsAvailable and (comp.fdId=:fdCompanyId or comp.fdId is null) and rate.fdSourceCurrency.fdId=:fdSourceCurrencyId and rate.fdTargetCurrency.fdId=:fdTargetCurrencyId";
				Query query = getServiceImp(request).getBaseDao().getHibernateSession().createQuery(hql);
				String fdCompanyId= data.getString("fdCompanyId");
				query.setParameter("fdCompanyId",fdCompanyId);
				query.setParameter("fdIsAvailable", true);
				String fdCurrencyId=null;
				if(!data.has("fdCurrencyId")){
					EopBasedataCompany company=(EopBasedataCompany)getEopBasedataCompanyService().findByPrimaryKey(fdCompanyId);
					fdCurrencyId=company.getFdBudgetCurrency().getFdId();
				}else{
					fdCurrencyId=data.getString("fdCurrencyId");
				}
				query.setParameter("fdSourceCurrencyId",fdCurrencyId);
				query.setParameter("fdTargetCurrencyId", comp.getFdBudgetCurrency().getFdId());
				List<EopBasedataExchangeRate> rates = query.list();
				if(ArrayUtil.isEmpty(rates)){
					//如果没有查到相应汇率，但匹配到了预算，提示用户不能申请
					if("2".equals(budget.get("result"))&&budget.getJSONArray("data").size()>0){
						rtn.put("result", "failure");
						rtn.put("message", ResourceUtil.getString("tips.exchangeRateNotExist","fssc-fee"));
					}else{
						rtn.put("budget", budget);
						rtn.put("fdBudgetRate", "0");
					}
				}else{
					rtn.put("budget", budget);
					rtn.put("fdBudgetRate", rates.get(0).getFdRate());
				}
			}else{
				rtn.put("budget", new JSONObject());
			}
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", ResourceUtil.getString("errors.unknown")+"<br>"+e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
    
    /**
	 * 查找预算
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForm getBudgetData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String json = request.getParameter("data");
		try {
			JSONArray param = JSONArray.fromObject(json);
			JSONArray budgets = new JSONArray();
			if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
				for(int i=0;i<param.size();i++){
					JSONObject row = param.getJSONObject(i);
					String fdDetailId = row.getString("index");
					JSONObject budget = getFsscCommonBudgetService().matchFsscBudget(row);
					Boolean fdIsNeedBudget=((IFsscFeeMainService) getServiceImp(request)).getIsNeedBudgetByItem(row);
					row.clear();
					row.put("fdDetailId", fdDetailId);
					row.put("budget", budget);
					row.put("fdIsNeedBudget", fdIsNeedBudget);
					budgets.add(row);
				}
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(budgets.toString());
		} catch (Exception e) {
			response.getWriter().write("");
			e.printStackTrace();
		}
        return null;
    }
	
	/**
	 * 匹配标准
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStandardData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject rtn = new JSONObject();
		String params = request.getParameter("params");
		rtn.put("result", "success");
		JSONArray object = new JSONArray();
		try {
			JSONArray data = JSONArray.fromObject(params);
			for(int i=0;i<data.size();i++){
				JSONObject obj = getEopBasedataStandardService().getStandardData(data.getJSONObject(i));
				obj.put("index", data.getJSONObject(i).get("index"));
				object.add(obj);
			}
			rtn.put("data", object);
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", ResourceUtil.getString("errors.unknown")+"<br>"+e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
	/**
	 * 校验是否可以转报销
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkToExpense(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject rtn = new JSONObject();
		String fdId = request.getParameter("fdId");
		FsscFeeMain main = (FsscFeeMain) getServiceImp(request).findByPrimaryKey(fdId, null, true);
		FsscFeeTemplate temp = main.getDocTemplate();
		rtn.put("result", "success");
		Boolean isCloseFlag = false;
		try {
			if(FsscCommonUtil.checkHasModule("/fssc/expense/")) {
				isCloseFlag=getFsscExpenseCommonService().getExpenseCloseFlag(fdId);
			}
			if(FsscCommonUtil.checkHasModule("/fssc/payment/")) {
				isCloseFlag = getFsscPaymentCommonService().getPaymentCloseFlag(fdId);
			}
			if(isCloseFlag){
				rtn.put("message", ResourceUtil.getString("tips.toExpense.closed","fssc-fee"));
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(rtn.toString());
				return null;
			}
			List<Object> temList = getFsscExpenseCommonService().getExpenseCategoryId(temp.getFdId());
			if(ArrayUtil.isEmpty(temList)){
				//报销分类里没有开启关联事前申请
			    rtn.put("message", rtn.containsKey("message")?rtn.getString("message")+";":""+ResourceUtil.getString("tips.toExpense.noTemplate","fssc-fee"));
			}else if(temList.size()==1&&!"noSelect".equals(temList.get(0))){
				rtn.put("docTemplateId",temList.get(0).toString());
				rtn.put("message", "connectOne");
			}else if(temList.size()>1){
				//报销分类中配置多个, 获取关联了此事前的报销分类
				rtn.put("docTemplateId",temList);
				rtn.put("message", "connectMore");
			}else{
				//报销分类中未选择关联事前申请模板,获取所有报销分类里开启关联事前申请的分类
				rtn.put("message", "noSelect");
			}
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", ResourceUtil.getString("errors.unknown")+"<br>"+e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
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
		if(!EopBasedataFsscUtil.checkStatus(ids,FsscFeeMain.class.getName(),"docStatus","20;30")){
			messages.addError(new KmssMessage(ResourceUtil.getString("fssc.common.examine.or.publish.delete.tips", "fssc-common")));	
		}
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
	
	/**
	 * 校验是否可以关闭申请
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkCloseFee(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject rtn = new JSONObject();
		String fdId = request.getParameter("fdId");
		rtn.put("result", "success");
		try {
			Boolean result = ((IFsscFeeMainService)getServiceImp(request)).checkCanCloseFee(fdId);
			rtn.put("result", result?"success":"failure");
			if(!result){
				rtn.put("message", ResourceUtil.getString("tips.closeFee.error","fssc-fee"));
			}
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", ResourceUtil.getString("errors.unknown")+"<br>"+e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
	/**
	 * 关闭申请
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward closeFee(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject rtn = new JSONObject();
		String fdId = request.getParameter("fdId");
		rtn.put("result", "success");
		try {
			((IFsscFeeMainService)getServiceImp(request)).updateCloseFee(fdId);
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", ResourceUtil.getString("errors.unknown")+"<br>"+e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
	
	/**
	 * 移动端显示流程
	 * 
	 */
	public ActionForward viewLbpm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		return getActionForward("viewFlow", mapping, form, request, response);
	}
	
	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
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
			if(PdaFlagUtil.checkIsPdaLogin(request)){
				return new ActionForward("/fssc/fee/fssc_fee_mobile/fsscFeeMobile.do?method=data",true);
			}else{
				KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
				return getActionForward("success", mapping, form, request, response);
			}
		}
	}
	
	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			super.update(mapping, form, request, response);
			
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-view", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            loadActionForm(mapping, form, request, response);
        } catch (Exception e) {
            messages.addError(e);
        }
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        }
        String fdId = request.getParameter("fdId");
        FsscFeeMainForm fsscFeeMainForm = (FsscFeeMainForm) form;
        FsscFeeMain fsscFeeMain = (FsscFeeMain) getServiceImp(request).findByPrimaryKey(fdId);
        Boolean enable = ((ISysPrintMainCoreService) getBean("sysPrintMainCoreService")).isEnablePrintTemplate(fsscFeeMain.getDocTemplate(), null, request);
        ((ISysPrintMainCoreService) getBean("sysPrintMainCoreService")).initPrintData(fsscFeeMain, fsscFeeMainForm, request);
        String printPage = request.getParameter("printPage");
        if (StringUtil.isNotNull(printPage)) {
            return mapping.findForward(printPage);
        }
        if (enable) {
            return mapping.findForward("sysprint");
        } else {
            KmssMessages noSelect = new KmssMessages();
            noSelect.addMsg(new KmssMessage("fssc-fee:printMechanism.page.noSelect"));
            KmssReturnPage.getInstance(request).addMessages(noSelect).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
    }
	/**
	 * 台账查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listLedger(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		HQLInfo hqlInfo = getLedgerHql(request);
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setPageNo(pageno);
			if(UserUtil.checkRole("ROLE_FSSCFEE_ACCOUNT")) {
				hqlInfo.setCheckParam(CheckType.AuthCheck, AuthCheck.SYS_NONE);
			}else {
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "fsscFeeLedger.fdModelId in(select fdId from com.landray.kmss.fssc.fee.model.FsscFeeMain where docCreator.fdId=:userId)"));
				hqlInfo.setParameter("userId",UserUtil.getUser().getFdId());
			}
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
			request.setAttribute("dataMap", getMoneyData(page.getList()));
		}catch (Exception e) {
			messages.addError(e);
		}
		if(messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
            return getActionForward("failure", mapping, form, request, response);
		}
		return getActionForward("listLedger", mapping, form, request, response);
	}
	
	private Map<String,String> getMoneyData(List<Object[]> datas) throws Exception{
		if(datas.size()==0) {
			return new HashMap<String,String>();
		}
		List<String> ids = new ArrayList<String>();
		for(Object[] data:datas) {
			ids.add((String) data[8]);
		}
		String sql = "select fdLedgerId as lid, sum( case when fdType = '1' then fdBudgetMoney end) as tm, sum( case when fdType = '2' then fdBudgetMoney end ) as um,"; 
		sql+="sum( case when fdType = '3' then fdBudgetMoney end) as um1 from FsscFeeLedger where ";
		sql += HQLUtil.buildLogicIN("fdLedgerId", ids);
		sql+=" GROUP BY fdLedgerId";
		Query query = getEopBasedataCompanyService().getBaseDao().getHibernateSession().createQuery(sql);
		List<Object[]> rs = query.list();
		Map<String,String> map = new HashMap<String,String>();
		for(Object[] data:rs) {
			String fdLedgerId = (String) data[0];
			if(StringUtil.isNull(fdLedgerId)) {
				continue;
			}
			Double total = (Double)data[1];
			total = total==null?0d:total;
			Double using = (Double)data[2];
			using = using==null?0d:using;
			Double used = (Double)data[3];
			used = used==null?0d:used;
			Double usable = FsscNumberUtil.getSubtraction(total, used);
			usable = FsscNumberUtil.getSubtraction(usable, using,2);
			DecimalFormat df = new DecimalFormat("0.00");
			map.put(fdLedgerId+"fdTotalMoney", df.format(total));
			map.put(fdLedgerId+"fdUsingMoney", df.format(using));
			map.put(fdLedgerId+"fdUsedMoney", df.format(used));
			map.put(fdLedgerId+"fdUsableMoney", df.format(usable));
		}
		return map;
	}

	private HQLInfo getLedgerHql(HttpServletRequest request) {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		hqlInfo.setSelectBlock("fsscFeeMain.docSubject,fsscFeeMain.docNumber,fsscFeeLedger.fdCompanyName,fsscFeeLedger.fdCostCenterGroupName,fsscFeeLedger.fdCostCenterName,fsscFeeLedger.fdExpenseItemName,fsscFeeLedger.fdProjectName,fsscFeeLedger.fdModelId,fsscFeeLedger.fdId");
		hqlInfo.setFromBlock("com.landray.kmss.fssc.fee.model.FsscFeeLedger fsscFeeLedger");
		StringBuilder hql = new StringBuilder();
		hqlInfo.setJoinBlock(",com.landray.kmss.fssc.fee.model.FsscFeeMain fsscFeeMain");
		hql = new StringBuilder();
		hql.append("fsscFeeLedger.fdId=fsscFeeLedger.fdLedgerId and fsscFeeLedger.fdModelId=fsscFeeMain.fdId ");
		String fdCompanyId = request.getParameter("fdCompanyId");
		if(StringUtil.isNotNull(fdCompanyId)) {
			hql.append(" and fsscFeeLedger.fdCompanyId =:fdCompanyId");
			hqlInfo.setParameter("fdCompanyId",fdCompanyId);
		}
		String fdCostCenterId = request.getParameter("fdCostCenterId");
		if(StringUtil.isNotNull(fdCostCenterId)) {
			hql.append(" and fsscFeeLedger.fdCostCenterId =:fdCostCenterId");
			hqlInfo.setParameter("fdCostCenterId",fdCostCenterId);
		}
		String fdCostCenterGroupId = request.getParameter("fdCostCenterGroupId");
		if(StringUtil.isNotNull(fdCostCenterGroupId)) {
			hql.append(" and fsscFeeLedger.fdCostCenterGroupId =:fdCostCenterGroupId");
			hqlInfo.setParameter("fdCostCenterGroupId",fdCostCenterGroupId);
		}
		String fdProjectId = request.getParameter("fdProjectId");
		if(StringUtil.isNotNull(fdProjectId)) {
			hql.append(" and fsscFeeLedger.fdProjectId =:fdProjectId");
			hqlInfo.setParameter("fdProjectId",fdProjectId);
		}
		String fdExpenseItemId = request.getParameter("fdExpenseItemId");
		if(StringUtil.isNotNull(fdExpenseItemId)) {
			hql.append(" and fsscFeeLedger.fdExpenseItemId =:fdExpenseItemId");
			hqlInfo.setParameter("fdExpenseItemId",fdExpenseItemId);
		}
		String docCreatorId = request.getParameter("docCreatorId");
		if(StringUtil.isNotNull(docCreatorId)) {
			hql.append(" and fsscFeeMain.docCreator.fdId =:docCreatorId");
			hqlInfo.setParameter("docCreatorId",docCreatorId);
		}
		String docCreateTime1 = request.getParameter("docCreateTime1");
		if(StringUtil.isNotNull(docCreateTime1)) {
			hql.append(" and fsscFeeMain.docCreateTime >:docCreateTime1");
			hqlInfo.setParameter("docCreateTime1",DateUtil.convertStringToDate(docCreateTime1,DateUtil.PATTERN_DATE));
		}
		String docCreateTime2 = request.getParameter("docCreateTime2");
		if(StringUtil.isNotNull(docCreateTime2)) {
			hql.append(" and fsscFeeMain.docCreateTime <:docCreateTime2");
			hqlInfo.setParameter("docCreateTime2",DateUtil.convertStringToDate(docCreateTime2,DateUtil.PATTERN_DATE));
		}
		hqlInfo.setWhereBlock(hql.toString());
		return hqlInfo;
	}
	
	/**
	 * 台账导出
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportLedger(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		HQLInfo hqlInfo = getLedgerHql(request);
		if(UserUtil.checkRole("ROLE_FSSCFEE_ACCOUNT")) {
			hqlInfo.setCheckParam(CheckType.AuthCheck, AuthCheck.SYS_NONE);
		}else {
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "fsscFeeLedger.fdModelId in(select fdId from com.landray.kmss.fssc.fee.model.FsscFeeMain where docCreator.fdId=:userId)"));
			hqlInfo.setParameter("userId",UserUtil.getUser().getFdId());
		}
		try {
			List<Object[]> datas = getFsscFeeLedgerService().findList(hqlInfo);
			String filename = ResourceUtil.getString("py.listLedger","fssc-fee");
			filename = new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls";
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setContentType("application/vnd.ms-excel; charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename);
			Workbook workBook = new HSSFWorkbook();
			String sheetName = new String(filename.getBytes("ISO8859-1"), "GBK");
			String[] cols = ResourceUtil.getString("py.ShiQianTaiZhang.columns","fssc-fee").split(";");
			Sheet sheet = workBook.createSheet(sheetName);
			for (int i = 0; i <= cols.length; i++) {
				sheet.setColumnWidth((short) i, (short) 4000);
			}
			CellStyle style = EopBasedataImportUtil.getTitleStyle(workBook);
			Row row = sheet.createRow(0);
			Cell cell = null;
			for (int i = 0; i < cols.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(cols[i]);
				cell.setCellStyle(style);
			}
			Map<String,String> dataMap = getMoneyData(datas);
			CellStyle content = EopBasedataImportUtil.getNormalStyle(workBook);
			int k=1;
			for(Object[] data:datas) {
				row = sheet.createRow(k++);
				cell = row.createCell(0);
				cell.setCellValue(k-1);
				cell.setCellStyle(content);
				for (int i = 1; i <= data.length; i++) {
					String value = data[i-1]==null?"":data[i-1].toString();
					cell = row.createCell(i);
					cell.setCellValue(value);
					cell.setCellStyle(content);
				}
				String fdLedgerId = (String) data[8];
				cell = row.createCell(8);
				cell.setCellValue(dataMap.get(fdLedgerId+"fdTotalMoney"));
				cell.setCellStyle(content);
				
				cell = row.createCell(9);
				cell.setCellValue(dataMap.get(fdLedgerId+"fdUsedMoney"));
				cell.setCellStyle(content);
				
				cell = row.createCell(10);
				cell.setCellValue(dataMap.get(fdLedgerId+"fdUsingMoney"));
				cell.setCellStyle(content);
				
				cell = row.createCell(11);
				cell.setCellValue(dataMap.get(fdLedgerId+"fdUsableMoney"));
				cell.setCellStyle(content);
			}
			workBook.write(os);
			os.flush();
			os.close();
		}catch (Exception e) {
			messages.addError(e);
		}
		if(messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
            return getActionForward("failure", mapping, form, request, response);
		}
		return null;
	}
}
