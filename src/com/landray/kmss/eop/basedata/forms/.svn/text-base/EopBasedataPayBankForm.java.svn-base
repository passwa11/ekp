package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 付款银行
  */
public class EopBasedataPayBankForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdAccountName;

    private String fdCode;

    private String fdBankName;

    private String fdBankNo;

    private String fdBankType;

    private String fdBankAccount;

    private String fdUse;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String fdCompanyListIds;

    private String fdCompanyListNames;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;
    
    private String fdPayBankBelong;
    
    private String fdAccountAreaName;
    
    private String fdAccountAreaId;
    
    private String fdAccountsName;
    
    private String fdAccountsId;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdAccountsId=null;
    	fdAccountsName=null;
        fdAccountName = null;
        fdCode = null;
        fdBankName = null;
        fdBankNo = null;
        fdBankAccount = null;
        fdBankType = null;
        fdUse = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdPayBankBelong = null;
        fdAccountAreaName = null;
        fdAccountAreaId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataPayBank> getModelClass() {
        return EopBasedataPayBank.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
            toModelPropertyMap.put("fdAccountsId", new FormConvertor_IDToModel("fdAccounts", EopBasedataAccounts.class));
        }
        return toModelPropertyMap;
    }
    
    /**
     * 会计科目
     * @return
     */
    public String getFdAccountsName() {
		return fdAccountsName;
	}
    /**
     * 会计科目
     * @return
     */
	public void setFdAccountsName(String fdAccountsName) {
		this.fdAccountsName = fdAccountsName;
	}
	  /**
     * 会计科目
     * @return
     */
	public String getFdAccountsId() {
		return fdAccountsId;
	}
	 /**
     * 会计科目
     * @return
     */
	public void setFdAccountsId(String fdAccountsId) {
		this.fdAccountsId = fdAccountsId;
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
  	 * 分行名称
  	 * @return
  	 */
  	public String getFdAccountAreaName() {
		return fdAccountAreaName;
	}
 	/**
  	 * 分行名称
  	 * @return
  	 */
	public void setFdAccountAreaName(String fdAccountAreaName) {
		this.fdAccountAreaName = fdAccountAreaName;
	}
 	/**
  	 * 分行地区ID
  	 * @return
  	 */
	public String getFdAccountAreaId() {
		return fdAccountAreaId;
	}
	/**
  	 * 分行地区ID
  	 * @return
  	 */
	public void setFdAccountAreaId(String fdAccountAreaId) {
		this.fdAccountAreaId = fdAccountAreaId;
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
}
