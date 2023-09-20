package com.landray.kmss.fssc.expense.forms;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.circulation.forms.CirculationForm;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.metadata.forms.ExtendDataFormInfo;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.recycle.forms.ISysRecycleModelForm;
import com.landray.kmss.sys.relation.forms.SysRelationMainForm;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;

/**
  * 报销主表
  */
public class FsscExpenseMainForm extends ExtendAuthForm implements ISysLbpmMainForm, IAttachmentForm, IExtendDataForm, ISysCirculationForm, ISysRelationMainForm , ISysRecycleModelForm{

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private String fdProappId;
    
    private String fdProappName;
    
    private String fdAttNumber;
    
    private String fdLedgerInvoiceId;
    
    private String fdFeeIds;
    
    private String fdFeeNames;
    
    protected String fdIsCloseFee;
    
    private String fdIsAmortize;
    
    private String fdAmortizeBegin;
    
    private String fdAmortizeEnd;
    
    private String fdAmortizeMonth;
    
    private String fdAmortizeMoney;
    
    private String docNumber;
    
    private String fdPaymentStatus;
    
    private String fdVoucherStatus;
    
    private String docCreateTime;

    private String docSubject;

    private String fdContent;

    private String fdTotalStandaryMoney;
    
    private String fdTotalApprovedMoney;
    
    private String fdIsOffsetLoan;

    private String fdStandardMessage;

    private String docXform;

    private String docUseXform;

    private String docCreatorId;

    private String docCreatorName;

    private String docTemplateId;

    private String docTemplateName;

    private String fdCompanyId;

    private String fdCompanyName;

    private String fdCostCenterId;

    private String fdCostCenterName;

    private String fdClaimantId;

    private String fdClaimantName;

    private String fdClaimantDeptId;

    private String fdClaimantDeptName;
    
    private String fdExpenseDeptId;

    private String fdExpenseDeptName;

    private String fdProjectId;

    private String fdProjectName;
    
    private String fdProjectAccountingId;

    private String fdProjectAccountingName;

    private String fdBookkeepingStatus;

    private String fdBookkeepingMessage;
    
    private String fdDeduFlag;

    private AutoArrayList fdDetailList_Form = new AutoArrayList(FsscExpenseDetailForm.class);

    private String fdDetailList_Flag;

    private AutoArrayList fdTravelList_Form = new AutoArrayList(FsscExpenseTravelDetailForm.class);

    private String fdTravelList_Flag;

    private AutoArrayList fdAccountsList_Form = new AutoArrayList(FsscExpenseAccountsForm.class);

    private String fdAccountsList_Flag;

    private AutoArrayList fdOffsetList_Form = new AutoArrayList(FsscExpenseOffsetLoanForm.class);

    private String fdOffsetList_Flag;

    private AutoArrayList fdInvoiceList_Form = new AutoArrayList(FsscExpenseInvoiceDetailForm.class);
    
    private String fdAmortizeList_Flag;
    
    private AutoArrayList fdAmortizeList_Form = new AutoArrayList(FsscExpenseAmortizeForm.class);

    private String fdInvoiceList_Flag;

    private LbpmProcessForm sysWfBusinessForm = new LbpmProcessForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    private ExtendDataFormInfo extendDataFormInfo = new ExtendDataFormInfo();

    public CirculationForm circulationForm = new CirculationForm();
    
    private String fdIsExportBank;
    
    private SysRelationMainForm sysRelationMainForm = new SysRelationMainForm();
    
    private String docPublishTime;
    
    private AutoArrayList fdDidiDetail_Form = new AutoArrayList(FsscExpenseDidiDetailForm.class);

    private String fdDidiDetail_Flag;

    private AutoArrayList fdTranDataList_Form = new AutoArrayList(FsscExpenseTranDataForm.class);

    private String fdTranDataList_Flag;

    private JSONArray presList;

