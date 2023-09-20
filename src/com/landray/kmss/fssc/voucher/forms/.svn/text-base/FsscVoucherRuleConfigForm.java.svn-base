package com.landray.kmss.fssc.voucher.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;

import com.landray.kmss.fssc.voucher.model.FsscVoucherModelConfig;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.fssc.voucher.model.FsscVoucherRuleConfig;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 凭证规则设置
  */
public class FsscVoucherRuleConfigForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdCategoryId;

    private String fdCategoryModelName;

    private String fdCategoryName;

    private String fdPushType;

    private String fdRuleFormula;

    private String fdRuleText;

    private String fdModelNumberFormula;

    private String fdModelNumberText;

    private String fdVoucherTypeFlag;

    private String fdVoucherTypeFormula;

    private String fdVoucherTypeText;

    private String fdCompanyFlag;

    private String fdCompanyFormula;

    private String fdCompanyText;

    private String fdCurrencyFlag;

    private String fdCurrencyFormula;

    private String fdCurrencyText;

    private String fdVoucherDateFormula;

    private String fdVoucherDateText;

    private String fdNumberFormula;

    private String fdNumberText;

    private String fdVoucherTextFormula;

    private String fdVoucherTextText;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String fdVoucherModelConfigId;

    private String fdVoucherModelConfigName;

    private String fdVoucherModelConfigModelName;

    private String fdVoucherTypeId;

    private String fdVoucherTypeName;

    private String fdCompanyId;

    private String fdCompanyName;

    private String fdCurrencyId;

    private String fdCurrencyName;

    private String fdOrderMakingPersonFlag;

    private String fdOrderMakingPersonFormula;

    private String fdOrderMakingPersonText;

    private String fdOrderMakingPersonId;

    private String fdOrderMakingPersonName;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private AutoArrayList fdDetail_Form = new AutoArrayList(FsscVoucherRuleDetailForm.class);

    private String fdDetail_Flag = "0";
    
    private String fdMergeEntry;//合并分录

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdCategoryId = null;
        fdCategoryModelName = null;
        fdCategoryName = null;
        fdPushType = null;
        fdRuleFormula = null;
        fdRuleText = null;
        fdModelNumberFormula = null;
        fdModelNumberText = null;
        fdVoucherTypeFlag = null;
        fdVoucherTypeFormula = null;
        fdVoucherTypeText = null;
        fdCompanyFlag = null;
        fdCompanyFormula = null;
        fdCompanyText = null;
        fdCurrencyFlag = null;
        fdCurrencyFormula = null;
        fdCurrencyText = null;
        fdVoucherDateFormula = null;
        fdVoucherDateText = null;
        fdNumberFormula = null;
        fdNumberText = null;
        fdVoucherTextFormula = null;
        fdVoucherTextText = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        fdVoucherModelConfigId = null;
        fdVoucherModelConfigName = null;
        fdVoucherModelConfigModelName = null;
        fdVoucherTypeId = null;
        fdVoucherTypeName = null;
        fdCompanyId = null;
        fdCompanyName = null;
        fdCurrencyId = null;
        fdCurrencyName = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdOrderMakingPersonFlag = null;
        fdOrderMakingPersonFormula = null;
        fdOrderMakingPersonText = null;
        fdOrderMakingPersonId = null;
        fdOrderMakingPersonName = null;
        fdDetail_Form = new AutoArrayList(FsscVoucherRuleDetailForm.class);
        fdDetail_Flag = null;
        fdMergeEntry = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscVoucherRuleConfig> getModelClass() {
        return FsscVoucherRuleConfig.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdOrderMakingPersonId", new FormConvertor_IDToModel("fdOrderMakingPerson", SysOrgPerson.class));
            toModelPropertyMap.put("fdVoucherModelConfigId", new FormConvertor_IDToModel("fdVoucherModelConfig", FsscVoucherModelConfig.class));
            toModelPropertyMap.put("fdVoucherTypeId", new FormConvertor_IDToModel("fdVoucherType", EopBasedataVoucherType.class));
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("fdCurrencyId", new FormConvertor_IDToModel("fdCurrency", EopBasedataCurrency.class));
            toModelPropertyMap.put("fdDetail_Form", new FormConvertor_FormListToModelList("fdDetail", "docMain", "fdDetail_Flag"));
        }
        return toModelPropertyMap;
    }

    /**
     * 凭证规则名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 凭证规则名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 对应分类id
     */
    public String getFdCategoryId() {
        return this.fdCategoryId;
    }

    /**
     * 对应分类id
     */
    public void setFdCategoryId(String fdCategoryId) {
        this.fdCategoryId = fdCategoryId;
    }

    /**
     * 对应分类modelName
     */
    public String getFdCategoryModelName() {
        return this.fdCategoryModelName;
    }

    /**
     * 对应分类modelName
     */
    public void setFdCategoryModelName(String fdCategoryModelName) {
        this.fdCategoryModelName = fdCategoryModelName;
    }

    /**
     * 对应分类名称
     */
    public String getFdCategoryName() {
        return this.fdCategoryName;
    }

    /**
     * 对应分类名称
     */
    public void setFdCategoryName(String fdCategoryName) {
        this.fdCategoryName = fdCategoryName;
    }

    /**
     * 生成规则
     */
    public String getFdRuleFormula() {
        return this.fdRuleFormula;
    }

    /**
     * 生成规则
     */
    public void setFdRuleFormula(String fdRuleFormula) {
        this.fdRuleFormula = fdRuleFormula;
    }

    /**
     * 生成规则Text
     */
    public String getFdRuleText() {
        return this.fdRuleText;
    }

    /**
     * 生成规则Text
     */
    public void setFdRuleText(String fdRuleText) {
        this.fdRuleText = fdRuleText;
    }

    /**
     * 来源单据编号
     */
    public String getFdModelNumberFormula() {
        return this.fdModelNumberFormula;
    }

    /**
     * 来源单据编号
     */
    public void setFdModelNumberFormula(String fdModelNumberFormula) {
        this.fdModelNumberFormula = fdModelNumberFormula;
    }

    /**
     * 来源单据编号Text
     */
    public String getFdModelNumberText() {
        return this.fdModelNumberText;
    }

    /**
     * 来源单据编号Text
     */
    public void setFdModelNumberText(String fdModelNumberText) {
        this.fdModelNumberText = fdModelNumberText;
    }

    /**
     * 凭证类型flag
     */
    public String getFdVoucherTypeFlag() {
        return this.fdVoucherTypeFlag;
    }

    /**
     * 凭证类型flag
     */
    public void setFdVoucherTypeFlag(String fdVoucherTypeFlag) {
        this.fdVoucherTypeFlag = fdVoucherTypeFlag;
    }

    /**
     * 凭证类型
     */
    public String getFdVoucherTypeFormula() {
        return this.fdVoucherTypeFormula;
    }

    /**
     * 凭证类型
     */
    public void setFdVoucherTypeFormula(String fdVoucherTypeFormula) {
        this.fdVoucherTypeFormula = fdVoucherTypeFormula;
    }

    /**
     * 凭证类型text
     */
    public String getFdVoucherTypeText() {
        return this.fdVoucherTypeText;
    }

    /**
     * 凭证类型text
     */
    public void setFdVoucherTypeText(String fdVoucherTypeText) {
        this.fdVoucherTypeText = fdVoucherTypeText;
    }

    /**
     * 公司flag
     */
    public String getFdCompanyFlag() {
        return this.fdCompanyFlag;
    }

    /**
     * 公司flag
     */
    public void setFdCompanyFlag(String fdCompanyFlag) {
        this.fdCompanyFlag = fdCompanyFlag;
    }

    /**
     * 公司
     */
    public String getFdCompanyFormula() {
        return this.fdCompanyFormula;
    }

    /**
     * 公司
     */
    public void setFdCompanyFormula(String fdCompanyFormula) {
        this.fdCompanyFormula = fdCompanyFormula;
    }

    /**
     * 公司text
     */
    public String getFdCompanyText() {
        return this.fdCompanyText;
    }

    /**
     * 公司text
     */
    public void setFdCompanyText(String fdCompanyText) {
        this.fdCompanyText = fdCompanyText;
    }

    /**
     * 凭证货币flag
     */
    public String getFdCurrencyFlag() {
        return this.fdCurrencyFlag;
    }

    /**
     * 凭证货币flag
     */
    public void setFdCurrencyFlag(String fdCurrencyFlag) {
        this.fdCurrencyFlag = fdCurrencyFlag;
    }

    /**
     * 凭证货币
     */
    public String getFdCurrencyFormula() {
        return this.fdCurrencyFormula;
    }

    /**
     * 凭证货币
     */
    public void setFdCurrencyFormula(String fdCurrencyFormula) {
        this.fdCurrencyFormula = fdCurrencyFormula;
    }

    /**
     * 凭证货币text
     */
    public String getFdCurrencyText() {
        return this.fdCurrencyText;
    }

    /**
     * 凭证货币text
     */
    public void setFdCurrencyText(String fdCurrencyText) {
        this.fdCurrencyText = fdCurrencyText;
    }

    /**
     * 凭证日期
     */
    public String getFdVoucherDateFormula() {
        return this.fdVoucherDateFormula;
    }

    /**
     * 凭证日期
     */
    public void setFdVoucherDateFormula(String fdVoucherDateFormula) {
        this.fdVoucherDateFormula = fdVoucherDateFormula;
    }

    /**
     * 凭证日期text
     */
    public String getFdVoucherDateText() {
        return this.fdVoucherDateText;
    }

    /**
     * 凭证日期text
     */
    public void setFdVoucherDateText(String fdVoucherDateText) {
        this.fdVoucherDateText = fdVoucherDateText;
    }

    /**
     * 单据数
     */
    public String getFdNumberFormula() {
        return this.fdNumberFormula;
    }

    /**
     * 单据数
     */
    public void setFdNumberFormula(String fdNumberFormula) {
        this.fdNumberFormula = fdNumberFormula;
    }

    /**
     * 单据数text
     */
    public String getFdNumberText() {
        return this.fdNumberText;
    }

    /**
     * 单据数text
     */
    public void setFdNumberText(String fdNumberText) {
        this.fdNumberText = fdNumberText;
    }

    /**
     * 凭证抬头文本
     */
    public String getFdVoucherTextFormula() {
        return this.fdVoucherTextFormula;
    }

    /**
     * 凭证抬头文本
     */
    public void setFdVoucherTextFormula(String fdVoucherTextFormula) {
        this.fdVoucherTextFormula = fdVoucherTextFormula;
    }

    /**
     * 凭证抬头文本text
     */
    public String getFdVoucherTextText() {
        return this.fdVoucherTextText;
    }

    /**
     * 凭证抬头文本text
     */
    public void setFdVoucherTextText(String fdVoucherTextText) {
        this.fdVoucherTextText = fdVoucherTextText;
    }

    /**
     * 是否有效
     */
    public String getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(String fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
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
     * 更新时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(String docAlterTime) {
        this.docAlterTime = docAlterTime;
    }
    /**
     * 推送方式
     */
    public String getFdPushType() {
        return fdPushType;
    }
    /**
     * 推送方式
     */
    public void setFdPushType(String fdPushType) {
        this.fdPushType = fdPushType;
    }

    /**
     * 凭证模板设置
     */
    public String getFdVoucherModelConfigId() {
        return this.fdVoucherModelConfigId;
    }

    /**
     * 凭证模板设置
     */
    public void setFdVoucherModelConfigId(String fdVoucherModelConfigId) {
        this.fdVoucherModelConfigId = fdVoucherModelConfigId;
    }

    /**
     * 凭证模板设置
     */
    public String getFdVoucherModelConfigName() {
        return this.fdVoucherModelConfigName;
    }

    /**
     * 凭证模板设置
     */
    public void setFdVoucherModelConfigName(String fdVoucherModelConfigName) {
        this.fdVoucherModelConfigName = fdVoucherModelConfigName;
    }
    /**
     * 凭证模板设置ModelName
     */
    public String getFdVoucherModelConfigModelName() {
        return fdVoucherModelConfigModelName;
    }
    /**
     * 凭证模板设置ModelName
     */
    public void setFdVoucherModelConfigModelName(String fdVoucherModelConfigModelName) {
        this.fdVoucherModelConfigModelName = fdVoucherModelConfigModelName;
    }

    /**
     * 凭证类型
     */
    public String getFdVoucherTypeId() {
        return this.fdVoucherTypeId;
    }

    /**
     * 凭证类型
     */
    public void setFdVoucherTypeId(String fdVoucherTypeId) {
        this.fdVoucherTypeId = fdVoucherTypeId;
    }

    /**
     * 凭证类型
     */
    public String getFdVoucherTypeName() {
        return this.fdVoucherTypeName;
    }

    /**
     * 凭证类型
     */
    public void setFdVoucherTypeName(String fdVoucherTypeName) {
        this.fdVoucherTypeName = fdVoucherTypeName;
    }

    /**
     * 公司
     */
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 公司
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
    }

    /**
     * 公司
     */
    public String getFdCompanyName() {
        return this.fdCompanyName;
    }

    /**
     * 公司
     */
    public void setFdCompanyName(String fdCompanyName) {
        this.fdCompanyName = fdCompanyName;
    }

    /**
     * 凭证货币
     */
    public String getFdCurrencyId() {
        return this.fdCurrencyId;
    }

    /**
     * 凭证货币
     */
    public void setFdCurrencyId(String fdCurrencyId) {
        this.fdCurrencyId = fdCurrencyId;
    }

    /**
     * 凭证货币
     */
    public String getFdCurrencyName() {
        return this.fdCurrencyName;
    }

    /**
     * 凭证货币
     */
    public void setFdCurrencyName(String fdCurrencyName) {
        this.fdCurrencyName = fdCurrencyName;
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
     * 修改人
     */
    public String getDocAlterorId() {
        return this.docAlterorId;
    }

    /**
     * 修改人
     */
    public void setDocAlterorId(String docAlterorId) {
        this.docAlterorId = docAlterorId;
    }

    /**
     * 修改人
     */
    public String getDocAlterorName() {
        return this.docAlterorName;
    }

    /**
     * 修改人
     */
    public void setDocAlterorName(String docAlterorName) {
        this.docAlterorName = docAlterorName;
    }

    /**
     * 凭证规则明细
     */
    public AutoArrayList getFdDetail_Form() {
        return this.fdDetail_Form;
    }

    /**
     * 凭证规则明细
     */
    public void setFdDetail_Form(AutoArrayList fdDetail_Form) {
        this.fdDetail_Form = fdDetail_Form;
    }

    /**
     * 凭证规则明细
     */
    public String getFdDetail_Flag() {
        return this.fdDetail_Flag;
    }

    /**
     * 凭证规则明细
     */
    public void setFdDetail_Flag(String fdDetail_Flag) {
        this.fdDetail_Flag = fdDetail_Flag;
    }

    /**
     * 制单人
     */
    public String getFdOrderMakingPersonFlag() {
        return fdOrderMakingPersonFlag;
    }
    /**
     * 制单人
     */
    public void setFdOrderMakingPersonFlag(String fdOrderMakingPersonFlag) {
        this.fdOrderMakingPersonFlag = fdOrderMakingPersonFlag;
    }
    /**
     * 制单人
     */
    public String getFdOrderMakingPersonFormula() {
        return fdOrderMakingPersonFormula;
    }
    /**
     * 制单人
     */
    public void setFdOrderMakingPersonFormula(String fdOrderMakingPersonFormula) {
        this.fdOrderMakingPersonFormula = fdOrderMakingPersonFormula;
    }
    /**
     * 制单人
     */
    public String getFdOrderMakingPersonText() {
        return fdOrderMakingPersonText;
    }
    /**
     * 制单人
     */
    public void setFdOrderMakingPersonText(String fdOrderMakingPersonText) {
        this.fdOrderMakingPersonText = fdOrderMakingPersonText;
    }
    /**
     * 制单人
     */
    public String getFdOrderMakingPersonId() {
        return fdOrderMakingPersonId;
    }
    /**
     * 制单人
     */
    public void setFdOrderMakingPersonId(String fdOrderMakingPersonId) {
        this.fdOrderMakingPersonId = fdOrderMakingPersonId;
    }
    /**
     * 制单人
     */
    public String getFdOrderMakingPersonName() {
        return fdOrderMakingPersonName;
    }
    /**
     * 制单人
     */
    public void setFdOrderMakingPersonName(String fdOrderMakingPersonName) {
        this.fdOrderMakingPersonName = fdOrderMakingPersonName;
    }
    
    /**
     * 合并分录
     */
	public String getFdMergeEntry() {
		return fdMergeEntry;
	}
	/**
     * 合并分录
     */
	public void setFdMergeEntry(String fdMergeEntry) {
		this.fdMergeEntry = fdMergeEntry;
	}
}
