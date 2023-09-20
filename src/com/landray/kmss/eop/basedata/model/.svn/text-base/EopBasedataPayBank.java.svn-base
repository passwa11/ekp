package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataPayBankForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 付款银行
  */
public class EopBasedataPayBank extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdAccountName;

    private String fdCode;

    private String fdBankName;

    private String fdBankNo;

    private String fdBankType;

    private String fdBankAccount;

    private String fdUse;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private Date docAlterTime;

    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;
    
    private String fdPayBankBelong;
    
    private String fdAccountAreaName;
    
    private String fdAccountAreaId;
    
    private EopBasedataAccounts fdAccounts;

    @Override
    public Class<EopBasedataPayBankForm> getFormClass() {
        return EopBasedataPayBankForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("fdAccounts.fdName", "fdAccountsName");
            toFormPropertyMap.put("fdAccounts.fdId", "fdAccountsId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }
    
    /**
     * 会计科目
     * @return
     */
    public EopBasedataAccounts getFdAccounts() {
		return fdAccounts;
	}
    
    /**
     * 会计科目
     * @param fdAccounts
     */
	public void setFdAccounts(EopBasedataAccounts fdAccounts) {
		this.fdAccounts = fdAccounts;
	}

	/**
     * 账户名
     */
    public String getFdAccountName() {
        return this.fdAccountName;
    }

    /**
     * 账户名
     */
    public void setFdAccountName(String fdAccountName) {
        this.fdAccountName = fdAccountName;
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
     * 联行号
     */
    public String getFdBankNo() {
        return this.fdBankNo;
    }

    /**
     * 联行号
     */
    public void setFdBankNo(String fdBankNo) {
        this.fdBankNo = fdBankNo;
    }

    /**
     * 开户行账号
     */
    public String getFdBankAccount() {
        return this.fdBankAccount;
    }

    /**
     * 开户行账号
     */
    public void setFdBankAccount(String fdBankAccount) {
        this.fdBankAccount = fdBankAccount;
    }

    /**
     * 账号用途
     */
    public String getFdUse() {
        return this.fdUse;
    }

    /**
     * 账号用途
     */
    public void setFdUse(String fdUse) {
        this.fdUse = fdUse;
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
     * 编码
     */
    public String getFdCode() {
        return fdCode;
    }
    /**
     * 编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }
    /**
     * 付款银行接口
     * @return
     */
    public String getFdPayBankBelong() {
  		return fdPayBankBelong;
  	}
    /**
     * 付款银行接口
     */
  	public void setFdPayBankBelong(String fdPayBankBelong) {
  		this.fdPayBankBelong = fdPayBankBelong;
  	}

    /**
     * 银行类型
     * @return
     */
    public String getFdBankType() {
        return fdBankType;
    }
    /**
     * 银行类型
     * @return
     */
    public void setFdBankType(String fdBankType) {
        this.fdBankType = fdBankType;
    }

    public String getFdAccountAreaName() {
		return fdAccountAreaName;
	}

	public void setFdAccountAreaName(String fdAccountAreaName) {
		this.fdAccountAreaName = fdAccountAreaName;
	}

	public String getFdAccountAreaId() {
		return fdAccountAreaId;
	}

	public void setFdAccountAreaId(String fdAccountAreaId) {
		this.fdAccountAreaId = fdAccountAreaId;
	}

}
