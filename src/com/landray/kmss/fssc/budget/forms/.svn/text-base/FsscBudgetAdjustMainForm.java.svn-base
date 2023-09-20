package com.landray.kmss.fssc.budget.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.circulation.forms.CirculationForm;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.recycle.forms.ISysRecycleModelForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 预算调整
  */
public class FsscBudgetAdjustMainForm extends ExtendAuthForm implements IAttachmentForm,ISysCirculationForm,ISysLbpmMainForm ,ISysRecycleModelForm{

	private static final long serialVersionUID = 1378830353107471972L;

	private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String docSubject;

    private String fdDesc;

    private String docNumber;

    private String docCreatorId;

    private String docCreatorName;

    private String docTemplateId;

    private String docTemplateName;

    private String fdCompanyId;

    private String fdCompanyName;

    private String fdCurrencyId;

    private String fdCurrencyName;

    private String fdBudgetSchemeId;

    private String fdBudgetSchemeName;
    
    private String fdTips;

    private AutoArrayList fdDetailList_Form = new AutoArrayList(FsscBudgetAdjustDetailForm.class);

    private String fdDetailList_Flag;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    private LbpmProcessForm sysWfBusinessForm = new LbpmProcessForm();
    
    public CirculationForm circulationForm = new CirculationForm();
    
    private String docPublishTime;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        docSubject = null;
        fdDesc = null;
        docNumber = null;
        docCreatorId = null;
        docCreatorName = null;
        docTemplateId = null;
        docTemplateName = null;
        fdCompanyId = null;
        fdCompanyName = null;
        fdCurrencyId = null;
        fdCurrencyName = null;
        fdBudgetSchemeId = null;
        fdBudgetSchemeName = null;
        fdTips=null;
        fdDetailList_Form = new AutoArrayList(FsscBudgetAdjustDetailForm.class);
        fdDetailList_Flag = null;
        sysWfBusinessForm = new LbpmProcessForm();
        docPublishTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetAdjustMain> getModelClass() {
        return FsscBudgetAdjustMain.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docStatus");
            toModelPropertyMap.addNoConvertProperty("docNumber");
            toModelPropertyMap.put("docTemplateId", new FormConvertor_IDToModel("docTemplate", FsscBudgetAdjustCategory.class));
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("fdCurrencyId", new FormConvertor_IDToModel("fdCurrency", EopBasedataCurrency.class));
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
     * 调整原因
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 调整原因
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
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
     * 所属公司
     */
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 所属公司
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
    }

    /**
     * 所属公司
     */
    public String getFdCompanyName() {
        return this.fdCompanyName;
    }

    /**
     * 所属公司
     */
    public void setFdCompanyName(String fdCompanyName) {
        this.fdCompanyName = fdCompanyName;
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
     * 预算方案
     */
    public String getFdBudgetSchemeId() {
        return this.fdBudgetSchemeId;
    }

    /**
     * 预算方案
     */
    public void setFdBudgetSchemeId(String fdBudgetSchemeId) {
        this.fdBudgetSchemeId = fdBudgetSchemeId;
    }

    /**
     * 预算方案
     */
    public String getFdBudgetSchemeName() {
        return this.fdBudgetSchemeName;
    }

    /**
     * 预算方案
     */
    public void setFdBudgetSchemeName(String fdBudgetSchemeName) {
        this.fdBudgetSchemeName = fdBudgetSchemeName;
    }
    
    /**
     * 预算调整提示信息，若是跨月，而借入当月或者当季无预算，提示
     */
    public String getFdTips() {
		return fdTips;
	}
    
    /**
     * 预算调整提示信息，若是跨月，而借入当月或者当季无预算，提示
     */
	public void setFdTips(String fdTips) {
		this.fdTips = fdTips;
	}

	/**
     * 预算调整明细
     */
    public AutoArrayList getFdDetailList_Form() {
        return this.fdDetailList_Form;
    }

    /**
     * 预算调整明细
     */
    public void setFdDetailList_Form(AutoArrayList fdDetailList_Form) {
        this.fdDetailList_Form = fdDetailList_Form;
    }

    /**
     * 预算调整明细
     */
    public String getFdDetailList_Flag() {
        return this.fdDetailList_Flag;
    }

    /**
     * 预算调整明细
     */
    public void setFdDetailList_Flag(String fdDetailList_Flag) {
        this.fdDetailList_Flag = fdDetailList_Flag;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
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
