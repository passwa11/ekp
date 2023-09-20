package com.landray.kmss.fssc.asset.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.fssc.asset.model.FsscAssetGoods;

/**
  * 资产物资
  */
public class FsscAssetGoodsForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String fdName;

    private String fdCode;

    private String fdNum;

    private String fdAssetName;

    private String docCreatorId;

    private String docCreatorName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        fdName = null;
        fdCode = null;
        fdNum = null;
        fdAssetName = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    public Class<FsscAssetGoods> getModelClass() {
        return FsscAssetGoods.class;
    }

    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
        }
        return toModelPropertyMap;
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
    public String getFdNum() {
        return this.fdNum;
    }

    /**
     * 数量
     */
    public void setFdNum(String fdNum) {
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

    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
