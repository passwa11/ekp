package com.landray.kmss.km.archives.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.archives.model.KmArchivesDestroy;
import com.landray.kmss.km.archives.model.KmArchivesDestroyTemplate;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 档案销毁
  */
public class KmArchivesDestroyForm extends ExtendAuthForm
		implements ISysLbpmMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;
	// 销毁时间
    private String docCreateTime;
	// 档案ID
	private String fdArchivesId;
	// 档案名称
    private String fdArchivesName;
	// 档案编号
    private String fdArchivesNumber;
	// 所属分类名称
    private String fdCategoryName;
	// 归档时间
    private String fdReturnDate;
	// 归档人
    private String fdReturnPerson;
	// 销毁意见
    private String fdDestroyIdea;
	// 销毁人ID
    private String docCreatorId;
	// 销毁人姓名
    private String docCreatorName;

	private String docTemplateId;

	private String docTemplateName;

	private String docDeptId;

	private String docDeptName;

	private AutoArrayList fdDestroyDetail_Form = new AutoArrayList(
			KmArchivesDestroyDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docCreateTime = null;
		fdArchivesId = null;
        fdArchivesName = null;
        fdArchivesNumber = null;
        fdCategoryName = null;
        fdReturnDate = null;
        fdReturnPerson = null;
        fdDestroyIdea = null;
        docCreatorId = null;
        docCreatorName = null;
		docTemplateId = null;
		docTemplateName = null;
		docDeptId = null;
		docDeptName = null;
		fdDestroyDetail_Form = new AutoArrayList(
				KmArchivesDestroyDetailsForm.class);
        super.reset(mapping, request);
    }

    @Override
    public Class<KmArchivesDestroy> getModelClass() {
        return KmArchivesDestroy.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
			toModelPropertyMap.put("docTemplateId", new FormConvertor_IDToModel(
					"docTemplate", KmArchivesDestroyTemplate.class));
			toModelPropertyMap.put("docDeptId", new FormConvertor_IDToModel(
					"docDept", SysOrgElement.class));
			toModelPropertyMap.put("fdDestroyDetail_Form",
					new FormConvertor_FormListToModelList("fdDestroyDetails",
							"docMain"));
        }
        return toModelPropertyMap;
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

	public String getDocTemplateId() {
		return docTemplateId;
	}

	public void setDocTemplateId(String docTemplateId) {
		this.docTemplateId = docTemplateId;
	}

	public String getDocTemplateName() {
		return docTemplateName;
	}

	public void setDocTemplateName(String docTemplateName) {
		this.docTemplateName = docTemplateName;
	}

	public AutoArrayList getFdDestroyDetail_Form() {
		return fdDestroyDetail_Form;
	}

	public void setFdDestroyDetail_Form(AutoArrayList fdDestroyDetail_Form) {
		this.fdDestroyDetail_Form = fdDestroyDetail_Form;
	}

	/**
	 * 销毁时间
	 */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 销毁时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

	/**
	 * 档案ID
	 */
	public String getFdArchivesId() {
		return this.fdArchivesId;
	}

    /**
	 * 档案ID
	 */
	public void setFdArchivesId(String fdArchivesId) {
		this.fdArchivesId = fdArchivesId;
	}

	/**
	 * 档案名称
	 */
    public String getFdArchivesName() {
        return this.fdArchivesName;
    }

    /**
     * 档案名称
     */
    public void setFdArchivesName(String fdArchivesName) {
        this.fdArchivesName = fdArchivesName;
    }

    /**
     * 档案编号
     */
    public String getFdArchivesNumber() {
        return this.fdArchivesNumber;
    }

    /**
     * 档案编号
     */
    public void setFdArchivesNumber(String fdArchivesNumber) {
        this.fdArchivesNumber = fdArchivesNumber;
    }

    /**
     * 所属分类
     */
    public String getFdCategoryName() {
        return this.fdCategoryName;
    }

    /**
     * 所属分类
     */
    public void setFdCategoryName(String fdCategoryName) {
        this.fdCategoryName = fdCategoryName;
    }

    /**
     * 归档日期
     */
    public String getFdReturnDate() {
        return this.fdReturnDate;
    }

    /**
     * 归档日期
     */
    public void setFdReturnDate(String fdReturnDate) {
        this.fdReturnDate = fdReturnDate;
    }

    /**
     * 归档人
     */
    public String getFdReturnPerson() {
        return this.fdReturnPerson;
    }

    /**
     * 归档人
     */
    public void setFdReturnPerson(String fdReturnPerson) {
        this.fdReturnPerson = fdReturnPerson;
    }

    /**
     * 销毁意见
     */
    public String getFdDestroyIdea() {
        return this.fdDestroyIdea;
    }

    /**
     * 销毁意见
     */
    public void setFdDestroyIdea(String fdDestroyIdea) {
        this.fdDestroyIdea = fdDestroyIdea;
    }

    /**
     * 销毁人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 销毁人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 销毁人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 销毁人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

	/**
	 * 新建时存放需要销毁的档案列表
	 */
	private AutoArrayList destroyList = new AutoArrayList(
			KmArchivesDestroyForm.class);

	public AutoArrayList getDestroyList() {
		return destroyList;
	}

	public void setDestroyList(AutoArrayList destroyList) {
		this.destroyList = destroyList;
	}

	private String docSubject;

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

	/*
	 * 文档发布状态
	 */
	protected String docStatus = null;

	@Override
    public String getDocStatus() {
		return docStatus;
	}

	@Override
    public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	private AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);

	private LbpmProcessForm sysWfBusinessForm = new LbpmProcessForm();

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	@Override
    public LbpmProcessForm getSysWfBusinessForm() {
		return sysWfBusinessForm;
	}

	@Override
    public String getAuthReaderNoteFlag() {
		return "2";
	}
}
