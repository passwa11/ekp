package com.landray.kmss.eop.basedata.forms;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

import javax.servlet.http.HttpServletRequest;

/**
  * 供应商客户信息表
  */
public class EopBasedataSupplierForm extends ExtendAuthTmpForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;
    
    private String fdIsAvailable;

    private String docCreateTime;

    private String fdAbbreviation;

    private String fdCreditCode;

    private String fdCodeValidityPeriod;

    private String fdIndustry;

    private String fdLegalPerson;

    private String fdRegistCapital;

    private String fdEstablishDate;

    private String fdEmail;

    private String fdAddress;

    private String fdUrl;

    private String fdBusinessScope;

    private String fdDesc;

    private String fdBankAccountName;

    private String fdBankName;

    private String fdBankAccount;

    private String fdCode;

    private String docCreatorId;

    private String docCreatorName;

    private AutoArrayList fdContactPerson_Form = new AutoArrayList(EopBasedataContactForm.class);

    private String fdContactPerson_Flag = "0";
    
    private String fdTaxNo;
    
    private String fdErpNo;
    
    private AutoArrayList fdAccountList_Form = new AutoArrayList(EopBasedataSupplierAccountForm.class);
    
    private String fdAccountList_Flag = "0";
    
    private FormFile fdFile;
    
    private String fdCompanyListIds;

    private String fdCompanyListNames;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdName = null;
        fdIsAvailable=null;
        docCreateTime = null;
        fdAbbreviation = null;
        fdCreditCode = null;
        fdCodeValidityPeriod = null;
        fdIndustry = null;
        fdLegalPerson = null;
        fdRegistCapital = null;
        fdEstablishDate = null;
        fdEmail = null;
        fdAddress = null;
        fdUrl = null;
        fdBusinessScope = null;
        fdDesc = null;
        fdBankAccountName = null;
        fdBankName = null;
        fdBankAccount = null;
        fdCode = null;
        docCreatorId = null;
        docCreatorName = null;
        fdContactPerson_Form = new AutoArrayList(EopBasedataContactForm.class);
        fdContactPerson_Flag = null;
        fdTaxNo = null;
        fdErpNo = null;
        fdAccountList_Form = new AutoArrayList(EopBasedataSupplierAccountForm.class);
        fdAccountList_Flag = null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataSupplier> getModelClass() {
        return EopBasedataSupplier.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdCodeValidityPeriod", new FormConvertor_Common("fdCodeValidityPeriod").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdEstablishDate", new FormConvertor_Common("fdEstablishDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdContactPerson_Form", new FormConvertor_FormListToModelList("fdContactPerson", "docMain", "fdContactPerson_Flag"));
            toModelPropertyMap.put("fdAccountList_Form", new FormConvertor_FormListToModelList("fdAccountList", "docMain", "fdAccountList_Flag"));
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 名称
     */
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
    public String getFdCodeValidityPeriod() {
        return this.fdCodeValidityPeriod;
    }

    /**
     * 信用证有效截止日期
     */
    public void setFdCodeValidityPeriod(String fdCodeValidityPeriod) {
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
    public String getFdEstablishDate() {
        return this.fdEstablishDate;
    }

    /**
     * 成立日期
     */
    public void setFdEstablishDate(String fdEstablishDate) {
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
     * 联系人
     */
    public AutoArrayList getFdContactPerson_Form() {
        return this.fdContactPerson_Form;
    }

    /**
     * 联系人
     */
    public void setFdContactPerson_Form(AutoArrayList fdContactPerson_Form) {
        this.fdContactPerson_Form = fdContactPerson_Form;
    }

    /**
     * 联系人
     */
    public String getFdContactPerson_Flag() {
        return this.fdContactPerson_Flag;
    }

    /**
     * 联系人
     */
    public void setFdContactPerson_Flag(String fdContactPerson_Flag) {
        this.fdContactPerson_Flag = fdContactPerson_Flag;
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
    public AutoArrayList getFdAccountList_Form() {
        return this.fdAccountList_Form;
    }

    /**
     * 收款账户信息
     */
    public void setFdAccountList_Form(AutoArrayList fdAccountList_Form) {
        this.fdAccountList_Form = fdAccountList_Form;
    }
    
    /**
     * 收款账户信息
     */
    public String getFdAccountList_Flag() {
        return this.fdAccountList_Flag;
    }

    /**
     * 收款账户信息
     */
    public void setFdAccountList_Flag(String fdAccountList_Flag) {
        this.fdAccountList_Flag = fdAccountList_Flag;
    }
    
    public FormFile getFdFile() {
		return fdFile;
	}

	public void setFdFile(FormFile fdFile) {
		this.fdFile = fdFile;
	}
	
	/**
     * 启用公司
     */
    public String getFdCompanyListIds() {
        return this.fdCompanyListIds;
    }

    /**
     * 启用公司
     */
    public void setFdCompanyListIds(String fdCompanyListIds) {
        this.fdCompanyListIds = fdCompanyListIds;
    }

    /**
     * 启用公司
     */
    public String getFdCompanyListNames() {
        return this.fdCompanyListNames;
    }

    /**
     * 启用公司
     */
    public void setFdCompanyListNames(String fdCompanyListNames) {
        this.fdCompanyListNames = fdCompanyListNames;
    }
}
