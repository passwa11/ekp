package com.landray.kmss.fssc.budget.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.fssc.budget.model.FsscBudgetMain;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
  * 预算主表
  */
public class FsscBudgetMainForm extends ExtendForm {

	private static final long serialVersionUID = -5332261784787153848L;

	private static FormToModelPropertyMap toModelPropertyMap;

    private String fdYear;

    private String docCreateTime;

    private String fdDesc;

    private String fdEnableDate;

    private String fdCompanyId;

    private String fdCompanyName;

    private String fdBudgetSchemeId;

    private String fdBudgetSchemeName;

    private String docCreatorId;

    private String docCreatorName;

    private String fdCompanyGroupId;

    private String fdCompanyGroupName;

    private String fdCurrencyId;

    private String fdCurrencyName;

    private AutoArrayList fdDetailList_Form = new AutoArrayList(FsscBudgetDetailForm.class);

    private String fdDetailList_Flag;
    
    private FormFile fdFile;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdYear = null;
        docCreateTime = null;
        fdDesc = null;
        fdEnableDate = null;
        fdCompanyId = null;
        fdCompanyName = null;
        fdBudgetSchemeId = null;
        fdBudgetSchemeName = null;
        docCreatorId = null;
        docCreatorName = null;
        fdCompanyGroupId = null;
        fdCompanyGroupName = null;
        fdCurrencyId = null;
        fdCurrencyName = null;
        fdDetailList_Form = new AutoArrayList(FsscBudgetDetailForm.class);
        fdDetailList_Flag = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetMain> getModelClass() {
        return FsscBudgetMain.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdEnableDate", new FormConvertor_Common("fdEnableDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("fdBudgetSchemeId", new FormConvertor_IDToModel("fdBudgetScheme", EopBasedataBudgetScheme.class));
            toModelPropertyMap.put("fdCompanyGroupId", new FormConvertor_IDToModel("fdCompanyGroup", EopBasedataCompanyGroup.class));
            toModelPropertyMap.put("fdCurrencyId", new FormConvertor_IDToModel("fdCurrency", EopBasedataCurrency.class));
            toModelPropertyMap.put("fdDetailList_Form", new FormConvertor_FormListToModelList("fdDetailList", "docMain", "fdDetailList_Flag"));
        }
        return toModelPropertyMap;
    }

    /**
     * 年度
     */
    public String getFdYear() {
        return this.fdYear;
    }

    /**
     * 年度
     */
    public void setFdYear(String fdYear) {
        this.fdYear = fdYear;
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
     * 描述
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 描述
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
    }

    /**
     * 预算启用时间
     */
    public String getFdEnableDate() {
        return this.fdEnableDate;
    }

    /**
     * 预算启用时间
     */
    public void setFdEnableDate(String fdEnableDate) {
        this.fdEnableDate = fdEnableDate;
    }

    /**
     * 所属公司
     */
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 所属公司
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
    }

    /**
     * 所属公司
     */
    public String getFdCompanyName() {
        return this.fdCompanyName;
    }

    /**
     * 所属公司
     */
    public void setFdCompanyName(String fdCompanyName) {
        this.fdCompanyName = fdCompanyName;
    }

    /**
     * 预算方案
     */
    public String getFdBudgetSchemeId() {
        return this.fdBudgetSchemeId;
    }

    /**
     * 预算方案
     */
    public void setFdBudgetSchemeId(String fdBudgetSchemeId) {
        this.fdBudgetSchemeId = fdBudgetSchemeId;
    }

    /**
     * 预算方案
     */
    public String getFdBudgetSchemeName() {
        return this.fdBudgetSchemeName;
    }

    /**
     * 预算方案
     */
    public void setFdBudgetSchemeName(String fdBudgetSchemeName) {
        this.fdBudgetSchemeName = fdBudgetSchemeName;
    }

    /**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 所属公司组
     */
    public String getFdCompanyGroupId() {
        return this.fdCompanyGroupId;
    }

    /**
     * 所属公司组
     */
    public void setFdCompanyGroupId(String fdCompanyGroupId) {
        this.fdCompanyGroupId = fdCompanyGroupId;
    }

    /**
     * 所属公司组
     */
    public String getFdCompanyGroupName() {
        return this.fdCompanyGroupName;
    }

    /**
     * 所属公司组
     */
    public void setFdCompanyGroupName(String fdCompanyGroupName) {
        this.fdCompanyGroupName = fdCompanyGroupName;
    }

    /**
     * 币种汇率
     */
    public String getFdCurrencyId() {
        return this.fdCurrencyId;
    }

    /**
     * 币种汇率
     */
    public void setFdCurrencyId(String fdCurrencyId) {
        this.fdCurrencyId = fdCurrencyId;
    }

    /**
     * 币种汇率
     */
    public String getFdCurrencyName() {
        return this.fdCurrencyName;
    }

    /**
     * 币种汇率
     */
    public void setFdCurrencyName(String fdCurrencyName) {
        this.fdCurrencyName = fdCurrencyName;
    }

    /**
     * 预算明细
     */
    public AutoArrayList getFdDetailList_Form() {
        return this.fdDetailList_Form;
    }

    /**
     * 预算明细
     */
    public void setFdDetailList_Form(AutoArrayList fdDetailList_Form) {
        this.fdDetailList_Form = fdDetailList_Form;
    }

    /**
     * 预算明细
     */
    public String getFdDetailList_Flag() {
        return this.fdDetailList_Flag;
    }

    /**
     * 预算明细
     */
    public void setFdDetailList_Flag(String fdDetailList_Flag) {
        this.fdDetailList_Flag = fdDetailList_Flag;
    }
    
    public FormFile getFdFile() {
		return fdFile;
	}

	public void setFdFile(FormFile fdFile) {
		this.fdFile = fdFile;
	}
}
