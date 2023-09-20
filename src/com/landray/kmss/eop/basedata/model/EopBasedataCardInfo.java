package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataCardInfoForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

public class EopBasedataCardInfo extends BaseModel{
    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdCorNum;  //公司号

    private String fdCorChiName; //公司中文名

    private EopBasedataCompany fdCompany;//公司

    private String fdActNum; //客户号

    private String fdAcctNbr; //账户号

    private String fdCardNumber; //卡号

    private SysOrgPerson fdHolder; //持卡人

    private String fdHolderChiName; //持卡人中文姓名

    private String fdHolderEngName; //持卡人英文姓名

    private String fdEmpNumber; //员工编号

    private String fdActivationCode; //开卡标识

    private Date fdActivationDate; //开卡日期

    private String fdCirculationFlag; //流通状态

    private Date fdCancelDate; //销卡日期

    private Date docCreateTime;  //创建时间

    private Date docAlterTime;  //修改时间

    private SysOrgPerson docCreator;  //创建人

    private SysOrgPerson docAlteror;  //修改人

    private Boolean fdIsAvailable;  //是否有效

    @Override
    public Class<EopBasedataCardInfoForm> getFormClass() {
        return EopBasedataCardInfoForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdActivationDate", new ModelConvertor_Common("fdActivationDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdCancelDate", new ModelConvertor_Common("fdCancelDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("fdHolder.fdId","fdHolderId");
            toFormPropertyMap.put("fdHolder.fdName", "fdHolderName");
            toFormPropertyMap.put("fdCompany.fdId","fdCompanyId");
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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

    public Date getFdActivationDate() {
        return fdActivationDate;
    }

    public void setFdActivationDate(Date fdActivationDate) {
        this.fdActivationDate = fdActivationDate;
    }

    public String getFdCirculationFlag() {
        return fdCirculationFlag;
    }

    public void setFdCirculationFlag(String fdCirculationFlag) {
        this.fdCirculationFlag = fdCirculationFlag;
    }

    public Date getFdCancelDate() {
        return fdCancelDate;
    }

    public void setFdCancelDate(Date fdCancelDate) {
        this.fdCancelDate = fdCancelDate;
    }

    public Date getDocCreateTime() {
        return docCreateTime;
    }

    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    public Date getDocAlterTime() {
        return docAlterTime;
    }

    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    public SysOrgPerson getDocCreator() {
        return docCreator;
    }

    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    public SysOrgPerson getDocAlteror() {
        return docAlteror;
    }

    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
    }

    public Boolean getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    public SysOrgPerson getFdHolder() {
        return fdHolder;
    }

    public void setFdHolder(SysOrgPerson fdHolder) {
        this.fdHolder = fdHolder;
    }

    public EopBasedataCompany getFdCompany() {
        return fdCompany;
    }

    public void setFdCompany(EopBasedataCompany fdCompany) {
        this.fdCompany = fdCompany;
    }
}
