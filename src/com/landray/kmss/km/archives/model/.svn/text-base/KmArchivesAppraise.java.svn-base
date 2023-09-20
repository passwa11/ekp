package com.landray.kmss.km.archives.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.archives.forms.KmArchivesAppraiseForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModel;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
  * 档案鉴定
  */
public class KmArchivesAppraise extends ExtendAuthModel
		implements ISysLbpmMainModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private String fdArchivesId;

    private String fdArchivesName;

    private String fdArchivesNumber;

    private Date fdOriginalDate;

    private Date fdAfterAppraiseDate;

    private String fdAppraiseIdea;

    private SysOrgPerson docCreator;

	private KmArchivesAppraiseTemplate docTemplate;

	private List<KmArchivesAppraiseDetails> fdAppraiseDetails;

	private SysOrgElement docDept;

	/**
	 * 所属部门
	 */
	public SysOrgElement getDocDept() {
		return this.docDept;
	}

	/**
	 * 所属部门
	 */
	public void setDocDept(SysOrgElement docDept) {
		this.docDept = docDept;
	}

	private String fdOriginId;// 流程文档ID

	public String getFdOriginId() {
		return fdOriginId;
	}

	public void setFdOriginId(String fdOriginId) {
		this.fdOriginId = fdOriginId;
	}

    @Override
    public Class<KmArchivesAppraiseForm> getFormClass() {
        return KmArchivesAppraiseForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("fdArchivesId");
			toFormPropertyMap.put("docTemplate.fdName", "docTemplateName");
			toFormPropertyMap.put("docTemplate.fdId", "docTemplateId");
            toFormPropertyMap.put("fdOriginalDate", new ModelConvertor_Common("fdOriginalDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdAfterAppraiseDate", new ModelConvertor_Common("fdAfterAppraiseDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docDept.fdName", "docDeptName");
			toFormPropertyMap.put("docDept.fdId", "docDeptId");
			toFormPropertyMap.put("fdAppraiseDetails",
					new ModelConvertor_ModelListToFormList(
							"fdAppraiseDetail_Form"));
        }
        return toFormPropertyMap;
    }

	public List<KmArchivesAppraiseDetails> getFdAppraiseDetails() {
		return fdAppraiseDetails;
	}

	public void setFdAppraiseDetails(
			List<KmArchivesAppraiseDetails> fdAppraiseDetails) {
		this.fdAppraiseDetails = fdAppraiseDetails;
	}

	public KmArchivesAppraiseTemplate getDocTemplate() {
		return docTemplate;
	}

	public void setDocTemplate(KmArchivesAppraiseTemplate docTemplate) {
		this.docTemplate = docTemplate;
	}

	@Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 鉴定时间
     */
    @Override
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 鉴定时间
     */
    @Override
    public void setDocCreateTime(Date docCreateTime) {
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
    public Date getFdOriginalDate() {
        return this.fdOriginalDate;
    }

    /**
     * 原档案有效期
     */
    public void setFdOriginalDate(Date fdOriginalDate) {
        this.fdOriginalDate = fdOriginalDate;
    }

    /**
     * 鉴定后有效期
     */
    public Date getFdAfterAppraiseDate() {
        return this.fdAfterAppraiseDate;
    }

    /**
     * 鉴定后有效期
     */
    public void setFdAfterAppraiseDate(Date fdAfterAppraiseDate) {
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
    @Override
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 鉴定人
     */
    @Override
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

	private String docStatus;

	@Override
    public String getDocStatus() {
		return docStatus;
	}

	@Override
    public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	private String docSubject;
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

	private AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);

	private LbpmProcessForm sysWfBusinessModel = new LbpmProcessForm();

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	@Override
    public LbpmProcessForm getSysWfBusinessModel() {
		return sysWfBusinessModel;
	}

	@Override
	public Boolean getAuthReaderFlag() {
		if (authReaderFlag == null) {
			return new Boolean(false);
		}
		return authReaderFlag;
	}

	@Override
	protected void recalculateReaderField() {
		if (docStatus != null) {
			super.recalculateReaderField();
		}
	}

	@Override
	protected void recalculateEditorField() {
		if (docStatus != null) {
			super.recalculateEditorField();
		}
	}
}
