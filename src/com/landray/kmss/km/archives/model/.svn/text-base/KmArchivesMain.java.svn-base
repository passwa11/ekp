package com.landray.kmss.km.archives.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.archives.forms.KmArchivesMainForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.edition.interfaces.ISysEditionAutoDeleteModel;
import com.landray.kmss.sys.edition.interfaces.ISysEditionMainModel;
import com.landray.kmss.sys.ftsearch.interfaces.INeedIndex;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModel;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.model.ExtendDataModelInfo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogAutoSaveModel;
import com.landray.kmss.sys.recycle.model.ISysRecycleModel;
import com.landray.kmss.sys.recycle.model.SysRecycleConstant;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.sys.tag.interfaces.ISysTagMainModel;
import com.landray.kmss.sys.tag.model.SysTagMain;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

/**
  * 档案信息
  */
public class KmArchivesMain extends ExtendAuthModel
		implements ISysLbpmMainModel, IAttachment, ISysReadLogAutoSaveModel,
		ISysTagMainModel, IExtendDataModel, ISysEditionAutoDeleteModel,ISysRecycleModel,INeedIndex {

    private static ModelToFormPropertyMap toFormPropertyMap;

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

	private String docSubject;

    private String docNumber;

    private Long docReadCount;

    private Date fdValidityDate;

    private Date fdFileDate;

	// 是否预归档状态
	private Boolean fdIsPreFile;

	public Boolean getFdIsPreFile() {
		return fdIsPreFile;
	}

	public void setFdIsPreFile(Boolean fdIsPreFile) {
		this.fdIsPreFile = fdIsPreFile;
	}

	private Boolean fdDestroyed;
    
    private int fdSaveOldFile;
    
    private int fdPdfAlive;

	private String fdVolumeYear;

    private String fdRemarks;

    private KmArchivesCategory docTemplate;

	private String fdPeriod;

	private String fdUnit;

	private String fdLibrary;

	private SysOrgElement fdStorekeeper;

    // 旧数据密级程度的字段
    private String fdDenseLevel;
    
	private KmArchivesDense fdDense;

    private LbpmProcessForm sysWfBusinessModel = new LbpmProcessForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    private SysTagMain sysTagMain = null;

    @Override
    public Class<KmArchivesMainForm> getFormClass() {
        return KmArchivesMainForm.class;
    }

	@Override
    public void recalculateFields() {
		super.recalculateFields();
		String tempStatus = getDocStatus();
		if (StringUtil.isNotNull(tempStatus) && tempStatus.charAt(0) >= '3') {
			List<SysOrgElement> tmpList = new ArrayList<SysOrgElement>();
			if (this.getFdStorekeeper() != null) {
				tmpList.add(this.getFdStorekeeper());
				ArrayUtil.concatTwoList(tmpList, authAllReaders);
			}
		}
	}

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdValidityDate", new ModelConvertor_Common("fdValidityDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdFileDate", new ModelConvertor_Common("fdFileDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdVolumeYear", new ModelConvertor_Common("fdVolumeYear").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.addNoConvertProperty("authEditorFlag");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docTemplate.fdName", "docTemplateName");
            toFormPropertyMap.put("docTemplate.fdId", "docTemplateId");
            toFormPropertyMap.put("fdDense.fdName", "fdDenseName");
            toFormPropertyMap.put("fdDense.fdId", "fdDenseId");
            toFormPropertyMap.put("fdStorekeeper.fdName", "fdStorekeeperName");
            toFormPropertyMap.put("fdStorekeeper.fdId", "fdStorekeeperId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttCopys", new ModelConvertor_ModelListToString("authAttCopyIds:authAttCopyNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttDownloads", new ModelConvertor_ModelListToString("authAttDownloadIds:authAttDownloadNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttPrints", new ModelConvertor_ModelListToString("authAttPrintIds:authAttPrintNames", "fdId:fdName"));
			toFormPropertyMap.put("authFileReaders",
					new ModelConvertor_ModelListToString(
							"authFileReaderIds:authFileReaderNames",
							"fdId:fdName"));
        }
        return toFormPropertyMap;
    }

	/**
	 * 文件级可阅读者
	 */
	private List authFileReaders = new ArrayList();
	/**
	 * 文件级可阅读者
	 */
	public List getAuthFileReaders() {
		return authFileReaders;
	}
	/**
	 * 文件级可阅读者
	 */
	public void setAuthFileReaders(List authFileReaders) {
		this.authFileReaders = authFileReaders;
	}

	/**
	 * 档案名称
	 */
    @Override
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
    @Override
    public Long getDocReadCount() {
        return this.docReadCount;
    }

    /**
     * 阅读次数
     */
    @Override
    public void setDocReadCount(Long docReadCount) {
        this.docReadCount = docReadCount;
    }

    /**
     * 档案有效期
     */
    public Date getFdValidityDate() {
        return this.fdValidityDate;
    }

    /**
     * 档案有效期
     */
    public void setFdValidityDate(Date fdValidityDate) {
        this.fdValidityDate = fdValidityDate;
    }

    /**
     * 归档日期
     */
    public Date getFdFileDate() {
        return this.fdFileDate;
    }

    /**
     * 归档日期
     */
    public void setFdFileDate(Date fdFileDate) {
        this.fdFileDate = fdFileDate;
    }

    /**
     * 是否销毁
     */
    public Boolean getFdDestroyed() {
        return this.fdDestroyed;
    }

    /**
     * 是否销毁
     */
    public void setFdDestroyed(Boolean fdDestroyed) {
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
     * 所属分类
     */
    public KmArchivesCategory getDocTemplate() {
        return this.docTemplate;
    }

    /**
     * 所属分类
     */
    public void setDocTemplate(KmArchivesCategory docTemplate) {
        this.docTemplate = docTemplate;
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
	public SysOrgElement getFdStorekeeper() {
        return this.fdStorekeeper;
    }

    /**
     * 保管员
     */
	public void setFdStorekeeper(SysOrgElement fdStorekeeper) {
        this.fdStorekeeper = fdStorekeeper;
    }
    
    /**
     * 密级程度
     */
    public KmArchivesDense getFdDense() {
		return fdDense;
	}

    /**
     * 密级程度
     */
	public void setFdDense(KmArchivesDense fdDense) {
		this.fdDense = fdDense;
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
	public void setFdDenseLevel(String fdDenseLevel) {
		if(this.fdDense != null) {
			this.fdDenseLevel = this.fdDense.getFdName();
		}else {
			this.fdDenseLevel = fdDenseLevel;
		}
	}
	
	@Override
    public LbpmProcessForm getSysWfBusinessModel() {
        return sysWfBusinessModel;
    }

	@Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    @Override
    public SysTagMain getSysTagMain() {
        return this.sysTagMain;
    }

    @Override
    public void setSysTagMain(SysTagMain sysTagMain) {
        this.sysTagMain = sysTagMain;
    }

	// --扩展数据--//
	private ExtendDataModelInfo extendDataModelInfo = new ExtendDataModelInfo(
			this);

	@Override
    public ExtendDataModelInfo getExtendDataModelInfo() {
		return extendDataModelInfo;
	}

	private String extendDataXML;

	@Override
    public String getExtendDataXML() {
		return (String) readLazyField("extendDataXML", extendDataXML);
	}

	@Override
    public void setExtendDataXML(String extendDataXML) {
		this.extendDataXML = (String) writeLazyField("extendDataXML",
				this.extendDataXML, extendDataXML);
	}

	private String extendFilePath;

	@Override
    public String getExtendFilePath() {
		return extendFilePath;
	}

	@Override
    public void setExtendFilePath(String extendFilePath) {
		this.extendFilePath = extendFilePath;
	}
	// --扩展数据结束--//
	// ==== 版本机制（开始） =====
	/*
	 * 是否是新版本
	 */
	protected Boolean docIsNewVersion = new Boolean(true);

	@Override
    public Boolean getDocIsNewVersion() {
		return docIsNewVersion;
	}

	@Override
    public void setDocIsNewVersion(Boolean docIsNewVersion) {
		this.docIsNewVersion = docIsNewVersion;
	}

	/*
	 * 版本锁定
	 */
	protected Boolean docIsLocked = new Boolean(false);

	@Override
    public Boolean getDocIsLocked() {
		return docIsLocked;
	}

	@Override
    public void setDocIsLocked(Boolean docIsLocked) {
		this.docIsLocked = docIsLocked;
	}

	/*
	 * 主版本号
	 */
	protected Long docMainVersion = new Long(1);

	@Override
    public Long getDocMainVersion() {
		return docMainVersion;
	}

	@Override
    public void setDocMainVersion(Long docMainVersion) {
		this.docMainVersion = docMainVersion;
	}

	/*
	 * 辅版本号
	 */
	protected Long docAuxiVersion = new Long(0);

	@Override
    public Long getDocAuxiVersion() {
		return docAuxiVersion;
	}

	@Override
    public void setDocAuxiVersion(Long docAuxiVersion) {
		this.docAuxiVersion = docAuxiVersion;
	}

	/*
	 * 主文档
	 */
	protected ISysEditionMainModel docOriginDoc;

	@Override
    public ISysEditionMainModel getDocOriginDoc() {
		return docOriginDoc;
	}

	@Override
    public void setDocOriginDoc(ISysEditionMainModel docOriginDoc) {
		this.docOriginDoc = docOriginDoc;
	}

	/*
	 * 历史版本
	 */
	protected List docHistoryEditions;

	@Override
    public List getDocHistoryEditions() {
		return docHistoryEditions;
	}

	@Override
    public void setDocHistoryEditions(List docHistoryEditions) {
		this.docHistoryEditions = docHistoryEditions;
	}
	// ==== 版本机制（结束） =====
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

	@Override
	public boolean isNeedIndex() {
		// 销毁也一样不能搜索
		return (fdDestroyed == null || BooleanUtils.isFalse(fdDestroyed))
				&& (docDeleteFlag == null || docDeleteFlag == SysRecycleConstant.OPT_TYPE_RECOVER);
	}

	public int getFdSaveOldFile() {
		return fdSaveOldFile;
	}

	public void setFdSaveOldFile(int fdSaveOldFile) {
		this.fdSaveOldFile = fdSaveOldFile;
	}

	public int getFdPdfAlive() {
		return fdPdfAlive;
	}

	public void setFdPdfAlive(int fdPdfAlive) {
		this.fdPdfAlive = fdPdfAlive;
	}

	/**
	 * 其它模块归档时下载原文档状态
	 * <pre>
	 *     0: 正在打印中（异步线程）
	 *     1: 打印成功
	 *     2: 打印失败
	 * </pre>
	 */
	private String fdPrintState;

	public String getFdPrintState() {
		return fdPrintState;
	}

	public void setFdPrintState(String fdPrintState) {
		this.fdPrintState = fdPrintState;
	}
}
