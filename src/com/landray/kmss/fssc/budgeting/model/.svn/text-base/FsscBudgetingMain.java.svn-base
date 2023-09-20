package com.landray.kmss.fssc.budgeting.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingMainForm;
import com.landray.kmss.sys.edition.interfaces.ISysEditionAutoDeleteModel;
import com.landray.kmss.sys.edition.interfaces.ISysEditionMainModel;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
  * 预算编制
  */
public class FsscBudgetingMain extends BaseModel implements ISysEditionAutoDeleteModel,ISysNotifyModel{

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdOrgType;

    private String fdYear;

    private String docStatus;
    
    private String fdStatus;

    private Double fdTotalMoney;

    private Double fdChildTotalMoney;

    private String fdOrgId;
    
    private String fdOrgName;
    
    private String fdYearRule;

    private String fdQuarterRule;

    private String fdMonthRule;

    private String fdYearApply;

    private String fdQuarterApply;

    private String fdMonthApply;
    
    private Double fdElasticPercent;
    
    private String fdSchemeId;
    
    private String docSubject;
    
    private Date docCreateTime;
    
    private SysOrgPerson docCreator;
    
    private EopBasedataCompany fdCompany;

    private List<FsscBudgetingDetail> fdDetailList;
    
    private String fdApprovalOpinions;

    @Override
    public Class<FsscBudgetingMainForm> getFormClass() {
        return FsscBudgetingMainForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdDetailList", new ModelConvertor_ModelListToFormList("fdDetailList_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 机构类型
     */
    public String getFdOrgType() {
        return this.fdOrgType;
    }

    /**
     * 机构类型
     */
    public void setFdOrgType(String fdOrgType) {
        this.fdOrgType = fdOrgType;
    }

    /**
     * 预算年度
     */
    public String getFdYear() {
        return this.fdYear;
    }

    /**
     * 预算年度
     */
    public void setFdYear(String fdYear) {
        this.fdYear = fdYear;
    }

    /**
     * 单据状态，只为版本机制需要
     */
    @Override
    public String getDocStatus() {
        return this.docStatus;
    }

    /**
     * 单据状态，只为版本机制需要
     */
    @Override
    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }
    /**
     * 预算状态
     */
    public String getFdStatus() {
		return fdStatus;
	}
    /**
     * 预算状态
     */
	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

    /**
     * 预算总额
     */
    public Double getFdTotalMoney() {
        return this.fdTotalMoney;
    }

    /**
     * 预算总额
     */
    public void setFdTotalMoney(Double fdTotalMoney) {
        this.fdTotalMoney = fdTotalMoney;
    }

    /**
     * 下级预算汇总
     */
    public Double getFdChildTotalMoney() {
        return this.fdChildTotalMoney;
    }

    /**
     * 下级预算汇总
     */
    public void setFdChildTotalMoney(Double fdChildTotalMoney) {
        this.fdChildTotalMoney = fdChildTotalMoney;
    }

    /**
     * 组织机构
     */
    public String getFdOrgId() {
        return this.fdOrgId;
    }

    /**
     * 组织机构
     */
    public void setFdOrgId(String fdOrgId) {
        this.fdOrgId = fdOrgId;
    }
    
    /**
     * 机构名称
     */
    public String getFdOrgName() {
        return this.fdOrgName;
    }

    /**
     * 机构名称
     */
    public void setFdOrgName(String fdOrgName) {
        this.fdOrgName = fdOrgName;
    }
    
    /**
     * 年度控制规则
     */
    public String getFdYearRule() {
        return this.fdYearRule;
    }

    /**
     * 年度控制规则
     */
    public void setFdYearRule(String fdYearRule) {
        this.fdYearRule = fdYearRule;
    }

    /**
     * 季度控制规则
     */
    public String getFdQuarterRule() {
        return this.fdQuarterRule;
    }

    /**
     * 季度控制规则
     */
    public void setFdQuarterRule(String fdQuarterRule) {
        this.fdQuarterRule = fdQuarterRule;
    }

    /**
     * 月度控制规则
     */
    public String getFdMonthRule() {
        return this.fdMonthRule;
    }

    /**
     * 月度控制规则
     */
    public void setFdMonthRule(String fdMonthRule) {
        this.fdMonthRule = fdMonthRule;
    }

    /**
     * 年度运用规则
     */
    public String getFdYearApply() {
        return this.fdYearApply;
    }

    /**
     * 年度运用规则
     */
    public void setFdYearApply(String fdYearApply) {
        this.fdYearApply = fdYearApply;
    }

    /**
     * 季度运用规则
     */
    public String getFdQuarterApply() {
        return this.fdQuarterApply;
    }

    /**
     * 季度运用规则
     */
    public void setFdQuarterApply(String fdQuarterApply) {
        this.fdQuarterApply = fdQuarterApply;
    }

    /**
     * 月度运用规则
     */
    public String getFdMonthApply() {
        return this.fdMonthApply;
    }

    /**
     * 月度运用规则
     */
    public void setFdMonthApply(String fdMonthApply) {
        this.fdMonthApply = fdMonthApply;
    }
    
    /**
     * 弹性比例
     */
    public Double getFdElasticPercent() {
		return fdElasticPercent;
	}
    
    /**
     * 弹性比例
     */
	public void setFdElasticPercent(Double fdElasticPercent) {
		this.fdElasticPercent = fdElasticPercent;
	}

	/**
     * 预算方案ID
     */
    public String getFdSchemeId() {
		return fdSchemeId;
	}
    /**
     * 预算方案ID
     */
	public void setFdSchemeId(String fdSchemeId) {
		this.fdSchemeId = fdSchemeId;
	}

    
    /**
     * 记账公司
     */
    public EopBasedataCompany getFdCompany() {
        return this.fdCompany;
    }

    /**
     * 记账公司
     */
    public void setFdCompany(EopBasedataCompany fdCompany) {
        this.fdCompany = fdCompany;
    }

    /**
     * 预算编制明细
     */
    public List<FsscBudgetingDetail> getFdDetailList() {
        return this.fdDetailList;
    }

    /**
     * 预算编制明细
     */
    public void setFdDetailList(List<FsscBudgetingDetail> fdDetailList) {
        this.fdDetailList = fdDetailList;
    }
    
 // ==== 版本机制（开始） =====
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

	@Override
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	@Override
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime=docCreateTime;
		
	}

	@Override
	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	@Override
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator=docCreator;
		
	}

	@Override
	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}
	
 	// ==== 版本机制（结束） =====
	/**
	 * 审批意见
	 * @return
	 */
	public String getFdApprovalOpinions() {
		return (String) readLazyField("fdApprovalOpinions", fdApprovalOpinions);
	}
	/**审批意见
	 * 
	 * @param fdApprovalOpinions
	 */
	public void setFdApprovalOpinions(String fdApprovalOpinions) {
		this.fdApprovalOpinions = (String) writeLazyField("fdApprovalOpinions",
				this.fdApprovalOpinions, fdApprovalOpinions);
	}
}
