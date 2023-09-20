package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;

/**
  * 主体
  */
public class EopBasedataContbody extends ExtendAuthModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Integer fdOrder;

    private Boolean fdIsAvailable;

    private String fdIden;

    private String fdKey;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<com.landray.kmss.eop.basedata.forms.EopBasedataContbodyForm> getFormClass() {
        return com.landray.kmss.eop.basedata.forms.EopBasedataContbodyForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.addNoConvertProperty("authEditorFlag");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
        if (!getAuthReaderFlag()) {
        }
    }

    /**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 排序号
     */
    public Integer getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 是否有效
     */
    public Boolean getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    /**
     * 统一信用代码
     */
    public String getFdIden() {
        return this.fdIden;
    }

    /**
     * 统一信用代码
     */
    public void setFdIden(String fdIden) {
        this.fdIden = fdIden;
    }

    /**
     * 关键字
     */
    public String getFdKey() {
        return this.fdKey;
    }

    /**
     * 关键字
     */
    public void setFdKey(String fdKey) {
        this.fdKey = fdKey;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    @Override
    public String getDocStatus() {
        return "30";
    }

    @Override
    public String getDocSubject() {
        return getFdName();
    }
}
