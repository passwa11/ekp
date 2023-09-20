package com.landray.kmss.km.archives.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.archives.model.KmArchivesCategory;
import com.landray.kmss.km.archives.model.KmArchivesDense;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.edition.forms.SysEditionMainForm;
import com.landray.kmss.sys.edition.interfaces.ISysEditionMainForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.metadata.forms.ExtendDataFormInfo;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.readlog.forms.ReadLogForm;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogForm;
import com.landray.kmss.sys.recycle.forms.ISysRecycleModelForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.sys.tag.forms.SysTagMainForm;
import com.landray.kmss.sys.tag.interfaces.ISysTagMainForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
  * 档案信息
  */
public class KmArchivesMainForm extends ExtendAuthForm
		implements ISysLbpmMainForm, IAttachmentForm, ISysReadLogForm,
		ISysTagMainForm, IExtendDataForm, ISysEditionMainForm,ISysRecycleModelForm {

    private static FormToModelPropertyMap toModelPropertyMap;

	/*
	 * 外模块名称
	 */
	protected String fdModelName = null;

	/*
	 * 外模块ID
	 */
	protected String fdModelId = null;

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	private String docCreateTime;

    private String docSubject;

    private String docNumber;

    private String docReadCount;

    private String fdValidityDate;

    private String fdFileDate;

    private String fdDestroyed;

    private String fdVolumeYear;

    private String fdRemarks;

    private String docCreatorId;

    private String docCreatorName;

    private String docTemplateId;

    private String docTemplateName;
    
    private String fdDenseLevel;
    
    private String fdDenseId;

    private String fdDenseName;

	private String fdPeriod;

	private String fdUnit;

	private String fdLibrary;

    private String fdStorekeeperId;

    private String fdStorekeeperName;

    private LbpmProcessForm sysWfBusinessForm = new LbpmProcessForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    private ReadLogForm readLogForm = new ReadLogForm();

    SysTagMainForm sysTagMainForm = new SysTagMainForm();

	/* 文件 */
	protected FormFile file = null;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docCreateTime = null;
        docSubject = null;
        docNumber = null;
        docReadCount = null;
        fdValidityDate = null;
        fdFileDate = null;
        fdDestroyed = null;
        fdVolumeYear = null;
        fdRemarks = null;
        docCreatorId = null;
        docCreatorName = null;
        docTemplateId = null;
        docTemplateName = null;
        fdDenseId = null;
        fdDenseName = null;
		fdPeriod = null;
		fdUnit = null;
		fdDenseLevel = null;
		fdLibrary = null;
        fdStorekeeperId = null;
        fdStorekeeperName = null;
        sysWfBusinessForm = new LbpmProcessForm();
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
		authFileReaderIds = null;
		authFileReaderNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<KmArchivesMain> getModelClass() {
        return KmArchivesMain.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docNumber");
            toModelPropertyMap.addNoConvertProperty("docStatus");
            toModelPropertyMap.addNoConvertProperty("docReadCount");
            toModelPropertyMap.put("fdValidityDate", new FormConvertor_Common("fdValidityDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdFileDate", new FormConvertor_Common("fdFileDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdVolumeYear", new FormConvertor_Common("fdVolumeYear").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("docTemplateId", new FormConvertor_IDToModel("docTemplate", KmArchivesCategory.class));
            toModelPropertyMap.put("fdDenseId", new FormConvertor_IDToModel("fdDense", KmArchivesDense.class));
			toModelPropertyMap.put("fdStorekeeperId",
					new FormConvertor_IDToModel("fdStorekeeper", SysOrgElement.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
            toModelPropertyMap.put("authAttCopyIds", new FormConvertor_IDsToModelList("authAttCopys", SysOrgElement.class));
            toModelPropertyMap.put("authAttDownloadIds", new FormConvertor_IDsToModelList("authAttDownloads", SysOrgElement.class));
            toModelPropertyMap.put("authAttPrintIds", new FormConvertor_IDsToModelList("authAttPrints", SysOrgElement.class));
			toModelPropertyMap.put("authFileReaderIds",
					new FormConvertor_IDsToModelList("authFileReaders",
							SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

	// 文件级可阅读者
	private String authFileReaderIds;

	private String authFileReaderNames;
	
	public String getAuthFileReaderIds() {
		return authFileReaderIds;
	}

	public void setAuthFileReaderIds(String authFileReaderIds) {
		this.authFileReaderIds = authFileReaderIds;
	}

	public String getAuthFileReaderNames() {
		return authFileReaderNames;
	}

	public void setAuthFileReaderNames(String authFileReaderNames) {
		this.authFileReaderNames = authFileReaderNames;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
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
     * 档案名称
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 档案名称
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * 档案编号
     */
    public String getDocNumber() {
        return this.docNumber;
    }

    /**
     * 档案编号
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    /**
     * 阅读次数
     */
    public String getDocReadCount() {
        return this.docReadCount;
    }

    /**
     * 阅读次数
     */
    public void setDocReadCount(String docReadCount) {
        this.docReadCount = docReadCount;
    }

    /**
     * 档案有效期
     */
    public String getFdValidityDate() {
        return this.fdValidityDate;
    }

    /**
     * 档案有效期
     */
    public void setFdValidityDate(String fdValidityDate) {
        this.fdValidityDate = fdValidityDate;
    }

    /**
     * 归档日期
     */
    public String getFdFileDate() {
        return this.fdFileDate;
    }

    /**
     * 归档日期
     */
    public void setFdFileDate(String fdFileDate) {
        this.fdFileDate = fdFileDate;
    }

    /**
     * 是否销毁
     */
    public String getFdDestroyed() {
        return this.fdDestroyed;
    }

    /**
     * 是否销毁
     */
    public void setFdDestroyed(String fdDestroyed) {
        this.fdDestroyed = fdDestroyed;
    }

    /**
     * 组卷年度
     */
    public String getFdVolumeYear() {
        return this.fdVolumeYear;
    }

    /**
     * 组卷年度
     */
    public void setFdVolumeYear(String fdVolumeYear) {
        this.fdVolumeYear = fdVolumeYear;
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
     * 归档人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 归档人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 归档人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 归档人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 所属分类
     */
    public String getDocTemplateId() {
        return this.docTemplateId;
    }

    /**
     * 所属分类
     */
    public void setDocTemplateId(String docTemplateId) {
        this.docTemplateId = docTemplateId;
    }

    /**
     * 所属分类
     */
    public String getDocTemplateName() {
        return this.docTemplateName;
    }

    /**
     * 所属分类
     */
    public void setDocTemplateName(String docTemplateName) {
        this.docTemplateName = docTemplateName;
    }
    
	/**
	 * 密级程度
	 */
    public String getFdDenseLevel() {
		return fdDenseLevel;
	}

    /**
	 * 密级程度
	 */
	public String getFdDenseId() {
		return fdDenseId;
	}

	 /**
	  * 密级程度
	  */
	public void setFdDenseId(String fdDenseId) {
		this.fdDenseId = fdDenseId;
	}

	 /**
	  * 密级程度
	  */
	public String getFdDenseName() {
		return fdDenseName;
	}
	
	 /**
	  * 密级程度
	  */
	public void setFdDenseName(String fdDenseName) {
		this.fdDenseName = fdDenseName;
	}
	
	/**
     * 保管期限
     */
	public String getFdPeriod() {
		return this.fdPeriod;
    }
	
	/**
     * 保管期限
     */
	public void setFdPeriod(String fdPeriod) {
		this.fdPeriod = fdPeriod;
    }

    /**
     * 保管单位
     */
	public String getFdUnit() {
		return this.fdUnit;
    }

    /**
     * 保管单位
     */
	public void setFdUnit(String fdUnit) {
		this.fdUnit = fdUnit;
    }

    /**
     * 所属卷库
     */
	public String getFdLibrary() {
		return this.fdLibrary;
    }

    /**
     * 所属卷库
     */
	public void setFdLibrary(String fdLibrary) {
		this.fdLibrary = fdLibrary;
    }

    /**
     * 保管员
     */
    public String getFdStorekeeperId() {
        return this.fdStorekeeperId;
    }

    /**
     * 保管员
     */
    public void setFdStorekeeperId(String fdStorekeeperId) {
        this.fdStorekeeperId = fdStorekeeperId;
    }

    /**
     * 保管员
     */
    public String getFdStorekeeperName() {
        return this.fdStorekeeperName;
    }

    /**
     * 保管员
     */
    public void setFdStorekeeperName(String fdStorekeeperName) {
        this.fdStorekeeperName = fdStorekeeperName;
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
    public ReadLogForm getReadLogForm() {
        return readLogForm;
    }

    public void setReadLogForm(ReadLogForm readLogForm) {
        this.readLogForm = readLogForm;
    }

    @Override
    public SysTagMainForm getSysTagMainForm() {
        return sysTagMainForm;
    }

	/* 扩展属性 */
	private ExtendDataFormInfo extendDataFormInfo = new ExtendDataFormInfo();

	@Override
    public ExtendDataFormInfo getExtendDataFormInfo() {
		return extendDataFormInfo;
	}

	public void setExtendDataFormInfo(ExtendDataFormInfo extendDataFormInfo) {
		this.extendDataFormInfo = extendDataFormInfo;
	}

	private String extendDataXML;

	public String getExtendDataXML() {
		return extendDataXML;
	}

	public void setExtendDataXML(String extendDataXML) {
		this.extendDataXML = extendDataXML;
	}

	private String extendFilePath;

	public String getExtendFilePath() {
		return extendFilePath;
	}

	public void setExtendFilePath(String extendFilePath) {
		this.extendFilePath = extendFilePath;
	}

	/*
	 * 版本机制
	 */
	protected SysEditionMainForm editionForm = new SysEditionMainForm();

	@Override
    public SysEditionMainForm getEditionForm() {
		return editionForm;
	}

	public void setEditionForm(SysEditionMainForm editionForm) {
		this.editionForm = editionForm;
	}
	/*软删除部署*/
	private Integer docDeleteFlag;

	@Override
    public Integer getDocDeleteFlag() {
		return docDeleteFlag;
	}

	@Override
    public void setDocDeleteFlag(Integer docDeleteFlag) {
		this.docDeleteFlag = docDeleteFlag;
	}

    /**
     * 其它模块归档时下载原文档状态
     */
    private String fdPrintState;

    public String getFdPrintState() {
        return fdPrintState;
    }

    public void setFdPrintState(String fdPrintState) {
        this.fdPrintState = fdPrintState;
    }
}
