package com.landray.kmss.fssc.voucher.forms;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.fssc.voucher.model.FsscVoucherMain;
import com.landray.kmss.sys.number.forms.SysNumberMainMappForm;
import com.landray.kmss.sys.number.interfaces.ISysNumberForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 凭证
  */
public class FsscVoucherMainForm extends ExtendForm implements ISysNumberForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdIsAmortize;

    private String fdPushType;

    private String fdVoucherCreateType;

    private String docFinanceNumber;

    private String docNumber;

    private String docSubject;

    private String fdModelId;

    private String fdModelName;

    private String fdModelNumber;

    private String fdModelMoney;

    private String fdModelUrl;

    private String fdVoucherDate;

    private String fdBookkeepingStatus;

    private String fdBookkeepingDate;

    private String fdBookkeepingMessage;

    private String fdBookkeepingPersonId;

    private String fdBookkeepingPersonName;

    private String fdAccountingYear;

    private String fdPeriod;

    private String fdNumber;

    private String fdVoucherText;

    private String docCreateTime;

    private String docAlterTime;

    private String fdBaseCurrencyId;

    private String fdBaseCurrencyName;

    private String fdCompanyId;

    private String fdCompanyName;

    private String fdCompanyCode;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private String fdBaseVoucherTypeId;

    private String fdBaseVoucherTypeName;

    private AutoArrayList fdDetail_Form = new AutoArrayList(FsscVoucherDetailForm.class);

    private String fdDetail_Flag;
    //编号机制
    private SysNumberMainMappForm sysNumberMainMappForm = new SysNumberMainMappForm();
    @Override
    public SysNumberMainMappForm getSysNumberMainMappForm() {
        return sysNumberMainMappForm;
    }
    @Override
    public void setSysNumberMainMappForm(SysNumberMainMappForm frm) {
        sysNumberMainMappForm=frm;
    }

    private String fdMergeEntry;//合并分录

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdIsAmortize = null;
        fdPushType = null;
        fdVoucherCreateType = null;
        docFinanceNumber = null;
        docNumber = null;
        docSubject = null;
        fdModelId = null;
        fdModelName = null;
        fdModelNumber = null;
        fdModelMoney = null;
        fdModelUrl = null;
        fdVoucherDate = null;
        fdAccountingYear = null;
        fdPeriod = null;
        fdNumber = null;
        fdVoucherText = null;
        docCreateTime = null;
        docAlterTime = null;
        fdBaseCurrencyId = null;
        fdBaseCurrencyName = null;
        fdCompanyId = null;
        fdCompanyName = null;
        fdCompanyCode = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdBaseVoucherTypeId = null;
        fdBaseVoucherTypeName = null;
        fdBookkeepingStatus = null;
        fdBookkeepingDate = null;
        fdBookkeepingMessage = null;
        fdBookkeepingPersonId = null;
        fdBookkeepingPersonName = null;
        fdDetail_Form = new AutoArrayList(FsscVoucherDetailForm.class);
        fdDetail_Flag = null;
        fdMergeEntry = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscVoucherMain> getModelClass() {
        return FsscVoucherMain.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("fdVoucherCreateType");
            toModelPropertyMap.addNoConvertProperty("docFinanceNumber");
            toModelPropertyMap.addNoConvertProperty("docNumber");
            toModelPropertyMap.addNoConvertProperty("docSubject");
            toModelPropertyMap.addNoConvertProperty("fdBookkeepingDate");
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdVoucherDate", new FormConvertor_Common("fdVoucherDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdBookkeepingPersonId", new FormConvertor_IDToModel("fdBookkeepingPerson", SysOrgPerson.class));
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("fdBaseCurrencyId", new FormConvertor_IDToModel("fdBaseCurrency", EopBasedataCurrency.class));
            toModelPropertyMap.put("fdBaseVoucherTypeId", new FormConvertor_IDToModel("fdBaseVoucherType", EopBasedataVoucherType.class));
            toModelPropertyMap.put("fdDetail_Form", new FormConvertor_FormListToModelList("fdDetail", "docMain", "fdDetail_Flag"));
        }
        return toModelPropertyMap;
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
     * 是否是摊销凭证
     */
    public String getFdIsAmortize() {
        return fdIsAmortize;
    }
    /**
     * 是否是摊销凭证
     */
    public void setFdIsAmortize(String fdIsAmortize) {
        this.fdIsAmortize = fdIsAmortize;
    }
    /**
     * 凭证生成类型
     */
    public String getFdVoucherCreateType() {
        return fdVoucherCreateType;
    }
    /**
     * 凭证生成类型
     */
    public void setFdVoucherCreateType(String fdVoucherCreateType) {
        this.fdVoucherCreateType = fdVoucherCreateType;
    }

    /**
     * 财务凭证号
     */
    public String getDocFinanceNumber() {
        return this.docFinanceNumber;
    }

    /**
     * 财务凭证号
     */
    public void setDocFinanceNumber(String docFinanceNumber) {
        this.docFinanceNumber = docFinanceNumber;
    }

    /**
     * 费控凭证号
     */
    public String getDocNumber() {
        return this.docNumber;
    }

    /**
     * 费控凭证号
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    /**
     * 标题
     */
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
     * 来源单据id
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 来源单据id
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 来源单据name
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 来源单据name
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 来源单据编号
     */
    public String getFdModelNumber() {
        return this.fdModelNumber;
    }

    /**
     * 来源单据编号
     */
    public void setFdModelNumber(String fdModelNumber) {
        this.fdModelNumber = fdModelNumber;
    }

    /**
     * 来源单据总金额
     */
    public String getFdModelMoney() {
        return this.fdModelMoney;
    }

    /**
     * 来源单据总金额
     */
    public void setFdModelMoney(String fdModelMoney) {
        this.fdModelMoney = fdModelMoney;
    }

    /**
     * fdModelUrl
     */
    public String getFdModelUrl() {
        return this.fdModelUrl;
    }

    /**
     * fdModelUrl
     */
    public void setFdModelUrl(String fdModelUrl) {
        this.fdModelUrl = fdModelUrl;
    }

    /**
     * 凭证类型
     */
    public String getFdBaseVoucherTypeId() {
        return this.fdBaseVoucherTypeId;
    }

    /**
     * 凭证类型
     */
    public void setFdBaseVoucherTypeId(String fdBaseVoucherTypeId) {
        this.fdBaseVoucherTypeId = fdBaseVoucherTypeId;
    }

    /**
     * 凭证类型
     */
    public String getFdBaseVoucherTypeName() {
        return this.fdBaseVoucherTypeName;
    }

    /**
     * 凭证类型
     */
    public void setFdBaseVoucherTypeName(String fdBaseVoucherTypeName) {
        this.fdBaseVoucherTypeName = fdBaseVoucherTypeName;
    }

    /**
     * 凭证日期
     */
    public String getFdVoucherDate() {
        return this.fdVoucherDate;
    }

    /**
     * 凭证日期
     */
    public void setFdVoucherDate(String fdVoucherDate) {
        this.fdVoucherDate = fdVoucherDate;
    }

    /**
     * 会计年度
     */
    public String getFdAccountingYear() {
        return this.fdAccountingYear;
    }

    /**
     * 会计年度
     */
    public void setFdAccountingYear(String fdAccountingYear) {
        this.fdAccountingYear = fdAccountingYear;
    }

    /**
     * 期间
     */
    public String getFdPeriod() {
        return this.fdPeriod;
    }

    /**
     * 期间
     */
    public void setFdPeriod(String fdPeriod) {
        this.fdPeriod = fdPeriod;
    }

    /**
     * 单据数
     */
    public String getFdNumber() {
        return this.fdNumber;
    }

    /**
     * 单据数
     */
    public void setFdNumber(String fdNumber) {
        this.fdNumber = fdNumber;
    }

    /**
     * 凭证抬头文本
     */
    public String getFdVoucherText() {
        return this.fdVoucherText;
    }

    /**
     * 凭证抬头文本
     */
    public void setFdVoucherText(String fdVoucherText) {
        this.fdVoucherText = fdVoucherText;
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
     * 凭证货币
     */
    public String getFdBaseCurrencyId() {
        return this.fdBaseCurrencyId;
    }

    /**
     * 凭证货币
     */
    public void setFdBaseCurrencyId(String fdBaseCurrencyId) {
        this.fdBaseCurrencyId = fdBaseCurrencyId;
    }

    /**
     * 凭证货币
     */
    public String getFdBaseCurrencyName() {
        return this.fdBaseCurrencyName;
    }

    /**
     * 凭证货币
     */
    public void setFdBaseCurrencyName(String fdBaseCurrencyName) {
        this.fdBaseCurrencyName = fdBaseCurrencyName;
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
     * 公司
     */
    public String getFdCompanyCode() {
        return this.fdCompanyCode;
    }

    /**
     * 公司
     */
    public void setFdCompanyCode(String fdCompanyCode) {
        this.fdCompanyCode = fdCompanyCode;
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
     * 凭证明细
     */
    public AutoArrayList getFdDetail_Form() {
        return this.fdDetail_Form;
    }

    /**
     * 凭证明细
     */
    public void setFdDetail_Form(AutoArrayList fdDetail_Form) {
        this.fdDetail_Form = fdDetail_Form;
    }

    /**
     * 凭证明细
     */
    public String getFdDetail_Flag() {
        return this.fdDetail_Flag;
    }

    /**
     * 凭证明细
     */
    public void setFdDetail_Flag(String fdDetail_Flag) {
        this.fdDetail_Flag = fdDetail_Flag;
    }

    /**
     * 记账状态
     */
    public String getFdBookkeepingStatus() {
        return fdBookkeepingStatus;
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
        return fdBookkeepingMessage;
    }
    /**
     * 记账失败原因
     */
    public void setFdBookkeepingMessage(String fdBookkeepingMessage) {
        this.fdBookkeepingMessage = fdBookkeepingMessage;
    }

    /**
     * 记账日期
     */
    public String getFdBookkeepingDate() {
        return this.fdBookkeepingDate;
    }

    /**
     * 记账日期
     */
    public void setFdBookkeepingDate(String fdBookkeepingDate) {
        this.fdBookkeepingDate = fdBookkeepingDate;
    }
    /**
     * 记账人
     */
    public String getFdBookkeepingPersonId() {
        return fdBookkeepingPersonId;
    }
    /**
     * 记账人
     */
    public void setFdBookkeepingPersonId(String fdBookkeepingPersonId) {
        this.fdBookkeepingPersonId = fdBookkeepingPersonId;
    }
    /**
     * 记账人
     */
    public String getFdBookkeepingPersonName() {
        return fdBookkeepingPersonName;
    }
    /**
     * 记账人
     */
    public void setFdBookkeepingPersonName(String fdBookkeepingPersonName) {
        this.fdBookkeepingPersonName = fdBookkeepingPersonName;
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
