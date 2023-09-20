package com.landray.kmss.fssc.budget.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.fssc.budget.forms.FsscBudgetAdjustMainForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationModel;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModel;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.recycle.model.ISysRecycleModel;
import com.landray.kmss.sys.recycle.model.SysRecycleConstant;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
  * 预算调整
  */
public class FsscBudgetAdjustMain extends ExtendAuthModel implements IAttachment,ISysCirculationModel,ISysLbpmMainModel,ISysRecycleModel {

	private static final long serialVersionUID = 7011252055761983181L;

	private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private String fdDesc;

    private String docNumber;

    private FsscBudgetAdjustCategory docTemplate;

    private EopBasedataCompany fdCompany;

    private EopBasedataCurrency fdCurrency;

    private EopBasedataBudgetScheme fdBudgetScheme;
    
    private String fdTips;

    private List<FsscBudgetAdjustDetail> fdDetailList;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    private LbpmProcessForm sysWfBusinessModel = new LbpmProcessForm();

    //发布时间
    private Date  docPublishTime;

    @Override
    public Class<FsscBudgetAdjustMainForm> getFormClass() {
        return FsscBudgetAdjustMainForm.class;
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
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docTemplate.fdName", "docTemplateName");
            toFormPropertyMap.put("docTemplate.fdId", "docTemplateId");
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
            toFormPropertyMap.put("fdCurrency.fdName", "fdCurrencyName");
            toFormPropertyMap.put("fdCurrency.fdId", "fdCurrencyId");
            toFormPropertyMap.put("fdBudgetScheme.fdName", "fdBudgetSchemeName");
            toFormPropertyMap.put("fdBudgetScheme.fdId", "fdBudgetSchemeId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttCopys", new ModelConvertor_ModelListToString("authAttCopyIds:authAttCopyNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttDownloads", new ModelConvertor_ModelListToString("authAttDownloadIds:authAttDownloadNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttPrints", new ModelConvertor_ModelListToString("authAttPrintIds:authAttPrintNames", "fdId:fdName"));
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
     * 返回 所有人可阅读标记
     */
    @Override
    public Boolean getAuthReaderFlag() {
        return false;
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
     * 调整原因
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 调整原因
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
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
     * 分类模板
     */
    public FsscBudgetAdjustCategory getDocTemplate() {
        return this.docTemplate;
    }

    /**
     * 分类模板
     */
    public void setDocTemplate(FsscBudgetAdjustCategory docTemplate) {
        this.docTemplate = docTemplate;
    }

    /**
     * 所属公司
     */
    public EopBasedataCompany getFdCompany() {
        return this.fdCompany;
    }

    /**
     * 所属公司
     */
    public void setFdCompany(EopBasedataCompany fdCompany) {
        this.fdCompany = fdCompany;
    }

    /**
     * 币种
     */
    public EopBasedataCurrency getFdCurrency() {
        return this.fdCurrency;
    }

    /**
     * 币种
     */
    public void setFdCurrency(EopBasedataCurrency fdCurrency) {
        this.fdCurrency = fdCurrency;
    }

    /**
     * 预算方案
     */
    public EopBasedataBudgetScheme getFdBudgetScheme() {
        return this.fdBudgetScheme;
    }

    /**
     * 预算方案
     */
    public void setFdBudgetScheme(EopBasedataBudgetScheme fdBudgetScheme) {
        this.fdBudgetScheme = fdBudgetScheme;
    }
    
    /**
     * 预算调整提示信息，若是跨月，而借入当月或者当季无预算，提示
     */

    public String getFdTips() {
		return fdTips;
	}
    
    /**
     * 预算调整提示信息，若是跨月，而借入当月或者当季无预算，提示
     */
	public void setFdTips(String fdTips) {
		this.fdTips = fdTips;
	}

	/**
     * 预算调整明细
     */
    public List<FsscBudgetAdjustDetail> getFdDetailList() {
        return this.fdDetailList;
    }

    /**
     * 预算调整明细
     */
    public void setFdDetailList(List<FsscBudgetAdjustDetail> fdDetailList) {
        this.fdDetailList = fdDetailList;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    @Override
    public LbpmProcessForm getSysWfBusinessModel() {
        return sysWfBusinessModel;
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
