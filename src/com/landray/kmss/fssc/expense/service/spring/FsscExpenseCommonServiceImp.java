package com.landray.kmss.fssc.expense.service.spring;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataBerth;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataPayWay;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.eop.basedata.service.IEopBasedataExchangeRateService;
import com.landray.kmss.eop.basedata.service.IEopBasedataStandardService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetMatchService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonExpenseService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonFeeService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLedgerService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.expense.forms.FsscExpenseMainForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseAccounts;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseInvoiceDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseItemConfig;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.model.FsscExpenseTravelDetail;
import com.landray.kmss.fssc.expense.service.IFsscExpenseCategoryService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.workflow.engine.WorkflowEngineContext;
import com.landray.kmss.sys.workflow.interfaces.ISysWfProcessSubService;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscExpenseCommonServiceImp implements IFsscCommonExpenseService{
	private IEopBasedataExchangeRateService eopBasedataExchangeService;
	public IEopBasedataExchangeRateService getEopBasedataExchangeService() {
		if (eopBasedataExchangeService == null) {
			eopBasedataExchangeService = (IEopBasedataExchangeRateService) SpringBeanUtil.getBean("eopBasedataExchangeRateService");
		}
		return eopBasedataExchangeService;
	}
	private IFsscCommonFeeService fsscCommonFeeService;
	public IFsscCommonFeeService getFsscCommonFeeService() {
		if (fsscCommonFeeService == null) {
			fsscCommonFeeService = (IFsscCommonFeeService) SpringBeanUtil.getBean("fsscCommonFeeService");
		}
		return fsscCommonFeeService;
	}
	private IFsscExpenseMainService fsscExpenseMainService;

	public void setFsscExpenseMainService(IFsscExpenseMainService fsscExpenseMainService) {
		this.fsscExpenseMainService = fsscExpenseMainService;
	}
	public ICoreOuterService dispatchCoreService;

	public ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) SpringBeanUtil.getBean("dispatchCoreService");
		}
		return dispatchCoreService;
	}
	
	protected IBackgroundAuthService backgroundAuthService;
	
	public IBackgroundAuthService getBackgroundAuthService() {
		if (backgroundAuthService == null) {
			backgroundAuthService = (IBackgroundAuthService) SpringBeanUtil.getBean("backgroundAuthService");
		}
		return backgroundAuthService;
	}
	
	public ISysWfProcessSubService sysWfProcessSubService;
	
	public ISysWfProcessSubService getSysWfProcessSubService() {
		if (sysWfProcessSubService == null) {
			sysWfProcessSubService = (ISysWfProcessSubService) SpringBeanUtil.getBean("sysWfProcessSubService");
		}
		return  sysWfProcessSubService;
	}
	private IFsscCommonBudgetMatchService fsscCommonBudgetService;

	public IFsscCommonBudgetMatchService getFsscCommonBudgetService() {
		if (fsscCommonBudgetService == null) {
			fsscCommonBudgetService = (IFsscCommonBudgetMatchService) SpringBeanUtil.getBean("fsscBudgetMatchService");
        }
		return fsscCommonBudgetService;
	}
	private IEopBasedataStandardService eopBasedataStandardService;

    public IEopBasedataStandardService getEopBasedataStandardService() {
    	if (eopBasedataStandardService == null) {
    		eopBasedataStandardService = (IEopBasedataStandardService) SpringBeanUtil.getBean("eopBasedataStandardService");
		}
		return eopBasedataStandardService;
	}
    
    private IFsscExpenseCategoryService fsscExpenseCategoryService;

    public IFsscExpenseCategoryService getFsscExpenseCategoryService() {
 	   if(fsscExpenseCategoryService==null){
 		   fsscExpenseCategoryService = (IFsscExpenseCategoryService) SpringBeanUtil.getBean("fsscExpenseCategoryService");
 	   }
 		return fsscExpenseCategoryService;
 	}
    
    private IFsscCommonLedgerService fsscCommonLedgerService;
    
    public IFsscCommonLedgerService getFsscCommonLedgerService(){
  	   if(fsscCommonLedgerService==null){
  		 fsscCommonLedgerService = (IFsscCommonLedgerService)SpringBeanUtil.getBean("fsscCommonLedgerService");
  	   }
  		return fsscCommonLedgerService;
  	}


    @Override
   	public List<Object> getExpenseCategoryId(String fdFeeTemplateId) throws Exception {
   		String hql = "select cate.fdId from "+FsscExpenseCategory.class.getName()+" cate where cate.fdFeeTemplateId like :fdFeeTemplateId";
   		Query query = fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery(hql);
   		query.setParameter("fdFeeTemplateId", "%"+fdFeeTemplateId+"%");
   		List<Object> list=query.list();
   		if(ArrayUtil.isEmpty(list)){
   			String hqlStr= "select cate.fdId from "+FsscExpenseCategory.class.getName()+" cate where cate.fdIsFee='1' and cate.fdFeeTemplateId not like :fdFeeTemplateId ";
   			Query queryStr= fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery(hqlStr);
   			queryStr.setParameter("fdFeeTemplateId", "%"+fdFeeTemplateId+"%");
   			List<Object> slist=queryStr.list();
   			if(!ArrayUtil.isEmpty(slist)){
   				list.add("noSelect");
   			}
   		}
   		return list;
   	}

	@Override
	public String getFeeExpenseId(String fdFeeId) throws Exception {
		String hql = "select main.fdId from "+FsscExpenseMain.class.getName()+" main where main.fdFeeIds like :fdFeeId";
		Query query = fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdFeeId", "%"+fdFeeId+"%");
		query.setMaxResults(1);
		return (String) query.uniqueResult();
	}

	@Override
	public Map<String, Object> getExpenseInfo(String fdModelId,String fdModelName) throws Exception {
		Map<String,Object> rtnMap=new ConcurrentHashMap<>();
		FsscExpenseMain main=(FsscExpenseMain) fsscExpenseMainService.findByPrimaryKey(fdModelId);
		if(main!=null){
			rtnMap.put("fdPaymentStatus", main.getFdPaymentStatus()!=null?main.getFdPaymentStatus():""); //付款状态
			//获取附加选项
			IExtendForm extendForm=null;
			FsscExpenseMainForm mainForm=(FsscExpenseMainForm) fsscExpenseMainService.convertModelToForm(extendForm, main, new RequestContext());
			Map nodeInfo=mainForm.getSysWfBusinessForm().getFdNodeAdditionalInfo();
			if(!nodeInfo.isEmpty()){
				rtnMap.put("examine",nodeInfo.containsKey("examine")?nodeInfo.get("examine"):""); //流程财务审核附加选项信息
				rtnMap.put("confirmPayment",nodeInfo.containsKey("confirmPayment")?nodeInfo.get("confirmPayment"):""); //流程确认付款附加选项信息
			}
		}
		
		return rtnMap;
	}

	@Override
	public Boolean isMonthStandardUsed(String fdPersonId, String fdExpenseItemId, Date fdHappenDate,String fdDetailId) throws Exception {
		Calendar start = Calendar.getInstance();
		start.setTime(fdHappenDate);
		start.set(Calendar.DAY_OF_MONTH, 1);
		String hql = "from "+FsscExpenseDetail.class.getName()+" where fdHappenDate between :fdStartDate and :fdEndDate and fdRealUser.fdId=:fdPersonId and fdExpenseItem.fdId=:fdExpenseItemId and fdId<>:fdDetailId and (docMain.docStatus=:docStatus20 or docMain.docStatus=:docStatus30)";
		Query query = fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdDetailId", fdDetailId);
		query.setParameter("fdPersonId",fdPersonId);
		query.setParameter("fdExpenseItemId",fdExpenseItemId);
		query.setParameter("docStatus20",SysDocConstant.DOC_STATUS_EXAMINE);
		query.setParameter("docStatus30",SysDocConstant.DOC_STATUS_PUBLISH);
		query.setParameter("fdStartDate",start.getTime());
		Calendar end = Calendar.getInstance();
		end.setTime(fdHappenDate);
		end.set(Calendar.DAY_OF_MONTH, 1);
		end.add(Calendar.MONTH, 1);
		end.add(Calendar.DATE, -1);
		query.setParameter("fdEndDate", end.getTime());
		List<FsscExpenseDetail> details = query.list();
		return !ArrayUtil.isEmpty(details);
	}

	@Override
	public List<EopBasedataExpenseItem> getExpenseItemByCategory(String fdCompanyId, String docCategoryId)
			throws Exception {
		String hql = "from "+FsscExpenseItemConfig.class.getName()+" main where main.fdCompany.fdId=:fdCompanyId and main.fdCategory.fdId=:docCategoryId ";
		Query query = fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdCompanyId", fdCompanyId);
		query.setParameter("docCategoryId", docCategoryId);
		List<FsscExpenseItemConfig> list = query.list();
		List<EopBasedataExpenseItem> rtn = new ArrayList<EopBasedataExpenseItem>();
		for(FsscExpenseItemConfig config:list){
			if(!ArrayUtil.isEmpty(config.getFdItemList())){
				ArrayUtil.concatTwoList(config.getFdItemList(), rtn);
			}
		}
		return rtn;
	}
	@Override
	public List<String> saveExpenseFromObject(JSONObject object) throws Exception {
		List<String> rtn = new ArrayList<String>();
		FsscExpenseMain main = null;
		String fdId = object.getString("fdId");
		Boolean canUpdate = true;
		try {
			main = (FsscExpenseMain) fsscExpenseMainService.findByPrimaryKey(fdId, FsscExpenseMain.class, true);
		} catch (Exception e1) {
		}
		if (main == null) {
			main = new FsscExpenseMain();
			main.setFdId(fdId);
			canUpdate = false;
		}
		SysOrgPerson user = UserUtil.getUser();
		if (object.containsKey("docStatus")) {
			main.setDocStatus(object.getString("docStatus"));
		} else {
			main.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		}
		main.setDocSubject(object.getString("docSubject"));
		main.setFdContent(object.getString("fdDesc"));  //报销事由
		main.setDocCreateTime(new Date());
		main.setFdClaimant(user);
		main.setDocCreator(user);
		main.setFdClaimantDept(user.getFdParent());
		String fdCostCenterId = object.getString("fdCostCenterId");
		EopBasedataCostCenter cost = (EopBasedataCostCenter) fsscExpenseMainService.findByPrimaryKey(fdCostCenterId,
				EopBasedataCostCenter.class, true);
		main.setFdCostCenter(cost);
		String fdCompanyId = object.getString("fdCompanyId");
		EopBasedataCompany comp = (EopBasedataCompany) fsscExpenseMainService.findByPrimaryKey(fdCompanyId,
				EopBasedataCompany.class, true);
		main.setFdCompany(comp);
		String docCategoryId = object.getString("docCategoryId");
		final FsscExpenseCategory cate = (FsscExpenseCategory) fsscExpenseMainService.findByPrimaryKey(docCategoryId,
				FsscExpenseCategory.class, true);
		main.setDocTemplate(cate);
		EopBasedataProject projectBudget = null;
		if(object.containsKey("fdProjectId")){
			String fdProjectId = object.getString("fdProjectId");
			projectBudget = (EopBasedataProject) fsscExpenseMainService.findByPrimaryKey(fdProjectId, EopBasedataProject.class, true);
			if(projectBudget!=null){
				main.setFdProject(projectBudget);
			}
		}
		if(object.containsKey("fdFeeMainId")){
			if(FsscCommonUtil.checkHasModule("/fssc/fee/")){
				String fdFeeMainId = object.getString("fdFeeMainId");
				if(StringUtil.isNotNull(fdFeeMainId)){
					JSONObject fee = getFsscCommonFeeService().getFeeInfoById(fdFeeMainId);
					if(fee.containsKey("docSubject")){
						main.setFdFeeIds(fdFeeMainId);
						main.setFdFeeNames(fee.getString("docSubject"));
					}
				}
			}
		}
		JSONArray detailData = object.getJSONArray("detailJson");
		Double totalMoney = 0d;
		FsscExpenseDetail project = null;
		//travel
		if("2".equals(main.getDocTemplate().getFdExpenseType())){
			JSONArray travelList = object.getJSONArray("fdTravelList");
			List<FsscExpenseTravelDetail> fdTravelList = new ArrayList<FsscExpenseTravelDetail>();
			for(int k=0;k<travelList.size();k++){
				JSONObject acc = travelList.getJSONObject(k);
				FsscExpenseTravelDetail travel = new FsscExpenseTravelDetail();
				if(canUpdate){
					travel = (FsscExpenseTravelDetail) fsscExpenseMainService.findByPrimaryKey(acc.getString("fdId"),
							FsscExpenseTravelDetail.class, true);
				}
				travel.setFdId(acc.getString("fdId"));
				String fdPersonListIds = acc.getString("fdPersonListIds");
				if(StringUtil.isNotNull(fdPersonListIds)){
					List<SysOrgPerson> persons = fsscExpenseMainService.getBaseDao().getHibernateSession()
							.createQuery("from "+SysOrgPerson.class.getName()+" where fdId in(:ids) and fdIsAvailable=:fdIsAvailable")
							.setParameter("fdIsAvailable", true).setParameterList("ids", Arrays.asList(fdPersonListIds.split(";"))).list();
					travel.setFdPersonList(persons);
				}
				travel.setFdStartPlace(acc.getString("fdStartPlaceName"));
				travel.setFdArrivalPlace(acc.getString("fdArrivalPlaceName"));
				travel.setFdArrivalId(acc.getString("fdArrivalPlaceId").split("__")[1]);
				travel.setFdBeginDate(DateUtil.convertStringToDate(acc.getString("fdBeginDate"), DateUtil.PATTERN_DATE));
				travel.setFdEndDate(DateUtil.convertStringToDate(acc.getString("fdEndDate"), DateUtil.PATTERN_DATE));
				travel.setFdTravelDays(acc.getInt("fdTravelDays"));
				travel.setFdSubject(acc.getString("fdSubject"));
				if(acc.containsKey("fdBerthId")){
					String fdBerthId = acc.getString("fdBerthId");
					EopBasedataBerth b = (EopBasedataBerth) fsscExpenseMainService.findByPrimaryKey(fdBerthId, EopBasedataBerth.class, true);
					if(b!=null){
						travel.setFdBerth(b);
						travel.setFdVehicle(b.getFdVehicle());
					}
				}
				fdTravelList.add(travel);
			}
			if(main.getFdTravelList()==null){
				main.setFdTravelList(fdTravelList);
			}else{
				main.getFdTravelList().clear();
				main.getFdTravelList().addAll(fdTravelList);
			}
		}
		
		//invoice
		JSONArray invoiceList = object.getJSONArray("fdInvoiceList");
		List<FsscExpenseInvoiceDetail> fdInvoiceList = new ArrayList<FsscExpenseInvoiceDetail>();
		for(int k=0;k<invoiceList.size();k++){
			JSONObject acc = invoiceList.getJSONObject(k);
			FsscExpenseInvoiceDetail inv = new FsscExpenseInvoiceDetail();
			if(canUpdate){
				inv = (FsscExpenseInvoiceDetail) fsscExpenseMainService.findByPrimaryKey(acc.getString("fdId"),
						FsscExpenseInvoiceDetail.class, true);
			}
			inv.setFdId(acc.getString("fdId"));
			fdCompanyId = acc.getString("fdCompanyId");
			inv.setFdCompany((EopBasedataCompany) fsscExpenseMainService.findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true));
			String fdExpenseItemId = acc.getString("fdExpenseItemId");
			inv.setFdExpenseType((EopBasedataExpenseItem) fsscExpenseMainService.findByPrimaryKey(fdExpenseItemId, EopBasedataExpenseItem.class, true));
			inv.setFdIsVat(acc.getBoolean("fdVatInvoice"));
			String fdInvoiceNumber=acc.getString("fdInvoiceNo");
			inv.setFdInvoiceNumber(fdInvoiceNumber);
			String fdInvoiceCode=acc.getString("fdInvoiceCode");
			String invoiceType=null;
			String status=null;
			JSONObject invoice=getFsscCommonLedgerService().getFsscLedgerInvoiceDetail(fdInvoiceCode, fdInvoiceNumber);
			if(!invoice.isEmpty()){
				invoiceType=invoice.getString("invoiceType");
				if(invoiceType!=null && invoiceType.length()!= 0){
				inv.setFdInvoiceType(invoiceType);
				}
				status=invoice.getString("checkStatus");
				if(status!=null&&status.length()!=0){
					inv.setFdCheckStatus(status);	
				}else{
					inv.setFdCheckStatus(String.valueOf(0));
				}
			}else{
				 inv.setFdCheckStatus(String.valueOf(0));
			}
		   
			if(fdInvoiceCode!=null&& fdInvoiceCode.length()!= 0){
				inv.setFdInvoiceCode(fdInvoiceCode);
			}
			
			inv.setFdInvoiceMoney(acc.getDouble("fdInvoiceMoney"));
			inv.setFdTax(acc.getDouble("fdTaxValue"));
			inv.setFdTaxMoney(acc.getDouble("fdTax"));
			inv.setFdNoTaxMoney(acc.getDouble("fdNoTax"));
			inv.setFdInvoiceDate(DateUtil.convertStringToDate(acc.getString("fdInvoiceDate"), DateUtil.PATTERN_DATE));
			fdInvoiceList.add(inv);
		}
		if(main.getFdInvoiceList()==null){
			main.setFdInvoiceList(fdInvoiceList);
		}else{
			main.getFdInvoiceList().clear();
			main.getFdInvoiceList().addAll(fdInvoiceList);
		}
		//detail
		List<FsscExpenseDetail> details = new ArrayList<FsscExpenseDetail>();
		Double totalStandard = 0d;
		for (int i = 0; i < detailData.size(); i++) {
			project = null;
			JSONObject detail = detailData.getJSONObject(i);
			if(canUpdate){
				project = (FsscExpenseDetail) fsscExpenseMainService.findByPrimaryKey(detail.getString("fdId"),
						FsscExpenseDetail.class, true);
			}
			Double fdMoney = 0d;
			if (project == null) {
				project = new FsscExpenseDetail();
			}
			String fdRealUserId = detail.getString("fdRealUserId");
			SysOrgPerson fdRealUser = (SysOrgPerson) fsscExpenseMainService.findByPrimaryKey(fdRealUserId, SysOrgPerson.class, true);
			if(detail.containsKey("fdDeptId")&&StringUtil.isNotNull(detail.getString("fdDeptId"))){
				SysOrgElement dept = (SysOrgElement) fsscExpenseMainService.findByPrimaryKey(detail.getString("fdDeptId"), SysOrgElement.class, true);
				project.setFdDept(dept);
			}
			project.setFdHappenDate(DateUtil.convertStringToDate(detail.getString("fdHappenDate"), DateUtil.PATTERN_DATE));
			String fdCurrencyId = detail.getString("fdCurrencyId");
			if(StringUtil.isNotNull(fdCurrencyId)){
				EopBasedataCurrency cu = (EopBasedataCurrency) fsscExpenseMainService.findByPrimaryKey(fdCurrencyId,EopBasedataCurrency.class, true);
				project.setFdCurrency(cu);
				Double budgetRate = getEopBasedataExchangeService().getBudgetRate(fdCurrencyId, main.getFdCompany().getFdId());
				Double exhcangeRate = getEopBasedataExchangeService().getExchangeRate(fdCurrencyId, main.getFdCompany().getFdId());
				project.setFdExchangeRate(exhcangeRate);
				project.setFdBudgetRate(budgetRate);
			}else{
				project.setFdCurrency(main.getFdCompany().getFdAccountCurrency());
				project.setFdExchangeRate(1d);
				project.setFdBudgetRate(1d);
			}
			if(detail.containsKey("fdPersonNumber")){
				String fdPersonNumber = detail.getString("fdPersonNumber");
				if(StringUtil.isNotNull(fdPersonNumber)){
					project.setFdPersonNumber(Integer.parseInt(fdPersonNumber));
				}
			}
			// 金额
			if (detail.containsKey("fdMoney")) {
				fdMoney = detail.getDouble("fdMoney");
				project.setFdStandardMoney(FsscNumberUtil.getMultiplication(fdMoney, project.getFdExchangeRate(),2));
				project.setFdApprovedApplyMoney(fdMoney);
				project.setFdApplyMoney(fdMoney);
				project.setFdBudgetMoney(FsscNumberUtil.getMultiplication(fdMoney, project.getFdBudgetRate(),2));
			}
			String fdExpenseItemId = detail.getString("fdExpenseItemId");
			EopBasedataExpenseItem item = (EopBasedataExpenseItem) fsscExpenseMainService.findByPrimaryKey(fdExpenseItemId, EopBasedataExpenseItem.class, true);
			totalMoney = FsscNumberUtil.getAddition(totalMoney, fdMoney,2);
			fdCostCenterId = detail.getString("fdCostCenterId");
			cost = (EopBasedataCostCenter) fsscExpenseMainService.findByPrimaryKey(fdCostCenterId,
					EopBasedataCostCenter.class, true);
			if(cost==null){
				cost = main.getFdCostCenter();
			}
			project.setFdCompany(main.getFdCompany());
			project.setFdCostCenter(cost);
			project.setFdRealUser(fdRealUser);
			project.setFdExpenseItem(item);
			if (detail.containsKey("fdDesc")) {
				project.setFdUse(detail.getString("fdDesc"));
			}
			if(detail.containsKey("fdTravel")){
				project.setFdTravel(detail.getString("fdTravel"));
			}
			if(detail.containsKey("fdWbsId")){
				String fdWbsId = detail.getString("fdWbsId");
				EopBasedataWbs wbs = (EopBasedataWbs) fsscExpenseMainService.findByPrimaryKey(fdWbsId, EopBasedataWbs.class, true);
				project.setFdWbs(wbs);
			}
			if(detail.containsKey("fdInnerOrderId")){
				String fdInnerOrderId = detail.getString("fdInnerOrderId");
				EopBasedataInnerOrder fdInnerOrder = (EopBasedataInnerOrder) fsscExpenseMainService.findByPrimaryKey(fdInnerOrderId, EopBasedataInnerOrder.class, true);
				project.setFdInnerOrder(fdInnerOrder);
			}
			if(detail.containsKey("fdIsDeduct")){
				Boolean fdIsDeduct = detail.getBoolean("fdIsDeduct");
				project.setFdIsDeduct(fdIsDeduct);
				if(fdIsDeduct){
					Double fdInputTax = detail.getDouble("fdInputTax");
					project.setFdInputTaxMoney(fdInputTax);
				}
			}
			if(detail.containsKey("fdTravel")){
				project.setFdTravel(detail.getString("fdTravel"));
			}
			if(detail.containsKey("fdBudgetInfo")){
				project.setFdBudgetInfo(detail.getString("fdBudgetInfo"));
			}else{
				project.setFdBudgetInfo("[]");
			}
			if(detail.containsKey("fdFeeInfo")){
				project.setFdFeeInfo(detail.getString("fdFeeInfo"));
				project.setFdFeeStatus(detail.getString("fdFeeStatus"));
			}else{
				project.setFdFeeInfo("[]");
				project.setFdFeeStatus("0");
			}
			if(detail.containsKey("fdBudgetStatus")){
				project.setFdBudgetStatus(detail.getString("fdBudgetStatus"));
			}else{
				project.setFdBudgetStatus("0");
			}
			if(detail.containsKey("fdStandardStatus")){
				project.setFdStandardStatus(detail.getString("fdStandardStatus"));
			}else{
				project.setFdStandardStatus("0");
			}
			project.setFdId(null);
			details.add(project);
			totalStandard+=project.getFdStandardMoney();
			if(detail.containsKey("fdNoteId")&&StringUtil.isNotNull(detail.getString("fdNoteId"))){
				fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery("update FsscMobileNote set fdExpenseDetailId=:fdExpenseDetailId where fdId=:fdId").setParameter("fdExpenseDetailId", project.getFdId()).setParameter("fdId", detail.getString("fdNoteId")).executeUpdate();
			}
			
			if(detail.containsKey("fdArrivalPlaceId")&&StringUtil.isNotNull(detail.getString("fdArrivalPlaceId"))){
				String[] fdArrivalPlace = detail.getString("fdArrivalPlaceId").split("__");
				project.setFdArrivalPlace(fdArrivalPlace[0]);
				project.setFdArrivalPlaceId(fdArrivalPlace[1]);
			}
			if(detail.containsKey("fdStartPlaceId")&&StringUtil.isNotNull(detail.getString("fdStartPlaceId"))){
				String[] fdStartPlace = detail.getString("fdStartPlaceId").split("__");
				project.setFdStartPlace(fdStartPlace[0]);
				project.setFdStartPlaceId(fdStartPlace[1]);
			}
			if(detail.containsKey("fdBeginDate")&&StringUtil.isNotNull(detail.getString("fdBeginDate"))){
				Date fdBeginDate = DateUtil.convertStringToDate(detail.getString("fdBeginDate"), DateUtil.PATTERN_DATE);
				project.setFdStartDate(fdBeginDate);
			}
			if(detail.containsKey("fdHappenDate")&&StringUtil.isNotNull(detail.getString("fdHappenDate"))){
				Date fdHappenDate = DateUtil.convertStringToDate(detail.getString("fdHappenDate"), DateUtil.PATTERN_DATE);
				project.setFdHappenDate(fdHappenDate);
			}
			if(detail.containsKey("fdTravelDays")&&StringUtil.isNotNull(detail.getString("fdTravelDays"))){
				project.setFdTravelDays(detail.getInt("fdTravelDays"));
			}
			if(detail.containsKey("fdBerthId")&&StringUtil.isNotNull(detail.getString("fdBerthId"))){
				EopBasedataBerth eopBasedataBerth =(EopBasedataBerth)fsscExpenseMainService.findByPrimaryKey(detail.getString("fdBerthId"), EopBasedataBerth.class, true);
				project.setFdBerth(eopBasedataBerth);
			}
		}
		if (main.getFdDetailList() != null) {
			main.getFdDetailList().clear();
			main.getFdDetailList().addAll(details);
		} else {
			main.setFdDetailList(details);
		}
		main.setFdIsOffsetLoan(false);
		main.setFdTotalApprovedMoney(totalMoney);
		main.setFdTotalStandaryMoney(totalStandard);
		List<FsscExpenseAccounts> accountList = new ArrayList<FsscExpenseAccounts>();
		FsscExpenseAccounts account = new FsscExpenseAccounts();
		JSONArray arr = object.getJSONArray("accountJson");
		for(int k=0;k<arr.size();k++){
			JSONObject acc = arr.getJSONObject(k);
			String id = acc.getString("fdId");
			if(canUpdate){
				account = (FsscExpenseAccounts) fsscExpenseMainService.findByPrimaryKey(id, FsscExpenseAccounts.class, true);
			}else{
				account = new FsscExpenseAccounts();
			}
			String fdPayWayId = acc.getString("fdPayId");
			EopBasedataPayWay p= (EopBasedataPayWay) fsscExpenseMainService.findByPrimaryKey(fdPayWayId, EopBasedataPayWay.class, true);
			account.setFdPayWay(p);
			if(p!=null){
				account.setFdBank(p.getFdDefaultPayBank());
			}
			account.setFdAccountName(acc.getString("fdAccountName"));
			account.setFdBankName(acc.getString("fdBank"));
			account.setFdBankAccount(acc.getString("fdBankAccount"));
			account.setFdMoney(totalMoney > 0 ? totalMoney : 0);
			account.setFdCurrency(main.getFdCompany().getFdAccountCurrency());
			account.setFdExchangeRate(1d);
			accountList.add(account);
		}
		if(main.getFdAccountsList()!=null){
			main.getFdAccountsList().clear();
			main.getFdAccountsList().addAll(accountList);
		} else {
			main.setFdAccountsList(accountList);
		}
		if (canUpdate) {
			fsscExpenseMainService.update(main);
		} else {
			fsscExpenseMainService.add(main);
			getDispatchCoreService().initModelSetting(main, "fsscExpenseMain", cate, "fsscExpenseMain");
			getBackgroundAuthService().switchUserById(user.getFdId(), new Runner() {
				@Override
                public Object run(Object parameter) throws Exception {
					WorkflowEngineContext subContext = getSysWfProcessSubService().init(
							(IBaseModel) parameter, "fsscExpenseMain", (IBaseModel) parameter, "fsscExpenseMain");
					getSysWfProcessSubService().doAction(subContext, (IBaseModel) parameter);
					return null;
				}
			}, main);
		}
		//更新附件关联
		JSONArray attachments = object.getJSONArray("attachments");
		List<String> attIds = new ArrayList<String>();
		for(int i=0;i<attachments.size();i++){
			JSONObject att = attachments.getJSONObject(i);
			attIds.add(att.getString("value"));
		}
		if(attIds.size()>0){
			fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery("update SysAttMain set fdModelId=:fdModelId,fdModelName=:fdModelName where fdId in(:ids)")
			.setParameterList("ids", attIds).setParameter("fdModelId", main.getFdId()).setParameter("fdModelName", FsscExpenseMain.class.getName()).executeUpdate();
		}
		return rtn;
	}

	@Override
	public JSONArray getMyExpenseList(String flag, String type) throws Exception {
		SysOrgElement user = UserUtil.getUser();
		HQLInfo hqlInfo = new HQLInfo();
		StringBuilder where = new StringBuilder("1=1 ");
		// flag-->0:已提交，1：未提交
		// type-->personal:个人报销,project:项目报销,view:我的审批

		if ("view".equals(type)) {
			if ("0".equals(flag)) {
				SysFlowUtil.buildLimitBlockForMyApproval("fsscExpenseMain", hqlInfo);
			} else {
				SysFlowUtil.buildLimitBlockForMyApproved("fsscExpenseMain", hqlInfo);
			}
		} else {
			if ("personal".equals(type)) {
				where.append("and fsscExpenseMain.docTemplate.fdMustSelect is null ");
			} else {
				where.append("and fsscExpenseMain.docTemplate.fdMustSelect is not null ");
			}
			if ("0".equals(flag)) {
				where.append(
						"and fsscExpenseMain.docStatus <>:docStatus and fsscExpenseMain.docStatus <>:docStatus1 ");
				hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_DRAFT);
				hqlInfo.setParameter("docStatus1",
						SysDocConstant.DOC_STATUS_REFUSE);
			} else {
				where.append(
						"and (fsscExpenseMain.docStatus = :docStatus or fsscExpenseMain.docStatus = :docStatus1) ");
				hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_DRAFT);
				hqlInfo.setParameter("docStatus1",
						SysDocConstant.DOC_STATUS_REFUSE);
			}
			where.append("and fsscExpenseMain.docCreator.fdId=:fdId ");
			hqlInfo.setParameter("fdId", user.getFdId());
			hqlInfo.setWhereBlock(where.toString());
		}
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		hqlInfo.setOrderBy(" fsscExpenseMain.docCreateTime desc");
		StringBuilder hql=new StringBuilder();
		hql.append("select fsscExpenseMain from FsExpenseMain fsscExpenseMain where ").append(hqlInfo.getWhereBlock());
		Query query=fsscExpenseMainService.getBaseDao().getHibernateSession().createNativeQuery(hql.toString());
		List<HQLParameter> paramList=hqlInfo.getParameterList();
		for(HQLParameter param:paramList){
			query.setParameter(param.getName(), param.getValue());
		}
		List<FsscExpenseMain> list = query.list();
		JSONArray arr = new JSONArray();
		for (FsscExpenseMain main : list) {
			JSONObject object = new JSONObject();
			object.put("id", main.getFdId());
			object.put("fdId", main.getFdId());
			object.put("name", main.getDocSubject());
			object.put("fdNumber", main.getDocNumber());
			object.put("personName", main.getDocCreator().getFdName());
			object.put("deptName", main.getFdClaimantDept() == null ? "" : main.getFdClaimantDept().getFdName());
			object.put("createTime", DateUtil.convertDateToString(main.getDocCreateTime(), DateUtil.PATTERN_DATE));
			object.put("docCategoryId", main.getDocTemplate().getFdId());
			object.put("status",ResourceUtil.getString("enums.doc_status."+main.getDocStatus(), "fssc-expense"));
			arr.add(object);
		}
		return arr;
	}

	@Override
	public JSONArray getMyExpenseList(String fdPersonId) throws Exception {
		JSONArray data = new JSONArray();
		String hql = "select kmss_tmp_fsscExpenseMain from com.landray.kmss.fssc.expense.model.FsscExpenseMain kmss_tmp_fsscExpenseMain where kmss_tmp_fsscExpenseMain.fdId " + 
				"in(select fsscExpenseMain.fdId from FsscExpenseMain fsscExpenseMain left join fsscExpenseMain.authAllReaders kmss_auth_field_0 left join fsscExpenseMain.docTemplate.authEditors kmss_auth_field_3 " + 
				"left join fsscExpenseMain.docTemplate.authTmpEditors kmss_auth_field_6 left join fsscExpenseMain.docTemplate.authTmpReaders kmss_auth_field_9 " + 
				"left join fsscExpenseMain.fdClaimant kmss_auth_field_12 where (kmss_auth_field_0.fdId in (:orgId0)) or " + 
				"(kmss_auth_field_3.fdId in (:orgId1)) or (kmss_auth_field_6.fdId in (:orgId2)) or " + 
				"(kmss_auth_field_9.fdId in (:orgId3)) or (kmss_auth_field_12.fdId=:fdPersonId) and fsscExpenseMain.docDeleteFlag = 0 )  ";
		hql+=" order by kmss_tmp_fsscExpenseMain.fdId desc";
		Query query=fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery(hql);
		List orgIds=UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		query.setParameterList("orgId0",orgIds);
		query.setParameterList("orgId1",orgIds);
		query.setParameterList("orgId2", orgIds);
		query.setParameterList("orgId3", orgIds);
		query.setParameter("fdPersonId", fdPersonId);
		List<FsscExpenseMain> list = (List<FsscExpenseMain>)query.list(); 
		for(FsscExpenseMain main:list){
			JSONObject obj = new JSONObject();
			obj.put("title", main.getDocSubject());
			obj.put("date", DateUtil.convertDateToString(main.getDocCreateTime(), DateUtil.PATTERN_DATE));
			obj.put("status", EnumerationTypeUtil.getColumnEnumsLabel("common_status", main.getDocStatus()));
			obj.put("clazz", "status"+main.getDocStatus());
			obj.put("company", main.getFdCompany()==null?"":main.getFdCompany().getFdName());
			obj.put("dept", main.getFdCostCenter()!=null?main.getFdCostCenter().getFdName():"");
			try {
				obj.put("count", new DecimalFormat("0.00#").format(Double.valueOf(main.getFdTotalApprovedMoney())));
			} catch (Exception e) {
				obj.put("count","0.00");
			}
			obj.put("id", main.getFdId());
			obj.put("link", "/expense/detailView?id="+main.getFdId());
			data.add(obj);
		}
		return data;
	}

	@Override
	public JSONObject getExpenseById(String fdId) throws Exception {
		JSONObject data = new JSONObject();
		JSONArray fdDetailList = new JSONArray();
		FsscExpenseMain main = (FsscExpenseMain)fsscExpenseMainService.getBaseDao().findByPrimaryKey(fdId, FsscExpenseMain.class, true);
		data.put("docSubject", main.getDocSubject());
		data.put("docContent", main.getFdContent());
		data.put("fdCompanyName", main.getFdCompany().getFdName());
		data.put("fdCostCenterName", main.getFdCostCenter().getFdName());
		data.put("fdProjectName", main.getFdProject()==null?"":main.getFdProject().getFdName());
		data.put("fdFeeMainName", main.getFdFeeNames());
		data.put("fdMoney", main.getFdTotalStandaryMoney());
		List<FsscExpenseAccounts> accountList=main.getFdAccountsList();
		if(!ArrayUtil.isEmpty(accountList)){
			JSONArray accountData=new JSONArray();
			for (FsscExpenseAccounts account : accountList) {
				JSONObject accountObj=new JSONObject();
				String name = account.getFdBank()==null?"":account.getFdBank().getFdBankName();
				String type="3";  //默认银行卡样式
				if (name.contains("中国银行") || name.contains("中行")) {
					type = "0";
				} else if (name.contains("农业银行") || name.contains("农行")) {
					type = "1";
				} else if (name.contains("建设银行") || name.contains("建行")) {
					type = "2";
				}
				accountObj.put("type", type);
				accountObj.put("title", account.getFdBankAccount());
				accountObj.put("name", account.getFdMoney());
				accountObj.put("fdPayment", account.getFdMoney());
				accountObj.put("fdAccountName", account.getFdAccountName());
				accountObj.put("fdBankAccount", account.getFdBankAccount());
				accountObj.put("fdBank", account.getFdBankName());
				accountData.add(accountObj);
			}
			data.put("accountData", accountData);
		}
		List<FsscExpenseDetail> details = main.getFdDetailList();
		int index = 0;
		for(FsscExpenseDetail detail:details){
			if(detail==null){
				continue;
			}
			JSONObject d = new JSONObject();
			d.put("index", (++index));
			EopBasedataExpenseItem item=detail.getFdExpenseItem();
			if(item.getFdName().contains("交通")){
				d.put("type", "taxi");
			}else if(item.getFdName().contains("住宿")){
				d.put("type", "hotel");
			}else if(item.getFdName().contains("机票")){
				d.put("type", "airplane");
			}else{
				d.put("type", "other");
			}
			d.put("fdExpenseItemName", item.getFdName());
			d.put("fdMoney", detail.getFdApprovedApplyMoney()!=null?detail.getFdApprovedApplyMoney():0.00);
			d.put("fdId", detail.getFdId());
			d.put("fdRealUserName", detail.getFdRealUser()!=null?detail.getFdRealUser().getFdName():"");
			d.put("fdExpenseItemName", detail.getFdExpenseItem()!=null?detail.getFdExpenseItem().getFdName():"");
			d.put("fdCostCenterName", detail.getFdCostCenter()!=null?detail.getFdCostCenter().getFdName():"");
			d.put("fdHappenDate", DateUtil.convertDateToString(detail.getFdHappenDate(), DateUtil.PATTERN_DATE));
			d.put("fdWbsName", detail.getFdWbs()==null?"":detail.getFdWbs().getFdName());
			d.put("fdInnerOrderName", detail.getFdInnerOrder()==null?"":detail.getFdInnerOrder().getFdName());
			d.put("fdIsDeduct", detail.getFdIsDeduct());
			d.put("fdCurrencyName", detail.getFdCurrency().getFdName());
			d.put("fdPersonNumber", detail.getFdPersonNumber());
			d.put("fdInputTax", detail.getFdInputTaxMoney());
			d.put("fdDesc", detail.getFdUse());
			d.put("fdTravel", detail.getFdTravel());
			d.put("fdDeptName", detail.getFdDept()==null?"":detail.getFdDept().getFdName());
			d.put("fdBudgetStatus", ResourceUtil.getString("py.budget."+detail.getFdBudgetStatus(),"fssc-expense"));
			d.put("fdStandardStatus", ResourceUtil.getString("py.standard."+detail.getFdStandardStatus(),"fssc-expense"));
			d.put("fdBeginDate", DateUtil.convertDateToString(detail.getFdStartDate(), DateUtil.PATTERN_DATE));
			d.put("fdTravelDays", detail.getFdTravelDays());
			d.put("fdStartPlace", detail.getFdStartPlace());
			d.put("fdArrivalPlace", detail.getFdArrivalPlace());
			d.put("fdBerthName", null==detail.getFdBerth()?"":detail.getFdBerth().getFdName());
			fdDetailList.add(d);
		}
		data.put("details", fdDetailList);
		List<SysAttMain> atts = fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery("from SysAttMain where fdModelId=:fdId").setParameter("fdId", fdId).list();
		JSONArray arr = new JSONArray();
		for(SysAttMain att:atts){
			JSONObject a = new JSONObject();
			a.put("title", att.getFdFileName());
			a.put("id", att.getFdId());
			arr.add(a);
		}
		data.put("attachments", arr);
		JSONArray fdTravelList = new JSONArray();
		index = 0;
		for(FsscExpenseTravelDetail t:main.getFdTravelList()){
			JSONObject d = new JSONObject();
			d.put("fdSubject", t.getFdSubject());
			d.put("fdStartPlace", t.getFdStartPlace());
			d.put("fdArrivalPlace", t.getFdArrivalPlace());
			d.put("fdArrivalPlaceId", t.getFdArrivalId());
			d.put("fdBeginDate", DateUtil.convertDateToString(t.getFdBeginDate(), DateUtil.PATTERN_DATE));
			d.put("fdEndDate", DateUtil.convertDateToString(t.getFdEndDate(), DateUtil.PATTERN_DATE));
			List<String> fdPersonListNames = new ArrayList<String>();
			for(SysOrgPerson p:t.getFdPersonList()){
				fdPersonListNames.add(p.getFdName());
			}
			d.put("fdPersonListNames", ArrayUtil.joinProperty(t.getFdPersonList(), "fdName", ";")[0]);
			d.put("fdVehicleName", t.getFdVehicle()==null?"":t.getFdVehicle().getFdName());
			d.put("fdBerthName", t.getFdBerth()==null?"":t.getFdBerth().getFdName());
			d.put("fdTravelDays", t.getFdTravelDays());
			fdTravelList.add(d);
		}
		data.put("fdTravelList", fdTravelList);
		JSONArray fdInvoiceList = new JSONArray();
		index = 0;
		for(FsscExpenseInvoiceDetail t:main.getFdInvoiceList()){
			JSONObject d = new JSONObject();
			d.put("index", (++index));
			if(t.getFdInvoiceDate()!=null){
				d.put("fdInvoiceDate", DateUtil.convertDateToString(t.getFdInvoiceDate(), DateUtil.PATTERN_DATE));
			}
			d.put("fdCompanyName", t.getFdCompany()==null?"":t.getFdCompany().getFdName());
			d.put("fdExpenseItemName", t.getFdExpenseType()==null?"":t.getFdExpenseType().getFdName());
			d.put("fdInvoiceNo", t.getFdInvoiceNumber());
			d.put("fdInvoiceCode", t.getFdInvoiceCode());
			d.put("fdTax", t.getFdTaxMoney());
			d.put("fdInvoiceMoney", t.getFdInvoiceMoney());
			d.put("fdVatInvoice", t.getFdIsVat());
			d.put("fdNoTax", t.getFdNoTaxMoney());
			d.put("fdTaxValue", t.getFdTax()+"%");
			fdInvoiceList.add(d);
		}
		data.put("fdInvoiceList", fdInvoiceList);
		FsscExpenseCategory cate = main.getDocTemplate();
		JSONObject categoryInfo = new JSONObject();
		categoryInfo.put("fdIsProject", cate.getFdIsProject());
		categoryInfo.put("fdIsFee", cate.getFdIsFee());
		categoryInfo.put("fdExpenseType", cate.getFdExpenseType());//报销类型
		categoryInfo.put("fdAllocType", cate.getFdAllocType());//报销模式
		categoryInfo.put("fdSubjectType", cate.getFdSubjectType());//主题生成方式
		String fdExtendField = ";"+cate.getFdExtendFields()+";";
		categoryInfo.put("fdPersonNumber", fdExtendField.indexOf(";1;")>-1);//招待人数
		categoryInfo.put("fdInputTax", fdExtendField.indexOf(";2;")>-1);//进项税额
		categoryInfo.put("fdWbs", fdExtendField.indexOf(";3;")>-1);//WBS
		categoryInfo.put("fdInnerOrder", fdExtendField.indexOf(";4;")>-1);//内部订单
		categoryInfo.put("fdIsTravelAlone", cate.getFdIsTravelAlone());
		categoryInfo.put("fdBerthId", fdExtendField.indexOf(";6;")>-1);//交通工具
		categoryInfo.put("fdBeginDate", fdExtendField.indexOf(";5;")>-1);//开始日期
		categoryInfo.put("fdDept", fdExtendField.indexOf(";7;")>-1);//部门
		data.put("categoryInfo", categoryInfo);
		return data;
	}

	@Override
	public JSONArray getTodoList(List<String> ids) throws Exception {
		JSONArray data = new JSONArray();
		StringBuilder hql=new StringBuilder();
		hql.append("from FsscExpenseMain main where ").append(HQLUtil.buildLogicIN("main.fdId", ids));
		/*modify by xiexx，发布单据特殊业务也会给业务人员发送待办（如报销线下领取现金），故去除发布状态*/
		hql.append("  order by main.fdId desc");
		List<FsscExpenseMain> mainList=fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery(hql.toString()).list();
		for (FsscExpenseMain main : mainList) {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("title", main.getDocSubject());
			jsonObj.put("date", DateUtil.convertDateToString(main.getDocCreateTime(), DateUtil.PATTERN_DATE));
			try {
				jsonObj.put("count", main.getFdTotalApprovedMoney());
			} catch (Exception e) {
				jsonObj.put("count","0.00");
			}
			jsonObj.put("company", main.getFdCompany().getFdName());
			jsonObj.put("dept", main.getFdCostCenter()==null?"":main.getFdCostCenter().getFdName());
			jsonObj.put("clazz", "status"+main.getDocStatus());
			jsonObj.put("status", EnumerationTypeUtil.getColumnEnumsLabel("common_status", main.getDocStatus()));
			jsonObj.put("link", "");
			jsonObj.put("id", main.getFdId());
			data.add(jsonObj);
		}
		return data;
	}

	@Override
	public JSONArray getCateList(HttpServletRequest request) throws Exception {
		   JSONArray rtn = new JSONArray();
			String hql = " fsscExpenseCategory.fdIsMobile=:fdIsMobile";
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(hql);
			hqlInfo.setParameter("fdIsMobile",true);
			hqlInfo.setRowSize(200);
			JSONArray data = new JSONArray();
			List<FsscExpenseCategory> list = (List<FsscExpenseCategory>) getFsscExpenseCategoryService().findPage(hqlInfo).getList();
			for(FsscExpenseCategory temp:list){
				JSONObject obj = new JSONObject();
				FsscExpenseCategory fdParent = (FsscExpenseCategory) temp.getFdParent();
				String fdCateName = "";
				if(null != fdParent){
					fdCateName = temp.getFdName() +"/(";
					StringBuffer fdParentName = new StringBuffer();
					while (null != fdParent){
						fdParentName = fdParentName.append(fdParent.getFdName()).append("/");
						fdParent = (FsscExpenseCategory) fdParent.getFdParent();
					}
					fdCateName = fdCateName + fdParentName.toString().substring(0, fdParentName.toString().length()-1) +")";
				}else{
					fdCateName = temp.getFdName();
				}
				obj.put("name", fdCateName);
				obj.put("text", fdCateName);
				obj.put("value", temp.getFdId());
				rtn.add(obj);
			}
	     	return rtn;
	}
		
	@Override
	public JSONObject checkMobileBudget(JSONObject data) throws Exception {
		JSONObject rtn = new JSONObject();
		String fdTemplateId = data.getString("docCategoryId");
		FsscExpenseCategory temp = (FsscExpenseCategory) fsscExpenseMainService.findByPrimaryKey(fdTemplateId, FsscExpenseCategory.class, true);
		if(temp.getFdIsFee()&&data.containsKey("fdFeeMainId")){
			rtn = this.checkFee(data);
		}
		if(rtn.containsKey("message")){
			return rtn;
		}
		String fdCompanyId = data.getString("fdCompanyId");
		String fdProjectId = data.containsKey("fdProjectId")?data.getString("fdProjectId"):"";
		//用于多明细匹配到同一预算时计算累计使用金额
		Map<String,Map<String,Object>> budgetMap = new HashMap<String,Map<String,Object>>();
		JSONArray detailList = data.getJSONArray("detailJson");
		for(int i=0;i<detailList.size();i++){
			JSONObject detailData = detailList.getJSONObject(i);
			//如果在事前内，则不匹配预算
			if(detailData.containsKey("fdFeeStatus")&&"1".equals(detailData.get("fdFeeStatus"))){
				detailData.put("fdBudgetStatus", "1");
				detailData.put("fdBudgetInfo", "[]");
				continue;
			}
			String fdExpenseItemId = detailData.getString("fdExpenseItemId");
			String fdCostCenterId = detailData.getString("fdCostCenterId");
			String fdWbsId = "";
			if(detailData.containsKey("fdWbsId")){
				fdWbsId = detailData.getString("fdWbsId");
			}
			String fdInnerOrderId = "";
			if(detailData.containsKey("fdInnerOrderId")){
				fdInnerOrderId = detailData.getString("fdInnerOrderId");
			}
			String fdPersonId = detailData.getString("fdRealUserId");
			String fdDeptId = "";
			if(detailData.containsKey("fdDeptId")){
				fdDeptId = detailData.getString("fdDeptId");
			}
			JSONObject budgetInfo = new JSONObject();
			budgetInfo.put("fdCompanyId", fdCompanyId);
			budgetInfo.put("fdExpenseItemId", fdExpenseItemId);
			budgetInfo.put("fdCostCenterId", fdCostCenterId);
			budgetInfo.put("fdWbsId", fdWbsId);
			budgetInfo.put("fdProjectId", fdProjectId);
			budgetInfo.put("fdInnerOrderId", fdInnerOrderId);
			budgetInfo.put("fdPersonId", fdPersonId);
			budgetInfo.put("fdDeptId", fdDeptId);
			JSONObject budget = getFsscCommonBudgetService().matchFsscBudget(budgetInfo);
			if(!budget.containsKey("data")){
				rtn.put("pass", true);
				rtn.put("data", data);
				return rtn;
			}
			JSONArray budgetList = budget.getJSONArray("data");
			if(budgetList==null){
				budgetList = new JSONArray();
			}
			detailData.put("fdBudgetInfo", budgetList.toString().replaceAll("\"", "\'"));
			//获取汇率
			Double budgetMoney = getBudgetMoneyAvoidFee(detailList,i,fdCompanyId);
			//默认无预算
			String status = "0";
			for(int k=0;k<budgetList.size();k++){
				status = "2".equals(status)?status:"1";
				//判断是否超出可使用额度，需要加上弹性比例
				Double fdCanUseAmount = budgetList.getJSONObject(k).getDouble("fdCanUseAmount");
				Double fdElasticPercent = 0d;
				if(budgetList.getJSONObject(k).containsKey("fdElasticPercent")){
					fdElasticPercent = budgetList.getJSONObject(k).getDouble("fdElasticPercent");
				}
				fdElasticPercent = fdElasticPercent==null?0d:fdElasticPercent;
				Double fdTotalAmount = budgetList.getJSONObject(k).getDouble("fdTotalAmount");
				if(fdCanUseAmount!=null&&fdCanUseAmount<budgetMoney){
					status = "2";
				}
				fdCanUseAmount =  FsscNumberUtil.getAddition(fdCanUseAmount, FsscNumberUtil.getMultiplication(fdTotalAmount, FsscNumberUtil.getDivide(fdElasticPercent, 100)));
				//刚控且超预算，不能提交
				if(fdCanUseAmount!=null&&fdCanUseAmount<budgetMoney){
					status = "2";
					if("1".equals(budgetList.getJSONObject(k).get("fdRule"))||"3".equals(budgetList.getJSONObject(k).get("fdRule"))){
						rtn.put("pass", false);
						rtn.put("data", data);
						rtn.put("message", ResourceUtil.getString("tips.budget.over.row","fssc-expense").replace("{row}", (i+1)+""));
						return rtn;
					}
				}
				String fdBudgetId = budgetList.getJSONObject(k).getString("fdBudgetId");
				//累计总额
				if(budgetMap.containsKey(fdBudgetId)){
					Map<String,Object> map = budgetMap.get(fdBudgetId);
					Double mon = (Double) map.get("useMoney");
					map.put("useMoney", FsscNumberUtil.getAddition(budgetMoney, mon));
					List<Integer> index = (List<Integer>) map.get("index");
					index.add(i);
					budgetMap.put(fdBudgetId, map);
				}else{
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("useMoney", budgetMoney);
					map.put("forbid", budgetList.getJSONObject(k).get("fdRule"));
					List<Integer> index = new ArrayList<Integer>();
					index.add(i);
					map.put("index", index);
					map.put("budgetMoney", fdCanUseAmount);
					budgetMap.put(fdBudgetId, map);
				}
			}
			detailData.put("fdBudgetStatus", status);
			detailData.put("fdBudgetInfo", budgetList.toString().replaceAll("\"", "\'"));
		}
		//校验多条明细占同一预算的情况
		Iterator<String> it = budgetMap.keySet().iterator();
		for(;it.hasNext();){
			String key = it.next();
			Map<String,Object> map = budgetMap.get(key);
			Double useMoney = (Double) map.get("useMoney");
			Double budMoney = (Double) map.get("budgetMoney");
			List<Integer> index = (List<Integer>) map.get("index");
			String forbid = (String) map.get("forbid");
			if(useMoney>budMoney){
				if("1".equals(forbid)||"3".equals(forbid)){
					rtn.put("pass", false);
					rtn.put("data", data);
					rtn.put("message", ResourceUtil.getString("tips.budget.over.row","fssc-expense").replace("{row}", index.size()+""));
					return rtn;
				}
				//覆盖预算状态
				for(Integer i:index){
					detailList.getJSONObject(i).put("fdBudgetStatus", "2");
				}
			}
		}
		rtn.put("data", data);
		if(!rtn.containsKey("pass")){
			rtn.put("pass", true);
		}
		return rtn;
	}

	/**
	 * 获取占预算的金额，去除占事前的部分
	 * @param detailList
	 * @param i
	 * @return
	 * @throws Exception 
	 */
	private Double getBudgetMoneyAvoidFee(JSONArray detailList, int i,String fdCompanyId) throws Exception {
		JSONObject detailData = detailList.getJSONObject(i);
		String fdCurrencyId = detailData.getString("fdCurrencyId");
		//获取汇率
		Double budgetRate = getEopBasedataExchangeService().getBudgetRate(fdCurrencyId, fdCompanyId);
		Double money = Double.valueOf(detailData.getString("fdMoney"));
		Double budgetMoney = FsscNumberUtil.getMultiplication(money, budgetRate);
		detailData.put("fdBudgetMoney", budgetMoney);
		Map<String,Double> feeMoney = new HashMap<String,Double>();
		if(detailData.containsKey("fdFeeInfo")){
			String fdFeeInfo = detailData.getString("fdFeeInfo");
			JSONArray feeInfo = JSONArray.fromObject(fdFeeInfo.replaceAll("\'", "\""));
			for(int m=0;m<i;m++){
				JSONObject detail = detailList.getJSONObject(m);
				String __fdFeeInfo = detail.containsKey("fdFeeInfo")?detail.getString("fdFeeInfo"):"[]";
				JSONArray _fdFeeInfo = JSONArray.fromObject(__fdFeeInfo);
				Double fdBudgetMoney = detail.getDouble("fdBudgetMoney");
				for(int k=0;k<_fdFeeInfo.size();k++){
					JSONObject info = _fdFeeInfo.getJSONObject(k);
					Double fdUsableMoney = info.getDouble("fdUsableMoney");
					String fdLedgerId = info.getString("fdLedgerId");
					if(feeMoney.containsKey(fdLedgerId)){
						fdUsableMoney = feeMoney.get(fdLedgerId);
					}
					if(FsscNumberUtil.isEqual(fdUsableMoney, 0d)||FsscNumberUtil.isEqual(fdBudgetMoney, 0d)){
						continue;
					}
					if(fdUsableMoney>fdBudgetMoney){
						feeMoney.put(fdLedgerId, FsscNumberUtil.getSubtraction(fdUsableMoney, fdBudgetMoney));
						fdBudgetMoney = 0d;
					}else if(fdUsableMoney<fdBudgetMoney){
						feeMoney.put(fdLedgerId, 0d);
						fdBudgetMoney = FsscNumberUtil.getSubtraction(fdBudgetMoney, fdUsableMoney);
					}else{
						fdBudgetMoney = 0d;
						feeMoney.put(fdLedgerId, 0d);
					}
				}
			}
			for(int k=0;k<feeInfo.size();k++){
				JSONObject info = feeInfo.getJSONObject(k);
				Double fdUsableMoney = info.getDouble("fdUsableMoney");
				String fdLedgerId = info.getString("fdLedgerId");
				if(feeMoney.containsKey(fdLedgerId)){
					fdUsableMoney = feeMoney.get(fdLedgerId);
				}
				if(budgetMoney<fdUsableMoney||FsscNumberUtil.isEqual(budgetMoney, fdUsableMoney)){
					return 0d;
				}else{
					budgetMoney = FsscNumberUtil.getSubtraction(budgetMoney, fdUsableMoney);
				}
			}
		}
		return budgetMoney;
	}

	private JSONObject checkFee(JSONObject data) throws Exception {
		JSONObject rtn = new JSONObject();
		String fdFeeMainId = data.getString("fdFeeMainId");
		if(StringUtil.isNull(fdFeeMainId)){
			rtn.put("data", data);
			rtn.put("pass", true);
			return rtn;
		}
		String fdCompanyId = data.getString("fdCompanyId");
		String fdCostCenterId = data.getString("fdCostCenterId");
		JSONArray fdDetailList = data.getJSONArray("detailJson");
		JSONArray params = new JSONArray();
		for(int i=0;i<fdDetailList.size();i++){
			JSONObject detail = fdDetailList.getJSONObject(i);
			String fdCurrencyId = detail.getString("fdCurrencyId");
			//获取汇率
			Double budgetRate = getEopBasedataExchangeService().getBudgetRate(fdCurrencyId, fdCompanyId);
			Double money = Double.valueOf(detail.getString("fdMoney"));
			Double budgetMoney = FsscNumberUtil.getMultiplication(money, budgetRate);
			detail.put("fdBudgetMoney", budgetMoney);
			if(detail.containsKey("fdCompanyId")){
				fdCompanyId = detail.getString("fdCompanyId");
			}
			if(detail.containsKey("fdCostCenterId")){
				fdCostCenterId = detail.getString("fdCostCenterId");
			}
			JSONObject param = new JSONObject();
			param.put("fdCompanyId", fdCompanyId);
			param.put("fdCostCenterId", fdCostCenterId);
			param.put("fdExpenseItemId", detail.get("fdExpenseItemId"));
			if(detail.containsKey("fdInnerOrderId")){
				param.put("fdInnerOrderId", detail.get("fdInnerOrderId"));
			}
			if(detail.containsKey("fdWbsId")){
				param.put("fdWbsId", detail.get("fdWbsId"));
			}
			if(detail.containsKey("fdProjectId")){
				param.put("fdProjectId", detail.get("fdProjectId"));
			}
			if(detail.containsKey("fdRealUserId")){
				param.put("fdPersonId", detail.get("fdRealUserId"));
			}
			if(detail.containsKey("fdDeptId")){
				param.put("fdDeptId", detail.get("fdDeptId"));
			}
			param.put("fdMoney", detail.get("fdMoney"));
			param.put("fdCurrencyId", detail.get("fdCurrencyId"));
			param.put("fdDetailId", i);
			params.add(param);
		}
		JSONObject fee = new JSONObject();
		fee.put("data", params);
		fee.put("fdFeeIds", fdFeeMainId);
		JSONObject feeData = getFsscCommonFeeService().getFeeLedgerData(fee);
		Map<String,Double> map = new HashMap<String,Double>();
		Map<String,Double> detailMap = new HashMap<String,Double>();
		for(int i=0;i<fdDetailList.size();i++){
			JSONObject detail = fdDetailList.getJSONObject(i);
			JSONArray feeInfo = (JSONArray) feeData.get(i+"");
			String fdCurrencyId = detail.getString("fdCurrencyId");
			Double rate = getEopBasedataExchangeService().getBudgetRate(fdCurrencyId, fdCompanyId);
			detail.put("fdFeeInfo", feeInfo.toString().replaceAll("\"", "\'"));
			detail.put("fdFeeStatus", feeInfo.size()>0?"1":"0");
			for(int k=0;k<feeInfo.size();k++){
				JSONObject info = feeInfo.getJSONObject(k);
				String fdLedgerId = info.getString("fdLedgerId");
				Double fdMoney = 0d;
				if(!detailMap.containsKey(detail.get("fdId"))){
					fdMoney = detail.getDouble("fdMoney");
					fdMoney = FsscNumberUtil.getMultiplication(fdMoney, rate);
				}else{
					fdMoney = detailMap.get(detail.get("fdId"));
				}
				Double money = info.getDouble("fdUsableMoney");
				if(map.containsKey(fdLedgerId)){
					money = map.get(fdLedgerId);
				}
				if(money>0&&fdMoney>0){
					if(fdMoney>money){
						map.put(fdLedgerId, 0d);
						detailMap.put(detail.getString("fdId"), FsscNumberUtil.getSubtraction(fdMoney, money));
						detail.put("fdFeeStatus", 2);
						if("1".equals(feeData.getString("fdForbid"))){
							rtn.put("message", ResourceUtil.getString("tips.fee.over.mobile","fssc-expense").replace("{row}", (i+1)+""));
							rtn.put("pass", false);
							rtn.put("data", data);
							return rtn;
						}
					}else{
						map.put(fdLedgerId, FsscNumberUtil.getSubtraction(money, fdMoney));
						detailMap.put(detail.getString("fdId"), 0d);
					}
				}
			}
		}
		rtn.put("data", data);
		rtn.put("pass", true);
		return rtn;
	}

	@Override
	public JSONObject checkMobileStandard(JSONObject data) throws Exception {
		JSONObject rtn = new JSONObject();
		JSONArray detailList = data.getJSONArray("detailJson");
		JSONArray fdTravelList = data.getJSONArray("fdTravelList");
		for(int k=0;k<fdTravelList.size();k++){
			JSONObject travelData = fdTravelList.getJSONObject(k);
			String fdBerthId = travelData.containsKey("fdBerthId")?travelData.getString("fdBerthId"):"";
			if(StringUtil.isNull(fdBerthId)){
				EopBasedataBerth b = (EopBasedataBerth) fsscExpenseMainService.findByPrimaryKey(fdBerthId, EopBasedataBerth.class, true);
				travelData.put("fdVehicleId", b.getFdVehicle().getFdId());
			}
		}
		for(int i=0;i<detailList.size();i++){
			JSONObject detailData = detailList.getJSONObject(i);
			for(int k=0;k<fdTravelList.size();k++){
				JSONObject travelData = fdTravelList.getJSONObject(k);
				if(detailData.getString("fdTravel").equals(travelData.getString("fdSubject"))){
					detailData.put("fdTravelDays", travelData.get("fdTravelDays"));
					detailData.put("fdVehicleId", travelData.get("fdVehicleId"));
					detailData.put("fdAreaId", travelData.getString("fdArrivalPlaceId").split("__")[1]);
				}
			}
		}
		String fdCompanyId = data.getString("fdCompanyId");
		for(int i=0;i<detailList.size();i++){
			JSONObject detailData = detailList.getJSONObject(i);
			String fdExpenseItemId = detailData.getString("fdExpenseItemId");
			String fdAreaId ="";
			if(detailData.containsKey("fdAreaId")){
				fdAreaId = detailData.getString("fdAreaId");
			}
			String fdPersonId = detailData.getString("fdRealUserId");
			String fdVehicleId ="";
			if(detailData.containsKey("fdVehicleId")){
				fdVehicleId = detailData.getString("fdVehicleId");
			}
			Number fdPersonNumber = null;
			if(detailData.containsKey("fdPersonNumber")){
				String n = detailData.getString("fdPersonNumber");
				if(StringUtil.isNotNull(n)){
					fdPersonNumber = Integer.valueOf(n);
				}
			}
			Double money = Double.valueOf(detailData.getString("fdMoney"));
			Integer fdTravelDays =1;
			if(detailData.containsKey("fdTravelDays")){
				fdTravelDays = detailData.getInt("fdTravelDays");
			}
			String fdCurrencyId = detailData.getString("fdCurrencyId");
			JSONObject standardInfo = new JSONObject();
			standardInfo.put("fdCompanyId", fdCompanyId);
			standardInfo.put("fdExpenseItemId", fdExpenseItemId);
			standardInfo.put("fdPersonId", fdPersonId);
			standardInfo.put("fdAreaId", fdAreaId);
			standardInfo.put("fdMoney", money);
			standardInfo.put("fdPersonNumber", fdPersonNumber);
			standardInfo.put("fdCurrencyId", fdCurrencyId);
			standardInfo.put("fdVehicleId", fdVehicleId);
			standardInfo.put("fdTravelDays", fdTravelDays);
			JSONObject standard = getEopBasedataStandardService().getStandardData(standardInfo);
			if("0".equals(standard.get("submit"))){
				rtn.put("pass", false);
				rtn.put("message", ResourceUtil.getString("tips.standard.over","fssc-expense").replace("{row}", (i+1)+""));
				return rtn;
			}
			detailData.put("fdStandardStatus", standard.get("status"));
		}
		
		if(!rtn.containsKey("pass")){
			rtn.put("pass", true);
			rtn.put("data", data);
		}
		return rtn;
	}

	@Override
	public JSONObject checkHasBudget(JSONObject data) throws Exception {
		JSONObject rtn = new JSONObject();
		//如果没有预算模块，不校验预算
		if(!FsscCommonUtil.checkHasModule("/fssc/budget/")){
			rtn.put("pass", true);
			rtn.put("data", data);
			return rtn;
		}
		String fdTemplateId = data.getString("docCategoryId");
		String fdCompanyId = data.getString("fdCompanyId");
		String hql = "from "+FsscExpenseItemConfig.class.getName()+" where fdCategory.fdId = :fdTemplateId and fdCompany.fdId=:fdCompanyId and fdIsAvailable=:fdIsAvailable";
		List<FsscExpenseItemConfig> list = fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdTemplateId", fdTemplateId)
				.setParameter("fdCompanyId", fdCompanyId)
				.setParameter("fdIsAvailable", true).list();
		JSONArray detailList = data.getJSONArray("detailJson");
		for(int i=0;i<detailList.size();i++){
			JSONObject detailData = detailList.getJSONObject(i);
			String fdBudgetStatus = detailData.containsKey("fdBudgetStatus")?detailData.getString("fdBudgetStatus"):"0";
			String fdExpenseItemName = detailData.getString("fdExpenseItemName");
			String message = ResourceUtil.getString("tips.expense.item.need.budget","fssc-expense").replace("{0}", fdExpenseItemName);
			for(FsscExpenseItemConfig config:list){
				if("0".equals(fdBudgetStatus)&&config.getFdIsNeedBudget()){
					rtn.put("pass", false);
					rtn.put("data", data);
					rtn.put("message", message);
					return rtn;
				}
			}
		}
		if(!rtn.containsKey("pass")){
			rtn.put("pass", true);
			rtn.put("data", data);
		}
		return rtn;
	}
	
	@Override
	public JSONObject getCategoryInfo(String fdCategoryId) throws Exception {
		JSONObject rtn = new JSONObject();
		FsscExpenseCategory cate = (FsscExpenseCategory) fsscExpenseMainService.findByPrimaryKey(fdCategoryId, FsscExpenseCategory.class, true);
		if(cate!=null){
			rtn.put("fdIsProject", cate.getFdIsProject());
			rtn.put("fdIsFee", cate.getFdIsFee());
			rtn.put("fdExpenseType", cate.getFdExpenseType());//报销类型
			rtn.put("fdAllocType", cate.getFdAllocType());//报销模式
			rtn.put("fdSubjectType", cate.getFdSubjectType());//主题生成方式
			String fdExtendField = ";"+cate.getFdExtendFields()+";";
			rtn.put("fdPersonNumber", fdExtendField.indexOf(";1;")>-1);//招待人数
			rtn.put("fdInputTax", fdExtendField.indexOf(";2;")>-1);//进项税额
			rtn.put("fdWbs", fdExtendField.indexOf(";3;")>-1);//WBS
			rtn.put("fdInnerOrder", fdExtendField.indexOf(";4;")>-1);//内部订单
			rtn.put("fdBerthId", fdExtendField.indexOf(";6;")>-1);//交通工具
			rtn.put("fdBeginDate", fdExtendField.indexOf(";5;")>-1);//开始日期
			rtn.put("fdDept", fdExtendField.indexOf(";7;")>-1);//部门
			rtn.put("fdIsTravelAlone", cate.getFdIsTravelAlone());
		}
		return rtn;
	}

	@Override
	public String getFeeTemplateId(String fdTemplateId) throws Exception {
		FsscExpenseCategory temp = (FsscExpenseCategory) fsscExpenseMainService.findByPrimaryKey(fdTemplateId, FsscExpenseCategory.class, true);
		if(temp!=null&&StringUtil.isNotNull(temp.getFdFeeTemplateId())){
			return temp.getFdFeeTemplateId();
		}
		return "";
	}

	@Override
	public Boolean isDateStandardUsed(String fdExpenseItemId,String fdPersonId,String fdBeginDate, String fdEndDate,String fdDetailId) throws Exception {
		Date begin = DateUtil.convertStringToDate(fdBeginDate, DateUtil.PATTERN_DATE);
		Date end = DateUtil.convertStringToDate(fdEndDate, DateUtil.PATTERN_DATE);
		Calendar begin_ = Calendar.getInstance();
		begin_.setTime(begin);
		begin_.set(Calendar.HOUR_OF_DAY, 0);
		begin_.set(Calendar.MINUTE, 0);
		begin_.set(Calendar.SECOND, 0);
		Calendar end_ = Calendar.getInstance();
		end_.setTime(end);
		end_.set(Calendar.HOUR_OF_DAY, 23);
		end_.set(Calendar.MINUTE, 59);
		end_.set(Calendar.SECOND, 59);
		StringBuilder hql = new StringBuilder();
		hql.append("select fdId from FsscExpenseDetail where fdExpenseItem.fdId=:fdExpenseItemId and docMain.fdId<>:fdId");
		hql.append(" and ((fdStartDate <= :begin and fdHappenDate>= :begin) or (fdStartDate <= :end and fdHappenDate>= :end)");
		hql.append(" or (fdStartDate >= :begin and fdStartDate<= :end) or (fdHappenDate >= :begin and fdHappenDate<= :end))");
		hql.append(" and fdRealUser.fdId=:fdPersonId and docMain.docStatus in('20','30')");
		Query query = fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery(hql.toString());
		query.setParameter("fdExpenseItemId", fdExpenseItemId);
		query.setParameter("fdPersonId", fdPersonId);
		query.setParameter("fdId", fdDetailId);
		query.setParameter("begin", begin_.getTime());
		query.setParameter("end", end_.getTime());
		if(!ArrayUtil.isEmpty(query.list())){
			return true;
		}
		hql = new StringBuilder();
		hql.append("select t.fdId from FsscExpenseTravelDetail t,FsscExpenseDetail d where d.docMain.fdId<>:fdId");
		hql.append(" and d.fdExpenseItem.fdId=:fdExpenseItemId and t.docMain.fdId=d.docMain.fdId and t.fdSubject=d.fdTravel");
		hql.append(" and d.fdRealUser.fdId=:fdPersonId and d.docMain.docStatus in('20','30')");
		hql.append(" and ((t.fdBeginDate <= :begin and t.fdEndDate>= :begin) or (t.fdBeginDate <= :end and t.fdEndDate>= :end)");
		hql.append(" or (t.fdBeginDate >= :begin and t.fdBeginDate<= :end) or (t.fdEndDate >= :begin and t.fdEndDate<= :end))");
		query = fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery(hql.toString());
		query.setParameter("fdExpenseItemId", fdExpenseItemId);
		query.setParameter("fdId", fdDetailId);
		query.setParameter("fdPersonId", fdPersonId);
		query.setParameter("begin", begin_.getTime());
		query.setParameter("end", end_.getTime());
		return !ArrayUtil.isEmpty(query.list());
	}
	/**
	 * 判断事前转报销是否已经被勾选关闭
	 * @param fdBeginDate
	 * @param fdEndDate
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean getExpenseCloseFlag(String fdFeeId) throws Exception {
		Boolean isCloseFlag=Boolean.FALSE;  //默认未勾选关闭
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock("fsscExpenseMain.fdFeeIds like :fdFeeId and fsscExpenseMain.fdIsCloseFee=:fdIsCloseFee and (fsscExpenseMain.docStatus=:examine or fsscExpenseMain.docStatus=:publish)");
		hqlInfo.setSelectBlock("count(fsscExpenseMain.fdId)");
		hqlInfo.setParameter("fdFeeId", "%"+fdFeeId+"%");
		hqlInfo.setParameter("fdIsCloseFee", Boolean.TRUE);
		hqlInfo.setParameter("examine", SysDocConstant.DOC_STATUS_EXAMINE);
		hqlInfo.setParameter("publish", SysDocConstant.DOC_STATUS_PUBLISH);
		List<Long> countList= fsscExpenseMainService.findList(hqlInfo);
		if(!ArrayUtil.isEmpty(countList)&&countList.get(0)>0){
			isCloseFlag=Boolean.TRUE;  //勾选关闭
		}
		return isCloseFlag;
	}
	
	/**
	 * 得到报销分类列表
	 * @param
	 */
	 @Override
     public Page getExpenseCategoryHql(HttpServletRequest request, String s_pageno, String s_rowsize, String keyWord, String source, List<String> list, String connFlag) throws Exception{
    	 int pageno = 0;
         int rowsize = SysConfigParameters.getRowSize();
         if (s_pageno != null && s_pageno.length() > 0) {
             pageno = Integer.parseInt(s_pageno);
         }
         if (s_rowsize != null && s_rowsize.length() > 0) {
             rowsize = Integer.parseInt(s_rowsize);
         }
    	 HQLInfo hqlInfo = new HQLInfo();
         hqlInfo.setPageNo(pageno);
         hqlInfo.setRowSize(rowsize);
         if("connectMore".equals(connFlag)){
        	 String where = hqlInfo.getWhereBlock();
             where =StringUtil.linkString(where, " and ", " fsscExpenseCategory.fdId in (:docTemplateId)");
             hqlInfo.setParameter("docTemplateId", list);
             hqlInfo.setWhereBlock(where);
         }else{
        	 String where = hqlInfo.getWhereBlock();
             where =StringUtil.linkString(where, " and ", " fsscExpenseCategory.fdIsFee='1' ");
        	 where =StringUtil.linkString(where, " and ", " fsscExpenseCategory.fdFeeTemplateId = '' ");
             hqlInfo.setWhereBlock(where);
         }
    	 if (StringUtil.isNotNull(keyWord)) {
    		 String where = hqlInfo.getWhereBlock();
             where =StringUtil.linkString(where, " and ", " fsscExpenseCategory.fdName like :fdName");
             hqlInfo.setParameter("fdName", "%" + keyWord + "%");
             hqlInfo.setWhereBlock(where);
         }
    	 if("mobile".equals(source)){
        	 String where = hqlInfo.getWhereBlock();
             where =StringUtil.linkString(where, " and ", " fsscExpenseCategory.fdIsMobile=:fdIsMobile");
             hqlInfo.setParameter("fdIsMobile", true);  //移动端使用
             hqlInfo.setWhereBlock(where);
        }
    	HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscExpenseCategory.class);
    	Page page=getFsscExpenseCategoryService().findPage(hqlInfo);
		return page;
    	
    }

}
