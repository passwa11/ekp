package com.landray.kmss.hr.ratify.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.ILastModifiedTimeModel;
import com.landray.kmss.hr.ratify.forms.HrRatifyMainForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.bookmark.interfaces.ISysBookmarkModel;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationModel;
import com.landray.kmss.sys.ftsearch.interfaces.INeedIndex;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.model.ExtendDataModelInfo;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzModel;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogAutoSaveModel;
import com.landray.kmss.sys.recycle.model.ISysRecycleModel;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainModel;
import com.landray.kmss.sys.relation.model.SysRelationMain;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainModel;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 人事流程主文档
  */
public class HrRatifyMain extends ExtendAuthModel
		implements InterceptFieldEnabled, ISysWfMainModel, IAttachment,
		IExtendDataModel, ISysReadLogAutoSaveModel, ISysRelationMainModel,
		ILastModifiedTimeModel, ISysCirculationModel, ISysQuartzModel,
		INeedIndex, ISysRecycleModel, ISysBookmarkModel, ISysNotifyModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

	protected Long docReadCount = new Long(0);

	private String readLogSSeparate = null;

	public String getReadLogSeparate() {
		return readLogSSeparate;
	}

	public void setReadLogSSeparate(String readLogSSeparate) {
		this.readLogSSeparate = readLogSSeparate;

	}

    private String docNumber;

    private String docSubject;

    private Date docPublishTime;

    private String docContent;

    private String docXform;

    private Boolean docUseXform;

    private String extendDataXML;

    private String extendFilePath;

    private HrRatifyTemplate docTemplate;

    private SysOrgElement fdDepartment;

    private List<SysOrgElement> fdFeedback = new ArrayList<SysOrgElement>();

	/*
	 * 在发布状态下已经指定过实施反馈人
	 */
	protected java.lang.Long fdFeedbackExecuted;

	private SysWfBusinessModel sysWfBusinessModel = new SysWfBusinessModel();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    private ExtendDataModelInfo extendDataModelInfo = new ExtendDataModelInfo(this);

    private SysRelationMain sysRelationMain = null;

    private String relationSeparate = null;

	/*
	 * 多对多关联 文档关键字
	 */
	protected List docKeyword = new ArrayList();

	// 标题规则
	private String titleRegulation;

	// 子流程modelName
	protected String fdSubclassModelname;

	// 是否归档
	protected Boolean fdIsFiling = Boolean.FALSE;

	/**
	 * 调岗、调薪生效状态 【true、生效；false、未生效;】
	 */
	protected Boolean fdIsEffective =Boolean.FALSE;

	/**
	 * 调岗、调薪生效状态 【true、生效；false、未生效;】
	 */
	public Boolean getFdIsEffective() {
		return fdIsEffective;
	}

	public void setFdIsEffective(Boolean fdIsEffective) {
		this.fdIsEffective = fdIsEffective;
	}

	@Override
    public Class<?> getFormClass() {
        return HrRatifyMainForm.class;
    }

	/*** 软删除 部署 ***/
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
		return docDeleteFlag != 1;
	}
	/***软删除结束***/
    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docPublishTime", new ModelConvertor_Common("docPublishTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.addNoConvertProperty("authEditorFlag");
            toFormPropertyMap.addNoConvertProperty("extendDataXML");
            toFormPropertyMap.addNoConvertProperty("extendFilePath");
            toFormPropertyMap.addNoConvertProperty("fdLastModifiedTime");
            toFormPropertyMap.put("docTemplate.fdName", "docTemplateName");
            toFormPropertyMap.put("docTemplate.fdId", "docTemplateId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdDepartment.deptLevelNames", "fdDepartmentName");
            toFormPropertyMap.put("fdDepartment.fdId", "fdDepartmentId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("fdFeedback", new ModelConvertor_ModelListToString("fdFeedbackIds:fdFeedbackNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttCopys", new ModelConvertor_ModelListToString("authAttCopyIds:authAttCopyNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttDownloads", new ModelConvertor_ModelListToString("authAttDownloadIds:authAttDownloadNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttPrints", new ModelConvertor_ModelListToString("authAttPrintIds:authAttPrintNames", "fdId:fdName"));
			// 关键字
			toFormPropertyMap.put("docKeyword",
					new ModelConvertor_ModelListToString(
							"fdKeywordIds:fdKeywordNames", "fdId:docKeyword")
									.setSplitStr(" "));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
        fdLastModifiedTime = new java.util.Date();
		if (getFdDepartment() == null && getDocCreator() != null) {
			setFdDepartment(getDocCreator().getFdParent());
		}
	}

	@Override
	protected void recalculateReaderField() {
		super.recalculateReaderField();
		String tmpStatus = getDocStatus();
		if (StringUtil.isNotNull(tmpStatus) && tmpStatus.charAt(0) >= '3') {
			List<SysOrgElement> tempList = this.getFdFeedback();
			if (tempList != null) {
				ArrayUtil.concatTwoList(tempList, authAllReaders);
			}
		}
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

    /**
     * 结束时间
     */
    public Date getDocPublishTime() {
        return this.docPublishTime;
    }

    /**
     * 结束时间
     */
    public void setDocPublishTime(Date docPublishTime) {
        this.docPublishTime = docPublishTime;
    }

    /**
     * 文档内容
     */
    public String getDocContent() {
        return (String) readLazyField("docContent", this.docContent);
    }

    /**
     * 文档内容
     */
    public void setDocContent(String docContent) {
        this.docContent = (String) writeLazyField("docContent", this.docContent, docContent);
    }

    /**
     * 扩展属性
     */
    public String getDocXform() {
        return (String) readLazyField("docXform", this.docXform);
    }

    /**
     * 扩展属性
     */
    public void setDocXform(String docXform) {
        this.docXform = (String) writeLazyField("docXform", this.docXform, docXform);
    }

    /**
     * 是否使用表单
     */
    public Boolean getDocUseXform() {
        return this.docUseXform;
    }

    /**
     * 是否使用表单
     */
    public void setDocUseXform(Boolean docUseXform) {
        this.docUseXform = docUseXform;
    }

    /**
     * 扩展属性
     */
    @Override
    public String getExtendDataXML() {
        return (String) readLazyField("extendDataXML", this.extendDataXML);
    }

    /**
     * 扩展属性
     */
    @Override
    public void setExtendDataXML(String extendDataXML) {
        this.extendDataXML = (String) writeLazyField("extendDataXML", this.extendDataXML, extendDataXML);
    }

    /**
     * 扩展属性文件路径
     */
    @Override
    public String getExtendFilePath() {
        return this.extendFilePath;
    }

    /**
     * 扩展属性文件路径
     */
    @Override
    public void setExtendFilePath(String extendFilePath) {
        this.extendFilePath = extendFilePath;
    }

    /**
     * 分类模板
     */
    public HrRatifyTemplate getDocTemplate() {
        return this.docTemplate;
    }

    /**
     * 分类模板
     */
    public void setDocTemplate(HrRatifyTemplate docTemplate) {
        this.docTemplate = docTemplate;
    }

    /**
     * 部门
     */
    public SysOrgElement getFdDepartment() {
        return this.fdDepartment;
    }

    /**
     * 部门
     */
    public void setFdDepartment(SysOrgElement fdDepartment) {
        this.fdDepartment = fdDepartment;
    }

    /**
     * 实施反馈人
     */
    public List<SysOrgElement> getFdFeedback() {
        return this.fdFeedback;
    }

    /**
     * 实施反馈人
     */
    public void setFdFeedback(List<SysOrgElement> fdFeedback) {
        this.fdFeedback = fdFeedback;
    }

	public java.lang.Long getFdFeedbackExecuted() {
		return fdFeedbackExecuted;
	}

	public void setFdFeedbackExecuted(java.lang.Long fdFeedbackExecuted) {
		this.fdFeedbackExecuted = fdFeedbackExecuted;
	}

    /**
     * 返回 所有人可阅读标记
     */
    @Override
    public Boolean getAuthReaderFlag() {
        return false;
    }

	@Override
    public SysWfBusinessModel getSysWfBusinessModel() {
        return sysWfBusinessModel;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    @Override
    public ExtendDataModelInfo getExtendDataModelInfo() {
        return extendDataModelInfo;
    }

    @Override
    public SysRelationMain getSysRelationMain() {
        return this.sysRelationMain;
    }

    @Override
    public void setSysRelationMain(SysRelationMain sysRelationMain) {
        this.sysRelationMain = sysRelationMain;
    }

    public String getRelationSeparate() {
        return this.relationSeparate;
    }

    public void setRelationSeparate(String relationSeparate) {
        this.relationSeparate = relationSeparate;
    }

	public List getDocKeyword() {
		return docKeyword;
	}

	public void setDocKeyword(List docKeyword) {
		this.docKeyword = docKeyword;
	}

	public String getTitleRegulation() {
		return titleRegulation;
	}

	public void setTitleRegulation(String titleRegulation) {
		this.titleRegulation = titleRegulation;
	}

	public String getFdSubclassModelname() {
		return fdSubclassModelname;
	}

	public void setFdSubclassModelname(String fdSubclassModelname) {
		this.fdSubclassModelname = fdSubclassModelname;
	}

    @Override
    public String getCirculationSeparate() {
        return null;
    }

    @Override
    public void setCirculationSeparate(String circulationSeparate) {
    }

	private List<SysOrgPost> fdEntryPosts;

	/**
	 * 入职岗位
	 */
	public List<SysOrgPost> getFdEntryPosts() {
		return this.fdEntryPosts;
	}

	/**
	 * 入职岗位
	 */
	public void setFdEntryPosts(List<SysOrgPost> fdEntryPosts) {
		this.fdEntryPosts = fdEntryPosts;
	}

	// *************以下是入职员工个人经历信息*************** //
	// 工作经历
	private List<HrRatifyHistory> fdHistory;
	// 教育记录
	private List<HrRatifyEduExp> fdEducations;
	// 资格证书
	private List<HrRatifyCertifi> fdCertificate;
	// 奖惩信息
	private List<HrRatifyRewPuni> fdRewardsPunishments;
	// *************以上是入职员工个人经历信息*************** //

	public List<HrRatifyHistory> getFdHistory() {
		return fdHistory;
	}

	public void setFdHistory(List<HrRatifyHistory> fdHistory) {
		this.fdHistory = fdHistory;
	}

	public List<HrRatifyEduExp> getFdEducations() {
		return fdEducations;
	}

	public void setFdEducations(List<HrRatifyEduExp> fdEducations) {
		this.fdEducations = fdEducations;
	}

	public List<HrRatifyCertifi> getFdCertificate() {
		return fdCertificate;
	}

	public void setFdCertificate(List<HrRatifyCertifi> fdCertificate) {
		this.fdCertificate = fdCertificate;
	}

	public List<HrRatifyRewPuni> getFdRewardsPunishments() {
		return fdRewardsPunishments;
	}

	public void
			setFdRewardsPunishments(
					List<HrRatifyRewPuni> fdRewardsPunishments) {
		this.fdRewardsPunishments = fdRewardsPunishments;
	}

	private List<SysOrgPost> fdTransferLeavePosts;

	private List<SysOrgPost> fdTransferEnterPosts;

	/**
	 * 调出岗位
	 */
	public List<SysOrgPost> getFdTransferLeavePosts() {
		return this.fdTransferLeavePosts;
	}

	/**
	 * 调出岗位
	 */
	public void setFdTransferLeavePosts(List<SysOrgPost> fdTransferLeavePosts) {
		this.fdTransferLeavePosts = fdTransferLeavePosts;
	}

	/**
	 * 调入岗位
	 */
	public List<SysOrgPost> getFdTransferEnterPosts() {
		return this.fdTransferEnterPosts;
	}

	/**
	 * 调入岗位
	 */
	public void setFdTransferEnterPosts(List<SysOrgPost> fdTransferEnterPosts) {
		this.fdTransferEnterPosts = fdTransferEnterPosts;
	}

	private List<SysOrgPost> fdRehirePosts;

	/**
	 * 返聘岗位
	 */
	public List<SysOrgPost> getFdRehirePosts() {
		return this.fdRehirePosts;
	}

	/**
	 * 返聘岗位
	 */
	public void setFdRehirePosts(List<SysOrgPost> fdRehirePosts) {
		this.fdRehirePosts = fdRehirePosts;
	}

	/**
	 * 是否归档
	 */
	public Boolean getFdIsFiling() {
		return fdIsFiling;
	}

	/**
	 * 是否归档
	 */
	public void setFdIsFiling(Boolean fdIsFiling) {
		this.fdIsFiling = fdIsFiling;
	}

	/**
	 * 被收藏次数
	 */
	private Integer docMarkCount;

	@Override
    public Integer getDocMarkCount() {
		return docMarkCount;
	}

	@Override
    public void setDocMarkCount(Integer count) {
		this.docMarkCount = count;
	}

	private java.lang.String fdNotifyType;

	public java.lang.String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(java.lang.String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

}
