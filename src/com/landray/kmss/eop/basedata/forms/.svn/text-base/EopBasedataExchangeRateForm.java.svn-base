package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 汇率
  */
public class EopBasedataExchangeRateForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdRate;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String fdOrder;

    private String fdStartDate;

    private String fdType;

    private String fdSourceCurrencyId;

    private String fdSourceCurrencyName;

    private String fdTargetCurrencyId;

    private String fdTargetCurrencyName;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private String fdCompanyListIds;

    private String fdCompanyListNames;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdRate = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        fdOrder = null;
        fdStartDate = null;
        fdType = null;
        fdSourceCurrencyId = null;
        fdSourceCurrencyName = null;
        fdTargetCurrencyId = null;
        fdTargetCurrencyName = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataExchangeRate> getModelClass() {
        return EopBasedataExchangeRate.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdStartDate", new FormConvertor_Common("fdStartDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdSourceCurrencyId", new FormConvertor_IDToModel("fdSourceCurrency", EopBasedataCurrency.class));
            toModelPropertyMap.put("fdTargetCurrencyId", new FormConvertor_IDToModel("fdTargetCurrency", EopBasedataCurrency.class));
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 汇率
     */
    public String getFdRate() {
        return this.fdRate;
    }

    /**
     * 汇率
     */
    public void setFdRate(String fdRate) {
        this.fdRate = fdRate;
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
     * 有效开始日期
     */
    public String getFdStartDate() {
        return this.fdStartDate;
    }

    /**
     * 有效开始日期
     */
    public void setFdStartDate(String fdStartDate) {
        this.fdStartDate = fdStartDate;
    }

    /**
     * 汇率类型
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 汇率类型
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 货币(源)
     */
    public String getFdSourceCurrencyId() {
        return this.fdSourceCurrencyId;
    }

    /**
     * 货币(源)
     */
    public void setFdSourceCurrencyId(String fdSourceCurrencyId) {
        this.fdSourceCurrencyId = fdSourceCurrencyId;
    }

    /**
     * 货币(源)
     */
    public String getFdSourceCurrencyName() {
        return this.fdSourceCurrencyName;
    }

    /**
     * 货币(源)
     */
    public void setFdSourceCurrencyName(String fdSourceCurrencyName) {
        this.fdSourceCurrencyName = fdSourceCurrencyName;
    }

    /**
     * 货币(目标)
     */
    public String getFdTargetCurrencyId() {
        return this.fdTargetCurrencyId;
    }

    /**
     * 货币(目标)
     */
    public void setFdTargetCurrencyId(String fdTargetCurrencyId) {
        this.fdTargetCurrencyId = fdTargetCurrencyId;
    }

    /**
     * 货币(目标)
     */
    public String getFdTargetCurrencyName() {
        return this.fdTargetCurrencyName;
    }

    /**
     * 货币(目标)
     */
    public void setFdTargetCurrencyName(String fdTargetCurrencyName) {
        this.fdTargetCurrencyName = fdTargetCurrencyName;
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
