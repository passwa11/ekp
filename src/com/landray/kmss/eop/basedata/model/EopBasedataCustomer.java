package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.forms.EopBasedataCustomerForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
  * 供应商客户信息表
  */
public class EopBasedataCustomer extends ExtendAuthTmpModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private SysOrgPerson fdCharge;

    private String fdTel;

    private String fdName;
    
    private Boolean fdIsAvailable;

    private String fdAbbreviation;

    private String fdCreditCode;

    private Date fdCodeValidityPeriod;

    private String fdIndustry;

    private String fdLegalPerson;

    private String fdRegistCapital;

    private Date fdEstablishDate;

    private String fdEmail;

    private String fdAddress;

    private String fdUrl;

    private String fdBusinessScope;

    private String fdDesc;

    private String fdBankAccountName;

    private String fdBankName;

    private String fdBankAccount;

    private String fdCode;

    private SysOrgPerson fdUser;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
    
    private String fdTaxNo;

    private String fdErpNo;
    
    private List<EopBasedataCustomerAccount> fdAccountList;
    
    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();

    private List<EopBasedataCustomerContact> fdContactPerson;

    @Override
    public Class<EopBasedataCustomerForm> getFormClass() {
        return EopBasedataCustomerForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdCodeValidityPeriod", new ModelConvertor_Common("fdCodeValidityPeriod").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdEstablishDate", new ModelConvertor_Common("fdEstablishDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdUser.fdName", "fdUserName");
            toFormPropertyMap.put("fdUser.fdId", "fdUserId");
            toFormPropertyMap.put("fdCharge.fdName", "fdChargeName");
            toFormPropertyMap.put("fdCharge.fdId", "fdChargeId");
            toFormPropertyMap.put("fdAccountList", new ModelConvertor_ModelListToFormList("fdAccountList_Form"));
            toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
            toFormPropertyMap.put("fdContactPerson", new ModelConvertor_ModelListToFormList("fdContactPerson_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 名称
     */
    @Override
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
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
     * 简称
     */
    public String getFdAbbreviation() {
        return this.fdAbbreviation;
    }

    /**
     * 简称
     */
    public void setFdAbbreviation(String fdAbbreviation) {
        this.fdAbbreviation = fdAbbreviation;
    }

    /**
     * 统一社会信用代码
     */
    public String getFdCreditCode() {
        return this.fdCreditCode;
    }

    /**
     * 统一社会信用代码
     */
    public void setFdCreditCode(String fdCreditCode) {
        this.fdCreditCode = fdCreditCode;
    }

    /**
     * 信用证有效截止日期
     */
    public Date getFdCodeValidityPeriod() {
        return this.fdCodeValidityPeriod;
    }

    /**
     * 信用证有效截止日期
     */
    public void setFdCodeValidityPeriod(Date fdCodeValidityPeriod) {
        this.fdCodeValidityPeriod = fdCodeValidityPeriod;
    }

    /**
     * 所属行业
     */
    public String getFdIndustry() {
        return this.fdIndustry;
    }

    /**
     * 所属行业
     */
    public void setFdIndustry(String fdIndustry) {
        this.fdIndustry = fdIndustry;
    }

    /**
     * 法人代表
     */
    public String getFdLegalPerson() {
        return this.fdLegalPerson;
    }

    /**
     * 法人代表
     */
    public void setFdLegalPerson(String fdLegalPerson) {
        this.fdLegalPerson = fdLegalPerson;
    }

    /**
     * 注册资金
     */
    public String getFdRegistCapital() {
        return this.fdRegistCapital;
    }

    /**
     * 注册资金
     */
    public void setFdRegistCapital(String fdRegistCapital) {
        this.fdRegistCapital = fdRegistCapital;
    }

    /**
     * 成立日期
     */
    public Date getFdEstablishDate() {
        return this.fdEstablishDate;
    }

    /**
     * 成立日期
     */
    public void setFdEstablishDate(Date fdEstablishDate) {
        this.fdEstablishDate = fdEstablishDate;
    }

    /**
     * 注册邮箱
     */
    public String getFdEmail() {
        return this.fdEmail;
    }

    /**
     * 注册邮箱
     */
    public void setFdEmail(String fdEmail) {
        this.fdEmail = fdEmail;
    }

    /**
     * 企业地址
     */
    public String getFdAddress() {
        return this.fdAddress;
    }

    /**
     * 企业地址
     */
    public void setFdAddress(String fdAddress) {
        this.fdAddress = fdAddress;
    }

    /**
     * 企业网址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * 企业网址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
    }

    /**
     * 经营范围
     */
    public String getFdBusinessScope() {
        return this.fdBusinessScope;
    }

    /**
     * 经营范围
     */
    public void setFdBusinessScope(String fdBusinessScope) {
        this.fdBusinessScope = fdBusinessScope;
    }

    /**
     * 企业简介
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 企业简介
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
    }

    /**
     * 账户名
     */
    public String getFdBankAccountName() {
        return this.fdBankAccountName;
    }

    /**
     * 账户名
     */
    public void setFdBankAccountName(String fdBankAccountName) {
        this.fdBankAccountName = fdBankAccountName;
    }

    /**
     * 开户行
     */
    public String getFdBankName() {
        return this.fdBankName;
    }

    /**
     * 开户行
     */
    public void setFdBankName(String fdBankName) {
        this.fdBankName = fdBankName;
    }

    /**
     * 银行账号
     */
    public String getFdBankAccount() {
        return this.fdBankAccount;
    }

    /**
     * 银行账号
     */
    public void setFdBankAccount(String fdBankAccount) {
        this.fdBankAccount = fdBankAccount;
    }

    /**
     * 编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 对应登陆人
     */
    public SysOrgPerson getFdUser() {
        return this.fdUser;
    }

    /**
     * 对应登陆人
     */
    public void setFdUser(SysOrgPerson fdUser) {
        this.fdUser = fdUser;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
    
    /**
     * 纳税人识别号
     */
    public String getFdTaxNo() {
        return this.fdTaxNo;
    }

    /**
     * 纳税人识别号
     */
    public void setFdTaxNo(String fdTaxNo) {
        this.fdTaxNo = fdTaxNo;
    }
    
    /**
     * ERP号
     */
    public String getFdErpNo() {
        return this.fdErpNo;
    }

    /**
     * ERP号
     */
    public void setFdErpNo(String fdErpNo) {
        this.fdErpNo = fdErpNo;
    }
    
    /**
     * 收款账户信息
     */
    public List<EopBasedataCustomerAccount> getFdAccountList() {
        return this.fdAccountList;
    }

    /**
     * 收款账户信息
     */
    public void setFdAccountList(List<EopBasedataCustomerAccount> fdAccountList) {
        this.fdAccountList = fdAccountList;
    }
    
    /**
     * 启用公司
     */
    public List<EopBasedataCompany> getFdCompanyList() {
        return this.fdCompanyList;
    }

    /**
     * 启用公司
     */
    public void setFdCompanyList(List<EopBasedataCompany> fdCompanyList) {
        this.fdCompanyList = fdCompanyList;
    }

    /**
     * 负责人
     */
    public SysOrgPerson getFdCharge() {
        return fdCharge;
    }

    public void setFdCharge(SysOrgPerson fdCharge) {
        this.fdCharge = fdCharge;
    }

    /**
     * 电话
     */
    public String getFdTel() {
        return fdTel;
    }

    public void setFdTel(String fdTel) {
        this.fdTel = fdTel;
    }

    public List<EopBasedataCustomerContact> getFdContactPerson() {
        return fdContactPerson;
    }

    public void setFdContactPerson(List<EopBasedataCustomerContact> fdContactPerson) {
        this.fdContactPerson = fdContactPerson;
    }
}
