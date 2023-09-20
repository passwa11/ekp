package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAccount;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 员工账户
  */
public class EopBasedataAccountForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdIsAvailable;

    private String fdBankName;

    private String fdBankNo;

    private String fdBankAccount;

    private String fdIsDefault;

    private String docCreateTime;

    private String docAlterTime;

    private String fdPersonId;

    private String fdPersonName;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;
    
    private String  fdAccountArea;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdAccountArea = null;
        fdName = null;
        fdIsAvailable = null;
        fdBankName = null;
        fdBankNo = null;
        fdBankAccount = null;
        fdIsDefault = null;
        docCreateTime = null;
        docAlterTime = null;
        fdPersonId = null;
        fdPersonName = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataAccount> getModelClass() {
        return EopBasedataAccount.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel("fdPerson", SysOrgPerson.class));
        }
        return toModelPropertyMap;
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
    public String getFdIsDefault() {
        return this.fdIsDefault;
    }

    /**
     * 是否默认账户
     */
    public void setFdIsDefault(String fdIsDefault) {
        this.fdIsDefault = fdIsDefault;
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
     * 归属人
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 归属人
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 归属人
     */
    public String getFdPersonName() {
        return this.fdPersonName;
    }

    /**
     * 归属人
     */
    public void setFdPersonName(String fdPersonName) {
        this.fdPersonName = fdPersonName;
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
}
