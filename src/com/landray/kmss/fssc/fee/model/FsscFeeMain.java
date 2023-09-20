package com.landray.kmss.fssc.fee.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.fssc.fee.forms.FsscFeeMainForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationModel;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModel;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.model.ExtendDataModelInfo;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.recycle.model.ISysRecycleModel;
import com.landray.kmss.sys.recycle.model.SysRecycleConstant;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainModel;
import com.landray.kmss.sys.relation.model.SysRelationMain;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 费用申请
  */
public class FsscFeeMain extends ExtendAuthModel implements InterceptFieldEnabled, ISysLbpmMainModel, IAttachment, IExtendDataModel, ISysRelationMainModel, ISysCirculationModel ,ISysRecycleModel{

    private static ModelToFormPropertyMap toFormPropertyMap;
    
    private Boolean fdIsClosed;

    private String docNumber;

    private String docSubject;

    private String fdControlType;

    private String docXform;

    private Boolean docUseXform;

    private String extendDataXML;

    private String extendFilePath;

    private FsscFeeTemplate docTemplate;

    private LbpmProcessForm sysWfBusinessModel = new LbpmProcessForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    private ExtendDataModelInfo extendDataModelInfo = new ExtendDataModelInfo(this);

    private SysRelationMain sysRelationMain = null;

    private String relationSeparate = null;
    
    //发布时间
    private Date  docPublishTime;

    @Override
    public Class<FsscFeeMainForm> getFormClass() {
        return FsscFeeMainForm.class;
    }

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
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docTemplate.fdName", "docTemplateName");
            toFormPropertyMap.put("docTemplate.fdId", "docTemplateId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
        if (!getAuthReaderFlag()) {
        }
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
     * 刚柔控制
     */
    public String getFdControlType() {
        return this.fdControlType;
    }

    /**
     * 刚柔控制
     */
    public void setFdControlType(String fdControlType) {
        this.fdControlType = fdControlType;
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
    public FsscFeeTemplate getDocTemplate() {
        return this.docTemplate;
    }

    /**
     * 分类模板
     */
    public void setDocTemplate(FsscFeeTemplate docTemplate) {
        this.docTemplate = docTemplate;
    }

    /**
     * 返回 所有人可阅读标记
     */
    @Override
    public Boolean getAuthReaderFlag() {
        return false;
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

    @Override
    public String getCirculationSeparate() {
        return null;
    }

    @Override
    public void setCirculationSeparate(String circulationSeparate) {
    }

	public Boolean getFdIsClosed() {
		return fdIsClosed;
	}

	public void setFdIsClosed(Boolean fdIsClosed) {
		this.fdIsClosed = fdIsClosed;
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

	public boolean isNeedIndex() {
		return docDeleteFlag == null || docDeleteFlag == SysRecycleConstant.OPT_TYPE_RECOVER;
	}
	/*回收站机制结束*/

	public Date getDocPublishTime() {
		return docPublishTime;
	}

	public void setDocPublishTime(Date docPublishTime) {
		this.docPublishTime = docPublishTime;
	}
}
