package com.landray.kmss.fssc.expense.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalance;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.circulation.forms.CirculationForm;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.recycle.forms.ISysRecycleModelForm;
import com.landray.kmss.sys.relation.forms.SysRelationMainForm;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 调账
  */
public class FsscExpenseBalanceForm extends ExtendAuthForm implements ISysLbpmMainForm, IAttachmentForm, ISysRelationMainForm, ISysCirculationForm , ISysRecycleModelForm{

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String docSubject;

    private String fdAttNum;

    private String fdMonth;

    private String fdSubject;

    private String docNumber;

    private String docCreatorId;

    private String docCreatorName;

    private String fdCompanyId;

    private String fdCompanyName;

    private String fdCostCenterId;

    private String fdCostCenterName;

    private String docCreatorDeptId;

    private String docCreatorDeptName;

    private String fdVoucherTypeId;

    private String fdVoucherTypeName;

    private String fdCurrencyId;

    private String fdCurrencyName;

    private String docTemplateId;

    private String docTemplateName;

    private String fdVoucherStatus;

    private String fdBookkeepingStatus;

    private String fdBookkeepingMessage;
    
    private String fdBudgetRate;

    private AutoArrayList fdDetailList_Form = new AutoArrayList(FsscExpenseBalanceDetailForm.class);

    private String fdDetailList_Flag;

    private LbpmProcessForm sysWfBusinessForm = new LbpmProcessForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
    
    private SysRelationMainForm sysRelationMainForm = new SysRelationMainForm();
    
    public CirculationForm circulationForm = new CirculationForm();
    
