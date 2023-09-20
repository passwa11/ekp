package com.landray.kmss.eop.basedata.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 公司
  */
public class EopBasedataCompanyForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private String fdType;

    private String fdName;

    private String fdCode;

    private String fdIsAvailable;

    private String fdJoinSystem;

	private String fdCabinetType;	//收单柜品牌

	private String fdIden;

	private String regAddress;

	private String legalRepresentative;

	private String legalRepresentativeIden;

	private String contactorMobileNo;

	private String contactorEmail;
    
    private String fdUEightUrl;
    
    private String fdKUrl;
    
    private String fdKUserName;
    
    private String fdKPassWord;
    
    private String fdEUserName;	//Eas
    
    private String fdEPassWord;
    
    private String fdESlnName;
    
    private String fdEDcName;
    
    private String fdELanguage;
    
    private String fdEDbType;
    
    private String fdEAuthPattern;
    
    private String fdELoginWsdlUrl;
    
    private String fdEImportVoucherWsdlUrl;

	private String fdSystemParam;

    private String docCreateTime;

    private String docAlterTime;

    private String fdDutyParagraph;//税号

    private String fdBudgetCurrencyId;

    private String fdBudgetCurrencyName;

    private String fdAccountCurrencyId;

    private String fdAccountCurrencyName;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

	private String contactorId;

	private String contactorName;

    private String fdGroupId;

    private String fdGroupName;

    private String fdEkpOrgIds;

    private String fdEkpOrgNames;

    private String fdFinancialStaffIds;

    private String fdFinancialStaffNames;

    private String fdFinancialManagerIds;

    private String fdFinancialManagerNames;
    
    private String fdK3cUrl;

    private String fdK3cId;

    private String fdK3cPersonName;

    private String fdK3cPassword;

    private String fdK3cIcid;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdCode = null;
        fdIsAvailable = null;
        fdJoinSystem = null;
		fdCabinetType= null;
		fdIden = null;
		regAddress = null;
		legalRepresentative = null;
		legalRepresentativeIden = null;
		contactorMobileNo = null;
		contactorEmail = null;
        fdUEightUrl= null;
        fdKUrl= null;
        fdKUserName= null;
        fdKPassWord= null;
        fdEUserName = null;
        fdEPassWord = null;
        fdESlnName = null;
        fdEDcName = null;
        fdELanguage = null;
        fdEDbType = null;
        fdEAuthPattern = null;
        fdELoginWsdlUrl = null;
        fdEImportVoucherWsdlUrl = null;
		fdSystemParam = null;
        docCreateTime = null;
        docAlterTime = null;
        fdBudgetCurrencyId = null;
        fdBudgetCurrencyName = null;
        fdAccountCurrencyId = null;
        fdAccountCurrencyName = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
		contactorId = null;
		contactorName = null;
        fdGroupId = null;
        fdGroupName = null;
        fdEkpOrgIds = null;
        fdEkpOrgNames = null;
        fdFinancialStaffIds = null;
        fdFinancialStaffNames = null;
        fdFinancialManagerIds = null;
        fdFinancialManagerNames = null;
        fdDutyParagraph = null;
        fdK3cUrl = null;
        fdK3cId = null;
        fdK3cPersonName = null;
        fdK3cPassword = null;
        fdK3cIcid = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataCompany> getModelClass() {
        return EopBasedataCompany.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdBudgetCurrencyId", new FormConvertor_IDToModel("fdBudgetCurrency", EopBasedataCurrency.class));
            toModelPropertyMap.put("fdAccountCurrencyId", new FormConvertor_IDToModel("fdAccountCurrency", EopBasedataCurrency.class));
            toModelPropertyMap.put("fdGroupId", new FormConvertor_IDToModel("fdGroup", EopBasedataCompanyGroup.class));
            toModelPropertyMap.put("fdEkpOrgIds", new FormConvertor_IDsToModelList("fdEkpOrg", SysOrgElement.class));
            toModelPropertyMap.put("fdFinancialStaffIds", new FormConvertor_IDsToModelList("fdFinancialStaff", SysOrgPerson.class));
            toModelPropertyMap.put("fdFinancialManagerIds", new FormConvertor_IDsToModelList("fdFinancialManager", SysOrgPerson.class));
			toModelPropertyMap.put("contactorId", new FormConvertor_IDToModel("contactor", SysOrgPerson.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 公司名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 公司名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
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
     * 对接财务系统
     */
    public String getFdJoinSystem() {
        return this.fdJoinSystem;
    }

    /**
     * 对接财务系统
     */
    public void setFdJoinSystem(String fdJoinSystem) {
        this.fdJoinSystem = fdJoinSystem;
    }

	/**
	 * 收单柜品牌
	 * @return
	 */
	public String getFdCabinetType() {
		return this.fdCabinetType;
	}

	/**
	 * 收单柜品牌
	 */
	public void setFdCabinetType(String fdCabinetType) {
		this.fdCabinetType = fdCabinetType;
	}
    
	/**
	 * 统一信用代码
	 */
	public String getFdIden() {
		return this.fdIden;
	}

	/**
	 * 统一信用代码
	 */
	public void setFdIden(String fdIden) {
		this.fdIden = fdIden;
	}

	/**
	 * 注册地址
	 */
	public String getRegAddress() {
		return this.regAddress;
	}

	/**
	 * 注册地址
	 */
	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	/**
	 * 法定代表人
	 */
	public String getLegalRepresentative() {
		return this.legalRepresentative;
	}

	/**
	 * 法定代表人
	 */
	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	/**
	 * 法定代表人证件号
	 */
	public String getLegalRepresentativeIden() {
		return this.legalRepresentativeIden;
	}

	/**
	 * 法定代表人证件号
	 */
	public void setLegalRepresentativeIden(String legalRepresentativeIden) {
		this.legalRepresentativeIden = legalRepresentativeIden;
	}

    /**
	 * 联系人手机号
	 */
	public String getContactorMobileNo() {
		return this.contactorMobileNo;
	}

	/**
	 * 联系人手机号
	 */
	public void setContactorMobileNo(String contactorMobileNo) {
		this.contactorMobileNo = contactorMobileNo;
	}

	/**
	 * 联系人邮箱
	 */
	public String getContactorEmail() {
		return this.contactorEmail;
	}

	/**
	 * 联系人邮箱
	 */
	public void setContactorEmail(String contactorEmail) {
		this.contactorEmail = contactorEmail;
	}

	/**
	 * U8路径
	 */
	public String getFdUEightUrl() {
		return fdUEightUrl;
	}
	 /**
     * U8路径
     */
	public void setFdUEightUrl(String fdUEightUrl) {
		this.fdUEightUrl = fdUEightUrl;
	}
	/**
	 * K3路径
	 * @return
	 */
	public String getFdKUrl() {
		return fdKUrl;
	}
	/**
	 * K3路径
	 * 
	 */
	public void setFdKUrl(String fdKUrl) {
		this.fdKUrl = fdKUrl;
	}
	/**
	 * K3用户名称
	 * @return
	 */
	public String getFdKUserName() {
		return fdKUserName;
	}
	/**
	 * K3用户名称
	 * 
	 */
	public void setFdKUserName(String fdKUserName) {
		this.fdKUserName = fdKUserName;
	}
	/**
	 * K3用户密码
	 * @return
	 */
	public String getFdKPassWord() {
		return fdKPassWord;
	}
	/**
	 * K3用户密码
	 * 
	 */
	public void setFdKPassWord(String fdKPassWord) {
		this.fdKPassWord = fdKPassWord;
	}
	
	/**
	 * Eas用户名
	 * @return
	 */
	public String getFdEUserName() {
		return fdEUserName;
	}
	
	/**
	 * Eas用户名
	 * @param fdEUserName
	 */
	public void setFdEUserName(String fdEUserName) {
		this.fdEUserName = fdEUserName;
	}
	
	/**
	 * Eas密码
	 * @return
	 */
	public String getFdEPassWord() {
		return fdEPassWord;
	}
	
	/**
	 * Eas密码
	 * @param fdEPassWord
	 */
	public void setFdEPassWord(String fdEPassWord) {
		this.fdEPassWord = fdEPassWord;
	}
	
	/**
	 * Eas解决方案
	 * @return
	 */
	public String getFdESlnName() {
		return fdESlnName;
	}
	
	/**
	 * Eas解决方案
	 * @param fdESlnName
	 */
	public void setFdESlnName(String fdESlnName) {
		this.fdESlnName = fdESlnName;
	}
	
	/**
	 * Eas数据中心
	 * @return
	 */
	public String getFdEDcName() {
		return fdEDcName;
	}
	
	/**
	 * Eas数据中心
	 * @param fdEDcName
	 */
	public void setFdEDcName(String fdEDcName) {
		this.fdEDcName = fdEDcName;
	}
	
	/**
	 * Eas语言
	 * @return
	 */
	public String getFdELanguage() {
		return fdELanguage;
	}
	
	/**
	 * Eas语言
	 * @param fdELanguage
	 */
	public void setFdELanguage(String fdELanguage) {
		this.fdELanguage = fdELanguage;
	}
	
	/**
	 * Eas数据库类型
	 */
	public String getFdEDbType() {
		return fdEDbType;
	}
	
	/**
	 * Eas数据库类型
	 * @param fdEDbType
	 */
	public void setFdEDbType(String fdEDbType) {
		this.fdEDbType = fdEDbType;
	}
	
	/**
	 * Eas验证类型
	 * @return
	 */
	public String getFdEAuthPattern() {
		return fdEAuthPattern;
	}
	
	/**
	 * Eas验证类型
	 * @param fdEAuthPattern
	 */
	public void setFdEAuthPattern(String fdEAuthPattern) {
		this.fdEAuthPattern = fdEAuthPattern;
	}
	
	/**
	 * Eas登录验证wsdl路径
	 * @return
	 */
	public String getFdELoginWsdlUrl() {
		return fdELoginWsdlUrl;
	}
	
	/**
	 * Eas登录验证wsdl路径
	 * @param fdELoginWsdlUrl
	 */
	public void setFdELoginWsdlUrl(String fdELoginWsdlUrl) {
		this.fdELoginWsdlUrl = fdELoginWsdlUrl;
	}
	
	/**
	 * Eas传输凭证wsdl路径
	 * @return
	 */
	public String getFdEImportVoucherWsdlUrl() {
		return fdEImportVoucherWsdlUrl;
	}
	
	/**
	 * Eas传输凭证wsdl路径
	 * @param fdEImportVoucherWsdlUrl
	 */
	public void setFdEImportVoucherWsdlUrl(String fdEImportVoucherWsdlUrl) {
		this.fdEImportVoucherWsdlUrl = fdEImportVoucherWsdlUrl;
	}

	/**
	 * 对接财务系统参数
	 */

	public String getFdSystemParam() {
		return fdSystemParam;
	}

	/**
	 * 对接财务系统参数
	 */
	public void setFdSystemParam(String fdSystemParam) {
		this.fdSystemParam = fdSystemParam;
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
     * 预算币种
     */
    public String getFdBudgetCurrencyId() {
        return this.fdBudgetCurrencyId;
    }

    /**
     * 预算币种
     */
    public void setFdBudgetCurrencyId(String fdBudgetCurrencyId) {
        this.fdBudgetCurrencyId = fdBudgetCurrencyId;
    }

    /**
     * 预算币种
     */
    public String getFdBudgetCurrencyName() {
        return this.fdBudgetCurrencyName;
    }

    /**
     * 预算币种
     */
    public void setFdBudgetCurrencyName(String fdBudgetCurrencyName) {
        this.fdBudgetCurrencyName = fdBudgetCurrencyName;
    }

    /**
     * 本位币
     */
    public String getFdAccountCurrencyId() {
        return this.fdAccountCurrencyId;
    }

    /**
     * 本位币
     */
    public void setFdAccountCurrencyId(String fdAccountCurrencyId) {
        this.fdAccountCurrencyId = fdAccountCurrencyId;
    }

    /**
     * 本位币
     */
    public String getFdAccountCurrencyName() {
        return this.fdAccountCurrencyName;
    }

    /**
     * 本位币
     */
    public void setFdAccountCurrencyName(String fdAccountCurrencyName) {
        this.fdAccountCurrencyName = fdAccountCurrencyName;
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
	 * 联系人
	 */
	public String getContactorId() {
		return this.contactorId;
	}

	/**
	 * 联系人
	 */
	public void setContactorId(String contactorId) {
		this.contactorId = contactorId;
	}

	/**
	 * 联系人
	 */
	public String getContactorName() {
		return this.contactorName;
	}

	/**
	 * 联系人
	 */
	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}

	/**
	 * 所属公司组
	 */
    public String getFdGroupId() {
        return this.fdGroupId;
    }

    /**
     * 所属公司组
     */
    public void setFdGroupId(String fdGroupId) {
        this.fdGroupId = fdGroupId;
    }

    /**
     * 所属公司组
     */
    public String getFdGroupName() {
        return this.fdGroupName;
    }

    /**
     * 所属公司组
     */
    public void setFdGroupName(String fdGroupName) {
        this.fdGroupName = fdGroupName;
    }

    /**
     * 对应EKP机构/部门
     */
    public String getFdEkpOrgIds() {
        return this.fdEkpOrgIds;
    }

    /**
     * 对应EKP机构/部门
     */
    public void setFdEkpOrgIds(String fdEkpOrgIds) {
        this.fdEkpOrgIds = fdEkpOrgIds;
    }

    /**
     * 对应EKP机构/部门
     */
    public String getFdEkpOrgNames() {
        return this.fdEkpOrgNames;
    }

    /**
     * 对应EKP机构/部门
     */
    public void setFdEkpOrgNames(String fdEkpOrgNames) {
        this.fdEkpOrgNames = fdEkpOrgNames;
    }

    /**
     * 财务人员
     */
    public String getFdFinancialStaffIds() {
        return this.fdFinancialStaffIds;
    }

    /**
     * 财务人员
     */
    public void setFdFinancialStaffIds(String fdFinancialStaffIds) {
        this.fdFinancialStaffIds = fdFinancialStaffIds;
    }

    /**
     * 财务人员
     */
    public String getFdFinancialStaffNames() {
        return this.fdFinancialStaffNames;
    }

    /**
     * 财务人员
     */
    public void setFdFinancialStaffNames(String fdFinancialStaffNames) {
        this.fdFinancialStaffNames = fdFinancialStaffNames;
    }

    /**
     * 财务管理员
     */
    public String getFdFinancialManagerIds() {
        return this.fdFinancialManagerIds;
    }

    /**
     * 财务管理员
     */
    public void setFdFinancialManagerIds(String fdFinancialManagerIds) {
        this.fdFinancialManagerIds = fdFinancialManagerIds;
    }

    /**
     * 财务管理员
     */
    public String getFdFinancialManagerNames() {
        return this.fdFinancialManagerNames;
    }

    /**
     * 财务管理员
     */
    public void setFdFinancialManagerNames(String fdFinancialManagerNames) {
        this.fdFinancialManagerNames = fdFinancialManagerNames;
    }

    /**
     * 税号
     */
    public String getFdDutyParagraph() {
        return fdDutyParagraph;
    }
    /**
     * 税号
     */
    public void setFdDutyParagraph(String fdDutyParagraph) {
        this.fdDutyParagraph = fdDutyParagraph;
    }
	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}
	
	/**
     * 站点地址
     */
    public String getFdK3cUrl() {
        return this.fdK3cUrl;
    }

    /**
     * 站点地址
     */
    public void setFdK3cUrl(String fdK3cUrl) {
        this.fdK3cUrl = fdK3cUrl;
    }

    /**
     * 账套id
     */
    public String getFdK3cId() {
        return this.fdK3cId;
    }

    /**
     * 账套id
     */
    public void setFdK3cId(String fdK3cId) {
        this.fdK3cId = fdK3cId;
    }

    /**
     * K3C用户名
     */
    public String getFdK3cPersonName() {
        return this.fdK3cPersonName;
    }

    /**
     * K3C用户名
     */
    public void setFdK3cPersonName(String fdK3cPersonName) {
        this.fdK3cPersonName = fdK3cPersonName;
    }

    /**
     * K3C密码
     */
    public String getFdK3cPassword() {
        return this.fdK3cPassword;
    }

    /**
     * K3C密码
     */
    public void setFdK3cPassword(String fdK3cPassword) {
        this.fdK3cPassword = fdK3cPassword;
    }

    /**
     * K3Clcid
     */
    public String getFdK3cIcid() {
        return this.fdK3cIcid;
    }

    /**
     * K3Clcid
     */
    public void setFdK3cIcid(String fdK3cIcid) {
        this.fdK3cIcid = fdK3cIcid;
    }
}
