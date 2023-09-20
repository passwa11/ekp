package com.landray.kmss.fssc.expense.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.fssc.expense.forms.FsscExpenseShareMainForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationModel;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModel;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.recycle.model.ISysRecycleModel;
import com.landray.kmss.sys.recycle.model.SysRecycleConstant;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainModel;
import com.landray.kmss.sys.relation.model.SysRelationMain;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
  * 事后分摊
  */
public class FsscExpenseShareMain extends ExtendAuthModel implements ISysLbpmMainModel, IAttachment, ISysRelationMainModel, ISysCirculationModel, ISysRecycleModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private Date fdOperateDate;

    private String fdDescription;

    private String fdNumber;

    private String fdVoucherStatus;

    private String fdBookkeepingStatus;

    private String fdBookkeepingMessage;

    private SysOrgPerson fdOperator;

    private SysOrgElement fdOperatorDept;

    private String fdModelId;	//	关联单据id

    private String fdModelName;	//	关联单据

    private FsscExpenseShareCategory docTemplate;

    private List<FsscExpenseShareDetail> fdDetailList;

    private LbpmProcessForm sysWfBusinessModel = new LbpmProcessForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
    
    private SysRelationMain sysRelationMain = null;

    private String relationSeparate = null;
    
    //发布时间
    private Date  docPublishTime;

    @Override
    public Class<FsscExpenseShareMainForm> getFormClass() {
        return FsscExpenseShareMainForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdOperateDate", new ModelConvertor_Common("fdOperateDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("docPublishTime", new ModelConvertor_Common("docPublishTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.addNoConvertProperty("authEditorFlag");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdOperator.fdName", "fdOperatorName");
            toFormPropertyMap.put("fdOperator.fdId", "fdOperatorId");
            toFormPropertyMap.put("fdOperatorDept.fdName", "fdOperatorDeptName");
            toFormPropertyMap.put("fdOperatorDept.fdId", "fdOperatorDeptId");
            toFormPropertyMap.put("docTemplate.fdName", "docTemplateName");
            toFormPropertyMap.put("docTemplate.fdId", "docTemplateId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttCopys", new ModelConvertor_ModelListToString("authAttCopyIds:authAttCopyNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttDownloads", new ModelConvertor_ModelListToString("authAttDownloadIds:authAttDownloadNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttPrints", new ModelConvertor_ModelListToString("authAttPrintIds:authAttPrintNames", "fdId:fdName"));
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("fdDetailList", new ModelConvertor_ModelListToFormList("fdDetailList_Form"));
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
     * 分摊时间
     */
    public Date getFdOperateDate() {
        return this.fdOperateDate;
    }

    /**
     * 分摊时间
     */
    public void setFdOperateDate(Date fdOperateDate) {
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

    /**
     * 编号
     */
    public String getFdNumber() {
        return this.fdNumber;
    }

    /**
     * 编号
     */
    public void setFdNumber(String docNumber) {
        this.fdNumber = docNumber;
    }

    /**
     * 分摊人
     */
    public SysOrgPerson getFdOperator() {
        return this.fdOperator;
    }

    /**
     * 分摊人
     */
    public void setFdOperator(SysOrgPerson fdOperator) {
        this.fdOperator = fdOperator;
    }

    /**
     * 分摊人部门
     */
    public SysOrgElement getFdOperatorDept() {
        return this.fdOperatorDept;
    }

    /**
     * 分摊人部门
     */
    public void setFdOperatorDept(SysOrgElement fdOperatorDept) {
        this.fdOperatorDept = fdOperatorDept;
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
    public FsscExpenseShareCategory getDocTemplate() {
        return this.docTemplate;
    }

    /**
     * 分类模板
     */
    public void setDocTemplate(FsscExpenseShareCategory docTemplate) {
        this.docTemplate = docTemplate;
    }

    /**
     * 分摊明细
     */
    public List<FsscExpenseShareDetail> getFdDetailList() {
        return this.fdDetailList;
    }

    /**
     * 分摊明细
     */
    public void setFdDetailList(List<FsscExpenseShareDetail> fdDetailList) {
        this.fdDetailList = fdDetailList;
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