    private String docPublishTime;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdBudgetRate = null;
        fdVoucherStatus = null;
        fdBookkeepingStatus = null;
        fdBookkeepingMessage = null;
        docCreateTime = null;
        docSubject = null;
        fdAttNum = null;
        fdMonth = null;
        fdSubject = null;
        docNumber = null;
        docCreatorId = null;
        docCreatorName = null;
        fdCompanyId = null;
        fdCompanyName = null;
        fdCostCenterId = null;
        fdCostCenterName = null;
        docCreatorDeptId = null;
        docCreatorDeptName = null;
        fdVoucherTypeId = null;
        fdVoucherTypeName = null;
        fdCurrencyId = null;
        fdCurrencyName = null;
        docTemplateId = null;
        docTemplateName = null;
        fdDetailList_Form = new AutoArrayList(FsscExpenseBalanceDetailForm.class);
        fdDetailList_Flag = null;
        sysWfBusinessForm = new LbpmProcessForm();
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docPublishTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseBalance> getModelClass() {
        return FsscExpenseBalance.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("fdCostCenterId", new FormConvertor_IDToModel("fdCostCenter", EopBasedataCostCenter.class));
            toModelPropertyMap.put("fdVoucherTypeId", new FormConvertor_IDToModel("fdVoucherType", EopBasedataVoucherType.class));
            toModelPropertyMap.put("fdCurrencyId", new FormConvertor_IDToModel("fdCurrency", EopBasedataCurrency.class));
            toModelPropertyMap.put("docTemplateId", new FormConvertor_IDToModel("docTemplate", FsscExpenseBalanceCategory.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
            toModelPropertyMap.put("authAttCopyIds", new FormConvertor_IDsToModelList("authAttCopys", SysOrgElement.class));
            toModelPropertyMap.put("authAttDownloadIds", new FormConvertor_IDsToModelList("authAttDownloads", SysOrgElement.class));
            toModelPropertyMap.put("authAttPrintIds", new FormConvertor_IDsToModelList("authAttPrints", SysOrgElement.class));
            toModelPropertyMap.put("fdDetailList_Form", new FormConvertor_FormListToModelList("fdDetailList", "docMain", "fdDetailList_Flag"));
            toModelPropertyMap.put("docPublishTime", new FormConvertor_Common("docPublishTime").setDateTimeType(DateUtil.TYPE_DATE));
        }
        return toModelPropertyMap;
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
     * 创建日期
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建日期
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 主题
     */
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
    public String getFdAttNum() {
        return this.fdAttNum;
    }

    /**
     * 附件(张)
     */
    public void setFdAttNum(String fdAttNum) {
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
     * 经办人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 经办人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 经办人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 经办人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 费用公司
     */
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 费用公司
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
    }

    /**
     * 费用公司
     */
    public String getFdCompanyName() {
        return this.fdCompanyName;
    }

    /**
     * 费用公司
     */
    public void setFdCompanyName(String fdCompanyName) {
        this.fdCompanyName = fdCompanyName;
    }

    /**
     * 成本中心
     */
    public String getFdCostCenterId() {
        return this.fdCostCenterId;
    }

    /**
     * 成本中心
     */
    public void setFdCostCenterId(String fdCostCenterId) {
        this.fdCostCenterId = fdCostCenterId;
    }

    /**
     * 成本中心
     */
    public String getFdCostCenterName() {
        return this.fdCostCenterName;
    }

    /**
     * 成本中心
     */
    public void setFdCostCenterName(String fdCostCenterName) {
        this.fdCostCenterName = fdCostCenterName;
    }

    /**
     * 经办人部门
     */
    public String getDocCreatorDeptId() {
        return this.docCreatorDeptId;
    }

    /**
     * 经办人部门
     */
    public void setDocCreatorDeptId(String docCreatorDeptId) {
        this.docCreatorDeptId = docCreatorDeptId;
    }

    /**
     * 经办人部门
     */
    public String getDocCreatorDeptName() {
        return this.docCreatorDeptName;
    }

    /**
     * 经办人部门
     */
    public void setDocCreatorDeptName(String docCreatorDeptName) {
        this.docCreatorDeptName = docCreatorDeptName;
    }

    /**
     * 凭证类型
     */
    public String getFdVoucherTypeId() {
        return this.fdVoucherTypeId;
    }

    /**
     * 凭证类型
     */
    public void setFdVoucherTypeId(String fdVoucherTypeId) {
        this.fdVoucherTypeId = fdVoucherTypeId;
    }

    /**
     * 凭证类型
     */
    public String getFdVoucherTypeName() {
        return this.fdVoucherTypeName;
    }

    /**
     * 凭证类型
     */
    public void setFdVoucherTypeName(String fdVoucherTypeName) {
        this.fdVoucherTypeName = fdVoucherTypeName;
    }

    /**
     * 币种
     */
    public String getFdCurrencyId() {
        return this.fdCurrencyId;
    }

    /**
     * 币种
     */
    public void setFdCurrencyId(String fdCurrencyId) {
        this.fdCurrencyId = fdCurrencyId;
    }

    /**
     * 币种
     */
    public String getFdCurrencyName() {
        return this.fdCurrencyName;
    }

    /**
     * 币种
     */
    public void setFdCurrencyName(String fdCurrencyName) {
        this.fdCurrencyName = fdCurrencyName;
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
     * 调账明细
     */
    public AutoArrayList getFdDetailList_Form() {
        return this.fdDetailList_Form;
    }

    /**
     * 调账明细
     */
    public void setFdDetailList_Form(AutoArrayList fdDetailList_Form) {
        this.fdDetailList_Form = fdDetailList_Form;
    }

    /**
     * 调账明细
     */
    public String getFdDetailList_Flag() {
        return this.fdDetailList_Flag;
    }

    /**
     * 调账明细
     */
    public void setFdDetailList_Flag(String fdDetailList_Flag) {
        this.fdDetailList_Flag = fdDetailList_Flag;
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

	public String getFdBudgetRate() {
		return fdBudgetRate;
	}

	public void setFdBudgetRate(String fdBudgetRate) {
		this.fdBudgetRate = fdBudgetRate;
	}
	
	@Override
    public SysRelationMainForm getSysRelationMainForm() {
        return sysRelationMainForm;
    }
	
	@Override
    public CirculationForm getCirculationForm() {
        return circulationForm;
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
}
