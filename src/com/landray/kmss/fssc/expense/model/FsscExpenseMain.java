package com.landray.kmss.fssc.expense.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.fssc.expense.forms.FsscExpenseMainForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationModel;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModel;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.model.ExtendDataModelInfo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.recycle.model.ISysRecycleModel;
import com.landray.kmss.sys.recycle.model.SysRecycleConstant;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainModel;
import com.landray.kmss.sys.relation.model.SysRelationMain;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
  * 报销主表
  */
public class FsscExpenseMain extends ExtendAuthModel implements InterceptFieldEnabled, ISysLbpmMainModel, IAttachment, IExtendDataModel, ISysCirculationModel, ISysRelationMainModel, ISysRecycleModel {

    private static ModelToFormPropertyMap toFormPropertyMap;
    
    private String fdProappId;
    
    private String fdProappName;
    
    private Integer fdAttNumber;
    
    private String fdLedgerInvoiceId;
    
    private String fdFeeIds;
    
    private String fdFeeNames;
    
    private Boolean fdIsCloseFee;
    
    private Boolean fdIsAmortize;
    
    private String fdAmortizeBegin;
    
    private String fdAmortizeEnd;
    
    private Integer fdAmortizeMonth;
    
    private Double fdAmortizeMoney;
    
    private String docNumber;
    
    private String fdPaymentStatus;
    
    private String fdVoucherStatus;
    
    private String docSubject;

    private String fdContent;

    private Double fdTotalStandaryMoney;
    
    private Double fdTotalApprovedMoney;

    private Boolean fdIsOffsetLoan;

    private String fdStandardMessage;

    private String docXform;

    private Boolean docUseXform;

    private String extendDataXML;

    private String extendFilePath;

    private String fdBookkeepingStatus;

    private String fdBookkeepingMessage;
    
    private String fdDeduFlag;

    private FsscExpenseCategory docTemplate;

    private EopBasedataCompany fdCompany;

    private EopBasedataCostCenter fdCostCenter;

    private SysOrgPerson fdClaimant;

    private SysOrgElement fdClaimantDept;
    
    private SysOrgElement fdExpenseDept;//报销部门


    private EopBasedataProject fdProject;
    
    private EopBasedataProject fdProjectAccounting;	//核算项目

    private List<FsscExpenseDetail> fdDetailList;

    private List<FsscExpenseTravelDetail> fdTravelList;

    private List<FsscExpenseAccounts> fdAccountsList;

    private List<FsscExpenseOffsetLoan> fdOffsetList;

    private List<FsscExpenseInvoiceDetail> fdInvoiceList;
    
    private List<FsscExpenseAmortize> fdAmortizeList;
    
    private String fdIsExportBank;

    private LbpmProcessForm sysWfBusinessModel = new LbpmProcessForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    private ExtendDataModelInfo extendDataModelInfo = new ExtendDataModelInfo(this);
    
    private SysRelationMain sysRelationMain = null;

    private String relationSeparate = null;
    
    private List<FsscExpenseDidiDetail> fdDidiDetail;

    private List<FsscExpenseTranData> fdTranDataList;

    //发布时间
    private Date  docPublishTime;

    private String fdBillStatus;    //实体单据状态

