package com.landray.kmss.fssc.expense.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataAccount;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataInputTax;
import com.landray.kmss.eop.basedata.model.EopBasedataPayWay;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostCenterService;
import com.landray.kmss.eop.basedata.service.IEopBasedataExchangeRateService;
import com.landray.kmss.eop.basedata.service.IEopBasedataInputTaxService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPayWayService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonExpenseService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLedgerService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonMobileService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.expense.forms.FsscExpenseMainForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseAccounts;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseInvoiceDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.model.FsscExpenseTemp;
import com.landray.kmss.fssc.expense.model.FsscExpenseTempDetail;
import com.landray.kmss.fssc.expense.service.IFsscExpenseCategoryService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMobileService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseTempService;
import com.landray.kmss.fssc.expense.util.FsscExpenseUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.lbpm.engine.persistence.AccessManager;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FsscExpenseMobileServiceImp  extends ExtendDataServiceImp implements IFsscExpenseMobileService,IXMLDataBean{
	
	public Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	public IFsscCommonExpenseService fsscCommonExpenseService;

	public IFsscCommonExpenseService getFsscCommonExpenseService() {
		if (fsscCommonExpenseService == null) {
			fsscCommonExpenseService = (IFsscCommonExpenseService) SpringBeanUtil.getBean("fsscExpenseCommonService");
		}
		return fsscCommonExpenseService;
	}
	
	public ISysOrgCoreService sysOrgCoreService;
	
	public ISysOrgCoreService getSysOrgCoreService() {
		if(sysOrgCoreService==null){
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}
	
	private IEopBasedataCompanyService eopBasedataCompanyService;

	public IEopBasedataCompanyService getEopBasedataCompanyService() {
		if(eopBasedataCompanyService==null){
			eopBasedataCompanyService = (IEopBasedataCompanyService) SpringBeanUtil.getBean("eopBasedataCompanyService");
		}
		return eopBasedataCompanyService;
	}
	
	private IEopBasedataCostCenterService eopBasedataCostCenterService;
	
	public IEopBasedataCostCenterService getEopBasedataCostCenterService() {
		if(eopBasedataCostCenterService==null){
			eopBasedataCostCenterService = (IEopBasedataCostCenterService) SpringBeanUtil.getBean("eopBasedataCostCenterService");
		}
		return eopBasedataCostCenterService;
	}
	
	private IFsscCommonMobileService fsscCommonMobileService;
	
	public IFsscCommonMobileService getFsscCommonMobileService() {
		if(fsscCommonMobileService==null){
			fsscCommonMobileService = (IFsscCommonMobileService) SpringBeanUtil.getBean("fsscCommonMobileService");
		}
		return fsscCommonMobileService;
	}
	
	private ISysNumberFlowService sysNumberFlowService;
	
	public ISysNumberFlowService getSysNumberFlowService() {
		if(sysNumberFlowService==null){
			sysNumberFlowService = (ISysNumberFlowService) SpringBeanUtil.getBean("sysNumberFlowService");
		}
		return sysNumberFlowService;
	}
	
	private IFsscExpenseMainService fsscExpenseMainService;
	
	public IFsscExpenseMainService getFsscExpenseMainService() {
		
		if(fsscExpenseMainService==null){
			fsscExpenseMainService = (IFsscExpenseMainService) SpringBeanUtil.getBean("fsscExpenseMainService");
		}
		return fsscExpenseMainService;
	}
	
	private IFsscCommonLedgerService fsscCommonLedgerService;
	
	public IFsscCommonLedgerService getFsscCommonLedgerService() {
		if (fsscCommonLedgerService == null) {
			fsscCommonLedgerService = (IFsscCommonLedgerService) SpringBeanUtil.getBean("fsscCommonLedgerService");
        }
		return fsscCommonLedgerService;
	}
	
	
	private ISysAttMainCoreInnerService sysAttMainService;

	public ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}
	
	private IEopBasedataExchangeRateService eopBasedataExchangeService;
	
	public IEopBasedataExchangeRateService getEopBasedataExchangeService() {
		if (eopBasedataExchangeService == null) {
			eopBasedataExchangeService = (IEopBasedataExchangeRateService) SpringBeanUtil.getBean("eopBasedataExchangeRateService");
		}
		return eopBasedataExchangeService;
	}
	
	private IEopBasedataPayWayService eopBasedataPayWayService;
    
	
   public IEopBasedataPayWayService getEopBasedataPayWayService() {
	   if (eopBasedataPayWayService == null) {
		   eopBasedataPayWayService = (IEopBasedataPayWayService) SpringBeanUtil.getBean("eopBasedataPayWayService");
		}
		return eopBasedataPayWayService;
	}
   
   private IFsscExpenseCategoryService fsscExpenseCategoryService;

   public IFsscExpenseCategoryService getFsscExpenseCategoryService() {
	   if(fsscExpenseCategoryService==null){
		   fsscExpenseCategoryService = (IFsscExpenseCategoryService) SpringBeanUtil.getBean("fsscExpenseCategoryService");
	   }
		return fsscExpenseCategoryService;
	}
   
   private IEopBasedataInputTaxService eopBasedataInputTaxService;
   
   public IEopBasedataInputTaxService getEopBasedataInputTaxService() {
	   if(eopBasedataInputTaxService==null){
		   eopBasedataInputTaxService = (IEopBasedataInputTaxService) SpringBeanUtil.getBean("eopBasedataInputTaxService");
	   }
	   return eopBasedataInputTaxService;
   }
   
   	private IFsscExpenseTempService fsscExpenseTempService;
   
	public IFsscExpenseTempService getFsscExpenseTempService() {
		if(fsscExpenseTempService==null){
			fsscExpenseTempService = (IFsscExpenseTempService) SpringBeanUtil.getBean("fsscExpenseTempService");
		}
		return fsscExpenseTempService;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
	   FsscExpenseMain fsscExpenseMain = (FsscExpenseMain) model;
	   if (fsscExpenseMain.getDocTemplate() != null) {
       		requestContext.setAttribute("docTemplate", fsscExpenseMain.getDocTemplate());
           dispatchCoreService.initFormSetting(form, "fsscExpenseMain", fsscExpenseMain.getDocTemplate(), "fsscExpenseMain", requestContext);
       }
    }
   
	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscExpenseMain) {
            FsscExpenseMain fsscExpenseMain = (FsscExpenseMain) model;
            FsscExpenseMainForm fsscExpenseMainForm = (FsscExpenseMainForm) form;
            if (fsscExpenseMain.getDocStatus() == null || fsscExpenseMain.getDocStatus().startsWith("1")) {
                if (fsscExpenseMainForm.getDocStatus() != null && (fsscExpenseMainForm.getDocStatus().startsWith("1") || fsscExpenseMainForm.getDocStatus().startsWith("2"))) {
                    fsscExpenseMain.setDocStatus(fsscExpenseMainForm.getDocStatus());
                }
            }
            if (fsscExpenseMain.getDocNumber() == null && (fsscExpenseMain.getDocStatus().startsWith("2") || fsscExpenseMain.getDocStatus().startsWith("3"))) {
                fsscExpenseMain.setDocNumber(getSysNumberFlowService().generateFlowNumber(fsscExpenseMain));
            }
        }
        return model;
    }
	
	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		requestContext.setAttribute("isShowDraftsmanStatus", EopBasedataFsscUtil.getIsEnableDraftorStatus());
		String docTemplate = requestContext.getParameter("docTemplate");
        FsscExpenseMain fsscExpenseMain = new FsscExpenseMain();
        fsscExpenseMain.setDocCreateTime(new Date());
        fsscExpenseMain.setDocCreator(UserUtil.getUser());
        if(null!=docTemplate){
			FsscExpenseCategory fsscExpenseCategory = (FsscExpenseCategory)this.findByPrimaryKey(docTemplate, FsscExpenseCategory.class.getName(), true);
			fsscExpenseMain.setDocTemplate(fsscExpenseCategory);
		}
        FsscExpenseUtil.initModelFromRequest(fsscExpenseMain, requestContext);
        if (fsscExpenseMain.getDocTemplate() != null) {
            fsscExpenseMain.setExtendFilePath(XFormUtil.getFileName(fsscExpenseMain.getDocTemplate(), "fsscExpenseMain"));
            if (Boolean.FALSE.equals(fsscExpenseMain.getDocTemplate().getDocUseXform())) {
                fsscExpenseMain.setDocXform(fsscExpenseMain.getDocTemplate().getDocXform());
            }
            fsscExpenseMain.setDocUseXform(fsscExpenseMain.getDocTemplate().getDocUseXform());
        }
        SysOrgPerson user = UserUtil.getUser();
        fsscExpenseMain.setFdClaimant(user);
        //设置默认公司
        JSONObject authObj=EopBasedataFsscUtil.getAccountAuth(ModelUtil.getModelClassName(fsscExpenseMain),user.getFdId());
        boolean auth=authObj.optBoolean("auth", Boolean.TRUE);  //默认有权限
        String fdCompanyIds=authObj.optString("fdCompanyIds", "");
        List<EopBasedataCompany> own = new ArrayList<EopBasedataCompany>();
        //设置默认公司
        List<EopBasedataCompany> ownList = getEopBasedataCompanyService().findCompanyByUserId(user.getFdId());  //获取当前登录人所在公司
		List<EopBasedataCompany> companyList = new ArrayList<EopBasedataCompany>();
		if(StringUtil.isNotNull(fdCompanyIds)){
			companyList = getEopBasedataCompanyService().findByPrimaryKeys(fdCompanyIds.split(";"));
		}
		if(auth) {	//开账，允许新建
			if(!ArrayUtil.isEmpty(companyList)){	//开关帐公司不为空，取交集
				ownList.retainAll(companyList);
				own = ownList;
			} else {	//开关帐公司为空，取当前登录人的所属公司
				own = ownList;
			}
		} else {	//关帐，不允许新建
			if(!ArrayUtil.isEmpty(companyList)){	//开关帐公司不为空
				ownList.removeAll(companyList);
				own = ownList;
			}
		}
        if(!ArrayUtil.isEmpty(own)){
        	fsscExpenseMain.setFdCompany(own.get(0));
        	String  fdDeduRule=EopBasedataFsscUtil.getDetailPropertyValue(own.get(0).getFdId(),"fdDeduRule");
        	if(StringUtil.isNull(fdDeduRule)){
        		fdDeduRule="1";  //为空则默认为含税金额，保留原有逻辑
        	}
        	requestContext.setAttribute("fdDeduFlag", fdDeduRule);
        	JSONArray fdCompanyData = new JSONArray();
        	//成本中心
        	for (EopBasedataCompany eopBasedataCompany : own) {
        		JSONObject fdCompany  = new JSONObject();
        		fdCompany.put("value",eopBasedataCompany.getFdId() );
        		fdCompany.put("text",eopBasedataCompany.getFdName() );
        		fdCompanyData.add(fdCompany);
			}
        	requestContext.setAttribute("fdCompanyData", fdCompanyData.toString());
        }
        String fdIsAuthorize=EopBasedataFsscUtil.getSwitchValue("fdIsAuthorize");
    	if(StringUtil.isNull(fdIsAuthorize)){
    		fdIsAuthorize="true";  //默认启用提单转授权
    	}
    	requestContext.setAttribute("fdIsAuthorize", fdIsAuthorize);
        //加载默认账户
        if(ArrayUtil.isEmpty(fsscExpenseMain.getFdAccountsList())){
        	Query query = getBaseDao().getHibernateSession().createQuery("from com.landray.kmss.eop.basedata.model.EopBasedataAccount where fdPerson.fdId=:fdPersonId and fdIsDefault=:fdIsDefault and fdIsAvailable=:fdIsAvailable");
        	query.setParameter("fdPersonId", user.getFdId());
        	query.setParameter("fdIsDefault", true);
			query.setParameter("fdIsAvailable", true);
        	List<EopBasedataAccount> list = query.list();
        	if(!ArrayUtil.isEmpty(list)){
        		FsscExpenseAccounts acc = new FsscExpenseAccounts();
            	acc.setFdAccountId(list.get(0).getFdId());
            	acc.setFdAccountName(list.get(0).getFdName());
            	acc.setFdBankAccount(list.get(0).getFdBankAccount());
            	acc.setFdBankName(list.get(0).getFdBankName());
            	acc.setFdAccountAreaName(list.get(0).getFdAccountArea());
            	acc.setFdBankAccountNo(list.get(0).getFdBankNo());
            	if(fsscExpenseMain.getFdCompany()!=null) {
            		EopBasedataPayWay payWay=getEopBasedataPayWayService().getDefaultPayWay(fsscExpenseMain.getFdCompany());
                	acc.setFdPayWay(payWay);
                	if(payWay!=null){
                		acc.setFdBank(payWay.getFdDefaultPayBank());
                	}
            	}
            	fsscExpenseMain.setFdAccountsList(new ArrayList());
            	fsscExpenseMain.getFdAccountsList().add(acc);
        	}
        }
        
        requestContext.setAttribute("parentId", user.getFdParent()!=null?user.getFdParent().getFdId():"");
        requestContext.setAttribute("parentName", user.getFdParent()!=null?user.getFdParent().getFdName():"");
        if(fsscExpenseMain.getFdAttNumber()==null){
        	fsscExpenseMain.setFdAttNumber(0);
        }
        
        //判断费用明细是否从记一笔中带过来
        EopBasedataCostCenter defalutCostCenter=null;
        EopBasedataCompany defalutCompany=null;
        String noteIds = requestContext.getParameter("noteIds");
        if(StringUtil.isNotNull(noteIds)){
    	   if(FsscCommonUtil.checkHasModule("/fssc/mobile/")){
    		   JSONObject  obj=  getFsscCommonMobileService().getMobileNote(noteIds);
    		   if(null != obj){
    			  String result = obj.getString("result");
    			  if("success".equals(result)){
    				  JSONArray noteArr =  (JSONArray) obj.get("data");
    					  Double fdTotalMoney = 0d;
    					  List<FsscExpenseDetail> fdDetailList = new ArrayList<FsscExpenseDetail>();
        				  List<FsscExpenseInvoiceDetail> fdInvoiceList = new ArrayList<FsscExpenseInvoiceDetail>();
    					  for (int i=0;i< noteArr.size();i++) {
    						  FsscExpenseTemp temp = new FsscExpenseTemp();
            				  List<FsscExpenseTempDetail> tmpDetailList = new ArrayList<FsscExpenseTempDetail>();
            				  StringBuffer tmpDetailIds = new StringBuffer();
    						  JSONObject note =noteArr.getJSONObject(i);
    						  FsscExpenseDetail fsscExpenseDetail = new FsscExpenseDetail();
    						  fsscExpenseDetail.setFdExpenseTempId(temp.getFdId());
    						  String fdCostCenterId = note.optString("fdCostCenterId");
    						  if(StringUtil.isNotNull(fdCostCenterId)){
    							  EopBasedataCostCenter fdCostCenter = (EopBasedataCostCenter) getEopBasedataCostCenterService().findByPrimaryKey(fdCostCenterId, EopBasedataCostCenter.class, true);
    							  fsscExpenseDetail.setFdCostCenter(fdCostCenter);
    							  defalutCostCenter=fdCostCenter;
    						  }
    						  String fdCompanyId = note.optString("fdCompanyId");
    						  if(StringUtil.isNotNull(fdCompanyId)){
    							  EopBasedataCompany fdCompany = (EopBasedataCompany) getEopBasedataCostCenterService().findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
    							  defalutCompany=fdCompany;
    						  }
    						  if(defalutCompany!=null) {//随手记有带公司，直接获取
    							  fsscExpenseDetail.setFdCompany(defalutCompany);
    						  }else if(!ArrayUtil.isEmpty(own)){
    							  fsscExpenseDetail.setFdCompany(own.get(0));
    						  }
    						  String fdExpenseItemId =  note.optString("fdExpenseItemId");
    						  if(StringUtil.isNotNull(fdExpenseItemId)){
    							  EopBasedataExpenseItem item=(EopBasedataExpenseItem) getEopBasedataCostCenterService().findByPrimaryKey(fdExpenseItemId, EopBasedataExpenseItem.class, true);
    							  fsscExpenseDetail.setFdExpenseItem(item);
    							  //设置是否抵扣
    							  if(StringUtil.isNotNull(fsscExpenseMain.getDocTemplate().getFdExtendFields())&&fsscExpenseMain.getDocTemplate().getFdExtendFields().contains("2")){
    								  String hql = "from "+EopBasedataInputTax.class.getName()+" where fdItem.fdId=:fdItemId and fdIsAvailable=:fdIsAvailable";
    								  List<EopBasedataInputTax> list = getBaseDao().getHibernateSession().createQuery(hql)
    										  .setParameter("fdItemId", fdExpenseItemId).setParameter("fdIsAvailable", true).list();
    								  if(!ArrayUtil.isEmpty(list)){
    									  EopBasedataInputTax inputTax = list.get(0);
    									  Double fdTaxRate = inputTax.getFdTaxRate();
    									  fsscExpenseDetail.setFdInputTaxRate(fdTaxRate);
    									  fdTaxRate = fdTaxRate==null?0:FsscNumberUtil.getDivide(fdTaxRate, 100);
    									  Double fdMoney = (Double) note.opt("fdMoney");
    									  fdMoney = fdMoney==null?0:fdMoney;
    									  //不含税额 = 总额/(1+税率)
    									  Double fdNoTaxMoney = FsscNumberUtil.getDivide(fdMoney, FsscNumberUtil.getAddition(1, fdTaxRate),2);
    									  //税额=总额-不含税额 
    									  Double fdTaxMoney = FsscNumberUtil.getSubtraction(fdMoney, fdNoTaxMoney,2);
    									  fsscExpenseDetail.setFdIsDeduct(true);
    									  fsscExpenseDetail.setFdNoTaxMoney(fdNoTaxMoney);
    									  fsscExpenseDetail.setFdInputTaxMoney(fdTaxMoney);
    								  }else{
    									  fsscExpenseDetail.setFdIsDeduct(false);
    									  fsscExpenseDetail.setFdNoTaxMoney(note.optDouble("fdMoney",0));
    									  fsscExpenseDetail.setFdInputTaxMoney(0.00);
    								  }
    							  }else{
    								  fsscExpenseDetail.setFdIsDeduct(false);
    								  fsscExpenseDetail.setFdInputTaxMoney(0.00);
    							  }
    						  }else {
    							  fsscExpenseDetail.setFdNoTaxMoney(note.optDouble("fdMoney",0));
    						  }
    						  String fdDeptId = note.optString("fdDeptId");
    						  if(StringUtil.isNotNull(fdDeptId)&&StringUtil.isNotNull(fsscExpenseMain.getDocTemplate().getFdExtendFields())&&fsscExpenseMain.getDocTemplate().getFdExtendFields().contains("7")){
    							  SysOrgElement fdDept=(SysOrgElement) getEopBasedataCostCenterService().findByPrimaryKey(fdDeptId, SysOrgElement.class, true);
    							  fsscExpenseDetail.setFdDept(fdDept);
    						  }
    						  String fdRealUserId = note.optString("fdRealUserId");
    						  if(StringUtil.isNotNull(fdRealUserId)){
    							  SysOrgPerson fdRealUser = (SysOrgPerson) getBaseDao().findByPrimaryKey(fdRealUserId, SysOrgPerson.class, true);
    							  fsscExpenseDetail.setFdRealUser(fdRealUser);
    						  }
        					  fsscExpenseDetail.setFdUse(note.optString("fdDesc"));
        					  String fdHappenDate = note.optString("fdHappenDate");
        					  if(StringUtil.isNotNull(fdHappenDate)){
        						  Date date = DateUtil.convertStringToDate(fdHappenDate, DateUtil.PATTERN_DATE);
        						  fsscExpenseDetail.setFdHappenDate(date);
        						  fsscExpenseDetail.setFdStartDate(date);
        					  }
        					  fsscExpenseDetail.setFdTravelDays(1);
        					  Double fdMoney = (Double) note.opt("fdMoney");
        					  fdMoney = fdMoney==null?0d:fdMoney;
        					  fdTotalMoney = FsscNumberUtil.getAddition(fdMoney,fdTotalMoney);
        					  fsscExpenseDetail.setFdApplyMoney(fdMoney);
        					  fsscExpenseDetail.setFdInvoiceMoney((Double) note.opt("fdInvoiceMoney"));
        					  String fdCurrencyId = note.optString("fdCurrencyId");
        					  fsscExpenseDetail.setFdNoteId(note.optString("fdNoteId"));
        					  if(StringUtil.isNotNull(fdCurrencyId)){
        						   EopBasedataCurrency eopBasedataCurrency = (EopBasedataCurrency) getBaseDao().findByPrimaryKey(fdCurrencyId, EopBasedataCurrency.class, true);
        						    fsscExpenseDetail.setFdCurrency(eopBasedataCurrency);
									 //计算税率
									Double budgetRate = getEopBasedataExchangeService().getBudgetRate(fdCurrencyId,defalutCompany.getFdId());
									Double exhcangeRate = getEopBasedataExchangeService().getExchangeRate(fdCurrencyId, defalutCompany.getFdId());
									fsscExpenseDetail.setFdExchangeRate(exhcangeRate!=null?exhcangeRate:0.0);
									fsscExpenseDetail.setFdBudgetRate(budgetRate!=null?budgetRate:0.0);
								}else{
								    fsscExpenseDetail.setFdCurrency(defalutCompany!=null?defalutCompany.getFdAccountCurrency():null);
									fsscExpenseDetail.setFdExchangeRate(1d);
									fsscExpenseDetail.setFdBudgetRate(1d);
								}
        					  fsscExpenseDetail.setFdStandardMoney(FsscNumberUtil.getMultiplication(fdMoney, fsscExpenseDetail.getFdExchangeRate(), 2));
        					  fsscExpenseDetail.setFdApprovedApplyMoney(fdMoney);
        					  fsscExpenseDetail.setFdApprovedStandardMoney(fsscExpenseDetail.getFdStandardMoney());
        					  fsscExpenseDetail.setFdStartPlace(note.optString("fdStartArea"));
        					  fsscExpenseDetail.setFdStartPlaceId(note.optString("fdStartAreaId"));
        					  fsscExpenseDetail.setFdArrivalPlace(note.optString("fdEndArea"));
        					  fsscExpenseDetail.setFdArrivalPlaceId(note.optString("fdEndAreaId"));
        					  fdDetailList.add(fsscExpenseDetail);
    						  //发票信息
        					  JSONArray invoiceArr =  (JSONArray) note.get("invoices");
        					  if(invoiceArr!=null) {
        						  for (Object object : invoiceArr) {
            						  JSONObject invoice = (JSONObject)object;
            						  FsscExpenseTempDetail tmpDetail = new FsscExpenseTempDetail();
            						  EopBasedataExpenseItem item = fsscExpenseDetail.getFdExpenseItem();
            						  if(item!=null) {
            							  tmpDetail.setFdExpenseTypeId(item.getFdId());
                						  tmpDetail.setFdExpenseTypeName(item.getFdName());
            						  }
            						  tmpDetail.setDocMain(temp);
            						  EopBasedataCompany comp  = fsscExpenseDetail.getFdCompany();
            						  if(comp!=null) {
            							  tmpDetail.setFdCompanyId(comp.getFdId());
            						  }
            						  tmpDetail.setFdInvoiceCode(invoice.optString("fdInvoiceCode"));
            						  tmpDetail.setFdInvoiceType(invoice.optString("fdInvoiceTypeId"));
            						  tmpDetail.setFdCheckCode(invoice.optString("fdCheckCode"));
            						  FsscExpenseInvoiceDetail invoiceDetail = new FsscExpenseInvoiceDetail();
            						  invoiceDetail.setFdInvoiceCode(invoice.optString("fdInvoiceCode"));
            						  invoiceDetail.setFdInvoiceNumber(invoice.optString("fdInvoiceNo"));
            						  invoiceDetail.setFdCheckCode(invoice.optString("fdCheckCode"));
            						  String fdInvoiceDate = invoice.optString("fdInvoiceDate");
            						  if(StringUtil.isNotNull(fdInvoiceDate)){
            							  invoiceDetail.setFdInvoiceDate(DateUtil.convertStringToDate(fdInvoiceDate, DateUtil.PATTERN_DATE));
            							  tmpDetail.setFdInvoiceDate(invoiceDetail.getFdInvoiceDate());
            						  }
            						  Double fdInvoiceMoney = (Double) invoice.opt("fdInvoiceMoney");
            						  invoiceDetail.setFdInvoiceMoney(fdInvoiceMoney);
            						  fdInvoiceMoney = fdInvoiceMoney==null?0d:fdInvoiceMoney;
            						  Double fdTax = (Double) invoice.opt("fdTax");
            						  invoiceDetail.setFdTaxMoney(fdTax);
            						  fdTax = fdTax==null?0d:fdTax;
            						  Double fdTaxRate = FsscNumberUtil.getDivide(fdTax, FsscNumberUtil.getSubtraction(fdInvoiceMoney, fdTax), 2);
            						  invoiceDetail.setFdTax(FsscNumberUtil.getMultiplication(fdTaxRate, 100,2));
            						  Double fdNoTax = (Double) invoice.opt("fdNoTax");
            						  invoiceDetail.setFdNoTaxMoney(fdNoTax);
            						  invoiceDetail.setFdInvoiceType(invoice.optString("fdInvoiceTypeId"));
            						  invoiceDetail.setFdCheckStatus(invoice.optString("fdCheckStatus"));
            						  invoiceDetail.setFdState(invoice.optString("fdState"));
            						  invoiceDetail.setFdPurchName(invoice.optString("fdPurchName"));
            						  invoiceDetail.setFdTaxNumber(invoice.optString("fdTaxNumber"));
            						  if(defalutCompany!=null){
										  invoiceDetail.setFdIsCurrent(getFsscExpenseTempService().getIsCurrent(defalutCompany.getFdId(),
												  invoice.optString("fdTaxNumber"),invoice.optString("fdPurchName"),invoice.optString("fdInvoiceTypeId")));
									  }
            						  tmpDetail.setFdTax(String.valueOf(FsscNumberUtil.getMultiplication(fdTaxRate, 100,2)));
            						  tmpDetail.setFdInvoiceMoney(fdInvoiceMoney);
            						  tmpDetail.setFdInvoiceNumber(invoice.optString("fdInvoiceNo"));
            						  tmpDetail.setFdCheckStatus(invoice.optString("fdCheckStatus"));
            						  tmpDetail.setFdState(invoice.optString("fdState"));
									  tmpDetail.setFdPurchName(invoice.optString("fdPurchName"));
									  tmpDetail.setFdTaxNumber(invoice.optString("fdTaxNumber"));
            						  tmpDetail.setFdTaxMoney(fdTax);
            						  tmpDetail.setFdNoTaxMoney(fdNoTax);
            						  tmpDetail.setDocMain(temp);
            						  tmpDetailList.addAll(buildTempInvoiceList(tmpDetail));
            						  fdInvoiceList.add(invoiceDetail);
            					  }
        					  }
        					  temp.setFdInvoiceListTemp(tmpDetailList);
        					  temp.setFdMainId(fsscExpenseMain.getFdId());
        					  getFsscExpenseTempService().add(temp);
        					  for(FsscExpenseTempDetail detailTemp:tmpDetailList){
									if(tmpDetailIds.length()>0){
										tmpDetailIds.append(";");
									}
								  tmpDetailIds.append(detailTemp.getFdId());
							  }
        					  fsscExpenseDetail.setFdExpenseTempDetailIds(tmpDetailIds.toString());
        					  //附件信息
        					  JSONArray attachmentArr =  (JSONArray) note.get("attachments");
        					  if(attachmentArr!=null) {
        						  String[] attIds = new String[attachmentArr.size()];
            					  for(int k=0;k<attachmentArr.size();k++) {
            						  JSONObject att = attachmentArr.getJSONObject(k);
            						  attIds[k] = att.getString("value");
            					  }
            					  List<SysAttMain> attList = getSysAttMainService().findByPrimaryKeys(attIds);
            					  List<SysAttMain> copyList = new ArrayList<SysAttMain>();
            					  for(SysAttMain att:attList) {
            						  SysAttMain copyAtt = getSysAttMainService().clone(att);
            						  copyAtt.setFdKey("attInvoice");
            						  copyAtt.setFdModelId(temp.getFdId());
            						  copyAtt.setFdModelName(FsscExpenseTemp.class.getName());
            						  copyList.add(copyAtt);
            					  }
            					  getSysAttMainService().add(copyList);
        					  }
    					  } 
    					  fsscExpenseMain.setFdInvoiceList(fdInvoiceList);
    					  fsscExpenseMain.setFdTotalApprovedMoney(fdTotalMoney);
    					  fsscExpenseMain.setFdTotalStandaryMoney(fdTotalMoney);
    					  requestContext.setAttribute("fdTotalMoney", fdTotalMoney);
    					  fsscExpenseMain.setFdDetailList(fdDetailList);
    					  if(!ArrayUtil.isEmpty(fsscExpenseMain.getFdAccountsList())){
    						  fsscExpenseMain.getFdAccountsList().get(0).setFdMoney(fdTotalMoney);
    					  }
    				   }
    			  }
    	   }
       }
        //默认记账公司
        if(defalutCompany==null&&!ArrayUtil.isEmpty(own)){
        	fsscExpenseMain.setFdCompany(own.get(0));
        }else{
        	fsscExpenseMain.setFdCompany(defalutCompany);
        }
        //默认成本中心
        if(defalutCostCenter==null&&!ArrayUtil.isEmpty(own)){
        	EopBasedataCostCenter costsOwn=getEopBasedataCostCenterService().findCostCenterByUserId(own.get(0).getFdId(), user.getFdId());
        	if(costsOwn!=null){
        		fsscExpenseMain.setFdCostCenter(costsOwn);
        	}
        }else{
        	fsscExpenseMain.setFdCostCenter(defalutCostCenter);
        }
        return fsscExpenseMain;
	}

	/**
	 * 根据随手记发票信息，判断发票是否存在多明细，多明细则拆分，狗则返回原信息
	 * @param tmpDetail
	 * @return
	 * @throws Exception
	 */
	public List<FsscExpenseTempDetail> buildTempInvoiceList(FsscExpenseTempDetail tmpDetail) throws Exception{
		List<FsscExpenseTempDetail> tempDetailList=new ArrayList<FsscExpenseTempDetail>();
		StringBuilder hql=new StringBuilder();
		hql.append("select detail.fdJe,detail.fdSlv,detail.fdSe from FsscLedgerInvoice main left join main.fdDetail detail where main.fdInvoiceNumber=:number");
		if(tmpDetail.getFdInvoiceCode()!=null){
			hql.append(" and main.fdInvoiceCode=:code");
		}
		Query query=this.getBaseDao().getHibernateSession().createQuery(hql.toString());
		query.setParameter("number",tmpDetail.getFdInvoiceNumber());
		if(tmpDetail.getFdInvoiceCode()!=null){
			query.setParameter("code",tmpDetail.getFdInvoiceCode());
		}
		List<Object[]> invoiceDetailList=query.list();
		if(!ArrayUtil.isEmpty(invoiceDetailList)){
			if(invoiceDetailList.size()==1){//一行明细
				tempDetailList.add(tmpDetail);
			}else{//多行明细
				Map<String, SysDictCommonProperty> dictMap= SysDataDict.getInstance().getModel(FsscExpenseTempDetail.class.getName()).getPropertyMap();
				FsscExpenseTempDetail temp=null;
				for(int i=0,size=invoiceDetailList.size();i<size;i++){
					temp=new FsscExpenseTempDetail();
					for (Map.Entry<String, SysDictCommonProperty> entry : dictMap.entrySet()) {
						String property=entry.getKey();
						if(!"fdId".equals(property)){
							PropertyUtils.setProperty(temp,property,PropertyUtils.getProperty(tmpDetail,property));
						}
   					 }
					Object[] obj=invoiceDetailList.get(i);
					if(obj!=null&&obj.length==3){
						Double fdNoTaxMoney=obj[0]!=null?Double.parseDouble(String.valueOf(obj[0])):0.00;
						temp.setFdNoTaxMoney(fdNoTaxMoney);//不含税金额
						temp.setFdTax(obj[1]!=null?String.valueOf(obj[1]).replace("%",""):""); //税率
						Double fdTaxMoney=obj[2]!=null?Double.parseDouble(String.valueOf(obj[2])):0.00;
						temp.setFdTaxMoney(fdTaxMoney); //税额
						temp.setFdInvoiceMoney(FsscNumberUtil.getAddition(fdNoTaxMoney,fdTaxMoney));
					}
					tempDetailList.add(temp);
				}
			}
		}
		return tempDetailList;
	}
	
	
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		FsscExpenseMain main = (FsscExpenseMain) modelObj;
		if (StringUtil.isNull(main.getDocNumber()) && !SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())) {
			main.setDocNumber(getSysNumberFlowService().generateFlowNumber(main));
        }
		if(!SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())&&FsscCommonUtil.checkHasModule("/fssc/ledger/")){
			getFsscExpenseMainService().addOrUpdateInvoiceInfo(main);
		}
		if(main.getFdClaimantDept()==null){
			main.setFdClaimantDept(main.getFdClaimant().getFdParent());
		}
		FsscExpenseCategory cate = main.getDocTemplate();
        if(cate!=null&&"2".equals(cate.getFdSubjectType())&&StringUtil.isNull(main.getDocSubject())){
        	FormulaParser parser = FormulaParser.getInstance(main);
        	main.setDocSubject((String) parser.parseValueScript(cate.getFdSubjectRule()));
        }
        updateInvoiceDetailCheckStatus(main);
        getFsscExpenseMainService().updateBudgetInfo(main);
        addExpenseTemp(main);//设置temp信息，防止编辑费用明细，移动端发票信息会被清除
        return super.add(modelObj);
	}
	
	@Override
    public void update(IBaseModel modelObj) throws Exception {
		FsscExpenseMain main = (FsscExpenseMain) modelObj;
		if (StringUtil.isNull(main.getDocNumber()) && !SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())) {
			main.setDocNumber(sysNumberFlowService.generateFlowNumber(main));
        }
		if(!SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())&&FsscCommonUtil.checkHasModule("/fssc/ledger/")){
			JSONObject valueJson=new JSONObject();
			valueJson.put("fdModelId", main.getFdId());
			valueJson.put("fdModelName", FsscExpenseMain.class.getName());
			valueJson.put("fdUseStatus", "1");   //设置为已使用
			getFsscCommonLedgerService().updatePropertys(getFsscExpenseMainService().getInvoiceIds(main), valueJson);
		}
		FsscExpenseCategory cate = main.getDocTemplate();
        if(cate!=null&&"2".equals(cate.getFdSubjectType())){
        	FormulaParser parser = FormulaParser.getInstance(main);
        	main.setDocSubject((String) parser.parseValueScript(cate.getFdSubjectRule()));
        }
        updateInvoiceDetailCheckStatus(main);
        getFsscExpenseMainService().updateBudgetInfo(main);
        //编辑时可能移除发票和费用明细，需要解除发票和随手记的关联
        updateInvoiceAndNote(main);
		super.update(modelObj);
	}
	
	private void updateInvoiceAndNote(FsscExpenseMain main) throws Exception {
		List<FsscExpenseDetail> list = main.getFdDetailList();
		if(null!=list){
			Map<String,String> map = new HashMap<String,String>();
			for (FsscExpenseDetail object : list) {
				if(StringUtil.isNull(object.getFdNoteId())){
					continue;
				}
				map.put(object.getFdNoteId(),object.getFdId());
			}
			//更新记一笔关联信息
			updateRelation(map);
		}
		List<String> noteIds = getBaseDao().getHibernateSession().createNativeQuery("select fd_note_id from fssc_expense_detail where doc_main_id=:fdModelId and fd_note_id is not null")
				.setParameter("fdModelId", main.getFdId()).list();
		for(FsscExpenseDetail detail:main.getFdDetailList()){
			if(StringUtil.isNotNull(detail.getFdNoteId())&&noteIds.contains(detail.getFdNoteId())){
				noteIds.remove(detail.getFdNoteId());
			}
		}
		if(noteIds.size()>0){//清空随手记关联
			getEopBasedataCostCenterService().getBaseDao().getHibernateSession().createQuery("update com.landray.kmss.fssc.mobile.model.FsscMobileNote set fdExpenseDetailId=null where fdId in(:ids)")
			.setParameterList("ids", noteIds).executeUpdate();
		}
		if(!FsscCommonUtil.checkHasModule("/fssc/ledger/")){
			return;
		}
		List<Object[]> oldInvoiceList = getBaseDao().getHibernateSession().createNativeQuery("select fd_id,fd_invoice_number,fd_invoice_code from fssc_expense_invoice_detail where doc_main_id=:fdModelId")
				.setParameter("fdModelId", main.getFdId()).list();
		out:for(Object[] detail:oldInvoiceList){
			if(!ArrayUtil.isEmpty(main.getFdInvoiceList())){//如果当前发票是原有的，跳过
				continue;
			}
			for(FsscExpenseInvoiceDetail inv:main.getFdInvoiceList()){
				if(inv.getFdId().equals(detail[0])){
					continue out;
				}
			}
			String fdInvoiceCode = (String)detail[2];
			String fdInvoiceNumber = (String)detail[1];
			String hql="select fdNoteId from com.landray.kmss.fssc.ledger.model.FsscLedgerInvoice where fdModelId=:fdModelId and fdInvoiceNumber=:fdInvoiceNumber ";
			if(StringUtil.isNotNull(fdInvoiceCode)){
				hql+=" and fdInvoiceCode=:fdInvoiceCode";
			}
			Query query = getEopBasedataCostCenterService().getBaseDao().getHibernateSession().createQuery(hql);
			query.setParameter("fdModelId", main.getFdId());
			query.setParameter("fdInvoiceNumber", fdInvoiceNumber);
			if(StringUtil.isNotNull(fdInvoiceCode)){
				query.setParameter("fdInvoiceCode", fdInvoiceCode);
			}
			List<String> objList=query.list();
			if(!ArrayUtil.isEmpty(objList)&&objList.get(0)!=null) {
				hql = "update com.landray.kmss.fssc.ledger.model.FsscLedgerInvoice set fdModelId=:fdNoteId,fdModelName=:fdModelName where fdModelId=:fdModelId and fdInvoiceNumber=:fdInvoiceNumber ";
			}else {
				hql = "update com.landray.kmss.fssc.ledger.model.FsscLedgerInvoice set fdModelId=null,fdModelName=null where fdModelId=:fdModelId and fdInvoiceNumber=:fdInvoiceNumber ";
			}
			if(StringUtil.isNotNull(fdInvoiceCode)){
				hql+=" and fdInvoiceCode=:fdInvoiceCode";
			}
			query = getEopBasedataCostCenterService().getBaseDao().getHibernateSession().createQuery(hql);
			query.setParameter("fdModelId", main.getFdId());
			query.setParameter("fdInvoiceNumber", fdInvoiceNumber);
			if(!ArrayUtil.isEmpty(objList)&&objList.get(0)!=null) {
				query.setParameter("fdNoteId", String.valueOf(objList.get(0)));
				query.setParameter("fdModelName", "com.landray.kmss.fssc.mobile.model.FsscMobileNote");
			}
			if(StringUtil.isNotNull(fdInvoiceCode)){
				query.setParameter("fdInvoiceCode", fdInvoiceCode);
			}
			query.executeUpdate();
		}
		
	}

	//移动端每条费用类型对应一张发票信息
	public void addExpenseTemp(FsscExpenseMain main) throws Exception{
		List<FsscExpenseDetail> fdDetailList=main.getFdDetailList();
		for(FsscExpenseDetail detail:fdDetailList) {
			String fdNoteId=detail.getFdNoteId();
			if(StringUtil.isNull(fdNoteId)) {
				continue;
			}
			String _hql = "select fdInvoiceType,fdInvoiceNumber,fdInvoiceCode,fdBillingDate,fdJshj,fdTotalTax,"
					+ "fdPurchaserName,fdPurchaserTaxNo,fdJym  from FsscLedgerInvoice where fdNoteId=:fdNoteId";
			List<Object[]> invoiceList = getBaseDao().getHibernateSession().createQuery(_hql).setParameter("fdNoteId", fdNoteId).list(); 
			FsscExpenseTemp temp=new FsscExpenseTemp();
			String fdExpenseTempDetailIds="";
			List<FsscExpenseTempDetail> detailTempList=new ArrayList<FsscExpenseTempDetail>();
			for(int i=0,size=invoiceList.size();i<size;i++) {
				Object[] obj=invoiceList.get(i);
				FsscExpenseTempDetail detailTemp=new FsscExpenseTempDetail();
				detailTemp.setFdInvoiceType(obj[0]!=null?String.valueOf(obj[0]):null);
				detailTemp.setFdInvoiceNumber(obj[1]!=null?String.valueOf(obj[1]):null);
				detailTemp.setFdCompanyId(main.getFdCompany()!=null?main.getFdCompany().getFdId():null);
				EopBasedataExpenseItem expenseItem=detail.getFdExpenseItem();
				if(expenseItem!=null) {
					detailTemp.setFdExpenseTypeId(expenseItem.getFdId());
					detailTemp.setFdExpenseTypeName(expenseItem.getFdName());
				}
				detailTemp.setFdIsVat(detail.getFdIsDeduct()?String.valueOf(true):String.valueOf(false));
				detailTemp.setFdNonDeductMoney(detail.getFdNonDeductMoney());
				detailTemp.setFdInvoiceCode(obj[2]!=null?String.valueOf(obj[2]):null);
				String invoiceDate=obj[3]!=null?String.valueOf(obj[3]):null;
				if(invoiceDate!=null) {
					detailTemp.setFdInvoiceDate(DateUtil.convertStringToDate(invoiceDate, DateUtil.PATTERN_DATE));
				}
				detailTemp.setFdInvoiceMoney(obj[4]!=null?Double.parseDouble(String.valueOf(obj[4])):null);
				detailTemp.setFdTaxMoney(obj[5]!=null?Double.parseDouble(String.valueOf(obj[5])):null);  //税额
				detailTemp.setFdPurchName(obj[6]!=null?String.valueOf(obj[6]):null);  //购方名称
				detailTemp.setFdTaxNumber(obj[7]!=null?String.valueOf(obj[7]):null);  //购方税号
				detailTemp.setFdCheckCode(obj[8]!=null?String.valueOf(obj[8]):null);  //校验码
				fdExpenseTempDetailIds=StringUtil.linkString(fdExpenseTempDetailIds, ";", detailTemp.getFdId());
				detailTempList.add(detailTemp);
			}
			temp.setFdInvoiceListTemp(detailTempList);
			getFsscExpenseTempService().getBaseDao().add(temp);
			detail.setFdExpenseTempDetailIds(fdExpenseTempDetailIds);
		}
	}
	
	/**
	 * 发票验真状态赋值
	 * @param main
	 * @throws Exception
	 */
	public  void updateInvoiceDetailCheckStatus(FsscExpenseMain main) throws Exception{
		List<FsscExpenseInvoiceDetail> invoiceList=main.getFdInvoiceList();
		JSONObject checkStatusObj=new JSONObject();
		JSONObject stateStatusObj=new JSONObject();
		if(!ArrayUtil.isEmpty(invoiceList)) {
		  List<String> keyList=new ArrayList<>();
		  for(FsscExpenseInvoiceDetail invoice:invoiceList) {
			  keyList.add((StringUtil.isNotNull(invoice.getFdInvoiceNumber())?invoice.getFdInvoiceNumber():"")
					  +(StringUtil.isNotNull(invoice.getFdInvoiceCode())?invoice.getFdInvoiceCode():""));
		  }
		  if(FsscCommonUtil.checkHasModule("/fssc/ledger/")) {
			  JSONObject statusObj=getFsscCommonLedgerService().getCheckStatusJson(keyList);
			  checkStatusObj=statusObj.optJSONObject("checkStatusObj");
			  stateStatusObj=statusObj.optJSONObject("stateStatusObj");
		  }
		}
		for(FsscExpenseInvoiceDetail invoice:invoiceList) {
			String checkStatus = checkStatusObj
					.optString((StringUtil.isNotNull(invoice.getFdInvoiceNumber()) ? invoice.getFdInvoiceNumber() : "")
							+ (StringUtil.isNotNull(invoice.getFdInvoiceCode()) ? invoice.getFdInvoiceCode() : ""));
			if (StringUtil.isNull(checkStatus)) {
				checkStatus = "0"; // 为空设置为未验真
			}
			invoice.setFdCheckStatus(checkStatus);
			String stateStatus = stateStatusObj
					.optString((StringUtil.isNotNull(invoice.getFdInvoiceNumber()) ? invoice.getFdInvoiceNumber() : "")
							+ (StringUtil.isNotNull(invoice.getFdInvoiceCode()) ? invoice.getFdInvoiceCode() : ""));
			if (StringUtil.isNull(stateStatus)) {
				stateStatus = "0"; // 为空设置为正常
			}
			invoice.setFdState(stateStatus);
		}
	}
	
	/**
	 * 获取报销类审批单
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
    public JSONObject getExpenseMainList(HttpServletRequest request) throws Exception {
		JSONObject rtn = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			arr = getFsscCommonExpenseService().getMyExpenseList(UserUtil.getKMSSUser().getUserId());
			rtn.put("data", arr);
			rtn.put("result", "success");
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", "获取报销列表发生错误.");
			logger.error("获取报销列表发生错误.", e);
		}
		return rtn;
	}

	/**
	 * 获取报销类别列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
    public JSONObject getExpenseCateList(HttpServletRequest request) throws Exception {
		JSONObject rtn=new JSONObject();
		try {
			String hql = " fsscExpenseCategory.fdIsMobile=:fdIsMobile";
			String keyword = request.getParameter("keyword");
			if(StringUtil.isNotNull(keyword)){
				hql+=" and fsscExpenseCategory.fdName like:keyword";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(hql);
			hqlInfo.setParameter("fdIsMobile",true);
			if(StringUtil.isNotNull(keyword)){
				hqlInfo.setParameter("keyword","%"+keyword+"%");
			}
			hqlInfo.setRowSize(1000);
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
				data.add(obj);
			}
			rtn.put("result", "success");
			rtn.put("data", data);
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", "获取报销类别列表发生错误.");
			logger.error("获取报销类别列表发生错误.", e);
		}
		return rtn;
	}
	
	/**
	 * 获取住址架构父节点id
	 */
	@Override
    public JSONObject getParentId(String fdId ) throws Exception {
		JSONObject rtn=new JSONObject();
		try {
			String hql = "from com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement where sysOrgElement.fdId=:fdId and fdIsAvailable=:fdIsAvailable ";
			Query query =	getBaseDao().getHibernateSession().createQuery(hql);
			query.setParameter("fdId", fdId);
			query.setParameter("fdIsAvailable", true);
			List list = query.list();
			if(!list.isEmpty()){
				SysOrgElement sysOrgElement = (SysOrgElement) list.get(0);
				rtn.put("fdId", sysOrgElement.getFdParent()==null?"":sysOrgElement.getFdParent().getFdId());
				rtn.put("fdName", sysOrgElement.getFdParent()==null?"":sysOrgElement.getFdParent().getFdName());
			}
			rtn.put("result", "success");
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", "获取报销类别列表发生错误.");
			logger.error("获取报销类别列表发生错误.", e);
		}
		return rtn;
	}
	
	/**
     * 
     * 保存附件
     * 
     */
  	@Override
    public JSONObject saveAtt(HttpServletRequest request) throws Exception{
  		String fdId = request.getParameter("fdId");
  		String key = request.getParameter("key");
  		if(StringUtil.isNull(key)){
  			key = "invoice";
  		}
  		System.out.println("附件ID:"+fdId);
  		String fdModelId = request.getParameter("fdModelId");
  		String fdModelName = request.getParameter("fdModelName");
  		String filename = request.getParameter("filename");
  		String[] filenameArr = filename.split("\\.");
  		InputStream in = request.getInputStream();
  		SysAttMain att = new SysAttMain();
  		att.setInputStream(in);
  		att.setFdFileName(filename);
//  		att.setFdModelName(fdModelName);
  		att.setFdKey(key);
  		att.setFdId(fdId);
  		att.setFdAttType("byte");
//  		att.setFdModelId(fdModelId);
		if(FsscCommonUtil.isImageFile(filename)){
			att.setFdContentType("image/"+filenameArr[1]);
		}else{
			att.setFdContentType("application/"+filenameArr[1]);
		}
  		String size=request.getParameter("size");
  		if(StringUtil.isNotNull(size)){
  			att.setFdSize(Double.parseDouble(size));
  		}else{
  			att.setFdSize(new Integer(in.available()).doubleValue());
  		}
  		att.setDocCreateTime(new Date());
  		getSysAttMainService().add(att);
  		JSONObject rtn = new JSONObject();
  		rtn.put("fdId", att.getFdId());
  		rtn.put("fdName", filename);
  		return rtn;
  	}


	@Override
	public JSONObject deleteAtt(HttpServletRequest request) throws Exception {
		JSONObject rtn = new JSONObject();
		String fdId = request.getParameter("fdId");
		try {
			getSysAttMainService().deleteAtt(fdId);
			rtn.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			rtn.put("result", "failure");
		}
		return rtn;
	}

	/**
	 * 更新关联记一笔的信息
	 */
	@Override
	public JSONObject updateRelation(Map<String,String>map) throws Exception {
		JSONObject rtn = new JSONObject();
		try {
			getFsscCommonMobileService().updateRelationNote( map);
			rtn.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			rtn.put("result", "failure");
		}
		return rtn;
	}

	@Override
	public void updateAttachmentRelation(String[] attId,String fdModelId) throws Exception {
		List<SysAttMain> attList = getBaseDao().getHibernateSession().createQuery("from "+SysAttMain.class.getName()+" where fdId in(:ids)")
				.setParameterList("ids", Arrays.asList(attId)).list();
		List<String> ids = new ArrayList<String>();
		for(SysAttMain att:attList){
			//如果是随手记带过来的附件，复制一份
			if(StringUtil.isNotNull(att.getFdModelName())&&(att.getFdModelName().contains("FsscOcrInfo")||att.getFdModelName().contains("FsscMobileNote"))){
				SysAttMain attNew = new SysAttMain();
				attNew.setDocCreateTime(new Date());
				attNew.setFdAttType(att.getFdAttType());
				attNew.setFdContentType(att.getFdContentType());
				attNew.setFdCreatorId(att.getFdCreatorId());
				attNew.setFdData(att.getFdData());
				attNew.setFdDataId(att.getFdDataId());
				attNew.setFdFileId(att.getFdFileId());
				attNew.setFdFileName(att.getFdFileName());
				attNew.setFdFilePath(att.getFdFilePath());
				attNew.setFdKey("invoice");
				attNew.setFdModelId(fdModelId);
				attNew.setFdModelName(FsscExpenseMain.class.getName());
				attNew.setFdSize(att.getFdSize());
				attNew.setInputStream(att.getInputStream());
				getSysAttMainService().add(attNew);
			}else{
				ids.add(att.getFdId());
			}
		}
		if(!ArrayUtil.isEmpty(ids)){
			String hql = "update "+SysAttMain.class.getName()+" set fdModelName=:fdModelName,fdModelId=:fdModelId where fdId in(:ids)";
			Query query = getBaseDao().getHibernateSession().createQuery(hql);
			query.setParameter("fdModelName", FsscExpenseMain.class.getName());
			query.setParameter("fdModelId", fdModelId);
			query.setParameterList("ids", ids);
			query.executeUpdate();
		}
	}

	/**
	 * 获取进项税率
	 */
	@Override
	public JSONArray getFdInputTax(JSONObject param) throws Exception {
		JSONArray taxArr=new JSONArray();
		HQLInfo hqlInfo=new HQLInfo();
		String fdExpenseItemId = param.optString("fdExpenseItemId");
		if(StringUtil.isNotNull(fdExpenseItemId)){
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " it.fdId=:fdExpenseItemId "));
			hqlInfo.setParameter("fdExpenseItemId", fdExpenseItemId);
		}
		hqlInfo.setJoinBlock("left join eopBasedataInputTax.fdCompanyList comp left join eopBasedataInputTax.fdItem it");
		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
				"(comp.fdId=:fdCompanyId or comp.fdId is null)"));
		hqlInfo.setParameter("fdCompanyId", param.optString("fdCompanyId"));
		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
				" eopBasedataInputTax.fdIsAvailable = :fdIsAvailable and eopBasedataInputTax.fdIsInputTax = :fdIsInputTax"));
        hqlInfo.setParameter("fdIsAvailable", true);
        hqlInfo.setParameter("fdIsInputTax", true);
        List<EopBasedataInputTax> taxList=getEopBasedataInputTaxService().findList(hqlInfo);
        for(EopBasedataInputTax tax:taxList){
        	JSONObject obj=new JSONObject();
    		obj.put("value", tax.getFdTaxRate());
    		obj.put("text", tax.getFdTaxRate()+"%");
    		taxArr.add(obj);
        }
		return taxArr;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String,Object>> rtn = new ArrayList<Map<String,Object>>();
		String type=requestInfo.getParameter("type");
		if("getTmpInfo".contentEquals(type)) {
			String fdExpenseTempId = requestInfo.getParameter("fdExpenseTempId");
			FsscExpenseTemp temp = (FsscExpenseTemp) findByPrimaryKey(fdExpenseTempId, FsscExpenseTemp.class, true);
			if(temp!=null) {
				Map<String,Object> data = new HashMap<String,Object>();
				List<FsscExpenseTempDetail> details = temp.getFdInvoiceListTemp();
				JSONArray invoices = new JSONArray();
				for(FsscExpenseTempDetail detail:details) {
					JSONObject inv = new JSONObject();
					inv.put("fdExpenseItemName", detail.getFdExpenseTypeName());
					inv.put("fdType", EnumerationTypeUtil.getColumnEnumsLabel("fssc_invoice_type", detail.getFdInvoiceType()));
					inv.put("fdInvoiceNumber", detail.getFdInvoiceNumber());
					inv.put("fdInvoiceCode", detail.getFdInvoiceCode());
					inv.put("fdInvoiceMoney", detail.getFdInvoiceMoney());
					invoices.add(inv);
				}
				data.put("invoices", invoices);
				JSONArray attachments = new JSONArray();
				List<SysAttMain> atts = getSysAttMainService().findByModelKey(FsscExpenseTemp.class.getName(), temp.getFdId(),"attInvoice");
				for(SysAttMain att:atts) {
					JSONObject a = new JSONObject();
					a.put("fdName", att.getFdFileName());
					a.put("fdId", att.getFdId());
					attachments.add(a);
				}
				data.put("attachments", attachments);
				rtn.add(data);
			}
		} else if ("addTmpInfo".contentEquals(type)) {
			String fdNoteId = requestInfo.getParameter("fdNoteId");
			String fdMainId = requestInfo.getParameter("fdMainId");
			JSONObject obj = getFsscCommonMobileService().getMobileNote(fdNoteId);
			if (null != obj) {
				String result = obj.getString("result");
				if ("success".equals(result)) {
					JSONArray noteArr = (JSONArray) obj.get("data");
					FsscExpenseTemp temp = new FsscExpenseTemp();
					List<FsscExpenseTempDetail> tmpDetailList = new ArrayList<FsscExpenseTempDetail>();
					StringBuffer tmpDetailIds = new StringBuffer();
					JSONObject note = noteArr.getJSONObject(0);
					String fdExpenseItemId = note.optString("fdExpenseItemId");
					EopBasedataExpenseItem item = null;
					if (StringUtil.isNotNull(fdExpenseItemId)) {
						item = (EopBasedataExpenseItem) getEopBasedataCostCenterService().findByPrimaryKey(fdExpenseItemId,
								EopBasedataExpenseItem.class, true);
					}
					// 发票信息
					JSONArray invoiceArr = (JSONArray) note.get("invoices");
					if(invoiceArr!=null) {
						for (Object object : invoiceArr) {
							JSONObject invoice = (JSONObject) object;
							FsscExpenseTempDetail tmpDetail = new FsscExpenseTempDetail();
							if (tmpDetailIds.length() > 0) {
								tmpDetailIds.append(";");
							}
							tmpDetailIds.append(tmpDetail.getFdId());
							if (item != null) {
								tmpDetail.setFdExpenseTypeId(item.getFdId());
								tmpDetail.setFdExpenseTypeName(item.getFdName());
							}
							tmpDetail.setDocMain(temp);
							tmpDetail.setFdCompanyId(invoice.optString("fdCompanyId"));
							tmpDetail.setFdInvoiceCode(invoice.optString("fdInvoiceCode"));
							tmpDetail.setFdInvoiceType(invoice.optString("fdInvoiceTypeId"));
							String fdInvoiceDate = invoice.optString("fdInvoiceDate");
							if (StringUtil.isNotNull(fdInvoiceDate)) {
								tmpDetail.setFdInvoiceDate(
										DateUtil.convertStringToDate(fdInvoiceDate, DateUtil.PATTERN_DATE));
							}
							Double fdInvoiceMoney = (Double) invoice.opt("fdInvoiceMoney");
							fdInvoiceMoney = fdInvoiceMoney == null ? 0d : fdInvoiceMoney;
							Double fdTax = (Double) invoice.opt("fdTax");
							fdTax = fdTax == null ? 0d : fdTax;
							Double fdTaxRate = FsscNumberUtil.getDivide(fdTax,
									FsscNumberUtil.getSubtraction(fdInvoiceMoney, fdTax), 2);
							Double fdNoTax = (Double) invoice.opt("fdNoTax");
							tmpDetail.setFdTax(String.valueOf(FsscNumberUtil.getMultiplication(fdTaxRate, 100,2)));
							tmpDetail.setFdInvoiceMoney(fdInvoiceMoney);
							tmpDetail.setFdInvoiceNumber(invoice.optString("fdInvoiceNo"));
							tmpDetail.setFdCheckCode(invoice.optString("fdCheckCode"));
							tmpDetail.setFdTaxMoney(fdTax);
							tmpDetail.setFdNoTaxMoney(fdNoTax);
							tmpDetail.setDocMain(temp);
							tmpDetailList.add(tmpDetail);
						}
					}
					temp.setFdInvoiceListTemp(tmpDetailList);
					temp.setFdMainId(fdMainId);
					getFsscExpenseTempService().add(temp);
					// 附件信息
					JSONArray attachmentArr = (JSONArray) note.get("attachments");
					if(attachmentArr!=null) {
						String[] attIds = new String[attachmentArr.size()];
						for (int k = 0; k < attachmentArr.size(); k++) {
							JSONObject att = attachmentArr.getJSONObject(k);
							attIds[k] = att.getString("value");
						}
						List<SysAttMain> attList = getSysAttMainService().findByPrimaryKeys(attIds);
						List<SysAttMain> copyList = new ArrayList<SysAttMain>();
						for (SysAttMain att : attList) {
							SysAttMain copyAtt = getSysAttMainService().clone(att);
							copyAtt.setFdKey("attInvoice");
							copyAtt.setFdModelId(temp.getFdId());
							copyAtt.setFdModelName(FsscExpenseTemp.class.getName());
							copyList.add(copyAtt);
						}
						getSysAttMainService().add(copyList);
					}
					Map<String,Object> map =new HashMap<String,Object>();
					map.put("fdExpenseTempId", temp.getFdId());
					map.put("fdExpenseTempDetailIds", tmpDetailIds.toString());
					rtn.add(map);
				}
			}
		}
		return rtn;
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext) throws Exception {
		String  fdId = super.add(form, requestContext);
		String fdSubmitTypeId = requestContext.getParameter("fdSubmitTypeId");
		if(StringUtil.isNotNull(fdSubmitTypeId)) {
			ProcessExecuteService processExecuteService = (ProcessExecuteService) SpringBeanUtil
					.getBean("lbpmProcessExecuteService");
			ProcessInstanceInfo load = processExecuteService.load(fdId);
			LbpmProcess processInstance = (LbpmProcess)load.getProcessInstance();
			SysOrgElement ele = getSysOrgCoreService().findByPrimaryKey(fdSubmitTypeId);
			processInstance.setFdIdentity(ele);
			AccessManager accessManager = (AccessManager) SpringBeanUtil
					.getBean("accessManager");
			accessManager.update(processInstance);
		}
		return fdId;
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext) throws Exception {
		FsscExpenseMainForm expenseForm = (FsscExpenseMainForm) form;
		super.update(form, requestContext);
		String fdSubmitTypeId = requestContext.getParameter("fdSubmitTypeId");
		if(StringUtil.isNotNull(fdSubmitTypeId)) {
			ProcessExecuteService processExecuteService = (ProcessExecuteService) SpringBeanUtil
					.getBean("lbpmProcessExecuteService");
			ProcessInstanceInfo load = processExecuteService.load(expenseForm.getFdId());
			LbpmProcess processInstance = (LbpmProcess)load.getProcessInstance();
			SysOrgElement ele = getSysOrgCoreService().findByPrimaryKey(fdSubmitTypeId);
			processInstance.setFdIdentity(ele);
			AccessManager accessManager = (AccessManager) SpringBeanUtil
					.getBean("accessManager");
			accessManager.update(processInstance);
		}
	}
	
}
