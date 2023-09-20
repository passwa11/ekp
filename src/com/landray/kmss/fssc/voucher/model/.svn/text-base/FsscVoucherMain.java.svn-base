package com.landray.kmss.fssc.voucher.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.sys.number.interfaces.ISysNumberModel;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.fssc.voucher.forms.FsscVoucherMainForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;

/**
  * 凭证
  */
public class FsscVoucherMain extends BaseModel implements ISysNumberModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Boolean fdIsAmortize;//是否是摊销凭证

    private String fdPushType;//推送方式

    private String fdVoucherCreateType;

    private String docFinanceNumber;

    private String docNumber;

    private String docSubject;

    private String fdModelId;

    private String fdModelName;

    private Double fdModelMoney;

    private String fdModelNumber;

    private Date fdVoucherDate;

    private String fdBookkeepingStatus;

    private String fdBookkeepingMessage;

    private Date fdBookkeepingDate;

    private SysOrgPerson fdBookkeepingPerson;

    private String fdAccountingYear;

    private String fdPeriod;

    private Integer fdNumber;

    private String fdVoucherText;

    private Date docCreateTime;

    private Date docAlterTime;

    private EopBasedataVoucherType fdBaseVoucherType;

    private EopBasedataCurrency fdBaseCurrency;

    private EopBasedataCompany fdCompany;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private List<FsscVoucherDetail> fdDetail;
    
    private String fdMergeEntry;//合并分录

    @Override
    public Class<FsscVoucherMainForm> getFormClass() {
        return FsscVoucherMainForm.class;
    }

    //编号机制
    private SysNumberMainMapp sysNumberMainMapp=new SysNumberMainMapp();
    @Override
    public SysNumberMainMapp getSysNumberMainMappModel() {
        return sysNumberMainMapp;
    }
    @Override
    public void setSysNumberMainMappModel(SysNumberMainMapp sysNumberMainMapp1) {
        this.sysNumberMainMapp=sysNumberMainMapp1;
    }


    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdVoucherDate", new ModelConvertor_Common("fdVoucherDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdBookkeepingDate", new ModelConvertor_Common("fdBookkeepingDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdBaseCurrency.fdName", "fdBaseCurrencyName");
            toFormPropertyMap.put("fdBaseCurrency.fdId", "fdBaseCurrencyId");
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
            toFormPropertyMap.put("fdCompany.fdCode", "fdCompanyCode");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("fdBookkeepingPerson.fdName", "fdBookkeepingPersonName");
            toFormPropertyMap.put("fdBookkeepingPerson.fdId", "fdBookkeepingPersonId");
            toFormPropertyMap.put("fdBaseVoucherType.fdName", "fdBaseVoucherTypeName");
            toFormPropertyMap.put("fdBaseVoucherType.fdId", "fdBaseVoucherTypeId");
            toFormPropertyMap.put("fdDetail", new ModelConvertor_ModelListToFormList("fdDetail_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public Boolean getFdIsAmortize() {
        return fdIsAmortize;
    }
    /**
     * 是否是摊销凭证
     */
    public void setFdIsAmortize(Boolean fdIsAmortize) {
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
    public Double getFdModelMoney() {
        return fdModelMoney;
    }
    /**
     * 来源单据总金额
     */
    public void setFdModelMoney(Double fdModelMoney) {
        this.fdModelMoney = fdModelMoney;
    }

    /**
     * 凭证类型
     */
    public EopBasedataVoucherType getFdBaseVoucherType() {
        return fdBaseVoucherType;
    }

    /**
     * 凭证类型
     */
    public void setFdBaseVoucherType(EopBasedataVoucherType fdBaseVoucherType) {
        this.fdBaseVoucherType = fdBaseVoucherType;
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
     * 凭证日期
     */
    public Date getFdVoucherDate() {
        return this.fdVoucherDate;
    }

    /**
     * 凭证日期
     */
    public void setFdVoucherDate(Date fdVoucherDate) {
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
    public Integer getFdNumber() {
        return this.fdNumber;
    }

    /**
     * 单据数
     */
    public void setFdNumber(Integer fdNumber) {
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
     * 凭证货币
     */
    public EopBasedataCurrency getFdBaseCurrency() {
        return this.fdBaseCurrency;
    }

    /**
     * 凭证货币
     */
    public void setFdBaseCurrency(EopBasedataCurrency fdBaseCurrency) {
        this.fdBaseCurrency = fdBaseCurrency;
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
     * 凭证明细
     */
    public List<FsscVoucherDetail> getFdDetail() {
        return this.fdDetail;
    }

    /**
     * 凭证明细
     */
    public void setFdDetail(List<FsscVoucherDetail> fdDetail) {
        this.fdDetail = fdDetail;
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
    public Date getFdBookkeepingDate() {
        return this.fdBookkeepingDate;
    }

    /**
     * 记账日期
     */
    public void setFdBookkeepingDate(Date fdBookkeepingDate) {
        this.fdBookkeepingDate = fdBookkeepingDate;
    }

    /**
     * 记账人
     */
    public SysOrgPerson getFdBookkeepingPerson() {
        return fdBookkeepingPerson;
    }
    /**
     * 记账人
     */
    public void setFdBookkeepingPerson(SysOrgPerson fdBookkeepingPerson) {
        this.fdBookkeepingPerson = fdBookkeepingPerson;
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
