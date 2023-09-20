package com.landray.kmss.fssc.voucher.model;

import java.util.List;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.fssc.voucher.forms.FsscVoucherRuleConfigForm;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;

/**
  * 凭证规则设置
  */
public class FsscVoucherRuleConfig extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdCategoryId;

    private String fdCategoryModelName;

    private String fdCategoryName;

    private String fdPushType;//推送方式

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

    private String fdOrderMakingPersonFlag;

    private String fdOrderMakingPersonFormula;

    private String fdOrderMakingPersonText;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private Date docAlterTime;

    private FsscVoucherModelConfig fdVoucherModelConfig;

    private EopBasedataVoucherType fdVoucherType;

    private EopBasedataCompany fdCompany;

    private EopBasedataCurrency fdCurrency;

    private SysOrgPerson fdOrderMakingPerson;//制单人

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private List<FsscVoucherRuleDetail> fdDetail;
    
    private String fdMergeEntry;//合并分录

    @Override
    public Class<FsscVoucherRuleConfigForm> getFormClass() {
        return FsscVoucherRuleConfigForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdVoucherModelConfig.fdModelName", "fdVoucherModelConfigModelName");
            toFormPropertyMap.put("fdVoucherModelConfig.fdName", "fdVoucherModelConfigName");
            toFormPropertyMap.put("fdVoucherModelConfig.fdId", "fdVoucherModelConfigId");
            toFormPropertyMap.put("fdVoucherType.fdName", "fdVoucherTypeName");
            toFormPropertyMap.put("fdVoucherType.fdId", "fdVoucherTypeId");
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
            toFormPropertyMap.put("fdCurrency.fdName", "fdCurrencyName");
            toFormPropertyMap.put("fdCurrency.fdId", "fdCurrencyId");
            toFormPropertyMap.put("fdOrderMakingPerson.fdName", "fdOrderMakingPersonName");
            toFormPropertyMap.put("fdOrderMakingPerson.fdId", "fdOrderMakingPersonId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("fdDetail", new ModelConvertor_ModelListToFormList("fdDetail_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public Boolean getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 凭证模板设置
     */
    public FsscVoucherModelConfig getFdVoucherModelConfig() {
        return this.fdVoucherModelConfig;
    }

    /**
     * 凭证模板设置
     */
    public void setFdVoucherModelConfig(FsscVoucherModelConfig fdVoucherModelConfig) {
        this.fdVoucherModelConfig = fdVoucherModelConfig;
    }

    /**
     * 凭证类型
     */
    public EopBasedataVoucherType getFdVoucherType() {
        return this.fdVoucherType;
    }

    /**
     * 凭证类型
     */
    public void setFdVoucherType(EopBasedataVoucherType fdVoucherType) {
        this.fdVoucherType = fdVoucherType;
    }

    /**
     * 公司
     */
    public EopBasedataCompany getFdCompany() {
        return this.fdCompany;
    }

    /**
     * 公司
     */
    public void setFdCompany(EopBasedataCompany fdCompany) {
        this.fdCompany = fdCompany;
    }

    /**
     * 凭证货币
     */
    public EopBasedataCurrency getFdCurrency() {
        return this.fdCurrency;
    }

    /**
     * 凭证货币
     */
    public void setFdCurrency(EopBasedataCurrency fdCurrency) {
        this.fdCurrency = fdCurrency;
    }

    /**
     * 创建人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 修改人
     */
    public SysOrgPerson getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * 修改人
     */
    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
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
     * 凭证规则明细
     */
    public List<FsscVoucherRuleDetail> getFdDetail() {
        return this.fdDetail;
    }

    /**
     * 凭证规则明细
     */
    public void setFdDetail(List<FsscVoucherRuleDetail> fdDetail) {
        this.fdDetail = fdDetail;
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
    public SysOrgPerson getFdOrderMakingPerson() {
        return fdOrderMakingPerson;
    }
    /**
     * 制单人
     */
    public void setFdOrderMakingPerson(SysOrgPerson fdOrderMakingPerson) {
        this.fdOrderMakingPerson = fdOrderMakingPerson;
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
