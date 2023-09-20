package com.landray.kmss.fssc.expense.forms;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.model.FsscExpenseTranData;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

import javax.servlet.http.HttpServletRequest;

/**
  * 交易数据明细
  */
public class FsscExpenseTranDataForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdTranDataId;

    private String fdCrdNum;

    private String fdActChiNam;

    private String fdTrsDte;

    private String fdTrxTim;

    private String fdOriCurAmt;

    private String fdOriCurCod;

    private String fdTrsCod;

    private String fdState;

    private String docMainId;

    private String docMainName;

    private String docIndex;

    private FormFile file;

    private String fdImportType;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdTranDataId = null;
        fdCrdNum = null;
        fdActChiNam = null;
        fdTrsDte = null;
        fdTrxTim = null;
        fdOriCurAmt = null;
        fdOriCurCod = null;
        fdTrsCod = null;
        fdState = null;
        docIndex = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseTranData> getModelClass() {
        return FsscExpenseTranData.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdTrsDte", new FormConvertor_Common("fdTrsDte").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", FsscExpenseMain.class));
        }
        return toModelPropertyMap;
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
    public String getFdTrsDte() {
        return this.fdTrsDte;
    }

    /**
     * 交易日期
     */
    public void setFdTrsDte(String fdTrsDte) {
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
    public String getFdOriCurAmt() {
        return this.fdOriCurAmt;
    }

    /**
     * 交易金额
     */
    public void setFdOriCurAmt(String fdOriCurAmt) {
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

    public String getDocMainId() {
        return this.docMainId;
    }

    public void setDocMainId(String docMainId) {
        this.docMainId = docMainId;
    }

    public String getDocMainName() {
        return this.docMainName;
    }

    public void setDocMainName(String docMainName) {
        this.docMainName = docMainName;
    }

    public String getDocIndex() {
        return this.docIndex;
    }

    public void setDocIndex(String docIndex) {
        this.docIndex = docIndex;
    }

    public FormFile getFile() {
        return this.file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public String getFdImportType() {
        return this.fdImportType;
    }

    public void setFdImportType(String fdImportType) {
        this.fdImportType = fdImportType;
    }
}
