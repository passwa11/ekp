package com.landray.kmss.fssc.expense.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.web.upload.FormFile;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseDidiDetail;

/**
  * 滴滴用车信息
  */
public class FsscExpenseDidiDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdPassenger;

    private String fdStartPlace;

    private String fdEndPlace;

    private String fdStartTime;

    private String fdEndTime;

    private String fdMoney;

    private String fdOrderId;

    private String fdCarLevel;

    private String docMainId;

    private String docMainName;

    private String docIndex;

    private FormFile file;

    private String fdImportType;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdPassenger = null;
        fdStartPlace = null;
        fdEndPlace = null;
        fdStartTime = null;
        fdEndTime = null;
        fdMoney = null;
        fdOrderId = null;
        fdCarLevel = null;
        docIndex = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseDidiDetail> getModelClass() {
        return FsscExpenseDidiDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", FsscExpenseMain.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 乘车人
     */
    public String getFdPassenger() {
        return this.fdPassenger;
    }

    /**
     * 乘车人
     */
    public void setFdPassenger(String fdPassenger) {
        this.fdPassenger = fdPassenger;
    }

    /**
     * 出发地
     */
    public String getFdStartPlace() {
        return this.fdStartPlace;
    }

    /**
     * 出发地
     */
    public void setFdStartPlace(String fdStartPlace) {
        this.fdStartPlace = fdStartPlace;
    }

    /**
     * 到达地
     */
    public String getFdEndPlace() {
        return this.fdEndPlace;
    }

    /**
     * 到达地
     */
    public void setFdEndPlace(String fdEndPlace) {
        this.fdEndPlace = fdEndPlace;
    }

    /**
     * 出发时间
     */
    public String getFdStartTime() {
        return this.fdStartTime;
    }

    /**
     * 出发时间
     */
    public void setFdStartTime(String fdStartTime) {
        this.fdStartTime = fdStartTime;
    }

    /**
     * 到达时间
     */
    public String getFdEndTime() {
        return this.fdEndTime;
    }

    /**
     * 到达时间
     */
    public void setFdEndTime(String fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    /**
     * 金额
     */
    public String getFdMoney() {
        return this.fdMoney;
    }

    /**
     * 金额
     */
    public void setFdMoney(String fdMoney) {
        this.fdMoney = fdMoney;
    }

    /**
     * 订单ID
     */
    public String getFdOrderId() {
        return this.fdOrderId;
    }

    /**
     * 订单ID
     */
    public void setFdOrderId(String fdOrderId) {
        this.fdOrderId = fdOrderId;
    }

    /**
     * 车型
     */
    public String getFdCarLevel() {
        return this.fdCarLevel;
    }

    /**
     * 车型
     */
    public void setFdCarLevel(String fdCarLevel) {
        this.fdCarLevel = fdCarLevel;
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
