package com.landray.kmss.eop.basedata.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataAccountForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 员工账户
  */
public class EopBasedataAccount extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Boolean fdIsAvailable;

    private String fdBankName;

    private String fdBankNo;

    private String fdBankAccount;

    private Boolean fdIsDefault;

    private Date docCreateTime;

    private Date docAlterTime;

    private SysOrgPerson fdPerson;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;
    
    private String fdAccountArea;

    @Override
    public Class<EopBasedataAccountForm> getFormClass() {
        return EopBasedataAccountForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
            toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 账户开户地
     */
    public String getFdAccountArea() {
		return fdAccountArea;
	}
    /**
     * 账户开户地
     */
   
	public void setFdAccountArea(String fdAccountArea) {
		this.fdAccountArea = fdAccountArea;
	}


	/**
     * 账户名
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 账户名
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
     * 是否默认账户
     */
    public Boolean getFdIsDefault() {
        return this.fdIsDefault;
    }

    /**
     * 是否默认账户
     */
    public void setFdIsDefault(Boolean fdIsDefault) {
        this.fdIsDefault = fdIsDefault;
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
     * 归属人
     */
    public SysOrgPerson getFdPerson() {
        return this.fdPerson;
    }

    /**
     * 归属人
     */
    public void setFdPerson(SysOrgPerson fdPerson) {
        this.fdPerson = fdPerson;
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
}
