package com.landray.kmss.fssc.expense.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.fssc.expense.forms.FsscExpenseBalanceForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationModel;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModel;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.recycle.model.ISysRecycleModel;
import com.landray.kmss.sys.recycle.model.SysRecycleConstant;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainModel;
import com.landray.kmss.sys.relation.model.SysRelationMain;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
  * 调账
  */
public class FsscExpenseBalance extends ExtendAuthModel implements ISysLbpmMainModel, IAttachment, ISysRelationMainModel, ISysCirculationModel, ISysRecycleModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private Integer fdAttNum;

    private String fdMonth;

    private String fdSubject;

    private String docNumber;

    private String fdVoucherStatus;

    private String fdBookkeepingStatus;

    private String fdBookkeepingMessage;

    private EopBasedataCompany fdCompany;

    private EopBasedataCostCenter fdCostCenter;

    private SysOrgElement docCreatorDept;

    private EopBasedataVoucherType fdVoucherType;

    private EopBasedataCurrency fdCurrency;
    
    private Double fdBudgetRate;

    private FsscExpenseBalanceCategory docTemplate;

    private List<FsscExpenseBalanceDetail> fdDetailList;

    private LbpmProcessForm sysWfBusinessModel = new LbpmProcessForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
    
    private SysRelationMain sysRelationMain = null;

    private String relationSeparate = null;
    
    //发布时间
    private Date  docPublishTime;

    @Override
    public Class<FsscExpenseBalanceForm> getFormClass() {
        return FsscExpenseBalanceForm.class;
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
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
            toFormPropertyMap.put("fdCostCenter.fdName", "fdCostCenterName");
            toFormPropertyMap.put("fdCostCenter.fdId", "fdCostCenterId");
            toFormPropertyMap.put("docCreatorDept.fdName", "docCreatorDeptName");
            toFormPropertyMap.put("docCreatorDept.fdId", "docCreatorDeptId");
            toFormPropertyMap.put("fdVoucherType.fdName", "fdVoucherTypeName");
            toFormPropertyMap.put("fdVoucherType.fdId", "fdVoucherTypeId");
            toFormPropertyMap.put("fdCurrency.fdName", "fdCurrencyName");
            toFormPropertyMap.put("fdCurrency.fdId", "fdCurrencyId");
            toFormPropertyMap.put("docTemplate.fdName", "docTemplateName");
            toFormPropertyMap.put("docTemplate.fdId", "docTemplateId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttCopys", new ModelConvertor_ModelListToString("authAttCopyIds:authAttCopyNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttDownloads", new ModelConvertor_ModelListToString("authAttDownloadIds:authAttDownloadNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttPrints", new ModelConvertor_ModelListToString("authAttPrintIds:authAttPrintNames", "fdId:fdName"));
            toFormPropertyMap.put("fdDetailList", new ModelConvertor_ModelListToFormList("fdDetailList_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
        if (!getAuthReaderFlag()) {
        }
    }

    /**
     * 制证状态
     */
    public String getFdVoucherStatus() {
        return this.fdVoucherStatus;
    }

    /**
     * 制证状态
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
     * 主题
     */
    @Override
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 主题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * 附件(张)
     */
    public Integer getFdAttNum() {
        return this.fdAttNum;
    }

    /**
     * 附件(张)
     */
    public void setFdAttNum(Integer fdAttNum) {
        this.fdAttNum = fdAttNum;
    }

    /**
     * 月份
     */
    public String getFdMonth() {
        return this.fdMonth;
    }

    /**
     * 月份
     */
    public void setFdMonth(String fdMonth) {
        this.fdMonth = fdMonth;
    }

    /**
     * 凭证抬头文本
     */
    public String getFdSubject() {
        return this.fdSubject;
    }

    /**
     * 凭证抬头文本
     */
    public void setFdSubject(String fdSubject) {
        this.fdSubject = fdSubject;
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
     * 费用公司
     */
    public EopBasedataCompany getFdCompany() {
        return this.fdCompany;
    }

    /**
     * 费用公司
     */
    public void setFdCompany(EopBasedataCompany fdCompany) {
        this.fdCompany = fdCompany;
    }

    /**
     * 成本中心
     */
    public EopBasedataCostCenter getFdCostCenter() {
        return this.fdCostCenter;
    }

    /**
     * 成本中心
     */
    public void setFdCostCenter(EopBasedataCostCenter fdCostCenter) {
        this.fdCostCenter = fdCostCenter;
    }

    /**
     * 经办人部门
     */
    public SysOrgElement getDocCreatorDept() {
        return this.docCreatorDept;
    }

    /**
     * 经办人部门
     */
    public void setDocCreatorDept(SysOrgElement docCreatorDept) {
        this.docCreatorDept = docCreatorDept;
    }

    /**
     * 凭证类型
     */
    public EopBasedataVoucherType getFdVoucherType() {
        return this.fdVoucherType;
    }

    /**
     * 凭证类型
     */
    public void setFdVoucherType(EopBasedataVoucherType fdVoucherType) {
        this.fdVoucherType = fdVoucherType;
    }

    /**
     * 币种
     */
    public EopBasedataCurrency getFdCurrency() {
        return this.fdCurrency;
    }

    /**
     * 币种
     */
    public void setFdCurrency(EopBasedataCurrency fdCurrency) {
        this.fdCurrency = fdCurrency;
    }

    /**
     * 分类模板
     */
    public FsscExpenseBalanceCategory getDocTemplate() {
        return this.docTemplate;
    }

    /**
     * 分类模板
     */
    public void setDocTemplate(FsscExpenseBalanceCategory docTemplate) {
        this.docTemplate = docTemplate;
    }

    /**
     * 调账明细
     */
    public List<FsscExpenseBalanceDetail> getFdDetailList() {
        return this.fdDetailList;
    }

    /**
     * 调账明细
     */
    public void setFdDetailList(List<FsscExpenseBalanceDetail> fdDetailList) {
        this.fdDetailList = fdDetailList;
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

	public Double getFdBudgetRate() {
		return fdBudgetRate;
	}

	public void setFdBudgetRate(Double fdBudgetRate) {
		this.fdBudgetRate = fdBudgetRate;
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
    
    @Override
    public String getCirculationSeparate() {
        return null;
    }

    @Override
    public void setCirculationSeparate(String circulationSeparate) {
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
	
}
