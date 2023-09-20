package com.landray.kmss.fssc.expense.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareMain;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.circulation.forms.CirculationForm;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.recycle.forms.ISysRecycleModelForm;
import com.landray.kmss.sys.relation.forms.SysRelationMainForm;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 事后分摊
  */
public class FsscExpenseShareMainForm extends ExtendAuthForm implements ISysLbpmMainForm, IAttachmentForm, ISysRelationMainForm, ISysCirculationForm , ISysRecycleModelForm{

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docSubject;

    private String docCreateTime;

    private String fdOperateDate;

    private String fdDescription;

    private String fdNumber;

    private String docCreatorId;

    private String docCreatorName;

    private String fdOperatorId;

    private String fdOperatorName;

    private String fdOperatorDeptId;

    private String fdOperatorDeptName;

    private String fdModelId;	//	关联单据id

    private String fdModelName;	//	关联单据

    private String docTemplateId;

    private String docTemplateName;

    private String fdVoucherStatus;

    private String fdBookkeepingStatus;

    private String fdBookkeepingMessage;

    private AutoArrayList fdDetailList_Form = new AutoArrayList(FsscExpenseShareDetailForm.class);

    private String fdDetailList_Flag;

    private LbpmProcessForm sysWfBusinessForm = new LbpmProcessForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
    
    private SysRelationMainForm sysRelationMainForm = new SysRelationMainForm();
    
    public CirculationForm circulationForm = new CirculationForm();
    
    private String docPublishTime;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdVoucherStatus = null;
        fdBookkeepingStatus = null;
        fdBookkeepingMessage = null;
        docSubject = null;
        docCreateTime = null;
        fdOperateDate = null;
        fdDescription = null;
        fdNumber = null;
        docCreatorId = null;
        docCreatorName = null;
        fdOperatorId = null;
        fdOperatorName = null;
        fdOperatorDeptId = null;
        fdOperatorDeptName = null;
        fdModelId = null;
        fdModelName = null;
        docTemplateId = null;
        docTemplateName = null;
        fdDetailList_Form = new AutoArrayList(FsscExpenseShareDetailForm.class);
        fdDetailList_Flag = null;
        sysWfBusinessForm = new LbpmProcessForm();
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docPublishTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseShareMain> getModelClass() {
        return FsscExpenseShareMain.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docStatus");
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdOperateDate", new FormConvertor_Common("fdOperateDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.addNoConvertProperty("docNumber");
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
            toModelPropertyMap.put("authAttCopyIds", new FormConvertor_IDsToModelList("authAttCopys", SysOrgElement.class));
            toModelPropertyMap.put("authAttDownloadIds", new FormConvertor_IDsToModelList("authAttDownloads", SysOrgElement.class));
            toModelPropertyMap.put("authAttPrintIds", new FormConvertor_IDsToModelList("authAttPrints", SysOrgElement.class));
            toModelPropertyMap.put("fdOperatorId", new FormConvertor_IDToModel("fdOperator", SysOrgPerson.class));
            toModelPropertyMap.put("fdOperatorDeptId", new FormConvertor_IDToModel("fdOperatorDept", SysOrgElement.class));
            toModelPropertyMap.put("docTemplateId", new FormConvertor_IDToModel("docTemplate", FsscExpenseShareCategory.class));
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
     * 分摊时间
     */
    public String getFdOperateDate() {
        return this.fdOperateDate;
    }

    /**
     * 分摊时间
     */
    public void setFdOperateDate(String fdOperateDate) {
        this.fdOperateDate = fdOperateDate;
    }

    /**
     * 分摊说明
     */
    public String getFdDescription() {
        return this.fdDescription;
    }

    /**
     * 分摊说明
     */
    public void setFdDescription(String fdDescription) {
        this.fdDescription = fdDescription;
    }


    public String getFdNumber() {
		return fdNumber;
	}

	public void setFdNumber(String fdNumber) {
		this.fdNumber = fdNumber;
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
     * 分摊人
     */
    public String getFdOperatorId() {
        return this.fdOperatorId;
    }

    /**
     * 分摊人
     */
    public void setFdOperatorId(String fdOperatorId) {
        this.fdOperatorId = fdOperatorId;
    }

    /**
     * 分摊人
     */
    public String getFdOperatorName() {
        return this.fdOperatorName;
    }

    /**
     * 分摊人
     */
    public void setFdOperatorName(String fdOperatorName) {
        this.fdOperatorName = fdOperatorName;
    }

    /**
     * 分摊人部门
     */
    public String getFdOperatorDeptId() {
        return this.fdOperatorDeptId;
    }

    /**
     * 分摊人部门
     */
    public void setFdOperatorDeptId(String fdOperatorDeptId) {
        this.fdOperatorDeptId = fdOperatorDeptId;
    }

    /**
     * 分摊人部门
     */
    public String getFdOperatorDeptName() {
        return this.fdOperatorDeptName;
    }

    /**
     * 分摊人部门
     */
    public void setFdOperatorDeptName(String fdOperatorDeptName) {
        this.fdOperatorDeptName = fdOperatorDeptName;
    }

    /**
     * 关联单据id
     * @return
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 关联单据id
     * @param fdModelId
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 关联单据
     * @return
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 关联单据
     * @param fdModelName
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
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
     * 分摊明细
     */
    public AutoArrayList getFdDetailList_Form() {
        return this.fdDetailList_Form;
    }

    /**
     * 分摊明细
     */
    public void setFdDetailList_Form(AutoArrayList fdDetailList_Form) {
        this.fdDetailList_Form = fdDetailList_Form;
    }

    /**
     * 分摊明细
     */
    public String getFdDetailList_Flag() {
        return this.fdDetailList_Flag;
    }

    /**
     * 分摊明细
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
