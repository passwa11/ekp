package com.landray.kmss.eop.basedata.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataOther;
import com.landray.kmss.eop.basedata.model.EopBasedataOtherContact;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

import javax.servlet.http.HttpServletRequest;

/**
  * 联系人
  */
public class EopBasedataOtherContactForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdPosition;

    private String fdPhone;

    private String fdEmail;

    private String fdAddress;

    private String fdRemarks;

    private String fdIsfirst;

    private String docMainId;

    private String docMainName;

    private String docIndex;

    private FormFile file;

    private String fdImportType;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdPosition = null;
        fdPhone = null;
        fdEmail = null;
        fdAddress = null;
        fdRemarks = null;
        fdIsfirst = null;
        docIndex = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataOtherContact> getModelClass() {
        return EopBasedataOtherContact.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", EopBasedataOther.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 姓名
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 姓名
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 职务
     */
    public String getFdPosition() {
        return this.fdPosition;
    }

    /**
     * 职务
     */
    public void setFdPosition(String fdPosition) {
        this.fdPosition = fdPosition;
    }

    /**
     * 联系电话
     */
    public String getFdPhone() {
        return this.fdPhone;
    }

    /**
     * 联系电话
     */
    public void setFdPhone(String fdPhone) {
        this.fdPhone = fdPhone;
    }

    /**
     * 电子邮箱
     */
    public String getFdEmail() {
        return this.fdEmail;
    }

    /**
     * 电子邮箱
     */
    public void setFdEmail(String fdEmail) {
        this.fdEmail = fdEmail;
    }

    /**
     * 联系地址
     */
    public String getFdAddress() {
        return this.fdAddress;
    }

    /**
     * 联系地址
     */
    public void setFdAddress(String fdAddress) {
        this.fdAddress = fdAddress;
    }

    /**
     * 备注
     */
    public String getFdRemarks() {
        return this.fdRemarks;
    }

    /**
     * 备注
     */
    public void setFdRemarks(String fdRemarks) {
        this.fdRemarks = fdRemarks;
    }

    /**
     * 第一联系人
     */
    public String getFdIsfirst() {
        return this.fdIsfirst;
    }

    /**
     * 第一联系人
     */
    public void setFdIsfirst(String fdIsfirst) {
        this.fdIsfirst = fdIsfirst;
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
