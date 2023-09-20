package com.landray.kmss.fssc.expense.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.fssc.expense.forms.FsscExpenseTranDataForm;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 交易数据明细
  */
public class FsscExpenseTranData extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdTranDataId;

    private String fdCrdNum;

    private String fdActChiNam;

    private Date fdTrsDte;

    private String fdTrxTim;

    private Double fdOriCurAmt;

    private String fdOriCurCod;

    private String fdTrsCod;

    private String fdState;

    private FsscExpenseMain docMain;

    private Integer docIndex;

    @Override
    public Class<FsscExpenseTranDataForm> getFormClass() {
        return FsscExpenseTranDataForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdTrsDte", new ModelConvertor_Common("fdTrsDte").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("docMain.docSubject", "docMainName");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
        }
        return toFormPropertyMap;
    }

    /**
     * 交易数据id
     */
    public String getFdTranDataId() {
        return this.fdTranDataId;
    }

    /**
     * 交易数据id
     */
    public void setFdTranDataId(String fdTranDataId) {
        this.fdTranDataId = fdTranDataId;
    }

    /**
     * 卡号
     */
    public String getFdCrdNum() {
        return this.fdCrdNum;
    }

    /**
     * 卡号
     */
    public void setFdCrdNum(String fdCrdNum) {
        this.fdCrdNum = fdCrdNum;
    }

    /**
     * 持卡人中文名称
     */
    public String getFdActChiNam() {
        return this.fdActChiNam;
    }

    /**
     * 持卡人中文名称
     */
    public void setFdActChiNam(String fdActChiNam) {
        this.fdActChiNam = fdActChiNam;
    }

    /**
     * 交易日期
     */
    public Date getFdTrsDte() {
        return this.fdTrsDte;
    }

    /**
     * 交易日期
     */
    public void setFdTrsDte(Date fdTrsDte) {
        this.fdTrsDte = fdTrsDte;
    }

    /**
     * 交易时间
     */
    public String getFdTrxTim() {
        return this.fdTrxTim;
    }

    /**
     * 交易时间
     */
    public void setFdTrxTim(String fdTrxTim) {
        this.fdTrxTim = fdTrxTim;
    }

    /**
     * 交易金额
     */
    public Double getFdOriCurAmt() {
        return this.fdOriCurAmt;
    }

    /**
     * 交易金额
     */
    public void setFdOriCurAmt(Double fdOriCurAmt) {
        this.fdOriCurAmt = fdOriCurAmt;
    }

    /**
     * 交易币种
     */
    public String getFdOriCurCod() {
        return this.fdOriCurCod;
    }

    /**
     * 交易币种
     */
    public void setFdOriCurCod(String fdOriCurCod) {
        this.fdOriCurCod = fdOriCurCod;
    }

    /**
     * 交易类型
     */
    public String getFdTrsCod() {
        return this.fdTrsCod;
    }

    /**
     * 交易类型
     */
    public void setFdTrsCod(String fdTrsCod) {
        this.fdTrsCod = fdTrsCod;
    }

    /**
     * 状态
     */
    public String getFdState() {
        return this.fdState;
    }

    /**
     * 状态
     */
    public void setFdState(String fdState) {
        this.fdState = fdState;
    }

    public FsscExpenseMain getDocMain() {
        return this.docMain;
    }

    public void setDocMain(FsscExpenseMain docMain) {
        this.docMain = docMain;
    }

    public Integer getDocIndex() {
        return this.docIndex;
    }

    public void setDocIndex(Integer docIndex) {
        this.docIndex = docIndex;
    }
}
