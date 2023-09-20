package com.landray.kmss.eop.basedata.forms;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCardInfo;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class EopBasedataCardInfoForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdCorNum;  //公司号

    private String fdCorChiName; //公司中文名

    private String fdActNum; //客户号

    private String fdAcctNbr; //账户号

    private String fdCardNumber; //卡号

    private String fdHolderId; //持卡人id

    private String fdHolderName; //持卡人名称

    private String fdHolderChiName; //持卡人中文姓名

    private String fdHolderEngName; //持卡人英文姓名

    private String fdEmpNumber; //员工编号

    private String fdActivationCode; //开卡标识

    private String fdActivationDate; //开卡日期

    private String fdCirculationFlag; //流通状态

    private String fdCancelDate; //销卡日期

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private String fdCompanyId;

    private String fdCompanyName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdCorNum = null;
        fdCorChiName = null;
        fdActNum = null;
        fdAcctNbr = null;
        fdCardNumber = null;
        fdHolderId = null;
        fdHolderName = null;
        fdHolderChiName = null;
        fdHolderEngName = null;
        fdEmpNumber = null;
        fdActivationCode = null;
        fdActivationDate = null;
        fdCirculationFlag = null;
        fdCancelDate = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdCompanyId=null;
        fdCompanyName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataCardInfo> getModelClass() {
        return EopBasedataCardInfo.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdActivationDate", new FormConvertor_Common("fdActivationDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdCancelDate", new FormConvertor_Common("fdCancelDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdHolderId", new FormConvertor_IDToModel("fdHolder", SysOrgPerson.class));
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
        }
        return toModelPropertyMap;
    }

    public String getFdCorNum() {
        return fdCorNum;
    }

    public void setFdCorNum(String fdCorNum) {
        this.fdCorNum = fdCorNum;
    }

    public String getFdCorChiName() {
        return fdCorChiName;
    }

    public void setFdCorChiName(String fdCorChiName) {
        this.fdCorChiName = fdCorChiName;
    }

    public String getFdActNum() {
        return fdActNum;
    }

    public void setFdActNum(String fdActNum) {
        this.fdActNum = fdActNum;
    }

    public String getFdAcctNbr() {
        return fdAcctNbr;
    }

    public void setFdAcctNbr(String fdAcctNbr) {
        this.fdAcctNbr = fdAcctNbr;
    }

    public String getFdCardNumber() {
        return fdCardNumber;
    }

    public void setFdCardNumber(String fdCardNumber) {
        this.fdCardNumber = fdCardNumber;
    }

    public String getFdHolderChiName() {
        return fdHolderChiName;
    }

    public void setFdHolderChiName(String fdHolderChiName) {
        this.fdHolderChiName = fdHolderChiName;
    }

    public String getFdHolderEngName() {
        return fdHolderEngName;
    }

    public void setFdHolderEngName(String fdHolderEngName) {
        this.fdHolderEngName = fdHolderEngName;
    }

    public String getFdEmpNumber() {
        return fdEmpNumber;
    }

    public void setFdEmpNumber(String fdEmpNumber) {
        this.fdEmpNumber = fdEmpNumber;
    }

    public String getFdActivationCode() {
        return fdActivationCode;
    }

    public void setFdActivationCode(String fdActivationCode) {
        this.fdActivationCode = fdActivationCode;
    }

    public String getFdActivationDate() {
        return fdActivationDate;
    }

    public void setFdActivationDate(String fdActivationDate) {
        this.fdActivationDate = fdActivationDate;
    }

    public String getFdCirculationFlag() {
        return fdCirculationFlag;
    }

    public void setFdCirculationFlag(String fdCirculationFlag) {
        this.fdCirculationFlag = fdCirculationFlag;
    }

    public String getFdCancelDate() {
        return fdCancelDate;
    }

    public void setFdCancelDate(String fdCancelDate) {
        this.fdCancelDate = fdCancelDate;
    }

    public String getFdIsAvailable() {
        return fdIsAvailable;
    }

    public void setFdIsAvailable(String fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    public String getDocCreateTime() {
        return docCreateTime;
    }

    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    public String getDocAlterTime() {
        return docAlterTime;
    }

    public void setDocAlterTime(String docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    public String getDocCreatorId() {
        return docCreatorId;
    }

    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    public String getDocCreatorName() {
        return docCreatorName;
    }

    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    public String getDocAlterorId() {
        return docAlterorId;
    }

    public void setDocAlterorId(String docAlterorId) {
        this.docAlterorId = docAlterorId;
    }

    public String getDocAlterorName() {
        return docAlterorName;
    }

    public void setDocAlterorName(String docAlterorName) {
        this.docAlterorName = docAlterorName;
    }

    public String getFdHolderId() {
        return fdHolderId;
    }

    public void setFdHolderId(String fdHolderId) {
        this.fdHolderId = fdHolderId;
    }

    public String getFdHolderName() {
        return fdHolderName;
    }

    public void setFdHolderName(String fdHolderName) {
        this.fdHolderName = fdHolderName;
    }

    public String getFdCompanyId() {
        return fdCompanyId;
    }

    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
    }

    public String getFdCompanyName() {
        return fdCompanyName;
    }

    public void setFdCompanyName(String fdCompanyName) {
        this.fdCompanyName = fdCompanyName;
    }
}
