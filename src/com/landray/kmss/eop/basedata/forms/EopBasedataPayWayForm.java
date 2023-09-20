package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.eop.basedata.model.EopBasedataPayWay;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpForm;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 付款方式
  */
public class EopBasedataPayWayForm extends ExtendAuthTmpForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String docCreateTime;

    private String fdStatus;

    private String fdOrder;

    private String fdCode;

    private String docCreatorId;

    private String docCreatorName;
    
    private String fdIsDefault;
    
    private String fdIsTransfer;
    
    private String fdAccountId;

    private String fdAccountName;
    
    private String fdDefaultPayBankName;
    
    private String fdDefaultPayBankAccount;
    
    private String fdDefaultPayBankId;
    
    private String fdCompanyListIds;

    private String fdCompanyListNames;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        docCreateTime = null;
        fdStatus = null;
        fdOrder = null;
        fdCode = null;
        docCreatorId = null;
        docCreatorName = null;
        fdIsDefault= null;
        fdIsTransfer= null;
        fdAccountId= null;
        fdAccountName= null;
        fdDefaultPayBankName= null;
        fdDefaultPayBankAccount= null;
        fdDefaultPayBankId= null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
       
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataPayWay> getModelClass() {
        return EopBasedataPayWay.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdAccountId", new FormConvertor_IDToModel("fdAccount", EopBasedataAccounts.class));
            toModelPropertyMap.put("fdDefaultPayBankId", new FormConvertor_IDToModel("fdDefaultPayBank", EopBasedataPayBank.class));
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 付款方式名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 付款方式名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 最近更新时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 最近更新时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 排序号
     */
    public String getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(String fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 付款方式编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 付款方式编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 最近更新人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 最近更新人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 最近更新人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 最近更新人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }
    
    /**
     * 是否默认默认付款方式
     */
	public String getFdIsDefault() {
		return fdIsDefault;
	}
	
	/**
     * 是否默认默认付款方式
     */
	public void setFdIsDefault(String fdIsDefault) {
		this.fdIsDefault = fdIsDefault;
	}

	/**
     * 是否涉及转账
     */
	public String getFdIsTransfer() {
		return fdIsTransfer;
	}

	/**
     * 是否涉及转账
     */
	public void setFdIsTransfer(String fdIsTransfer) {
		this.fdIsTransfer = fdIsTransfer;
	}

	 /**
     * 对应会计科目
     */
	public String getFdAccountId() {
		return fdAccountId;
	}
	
	 /**
     * 对应会计科目
     */
	public void setFdAccountId(String fdAccountId) {
		this.fdAccountId = fdAccountId;
	}
	
	 /**
     * 对应会计科目
     */

	public String getFdAccountName() {
		return fdAccountName;
	}

	 /**
     * 对应会计科目
     */
	public void setFdAccountName(String fdAccountName) {
		this.fdAccountName = fdAccountName;
	}

	 /**
     * 付款方式对应默认银行-开户行
     */
	public String getFdDefaultPayBankName() {
		return fdDefaultPayBankName;
	}
	 /**
     * 付款方式对应默认银行-开户行
     */
	public void setFdDefaultPayBankName(String fdDefaultPayBankName) {
		this.fdDefaultPayBankName = fdDefaultPayBankName;
	}
	 /**
     * 付款方式对应默认银行-开户行账号
     */
	public String getFdDefaultPayBankAccount() {
		return fdDefaultPayBankAccount;
	}

	 /**
     * 付款方式对应默认银行-开户行账号
     */
	public void setFdDefaultPayBankAccount(String fdDefaultPayBankAccount) {
		this.fdDefaultPayBankAccount = fdDefaultPayBankAccount;
	}

	 /**
     * 付款方式对应默认银行-ID
     */
	public String getFdDefaultPayBankId() {
		return fdDefaultPayBankId;
	}

	 /**
     * 付款方式对应默认银行-ID
     */
	public void setFdDefaultPayBankId(String fdDefaultPayBankId) {
		this.fdDefaultPayBankId = fdDefaultPayBankId;
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
