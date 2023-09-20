package com.landray.kmss.km.archives.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.archives.model.KmArchivesBorrow;
import com.landray.kmss.km.archives.model.KmArchivesTemplate;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.recycle.forms.ISysRecycleModelForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 档案借阅申请
  */
public class KmArchivesBorrowForm extends ExtendAuthForm implements IAttachmentForm, ISysLbpmMainForm,ISysRecycleModelForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String docSubject;

    private String fdBorrowDate;

    private String fdBorrowReason;

    private String fdRemarks;

    private String docCreatorId;

    private String docCreatorName;

    private String docTemplateId;

    private String docTemplateName;

    private String docDeptId;

    private String docDeptName;

    private String fdBorrowerId;

    private String fdBorrowerName;
    
	private AutoArrayList fdBorrowDetail_Form = new AutoArrayList(
			KmArchivesDetailsForm.class);

	private String fdBorrowDetail_Flag;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    private LbpmProcessForm sysWfBusinessForm = new LbpmProcessForm();

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        docSubject = null;
        fdBorrowDate = null;
        fdBorrowReason = null;
        fdRemarks = null;
        docCreatorId = null;
        docCreatorName = null;
        docTemplateId = null;
        docTemplateName = null;
        docDeptId = null;
        docDeptName = null;
        fdBorrowerId = null;
        fdBorrowerName = null;
		fdBorrowDetail_Form = new AutoArrayList(KmArchivesDetailsForm.class);
		fdBorrowDetail_Flag = null;
        sysWfBusinessForm = new LbpmProcessForm();
        super.reset(mapping, request);
    }

    @Override
    public Class<KmArchivesBorrow> getModelClass() {
        return KmArchivesBorrow.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docStatus");
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdBorrowDate", new FormConvertor_Common("fdBorrowDate").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.addNoConvertProperty("fdRemarks");
            toModelPropertyMap.put("docTemplateId", new FormConvertor_IDToModel("docTemplate", KmArchivesTemplate.class));
            toModelPropertyMap.put("docDeptId", new FormConvertor_IDToModel("docDept", SysOrgElement.class));
			toModelPropertyMap.put("fdBorrowerId", new FormConvertor_IDToModel(
					"fdBorrower", SysOrgPerson.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
			toModelPropertyMap.put("fdBorrowDetail_Form",
					new FormConvertor_FormListToModelList("fdBorrowDetails",
							"docMain", "fdBorrowDetail_Flag"));
            toModelPropertyMap.put("authAttCopyIds", new FormConvertor_IDsToModelList("authAttCopys", SysOrgElement.class));
            toModelPropertyMap.put("authAttDownloadIds", new FormConvertor_IDsToModelList("authAttDownloads", SysOrgElement.class));
            toModelPropertyMap.put("authAttPrintIds", new FormConvertor_IDsToModelList("authAttPrints", SysOrgElement.class));
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
     * 借阅时间
     */
    public String getFdBorrowDate() {
        return this.fdBorrowDate;
    }

    /**
     * 借阅时间
     */
    public void setFdBorrowDate(String fdBorrowDate) {
        this.fdBorrowDate = fdBorrowDate;
    }

    /**
     * 借阅事由
     */
    public String getFdBorrowReason() {
        return this.fdBorrowReason;
    }

    /**
     * 借阅事由
     */
    public void setFdBorrowReason(String fdBorrowReason) {
        this.fdBorrowReason = fdBorrowReason;
    }

    /**
     * 备注
     */
    public String getFdRemarks() {
        return this.fdRemarks;
    }

    /**
     * 备注
     */
    public void setFdRemarks(String fdRemarks) {
        this.fdRemarks = fdRemarks;
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
     * 流程
     */
    public String getDocTemplateId() {
        return this.docTemplateId;
    }

    /**
     * 流程
     */
    public void setDocTemplateId(String docTemplateId) {
        this.docTemplateId = docTemplateId;
    }

    /**
     * 流程
     */
    public String getDocTemplateName() {
        return this.docTemplateName;
    }

    /**
     * 流程
     */
    public void setDocTemplateName(String docTemplateName) {
        this.docTemplateName = docTemplateName;
    }

    /**
     * 所属部门
     */
    public String getDocDeptId() {
        return this.docDeptId;
    }

    /**
     * 所属部门
     */
    public void setDocDeptId(String docDeptId) {
        this.docDeptId = docDeptId;
    }

    /**
     * 所属部门
     */
    public String getDocDeptName() {
        return this.docDeptName;
    }

    /**
     * 所属部门
     */
    public void setDocDeptName(String docDeptName) {
        this.docDeptName = docDeptName;
    }

    /**
     * 借阅人
     */
    public String getFdBorrowerId() {
        return this.fdBorrowerId;
    }

    /**
     * 借阅人
     */
    public void setFdBorrowerId(String fdBorrowerId) {
        this.fdBorrowerId = fdBorrowerId;
    }

    /**
     * 借阅人
     */
    public String getFdBorrowerName() {
        return this.fdBorrowerName;
    }

    /**
     * 借阅人
     */
    public void setFdBorrowerName(String fdBorrowerName) {
        this.fdBorrowerName = fdBorrowerName;
    }

	/**
	 * 档案借用明细
	 */
	public AutoArrayList getFdBorrowDetail_Form() {
		return this.fdBorrowDetail_Form;
	}

	/**
	 * 档案借用明细
	 */
	public void setFdBorrowDetail_Form(AutoArrayList fdBorrowDetail_Form) {
		this.fdBorrowDetail_Form = fdBorrowDetail_Form;
	}

	/**
	 * 档案借用明细
	 */
	public String getFdBorrowDetail_Flag() {
		return this.fdBorrowDetail_Flag;
	}

	/**
	 * 档案借用明细
	 */
	public void setFdBorrowDetail_Flag(String fdBorrowDetail_Flag) {
		this.fdBorrowDetail_Flag = fdBorrowDetail_Flag;
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
    
    /*软删除配置*/
	private Integer docDeleteFlag;

	@Override
    public Integer getDocDeleteFlag() {
		return docDeleteFlag;
	}

	@Override
    public void setDocDeleteFlag(Integer docDeleteFlag) {
		this.docDeleteFlag = docDeleteFlag;
	}
}
