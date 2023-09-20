package com.landray.kmss.fssc.budgeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingMain;
import com.landray.kmss.sys.edition.forms.SysEditionMainForm;
import com.landray.kmss.sys.edition.interfaces.ISysEditionMainForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 预算编制
  */
public class FsscBudgetingMainForm extends ExtendForm implements ISysEditionMainForm{

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdOrgType;

    private String fdYear;

    private String docStatus;
    
    private String fdStatus;

    private String fdTotalMoney;

    private String fdChildTotalMoney;

    private String fdOrgId;
    
    private String fdOrgName;
    
    private String fdYearRule;

    private String fdQuarterRule;

    private String fdMonthRule;

    private String fdYearApply;

    private String fdQuarterApply;

    private String fdMonthApply;
    
    private String fdElasticPercent;
    
    private String fdCompanyId;
    
    private String fdCompanyName;
    
    private String fdSchemeId;
    
    private String docSubject;
    
    private String docCreateTime;
    
    private String docCreatorId;
    
    private String docCreatorName;

    private AutoArrayList fdDetailList_Form = new AutoArrayList(FsscBudgetingDetailForm.class);

    private String fdDetailList_Flag;
    
    private String docIsNewVersion;
    
    private String fdApprovalOpinions;
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdOrgType = null;
        fdYear = null;
        docStatus = null;
        fdStatus=null;
        fdTotalMoney = null;
        fdChildTotalMoney = null;
        fdOrgId = null;
        fdOrgName=null;
        fdYearRule=null;
        fdQuarterRule=null;
        fdMonthRule=null;
        fdYearApply=null;
        fdQuarterApply=null;
        fdMonthApply=null;
        fdElasticPercent=null;
        fdCompanyId=null;
        fdCompanyName=null;
        fdSchemeId=null;
        docSubject=null;
        docCreateTime=null;
        docCreatorId=null;
        docCreatorName=null;
        fdDetailList_Form = new AutoArrayList(FsscBudgetingDetailForm.class);
        fdDetailList_Flag = null;
        docIsNewVersion=null;
        fdApprovalOpinions=null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetingMain> getModelClass() {
        return FsscBudgetingMain.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("fdOrgType");
            toModelPropertyMap.addNoConvertProperty("fdYear");
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel("docCreator", SysOrgPerson.class));
            toModelPropertyMap.put("fdDetailList_Form", new FormConvertor_FormListToModelList("fdDetailList", "docMain", "fdDetailList_Flag"));
        }
        return toModelPropertyMap;
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
    public String getDocStatus() {
        return this.docStatus;
    }

    /**
     * 单据状态，只为版本机制需要
     */
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
    public String getFdTotalMoney() {
        return this.fdTotalMoney;
    }

    /**
     * 预算总额
     */
    public void setFdTotalMoney(String fdTotalMoney) {
        this.fdTotalMoney = fdTotalMoney;
    }

    /**
     * 下级预算汇总
     */
    public String getFdChildTotalMoney() {
        return this.fdChildTotalMoney;
    }

    /**
     * 下级预算汇总
     */
    public void setFdChildTotalMoney(String fdChildTotalMoney) {
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
    
    public String getFdElasticPercent() {
		return fdElasticPercent;
	}
    
    /**
     * 弹性比例
     */
	public void setFdElasticPercent(String fdElasticPercent) {
		this.fdElasticPercent = fdElasticPercent;
	}

	/**
     * 记账公司
     */
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 记账公司
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
    }

    /**
     * 记账公司
     */
    public String getFdCompanyName() {
        return this.fdCompanyName;
    }

    /**
     * 记账公司
     */
    public void setFdCompanyName(String fdCompanyName) {
        this.fdCompanyName = fdCompanyName;
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
     * 预算编制明细
     */
    public AutoArrayList getFdDetailList_Form() {
        return this.fdDetailList_Form;
    }

    /**
     * 预算编制明细
     */
    public void setFdDetailList_Form(AutoArrayList fdDetailList_Form) {
        this.fdDetailList_Form = fdDetailList_Form;
    }

    /**
     * 预算编制明细
     */
    public String getFdDetailList_Flag() {
        return this.fdDetailList_Flag;
    }

    /**
     * 预算编制明细
     */
    public void setFdDetailList_Flag(String fdDetailList_Flag) {
        this.fdDetailList_Flag = fdDetailList_Flag;
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

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}
	
	 /*
    * 通知机制
    */
    private String fdNotifyType = null;

    public String getFdNotifyType() {
    	return fdNotifyType;
    }

    public void setFdNotifyType(String fdNotifyType) {
    	this.fdNotifyType = fdNotifyType;
    }
    
    /**
     * 是否为新版本
     * @return
     */
	public String getDocIsNewVersion() {
		return docIsNewVersion;
	}

	public void setDocIsNewVersion(String docIsNewVersion) {
		this.docIsNewVersion = docIsNewVersion;
	}
	
	/**
	 * 审批意见
	 * @return
	 */
	public String getFdApprovalOpinions() {
		return fdApprovalOpinions;
	}
	/**
	 * 审批意见
	 * @param fdApprovalOpinions
	 */
	public void setFdApprovalOpinions(String fdApprovalOpinions) {
		this.fdApprovalOpinions = fdApprovalOpinions;
	}
	
}
