package com.landray.kmss.km.archives.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.archives.model.KmArchivesAppraise;
import com.landray.kmss.km.archives.model.KmArchivesAppraiseTemplate;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 档案鉴定
  */
public class KmArchivesAppraiseForm
		extends ExtendAuthForm implements ISysLbpmMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;
	// 鉴定时间
    private String docCreateTime;
	// 档案ID
	private String fdArchivesId;
	// 档案名称
    private String fdArchivesName;
	// 档案编号
    private String fdArchivesNumber;
	// 原档案有效期
    private String fdOriginalDate;
	// 鉴定后有效期
    private String fdAfterAppraiseDate;
	// 鉴定意见
    private String fdAppraiseIdea;
	// 鉴定人ID
    private String docCreatorId;
	// 鉴定人名称
    private String docCreatorName;

	private String docTemplateId;

	private String docTemplateName;

	private String docDeptId;

	private String docDeptName;

	private AutoArrayList fdAppraiseDetail_Form = new AutoArrayList(
			KmArchivesAppraiseDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docCreateTime = null;
		fdArchivesId = null;
        fdArchivesName = null;
        fdArchivesNumber = null;
        fdOriginalDate = null;
        fdAfterAppraiseDate = null;
        fdAppraiseIdea = null;
        docCreatorId = null;
        docCreatorName = null;
		docTemplateId = null;
		docTemplateName = null;
		docDeptId = null;
		docDeptName = null;
		fdAppraiseDetail_Form = new AutoArrayList(
				KmArchivesAppraiseDetailsForm.class);
        super.reset(mapping, request);
    }

	public AutoArrayList getFdAppraiseDetail_Form() {
		return fdAppraiseDetail_Form;
	}

	public void setFdAppraiseDetail_Form(AutoArrayList fdAppraiseDetail_Form) {
		this.fdAppraiseDetail_Form = fdAppraiseDetail_Form;
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

	@Override
    public Class<KmArchivesAppraise> getModelClass() {
        return KmArchivesAppraise.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
			toModelPropertyMap.put("docTemplateId", new FormConvertor_IDToModel(
					"docTemplate", KmArchivesAppraiseTemplate.class));
            toModelPropertyMap.put("fdAfterAppraiseDate", new FormConvertor_Common("fdAfterAppraiseDate").setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("docDeptId", new FormConvertor_IDToModel(
					"docDept", SysOrgElement.class));
			toModelPropertyMap.put("fdAppraiseDetail_Form",
					new FormConvertor_FormListToModelList("fdAppraiseDetails",
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

    /**
     * 鉴定时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 鉴定时间
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
     * 原档案有效期
     */
    public String getFdOriginalDate() {
        return this.fdOriginalDate;
    }

    /**
     * 原档案有效期
     */
    public void setFdOriginalDate(String fdOriginalDate) {
        this.fdOriginalDate = fdOriginalDate;
    }

    /**
     * 鉴定后有效期
     */
    public String getFdAfterAppraiseDate() {
        return this.fdAfterAppraiseDate;
    }

    /**
     * 鉴定后有效期
     */
    public void setFdAfterAppraiseDate(String fdAfterAppraiseDate) {
        this.fdAfterAppraiseDate = fdAfterAppraiseDate;
    }

    /**
     * 鉴定意见
     */
    public String getFdAppraiseIdea() {
        return this.fdAppraiseIdea;
    }

    /**
     * 鉴定意见
     */
    public void setFdAppraiseIdea(String fdAppraiseIdea) {
        this.fdAppraiseIdea = fdAppraiseIdea;
    }

    /**
     * 鉴定人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 鉴定人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 鉴定人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 鉴定人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

	/**
	 * 新建时存放需要鉴定的档案列表
	 */
	private AutoArrayList appraiseList = new AutoArrayList(
			KmArchivesAppraiseForm.class);

	public AutoArrayList getAppraiseList() {
		return appraiseList;
	}

	public void setAppraiseList(AutoArrayList appraiseList) {
		this.appraiseList = appraiseList;
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
