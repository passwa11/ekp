package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataCompanyForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
  * 公司
  */
public class EopBasedataCompany extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;
    
    private String fdType;

    private String fdName;

    private String fdCode;

    private Boolean fdIsAvailable;

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

    private String fdDutyParagraph;//税号

    private Date docCreateTime;

    private Date docAlterTime;

    private EopBasedataCurrency fdBudgetCurrency;

    private EopBasedataCurrency fdAccountCurrency;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

	private SysOrgPerson contactor;

    private EopBasedataCompanyGroup fdGroup;

    private List<SysOrgElement> fdEkpOrg = new ArrayList<SysOrgElement>();

    private List<SysOrgPerson> fdFinancialStaff = new ArrayList<SysOrgPerson>();

    private List<SysOrgPerson> fdFinancialManager = new ArrayList<SysOrgPerson>();
    
    private String fdK3cUrl;

    private String fdK3cId;

    private String fdK3cPersonName;

    private String fdK3cPassword;

    private String fdK3cIcid;

    @Override
    public Class<EopBasedataCompanyForm> getFormClass() {
        return EopBasedataCompanyForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdBudgetCurrency.fdName", "fdBudgetCurrencyName");
            toFormPropertyMap.put("fdBudgetCurrency.fdId", "fdBudgetCurrencyId");
            toFormPropertyMap.put("fdAccountCurrency.fdName", "fdAccountCurrencyName");
            toFormPropertyMap.put("fdAccountCurrency.fdId", "fdAccountCurrencyId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("contactor.fdName", "contactorName");
			toFormPropertyMap.put("contactor.fdId", "contactorId");
            toFormPropertyMap.put("fdGroup.fdName", "fdGroupName");
            toFormPropertyMap.put("fdGroup.fdId", "fdGroupId");
            toFormPropertyMap.put("fdEkpOrg", new ModelConvertor_ModelListToString("fdEkpOrgIds:fdEkpOrgNames", "fdId:fdName"));
            toFormPropertyMap.put("fdFinancialStaff", new ModelConvertor_ModelListToString("fdFinancialStaffIds:fdFinancialStaffNames", "fdId:fdName"));
            toFormPropertyMap.put("fdFinancialManager", new ModelConvertor_ModelListToString("fdFinancialManagerIds:fdFinancialManagerNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
	 * 对接财务系统
	 */

	public String getFdSystemParam() {
		return fdSystemParam;
	}

	/**
	 * 对接财务系统
	 */
	public void setFdSystemParam(String fdSystemParam) {
		this.fdSystemParam = fdSystemParam;
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
     * 预算币种
     */
    public EopBasedataCurrency getFdBudgetCurrency() {
        return this.fdBudgetCurrency;
    }

    /**
     * 预算币种
     */
    public void setFdBudgetCurrency(EopBasedataCurrency fdBudgetCurrency) {
        this.fdBudgetCurrency = fdBudgetCurrency;
    }

    /**
     * 本位币
     */
    public EopBasedataCurrency getFdAccountCurrency() {
        return this.fdAccountCurrency;
    }

    /**
     * 本位币
     */
    public void setFdAccountCurrency(EopBasedataCurrency fdAccountCurrency) {
        this.fdAccountCurrency = fdAccountCurrency;
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
	 * 联系人
	 */
	public SysOrgPerson getContactor() {
		return this.contactor;
	}

	/**
	 * 联系人
	 */
	public void setContactor(SysOrgPerson contactor) {
		this.contactor = contactor;
	}

	/**
	 * 所属公司组
	 */
    public EopBasedataCompanyGroup getFdGroup() {
        return this.fdGroup;
    }

    /**
     * 所属公司组
     */
    public void setFdGroup(EopBasedataCompanyGroup fdGroup) {
        this.fdGroup = fdGroup;
    }

    /**
     * 对应EKP机构/部门
     */
    public List<SysOrgElement> getFdEkpOrg() {
        return this.fdEkpOrg;
    }

    /**
     * 对应EKP机构/部门
     */
    public void setFdEkpOrg(List<SysOrgElement> fdEkpOrg) {
        this.fdEkpOrg = fdEkpOrg;
    }

    /**
     * 财务人员
     */
    public List<SysOrgPerson> getFdFinancialStaff() {
        return this.fdFinancialStaff;
    }

    /**
     * 财务人员
     */
    public void setFdFinancialStaff(List<SysOrgPerson> fdFinancialStaff) {
        this.fdFinancialStaff = fdFinancialStaff;
    }

    /**
     * 财务管理员
     */
    public List<SysOrgPerson> getFdFinancialManager() {
        return this.fdFinancialManager;
    }

    /**
     * 财务管理员
     */
    public void setFdFinancialManager(List<SysOrgPerson> fdFinancialManager) {
        this.fdFinancialManager = fdFinancialManager;
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
