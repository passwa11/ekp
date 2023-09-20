package com.landray.kmss.fssc.expense.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.imp.EopBasedataImportUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataAccount;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataPayWay;
import com.landray.kmss.eop.basedata.model.EopBasedataPayment;
import com.landray.kmss.eop.basedata.model.EopBasedataPaymentDetail;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostCenterService;
import com.landray.kmss.eop.basedata.service.IEopBasedataExchangeRateService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPaymentDataService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPaymentService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBaiwangService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonCashierPaymentService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonCcardService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonFeeService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonInvoiceService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonIqubicService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLedgerService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLoanService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonMobileService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonNuoService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonOcrService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProappService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProvisionService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonVoucherService;
import com.landray.kmss.fssc.common.util.CompressImage;
import com.landray.kmss.fssc.common.util.FsscCommonParsePdfUtil;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.expense.forms.FsscExpenseDetailForm;
import com.landray.kmss.fssc.expense.forms.FsscExpenseMainForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseAccounts;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseInvoiceDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.model.FsscExpenseOffsetLoan;
import com.landray.kmss.fssc.expense.model.FsscExpenseTemp;
import com.landray.kmss.fssc.expense.model.FsscExpenseTempDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseTranData;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseTempDetailService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseTempService;
import com.landray.kmss.fssc.expense.util.FsscExpenseUtil;
import com.landray.kmss.sys.archives.config.SysArchivesConfig;
import com.landray.kmss.sys.archives.interfaces.IArchAutoFileDataService;
import com.landray.kmss.sys.archives.interfaces.IArchFileDataService;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.archives.model.SysArchivesParamModel;
import com.landray.kmss.sys.archives.service.ISysArchivesFileTemplateService;
import com.landray.kmss.sys.archives.util.SysArchivesUtil;
import com.landray.kmss.sys.attachment.io.DecryptionInputStream;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmNode;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmWorkitem;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.query.Query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FsscExpenseMainServiceImp extends ExtendDataServiceImp implements IFsscExpenseMainService,IEopBasedataPaymentDataService,IXMLDataBean, IArchAutoFileDataService, IArchFileDataService {

	private IFsscCommonIqubicService fsscCommonIqubicService;
	public IFsscCommonIqubicService getFsscCommonIqubicService() {
		if (fsscCommonIqubicService == null) {
			fsscCommonIqubicService = (IFsscCommonIqubicService) SpringBeanUtil.getBean("fsscIqubicCommonService");
		}
		return fsscCommonIqubicService;
	}
	private IFsscCommonProappService fsscCommonProappService;

	public IFsscCommonProappService getFsscCommonProappService() {
		if (fsscCommonProappService == null) {
			fsscCommonProappService = (IFsscCommonProappService) SpringBeanUtil.getBean("fsscProappCommonService");
		}
		return fsscCommonProappService;
	}

	private IFsscCommonFeeService fsscCommonFeeService;

	public IFsscCommonFeeService getFsscCommonFeeService() {
		if (fsscCommonFeeService == null) {
			fsscCommonFeeService = (IFsscCommonFeeService) SpringBeanUtil.getBean("fsscCommonFeeService");
		}
		return fsscCommonFeeService;
	}

	private IFsscCommonLedgerService fsscCommonLedgerService;
	public IFsscCommonLedgerService getFsscCommonLedgerService() {
		if (fsscCommonLedgerService == null) {
			fsscCommonLedgerService = (IFsscCommonLedgerService) SpringBeanUtil.getBean("fsscCommonLedgerService");
		}
		return fsscCommonLedgerService;
	}

	private IFsscCommonLoanService fsscCommonLoanService;

	public IFsscCommonLoanService getFsscCommonLoanService() {
		if (fsscCommonLoanService == null) {
			fsscCommonLoanService = (IFsscCommonLoanService) SpringBeanUtil.getBean("fsscCommonLoanService");
		}
		return fsscCommonLoanService;
	}

	private IFsscCommonBudgetOperatService fsscBudgetOperatService;

	public IFsscCommonBudgetOperatService getFsscBudgetOperatService() {
		if(fsscBudgetOperatService==null){
			fsscBudgetOperatService = (IFsscCommonBudgetOperatService) SpringBeanUtil.getBean("fsscBudgetOperatService");
		}
		return fsscBudgetOperatService;
	}

	private IEopBasedataPaymentService eopBasedataPaymentService;
	public void setEopBasedataPaymentService(IEopBasedataPaymentService eopBasedataPaymentService) {
		this.eopBasedataPaymentService = eopBasedataPaymentService;
	}

	private ISysNumberFlowService sysNumberFlowService;

	protected ILbpmProcessService lbpmProcessService;

	public void setLbpmProcessService(ILbpmProcessService lbpmProcessService) {
		this.lbpmProcessService = lbpmProcessService;
	}

	protected IBackgroundAuthService backgroundAuthService;

	public void setBackgroundAuthService(IBackgroundAuthService backgroundAuthService) {
		this.backgroundAuthService = backgroundAuthService;
	}

	private IFsscCommonCashierPaymentService fsscCommonCashierPaymentService;

	public IFsscCommonCashierPaymentService getFsscCommonCashierPaymentService() {
		if(fsscCommonCashierPaymentService==null){
			fsscCommonCashierPaymentService = (IFsscCommonCashierPaymentService) SpringBeanUtil.getBean("fsscCommonCashierPaymentService");
		}
		return fsscCommonCashierPaymentService;
	}

	private IFsscCommonVoucherService fsscCommonVoucherService;

	public IFsscCommonVoucherService getFsscCommonVoucherService() {
		if(fsscCommonVoucherService==null){
			fsscCommonVoucherService = (IFsscCommonVoucherService) SpringBeanUtil.getBean("fsscCommonVoucherService");
		}
		return fsscCommonVoucherService;
	}

	private IEopBasedataExchangeRateService eopBasedataExchangeRateService;


	public IEopBasedataExchangeRateService getEopBasedataExchangeRateService() {
		if(eopBasedataExchangeRateService==null){
			eopBasedataExchangeRateService = (IEopBasedataExchangeRateService) SpringBeanUtil.getBean("eopBasedataExchangeRateService");
		}
		return eopBasedataExchangeRateService;
	}

	private IFsscCommonOcrService fsscCommonOcrService;

	public IFsscCommonOcrService getFsscCommonOcrService() {
		if(fsscCommonOcrService==null){
			fsscCommonOcrService = (IFsscCommonOcrService) SpringBeanUtil.getBean("fsscOcrCommonService");
		}
		return fsscCommonOcrService;
	}

	public IFsscCommonNuoService fsscCommonNuoService;

	public IFsscCommonNuoService getFsscCommonNuoService() {
		if (fsscCommonNuoService == null) {
			fsscCommonNuoService = (IFsscCommonNuoService) SpringBeanUtil.getBean("fsscCommonNuoService");
		}
		return fsscCommonNuoService;
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

	public IFsscExpenseTempService fsscExpenseTempService;

	public IFsscExpenseTempService getFsscExpenseTempService() {
		if(fsscExpenseTempService==null){
			fsscExpenseTempService = (IFsscExpenseTempService) SpringBeanUtil.getBean("fsscExpenseTempService");
		}
		return fsscExpenseTempService;
	}

	private IFsscExpenseTempDetailService fsscExpenseTempDetailService;

	public IFsscExpenseTempDetailService getFsscExpenseTempDetailService() {
		if(fsscExpenseTempDetailService==null){
			fsscExpenseTempDetailService = (IFsscExpenseTempDetailService) SpringBeanUtil.getBean("fsscExpenseTempDetailService");
		}
		return fsscExpenseTempDetailService;
	}

	public IFsscCommonBaiwangService fsscCommonBaiwangService;

	public IFsscCommonBaiwangService getFsscCommonBaiwangService() {
		if (fsscCommonBaiwangService == null) {
			fsscCommonBaiwangService = (IFsscCommonBaiwangService) SpringBeanUtil.getBean("fsscCommonBaiwangService");
		}
		return fsscCommonBaiwangService;
	}

	public IFsscCommonMobileService fsscCommonMobileService;

	public IFsscCommonMobileService getFsscCommonMobileService() {
		if(fsscCommonMobileService==null){
			fsscCommonMobileService = (IFsscCommonMobileService) SpringBeanUtil.getBean("fsscCommonMobileService");
		}
		return fsscCommonMobileService;
	}

	private IFsscCommonProvisionService fsscCommonProvisionService;

	public IFsscCommonProvisionService getFsscCommonProvisionService() {
		if(fsscCommonProvisionService==null){
			fsscCommonProvisionService = (IFsscCommonProvisionService) SpringBeanUtil.getBean("fsscProvisionCommonService");
		}
		return fsscCommonProvisionService;
	}

	private IFsscCommonInvoiceService fsscInvoiceCommonService;

	public IFsscCommonInvoiceService getFsscInvoiceCommonService() {
		if (fsscInvoiceCommonService == null) {
			fsscInvoiceCommonService = (IFsscCommonInvoiceService) SpringBeanUtil.getBean("fsscInvoiceCommonService");
		}
		return fsscInvoiceCommonService;
	}

	private IFsscCommonCcardService fsscCommonCcardService;

	public IFsscCommonCcardService getFsscCommonCcardService() {
		if (fsscCommonCcardService == null) {
			fsscCommonCcardService = (IFsscCommonCcardService) SpringBeanUtil.getBean("fsscCommonCcardService");
		}
		return fsscCommonCcardService;
	}

	private ISysAttMainCoreInnerService sysAttMainService = null;

	private ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	private ISysAttUploadService sysAttUploadService;

	public ISysAttUploadService getSysAttUploadService() {
		if (sysAttUploadService == null) {
			sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
		}
		return sysAttUploadService;
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
				fsscExpenseMain.setDocNumber(sysNumberFlowService.generateFlowNumber(fsscExpenseMain));
			}
		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		FsscExpenseMain fsscExpenseMain = new FsscExpenseMain();
		fsscExpenseMain.setDocCreateTime(new Date());
		fsscExpenseMain.setDocCreator(UserUtil.getUser());
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
			//默认成本中心
			EopBasedataCostCenter costsOwn=getEopBasedataCostCenterService().findCostCenterByUserId(own.get(0).getFdId(), user.getFdId());
			if(costsOwn!=null){
				fsscExpenseMain.setFdCostCenter(costsOwn);
			}
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
				fsscExpenseMain.setFdAccountsList(new ArrayList());
				//设置默认币种汇率
				if(fsscExpenseMain.getFdCompany()!=null){
					Double rate = getEopBasedataExchangeRateService().getRateByAccountCurrency(fsscExpenseMain.getFdCompany(), fsscExpenseMain.getFdCompany().getFdAccountCurrency().getFdId());
					acc.setFdCurrency(fsscExpenseMain.getFdCompany().getFdAccountCurrency());
					acc.setFdExchangeRate(rate);
				}
				//设置默认付款方式
				if(fsscExpenseMain.getFdCompany()!=null){
					List<EopBasedataPayWay> payWayList=getBaseDao().getHibernateSession().createQuery("select pay from EopBasedataPayWay pay left join pay.fdCompanyList comp where  pay.fdIsDefault=:fdIsDefault and (comp.fdId=:fdCompanyId or comp.fdId is null) and pay.fdStatus=:fdIsAvailable")
							.setParameter("fdIsDefault", true).setParameter("fdIsAvailable", 0).setParameter("fdCompanyId", fsscExpenseMain.getFdCompany().getFdId()).list();
					if(!ArrayUtil.isEmpty(payWayList)){
						payWayList = EopBasedataFsscUtil.sortByCompany(payWayList);
						acc.setFdPayWay(payWayList.get(0));
						if(payWayList.get(0)!=null){
							acc.setFdBank(payWayList.get(0).getFdDefaultPayBank());
							requestContext.setAttribute("fdIsTransfer", payWayList.get(0).getFdIsTransfer());	//是否涉及转账
						}
					}
				}
				fsscExpenseMain.getFdAccountsList().add(acc);
			}
		}
		String fdFeeMainId = requestContext.getParameter("fdFeeMainId");
		if(StringUtil.isNotNull(fdFeeMainId)){
			JSONObject fee = getFsscCommonFeeService().getFeeInfoById(fdFeeMainId);
			if(fee.containsKey("docSubject")){
				fsscExpenseMain.setFdFeeIds(fdFeeMainId);
				fsscExpenseMain.setFdFeeNames(fee.getString("docSubject"));
			}
		}
		if(fsscExpenseMain.getFdAttNumber()==null){
			fsscExpenseMain.setFdAttNumber(0);
		}
		return fsscExpenseMain;
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
    public List<FsscExpenseMain> findByDocTemplate(FsscExpenseCategory docTemplate) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fsscExpenseMain.docTemplate.fdId=:fdId");
		hqlInfo.setParameter("fdId", docTemplate.getFdId());
		return this.findList(hqlInfo);
	}

	@Override
    public List<FsscExpenseMain> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fsscExpenseMain.fdCompany.fdId=:fdId");
		hqlInfo.setParameter("fdId", fdCompany.getFdId());
		return this.findList(hqlInfo);
	}

	@Override
    public List<FsscExpenseMain> findByFdCostCenter(EopBasedataCostCenter fdCostCenter) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fsscExpenseMain.fdCostCenter.fdId=:fdId");
		hqlInfo.setParameter("fdId", fdCostCenter.getFdId());
		return this.findList(hqlInfo);
	}

	@Override
    public List<FsscExpenseMain> findByFdProject(EopBasedataProject fdProject) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fsscExpenseMain.fdProject.fdId=:fdId");
		hqlInfo.setParameter("fdId", fdProject.getFdId());
		return this.findList(hqlInfo);
	}

	public void setSysNumberFlowService(ISysNumberFlowService sysNumberFlowService) {
		this.sysNumberFlowService = sysNumberFlowService;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		FsscExpenseMain main = (FsscExpenseMain) modelObj;
		if (StringUtil.isNull(main.getDocNumber()) && !SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())) {
			main.setDocNumber(sysNumberFlowService.generateFlowNumber(main));
		}
		if(!SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())&&FsscCommonUtil.checkHasModule("/fssc/ledger/")){
			addOrUpdateInvoiceInfo(main);//保存发票，并更新为已使用状态
		}
		if(!SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())&&FsscCommonUtil.checkHasModule("/fssc/ccard/")){
			addOrUpdateTranData(main);//更新交易数据为已使用状态
		}
		if(main.getFdClaimantDept()==null){
			main.setFdClaimantDept(main.getFdClaimant().getFdParent());
		}
		FsscExpenseCategory cate = main.getDocTemplate();
		if(cate!=null&&"2".equals(cate.getFdSubjectType())&&StringUtil.isNull(main.getDocSubject())){
			FormulaParser parser = FormulaParser.getInstance(main);
			main.setDocSubject((String) parser.parseValueScript(cate.getFdSubjectRule()));
		}
		updateBudgetInfo(main);
		updateInvoiceDetailCheckStatus(main);
		deleteTempInvoiceDetail(main);//根据发票明细删除temp中多余的发票
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		FsscExpenseMain main = (FsscExpenseMain) modelObj;
		if (StringUtil.isNull(main.getDocNumber()) && !SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())) {
			main.setDocNumber(sysNumberFlowService.generateFlowNumber(main));
		}
		if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
			//先把全部发票置为未使用
			this.getBaseDao().getHibernateSession().createQuery("update FsscLedgerInvoice set fdUseStatus=:noUse where fdModelId=:fdModelId")
					.setParameter("noUse", "0").setParameter("fdModelId", main.getFdId()).executeUpdate();
			addOrUpdateInvoiceInfo(main);//保存发票，并更新为已使用状态
		}
		if(FsscCommonUtil.checkHasModule("/fssc/ccard/")){
			addOrUpdateTranData(main);//更新交易数据为已使用状态
		}
		FsscExpenseCategory cate = main.getDocTemplate();
		if(cate!=null&&"2".equals(cate.getFdSubjectType())&&StringUtil.isNotNull(main.getDocSubject())){
			FormulaParser parser = FormulaParser.getInstance(main);
			main.setDocSubject((String) parser.parseValueScript(cate.getFdSubjectRule()));
		}
		updateBudgetInfo(main);
		updateInvoiceDetailCheckStatus(main);
		deleteTempInvoiceDetail(main);//根据发票明细删除temp中多余的发票
		if(FsscCommonUtil.checkHasModule("/fssc/cashier/")) {
			getFsscCommonCashierPaymentService().deletePaymentTodo(main.getFdId(), "com.landray.kmss.fssc.expense.model.FsscExpenseMain");
		}
		super.update(modelObj);
	}

	@Override
    public void update(IExtendForm form, RequestContext requestContext) throws Exception {
		FsscExpenseMainForm mainForm=(FsscExpenseMainForm) form;
		if("true".equals(requestContext.getParameter("edit_examine"))) {
			//待审情况下，编辑主文档
			Map<String, String> customMap=new HashMap<>();
			customMap.put("edit20", "true");
			JSONObject detailBudgetOldObj=new JSONObject();
			AutoArrayList fdDetailList_FormList=mainForm.getFdDetailList_Form();
			String[] moneyArr=requestContext.getParameterValues("fdBudgetMoneyOld");
			Map<String, String[]> map = requestContext.getParameterMap();
			for(int n=0,size=fdDetailList_FormList.size();n<size;n++) {
				String[] old=map.get("fdDetailList_Form["+n+"].fdBudgetMoneyOld");
				if(old!=null&&old.length>0) {
					FsscExpenseDetailForm detailForm=(FsscExpenseDetailForm) fdDetailList_FormList.get(n);
					detailBudgetOldObj.put(detailForm.getFdId(), map.get("fdDetailList_Form["+n+"].fdBudgetMoneyOld")[0]);
				}
			}
			customMap.put("detailBudgetOldMap", detailBudgetOldObj.toString());
			mainForm.setCustomPropMap(customMap);
		}
		//选择未报费用，需要清空关联，处理删除明细的情况
		if(FsscCommonUtil.checkHasModule("/fssc/mobile/")){
			String hql = "update com.landray.kmss.fssc.mobile.model.FsscMobileNote set fdExpenseDetailId=null where ";
			hql+="fdExpenseDetailId in(select fdId from com.landray.kmss.fssc.expense.model.FsscExpenseDetail where docMain.fdId=:fdId)";
			getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdId", mainForm.getFdId()).executeUpdate();
		}
		super.update(mainForm, requestContext);
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

	@Override
    public  void addOrUpdateInvoiceInfo(FsscExpenseMain main) throws Exception{
		Map<String,String> infoMap=new HashMap<>();
		List<FsscExpenseInvoiceDetail> invoiceList=main.getFdInvoiceList();
		for(FsscExpenseInvoiceDetail detail:invoiceList){
			infoMap=new HashMap<>();
			infoMap.put("type",detail.getFdInvoiceType());
			infoMap.put("code",detail.getFdInvoiceCode());
			infoMap.put("number",detail.getFdInvoiceNumber());
			infoMap.put("date",DateUtil.convertDateToString(detail.getFdInvoiceDate(), DateUtil.PATTERN_DATE));
			infoMap.put("jshj",detail.getFdInvoiceMoney()!=null?String.valueOf(detail.getFdInvoiceMoney()):"0.0");
			infoMap.put("fdSl",detail.getFdTax()!=null?String.valueOf(detail.getFdTax()):"0.0");
			infoMap.put("tax",detail.getFdTaxMoney()!=null?String.valueOf(detail.getFdTaxMoney()):"0.0");
			infoMap.put("notax",detail.getFdNoTaxMoney()!=null?String.valueOf(detail.getFdNoTaxMoney()):"0.0");
			infoMap.put("fdModelId", main.getFdId());
			infoMap.put("fdModelName", FsscExpenseMain.class.getName());
			infoMap.put("fdUseStatus", "1");   //设置为已使用
			infoMap.put("CheckCode", detail.getFdCheckCode());
			infoMap.put("fdPurchaserName",detail.getFdPurchName());
			infoMap.put("fdPurchaserTaxNo",detail.getFdTaxNumber());
			getFsscCommonLedgerService().addOrUpdateInvoiceByPdf(infoMap);
		}
	}

	/**
	 * 更新交易数据状态为已使用
	 * @param main
	 * @throws Exception
	 */
	public void addOrUpdateTranData(FsscExpenseMain main) throws Exception{
		Map<String,String> map=new HashMap<>();
		List<FsscExpenseTranData> tranDataList=main.getFdTranDataList();
		for(FsscExpenseTranData detail:tranDataList){
			map=new HashMap<>();
			map.put("fdId", detail.getFdTranDataId());
			map.put("fdState", "1");   //设置为已使用
			getFsscCommonCcardService().addOrUpdateTranData(map);
		}
	}

	/**
	 * 获取报销单的发票对发票台账的发票ID，更新状态已使用
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	@Override
    public  String[] getInvoiceIds(FsscExpenseMain main) throws Exception{
		String[] invoiceArr={};
		List<FsscExpenseDetail> detailList=main.getFdDetailList();
		List<String> tempDetailIdsList=new ArrayList<String>();
		for(FsscExpenseDetail detail:detailList){
			String fdExpenseTempDetailIds=detail.getFdExpenseTempDetailIds();
			if(StringUtil.isNotNull(fdExpenseTempDetailIds)){
				tempDetailIdsList.addAll(ArrayUtil.convertArrayToList(fdExpenseTempDetailIds.split(";")));
			}
		}
		if(!ArrayUtil.isEmpty(tempDetailIdsList)){
			HQLInfo hqlInfo=new HQLInfo();
			hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("fsscExpenseTempDetail.fdId", tempDetailIdsList));
			hqlInfo.setSelectBlock("fsscExpenseTempDetail.fdInvoiceNumber");
			List<String> invoiceNumberList=getFsscExpenseTempDetailService().findList(hqlInfo);
			if(!ArrayUtil.isEmpty(invoiceNumberList)){
				List<String> invoiceIdList=getFsscExpenseTempDetailService().getBaseDao().getHibernateSession().createQuery("select t.fdId from FsscLedgerInvoice t where "+HQLUtil.buildLogicIN("t.fdInvoiceNumber", invoiceNumberList)).list();
				invoiceArr = new String[invoiceIdList.size()];
				if(!ArrayUtil.isEmpty(invoiceIdList)){
					invoiceIdList.toArray(invoiceArr);
				}
			}
		}
		return invoiceArr;
	}

	private String getFeeKey(JSONArray data){
		String[] ids = new String[data.size()];
		for(int i=0;i<ids.length;i++){
			ids[i] = data.getJSONObject(i).getString("fdLedgerId");
		}
		Arrays.sort(ids);
		return ids[0];
	}

	private Double getFeeMoney(JSONArray data){
		Double total = 0d;
		for(int i=0;i<data.size();i++){
			Double cur = data.getJSONObject(i).getDouble("fdUsableMoney");
			total = FsscNumberUtil.getAddition(total, cur);
		}
		return total;
	}

	@Override
    public void updateBudgetInfo(FsscExpenseMain main)throws Exception{
		if(!SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())){
			Map<String,Double> budgetMap = new HashMap<String,Double>();
			if(FsscCommonUtil.checkHasModule("/fssc/budget/")) {
				JSONObject param=new JSONObject();
				param.put("fdModelId", main.getFdId());
				getFsscBudgetOperatService().deleteFsscBudgetExecute(param);
			}
			if(FsscCommonUtil.checkHasModule("/fssc/proapp/")&&StringUtil.isNotNull(main.getFdProappId())){
				List<FsscExpenseDetail> list = main.getFdDetailList();
				EopBasedataProject project = main.getFdProject();
				JSONArray datas = new JSONArray();
				getFsscCommonProappService().deleteProappLedger(main.getFdId(),FsscExpenseMain.class.getName());
				for(FsscExpenseDetail detail:list){
					if(StringUtil.isNull(detail.getFdProappInfo())||detail.getFdApprovedApplyMoney()<0){
						continue;
					}
					if(main.getDocTemplate().getFdIsProjectShare()!=null&&main.getDocTemplate().getFdIsProjectShare()){
						project = detail.getFdProject();
					}
					JSONObject row = new JSONObject();
					row.put("fdModelId", main.getFdId());
					row.put("fdModelName",FsscExpenseMain.class.getName());
					row.put("fdModelSubject", main.getDocSubject());
					row.put("fdDetailId", detail.getFdId());
					row.put("fdHappenDate", DateUtil.convertDateToString(detail.getFdHappenDate(), DateUtil.PATTERN_DATE));
					row.put("fdType", "2");
					row.put("fdProjectApprovalId", main.getFdProappId());
					row.put("fdProjectApprovalSubject", main.getFdProappName());
					EopBasedataExpenseItem item = detail.getFdExpenseItem();
					if(item!=null){
						row.put("fdExpenseItemId", item.getFdId());
						row.put("fdExpenseItemName", item.getFdName());
						row.put("fdExpenseItemCode", item.getFdCode());
					}
					EopBasedataCompany comp = detail.getFdCompany();
					if(comp!=null){
						row.put("fdCompanyId", comp.getFdId());
						row.put("fdCompanyName", comp.getFdName());
						row.put("fdCompanyCode", comp.getFdCode());
						EopBasedataCompanyGroup group = comp.getFdGroup();
						if(group!=null){
							row.put("fdCompanyGroupId", group.getFdId());
							row.put("fdCompanyGroupName", group.getFdName());
							row.put("fdCopanyGroupCode", group.getFdCode());
						}
					}
					EopBasedataCostCenter cost = detail.getFdCostCenter();
					if(cost!=null){
						row.put("fdCostCenterId", cost.getFdId());
						row.put("fdCostCenterName", cost.getFdName());
						row.put("fdCostCenterCode", cost.getFdCode());
					}
					if(project!=null){
						row.put("fdProjectId", project.getFdId());
						row.put("fdProjectName", project.getFdName());
						row.put("fdProjectCode", project.getFdCode());
					}
					EopBasedataWbs wbs = detail.getFdWbs();
					if(wbs!=null){
						row.put("fdWbsId", wbs.getFdId());
						row.put("fdWbsName", wbs.getFdName());
						row.put("fdWbsCode", wbs.getFdCode());
					}
					EopBasedataInnerOrder order = detail.getFdInnerOrder();
					if(order!=null){
						row.put("fdInnerOrderId", order.getFdId());
						row.put("fdInnerOrderName", order.getFdName());
						row.put("fdInnerOrderCode", order.getFdCode());
					}
					SysOrgPerson person = detail.getFdRealUser();
					if(person!=null){
						row.put("fdPersonId", person.getFdId());
						row.put("fdPersonName", person.getFdName());
						row.put("fdPersonCode", person.getFdNo());
					}
					row.put("fdCurrency", detail.getFdCurrency()!=null?"":detail.getFdCurrency().getFdName());
					row.put("fdMoney", detail.getFdBudgetMoney());
					if(cost!=null&&item!=null){
						List<EopBasedataBudgetItem> items = item.getFdBudgetItems();
						EopBasedataBudgetItem it = null;
						String fdCode = cost.getFdType().getFdCode();
						if(items.size()==1){
							it = items.get(0);
						}
						for(EopBasedataBudgetItem i:items){
							if(i.getFdCode().indexOf(fdCode)>-1){
								it = i;
								break;
							}
						}
						if(it!=null){
							row.put("fdBudgetItemId", it.getFdId());
							row.put("fdBudgetItemName", it.getFdName());
							row.put("fdBudgetItemCode", it.getFdCode());
						}
					}
					datas.add(row);
				}
				getFsscCommonProappService().udpateProappLedger(datas);
			}
			//如果有预提模块，需要生成预提占用台账
			if(FsscCommonUtil.checkHasModule("/fssc/provision/")){
				//冲抵逻辑，1含税，2不含税
				String  fdProvisionRule=EopBasedataFsscUtil.getDetailPropertyValue(main.getFdCompany().getFdId(),"fdProvisionRule");
				JSONArray datas = new JSONArray();
				getFsscCommonProvisionService().deleteProvisionLedger(main.getFdId(), FsscExpenseMain.class.getName());
				List<FsscExpenseDetail> list = main.getFdDetailList();
				Map<String,Double> ledgerMap = new HashMap<String,Double>();
				for(FsscExpenseDetail detail:list) {
					if(detail.getFdApprovedApplyMoney()<0){
						continue;
					}
					JSONObject obj = new JSONObject();
					obj.put("fdCompanyId", main.getFdCompany().getFdId());
					if(detail.getFdExpenseItem()!=null) {
						obj.put("fdExpenseItemId", detail.getFdExpenseItem().getFdId());
					}
					if(detail.getFdCostCenter()!=null) {
						obj.put("fdCostCenterId", detail.getFdCostCenter().getFdId());
					}
					obj.put("fdProappId", main.getFdProappId());
					if(detail.getFdProject()!=null) {
						obj.put("fdProjectId", detail.getFdProject().getFdId());
					}
					if(detail.getFdInnerOrder()!=null) {
						obj.put("fdInnerOrderId", detail.getFdInnerOrder().getFdId());
					}
					if(detail.getFdWbs()!=null) {
						obj.put("fdWbsId", detail.getFdWbs().getFdId());
					}
					obj.put("fdProappId", main.getFdProappId());
					obj.put("fdDetailId",detail.getFdId());
					JSONObject rtn = getFsscCommonProvisionService().getProvisionLedgerData(obj);
					if("success".equals(rtn.optString("result"))) {
						JSONArray data = rtn.getJSONArray("data");
						detail.setFdProvisionInfo(data.toString());
						Double detailMoney = detail.getFdStandardMoney();
						if("2".equals(fdProvisionRule)&&detail.getFdNoTaxMoney()!=null) {//如果是不含税，则需要扣除税额
							detailMoney = detail.getFdNoTaxMoney();
						}
						Double useMoney = 0d;
						if(data.size()>0) {
							for(int i=0;i<data.size();i++) {
								JSONObject row = new JSONObject();
								row.put("fdDetailId", detail.getFdId());
								row.put("fdModelName", FsscExpenseMain.class.getName());
								row.put("fdModelId", main.getFdId());
								row.put("fdType", "2");
								row.putAll(data.getJSONObject(i));
								Double fdMoney = ledgerMap.containsKey(row.getString("fdLedgerId"))?ledgerMap.get(row.getString("fdLedgerId")):row.getDouble("fdMoney");
								if(FsscNumberUtil.isEqual(fdMoney, 0d)||FsscNumberUtil.isEqual(detailMoney, 0d)) {//如果当前可用为0，跳过
									continue;
								}
								if(fdMoney>detailMoney) {//如果当前预提可用金额大于报销金额，直接全额冲抵
									row.put("fdMoney", detailMoney);
									fdMoney = FsscNumberUtil.getSubtraction(fdMoney, detailMoney);
									ledgerMap.put(row.getString("fdLedgerId"), fdMoney);
									useMoney = FsscNumberUtil.getAddition(useMoney, detailMoney);
									detailMoney = 0d;
								}else if(FsscNumberUtil.isEqual(detailMoney, fdMoney)){//如果当前预提可用金额等于报销金额
									row.put("fdMoney", detailMoney);
									useMoney = FsscNumberUtil.getAddition(useMoney, fdMoney);
									ledgerMap.put(row.getString("fdLedgerId"), 0d);
									detailMoney = 0d;
								}else {
									useMoney = FsscNumberUtil.getAddition(useMoney, fdMoney);
									detailMoney = FsscNumberUtil.getSubtraction(detailMoney, fdMoney);
									ledgerMap.put(row.getString("fdLedgerId"), 0d);
									row.put("fdMoney", fdMoney);
								}
								datas.add(row);
							}
						}
						detail.setFdProvisionMoney(useMoney);
					}
				}
				if(datas.size()>0) {
					getFsscCommonProvisionService().addProvisionLedger(datas);
				}
				getBaseDao().update(main);
			}
			if(FsscCommonUtil.checkHasModule("/fssc/fee/")&&StringUtil.isNotNull(main.getFdFeeIds())){
				List<FsscExpenseDetail> list = main.getFdDetailList();
				getFsscCommonFeeService().deleteFeeLedgerByModel(main.getFdId(),FsscExpenseMain.class.getName());
				JSONArray params = new JSONArray();
				for(FsscExpenseDetail detail:list){
					if(StringUtil.isNull(detail.getFdFeeInfo())||detail.getFdApprovedApplyMoney()<0){
						continue;
					}
					Boolean hasBudget = "1".equals(detail.getFdBudgetStatus())||"2".equals(detail.getFdBudgetStatus());//是否有预算
					JSONArray data = JSONArray.fromObject(detail.getFdFeeInfo().replaceAll("'", "\""));
					Double fdBudgetMoney = detail.getFdBudgetMoney();//明细总额
					Double total = fdBudgetMoney;//需要占用预算的金额
					for(int i=0;i<data.size();i++){
						JSONObject obj = data.getJSONObject(i);
						Double fdOffsetMoney = Double.parseDouble(obj.optString("fdOffsetMoney","0"));//当前占用的金额
						//明细剩余金额为0，跳过
						if(FsscNumberUtil.isEqual(fdBudgetMoney, 0d)) {
							break;
						}
						//当前占用金额为0，且不是最后一条或者明细有预算,跳过
						if((hasBudget||i<data.size()-1)&&FsscNumberUtil.isEqual(fdOffsetMoney, 0d)) {
							continue;
						}
						//如果当前是最后一条事前，并且明细没有预算，直接把剩余明细金额全占事前,防止出现部分金额既没有占事前又没有占预算的情况
						if(!hasBudget&&i==data.size()-1) {
							fdOffsetMoney = fdBudgetMoney;
						}else {
							fdOffsetMoney = fdOffsetMoney>fdBudgetMoney?fdBudgetMoney:fdOffsetMoney;
						}
						JSONObject row = new JSONObject();
						row.putAll(obj);
						row.put("fdModelId", main.getFdId());
						row.put("fdModelName", FsscExpenseMain.class.getName());
						row.put("fdType", "2");
						row.put("fdDetailId", detail.getFdId());
						row.put("fdBudgetMoney", fdOffsetMoney);
						params.add(row);
						fdBudgetMoney = FsscNumberUtil.getSubtraction(fdBudgetMoney, fdOffsetMoney);
						if(obj.optBoolean("fdIsUseBudget",false)) {
							total = FsscNumberUtil.getSubtraction(total, fdOffsetMoney);
						}
					}
					total = total<0?0d:total;//如果是负的，置为0，以便后续占预算时判断
					budgetMap.put(detail.getFdId(), total);
				}
				if(params.size()>0){
					getFsscCommonFeeService().addFsscFeeLedger(params);
				}
			}
			if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
				List<FsscExpenseDetail> list = main.getFdDetailList();
				JSONArray params = new JSONArray();
				for(FsscExpenseDetail detail:list){
					if(budgetMap.containsKey(detail.getFdId())){
						Double money = budgetMap.get(detail.getFdId());
						if(FsscNumberUtil.isEqual(money, 0d)||detail.getFdApprovedApplyMoney()<0){
							continue;
						}
					}
					if(StringUtil.isNull(detail.getFdBudgetInfo())||detail.getFdApprovedApplyMoney()<0){
						continue;
					}
					JSONArray data = JSONArray.fromObject(detail.getFdBudgetInfo().replaceAll("'", "\""));
					for(int i=0;i<data.size();i++){
						EopBasedataProject project = main.getFdProject();
						if(main.getDocTemplate().getFdIsProjectShare()!=null&&main.getDocTemplate().getFdIsProjectShare()){
							project = detail.getFdProject();
						}
						JSONObject obj = data.getJSONObject(i);
						JSONObject row = new JSONObject();
						row.put("fdModelId", main.getFdId());
						row.put("fdModelName", FsscExpenseMain.class.getName());
						if(budgetMap.containsKey(detail.getFdId())){
							row.put("fdMoney", budgetMap.get(detail.getFdId()));
						}else{
							row.put("fdMoney", detail.getFdBudgetMoney());
						}
						row.put("fdBudgetId", obj.get("fdBudgetId"));
						row.put("fdDetailId", detail.getFdId());
						row.put("fdCompanyId", detail.getFdCompany().getFdId());
						row.put("fdCostCenterId", detail.getFdCostCenter().getFdId());
						row.put("fdExpenseItemId", detail.getFdExpenseItem()==null?"":detail.getFdExpenseItem().getFdId()); //由于结转情况下，需要重新匹配新的预算，所以需要传费用类型ID
						row.put("fdBudgetItemId", obj.get("fdBudgetItemId"));
						row.put("fdType", "2");
						row.put("fdProjectId", project==null?"":project.getFdId());
						row.put("fdInnerOrderId", detail.getFdInnerOrder()==null?"":detail.getFdInnerOrder().getFdId());
						row.put("fdWbsId", detail.getFdWbs()==null?"":detail.getFdWbs().getFdId());
						row.put("fdPersonId", detail.getFdRealUser().getFdId());
						if(detail.getFdDept()!=null){
							row.put("fdDeptId", detail.getFdDept().getFdId());
						}else{
							if(detail.getFdRealUser().getFdParent()!=null){
								row.put("fdDeptId", detail.getFdRealUser().getFdParent().getFdId());
							}
						}
						row.put("fdCurrency", detail.getFdCurrency().getFdId());
						params.add(row);
					}
				}
				if(params.size()>0){
					getFsscBudgetOperatService().updateFsscBudgetExecute(params);
				}
			}
			//没有预算模块，不进行操作
			if(FsscCommonUtil.checkHasModule("/fssc/loan/")){
				if(getFsscCommonLoanService()!=null&&main.getFdIsOffsetLoan()!=null&&main.getFdIsOffsetLoan()){
					List<FsscExpenseOffsetLoan> list = main.getFdOffsetList();
					JSONObject data = new JSONObject();
					data.put("fdModelId", main.getFdId());
					data.put("fdModelName", FsscExpenseMain.class.getName());
					JSONArray params = new JSONArray();
					for(FsscExpenseOffsetLoan detail:list){
						if(detail.getFdOffsetMoney()==null||FsscNumberUtil.isEqual(0d, Double.valueOf(detail.getFdOffsetMoney()))){
							continue;
						}
						JSONObject obj = new JSONObject();
						obj.put("fdPersonId", main.getFdClaimant().getFdId());
						obj.put("fdLoanId", detail.getFdLoanId());
						obj.put("fdModelId", main.getFdId());
						obj.put("fdType", "2");
						obj.put("fdModelName", FsscExpenseMain.class.getName());
						obj.put("fdModelNumber", main.getDocNumber());
						obj.put("fdModelSubject", main.getDocSubject());
						obj.put("fdMoney", detail.getFdOffsetMoney());
						obj.put("fdMultiplier", "-1");//标识报销冲抵
						params.add(obj);
					}
					data.put("jsonArray", params);
					getFsscCommonLoanService().deleteAdd(data);
				}
			}
		}
		//选择未报费用，需要更新关联，标识为已使用
		if(FsscCommonUtil.checkHasModule("/fssc/mobile/")){
			List<FsscExpenseDetail> list = main.getFdDetailList();
			Map<String,String> rel = new HashMap<String,String>();
			for(FsscExpenseDetail d:list){
				if(StringUtil.isNotNull(d.getFdNoteId())){
					rel.put(d.getFdNoteId(), d.getFdId());
				}
			}
			getFsscCommonMobileService().updateRelationNote(rel);
		}
	}

	public void deleteTempInvoiceDetail(FsscExpenseMain main) throws Exception{
		Map<String,Boolean> invoiceMap=new HashMap<>();
		List<FsscExpenseInvoiceDetail> invoiceList=main.getFdInvoiceList();
		for(FsscExpenseInvoiceDetail invoice:invoiceList){
			String type=StringUtil.isNotNull(invoice.getFdInvoiceType())?invoice.getFdInvoiceType():"";
			String number=StringUtil.isNotNull(invoice.getFdInvoiceNumber())?invoice.getFdInvoiceNumber():"";
			String code=StringUtil.isNotNull(invoice.getFdInvoiceCode())?invoice.getFdInvoiceCode():"";
			invoiceMap.put(type+number+code, Boolean.TRUE);
		}
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock("fsscExpenseTemp.fdMainId=:expenseMainId");
		hqlInfo.setParameter("expenseMainId", main.getFdId());
		List<FsscExpenseTemp> tempList=getFsscExpenseTempService().findList(hqlInfo);
		for(FsscExpenseTemp temp:tempList){
			List<FsscExpenseTempDetail> newDetailList=new ArrayList<>();
			List<FsscExpenseTempDetail> detailList=temp.getFdInvoiceListTemp();
			for(FsscExpenseTempDetail detail:detailList){
				String type=StringUtil.isNotNull(detail.getFdInvoiceType())?detail.getFdInvoiceType():"";
				String number=StringUtil.isNotNull(detail.getFdInvoiceNumber())?detail.getFdInvoiceNumber():"";
				String code=StringUtil.isNotNull(detail.getFdInvoiceCode())?detail.getFdInvoiceCode():"";
				if(invoiceMap.containsKey(type+number+code)){//发票明细存在，则保留
					newDetailList.add(detail);
				}
			}
			detailList.clear();
			detailList.addAll(newDetailList);
			temp.setFdInvoiceListTemp(detailList);
			getFsscExpenseTempService().update(temp);
		}
	}

	/**
	 * 初始化付款单
	 * @param payment
	 * @param fdId
	 * @throws Exception
	 */
	@Override
    public void initPaymentData(HttpServletRequest request, EopBasedataPayment payment, String fdId) throws Exception{
		FsscExpenseMain main = (FsscExpenseMain) findByPrimaryKey(fdId, null, true);
		payment.setFdModelNumber(main.getDocNumber());
		payment.setFdPaymentMoney(main.getFdTotalApprovedMoney());
		payment.setFdSubject(main.getDocSubject());
		List<FsscExpenseAccounts> accounts = main.getFdAccountsList();
		List<EopBasedataPaymentDetail> details = new ArrayList<EopBasedataPaymentDetail>();
		List<String> currencyIds = new ArrayList<String>();
		for(FsscExpenseDetail detail:main.getFdDetailList()){
			if(!currencyIds.contains(detail.getFdCurrency().getFdId())){
				currencyIds.add(detail.getFdCurrency().getFdId());
			}
		}
		for(FsscExpenseAccounts account:accounts){
			EopBasedataPaymentDetail detail = new EopBasedataPaymentDetail();
			detail.setFdCompany(main.getFdCompany());
			detail.setFdCurrency(account.getFdCurrency());
			detail.setFdExchangeRate(account.getFdExchangeRate());
			detail.setFdPayBank(account.getFdBank());
			detail.setFdPayeeAccount(account.getFdBankAccount());
			detail.setFdPayeeBankName(account.getFdBankName());
			detail.setFdPayeeName(account.getFdAccountName());
			detail.setFdPaymentMoney(account.getFdMoney());
			detail.setFdPayWay(account.getFdPayWay());
			if(!currencyIds.contains(account.getFdCurrency().getFdId())){
				currencyIds.add(account.getFdCurrency().getFdId());
			}
			details.add(detail);
		}
		payment.setFdDetail(details);
		request.setAttribute("currencyIds", StringUtil.join(currencyIds, ";"));
	}

	@Override
	public String checkMoney(String fdModelId, JSONObject info) throws Exception {
		FsscExpenseMain main = (FsscExpenseMain) findByPrimaryKey(fdModelId, null, true);
		JSONObject src = new JSONObject();
		for(FsscExpenseAccounts acc:main.getFdAccountsList()){
			if(src.containsKey(acc.getFdCurrency().getFdId())){
				JSONObject detail = src.getJSONObject(acc.getFdCurrency().getFdId());
				Double money = detail.getDouble("money");
				detail.put("money", FsscNumberUtil.getAddition(money, acc.getFdMoney()));
			}else{
				JSONObject detail = new JSONObject();
				detail.put("money", acc.getFdMoney());
				detail.put("currency", acc.getFdCurrency().getFdName());
				src.put(acc.getFdCurrency().getFdId(), detail);
			}
		}
		JSONObject rtn = new JSONObject();
		List<String> msg = new ArrayList<String>();
		for(Iterator<String> it = info.keys();it.hasNext();){
			String key = it.next();
			Double money = info.getDouble(key);
			if(src.containsKey(key)){
				JSONObject detail = src.getJSONObject(key);
				if(!FsscNumberUtil.isEqual(money, detail.getDouble("money"))){
					msg.add(detail.getString("currency"));
				}
			}
		}
		if(!ArrayUtil.isEmpty(msg)){
			rtn.put("result", "failure");
			rtn.put("message", ResourceUtil.getString("tips.paymnet.moneyInvalid","fssc-expense").replaceAll("\\{0\\}",StringUtil.join(msg, "/") ));
		}else{
			rtn.put("result", "success");
		}
		return rtn.toString();
	}

	@Override
    public void downloadBankFile(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String ids = request.getParameter("ids");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataPayment.fdModelId in(:ids)");
		hqlInfo.setParameter("ids",Arrays.asList(ids.split(";")));
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("fdModelId", ids);
		Map<String, Object> mainMap = getFsscCommonCashierPaymentService().getEopBasedataCashierPaymentList(map);

		String filename = ResourceUtil.getString("py.exportBankFileName","fssc-expense");
		filename = new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls";
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filename);
		Workbook workBook = new HSSFWorkbook();
		String sheetName = new String(filename.getBytes("ISO8859-1"), "GBK");
		Sheet sheet = workBook.createSheet(sheetName);
		String title[] = ResourceUtil.getString("py.exportBankFileTitle","fssc-expense").split(";");
		for (int i = 0; i <= title.length; i++) {
			sheet.setColumnWidth((short) i, (short) 4000);
		}
		CellStyle style = EopBasedataImportUtil.getTitleStyle(workBook);
		Row row = sheet.createRow(0);
		Cell cell = null;
		//生成标题行
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		//生成内容行
		int i=1;
		style = EopBasedataImportUtil.getNormalStyle(workBook);

		List<Map<String, Object>> mapList = null;
		if("success".equals(mainMap.get("result"))){
			mapList = (List<Map<String, Object>>)mainMap.get("list");
			for(Map<String, Object> main:mapList){
				row = sheet.createRow(i++);
				//单据编号
				cell = row.createCell(0);
				cell.setCellValue(main.get("docNumber")+"");
				cell.setCellStyle(style);
				//付款银行
				cell = row.createCell(1);
				cell.setCellValue(main.get("fdBasePayBank")+"");
				cell.setCellStyle(style);
				//付款账号
				cell = row.createCell(2);
				cell.setCellValue(main.get("fdBankAccount")+"");
				cell.setCellStyle(style);
				//收款人账号
				cell = row.createCell(3);
				cell.setCellValue(main.get("fdPayeeAccount")+"");
				cell.setCellStyle(style);
				//收款人账户名
				cell = row.createCell(4);
				cell.setCellValue(main.get("fdPayeeName")+"");
				cell.setCellStyle(style);
				//币种
				cell = row.createCell(5);
				cell.setCellValue(main.get("fdBaseCurrency")+"");
				cell.setCellStyle(style);
				//实际付款
				cell = row.createCell(6);
				cell.setCellValue(main.get("fdPaymentMoney")+"");
				cell.setCellStyle(style);

			}
		}
		workBook.write(os);
		os.flush();
		os.close();
	}

	@Override
	public JSONObject updatePyament(String ids,String type) throws Exception {
		JSONObject rtn = new JSONObject();
		rtn.put("result", "success");
		List<FsscExpenseMain> list = findByPrimaryKeys(ids.split(";"));
		//如果操作不是来自付款单列表，需要校验是否已经生成了付款单
		if(!"paymentList".equals(type)){
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("eopBasedataPayment.fdModelId in(:ids) and eopBasedataPayment.fdModelName=:fdModelName");
			hqlInfo.setParameter("ids",Arrays.asList(ids.split(";")));
			hqlInfo.setParameter("fdModelName",FsscExpenseMain.class.getName());
			hqlInfo.setSelectBlock("eopBasedataPayment.fdModelNumber");
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			List<String> rs = eopBasedataPaymentService.findList(hqlInfo);
			for(FsscExpenseMain main : list){
				//结果集不包含该报销单，说明未生成付款单
				if(!rs.contains(main.getDocNumber())){
					rtn.put("result", "faliure");
					rtn.put("message", ResourceUtil.getString("tips.cannotPayment","fssc-expense").replaceAll("\\{0\\}", main.getDocNumber()));
					return rtn;
				}
			}
		}
		for(FsscExpenseMain main : list){
			LbpmProcess lbpmProcess = (LbpmProcess) lbpmProcessService.findByPrimaryKey(main.getFdId(), null, true);
			FsscExpenseMainForm mainForm = (FsscExpenseMainForm) convertModelToForm(null, main, new RequestContext());
			LbpmProcessForm sysWfBusinessForm = (LbpmProcessForm) mainForm.getSysWfBusinessForm();
			LbpmNode node = lbpmProcess.getFdNodes().get(0);
			List<LbpmWorkitem> items = node.getFdWorkitems();
			SysOrgElement person = items.get(0).getFdExpecter();
			JSONObject param = new JSONObject();
			param.put("taskId", items.get(0).getFdId());
			param.put("processId", main.getFdId());
			param.put("activityType", items.get(0).getFdActivityType());
			param.put("operationType", "handler_pass");
			JSONObject audit = new JSONObject();
			audit.put("operationName", "pass");
			audit.put("auditNote", "pass");
			audit.put("notifyType", "todo");
			audit.put("notifyOnFinish", true);
			param.put("param", audit);
			sysWfBusinessForm.setFdParameterJson(param.toString());
			backgroundAuthService.switchUserById(person.getFdId(), new Runner() {
				@Override
				public Object run(Object parameter) throws Exception {
					lbpmProcessService.updateByPanel((LbpmProcessForm) parameter);
					return null;
				}
			}, sysWfBusinessForm);
			main.setFdPaymentStatus(EopBasedataConstant.FSSC_BASE_PAYMENT_STATUS_PAYED);
			getBaseDao().update(main);
		}
		String hql = "update com.landray.kmss.eop.basedata.model.EopBasedataPayment set fdStatus=:fdStatus where fdModelId in(:ids) and fdModelName=:fdModelName";
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdModelName", FsscExpenseMain.class.getName());
		query.setParameterList("ids", Arrays.asList(ids.split(";")));
		query.setParameter("fdStatus", EopBasedataConstant.FSSC_BASE_PAYMENT_STATUS_PAYED);
		query.executeUpdate();
		return rtn;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map> rtn = new ArrayList<Map>();
		String flag = requestInfo.getParameter("flag");
		String fdModelId = requestInfo.getParameter("fdMainId");
		String fdAttId = requestInfo.getParameter("fdAttId");
		String fileRelativePath = requestInfo.getParameter("fileRelativePath");
		File file =null;
		String tempPath="";  //压缩后的路径
		Boolean compressFlag=false;  //标识是否被压缩
		String fileName=requestInfo.getParameter("fileName");
		Boolean isImage=CompressImage.isImage(fileName);  //附件类型,ID
		if("checkInvoice".equals(flag)||"deleteInvoice".equals(flag)){
			SysAttFile attFile;
			if ("aliyun".equals(ResourceUtil.getKmssConfigString("sys.att.location"))) { //文件存储路径为阿里云存储对象
				if(StringUtil.isNull(fileRelativePath)) {
					attFile = getSysAttUploadService().getFileById(fdAttId);
					fileRelativePath = attFile.getFdFilePath();
				}else {
					attFile = getSysAttUploadService().getFileByPath(fileRelativePath);
					fdAttId = attFile.getFdId();
				}
				String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
				file = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation()).readFileToTemp(fileRelativePath, pathPrefix);
			} else {
				attFile = (SysAttFile) findByPrimaryKey(fdAttId, SysAttFile.class, true);
				if(attFile!=null){
					String path=""; //附件路径
					path=ResourceUtil.getKmssConfigString("kmss.resource.path")+File.separator+attFile.getFdFilePath();
					if(attFile.getFdFileSize()>8192000) {//由于接口图片大小限制为8M，所以图片超过8M则进行压缩
						tempPath=path;
						path=CompressImage.CompressImageImg(path);
						compressFlag=true;
					}
					file=new File(path);
				}
			}
		}
		//上传单个pdf文件时校验发票是否已存在
		if("checkInvoice".equals(flag)){
			Boolean exists=false;   //默认发票是不存在的
			JSONArray dataArray = new JSONArray();
			Map<String,String> info=new HashMap<String,String>();
			try {
				InputStream in =new DecryptionInputStream(new FileInputStream(file),2);//server类型的，不管是否加过密，以兼容方式解密再读取
				info = FsscCommonParsePdfUtil.pdfParseByIn(in);
				if(StringUtil.isNotNull(info.get("code"))||StringUtil.isNotNull(info.get("number"))) {
					info.put("fdModelId", fdModelId);
					info.put("fdModelName", FsscExpenseMain.class.getName());
					//由于pdfbox.jar解析出来和ocr票种不同，在此转转换，统一为OCR票种值
					if(info.containsKey("type")&&StringUtil.isNotNull(info.get("type"))){
						String fdTicketType=info.get("type");
						if("01".equals(fdTicketType)){
							fdTicketType="10100";
						}else if("04".equals(fdTicketType)){
							fdTicketType="10101";
						}else if("10".equals(fdTicketType)){
							fdTicketType="10102";
						}else if("11".equals(fdTicketType)){
							fdTicketType="10103";
						}else if("03".equals(fdTicketType)){
							fdTicketType="10104";
						}else if("15".equals(fdTicketType)){
							fdTicketType="10105";
						}
						info.put("type", fdTicketType);
					}
					dataArray.add(JSONObject.fromObject(info));
				}
			} catch (Exception e) {

			}
			if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
				try {
					//如果有发票台账，校验发票是否已经存在
					if(!info.isEmpty()){
						exists = getFsscCommonLedgerService().hasInvoice(info);
					}
					if(exists){
						rtn = new ArrayList<Map>();
						Map node = new HashMap();
						node.put("flag", "failure");
						rtn.add(node);
						return rtn;
					}else{
						//附件上传不保存fdModelId，fdModelName，防止出现上传后页面刷新，同一个页面附件名字修改重新上传不做校验
						if(!info.isEmpty()&&(StringUtil.isNotNull(info.get("code"))||StringUtil.isNotNull(info.get("number")))){//ocr图片时，info无信息
							info.put("fdState", "0");
							info.put("fdDeductible", "0");
							info.put("fdCheckStatus", "0");
							info.put("fdUseStatus", "0");
							getFsscCommonLedgerService().addOrUpdateInvoiceByPdf(info);
						}
						Map node = new HashMap();
						node.put("flag", "success");
						node.put("data", dataArray);
						rtn.add(node);
					}
				} catch (Exception e) {
				}
			}else{
				//无发票台账直接返回发票信息
				Map node = new HashMap();
				node.put("flag", "success");
				node.put("info", dataArray);
				rtn.add(node);
			}
			//如果有OCR识别，则调用接口识别接口补充信息
			if (FsscCommonUtil.checkHasModule("/fssc/ocr/")||FsscCommonUtil.checkHasModule("/fssc/iqubic/")) {
				dataArray = new JSONArray();
				String fdCompanyId = requestInfo.getParameter("fdCompanyId");
				String fdCategoryId= requestInfo.getParameter("fdCategoryId");
				JSONObject ocrInfo = null;
				try {
					JSONObject params=new JSONObject();
					params.put("fileName",fileName);
					if ("1".equals(EopBasedataFsscUtil.getSwitchValue("fdOcrCompany"))){
						ocrInfo = getFsscCommonOcrService().saveInvoiceByFile(file, fdCompanyId,fdCategoryId);  //保存到OCR台账
						params.put("ocrType","ocr");
					}else if ("2".equals(EopBasedataFsscUtil.getSwitchValue("fdOcrCompany"))) {
						ocrInfo = getFsscCommonIqubicService().saveInvoiceByFile(file, fdCompanyId,fdCategoryId);
						params.put("ocrType","iqubic");
					}
					Map node = ArrayUtil.isEmpty(rtn)?new HashMap():rtn.get(0);
					//识别成功
					if(ocrInfo!=null&&"success".equals(ocrInfo.get("result"))){
						JSONArray locationArr=new JSONArray();
						JSONObject locationObj=new JSONObject();//接收发票id和位置信息
						JSONArray data = ocrInfo.getJSONArray("data");
						for(int i=0,size=data.size();i<size;i++){
							JSONObject invoiceData=data.getJSONObject(i);
							if(!"20105".equals(invoiceData.optString("type"))) {//滴滴行程单不做处理
								invoiceData.put("fdModelId", fdModelId);
								if ("2".equals(EopBasedataFsscUtil.getSwitchValue("fdOcrCompany"))) {
									invoiceData.put("CheckCode",invoiceData.optString("check_code"));
								}
								if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
									exists = getFsscCommonLedgerService().hasInvoice(invoiceData);
								}
								if(exists){
									rtn = new ArrayList<Map>();
									node = new HashMap();
									node.put("flag", "failure");
									rtn.add(node);
									return rtn;
								}else{
									//附件上传不保存fdModelId，fdModelName，防止出现上传后页面刷新，同一个页面附件名字修改重新上传不做校验
									if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
										locationObj=saveInvoiceLedger(invoiceData,fdModelId,FsscExpenseMain.class.getName());  //保存到发票台账
										locationObj.put("ledger_module","ledger");
									}
									if(FsscCommonUtil.checkHasModule("/fssc/invoice/")){
										JSONObject rtnJSON=getFsscInvoiceCommonService().saveInvoice(invoiceData, fdModelId, FsscExpenseMain.class.getName(), "");	//保存到发票池
										locationObj.put("fdInfoId",rtnJSON.optString("fdId"));
										locationObj.put("fpLocation",rtnJSON.optJSONArray("fpLocation"));
										locationObj.put("invoice_module","invoice");
									}
									if(FsscCommonUtil.checkHasModule("/fssc/ledger/")||FsscCommonUtil.checkHasModule("/fssc/invoice/")){
										locationArr.add(locationObj);
									}
									String fdIsAutoCheck=EopBasedataFsscUtil.getSwitchValue("fdIsAutoCheck");  //是否开启自动验证
									if("true".equals(fdIsAutoCheck)&&(FsscCommonUtil.checkHasModule("/fssc/baiwang/")||FsscCommonUtil.checkHasModule("/fssc/nuo/"))){
										String fdInvVerCpy = EopBasedataFsscUtil.getSwitchValue("fdInvVerCpy");
										Map<String,Object> rtnMap=null;
										if("2".equals(fdInvVerCpy)){
											rtnMap = getFsscCommonNuoService().checkInvoiceVerification(invoiceData);
										}else {
											rtnMap = getFsscCommonBaiwangService().checkInvoiceVerification(invoiceData);
										}
										if(!rtnMap.isEmpty()) {//为空说明改票不能验真
											List rtnList=new ArrayList();
											if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
												rtnList=getFsscCommonLedgerService().getInvoiceInfoByCode(invoiceData.containsKey("code")?invoiceData.getString("code"):"",invoiceData.containsKey("number")?invoiceData.getString("number"):"");
											}
											String fdInvoiceId="";
											if(!ArrayUtil.isEmpty(rtnList)){
												Map<String,Object> map=(Map<String, Object>) rtnList.get(0);
												fdInvoiceId=map.containsKey("fdId")?map.get("fdId").toString():"";
											}
											String result=rtnMap.containsKey("type")?rtnMap.get("type").toString():"1";
											if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
												JSONObject valueJson=new JSONObject();
												if("0".equals(result)){
													valueJson=new JSONObject();
													if(StringUtil.isNotNull(fdInvoiceId)){
														String[] fdInvoiceIds=new String[1];
														fdInvoiceIds[0]=fdInvoiceId;
														valueJson.put("fdMsg", rtnMap.get("message"));
														invoiceData.put("fdCheckStatus", "0");   //回传验真状态到页面
														invoiceData.put("fdState", "0");   //回传发票状态到页面
														getFsscCommonLedgerService().updatePropertys(fdInvoiceIds, valueJson);
													}
												}else if("1".equals(result)){
													valueJson=new JSONObject();
													String[] fdInvoiceIds=new String[1];
													fdInvoiceIds[0]=fdInvoiceId;
													valueJson.put("fdCheckStatus", "1");
													String fdState="0";   //默认是正常
													if(rtnMap.containsKey("zfbz")&&"Y".equals(rtnMap.get("zfbz"))) {
														fdState="1";  //作废
													}
													valueJson.put("fdState", fdState);
													invoiceData.put("fdCheckStatus", "1");   //回传验真状态到页面
													invoiceData.put("fdState", fdState);   //回传发票状态到页面
													getFsscCommonLedgerService().updatePropertys(fdInvoiceIds, valueJson);
												}
											}
										}
									}
									dataArray.add(invoiceData);
								}
							}
						}
						node.put("data", dataArray);
						node.put("flag", "success");
						rtn.add(node);
						if(isImage&&!dataArray.isEmpty()&&dataArray.size()>1){//图片格式,且是多票才需要切割
							params.put("locationArr",locationArr);
							params.put("fdFileId",fdAttId);
							params.put("key","attInvoice"); //台账和发票管理附件key统一为attInvoice
							CompressImage.cuttingImgae(file,params);
						}else{//非图片或者单张图片直接保存
							JSONObject attJSON=new JSONObject();
							attJSON.put("key","attInvoice");  //发票id
							attJSON.put("filename",fileName);  //发票id
							String fdInvoiceId=locationObj.optString("fdInvoiceId");  //发票台账ID
							if("ledger".equals(locationObj.optString("ledger_module"))&&StringUtil.isNotNull(fdInvoiceId)&&CompressImage.attIsNotExist(fdInvoiceId,fdAttId)){
								attJSON.put("fdModelId",fdInvoiceId);  //发票id
								attJSON.put("fdModelName","com.landray.kmss.fssc.ledger.model.FsscLedgerInvoice");
								CompressImage.saveAtt(attJSON,file);
							}
							fdInvoiceId=locationObj.optString("fdInfoId");  //发票池ID
							if("invoice".equals(locationObj.optString("invoice_module"))&&StringUtil.isNotNull(fdInvoiceId)&&CompressImage.attIsNotExist(fdInvoiceId,fdAttId)){
								attJSON.put("fdModelId",fdInvoiceId);  //发票id
								attJSON.put("fdModelName","com.landray.kmss.fssc.invoice.model.FsscInvoiceInfo");
								CompressImage.saveAtt(attJSON,file);
							}
						}
					}else if(ocrInfo!=null&&"failure".equals(ocrInfo.optString("result"))){
						node.put("flag", "ocrError");
						node.put("message", ocrInfo.optString("message"));
						rtn.add(node);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					if(compressFlag&&StringUtil.isNotNull(tempPath)) {
						//删除压缩中间产生的文件
						file=new File(tempPath+"_new");
						if (file.exists()) {
							file.delete();
						}
						file=new File(tempPath+"_temp");
						if (file.exists()) {
							file.delete();
						}
					}
				}
			}else if(!dataArray.isEmpty()) {
				String fdIsAutoCheck=EopBasedataFsscUtil.getSwitchValue("fdIsAutoCheck");  //是否开启自动验证
				if("true".equals(fdIsAutoCheck)&&(FsscCommonUtil.checkHasModule("/fssc/baiwang/")||FsscCommonUtil.checkHasModule("/fssc/nuo/"))){
					for(int n=0,size=dataArray.size();n<size;n++) {
						JSONObject invoiceData=dataArray.getJSONObject(n);
						invoiceData.put("check_code", invoiceData.optString("CheckCode", ""));  //校验码，转为验真所取参数
						invoiceData.put("pretax_amount", invoiceData.optDouble("notax", 0.0));  //不含税金额，转为验真所取参数，有税额情况可能会导致验真失败
						String fdInvVerCpy = EopBasedataFsscUtil.getSwitchValue("fdInvVerCpy");
						Map<String,Object> rtnMap=null;
						if("2".equals(fdInvVerCpy)){
							rtnMap = getFsscCommonNuoService().checkInvoiceVerification(invoiceData);
						}else {
							rtnMap = getFsscCommonBaiwangService().checkInvoiceVerification(invoiceData);
						}
						if(!rtnMap.isEmpty()) {//为空说明改票不能验真
							List rtnList=new ArrayList();
							if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
								rtnList=getFsscCommonLedgerService().getInvoiceInfoByCode(invoiceData.containsKey("code")?invoiceData.getString("code"):"",invoiceData.containsKey("number")?invoiceData.getString("number"):"");
							}
							String fdInvoiceId="";
							if(!ArrayUtil.isEmpty(rtnList)){
								Map<String,Object> map=(Map<String, Object>) rtnList.get(0);
								fdInvoiceId=map.containsKey("fdId")?map.get("fdId").toString():"";
							}
							String result=rtnMap.containsKey("type")?rtnMap.get("type").toString():"1";
							if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
								JSONObject valueJson=new JSONObject();
								if("0".equals(result)){
									invoiceData.put("fdCheckStatus", "0");   //回传验真状态到页面
									invoiceData.put("fdState", "0");   //回传发票状态到页面
									valueJson=new JSONObject();
									if(StringUtil.isNotNull(fdInvoiceId)){
										String[] fdInvoiceIds=new String[1];
										fdInvoiceIds[0]=fdInvoiceId;
										valueJson.put("fdMsg", rtnMap.get("message"));
										getFsscCommonLedgerService().updatePropertys(fdInvoiceIds, valueJson);
									}
								}else if("1".equals(result)){
									valueJson=new JSONObject();
									String[] fdInvoiceIds=new String[1];
									fdInvoiceIds[0]=fdInvoiceId;
									valueJson.put("fdCheckStatus", "1");
									String fdState="0";   //默认是正常
									if(rtnMap.containsKey("zfbz")&&"Y".equals(rtnMap.get("zfbz"))) {
										fdState="1";  //作废
									}
									valueJson.put("fdState", fdState);
									invoiceData.put("fdCheckStatus", "1");   //回传验真状态到页面
									invoiceData.put("fdState", fdState);   //回传发票状态到页面
									getFsscCommonLedgerService().updatePropertys(fdInvoiceIds, valueJson);
								}
							}
						}
					}
				}
			}
			return rtn;
		}else if("deleteInvoice".equals(flag)){
			String id = "";
			if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
				try {
					Map<String,String> info = FsscCommonParsePdfUtil.pdfParseByIn(new FileInputStream(file));
					info.put("fdModelId",fdModelId);
					info.put("fdModelName",FsscExpenseMain.class.getName());
					id = getFsscCommonLedgerService().deleteInvoice(info);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map node = new HashMap();
			node.put("flag", "success");
			node.put("id", id);
			rtn.add(node);
			return rtn;
		}else if("submitInvoice".equals(flag)){
			String ids="";
			String fdAttIds = requestInfo.getParameter("fdAttIds");
			if(FsscCommonUtil.checkHasModule("/fssc/ledger/")&&StringUtil.isNotNull(fdAttIds)){
				try {
					for(String attId:fdAttIds.split(";")){
						SysAttFile att = (SysAttFile) findByPrimaryKey(attId,SysAttFile.class,true);
						File attfile = new File(ResourceUtil.getKmssConfigString("kmss.resource.path")+File.separator+att.getFdFilePath());
						Map<String,String> info = FsscCommonParsePdfUtil.pdfParseByIn(new FileInputStream(attfile));
						String id = getFsscCommonLedgerService().findInvoice(info);
						if(StringUtil.isNotNull(id)){
							ids+=id+";";
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map node = new HashMap();
			node.put("flag", "success");
			if(StringUtil.isNotNull(ids)){
				ids=ids.substring(0, ids.length()-1);
			}
			node.put("id", ids);
			rtn.add(node);
		}

		return rtn;
	}

	public  JSONObject saveInvoiceLedger(JSONObject invoiceInfo, String fdModelId, String fdModelName) throws Exception{
		JSONObject poolValue=new JSONObject();
		JSONObject param=new JSONObject();
		poolValue=new JSONObject();
		poolValue.put("modelId", fdModelId);
		poolValue.put("modelName", fdModelName);
		poolValue.put("kprq", invoiceInfo.containsKey("date")?invoiceInfo.getString("date"):null);  //开票日期
		poolValue.put("bz", invoiceInfo.containsKey("remark")?invoiceInfo.getString("remark"):null);  //备注
		poolValue.put("cjh", invoiceInfo.containsKey("car_code")?invoiceInfo.getString("car_code"):null);  //车架号/车辆识别代码
		poolValue.put("djzh", invoiceInfo.containsKey("registration_number")?invoiceInfo.getString("registration_number"):null);  //登记证号
		poolValue.put("escsc", invoiceInfo.containsKey("company_name")?invoiceInfo.getString("company_name"):null);  //二手车市场
		poolValue.put("escnsrsbh", invoiceInfo.containsKey("company_tax_id")?invoiceInfo.getString("company_tax_id"):null);  //二手车市场纳税人识别号
		poolValue.put("fdjhm", invoiceInfo.containsKey("car_engine_code")?invoiceInfo.getString("car_engine_code"):null);  //发动机号码
		poolValue.put("gfdw", invoiceInfo.containsKey("buyer")?invoiceInfo.getString("buyer"):null);  //买方单位/个人
		poolValue.put("gfdwdm", invoiceInfo.containsKey("buyer_id")?invoiceInfo.getString("buyer_id"):null);  //买方单位代码/身份证号码
		poolValue.put("hgzh", invoiceInfo.containsKey("certificate_number")?invoiceInfo.getString("certificate_number"):null);  //合格证号
		poolValue.put("fpdm", invoiceInfo.containsKey("code")?invoiceInfo.getString("code"):null);  //发票代码
		poolValue.put("fphm", invoiceInfo.containsKey("number")?invoiceInfo.getString("number"):null);  //发票号码
		poolValue.put("fplx", invoiceInfo.containsKey("type")?invoiceInfo.getString("type"):null);  //发票类型
		poolValue.put("jqbm", invoiceInfo.containsKey("machine_code")?invoiceInfo.getString("machine_code"):null);  //机器编码
		poolValue.put("jshj", invoiceInfo.containsKey("total")?invoiceInfo.getString("total"):null);  //价税合计
		poolValue.put("jym", invoiceInfo.containsKey("check_code")?invoiceInfo.getString("check_code"):null);  //校验码
		poolValue.put("mfdw", invoiceInfo.containsKey("seller")?invoiceInfo.getString("seller"):null);  //卖方单位/个人
		poolValue.put("mfdwdm", invoiceInfo.containsKey("seller_id")?invoiceInfo.getString("seller_id"):null);  //卖方单位代码/身份证号码
		poolValue.put("gfmc", invoiceInfo.containsKey("buyer")?invoiceInfo.getString("buyer"):null);  //购方名称
		poolValue.put("gfsh", invoiceInfo.containsKey("buyer_tax_id")?invoiceInfo.getString("buyer_tax_id"):null);  //购方税号
		poolValue.put("xfmc", invoiceInfo.containsKey("seller")?invoiceInfo.getString("seller"):null);  //销方名称
		poolValue.put("xfsh", invoiceInfo.containsKey("seller_tax_id")?invoiceInfo.getString("seller_tax_id"):null);  //购方税号
		poolValue.put("sfz", invoiceInfo.containsKey("user_id")?invoiceInfo.getString("user_id"):null);  //身份证号码/组织机构代码
		poolValue.put("sl", invoiceInfo.containsKey("tax_rate")?invoiceInfo.getString("tax_rate"):null);  //税率
		poolValue.put("je", invoiceInfo.containsKey("pretax_amount")?invoiceInfo.getString("pretax_amount"):null);  //发票金额(不含税金额)
		poolValue.put("se", invoiceInfo.containsKey("tax")?invoiceInfo.getString("tax"):null);  //税额
		poolValue.put("zgswjg", invoiceInfo.containsKey("tax_authorities")?invoiceInfo.getString("tax_authorities"):null);  //主管税务机关
		poolValue.put("zgswjgdm", invoiceInfo.containsKey("tax_authorities_code")?invoiceInfo.getString("tax_authorities_code"):null);  //主管税务机关代码
		poolValue.put("state", "0");  //发票状态
		//--begin-------------------增加台账信息by bo---------------------------//
		poolValue.put("xfdzdh", invoiceInfo.containsKey("fpSellerAddress")?invoiceInfo.getString("fpSellerAddress"):null);  //销方地址电话
		poolValue.put("xfyhzh", invoiceInfo.containsKey("fpSellerBankAccount")?invoiceInfo.getString("fpSellerBankAccount"):null);  //销方银行账号
		poolValue.put("gfdzdh", invoiceInfo.containsKey("fpPayerAddress")?invoiceInfo.getString("fpPayerAddress"):null);  //购方地址电话
		poolValue.put("gfyhzh", invoiceInfo.containsKey("fpPayerBankAccount")?invoiceInfo.getString("fpPayerBankAccount"):null);  //购方银行账号
		poolValue.put("sjly", invoiceInfo.containsKey("sjly")?invoiceInfo.getString("sjly"):null);  //数据来源
		poolValue.put("block_chain", invoiceInfo.containsKey("block_chain")?invoiceInfo.getString("block_chain"):null);  //是否区块链发票
		poolValue.put("transit_mark", invoiceInfo.containsKey("transit_mark")?invoiceInfo.getString("transit_mark"):null);  //是否收费公路电子普通发票
		poolValue.put("electronic_mark", invoiceInfo.containsKey("electronic_mark")?invoiceInfo.getString("electronic_mark"):null);  //是否电子专票
		poolValue.put("fpLocation", invoiceInfo.optString("fpLocation"));  //发票所在位置
		if("10503".equals(invoiceInfo.optString("type"))){//火车票。保存站点信息
			poolValue.put("fdStartStation",invoiceInfo.optString("station_geton")); //出发站
			poolValue.put("fdArriveStation",invoiceInfo.optString("station_getoff")); //终点站
			String fdTravelDate=StringUtil.linkString(invoiceInfo.optString("date","")," ",invoiceInfo.optString("time",""));
			if(StringUtil.isNotNull(fdTravelDate)){
				poolValue.put("fdTravelDate",fdTravelDate); //乘车日期
			}
			poolValue.put("fdTrainNumber",invoiceInfo.optString("train_number")); //车次
			poolValue.put("fdSeatType",invoiceInfo.optString("seat")); //座次
		}
		if (invoiceInfo.containsKey("sph")) {
			JSONArray sph = invoiceInfo.getJSONArray("sph");
			poolValue.put("sph", sph);
		}
		//--end----------------------------------------------//

		JSONArray json = new JSONArray();
		if (invoiceInfo.containsKey("items")) {
			JSONArray jsonArray = JSONArray.fromObject(invoiceInfo.get("items"));
			for (Object object : jsonArray) {
				JSONObject jsonObject = JSONObject.fromObject(object);
				JSONObject newObject = new JSONObject();
				// {"name":"技术服务费","unit":"元","quantity":"1","price":"39657.65","total":"39657.65","tax_rate":"3%","tax":"1189.73"}
				newObject.put("spmc", jsonObject.containsKey("name") ? jsonObject.get("name") : null);
				newObject.put("jldw", jsonObject.containsKey("unit") ? jsonObject.get("unit") : null);
				newObject.put("sl", jsonObject.containsKey("quantity") ? jsonObject.get("quantity") : null);
				newObject.put("je",FsscNumberUtil.doubleToUp(jsonObject.getDouble("total")));//不含税金额
				newObject.put("dj", jsonObject.containsKey("price") ? jsonObject.get("price") : null);
				newObject.put("slv", jsonObject.containsKey("tax_rate") ? jsonObject.get("tax_rate") : null);//税率
				newObject.put("se", jsonObject.containsKey("tax") ? jsonObject.get("tax") : null); //税额
				json.add(newObject);
			}
			poolValue.put("sph", json);// 发票内容明细
			if (!invoiceInfo.containsKey("sph")) {  //将票小秘明细信息转为sph
				invoiceInfo.put("sph", json);
			}
		}
		param=new JSONObject();
		param.put("fpxx", poolValue);
		String fdInvoiceId=getFsscCommonLedgerService().saveInvoice(String.valueOf(param));
		JSONObject rtnJson=new JSONObject();
		rtnJson.put("fpLocation",invoiceInfo.optJSONArray("fpLocation"));
		rtnJson.put("fpRotationAngle",invoiceInfo.optInt("fpRotationAngle"));
		rtnJson.put("fdInvoiceId",fdInvoiceId);
		return rtnJson;
	}

	@Override
	public JSONObject checkFeeRelation(HttpServletRequest request) throws Exception {
		JSONObject rtn=new JSONObject();
		rtn.put("result", Boolean.TRUE);  //默认允许勾选关闭
		String fdFeeIds = request.getParameter("fdFeeIds");
		String fdId=request.getParameter("fdId");
		List<Long> resultList = new ArrayList<>();
		if(StringUtil.isNotNull(fdFeeIds)){
			HQLInfo hqlInfo=new HQLInfo();
			hqlInfo.setSelectBlock("count(fsscExpenseMain.fdId)");
			StringBuilder whereBlock=new StringBuilder();
			whereBlock.append(" (fsscExpenseMain.docStatus=:refuse or fsscExpenseMain.docStatus=:examine)");
			whereBlock.append("  and fsscExpenseMain.fdIsCloseFee=:fdIsCloseFee ");
			whereBlock.append("  and fsscExpenseMain.fdId<>:fdId ");
			hqlInfo.setParameter("refuse", SysDocConstant.DOC_STATUS_REFUSE);
			hqlInfo.setParameter("examine", SysDocConstant.DOC_STATUS_EXAMINE);
			hqlInfo.setParameter("fdIsCloseFee", Boolean.TRUE);
			hqlInfo.setParameter("fdId", fdId);
			List<String> feeIds=ArrayUtil.convertArrayToList(fdFeeIds.split(";"));
			String likeBlock="";
			int i=0;
			for(String feeId:feeIds){
				likeBlock=StringUtil.linkString(likeBlock, " or ", "fsscExpenseMain.fdFeeIds like :param"+i);
				hqlInfo.setParameter("param"+i, "%"+feeId+"%");
				i++;
			}
			if(StringUtil.isNotNull(likeBlock)){
				whereBlock.append(" and (").append(likeBlock).append(")");
			}
			hqlInfo.setWhereBlock(whereBlock.toString());
			List<Long> expenResult=this.findValue(hqlInfo);
			if(!expenResult.isEmpty()&&expenResult.get(0)>0) {
				resultList.addAll(expenResult);
			}

			if(FsscCommonUtil.checkHasModule("/fssc/payment/")) {
				String hql = "select count(t.fdId) from FsscPaymentMain t where (t.docStatus=:refuse or t.docStatus=:examine) and t.fdIsCloseFee=:fdIsCloseFee and t.fdFeeIds in (:param)";
				Query query = this.getBaseDao().getHibernateSession().createQuery(hql);
				query.setParameter("refuse", SysDocConstant.DOC_STATUS_REFUSE);
				query.setParameter("examine", SysDocConstant.DOC_STATUS_EXAMINE);
				query.setParameter("fdIsCloseFee", Boolean.TRUE);
				String feeIdsByPayment = fdFeeIds.replace(";",",");
				query.setParameter("param", feeIdsByPayment);
				List<Long> paymentResult=query.list();
				if(!paymentResult.isEmpty()&&paymentResult.get(0)>0) {
					resultList.addAll(paymentResult);
				}
			}

			if(!ArrayUtil.isEmpty(resultList)&&resultList.get(0)>0){
				rtn.put("result", Boolean.FALSE);  //存在待审、驳回的，不允许勾选关闭
			}
		}
		return rtn;
	}

	@Override
	public JSONObject checkInvoiceDetail(HttpServletRequest request) throws Exception {
		JSONObject rtn=new JSONObject();
		String data = request.getParameter("data");
		if(StringUtil.isNotNull(data)&&FsscCommonUtil.checkHasModule("/fssc/ledger/")){
			String msg=getFsscCommonLedgerService().checkInvoiceDetail(JSONObject.fromObject(data));
			rtn.put("msg", msg);
		}
		return rtn;
	}

	@Override
	public JSONObject checkInvoice(HttpServletRequest request) throws Exception {
		JSONObject rtn=new JSONObject();
		try {
			rtn.put("result", "success");
			JSONObject invoiceInfo=new JSONObject();
			String fdInvoiceId="";
			if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
				String fdInvoiceCode=request.getParameter("fdInvoiceCode"), fdInvoiceNumber=request.getParameter("fdInvoiceNumber");
				List rtnList=getFsscCommonLedgerService().getInvoiceInfoByCode(fdInvoiceCode,fdInvoiceNumber);
				if(!ArrayUtil.isEmpty(rtnList)){
					Map<String,Object> map=(Map<String, Object>) rtnList.get(0);
					invoiceInfo.put("type", map.containsKey("type")?map.get("type"):"");
					invoiceInfo.put("date", map.containsKey("fdInvoiceDate")?map.get("fdInvoiceDate"):"");
					invoiceInfo.put("number", map.containsKey("fdInvoiceNumber")?map.get("fdInvoiceNumber"):"");
					invoiceInfo.put("code", map.containsKey("fdInvoiceCode")?map.get("fdInvoiceCode"):"");
					invoiceInfo.put("check_code", map.containsKey("fdCheckCode")?map.get("fdCheckCode"):"");
					invoiceInfo.put("total", map.containsKey("fdTotalAmount")?map.get("fdTotalAmount"):"");
					fdInvoiceId=map.containsKey("fdId")?map.get("fdId").toString():"";
				}
			}
			if((FsscCommonUtil.checkHasModule("/fssc/baiwang/")||FsscCommonUtil.checkHasModule("/fssc/nuo/"))){
				String fdInvVerCpy = EopBasedataFsscUtil.getSwitchValue("fdInvVerCpy");
				Map<String,Object> rtnMap=null;
				if("2".equals(fdInvVerCpy)){
					rtnMap = getFsscCommonNuoService().checkInvoiceVerification(invoiceInfo);
				}else {
					rtnMap = getFsscCommonBaiwangService().checkInvoiceVerification(invoiceInfo);
				}
				if(!rtnMap.isEmpty()) {//为空说明改票不能验真
					String result=rtnMap.containsKey("type")?rtnMap.get("type").toString():"1";
					JSONObject valueJson=new JSONObject();
					if("0".equals(result)){
						rtn.put("result", "error");
						rtn.put("error", rtnMap.get("message"));
						valueJson=new JSONObject();
						if(StringUtil.isNotNull(fdInvoiceId)){
							String[] fdInvoiceIds=new String[1];
							fdInvoiceIds[0]=fdInvoiceId;
							valueJson.put("fdMsg", rtnMap.get("message"));
							getFsscCommonLedgerService().updatePropertys(fdInvoiceIds, valueJson);
						}
					}else if("1".equals(result)){
						valueJson=new JSONObject();
						String[] fdInvoiceIds=new String[1];
						fdInvoiceIds[0]=fdInvoiceId;
						valueJson.put("fdCheckStatus", "1");
						getFsscCommonLedgerService().updatePropertys(fdInvoiceIds, valueJson);
						String fdState="0";   //默认是正常
						if(rtnMap.containsKey("zfbz")&&"Y".equals(rtnMap.get("zfbz"))) {
							fdState="1";  //作废
						}
						this.getBaseDao().getHibernateSession().createQuery("update FsscExpenseInvoiceDetail set fdCheckStatus=:fdCheckStatus,fdState=:fdState where fdId=:fdDetailId")
								.setParameter("fdCheckStatus", "1").setParameter("fdState", fdState).setParameter("fdDetailId", request.getParameter("fdDetailId")).executeUpdate(); //设置发票信息为已验真
					}
				}else if(StringUtil.isNotNull(invoiceInfo.optString("check_code"))&&invoiceInfo.optString("check_code").length()<6){
					rtn.put("result", "error");
					rtn.put("error", ResourceUtil.getString("message.invoice.cannot.check.tips", "fssc-baiwang"));
				}
			}
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("error", e.getMessage());
		}
		return rtn;
	}

	/**
	 * porlet获取报销列表
	 */
	@Override
	public Page listPortlet(HttpServletRequest request) throws Exception {
		Page page = Page.getEmptyPage();// 简单列表使用
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setGetCount(false);
		String type=request.getParameter("type");
		if("ower".equals(type)){
			String where="";
			where=StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "fsscExpenseMain.docCreator.fdId=:creatorId");
			hqlInfo.setWhereBlock(where);
			hqlInfo.setParameter("creatorId", UserUtil.getUser().getFdId());
		}
		hqlInfo.setOrderBy("fsscExpenseMain.fdId desc");
		page = findPage(hqlInfo);
		UserOperHelper.logFindAll(page.getList(), getModelName());
		return page;
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		FsscExpenseMain main = (FsscExpenseMain) modelObj;
		super.delete(modelObj);
		//选择未报费用，需要清空关联，标识为可用
		if(FsscCommonUtil.checkHasModule("/fssc/mobile/")){
			List<FsscExpenseDetail> list = main.getFdDetailList();
			Map<String,String> rel = new HashMap<String,String>();
			for(FsscExpenseDetail d:list){
				if(StringUtil.isNotNull(d.getFdNoteId())){
					rel.put(d.getFdNoteId(), null);
				}
			}
			getFsscCommonMobileService().updateRelationNote(rel);
		}
	}
	@Override
	public void updateLederInvice(String fdId) throws Exception {
		if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
			//先把全部发票置为未使用
			this.getBaseDao().getHibernateSession().createQuery("update FsscLedgerInvoice set fdUseStatus=:noUse where fdModelId=:fdModelId")
					.setParameter("noUse", "0").setParameter("fdModelId", fdId).executeUpdate();
		}
	}

	@Override
	public void addArchAutoFileModel(HttpServletRequest request, String fdId) throws Exception {
		if ("true".equals(SysArchivesConfig.newInstance().getSysArchEnabled())&& SysArchivesUtil.isStartFile("fssc/expense")) {
			String sign = fdId.split(",")[1];
			FsscExpenseMain expenseMain = (FsscExpenseMain) findByPrimaryKey(fdId.split(",")[0]);
			FsscExpenseCategory template = expenseMain.getDocTemplate();
			ISysArchivesFileTemplateService sysArchivesFileTemplateService = (ISysArchivesFileTemplateService) SpringBeanUtil.getBean("sysArchivesFileTemplateService");
			SysArchivesFileTemplate fileTemp = sysArchivesFileTemplateService.getFileTemplate(template, null);
			if (fileTemp != null) {
				SysArchivesParamModel paramModel = new SysArchivesParamModel();
				paramModel.setAuto("1");
				paramModel.setFileName(expenseMain.getDocSubject() + ".html");
				// 归档页面URL(若为多表单，暂时归档默认表单)
				int saveApproval = fileTemp.getFdSaveApproval() != null
						&& fileTemp.getFdSaveApproval() ? 1 : 0;
				//流程自动归档
				String fdModelId = expenseMain.getFdId();
				long expires = System.currentTimeMillis() + (3 * 60 * 1000);//下载链接3分钟有效
				String url = "/fssc/expense/fssc_expense_main/fsscExpenseMainArchives.do?method=printFileDocArchives&fdId="
						+ fdModelId + "&s_xform=default&saveApproval=" + saveApproval + "&Signature=" + sign + "&Expires=" + expires;
				paramModel.setUrl(url);


				//增加出纳付款单归档页面
				List<Map<String,String>>  extendUrlList=null;
				boolean hasCarshier=getFsscCommonCashierPaymentService().checkHasCashier(fdModelId);
				if(hasCarshier) {
					extendUrlList = new ArrayList<Map<String, String>>();
					Map<String, String> extendUrl = new HashMap<String, String>();
					String carshierUrl = "/fssc/expense/fssc_expense_main/fsscExpenseMainArchives.do?method=printFileDocArchives&fdId="
							+ fdModelId + "&pageType=cashier&Signature=" + sign + "&Expires=" + expires;
					extendUrl.put("fileName", expenseMain.getDocSubject() + "-"+ResourceUtil.getString("table.fsscCashierPaymentDetail", "fssc-cashier")+".html");
					extendUrl.put("url", carshierUrl);
					extendUrlList.add(extendUrl);
				}
				//增加凭证的归档页面
				boolean hasVoucher=getFsscCommonVoucherService().checkHasVoucher(fdModelId);
				if(hasVoucher) {
					if(extendUrlList==null) {
						extendUrlList = new ArrayList<Map<String, String>>();
					}
					Map<String, String> extendUrl = new HashMap<String, String>();
					String voucherUrl = "/fssc/expense/fssc_expense_main/fsscExpenseMainArchives.do?method=printFileDocArchives&fdId="
							+ fdModelId + "&pageType=voucher&Signature=" + sign + "&Expires=" + expires;
					extendUrl.put("fileName", expenseMain.getDocSubject() + "-"+ResourceUtil.getString("table.fsscVoucherDetail", "fssc-voucher")+".html");
					extendUrl.put("url", voucherUrl);
					extendUrlList.add(extendUrl);
				}
				paramModel.setExtendUrlList(extendUrlList);

				//添加额外附件
				List<SysAttMain> extendAttList =null;
				//获取发票信息
				List<FsscExpenseDetail>  fdDetailList=expenseMain.getFdDetailList();
				if(fdDetailList!=null&&fdDetailList.size()>0){
					extendAttList=new ArrayList<SysAttMain>();
					for(FsscExpenseDetail detail: fdDetailList){
						String fdExpenseTempId=detail.getFdExpenseTempId();
						if(StringUtil.isNotNull(fdExpenseTempId)){
							List<SysAttMain> attList = getSysAttMainService().findAttListByModel("com.landray.kmss.fssc.expense.model.FsscExpenseTemp",fdExpenseTempId);
							extendAttList.addAll(attList);
						}
					}
				}
				paramModel.setExtendAttList(extendAttList);


				sysArchivesFileTemplateService.addArchAutoFileModel(request, expenseMain, paramModel, fileTemp, sign);
			} else {
				return;
			}
		}
	}

	@Override
	public void addArchFileModel(HttpServletRequest request, String fdId) throws Exception {
		if ("true".equals(SysArchivesConfig.newInstance().getSysArchEnabled())&&SysArchivesUtil.isStartFile("fssc/expense")) {
			FsscExpenseMain expenseMain = (FsscExpenseMain)findByPrimaryKey(fdId.split(",")[0]);
			FsscExpenseCategory template = expenseMain.getDocTemplate();
			ISysArchivesFileTemplateService sysArchivesFileTemplateService= (ISysArchivesFileTemplateService)SpringBeanUtil.getBean("sysArchivesFileTemplateService");
			SysArchivesFileTemplate fileTemp = (SysArchivesFileTemplate)request.getAttribute("flieTemplate");
			if(fileTemp==null){
				fileTemp=sysArchivesFileTemplateService.getFileTemplate(template,null);
			}
			if(fileTemp!=null){
				SysArchivesParamModel paramModel = new SysArchivesParamModel();
				paramModel.setAuto("0");
				paramModel.setFileName(expenseMain.getDocSubject()+".html");
				// 归档页面URL(若为多表单，暂时归档默认表单)
				int saveApproval = fileTemp.getFdSaveApproval() != null
						&& fileTemp.getFdSaveApproval() ? 1 : 0;
				//流程自动归档
				String fdModelId = expenseMain.getFdId();
				String url = "/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=printFileDoc&fdId="
						+ expenseMain.getFdId() + "&s_xform=default&saveApproval="
						+ saveApproval;
				paramModel.setUrl(url);

				List<Map<String,String>>  extendUrlList=null;
				boolean hasCarshier=getFsscCommonCashierPaymentService().checkHasCashier(fdId);
				if(hasCarshier) {
					extendUrlList = new ArrayList<Map<String, String>>();
					Map<String, String> extendUrl = new HashMap<String, String>();
					String carshierUrl = "/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=printFileDoc&fdId="
							+ fdModelId + "&pageType=cashier";
					extendUrl.put("fileName", expenseMain.getDocSubject()+"-"+ ResourceUtil.getString("table.fsscCashierPaymentDetail", "fssc-cashier")+".html");
					extendUrl.put("url", carshierUrl);
					extendUrlList.add(extendUrl);
				}
				//增加凭证的归档页面
				boolean hasVoucher=getFsscCommonVoucherService().checkHasVoucher(fdId);
				if(hasVoucher) {
					Map<String, String> extendUrl = new HashMap<String, String>();
					String voucherUrl = "/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=printFileDoc&fdId="
							+ fdModelId + "&pageType=voucher";
					extendUrl.put("fileName",expenseMain.getDocSubject()+"-"+ ResourceUtil.getString("table.fsscVoucherDetail", "fssc-voucher")+".html");
					extendUrl.put("url", voucherUrl);
					extendUrlList.add(extendUrl);
				}
				paramModel.setExtendUrlList(extendUrlList);

				//添加额外附件
				List<SysAttMain> extendAttList =null;
				//获取发票信息
				List<FsscExpenseDetail>  fdDetailList=expenseMain.getFdDetailList();
				if(fdDetailList!=null&&fdDetailList.size()>0){
					extendAttList=new ArrayList<SysAttMain>();
					for(FsscExpenseDetail detail: fdDetailList){
						String fdExpenseTempId=detail.getFdExpenseTempId();
						if(StringUtil.isNotNull(fdExpenseTempId)){
							List<SysAttMain> attList = getSysAttMainService().findAttListByModel("com.landray.kmss.fssc.expense.model.FsscExpenseTemp",fdExpenseTempId);
							extendAttList.addAll(attList);
						}
					}
				}
				paramModel.setExtendAttList(extendAttList);

				sysArchivesFileTemplateService.addArchFileModel(request,expenseMain,paramModel,fileTemp);
			}else{
				return;
			}
		}
	}
}
