package com.landray.kmss.fssc.asset.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.fssc.asset.forms.FsscAssetGoodsForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 资产物资
  */
public class FsscAssetGoods extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private String fdName;

    private String fdCode;

    private Integer fdNum;

    private String fdAssetName;

    private SysOrgPerson docCreator;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    public Class<FsscAssetGoodsForm> getFormClass() {
        return FsscAssetGoodsForm.class;
    }

    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }

    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 物资名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 物资名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 物资编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 物资编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 数量
     */
    public Integer getFdNum() {
        return this.fdNum;
    }

    /**
     * 数量
     */
    public void setFdNum(Integer fdNum) {
        this.fdNum = fdNum;
    }

    /**
     * 仓库名称
     */
    public String getFdAssetName() {
        return this.fdAssetName;
    }

    /**
     * 仓库名称
     */
    public void setFdAssetName(String fdAssetName) {
        this.fdAssetName = fdAssetName;
    }

    /**
     * 创建人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