    @Override
    public Class<FsscExpenseMainForm> getFormClass() {
        return FsscExpenseMainForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docPublishTime", new ModelConvertor_Common("docPublishTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.addNoConvertProperty("authEditorFlag");
            toFormPropertyMap.addNoConvertProperty("extendDataXML");
            toFormPropertyMap.addNoConvertProperty("extendFilePath");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docTemplate.fdName", "docTemplateName");
            toFormPropertyMap.put("docTemplate.fdId", "docTemplateId");
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
            toFormPropertyMap.put("fdCostCenter.fdName", "fdCostCenterName");
            toFormPropertyMap.put("fdCostCenter.fdId", "fdCostCenterId");
            toFormPropertyMap.put("fdClaimant.fdName", "fdClaimantName");
            toFormPropertyMap.put("fdClaimant.fdId", "fdClaimantId");
            toFormPropertyMap.put("fdClaimantDept.fdName", "fdClaimantDeptName");
            toFormPropertyMap.put("fdClaimantDept.fdId", "fdClaimantDeptId");
            toFormPropertyMap.put("fdExpenseDept.fdName", "fdExpenseDeptName");
            toFormPropertyMap.put("fdExpenseDept.fdId", "fdExpenseDeptId");
            
            toFormPropertyMap.put("fdProject.fdName", "fdProjectName");
            toFormPropertyMap.put("fdProject.fdId", "fdProjectId");
            toFormPropertyMap.put("fdProjectAccounting.fdName", "fdProjectAccountingName");
            toFormPropertyMap.put("fdProjectAccounting.fdId", "fdProjectAccountingId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttCopys", new ModelConvertor_ModelListToString("authAttCopyIds:authAttCopyNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttDownloads", new ModelConvertor_ModelListToString("authAttDownloadIds:authAttDownloadNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttPrints", new ModelConvertor_ModelListToString("authAttPrintIds:authAttPrintNames", "fdId:fdName"));
            toFormPropertyMap.put("fdDetailList", new ModelConvertor_ModelListToFormList("fdDetailList_Form"));
            toFormPropertyMap.put("fdTravelList", new ModelConvertor_ModelListToFormList("fdTravelList_Form"));
            toFormPropertyMap.put("fdAccountsList", new ModelConvertor_ModelListToFormList("fdAccountsList_Form"));
            toFormPropertyMap.put("fdOffsetList", new ModelConvertor_ModelListToFormList("fdOffsetList_Form"));
            toFormPropertyMap.put("fdInvoiceList", new ModelConvertor_ModelListToFormList("fdInvoiceList_Form"));
            toFormPropertyMap.put("fdAmortizeList", new ModelConvertor_ModelListToFormList("fdAmortizeList_Form"));
            toFormPropertyMap.put("fdDidiDetail", new ModelConvertor_ModelListToFormList("fdDidiDetail_Form"));
            toFormPropertyMap.put("fdTranDataList", new ModelConvertor_ModelListToFormList("fdTranDataList_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
        if (!getAuthReaderFlag()) {
        }
    }

    public List<FsscExpenseDidiDetail> getFdDidiDetail() {
		return fdDidiDetail;
	}

	public void setFdDidiDetail(List<FsscExpenseDidiDetail> fdDidiDetail) {
		this.fdDidiDetail = fdDidiDetail;
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
     * 标题
     */
    @Override
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
    public Double getFdTotalStandaryMoney() {
        return this.fdTotalStandaryMoney;
    }

    /**
     * 合计申请金额(本币)
     */
    public void setFdTotalStandaryMoney(Double fdTotalStandaryMoney) {
        this.fdTotalStandaryMoney = fdTotalStandaryMoney;
    }
    
    /**
     * 核准金额合计(本币)
     */
    public Double getFdTotalApprovedMoney() {
        return this.fdTotalApprovedMoney;
    }

    /**
     * 核准金额合计(本币)
     */
    public void setFdTotalApprovedMoney(Double fdTotalApprovedMoney) {
        this.fdTotalApprovedMoney = fdTotalApprovedMoney;
    }
    
    /**
     * 是否冲抵借款
     */
    public Boolean getFdIsOffsetLoan() {
        return this.fdIsOffsetLoan;
    }

    /**
     * 是否冲抵借款
     */
    public void setFdIsOffsetLoan(Boolean fdIsOffsetLoan) {
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
        return (String) readLazyField("docXform", this.docXform);
    }

    /**
     * 扩展属性
     */
    public void setDocXform(String docXform) {
        this.docXform = (String) writeLazyField("docXform", this.docXform, docXform);
    }

    /**
     * 是否使用表单
     */
    public Boolean getDocUseXform() {
        return this.docUseXform;
    }

    /**
     * 是否使用表单
     */
    public void setDocUseXform(Boolean docUseXform) {
        this.docUseXform = docUseXform;
    }

    /**
     * 扩展属性
     */
    @Override
    public String getExtendDataXML() {
        return (String) readLazyField("extendDataXML", this.extendDataXML);
    }

    /**
     * 扩展属性
     */
    @Override
    public void setExtendDataXML(String extendDataXML) {
        this.extendDataXML = (String) writeLazyField("extendDataXML", this.extendDataXML, extendDataXML);
    }

    /**
     * 扩展属性文件路径
     */
    @Override
    public String getExtendFilePath() {
        return this.extendFilePath;
    }

    /**
     * 扩展属性文件路径
     */
    @Override
    public void setExtendFilePath(String extendFilePath) {
        this.extendFilePath = extendFilePath;
    }

    /**
     * 分类模板
     */
    public FsscExpenseCategory getDocTemplate() {
        return this.docTemplate;
    }

    /**
     * 分类模板
     */
    public void setDocTemplate(FsscExpenseCategory docTemplate) {
        this.docTemplate = docTemplate;
    }

    /**
     * 费用承担方
     */
    public EopBasedataCompany getFdCompany() {
        return this.fdCompany;
    }

    /**
     * 费用承担方
     */
    public void setFdCompany(EopBasedataCompany fdCompany) {
        this.fdCompany = fdCompany;
    }

    /**
     * 费用承担部门
     */
    public EopBasedataCostCenter getFdCostCenter() {
        return this.fdCostCenter;
    }

    /**
     * 费用承担部门
     */
    public void setFdCostCenter(EopBasedataCostCenter fdCostCenter) {
        this.fdCostCenter = fdCostCenter;
    }

    /**
     * 报销人
     */
    public SysOrgPerson getFdClaimant() {
        return this.fdClaimant;
    }

    /**
     * 报销人
     */
    public void setFdClaimant(SysOrgPerson fdClaimant) {
        this.fdClaimant = fdClaimant;
    }

    /**
     * 报销人部门
     */
    public SysOrgElement getFdClaimantDept() {
        return this.fdClaimantDept;
    }

    /**
     * 报销人部门
     */
    public void setFdClaimantDept(SysOrgElement fdClaimantDept) {
        this.fdClaimantDept = fdClaimantDept;
    }

    public SysOrgElement getFdExpenseDept() {
		return fdExpenseDept;
	}

	public void setFdExpenseDept(SysOrgElement fdExpenseDept) {
		this.fdExpenseDept = fdExpenseDept;
	}

	/**
     * 归属项目
     */
    public EopBasedataProject getFdProject() {
        return this.fdProject;
    }

    /**
     * 归属项目
     */
    public void setFdProject(EopBasedataProject fdProject) {
        this.fdProject = fdProject;
    }
    
    /**
     * 核算项目
     */
    public EopBasedataProject getFdProjectAccounting() {
        return this.fdProjectAccounting;
    }

    /**
     * 核算项目
     */
    public void setFdProjectAccounting(EopBasedataProject fdProjectAccounting) {
        this.fdProjectAccounting = fdProjectAccounting;
    }

    /**
     * 费用明细
     */
    public List<FsscExpenseDetail> getFdDetailList() {
        return this.fdDetailList;
    }

    /**
     * 费用明细
     */
    public void setFdDetailList(List<FsscExpenseDetail> fdDetailList) {
        this.fdDetailList = fdDetailList;
    }

    /**
     * 行程明细
     */
    public List<FsscExpenseTravelDetail> getFdTravelList() {
        return this.fdTravelList;
    }

    /**
     * 行程明细
     */
    public void setFdTravelList(List<FsscExpenseTravelDetail> fdTravelList) {
        this.fdTravelList = fdTravelList;
    }

    /**
     * 收款账户明细
     */
    public List<FsscExpenseAccounts> getFdAccountsList() {
        return this.fdAccountsList;
    }

    /**
     * 收款账户明细
     */
    public void setFdAccountsList(List<FsscExpenseAccounts> fdAccountsList) {
        this.fdAccountsList = fdAccountsList;
    }

    /**
     * 借款信息
     */
    public List<FsscExpenseOffsetLoan> getFdOffsetList() {
        return this.fdOffsetList;
    }

    /**
     * 借款信息
     */
    public void setFdOffsetList(List<FsscExpenseOffsetLoan> fdOffsetList) {
        this.fdOffsetList = fdOffsetList;
    }

    /**
     * 发票明细
     */
    public List<FsscExpenseInvoiceDetail> getFdInvoiceList() {
        return this.fdInvoiceList;
    }

    /**
     * 发票明细
     */
    public void setFdInvoiceList(List<FsscExpenseInvoiceDetail> fdInvoiceList) {
        this.fdInvoiceList = fdInvoiceList;
    }

    /**
     * 返回 所有人可阅读标记
     */
    @Override
    public Boolean getAuthReaderFlag() {
        return false;
    }

    @Override
    public LbpmProcessForm getSysWfBusinessModel() {
        return sysWfBusinessModel;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    @Override
    public ExtendDataModelInfo getExtendDataModelInfo() {
        return extendDataModelInfo;
    }

    @Override
    public String getCirculationSeparate() {
        return null;
    }

    @Override
    public void setCirculationSeparate(String circulationSeparate) {
    }

    /**
     * 付款状态
     * @return
     */
	public String getFdPaymentStatus() {
		return fdPaymentStatus;
	}
	/**
     * 付款状态
     * @return
     */
	public void setFdPaymentStatus(String fdPaymentStatus) {
		this.fdPaymentStatus = fdPaymentStatus;
	}

	/**
     * 制证状态
     * @return
     */
	public String getFdVoucherStatus() {
		return fdVoucherStatus;
	}
	/**
     * 制证状态
     * @return
     */
	public void setFdVoucherStatus(String fdVoucherStatus) {
		this.fdVoucherStatus = fdVoucherStatus;
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


	public String getFdIsExportBank() {
		return fdIsExportBank;
	}

	public void setFdIsExportBank(String fdIsExportBank) {
		this.fdIsExportBank = fdIsExportBank;
	}

	public List<FsscExpenseAmortize> getFdAmortizeList() {
		return fdAmortizeList;
	}

	public void setFdAmortizeList(List<FsscExpenseAmortize> fdAmortizeList) {
		this.fdAmortizeList = fdAmortizeList;
	}

	public Boolean getFdIsAmortize() {
		return fdIsAmortize;
	}

	public void setFdIsAmortize(Boolean fdIsAmortize) {
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

	public Integer getFdAmortizeMonth() {
		return fdAmortizeMonth;
	}

	public void setFdAmortizeMonth(Integer fdAmortizeMonth) {
		this.fdAmortizeMonth = fdAmortizeMonth;
	}

	public Double getFdAmortizeMoney() {
		return fdAmortizeMoney;
	}

	public void setFdAmortizeMoney(Double fdAmortizeMoney) {
		this.fdAmortizeMoney = fdAmortizeMoney;
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
	
	public Boolean getFdIsCloseFee() {
		return fdIsCloseFee;
	}

	public void setFdIsCloseFee(Boolean fdIsCloseFee) {
		this.fdIsCloseFee = fdIsCloseFee;
	}

	public String getFdLedgerInvoiceId() {
		return fdLedgerInvoiceId;
	}

	public void setFdLedgerInvoiceId(String fdLedgerInvoiceId) {
		this.fdLedgerInvoiceId = fdLedgerInvoiceId;
	}

	public Integer getFdAttNumber() {
		return fdAttNumber;
	}

	public void setFdAttNumber(Integer fdAttNumber) {
		this.fdAttNumber = fdAttNumber;
	}
	
	@Override
    public SysRelationMain getSysRelationMain() {
        return this.sysRelationMain;
    }

    @Override
    public void setSysRelationMain(SysRelationMain sysRelationMain) {
        this.sysRelationMain = sysRelationMain;
    }

    public String getRelationSeparate() {
        return this.relationSeparate;
    }

    public void setRelationSeparate(String relationSeparate) {
        this.relationSeparate = relationSeparate;
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

	private Date docDeleteTime;

	@Override
	public Date getDocDeleteTime() {
		return docDeleteTime;
	}

	@Override
	public void setDocDeleteTime(Date docDeleteTime) {
		this.docDeleteTime = docDeleteTime;
	}

	private SysOrgPerson docDeleteBy;

	@Override
	public SysOrgPerson getDocDeleteBy() {
		return docDeleteBy;
	}

	@Override
	public void setDocDeleteBy(SysOrgPerson docDeleteBy) {
		this.docDeleteBy = docDeleteBy;
	}

	public boolean isNeedIndex() {
		return docDeleteFlag == null || docDeleteFlag == SysRecycleConstant.OPT_TYPE_RECOVER;
	}
	/*回收站机制结束*/

	public Date getDocPublishTime() {
		return docPublishTime;
	}

	public void setDocPublishTime(Date docPublishTime) {
		this.docPublishTime = docPublishTime;
	}

    public List<FsscExpenseTranData> getFdTranDataList() {
        return this.fdTranDataList;
    }

    public void setFdTranDataList(List<FsscExpenseTranData> fdTranDataList) {
        this.fdTranDataList = fdTranDataList;
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