    private String fdBillStatus;    //实体单据状态

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdProappId = null;
    	fdProappName = null;
	    fdAttNumber = null;
	    fdLedgerInvoiceId = null;
	    fdFeeIds = null;
	    fdFeeNames = null;
	    fdIsAmortize = null;
	    fdIsCloseFee=null;
	    fdAmortizeBegin = null;
	    fdAmortizeEnd = null;
	    fdAmortizeMoney = null;
	    fdAmortizeMonth = null;
	    fdIsExportBank = null;
        docNumber = null;
        fdPaymentStatus = null;
        fdVoucherStatus = null;
        docCreateTime = null;
        docSubject = null;
        fdContent = null;
        fdTotalStandaryMoney = null;
        fdTotalApprovedMoney = null;
        fdIsOffsetLoan = null;
        fdStandardMessage = null;
        docXform = null;
        docUseXform = null;
        docCreatorId = null;
        docCreatorName = null;
        docTemplateId = null;
        docTemplateName = null;
        fdCompanyId = null;
        fdCompanyName = null;
        fdCostCenterId = null;
        fdCostCenterName = null;
        fdClaimantId = null;
        fdClaimantName = null;
        fdClaimantDeptId = null;
        fdClaimantDeptName = null;
        fdExpenseDeptId = null;
        fdExpenseDeptName = null;
        fdProjectId = null;
        fdProjectName = null;
        fdProjectAccountingId = null;
        fdProjectAccountingName = null;
        fdBookkeepingStatus = null;
        fdBookkeepingMessage = null;
        fdDeduFlag=null;
        fdBillStatus = null;
        fdDetailList_Form = new AutoArrayList(FsscExpenseDetailForm.class);
        fdDetailList_Flag = null;
        fdTravelList_Form = new AutoArrayList(FsscExpenseTravelDetailForm.class);
        fdTravelList_Flag = null;
        fdAccountsList_Form = new AutoArrayList(FsscExpenseAccountsForm.class);
        fdAccountsList_Flag = null;
        fdOffsetList_Form = new AutoArrayList(FsscExpenseOffsetLoanForm.class);
        fdOffsetList_Flag = null;
        fdInvoiceList_Form = new AutoArrayList(FsscExpenseInvoiceDetailForm.class);
        fdAmortizeList_Flag = null;
        fdAmortizeList_Form = new AutoArrayList(FsscExpenseAmortizeForm.class);
        fdInvoiceList_Flag = null;
        sysWfBusinessForm = new LbpmProcessForm();
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        extendDataFormInfo = new ExtendDataFormInfo();
        docPublishTime = null;
        fdDidiDetail_Form = new AutoArrayList(FsscExpenseDidiDetailForm.class);
        fdDidiDetail_Flag = null;
        fdTranDataList_Form = new AutoArrayList(FsscExpenseTranDataForm.class);
        fdTranDataList_Flag = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseMain> getModelClass() {
        return FsscExpenseMain.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docStatus");
            toModelPropertyMap.addNoConvertProperty("docNumber");
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("docTemplateId", new FormConvertor_IDToModel("docTemplate", FsscExpenseCategory.class));
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("fdCostCenterId", new FormConvertor_IDToModel("fdCostCenter", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdClaimantId", new FormConvertor_IDToModel("fdClaimant", SysOrgPerson.class));
            toModelPropertyMap.put("fdExpenseDeptId", new FormConvertor_IDToModel("fdExpenseDept", SysOrgElement.class));

            toModelPropertyMap.put("fdProjectId", new FormConvertor_IDToModel("fdProject", EopBasedataProject.class));
            toModelPropertyMap.put("fdProjectAccountingId", new FormConvertor_IDToModel("fdProjectAccounting", EopBasedataProject.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
            toModelPropertyMap.put("authAttCopyIds", new FormConvertor_IDsToModelList("authAttCopys", SysOrgElement.class));
            toModelPropertyMap.put("authAttDownloadIds", new FormConvertor_IDsToModelList("authAttDownloads", SysOrgElement.class));
            toModelPropertyMap.put("authAttPrintIds", new FormConvertor_IDsToModelList("authAttPrints", SysOrgElement.class));
            toModelPropertyMap.put("fdDetailList_Form", new FormConvertor_FormListToModelList("fdDetailList", "docMain", "fdDetailList_Flag"));
            toModelPropertyMap.put("fdTravelList_Form", new FormConvertor_FormListToModelList("fdTravelList", "docMain", "fdTravelList_Flag"));
            toModelPropertyMap.put("fdAccountsList_Form", new FormConvertor_FormListToModelList("fdAccountsList", "docMain", "fdAccountsList_Flag"));
            toModelPropertyMap.put("fdOffsetList_Form", new FormConvertor_FormListToModelList("fdOffsetList", "docMain", "fdOffsetList_Flag"));
            toModelPropertyMap.put("fdInvoiceList_Form", new FormConvertor_FormListToModelList("fdInvoiceList", "docMain", "fdInvoiceList_Flag"));
            toModelPropertyMap.put("fdAmortizeList_Form", new FormConvertor_FormListToModelList("fdAmortizeList", "docMain", "fdAmortizeList_Flag"));
            toModelPropertyMap.put("docPublishTime", new FormConvertor_Common("docPublishTime").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdDidiDetail_Form", new FormConvertor_FormListToModelList("fdDidiDetail", "docMain", "fdDidiDetail_Flag"));
            toModelPropertyMap.put("fdTranDataList_Form", new FormConvertor_FormListToModelList("fdTranDataList", "docMain", "fdTranDataList_Flag"));
        }
        return toModelPropertyMap;
    }

    public AutoArrayList getFdDidiDetail_Form() {
		return fdDidiDetail_Form;
	}

	public void setFdDidiDetail_Form(AutoArrayList fdDidiDetail_Form) {
		this.fdDidiDetail_Form = fdDidiDetail_Form;
	}

	public String getFdDidiDetail_Flag() {
		return fdDidiDetail_Flag;
	}

	public void setFdDidiDetail_Flag(String fdDidiDetail_Flag) {
		this.fdDidiDetail_Flag = fdDidiDetail_Flag;
	}

	public String getFdProappId() {
		return fdProappId;
	}

	public void setFdProappId(String fdProappId) {
		this.fdProappId = fdProappId;
	}

	public String getFdProappName() {
		return fdProappName;
	}

	public void setFdProappName(String fdProappName) {
		this.fdProappName = fdProappName;
	}

	/**
     * 编号
     */
    public String getDocNumber() {
        return this.docNumber;
    }

    /**
     * 编号
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    /**
     * 创建时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 标题
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 标题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * 差旅说明
     */
    public String getFdContent() {
        return this.fdContent;
    }

    /**
     * 差旅说明
     */
    public void setFdContent(String fdContent) {
        this.fdContent = fdContent;
    }

    /**
     * 合计申请金额(本币)
     */
    public String getFdTotalStandaryMoney() {
    	if(StringUtil.isNull(this.fdTotalStandaryMoney)){
    		return this.fdTotalStandaryMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdTotalStandaryMoney));
    }

    /**
     * 合计申请金额(本币)
     */
    public void setFdTotalStandaryMoney(String fdTotalStandaryMoney) {
        this.fdTotalStandaryMoney = fdTotalStandaryMoney;
    }
    

    /**
     * 核准金额合计(本币)
     */
    public String getFdTotalApprovedMoney() {
    	if(StringUtil.isNull(this.fdTotalApprovedMoney)){
    		return this.fdTotalApprovedMoney;
    	}
    	return new DecimalFormat("0.0#").format(Double.valueOf(this.fdTotalApprovedMoney));
    }

    /**
     * 核准金额合计(本币)
     */
    public void setFdTotalApprovedMoney(String fdTotalApprovedMoney) {
        this.fdTotalApprovedMoney =fdTotalApprovedMoney;
    }
    
    /**
     * 是否冲抵借款
     */
    public String getFdIsOffsetLoan() {
        return this.fdIsOffsetLoan;
    }

    /**
     * 是否冲抵借款
     */
    public void setFdIsOffsetLoan(String fdIsOffsetLoan) {
        this.fdIsOffsetLoan = fdIsOffsetLoan;
    }


    /**
     * 标准信息
     */
    public String getFdStandardMessage() {
        return this.fdStandardMessage;
    }

    /**
     * 标准信息
     */
    public void setFdStandardMessage(String fdStandardMessage) {
        this.fdStandardMessage = fdStandardMessage;
    }

    /**
     * 扩展属性
     */
    public String getDocXform() {
        return this.docXform;
    }

    /**
     * 扩展属性
     */
    public void setDocXform(String docXform) {
        this.docXform = docXform;
    }

    /**
     * 是否使用表单
     */
    public String getDocUseXform() {
        return this.docUseXform;
    }

    /**
     * 是否使用表单
     */
    public void setDocUseXform(String docUseXform) {
        this.docUseXform = docUseXform;
    }

    /**
     * 记账状态
     */
    public String getFdBookkeepingStatus() {
        return this.fdBookkeepingStatus;
    }

    /**
     * 记账状态
     */
    public void setFdBookkeepingStatus(String fdBookkeepingStatus) {
        this.fdBookkeepingStatus = fdBookkeepingStatus;
    }

    /**
     * 记账失败原因
     */
    public String getFdBookkeepingMessage() {
        return this.fdBookkeepingMessage;
    }

    /**
     * 记账失败原因
     */
    public void setFdBookkeepingMessage(String fdBookkeepingMessage) {
        this.fdBookkeepingMessage = fdBookkeepingMessage;
    }
    
    /**
     * 预算扣减逻辑  1 :含税  2：不含税
     */
    public String getFdDeduFlag() {
		return fdDeduFlag;
	}
    
    /**
     * 预算扣减逻辑  1 :含税  2：不含税
     */
	public void setFdDeduFlag(String fdDeduFlag) {
		this.fdDeduFlag = fdDeduFlag;
	}

	/**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 分类模板
     */
    public String getDocTemplateId() {
        return this.docTemplateId;
    }

    /**
     * 分类模板
     */
    public void setDocTemplateId(String docTemplateId) {
        this.docTemplateId = docTemplateId;
    }

    /**
     * 分类模板
     */
    public String getDocTemplateName() {
        return this.docTemplateName;
    }

    /**
     * 分类模板
     */
    public void setDocTemplateName(String docTemplateName) {
        this.docTemplateName = docTemplateName;
    }

    /**
     * 费用承担方
     */
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 费用承担方
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
    }

    /**
     * 费用承担方
     */
    public String getFdCompanyName() {
        return this.fdCompanyName;
    }

    /**
     * 费用承担方
     */
    public void setFdCompanyName(String fdCompanyName) {
        this.fdCompanyName = fdCompanyName;
    }

    /**
     * 费用承担部门
     */
    public String getFdCostCenterId() {
        return this.fdCostCenterId;
    }

    /**
     * 费用承担部门
     */
    public void setFdCostCenterId(String fdCostCenterId) {
        this.fdCostCenterId = fdCostCenterId;
    }

    /**
     * 费用承担部门
     */
    public String getFdCostCenterName() {
        return this.fdCostCenterName;
    }

    /**
     * 费用承担部门
     */
    public void setFdCostCenterName(String fdCostCenterName) {
        this.fdCostCenterName = fdCostCenterName;
    }

    /**
     * 报销人
     */
    public String getFdClaimantId() {
        return this.fdClaimantId;
    }

    /**
     * 报销人
     */
    public void setFdClaimantId(String fdClaimantId) {
        this.fdClaimantId = fdClaimantId;
    }

    /**
     * 报销人
     */
    public String getFdClaimantName() {
        return this.fdClaimantName;
    }

    /**
     * 报销人
     */
    public void setFdClaimantName(String fdClaimantName) {
        this.fdClaimantName = fdClaimantName;
    }

    /**
     * 报销人部门
     */
    public String getFdClaimantDeptId() {
        return this.fdClaimantDeptId;
    }

    /**
     * 报销人部门
     */
    public void setFdClaimantDeptId(String fdClaimantDeptId) {
        this.fdClaimantDeptId = fdClaimantDeptId;
    }

    /**
     * 报销人部门
     */
    public String getFdClaimantDeptName() {
        return this.fdClaimantDeptName;
    }

    /**
     * 报销人部门
     */
    public void setFdClaimantDeptName(String fdClaimantDeptName) {
        this.fdClaimantDeptName = fdClaimantDeptName;
    }

    public String getFdExpenseDeptId() {
		return fdExpenseDeptId;
	}

	public void setFdExpenseDeptId(String fdExpenseDeptId) {
		this.fdExpenseDeptId = fdExpenseDeptId;
	}

	public String getFdExpenseDeptName() {
		return fdExpenseDeptName;
	}

	public void setFdExpenseDeptName(String fdExpenseDeptName) {
		this.fdExpenseDeptName = fdExpenseDeptName;
	}

	/**
     * 归属项目
     */
    public String getFdProjectId() {
        return this.fdProjectId;
    }

    /**
     * 归属项目
     */
    public void setFdProjectId(String fdProjectId) {
        this.fdProjectId = fdProjectId;
    }

    /**
     * 归属项目
     */
    public String getFdProjectName() {
        return this.fdProjectName;
    }

    /**
     * 归属项目
     */
    public void setFdProjectName(String fdProjectName) {
        this.fdProjectName = fdProjectName;
    }
    
    /**
     * 核算项目
     */
    public String getFdProjectAccountingId() {
        return this.fdProjectAccountingId;
    }

    /**
     * 核算项目
     */
    public void setFdProjectAccountingId(String fdProjectAccountingId) {
        this.fdProjectAccountingId = fdProjectAccountingId;
    }

    /**
     * 核算项目
     */
    public String getFdProjectAccountingName() {
        return this.fdProjectAccountingName;
    }

    /**
     * 核算项目
     */
    public void setFdProjectAccountingName(String fdProjectAccountingName) {
        this.fdProjectAccountingName = fdProjectAccountingName;
    }

    /**
     * 费用明细
     */
    public AutoArrayList getFdDetailList_Form() {
        return this.fdDetailList_Form;
    }

    /**
     * 费用明细
     */
    public void setFdDetailList_Form(AutoArrayList fdDetailList_Form) {
        this.fdDetailList_Form = fdDetailList_Form;
    }

    /**
     * 费用明细
     */
    public String getFdDetailList_Flag() {
        return this.fdDetailList_Flag;
    }

    /**
     * 费用明细
     */
    public void setFdDetailList_Flag(String fdDetailList_Flag) {
        this.fdDetailList_Flag = fdDetailList_Flag;
    }

    /**
     * 行程明细
     */
    public AutoArrayList getFdTravelList_Form() {
        return this.fdTravelList_Form;
    }

    /**
     * 行程明细
     */
    public void setFdTravelList_Form(AutoArrayList fdTravelList_Form) {
        this.fdTravelList_Form = fdTravelList_Form;
    }

    /**
     * 行程明细
     */
    public String getFdTravelList_Flag() {
        return this.fdTravelList_Flag;
    }

    /**
     * 行程明细
     */
    public void setFdTravelList_Flag(String fdTravelList_Flag) {
        this.fdTravelList_Flag = fdTravelList_Flag;
    }

    /**
     * 收款账户明细
     */
    public AutoArrayList getFdAccountsList_Form() {
        return this.fdAccountsList_Form;
    }

    /**
     * 收款账户明细
     */
    public void setFdAccountsList_Form(AutoArrayList fdAccountsList_Form) {
        this.fdAccountsList_Form = fdAccountsList_Form;
    }

    /**
     * 收款账户明细
     */
    public String getFdAccountsList_Flag() {
        return this.fdAccountsList_Flag;
    }

    /**
     * 收款账户明细
     */
    public void setFdAccountsList_Flag(String fdAccountsList_Flag) {
        this.fdAccountsList_Flag = fdAccountsList_Flag;
    }

    /**
     * 借款信息
     */
    public AutoArrayList getFdOffsetList_Form() {
        return this.fdOffsetList_Form;
    }

    /**
     * 借款信息
     */
    public void setFdOffsetList_Form(AutoArrayList fdOffsetList_Form) {
        this.fdOffsetList_Form = fdOffsetList_Form;
    }

    /**
     * 借款信息
     */
    public String getFdOffsetList_Flag() {
        return this.fdOffsetList_Flag;
    }

    /**
     * 借款信息
     */
    public void setFdOffsetList_Flag(String fdOffsetList_Flag) {
        this.fdOffsetList_Flag = fdOffsetList_Flag;
    }

    /**
     * 发票明细
     */
    public AutoArrayList getFdInvoiceList_Form() {
        return this.fdInvoiceList_Form;
    }

    /**
     * 发票明细
     */
    public void setFdInvoiceList_Form(AutoArrayList fdInvoiceList_Form) {
        this.fdInvoiceList_Form = fdInvoiceList_Form;
    }

    /**
     * 发票明细
     */
    public String getFdInvoiceList_Flag() {
        return this.fdInvoiceList_Flag;
    }

    /**
     * 发票明细
     */
    public void setFdInvoiceList_Flag(String fdInvoiceList_Flag) {
        this.fdInvoiceList_Flag = fdInvoiceList_Flag;
    }

    @Override
    public String getAuthReaderNoteFlag() {
        return "2";
    }

    @Override
    public LbpmProcessForm getSysWfBusinessForm() {
        return sysWfBusinessForm;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    @Override
    public ExtendDataFormInfo getExtendDataFormInfo() {
        return extendDataFormInfo;
    }

    @Override
    public CirculationForm getCirculationForm() {
        return circulationForm;
    }

	public String getFdPaymentStatus() {
		return fdPaymentStatus;
	}

	public void setFdPaymentStatus(String fdPaymentStatus) {
		this.fdPaymentStatus = fdPaymentStatus;
	}

	public String getFdVoucherStatus() {
		return fdVoucherStatus;
	}

	public void setFdVoucherStatus(String fdVoucherStatus) {
		this.fdVoucherStatus = fdVoucherStatus;
	}

	public String getFdIsExportBank() {
		return fdIsExportBank;
	}

	public void setFdIsExportBank(String fdIsExportBank) {
		this.fdIsExportBank = fdIsExportBank;
	}

	public AutoArrayList getFdAmortizeList_Form() {
		return fdAmortizeList_Form;
	}

	public void setFdAmortizeList_Form(AutoArrayList fdAmortizeList_Form) {
		this.fdAmortizeList_Form = fdAmortizeList_Form;
	}

	public String getFdIsAmortize() {
		return fdIsAmortize;
	}

	public void setFdIsAmortize(String fdIsAmortize) {
		this.fdIsAmortize = fdIsAmortize;
	}

	public String getFdAmortizeBegin() {
		return fdAmortizeBegin;
	}

	public void setFdAmortizeBegin(String fdAmortizeBegin) {
		this.fdAmortizeBegin = fdAmortizeBegin;
	}

	public String getFdAmortizeEnd() {
		return fdAmortizeEnd;
	}

	public void setFdAmortizeEnd(String fdAmortizeEnd) {
		this.fdAmortizeEnd = fdAmortizeEnd;
	}

	public String getFdAmortizeMonth() {
		return fdAmortizeMonth;
	}

	public void setFdAmortizeMonth(String fdAmortizeMonth) {
		this.fdAmortizeMonth = fdAmortizeMonth;
	}

	public String getFdAmortizeMoney() {
		return fdAmortizeMoney;
	}

	public void setFdAmortizeMoney(String fdAmortizeMoney) {
		this.fdAmortizeMoney = fdAmortizeMoney;
	}

	public String getFdAmortizeList_Flag() {
		return fdAmortizeList_Flag;
	}

	public void setFdAmortizeList_Flag(String fdAmortizeList_Flag) {
		this.fdAmortizeList_Flag = fdAmortizeList_Flag;
	}

	public String getFdFeeIds() {
		return fdFeeIds;
	}

	public void setFdFeeIds(String fdFeeIds) {
		this.fdFeeIds = fdFeeIds;
	}

	public String getFdFeeNames() {
		return fdFeeNames;
	}

	public void setFdFeeNames(String fdFeeNames) {
		this.fdFeeNames = fdFeeNames;
	}
	
	/**
	 * 是否关闭事前
	 */
	
	public String getFdIsCloseFee() {
		return fdIsCloseFee;
	}

	public void setFdIsCloseFee(String fdIsCloseFee) {
		this.fdIsCloseFee = fdIsCloseFee;
	}

	public String getFdLedgerInvoiceId() {
		return fdLedgerInvoiceId;
	}

	public void setFdLedgerInvoiceId(String fdLedgerInvoiceId) {
		this.fdLedgerInvoiceId = fdLedgerInvoiceId;
	}

	public String getFdAttNumber() {
		return fdAttNumber;
	}

	public void setFdAttNumber(String fdAttNumber) {
		this.fdAttNumber = fdAttNumber;
	}
	
	@Override
    public SysRelationMainForm getSysRelationMainForm() {
        return sysRelationMainForm;
    }

	/*回收站机制开始*/
	private Integer docDeleteFlag;

	@Override
    public Integer getDocDeleteFlag() {
		return docDeleteFlag;
	}

	@Override
    public void setDocDeleteFlag(Integer docDeleteFlag) {
		this.docDeleteFlag = docDeleteFlag;
	}
	/*回收站机制结束*/

	public String getDocPublishTime() {
		return docPublishTime;
	}

	public void setDocPublishTime(String docPublishTime) {
		this.docPublishTime = docPublishTime;
	}

    public AutoArrayList getFdTranDataList_Form() {
        return this.fdTranDataList_Form;
    }

    public void setFdTranDataList_Form(AutoArrayList fdTranDataList_Form) {
        this.fdTranDataList_Form = fdTranDataList_Form;
    }

    public String getFdTranDataList_Flag() {
        return this.fdTranDataList_Flag;
    }

    public void setFdTranDataList_Flag(String fdTranDataList_Flag) {
        this.fdTranDataList_Flag = fdTranDataList_Flag;
    }

    /**
     * 交单记录
     * @return
     */
    public JSONArray getPresList() {
        return presList;
    }

    /**
     * 交单记录
     * @param presList
     */
    public void setPresList(JSONArray presList) {
        this.presList = presList;
    }

    /**
     * 实体单据状态
     * @return
     */
    public String getFdBillStatus() {
        return fdBillStatus;
    }

    /**
     * 实体单据状态
     * @param fdBillStatus
     */
    public void setFdBillStatus(String fdBillStatus) {
        this.fdBillStatus = fdBillStatus;
    }
}
