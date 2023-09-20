package com.landray.kmss.km.archives.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.archives.forms.KmArchivesDestroyForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModel;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
  * 档案销毁
  */
public class KmArchivesDestroy extends ExtendAuthModel
		implements ISysLbpmMainModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private String fdArchivesId;

    private String fdArchivesName;

    private String fdArchivesNumber;

    private String fdCategoryName;

    private Date fdReturnDate;

    private String fdReturnPerson;

    private String fdDestroyIdea;

    private SysOrgPerson docCreator;

	private KmArchivesDestroyTemplate docTemplate;

	private List<KmArchivesDestroyDetails> fdDestroyDetails;

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
    public Class<KmArchivesDestroyForm> getFormClass() {
        return KmArchivesDestroyForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("fdArchivesId");
            toFormPropertyMap.put("fdReturnDate", new ModelConvertor_Common("fdReturnDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docTemplate.fdName", "docTemplateName");
			toFormPropertyMap.put("docTemplate.fdId", "docTemplateId");
			toFormPropertyMap.put("docDept.fdName", "docDeptName");
			toFormPropertyMap.put("docDept.fdId", "docDeptId");
			toFormPropertyMap.put("fdDestroyDetails",
					new ModelConvertor_ModelListToFormList(
							"fdDestroyDetail_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 销毁时间
     */
    @Override
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 销毁时间
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
    public Date getFdReturnDate() {
        return this.fdReturnDate;
    }

    /**
     * 归档日期
     */
    public void setFdReturnDate(Date fdReturnDate) {
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
    @Override
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 销毁人
     */
    @Override
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

	public KmArchivesDestroyTemplate getDocTemplate() {
		return docTemplate;
	}

	public void setDocTemplate(KmArchivesDestroyTemplate docTemplate) {
		this.docTemplate = docTemplate;
	}

	public List<KmArchivesDestroyDetails> getFdDestroyDetails() {
		return fdDestroyDetails;
	}

	public void
			setFdDestroyDetails(
					List<KmArchivesDestroyDetails> fdDestroyDetails) {
		this.fdDestroyDetails = fdDestroyDetails;
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
