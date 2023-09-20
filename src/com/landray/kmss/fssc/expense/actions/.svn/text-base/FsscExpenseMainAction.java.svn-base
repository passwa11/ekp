package com.landray.kmss.fssc.expense.actions;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.model.EopBasedataPayment;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetItemService;
import com.landray.kmss.eop.basedata.service.IEopBasedataItemBudgetService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPaymentDataService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPaymentService;
import com.landray.kmss.eop.basedata.service.IEopBasedataStandardService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBaiwangService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetMatchService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonCashierPaymentService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonCreditService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonFeeService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLoanService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonNuoService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonPresService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProappService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProvisionService;
import com.landray.kmss.fssc.common.util.FsscCommonProcessUtil;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.common.util.PdaFlagUtil;
import com.landray.kmss.fssc.expense.constant.FsscExpenseConstant;
import com.landray.kmss.fssc.expense.forms.FsscExpenseMainForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseAccounts;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.model.FsscExpenseOffsetLoan;
import com.landray.kmss.fssc.expense.service.IFsscExpenseDetailService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.hr.function.HrFunctions;
import com.landray.kmss.sys.organization.dao.ISysOrgElementDao;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscExpenseMainAction extends ExtendAction {
	private IFsscCommonProappService fsscCommonProappService;
	public IFsscCommonProappService getFsscCommonProappService() {
		if (fsscCommonProappService == null) {
			fsscCommonProappService = (IFsscCommonProappService) getBean("fsscProappCommonService");
        }
		return fsscCommonProappService;
	}
	private IFsscCommonFeeService fsscCommonFeeService;

	public IFsscCommonFeeService getFsscCommonFeeService() {
		if (fsscCommonFeeService == null) {
			fsscCommonFeeService = (IFsscCommonFeeService) getBean("fsscCommonFeeService");
		}
		return fsscCommonFeeService;
	}

	private IFsscCommonLoanService fsscCommonLoanService;

	public IFsscCommonLoanService getFsscCommonLoanService() {
		if (fsscCommonLoanService == null) {
			fsscCommonLoanService = (IFsscCommonLoanService) getBean("fsscCommonLoanService");
		}
		return fsscCommonLoanService;
	}

	private IFsscCommonBudgetMatchService fsscCommonBudgetService;

	public IFsscCommonBudgetMatchService getFsscCommonBudgetService() {
		if (fsscCommonBudgetService == null) {
			fsscCommonBudgetService = (IFsscCommonBudgetMatchService) getBean("fsscBudgetMatchService");
		}
		return fsscCommonBudgetService;
	}

	private IEopBasedataItemBudgetService eopBasedataItemBudgetService;

	public IEopBasedataItemBudgetService getEopBasedataItemBudgetService() {
		if (eopBasedataItemBudgetService == null) {
			eopBasedataItemBudgetService = (IEopBasedataItemBudgetService) getBean("eopBasedataItemBudgetService");
		}
		return eopBasedataItemBudgetService;
	}

	private IFsscExpenseMainService fsscExpenseMainService;

	@Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (fsscExpenseMainService == null) {
			fsscExpenseMainService = (IFsscExpenseMainService) getBean("fsscExpenseMainService");
		}
		return fsscExpenseMainService;
	}

	private IEopBasedataPaymentService eopBasedataPaymentService;

	public IEopBasedataPaymentService getEopBasedataPaymentService() {
		if (eopBasedataPaymentService == null) {
			eopBasedataPaymentService = (IEopBasedataPaymentService) getBean("eopBasedataPaymentService");
		}
		return eopBasedataPaymentService;
	}

	private IEopBasedataStandardService eopBasedataStandardService;

	public IEopBasedataStandardService getEopBasedataStandardService() {
		if (eopBasedataStandardService == null) {
			eopBasedataStandardService = (IEopBasedataStandardService) getBean("eopBasedataStandardService");
		}
		return eopBasedataStandardService;
	}

	private IFsscCommonCashierPaymentService fsscCommonCashierPaymentService;

	private IFsscExpenseDetailService fsscExpenseDetailService;

	public IFsscExpenseDetailService getFsscExpenseDetailService() {
		if (fsscExpenseDetailService == null) {
			fsscExpenseDetailService = (IFsscExpenseDetailService) getBean("fsscExpenseDetailService");
		}
		return fsscExpenseDetailService;
	}
	
	private IFsscCommonProvisionService fsscCommonProvisionService;

	public IFsscCommonProvisionService getFsscCommonProvisionService() {
		if (fsscCommonProvisionService == null) {
			fsscCommonProvisionService = (IFsscCommonProvisionService) getBean("fsscProvisionCommonService");
		}
		return fsscCommonProvisionService;
	}
	
	public IFsscCommonBaiwangService fsscCommonBaiwangService;
	
	public IFsscCommonBaiwangService getFsscCommonBaiwangService() {
		if (fsscCommonBaiwangService == null) {
			fsscCommonBaiwangService = (IFsscCommonBaiwangService) SpringBeanUtil.getBean("fsscCommonBaiwangService");
		}
		return fsscCommonBaiwangService;
	}
	public IFsscCommonNuoService fsscCommonNuoService;

	public IFsscCommonNuoService getFsscCommonNuoService() {
		if (fsscCommonNuoService == null) {
			fsscCommonNuoService = (IFsscCommonNuoService) SpringBeanUtil.getBean("fsscCommonNuoService");
		}
		return fsscCommonNuoService;
	}

	public IFsscCommonCreditService fsscCommonCreditService;

	public IFsscCommonCreditService getFsscCommonCreditService() {
		if (fsscCommonCreditService == null) {
			fsscCommonCreditService = (IFsscCommonCreditService) SpringBeanUtil.getBean("fsscCreditCommonService");
		}
		return fsscCommonCreditService;
	}
	
	public IFsscCommonPresService fsscCommonPresService;

	public IFsscCommonPresService getFsscCommonPresService() {
		if (fsscCommonPresService == null) {
			fsscCommonPresService = (IFsscCommonPresService) SpringBeanUtil.getBean("fsscPresCommonService");
		}
		return fsscCommonPresService;
	}

	/*public IFsscCommonInhandService fsscCommonInhandService;

	public IFsscCommonInhandService getFsscCommonInhandService() {
		if (fsscCommonInhandService == null) {
			fsscCommonInhandService = (IFsscCommonInhandService) SpringBeanUtil.getBean("fsscCommonInhandService");
		}
		return fsscCommonInhandService;
	}*/

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscExpenseMain.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		FsscCommonProcessUtil.buildLbpmHanderHql(hqlInfo, request, "fsscExpenseMain");
		FsscCommonUtil.getCommonWhereBlock(request,hqlInfo,"fsscExpenseMain.fdCompany.fdName","fdCompanyName","like"," and ");
		FsscCommonUtil.getCommonWhereBlock(request,hqlInfo,"fsscExpenseMain.fdProject.fdName","fdProjectName","like"," and ");
	}

	@Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
		FsscExpenseMainForm fsscExpenseMainForm = (FsscExpenseMainForm) super.createNewForm(mapping, form, request,
				response);
		((IFsscExpenseMainService) getServiceImp(request)).initFormSetting((IExtendForm) form,
				new RequestContext(request));
		String docTemplateName = fsscExpenseMainForm.getDocTemplateName();
		FsscExpenseCategory tem = (FsscExpenseCategory) getServiceImp(request).findByPrimaryKey(fsscExpenseMainForm.getDocTemplateId(),FsscExpenseCategory.class,true);
		while (tem.getFdParent() != null) {
			tem = (FsscExpenseCategory) tem.getFdParent();
			docTemplateName = tem.getFdName() + "  >  " + docTemplateName;
		}
		request.setAttribute("docTemplateName", docTemplateName);
		return fsscExpenseMainForm;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		String fdId = request.getParameter("fdId");
		FsscExpenseMain main = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(fdId, null, true);
		FsscExpenseCategory tem = main.getDocTemplate();
		String docTemplateName = tem.getFdName();
		while (tem.getFdParent() != null) {
			tem = (FsscExpenseCategory) tem.getFdParent();
			docTemplateName = tem.getFdName() + "  >  " + docTemplateName;
		}
		request.setAttribute("docTemplateName", docTemplateName);
		request.setAttribute("docTemplate", main.getDocTemplate());
		if (main.getFdCompany() != null) {
			String fdDeduRule = EopBasedataFsscUtil.getDetailPropertyValue(main.getFdCompany().getFdId(), "fdDeduRule");
			if (StringUtil.isNull(fdDeduRule)) {
				fdDeduRule = "1"; // 为空则默认为含税金额，保留原有逻辑
			}
			request.setAttribute("fdDeduFlag", fdDeduRule);
		}
		 String fdIsAuthorize=EopBasedataFsscUtil.getSwitchValue("fdIsAuthorize");
		 if(StringUtil.isNull(fdIsAuthorize)){
    		fdIsAuthorize="true";  //默认启用提单转授权
		 }
		 request.setAttribute("fdIsAuthorize", fdIsAuthorize);
		 Double offsetMoney =0.0;
		 if(null !=main.getFdOffsetList()){
			for(FsscExpenseOffsetLoan offset: main.getFdOffsetList()){
				if(null !=offset.getFdOffsetMoney() && offset.getFdOffsetMoney()>0) {
					offsetMoney = FsscNumberUtil.getAddition(offsetMoney, offset.getFdOffsetMoney());
				}
			}
		 }
		 request.setAttribute("offsetMoney", offsetMoney);
		 if(FsscCommonUtil.checkHasModule("/fssc/fee/")) {
			 List<Object[]> ledgerList=getServiceImp(request).getBaseDao().getHibernateSession().createQuery("select fdDetailId,sum(fdBudgetMoney) from FsscFeeLedger where fdModelId=:fdModelId and fdType='2' group by fdDetailId")
						.setParameter("fdModelId", main.getFdId()).list();  //占过事前的台账
			 JSONObject ledgerObj=new JSONObject();
			 for(int n=0,size=ledgerList.size();n<size;n++) {
				 Object[] obj=ledgerList.get(n);
				 ledgerObj.put(obj[0], obj[1]);
			 }
			 request.setAttribute("feeLedgerObj", ledgerObj);
		 }
		 if(FsscCommonUtil.checkHasModule("/fssc/budget/")) {
			 List<Object[]> budgetList=getServiceImp(request).getBaseDao().getHibernateSession().createQuery("select fdDetailId,fdBudgetId,sum(fdMoney) from FsscBudgetExecute where fdModelId=:fdModelId and fdType='2' group by fdDetailId,fdBudgetId")
					 .setParameter("fdModelId", main.getFdId()).list();  //占过预算的信息
			 JSONObject budgetObj=new JSONObject();
			 for(int n=0,size=budgetList.size();n<size;n++) {
				 Object[] obj=budgetList.get(n);
				 if(budgetObj.containsKey(obj[0])) {
					 Double old_value=budgetObj.optDouble(String.valueOf(obj[0]), 0.0);
					 Double new_value=obj[2]!=null?Double.parseDouble(String.valueOf(obj[2])):0.0;
					 if(new_value-old_value>0) {
						 budgetObj.put(obj[0], new_value); //占用年度季度月度，如果月份占用了多个的话,统计出来的年度金额肯定是最大的，若是单个月度金额，那和年度一致。项目等预算只会存在一个。
					 }
					 
				 }else {
					 budgetObj.put(obj[0], obj[2]); //占用多个预算，
				 }
			 }
			 request.setAttribute("budgetObj", budgetObj);
		 }
		 //查询当前单据有无未申诉的信用扣分记录
		 if(FsscCommonUtil.checkHasModule("/fssc/credit/")) {
			 Boolean fdIsAppealed = getFsscCommonCreditService().getFdRecordIsAppealed(fdId, FsscExpenseMain.class.getName());
			 request.setAttribute("fdIsAppealed", fdIsAppealed);
		 }
		 //查询当前单据有无交退单记录
		 if(FsscCommonUtil.checkHasModule("/fssc/pres/")) {
			 Boolean hasPres = getFsscCommonPresService().getIsHasPres(fdId, FsscExpenseMain.class.getName());
			 request.setAttribute("hasPres", hasPres);
		 }
		 if(PdaFlagUtil.checkIsPdaLogin(request)){
			//移动端审批加载交单退单记录
			if(FsscCommonUtil.checkHasModule("/fssc/pres/")) {
				JSONArray list = getFsscCommonPresService().getPresData(fdId, FsscExpenseMain.class.getName());
				request.setAttribute("showPres", list.size()>0?true:false);
				request.setAttribute("queryList", list);
			}
		}
	}

	/**
	 * 查找预算
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForm getBudgetData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String json = request.getParameter("data");
		try {
			JSONArray param = JSONArray.fromObject(json);
			JSONArray budgets = new JSONArray();
			if (FsscCommonUtil.checkHasModule("/fssc/budget/")) {
				for (int i = 0; i < param.size(); i++) {
					JSONObject row = param.getJSONObject(i);
					String fdDetailId = row.getString("fdDetailId");
					JSONObject budget = getFsscCommonBudgetService().matchFsscBudget(row);
					row.clear();
					row.put("fdDetailId", fdDetailId);
					row.put("budget", budget);
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
	 * 导出网银
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadBankFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String ids = request.getParameter("ids");
		List<FsscExpenseMain> mainList = getServiceImp(request).findByPrimaryKeys(ids.split(";"));
		for (FsscExpenseMain main : mainList) {
			main.setFdIsExportBank(FsscExpenseConstant.FSSC_EXPENSE_BANK_EXPORTING);
			getServiceImp(request).update(main);
		}
		getServiceImp(request).getBaseDao().getHibernateSession().flush();
		try {
			((IFsscExpenseMainService) getServiceImp(request)).downloadBankFile(request, response);
			for (FsscExpenseMain main : mainList) {
				main.setFdIsExportBank(FsscExpenseConstant.FSSC_EXPENSE_BANK_EXPORTED);
				getServiceImp(request).update(main);
			}
			getServiceImp(request).getBaseDao().getHibernateSession().flush();
		} catch (Exception e) {
			messages.addError(e);
			for (FsscExpenseMain main : mainList) {
				main.setFdIsExportBank(FsscExpenseConstant.FSSC_EXPENSE_BANK_TO_EXPORT);
				getServiceImp(request).update(main);
			}
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addButton(KmssReturnPage.BUTTON_CLOSE).addMessages(messages)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return null;
		}
	}

	public ActionForward checkDownloadBank(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ids = request.getParameter("ids");
		List<FsscExpenseMain> mainList = getServiceImp(request).findByPrimaryKeys(ids.split(";"));
		JSONObject rtn = new JSONObject();
		rtn.put("result", "success");
		for (FsscExpenseMain main : mainList) {
			if (FsscExpenseConstant.FSSC_EXPENSE_BANK_EXPORTING.equals(main.getFdIsExportBank())) {
				rtn.put("result", "failure");
				rtn.put("message", ResourceUtil.getString("tips.bankExporting", "fssc-expense").replaceAll("\\{0\\}",
						main.getDocNumber()));
				break;
			}
			if (FsscExpenseConstant.FSSC_EXPENSE_BANK_EXPORTED.equals(main.getFdIsExportBank())) {
				rtn.put("result", "failure");
				rtn.put("message", ResourceUtil.getString("tips.bankExported", "fssc-expense").replaceAll("\\{0\\}",
						main.getDocNumber()));
				break;
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}

	/**
	 * 确认付款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward confirmPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FsscExpenseMainForm mainForm = (FsscExpenseMainForm) form;
		mainForm.setFdPaymentStatus(EopBasedataConstant.FSSC_BASE_PAYMENT_STATUS_PAYED);
		mainForm.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		return super.update(mapping, mainForm, request, response);
	}

	/**
	 * 检查付款节点是否可以通过流程
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		JSONObject rtn = new JSONObject();
		rtn.put("result", "success");
		try {
			if (FsscCommonUtil.checkHasModule("/fssc/cashier/")) {
				FsscExpenseMain main = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(fdId, null, true);
				List<FsscExpenseAccounts> listCount = main.getFdAccountsList();
				Double fdMoney = 0.0;
				for (int i = 0; i < listCount.size(); i++) {
					fdMoney += listCount.get(i).getFdMoney();
				}
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"eopBasedataPayment.fdModelId=:fdModelId and eopBasedataPayment.fdModelName=:fdModelName");
				hqlInfo.setParameter("fdModelId", fdId);
				hqlInfo.setParameter("fdModelName", FsscExpenseMain.class.getName());
				List<EopBasedataPayment> list = getEopBasedataPaymentService().findList(hqlInfo);

				Map<String, String> map = new HashMap<String, String>();
				map.put("fdModelId", fdId);
				map.put("fdModelName", FsscExpenseMain.class.getName());
				Map<String, Object> tempMap = getFsscCommonCashierPaymentService().isCreatePayment(map);
				if (fdMoney > 0.0 && "failure".equals(tempMap.get("result") + "")) {
					rtn.put("result", "failure");
					rtn.put("message", tempMap.get("message") + "");
				} else {
					if (fdMoney > 0.0 && !((Boolean) tempMap.get("fdIsCreatePayment"))) {
						rtn.put("result", "failure");
						rtn.put("message", ResourceUtil.getString("tips.cannotPayment", "fssc-expense")
								.replaceAll("\\{0\\}", main.getDocNumber()));
					}
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addButton(KmssReturnPage.BUTTON_CLOSE).addMessages(messages)
					.save(request);
			return mapping.findForward("failure");
		} else {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(rtn.toString());
			return null;
		}
	}

	/**
	 * 批量确认付款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchConfirmPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ids = request.getParameter("ids");
		String type = request.getParameter("type");
		JSONObject rtn = new JSONObject();
		rtn.put("result", "success");
		try {
			rtn = ((IEopBasedataPaymentDataService) getServiceImp(request)).updatePyament(ids, type);
		} catch (Exception e) {
			rtn.put("result", "faliure");
			rtn.put("message", ResourceUtil.getString("errors.unknown") + "<br>" + e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}

	/**
	 * 单条明细匹配预算
	 * 
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
		if (!FsscCommonUtil.checkHasModule("/fssc/budget/")) {
			JSONObject budget = new JSONObject();
			budget.put("result", "2");
			budget.put("data", new JSONArray());
			rtn.put("budget", new JSONObject());
		} else {
			JSONObject data = JSONObject.fromObject(param);
			JSONObject budget = getFsscCommonBudgetService().matchFsscBudget(data);
			try {
				EopBasedataCompany comp = (EopBasedataCompany) getServiceImp(request)
						.findByPrimaryKey(data.getString("fdCompanyId"), EopBasedataCompany.class, true);
				String hql = "select rate from com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate rate left join rate.fdCompanyList comp where rate.fdType=:fdType and rate.fdIsAvailable=:fdIsAvailable and (comp.fdId=:fdCompanyId or comp.fdId is null) and rate.fdSourceCurrency.fdId=:fdSourceCurrencyId and rate.fdTargetCurrency.fdId=:fdTargetCurrencyId";
				Query query = getServiceImp(request).getBaseDao().getHibernateSession().createQuery(hql);
				// 判断是否启用了实时汇率
				String value = EopBasedataFsscUtil.getSwitchValue("fdRateEnabled");
				if ("true".equals(value)) {
					query.setParameter("fdType", EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_CURRENT);
				} else {
					query.setParameter("fdType", EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
				}
				query.setParameter("fdCompanyId", data.getString("fdCompanyId"));
				query.setParameter("fdIsAvailable", true);
				query.setParameter("fdSourceCurrencyId", data.getString("fdCurrencyId"));
				query.setParameter("fdTargetCurrencyId", comp.getFdBudgetCurrency().getFdId());
				List<EopBasedataExchangeRate> rates = query.list();
				if (ArrayUtil.isEmpty(rates)) {
					// 如果没有查到相应汇率，但匹配到了预算，提示用户不能报销
					if ("2".equals(budget.get("result")) && budget.getJSONArray("data").size() > 0) {
						rtn.put("result", "failure");
						rtn.put("message", ResourceUtil.getString("tips.exchangeRateNotExist", "fssc-expense"));
					} else {
						rtn.put("budget", budget);
						rtn.put("fdBudgetRate", "0");
					}
				} else {
					rtn.put("budget", budget);
					rtn.put("fdBudgetRate", rates.get(0).getFdRate());
				}
			} catch (Exception e) {
				rtn.put("result", "failure");
				rtn.put("message", ResourceUtil.getString("errors.unknown") + "<br>" + e.getMessage());
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}

	/**
	 * 匹配标准
	 * 
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
			for (int i = 0; i < data.size(); i++) {
				JSONObject obj = getEopBasedataStandardService().getStandardData(data.getJSONObject(i));
				obj.put("index", data.getJSONObject(i).get("index"));
				object.add(obj);
			}
			rtn.put("data", object);
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", ResourceUtil.getString("errors.unknown") + "<br>" + e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}

	/**
	 * 加载冲抵借款信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLoanData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        String fdCompanyId=request.getParameter("fdCompanyId");
		String flag = request.getParameter("flag");
		try {
			JSONObject param = new JSONObject();
			String fdPersonId = request.getParameter("fdPersonId");
			String fdId = request.getParameter("fdId");
			String fdLoanIds = "";
			if (StringUtil.isNotNull(fdId)) {// 如果是财务审核或者编辑，不应该将剩余金额为0的忽略
				FsscExpenseMain main = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(fdId,
						FsscExpenseMain.class, true);
				for (FsscExpenseOffsetLoan off : main.getFdOffsetList()) {
					if (off.getFdOffsetMoney() == null) {
						continue;
					}
					fdLoanIds += off.getFdLoanId() + ";";
				}
			}
			param.put("fdPersonId", fdPersonId);
			param.put("fdLoanIds", fdLoanIds);
			JSONArray list =new JSONArray();
			if(FsscCommonUtil.checkHasModule("/fssc/loan/")) {
				JSONObject obj = getFsscCommonLoanService().getLoanInfo(param);
				list = obj.getJSONArray("jsonArray");
				for (int i=0;i< list.size();i++) {
		            JSONObject o = list.getJSONObject(i);
		            String companyId= getFsscCommonLoanService().getLoanInfoById(o.getString("fdLoanId"));
					if(!fdCompanyId.equals(companyId)){
						list.remove(i--);
					}
				}
				// 如果传了id，说明是审批或者编辑
				if (StringUtil.isNotNull(fdId)) {
					FsscExpenseMain main = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(fdId,
							FsscExpenseMain.class, true);
					for (FsscExpenseOffsetLoan off : main.getFdOffsetList()) {
						if (off.getFdOffsetMoney() == null) {
							continue;
						}
						for (int i = 0; i < list.size(); i++) {
							JSONObject data = list.getJSONObject(i);
							if (!off.getFdLoanId().equals(data.optString("fdLoanId"))) {// 不是同一笔借款
								continue;
							}
							Double fdLeftMoney = data.getDouble("fdLeftMoney");
							Double fdCanOffsetMoney = data.getDouble("fdCanOffsetMoney");
							Double fdOffsetMoney = off.getFdOffsetMoney();
							// 如果当前单据是财务审批阶段或者编辑,需要重新加载相关数据
							if ("edit".equals(flag) && !(SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())
									|| SysDocConstant.DOC_STATUS_REFUSE.equals(main.getDocStatus()))) {
								fdCanOffsetMoney = FsscNumberUtil.getAddition(fdCanOffsetMoney, fdOffsetMoney, 2);
							}
							fdLeftMoney = FsscNumberUtil.getSubtraction(fdCanOffsetMoney, fdOffsetMoney, 2);
							data.put("fdCanOffsetMoney", fdCanOffsetMoney);
							data.put("fdLeftMoney", fdLeftMoney);
							data.put("fdOffsetMoney", off.getFdOffsetMoney());
						}
					}
				}
			}
			request.setAttribute("queryList", list);
			// 移快報借款初始化
			if ("mobile".equals(flag)) {
				JSONObject rtn = new JSONObject();
				rtn.put("data", list);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(rtn.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("mobile".equals(flag)) {
			return null;
		} else {
			return getActionForward("listLoan", mapping, form, request, response);
		}

	}

	public ActionForward publishDraft(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FsscExpenseMainForm mainForm = (FsscExpenseMainForm) form;
		mainForm.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		return super.update(mapping, form, request, response);
	}

	/**
	 * 匹配事前申请
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward matchFee(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String params = request.getParameter("data");
		JSONObject data = JSONObject.fromObject(params);
		JSONObject rtn = new JSONObject();
		try {
			rtn = getFsscCommonFeeService().getFeeLedgerData(data);
			rtn.put("result", "success");
		} catch (Exception e) {
			rtn.put("result", "failure");
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}

	/**
	 * 打印粘贴单/粘帖单。<br>
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
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			FsscExpenseMainForm rtnForm = null;
			String id = request.getParameter("fdId");
			if (!StringUtil.isNull(id)) {
				FsscExpenseMain model = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(id, null, true);
				request.setAttribute("fsscExpenseMain", model);
				request.setAttribute("docTemplate",model.getDocTemplate());
				request.setAttribute("fdPrintTime",
						DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME));// 打印时间
				if(model.getFdTotalApprovedMoney()!=null) {
					BigDecimal bd=new BigDecimal(model.getFdTotalApprovedMoney());
					request.setAttribute("fdTotalApprovedMoney", bd.toPlainString());//核准金额
				}
				Double offsetMoney =0.0;
				if(null !=model.getFdOffsetList()){
					for(FsscExpenseOffsetLoan offset: model.getFdOffsetList()){
						if(null !=offset.getFdOffsetMoney() && offset.getFdOffsetMoney()>0) {
							offsetMoney = FsscNumberUtil.getAddition(offsetMoney, offset.getFdOffsetMoney());
						}
					}
				}
				request.setAttribute("offsetMoney", offsetMoney);
				Double accountMoney =0.0;
				if(null !=model.getFdAccountsList()){
					for(FsscExpenseAccounts account: model.getFdAccountsList()){
						accountMoney = FsscNumberUtil.getAddition(accountMoney, account.getFdMoney()==null?0d:account.getFdMoney());
					}
				}
				request.setAttribute("accountMoney", accountMoney);
				if (model != null) {
					rtnForm = (FsscExpenseMainForm) getServiceImp(request).convertModelToForm((IExtendForm) form, model,
							new RequestContext(request));
				}
				//交单记录
				if(FsscCommonUtil.checkHasModule("/fssc/pres/")) {
					JSONArray presList = getFsscCommonPresService().getPresData(id, FsscExpenseMain.class.getName());
					request.setAttribute("presList", presList);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			if ("stickyNote".equals(request.getParameter("type"))) {// 粘帖单
				return getActionForward("stickyNote", mapping, form, request, response);
			} else {// 报销单
				return getActionForward("expenseNote", mapping, form, request, response);
			}
		}
	}
	
	/**
	 * 批量打印
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printall(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String xForm = request.getParameter("getXForm");
			if ("1".equals(xForm)) {
				view(mapping, form, request, response);
				return mapping.findForward("printall_xform");
			}
			String fdIds = request.getParameter("ids");
			String[] fdId = fdIds != null ? fdIds.split(",") : null;
			List<FsscExpenseMainForm> expenseMainFormList = new ArrayList<FsscExpenseMainForm>();
			FsscExpenseMain model = null;
			for (String id : fdId) {
				model = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(id, null, true);
				FsscExpenseMainForm rtnForm = null;
				if (model != null) {
					rtnForm = (FsscExpenseMainForm) getServiceImp(request).convertModelToForm((IExtendForm) rtnForm, model,
							new RequestContext(request));
					//交单记录
					if(FsscCommonUtil.checkHasModule("/fssc/pres/")) {
						JSONArray presList = getFsscCommonPresService().getPresData(id, FsscExpenseMain.class.getName());
						rtnForm.setPresList(presList);
					}
					expenseMainFormList.add(rtnForm);
				}

			}
	        request.setAttribute("expenseMainFormList", expenseMainFormList);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("printall", mapping, form, request, response);
		}
	}

	/**
	 * 校验是否选中待审、发布单据
	 */
	public ActionForward checkDeleteAll(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		if (!EopBasedataFsscUtil.checkStatus(ids, FsscExpenseMain.class.getName(), "docStatus", "20;30")) {
			messages.addError(new KmssMessage(ResourceUtil.getString("fssc.common.examine.or.publish.delete.tips", "fssc-common")));
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 对接银企直联支付
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward paymentToBank(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		JSONObject rtn = new JSONObject();
		rtn.put("result", "success");
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("fdModelId", fdId);
			map.put("fdModelName", FsscExpenseMain.class.getName());
			Map<String, Object> paymentMap = getFsscCommonCashierPaymentService().paymentToBank(map);
			rtn.put("result", paymentMap.get("result"));
			rtn.put("message", paymentMap.get("message"));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addButton(KmssReturnPage.BUTTON_CLOSE).addMessages(messages)
					.save(request);
			return mapping.findForward("failure");
		} else {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(rtn.toString());
			return null;
		}
	}

	public IFsscCommonCashierPaymentService getFsscCommonCashierPaymentService() {
		if (fsscCommonCashierPaymentService == null) {
			fsscCommonCashierPaymentService = (IFsscCommonCashierPaymentService) SpringBeanUtil
					.getBean("fsscCommonCashierPaymentService");
		}
		return fsscCommonCashierPaymentService;
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

	/**
	 * 报销台账
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = getDetailHqlInfo(request);
			hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
			hqlInfo.setOrderBy("fsscExpenseDetail.docMain.docCreateTime desc");
			Page page = getFsscExpenseDetailService().findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addButton(KmssReturnPage.BUTTON_CLOSE).addMessages(messages)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("listDetail");
		}
	}

	/**
	 * 报销台账导出
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = getDetailHqlInfo(request);
			getFsscExpenseDetailService().exportDetail(hqlInfo, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addButton(KmssReturnPage.BUTTON_CLOSE).addMessages(messages)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return null;
		}
	}

	private HQLInfo getDetailHqlInfo(HttpServletRequest request) {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setFromBlock(FsscExpenseDetail.class.getName() + " fsscExpenseDetail");
		hqlInfo.setSelectBlock("");
		StringBuilder where = new StringBuilder(
				"fsscExpenseDetail.docMain.docStatus<>'00' and fsscExpenseDetail.docMain.docStatus<>'10'  ");
		String fdCompanyId = request.getParameter("fdCompanyId");
		if (StringUtil.isNotNull(fdCompanyId)) {
			where.append("and fsscExpenseDetail.fdCompany.fdId=:fdCompanyId ");
			hqlInfo.setParameter("fdCompanyId", fdCompanyId);
		}
		String fdCostCenterId = request.getParameter("fdCostCenterId");
		if (StringUtil.isNotNull(fdCostCenterId)) {
			where.append("and fsscExpenseDetail.fdCostCenter.fdId=:fdCostCenterId ");
			hqlInfo.setParameter("fdCostCenterId", fdCostCenterId);
		}
		String fdProjectId = request.getParameter("fdProjectId");
		if (StringUtil.isNotNull(fdProjectId)) {
			where.append("and fsscExpenseDetail.docMain.fdProject.fdId=:fdProjectId ");
			hqlInfo.setParameter("fdProjectId", fdProjectId);
		}
		String fdCategoryId = request.getParameter("fdCategoryId");
		if (StringUtil.isNotNull(fdCategoryId)) {
			where.append("and fsscExpenseDetail.docMain.docTemplate.fdId=:fdCategoryId ");
			hqlInfo.setParameter("fdCategoryId", fdCategoryId);
		}
		String fdExpenseItemId = request.getParameter("fdExpenseItemId");
		if (StringUtil.isNotNull(fdExpenseItemId)) {
			where.append("and fsscExpenseDetail.fdExpenseItem.fdId=:fdExpenseItemId ");
			hqlInfo.setParameter("fdExpenseItemId", fdExpenseItemId);
		}
		String docCreatorId = request.getParameter("docCreatorId");
		String fdClaimantId = request.getParameter("fdClaimantId");
		if (StringUtil.isNotNull(docCreatorId) || StringUtil.isNotNull(fdClaimantId)) {
			where.append("and (fsscExpenseDetail.docMain.docCreator.fdId=:docCreatorId  or  fsscExpenseDetail.docMain.fdClaimant.fdId=:fdClaimantId) ");
			hqlInfo.setParameter("docCreatorId", docCreatorId);
			hqlInfo.setParameter("fdClaimantId", fdClaimantId);
		}
		String docCreateTime1 = request.getParameter("docCreateTime1");
		if (StringUtil.isNotNull(docCreateTime1)) {
			Calendar now = Calendar.getInstance();
			now.setTime(DateUtil.convertStringToDate(docCreateTime1, DateUtil.PATTERN_DATE));
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
			where.append("and fsscExpenseDetail.docMain.docCreateTime>=:docCreateTime1 ");
			hqlInfo.setParameter("docCreateTime1", now.getTime());
		}
		String docCreateTime2 = request.getParameter("docCreateTime2");
		if (StringUtil.isNotNull(docCreateTime2)) {
			Calendar now = Calendar.getInstance();
			now.setTime(DateUtil.convertStringToDate(docCreateTime2, DateUtil.PATTERN_DATE));
			now.set(Calendar.HOUR_OF_DAY, 23);
			now.set(Calendar.MINUTE, 59);
			now.set(Calendar.SECOND, 59);
			where.append("and fsscExpenseDetail.docMain.docCreateTime<=:docCreateTime2 ");
			hqlInfo.setParameter("docCreateTime2", now.getTime());
		}
		String fdProappId = request.getParameter("fdProappId");
		if (StringUtil.isNotNull(fdProappId)) {
			where.append("and fsscExpenseDetail.docMain.fdProappId=:fdProappId ");
			hqlInfo.setParameter("fdProappId", fdProappId);
		}
		
		hqlInfo.setWhereBlock(where.toString());
		String rowsize = request.getParameter("rowsize");
		int rs = 15;
		if (StringUtil.isNotNull(rowsize)) {
			rs = Integer.parseInt(rowsize);
		}
		String pageno = request.getParameter("pageno");
		int pn = 1;
		if (StringUtil.isNotNull(pageno)) {
			pn = Integer.parseInt(pageno);
		}
		hqlInfo.setRowSize(rs);
		hqlInfo.setPageNo(pn);
		return hqlInfo;
	}

	/**
	 * 校验是否有报销关联所选的事前，在途
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForm checkFeeRelation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			JSONObject rtn = ((IFsscExpenseMainService) getServiceImp(request)).checkFeeRelation(request);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(String.valueOf(rtn));
		} catch (Exception e) {
			response.getWriter().write("");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 校验是否有报销关联所选的发票，在途
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForm checkInvoiceDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			JSONObject rtn = ((IFsscExpenseMainService) getServiceImp(request)).checkInvoiceDetail(request);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(String.valueOf(rtn));
		} catch (Exception e) {
			response.getWriter().write("");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 手动验真
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForm checkInvoice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			JSONObject rtn = ((IFsscExpenseMainService) getServiceImp(request)).checkInvoice(request);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(String.valueOf(rtn));
		} catch (Exception e) {
			response.getWriter().write("");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 提交人手动验真，批量
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForm checkInvoices(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String fdInvVerCpy = EopBasedataFsscUtil.getSwitchValue("fdInvVerCpy");
			JSONObject rtn =null;
			if("2".equals(fdInvVerCpy)){
				rtn = getFsscCommonNuoService().checkInvoices(request);
			}else {
				rtn = getFsscCommonBaiwangService().checkInvoices(request);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(String.valueOf(rtn));
		} catch (Exception e) {
			response.getWriter().write("");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 打开辅助信息页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward viewMsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdExpenseId = request.getParameter("fdExpenseId");
			FsscExpenseMain fsscExpenseMain = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(fdExpenseId,
					null, true);
			Double fdTotalApprovedMoney = fsscExpenseMain.getFdTotalApprovedMoney();
			String fdPersonId = fsscExpenseMain.getFdClaimant().getFdId();// 创建人
			request.setAttribute("fdTotalMoney", fdTotalApprovedMoney);// 报销单对应的核准金额
			List<FsscExpenseAccounts> fsscExpenseAccountss = fsscExpenseMain.getFdAccountsList();// 报销收款明细
			Double fdAccountsMoney = 0.0;
			for (FsscExpenseAccounts fsscExpenseAccounts : fsscExpenseAccountss) {
				fdAccountsMoney += fsscExpenseAccounts.getFdMoney();
			}
			request.setAttribute("fdAccountsMoney", FsscNumberUtil.doubleToUp(fdAccountsMoney));// 报销收款金额
			if(null !=fdTotalApprovedMoney){
				request.setAttribute("fdOffsetMoney", FsscNumberUtil.doubleToUp(fdTotalApprovedMoney - fdAccountsMoney));// 报销核准金额-收款金额=冲抵金额
			}else{
				request.setAttribute("fdOffsetMoney", 0.0);
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fsscExpenseMain.docCreator.fdId =:docCreatorId ");
			hqlInfo.setParameter("docCreatorId", fdPersonId);
			hqlInfo.setOrderBy(" fsscExpenseMain.docCreateTime desc");
			hqlInfo.setRowSize(10);
			hqlInfo.setPageNo(0);
			List<FsscExpenseMain> fsscExpenseMains = getServiceImp(request).findPage(hqlInfo).getList();// 查询10条个人的报销列表
			request.setAttribute("expenseList", fsscExpenseMains);
			if (FsscCommonUtil.checkHasModule("/fssc/loan/")) {
				Map<String, Double> fdLoanTotalObj = getFsscCommonLoanService().getLoanExecuteDetailControl(null,
						fdPersonId);
				request.setAttribute("fdLoanTotalObj", fdLoanTotalObj);// 获取个人借款情况
			}
			if (FsscCommonUtil.checkHasModule("/fssc/fee/")) {
				String fdFeeIds = fsscExpenseMain.getFdFeeIds();
				JSONObject feeByIdsObject = new JSONObject();
				JSONObject feeByCreaObject = new JSONObject();
				if (StringUtil.isNotNull(fdFeeIds)) {
					request.setAttribute("isHaveFee", true);
					String[] fdFeeId = fdFeeIds.split(";");
					String sqlByFeeId = "select sum(case when fsscFeeLedger.fdType = '1' then fdStandardMoney end ) as fdStandardMoney, "
							+ "sum(case when fsscFeeLedger.fdType = '2' then fdBudgetMoney end ) as fdUseingMoney, "
							+ "sum(case when fsscFeeLedger.fdType = '3' then fdBudgetMoney end ) as fdUsedMoney "
							+ "from FsscFeeLedger fsscFeeLedger " + "where fsscFeeLedger.fdLedgerId in ( "
							+ "select fsscFeeLedger1.fdId from FsscFeeLedger fsscFeeLedger1 where fsscFeeLedger1.fdModelId in (:fdModelId) "
							+ ") ";
					Query queryByFeeId = getServiceImp(request).getBaseDao().getHibernateSession()
							.createQuery(sqlByFeeId);
					queryByFeeId.setParameterList("fdModelId", Arrays.asList(fdFeeId));
					List<Object[]> listByFeeId = queryByFeeId.list();
					for (Object[] object : listByFeeId) {
						BigDecimal fdStandardMoney = BigDecimal.valueOf((Double) (null != object[0] ? object[0] : 0.0));
						BigDecimal fdUseingMoney = BigDecimal.valueOf((Double) (null != object[1] ? object[1] : 0.0));
						BigDecimal fdUsedMoney = BigDecimal.valueOf((Double) (null != object[2] ? object[2] : 0.0));
						BigDecimal fdCanUseMoney = fdStandardMoney.subtract(fdUseingMoney).subtract(fdUsedMoney);
						feeByIdsObject.put("fdStandardMoney", fdStandardMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
						feeByIdsObject.put("fdUseingMoney", fdUseingMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
						feeByIdsObject.put("fdUsedMoney", fdUsedMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
						feeByIdsObject.put("fdCanUseMoney", fdCanUseMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
						request.setAttribute("feeByIdsObject", feeByIdsObject);
					}

				} else {
					feeByIdsObject.put("fdStandardMoney", 0.00);
					feeByIdsObject.put("fdUseingMoney", 0.00);
					feeByIdsObject.put("fdUsedMoney", 0.00);
					feeByIdsObject.put("fdCanUseMoney", 0.00);
					request.setAttribute("feeByIdsObject", feeByIdsObject);
					request.setAttribute("isHaveFee", false);
				}
				String sqlFeeByCrea = "select sum(case when fsscFeeLedger.fdType = '1' then fdStandardMoney end ) as fdStandardMoney, "
						+ "sum(case when fsscFeeLedger.fdType = '2' then fdBudgetMoney end ) as fdUseingMoney, "
						+ "sum(case when fsscFeeLedger.fdType = '3' then fdBudgetMoney end ) as fdUsedMoney "
						+ "from FsscFeeLedger fsscFeeLedger " + "where fsscFeeLedger.fdLedgerId in ( "
						+ "select fsscFeeLedger1.fdId from FsscFeeLedger fsscFeeLedger1 where fsscFeeLedger1.fdModelId in (select  fsscFeeMain.fdId from FsscFeeMain fsscFeeMain where fsscFeeMain.docCreator.fdId =:docCreatorId"
						+ ") " + ") ";
				Query queryByCrea = getServiceImp(request).getBaseDao().getHibernateSession().createQuery(sqlFeeByCrea);
				queryByCrea.setParameter("docCreatorId", fdPersonId);
				List<Object[]> listByCrea = queryByCrea.list();
				for (Object[] object : listByCrea) {
					BigDecimal fdStandardMoney = BigDecimal.valueOf((Double) (null != object[0] ? object[0] : 0.0));
					BigDecimal fdUseingMoney = BigDecimal.valueOf((Double) (null != object[1] ? object[1] : 0.0));
					BigDecimal fdUsedMoney = BigDecimal.valueOf((Double) (null != object[2] ? object[2] : 0.0));
					BigDecimal fdCanUseMoney = fdStandardMoney.subtract(fdUseingMoney).subtract(fdUsedMoney);
					feeByCreaObject.put("fdStandardMoney", fdStandardMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
					feeByCreaObject.put("fdUseingMoney", fdUseingMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
					feeByCreaObject.put("fdUsedMoney", fdUsedMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
					feeByCreaObject.put("fdCanUseMoney", fdCanUseMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
					request.setAttribute("feeByCreaObject", feeByCreaObject);
				}
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.YEAR, -1);
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			Date beginDate = calendar.getTime();// 一年前月份的第一天
			calendar.setTime(new Date());
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date endDate = calendar.getTime();// 当前月份的最后一天
			HQLInfo hqlInfo2 = new HQLInfo();
			hqlInfo2.setWhereBlock(
					" fsscExpenseMain.docCreator.fdId =:docCreatorId and (fsscExpenseMain.docCreateTime between :beginDate and :endDate )");
			hqlInfo2.setParameter("docCreatorId", fdPersonId);
			hqlInfo2.setParameter("beginDate", beginDate);
			hqlInfo2.setParameter("endDate", endDate);
			List<FsscExpenseMain> expenseList = getServiceImp(request).findList(hqlInfo2);
			Map<Integer, Double> expenseListByMonth = new HashMap<Integer, Double>();
			for (FsscExpenseMain fsscExpenseMain1 : expenseList) {
				Date docCreateTime = fsscExpenseMain1.getDocCreateTime();
				calendar.setTime(docCreateTime);
				Integer month = calendar.get(Calendar.MONTH) + 1;
				Double fdExpenseMoney = 0.0;
				if(null!= fsscExpenseMain1.getFdTotalApprovedMoney()){
					fdExpenseMoney = FsscNumberUtil.doubleToUp(fsscExpenseMain1.getFdTotalApprovedMoney());
				}
				if (null != expenseListByMonth.get(month)) {
					fdExpenseMoney += expenseListByMonth.get(month);
				}
				expenseListByMonth.put(month, fdExpenseMoney);
			}

			JSONArray expenseObject = new JSONArray();
			JSONArray monthObject = new JSONArray();
			for (int i = 11; i >= 0; i--) {
				calendar.setTime(new Date());
				calendar.add(Calendar.MONTH, -i);
				Integer month = calendar.get(Calendar.MONTH) + 1;
				monthObject.add(month + "月");
				expenseObject.add(null != expenseListByMonth.get(month) ? expenseListByMonth.get(month) : 0.0);
			}
			request.setAttribute("monthObject", monthObject);
			request.setAttribute("expenseObject", expenseObject);
			List<FsscExpenseDetail> fsscExpenseDetails = fsscExpenseMain.getFdDetailList();// 费用明细
			List<Map<String, Object>> budgetMapList = new ArrayList<Map<String, Object>>();
			Map<String, String> budgetMap = new HashMap<String, String>();
			List<Map<String, Object>> fdStandardMapList = new ArrayList<Map<String, Object>>();
			Map<String, String> fdStandardMap = new HashMap<String, String>();
			String budgetStatus = "0";// 预算状态
			String fdStandStatus = "0";// 标准状态
			String fdFeeStatus = "0";// 事前状态
			IEopBasedataBudgetItemService eopBasedataBudgetItemComService = (IEopBasedataBudgetItemService) SpringBeanUtil
					.getBean("eopBasedataBudgetItemService");
			for (FsscExpenseDetail fsscExpenseDetail : fsscExpenseDetails) {

				String fdStandardInfo = fsscExpenseDetail.getFdStandardInfo();// 标准信息
				if (StringUtil.isNotNull(fdStandardInfo)) {
					JSONArray jsonStandardInfo = JSONArray.fromObject(fdStandardInfo);
					for (int i = 0; i < jsonStandardInfo.size(); i++) {
						JSONObject jsonObject = JSONObject.fromObject(jsonStandardInfo.get(i));
						if (null != jsonObject
								&& StringUtil.isNull(fdStandardMap.get(jsonObject.getString("fdStandardId")))) {
							fdStandardMap.put(jsonObject.getString("fdStandardId"), jsonObject.getString("subject"));
							Map<String, Object> StandardObject = new HashMap<String, Object>();
							StandardObject.put("fdStandardId", jsonObject.getString("fdStandardId"));
							StandardObject.put("fdItemName", jsonObject.getString("fdItemName"));
							StandardObject.put("fdMoney",
									null != jsonObject.get("fdMoney") ? jsonObject.getString("fdMoney") : 0.00);
							StandardObject.put("subject", jsonObject.getString("subject"));
							fdStandardMapList.add(StandardObject);
						}
					}
				}

				if ("2".equals(fsscExpenseDetail.getFdBudgetStatus())) {
					budgetStatus = fsscExpenseDetail.getFdBudgetStatus();// 预算状态
				} else {
					if (!("1".equals(budgetStatus) || "2".equals(budgetStatus))) {
						budgetStatus = fsscExpenseDetail.getFdBudgetStatus();// 预算状态
					}
				}

				if ("2".equals(fsscExpenseDetail.getFdStandardStatus())) {
					fdStandStatus = fsscExpenseDetail.getFdStandardStatus();// 标准状态
				} else {
					if (!("1".equals(fdStandStatus) || "2".equals(fdStandStatus))) {
						fdStandStatus = fsscExpenseDetail.getFdStandardStatus();// 标准状态
					}
				}
				if ("2".equals(fsscExpenseDetail.getFdFeeStatus())) {
					fdFeeStatus = fsscExpenseDetail.getFdFeeStatus();// 事前状态
				} else {
					if (!("1".equals(fdFeeStatus) || "2".equals(fdFeeStatus))) {
						fdFeeStatus = fsscExpenseDetail.getFdFeeStatus();// 事前状态
					}
				}
				String budgetJson = fsscExpenseDetail.getFdBudgetInfo();// 预算信息
				if (StringUtil.isNotNull(budgetJson)) {
					JSONArray jsonbudgetInfo = JSONArray.fromObject(budgetJson);
					for (int i = 0; i < jsonbudgetInfo.size(); i++) {
						JSONObject jsonObject = JSONObject.fromObject(jsonbudgetInfo.get(i));
						if (StringUtil.isNull(budgetMap.get(jsonObject.getString("fdBudgetId")))) {
							budgetMap.put(jsonObject.getString("fdBudgetId"), jsonObject.getString("fdSubject"));
							Map<String, Object> budgetObject = new HashMap<String, Object>();
							budgetObject.put("fdBudgetId", jsonObject.getString("fdBudgetId"));
							String fdBudgetItemId = jsonObject.getString("fdBudgetItemId");
							if (StringUtil.isNotNull(fdBudgetItemId)) {
								EopBasedataBudgetItem eopBasedataBudgetItemCom = (EopBasedataBudgetItem) eopBasedataBudgetItemComService
										.findByPrimaryKey(fdBudgetItemId);
								budgetObject.put("fdSubject", eopBasedataBudgetItemCom.getFdName());
							} else {
								budgetObject.put("fdSubject", jsonObject.getString("fdSubject"));
							}
							budgetObject.put("fdTotalAmount", jsonObject.getString("fdTotalAmount"));
							budgetObject.put("fdAlreadyUsedAmount", jsonObject.getString("fdAlreadyUsedAmount"));
							budgetObject.put("fdOccupyAmount", jsonObject.getString("fdOccupyAmount"));
							budgetObject.put("fdCanUseAmount", jsonObject.getString("fdCanUseAmount"));
							budgetMapList.add(budgetObject);
						}
					}
				}

			}

			request.setAttribute("fdStandardMapList", fdStandardMapList);
			if (budgetMapList.size() > 0) {// 是否有预算明细展示
				request.setAttribute("isHasBudget", true);
			} else {
				request.setAttribute("isHasBudget", false);
			}
			request.setAttribute("budgetMapList", budgetMapList);// 预算明细
			request.setAttribute("budgetStatus", budgetStatus);// 预算状态
			request.setAttribute("fdStandStatus", fdStandStatus);// 标准状态
			request.setAttribute("fdFeeStatus", fdFeeStatus);// 事前状态
			request.setAttribute("isOffsetLoan", fsscExpenseMain.getFdIsOffsetLoan());// 是否冲抵借款
			if (null != fsscExpenseMain.getFdProject() || null != fsscExpenseMain.getFdProjectAccounting()) {
				request.setAttribute("isProject", true);
			} else {
				request.setAttribute("isProject", false);
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("viewMsg", mapping, form, request, response);
		}
	}
	
	/**
	 * 匹配立项
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward matchProapp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String params = request.getParameter("data");
		JSONObject data = JSONObject.fromObject(params);
		JSONObject rtn = new JSONObject();
		try {
			rtn = getFsscCommonProappService().getProappLedgerData(data);
			rtn.put("result", "success");
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", e.getMessage());
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
	
	/**
	 * 匹配预提
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward matchProvision(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String params = request.getParameter("params");
		JSONArray data = JSONArray.fromObject(params);
		JSONObject rtn = new JSONObject();
		try {
			if(FsscCommonUtil.checkHasModule("/fssc/provision/")) {
				for(int i=0;i<data.size();i++) {
					JSONObject obj = data.getJSONObject(i);
					String fdDetailId = obj.optString("fdDetailId");
					JSONObject json = getFsscCommonProvisionService().getProvisionLedgerData(obj);
					json.put("index", obj.get("index"));
					if("success".equals(json.opt("result"))) {
						rtn.put(fdDetailId, json);
					}
				}
			}
			rtn.put("result", "success");
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", e.getMessage());
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
	
	/**
	 * 删除报销单并更新台账发票为未使用
	 */
	@Override
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).delete(id);
			}
				((IFsscExpenseMainService)getServiceImp(request)).updateLederInvice(id);
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 归档模板页面
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printFileDoc(ActionMapping mapping, ActionForm form,
									  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String pageType=request.getParameter("pageType");
		try {
			HtmlToMht.setLocaleWhenExport(request);
			loadActionForm(mapping, form, request, response);
			String saveApprovalStr = request.getParameter("saveApproval");
			boolean saveApproval = "1".equals(saveApprovalStr);
			request.setAttribute("saveApproval", saveApproval);

			FsscExpenseMainForm rtnForm = null;
			String id = request.getParameter("fdId");
			if (!StringUtil.isNull(id)) {
				FsscExpenseMain model = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(id, null, true);
				request.setAttribute("fsscExpenseMain", model);
				request.setAttribute("docTemplate",model.getDocTemplate());
				request.setAttribute("fdPrintTime",
						DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME));// 打印时间
				if(model.getFdTotalApprovedMoney()!=null) {
					BigDecimal bd=new BigDecimal(model.getFdTotalApprovedMoney());
					request.setAttribute("fdTotalApprovedMoney", bd.toPlainString());//核准金额
				}
				Double offsetMoney =0.0;
				if(null !=model.getFdOffsetList()){
					for(FsscExpenseOffsetLoan offset: model.getFdOffsetList()){
						if(null !=offset.getFdOffsetMoney() && offset.getFdOffsetMoney()>0) {
							offsetMoney = FsscNumberUtil.getAddition(offsetMoney, offset.getFdOffsetMoney());
						}
					}
				}
				request.setAttribute("offsetMoney", offsetMoney);
				Double accountMoney =0.0;
				if(null !=model.getFdAccountsList()){
					for(FsscExpenseAccounts account: model.getFdAccountsList()){
						accountMoney = FsscNumberUtil.getAddition(accountMoney, account.getFdMoney()==null?0d:account.getFdMoney());
					}
				}
				request.setAttribute("accountMoney", accountMoney);
				if (model != null) {
					rtnForm = (FsscExpenseMainForm) getServiceImp(request).convertModelToForm((IExtendForm) form, model,
							new RequestContext(request));
				}
			}

			//出纳付款单明细
			String fdId=request.getParameter("fdId");
			((IFsscCommonCashierPaymentService)getFsscCommonCashierPaymentService()).addFileDocList(request,fdId);


			//交单记录
			if(FsscCommonUtil.checkHasModule("/fssc/pres/")) {
				JSONArray presList = getFsscCommonPresService().getPresData(fdId, FsscExpenseMain.class.getName());
				request.setAttribute("presList", presList);
			}



		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		} else{
			if("cashier".equals(pageType)){
				request.setAttribute("form",form);
				return getActionForward("cashierPrint", mapping, form, request, response);
			}else if("voucher".equals(pageType)){
				request.setAttribute("form",form);
				request.setAttribute("fdModelName",FsscExpenseMain.class.getName());
				return getActionForward("voucherPrint", mapping, form, request, response);
			}else{
				return getActionForward("expenseArch", mapping, form, request,
						response);
			}
		}

	}
	/*
		*//**
	 * 映翰通退件码查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	public ActionForward codeQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		String fdId = request.getParameter("params");
		FsscExpenseMain main = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(fdId, null, true);
		try {
			String resultMsg = getFsscCommonInhandService().codeQuery(main.getFdCompany() !=null? main.getFdCompany().getFdId():"", main.getDocNumber());
			json.put("result", true);
			json.put("massege", resultMsg);
		} catch (Exception e) {
			json.put("result", false);
			json.put("massege", "failure");
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
	*/
	private static ISysOrgElementDao sysOrgElementDao;

	public static ISysOrgElementDao getSysOrgElementDao() {
		if (sysOrgElementDao == null) {
			sysOrgElementDao = (ISysOrgElementDao) SpringBeanUtil.getBean("sysOrgElementDao");
		}
		return sysOrgElementDao;
	}
	
	private static IFsscBudgetDataService fsscBudgetDataService;

	public static IFsscBudgetDataService getFsscBudgetDataService() {
		if (fsscBudgetDataService == null) {
			fsscBudgetDataService = (IFsscBudgetDataService) SpringBeanUtil.getBean("fsscBudgetDataService");
		}
		return fsscBudgetDataService;
	}
	 public void findParentsNameById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	String fdId=request.getParameter("fdId");
	    	String fdParentsName="无预算部门";
			try {
				if(StringUtil.isNotNull(fdId)){
					SysOrgElement element= (SysOrgElement) getSysOrgElementDao().findByPrimaryKey(fdId);
					fdParentsName=(String) HrFunctions.getDeptHaveBudget(element);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(fdParentsName);
	    }
}
