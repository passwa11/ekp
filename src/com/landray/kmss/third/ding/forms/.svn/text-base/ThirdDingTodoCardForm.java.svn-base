package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ding.model.ThirdDingTodoCard;

/**
  * 待办卡片
  */
public class ThirdDingTodoCardForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdTemplateId;

    private String fdCardId;

    private String docCreateTime;

    private String docAlterTime;

    private String fdCardMsg;

    private String fdLang;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdName = null;
        fdTemplateId = null;
        fdCardId = null;
        docCreateTime = null;
        docAlterTime = null;
        fdCardMsg = null;
        fdLang = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingTodoCard> getModelClass() {
        return ThirdDingTodoCard.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
        }
        return toModelPropertyMap;
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
     * 待办模板ID
     */
    public String getFdTemplateId() {
        return this.fdTemplateId;
    }

    /**
     * 待办模板ID
     */
    public void setFdTemplateId(String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }

    /**
     * 卡片ID
     */
    public String getFdCardId() {
        return this.fdCardId;
    }

    /**
     * 卡片ID
     */
    public void setFdCardId(String fdCardId) {
        this.fdCardId = fdCardId;
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
     * 卡片信息
     */
    public String getFdCardMsg() {
        return this.fdCardMsg;
    }

    /**
     * 卡片信息
     */
    public void setFdCardMsg(String fdCardMsg) {
        this.fdCardMsg = fdCardMsg;
    }

    /**
     * 待办语言
     */
    public String getFdLang() {
        return this.fdLang;
    }

    /**
     * 待办语言
     */
    public void setFdLang(String fdLang) {
        this.fdLang = fdLang;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
